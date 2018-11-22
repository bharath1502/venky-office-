package com.chatak.acquirer.admin.controller;

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
import com.chatak.acquirer.admin.service.CurrencyService;
import com.chatak.acquirer.admin.service.DCCMarkupService;
import com.chatak.acquirer.admin.util.JsonUtil;
import com.chatak.acquirer.admin.util.PaginationUtil;
import com.chatak.pg.bean.DCCCurrency;
import com.chatak.pg.bean.DCCMarkup;
import com.chatak.pg.bean.DCCMarkupResponse;
import com.chatak.pg.bean.MerchantNameResponse;
import com.chatak.pg.bean.Response;
import com.chatak.pg.util.Constants;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
@SuppressWarnings({"unchecked", "rawtypes"})
public class DCCMarkupController implements URLMappingConstants {

  private static Logger logger = Logger.getLogger(DCCMarkupController.class);

  @Autowired
  private CurrencyService currencyService;

  @Autowired
  private DCCMarkupService dccMarkupService;

  @Autowired
  private MessageSource messageSource;

  /**
   * Method to show create dcc Markup page
   * 
   * @param model
   * @param session
   * @return
   */
  @RequestMapping(value = CHATAK_ADMIN_CREATE_DCC_MARKUP_PAGE, method = RequestMethod.GET)
  public ModelAndView showCreateMarkupPage(Map model, HttpSession session, DCCMarkup dccMarkup) {
    logger.info("Entering:: DCCMarkupController:: showCreateMarkupPage method");
    ModelAndView modelAndView = new ModelAndView(CHATAK_ADMIN_CREATE_DCC_MARKUP_PAGE);
    modelAndView.addObject(Constants.ERROR, null);
    session.setAttribute(Constants.ERROR, null);
    String existingFeature = (String) session.getAttribute(Constants.EXISTING_FEATURES);
    if (!existingFeature.contains(FeatureConstants.ADMIN_SERVICE_DCC_MARKUP_CREATE_FEATURE_ID)) {
      session.invalidate();
      modelAndView.setViewName(INVALID_REQUEST_PAGE);
      return modelAndView;
    }
    try {
      MerchantNameResponse merchantNames = dccMarkupService.getMarkupMerchantsCode();
      model.put(Constants.MERCHANT_NAMES_LIST, merchantNames.getMerchantNames());
      model.put(Constants.DCC_MARKUP, dccMarkup);
    } catch (Exception e) {
      modelAndView.addObject(Constants.ERROR, messageSource
          .getMessage(Constants.CHATAK_GENERAL_ERROR, null, LocaleContextHolder.getLocale()));
      logger.error("ERROR:: DCCMarkupController:: showCreateMarkupPage method", e);
    }
    logger.info("Exiting:: DCCMarkupController:: showCreateMarkupPage method");
    return modelAndView;
  }

  @RequestMapping(value = CHATAK_MERCHANT_CURRENCY_CODE, method = RequestMethod.POST)
  public @ResponseBody String getCurrencyCode(HttpServletRequest request, Map model,
      HttpServletResponse httpServletResponse) {
    logger.info("Entering:: DCCMarkupController:: getCurrencyCode method");
    String merchantCode = request.getParameter("merchantCode");
    DCCCurrency currencyCodes = new DCCCurrency();

    try {
      currencyCodes = currencyService.getCurrencyCode(merchantCode);
      if (currencyCodes != null) {
        return JsonUtil.convertObjectToJSON(currencyCodes);
      }
    } catch (Exception e) {
      logger.error("ERROR:: DCCMarkupController:: getCurrencyCode method", e);
    }
    return null;

  }


  @RequestMapping(value = CHATAK_ADMIN_CREATE_PROCESS, method = RequestMethod.POST)
  public @ResponseBody String createProcessMarkup(HttpServletRequest request, Map model,
      HttpServletResponse httpServletResponse) {
    logger.info("Entering:: DCCMarkupController:: createProcessMarkup method");
    DCCMarkup dccMarkup = new DCCMarkup();
    String merchantCode = request.getParameter("merchantCode");
    String arrayList = request.getParameter("arrayData");
    try {
      ObjectMapper mapper = new ObjectMapper();
      dccMarkup.setMerchantCode(merchantCode);
      List<DCCCurrency> currencyDatas = mapper.readValue(arrayList,
          new TypeReference<List<DCCCurrency>>() {});
      dccMarkup.setDccCurrency(currencyDatas);
      Response response = dccMarkupService.addDccMarkup(dccMarkup);
      if (response != null) {
        return JsonUtil.convertObjectToJSON(response);
      }
    } catch (Exception e) {
      logger.error("ERROR:: DCCMarkupController:: getCurrencyCode method", e);
    }
    return null;

  }

  @RequestMapping(value = CHATAK_ADMIN_SEARCH_MARKUP_FEE_PAGE, method = RequestMethod.GET)
  public ModelAndView showSearchMarkupFeePage(HttpServletRequest request,
      HttpServletResponse response, DCCMarkup dccMarkup, BindingResult bindingResult, Map model,
      HttpSession session) {
    logger.info("Entering:: DCCMarkupController:: showSearchMarkupFeePage method");
    ModelAndView modelAndView = new ModelAndView(CHATAK_ADMIN_SEARCH_MARKUP_FEE_PAGE);
    String existingFeature = (String) session.getAttribute(Constants.EXISTING_FEATURES);
    if (!existingFeature.contains(FeatureConstants.ADMIN_SERVICE_DCC_MARKUP_FEATURE_ID)) {
      session.invalidate();
      modelAndView.setViewName(INVALID_REQUEST_PAGE);
      return modelAndView;
    }
    modelAndView.addObject(Constants.ERROR, null);
    session.setAttribute(Constants.ERROR, null);
    session.setAttribute(Constants.MARKUP_FEE_MODEL, dccMarkup);
    dccMarkup.setPageSize(Constants.MAX_ENTITIES_PORTAL_DISPLAY_SIZE);
    dccMarkup.setPageIndex(Constants.ONE);
    String succMsg = request.getParameter("succMsg");
    try {
      MerchantNameResponse merchantNames = dccMarkupService.getActiveMerchantsCode();
      model.put(Constants.MERCHANT_NAMES_LIST, merchantNames.getMerchantNames());
      DCCMarkupResponse dccMarkupResponse = dccMarkupService.searchMarkupFee(dccMarkup);
      if (dccMarkupResponse != null) {
        List<DCCMarkup> dccMarkupList = dccMarkupResponse.getDccmarkUps();
        modelAndView =
            PaginationUtil.getPagenationModel(modelAndView, dccMarkupResponse.getTotalNoOfRows());
        modelAndView.addObject(Constants.MARKUP_FEE_MODEL, dccMarkupList);
      }
      modelAndView.addObject(Constants.SUCESS, succMsg);
    } catch (Exception e) {
      modelAndView.addObject(Constants.ERROR, messageSource
          .getMessage(Constants.CHATAK_GENERAL_ERROR, null, LocaleContextHolder.getLocale()));
      logger.error("ERROR:: DCCMarkupController:: showSearchMarkupFeePage method", e);
    }
    model.put(Constants.DCC_MARKUP, dccMarkup);
    logger.info("Exiting:: DCCMarkupController:: showSearchMarkupFeePage method");
    return modelAndView;
  }

  @RequestMapping(value = CHATAK_ADMIN_SEARCH_MARKUP_FEE, method = RequestMethod.POST)
  public ModelAndView searchMarkupFee(HttpServletRequest request, HttpServletResponse response,
      DCCMarkup dccMarkup, BindingResult bindingResult, Map model, HttpSession session) {
    logger.info("Entering:: DccMarkupController:: searchMarkupFee method");
    ModelAndView modelAndView = new ModelAndView(CHATAK_ADMIN_SEARCH_MARKUP_FEE_PAGE);
    String existingFeature = (String) session.getAttribute(Constants.EXISTING_FEATURES);
    if (!existingFeature.contains(FeatureConstants.ADMIN_SERVICE_DCC_MARKUP_FEATURE_ID)) {
      session.invalidate();
      modelAndView.setViewName(INVALID_REQUEST_PAGE);
      return modelAndView;
    }
    modelAndView.addObject(Constants.ERROR, null);
    session.setAttribute(Constants.ERROR, null);
    dccMarkup.setPageSize(Constants.MAX_ENTITIES_PORTAL_DISPLAY_SIZE);
    dccMarkup.setPageIndex(Constants.ONE);
    session.setAttribute(Constants.MARKUP_FEE_MODEL, dccMarkup);
    try {
      MerchantNameResponse merchantNames = dccMarkupService.getActiveMerchantsCode();
      model.put(Constants.MERCHANT_NAMES_LIST, merchantNames.getMerchantNames());
      DCCMarkupResponse dccMarkupResponse = dccMarkupService.searchMarkupFee(dccMarkup);
      if (dccMarkupResponse != null) {
        List<DCCMarkup> dccMarkupList = dccMarkupResponse.getDccmarkUps();
        modelAndView =
            PaginationUtil.getPagenationModel(modelAndView, dccMarkupResponse.getTotalNoOfRows());
        modelAndView.addObject(Constants.MARKUP_FEE_MODEL, dccMarkupList);
      }

    } catch (Exception e) {
      modelAndView.addObject(Constants.ERROR, messageSource
          .getMessage(Constants.CHATAK_GENERAL_ERROR, null, LocaleContextHolder.getLocale()));
      logger.error("ERROR:: DCCMarkupController:: searchMarkupFee method", e);
    }
    model.put(Constants.DCC_MARKUP, dccMarkup);
    logger.info("Exiting:: DCCMarkupController:: searchMarkupFee method");
    return modelAndView;
  }

  @RequestMapping(value = CHATAK_ADMIN_EDIT_DCC_MARKUP, method = RequestMethod.POST)
  public ModelAndView showEditDCCMarkUp(HttpServletRequest request, HttpServletResponse response,
      @FormParam("getMerchantName") final String getMerchantName, DCCMarkup dccMarkup,
      BindingResult bindingResult, HttpSession session, Map model) {
    logger.info("Entering :: DCCMarkupController :: showEditDccMarkUp method ");
    String getMerchantCode = request.getParameter("getMerchantCode");
    ModelAndView modelAndView = new ModelAndView(CHATAK_ADMIN_EDIT_DCC_MARKUP_PAGE);
    String existingFeature = (String) session.getAttribute(Constants.EXISTING_FEATURES);
    if (!existingFeature.contains(FeatureConstants.ADMIN_SERVICE_DCC_MARKUP_EDIT_FEATURE_ID)) {
      session.invalidate();
      modelAndView.setViewName(INVALID_REQUEST_PAGE);
      return modelAndView;
    }
    DCCMarkupResponse dccResponse = new DCCMarkupResponse();
    modelAndView.addObject(Constants.ERROR, null);
    modelAndView.addObject(Constants.SUCESS, null);
    try {
      dccResponse = dccMarkupService.getDccMarkup(getMerchantCode);
      if (dccResponse != null) {
        List<DCCMarkup> dccMarkupData = dccResponse.getDccmarkUps();
        model.put("dccMarkupList", dccMarkupData);
      }

    } catch (Exception e) {
      modelAndView.addObject(Constants.ERROR, messageSource
          .getMessage(Constants.CHATAK_GENERAL_ERROR, null, LocaleContextHolder.getLocale()));
      logger.error("ERROR:: DCCMarkupController :: showEditDccMarkUp method", e);
    }
    model.put("merchantName", getMerchantName);
    model.put(Constants.MERCHANT_CODE, getMerchantCode);
    logger.info("EXITING :: DCCMarkupController :: showEditDccMarkUp");
    return modelAndView;
  }

  @RequestMapping(value = CHATAK_ADMIN_UPDATE_PROCESS, method = RequestMethod.POST)
  public @ResponseBody String updateProcessMarkup(HttpServletRequest request, Map model,
      HttpServletResponse httpServletResponse) {
    logger.info("Entering:: DCCMarkupController:: updateProcessMarkup method");
    DCCMarkup dccMarkup = new DCCMarkup();
    String merchantCode = request.getParameter(Constants.MERCHANT_CODE);
    String arrayList = request.getParameter("arrayData");
    try {
      dccMarkup.setMerchantCode(merchantCode);
      ObjectMapper mapper = new ObjectMapper();
      List<DCCCurrency> currencyDatas = mapper.readValue(arrayList,
          new TypeReference<List<DCCCurrency>>() {});
      dccMarkup.setDccCurrency(currencyDatas);
      Response dccResponse = dccMarkupService.updateDCCMarkup(dccMarkup);
      if (dccResponse != null) {
        return JsonUtil.convertObjectToJSON(dccResponse);
      }
    } catch (Exception e) {
      logger.error("ERROR:: DCCMarkupController:: updateProcessMarkup method", e);
    }
    return null;
  }


  @RequestMapping(value = CHATAK_ADMIN_DCC_MARKUP_DELETE, method = RequestMethod.POST)
  public ModelAndView deleteDCCMarkup(HttpServletRequest request, HttpServletResponse response,
      @FormParam("merchantCodeId") final String merchantCodeId, DCCMarkup dccMarkup,
      BindingResult bindingResult, HttpSession session, Map model) {
    logger.info("Entering :: DCCMarkupController :: deleteDCCMarkup method ");
    ModelAndView modelAndView = new ModelAndView(CHATAK_ADMIN_SEARCH_MARKUP_FEE_PAGE);
    String existingFeature = (String) session.getAttribute(Constants.EXISTING_FEATURES);
    if (!existingFeature.contains(FeatureConstants.ADMIN_SERVICE_DCC_MARKUP_DELETE_FEATURE_ID)) {
      session.invalidate();
      modelAndView.setViewName(INVALID_REQUEST_PAGE);
      return modelAndView;
    }
    modelAndView.addObject(Constants.ERROR, null);
    modelAndView.addObject(Constants.SUCESS, null);
    try {
      Response dccResponse = dccMarkupService.deleteDCCMarkup(merchantCodeId);
      if (dccResponse != null && dccResponse.getErrorCode().equals(Constants.SUCCESS_CODE)) {
        modelAndView =
            showSearchMarkupFeePage(request, response, dccMarkup, bindingResult, model, session);
        modelAndView.addObject(Constants.SUCESS, messageSource.getMessage(
            "chatak.merchant.dcc.markup.success", null, LocaleContextHolder.getLocale()));
      } else {
        modelAndView =
            showSearchMarkupFeePage(request, response, dccMarkup, bindingResult, model, session);
        modelAndView.addObject(Constants.ERROR, messageSource.getMessage(
            "chatak.merchant.dcc.markup.failure", null, LocaleContextHolder.getLocale()));
      }

    } catch (Exception e) {
      modelAndView.addObject(Constants.ERROR, messageSource
          .getMessage(Constants.CHATAK_GENERAL_ERROR, null, LocaleContextHolder.getLocale()));
      logger.error("ERROR:: DCCMarkupController :: deleteDCCMarkup method", e);
    }
    logger.info("EXITING :: DCCMarkupController :: deleteDCCMarkup");
    return modelAndView;
  }

}
