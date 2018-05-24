package com.chatak.merchant.controller;

import java.io.IOException;
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
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.chatak.merchant.constants.FeatureConstants;
import com.chatak.merchant.constants.URLMappingConstants;
import com.chatak.merchant.controller.model.ExportDetails;
import com.chatak.merchant.controller.model.Option;
import com.chatak.merchant.model.GetMerchantDetailsResponse;
import com.chatak.merchant.model.TransactionListResponse;
import com.chatak.merchant.service.FundTransferService;
import com.chatak.merchant.service.MerchantInfoService;
import com.chatak.merchant.service.RestPaymentService;
import com.chatak.merchant.service.TransactionService;
import com.chatak.merchant.util.ExportUtil;
import com.chatak.merchant.util.JsonUtil;
import com.chatak.merchant.util.StringUtil;
import com.chatak.merchant.util.SystemReportsFileExportsUtil;
import com.chatak.merchant.util.TransactionFileExportUtil;
import com.chatak.pg.acq.dao.MerchantDao;
import com.chatak.pg.acq.dao.MerchantProfileDao;
import com.chatak.pg.acq.dao.model.PGMerchant;
import com.chatak.pg.constants.AccountTransactionCode;
import com.chatak.pg.constants.PGConstants;
import com.chatak.pg.enums.ExportType;
import com.chatak.pg.model.AccountTransactionDTO;
import com.chatak.pg.model.EFTDetails;
import com.chatak.pg.model.GetTransactionIdsListResponse;
import com.chatak.pg.model.ReportsDTO;
import com.chatak.pg.user.bean.GetTransactionsListRequest;
import com.chatak.pg.user.bean.GetTransactionsListResponse;
import com.chatak.pg.user.bean.GetTransferListRequest;
import com.chatak.pg.user.bean.Merchant;
import com.chatak.pg.util.CommonUtil;
import com.chatak.pg.util.Constants;
import com.chatak.pg.util.Properties;
import com.chatak.pg.util.StringUtils;
import com.itextpdf.text.DocumentException;

import jxl.write.WriteException;

@SuppressWarnings({"rawtypes", "unchecked"})
@Controller
public class ReportsController implements URLMappingConstants {

  @Autowired
  MessageSource messageSource;

  private static Logger logger = Logger.getLogger(ReportsController.class);

  @Autowired
  private TransactionService transactionService;

  @Autowired
  private FundTransferService fundTransfersService;

  @Autowired
  private MerchantDao merchantDao;

  @Autowired
  private RestPaymentService paymentService;

  @Autowired
  MerchantInfoService merchantInfoService;

  @Autowired
  MerchantProfileDao merchantProfileDao;

  @RequestMapping(value = GLOBAL_REVENUE_GENERATED_REPORTS_DATES, method = RequestMethod.GET)
  public ModelAndView showGlobalRevenueGeneratedReportsDates(HttpServletRequest request,
      HttpServletResponse response, Merchant merchant, BindingResult bindingResult, Map model,
      HttpSession session) {
    logger.info("Entering:: ReportsController:: showGlobalRevenueGeneratedReportsDates method");
    ModelAndView modelAndView = new ModelAndView(SHOW_GLOBAL_REVENUE_GENERATED_REPORT);
    String existingFeature = (String) session.getAttribute(Constants.EXISTING_FEATURES);
    if (!existingFeature.contains(FeatureConstants.MERCHANT_SERVICE_REPORTS_FEATURE_ID)) {
      session.invalidate();
      modelAndView.setViewName(INVALID_REQUEST_PAGE);
      return modelAndView;
    }
    List<ReportsDTO> executedTransactionsReportList = new ArrayList<>();
    Long merchantId = (Long) session.getAttribute(Constants.LOGIN_USER_MERCHANT_ID);
    PGMerchant pgMerchant = merchantInfoService.getMerchantOnId(merchantId);
    List<Option> merchantCodes =
        merchantInfoService.getMerchantCodeAndName(pgMerchant.getMerchantCode());
    session.setAttribute("executedTransactionsReportList", executedTransactionsReportList);
    modelAndView.addObject(Constants.TRANSACTION_DIV, Boolean.FALSE);
    modelAndView.addObject("merchantCodes", merchantCodes);
    modelAndView.addObject(executedTransactionsReportList);
    modelAndView.addObject("merchant", new Merchant());
    logger.info("Exiting:: ReportsController:: showGlobalRevenueGeneratedReportsDates method");
    return modelAndView;
  }

  @RequestMapping(value = GLOBAL_REVENUE_GENERATED_REPORT, method = RequestMethod.GET)
  public ModelAndView showGlobalRevenueGeneratedReport(HttpServletRequest request,
      HttpServletResponse response, Merchant merchant, BindingResult bindingResult, Map model,
      @FormParam("toDate") final String toDate, HttpSession session) {
    logger.info("Entering:: ReportsController:: showGlobalRevenueGeneratedReport method");
    String merchantCode = request.getParameter("merchantCode");
    String revenueType = request.getParameter(Constants.REVENUE_TYPE);
    String fromDate = request.getParameter("fromDate");
    ModelAndView modelAndView = new ModelAndView(SHOW_GLOBAL_REVENUE_GENERATED_REPORT);
    GetTransactionsListRequest transactionsListRequest = new GetTransactionsListRequest();
    String existingFeature = (String) session.getAttribute(Constants.EXISTING_FEATURES);
    if (!existingFeature.contains(FeatureConstants.MERCHANT_SERVICE_REPORTS_FEATURE_ID)) {
      session.invalidate();
      modelAndView.setViewName(INVALID_REQUEST_PAGE);
      return modelAndView;
    }
    Long rapidRevenue = 0L;
    Long merchantRevenue = 0L;
    Long subMerRevenue = 0L;
    modelAndView.addObject(Constants.ERROR, null);
    session.setAttribute(Constants.ERROR, null);
    merchant.setPageSize(Constants.MAX_ENTITIES_PORTAL_DISPLAY_SIZE);
    merchant.setPageIndex(Constants.ONE);
    transactionsListRequest.setFrom_date(fromDate);
    transactionsListRequest.setTo_date(toDate);
    transactionsListRequest.setSettlementStatus(PGConstants.PG_SETTLEMENT_EXECUTED);
    transactionsListRequest.setEntryMode(revenueType);

    Long merchantId = (Long) session.getAttribute(Constants.LOGIN_USER_MERCHANT_ID);
    PGMerchant pgMerchant = merchantInfoService.getMerchantOnId(merchantId);
    String parentMerchantCode = merchantInfoService.getParentMerchantCode(merchantCode);
    String currency = pgMerchant.getLocalCurrency();

    if (null != pgMerchant) {
      if (fetchMerchantCode(merchantCode, pgMerchant, parentMerchantCode)) {
        transactionsListRequest.setMerchant_code(merchantCode);
      }  else if (StringUtils.isEmpty(merchantCode)) {
        transactionsListRequest.setMerchant_code(pgMerchant.getMerchantCode());
      } else {
        modelAndView.addObject(Constants.ERROR, messageSource.getMessage(
            "chatak.invalid.submerchant.error.message", null, LocaleContextHolder.getLocale()));
        modelAndView.addObject(Constants.TRANSACTION_DIV, Boolean.FALSE);
        modelAndView.addObject("startDate", fromDate);
        modelAndView.addObject("endDate", toDate);
        modelAndView.addObject(Constants.CURRENCY, currency);
        modelAndView.addObject("rapidRevenue", rapidRevenue);
        modelAndView.addObject("subMerRevenue", subMerRevenue);
        modelAndView.addObject("merchantRevenue", merchantRevenue);
        modelAndView.addObject(Constants.REVENUE_GENERATED_REPORT_LIST,
            new ArrayList<ReportsDTO>());
        return modelAndView;
      }
    }
    try {
      List<ReportsDTO> revenueGeneratedReportList =
          transactionService.getAllExecutedAccTransFeeOnDate(transactionsListRequest);
      
      if(revenueGeneratedReportList == null) {
        modelAndView.addObject(Constants.ERROR,
            Properties.getProperty("chatak.admin.revenue.error.message"));
        return modelAndView;
      }
      
        for (ReportsDTO reportsDto : revenueGeneratedReportList) {
          getSystemRevenueAmount(rapidRevenue, reportsDto);
          if (StringUtils.isValidString(reportsDto.getParentMerchantId())) {
            subMerRevenue += Long.parseLong(reportsDto.getTotalTxnAmount());
          } else {
            merchantRevenue += Long.parseLong(reportsDto.getTotalTxnAmount());
          }
          reportsDto.setTotalTxnAmount(
              StringUtils.amountToString(Long.parseLong(reportsDto.getTotalTxnAmount())));
          reportsDto
              .setChatakFee(StringUtils.amountToString(Long.parseLong(reportsDto.getChatakFee())));
          reportsDto.setFee(StringUtils.amountToString(Long.parseLong(reportsDto.getFee())));

          reportsDto.setTxnJsonString(JsonUtil.convertObjectToJSON(reportsDto.getTxnPopupDto()));
        }
        fetchRevenueType(revenueType, modelAndView);
        session.setAttribute(Constants.REVENUE_GENERATED_REPORT_LIST, revenueGeneratedReportList);
        modelAndView.addObject(Constants.TRANSACTION_DIV, Boolean.TRUE);
        modelAndView.addObject("startDate", fromDate);
        modelAndView.addObject("endDate", toDate);
        modelAndView.addObject(Constants.CURRENCY, currency);
        modelAndView.addObject("rapidRevenue", rapidRevenue);
        modelAndView.addObject("subMerRevenue", subMerRevenue);
        modelAndView.addObject("merchantRevenue", merchantRevenue);
        modelAndView.addObject(Constants.REVENUE_GENERATED_REPORT_LIST, revenueGeneratedReportList);

    } catch (Exception e) {
      modelAndView.addObject(Constants.ERROR, messageSource
          .getMessage(Constants.CHATAK_GENERAL_ERROR, null, LocaleContextHolder.getLocale()));
      logger.error("ERROR:: ReportsController:: showGlobalRevenueGeneratedReport method", e);
    }
    logger.info("Exiting:: ReportsController:: showGlobalRevenueGeneratedReport method");
    return modelAndView;
  }
  
private boolean fetchMerchantCode(String merchantCode, PGMerchant pgMerchant, String parentMerchantCode) {
	return pgMerchant.getMerchantCode().equals(parentMerchantCode)
          || pgMerchant.getMerchantCode().equals(merchantCode);
}

private Long getSystemRevenueAmount(Long rapidRevenue, ReportsDTO reportsDto) {
	if (StringUtils.isValidString(reportsDto.getChatakFee())) {
	    rapidRevenue += Long.parseLong(reportsDto.getChatakFee());
	  }
	return rapidRevenue;
}

private void fetchRevenueType(String revenueType, ModelAndView modelAndView) {
	if ("MERCHANT_WEB".equalsIgnoreCase(revenueType)) {
	  modelAndView.addObject(Constants.REVENUE_TYPE, Constants.MANUAL);
	} else if ("pos".equalsIgnoreCase(revenueType)) {
	  modelAndView.addObject(Constants.REVENUE_TYPE, Constants.SYSTEM);
	} else {
	  modelAndView.addObject(Constants.REVENUE_TYPE, Constants.ALL);
	}
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
        SystemReportsFileExportsUtil.downloadRevenueGeneratedPdf(revenueGeneratedList, response,
            messageSource);
      } else if (Constants.XLS_FILE_FORMAT.equalsIgnoreCase(downloadType)) {
        SystemReportsFileExportsUtil.downloadRevenueGeneratedXl(revenueGeneratedList, response,
            messageSource);
      }
    } catch (Exception e) {
      modelAndView.addObject(Constants.ERROR, messageSource
          .getMessage(Constants.CHATAK_GENERAL_ERROR, null, LocaleContextHolder.getLocale()));
      logger.error("ERROR:: ReportsController:: downloadRevenueGeneratedReport method", e);
    }
    logger.info("Exiting:: ReportsController:: downloadRevenueGeneratedReport method");
    return modelAndView;
  }

  @RequestMapping(value = CHATAK_ADMIN_SPECIFIC_USER_EFT_TRANSFERS, method = RequestMethod.GET)
  public ModelAndView showSpecificEFTTransfersReports(HttpServletRequest request,
      HttpServletResponse response, Merchant merchant, BindingResult bindingResult, Map model,
      HttpSession session) {
    logger.info("Entering:: ReportsController:: showSpecificEFTTransfersReports method");
    ModelAndView modelAndView = new ModelAndView(SHOW_CHATAK_ADMIN_SPECIFIC_USER_EFT_TRANSFERS);
    List<ReportsDTO> executedTransactionsReportList = new ArrayList<>();
    Long merchantId = (Long) session.getAttribute(Constants.LOGIN_USER_MERCHANT_ID);
    PGMerchant pgMerchant = merchantInfoService.getMerchantOnId(merchantId);
    List<Option> merchantCodes =
        merchantInfoService.getMerchantCodeAndName(pgMerchant.getMerchantCode());
    session.setAttribute("executedTransactionsReportList", executedTransactionsReportList);
    session.setAttribute("merchantCodesOptions", merchantCodes);
    modelAndView.addObject("merchantList", merchantCodes);
    modelAndView.addObject(Constants.TRANSACTION_DIV, Boolean.FALSE);
    modelAndView.addObject(executedTransactionsReportList);
    modelAndView.addObject("merchant", new Merchant());
    logger.info("Exiting:: ReportsController:: showSpecificEFTTransfersReports method");
    return modelAndView;
  }

  @RequestMapping(value = PROCESS_CHATAK_ADMIN_SPECIFIC_USER_EFT_TRANSFERS,
      method = RequestMethod.GET)
  public ModelAndView processSpecificEFTTransfersReports(HttpServletRequest request,
      HttpServletResponse response, Merchant merchant, BindingResult bindingResult, Map model,
      @FormParam("merchantCode") final String merchantCode, HttpSession session) {
    logger.info("Entering:: ReportsController:: processSpecificEFTTransfersReports method");
    String fromDate = request.getParameter("fromDate");
    String toDate = request.getParameter("toDate");
    String currency = request.getParameter(Constants.CURRENCY);
    ModelAndView modelAndView = new ModelAndView(SHOW_CHATAK_ADMIN_SPECIFIC_USER_EFT_TRANSFERS);
    String existingFeature = (String) session.getAttribute(Constants.EXISTING_FEATURES);
    if (!existingFeature.contains(FeatureConstants.MERCHANT_SERVICE_REPORTS_EFT_FEATURE_ID)) {
      session.invalidate();
      modelAndView.setViewName(INVALID_REQUEST_PAGE);
      return modelAndView;
    }
    GetTransferListRequest transferListRequest = new GetTransferListRequest();
    List<ReportsDTO> eftTransferReportList = new ArrayList<>();
    List<EFTDetails> searchDetails = new ArrayList<>();
    if (request.getHeader(Constants.REFERER) == null) {
      session.invalidate();
      modelAndView.setViewName(INVALID_REQUEST_PAGE);
      return modelAndView;
    }
    Long merchantId = (Long) session.getAttribute(Constants.LOGIN_USER_MERCHANT_ID);
    PGMerchant pgMerchant = merchantInfoService.getMerchantOnId(merchantId);
    List<Option> merchantCodes = (List<Option>) session.getAttribute("merchantCodesOptions");
    modelAndView.addObject("merchantList", merchantCodes);
    String parentMerchantCode = merchantInfoService.getParentMerchantCode(merchantCode);
    if (null != pgMerchant) {
      if (fetchMerchantCode(merchantCode, pgMerchant, parentMerchantCode)) {
        transferListRequest.setMerchantCode(merchantCode);
      } else if ("".equals(merchantCode)) {
        transferListRequest.setMerchantCode(pgMerchant.getMerchantCode());
      } else {
        modelAndView.addObject(Constants.ERROR, messageSource.getMessage(
            "chatak.invalid.submerchant.error.message", null, LocaleContextHolder.getLocale()));
        session.setAttribute(Constants.EFT_TRANSFER_REPORT_LIST, eftTransferReportList);
        modelAndView.addObject(Constants.TRANSACTION_DIV, Boolean.FALSE);
        modelAndView.addObject(eftTransferReportList);
        modelAndView.addObject("searchDetails", searchDetails);
        return modelAndView;
      }
    }
    modelAndView.addObject(Constants.ERROR, null);
    session.setAttribute(Constants.ERROR, null);
    transferListRequest.setFrom_date(fromDate);
    transferListRequest.setTo_date(toDate);
    transferListRequest.setCurrency(currency);
    transferListRequest.setTransferMode(Constants.CONSTANT_EFT);
    try {
      eftTransferReportList =
          fundTransfersService.getAllEftTransfersListOnMerchantCode(transferListRequest);
      Map<String, Long> tempMap = fundTransfersService.splitReportsAmount(eftTransferReportList);
      searchDetails = fundTransfersService.getReportsEFTAmount(tempMap);
      for (EFTDetails eftDetails : searchDetails) {
        eftDetails.setToDate(toDate);
        eftDetails.setFromDate(fromDate);
        eftDetails.setCurrency(currency);
      }
      if (Constants.ZERO != eftTransferReportList.size()) {
        for (ReportsDTO reportsDTO : eftTransferReportList) {
          reportsDTO.setAmount(PGConstants.DOLLAR_SYMBOL
              + StringUtils.amountToString(Long.parseLong(reportsDTO.getAmount())));
        }
        session.setAttribute(Constants.EFT_TRANSFER_REPORT_LIST, eftTransferReportList);
        modelAndView.addObject(Constants.TRANSACTION_DIV, Boolean.TRUE);
        modelAndView.addObject(eftTransferReportList);
        modelAndView.addObject("searchDetails", searchDetails);
      } else {
        modelAndView.addObject(Constants.ERROR, messageSource.getMessage(
            "chatak.admin.transactions.error.message", null, LocaleContextHolder.getLocale()));
      }
    } catch (Exception e) {
      modelAndView.addObject(Constants.ERROR, messageSource
          .getMessage(Constants.CHATAK_GENERAL_ERROR, null, LocaleContextHolder.getLocale()));
      logger.error("ERROR:: ReportsController:: processSpecificEFTTransfersReports method", e);
    }
    logger.info("Exiting:: ReportsController:: processSpecificEFTTransfersReports method");
    return modelAndView;
  }

  @RequestMapping(value = CHATAK_EFT_FETCH_TRAN_ID, method = RequestMethod.GET)
  public @ResponseBody String fetchTransactionIdsListBytransferId(Map model,
      HttpServletRequest request, HttpServletResponse response, HttpSession session) {
	String refId = request.getParameter("refId");
    ModelAndView modelAndView = new ModelAndView(SHOW_CHATAK_ADMIN_SPECIFIC_USER_EFT_TRANSFERS);
    modelAndView.addObject(Constants.ERROR, null);
    logger.info("Entering :: ReportsController :: fetchTransactionIdsListBytransferId method");
    GetTransactionIdsListResponse gettransactionIdsListResponse;
    try {
      gettransactionIdsListResponse = fundTransfersService.getTransactionIdListOnTransferId(refId);
      if (gettransactionIdsListResponse != null) {
        return JsonUtil.convertObjectToJSON(gettransactionIdsListResponse);
      }
    } catch (Exception exp) {
      modelAndView.addObject(Constants.ERROR, messageSource
          .getMessage(Constants.CHATAK_GENERAL_ERROR, null, LocaleContextHolder.getLocale()));
      logger.error("ERROR :: ReportsController :: fetchTransactionIdsListBytransferId method:" + exp);
    }
    logger.info("Exiting :: ReportsController :: fetchTransactionIdsListBytransferId method");
    return refId;
  }

  @RequestMapping(value = CHATAK_MERCHANT_PROCESSING_TRANSACTIONS_REPORT,
      method = RequestMethod.POST)
  public ModelAndView processingTransactionsReport(HttpSession session, Map model,
      HttpServletRequest request, HttpServletResponse response,
      @FormParam("requestFrom") final String requestFrom,
      @FormParam("downloadType") final String downloadType,
      @FormParam("downloadAllRecords") final boolean downloadAllRecords) {
    logger.info("Entering :: ReportsController :: processingTransactionsReport method");

    ModelAndView modelAndView = new ModelAndView(CHATAK_MERCHANT_PROCESSING_TRANSACTIONS);
    if (request.getHeader(Constants.REFERER) == null) {
      session.invalidate();
      modelAndView.setViewName(INVALID_REQUEST_PAGE);
      return modelAndView;
    }

    if (!StringUtil.isNullAndEmpty(requestFrom) && requestFrom.equals("dashobard")) {
      modelAndView.setViewName(CHATAK_MERCHANT_DASH_BOARD);
    }
    GetTransactionsListRequest transactionRequest = null;
    TransactionListResponse transactionResponse = null;
    List<AccountTransactionDTO> processingTxnList = null;
    try {
      processingTxnList =
          (List<AccountTransactionDTO>) session.getAttribute(Constants.PROCESSING_TXN_LIST);
      if (null != processingTxnList) {
        Long merchantId = (Long) session.getAttribute(Constants.LOGIN_USER_MERCHANT_ID);
        if (merchantId != null) {
          GetMerchantDetailsResponse merchantDetailsResponse;
          transactionRequest = new GetTransactionsListRequest();
          transactionResponse = new TransactionListResponse();

          merchantDetailsResponse =
              paymentService.getMerchantIdAndTerminalId(merchantId.toString());
          transactionRequest.setMerchant_code(merchantDetailsResponse.getMerchantId());

          List<String> txnCodeList = new ArrayList<>(Constants.ELEVEN);
          setTxnCodeList(txnCodeList);
          txnCodeList.add(AccountTransactionCode.MANUAL_CREDIT);
          txnCodeList.add(AccountTransactionCode.MANUAL_DEBIT);

          transactionRequest.setTransactionCodeList(txnCodeList);
          transactionRequest.setSettlementStatus(PGConstants.PG_SETTLEMENT_PROCESSING);

          processDownloadReport(response, downloadType, downloadAllRecords, transactionRequest,
              transactionResponse, processingTxnList);
        }
      }
    } catch (Exception e) {
      modelAndView.addObject(Constants.ERROR, messageSource
          .getMessage(Constants.CHATAK_GENERAL_ERROR, null, LocaleContextHolder.getLocale()));
      logger.error("Error :: ReportsController :: processingTransactionsReport method", e);
    }
    logger.info("Exiting :: ReportsController :: processingTransactionsReport method");
    return null;
  }

  private void setTxnCodeList(List<String> txnCodeList) {
	  txnCodeList.add(AccountTransactionCode.CC_AMOUNT_CREDIT);
	  txnCodeList.add(AccountTransactionCode.CC_AMOUNT_DEBIT);
	  txnCodeList.add(AccountTransactionCode.EFT_DEBIT);
	  txnCodeList.add(AccountTransactionCode.FT_CHECK);
	  txnCodeList.add(AccountTransactionCode.CC_FEE_CREDIT); 
	  txnCodeList.add(AccountTransactionCode.CC_FEE_DEBIT);
	  txnCodeList.add(AccountTransactionCode.FT_BANK);
	  txnCodeList.add(AccountTransactionCode.ACCOUNT_CREDIT);
	  txnCodeList.add(AccountTransactionCode.ACCOUNT_DEBIT);
  }

  private void processDownloadReport(HttpServletResponse response, final String downloadType,
      final boolean downloadAllRecords, GetTransactionsListRequest transactionRequest,
      TransactionListResponse transactionResponse, List<AccountTransactionDTO> processingTxnList) throws WriteException, IOException, DocumentException {
    if (downloadAllRecords) {
      processingTxnList = fetchProcessingTxnList(transactionRequest, transactionResponse,
    processingTxnList);
    }
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
  
  private void setExportDetailsDataForDownloadProcessingTransactionsReport(List<AccountTransactionDTO> executedTxnsList,
      ExportDetails exportDetails) {
    exportDetails.setReportName("Processing_Transactions");
    exportDetails.setHeaderMessageProperty("chatak.header.processing.transactions.list");

    exportDetails.setHeaderList(getProcessingTransactionsHeaderList());
    exportDetails.setFileData(getProcessingTransactionsFileData(executedTxnsList));
  }

  private List<AccountTransactionDTO> fetchProcessingTxnList(GetTransactionsListRequest transactionRequest,
		TransactionListResponse transactionResponse, List<AccountTransactionDTO> processingTxnList) {
	GetTransactionsListResponse executedTxnList;
	transactionRequest.setPageIndex(Constants.ONE);

	executedTxnList = transactionService.searchAccountTransactions(transactionRequest);

	if (null != executedTxnList && null != executedTxnList.getAccountTransactionList()) {
	  transactionResponse.setAccountTxnList(executedTxnList.getAccountTransactionList());
	  transactionResponse.setErrorCode(executedTxnList.getErrorCode());
	  transactionResponse.setErrorMessage(executedTxnList.getErrorMessage());
	  transactionResponse.setTotalNoOfRows(executedTxnList.getTotalResultCount());
	  processingTxnList = transactionResponse.getAccountTxnList() != null
	      ? transactionResponse.getAccountTxnList()
	      : new ArrayList<AccountTransactionDTO>();
	}
	return processingTxnList;
  }

  @RequestMapping(value = CHATAK_MERCHANT_EXECUTED_TRANSACTIONS_REPORT, method = RequestMethod.POST)
  public ModelAndView executedTransactionsReport(HttpSession session, Map model,
      HttpServletRequest request, HttpServletResponse response,
      @FormParam("requestFrom") final String requestFrom,
      @FormParam("downloadType") final String downloadType,
      @FormParam("downloadAllRecords") final boolean downloadAllRecords) {
    logger.info("Entering:: ReportsController:: executedTransactionsReport method");

    ModelAndView modelAndView = new ModelAndView(CHATAK_MERCHANT_EXECUTED_TRANSACTIONS);
    if (request.getHeader(Constants.REFERER) == null) {
      session.invalidate();
      modelAndView.setViewName(INVALID_REQUEST_PAGE);
      return modelAndView;
    }

    if (!StringUtil.isNullAndEmpty(requestFrom) && requestFrom.equals("dashobard")) {
      modelAndView.setViewName(CHATAK_MERCHANT_DASH_BOARD);
    }

    List<AccountTransactionDTO> executedTxnsList = null;
    try {
      executedTxnsList =
          (List<AccountTransactionDTO>) session.getAttribute(Constants.EXECUTED_TXN_LIST);
      if (null != executedTxnsList) {
        GetMerchantDetailsResponse merchantDetailsResponse = null;
        Long merchantId = (Long) session.getAttribute(Constants.LOGIN_USER_MERCHANT_ID);

        if (merchantId != null) {
          GetTransactionsListRequest transaction = new GetTransactionsListRequest();
          TransactionListResponse transactionResponse = new TransactionListResponse();

          merchantDetailsResponse =
              paymentService.getMerchantIdAndTerminalId(merchantId.toString());
          transaction.setMerchant_code(merchantDetailsResponse.getMerchantId());

          List<String> txnCodeList = new ArrayList<>(Constants.ELEVEN);
          setTxnCodeList(txnCodeList);

          transaction.setTransactionCodeList(txnCodeList);

          transaction.setSettlementStatus(PGConstants.PG_SETTLEMENT_EXECUTED);

          processDownloadReport(response, downloadType, downloadAllRecords, executedTxnsList,
              transaction, transactionResponse);
        }
      }
    } catch (Exception e) {
      modelAndView.addObject(Constants.ERROR, messageSource
          .getMessage(Constants.CHATAK_GENERAL_ERROR, null, LocaleContextHolder.getLocale()));
      logger.error("ERROR:: ReportsController:: executedTransactionsReport method", e);
    }
    logger.info("Exiting:: ReportsController:: executedTransactionsReport method");
    return null;
  }

  private void processDownloadReport(HttpServletResponse response, final String downloadType,
      final boolean downloadAllRecords, List<AccountTransactionDTO> executedTxnsList,
      GetTransactionsListRequest transaction, TransactionListResponse transactionResponse) throws WriteException, IOException, DocumentException {
    if (downloadAllRecords) {
      executedTxnsList = fetchExecutedTxnList(executedTxnsList, transaction, transactionResponse);
    }
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
  
  private void setExportDetailsDataForDownloadExecutedTransactionsReport(List<AccountTransactionDTO> executedTxnsList,
      ExportDetails exportDetails) {
    exportDetails.setReportName("Executed_Transactions");
    exportDetails.setHeaderMessageProperty("dash-board.label.completedtransactions");

    exportDetails.setHeaderList(getExecutedTransactionsHeaderList());
    exportDetails.setFileData(getExecutedTransactionsFileData(executedTxnsList));
  }

  private List<AccountTransactionDTO> fetchExecutedTxnList(List<AccountTransactionDTO> executedTxnsList,
		GetTransactionsListRequest transaction, TransactionListResponse transactionResponse) {
	transaction.setPageIndex(Constants.ONE);

	GetTransactionsListResponse executedTxnList =
	    transactionService.searchAccountTransactions(transaction);

	if (null != executedTxnList && null != executedTxnList.getAccountTransactionList()) {
	  transactionResponse.setAccountTxnList(executedTxnList.getAccountTransactionList());
	  transactionResponse.setErrorCode(executedTxnList.getErrorCode());
	  transactionResponse.setErrorMessage(executedTxnList.getErrorMessage());
	  transactionResponse.setTotalNoOfRows(executedTxnList.getTotalResultCount());
	  executedTxnsList = transactionResponse.getAccountTxnList() != null
	      ? transactionResponse.getAccountTxnList()
	      : new ArrayList<AccountTransactionDTO>();
	}
	return executedTxnsList;
  }

  @RequestMapping(value = CHATAK_MERCHANT_MANUAL_TRANSACTIONS_REPORT, method = RequestMethod.POST)
  public ModelAndView manualTransactionsReport(HttpSession session, Map model,
      HttpServletRequest request, @FormParam("downloadType") final String downloadType,
      HttpServletResponse response) {
    logger.info("Entering:: ReportsController:: manualTransactionsReport method");

    ModelAndView modelAndView = new ModelAndView(CHATAK_MERCHANT_MANUAL_TRANSACTIONS);
    List<AccountTransactionDTO> manualTransferDownloadList =
        (List<AccountTransactionDTO>) session.getAttribute("manualTransactionsReportList");
    GetTransactionsListRequest manualTransactionRequest = new GetTransactionsListRequest();
    TransactionListResponse transactionResponse = new TransactionListResponse();
    Long merchantId = (Long) session.getAttribute(Constants.LOGIN_USER_MERCHANT_ID);
    Boolean downloadAll = Boolean.valueOf(request.getParameter("downloadAllRecords"));
    manualTransactionRequest.setAcqChannel("web");
    List<String> manualTxnCodeList = new ArrayList<>(Constants.TWO);
    manualTxnCodeList.add(AccountTransactionCode.MANUAL_CREDIT);
    manualTxnCodeList.add(AccountTransactionCode.MANUAL_DEBIT);
    manualTransactionRequest.setTransactionCodeList(manualTxnCodeList);
    manualTransactionRequest.setSettlementStatus(PGConstants.PG_SETTLEMENT_EXECUTED);
    try {
      
      if (downloadAll) {
        manualTransactionRequest.setPageIndex(Constants.ONE);
        PGMerchant parentMerchant = merchantProfileDao.getMerchantById(merchantId);
        List<PGMerchant> subMerchantList = merchantDao.findById(merchantId);
        List<String> merchantCodeList = new ArrayList<>();
        merchantCodeList.add(parentMerchant.getMerchantCode());
        if (CommonUtil.isListNotNullAndEmpty(subMerchantList)) {
          getSubMerchantCodes(subMerchantList, merchantCodeList);
        }
        String merchantCodes = org.apache.commons.lang.StringUtils.join(merchantCodeList, "|");
        manualTransactionRequest.setMerchant_code(merchantCodes);
        GetTransactionsListResponse manualTransactionsReportList =
            transactionService.searchManulAccountTransactions(manualTransactionRequest);
        if (null != manualTransactionsReportList
            && null != manualTransactionsReportList.getAccountTransactionList()) {
          transactionResponse.setTotalNoOfRows(manualTransactionsReportList.getTotalResultCount());
          manualTransferDownloadList =
              manualTransactionsReportList.getAccountTransactionList() != null
                  ? manualTransactionsReportList.getAccountTransactionList()
                  : new ArrayList<AccountTransactionDTO>();
        }
      }
      if (Constants.PDF_FILE_FORMAT.equalsIgnoreCase(downloadType)) {
        TransactionFileExportUtil.downloadManualTransactionsPdf(manualTransferDownloadList,
            response, messageSource);
      } else if (Constants.XLS_FILE_FORMAT.equalsIgnoreCase(downloadType)) {
        TransactionFileExportUtil.downloadManualTransactionsXl(manualTransferDownloadList, response,
            messageSource);
      }
    } catch (Exception e) {
      modelAndView.addObject(Constants.ERROR, messageSource
          .getMessage(Constants.CHATAK_GENERAL_ERROR, null, LocaleContextHolder.getLocale()));
      logger.error("ERROR:: ReportsController:: manualTransactionsReport method", e);
    }
    logger.info("Exiting:: ReportsController:: manualTransactionsReport method");
    modelAndView.addObject(Constants.EFT_TRANSFER_REPORT_LIST, manualTransferDownloadList);
    return null;
  }

  private void getSubMerchantCodes(List<PGMerchant> subMerchantList,
      List<String> merchantCodeList) {
    for (PGMerchant subMerchant : subMerchantList) {
      merchantCodeList.add(subMerchant.getMerchantCode());
    }
  }
  
  private List<String> getExecutedTransactionsHeaderList() {
    String[] headerArr = {
        messageSource.getMessage("dash-board.label.transactiontime", null,
            LocaleContextHolder.getLocale()),
        messageSource.getMessage("merchant.common-deviceLocalTxnTime", null,
            LocaleContextHolder.getLocale()),
        messageSource.getMessage("transactionFileExportUtil.processed.time", null,
            LocaleContextHolder.getLocale()),
        messageSource.getMessage("transaction-file-exportutil-accountTransactionId", null,
            LocaleContextHolder.getLocale()),
        messageSource.getMessage("transactionFileExportUtil.transaction.id", null,
            LocaleContextHolder.getLocale()),
        messageSource.getMessage("search-sub-merchant.label.currencycode", null,
            LocaleContextHolder.getLocale()),
        messageSource.getMessage("transactionFileExportUtil.type", null,
            LocaleContextHolder.getLocale()),
        messageSource.getMessage("transactionFileExportUtil.description", null,
            LocaleContextHolder.getLocale()),
        messageSource.getMessage("transactionFileExportUtil.debit", null,
            LocaleContextHolder.getLocale()),
        messageSource.getMessage("transactionFileExportUtil.credit", null,
            LocaleContextHolder.getLocale()),
        messageSource.getMessage("transactionFileExportUtil.current.balance", null,
            LocaleContextHolder.getLocale()),
        messageSource.getMessage("transactionFileExportUtil.status", null,
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
      Object[] rowData = {transaction.getTransactionTime(),deviceLocalTxnTimeAndOffSet,
          transaction.getProcessedTime(),
          Long.parseLong(transaction.getTransactionId()),
          Long.parseLong(transaction.getPgTransactionId()), transaction.getCurrency(),
          transaction.getType(), transaction.getDescription(),
          (!"".equals(transaction.getDebit())) ? Double.parseDouble(transaction.getDebit())
              : transaction.getDebit(),
          (!"".equals(transaction.getCredit())) ? Double.parseDouble(transaction.getCredit())
              : transaction.getCredit(),
          Double.parseDouble(transaction.getCurrentBalance()), transaction.getStatus()};
      fileData.add(rowData);
    }

    return fileData;
  }
  
  private List<String> getProcessingTransactionsHeaderList() {
    String[] headerArr = {
        messageSource.getMessage("dash-board.label.transactiontime", null,
            LocaleContextHolder.getLocale()),
        messageSource.getMessage("merchant.common-deviceLocalTxnTime", null,
            LocaleContextHolder.getLocale()),
        messageSource.getMessage("transaction-file-exportutil-accountTransactionId", null,
            LocaleContextHolder.getLocale()),
        messageSource.getMessage("transactionFileExportUtil.transaction.id", null,
            LocaleContextHolder.getLocale()),
        messageSource.getMessage("search-sub-merchant.label.currencycode", null,
            LocaleContextHolder.getLocale()),
        messageSource.getMessage("transactionFileExportUtil.type", null,
            LocaleContextHolder.getLocale()),
        messageSource.getMessage("transactionFileExportUtil.description", null,
            LocaleContextHolder.getLocale()),
        messageSource.getMessage("transactionFileExportUtil.debit", null,
            LocaleContextHolder.getLocale()),
        messageSource.getMessage("transactionFileExportUtil.credit", null,
            LocaleContextHolder.getLocale())};
    return new ArrayList<String>(Arrays.asList(headerArr));
  }

  private static List<Object[]> getProcessingTransactionsFileData(List<AccountTransactionDTO> executedTxnsList) {
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
              transaction.getType() != null ? transaction.getType() + "" : "",
              transaction.getDescription() != null ? transaction.getDescription() + "" : "",
              (!"".equals(transaction.getDebit())) ? Double.parseDouble(transaction.getDebit())
                  : transaction.getDebit(),
              (!"".equals(transaction.getCredit())) ? Double.parseDouble(transaction.getCredit())
                  : transaction.getCredit(),
      };
      fileData.add(rowData);
    }

    return fileData;
  }
  
}
