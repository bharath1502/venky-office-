package com.chatak.merchant.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.ws.rs.FormParam;

import org.apache.commons.lang.StringUtils;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.chatak.merchant.constants.URLMappingConstants;
import com.chatak.merchant.exception.ChatakPayException;
import com.chatak.merchant.model.GetTransactionResponse;
import com.chatak.merchant.model.TransactionResponse;
import com.chatak.merchant.service.AccountService;
import com.chatak.merchant.service.RestPaymentService;
import com.chatak.merchant.service.TransactionService;
import com.chatak.merchant.util.PaginationUtil;
import com.chatak.pg.acq.dao.AdminUserDao;
import com.chatak.pg.acq.dao.MerchantDao;
import com.chatak.pg.acq.dao.MerchantProfileDao;
import com.chatak.pg.acq.dao.model.PGAdminUser;
import com.chatak.pg.acq.dao.model.PGMerchant;
import com.chatak.pg.bean.TransactionPopUpDataDto;
import com.chatak.pg.constants.AccountTransactionCode;
import com.chatak.pg.constants.FeatureConstants;
import com.chatak.pg.constants.PGConstants;
import com.chatak.pg.model.VirtualTerminalRefundDTO;
import com.chatak.pg.model.VirtualTerminalVoidDTO;
import com.chatak.pg.user.bean.GetTransactionsListRequest;
import com.chatak.pg.user.bean.GetTransactionsListResponse;
import com.chatak.pg.util.CommonUtil;
import com.chatak.pg.util.Constants;

@SuppressWarnings({"rawtypes", "unchecked"})
@Controller
public class MerchantTransactionsController implements URLMappingConstants {

  private static Logger logger = Logger.getLogger(TransactionsController.class);

  @Autowired
  MessageSource messageSource;

  @Autowired
  private TransactionService transactionService;

  @Autowired
  private RestPaymentService paymentService;

  @Autowired
  AccountService accountService;

  @Autowired
  private MerchantDao merchantDao;

  @Autowired
  MerchantProfileDao merchantProfileDao;
  
  @Autowired
  private AdminUserDao adminUserDao;

  @RequestMapping(value = CHATAK_MERCHANT_TRANSACTION_REFUND_PAGE, method = RequestMethod.POST)
  public ModelAndView showTransactionRefund(HttpServletRequest request,
      HttpServletResponse response, HttpSession session, Map model,
      @FormParam("getRefundTxnId") final String getRefundTxnId,
      @FormParam("terminalId") final String terminalId,
      final RedirectAttributes redirectAttributes) {
    logger.info("Entering :: TransactionsController :: showTransactionRefund method");
    String merchantId = request.getParameter("merchantId");
    ModelAndView modelAndView = new ModelAndView(SHOW_CHATAK_MERCHANT_TRANSACTION_REFUND);
    String existingFeature = (String) session.getAttribute(Constants.EXISTING_FEATURES);

    if (null == request.getHeader(Constants.REFERER)) {
      return validateHeader(session, modelAndView);
    }

    if (!existingFeature
        .contains(FeatureConstants.MERCHANT_SERVICE_REPORTS_TRANSACTION_FEATURE_ID)) {
      return validateHeader(session, modelAndView);
    }
    GetTransactionResponse getTransactionResponse;
    VirtualTerminalRefundDTO virtualTeminalRefund = new VirtualTerminalRefundDTO();
    try {
      boolean hasAccess = transactionService
          .hasAccessTo(FeatureConstants.REFUND_SUB_MERCHANT_TRANSACTIONS, merchantId, session);

      if (hasAccess) {

        getTransactionResponse =
            paymentService.getTransactionByRefId(merchantId, terminalId, getRefundTxnId, "sale");
        if (null != getTransactionResponse) {
          virtualTeminalRefund.setCardNum(getTransactionResponse.getCardNumMasked());
          virtualTeminalRefund.setInvoiceNumber(getTransactionResponse.getInvoiceNumber());
          virtualTeminalRefund.setExpDate(getTransactionResponse.getExpDate());
          virtualTeminalRefund.setFeeAmount(getTransactionResponse.getFeeAmount());
          virtualTeminalRefund.setCardHolderName(getTransactionResponse.getCardHolderName());
          virtualTeminalRefund.setSubTotal(getTransactionResponse.getSubTotal());
          virtualTeminalRefund.setTxnAmount(getTransactionResponse.getTxnAmount());
          virtualTeminalRefund.setCgRefNumber(getTransactionResponse.getCgRefNumber());
          virtualTeminalRefund.setTxnRefNumber(getTransactionResponse.getTxnRefNum());
          virtualTeminalRefund.setMerchantId(getTransactionResponse.getMerchantId());
          virtualTeminalRefund.setAuthId(getTransactionResponse.getAuthId());
          virtualTeminalRefund.setTerminalId(getTransactionResponse.getTerminalId());
        }
        modelAndView.addObject(Constants.VIRTUAL_TEMINAL_REFUND, virtualTeminalRefund);
      } else {
        modelAndView.setViewName(Constants.REDIRECT_PG + CHATAK_MERCHANT_SEARCH_TRANSACTION_PAGE);
        redirectAttributes.addAttribute(Constants.REDIRECTION_ERROR, Constants.ACCESS_DENIED);
      }
    } catch (ChatakPayException e) {
      modelAndView.addObject(Constants.ERROR, e.getMessage());
      logger.error("ERROR :: TransactionsController :: fetchTransactionbyRefId method:" + e);
    } catch (Exception e) {
      modelAndView.addObject(Constants.ERROR, messageSource
          .getMessage(Constants.CHATAK_GENERAL_ERROR, null, LocaleContextHolder.getLocale()));
      logger.error("ERROR :: TransactionsController :: fetchTransactionbyRefId method:" + e);
    }

    logger.info("Exiting :: TransactionsController :: showTransactionRefund method");
    return modelAndView;

  }

  @RequestMapping(value = CHATAK_MERCHANT_TRANSACTION_VOID_PROCESS, method = RequestMethod.POST)
  public ModelAndView fetchTransactionForVoid(Map model, HttpServletRequest request,
      HttpServletResponse response, @FormParam("transactionID") final String transactionID,
      @FormParam("terminalId") final String terminalId, HttpSession session,
      final RedirectAttributes redirectAttributes) {
    String merchantId = request.getParameter("merchantId");
    ModelAndView modelAndView = new ModelAndView(CHATAK_MERCHANT_TRANSACTION_VOID_PAGE);
    String existingFeature = (String) session.getAttribute(Constants.EXISTING_FEATURES);

    if (request.getHeader(Constants.REFERER) == null) {
      return validateHeader(session, modelAndView);
    }

    if (!existingFeature
        .contains(FeatureConstants.MERCHANT_SERVICE_REPORTS_TRANSACTION_FEATURE_ID)) {
      return validateHeader(session, modelAndView);
    }
    modelAndView.addObject(Constants.ERROR, null);
    logger.info("Entering :: TransactionsController :: fetchTransactionForVoid method");

    GetTransactionResponse getTransactionResponse;
    VirtualTerminalVoidDTO virtualTeminalVoid = new VirtualTerminalVoidDTO();
    try {

      boolean hasAccess = transactionService
          .hasAccessTo(FeatureConstants.VOID_SUB_MERCHANT_TRANSACTIONS, merchantId, session);

      if (hasAccess) {
        getTransactionResponse = paymentService.getTransactionByRefId(merchantId, terminalId,
            transactionID, PGConstants.TXN_TYPE_SALE);
        if (null != getTransactionResponse) {
          virtualTeminalVoid.setCardNum(getTransactionResponse.getCardNumMasked());
          virtualTeminalVoid.setInvoiceNumber(getTransactionResponse.getInvoiceNumber());
          virtualTeminalVoid.setFeeAmount(getTransactionResponse.getFeeAmount());
          virtualTeminalVoid.setExpDate(getTransactionResponse.getExpDate());
          virtualTeminalVoid.setSubTotal(getTransactionResponse.getSubTotal());
          virtualTeminalVoid.setTxnAmount(getTransactionResponse.getTxnAmount());
          virtualTeminalVoid.setTxnRefNumber(getTransactionResponse.getTxnRefNum());
          virtualTeminalVoid.setCgRefNumber(getTransactionResponse.getCgRefNumber());
          virtualTeminalVoid.setMerchantId(getTransactionResponse.getMerchantId());
          virtualTeminalVoid.setCardHolderName(getTransactionResponse.getCardHolderName());
          virtualTeminalVoid.setAuthId(getTransactionResponse.getAuthId());
          virtualTeminalVoid.setTerminalId(getTransactionResponse.getTerminalId());
        }
        modelAndView.addObject(Constants.VIRTUAL_TEMINAL_VOID, virtualTeminalVoid);
      } else {
        modelAndView.setViewName(Constants.REDIRECT_PG + CHATAK_MERCHANT_SEARCH_TRANSACTION_PAGE);
        redirectAttributes.addAttribute("redirectionError", Constants.ACCESS_DENIED);
      }
    } catch (ChatakPayException e) {
      modelAndView.addObject(Constants.ERROR, e.getMessage());
      logger.error("ERROR :: TransactionsController :: fetchTransactionForVoid method:" + e);
    } catch (Exception e) {
      modelAndView.addObject(Constants.ERROR, messageSource
          .getMessage(Constants.CHATAK_GENERAL_ERROR, null, LocaleContextHolder.getLocale()));
      logger.error("ERROR :: TransactionsController :: fetchTransactionForVoid method:" + e);
    }
    logger.info("Exiting :: TransactionsController :: fetchTransactionForVoid method");
    return modelAndView;
  }

  @RequestMapping(value = PROCESS_CHATAK_MERCHANT_TRANSACTION_VOID, method = RequestMethod.POST)
  public @ResponseBody ModelAndView processVoid(HttpServletRequest request,
      HttpServletResponse response, HttpSession session,
      VirtualTerminalVoidDTO virtualTerminalVoidDTO, BindingResult bindingResult, Map model,
      final RedirectAttributes redirectAttributes) {

    logger.info("Entering :: VirtualTerminalController :: processVoid method");
    ModelAndView modelAndView = new ModelAndView(CHATAK_MERCHANT_TRANSACTION_VOID_PAGE);
    String existingFeature = (String) session.getAttribute(Constants.EXISTING_FEATURES);

    if (request.getHeader(Constants.REFERER) == null) {
      return validateHeader(session, modelAndView);
    }

    if (!existingFeature
        .contains(FeatureConstants.MERCHANT_SERVICE_REPORTS_TRANSACTION_FEATURE_ID)) {
      return validateHeader(session, modelAndView);
    }
    try {
      virtualTerminalVoidDTO.setUserName((String) session.getAttribute(CHATAK_ADMIN_USER_NAME));
      boolean hasAccess =
          transactionService.hasAccessTo(FeatureConstants.VOID_SUB_MERCHANT_TRANSACTIONS,
              virtualTerminalVoidDTO.getMerchantId(), session);

      if (hasAccess) {
        TransactionResponse response2 = paymentService.doVoid(virtualTerminalVoidDTO);
        if (response2 != null && response2.getErrorCode().equals(Constants.SUCCESS_CODE)) {
          model.put("txnRefNum", response2);
          model.put("refFlag", true);
          modelAndView.addObject(Constants.SUCESS,
              response2.getErrorMessage() + "," + response2.getErrorCode());
          virtualTerminalVoidDTO.setSuccessDiv(true);
          modelAndView.addObject(Constants.VIRTUAL_TEMINAL_VOID, virtualTerminalVoidDTO);
        } else if (response2 != null) {
          modelAndView.addObject(Constants.ERROR,
              response2.getErrorMessage() + "," + response2.getErrorCode());
          modelAndView.addObject(Constants.VIRTUAL_TEMINAL_VOID, virtualTerminalVoidDTO);
        }
      } else {
        modelAndView.setViewName(Constants.REDIRECT_PG + CHATAK_MERCHANT_SEARCH_TRANSACTION_PAGE);
        redirectAttributes.addAttribute(Constants.REDIRECTION_ERROR, Constants.ACCESS_DENIED);
      }
    } catch (ChatakPayException e) {
      modelAndView.addObject(Constants.ERROR, e.getMessage());
      modelAndView.addObject(Constants.VIRTUAL_TEMINAL_VOID, virtualTerminalVoidDTO);
      logger.error("ERROR :: VirtualTerminalController :: processVoid method:" + e);
    } catch (Exception e) {
      modelAndView.addObject(Constants.ERROR, messageSource
          .getMessage(Constants.CHATAK_GENERAL_ERROR, null, LocaleContextHolder.getLocale()));
      modelAndView.addObject(Constants.VIRTUAL_TEMINAL_VOID, virtualTerminalVoidDTO);
      logger.error("ERROR :: VirtualTerminalController :: processVoid method:" + e);
    }
    logger.info("Exiting :: VirtualTerminalController :: processVoid method");
    return modelAndView;
  }

  @RequestMapping(value = PROCESS_CHATAK_MERCHANT_TRANSACTION_REFUND, method = RequestMethod.POST)
  public @ResponseBody ModelAndView processRefund(HttpServletRequest request,
      HttpServletResponse response, HttpSession session,
      VirtualTerminalRefundDTO virtualTerminalRefundDTO, BindingResult bindingResult, Map model,
      final RedirectAttributes redirectAttributes) {

    logger.info("Entering :: VirtualTerminalController :: processRefund method");
    ModelAndView modelAndView = new ModelAndView(SHOW_CHATAK_MERCHANT_TRANSACTION_REFUND);
    String existingFeature = (String) session.getAttribute(Constants.EXISTING_FEATURES);

    if (request.getHeader(Constants.REFERER) == null) {
      return validateHeader(session, modelAndView);
    }

    if (!existingFeature
        .contains(FeatureConstants.MERCHANT_SERVICE_REPORTS_TRANSACTION_FEATURE_ID)) {
      return validateHeader(session, modelAndView);
    }
    try {
      virtualTerminalRefundDTO.setUserName((String) session.getAttribute(CHATAK_ADMIN_USER_NAME));
      boolean hasAccess =
          transactionService.hasAccessTo(FeatureConstants.REFUND_SUB_MERCHANT_TRANSACTIONS,
              virtualTerminalRefundDTO.getMerchantId(), session);

      if (hasAccess) {

        TransactionResponse refundResponse = paymentService.doRefund(virtualTerminalRefundDTO);
        if (refundResponse != null
            && refundResponse.getErrorCode().equals(Constants.SUCCESS_CODE)) {
          model.put("txnRefNum", refundResponse);
          model.put("refFlag", true);
          modelAndView.addObject(Constants.SUCESS, refundResponse.getErrorMessage());
          virtualTerminalRefundDTO.setSuccessDiv(true);
          modelAndView.addObject(Constants.VIRTUAL_TEMINAL_REFUND, virtualTerminalRefundDTO);
        } else if (refundResponse != null) {
          modelAndView.addObject(Constants.ERROR, refundResponse.getErrorMessage());
          modelAndView.addObject(Constants.VIRTUAL_TEMINAL_REFUND, virtualTerminalRefundDTO);
        }
      } else {
        modelAndView.setViewName(Constants.REDIRECT_PG + CHATAK_MERCHANT_SEARCH_TRANSACTION_PAGE);
        redirectAttributes.addAttribute(Constants.REDIRECTION_ERROR, Constants.ACCESS_DENIED);
      }

    } catch (ChatakPayException exp) {
      modelAndView.addObject(Constants.ERROR, exp.getMessage());
      modelAndView.addObject(Constants.VIRTUAL_TEMINAL_REFUND, virtualTerminalRefundDTO);
      logger.error("ERROR :: VirtualTerminalController :: processRefund method:" + exp);
    } catch (Exception ex) {
      modelAndView.addObject(Constants.ERROR, messageSource
          .getMessage(Constants.CHATAK_GENERAL_ERROR, null, LocaleContextHolder.getLocale()));
      modelAndView.addObject(Constants.VIRTUAL_TEMINAL_REFUND, virtualTerminalRefundDTO);
      logger.error("ERROR :: VirtualTerminalController :: processRefund method:" + ex);
    }
    logger.info("Exiting :: VirtualTerminalController :: processRefund method");
    return modelAndView;
  }

  private ModelAndView validateHeader(HttpSession session, ModelAndView modelAndView) {
	session.invalidate();
      modelAndView.setViewName(INVALID_REQUEST_PAGE);
      return modelAndView;
  }

  @RequestMapping(value = CHATAK_TRANSACTION_POPUP_DETAILS, method = RequestMethod.POST)
  public @ResponseBody TransactionPopUpDataDto fetchTransactionPopupDetails(HttpSession session,
      Map model, HttpServletRequest request,
      @FormParam("accountTransactionId") final String accountTransactionId,
      HttpServletResponse response) {
    return transactionService.fetchTransactionDetails(accountTransactionId);
  }

  @RequestMapping(value = CHATAK_PARTIAL_REFUND_BALANCE, method = RequestMethod.POST)
  public @ResponseBody String fetchPartialRefundBalance(HttpSession session, Map model,
      HttpServletRequest request,
      @FormParam("accountTransactionId") final String accountTransactionId,
      @FormParam("merchantCode") final String merchantCode, HttpServletResponse response) {
    logger.info("Entring:: TransactionsController:: fetchPartialRefundBalance method");
    return transactionService.getPartialRefundBalance(accountTransactionId, merchantCode);
  }

  @RequestMapping(value = CHATAK_MERCHANT_MANUAL_TRANSACTIONS, method = RequestMethod.GET)
  public ModelAndView showManualTransactions(HttpServletRequest request,
      HttpServletResponse response, HttpSession session, Map model) {

    logger.info("Entering :: TransactionsController :: showManualTransactions method");
    ModelAndView modelAndView = new ModelAndView(CHATAK_MERCHANT_MANUAL_TRANSACTIONS);
    String existingFeature = (String) session.getAttribute(Constants.EXISTING_FEATURES);
    if (!existingFeature
        .contains(FeatureConstants.MERCHANT_SERVICE_REPORTS_TRANSACTION_FEATURE_ID)) {
      return validateHeader(session, modelAndView);
    }
    Long merchantId = (Long) session.getAttribute(Constants.LOGIN_USER_MERCHANT_ID);
    GetTransactionsListRequest manualTransactionRequest = new GetTransactionsListRequest();
    if (merchantId != null) {
      try {
        manualTransactionRequest.setAcqChannel("web");
        List<String> manualTxnCodeList = new ArrayList<>(Constants.TWO);
        manualTxnCodeList.add(AccountTransactionCode.MANUAL_CREDIT);
        manualTxnCodeList.add(AccountTransactionCode.MANUAL_DEBIT);
        manualTransactionRequest.setTransactionCodeList(manualTxnCodeList);
        manualTransactionRequest.setSettlementStatus(PGConstants.PG_SETTLEMENT_EXECUTED);
        manualTransactionRequest.setPageIndex(Constants.ONE);
        manualTransactionRequest.setPageSize(Constants.MAX_ENTITIES_PORTAL_DISPLAY_SIZE);

        GetTransactionsListResponse manualTransactionsReportList = fetchPGMerchantDetails(merchantId,
				manualTransactionRequest);

        if (null != manualTransactionsReportList) {
          session.setAttribute(Constants.MANUAL_TRANSACTIONS_REPORT_LIST,
              manualTransactionsReportList.getAccountTransactionList());
          modelAndView.addObject("manualTransactionsReportList",
              manualTransactionsReportList.getAccountTransactionList());
          manualTransactionsReportList
              .setTotalNoOfRows(manualTransactionsReportList.getTotalResultCount());
          modelAndView = PaginationUtil.getPagenationModel(modelAndView,
              manualTransactionsReportList.getTotalResultCount());
        }
      } catch (Exception e) {
        logger.error("Error :: TransactionsController :: showManualTransactions method", e);
        modelAndView.setViewName(INVALID_REQUEST_PAGE);
        return modelAndView;
      }
    }
    session.setAttribute(Constants.ERROR, null);
    session.setAttribute(Constants.SUCESS, null);
    logger.info("Exiting :: TransactionsController :: showManualTransactions method");
    return modelAndView;

  }

  private GetTransactionsListResponse fetchPGMerchantDetails(Long merchantId,
		GetTransactionsListRequest manualTransactionRequest) {
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
	String merchantCodes = StringUtils.join(merchantCodeList, "|");
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
	return manualTransactionsReportList;
  }

  private void getSubMerchantCodes(List<PGMerchant> subMerchantList,
      List<String> merchantCodeList) {
    for (PGMerchant subMerchant : subMerchantList) {
      merchantCodeList.add(subMerchant.getMerchantCode());
    }
  }

  @RequestMapping(value = CHATAK_MERCHANT_MANUAL_TRANSACTIONS_PAGINATION,
      method = RequestMethod.POST)
  public ModelAndView manualTransactionsPagination(HttpServletRequest request,
      final HttpSession session, @FormParam("pageNumber") final Integer pageNumber,
      @FormParam("totalRecords") final Integer totalRecords, Map model) {
    logger.info("Entering :: TransactionsController :: manualTransactionsPagination method");
    
    ModelAndView modelAndView = new ModelAndView(CHATAK_MERCHANT_MANUAL_TRANSACTIONS);
    Long merchantId = (Long) session.getAttribute(Constants.LOGIN_USER_MERCHANT_ID);
    GetTransactionsListRequest manualTransactionRequest = new GetTransactionsListRequest();

    if (request.getHeader(Constants.REFERER) == null) {
      return validateHeader(session, modelAndView);
    }
    manualTransactionRequest.setAcqChannel("web");
    List<String> manualTxnCodeList = new ArrayList<>(Constants.TWO);
    manualTxnCodeList.add(AccountTransactionCode.MANUAL_CREDIT);
    manualTxnCodeList.add(AccountTransactionCode.MANUAL_DEBIT);
    manualTransactionRequest.setTransactionCodeList(manualTxnCodeList);
    manualTransactionRequest.setSettlementStatus(PGConstants.PG_SETTLEMENT_EXECUTED);
    manualTransactionRequest.setPageIndex(pageNumber);
    manualTransactionRequest.setNoOfRecords(totalRecords);
    manualTransactionRequest.setPageSize(Constants.MAX_ENTITIES_PORTAL_DISPLAY_SIZE);

    GetTransactionsListResponse manualTransactionsReportList = fetchPGMerchantDetails(merchantId,
			manualTransactionRequest);

    if (null != manualTransactionsReportList) {
      session.setAttribute(Constants.MANUAL_TRANSACTIONS_REPORT_LIST,
          manualTransactionsReportList.getAccountTransactionList());
      modelAndView.addObject(Constants.MANUAL_TRANSACTIONS_REPORT_LIST,
          manualTransactionsReportList.getAccountTransactionList());
      manualTransactionsReportList
          .setTotalNoOfRows(manualTransactionsReportList.getTotalResultCount());
      modelAndView = PaginationUtil.getPagenationModelSuccessive(modelAndView, pageNumber,
          manualTransactionsReportList.getTotalResultCount());
    } else {
      modelAndView.addObject(Constants.MANUAL_TRANSACTIONS_REPORT_LIST, "");
      session.setAttribute(Constants.MANUAL_TRANSACTIONS_REPORT_LIST, "");
    }
    return modelAndView;
  }
}
