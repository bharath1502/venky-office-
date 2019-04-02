package com.chatak.acquirer.admin.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

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

import com.chatak.acquirer.admin.constants.URLMappingConstants;
import com.chatak.acquirer.admin.controller.model.ExportDetails;
import com.chatak.acquirer.admin.controller.model.LoginResponse;
import com.chatak.acquirer.admin.model.TransactionResponse;
import com.chatak.acquirer.admin.service.RestPaymentService;
import com.chatak.acquirer.admin.service.TransactionService;
import com.chatak.acquirer.admin.util.ExportUtil;
import com.chatak.acquirer.admin.util.PaginationUtil;
import com.chatak.acquirer.admin.util.StringUtil;
import com.chatak.pg.constants.AccountTransactionCode;
import com.chatak.pg.constants.PGConstants;
import com.chatak.pg.enums.ExportType;
import com.chatak.pg.model.AccountTransactionDTO;
import com.chatak.pg.user.bean.GetTransactionsListRequest;
import com.chatak.pg.user.bean.GetTransactionsListResponse;
import com.chatak.pg.util.Constants;

@SuppressWarnings({"rawtypes", "unchecked"})
@Controller
public class ExecutedTransactionsController implements URLMappingConstants {

  private static Logger logger = Logger.getLogger(ExecutedTransactionsController.class);

  @Autowired
  MessageSource messageSource;

  @Autowired
  private TransactionService transactionService;

  @Autowired
  RestPaymentService paymentService;

  @RequestMapping(value = CHATAK_MERCHANT_EXECUTED_TRANSACTIONS, method = RequestMethod.GET)
  public ModelAndView showExecutedTransactions(HttpServletRequest request,
      HttpServletResponse response, HttpSession session, Map model) {

    logger.info("Entering :: ExecutedTransactionsController :: showExecutedTransactions method");
    ModelAndView modelAndView = new ModelAndView(CHATAK_MERCHANT_EXECUTED_TRANSACTIONS);

    GetTransactionsListRequest transaction = new GetTransactionsListRequest();
    TransactionResponse transactionResponse = new TransactionResponse();
    try {
      List<String> txnCodeList = new ArrayList<String>(Constants.ELEVEN);
      validateTxnCodeList(txnCodeList);

      transaction.setTransactionCodeList(txnCodeList);
      transaction.setPageIndex(PGConstants.ONE);
      transaction.setPageSize(Constants.MAX_ENTITIES_PORTAL_DISPLAY_SIZE);
      transaction.setSettlementStatus(PGConstants.PG_SETTLEMENT_EXECUTED);
      GetTransactionsListResponse executedTxnList = null;
      LoginResponse loginResponse = (LoginResponse) session.getAttribute(Constants.LOGIN_RESPONSE_DATA);
      if (loginResponse.getUserType().equals(Constants.PM_USER_TYPE)
    		  || loginResponse.getUserType().equals(Constants.ISO_USER_TYPE)) {
    	  executedTxnList = transactionService.searchAccountTransactionsForEntityId(transaction, loginResponse.getEntityId(), loginResponse.getUserType());
      } else {
    	  executedTxnList = transactionService.searchAccountTransactions(transaction);
      }
      if (null != executedTxnList && null != executedTxnList.getAccountTransactionList()) {

        setTransactionResponse(session, modelAndView, transactionResponse, executedTxnList);

        modelAndView =
            PaginationUtil.getPagenationModel(modelAndView, executedTxnList.getTotalResultCount());
      }
    } catch (Exception e) {
      modelAndView.setViewName(INVALID_REQUEST_PAGE);
      logger.error("Error :: ExecutedTransactionsController :: showExecutedTransactions method", e);
      return modelAndView;
    }
    session.setAttribute(Constants.ERROR, null);
    session.setAttribute(Constants.SUCESS, null);
    logger.info("Exiting :: ExecutedTransactionsController :: showExecutedTransactions method");
    return modelAndView;
  }

  private void validateTxnCodeList(List<String> txnCodeList) {
    txnCodeList.add(AccountTransactionCode.CC_AMOUNT_CREDIT);
    txnCodeList.add(AccountTransactionCode.CC_AMOUNT_DEBIT);
    txnCodeList.add(AccountTransactionCode.CC_FEE_CREDIT);
    txnCodeList.add(AccountTransactionCode.CC_FEE_DEBIT);
    txnCodeList.add(AccountTransactionCode.ACCOUNT_CREDIT);
    txnCodeList.add(AccountTransactionCode.ACCOUNT_DEBIT);
    txnCodeList.add(AccountTransactionCode.EFT_DEBIT);
    txnCodeList.add(AccountTransactionCode.FT_BANK);
    txnCodeList.add(AccountTransactionCode.FT_CHECK);
  }

  @RequestMapping(value = CHATAK_MERCHANT_EXECUTED_TRANSACTIONS_PAGINATION,
      method = RequestMethod.POST)
  public ModelAndView executedTransactionsPagination(final HttpSession session,
      @FormParam("pageNumber") final Integer pageNumber,
      @FormParam(PGConstants.TOTAL_RECORDS) final Integer totalRecords, Map model) {

    logger.info(
        "Entering :: ExecutedTransactionsController :: executedTransactionsPagination method");
    ModelAndView modelAndView = new ModelAndView(CHATAK_MERCHANT_EXECUTED_TRANSACTIONS);
    GetTransactionsListRequest transaction = new GetTransactionsListRequest();
    TransactionResponse transactionResponse = new TransactionResponse();
    LoginResponse loginResponse = (LoginResponse) session.getAttribute(Constants.LOGIN_RESPONSE_DATA);
    try {
      transaction.setPageIndex(pageNumber);
      transaction.setNoOfRecords(totalRecords);
      transaction.setPageSize(Constants.MAX_ENTITIES_PORTAL_DISPLAY_SIZE);
      List<String> txnCodeList = new ArrayList<>(Constants.ELEVEN);
      validateTxnCodeList(txnCodeList);

      transaction.setTransactionCodeList(txnCodeList);
      transaction.setSettlementStatus(PGConstants.PG_SETTLEMENT_EXECUTED);
      
      GetTransactionsListResponse executedTxnList = null;
      if (loginResponse.getUserType().equals(Constants.PM_USER_TYPE)
    		  || loginResponse.getUserType().equals(Constants.ISO_USER_TYPE)) {
    	  executedTxnList = transactionService.searchAccountTransactionsForEntityId(transaction, loginResponse.getEntityId(), loginResponse.getUserType());
      } else {
    	  executedTxnList = transactionService.searchAccountTransactions(transaction);
      }
      if (null != executedTxnList && null != executedTxnList.getAccountTransactionList()) {

        setTransactionResponse(session, modelAndView, transactionResponse, executedTxnList);
        modelAndView = PaginationUtil.getPagenationModelSuccessive(modelAndView, pageNumber,
            executedTxnList.getTotalResultCount());
      }

    } catch (Exception e) {
      modelAndView.setViewName(INVALID_REQUEST_PAGE);
      logger.error(
          "Error :: ExecutedTransactionsController :: executedTransactionsPagination method", e);
    }
    session.setAttribute(Constants.ERROR, null);
    session.setAttribute(Constants.SUCESS, null);
    logger
        .info("Exiting :: ExecutedTransactionsController :: executedTransactionsPagination method");
    return modelAndView;
  }

  private void setTransactionResponse(final HttpSession session, ModelAndView modelAndView,
		TransactionResponse transactionResponse, GetTransactionsListResponse executedTxnList) {
	List<AccountTransactionDTO> transactionList;
	transactionResponse.setAccountTxnList(executedTxnList.getAccountTransactionList());
	transactionResponse.setErrorCode(executedTxnList.getErrorCode());
	transactionResponse.setErrorMessage(executedTxnList.getErrorMessage());
	transactionResponse.setTotalNoOfRows(executedTxnList.getTotalResultCount());
	transactionList = transactionResponse.getAccountTxnList() != null
	    ? transactionResponse.getAccountTxnList() : new ArrayList<AccountTransactionDTO>();
	modelAndView.addObject(Constants.EXECUTED_TXN_LIST, transactionList);
	session.setAttribute(Constants.EXECUTED_TXN_LIST, transactionList);
  }

  @RequestMapping(value = CHATAK_MERCHANT_EXECUTED_TRANSACTIONS_REPORT, method = RequestMethod.POST)
  public ModelAndView executedTransactionsReport(HttpSession session, Map model,
      HttpServletRequest request, @FormParam("downLoadPageNumber") final Integer downLoadPageNumber,
      @FormParam("downloadType") final String downloadType,
      @FormParam(PGConstants.TOTAL_RECORDS) final Integer totalRecords,
      @FormParam("requestFrom") final String requestFrom, HttpServletResponse response,
      @FormParam("downloadAllRecords") final boolean downloadAllRecords) {
    logger.info("Entering :: ExecutedTransactionsController :: executedTransactionsReport method");
    ModelAndView modelAndView = new ModelAndView(CHATAK_MERCHANT_EXECUTED_TRANSACTIONS);

    LoginResponse loginResponse = (LoginResponse) session.getAttribute(Constants.LOGIN_RESPONSE_DATA);
    List<AccountTransactionDTO> executedTxnsList = new ArrayList<>();
    try {
      GetTransactionsListRequest transaction = null;
      TransactionResponse transactionResponse = null;
      executedTxnsList =
          (List<AccountTransactionDTO>) session.getAttribute(Constants.EXECUTED_TXN_LIST);
      if (null != executedTxnsList) {
        transaction = new GetTransactionsListRequest();
        transaction.setPageSize(Integer.parseInt("20"));
        transaction.setPageIndex(downLoadPageNumber);
        if (!StringUtil.isNullAndEmpty(requestFrom) && "dashobard".equals(requestFrom)) {
        	transaction.setPageSize(Integer.parseInt("10"));
        	modelAndView.setViewName(CHATAK_ADMIN_HOME);
        }
        transactionResponse = new TransactionResponse();
        List<String> txnCodeList = new ArrayList<>(Constants.ELEVEN);
        validateTxnCodeList(txnCodeList);
        transaction.setTransactionCodeList(txnCodeList);
        transaction.setSettlementStatus(PGConstants.PG_SETTLEMENT_EXECUTED);
        transaction.setUserType(loginResponse.getUserType());
        transaction.setEntityId(loginResponse.getEntityId());
        executedTxnsList = getTotalRecords(totalRecords, downloadAllRecords, executedTxnsList, transaction,
				transactionResponse);
        ExportDetails exportDetails = new ExportDetails();
        if (Constants.PDF_FILE_FORMAT.equalsIgnoreCase(downloadType)) {
        	exportDetails.setExportType(ExportType.PDF);
        } else if (Constants.XLS_FILE_FORMAT.equalsIgnoreCase(downloadType)) {
        	exportDetails.setExportType(ExportType.XLS);
			exportDetails.setExcelStartRowNumber(Integer.parseInt("5"));
        }
        setExportDetailsDataForDownloadExecutedTransactionsReport(executedTxnsList, exportDetails);	
	 	ExportUtil.exportData(exportDetails, response, messageSource);
      }
    } catch (Exception e) {
      modelAndView.addObject(Constants.ERROR,
          messageSource.getMessage("chatak.general.error", null, LocaleContextHolder.getLocale()));
      logger.error("ERROR :: ExecutedTransactionsController :: executedTransactionsReport method",
          e);
    }
    logger.info("Exiting :: ExecutedTransactionsController :: executedTransactionsReport method");
    return null;
  }
  
  private void setExportDetailsDataForDownloadExecutedTransactionsReport(
      List<AccountTransactionDTO> executedTxnsList, ExportDetails exportDetails) {
    exportDetails.setReportName("Executed_Transactions");
    exportDetails.setHeaderMessageProperty("home.label.completedtransactions");

    exportDetails.setHeaderList(getExecutedTransactionsHeaderList());
    exportDetails.setFileData(getExecutedTransactionsFileData(executedTxnsList));
  }

  @RequestMapping(value = CHATAK_MERCHANT_PROCESSING_TRANSACTIONS, method = RequestMethod.GET)
  public ModelAndView showProcessingTransactions(HttpServletRequest request,
      HttpServletResponse response, HttpSession session, Map model) {

    logger.info("Entering :: ExecutedTransactionsController :: showProcessingTransactions method");
    ModelAndView modelAndView = new ModelAndView(CHATAK_MERCHANT_PROCESSING_TRANSACTIONS);
    if (request.getHeader("referer") == null) {
      session.invalidate();
      modelAndView.setViewName(INVALID_REQUEST_PAGE);
      return modelAndView;
    }
    GetTransactionsListRequest transaction = new GetTransactionsListRequest();
    TransactionResponse transactionResponse = new TransactionResponse();

    try {

      List<String> txnCodeList = new ArrayList<>(Constants.ELEVEN);
      validateTxnCodeList(txnCodeList);
      txnCodeList.add(AccountTransactionCode.MANUAL_CREDIT);
      txnCodeList.add(AccountTransactionCode.MANUAL_DEBIT);
      transaction.setTransactionCodeList(txnCodeList);
      transaction.setPageIndex(PGConstants.ONE);
      transaction.setPageSize(Constants.MAX_ENTITIES_PORTAL_DISPLAY_SIZE);

      transaction.setSettlementStatus(PGConstants.PG_SETTLEMENT_PROCESSING);
      GetTransactionsListResponse processingTxnList =
          transactionService.searchAccountTransactions(transaction);

      if (null != processingTxnList && null != processingTxnList.getAccountTransactionList()) {

    	  setTransactionResponseDetails(session, modelAndView, transactionResponse, processingTxnList);
        modelAndView = PaginationUtil.getPagenationModel(modelAndView,
            processingTxnList.getTotalResultCount());

      }
    } catch (Exception e) {
      modelAndView.setViewName(INVALID_REQUEST_PAGE);
      logger.error("Error :: ExecutedTransactionsController :: executedTransactionsPagination method", e);
    }
    session.setAttribute(Constants.ERROR, null);
    session.setAttribute(Constants.SUCESS, null);
    logger.info("Exiting :: ExecutedTransactionsController :: showProcessingTransactions method");
    return modelAndView;
  }

  @RequestMapping(value = CHATAK_MERCHANT_PROCESSING_TRANSACTIONS_PAGINATION,
      method = RequestMethod.POST)
  public ModelAndView processingTransactionsPagination(final HttpSession session,
      @FormParam("pageNumber") final Integer pageNumber,
      @FormParam(PGConstants.TOTAL_RECORDS) final Integer totalRecords, Map model) {

    logger.info(
        "Entering :: ExecutedTransactionsController :: processingTransactionsPagination method");
    ModelAndView modelAndView = new ModelAndView(CHATAK_MERCHANT_PROCESSING_TRANSACTIONS);

    GetTransactionsListRequest transaction = new GetTransactionsListRequest();
    TransactionResponse transactionResponse = new TransactionResponse();

    try {
      transaction.setPageIndex(pageNumber);
      transaction.setNoOfRecords(totalRecords);
      transaction.setPageSize(Constants.MAX_ENTITIES_PORTAL_DISPLAY_SIZE);

      List<String> txnCodeList = new ArrayList<>(Constants.ELEVEN);
      validateTxnCodeList(txnCodeList);
      txnCodeList.add(AccountTransactionCode.MANUAL_CREDIT);
      txnCodeList.add(AccountTransactionCode.MANUAL_DEBIT);
      transaction.setTransactionCodeList(txnCodeList);

      transaction.setSettlementStatus(PGConstants.PG_SETTLEMENT_PROCESSING);
      GetTransactionsListResponse processingTxnList =
          transactionService.searchAccountTransactions(transaction);

      if (null != processingTxnList && null != processingTxnList.getAccountTransactionList()) {

    	  setTransactionResponseDetails(session, modelAndView, transactionResponse, processingTxnList);

        modelAndView = PaginationUtil.getPagenationModelSuccessive(modelAndView, pageNumber,
            processingTxnList.getTotalResultCount());
      }
    } catch (Exception e) {
      modelAndView.setViewName(INVALID_REQUEST_PAGE);
      logger.error(
          "Error :: executedTransactionsPagination :: processingTransactionsPagination method", e);
      return modelAndView;
    }
    session.setAttribute(Constants.ERROR, null);
    session.setAttribute(Constants.SUCESS, null);
    logger.info(
        "Exiting :: executedTransactionsPagination :: processingTransactionsPagination method");
    return modelAndView;
  }

  private void setTransactionResponseDetails(final HttpSession session, ModelAndView modelAndView,
		TransactionResponse transactionResponse, GetTransactionsListResponse processingTxnList) {
	List<AccountTransactionDTO> transactionList;
	transactionResponse.setAccountTxnList(processingTxnList.getAccountTransactionList());
	transactionResponse.setErrorCode(processingTxnList.getErrorCode());
	transactionResponse.setErrorMessage(processingTxnList.getErrorMessage());
	transactionResponse.setTotalNoOfRows(processingTxnList.getTotalResultCount());
	transactionList = transactionResponse.getAccountTxnList() != null
	    ? transactionResponse.getAccountTxnList() : new ArrayList<AccountTransactionDTO>();
	modelAndView.addObject(Constants.PROCESSING_TXN_LIST, transactionList);
	session.setAttribute(Constants.PROCESSING_TXN_LIST, transactionList);
  }

  @RequestMapping(value = CHATAK_MERCHANT_PROCESSING_TRANSACTIONS_REPORT,
      method = RequestMethod.POST)
  public ModelAndView processingTransactionsReport(HttpSession session, Map model,
      HttpServletRequest request, HttpServletResponse response,
      @FormParam("downLoadPageNumber") final Integer downLoadPageNumber,
      @FormParam("downloadType") final String downloadType,
      @FormParam(PGConstants.TOTAL_RECORDS) final Integer totalRecords,
      @FormParam("requestFrom") final String requestFrom,
      @FormParam("downloadAllRecords") final boolean downloadAllRecords) {
    logger
        .info("Entering :: ExecutedTransactionsController :: processingTransactionsReport method");
    ModelAndView modelAndView = new ModelAndView(CHATAK_MERCHANT_PROCESSING_TRANSACTIONS);

    List<AccountTransactionDTO> processingTxnList = null;
    try {
      GetTransactionsListRequest transaction = null;
      TransactionResponse transactionResponse = null;
      processingTxnList =
          (List<AccountTransactionDTO>) session.getAttribute(Constants.PROCESSING_TXN_LIST);
      if (null != processingTxnList) {
    	  transaction = new GetTransactionsListRequest();
          transaction.setPageSize(Constants.MAX_ENTITIES_PORTAL_DISPLAY_SIZE);
          transaction.setPageIndex(Constants.ONE);
          if (!StringUtil.isNullAndEmpty(requestFrom) && "dashobard".equals(requestFrom)) {
          	transaction.setPageSize(Constants.INITIAL_ENTITIES_PORTAL_DISPLAY_SIZE);
          	modelAndView.setViewName(CHATAK_ADMIN_HOME);
          }
        transactionResponse = new TransactionResponse();
        List<String> txnCodeList = new ArrayList<>(Constants.NINE);
        validateTxnCodeList(txnCodeList);
        txnCodeList.add(AccountTransactionCode.MANUAL_CREDIT);
        txnCodeList.add(AccountTransactionCode.MANUAL_DEBIT);
        transaction.setTransactionCodeList(txnCodeList);
        transaction.setSettlementStatus(PGConstants.PG_SETTLEMENT_PROCESSING);
        processingTxnList = getTotalRecords(totalRecords, downloadAllRecords, processingTxnList, transaction,
				transactionResponse);
        ExportDetails exportDetails = new ExportDetails();
        if (Constants.PDF_FILE_FORMAT.equalsIgnoreCase(downloadType)) {
        	exportDetails.setExportType(ExportType.PDF);
        } else if (Constants.XLS_FILE_FORMAT.equalsIgnoreCase(downloadType)) {
        	exportDetails.setExportType(ExportType.XLS);
			exportDetails.setExcelStartRowNumber(Integer.parseInt("5"));
        }
        setExportDetailsDataForDownloadProcessingTransactionsReport(processingTxnList, exportDetails);	
	 	ExportUtil.exportData(exportDetails, response, messageSource);
      }
    } catch (Exception e) {
      modelAndView.addObject(Constants.ERROR,
          messageSource.getMessage("chatak.general.error", null, LocaleContextHolder.getLocale()));
      logger.error("ERROR :: ExecutedTransactionsController :: processingTransactionsReport method",
          e);
    }
    logger.info("Exiting :: ExecutedTransactionsController :: processingTransactionsReport method");
    return null;
  }
  
  private void setExportDetailsDataForDownloadProcessingTransactionsReport(
      List<AccountTransactionDTO> executedTxnsList, ExportDetails exportDetails) {
    exportDetails.setReportName("Processing_Transactions");
    exportDetails.setHeaderMessageProperty("chatak.header.processing.transactions.list");

    exportDetails.setHeaderList(getProcessingTransactionsHeaderList());
    exportDetails.setFileData(getProcessingTransactionsFileData(executedTxnsList));
  }

  private List<AccountTransactionDTO> getTotalRecords(final Integer totalRecords,
      final boolean downloadAllRecords, List<AccountTransactionDTO> processingTxnList,
      GetTransactionsListRequest transaction, TransactionResponse transactionResponse) {
    if (downloadAllRecords) {
      transaction.setPageIndex(Constants.ONE);
      transaction.setPageSize(totalRecords);
    }
    GetTransactionsListResponse executedTxnList = null;
    if (transaction.getUserType().equals(Constants.PM_USER_TYPE)
    		|| transaction.getUserType().equals(Constants.ISO_USER_TYPE)) {
    	executedTxnList = transactionService.searchAccountTransactionsForEntityId(transaction, transaction.getEntityId(), transaction.getUserType());
    } else {
    	executedTxnList = transactionService.searchAccountTransactions(transaction);
    }
    if (null != executedTxnList && null != executedTxnList.getAccountTransactionList()) {
    	transactionResponse.setAccountTxnList(executedTxnList.getAccountTransactionList());
    	transactionResponse.setErrorCode(executedTxnList.getErrorCode());
    	transactionResponse.setErrorMessage(executedTxnList.getErrorMessage());
    	transactionResponse.setTotalNoOfRows(executedTxnList.getTotalResultCount());
    	processingTxnList = transactionResponse.getAccountTxnList() != null
    			? transactionResponse.getAccountTxnList() : new ArrayList<AccountTransactionDTO>();
    }
    return processingTxnList;
  }

  private List<String> getExecutedTransactionsHeaderList() {
    String[] headerArr = {
        messageSource.getMessage(PGConstants.REPORT_LABEL_TRANSACTIONS_DATEORTIME, null,
            LocaleContextHolder.getLocale()),
        messageSource.getMessage("transaction-file-exportutil-processedTime", null,
            LocaleContextHolder.getLocale()),
        messageSource.getMessage("admin.common-deviceLocalTxnTime", null,
            LocaleContextHolder.getLocale()),
        messageSource.getMessage("transaction-file-exportutil-accountTransactionId", null,
            LocaleContextHolder.getLocale()),
        messageSource.getMessage("transaction-file-exportutil-transactionId", null,
            LocaleContextHolder.getLocale()),
        messageSource.getMessage("currency-search-page.label.currencycode", null,
            LocaleContextHolder.getLocale()),
        messageSource.getMessage("transaction-file-exportutil-type", null,
            LocaleContextHolder.getLocale()),
        messageSource.getMessage("transaction-file-exportutil-description", null,
            LocaleContextHolder.getLocale()),
        messageSource.getMessage("transaction-file-exportutil-debit", null,
            LocaleContextHolder.getLocale()),
        messageSource.getMessage("transaction-file-exportutil-credit", null,
            LocaleContextHolder.getLocale()),
        messageSource.getMessage("transaction-file-exportutil-currentBalance", null,
            LocaleContextHolder.getLocale()),
        messageSource.getMessage("transaction-file-exportutil-status", null,
            LocaleContextHolder.getLocale())};
    return new ArrayList<String>(Arrays.asList(headerArr));
  }

  private static List<Object[]> getExecutedTransactionsFileData(
      List<AccountTransactionDTO> executedTxnsList) {
    List<Object[]> fileData = new ArrayList<Object[]>();

    for (AccountTransactionDTO transaction : executedTxnsList) {
      String deviceLocalTxnTimeAndOffSet =
          !StringUtil.isNullAndEmpty(transaction.getDeviceLocalTxnTime())
              && !StringUtil.isNullAndEmpty(transaction.getTimeZoneOffset())
                  ? transaction.getDeviceLocalTxnTime() + "( " + transaction.getTimeZoneOffset() + " )"
                  : "";
      Object[] rowData = {transaction.getTransactionTime(), transaction.getProcessedTime(),deviceLocalTxnTimeAndOffSet,     
          Long.parseLong(transaction.getTransactionId()), Long.parseLong(transaction.getPgTransactionId()),
          transaction.getCurrency(), transaction.getType(), transaction.getDescription().replace("\n" , " "),
          (!"".equals(transaction.getDebit())) ? Double.parseDouble(transaction.getDebit()) : transaction.getDebit(), 
          (!"".equals(transaction.getCredit())) ? Double.parseDouble(transaction.getCredit()) : transaction.getCredit(), 
          (!"".equals(transaction.getCurrentBalance())) ? Double.parseDouble(transaction.getCurrentBalance()) : transaction.getCurrentBalance(), 
          transaction.getStatus()

      };
      fileData.add(rowData);
    }

    return fileData;
  }

  private List<String> getProcessingTransactionsHeaderList() {
    String[] headerArr = {
        messageSource.getMessage(PGConstants.REPORT_LABEL_TRANSACTIONS_DATEORTIME, null,
            LocaleContextHolder.getLocale()),
        messageSource.getMessage("admin.common-deviceLocalTxnTime", null,
            LocaleContextHolder.getLocale()),
        messageSource.getMessage("transaction-file-exportutil-accountTransactionId", null,
            LocaleContextHolder.getLocale()),
        messageSource.getMessage("transaction-file-exportutil-transactionId", null,
            LocaleContextHolder.getLocale()),
        messageSource.getMessage("currency-search-page.label.currencycode", null,
            LocaleContextHolder.getLocale()),
        messageSource.getMessage("home.label.type", null, LocaleContextHolder.getLocale()),
        messageSource.getMessage("home.label.description", null, LocaleContextHolder.getLocale()),
        messageSource.getMessage("reports-file-exportutil-debit", null,
            LocaleContextHolder.getLocale()),
        messageSource.getMessage("reports-file-exportutil-credit", null,
            LocaleContextHolder.getLocale())};
    return new ArrayList<String>(Arrays.asList(headerArr));
  }

  private static List<Object[]> getProcessingTransactionsFileData(
      List<AccountTransactionDTO> executedTxnsList) {
    List<Object[]> fileData = new ArrayList<Object[]>();

    for (AccountTransactionDTO transaction : executedTxnsList) {
      String deviceLocalTxnTimeAndOffSet =
          !StringUtil.isNullAndEmpty(transaction.getDeviceLocalTxnTime())
              && !StringUtil.isNullAndEmpty(transaction.getTimeZoneOffset())
                  ? transaction.getDeviceLocalTxnTime() + "( " + transaction.getTimeZoneOffset() + " )"
                  : "";
      Object[] rowData =
         { transaction.getTransactionTime(),deviceLocalTxnTimeAndOffSet,
              Long.parseLong(transaction.getTransactionId()),
              Long.parseLong(transaction.getPgTransactionId()), transaction.getCurrency(),
              transaction.getType(), transaction.getDescription().replace("\n" , " "),
              (!"".equals(transaction.getDebit())) ? Double.parseDouble(transaction.getDebit()) : transaction.getDebit(),
              (!"".equals(transaction.getCredit())) ? Double.parseDouble(transaction.getCredit()) : transaction.getCredit()};
      fileData.add(rowData);
    }

    return fileData;
  }
}
