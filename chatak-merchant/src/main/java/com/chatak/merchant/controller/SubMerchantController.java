
package com.chatak.merchant.controller;

import java.io.Serializable;
import java.text.NumberFormat;
import java.util.ArrayList;
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
import com.chatak.merchant.controller.model.Option;
import com.chatak.merchant.exception.ChatakMerchantException;
import com.chatak.merchant.model.MerchantData;
import com.chatak.merchant.model.MerchantSearchResponse;
import com.chatak.merchant.service.BatchSchedularReportService;
import com.chatak.merchant.service.CurrencyConfigService;
import com.chatak.merchant.service.MerchantInfoService;
import com.chatak.merchant.service.MerchantService;
import com.chatak.merchant.util.JsonUtil;
import com.chatak.merchant.util.PaginationUtil;
import com.chatak.merchant.util.StringUtil;
import com.chatak.pg.acq.dao.model.PGMerchant;
import com.chatak.pg.bean.Response;
import com.chatak.pg.constants.ActionErrorCode;
import com.chatak.pg.constants.PGConstants;
import com.chatak.pg.model.Merchant;
import com.chatak.pg.user.bean.AddMerchantResponse;
import com.chatak.pg.user.bean.DeleteMerchantResponse;
import com.chatak.pg.user.bean.UpdateMerchantResponse;
import com.chatak.pg.util.Constants;
import com.chatak.pg.util.Properties;

/**
 * << Class to create sub merchant activities >>
 *
 * @author Girmiti Software
 * @date Jul 2, 2015 10:33:36 AM
 * @version 1.0
 */
@Controller
@SuppressWarnings({"rawtypes", "unchecked"})
public class SubMerchantController implements URLMappingConstants {

  private static Logger logger = Logger.getLogger(SubMerchantController.class);

  @Autowired
  private MessageSource messageSource;

  @Autowired
  MerchantService merchantService;

  @Autowired
  CurrencyConfigService currencyConfigService;

  @Autowired
  MerchantInfoService merchantInfoService;
  
  @Autowired
  BatchSchedularReportService batchSchedularReportService;

  /**
   * Method to create merchant page
   * 
   * @param model
   * @param session
   * @return
   */
  @RequestMapping(value = CHATAK_PROCESS_SUMBERCHANT_CREATION, method = RequestMethod.POST)
  public ModelAndView createMerchant(HttpServletRequest request, HttpServletResponse response,
      Merchant merchant, BindingResult bindingResult, Map model, HttpSession session) {
    logger.info("Entering:: SubMerchantController:: createMerchant method");
    ModelAndView modelAndView = new ModelAndView(CHATAK_SHOW_SUB_MERCHANT_SEARCH_PAGE);
    String existingFeature = (String) session.getAttribute(Constants.EXISTING_FEATURES);
    if (!existingFeature
        .contains(FeatureConstants.MERCHANT_SERVICE_SUB_MERCHANT_CREATE_FEATURE_ID)) {
      session.invalidate();
      modelAndView.setViewName(INVALID_REQUEST_PAGE);
      return modelAndView;
    }
    modelAndView.addObject(Constants.ERROR, null);

    try {
      Long userid = (Long) session.getAttribute(Constants.LOGIN_USER_MERCHANT_ID);
      merchant.setParentMerchantId(userid);
      model.put(Constants.MERCHANT, merchant);
      String url = request.getRequestURL().toString();
      String uri = request.getRequestURI();
      String merchantLink = url.substring(0, url.length() - uri.length()) + "/"
          + Properties.getProperty("chatak.merchant.portal");
      merchant.setMerchantLink(merchantLink);

      // Check if currency is being prepended to the amount.
      if (merchant.getLegalAnnualCard().startsWith(PGConstants.DOLLAR_SYMBOL)) {
        merchant.setLegalAnnualCard(merchant.getLegalAnnualCard().substring(1));
      } else {
        merchant.setLegalAnnualCard(merchant.getLegalAnnualCard());
      }

      /* New code added for BanKcurrency update */
      String currencyCodeAlpha = merchant.getCurrencyId();
      Response currencyCodes = currencyConfigService.getCurrencyCodeNumeric(currencyCodeAlpha);
      merchant.setBankCurrencyCode(currencyCodes.getCurrencyCodeNumeric());

      if (StringUtil.isNullAndEmpty(merchant.getAgentId())) {
        merchant.setIssuancePartnerId(null);
        merchant.setProgramManagerId(null);
      }
      merchant.setCategory(PGConstants.PRIMARY_ACCOUNT);
      merchant.setStatus(PGConstants.STATUS_ACTIVE);
      AddMerchantResponse addMerchantResponse =
          merchantService.addMerchant(merchant, String.valueOf(userid));
      if (null != addMerchantResponse
          && addMerchantResponse.getErrorCode().equals(ActionErrorCode.ERROR_CODE_00)) {
    	  merchant = new Merchant();
        new Merchant();
        modelAndView =
            showSearchMerchantPage(request, response, merchant, bindingResult, model, session);
        modelAndView.addObject(Constants.SUCESS, messageSource
            .getMessage("chatak.merchant.create.success", null, LocaleContextHolder.getLocale()));
      } else {
        modelAndView = showCreateSubMerchantPage(model, session);
        modelAndView.addObject(Constants.ERROR, messageSource.getMessage(
            ActionErrorCode.ERROR_CODE_DUPLICATE_ENTRY, null, LocaleContextHolder.getLocale()));
      }
    } catch (Exception e) {
      logger.error("ERROR:: SubMerchantController:: createMerchant method", e);
      modelAndView = showCreateSubMerchantPage(model, session);
      modelAndView.addObject(Constants.ERROR,
          messageSource.getMessage(Constants.CHATAK_GENERAL_ERROR, null, LocaleContextHolder.getLocale()));
    }

    logger.info("Exiting:: SubMerchantController:: createMerchant method");
    return modelAndView;
  }

  /**
   * Method to show search merchant page
   * 
   * @param model
   * @param session
   * @return
   */
  /* */
  @RequestMapping(value = CHATAK_SHOW_SUB_MERCHANT_SEARCH_PAGE, method = RequestMethod.GET)
  public ModelAndView showSearchMerchantPage(HttpServletRequest request,
      HttpServletResponse response, Merchant merchant, BindingResult bindingResult, Map model,
      HttpSession session) {
    logger.info("Entering:: SubMerchantController:: showSearchMerchantPage method");

    ModelAndView modelAndView = new ModelAndView(CHATAK_SHOW_SUB_MERCHANT_SEARCH_PAGE);
    String existingFeature = (String) session.getAttribute(Constants.EXISTING_FEATURES);
    if (!existingFeature.contains(FeatureConstants.MERCHANT_SERVICE_SUB_MERCHANT_FEATURE_ID)) {
      session.invalidate();
      modelAndView.setViewName(INVALID_REQUEST_PAGE);
      return modelAndView;
    }
    Long merchantId = (Long) session.getAttribute(Constants.LOGIN_USER_MERCHANT_ID);
    Long parentId = (Long) session.getAttribute(Constants.LOGIN_USER_PARENT_ID);
    if (null != parentId) {
      modelAndView.addObject(Constants.ERROR, messageSource
          .getMessage("chatak.submerchant.creation.error", null, LocaleContextHolder.getLocale()));
      modelAndView.setViewName(SUBMERCHANT_ERROR_PAGE);
      return modelAndView;
    }
    merchant.setParentMerchantId(merchantId);
    modelAndView.addObject(Constants.ERROR, null);
    session.setAttribute(Constants.ERROR, null);
    session.setAttribute(Constants.MERCHANTS_MODEL, merchant);
    merchant.setPageSize(Constants.MAX_ENTITIES_PAGINATION_DISPLAY_SIZE);
    merchant.setPageIndex(Constants.ONE);
    try {
      List<Option> countryList = merchantInfoService.getCountries();
      modelAndView.addObject(Constants.COUNTRY_LIST, countryList);
      session.setAttribute(Constants.COUNTRY_LIST, countryList);
      MerchantSearchResponse searchResponse = merchantInfoService.searchSubMerchantList(merchant);
      List<MerchantData> merchants = new ArrayList<>();
      if (searchResponse != null && !CollectionUtils.isEmpty(searchResponse.getMerchants())) {
        merchants = searchResponse.getMerchants();
        modelAndView.addObject(Constants.PAGE_SIZE, merchant.getPageSize());
        modelAndView =
            PaginationUtil.getPagenationModel(modelAndView, searchResponse.getTotalNoOfRows());
      }
      modelAndView.addObject(Constants.MERCHANTS_MODEL, merchants);
    } catch (Exception e) {
      modelAndView.addObject(Constants.ERROR,
          messageSource.getMessage(Constants.CHATAK_GENERAL_ERROR, null, LocaleContextHolder.getLocale()));
      logger.error("ERROR:: SubMerchantController:: showSearchMerchantPage method", e);
    }
    modelAndView.addObject("flag", false);
    modelAndView.addObject(Constants.MERCHANT, merchant);
    logger.info("Exiting:: SubMerchantController:: showSearchMerchantPage method");
    return modelAndView;
  }

  /**
   * Method to show search merchant
   * 
   * @param model
   * @param session
   * @return
   */
  @RequestMapping(value = CHATAK_SHOW_SUB_MERCHANT_SEARCH_PAGE, method = RequestMethod.POST)
  public ModelAndView searchMerchant(HttpServletRequest request, HttpServletResponse response,
      Merchant merchant, BindingResult bindingResult, Map model, HttpSession session) {
    logger.info("Entering:: SubMerchantController:: searchMerchant method");

    ModelAndView modelAndView = new ModelAndView(CHATAK_SHOW_SUB_MERCHANT_SEARCH_PAGE);
    String existingFeature = (String) session.getAttribute(Constants.EXISTING_FEATURES);
    if (!existingFeature.contains(FeatureConstants.MERCHANT_SERVICE_SUB_MERCHANT_FEATURE_ID)) {
      session.invalidate();
      modelAndView.setViewName(INVALID_REQUEST_PAGE);
      return modelAndView;
    }
    modelAndView.addObject(Constants.ERROR, null);
    session.setAttribute(Constants.ERROR, null);
    session.setAttribute(Constants.MERCHANTS_MODEL, merchant);
    merchant.setPageIndex(Constants.ONE);
    session.setAttribute(Constants.PAGE_NUMBER, Constants.ONE);
    MerchantSearchResponse searchResponse = null;
    try {
      Long merchantId = (Long) session.getAttribute(Constants.LOGIN_USER_MERCHANT_ID);
      merchant.setParentMerchantId(merchantId);
      if (null != merchant.getSubMerchantCode() && !"".equals(merchant.getSubMerchantCode())) {
        searchResponse = merchantService.searchMerchant(merchant);
      } else {
        searchResponse = merchantInfoService.searchSubMerchantList(merchant);
      }
      List<MerchantData> merchants = new ArrayList<>();
      if (searchResponse != null && !CollectionUtils.isEmpty(searchResponse.getMerchants())) {
        merchants = searchResponse.getMerchants();
        session.setAttribute(Constants.TOTAL_RECORDS, searchResponse.getTotalNoOfRows());
        modelAndView.addObject(Constants.PAGE_SIZE, merchant.getPageSize());
        modelAndView =
            PaginationUtil.getPagenationModel(modelAndView, searchResponse.getTotalNoOfRows());
      }
      modelAndView.addObject(Constants.MERCHANTS_MODEL, merchants);
    } catch (Exception e) {
      modelAndView.addObject(Constants.ERROR,
          messageSource.getMessage(Constants.CHATAK_GENERAL_ERROR, null, LocaleContextHolder.getLocale()));
      logger.error("ERROR:: SubMerchantController:: searchMerchant method", e);
    }
    modelAndView.addObject("flag", true);
    model.put(Constants.MERCHANT, merchant);
    logger.info("Exiting:: SubMerchantController:: searchMerchant method");
    return modelAndView;
  }

  @RequestMapping(value = CHATAK_DELETE_SUB_MERCHANT, method = RequestMethod.POST)
  public ModelAndView deleteMerchant(HttpServletRequest request, HttpServletResponse response,
      @FormParam("getMerchantsId") final Long getMerchantsId, Merchant merchant,
      BindingResult bindingResult, HttpSession session, Map model) {
    logger.info("Entering :: SubMerchantController :: deleteMerchant method ");
    ModelAndView modelAndView = new ModelAndView("");
    String existingFeature = (String) session.getAttribute(Constants.EXISTING_FEATURES);
    if (!existingFeature
        .contains(FeatureConstants.MERCHANT_SERVICE_SUB_MERCHANT_DELETE_FEATURE_ID)) {
      session.invalidate();
      modelAndView.setViewName(INVALID_REQUEST_PAGE);
      return modelAndView;
    }
    modelAndView.addObject(Constants.ERROR, null);
    modelAndView.addObject(Constants.SUCESS, null);
    try {
      merchant.setId(getMerchantsId);
      DeleteMerchantResponse deleteMerchantResponse =
          merchantInfoService.deleteMerchant(getMerchantsId);
      if (deleteMerchantResponse != null && deleteMerchantResponse.isIsdeleated()) {
        modelAndView =
            showSearchMerchantPage(request, response, merchant, bindingResult, model, session);
        modelAndView.addObject(Constants.SUCESS, messageSource
            .getMessage("chatak.merchant.delete.success", null, LocaleContextHolder.getLocale()));
      } else {
        modelAndView =
            showSearchMerchantPage(request, response, merchant, bindingResult, model, session);
        modelAndView.addObject(Constants.ERROR, messageSource
            .getMessage("chatak.merchant.delete.failure", null, LocaleContextHolder.getLocale()));
      }

    } catch (Exception e) {
      modelAndView.addObject(Constants.ERROR,
          messageSource.getMessage(Constants.CHATAK_GENERAL_ERROR, null, LocaleContextHolder.getLocale()));
      logger.error("ERROR:: SubMerchantController:: deleteMerchant method", e);
    }

    modelAndView.addObject(Constants.MERCHANT, merchant);
    logger.info("EXITING :: SubMerchantController :: deleteMerchant");
    return modelAndView;
  }

  /**
   * <<Method to create sub merchant >>
   * 
   * @param model
   * @param session
   * @return
   */
  @RequestMapping(value = CHATAK_SHOW_SUB_MERCHANT_CREATION_PAGE, method = RequestMethod.GET)
  public ModelAndView showCreateSubMerchantPage(Map model, HttpSession session) {
    logger.info("Entering:: SubMerchantController:: showCreateMerchantPage method");
    ModelAndView modelAndView = new ModelAndView(CHATAK_SHOW_SUB_MERCHANT_CREATION_PAGE);
    String existingFeature = (String) session.getAttribute(Constants.EXISTING_FEATURES);
    if (!existingFeature
        .contains(FeatureConstants.MERCHANT_SERVICE_SUB_MERCHANT_CREATE_FEATURE_ID)) {
      session.invalidate();
      modelAndView.setViewName(INVALID_REQUEST_PAGE);
      return modelAndView;
    }
    modelAndView.addObject(Constants.ERROR, null);
    session.setAttribute(Constants.ERROR, null);
    Merchant subMerchant = new Merchant();
    List<Option> processorNames = null;
    try {
      List<Option> options = merchantInfoService.getFeeProgramNames();
      modelAndView.addObject(Constants.FEE_PROGRAM_NAMES, options);
      session.setAttribute(Constants.FEE_PROGRAM_NAMES, options);
      session.setAttribute(Constants.UPDATE_MERCHANT_ID, session.getAttribute(Constants.UPDATE_MERCHANT_ID));
      List<Option> countryList = merchantInfoService.getCountries();
      modelAndView.addObject(Constants.COUNTRY_LIST, countryList);
      session.setAttribute(Constants.COUNTRY_LIST, countryList);
      subMerchant.setMerchantType(PGConstants.SUB_MERCHANT);
      Long parentMerchantId = (Long) session.getAttribute(Constants.LOGIN_USER_MERCHANT_ID);
      subMerchant.setParentMerchantId(parentMerchantId);
      MerchantData merchantData = batchSchedularReportService.getMerchantCodeAndCompanyName(parentMerchantId);
      List<Option> mainMerchantList = new ArrayList<>();
      Option option = new Option();
      if (merchantData != null) {
        option.setValue(String.valueOf(merchantData.getId()));
        option.setLabel(merchantData.getMerchantCode() + " - " + merchantData.getBusinessName());
        mainMerchantList.add(option);
        modelAndView.addObject("mainMerchantList", mainMerchantList);
      }
      processorNames = merchantInfoService.getProcessorNames();
      String issuancePartnerId = merchantInfoService.getPartnerLinkedToMerchant(parentMerchantId);
      session.setAttribute("issuancePartnerId", issuancePartnerId);

      /*To check is email uniqueness is allowed or not*/
      String isUserMailUnique = Properties.getProperty("prepaid.email.unique.enable");
      model.put("isUserMailUnique", isUserMailUnique);

      subMerchant.setCategory(PGConstants.PRIMARY_ACCOUNT);

      subMerchant.setIssuancePartnerId(issuancePartnerId);
      model.put("linkedPartnerId", issuancePartnerId);

      PGMerchant pgMerchant = merchantInfoService.getMerchantOnId(parentMerchantId);
      String bankCurrencyAlpha = pgMerchant.getLocalCurrency();
      subMerchant.setCurrencyId(bankCurrencyAlpha);

    } catch (ChatakMerchantException e) {
      modelAndView.addObject(Constants.ERROR,
          messageSource.getMessage(Constants.CHATAK_GENERAL_ERROR, null, LocaleContextHolder.getLocale()));
      logger.error("ERROR:: SubMerchantController:: showCreateMerchantPage method", e);
    }
    modelAndView.addObject(Constants.PROCESSOR_NAMES, processorNames);
    model.put("subMerchant", subMerchant);
    logger.info("Exiting:: SubMerchantController:: showCreateMerchantPage method");
    return modelAndView;
  }

  /**
   * << Method to create sub merchant >>
   * 
   * @param request
   * @param response
   * @param subMerchant
   * @param bindingResult
   * @param model
   * @param session
   * @return
   */

  @RequestMapping(value = CHATAK_PROCESS_SUB_MERCHANT_UPDATE_PAGE, method = RequestMethod.POST)
  public ModelAndView updateSubMerchant(HttpServletRequest request, HttpServletResponse response,
      Merchant merchant, BindingResult bindingResult, HttpSession session, Map model) {
    logger.info("Entering :: SubMerchantController :: updateSubMerchant method");

    ModelAndView modelAndView = new ModelAndView(CHATAK_SHOW_SUB_MERCHANT_SEARCH_PAGE);
    String existingFeature = (String) session.getAttribute(Constants.EXISTING_FEATURES);
    if (!existingFeature.contains(FeatureConstants.MERCHANT_SERVICE_SUB_MERCHANT_FEATURE_ID)) {
      session.invalidate();
      modelAndView.setViewName(INVALID_REQUEST_PAGE);
      return modelAndView;
    }
    modelAndView.addObject(Constants.ERROR, null);
    session.setAttribute(Constants.ERROR, null);
    merchant.setPageSize(Constants.MAX_ENTITIES_PORTAL_DISPLAY_SIZE);
    merchant.setPageIndex(Constants.ONE);
    try {
      // Check if currency is being prepended to the amount.
      if (merchant.getLegalAnnualCard().startsWith(PGConstants.DOLLAR_SYMBOL)) {
        merchant.setLegalAnnualCard(merchant.getLegalAnnualCard().substring(1));
      } else {
        merchant.setLegalAnnualCard(merchant.getLegalAnnualCard());
      }

      NumberFormat format = NumberFormat.getInstance(LocaleContextHolder.getLocale());
      Number number = format.parse(merchant.getLegalAnnualCard());
      double d = number.doubleValue();
      merchant.setLegalAnnualCard(Double.toString(d));

      /* New code added for BanKcurrency update */
      String currencyCodeAlpha = merchant.getCurrencyId();
      Response currencyCodes = currencyConfigService.getCurrencyCodeNumeric(currencyCodeAlpha);
      merchant.setBankCurrencyCode(currencyCodes.getCurrencyCodeNumeric());

      if (StringUtil.isNullAndEmpty(merchant.getAgentId())) {
        merchant.setIssuancePartnerId(null);
        merchant.setProgramManagerId(null);
      }

      merchant.setCategory(PGConstants.PRIMARY_ACCOUNT);

      UpdateMerchantResponse updateMerchantResponse = merchantService.updateMerchant(merchant);
      if (updateMerchantResponse.isUpdated()
          && updateMerchantResponse.getErrorCode().equals(ActionErrorCode.ERROR_CODE_00)) {
        Merchant merchantData = new Merchant();
        modelAndView =
            showSearchMerchantPage(request, response, merchantData, bindingResult, model, session);
        modelAndView.addObject(Constants.SUCESS, messageSource
            .getMessage("chatak.merchant.update.success", null, LocaleContextHolder.getLocale()));
        modelAndView.addObject(Constants.MERCHANT, merchantData);

      } else {
        modelAndView = showEditSubMerchant(request, response, merchant.getId(), session, model);
        modelAndView.setViewName("");
        modelAndView.addObject(Constants.MERCHANT, merchant);
        modelAndView.addObject(Constants.ERROR, messageSource
            .getMessage(ActionErrorCode.ERROR_CODE_Z5, null, LocaleContextHolder.getLocale()));
      }
    } catch (Exception e) {
      logger.error("ERROR:: SubMerchantController:: updateSubMerchant method", e);
      modelAndView.addObject(Constants.ERROR,
          messageSource.getMessage(Constants.CHATAK_GENERAL_ERROR, null, LocaleContextHolder.getLocale()));
    }
    logger.info("Exiting:: SubMerchantController:: updateSubMerchant method");
    return modelAndView;
  }

  @RequestMapping(value = CHATAK_SHOW_SUB_MERCHANT_UPDATE_PAGE, method = RequestMethod.POST)
  public ModelAndView showEditSubMerchant(HttpServletRequest request, HttpServletResponse response,
      @FormParam("getSubMerchantId") final Long getSubMerchantId, HttpSession session, Map model) {
    logger.info("Entering :: SubMerchantController :: showEditSubMerchant method ");
    ModelAndView modelAndView = new ModelAndView(CHATAK_PROCESS_SUB_MERCHANT_UPDATE_PAGE);
    String existingFeature = (String) session.getAttribute(Constants.EXISTING_FEATURES);
    if (!existingFeature.contains(FeatureConstants.MERCHANT_SERVICE_SUB_MERCHANT_FEATURE_ID)) {
      session.invalidate();
      modelAndView.setViewName(INVALID_REQUEST_PAGE);
      return modelAndView;
    }
    Merchant merchant = new Merchant();

    modelAndView.addObject(Constants.ERROR, null);
    modelAndView.addObject(Constants.SUCESS, null);
    List<Option> processorNames = null;
    try {

      List<Option> countryList = merchantInfoService.getCountries();
      modelAndView.addObject(Constants.COUNTRY_LIST, countryList);
      session.setAttribute(Constants.COUNTRY_LIST, countryList);
      merchant.setId(getSubMerchantId);
      merchant = merchantService.getMerchant(merchant);

      if (null == merchant)
        modelAndView.addObject(Constants.ERROR, messageSource.getMessage(Constants.CHATAK_GENERAL_ERROR,
            null, LocaleContextHolder.getLocale()));
      else {

        List<Option> options =
            merchantInfoService.getFeeProgramNamesForEdit(merchant.getFeeProgram());
        modelAndView.addObject(Constants.FEE_PROGRAM_NAMES, options);
        session.setAttribute(Constants.FEE_PROGRAM_NAMES, options);

        Response agentnamesList = merchantInfoService.getAgentNames(merchant.getLocalCurrency());
        if (agentnamesList != null) {
          modelAndView.addObject(Constants.AGENT_NAMES_LIST, agentnamesList.getResponseList());
          session.setAttribute(Constants.AGENT_NAMES_LIST, agentnamesList.getResponseList());
        }

        Response stateList = merchantInfoService.getStatesByCountry(merchant.getCountry());
        modelAndView.addObject(Constants.STATE_LIST, stateList.getResponseList());
        session.setAttribute(Constants.STATE_LIST, stateList.getResponseList());
        stateList = merchantInfoService.getStatesByCountry(merchant.getBankCountry());
        modelAndView.addObject(Constants.BANK_STATE_LIST, stateList.getResponseList());
        session.setAttribute(Constants.BANK_STATE_LIST, stateList.getResponseList());
        stateList = merchantInfoService.getStatesByCountry(merchant.getLegalCountry());
        modelAndView.addObject(Constants.LEGAL_STATE_LIST, stateList.getResponseList());
        session.setAttribute(Constants.LEGAL_STATE_LIST, stateList.getResponseList());
        session.setAttribute(Constants.UPDATE_MERCHANT_ID, getSubMerchantId);
        modelAndView.addObject(Constants.MERCHANT, merchant);
        merchantInfoService.getProcessorNames();
        Long parentMerchantId = (Long) session.getAttribute(Constants.LOGIN_USER_MERCHANT_ID);
        PGMerchant pgMerchant = merchantInfoService.getMerchantOnId(parentMerchantId);
        merchant.setParentMerchantName(pgMerchant.getBusinessName());
        processorNames = merchantInfoService.getProcessorNames();
        String issuancePartnerId = merchantInfoService.getPartnerLinkedToMerchant(parentMerchantId);
        session.setAttribute("issuancePartnerId", issuancePartnerId);
        merchant.setIssuancePartnerId(issuancePartnerId);
        model.put("linkedPartnerId", issuancePartnerId);

        modelAndView.addObject("accountNumber", merchant.getAccountId());
        session.setAttribute("accountNumber", merchant.getAccountId());
      }
    } catch (ChatakMerchantException e) {
      modelAndView.addObject(Constants.ERROR,
          messageSource.getMessage(Constants.CHATAK_GENERAL_ERROR, null, LocaleContextHolder.getLocale()));
      logger.error("ERROR:: SubMerchantController:: showEditSubMerchant method", e);
    }
    modelAndView.addObject(Constants.PROCESSOR_NAMES, processorNames);
    model.put(Constants.MERCHANT, merchant);
    logger.info("EXITING :: SubMerchantController :: showEditSubMerchant");
    return modelAndView;
  }

  @RequestMapping(value = CHATAK_VIEW_SUB_MERCHANT_UPDATE_PAGE, method = RequestMethod.POST)
  public ModelAndView showViewSubMerchant(HttpServletRequest request, HttpServletResponse response,
      @FormParam("getSubMerchantIdForView") final Long getSubMerchantIdForView, HttpSession session,
      Map model) {
    logger.info("Entering :: SubMerchantController :: showEditSubMerchant method ");
    ModelAndView modelAndView = new ModelAndView(CHATAK_VIEW_SUB_MERCHANT_PAGE);
    String existingFeature = (String) session.getAttribute(Constants.EXISTING_FEATURES);
    if (!existingFeature.contains(FeatureConstants.MERCHANT_SERVICE_SUB_MERCHANT_FEATURE_ID)) {
      session.invalidate();
      modelAndView.setViewName(INVALID_REQUEST_PAGE);
      return modelAndView;
    }
    modelAndView.addObject(Constants.ERROR, null);
    modelAndView.addObject(Constants.SUCESS, null);
    Merchant merchant = new Merchant();
    List<Option> processorNames = null;
    try {

      List<Option> countryList = merchantInfoService.getCountries();
      modelAndView.addObject(Constants.COUNTRY_LIST, countryList);
      session.setAttribute(Constants.COUNTRY_LIST, countryList);
      merchant.setId(getSubMerchantIdForView);
      merchant = merchantService.getMerchant(merchant);
      if (null == merchant)
        modelAndView.addObject(Constants.ERROR, messageSource.getMessage(Constants.CHATAK_GENERAL_ERROR,
            null, LocaleContextHolder.getLocale()));
      else {

        List<Option> options =
            merchantInfoService.getFeeProgramNamesForEdit(merchant.getFeeProgram());
        modelAndView.addObject(Constants.FEE_PROGRAM_NAMES, options);
        session.setAttribute(Constants.FEE_PROGRAM_NAMES, options);

        Response stateList = merchantInfoService.getStatesByCountry(merchant.getCountry());
        modelAndView.addObject(Constants.STATE_LIST, stateList.getResponseList());
        session.setAttribute(Constants.STATE_LIST, stateList.getResponseList());
        stateList = merchantInfoService.getStatesByCountry(merchant.getBankCountry());
        modelAndView.addObject(Constants.BANK_STATE_LIST, stateList.getResponseList());
        session.setAttribute(Constants.BANK_STATE_LIST, stateList.getResponseList());
        stateList = merchantInfoService.getStatesByCountry(merchant.getLegalCountry());
        modelAndView.addObject(Constants.LEGAL_STATE_LIST, stateList.getResponseList());
        session.setAttribute(Constants.LEGAL_STATE_LIST, stateList.getResponseList());
        modelAndView.addObject(Constants.MERCHANT, merchant);
        processorNames = merchantInfoService.getProcessorNames();

        Response agentnamesList = merchantInfoService.getAgentNames(merchant.getLocalCurrency());
        if (agentnamesList != null) {
          modelAndView.addObject(Constants.AGENT_NAMES_LIST, agentnamesList.getResponseList());
          session.setAttribute(Constants.AGENT_NAMES_LIST, agentnamesList.getResponseList());
        }
      }
      Long parentMerchantId = (Long) session.getAttribute(Constants.LOGIN_USER_MERCHANT_ID);
      PGMerchant pgMerchant = merchantInfoService.getMerchantOnId(parentMerchantId);
      if(merchant != null){
        merchant.setParentMerchantName(pgMerchant.getBusinessName());
      }
    } catch (ChatakMerchantException e) {
      modelAndView.addObject(Constants.ERROR,
          messageSource.getMessage(Constants.CHATAK_GENERAL_ERROR, null, LocaleContextHolder.getLocale()));
      logger.error("ERROR:: SubMerchantController:: showEditSubMerchant method", e);
    }
    modelAndView.addObject(Constants.PROCESSOR_NAMES, processorNames);
    model.put(Constants.MERCHANT, merchant);
    logger.info("EXITING :: SubMerchantController :: showEditSubMerchant");
    return modelAndView;
  }

  @RequestMapping(value = CHATAK_ADMIN_SUB_MERCHANT_STATUS_CHANGE, method = RequestMethod.POST)
  public ModelAndView changeSubMerchantStatus(final HttpSession session,
      @FormParam("suspendActiveId") final Long subMerchantId,
      @FormParam("suspendActiveStatus") final String subMerchantStatus,
      @FormParam("reason") final String reason, Map<String, Serializable> model,
      HttpServletRequest request, HttpServletResponse response) {
    logger.info("Entering:: MerchantController:: changeSubMerchantStatus method");

    ModelAndView modelAndView = new ModelAndView(CHATAK_SHOW_SUB_MERCHANT_SEARCH_PAGE);
    String existingFeature = (String) session.getAttribute(Constants.EXISTING_FEATURES);
    if (!existingFeature.contains(FeatureConstants.MERCHANT_SERVICE_SUB_MERCHANT_FEATURE_ID)) {
      session.invalidate();
      modelAndView.setViewName(INVALID_REQUEST_PAGE);
      return modelAndView;
    }
    try {
      MerchantData merchantData = new MerchantData();
      merchantData.setId(subMerchantId);
      merchantData.setStatus(subMerchantStatus);
      merchantData.setReason(reason);
      Response merchantResponse = merchantInfoService.changeMerchantStatus(merchantData);
      modelAndView = getPaginationList(session,
          StringUtil.isNull((Integer) session.getAttribute(Constants.PAGE_NUMBER)) ? 1
              : (Integer) session.getAttribute(Constants.PAGE_NUMBER),
          (Integer) session.getAttribute(Constants.TOTAL_RECORDS), model);
      if (ActionErrorCode.ERROR_CODE_00.equals(merchantResponse.getErrorCode())) {
        model.put(Constants.SUCESS, merchantResponse.getErrorMessage());
      } else {
        model.put(Constants.ERROR, messageSource.getMessage(Constants.CHATAK_GENERAL_ERROR, null,
            LocaleContextHolder.getLocale()));
      }
    } catch (Exception e) {
      logger.error("ERROR:: MerchantController:: changeSubMerchantStatus method", e);
      model.put(Constants.ERROR,
          messageSource.getMessage(Constants.CHATAK_GENERAL_ERROR, null, LocaleContextHolder.getLocale()));
    }
    logger.info("Exiting:: MerchantController:: changeSubMerchantStatus method");
    return modelAndView;
  }

  /**
   * Method used for Pagination Util
   * 
   * @param session
   * @param pageNumber
   * @param model
   * @return
   */
  @RequestMapping(value = CHATAK_SUB_MERCHANTS_PAGINATION, method = RequestMethod.POST)
  public ModelAndView getPaginationList(final HttpSession session,
      @FormParam(Constants.PAGE_NUMBER) final Integer pageNumber,
      @FormParam(Constants.TOTAL_RECORDS) final Integer totalRecords, Map model) {
    logger.info("Entering:: SubMerchantController:: getPaginationList method");
    ModelAndView modelAndView = new ModelAndView(CHATAK_SHOW_SUB_MERCHANT_SEARCH_PAGE);
    try {
      Merchant merchant = (Merchant) session.getAttribute(Constants.MERCHANTS_MODEL);
      model.put(Constants.MERCHANT, merchant);
      merchant.setPageIndex(pageNumber);
      merchant.setNoOfRecords(totalRecords);
      MerchantSearchResponse searchResponse = merchantInfoService.searchSubMerchantList(merchant);
      List<MerchantData> merchants = searchResponse.getMerchants();

      if (null != searchResponse && !CollectionUtils.isEmpty(merchants)) {
        modelAndView.addObject(Constants.PAGE_SIZE, merchant.getPageSize());
        modelAndView = PaginationUtil.getPagenationModelSuccessive(modelAndView, pageNumber,
            searchResponse.getTotalNoOfRows());
        session.setAttribute(Constants.PAGE_NUMBER, pageNumber);
        session.setAttribute(Constants.TOTAL_RECORDS, totalRecords);
        modelAndView.addObject(Constants.MERCHANTS_MODEL, merchants);
      }
    } catch (Exception e) {
      logger.error("ERROR:: SubMerchantController:: getPaginationList method", e);
      modelAndView.addObject(Constants.ERROR,
          Properties.getProperty("prepaid.admin.general.error.message"));
    }
    logger.info("Exiting:: SubMerchantController:: getPaginationList method");
    return modelAndView;
  }
  
  @RequestMapping(value = GET_PARTNER_NAME_BY_MERCHANT_CODE, method = RequestMethod.GET)
  public @ResponseBody String getPartnerName(Map model, HttpServletRequest request,
      HttpServletResponse response, HttpSession session) {
    ModelAndView modelAndView = new ModelAndView(CHATAK_SHOW_SUB_MERCHANT_CREATION_PAGE);
    modelAndView.addObject(Constants.ERROR, null);
    logger.info("Entering :: SubMerchantController :: getPartnerName method");
    String merchantId = request.getParameter("merchantId");
    try {
      Response responseVal = merchantService.findPartnerByMerchantCode(merchantId);

      if (null != responseVal) {
        return JsonUtil.convertObjectToJSON(responseVal);
      }
    } catch (ChatakMerchantException exp) {
      logger.error("ERROR:: SubMerchantController:: getPartnerName method", exp);
      modelAndView.addObject(Constants.ERROR, messageSource
          .getMessage(Constants.CHATAK_GENERAL_ERROR, null, LocaleContextHolder.getLocale()));
    }
    logger.info("Exiting:: SubMerchantController:: getPartnerName method");
    return null;
  }
}
