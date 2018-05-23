--------------------------------------------------------
------  DDL for Table DEVICE
--------------------------------------------------------
CREATE TABLE `DEVICE` (
  `DEVICE_ID` int(11) NOT NULL AUTO_INCREMENT,
  `DEVICE_TYPE` varchar(40) DEFAULT NULL,
  `DEVICE_MAKE` varchar(40) DEFAULT NULL,
  `DEVICE_MODEL` varchar(40) DEFAULT NULL,
  `STATUS` varchar(40) DEFAULT NULL,
  `CREATED_BY` varchar(50) DEFAULT NULL,
  `UPDATED_BY` varchar(50) DEFAULT NULL,
  `CREATED_DATE` datetime DEFAULT NULL,
  `UPDATED_DATE` datetime DEFAULT NULL,
  PRIMARY KEY (`DEVICE_ID`),
  UNIQUE KEY `TABLE1_PK` (`DEVICE_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--------------------------------------------------------
------  DDL for Table PG_ACCOUNT
--------------------------------------------------------
CREATE TABLE `PG_ACCOUNT` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `ENTITY_ID` varchar(50) DEFAULT NULL,
  `ENTITY_TYPE` varchar(50) DEFAULT NULL,
  `ACCOUNT_DESC` varchar(100) DEFAULT NULL,
  `CATEGORY` varchar(100) DEFAULT NULL,
  `CURRENT_BALANCE` bigint(20) DEFAULT '0',
  `AVAILABLE_BALANCE` bigint(20) DEFAULT '0',
  `CURRENCY` varchar(20) DEFAULT 'USD',
  `AUTO_PAYMENT_LIMIT` bigint(20) DEFAULT NULL,
  `AUTO_PAYMENT_METHOD` varchar(20) DEFAULT NULL,
  `AUTO_TRANSFER_DAY` varchar(80) DEFAULT NULL,
  `STATUS` varchar(20) DEFAULT NULL,
  `CREATED_DATE` datetime DEFAULT NULL,
  `UPDATED_DATE` datetime DEFAULT NULL,
  `ACCOUNT_NUM` bigint(20) DEFAULT NULL,
  `FEE_BALANCE` bigint(20) DEFAULT '0',
  `REASON` varchar(500) DEFAULT NULL,
  `MERCHANT_BANK_ID` bigint(20) DEFAULT NULL,
  `AUTO_SETTLEMENT` tinyint(4) DEFAULT '0',
  `EMAIL_NOTIFICATION_FLAG` tinyint(4) DEFAULT NULL,
  `EMAIL` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `SYS_C0023118` (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=133 DEFAULT CHARSET=latin1;

--------------------------------------------------------
------  DDL for Table PG_ACCOUNT_FEE_LOG
--------------------------------------------------------
CREATE TABLE `PG_ACCOUNT_FEE_LOG` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `ENTITY_ID` varchar(50) DEFAULT NULL,
  `ENTITY_TYPE` varchar(50) DEFAULT NULL,
  `ACCOUNT_DESC` varchar(100) DEFAULT NULL,
  `CATEGORY` varchar(100) DEFAULT NULL,
  `CURRENCY` varchar(20) DEFAULT 'USD',
  `STATUS` varchar(20) DEFAULT NULL,
  `CREATED_DATE` datetime DEFAULT NULL,
  `UPDATED_DATE` datetime DEFAULT NULL,
  `ACCOUNT_NUM` bigint(20) DEFAULT NULL,
  `PAYMENT_METHOD` varchar(20) DEFAULT NULL,
  `TRANSACTION_ID` bigint(20) DEFAULT NULL,
  `PARENT_ENTITY_ID` varchar(50) DEFAULT NULL,
  `MERCHANT_FEE` bigint(20) DEFAULT '0',
  `CHATAK_FEE` bigint(20) DEFAULT '0',
  `TXN_AMOUNT` bigint(20) DEFAULT '0',
  `FEE_POST_STATUS` tinyint(4) DEFAULT '1',
  `PARTNER_ACCOUNT_NUM` varchar(50) DEFAULT NULL,
  `AGENT_ACCOUNT_NUM` varchar(50) DEFAULT NULL,
  `ISSUANCE_MESSAGE` varchar(500) DEFAULT NULL,
  `ISSUANCE_FEE_TXN_ID` varchar(25) DEFAULT NULL,
  `FEE_TXN_DATE` datetime DEFAULT NULL,
  `SPECIFIC_ACCOUNT_NUM` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `SYS_C0023128` (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=1524 DEFAULT CHARSET=latin1;

--------------------------------------------------------
------  DDL for Table PG_ACCOUNT_H
--------------------------------------------------------
CREATE TABLE `PG_ACCOUNT_H` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `ENTITY_ID` varchar(50) DEFAULT NULL,
  `ENTITY_TYPE` varchar(50) DEFAULT NULL,
  `ACCOUNT_DESC` varchar(100) DEFAULT NULL,
  `CATEGORY` varchar(100) DEFAULT NULL,
  `CURRENT_BALANCE` bigint(20) DEFAULT '0',
  `AVAILABLE_BALANCE` bigint(20) DEFAULT '0',
  `CURRENCY` varchar(20) DEFAULT 'USD',
  `AUTO_PAYMENT_LIMIT` bigint(20) DEFAULT NULL,
  `AUTO_PAYMENT_METHOD` varchar(20) DEFAULT NULL,
  `AUTO_TRANSFER_DAY` varchar(80) DEFAULT NULL,
  `STATUS` varchar(20) DEFAULT NULL,
  `CREATED_DATE` datetime DEFAULT NULL,
  `UPDATED_DATE` datetime DEFAULT NULL,
  `ACCOUNT_NUM` bigint(20) DEFAULT NULL,
  `FEE_BALANCE` bigint(20) DEFAULT '0',
  `REASON` varchar(500) DEFAULT NULL,
  `MERCHANT_BANK_ID` bigint(20) DEFAULT NULL,
  `AUTO_SETTLEMENT` tinyint(4) DEFAULT '0',
  `EMAIL_NOTIFICATION_FLAG` tinyint(4) DEFAULT NULL,
  `EMAIL` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=4333 DEFAULT CHARSET=latin1;

--------------------------------------------------------
------  DDL for Table PG_ACCOUNT_HISTORY
--------------------------------------------------------
CREATE TABLE `PG_ACCOUNT_HISTORY` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `ENTITY_ID` varchar(50) NOT NULL,
  `ENTITY_TYPE` varchar(50) NOT NULL,
  `ACCOUNT_DESC` varchar(100) DEFAULT NULL,
  `CATEGORY` varchar(100) DEFAULT NULL,
  `CURRENT_BALANCE` bigint(20) DEFAULT '0',
  `AVAILABLE_BALANCE` bigint(20) DEFAULT '0',
  `CURRENCY` varchar(20) DEFAULT 'USD',
  `AUTO_PAYMENT_LIMIT` bigint(20) DEFAULT NULL,
  `AUTO_PAYMENT_METHOD` varchar(20) DEFAULT NULL,
  `AUTO_TRANSFER_DAY` varchar(80) DEFAULT NULL,
  `STATUS` varchar(20) DEFAULT NULL,
  `UPDATED_DATE` datetime DEFAULT NULL,
  `ACCOUNT_NUM` bigint(20) DEFAULT NULL,
  `FEE_BALANCE` bigint(20) DEFAULT '0',
  `PAYMENT_METHOD` varchar(20) DEFAULT NULL,
  `TRANSACTION_ID` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=837 DEFAULT CHARSET=latin1;

--------------------------------------------------------
------  DDL for Table PG_ACCOUNT_TRANSACTIONS
--------------------------------------------------------

CREATE TABLE `PG_ACCOUNT_TRANSACTIONS` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `ACCOUNT_TRANSACTION_ID` varchar(20) DEFAULT NULL,
  `PG_TRANSACTION_ID` varchar(20) DEFAULT NULL,
  `PG_TRANSFER_ID` varchar(20) DEFAULT NULL,
  `ACCOUNT_NUMBER` varchar(20) DEFAULT NULL,
  `TO_ACCOUNT_NUMBER` varchar(20) DEFAULT NULL,
  `TRANSACTION_TYPE` varchar(20) DEFAULT NULL,
  `MERCHANT_CODE` varchar(50) DEFAULT NULL,
  `TRANSACTION_CODE` varchar(100) DEFAULT NULL,
  `TRANSACTION_TIME` datetime DEFAULT NULL,
  `PROCESSED_TIME` datetime DEFAULT NULL,
  `CREATED_DATE` datetime NOT NULL,
  `UPDATED_DATE` datetime DEFAULT NULL,
  `DESCRIPTION` varchar(150) DEFAULT NULL,
  `DEBIT` bigint(20) DEFAULT '0',
  `CREDIT` bigint(20) DEFAULT '0',
  `CURRENT_BALANCE` bigint(20) DEFAULT NULL,
  `STATUS` varchar(20) DEFAULT NULL,
  `BATCH_ID` varchar(100) DEFAULT NULL,
  `REFUNDABLE_AMOUNT` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `PG_ACCOUNT_TRANSACTIONS_PK` (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=4799 DEFAULT CHARSET=latin1;

--------------------------------------------------------
------  DDL for Table PG_ACCOUNT_TRANSACTIONS_BK
--------------------------------------------------------
CREATE TABLE `PG_ACCOUNT_TRANSACTIONS_BK` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `ACCOUNT_TRANSACTION_ID` varchar(20) DEFAULT NULL,
  `PG_TRANSACTION_ID` varchar(20) DEFAULT NULL,
  `PG_TRANSFER_ID` varchar(20) DEFAULT NULL,
  `ACCOUNT_NUMBER` varchar(20) DEFAULT NULL,
  `TO_ACCOUNT_NUMBER` varchar(20) DEFAULT NULL,
  `TRANSACTION_TYPE` varchar(20) DEFAULT NULL,
  `MERCHANT_CODE` varchar(50) DEFAULT NULL,
  `TRANSACTION_CODE` varchar(100) DEFAULT NULL,
  `TRANSACTION_TIME` datetime DEFAULT NULL,
  `PROCESSED_TIME` datetime DEFAULT NULL,
  `CREATED_DATE` datetime NOT NULL,
  `UPDATED_DATE` datetime DEFAULT NULL,
  `DESCRIPTION` varchar(300) DEFAULT NULL,
  `DEBIT` bigint(20) DEFAULT NULL,
  `CREDIT` bigint(20) DEFAULT NULL,
  `CURRENT_BALANCE` bigint(20) DEFAULT NULL,
  `STATUS` varchar(20) DEFAULT NULL,
  `BATCH_ID` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--------------------------------------------------------
------  DDL for Table PG_ACQUIRER_FEE_CODE
--------------------------------------------------------
CREATE TABLE `PG_ACQUIRER_FEE_CODE` (
  `ACQUIRER_FEE_CODE_ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `ACQUIRER_NAME` varchar(50) DEFAULT NULL,
  `PARTNER_ID` bigint(20) NOT NULL,
  `FEE_PERCENTAGE_ONLY` decimal(20,2) DEFAULT NULL,
  `FLAT_FEE` bigint(20) DEFAULT NULL,
  `UPDATED_BY` varchar(20) DEFAULT NULL,
  `UPDATED_DATE` datetime DEFAULT NULL,
  `MERCHANT_CODE` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`ACQUIRER_FEE_CODE_ID`),
  UNIQUE KEY `PG_ACQUIRER_FEE_CODE_PK` (`ACQUIRER_FEE_CODE_ID`),
  UNIQUE KEY `ACQUIRER_NAME_AND_PARTNER_UK` (`ACQUIRER_NAME`,`PARTNER_ID`,`MERCHANT_CODE`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--------------------------------------------------------
------  DDL for Table PG_ACQUIRER_FEE_VALUE
--------------------------------------------------------
CREATE TABLE `PG_ACQUIRER_FEE_VALUE` (
  `PG_ACQUIRER_FEE_VALUE_ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `CARD_TYPE` varchar(4) DEFAULT NULL,
  `FEE_PROGRAM_ID` bigint(20) DEFAULT NULL,
  `FEE_FLAT_VALUE` bigint(20) DEFAULT NULL,
  `FEE_PERCENTAGE_ONLY` decimal(20,2) DEFAULT NULL,
  `ACCOUNT_TYPE` varchar(20) DEFAULT NULL,
  `ACCOUNT_NUMBER` varchar(200) DEFAULT NULL,
  `CREATED_BY` varchar(20) NOT NULL,
  `UPDATED_BY` varchar(20) DEFAULT NULL,
  `CREATED_DATE` datetime NOT NULL,
  `UPDATED_DATE` datetime DEFAULT NULL,
  `STATUS` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`PG_ACQUIRER_FEE_VALUE_ID`),
  UNIQUE KEY `PG_ACQUIRER_FEE_VALUE_PK` (`PG_ACQUIRER_FEE_VALUE_ID`),
  KEY `PG_ACQUIRER_FEE_VALUE_PG_F_FK1` (`FEE_PROGRAM_ID`),
  CONSTRAINT `PG_ACQUIRER_FEE_VALUE_PG_F_FK1` FOREIGN KEY (`FEE_PROGRAM_ID`) REFERENCES `PG_FEE_PROGRAM` (`FEE_PROGRAM_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=latin1;

--------------------------------------------------------
------  DDL for Table PG_ACTION_CODE_PARAMETERS
--------------------------------------------------------
CREATE TABLE `PG_ACTION_CODE_PARAMETERS` (
  `ACTION_CODE_ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `ACTION_CODE_NAME` varchar(255) DEFAULT NULL,
  `CREATED_BY` varchar(255) NOT NULL,
  `CREATED_DATE` datetime NOT NULL,
  PRIMARY KEY (`ACTION_CODE_ID`),
  UNIQUE KEY `PG_ACTION_CODE_PARAMETERS_PK` (`ACTION_CODE_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=latin1;

--------------------------------------------------------
------  DDL for Table PG_ACTIVITY_LOG
--------------------------------------------------------
CREATE TABLE `PG_ACTIVITY_LOG` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `SYS_TRACE_NUM` bigint(20) DEFAULT NULL,
  `REQUEST_IP` varchar(20) DEFAULT NULL,
  `REQUEST_PORT` int(11) DEFAULT NULL,
  `RESPONSE_PORT` int(11) DEFAULT NULL,
  `POS_ENTRY_MODE` varchar(20) DEFAULT NULL,
  `CHIP_TRANSACTION` tinyint(4) DEFAULT '0',
  `PROCESSING_CODE` varchar(20) DEFAULT NULL,
  `RESPONSE_CODE` varchar(20) DEFAULT NULL,
  `F39` varchar(10) DEFAULT NULL,
  `TXN_AMOUNT` decimal(10,2) DEFAULT NULL,
  `ADJ_AMOUNT` decimal(10,2) DEFAULT NULL,
  `MTI` varchar(4) DEFAULT NULL,
  `PAN` varchar(150) DEFAULT NULL,
  `PAN_MASKED` varchar(20) DEFAULT NULL,
  `EXP_DATE` varchar(50) DEFAULT NULL,
  `POS_TXN_DATE` varchar(4) DEFAULT NULL,
  `POS_TXN_TIME` varchar(6) DEFAULT NULL,
  `MCC` varchar(4) DEFAULT NULL,
  `TXN_COUNTRY_CODE` varchar(3) DEFAULT NULL,
  `TXN_CURRENCY_CODE` varchar(3) DEFAULT NULL,
  `AI_COUNTRY_CODE` varchar(3) DEFAULT NULL,
  `PAN_COUNTRY_CODE` varchar(3) DEFAULT NULL,
  `FWD_COUNTRY_CODE` varchar(3) DEFAULT NULL,
  `F55` varchar(1000) DEFAULT NULL,
  `REQ_DE07_TRANSMISSION_DATE` varchar(20) DEFAULT NULL,
  `DE37_RRN` varchar(50) DEFAULT NULL,
  `DE38_AUTH_ID` varchar(50) DEFAULT NULL,
  `DE41_MID` varchar(50) DEFAULT NULL,
  `DE42_TID` varchar(50) DEFAULT NULL,
  `DE43_ACCEPTOR_LOCATION` varchar(50) DEFAULT NULL,
  `RES_DE04_TXN_AMOUNT` varchar(20) DEFAULT NULL,
  `RES_DE07_TRANSMISSION_DATE` varchar(20) DEFAULT NULL,
  `CREATED_DATE` datetime DEFAULT NULL,
  `UPDATED_DATE` datetime DEFAULT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `PG_ACTIVITY_LOG_PK` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--------------------------------------------------------
------  DDL for Table PG_ADMIN_USER
--------------------------------------------------------
CREATE TABLE `PG_ADMIN_USER` (
  `ADMIN_USER_ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `USER_ROLE_ID` int(11) NOT NULL,
  `EMAIL` varchar(100) NOT NULL,
  `USER_NAME` varchar(50) DEFAULT NULL,
  `FIRST_NAME` varchar(50) NOT NULL,
  `LAST_NAME` varchar(50) DEFAULT NULL,
  `PASSWORD` varchar(200) NOT NULL,
  `EMAIL_TOKEN` varchar(255) DEFAULT '',
  `EMAIL_VERIFIED` tinyint(4) DEFAULT '0',
  `LANGUAGE` varchar(10) DEFAULT '',
  `ADDRESS1` varchar(500) NOT NULL,
  `ADDRESS2` varchar(500) DEFAULT '',
  `CITY` varchar(50) DEFAULT '',
  `STATE` varchar(50) DEFAULT '',
  `COUNTRY` varchar(50) DEFAULT '',
  `ZIP` varchar(20) DEFAULT NULL,
  `PHONE` varchar(20) DEFAULT NULL,
  `LAST_LOGIN_IP` varchar(50) DEFAULT '',
  `PREVIOUS_PASSWORDS` varchar(1000) DEFAULT NULL,
  `PASS_RETRY_COUNT` tinyint(4) DEFAULT '0',
  `STATUS` tinyint(4) DEFAULT '1',
  `USER_TYPE` varchar(20) DEFAULT NULL,
  `SECURITY_QUESTION` varchar(255) DEFAULT NULL,
  `SECURITY_ANSWER` varchar(255) DEFAULT NULL,
  `SERVICE_TYPE` int(11) DEFAULT NULL,
  `CREATED_DATE` datetime NOT NULL,
  `UPDATED_DATE` datetime DEFAULT NULL,
  `LAST_PASSWORD_CHANGE` datetime DEFAULT NULL,
  `LAST_LOGIN` datetime DEFAULT NULL,
  `CREATED_BY` varchar(50) NOT NULL,
  `UPDATED_BY` varchar(50) DEFAULT NULL,
  `REASON` varchar(100) DEFAULT NULL,
  `USER_ROLE_TYPE` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`ADMIN_USER_ID`),
  UNIQUE KEY `ADMIN_USER_PK` (`ADMIN_USER_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=46 DEFAULT CHARSET=latin1;

--------------------------------------------------------
------  DDL for Table PG_AID
--------------------------------------------------------
CREATE TABLE `PG_AID` (
  `APPLICATION_ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `APPLICATION_NAME` varchar(255) DEFAULT NULL,
  `CREATED_BY` varchar(255) NOT NULL,
  `CREATED_DATE` datetime NOT NULL,
  PRIMARY KEY (`APPLICATION_ID`),
  UNIQUE KEY `PG_AID_PK` (`APPLICATION_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=latin1;

--------------------------------------------------------
------  DDL for Table PG_APPLICATION_CLIENT
--------------------------------------------------------
CREATE TABLE `PG_APPLICATION_CLIENT` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `APP_NAME` varchar(100) NOT NULL,
  `APP_DESCRIPTION` varchar(300) NOT NULL,
  `APP_ADMIN_EMAIL` varchar(150) NOT NULL,
  `APP_CLIENT_NAME` varchar(150) NOT NULL,
  `APP_CLIENT_EMAIL` varchar(150) NOT NULL,
  `APP_CLIENT_PHONE` varchar(20) NOT NULL,
  `APP_CLIENT_ADDRESS` varchar(200) NOT NULL,
  `APP_CLIENT_CITY` varchar(100) NOT NULL,
  `APP_CLIENT_COUNTRY` varchar(10) NOT NULL,
  `APP_CLIENT_ZIP` varchar(12) NOT NULL,
  `APP_CLIENT_ROLE` varchar(1000) NOT NULL,
  `STATUS` tinyint(4) DEFAULT '0',
  `ACTIVE_FROM` datetime NOT NULL,
  `ACTIVE_TILL` datetime NOT NULL,
  `APP_CLIENT_ID` varchar(50) NOT NULL,
  `APP_CLIENT_ACCESS` varchar(100) NOT NULL,
  `APP_AUTH_USER` varchar(50) NOT NULL,
  `APP_AUTH_PASS` varchar(100) DEFAULT NULL,
  `CREATED_BY` varchar(20) NOT NULL,
  `UPDATED_BY` varchar(20) DEFAULT NULL,
  `CREATED_DATE` datetime NOT NULL,
  `UPDATED_DATE` datetime DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

--------------------------------------------------------
------  DDL for Table PG_BANK
--------------------------------------------------------
CREATE TABLE `PG_BANK` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `BANK_NAME` varchar(100) DEFAULT NULL,
  `BANK_SHORT_NAME` varchar(50) DEFAULT NULL,
  `STATUS` varchar(20) DEFAULT NULL,
  `ACQUIRER_ID` varchar(50) DEFAULT NULL,
  `ADDRESS1` varchar(100) DEFAULT NULL,
  `ADDRESS2` varchar(100) DEFAULT NULL,
  `CITY` varchar(100) DEFAULT NULL,
  `STATE` varchar(100) DEFAULT NULL,
  `ZIP` varchar(10) DEFAULT NULL,
  `COUNTRY` varchar(100) DEFAULT NULL,
  `CREATED_DATE` datetime NOT NULL,
  `UPDATED_DATE` datetime DEFAULT NULL,
  `CREATED_BY` varchar(50) NOT NULL,
  `UPDATED_BY` varchar(50) DEFAULT NULL,
  `ACCOUNT_NUM` bigint(20) DEFAULT NULL,
  `CURRENCY_ID` bigint(20) DEFAULT NULL,
  `REASON` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `BANK_PK` (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=latin1;

--------------------------------------------------------
------  DDL for Table PG_BANK_CURRENCY_MAPPING
--------------------------------------------------------
CREATE TABLE `PG_BANK_CURRENCY_MAPPING` (
  `PG_BANK_CUR_MAP_ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `BANK_ID` bigint(20) DEFAULT NULL,
  `CURRENCY_CODE_ALPHA` varchar(3) DEFAULT NULL,
  `STATUS` bigint(20) DEFAULT NULL,
  `CREATED_DATE` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `CREATED_BY` varchar(100) DEFAULT NULL,
  `UPDATED_DATE` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `UPDATED_BY` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`PG_BANK_CUR_MAP_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=53 DEFAULT CHARSET=utf8;

--------------------------------------------------------
------  DDL for Table PG_BIN_RANGE
--------------------------------------------------------
CREATE TABLE `PG_BIN_RANGE` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `BIN` int(11) DEFAULT NULL,
  `CREATED_DATE` datetime DEFAULT NULL,
  `STATUS` tinyint(4) DEFAULT NULL,
  `SWITCH_ID` bigint(20) DEFAULT NULL,
  `DCC_SUPPORTED` bigint(20) DEFAULT NULL,
  `EMV_SUPPORTED` bigint(20) DEFAULT NULL,
  `REASON` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `SYS_C0023114` (`ID`),
  UNIQUE KEY `SYS_C0023115` (`BIN`),
  UNIQUE KEY `BIN` (`BIN`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=latin1;

--------------------------------------------------------
------  DDL for Table PG_BLACKLISTED_CARD
--------------------------------------------------------
CREATE TABLE `PG_BLACKLISTED_CARD` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `CARD_NUM` varchar(50) NOT NULL,
  `CREATED_DATE` datetime DEFAULT NULL,
  `STATUS` tinyint(4) DEFAULT '0',
  `REASON` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `PG_BLACKLISTED_CARD_PK` (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=63 DEFAULT CHARSET=latin1;

--------------------------------------------------------
------  DDL for Table PG_CA_PUBLIC_KEYS
--------------------------------------------------------
CREATE TABLE `PG_CA_PUBLIC_KEYS` (
  `PUBLIC_KEY_ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `PUBLIC_KEY_NAME` varchar(255) DEFAULT NULL,
  `CREATED_BY` varchar(20) NOT NULL,
  `CREATED_DATE` datetime NOT NULL,
  `STATUS` varchar(20) DEFAULT NULL,
  `R_ID` varchar(100) DEFAULT NULL,
  `PUBLIC_KEY_MODULUS` varchar(1000) DEFAULT NULL,
  `PUBLIC_KEY_EXPONENT` double DEFAULT NULL,
  `PUBLIC_KEY_INDEX` varchar(20) DEFAULT NULL,
  `EXPIRY_DATE` varchar(20) DEFAULT NULL,
  `REASON` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`PUBLIC_KEY_ID`),
  UNIQUE KEY `PG_CA_PUBLIC_KEYS_PK` (`PUBLIC_KEY_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

--------------------------------------------------------
------  DDL for Table PG_CARD_TOKEN_DETAILS
--------------------------------------------------------
CREATE TABLE `PG_CARD_TOKEN_DETAILS` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `CARD_USER_EMAIL` varchar(150) NOT NULL,
  `CARD_TOKEN` varchar(100) NOT NULL,
  `CARD_TYPE` varchar(10) NOT NULL,
  `CARD_TOKEN_EXP_DATE` varchar(40) NOT NULL,
  `CARD_LAST_FOUR` varchar(4) NOT NULL,
  `CREATED_DATE` datetime DEFAULT NULL,
  `PG_TOKEN_CUSTOMER_ID` bigint(20) DEFAULT NULL,
  `PAN` varchar(500) DEFAULT NULL,
  `EXPIRY_DATE` varchar(500) DEFAULT NULL,
  `CARD_HOLDER_NAME` varchar(200) DEFAULT NULL,
  `STATUS` varchar(10) DEFAULT 'Active',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--------------------------------------------------------
------  DDL for Table PG_COMMISSION_PROGRAM
--------------------------------------------------------
CREATE TABLE `PG_COMMISSION_PROGRAM` (
  `COMMISSION_PROGRAM_ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `COMMISSION_NAME` varchar(50) NOT NULL,
  `STATUS` varchar(10) DEFAULT NULL,
  `MERCHANT_ON_BORDING_FEE` decimal(10,2) DEFAULT NULL,
  `CREATED_BY` bigint(20) DEFAULT NULL,
  `UPDATED_BY` bigint(20) DEFAULT NULL,
  `CREATED_DATE` datetime DEFAULT NULL,
  `UPDATED_DATE` datetime DEFAULT NULL,
  PRIMARY KEY (`COMMISSION_PROGRAM_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--------------------------------------------------------
------  DDL for Table PG_COMMISSION_PROGRAM_OTHER
--------------------------------------------------------
CREATE TABLE `PG_COMMISSION_PROGRAM_OTHER` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `COMMISSION_PROGRAM_ID` bigint(20) DEFAULT NULL,
  `COMMISSION_TYPE` varchar(20) DEFAULT NULL,
  `FLAT_FEE` decimal(10,2) DEFAULT NULL,
  `FROM_VALUE` decimal(10,2) DEFAULT NULL,
  `TO_VALUE` decimal(10,2) DEFAULT NULL,
  `AMOUNT` decimal(10,2) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--------------------------------------------------------
------  DDL for Table PG_COUNTRY
--------------------------------------------------------
CREATE TABLE `PG_COUNTRY` (
  `ID` bigint(20) NOT NULL,
  `NAME` varchar(255) NOT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `PG_COUNTRY_PK` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--------------------------------------------------------
------  DDL for Table PG_CURRENCY_CODE
--------------------------------------------------------
CREATE TABLE `PG_CURRENCY_CODE` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `COUNTRY_ID` bigint(20) DEFAULT NULL,
  `CODE` varchar(255) DEFAULT NULL,
  `CREATED_BY` varchar(20) NOT NULL,
  `CREATED_DATE` datetime NOT NULL,
  `CURRENCY_NAME` varchar(20) DEFAULT NULL,
  `CURRENCY_CODE_NUMERIC` varchar(3) DEFAULT NULL,
  `CURRENCY_CODE_ALPHA` varchar(3) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `PG_CURRENCY_CODE_PK` (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=latin1;

--------------------------------------------------------
------  DDL for Table PG_CURRENCY_CONFIG
--------------------------------------------------------
CREATE TABLE `PG_CURRENCY_CONFIG` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `CURRENCY_NAME` varchar(20) NOT NULL,
  `CURRENCY_CODE_NUMERIC` varchar(3) NOT NULL,
  `CURRENCY_CODE_ALPHA` varchar(3) NOT NULL,
  `CURRENCY_EXPONENT` bigint(5) NOT NULL,
  `CURRENCY_SEPARATOR_POSITION` bigint(5) NOT NULL,
  `CURRENCY_MINOR_SEPARATOR_UNIT` varchar(1) NOT NULL,
  `CURRENCY_THOUS_SEPARATOR_UNIT` varchar(1) NOT NULL,
  `STATUS` bigint(20) NOT NULL,
  `CREATED_BY` varchar(50) NOT NULL,
  `UPDATED_BY` varchar(50) DEFAULT NULL,
  `CREATED_DATE` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `MODIFIED_DATE` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `REASON` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

--------------------------------------------------------
------  DDL for Table PG_DCC_BIN_RANGE
--------------------------------------------------------
CREATE TABLE `PG_DCC_BIN_RANGE` (
  `PG_DCC_BIN_RANGE_ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `HIGH_CARD_BIN_RANGE` varchar(20) DEFAULT NULL,
  `LOW_CARD_BIN_RANGE` varchar(20) DEFAULT NULL,
  `COUNTRY_CODE` varchar(3) DEFAULT NULL,
  `CURRENCY_CODE` varchar(3) DEFAULT NULL,
  `BIN_NETWORK` varchar(10) DEFAULT NULL,
  `CREATED_DATE` datetime DEFAULT NULL,
  `CREATED_BY` varchar(100) DEFAULT NULL,
  `UPDATED_DATE` datetime DEFAULT NULL,
  `UPDATED_BY` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`PG_DCC_BIN_RANGE_ID`),
  UNIQUE KEY `PG_DCC_BIN_RANGE_PK` (`PG_DCC_BIN_RANGE_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=61 DEFAULT CHARSET=latin1;

--------------------------------------------------------
------  DDL for Table PG_DCC_EXCHANGE_RATE
--------------------------------------------------------
CREATE TABLE `PG_DCC_EXCHANGE_RATE` (
  `PG_DCC_EXCHANGE_RATE_ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `SOURCE_CURRENCY_CODE` varchar(100) DEFAULT NULL,
  `DESTINATION_CURRENCY_CODE` varchar(100) DEFAULT NULL,
  `EXCHANGE_RATE` decimal(10,4) DEFAULT NULL,
  `SOURCE_CURRENCY_DEC_POS` bigint(20) DEFAULT NULL,
  `DESTINATION_CURRENCY_DEC_POS` bigint(20) DEFAULT NULL,
  `CREATED_DATE` datetime DEFAULT NULL,
  `UPDATED_DATE` datetime DEFAULT NULL,
  PRIMARY KEY (`PG_DCC_EXCHANGE_RATE_ID`),
  UNIQUE KEY `SYS_C0035658` (`PG_DCC_EXCHANGE_RATE_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--------------------------------------------------------
------  DDL for Table PG_DEVICE_MANAGEMENT
--------------------------------------------------------
CREATE TABLE `PG_DEVICE_MANAGEMENT` (
  `DEVICE_MANAGEMENT_ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `POS_DEVICE_ID` bigint(20) DEFAULT NULL,
  `IMEI_NO` varchar(255) DEFAULT NULL,
  `APPLICATION_ID` varchar(255) DEFAULT NULL,
  `MAGNETIC_STRIPE_PARAMETERS` varchar(255) DEFAULT NULL,
  `CA_PUBLIC_KEYS` varchar(255) DEFAULT NULL,
  `ACTION_CODE_PARAMETERS` varchar(255) DEFAULT NULL,
  `STAN_NUMBER` varchar(255) DEFAULT NULL,
  `INVOICE_NUMBER` varchar(255) DEFAULT NULL,
  `BATCH_NUMBER` varchar(255) DEFAULT NULL,
  `TIP_PERCENTAGE` varchar(255) DEFAULT NULL,
  `JUST_ALLOWED` varchar(255) DEFAULT NULL,
  `REFUND_ALLOWED` varchar(255) DEFAULT NULL,
  `PREAUTH_ALLOWED` varchar(255) DEFAULT NULL,
  `TIP_ALLOWED` varchar(255) DEFAULT NULL,
  `PROFILE_ID` bigint(20) NOT NULL,
  `STATUS` varchar(255) NOT NULL,
  `TERMINAL_CONFIG_PROFILE` varchar(255) DEFAULT NULL,
  `TERMINAL_ID` bigint(20) NOT NULL,
  `REMARKS` varchar(255) DEFAULT NULL,
  `CREATED_BY` varchar(20) NOT NULL,
  `UPDATED_BY` varchar(20) DEFAULT NULL,
  `CREATED_DATE` datetime NOT NULL,
  `UPDATED_DATE` datetime DEFAULT NULL,
  PRIMARY KEY (`DEVICE_MANAGEMENT_ID`),
  UNIQUE KEY `PG_MOBILE_DEVICE_MGMT_PK` (`DEVICE_MANAGEMENT_ID`),
  KEY `PROFILE_ID` (`PROFILE_ID`),
  KEY `TERMINAL_ID` (`TERMINAL_ID`),
  CONSTRAINT `PG_DEVICE_MANAGEMENT_ibfk_1` FOREIGN KEY (`PROFILE_ID`) REFERENCES `PG_PARAMETER_PROFILE` (`PROFILE_ID`),
  CONSTRAINT `PG_DEVICE_MANAGEMENT_ibfk_2` FOREIGN KEY (`TERMINAL_ID`) REFERENCES `PG_TERMINAL` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--------------------------------------------------------
------  DDL for Table PG_DYNAMIC_MDR
--------------------------------------------------------
CREATE TABLE `PG_DYNAMIC_MDR` (
  `MDR_ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `BIN_NUMBER` bigint(20) DEFAULT NULL,
  `PAYMENT_SCHEME` varchar(255) DEFAULT NULL,
  `BANK` varchar(20) DEFAULT NULL,
  `ACCOUNT_TYPE` varchar(255) DEFAULT NULL,
  `PRODUCT_TYPE` varchar(255) DEFAULT NULL,
  `TRANSACTION_TYPE` varchar(255) DEFAULT NULL,
  `SLAB` decimal(6,2) DEFAULT NULL,
  `CREATED_BY` varchar(20) NOT NULL,
  `UPDATED_BY` varchar(20) DEFAULT NULL,
  `CREATED_DATE` datetime NOT NULL,
  `UPDATED_DATE` datetime DEFAULT NULL,
  PRIMARY KEY (`MDR_ID`),
  UNIQUE KEY `SYS_C0035654` (`MDR_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--------------------------------------------------------
------  DDL for Table PG_EMV_TRANSACTION
--------------------------------------------------------
CREATE TABLE `PG_EMV_TRANSACTION` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `PG_TRANSACTION_ID` bigint(20) DEFAULT NULL,
  `AID` varchar(50) DEFAULT NULL,
  `IST` varchar(50) DEFAULT NULL,
  `IST1` varchar(50) DEFAULT NULL,
  `AIP` varchar(50) DEFAULT NULL,
  `IID` varchar(50) DEFAULT NULL,
  `TVR` varchar(50) DEFAULT NULL,
  `AED` varchar(50) DEFAULT NULL,
  `FCI` varchar(50) DEFAULT NULL,
  `FCIP` varchar(50) DEFAULT NULL,
  `TXN_STATUS_INFO` varchar(50) DEFAULT NULL,
  `PSL` varchar(50) DEFAULT NULL,
  `TAVN` varchar(50) DEFAULT NULL,
  `IAD` varchar(50) DEFAULT NULL,
  `IFD` varchar(50) DEFAULT NULL,
  `APP_CRYPTO` varchar(50) DEFAULT NULL,
  `CRYPTO_INFO` varchar(50) DEFAULT NULL,
  `TERMINAL_CAPABILITIES` varchar(50) DEFAULT NULL,
  `CVMR` varchar(50) DEFAULT NULL,
  `TERMINAL_TYPE` varchar(50) DEFAULT NULL,
  `ATC` varchar(50) DEFAULT NULL,
  `UNPRED_NUMBER` varchar(50) DEFAULT NULL,
  `TSN` varchar(50) DEFAULT NULL,
  `TCC` varchar(50) DEFAULT NULL,
  `ISR` varchar(50) DEFAULT NULL,
  `LAN_PREF` varchar(10) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `PG_EMV_TRANSACTION_PK` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--------------------------------------------------------
------  DDL for Table PG_FEATURE
--------------------------------------------------------
CREATE TABLE `PG_FEATURE` (
  `FEATURE_ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `NAME` varchar(50) NOT NULL,
  `FEATURE_LEVEL` tinyint(4) NOT NULL,
  `ROLE_TYPE` varchar(50) DEFAULT NULL,
  `REF_FEATURE_ID` bigint(20) DEFAULT NULL,
  `CREATED_DATE` datetime NOT NULL,
  PRIMARY KEY (`FEATURE_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=163 DEFAULT CHARSET=latin1;

--------------------------------------------------------
------  DDL for Table PG_FEATURES
--------------------------------------------------------
CREATE TABLE `PG_FEATURES` (
  `FEATURE_ID` tinyint(4) NOT NULL AUTO_INCREMENT,
  `FEATURE_NAME` varchar(30) DEFAULT NULL,
  `FEATURE_URL` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`FEATURE_ID`),
  UNIQUE KEY `PG_FEATURES_PK` (`FEATURE_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=latin1;

--------------------------------------------------------
------  DDL for Table PG_FEE_CODE
--------------------------------------------------------
CREATE TABLE `PG_FEE_CODE` (
  `FEE_CODE` bigint(20) NOT NULL AUTO_INCREMENT,
  `FEE_SHORT_CODE` varchar(255) DEFAULT NULL,
  `PROC_CODE_TXN_TYPE` varchar(10) DEFAULT NULL,
  `RESPONSE_CODE` varchar(10) DEFAULT NULL,
  `FEE_DESCRIPTION` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`FEE_CODE`)
) ENGINE=InnoDB AUTO_INCREMENT=74 DEFAULT CHARSET=latin1;

--------------------------------------------------------
------  DDL for Table PG_FEE_DETAIL
--------------------------------------------------------
CREATE TABLE `PG_FEE_DETAIL` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `TXN_TYPE` varchar(20) DEFAULT NULL,
  `FEE_AMOUNT` decimal(10,2) DEFAULT NULL,
  `CREATED_DATE` datetime DEFAULT NULL,
  `STATUS` tinyint(4) DEFAULT '0',
  PRIMARY KEY (`ID`),
  UNIQUE KEY `PG_FEE_DETAIL_PK` (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=latin1;

--------------------------------------------------------
------  DDL for Table PG_FEE_PROGRAM
--------------------------------------------------------
CREATE TABLE `PG_FEE_PROGRAM` (
  `FEE_PROGRAM_ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `FEE_PROGRAM_NAME` varchar(50) DEFAULT NULL,
  `FEE_PROGRAM_DESC` varchar(255) DEFAULT NULL,
  `PROCESSOR` varchar(20) DEFAULT NULL,
  `REASON` varchar(255) DEFAULT NULL,
  `CREATED_BY` varchar(20) NOT NULL,
  `UPDATED_BY` varchar(20) DEFAULT NULL,
  `CREATED_DATE` datetime NOT NULL,
  `UPDATED_DATE` datetime DEFAULT NULL,
  `STATUS` varchar(20) DEFAULT NULL,
  `PG_OTHER_FEE_VALUE_ID` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`FEE_PROGRAM_ID`),
  UNIQUE KEY `FEE_PROGRAM_PK` (`FEE_PROGRAM_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=latin1;

--------------------------------------------------------
------  DDL for Table PG_FEE_PROGRAM_VALUE
--------------------------------------------------------
CREATE TABLE `PG_FEE_PROGRAM_VALUE` (
  `FEE_PROGRAM_VALUE_ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `FEE_CODE` bigint(20) NOT NULL,
  `FEE_PROGRAM_ID` bigint(20) DEFAULT NULL,
  `FEE_MAX_VALUE` bigint(20) DEFAULT NULL,
  `FEE_MIN_VALUE` bigint(20) DEFAULT NULL,
  `FEE_PERCENTAGE_ONLY` bigint(20) DEFAULT NULL,
  `CREATED_BY` varchar(20) NOT NULL,
  `UPDATED_BY` varchar(20) DEFAULT NULL,
  `CREATED_DATE` datetime NOT NULL,
  `UPDATED_DATE` datetime DEFAULT NULL,
  PRIMARY KEY (`FEE_PROGRAM_VALUE_ID`),
  UNIQUE KEY `FEE_PROGRAM_VALUE_PK` (`FEE_PROGRAM_VALUE_ID`),
  KEY `PG_FEE_PROGRAM_VALUE_PG_F_FK1` (`FEE_PROGRAM_ID`),
  CONSTRAINT `PG_FEE_PROGRAM_VALUE_PG_F_FK1` FOREIGN KEY (`FEE_PROGRAM_ID`) REFERENCES `PG_FEE_PROGRAM` (`FEE_PROGRAM_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--------------------------------------------------------
------  DDL for Table PG_FRAUD_BASIC
--------------------------------------------------------
CREATE TABLE `PG_FRAUD_BASIC` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `DENIED_IP` varchar(2000) DEFAULT NULL,
  `DENIED_EMAIL` varchar(2000) DEFAULT NULL,
  `DENIED_BIN` varchar(2000) DEFAULT NULL,
  `DENIED_COUNTRY` varchar(2000) DEFAULT NULL,
  `CREATED_DATE` datetime DEFAULT NULL,
  `UPDATED_DATE` datetime DEFAULT NULL,
  `MERCHANT_ID` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `PG_FRAUD_BASIC_PK` (`ID`),
  KEY `fk_PG_FRAUD_BASIC_1_idx` (`MERCHANT_ID`),
  CONSTRAINT `PG_FRAUD_BASIC_FK1` FOREIGN KEY (`MERCHANT_ID`) REFERENCES `PG_MERCHANT` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--------------------------------------------------------
------  DDL for Table PG_ISO_COUNTRY_CODE
--------------------------------------------------------
CREATE TABLE `PG_ISO_COUNTRY_CODE` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `CODE` varchar(255) DEFAULT NULL,
  `NAME` varchar(255) DEFAULT NULL,
  `CREATED_BY` varchar(20) NOT NULL,
  `CREATED_DATE` datetime NOT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `PG_ISO_COUNTRY_CODE_PK` (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

--------------------------------------------------------
------  DDL for Table PG_LEGAL_ENTITY
--------------------------------------------------------
CREATE TABLE `PG_LEGAL_ENTITY` (
  `PG_LEGAL_ENTITY_ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `LEGAL_ENTITY_NAME` varchar(500) DEFAULT NULL,
  `LEGAL_ENTITY_TYPE` varchar(500) DEFAULT NULL,
  `TAX_ID` varchar(500) DEFAULT NULL,
  `FIRST_NAME` varchar(500) DEFAULT NULL,
  `LAST_NAME` varchar(500) DEFAULT NULL,
  `DATE_OF_BIRTH` varchar(10) DEFAULT NULL,
  `SSN` varchar(500) DEFAULT NULL,
  `COUNTRY_OF_RESIDENCE` varchar(20) DEFAULT NULL,
  `COUNTRY_OF_CITIZENSHIP` varchar(20) DEFAULT NULL,
  `HOME_PHONE` varchar(20) DEFAULT NULL,
  `MOBILE_PHONE` varchar(20) DEFAULT NULL,
  `ADDRESS1` varchar(60) DEFAULT NULL,
  `ADDRESS2` varchar(60) DEFAULT NULL,
  `CITY` varchar(60) DEFAULT NULL,
  `STATE` varchar(60) DEFAULT NULL,
  `COUNTRY` varchar(60) DEFAULT NULL,
  `PASSPORT_NUMBER` varchar(20) DEFAULT NULL,
  `CREATED_DATE` datetime DEFAULT NULL,
  `UPDATED_DATE` datetime DEFAULT NULL,
  `ANNUAL_CARD_SALE` bigint(20) DEFAULT NULL,
  `MERCHANT_ID` varchar(200) DEFAULT NULL,
  `PIN` varchar(10) DEFAULT NULL,
  PRIMARY KEY (`PG_LEGAL_ENTITY_ID`),
  UNIQUE KEY `SYS_C0023125` (`PG_LEGAL_ENTITY_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=95 DEFAULT CHARSET=latin1;

--------------------------------------------------------
------  DDL for Table PG_LOCAL_CURRENCY_CODE
--------------------------------------------------------
CREATE TABLE `PG_LOCAL_CURRENCY_CODE` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `COUNTRY_ID` bigint(20) DEFAULT NULL,
  `CODE` varchar(255) DEFAULT NULL,
  `CREATED_BY` varchar(20) NOT NULL,
  `CREATED_DATE` datetime NOT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `PG_LOCAL_CURRENCY_CODE_PK` (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;

--------------------------------------------------------
------  DDL for Table PG_MAGNETIC_STRIPE_PARAMETERS
--------------------------------------------------------
CREATE TABLE `PG_MAGNETIC_STRIPE_PARAMETERS` (
  `MAGNETIC_STRIPE_ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `MAGNETIC_STRIPE_NAME` varchar(255) DEFAULT NULL,
  `CREATED_BY` varchar(20) NOT NULL,
  `CREATED_DATE` datetime NOT NULL,
  PRIMARY KEY (`MAGNETIC_STRIPE_ID`),
  UNIQUE KEY `PG_MAGNETIC_STRIPE_PARAMS_PK` (`MAGNETIC_STRIPE_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=latin1;

--------------------------------------------------------
------  DDL for Table PG_MCC_CODE
--------------------------------------------------------
CREATE TABLE `PG_MCC_CODE` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `MCC_CODE` varchar(45) NOT NULL,
  `DESCRIPTION` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`ID`,`MCC_CODE`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=latin1;

--------------------------------------------------------
------  DDL for Table PG_MERCHANT
--------------------------------------------------------
CREATE TABLE `PG_MERCHANT` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `BUSINESS_NAME` varchar(50) NOT NULL,
  `FIRST_NAME` varchar(50) DEFAULT NULL,
  `LAST_NAME` varchar(50) DEFAULT NULL,
  `PHONE` bigint(20) DEFAULT NULL,
  `FAX` bigint(20) DEFAULT NULL,
  `EMAIL` varchar(100) DEFAULT NULL,
  `ADDRESS1` varchar(60) DEFAULT NULL,
  `ADDRESS2` varchar(60) DEFAULT NULL,
  `CITY` varchar(60) DEFAULT NULL,
  `STATE` varchar(60) DEFAULT NULL,
  `COUNTRY` varchar(60) DEFAULT NULL,
  `PIN` varchar(10) DEFAULT NULL,
  `TIN` varchar(20) DEFAULT NULL,
  `SSN` varchar(20) DEFAULT NULL,
  `TIMEZONE` varchar(50) DEFAULT NULL,
  `APPLICATION_MODE` varchar(50) DEFAULT NULL,
  `USER_NAME` varchar(50) DEFAULT NULL,
  `BUSINESS_URL` varchar(200) DEFAULT NULL,
  `FEDERAL_TAX_ID` varchar(50) DEFAULT NULL,
  `STATE_TAX_ID` varchar(50) DEFAULT NULL,
  `SALES_TAX_ID` varchar(50) DEFAULT NULL,
  `OWNERSHIP` varchar(50) DEFAULT NULL,
  `ESTIMATED_YEAR_SALE` bigint(20) DEFAULT NULL,
  `STATUS` tinyint(4) DEFAULT NULL,
  `BUSINESS_START_DATE` datetime DEFAULT NULL,
  `NO_OF_EMPLOYEE` bigint(20) DEFAULT NULL,
  `ROLE` varchar(50) DEFAULT NULL,
  `CREATED_DATE` datetime DEFAULT NULL,
  `UPDATED_DATE` datetime NOT NULL,
  `MER_CONFIG_ID` bigint(20) NOT NULL,
  `MERCHANT_CODE` varchar(50) DEFAULT NULL,
  `MERCHANT_USER_ID` bigint(20) DEFAULT NULL,
  `MCC` varchar(20) DEFAULT NULL,
  `DAILY_TXN_LIMIT` bigint(20) DEFAULT NULL,
  `ALLOW_INTERNATIONAL_TXN` tinyint(4) DEFAULT '0',
  `MERCHANT_TYPE` varchar(50) DEFAULT NULL,
  `VALID_FROM_DATE` datetime DEFAULT NULL,
  `VALID_TO_DATE` datetime DEFAULT NULL,
  `MERCHANT_CALLBACK_URL` varchar(100) DEFAULT NULL,
  `PARENT_MERCHANT_ID` bigint(20) DEFAULT NULL,
  `LITLE_MID` varchar(50) DEFAULT NULL,
  `LOOKING_FOR` varchar(500) DEFAULT NULL,
  `BUSINESS_TYPE` varchar(100) DEFAULT NULL,
  `MERCHANT_ID_NEW` varchar(200) DEFAULT NULL,
  `PROGRAM_MANAGER_ID` varchar(50) DEFAULT NULL,
  `AGENT_ID` varchar(50) DEFAULT NULL,
  `ISSUANCE_PARTNER_ID` varchar(50) DEFAULT NULL,
  `CREATED_BY` varchar(50) DEFAULT NULL,
  `UPDATED_BY` varchar(50) DEFAULT NULL,
  `AGENT_ACC_NUMBER` varchar(20) DEFAULT NULL,
  `AGENT_CLIENT_ID` varchar(50) DEFAULT NULL,
  `AGENT_ANI` varchar(50) DEFAULT NULL,
  `MERCHANT_CATEGORY` varchar(225) DEFAULT NULL,
  `BANK_ID` bigint(20) DEFAULT NULL,
  `RESELLER_ID` bigint(20) DEFAULT NULL,
  `ALLOW_ADVANCED_FRAUD_FILTER` tinyint(4) DEFAULT NULL,
  `DCC_ENABLE` tinyint(4) DEFAULT '0',
  `LOCAL_CURRENCY` varchar(20) DEFAULT NULL,
  `DECLINED_REASON` varchar(600) DEFAULT NULL,
  `REASON` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `PG_MERCHANT1_PK` (`ID`),
  KEY `PG_MERCHANT_FK1` (`MER_CONFIG_ID`),
  KEY `PG_MERCHANT_FK2` (`MERCHANT_USER_ID`),
  CONSTRAINT `PG_MERCHANT_FK1` FOREIGN KEY (`MER_CONFIG_ID`) REFERENCES `PG_MERCHANT_CONFIG` (`MER_CONFIG_ID`),
  CONSTRAINT `PG_MERCHANT_FK2` FOREIGN KEY (`MERCHANT_USER_ID`) REFERENCES `PG_MERCHANT_USERS` (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=95 DEFAULT CHARSET=latin1;

--------------------------------------------------------
------  DDL for Table PG_MERCHANT_ADVANCED_FRAUD
--------------------------------------------------------
CREATE TABLE `PG_MERCHANT_ADVANCED_FRAUD` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `FILTER_TYPE` varchar(20) DEFAULT NULL,
  `FILTER_ON` varchar(20) DEFAULT NULL,
  `DURATION` varchar(20) DEFAULT NULL,
  `TRANSACTION_LIMIT` varchar(150) DEFAULT NULL,
  `MAX_LIMIT` varchar(150) DEFAULT NULL,
  `ACTION` varchar(20) DEFAULT NULL,
  `MERCHANT_CODE` varchar(50) DEFAULT NULL,
  `CREATED_DATE` datetime NOT NULL,
  `UPDATED_DATE` datetime DEFAULT NULL,
  `CREATED_BY` varchar(50) DEFAULT NULL,
  `UPDATED_BY` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--------------------------------------------------------
------  DDL for Table PG_MERCHANT_BANK
--------------------------------------------------------
CREATE TABLE `PG_MERCHANT_BANK` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `BANK_NAME` varchar(50) DEFAULT NULL,
  `BANK_ACC_NUM` varchar(20) DEFAULT NULL,
  `BANK_CODE` varchar(20) DEFAULT NULL,
  `ROUTING_NUMBER` varchar(20) DEFAULT NULL,
  `CURRENCY_CODE` varchar(6) DEFAULT NULL,
  `ACCOUNT_TYPE` varchar(20) DEFAULT NULL,
  `STATUS` tinyint(4) DEFAULT '0',
  `CREATED_DATE` datetime DEFAULT NULL,
  `UPDATED_DATE` datetime DEFAULT NULL,
  `CREATED_BY` bigint(20) DEFAULT NULL,
  `MERCHANT_CODE` varchar(20) DEFAULT NULL,
  `NAME_ON_ACCOUNT` varchar(150) DEFAULT NULL,
  `ADDRESS1` varchar(100) DEFAULT NULL,
  `ADDRESS2` varchar(60) DEFAULT NULL,
  `CITY` varchar(60) DEFAULT NULL,
  `STATE` varchar(60) DEFAULT NULL,
  `COUNTRY` varchar(60) DEFAULT NULL,
  `MERCHANT_ID` bigint(20) DEFAULT NULL,
  `PIN` varchar(10) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `PG_MERCHANT_BANK_PK` (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=95 DEFAULT CHARSET=latin1;

--------------------------------------------------------
------  DDL for Table PG_MERCHANT_CATEGORY_CODES
--------------------------------------------------------
CREATE TABLE `PG_MERCHANT_CATEGORY_CODES` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `MERCHANT_CATEGORY_CODE` varchar(10) DEFAULT NULL,
  `TRANSACTION_CATEGORY_CODE` varchar(10) DEFAULT NULL,
  `DESCRIPTION` varchar(250) DEFAULT NULL,
  `CREATED_DATE` datetime NOT NULL,
  `UPDATED_DATE` datetime DEFAULT NULL,
  `CREATED_BY` varchar(50) NOT NULL,
  `UPDATED_BY` varchar(50) DEFAULT NULL,
  `STATUS` varchar(20) DEFAULT NULL,
  `REASON` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `MCC_PK` (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=latin1;

--------------------------------------------------------
------  DDL for Table PG_MERCHANT_CONFIG
--------------------------------------------------------
CREATE TABLE `PG_MERCHANT_CONFIG` (
  `MER_CONFIG_ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `FEE_PROGRAM` varchar(100) DEFAULT NULL,
  `PROCESSOR` varchar(100) DEFAULT NULL,
  `REFUND` tinyint(4) DEFAULT NULL,
  `TIP_AMOUNT` tinyint(4) DEFAULT NULL,
  `TAX_AMOUNT` tinyint(4) DEFAULT NULL,
  `SHIPPING_AMT` tinyint(4) DEFAULT NULL,
  `ALLOW_REPEAT_SALE` tinyint(4) DEFAULT NULL,
  `SHOW_RECURRING_BILLING` tinyint(4) DEFAULT NULL,
  `CREATED_DATE` datetime DEFAULT NULL,
  `UPDATED_DATE` datetime DEFAULT NULL,
  `AUTO_SETTLEMENT` tinyint(4) DEFAULT NULL,
  `VIRTUAL_TERMINAL` tinyint(4) DEFAULT NULL,
  `POS_TERMINAL` tinyint(4) DEFAULT NULL,
  `ONLINE_TERMINAL` tinyint(4) DEFAULT NULL,
  `WEBSITE_ADDRESS` varchar(255) DEFAULT NULL,
  `RETURN_URL` varchar(255) DEFAULT NULL,
  `CANCEL_URL` varchar(255) DEFAULT NULL,
  `PAYOUT_AT` varchar(225) DEFAULT NULL,
  `PAY_PAGE_CONFIG` tinyint(4) DEFAULT NULL,
  `NMAS_REQUIRED` tinyint(4) DEFAULT NULL,
  PRIMARY KEY (`MER_CONFIG_ID`),
  UNIQUE KEY `PG_MERCHANT_CONFIG_PK` (`MER_CONFIG_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=95 DEFAULT CHARSET=latin1;

--------------------------------------------------------
------  DDL for Table PG_MERCHANT_CURRENCY_MAPPING
--------------------------------------------------------
CREATE TABLE `PG_MERCHANT_CURRENCY_MAPPING` (
  `PG_MERCHANT_CUR_MAP_ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `MERCHANT_CODE` varchar(100) DEFAULT NULL,
  `CURRENCY_CODE` varchar(100) DEFAULT NULL,
  `STATUS` tinyint(4) DEFAULT NULL,
  `CREATED_DATE` datetime DEFAULT NULL,
  `CREATED_BY` varchar(100) DEFAULT NULL,
  `UPDATED_DATE` datetime DEFAULT NULL,
  `UPDATED_BY` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`PG_MERCHANT_CUR_MAP_ID`),
  UNIQUE KEY `SYS_C0035655` (`PG_MERCHANT_CUR_MAP_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--------------------------------------------------------
------  DDL for Table PG_MERCHANT_DCC_MARKUP
--------------------------------------------------------
CREATE TABLE `PG_MERCHANT_DCC_MARKUP` (
  `PG_MERCHANT_DCC_MARKUP_ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `MERCHANT_CODE` varchar(50) NOT NULL,
  `BASE_CURRENCY` varchar(10) NOT NULL,
  `TRANSACTION_CURRENCY` varchar(10) NOT NULL,
  `MARKUP_FEE` decimal(10,2) NOT NULL,
  `STATUS` tinyint(4) NOT NULL,
  `CREATED_DATE` datetime DEFAULT NULL,
  `UPDATED_DATE` datetime DEFAULT NULL,
  PRIMARY KEY (`PG_MERCHANT_DCC_MARKUP_ID`),
  UNIQUE KEY `SYS_C0035656` (`PG_MERCHANT_DCC_MARKUP_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--------------------------------------------------------
------  DDL for Table PG_MERCHANT_SETTLEMENT
--------------------------------------------------------
CREATE TABLE `PG_MERCHANT_SETTLEMENT` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `MERCAHNT_CODE` varchar(50) DEFAULT NULL,
  `ACCOUNT_NUMBER` varchar(50) DEFAULT NULL,
  `AMOUNT` bigint(20) DEFAULT NULL,
  `STATUS` varchar(20) DEFAULT NULL,
  `CREATED_BY` bigint(20) DEFAULT NULL,
  `UPDATED_BY` bigint(20) DEFAULT NULL,
  `REQUESTED_DATE` datetime DEFAULT NULL,
  `UPDATED_DATE` datetime DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--------------------------------------------------------
------  DDL for Table PG_MERCHANT_USER_ADDRESS
--------------------------------------------------------
CREATE TABLE `PG_MERCHANT_USER_ADDRESS` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `ADDRESS1` varchar(60) DEFAULT NULL,
  `ADDRESS2` varchar(60) DEFAULT NULL,
  `CITY` varchar(60) DEFAULT NULL,
  `STATE` varchar(60) DEFAULT NULL,
  `COUNTRY` varchar(60) DEFAULT NULL,
  `PIN` varchar(10) DEFAULT NULL,
  `MERCHANT_CODE` varchar(50) DEFAULT NULL,
  `MERCHANT_USER_ID` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `SYS_C0023117` (`MERCHANT_USER_ID`),
  UNIQUE KEY `MERCHANT_USER_ID` (`MERCHANT_USER_ID`),
  UNIQUE KEY `MERCHANT_USER_ID_2` (`MERCHANT_USER_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=latin1;

--------------------------------------------------------
------  DDL for Table PG_MERCHANT_USERS
--------------------------------------------------------
CREATE TABLE `PG_MERCHANT_USERS` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `USER_NAME` varchar(100) DEFAULT NULL,
  `P_PASSWORD` varchar(200) DEFAULT NULL,
  `STATUS` tinyint(4) DEFAULT '0',
  `CREATED_DATE` datetime DEFAULT NULL,
  `UPDATED_DATE` datetime DEFAULT NULL,
  `CREATED_BY` varchar(50) DEFAULT NULL,
  `UPDATED_BY` varchar(50) DEFAULT NULL,
  `USER_TYPE` varchar(100) DEFAULT NULL,
  `EMAIL_TOKEN` varchar(255) DEFAULT '',
  `EMAIL_VERIFIED` tinyint(4) DEFAULT '0',
  `LAST_LOGIN_IP` varchar(50) DEFAULT '',
  `PREVIOUS_PASSWORDS` varchar(1000) DEFAULT NULL,
  `PASS_RETRY_COUNT` tinyint(4) DEFAULT '0',
  `SECURITY_QUESTION` varchar(255) DEFAULT NULL,
  `SECURITY_ANSWER` varchar(255) DEFAULT NULL,
  `SERVICE_TYPE` int(11) DEFAULT NULL,
  `LAST_PASSWORD_CHANGE` datetime DEFAULT NULL,
  `LAST_LOGIN` datetime DEFAULT NULL,
  `REASON` varchar(200) DEFAULT NULL,
  `FIRST_NAME` varchar(50) DEFAULT NULL,
  `LAST_NAME` varchar(50) DEFAULT NULL,
  `PHONE` varchar(20) DEFAULT NULL,
  `USER_ROLE_ID` int(11) NOT NULL DEFAULT '2',
  `EMAIL` varchar(100) DEFAULT NULL,
  `PG_MERCHANT_ID` bigint(20) DEFAULT NULL,
  `ADDRESS` varchar(255) DEFAULT NULL,
  `USER_ROLE_TYPE` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `PG_MERCHANT_USERS_PK` (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=111 DEFAULT CHARSET=latin1;

--------------------------------------------------------
------  DDL for Table PG_OAUTH_ACCESS_TOKEN
--------------------------------------------------------
CREATE TABLE `PG_OAUTH_ACCESS_TOKEN` (
  `TOKEN_ID` varchar(256) NOT NULL,
  `TOKEN` blob,
  `AUTHENTICATION_ID` varchar(256) DEFAULT NULL,
  `USER_NAME` varchar(256) DEFAULT NULL,
  `CLIENT_ID` varchar(256) DEFAULT NULL,
  `AUTHENTICATION` blob,
  `REFRESH_TOKEN` varchar(256) DEFAULT NULL,
  PRIMARY KEY (`TOKEN_ID`),
  UNIQUE KEY `AUTHENTICATION_ID_UNIQUE` (`AUTHENTICATION_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--------------------------------------------------------
------  DDL for Table PG_OAUTH_REFRESH_TOKEN
--------------------------------------------------------
CREATE TABLE `PG_OAUTH_REFRESH_TOKEN` (
  `TOKEN_ID` varchar(256) DEFAULT NULL,
  `TOKEN` blob,
  `AUTHENTICATION` blob
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--------------------------------------------------------
------  DDL for Table PG_ONLINE_TXN_LOG
--------------------------------------------------------
CREATE TABLE `PG_ONLINE_TXN_LOG` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `ORDER_ID` varchar(50) DEFAULT NULL,
  `PROCESSOR_TXN_ID` varchar(50) DEFAULT NULL,
  `PG_TXN_ID` bigint(20) DEFAULT NULL,
  `TRANSACTION_TYPE` varchar(20) DEFAULT NULL,
  `REQUEST_IP_PORT` varchar(60) DEFAULT NULL,
  `MERCHANT_ID` varchar(50) DEFAULT NULL,
  `TXN_TOTAL_AMOUNT` bigint(20) DEFAULT NULL,
  `MERCHANT_AMOUNT` bigint(20) DEFAULT NULL,
  `CARD_ASSOCIATION` varchar(10) DEFAULT NULL,
  `TXN_DESCRIPTION` varchar(150) DEFAULT NULL,
  `BILLER_NAME` varchar(150) DEFAULT NULL,
  `BILLER_EMAIL` varchar(100) DEFAULT NULL,
  `BILLER_ADDRESS` varchar(100) DEFAULT NULL,
  `BILLER_ADDRESS2` varchar(100) DEFAULT NULL,
  `BILLER_CITY` varchar(100) DEFAULT NULL,
  `BILLER_STATE` varchar(100) DEFAULT NULL,
  `BILLER_COUNTRY` varchar(50) DEFAULT NULL,
  `BILLER_ZIP` varchar(15) DEFAULT NULL,
  `MERCHANT_RETURN_URL` varchar(500) DEFAULT NULL,
  `PAN_DATA` varchar(150) DEFAULT NULL,
  `PAN_MASKED` varchar(20) DEFAULT NULL,
  `POS_TXN_DATE_TIME` bigint(20) DEFAULT NULL,
  `PROCESSOR_RESPONSE` varchar(500) DEFAULT NULL,
  `TXN_STATE` varchar(20) DEFAULT NULL,
  `TXN_REASON` varchar(250) DEFAULT NULL,
  `REQUEST_DATE_TIME` datetime DEFAULT NULL,
  `RESPONSE_DATE_TIME` datetime DEFAULT NULL,
  `INVOICE_NUMBER` varchar(20) DEFAULT NULL,
  `REGISTER_NUMBER` varchar(20) DEFAULT NULL,
  `TXN_FEE_AMOUNT` bigint(20) DEFAULT NULL,
  `CARD_HOLDER_NAME` varchar(50) DEFAULT NULL,
  `TXN_PAYMENT_PROCESS_TYPE` tinyint(4) DEFAULT NULL,
  `POS_ENTRY_MODE` varchar(50) DEFAULT NULL,
  `MERCHANT_NAME` varchar(100) DEFAULT NULL,
  `APP_MODE` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=3533 DEFAULT CHARSET=latin1;

--------------------------------------------------------
------  DDL for Table PG_OTHER_FEE_VALUE
--------------------------------------------------------
CREATE TABLE `PG_OTHER_FEE_VALUE` (
  `PG_OTHER_FEE_VALUE_ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `SETUP_FEE` bigint(20) DEFAULT NULL,
  `SETTLEMENT_FEE` bigint(20) DEFAULT NULL,
  `CHARGE_BACK_FEE` bigint(20) DEFAULT NULL,
  `CHARGE_FREQUENCY` varchar(20) DEFAULT NULL,
  `CREATED_BY` varchar(20) NOT NULL,
  `UPDATED_BY` varchar(20) DEFAULT NULL,
  `CREATED_DATE` datetime NOT NULL,
  `UPDATED_DATE` datetime DEFAULT NULL,
  PRIMARY KEY (`PG_OTHER_FEE_VALUE_ID`),
  UNIQUE KEY `PG_OTHER_FEE_VALUE_PK` (`PG_OTHER_FEE_VALUE_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=latin1;

--------------------------------------------------------
------  DDL for Table PG_PAN_LENGTH
--------------------------------------------------------
CREATE TABLE `PG_PAN_LENGTH` (
  `PAN_LENGTH_ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `PAN_LENGTH` bigint(20) NOT NULL,
  `CREATED_BY` varchar(20) NOT NULL,
  `CREATED_DATE` datetime NOT NULL,
  PRIMARY KEY (`PAN_LENGTH_ID`),
  UNIQUE KEY `PG_PAN_LENGTH_PK` (`PAN_LENGTH_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=latin1;

--------------------------------------------------------
------  DDL for Table PG_PARAMETER_MAGSTRIPE
--------------------------------------------------------
CREATE TABLE `PG_PARAMETER_MAGSTRIPE` (
  `MAGSTRIPE_ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `CARD_LABEL` varchar(255) DEFAULT NULL,
  `CARD_RANGE_HIGH` bigint(20) DEFAULT NULL,
  `CARD_RANGE_LOW` bigint(20) DEFAULT NULL,
  `MAGSTRIPE_NAME` varchar(255) NOT NULL,
  `PAN_LENGTH` varchar(255) DEFAULT NULL,
  `STATUS` varchar(255) NOT NULL,
  `CREATED_BY` varchar(20) NOT NULL,
  `CREATED_DATE` datetime DEFAULT NULL,
  `UPDATED_BY` varchar(20) DEFAULT NULL,
  `UPDATED_DATE` datetime DEFAULT NULL,
  PRIMARY KEY (`MAGSTRIPE_ID`),
  UNIQUE KEY `PG_PARAMETER_MAGSTRIPE_PK` (`MAGSTRIPE_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--------------------------------------------------------
------  DDL for Table PG_PARAMETER_PROFILE
--------------------------------------------------------
CREATE TABLE `PG_PARAMETER_PROFILE` (
  `PROFILE_ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `ATTENDED` varchar(255) DEFAULT NULL,
  `CURRENCY_CODE` varchar(255) DEFAULT NULL,
  `DEFAULT_CDOL` varchar(255) DEFAULT NULL,
  `DEFAULT_DDOL` varchar(255) DEFAULT NULL,
  `DEFAULT_TDOL` varchar(255) DEFAULT NULL,
  `ISO_COUNTRY_CODE` varchar(255) DEFAULT NULL,
  `LOCAL_CURRENCY_CODE` varchar(255) DEFAULT NULL,
  `MAXT_TARGET_PERCENTAGE` varchar(255) DEFAULT NULL,
  `PROFILE_NAME` varchar(255) DEFAULT NULL,
  `STATUS` varchar(255) DEFAULT NULL,
  `TARGET_PERCENTAGE` varchar(255) DEFAULT NULL,
  `TERMINAL_FLOOR_LIMIT` varchar(255) DEFAULT NULL,
  `TERMINAL_TYPE` varchar(255) DEFAULT NULL,
  `UNATTENDED` varchar(255) DEFAULT NULL,
  `TERMINAL_CAPABILITIES_ID` bigint(20) NOT NULL,
  `FINANCIAL_INSTITUTE` tinyint(4) DEFAULT NULL,
  `MERCHANT` tinyint(4) DEFAULT NULL,
  `ADMINISTRATIVE` varchar(255) DEFAULT NULL,
  `ALPHANUMERIC_SPECIAL_CHAR` varchar(255) DEFAULT NULL,
  `CARD_CAPTURE` varchar(255) DEFAULT NULL,
  `CASH` varchar(255) DEFAULT NULL,
  `CASH_DEPOSITS` varchar(255) DEFAULT NULL,
  `CDA` varchar(255) DEFAULT NULL,
  `COMMAND_KEYS` varchar(255) DEFAULT NULL,
  `DDA` varchar(255) DEFAULT NULL,
  `DISPLAY_ATTENDANT` varchar(255) DEFAULT NULL,
  `DISPLAY_CARDHOLDER` varchar(255) DEFAULT NULL,
  `ENCIPHERED_PIN_FOR_OFFLINE` varchar(255) DEFAULT NULL,
  `ENCIPHERED_PIN_FOR_ONLINE` varchar(255) DEFAULT NULL,
  `FUNCTION_KEYS` varchar(255) DEFAULT NULL,
  `GOODS` varchar(255) DEFAULT NULL,
  `IC_WITH_CONTACTS` varchar(255) DEFAULT NULL,
  `INQUIRY` varchar(255) DEFAULT NULL,
  `MAGNETIC_STRIPES` varchar(255) DEFAULT NULL,
  `MANUAL_ENTRY_KEY` varchar(255) DEFAULT NULL,
  `NO_CVM_REQUIRED` varchar(255) DEFAULT NULL,
  `NUMERIC_KEYS` varchar(255) DEFAULT NULL,
  `OPERATED_BY` varchar(255) DEFAULT NULL,
  `PAYMENT` varchar(255) DEFAULT NULL,
  `PLAIN_TEXT_PIN_FOR_ICC` varchar(255) DEFAULT NULL,
  `PRINT_ATTENDANT` varchar(255) DEFAULT NULL,
  `PRINT_CARDHOLDER` varchar(255) DEFAULT NULL,
  `SDA` varchar(255) DEFAULT NULL,
  `SERVICES` varchar(255) DEFAULT NULL,
  `SIGNATURE` varchar(255) DEFAULT NULL,
  `TRANSFER` varchar(255) DEFAULT NULL,
  `ATTEND_FINANCIAL_INSTITUTE` tinyint(4) DEFAULT NULL,
  `ATTEND_MERCHANT` tinyint(4) DEFAULT NULL,
  `UNATTEND_MERCHANT` tinyint(4) DEFAULT NULL,
  `UNATTEND_FINANCIAL_INSTITUTE` tinyint(4) DEFAULT NULL,
  `PARAMETER_TYPE` varchar(255) DEFAULT NULL,
  `REASON` varchar(250) DEFAULT '',
  `CREATED_BY` varchar(255) NOT NULL,
  `CREATED_DATE` datetime NOT NULL,
  `UPDATED_BY` varchar(255) DEFAULT NULL,
  `UPDATED_DATE` datetime DEFAULT NULL,
  `UNATTEND_CARDHOLDER` tinyint(4) DEFAULT NULL,
  PRIMARY KEY (`PROFILE_ID`),
  UNIQUE KEY `PG_PARAMETER_PROFILE_PK` (`PROFILE_ID`),
  KEY `TERMINAL_CAPABILITIES_ID` (`TERMINAL_CAPABILITIES_ID`),
  CONSTRAINT `PG_PARAMETER_PROFILE_ibfk_1` FOREIGN KEY (`TERMINAL_CAPABILITIES_ID`) REFERENCES `PG_TERMINAL_CAPABILITIES` (`TERMINAL_CAPABILITIES_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--------------------------------------------------------
------  DDL for Table PG_PARAMS
--------------------------------------------------------
CREATE TABLE `PG_PARAMS` (
  `ID` smallint(6) NOT NULL AUTO_INCREMENT,
  `PARAM_NAME` varchar(100) DEFAULT NULL,
  `PARAM_VALUE` varchar(1000) DEFAULT NULL,
  `STATUS` tinyint(4) DEFAULT '1',
  PRIMARY KEY (`ID`),
  UNIQUE KEY `SYS_C0023126` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--------------------------------------------------------
------  DDL for Table PG_PARTNER_FEE_CODE
--------------------------------------------------------
CREATE TABLE `PG_PARTNER_FEE_CODE` (
  `PARTNER_FEE_CODE_ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `PARTNER_NAME` varchar(50) DEFAULT NULL,
  `PARTNER_ENTITY_ID` varchar(50) NOT NULL,
  `PARTNER_ENTITY_TYPE` varchar(50) DEFAULT NULL,
  `FEE_PERCENTAGE_ONLY` bigint(20) DEFAULT NULL,
  `FLAT_FEE` bigint(20) DEFAULT NULL,
  `CREATED_BY` varchar(20) NOT NULL,
  `UPDATED_BY` varchar(20) DEFAULT NULL,
  `CREATED_DATE` datetime NOT NULL,
  `UPDATED_DATE` datetime DEFAULT NULL,
  `ACCOUNT_NUM` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`PARTNER_FEE_CODE_ID`),
  UNIQUE KEY `PARTNER_ENTITY_ID_UK` (`PARTNER_ENTITY_ID`),
  UNIQUE KEY `PG_PARTNER_FEE_CODE_PK` (`PARTNER_FEE_CODE_ID`),
  UNIQUE KEY `ACCOUNT_NUM_UK` (`ACCOUNT_NUM`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--------------------------------------------------------
------  DDL for Table PG_PAYMENT_SCHEME
--------------------------------------------------------
CREATE TABLE `PG_PAYMENT_SCHEME` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `PAYMENT_SCHEME_NAME` varchar(50) NOT NULL,
  `CONTACT_NAME` varchar(50) DEFAULT NULL,
  `CONTACT_EMAIL` varchar(100) NOT NULL,
  `CONTACT_PHONE` varchar(20) DEFAULT NULL,
  `RID` varchar(100) DEFAULT NULL,
  `TYPE_OF_CARD` varchar(25) DEFAULT NULL,
  `STATUS` bigint(20) NOT NULL,
  `CREATED_BY` bigint(20) DEFAULT NULL,
  `CREATED_DATE` datetime NOT NULL,
  `UPDATED_BY` bigint(20) DEFAULT NULL,
  `UPDATED_DATE` datetime DEFAULT NULL,
  `REASON` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `PG_PAYMENT_SCHEME_PK` (`ID`),
  UNIQUE KEY `PG_PAYMENT_SCHEME_UK1` (`CONTACT_EMAIL`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

--------------------------------------------------------
------  DDL for Table PG_PAY_PAGE_CONFIG
--------------------------------------------------------
CREATE TABLE `PG_PAY_PAGE_CONFIG` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `MERCHANT_ID` bigint(20) DEFAULT NULL,
  `HEADER_TEXT` varchar(100) DEFAULT NULL,
  `FOOTER_TEXT` varchar(100) DEFAULT NULL,
  `LOGO` longblob,
  `CREATED_DATE` datetime NOT NULL,
  `UPDATED_DATE` datetime DEFAULT NULL,
  `CREATED_BY` varchar(50) NOT NULL,
  `UPDATED_BY` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `PAY_PAGE_CONFIG_PK` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--------------------------------------------------------
------  DDL for Table PG_POS_DEVICE
--------------------------------------------------------
CREATE TABLE `PG_POS_DEVICE` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `DEVICE_SERIAL_NO` varchar(255) NOT NULL,
  `DEVICE_TYPE` varchar(255) NOT NULL,
  `DEVICE_MAKE` varchar(255) NOT NULL,
  `DEVICE_MODEL` varchar(255) NOT NULL,
  `STATUS` varchar(255) NOT NULL,
  `CREATED_BY` varchar(20) NOT NULL,
  `CREATED_DATE` datetime DEFAULT NULL,
  `UPDATED_BY` varchar(20) DEFAULT NULL,
  `UPDATED_DATE` datetime DEFAULT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `PG_POS_DEVICE_PK` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--------------------------------------------------------
------  DDL for Table PG_RECURRING_CONTRACT_INFO
--------------------------------------------------------
CREATE TABLE `PG_RECURRING_CONTRACT_INFO` (
  `RECURRING_CONTRACT_INFO_ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `CONTRACT_ID` varchar(50) DEFAULT NULL,
  `CONTRACT_NAME` varchar(50) NOT NULL,
  `START_DATE` datetime DEFAULT NULL,
  `END_DATE` datetime DEFAULT NULL,
  `CONTACT_AMOUNT` decimal(15,2) DEFAULT NULL,
  `TAX` decimal(15,2) DEFAULT NULL,
  `STATUS` varchar(20) DEFAULT NULL,
  `CONTRACT_EXECUTION` varchar(20) DEFAULT NULL,
  `RECURRING_PAYMENT_INFO_ID` bigint(20) DEFAULT NULL,
  `TRANSACTION_APPROVED_EMAIL` varchar(10) DEFAULT NULL,
  `TRANSACTION_DECLINED_EMAIL` varchar(10) DEFAULT NULL,
  `CREATED_DATE` datetime DEFAULT NULL,
  `UPDATED_DATE` datetime DEFAULT NULL,
  `CREATED_BY` varchar(20) NOT NULL,
  `UPDATED_BY` varchar(20) DEFAULT NULL,
  `LAST_BILL_DATE` datetime DEFAULT NULL,
  `CONTRACT_EXECUTION_REPROCESS` varchar(10) DEFAULT NULL,
  PRIMARY KEY (`RECURRING_CONTRACT_INFO_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--------------------------------------------------------
------  DDL for Table PG_RECURRING_CUSTOMER_INFO
--------------------------------------------------------
CREATE TABLE `PG_RECURRING_CUSTOMER_INFO` (
  `RECURRING_CUSTOMER_INFO_ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `CUSTOMER_ID` varchar(50) NOT NULL,
  `FIRST_NAME` varchar(50) NOT NULL,
  `LAST_NAME` varchar(50) NOT NULL,
  `EMAIL_ID` varchar(50) DEFAULT NULL,
  `MOBILE_NUMBER` varchar(20) DEFAULT NULL,
  `WORK_PHONE` varchar(20) DEFAULT NULL,
  `FAX` varchar(20) DEFAULT NULL,
  `ADDRESS1` varchar(255) DEFAULT NULL,
  `ADDRESS2` varchar(255) DEFAULT NULL,
  `ADDRESS3` varchar(255) DEFAULT NULL,
  `AREA` varchar(50) DEFAULT NULL,
  `CITY` varchar(20) DEFAULT NULL,
  `STATE` varchar(20) DEFAULT NULL,
  `COUNTRY` varchar(20) DEFAULT NULL,
  `ZIPCODE` varchar(20) DEFAULT NULL,
  `STATUS` varchar(20) DEFAULT NULL,
  `CREATED_DATE` datetime DEFAULT NULL,
  `UPDATED_DATE` datetime DEFAULT NULL,
  `CREATED_BY` varchar(20) NOT NULL,
  `UPDATED_BY` varchar(20) DEFAULT NULL,
  `TERMS_FLAG` varchar(4) DEFAULT NULL,
  `TERMS_VERSION` varchar(10) DEFAULT NULL,
  `PG_MERCHANT_ID` bigint(20) NOT NULL,
  `DAYTIME_PHONE` varchar(20) DEFAULT NULL,
  `EVENING_PHONE` varchar(20) DEFAULT NULL,
  `DEPARTMENT` varchar(50) DEFAULT NULL,
  `TITLE` varchar(50) DEFAULT NULL,
  `COMPANY` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`RECURRING_CUSTOMER_INFO_ID`),
  UNIQUE KEY `RECURRING_CUSTOMER_INFO_PK` (`RECURRING_CUSTOMER_INFO_ID`),
  KEY `PG_MERCHANT_ID_FK` (`PG_MERCHANT_ID`),
  CONSTRAINT `PG_MERCHANT_ID_FK` FOREIGN KEY (`PG_MERCHANT_ID`) REFERENCES `PG_MERCHANT_USERS` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--------------------------------------------------------
------  DDL for Table PG_RECURRING_PAYMENT_INFO
--------------------------------------------------------
CREATE TABLE `PG_RECURRING_PAYMENT_INFO` (
  `RECURRING_PAYMENT_INFO_ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `RECURRING_CUSTOMER_INFO_ID` bigint(20) NOT NULL,
  `CREDIT_CARD_TYPE` varchar(50) NOT NULL,
  `CARD_NUMBER` varchar(255) DEFAULT NULL,
  `EXP_DT` varchar(10) DEFAULT NULL,
  `NAME_ON_CARD` varchar(50) DEFAULT NULL,
  `STREET_ADDRESS` varchar(255) DEFAULT NULL,
  `ZIP_CODE` varchar(20) DEFAULT NULL,
  `IMMIDIATE_CHARGE` varchar(5) DEFAULT NULL,
  `CREATED_DATE` datetime DEFAULT NULL,
  `UPDATED_DATE` datetime DEFAULT NULL,
  `CREATED_BY` varchar(20) NOT NULL,
  `UPDATED_BY` varchar(20) DEFAULT NULL,
  `STATUS` varchar(20) DEFAULT NULL,
  `AMOUNT` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`RECURRING_PAYMENT_INFO_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--------------------------------------------------------
------  DDL for Table PG_RESELLER
--------------------------------------------------------
CREATE TABLE `PG_RESELLER` (
  `RESELLER_ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `RESELLER_NAME` varchar(100) NOT NULL,
  `CONTACT_NAME` varchar(50) DEFAULT NULL,
  `EMAIL_ID` varchar(100) DEFAULT NULL,
  `PHONE` bigint(20) DEFAULT NULL,
  `ACCOUNT_NUMBER` bigint(20) DEFAULT NULL,
  `ACCOUNT_BALANCE` bigint(20) DEFAULT '0',
  `STATUS` varchar(20) DEFAULT 'Active',
  `ADDRESS1` varchar(100) DEFAULT NULL,
  `ADDRESS2` varchar(100) DEFAULT NULL,
  `CITY` varchar(100) DEFAULT NULL,
  `STATE` varchar(100) DEFAULT NULL,
  `COUNTRY` varchar(100) DEFAULT NULL,
  `ZIP` varchar(10) DEFAULT NULL,
  `CREATED_BY` varchar(50) DEFAULT NULL,
  `UPDATED_BY` varchar(50) DEFAULT NULL,
  `CREATED_DATE` datetime NOT NULL,
  `UPDATED_DATE` datetime NOT NULL,
  PRIMARY KEY (`RESELLER_ID`),
  UNIQUE KEY `PG_RESELLER_PK` (`RESELLER_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--------------------------------------------------------
------  DDL for Table PG_ROLE_FEATURE_MAPPING
--------------------------------------------------------
CREATE TABLE `PG_ROLE_FEATURE_MAPPING` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `ROLE_ID` bigint(20) NOT NULL,
  `FEATURE_ID` tinyint(4) NOT NULL,
  `DISPLAY_ORDER` smallint(6) NOT NULL,
  `SUB_FEATURE_ID` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `PG_ROLE_FEATURE_MAPPING_PK` (`ID`),
  KEY `PG_ROLE_FEATURE_MAPPING_P_FK2` (`FEATURE_ID`),
  KEY `PG_ROLE_FEATURE_MAPPING_P_FK1` (`ROLE_ID`),
  CONSTRAINT `PG_ROLE_FEATURE_MAPPING_P_FK1` FOREIGN KEY (`ROLE_ID`) REFERENCES `PG_USER_ROLES` (`ROLE_ID`),
  CONSTRAINT `PG_ROLE_FEATURE_MAPPING_P_FK2` FOREIGN KEY (`FEATURE_ID`) REFERENCES `PG_FEATURES` (`FEATURE_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=latin1;

--------------------------------------------------------
------  DDL for Table PG_ROLE_FEATURE_MAPPING_NEW
--------------------------------------------------------
CREATE TABLE `PG_ROLE_FEATURE_MAPPING_NEW` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `USER_ROLE_ID` bigint(20) NOT NULL,
  `FEATURE_ID` bigint(20) NOT NULL,
  `CREATED_DATE` datetime NOT NULL,
  `CREATED_BY` varchar(50) DEFAULT NULL,
  `UPDATED_DATE` datetime NOT NULL,
  `UPDATED_BY` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=3824 DEFAULT CHARSET=latin1;

--------------------------------------------------------
------  DDL for Table PG_SPLIT_TRANSACTION
--------------------------------------------------------
CREATE TABLE `PG_SPLIT_TRANSACTION` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `SPLIT_TRANSACTION_ID` bigint(20) DEFAULT NULL,
  `PG_TRANSACTION_ID` bigint(20) DEFAULT NULL,
  `PG_REF_TRANSACTION_ID` bigint(20) DEFAULT NULL,
  `SPLIT_AMOUNT` bigint(20) DEFAULT NULL,
  `TOTAL_AMOUNT` bigint(20) DEFAULT NULL,
  `MERCHANT_ID` bigint(20) DEFAULT NULL,
  `SPLIT_MODE` varchar(20) DEFAULT NULL,
  `CREATED_DATE` datetime DEFAULT NULL,
  `UPDATED_DATE` datetime DEFAULT NULL,
  `STATUS` tinyint(4) DEFAULT NULL,
  `CARD_HOLDER_NAME` varchar(150) DEFAULT NULL,
  `PAN` varchar(150) DEFAULT NULL,
  `PAN_MASKED` varchar(20) DEFAULT NULL,
  `EXP_DATE` varchar(50) DEFAULT NULL,
  `TXN_DESCRIPTION` varchar(500) DEFAULT NULL,
  `REASON` varchar(500) DEFAULT NULL,
  `MOBILE_NUM` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `SYS_C0023116` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--------------------------------------------------------
------  DDL for Table PG_STATE
--------------------------------------------------------
CREATE TABLE `PG_STATE` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `NAME` varchar(255) NOT NULL,
  `STATUS` varchar(255) DEFAULT NULL,
  `COUNTRY_ID` bigint(20) DEFAULT NULL,
  `STATE_CODE` char(2) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `PG_STATE_PK` (`ID`),
  KEY `COUNTRY_ID` (`COUNTRY_ID`),
  CONSTRAINT `PG_STATE_ibfk_1` FOREIGN KEY (`COUNTRY_ID`) REFERENCES `PG_COUNTRY` (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=163 DEFAULT CHARSET=latin1;

--------------------------------------------------------
------  DDL for Table PG_SUB_FEATURES
--------------------------------------------------------
CREATE TABLE `PG_SUB_FEATURES` (
  `SUB_FEATURE_ID` tinyint(4) NOT NULL AUTO_INCREMENT,
  `FEATURE_ID` tinyint(4) NOT NULL,
  `FEATURE_NAME` varchar(50) DEFAULT NULL,
  `FEATURE_URL` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`SUB_FEATURE_ID`),
  UNIQUE KEY `PG_SUB_FEATURES_PK` (`SUB_FEATURE_ID`),
  KEY `PG_SUB_FEATURES_PG_FEATUR_FK1` (`FEATURE_ID`),
  CONSTRAINT `PG_SUB_FEATURES_PG_FEATUR_FK1` FOREIGN KEY (`FEATURE_ID`) REFERENCES `PG_FEATURES` (`FEATURE_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=26 DEFAULT CHARSET=latin1;

--------------------------------------------------------
------  DDL for Table PG_SWITCH
--------------------------------------------------------
CREATE TABLE `PG_SWITCH` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `SWITCH_NAME` varchar(50) NOT NULL,
  `SWITCH_TYPE` varchar(50) NOT NULL,
  `PRIMARY_SWITCH_URL` varchar(75) DEFAULT NULL,
  `PRIMARY_SWITCH_PORT` varchar(50) DEFAULT NULL,
  `STATUS` tinyint(4) DEFAULT NULL,
  `PRIORITY` tinyint(4) DEFAULT NULL,
  `CREATED_BY` bigint(20) DEFAULT NULL,
  `UPDATED_BY` bigint(20) DEFAULT NULL,
  `SECONDARY_SWITCH_URL` varchar(75) DEFAULT NULL,
  `SECONDARY_SWITCH_PORT` varchar(50) DEFAULT NULL,
  `CREATED_DATE` datetime DEFAULT NULL,
  `UPDATED_DATE` datetime DEFAULT NULL,
  `REASON` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `PG_SWITCH_PK` (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=latin1;

--------------------------------------------------------
------  DDL for Table PG_SWITCH_TRANSACTION
--------------------------------------------------------
CREATE TABLE `PG_SWITCH_TRANSACTION` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `TRANSACTION_ID` bigint(20) DEFAULT NULL,
  `PG_TRANSACTION_ID` bigint(20) DEFAULT NULL,
  `SWITCH_ID` bigint(20) DEFAULT '1',
  `PAN` varchar(150) DEFAULT NULL,
  `PAN_MASKED` varchar(20) DEFAULT NULL,
  `MCC` varchar(4) DEFAULT NULL,
  `POS_ENTRY_MODE` varchar(4) DEFAULT NULL,
  `TXN_AMOUNT` decimal(14,2) DEFAULT NULL,
  `CREATED_DATE` datetime DEFAULT NULL,
  `STATUS` tinyint(4) DEFAULT NULL,
  `SETTLEMENT_BATCH_ID` bigint(20) DEFAULT NULL,
  `SETTLEMENT_BATCH_STATUS` tinyint(4) DEFAULT NULL,
  `TRANSACTION_CODE` varchar(50) DEFAULT NULL,
  `PROCESSOR_RESPONSE` varchar(500) DEFAULT NULL,
  `PROCESSOR_RESPONSE_MSG` varchar(500) DEFAULT NULL,
  `PROCESSOR_RESPONSE_TIME` datetime DEFAULT NULL,
  `PROCESSOR_RESPONSE_POST_DATE` datetime DEFAULT NULL,
  `PROCESSOR_MESSAGE` varchar(500) DEFAULT NULL,
  `PROCESSOR_AUTH_CODE` varchar(100) DEFAULT NULL,
  `PROCESSOR_APPROVED_AMT` bigint(20) DEFAULT NULL,
  `PROCESSOR_TXN_DUPLICATE` varchar(10) DEFAULT NULL,
  `TXN_MODE` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `PG_SWITCH_TRANSACTION_PK` (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=1763 DEFAULT CHARSET=latin1;

--------------------------------------------------------
------  DDL for Table PG_TERMINAL
--------------------------------------------------------
CREATE TABLE `PG_TERMINAL` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `COMMENTS` varchar(255) DEFAULT NULL,
  `MERCHANT_ID` bigint(20) DEFAULT NULL,
  `PRICE` bigint(20) DEFAULT NULL,
  `STATUS` varchar(255) DEFAULT NULL,
  `TERMINAL_ID` bigint(20) NOT NULL,
  `DESCRIPTION` varchar(255) DEFAULT NULL,
  `PRODUCT_ID` varchar(255) DEFAULT NULL,
  `CREATED_BY` varchar(20) NOT NULL,
  `UPDATED_BY` varchar(20) DEFAULT NULL,
  `CREATED_DATE` datetime NOT NULL,
  `UPDATED_DATE` datetime DEFAULT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `PG_TERMINAL_PK1` (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=95 DEFAULT CHARSET=latin1;

--------------------------------------------------------
------  DDL for Table PG_TERMINAL_CAPABILITIES
--------------------------------------------------------
CREATE TABLE `PG_TERMINAL_CAPABILITIES` (
  `TERMINAL_CAPABILITIES_ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `ADMINISTRATIVE` tinyint(4) DEFAULT NULL,
  `ALPHANUMERIC_SPECIAL` tinyint(4) DEFAULT NULL,
  `CARD_CAPTURE` tinyint(4) DEFAULT NULL,
  `CASH` tinyint(4) DEFAULT NULL,
  `CASH_DEPOSITS` tinyint(4) DEFAULT NULL,
  `CDA` tinyint(4) DEFAULT NULL,
  `COMMAND_KEYS` tinyint(4) DEFAULT NULL,
  `DDA` tinyint(4) DEFAULT NULL,
  `DISPLAY_ATTENDANT` tinyint(4) DEFAULT NULL,
  `DISPLAY_CARDHOLDER` tinyint(4) DEFAULT NULL,
  `ENCIPHERED_PIN_FOR_OFFLINE` tinyint(4) DEFAULT NULL,
  `ENCIPHERED_PIN_FOR_ONLINE` tinyint(4) DEFAULT NULL,
  `FINANCIAL_INSTITUTE` tinyint(4) DEFAULT NULL,
  `FUNCTION_KEYS` tinyint(4) DEFAULT NULL,
  `GOODS` tinyint(4) DEFAULT NULL,
  `IC_WITH_CONTACTS` tinyint(4) DEFAULT NULL,
  `INQUIRY` tinyint(4) DEFAULT NULL,
  `MAGNETIC_STRIPES` tinyint(4) DEFAULT NULL,
  `MANUAL_ENTRY_KEY` tinyint(4) DEFAULT NULL,
  `MERCHANT` tinyint(4) DEFAULT NULL,
  `NO_CVM_REQUIRED` tinyint(4) DEFAULT NULL,
  `NUMERIC_KEYS` tinyint(4) DEFAULT NULL,
  `PAYMENT` tinyint(4) DEFAULT NULL,
  `PLAIN_TEXT_PIN_FOR_ICC` tinyint(4) DEFAULT NULL,
  `PRINT_ATTENDANT` tinyint(4) DEFAULT NULL,
  `PRINT_CARDHOLDER` tinyint(4) DEFAULT NULL,
  `SDA` tinyint(4) DEFAULT NULL,
  `SERVICES` tinyint(4) DEFAULT NULL,
  `SIGNATURE` tinyint(4) DEFAULT NULL,
  `TRANSFER` tinyint(4) DEFAULT NULL,
  `PARAMETER_TYPE` varchar(255) DEFAULT NULL,
  `CREATED_BY` varchar(20) NOT NULL,
  `UPDATED_BY` varchar(20) DEFAULT NULL,
  `CREATED_DATE` datetime NOT NULL,
  `UPDATED_DATE` datetime DEFAULT NULL,
  PRIMARY KEY (`TERMINAL_CAPABILITIES_ID`),
  UNIQUE KEY `PG_TERMINAL_CAPABILITIES_PK` (`TERMINAL_CAPABILITIES_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--------------------------------------------------------
------  DDL for Table PG_TOKEN_CUSTOMER
--------------------------------------------------------
CREATE TABLE `PG_TOKEN_CUSTOMER` (
  `PG_TOKEN_CUSTOMER_ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `USER_ID` varchar(50) NOT NULL,
  `PASSWORD` varchar(50) NOT NULL,
  `CREATED_DATE` datetime NOT NULL,
  `UPDATED_DATE` datetime DEFAULT NULL,
  PRIMARY KEY (`PG_TOKEN_CUSTOMER_ID`),
  UNIQUE KEY `PG_TOKEN_CUSTOMER_PK` (`PG_TOKEN_CUSTOMER_ID`),
  UNIQUE KEY `USER_ID_UK` (`USER_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--------------------------------------------------------
------  DDL for Table PG_TRANSACTION
--------------------------------------------------------
CREATE TABLE `PG_TRANSACTION` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `TRANSACTION_ID` bigint(20) DEFAULT NULL,
  `REF_TRANSACTION_ID` bigint(20) DEFAULT NULL,
  `AUTH_ID` bigint(20) DEFAULT NULL,
  `INVOICE_NUMBER` varchar(20) DEFAULT NULL,
  `SYS_TRACE_NUM` bigint(20) DEFAULT NULL,
  `TXN_TYPE` varchar(20) DEFAULT NULL,
  `PAYMENT_METHOD` varchar(20) DEFAULT NULL,
  `TXN_AMOUNT` bigint(20) DEFAULT NULL,
  `ADJ_AMOUNT` bigint(20) DEFAULT NULL,
  `FEE_AMOUNT` bigint(20) DEFAULT NULL,
  `TOTAL_AMOUNT` bigint(20) DEFAULT NULL,
  `TERMINAL_ID` int(11) NOT NULL,
  `ACQ_CHANNEL` varchar(30) NOT NULL DEFAULT 'web',
  `ACQ_TXN_MODE` varchar(40) NOT NULL DEFAULT 'rest',
  `ISSUER_TXN_REF_NUM` bigint(20) DEFAULT NULL,
  `CREATED_DATE` datetime DEFAULT NULL,
  `UPDATED_DATE` datetime DEFAULT NULL,
  `STATUS` tinyint(4) DEFAULT NULL,
  `CHIP_TRANSACTION` tinyint(4) DEFAULT '0',
  `CHIP_FALLBACK_TRANSACTION` tinyint(4) DEFAULT '0',
  `SETTLEMENT_BATCH_ID` bigint(20) DEFAULT NULL,
  `SETTLEMENT_BATCH_STATUS` tinyint(4) DEFAULT NULL,
  `MTI` varchar(4) DEFAULT NULL,
  `PROC_CODE` varchar(6) DEFAULT NULL,
  `PAN` varchar(150) DEFAULT NULL,
  `PAN_MASKED` varchar(20) DEFAULT NULL,
  `EXP_DATE` varchar(50) DEFAULT NULL,
  `POS_TXN_TIME` varchar(6) DEFAULT NULL,
  `POS_TXN_DATE` varchar(4) DEFAULT NULL,
  `MCC` varchar(4) DEFAULT NULL,
  `POS_ENTRY_MODE` varchar(3) DEFAULT NULL,
  `TXN_COUNTRY_CODE` varchar(3) DEFAULT NULL,
  `TXN_CURRENCY_CODE` varchar(3) DEFAULT NULL,
  `AI_COUNTRY_CODE` varchar(3) DEFAULT NULL,
  `PAN_COUNTRY_CODE` varchar(3) DEFAULT NULL,
  `FWD_COUNTRY_CODE` varchar(3) DEFAULT NULL,
  `PROCESSOR` varchar(50) DEFAULT NULL,
  `ACCOUNT_NUMBER` varchar(20) DEFAULT NULL,
  `MERCHANT_SETTLEMENT_ID` bigint(20) DEFAULT NULL,
  `MERCHANT_SETTLEMENT_STATUS` varchar(20) DEFAULT NULL,
  `MERCHANT_FEE_AMOUNT` bigint(20) DEFAULT NULL,
  `TXN_DESCRIPTION` varchar(500) DEFAULT NULL,
  `REASON` varchar(500) DEFAULT NULL,
  `CARD_HOLDER_NAME` varchar(50) DEFAULT NULL,
  `TXN_MODE` varchar(50) DEFAULT NULL,
  `MERCHANT_ID` varchar(200) DEFAULT NULL,
  `EFT_STATUS` varchar(50) DEFAULT NULL,
  `EFT_ID` varchar(50) DEFAULT NULL,
  `EFT_MODE` varchar(50) DEFAULT NULL,
  `CARD_HOLDER_EMAIL` varchar(100) DEFAULT NULL,
  `BATCH_ID` varchar(100) DEFAULT NULL,
  `AUTO_SETTLEMENT_STATUS` varchar(100) DEFAULT NULL,
  `REFUND_STATUS` int(11) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `PG_TRANSACTION_PK` (`ID`),
  UNIQUE KEY `PG_TRANSACTION_UK1` (`TRANSACTION_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=1767 DEFAULT CHARSET=latin1;

--------------------------------------------------------
------  DDL for Table PG_TRANSACTION_CATEGORY_CODES
--------------------------------------------------------
CREATE TABLE `PG_TRANSACTION_CATEGORY_CODES` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `TRANSACTION_CATEGORY_CODE` varchar(5) DEFAULT NULL,
  `DESCRIPTION` varchar(250) DEFAULT NULL,
  `CREATED_DATE` datetime NOT NULL,
  `CREATED_BY` varchar(50) NOT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `TCC_PK` (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=latin1;

--------------------------------------------------------
------  DDL for Table PG_TRANSFERS
--------------------------------------------------------
CREATE TABLE `PG_TRANSFERS` (
  `PG_TRANSFERS_ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `AMOUNT` bigint(20) DEFAULT NULL,
  `FROM_ACCOUNT` varchar(20) DEFAULT NULL,
  `TO_ACCOUNT` varchar(20) DEFAULT NULL,
  `NAME_ON_ACCOUNT` varchar(50) DEFAULT NULL,
  `CITY` varchar(20) DEFAULT NULL,
  `STATE` varchar(20) DEFAULT NULL,
  `ACCOUNT_TYPE` varchar(20) DEFAULT NULL,
  `BANK_ROUTING_NUMBER` varchar(20) DEFAULT NULL,
  `TRANSFER_MODE` varchar(20) DEFAULT NULL,
  `CREATED_DATE` datetime DEFAULT NULL,
  `UPDATED_DATE` datetime DEFAULT NULL,
  `STATUS` varchar(20) DEFAULT NULL,
  `TXN_DESCRIPTION` varchar(500) DEFAULT NULL,
  `MERCHANT_ID` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`PG_TRANSFERS_ID`),
  UNIQUE KEY `SYS_C0023124` (`PG_TRANSFERS_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--------------------------------------------------------
------  DDL for Table PG_USER_ACTIVITY_LOG
--------------------------------------------------------
CREATE TABLE `PG_USER_ACTIVITY_LOG` (
  `ACTIVITY_LOG_ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `USER_ID` varchar(255) DEFAULT NULL,
  `LOGIN_SESSION_ID` varchar(255) DEFAULT NULL,
  `AUDIT_SECTION` varchar(255) DEFAULT NULL,
  `AUDIT_EVENT` varchar(255) DEFAULT NULL,
  `AUDIT_SERVICE` varchar(255) DEFAULT NULL,
  `REQUEST_DATA` varchar(4000) DEFAULT NULL,
  `DATA_CHANGE` varchar(255) DEFAULT NULL,
  `STATUS` varchar(500) DEFAULT NULL,
  `DESCRIPTION` varchar(500) DEFAULT NULL,
  `IP_ADDRESS` varchar(500) DEFAULT NULL,
  `ACTIVITY_DATE` datetime NOT NULL,
  PRIMARY KEY (`ACTIVITY_LOG_ID`),
  UNIQUE KEY `PG_USER_ACTIVITY_LOG_PK` (`ACTIVITY_LOG_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--------------------------------------------------------
------  DDL for Table PG_USER_ROLES
--------------------------------------------------------
CREATE TABLE `PG_USER_ROLES` (
  `ROLE_ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `ROLE_NAME` varchar(30) NOT NULL,
  `STATUS` tinyint(4) DEFAULT '0',
  `ROLE_DESCRIPTION` varchar(100) DEFAULT NULL,
  `CREATED_BY` varchar(20) DEFAULT NULL,
  `CREATED_DATE` datetime DEFAULT NULL,
  `UPDATED_BY` varchar(20) DEFAULT NULL,
  `UPDATED_DATE` datetime DEFAULT NULL,
  `ROLE_TYPE` varchar(20) DEFAULT NULL,
  `REASON` varchar(500) DEFAULT NULL,
  PRIMARY KEY (`ROLE_ID`),
  UNIQUE KEY `PG_USER_ROLES_PK` (`ROLE_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=latin1;

--------------------------------------------------------
------  DDL for Table SYSTEM_CONFIGURATION
--------------------------------------------------------
CREATE TABLE `SYSTEM_CONFIGURATION` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `CONFIG_KEY` varchar(100) DEFAULT NULL,
  `CONFIG_VALUE` varchar(255) DEFAULT NULL,
  `CONFIG_DESCRIPTION` varchar(500) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `SYSTEM_CONFIGURATION_PK` (`ID`),
  UNIQUE KEY `SYSTEM_CONFIGURATION_UNIQUE` (`CONFIG_KEY`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

commit;