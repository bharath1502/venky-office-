package com.chatak.acquirer.admin.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
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
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.chatak.acquirer.admin.constants.FeatureConstants;
import com.chatak.acquirer.admin.constants.URLMappingConstants;
import com.chatak.acquirer.admin.controller.model.ExportDetails;
import com.chatak.acquirer.admin.exception.ChatakAdminException;
import com.chatak.acquirer.admin.service.MerchantAccountService;
import com.chatak.acquirer.admin.service.TransactionService;
import com.chatak.acquirer.admin.util.ExportUtil;
import com.chatak.acquirer.admin.util.JsonUtil;
import com.chatak.acquirer.admin.util.PaginationUtil;
import com.chatak.acquirer.admin.util.ReportsFileExportsUtil;
import com.chatak.pg.constants.AccountTransactionCode;
import com.chatak.pg.constants.PGConstants;
import com.chatak.pg.enums.ExportType;
import com.chatak.pg.model.AccountBalanceReportDTO;
import com.chatak.pg.model.AccountTransactionDTO;
import com.chatak.pg.model.Merchant;
import com.chatak.pg.model.ReportsDTO;
import com.chatak.pg.user.bean.GetTransactionsListRequest;
import com.chatak.pg.user.bean.GetTransactionsListResponse;
import com.chatak.pg.util.Constants;

@SuppressWarnings({"rawtypes", "unchecked"})
@Controller
public class ReportsController implements URLMappingConstants {

  private static Logger logger = Logger.getLogger(ReportsController.class);

  @Autowired
  MessageSource messageSource;

  @Autowired
  private MerchantAccountService merchantAccountService;

  @Autowired
  private TransactionService transactionService;

  @RequestMapping(value = GLOBAL_MANUAL_TRANSFER_DOWNLOAD, method = RequestMethod.POST)
  public ModelAndView downloadManualTransferReport(HttpSession session, Map model,
      HttpServletRequest request, @FormParam("downLoadPageNumber") final Integer downLoadPageNumber,
      @FormParam("totalRecords") final Integer totalRecords,
      @FormParam("downloadAllRecords") final boolean downloadAllRecords,
      HttpServletResponse response) {
    String downloadType = request.getParameter(Constants.DOWNLOAD_TYPE);
    logger.info("Entering:: ReportsController:: downloadManualTransferReport method");
    ModelAndView modelAndView = new ModelAndView(SHOW_GLOBAL_MANUAL_TRANSACTION_REPORT);
    GetTransactionsListRequest getTransactionsListRequest;
    getTransactionsListRequest =
        (GetTransactionsListRequest) session.getAttribute(Constants.TRANSACTIONS_LIST_REQUEST);
    getTransactionsListRequest.setPageIndex(downLoadPageNumber);
    getTransactionsListRequest.setNoOfRecords(totalRecords);
    getTransactionsListRequest.setPageSize(Constants.MAX_ENTITIES_PORTAL_DISPLAY_SIZE);
    if (downloadAllRecords) {
      getTransactionsListRequest.setPageIndex(Constants.ONE);
      getTransactionsListRequest.setPageSize(totalRecords);
    }
    GetTransactionsListResponse manualTransferDownloadList =
        transactionService.searchAccountTransactions(getTransactionsListRequest);
    try {
      ExportDetails exportDetails = new ExportDetails();
      if (Constants.PDF_FILE_FORMAT.equalsIgnoreCase(downloadType)) {
        exportDetails.setExportType(ExportType.PDF);
      } else if (Constants.XLS_FILE_FORMAT.equalsIgnoreCase(downloadType)) {
        exportDetails.setExportType(ExportType.XLS);
        exportDetails.setExcelStartRowNumber(Integer.parseInt("4"));
      }
      setExportDetailsDataForDownloadRoleReport(
          manualTransferDownloadList.getAccountTransactionList(), exportDetails);
      ExportUtil.exportData(exportDetails, response, messageSource);
    } catch (Exception e) {
      modelAndView.addObject(Constants.ERROR, messageSource
          .getMessage(Constants.CHATAK_GENERAL_ERROR, null, LocaleContextHolder.getLocale()));
      logger.error("ERROR:: ReportsController:: downloadManualTransferReport method", e);
    }
    logger.info("Exiting:: ReportsController:: downloadManualTransferReport method");
    modelAndView.addObject("eftTransferReportList", manualTransferDownloadList);
    return null;
  }

  private void setExportDetailsDataForDownloadRoleReport(List<AccountTransactionDTO> list,
      ExportDetails exportDetails) {
    exportDetails.setReportName("Manual_Transactions");
    exportDetails.setHeaderMessageProperty("chatak.header.manual.transactions.reports");
    exportDetails.setHeaderList(getRoleHeaderLists());
    exportDetails.setFileData(getRoleFilesData(list));
  }

  private List<String> getRoleHeaderLists() {
    String[] headerArr = {
        messageSource.getMessage("reports.label.transactions.dateortime", null,
            LocaleContextHolder.getLocale()),
        messageSource.getMessage("admin.common-deviceLocalTxnTime", null,
            LocaleContextHolder.getLocale()),
        messageSource.getMessage("show-account-transfer.label.description", null,
            LocaleContextHolder.getLocale()),
        messageSource.getMessage("accounts-manual-debit.label.merchantorsubmerchantcode", null,
            LocaleContextHolder.getLocale()),
        messageSource.getMessage("reports.label.balancereports.manualtransactions.transactionID",
            null, LocaleContextHolder.getLocale()),
        messageSource.getMessage("currency-search-page.label.currencycode", null,
            LocaleContextHolder.getLocale()),
        messageSource.getMessage("show-account-transfer.label.availablebalance", null,
            LocaleContextHolder.getLocale()),
        messageSource.getMessage("reports-file-exportutil-credit", null,
            LocaleContextHolder.getLocale()),
        messageSource.getMessage("reports-file-exportutil-debit", null,
            LocaleContextHolder.getLocale())};
    return new ArrayList<String>(Arrays.asList(headerArr));
  }

  private static List<Object[]> getRoleFilesData(List<AccountTransactionDTO> list) {
    List<Object[]> fileData = new ArrayList<Object[]>();
    for (AccountTransactionDTO eftData : list) {
      Object[] rowData = new Object[Integer.parseInt("9")];
      rowData[0] = eftData.getTransactionTime();
      rowData[1] = eftData.getDeviceLocalTxnTime() + eftData.getTimeZoneOffset();
      rowData[Integer.parseInt("2")] = eftData.getDescription();
      rowData[Integer.parseInt("3")] = eftData.getMerchantCode();
      rowData[Integer.parseInt("4")] = eftData.getTransactionId();
      rowData[Integer.parseInt("5")] = eftData.getCurrency();
      rowData[Integer.parseInt("6")] = eftData.getCurrentBalance();
      if (eftData.getTransactionCode() != null
          && "MANUAL_CREDIT".equalsIgnoreCase(eftData.getTransactionCode())) {
        rowData[Integer.parseInt("7")] = eftData.getCredit();
        rowData[Integer.parseInt("8")] = " ";
      } else if (eftData.getTransactionCode() != null
          && "MANUAL_DEBIT".equalsIgnoreCase(eftData.getTransactionCode())) {
        rowData[Integer.parseInt("7")] = " ";
        rowData[Integer.parseInt("8")] = eftData.getDebit();
      } else {
        rowData[Integer.parseInt("7")] = " ";
        rowData[Integer.parseInt("8")] = " ";
      }
      fileData.add(rowData);
    }
    return fileData;
  }

  @RequestMapping(value = GLOBAL_PENDING_TRANSACTION_PAGINATION, method = RequestMethod.POST)
  public ModelAndView getTransactionPagination(final HttpSession session,
      @FormParam("pageNumber") final Integer pageNumber,
      @FormParam("totalRecords") final Integer totalRecords, Map model) {
    logger.info("Entering:: PaymentSchemeController:: getPaginationList method");
    ModelAndView modelAndView = new ModelAndView(SHOW_GLOBAL_PENDING_TRANS_REPORT);
    try {
      GetTransactionsListRequest transactionsListRequest = null;
      transactionsListRequest =
          (GetTransactionsListRequest) session.getAttribute(Constants.TRANSACTIONS_LIST_REQUEST);
      transactionsListRequest.setPageIndex(pageNumber);
      transactionsListRequest.setNoOfRecords(totalRecords);
      modelAndView =
          transactionPaginationList(pageNumber, model, modelAndView, transactionsListRequest);
    } catch (Exception e) {
      logger.error("ERROR:: PaymentSchemeController:: getPaginationList method", e);
      modelAndView.addObject(Constants.ERROR,
          messageSource.getMessage(Constants.CHATAK_GENERAL_ERROR, null, LocaleContextHolder.getLocale()));
    }
    logger.info("Exiting:: PaymentSchemeController:: getPaginationList method");
    return modelAndView;
  }

  public ModelAndView transactionPaginationList(final Integer pageNumber, Map model,
      ModelAndView modelAndView, GetTransactionsListRequest transactionsListRequest) {
    try {
      List<ReportsDTO> pendingTransactionsReportList =
          transactionService.getAllAccountsExecutedTransactionsOnDate(transactionsListRequest);
      if (null != pendingTransactionsReportList) {
        modelAndView.addObject(Constants.TRANSACTION_DIV, Boolean.TRUE);
        modelAndView.addObject(Constants.PAGE_SIZE, transactionsListRequest.getPageSize());
        PaginationUtil.getPagenationModelSuccessive(modelAndView, pageNumber,
            transactionsListRequest.getNoOfRecords());
        
        for (ReportsDTO reportsDTO : pendingTransactionsReportList) {
          reportsDTO.setTxnJsonString(JsonUtil.convertObjectToJSON(reportsDTO.getTxnPopupDto()));
        }
        model.put(Constants.PENDING_TRANSACTIONS_REPORT_LIST, pendingTransactionsReportList);
        model.put(Constants.GET_TRANSACTIONS_LIST_REQUEST, transactionsListRequest);
        modelAndView.addObject("flag", true);
      } else {
        modelAndView.addObject(Constants.ERROR, messageSource.getMessage(
            Constants.CHATAK_ADMIN_TRANSACTIONS_ERROR_MESSAGE, null, LocaleContextHolder.getLocale()));
      }
    } catch (Exception e) {
      modelAndView.addObject(Constants.ERROR, messageSource.getMessage(Constants.CHATAK_GENERAL_ERROR,
          null, LocaleContextHolder.getLocale()));
      logger.error("ERROR:: PaymentSchemeController:: getPaginationList method", e);
    }
    return modelAndView;
  }

  @RequestMapping(value = GLOBAL_ALL_TRANSACTIONS_DOWNLOAD, method = RequestMethod.POST)
  public ModelAndView downloadAllTransReport(HttpSession session, Map model,
      HttpServletRequest request, @FormParam(Constants.DOWNLOAD_TYPE) final String downloadType,
      HttpServletResponse response) {
    logger.info("Entering:: ReportsController:: downloadAllTransReport method");
    ModelAndView modelAndView = new ModelAndView(SHOW_GLOBAL_ALL_TRANS_REPORT);
    List<ReportsDTO> allTransDownloadList =
        (List<ReportsDTO>) session.getAttribute(Constants.ALL_TRANSACTIONS_REPORT_LIST);
    try {
    	ReportsFileExportsUtil.exportDetailsDataForDownloadRoleReport(allTransDownloadList,messageSource,downloadType,response);	
    } catch (Exception e) {
      modelAndView.addObject(Constants.ERROR,
          messageSource.getMessage(Constants.CHATAK_GENERAL_ERROR, null, LocaleContextHolder.getLocale()));
      logger.error("ERROR:: ReportsController:: downloadAllTransReport method", e);
    }
    logger.info("Exiting:: ReportsController:: downloadAllTransReport method");
    modelAndView.addObject(Constants.ALL_TRANSACTIONS_REPORT_LIST, allTransDownloadList);
    return null;
  }

  @RequestMapping(value = SYSTEM_OVERVIEW_REPORT, method = RequestMethod.GET)
  public ModelAndView showSystemOverViewReport(HttpServletRequest request,
      HttpServletResponse response, Merchant merchant, BindingResult bindingResult, Map model,
      HttpSession session) {
    logger.info("Entering:: ReportsController:: showSystemOverViewReport method");
    ModelAndView modelAndView = new ModelAndView(SHOW_SYSTEM_OVERVIEW_REPORT);
    String userType = (String) session.getAttribute(Constants.LOGIN_USER_TYPE);
    String existingFeature = (String) session.getAttribute(Constants.EXISTING_FEATURES);
    if (userType.equalsIgnoreCase(Constants.ADMIN_VALUE)) {
      if (!existingFeature.contains(FeatureConstants.ADMIN_SERVICE_REPORTS_FEATURE_ID)) {
        return setInvalidRequestPage(session, modelAndView);
      }
    }
     else if (userType.equalsIgnoreCase(Constants.RESELLER)&& !existingFeature.contains(FeatureConstants.RESELLER_SERVICE_REPORTS_FEATURE_ID)) {
        return setInvalidRequestPage(session, modelAndView);
      }
   
    List<ReportsDTO> accessLogReportList = null;
    try {
      accessLogReportList = transactionService.getSystemOverViewData();
      session.setAttribute("accessLogReportList", accessLogReportList);
    } catch (Exception e) {
      modelAndView.addObject(Constants.ERROR,
          messageSource.getMessage(Constants.CHATAK_GENERAL_ERROR, null, LocaleContextHolder.getLocale()));
      logger.error("ERROR:: ReportsController:: showSystemOverViewReport method", e);
    }
    modelAndView.addObject(accessLogReportList);
    logger.info("Exiting:: ReportsController:: showSystemOverViewReport method");
    return modelAndView;
  }

  private ModelAndView setInvalidRequestPage(HttpSession session, ModelAndView modelAndView) {
	session.invalidate();
	modelAndView.setViewName(INVALID_REQUEST_PAGE);
	return modelAndView;
  }

  @RequestMapping(value = SYSTEM_OVERVIEW_REPORT_DOWNLOAD, method = RequestMethod.POST)
  public ModelAndView downloadSystemOverviewReport(HttpSession session, Map model,
      HttpServletRequest request,
      @FormParam("downLoadPageNumber") final Integer downLoadPageNumber,
      @FormParam("totalRecords") final Integer totalRecords,
      @FormParam("downloadAllRecords") final boolean downloadAllRecords,
      HttpServletResponse response) {
    String downloadType=request.getParameter(Constants.DOWNLOAD_TYPE);
    logger.info("Entering:: ReportsController:: downloadSystemOverviewReport method");
    ModelAndView modelAndView = new ModelAndView(SHOW_GLOBAL_ACCESS_LOG_REPORT);
    
    List<ReportsDTO> overViewDownloadList =
        (List<ReportsDTO>) session.getAttribute("accessLogReportList");
    ExportDetails exportDetails = new ExportDetails();
    try {
      if (Constants.PDF_FILE_FORMAT.equalsIgnoreCase(downloadType)) {
    	  exportDetails.setExportType(ExportType.PDF);
        
      } else if (Constants.XLS_FILE_FORMAT.equalsIgnoreCase(downloadType)) {
    	  exportDetails.setExportType(ExportType.XLS);
  		exportDetails.setExcelStartRowNumber(Integer.parseInt("4"));
        
      }
      setExportDetailsDataForDownloadRevenueGeneratedReport(overViewDownloadList, exportDetails);	
	 	ExportUtil.exportData(exportDetails, response, messageSource);
    } catch (Exception e) {
      modelAndView.addObject(Constants.ERROR,
          messageSource.getMessage(Constants.CHATAK_GENERAL_ERROR, null, LocaleContextHolder.getLocale()));
      logger.error("ERROR:: ReportsController:: downloadSystemOverviewReport method", e);
    }
    logger.info("Exiting:: ReportsController:: downloadSystemOverviewReport method");
    modelAndView.addObject("accessLogs", overViewDownloadList);
    return null;
  }
  private void  setExportDetailsDataForDownloadRevenueGeneratedReport(
	      List<ReportsDTO> overViewDownloadList, ExportDetails exportDetails) {
	  Date date = new Date();
	    String dateString = new SimpleDateFormat(Constants.EXPORT_FILE_NAME_DATE_FORMAT).format(date);
	    String filename = "Revenue" + dateString + ".xls";
	    exportDetails.setReportName(filename);
	    exportDetails.setHeaderMessageProperty("chatak.system.overview.reports.header.message");

	    exportDetails.setHeaderList(getRoleHeaderList());
	    exportDetails.setFileData(getRoleFileData(overViewDownloadList));
	  }
  private List<String> getRoleHeaderList() {
	    String[] headerArr = {
	        messageSource.getMessage("reports-file-exportutil-accountType", null,
	            LocaleContextHolder.getLocale()),
	        messageSource.getMessage("reports-file-exportutil-activeAndBlockedAccounts", null,
		            LocaleContextHolder.getLocale()),
	        messageSource.getMessage("currency-search-page.label.currencycode", null,
		            LocaleContextHolder.getLocale()),
	        messageSource.getMessage("reports-file-exportutil-totalBalances", null,
		            LocaleContextHolder.getLocale())};
	    return new ArrayList<String>(Arrays.asList(headerArr));
	  }
  
  private static List<Object[]> getRoleFileData(List<ReportsDTO> list) {
	    List<Object[]> fileData = new ArrayList<Object[]>();

	    for (ReportsDTO reportDTO : list) {
	    	Object[] rowData = new Object[Integer.parseInt("4")];
			  rowData[0] ="Merchant";
			  rowData[1] = reportDTO.getMerchantAccountCount().toString();
			  rowData[Integer.parseInt("2")]= reportDTO.getCurrency();
			  rowData[Integer.parseInt("3")]=(reportDTO.getMerchantAccountBalance() != null)
			            ? Double.parseDouble(reportDTO.getMerchantAccountBalance()) : 0d;
	      fileData.add(rowData);
	    }
	    for (ReportsDTO reportDTO : list) {
	    	Object[] rowData = new Object[Integer.parseInt("4")];
			  rowData[0] ="Sub Merchant";
			  rowData[1] = reportDTO.getSubMerchantAccountCount().toString();
			  rowData[Integer.parseInt("2")]= reportDTO.getCurrency();
			  rowData[Integer.parseInt("3")]=(reportDTO.getSubMerchantAccountBalance() != null)
			            ? Double.parseDouble(reportDTO.getSubMerchantAccountBalance()) : 0d;
	      fileData.add(rowData);
	    }
	    for (ReportsDTO reportDTO : list) {
	    	Object[] rowData = new Object[Integer.parseInt("4")];
			  rowData[0] ="Revenue Account";
			  rowData[1] = reportDTO.getChatakAccountCount().toString();
			  rowData[Integer.parseInt("2")]= reportDTO.getCurrency();
			  rowData[Integer.parseInt("3")]=(reportDTO.getChatakAccountBalance() != null)
			            ? Double.parseDouble(reportDTO.getChatakAccountBalance()) : 0d;
	      fileData.add(rowData);
	    }
	    

	    return fileData;
	  }

  /**
   * Method used for Pagination Util
   * 
   * @param session
   * @param pageNumber
   * @param model
   * @return
   */
  @RequestMapping(value = MANNUAL_TRXN_PAGINATION, method = RequestMethod.POST)
  public ModelAndView getManualTransactionPagination(final HttpSession session,
      @FormParam("pageNumber") final Integer pageNumber,
      @FormParam("totalRecords") final Integer totalRecords, Map model) {
    logger.info("Entering:: ReportsController:: showGlobalManualTransactionReport method");
    ModelAndView modelAndView = new ModelAndView(SHOW_GLOBAL_MANUAL_TRANSACTION_REPORT);
    GetTransactionsListRequest transactionsListRequest;
    String userType = (String) session.getAttribute(Constants.LOGIN_USER_TYPE);
    String existingFeature = (String) session.getAttribute(Constants.EXISTING_FEATURES);
    transactionsListRequest =
        (GetTransactionsListRequest) session.getAttribute(Constants.TRANSACTIONS_LIST_REQUEST);
    transactionsListRequest.setPageIndex(pageNumber);
    transactionsListRequest.setNoOfRecords(totalRecords);
    transactionsListRequest.setPageSize(Constants.MAX_ENTITIES_PORTAL_DISPLAY_SIZE);
    if (userType.equalsIgnoreCase(Constants.ADMIN_VALUE)) {
      if (!existingFeature.contains(FeatureConstants.ADMIN_SERVICE_REPORTS_FEATURE_ID)) {
        return setInvalidRequestPage(session, modelAndView);
      }
    } else if (userType.equalsIgnoreCase(Constants.RESELLER)&& !existingFeature.contains(FeatureConstants.RESELLER_SERVICE_REPORTS_FEATURE_ID)) {
        return setInvalidRequestPage(session, modelAndView);
      }
    
    modelAndView.addObject(Constants.ERROR, null);
    session.setAttribute(Constants.ERROR, null);
    try {
      GetTransactionsListResponse manualTransactionsReportList =
          transactionService.searchAccountTransactions(transactionsListRequest);

      if (null != manualTransactionsReportList) {
        session.setAttribute(Constants.MANUAL_TRANSACTIONS_REPORT_LIST,
            manualTransactionsReportList.getAccountTransactionList());
        modelAndView.addObject(Constants.TRANSACTION_DIV, Boolean.TRUE);
        modelAndView.addObject("totalRecords", totalRecords);
        modelAndView =
            PaginationUtil.getPagenationModelSuccessive(modelAndView, pageNumber, totalRecords);
        modelAndView.addObject(Constants.MANUAL_TRANSACTIONS_REPORT_LIST,
            manualTransactionsReportList.getAccountTransactionList());
        model.put(Constants.GET_TRANSACTIONS_LIST_REQUEST, transactionsListRequest);
      } else {
        modelAndView.addObject(Constants.ERROR, messageSource.getMessage(
            Constants.CHATAK_ADMIN_TRANSACTIONS_ERROR_MESSAGE, null, LocaleContextHolder.getLocale()));
      }
    } catch (Exception e) {
      modelAndView.addObject(Constants.ERROR,
          messageSource.getMessage(Constants.CHATAK_GENERAL_ERROR, null, LocaleContextHolder.getLocale()));
      logger.error("ERROR:: ReportsController:: showGlobalManualTransactionReport method", e);
    }
    logger.info("Exiting:: ReportsController:: showGlobalManualTransactionReport method");
    return modelAndView;
  }

  @RequestMapping(value = GLOBAL_ALL_TRANSACTION_REPORT, method = RequestMethod.GET)
  public ModelAndView showGlobalAllTransactions(HttpServletRequest request,
      HttpServletResponse response, Merchant merchant, BindingResult bindingResult, Map model,
      @FormParam("fromDate") final String fromDate,
      HttpSession session) {
    String toDate=request.getParameter("toDate");
    logger.info("Entering:: ReportsController:: showGlobalAllTransactions method");
    ModelAndView modelAndView = new ModelAndView(SHOW_GLOBAL_ALL_TRANS_REPORT);
    GetTransactionsListRequest transactionsListRequest = new GetTransactionsListRequest();
    String userType = (String) session.getAttribute(Constants.LOGIN_USER_TYPE);
    String existingFeature = (String) session.getAttribute(Constants.EXISTING_FEATURES);
    if (userType.equalsIgnoreCase(Constants.ADMIN_VALUE)) {
      if (!existingFeature.contains(FeatureConstants.ADMIN_SERVICE_REPORTS_FEATURE_ID)) {
        return setInvalidRequestPage(session, modelAndView);
      }
    } else if (userType.equalsIgnoreCase(Constants.RESELLER)&& !existingFeature.contains(FeatureConstants.RESELLER_SERVICE_REPORTS_FEATURE_ID)) {
      
        return setInvalidRequestPage(session, modelAndView);
      }
    
    modelAndView.addObject(Constants.ERROR, null);
    session.setAttribute(Constants.ERROR, null);
    session.setAttribute(Constants.MERCHANTS_MODEL, merchant);
    merchant.setPageSize(Constants.MAX_ENTITIES_PORTAL_DISPLAY_SIZE);
    merchant.setPageIndex(Constants.ONE);
    transactionsListRequest.setFrom_date(fromDate);
    transactionsListRequest.setTo_date(toDate);
    try {
      List<ReportsDTO> allTransactionsReportList =
          transactionService.getAllTransactionsOnDate(transactionsListRequest);
      if (null != allTransactionsReportList) {
        session.setAttribute(Constants.ALL_TRANSACTIONS_REPORT_LIST, allTransactionsReportList);
        modelAndView.addObject(Constants.TRANSACTION_DIV, Boolean.TRUE);

        for (ReportsDTO reportsDTO : allTransactionsReportList) {
          reportsDTO.setTxnJsonString(JsonUtil.convertObjectToJSON(reportsDTO.getTxnPopupDto()));
        }

        modelAndView.addObject(Constants.ALL_TRANSACTIONS_REPORT_LIST, allTransactionsReportList);
      } else {
        modelAndView.addObject(Constants.ERROR, messageSource.getMessage(
            Constants.CHATAK_ADMIN_TRANSACTIONS_ERROR_MESSAGE, null, LocaleContextHolder.getLocale()));
      }
    } catch (Exception e) {
      modelAndView.addObject(Constants.ERROR,
          messageSource.getMessage(Constants.CHATAK_GENERAL_ERROR, null, LocaleContextHolder.getLocale()));
      logger.error("ERROR:: ReportsController:: showGlobalAllTransactions method", e);
    }
    modelAndView.addObject("merchant", new Merchant());
    logger.info("Exiting:: ReportsController:: showGlobalAllTransactions method");
    return modelAndView;
  }

  @RequestMapping(value = GLOBAL_SYSTEM_BALANCES_REPORT, method = RequestMethod.GET)
  public ModelAndView showGlobalSystemBalancesreport(HttpServletRequest request,
      HttpServletResponse response, Merchant merchant, BindingResult bindingResult, Map model,
      HttpSession session) {
    logger.info("Entering:: ReportsController:: showGlobalSystemBalancesreport method");
    ModelAndView modelAndView = new ModelAndView(GLOBAL_BALANCE_REPORTS_SHOW);
    List<AccountBalanceReportDTO> balanceReportList = null;
    String userType = (String) session.getAttribute(Constants.LOGIN_USER_TYPE);
    String existingFeature = (String) session.getAttribute(Constants.EXISTING_FEATURES);
    if (userType.equalsIgnoreCase(Constants.ADMIN_VALUE)) {
      if (!existingFeature.contains(FeatureConstants.ADMIN_SERVICE_REPORTS_FEATURE_ID)) {
        return setInvalidRequestPage(session, modelAndView);
      }
    } else if (userType.equalsIgnoreCase(Constants.RESELLER)&& !existingFeature.contains(FeatureConstants.RESELLER_SERVICE_REPORTS_FEATURE_ID)) {
     
        return setInvalidRequestPage(session, modelAndView);
      }
    
    merchant.setPageIndex(Constants.ONE);
    merchant.setPageSize(Constants.MAX_ENTITIES_PORTAL_DISPLAY_SIZE);
    session.setAttribute(Constants.SYSTEM_BALANCE_REPORT, merchant);

    try {
      balanceReportList = merchantAccountService.getAllAccountsBalanceReport(merchant);
      if (null != balanceReportList) {
        modelAndView.addObject(Constants.PAGE_SIZE, merchant.getPageSize());
        modelAndView = PaginationUtil.getPagenationModel(modelAndView, merchant.getNoOfRecords());
      }
      modelAndView.addObject(Constants.SYSTEM_BALANCE_REPORT, balanceReportList);

    } catch (ChatakAdminException e) {
      modelAndView.addObject(Constants.ERROR, e.getMessage());
      logger.error("ERROR:: ReportsController:: showGlobalSystemBalancesreport method", e);
    } catch (Exception e) {
      modelAndView.addObject(Constants.ERROR,
          messageSource.getMessage(Constants.CHATAK_GENERAL_ERROR, null, LocaleContextHolder.getLocale()));
      logger.error("ERROR:: ReportsController:: showGlobalSystemBalancesreport method", e);
    }
    model.put(Constants.BALANCE_LIST, balanceReportList);
    modelAndView.addObject(Constants.BALANCE_LIST, balanceReportList);
    logger.info("Exiting:: ReportsController:: showGlobalSystemBalancesreport method");
    return modelAndView;
  }

  @RequestMapping(value = GLOBAL_BALANCE_PAGINATION_REPORTS, method = RequestMethod.POST)
  public ModelAndView showGlobalSystemBalancesReportPagination(final HttpSession session,
      @FormParam("pageNumber") final Integer pageNumber,
      @FormParam("totalRecords") final Integer totalRecords, Map model) {
    logger.info("Entering:: ReportsController:: showGlobalSystemBalancesreport method");
    ModelAndView modelAndView = new ModelAndView(GLOBAL_BALANCE_REPORTS_SHOW);
    try {
      Merchant merchant = (Merchant) session.getAttribute(Constants.SYSTEM_BALANCE_REPORT);
      merchant.setPageIndex(pageNumber);
      merchant.setNoOfRecords(totalRecords);
      merchant.setPageSize(Constants.MAX_ENTITIES_PORTAL_DISPLAY_SIZE);
      modelAndView = balanceReport(pageNumber, model, modelAndView, merchant);
    } catch (Exception e) {
      modelAndView.addObject(Constants.ERROR,
          messageSource.getMessage(Constants.CHATAK_GENERAL_ERROR, null, LocaleContextHolder.getLocale()));
      logger.error("ERROR:: ReportsController:: showGlobalSystemBalancesReportPagination method",
          e);
    }
    logger.info("Exiting:: ReportsController:: showGlobalSystemBalancesReportPagination method");
    return modelAndView;
  }

  public ModelAndView balanceReport(final Integer pageNumber, Map model, ModelAndView modelAndView,
      Merchant merchant) {
    List<AccountBalanceReportDTO> balanceReportList;
    try {
      balanceReportList = merchantAccountService.getAllAccountsBalanceReportPagination(merchant);
      if (balanceReportList != null) {
        modelAndView.addObject(Constants.PAGE_SIZE, merchant.getPageSize());
        PaginationUtil.getPagenationModelSuccessive(modelAndView, pageNumber,
            merchant.getNoOfRecords());
        model.put(Constants.BALANCE_LIST, balanceReportList);
      }
      modelAndView.addObject(Constants.SYSTEM_BALANCE_REPORT, balanceReportList);
    } catch (ChatakAdminException e) {
      modelAndView.addObject(Constants.ERROR, e.getMessage());
      logger.error("ERROR:: ReportsController:: showGlobalSystemBalancesReportPagination method",
          e);
    }
    return modelAndView;
  }

  @RequestMapping(value = GLOBAL_BALANCE_REPORT_DOWNLOAD, method = RequestMethod.POST)
  public ModelAndView downloadGlobalBalanceReport(HttpSession session, Map model,
      HttpServletRequest request,
      @FormParam("downLoadPageNumber") final Integer downLoadPageNumber,
      @FormParam("totalRecords") final Integer totalRecords,
      @FormParam("downloadAllRecords") final boolean downloadAllRecords,
      HttpServletResponse response) {
    String downloadType=request.getParameter(Constants.DOWNLOAD_TYPE);
    logger.info("Entering:: ReportsController:: downloadGlobalBalanceReport method");
    ModelAndView modelAndView = new ModelAndView(GLOBAL_BALANCE_REPORTS_SHOW);
    Merchant merchant = null;
    String userType = (String) session.getAttribute(Constants.LOGIN_USER_TYPE);
    String existingFeature = (String) session.getAttribute(Constants.EXISTING_FEATURES);
    if (userType.equalsIgnoreCase(Constants.ADMIN_VALUE)) {
      if (!existingFeature.contains(FeatureConstants.ADMIN_SERVICE_REPORTS_FEATURE_ID)) {
        return setInvalidRequestPage(session, modelAndView);
      }
    } else if (userType.equalsIgnoreCase(Constants.RESELLER)&& !existingFeature.contains(FeatureConstants.RESELLER_SERVICE_REPORTS_FEATURE_ID)) {
      
        return setInvalidRequestPage(session, modelAndView);
      }
    
    try {
      merchant = (Merchant) session.getAttribute(Constants.SYSTEM_BALANCE_REPORT);
      merchant.setPageIndex(downLoadPageNumber);
      merchant.setPageSize(Constants.MAX_ENTITIES_PORTAL_DISPLAY_SIZE);
      if (downloadAllRecords) {
        merchant.setPageIndex(Constants.ONE);
        merchant.setPageSize(totalRecords);
      }
      List<AccountBalanceReportDTO> balanceReportList =
          merchantAccountService.getAllAccountsBalanceReport(merchant);
      ExportDetails exportDetails = new ExportDetails();
      if (Constants.PDF_FILE_FORMAT.equalsIgnoreCase(downloadType)) {
    	  exportDetails.setExportType(ExportType.PDF);
      } else if (Constants.XLS_FILE_FORMAT.equalsIgnoreCase(downloadType)) {
    	  exportDetails.setExportType(ExportType.XLS);
		  exportDetails.setExcelStartRowNumber(Integer.parseInt("5"));
      }
      setExportDetailsDataForDownloadGlobalBalanceReport(balanceReportList, exportDetails);	
	  ExportUtil.exportData(exportDetails, response, messageSource);
    } catch (Exception e) {
      modelAndView.addObject(Constants.ERROR,
          messageSource.getMessage(Constants.CHATAK_GENERAL_ERROR, null, LocaleContextHolder.getLocale()));
      logger.error("ERROR:: ReportsController:: downloadGlobalBalanceReport method", e);
    }
    logger.info("Exiting:: ReportsController:: downloadGlobalBalanceReport method");
    return null;
  }
  
  private void setExportDetailsDataForDownloadGlobalBalanceReport(
      List<AccountBalanceReportDTO> balanceReportList, ExportDetails exportDetails) {
    exportDetails.setReportName("Balance_");
    exportDetails.setHeaderMessageProperty("chatak.header.global.bal.reports.messages");

    exportDetails.setHeaderList(getGlobalBalanceHeaderList());
    exportDetails.setFileData(getGlobalBalanceFileData(balanceReportList));
  }

  @RequestMapping(value = SHOW_GLOBAL_MANUAL_TRANS_REPORT_DATES, method = RequestMethod.GET)
  public ModelAndView showGlobalManualTransReportsDates(HttpServletRequest request,
      HttpServletResponse response, GetTransactionsListRequest getTransactionsListRequest,
      BindingResult bindingResult, Map model, HttpSession session) {
    logger.info("Entering:: ReportsController:: showGlobalManualTransReportsDates method");
    ModelAndView modelAndView = new ModelAndView(SHOW_GLOBAL_MANUAL_TRANSACTION_REPORT);
    String userType = (String) session.getAttribute(Constants.LOGIN_USER_TYPE);
    String existingFeature = (String) session.getAttribute(Constants.EXISTING_FEATURES);
    if (userType.equalsIgnoreCase(Constants.ADMIN_VALUE)) {
      if (!existingFeature.contains(FeatureConstants.ADMIN_SERVICE_REPORTS_FEATURE_ID)) {
        return setInvalidRequestPage(session, modelAndView);
      }
    } else if (userType.equalsIgnoreCase(Constants.RESELLER)&& !existingFeature.contains(FeatureConstants.RESELLER_SERVICE_REPORTS_FEATURE_ID)) {
      
        return setInvalidRequestPage(session, modelAndView);
      
    }
    List<GetTransactionsListRequest> executedTransactionsReportList =
        new ArrayList<>();
    session.setAttribute("executedTransactionsReportList", executedTransactionsReportList);
    modelAndView.addObject(Constants.TRANSACTION_DIV, Boolean.FALSE);
    modelAndView.addObject(executedTransactionsReportList);
    modelAndView.addObject("merchant", new GetTransactionsListRequest());
    logger.info("Exiting:: ReportsController:: showGlobalManualTransReportsDates method");
    return modelAndView;
  }

  @RequestMapping(value = GLOBAL_MANUAL_TRANSACTION_REPORT, method = RequestMethod.POST)
  public ModelAndView showGlobalManualTransactionReport(HttpServletRequest request,
      HttpServletResponse response, GetTransactionsListRequest getTransactionsListRequest,
      BindingResult bindingResult, Map model, HttpSession session) {
    logger.info("Entering:: ReportsController:: showGlobalManualTransactionReport method");
    ModelAndView modelAndView = new ModelAndView(SHOW_GLOBAL_MANUAL_TRANSACTION_REPORT);
    GetTransactionsListRequest transactionsListRequest = new GetTransactionsListRequest();
    String userType = (String) session.getAttribute(Constants.LOGIN_USER_TYPE);
    String existingFeature = (String) session.getAttribute(Constants.EXISTING_FEATURES);
    if (userType.equalsIgnoreCase(Constants.ADMIN_VALUE)) {
      if (!existingFeature.contains(FeatureConstants.ADMIN_SERVICE_REPORTS_FEATURE_ID)) {
        return setInvalidRequestPage(session, modelAndView);
      }
    } else if (userType.equalsIgnoreCase(Constants.RESELLER)&& !existingFeature.contains(FeatureConstants.RESELLER_SERVICE_REPORTS_FEATURE_ID)) {
      
        return setInvalidRequestPage(session, modelAndView);
      
    }
    modelAndView.addObject(Constants.ERROR, null);
    session.setAttribute(Constants.ERROR, null);
    getTransactionsListRequest.setPageSize(Constants.MAX_ENTITIES_PORTAL_DISPLAY_SIZE);
    getTransactionsListRequest.setPageIndex(Constants.ONE);
    transactionsListRequest.setAcqChannel(Constants.ACQUIRING_CHANNEL);

    List<String> txnCodeList = new ArrayList<>(Constants.ELEVEN);
    txnCodeList.add(AccountTransactionCode.MANUAL_CREDIT);
    txnCodeList.add(AccountTransactionCode.MANUAL_DEBIT);

    transactionsListRequest.setTransactionCodeList(txnCodeList);
    transactionsListRequest.setSettlementStatus(PGConstants.PG_SETTLEMENT_EXECUTED);
    if (null != getTransactionsListRequest.getFrom_date()
        && null != getTransactionsListRequest.getTo_date()) {
      transactionsListRequest.setFrom_date(getTransactionsListRequest.getFrom_date());
      transactionsListRequest.setTo_date(getTransactionsListRequest.getTo_date());
    }
    session.setAttribute(Constants.TRANSACTIONS_LIST_REQUEST, transactionsListRequest);
    try {
      GetTransactionsListResponse manualTransactionsReportList =
          transactionService.searchAccountTransactions(transactionsListRequest);
      if (null != manualTransactionsReportList) {
        int totalRecords = manualTransactionsReportList.getAccountTransactionList().size();
        session.setAttribute(Constants.MANUAL_TRANSACTIONS_REPORT_LIST,
            manualTransactionsReportList.getAccountTransactionList());
        modelAndView.addObject(Constants.TRANSACTION_DIV, Boolean.TRUE);
        modelAndView.addObject("totalRecords", totalRecords);
        PaginationUtil.getPagenationModel(modelAndView, totalRecords);
        modelAndView.addObject(Constants.MANUAL_TRANSACTIONS_REPORT_LIST,
            manualTransactionsReportList.getAccountTransactionList());
        model.put(Constants.GET_TRANSACTIONS_LIST_REQUEST, getTransactionsListRequest);
      } else {
        modelAndView.addObject(Constants.ERROR, messageSource.getMessage(
            Constants.CHATAK_ADMIN_TRANSACTIONS_ERROR_MESSAGE, null, LocaleContextHolder.getLocale()));
      }
    } catch (Exception e) {
      modelAndView.addObject(Constants.ERROR,
          messageSource.getMessage(Constants.CHATAK_GENERAL_ERROR, null, LocaleContextHolder.getLocale()));
      logger.error("ERROR:: ReportsController:: showGlobalManualTransactionReport method", e);
    }
    logger.info("Exiting:: ReportsController:: showGlobalManualTransactionReport method");
    return modelAndView;
  }

  @RequestMapping(value = GLOBAL_PENDING_TRANS_REPORT_DATES, method = RequestMethod.GET)
  public ModelAndView showAllPendingTransDate(HttpServletRequest request,
      HttpServletResponse response, GetTransactionsListRequest getTransactionsListRequest,
      Merchant merchant, BindingResult bindingResult, Map model, HttpSession session) {
    logger.info("Entering:: ReportsController:: showAllPendingTransDate method");
    ModelAndView modelAndView = new ModelAndView(SHOW_GLOBAL_PENDING_TRANS_REPORT);
    List<ReportsDTO> pendingTransactionsReportList = new ArrayList<>();
    session.setAttribute(Constants.PENDING_TRANSACTIONS_REPORT_LIST, pendingTransactionsReportList);
    modelAndView.addObject(Constants.TRANSACTION_DIV, Boolean.FALSE);
    modelAndView.addObject(pendingTransactionsReportList);
    modelAndView.addObject(Constants.GET_TRANSACTIONS_LIST_REQUEST, getTransactionsListRequest);
    modelAndView.addObject("flag", false);
    logger.info("Exiting:: ReportsController:: showAllPendingTransDate method");
    return modelAndView;
  }

  @RequestMapping(value = GLOBAL_PENDING_TRANS_DOWNLOAD, method = RequestMethod.POST)
  public ModelAndView downloadPendingTransReport(HttpSession session, Map model,
      HttpServletRequest request,
      @FormParam("downLoadPageNumber") final Integer downLoadPageNumber,
      @FormParam("totalRecords") final Integer totalRecords,
      @FormParam("downloadAllRecords") final boolean downloadAllRecords,
      HttpServletResponse response) {
    String downloadType=request.getParameter(Constants.DOWNLOAD_TYPE);
    logger.info("Entering:: ReportsController:: downloadPendingTransReport method");
    ModelAndView modelAndView = new ModelAndView(SHOW_GLOBAL_PENDING_TRANS_REPORT);
    GetTransactionsListRequest transactionsListRequest =
        (GetTransactionsListRequest) session.getAttribute(Constants.TRANSACTIONS_LIST_REQUEST);
    List<ReportsDTO> pendingTransDownloadList =
        (List<ReportsDTO>) session.getAttribute(Constants.PENDING_TRANSACTIONS_REPORT_LIST);
    transactionsListRequest.setPageIndex(downLoadPageNumber);
    int pageSize = transactionsListRequest.getPageSize();
    try {
      if (downloadAllRecords) {
        transactionsListRequest.setPageIndex(Constants.ONE);
        transactionsListRequest.setPageSize(totalRecords);
      }
      pendingTransDownloadList =
          transactionService.getAllAccountsExecutedTransactionsOnDate(transactionsListRequest);
      ReportsFileExportsUtil.setExportDetailsDataForPendingRoleReport(pendingTransDownloadList,messageSource,downloadType,response);
      transactionsListRequest.setPageSize(pageSize);
    } catch (Exception e) {
      modelAndView.addObject(Constants.ERROR,
          messageSource.getMessage(Constants.CHATAK_GENERAL_ERROR, null, LocaleContextHolder.getLocale()));
      logger.error("ERROR:: ReportsController:: downloadPendingTransReport method", e);
    }
    logger.info("Exiting:: ReportsController:: downloadPendingTransReport method");
    modelAndView.addObject(Constants.PENDING_TRANSACTIONS_REPORT_LIST, pendingTransDownloadList);
    return null;
  }
  
  private List<String> getGlobalBalanceHeaderList() {
    String[] headerArr = {
        messageSource.getMessage("reports-file-exportutil-userName", null,
            LocaleContextHolder.getLocale()),
        messageSource.getMessage("reports-file-exportutil-companyName", null,
            LocaleContextHolder.getLocale()),
        messageSource.getMessage("reports-file-exportutil-accountCreationDate", null,
            LocaleContextHolder.getLocale()),
        messageSource.getMessage("reports-file-exportutil-accountNumber", null,
            LocaleContextHolder.getLocale()),
        messageSource.getMessage("reports-file-exportutil-accountType", null,
            LocaleContextHolder.getLocale()),
        messageSource.getMessage("currency-search-page.label.currencycode", null,
            LocaleContextHolder.getLocale()),
        messageSource.getMessage("reports-file-exportutil-availableBalance", null,
            LocaleContextHolder.getLocale()),
        messageSource.getMessage("reports-file-exportutil-currentBalance", null,
            LocaleContextHolder.getLocale()),
        messageSource.getMessage("reports-file-exportutil-status", null,
            LocaleContextHolder.getLocale())};
    return new ArrayList<String>(Arrays.asList(headerArr));
  }

  private static List<Object[]> getGlobalBalanceFileData(
      List<AccountBalanceReportDTO> balanceReportList) {
    List<Object[]> fileData = new ArrayList<Object[]>();

    for (AccountBalanceReportDTO accData : balanceReportList) {

      Object[] rowData = {accData.getUserName(), accData.getBusinessName(),
          accData.getAccCreationDate(), accData.getAccountNumber(),
          accData.getAccountType(), accData.getCurrency(), Double.parseDouble(accData.getAvailableBalance()),
          Double.parseDouble(accData.getCurrentBalance()), accData.getStatus()

      };
      fileData.add(rowData);
    }

    return fileData;
  }
}
