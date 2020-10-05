package com.windcoder.qycms.blog.service;

import com.windcoder.qycms.blog.dto.BlogArticleDto;
import com.windcoder.qycms.blog.dto.BlogCategoryDto;
import com.windcoder.qycms.blog.entity.BlogMove;
import com.windcoder.qycms.exception.BusinessException;
import com.windcoder.qycms.system.dto.CommentDto;
import com.windcoder.qycms.system.dto.UserWebDto;
import com.windcoder.qycms.system.enums.CommentStatus;
import com.windcoder.qycms.system.service.CommentService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.*;
import java.util.Arrays;

@Service
@Slf4j
public class BlogMoveService {
    @Autowired
    private BlogArticleService blogArticleService;
    @Autowired
    private CommentService commentService;
    String URL = "jdbc:mysql://%s:%s/%s?useSSL=false&characterEncoding=utf8";

    String MYSQL_DRIVER = "com.mysql.cj.jdbc.Driver";
    public void importDataByDB(BlogMove blogMove, UserWebDto user) {
        String infoSql = "SELECT  " +
                "p.* " +
                ",  " +
                " ( " +
                "    SELECT " +
                "    wt. NAME " +
                "    FROM " +
                "    wp_terms wt " +
                "    LEFT JOIN wp_term_taxonomy wtt ON wt.term_id = wtt.term_id " +
                "    LEFT JOIN wp_term_relationships wtr ON wtt.term_taxonomy_id = wtr.term_taxonomy_id " +
                "    WHERE " +
                "    wtt.taxonomy = 'category' " +
                "    AND wt.term_id > 1 " +
                "    AND wtr.object_id = p.id " +
                "    LIMIT 1 " +
                "    ) category, " +
                "  ( " +
                "    SELECT " +
                "    GROUP_CONCAT(wt. NAME) " +
                "    FROM " +
                "    wp_terms wt " +
                "    LEFT JOIN wp_term_taxonomy wtt ON wt.term_id = wtt.term_id " +
                "    LEFT JOIN wp_term_relationships wtr ON wtt.term_taxonomy_id = wtr.term_taxonomy_id " +
                "    WHERE " +
                "    wtt.taxonomy = 'post_tag' " +
                "    AND wt.term_id > 1 " +
                "    AND wtr.object_id = p.id  " +
                "    ) tags, " +
                "(select    MAX(cast(meta_value as SIGNED INTEGER))  from wp_postmeta pm where pm.post_id = p.id and (pm.meta_key  = 'views' " +
                "or pm.meta_key = 'tin_post_views')  ) post_views  " +
                ",(select meta_value from wp_postmeta pm where  pm.post_id = p.id and pm.meta_key = '_yoast_wpseo_canonical') canonical";

        String whereSql =  "FROM  " +
                "wp_posts p   " +
                "where   (p.post_status = 'private' or p.post_status = 'publish') " +
                "  AND (p.post_type = 'post' or p.post_type = 'page')";
        StringBuilder countSql = new StringBuilder("select count(*)").append(whereSql);
        String url = getUrl(blogMove);
        StringBuilder listSql = new StringBuilder(infoSql).append(whereSql).append(" limit %s,%s");
        String commentCountSql = "SELECT count(*) FROM  wp_comments wc  where  comment_post_ID  = %s";
        String commentListSql = "SELECT * FROM  wp_comments wc  where  comment_post_ID  = %s";
//        Connection conn = null;
//        Statement stat = null;
        try {
            Class.forName(getDriver());
        }catch (ClassNotFoundException e) {
            log.error("连接数据库异常 {}", e.getMessage());
        }
        //测试url中是否包含useSSL字段，没有则添加设该字段且禁用
        if (url.indexOf("?") == -1) {
            url = url + "?useSSL=false";
        } else if (url.indexOf("useSSL=false") == -1 || url.indexOf("useSSL=true") == -1) {
            url = url + "&useSSL=false";
        }
        ResultSet resultSet = null;
//        ResultSet ccountRS = null;
        ResultSet cListRS = null;
        // TODO: 后期视情况改为prepareStatement
        try(Connection conn =DriverManager.getConnection(url, blogMove.getUsername(), blogMove.getPassword());
            Statement stat = conn.createStatement();){
            int count = 0;
            try(ResultSet resultSet1 = stat.executeQuery(countSql.toString()); ) {
               if (resultSet1.next()) {
                   count = resultSet1.getInt(1);
                   log.warn("当前总数量 {} ", count);
               }
            } catch (SQLException e) {
                throw new BusinessException("数据库解析异常");
            }
            int pageIndex = 0;
            int pageSize = 10;
            BlogArticleDto articleDto = null;
            String tagStr = null;
            String category = null;
            BlogCategoryDto categoryDto = null;
            Long aid = null;
            int commentCount = 0;
            CommentDto commentDto = null;
            while (count>0 && count>=pageIndex) {
                resultSet = stat.executeQuery(String.format(listSql.toString(), pageIndex, pageSize));
                while (resultSet.next()) {
                    articleDto = new BlogArticleDto();
                    tagStr = resultSet.getString("tags");
                    category = resultSet.getString("category");
                    aid = resultSet.getLong("id");
                    articleDto.setTitle(resultSet.getString("post_type"));
                    articleDto.setPermaLink(resultSet.getString("post_name"));
                    articleDto.setContent(resultSet.getString("post_content_filtered"));
                    articleDto.setContentHtml(resultSet.getString("post_content"));
                    articleDto.setViewCount(Long.valueOf(resultSet.getInt("post_views")));
                    articleDto.setCanonicalLink(resultSet.getString("canonical"));
                    articleDto.setDeleted(false);
                    articleDto.setPublished(true);
                    articleDto.setPublishedDate(resultSet.getDate("post_date"));
                    articleDto.setCreatedDate(resultSet.getDate("post_date"));
                    articleDto.setLastModifiedDate(resultSet.getDate("post_modified"));
                    if (StringUtils.isNotBlank(tagStr)) {
                        articleDto.setTagStrings(Arrays.asList(tagStr.split(",")));
                    }
                    if (StringUtils.isNotBlank(category)) {
                        categoryDto = new BlogCategoryDto();
                        categoryDto.setName(category);
                        articleDto.setCategory(categoryDto);
                    }
                    // 更新文章,标签,分类
                    blogArticleService.saveByBlogMove(articleDto, user);
                    // 更新评论

                    try(ResultSet ccountRS = stat.executeQuery(String.format(commentCountSql, aid)); ) {
                        if (ccountRS.next()) {
                            commentCount = ccountRS.getInt(1);
                            log.warn("当前评论总数量 {} ", count);
                        }
                    } catch (SQLException e){
                        log.error("获取评论异常 {}", e.getMessage());
                    }
                    int cpageIndex = 0;
                    int cpageSize = 10;
                    int cParent = 0;
                    int cUserId = 0;
                    UserWebDto userWebDto = null;
                    while (commentCount>0 && commentCount >= cpageIndex) {
                        cListRS = stat.executeQuery(String.format(commentListSql, cpageIndex, cpageSize));
                        while (cListRS.next()) {
                            commentDto = new CommentDto();
                            cUserId = cListRS.getInt("user_id");
                            cParent = cListRS.getInt("comment_parent");
                            commentDto.setId(cListRS.getLong("comment_id"));
                            commentDto.setAuthorName(cListRS.getString("comment_author"));
                            commentDto.setAuthorEmail(cListRS.getString("comment_author_email"));
                            commentDto.setAuthorUrl(cListRS.getString("comment_author_url"));
                            commentDto.setAuthorIp(cListRS.getString("comment_author_IP"));
                            commentDto.setAgent(cListRS.getString("comment_agent"));
                            commentDto.setContent(cListRS.getString("comment_content"));
                            commentDto.setCreatedDate(cListRS.getDate("comment_date"));
                            commentDto.setLastModifiedDate(cListRS.getDate("comment_date"));
                            commentDto.setTargetId(articleDto.getCommentAgentId());
                            commentDto.setStatus(CommentStatus.ENROLLED.name());
                            if (cUserId > 0) {
                                userWebDto = new UserWebDto();
                                userWebDto.setId(Long.valueOf(cUserId));
                                commentDto.setUser(userWebDto);
                            }
                            if (cParent >0 ) {
                                commentDto.setParentId(Long.valueOf(cParent));
                            }
                            commentService.saveComment(commentDto);
                        }
                    }
                }
                pageIndex += pageSize;
            }

        } catch (final SQLException e) {
            log.error("数据库解析异常 ", e);
            throw new BusinessException("数据库解析异常");
        } finally {
            if (resultSet!=null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    log.error("数据库解析异常 ", e);
                    throw new BusinessException("数据库解析异常");
                }
            }
        }


    }
    public String getUrl(BlogMove blogMove) {
        return String.format(URL, blogMove.getIp(), blogMove.getPort(), blogMove.getDatabase());
    }
    public String getDriver() {
        return MYSQL_DRIVER;
    }

}
