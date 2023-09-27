--  *********************************************************************
--  Update Database Script
--  *********************************************************************
--  Change Log: db/liquibase/changelog-master.yaml
--  Ran at: 18-12-5 下午4:57
--  Against: qycmsdev@localhost@jdbc:MySQL://localhost:3306/qycmsdev?characterEncoding=utf8&useSSL=false
--  Liquibase version: 3.6.2
--  *********************************************************************

--  Lock Database
UPDATE qycmsdev.DATABASECHANGELOGLOCK SET `LOCKED` = 1, LOCKEDBY = 'DESKTOP-SI04Q2I (172.19.102.97)', LOCKGRANTED = '2018-12-05 16:57:26.107' WHERE ID = 1 AND `LOCKED` = 0;

--  Changeset db/liquibase/changelog-master.yaml::initData::windcoder
INSERT INTO `sys_role` ( `name`, `remark`,display_order,created_date,last_modified_date,created_by, last_modified_by) VALUES ('超级管理员', '超级管理者',1,now(),now(),2,2);

INSERT INTO `sys_role` ( `name`, `remark`,display_order,created_date,last_modified_date,created_by, last_modified_by) VALUES ('超级管理员2', '超级管理者2',2,now(),now(),2,2);

INSERT INTO `sys_role` ( `name`, `remark`,display_order,created_date,last_modified_date,created_by, last_modified_by) VALUES ('超级管理员22', '超级管理者4',3,now(),now(),2,2);

INSERT INTO qycmsdev.DATABASECHANGELOG (ID, AUTHOR, FILENAME, DATEEXECUTED, ORDEREXECUTED, MD5SUM, `DESCRIPTION`, COMMENTS, EXECTYPE, CONTEXTS, LABELS, LIQUIBASE, DEPLOYMENT_ID) VALUES ('initData', 'windcoder', 'db/liquibase/changelog-master.yaml', NOW(), 3, '8:f640c948873a3290d000ba8631ca6ea4', 'sqlFile', '', 'EXECUTED', NULL, NULL, '3.6.2', '4000247719');

--  Changeset db/liquibase/mysql/changelog-mysql-master.yaml::initData::windcoder
INSERT INTO `sys_role` ( `name`, `remark`,display_order,created_date,last_modified_date,created_by, last_modified_by) VALUES ('管理员', '管理者',2,now(),now(),2,2);

INSERT INTO `sys_role` ( `name`, `remark`,display_order,created_date,last_modified_date,created_by, last_modified_by) VALUES ('用户', '用户',3,now(),now(),2,2);

INSERT INTO qycmsdev.DATABASECHANGELOG (ID, AUTHOR, FILENAME, DATEEXECUTED, ORDEREXECUTED, MD5SUM, `DESCRIPTION`, COMMENTS, EXECTYPE, CONTEXTS, LABELS, LIQUIBASE, DEPLOYMENT_ID) VALUES ('initData', 'windcoder', 'db/liquibase/mysql/changelog-mysql-master.yaml', NOW(), 4, '8:65160210327454d27bf125684231cc41', 'sqlFile; sqlFile', '', 'EXECUTED', NULL, NULL, '3.6.2', '4000247719');

--  Release Database Lock
UPDATE qycmsdev.DATABASECHANGELOGLOCK SET `LOCKED` = 0, LOCKEDBY = NULL, LOCKGRANTED = NULL WHERE ID = 1;

