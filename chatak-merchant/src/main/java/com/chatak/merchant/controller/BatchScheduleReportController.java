package com.chatak.merchant.controller;

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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.chatak.merchant.constants.FeatureConstants;
import com.chatak.merchant.constants.URLMappingConstants;
import com.chatak.merchant.controller.model.ExportDetails;
import com.chatak.merchant.controller.model.Option;
import com.chatak.merchant.exception.ChatakMerchantException;
import com.chatak.merchant.model.MerchantData;
import com.chatak.merchant.service.BatchSchedularReportService;
import com.chatak.merchant.service.LoginService;
import com.chatak.merchant.util.ExportUtil;
import com.chatak.merchant.util.PaginationUtil;
import com.chatak.merchant.util.StringUtil;
import com.chatak.pg.acq.dao.model.PGMerchant;
import com.chatak.pg.enums.ExportType;
import com.chatak.pg.model.SettlemetActionDTO;
import com.chatak.pg.user.bean.DailyFundingReport;
import com.chatak.pg.user.bean.GetBatchReportRequest;
import com.chatak.pg.user.bean.GetDailyFundingReportRequest;
import com.chatak.pg.user.bean.GetDailyFundingReportResponse;
import com.chatak.pg.user.bean.GetTransactionsListResponse;
import com.chatak.pg.user.bean.Transaction;
import com.chatak.pg.util.Constants;


@SuppressWarnings({"unchecked", "rawtypes"})
@Controller
public class BatchScheduleReportController implements URLMappingConstants {

  private static Logger logger = Logger.getLogger(BatchScheduleReportController.class);
  
  @Autowired
  BatchSchedularReportService batchSchedularReportService;
  
  @Autowired
  private MessageSource messageSource;
  
  @Autowired
  LoginService loginService;
  
  @RequestMapping(value = CHATAK_MERCHANT_SHOW_BATCH_REPORT, method = RequestMethod.GET)
  public ModelAndView showBatchReport(HttpSession session, Map model, HttpServletRequest request,
      HttpServletResponse response) {
    logger.info("Entering :: BatchScheduleReportController :: showBatchReport method");

    ModelAndView modelAndView = new ModelAndView(CHATAK_MERCHANT_SHOW_BATCH_REPORT);
    model.put(Constants.BATCH_REPORT, new GetBatchReportRequest());
    session.setAttribute(Constants.ERROR, null);
    modelAndView.addObject(Constants.ERROR, null);
    List<Option> merchantList = new ArrayList<>();
    List<Option> subMerchantData = new ArrayList<>();
    Option option = new Option();
    Long userid = (Long) session.getAttribute(Constants.LOGIN_USER_MERCHANT_ID);
    setMerchantCodeAndCompanyName(modelAndView, merchantList, subMerchantData, option, userid);
    modelAndView.addObject("subMerchantList", subMerchantData);
    modelAndView.addObject("flag", false);
    logger.info("Exiting :: BatchScheduleReportController :: showBatchReport method");
    return modelAndView;
  }

  private void setMerchantCodeAndCompanyName(ModelAndView modelAndView, List<Option> merchantList,
	List<Option> subMerchantData, Option option, Long userid) {
	MerchantData merchantData = batchSchedularReportService.getMerchantCodeAndCompanyName(userid);
    List<PGMerchant> subMerchantList = batchSchedularReportService.findById(merchantData.getId());
      option.setValue(merchantData.getMerchantCode());
      option.setLabel(merchantData.getMerchantCode() + " - " + merchantData.getBusinessName());
      merchantList.add(option);
      modelAndView.addObject("merchantList", merchantList);
    for (PGMerchant subMerchant : subMerchantList) {
      option = new Option();
      option.setValue(subMerchant.getMerchantCode());
      option.setLabel(subMerchant.getMerchantCode() + " - " + subMerchant.getBusinessName());
      subMerchantData.add(option);
    }
}
  
  @RequestMapping(value = CHATAK_MERCHANT_GET_BATCH_REPORT, method = RequestMethod.POST)
  public ModelAndView getBatchReport(HttpServletRequest request, HttpServletResponse response,
      GetBatchReportRequest batchReport, BindingResult bindingResult, Map model,
      HttpSession session) {
    logger.info("Entering :: BatchScheduleReportController :: getBatchReport method");

    ModelAndView modelAndView = new ModelAndView(CHATAK_MERCHANT_SHOW_BATCH_REPORT);
    String existingFeature = (String) session.getAttribute(Constants.EXISTING_FEATURES);
    if (!existingFeature.contains(FeatureConstants.MERCHANT_SERVICE_BATCH_REPORT_FEATURE_ID)) {
      session.invalidate();
      modelAndView.setViewName(INVALID_REQUEST_PAGE);
      return modelAndView;
    }
    if (!loginService.checkUserActive(session)) {
      model.put(Constants.ERROR, messageSource.getMessage("user.has.been.inactivated", null,
          LocaleContextHolder.getLocale()));
      session.invalidate();
      return new ModelAndView(CHATAK_MERCHANT_LOG_OUT);
    }
    List<Option> merchantList = new ArrayList<>();
    List<Option> subMerchantData = new ArrayList<>();
    Option option = new Option();
    session.setAttribute(Constants.BATCH_REQUEST_OBJECT, batchReport);
    List<Transaction> transactionList = new ArrayList<>();
    Long userid = (Long) session.getAttribute(Constants.LOGIN_USER_MERCHANT_ID);
    batchReport.setId(userid);
    try {
      GetTransactionsListResponse transactionResponse =
          batchSchedularReportService.searchBatchReportTransactions(batchReport);
      session.setAttribute(Constants.TRANSACTIONS_REPORT, transactionResponse);
      if (!CollectionUtils.isEmpty(transactionResponse.getTransactionList())
          && transactionResponse.getTransactionList() != null) {
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
    } catch (ChatakMerchantException e) {
      logger.error("Error :: BatchScheduleReportController :: getBatchReport method", e);
    }
    setMerchantCodeAndCompanyName(modelAndView, merchantList, subMerchantData, option, userid);
    modelAndView.addObject("subMerchantList", subMerchantData);
    modelAndView.addObject(Constants.BATCH_REPORT, batchReport);
    return modelAndView;
  }
  
	@RequestMapping(value = CHATAK_MERCHANT_GET_BATCH_REPORT, method = RequestMethod.GET)
	public ModelAndView getBatchReportGetMethod(HttpServletRequest request, HttpServletResponse response,
			GetBatchReportRequest batchReport, BindingResult bindingResult, Map model, HttpSession session) {
		logger.info("Entering :: BatchScheduleReportController :: getBatchReport method");
		ModelAndView modelAndView = showBatchReport(session, model, request, response);
		return modelAndView;
	}
  
  @RequestMapping(value = CHATA_MERCHANT_BATCH_TRANSACTION_EXPORT, method = RequestMethod.POST)
  public ModelAndView downloadBatchReport(HttpSession session, Map model,
      HttpServletRequest request, @FormParam("downLoadPageNumber") final Integer downLoadPageNumber,
      HttpServletResponse response, @FormParam("totalRecords") final Integer totalRecords,
      @FormParam("downloadAllRecords") final boolean downloadAllRecords) {
    logger.info("Entering :: BatchScheduleReportController :: downloadBatchReport method");

    String downloadType = request.getParameter("downloadType");
    ModelAndView modelAndView = new ModelAndView(CHATAK_MERCHANT_SHOW_BATCH_REPORT);
    try {
      GetBatchReportRequest batchReport =
          (GetBatchReportRequest) session.getAttribute(Constants.BATCH_REQUEST_OBJECT);
      batchReport.setPageIndex(downLoadPageNumber);
      batchReport.setPageSize(Constants.MAX_ENTITIES_PORTAL_DISPLAY_SIZE);
      if (downloadAllRecords) {
        batchReport.setPageIndex(Constants.ONE);
        batchReport.setPageSize(totalRecords);
      }
      GetTransactionsListResponse transactionsList = 
          batchSchedularReportService.searchBatchReportTransactions(batchReport);
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
    } catch (Exception exp) {
      modelAndView.addObject(Constants.ERROR, messageSource
          .getMessage(Constants.CHATAK_GENERAL_ERROR, null, LocaleContextHolder.getLocale()));
      logger.error("ERROR :: BatchScheduleReportController :: downloadBatchReport method", exp);
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
  
  @RequestMapping(value = CHATAK_MERCHNAT_BATCH_TRANSACTION_PAGINATION, method = RequestMethod.POST)
  public ModelAndView getBatchReportPagination(final HttpSession session,
      @FormParam(Constants.PAGE_NUMBER) final Integer pageNumber,
      @FormParam(Constants.TOTAL_RECORDS) final Integer totalRecords, Map model) {
    logger.info("Entering :: BatchScheduleReportController :: getBatchReportPagination method");

    ModelAndView modelAndView = new ModelAndView(CHATAK_MERCHANT_SHOW_BATCH_REPORT);
    try {

      GetBatchReportRequest batchReport =
          (GetBatchReportRequest) session.getAttribute(Constants.BATCH_REQUEST_OBJECT);
      model.put(Constants.BATCH_REPORT, batchReport);
      session.setAttribute(Constants.BATCH_REQUEST_OBJECT, batchReport);
      batchReport.setPageIndex(pageNumber);
      batchReport.setNoOfRecords(totalRecords);
      batchReport.setPageSize(Constants.MAX_TRANSACTION_ENTITY_DISPLAY_SIZE);


      GetTransactionsListResponse transactionsList =
          batchSchedularReportService.searchBatchReportTransactions(batchReport);
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
          messageSource.getMessage("chatak.general.error", null, LocaleContextHolder.getLocale()));
      logger.error("ERROR :: BatchScheduleReportController :: getBatchReportPagination method", e);
    }
    modelAndView.addObject("settlementDto", new SettlemetActionDTO());
    List<Option> merchantList = new ArrayList<>();
    List<Option> subMerchantData = new ArrayList<>();
    Option option = new Option();
    Long userid = (Long) session.getAttribute(Constants.LOGIN_USER_MERCHANT_ID);
    setMerchantCodeAndCompanyName(modelAndView, merchantList, subMerchantData, option, userid);
    modelAndView.addObject("subMerchantList", subMerchantData);

    logger.info("Exiting :: BatchScheduleReportController :: getBatchReportPagination method");
    return modelAndView;
  }
  
  @RequestMapping(value = CHATAK_MERCHANT_SHOW_DAILY_FUNDING_REPORT, method = RequestMethod.GET)
  public ModelAndView showDailyFundingReport(HttpSession session, Map model,
      HttpServletRequest request, HttpServletResponse response) {
    logger.info("Entering :: BatchScheduleReportController :: showDailyFundingReport method");
    
    ModelAndView modelAndView = new ModelAndView(CHATAK_MERCHANT_SHOW_DAILY_FUNDING_REPORT);
    String existingFeature = (String) session.getAttribute(Constants.EXISTING_FEATURES);
    if (!existingFeature.contains(FeatureConstants.MERCHANT_SERVICE_FUNDING_REPORT_FEATURE_ID)) {
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
  
  @RequestMapping(value = CHATAK_MERCHANT_GET_DAILY_FUNDING_REPORT, method = RequestMethod.POST)
  public ModelAndView getDailyFundingReport(HttpServletRequest request,
      HttpServletResponse response, GetDailyFundingReportRequest dailyFundingReport,
      BindingResult bindingResult, Map model, HttpSession session) {
    logger.info("Entering :: BatchScheduleReportController :: getDailyFundingReport method");

    ModelAndView modelAndView = new ModelAndView(CHATAK_MERCHANT_SHOW_DAILY_FUNDING_REPORT);
    String existingFeature = (String) session.getAttribute(Constants.EXISTING_FEATURES);
    if (!loginService.checkUserActive(session)) {
      model.put(Constants.ERROR, messageSource.getMessage("user.has.been.inactivated", null,
          LocaleContextHolder.getLocale()));
      session.invalidate();
      return new ModelAndView(CHATAK_MERCHANT_LOG_OUT);
    }
    if (!existingFeature.contains(FeatureConstants.MERCHANT_SERVICE_FUNDING_REPORT_FEATURE_ID)) {
      session.invalidate();
      modelAndView.setViewName(INVALID_REQUEST_PAGE);
      return modelAndView;
    }
    Long userid = (Long) session.getAttribute(Constants.LOGIN_USER_MERCHANT_ID);
    String userType = (String) session.getAttribute("userType");
    session.setAttribute(Constants.DAILY_FUNDING_REQUEST_OBJECT, dailyFundingReport);
    dailyFundingReport.setId(userid);
    dailyFundingReport.setPageIndex(Constants.ONE);
    dailyFundingReport.setPageSize(Constants.MAX_ENTITIES_PORTAL_DISPLAY_SIZE);
    List<DailyFundingReport> list = new ArrayList<>();
    try {
      GetDailyFundingReportResponse getReportResponse =
          batchSchedularReportService.searchDailyFundingReportDetails(dailyFundingReport, userType);
      if (getReportResponse.getDailyFundingReport() != null
          && !CollectionUtils.isEmpty(getReportResponse.getDailyFundingReport())) {
        list = getReportResponse.getDailyFundingReport();
        modelAndView.addObject(Constants.TRANSACTIONS_MODEL, list);
        modelAndView = PaginationUtil.getPagenationModel(modelAndView,
            getReportResponse.getTotalResultCount());
        modelAndView.addObject(Constants.TRANSACTIONS_MODEL, list);
      }
      modelAndView.addObject(Constants.TOTAL_RECORDS, getReportResponse.getTotalResultCount());
    } catch (ChatakMerchantException e) {
      logger.error("Error :: BatchScheduleReportController :: getDailyFundingReport method", e);
    }
    modelAndView.addObject("dailyFundingReport", dailyFundingReport);

    logger.info("Exiting :: BatchScheduleReportController :: getDailyFundingReport method");
    return modelAndView;
  }
  
	@RequestMapping(value = CHATAK_MERCHANT_GET_DAILY_FUNDING_REPORT, method = RequestMethod.GET)
	public ModelAndView getDailyFundingReportGetMethod(HttpServletRequest request, HttpServletResponse response,
			GetDailyFundingReportRequest dailyFundingReport, BindingResult bindingResult, Map model,
			HttpSession session) {
		logger.info("Entering :: BatchScheduleReportController :: getDailyFundingReport method");
		ModelAndView modelAndView = showDailyFundingReport(session, model, request, response);
		logger.info("Exiting :: BatchScheduleReportController :: getDailyFundingReport method");
		return modelAndView;
	}
  
  @RequestMapping(value = CHATA_MERCHANT_FUNDING_REPORT_EXPORT, method = RequestMethod.POST)
  public ModelAndView downloadFundingReport(HttpSession session, Map model,
      HttpServletRequest request, @FormParam("downLoadPageNumber") final Integer downLoadPageNumber,
      HttpServletResponse response, @FormParam("totalRecords") final Integer totalRecords,
      @FormParam("downloadAllRecords") final boolean downloadAllRecords) {
    logger.info("Entering :: BatchScheduleReportController :: downloadFundingReport method");

    String downloadType = request.getParameter("downloadType");
    ModelAndView modelAndView = new ModelAndView(CHATAK_MERCHANT_SHOW_DAILY_FUNDING_REPORT);
    try {
      GetDailyFundingReportRequest batchReport = (GetDailyFundingReportRequest) session
          .getAttribute(Constants.DAILY_FUNDING_REQUEST_OBJECT);
      String usertype = (String) session.getAttribute("userType");
      batchReport.setPageIndex(downLoadPageNumber);
      batchReport.setPageSize(Constants.MAX_ENTITIES_PORTAL_DISPLAY_SIZE);
      if (downloadAllRecords) {
        batchReport.setPageIndex(Constants.ONE);
        batchReport.setPageSize(totalRecords);
      }
      Long userid = (Long) session.getAttribute(Constants.LOGIN_USER_MERCHANT_ID);
      batchReport.setId(userid);
      GetDailyFundingReportResponse getReportResponse =
          batchSchedularReportService.searchDailyFundingReportDetails(batchReport, usertype);
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
    } catch (Exception e) {
      modelAndView.addObject(Constants.ERROR, messageSource
          .getMessage(Constants.CHATAK_GENERAL_ERROR, null, LocaleContextHolder.getLocale()));
      logger.error("ERROR :: BatchScheduleReportController :: downloadFundingReport method", e);
    }
    logger.info("Exiting :: BatchScheduleReportController :: downloadFundingReport method");
    return null;
  }
  
  private void setExportDetailsDataForDownloadFundingReport(List<DailyFundingReport> roleList,
      ExportDetails exportDetails) {
    exportDetails.setReportName("Daily_Funding_Report_");
    exportDetails.setHeaderMessageProperty("chatak-report-lable-daily-funding-report");

    exportDetails.setHeaderList(getFundingHeaderList());
    exportDetails.setFileData(getFundingFileData(roleList));
  }
  
  @RequestMapping(value = CHATAK_MERCHANT_FUNDING_REPORT_PAGINATION, method = RequestMethod.POST)
  public ModelAndView getFundingReportPagination(final HttpSession session,
      @FormParam(Constants.PAGE_NUMBER) final Integer pageNumber,
      @FormParam(Constants.TOTAL_RECORDS) final Integer totalRecords, Map model) {
    logger.info("Entering :: BatchScheduleReportController :: getFundingReportPagination method");

    ModelAndView modelAndView = new ModelAndView(CHATAK_MERCHANT_SHOW_DAILY_FUNDING_REPORT);
    try {

      GetDailyFundingReportRequest batchReport = (GetDailyFundingReportRequest) session
          .getAttribute(Constants.DAILY_FUNDING_REQUEST_OBJECT);
      String userType = (String) session.getAttribute("userType");
      model.put(Constants.FUNDING_REPORT, batchReport);
      batchReport.setPageIndex(pageNumber);
      batchReport.setNoOfRecords(totalRecords);
      batchReport.setPageSize(Constants.MAX_TRANSACTION_ENTITY_DISPLAY_SIZE);
      Long userid = (Long) session.getAttribute(Constants.LOGIN_USER_MERCHANT_ID);
      batchReport.setId(userid);
      GetDailyFundingReportResponse getReportResponse =
          batchSchedularReportService.searchDailyFundingReportDetails(batchReport, userType);
      session.setAttribute(Constants.TRANSACTIONS_REPORT,
          getReportResponse.getDailyFundingReport());

      if (getReportResponse.getDailyFundingReport() != null
          && !CollectionUtils.isEmpty(getReportResponse.getDailyFundingReport())) {
        modelAndView = PaginationUtil.getPagenationModelSuccessive(modelAndView, pageNumber,
            getReportResponse.getTotalResultCount());
      }
      if (null != getReportResponse.getDailyFundingReport()) {
        modelAndView.addObject(Constants.TRANSACTIONS_MODEL,
            getReportResponse.getDailyFundingReport());
      }
    } catch (Exception exp) {
      modelAndView.addObject(Constants.ERROR,
          messageSource.getMessage("chatak.general.error", null, LocaleContextHolder.getLocale()));
      logger.error("ERROR :: BatchScheduleReportController :: getFundingReportPagination method",
          exp);
    }
    modelAndView.addObject("settlementDto", new SettlemetActionDTO());

    logger.info("Exiting :: BatchScheduleReportController :: getFundingReportPagination method");
    return modelAndView;
  }
  
  private List<String> getBatchHeaderList() {
    String[] headerArr = {
        messageSource.getMessage("dash-board.label.transactiontime", null,
            LocaleContextHolder.getLocale()),
        messageSource.getMessage("merchant.common-deviceLocalTxnTime", null,
            LocaleContextHolder.getLocale()),
        messageSource.getMessage("merchant-batch-report.transaction.id", null,
            LocaleContextHolder.getLocale()),
        messageSource.getMessage(
            "reports.label.balancereports.manualtransactions.merchantorsubmerchantcode", null,
            LocaleContextHolder.getLocale()),
        messageSource.getMessage("transaction-report-batchID", null,
                LocaleContextHolder.getLocale()),
        messageSource.getMessage("transactions-search.label.cardnumber", null,
                LocaleContextHolder.getLocale()),
        messageSource.getMessage("processing-transaction-details.label.description", null,
            LocaleContextHolder.getLocale()),
        messageSource.getMessage("fileExportUtil.currency", null, LocaleContextHolder.getLocale()),
        messageSource.getMessage("recurring-search.label.amount", null,
            LocaleContextHolder.getLocale()),
        messageSource.getMessage("transactionFileExportUtil.txn.type", null,
            LocaleContextHolder.getLocale()),
        messageSource.getMessage("merchant-batch-report.status", null, LocaleContextHolder.getLocale())
        };
    return new ArrayList<String>(Arrays.asList(headerArr));
  }

	private static List<Object[]> getBatchFileData(List<Transaction> list) {
		List<Object[]> fileData = new ArrayList<Object[]>();

		for (Transaction transaction : list) {
			if (!"".equals(transaction.getTimeZoneOffset()) && null != transaction.getTimeZoneOffset()) {
				transaction.setTimeZoneOffset("(" + transaction.getTimeZoneOffset() + ")");
			}
			Object[] rowData = { transaction.getTransactionDate(),
					transaction.getDeviceLocalTxnTime() + transaction.getTimeZoneOffset(),
					Long.parseLong(transaction.getTransactionId()), Long.parseLong(transaction.getMerchant_code()),
					transaction.getBatchId(), Long.parseLong(transaction.getMaskCardNumber()),
					transaction.getTxnDescription(), transaction.getLocalCurrency(),
					transaction.getTxn_total_amount() / Double.parseDouble("100"), transaction.getTransaction_type(),
					transaction.getMerchantSettlementStatus() };
			fileData.add(rowData);
		}
		return fileData;
	}
  
  private List<String> getFundingHeaderList() {
    String[] headerArr = {
        messageSource.getMessage("transactions-search.label.dateortime", null,
            LocaleContextHolder.getLocale()),
        messageSource.getMessage("transaction-report-batchID", null,
            LocaleContextHolder.getLocale()),
        messageSource.getMessage("myprofile.label.merchantcode", null,
            LocaleContextHolder.getLocale()),
        messageSource.getMessage("search-sub-merchant.label.merchantcompanyname", null,
            LocaleContextHolder.getLocale()),
        messageSource.getMessage("search-sub-merchant.label.submerchantcode", null,
            LocaleContextHolder.getLocale()),
        messageSource.getMessage(
            "sub-merchant-account-search.label.sub-merchantname", null,
            LocaleContextHolder.getLocale()),
        messageSource.getMessage("reports.label.balancereports.manualtransactions.currency", null,
            LocaleContextHolder.getLocale()),
        messageSource.getMessage("sub-merchant-create.label.bankaccountnumber", null,
            LocaleContextHolder.getLocale()),
        messageSource.getMessage("sub-merchant-create.label.bankroutingnumber", null, LocaleContextHolder.getLocale()),
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

      Object[] rowData =
          {transaction.getDate(), transaction.getBatchId(), Long.parseLong(transaction.getParentMerchantCode()),
              transaction.getParentBusinessName(), transaction.getMerchantCode(),
              transaction.getBusinessName(), transaction.getCurrency(),
              (transaction.getBankAccountNumber() == null
                  || "".equals(transaction.getBankAccountNumber())) ? transaction.getBankAccountNumber()
                      : Long.parseLong(transaction.getBankAccountNumber()),
              (transaction.getBankRoutingNumber() == null
                  || "".equals(transaction.getBankRoutingNumber())) ? transaction.getBankRoutingNumber()
                      : Long.parseLong(transaction.getBankRoutingNumber()),
              transaction.getTxnAmount(), transaction.getFeeAmount(),
              transaction.getTotalAmount()

          };
      fileData.add(rowData);
    }

    return fileData;
  }
}
