/**
 * 
 */
package com.chatak.merchant.constants;

/**
 * << Add Comments Here >>
 * 
 * @author Girmiti Software
 * @date 03-Jan-2015 10:45:01 AM
 * @version 1.0
 */
public interface URLMappingConstants {

	public static final String CHATAK_MERCHANT_LOGIN = "login";

	public static final String CHATAK_MERCHANT_AUTHENTICATE = "authenticate";

	public static final String CHATAK_MERCHANT_LOG_OUT = "logout";

	public static final String INVALID_REQUEST_PAGE = "badRequestError";

	public static final String CHATAK_MERCHANT_SIGNUP = "merchantSignUp";

	public static final String SHOW_CHATAK_MERCHANT_SIGNUP = "merchant-signup";

	public static final String CHATAK_ADMIN_USER_NAME = "chatakAcqAdmin";

	public static final String CHATAK_ADMIN_HOME = "home";

	public static final String CHATAK_ADMIN_CREATE_MERCHANT_PAGE = "merchant-create";

	public static final String CHATAK_ADMIN_SEARCH_MERCHANT_PAGE = "merchant-search";

	public static final String CHATAK_MERCHANT_ERROR_PAGE = "error";

	public static final String CHATAK_MERCHANT_DASH_BOARD = "dash-board";

	public static final String CHATAK_MERCHANT_ACCOUNT_HISTORY = "merchant-account-history";

	/********************** Recurring *****************************/

	public static final String CHATAK_MERCHANT_RUCURRING_SEARCH_PAGE = "recurring-search";

	public static final String CHATAK_MERCHANT_RECURRING_PAGINATION = "getRecurrings";

	public static final String CHATAK_MERCHANT_RECURRING_DOWNLOAD = "get-recurring-report";

	public static final String CHATAK_MERCHANT_RECURRING_CREATE = "recurring-create";

	public static final String CHATAK_MERCHANT_RECURRING_SEARCH = "recurringSearch";

	public static final String CHATAK_MERCHANT_CREATE_CUSTOMER_CREATE_INFO = "createRecurringCustomer";

	public static final String CHATAK_MERCHANT_CREATE_PAYMENT_CREATE_INFO = "recurring-payment-create";

	public static final String CHATAK_MERCHANT_PAYMENT_INFO_CREATE = "createRecurringPaymentInfo";

	public static final String CHATAK_MERCHANT_CONTRACT_INFO_CREATE = "recurring-contract-create";

	public static final String CHATAK_MERCHAT_CREATE_CONTRACT_INFO = "createRecurringContractInfo";

	public static final String CHATAK_MERCHANT_SHOW_RECURRING_CUSTOMER_EDIT_PAGE = "editRecurring";

	public static final String CHATAK_MERCHANT_RECURRING_CUSTOMER_EDIT_PAGE = "recurring-customer-update";

	public static final String CHATAK_MERCHANT_RECURRING_CUSTOMER_UPDATE = "updateRecurringCustomer";

	public static final String CHATAK_MERCHANT_RECURRING_PAYMENT_ADD_NEW = "addOrUpdateRecurringPayment";

	public static final String CHATAK_MERCHANT_RECURRING_ADD_NEW_PAYMENT = "addNewPaymentInfo";

	public static final String CHATAK_MERCHANT_SHOW_ADD_NEW_RECURRING_PAYMENT_PAGE = "recurring-payment-add-new";

	public static final String CHATAK_MERCHANT_SHOW_RECURRING_CONTRACT_ADD_NEW_PAGE = "showContractAddPage";

	public static final String CHATAK_MERCHANT_ADD_NEW_RECURRING_CONTRACT_PAGE = "recurring-contract-add-new";

	public static final String CHTAK_MERCHANR_RECURRING_CONTRACT_ADD = "showContractAddOrUpdatePage";

	public static final String CHATAK_MERCHANT_SHOW_RECURRING_PAYMENT_INFO_EDIT_PAGE = "showRecurringPaymentInfoEditPage";

	public static final String CHATAK_MERCHANT_RECURRING_PAYMENT_INFO_EDIT_PAGE = "recurring-payment-info-edit";

	public static final String CHATAK_MERCHANT_RECURRING_PAYMENT_UPDATE = "updateRecurringPayment";

	public static final String CHATAK_MERCHANT_RECURRING_CONTRACT_UPDATE_PAGE = "showContractUpdatePage";

	public static final String CHTATK_MERCHANT_RECURRING_CONTRACT_EDIT_PAGE = "recurring-contract-info-edit";

	public static final String CHATAK_MERCHANT_RECURRING_CONTRACT_UPDATE = "updateRecurringContractInfo";

	public static final String CHATAK_MERCHAENT_DELETE_RECURRING_CUSTOMER = "deleteRecurringCustomer";

	public static final String CHATAK_MERCHANT_DELETE_RECURRING_PAYMENT = "deleteRecurringPayment";

	public static final String CHATAK_MERCHANT_DELETE_RECURRING_CONTRACT = "deleteRecurringContract";

	public static final String CHATAK_MERCHANT_RECURRING_CREATE1 = "recurring-create1";
	
	public static final String CHATAK_MERCHANT_UNIQUE_RECURRING_EMAIL_ID = "uniqueRecurringEmailId";

	public static final String CHATAK_MERCHANT_UNIQUE_RECURRING_UPDATE_EMAIL_ID = "uniqueRecurringUpdateEmailId";
	
	/********************** Basic Fraud *****************************/

	public static final String CHATAK_MERCHANT_FRAUD_BASIC_PAGE = "fraud-basic";

	public static final String CHATAK_MERCHANT_FRAUD_BASIC_CREATE = "create-fraud";

	/********************** Advanced Fraud *****************************/

	public static final String CHATAK_MERCHANT_FRAUD_ADVANCED_PAGE = "fraud-advanced";

	public static final String CHATAK_MERCHANT_FRAUD_ADVANCED_CREATE = "create-advanced";

	public static final String CHATAK_MERCHANT_ADD_NEW_ADVANCED_FRAUD = "addNewAdvancedFraud";

	public static final String CHATAK_MERCHANT_SHOW_ADVANCED_FRAUD_EDIT_PAGE = "advancedFraudEditPage";

	public static final String CHATAK_MERCHANT_ADVANCED_FRAUD_EDIT_PAGE = "fraud-advanced-edit";

	public static final String CHATAK_MERCHANT_ADVANCED_FRAUD_UPDATE = "updateAdvancedFraud";

	public static final String CHATAK_MERCHANT_DELETE_ADVANCED_FRAUD = "deleteAdvancedFraud";

	/********************** VirtualTerminal *****************************/

	public static final String CHATAK_MERCHANT_VIRTUAL_TERMINAL_SALE_PAGE = "virtual-terminal-sale";

	public static final String CHATAK_MERCHANT_VIRTUAL_TERMINAL_SALE_PROCESS = "do-virtual-terminal-sale";

	public static final String CHATAK_MERCHANT_VIRTUAL_TERMINAL_PRE_AUTH_PAGE = "virtual-pre-auth";

	public static final String CHATAK_MERCHANT_VIRTUAL_TERMINAL_PRE_AUTH_PROCESS = "do-virtual-terminal-preAuth";

	public static final String CHATAK_MERCHANT_VIRTUAL_TERMINAL_ADJUST_PAGE = "virtual-terminal-adjust";

	public static final String CHATAK_MERCHANT_VIRTUAL_TERMINAL_ADJUST_PROCESS = "do-virtual-terminal-adjust";

	public static final String CHATAK_MERCHANT_VIRTUAL_TERMINAL_FETCH_TRAN_ADJUST = "getTransactionForAdjust";

	public static final String CHATAK_MERCHANT_VIRTUAL_TERMINAL_FETCH_TRAN = "getTransaction";

	public static final String CHATAK_MERCHANT_VIRTUAL_TERMINAL_VOID_PAGE = "virtual-terminal-void";

	public static final String CHATAK_MERCHANT_VIRTUAL_TERMINAL_VOID_PROCESS = "do-virtual-terminal-void";

	public static final String CHATAK_MERCHANT_VIRTUAL_TERMINAL_REFUND_PAGE = "virtual-terminal-refund";

	public static final String CHATAK_MERCHANT_VIRTUAL_TERMINAL_REFUND_PROCESS = "do-virtual-terminal-refund";

	public static final String CHATAK_MERCHANT_VIRTUAL_TERMINAL_PREAUTH_COMPLEATION_PAGE = "virtual-terminal-pre-auth-completion";

	public static final String CHATAK_MERCHANT_VIRTUAL_TERMINAL_PREAUTH_COMPLEATION_PROCESS = "do-virtual-terminal-pre-auth-completion";

	public static final String CHATAK_MERCHANT_SEARCH_TRANSACTION_PAGE = "transactions-search";

	public static final String CHATAK_MERCHANT_SEARCH_TRANSACTION = "searchTransaction";

	public static final String CHATAK_MERCHANT_TRANSACTION_EXPORT = "get-transaction-report";

	public static final String CHATAK_MERCHANT_TRANSACTION_PAGINATION = "getTransactions";

	public static final String CHATAK_MERCHANT_HISTORY_PAGINATION = "getHistory";

	/********************** Password management *****************************/

	public static final String SHOW_MERCHANT_FORGOT_PSWD = "merchant-forgot-password";

	public static final String SHOW_MERCHANT_RESET_PSWD = "merchant-password-reset";

	public static final String SHOW_MERCHANT_CHANGE_PSWD = "merchant-change-password";

	public static final String PROCESS_MERCHANT_FORGOT_PSWD = "merchant-forgot-password";

	public static final String PROCESS_MERCHANT_RESET_PSWD = "merchant-password-reset";

	public static final String PROCESS_MERCHANT_CHANGE_PSWD = "merchant-change-password";

	/********************** Profile edit *****************************/

	public static final String CHATAK_MERCHANT_EDIT_PROFILE = "chatak_merchant_myprofile";

	public static final String CHATAK_MERCHANT_EDIT_PROFILE_PROCESS = "chatak_merchant_myprofile_edit";

	public static final String INVALID_TOKEN = "invalid-token";

	public static final String CHATAK_MERCHANT_ACCOUNT_HISTORY_EXPORT = "get-history-report";

	public static final String CHATAK_SHOW_SUB_MERCHANT_CREATION_PAGE = "sub-merchant-create";

	public static final String CHATAK_PROCESS_SUMBERCHANT_CREATION = "createSubMerchant";

	public static final String CHATAK_SHOW_SUB_MERCHANT_SEARCH_PAGE = "search-sub-merchant";

	public static final String GET_STATES = "getStatesById";

	public static final String CHATAK_SHOW_SUB_MERCHANT_UPDATE_PAGE = "editSubMerchant";
  
    public static final String NEW_USER_PSWD_MANAGEMENT = "new-user-password-change";

	public static final String CHATAK_PROCESS_SUB_MERCHANT_UPDATE_PAGE = "sub-merchant-update";

	public static final String CHATAK_SUB_MERCHANTS_PAGINATION = "getMerchants";

	public static final String CHATAK_SUB_MERCHANT_REPORT = "get-merchant-report";

	public static final String CHATAK_DELETE_SUB_MERCHANT = "deleteMerchant";

	public static final String UNIQUE_EMAIL = "uniqueEmailId";

	public static final String UNIQUE_USER = "uniqueUser";

	public static final String CHATAK_SHOW_SUB_MERCHANT_EDIT_PAGE = "editMerchant";

	public static final String CHATAK_SHOW_EFT_FUND_TRANSFER_PAGE = "fund-transfer-eft";

	public static final String CHATAK_SHOW_CHECK_FUND_TRANSFER_PAGE = "fund-transfer-check";

	public static final String CHATAK_PROCESS_EFT_FUND_TRANSFER = "eft-process-fund-transfer";

	public static final String CHATAK_PROCESS_CHECK_FUND_TRANSFER = "check-process-fund-transfer";

	public static final String CHATAK_FUND_TRANSFER_RESPONSE = "fund-transfer-response";

	public static final String UNIQUE_EMAIL_EDIT = "uniqueEmailIdEdit";

	public static final String UNIQUE_USER_EDIT = "uniqueUserEdit";

	public static final String SUBMERCHANT_ERROR_PAGE = "submerchant-error-page";

	public static final String CHATAK_MERCHANT_SIGNUP_PROCESS = "processMerchantSignUP";

	public static final String CHATAK_VIEW_SUB_MERCHANT_UPDATE_PAGE = "viewSubMerchant";

	public static final String CHATAK_VIEW_SUB_MERCHANT_PAGE = "sub-merchant-view";

	public static final String CHATAK_ADMIN_SPECIFIC_USER_EFT_TRANSFERS = "specific-user-eftTransfers-show";

	public static final String SHOW_CHATAK_ADMIN_SPECIFIC_USER_EFT_TRANSFERS = "specific-eft-transfers-report";

	public static final String PROCESS_CHATAK_ADMIN_SPECIFIC_USER_EFT_TRANSFERS = "processSpecificEFTTransfers";

	public static final String DOWNLOAD_SPECIFIC_EFT_TRANSFERS_REPORT = "downloadSpecificEFTTransferReport";

	public static final String DOWNLOAD_REVENUE_GENERATED_REPORT = "downloadRevenueGeneratedList";

	public static final String GLOBAL_REVENUE_GENERATED_REPORTS_DATES = "showGlobalSysRevenueGeneratedReports";

	public static final String SHOW_GLOBAL_REVENUE_GENERATED_REPORT = "global-revenue-generated-report";

	public static final String GLOBAL_REVENUE_GENERATED_REPORT = "globalRevenueGeneratedReports";

	public static final String CHATAK_EFT_FETCH_TRAN_ID = "doAjaxFetchTxnIdList";

	public static final String CHATAK_MERCHANT_TRANSACTION_VOID_PAGE = "transaction-void";

	public static final String CHATAK_MERCHANT_TRANSACTION_VOID_PROCESS = "do-transaction-void";

	public static final String PROCESS_CHATAK_MERCHANT_TRANSACTION_VOID = "processTransactionVoid";

	public static final String CHATAK_MERCHANT_TRANSACTION_REFUND_PAGE = "do-transaction-refund";

	public static final String SHOW_CHATAK_MERCHANT_TRANSACTION_REFUND = "transaction-refund";

	public static final String PROCESS_CHATAK_MERCHANT_TRANSACTION_REFUND = "process-transaction-refund";

	public static final String VALIDATE_MERCHANT_CODE = "validateMerchantCode";

	public static final String CHATAK_PARTNER_AND_AGENT_DETAILS = "get-partner-agent-details";

	public static final String CHATAK_MERCHANT_BULK_SETTLEMRNT_ACTION = "process-bulk-settlement-action";

	public static final String CHATAK_MERCHANT_SETTLEMRNT_ACTION = "process-settlement-action";

	public static final String CHATAK_FETCH_ACCOUNT_DETAILS = "chatak-fetch-account-details";

	public static final String CHATAK_PROCESS_ACCOUNT_TRANSFER = "show-account-transfer";

	public static final String CHATAK_PROCESS_POPUP_ACTION = "process-popup-action";

	public static final String CHATAK_MERCHANT_PROCESSING_TRANSACTIONS = "processing-transaction-details";

	public static final String CHATAK_MERCHANT_PROCESSING_TRANSACTIONS_PAGINATION = "processing-transaction-details-pagination";

	public static final String CHATAK_MERCHANT_EXECUTED_TRANSACTIONS = "executed-transaction-details";

	public static final String CHATAK_MERCHANT_EXECUTED_TRANSACTIONS_PAGINATION = "executed-transaction-details-pagination";

	public static final String CHATAK_MERCHANT_PROCESSING_TRANSACTIONS_REPORT = "processing-transaction-details-report";

	public static final String CHATAK_MERCHANT_EXECUTED_TRANSACTIONS_REPORT = "executed-transaction-details-report";

	public static final String CHATAK_TRANSACTION_POPUP_DETAILS = "get-transaction-popup";

	/********************** Recurring Previous*****************************/

	public static final String CHATAK_MERCHANT_RECURRING_CUSTOMER_PREVIOUS = "recurring-customer-previous";

	public static final String CHATAK_MERCHANT_RECURRING_CUSTOMER_SAVE = "create-recurring-customer";

	public static final String CHATAK_MERCHANT_RECURRING_PAYMENT_PREVIOUS = "recurring-payment-previous";

	public static final String CHATAK_MERCHANT_RECURRING_PAYMENT_SAVE = "recurring-payment-contact";
	
	/********************** Pay page config *****************************/
	public static final String CHATAK_MERCHANT_PAY_PAGE_CONFIG = "chatak-pay-page-config";
	
	public static final String VIEW_TERMS_CONDITIONS = "recurring-terms-condition";
	
	public static final String CHATAK_PARTIAL_REFUND_BALANCE="chatak-partial-refund-balance";

	public static final String CHATAK_MERCHANT_VALIDATE_AGENT_DETAILS  = "validateAgentDetails";
	
	/************************ Manual Transaction ************************/
	
	public static final String CHATAK_MERCHANT_MANUAL_TRANSACTIONS_REPORT = "manual-transaction-details-report"; 
	
	public static final String CHATAK_MERCHANT_MANUAL_TRANSACTIONS_PAGINATION = "manual-transaction-details-pagination";
	
	public static final String CHATAK_MERCHANT_MANUAL_TRANSACTIONS = "manual-transaction-details";

	public static final String GET_AGENT_DATA_BY_ID  = "getAgentDataById";
	
	public static final String GET_AGENT_NAMES_BY_CURRENCY_ALPHA  = "getAgentNamesByCurrencyAlpha";
	
	public static final String CHATAK_ADMIN_SUB_MERCHANT_STATUS_CHANGE = "subMerchantActivationSuspention";
	
	public static final String CHATAK_INVALID_SESSION = "session-invalid";

	public static final String CHATAK_INVALID_ACCESS = "access-invalid";
	
	public static final String CHATAK_MERCHANT_SHOW_BATCH_REPORT = "merchant-batch-report";
	
	public static final String CHATAK_MERCHANT_GET_BATCH_REPORT = "getBatchReport";
	
	public static final String CHATA_MERCHANT_BATCH_TRANSACTION_EXPORT = "get-batch-transaction-report";
	
	public static final String CHATAK_MERCHNAT_BATCH_TRANSACTION_PAGINATION = "getBatchTransactions";
	
	public static final String CHATAK_MERCHANT_SHOW_DAILY_FUNDING_REPORT = "merchant-daily-funding-report";
	
	public static final String CHATAK_MERCHANT_GET_DAILY_FUNDING_REPORT = "getDailyFundingReport";
	
	public static final String CHATA_MERCHANT_FUNDING_REPORT_EXPORT = "get-daily-funding-report";
	
	public static final String CHATAK_MERCHANT_FUNDING_REPORT_PAGINATION = "get-daily-funding-Transactions";
	
	public static final String GET_PARTNER_NAME_BY_MERCHANT_CODE = "getPartnerNameByMerchantCode";
	
}