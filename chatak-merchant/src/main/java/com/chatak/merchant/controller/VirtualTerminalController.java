package com.chatak.merchant.controller;

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

import com.chatak.merchant.constants.URLMappingConstants;
import com.chatak.merchant.exception.ChatakPayException;
import com.chatak.merchant.model.GetMerchantDetailsResponse;
import com.chatak.merchant.model.TransactionResponse;
import com.chatak.merchant.model.VirtualTerminalAdjustmentResponse;
import com.chatak.merchant.service.RestPaymentService;
import com.chatak.pg.acq.dao.model.PGMerchantConfig;
import com.chatak.pg.acq.dao.repository.MerchantConfigRepositrory;
import com.chatak.pg.constants.ActionErrorCode;
import com.chatak.pg.constants.PGConstants;
import com.chatak.pg.model.TransactionRequest;
import com.chatak.pg.model.VirtualTerminalAdjustmentDTO;
import com.chatak.pg.model.VirtualTerminalCaptureDTO;
import com.chatak.pg.model.VirtualTerminalPreAuthDTO;
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
  private MerchantConfigRepositrory merchantConfigRepositrory;

  /**
   * Method get the Virtual terminal Sale page
   * 
   * @param request
   * @param response
   * @param session
   * @param model
   * @return
   * @throws Exception 
   * @throws ChatakPayException 
   */
  @RequestMapping(value = CHATAK_MERCHANT_VIRTUAL_TERMINAL_SALE_PAGE, method = RequestMethod.GET)
  public ModelAndView showVirtualterminalSale(HttpServletRequest request,
      HttpServletResponse response, HttpSession session, Map model) {
    logger.info("Entering :: VirtualTerminalController :: showVirtualterminalSale method");

    ModelAndView modelAndView = new ModelAndView(CHATAK_MERCHANT_VIRTUAL_TERMINAL_SALE_PAGE);
    Long userid = (Long) session.getAttribute(Constants.LOGIN_USER_MERCHANT_ID);
    PGMerchantConfig pgmerchantconfig = merchantConfigRepositrory.findById(userid);
    modelAndView.addObject(Constants.VIRTUAL_TEMINAL_SALE, new VirtualTerminalSaleDTO());
    model.put("pgmerchantconfig", pgmerchantconfig);

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
  @RequestMapping(value = CHATAK_MERCHANT_VIRTUAL_TERMINAL_SALE_PROCESS,
      method = RequestMethod.POST)
  public ModelAndView processSale(HttpServletRequest request, HttpServletResponse response,
      HttpSession session, VirtualTerminalSaleDTO terminalSaleDTO, BindingResult bindingResult,
      Map model) {
    logger.info("Entering :: VirtualTerminalController :: processSale method");

    Long merchantId = (Long) session.getAttribute(Constants.LOGIN_USER_MERCHANT_ID);
    ModelAndView modelAndView = new ModelAndView(CHATAK_MERCHANT_VIRTUAL_TERMINAL_SALE_PAGE);
    if (null != merchantId) {
      try {
        terminalSaleDTO.setUserName((String) session.getAttribute(CHATAK_ADMIN_USER_NAME));
        GetMerchantDetailsResponse merchantDetailsResponse =
            paymentService.getMerchantIdAndTerminalId(merchantId.toString());
        if (merchantDetailsResponse.getErrorCode().equals(Constants.SUCCESS_CODE)) {
          terminalSaleDTO.setMerchantId(merchantDetailsResponse.getMerchantId());
          terminalSaleDTO.setTerminalId(merchantDetailsResponse.getTerminalId());
        } else {
          modelAndView.addObject(Constants.ERROR, merchantDetailsResponse.getErrorMessage());
          modelAndView.addObject(Constants.VIRTUAL_TEMINAL_SALE, terminalSaleDTO);
          return modelAndView;
        }
        terminalSaleDTO.setExpDate(
            terminalSaleDTO.getYear().substring(Constants.TWO) + terminalSaleDTO.getMonth());
        TransactionResponse transactionResponse = paymentService.doSale(terminalSaleDTO);
        if (transactionResponse != null
            && (transactionResponse.getErrorCode().equals(Constants.SUCCESS_CODE)
                || transactionResponse.getErrorCode().equals("000"))) {
          model.put(Constants.TXN_REF_NUM, transactionResponse);
          model.put(Constants.REF_FLAG, true);
          modelAndView.addObject(Constants.SUCESS, transactionResponse.getErrorMessage());
          terminalSaleDTO.setSuccessDiv(true);
          modelAndView.addObject(Constants.VIRTUAL_TEMINAL_SALE, terminalSaleDTO);
        } else if(transactionResponse != null){
          modelAndView.addObject(Constants.ERROR, transactionResponse.getErrorMessage());
          modelAndView.addObject(Constants.VIRTUAL_TEMINAL_SALE, terminalSaleDTO);
          Long userid = (Long) session.getAttribute(Constants.LOGIN_USER_MERCHANT_ID);
          PGMerchantConfig pgmerchantconfig = merchantConfigRepositrory.findById(userid);
          model.put("pgmerchantconfig", pgmerchantconfig);
        }
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
      return virtualTerminalController(response, terminalSaleDTO, modelAndView);
    }
  }

  private ModelAndView virtualTerminalController(HttpServletResponse response,
      VirtualTerminalSaleDTO terminalSaleDTO, ModelAndView modelAndView) {
    modelAndView.addObject(Constants.ERROR, messageSource.getMessage(
        Constants.VIRTUAL_TERMINAL_CONTROLLER_INVALID_RETRY, null, LocaleContextHolder.getLocale()));
    modelAndView.addObject(Constants.VIRTUAL_TEMINAL_SALE, terminalSaleDTO);
    try {
      response.sendRedirect("/chatak-merchant/session-invalid.jsp");
    } catch (IOException e) {
      logger.error("Error :: VirtualTerminalController :: processSale method", e);
    }
    logger.info("Exiting :: VirtualTerminalController :: processSale method");
    return modelAndView;
  }

  @RequestMapping(value = CHATAK_MERCHANT_VIRTUAL_TERMINAL_SALE_PROCESS, method = RequestMethod.GET)
  public ModelAndView processSaleOnRefresh(HttpServletRequest request, HttpServletResponse response,
      HttpSession session, VirtualTerminalSaleDTO terminalSaleDTO, BindingResult bindingResult,
      Map model) {
    logger.info("Exiting :: VirtualTerminalController :: processSaleOnRefresh method");
    ModelAndView modelAndView = new ModelAndView(CHATAK_MERCHANT_VIRTUAL_TERMINAL_SALE_PAGE);
    modelAndView.addObject(Constants.VIRTUAL_TEMINAL_SALE, terminalSaleDTO);
    logger.info("Exiting :: VirtualTerminalController :: processSaleOnRefresh method");
    return modelAndView;
  }

  /**
   * @param request
   * @param response
   * @param session
   * @param model
   * @return
   */
  @RequestMapping(value = CHATAK_MERCHANT_VIRTUAL_TERMINAL_PRE_AUTH_PAGE,
      method = RequestMethod.GET)
  public ModelAndView showVirtualterminalPreAuth(HttpServletRequest request,
      HttpServletResponse response, HttpSession session, Map model) {

    logger.info("Entering :: VirtualTerminalController :: showVirtualterminalPreAuth method");
    ModelAndView modelAndView = new ModelAndView(CHATAK_MERCHANT_VIRTUAL_TERMINAL_PRE_AUTH_PAGE);
    modelAndView.addObject(Constants.VIRTUAL_TERMINAL_PRE_AUTH, new VirtualTerminalPreAuthDTO());
    logger.info("Exiting :: VirtualTerminalController :: showVirtualterminalPreAuth method");
    return modelAndView;
  }

  /**
   * @param request
   * @param response
   * @param session
   * @param virtualTerminalPreAuthDTO
   * @param bindingResult
   * @param model
   * @return
   */
  @RequestMapping(value = CHATAK_MERCHANT_VIRTUAL_TERMINAL_PRE_AUTH_PROCESS,
      method = RequestMethod.POST)
  public ModelAndView processPreAuth(HttpServletRequest request, HttpServletResponse response,
      HttpSession session, VirtualTerminalPreAuthDTO virtualTerminalPreAuthDTO,
      BindingResult bindingResult, Map model) {

    logger.info("Entering :: VirtualTerminalController :: processPreAuth method");
    ModelAndView modelAndView = new ModelAndView(CHATAK_MERCHANT_VIRTUAL_TERMINAL_PRE_AUTH_PAGE);
    Long merchantId = (Long) session.getAttribute(Constants.LOGIN_USER_MERCHANT_ID);
    if (null != merchantId) {
      try {
        GetMerchantDetailsResponse merchantDetailsResponse =
            paymentService.getMerchantIdAndTerminalId(merchantId.toString());
        if (merchantDetailsResponse.getErrorCode().equals(Constants.SUCCESS_CODE)) {
          virtualTerminalPreAuthDTO.setMerchantId(merchantDetailsResponse.getMerchantId());
          virtualTerminalPreAuthDTO.setTerminalId(merchantDetailsResponse.getTerminalId());
        } else {
          modelAndView.addObject(Constants.ERROR, merchantDetailsResponse.getErrorMessage());
          modelAndView.addObject(Constants.VIRTUAL_TERMINAL_PRE_AUTH, virtualTerminalPreAuthDTO);
          return modelAndView;
        }
        virtualTerminalPreAuthDTO.setExpDate(virtualTerminalPreAuthDTO.getYear().substring(Constants.TWO)
            + virtualTerminalPreAuthDTO.getMonth());
        TransactionResponse preAuthResponse = paymentService.doPreAuth(virtualTerminalPreAuthDTO);
        if (preAuthResponse != null
            && preAuthResponse.getErrorCode().equals(Constants.SUCCESS_CODE)) {
          model.put(Constants.TXN_REF_NUM, preAuthResponse);
          model.put(Constants.REF_FLAG, true);
          virtualTerminalPreAuthDTO.setSuccessDiv(true);
          modelAndView.addObject(Constants.SUCESS, preAuthResponse.getErrorMessage());
          modelAndView.addObject(Constants.VIRTUAL_TERMINAL_PRE_AUTH, virtualTerminalPreAuthDTO);
        } else if (preAuthResponse != null){
          modelAndView.addObject(Constants.ERROR, preAuthResponse.getErrorMessage());
          modelAndView.addObject(Constants.VIRTUAL_TERMINAL_PRE_AUTH, virtualTerminalPreAuthDTO);
        }
      } catch (ChatakPayException e) {
        modelAndView.addObject(Constants.ERROR, e.getMessage());
        modelAndView.addObject(Constants.VIRTUAL_TERMINAL_PRE_AUTH, virtualTerminalPreAuthDTO);
        logger.error("ERROR :: VirtualTerminalController :: processPreAuth method:" + e);
      } catch (Exception e) {
        logger.error("ERROR :: VirtualTerminalController :: processPreAuth method:" + e);
        modelAndView.addObject(Constants.ERROR, messageSource.getMessage(Constants.CHATAK_GENERAL_ERROR,
            null, LocaleContextHolder.getLocale()));
        modelAndView.addObject(Constants.VIRTUAL_TERMINAL_PRE_AUTH, virtualTerminalPreAuthDTO);
      }
      logger.info("Exiting :: VirtualTerminalController :: processPreAuth method");
      return modelAndView;
    } else {
      modelAndView.addObject(Constants.ERROR, messageSource.getMessage(
          Constants.VIRTUAL_TERMINAL_CONTROLLER_INVALID_RETRY, null, LocaleContextHolder.getLocale()));
      modelAndView.addObject(Constants.VIRTUAL_TERMINAL_PRE_AUTH, virtualTerminalPreAuthDTO);
      try {
        response.sendRedirect(Constants.CHATAK_MERCHANT_SESSION_INVALID_JSP);
      } catch (IOException e) {
        logger.error("Error :: VirtualTerminalController :: processPreAuth method", e);
      }
      logger.info("Exiting :: VirtualTerminalController :: processPreAuth method");
      return modelAndView;
    }
  }

  /**
   * @param request
   * @param response
   * @param session
   * @param model
   * @return
   */
  @RequestMapping(value = CHATAK_MERCHANT_VIRTUAL_TERMINAL_ADJUST_PAGE, method = RequestMethod.GET)
  public ModelAndView showVirtualterminalAdjust(HttpServletRequest request,
      HttpServletResponse response, HttpSession session, Map model) {

    logger.info("Entering :: VirtualTerminalController :: showVirtualterminalAdjust method");
    ModelAndView modelAndView = new ModelAndView(CHATAK_MERCHANT_VIRTUAL_TERMINAL_ADJUST_PAGE);
    modelAndView.addObject(Constants.VIRTUAL_TEMINAL_ADJUST, new VirtualTerminalAdjustmentDTO());
    logger.info("Exiting :: VirtualTerminalController :: showVirtualterminalAdjust method");
    return modelAndView;
  }

  /**
   * Method get the Fraud Basic page
   * 
   * @param request
   * @param response
   * @param session
   * @param terminalAdjustmentDTO
   * @param bindingResult
   * @param model
   * @return
   */
  @RequestMapping(value = CHATAK_MERCHANT_VIRTUAL_TERMINAL_ADJUST_PROCESS,
      method = RequestMethod.POST)
  public @ResponseBody ModelAndView processAdjust(HttpServletRequest request,
      HttpServletResponse response, HttpSession session,
      @FormParam(Constants.TXN_REF_NUM) final String txnRefNum,
      VirtualTerminalAdjustmentDTO terminalAdjustmentDTO, BindingResult bindingResult, Map model) {
    String expDate=request.getParameter("expDate");
    logger.info("Entering :: VirtualTerminalController :: processAdjust method");
    ModelAndView modelAndView = new ModelAndView(CHATAK_MERCHANT_VIRTUAL_TERMINAL_ADJUST_PAGE);
    Long merchantId = (Long) session.getAttribute(Constants.LOGIN_USER_MERCHANT_ID);
    if (null != merchantId) {
      try {
    	terminalAdjustmentDTO.setExpDate(expDate);
        terminalAdjustmentDTO.setTxnRefNum(txnRefNum);
        GetMerchantDetailsResponse merchantDetailsResponse =
            paymentService.getMerchantIdAndTerminalId(merchantId.toString());
        if (merchantDetailsResponse.getErrorCode().equals(Constants.SUCCESS_CODE)) {
          terminalAdjustmentDTO.setMerchantId(merchantDetailsResponse.getMerchantId());
          terminalAdjustmentDTO.setTerminalId(merchantDetailsResponse.getTerminalId());
        } else {
          modelAndView.addObject(Constants.ERROR, merchantDetailsResponse.getErrorMessage());
          modelAndView.addObject(Constants.VIRTUAL_TEMINAL_ADJUST, terminalAdjustmentDTO);
          return modelAndView;
        }
        VirtualTerminalAdjustmentResponse adjustmentResponse =
            paymentService.doAdjust(terminalAdjustmentDTO);
        if (adjustmentResponse != null
            && adjustmentResponse.getErrorCode().equals(Constants.SUCCESS_CODE)) {
          model.put(Constants.TXN_REF_NUM, adjustmentResponse);
          model.put(Constants.REF_FLAG, true);
          modelAndView.addObject(Constants.SUCESS, adjustmentResponse.getErrorMessage());
          modelAndView.addObject(Constants.VIRTUAL_TEMINAL_ADJUST, new VirtualTerminalAdjustmentDTO());
        } else if (adjustmentResponse != null){
          modelAndView.addObject(Constants.ERROR, adjustmentResponse.getErrorMessage());
          modelAndView.addObject(Constants.VIRTUAL_TEMINAL_ADJUST, terminalAdjustmentDTO);
        }
      } catch (ChatakPayException e) {
        modelAndView.addObject(Constants.ERROR, e.getMessage());
        modelAndView.addObject(Constants.VIRTUAL_TEMINAL_ADJUST, terminalAdjustmentDTO);
        logger.error("ERROR :: VirtualTerminalController :: processAdjust method:" + e);
      } catch (Exception e) {
        modelAndView.addObject(Constants.ERROR, messageSource.getMessage(Constants.CHATAK_GENERAL_ERROR,
            null, LocaleContextHolder.getLocale()));
        modelAndView.addObject(Constants.VIRTUAL_TEMINAL_ADJUST, terminalAdjustmentDTO);
        logger.error("ERROR :: VirtualTerminalController :: processAdjust method:" + e);
      }
      logger.info("Exiting :: VirtualTerminalController :: processAdjust method");
      return modelAndView;
    } else {
      modelAndView.addObject(Constants.ERROR, messageSource.getMessage(
          Constants.VIRTUAL_TERMINAL_CONTROLLER_INVALID_RETRY, null, LocaleContextHolder.getLocale()));
      modelAndView.addObject(Constants.VIRTUAL_TEMINAL_ADJUST, terminalAdjustmentDTO);
      try {
        response.sendRedirect(Constants.CHATAK_MERCHANT_SESSION_INVALID_JSP);
      } catch (IOException e) {
        logger.error("Error :: VirtualTerminalController :: processAdjust method", e);
      }
      logger.error("Error :: VirtualTerminalController :: processAdjust method");
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
  @RequestMapping(value = CHATAK_MERCHANT_VIRTUAL_TERMINAL_VOID_PAGE, method = RequestMethod.GET)
  public ModelAndView showVirtualterminalVoid(HttpServletRequest request,
      HttpServletResponse response, HttpSession session, Map model) {

    logger.info("Entering :: VirtualTerminalController :: showVirtualterminalVoid method");
    ModelAndView modelAndView = new ModelAndView(CHATAK_MERCHANT_VIRTUAL_TERMINAL_VOID_PAGE);
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
  @RequestMapping(value = CHATAK_MERCHANT_VIRTUAL_TERMINAL_VOID_PROCESS,
      method = RequestMethod.POST)
  public @ResponseBody ModelAndView processVoid(HttpServletRequest request,
      HttpServletResponse response, HttpSession session,
      @FormParam(Constants.TXN_REF_NUM) final String txnRefNum, VirtualTerminalVoidDTO virtualTerminalVoidDTO,
      BindingResult bindingResult, Map model) {

    logger.info("Entering :: VirtualTerminalController :: processVoid method");
    ModelAndView modelAndView = new ModelAndView(CHATAK_MERCHANT_VIRTUAL_TERMINAL_VOID_PAGE);
    Long merchantId = (Long) session.getAttribute(Constants.LOGIN_USER_MERCHANT_ID);
    if (null != merchantId) {
      try {
        virtualTerminalVoidDTO.setUserName((String) session.getAttribute(CHATAK_ADMIN_USER_NAME));
        GetMerchantDetailsResponse merchantDetailsResponse =
            paymentService.getMerchantIdAndTerminalId(merchantId.toString());
        if (merchantDetailsResponse.getErrorCode().equals(Constants.SUCCESS_CODE)) {
          virtualTerminalVoidDTO.setMerchantId(merchantDetailsResponse.getMerchantId());
          virtualTerminalVoidDTO.setTerminalId(merchantDetailsResponse.getTerminalId());
        } else {
          modelAndView.addObject(Constants.ERROR, merchantDetailsResponse.getErrorMessage());
          modelAndView.addObject(Constants.VIRTUAL_TEMINAL_VOID, virtualTerminalVoidDTO);
          return modelAndView;
        }
        TransactionResponse response2 = paymentService.doVoid(virtualTerminalVoidDTO);
        if (response2 != null && response2.getErrorCode().equals(Constants.SUCCESS_CODE)) {
          model.put(Constants.TXN_REF_NUM, response2);
          model.put(Constants.REF_FLAG, true);
          modelAndView.addObject(Constants.SUCESS, response2.getErrorMessage());
          virtualTerminalVoidDTO.setSuccessDiv(true);
          modelAndView.addObject(Constants.VIRTUAL_TEMINAL_VOID, virtualTerminalVoidDTO);
        } else if (response2 != null){
          modelAndView.addObject(Constants.ERROR, response2.getErrorMessage());
          modelAndView.addObject(Constants.VIRTUAL_TEMINAL_VOID, virtualTerminalVoidDTO);
        }
      } catch (ChatakPayException e) {
        modelAndView.addObject(Constants.ERROR, e.getMessage());
        modelAndView.addObject(Constants.VIRTUAL_TEMINAL_VOID, virtualTerminalVoidDTO);
        logger.error("ERROR :: VirtualTerminalController :: processVoid method:" + e);
      } catch (Exception exp) {
        modelAndView.addObject(Constants.ERROR, messageSource.getMessage(Constants.CHATAK_GENERAL_ERROR,
            null, LocaleContextHolder.getLocale()));
        modelAndView.addObject(Constants.VIRTUAL_TEMINAL_VOID, virtualTerminalVoidDTO);
        logger.error("ERROR :: VirtualTerminalController :: processVoid method:" + exp);
      }
      logger.info("Exiting :: VirtualTerminalController :: processVoid method");
      return modelAndView;
    } else {
      modelAndView.addObject(Constants.ERROR, messageSource.getMessage(
          Constants.VIRTUAL_TERMINAL_CONTROLLER_INVALID_RETRY, null, LocaleContextHolder.getLocale()));
      modelAndView.addObject(Constants.VIRTUAL_TEMINAL_VOID, virtualTerminalVoidDTO);
      try {
        response.sendRedirect(Constants.CHATAK_MERCHANT_SESSION_INVALID_JSP);
      } catch (IOException e) {
        logger.info("Error :: VirtualTerminalController :: processVoid method", e);
      }
      logger.info("Exiting :: VirtualTerminalController :: processVoid method");
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
  @RequestMapping(value = CHATAK_MERCHANT_VIRTUAL_TERMINAL_REFUND_PAGE, method = RequestMethod.GET)
  public ModelAndView showVirtualterminalRefund(HttpServletRequest request,
      HttpServletResponse response, HttpSession session, Map model) {
    logger.info("Entering :: VirtualTerminalController :: showVirtualterminalRefund method");
    ModelAndView modelAndView = new ModelAndView(CHATAK_MERCHANT_VIRTUAL_TERMINAL_REFUND_PAGE);
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
  @RequestMapping(value = CHATAK_MERCHANT_VIRTUAL_TERMINAL_REFUND_PROCESS,
      method = RequestMethod.POST)
  public @ResponseBody ModelAndView processRefund(HttpServletRequest request,
      HttpServletResponse response, HttpSession session,
      VirtualTerminalRefundDTO virtualTerminalRefundDTO, BindingResult bindingResult, Map model) {

    logger.info("Entering :: VirtualTerminalController :: processRefund method");
    ModelAndView modelAndView = new ModelAndView(CHATAK_MERCHANT_VIRTUAL_TERMINAL_REFUND_PAGE);
    Long merchantId = (Long) session.getAttribute(Constants.LOGIN_USER_MERCHANT_ID);
    if (null != merchantId) {
      try {
        virtualTerminalRefundDTO.setUserName((String) session.getAttribute(CHATAK_ADMIN_USER_NAME));
        GetMerchantDetailsResponse merchantDetailsResponse =
            paymentService.getMerchantIdAndTerminalId(merchantId.toString());
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
          refundResponse.setTotalTxnAmount(virtualTerminalRefundDTO.getTxnAmount());
          model.put(Constants.TXN_REF_NUM, refundResponse);
          model.put(Constants.REF_FLAG, true);
          modelAndView.addObject(Constants.SUCESS, refundResponse.getErrorMessage());
          virtualTerminalRefundDTO.setSuccessDiv(true);
          modelAndView.addObject(Constants.VIRTUAL_TEMINAL_REFUND, virtualTerminalRefundDTO);
        } else if (refundResponse != null){
          modelAndView.addObject(Constants.ERROR, refundResponse.getErrorMessage());
          modelAndView.addObject(Constants.VIRTUAL_TEMINAL_REFUND, virtualTerminalRefundDTO);
        }
      } catch (ChatakPayException e) {
        modelAndView.addObject(Constants.ERROR, e.getMessage());
        modelAndView.addObject(Constants.VIRTUAL_TEMINAL_REFUND, virtualTerminalRefundDTO);
        logger.error("ERROR :: VirtualTerminalController :: processRefund method:" + e);
      } catch (Exception exp) {
        modelAndView.addObject(Constants.ERROR, messageSource.getMessage(Constants.CHATAK_GENERAL_ERROR,
            null, LocaleContextHolder.getLocale()));
        modelAndView.addObject(Constants.VIRTUAL_TEMINAL_REFUND, virtualTerminalRefundDTO);
        logger.error("ERROR :: VirtualTerminalController :: processRefund method:" + exp);
      }
      logger.info("Exiting :: VirtualTerminalController :: processRefund method");
      return modelAndView;
    } else {
      modelAndView.addObject(Constants.ERROR, messageSource.getMessage(
          Constants.VIRTUAL_TERMINAL_CONTROLLER_INVALID_RETRY, null, LocaleContextHolder.getLocale()));
      modelAndView.addObject(Constants.VIRTUAL_TEMINAL_REFUND, virtualTerminalRefundDTO);
      try {
        response.sendRedirect(Constants.CHATAK_MERCHANT_SESSION_INVALID_JSP);
      } catch (IOException e) {
        logger.info("Exiting :: VirtualTerminalController :: processRefund method", e);
      }
      return modelAndView;
    }
  }

  @RequestMapping(value = CHATAK_MERCHANT_VIRTUAL_TERMINAL_REFUND_PROCESS,
      method = RequestMethod.GET)
  public @ResponseBody ModelAndView processRefundOnRefresh(HttpServletRequest request,
      HttpServletResponse response, HttpSession session,
      VirtualTerminalRefundDTO virtualTerminalRefundDTO, BindingResult bindingResult, Map model) {
    ModelAndView modelAndView = new ModelAndView(CHATAK_MERCHANT_VIRTUAL_TERMINAL_REFUND_PAGE);
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
  @RequestMapping(value = CHATAK_MERCHANT_VIRTUAL_TERMINAL_PREAUTH_COMPLEATION_PAGE,
      method = RequestMethod.GET)
  public ModelAndView showVirtualterminalPreAuthCompleation(HttpServletRequest request,
      HttpServletResponse response, HttpSession session, Map model) {
    logger.info(
        "Entering :: VirtualTerminalController :: showVirtualterminalPreAuthCompleation method");
    ModelAndView modelAndView =
        new ModelAndView(CHATAK_MERCHANT_VIRTUAL_TERMINAL_PREAUTH_COMPLEATION_PAGE);
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
  @RequestMapping(value = CHATAK_MERCHANT_VIRTUAL_TERMINAL_PREAUTH_COMPLEATION_PROCESS,
      method = RequestMethod.POST)
  public @ResponseBody ModelAndView processPreAuthCompleation(HttpServletRequest request,
      HttpServletResponse response, HttpSession session,
      @FormParam(Constants.TXN_REF_NUM) final String txnRefNum,
      VirtualTerminalCaptureDTO virtualTerminalCaptureDTO, BindingResult bindingResult, Map model) {
    logger.info("Entering :: VirtualTerminalController :: processPreAuthCompleation method");

    ModelAndView modelAndView =
        new ModelAndView(CHATAK_MERCHANT_VIRTUAL_TERMINAL_PREAUTH_COMPLEATION_PAGE);
    Long merchantId = (Long) session.getAttribute(Constants.LOGIN_USER_MERCHANT_ID);
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
          virtualTerminalCaptureDTO.setSuccessDiv(true);
          modelAndView.addObject(Constants.VIRTUAL_TEMINAL_PRE_AUTH_COMPLEATION, virtualTerminalCaptureDTO);
        } else if (captureResponse != null){
          modelAndView.addObject(Constants.ERROR, captureResponse.getErrorMessage());
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
      modelAndView.addObject(Constants.ERROR, messageSource.getMessage(
          Constants.VIRTUAL_TERMINAL_CONTROLLER_INVALID_RETRY, null, LocaleContextHolder.getLocale()));
      modelAndView.addObject(Constants.VIRTUAL_TEMINAL_PRE_AUTH_COMPLEATION, virtualTerminalCaptureDTO);
      try {
        response.sendRedirect(Constants.CHATAK_MERCHANT_SESSION_INVALID_JSP);
      } catch (IOException e) {
        logger.error("Error :: VirtualTerminalController :: processPreAuthCompleation method", e);
      }
      return modelAndView;
    }
  }

  @RequestMapping(value = CHATAK_PROCESS_POPUP_ACTION, method = RequestMethod.POST)
  public @ResponseBody TransactionResponse processPopupAction(HttpServletRequest request,
      HttpServletResponse response, HttpSession session, TransactionRequest transactionRequest,
      Model model) {
    TransactionResponse txnResponse = null;
    try {
      if (transactionRequest != null) {
        transactionRequest.setUserName((String) session.getAttribute(CHATAK_ADMIN_USER_NAME));
        switch (transactionRequest.getTransactionType()) {
          case VOID:
            txnResponse = paymentService.processPopupVoidOrRefund(transactionRequest);
            break;
          case REFUND:
            getAmountDetails(transactionRequest);
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
    } catch (Exception exp) {
      logger.error("Error :: VirtualTerminalController :: processPopupAction Method", exp);
      txnResponse = new TransactionResponse();
      txnResponse.setErrorCode(ActionErrorCode.ERROR_CODE_Z5);
      txnResponse
          .setErrorMessage(ActionErrorCode.getInstance().getMessage(ActionErrorCode.ERROR_CODE_Z5));
    }
    return txnResponse;
  }

  private void getAmountDetails(TransactionRequest transactionRequest) {
    if (PGConstants.PARTIAL_REFUND_FLAG.equals(transactionRequest.getPartialRefundFlag())) {
      transactionRequest.setMerchantAmount(transactionRequest.getTotalTxnAmount());
    } else {
      transactionRequest.setTotalTxnAmount(transactionRequest.getTotalTxnAmount());
    }
  }
}
