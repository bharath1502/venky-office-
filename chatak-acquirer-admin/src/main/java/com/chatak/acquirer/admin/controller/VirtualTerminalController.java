package com.chatak.acquirer.admin.controller;

import java.io.IOException;
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
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.chatak.acquirer.admin.constants.FeatureConstants;
import com.chatak.acquirer.admin.constants.URLMappingConstants;
import com.chatak.acquirer.admin.controller.model.LoginResponse;
import com.chatak.acquirer.admin.exception.ChatakPayException;
import com.chatak.acquirer.admin.service.BlackListedCardService;
import com.chatak.acquirer.admin.service.MerchantValidateService;
import com.chatak.acquirer.admin.service.RestPaymentService;
import com.chatak.acquirer.admin.util.JsonUtil;
import com.chatak.pg.bean.Response;
import com.chatak.pg.constants.ActionErrorCode;
import com.chatak.pg.constants.PGConstants;
import com.chatak.pg.model.GetMerchantDetailsResponse;
import com.chatak.pg.model.GetTransactionResponse;
import com.chatak.pg.model.TransactionRequest;
import com.chatak.pg.model.TransactionResponse;
import com.chatak.pg.model.VirtualTerminalCaptureDTO;
import com.chatak.pg.model.VirtualTerminalRefundDTO;
import com.chatak.pg.model.VirtualTerminalSaleDTO;
import com.chatak.pg.model.VirtualTerminalVoidDTO;
import com.chatak.pg.util.Constants;

/**
 * << Add Comments Here >>
 * 
 * @author Girmiti Software
 * @date 16-Mar-2015 6:45:44 PM
 * @version 1.0
 */
@SuppressWarnings({"rawtypes", "unchecked"})
@Controller
public class VirtualTerminalController implements URLMappingConstants {

  private static Logger logger = Logger.getLogger(VirtualTerminalController.class);

  @Autowired
  RestPaymentService paymentService;

  @Autowired
  private MessageSource messageSource;

  @Autowired
  MerchantValidateService merchantValidateService;

  @Autowired
  BlackListedCardService blackListedCardService;

  /**
   * Method get the Virtual terminal Sale page
   * 
   * @param request
   * @param response
   * @param session
   * @param model
   * @return
   */
  @RequestMapping(value = CHATAK_ADMIN_VIRTUAL_TERMINAL_SALE_PAGE, method = RequestMethod.GET)
  public ModelAndView showVirtualterminalSale(HttpServletRequest request,
      HttpServletResponse response, HttpSession session, Map model) {

    logger.info("Entering :: VirtualTerminalController :: showVirtualterminalSale method");
    ModelAndView modelAndView = new ModelAndView(CHATAK_ADMIN_VIRTUAL_TERMINAL_SALE_PAGE);
    modelAndView.addObject(Constants.VIRTUAL_TEMINAL_SALE, new VirtualTerminalSaleDTO());
    String existingFeature = (String) session.getAttribute(Constants.EXISTING_FEATURES);
    if (!existingFeature
        .contains(FeatureConstants.ADMIN_SERVICE_VIRTUAL_TERMINAL_SALE_FEATURE_ID)) {
      session.invalidate();
      modelAndView.setViewName(INVALID_REQUEST_PAGE);
      return modelAndView;
    }
    logger.info("Exiting :: VirtualTerminalController :: showVirtualterminalSale method");
    return modelAndView;
  }

  /**
   * Method to process sale Transaction
   * 
   * @param request
   * @param response
   * @param session
   * @param terminalSaleDTO
   * @param bindingResult
   * @param model
   * @return
   */
  @RequestMapping(value = CHATAK_ADMIN_VIRTUAL_TERMINAL_SALE_PROCESS, method = RequestMethod.POST)
  public ModelAndView processSale(HttpServletRequest request, HttpServletResponse response,
      HttpSession session, VirtualTerminalSaleDTO terminalSaleDTO, BindingResult bindingResult,
      Map model) {

    logger.info("Entering :: VirtualTerminalController :: processSale method with TimeZone : " + terminalSaleDTO.getTimeZoneOffset());
    String merchantCode = terminalSaleDTO.getMerchantCode();
    ModelAndView modelAndView = new ModelAndView(CHATAK_ADMIN_VIRTUAL_TERMINAL_SALE_PAGE);
    String existingFeature = (String) session.getAttribute(Constants.EXISTING_FEATURES);
    if (!existingFeature
        .contains(FeatureConstants.ADMIN_SERVICE_VIRTUAL_TERMINAL_SALE_FEATURE_ID)) {
      session.invalidate();
      modelAndView.setViewName(INVALID_REQUEST_PAGE);
      return modelAndView;
    }
    terminalSaleDTO.setUserName((String)session.getAttribute(CHATAK_ADMIN_USER_NAME));
    if (null != merchantCode) {
      try {
        Response blackListedCardResponse =
            blackListedCardService.findByCardNumber(terminalSaleDTO.getCardNum());
        if (blackListedCardResponse.getErrorCode() != null
            && blackListedCardResponse.getErrorCode().equals(ActionErrorCode.ERROR_CODE_Z5)) {
          modelAndView.addObject(Constants.ERROR, blackListedCardResponse.getErrorMessage());
          modelAndView.addObject(Constants.VIRTUAL_TEMINAL_SALE, terminalSaleDTO);
          return modelAndView;
        }

        GetMerchantDetailsResponse merchantDetailsResponse =
            paymentService.getMerchantIdAndTerminalId(terminalSaleDTO.getMerchantCode());
        if (merchantDetailsResponse.getErrorCode().equals(Constants.SUCCESS_CODE)) {
          terminalSaleDTO.setMerchantId(merchantDetailsResponse.getMerchantId());
          terminalSaleDTO.setTerminalId(merchantDetailsResponse.getTerminalId());
        } else {
          modelAndView.addObject(Constants.ERROR, merchantDetailsResponse.getErrorMessage());
          modelAndView.addObject(Constants.VIRTUAL_TEMINAL_SALE, terminalSaleDTO);
          return modelAndView;
        }
        terminalSaleDTO
            .setExpDate(terminalSaleDTO.getYear().substring(Constants.TWO) + terminalSaleDTO.getMonth());
        TransactionResponse transactionResponse = paymentService.doSale(terminalSaleDTO);
        
        return setSaleTxnResponse(terminalSaleDTO, model, modelAndView, transactionResponse);
        
      } catch (ChatakPayException e) {
        logger.error("ERROR :: VirtualTerminalController :: processSale method:" + e);
        modelAndView.addObject(Constants.ERROR, e.getMessage());
        modelAndView.addObject(Constants.VIRTUAL_TEMINAL_SALE, terminalSaleDTO);
      } catch (Exception e) {
        logger.error("ERROR :: VirtualTerminalController :: processSale method:" + e);
        modelAndView.addObject(Constants.ERROR, messageSource.getMessage(Constants.CHATAK_GENERAL_ERROR,
            null, LocaleContextHolder.getLocale()));
        modelAndView.addObject(Constants.VIRTUAL_TEMINAL_SALE, terminalSaleDTO);
      }
      logger.info("Exiting :: VirtualTerminalController :: processSale method");
      return modelAndView;
    } else {
      logger.error("ERROR :: VirtualTerminalController :: processSale method");
      modelAndView.addObject(Constants.ERROR,
          messageSource.getMessage(Constants.CHATAK_GENERAL_ERROR, null, LocaleContextHolder.getLocale()));
      modelAndView.addObject(Constants.VIRTUAL_TEMINAL_SALE, terminalSaleDTO);
      logger.info("Exiting :: VirtualTerminalController :: processSale method");
      return modelAndView;
    }
  }

  private ModelAndView setSaleTxnResponse(VirtualTerminalSaleDTO terminalSaleDTO, Map model,       
      ModelAndView modelAndView, TransactionResponse transactionResponse) {
    if (transactionResponse != null
        && (Constants.SUCCESS_CODE.equals(transactionResponse.getErrorCode())
            || "000".equals(transactionResponse.getErrorCode()))) {
      model.put(Constants.TXN_REF_NUM, transactionResponse);
      model.put(Constants.REF_FLAG, true);
      model.put("mCode", terminalSaleDTO.getMerchantId());
      modelAndView.addObject(Constants.SUCESS, transactionResponse.getErrorMessage());
      terminalSaleDTO.setSuccessDiv(true);
      modelAndView.addObject(Constants.VIRTUAL_TEMINAL_SALE, terminalSaleDTO);
    } else {
      setTransactionError(modelAndView, transactionResponse);
      modelAndView.addObject(Constants.VIRTUAL_TEMINAL_SALE, terminalSaleDTO);
    }
    return modelAndView;
  }
  
  
  @RequestMapping(value = CHATAK_ADMIN_VIRTUAL_TERMINAL_SALE_PROCESS, method = RequestMethod.GET)
  public ModelAndView processSaleOnRefresh(HttpServletRequest request, HttpServletResponse response,
      HttpSession session, VirtualTerminalSaleDTO terminalSaleDTO, BindingResult bindingResult,
      Map model) {
    ModelAndView modelAndView = new ModelAndView(CHATAK_ADMIN_VIRTUAL_TERMINAL_SALE_PAGE);
    modelAndView.addObject(Constants.VIRTUAL_TEMINAL_SALE, terminalSaleDTO);
    return modelAndView;
  }

  /**
   * @param model
   * @param request
   * @param response
   * @param session
   * @return
   */
  @RequestMapping(value = CHATAK_ADMIN_VIRTUAL_TERMINAL_FETCH_TRAN_ADJUST,
      method = RequestMethod.GET)
  public @ResponseBody String fetchTransaction(Map model, HttpServletRequest request,
      HttpServletResponse response, HttpSession session) {
    ModelAndView modelAndView = new ModelAndView(CHATAK_ADMIN_VIRTUAL_TERMINAL_SALE_PAGE);
    modelAndView.addObject(Constants.ERROR, null);
    logger.info("Entering :: VirtualTerminalController :: fetchTransaction method");
    String cardNum = request.getParameter("cardNum");
    String authId = request.getParameter("authId");
    String invoiceNum = request.getParameter("invoiceNum");
    String merchantId = request.getParameter("merchantId");
    GetTransactionResponse getTransactionResponse;
    GetMerchantDetailsResponse merchantDetailsResponse;
    try {
      merchantDetailsResponse = paymentService.getMerchantIdAndTerminalId(merchantId);
      if (merchantDetailsResponse != null) {
        getTransactionResponse = paymentService.getTransaction(merchantId,
            merchantDetailsResponse.getTerminalId(), authId, cardNum, invoiceNum);
        return JsonUtil.convertObjectToJSON(getTransactionResponse);
      }
    } catch (ChatakPayException e) {
      modelAndView.addObject(Constants.ERROR, e.getMessage());
      logger.error("ERROR :: VirtualTerminalController :: fetchTransaction method:" + e);
    } catch (Exception e) {
      modelAndView.addObject(Constants.ERROR,
          messageSource.getMessage(Constants.CHATAK_GENERAL_ERROR, null, LocaleContextHolder.getLocale()));
      logger.error("ERROR :: VirtualTerminalController :: fetchTransaction method:" + e);
    }
    logger.info("Exiting :: VirtualTerminalController :: fetchTransaction method");
    return invoiceNum;
  }

  @RequestMapping(value = CHATAK_ADMIN_VIRTUAL_TERMINAL_FETCH_TRAN, method = RequestMethod.GET)
  public @ResponseBody String fetchTransactionbyRefId(Map model, HttpServletRequest request,
      HttpServletResponse response, HttpSession session) {
    ModelAndView modelAndView = new ModelAndView(CHATAK_ADMIN_VIRTUAL_TERMINAL_SALE_PAGE);
    modelAndView.addObject(Constants.ERROR, null);
    logger.info("Entering :: VirtualTerminalController :: fetchTransactionbyRefId method");
    String refId = request.getParameter("refId");
    String txnType = request.getParameter("txnType");
    String merchantId = request.getParameter("merchantId");
    GetTransactionResponse getTransactionResponse = null;
    GetMerchantDetailsResponse merchantDetailsResponse;
    try {
      LoginResponse loginResponse = (LoginResponse) session.getAttribute(Constants.LOGIN_RESPONSE_DATA);
      merchantDetailsResponse = paymentService.getMerchantIdAndTerminalIdOnEntityType(merchantId, loginResponse.getUserType(), loginResponse.getEntityId());
      if (merchantDetailsResponse != null && merchantDetailsResponse.getErrorCode().equals(Constants.SUCCESS_CODE)) {
        if ("sale".equalsIgnoreCase(txnType)) {
          getTransactionResponse =
              paymentService.getTransactionByRefId(merchantDetailsResponse.getMerchantId(),
                  merchantDetailsResponse.getTerminalId(), refId, txnType);
        } else {
          getTransactionResponse =
              paymentService.getTransactionByRefIdForRefund(merchantDetailsResponse.getMerchantId(),
                  merchantDetailsResponse.getTerminalId(), refId, txnType);
        }
        return JsonUtil.convertObjectToJSON(getTransactionResponse);
      } else {
    	  getTransactionResponse = new GetTransactionResponse();
    	  getTransactionResponse.setErrorCode(merchantDetailsResponse != null ? merchantDetailsResponse.getErrorCode() : "");
    	  return JsonUtil.convertObjectToJSON(getTransactionResponse);  
      }
    } catch (ChatakPayException e) {
      modelAndView.addObject(Constants.ERROR, e.getMessage());
      logger.error("ERROR :: VirtualTerminalController :: fetchTransactionbyRefId method:" + e);
    } catch (Exception e) {
      modelAndView.addObject(Constants.ERROR,
          messageSource.getMessage(Constants.CHATAK_GENERAL_ERROR, null, LocaleContextHolder.getLocale()));
      logger.error("ERROR :: VirtualTerminalController :: fetchTransactionbyRefId method:" + e);
    }
    logger.info("Exiting :: VirtualTerminalController :: fetchTransactionbyRefId method");
    return refId;
  }

  /**
   * Method get the Virtual terminal Void page
   * 
   * @param request
   * @param response
   * @param session
   * @param model
   * @return
   */
  @RequestMapping(value = CHATAK_ADMIN_VIRTUAL_TERMINAL_VOID_PAGE, method = RequestMethod.GET)
  public ModelAndView showVirtualterminalVoid(HttpServletRequest request,
      HttpServletResponse response, HttpSession session, Map model) {

    logger.info("Entering :: VirtualTerminalController :: showVirtualterminalVoid method");
    ModelAndView modelAndView = new ModelAndView(CHATAK_ADMIN_VIRTUAL_TERMINAL_VOID_PAGE);
    String existingFeature = (String) session.getAttribute(Constants.EXISTING_FEATURES);
    if (!existingFeature
        .contains(FeatureConstants.ADMIN_SERVICE_VIRTUAL_TERMINAL_VOID_FEATURE_ID)) {
      session.invalidate();
      modelAndView.setViewName(INVALID_REQUEST_PAGE);
      return modelAndView;
    }
    modelAndView.addObject(Constants.VIRTUAL_TEMINAL_VOID, new VirtualTerminalVoidDTO());
    logger.info("Exiting :: VirtualTerminalController :: showVirtualterminalVoid method");
    return modelAndView;
  }

  /**
   * Method to post Void transaction
   * 
   * @param request
   * @param response
   * @param session
   * @param virtualTerminalVoidDTO
   * @param bindingResult
   * @param model
   * @return
   */
  @RequestMapping(value = CHATAK_ADMIN_VIRTUAL_TERMINAL_VOID_PROCESS, method = RequestMethod.POST)
  public @ResponseBody ModelAndView processVoid(HttpServletRequest request,
      HttpServletResponse response, HttpSession session,
      @FormParam(Constants.TXN_REF_NUM) final String txnRefNum, VirtualTerminalVoidDTO virtualTerminalVoidDTO,
      BindingResult bindingResult, Map model) {

    logger.info("Entering :: VirtualTerminalController :: processVoid method");
    ModelAndView modelAndView = new ModelAndView(CHATAK_ADMIN_VIRTUAL_TERMINAL_VOID_PAGE);
    String existingFeature = (String) session.getAttribute(Constants.EXISTING_FEATURES);
    if (!existingFeature
        .contains(FeatureConstants.ADMIN_SERVICE_VIRTUAL_TERMINAL_VOID_FEATURE_ID)) {
      session.invalidate();
      modelAndView.setViewName(INVALID_REQUEST_PAGE);
      return modelAndView;
    }
    String merchantId = virtualTerminalVoidDTO.getMerchantId();
    if (null != merchantId) {
      try {
        virtualTerminalVoidDTO.setUserName((String)session.getAttribute(CHATAK_ADMIN_USER_NAME));
        GetMerchantDetailsResponse merchantDetailsResponse =
            paymentService.getMerchantIdAndTerminalId(merchantId);
        if (merchantDetailsResponse.getErrorCode().equals(Constants.SUCCESS_CODE)) {
          virtualTerminalVoidDTO.setMerchantId(merchantDetailsResponse.getMerchantId());
          virtualTerminalVoidDTO.setTerminalId(merchantDetailsResponse.getTerminalId());
        } else {
          modelAndView.addObject(Constants.ERROR, merchantDetailsResponse.getErrorMessage());
          modelAndView.addObject(Constants.VIRTUAL_TEMINAL_VOID, virtualTerminalVoidDTO);
          return modelAndView;
        }
        TransactionResponse voidResponse = paymentService.doVoid(virtualTerminalVoidDTO);
        if (voidResponse != null && voidResponse.getErrorCode().equals(Constants.SUCCESS_CODE)) {
          model.put(Constants.TXN_REF_NUM, voidResponse);
          model.put(Constants.REF_FLAG, true);
          model.put("mCode", virtualTerminalVoidDTO.getMerchantId());
          modelAndView.addObject(Constants.SUCESS, voidResponse.getErrorMessage());
          virtualTerminalVoidDTO.setSuccessDiv(true);
          modelAndView.addObject(Constants.VIRTUAL_TEMINAL_VOID, virtualTerminalVoidDTO);
        } else {
          setTransactionError(modelAndView, voidResponse);
          modelAndView.addObject(Constants.VIRTUAL_TEMINAL_VOID, virtualTerminalVoidDTO);
        }
      } catch (ChatakPayException e) {
        modelAndView.addObject(Constants.ERROR, e.getMessage());
        modelAndView.addObject(Constants.VIRTUAL_TEMINAL_VOID, virtualTerminalVoidDTO);
        logger.error("ERROR :: VirtualTerminalController :: processVoid method", e);
      } catch (Exception e) {
        modelAndView.addObject(Constants.ERROR, messageSource.getMessage(Constants.CHATAK_GENERAL_ERROR,
            null, LocaleContextHolder.getLocale()));
        modelAndView.addObject(Constants.VIRTUAL_TEMINAL_VOID, virtualTerminalVoidDTO);
        logger.error("ERROR :: VirtualTerminalController :: processVoid method:" + e);
      }
      logger.info("Exiting :: VirtualTerminalController :: processVoid method");
      return modelAndView;
    } else {
      modelAndView.addObject(Constants.ERROR,
          messageSource.getMessage(Constants.CHATAK_GENERAL_ERROR, null, LocaleContextHolder.getLocale()));
      modelAndView.addObject(Constants.VIRTUAL_TEMINAL_VOID, virtualTerminalVoidDTO);
      logger.error("ERROR :: VirtualTerminalController :: processVoid method:");
      return modelAndView;
    }
  }

  /**
   * Method get the Virtual terminal Void page
   * 
   * @param request
   * @param response
   * @param session
   * @param model
   * @return
   */
  @RequestMapping(value = CHATAK_ADMIN_VIRTUAL_TERMINAL_REFUND_PAGE, method = RequestMethod.GET)
  public ModelAndView showVirtualterminalRefund(HttpServletRequest request,
      HttpServletResponse response, HttpSession session, Map model) {

    logger.info("Entering :: VirtualTerminalController :: showVirtualterminalRefund method");
    ModelAndView modelAndView = new ModelAndView(CHATAK_ADMIN_VIRTUAL_TERMINAL_REFUND_PAGE);
    String existingFeature = (String) session.getAttribute(Constants.EXISTING_FEATURES);
    if (!existingFeature
        .contains(FeatureConstants.ADMIN_SERVICE_VIRTUAL_TERMINAL_REFUND_FEATURE_ID)) {
      session.invalidate();
      modelAndView.setViewName(INVALID_REQUEST_PAGE);
      return modelAndView;
    }
    modelAndView.addObject(Constants.VIRTUAL_TEMINAL_REFUND, new VirtualTerminalRefundDTO());
    logger.info("Exiting :: VirtualTerminalController :: showVirtualterminalRefund method");
    return modelAndView;
  }

  /**
   * @param request
   * @param response
   * @param session
   * @param virtualTerminalRefundDTO
   * @param bindingResult
   * @param model
   * @return
   */
  @RequestMapping(value = CHATAK_ADMIN_VIRTUAL_TERMINAL_REFUND_PROCESS, method = RequestMethod.POST)
  public @ResponseBody ModelAndView processRefund(HttpServletRequest request,
      HttpServletResponse response, HttpSession session,

      VirtualTerminalRefundDTO virtualTerminalRefundDTO, BindingResult bindingResult, Map model) {

    logger.info("Entering :: VirtualTerminalController :: processRefund method");
    ModelAndView modelAndView = new ModelAndView(CHATAK_ADMIN_VIRTUAL_TERMINAL_REFUND_PAGE);
    String existingFeature = (String) session.getAttribute(Constants.EXISTING_FEATURES);
    if (!existingFeature
        .contains(FeatureConstants.ADMIN_SERVICE_VIRTUAL_TERMINAL_REFUND_FEATURE_ID)) {
      session.invalidate();
      modelAndView.setViewName(INVALID_REQUEST_PAGE);
      return modelAndView;
    }
    virtualTerminalRefundDTO.setUserName((String)session.getAttribute(CHATAK_ADMIN_USER_NAME));
    String merchantId = virtualTerminalRefundDTO.getMerchantId();
    if (null != merchantId) {
      try {
        GetMerchantDetailsResponse merchantDetailsResponse =
            paymentService.getMerchantIdAndTerminalId(merchantId);
        if (merchantDetailsResponse.getErrorCode().equals(Constants.SUCCESS_CODE)) {
          virtualTerminalRefundDTO.setMerchantId(merchantDetailsResponse.getMerchantId());
          virtualTerminalRefundDTO.setTerminalId(merchantDetailsResponse.getTerminalId());
        } else {
          modelAndView.addObject(Constants.ERROR, merchantDetailsResponse.getErrorMessage());
          modelAndView.addObject(Constants.VIRTUAL_TEMINAL_REFUND, virtualTerminalRefundDTO);
          return modelAndView;
        }
        TransactionResponse refundResponse = paymentService.doRefund(virtualTerminalRefundDTO);
        if (refundResponse != null
            && refundResponse.getErrorCode().equals(Constants.SUCCESS_CODE)) {
          model.put(Constants.TXN_REF_NUM, refundResponse);
          model.put(Constants.REF_FLAG, true);
          model.put("mCode", virtualTerminalRefundDTO.getMerchantId());
          modelAndView.addObject(Constants.SUCESS, refundResponse.getErrorMessage());
          virtualTerminalRefundDTO.setSuccessDiv(true);
          modelAndView.addObject(Constants.VIRTUAL_TEMINAL_REFUND, virtualTerminalRefundDTO);
        } else {
          setTransactionError(modelAndView, refundResponse);
          modelAndView.addObject(Constants.VIRTUAL_TEMINAL_REFUND, virtualTerminalRefundDTO);
        }
      } catch (ChatakPayException e) {
        modelAndView.addObject(Constants.ERROR, e.getMessage());
        modelAndView.addObject(Constants.VIRTUAL_TEMINAL_REFUND, virtualTerminalRefundDTO);
        logger.error("ERROR :: VirtualTerminalController :: processRefund method:" + e);
      } catch (Exception e) {
        modelAndView.addObject(Constants.ERROR, messageSource.getMessage(Constants.CHATAK_GENERAL_ERROR,
            null, LocaleContextHolder.getLocale()));
        modelAndView.addObject(Constants.VIRTUAL_TEMINAL_REFUND, virtualTerminalRefundDTO);
        logger.error("ERROR :: VirtualTerminalController :: processRefund method:" + e);
      }
      logger.info("Exiting :: VirtualTerminalController :: processRefund method");
      return modelAndView;
    } else {
      modelAndView.addObject(Constants.ERROR,
          messageSource.getMessage(Constants.CHATAK_GENERAL_ERROR, null, LocaleContextHolder.getLocale()));
      modelAndView.addObject(Constants.VIRTUAL_TEMINAL_REFUND, virtualTerminalRefundDTO);
      logger.error("Exiting :: VirtualTerminalController :: processRefund method");
      return modelAndView;
    }
  }

  @RequestMapping(value = CHATAK_ADMIN_VIRTUAL_TERMINAL_REFUND_PROCESS, method = RequestMethod.GET)
  public @ResponseBody ModelAndView processRefundOnRefresh(HttpServletRequest request,
      HttpServletResponse response, HttpSession session,
      VirtualTerminalRefundDTO virtualTerminalRefundDTO, BindingResult bindingResult, Map model) {

    ModelAndView modelAndView = new ModelAndView(CHATAK_ADMIN_VIRTUAL_TERMINAL_REFUND_PAGE);
    modelAndView.addObject(Constants.VIRTUAL_TEMINAL_REFUND, virtualTerminalRefundDTO);
    return modelAndView;
  }

  /**
   * Method get the Virtual terminal Pre-Auth Completion Page
   * 
   * @param request
   * @param response
   * @param session
   * @param model
   * @return
   */
  @RequestMapping(value = CHATAK_ADMIN_VIRTUAL_TERMINAL_PREAUTH_COMPLEATION_PAGE,
      method = RequestMethod.GET)
  public ModelAndView showVirtualterminalPreAuthCompleation(HttpServletRequest request,
      HttpServletResponse response, HttpSession session, Map model) {

    logger.info(
        "Entering :: VirtualTerminalController :: showVirtualterminalPreAuthCompleation method");
    ModelAndView modelAndView =
        new ModelAndView(CHATAK_ADMIN_VIRTUAL_TERMINAL_PREAUTH_COMPLEATION_PAGE);
    modelAndView.addObject(Constants.VIRTUAL_TEMINAL_PRE_AUTH_COMPLEATION, new VirtualTerminalCaptureDTO());
    logger.info(
        "Exiting :: VirtualTerminalController :: showVirtualterminalPreAuthCompleation method");
    return modelAndView;
  }

  /**
   * @param request
   * @param response
   * @param session
   * @param virtualTerminalCaptureDTO
   * @param bindingResult
   * @param model
   * @return
   */
  @RequestMapping(value = CHATAK_ADMIN_VIRTUAL_TERMINAL_PREAUTH_COMPLEATION_PROCESS,
      method = RequestMethod.POST)
  public @ResponseBody ModelAndView processPreAuthCompleation(HttpServletRequest request,
      HttpServletResponse response, HttpSession session,
      @FormParam(Constants.TXN_REF_NUM) final String txnRefNum,
      VirtualTerminalCaptureDTO virtualTerminalCaptureDTO, BindingResult bindingResult, Map model) {

    logger.info("Entering :: VirtualTerminalController :: processPreAuthCompleation method");
    ModelAndView modelAndView =
        new ModelAndView(CHATAK_ADMIN_VIRTUAL_TERMINAL_PREAUTH_COMPLEATION_PAGE);
    Long merchantId = (Long) session.getAttribute(Constants.LOGIN_USER_ID);
    if (null != merchantId) {
      try {
        GetMerchantDetailsResponse merchantDetailsResponse =
            paymentService.getMerchantIdAndTerminalId(merchantId.toString());
        if (merchantDetailsResponse.getErrorCode().equals(Constants.SUCCESS_CODE)) {
          virtualTerminalCaptureDTO.setMerchantId(merchantDetailsResponse.getMerchantId());
          virtualTerminalCaptureDTO.setTerminalId(merchantDetailsResponse.getTerminalId());
        } else {
          modelAndView.addObject(Constants.ERROR, merchantDetailsResponse.getErrorMessage());
          modelAndView.addObject(Constants.VIRTUAL_TEMINAL_PRE_AUTH_COMPLEATION, virtualTerminalCaptureDTO);
          return modelAndView;
        }
        TransactionResponse captureResponse =
            paymentService.doPreAuthCapture(virtualTerminalCaptureDTO);
        if (captureResponse != null
            && captureResponse.getErrorCode().equals(Constants.SUCCESS_CODE)) {
          model.put(Constants.TXN_REF_NUM, captureResponse);
          model.put(Constants.REF_FLAG, true);
          modelAndView.addObject(Constants.SUCESS, captureResponse.getErrorMessage());
          modelAndView.addObject(Constants.VIRTUAL_TEMINAL_PRE_AUTH_COMPLEATION,
              new VirtualTerminalRefundDTO());
        } else {
          setTransactionError(modelAndView, captureResponse);
          modelAndView.addObject(Constants.VIRTUAL_TEMINAL_PRE_AUTH_COMPLEATION, virtualTerminalCaptureDTO);
        }
      } catch (ChatakPayException e) {
        modelAndView.addObject(Constants.ERROR, e.getMessage());
        modelAndView.addObject(Constants.VIRTUAL_TEMINAL_PRE_AUTH_COMPLEATION, virtualTerminalCaptureDTO);
        logger.error("ERROR :: VirtualTerminalController :: processPreAuthCompleation method:" + e);
      } catch (Exception e) {
        modelAndView.addObject(Constants.ERROR, messageSource.getMessage(Constants.CHATAK_GENERAL_ERROR,
            null, LocaleContextHolder.getLocale()));
        modelAndView.addObject(Constants.VIRTUAL_TEMINAL_PRE_AUTH_COMPLEATION, virtualTerminalCaptureDTO);
        logger.error("ERROR :: VirtualTerminalController :: processPreAuthCompleation method:" + e);
      }

      logger.info("Exiting :: VirtualTerminalController :: processPreAuthCompleation method");
      return modelAndView;
    } else {
      modelAndView.addObject(Constants.ERROR, "invalid session ,retry after login");
      modelAndView.addObject(Constants.VIRTUAL_TEMINAL_PRE_AUTH_COMPLEATION, virtualTerminalCaptureDTO);
      try {
        response.sendRedirect("/chatak-acquirer-admin/session-invalid.jsp");
      } catch (IOException e) {
        logger.error("Error :: VirtualTerminalController :: processPreAuthCompleation method", e);
      }
      return modelAndView;
    }
  }
  
  private ModelAndView setTransactionError(ModelAndView modelAndView,
      TransactionResponse captureResponse) {
    if (captureResponse != null) {
      modelAndView.addObject(Constants.ERROR, captureResponse.getErrorMessage());
    } else {
      modelAndView.addObject(Constants.ERROR, "");
    }
    return modelAndView;
  }

  @RequestMapping(value = CHATAK_PROCESS_POPUP_ACTION, method = RequestMethod.POST)
  public @ResponseBody TransactionResponse processPopupAction(HttpServletRequest request,
      HttpServletResponse response, HttpSession session, TransactionRequest transactionRequest,
      Model model) {
    TransactionResponse txnResponse = null;
    try {
      if (null != transactionRequest) {
        transactionRequest.setUserName((String)session.getAttribute(CHATAK_ADMIN_USER_NAME));
        switch (transactionRequest.getTransactionType()) {
          case VOID:
            txnResponse = paymentService.processPopupVoidOrRefund(transactionRequest);
            break;
          case REFUND:
            transactionRequest = setTransactionRequest(transactionRequest);
            txnResponse = paymentService.processPopupVoidOrRefund(transactionRequest);
            break;
          default:
            txnResponse = new TransactionResponse();
            txnResponse.setErrorCode(ActionErrorCode.ERROR_CODE_Z5);
            txnResponse.setErrorMessage(
                ActionErrorCode.getInstance().getMessage(ActionErrorCode.ERROR_CODE_Z5));
            break;
        }
      }
    } catch (Exception e) {
      logger.error("Error :: VirtualTerminalController :: processPopupAction", e);
      txnResponse = new TransactionResponse();
      txnResponse.setErrorCode(ActionErrorCode.ERROR_CODE_Z5);
      txnResponse
          .setErrorMessage(ActionErrorCode.getInstance().getMessage(ActionErrorCode.ERROR_CODE_Z5));
    }
    return txnResponse;
  }
  
  private TransactionRequest setTransactionRequest(TransactionRequest transactionRequest) {
    if (PGConstants.PARTIAL_REFUND_FLAG.equals(transactionRequest.getPartialRefundFlag())) {
      transactionRequest.setMerchantAmount(transactionRequest.getTotalTxnAmount());
    }
    return transactionRequest;
  }

  @RequestMapping(value = CHATAK_ADMIN_GET_MERCHANT_CURRENCY, method = RequestMethod.GET)
  public @ResponseBody String fetchMerchantCurrencyByMerchantCode(HttpServletRequest request,
      HttpServletResponse response, HttpSession session, Map model) {
    logger.info(
        "Entering :: VirtualTerminalController :: fetchMerchantCurrencyByMerchantCode method");
    ModelAndView modelAndView = new ModelAndView(FEE_PROGRAM_SEARCH);

    modelAndView.addObject(Constants.ERROR, null);
    modelAndView.addObject(Constants.SUCESS, null);
    String merchantCode = request.getParameter("merchantCode");
    try {
      Response virtualTeminalSale =
          merchantValidateService.fetchMerchantCurrencyByCode(merchantCode);
      if (virtualTeminalSale != null) {
        return JsonUtil.convertObjectToJSON(virtualTeminalSale);
      }
    } catch (Exception e) {
      modelAndView.addObject(Constants.ERROR,
          messageSource.getMessage(Constants.CHATAK_GENERAL_ERROR, null, LocaleContextHolder.getLocale()));
      logger.error("ERROR:: VirtualTerminalController:: fetchMerchantCurrencyByMerchantCode method",
          e);
    }
    logger
        .info("EXITING :: VirtualTerminalController :: fetchMerchantCurrencyByMerchantCode method");
    return null;
  }
  
}
