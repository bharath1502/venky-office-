package com.chatak.acquirer.admin.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
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

import com.chatak.acquirer.admin.constants.FeatureConstants;
import com.chatak.acquirer.admin.constants.URLMappingConstants;
import com.chatak.acquirer.admin.controller.model.ExportDetails;
import com.chatak.acquirer.admin.controller.model.LoginResponse;
import com.chatak.acquirer.admin.exception.ChatakAdminException;
import com.chatak.acquirer.admin.model.Response;
import com.chatak.acquirer.admin.model.TransactionResponse;
import com.chatak.acquirer.admin.service.FeeReportService;
import com.chatak.acquirer.admin.service.IsoService;
import com.chatak.acquirer.admin.service.ProgramManagerService;
import com.chatak.acquirer.admin.util.ExportUtil;
import com.chatak.acquirer.admin.util.JsonUtil;
import com.chatak.acquirer.admin.util.PaginationUtil;
import com.chatak.pg.acq.dao.model.Iso;
import com.chatak.pg.bean.settlement.SettlementEntity;
import com.chatak.pg.dao.util.StringUtil;
import com.chatak.pg.enums.ExportType;
import com.chatak.pg.enums.RoleLevel;
import com.chatak.pg.model.FeeReportDto;
import com.chatak.pg.model.FeeReportRequest;
import com.chatak.pg.model.FeeReportResponse;
import com.chatak.pg.model.Merchant;
import com.chatak.pg.user.bean.BankResponse;
import com.chatak.pg.user.bean.IsoRequest;
import com.chatak.pg.user.bean.IsoResponse;
import com.chatak.pg.user.bean.ProgramManagerRequest;
import com.chatak.pg.user.bean.ProgramManagerResponse;
import com.chatak.pg.util.Constants;
import com.chatak.pg.util.DateUtil;
import com.chatak.pg.util.LogHelper;
import com.chatak.pg.util.LoggerMessage;

@SuppressWarnings({"rawtypes", "unchecked"})
@Controller
public class FeeReportController implements URLMappingConstants {

	private static Logger logger = Logger.getLogger(FeeReportController.class);

	  @Autowired
	  MessageSource messageSource;
	  
	  @Autowired
	  ProgramManagerService programManagerService;
	  
	  @Autowired
	  FeeReportService feeReportService;
	  
	  @Autowired
	  IsoService isoService;
	  
	@RequestMapping(value = PREPAID_SHOW_FEE_REPORT_PAGE, method = RequestMethod.GET)
	public ModelAndView showFeeReport(HttpServletRequest request, HttpServletResponse response,
			FeeReportRequest feeReportRequest, BindingResult bindingResult, Map model, HttpSession session) {
		LogHelper.logEntry(logger, LoggerMessage.getCallerName());
		ModelAndView modelAndView = new ModelAndView(PREPAID_FEE_REPORT_PAGE);
		String existingFeature = (String) session.getAttribute(Constants.EXISTING_FEATURES);
	    if (!existingFeature.contains(FeatureConstants.ADMIN_SERVICE_FEE_REPORT_FEATURE_ID)) {
	      return feeReportPermission(session, modelAndView);
	    }
		try {
			modelAndView.addObject(Constants.FEE_REPORT_REQUEST, feeReportRequest);
			modelAndView.addObject("flag", false);
			ProgramManagerRequest programManagerRequest = new ProgramManagerRequest();
			ProgramManagerResponse programManagerResponse = programManagerService
					.getAllProgramManagers(programManagerRequest);
			if (!StringUtil.isNull(programManagerResponse)) {
				model.put("programManagersList", programManagerResponse.getProgramManagersList());
			}
		} catch (ChatakAdminException e) {
			LogHelper.logError(logger, LoggerMessage.getCallerName(), e, Constants.CHATAK_ADMIN_EXCEPTION);
		} catch (Exception e) {
			LogHelper.logError(logger, LoggerMessage.getCallerName(), e, Constants.EXCEPTION);
		}
		LogHelper.logExit(logger, LoggerMessage.getCallerName());
		return modelAndView;
	}

	private ModelAndView feeReportPermission(HttpSession session, ModelAndView modelAndView) {
		session.invalidate();
		modelAndView.setViewName(INVALID_REQUEST_PAGE);
		return modelAndView;
	}
	  
	@RequestMapping(value = PREPAID_PROCESS_FEE_REPORT_PAGE, method = RequestMethod.POST)
	public ModelAndView processFeeReport(HttpServletRequest request, HttpServletResponse response,
			FeeReportRequest feeReportRequest, Merchant merchant, BindingResult bindingResult, Map model,
			HttpSession session) {
		LogHelper.logEntry(logger, LoggerMessage.getCallerName());
		ModelAndView modelAndView = new ModelAndView(PREPAID_FEE_REPORT_PAGE);
		String existingFeature = (String) session.getAttribute(Constants.EXISTING_FEATURES);
		if (!existingFeature.contains(FeatureConstants.ADMIN_SERVICE_FEE_REPORT_FEATURE_ID)) {
			return feeReportPermission(session, modelAndView);
		}
		try {
			feeReportRequest.setPageIndex(Constants.ONE);
			feeReportRequest.setPageSize(Constants.DEFAULT_PAGE_SIZE);
			session.setAttribute(Constants.FEE_REPORT_REQUEST_LIST_EXPORTDATA, feeReportRequest);
			FeeReportResponse feeReportResponse = feeReportService.fetchFeeTransactions(feeReportRequest);
			if (!StringUtil.isNull(feeReportResponse)
					&& StringUtil.isListNotNullNEmpty(feeReportResponse.getFeeReportDto())) {
				model.put(Constants.FEE_TRANSACTIONS_SEARCH_LIST, feeReportResponse.getFeeReportDto());
			}
			modelAndView = PaginationUtil.getPagenationModel(modelAndView,
					feeReportRequest.getNoOfRecords().intValue());
			showFeeReport(request, response, feeReportRequest, bindingResult, model, session);
		} catch (ChatakAdminException e) {
			showFeeReport(request, response, feeReportRequest, bindingResult, model, session);
			LogHelper.logError(logger, LoggerMessage.getCallerName(), e, Constants.CHATAK_ADMIN_EXCEPTION);
		} catch (Exception e) {
			showFeeReport(request, response, feeReportRequest, bindingResult, model, session);
			LogHelper.logError(logger, LoggerMessage.getCallerName(), e, Constants.EXCEPTION);
		}

		modelAndView.addObject("flag", true);
		LogHelper.logExit(logger, LoggerMessage.getCallerName());
		return modelAndView;
	}
	  
	@RequestMapping(value = PREPAID_SHOW_ISO_FEE_REPORT, method = RequestMethod.POST)
	public ModelAndView showISOFeeReport(HttpSession session,
		      @FormParam("getISOId") final Long getISOId, @FormParam("getFromDate") final String getFromDate, @FormParam("getFromDate") final String getToDate, Map<String, Object> model,
		      HttpServletRequest request, HttpServletResponse response) {
		LogHelper.logEntry(logger, LoggerMessage.getCallerName());
		ModelAndView modelAndView = new ModelAndView(PREPAID_ISO_FEE_REPORT);
		String existingFeature = (String) session.getAttribute(Constants.EXISTING_FEATURES);
		if (!existingFeature.contains(FeatureConstants.ADMIN_SERVICE_FEE_REPORT_FEATURE_ID)) {
			return feeReportPermission(session, modelAndView);
		}
		FeeReportRequest feeReportRequest = new FeeReportRequest();
		feeReportRequest.setIsoId(getISOId);
		feeReportRequest.setFromDate(getFromDate);
		feeReportRequest.setToDate(getToDate);
		modelAndView.addObject(Constants.FEE_REPORT_REQUEST, feeReportRequest);
		modelAndView.addObject("flag", false);
		try {
			FeeReportResponse feeReportResponse = feeReportService.fetchISOFeeTransactions(feeReportRequest);
			if(!StringUtil.isNull(feeReportResponse) && StringUtil.isListNotNullNEmpty(feeReportResponse.getSettlementEntity())) {
				model.put("isoFeeList", feeReportResponse.getSettlementEntity());
				model.put("totalRecords", feeReportResponse.getSettlementEntity().size());
			}
		} catch (ChatakAdminException e) {
			LogHelper.logError(logger, LoggerMessage.getCallerName(), e, Constants.CHATAK_ADMIN_EXCEPTION);
		}
		LogHelper.logExit(logger, LoggerMessage.getCallerName());
		return modelAndView;
	}
	
	@RequestMapping(value = PREPAID_FEE_REPORT_PAGINATION, method = RequestMethod.POST)
	  public ModelAndView getFeeReportPagination(final HttpSession session,
	      @FormParam(Constants.PAGE_NUMBER) final Integer pageNumber,
	      @FormParam("totalRecords") final Integer totalRecords, Map model) {
		LogHelper.logEntry(logger, LoggerMessage.getCallerName());
	    ModelAndView modelAndView = new ModelAndView(PREPAID_FEE_REPORT_PAGE);
	    try {
	    	FeeReportRequest feeReportRequest = new FeeReportRequest();
	      model.put(Constants.FEE_REPORT_REQUEST, feeReportRequest);
	      LoginResponse loginResponse =
	          (LoginResponse) session.getAttribute(Constants.LOGIN_RESPONSE_DATA);
	      feeReportRequest =
	          (FeeReportRequest) session.getAttribute(Constants.FEE_REPORT_REQUEST_LIST_EXPORTDATA);
	      feeReportRequest.setCreatedBy(loginResponse.getUserId().toString());
	      feeReportRequest.setPageIndex(pageNumber);
	      feeReportRequest.setNoOfRecords(totalRecords);
	      FeeReportResponse feeReportResponse = feeReportService.fetchFeeTransactions(feeReportRequest);
			if (!StringUtil.isNull(feeReportResponse)
					&& StringUtil.isListNotNullNEmpty(feeReportResponse.getFeeReportDto())) {
				model.put(Constants.FEE_TRANSACTIONS_SEARCH_LIST, feeReportResponse.getFeeReportDto());
	        modelAndView = PaginationUtil.getPagenationModelSuccessive(modelAndView, pageNumber,
	        		feeReportResponse.getTotalNoOfRows());
	        session.setAttribute(Constants.PAGE_NUMBER, pageNumber);
	        session.setAttribute("totalRecords", totalRecords);
			}
	    } catch (Exception e) {
	      modelAndView.addObject(Constants.ERROR,
	          messageSource.getMessage(Constants.CHATAK_GENERAL_ERROR, null, LocaleContextHolder.getLocale()));
	      LogHelper.logError(logger, LoggerMessage.getCallerName(), e, Constants.EXCEPTION);
	    }
	    LogHelper.logExit(logger, LoggerMessage.getCallerName());
	    return modelAndView;
	  }
	
	@RequestMapping(value = DOWNLOAD_FEE_TXN_REPORT, method = RequestMethod.POST)
	public ModelAndView downloadFeeTxnReport(HttpServletRequest request, HttpServletResponse response,
			HttpSession session, Map model, @FormParam("downLoadPageNumber") final Integer downLoadPageNumber,
			@FormParam("downloadType") final String downloadType, @FormParam("totalRecords") final Integer totalRecords,
			@FormParam("downloadAllRecords") final boolean downloadAllRecords) {
		LogHelper.logEntry(logger, LoggerMessage.getCallerName());
		try {
			FeeReportRequest feeReportRequest = (FeeReportRequest) session
					.getAttribute(Constants.FEE_REPORT_REQUEST_LIST_EXPORTDATA);
			LoginResponse loginResponse = (LoginResponse) session.getAttribute(Constants.LOGIN_RESPONSE_DATA);
			feeReportRequest.setCreatedBy(loginResponse.getUserId().toString());
			feeReportRequest.setPageIndex(downLoadPageNumber);
			Integer pageSize = feeReportRequest.getPageSize();
			if (downloadAllRecords) {
				feeReportRequest.setPageIndex(Constants.ONE);
				feeReportRequest.setPageSize(totalRecords);
			}
			FeeReportResponse feeReportResponse = feeReportService.fetchFeeTransactions(feeReportRequest);
			ExportDetails exportDetails = new ExportDetails();
			if (!StringUtil.isNull(feeReportResponse)
					&& StringUtil.isListNotNullNEmpty(feeReportResponse.getFeeReportDto())) {
				if (Constants.PDF_FILE_FORMAT.equalsIgnoreCase(downloadType)) {
					exportDetails.setExportType(ExportType.PDF);
				} else if (Constants.XLS_FILE_FORMAT.equalsIgnoreCase(downloadType)) {
					exportDetails.setExportType(ExportType.XLS);
					exportDetails.setExcelStartRowNumber(Integer.parseInt("5"));
				}
				feeReportHeader(feeReportResponse.getFeeReportDto(), exportDetails);
				ExportUtil.exportData(exportDetails, response, messageSource);
			}
			feeReportRequest.setPageSize(pageSize);
		} catch (Exception e) {
			LogHelper.logError(logger, LoggerMessage.getCallerName(), e, Constants.EXCEPTION);
		}
		LogHelper.logExit(logger, LoggerMessage.getCallerName());
		return null;
	}
	
	private void feeReportHeader(List<FeeReportDto> feeProgramList, ExportDetails exportDetails) {
		exportDetails.setReportName("Fee_Report_");
		exportDetails.setHeaderMessageProperty("fee-report.label.fee.report.download");

		exportDetails.setHeaderList(getFeeReportHeaderList());
		exportDetails.setFileData(getFeeReportFileData(feeProgramList));
	}
	
	private List<String> getFeeReportHeaderList() {
		String[] headerArr = {
				messageSource.getMessage("admin.iso.label.message", null, LocaleContextHolder.getLocale()),
				messageSource.getMessage("fee-report.label.fee.report.totalamount", null,
						LocaleContextHolder.getLocale()) };
		return new ArrayList<String>(Arrays.asList(headerArr));
	}
	
	private static List<Object[]> getFeeReportFileData(List<FeeReportDto> feeReportList) {
		List<Object[]> fileData = new ArrayList<Object[]>();
		for (FeeReportDto feeReportDto : feeReportList) {
			Object[] rowData = { feeReportDto.getIsoName(), feeReportDto.getIsoEarnedAmount() };
			fileData.add(rowData);
		}
		return fileData;
	}
	
	@RequestMapping(value = SHOW_ISO_REVENUE_REPORT_PAGE, method = RequestMethod.GET)
	public ModelAndView showIsoRevenueReport(HttpServletRequest request, HttpServletResponse response,
			FeeReportRequest feeReportRequest, BindingResult bindingResult, Map model, HttpSession session) {
		LogHelper.logEntry(logger, LoggerMessage.getCallerName());
		ModelAndView modelAndView = new ModelAndView(ISO_REVENUE_REPORT_PAGE);
		String userType = (String) session.getAttribute(Constants.LOGIN_USER_TYPE);
		String existingFeature = (String) session.getAttribute(Constants.EXISTING_FEATURES);
		if (!existingFeature.contains(FeatureConstants.ADMIN_SERVICE_ISO_REVENUE_REPORT_FEATURE_ID)) {
			return feeReportPermission(session, modelAndView);
		}
		try {

			if (userType.equals(Constants.ISO_USER_TYPE)) {
				Iso iso = isoService.findIsoByIsoId(Long.valueOf(session.getId())).get(0);
				feeReportRequest.setIsoId(iso.getId());
				iso.getIsoName();
				feeReportRequest.setIsoName(iso.getIsoName());
				ProgramManagerRequest programManagerRequest = isoService.findPmByIsoId(Long.valueOf(session.getId()))
						.get(0);
				feeReportRequest.setProgramManagerId(programManagerRequest.getId().toString());
				feeReportRequest.setProgramManagerName(programManagerRequest.getProgramManagerName());
			} else if (userType.equals(Constants.PM_USER_TYPE)) {
				ProgramManagerRequest programManagerRequest = isoService.findPmByIsoId(Long.valueOf(session.getId()))
						.get(0);
				feeReportRequest.setProgramManagerId(programManagerRequest.getId().toString());
				feeReportRequest.setProgramManagerName(programManagerRequest.getProgramManagerName());
			} else {
				ProgramManagerRequest programManagerRequest = new ProgramManagerRequest();
				ProgramManagerResponse programManagerResponse = programManagerService
						.getAllProgramManagers(programManagerRequest);
				if (!StringUtil.isNull(programManagerResponse)) {
					model.put("programManagersList", programManagerResponse.getProgramManagersList());
				}
			}
			feeReportRequest.setEntityType(userType);
			modelAndView.addObject(Constants.FEE_REPORT_REQUEST, feeReportRequest);
			modelAndView.addObject("flag", false);
		} catch (ChatakAdminException e) {
			LogHelper.logError(logger, LoggerMessage.getCallerName(), e, Constants.CHATAK_ADMIN_EXCEPTION);
		} catch (Exception e) {
			LogHelper.logError(logger, LoggerMessage.getCallerName(), e, Constants.EXCEPTION);
		}
		LogHelper.logExit(logger, LoggerMessage.getCallerName());
		return modelAndView;
	}

	@RequestMapping(value = PROCESS_ISO_REVENUE_REPORT_PAGE, method = RequestMethod.POST)
	public ModelAndView processIsoRevenueReport(HttpServletRequest request, HttpServletResponse response,
			FeeReportRequest feeReportRequest, BindingResult bindingResult, Map model, HttpSession session) {
		LogHelper.logEntry(logger, LoggerMessage.getCallerName());
		ModelAndView modelAndView = new ModelAndView(ISO_REVENUE_REPORT_PAGE);
		String existingFeature = (String) session.getAttribute(Constants.EXISTING_FEATURES);
		if (!existingFeature.contains(FeatureConstants.ADMIN_SERVICE_ISO_REVENUE_REPORT_FEATURE_ID)) {
			return feeReportPermission(session, modelAndView);
		}

		try {
			feeReportRequest.setPageIndex(Constants.ONE);
			feeReportRequest.setPageSize(Constants.DEFAULT_PAGE_SIZE);
			session.setAttribute(Constants.FEE_REPORT_REQUEST_LIST_EXPORTDATA, feeReportRequest);
			FeeReportResponse feeReportResponse = feeReportService.fetchIsoRevenueTransactions(feeReportRequest);
			if (!StringUtil.isNull(feeReportResponse)
					&& StringUtil.isListNotNullNEmpty(feeReportResponse.getSettlementEntity())) {
				model.put(Constants.FEE_TRANSACTIONS_SEARCH_LIST, feeReportResponse.getSettlementEntity());
			}
			modelAndView = PaginationUtil.getPagenationModel(modelAndView,
					feeReportRequest.getNoOfRecords().intValue());
			showIsoRevenueReport(request, response, feeReportRequest, bindingResult, model, session);
		} catch (Exception e) {
			showIsoRevenueReport(request, response, feeReportRequest, bindingResult, model, session);
			LogHelper.logError(logger, LoggerMessage.getCallerName(), e, Constants.EXCEPTION);
		}
		return modelAndView;

	}

	@RequestMapping(value = PREPAID_ADMIN_FETCH_PARTNER_FOR_ENTITY, method = RequestMethod.GET)
	public @ResponseBody String getPartnerByPMId(HttpServletRequest request, HttpServletResponse response,
			Map<String, Object> model, @FormParam("ProgrammanagerId") final Long programManagerId,
			HttpSession session) {
		LogHelper.logEntry(logger, LoggerMessage.getCallerName());
		try {
			IsoResponse isoResponse = new IsoResponse();
			FeeReportRequest feeReportRequest = new FeeReportRequest();
			feeReportRequest.setPageIndex(Constants.ONE);
			feeReportRequest.setPageSize(Constants.DEFAULT_PAGE_SIZE);
			feeReportRequest.setProgramManagerId(programManagerId.toString());
			List<IsoRequest> isoRequestList = isoService
					.findIsoByProgramaManagerId(Long.valueOf(feeReportRequest.getProgramManagerId()));
			isoResponse.setIsoRequest(isoRequestList);
			isoResponse.setErrorMessage(Constants.SUCESS);
			return JsonUtil.convertObjectToJSON(isoResponse);
		} catch (Exception e) {
			LogHelper.logError(logger, LoggerMessage.getCallerName(), e, Constants.EXCEPTION);
		}
		LogHelper.logExit(logger, LoggerMessage.getCallerName());
		return null;
	}

	@RequestMapping(value = SHOW_MATCHED_TRANSACTIONS_REPORT_PAGE, method = RequestMethod.POST)
	public ModelAndView showIsoAndMerchantMatchedTxnsByPgTxnId(HttpSession session,
			@FormParam("issuanceSettlementEntityId") final Long issuanceSettlementEntityId, Map<String, Object> model,
			HttpServletRequest request, HttpServletResponse response) {
		LogHelper.logEntry(logger, LoggerMessage.getCallerName());
		
		ModelAndView modelAndView = new ModelAndView(ISO_REVENUE_REPORT_PAGE);
		FeeReportRequest transactionRequest = new FeeReportRequest();
		transactionRequest.setIssuanceSettlementEntityId(issuanceSettlementEntityId);
		try {
			TransactionResponse transactionResponse = feeReportService
					.getAllMatchedTxnsByEntityId(transactionRequest.getIssuanceSettlementEntityId());
			ExportDetails exportDetails = new ExportDetails();
			if (!StringUtil.isNull(transactionResponse)
					&& StringUtil.isListNotNullNEmpty(transactionResponse.getSettlementEntity())) {
				exportDetails.setExportType(ExportType.XLS);
				exportDetails.setExcelStartRowNumber(Integer.parseInt("5"));
				exportDetails.setReportName("Matched_Transactions_");
				exportDetails.setHeaderMessageProperty("matched-transactions.label.matchedtxns");
				Map<String, String> map = new HashMap<>();
                
                Long isoId = transactionResponse.getSettlementEntity().get(0).getIsoId();
                Iso iso = isoService.findIsoByIsoId(isoId).get(0);
                map.put("ISO :", iso.getIsoName());
                
                String pmid = transactionResponse.getSettlementEntity().get(0).getAcqPmId();
                ProgramManagerRequest programManagerRequest = programManagerService.findbyProgramManagerId(Long.valueOf(pmid));
                map.put("Program Manager :", programManagerRequest.getProgramManagerName());
                exportDetails.setMap(map);
				exportDetails.setHeaderList(getSettlementReportHeaderList());
				exportDetails.setFileData(getSettlementReportFileData(transactionResponse.getSettlementEntity()));
				ExportUtil.exportData(exportDetails, response, messageSource);
			} else {
				modelAndView = showIsoRevenueReport(request, response, transactionRequest, null, model, session);
				modelAndView.addObject(Constants.ERROR, messageSource
						.getMessage("matched-transactions.label.nomatchedtxns", null, LocaleContextHolder.getLocale()));
			}
		} catch (Exception e) {
			LogHelper.logError(logger, LoggerMessage.getCallerName(), e, Constants.CHATAK_ADMIN_EXCEPTION);
			modelAndView = showIsoRevenueReport(request, response, transactionRequest, null, model, session);
		}
		LogHelper.logExit(logger, LoggerMessage.getCallerName());
		return modelAndView;
	}

	private List<String> getSettlementReportHeaderList() {
		LogHelper.logEntry(logger, LoggerMessage.getCallerName());
		String[] headerArr = {
				messageSource.getMessage("fee-report.label.fee.report.merchantid", null,
						LocaleContextHolder.getLocale()),
				messageSource.getMessage("matched-transactions.label.terminal.id", null,
						LocaleContextHolder.getLocale()),
				messageSource.getMessage("home.label.transactionid", null, LocaleContextHolder.getLocale()),
				messageSource.getMessage("fundtransferfile.proc.txn.id", null, LocaleContextHolder.getLocale()),
				messageSource.getMessage("transaction-report-batchID", null, LocaleContextHolder.getLocale()),
				messageSource.getMessage("admin.label.pmamount", null, LocaleContextHolder.getLocale()),
				messageSource.getMessage("admin.label.isoamount", null, LocaleContextHolder.getLocale()),
				messageSource.getMessage("admin.label.merchantamount", null, LocaleContextHolder.getLocale()),
				messageSource.getMessage("show-dynamic-MDR-edit.label.transactiontype", null,
						LocaleContextHolder.getLocale()),
				messageSource.getMessage("admin.common-deviceLocalTxnTime", null, LocaleContextHolder.getLocale()),
				messageSource.getMessage("admin.label.issPartner", null, LocaleContextHolder.getLocale()),
				messageSource.getMessage("admin.label.TimeZoneRegion", null, LocaleContextHolder.getLocale()),
				messageSource.getMessage("admin.label.Batchdate", null, LocaleContextHolder.getLocale()),
				messageSource.getMessage("reports.label.pendingtransactions.username", null, LocaleContextHolder.getLocale()),
				messageSource.getMessage("virtual-terminal-void.label.cardholdername", null, LocaleContextHolder.getLocale()),
				messageSource.getMessage("reports.label.pendingtransactions.transactiondescription", null, LocaleContextHolder.getLocale()),
				messageSource.getMessage("admin.label.merchantsettlementstatus", null, LocaleContextHolder.getLocale()),
				messageSource.getMessage("admin.label.TxnCurrencyCode", null, LocaleContextHolder.getLocale()),
				messageSource.getMessage("admin.label.panmasked", null, LocaleContextHolder.getLocale()),
				messageSource.getMessage("admin.label.settlement.batch.status", null, LocaleContextHolder.getLocale()),
				messageSource.getMessage("admin.label.Acq.Txn.Mode", null, LocaleContextHolder.getLocale()),
				messageSource.getMessage("admin.label.Acq.channel", null, LocaleContextHolder.getLocale()),
				messageSource.getMessage("virtual-terminal-void.label.invoicenumber", null, LocaleContextHolder.getLocale()),				
		};
		LogHelper.logExit(logger, LoggerMessage.getCallerName());
		return new ArrayList<>(Arrays.asList(headerArr));
	}

	private static List<Object[]> getSettlementReportFileData(List<SettlementEntity> list) {
		LogHelper.logEntry(logger, LoggerMessage.getCallerName());
		List<Object[]> fileData = new ArrayList<>();
		for (SettlementEntity settlementData : list) {
			Object[] rowData = { settlementData.getMerchantId(), settlementData.getTerminalId(),
					settlementData.getPgTxnId(), settlementData.getIssTxnId(), settlementData.getBatchId(),
					conversionAmountValidation(settlementData.getPmAmount()),
					conversionAmountValidation(settlementData.getIsoAmount()),
					conversionAmountValidation(settlementData.getMerchantAmount()), settlementData.getTransactionType(),
					settlementData.getDeviceLocalTxnTime(), settlementData.getIssPartner(),  settlementData.getTimeZoneRegion(),
					settlementData.getBatchDate(), settlementData.getUserName(), settlementData.getCardHolderName(),
					settlementData.getTxnDesc(), settlementData.getMerchantSettlementStatus(), settlementData.getTxnCurrencyCode(),
					settlementData.getPanMasked(), settlementData.getSettlementBatchStatus(), settlementData.getAcqTxnMode(),
					settlementData.getAcqChannel(), settlementData.getInVoiceNumber() };
			fileData.add(rowData);
		}
		LogHelper.logExit(logger, LoggerMessage.getCallerName());
		return fileData;
	}

	private static Object conversionAmountValidation(Long amount) {
		Double amountvalue = null;
		try {
			if (amount != null) {
				amountvalue = Double.parseDouble(amount.toString()) / Constants.ONE_HUNDRED;

			} else {
				amountvalue = 0.0;
			}
		} catch (Exception e) {
			LogHelper.logError(logger, LoggerMessage.getCallerName(), e, e.getMessage());
		}
		return amountvalue;
	}

	@RequestMapping(value = SHOW_MERCHANT_REVENUE_REPORT_PAGE, method = RequestMethod.GET)
	public ModelAndView showMerchantRevenueReport(HttpServletRequest request, HttpServletResponse response,
			FeeReportRequest feeReportRequest, BindingResult bindingResult, Map model, HttpSession session) {
		LogHelper.logEntry(logger, LoggerMessage.getCallerName());
		ModelAndView modelAndView = new ModelAndView(MERCHANT_REVENUE_REPORT_PAGE);
		String userType = (String) session.getAttribute(Constants.LOGIN_USER_TYPE);
		String existingFeature = (String) session.getAttribute(Constants.EXISTING_FEATURES);
		if (!existingFeature.contains(FeatureConstants.ADMIN_SERVICE_MERCHANT_REVENUE_REPORT_FEATURE_ID)) {
			return feeReportPermission(session, modelAndView);
		}
		try {

			if (userType.equals(Constants.ISO_USER_TYPE)) {
				Iso iso = isoService.findIsoByIsoId(Long.valueOf(session.getId())).get(0);
				feeReportRequest.setIsoId(iso.getId());
				iso.getIsoName();
				feeReportRequest.setIsoName(iso.getIsoName());
				ProgramManagerRequest programManagerRequest = isoService.findPmByIsoId(Long.valueOf(session.getId()))
						.get(0);
				feeReportRequest.setProgramManagerId(programManagerRequest.getId().toString());
				feeReportRequest.setProgramManagerName(programManagerRequest.getProgramManagerName());
			} else if (userType.equals(Constants.PM_USER_TYPE)) {
				ProgramManagerRequest programManagerRequest = isoService.findPmByIsoId(Long.valueOf(session.getId()))
						.get(0);
				feeReportRequest.setProgramManagerId(programManagerRequest.getId().toString());
				feeReportRequest.setProgramManagerName(programManagerRequest.getProgramManagerName());
			} else {
				ProgramManagerRequest programManagerRequest = new ProgramManagerRequest();
				ProgramManagerResponse programManagerResponse = programManagerService
						.getAllProgramManagers(programManagerRequest);
				if (!StringUtil.isNull(programManagerResponse)) {
					model.put("programManagersList", programManagerResponse.getProgramManagersList());
				}
			}
			feeReportRequest.setEntityType(userType);
			modelAndView.addObject(Constants.FEE_REPORT_REQUEST, feeReportRequest);
			modelAndView.addObject("flag", false);
		} catch (ChatakAdminException e) {
			LogHelper.logError(logger, LoggerMessage.getCallerName(), e, Constants.CHATAK_ADMIN_EXCEPTION);
		} catch (Exception e) {
			LogHelper.logError(logger, LoggerMessage.getCallerName(), e, Constants.EXCEPTION);
		}
		LogHelper.logExit(logger, LoggerMessage.getCallerName());
		return modelAndView;
	}

	@RequestMapping(value = PROCESS_MERCHANT_REVENUE_REPORT_PAGE, method = RequestMethod.POST)
	public ModelAndView processMerchantRevenueReport(HttpServletRequest request, HttpServletResponse response,
			FeeReportRequest feeReportRequest, BindingResult bindingResult, Map model, HttpSession session) {
		LogHelper.logEntry(logger, LoggerMessage.getCallerName());
		ModelAndView modelAndView = new ModelAndView(MERCHANT_REVENUE_REPORT_PAGE);
		String existingFeature = (String) session.getAttribute(Constants.EXISTING_FEATURES);
		if (!existingFeature.contains(FeatureConstants.ADMIN_SERVICE_MERCHANT_REVENUE_REPORT_FEATURE_ID)) {
			return feeReportPermission(session, modelAndView);
		}

		try {
			feeReportRequest.setPageIndex(Constants.ONE);
			feeReportRequest.setPageSize(Constants.DEFAULT_PAGE_SIZE);
			session.setAttribute(Constants.FEE_REPORT_REQUEST_LIST_EXPORTDATA, feeReportRequest);
			FeeReportResponse feeReportResponse = feeReportService.fetchMerchantRevenueTransactions(feeReportRequest);
			if (!StringUtil.isNull(feeReportResponse)
					&& StringUtil.isListNotNullNEmpty(feeReportResponse.getSettlementEntity())) {
				model.put(Constants.FEE_TRANSACTIONS_SEARCH_LIST, feeReportResponse.getSettlementEntity());
			}
			modelAndView = PaginationUtil.getPagenationModel(modelAndView,
					feeReportRequest.getNoOfRecords().intValue());
			showIsoRevenueReport(request, response, feeReportRequest, bindingResult, model, session);
		} catch (Exception e) {
			showIsoRevenueReport(request, response, feeReportRequest, bindingResult, model, session);
			LogHelper.logError(logger, LoggerMessage.getCallerName(), e, Constants.EXCEPTION);
		}
		return modelAndView;

	}

	@RequestMapping(value = PREPAID_MERCHANT_REPORT_PAGINATION, method = RequestMethod.POST)
	public ModelAndView getMerchantReportPagination(final HttpSession session,
			@FormParam(Constants.PAGE_NUMBER) final Integer pageNumber,
			@FormParam("totalRecords") final Integer totalRecords, Map model) {
		LogHelper.logEntry(logger, LoggerMessage.getCallerName());
		ModelAndView modelAndView = new ModelAndView(MERCHANT_REVENUE_REPORT_PAGE);
		try {
			FeeReportRequest feeReportRequest = new FeeReportRequest();
			model.put(Constants.FEE_REPORT_REQUEST, feeReportRequest);
			feeReportRequest.setPageIndex(pageNumber);
			feeReportRequest.setNoOfRecords(totalRecords);
			FeeReportResponse feeReportResponse = feeReportService.fetchMerchantRevenueTransactions(feeReportRequest);
			if (!StringUtil.isNull(feeReportResponse)
					&& StringUtil.isListNotNullNEmpty(feeReportResponse.getSettlementEntity())) {
				model.put(Constants.FEE_TRANSACTIONS_SEARCH_LIST, feeReportResponse.getSettlementEntity());
				modelAndView = PaginationUtil.getPagenationModelSuccessive(modelAndView, pageNumber,
						feeReportResponse.getTotalNoOfRows());
				session.setAttribute(Constants.PAGE_NUMBER, pageNumber);
				session.setAttribute("totalRecords", totalRecords);
			}
		} catch (Exception e) {
			modelAndView.addObject(Constants.ERROR,
					messageSource.getMessage(Constants.CHATAK_GENERAL_ERROR, null, LocaleContextHolder.getLocale()));
			LogHelper.logError(logger, LoggerMessage.getCallerName(), e, Constants.EXCEPTION);
		}
		LogHelper.logExit(logger, LoggerMessage.getCallerName());
		return modelAndView;
	}

	@RequestMapping(value = PREPAID_ISO_REPORT_PAGINATION, method = RequestMethod.POST)
	public ModelAndView getISOReportPagination(final HttpSession session,
			@FormParam(Constants.PAGE_NUMBER) final Integer pageNumber,
			@FormParam("totalRecords") final Integer totalRecords, Map model) {
		LogHelper.logEntry(logger, LoggerMessage.getCallerName());
		ModelAndView modelAndView = new ModelAndView(ISO_REVENUE_REPORT_PAGE);
		try {
			FeeReportRequest feeReportRequest = new FeeReportRequest();
			model.put(Constants.FEE_REPORT_REQUEST, feeReportRequest);
			feeReportRequest.setPageIndex(pageNumber);
			feeReportRequest.setNoOfRecords(totalRecords);
			FeeReportResponse feeReportResponse = feeReportService.fetchMerchantRevenueTransactions(feeReportRequest);
			if (!StringUtil.isNull(feeReportResponse)
					&& StringUtil.isListNotNullNEmpty(feeReportResponse.getSettlementEntity())) {
				model.put(Constants.FEE_TRANSACTIONS_SEARCH_LIST, feeReportResponse.getSettlementEntity());
				modelAndView = PaginationUtil.getPagenationModelSuccessive(modelAndView, pageNumber,
						feeReportResponse.getTotalNoOfRows());
				session.setAttribute(Constants.PAGE_NUMBER, pageNumber);
				session.setAttribute("totalRecords", totalRecords);
			}
		} catch (Exception e) {
			modelAndView.addObject(Constants.ERROR,
					messageSource.getMessage(Constants.CHATAK_GENERAL_ERROR, null, LocaleContextHolder.getLocale()));
			LogHelper.logError(logger, LoggerMessage.getCallerName(), e, Constants.EXCEPTION);
		}
		LogHelper.logExit(logger, LoggerMessage.getCallerName());
		return modelAndView;
	}

	@RequestMapping(value = DOWNLOAD_ISO_REPORT, method = RequestMethod.POST)
	public ModelAndView downloadISORevenueReport(HttpServletRequest request, HttpServletResponse response,
			HttpSession session, Map model, @FormParam("downLoadPageNumber") final Integer downLoadPageNumber,
			@FormParam("downloadType") final String downloadType, @FormParam("totalRecords") final Integer totalRecords,
			@FormParam("downloadAllRecords") final boolean downloadAllRecords) {
		LogHelper.logEntry(logger, LoggerMessage.getCallerName());
		try {
			FeeReportRequest feeReportRequest = (FeeReportRequest) session
					.getAttribute(Constants.FEE_REPORT_REQUEST_LIST_EXPORTDATA);
			feeReportRequest.setPageIndex(downLoadPageNumber);
			Integer pageSize = feeReportRequest.getPageSize();
			if (downloadAllRecords) {
				feeReportRequest.setPageIndex(Constants.ONE);
				feeReportRequest.setPageSize(totalRecords);
			}
			FeeReportResponse feeReportResponse = feeReportService.fetchIsoRevenueTransactions(feeReportRequest);
			ExportDetails exportDetails = new ExportDetails();
			if (!StringUtil.isNull(feeReportResponse)
					&& StringUtil.isListNotNullNEmpty(feeReportResponse.getSettlementEntity())) {
				if (Constants.PDF_FILE_FORMAT.equalsIgnoreCase(downloadType)) {
					exportDetails.setExportType(ExportType.PDF);
				} else if (Constants.XLS_FILE_FORMAT.equalsIgnoreCase(downloadType)) {
					exportDetails.setExportType(ExportType.XLS);
					exportDetails.setExcelStartRowNumber(Integer.parseInt("5"));
				}
				isoReportHeader(feeReportResponse.getSettlementEntity(), exportDetails);
				ExportUtil.exportData(exportDetails, response, messageSource);
			}
			feeReportRequest.setPageSize(pageSize);
		} catch (Exception e) {
			LogHelper.logError(logger, LoggerMessage.getCallerName(), e, Constants.EXCEPTION);
		}
		LogHelper.logExit(logger, LoggerMessage.getCallerName());
		return null;
	}

	private void isoReportHeader(List<SettlementEntity> settlementEntityList, ExportDetails exportDetails) {
		exportDetails.setReportName("Fee_Report_");
		exportDetails.setHeaderMessageProperty("fee-report.label.fee.report.download");

		exportDetails.setHeaderList(getIsoReportHeaderList());
		exportDetails.setFileData(getIsoReportFileData(settlementEntityList));
	}

	private List<String> getIsoReportHeaderList() {
		String[] headerArr = {
				messageSource.getMessage("fee-report.label.fee.report.merchantid", null,
						LocaleContextHolder.getLocale()),
				messageSource.getMessage("home.label.acqsaleamunt", null, LocaleContextHolder.getLocale()),
				messageSource.getMessage("home.label.issunceAmunt", null, LocaleContextHolder.getLocale()),
				messageSource.getMessage("admin.label.isoamount", null, LocaleContextHolder.getLocale()),
				messageSource.getMessage("transaction-report-batchID", null, LocaleContextHolder.getLocale()) };
		return new ArrayList<>(Arrays.asList(headerArr));
	}

	private static List<Object[]> getIsoReportFileData(List<SettlementEntity> settlementEntityList) {
		List<Object[]> fileData = new ArrayList<>();
		for (SettlementEntity settlementEntity : settlementEntityList) {
			Object[] rowData = { settlementEntity.getMerchantId(), settlementEntity.getAcquirerAmount(),
					settlementEntity.getIssAmount(), settlementEntity.getIsoAmount(), settlementEntity.getBatchId() };
			fileData.add(rowData);
		}
		return fileData;
	}

	@RequestMapping(value = DOWNLOAD_MERCHANT_REPORT, method = RequestMethod.POST)
	public ModelAndView downloadMerchantRevenueReport(HttpServletRequest request, HttpServletResponse response,
			HttpSession session, Map model, @FormParam("downLoadPageNumber") final Integer downLoadPageNumber,
			@FormParam("downloadType") final String downloadType, @FormParam("totalRecords") final Integer totalRecords,
			@FormParam("downloadAllRecords") final boolean downloadAllRecords) {
		LogHelper.logEntry(logger, LoggerMessage.getCallerName());
		try {
			FeeReportRequest feeReportRequest = (FeeReportRequest) session
					.getAttribute(Constants.FEE_REPORT_REQUEST_LIST_EXPORTDATA);
			feeReportRequest.setPageIndex(downLoadPageNumber);
			Integer pageSize = feeReportRequest.getPageSize();
			if (downloadAllRecords) {
				feeReportRequest.setPageIndex(Constants.ONE);
				feeReportRequest.setPageSize(totalRecords);
			}
			FeeReportResponse feeReportResponse = feeReportService.fetchMerchantRevenueTransactions(feeReportRequest);
			ExportDetails exportDetails = new ExportDetails();
			if (!StringUtil.isNull(feeReportResponse)
					&& StringUtil.isListNotNullNEmpty(feeReportResponse.getSettlementEntity())) {
				if (Constants.PDF_FILE_FORMAT.equalsIgnoreCase(downloadType)) {
					exportDetails.setExportType(ExportType.PDF);
				} else if (Constants.XLS_FILE_FORMAT.equalsIgnoreCase(downloadType)) {
					exportDetails.setExportType(ExportType.XLS);
					exportDetails.setExcelStartRowNumber(Integer.parseInt("5"));
				}
				merchantReportHeader(feeReportResponse.getSettlementEntity(), exportDetails);
				ExportUtil.exportData(exportDetails, response, messageSource);
			}
			feeReportRequest.setPageSize(pageSize);
		} catch (Exception e) {
			LogHelper.logError(logger, LoggerMessage.getCallerName(), e, Constants.EXCEPTION);
		}
		LogHelper.logExit(logger, LoggerMessage.getCallerName());
		return null;
	}

	private void merchantReportHeader(List<SettlementEntity> settlementEntityList, ExportDetails exportDetails) {
		exportDetails.setReportName("Fee_Report_");
		exportDetails.setHeaderMessageProperty("fee-report.label.fee.report.download");

		exportDetails.setHeaderList(getMerchantReportHeaderList());
		exportDetails.setFileData(getMerchantReportFileData(settlementEntityList));
	}

	private List<String> getMerchantReportHeaderList() {
		String[] headerArr = {
				messageSource.getMessage("fee-report.label.fee.report.merchantid", null,
						LocaleContextHolder.getLocale()),
				messageSource.getMessage("home.label.acqsaleamunt", null, LocaleContextHolder.getLocale()),
				messageSource.getMessage("home.label.issunceAmunt", null, LocaleContextHolder.getLocale()),
				messageSource.getMessage("admin.label.merchantamount", null, LocaleContextHolder.getLocale()),
				messageSource.getMessage("transaction-report-batchID", null, LocaleContextHolder.getLocale()) };
		return new ArrayList<>(Arrays.asList(headerArr));
	}

	private static List<Object[]> getMerchantReportFileData(List<SettlementEntity> settlementEntityList) {
		List<Object[]> fileData = new ArrayList<>();
		for (SettlementEntity settlementEntity : settlementEntityList) {
			Object[] rowData = { settlementEntity.getMerchantId(), settlementEntity.getAcquirerAmount(),
					settlementEntity.getIssAmount(), settlementEntity.getMerchantAmount(),
					settlementEntity.getBatchId() };
			fileData.add(rowData);
		}
		return fileData;
	}
	
	@RequestMapping(value = SHOW_PM_REVENUE_REPORT_PAGE, method = RequestMethod.GET)
	public ModelAndView showPmRevenueReport(HttpServletRequest request, HttpServletResponse response,
			FeeReportRequest feeReportRequest, BindingResult bindingResult, Map model, HttpSession session) {
		LogHelper.logEntry(logger, LoggerMessage.getCallerName());
		ModelAndView modelAndView = new ModelAndView(PM_REVENUE_REPORT_PAGE);
		String existingFeature = (String) session.getAttribute(Constants.EXISTING_FEATURES);
		if (!existingFeature.contains(FeatureConstants.ADMIN_SERVICE_PM_REVENUE_REPORT_FEATURE_ID)) {
			return feeReportPermission(session, modelAndView);
		}
		try {
			modelAndView.addObject(Constants.FEE_REPORT_REQUEST, feeReportRequest);
			modelAndView.addObject("flag", false);
			ProgramManagerRequest programManagerRequest = new ProgramManagerRequest();
			ProgramManagerResponse programManagerResponse = programManagerService
					.getAllProgramManagers(programManagerRequest);
			if (!StringUtil.isNull(programManagerResponse)) {
				model.put("programManagersList", programManagerResponse.getProgramManagersList());
			}
		} catch (ChatakAdminException e) {
			LogHelper.logError(logger, LoggerMessage.getCallerName(), e, Constants.CHATAK_ADMIN_EXCEPTION);
		} catch (Exception e) {
			LogHelper.logError(logger, LoggerMessage.getCallerName(), e, Constants.EXCEPTION);
		}
		LogHelper.logExit(logger, LoggerMessage.getCallerName());
		return modelAndView;
	}
	
	@RequestMapping(value = PROCESS_PM_REVENUE_REPORT_PAGE, method = RequestMethod.POST)
	public ModelAndView processPmRevenueReport(HttpServletRequest request, HttpServletResponse response,
			FeeReportRequest feeReportRequest, Merchant merchant, BindingResult bindingResult, Map model,
			HttpSession session) {
		LogHelper.logEntry(logger, LoggerMessage.getCallerName());
		ModelAndView modelAndView = new ModelAndView(PM_REVENUE_REPORT_PAGE);
		String existingFeature = (String) session.getAttribute(Constants.EXISTING_FEATURES);
		if (!existingFeature.contains(FeatureConstants.ADMIN_SERVICE_PM_REVENUE_REPORT_FEATURE_ID)) {
			return feeReportPermission(session, modelAndView);
		}
		try {
			feeReportRequest.setPageIndex(Constants.ONE);
			feeReportRequest.setPageSize(Constants.DEFAULT_PAGE_SIZE);
			session.setAttribute(Constants.FEE_REPORT_REQUEST_LIST_EXPORTDATA, feeReportRequest);
			FeeReportResponse feeReportResponse = feeReportService.fetchPmRevenueTransactions(feeReportRequest);
			if (!StringUtil.isNull(feeReportResponse)
					&& StringUtil.isListNotNullNEmpty(feeReportResponse.getSettlementEntity())) {
				model.put(Constants.FEE_TRANSACTIONS_SEARCH_LIST, feeReportResponse.getSettlementEntity());
			}
			modelAndView = PaginationUtil.getPagenationModel(modelAndView,
					feeReportRequest.getNoOfRecords().intValue());
			showFeeReport(request, response, feeReportRequest, bindingResult, model, session);
		} catch (ChatakAdminException e) {
			showFeeReport(request, response, feeReportRequest, bindingResult, model, session);
			LogHelper.logError(logger, LoggerMessage.getCallerName(), e, Constants.CHATAK_ADMIN_EXCEPTION);
		} catch (Exception e) {
			showFeeReport(request, response, feeReportRequest, bindingResult, model, session);
			LogHelper.logError(logger, LoggerMessage.getCallerName(), e, Constants.EXCEPTION);
		}

		modelAndView.addObject("flag", true);
		LogHelper.logExit(logger, LoggerMessage.getCallerName());
		return modelAndView;
	}
	
}
