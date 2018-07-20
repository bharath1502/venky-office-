package com.chatak.pg.constants;

/**
 * @author Kumar
 */
public interface PGConstants {

  // Field Lengths
  Integer LENGTH_PROFILE_ID = 8;

  Integer LENGTH_MERCHANT_ID = 15;

  Integer LENGTH_STORE_ID = 8;

  Integer LENGTH_TERMINAL_ID = 8;

  // Database Status field
  Integer STATUS_SUCCESS = 0;

  Integer STATUS_PENDING = 1;

  Integer STATUS_INACTIVE = 2;
 
  Integer STATUS_ACTIVE = 0;
  
  Integer STATUS_DELETED = 3;

  Integer STATUS_HOLD = 4;

  Integer STATUS_SELF_REGISTERATION_PENDING = 5;
  
  Integer STATUS_CANCELLED = 6;
  
  String S_STATUS_ACTIVE = "Active";
  
  String S_STATUS_PENDING = "Pending";
  
  String S_STATUS_DELETED = "Deleted";
  
  String S_STATUS_INACTIVE = "In-Active";
  
  String S_STATUS_TERMINATED = "Terminated";
  
  String S_STATUS_SELFREGISTERED = "Self-Registered";
  
  String S_STATUS_CANCELLED = "Cancelled";
  
  // Error Messages of Merchant application
  String DB_FIELD_USER_NAME = "USER_NAME";

  String USER_NAME_DUPLICATE_MESSAGE = "User name already exists, choose diff user name";

  String REQ_PARAM_ACCESS_TOKEN = "access_token";

  String DB_FIELD_USER_EMAIL = "EMAIL";

  String EMAIL_DUPLICATE_MESSAGE = "Email already exists, choose diff email";

  String INVALID_CREDENTIAL = "Invalid Credential";

  String AUTHENTICATION_FAILED = "Authentication failed";

  String MERCHANT_LIST_FETCH_ERROR = "Fetch merchant list error, Try again";

  String MERCHANT_CREATION_ERROR = "Merchant creation failed, Try again";

  String MERCHANT_DETAIL_UPDATE_ERROR = "Merchant details update failed, Try again";

  String MERCHANT_DETAIL_DELETE_ERROR = "Merchant details deletion failed, Try again";

  String MERCHANT_BANK_CREATION_ERROR = "Merchant bank record creation failed, Try again";

  String MERCHANT_BANK_RECORD_ERROR = "Merchant bank record not found, Try adding bank record";

  String STORE_LIST_FETCH_ERROR = "Fetch Store list error, Try again";

  String STORE_CREATION_ERROR = "Store creation failed, Try again";

  String STORE_DETAIL_UPDATE_ERROR = "Store details update failed, Try again";

  String STORE_DETAIL_DELETE_ERROR = "Store details deletion failed, Try again";

  String TERMINAL_LIST_FETCH_ERROR = "Fetch Terminal list error, Try again";

  String TERMINAL_CREATION_ERROR = "Terminal creation failed, Try again";

  String TERMINAL_DETAIL_UPDATE_ERROR = "Terminal details update failed, Try again";

  String TERMINAL_DETAIL_DELETE_ERROR = "Terminal details deletion failed, Try again";

  String INVALID_MERCHANT_ID = "Merchant record not found";

  String INVALID_STORE_ID = "Store record not found";

  String INVALID_TERMINAL_ID = "Terminal record not found";

  String INVALID_MERCHANT_STORE_ID = "Merchant or Store record not found";

  String NO_RECORDS_FOUND = "No records found";

  String INVALID_USER_FOUND = "User record not found";

  String USER_DETAIL_UPDATE_ERROR = "User details update failed, Try again";

  String USER_DETAIL_DELETE_ERROR = "User details deletion failed, Try again";

  String DUPLICATE_MESSAGE_MERCHANT = "Merchant record exists with merchant_code, please try again with different merchant code";

  String DUPLICATE_MESSAGE_STORE = "Store record exists with store_id, please try again with different store id";

  String DUPLICATE_MESSAGE_TERMINAL = "Terminal record exists with terminal_code, please try again with different terminal code";

  String DUPLICATE_MERCHANT_EMAIL = "Duplicte Merchant Email";

  String ACTION_UPDATE = "update";

  String ACTION_DELETE = "delete";

  String ACTION_BLOCK = "block";

  String ACTION_UNBLOCK = "unblock";

  String ACTION_ACTIVE = "active";

  String ACTION_INACTIVE = "inactive";

  // Transaction Types
  String TXN_TYPE_SALE = "sale";

  String TXN_TYPE_AUTH = "auth";

  String TXN_TYPE_SALE_ADJ = "sale-adj";

  String TXN_TYPE_VOID = "void";

  String TXN_TYPE_REVERSAL = "reversal";

  String TXN_TYPE_REFUND = "refund";
  
  String TXN_TYPE_EXECUTED = "executed";

  String TXN_TYPE_BALANCE_ENQ = "balanceEnquiry";

  String TXN_TYPE_CASH_WITHDRAWAL = "cash-withdrawl";

  String TXN_TYPE_CASH_BACK = "cash-back";

  String PAYMENT_METHOD_DEBIT = "debit";

  String PAYMENT_METHOD_CREDIT = "credit";

  String AUTH_PAYMENT_METHOD = "auth_payment";

  String CAPTURE_PAYMENT_METHOD = " capture_payment";

  // Status
  Integer STATUS_INPROCESS = 1;

  Integer STATUS_FAILED = 2;

  Integer STATUS_DECLINED = 4;

  String SUCCESS = "00";

  String FALIURE = "00";

  String FORMAT_ERROR = "30";

  // Field Lengths
  Integer LENGTH_TXN_REF_NUM = 12;

  Integer LENGTH_AUTH_ID = 6;

  String VOID_TXN_AMOUNT = "00";

  // POS entry mode values for Chip Transactions

  String POS_ENTRY_MODE_CHIP_TXN_05 = "05";

  String POS_ENTRY_MODE_CHIP_TXN_95 = "95";
  
  String POS_ENTRY_MODE_CONTACT_LESS_TXN_91 = "91";

  // Service code of Track-2 for Chip Fallback Transactions
  String SERVICE_CODE_2 = "2";

  String SERVICE_CODE_6 = "6";

  String POS_ENTRY_MODE_CHIP_FALLBACK_TXN_80 = "80";

  int LENGTH_MER_ACC_NUM = 12;

  String MERCHANT = "Merchant";

  Long ZERO = (long) 0;

  String ACC_DESC = "Merchant Acc Creation";

  String USD = "USD";

  String PG_SETTLEMENT_PENDING = "Pending";

  String PG_SETTLEMENT_PROCESSING = "Processing";

  String PG_SETTLEMENT_EXECUTED = "Executed";
  
  String PG_SETTLEMENT_REJECTED = "Rejected";

  String PG_TXN_VOIDED = "Cancelled";

  String PG_TXN_REFUNDED = "Refunded";

  String PG_TXN_FAILED = "Failed";

  String PG_TXN_DECLILNED = "Declined";
  
  String PG_TXN_BATCH = "Batch";
  
  String SUB_MERCHANT = "SubMerchant";

  String SUB_MERCHANT_CREATE_BLOCK = "Cannot allow merchant creation for Sub merchant";

  String FUND_TRANSFER_EFT = "FT_BANK";

  String FUND_TRANSFER_CHECK = "FT_CHECK";
  
  String LITLE_EXECUTED = "LITLE_EXECUTED";
  
  String LITLE_PENDING = "LITLE_PENDING";
  
  String LITLE_EFT_EXECUTED = "LITLE_EFT_EXECUTED";
  
  String LITLE_REFUNDED = "LITLE_REFUNDED";
  
  String CHECKING_ACCOUNT = "Checking";
  
  String SAVINGS_ACCOUNT = "Savings";
  
  String PARTNER_AGENT_LIST_SESSION = "PA_LIST";
  
  String DOLLAR_SYMBOL = "$";
  
  String BULK_SETTLEMENT_LIST = "selected_bulk_settlement_list";
  
  String BULK_SETTLEMENT_LIST_OBJ = "selected_bulk_settlement_list_obj";
  
  String PRIMARY_ACCOUNT = "primary";
  
  String SECONDARY_ACCOUNT = "secondary";
  
  String PARTIAL="PARTIAL";
  
  Integer PARTIAL_REFUND_FLAG=1;
  
  Integer ONE = 1;
  
  Long ONE_LONG = 1L;
  
  String  DUPLICATE_RESELLER_EMAIL_ID = "Duplicate Reseller EmailId";
  
  String  DUPLICATE_COMMISSION_NAME = "Duplicate Comission Name";
  
  String  DUPLICATE_CAPUBLICKEY_NAME = "Duplicate CAPublicKey Name";
 
  
  Integer LENGTH_RESELLER_ACC_NUMBER = 12;
  
  String RESELLER_DETAIL_DELETE_ERROR = "Reseller details deletion failed, Try again";
  
  String RESELLER_DETAIL_UPDATE_ERROR = "Reseller Update failed, Try again";
  
  String DUPLICATE_BLACK_LISTED_CARD = "BlackListed Card Already exist";
   
  String DUPLICATE_PAYMENT_SCHEME_EMAIL_ID = "Duplicate Payment Scheme Email-Id";
  
  String NEW_USER = "NEW-USER";
  
  String USER_LOCKED_ERROR_MSG_FOR_REASON = "Locked, Entered Wrong password more than 3 times";
  
  String DUPLICATE_EXCHANGE_RATE = "Exchange rate is already defined for the specified source and destination currencies.";
  
  String SAME_EXCHANGE_CURRENCIES = "Source and destination currencies are same. Please choose different source and destination currency.";
  
  String DUPLICATE_RECURRING_CUSTOMER_EMAIL_ID = "Email Already in use ";
  
  String DEFAULT_LOCALE = "locale";

  String DEFAULT_REVENUE_ACCOUNT = "Chatak";
  
  String REVENUE_ACCOUNT ="Chatak Revenue Account";
  
  String DEFAULT_ENTITY_TYPE ="Chatak";
  
  Integer STATUS_SUSPENDED = 2;
  
  String S_STATUS_SUSPENDED = "Suspended";
  
  String ADMIN = "Admin";
  
  String DD_MM_YYYY = "dd/MM/yyyy";
  
  String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
  
  String PROGRAM_MANAGER_NAME = "Program Manager";
  
  String UPDATE = "Update";
  
  String BATCH_STATUS_ASSIGNED = "ASSIGNED";
  
  String BATCH_STATUS_PROCESSING = "PROCESSING";
  
  String BATCH_STATUS_COMPLETED = "COMPLETED";
  
   String REGEX_TIME = "(00|[0-9]|1[0-9]|2[0-3])-([0-9]|[0-5][0-9])-([0-9]|[0-5][0-9])$";
   
   String REGEX_DATE =  "\\d{2}-\\d{2}-\\d{4}";
   
   public static final Integer INDEX_ONE = 1;
   
   public static final Integer INDEX_TWO = 2;
   
   public static final Integer INDEX_THREE = 3;

   public static final Integer INDEX_FOUR = 4;
   
   public static final Integer INDEX_FIVE = 5;
   
   public static final Integer INDEX_SIX = 6;
   
   public static final Integer INDEX_SEVEN = 7;
   
   public static final Integer INDEX_EIGHT = 8;
   
   public static final Integer INDEX_NINE = 9;
   
   public static final Integer INDEX_TEN = 10;
   
   public static final Integer INDEX_ELEVEN = 11;
   
   public static final Integer INDEX_TWELVE = 12;
   
   public static final String REGULAR_EXPRESSION_ALPHANUMERIC_AND_SPACE = "^[a-zA-Z0-9][a-zA-Z0-9,\\s#.\\-]*";
   
   public static final Integer INDEX_THIRTEEN = 13;
   
   public static final Integer INDEX_FOURTEEN = 14;
   
   public static final Integer INDEX_FIFTEEN = 15;
   
   public static final Integer INDEX_SIXTEEN =16;
   
   String DATE_TIME_ZONE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss";
}
