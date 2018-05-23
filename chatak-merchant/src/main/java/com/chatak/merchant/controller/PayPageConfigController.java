package com.chatak.merchant.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.DataBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.chatak.pg.util.Constants;
import com.chatak.merchant.constants.URLMappingConstants;
import com.chatak.merchant.exception.ChatakMerchantException;
import com.chatak.merchant.model.PayPageConfig;
import com.chatak.merchant.model.PayPageConfigResponse;
import com.chatak.merchant.service.PayPageConfigService;
import com.chatak.pg.model.Response;

@Controller
public class PayPageConfigController implements URLMappingConstants {

  private Logger logger = Logger.getLogger(PayPageConfigController.class);

  @Autowired
  PayPageConfigService payPageConfigService;

  @Autowired
  private MessageSource messageSource;

  @RequestMapping(value = CHATAK_MERCHANT_PAY_PAGE_CONFIG, method = RequestMethod.GET)
  public ModelAndView showPayPageConfig(HttpServletRequest request, HttpServletResponse response,
      HttpSession session, PayPageConfig payPageConfig, BindingResult bindingResult) {
    logger.info("Entering :: PayPageConfigController :: showPayPageConfig method");
    session.setAttribute(Constants.ERROR, null);
    session.setAttribute(Constants.SUCESS, null);
    String payPageLogo = null;
    ModelAndView modelAndView = new ModelAndView(CHATAK_MERCHANT_PAY_PAGE_CONFIG);
    Long merchantId = (Long) session.getAttribute(Constants.LOGIN_USER_MERCHANT_ID);
    if (null != merchantId) {
      try {
        PayPageConfigResponse payPageConfigResponse =
            payPageConfigService.getPayPageConfigDetails(merchantId);
        if (null != payPageConfigResponse
            && payPageConfigResponse.getErrorMessage().equals(Constants.SUCESS)) {
          payPageConfig.setHeader(payPageConfigResponse.getHeader());
          payPageConfig.setFooter(payPageConfigResponse.getFooter());
          payPageLogo = new String(payPageConfigResponse.getPayPageLogo());
          modelAndView.addObject("payPageConfig", payPageConfig);
          modelAndView.addObject("payPageLogo", payPageLogo);
        }
      } catch (ChatakMerchantException e) {
        logger.error("ERROR:: PayPageConfigController:: showPayPageConfig method", e);
      }
    } else {
      modelAndView.addObject(Constants.ERROR, "invalid session,retry after login");
      try {
        response.sendRedirect("/chatak-merchant//session-invalid.jsp");
      } catch (IOException e) {
        logger.error("ERROR:: PayPageConfigController:: showPayPageConfig method", e);
      }
    }
    logger.info("Exiting:: PayPageConfigController:: showPayPageConfig method");
    return modelAndView;
  }

  @RequestMapping(value = CHATAK_MERCHANT_PAY_PAGE_CONFIG, method = RequestMethod.POST)
  public ModelAndView saveOrUpdatePayPageConfig(HttpServletRequest request,
      HttpServletResponse response, HttpSession session, PayPageConfig payPageConfig,
      BindingResult bindingResult, @RequestParam("payPageLogo") String payPageLogo) {
    logger.info("Entering :: PayPageConfigController :: saveOrUpdatePayPageConfig method");
    ModelAndView modelAndView = new ModelAndView(CHATAK_MERCHANT_PAY_PAGE_CONFIG);
    session.setAttribute(Constants.ERROR, null);
    session.setAttribute(Constants.SUCESS, null);
    byte[] logo = null;
    PayPageConfig payPageConfig2 = new PayPageConfig();
    DataBinder dataBinder = new DataBinder(payPageConfig2);
    Long merchantId = (Long) session.getAttribute(Constants.LOGIN_USER_MERCHANT_ID);

    if (request.getHeader("referer") == null) {
      session.invalidate();
      modelAndView.setViewName(INVALID_REQUEST_PAGE);
      return modelAndView;
    }

    if (null != merchantId) {
      try {
        if (null == payPageLogo) {
          throw new ChatakMerchantException(
              messageSource.getMessage("chatak.merchant.paypageconfig.logo.error.message", null,
                  LocaleContextHolder.getLocale()));
        }
        logo = payPageLogo.getBytes();
        payPageConfig.setMerchantId(merchantId);
        payPageConfig.setPayPageLogo(logo);
        Response response2 = payPageConfigService.saveOrUpdatePayPageConfig(payPageConfig);
        if (null != response2 && response2.getErrorCode().equals(Constants.SUCCESS_CODE)) {
          modelAndView = showPayPageConfig(request, response, session, payPageConfig2,
              dataBinder.getBindingResult());
          modelAndView.addObject(Constants.SUCESS,
              messageSource.getMessage("chatak.merchant.paypageconfig.configured.success", null,
                  LocaleContextHolder.getLocale()));
        } else {
          modelAndView = showPayPageConfig(request, response, session, payPageConfig2,
              dataBinder.getBindingResult());
          modelAndView.addObject(Constants.ERROR,
              messageSource.getMessage("chatak.merchant.paypageconfig.configured.failure", null,
                  LocaleContextHolder.getLocale()));
        }
      } catch (ChatakMerchantException e) {
        logger.error("ERROR:: PayPageConfigController:: saveOrUpdatePayPageConfig method", e);
        modelAndView = showPayPageConfig(request, response, session, payPageConfig2,
            dataBinder.getBindingResult());
        modelAndView.addObject(Constants.ERROR, e);
      }

    } else {
      modelAndView.addObject(Constants.ERROR, "invalid session,retry after login");
      try {
        response.sendRedirect("/chatak-merchant//session-invalid.jsp");
      } catch (IOException e) {
        logger.error("ERROR:: PayPageConfigController:: saveOrUpdatePayPageConfig method", e);
      }
    }

    logger.info("Exiting:: PayPageConfigController:: saveOrUpdatePayPageConfig method");
    return modelAndView;
  }
}
