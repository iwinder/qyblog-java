--  *********************************************************************
--  Update Database Script
--  *********************************************************************
--  Change Log: G:/none/Java/idea/qycms/db/liquibase/mysql/changelog-mysql-master-20181214170440.yaml
--  Ran at: 18-12-14 下午5:04
--  Against: qycmsdev@localhost@jdbc:MySQL://localhost:3306/qycmstest?characterEncoding=utf8&useSSL=false
--  Liquibase version: 3.6.2
--  *********************************************************************

--  Create Database Lock Table
CREATE TABLE qycmstest.DATABASECHANGELOGLOCK (ID INT NOT NULL, `LOCKED` BIT(1) NOT NULL, LOCKGRANTED datetime NULL, LOCKEDBY VARCHAR(255) NULL, CONSTRAINT PK_DATABASECHANGELOGLOCK PRIMARY KEY (ID));

--  Initialize Database Lock Table
DELETE FROM qycmstest.DATABASECHANGELOGLOCK;

INSERT INTO qycmstest.DATABASECHANGELOGLOCK (ID, `LOCKED`) VALUES (1, 0);

--  Lock Database
UPDATE qycmstest.DATABASECHANGELOGLOCK SET `LOCKED` = 1, LOCKEDBY = 'DESKTOP-SI04Q2I (172.19.102.97)', LOCKGRANTED = '2018-12-14 17:04:45.615' WHERE ID = 1 AND `LOCKED` = 0;

--  Create Database Change Log Table
CREATE TABLE qycmstest.DATABASECHANGELOG (ID VARCHAR(255) NOT NULL, AUTHOR VARCHAR(255) NOT NULL, FILENAME VARCHAR(255) NOT NULL, DATEEXECUTED datetime NOT NULL, ORDEREXECUTED INT NOT NULL, EXECTYPE VARCHAR(10) NOT NULL, MD5SUM VARCHAR(35) NULL, `DESCRIPTION` VARCHAR(255) NULL, COMMENTS VARCHAR(255) NULL, TAG VARCHAR(255) NULL, LIQUIBASE VARCHAR(20) NULL, CONTEXTS VARCHAR(255) NULL, LABELS VARCHAR(255) NULL, DEPLOYMENT_ID VARCHAR(10) NULL);

--  Release Database Lock
UPDATE qycmstest.DATABASECHANGELOGLOCK SET `LOCKED` = 0, LOCKEDBY = NULL, LOCKGRANTED = NULL WHERE ID = 1;

