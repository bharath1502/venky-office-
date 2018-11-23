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
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.chatak.acquirer.admin.constants.URLMappingConstants;
import com.chatak.acquirer.admin.exception.ChatakPayException;
import com.chatak.acquirer.admin.service.BlackListedCardService;
import com.chatak.acquirer.admin.service.MerchantService;
import com.chatak.acquirer.admin.service.RestPaymentService;
import com.chatak.pg.model.GetMerchantDetailsResponse;
import com.chatak.pg.model.TransactionResponse;
import com.chatak.pg.model.VirtualTerminalAdjustmentDTO;
import com.chatak.pg.model.VirtualTerminalAdjustmentResponse;
import com.chatak.pg.model.VirtualTerminalPreAuthDTO;
import com.chatak.pg.util.Constants;

@SuppressWarnings({"rawtypes", "unchecked"})
@Controller
public class VirtualTerminalPreAuthController implements URLMappingConstants {

  private static Logger logger = Logger.getLogger(VirtualTerminalPreAuthController.class);

  @Autowired
  RestPaymentService paymentService;

  @Autowired
  private MessageSource messageSource;

  @Autowired
  MerchantService merchantService;

  @Autowired
  BlackListedCardService blackListedCardService;

  @RequestMapping(value = CHATAK_ADMIN_VIRTUAL_TERMINAL_PRE_AUTH_PAGE, method = RequestMethod.GET)
  public ModelAndView showVirtualterminalPreAuth(HttpServletRequest request,
      HttpServletResponse response, HttpSession session, Map model) {
    logger.info("Entering :: VirtualTerminalPreAuthController :: showVirtualterminalPreAuth method");
    ModelAndView modelAndView = new ModelAndView(CHATAK_ADMIN_VIRTUAL_TERMINAL_PRE_AUTH_PAGE);
    modelAndView.addObject(Constants.VIRTUAL_TERMINAL_PRE_AUTH, new VirtualTerminalPreAuthDTO());
    logger.info("Exiting :: VirtualTerminalPreAuthController :: showVirtualterminalPreAuth method");
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
  @RequestMapping(value = CHATAK_ADMIN_VIRTUAL_TERMINAL_PRE_AUTH_PROCESS,
      method = RequestMethod.POST)
  public ModelAndView processPreAuth(HttpServletRequest request, HttpServletResponse response,
      HttpSession session, VirtualTerminalPreAuthDTO virtualTerminalPreAuthDTO,
      BindingResult bindingResult, Map model) {

    logger.info("Entering :: VirtualTerminalPreAuthController :: processPreAuth method");
    ModelAndView modelAndView = new ModelAndView(CHATAK_ADMIN_VIRTUAL_TERMINAL_PRE_AUTH_PAGE);
    String merchantId = virtualTerminalPreAuthDTO.getMerchantId();
    if (null != merchantId) {
      try {
        GetMerchantDetailsResponse merchantDetailsResponse =
            paymentService.getMerchantIdAndTerminalId(virtualTerminalPreAuthDTO.getMerchantId());
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
          model.put("txnRefNum", preAuthResponse);
          model.put("refFlag", true);
          modelAndView.addObject(Constants.SUCESS, preAuthResponse.getErrorMessage());
          modelAndView.addObject(Constants.VIRTUAL_TERMINAL_PRE_AUTH, new VirtualTerminalPreAuthDTO());
        } else {
          setPreAuthError(modelAndView, preAuthResponse);
          modelAndView.addObject(Constants.VIRTUAL_TERMINAL_PRE_AUTH, virtualTerminalPreAuthDTO);
        }
      } catch (ChatakPayException e) {
        modelAndView.addObject(Constants.ERROR, e.getMessage());
        modelAndView.addObject(Constants.VIRTUAL_TERMINAL_PRE_AUTH, virtualTerminalPreAuthDTO);
        logger.error("ERROR :: VirtualTerminalPreAuthController :: processPreAuth method:" + e);
      } catch (Exception e) {
        logger.error("ERROR :: VirtualTerminalPreAuthController :: processPreAuth method:" + e);
        modelAndView.addObject(Constants.ERROR, messageSource.getMessage("chatak.general.error",
            null, LocaleContextHolder.getLocale()));
        modelAndView.addObject(Constants.VIRTUAL_TERMINAL_PRE_AUTH, virtualTerminalPreAuthDTO);
      }
      logger.info("Exiting :: VirtualTerminalPreAuthController :: processPreAuth method");
      return modelAndView;
    } else {
      modelAndView.addObject(Constants.ERROR, "invalid session ,retry after login");
      modelAndView.addObject(Constants.VIRTUAL_TERMINAL_PRE_AUTH, virtualTerminalPreAuthDTO);
      try {
        response.sendRedirect("/chatak-acquirer-admin/session-invalid.jsp");
      } catch (IOException e) {
        logger.error("Error :: VirtualTerminalPreAuthController :: processPreAuth method", e);
      }
      logger.info("Exiting :: VirtualTerminalPreAuthController :: processPreAuth method");
      return modelAndView;
    }
  }
  
  private ModelAndView setPreAuthError(ModelAndView modelAndView,
      TransactionResponse preAuthResponse) {
    if (preAuthResponse != null) {
      modelAndView.addObject(Constants.ERROR, preAuthResponse.getErrorMessage());
    } else {
      modelAndView.addObject(Constants.ERROR, "");
    }
    return modelAndView;
  }

  /**
   * @param request
   * @param response
   * @param session
   * @param model
   * @return
   */
  @RequestMapping(value = CHATAK_ADMIN_VIRTUAL_TERMINAL_ADJUST_PAGE, method = RequestMethod.GET)
  public ModelAndView showVirtualterminalAdjust(HttpServletRequest request,
      HttpServletResponse response, HttpSession session, Map model) {
    logger.info("Entering :: VirtualTerminalPreAuthController :: showVirtualterminalAdjust method");
    ModelAndView modelAndView = new ModelAndView(CHATAK_ADMIN_VIRTUAL_TERMINAL_ADJUST_PAGE);
    modelAndView.addObject(Constants.VIRTUAL_TEMINAL_ADJUST, new VirtualTerminalAdjustmentDTO());
    logger.info("Exiting :: VirtualTerminalPreAuthController :: showVirtualterminalAdjust method");
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
  @RequestMapping(value = CHATAK_ADMIN_VIRTUAL_TERMINAL_ADJUST_PROCESS, method = RequestMethod.POST)
  public @ResponseBody ModelAndView processAdjust(HttpServletRequest request,
      HttpServletResponse response, HttpSession session,
      @FormParam("txnRefNum") final String txnRefNum, @FormParam("expDate") final String expDate,
      VirtualTerminalAdjustmentDTO terminalAdjustmentDTO, BindingResult bindingResult, Map model) {
    logger.info("Entering :: VirtualTerminalPreAuthController :: processAdjust method");
    ModelAndView modelAndView = new ModelAndView(CHATAK_ADMIN_VIRTUAL_TERMINAL_ADJUST_PAGE);
    Long merchantId = (Long) session.getAttribute(Constants.LOGIN_USER_ID);
    if (null != merchantId) {
      try {
        terminalAdjustmentDTO.setTxnRefNum(txnRefNum);
        terminalAdjustmentDTO.setExpDate(expDate);
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
          model.put("txnRefNum", adjustmentResponse);
          model.put("refFlag", true);
          modelAndView.addObject(Constants.SUCESS, adjustmentResponse.getErrorMessage());
          modelAndView.addObject(Constants.VIRTUAL_TEMINAL_ADJUST, new VirtualTerminalAdjustmentDTO());
        } else {
          setAdjustmentError(modelAndView, adjustmentResponse);
          modelAndView.addObject(Constants.VIRTUAL_TEMINAL_ADJUST, terminalAdjustmentDTO);
        }
      } catch (ChatakPayException e) {
        modelAndView.addObject(Constants.ERROR, e.getMessage());
        modelAndView.addObject(Constants.VIRTUAL_TEMINAL_ADJUST, terminalAdjustmentDTO);
        logger.error("ERROR :: VirtualTerminalPreAuthController :: processAdjust method:" + e);
      } catch (Exception e) {
        modelAndView.addObject(Constants.ERROR, messageSource.getMessage("chatak.general.error",
            null, LocaleContextHolder.getLocale()));
        modelAndView.addObject(Constants.VIRTUAL_TEMINAL_ADJUST, terminalAdjustmentDTO);
        logger.error("ERROR :: VirtualTerminalPreAuthController :: processAdjust method:" + e);
      }
      logger.info("Exiting :: VirtualTerminalPreAuthController :: processAdjust method");
      return modelAndView;
    } else {
      modelAndView.addObject(Constants.ERROR, "invalid session ,retry after login");
      modelAndView.addObject(Constants.VIRTUAL_TEMINAL_ADJUST, terminalAdjustmentDTO);
      try {
        response.sendRedirect("/chatak-acquirer-admin/session-invalid.jsp");
      } catch (IOException e) {
        logger.error("Error :: VirtualTerminalPreAuthController :: processAdjust method:", e);
      }
      return modelAndView;
    }
  }
  
  private ModelAndView setAdjustmentError(ModelAndView modelAndView,
      VirtualTerminalAdjustmentResponse adjustmentResponse) {
    if (adjustmentResponse != null) {
      modelAndView.addObject(Constants.ERROR, adjustmentResponse.getErrorMessage());
    } else {
      modelAndView.addObject(Constants.ERROR, "");
    }
    return modelAndView;
  }

}
