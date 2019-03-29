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
import org.springframework.util.CollectionUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.chatak.acquirer.admin.constants.FeatureConstants;
import com.chatak.acquirer.admin.constants.URLMappingConstants;
import com.chatak.acquirer.admin.controller.model.ExportDetails;
import com.chatak.acquirer.admin.controller.model.LoginResponse;
import com.chatak.acquirer.admin.controller.model.Option;
import com.chatak.acquirer.admin.exception.ChatakAdminException;
import com.chatak.acquirer.admin.service.BatchSchedularService;
import com.chatak.acquirer.admin.service.MerchantService;
import com.chatak.acquirer.admin.service.MerchantUpdateService;
import com.chatak.acquirer.admin.service.SettlementReportService;
import com.chatak.acquirer.admin.util.ExportUtil;
import com.chatak.acquirer.admin.util.JsonUtil;
import com.chatak.acquirer.admin.util.PaginationUtil;
import com.chatak.acquirer.admin.util.StringUtil;
import com.chatak.pg.bean.Response;
import com.chatak.pg.constants.PGConstants;
import com.chatak.pg.enums.ExportType;
import com.chatak.pg.model.SettlemetActionDTO;
import com.chatak.pg.user.bean.DailyFundingReport;
import com.chatak.pg.user.bean.GetBatchReportRequest;
import com.chatak.pg.user.bean.GetDailyFundingReportRequest;
import com.chatak.pg.user.bean.GetDailyFundingReportResponse;
import com.chatak.pg.user.bean.GetTransactionsListRequest;
import com.chatak.pg.user.bean.GetTransactionsListResponse;
import com.chatak.pg.user.bean.Transaction;
import com.chatak.pg.util.Constants;

@SuppressWarnings({"unchecked", "rawtypes"})
@Controller
public class BatchScheduleReportController implements URLMappingConstants {
	
  private static Logger logger = Logger.getLogger(BatchScheduleReportController.class);

  @Autowired
  MerchantUpdateService merchantUpdateService;

  @Autowired
  MessageSource messageSource;

  @Autowired
  MerchantService merchantService;

  @Autowired
  BatchSchedularService batchSchedularService;

  @Autowired
  SettlementReportService settlementReportService;

  @RequestMapping(value = CHATAK_ADMIN_SHOW_BATCH_REPORT, method = RequestMethod.GET)
  public ModelAndView showBatchReport(HttpSession session, Map model, HttpServletRequest request,
      HttpServletResponse response) {
    logger.info("Entering :: BatchScheduleReportController :: showBatchReport method");

    ModelAndView modelAndView = new ModelAndView(CHATAK_ADMIN_SHOW_BATCH_REPORT);
    String existingFeature = (String) session.getAttribute(Constants.EXISTING_FEATURES);
    if (!existingFeature.contains(FeatureConstants.ADMIN_SERVICE_UNBLOCKUSERS_FEATURE_ID)) {
      session.invalidate();
      modelAndView.setViewName(INVALID_REQUEST_PAGE);
      return modelAndView;
    }
    model.put(Constants.BATCH_REPORT, new GetBatchReportRequest());
    session.setAttribute(Constants.ERROR, null);
    modelAndView.addObject(Constants.ERROR, null);

    List<Option> merchantList = getMerchantData();
    modelAndView.addObject(Constants.MERCHANT_LIST, merchantList);
    List<Option> subMerchantList = getSubMerchantData();
    modelAndView.addObject(Constants.SUB_MERCHANT_LIST, subMerchantList);
    modelAndView.addObject("flag", false);

    logger.info("Exiting :: BatchScheduleReportController :: showBatchReport method");
    return modelAndView;
  }

  @RequestMapping(value = CHATAK_ADMIN_GET_BATCH_REPORT, method = RequestMethod.POST)
  public ModelAndView getBatchReport(HttpServletRequest request, HttpServletResponse response,
      GetBatchReportRequest batchReport, BindingResult bindingResult, Map model,
      HttpSession session) {
    logger.info("Entering :: BatchScheduleReportController :: getBatchReport method");
    
    ModelAndView modelAndView = new ModelAndView(CHATAK_ADMIN_SHOW_BATCH_REPORT);
    String existingFeature = (String) session.getAttribute(Constants.EXISTING_FEATURES);
    if (!existingFeature.contains(FeatureConstants.ADMIN_SERVICE_UNBLOCKUSERS_FEATURE_ID)) {
      session.invalidate();
      modelAndView.setViewName(INVALID_REQUEST_PAGE);
      return modelAndView;
    }
    session.setAttribute(Constants.BATCH_REQUEST_OBJECT, batchReport);
    List<Transaction> transactionList = new ArrayList<>();
    try {
      GetTransactionsListResponse transactionResponse =
          settlementReportService.searchBatchReportTransactions(batchReport);
      session.setAttribute(Constants.TRANSACTIONS_REPORT, transactionResponse);
      if (transactionResponse.getTransactionList() != null
          && !CollectionUtils.isEmpty(transactionResponse.getTransactionList())) {
        transactionList = transactionResponse.getTransactionList();
        modelAndView.addObject(Constants.TRANSACTIONS_MODEL, transactionList);
        modelAndView = PaginationUtil.getPagenationModel(modelAndView,
            transactionResponse.getTotalResultCount());
        modelAndView.addObject(Constants.TRANSACTIONS_MODEL, transactionList);
      } else {
        modelAndView.addObject(Constants.TRANSACTIONS_MODEL, transactionList);
        modelAndView = PaginationUtil.getPagenationModel(modelAndView,
            transactionResponse.getTotalResultCount());
      }
      modelAndView.addObject(Constants.TOTAL_RECORDS, transactionResponse.getTotalResultCount());
    } catch (ChatakAdminException e) {
      logger.error("Error :: BatchScheduleReportController :: getBatchReport method", e);
    }

    List<Option> merchantList = getMerchantData();
    modelAndView.addObject(Constants.MERCHANT_LIST, merchantList);
    List<Option> subMerchantList = getSubMerchantData();
    modelAndView.addObject(Constants.SUB_MERCHANT_LIST, subMerchantList);
    modelAndView.addObject(Constants.BATCH_REPORT, batchReport);
    if (batchReport.getMerchantCode() != null) {        
      Response response2 = merchantService.getSubMerchantCodeAndCompanyName(batchReport.getMerchantCode());
      modelAndView.addObject(Constants.SUB_MERCHANT_LIST, response2.getResponseList());
    }
    logger.info("Exiting :: BatchScheduleReportController :: getBatchReport method");
    return modelAndView;
  }
  
	@RequestMapping(value = CHATAK_ADMIN_GET_BATCH_REPORT, method = RequestMethod.GET)
	public ModelAndView getBatchReportGetMethod(HttpServletRequest request, HttpServletResponse response,
			GetBatchReportRequest batchReport, BindingResult bindingResult, Map model, HttpSession session) {
		logger.info("Entering :: BatchScheduleReportController :: getBatchReportGetMethod method");
		ModelAndView modelAndView = showBatchReport(session, model, request, response);
		logger.info("Exiting :: BatchScheduleReportController :: getBatchReportGetMethod method");
		return modelAndView;
	}

  @RequestMapping(value = CHATAK_ADMIN_SHOW_DAILY_FUNDING_REPORT, method = RequestMethod.GET)
  public ModelAndView showDailyFundingReport(HttpSession session, Map model,
      HttpServletRequest request, HttpServletResponse response) {
    logger.info("Entering :: BatchScheduleReportController :: showDailyFundingReport method");
    
    ModelAndView modelAndView = new ModelAndView(CHATAK_ADMIN_SHOW_DAILY_FUNDING_REPORT);
    String existingFeature = (String) session.getAttribute(Constants.EXISTING_FEATURES);
    if (!existingFeature.contains(FeatureConstants.ADMIN_SERVICE_FUNDING_REPORT_FEATURE_ID)) {
      session.invalidate();
      modelAndView.setViewName(INVALID_REQUEST_PAGE);
      return modelAndView;
    }
    session.setAttribute(Constants.ERROR, null);
    model.put("dailyFundingReport", new GetDailyFundingReportRequest());
    modelAndView.addObject(Constants.ERROR, null);
    modelAndView.addObject("flag", false);
    logger.info("Exiting :: BatchScheduleReportController :: showDailyFundingReport method");
    return modelAndView;
  }

  @PostMapping(value = CHATAK_ADMIN_GET_DAILY_FUNDING_REPORT)
  public ModelAndView getDailyFundingReport(HttpServletRequest request,
      HttpServletResponse response, GetDailyFundingReportRequest dailyFundingReport,
      BindingResult bindingResult, Map model, HttpSession session) {
    logger.info("Entering :: BatchScheduleReportController :: getDailyFundingReport method");

    ModelAndView modelAndView = new ModelAndView(CHATAK_ADMIN_SHOW_DAILY_FUNDING_REPORT);
    String existingFeature = (String) session.getAttribute(Constants.EXISTING_FEATURES);
    LoginResponse loginResponse =
	          (LoginResponse) session.getAttribute(Constants.LOGIN_RESPONSE_DATA);
    if (!existingFeature.contains(FeatureConstants.ADMIN_SERVICE_FUNDING_REPORT_FEATURE_ID)) {
      session.invalidate();
      modelAndView.setViewName(INVALID_REQUEST_PAGE);
      return modelAndView;
    }
    if (dailyFundingReport.getFromDate() == null && dailyFundingReport.getToDate() == null) {
      dailyFundingReport = (GetDailyFundingReportRequest) session.getAttribute(Constants.DAILY_FUNDING_REQUEST_OBJECT);
    }
    session.setAttribute(Constants.DAILY_FUNDING_REQUEST_OBJECT, dailyFundingReport);
    dailyFundingReport.setPageIndex(Constants.ONE);
    dailyFundingReport.setPageSize(Constants.MAX_ENTITIES_PORTAL_DISPLAY_SIZE);
    List<DailyFundingReport> list = new ArrayList<>();
    try {
      if (loginResponse.getUserType().equalsIgnoreCase(Constants.PM_USER_TYPE)
          || loginResponse.getUserType().equalsIgnoreCase(Constants.ISO_USER_TYPE)) {
        dailyFundingReport.setId(loginResponse.getEntityId());
      }  	
      GetDailyFundingReportResponse getReportResponse =
          batchSchedularService.searchDailyFundingReportDetails(dailyFundingReport);
      if (getReportResponse.getDailyFundingReport() != null
          && !CollectionUtils.isEmpty(getReportResponse.getDailyFundingReport())) {
        list = getReportResponse.getDailyFundingReport();
        modelAndView.addObject(Constants.TRANSACTIONS_MODEL, list);
        modelAndView = PaginationUtil.getPagenationModel(modelAndView,
            getReportResponse.getTotalResultCount());
        modelAndView.addObject(Constants.TRANSACTIONS_MODEL, list);
      }
      modelAndView.addObject(Constants.TOTAL_RECORDS, getReportResponse.getTotalResultCount());
    } catch (ChatakAdminException e) {
      logger.error("Error :: BatchScheduleReportController :: getDailyFundingReport method", e);
    }
    modelAndView.addObject("dailyFundingReport", dailyFundingReport);

    logger.info("Exiting :: BatchScheduleReportController :: getDailyFundingReport method");
    return modelAndView;
  }

  @RequestMapping(value = GET_SUB_MERCHANTS_BY_MERCHANT_CODE, method = RequestMethod.GET)
  public @ResponseBody String getSubMerchantsByMerchantId(Map model, HttpServletRequest request,
      HttpServletResponse response, HttpSession session) {
    ModelAndView modelAndView = new ModelAndView(CHATAK_ADMIN_SHOW_BATCH_REPORT);
    modelAndView.addObject(Constants.ERROR, null);
    logger.info("Entering :: BatchScheduleReportController :: getSubMerchantsByMerchantId method");
    String merchantCode = request.getParameter("merchantID");
    try {
      Response response2 = merchantService.getSubMerchantCodeAndCompanyName(merchantCode);
      if (response2 != null) {
        return JsonUtil.convertObjectToJSON(response2);
      }
    } catch (ChatakAdminException e) {
      logger.error("Error :: BatchScheduleReportController :: getSubMerchantsByMerchantId method", e);
    }
    logger.info("Exiting :: BatchScheduleReportController :: getSubMerchantsByMerchantId method");
    return null;
  }

  @RequestMapping(value = CHATAK_ADMIN_BATCH_TRANSACTION_PAGINATION, method = RequestMethod.POST)
  public ModelAndView getBatchReportPagination(final HttpSession session,
      @FormParam(Constants.PAGE_NUMBER) final Integer pageNumber,
      @FormParam(Constants.TOTAL_RECORDS) final Integer totalRecords, Map model) {
    logger.info("Entering :: BatchScheduleReportController :: getBatchReportPagination method");

    ModelAndView modelAndView = new ModelAndView(CHATAK_ADMIN_SHOW_BATCH_REPORT);
    try {

      GetBatchReportRequest batchReport =
          (GetBatchReportRequest) session.getAttribute(Constants.BATCH_REQUEST_OBJECT);
      model.put(Constants.BATCH_REPORT, batchReport);
      session.setAttribute(Constants.BATCH_REQUEST_OBJECT, batchReport);
      batchReport.setPageIndex(pageNumber);
      batchReport.setNoOfRecords(totalRecords);
      batchReport.setPageSize(Constants.MAX_TRANSACTION_ENTITY_DISPLAY_SIZE);

      GetTransactionsListResponse transactionsList =
          settlementReportService.searchBatchReportTransactions(batchReport);
      session.setAttribute(Constants.TRANSACTIONS_REPORT, transactionsList);

      if (transactionsList != null
          && !CollectionUtils.isEmpty(transactionsList.getTransactionList())) {
        modelAndView = PaginationUtil.getPagenationModelSuccessive(modelAndView, pageNumber,
            transactionsList.getTotalResultCount());
      }
      if (transactionsList != null) {
        modelAndView.addObject(Constants.TRANSACTIONS_MODEL, transactionsList.getTransactionList());
      }
    } catch (Exception e) {
      modelAndView.addObject(Constants.ERROR,
          messageSource.getMessage(Constants.CHATAK_GENERAL_ERROR, null, LocaleContextHolder.getLocale()));
      logger.error("ERROR :: BatchScheduleReportController :: getBatchReportPagination method", e);
    }
    modelAndView.addObject(Constants.SETTLEMENT_DTO, new SettlemetActionDTO());
    
    List<Option> merchantList = getMerchantData();
    modelAndView.addObject(Constants.MERCHANT_LIST, merchantList);
    List<Option> subMerchantList = getSubMerchantData();
    modelAndView.addObject(Constants.SUB_MERCHANT_LIST, subMerchantList);

    logger.info("Exiting :: BatchScheduleReportController :: getBatchReportPagination method");
    return modelAndView;
  }

  @RequestMapping(value = CHATA_ADMIN_BATCH_TRANSACTION_EXPORT, method = RequestMethod.POST)
  public ModelAndView downloadBatchReport(HttpSession session, Map model,
      HttpServletRequest request, @FormParam("downLoadPageNumber") final Integer downLoadPageNumber,
      HttpServletResponse response, @FormParam(PGConstants.TOTAL_RECORDS) final Integer totalRecords,
      @FormParam("downloadAllRecords") final boolean downloadAllRecords) {
    logger.info("Entering :: BatchScheduleReportController :: downloadBatchReport method");

    String downloadType = request.getParameter(Constants.DOWNLOAD_TYPE);
    ModelAndView modelAndView = new ModelAndView(CHATAK_ADMIN_SHOW_BATCH_REPORT);
    try {
      GetBatchReportRequest batchReport =
          (GetBatchReportRequest) session.getAttribute(Constants.BATCH_REQUEST_OBJECT);
      batchReport.setPageIndex(downLoadPageNumber);
      Integer pageSize = batchReport.getPageSize();
      if (downloadAllRecords) {
        batchReport.setPageIndex(Constants.ONE);
        batchReport.setPageSize(totalRecords);
      }
      GetTransactionsListResponse transactionsList =
          settlementReportService.searchBatchReportTransactions(batchReport);
      List<Transaction> list = transactionsList.getTransactionList();
      ExportDetails exportDetails = new ExportDetails();
      if (StringUtil.isListNotNullNEmpty(list)
          && Constants.XLS_FILE_FORMAT.equalsIgnoreCase(downloadType)) {
    	  exportDetails.setExportType(ExportType.XLS);
		  exportDetails.setExcelStartRowNumber(Integer.parseInt("5"));
      } else {
    	  exportDetails.setExportType(ExportType.PDF);
      }
      setExportDetailsDataForDownloadBatchReport(list, exportDetails);	
	  ExportUtil.exportData(exportDetails, response, messageSource);
      batchReport.setPageSize(pageSize);
    } catch (Exception e) {
      modelAndView.addObject(Constants.ERROR, messageSource
          .getMessage(Constants.CHATAK_GENERAL_ERROR, null, LocaleContextHolder.getLocale()));
      logger.error("ERROR :: BatchScheduleReportController :: downloadBatchReport method", e);
    }
    logger.info("Exiting :: BatchScheduleReportController :: downloadBatchReport method");
    return null;
  }
  
  private void setExportDetailsDataForDownloadBatchReport(List<Transaction> list,
      ExportDetails exportDetails) {
    exportDetails.setReportName("Batch-Report-");
    exportDetails.setHeaderMessageProperty("chatak-batch-report");

    exportDetails.setHeaderList(getBatchHeaderList());
    exportDetails.setFileData(getBatchFileData(list));
  }

  @RequestMapping(value = CHATAK_ADMIN_FUNDING_REPORT_PAGINATION, method = RequestMethod.POST)
  public ModelAndView getFundingReportPagination(final HttpSession session,
      @FormParam(Constants.PAGE_NUMBER) final Integer pageNumber,
      @FormParam(Constants.TOTAL_RECORDS) final Integer totalRecords, Map model) {
    logger.info("Entering :: BatchScheduleReportController :: getFundingReportPagination method");

    ModelAndView modelAndView = new ModelAndView(CHATAK_ADMIN_SHOW_DAILY_FUNDING_REPORT);
    try {

      GetDailyFundingReportRequest batchReport = (GetDailyFundingReportRequest) session
          .getAttribute(Constants.DAILY_FUNDING_REQUEST_OBJECT);
      model.put(Constants.FUNDING_REPORT, batchReport);
      batchReport.setPageIndex(pageNumber);
      batchReport.setNoOfRecords(totalRecords);
      batchReport.setPageSize(Constants.MAX_TRANSACTION_ENTITY_DISPLAY_SIZE);

      GetDailyFundingReportResponse getReportResponse =
          batchSchedularService.searchDailyFundingReportDetails(batchReport);
      session.setAttribute(Constants.TRANSACTIONS_REPORT,
          getReportResponse.getDailyFundingReport());

      if (getReportResponse.getDailyFundingReport() != null
          && !CollectionUtils.isEmpty(getReportResponse.getDailyFundingReport())) {
        modelAndView = PaginationUtil.getPagenationModelSuccessive(modelAndView, pageNumber,
            getReportResponse.getTotalResultCount());
      }
      if (getReportResponse.getDailyFundingReport() != null) {
        modelAndView.addObject(Constants.TRANSACTIONS_MODEL,
            getReportResponse.getDailyFundingReport());
      }
    } catch (Exception e) {
      modelAndView.addObject(Constants.ERROR,
          messageSource.getMessage(Constants.CHATAK_GENERAL_ERROR, null, LocaleContextHolder.getLocale()));
      logger.error("ERROR :: BatchScheduleReportController :: getFundingReportPagination method",
          e);
    }
    modelAndView.addObject(Constants.SETTLEMENT_DTO, new SettlemetActionDTO());

    logger.info("Exiting :: BatchScheduleReportController :: getFundingReportPagination method");
    return modelAndView;
  }

  @RequestMapping(value = CHATA_ADMIN_FUNDING_REPORT_EXPORT, method = RequestMethod.POST)
  public ModelAndView downloadFundingReport(HttpSession session, Map model,
      HttpServletRequest request, @FormParam("downLoadPageNumber") final Integer downLoadPageNumber,
      HttpServletResponse response, @FormParam(PGConstants.TOTAL_RECORDS) final Integer totalRecords,
      @FormParam("downloadAllRecords") final boolean downloadAllRecords) {
    logger.info("Entering :: BatchScheduleReportController :: downloadFundingReport method");

    String downloadType = request.getParameter(Constants.DOWNLOAD_TYPE);
    ModelAndView modelAndView = new ModelAndView(CHATAK_ADMIN_SHOW_DAILY_FUNDING_REPORT);
    try {
      GetDailyFundingReportRequest batchReport = (GetDailyFundingReportRequest) session
          .getAttribute(Constants.DAILY_FUNDING_REQUEST_OBJECT);
      batchReport.setPageIndex(downLoadPageNumber);
      Integer pageSize = batchReport.getPageSize();
      if (downloadAllRecords) {
        batchReport.setPageIndex(Constants.ONE);
        batchReport.setPageSize(totalRecords);
      }
      GetDailyFundingReportResponse getReportResponse =
          batchSchedularService.searchDailyFundingReportDetails(batchReport);
      List<DailyFundingReport> list = getReportResponse.getDailyFundingReport();
      ExportDetails exportDetails = new ExportDetails();
      if (StringUtil.isListNotNullNEmpty(list)
          && Constants.XLS_FILE_FORMAT.equalsIgnoreCase(downloadType)) {
    	  exportDetails.setExportType(ExportType.XLS);
		  exportDetails.setExcelStartRowNumber(Integer.parseInt("5"));
      } else {
    	  exportDetails.setExportType(ExportType.PDF);
      }
      setExportDetailsDataForDownloadFundingReport(list, exportDetails);	
	  ExportUtil.exportData(exportDetails, response, messageSource);
      batchReport.setPageSize(pageSize);
    } catch (Exception e) {
      modelAndView.addObject(Constants.ERROR, messageSource
          .getMessage(Constants.CHATAK_GENERAL_ERROR, null, LocaleContextHolder.getLocale()));
      logger.error("ERROR :: BatchScheduleReportController :: downloadFundingReport method", e);
    }
    logger.info("Exiting :: BatchScheduleReportController :: downloadFundingReport method");
    return null;
  }
  
  private void setExportDetailsDataForDownloadFundingReport(List<DailyFundingReport> list,
      ExportDetails exportDetails) {
    exportDetails.setReportName("Daily_Funding_Report_");
    exportDetails.setHeaderMessageProperty("chatak-report-lable-daily-unding-report");

    exportDetails.setHeaderList(getFundingHeaderList());
    exportDetails.setFileData(getFundingFileData(list));
  }

  private List<Option> getSubMerchantData() {
    Option option;
    Map<String, String> subMerchantMap =
        merchantUpdateService.getMerchantCodeAndCompanyName(PGConstants.SUB_MERCHANT);
    List<Option> subMerchantList = new ArrayList<>();
    for (Map.Entry<String, String> entry : subMerchantMap.entrySet()) {
      option = new Option();
      option.setValue(entry.getKey());
      option.setLabel(entry.getValue());

      subMerchantList.add(option);
    }
    return subMerchantList;
  }

  private List<Option> getMerchantData() {
    Map<String, String> merchantMap =
        merchantUpdateService.getMerchantCodeAndCompanyName(PGConstants.MERCHANT);
    List<Option> merchantList = new ArrayList<>();
    Option option = null;
    for (Map.Entry<String, String> entry : merchantMap.entrySet()) {
      option = new Option();
      option.setValue(entry.getKey());
      option.setLabel(entry.getValue());

      merchantList.add(option);
    }
    return merchantList;
  }
  
  @RequestMapping(value = CHATA_ADMIN_MANUAL_FUNDING_REPORT, method = RequestMethod.POST)
  public @ResponseBody String processManualFundingReport(HttpServletRequest request, Map model,
      HttpSession session) {
    try {
      Response response2 = batchSchedularService.manualFundingReport();
      return JsonUtil.convertObjectToJSON(response2);
    } catch (Exception e) {
      logger.info("ERROR :: BatchScheduleReportController :: processManualFundingReport method", e);
    }
    return null;
  }
  
  @RequestMapping(value = CHATAK_MERCHANT_TXN_HISTORY, method = RequestMethod.POST)
  public ModelAndView getTransactionHistory(HttpServletRequest request, HttpServletResponse response,
      HttpSession session, Map model, @FormParam("merchantCode") final String merchantCode,
      @FormParam("batchID") final String batchID) {
    logger.info("Entering :: BatchScheduleReportController :: getTransactionHistory method");
    
    ModelAndView modelAndView = new ModelAndView(CHATAK_MERCHANT_TXN_HISTORY);
    GetTransactionsListRequest transaction = new GetTransactionsListRequest();
    transaction.setMerchant_code(merchantCode);
    transaction.setBatchID(batchID);
    transaction.setPageIndex(Constants.ONE);
    transaction.setPageSize(Constants.MAX_ENTITIES_PORTAL_DISPLAY_SIZE);
    session.setAttribute(Constants.TRANSACTION_HISTORY, transaction);
    List<Transaction> transactionList = new ArrayList<>();
    try {
      GetTransactionsListResponse transactionResponse = batchSchedularService.getMerchantHistoryOnId(transaction);
      session.setAttribute(Constants.TRANSACTIONS_REPORT, transactionResponse);
      if (transactionResponse.getTransactionList() != null
          && !CollectionUtils.isEmpty(transactionResponse.getTransactionList())) {
        transactionList = transactionResponse.getTransactionList();
        modelAndView.addObject(Constants.TRANSACTIONS_MODEL, transactionList);
        modelAndView = PaginationUtil.getPagenationModel(modelAndView,
            transactionResponse.getTotalResultCount());
        modelAndView.addObject(Constants.TRANSACTIONS_MODEL, transactionList);
        session.setAttribute("txnsHistoryOnId", transactionList);
      } else {
        modelAndView.addObject(Constants.TRANSACTIONS_MODEL, transactionList);
        modelAndView = PaginationUtil.getPagenationModel(modelAndView,
            transactionResponse.getTotalResultCount());
      }
    } catch (Exception e) {
      logger.error("ERROR :: DashBoardController :: getAccountHistory method", e);
      modelAndView.setViewName(INVALID_REQUEST_PAGE);
      return modelAndView;
    }
    
    logger.info("Exiting :: BatchScheduleReportController :: getTransactionHistory method");
    return modelAndView;
  }

  @RequestMapping(value = CHATAK_MERCHANT_TXN_HISTORY_REPORT, method = RequestMethod.POST)
  public ModelAndView downloadTxnsHistoryReport(HttpSession session, Map model,
      HttpServletRequest request, @FormParam("downLoadPageNumber") final Integer downLoadPageNumber,
      HttpServletResponse response, @FormParam(PGConstants.TOTAL_RECORDS) final Integer totalRecords,
      @FormParam("downloadAllRecords") final boolean downloadAllRecords) {
    logger.info("Entering :: BatchScheduleReportController :: downloadBatchReport method");

    String downloadType = request.getParameter(Constants.DOWNLOAD_TYPE);
    ModelAndView modelAndView = new ModelAndView(CHATAK_MERCHANT_TXN_HISTORY);
    try {      
      GetTransactionsListRequest batchReport = (GetTransactionsListRequest) session.getAttribute(Constants.TRANSACTION_HISTORY);
      model.put(Constants.FUNDING_REPORT, batchReport);
      
      batchReport.setPageIndex(downLoadPageNumber);
      batchReport.setPageSize(Constants.MAX_TRANSACTION_ENTITY_DISPLAY_SIZE);
      
      if (downloadAllRecords) {
        batchReport.setPageIndex(Constants.ONE);
        batchReport.setPageSize(totalRecords);
      }

      GetTransactionsListResponse transactionResponse = batchSchedularService.getMerchantHistoryOnId(batchReport);
      session.setAttribute(Constants.TRANSACTIONS_REPORT, transactionResponse.getTransactionList());
      List<Transaction> list = transactionResponse.getTransactionList();
      ExportDetails exportDetails = new ExportDetails();
      if (StringUtil.isListNotNullNEmpty(list) && Constants.XLS_FILE_FORMAT.equalsIgnoreCase(downloadType)) {
        exportDetails.setExportType(ExportType.XLS);
		exportDetails.setExcelStartRowNumber(Integer.parseInt("5"));
      } else {
    	  exportDetails.setExportType(ExportType.PDF);
      }
      setExportDetailsDataForDownloadTransactionHistoryReport(list, exportDetails);	
	  ExportUtil.exportData(exportDetails, response, messageSource);
    } catch (Exception e) {
      modelAndView.addObject(Constants.ERROR, messageSource
          .getMessage(Constants.CHATAK_GENERAL_ERROR, null, LocaleContextHolder.getLocale()));
      logger.error("ERROR :: BatchScheduleReportController :: downloadBatchReport method", e);
    }
    logger.info("Exiting :: BatchScheduleReportController :: downloadBatchReport method");
    return null;
  }
  
  private void setExportDetailsDataForDownloadTransactionHistoryReport(
      List<Transaction> transactionList, ExportDetails exportDetails) {
    exportDetails.setReportName("Transactions");
    exportDetails.setHeaderMessageProperty("chatak.header.transaction.messages");

    exportDetails.setHeaderList(getTransactionHistoryHeaderList());
    exportDetails.setFileData(getTransactionHistoryFileData(transactionList));
  }

  @RequestMapping(value = CHATAK_TRANSACTION_HISTORY_PAGINATION, method = RequestMethod.POST)
  public ModelAndView getTransactionHistoryPagination(final HttpSession session,
      @FormParam(Constants.PAGE_NUMBER) final Integer pageNumber,
      @FormParam(Constants.TOTAL_RECORDS) final Integer totalRecords, Map model) {
    logger.info("Entering :: BatchScheduleReportController :: getTransactionHistoryPagination method");

    ModelAndView modelAndView = new ModelAndView(CHATAK_MERCHANT_TXN_HISTORY);
    try {

      GetTransactionsListRequest batchReport = (GetTransactionsListRequest) session.getAttribute(Constants.TRANSACTION_HISTORY);
      model.put(Constants.FUNDING_REPORT, batchReport);
      batchReport.setPageIndex(pageNumber);
      batchReport.setNoOfRecords(totalRecords);
      batchReport.setPageSize(Constants.MAX_TRANSACTION_ENTITY_DISPLAY_SIZE);

      GetTransactionsListResponse transactionResponse = batchSchedularService.getMerchantHistoryOnId(batchReport);
      session.setAttribute(Constants.TRANSACTIONS_REPORT, transactionResponse.getTransactionList());

      if (transactionResponse.getTransactionList() != null
          && !CollectionUtils.isEmpty(transactionResponse.getTransactionList())) {
        modelAndView = PaginationUtil.getPagenationModelSuccessive(modelAndView, pageNumber,
            transactionResponse.getTotalResultCount());
      }
      if (transactionResponse.getTransactionList() != null) {
        modelAndView.addObject(Constants.TRANSACTIONS_MODEL, transactionResponse.getTransactionList());
      }
    } catch (Exception e) {
      modelAndView.addObject(Constants.ERROR,
          messageSource.getMessage(Constants.CHATAK_GENERAL_ERROR, null, LocaleContextHolder.getLocale()));
      logger.error("ERROR :: BatchScheduleReportController :: getTransactionHistoryPagination method",
          e);
    }
    modelAndView.addObject(Constants.SETTLEMENT_DTO, new SettlemetActionDTO());

    logger.info("Exiting :: BatchScheduleReportController :: getTransactionHistoryPagination method");
    return modelAndView;
  }
  
  private List<String> getBatchHeaderList() {
    String[] headerArr = {
        messageSource.getMessage(PGConstants.TRANSACTION_REPORT_BATCHID, null,
            LocaleContextHolder.getLocale()),
        messageSource.getMessage("home.label.batch.date.time", null,
            LocaleContextHolder.getLocale()),
        messageSource.getMessage(PGConstants.REPORT_LABEL_TRANSACTIONS_DATEORTIME, null,
            LocaleContextHolder.getLocale()),
        messageSource.getMessage("admin.common-deviceLocalTxnTime", null,
            LocaleContextHolder.getLocale()),
        messageSource.getMessage("transaction-file-exportutil-transactionId", null,
            LocaleContextHolder.getLocale()),
        messageSource.getMessage("reports.label.balancereports.merchantorsubmerchantName", null,
            LocaleContextHolder.getLocale()),
        messageSource.getMessage("reports.label.balancereports.merchantorsubmerchantcode", null,
            LocaleContextHolder.getLocale()),
        messageSource.getMessage("reports.label.balancereports.accountnumber", null,
            LocaleContextHolder.getLocale()),
        messageSource.getMessage("transaction-file-exportutil-description", null,
            LocaleContextHolder.getLocale()),
        messageSource.getMessage("currency-search-page.label.currencycode", null,
            LocaleContextHolder.getLocale()),
        messageSource.getMessage("transaction-file-exportutil-amount", null,
            LocaleContextHolder.getLocale()),
        messageSource.getMessage("transaction-file-exportutil-procTxnId", null,
            LocaleContextHolder.getLocale()),
        messageSource.getMessage("reports.label.transactions.cardnumberField", null,
            LocaleContextHolder.getLocale()),
        messageSource.getMessage("transaction-file-exportutil-txnType", null,
            LocaleContextHolder.getLocale()),
        messageSource.getMessage("transaction-file-exportutil-txnStatus", null,
            LocaleContextHolder.getLocale()),
        messageSource.getMessage("transaction-file-exportutil-merchantTXNAmount", null,
            LocaleContextHolder.getLocale())};
    return new ArrayList<String>(Arrays.asList(headerArr));
  }

  private static List<Object[]> getBatchFileData(List<Transaction> list) {
    List<Object[]> fileData = new ArrayList<Object[]>();

    for (Transaction transaction : list) {
      if (!"".equals(transaction.getTimeZoneOffset()) && null != transaction.getTimeZoneOffset()) {
        transaction.setTimeZoneOffset("("+transaction.getTimeZoneOffset()+")");
      }
      Object[] rowData =
          {transaction.getBatchId(), transaction.getBatchDate(), transaction.getTransactionDate(),
              transaction.getDeviceLocalTxnTime()+transaction.getTimeZoneOffset(),
              transaction.getTransactionId() != null ? Long.parseLong(transaction.getTransactionId()) : "", transaction.getMerchantBusinessName(),
    		  Long.parseLong( transaction.getMerchant_code()), transaction.getAccountNumber(),
              transaction.getTxnDescription(), transaction.getLocalCurrency(),
              transaction.getTxn_total_amount()/Double.parseDouble("100"), transaction.getRef_transaction_id(),
              Long.parseLong(transaction.getMaskCardNumber()), transaction.getTransaction_type().toUpperCase(),
              transaction.getMerchantSettlementStatus(), Double.parseDouble(transaction.getTransactionAmount())

          };
      fileData.add(rowData);
    }

    return fileData;
  }

  private List<String> getTransactionHistoryHeaderList() {
    String[] headerArr = {
        messageSource.getMessage(PGConstants.REPORT_LABEL_TRANSACTIONS_DATEORTIME, null,
            LocaleContextHolder.getLocale()),
        messageSource.getMessage("admin.common-deviceLocalTxnTime", null,
            LocaleContextHolder.getLocale()),
        messageSource.getMessage("transaction-file-exportutil-transactionId", null,
            LocaleContextHolder.getLocale()),
        messageSource.getMessage("reports.label.balancereports.merchantorsubmerchantName", null,
            LocaleContextHolder.getLocale()),
        messageSource.getMessage("reports.label.balancereports.merchantorsubmerchantcode", null,
            LocaleContextHolder.getLocale()),
        messageSource.getMessage("transaction-file-exportutil-terminalid", null,
            LocaleContextHolder.getLocale()),
        messageSource.getMessage("reports-file-exportutil-accountNumber", null,
            LocaleContextHolder.getLocale()),
        messageSource.getMessage("transaction-file-exportutil-description", null,
            LocaleContextHolder.getLocale()),
        messageSource.getMessage("transaction-file-exportutil-procTxnId", null,
            LocaleContextHolder.getLocale()),
        messageSource.getMessage(PGConstants.TRANSACTION_REPORT_BATCHID, null,
            LocaleContextHolder.getLocale()),
        messageSource.getMessage("reports.label.transactions.cardnumberField", null,
            LocaleContextHolder.getLocale()),
        messageSource.getMessage("currency-search-page.label.currency", null,
            LocaleContextHolder.getLocale()),
        messageSource.getMessage("transactionFileExportUtil.admin.txn.amt", null,
            LocaleContextHolder.getLocale()),
        messageSource.getMessage("transactions-search.label.merchantfee", null,
            LocaleContextHolder.getLocale()),
        messageSource.getMessage("transaction-file-exportutil-totaltxnamt", null,
            LocaleContextHolder.getLocale()),
        messageSource.getMessage("transaction-file-exportutil-txnType", null,
            LocaleContextHolder.getLocale()),
        messageSource.getMessage("transaction-file-exportutil-txnStatus", null,
            LocaleContextHolder.getLocale()),
        messageSource.getMessage("login.label.username", null, LocaleContextHolder.getLocale())};
    return new ArrayList<String>(Arrays.asList(headerArr));
  }

  private static List<Object[]> getTransactionHistoryFileData(List<Transaction> transactionList) {
    List<Object[]> fileData = new ArrayList<Object[]>();

    for (Transaction transaction : transactionList) {
      if (!"".equals(transaction.getTimeZoneOffset()) && null != transaction.getTimeZoneOffset()) {
        transaction.setTimeZoneOffset("("+transaction.getTimeZoneOffset()+")");
      }
      Object[] rowData = {transaction.getTransactionDate(),
          transaction.getDeviceLocalTxnTime() + transaction.getTimeZoneOffset(),
          Long.parseLong(transaction.getTransactionId()),
          transaction.getMerchantBusinessName(), Long.parseLong(transaction.getMerchant_code()),
          transaction.getTerminal_id(), transaction.getAccountNumber(),
          transaction.getTxnDescription(), transaction.getRef_transaction_id().toString(),
          transaction.getBatchId(), Long.parseLong(transaction.getMaskCardNumber()), transaction.getLocalCurrency(),
          Double.parseDouble(transaction.getTransactionAmount()), transaction.getFee_amount(),
          transaction.getTxn_total_amount(),
          transaction.getTransaction_type().toUpperCase(),
          transaction.getMerchantSettlementStatus(), transaction.getUserName()

      };
      fileData.add(rowData);
    }

    return fileData;
  }

  private List<String> getFundingHeaderList() {
    String[] headerArr = {
        messageSource.getMessage(PGConstants.REPORT_LABEL_TRANSACTIONS_DATEORTIME, null,
            LocaleContextHolder.getLocale()),
        messageSource.getMessage(PGConstants.TRANSACTION_REPORT_BATCHID, null,
            LocaleContextHolder.getLocale()),
        messageSource.getMessage("reports.label.transactions.merchantcode", null,
            LocaleContextHolder.getLocale()),
        messageSource.getMessage("merchant.label.merchantname", null,
            LocaleContextHolder.getLocale()),
        messageSource.getMessage("chatak-report-lable-sub-merchant-code", null,
            LocaleContextHolder.getLocale()),
        messageSource.getMessage("merchant-file-exportutil-subMerchantName", null,
            LocaleContextHolder.getLocale()),
        messageSource.getMessage("reports.label.balancereports.currency", null,
            LocaleContextHolder.getLocale()),
        messageSource.getMessage("manage.label.sub-merchant.bankaccountnumber", null,
            LocaleContextHolder.getLocale()),
        messageSource.getMessage("manage.label.sub-merchant.bankroutingnumber", null,
            LocaleContextHolder.getLocale()),
        messageSource.getMessage("chatak-report-lable-funding-amount", null,
            LocaleContextHolder.getLocale()),
        messageSource.getMessage("chatak-report-lable-fees-billed-amount", null,
            LocaleContextHolder.getLocale()),
        messageSource.getMessage("chatak-report-lable-net-funding-amount", null,
            LocaleContextHolder.getLocale())};
    return new ArrayList<String>(Arrays.asList(headerArr));
  }

  private static List<Object[]> getFundingFileData(List<DailyFundingReport> list) {
    List<Object[]> fileData = new ArrayList<Object[]>();

    for (DailyFundingReport transaction : list) {

      Object[] rowData = {transaction.getDate(), transaction.getBatchId(),
          Long.parseLong(transaction.getParentMerchantCode()), transaction.getParentBusinessName(),
          transaction.getMerchantCode(), transaction.getBusinessName(), transaction.getCurrency(),
          transaction.getBankAccountNumber(), transaction.getBankRoutingNumber(),
          transaction.getTxnAmount(), transaction.getFeeAmount(),
          transaction.getTotalAmount()

      };
      fileData.add(rowData);
    }

    return fileData;
  }
  
}
