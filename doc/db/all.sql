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
  PRIMARY KEY ( `article_id`,`tag_id` ),
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




-- qycmsdev.sys_role definition
drop table if exists `sys_role`;
CREATE TABLE `sys_role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT comment 'id',
  `name` varchar(255) DEFAULT NULL comment '名称',
  `role_type` varchar(255) DEFAULT NULL comment '角色类型',
  `remark` varchar(255) DEFAULT NULL comment '备注',
  `created_by` bigint(20) DEFAULT null comment '创建者',
  `last_modified_by` bigint(20) DEFAULT NULL comment '更新者',
  `created_date` datetime DEFAULT now() comment '创建时间',
  `last_modified_date` datetime DEFAULT now() comment '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='角色';


Insert into sys_role (ID,NAME,ROLE_TYPE,CREATED_BY,LAST_MODIFIED_BY) values (@v_superuser_role_id,'超级管理员','SYSTEM',@v_system_user_id,@v_system_user_id);


-- qycmsdev.sys_role_privilege definition
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


-- qycmsdev.sys_privilege definition
drop table if exists `sys_permission`;
CREATE TABLE `sys_permission` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
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
