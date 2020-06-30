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
INSERT INTO `sys_user` (`id`, `created_date`, `last_modified_date`, `avatar`, `email`, `deleted`, `disable`, `nickname`, `password`, `salt`, `username`, `created_by`, `last_modified_by`) VALUES (1, '2018-9-7 03:14:53', '2018-11-4 03:58:11', '/content/upload/2018/11/04/e246ba56-81aa-4226-8189-cc2d10ccd56a.jpg', '1@qq.com', 0, 0, '测试', 'e68b0a6751860afed4938951be1ef002ee697d04', 'b8314a417d4037731ad10f92c25dc911fad829094a22', 'admin', 1, 1);


-- 文章
drop table if exists `blog_article`;
CREATE TABLE `blog_article` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT comment 'id',
  `title` varchar(255) DEFAULT NULL comment '标题',
  `content` longtext comment '内容-mkdown',
  `content_html` longtext comment '内容-html',
  `perma_link` varchar(255) DEFAULT NULL comment '链接',
  `summary` varchar(255) DEFAULT NULL comment '摘要',
  `thumbnail` varchar(255) DEFAULT NULL comment '缩略图',
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
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8mb4 comment='文章';


-- 标签
drop table if exists `blog_tag`;
CREATE TABLE `blog_tag` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT comment 'id',
  `name` varchar(255) DEFAULT NULL comment '名称',
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
  KEY `FKrrs2kus3wx0wfxgdn3qrinpt7` (`tag_id`),
  KEY `FKjirukkkkhj5pkpn9i7x0gn83b` (`article_id`),
  CONSTRAINT `FKjirukkkkhj5pkpn9i7x0gn83b` FOREIGN KEY (`article_id`) REFERENCES `blog_article` (`id`),
  CONSTRAINT `FKrrs2kus3wx0wfxgdn3qrinpt7` FOREIGN KEY (`tag_id`) REFERENCES `blog_tag` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 comment='文章-标签关系表';


-- 文章分类
drop table if exists `blog_category`;
CREATE TABLE `blog_category` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT comment 'id',
  `name` varchar(255) DEFAULT NULL comment '名称',
  `description` varchar(255) DEFAULT NULL comment '描述',
  `key_word` varchar(255) DEFAULT NULL comment '关键词',
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



INSERT INTO blog_category (id,name,description,key_word,id_path,name_path,display_order,parent_id,deleted,created_by,last_modified_by,created_date,last_modified_date) VALUES
(1,'默认分类',NULL,NULL,'1','默认分类',1,NULL,0,NULL,NULL,'2020-06-30 04:02:17.0','2020-06-30 04:02:17.0');
INSERT INTO blog_category_tree (parent_id,child_id,distance,child_count) VALUES
(1,1,0,1);