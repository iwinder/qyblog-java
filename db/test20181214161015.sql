--  *********************************************************************
--  Update Database Script
--  *********************************************************************
--  Change Log: db/liquibase/changelog-master20181214161015.yaml
--  Ran at: 18-12-14 下午4:10
--  Against: qycmsdev@localhost@jdbc:MySQL://localhost:3306/qycmsdev?characterEncoding=utf8&useSSL=false
--  Liquibase version: 3.6.2
--  *********************************************************************

--  Lock Database
UPDATE qycmsdev.DATABASECHANGELOGLOCK SET `LOCKED` = 1, LOCKEDBY = 'DESKTOP-SI04Q2I (172.19.102.97)', LOCKGRANTED = '2018-12-14 16:10:21.288' WHERE ID = 1 AND `LOCKED` = 0;

--  Changeset db/liquibase/changelog-master20181214161015.yaml::1544775018806-1::zhangrucong (generated)
ALTER TABLE qycmsdev.sys_category DROP FOREIGN KEY FK18qsxukvf40pdippprno5eq5b;

INSERT INTO qycmsdev.DATABASECHANGELOG (ID, AUTHOR, FILENAME, DATEEXECUTED, ORDEREXECUTED, MD5SUM, `DESCRIPTION`, COMMENTS, EXECTYPE, CONTEXTS, LABELS, LIQUIBASE, DEPLOYMENT_ID) VALUES ('1544775018806-1', 'zhangrucong (generated)', 'db/liquibase/changelog-master20181214161015.yaml', NOW(), 3, '8:9fc48fd252984b633b816b953a9d65e2', 'dropForeignKeyConstraint baseTableName=sys_category, constraintName=FK18qsxukvf40pdippprno5eq5b', '', 'EXECUTED', NULL, NULL, '3.6.2', '4775022961');

--  Changeset db/liquibase/changelog-master20181214161015.yaml::1544775018806-2::zhangrucong (generated)
ALTER TABLE qycmsdev.sys_privilege DROP FOREIGN KEY FK1nk6vdhuhcniyse9wik3pp5l7;

INSERT INTO qycmsdev.DATABASECHANGELOG (ID, AUTHOR, FILENAME, DATEEXECUTED, ORDEREXECUTED, MD5SUM, `DESCRIPTION`, COMMENTS, EXECTYPE, CONTEXTS, LABELS, LIQUIBASE, DEPLOYMENT_ID) VALUES ('1544775018806-2', 'zhangrucong (generated)', 'db/liquibase/changelog-master20181214161015.yaml', NOW(), 4, '8:162474fe388b91a5878a1db6b80df9d7', 'dropForeignKeyConstraint baseTableName=sys_privilege, constraintName=FK1nk6vdhuhcniyse9wik3pp5l7', '', 'EXECUTED', NULL, NULL, '3.6.2', '4775022961');

--  Changeset db/liquibase/changelog-master20181214161015.yaml::1544775018806-3::zhangrucong (generated)
ALTER TABLE qycmsdev.sys_privilege DROP FOREIGN KEY FK7h567kbm8i2uteel1pwveyamj;

INSERT INTO qycmsdev.DATABASECHANGELOG (ID, AUTHOR, FILENAME, DATEEXECUTED, ORDEREXECUTED, MD5SUM, `DESCRIPTION`, COMMENTS, EXECTYPE, CONTEXTS, LABELS, LIQUIBASE, DEPLOYMENT_ID) VALUES ('1544775018806-3', 'zhangrucong (generated)', 'db/liquibase/changelog-master20181214161015.yaml', NOW(), 5, '8:59ba2e83458708e4c90a3d1784fa09ae', 'dropForeignKeyConstraint baseTableName=sys_privilege, constraintName=FK7h567kbm8i2uteel1pwveyamj', '', 'EXECUTED', NULL, NULL, '3.6.2', '4775022961');

--  Changeset db/liquibase/changelog-master20181214161015.yaml::1544775018806-4::zhangrucong (generated)
ALTER TABLE qycmsdev.sys_privilege DROP FOREIGN KEY FKhidmju68461bmv7st3pfkrhcu;

INSERT INTO qycmsdev.DATABASECHANGELOG (ID, AUTHOR, FILENAME, DATEEXECUTED, ORDEREXECUTED, MD5SUM, `DESCRIPTION`, COMMENTS, EXECTYPE, CONTEXTS, LABELS, LIQUIBASE, DEPLOYMENT_ID) VALUES ('1544775018806-4', 'zhangrucong (generated)', 'db/liquibase/changelog-master20181214161015.yaml', NOW(), 6, '8:f2692e785aa3424be5bc9221ba9a8d20', 'dropForeignKeyConstraint baseTableName=sys_privilege, constraintName=FKhidmju68461bmv7st3pfkrhcu', '', 'EXECUTED', NULL, NULL, '3.6.2', '4775022961');

--  Changeset db/liquibase/changelog-master20181214161015.yaml::1544775018806-5::zhangrucong (generated)
ALTER TABLE qycmsdev.blog_article_tag DROP FOREIGN KEY FKjirukkkkhj5pkpn9i7x0gn83b;

INSERT INTO qycmsdev.DATABASECHANGELOG (ID, AUTHOR, FILENAME, DATEEXECUTED, ORDEREXECUTED, MD5SUM, `DESCRIPTION`, COMMENTS, EXECTYPE, CONTEXTS, LABELS, LIQUIBASE, DEPLOYMENT_ID) VALUES ('1544775018806-5', 'zhangrucong (generated)', 'db/liquibase/changelog-master20181214161015.yaml', NOW(), 7, '8:3f789e96af19debb5734a8408dad54a8', 'dropForeignKeyConstraint baseTableName=blog_article_tag, constraintName=FKjirukkkkhj5pkpn9i7x0gn83b', '', 'EXECUTED', NULL, NULL, '3.6.2', '4775022961');

--  Changeset db/liquibase/changelog-master20181214161015.yaml::1544775018806-6::zhangrucong (generated)
ALTER TABLE qycmsdev.sys_privilege DROP FOREIGN KEY FKl0lymq83eg69a9awoqn9sp3pi;

INSERT INTO qycmsdev.DATABASECHANGELOG (ID, AUTHOR, FILENAME, DATEEXECUTED, ORDEREXECUTED, MD5SUM, `DESCRIPTION`, COMMENTS, EXECTYPE, CONTEXTS, LABELS, LIQUIBASE, DEPLOYMENT_ID) VALUES ('1544775018806-6', 'zhangrucong (generated)', 'db/liquibase/changelog-master20181214161015.yaml', NOW(), 8, '8:f9a39d917032548017e9ec00893b5fd2', 'dropForeignKeyConstraint baseTableName=sys_privilege, constraintName=FKl0lymq83eg69a9awoqn9sp3pi', '', 'EXECUTED', NULL, NULL, '3.6.2', '4775022961');

--  Changeset db/liquibase/changelog-master20181214161015.yaml::1544775018806-7::zhangrucong (generated)
ALTER TABLE qycmsdev.sys_role_privilege DROP FOREIGN KEY FKr3sw06rqbrufgrkfq2c8dxhvb;

INSERT INTO qycmsdev.DATABASECHANGELOG (ID, AUTHOR, FILENAME, DATEEXECUTED, ORDEREXECUTED, MD5SUM, `DESCRIPTION`, COMMENTS, EXECTYPE, CONTEXTS, LABELS, LIQUIBASE, DEPLOYMENT_ID) VALUES ('1544775018806-7', 'zhangrucong (generated)', 'db/liquibase/changelog-master20181214161015.yaml', NOW(), 9, '8:1106b7366aa1d59b1ab84b2fc89b6331', 'dropForeignKeyConstraint baseTableName=sys_role_privilege, constraintName=FKr3sw06rqbrufgrkfq2c8dxhvb', '', 'EXECUTED', NULL, NULL, '3.6.2', '4775022961');

--  Changeset db/liquibase/changelog-master20181214161015.yaml::1544775018806-8::zhangrucong (generated)
ALTER TABLE qycmsdev.blog_article_tag DROP FOREIGN KEY FKrrs2kus3wx0wfxgdn3qrinpt7;

INSERT INTO qycmsdev.DATABASECHANGELOG (ID, AUTHOR, FILENAME, DATEEXECUTED, ORDEREXECUTED, MD5SUM, `DESCRIPTION`, COMMENTS, EXECTYPE, CONTEXTS, LABELS, LIQUIBASE, DEPLOYMENT_ID) VALUES ('1544775018806-8', 'zhangrucong (generated)', 'db/liquibase/changelog-master20181214161015.yaml', NOW(), 10, '8:7b7d9144683943c47d1193a4896f4714', 'dropForeignKeyConstraint baseTableName=blog_article_tag, constraintName=FKrrs2kus3wx0wfxgdn3qrinpt7', '', 'EXECUTED', NULL, NULL, '3.6.2', '4775022961');

--  Changeset db/liquibase/changelog-master20181214161015.yaml::1544775018806-9::zhangrucong (generated)
ALTER TABLE qycmsdev.sys_role_privilege DROP FOREIGN KEY FKvf9lt66q9oyjludcg83nfinq;

INSERT INTO qycmsdev.DATABASECHANGELOG (ID, AUTHOR, FILENAME, DATEEXECUTED, ORDEREXECUTED, MD5SUM, `DESCRIPTION`, COMMENTS, EXECTYPE, CONTEXTS, LABELS, LIQUIBASE, DEPLOYMENT_ID) VALUES ('1544775018806-9', 'zhangrucong (generated)', 'db/liquibase/changelog-master20181214161015.yaml', NOW(), 11, '8:c9c84fa147b3b2707349cd8080d0e981', 'dropForeignKeyConstraint baseTableName=sys_role_privilege, constraintName=FKvf9lt66q9oyjludcg83nfinq', '', 'EXECUTED', NULL, NULL, '3.6.2', '4775022961');

--  Changeset db/liquibase/changelog-master20181214161015.yaml::1544775018806-10::zhangrucong (generated)
DROP TABLE qycmsdev.blog_article;

INSERT INTO qycmsdev.DATABASECHANGELOG (ID, AUTHOR, FILENAME, DATEEXECUTED, ORDEREXECUTED, MD5SUM, `DESCRIPTION`, COMMENTS, EXECTYPE, CONTEXTS, LABELS, LIQUIBASE, DEPLOYMENT_ID) VALUES ('1544775018806-10', 'zhangrucong (generated)', 'db/liquibase/changelog-master20181214161015.yaml', NOW(), 12, '8:1ece576ac9ae68cec83e919bde1c37a6', 'dropTable tableName=blog_article', '', 'EXECUTED', NULL, NULL, '3.6.2', '4775022961');

--  Changeset db/liquibase/changelog-master20181214161015.yaml::1544775018806-11::zhangrucong (generated)
DROP TABLE qycmsdev.blog_article_tag;

INSERT INTO qycmsdev.DATABASECHANGELOG (ID, AUTHOR, FILENAME, DATEEXECUTED, ORDEREXECUTED, MD5SUM, `DESCRIPTION`, COMMENTS, EXECTYPE, CONTEXTS, LABELS, LIQUIBASE, DEPLOYMENT_ID) VALUES ('1544775018806-11', 'zhangrucong (generated)', 'db/liquibase/changelog-master20181214161015.yaml', NOW(), 13, '8:7cdb94685fa249212f699bce811648f9', 'dropTable tableName=blog_article_tag', '', 'EXECUTED', NULL, NULL, '3.6.2', '4775022961');

--  Changeset db/liquibase/changelog-master20181214161015.yaml::1544775018806-12::zhangrucong (generated)
DROP TABLE qycmsdev.blog_tag;

INSERT INTO qycmsdev.DATABASECHANGELOG (ID, AUTHOR, FILENAME, DATEEXECUTED, ORDEREXECUTED, MD5SUM, `DESCRIPTION`, COMMENTS, EXECTYPE, CONTEXTS, LABELS, LIQUIBASE, DEPLOYMENT_ID) VALUES ('1544775018806-12', 'zhangrucong (generated)', 'db/liquibase/changelog-master20181214161015.yaml', NOW(), 14, '8:96579f2d14220b4577897bfa22319ba6', 'dropTable tableName=blog_tag', '', 'EXECUTED', NULL, NULL, '3.6.2', '4775022961');

--  Changeset db/liquibase/changelog-master20181214161015.yaml::1544775018806-13::zhangrucong (generated)
DROP TABLE qycmsdev.sys_category;

INSERT INTO qycmsdev.DATABASECHANGELOG (ID, AUTHOR, FILENAME, DATEEXECUTED, ORDEREXECUTED, MD5SUM, `DESCRIPTION`, COMMENTS, EXECTYPE, CONTEXTS, LABELS, LIQUIBASE, DEPLOYMENT_ID) VALUES ('1544775018806-13', 'zhangrucong (generated)', 'db/liquibase/changelog-master20181214161015.yaml', NOW(), 15, '8:d082c6f5ef6809dbd944d18804bc7c3c', 'dropTable tableName=sys_category', '', 'EXECUTED', NULL, NULL, '3.6.2', '4775022961');

--  Changeset db/liquibase/changelog-master20181214161015.yaml::1544775018806-14::zhangrucong (generated)
DROP TABLE qycmsdev.sys_ldap_setting;

INSERT INTO qycmsdev.DATABASECHANGELOG (ID, AUTHOR, FILENAME, DATEEXECUTED, ORDEREXECUTED, MD5SUM, `DESCRIPTION`, COMMENTS, EXECTYPE, CONTEXTS, LABELS, LIQUIBASE, DEPLOYMENT_ID) VALUES ('1544775018806-14', 'zhangrucong (generated)', 'db/liquibase/changelog-master20181214161015.yaml', NOW(), 16, '8:0643b5d1c3b1513879dc71ca73f7cfa9', 'dropTable tableName=sys_ldap_setting', '', 'EXECUTED', NULL, NULL, '3.6.2', '4775022961');

--  Changeset db/liquibase/changelog-master20181214161015.yaml::1544775018806-15::zhangrucong (generated)
DROP TABLE qycmsdev.sys_privilege;

INSERT INTO qycmsdev.DATABASECHANGELOG (ID, AUTHOR, FILENAME, DATEEXECUTED, ORDEREXECUTED, MD5SUM, `DESCRIPTION`, COMMENTS, EXECTYPE, CONTEXTS, LABELS, LIQUIBASE, DEPLOYMENT_ID) VALUES ('1544775018806-15', 'zhangrucong (generated)', 'db/liquibase/changelog-master20181214161015.yaml', NOW(), 17, '8:dc582d46daab8e54d0bdc73176984ced', 'dropTable tableName=sys_privilege', '', 'EXECUTED', NULL, NULL, '3.6.2', '4775022961');

--  Changeset db/liquibase/changelog-master20181214161015.yaml::1544775018806-16::zhangrucong (generated)
DROP TABLE qycmsdev.sys_role;

INSERT INTO qycmsdev.DATABASECHANGELOG (ID, AUTHOR, FILENAME, DATEEXECUTED, ORDEREXECUTED, MD5SUM, `DESCRIPTION`, COMMENTS, EXECTYPE, CONTEXTS, LABELS, LIQUIBASE, DEPLOYMENT_ID) VALUES ('1544775018806-16', 'zhangrucong (generated)', 'db/liquibase/changelog-master20181214161015.yaml', NOW(), 18, '8:7d349d987b54b2ed42a7642329ca201c', 'dropTable tableName=sys_role', '', 'EXECUTED', NULL, NULL, '3.6.2', '4775022961');

--  Changeset db/liquibase/changelog-master20181214161015.yaml::1544775018806-17::zhangrucong (generated)
DROP TABLE qycmsdev.sys_role_privilege;

INSERT INTO qycmsdev.DATABASECHANGELOG (ID, AUTHOR, FILENAME, DATEEXECUTED, ORDEREXECUTED, MD5SUM, `DESCRIPTION`, COMMENTS, EXECTYPE, CONTEXTS, LABELS, LIQUIBASE, DEPLOYMENT_ID) VALUES ('1544775018806-17', 'zhangrucong (generated)', 'db/liquibase/changelog-master20181214161015.yaml', NOW(), 19, '8:99028c1673af64301c963f97d3e9277c', 'dropTable tableName=sys_role_privilege', '', 'EXECUTED', NULL, NULL, '3.6.2', '4775022961');

--  Changeset db/liquibase/changelog-master20181214161015.yaml::1544775018806-18::zhangrucong (generated)
DROP TABLE qycmsdev.sys_user;

INSERT INTO qycmsdev.DATABASECHANGELOG (ID, AUTHOR, FILENAME, DATEEXECUTED, ORDEREXECUTED, MD5SUM, `DESCRIPTION`, COMMENTS, EXECTYPE, CONTEXTS, LABELS, LIQUIBASE, DEPLOYMENT_ID) VALUES ('1544775018806-18', 'zhangrucong (generated)', 'db/liquibase/changelog-master20181214161015.yaml', NOW(), 20, '8:485c37fcc117d1b20de260f6d04b5133', 'dropTable tableName=sys_user', '', 'EXECUTED', NULL, NULL, '3.6.2', '4775022961');

--  Release Database Lock
UPDATE qycmsdev.DATABASECHANGELOGLOCK SET `LOCKED` = 0, LOCKEDBY = NULL, LOCKGRANTED = NULL WHERE ID = 1;

