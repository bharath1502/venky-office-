package com.chatak.acquirer.admin.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
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
import org.springframework.util.CollectionUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.chatak.acquirer.admin.constants.FeatureConstants;
import com.chatak.acquirer.admin.constants.URLMappingConstants;
import com.chatak.acquirer.admin.controller.model.ExportDetails;
import com.chatak.acquirer.admin.exception.ChatakAdminException;
import com.chatak.acquirer.admin.model.MerchantData;
import com.chatak.acquirer.admin.model.MerchantSearchResponse;
import com.chatak.acquirer.admin.service.MerchantAccountService;
import com.chatak.acquirer.admin.service.MerchantUpdateService;
import com.chatak.acquirer.admin.service.MerchantValidateService;
import com.chatak.acquirer.admin.service.SettlementReportService;
import com.chatak.acquirer.admin.service.TransactionService;
import com.chatak.acquirer.admin.util.ExportUtil;
import com.chatak.acquirer.admin.util.PaginationUtil;
import com.chatak.acquirer.admin.util.ReportsFileExportsUtil;
import com.chatak.acquirer.admin.util.TransactionFileExportUtil;
import com.chatak.pg.acq.dao.model.PGMerchant;
import com.chatak.pg.constants.PGConstants;
import com.chatak.pg.enums.ExportType;
import com.chatak.pg.model.AccessLogsDTO;
import com.chatak.pg.model.Merchant;
import com.chatak.pg.model.ReportsDTO;
import com.chatak.pg.user.bean.GetTransactionsListRequest;
import com.chatak.pg.user.bean.GetTransactionsListResponse;
import com.chatak.pg.user.bean.Transaction;
import com.chatak.pg.util.Constants;
import com.chatak.pg.util.DateUtil;

@SuppressWarnings({"rawtypes", "unchecked"})
@Controller
public class UserReportsController implements URLMappingConstants {

  private static Logger logger = Logger.getLogger(UserReportsController.class);

  @Autowired
  MessageSource messageSource;

  @Autowired
  private MerchantValidateService merchantValidateService;
  
  @Autowired
  private MerchantUpdateService merchantUpdateService;
  
  @Autowired
  private MerchantAccountService merchantAccountService;

  @Autowired
  private TransactionService transactionService;
  
  @Autowired
  SettlementReportService settlementReportService;

  @RequestMapping(value = SHOW_USER_ACCOUNT_TRANSACTION_DETAILS, method = RequestMethod.POST)
  public ModelAndView showIndividualUserAccTransdetails(HttpServletRequest request,
      HttpServletResponse response, @FormParam("getMerchantId") final Long getMerchantId,
      @FormParam("getMerchantCode") final String getMerchantCode,
      @FormParam("fromDate") final String fromDate, @FormParam("toDate") final String toDate,
      HttpSession session, Map model) {
    logger.info("Entering :: ReportsController :: showIndividualUserAccdetails method ");
    String userType = (String) session.getAttribute(Constants.LOGIN_USER_TYPE);
    ModelAndView modelAndView = new ModelAndView(SHOW_INDIVIDUAL_USER_ACCOUNT_DETAILS);
    String existingFeature = (String) session.getAttribute(Constants.EXISTING_FEATURES);
    if (Constants.ADMIN_VALUE.equalsIgnoreCase(userType)) {
      if (!existingFeature.contains(FeatureConstants.ADMIN_SERVICE_REPORTS_FEATURE_ID)) {
        return setInvalidPage(session, modelAndView);
      }
    } else if (Constants.RESELLER.equalsIgnoreCase(userType)
        && !existingFeature.contains(FeatureConstants.RESELLER_SERVICE_REPORTS_FEATURE_ID)) {
      return setInvalidPage(session, modelAndView);
    }
    Merchant merchant = new Merchant();
    GetTransactionsListRequest transactionsListRequest = new GetTransactionsListRequest();
    modelAndView.addObject(Constants.ERROR, null);
    modelAndView.addObject(Constants.SUCESS, null);
    try {
      merchant.setId(getMerchantId);
      transactionsListRequest.setMerchant_code((getMerchantCode));
      transactionsListRequest.setFrom_date(fromDate);
      transactionsListRequest.setTo_date(toDate);
      merchant = merchantValidateService.getMerchant(merchant);
      Merchant merchantAccount = merchantAccountService.getMerchantAccountDetails(getMerchantCode);
      merchant.setCreatedDate(merchantAccount.getCreatedDate().toString());
      GetTransactionsListResponse transactionsList =
          transactionService.getAllTransactionsOnMerchantCode(transactionsListRequest);
      session.setAttribute("specificUserTransList", transactionsList.getTransactionList());
      merchant.setCreatedDate(merchantAccount.getCreatedDate().toString());
      merchant.setTransactionDiv(true);
      modelAndView.addObject(Constants.MERCHANT, merchant);
      modelAndView.addObject("merchantAccount", merchantAccount);
      session.setAttribute("transactionsList", transactionsList.getTransactionList());
    } catch (ChatakAdminException e) {
      modelAndView.addObject(Constants.ERROR,
          messageSource.getMessage(Constants.CHATAK_GENERAL_ERROR, null, LocaleContextHolder.getLocale()));
      logger.error("ERROR:: ReportsController:: showIndividualUserAccdetails ChatakAdminException", e);
    } catch (Exception e) {
      modelAndView.addObject(Constants.ERROR,
          messageSource.getMessage(Constants.CHATAK_GENERAL_ERROR, null, LocaleContextHolder.getLocale()));
      logger.error("ERROR:: ReportsController:: showIndividualUserAccdetails Exception", e);
    }
    logger.info("EXITING :: ReportsController :: showIndividualUserAccdetails");
    return modelAndView;
  }

  private ModelAndView setInvalidPage(HttpSession session, ModelAndView modelAndView) {
	session.invalidate();
	modelAndView.setViewName(INVALID_REQUEST_PAGE);
	return modelAndView;
  }

  @RequestMapping(value = SPECIFIC_USER_STATEMENT_REPORT, method = RequestMethod.GET)
  public ModelAndView showSpecificUserStatementReports(HttpServletRequest request,
      HttpServletResponse response, Merchant merchant, BindingResult bindingResult, Map model,
      HttpSession session) {
    logger.info("Entering:: ReportsController:: showSpecificUserStatementReports method");
    ModelAndView modelAndView = new ModelAndView(SPECIFIC_USER_STATEMENT_REPORTS_SHOW);
    String existingFeature = (String) session.getAttribute(Constants.EXISTING_FEATURES);
    String userType = (String) session.getAttribute(Constants.LOGIN_USER_TYPE);
    if (Constants.ADMIN_VALUE.equalsIgnoreCase(userType)) {
      if (!existingFeature.contains(FeatureConstants.ADMIN_SERVICE_REPORTS_FEATURE_ID)) {
        return setInvalidPage(session, modelAndView);
      }
    } else if (Constants.RESELLER.equalsIgnoreCase(userType)
        && !existingFeature.contains(FeatureConstants.RESELLER_SERVICE_REPORTS_FEATURE_ID)) {
      return setInvalidPage(session, modelAndView);
    }
    modelAndView.addObject(Constants.ERROR, null);
    session.setAttribute(Constants.ERROR, null);
    merchant.setPageSize(Constants.MAX_ENTITIES_PORTAL_DISPLAY_SIZE);
    merchant.setPageIndex(Constants.ONE);
    try {
      MerchantSearchResponse searchResponse = merchantUpdateService.searchAllMerchant(merchant);
      List<MerchantData> merchants = new ArrayList<MerchantData>();
      if (searchResponse != null && !CollectionUtils.isEmpty(searchResponse.getMerchants())) {
        merchants = searchResponse.getMerchants();
        for (MerchantData merchantNewDate : merchants) {
          merchantNewDate.setCreatedDateString(DateUtil
              .toDateStringFormat(merchantNewDate.getCreatedDate(), DateUtil.VIEW_DATE_TIME_FORMAT));
        }
        modelAndView =
            PaginationUtil.getPagenationModel(modelAndView, searchResponse.getTotalNoOfRows());
      }
      modelAndView.addObject(Constants.MERCHANTS_MODEL, merchants);
    } catch (Exception e) {
      modelAndView.addObject(Constants.ERROR,
          messageSource.getMessage(Constants.CHATAK_GENERAL_ERROR, null, LocaleContextHolder.getLocale()));
      logger.error("ERROR:: ReportsController:: showSpecificUserStatementReports method", e);
    }
    modelAndView.addObject(Constants.MERCHANT, new Merchant());
    logger.info("Exiting:: ReportsController:: showSpecificUserStatementReports method");
    return modelAndView;
  }

  @RequestMapping(value = ALL_ACCOUNTS_EXECUTED_TRANSACTIONS, method = RequestMethod.POST)
  public ModelAndView downloadAllUsersExecutedTransactions(HttpSession session, Map model,
      HttpServletRequest request, @FormParam("downLoadPageNumber") final Integer downLoadPageNumber,
      @FormParam("downloadType") final String downloadType, HttpServletResponse response) {
    logger.info("Entering:: ReportsController:: downloadSpecificUserAllTransReport method");
    ModelAndView modelAndView = new ModelAndView(ALL_ACCOUNTS_EXECUTED_TRANSACTIONS_SHOW);
    String userType = (String) session.getAttribute(Constants.LOGIN_USER_TYPE);
    String existingFeature = (String) session.getAttribute(Constants.EXISTING_FEATURES);
    if (Constants.ADMIN_VALUE.equalsIgnoreCase(userType)) {
      if (!existingFeature.contains(FeatureConstants.ADMIN_SERVICE_REPORTS_FEATURE_ID)) {
        return setInvalidPage(session, modelAndView);
      }
    } else if (Constants.RESELLER.equalsIgnoreCase(userType)
        && !existingFeature.contains(FeatureConstants.RESELLER_SERVICE_REPORTS_FEATURE_ID)) {
      return setInvalidPage(session, modelAndView);
    }
    List<ReportsDTO> executedTransactionsReportList =
        (List<ReportsDTO>) session.getAttribute("executedTransactionsReportList");
    try {
        ReportsFileExportsUtil.setExportDetailsDataForDownloadRoleReports(executedTransactionsReportList,messageSource,downloadType,response);
      modelAndView.addObject("transactionDiv", Boolean.TRUE);
      modelAndView.addObject(executedTransactionsReportList);
    } catch (Exception e) {
      modelAndView.addObject(executedTransactionsReportList);
      modelAndView.addObject(Constants.ERROR,
          messageSource.getMessage(Constants.CHATAK_GENERAL_ERROR, null, LocaleContextHolder.getLocale()));
      logger.error("ERROR:: ReportsController:: downloadSpecificUserAllTransReport method", e);
    }
    logger.info("Exiting:: ReportsController:: downloadSpecificUserAllTransReport method");
    return null;
  }

  @RequestMapping(value = SHOW_INDIVIDUAL_STATEMENT_REPORT, method = RequestMethod.POST)
  public ModelAndView showIndividualStamentAccdetails(HttpServletRequest request,
      HttpServletResponse response, @FormParam("getMerchantId") final Long getMerchantId,
      @FormParam("getMerchantCode") final String getMerchantCode, HttpSession session, Map model) {
    logger.info("Entering :: ReportsController :: showIndividualStamentAccdetails method ");
    ModelAndView modelAndView = new ModelAndView(SHOW_INDIVIDUAL_STATEMENT_ACC_DETAILS);
    String userType = (String) session.getAttribute(Constants.LOGIN_USER_TYPE);
    String existingFeature = (String) session.getAttribute(Constants.EXISTING_FEATURES);
    if (Constants.ADMIN_VALUE.equalsIgnoreCase(userType)) {
      if (!existingFeature.contains(FeatureConstants.ADMIN_SERVICE_REPORTS_FEATURE_ID)) {
        return setInvalidPage(session, modelAndView);
      }
    } else if (Constants.RESELLER.equalsIgnoreCase(userType)
        && !existingFeature.contains(FeatureConstants.RESELLER_SERVICE_REPORTS_FEATURE_ID)) {
      return setInvalidPage(session, modelAndView);
    }
    Merchant merchant = new Merchant();
    GetTransactionsListResponse transactionsList = new GetTransactionsListResponse();
    modelAndView.addObject(Constants.ERROR, null);
    modelAndView.addObject(Constants.SUCESS, null);
    try {
      merchant.setId(getMerchantId);
      merchant = merchantValidateService.getMerchant(merchant);
      Merchant merchantAccount = merchantAccountService.getMerchantAccountDetails(getMerchantCode);
      merchant.setCreatedDate(merchantAccount.getCreatedDate());
      modelAndView.addObject(Constants.MERCHANT, merchant);
      modelAndView.addObject("merchantAccount", merchantAccount);
      modelAndView.addObject("transactionsList", transactionsList.getTransactionList());
    } catch (ChatakAdminException e) {
      modelAndView.addObject(Constants.ERROR,
          messageSource.getMessage(Constants.CHATAK_GENERAL_ERROR, null, LocaleContextHolder.getLocale()));
      logger.error("ERROR:: ReportsController:: showIndividualStamentAccdetails ChatakAdminException", e);
    } catch (Exception e) {
      modelAndView.addObject(Constants.ERROR,
          messageSource.getMessage(Constants.CHATAK_GENERAL_ERROR, null, LocaleContextHolder.getLocale()));
      logger.error("ERROR:: ReportsController:: showIndividualStamentAccdetails Exception", e);
    }
    logger.info("EXITING :: ReportsController :: showIndividualStamentAccdetails");
    return modelAndView;
  }

  @RequestMapping(value = ACCESS_LOGS_REPORT_DOWNLOAD, method = RequestMethod.POST)
  public ModelAndView downloadAccessLogsReport(HttpSession session, Map model,
      HttpServletRequest request, @FormParam("downloadType") final String downloadType,
      HttpServletResponse response) {
    logger.info("Entering:: ReportsController:: downloadAccessLogsReport method");
    ModelAndView modelAndView = new ModelAndView(SHOW_GLOBAL_ACCESS_LOG_REPORT);
    List<AccessLogsDTO> accessLogsDownloadList =
        (List<AccessLogsDTO>) session.getAttribute("accessLogs");
    ExportDetails exportDetails = new ExportDetails();
    try {
      if (Constants.PDF_FILE_FORMAT.equalsIgnoreCase(downloadType)) {
        exportDetails.setExportType(ExportType.PDF);
      } else if (Constants.XLS_FILE_FORMAT.equalsIgnoreCase(downloadType)) {
        exportDetails.setExportType(ExportType.XLS);
        exportDetails.setExcelStartRowNumber(Integer.parseInt("5"));
    }
    setExportDetailsDataForDownloadAccessLogsReport(accessLogsDownloadList, exportDetails); 
    ExportUtil.exportData(exportDetails, response, messageSource);
    } catch (Exception e) {
      modelAndView.addObject(Constants.ERROR,
          messageSource.getMessage(Constants.CHATAK_GENERAL_ERROR, null, LocaleContextHolder.getLocale()));
      logger.error("ERROR:: ReportsController:: downloadAccessLogsReport method", e);
    }
    logger.info("Exiting:: ReportsController:: downloadAccessLogsReport method");
    modelAndView.addObject("accessLogs", accessLogsDownloadList);
    return modelAndView;
  }
  
  private void setExportDetailsDataForDownloadAccessLogsReport(List<AccessLogsDTO> list,
      ExportDetails exportDetails) {
    exportDetails.setReportName("Access_Logs");
    exportDetails.setHeaderMessageProperty("chatak.access.logs.reports.header.message");

    exportDetails.setHeaderList(getAccessLogsHeaderList());
    exportDetails.setFileData(getAccessLogsFileData(list));
  }

  @RequestMapping(value = DOWNLOAD_SPECIFIC_USER_STATEMENT_REPORT, method = RequestMethod.POST)
  public ModelAndView downloadSpecificUserStatementReport(HttpSession session, Map model,
      HttpServletRequest request, @FormParam("downLoadPageNumber") final Integer downLoadPageNumber,
      @FormParam("downloadType") final String downloadType, HttpServletResponse response,
      @FormParam("totalRecords") final Integer totalRecords,
      @FormParam("downloadAllRecords") final boolean downloadAllRecords) {
    logger.info("Entering:: ReportsController:: downloadSpecificUserStatementReport method");
    ModelAndView modelAndView = new ModelAndView(SPECIFIC_USER_STATEMENT_REPORTS_SHOW);
    Merchant merchant = new Merchant();
    List<PGMerchant> merchantList = null;
    try {
      if (downloadAllRecords) {
        merchant.setPageIndex(Constants.ONE);
        merchant.setPageSize(totalRecords);
      } else {
        merchant.setPageIndex(downLoadPageNumber);
        merchant.setPageSize(Constants.MAX_ENTITIES_PORTAL_DISPLAY_SIZE);
      }

      MerchantSearchResponse searchResponse = merchantUpdateService.searchAllMerchant(merchant);
      List<MerchantData> list = searchResponse.getMerchants();
      for (MerchantData merchantData : list) {
        merchantData.setCreatedDateString(DateUtil.toDateStringFormat(merchantData.getCreatedDate(),
            DateUtil.VIEW_DATE_TIME_FORMAT));
      }
      ExportDetails exportDetails = new ExportDetails();
      if (Constants.PDF_FILE_FORMAT.equalsIgnoreCase(downloadType)) {
    	  exportDetails.setExportType(ExportType.PDF);
      } else if (Constants.XLS_FILE_FORMAT.equalsIgnoreCase(downloadType)) {
    	  exportDetails.setExportType(ExportType.XLS);
		  exportDetails.setExcelStartRowNumber(Integer.parseInt("5"));
      }
      setExportDetailsDataForDownloadSpecificUserStatementReport(list, exportDetails);	
	  ExportUtil.exportData(exportDetails, response, messageSource);
    } catch (Exception e) {
      modelAndView.addObject(Constants.ERROR,
          messageSource.getMessage(Constants.CHATAK_GENERAL_ERROR, null, LocaleContextHolder.getLocale()));
      logger.error("ERROR:: ReportsController:: downloadSpecificUserStatementReport method", e);
    }
    logger.info("Exiting:: ReportsController:: downloadSpecificUserStatementReport method");
    modelAndView.addObject("merchants", merchantList);
    return null;
  }
  
  private void setExportDetailsDataForDownloadSpecificUserStatementReport(List<MerchantData> list,
      ExportDetails exportDetails) {
    exportDetails.setReportName("Reports");
    exportDetails.setHeaderMessageProperty("specific.user.statement.report.heading");

    exportDetails.setHeaderList(getSpecificUserStatementHeaderList());
    exportDetails.setFileData(getSpecificUserStatementFileData(list));
  }

  @RequestMapping(value = CHATAK_ADMIN_GET_SETTLEMENT_REPORT, method = RequestMethod.GET)
  public ModelAndView getSettlementReport(HttpSession session, Map model,
      HttpServletRequest request, HttpServletResponse response) {
    logger.info("Entering :: UserReportsController :: getSettlementReport method");
    ModelAndView modelAndView = new ModelAndView(CHATAK_ADMIN_HOME);
    List<PGMerchant> merchantList = null;
    try {
      GetTransactionsListRequest transaction = new GetTransactionsListRequest();
      final Calendar cal = Calendar.getInstance();
      cal.add(Calendar.DATE, -1);
      String yesterday = new SimpleDateFormat(PGConstants.DD_MM_YYYY).format(cal.getTime());

      transaction.setFrom_date(yesterday);
      transaction.setTo_date(yesterday);
      transaction.setMerchantSettlementStatus(PGConstants.PG_SETTLEMENT_EXECUTED);

      GetTransactionsListResponse transactionResponse =
          settlementReportService.searchSettlementReportTransactions(transaction);
      List<Transaction> list = transactionResponse.getTransactionList();
      TransactionFileExportUtil.downloadSettlementReportXl(list, response, messageSource, transaction);

    } catch (Exception e) {
      modelAndView.addObject(Constants.ERROR,
          messageSource.getMessage(Constants.CHATAK_GENERAL_ERROR, null, LocaleContextHolder.getLocale()));
      logger.error("ERROR :: UserReportsController :: getSettlementReport method", e);
    }
    logger.info("Exiting :: UserReportsController :: getSettlementReport method");
    modelAndView.addObject("merchants", merchantList);
    return null;
  }
  
  private List<String> getSpecificUserStatementHeaderList() {
    String[] headerArr = {
        messageSource.getMessage("reports-file-exportutil-userName", null,
            LocaleContextHolder.getLocale()),
        messageSource.getMessage("reports-file-exportutil-companyName", null,
            LocaleContextHolder.getLocale()),
        messageSource.getMessage("reports-file-exportutil-firstName", null,
            LocaleContextHolder.getLocale()),
        messageSource.getMessage("reports-file-exportutil-lastName", null,
            LocaleContextHolder.getLocale()),
        messageSource.getMessage("reports-file-exportutil-creationDate", null,
            LocaleContextHolder.getLocale()),
        messageSource.getMessage("reports-file-exportutil-status", null,
            LocaleContextHolder.getLocale())};
    return new ArrayList<String>(Arrays.asList(headerArr));
  }

  private static List<Object[]> getSpecificUserStatementFileData(List<MerchantData> list) {
    List<Object[]> fileData = new ArrayList<Object[]>();

    for (MerchantData merData : list) {

      Object[] rowData = {merData.getUserName(), merData.getBusinessName(), merData.getFirstName(),
          merData.getLastName(), merData.getCreatedDateString(), merData.getStatus()

      };
      fileData.add(rowData);
    }

    return fileData;
  }
  
  private List<String> getAccessLogsHeaderList() {
    String[] headerArr = {
        messageSource.getMessage("reports-file-exportutil-dateTime", null,
            LocaleContextHolder.getLocale()),
        messageSource.getMessage("reports-file-exportutil-userName", null,
            LocaleContextHolder.getLocale()),
        messageSource.getMessage("reports-file-exportutil-ipAddress", null,
            LocaleContextHolder.getLocale())};
    return new ArrayList<String>(Arrays.asList(headerArr));
  }

  private static List<Object[]> getAccessLogsFileData(List<AccessLogsDTO> list) {
    List<Object[]> fileData = new ArrayList<Object[]>();

    for (AccessLogsDTO accessLogs : list) {

      Object[] rowData = {accessLogs.getDateTime() != null ? accessLogs.getDateTime() + "" : "",
          accessLogs.getUserName() != null ? accessLogs.getUserName() + "" : "",
          accessLogs.getIpAddress() != null ? accessLogs.getIpAddress() : ""

      };
      fileData.add(rowData);
    }

    return fileData;
  }

}
