package com.chatak.merchant.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.ws.rs.FormParam;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.chatak.merchant.constants.FeatureConstants;
import com.chatak.merchant.constants.URLMappingConstants;
import com.chatak.merchant.controller.model.ExportDetails;
import com.chatak.merchant.model.GetMerchantDetailsResponse;
import com.chatak.merchant.model.TransactionListResponse;
import com.chatak.merchant.service.AccountService;
import com.chatak.merchant.service.FundTransferService;
import com.chatak.merchant.service.RestPaymentService;
import com.chatak.merchant.service.TransactionService;
import com.chatak.merchant.util.ExportUtil;
import com.chatak.merchant.util.PaginationUtil;
import com.chatak.merchant.util.ProcessorConfig;
import com.chatak.pg.acq.dao.AdminUserDao;
import com.chatak.pg.acq.dao.MerchantDao;
import com.chatak.pg.acq.dao.MerchantProfileDao;
import com.chatak.pg.acq.dao.PGParamsDao;
import com.chatak.pg.acq.dao.model.PGAccount;
import com.chatak.pg.acq.dao.model.PGAdminUser;
import com.chatak.pg.acq.dao.model.PGMerchant;
import com.chatak.pg.acq.dao.model.PGParams;
import com.chatak.pg.constants.AccountTransactionCode;
import com.chatak.pg.constants.PGConstants;
import com.chatak.pg.enums.ExportType;
import com.chatak.pg.model.AccountBalanceDTO;
import com.chatak.pg.model.AccountTransactionDTO;
import com.chatak.pg.model.CurrencyDTO;
import com.chatak.pg.model.ReportsDTO;
import com.chatak.pg.user.bean.GetAccountHistoryListResponse;
import com.chatak.pg.user.bean.GetTransactionsListRequest;
import com.chatak.pg.user.bean.GetTransactionsListResponse;
import com.chatak.pg.user.bean.MerchantAccountHistory;
import com.chatak.pg.user.bean.Transaction;
import com.chatak.pg.util.CommonUtil;
import com.chatak.pg.util.Constants;

/**
 * @author guruputra
 */
@SuppressWarnings({"rawtypes", "unchecked"})
@Controller
public class DashBoardController implements URLMappingConstants {

  @Autowired
  private MessageSource messageSource;

  @Autowired
  private TransactionService transactionService;

  @Autowired
  private RestPaymentService paymentService;

  @Autowired
  AccountService accountService;

  @Autowired
  FundTransferService fundTransferService;

  @Autowired
  private PGParamsDao paramsDao;

  @Autowired
  private MerchantDao merchantDao;

  @Autowired
  MerchantProfileDao merchantProfileDao;
  
  @Autowired
  private AdminUserDao adminUserDao;

  @PostConstruct
  private void loadConfiguration() {
    List<PGParams> pgParams = paramsDao.getAllPGParams();
    ProcessorConfig.setProcessorConfiguration(pgParams);
  }

  private static Logger logger = Logger.getLogger(DashBoardController.class);

  /**
   * Method get the Dash board page
   * 
   * @param request
   * @param response
   * @param session
   * @param model
   * @return
   */
  @RequestMapping(value = CHATAK_MERCHANT_DASH_BOARD, method = RequestMethod.GET)
  public ModelAndView showDashBoard(HttpServletRequest request, HttpServletResponse response,
      HttpSession session, Map model) {

    logger.info("Entering :: DashBoarController :: showDashBoard method");
    ModelAndView modelAndView = new ModelAndView(CHATAK_MERCHANT_DASH_BOARD);
    String existingFeature = (String) session.getAttribute(Constants.EXISTING_FEATURES);

    if (!existingFeature.contains(FeatureConstants.MERCHANT_SERVICE_DASHBOARD_FEATURE_ID)) {
      session.invalidate();
      modelAndView.setViewName(INVALID_REQUEST_PAGE);
      return modelAndView;
    }
    Long merchantId = (Long) session.getAttribute(Constants.LOGIN_USER_MERCHANT_ID);
    GetMerchantDetailsResponse merchantDetailsResponse;
    GetTransactionsListRequest transaction = new GetTransactionsListRequest();
    GetTransactionsListRequest manualTransactionRequest = new GetTransactionsListRequest();
    TransactionListResponse transactionResponse = new TransactionListResponse();
    List<AccountTransactionDTO> transactionList = new ArrayList<>();
    if (merchantId != null) {

      try {
        merchantDetailsResponse = paymentService.getMerchantIdAndTerminalId(merchantId.toString());
        PGAccount account =
            accountService.getAccountDetailsByEntityId(merchantDetailsResponse.getMerchantId());
        AccountBalanceDTO accountDetails = new AccountBalanceDTO();
        accountDetails.setEntityId(account.getEntityId());
        accountDetails.setAccountNum(account.getAccountNum());
        CurrencyDTO currencyConfig = accountService.getCurrencyConfigOnCurrencyCodeAlpha(account.getCurrency());
        accountDetails.setAvailableBalanceString(
        		CommonUtil.formatAmountOnCurrency(account.getAvailableBalance().toString(),
        				currencyConfig.getCurrencyExponent(), currencyConfig.getCurrencySeparatorPosition(),
        				currencyConfig.getCurrencyMinorUnit(), currencyConfig.getCurrencyThousandsUnit()));
        accountDetails.setCurrentBalanceString(
        		CommonUtil.formatAmountOnCurrency(account.getCurrentBalance().toString(),
        				currencyConfig.getCurrencyExponent(), currencyConfig.getCurrencySeparatorPosition(),
        				currencyConfig.getCurrencyMinorUnit(), currencyConfig.getCurrencyThousandsUnit()));
        modelAndView.addObject("accountDetails", accountDetails);
        modelAndView.addObject("currencyAlpha", account.getCurrency());
        modelAndView.addObject(Constants.MERCHANT_BUSINESS_NAME,
            merchantDetailsResponse.getBusinessName());
        session.setAttribute("accountDetails", accountDetails);

        transaction.setMerchant_code(merchantDetailsResponse.getMerchantId());

        List<String> txnCodeList = new ArrayList<>(Constants.ELEVEN);
        txnCodeList.add(AccountTransactionCode.CC_FEE_CREDIT);
        txnCodeList.add(AccountTransactionCode.CC_FEE_DEBIT);
        txnCodeList.add(AccountTransactionCode.CC_AMOUNT_CREDIT);
        txnCodeList.add(AccountTransactionCode.CC_AMOUNT_DEBIT);
        txnCodeList.add(AccountTransactionCode.FT_BANK);
        txnCodeList.add(AccountTransactionCode.FT_CHECK);
        txnCodeList.add(AccountTransactionCode.ACCOUNT_CREDIT);
        txnCodeList.add(AccountTransactionCode.ACCOUNT_DEBIT);
        txnCodeList.add(AccountTransactionCode.EFT_DEBIT);
        transaction.setTransactionCodeList(txnCodeList);

        transaction.setSettlementStatus(PGConstants.PG_SETTLEMENT_PROCESSING);
        GetTransactionsListResponse processingTxnList =
            transactionService.searchAccountTransactions(transaction);
        
        //fetching entityId and entityType
        PGAdminUser pgAdminUser  = adminUserDao.findByAdminUserId(Long.valueOf(merchantDetailsResponse.getCreatedBy()));
        if(null != pgAdminUser){
        	transaction.setEntityId(pgAdminUser.getEntityId());
        	transaction.setUserType(pgAdminUser.getUserType());
        }

        transaction.setSettlementStatus(PGConstants.PG_SETTLEMENT_EXECUTED);
        GetTransactionsListResponse executedTxnList = null;

        if (transaction.getUserType().equals(Constants.PM_USER_TYPE)
        		|| transaction.getUserType().equals(Constants.ISO_USER_TYPE)) {
        	logger.info("LoginController:: fetching executed txn for entityType");
        	executedTxnList = transactionService.searchAccountTransactionsForEntityId(transaction, transaction.getEntityId(), transaction.getUserType());
        } else {
        	logger.info("LoginController:: fetching executed txn for Merchant");
        	executedTxnList = transactionService.searchAccountTransactions(transaction);
        }

        transactionList = fetchProcessingTxnList(session, modelAndView, transactionResponse, transactionList,
				processingTxnList, executedTxnList);

        manualTransactionRequest.setAcqChannel("web");
        List<String> manualTxnCodeList = new ArrayList<>(Constants.TWO);
        manualTxnCodeList.add(AccountTransactionCode.MANUAL_CREDIT);
        manualTxnCodeList.add(AccountTransactionCode.MANUAL_DEBIT);
        manualTransactionRequest.setTransactionCodeList(manualTxnCodeList);

        manualTransactionRequest.setSettlementStatus(PGConstants.PG_SETTLEMENT_EXECUTED);
        GetTransactionsListResponse manualTransactionsReportList = null;
        
        if (transaction.getUserType().equals(Constants.PM_USER_TYPE)
        		|| transaction.getUserType().equals(Constants.ISO_USER_TYPE)) {
        	logger.info("LoginController:: fetching manual txn for entityType");
        	manualTransactionsReportList = transactionService.searchAccountTransactionsForEntityId(manualTransactionRequest, transaction.getEntityId(), transaction.getUserType());
        } else {
        	logger.info("LoginController:: fetching manual txn for Merchant");
        	manualTransactionsReportList = transactionService.searchAccountTransactions(manualTransactionRequest);
        }

        PGMerchant parentMerchant = merchantProfileDao.getMerchantById(merchantId);
        List<PGMerchant> subMerchantList = merchantDao.findById(merchantId);
        List<String> merchantCodeList = new ArrayList<>();
        merchantCodeList.add(parentMerchant.getMerchantCode());
        if (CommonUtil.isListNotNullAndEmpty(subMerchantList)) {
          addMerchantCodeList(subMerchantList, merchantCodeList);
        }

        fetchManualTxnList(session, modelAndView, transactionList, manualTransactionsReportList, merchantCodeList);

        modelAndView.addObject(Constants.ACCOUNT_HISTORY_LIST,
            new ArrayList<MerchantAccountHistory>());

      } catch (Exception e) {
        logger.error("ERROR :: DashBoardController :: showDashBoard method", e);
        modelAndView.setViewName(INVALID_REQUEST_PAGE);
        return modelAndView;
      }

    }

    session.setAttribute(Constants.ERROR, null);
    session.setAttribute(Constants.SUCESS, null);
    logger.info("Exiting :: DashBoardController :: showDashBoard method");
    return modelAndView;

  }

  private void addMerchantCodeList(List<PGMerchant> subMerchantList,
      List<String> merchantCodeList) {
    for (PGMerchant subMerchant : subMerchantList) {
      merchantCodeList.add(subMerchant.getMerchantCode());
    }
  }

private void fetchManualTxnList(HttpSession session, ModelAndView modelAndView,
		List<AccountTransactionDTO> transactionList, GetTransactionsListResponse manualTransactionsReportList,
		List<String> merchantCodeList) {
	if (null != manualTransactionsReportList
	    && null != manualTransactionsReportList.getAccountTransactionList()) {
	  String merchantCodes = org.apache.commons.lang.StringUtils.join(merchantCodeList, "|");
	  List<AccountTransactionDTO> accountTransactionDTOList =
	      manualTransactionsReportList.getAccountTransactionList();
	  Iterator accountTransactionDTOListItr = accountTransactionDTOList.iterator();
	  List<AccountTransactionDTO> accountTransactionDTOs = new ArrayList<>();
	  while (accountTransactionDTOListItr.hasNext()) {
	    AccountTransactionDTO accountTransactionDTO =
	        (AccountTransactionDTO) accountTransactionDTOListItr.next();
	    if (merchantCodes.contains(accountTransactionDTO.getMerchantCode())) {
	      accountTransactionDTOs.add(accountTransactionDTO);
	    }
	  }
	  manualTransactionsReportList.setAccountTransactionList(accountTransactionDTOs);

	  if (manualTransactionsReportList.getAccountTransactionList().size() > Constants.TEN) {
	    session.setAttribute(Constants.MANUAL_TRANSACTIONS_REPORT_LIST,
	        manualTransactionsReportList.getAccountTransactionList().subList(0, Constants.TEN));
	    modelAndView.addObject(Constants.MANUAL_TRANSACTIONS_REPORT_LIST,
	        manualTransactionsReportList.getAccountTransactionList().subList(0, Constants.TEN));
	  } else {
	    session.setAttribute(Constants.MANUAL_TRANSACTIONS_REPORT_LIST,
	        manualTransactionsReportList.getAccountTransactionList());
	    modelAndView.addObject(Constants.MANUAL_TRANSACTIONS_REPORT_LIST,
	        manualTransactionsReportList.getAccountTransactionList());
	  }
	  modelAndView.addObject(Constants.MANUAL_TRANSACTIONS_LIST_SIZE,
	      manualTransactionsReportList.getAccountTransactionList().size());
	  session.setAttribute(Constants.MANUAL_TRANSACTIONS_LIST_SIZE,
	      manualTransactionsReportList.getAccountTransactionList().size());

	} else {
	  modelAndView.addObject(Constants.MANUAL_TRANSACTIONS_LIST_SIZE, 0);
	  session.setAttribute(Constants.MANUAL_TRANSACTIONS_LIST_SIZE, 0);
	  modelAndView.addObject(Constants.MANUAL_TXN_LIST, transactionList);
	  session.setAttribute(Constants.MANUAL_TXN_LIST, transactionList);
	}
}

private List<AccountTransactionDTO> fetchProcessingTxnList(HttpSession session, ModelAndView modelAndView,
		TransactionListResponse transactionResponse, List<AccountTransactionDTO> transactionList,
		GetTransactionsListResponse processingTxnList, GetTransactionsListResponse executedTxnList) {
	if (null != processingTxnList && null != processingTxnList.getAccountTransactionList()) {

	  int listSize = processingTxnList.getAccountTransactionList().size();
	  transactionResponse
	      .setAccountTxnList(listSize < Constants.TEN ? processingTxnList.getAccountTransactionList()
	          : processingTxnList.getAccountTransactionList().subList(0, Constants.TEN));
	  transactionResponse.setErrorCode(processingTxnList.getErrorCode());
	  transactionResponse.setErrorMessage(processingTxnList.getErrorMessage());
	  transactionResponse.setTotalNoOfRows(processingTxnList.getTotalResultCount());
	  transactionList = transactionResponse.getAccountTxnList() != null
	      ? transactionResponse.getAccountTxnList() : new ArrayList<AccountTransactionDTO>();
	  modelAndView.addObject(Constants.PROCESSING_LIST_SIZE, listSize);
	  session.setAttribute(Constants.PROCESSING_LIST_SIZE, listSize);
	  modelAndView.addObject(Constants.PROCESSING_TXN_LIST, transactionList);
	  session.setAttribute(Constants.PROCESSING_TXN_LIST, transactionList);
      modelAndView.addObject(Constants.MODEL_ATTRIBUTE_PORTAL_TOTAL_RECORDS_PAGE_NUM, processingTxnList.getTotalResultCount());
	} else {
	  modelAndView.addObject(Constants.PROCESSING_LIST_SIZE, 0);
	  session.setAttribute(Constants.PROCESSING_LIST_SIZE, 0);
	  modelAndView.addObject(Constants.PROCESSING_TXN_LIST, transactionList);
	  session.setAttribute(Constants.PROCESSING_TXN_LIST, transactionList);
	}
	transactionList = new ArrayList<>();
	if (null != executedTxnList && null != executedTxnList.getAccountTransactionList()) {

	  int listSize = executedTxnList.getAccountTransactionList().size();
	  transactionResponse
	      .setAccountTxnList(listSize < Constants.TEN ? executedTxnList.getAccountTransactionList()
	          : executedTxnList.getAccountTransactionList().subList(0, Constants.TEN));
	  transactionList = transactionResponse.getAccountTxnList() != null
	      ? transactionResponse.getAccountTxnList() : new ArrayList<AccountTransactionDTO>();
	  modelAndView.addObject(Constants.EXECUTED_LIST_SIZE, listSize);
	  session.setAttribute(Constants.EXECUTED_LIST_SIZE, listSize);
	  modelAndView.addObject(Constants.EXECUTED_TXN_LIST, transactionList);
	  session.setAttribute(Constants.EXECUTED_TXN_LIST, transactionList);
      modelAndView.addObject(Constants.MODEL_ATTRIBUTE_PORTAL_TOTAL_RECORDS_PAGE_NUM, executedTxnList.getTotalResultCount());
	} else {
	  modelAndView.addObject(Constants.EXECUTED_LIST_SIZE, 0);
	  session.setAttribute(Constants.EXECUTED_LIST_SIZE, 0);
	  modelAndView.addObject(Constants.EXECUTED_TXN_LIST, transactionList);
	  session.setAttribute(Constants.EXECUTED_TXN_LIST, transactionList);
	}
    modelAndView.addObject(Constants.MODEL_ATTRIBUTE_PORTAL_LIST_PAGE_NUMBER, Constants.ONE);
	return transactionList;
}

  @RequestMapping(value = CHATAK_MERCHANT_ACCOUNT_HISTORY, method = RequestMethod.POST)
  public ModelAndView getAccountHistory(HttpServletRequest request, HttpServletResponse response,
      HttpSession session, Map model, @FormParam("accountNumber") final String accountNumber) {

    logger.info("Entering :: DashBoarController :: getAccountHistory method");
    ModelAndView modelAndView = new ModelAndView(CHATAK_MERCHANT_ACCOUNT_HISTORY);

    session.setAttribute(Constants.ACCOUNT_NUMBER, accountNumber);
    Long merchantId = (Long) session.getAttribute(Constants.LOGIN_USER_MERCHANT_ID);
    List<MerchantAccountHistory> accountHistoryList = null;
    MerchantAccountHistory merchantAccountHistory = new MerchantAccountHistory();
    merchantAccountHistory.setPageIndex(Constants.ONE);
    merchantAccountHistory.setPageSize(Constants.MAX_ENTITY_DISPLAY_SIZE);
    session.setAttribute(Constants.HISTORY_MODEL, merchantAccountHistory);
    if (merchantId != null) {
      try {

        GetAccountHistoryListResponse historyListResponse =
            transactionService.getAccountHistory(accountNumber, merchantAccountHistory.getPageIndex());

        if (historyListResponse != null) {
          accountHistoryList = historyListResponse.getAccountHistoryList() != null
              ? historyListResponse.getAccountHistoryList()
              : new ArrayList<MerchantAccountHistory>();
          modelAndView.addObject(Constants.ACCOUNT_HISTORY_LIST, accountHistoryList);
          modelAndView = PaginationUtil.getPagenationModel(modelAndView,
              historyListResponse.getTotalResultCount());
        } else {
          modelAndView.addObject(Constants.ACCOUNT_HISTORY_LIST,
              new ArrayList<MerchantAccountHistory>());
        }

      } catch (Exception e) {
        logger.error("ERROR :: DashBoardController :: getAccountHistory method", e);
        modelAndView.setViewName(INVALID_REQUEST_PAGE);
        return modelAndView;
      }

    }

    session.setAttribute(Constants.ERROR, null);
    session.setAttribute(Constants.SUCESS, null);
    logger.info("Exiting :: DashBoardController :: showDashBoard method");
    return modelAndView;

  }

  @RequestMapping(value = CHATAK_MERCHANT_HISTORY_PAGINATION, method = RequestMethod.POST)
  public ModelAndView getPaginationList(final HttpSession session,
      @FormParam("pageNumber") final Integer pageNumber,
      @FormParam("totalRecords") final Integer totalRecords, Map model) {
    logger.info("Entering:: MerchantController:: getPaginationList method");

    ModelAndView modelAndView = new ModelAndView(CHATAK_MERCHANT_ACCOUNT_HISTORY);
    String accountNumber = (String) session.getAttribute(Constants.ACCOUNT_NUMBER);

    try {
      MerchantAccountHistory merchantAccountHistory = new MerchantAccountHistory();
      merchantAccountHistory.setPageIndex(pageNumber);
      merchantAccountHistory.setNoOfRecords(totalRecords);
      merchantAccountHistory.setPageSize(Constants.MAX_ENTITY_DISPLAY_SIZE);

      GetAccountHistoryListResponse historyListResponse =
          transactionService.getAccountHistory(accountNumber, pageNumber);

      if (historyListResponse != null) {
        List<MerchantAccountHistory> accountHistoryList =
            historyListResponse.getAccountHistoryList() != null
                ? historyListResponse.getAccountHistoryList()
                : new ArrayList<MerchantAccountHistory>();
        modelAndView.addObject(Constants.ACCOUNT_HISTORY_LIST, accountHistoryList);
        modelAndView = PaginationUtil.getPagenationModelSuccessive(modelAndView, pageNumber,
            historyListResponse.getTotalResultCount());
      }

    } catch (Exception e) {
      modelAndView.addObject(Constants.ERROR, messageSource
          .getMessage(Constants.CHATAK_GENERAL_ERROR, null, LocaleContextHolder.getLocale()));
      logger.error("ERROR:: DashBoardController:: getPaginationList method", e);
    }
    logger.info("Exiting:: DashBoardController:: getPaginationList method");
    return modelAndView;
  }

  @RequestMapping(value = CHATAK_MERCHANT_ACCOUNT_HISTORY_EXPORT, method = RequestMethod.POST)
  public ModelAndView downloadTransactionReport(HttpServletRequest request,
      HttpServletResponse response, @FormParam("requestFrom") final String requestFrom,
      @FormParam("downLoadPageNumber") final Integer downLoadPageNumber,
      @FormParam("downloadAllRecords") final boolean downloadAllRecords, HttpSession session,
      Map model) {

    logger.info("Entering :: TransactionController ;; downloadTransactionReport method ");

    String downloadType = request.getParameter("downloadType");
    ModelAndView modelAndView = new ModelAndView(CHATAK_MERCHANT_SEARCH_TRANSACTION_PAGE);
    GetTransactionsListRequest transaction = new GetTransactionsListRequest();

    if (request.getHeader("referer") == null) {
      session.invalidate();
      modelAndView.setViewName(INVALID_REQUEST_PAGE);
      return modelAndView;
    }
    try {
      transaction.setPageIndex(downLoadPageNumber);
      transaction.setPageSize(Constants.MAX_ENTITY_DISPLAY_SIZE);

      String accountNumber = (String) session.getAttribute(Constants.ACCOUNT_NUMBER);

      if (accountNumber != null) {
        GetAccountHistoryListResponse transactionResponse =
            transactionService.getAccountHistory(accountNumber, downLoadPageNumber);

        List<MerchantAccountHistory> list = transactionResponse.getAccountHistoryList();

        ExportDetails exportDetails = new ExportDetails();
        if (Constants.PDF_FILE_FORMAT.equalsIgnoreCase(downloadType)) {
          exportDetails.setExportType(ExportType.PDF);
        } else if (Constants.XLS_FILE_FORMAT.equalsIgnoreCase(downloadType)) {
          exportDetails.setExportType(ExportType.XLS);
          exportDetails.setExcelStartRowNumber(Integer.parseInt("5"));
      }
      setExportDetailsDataForDownloadTxnAccountHistoryReport(list, exportDetails); 
      ExportUtil.exportData(exportDetails, response, messageSource);
      }
    } catch (Exception e) {
      modelAndView.addObject(Constants.ERROR, messageSource
          .getMessage(Constants.CHATAK_GENERAL_ERROR, null, LocaleContextHolder.getLocale()));
      logger.error("ERROR:: DashBoardController:: downloadTransactionReport method", e);
    }
    logger.info("Exiting:: DashBoardController:: downloadTransactionReport method");
    modelAndView.addObject("transactions", transaction);
    return null;
  }
  
  private void setExportDetailsDataForDownloadTxnAccountHistoryReport(List<MerchantAccountHistory> transactionList,
      ExportDetails exportDetails) {
    exportDetails.setReportName("Transactions");
    exportDetails.setHeaderMessageProperty("chatak.header.transaction.messages");

    exportDetails.setHeaderList(getTxnAccountHistoryHeaderList());
    exportDetails.setFileData(getTxnAccountHistoryFileData(transactionList));
  }
  
  private List<String> getTxnAccountHistoryHeaderList() {
    String[] headerArr = {
        messageSource.getMessage("fileExportUtil.upadte.date.time", null,
            LocaleContextHolder.getLocale()),
        messageSource.getMessage("fileExportUtil.account.number", null,
            LocaleContextHolder.getLocale()),
        messageSource.getMessage("fileExportUtil.currency", null,
            LocaleContextHolder.getLocale()),
        messageSource.getMessage("fileExportUtil.available.balance", null,
            LocaleContextHolder.getLocale()),
        messageSource.getMessage("fileExportUtil.current.balance", null,
            LocaleContextHolder.getLocale()),
        messageSource.getMessage("fileExportUtil.fee.balance", null,
            LocaleContextHolder.getLocale()),
        messageSource.getMessage("fileExportUtil.payment.type", null,
            LocaleContextHolder.getLocale()),
        messageSource.getMessage("fileExportUtil.status", null,
            LocaleContextHolder.getLocale())};
    return new ArrayList<String>(Arrays.asList(headerArr));
  }

  private static List<Object[]> getTxnAccountHistoryFileData(List<MerchantAccountHistory> transactionList) {
    List<Object[]> fileData = new ArrayList<Object[]>();

    for (MerchantAccountHistory transaction : transactionList) {

      Object[] rowData = {transaction.getUpdatedDateString(),
          transaction.getAccountNum(), transaction.getCurrency(),
          transaction.getAvailableBalance() != null ? Double.parseDouble(CommonUtil.toAmount(
              (Double.valueOf(transaction.getAvailableBalance()) / Constants.MAX_PAGE_SIZE))) : "",
          transaction.getCurrentBalance() != null
              ? Double.parseDouble(CommonUtil.toAmount(
                  (Double.valueOf(transaction.getCurrentBalance()) / Constants.MAX_PAGE_SIZE))) : "",
          transaction.getFeeBalance() != null
              ? Double.parseDouble(CommonUtil.toAmount(
                  (Double.valueOf(transaction.getFeeBalance()) / Constants.MAX_PAGE_SIZE))) : "",
          transaction.getPaymentMethod(), transaction.getStatus()

      };
      fileData.add(rowData);
    }

    return fileData;
  }

  @RequestMapping(value = CHATAK_MERCHANT_TRANSACTION_EXPORT, method = RequestMethod.POST)
  public ModelAndView downloadTransactionReport(HttpServletRequest request,
      HttpServletResponse response,
      @FormParam("downLoadPageNumber") final Integer downLoadPageNumber,
      @FormParam("downloadAllRecords") final boolean downloadAllRecords,
      @FormParam("totalRecords") final Integer totalRecords,
      HttpSession session,
      Map model) {
    String downloadType=request.getParameter("downloadType");
    logger.info("Entering :: TransactionController ;; downloadTransactionReport method ");

    ModelAndView modelAndView = new ModelAndView(CHATAK_MERCHANT_SEARCH_TRANSACTION_PAGE);

    if (request.getHeader(Constants.REFERER) == null) {
      session.invalidate();
      modelAndView.setViewName(INVALID_REQUEST_PAGE);
      return modelAndView;
    }
    try {
      GetTransactionsListRequest listRequest = (GetTransactionsListRequest) session.getAttribute(Constants.TRANSACTIONS_REQ_OBJ_MODEL);
      listRequest.setPageIndex(downLoadPageNumber);
      listRequest.setPageSize(Constants.DEFAULT_PAGE_SIZE);
      if (downloadAllRecords) {
        listRequest.setPageIndex(Constants.ONE);
        listRequest.setPageSize(totalRecords);
      }
      GetTransactionsListResponse transactionResponse =
          transactionService.searchTransactions(listRequest);
      ExportDetails exportDetails = new ExportDetails();

      if (Constants.PDF_FILE_FORMAT.equalsIgnoreCase(downloadType)) {
        exportDetails.setExportType(ExportType.PDF);
      } else if (Constants.XLS_FILE_FORMAT.equalsIgnoreCase(downloadType)) {
        exportDetails.setExportType(ExportType.XLS);
        exportDetails.setExcelStartRowNumber(Integer.parseInt("5"));
    }
    setExportDetailsDataForDownloadTransactionReport(transactionResponse.getTransactionList(), exportDetails); 
    ExportUtil.exportData(exportDetails, response, messageSource);
    } catch (Exception e) {
      modelAndView.addObject(Constants.ERROR, messageSource
          .getMessage(Constants.CHATAK_GENERAL_ERROR, null, LocaleContextHolder.getLocale()));
      logger.error("ERROR:: TrasactionController:: downloadTransactionReport method", e);
    }

    logger.info("Exiting:: TransactionController:: downloadTransactionReport method");
    return null;
  }

  private void setExportDetailsDataForDownloadTransactionReport(List<Transaction> transactionList,
      ExportDetails exportDetails) {
    exportDetails.setReportName("Transactions");
    exportDetails.setHeaderMessageProperty("chatak.header.transaction.messages");

    exportDetails.setHeaderList(getTransactionHeaderList());
    exportDetails.setFileData(getTransactionFileData(transactionList));
  }

  private List<String> getTransactionHeaderList() {
    String[] headerArr = { 
        messageSource.getMessage("dash-board.label.transactiontime", null,
            LocaleContextHolder.getLocale()),
        messageSource.getMessage("merchant.common-deviceLocalTxnTime", null,
            LocaleContextHolder.getLocale()),
        messageSource.getMessage("transactionFileExportUtil.txn.id", null,
            LocaleContextHolder.getLocale()),
        messageSource.getMessage("reports.label.balancereports.merchantorsubmerchantName", null,
            LocaleContextHolder.getLocale()),
        messageSource.getMessage(
            "reports.label.balancereports.manualtransactions.merchantorsubmerchantcode", null,
            LocaleContextHolder.getLocale()),
        messageSource.getMessage("transaction-file-exportutil-terminalid", null,
            LocaleContextHolder.getLocale()),
        messageSource.getMessage("transactionFileExportUtil.MerchantOrSubMerchantType", null,
            LocaleContextHolder.getLocale()),
        messageSource.getMessage("transactionFileExportUtil.acount.number", null,
            LocaleContextHolder.getLocale()),
        messageSource.getMessage("transactionFileExportUtil.description", null,
            LocaleContextHolder.getLocale()),
        messageSource.getMessage("search-sub-merchant.label.currencycode", null,
            LocaleContextHolder.getLocale()),
        messageSource.getMessage("transactionFileExportUtil.merchant.txn.amt", null,
            LocaleContextHolder.getLocale()),
        messageSource.getMessage("transactions-search.label.merchantfee", null,
            LocaleContextHolder.getLocale()),
        messageSource.getMessage("transactions-search.label.totaltxnamt", null,
            LocaleContextHolder.getLocale()),
        messageSource.getMessage("transactionFileExportUtil.proc.txn.id", null,
            LocaleContextHolder.getLocale()),
        messageSource.getMessage("transactions-search.label.cardnumber", null,
            LocaleContextHolder.getLocale()),
        messageSource.getMessage("transactionFileExportUtil.txn.type", null,
            LocaleContextHolder.getLocale()),
        messageSource.getMessage("merchant-batch-report.label.status", null,
            LocaleContextHolder.getLocale()),
        messageSource.getMessage("login.label.username", null, LocaleContextHolder.getLocale())};
    return new ArrayList<String>(Arrays.asList(headerArr));
  }

  private static List<Object[]> getTransactionFileData(List<Transaction> transactionList) {
    List<Object[]> fileData = new ArrayList<Object[]>();

    for (Transaction transaction : transactionList) {
      getTransactionTimeZone(transaction);
      Object[] rowData = {
          transaction.getTransactionDate() != null ? transaction.getTransactionDate() : "",
              transaction.getDeviceLocalTxnTime()+transaction.getTimeZoneOffset(),
              Long.parseLong(transaction.getTransactionId()),
          transaction.getMerchantBusinessName() != null ? transaction.getMerchantBusinessName()
              : "",
              Long.parseLong(transaction.getMerchant_code()), transaction.getTerminal_id(),
          transaction.getMerchantType() != null ? transaction.getMerchantType() : "",
          transaction.getAccountNumber(),
          transaction.getTxnDescription() != null ? transaction.getTxnDescription() : "",
          transaction.getLocalCurrency(), Double.parseDouble(transaction.getTransactionAmount()),
          transaction.getFee_amount(), String.format("%.2f",transaction.getTxn_total_amount()),
          transaction.getRef_transaction_id(), Long.parseLong(transaction.getMaskCardNumber()),
          transaction.getTransaction_type() != null
              ? transaction.getTransaction_type().toUpperCase() : "",
          transaction.getStatus() != null ? transaction.getStatus() : "",
          transaction.getUserName() != null ? transaction.getUserName() : ""

      };
      fileData.add(rowData);
    }

    return fileData;
  }

	/**
	 * @param transaction
	 */
	private static void getTransactionTimeZone(Transaction transaction) {
		if (!"".equals(transaction.getTimeZoneOffset()) && null != transaction.getTimeZoneOffset()) {
			transaction.setTimeZoneOffset("(" + transaction.getTimeZoneOffset() + ")");
		}
	}

  @RequestMapping(value = DOWNLOAD_SPECIFIC_EFT_TRANSFERS_REPORT, method = RequestMethod.POST)
  public ModelAndView downloadSpecificEFTTransferReport(HttpSession session, Map model,
      HttpServletRequest request, @FormParam("downloadType") final String downloadType,
      HttpServletResponse response) {
    logger.info("Entering:: ReportsController:: downloadBankEFTReport method");
    ModelAndView modelAndView = new ModelAndView(SHOW_CHATAK_ADMIN_SPECIFIC_USER_EFT_TRANSFERS);
    List<ReportsDTO> eftTransferDownloadList =
        (List<ReportsDTO>) session.getAttribute(Constants.EFT_TRANSFER_REPORT_LIST);
    ExportDetails exportDetails = new ExportDetails();
    try {
      if (Constants.PDF_FILE_FORMAT.equalsIgnoreCase(downloadType)) {
        exportDetails.setExportType(ExportType.PDF);
      } else if (Constants.XLS_FILE_FORMAT.equalsIgnoreCase(downloadType)) {
        exportDetails.setExportType(ExportType.XLS);
        exportDetails.setExcelStartRowNumber(Integer.parseInt("5"));
    }
    setExportDetailsDataForDownloadSpecificEFTTransferReport(eftTransferDownloadList, exportDetails); 
    ExportUtil.exportData(exportDetails, response, messageSource);
    } catch (Exception e) {
      modelAndView.addObject(Constants.ERROR, messageSource
          .getMessage(Constants.CHATAK_GENERAL_ERROR, null, LocaleContextHolder.getLocale()));
      logger.error("ERROR:: ReportsController:: downloadBankEFTReport method", e);
    }
    logger.info("Exiting:: ReportsController:: downloadBankEFTReport method");
    return null;
  }
  
  private void setExportDetailsDataForDownloadSpecificEFTTransferReport(List<ReportsDTO> list,
      ExportDetails exportDetails) {
    exportDetails.setReportName("BankEFT");
    exportDetails.setHeaderMessageProperty("chatak.specific.eft.tranfers.reports");

    exportDetails.setHeaderList(getSpecificEFTTransferHeaderList());
    exportDetails.setFileData(getSpecificEFTTransferFileData(list));
  }

  private List<String> getSpecificEFTTransferHeaderList() {
    String[] headerArr = {
        messageSource.getMessage("reportFileExportUtil.date.time", null,
            LocaleContextHolder.getLocale()),
        messageSource.getMessage("reportFileExportUtil.user.name", null,
            LocaleContextHolder.getLocale()),
        messageSource.getMessage("reportFileExportUtil.company", null,
            LocaleContextHolder.getLocale()),
        messageSource.getMessage("reportFileExportUtil.account.number", null,
            LocaleContextHolder.getLocale()),
        messageSource.getMessage("reportFileExportUtil.account.type", null,
            LocaleContextHolder.getLocale()),
        messageSource.getMessage("reportFileExportUtil.transfer.id", null,
            LocaleContextHolder.getLocale()),
        messageSource.getMessage("reportFileExportUtil.transaction.description", null,
            LocaleContextHolder.getLocale()),
        messageSource.getMessage("reportFileExportUtil.currency", null,
            LocaleContextHolder.getLocale()),
        messageSource.getMessage("reportFileExportUtil.debit", null,
            LocaleContextHolder.getLocale())};
    return new ArrayList<String>(Arrays.asList(headerArr));
  }

  private static List<Object[]> getSpecificEFTTransferFileData(List<ReportsDTO> list) {
    List<Object[]> fileData = new ArrayList<Object[]>();

    for (ReportsDTO eftData : list) {

      Object[] rowData = {eftData.getDateTime() != null ? eftData.getDateTime() + "" : "",
          eftData.getUserName(), eftData.getCompanyName(),
          eftData.getAccountNumber().toString() != null ? eftData.getAccountNumber() : "",
          eftData.getAccountType(),
          eftData.getTransactionId() != null ? Long.parseLong(eftData.getTransactionId()) : "",
          eftData.getDescription(), eftData.getCurrency(),
          eftData.getAmount() != null ? Double.parseDouble(eftData.getAmount()) : ""

      };
      fileData.add(rowData);
    }

    return fileData;
  }
}
