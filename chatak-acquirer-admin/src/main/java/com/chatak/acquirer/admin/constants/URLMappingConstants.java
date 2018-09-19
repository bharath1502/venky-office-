/**
 * 
 */
package com.chatak.acquirer.admin.constants;

/**
 * << Add Comments Here >>
 *
 * @author Girmiti Software
 * @date 03-Jan-2015 10:45:01 AM
 * @version 1.0
 */
public interface URLMappingConstants {

	String CHATAK_ADMIN_LOGIN = "login";

	String CHATAK_ADMIN_AUTHENTICATE = "authenticate";

	String CHATAK_ADMIN_LOG_OUT = "logout";

	String INVALID_REQUEST_PAGE = "badRequestError";

	String CHATAK_ADMIN_USER_NAME = "chatakAcqAdmin";

	String CHATAK_ADMIN_HOME = "home";

	String CHATAK_ADMIN_VIEW_MERCHANT_PAGE = "merchant-view";

	String CHATAK_ADMIN_CREATE_MERCHANT_PAGE = "merchant-create";

	String CHATAK_ADMIN_CREATE_MERCHANT = "createMerchant";

	String CHATAK_ADMIN_SEARCH_MERCHANT_PAGE = "merchant-search-page";

	String CHATAK_ADMIN_SEARCH_MERCHANT = "merchant-search";

	String CHATAK_ADMIN_SEARCH_TRANSACTION_PAGE = "transactions-search";

	String CHATAK_ADMIN_SEARCH_TRANSACTION = "searchTransaction";

	String CHATAK_ADMIN_CREATE_TERMINAL_PAGE = "terminal-create";

	String CHATAK_ADMIN_SEARCH_TERMINAL_PAGE = "terminal-search";

	String CHATAK_ADMIN_CREATE_TERMINAL = "createTerminal";

	String CHATAK_ADMIN_SEARCH_TERMINAL = "searchTerminal";

	String CHATAK_ADMIN_MERCHANTS_PAGINATION = "getMerchants";

	String CHATAK_ADMIN_SUB_MERCHANTS_PAGINATION = "getSubMerchants";

	String CHATAK_ADMIN_TERMINALS_PAGINATION = "getTerminals";

	String CHATAK_ADMIN_SHOW_EDIT_MERCHANT = "editMerchant";

	String CHATAK_ADMIN_DELETE_MERCHANT = "deleteMerchant";

	String CHATAK_ADMIN_EDIT_MERCHANT = "merchant-update";

	String CHATAK_ADMIN_UPDATE_MERCHANT = "updateMerchant";
	
	String CHATAK_ADMIN_MERCHANT_STATUS_CHANGE = "merchantActivationSuspention";
	
	String CHATAK_ADMIN_SUB_MERCHANT_STATUS_CHANGE = "subMerchantActivationSuspention";

	String CHATAK_ADMIN_EDIT_TERMINAL = "editTerminal";

	String CHATAK_ADMIN_EDIT_TERMINAL_PAGE = "terminal-edit";

	String CHATAK_ADMIN_UPDATE_TERMINAL = "updateTerminal";

	String CHATAK_ADMIN_TRANSACTION_PAGINATION = "getTransactions";

	String CHATAK_ADMIN_UNIQUE_USER_VALIDATE = "uniqueUser";

	String CHATA_ADMIN_TRANSACTION_EXPORT = "get-transaction-report";

	String CHATAK_ADMIN_UNIQUE_EMAIL_VALIDATE = "uniqueEmailId";

	String CHATAK_ADMIN_SETTLEMRNT_ACTION = "process-settlement-action";

	String CHATAK_ADMIN_CANCEL_SETTLEMENT_ACTION = "cancel-settlement-action";

	String CHATAK_ADMIN_UPDATE_SUB_MERCHANT = "updateSubMerchant";

	String CHATAK_ADMIN_SHOW_EDIT_SUB_MERCHANT = "editSubMerchant";

	String CHATAK_ADMIN_SHOW_VIEW_SUB_MERCHANT = "viewSubMerchant";

	String CHATAK_ADMIN_EDIT_SUB_MERCHANT = "sub-merchant-update";

	String CHATAK_ADMIN_VIEW_SUB_MERCHANT = "sub-merchant-view";

	String CHATAK_ADMIN_SEARCH_SUB_MERCHANT_PAGE = "sub-merchant-search-page";

	String CHATAK_ADMIN_SEARCH_SUB_MERCHANT = "sub-merchant-search";

	String CHATAK_ADMIN_SHOW_SUB_MERCHANT_LIST = "showSubMerchantList";

	String CHATAK_ADMIN_DASH_BOARD_SETTLEMRNT_ACTION = "process-dash-board-settlement-action";

	String CHATAK_ADMIN_DASH_BOARD_SEARCH_TRANSACTION_PAGE = "dash-board-transactions-search";

	String CHATAK_ADMIN_SEARCH_TRANSACTION_ON_STATUS = "searchTransactionBySettlementStatus";

	String CHATAK_ADMIN_TRANSACTION_PAGINATION_ON_STATUS = "getTransactionsOnStatus";

	// payment scheme edit page //

	String SHOW_CHATAK_PAYMENT_SCHEME_SEARCH = "payment-scheme-search-urlpage";

	String CHATAK_PAYMENT_SCHEME_SEARCH_PAGE = "payment-scheme-search";

	String CHATAK_PAYMENT_SCHEME_SEARCH_ACTION = "payment-scheme-search-action";

	String CHATAK_ADMIN_PAYMENT_SCHEME_SHOW_CREATE_PAGE = "payment-scheme-create";

	String CHATAK_ADMIN_PAYMENT_SCHEME_CREATE = "createPaymentScheme";

	String CHATAK_ADMIN_SHOW_EDIT_PAYMENT_SCHEME = "edit-Payment-Scheme";

	String CHATAK_ADMIN_EDIT_PAYMENT_SCHEME = "payment-scheme-edit-page";
	
	String CHATAK_ADMIN_VIEW_PAYMENT_SCHEME = "view-payment-scheme";

	String CHATAK_ADMIN_UPDATE_PAYMENT_SCHEME = "updatePaymentScheme";
	
	String CHATAK_ADMIN_PAYMENT_SCHEME_ACTIVATION_SUSPENTION = "paymentSchemeActivationSuspention";

	String CHATAK_ADMIN_UNIQUE_PAYMENT_SCHEME_EMAIL_ID = "uniqueContactEmail";
	
	String CHATAK_ADMIN_PAGINATION_PAYMENT_SCHEME = "getPaymentSchemeInfo";
	
	String CHATAK_ADMIN_REPORT_PAYMENT_SCHEME = "getPaymentShemeReport";


	/****************************************
	 * Fee Program
	 **********************/

	String CHATAK_ADMIN_MERCHANT_REPORT = "get-merchant-report";

	String CHATAK_ADMIN_SUB_MERCHANT_REPORT = "get-sub-merchant-report";

	/****************************************
	 * Fee Program
	 **********************/

	String SHOW_FEE_PROGRAM_SEARCH = "show-fee-program-search";

	String FEE_PROGRAM_SEARCH_PAGE = "fee-program-search";

	String SHOW_FEE_PROGRAM_CREATE = "show-fee-program-create";

	String FEE_PROGRAM_CREATE_PAGE = "fee-program-create";

	String FEE_PROGRAM_CREATE = "feeProgramCreate";

	String FEE_PROGRAM_SEARCH = "feeProgramSearch";

	String FEE_PROGRAM_PAGINATION_ACTION = "getFeeProgramDetails";

	String DOWNLOAD_FEE_PROGRAM_REPORT = "downloadFeeProgramreport";

	String SHOW_FEE_PROGRAM_EDIT = "editFeeProgram";
	
	String SHOW_FEE_PROGRAM_VIEW = "fee-program-view";

	String FEE_PROGRAM_EDIT_PAGE = "fee-program-edit";

	String UPDATE_FEE_PROGRAM = "updateFeeProgram";
	
	String FEE_PROGRAM_STATUS_CHANGE = "feeProgramActivationSuspention";

	String VALIDATE_FEEPGM_ACCOUNTNUM = "validateFeePgmAccNo";
	
	String SHOW_FEE_PROGRAM_DELETE = "deleteFeeProgram";

	/*************************************** Fee Code ************************/

	String CHATAK_ADMIN_FEE_CODE_PAGE = "fee-code-search";

	String CHATAK_ADMIN_FEE_CODE_CREATE_PAGE = "fee-code-create";

	String CHATAK_ADMIN_FEE_CODE_SEARCH = "searchFeeCode";

	String CHATAK_ADMIN_FEE_CODE_PAGINATION = "getFeeCodes";

	String CHATAK_ADMIN_CREATE_FEE_CODE = "createFeeCode";

	/**************************************** User Roles **********************/

	String CHATAK_ADMIN_ACCESS_ROLE_SEARCH = "access-role-search";

	String CHATAK_ADMIN_ACCESS_ROLE_CREATE = "access-role-create";

	String ROLE_PAGINATION_ACTION = "role-pagination-action";

	String DOWNLOAD_ROLE_REPORT = "downloadRoleport";

	String CHATAK_ADMIN_UNIQUE_ROLENAME_VALIDATE = "uniqueRoleName";

	String UPDATE_ROLE = "updateRole";

	String CHATAK_ADMIN_ACCESS_ROLE_SEARCH_EDIT = "editRoleSearch";

	String CHATAK_ADMIN_ACCESS_ROLE_SEARCH_CREATE = "createRoleSearch";

	String CHATAK_ADMIN_CHANGE_PSWD = "change-password";

	String CHATAK_ADMIN_MYPROFILE = "chatak_admin_myprofile";

	String UPDATE_USER_PROFILE = "updateUserProfile";

	String CHATAK_USER_CREATE = "access-user-create";

	String GET_STATES = "getStatesById";
		
	String GET_LOCAL_CURRENCY_BNAK_NAME = "getLocalCurrencyandbankName";
	
	String GET_CURRENCY_MERCHANT_CODE = "getmerchantCodeByCurrency";

	String DELETE_MERCHANT_USER = "deleteMerchantUser";

	String ADMIN_ROLE_NAME = "adminRoleName";

	String PROCESS_CREATE_ROLE = "processCreateRole";

	String CHATAK_SHOW_ROLE_EDIT = "editRole";
	
	String CHATAK_SHOW_ROLE_VIEW = "access-role-view";

	String CHATAK_SHOW_ROLE_EDIT_PAGE = "access-role-edit";

	String PROCESS_CHATAK_ADMIN_ROLE_EDIT = "processChatakAdminEdit";

	String CHATAK_USER_TYPE_VALUE = "chatakUserTypeValue";

	/** End of Service provider mapping constants **/

	String CHATAK_USER_EDIT = "access-user-edit";
	
	String CHATAK_USER_VIEW = "access-user-view";

	String CHATAK_USER_SEARCH = "access-user-search";

	String CHATAK_USER_PAGINATION = "userPaginationAction";

	String DOWNLOAD_USER_REPORT = "downloadUserport";

	String USER_EDIT_DATA = "userEdit";

	String UPDATE_USER = "updateUser";

	String DELETE_ROLE = "deleteRole";

	String ACCESS_ROLE_VIEW = "access-role-view";

	String VIEW_ROLE = "viewRole";

	String FORGOT_PSWD = "forgot-password";

	String PSWD_RESET = "password-reset";

	String RESET_PSWD = "reset-password";

	String INVALID_TOKEN = "invalid-token";

	String ACCESS_INVALID = "access-invalid";

	public static String CHATAK_INVALID_SESSION = "session-invalid";

	public static String CHATAK_USER_EMAIL_VALIDATE = "uniqueUserEmailId";
	
	public static String CHATAK_USERNAME_VALIDATE = "uniqueUserName";

	/** End of Service provider mapping constants **/
	String CHATAK_ADMIN_CREATE_SUB_MERCHANT_PAGE = "sub-merchant-create";

	String CHATAK_ADMIN_CREATE_SUB_MERCHANT = "createSubMerchant";

	String CHATAK_CRYPTO_DOWNLOAD = "cryptoDownload";

	String CHATAK_CRYPTO = "crypto";

	String CHATAK_ADMIN_BULK_SETTLEMRNT_ACTION = "process-bulk-settlement-action";

	String CHATAK_ACQUIRER_FEE_EDIT = "chatak-acquirer-fee-edit";

	String CHATAK_ACQUIRER_FEE_SHOW = "chatak-acquirer-fee-show";

	String CHATAK_PARTNER_FEE_EDIT = "chatak-partner-fee-edit";

	String CHATAK_PARTNER_FEE_SHOW = "chatak-partner-fee-show";

	String CHATAK_PARTNER_FEE_CREATE = "chatak-partner-fee-create";

	String CHATAK_PARTNER_FEE_PAGINATION_ACTION = "getPartnerFeeDetails";

	String SHOW_CHATAK_PARTNER_FEE_EDIT = "showChatakPartnerFeeEdit";

	String PROCESS_CHATAK_PARTNER_FEE_EDIT = "processChatakPartnerFeeEdit";

	String SHOW_CHATAK_AQUIRER_FEE_EDIT = "showChatakAquirerFeeEdit";

	String PROCESS_CHATAK_AQUIRER_FEE_EDIT = "processChatakAquirerFeeEdit";

	String CHATAK_PARTNER_FEE_SEARCH = "chatak-partner-fee-search";

	String CHATAK_GET_PARTNER_FEE_CREATE_PAGE = "chatak-partner-fee-create-page";

	String CHATAK_ACQUIRER_FEE_CREATE_SHOW = "chatak-acquirer-fee-create-show";

	String CHATAK_ACQUIRER_FEE_CREATE_PROCESS = "chatak-acquirer-fee-create-process";

	String CHATAK_FUND_TRANSFER_ALL_REQUESTS_PAGE = "chatak-fund-transfers-show-page";

	String CHATAK_FUND_TRANSFER_LIST_REQUESTS_PAGE = "getTransfersByStatus";

	String CHATAK_FUND_TRANSFER_LIST_REQUESTS_SHOW = "fund-transfers-list-show-page";

	String CHATAK_PROCESS_FUND_TRANSFER_ACTION = "process-transfer-action";

	String CHATAK_ADMIN_BULK_FUND_TRANSFER_ACTION = "process-bulk-fund-transfer-action";

	String CHATAK_ADMIN_FUND_TRANSFER_PAGINATION = "getFundTransferPagination";

	String SPECIFIC_USER_ALL_TRANSACTIONS_REPORTS = "specific-user-allTransactions-show";

	String SPECIFIC_USER_ALL_TRANSACTIONS_REPORTS_SHOW = "specific-user-transaction-reports";

	String SHOW_USER_ACCOUNT_DETAILS = "showUserAccountDetails";

	String SHOW_INDIVIDUAL_USER_ACCOUNT_DETAILS = "specific-individual-user-report";

	String SPECIFIC_USER_ALL_TRANS_REPORT = "downloadSpecificUserAllTransReport";

	String SPECIFIC_USER_REPORT_PAGINATION = "getSpecificUser";

	String GLOBAL_SYSTEM_BALANCES_REPORT = "showGlobalSysBalancesReports";

	String GLOBAL_BALANCE_REPORTS_SHOW = "global-balance-report";
	
	String GLOBAL_BALANCE_PAGINATION_REPORTS = "getGlobalBalanceInfo";	

	String GLOBAL_BALANCE_REPORT_DOWNLOAD = "downloadGlobalBalanceReport";

	String ALL_ACCOUNTS_EXECUTED_TRANSACTIONS_SHOW = "all-accounts-executed-transactions";

	String ALL_ACCOUNTS_EXECUTED_TRANS_DATE_SHOW = "all-accounts-executed-transactions_date";

	String CHATAK_FUND_TRANSFER_EXPORT = "fund-transfer-export";

	String ALL_ACCOUNTS_EXECUTED_TRANSACTIONS = "downloadAllUsersExecutedTransactions";

	String SHOW_USER_ACCOUNT_TRANSACTION_DETAILS = "showUserAccTransDetails";

	String CHATAK_REQUESTS_EXPORT = "fund-request-export";

	String CHATAK_EFT_BATCH_EXPORT = "eft-batch-export";

	String SPECIFIC_USER_STATEMENT_REPORT = "specific-user-statement-show";

	String SPECIFIC_USER_STATEMENT_REPORTS_SHOW = "specific-user-statement-report";

	String SHOW_INDIVIDUAL_STATEMENT_REPORT = "showStatementAccountDetails";

	String SHOW_INDIVIDUAL_STATEMENT_ACC_DETAILS = "specific-statment-account-details";

	String SHOW_STATEMENT_TRANSACTION_DETAILS = "showStatementTransDetails";

	String SPECIFIC_STATEMENT_REPORT_PAGINATION = "getStatementUsers";

	String GLOBAL_BANK_EFT_TRANSFER_REPORT = "showGlobalSysBankEFTReports";

	String SHOW_GLOBAL_BANK_EFT_TRANSFER_REPORT = "global-bank-eft-report";

	String SHOW_GLOBAL_MANUAL_TRANS_REPORT_DATES = "showGlobalSysManualTransReports";

	String SHOW_GLOBAL_MANUAL_TRANSACTION_REPORT = "global-manual-transactions-report";

	String GLOBAL_MANUAL_TRANSACTION_REPORT = "showGlobalManualTransferReport";
	
	String MANNUAL_TRXN_PAGINATION = "mannual-txn-pagination";

	String GLOBAL_REVENUE_GENERATED_REPORTS_DATES = "showGlobalSysRevenueGeneratedReports";

	String SHOW_GLOBAL_REVENUE_GENERATED_REPORT = "global-revenue-generated-report";

	String GLOBAL_REVENUE_GENERATED_REPORT = "globalRevenueGeneratedReports";

	String GLOBAL_BANK_EFT_DOWNLOAD = "downloadBankEFTReport";

	String GLOBAL_MANUAL_TRANSFER_DOWNLOAD = "downloadGlobalManualReport";

	String GLOBAL_PENDING_TRANS_REPORT_DATES = "showGlobalSysPendingTransReports";

	String SHOW_GLOBAL_PENDING_TRANS_REPORT = "global-pending-transactions-report";

	String GLOBAL_PENDING_TRANSACTION_REPORT = "showGlobalPendingTransactionReport";
	
	String GLOBAL_PENDING_TRANSACTION_PAGINATION = "getPendingTranscationInfo";

	String GLOBAL_ALL_TRANSACTIONS_REPORT_DATES = "showGlobalSysAllTransReports";

	String SHOW_GLOBAL_ALL_TRANS_REPORT = "global-all-transactions-report";

	String GLOBAL_ALL_TRANSACTION_REPORT = "showGlobalAllTransactionReport";

	String GLOBAL_PENDING_TRANS_DOWNLOAD = "downloadPendingTransReport";

	String GLOBAL_ALL_TRANSACTIONS_DOWNLOAD = "downloadAllTransactionsReport";

	String DOWNLOAD_SPECIFIC_USER_STATEMENT_REPORT = "downloadSpecificUserStatementReport";

	String GLOBAL_ACCESS_LOG_REPORT = "showGlobalSysAccessLogReports";

	String SHOW_GLOBAL_ACCESS_LOG_REPORT = "global-access-log-report";

	String SYSTEM_OVERVIEW_REPORT = "showSystemOverviewReports";

	String SHOW_SYSTEM_OVERVIEW_REPORT = "system-overview-report";

	String ACCESS_LOGS_REPORT_DOWNLOAD = "downloadAccessLogsReports";

	String CHATAK_ADMIN_UNIQUE_USER_VALIDATE_EDIT = "uniqueUserEdit";

	String CHATAK_ADMIN_UNIQUE_EMAIL_VALIDATE_EDIT = "uniqueEmailIdEdit";

	String DOWNLOAD_SPECIFIC_USER_TRANSACTION_REPORT = "downloadSpecificUserTransReport";

	String DOWNLOAD_SPECIFIC_USER_STATEMENTLIST_REPORT = "downloadSpecificUserStatementListReport";

	String DOWNLOAD_REVENUE_GENERATED_REPORT = "downloadRevenueGeneratedList";

	String SYSTEM_OVERVIEW_REPORT_DOWNLOAD = "downloadOverviewReport";

	String LITLE_EFT_EXECUTED_TRANSACTIONS = "getLitleEFTTransactionListToDashBoard";

	String LITLE_EFT_EXECUTED_TRANSACTIONS_SHOW = "dash-board-litle-eft-transactions-search";

	String LITLE_EFT_EXECUTED_TRANSACTIONS_PAGINATION = "getEFTPagination";

	String CHATAK_ADMIN_BULK_EFT_ACTION = "process-bulk-litle-eft-action";

	String DASH_BOARD_EFT_SETTLEMENT_ACTION = "process-eft-dash-board-settlement-action";

	String CHATAK_ADMIN_VIRTUAL_TERMINAL_SALE_PAGE = "virtual-terminal-sale";

	String CHATAK_ADMIN_VIRTUAL_TERMINAL_SALE_PROCESS = "do-virtual-terminal-sale";

	String CHATAK_ADMIN_VIRTUAL_TERMINAL_PRE_AUTH_PAGE = "virtual-pre-auth";

	String CHATAK_ADMIN_VIRTUAL_TERMINAL_PRE_AUTH_PROCESS = "do-virtual-terminal-preAuth";

	String CHATAK_ADMIN_VIRTUAL_TERMINAL_ADJUST_PAGE = "virtual-terminal-adjust";

	String CHATAK_ADMIN_VIRTUAL_TERMINAL_ADJUST_PROCESS = "do-virtual-terminal-adjust";

	String CHATAK_ADMIN_VIRTUAL_TERMINAL_FETCH_TRAN_ADJUST = "getTransactionForAdjust";

	String CHATAK_ADMIN_VIRTUAL_TERMINAL_FETCH_TRAN = "getTransaction";

	String CHATAK_ADMIN_VIRTUAL_TERMINAL_VOID_PAGE = "virtual-terminal-void";

	String CHATAK_ADMIN_VIRTUAL_TERMINAL_VOID_PROCESS = "do-virtual-terminal-void";

	String CHATAK_ADMIN_VIRTUAL_TERMINAL_REFUND_PAGE = "virtual-terminal-refund";

	String CHATAK_ADMIN_VIRTUAL_TERMINAL_REFUND_PROCESS = "do-virtual-terminal-refund";

	String CHATAK_ADMIN_VIRTUAL_TERMINAL_PREAUTH_COMPLEATION_PAGE = "virtual-terminal-pre-auth-completion";

	String CHATAK_ADMIN_VIRTUAL_TERMINAL_PREAUTH_COMPLEATION_PROCESS = "do-virtual-terminal-pre-auth-completion";

	String CHATAK_ADMIN_SPECIFIC_USER_EFT_TRANSFERS = "specific-user-eftTransfers-show";

	String SHOW_CHATAK_ADMIN_SPECIFIC_USER_EFT_TRANSFERS = "specific-eft-transfers-report";

	String PROCESS_CHATAK_ADMIN_SPECIFIC_USER_EFT_TRANSFERS = "processSpecificEFTTransfers";

	String DOWNLOAD_SPECIFIC_EFT_TRANSFERS_REPORT = "downloadSpecificEFTTransferReport";

	String CHATAK_ADMIN_TRANSACTION_VOID_PAGE = "transaction-void";

	String CHATAK_ADMIN_TRANSACTION_VOID_PROCESS = "do-transaction-void";

	String PROCESS_CHATAK_ADMIN_TRANSACTION_VOID = "processTransactionVoid";

	String CHATAK_ADMIN_TRANSACTION_REFUND_PAGE = "do-transaction-refund";

	String SHOW_CHATAK_ADMIN_TRANSACTION_REFUND = "transaction-refund";

	String PROCESS_CHATAK_ADMIN_TRANSACTION_REFUND = "process-transaction-refund";

	String CHATAK_EFT_FETCH_TRAN_ID = "doAjaxFetchTxnIdList";

	String DOWNLOAD_TRANSACTIONS_FROM_DASHBOARD = "get-transaction-report-from-dashBoard";

	String DOWNLOAD_EFT_TRANSACTIONS_FROM_DASHBOARD = "get-EFTtransaction-report-from-dashBoard";

	String SHOW_VIRTUAL_FEE_LOG_DATE_RANGE = "showVirtualFeeLogReports";

	String SHOW_VIRTUAL_FEE_LOG_REPORT = "virtual-fee-log";

	String SHOW_VIRTUAL_FEE_LOG = "showVirtualFeeLogReport";

	String VIRTUAL_FEE_LOG_REPORT_DOWNLOAD = "downloadVirtualFeeReport";

	String SHOW_ACC_MANUAL_CREDIT = "accounts-manual-credit";

	String SHOW_ACC_MANUAL_DEBIT = "accounts-manual-debit";

	String AJAX_MERCHANT_BALANCE_DETAILS = "getMerchantAccBalanceDetails";

	String PROCESS_ACC_MANUAL_CREDIT = "process-manual-credit";

	String PROCESS_ACC_MANUAL_DEBIT = "process-manual-debit";

	String CHATAK_PARTNER_AND_AGENT_DETAILS = "get-partner-agent-details";

	String CHATAK_MERCHANT_ACCOUNT_INITIAL_CREATE_PAGE = "merchant-details-account-create";

	String CHATAK_MERCHANT_ACCOUNT_CREATE_PAGE = "merchant-account-create-page";

	String CHATAK_MERCHANT_ACCOUNT_CREATE = "merchant-account-create";

	String CHATAK_MERCHANT_ACCOUNT_SEARCH_PAGE = "merchant-account-search";

	String CHATAK_MERCHANT_ACCOUNT_SEARCH_PAGINATION = "merchant-account-search-pagination";

	String CHATAK_SUB_MERCHANT_ACCOUNT_SEARCH_PAGE = "sub-merchant-account-search";

	String CHATAK_SUB_MERCHANT_ACCOUNT_SEARCH_PAGINATION = "sub-merchant-account-search-pagination";

	String CHATAK_MERCHANTDETAILS_ACCOUNT_CREATE_PAGINATION = "getMerchantDetailsForAccountCreate";

	String CHATAK_MERCHANT_ACCOUNT_SEARCH_REPORT = "merchant-account-search-report";

	String CHATAK_SUB_MERCHANT_ACCOUNT_SEARCH_REPORT = "sub-merchant-account-search-report";

	String CHATAK_MERCHANT_ACCOUNT_STATUS_CHANGE = "change-Account-Status";

	String CHATAK_MERCHANT_ACCOUNT_EDIT_PAGE = "merchant-account-edit-page";

	String CHATAK_MERCHANT_ACCOUNT_EDIT = "merchant-account-edit";

	String CHATAK_FETCH_ACCOUNT_DETAILS = "chatak-fetch-account-details";

	String CHATAK_PROCESS_ACCOUNT_TRANSFER = "show-account-transfer";

	String CHATAK_MERCHANT_PROCESSING_TRANSACTIONS = "processing-transaction-details";

	String CHATAK_MERCHANT_PROCESSING_TRANSACTIONS_PAGINATION = "processing-transaction-details-pagination";

	String CHATAK_MERCHANT_EXECUTED_TRANSACTIONS = "executed-transaction-details";

	String CHATAK_MERCHANT_EXECUTED_TRANSACTIONS_PAGINATION = "executed-transaction-details-pagination";

	String CHATAK_MERCHANT_PROCESSING_TRANSACTIONS_REPORT = "processing-transaction-details-report";

	String CHATAK_MERCHANT_EXECUTED_TRANSACTIONS_REPORT = "executed-transaction-details-report";

	String CHATAK_PROCESS_POPUP_ACTION = "process-popup-action";

	String CHATAK_TRANSACTION_POPUP_DETAILS = "get-transaction-popup";

	// Switch INFO
	String CHATAK_ADMIN_SWITCH_CREATE_PAGE = "switch-create";

	String CHATAK_ADMIN_SWITCH_CREATE = "createSwitch";

	String CHATAK_ADMIN_SEARCH_SWITCH_PAGE = "switch-search";

	String CHATAK_ADMIN_SEARCH_SWITCH = "searchSwitch";

	String CHATAK_ADMIN_SHOW_EDIT_SWITCH = "editSwitch";

	String CHATAK_ADMIN_EDIT_SWITCH = "switch-update";

	String CHATAK_ADMIN_UPDATE_SWITCH = "updateSwitch";

	String CHATAK_ADMIN_VIEW_SWITCH_PAGE = "switch-view";

	String CHATAK_ADMIN_SWITCH_PAGINATION = "getSwitchInfo";

	String CHATAK_ADMIN_SWITCH_REPORT = "get-switch-report";

	/*************************************** Bank Data ************************/
	String BANK_CREATE = "bank-create";

	String BANK_SEARCH = "bank-search";

	String GET_STATES_BY_COUNTRY_ID = "getStatesByCountryId";

	String BANK_EDIT = "bank-edit";

	String BANK_DELETE = "bank-delete";

	String BANK_UPDATE = "bank-update";
	
	String BANK_STATUS_CHANGE = "bankActivationSuspention";

	String BANK_VIEW = "bank-view";

	String BANK_PAGINATION = "getBanks";

	String GET_BANK_REPORT = "get-bank-report";

	/*************************************** Manage ************************/

	String CHATAK_ADMIN_SEARCH_RESELLER_PAGE = "reseller-search-page";

	String CHATAK_ADMIN_SEARCH_RESELLER = "reseller-search";

	String CHATAK_ADMIN_RESELLER_CREATE_PAGE = "reseller-create-page";

	String CHATAK_ADMIN_SHOW_CREATE_RESELLER = "reseller-create";

	String CHATAK_ADMIN_CREATE_RESELLER = "createReseller";

	String CHATAK_ADMIN_SEARCH_PROCESS_RESELLER_PAGE = "searchReseller";

	String CHATAK_ADMIN_SHOW_EDIT_RESELLER = "editReseller";

	String CHATAK_ADMIN_UPDATE_RESELLER = "updateReseller";

	String CHATAK_ADMIN_EDIT_RESELLER = "reseller-update";

	String CHATAK_ADMIN_DELETE_RESELLER = "deleteReseller";

	String CHATAK_ADMIN_RESELLER_PAGE_VIEW = "reseller-viewdata";

	String CHATAK_ADMIN_VIEW_RESELLERS_PAGE = "reseller-view";

	String CHATAK_ADMIN_RESELLER_PAGINATION = "getResellers";

	String CHATAK_ADMIN_RESELLER_REPORT = "get-reseller-report";

	String CHATAK_ADMIN_CREATE_BLACK_LISTED_CARD_PAGE = "black-listed-card-create";

	String CHATAK_ADMIN_CREATE_BLACK_LISTED_CARD = "createBlackListedCard";

	String CHATAK_ADMIN_SEARCH_BLACK_LISTED_CARD_PAGE = "black-listed-card-search";

	String CHATAK_ADMIN_SEARCH_BLACK_LISTED_CARD = "searchBlackListedCard";

	String CHATAK_ADMIN_SHOW_EDIT_BLACKLISTED_CARD = "editBlackListedCard";
	
	String CHATAK_ADMIN_SHOW_VIEW_BLACKLISTED_CARD = "viewBlackListedCard";
	
	String CHATAK_ADMIN_VIEW_BLACKLISTED_CARD = "viewBlackListedCard";
	
	String CHATAK_ADMIN_EDIT_BLACK_LISTED_CARD = "black-listed-card-edit";

	String CHATAK_ADMIN_UPDATE_BLACK_LISTD_CARD = "updateBlackListedCard";
	
	String CHATAK_ADMIN_ACTIVATION_SUSPENTION_BLACK_LISTD_CARD = "blackListedCardActivationSuspention";

	String CHATAK_BLACK_LISTED_CARD_PAGINATION = "getBlackListedCardInfo";

	String CHATAK_ADMIN_BLACK_LISTED_CARD_REPORT = "getblackListedCardreport";

	String PENDING_MERCHANT_SHOW = "pending-merchant-show";

	String DOWNLOAD_PENDING_NMAS = "pending-nmas-download";

	String SHOW_ALL_PENDING_MERCHANTS = "show-all-pending-merchants";

	String MERCHANT_PENDING_TO_ACTIVE_STATE = "merchant-pending-to-active";
	
	String DECLINE_MERCHANT = "merchant-decline";

	/* MCC */
	String CHATAK_ADMIN_MERCHANT_CATEGORY_CODE_CREATE = "merchant-category-code-create";

	String CHATAK_ADMIN_MERCHANT_CATEGORY_CODE_SEARCH = "merchant-category-code-search";

	String CHATAK_ADMIN_MERCHANT_CATEGORY_CODE_EDIT = "merchant-category-code-edit";

	String CHATAK_ADMIN_MERCHANT_CATEGORY_CODE_UPDATE = "merchant-category-code-update";
	
	String CHATAK_ADMIN_MERCHANT_CATEGORY_CODE_ACTIVATION_SUSPENTION = "merchantCategoryCodeActivationSuspention";

	String CHATAK_ADMIN_MERCHANT_CATEGORY_CODE_VIEW = "merchant-category-code-view";

	String CHATAK_ADMIN_MERCHANT_CATEGORY_CODE_PAGINATION = "merchant-category-code-pagination";
	
	String CHATAK_ADMIN_MERCHANT_CATEGORY_CODE_DELERE = "deleteMCC";

	String ONUS_BIN_CONFIGURATION_SHOW = "bin-search-show";

	String ONUS_BIN_SEARCH = "bin-search";

	String ONUS_BIN_CREATE_SHOW = "show-bin-create";

	String ONUS_BIN_CREATE = "bin-create";

	String ONUS_BIN_SAVE_PROCESS = "bin-save-update";

	String ONUS_BIN_EDIT = "editBin";
	
	String ONUS_BIN_VIEW = "viewBin";

	String ONUS_BIN_DELETE = "deleteBin";

	String ONUS_BIN_UPDATE = "updateBin";
	
	String ONUS_BIN_ACTIVATION_SUSPENTION = "binActivationSuspention";

	String GET_BINS_FOR_PAGINATIONS = "getBinForPagination";

	String GET_BIN_REPORT = "get-bin-report";

	String SHOW_COMMISION_PROGRAM_SEARCH = "show-commission-program-search";

	String COMMISION_PROGRAM_SEARCH_PAGE = "commission-program-search";

	String SHOW_COMMISION_PROGRAM_CREATE = "show-commission-program-create";

	String COMMISION_PROGRAM_CREATE_PAGE = "commission-program-create";

	String COMMISION_PROGRAM_CREATE = "commissionProgramCreate";

	String COMMISION_PROGRAM_SEARCH = "commissionProgramSearch";

	String CHATAK_ADMIN_SHOW_EDIT_COMMISION_PROGRAM = "commissionProgramEdit";

	String CHATAK_ADMIN_EDIT_COMMISION_PROGRAM = "commission-program-update";

	String CHATAK_ADMIN_UPDATE_COMMISION_PROGRAM = "commissionProgramUpdate";

	String CHATAK_ADMIN_COMMISION_PROGRAM_REPORT = "get-commission-program-report";

	String COMMISION_PROGRAM_PAGINATION = "commissionProgramPagination";

	String CHATAK_ADMIN_UNIQUE_BIN_VALIDATE = "uniqueBinId";

	/*************************************** CAPublicKeys *********************************/
	String CHATAK_ADMIN_CREATE_CA_PUBLIC_KEYS = "createCAPublicKeys";

	String CHATAK_ADMIN_CREATE_CA_PUBLIC_KEYS_PAGE = "ca-public-keys-create";

	String SHOW_CA_PUBLIC_KEYS_EDIT = "editCAPublicKeys";
	
	String SHOW_CA_PUBLIC_KEYS_VIEW = "ca-public-key-view";

	String CA_PUBLIC_KEYS_EDIT_PAGE = "ca-public-keys-edit";

	String UPDATE_CA_PUBLIC_KEYS = "updateCAPUBLICKEYS";
	
	String CA_PUBLIC_KEYS_ACTIVATION_SUSPENTION = "caPublicKeyActivationSuspention";

	String SHOW_CA_PUBLIC_KEYS_SEARCH_PAGE = "show-ca-public-keys-search-page";

	String CA_PUBLIC_KEYS_SEARCH_PAGE = "ca-public-keys-search-page";

	String CA_PUBLIC_KEYS_SEARCH = "caPublicKeysSearch";

	String CA_PUBLIC_KEYS_PAGINATION = "getCaPublicKeysInfo";

	String DOWNLOAD_CA_PUBLIC_KEYS_REPORT = "downloadCaPublicKeysreport";
	
	String DELETE_CA_PUBLIC_KEY = "deleteCAPublicKey";

	/* MDR */

	String DYNAMIC_MDR_SHOW_SEARCH = "show-dynamic-MDR-search";

	String PROCESS_DYNAMIC_MDR_SEARCH = "process-dynamic-mdr-search";

	String DYNAMIC_MDR_SHOW_CREATE = "show-dynamic-MDR-create";

	String DYNAMIC_MDR_SHOW_CREATE_PAGE = "show-dynamic-MDR-create-page";

	String PROCESS_DYNAMIC_MDR_CREATE = "process-dynamic-mdr-create";

	String DYNAMIC_MDR_SHOW_EDIT = "show-dynamic-MDR-edit";

	String PROCESS_DYNAMIC_MDR_UPDATE = "process-dynamic-MDR-update";

	String DYNAMIC_MDR_PAGINATION = "dynamic-MDR-Pagination";

	String DOWNLOAD_DYNAMIC_MDR_REPORT = "download-dynamic-mdr-report";

	/** Start of DCC markup constants **/

	String CHATAK_ADMIN_CREATE_DCC_MARKUP_PAGE = "chatak-admin-dcc-markup-create-page";

	String CHATAK_MERCHANT_CURRENCY_CODE = "getCurrencyCodes";

	String CHATAK_ADMIN_SEARCH_MARKUP_FEE_PAGE = "dcc-markup-search";

	String CHATAK_ADMIN_SEARCH_MARKUP_FEE = "chatak-admin-search-markup-fee";

	String CHATAK_ADMIN_CREATE_PROCESS = "processMarkupFeeCreate";

	String CHATAK_ADMIN_EDIT_DCC_MARKUP = "editDccMarkup";

	String CHATAK_ADMIN_EDIT_DCC_MARKUP_PAGE = "chatak-admin-dcc-markup-edit-page";

	String CHATAK_ADMIN_DCC_MARKUP_DELETE = "deleteDccMarkup";

	String CHATAK_ADMIN_UPDATE_PROCESS = "processMarkupFeeUpdate";

	/** End of DCC markup constants **/

	// Exchange Rate
	String CHATAK_ADMIN_EXCHANGE_RATE_CREATE_PAGE = "exchange-rate-create";

	String CHATAK_ADMIN_EXCHANGE_RATE_CREATE = "createExchangeRate";

	String CHATAK_ADMIN_SHOW_EDIT_EXCHANGE = "editExchangeRate";

	String CHATAK_ADMIN_EXCHANGE_RATE_DELETE = "deleteExchangeRate";

	String CHATAK_ADMIN_EDIT_EXCHANGE = "exchange-rate-update";

	String CHATAK_ADMIN_UPDATE_EXCHANGE = "updateExchangeRate";
	
	String CHATAK_PARTIAL_REFUND_BALANCE="chatak-partial-refund-balance";
	
	// MerchantCategoryCode dowload url
	String GET_MERCHANT_CATEGORY_CODE_REPORT = "get-mcc-report";
	
	//role
	String ROLE_ACTIVATION = "roleActivation";
	
	String USER_ACTIVATION_SUSPENTION = "userActivationSuspention";
	
	String SWITCH_ACTIVATION_SUSPENTION = "switchActivationSuspention";

	/******************************* CURRENCY *********************************/
	
	String CHATAK_ADMIN_SHOW_CURRENCY_PAGE = "show-currency-search";
	
	String CHATAK_ADMIN_CURRENCY_SEARCH_PAGE = "currency-search-page";
	
	String CHATAK_ADMIN_CURRENCY_SEARCH_ACTION = "currency-search-action";
	
	String CHATAK_ADMIN_SHOW_CURRENCY_CREATE_PAGE = "currency-create-page";
	
	String CHATAK_ADMIN_CURRENCY_CREATE_PAGE = "createcurrency";
	
	String CHATAK_ADMIN_SHOW_EDIT_CURRENCY = "edit-currency";
	
	String CHATAK_ADMIN_SHOW_VIEW_CURRENCY = "currency-view";
	
	String CHATAK_ADMIN_CURRENCY_EDIT_PAGE = "currency-edit-page";
	
	String CHATAK_ADMIN_UPDATE_CURRENCY = "updateCurrency";
	
	String CHATAK_ADMIN_CURRENCY_ACTIVATION_SUSPENTION = "currencyActivationSuspention";
		
	String CHATAK_ADMIN_DELETE_CURRENCY = "deleteCurrency";
	
	String CHATAK_ADMIN_PAGINATION_CURRENCY = "getCurrencyInfo";
	
	String CHATAK_ADMIN_DOWNLOAD_CURRENCY = "get-currency-report";
	
	String CHATAK_ADMIN_GET_CURRENCIES_FOR_BANK = "getCurrenciesForBankId";
	
	String CHATAK_ADMIN_GET_CURRENCY_DATA  = "getCurrencyDataByCurrencyName";
	
	String CHATAK_ADMIN_GET_MERCHANT_CURRENCY  = "getMerchantCurrency";
	
	String CHATAK_ADMIN_VALIDATE_AGENT_DETAILS  = "validateAgentDetails";
	
	String GET_AGENT_DATA_BY_ID  = "getAgentDataById";
	
	String GET_AGENT_NAMES_BY_CURRENCY_ALPHA  = "getAgentNamesByCurrencyAlpha";  
	
	String CHATAK_ADMIN_PUBLICKEYNAME_VALIDATE = "uniquepublicKeyName"; 
	
	String CHATAK_ADMIN_CARDNUMBER_VALIDATE = "uniquecardNumber"; 
	
	String CHATAK_ADMIN_PAYMENT_SCHEME_VALIDATE = "uniquePaymentSchemeName";
	
	String CHATAK_ADMIN_FEEPROGRAM_VALIDATE = "uniquefeeProgramName";  
	
	String CHATAK_ADMIN_UNBLOCK_USERS = "admin-reset-password-unblock";
	
	String CHATAK_ADMIN_UNBLOCK_USERS_SEARCH = "unblockUser";
	
	String CHATAK_ADMIN_DO_UNBLOCK_USERS = "performUserUnblock";
	
	String CHATAK_ADMIN_GET_SETTLEMENT_REPORT = "getSettlementReport";

	String CHATAK_ADMIN_SHOW_BATCH_REPORT = "batch-report";
	
	String CHATAK_ADMIN_GET_BATCH_REPORT = "getBatchReport";

	String CHATAK_ADMIN_SHOW_DAILY_FUNDING_REPORT = "daily-funding-report";
	
	String CHATAK_ADMIN_GET_DAILY_FUNDING_REPORT = "getDailyFundingReport";
	
	String GET_SUB_MERCHANTS_BY_MERCHANT_CODE = "getSubMerchantsByMerchantId";
	
	String CHATAK_ADMIN_BATCH_TRANSACTION_PAGINATION = "getBatchTransactions";
	
	String CHATA_ADMIN_BATCH_TRANSACTION_EXPORT = "get-batch-transaction-report";
	
	String CHATAK_ADMIN_FUNDING_REPORT_PAGINATION = "get-daily-funding-Transactions";
    
    String CHATA_ADMIN_FUNDING_REPORT_EXPORT = "get-daily-funding-report";
	
	String CHATA_ADMIN_MANUAL_FUNDING_REPORT = "process-manualFunding-trigger";
	
	String CHATAK_ADMIN_MERCHANTCODE_BY_NAME = "fetchMerchantIdByName";

	String CHATAK_MERCHANT_TXN_HISTORY = "merchant-txns-history-on-batchid";

	String CHATAK_MERCHANT_TXN_HISTORY_REPORT = "get-transaction-history-report";

	String CHATAK_TRANSACTION_HISTORY_PAGINATION = "get-transaction-history-pagination";

	String SHOW_PREPAID_ADMIN_SEARCH_PROGRAM_MANAGER = "showProgramManager";

    String PREPAID_ADMIN_SEARCH_PROGRAM_MANAGER_PAGE = "program-manager-search";

    String PREPAID_ADMIN_CREATE_PROGRAM_MANAGER_PAGE = "program-manager-create";

    String PREPAID_ADMIN_EDIT_PROGRAM_MANAGER_PAGE = "program-manager-edit";

    String SHOW_PREPAID_ADMIN_CREATE_PROGRAM_MANAGER = "showCreateProgramManager";

    String PREPAID_ADMIN_CREATE_PROGRAM_MANAGER = "createProgramManager";

    String PREPAID_ADMIN_SEARCH_PROGRAM_MANAGER = "searchProgramManager";

    String PREPAID_ADMIN_PROGRAM_MANAGER_PAGINATION_ACTION = "programManagerPaginationAction";

    String PREPAID_ADMIN_PROGRAM_MANAGER_MCC_PAGINATION_ACTION = "programManagerMccPaginationAction";

    String PREPAID_ADMIN_PROGRAM_MANAGER_REPORT = "downloadProgramManagerReport";

    String SHOW_PREPAID_ADMIN_EDIT_PROGRAM_MANAGER = "showEditProgramManager";

    String PREPAID_ADMIN_UPDATE_PROGRAM_MANAGER = "updateProgramManager";

    String PREPAID_ADMIN_CHANGE_PROGRAM_MANAGER_STATUS = "changeProgramManagerStatus";

    String PREPAID_ADMIN_FETCH_CURRENCY = "fetchCurrency";
    
    String SHOW_PREPAID_ADMIN_SEARCH_PARTNER = "showSearchPartner";

    String PREPAID_ADMIN_SEARCH_PARTNER = "searchPartner";

    String PREPAID_ADMIN_SEARCH_PARTNER_PAGE = "partner-search";

    String SHOW_PREPAID_ADMIN_CREATE_PARTNER = "showCreatePartner";

    String PREPAID_ADMIN_CREATE_PARTNER_PAGE = "partner-create";

    String SHOW_PREPAID_ADMIN_EDIT_PARTNER = "showEditPartner";

    String PREPAID_ADMIN_EDIT_PARTNER_PAGE = "partner-edit";

    String PREPAID_ADMIN_UPDATE_PARTNER = "updatePartner";

    String PREPAID_ADMIN_CREATE_PARTNER = "createPartner";

    String CHANGE_PARTNER_STATUS = "changePartnerStatus";

    String PARTNER_PAGINATION_ACTION = "partnerPaginationAction";

    String PARTNER_MCC_PAGINATION_ACTION = "partnerMccPaginationAction";

    String DOWNLOAD_PARTNER_REPORT = "downloadPartnerReport";

    String FETCH_BANKS_OF_PGMNG = "fetchBanksofPgmng";

    String PREPAID_ADMIN_SHOW_PROGRAM_MANAGER_PARTNER = "prepaid-admin-program-manager-partner-create";
    
    String CHATAK_ADMIN_GET_PM_DATA_BY_PARTNER = "getProgramManagerDetailsByPartner";
    
    String GET_PARTNER_NAME_BY_MERCHANT_CODE = "getPartnerNameByMerchantCode";
    
   /* download LOgs*/

   String CHATAK_DOWNLOAD_LOGS ="download-logs";
   	 
   String CHATAK_PROCESS_DOWNLOAD_LOGS ="chatak-process-download-logs";
   	 
   String CHATAK_DOWNLOAD_FORM_LOGS ="downloadFormLog";

   String GET_PARTNERS_BY_PROGRAM_MANAGER_ID = "getPartnersByProgramManagerId";
   
   String GET_ISSUANCE_PM_DETAILS_BY_PROGRAM_MANAGER_TYPE = "getIssunacePMDetailsByPmType";
   
   String GET_ISSUANCE_PM_DETAILS_BY_PROGRAM_MANAGER_ID = "getIssunacePMDetailsByPmId";
   
   String GET_ISSUANCE_PM_CARD_PROGRAMS_BY_PROGRAM_MANAGER_ID = "getIssunacePMCardProgramsByPmId";
   
   String GET_INDEPENDENT_PM_DETAILS_PM_DETAILS_BY_PROGRAM_MANAGER_TYPE = "getIndependentPMDetails";
   
   String GET_BANK_DETAILS_BY_CURRENCY = "getBankDetailsByCurrency";
   
   String GET_ENTITY_DETAILS_BY_ENTITY_NAME = "getAllEntityName"; 

   String SHOW_ISO_CREATE = "showIsoCreate";
   
   String ISO_CREATE_VIEW = "iso-create";
   
   String PROCESS_ISO_CREATE = "createIso";
   
   String  GET_CARD_PROGRAM = "getCardProgramByPmId";
   
   String SHOW_ISO_SEARCH = "showIsoSearch";
   
   String PROCESS_ISO_SEARCH = "processIsoSearch";
   
   String VIEW_ISO_SEARCH = "iso-search";
   
   String VIEW_ISO_EDIT = "iso-edit";
   
   String SHOW_ISO_EDIT = "showIsoEdit";
   
   String UPDATE_ISO = "updateIso";

   String  GET_CARD_PROGRAMS_BY_CURRENCY = "getAcquirerCardProgramDetailsByCurrency";
   
   String FETCH_CARD_PROGRAM_BY_ISO = "fetchCardProgramByIso";
   
   String PREPAID_ADMIN_ISO_PAGINATION_ACTION = "isoPaginationAction";
   
   String GET_ISO_REPORT = "downloadIsoReport";
   
   String ADMIN_CHANGE_ISO_STATUS = "changeIsoStatus";
   
   String FETCH_COUNTRY_TIME_ZONE = "fetchTimeZone";
	
   String GET_PM_STATES_BY_COUNTRY_ID = "getPMStatesByCountryId";
   
   String  FETCH_CARD_PROGRAM_BY_MERCHANTID = "fetchCardProgramByMerchantId";

   String  FETCH_SETTLEMENT_DATA_BY_PMID = "show-incoming-settlement-report";

   String SHOW_ALL_PENDING_SETTLEMENT_DATA = "show-All-incoming-settlement-report";

   String PROCESS_SETTLEMENT_DATA = "processIncomingSettlementData";
   
   String  SETTLEMENT_MONEY_MOVEMENT = "settlement-money-moment-report";
   
   String  EXECUTE_SETTLEMENT_DATA = "executeSettlementData";
   
   String PREPAID_SHOW_FEE_REPORT_PAGE = "showFeeReport";
   
   String PREPAID_PROCESS_FEE_REPORT_PAGE = "processFeeReport";
   
   String PREPAID_FEE_REPORT_PAGE = "fee-report";
   
   String PREPAID_SHOW_ISO_FEE_REPORT = "processISOTxns";
   
   String PREPAID_ISO_FEE_REPORT = "iso-fee-report";
   
   String PREPAID_FEE_REPORT_PAGINATION = "getFeeReportPagination";
   
   String DOWNLOAD_FEE_TXN_REPORT = "downloadFeeTxnReport";
   
   String PREPAID_MATCHED_TRANSACTIONS_PAGE = "matched-transactions";
   
   String PREPAID_SHOW_MATCHED_TRANSACTIONS_PAGE = "getAllMatchedTxnsByPgTxnId";
   
   String SHOW_ISO_REVENUE_REPORT_PAGE = "showIsoRevenueReport";
   
   String ISO_REVENUE_REPORT_PAGE = "iso-revenue-report";
   
   String PROCESS_ISO_REVENUE_REPORT_PAGE = "processIsoRevenueReport";
   
   String PREPAID_ADMIN_FETCH_PARTNER_FOR_ENTITY = "getPartnersEntites";
   
   String SHOW_MATCHED_TRANSACTIONS_REPORT_PAGE = "getAllMatchedTxnsByEntityId";
   
   String SHOW_MERCHANT_REVENUE_REPORT_PAGE = "showMerchantRevenueReport";

   String MERCHANT_REVENUE_REPORT_PAGE = "merchant-revenue-report";
   
   String PROCESS_MERCHANT_REVENUE_REPORT_PAGE = "processMerchantRevenueReport";
   
   String PREPAID_MERCHANT_REPORT_PAGINATION = "getMerchantRevenueReportPagination"; 
   
   String PREPAID_ISO_REPORT_PAGINATION = "getIsoRevenueReportPagination";
   
   String DOWNLOAD_ISO_REPORT = "downloadIsoRevenueReport";
   
   String DOWNLOAD_MERCHANT_REPORT = "downloadMerchantRevenueReport";
   
   String SHOW_PM_REVENUE_REPORT_PAGE = "showPmRevenueReport";
   
   String PM_REVENUE_REPORT_PAGE = "pm-revenue-report";
   
   String PROCESS_PM_REVENUE_REPORT_PAGE = "processPmRevenueReport";
   
   String DOWNLOAD_PM_REVENUE_REPORT = "downloadPmRevenueReport";
   
   String PREPAID_PM_REPORT_PAGINATION = "getPmRevenueReportPagination";
   
   String GET_ISSUANCE_CARD_PROGRAMS_BY_PM_ID = "getIssuanceCardProgramsByPmId";
   
}
