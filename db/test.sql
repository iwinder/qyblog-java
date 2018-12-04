--  *********************************************************************
--  Update Database Script
--  *********************************************************************
--  Change Log: G:/none/Java/idea/qycms/db/liquibase/changelog-master.yaml
--  Ran at: 18-12-4 下午5:46
--  Against: qycmsdev@localhost@jdbc:MySQL://localhost:3306/qycmsdev?characterEncoding=utf8&useSSL=false
--  Liquibase version: 3.6.2
--  *********************************************************************

--  Lock Database
UPDATE qycmsdev.DATABASECHANGELOGLOCK SET `LOCKED` = 1, LOCKEDBY = 'DESKTOP-SI04Q2I (172.19.102.97)', LOCKGRANTED = '2018-12-04 17:46:25.682' WHERE ID = 1 AND `LOCKED` = 0;

--  Changeset G:/none/Java/idea/qycms/db/liquibase/changelog-master.yaml::2::windcoder
INSERT INTO `sys_role` ( `name`, `remark`,display_order,created_date,last_modified_date,created_by, last_modified_by) VALUES ('超级管理员', '管理者',1,now(),now(),2,2);

INSERT INTO `sys_role` ( `name`, `remark`,display_order,created_date,last_modified_date,created_by, last_modified_by) VALUES ('超级管理员2', '管理者2',2,now(),now(),2,2);

INSERT INTO `sys_role` ( `name`, `remark`,display_order,created_date,last_modified_date,created_by, last_modified_by) VALUES ('超级管理员22', '管理者4',3,now(),now(),2,2);

INSERT INTO qycmsdev.DATABASECHANGELOG (ID, AUTHOR, FILENAME, DATEEXECUTED, ORDEREXECUTED, MD5SUM, `DESCRIPTION`, COMMENTS, EXECTYPE, CONTEXTS, LABELS, LIQUIBASE, DEPLOYMENT_ID) VALUES ('2', 'windcoder', 'G:/none/Java/idea/qycms/db/liquibase/changelog-master.yaml', NOW(), 3, '8:00e3c5f6588d6f35d2cac816a84801df', 'sqlFile', '', 'EXECUTED', NULL, NULL, '3.6.2', '3916787263');

--  Release Database Lock
UPDATE qycmsdev.DATABASECHANGELOGLOCK SET `LOCKED` = 0, LOCKEDBY = NULL, LOCKGRANTED = NULL WHERE ID = 1;

