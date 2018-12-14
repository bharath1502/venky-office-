-- DROP SCHEMA and Create
DROP DATABASE IF EXISTS chatak_pg;
CREATE DATABASE chatak_pg;
USE chatak_pg;

DROP TABLE IF EXISTS PG_PASSWORD_HISTORY;
DROP TABLE IF EXISTS PG_USER_AUTHENTICATION;
DROP TABLE IF EXISTS PG_USER_PROFILE;
DROP TABLE IF EXISTS PG_USER_ROLES;
DROP TABLE IF EXISTS PG_MERCHANT_BANK;
DROP TABLE IF EXISTS PG_MERCHANT_SETTINGS;
DROP TABLE IF EXISTS PG_MERCHANT;
DROP TABLE IF EXISTS PG_STORE;
DROP TABLE IF EXISTS PG_STORE_SETTINGS;
DROP TABLE IF EXISTS PG_TERMINAL;
DROP TABLE IF EXISTS PG_TERMINAL_SETTINGS;
DROP TABLE IF EXISTS PG_ACTIVITY_LOG;
DROP TABLE IF EXISTS PG_TRANSACTION;
DROP TABLE IF EXISTS PG_TXN_CARD_INFO;
DROP TABLE IF EXISTS PG_ISSUER_LOG;
DROP TABLE IF EXISTS PG_FEE_DETAIL;
DROP TABLE IF EXISTS PG_ACQUIRER;
DROP TABLE IF EXISTS PG_FRAUD_TYPE;
DROP TABLE IF EXISTS PG_FRAUD_CONFIG;
DROP TABLE IF EXISTS PG_BLACKLISTED_CARD;
DROP TABLE IF EXISTS PG_EMV_TRANSACTION;
DROP TABLE IF EXISTS PG_SERVICE_ADMIN_USER;
DROP TABLE IF EXISTS PG_SWITCH_TRANSACTION;
DROP TABLE IF EXISTS PG_SWITCH;


-- 01
-- Table structure for table `PG_USER_ROLES`
--

CREATE TABLE PG_USER_ROLES(
ROLE_ID  SMALLINT(2),
ROLE_NAME VARCHAR(30),
constraint RID PRIMARY KEY(ROLE_ID)
)ENGINE=INNODB DEFAULT CHARSET=latin1;

INSERT INTO PG_USER_ROLES (ROLE_ID, ROLE_NAME) VALUES
(1, 'SuperAdmin'),
(2, 'MerchantAdmin'),
(3, 'StoreAdmin'),
(4, 'PortalUser');


-- PGAdmin tables 
-- 02
-- Table structure for table `PG_MERCHANT`
--

CREATE TABLE PG_MERCHANT(
ID BIGINT(15) NOT NULL AUTO_INCREMENT, 
MERCHANT_ID VARCHAR(15) NOT NULL, 
MERCHANT_NAME VARCHAR(50) NOT NULL,
MCC VARCHAR(20), 
DAILY_TXN_LIMIT BIGINT(16),
ALLOW_INTERNATIONAL_TXN SMALLINT(1) default 0,
MERCHANT_TYPE VARCHAR(50), 
PHONE1 BIGINT(13) NOT NULL,
PHONE2 BIGINT(13),
TIN VARCHAR(20),
SSN VARCHAR(20),
ZIP VARCHAR(10),
ADDRESS1 VARCHAR(50),
ADDRESS2 VARCHAR(50),
CITY VARCHAR(50),
STATE VARCHAR(50),
COUNTRY VARCHAR(50),
CREATED_DATE TIMESTAMP NOT NULL,
UPDATED_DATE TIMESTAMP NOT NULL,
VALID_FROM_DATE TIMESTAMP NOT NULL,
VALID_TO_DATE TIMESTAMP NOT NULL,
STATUS SMALLINT(1) default 0,
CONSTRAINT MI_PK PRIMARY KEY(ID)
)ENGINE=INNODB DEFAULT CHARSET=latin1;

insert into pg_merchant (MERCHANT_ID, MERCHANT_NAME, MCC, DAILY_TXN_LIMIT, ALLOW_INTERNATIONAL_TXN, MERCHANT_TYPE, PHONE1, TIN, ADDRESS1, CREATED_DATE, UPDATED_DATE, VALID_FROM_DATE, VALID_TO_DATE)
values ('222222222222222', "Virtual Merchant1", 'TEST', 10000, 1, 'TEST', 9999999999, "TIN000000001", "virtual Address", now(), now(),now(), now());


-- 03
-- Table structure for table `PG_USER_PROFILE`
--

CREATE TABLE PG_USER_PROFILE(
PROFILE_ID BIGINT(10),
FIRST_NAME VARCHAR(50) NOT NULL,
MIDDLE_NAME VARCHAR(50),
LAST_NAME VARCHAR(50),
DOB TIMESTAMP NOT NULL,
EMAIL VARCHAR(50) NOT NULL UNIQUE,
PASSWORD VARCHAR(50),
SSN VARCHAR(20),
PHONE1 VARCHAR(15),
PHONE2 VARCHAR(15),
ADDRESS1 VARCHAR(50),
ADDRESS2 VARCHAR(50),
CITY VARCHAR(50),
STATE VARCHAR(50),
ZIP VARCHAR(10),
ROLE_ID SMALLINT(1) NOT NULL,
MERCHANT_ID BIGINT(15) NOT NULL,
STORE_ID BIGINT(10),
CREATED_DATE TIMESTAMP NOT NULL,
UPDATED_DATE TIMESTAMP NOT NULL,
STATUS SMALLINT(1) default 0,
CONSTRAINT UID PRIMARY KEY(PROFILE_ID),
CONSTRAINT FK_ROLEID FOREIGN KEY (ROLE_ID)
    REFERENCES PG_USER_ROLES(ROLE_ID),
CONSTRAINT FK_MERCHANT_ID FOREIGN KEY (MERCHANT_ID)
    REFERENCES PG_MERCHANT(ID)
)ENGINE=INNODB DEFAULT CHARSET=latin1;


INSERT INTO PG_USER_PROFILE (FIRST_NAME,PROFILE_ID,  ROLE_ID,  PASSWORD, MERCHANT_ID, EMAIL, CREATED_DATE, UPDATED_DATE) values
('super user', 99999999, 1, '32/3u+t6XemudIJq3adGYw==', 1 , 'testuser@google.com', now(), now());


-- 04
-- Table structure for table `PG_PASSWORD_HISTORY`
--

CREATE TABLE PG_PASSWORD_HISTORY (
ID  BIGINT(10) NOT NULL AUTO_INCREMENT,
PROFILE_ID BIGINT(10),
CREATED_DATE TIMESTAMP NOT NULL,
CONSTRAINT AUTH_ID PRIMARY KEY(ID),
CONSTRAINT FK_PGPH_PROFILE_ID FOREIGN KEY (PROFILE_ID)
    REFERENCES PG_USER_PROFILE(PROFILE_ID)
)ENGINE=INNODB DEFAULT CHARSET=latin1;


-- 04
-- Table structure for table `PG_USER_AUTHENTICATION`
--

CREATE TABLE PG_USER_AUTHENTICATION (
ID BIGINT(10) NOT NULL AUTO_INCREMENT,
PROFILE_ID BIGINT(10) NOT NULL UNIQUE,
TOKEN VARCHAR(100) NOT NULL UNIQUE,
TOKEN_EXP_TIME TIMESTAMP NOT NULL,
CLIENT_IP VARCHAR(50) NOT NULL,
CONSTRAINT AUTH_ID PRIMARY KEY(ID),
CONSTRAINT FK_PROFILE_ID FOREIGN KEY (PROFILE_ID)
    REFERENCES PG_USER_PROFILE(PROFILE_ID)
)ENGINE=INNODB DEFAULT CHARSET=latin1;


-- 04
-- Table structure for table `PG_MERCHANT_BANK`
--

CREATE TABLE PG_MERCHANT_BANK(
ID BIGINT(10) NOT NULL AUTO_INCREMENT,
MERCHANT_ID BIGINT(15) NOT NULL,
BANK_NAME VARCHAR(50) NOT NULL,
BANK_ACC_NUM VARCHAR(20) NOT NULL,
BANK_CODE VARCHAR(20),
CURRENCY_CODE MEDIUMINT(3),
ACCOUNT_TYPE VARCHAR(20),
STATUS SMALLINT(1) default 0,
CREATED_DATE TIMESTAMP ,
UPDATED_DATE TIMESTAMP ,
CREATED_BY BIGINT(10),
CONSTRAINT BID PRIMARY KEY(ID),
CONSTRAINT FK_PGMB_MERCHANT_ID FOREIGN KEY (MERCHANT_ID)
    REFERENCES PG_MERCHANT(ID),
CONSTRAINT FK_CREATED_BY FOREIGN KEY (CREATED_BY)
   REFERENCES PG_USER_PROFILE(PROFILE_ID)
)ENGINE=INNODB DEFAULT CHARSET=latin1;


-- 05
-- Table structure for table `PG_MERCHANT_SETTINGS`
--


CREATE TABLE PG_MERCHANT_SETTINGS(
ID  BIGINT(10) NOT NULL AUTO_INCREMENT,
MERCHANT_ID BIGINT(15) NOT NULL,
SETTLEMENT_OPTION SMALLINT(1) NOT NULL DEFAULT 0,
SETTLEMENT_FREQUENCY MEDIUMINT(3),
MAX_TXN_DAY BIGINT(7) DEFAULT 9999999,
MAX_TXN_WEEK BIGINT(7) DEFAULT 9999999,
MAX_TXN_MONTH BIGINT(7) DEFAULT 9999999,
MAX_TXN_AMT BIGINT(12) DEFAULT 9999999,
CREATED_DATE TIMESTAMP ,
UPDATED_DATE TIMESTAMP ,
CREATED_BY BIGINT(10),
CONSTRAINT MSID PRIMARY KEY(ID),
CONSTRAINT FK_PGMS_MERCHANT_ID FOREIGN KEY (MERCHANT_ID)
    REFERENCES PG_MERCHANT(ID),
CONSTRAINT FK_PGMS_CREATED_BY FOREIGN KEY (CREATED_BY)
   REFERENCES PG_USER_PROFILE(PROFILE_ID)
)ENGINE=INNODB DEFAULT CHARSET=latin1;


-- 06
-- Table structure for table `PG_STORE`
--

CREATE TABLE PG_STORE(
STORE_ID BIGINT(10) NOT NULL,
MERCHANT_ID BIGINT(15) NOT NULL,
STORE_NAME VARCHAR(50),
PHONE1 VARCHAR(15),
PHONE2 VARCHAR(15),
ADDRESS1 VARCHAR(50),
ADDRESS2 VARCHAR(50),
ZIP VARCHAR(10),
CITY VARCHAR(50),
STATE VARCHAR(50),
CREATED_DATE TIMESTAMP ,
UPDATED_DATE TIMESTAMP ,
VALID_FROM_DATE TIMESTAMP NOT NULL,
VALID_TO_DATE TIMESTAMP NOT NULL,
STATUS SMALLINT(1) default 0, 
CONSTRAINT UID PRIMARY KEY(STORE_ID),
CONSTRAINT FK_PGS_MERCHANT_ID FOREIGN KEY (MERCHANT_ID)
    REFERENCES PG_MERCHANT(ID)
)ENGINE=INNODB DEFAULT CHARSET=latin1;


-- 07
-- Table structure for table `PG_STORE_SETTINGS`
--

CREATE TABLE PG_STORE_SETTINGS(
ID  BIGINT(10) NOT NULL AUTO_INCREMENT,
MERCHANT_ID BIGINT(15) NOT NULL,
STORE_ID BIGINT(10),
SETTLEMENT_OPTION MEDIUMINT(1),
SETTLEMENT_FREQUENCY MEDIUMINT(3),
MAX_TXN_DAY BIGINT(7) DEFAULT 9999999,
MAX_TXN_WEEK BIGINT(7) DEFAULT 9999999,
MAX_TXN_MONTH BIGINT(7) DEFAULT 9999999,
MAX_TXN_AMT BIGINT(12) DEFAULT 9999999,
CREATED_DATE TIMESTAMP ,
UPDATED_DATE TIMESTAMP ,
CREATED_BY BIGINT(10),
CONSTRAINT SSID PRIMARY KEY(ID),
CONSTRAINT FK_PGSS_MERCHANT_ID FOREIGN KEY (MERCHANT_ID)
    REFERENCES PG_MERCHANT(ID),
CONSTRAINT FK_PGSS_STORE_ID FOREIGN KEY (STORE_ID)
    REFERENCES PG_STORE(STORE_ID),
CONSTRAINT FK_PGSS_CREATED_BY FOREIGN KEY (CREATED_BY)
   REFERENCES PG_USER_PROFILE(PROFILE_ID)
)ENGINE=INNODB DEFAULT CHARSET=latin1;


-- 08
-- Table structure for table `PG_TERMINAL`
--

CREATE TABLE PG_TERMINAL(
ID  BIGINT(10) NOT NULL AUTO_INCREMENT,
TERMINAL_ID BIGINT(8) NOT NULL,
MERCHANT_ID BIGINT(15) NOT NULL,
STORE_ID BIGINT(10),
CREATED_DATE TIMESTAMP ,
UPDATED_DATE TIMESTAMP ,
VALID_FROM_DATE TIMESTAMP NOT NULL,
VALID_TO_DATE TIMESTAMP NOT NULL,
STATUS SMALLINT(1) default 0,
MAKE VARCHAR(100),
MODEL VARCHAR(100),
TERMINAL_TYPE VARCHAR(50),-- Attended/Un Attended
CATEGORY VARCHAR(50), -- POS/ATM
CURRENCY VARCHAR(20), -- INR/USD/EUR
ZIP VARCHAR(10),
CITY VARCHAR(50),
STATE VARCHAR(50),
COUNTRY VARCHAR(50),
CONSTRAINT TID PRIMARY KEY(ID),
CONSTRAINT FK_PGT_MERCHANT_ID FOREIGN KEY (MERCHANT_ID)
    REFERENCES PG_MERCHANT(ID)
-- ,CONSTRAINT FK_PGT_STORE_ID FOREIGN KEY (STORE_ID)
--    REFERENCES PG_STORE(STORE_ID)
)ENGINE=INNODB DEFAULT CHARSET=latin1;

-- insert into pg_terminal (TERMINAL_ID, MERCHANT_ID, STORE_ID, CREATED_DATE, UPDATED_DATE, VALID_FROM_DATE, VALID_TO_DATE)
-- values (22222222, 371815745501832, '', now(), now(), now(), now() );


-- 09
-- Table structure for table `PG_TERMINAL_SETTINGS`
--

CREATE TABLE PG_TERMINAL_SETTINGS(
ID  BIGINT(10) NOT NULL AUTO_INCREMENT,
MERCHANT_ID BIGINT(15) NOT NULL,
STORE_ID BIGINT(10),
TERMINAL_ID BIGINT(8) NOT NULL,
SETTLEMENT_OPTION MEDIUMINT(1),
SETTLEMENT_FREQUENCY MEDIUMINT(3),
MAX_TXN_DAY BIGINT(7) DEFAULT 9999999,
MAX_TXN_WEEK BIGINT(7) DEFAULT 9999999,
MAX_TXN_MONTH BIGINT(7) DEFAULT 9999999,
MAX_TXN_AMT BIGINT(12) DEFAULT 9999999,
CREATED_DATE TIMESTAMP ,
UPDATED_DATE TIMESTAMP ,
CREATED_BY BIGINT(10),
CONSTRAINT SSID PRIMARY KEY(ID),
CONSTRAINT FK_PGTS_MERCHANT_ID FOREIGN KEY (MERCHANT_ID)
    REFERENCES PG_MERCHANT(ID),
-- CONSTRAINT FK_PGTS_STORE_ID FOREIGN KEY (STORE_ID) Commented since this is not used
--    REFERENCES PG_STORE(STORE_ID),
CONSTRAINT FK_PGTS_TERMINAL_ID FOREIGN KEY (TERMINAL_ID)
    REFERENCES PG_TERMINAL(ID),
CONSTRAINT FK_CREATED_PGTS_BY FOREIGN KEY (CREATED_BY)
   REFERENCES PG_USER_PROFILE(PROFILE_ID)
    
)ENGINE=INNODB DEFAULT CHARSET=latin1;


-- Payment Gateway tables 
-- 07
-- Table structure for table `PG_ACTIVITY_LOG`
--

CREATE TABLE PG_ACTIVITY_LOG(
ID  BIGINT(10) NOT NULL AUTO_INCREMENT,
SYS_TRACE_NUM BIGINT(12),
REQUEST_IP VARCHAR(20),
REQUEST_PORT MEDIUMINT(5),
RESPONSE_PORT MEDIUMINT(5),
POS_ENTRY_MODE VARCHAR(10),
CHIP_TRANSACTION SMALLINT(1) DEFAULT 0, 
PROCESSING_CODE VARCHAR(10),
RESPONSE_CODE VARCHAR(10),
F39 VARCHAR(10),
TXN_AMOUNT BIGINT(12),
ADJ_AMOUNT BIGINT(12),
MTI VARCHAR(4) NOT NULL,
PAN VARCHAR(150),
PAN_MASKED VARCHAR(20),
EXP_DATE VARCHAR(50),
POS_TXN_TIME VARCHAR(6),
POS_TXN_DATE VARCHAR(4),
MCC VARCHAR(4),
TXN_COUNTRY_CODE VARCHAR(3),
TXN_CURRENCY_CODE VARCHAR(3),
AI_COUNTRY_CODE VARCHAR(3),
PAN_COUNTRY_CODE VARCHAR(3),
FWD_COUNTRY_CODE VARCHAR(3),
F55  VARCHAR(1000),
CREATED_DATE TIMESTAMP,
CONSTRAINT ACTIVITYID PRIMARY KEY(ID)
)ENGINE=INNODB DEFAULT CHARSET=latin1;

-- 08
-- Table structure for table `PG_TRANSACTION`
--

CREATE TABLE PG_TRANSACTION(
ID  BIGINT(10) NOT NULL AUTO_INCREMENT,
TRANSACTION_ID BIGINT(12) UNIQUE,
REF_TRANSACTION_ID BIGINT(12),
AUTH_ID BIGINT(12),
INVOICE_NUMBER BIGINT(12),
SYS_TRACE_NUM BIGINT(12),
TXN_TYPE VARCHAR(20),
PAYMENT_METHOD VARCHAR(20),
TXN_AMOUNT BIGINT(12),
ADJ_AMOUNT BIGINT(12),
FEE_AMOUNT BIGINT(12),
TOTAL_AMOUNT BIGINT(12),
MERCHANT_ID BIGINT(15) NOT NULL,
TERMINAL_ID BIGINT(8) NOT NULL,
ACQ_CHANNEL VARCHAR(5) NOT NULL DEFAULT "web",
ACQ_TXN_MODE VARCHAR(20) NOT NULL DEFAULT "rest",
ISSUER_TXN_REF_NUM BIGINT(12),
CREATED_DATE TIMESTAMP,
UPDATED_DATE TIMESTAMP,
STATUS SMALLINT(1),
CHIP_TRANSACTION SMALLINT(1) DEFAULT 0,
CHIP_FALLBACK_TRANSACTION SMALLINT(1) DEFAULT 0,
SETTLEMENT_BATCH_ID BIGINT(10),
SETTLEMENT_BATCH_STATUS SMALLINT(1),
MTI VARCHAR(4) NOT NULL,
PROC_CODE VARCHAR(6) NOT NULL,
PAN VARCHAR(150),
PAN_MASKED VARCHAR(20),
EXP_DATE VARCHAR(50),
POS_TXN_TIME VARCHAR(6),
POS_TXN_DATE VARCHAR(4),
MCC VARCHAR(4),
POS_ENTRY_MODE VARCHAR(3),
TXN_COUNTRY_CODE VARCHAR(3),
TXN_CURRENCY_CODE VARCHAR(3),
AI_COUNTRY_CODE VARCHAR(3),
PAN_COUNTRY_CODE VARCHAR(3),
FWD_COUNTRY_CODE VARCHAR(3),
CONSTRAINT TXNID PRIMARY KEY(ID)
)ENGINE=INNODB DEFAULT CHARSET=latin1;

-- 09
-- Table structure for table `PG_TXN_CARD_INFO`
--

CREATE TABLE PG_TXN_CARD_INFO(
ID  BIGINT(10) NOT NULL AUTO_INCREMENT,
TRANSACTION_ID BIGINT(12),
CARD_NO VARCHAR(50),
PIN MEDIUMINT(5),
LAST_FOUR VARCHAR(4),
CARD_NO_LENGTH MEDIUMINT(2),
EXP_DATE VARCHAR(4),
NAME_ON_CARD VARCHAR(50),
ADDRESS VARCHAR(50),
AVS_ZIP MEDIUMINT(6),
TRACK1 VARCHAR(100),
TRACK2 VARCHAR(50),
EMV TEXT,
CARD_ACC_TYPE VARCHAR(20),
CREATED_DATE TIMESTAMP,
CONSTRAINT CID PRIMARY KEY(ID)
)ENGINE=INNODB DEFAULT CHARSET=latin1;

-- 10
-- Table structure for table `PG_ISSUER_LOG`
--

CREATE TABLE PG_ISSUER_LOG(
ID  BIGINT(10) NOT NULL AUTO_INCREMENT,
REQUEST TEXT,
RESPONSE TEXT,
CREATED_DATE TIMESTAMP,
UPDATED_DATE TIMESTAMP,
CONSTRAINT IID PRIMARY KEY(ID)
)ENGINE=INNODB DEFAULT CHARSET=latin1;


-- 11
-- Table structure for table `PG_FEE_DETAIL`
--

CREATE TABLE PG_FEE_DETAIL(
ID  BIGINT(10) NOT NULL AUTO_INCREMENT,
TXN_TYPE VARCHAR(20),
FEE_AMOUNT BIGINT(12),
CREATED_DATE TIMESTAMP,
STATUS SMALLINT(1) default 0,
CONSTRAINT FID PRIMARY KEY(ID)
)ENGINE=INNODB DEFAULT CHARSET=latin1;

INSERT INTO PG_FEE_DETAIL (TXN_TYPE, FEE_AMOUNT, CREATED_DATE)
VALUES 
("auth", 0.00, now()),
("sale", 0.00, now()),
("sale-adj", 0.00, now()),
("void", 0.00, now()),
("reversal", 0.00, now());


-- Fraud detection and Merchant config
-- 12
-- Table structure for table `PG_ACQUIRER`
--

CREATE TABLE PG_ACQUIRER(
ACQUIRER_ID BIGINT(10) NOT NULL,
NAME VARCHAR(50),
URL VARCHAR(100),
CONFIG_PARAM1 VARCHAR(50),
CONFIG_PARAM2 VARCHAR(50),
CONFIG_PARAM3 VARCHAR(50),
CONFIG_PARAM4 VARCHAR(50),
CONFIG_PARAM5 VARCHAR(50),
CONFIG_PARAM6 VARCHAR(50),
CREATED_DATE  TIMESTAMP,
UPDATED_DATE  TIMESTAMP,
STATUS SMALLINT(1) default 0,
CONSTRAINT ACQID PRIMARY KEY(ACQUIRER_ID)
)ENGINE=INNODB DEFAULT CHARSET=latin1;

-- 13
-- Table structure for table `PG_FRAUD_TYPE`
--

CREATE TABLE PG_FRAUD_TYPE(
ID  BIGINT(10) NOT NULL AUTO_INCREMENT,
FRAUD_ID MEDIUMINT(5) NOT NULL,
FRAUD_NAME VARCHAR(50),
CREATED_DATE  TIMESTAMP,
STATUS SMALLINT(1) default 0,
CONSTRAINT FTID PRIMARY KEY(ID)
)ENGINE=INNODB DEFAULT CHARSET=latin1;

INSERT INTO PG_FRAUD_TYPE ( FRAUD_ID, FRAUD_NAME, CREATED_DATE ) 
VALUES 
(1, 'BLACKLIST CARD', NOW() ),
(2, 'COUNTRY BLOCK', NOW() ),
(3, 'IP ACTIVITY LIMIT', NOW() ),
(4, 'CRAMMING PROTECTION', NOW() ),
(5, 'CVV2 CHECK', NOW() ),
(6, 'VELOCITY CHECK', NOW() ),
(7, 'AMOUNT CHECK', NOW() ),
(8, 'BIN COUNTRY CHECK ', NOW() ),
(9, 'AVS CHECK', NOW() ),
(10, 'SSL CONFIG', NOW() ),
(11, 'THIRD PARTY ANTI FRAUD', NOW() );


-- 14
-- Table structure for table `PG_FRAUD_CONFIG`
--

CREATE TABLE PG_FRAUD_CONFIG(
ID  BIGINT(10) NOT NULL AUTO_INCREMENT,
ACQUIRER_ID MEDIUMINT(5) NOT NULL,
FRAUD_ID MEDIUMINT(5) NOT NULL,
FRAUD_NAME VARCHAR(50),
CONFIG_PARAM1 VARCHAR(50),
CONFIG_PARAM2 VARCHAR(50),
CONFIG_PARAM3 VARCHAR(50),
CONFIG_PARAM4 VARCHAR(50),
CONFIG_PARAM5 VARCHAR(50),
CREATED_DATE  TIMESTAMP,
STATUS SMALLINT(1) default 0,
CONSTRAINT FCID PRIMARY KEY(ID)
)ENGINE=INNODB DEFAULT CHARSET=latin1;

-- 15
-- Table structure for table `PG_BLACKLISTED_CARD`
--

CREATE TABLE PG_BLACKLISTED_CARD(
ID  BIGINT(10) NOT NULL AUTO_INCREMENT,
CARD_NUM VARCHAR(50),
CREATED_DATE  TIMESTAMP,
STATUS SMALLINT(1) default 0,
CONSTRAINT BLCID PRIMARY KEY(ID)
)ENGINE=INNODB DEFAULT CHARSET=latin1;

INSERT INTO PG_BLACKLISTED_CARD (CARD_NUM, CREATED_DATE ) VALUES
("1111111111111111", NOW() );


-- 16
-- Table structure for table `PG_FEATURES`
--

CREATE TABLE PG_FEATURES(
FEATURE_ID  SMALLINT(2),
FEATURE_NAME VARCHAR(30),
FEATURE_URL VARCHAR(100),
constraint FID PRIMARY KEY(FEATURE_ID)
)ENGINE=INNODB DEFAULT CHARSET=latin1;

INSERT INTO PG_FEATURES (FEATURE_ID, FEATURE_NAME, FEATURE_URL) VALUES
(1, 'Merchant Management', 'merchantManagement'),
(2, 'User Management', 'userManagement'),
(3, 'Transaction Management', 'transactionManagement'),
(4, 'Settlement Report', 'settlementReport');



-- 17
-- Table structure for table `PG_SUB_FEATURES`
--

CREATE TABLE PG_SUB_FEATURES(
SUB_FEATURE_ID SMALLINT(2),
FEATURE_ID  SMALLINT(2),
FEATURE_NAME VARCHAR(30),
FEATURE_URL VARCHAR(100),
constraint SFID PRIMARY KEY(SUB_FEATURE_ID),
CONSTRAINT PGSF_FEATURE_ID FOREIGN KEY (FEATURE_ID)
    REFERENCES PG_FEATURES(FEATURE_ID)
)ENGINE=INNODB DEFAULT CHARSET=latin1;

INSERT INTO PG_SUB_FEATURES (SUB_FEATURE_ID, FEATURE_ID, FEATURE_NAME, FEATURE_URL) VALUES

(1, 1, 'Store Management', 'storeManagement'),
(2, 1, 'Terminal Merchant', 'terminalmanagement'),
(3, 3, 'Transaction Report', 'transactionReport'),
(4, 3, 'Transaction Summary', 'transactionSummary');




-- 18
-- Table structure for table `PG_ROLE_FEATURE_MAPPING`
--
CREATE TABLE PG_ROLE_FEATURE_MAPPING(
ID MEDIUMINT(5) NOT NULL AUTO_INCREMENT,
ROLE_ID SMALLINT(2) NOT NULL,
FEATURE_ID  SMALLINT(2) NOT NULL,
DISPLAY_ORDER SMALLINT(3) NOT NULL,
CONSTRAINT RFID PRIMARY KEY(ID),
CONSTRAINT FK_ROLE_ID FOREIGN KEY (ROLE_ID)
    REFERENCES PG_USER_ROLES(ROLE_ID),
CONSTRAINT FK_FEATURE_ID FOREIGN KEY (FEATURE_ID)
    REFERENCES PG_FEATURES(FEATURE_ID)
)ENGINE=INNODB DEFAULT CHARSET=latin1;


-- POS Testing records

insert into pg_merchant (MERCHANT_ID, MERCHANT_NAME, MCC, DAILY_TXN_LIMIT, ALLOW_INTERNATIONAL_TXN, MERCHANT_TYPE,  PHONE1, TIN, ADDRESS1, CREATED_DATE, UPDATED_DATE, VALID_FROM_DATE, VALID_TO_DATE, STATUS)
values ('471382812818315', "POS merchant", 'TEST', 10000, 1, 'TEST','9999999999', "TIN000000001", "Virtual Address", now(), now(), now(), now(), 0);


insert into PG_STORE ( STORE_ID, MERCHANT_ID, STORE_NAME, CREATED_DATE, UPDATED_DATE, VALID_FROM_DATE, VALID_TO_DATE, STATUS)
values (41314311, 2, "POS store", now(), now(),now(), now(), 0);

insert into pg_terminal (TERMINAL_ID, MERCHANT_ID, STORE_ID, CREATED_DATE, UPDATED_DATE, VALID_FROM_DATE, VALID_TO_DATE, STATUS)
values (85531523, 2, 41314311 , now(), now(),now(), now(), 0);


INSERT INTO PG_ROLE_FEATURE_MAPPING (ROLE_ID, FEATURE_ID, DISPLAY_ORDER) 
VALUES 
(1, 1, 1),
(1, 2, 2),
(1, 3, 3),
(1, 4, 4),
(2, 1, 1),
(2, 2, 2),
(2, 3, 4),
(2, 4, 3);

-- 19
-- Table structure for table `PG_USER_ACCOUNT`
--
CREATE TABLE PG_USER_ACCOUNT (
	ID BIGINT(20) NOT NULL  AUTO_INCREMENT,
	ACCOUNT_NUMBER VARCHAR(20) NOT NULL,
	ACC_HOLDER_ID BIGINT(20) NOT NULL,
	ACCOUNT_TYPE VARCHAR(50) NOT NULL,
	CURRENCY VARCHAR(5),
	STATUS  INTEGER(1) NOT NULL DEFAULT 0 CHECK('STATUS' IN (0,1,2,3,4,5,6)),
	DELETED  INTEGER(1) NOT NULL DEFAULT 0 CHECK('DELETED' IN (0,1)),
	CREATED_DATE TIMESTAMP NOT NULL,
	CREATED_BY BIGINT(20) NOT NULL,
	UPDATED_DATE TIMESTAMP,
	UPDATED_BY BIGINT(20),
	CONSTRAINT PG_ACCOUNT_ID_PK PRIMARY KEY (ID)
)ENGINE=INNODB DEFAULT CHARSET=LATIN1;

-- 20
-- Table structure for table `PG_USER_ACCOUNT_BALANCE`
--
CREATE TABLE PG_USER_ACCOUNT_BALANCE (
	ID BIGINT(20) NOT NULL  AUTO_INCREMENT,
	ACCOUNT_ID BIGINT(20) NOT NULL,
	ACCOUNT_BALANCE BIGINT(20) NOT NULL,
	CREATED_DATE TIMESTAMP NOT NULL,
	CREATED_BY BIGINT(20) NOT NULL,
	UPDATED_DATE TIMESTAMP,
	UPDATED_BY BIGINT(20),
	CONSTRAINT PG_ACCOUNT_BALANCE_ID_PK PRIMARY KEY (ID)
)ENGINE=INNODB DEFAULT CHARSET=LATIN1;

ALTER TABLE PG_TRANSACTION ADD COLUMN ACCOUNT_NUMBER VARCHAR(20) DEFAULT NULL;

-- 21
-- Table structure for table `PG_SERVICE_ADMIN_USER`
--
CREATE TABLE PG_SERVICE_ADMIN_USER(
ID  BIGINT(10) NOT NULL  AUTO_INCREMENT,
NAME VARCHAR(100),
USER_EMAIL VARCHAR(100),
P_PASSWORD VARCHAR(200),
PG_SERVICE_TYPE INTEGER(1) NOT NULL DEFAULT 0 CHECK('PG_SERVICE_TYPE' IN (0,1,2,3,4)),
STATUS  INTEGER(1) NOT NULL DEFAULT 0 CHECK('STATUS' IN (0,1,2,3,4,5,6)),
constraint SAID PRIMARY KEY(ID)
)ENGINE=INNODB DEFAULT CHARSET=latin1;
INSERT INTO PG_SERVICE_ADMIN_USER VALUES(1, 'PG Acquirer Admin', 'acqadmin@chatak.com', '5F37EF747797C8D64A41BE8BAF5519D2', 1, 0);
INSERT INTO PG_SERVICE_ADMIN_USER VALUES(2, 'PG Acquirer PG Admin', 'acqpg@chatak.com', '5F37EF747797C8D64A41BE8BAF5519D2', 1, 0);

-- 21
-- Table structure for table `PG_EMV_TRANSACTION`
--
CREATE TABLE PG_EMV_TRANSACTION(
ID  BIGINT(10) NOT NULL AUTO_INCREMENT,
PG_TRANSACTION_ID BIGINT(12) ,
AID VARCHAR(50),
IST VARCHAR(50),
IST1 VARCHAR(50),
AIP VARCHAR(50),
IID VARCHAR(50), 
TVR VARCHAR(50),
AED VARCHAR(50),
FCI VARCHAR(50),
FCIP VARCHAR(50),
TXN_STATUS_INFO VARCHAR(50),
PSL VARCHAR(50),
TAVN VARCHAR(50),
IAD VARCHAR(50),
IFD VARCHAR(50),
APP_CRYPTO VARCHAR(50),
CRYPTO_INFO VARCHAR(50),
TERMINAL_CAPABILITIES VARCHAR(50),
CVMR VARCHAR(50),
TERMINAL_TYPE  VARCHAR(50),
ATC  VARCHAR(50),
UNPRED_NUMBER  VARCHAR(50),
TSN  VARCHAR(50),
TCC  VARCHAR(50),
ISR  VARCHAR(50),
LAN_PREF  VARCHAR(10),
CONSTRAINT EMVTXNID PRIMARY KEY(ID),
CONSTRAINT FK_EMVTXNID FOREIGN KEY (PG_TRANSACTION_ID)
    REFERENCES PG_TRANSACTION(TRANSACTION_ID)
)ENGINE=INNODB DEFAULT CHARSET=latin1;


-- 22
-- Table structure for table `PG_SWITCH`
--

CREATE TABLE PG_SWITCH(
ID  BIGINT(10) NOT NULL AUTO_INCREMENT,
SWITCH_NAME VARCHAR(50) NOT NULL,
SWITCH_TYPE VARCHAR(50) NOT NULL, 
SWITCH_IP VARCHAR(50),
SWITCH_PORT VARCHAR(50),
STATUS SMALLINT(1),
PRIORITY INT(2) DEFAULT 1,
CREATED_BY BIGINT(10),
UPDATED_BY BIGINT(10),
CONSTRAINT SWITCHID PRIMARY KEY(ID)
)ENGINE=INNODB DEFAULT CHARSET=latin1;

INSERT INTO PG_SWITCH VALUES(1, 'Chatak Switch', 'SOCKET', 'localhost', '20134', 1,1,1,1);

-- 23
-- Table structure for table `PG_SWITCH_TRANSACTION`
--
CREATE TABLE PG_SWITCH_TRANSACTION(
ID  BIGINT(10) NOT NULL AUTO_INCREMENT,
TRANSACTION_ID BIGINT(12) UNIQUE,
PG_TRANSACTION_ID BIGINT(12),
SWITCH_ID BIGINT(12) DEFAULT 1,
TXN_AMOUNT BIGINT(12),
CREATED_DATE TIMESTAMP,
STATUS SMALLINT(1),
SETTLEMENT_BATCH_ID BIGINT(10),
SETTLEMENT_BATCH_STATUS SMALLINT(1),
MTI VARCHAR(4) NOT NULL,
PAN VARCHAR(150),
PAN_MASKED VARCHAR(20),
MCC VARCHAR(4),
POS_ENTRY_MODE VARCHAR(3),
CONSTRAINT PGSWITCHTXNID PRIMARY KEY(ID),
CONSTRAINT FK_SWITCHTXNID FOREIGN KEY (PG_TRANSACTION_ID)
    REFERENCES PG_TRANSACTION(TRANSACTION_ID),
    CONSTRAINT FK_SWITCHID FOREIGN KEY (SWITCH_ID)
    REFERENCES PG_SWITCH(ID)
)ENGINE=INNODB DEFAULT CHARSET=latin1;

