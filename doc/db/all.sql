
SET FOREIGN_KEY_CHECKS = 0;
SET AUTOCOMMIT = 0;
-- 用户
drop table if exists `sys_user`;
create table `sys_user` (
  `id` bigint(20) not null auto_increment comment 'id',
  `username` varchar(50) not null comment '用户名',
  `nickname` varchar(50) comment '昵称',
  `password` varchar(255) not null comment '密码',
  `salt` varchar(50) comment '盐值',
  `email` varchar(255) comment '邮箱',
  `avatar` varchar(255) comment '用户头像',
  `disable_date` datetime DEFAULT null comment '禁用时间',
  `disable` bit(1) DEFAULT b'0' comment '是否禁用：0不禁用， 1 禁用',
  `deleted` bit(1) DEFAULT b'0' comment '是否删除：0不删除， 1 删除',
  `created_by` bigint(20)   comment '创建者',
  `last_modified_by` bigint(20)   comment '更新者',
  `created_date` datetime comment '创建时间',
  `last_modified_date` datetime comment '更新时间',
  primary key (`id`),
  unique key `username_unique` (`username`),
  unique key `email_unique` (`email`)
) engine=innodb default charset=utf8mb4 comment='用户';
# INSERT INTO `sys_user` (`id`, `created_date`, `last_modified_date`, `avatar`, `email`, `deleted`, `disable`, `nickname`, `password`, `salt`, `username`, `created_by`, `last_modified_by`) VALUES (1, '2018-9-7 03:14:53', '2018-11-4 03:58:11', '/content/upload/2018/11/04/e246ba56-81aa-4226-8189-cc2d10ccd56a.jpg', '1@qq.com', 0, 0, '测试', 'e68b0a6751860afed4938951be1ef002ee697d04', 'b8314a417d4037731ad10f92c25dc911fad829094a22', 'admin', 1, 1);
INSERT INTO `sys_user` (`id`, `created_date`, `last_modified_date`, `avatar`, `email`, `deleted`, `disable`, `nickname`, `password`, `salt`, `username`, `created_by`, `last_modified_by`) VALUES (1, now(), now(), '/content/upload/2018/11/04/e246ba56-81aa-4226-8189-cc2d10ccd56a.jpg', '1@qq.com', 0, 0, '测试', 'e68b0a6751860afed4938951be1ef002ee697d04', 'b8314a417d4037731ad10f92c25dc911fad829094a22', 'admin', 1, 1);




select min(id) into @v_system_user_id from sys_user;







-- 文章
drop table if exists `blog_article`;
CREATE TABLE `blog_article` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT comment 'id',
  `title` varchar(255) DEFAULT NULL comment '标题',
  `content` longtext comment '内容-mkdown',
  `content_html` longtext comment '内容-html',
  `perma_link` varchar(255) DEFAULT NULL comment '链接',
  `canonical_link` varchar(255) DEFAULT NULL comment '规范链接',
  `summary` varchar(255) DEFAULT NULL comment '摘要',
  `thumbnail` varchar(255) DEFAULT NULL comment '缩略图',
  `type` int(11) DEFAULT 1 comment '类型：1：文章，2：页面',
  `view_count` bigint(20) DEFAULT '0' comment '浏览人数',
  `published_date` datetime DEFAULT NULL comment '发布日期',
  `author_id` bigint(20) DEFAULT NULL comment '作者id',
  `category_id` bigint(20) DEFAULT NULL comment '所属分类',
  `comment_agent_id` bigint(20) DEFAULT NULL comment '评论组件id',
  `deleted` bit(1) DEFAULT b'0' comment '是否删除：0不删除， 1 删除',
  `published` bit(1) DEFAULT b'0' comment '是否发布：0不发布， 1 发布',
  `created_by` bigint(20) comment '创建者',
  `last_modified_by` bigint(20) comment '更新者',
  `created_date` datetime comment '创建时间',
  `last_modified_date` datetime comment '更新时间',
  PRIMARY KEY (`id`),
  KEY `blog_article_author_id_IDX` (`author_id`),
  KEY `blog_article_type_and_category_id_IDX` (`type`,`category_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8mb4 comment='文章';


-- 文章访客
drop table if exists `blog_article_visitor`;
CREATE TABLE `blog_article_visitor` (
`id` bigint(20) NOT NULL AUTO_INCREMENT comment 'id',
`article_id` bigint(20) NOT NULL comment '文章id',
`visitor_agent` varchar(255) DEFAULT NULL comment '访客客户端',
`visitor_ip` varchar(255) DEFAULT NULL comment '访客ip',
`created_date` datetime comment '创建时间',
`last_modified_date` datetime comment '更新时间',
 PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8mb4 comment='文章浏览量详情';

-- 标签
drop table if exists `blog_tag`;
CREATE TABLE `blog_tag` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT comment 'id',
  `name` varchar(255) DEFAULT NULL comment '名称',
  `identifier` varchar(255) DEFAULT NULL comment '别名',
  `created_by` bigint(20) comment '创建者',
  `last_modified_by` bigint(20) comment '更新者',
  `created_date` datetime comment '创建时间',
  `last_modified_date` datetime comment '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 comment='文章标签';

-- 文章-标签关系表
drop table if exists `blog_article_tag`;
CREATE TABLE `blog_article_tag` (
  `article_id` bigint(20) NOT NULL comment '文章id',
  `tag_id` bigint(20) NOT NULL comment '标签id',
  PRIMARY KEY ( `article_id`,`tag_id` )
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 comment='文章-标签关系表';


-- 文章分类
drop table if exists `blog_category`;
CREATE TABLE `blog_category` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT comment 'id',
  `name` varchar(255) DEFAULT NULL comment '名称',
  `description` varchar(255) DEFAULT NULL comment '描述',
  `identifier` varchar(255) DEFAULT NULL comment '别名',
  `id_path` varchar(1000) DEFAULT NULL comment ' id路径',
  `name_path` varchar(1000) DEFAULT NULL comment '名称路径',
  `display_order` bigint(20) DEFAULT NULL comment '显示顺序',
  `parent_id` bigint(20) DEFAULT NULL comment '父级分类',
  `deleted` bit(1) DEFAULT b'0' comment '是否删除：0不删除， 1 删除',
  `created_by` bigint(20) DEFAULT NULL comment '创建者',
  `last_modified_by` bigint(20) DEFAULT NULL comment '更新者',
  `created_date` datetime DEFAULT NULL comment '创建时间',
  `last_modified_date` datetime DEFAULT NULL comment '更新时间',
  PRIMARY KEY (`id`),
  KEY `FK18qsxukvf40pdippprno5eq5b` (`parent_id`),
  CONSTRAINT `FK18qsxukvf40pdippprno5eq5b` FOREIGN KEY (`parent_id`) REFERENCES `blog_category` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 comment='文章分类';


-- 文章分类树
drop table if exists `blog_category_tree`;
CREATE TABLE `blog_category_tree` (
  `parent_id` bigint(20) NOT NULL COMMENT '父级分类id',
  `child_id` bigint(20) NOT NULL COMMENT '子级分类id',
  `distance` int(11) NOT NULL COMMENT '层级: 处于第几级分类',
  `child_count` bigint(20) DEFAULT 0 COMMENT '子分类数',
  PRIMARY KEY (`parent_id`,`child_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='文章分类树';



INSERT INTO blog_category (id,name,description,identifier,id_path,name_path,display_order,parent_id,deleted,created_by,last_modified_by,created_date,last_modified_date) VALUES
(1,'默认分类',NULL,'default','1','默认分类',1,NULL,0,NULL,NULL,'2020-06-30 04:02:17.0','2020-06-30 04:02:17.0');
INSERT INTO blog_category_tree (parent_id,child_id,distance,child_count) VALUES
(1,1,0,1);




-- 资源
drop table if exists `sys_privilege`;
CREATE TABLE `sys_privilege` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT comment 'id',
  `name` varchar(255) DEFAULT NULL comment '资源名称',
  `url` varchar(255) DEFAULT NULL comment '资源路径',
  `description` varchar(255) DEFAULT NULL comment '描述，界面UI显示字段',
  `identifier` varchar(255) DEFAULT NULL comment '权限字符串',
  `availabled` bit(1) DEFAULT 1 comment '是否可用,如果不可用将不会添加给用户 0 不可用，1 可用',
  `hided` bit(1) DEFAULT 0 comment '是否隐藏, 0 不隐藏，1 隐藏',
  `parent_id` bigint(20) DEFAULT NULL comment '父级 id',
  `type` varchar(255) DEFAULT NULL comment '类型',
  `created_by` bigint(20) DEFAULT null comment '创建者',
  `last_modified_by` bigint(20) DEFAULT NULL comment '更新者',
  `created_date` datetime DEFAULT now() comment '创建时间',
  `last_modified_date` datetime DEFAULT now() comment '更新时间',
  PRIMARY KEY (`id`),
  KEY `FK7h567kbm8i2uteel1pwveyamj` (`parent_id`),
  CONSTRAINT `FK7h567kbm8i2uteel1pwveyamj` FOREIGN KEY (`parent_id`) REFERENCES `sys_privilege` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='资源';


Insert into sys_privilege (IDENTIFIER,NAME,description,PARENT_ID) values ('SYSTEM','系统管理','系统管理',null);
select id into @parent_id from sys_privilege where IDENTIFIER='SYSTEM';
Insert into sys_privilege (IDENTIFIER,NAME,description,PARENT_ID) values ('SYSTEM:PERMISSION','授权管理','授权管理',@parent_id);
select id into @c_parent_id from sys_privilege where IDENTIFIER='SYSTEM:PERMISSION';
Insert into sys_privilege (IDENTIFIER,NAME,description,PARENT_ID) values ('SYSTEM:PERMISSION:VIEW','查看授权','查看授权',@c_parent_id);
Insert into sys_privilege (IDENTIFIER,NAME,description,PARENT_ID) values ('SYSTEM:PERMISSION:ADD','添加授权','添加授权',@c_parent_id);
Insert into sys_privilege (IDENTIFIER,NAME,description,PARENT_ID) values ('SYSTEM:PERMISSION:EDIT','修改授权','修改授权',@c_parent_id);
Insert into sys_privilege (IDENTIFIER,NAME,description,PARENT_ID) values ('SYSTEM:PERMISSION:REMOVE','删除授权','删除授权',@c_parent_id);

Insert into sys_privilege (IDENTIFIER,NAME,description,PARENT_ID) values ('SYSTEM:ROLE','角色管理','角色管理',@parent_id);
select id into @c_parent_id from sys_privilege where IDENTIFIER='SYSTEM:ROLE';
Insert into sys_privilege (IDENTIFIER,NAME,description,PARENT_ID) values ('SYSTEM:ROLE:VIEW','查看角色','查看角色',@c_parent_id);
Insert into sys_privilege (IDENTIFIER,NAME,description,PARENT_ID) values ('SYSTEM:ROLE:ADD','添加角色','添加角色',@c_parent_id);
Insert into sys_privilege (IDENTIFIER,NAME,description,PARENT_ID) values ('SYSTEM:ROLE:EDIT','修改角色','修改角色',@c_parent_id);
Insert into sys_privilege (IDENTIFIER,NAME,description,PARENT_ID) values ('SYSTEM:ROLE:REMOVE','删除角色','删除角色',@c_parent_id);

Insert into sys_privilege (IDENTIFIER,NAME,description,PARENT_ID) values ('SYSTEM:USER','用户管理','用户管理',@parent_id);
select id into @c_parent_id from sys_privilege where IDENTIFIER='SYSTEM:USER';
Insert into sys_privilege (IDENTIFIER,NAME,description,PARENT_ID) values ('SYSTEM:USER:VIEW','查看用户','查看用户',@c_parent_id);
Insert into sys_privilege (IDENTIFIER,NAME,description,PARENT_ID) values ('SYSTEM:USER:ADD','添加用户','添加用户',@c_parent_id);
Insert into sys_privilege (IDENTIFIER,NAME,description,PARENT_ID) values ('SYSTEM:USER:EDIT','修改用户','修改用户',@c_parent_id);
Insert into sys_privilege (IDENTIFIER,NAME,description,PARENT_ID) values ('SYSTEM:USER:REMOVE','删除用户','删除用户',@c_parent_id);

Insert into sys_privilege (IDENTIFIER,NAME,description,PARENT_ID) values ('BLOG','博客管理','博客管理',null);
select id into @parent_id from sys_privilege where IDENTIFIER='BLOG';
Insert into sys_privilege (IDENTIFIER,NAME,description,PARENT_ID) values ('BLOG:ARTICLE','文章管理','文章管理',@parent_id);
select id into @c_parent_id from sys_privilege where IDENTIFIER='BLOG:ARTICLE';
Insert into sys_privilege (IDENTIFIER,NAME,description,PARENT_ID) values ('BLOG:ARTICLE:VIEW','查看文章','查看文章',@c_parent_id);
Insert into sys_privilege (IDENTIFIER,NAME,description,PARENT_ID) values ('BLOG:ARTICLE:ADD','添加文章','添加文章',@c_parent_id);
Insert into sys_privilege (IDENTIFIER,NAME,description,PARENT_ID) values ('BLOG:ARTICLE:EDIT','修改文章','修改文章',@c_parent_id);
Insert into sys_privilege (IDENTIFIER,NAME,description,PARENT_ID) values ('BLOG:ARTICLE:REMOVE','删除文章','删除文章',@c_parent_id);

Insert into sys_privilege (IDENTIFIER,NAME,description,PARENT_ID) values ('BLOG:CATEGORY','分类管理','分类管理',@parent_id);
select id into @c_parent_id from sys_privilege where IDENTIFIER='BLOG:CATEGORY';
Insert into sys_privilege (IDENTIFIER,NAME,description,PARENT_ID) values ('BLOG:CATEGORY:VIEW','查看分类','查看分类',@c_parent_id);
Insert into sys_privilege (IDENTIFIER,NAME,description,PARENT_ID) values ('BLOG:CATEGORY:ADD','添加分类','添加分类',@c_parent_id);
Insert into sys_privilege (IDENTIFIER,NAME,description,PARENT_ID) values ('BLOG:CATEGORY:EDIT','修改分类','修改分类',@c_parent_id);
Insert into sys_privilege (IDENTIFIER,NAME,description,PARENT_ID) values ('BLOG:CATEGORY:REMOVE','删除分类','删除分类',@c_parent_id);

Insert into sys_privilege (IDENTIFIER,NAME,description,PARENT_ID) values ('BLOG:TAG','标签管理','标签管理',@parent_id);
select id into @c_parent_id from sys_privilege where IDENTIFIER='BLOG:TAG';
Insert into sys_privilege (IDENTIFIER,NAME,description,PARENT_ID) values ('BLOG:TAG:VIEW','查看标签','查看标签',@c_parent_id);
Insert into sys_privilege (IDENTIFIER,NAME,description,PARENT_ID) values ('BLOG:TAG:ADD','添加标签','添加标签',@c_parent_id);
Insert into sys_privilege (IDENTIFIER,NAME,description,PARENT_ID) values ('BLOG:TAG:EDIT','修改标签','修改标签',@c_parent_id);
Insert into sys_privilege (IDENTIFIER,NAME,description,PARENT_ID) values ('BLOG:TAG:REMOVE','删除标签','删除标签',@c_parent_id);




-- 角色
drop table if exists `sys_role`;
CREATE TABLE `sys_role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT comment 'id',
  `name` varchar(255) DEFAULT NULL comment '名称',
  `identifier` varchar(255) DEFAULT NULL comment '角色标识字符串',
  `role_type` varchar(255) DEFAULT NULL comment '角色类型',
  `remark` varchar(255) DEFAULT NULL comment '备注',
  `created_by` bigint(20) DEFAULT null comment '创建者',
  `last_modified_by` bigint(20) DEFAULT NULL comment '更新者',
  `created_date` datetime DEFAULT now() comment '创建时间',
  `last_modified_date` datetime DEFAULT now() comment '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='角色';

Insert into sys_role (NAME,ROLE_TYPE,IDENTIFIER,CREATED_BY,LAST_MODIFIED_BY) values ('超级管理员','SYSTEM','admin',@v_system_user_id,@v_system_user_id);
INSERT INTO sys_role (NAME,ROLE_TYPE,IDENTIFIER,CREATED_BY,LAST_MODIFIED_BY) VALUES('用户', 'SYSTEM', 'user',@v_system_user_id,@v_system_user_id);




-- 角色-权限
drop table if exists `sys_role_privilege`;
CREATE TABLE `sys_role_privilege` (
  `role_id` bigint(20) NOT NULL comment '角色id',
  `privilege_id` bigint(20) NOT NULL comment '资源id',
  PRIMARY KEY (`role_id`,`privilege_id`),
  KEY `FKvf9lt66q9oyjludcg83nfinq` (`privilege_id`),
  CONSTRAINT `FKr3sw06rqbrufgrkfq2c8dxhvb` FOREIGN KEY (`role_id`) REFERENCES `sys_role` (`id`),
  CONSTRAINT `FKvf9lt66q9oyjludcg83nfinq` FOREIGN KEY (`privilege_id`) REFERENCES `sys_privilege` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='角色权限表';

select min(id) into @v_superuser_role_id from sys_role;
insert into sys_role_privilege (role_id, privilege_id)
select @v_superuser_role_id as role_id, p.id as privilege_id from sys_privilege p;




-- 授权
drop table if exists `sys_permission`;
CREATE TABLE `sys_permission` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT  comment 'id',
  `user_id` bigint(20) DEFAULT NULL comment '用户',
  `role_id` bigint(20) DEFAULT NULL comment '角色',
  `privilege_id` bigint(20) DEFAULT NULL comment '权限',
  `created_by` bigint(20) DEFAULT null comment '创建者',
  `last_modified_by` bigint(20) DEFAULT NULL comment '更新者',
  `created_date` datetime DEFAULT now() comment '创建时间',
  `last_modified_date` datetime DEFAULT now() comment '更新时间',
  PRIMARY KEY (`id`),
  KEY `FKl0lymq83eg69a9awoqn9sp3pi` (`privilege_id`),
  KEY `FKhidmju68461bmv7st3pfkrhcu` (`role_id`),
  KEY `FK1nk6vdhuhcniyse9wik3pp5l7` (`user_id`),
  CONSTRAINT `FK1nk6vdhuhcniyse9wik3pp5l7` FOREIGN KEY (`user_id`) REFERENCES `sys_user` (`id`),
  CONSTRAINT `FKhidmju68461bmv7st3pfkrhcu` FOREIGN KEY (`role_id`) REFERENCES `sys_role` (`id`),
  CONSTRAINT `FKl0lymq83eg69a9awoqn9sp3pi` FOREIGN KEY (`privilege_id`) REFERENCES `sys_privilege` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='授权表';
# select min(id) into @v_system_user_id from sys_user;
Insert into sys_permission (CREATED_BY,LAST_MODIFIED_BY,PRIVILEGE_ID,ROLE_ID,USER_ID) values (@v_system_user_id,@v_system_user_id,null,@v_superuser_role_id,@v_system_user_id);


-- qycmsdev.sns_comment_agent definition
drop table if exists `sns_comment_agent`;
CREATE TABLE `sns_comment_agent` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT comment 'id',
  `target_id` bigint(20) DEFAULT NULL comment '评论对象id',
  `target_type` varchar(255) DEFAULT NULL comment '评论对象类型',
  `target_name` varchar(255) DEFAULT NULL comment '评论对象名称',
   `enabled` bit(1) DEFAULT b'1' comment '是否开启：0不开启， 1开启',
  `created_by` bigint(20) DEFAULT null comment '创建者',
  `last_modified_by` bigint(20) DEFAULT NULL comment '更新者',
  `created_date` datetime DEFAULT now() comment '创建时间',
  `last_modified_date` datetime DEFAULT now() comment '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `sns_comment_agent_UN` (`target_id`,`target_name`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8mb4 COMMENT='评论包装表';



-- qycmsdev.sns_comment definition
drop table if exists `sns_comment`;
CREATE TABLE `sns_comment` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT comment 'id',
  `agent` varchar(255) DEFAULT NULL comment '评论者客户端',
  `author_name` varchar(255) DEFAULT NULL  comment '评论者用户名',
  `author_email` varchar(255) DEFAULT NULL comment '评论者邮箱',
  `author_ip` varchar(255) DEFAULT NULL comment '评论者ip',
  `author_url` varchar(255) DEFAULT NULL comment '评论者网址',
  `content` longtext  comment '内容',
  `status` varchar(255) DEFAULT 'APPLIED'  comment '评论状态',
  `target_id` bigint(20) DEFAULT NULL comment '评论代理',
  `depth` int(11) DEFAULT NULL comment '评论层级',
  `parent_id` bigint(20) DEFAULT NULL comment '父评论',
  `user_id` bigint(20) DEFAULT 0 comment '用户id, 0 游客',
  `top_parent_id` bigint(20) DEFAULT NULL comment '根回复',
  `created_date` datetime DEFAULT now() comment '创建时间',
  `last_modified_date` datetime DEFAULT now() comment '更新时间',
  PRIMARY KEY (`id`),
  KEY `FKhx4nrk2kessc0qfc7i3n9kt5u` (`parent_id`),
  KEY `FK5fdxbmdylnf81xn1iu12ac9i2` (`target_id`),
  KEY `FKdjdspxwjo7efjfxqrryds8phs` (`top_parent_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8mb4 COMMENT='评论表';


drop table if exists `sys_site_config`;
CREATE TABLE `sys_site_config` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT comment 'id',
  `config_key` varchar(255) DEFAULT NULL comment '站点设置key',
  `config_value` longtext DEFAULT NULL comment '站点设置value',
  `config_name` varchar(255) DEFAULT NULL comment '站点设置名称',
  `config_tip` varchar(255) DEFAULT NULL comment '站点设置提示',
  `type` int(11) DEFAULT NULL comment '配置类型',
  `created_by` bigint(20) DEFAULT null comment '创建者',
  `last_modified_by` bigint(20) DEFAULT NULL comment '更新者',
  `created_date` datetime DEFAULT now() comment '创建时间',
  `last_modified_date` datetime DEFAULT now() comment '更新时间',
   PRIMARY KEY (`id`),
  UNIQUE KEY (config_key),
  KEY sys_site_config_type_IDX (`type`)  USING BTREE
) ENGINE=InnoDB  DEFAULT CHARSET=utf8mb4 COMMENT='站点配置表';

#  select min(id) into @v_system_user_id from sys_user;
Insert into sys_site_config (config_key,config_value,config_name,type,created_by,last_modified_by)
values('site_name','青语博客','站点名称',1,@v_system_user_id,@v_system_user_id) ;
Insert into sys_site_config (config_key,config_value,config_name,config_tip,type,created_by,last_modified_by)
values('site_url','','站点地址','',1,@v_system_user_id,@v_system_user_id) ;
Insert into sys_site_config (config_key,config_value,config_name,config_tip,type,created_by,last_modified_by)
values('site_key','Java,Web','关键词','主页关键词元信息，SEO友好(多个关键词以英文逗号分隔)',1,@v_system_user_id,@v_system_user_id) ;
Insert into sys_site_config (config_key,config_value,config_name,config_tip, type,created_by,last_modified_by)
values('site_description','这是一个springboot的博客','站点描述','主页描述元信息，SEO友好',1,@v_system_user_id,@v_system_user_id);
Insert into sys_site_config (config_key,config_value,config_name,config_tip,type,created_by,last_modified_by)
values('site_icon','https://windcoder.com/wp-content/uploads/2015/08/favicon-1.gif','Favicon','站点 ICON 图标',1,@v_system_user_id,@v_system_user_id) ;
Insert into sys_site_config (config_key,config_value,config_name,config_tip,type,created_by,last_modified_by)
values('site_logo','https://windcoder.com/wp-content/uploads/2017/02/logo_vift.png','网站Logo','请上传PNG图片作为网站Logo',1,@v_system_user_id,@v_system_user_id);
Insert into sys_site_config (config_key,config_value,config_name,config_tip,type,created_by,last_modified_by)
values('site_small_logo','https://windcoder.com/wp-content/uploads/2017/02/logo_vif_small.png','网站小Logo','请上传PNG图片作为网站小Logo',1,@v_system_user_id,@v_system_user_id);

Insert into sys_site_config (config_key,config_value,config_name,config_tip,type,created_by,last_modified_by)
values('site_beian','','页脚备案文字','工信部域名备案号码',1,@v_system_user_id,@v_system_user_id) ;
Insert into sys_site_config (config_key,config_value,config_name,config_tip,type,created_by,last_modified_by)
values('site_open_date','2013-07-17','建站日期','网站开放的日期, 使用`YYYY-mm-dd`格式',1,@v_system_user_id,@v_system_user_id) ;

Insert into sys_site_config (config_key,config_value,config_name,config_tip,type,created_by,last_modified_by)
values('site_comment_flag','true','允许评论','站点评论总开关',2,@v_system_user_id,@v_system_user_id);
Insert into sys_site_config (config_key,config_value,config_name,config_tip,type,created_by,last_modified_by)
values('site_home_undisplay_cats','','不显示的分类ID列表','分类ID数字之间用英文逗号分隔, 如果留空将展示所有分类到文章',2,@v_system_user_id,@v_system_user_id);
Insert into sys_site_config (config_key,config_value,config_name,config_tip,type,created_by,last_modified_by)
values('site_head_code','','页头自定义代码','页面头部加载的自定义代码，位于head标签结束前',2,@v_system_user_id,@v_system_user_id);
Insert into sys_site_config (config_key,config_value,config_name,config_tip,type,created_by,last_modified_by)
values('site_foot_code','','页脚自定义代码','页面底部加载的自定义代码，位于body标签结束前',2,@v_system_user_id,@v_system_user_id);

Insert into sys_site_config (config_key,config_value,config_name,config_tip,type,created_by,last_modified_by)
values('site_desc','','个人描述','个人一句话说明',3,@v_system_user_id,@v_system_user_id);
Insert into sys_site_config (config_key,config_value,config_name,config_tip,type,created_by,last_modified_by)
values('site_qq','','网站QQ','站点服务专属QQ号码',3,@v_system_user_id,@v_system_user_id);
Insert into sys_site_config (config_key,config_value,config_name,config_tip,type,created_by,last_modified_by)
values('site_qq_group','','网站QQ群 ID Key','站点专属服务QQ群加群链接的ID Key, 非群号, 至`http://shang.qq.com`获取',3,@v_system_user_id,@v_system_user_id);
Insert into sys_site_config (config_key,config_value,config_name,config_tip,type,created_by,last_modified_by)
values('site_weibo','','网站微博名','站点服务专属微博用户名',3,@v_system_user_id,@v_system_user_id);
Insert into sys_site_config (config_key,config_value,config_name,config_tip,type,created_by,last_modified_by)
values('site_weixin_qr','','网站微信','站点服务专属微信号的二维码图片(可以是公众号二维码)',3,@v_system_user_id,@v_system_user_id);
Insert into sys_site_config (config_key,config_value,config_name,config_tip,type,created_by,last_modified_by)
values('site_mailme_id','','QQ邮我按钮ID','QQ邮我链接内的ID字段, 访问`http://open.mail.qq.com`获取',3,@v_system_user_id,@v_system_user_id);

Insert into sys_site_config (config_key,config_value,config_name,config_tip,type,created_by,last_modified_by)
values('site_pay_flag','true','启用赞赏','赞赏开关，开启后展示收款二维码',4,@v_system_user_id,@v_system_user_id);
Insert into sys_site_config (config_key,config_value,config_name,config_tip,type,created_by,last_modified_by)
values('site_weixin_pay_qr','','网站微信收款二维码','用于网站收集打赏等的微信收款二维码图片(赞助站长小工具等使用)',4,@v_system_user_id,@v_system_user_id);
Insert into sys_site_config (config_key,config_value,config_name,config_tip,type,created_by,last_modified_by)
values('site_alipay_pay_qr','','网站支付宝收款二维码','用于网站收集打赏等的支付宝收款二维码图片(赞助站长小工具等使用)',4,@v_system_user_id,@v_system_user_id);


drop table if exists `sys_menus_agent`;
CREATE TABLE `sys_menus_agent` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT comment 'id',
  `name` varchar(255) DEFAULT NULL comment '菜单组名称',
  `type` int(11) DEFAULT 0  comment '菜单位置：0未启用， 1顶部，2页尾',
  `created_by` bigint(20) DEFAULT null comment '创建者',
  `last_modified_by` bigint(20) DEFAULT NULL comment '更新者',
  `created_date` datetime DEFAULT now() comment '创建时间',
  `last_modified_date` datetime DEFAULT now() comment '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8mb4 COMMENT='菜单包装表';

select min(id) into @v_system_user_id from sys_user;
Insert into sys_menus_agent (name,type,created_by,last_modified_by)
values('顶部菜单',1,@v_system_user_id,@v_system_user_id) ;
Insert into sys_menus_agent (name,type,created_by,last_modified_by)
values('页脚菜单',2,@v_system_user_id,@v_system_user_id) ;


drop table if exists `sys_menus`;
CREATE TABLE `sys_menus` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT comment 'id',
  `name` varchar(255) DEFAULT NULL comment '菜单名称',
  `url` varchar(255) DEFAULT NULL comment '菜单地址',
  `blanked` bit(1) DEFAULT b'1' comment '是否新窗口打开：0否， 1是',
  `display_order` int(11) DEFAULT 0 comment '排序',
  `parent_id` bigint(1) DEFAULT NULL comment '父级菜单',
  `target_id` bigint(20) DEFAULT NULL comment '菜单代理',
  `created_by` bigint(20) DEFAULT null comment '创建者',
  `last_modified_by` bigint(20) DEFAULT NULL comment '更新者',
  `created_date` datetime DEFAULT now() comment '创建时间',
  `last_modified_date` datetime DEFAULT now() comment '更新时间',
  PRIMARY KEY (`id`),
  KEY `sys_menus_target_id_IDX` (`target_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8mb4 COMMENT='菜单表';

drop table if exists `sys_link`;
CREATE TABLE `sys_link` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT comment 'id',
  `name` varchar(255) DEFAULT NULL comment '链接名称',
  `url` varchar(255) DEFAULT NULL comment '链接地址',
  `show_index` bit(1) DEFAULT b'0' comment '是否首页外链：0不是， 1是',
  `description` varchar(255) DEFAULT NULL comment '描述',
  `created_by` bigint(20) DEFAULT null comment '创建者',
  `last_modified_by` bigint(20) DEFAULT NULL comment '更新者',
  `created_date` datetime DEFAULT now() comment '创建时间',
  `last_modified_date` datetime DEFAULT now() comment '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8mb4 COMMENT='链接表';


drop table if exists `sys_short_link`;
CREATE TABLE `sys_short_link` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT comment 'id',
  `identifier` varchar(255) DEFAULT NULL comment '短链接',
  `url` varchar(2048) DEFAULT NULL comment '链接地址',
  `description` varchar(255) DEFAULT NULL comment '描述',
  `created_by` bigint(20) DEFAULT null comment '创建者',
  `last_modified_by` bigint(20) DEFAULT NULL comment '更新者',
  `created_date` datetime DEFAULT now() comment '创建时间',
  `last_modified_date` datetime DEFAULT now() comment '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8mb4 COMMENT='短链接表';

commit;
-- end;

SET FOREIGN_KEY_CHECKS = 1;
SET AUTOCOMMIT = 1;