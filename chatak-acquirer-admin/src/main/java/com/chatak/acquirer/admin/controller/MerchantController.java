/**
 * 
 */
package com.chatak.acquirer.admin.controller;

import java.io.Serializable;
import java.text.NumberFormat;
import java.util.ArrayList;
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
import org.springframework.validation.BindingResult;
import org.springframework.validation.DataBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.chatak.acquirer.admin.constants.FeatureConstants;
import com.chatak.acquirer.admin.constants.URLMappingConstants;
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
import com.chatak.acquirer.admin.service.ResellerService;
import com.chatak.acquirer.admin.util.JsonUtil;
import com.chatak.acquirer.admin.util.PaginationUtil;
import com.chatak.acquirer.admin.util.StringUtil;
import com.chatak.pg.bean.Response;
import com.chatak.pg.constants.ActionErrorCode;
import com.chatak.pg.constants.PGConstants;
import com.chatak.pg.model.Merchant;
import com.chatak.pg.model.MerchantCurrencyMapping;
import com.chatak.pg.model.MerchantRequest;
import com.chatak.pg.user.bean.AddMerchantResponse;
import com.chatak.pg.user.bean.MerchantResponse;
import com.chatak.pg.user.bean.ProgramManagerRequest;
import com.chatak.pg.user.bean.UpdateMerchantResponse;
import com.chatak.pg.util.Constants;
import com.chatak.pg.util.Properties;

/**
 * << Add Comments Here >>
 * 
 * @author Girmiti Software
 * @date 03-Jan-2015 3:51:40 PM
 * @version 1.0
 */
@SuppressWarnings({"rawtypes", "unchecked", "unused"})
@Controller
public class MerchantController implements URLMappingConstants {

  private static Logger logger = Logger.getLogger(MerchantController.class);

  @Autowired
  MessageSource messageSource;

  @Autowired
  private MerchantService merchantService;

  @Autowired
  private BankService bankService;

  @Autowired
  private ResellerService resellerService;

  @Autowired
  private MerchantCategoryCodeService merchantCategoryCodeService;

  @Autowired
  private CurrencyConfigService currencyConfigService;

  @Autowired
  private MerchantUpdateService merchantUpdateService;

  @Autowired
  private MerchantValidateService merchantValidateService;

  @Autowired
  private PartnerService partnerService;

  @Autowired
  private ProgramManagerService programManagerService;
  
  @Autowired
  private IsoService isoService;

  /**
   * Method to show create merchant page
   * 
   * @param model
   * @param session
   * @return
   */
  @RequestMapping(value = CHATAK_ADMIN_CREATE_MERCHANT_PAGE, method = RequestMethod.GET)
  public ModelAndView showCreateMerchantPage(Map model, HttpSession session) {
    logger.info("Entering:: MerchantController:: showCreateMerchantPage method");
    ModelAndView modelAndView = new ModelAndView(CHATAK_ADMIN_CREATE_MERCHANT_PAGE);

    String existingFeature = (String) session.getAttribute(Constants.EXISTING_FEATURES);
    if (!existingFeature.contains(FeatureConstants.ADMIN_SERVICE_MERCHANT_CREATE_FEATURE_ID)) {
      return setInvalidRequestPage(session, modelAndView);
    }

    modelAndView.addObject(Constants.ERROR, null);
    session.setAttribute(Constants.ERROR, null);
    Merchant merchant = new Merchant();
    List<Option> processorNames = null;
    try {
      List<Option> options = merchantValidateService.getFeeProgramNames();
      modelAndView.addObject(Constants.FEE_PROGRAM_NAMES, options);

      /*To check is email uniqueness is allowed or not*/
      String isUserMailUnique = Properties.getProperty("prepaid.email.unique.enable");
      model.put("isUserMailUnique", isUserMailUnique);

      List<Option> countryList = merchantUpdateService.getCountries();
      modelAndView.addObject(Constants.COUNTRY_LIST, countryList);

      List<Option> currencyList = merchantUpdateService.getCurrencies();
      modelAndView.addObject(Constants.CURRENCY_LIST, currencyList);

      merchant.setMerchantType(PGConstants.MERCHANT);
      merchant.setCategory(PGConstants.PRIMARY_ACCOUNT);
      processorNames = merchantValidateService.getProcessorNames();

      List<Option> bankOptions = bankService.getBankData();
      modelAndView.addObject("bankList", bankOptions);

      List<String> mccList = merchantCategoryCodeService.getAllMCC();
      modelAndView.addObject("mccList", mccList);

      List<Option> currencyCodeList = currencyConfigService.getCurrencyConfigCode();
      modelAndView.addObject("currencyCodeList", currencyCodeList);

    } catch (ChatakAdminException e) {
      modelAndView.addObject(Constants.ERROR,
          messageSource.getMessage(Constants.CHATAK_GENERAL_ERROR, null, LocaleContextHolder.getLocale()));
      logger.error("ERROR:: MerchantController:: showCreateMerchantPage ChatakAdminException", e);
    } catch (Exception e) {
      modelAndView.addObject(Constants.ERROR,
          messageSource.getMessage(Constants.CHATAK_GENERAL_ERROR, null, LocaleContextHolder.getLocale()));
      logger.error("ERROR:: MerchantController:: showCreateMerchantPage Exception", e);
    }
    modelAndView.addObject(Constants.PROCESSOR_NAMES, processorNames);
    model.put(Constants.MERCHANT, merchant);
    logger.info("Exiting:: MerchantController:: showCreateMerchantPage method");
    return modelAndView;
  }

  /**
   * Method to create merchant page
   * 
   * @param model
   * @param session
   * @return
   */
  @RequestMapping(value = CHATAK_ADMIN_CREATE_MERCHANT, method = RequestMethod.POST)
  public ModelAndView createMerchant(HttpServletRequest request, HttpServletResponse response,
      Merchant merchant, BindingResult bindingResult, Map model, HttpSession session,
      @FormParam("currencyCode") final String currencyCode) {
    logger.info("Entering:: MerchantController:: createMerchant method");
    ModelAndView modelAndView = new ModelAndView(CHATAK_ADMIN_CREATE_MERCHANT_PAGE);
    String existingFeature = (String) session.getAttribute(Constants.EXISTING_FEATURES);
    if (!existingFeature.contains(FeatureConstants.ADMIN_SERVICE_MERCHANT_CREATE_FEATURE_ID)) {
      return setInvalidRequestPage(session, modelAndView);
    }
    modelAndView.addObject(Constants.ERROR, null);
    try {
      Long userid = (Long) session.getAttribute(Constants.LOGIN_USER_ID);
      String url = request.getRequestURL().toString();
      String uri = request.getRequestURI();
    
      String merchantLink = url.substring(0, url.length() - uri.length()) + "/"
          + Properties.getProperty("chatak.merchant.portal");
      merchant.setMerchantLink(merchantLink);
      Map<Long, Long> cardProgramAndEntityId = new HashMap<Long, Long>();
      String ids[];
      for(String id : merchant.getCardProgramIds()){
        ids = id.split("@");
        cardProgramAndEntityId.put(Long.valueOf(ids[0]), Long.valueOf(ids[1]));
      }
      merchant.setCardProgramAndEntityId(cardProgramAndEntityId);
      validateIssuancePartnerId(merchant);
      merchant.setMerchantType(PGConstants.MERCHANT);
      currencyCodeValidation(merchant, currencyCode);

      String currencyCodeAlpha = merchant.getCurrencyId();
      Response currencyCodes = currencyConfigService.getCurrencyCodeNumeric(currencyCodeAlpha);
      merchant.setBankCurrencyCode(currencyCodes.getCurrencyCodeNumeric());

      AddMerchantResponse addMerchantResponse =
          merchantService.addMerchant(merchant, String.valueOf(userid));
      if (null != addMerchantResponse
          && addMerchantResponse.getErrorCode().equals(ActionErrorCode.ERROR_CODE_00)) {
        new Merchant();
        modelAndView =
            showSearchMerchantPage(request, response, merchant, bindingResult, model, session);
        modelAndView.addObject(Constants.SUCESS, messageSource
            .getMessage("chatak.merchant.create.success", null, LocaleContextHolder.getLocale()));
      } else if (null != addMerchantResponse && addMerchantResponse.getErrorCode()
          .equals(ActionErrorCode.ERROR_CODE_DUPLICATE_ENTRY)) {
        modelAndView.addObject(Constants.ERROR, messageSource.getMessage(
            ActionErrorCode.ERROR_CODE_DUPLICATE_ENTRY, null, LocaleContextHolder.getLocale()));
      } else {
        modelAndView.addObject(Constants.ERROR, messageSource.getMessage(
            ActionErrorCode.ERROR_CODE_DUPLICATE_ENTRY, null, LocaleContextHolder.getLocale()));
      }
    } catch (Exception e) {
      logger.error("ERROR:: MerchantController:: createMerchant method", e);
      modelAndView.addObject(Constants.ERROR,
          messageSource.getMessage(Constants.CHATAK_GENERAL_ERROR, null, LocaleContextHolder.getLocale()));
    }
    model.put(Constants.MERCHANT, new Merchant());
    logger.info("Exiting:: MerchantController:: createMerchant method");
    return modelAndView;
  }

  private void setcurrencyCodeArray(Merchant merchant, String[] currencyCodeArray,
      List<MerchantCurrencyMapping> merchantCurrencyMappingList) {
    for (int i = 0; i < currencyCodeArray.length; i++) {
      merchant.setCurrencyId(currencyCodeArray[i]);
      MerchantCurrencyMapping merchantCurrencyMapping = new MerchantCurrencyMapping();
      merchantCurrencyMapping.setCurrencyCode(merchant.getCurrencyId());
      merchantCurrencyMappingList.add(merchantCurrencyMapping);
    }
  }

  private void validateIssuancePartnerId(Merchant merchant) {
    if (StringUtil.isNullAndEmpty(merchant.getIssuancePartnerId())) {
      merchant.setProgramManagerId(null);
    }
  }

  /**
   * Method to show search merchant page
   * 
   * @param model
   * @param session
   * @return
   */
  /* */
  @RequestMapping(value = CHATAK_ADMIN_SEARCH_MERCHANT_PAGE, method = RequestMethod.GET)
  public ModelAndView showSearchMerchantPage(HttpServletRequest request,
      HttpServletResponse response, Merchant merchant, BindingResult bindingResult, Map model,
      HttpSession session) {
    logger.info("Entering:: MerchantController:: showSearchMerchantPage method");
    ModelAndView modelAndView = new ModelAndView(CHATAK_ADMIN_SEARCH_MERCHANT);
    String existingFeature = (String) session.getAttribute(Constants.EXISTING_FEATURES);
    String userType = (String) session.getAttribute(Constants.LOGIN_USER_TYPE);
    if ("admin".equalsIgnoreCase(userType)) {
      if (!existingFeature.contains(FeatureConstants.ADMIN_SERVICE_MERCHANT_FEATURE_ID)) {
        return setInvalidRequestPage(session, modelAndView);
      }
    } else if (userType.equalsIgnoreCase(Constants.RESELLER)
        && !existingFeature.contains(FeatureConstants.RESELLER_SERVICE_MERCHANT_FEATURE_ID)) {
      return setInvalidRequestPage(session, modelAndView);
    }
    modelAndView.addObject(Constants.ERROR, null);
    session.setAttribute(Constants.ERROR, null);
    session.setAttribute(Constants.MERCHANTS_MODEL, merchant);
    merchant.setPageSize(Constants.INITIAL_ENTITIES_PORTAL_DISPLAY_SIZE);
    merchant.setPageIndex(Constants.ONE);
    try {
      List<Option> countryList = merchantUpdateService.getCountries();
      modelAndView.addObject(Constants.COUNTRY_LIST, countryList);
      List<MerchantData> merchants = new ArrayList<>();
      modelAndView.addObject(Constants.PAGE_SIZE, null);
      modelAndView.addObject(Constants.MERCHANTS_MODEL, merchants);
    } catch (Exception e) {
      modelAndView.addObject(Constants.ERROR,
          messageSource.getMessage(Constants.CHATAK_GENERAL_ERROR, null, LocaleContextHolder.getLocale()));
      logger.error("ERROR:: MerchantController:: showSearchMerchantPage method", e);
    }
    modelAndView.addObject("flag", false);
    modelAndView.addObject(Constants.MERCHANT, new Merchant());
    logger.info("Exiting:: MerchantController:: showSearchMerchantPage method");
    return modelAndView;
  }

  private ModelAndView setInvalidRequestPage(HttpSession session, ModelAndView modelAndView) {
	session.invalidate();
	modelAndView.setViewName(INVALID_REQUEST_PAGE);
	return modelAndView;
  }
  
  @RequestMapping(value = CHATAK_ADMIN_CREATE_MERCHANT, method = RequestMethod.GET)
  public ModelAndView createMerchantGetMethod(HttpServletRequest request, HttpServletResponse response,
	    Merchant merchant, BindingResult bindingResult, Map model, HttpSession session) {
    logger.info("Entering:: MerchantController:: createMerchantGetMethod method");
    ModelAndView modelAndView = searchMerchantGetMethod(request, response, merchant, bindingResult, model, session);
    logger.info("Exiting:: MerchantController:: createMerchantGetMethod method");
    return modelAndView;
  }

  /**
   * Method to show search merchant
   * 
   * @param model
   * @param session
   * @return
   */
  @RequestMapping(value = CHATAK_ADMIN_SEARCH_MERCHANT, method = RequestMethod.POST)
  public ModelAndView searchMerchant(HttpServletRequest request, HttpServletResponse response,
      Merchant merchant, BindingResult bindingResult, Map model, HttpSession session) {
    logger.info("Entering:: MerchantController:: searchMerchant method");
    ModelAndView modelAndView = new ModelAndView(CHATAK_ADMIN_SEARCH_MERCHANT);
    String userType = (String) session.getAttribute(Constants.LOGIN_USER_TYPE);
    String existingFeature = (String) session.getAttribute(Constants.EXISTING_FEATURES);
    if ("admin".equalsIgnoreCase(userType)) {
      if (!existingFeature.contains(FeatureConstants.ADMIN_SERVICE_MERCHANT_FEATURE_ID)) {
        return setInvalidRequestPage(session, modelAndView);
      }
    } else if (userType.equalsIgnoreCase(Constants.RESELLER)
        && !existingFeature.contains(FeatureConstants.RESELLER_SERVICE_MERCHANT_FEATURE_ID)) {
      return setInvalidRequestPage(session, modelAndView);
    }
    modelAndView.addObject(Constants.ERROR, null);
    session.setAttribute(Constants.ERROR, null);
    session.setAttribute(Constants.MERCHANTS_MODEL, merchant);
    merchant.setPageIndex(Constants.ONE);
    session.setAttribute(Constants.PAGE_NUMBER, Constants.ONE);
    try {
    	MerchantSearchResponse searchResponse = null;
    	LoginResponse loginResponse = (LoginResponse) session.getAttribute(Constants.LOGIN_RESPONSE_DATA);
    	getMerchantIdsMappedToEntity(merchant, loginResponse);
    	searchResponse = merchantUpdateService.searchMerchant(merchant, loginResponse.getUserType(), loginResponse.getEntityId());
      List<MerchantCreateResponse> merchants = new ArrayList<>();
      List<MerchantData> subMerchants = new ArrayList<>();
      if (searchResponse != null && !CollectionUtils.isEmpty(searchResponse.getMerchantCreateResponses())) {
        merchants = searchResponse.getMerchantCreateResponses();
        session.setAttribute(Constants.TOTAL_RECORDS, searchResponse.getTotalNoOfRows());
        modelAndView.addObject(Constants.PAGE_SIZE, merchant.getPageSize());
        modelAndView = PaginationUtil.getPagenationModel(modelAndView, searchResponse.getTotalNoOfRows());
      }
      if (searchResponse != null && !CollectionUtils.isEmpty(searchResponse.getSubMerchants())) {
        subMerchants = searchResponse.getSubMerchants();
      }
      session.setAttribute(Constants.TOTAL_RECORDS, searchResponse != null ? searchResponse.getTotalNoOfRows() : 0);
      modelAndView.addObject(Constants.MERCHANTS_MODEL, merchants);//
      modelAndView.addObject(Constants.SUB_MERCHANTS_MODEL, subMerchants);
    } catch (Exception e) {
      modelAndView.addObject(Constants.ERROR,
          messageSource.getMessage(Constants.CHATAK_GENERAL_ERROR, null, LocaleContextHolder.getLocale()));
      logger.error("ERROR:: MerchantController:: searchMerchant method", e);
    }
    modelAndView.addObject("flag", true);
    model.put(Constants.MERCHANT, merchant);
    logger.info("Exiting:: MerchantController:: searchMerchant method");
    return modelAndView;
  }
  
	@RequestMapping(value = CHATAK_ADMIN_SEARCH_MERCHANT, method = RequestMethod.GET)
	public ModelAndView searchMerchantGetMethod(HttpServletRequest request, HttpServletResponse response,
			Merchant merchant, BindingResult bindingResult, Map model, HttpSession session) {
		logger.info("Entering:: MerchantController:: searchMerchant method");
		ModelAndView modelAndView = showSearchMerchantPage(request, response, merchant, bindingResult, model, session);
		logger.info("Exiting:: MerchantController:: searchMerchant method");
		return modelAndView;
	}

  @RequestMapping(value = CHATAK_ADMIN_SHOW_EDIT_MERCHANT, method = RequestMethod.POST)
  public ModelAndView showEditMerchant(HttpServletRequest request, HttpServletResponse response,
      @FormParam("getMerchantId") final Long getMerchantId, HttpSession session, Map model) {
    logger.info("Entering :: MerchantController :: showEditMerchant method ");
    ModelAndView modelAndView = new ModelAndView(CHATAK_ADMIN_EDIT_MERCHANT);

    String existingFeature = (String) session.getAttribute(Constants.EXISTING_FEATURES);
    LoginResponse loginResponse = (LoginResponse) session.getAttribute(Constants.LOGIN_RESPONSE_DATA);
    if (!existingFeature.contains(FeatureConstants.ADMIN_SERVICE_MERCHANT_EDIT_FEATURE_ID)) {
      return setInvalidRequestPage(session, modelAndView);
    }
    Merchant merchant = new Merchant();
    DataBinder dataBinder = new DataBinder(merchant);
    modelAndView.addObject(Constants.ERROR, null);
    modelAndView.addObject(Constants.SUCESS, null);
    List<Option> processorNames = null;
    try {
      List<Option> countryList = merchantUpdateService.getCountries();
      modelAndView.addObject(Constants.COUNTRY_LIST, countryList);
      merchant.setId(getMerchantId);
      merchant = merchantValidateService.getMerchant(merchant);
      session.setAttribute(Constants.STATUS, merchant.getStatus());
      merchant.setCurrencyId(merchant.getLocalCurrency());
      modelAndView.addObject("accountNumber", merchant.getAccountId());
      session.setAttribute("accountNumber", merchant.getAccountId());

      Response agentnamesList = merchantUpdateService.getAgentNames(merchant.getLocalCurrency());
      if (agentnamesList != null) {
        modelAndView.addObject("agentnamesList", agentnamesList.getResponseList());
        session.setAttribute("agentnamesList", new ArrayList(agentnamesList.getResponseList()));
      }
      // currency list start
      List<Option> currencyList = merchantUpdateService.getCurrencies();
      modelAndView.addObject(Constants.CURRENCY_LIST, currencyList);
      // currency list End
      MerchantResponse selectedCurrencyList = merchantUpdateService.findByMerchantId(getMerchantId);
      processCurrencyList(model, currencyList, selectedCurrencyList);
      Response  programManagerResponse;
      merchant.setAssociatedTo(selectedCurrencyList.getMerchant().getAssociatedTo());
      if (StringUtil.isListNotNullNEmpty(currencyList)) {
        List<Option> allcurrencyLists = getAllCurrencyLists(currencyList, selectedCurrencyList);
        model.put("currencyListData", allcurrencyLists);
      }
      if(loginResponse.getUserType().equals(Constants.PM_USER_TYPE)){
    	  if(isMerchantAssociatedToPm(merchant)){
    		  programManagerResponse = programManagerService.findProgramManagerNameAndIdByEntityId(loginResponse.getEntityId(), getMerchantId);
    		  List<Option> options = new ArrayList<>(1);
    		  Option option = getOptionOnProgramManagerResponse(programManagerResponse);
    		  options.add(option);
    		  programManagerResponse.setResponseList(options);
    		  model.put("selectedEntityList", selectedCurrencyList.getProgramManagerRequests());
    	  } else{
    		  programManagerResponse = isoService.findIsoNameAndIdByEntityId(loginResponse.getEntityId());
    		  model.put("selectedEntityList", selectedCurrencyList.getIsoRequests());
    	  }
      } else {
    	  if(isMerchantAssociatedToPm(merchant)){
    		  programManagerResponse = programManagerService.findProgramManagerNameByCurrencyAndId(getMerchantId,merchant.getLocalCurrency());
    		  model.put("selectedEntityList", selectedCurrencyList.getProgramManagerRequests());
    	  } else{
    		  programManagerResponse = isoService.findIsoNameByCurrencyAndId(getMerchantId, merchant.getLocalCurrency());
    		  model.put("selectedEntityList", selectedCurrencyList.getIsoRequests());
    	  }
      }
      model.put("cardProgramList", selectedCurrencyList.getCardProgramRequests());
      model.put(Constants.MERCHANT, selectedCurrencyList.getMerchant());
      model.put("EntityList", programManagerResponse.getResponseList());
      
      if (null == merchant) {
        merchant = new Merchant();
        throw new ChatakAdminException();
      } else {
        List<Option> options =
            merchantValidateService.getFeeProgramNamesForEdit(merchant.getFeeProgram());
        modelAndView.addObject(Constants.FEE_PROGRAM_NAMES, options);

        Response stateList = merchantUpdateService.getStatesByCountry(merchant.getCountry());
        modelAndView.addObject(Constants.STATE_LIST, stateList.getResponseList());
        session.setAttribute(Constants.STATE_LIST, new ArrayList(stateList.getResponseList()));

        stateList = merchantUpdateService.getStatesByCountry(merchant.getBankCountry());
        modelAndView.addObject(Constants.BANK_STATE_LIST, stateList.getResponseList());
        session.setAttribute(Constants.BANK_STATE_LIST, new ArrayList(stateList.getResponseList()));
        session.setAttribute(Constants.UPDATE_MERCHANT_ID, getMerchantId);
        modelAndView.addObject(Constants.MERCHANT, merchant);

        processorNames = merchantValidateService.getProcessorNames();
        List<Option> bankOptions = bankService.getBankData();
        modelAndView.addObject("bankList", bankOptions);
        List<Option> resellerOptions = resellerService.getResellerData();
        modelAndView.addObject("resellerList", resellerOptions);
        List<String> mccList = merchantCategoryCodeService.getAllMCC();
        modelAndView.addObject("mccList", mccList);
      }
    } catch (ChatakAdminException e) {
      modelAndView = showSearchMerchantPage(request, response, merchant,
          dataBinder.getBindingResult(), model, session);
      modelAndView.addObject(Constants.ERROR,
          messageSource.getMessage(Constants.CHATAK_GENERAL_ERROR, null, LocaleContextHolder.getLocale()));
      modelAndView.addObject(Constants.MERCHANT, new Merchant());
      logger.error("ERROR:: MerchantController:: showEditMerchant method", e);
    } catch (Exception e) {
      modelAndView.addObject(Constants.ERROR,
          messageSource.getMessage(Constants.CHATAK_GENERAL_ERROR, null, LocaleContextHolder.getLocale()));
      logger.error("ERROR:: MerchantController:: showEditMerchant method", e);
    }
    modelAndView.addObject(Constants.PROCESSOR_NAMES, processorNames);
    logger.info("EXITING :: MerchantController :: showEditMerchant");
    modelAndView.addObject(Constants.MERCHANT, merchant);
    
    return modelAndView;
  }

  private boolean isMerchantAssociatedToPm(Merchant merchant) {
    return merchant.getAssociatedTo() != null && merchant.getAssociatedTo().equals(PGConstants.PROGRAM_MANAGER_NAME);
  }

  private Option getOptionOnProgramManagerResponse(Response programManagerResponse) {
    Option option = new Option();
    if (programManagerResponse.getId() != null) {
      option.setValue(programManagerResponse.getId().toString());
    }
    option.setLabel(programManagerResponse.getProgramManagerName());
    return option;
  }

  private List<Option> getAllCurrencyLists(List<Option> currencyList,
		  MerchantResponse selectedCurrencyList) {
    List<Option> allcurrencyLists = new ArrayList<>();
    for (int i = 0; i < currencyList.size(); i++) {
      boolean isExists = false;
      if (StringUtil.isListNotNullNEmpty(selectedCurrencyList.getMerchant().getSelectedCurrencyMapping())) {
        isExists = getMerchantCurrencyMapping(currencyList, selectedCurrencyList, i, isExists);
      }
      if (!isExists)
        allcurrencyLists.add(currencyList.get(i));
    }
    return allcurrencyLists;
  }

  private boolean getMerchantCurrencyMapping(List<Option> currencyList,
		  MerchantResponse selectedCurrencyList, int i, boolean isExists) {
    for (MerchantCurrencyMapping merchantCurrencyMapping : selectedCurrencyList.getMerchant()
        .getSelectedCurrencyMapping()) {
      if (currencyList.get(i).getLabel()
          .equals(merchantCurrencyMapping.getCurrencyCode())) {
        isExists = true;
        break;
      }
    }
    return isExists;
  }

  private void processCurrencyList(Map model, List<Option> currencyList,
		  MerchantResponse selectedCurrencyList) {
    if (StringUtil.isListNotNullNEmpty(currencyList)) {
      List<Option> currencyLists = new ArrayList<>();
      getCurrencyList(currencyList, selectedCurrencyList, currencyLists);
      model.put("selectedCurrencyCodes", currencyLists);
    }
  }

  private void getCurrencyList(List<Option> currencyList, MerchantResponse selectedCurrencyList,
      List<Option> currencyLists) {
    for (int i = 0; i < currencyList.size(); i++) {
      boolean isExists = false;
      if (StringUtil.isListNotNullNEmpty(selectedCurrencyList.getMerchant().getSelectedCurrencyMapping())) {
        isExists = getMerchantCurrencyMapping(currencyList, selectedCurrencyList, i, isExists);
      }
      if (isExists)
        currencyLists.add(currencyList.get(i));
    }
  }

  /**
   * Method used for Pagination Util
   * 
   * @param session
   * @param pageNumber
   * @param model
   * @return
   */
  @RequestMapping(value = CHATAK_ADMIN_MERCHANTS_PAGINATION, method = RequestMethod.POST)
  public ModelAndView getPaginationList(final HttpSession session,
      @FormParam(Constants.PAGE_NUMBER) final Integer pageNumber,
      @FormParam(Constants.TOTAL_RECORDS) final Integer totalRecords, Map model) {
    logger.info("Entering:: MerchantController:: getPaginationList method");
    ModelAndView modelAndView = new ModelAndView(CHATAK_ADMIN_SEARCH_MERCHANT);
    try {
      Merchant merchant = null;
      MerchantSearchResponse searchResponse = null;
      List<MerchantData> merchants = null;
      merchant = (Merchant) session.getAttribute(Constants.MERCHANTS_MODEL);
      model.put(Constants.MERCHANT, merchant);
      merchant.setPageIndex(pageNumber);
      merchant.setNoOfRecords(totalRecords);
      modelAndView =
          validateMerchantSearchResponse(session, pageNumber, totalRecords, modelAndView, merchant);
    } catch (Exception e) {
      logger.error("ERROR:: MerchantController:: getPaginationList method", e);
      modelAndView.addObject(Constants.ERROR,
          messageSource.getMessage(Constants.CHATAK_GENERAL_ERROR, null, LocaleContextHolder.getLocale()));
    }
    logger.info("Exiting:: MerchantController:: getPaginationList method");
    return modelAndView;
  }

  private ModelAndView validateMerchantSearchResponse(final HttpSession session,
      final Integer pageNumber, final Integer totalRecords, ModelAndView modelAndView,
      Merchant merchant) {
    MerchantSearchResponse searchResponse;
    List<MerchantCreateResponse> merchants;
    LoginResponse loginResponse = (LoginResponse) session.getAttribute(Constants.LOGIN_RESPONSE_DATA);
    try {
      searchResponse = merchantUpdateService.searchMerchant(merchant, loginResponse.getUserType(), loginResponse.getEntityId());
      if (searchResponse != null && !CollectionUtils.isEmpty(searchResponse.getMerchantCreateResponses())) {
        merchants = searchResponse.getMerchantCreateResponses();
        modelAndView.addObject(Constants.PAGE_SIZE, merchant.getPageSize());
        modelAndView = PaginationUtil.getPagenationModelSuccessive(modelAndView, pageNumber,
            searchResponse.getTotalNoOfRows());
        session.setAttribute(Constants.PAGE_NUMBER, pageNumber);
        session.setAttribute(Constants.TOTAL_RECORDS, totalRecords);
        modelAndView.addObject(Constants.MERCHANTS_MODEL, merchants);
      }
    } catch (Exception e) {
      modelAndView.addObject(Constants.ERROR, messageSource
          .getMessage(Constants.CHATAK_GENERAL_ERROR, null, LocaleContextHolder.getLocale()));
      logger.error("ERROR:: MerchantController:: searchMerchant method", e);
    }
    return modelAndView;
  }

  /**
   * Method to update merchant page
   * 
   * @param model
   * @param session
   * @return
   */
  @RequestMapping(value = CHATAK_ADMIN_MERCHANT_STATUS_CHANGE, method = RequestMethod.POST)
  public ModelAndView changeMerchantStatus(final HttpSession session,
      @FormParam("suspendActiveId") final Long merchantId,
      @FormParam("suspendActiveStatus") final String merchantStatus,
      @FormParam("reason") final String reason, Map<String, Serializable> model,
      HttpServletRequest request, HttpServletResponse response) {
    logger.info("Entering:: MerchantController:: changeMerchantStatus method");

    ModelAndView modelAndView = new ModelAndView(CHATAK_ADMIN_SEARCH_MERCHANT);
    String existingFeature = (String) session.getAttribute(Constants.EXISTING_FEATURES);
    if (!existingFeature.contains(FeatureConstants.ADMIN_SERVICE_MERCHANT_FEATURE_ID)) {
      return setInvalidRequestPage(session, modelAndView);
    }
    try {
      MerchantData merchantData = new MerchantData();
      merchantData.setId(merchantId);
      merchantData.setStatus(merchantStatus);
      merchantData.setReason(reason);
      Response merchantResponse = merchantValidateService.changeMerchantStatus(merchantData);
      modelAndView = getPaginationList(session,
          StringUtil.isNull((Integer) session.getAttribute(Constants.PAGE_NUMBER)) ? 1
              : (Integer) session.getAttribute(Constants.PAGE_NUMBER),
          (Integer) session.getAttribute(Constants.TOTAL_RECORDS), model);
      if (ActionErrorCode.ERROR_CODE_00.equals(merchantResponse.getErrorCode())) {
        model.put(Constants.SUCESS, merchantResponse.getErrorMessage());
      } else {
        model.put(Constants.ERROR, merchantResponse.getErrorMessage());
      }
    } catch (Exception e) {
      logger.error("ERROR:: MerchantController:: changeMerchantStatus method", e);
      model.put(Constants.ERROR,
          messageSource.getMessage(Constants.CHATAK_GENERAL_ERROR, null, LocaleContextHolder.getLocale()));
    }
    modelAndView.addObject("flag", false);
    logger.info("Exiting:: MerchantController:: changeMerchantStatus method");
    return modelAndView;
  }

  @RequestMapping(value = CHATAK_ADMIN_UPDATE_MERCHANT, method = RequestMethod.POST)
  public ModelAndView updateMerchant(HttpServletRequest request, HttpServletResponse response,
		  Merchant merchant, BindingResult bindingResult, HttpSession session, Map model,
      @FormParam("currencyCode") final String currencyCode) {
    logger.info("Entering :: MerchantController :: updateMerchant method");
    ModelAndView modelAndView = new ModelAndView(CHATAK_ADMIN_SEARCH_MERCHANT);
    String existingFeature = (String) session.getAttribute(Constants.EXISTING_FEATURES);
    if (!existingFeature.contains(FeatureConstants.ADMIN_SERVICE_MERCHANT_EDIT_FEATURE_ID)) {
      return setInvalidRequestPage(session, modelAndView);
    }
    modelAndView.addObject(Constants.ERROR, null);
    session.setAttribute(Constants.ERROR, null);
    merchant.setPageSize(Constants.MAX_ENTITIES_PORTAL_DISPLAY_SIZE);
    merchant.setPageIndex(Constants.ONE);
    try {
      String url = request.getRequestURL().toString();
      String uri = request.getRequestURI();
      String merchantLink = url.substring(0, url.length() - uri.length()) + "/"
          + Properties.getProperty("chatak.merchant.portal");
      merchant.setMerchantLink(merchantLink);
      String prevCiPartnerId = null;
      boolean isSameCiPartner = true;
      if (null != session.getAttribute(Constants.PREVIOUS_CI_PARTNER_ID)) {
        prevCiPartnerId = session.getAttribute(Constants.PREVIOUS_CI_PARTNER_ID).toString();
      }
      if (null != prevCiPartnerId && !merchant.getIssuancePartnerId().equals(prevCiPartnerId)) {
        isSameCiPartner = false;
      }
      Map<Long, Long> cardProgramAndEntityId = new HashMap<Long, Long>();
      String ids[];
      for(String id : merchant.getCardProgramIds()){
        ids = id.split("@");
        cardProgramAndEntityId.put(Long.valueOf(ids[0]), Long.valueOf(ids[1]));
      }
      merchant.setCardProgramAndEntityId(cardProgramAndEntityId);
      setIssuancePartnerId(merchant);
      setSessionStatus(merchant, session);
      currencyCodeValidation(merchant, currencyCode);
      MerchantSearchResponse merchant2 =
          merchantUpdateService.getMerchantCode(merchant.getMerchantCode());
      merchant.setStatus(merchant2.getMerchantData().getStatus());
      merchant.setId(merchant2.getMerchantData().getId());
      merchant.setProcess(PGConstants.UPDATE);
      String currencyCodeAlpha = merchant.getCurrencyId();
      Response currencyCodes = currencyConfigService.getCurrencyCodeNumeric(currencyCodeAlpha);
      merchant.setBankCurrencyCode(currencyCodes.getCurrencyCodeNumeric());
      UpdateMerchantResponse updateMerchantResponse = merchantService.updateMerchant(merchant);
      if (updateMerchantResponse.isUpdated()
          && updateMerchantResponse.getErrorCode().equals(ActionErrorCode.ERROR_CODE_00)) {
        if (!isSameCiPartner) {
          merchantUpdateService.updateSubMerchantsPartnerId(merchant.getMerchantCode());
        }
        Merchant merchantData = new Merchant();
        modelAndView =
            showSearchMerchantPage(request, response, merchantData, bindingResult, model, session);
        modelAndView.addObject(Constants.SUCESS, messageSource
            .getMessage("chatak.merchant.update.success", null, LocaleContextHolder.getLocale()));
        modelAndView.addObject(Constants.MERCHANT, merchantData);
      } else {
        modelAndView = showEditMerchant(request, response, merchant.getId(), session, model);
        modelAndView.setViewName(CHATAK_ADMIN_SEARCH_MERCHANT_PAGE);
        modelAndView.addObject(Constants.MERCHANT, merchant);
        modelAndView.addObject(Constants.ERROR, messageSource
            .getMessage(ActionErrorCode.ERROR_CODE_Z5, null, LocaleContextHolder.getLocale()));
      }
    } catch (Exception e) {
      logger.error("ERROR:: MerchantController:: updateMerchant method", e);
      modelAndView.addObject(Constants.ERROR,
          messageSource.getMessage(Constants.CHATAK_GENERAL_ERROR, null, LocaleContextHolder.getLocale()));
    }
    logger.info("Exiting:: MerchantController:: updateMerchant method");
    return modelAndView;
  }

	/**
	 * @param merchant
	 * @param currencyCode
	 */
	private void currencyCodeValidation(Merchant merchant, final String currencyCode) {
		if (!StringUtil.isNullAndEmpty(currencyCode)) {
			String[] currencyCodeArray = currencyCode.split(",");

			List<MerchantCurrencyMapping> merchantCurrencyMappingList = new ArrayList<>();
			if (currencyCodeArray != null) {
				setcurrencyCodeArray(merchant, currencyCodeArray, merchantCurrencyMappingList);
			}
			merchant.setSelectedCurrencyMapping(merchantCurrencyMappingList);
		}
	}

  private void setSessionStatus(Merchant merchant, HttpSession session) {
    Integer sessionStatus;
    if (null != session.getAttribute(Constants.STATUS)) {
      sessionStatus = (Integer) session.getAttribute(Constants.STATUS);
      merchant.setSessionStatus(sessionStatus);
    }
  }

  private void setIssuancePartnerId(Merchant merchant) {
    if (StringUtil.isNullAndEmpty(merchant.getIssuancePartnerId())) {
      merchant.setProgramManagerId(null);
    } else if (!merchant.getMerchantType().equalsIgnoreCase(Constants.MERCHANT)
        && StringUtil.isNullAndEmpty(merchant.getAgentId())) {
      merchant.setIssuancePartnerId(null);
      merchant.setProgramManagerId(null);
    }
  }
  
  @RequestMapping(value = CHATAK_ADMIN_GET_PM_DATA_BY_PARTNER, method = RequestMethod.GET)
  public @ResponseBody String getProgramManagerDetailsByPartner(Map model, HttpServletRequest request,
      HttpServletResponse response, HttpSession session) {

    ModelAndView modelAndView = new ModelAndView(CHATAK_ADMIN_CREATE_MERCHANT_PAGE);
    modelAndView.addObject(Constants.ERROR, null);
    logger.info("Entering :: MerchantController :: getProgramManagerDetailsByPartner method");
    String partnerId = request.getParameter("partnerId");

    try {
      Response programManagerResponse = merchantService.findProgramManagerByPartnerId(partnerId);
      if (programManagerResponse != null) {
        return JsonUtil.convertObjectToJSON(programManagerResponse);
      }
    } catch (ChatakAdminException e) {
      logger.error("ERROR:: MerchantController:: getProgramManagerDetailsByPartner method", e);
      modelAndView.addObject(Constants.ERROR, messageSource
          .getMessage(Constants.CHATAK_GENERAL_ERROR, null, LocaleContextHolder.getLocale()));
    }
    logger.info("Exiting:: MerchantController:: getProgramManagerDetailsByPartner method");
    return null;
  }
  
  private void getMerchantIdsMappedToEntity(Merchant merchant, LoginResponse loginResponse) throws ChatakAdminException{
	  List<Long> entityIds = new ArrayList<>();
		if(loginResponse.getUserType().equalsIgnoreCase(Constants.PM_USER_TYPE)) {
			entityIds.add(loginResponse.getEntityId());
  		List<Long> merchantIds = merchantUpdateService.findByEntityIdAndEntitytype(entityIds, Constants.PM_USER_TYPE);
  		List<Long> entityIsoIds = new ArrayList<>();
  		List<Long> isoIds = isoService.findByPmId(loginResponse.getEntityId());
  		entityIsoIds.addAll(isoIds);
  		List<Long> merchantIsoIds = merchantUpdateService.findByEntityIdAndEntitytype(entityIsoIds, Constants.ISO_USER_TYPE);
  		merchantIds.addAll(merchantIsoIds);
  		merchant.setEntitiesId(merchantIds);
		} else if(loginResponse.getUserType().equalsIgnoreCase(Constants.ISO_USER_TYPE)) {
			entityIds.add(loginResponse.getEntityId());
			List<Long> merchantIds = merchantUpdateService.findByEntityIdAndEntitytype(entityIds, Constants.ISO_USER_TYPE);
		    		merchant.setEntitiesId(merchantIds);
		}
  }
}
