package com.windcoder.qycms.blog.service;

import com.alibaba.druid.pool.DruidDataSourceFactory;
import com.google.common.base.Charsets;
import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnels;
import com.windcoder.qycms.blog.dto.BlogArticleDto;
import com.windcoder.qycms.blog.dto.BlogCategoryDto;
import com.windcoder.qycms.blog.entity.BlogMove;
import com.windcoder.qycms.blog.enums.BlogArticleStatus;
import com.windcoder.qycms.blog.utils.RemarkUtils;
import com.windcoder.qycms.entity.DruidCommonProperties;
import com.windcoder.qycms.exception.BusinessException;
import com.windcoder.qycms.system.dto.CommentDto;
import com.windcoder.qycms.system.dto.UserWebDto;
import com.windcoder.qycms.system.enums.CommentStatus;
import com.windcoder.qycms.system.enums.CommentType;
import com.windcoder.qycms.system.filters.BloomCacheFilter;
import com.windcoder.qycms.system.repository.mybatis.MyCommonMapper;
import com.windcoder.qycms.system.service.CommentService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.*;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

@Service
@Slf4j
public class BlogMoveService {
    @Autowired
    private BlogArticleService blogArticleService;
    @Autowired
    private CommentService commentService;
    @Autowired
    private DruidCommonProperties druidCommonProperties;
    @Autowired
    private MyCommonMapper myCommonMapper;

    String URL = "jdbc:mysql://%s:%s/%s?useSSL=false&characterEncoding=utf8";

    String MYSQL_DRIVER = "com.mysql.cj.jdbc.Driver";
    int ARTICLE_PAGE_SIZE = 20;
    int COMMENT_PAGE_SIZE = 20;
    public void importDataByDB(BlogMove blogMove, UserWebDto user) {

        String url = getUrl(blogMove);
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


        // TODO: 后期视情况改为prepareStatement

        try(Connection conn =initDruidDataSource(url, blogMove.getUsername(), blogMove.getPassword());){
            importArticleDataByDB( conn,  user);
        } catch (final SQLException e) {
            log.error("数据库解析异常 ", e);
            throw new BusinessException("数据库解析异常");
        } catch (Exception e) {
            log.error("数据库解析异常2 ", e);
            throw new BusinessException("数据库解析异常");
        }
    }
    public Connection initDruidDataSource(String url, String username, String password) throws Exception {
//        druidCommonProperties
        HashMap<String, Object> properties = new HashMap<String, Object>();
        properties.put("url",url);
        properties.put("username",username);
        properties.put("password",password);
        properties.put("driverClassName",getDriver());
        properties.put("initialSize",druidCommonProperties.getInitialSize());
        properties.put("minIdle",druidCommonProperties.getMinIdle());
        properties.put("maxActive",druidCommonProperties.getMaxActive());
        properties.put("maxWait",druidCommonProperties.getMaxWait());
        properties.put("timeBetweenEvictionRunsMillis",druidCommonProperties.getTimeBetweenEvictionRunsMillis());
        properties.put("minEvictableIdleTimeMillis",druidCommonProperties.getMinEvictableIdleTimeMillis());
        properties.put("validationQuery",druidCommonProperties.getValidationQuery());
        properties.put("testWhileIdle",druidCommonProperties.getTestWhileIdle());
        properties.put("testOnBorrow",druidCommonProperties.getTestOnBorrow());
        properties.put("testOnReturn",druidCommonProperties.getTestOnReturn());
        properties.put("poolPreparedStatements",druidCommonProperties.getPoolPreparedStatements());
        properties.put("maxOpenPreparedStatements",druidCommonProperties.getMaxOpenPreparedStatements());
        properties.put("asyncInit",druidCommonProperties.getAsyncInit());

        return DruidDataSourceFactory.createDataSource(properties).getConnection();
    }
    public void importArticleDataByDB(Connection conn, UserWebDto user) {
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

        String whereSql =  " FROM  " +
                "wp_posts p   " +
                "where   (p.post_status = 'private' or p.post_status = 'publish') " +
                "  AND (p.post_type = 'post' or p.post_type = 'page')";
        StringBuilder countSql = new StringBuilder("select count(*) ").append(whereSql);
        StringBuilder listSql = new StringBuilder(infoSql).append(whereSql).append(" order by p.id ASC limit ?,?");
        int count = 0;
        try(PreparedStatement stat = conn.prepareStatement(countSql.toString());
            ResultSet resultSet1 = stat.executeQuery(); ) {
            if (resultSet1.next()) {
                count = resultSet1.getInt(1);
                log.warn("当前总数量 {} ", count);
            }
        } catch (SQLException e) {
            log.error("数据库文章总数解析异常", e);
            throw new BusinessException("数据库文章总数解析异常");
        }
        int pageIndex = 0;
        int pageSize = ARTICLE_PAGE_SIZE;
        BlogArticleDto articleDto = null;
        String tagStr = null;
        String category = null;
        String password = null;
        String status = null;
        String type =null;
        String commentMark = null;
        String commentHtml = null;
        BlogCategoryDto categoryDto = null;
        Long aid = null;
        int commentCount = 0;
        CommentDto commentDto = null;
        ResultSet resultSet = null;
        ResultSet ccountRS = null;
        ResultSet cListRS = null;
        while (count>0 && count>=pageIndex) {
            try(PreparedStatement pstmt = conn.prepareStatement(String.format(listSql.toString()));
            ) {
                pstmt.setInt(1,pageIndex);
                pstmt.setInt(2,pageSize);
                resultSet = pstmt.executeQuery();
                while (resultSet.next()) {
                    articleDto = new BlogArticleDto();
                    tagStr = resultSet.getString("tags");
                    category = resultSet.getString("category");
                    aid = resultSet.getLong("id");
                    status = resultSet.getString("post_status");
                    password = resultSet.getString("post_password");
                    type = resultSet.getString("post_type");
                    commentMark = resultSet.getString("post_content_filtered");
                    commentHtml = resultSet.getString("post_content");
                    if (StringUtils.isBlank(commentMark)){
                        commentMark = RemarkUtils.htmlToMarkdown(commentHtml);
                    }
                    articleDto.setType(type.equalsIgnoreCase("page")? 2: 1);
                    articleDto.setTitle(resultSet.getString("post_title"));
                    articleDto.setPermaLink(resultSet.getString("post_name"));
                    articleDto.setContent(commentMark);
                    articleDto.setContentHtml(commentHtml);
                    articleDto.setViewCount(Long.valueOf(resultSet.getInt("post_views")));
                    articleDto.setCanonicalLink(resultSet.getString("canonical"));
                    articleDto.setDeleted(false);
                    articleDto.setPublished(true);
                    articleDto.setStatus(BlogArticleStatus.PUBLIC.name());
                    if (status.equalsIgnoreCase("private")) {
                        articleDto.setStatus(BlogArticleStatus.PRIVATE.name());
                    }
                    if (StringUtils.isNotBlank(password)) {
                        articleDto.setStatus(BlogArticleStatus.ENCRYPTION.name());
                        articleDto.setPassword(password);
                    }
                    articleDto.setPublishedDate(resultSet.getTimestamp("post_date"));
                    articleDto.setCreatedDate(resultSet.getTimestamp("post_date"));
                    articleDto.setLastModifiedDate(resultSet.getTimestamp("post_modified"));
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
                    importComentDataByDB(conn,ccountRS,cListRS,aid,user.getId(), articleDto.getCommentAgentId(),commentCount,commentDto);

                }
            } catch (SQLException e) {
                log.error("数据库文章查询列表解析异常:", e);
                throw new BusinessException("数据库文章查询列表解析异常");
            } finally {
                if (resultSet!=null) {
                    try {
                        resultSet.close();
                    } catch (SQLException e) {
                        log.error("数据库文章查询列表解析异常2: ", e);
                        throw new BusinessException("数据库文章查询列表解析异常");
                    }
                }
            }

            pageIndex += pageSize;
        }
        if (count >0) {
            List<String> list = myCommonMapper.findAllBlogPostLink();
            BloomCacheFilter.refresh(list);
        }
    }

    public void importComentDataByDB(Connection conn,ResultSet ccountRS, ResultSet cListRS,Long aid,Long userId,Long agentId, int commentCount ,CommentDto commentDto) {
        String commentCountSql = "SELECT count(*) FROM  wp_comments wc  where  comment_post_ID  = ?;";
        String commentListSql = "SELECT * FROM  wp_comments wc  where  comment_post_ID  = ? order by comment_id ASC limit ?,?;";
        try(PreparedStatement pstmt = conn.prepareStatement(commentCountSql);) {
            pstmt.setLong(1,aid);
            ccountRS =  pstmt.executeQuery();
            if (ccountRS.next()) {
                commentCount = ccountRS.getInt(1);
                log.warn("当前评论总数量 {} ", commentCount);
            }
        } catch (SQLException e){
            log.error("数据库评论总数解析异常", e);
            throw new BusinessException("数据库评论总数解析异常");
        } finally {
            if (ccountRS!=null) {
                try {
                    ccountRS.close();
                } catch (SQLException e) {
                    log.error("数据库评论总数解析异常2 ", e);
                    throw new BusinessException("数据库评论总数解析异常");
                }
            }
        }
        int cpageIndex = 0;
        int cpageSize = COMMENT_PAGE_SIZE;
        Long cParent = 0L;
        Long cUserId = 0L;
        String type= null;
        UserWebDto userWebDto = null;
        while (commentCount>0 && commentCount >= cpageIndex) {
            try(PreparedStatement pstmt = conn.prepareStatement(commentListSql);) {
                pstmt.setLong(1,aid);
                pstmt.setInt(2,cpageIndex);
                pstmt.setInt(3,cpageSize);
                cListRS = pstmt.executeQuery();
                while (cListRS.next()) {
                    commentDto = new CommentDto();
                    cUserId = cListRS.getLong("user_id");
                    cParent = cListRS.getLong("comment_parent");
                    type = cListRS.getString("comment_type");
                    commentDto.setId(cListRS.getLong("comment_id"));
                    commentDto.setAuthorName(cListRS.getString("comment_author"));
                    commentDto.setAuthorEmail(cListRS.getString("comment_author_email"));
                    commentDto.setAuthorUrl(cListRS.getString("comment_author_url"));
                    commentDto.setAuthorIp(cListRS.getString("comment_author_IP"));
                    commentDto.setAgent(cListRS.getString("comment_agent"));
                    commentDto.setContent(cListRS.getString("comment_content"));
                    commentDto.setCreatedDate(cListRS.getDate("comment_date"));
                    commentDto.setLastModifiedDate(cListRS.getDate("comment_date"));
                    commentDto.setTargetId(agentId);
                    commentDto.setStatus(CommentStatus.ENROLLED.name());
                    commentDto.setType(CommentType.GENERAL.name());
                    if (StringUtils.isNotBlank(type) && type.equalsIgnoreCase("pingback")) {
                        commentDto.setType(CommentType.PINGBACK.name());
                    }
                    if (cUserId!=null && cUserId.longValue() > 0 && cUserId.equals(1L)) {
                        userWebDto = new UserWebDto();
                        userWebDto.setId(userId);
                        commentDto.setUser(userWebDto);
                    }
                    if (cParent!=null && cParent.longValue() >0 ) {
                        commentDto.setParentId(Long.valueOf(cParent));
                    }
                    commentService.saveCommentFromDb(commentDto);
                }
            } catch (SQLException e) {
                log.error("数据库评论列表解析异常", e);
                throw new BusinessException("数据库评论列表解析异常");
            } finally {
                if (cListRS!=null) {
                    try {
                        cListRS.close();
                    } catch (SQLException e) {
                        log.error("数据库评论列表解析异常2 ", e);
                        throw new BusinessException("数据库评论列表解析异常");
                    }
                }
            }
            cpageIndex += cpageSize;
        }
    }
    public String getUrl(BlogMove blogMove) {
        return String.format(URL, blogMove.getIp(), blogMove.getPort(), blogMove.getDatabase());
    }
    public String getDriver() {
        return MYSQL_DRIVER;
    }


}
