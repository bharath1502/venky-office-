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
import org.springframework.util.CollectionUtils;
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
import com.chatak.merchant.service.LoginService;
import com.chatak.merchant.service.MerchantInfoService;
import com.chatak.merchant.service.RestPaymentService;
import com.chatak.merchant.service.TransactionService;
import com.chatak.merchant.util.ExportUtil;
import com.chatak.merchant.util.JsonUtil;
import com.chatak.merchant.util.PaginationUtil;
import com.chatak.merchant.util.StringUtil;
import com.chatak.pg.acq.dao.AdminUserDao;
import com.chatak.pg.acq.dao.MerchantDao;
import com.chatak.pg.acq.dao.MerchantProfileDao;
import com.chatak.pg.acq.dao.model.PGAdminUser;
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

  @Autowired
  LoginService loginService;
  
  @Autowired
  private AdminUserDao adminUserDao;

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
    if (!loginService.checkUserActive(session)) {
      model.put(Constants.ERROR, messageSource.getMessage("user.has.been.inactivated", null,
          LocaleContextHolder.getLocale()));
      session.invalidate();
      return new ModelAndView(CHATAK_MERCHANT_LOG_OUT);
    }
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
    transactionsListRequest.setPageSize(Constants.MAX_ENTITIES_PORTAL_DISPLAY_SIZE);
    transactionsListRequest.setPageIndex(Constants.ONE);

    Long merchantId = (Long) session.getAttribute(Constants.LOGIN_USER_MERCHANT_ID);
    PGMerchant pgMerchant = merchantInfoService.getMerchantOnId(merchantId);
    String parentMerchantCode = merchantInfoService.getParentMerchantCode(merchantCode);
    String currency = pgMerchant.getLocalCurrency();
    session.setAttribute(Constants.CURRENCY, currency);
    if (null != pgMerchant) {
      if (fetchMerchantCode(merchantCode, pgMerchant, parentMerchantCode)) {
        transactionsListRequest.setMerchant_code(merchantCode);
      }  else if (StringUtils.isEmpty(merchantCode)) {
        transactionsListRequest.setMerchant_code(pgMerchant.getMerchantCode());
      } else {
        modelAndView.addObject(Constants.ERROR, messageSource.getMessage(
            "chatak.invalid.submerchant.error.message", null, LocaleContextHolder.getLocale()));
        modelAndView.addObject(Constants.TRANSACTION_DIV, Boolean.FALSE);
        modelAndView.addObject(Constants.START_DATE, fromDate);
        modelAndView.addObject(Constants.END_DATE, toDate);
        modelAndView.addObject(Constants.CURRENCY, currency);
        modelAndView.addObject(Constants.RAPID_REVENUE, rapidRevenue);
        modelAndView.addObject(Constants.SUB_MER_REVENUE, subMerRevenue);
        modelAndView.addObject(Constants.MERCHANT_REVENUE, merchantRevenue);
        modelAndView.addObject(Constants.REVENUE_GENERATED_REPORT_LIST,
            new ArrayList<ReportsDTO>());
        return modelAndView;
      }
    }
    try {
    	 session.setAttribute(Constants.TRANSACTIONS_LIST_REQUEST, transactionsListRequest);
      List<ReportsDTO> revenueGeneratedReportList =
          transactionService.getAllExecutedAccTransFeeOnDate(transactionsListRequest);
      if(revenueGeneratedReportList == null) {
        modelAndView.addObject(Constants.ERROR,
            Properties.getProperty("chatak.admin.revenue.error.message"));
        return modelAndView;
      }
         int totalcount = 0;
         totalcount = getTotalCount(revenueGeneratedReportList, totalcount);
         modelAndView.addObject(Constants.TOTAL_RECORDS,totalcount);
      
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
        modelAndView.addObject(Constants.START_DATE, fromDate);
        modelAndView.addObject(Constants.END_DATE, toDate);
        modelAndView.addObject(Constants.CURRENCY, currency);
        modelAndView.addObject(Constants.RAPID_REVENUE, rapidRevenue);
        session.setAttribute(Constants.RAPID_REVENUE, rapidRevenue);
        modelAndView.addObject(Constants.SUB_MER_REVENUE, subMerRevenue);
        session.setAttribute(Constants.SUB_MER_REVENUE, subMerRevenue);
        modelAndView.addObject(Constants.MERCHANT_REVENUE, merchantRevenue);
        session.setAttribute(Constants.REVENUE_TYPE, getRevenueType(revenueType));
        session.setAttribute(Constants.MERCHANT_REVENUE, merchantRevenue);
        modelAndView.addObject(Constants.REVENUE_GENERATED_REPORT_LIST, revenueGeneratedReportList);
        if(!revenueGeneratedReportList.isEmpty()) {
        	modelAndView = PaginationUtil.getPagenationModel(modelAndView, revenueGeneratedReportList.get(0).getNoOfRecords());
        } else {
        	modelAndView = PaginationUtil.getPagenationModel(modelAndView, Constants.ACTIVE_STATUS);
        }
    } catch (Exception e) {
      modelAndView.addObject(Constants.ERROR, messageSource
          .getMessage(Constants.CHATAK_GENERAL_ERROR, null, LocaleContextHolder.getLocale()));
      logger.error("ERROR:: ReportsController:: showGlobalRevenueGeneratedReport method", e);
    }
    logger.info("Exiting:: ReportsController:: showGlobalRevenueGeneratedReport method");
    return modelAndView;
  }

  private String getRevenueType(String revenueType) {
    return StringUtil.isNullAndEmpty(revenueType)?Constants.ALL:revenueType;
  }

	private int getTotalCount(List<ReportsDTO> revenueGeneratedReportList, int totalcount) {
		if (!revenueGeneratedReportList.isEmpty()) {
			totalcount = revenueGeneratedReportList.get(0).getNoOfRecords();
		}
		return totalcount;
	}
  
	@RequestMapping(value = CHATAK_MERCHANT_TRANSACTION_REVENUE_PAGINATION, method = RequestMethod.POST)
	public ModelAndView getPaginationList(final HttpSession session, HttpServletRequest request,
			@FormParam(Constants.PAGE_NUMBER) final Integer pageNumber,
			@FormParam(Constants.TOTAL_RECORDS) final Integer totalRecords, Map model) {
		logger.info("Entering:: SubMerchantController:: getPaginationList method");
		ModelAndView modelAndView = new ModelAndView(SHOW_GLOBAL_REVENUE_GENERATED_REPORT);
		try {
			GetTransactionsListRequest transactionsListRequest = (GetTransactionsListRequest) session
					.getAttribute(Constants.TRANSACTIONS_LIST_REQUEST);
			transactionsListRequest.setPageIndex(pageNumber);
			transactionsListRequest.setNoOfRecords(totalRecords);
			List<ReportsDTO> revenueGeneratedReportList = transactionService
					.getAllExecutedAccTransFeeOnDate(transactionsListRequest);
			if (!CollectionUtils.isEmpty(revenueGeneratedReportList)) {
				modelAndView.addObject(Constants.PAGE_SIZE, transactionsListRequest.getPageSize());
				modelAndView = PaginationUtil.getPagenationModel(modelAndView,
						revenueGeneratedReportList.get(0).getNoOfRecords());
				modelAndView.addObject(Constants.MODEL_ATTRIBUTE_PORTAL_LIST_PAGE_NUMBER, pageNumber);
			    modelAndView.addObject(Constants.MODEL_ATTRIBUTE_PORTAL_LIST_BEGIN_PAGE_NUM, Constants.ONE);
				session.setAttribute(Constants.TOTAL_RECORDS, totalRecords);
				modelAndView.addObject(Constants.TRANSACTION_DIV, Boolean.TRUE);
				modelAndView.addObject(Constants.PAGE_NUMBER, pageNumber);
				modelAndView.addObject(Constants.START_DATE, transactionsListRequest.getFrom_date());
				modelAndView.addObject(Constants.END_DATE, transactionsListRequest.getTo_date());
				modelAndView.addObject(Constants.REVENUE_TYPE, session.getAttribute(Constants.REVENUE_TYPE));
				modelAndView.addObject(Constants.CURRENCY, session.getAttribute(Constants.CURRENCY));
				modelAndView.addObject(Constants.RAPID_REVENUE, session.getAttribute(Constants.RAPID_REVENUE));
				modelAndView.addObject(Constants.SUB_MER_REVENUE, session.getAttribute(Constants.SUB_MER_REVENUE));
				modelAndView.addObject(Constants.MERCHANT_REVENUE, session.getAttribute(Constants.MERCHANT_REVENUE));
				modelAndView.addObject(Constants.REVENUE_GENERATED_REPORT_LIST, revenueGeneratedReportList);
			}

		} catch (Exception e) {
			logger.error("ERROR:: SubMerchantController:: getPaginationList method", e);
			modelAndView.addObject(Constants.ERROR, Properties.getProperty("prepaid.admin.general.error.message"));
		}
		logger.info("Exiting:: SubMerchantController:: getPaginationList method");
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
      @FormParam("downloadType") final String downloadType,@FormParam("downloadAllRecords") final boolean downloadAllRecords,
      @FormParam("totalRecords") final Integer totalRecords, HttpServletResponse response) {
    logger.info("Entering:: ReportsController:: downloadRevenueGeneratedReport method");
    ModelAndView modelAndView = new ModelAndView(SHOW_GLOBAL_REVENUE_GENERATED_REPORT);
    GetTransactionsListRequest transactionsListRequest = (GetTransactionsListRequest) session
			.getAttribute(Constants.TRANSACTIONS_LIST_REQUEST);
    transactionsListRequest.setPageIndex(downLoadPageNumber);
    transactionsListRequest.setPageSize(Constants.MAX_ENTITIES_PORTAL_DISPLAY_SIZE);
    if (downloadAllRecords) {
    	transactionsListRequest.setPageIndex(Constants.ONE);
    	transactionsListRequest.setPageSize(totalRecords);
     }
    
    List<ReportsDTO> revenueGeneratedReportList = transactionService
			.getAllExecutedAccTransFeeOnDate(transactionsListRequest);
    try {
      ExportDetails exportDetails = new ExportDetails();
      if (Constants.PDF_FILE_FORMAT.equalsIgnoreCase(downloadType)) {
        exportDetails.setExportType(ExportType.PDF);
      } else if (Constants.XLS_FILE_FORMAT.equalsIgnoreCase(downloadType)) {
        exportDetails.setExportType(ExportType.XLS);
        exportDetails.setExcelStartRowNumber(Integer.parseInt("4"));
      }
      setExportDetailsDataForDownloadRoleReport(revenueGeneratedReportList, exportDetails);
      ExportUtil.exportData(exportDetails, response, messageSource);
    } catch (Exception e) {
      modelAndView.addObject(Constants.ERROR, messageSource
          .getMessage(Constants.CHATAK_GENERAL_ERROR, null, LocaleContextHolder.getLocale()));
      logger.error("ERROR:: ReportsController:: downloadRevenueGeneratedReport method", e);
    }
    logger.info("Exiting:: ReportsController:: downloadRevenueGeneratedReport method");
    return modelAndView;
  }
  
  private void setExportDetailsDataForDownloadRoleReport(List<ReportsDTO> list,
      ExportDetails exportDetails) {
    exportDetails.setReportName("Revenue");
    exportDetails.setHeaderMessageProperty("chatak.admin.revenue.generated.header.message");
    exportDetails.setHeaderList(getRoleHeaderList());
    exportDetails.setFileData(getRoleFileData(list));
  }
  
  private List<String> getRoleHeaderList() {
    String[] headerArr = {
        messageSource.getMessage("dash-board.label.transactiontime", null,
            LocaleContextHolder.getLocale()),
        messageSource.getMessage("merchant.common-deviceLocalTxnTime", null,
            LocaleContextHolder.getLocale()),
        messageSource.getMessage("reportFileExportUtil.user.name", null,
            LocaleContextHolder.getLocale()),
        messageSource.getMessage("reportFileExportUtil.company", null,
            LocaleContextHolder.getLocale()),
        messageSource.getMessage("reportFileExportUtil.account.number", null,
            LocaleContextHolder.getLocale()),
        messageSource.getMessage("reportFileExportUtil.transaction.id", null, LocaleContextHolder.getLocale()),
        messageSource.getMessage("reportFileExportUtil.transaction.description", null,
            LocaleContextHolder.getLocale()),
        messageSource.getMessage("reportFileExportUtil.total.amount", null,
            LocaleContextHolder.getLocale()),
        messageSource.getMessage("reportFileExportUtil.currency", null, LocaleContextHolder.getLocale()),
        messageSource.getMessage("reportFileExportUtil.rapid.revenue", null, LocaleContextHolder.getLocale()),
        messageSource.getMessage("reportFileExportUtil.merchant.revenue", null,
            LocaleContextHolder.getLocale()),
        messageSource.getMessage("reportFileExportUtil.amt.to.merchant.ac", null,
            LocaleContextHolder.getLocale()),
        messageSource.getMessage("reportFileExportUtil.amt.to.submerchant.ac", null, LocaleContextHolder.getLocale()),
};
    return new ArrayList<String>(Arrays.asList(headerArr));
  }

  private static List<Object[]> getRoleFileData(List<ReportsDTO> reportData) {
    List<Object[]> fileData = new ArrayList<Object[]>();
    for (ReportsDTO repData : reportData) {
      if (!"".equals(repData.getTimeZoneOffset())
          && null != repData.getTimeZoneOffset()) {
        repData.setTimeZoneOffset("(" + repData.getTimeZoneOffset() + ")");
      }
      Object[] rowData = new Object[Integer.parseInt("13")];
      rowData[Integer.parseInt("0")] = repData.getDateTime();
      rowData[Integer.parseInt("1")] = repData.getDeviceLocalTxnTime()+repData.getTimeZoneOffset();
      rowData[Integer.parseInt("2")] = repData.getUserName();
      rowData[Integer.parseInt("3")] = repData.getCompanyName();
      rowData[Integer.parseInt("4")] = repData.getAccountNumber().toString();
      rowData[Integer.parseInt("5")] = repData.getTransactionId();
      rowData[Integer.parseInt("6")] = repData.getDescription();
      rowData[Integer.parseInt("7")] = repData.getAmount();
      rowData[Integer.parseInt("8")] = repData.getCurrency();
      rowData[Integer.parseInt("9")] = repData.getChatakFee();
      rowData[Integer.parseInt("10")] = repData.getFee();
      
      if (StringUtils.isValidString(repData.getParentMerchantId())) {
        rowData[Integer.parseInt("11")] = "NA";
        rowData[Integer.parseInt("12")] = repData.getTotalTxnAmount();
      } else {
        rowData[Integer.parseInt("11")] = repData.getTotalTxnAmount();
        rowData[Integer.parseInt("12")] = "NA";
      }
      fileData.add(rowData);
    }
    return fileData;
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
    session.setAttribute("merchantCodesOptions", new ArrayList(merchantCodes));
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
      @FormParam("totalRecords") final Integer totalRecords,
      @FormParam("downloadAllRecords") final boolean downloadAllRecords) {
    logger.info("Entering :: ReportsController :: processingTransactionsReport method");

    ModelAndView modelAndView = new ModelAndView(CHATAK_MERCHANT_PROCESSING_TRANSACTIONS);
    if (request.getHeader(Constants.REFERER) == null) {
      session.invalidate();
      modelAndView.setViewName(INVALID_REQUEST_PAGE);
      return modelAndView;
    }

    GetTransactionsListRequest transactionRequest = null;
    TransactionListResponse transactionResponse = null;
    List<AccountTransactionDTO> processingTxnList = null;
    try {
      processingTxnList =
          (List<AccountTransactionDTO>) session.getAttribute(Constants.PROCESSING_TXN_LIST);
      if (null != processingTxnList) {
    	  transactionRequest = new GetTransactionsListRequest();
    	  transactionRequest.setPageSize(Constants.MAX_ENTITIES_PORTAL_DISPLAY_SIZE);
    	  transactionRequest.setPageIndex(Constants.ONE);
		  if (!StringUtil.isNullAndEmpty(requestFrom) && "dashobard".equals(requestFrom)) {
			   transactionRequest.setPageSize(Constants.MAX_ENTITY_DISPLAY_SIZE);
			   modelAndView.setViewName(CHATAK_MERCHANT_DASH_BOARD);
		}
        Long merchantId = (Long) session.getAttribute(Constants.LOGIN_USER_MERCHANT_ID);
        if (merchantId != null) {
          GetMerchantDetailsResponse merchantDetailsResponse;
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
      TransactionListResponse transactionResponse, List<AccountTransactionDTO> processingTxnList) throws IOException {
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
      @FormParam("totalRecords") final Integer totalRecords,
      @FormParam("downloadAllRecords") final boolean downloadAllRecords,
      @FormParam("downLoadPageNumber") final Integer downLoadPageNumber) {
    logger.info("Entering:: ReportsController:: executedTransactionsReport method");

    ModelAndView modelAndView = new ModelAndView(CHATAK_MERCHANT_EXECUTED_TRANSACTIONS);
    if (request.getHeader(Constants.REFERER) == null) {
      session.invalidate();
      modelAndView.setViewName(INVALID_REQUEST_PAGE);
      return modelAndView;
    }

    List<AccountTransactionDTO> executedTxnsList = null;
    try {
      GetTransactionsListRequest transaction = null;
      executedTxnsList =
          (List<AccountTransactionDTO>) session.getAttribute(Constants.EXECUTED_TXN_LIST);
      if (null != executedTxnsList) {
        transaction = new GetTransactionsListRequest();
        transaction.setPageSize(Constants.MAX_ENTITIES_PORTAL_DISPLAY_SIZE);
        transaction.setPageIndex(Constants.ONE);
        if (!StringUtil.isNullAndEmpty(requestFrom) && "dashobard".equals(requestFrom)) {
          transaction.setPageSize(Constants.MAX_ENTITY_DISPLAY_SIZE);
          modelAndView.setViewName(CHATAK_MERCHANT_DASH_BOARD);
        }
        GetMerchantDetailsResponse merchantDetailsResponse = null;
        Long merchantId = (Long) session.getAttribute(Constants.LOGIN_USER_MERCHANT_ID);

        if (merchantId != null) {
          TransactionListResponse transactionResponse = new TransactionListResponse();

          merchantDetailsResponse =
              paymentService.getMerchantIdAndTerminalId(merchantId.toString());
          transaction.setMerchant_code(merchantDetailsResponse.getMerchantId());
          //fetching entityId and entityType
          PGAdminUser pgAdminUser  = adminUserDao.findByAdminUserId(Long.valueOf(merchantDetailsResponse.getCreatedBy()));
          if(null != pgAdminUser){
        	  transaction.setEntityId(pgAdminUser.getEntityId());
        	  transaction.setUserType(pgAdminUser.getUserType());
          }
          List<String> txnCodeList = new ArrayList<>(Constants.ELEVEN);
          setTxnCodeList(txnCodeList);

          transaction.setTransactionCodeList(txnCodeList);

          transaction.setSettlementStatus(PGConstants.PG_SETTLEMENT_EXECUTED);

          processDownloadReport(response, downloadType, downloadAllRecords, executedTxnsList,
              transaction, transactionResponse, totalRecords);
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
      GetTransactionsListRequest transaction, TransactionListResponse transactionResponse, Integer totalRecords) throws IOException {
    if (downloadAllRecords) {
    	transaction.setPageIndex(Constants.ONE);
	    transaction.setPageSize(totalRecords);
    }
    executedTxnsList = fetchExecutedTxnList(executedTxnsList, transaction, transactionResponse);
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

	GetTransactionsListResponse executedTxnList = null;
	if (transaction.getUserType().equals(Constants.PM_USER_TYPE)
			|| transaction.getUserType().equals(Constants.ISO_USER_TYPE)) {
		logger.info("LoginController:: fetching executed txn for entityType");
		executedTxnList = transactionService.searchAccountTransactionsForEntityId(transaction, transaction.getEntityId(), transaction.getUserType());
	} else {
		logger.info("LoginController:: fetching executed txn for Merchant");
		executedTxnList = transactionService.searchAccountTransactions(transaction);
	}

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
    	 ExportDetails exportDetails = new ExportDetails();
      if (downloadAll) {
        manualTransactionRequest.setPageIndex(Constants.ONE);
        PGMerchant parentMerchant = merchantProfileDao.getMerchantById(merchantId);
        //fetching entityId and entityType
        PGAdminUser pgAdminUser  = adminUserDao.findByAdminUserId(Long.valueOf(parentMerchant.getCreatedBy()));
        if(null != pgAdminUser){
        	manualTransactionRequest.setEntityId(pgAdminUser.getEntityId());
        	manualTransactionRequest.setUserType(pgAdminUser.getUserType());
        }
        List<PGMerchant> subMerchantList = merchantDao.findById(merchantId);
        List<String> merchantCodeList = new ArrayList<>();
        merchantCodeList.add(parentMerchant.getMerchantCode());
        if (CommonUtil.isListNotNullAndEmpty(subMerchantList)) {
          getSubMerchantCodes(subMerchantList, merchantCodeList);
        }
        String merchantCodes = org.apache.commons.lang.StringUtils.join(merchantCodeList, "|");
        manualTransactionRequest.setMerchant_code(merchantCodes);
        
        GetTransactionsListResponse manualTransactionsReportList = null;
        if (manualTransactionRequest.getUserType().equals(Constants.PM_USER_TYPE)
        		|| manualTransactionRequest.getUserType().equals(Constants.ISO_USER_TYPE)) {
        	logger.info("LoginController:: fetching manual txn for entityType");
        	manualTransactionsReportList = transactionService.searchManualAccountTransactionsForEntityId(manualTransactionRequest, manualTransactionRequest.getEntityId(), manualTransactionRequest.getUserType());
        } else {
        	logger.info("LoginController:: fetching manual txn for Merchant");
        	manualTransactionsReportList =  transactionService.searchManulAccountTransactions(manualTransactionRequest);
        }
    	
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
    	  exportDetails.setExportType(ExportType.PDF);
      } else if (Constants.XLS_FILE_FORMAT.equalsIgnoreCase(downloadType)) {
    	  exportDetails.setExportType(ExportType.XLS);
		  exportDetails.setExcelStartRowNumber(Integer.parseInt("5"));
      }
          setExportDetailsDataForDownloadRoleReports(manualTransferDownloadList, exportDetails);	
	 	  ExportUtil.exportData(exportDetails, response, messageSource);
    } catch (Exception e) {
      modelAndView.addObject(Constants.ERROR, messageSource
          .getMessage(Constants.CHATAK_GENERAL_ERROR, null, LocaleContextHolder.getLocale()));
      logger.error("ERROR:: ReportsController:: manualTransactionsReport method", e);
    }
    logger.info("Exiting:: ReportsController:: manualTransactionsReport method");
    modelAndView.addObject(Constants.EFT_TRANSFER_REPORT_LIST, manualTransferDownloadList);
    return null;
  }

  private void setExportDetailsDataForDownloadRoleReports(List<AccountTransactionDTO> transactionDTO,
	      ExportDetails exportDetails) {
	        exportDetails.setReportName("Manual_Transactions");
	        exportDetails.setHeaderMessageProperty("chatak.header.manual.transactions.reports");
	        exportDetails.setHeaderList(getRoleHeaderLists());
	        exportDetails.setFileData(getRoleFilesData(transactionDTO));
	  }
  
  private List<String> getRoleHeaderLists() {
	    String[] headerArr = {
	        messageSource.getMessage("dash-board.label.transactiontime", null,
	            LocaleContextHolder.getLocale()),
	        messageSource.getMessage("merchant.common-deviceLocalTxnTime", null,
	            LocaleContextHolder.getLocale()),
	        messageSource.getMessage("reports.label.balancereports.manualtransactions.description", null,
	            LocaleContextHolder.getLocale()),
	        messageSource.getMessage("reports.label.balancereports.manualtransactions.merchantorsubmerchantcode", null,
	            LocaleContextHolder.getLocale()),
	        messageSource.getMessage("reports.label.balancereports.manualtransactions.transactionID", null,
		        LocaleContextHolder.getLocale()),
		    messageSource.getMessage("search-sub-merchant.label.currencycode", null,
		        LocaleContextHolder.getLocale()),
		    messageSource.getMessage("reports.label.balancereports.manualtransactions.availableBalance", null,
		        LocaleContextHolder.getLocale()),
		    messageSource.getMessage("reports.label.balancereports.manualtransactions.credit", null,
		        LocaleContextHolder.getLocale()),
	        messageSource.getMessage("reports.label.balancereports.manualtransactions.debit", null,
	            LocaleContextHolder.getLocale())};
	    return new ArrayList<String>(Arrays.asList(headerArr));
	  }
  
  private static List<Object[]> getRoleFilesData(List<AccountTransactionDTO> list) {
	    List<Object[]> fileData = new ArrayList<Object[]>();
	    for (AccountTransactionDTO transactionData : list) {
	    	Object[] rowData = new Object[Integer.parseInt("9")];
			  rowData[0] =(transactionData.getTransactionTime() != null)
					    ? transactionData.getTransactionTime() : " ";
			  rowData[1] = (transactionData.getDeviceLocalTxnTime() != null
			            && transactionData.getTimeZoneOffset() != null)
		                ? transactionData.getDeviceLocalTxnTime() + "( "
		                    + transactionData.getTimeZoneOffset() + " )"
		                : " ";
			  rowData[Integer.parseInt("2")]= descriptionData(transactionData);
			  rowData[Integer.parseInt("3")]= merchantCodeData(transactionData);
			  rowData[Integer.parseInt("4")]= transactionIdData(transactionData);
			  rowData[Integer.parseInt("5")]= currencyData(transactionData);
			  rowData[Integer.parseInt("6")]= currentBalanceData(transactionData);
			  if ("MANUAL_DEBIT".equalsIgnoreCase(transactionData.getTransactionCode())) {
			  rowData[Integer.parseInt("7")]="";
			  rowData[Integer.parseInt("8")]= (transactionData.getDebit() !=null) ? Double.parseDouble(transactionData.getDebit()): 0d;
			  }
			  else {
			  rowData[Integer.parseInt("7")]= (transactionData.getCredit() !=null) ? Double.parseDouble(transactionData.getCredit()): 0d;
			  rowData[Integer.parseInt("8")]="";
			  }
	      fileData.add(rowData);
	    }
	    return fileData;
	  }

	/**
	 * @param transactionData
	 * @return
	 */
	private static double currentBalanceData(AccountTransactionDTO transactionData) {
		return (transactionData.getCurrentBalance() != null) ? Double.parseDouble(transactionData.getCurrentBalance())
				: 0d;
	}

	/**
	 * @param transactionData
	 * @return
	 */
	private static Object currencyData(AccountTransactionDTO transactionData) {
		return (transactionData.getCurrency() != null) ? transactionData.getCurrency() : " ";
	}

	/**
	 * @param transactionData
	 * @return
	 */
	private static Object transactionIdData(AccountTransactionDTO transactionData) {
		return (transactionData.getTransactionId() != null) ? transactionData.getTransactionId() : " ";
	}

	/**
	 * @param transactionData
	 * @return
	 */
	private static Object merchantCodeData(AccountTransactionDTO transactionData) {
		return (transactionData.getMerchantCode() != null) ? transactionData.getMerchantCode() : " ";
	}

	/**
	 * @param transactionData
	 * @return
	 */
	private static Object descriptionData(AccountTransactionDTO transactionData) {
		return (transactionData.getDescription() != null) ? transactionData.getDescription() : " ";
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
          transaction.getType(), !StringUtils.isNullAndEmpty(transaction.getDescription())
		  ? transaction.getDescription().replaceAll("\t", "") : transaction.getDescription(),
          (!"".equals(transaction.getDebit())) ? Double.parseDouble(transaction.getDebit())
              : transaction.getDebit(),
          (!"".equals(transaction.getCredit())) ? Double.parseDouble(transaction.getCredit())
              : transaction.getCredit(),
          (!"".equals(transaction.getCurrentBalance())) ? Double.parseDouble(transaction.getCurrentBalance())
            		  : transaction.getCurrentBalance(),
            		  transaction.getStatus()};
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
