BEGIN 
  FOR i IN (SELECT table_name FROM user_tables) 
	LOOP 
	  EXECUTE IMMEDIATE('DROP TABLE ' || user || '.' || i.table_name || ' CASCADE CONSTRAINTS'); 
	END LOOP; 
END;
/       
BEGIN 
  FOR i IN (SELECT sequence_name FROM user_sequences) 
	LOOP 
	  EXECUTE IMMEDIATE('DROP SEQUENCE ' || user || '.' || i.sequence_name); 
	END LOOP; 
END;
/



--------------------------------------------------------
------------ Cleanup Scripts for PG Tables -------------
--------------------------------------------------------

SET FOREIGN_KEY_CHECKS=0;

DELETE FROM PG_ACCOUNT;
DELETE FROM PG_ACCOUNT_FEE_LOG;
DELETE FROM PG_ACCOUNT_H;
DELETE FROM PG_ACCOUNT_HISTORY;
DELETE FROM PG_ACCOUNT_TRANSACTIONS;
DELETE FROM PG_ACQUIRER_FEE_VALUE;
DELETE FROM PG_ACTION_CODE_PARAMETERS WHERE ACTION_CODE_ID NOT IN (1,2,3,4,5);
DELETE FROM PG_ADMIN_USER WHERE ADMIN_USER_ID NOT IN (1,2,3,4,5,6);
DELETE FROM PG_AID where APPLICATION_ID NOT IN (1,2,3,4,5,6, 7);
DELETE FROM PG_APPLICATION_CLIENT WHERE ID NOT IN (1,2);
DELETE FROM PG_BANK;
DELETE FROM PG_BANK_CURRENCY_MAPPING;
DELETE FROM PG_BIN_RANGE;
DELETE FROM PG_BLACKLISTED_CARD;
DELETE FROM PG_CA_PUBLIC_KEYS;
DELETE FROM PG_CURRENCY_CONFIG;
DELETE FROM PG_DCC_BIN_RANGE;
DELETE FROM PG_FEE_PROGRAM;
DELETE FROM PG_FUNDING_REPORT;
DELETE FROM PG_LEGAL_ENTITY;
DELETE FROM PG_MERCHANT;
DELETE FROM PG_MERCHANT_BANK;
DELETE FROM PG_MERCHANT_CATEGORY_CODES;
DELETE FROM PG_MERCHANT_CONFIG;
DELETE FROM PG_MERCHANT_USER_ADDRESS;
DELETE FROM PG_MERCHANT_USERS;
DELETE FROM PG_OAUTH_ACCESS_TOKEN;
DELETE FROM PG_OAUTH_REFRESH_TOKEN;
DELETE FROM PG_ONLINE_TXN_LOG;
DELETE FROM PG_OTHER_FEE_VALUE;
DELETE FROM PG_PAYMENT_SCHEME;
DELETE FROM PG_ROLE_FEATURE_MAPPING_NEW WHERE USER_ROLE_ID NOT IN (1,2);
DELETE FROM PG_SWITCH WHERE SWITCH_NAME NOT IN ('Chatak Prepaid');
DELETE FROM PG_SWITCH_TRANSACTION;
DELETE FROM PG_TERMINAL;
DELETE FROM PG_TRANSACTION;
DELETE FROM PG_USER_ROLES WHERE ROLE_ID NOT IN (1,2);

SET FOREIGN_KEY_CHECKS=1;
COMMIT;