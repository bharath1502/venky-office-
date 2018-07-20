package com.chatak.acquirer.admin.controller;

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
import org.springframework.validation.DataBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.chatak.acquirer.admin.constants.FeatureConstants;
import com.chatak.acquirer.admin.constants.StatusConstants;
import com.chatak.acquirer.admin.constants.URLMappingConstants;
import com.chatak.acquirer.admin.controller.model.ExportDetails;
import com.chatak.acquirer.admin.controller.model.LoginResponse;
import com.chatak.acquirer.admin.controller.model.Option;
import com.chatak.acquirer.admin.exception.ChatakAdminException;
import com.chatak.acquirer.admin.model.MerchantCreateResponse;
import com.chatak.acquirer.admin.model.MerchantData;
import com.chatak.acquirer.admin.model.MerchantSearchResponse;
import com.chatak.acquirer.admin.service.BankService;
import com.chatak.acquirer.admin.service.CurrencyConfigService;
import com.chatak.acquirer.admin.service.IsoService;
import com.chatak.acquirer.admin.service.MerchantCategoryCodeService;
import com.chatak.acquirer.admin.service.MerchantService;
import com.chatak.acquirer.admin.service.MerchantUpdateService;
import com.chatak.acquirer.admin.service.MerchantValidateService;
import com.chatak.acquirer.admin.service.PartnerService;
import com.chatak.acquirer.admin.service.ProgramManagerService;
import com.chatak.acquirer.admin.util.ExportUtil;
import com.chatak.acquirer.admin.util.JsonUtil;
import com.chatak.acquirer.admin.util.PaginationUtil;
import com.chatak.acquirer.admin.util.StringUtil;
import com.chatak.pg.bean.Response;
import com.chatak.pg.constants.ActionErrorCode;
import com.chatak.pg.constants.PGConstants;
import com.chatak.pg.enums.ExportType;
import com.chatak.pg.model.AgentDTOResponse;
import com.chatak.pg.model.Bank;
import com.chatak.pg.model.Merchant;
import com.chatak.pg.user.bean.CardProgramResponse;
import com.chatak.pg.user.bean.UpdateMerchantResponse;
import com.chatak.pg.util.Constants;
import com.chatak.pg.util.LogHelper;
import com.chatak.pg.util.LoggerMessage;
import com.chatak.pg.util.Properties;

@SuppressWarnings({"rawtypes", "unchecked", "unused"})
@Controller
public class MerchantValidationController implements URLMappingConstants {

  private static Logger logger = Logger.getLogger(MerchantValidationController.class);

  @Autowired
  MessageSource messageSource;

  @Autowired
  private MerchantService merchantService;

  @Autowired
  private BankService bankService;

  @Autowired
  private MerchantCategoryCodeService merchantCategoryCodeService;

  @Autowired
  private CurrencyConfigService currencyConfigService;

  @Autowired
  private MerchantValidateService merchantValidateService;

  @Autowired
  private MerchantUpdateService merchantUpdateService;

  @Autowired
  private PartnerService partnerService;
  
  @Autowired
  private ProgramManagerService programManagerService;
  
  @Autowired
  private IsoService isoService;

  MerchantController merchantController = new MerchantController();

  @RequestMapping(value = CHATAK_ADMIN_UNIQUE_EMAIL_VALIDATE, method = RequestMethod.GET)
  public @ResponseBody String validateUniqueEmailId(Map model, HttpServletRequest request,
      HttpServletResponse response, HttpSession session) {
    logger.info("Entering :: MerchantValidationController :: validateUniqueEmailId Method");

    ModelAndView modelAndView = new ModelAndView(CHATAK_ADMIN_CREATE_MERCHANT_PAGE);
    modelAndView.addObject(Constants.ERROR, null);
    String emailId = request.getParameter("emailId");
    Response response2 = null;
    try {
      response2 = merchantValidateService.validateEmailId(emailId);
      return JsonUtil.convertObjectToJSON(response2);
    } catch (ChatakAdminException e) {
      logger.error("Error :: MerchantValidationController :: validateUniqueEmailId", e);
      response2 = setErrorCodeAndErrorMessage(modelAndView);
      try {
        return JsonUtil.convertObjectToJSON(response2);
      } catch (ChatakAdminException e1) {
        logger.error(
            "ERROR:: MerchantValidationController:: validateUniqueEmailId method:: convertObjectToJSON",
            e1);
      }
    } catch (Exception e) {
      logger.error("ERROR :: MerchantValidationController :: validateUniqueEmailId method", e);
      response2 = setErrorCodeAndErrorMessage(modelAndView);
      try {
        return JsonUtil.convertObjectToJSON(response2);
      } catch (ChatakAdminException e1) {
        logger.error(
            "ERROR:: MerchantValidationController:: validateUniqueEmailId method ::convertObjectToJSON",
            e1);
      }
    }
    logger.info("Exiting:: MerchantValidationController:: validateUniqueEmailId method");
    return null;
  }

  private Response setErrorCodeAndErrorMessage(ModelAndView modelAndView) {
	Response response2;
	response2 = new Response();
    response2.setErrorCode(ActionErrorCode.ERROR_CODE_Z5);
    response2
        .setErrorMessage(ActionErrorCode.getInstance().getMessage(ActionErrorCode.ERROR_CODE_Z5));
    modelAndView.addObject(Constants.ERROR,
        messageSource.getMessage(Constants.CHATAK_GENERAL_ERROR, null, LocaleContextHolder.getLocale()));
	return response2;
  }

  @RequestMapping(value = CHATAK_ADMIN_UNIQUE_USER_VALIDATE, method = RequestMethod.GET)
  public @ResponseBody String validateUniqueUserName(Map model, HttpServletRequest request,
      HttpServletResponse response, HttpSession session) {
    ModelAndView modelAndView = new ModelAndView(CHATAK_ADMIN_CREATE_MERCHANT_PAGE);
    modelAndView.addObject(Constants.ERROR, null);
    logger.info("Entering :: MerchantValidationController :: validateUniqueUserName method");
    String userName = request.getParameter("userName");
    Response response2 = null;
    try {
      response2 = merchantValidateService.validateUserName(userName);
      return JsonUtil.convertObjectToJSON(response2);
    } catch (ChatakAdminException e) {
      logger.error("ERROR:: MerchantValidationController:: validateUniqueUserName method", e);
      response2 = setErrorCodeAndErrorMessage(modelAndView);
      try {
        return JsonUtil.convertObjectToJSON(response2);
      } catch (ChatakAdminException e1) {
        logger.error(
            "ERROR:: MerchantValidationController:: validateUniqueUserName method :: convertObjectToJSON",
            e1);
      }
    } catch (Exception ex) {
      logger.error("ERROR:: MerchantValidationController:: validateUniqueUserName method", ex);
      response2 = setErrorCodeAndErrorMessage(modelAndView);
      try {
        return JsonUtil.convertObjectToJSON(response2);
      } catch (ChatakAdminException e1) {
        logger.error(
            "ERROR:: MerchantValidationController:: validateUniqueUserName method ::convertObjectToJSON",
            e1);
      }
    }
    logger.info("Exiting:: MerchantValidationController:: validateUniqueUserName method");
    return null;
  }

  @RequestMapping(value = GET_STATES, method = RequestMethod.GET)
  public @ResponseBody String getStatesById(Map model, HttpServletRequest request,
      HttpServletResponse response, HttpSession session) {
    ModelAndView modelAndView = new ModelAndView(CHATAK_ADMIN_CREATE_MERCHANT_PAGE);
    modelAndView.addObject(Constants.ERROR, null);
    logger.info("Entering :: MerchantValidationController :: getStatesById method");
    String country = request.getParameter("countryid");
    try {
      Response response2 = merchantUpdateService.getStatesByCountry(country);
      if (response2 != null) {
        return JsonUtil.convertObjectToJSON(response2);
      }
    } catch (ChatakAdminException e) {
      logger.error("ERROR:: MerchantValidationController:: getStatesById method", e);
      modelAndView.addObject(Constants.ERROR, messageSource
          .getMessage(Constants.CHATAK_GENERAL_ERROR, null, LocaleContextHolder.getLocale()));
    }
    logger.info("Exiting:: MerchantValidationController:: getStatesById method");
    return null;
  }

  @RequestMapping(value = GET_AGENT_NAMES_BY_CURRENCY_ALPHA, method = RequestMethod.GET)
  public @ResponseBody String getAgentNamesByCurrency(Map model, HttpServletRequest request,
      HttpServletResponse response, HttpSession session) {
    ModelAndView modelAndView = new ModelAndView(CHATAK_ADMIN_CREATE_MERCHANT_PAGE);
    modelAndView.addObject(Constants.ERROR, null);
    logger.info("Entering :: MerchantValidationController :: getAgentNamesByCurrency method");
    String currencyAlpha = request.getParameter("currencyAlpha");
    try {
      Response agentnamesList = merchantUpdateService.getAgentNames(currencyAlpha);
      if (agentnamesList != null) {
        model.put(Constants.AGENT_NAMES_LIST, agentnamesList);
        return JsonUtil.convertObjectToJSON(agentnamesList);
      }
    } catch (ChatakAdminException e) {
      logger.error("ERROR:: MerchantValidationController:: getAgentNamesByCurrency method", e);
      modelAndView.addObject(Constants.ERROR, messageSource
          .getMessage(Constants.CHATAK_GENERAL_ERROR, null, LocaleContextHolder.getLocale()));
    }
    logger.info("Exiting:: MerchantValidationController:: getAgentNamesByCurrency method");
    return null;
  }

  @RequestMapping(value = GET_AGENT_DATA_BY_ID, method = RequestMethod.GET)
  public @ResponseBody String getAgentById(Map model, HttpServletRequest request,
      HttpServletResponse response, HttpSession session) {
    ModelAndView modelAndView = new ModelAndView(CHATAK_ADMIN_CREATE_MERCHANT_PAGE);
    modelAndView.addObject(Constants.ERROR, null);
    logger.info("Entering :: MerchantValidationController :: getStatesById method");
    Long agentId = Long.parseLong(request.getParameter("agentId"));
    try {
      AgentDTOResponse merchantAgent = merchantValidateService.getAgentDataById(agentId);
      if (merchantAgent != null) {
        return JsonUtil.convertObjectToJSON(merchantAgent);
      }
    } catch (ChatakAdminException e) {
      logger.error("ERROR:: MerchantValidationController:: getStatesById method", e);
      modelAndView.addObject(Constants.ERROR, messageSource
          .getMessage(Constants.CHATAK_GENERAL_ERROR, null, LocaleContextHolder.getLocale()));
    }
    logger.info("Exiting:: MerchantValidationController:: getStatesById method");
    return null;
  }

  @RequestMapping(value = CHATAK_ADMIN_VALIDATE_AGENT_DETAILS, method = RequestMethod.GET)
  public @ResponseBody String validateAgentData(Map model, HttpServletRequest request,
      HttpServletResponse response, HttpSession session) {
    ModelAndView modelAndView = new ModelAndView(CHATAK_ADMIN_CREATE_MERCHANT_PAGE);
    modelAndView.addObject(Constants.ERROR, null);
    logger.info("Entering :: MerchantValidationController :: validateAgentData method");

    String agentAccountNumber = request.getParameter("agentAccountNumber");
    String agentClientId = request.getParameter("agentClientId");
    String agentANI = request.getParameter("agentANI");
    String currencyCodeAlpha = request.getParameter("currencyId");
    String clientResponse = null;

    try {
      clientResponse = merchantValidateService.validateAgentDetails(agentAccountNumber,
          agentClientId, agentANI, currencyCodeAlpha);

    } catch (Exception e) {
      logger.error("ERROR:: MerchantValidationController:: validateAgentData method", e);
      modelAndView.addObject(Constants.ERROR, messageSource
          .getMessage(Constants.CHATAK_GENERAL_ERROR, null, LocaleContextHolder.getLocale()));
    }
    logger.info("Exiting:: MerchantValidationController:: validateAgentData method");
    return clientResponse;
  }

  @RequestMapping(value = GET_CURRENCY_MERCHANT_CODE, method = RequestMethod.GET)
  public @ResponseBody String getmerchantCodeByCurrency(Map model, HttpServletRequest request,
      HttpServletResponse response, HttpSession session) {
    ModelAndView modelAndView = new ModelAndView(CHATAK_ADMIN_CREATE_MERCHANT_PAGE);
    modelAndView.addObject(Constants.ERROR, null);
    logger.info("Entering :: MerchantValidationController :: getLocalCurrencysBybankId method");
    String parentMerchantId = request.getParameter("parentMerchantId");
    Long id = Long.valueOf(parentMerchantId);

    try {
      Response responseVal = merchantValidateService.getLocalCurrency(id);

      if (responseVal != null) {
        return JsonUtil.convertObjectToJSON(responseVal);
      }
    } catch (ChatakAdminException e) {
      logger.error("ERROR:: MerchantValidationController:: getLocalCurrencysBybankId method", e);
      modelAndView.addObject(Constants.ERROR, messageSource
          .getMessage(Constants.CHATAK_GENERAL_ERROR, null, LocaleContextHolder.getLocale()));
    }
    logger.info("Exiting:: MerchantValidationController:: getLocalCurrencysBybankId method");
    return null;
  }

  @RequestMapping(value = GET_LOCAL_CURRENCY_BNAK_NAME, method = RequestMethod.GET)
  public @ResponseBody String getLocalCurrencyandbankName(Map model, HttpServletRequest request,
      HttpServletResponse response, HttpSession session) {
    ModelAndView modelAndView = new ModelAndView(CHATAK_ADMIN_CREATE_MERCHANT_PAGE);
    modelAndView.addObject(Constants.ERROR, null);
    logger.info("Entering :: MerchantValidationController :: getLocalCurrencysBybankId method");
    String currencyCodeAlpha = request.getParameter("currencyId");
    Response currencyCodes = currencyConfigService.getCurrencyCodeNumeric(currencyCodeAlpha);
    Long currencyId = currencyCodes.getCurrencyId();
    try {
      Response responseVal = bankService.getBankName(currencyId);

      if (responseVal != null) {
        return JsonUtil.convertObjectToJSON(responseVal);
      }
    } catch (ChatakAdminException e) {
      logger.error("ERROR:: MerchantValidationController:: getLocalCurrencyandbankName method", e);
      modelAndView.addObject(Constants.ERROR, messageSource
          .getMessage(Constants.CHATAK_GENERAL_ERROR, null, LocaleContextHolder.getLocale()));
    }
    logger.info("Exiting:: MerchantValidationController:: getLocalCurrencyandbankName method");
    return null;
  }

  @RequestMapping(value = CHATAK_ADMIN_UNIQUE_USER_VALIDATE_EDIT, method = RequestMethod.GET)
  public @ResponseBody String validateUniqueUserNameEdit(Map model, HttpServletRequest request,
      HttpServletResponse response, HttpSession session) {
    ModelAndView modelAndView = new ModelAndView(CHATAK_ADMIN_CREATE_MERCHANT_PAGE);
    modelAndView.addObject(Constants.ERROR, null);
    logger.info("Entering :: MerchantValidationController :: validateUniqueUserNameEdit method");
    String userName = request.getParameter("userName");
    String merchantCode = request.getParameter("merchantCode");
    Response response2 = null;
    try {
      response2 = merchantValidateService.validateUserNameEdit(userName, merchantCode);
      return JsonUtil.convertObjectToJSON(response2);
    } catch (ChatakAdminException exp) {
      logger.error("ERROR:: MerchantValidationController:: validateUniqueUserNameEdit method", exp);
      response2 = setErrorCodeAndErrorMessage(modelAndView);
      try {
        return JsonUtil.convertObjectToJSON(response2);
      } catch (ChatakAdminException e1) {
        logger.error(
            "ERROR:: MerchantValidationController:: validateUniqueUserNameEdit method::convertObjectToJSON",
            e1);
      }
    } catch (Exception e) {
      logger.error("ERROR:: MerchantValidationController:: validateUniqueUserNameEdit method", e);
      response2 = setErrorCodeAndErrorMessage(modelAndView);
      try {
        return JsonUtil.convertObjectToJSON(response2);
      } catch (ChatakAdminException e1) {
        logger.error(
            "ERROR:: MerchantValidationController:: validateUniqueUserNameEdit method ::convertObjectToJSON",
            e1);
      }
    }
    logger.info("Exiting:: MerchantValidationController:: validateUniqueUserNameEdit method");
    return null;
  }

  @RequestMapping(value = DECLINE_MERCHANT, method = RequestMethod.POST)
  public ModelAndView declineMerchant(HttpServletRequest request, HttpServletResponse response,
      Merchant merchant, @FormParam("merchantId") final Long merchantId,
      BindingResult bindingResult, HttpSession session, Map model) {
    String declineReason = request.getParameter("declineReason");
    logger.info("Entering :: MerchantValidationController :: updateMerchant method");
    ModelAndView modelAndView = new ModelAndView(CHATAK_ADMIN_HOME);

    String existingFeature = (String) session.getAttribute(Constants.EXISTING_FEATURES);
    if (!existingFeature.contains(FeatureConstants.ADMIN_SERVICE_MERCHANT_DELETE_FEATURE_ID)) {
      session.invalidate();
      modelAndView.setViewName(INVALID_REQUEST_PAGE);
      return modelAndView;
    }
    modelAndView.addObject(Constants.ERROR, null);
    session.setAttribute(Constants.ERROR, null);
    merchant.setPageSize(Constants.MAX_ENTITIES_PORTAL_DISPLAY_SIZE);
    merchant.setPageIndex(Constants.ONE);
    try {
      merchant.setId(merchantId);
      merchantValidateService.getMerchant(merchant);
      merchant.setStatus(PGConstants.STATUS_DECLINED);
      merchant.setDeclineReason(declineReason);
      merchant.setProcess(PGConstants.PG_TXN_DECLILNED);
      UpdateMerchantResponse updateMerchantResponse = merchantService.updateMerchant(merchant);
      if (updateMerchantResponse.isUpdated()
          && updateMerchantResponse.getErrorCode().equals(ActionErrorCode.ERROR_CODE_00)) {
        validateStatusPendingandDecline(session, modelAndView);
        modelAndView.addObject("pendingMerchantSucess", messageSource
            .getMessage("chatak.merchant.approve.decline", null, LocaleContextHolder.getLocale()));
      } else {
        setChatakAdminHomeViewName(modelAndView);
      }
    } catch (Exception exp) {
      logger.error("ERROR:: MerchantValidationController:: updateMerchant method", exp);
      modelAndView.addObject(Constants.ERROR,
          messageSource.getMessage(Constants.CHATAK_GENERAL_ERROR, null, LocaleContextHolder.getLocale()));
    }
    logger.info("Exiting:: MerchantValidationController:: updateMerchant method");
    return modelAndView;
  }

  private void setChatakAdminHomeViewName(ModelAndView modelAndView) {
	modelAndView.setViewName(CHATAK_ADMIN_HOME);
	modelAndView.addObject("pendingMerchantError", messageSource
	    .getMessage(ActionErrorCode.ERROR_CODE_Z5, null, LocaleContextHolder.getLocale()));
  }

  private void validateStatusPendingandDecline(HttpSession session, ModelAndView modelAndView) {
	List<Merchant> merchants = merchantUpdateService.getMerchantByStatusPendingandDecline();
	setSessionValue(session, merchants);
	modelAndView.setViewName(CHATAK_ADMIN_HOME);
	modelAndView.addObject(Constants.MERCHANT_SUB_LIST, merchants);
  }

  private void setSessionValue(HttpSession session, List<Merchant> merchants) {
    if (StringUtil.isListNotNullNEmpty(merchants)) {
      if (merchants.size() > Constants.TEN) {
        session.setAttribute(Constants.MERCHANT_SUB_LIST, merchants.subList(0, Constants.TEN));
      } else {
        session.setAttribute(Constants.MERCHANT_SUB_LIST, merchants);
      }
      session.setAttribute("pendingMerchants", merchants);
    }
  }

  @RequestMapping(value = MERCHANT_PENDING_TO_ACTIVE_STATE, method = RequestMethod.POST)
  public ModelAndView activeMerchant(HttpServletRequest request, HttpServletResponse response,
      Merchant merchant, @FormParam("getMerchantId") final Long getMerchantId,
      BindingResult bindingResult, HttpSession session, Map model) {
    logger.info("Entering :: MerchantValidationController :: updateMerchant method");
    ModelAndView modelAndView = new ModelAndView(CHATAK_ADMIN_HOME);
    modelAndView.addObject(Constants.ERROR, null);
    session.setAttribute(Constants.ERROR, null);
    merchant.setPageSize(Constants.MAX_ENTITIES_PORTAL_DISPLAY_SIZE);
    merchant.setPageIndex(Constants.ONE);
    try {
      merchant.setId(getMerchantId);
      merchantValidateService.getMerchant(merchant);
      String url = request.getRequestURL().toString();
      String uri = request.getRequestURI();
      String merchantLink = url.substring(0, url.length() - uri.length()) + "/"
          + Properties.getProperty("chatak.merchant.portal");
      merchant.setMerchantLink(merchantLink);
      merchant.setStatus(PGConstants.ZERO.intValue());
      merchant.setProcess(PGConstants.ACTION_ACTIVE);
      UpdateMerchantResponse updateMerchantResponse = merchantService.updateMerchant(merchant);
      if (updateMerchantResponse.isUpdated()
          && updateMerchantResponse.getErrorCode().equals(ActionErrorCode.ERROR_CODE_00)) {
        validateStatusPendingandDecline(session, modelAndView);
        modelAndView.addObject("pendingMerchantSucess", messageSource
            .getMessage("chatak.merchant.approve.success", null, LocaleContextHolder.getLocale()));
      } else {
        setChatakAdminHomeViewName(modelAndView);
      }
    } catch (Exception e) {
      logger.error("ERROR:: MerchantValidationController:: updateMerchant method", e);
      modelAndView.addObject(Constants.ERROR, messageSource
          .getMessage(Constants.CHATAK_GENERAL_ERROR, null, LocaleContextHolder.getLocale()));
    }
    logger.info("Exiting:: MerchantValidationController:: updateMerchant method");
    return modelAndView;
  }

  @RequestMapping(value = CHATAK_ADMIN_SHOW_SUB_MERCHANT_LIST, method = RequestMethod.POST)
  public ModelAndView showMerchantAndSubMerchantList(HttpServletRequest request,
      HttpServletResponse response,
      @FormParam("getParentMerchantId") final Long getParentMerchantId, HttpSession session,
      Map model) {
    logger
        .info("Entering :: MerchantValidationController :: showMerchantAndSubMerchantList method ");
    ModelAndView modelAndView = new ModelAndView(CHATAK_ADMIN_SEARCH_SUB_MERCHANT);
    Merchant merchant = new Merchant();
    Merchant subMerchant = new Merchant();
    DataBinder dataBinder = new DataBinder(merchant);
    modelAndView.addObject(Constants.ERROR, null);
    modelAndView.addObject(Constants.SUCESS, null);
    List<Option> processorNames = null;
    try {
      List<Option> countryList = merchantUpdateService.getCountries();
      modelAndView.addObject("countryList", countryList);
      session.setAttribute("countryList", countryList);
      merchant.setId(getParentMerchantId);
      session.setAttribute("updateMerchantId", getParentMerchantId);
      merchant = merchantValidateService.getMerchant(merchant);
      if (null == merchant) {
        new Merchant();
        throw new ChatakAdminException();
      } else {

        List<Option> options =
            merchantValidateService.getFeeProgramNamesForEdit(merchant.getFeeProgram());
        modelAndView.addObject("feeprogramnames", options);
        session.setAttribute("feeprogramnames", options);

        Response stateList = merchantUpdateService.getStatesByCountry(merchant.getCountry());
        modelAndView.addObject("stateList", stateList.getResponseList());
        session.setAttribute("stateList", stateList.getResponseList());

        stateList = merchantUpdateService.getStatesByCountry(merchant.getBankCountry());
        modelAndView.addObject("bankStateList", stateList.getResponseList());
        session.setAttribute("bankStateList", stateList.getResponseList());

        Response agentnamesList = merchantUpdateService.getAgentNames(merchant.getLocalCurrency());
        if (agentnamesList != null) {
          modelAndView.addObject(Constants.AGENT_NAMES_LIST, agentnamesList.getResponseList());
          session.setAttribute(Constants.AGENT_NAMES_LIST, agentnamesList.getResponseList());
        }

        stateList = merchantUpdateService.getStatesByCountry(merchant.getLegalCountry());
        modelAndView.addObject("legalStateList", stateList.getResponseList());
        session.setAttribute("legalStateList", stateList.getResponseList());

        modelAndView.addObject(Constants.MERCHANT, merchant);
        processorNames = merchantValidateService.getProcessorNames();

      }
      merchant.setPageSize(Constants.MAX_ENTITIES_PORTAL_DISPLAY_SIZE);
      merchant.setPageIndex(Constants.ONE);
      subMerchant.setPageSize(Constants.MAX_ENTITIES_PORTAL_DISPLAY_SIZE);
      subMerchant.setPageIndex(Constants.ONE);
      subMerchant.setParentMerchantId(getParentMerchantId);

      modelAndView = getSubMerchantList(session, model, modelAndView, subMerchant);

    } catch (ChatakAdminException e) {
      modelAndView = merchantController.showSearchMerchantPage(request, response, new Merchant(),
          dataBinder.getBindingResult(), model, session);
      modelAndView.addObject(Constants.ERROR, messageSource
          .getMessage(Constants.CHATAK_GENERAL_ERROR, null, LocaleContextHolder.getLocale()));
      modelAndView.addObject(Constants.MERCHANT, new Merchant());
      logger.error("ERROR:: MerchantValidationController:: showMerchantAndSubMerchantList method",
          e);
    }
    model.put(Constants.MERCHANT, merchant);
    modelAndView.addObject("processorNames", processorNames);
    logger.info("EXITING :: MerchantValidationController :: showMerchantAndSubMerchantList");
    return modelAndView;
  }

  private ModelAndView getSubMerchantList(HttpSession session, Map model, ModelAndView modelAndView,
      Merchant subMerchant) {
    try {
      MerchantSearchResponse searchResponse =
          merchantUpdateService.searchSubMerchantList(subMerchant);
      List<MerchantData> subMerchants = new ArrayList<>();
      session.setAttribute(Constants.MERCHANTS_MODEL, subMerchant);
      if (searchResponse != null && !CollectionUtils.isEmpty(searchResponse.getMerchants())) {
        subMerchants = searchResponse.getMerchants();

        PaginationUtil.getPagenationModel(modelAndView, searchResponse.getTotalNoOfRows());
      }
      modelAndView.addObject(Constants.SUB_MERCHANTS_MODEL, subMerchants);
      model.put("subMerchants", subMerchants);
    } catch (Exception e) {
      modelAndView.addObject(Constants.ERROR, messageSource
          .getMessage(Constants.CHATAK_GENERAL_ERROR, null, LocaleContextHolder.getLocale()));
      logger.error("ERROR:: MerchantValidationController:: showMerchantAndSubMerchantList method",
          e);
    }
    return modelAndView;
  }

  @RequestMapping(value = CHATAK_ADMIN_MERCHANT_REPORT, method = RequestMethod.POST)
  public ModelAndView downloadMerchantReport(HttpSession session, Map model,
      HttpServletRequest request, @FormParam("downLoadPageNumber") final Integer downLoadPageNumber,
      HttpServletResponse response, @FormParam("totalRecords") final Integer totalRecords,
      @FormParam("downloadAllRecords") final boolean downloadAllRecords) {
    String downloadType = request.getParameter("downloadType");
    logger.info("Entering:: MerchantValidationController:: downloadMerchantReport method");
    ModelAndView modelAndView = new ModelAndView(CHATAK_ADMIN_SEARCH_MERCHANT);
    Merchant merchant = null;
    MerchantSearchResponse searchResponse = null;
    try {
      merchant = (Merchant) session.getAttribute(Constants.MERCHANTS_MODEL);
      merchant.setPageIndex(downLoadPageNumber);
      Integer pageSize = merchant.getPageSize();
      if (downloadAllRecords) {
        merchant.setPageIndex(Constants.ONE);
        merchant.setPageSize(totalRecords);
      }
      searchResponse = merchantUpdateService.searchMerchant(merchant);
      List<MerchantCreateResponse> list = searchResponse.getMerchantCreateResponses();
      ExportDetails exportDetails = new ExportDetails();
      if (StringUtil.isListNotNullNEmpty(list)) {
        if (Constants.PDF_FILE_FORMAT.equalsIgnoreCase(downloadType)) {
          exportDetails.setExportType(ExportType.PDF);
        } else if (Constants.XLS_FILE_FORMAT.equalsIgnoreCase(downloadType)) {
          exportDetails.setExportType(ExportType.XLS);
          exportDetails.setExcelStartRowNumber(Integer.parseInt("4"));
        }
        setExportDetailsDataForDownloadRoleReport(list, exportDetails);
        ExportUtil.exportData(exportDetails, response, messageSource);
      }
      merchant.setPageSize(pageSize);
    } catch (Exception e) {
      modelAndView.addObject(Constants.ERROR, messageSource
          .getMessage(Constants.CHATAK_GENERAL_ERROR, null, LocaleContextHolder.getLocale()));
      logger.error("ERROR:: MerchantValidationController:: downloadMerchantReport method", e);
    }
    logger.info("Exiting:: MerchantValidationController:: downloadMerchantReport method");
    return null;
  }

  private void setExportDetailsDataForDownloadRoleReport(List<MerchantCreateResponse> list,
      ExportDetails exportDetails) {
    exportDetails.setReportName("Merchant_");
    exportDetails.setHeaderMessageProperty("chatak.header.merchant.messages");
    exportDetails.setHeaderList(getRoleHeaderList());
    exportDetails.setFileData(getRoleFileData(list));
  }
  
  private List<String> getRoleHeaderList() {
    String[] headerArr = {
        messageSource.getMessage("manage.label.sub-merchant.merchantcode", null,
            LocaleContextHolder.getLocale()),
        messageSource.getMessage("merchant.label.merchantname", null,
            LocaleContextHolder.getLocale()),
        messageSource.getMessage("currency-search-page.label.currencycode", null,
            LocaleContextHolder.getLocale()),
        messageSource.getMessage("merchant.label.entitytype", null,
            LocaleContextHolder.getLocale()),
        messageSource.getMessage("merchant.label.entityname", null,
                LocaleContextHolder.getLocale()),
        messageSource.getMessage("merchant-file-exportutil-phone", null,
            LocaleContextHolder.getLocale()),
        messageSource.getMessage("merchant-file-exportutil-email", null,
            LocaleContextHolder.getLocale()),
        messageSource.getMessage("admin.cardprogramname", null, LocaleContextHolder.getLocale()),
        messageSource.getMessage("merchant-file-exportutil-country", null,
            LocaleContextHolder.getLocale()),
        messageSource.getMessage("merchant-file-exportutil-status", null,
            LocaleContextHolder.getLocale())};
    return new ArrayList<String>(Arrays.asList(headerArr));
  }

  private static List<Object[]> getRoleFileData(List<MerchantCreateResponse> merchantData) {
    List<Object[]> fileData = new ArrayList<Object[]>();
    for (MerchantCreateResponse merData : merchantData) {
      Object[] rowData = new Object[Integer.parseInt("10")];
      rowData[0] = merData.getMerchantCode();
      rowData[1] = merData.getBusinessName();
      rowData[Integer.parseInt("2")] = merData.getLocalCurrency();
      rowData[Integer.parseInt("3")] = merData.getEntityType();
      rowData[Integer.parseInt("4")] = merData.getEntityName();
      if (merData.getPhone() != null) {
        rowData[Integer.parseInt("5")] = merData.getPhone();
      } else {
        rowData[Integer.parseInt("5")] = " ";
      }
      rowData[Integer.parseInt("6")] = merData.getEmailId();
      rowData[Integer.parseInt("7")] = merData.getCardProgramName();
      rowData[Integer.parseInt("8")] = merData.getCountry();
      rowData[Integer.parseInt("9")] = merData.getStatus();
      fileData.add(rowData);
    }
    return fileData;
  }
  
  @RequestMapping(value = GET_PARTNERS_BY_PROGRAM_MANAGER_ID, method = RequestMethod.GET)
  public @ResponseBody String getPartnersByProgramManagerId(Map model, HttpServletRequest request,
      HttpServletResponse response, HttpSession session) {
    ModelAndView modelAndView = new ModelAndView(CHATAK_ADMIN_SHOW_BATCH_REPORT);
    modelAndView.addObject(Constants.ERROR, null);
    logger.info("Entering :: MerchantValidationController :: getPartnersByProgramManagerId method");
    String programManagerId = request.getParameter("programManagerId");
    try {
      Response response2 = partnerService.getPartnersByProgramManagerId(programManagerId);
      if (response2 != null) {
        return JsonUtil.convertObjectToJSON(response2);
      }
    } catch (ChatakAdminException e) {
      logger.error("Error :: MerchantValidationController :: getPartnersByProgramManagerId method", e);
    }
    logger.info("Exiting :: MerchantValidationController :: getPartnersByProgramManagerId method");
    return null;
  }
  
  @RequestMapping(value = GET_ENTITY_DETAILS_BY_ENTITY_NAME, method = RequestMethod.GET)
  public @ResponseBody String getEntityName(Map model, HttpServletRequest request,
      HttpServletResponse response, HttpSession session,@FormParam("entityType")String entityType,@FormParam("currencyId") String currencyId) {

    ModelAndView modelAndView = new ModelAndView(CHATAK_ADMIN_CREATE_MERCHANT_PAGE);
    modelAndView.addObject(Constants.ERROR, null);
    logger.info("Entering :: MerchantValidationController :: getEntityName method");
    Response programManagerResponse = null;
    String userType = (String) session.getAttribute(Constants.LOGIN_USER_TYPE);
    LoginResponse loginResponse = (LoginResponse)session.getAttribute(Constants.LOGIN_RESPONSE_DATA);
    try {
      if(userType.equals(Constants.ADMIN_USER_TYPE)){
        if(null != entityType && entityType.equals(PGConstants.PROGRAM_MANAGER_NAME)){
          programManagerResponse = programManagerService.findProgramManagerNameByAccountCurrency(currencyId);
       } else {
           programManagerResponse = isoService.findISONameByAccountCurrency(currencyId);
       }        
      }else if(userType.equals(Constants.PM_USER_TYPE)){
        programManagerResponse = programManagerService.findByProgramManagerIdAndAccountCurrency(loginResponse.getEntityId(), currencyId);
      }
      
      if (programManagerResponse != null) {
        return JsonUtil.convertObjectToJSON(programManagerResponse);
      }
    } catch (ChatakAdminException e) {
      logger.error("ERROR:: MerchantValidationController:: getEntityName method", e);
      modelAndView.addObject(Constants.ERROR, messageSource
          .getMessage(Constants.CHATAK_GENERAL_ERROR, null, LocaleContextHolder.getLocale()));
    }
    logger.info("Exiting:: MerchantValidationController:: getEntityName method");
    return null;
  }
  
  @RequestMapping(value = FETCH_CARD_PROGRAM_BY_MERCHANTID, method = RequestMethod.GET)
	public @ResponseBody String fetchCardProgramByMerchantId(Map model, HttpSession session, @FormParam("merchantId") Long merchantId,
			@FormParam("entityType") String entityType) {
		LogHelper.logEntry(logger, LoggerMessage.getCallerName());
		CardProgramResponse cardProgramResponse = null;
		try {
			if (null != entityType && entityType.equals(PGConstants.PROGRAM_MANAGER_NAME)) {
				cardProgramResponse = programManagerService.findPMCardprogramByMerchantId(merchantId);
			} else {
				cardProgramResponse = isoService.fetchIsoCardProgramByMerchantId(merchantId);
			}
			cardProgramResponse.setErrorMessage(StatusConstants.STATUS_MESSAGE_SUCCESS);
			return JsonUtil.convertObjectToJSON(cardProgramResponse);
		} catch (Exception e) {
			LogHelper.logError(logger, LoggerMessage.getCallerName(), e, Constants.EXCEPTION);
		}
		LogHelper.logExit(logger, LoggerMessage.getCallerName());
		return null;
	}   	
}
