package com.chatak.pg.util;

public class Constants {

 public Constants() {
    super();
  }

  public static final Double HUNDRED = 100.00D;

  public static final String CONFIG_FILE_NAME = "iso93binary.xml";

  public static final String CHATAK_PG_LOGIN = "chatakUserAuthenticate";

  public static final String CHATAK_PG_HOME = "home";
  
  public static final Integer EMAIL_ID = 50;
  
  public static final Integer FAX = 17;
  
  public static final Integer EXTENSION = 5;
  
  public static final Integer PHONE = 17;
  
  public static final Integer CONTACT_PERSON_NAME = 50;
  
  public static final Integer BANK_ACCOUNT_NUMBER = 16;
  
  public static final Integer ACCOUNT_ROUTING_NUMBER = 9;
  
  public static final Integer BANK_CODE = 30;

  public static final String CHATAK_PG_LOGIN_PAGE = "login";

  public static final String CHATAK_PG_RELOAD = "reload";

  public static  boolean CHATAK_LICENSE_VALID = false;

  public static final Integer CHATAK_DOWNSTREAM_SOCKET_TIMEOUT = 20000;

  public static final String CHATAK_ACQ_ISO_PACKET_FIRST_4_BYTES_VALUE = "0000";

  public static final Integer DEFAULT_PAGE_SIZE = 20;

  public static final Integer MAX_PAGE_SIZE = 100;

  public static final String ACTIVE = "Active";

  public static final Integer PASS_RETRY_COUNT = 3;

  public static final Integer PASS_EXPIRATION_TIME_DAYS = 90;

  public static final String LOCKED = "Locked";

  public static final Integer ACTIVE_STATUS = 0;

  public static final String TMS_USER_TYPE = "Tms";

  public static final String ADMIN_USER_TYPE = "Admin";

  public static final String EMPTY = "EMPTY";

  public static final String EMAIL_TOKEN_FORGOT_PSWD = "FORGOT_PASSWORD";

  public static final String EMAIL_TOKEN_SET_NEW_PSWD = "SET_NEW_PASSWORD";

  public static final String APPROVED = "Approved";

  public static final String SUPERADMIN = "SUPER-ADMIN";

  public static final String DAILY = "D";

  public static final String WEEKLY = "W";

  public static final String MONTHLY = "M";

  public static final String SCHEDULER_EFT_PROCESS = "SCHEDULER_EFT_PROCESS";

  public static final String MANUAL_EFT_PROCESS = "MANUAL_EFT_PROCESS";

  public static final String USERS_GROUP_ADMIN = "ADMIN";

  public static final String USERS_GROUP_MERCHANT = "MERCHANT";

  public static final String TYPE_MERCHANT = "Merchant";

  public static final String TYPE_SUB_MERCHANT = "SubMerchant";

  public static final String ACCOUNT_TYPE_OTHER = "OTH";

  public static final String ACCOUNT_TYPE_SYSTEM = "SYS";

  public static final String ACC_ACTIVE = "Active";

  public static final String ACC_SUSPENDED = "Suspended";

  public static final String ACC_TERMINATED = "Terminated";

  public static final String PROCESSING_TXN_LIST = "processingTxnList";

  public static final String EXECUTED_TXN_LIST = "executedTxnList";

  public static final String MANUAL_TXN_LIST = "manualTransactionsReportList";

  public static final String TXN_CC = "TXN_CC";

  public static final String TXN_MANUAL_CREDIT = "TXN_MANUAL_CREDIT";

  public static final String TXN_MANUAL_DEBIT = "TXN_MANUAL_DEBIT";

  public static final String TXN_EFT = "TXN_EFT";

  public static final String TXN_ACCOUNT = "TXN_ACCOUNT";

  public static final String TXN_UNKNOWN = "TXN_UNKNOWN";

  public static final Integer DEFAULT_DASHBOARD_PAGE_SIZE = 10;

  public static final String SYSTEM_UPLOAD = "SU";

  public static final String EFT = "EFT";

  public static final String CHECK = "CHECK";

  public static final String NONAMEX = "NONAMEX";

  public static final String AMEX = "AMEX";

  public static final String REFUND = "REFUND";

  public static final String BATCH = "BATCH";

  public static final String RQUEST_Q_DATA_LIST = "requestQDataList";

  public static final String DOUBLE_AMOUNT_FORMAT = "###0.00";

  public static final String ACCOUNT_NAME_SYSTEM = "System Account";

  public static final String BATCH_STATUS_NA = "NA";

  public static final String BATCH_STATUS_NEXT = "NEXT";

  public static final String AUTO_SETTLEMENT_STATUS_YES = "YES";

  public static final String AUTO_SETTLEMENT_STATUS_NO = "NO";

  public static final String SYSTEM_ACCOUNT_NUMBER = "1000000001";

  public static final String SYSTEM_REVENUE_ACCOUNT = "SYSTEM REVENUE ACCOUNT";

  public static final String DATE_TIME_FORMAT = "MM/dd/yyyy HH:mm:ss";

  public static final String DATE_TIME_FORMAT_MARKER = "MM/dd/yyyy HH:mm:ss a";

  public static final String DELIMITER = "\\|";

  public static final String FLAG_TRUE = "true";

  public static final String FLAG_FALSE = "false";

  public static final String USERS_GROUP_SUBMERCHANT = "SUBMERCHANT";

  public static final String SUCESS = "sucess";

  public static final Integer INITIAL_ENTITIES_PORTAL_DISPLAY_SIZE = 10;

  public static final Integer INITIAL_PAGE_SIZE_FOR_MERCHANT_ACCOUNT_SEARCH = 100;

  public static final Integer INITIAL_PAGE_SIZE_FOR_CA_PUBLIC_KEY_SEARCH = 100;

  public static final String ADMIN_URL_SPERATOR = "/admin/";

  public static final String INVALID_SESSION = "session-invalid";

  public static final String INVALID_ACCESS = "access-invalid";

  public static final String EXISTING_FEATURE_DATA = "existingFeatureData";

  public static final String EXISTING_FEATURES = "existingFeatures";

  public static final String LOGIN_USER_ID = "loginUserId";

  public static final String TERMINALS_MODEL = "terminals";

  public static final String SEARCH_LIST = "searchList";

  public static final String PUBLIC_KEY_REQUEST = "publicKeyRequest";

  public static final String FEECODE_PROGRAM_REQUEST_LIST_EXPORTDATA = "feeCodeprogramRequestListForExport";

  public static final String COMMISSION_PROGRAM_REQUEST_LIST_EXPORTDATA = "commissionProgramRequestListForExport";

  public static final String STATUS_ACTIVE = "Active";

  /*-------------------- Feature Level -------------------------*/

  public static final Integer FEATURE_LEVEL_ZERO = 0;

  public static final Integer FEATURE_LEVEL_ONE = 1;

  public static final String FEATURE_DATA_RESPONSE = "featureDataResponse";

  public static final String SEARCH_ROLE_REQUEST = "searchRoleRequest";

  public static final String ROLE_LIST = "roleList";

  public static final String USER_SEARCH_REQUEST = "userSearchRequest";

  public static final String USER_LIST = "userList";

  public static final String USER_EDIT_DATA = "userEditData";

  public static final String USER_VIEW_DATA = "userViewData";

  public static final String COOKIE_MW_NAME = "ACQ_ID";

  public static final String SUB_MERCHANTS_MODEL = "subMerchants";

  public static final Integer MAX_TRANSACTION_ENTITY_DISPLAY_SIZE = 20;

  public static final String PARTNER_FEE_CODES_MODEL = "partnerFeeCodes";

  public static final String TRANSACTIONS_REPORT = "transactionsReport";

  public static final String ALL_PENDING_TRANSACTION = "allPendingTransactions";

  public static final String MERCHANT_ACCOUNT_SEARCH_MODEL = "merchant-account-search-model";

  public static final String CA_PUBLIC_KEY_SEARCH_MODEL = "ca-public-key-search-model";

  public static final String SWITCH_INFO = "searchSwitchRequestList";

  public static final String RESELLER_MODEL = "resellers";

  public static final String RESELLERS_MODEL = "resellerValues";

  public static final String BANK_MODEL = "bank";

  public static final String PAYMENT_SCHEME_INFO = "paymentSchemeSearchData";

  public static final String BLACK_LISTED_CARD_INFO = "blackListedCardInfo";

  public static final String SYSTEM_BALANCE_REPORT = "systemBalanceReport";

  public static final String MCC_MODEL = "mcc";

  public static final String COMMISION_MODEL = "commission-model";

  public static final String DYNAMIC_MDR_MODEL = "dynamicMDR";

  public static final String MARKUP_FEE_MODEL = "markupFee";

  public static final String DOWNLOAD_ALL_LABEL = "Download All";

  public static final String EXCHANGE_RATE_INFO = "exchangeRateData";

  public static final String REQUEST_STATUS_PROCESSING = "Processing";

  public static final String CURRENCY_MODEL = "currencyDTO";

  public static final String DELETED = "deleted";

  public static final String SEARCH_BIN_INFO = "searchBinInfo";

  // merchant application

  public static final String ERROR = "error";

  public static final String SUCCESS = "success";

  public static final String URL_SPERATOR = "/pg/";

  public static final String FEE_PROGRAM_NAMES = "feeprogramnames";

  public static final String CHATAK_MERCHANT_SESSION = "CHATAK_MERCHANT";

  public static final String COOKIE_CHATAK_NAME = "CHATAK_ACQ_ID";

  public static final String LOGIN_RESPONSE_DATA = "loginReponseData";

  public static final String LOING_USER_ID = "loginUserId";

  public static final String LOGIN_ROLE_ID = "loginRoleId";

  public static final String LOGIN_USER_TYPE = "loginUserType";

  public static final String ERROR_DATA = "loginError";

  public static final String SUCCESS_CODE = "00";

  public static final String ERROR_CODE = "01";

  public static final String ENTITY_LIST_SESSION_NAME = "ENTITY-LIST";

  public static final String ENTITY_PAGES_SESSION_NAME = "entityPages";

  public static final String MODEL_ATTRIBUTE_ENTITY_LIST_PAGE_NUMBER = "entityListPageNumber";

  public static final String MODEL_ATTRIBUTE_ENTITY_LIST_BEGIN_PAGE_NUM = "beginEntityPage";

  public static final String MODEL_ATTRIBUTE_ENTITY_LIST_END_PAGE_NUM = "endEntityPage";

  public static final Integer MAX_ENTITIES_PAGINATION_DISPLAY_SIZE = 10;

  public static final Integer MAX_ENTITY_DISPLAY_SIZE = 10;

  public static final String MODEL_ATTRIBUTE_PORTAL_LIST_PAGE_NUMBER = "portalListPageNumber";

  public static final String MODEL_ATTRIBUTE_PORTAL_LIST_BEGIN_PAGE_NUM = "beginPortalPage";

  public static final String MODEL_ATTRIBUTE_PORTAL_LIST_END_PAGE_NUM = "endPortalPage";

  public static final String PORTAL_PAGES_SESSION_NAME = "portalPages";

  public static final String MODEL_ATTRIBUTE_PORTAL_TOTAL_RECORDS_PAGE_NUM = "totalRecords";

  public static final Integer MAX_ENTITIES_PORTAL_DISPLAY_SIZE = 20;

  public static final Integer ZERO = 0;

  public static final Integer ONE = 1;

  public static final Integer TWO = 2;

  public static final Integer THREE = 3;

  public static final Integer FOUR = 4;

  public static final Integer FIVE = 5;

  public static final Integer SIX = 6;

  public static final Integer SEVEN = 7;

  public static final Integer EIGHT = 8;

  public static final Integer NINE = 9;

  public static final String USER_LIST_DATA = "userLIstData";

  public static final String TOP_PANEL = "topPanel";

  public static final String ROLE_PAGINATION_LIST = "rolePaginationList";

  public static final Integer FEATURE_LIST_INDEX_LIST = 4;

  public static final String ROLE_LEVEL_SESSION = "roleLevelSession";

  public static final String EXISTING_FEATURE_LIST = "existingFeatureList";

  public static final String TOKEN_ERROR = "tokenError";

  public static final String RESET_PASS_SESSION = "resetPasswordDataSession";

  public static final String NEW_USER_TOKEN = "newUserToken";

  public static final String ADMIN_USER_LIST_DATA = "adminUserListData";

  public static final String STATUS_CODE_SUCCESS = "0";

  public static final String ROLE_SIZE_SESSION = "rolesSize";

  public static final String ALL = "ALL";

  public static final String ACTIVE_PENDING = "ACTIVE_PENDING";

  /*-------------------- report constants -------------------------*/

  public static final String PDF_FILE_FORMAT = "PDF";

  public static final String XLS_FILE_FORMAT = "XLS";

  public static final String EXPORT_FILE_NAME_DATE_FORMAT = Properties.getProperty("chatak.reportfilename.date.format");
  
  public static final String EXPORT_FILE_DATE_FORMAT_MMDDYY = Properties.getProperty("chatak.reportfile.date.format.mmddyy");

  public static final String EXPORT_HEADER_DATE_FORMAT = Properties.getProperty("chatak.reportdate.format");

  /*-------------------- recurring constants -------------------------*/

  public static final String RECURRING_MODEL = "recurringList";

  public static final String PHONE_NUMBER_FORMAT = "999-999-9999";

  public static final String CARD_NUMBER_FORMAT = "9999-9999-9999-9999";

  /*-------------------- Transactions constants -------------------------*/
  public static final String TRANSACTIONS_MODEL = "transactions";

  public static final String HISTORY_MODEL = "historyModel";

  /*-------------------- Password management -------------------------*/

  public static final String RESET_USER_ID = "resetUserId";

  public static final String RESET_EMAIL = "resetEmail";

  public static final String USER_PROFILE_REQUEST = "userProfileRequest";

  public static final String SAPARETOR = "|";

  /*-------------------- Session management -------------------------*/

  public static final String CHATAK_INVALID_SESSION = "session-invalid";

  public static final String CHATAK_INVALID_ACCESS = "access-invalid";

  /*-----------------------AMOUNT-------------------------*/

  public static final String AMOUNT_FORMAT = "###0.00";

  public static final String CARD_NUM_MAXLEN = "19";

  public static final String MERCHANTS_MODEL = "merchants";
  
  public static final String LOGIN_USER_PARENT_ID = "parentMerchantId";

  public static final String ALL_TRANSACTIONS_MODEL = "allTransactions";

  public static final String CONSTANT_EFT = "EFT";

  public static final String REQUEST_STATUS = "Pending";

  public static final String EXECUTED_STATUS = "Executed";

  public static final String REFUNDED_STATUS = "Refunded";

  public static final String ACQUIRING_CHANNEL = "web";

  public static final String MANUAL = "Manual";

  public static final String SYSTEM = "System";

  public static final String TRANSACTIONS_REQ_OBJ_MODEL = "transactionReqObject";

  public static final String LOGIN_USER_MERCHANT_ID = "loginUserMerchantId";

  public static final String MERCHANT_BUSINESS_NAME = "merchantBusinessName";

  public static final Integer TIME_OUT = 60000;

  public static final Integer MAX_AGE = 20 * 60;

  public static final Integer ELEVEN = 11;

  public static final String REFERER = "referer";

  public static final String STATE_LIST = "stateList";

  public static final String CHATAK_GENERAL_ERROR = "chatak.general.error";

  public static final String COUNTRY_LIST = "countryList";

  public static final String PAGE_NUMBER = "pageNumber";

  public static final String TOTAL_RECORDS = "totalRecords";

  public static final String BIN_LIST = "binList";

  public static final String SWITCH_NAME = "switchName";

  public static final String BIN_DTO = "binDTO";

  public static final String BLACK_LISTED_CARD = "blackListedCard";

  public static final String CAPUBLIC_KEYSDTO = "caPublicKeysDTO";

  public static final String COMMISSION_DTO = "commissionDTO";

  public static final String PAGE_SIZE = "pageSize";

  public static final String COMMISSION_DTO_LIST = "commissionDTOList";

  public static final String CURRENCY_DTO = "currencyDTO";

  public static final String MERCHANT_NAMES_LIST = "merchantNamesList";

  public static final String DCC_MARKUP = "dccMarkup";

  public static final String MERCHANT_CODE = "merchantCode";

  public static final String USERDATA_DTO = "userDataDto";

  public static final String CHATAK_NORMAL_ERROR_MESSAGE = "chatak.normal.error.message";

  public static final String CURRENCY_LIST = "currencyList";

  public static final String EXCHANGE_RATE = "exchangeRate";

  public static final long DAY_TO_MILLISECONDS = 24 * 60 * 60 * 1000l;

  public static final String PROCESSOR_NAMES = "processorNames";

  public static final String MERCHANT = "merchant";
  
  public static final String AGENT_NAMES_LIST = "agentnamesList";

  public static final String BANK_STATE_LIST = "bankStateList";

  public static final String LEGAL_STATE_LIST = "legalStateList";

  public static final String STATUS = "status";

  public static final String PREVIOUS_CI_PARTNER_ID = "previousCiPartnerId";

  public static final String TCC_MULTIPLE_LIST = "tccMultipleList";

  public static final String UPDATE_MERCHANT_ID = "updateMerchantId";

  public static final String ADMIN_VALUE = "admin";

  public static final String RESELLER = "reseller";

  public static final String CA_PUBLIC_KEYS_DTO = "capublickeysDTO";

  public static final String MERCHANT_SUB_LIST = "merchantSubList";

  public static final String PAYMENT_SCHEME = "paymentScheme";

  public static final String TRANSACTIONS_LIST_REQUEST = "transactionsListRequest";

  public static final String PENDING_TRANSACTIONS_REPORT_LIST = "pendingTransactionsReportList";

  public static final String GET_TRANSACTIONS_LIST_REQUEST = "getTransactionsListRequest";

  public static final String MANUAL_TRANSACTIONS_REPORT_LIST = "manualTransactionsReportList";

  public static final String BALANCE_LIST = "balanceList";

  public static final String ALL_TRANSACTIONS_REPORT_LIST = "allTransactionsReportList";

  public static final String CHATAK_ADMIN_TRANSACTIONS_ERROR_MESSAGE = "chatak.admin.transactions.error.message";

  public static final String TRANSACTION_DIV = "transactionDiv";

  public static final String DOWNLOAD_TYPE = "downloadType";

  public static final String EXECUTED_TRANSACTIONS_REPORT_LIST = "executedTransactionsReportList";

  public static final String RESELLER_DATA = "resellerData";

  public static final String USER_ROLE_DTO = "userRoleDTO";

  public static final String FEATURE_LIST = "featureList";

  public static final String ROLE_EXISTING_FEATURES = "roleExistingFeatures";

  public static final String SAME_ROLE_FLAG = "SAME_ROLE_FLAG";

  public static final String USER_ROLES_DTO = "userRolesDTO";

  public static final String UNABLE_TO_PROCESS_REQUEST = "Unable to process your request. Please try again";

  public static final String PROCESSING_LIST_SIZE = "processingListSize";

  public static final String EXECUTED_LIST_SIZE = "executedListSize";

  public static final String MANUAL_TRANSACTIONS_LIST_SIZE = "manualTransactionsListSize";

  public static final String ACCOUNT_HISTORY_LIST = "accountHistoryList";

  public static final String ACCOUNT_NUMBER = "accountNumber";

  public static final String FRAUD_DTO = "fraudDTO";

  public static final String LOGIN_RESPONSE = "loginResponse";

  public static final String ADVANCED_FRAUD_LIST = "advancedFraudList";

  public static final String ADVANCED_FRAUD_DTO = "advancedFraudDTO";

  public static final String FUND_TRANSFER_DTO = "fundTransferDTO";

  public static final String ACCOUNTS = "accounts";

  public static final String LOGIN_DETAILS = "loginDetails";

  public static final String CHATAK_INVALIDINPUT_ERROR = "chatak.invalidInput.error";

  public static final String CHANGE_PASS_WORD_LABEL_CONFIRM_PSWD_ERROR = "change-password.label.confirmpassword.error";

  public static final String MERCHANT_PROFILE = "merchantProfile";

  public static final String VIRTUAL_TEMINAL_REFUND = "virtualTeminalRefund";

  public static final String REDIRECT_PG = "redirect:/pg/";

  public static final String REDIRECTION_ERROR = "redirectionError";

  public static final String ACCESS_DENIED = "Access Denied!!!";

  public static final String VIRTUAL_TEMINAL_VOID = "virtualTeminalVoid";

  public static final String REVENUE_GENERATED_REPORT_LIST = "revenueGeneratedReportList";

  public static final String REVENUE_TYPE = "revenueType";

  public static final String EFT_TRANSFER_REPORT_LIST = "eftTransferReportList";

  public static final String CURRENCY = "currency";
  
  public static final String START_DATE = "startDate";
  
  public static final String END_DATE = "endDate";
  
  public static final String RAPID_REVENUE = "rapidRevenue";
  
  public static final String SUB_MER_REVENUE = "subMerRevenue";
  
  public static final String MERCHANT_REVENUE = "merchantRevenue";

  public static final String BATCH_ID_DATE_FORMAT = "ddMMyy";

  public static final Integer TEN = 10;

  public static final Integer TWELVE = 12;

  public static final Integer THIRTEEN = 13;

  public static final Integer FIFTEEN = 15;

  public static final Integer SIXTEEN = 16;

  public static final Integer EIGHTEEN = 18;

  public static final Integer TWENTY = 20;

  public static final Integer THIRTY = 30;

  public static final Integer FIFTY = 50;

  public static final Integer SEVENTY = 70;

  public static final String MERCHANT_SIGN_UP_REQUEST = "merchantSignUpRequest";

  public static final String TRANSACTION = "transaction";

  public static final String SETTLEMENT_DTO = "settlementDto";

  public static final String ACCOUNT_DETAILS = "accountDetails";

  public static final String VIRTUAL_TEMINAL_SALE = "virtualTeminalSale";

  public static final String TXN_REF_NUM = "txnRefNum";

  public static final String REF_FLAG = "refFlag";

  public static final String VIRTUAL_TERMINAL_CONTROLLER_INVALID_RETRY = "virtualTerminalController.invalid.retry";

  public static final Integer SEVENTYTWO = 72;

  public static final Integer FORTYEIGHT = 48;

  public static final Integer FOURTEEN = 14;

  public static final String EMPTY_STRING = "";

  public static final Integer NINETYNINE = 99;

  public static final String CONTENT_TYPE_PDF = "application/pdf";

  public static final String VIRTUAL_TERMINAL_PRE_AUTH = "virtualTerminalPreAuth";

  public static final String CHATAK_MERCHANT_SESSION_INVALID_JSP = "/chatak-merchant//session-invalid.jsp";

  public static final String VIRTUAL_TEMINAL_PRE_AUTH_COMPLEATION = "virtualTeminalPreAuthCompleation";

  public static final String VIRTUAL_TEMINAL_ADJUST = "virtualTeminalAdjust";

  public static final String DUMMY_STREET = "Dummy street";

  public static final String TRANSACTION_PROCESS = "/transaction/process";

  public static final String EXCEPTION_OCCURED_WHILE_SEARCHING_CUSTOMER_INFO = "Exception occured while searching customer Info: ";

  public static final String EXCEPTION_OCCURED_WHILE_DELETING_CUSTOMER_INFO = "Exception occured while deleting Contract Info: ";

  public static final String ENTERING = "Entering:: ";

  public static final String EXITING = "Exiting:: ";

  public static final String MM_DD_YYYY = "MM/dd/yyyy";

  public static final String DD_MM_YYYY = "dd/MM/yyyy";

  public static final String ERROR_FUND_TRANSFER_SERVICE_IMPL_PROCESS_ACCOUNT_TRANSFER = "ERROR |FundTransferServiceImpl | processAccountTransfer";

  public static final String PREPAID_EMAIL_TEMPLATE_FILE_PATH = "prepaid.email.template.file.path";

  public static final String PREPAID_FROM_EMAIL_ID = "prepaid.from.email.id";

  public static final String TO = " to ";

  public static final String BATCH_REPORT = "batchReport";

  public static final String BATCH_REQUEST_OBJECT = "batchRequestObj";

  public static final String DEVICE_MANAGEMENT_ID = "deviceManagementId";

  public static final String DECLINED = "Declined";

  public static final String FAILED = "Failed";

  public static final String DAILY_FUNDING_REQUEST_OBJECT = "dailyFundingRequestObj";

  public static final String FUNDING_REPORT = "dailyFundingReport";

  public static final Integer FIVETHOUSAND = 5000;

  public static final Integer THIRTYNINE = 39;

  public static final Integer THIRTYSEVEN = 37;

  public static final Integer TWENTYTWO = 22;

  public static final Integer NUMBER = 1024;

  public static final Integer TWENTY_FOUR = 24;

  public static final Integer ONE_TWO_EIGHT = 128;

  public static final Integer ONE_NINE_TWO = 192;

  public static final Integer TWO_FIVE_SIX = 256;

  public static final Integer TWO_EIGHT_THREE = 283;

  public static final Integer ONE_THOUSAND = 1000;

  public static final Integer ONE_ZERO_TWO_FOUR = 1024;

  public static final Long ONE_THOUSAND_LONG = 1000L;

  public static final Float SIXTEEN_FLOAT = 16F;

  public static final Double SIX_FIVE_FIVE_THREE_FIVE_DOUBLE = 65535D;

  public static final Long TWO_LONG = 2L;

  public static final Long ONE_NINE_NINE_LONG = 199L;

  public static final Long FIVE_HUNDRED_LONG = 500L;

  public static final Long TRILLION_LONG = 1000000000000L;

  public static final Integer SEVENTEEN = 17;

  public static final Integer TWENTY_ONE = 21;

  public static final Double ONE_HUNDRED = 100.0;

  public static final String BALANCE_ENQUIRY = "BAL ENQ";

  public static final String APPLICATION_VND_MS_EXCEL = "application/vnd.ms-excel";

  public static final String CONTENT_DISPOSITION = "Content-Disposition";

  public static final String ATTACHMENT_FILENAME = "attachment;filename=";

  public static final String MERCHANT_FILE_EXPORT_UTIL_REPORT_DATE = "merchantFileExportUtil.report.date";

  public static final String MERCHANT_FILE_EXPORT_UTIL_STATUS = "merchantFileExportUtil.status";

  public static final String FFFFFF = "#FFFFFF";

  public static final String CHATAK_FOOTER_COPYRIGHT_MESSAGE = "chatak.footer.copyright.message";

  public static final String EXPIRES = "Expires";

  public static final String REVALIDATE = "must-revalidate, post-check=0, pre-check=0";

  public static final String CACHE_CONTROL = "Cache-Control";
  
  public static final Integer TWENTY_THREE = 23;

  public static final Integer SIXTY = 60;

  public static final Integer TWO_HUNDRED = 200;

  public static final Long THREE_LONG = 3l;

  public static final Integer THIRTY_THREE = 33;

  public static final String PRAGMA = "Pragma";

  public static final String PUBLIC = "public";

  public static final String REPORT_FILE_EXPORT_UTIL_DATE_TIME = "reportFileExportUtil.date.time";

  public static final String MERCHANT_FILE_EXPORT_UTIL_ACCOUNT_TRANSACTION_ID = "merchantFileExportUtil.Account.transactionid";

  public static final String REPORT_FILE_EXPORT_UTIL_TRANSACTION_DESCRIPTION = "reportFileExportUtil.transaction.description";

  public static final String MERCHANT_FILE_EXPORT_UTIL_CARDTYPE = "merchantFileExportUtil.cardtype";

  public static final String TRANSACTION_FILE_EXPORT_UTIL_DEBIT = "transactionFileExportUtil.debit";

  public static final String TRANSACTION_FILE_EXPORT_UTIL_CREDIT = "transactionFileExportUtil.credit";

  public static final String FILE_EXPORT_UTIL_AVAILABLE_BALANCE = "fileExportUtil.available.balance";

  public static final String DEBIT = "debit";

  public static final String REPORTS_LABEL_BALANCEREPORTS_MANUALTRANSACTIONS = "reports.label.balancereports.manualtransactions.merchantorsubmerchantcode";

  public static final String SEARCH_SUB_MERCHANT_LABEL_CURRENCYCODE = "search-sub-merchant.label.currencycode";

  public static final String CHATAK_HEADER_TRANSACTION_MESSAGES = "chatak.header.transaction.messages";

  public static final String CHATAK_HEADER_MANUAL_TRANSACTIONS_REPORTS = "chatak.header.manual.transactions.reports";
  
  public static final Integer ONE_EIGHTY = 180;
  
  public static final String CHATAK_ADMIN_REVENUE_GENERATED_HEADER_MESSAGE="chatak.admin.revenue.generated.header.message";
  
  public static final String CHATAK_SYSTEM_OVERVIEW_REPORTS_HEADER_MESSAGE="chatak.system.overview.reports.header.message";
  
  public static final int TWO_INT = 2;
  
  public static final int THREE_INT = 3;
  
  public static final int FOUR_INT = 4;

  public static final String PROGRAM_MANAGER_NAME_EXIST = "PROGRAM_MANAGER_ALREADY_EXISTS_WITH_NAME";

  public static final String PROGRAM_MANAGER_NAME_DOESNOTEXIST = "PROGRAM_MANAGER_DOES_NOT_EXISTS_WITH_NAME";

  public static final String DEFAULT_PM_ID = "defaultPMId";

  public static final String PROGRAM_MANAGER = "ProgramManager";

  public static final String PARTNER = "Partner";

  public static final String PROGRAM_MANAGER_REQUEST_LIST_EXPORTDATA = "programManagerRequestListForExport";

  public static final Integer SUCCESS_STATUS = 200;

  public static final String PROGRAM_MANAGER_ALREADY_EXISTS_WITH_NAME = "Program Manager already on-boarded";

  public static final String PROGRAM_MANAGER_ID_IS_EMPTY = "PROGRAM_MANAGER_ID_IS_EMPTY";

  public static final String GENERIC_MALFORMED_REQUEST = "31000";

  public static final String HTTP_INTERNAL_SERVER_ERROR = "CEC_0009";

  public static final String GENERIC_REQUEST_EXCEPTION = "31105";

  public static final String SYSTEM_PROGRAM_MANAGER = "SystemProgramManager";

  public static final String SYSTEM_BANK = "SystemBank";

  public static final String NORMAL_PARTNER = "Rapid_Partner";

  public static final String CEC_0001 = "CEC_0001";

  public static final String CEC_0002 = "CEC_0002";

  public static final String CEC_0009 = "CEC_0009";

  public static final String CEC_0013 = "CEC_0013";

  public static final String PARTNER_ALREADY_EXISTS_WITH_SAME_NAME = "CEC_1054";

  public static final String  PARTNER_ALREADY_EXISTS_WITH_SAME_CLIENT_ID = "CEC_1055";

  public static final String NO_RECORDS_FOUND = "CEC_1283";

  public static final String CEC_1272 = "CEC_1272";

  public static final String CEC_1542 = "CEC_1542";

  public static final String CEC_1543 = "CEC_1543";

  public static final String PARTNER_ID_IS_EMPTY = "PARTNER_ID_IS_EMPTY";

  public static final String PARTNER_ALREADY_EXISTS_WITH_SAME_NAME_MESSAGE = "Partner is already exist with the given name,please enter different partner name";

  public static final String SYSTEM_PARTNER = "SystemPartner";

  public static final String PARTNER_CREATION_ERROR = "PARTNER_CREATION_ERROR";

  public static final String PROGRAM_MANAGER_CREATION_ERROR = "PROGRAM_MANAGER_CREATION_ERROR";

  public static final String PROGRAM_MANAGER_SEARCH_ERROR = "PROGRAM_MANAGER_SEARCH_ERROR";

  public static final String PARTNER_REQUEST_LIST_EXPORTDATA = "partnerRequestListForExport";

  public static final Long PM_ACCOUNT_NUMBER1 = 100000000001l;

  public static final Long PM_ACCOUNT_NUMBER2 = 100000000002l;

  public static final Long PM_ACCOUNT_NUMBER3 = 100000000003l;
  
  public static final String LOGIN_USER = "loginUser";
  
  public static final String EXCEPTION = "exception";
  
  public static final String SESSION = "session";
  
  public static final String YYYY_MM_DD = "yyyyMMdd";
  
  public static final String EXCEPTIONS = "Exception :";
  
  public static final String MERCHANT_LIST = "merchantList";
  
  public static final String SUB_MERCHANT_LIST = "subMerchantList";
  
  public static final String TRANSACTION_HISTORY = "transactionHistory";
  
  public static final String DATA_ACCESS_EXCEPTION = "DataAccessException";
  
  public static final String PM_USER_TYPE = "Program Manager";
  
  public static final String ISO_USER_TYPE = "ISO";

  public static final String ISO_CREATE_ERROR = "CEC_1544";
  
  public static final String ISO_NAME_ALREADY_EXIST = "CEC_1545";
  
  public static final String CHATAK_ADMIN_EXCEPTION = "ChatakAdminException";
  
  public static final String  CARD_PROGRAM_ERROR = "Invalid Card Program";
  
  public static final String  BANK_PM_MAPPING_ERROR = "Invalid Bank";
  
  public static final String REVENUE_ACCOUNT = "Revenue Account";
  
  public static final String ONBOARDED = "onboarded";
  
  public static final String PM_ALREADY_ONBOARDED = "CEC_1546";

  public static final String CREATE_INDEPENDENT = "CreateIndependent";
  
  public static final String  ISO_REQUEST_EXPORT_DATA = "isoRequestExportData";
  
  public static final Integer INT_TWO = 2;
  
  public static final Integer INT_TEN = 2;
  
  public static final Integer TWENTY_FIVE = 25;
  
  public static final Integer INT_THIRTY = 30;
  
  public static final Integer INT_FIFTY = 50;
  
  public static final Integer INDEX_ONE = 1;
  
  public static final Integer INDEX_TEN = 10;
  
  public static final Integer INDEX_FIFTEEN = 15;
  
  public static final Integer INDEX_EIGHTEEN = 18;
  
  public static final Integer SEVEN_HUNDRED_SIXTY_FIVE = 765;
  
  public static final Integer CARD_NUMBER_MIN_LENGTH = 12;
  
  public static final String CARD_PROGRAM_ALREADY_MAPPED = "CEC_1547";
  
  public static final String FEE_REPORT_REQUEST_LIST_EXPORTDATA = "feeReportRequestListForExport";
  
  public static final String FEE_TRANSACTIONS_SEARCH_LIST = "feeTransactionList";
  
  public static final String FEE_REPORT_REQUEST = "feeReportRequest";

  public static final String APP_ADMIN_EMAIL = "prepaid@girmiti.com";

  public static final String APP_CLIENT_ADDRESS = "BLR";

  public static final String APP_CLIENT_CITY = "BLR";

  public static final String APP_CLIENT_COUNTRY = "INDIA";

  public static final String APP_CLIENT_ID = "WALLETUSER";//Need to change to mpos if required

  public static final String APP_CLIENT_ACCESS = "WALLETPASS";//Need to change to mpos if required

  public static final String APP_CLIENT_NAME = "WALLET";//Need to change to mpos if required

  public static final String APP_CLIENT_PHONE = "9876543210";

  public static final String APP_CLIENT_ROLE = "WALLET_ADMIN";//Need to change to mpos if required

  public static final String APP_CLIENT_ZIP = "560037";

  public static final String APP_DESCRIPTION = "PREPAID_PROJECT";

  public static final String APP_NAME = "PREPAID";

  public static final String CREATED_BY = "2";

  public static final String APP_STATUS = "Active";

  public static final String SCHEDULER_ENABLE_FLAG = Properties.getProperty("settlement.scheduler.enabled");
  
  public static final String SETTLEMENT_DATE_FORMAT = "MM-dd-yyyy";
  
  public static final String TIMEZONE_DATE_FORMAT = "dd-MM-yyyy HH:mm:ss z";
  
  public static final String SETTLEMENT_STATUS = "Settled";
  
  public static final String SALE = "sale";
  
  public static final String POS_PURCHASE = "POS Purchase";

  public static final String MERCHANT_NOT_ASSOCIATED = "CEC_1548";
  
  public static final String POS_REFUND = "POS Refund";

  public static final String JSESSIONID = "JSESSIONID";
  
  public static final String SETTELEMENT_AMOUNT_FORMAT ="#,##0.00";
  
  public static final String ACCOUNT_PAY_VALUE ="350";
  
  public static final String HYPHEN_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
  
  public static final String BANK_VIEW_NAME = "bankViewName";
  
  public static final String MASK_DATE_FORMAT = "99/99/9999";

}
