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
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.chatak.acquirer.admin.constants.URLMappingConstants;
import com.chatak.acquirer.admin.controller.model.ExportDetails;
import com.chatak.acquirer.admin.controller.model.LoginResponse;
import com.chatak.acquirer.admin.exception.ChatakAdminException;
import com.chatak.acquirer.admin.model.MerchantAccountSearchResponse;
import com.chatak.acquirer.admin.service.MerchantAccountService;
import com.chatak.acquirer.admin.util.ExportUtil;
import com.chatak.acquirer.admin.util.JsonUtil;
import com.chatak.acquirer.admin.util.PaginationUtil;
import com.chatak.acquirer.admin.util.StringUtil;
import com.chatak.pg.bean.Response;
import com.chatak.pg.constants.PGConstants;
import com.chatak.pg.enums.ExportType;
import com.chatak.pg.model.AccountBalanceDTO;
import com.chatak.pg.user.bean.MerchantAccountSearchDto;
import com.chatak.pg.util.Constants;

@SuppressWarnings({"rawtypes", "unchecked"})
@Controller
public class MerchantAccountBalancesController implements URLMappingConstants {
	
  private static Logger logger = Logger.getLogger(MerchantAccountBalancesController.class);
  
  private static final String ACCOUNT_BALANCE = "accountBalance";

  @Autowired
  MerchantAccountService merchantAccountService;

  @Autowired
  private MessageSource messageSource;

  @RequestMapping(value = SHOW_ACC_MANUAL_CREDIT, method = RequestMethod.GET)
  public ModelAndView showAccManualCredit(HttpServletRequest request, Map model,
      HttpSession session) {
    logger.info("Entering:: MerchantAccountBalancesController:: showAccManualCredit method");
    ModelAndView modelAndView = new ModelAndView(SHOW_ACC_MANUAL_CREDIT);

    AccountBalanceDTO accountBalance = new AccountBalanceDTO();
    modelAndView.addObject(ACCOUNT_BALANCE, accountBalance);
    return modelAndView;
  }

  @RequestMapping(value = SHOW_ACC_MANUAL_DEBIT, method = RequestMethod.GET)
  public ModelAndView showAccManualDebit(HttpServletRequest request, Map model,
      HttpSession session) {
    logger.info("Entering:: MerchantAccountBalancesController:: showAccManualDebit method");
    ModelAndView modelAndView = new ModelAndView(SHOW_ACC_MANUAL_DEBIT);

    AccountBalanceDTO accountBalance = new AccountBalanceDTO();
    modelAndView.addObject(ACCOUNT_BALANCE, accountBalance);
    return modelAndView;
  }

  @RequestMapping(value = PROCESS_ACC_MANUAL_CREDIT, method = RequestMethod.POST)
  public ModelAndView processAccManualCredit(HttpServletRequest request,
      HttpServletResponse response, Map model, HttpSession session,
      AccountBalanceDTO accountBalance) {
    logger.info("Entering:: MerchantAccountBalancesController:: processAccManualCredit method");
    ModelAndView modelAndView = new ModelAndView(SHOW_ACC_MANUAL_CREDIT);
    if (request.getHeader("referer") == null) {
      session.invalidate();
      modelAndView.setViewName(INVALID_REQUEST_PAGE);
      return modelAndView;
    }
    try {
    	 Response creditResponse = merchantAccountService.processMerchantAccountBalanceUpdate(accountBalance,
          PGConstants.PAYMENT_METHOD_CREDIT);
    	 LoginResponse loginResponse =
             (LoginResponse) session.getAttribute(Constants.LOGIN_RESPONSE_DATA);
         Long entityId = loginResponse.getEntityId();
         String userType = loginResponse.getUserType();
      if (null != creditResponse
          && creditResponse.getErrorCode().equalsIgnoreCase(Constants.SUCCESS_CODE)) {
        accountBalance =
            merchantAccountService.getAccountBalanceDTO(accountBalance.getMerchantCode(), entityId, userType);
      } else {
        modelAndView.addObject(Constants.ERROR, messageSource
            .getMessage("merchant.should.be.active", null, LocaleContextHolder.getLocale()));
      }
      modelAndView.addObject(ACCOUNT_BALANCE, accountBalance);
      modelAndView.addObject("creditResponse", creditResponse);
    } catch (Exception e) {
      modelAndView.addObject(Constants.ERROR,
          messageSource.getMessage(Constants.CHATAK_GENERAL_ERROR, null, LocaleContextHolder.getLocale()));
      logger.error(
          "ERROR :: MerchantAccountBalancesController :: processAccManualCredit method:" + e);
    }
    return modelAndView;
  }

  @RequestMapping(value = PROCESS_ACC_MANUAL_DEBIT, method = RequestMethod.POST)
  public ModelAndView processAccManualDebit(HttpServletRequest request, Map model,
      HttpSession session, AccountBalanceDTO accountBalance) {
    logger.info("Entering:: MerchantAccountBalancesController:: processAccManualDebit method");
    ModelAndView modelAndView = new ModelAndView(SHOW_ACC_MANUAL_DEBIT);
    if (request.getHeader("referer") == null) {
      session.invalidate();
      modelAndView.setViewName(INVALID_REQUEST_PAGE);
      return modelAndView;
    }
    try {
    	Response debitResponse = merchantAccountService.processMerchantAccountBalanceUpdate(accountBalance,
          PGConstants.PAYMENT_METHOD_DEBIT);
    	LoginResponse loginResponse =
            (LoginResponse) session.getAttribute(Constants.LOGIN_RESPONSE_DATA);
        Long entityId = loginResponse.getEntityId();
        String userType = loginResponse.getUserType();
      if (null != debitResponse && debitResponse.getErrorCode().equalsIgnoreCase(Constants.SUCCESS_CODE)) {
        accountBalance = merchantAccountService.getAccountBalanceDTO(accountBalance.getMerchantCode(), entityId, userType);
      } else {
        accountBalance.setInputAmount(accountBalance.getInputAmount() / Constants.MAX_PAGE_SIZE);
        if(null != debitResponse) {
        	modelAndView.addObject(Constants.ERROR, debitResponse.getErrorMessage());
        } else {
        	modelAndView.addObject(Constants.ERROR, "");
        }
      }
      modelAndView.addObject(ACCOUNT_BALANCE, accountBalance);
      modelAndView.addObject("debitResponse", debitResponse);
    } catch (Exception e) {
      modelAndView.addObject(Constants.ERROR,
          messageSource.getMessage(Constants.CHATAK_GENERAL_ERROR, null, LocaleContextHolder.getLocale()));
      logger
          .error("ERROR :: MerchantAccountBalancesController :: processAccManualDebit method:" + e);
    }
    return modelAndView;
  }

  @RequestMapping(value = AJAX_MERCHANT_BALANCE_DETAILS, method = RequestMethod.GET)
  public @ResponseBody String fetchAccountDetialsbyMerchantCode(Map model,
      HttpServletRequest request, HttpServletResponse response, HttpSession session) throws ChatakAdminException {
    ModelAndView modelAndView = new ModelAndView(SHOW_ACC_MANUAL_CREDIT);
    modelAndView.addObject(Constants.ERROR, null);
    logger.info(
        "Entering :: MerchantAccountBalancesController :: fetchAccountDetialsbyMerchantCode method");
    String merchantId = request.getParameter("merchantId");
    AccountBalanceDTO accountBalance;
    try {
      if(StringUtil.isNullAndEmpty(merchantId)){
        throw new Exception();
      }
      LoginResponse loginResponse =
          (LoginResponse) session.getAttribute(Constants.LOGIN_RESPONSE_DATA);
      Long entityId = loginResponse.getEntityId();
      String userType = loginResponse.getUserType();
      accountBalance = merchantAccountService.getAccountBalanceDTO(merchantId, entityId, userType);
      return JsonUtil.convertObjectToJSON(accountBalance);
    } catch (ChatakAdminException ex) {
      Response rsp = new Response();
      rsp.setErrorCode(ex.getErrorCode());
      rsp.setErrorMessage(ex.getErrorMessage());
      return JsonUtil.convertObjectToJSON(rsp);
    } catch (Exception e) {
      modelAndView.addObject(Constants.ERROR, messageSource
          .getMessage(Constants.CHATAK_GENERAL_ERROR, null, LocaleContextHolder.getLocale()));
      logger.error(
          "ERROR :: MerchantAccountBalancesController :: fetchAccountDetialsbyMerchantCode method:"
              + e);
    }
    logger.info(
        "Exiting :: MerchantAccountBalancesController :: fetchAccountDetialsbyMerchantCode method");
    return merchantId;
  }

  @RequestMapping(value = CHATAK_SUB_MERCHANT_ACCOUNT_SEARCH_REPORT, method = RequestMethod.POST)
  public ModelAndView downloadSubMerchantAccountReport(HttpSession session, Map model,
      HttpServletRequest request, HttpServletResponse response,
      @FormParam("downLoadPageNumber") final Integer downLoadPageNumber,
      @FormParam("downloadType") final String downloadType,
      @FormParam(PGConstants.TOTAL_RECORDS) final Integer totalRecords,
      @FormParam("downloadAllRecords") final boolean downloadAllRecords) {
    logger.info("Entering:: MerchantController:: downloadMerchantReport method");
    ModelAndView modelAndView = new ModelAndView(CHATAK_SUB_MERCHANT_ACCOUNT_SEARCH_PAGE);
    MerchantAccountSearchDto merchantAccountSearchDto = null;
    try {
      merchantAccountSearchDto =
          (MerchantAccountSearchDto) session.getAttribute(Constants.MERCHANT_ACCOUNT_SEARCH_MODEL);
      Map<String, String> merchantDataMap =
          (Map<String, String>) session.getAttribute(PGConstants.MERCHANT_DATA_MAP);
      MerchantAccountSearchResponse searchResponse = null;
      merchantAccountSearchDto.setPageIndex(downLoadPageNumber);
      Integer pageSize = merchantAccountSearchDto.getPageSize();

      if (null == merchantDataMap) {
        merchantDataMap = merchantAccountService.getMerchantMapByMerchantType(PGConstants.MERCHANT);
        session.setAttribute(PGConstants.MERCHANT_DATA_MAP, new HashMap(merchantDataMap));
      }
      modelAndView.addObject(PGConstants.MERCHANT_DATA_MAP, merchantDataMap);
      if (downloadAllRecords) {
        merchantAccountSearchDto.setPageIndex(Constants.ONE);
        merchantAccountSearchDto.setPageSize(totalRecords);
      }
      searchResponse =
          merchantAccountService.searchMerchantAccount(merchantAccountSearchDto, merchantDataMap);
      List<MerchantAccountSearchDto> list = searchResponse.getMerchantAccountSearchDtoList();
      ExportDetails exportDetails = new ExportDetails();
      if (StringUtil.isListNotNullNEmpty(list)) {
        if (Constants.PDF_FILE_FORMAT.equalsIgnoreCase(downloadType)) {
          exportDetails.setExportType(ExportType.PDF);
        } else if (Constants.XLS_FILE_FORMAT.equalsIgnoreCase(downloadType)) {
          exportDetails.setExportType(ExportType.XLS);
          exportDetails.setExcelStartRowNumber(Integer.parseInt("5"));
      }
      setExportDetailsDataForDownloadRoleReport(list, exportDetails); 
      ExportUtil.exportData(exportDetails, response, messageSource);
      }
      merchantAccountSearchDto.setPageSize(pageSize);
    } catch (Exception e) {
      modelAndView.addObject(Constants.ERROR,
          messageSource.getMessage(Constants.CHATAK_GENERAL_ERROR, null, LocaleContextHolder.getLocale()));
      logger.error("ERROR:: MerchantController:: downloadMerchantReport method", e);
    }
    logger.info("Exiting:: MerchantController:: downloadMerchantReport method");
    return null;
  }
  
  private void setExportDetailsDataForDownloadRoleReport(List<MerchantAccountSearchDto> merchantAccountData,
      ExportDetails exportDetails) {
    exportDetails.setReportName("Sub_Merchant_Accounts_");
    exportDetails.setHeaderMessageProperty("chatak.header.sub.merchant.account.messages");

    exportDetails.setHeaderList(getRoleHeaderList());
    exportDetails.setFileData(getRoleFileData(merchantAccountData));
  }

  @RequestMapping(value = CHATAK_SUB_MERCHANT_ACCOUNT_SEARCH_PAGINATION,
      method = RequestMethod.POST)
  public ModelAndView subMerchantAccountSearchPagination(final HttpSession session,
      @FormParam("pageNumber") final Integer pageNumber,
      @FormParam(PGConstants.TOTAL_RECORDS) final Integer totalRecords,
      @FormParam("sortProperty") final String sortProperty, Map model) {
    logger.info("Entering:: MerchantController:: showMerchantAccountSearchPage method");
    ModelAndView modelAndView = new ModelAndView(CHATAK_SUB_MERCHANT_ACCOUNT_SEARCH_PAGE);

    MerchantAccountSearchDto merchantAccountSearchDto =
        (MerchantAccountSearchDto) session.getAttribute(Constants.MERCHANT_ACCOUNT_SEARCH_MODEL);
    merchantAccountSearchDto.setPageIndex(pageNumber);
    merchantAccountSearchDto.setNoOfRecords(totalRecords);
    try {

      Map<String, String> merchantDataMap =
          (Map<String, String>) session.getAttribute(PGConstants.MERCHANT_DATA_MAP);

      if (null == merchantDataMap) {
        merchantDataMap = merchantAccountService.getMerchantMapByMerchantType(PGConstants.MERCHANT);
        session.setAttribute(PGConstants.MERCHANT_DATA_MAP, new HashMap(merchantDataMap));
      }

      modelAndView.addObject(PGConstants.MERCHANT_DATA_MAP, merchantDataMap);

      MerchantAccountSearchResponse searchResponse =
          merchantAccountService.searchMerchantAccount(merchantAccountSearchDto, merchantDataMap);
      modelAndView.addObject(PGConstants.SEARCH_RESPONSE, searchResponse);
      modelAndView.addObject("sortProperty", (null != sortProperty) ? sortProperty : "");
      if (searchResponse != null
          && !CollectionUtils.isEmpty(searchResponse.getMerchantAccountSearchDtoList())) {
        modelAndView.addObject(PGConstants.PAGE_SIZE, merchantAccountSearchDto.getPageSize());
        modelAndView = PaginationUtil.getPagenationModelSuccessive(modelAndView, pageNumber,
            searchResponse.getTotalNoOfRows());
      }
      modelAndView.addObject(PGConstants.MERCHANT_ACCOUNT_SEARCH_DTO, merchantAccountSearchDto);
    } catch (Exception e) {
      modelAndView.addObject(Constants.ERROR,
          messageSource.getMessage(Constants.CHATAK_GENERAL_ERROR, null, LocaleContextHolder.getLocale()));
      logger.error("ERROR:: MerchantController:: showMerchantAccountSearchPage method", e);
    }
    logger.info("Exiting:: MerchantController:: showMerchantAccountSearchPage method");
    return modelAndView;
  }
  
  private List<String> getRoleHeaderList() {
	  String[] headerArr = {
			  messageSource.getMessage("merchant-file-exportutil-subMerchantName", null,
					  LocaleContextHolder.getLocale()),
			  messageSource.getMessage("merchant-file-exportutil-merchantName", null,
					  LocaleContextHolder.getLocale()),
			  messageSource.getMessage("merchant-file-exportutil-state", null,
					  LocaleContextHolder.getLocale()),
			  messageSource.getMessage("merchant-file-exportutil-accountNumber", null,
					  LocaleContextHolder.getLocale()),
			  messageSource.getMessage("merchant-file-exportutil-contactPerson", null,
					  LocaleContextHolder.getLocale()),
			  messageSource.getMessage("merchant-file-exportutil-currentBalance", null,
					  LocaleContextHolder.getLocale()),
			  messageSource.getMessage("merchant-file-exportutil-status", null,
					  LocaleContextHolder.getLocale())};
	  return new ArrayList<String>(Arrays.asList(headerArr));
  }

  private static List<Object[]> getRoleFileData(List<MerchantAccountSearchDto> merchantAccountData) {
    List<Object[]> fileData = new ArrayList<Object[]>();

    for (MerchantAccountSearchDto merchantData : merchantAccountData) {

      Object[] rowData = {merchantData.getBusinessName(), merchantData.getParentBusinessName(),
          merchantData.getBankState(), merchantData.getMerchantAccountNumber(),
          merchantData.getBankAccountName(), Double.parseDouble(merchantData.getCurrentBalance()),
          merchantData.getAccountStatus()

      };
      fileData.add(rowData);
    }

    return fileData;
  }
}

