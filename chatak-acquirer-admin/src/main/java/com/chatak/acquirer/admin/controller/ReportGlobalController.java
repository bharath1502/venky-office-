package com.chatak.acquirer.admin.controller;

import java.util.ArrayList;
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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.chatak.acquirer.admin.constants.FeatureConstants;
import com.chatak.acquirer.admin.constants.URLMappingConstants;
import com.chatak.acquirer.admin.exception.ChatakAdminException;
import com.chatak.acquirer.admin.model.MerchantData;
import com.chatak.acquirer.admin.model.MerchantSearchResponse;
import com.chatak.acquirer.admin.service.FundTransfersService;
import com.chatak.acquirer.admin.service.MerchantAccountService;
import com.chatak.acquirer.admin.service.MerchantUpdateService;
import com.chatak.acquirer.admin.service.MerchantValidateService;
import com.chatak.acquirer.admin.service.TransactionService;
import com.chatak.acquirer.admin.service.UserService;
import com.chatak.acquirer.admin.util.JsonUtil;
import com.chatak.acquirer.admin.util.PaginationUtil;
import com.chatak.acquirer.admin.util.SystemRevenueReportFileExportUtil;
import com.chatak.acquirer.admin.util.UserTxnReportFileExportUtil;
import com.chatak.pg.constants.PGConstants;
import com.chatak.pg.model.AccessLogsDTO;
import com.chatak.pg.model.GetTransactionIdsListResponse;
import com.chatak.pg.model.Merchant;
import com.chatak.pg.model.ReportsDTO;
import com.chatak.pg.user.bean.GetTransactionsListRequest;
import com.chatak.pg.user.bean.GetTransactionsListResponse;
import com.chatak.pg.user.bean.Transaction;
import com.chatak.pg.util.Constants;
import com.chatak.pg.util.DateUtil;
import com.chatak.pg.util.StringUtils;

@SuppressWarnings({"rawtypes", "unchecked"})
@Controller
public class ReportGlobalController implements URLMappingConstants {

  private static Logger logger = Logger.getLogger(ReportGlobalController.class);

  @Autowired
  MessageSource messageSource;

  @Autowired
  private MerchantAccountService merchantAccountService;

  @Autowired
  private TransactionService transactionService;

  @Autowired
  private UserService userService;

  @Autowired
  private FundTransfersService fundTransfersService;

  @Autowired
  private MerchantUpdateService merchantUpdateService;

  @Autowired
  private MerchantValidateService merchantValidateService;

  @RequestMapping(value = ALL_ACCOUNTS_EXECUTED_TRANS_DATE_SHOW, method = RequestMethod.GET)
  public ModelAndView showAllAccExecutedTranDate(HttpServletRequest request,
      HttpServletResponse response, Merchant merchant, BindingResult bindingResult, Map model,
      HttpSession session) {
    logger.info("Entering:: ReportsController:: showSpecificUserAllTransReports method");
    ModelAndView modelAndView = new ModelAndView(ALL_ACCOUNTS_EXECUTED_TRANSACTIONS_SHOW);
    String existingFeature = (String) session.getAttribute(Constants.EXISTING_FEATURES);
    String userType = (String) session.getAttribute(Constants.LOGIN_USER_TYPE);
    if (userType.equalsIgnoreCase(Constants.ADMIN_VALUE)) {
      if (!existingFeature.contains(FeatureConstants.ADMIN_SERVICE_REPORTS_FEATURE_ID)) {
        return validateExistingFeature(session, modelAndView);
      }
    } else if (userType.equalsIgnoreCase(Constants.RESELLER)&& !existingFeature.contains(FeatureConstants.RESELLER_SERVICE_REPORTS_FEATURE_ID)) {
      
        return validateExistingFeature(session, modelAndView);
    }
    setListOfReportsDTO(session, modelAndView);
    logger.info("Exiting:: ReportsController:: showSpecificUserAllTransReports method");
    return modelAndView;
  }

  private void setListOfReportsDTO(HttpSession session, ModelAndView modelAndView) {
	List<ReportsDTO> executedTransactionsReportList = new ArrayList<>();
    session.setAttribute(Constants.EXECUTED_TRANSACTIONS_REPORT_LIST, executedTransactionsReportList);
    modelAndView.addObject(Constants.TRANSACTION_DIV, Boolean.FALSE);
    modelAndView.addObject(executedTransactionsReportList);
    modelAndView.addObject(Constants.MERCHANT, new Merchant());
  }

  private ModelAndView validateExistingFeature(HttpSession session, ModelAndView modelAndView) {
	session.invalidate();
	modelAndView.setViewName(INVALID_REQUEST_PAGE);
	return modelAndView;
  }

  @RequestMapping(value = ALL_ACCOUNTS_EXECUTED_TRANSACTIONS_SHOW, method = RequestMethod.GET)
  public ModelAndView showAllAccountsExecutedTransactions(HttpServletRequest request,
      HttpServletResponse response, Merchant merchant, BindingResult bindingResult, Map model,
      @FormParam("toDate") final String toDate,
      HttpSession session) {
    String fromDate=request.getParameter("fromDate");
    logger.info("Entering:: ReportsController:: showSpecificUserAllTransReports method");
    String existingFeature = (String) session.getAttribute(Constants.EXISTING_FEATURES);
    ModelAndView modelAndView = new ModelAndView(ALL_ACCOUNTS_EXECUTED_TRANSACTIONS_SHOW);
    GetTransactionsListRequest transactionsListRequest = new GetTransactionsListRequest();
    String userType = (String) session.getAttribute(Constants.LOGIN_USER_TYPE);
    if (userType.equalsIgnoreCase(Constants.ADMIN_VALUE)) {
      if (!existingFeature.contains(FeatureConstants.ADMIN_SERVICE_REPORTS_FEATURE_ID)) {
        return validateExistingFeature(session, modelAndView);
      }
    } else if (userType.equalsIgnoreCase(Constants.RESELLER) &&!existingFeature.contains(FeatureConstants.RESELLER_SERVICE_REPORTS_FEATURE_ID) ) {
        return validateExistingFeature(session, modelAndView);
      }
    
    setMerchantData(merchant, session, modelAndView);
    transactionsListRequest.setFrom_date(fromDate);
    transactionsListRequest.setTo_date(toDate);
    transactionsListRequest.setSettlementStatus(Constants.EXECUTED_STATUS);
    try {
      List<ReportsDTO> executedTransactionsReportList =
          transactionService.getAllAccountsExecutedTransactionsOnDate(transactionsListRequest);
      if (null != executedTransactionsReportList) {
        session.setAttribute(Constants.EXECUTED_TRANSACTIONS_REPORT_LIST, executedTransactionsReportList);
        modelAndView.addObject(Constants.TRANSACTION_DIV, Boolean.TRUE);

        for (ReportsDTO reportsDTO : executedTransactionsReportList) {
          reportsDTO.setTxnJsonString(JsonUtil.convertObjectToJSON(reportsDTO.getTxnPopupDto()));
        }

        modelAndView.addObject(executedTransactionsReportList);
      } else {
        modelAndView.addObject(Constants.ERROR, messageSource.getMessage(
            "chatak.admin.transactions.error.message", null, LocaleContextHolder.getLocale()));
      }
    } catch (Exception e) {
      modelAndView.addObject(Constants.ERROR,
          messageSource.getMessage(Constants.CHATAK_GENERAL_ERROR, null, LocaleContextHolder.getLocale()));
      logger.error("ERROR:: ReportsController:: showSpecificUserAllTransReports method", e);
    }
    modelAndView.addObject(Constants.MERCHANT, new Merchant());
    logger.info("Exiting:: ReportsController:: showSpecificUserAllTransReports method");
    return modelAndView;
  }

  private void setMerchantData(Merchant merchant, HttpSession session, ModelAndView modelAndView) {
	modelAndView.addObject(Constants.ERROR, null);
    session.setAttribute(Constants.ERROR, null);
    session.setAttribute(Constants.MERCHANTS_MODEL, merchant);
    merchant.setPageSize(Constants.MAX_ENTITIES_PORTAL_DISPLAY_SIZE);
    merchant.setPageIndex(Constants.ONE);
  }

  @RequestMapping(value = SHOW_STATEMENT_TRANSACTION_DETAILS, method = RequestMethod.POST)
  public ModelAndView showStatementAccTransdetails(HttpServletRequest request,
      HttpServletResponse response, @FormParam("getMerchantId") final Long getMerchantId,
      @FormParam("getMerchantCode") final String getMerchantCode,
      @FormParam("fromDate") final String fromDate,
      HttpSession session, Map model) {
    String toDate=request.getParameter("toDate");
    logger.info("Entering :: ReportsController :: showIndividualUserAccdetails method ");
    ModelAndView modelAndView = new ModelAndView(SHOW_INDIVIDUAL_STATEMENT_ACC_DETAILS);
    String userType = (String) session.getAttribute(Constants.LOGIN_USER_TYPE);
    String existingFeature = (String) session.getAttribute(Constants.EXISTING_FEATURES);
    if (userType.equalsIgnoreCase(Constants.ADMIN_VALUE)) {
      if (!existingFeature.contains(FeatureConstants.ADMIN_SERVICE_REPORTS_FEATURE_ID)) {
        return validateExistingFeature(session, modelAndView);
      }
    } else if (userType.equalsIgnoreCase(Constants.RESELLER)&& !existingFeature.contains(FeatureConstants.RESELLER_SERVICE_REPORTS_FEATURE_ID)) {
    
        return validateExistingFeature(session, modelAndView);
     
    }
    Merchant merchant = new Merchant();
    GetTransactionsListRequest transactionsListRequest = new GetTransactionsListRequest();
    modelAndView.addObject(Constants.ERROR, null);
    modelAndView.addObject(Constants.SUCESS, null);
    try {
      merchant.setId(getMerchantId);
      transactionsListRequest.setMerchant_code(getMerchantCode);
      transactionsListRequest.setFrom_date(fromDate);
      transactionsListRequest.setTo_date(toDate);
      transactionsListRequest.setSettlementStatus(Constants.EXECUTED_STATUS);
      merchant = merchantValidateService.getMerchant(merchant);
      Merchant merchantAccount = merchantAccountService.getMerchantAccountDetails(getMerchantCode);
      merchant.setCreatedDate(merchantAccount.getCreatedDate().toString());
      GetTransactionsListResponse transactionsList =
          transactionService.searchTransactionsReport(transactionsListRequest);
      session.setAttribute("specificUserStatementList", transactionsList.getTransactionList());
      merchant.setCreatedDate(merchantAccount.getCreatedDate().toString());
      merchant.setTransactionDiv(true);
      modelAndView.addObject(Constants.MERCHANT, merchant);
      modelAndView.addObject("merchantAccount", merchantAccount);
      session.setAttribute("transactionsList", transactionsList.getTransactionList());
    } catch (ChatakAdminException e) {
      modelAndView.addObject(Constants.ERROR,
          messageSource.getMessage(Constants.CHATAK_GENERAL_ERROR, null, LocaleContextHolder.getLocale()));
      logger.error("ERROR:: ReportsController:: showIndividualUserAccdetails ChatakAdminException", e);
    } catch (Exception exp) {
      modelAndView.addObject(Constants.ERROR,
          messageSource.getMessage(Constants.CHATAK_GENERAL_ERROR, null, LocaleContextHolder.getLocale()));
      logger.error("ERROR:: ReportsController:: showIndividualUserAccdetails Exception", exp);
    }
    logger.info("EXITING :: ReportsController :: showIndividualUserAccdetails");
    return modelAndView;
  }

  @RequestMapping(value = DOWNLOAD_SPECIFIC_USER_STATEMENTLIST_REPORT, method = RequestMethod.POST)
  public ModelAndView downloadSpecificUserStatementListReport(HttpSession session, Map model,
      HttpServletRequest request, @FormParam("downLoadPageNumber") final Integer downLoadPageNumber,
      @FormParam("downloadType") final String downloadType, HttpServletResponse response) {
    logger.info("Entering:: ReportsController:: downloadSpecificUserTransactionReport method");
    ModelAndView modelAndView = new ModelAndView(SHOW_INDIVIDUAL_USER_ACCOUNT_DETAILS);
    Merchant merchant = new Merchant();
    List<Transaction> userTransList =
        (List<Transaction>) session.getAttribute("specificUserStatementList");
    try {
      if (Constants.PDF_FILE_FORMAT.equalsIgnoreCase(downloadType)) {

        UserTxnReportFileExportUtil.downloadSpecificUserStatementPdf(userTransList, response,
            messageSource.getMessage("specific.user.statement.report.heading", null,
                LocaleContextHolder.getLocale()),
            messageSource);

      } else if (Constants.XLS_FILE_FORMAT.equalsIgnoreCase(downloadType)) {

        UserTxnReportFileExportUtil.downloadSpecificUserStatementXl(userTransList, response,
            messageSource.getMessage("specific.user.statement.report.heading", null,
                LocaleContextHolder.getLocale()),
            messageSource);

      }
    } catch (Exception e) {
      modelAndView.addObject(Constants.ERROR,
          messageSource.getMessage(Constants.CHATAK_GENERAL_ERROR, null, LocaleContextHolder.getLocale()));
      logger.error("ERROR:: ReportsController:: downloadSpecificUserTransactionReport method", e);
    }
    logger.info("Exiting:: ReportsController:: downloadSpecificUserTransactionReport method");
    modelAndView.addObject("merchants", merchant);
    return null;
  }

  @RequestMapping(value = SPECIFIC_STATEMENT_REPORT_PAGINATION, method = RequestMethod.POST)
  public ModelAndView getStatementPaginationList(final HttpSession session,
      @FormParam("pageNumber") final Integer pageNumber,
      @FormParam("totalRecords") final Integer totalRecords, Map model) {
    logger.info("Entering:: MerchantController:: getStatementPaginationList method");
    ModelAndView modelAndView = new ModelAndView(SPECIFIC_USER_STATEMENT_REPORTS_SHOW);
    Merchant merchant = new Merchant();
    try {
      merchant.setPageIndex(pageNumber);
      merchant.setNoOfRecords(totalRecords);
      merchant.setPageSize(Constants.MAX_ENTITIES_PORTAL_DISPLAY_SIZE);
      modelAndView = paginationReport(pageNumber, modelAndView, merchant);
    } catch (Exception e) {
      logger.error("ERROR:: MerchantController:: getStatementPaginationList method", e);
      modelAndView.addObject(Constants.ERROR,
          messageSource.getMessage(Constants.CHATAK_GENERAL_ERROR, null, LocaleContextHolder.getLocale()));
    }
    logger.info("Exiting:: MerchantController:: getStatementPaginationList method");
    return modelAndView;
  }

  /**
   * @param pageNumber
   * @param modelAndView
   * @param merchant
   * @return
   */
  private ModelAndView paginationReport(final Integer pageNumber, ModelAndView modelAndView,
      Merchant merchant) {
    try {
      MerchantSearchResponse searchResponse = merchantUpdateService.searchAllMerchant(merchant);
      List<MerchantData> merchants = new ArrayList<>();
      if (searchResponse != null && !CollectionUtils.isEmpty(searchResponse.getMerchants())) {
        merchants = searchResponse.getMerchants();
        for (MerchantData merchantData : merchants) {
          merchantData.setCreatedDateString(DateUtil
              .toDateStringFormat(merchantData.getCreatedDate(), DateUtil.VIEW_DATE_TIME_FORMAT));
        }
        PaginationUtil.getPagenationModelSuccessive(modelAndView, pageNumber,
            searchResponse.getTotalNoOfRows());
      }
      modelAndView.addObject(Constants.MERCHANTS_MODEL, merchants);
    } catch (Exception e) {
      modelAndView.addObject(Constants.ERROR, messageSource.getMessage(Constants.CHATAK_GENERAL_ERROR,
          null, LocaleContextHolder.getLocale()));
      logger.error("ERROR:: MerchantController:: getStatementPaginationList method", e);
    }
    return modelAndView;
  }

  @RequestMapping(value = DOWNLOAD_REVENUE_GENERATED_REPORT, method = RequestMethod.POST)
  public ModelAndView downloadRevenueGeneratedReport(HttpSession session, Map model,
      HttpServletRequest request, @FormParam("downLoadPageNumber") final Integer downLoadPageNumber,
      @FormParam("downloadType") final String downloadType, HttpServletResponse response) {
    logger.info("Entering:: ReportsController:: downloadRevenueGeneratedReport method");
    ModelAndView modelAndView = new ModelAndView(SHOW_GLOBAL_REVENUE_GENERATED_REPORT);
    List<ReportsDTO> revenueGeneratedList =
        (List<ReportsDTO>) session.getAttribute(Constants.REVENUE_GENERATED_REPORT_LIST);
    try {
      if (Constants.PDF_FILE_FORMAT.equalsIgnoreCase(downloadType)) {
        SystemRevenueReportFileExportUtil.downloadRevenueGeneratedPdf(revenueGeneratedList, response,
            messageSource);
      } else if (Constants.XLS_FILE_FORMAT.equalsIgnoreCase(downloadType)) {
        SystemRevenueReportFileExportUtil.downloadRevenueGeneratedXl(revenueGeneratedList, response,
            messageSource);
      }
    } catch (Exception e) {
      modelAndView.addObject(Constants.ERROR,
          messageSource.getMessage(Constants.CHATAK_GENERAL_ERROR, null, LocaleContextHolder.getLocale()));
      logger.error("ERROR:: ReportsController:: downloadRevenueGeneratedReport method", e);
    }
    logger.info("Exiting:: ReportsController:: downloadRevenueGeneratedReport method");
    return null;
  }

  @RequestMapping(value = GLOBAL_ACCESS_LOG_REPORT, method = RequestMethod.GET)
  public ModelAndView showGlobalAccessLogReport(HttpServletRequest request,
      HttpServletResponse response, Merchant merchant, BindingResult bindingResult, Map model,
      HttpSession session) {
    logger.info("Entering:: ReportsController:: showGlobalAccessLogReport method");
    ModelAndView modelAndView = new ModelAndView(SHOW_GLOBAL_ACCESS_LOG_REPORT);
    String userType = (String) session.getAttribute(Constants.LOGIN_USER_TYPE);
    String existingFeature = (String) session.getAttribute(Constants.EXISTING_FEATURES);
    if (userType.equalsIgnoreCase(Constants.ADMIN_VALUE)) {
      if (!existingFeature.contains(FeatureConstants.ADMIN_SERVICE_REPORTS_FEATURE_ID)) {
        return validateExistingFeature(session, modelAndView);
      }
    } else if (userType.equalsIgnoreCase(Constants.RESELLER)&& !existingFeature.contains(FeatureConstants.RESELLER_SERVICE_REPORTS_FEATURE_ID)) {
    
        return validateExistingFeature(session, modelAndView);
      }
   
    try {
      List<AccessLogsDTO> accessLogs = userService.getAllPgUserActivityLogs();
      if (null != accessLogs) {
        session.setAttribute("accessLogs", accessLogs);
        modelAndView.addObject("accessLogs", accessLogs);
      }
    } catch (Exception e) {
      modelAndView.addObject(Constants.ERROR,
          messageSource.getMessage(Constants.CHATAK_GENERAL_ERROR, null, LocaleContextHolder.getLocale()));
      logger.error("ERROR:: ReportsController:: showGlobalAccessLogReport method", e);
    }
    logger.info("Exiting:: ReportsController:: showGlobalAccessLogReport method");
    return modelAndView;
  }

  @RequestMapping(value = CHATAK_EFT_FETCH_TRAN_ID, method = RequestMethod.GET)
  public @ResponseBody String fetchTransactionIdsListBytransferId(Map model,
      HttpServletRequest request, HttpServletResponse response, HttpSession session) {
    ModelAndView modelAndView = new ModelAndView(SHOW_CHATAK_ADMIN_SPECIFIC_USER_EFT_TRANSFERS);
    modelAndView.addObject(Constants.ERROR, null);
    logger.info("Entering :: ReportsController :: fetchTransactionIdsListBytransferId method");
    String refId = request.getParameter("refId");
    GetTransactionIdsListResponse gettransactionIdsListResponse;
    try {
      gettransactionIdsListResponse = fundTransfersService.getTransactionIdListOnTransferId(refId);
      if (gettransactionIdsListResponse != null) {
        return JsonUtil.convertObjectToJSON(gettransactionIdsListResponse);
      }
    } catch (Exception e) {
      modelAndView.addObject(Constants.ERROR,
          messageSource.getMessage(Constants.CHATAK_GENERAL_ERROR, null, LocaleContextHolder.getLocale()));
      logger.error("ERROR :: ReportsController :: fetchTransactionIdsListBytransferId method:" + e);
    }
    logger.info("Exiting :: ReportsController :: fetchTransactionIdsListBytransferId method");
    return refId;
  }

  @RequestMapping(value = GLOBAL_PENDING_TRANSACTION_REPORT, method = RequestMethod.POST)
  public ModelAndView showAllPendingTransactions(HttpServletRequest request,
      HttpServletResponse response, GetTransactionsListRequest getTransactionsListRequest,
      Merchant merchant, BindingResult bindingResult, Map model, HttpSession session) {
    logger.info("Entering:: ReportsController:: showAllPendingTransactions method");
    ModelAndView modelAndView = new ModelAndView(SHOW_GLOBAL_PENDING_TRANS_REPORT);
    GetTransactionsListRequest transactionsListRequest = new GetTransactionsListRequest();
    List<ReportsDTO> pendingTransactionsReportList = null;
    String userType = (String) session.getAttribute(Constants.LOGIN_USER_TYPE);
    String existingFeature = (String) session.getAttribute(Constants.EXISTING_FEATURES);
    if (userType.equalsIgnoreCase(Constants.ADMIN_VALUE)) {
      if (!existingFeature.contains(FeatureConstants.ADMIN_SERVICE_REPORTS_FEATURE_ID)) {
        return validateExistingFeature(session, modelAndView);
      }
    } else if (userType.equalsIgnoreCase(Constants.RESELLER)&& !existingFeature.contains(FeatureConstants.RESELLER_SERVICE_REPORTS_FEATURE_ID)) {
     
        return validateExistingFeature(session, modelAndView);
      }
   
    setMerchantData(merchant, session, modelAndView);
    session.setAttribute(Constants.ALL_PENDING_TRANSACTION, transactionsListRequest);
    if (null != getTransactionsListRequest.getFrom_date()
        && null != getTransactionsListRequest.getTo_date()) {
      transactionsListRequest.setFrom_date(getTransactionsListRequest.getFrom_date());
      transactionsListRequest.setTo_date(getTransactionsListRequest.getTo_date());
    }
    session.setAttribute("transactionsListRequest", transactionsListRequest);
    transactionsListRequest.setPageIndex(Constants.ONE);
    transactionsListRequest.setPageSize(Constants.MAX_ENTITIES_PORTAL_DISPLAY_SIZE);
    transactionsListRequest.setSettlementStatus(Constants.REQUEST_STATUS_PROCESSING);
    try {
      pendingTransactionsReportList =
          transactionService.getAllAccountsExecutedTransactionsOnDate(transactionsListRequest);
      if (StringUtils.isListNotNullNEmpty(pendingTransactionsReportList)) {
        session.setAttribute(Constants.PENDING_TRANSACTIONS_REPORT_LIST, pendingTransactionsReportList);
        modelAndView.addObject(Constants.TRANSACTION_DIV, Boolean.TRUE);

        for (ReportsDTO reportsDTO : pendingTransactionsReportList) {
          reportsDTO.setTxnJsonString(JsonUtil.convertObjectToJSON(reportsDTO.getTxnPopupDto()));
        }
        modelAndView.addObject("pageSize", transactionsListRequest.getPageSize());
        modelAndView = PaginationUtil.getPagenationModel(modelAndView,
            transactionsListRequest.getNoOfRecords());
        modelAndView.addObject(pendingTransactionsReportList);
        model.put(Constants.PENDING_TRANSACTIONS_REPORT_LIST, pendingTransactionsReportList);
        model.put("getTransactionsListRequest", getTransactionsListRequest);
      }
    } catch (Exception e) {
      modelAndView.addObject(Constants.ERROR,
          messageSource.getMessage(Constants.CHATAK_GENERAL_ERROR, null, LocaleContextHolder.getLocale()));
      logger.error("ERROR:: ReportsController:: showAllPendingTransactions method", e);
    }

    session.setAttribute(Constants.PENDING_TRANSACTIONS_REPORT_LIST, pendingTransactionsReportList);
    modelAndView.addObject(Constants.MERCHANT, new Merchant());
    modelAndView.addObject("flag", true);
    logger.info("Exiting:: ReportsController:: showAllPendingTransactions method");
    return modelAndView;
  }

  @RequestMapping(value = GLOBAL_ALL_TRANSACTIONS_REPORT_DATES, method = RequestMethod.GET)
  public ModelAndView showGlobalAllTransactionsDate(HttpServletRequest request,
      HttpServletResponse response, Merchant merchant, BindingResult bindingResult, Map model,
      HttpSession session) {
    logger.info("Entering:: ReportsController:: showGlobalAllTransactionsDate method");
    ModelAndView modelAndView = new ModelAndView(SHOW_GLOBAL_ALL_TRANS_REPORT);
    String userType = (String) session.getAttribute(Constants.LOGIN_USER_TYPE);
    String existingFeature = (String) session.getAttribute(Constants.EXISTING_FEATURES);
    if (userType.equalsIgnoreCase(Constants.ADMIN_VALUE)) {
      if (!existingFeature.contains(FeatureConstants.ADMIN_SERVICE_REPORTS_FEATURE_ID)) {
        return validateExistingFeature(session, modelAndView);
      }
    } else if (userType.equalsIgnoreCase(Constants.RESELLER)&& !existingFeature.contains(FeatureConstants.RESELLER_SERVICE_REPORTS_FEATURE_ID)) {
     
        return validateExistingFeature(session, modelAndView);
      }
    
    List<ReportsDTO> allTransactionsReportList = new ArrayList<>();
    modelAndView.addObject(Constants.TRANSACTION_DIV, Boolean.FALSE);
    modelAndView.addObject(allTransactionsReportList);
    logger.info("Exiting:: ReportsController:: showGlobalAllTransactionsDate method");
    return modelAndView;
  }

  @RequestMapping(value = GLOBAL_REVENUE_GENERATED_REPORTS_DATES, method = RequestMethod.GET)
  public ModelAndView showGlobalRevenueGeneratedReportsDates(HttpServletRequest request,
      HttpServletResponse response, Merchant merchant, BindingResult bindingResult, Map model,
      HttpSession session) {
    logger.info("Entering:: ReportsController:: showGlobalRevenueGeneratedReportsDates method");
    ModelAndView modelAndView = new ModelAndView(SHOW_GLOBAL_REVENUE_GENERATED_REPORT);
    String userType = (String) session.getAttribute(Constants.LOGIN_USER_TYPE);
    String existingFeature = (String) session.getAttribute(Constants.EXISTING_FEATURES);
    if (userType.equalsIgnoreCase(Constants.ADMIN_VALUE)) {
      if (!existingFeature.contains(FeatureConstants.ADMIN_SERVICE_REPORTS_FEATURE_ID)) {
        return validateExistingFeature(session, modelAndView);
      }
    } else if (userType.equalsIgnoreCase(Constants.RESELLER)&& !existingFeature.contains(FeatureConstants.RESELLER_SERVICE_REPORTS_FEATURE_ID)) {
      
        return validateExistingFeature(session, modelAndView);
      
    }
    setListOfReportsDTO(session, modelAndView);
    logger.info("Exiting:: ReportsController:: showGlobalRevenueGeneratedReportsDates method");
    return modelAndView;
  }

  @RequestMapping(value = GLOBAL_REVENUE_GENERATED_REPORT, method = RequestMethod.GET)
  public ModelAndView showGlobalRevenueGeneratedReport(HttpServletRequest request,
      HttpServletResponse response, Merchant merchant, BindingResult bindingResult, Map model,
      @FormParam("fromDate") final String fromDate,HttpSession session) {
    String currency=request.getParameter("currency");
    String revenueType=request.getParameter(Constants.REVENUE_TYPE);
    String merchantCode=request.getParameter("merchantCode");
    String toDate=request.getParameter("toDate");
    logger.info("Entering:: ReportsController:: showGlobalRevenueGeneratedReport method");
    ModelAndView modelAndView = new ModelAndView(SHOW_GLOBAL_REVENUE_GENERATED_REPORT);
    GetTransactionsListRequest transactionsListRequest = new GetTransactionsListRequest();
    String userType = (String) session.getAttribute(Constants.LOGIN_USER_TYPE);
    String existingFeature = (String) session.getAttribute(Constants.EXISTING_FEATURES);
    if (userType.equalsIgnoreCase(Constants.ADMIN_VALUE)) {
      if (!existingFeature.contains(FeatureConstants.ADMIN_SERVICE_REPORTS_FEATURE_ID)) {
        return validateExistingFeature(session, modelAndView);
      }
    } else if (userType.equalsIgnoreCase(Constants.RESELLER)&& !existingFeature.contains(FeatureConstants.RESELLER_SERVICE_REPORTS_FEATURE_ID)) {
        return validateExistingFeature(session, modelAndView);
    }
    modelAndView.addObject(Constants.ERROR, null);
    session.setAttribute(Constants.ERROR, null);
    merchant.setPageSize(Constants.MAX_ENTITIES_PORTAL_DISPLAY_SIZE);
    merchant.setPageIndex(Constants.ONE);
    transactionsListRequest.setFrom_date(fromDate);
    transactionsListRequest.setTo_date(toDate);
    transactionsListRequest.setSettlementStatus(Constants.EXECUTED_STATUS);
    transactionsListRequest.setEntryMode(revenueType);
    transactionsListRequest = setMerchantCode(merchantCode, transactionsListRequest);
    try {
      List<ReportsDTO> revenueGeneratedReportList =
          transactionService.getAllExecutedAccTransFeeOnDate(transactionsListRequest);
      modelAndView = setRevenueReportModel(session, currency,
          modelAndView, revenueGeneratedReportList, transactionsListRequest);
    } catch (Exception e) {
      modelAndView.addObject(Constants.ERROR,
          messageSource.getMessage(Constants.CHATAK_GENERAL_ERROR, null, LocaleContextHolder.getLocale()));
      logger.error("ERROR:: ReportsController:: showGlobalRevenueGeneratedReport method", e);
    }
    logger.info("Exiting:: ReportsController:: showGlobalRevenueGeneratedReport method");
    return modelAndView;
  }

  private ModelAndView setRevenueReportModel(HttpSession session,
      String currency, ModelAndView modelAndView,
      List<ReportsDTO> revenueGeneratedReportList, GetTransactionsListRequest request) throws ChatakAdminException {
    Long rapidRevenue = 0L;
    Long merchantRevenue = 0L;
    Long subMerRevenue = 0L;
    if (null != revenueGeneratedReportList) {
      for (ReportsDTO reportsDto : revenueGeneratedReportList) {
        if (StringUtils.isValidString(reportsDto.getChatakFee())) {
          rapidRevenue += Long.parseLong(reportsDto.getChatakFee());
        }
        if (StringUtils.isValidString(reportsDto.getParentMerchantId())) {
          subMerRevenue += Long.parseLong(reportsDto.getTotalTxnAmount());
        } else {
          merchantRevenue += Long.parseLong(reportsDto.getTotalTxnAmount());
        }
        reportsDto.setTotalTxnAmount(PGConstants.DOLLAR_SYMBOL
            + StringUtils.amountToString(Long.parseLong(reportsDto.getTotalTxnAmount())));
        reportsDto.setChatakFee(PGConstants.DOLLAR_SYMBOL
            + StringUtils.amountToString(Long.parseLong(reportsDto.getChatakFee())));
        reportsDto.setFee(PGConstants.DOLLAR_SYMBOL
            + StringUtils.amountToString(Long.parseLong(reportsDto.getFee())));

        reportsDto.setTxnJsonString(JsonUtil.convertObjectToJSON(reportsDto.getTxnPopupDto()));
      }
      modelAndView = setRevenueType(request.getEntryMode(), modelAndView);
      session.setAttribute(Constants.REVENUE_GENERATED_REPORT_LIST, revenueGeneratedReportList);
      modelAndView.addObject(Constants.TRANSACTION_DIV, Boolean.TRUE);
      modelAndView.addObject("startDate", request.getFrom_date());
      modelAndView.addObject("endDate", request.getTo_date());
      modelAndView.addObject("currency", currency);
      modelAndView.addObject("rapidRevenue", rapidRevenue);
      modelAndView.addObject("subMerRevenue", subMerRevenue);
      modelAndView.addObject("merchantRevenue", merchantRevenue);
      modelAndView.addObject(Constants.REVENUE_GENERATED_REPORT_LIST, revenueGeneratedReportList);
    } else {
      modelAndView.addObject(Constants.ERROR, messageSource.getMessage(
          "chatak.admin.revenue.error.message", null, LocaleContextHolder.getLocale()));
    }
    return modelAndView;
  }

  private GetTransactionsListRequest setMerchantCode(String merchantCode,
      GetTransactionsListRequest transactionsListRequest) {
    if ("".equals(merchantCode))
      transactionsListRequest.setMerchant_code(null);
    else
      transactionsListRequest.setMerchant_code(merchantCode);
    return transactionsListRequest;
  }

  private ModelAndView setRevenueType(String revenueType, ModelAndView modelAndView) {
    if ("MERCHAT_WEB".equalsIgnoreCase(revenueType)) {
      modelAndView.addObject(Constants.REVENUE_TYPE, Constants.MANUAL);
    } else if ("pos".equalsIgnoreCase(revenueType)) {
      modelAndView.addObject(Constants.REVENUE_TYPE, Constants.SYSTEM);
    } else {
      modelAndView.addObject(Constants.REVENUE_TYPE, Constants.ALL);
    }
    return modelAndView;
  }
}
