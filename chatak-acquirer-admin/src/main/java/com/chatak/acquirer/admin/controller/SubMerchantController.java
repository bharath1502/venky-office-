package com.chatak.acquirer.admin.controller;

import java.io.Serializable;
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
import org.springframework.web.servlet.ModelAndView;

import com.chatak.acquirer.admin.constants.FeatureConstants;
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
import com.chatak.acquirer.admin.service.MerchantAccountService;
import com.chatak.acquirer.admin.service.MerchantCategoryCodeService;
import com.chatak.acquirer.admin.service.MerchantService;
import com.chatak.acquirer.admin.service.MerchantUpdateService;
import com.chatak.acquirer.admin.service.MerchantValidateService;
import com.chatak.acquirer.admin.service.PartnerService;
import com.chatak.acquirer.admin.service.ProgramManagerService;
import com.chatak.acquirer.admin.service.ResellerService;
import com.chatak.acquirer.admin.util.ExportUtil;
import com.chatak.acquirer.admin.util.PaginationUtil;
import com.chatak.acquirer.admin.util.StringUtil;
import com.chatak.pg.bean.Response;
import com.chatak.pg.constants.ActionErrorCode;
import com.chatak.pg.constants.PGConstants;
import com.chatak.pg.enums.ExportType;
import com.chatak.pg.model.Merchant;
import com.chatak.pg.user.bean.AddMerchantResponse;
import com.chatak.pg.user.bean.DeleteMerchantResponse;
import com.chatak.pg.user.bean.GetMerchantListRequest;
import com.chatak.pg.user.bean.MerchantResponse;
import com.chatak.pg.user.bean.UpdateMerchantResponse;
import com.chatak.pg.util.Constants;
import com.chatak.pg.util.Properties;

@SuppressWarnings({"rawtypes", "unchecked"})
@Controller
public class SubMerchantController implements URLMappingConstants {

  private static Logger logger = Logger.getLogger(SubMerchantController.class);

  @Autowired
  MessageSource messageSource;

  @Autowired
  private MerchantUpdateService merchantUpdateService;

  @Autowired
  private BankService bankService;

  @Autowired
  private ResellerService resellerService;

  @Autowired
  private MerchantCategoryCodeService merchantCategoryCodeService;

  @Autowired
  private CurrencyConfigService currencyConfigService;

  @Autowired
  MerchantController merchantController;

  @Autowired
  private MerchantService merchantService;

  @Autowired
  private MerchantValidateService merchantValidateService;

  @Autowired
  private MerchantAccountService merchantAccountService;

  @Autowired
  private PartnerService partnerService;
  
  @Autowired
  private ProgramManagerService programManagerService;
  
  @Autowired
  private IsoService isoService;

  @RequestMapping(value = CHATAK_ADMIN_SEARCH_SUB_MERCHANT_PAGE, method = RequestMethod.GET)
  public ModelAndView showSearchSubMerchantPage(HttpServletRequest request,
      HttpServletResponse response, Merchant merchant, BindingResult bindingResult, Map model,
      HttpSession session) {
    logger.info("Entering:: MerchantController:: showSearchMerchantPage method");
    ModelAndView modelAndView = new ModelAndView(CHATAK_ADMIN_SEARCH_SUB_MERCHANT);
    String existingFeature = (String) session.getAttribute(Constants.EXISTING_FEATURES);
      if (!existingFeature.contains(FeatureConstants.ADMIN_SERVICE_SUB_MERCHANT_FEATURE_ID)) {
        return setInvalidRequestPage(session, modelAndView);
      }
    setPageSizeAndIndex(merchant, session, modelAndView);
    try {
      fetchCountryListMerchantData(session, modelAndView);
    } catch (Exception e) {
      modelAndView.addObject(Constants.ERROR,
          messageSource.getMessage(Constants.CHATAK_GENERAL_ERROR, null, LocaleContextHolder.getLocale()));
      logger.error("ERROR:: MerchantController:: showSearchMerchantPage method", e);
    }
    modelAndView.addObject("flag", false);
    modelAndView.addObject(Constants.MERCHANT, merchant);
    logger.info("Exiting:: MerchantController:: showSearchMerchantPage method");
    return modelAndView;
  }

  private ModelAndView setInvalidRequestPage(HttpSession session, ModelAndView modelAndView) {
	session.invalidate();
	modelAndView.setViewName(INVALID_REQUEST_PAGE);
	return modelAndView;
  }

  private void setPageSizeAndIndex(Merchant merchant, HttpSession session, ModelAndView modelAndView) {
	modelAndView.addObject(Constants.ERROR, null);
    session.setAttribute(Constants.ERROR, null);
    session.setAttribute(Constants.MERCHANTS_MODEL, merchant);
    merchant.setPageSize(Constants.INITIAL_ENTITIES_PORTAL_DISPLAY_SIZE);
    merchant.setPageIndex(Constants.ONE);
  }

  private void fetchCountryListMerchantData(HttpSession session, ModelAndView modelAndView) {
	List<Option> countryList = merchantUpdateService.getCountries();
	modelAndView.addObject(Constants.COUNTRY_LIST, countryList);
	List<MerchantData> merchants = new ArrayList<>();
	modelAndView.addObject(Constants.PAGE_SIZE, null);
	modelAndView.addObject(Constants.MERCHANTS_MODEL, merchants);
  }

  @RequestMapping(value = CHATAK_ADMIN_SEARCH_SUB_MERCHANT, method = RequestMethod.POST)
  public ModelAndView subMerchantSearch(HttpServletRequest request, HttpServletResponse response,
      Merchant merchant, BindingResult bindingResult, Map model, HttpSession session) {
    logger.info("Entering:: MerchantController:: showSearchMerchantPage method");
    ModelAndView modelAndView = new ModelAndView(CHATAK_ADMIN_SEARCH_SUB_MERCHANT);
    String userType = (String) session.getAttribute(Constants.LOGIN_USER_TYPE);
    String existingFeature = (String) session.getAttribute(Constants.EXISTING_FEATURES);
    if (userType.equalsIgnoreCase(Constants.ADMIN_VALUE)) {
      if (!existingFeature.contains(FeatureConstants.ADMIN_SERVICE_SUB_MERCHANT_FEATURE_ID)) {
        return setInvalidRequestPage(session, modelAndView);
      }
    } else if (userType.equalsIgnoreCase(Constants.RESELLER) && 
    		!existingFeature.contains(FeatureConstants.RESELLER_SERVICE_SUBMERCHANT_FEATURE_ID)) {
        return setInvalidRequestPage(session, modelAndView);
    }
    modelAndView.addObject(Constants.ERROR, null);
    session.setAttribute(Constants.ERROR, null);
    session.setAttribute(Constants.MERCHANTS_MODEL, merchant);
    session.setAttribute(Constants.PAGE_NUMBER, Constants.ONE);
    merchant.setPageIndex(Constants.ONE);
    LoginResponse loginResponse = (LoginResponse) session.getAttribute(Constants.LOGIN_RESPONSE_DATA);
    try {
      List<Option> countryList = merchantUpdateService.getCountries();
      modelAndView.addObject(Constants.COUNTRY_LIST, countryList);
      MerchantSearchResponse searchResponse = merchantUpdateService.searchSubMerchants(merchant, loginResponse.getUserType(), loginResponse.getEntityId());
      List<MerchantCreateResponse> merchants = new ArrayList<>();
      if (searchResponse != null && !CollectionUtils.isEmpty(searchResponse.getMerchantCreateResponseList())) {
        merchants = searchResponse.getMerchantCreateResponseList();
        modelAndView.addObject(Constants.PAGE_SIZE, merchant.getPageSize());
        modelAndView =
            PaginationUtil.getPagenationModel(modelAndView, searchResponse.getTotalNoOfRows());
      }
      if (searchResponse != null) {
    	  session.setAttribute(Constants.TOTAL_RECORDS, searchResponse.getTotalNoOfRows());
      } else {
		session.setAttribute(Constants.TOTAL_RECORDS, Constants.ZERO);
      }
      modelAndView.addObject(Constants.MERCHANTS_MODEL, merchants);
    } catch (Exception e) {
      modelAndView.addObject(Constants.ERROR,
          messageSource.getMessage(Constants.CHATAK_GENERAL_ERROR, null, LocaleContextHolder.getLocale()));
      logger.error("ERROR:: MerchantController:: showSearchMerchantPage method", e);
    }
    modelAndView.addObject("flag", true);
    model.put(Constants.MERCHANT, merchant);
    logger.info("Exiting:: MerchantController:: showSearchMerchantPage method");
    return modelAndView;
  }
  
	@RequestMapping(value = CHATAK_ADMIN_SEARCH_SUB_MERCHANT, method = RequestMethod.GET)
	public ModelAndView subMerchantSearchGetMethod(HttpServletRequest request, HttpServletResponse response,
			Merchant merchant, BindingResult bindingResult, Map model, HttpSession session) {
		logger.info("Entering:: MerchantController:: showSearchMerchantPage method");
		ModelAndView modelAndView = showSearchSubMerchantPage(request, response, merchant, bindingResult, model, session);
		logger.info("Exiting:: MerchantController:: showSearchMerchantPage method");
		return modelAndView;
	}

  @RequestMapping(value = CHATAK_ADMIN_CREATE_SUB_MERCHANT_PAGE, method = RequestMethod.GET)
  public ModelAndView showCreateSubMerchantPage(Map model, HttpSession session) {
    logger.info("Entering:: MerchantController:: showCreateMerchantPage method");
    ModelAndView modelAndView = new ModelAndView(CHATAK_ADMIN_CREATE_SUB_MERCHANT_PAGE);
    String existingFeature = (String) session.getAttribute(Constants.EXISTING_FEATURES);
    if (!existingFeature.contains(FeatureConstants.ADMIN_SERVICE_SUB_MERCHANT_CREATE_FEATURE_ID)) {
      return setInvalidRequestPage(session, modelAndView);
    }
    modelAndView.addObject(Constants.ERROR, null);
    session.setAttribute(Constants.ERROR, null);
    Merchant subMerchant = new Merchant();
    List<Option> processorNames = null;
    try {
      LoginResponse loginResponse = (LoginResponse) session.getAttribute(Constants.LOGIN_RESPONSE_DATA);
      List<Option> options = merchantValidateService.getFeeProgramNames();
      modelAndView.addObject(Constants.FEE_PROGRAM_NAMES, options);
      List<Option> countryList = merchantUpdateService.getCountries();
      modelAndView.addObject(Constants.COUNTRY_LIST, countryList);

      subMerchant.setMerchantType(PGConstants.SUB_MERCHANT);
      subMerchant.setCategory(PGConstants.PRIMARY_ACCOUNT);
      Long entityId=loginResponse.getEntityId();
      String loginUserType = loginResponse.getUserType();
      Map<String, String> mainMerchantMap =
          merchantAccountService.getMerchantDataMapByMerchantType(PGConstants.MERCHANT, entityId, loginUserType);
      
      List<Option> mainMerchantList = new ArrayList<>();
      Option option = null;
      for (Map.Entry<String, String> entry : mainMerchantMap.entrySet()) {
        option = new Option();
        option.setValue(entry.getKey());
        option.setLabel(entry.getValue());

        mainMerchantList.add(option);
      }
      modelAndView.addObject("mainMerchantList", mainMerchantList);
      processorNames = merchantValidateService.getProcessorNames();
      //To check is email uniqueness is allowed or not
      String isUserMailUnique = Properties.getProperty("prepaid.email.unique.enable");
      model.put("isUserMailUnique", isUserMailUnique);
    } catch (Exception e) {
      modelAndView.addObject(Constants.ERROR,
          messageSource.getMessage(Constants.CHATAK_GENERAL_ERROR, null, LocaleContextHolder.getLocale()));
      logger.error("ERROR:: MerchantController:: showCreateMerchantPage method", e);
    }
    modelAndView.addObject(Constants.PROCESSOR_NAMES, processorNames);
    model.put("subMerchant", subMerchant);
    logger.info("Exiting:: MerchantController:: showCreateMerchantPage method");
    return modelAndView;
  }

  @RequestMapping(value = CHATAK_ADMIN_CREATE_SUB_MERCHANT, method = RequestMethod.POST)
  public ModelAndView createSubMerchant(HttpServletRequest request, HttpServletResponse response,
      Merchant subMerchant, BindingResult bindingResult, Map model, HttpSession session) {
    logger.info("Entering:: MerchantController:: createSubMerchant method");
    ModelAndView modelAndView = new ModelAndView(CHATAK_ADMIN_CREATE_SUB_MERCHANT_PAGE);

    String existingFeature = (String) session.getAttribute(Constants.EXISTING_FEATURES);
    if (!existingFeature.contains(FeatureConstants.ADMIN_SERVICE_SUB_MERCHANT_CREATE_FEATURE_ID)) {
      return setInvalidRequestPage(session, modelAndView);
    }
    modelAndView.addObject(Constants.ERROR, null);

    subMerchant.setMerchantType(PGConstants.SUB_MERCHANT);
    subMerchant.setStatus(PGConstants.STATUS_ACTIVE);

    try {
      Long userid = (Long) session.getAttribute(Constants.LOGIN_USER_ID);
      String url = request.getRequestURL().toString();
      String uri = request.getRequestURI();
      
      String merchantLink = url.substring(0, url.length() - uri.length()) + "/"
          + Properties.getProperty("chatak.merchant.portal");
      subMerchant.setMerchantLink(merchantLink);

      if (StringUtil.isNullAndEmpty(subMerchant.getAgentId())) {
        subMerchant.setIssuancePartnerId(null);
        subMerchant.setProgramManagerId(null);
      }

      AddMerchantResponse addMerchantResponse =
          merchantService.addMerchant(subMerchant, String.valueOf(userid));
      session.getAttribute(Constants.UPDATE_MERCHANT_ID);
      if (null != addMerchantResponse
          && addMerchantResponse.getErrorCode().equals(ActionErrorCode.ERROR_CODE_00)) {
           new Merchant();

        modelAndView = showSearchSubMerchantPage(request, response, new Merchant(), bindingResult,
            model, session);
        modelAndView.addObject(Constants.SUCESS, messageSource.getMessage(
            "chatak.sub.merchant.create.success", null, LocaleContextHolder.getLocale()));
      } else if (null != addMerchantResponse && addMerchantResponse.getErrorCode()
          .equals(ActionErrorCode.ERROR_CODE_DUPLICATE_ENTRY)) {
        modelAndView.addObject(Constants.ERROR, messageSource.getMessage(
            ActionErrorCode.ERROR_CODE_DUPLICATE_ENTRY, null, LocaleContextHolder.getLocale()));
      } else {
        modelAndView.addObject(Constants.ERROR, messageSource.getMessage(
            ActionErrorCode.ERROR_CODE_DUPLICATE_ENTRY, null, LocaleContextHolder.getLocale()));
      }
    } catch (Exception e) {
      logger.error("ERROR:: MerchantController:: createSubMerchant method", e);
      modelAndView.addObject(Constants.ERROR,
          messageSource.getMessage(Constants.CHATAK_GENERAL_ERROR, null, LocaleContextHolder.getLocale()));
    }
    model.put("subMerchant", subMerchant);
    logger.info("Exiting:: MerchantController:: createSubMerchant method");
    return modelAndView;
  }

  @RequestMapping(value = CHATAK_ADMIN_VIEW_MERCHANT_PAGE, method = RequestMethod.POST)
  public ModelAndView showViewSubMerchant(HttpServletRequest request, HttpServletResponse response,
      @FormParam("merchantViewId") final Long merchantViewId,
      @FormParam("merchantType") final String merchantType, HttpSession session, Map model) throws InstantiationException, IllegalAccessException {
    logger.info("Entering :: MerchantController :: showViewSubMerchant method ");
    ModelAndView modelAndView = new ModelAndView(CHATAK_ADMIN_VIEW_MERCHANT_PAGE);
    String userType = (String) session.getAttribute(Constants.LOGIN_USER_TYPE);
    String existingFeature = (String) session.getAttribute(Constants.EXISTING_FEATURES);
    LoginResponse loginResponse = (LoginResponse) session.getAttribute(Constants.LOGIN_RESPONSE_DATA);
    if (userType.equalsIgnoreCase(Constants.ADMIN_VALUE)) {
      if (!existingFeature.contains(FeatureConstants.ADMIN_SERVICE_MERCHANT_VIEW_FEATURE_ID)) {
        return setInvalidRequestPage(session, modelAndView);
      }
    } else if (userType.equalsIgnoreCase(Constants.RESELLER) &&
    		!existingFeature.contains(FeatureConstants.RESELLER_SERVICE_MERCHANT_VIEW_FEATURE_ID)) {
    	return setInvalidRequestPage(session, modelAndView);
    }
    if (merchantType.equalsIgnoreCase(PGConstants.SUB_MERCHANT)) {
      modelAndView.setViewName(CHATAK_ADMIN_VIEW_SUB_MERCHANT);
    }
    Merchant merchant = new Merchant();
    DataBinder dataBinder = new DataBinder(merchant);
    modelAndView.addObject(Constants.ERROR, null);
    modelAndView.addObject(Constants.SUCESS, null);
    List<Option> processorNames = null;
    try {

      List<Option> countryList = merchantUpdateService.getCountries();
      modelAndView.addObject(Constants.COUNTRY_LIST, countryList);
      merchant.setId(merchantViewId);
      merchant = merchantValidateService.getMerchant(merchant);
      if (null == merchant) {
        new Merchant();
        throw new ChatakAdminException();
      } else {
    		MerchantResponse selectedCurrencyList = merchantUpdateService.findByMerchantId(merchant.getId());
    		if (selectedCurrencyList != null) {
    			getCurrencyListResponse(model, merchant, selectedCurrencyList, loginResponse);
    		}
        String bankCurrencyCode = merchant.getBankCurrencyCode();
        Response currencyCodeAlpha = currencyConfigService.getcurrencyCodeAlpha(bankCurrencyCode);
        merchant.setCurrencyId(currencyCodeAlpha.getCurrencyCodeAlpha());

        List<Option> options =
            merchantValidateService.getFeeProgramNamesForEdit(merchant.getFeeProgram());
        modelAndView.addObject(Constants.FEE_PROGRAM_NAMES, options);

        Response stateList = merchantUpdateService.getStatesByCountry(merchant.getCountry());
        modelAndView.addObject(Constants.STATE_LIST, stateList.getResponseList());
        session.setAttribute(Constants.STATE_LIST, new ArrayList(stateList.getResponseList()));

        Response agentnamesList = merchantUpdateService.getAgentNames(merchant.getLocalCurrency());
        if (agentnamesList != null) {
          modelAndView.addObject(Constants.AGENT_NAMES_LIST, agentnamesList.getResponseList());
          session.setAttribute(Constants.AGENT_NAMES_LIST, new ArrayList(agentnamesList.getResponseList()));
        }

        stateList = merchantUpdateService.getStatesByCountry(merchant.getBankCountry());
        modelAndView.addObject(Constants.BANK_STATE_LIST, stateList.getResponseList());
        session.setAttribute(Constants.BANK_STATE_LIST, new ArrayList(stateList.getResponseList()));

        session.setAttribute(Constants.UPDATE_MERCHANT_ID, merchantViewId);
        merchant.setMerchantFlag(true);
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
      modelAndView = merchantController.showSearchMerchantPage(request, response, merchant,
          dataBinder.getBindingResult(), model, session);
      modelAndView.addObject(Constants.ERROR,
          messageSource.getMessage(Constants.CHATAK_GENERAL_ERROR, null, LocaleContextHolder.getLocale()));
      modelAndView.addObject(Constants.MERCHANT, new Merchant());
      logger.error("ERROR:: MerchantController:: showViewSubMerchant method", e);
    }
    modelAndView.addObject(Constants.PROCESSOR_NAMES, processorNames);
    model.put(Constants.MERCHANT, merchant);
    if(merchant != null) {
    	session.setAttribute("parentMerchantId", merchant.getParentMerchantId());
    }
    logger.info("EXITING :: MerchantController :: showViewSubMerchant");
    return modelAndView;
  }

	/**
	 * @param model
	 * @param merchant
	 * @param selectedCurrencyList
	 */
	private void getCurrencyListResponse(Map model, Merchant merchant, MerchantResponse selectedCurrencyList, LoginResponse loginResponse) {
		merchant.setAssociatedTo(selectedCurrencyList.getMerchant().getAssociatedTo());
		Response programManagerResponse = null;
		if(loginResponse.getUserType().equals(Constants.PM_USER_TYPE)){
			if(merchant.getAssociatedTo() != null && merchant.getAssociatedTo().equals(PGConstants.PROGRAM_MANAGER_NAME)){
				programManagerResponse = programManagerService.findProgramManagerNameAndIdByEntityId(loginResponse.getEntityId(), merchant.getId());
				List<Option> options = new ArrayList<>(1);
				Option option = new Option();
				if (programManagerResponse.getId() != null) {
					option.setValue(programManagerResponse.getId().toString());
				}
				option.setLabel(programManagerResponse.getProgramManagerName());
				options.add(option);
				programManagerResponse.setResponseList(options);
				model.put("selectedEntityList", selectedCurrencyList.getProgramManagerRequests());
			} else{
				programManagerResponse = isoService.findIsoNameAndIdByEntityId(loginResponse.getEntityId());
				model.put("selectedEntityList", selectedCurrencyList.getIsoRequests());
			}
		} else {
			if (merchant.getAssociatedTo() != null && merchant.getAssociatedTo().equals(PGConstants.PROGRAM_MANAGER_NAME)) {
				programManagerResponse = programManagerService
						.findProgramManagerNameByCurrencyAndId(merchant.getId(), merchant.getLocalCurrency());
				model.put("selectedEntityList", selectedCurrencyList.getProgramManagerRequests());
			} else {
				programManagerResponse = isoService.findIsoNameByCurrencyAndId(merchant.getId(),
						merchant.getLocalCurrency());
				model.put("selectedEntityList", selectedCurrencyList.getIsoRequests());
			}
		}
		model.put(Constants.MERCHANT, selectedCurrencyList.getMerchant());
		model.put("EntityList", programManagerResponse.getResponseList());
		model.put("selectedCardProgramList", selectedCurrencyList.getCardProgramRequests());
	}

  @RequestMapping(value = CHATAK_ADMIN_SUB_MERCHANTS_PAGINATION, method = RequestMethod.POST)
  public ModelAndView getSubMerchantPaginationList(final HttpSession session,
      @FormParam(Constants.PAGE_NUMBER) final Integer pageNumber,
      @FormParam(Constants.TOTAL_RECORDS) final Integer totalRecords, Map model) {
    logger.info("Entering:: MerchantController:: getSubMerchantPaginationList method");
    ModelAndView modelAndView = new ModelAndView(CHATAK_ADMIN_SEARCH_SUB_MERCHANT);
    Merchant subMerchant = null;
    LoginResponse loginResponse = (LoginResponse) session.getAttribute(Constants.LOGIN_RESPONSE_DATA);
    try {
      subMerchant = (Merchant) session.getAttribute(Constants.MERCHANTS_MODEL);
      model.put(Constants.MERCHANT, subMerchant);
      subMerchant.setPageIndex(pageNumber);
      subMerchant.setNoOfRecords(totalRecords);
      MerchantSearchResponse searchResponse = merchantUpdateService.searchSubMerchants(subMerchant, loginResponse.getUserType(), loginResponse.getEntityId());
      if (searchResponse != null && !CollectionUtils.isEmpty(searchResponse.getMerchantCreateResponseList())) {
    	List<MerchantCreateResponse> submerchants = searchResponse.getMerchantCreateResponseList();
        modelAndView.addObject(Constants.PAGE_SIZE, subMerchant.getPageSize());
        modelAndView = PaginationUtil.getPagenationModelSuccessive(modelAndView, pageNumber,
            searchResponse.getTotalNoOfRows());
        session.setAttribute(Constants.PAGE_NUMBER, pageNumber);
        session.setAttribute(Constants.TOTAL_RECORDS, totalRecords);
        modelAndView.addObject(Constants.MERCHANTS_MODEL, submerchants);
      }
    } catch (Exception e) {
      logger.error("ERROR:: MerchantController:: getSubMerchantPaginationList method", e);
      modelAndView.addObject(Constants.ERROR,
          messageSource.getMessage(Constants.CHATAK_GENERAL_ERROR, null, LocaleContextHolder.getLocale()));
    }
    logger.info("Exiting:: MerchantController:: getSubMerchantPaginationList method");
    return modelAndView;
  }

  @RequestMapping(value = CHATAK_ADMIN_SUB_MERCHANT_REPORT, method = RequestMethod.POST)
  public ModelAndView downloadSubMerchantReport(HttpSession session, Map model,
      HttpServletRequest request, HttpServletResponse response,
      @FormParam("downLoadPageNumber") final Integer downLoadPageNumber,
      @FormParam(Constants.TOTAL_RECORDS) final Integer totalRecords,
      @FormParam("downloadAllRecords") final boolean downloadAllRecords) {
    logger.info("Entering:: MerchantController:: downloadSubMerchantReport method");
    ModelAndView modelAndView = new ModelAndView(CHATAK_ADMIN_SEARCH_SUB_MERCHANT);
    Merchant merchant = null;
    LoginResponse loginResponse = (LoginResponse) session.getAttribute(Constants.LOGIN_RESPONSE_DATA);
    try {
      String downloadType=request.getParameter("downloadType");
      merchant = (Merchant) session.getAttribute(Constants.MERCHANTS_MODEL);
      merchant.setPageIndex(downLoadPageNumber);
      Integer pageSize = merchant.getPageSize();
      if (downloadAllRecords) {
        merchant.setPageIndex(Constants.ONE);
        merchant.setPageSize(totalRecords);
      }
      MerchantSearchResponse searchResponse = merchantUpdateService.searchSubMerchants(merchant, loginResponse.getUserType(), loginResponse.getEntityId());
      List<MerchantCreateResponse> list = searchResponse.getMerchantCreateResponseList();
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
      modelAndView.addObject(Constants.ERROR,
          messageSource.getMessage(Constants.CHATAK_GENERAL_ERROR, null, LocaleContextHolder.getLocale()));
      logger.error("ERROR:: MerchantController:: downloadMerchantReport method", e);
    }
    logger.info("Exiting:: MerchantController:: downloadMerchantReport method");
    return null;
  }

  private void setExportDetailsDataForDownloadRoleReport(List<MerchantCreateResponse> list,
      ExportDetails exportDetails) {
    exportDetails.setReportName("Sub_Merchant_");
    exportDetails.setHeaderMessageProperty("chatak.header.sub.merchant.messages");
    exportDetails.setHeaderList(getRoleHeaderList());
    exportDetails.setFileData(getRoleFileData(list));
  }
  
  private List<String> getRoleHeaderList() {
    String[] headerArr = {
        messageSource.getMessage("manage.label.sub-merchant.submerchantcode", null,
            LocaleContextHolder.getLocale()),
        messageSource.getMessage("merchant.label.merchantname", null,
            LocaleContextHolder.getLocale()),
        messageSource.getMessage("currency-search-page.label.currencycode", null,
            LocaleContextHolder.getLocale()),
        messageSource.getMessage("merchant-file-exportutil-email", null,
            LocaleContextHolder.getLocale()),
        messageSource.getMessage("merchant-file-exportutil-phone", null,
                LocaleContextHolder.getLocale()),
        messageSource.getMessage("merchant-file-exportutil-country", null,
            LocaleContextHolder.getLocale()),
        messageSource.getMessage("merchant-file-exportutil-status", null,
            LocaleContextHolder.getLocale())};
    return new ArrayList<String>(Arrays.asList(headerArr));
  }
  
  private static List<Object[]> getRoleFileData(List<MerchantCreateResponse> merchantData) {
    List<Object[]> fileData = new ArrayList<Object[]>();
    for (MerchantCreateResponse merData : merchantData) {
      Object[] rowData = new Object[Integer.parseInt("7")];
      rowData[0] = merData.getMerchantCode();
      rowData[1] = merData.getBusinessName();
      rowData[Integer.parseInt("2")] = merData.getLocalCurrency();
      rowData[Integer.parseInt("3")] = merData.getEmailId();
      rowData[Integer.parseInt("4")] = merData.getPhone();
      rowData[Integer.parseInt("5")] = merData.getCountry();
      rowData[Integer.parseInt("6")] = merData.getStatus();
      fileData.add(rowData);
    }
    return fileData;
  }
  
  /**
   * Method to update merchant page
   * 
   * @param model
   * @param session
   * @return
   */
  @RequestMapping(value = CHATAK_ADMIN_UPDATE_SUB_MERCHANT, method = RequestMethod.POST)
  public ModelAndView updateSubMerchant(HttpServletRequest request, HttpServletResponse response,
      Merchant merchant, BindingResult bindingResult, HttpSession session, Map model) {
    logger.info("Entering :: MerchantController :: updateMerchant method");
    ModelAndView modelAndView = new ModelAndView(CHATAK_ADMIN_SEARCH_SUB_MERCHANT);

    String existingFeature = (String) session.getAttribute(Constants.EXISTING_FEATURES);
    if (!existingFeature.contains(FeatureConstants.ADMIN_SERVICE_SUB_MERCHANT_EDIT_FEATURE_ID)) {
      return setInvalidRequestPage(session, modelAndView);
    }
    modelAndView.addObject(Constants.ERROR, null);
    session.setAttribute(Constants.ERROR, null);
    merchant.setPageSize(Constants.MAX_ENTITIES_PORTAL_DISPLAY_SIZE);
    merchant.setPageIndex(Constants.ONE);
    merchant.setStatus(PGConstants.STATUS_ACTIVE);
    merchant.setMerchantType(PGConstants.SUB_MERCHANT);
    try {
      if (StringUtil.isNullAndEmpty(merchant.getAgentId())) {
        merchant.setIssuancePartnerId(null);
        merchant.setProgramManagerId(null);
      }
      merchant.setCategory(PGConstants.PRIMARY_ACCOUNT);
      UpdateMerchantResponse updateMerchantResponse = merchantService.updateMerchant(merchant);
      if (updateMerchantResponse.isUpdated()
          && updateMerchantResponse.getErrorCode().equals(ActionErrorCode.ERROR_CODE_00)) {
        Merchant merchantData = new Merchant();
        modelAndView = showSearchSubMerchantPage(request, response, merchantData, bindingResult,
            model, session);
        modelAndView.addObject(Constants.SUCESS, messageSource.getMessage(
            "chatak.submerchant.update.success", null, LocaleContextHolder.getLocale()));
        modelAndView.addObject(Constants.MERCHANT, merchantData);
      } else {
        modelAndView = showEditSubMerchant(request, response, merchant.getId(), session, model);
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

  @RequestMapping(value = CHATAK_ADMIN_SHOW_EDIT_SUB_MERCHANT, method = RequestMethod.POST)
  public ModelAndView showEditSubMerchant(HttpServletRequest request, HttpServletResponse response,
      @FormParam("merchantEditId") final Long merchantEditId, HttpSession session, Map model) {
    logger.info("Entering :: MerchantController :: showEditMerchant method ");
    ModelAndView modelAndView = new ModelAndView(CHATAK_ADMIN_EDIT_SUB_MERCHANT);

    String existingFeature = (String) session.getAttribute(Constants.EXISTING_FEATURES);
    if (!existingFeature.contains(FeatureConstants.ADMIN_SERVICE_SUB_MERCHANT_EDIT_FEATURE_ID)) {
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
      merchant.setId(merchantEditId);
      merchant = merchantValidateService.getMerchant(merchant);
      if (null == merchant) {
        merchant = new Merchant();
        throw new ChatakAdminException();
      } else {
        List<Option> options =
            merchantValidateService.getFeeProgramNamesForEdit(merchant.getFeeProgram());
        modelAndView.addObject(Constants.FEE_PROGRAM_NAMES, options);

        Response agentnamesList = merchantUpdateService.getAgentNames(merchant.getLocalCurrency());
        if (agentnamesList != null) {
          modelAndView.addObject(Constants.AGENT_NAMES_LIST, agentnamesList.getResponseList());
          session.setAttribute(Constants.AGENT_NAMES_LIST, new ArrayList(agentnamesList.getResponseList()));
        }

        Response stateList = merchantUpdateService.getStatesByCountry(merchant.getCountry());
        modelAndView.addObject(Constants.STATE_LIST, stateList.getResponseList());
        session.setAttribute(Constants.STATE_LIST, new ArrayList(stateList.getResponseList()));

        stateList = merchantUpdateService.getStatesByCountry(merchant.getBankCountry());
        modelAndView.addObject(Constants.BANK_STATE_LIST, stateList.getResponseList());
        session.setAttribute(Constants.BANK_STATE_LIST, new ArrayList(stateList.getResponseList()));

        session.setAttribute(Constants.UPDATE_MERCHANT_ID, merchantEditId);
        merchant.setMerchantFlag(true);
        modelAndView.addObject(Constants.MERCHANT, merchant);
        session.setAttribute("parentMerchantId", merchant.getParentMerchantId());
        processorNames = merchantValidateService.getProcessorNames();

        Map<String, String> mainMerchantMap =
            merchantAccountService.getMerchantMapByMerchantType(PGConstants.MERCHANT);
        List<Option> mainMerchantList = new ArrayList<>();
        Option option = null;
        for (Map.Entry<String, String> entry : mainMerchantMap.entrySet()) {
          option = new Option();
          option.setValue(entry.getKey());
          option.setLabel(entry.getValue());
          mainMerchantList.add(option);
        }
        modelAndView.addObject("mainMerchantList", mainMerchantList);
        modelAndView.addObject("accountNumber", merchant.getAccountId());
        session.setAttribute("accountNumber", merchant.getAccountId());
      }
    } catch (ChatakAdminException e) {
      logger.error("ERROR:: MerchantController:: showEditMerchant method", e);
      modelAndView = merchantController.showSearchMerchantPage(request, response, merchant,
          dataBinder.getBindingResult(), model, session);
      modelAndView.addObject(Constants.MERCHANT, new Merchant());
      modelAndView.addObject(Constants.ERROR,
          messageSource.getMessage(Constants.CHATAK_GENERAL_ERROR, null, LocaleContextHolder.getLocale()));
    } catch (Exception e) {
      logger.error("ERROR:: MerchantController:: showEditMerchant method", e);
      modelAndView.addObject(Constants.ERROR,
          messageSource.getMessage(Constants.CHATAK_GENERAL_ERROR, null, LocaleContextHolder.getLocale()));
    }
    modelAndView.addObject(Constants.PROCESSOR_NAMES, processorNames);
    model.put(Constants.MERCHANT, merchant);
    logger.info("EXITING :: MerchantController :: showEditMerchant");
    return modelAndView;
  }

  @RequestMapping(value = CHATAK_ADMIN_DELETE_MERCHANT, method = RequestMethod.POST)
  public ModelAndView deleteMerchant(HttpServletRequest request, HttpServletResponse response,
      @FormParam("getMerchantsId") final Long getMerchantsId,
      Merchant merchant,
      BindingResult bindingResult, HttpSession session, Map model) {
    String merchantsType=request.getParameter("merchantsType");
    logger.info("Entering :: MerchantController :: deleteMerchant method ");
    ModelAndView modelAndView = new ModelAndView(CHATAK_ADMIN_SEARCH_MERCHANT_PAGE);

    String existingFeature = (String) session.getAttribute(Constants.EXISTING_FEATURES);
    if (!existingFeature.contains(FeatureConstants.ADMIN_SERVICE_MERCHANT_DELETE_FEATURE_ID)) {
      return setInvalidRequestPage(session, modelAndView);
    }
    if (merchantsType.equalsIgnoreCase(PGConstants.SUB_MERCHANT)) {
      modelAndView.setViewName(CHATAK_ADMIN_SEARCH_SUB_MERCHANT);
    }
    modelAndView.addObject(Constants.ERROR, null);
    modelAndView.addObject(Constants.SUCESS, null);
    try {
      merchant.setId(getMerchantsId);
      DeleteMerchantResponse deleteMerchantResponse =
          merchantUpdateService.deleteMerchant(getMerchantsId);
      if (deleteMerchantResponse.isIsdeleated()) {
    	  if(merchantsType.equalsIgnoreCase(PGConstants.MERCHANT)) {
    		  modelAndView = merchantController.showSearchMerchantPage(request, response, merchant, bindingResult, model, session);
    		  modelAndView.addObject(Constants.SUCESS, messageSource.getMessage("chatak.merchant.delete.success", null, LocaleContextHolder.getLocale()));
    	  } else if(merchantsType.equalsIgnoreCase(PGConstants.SUB_MERCHANT)) {
    		  modelAndView = showSearchSubMerchantPage(request, response, merchant, bindingResult, model, session);
    		  modelAndView.addObject(Constants.SUCESS, messageSource.getMessage("chatak.submerchant.delete.failure", null, LocaleContextHolder.getLocale()));
    	  }
      } else {
        modelAndView = (merchantsType.equalsIgnoreCase(PGConstants.SUB_MERCHANT))
            ? showSearchSubMerchantPage(request, response, merchant, bindingResult, model, session)
            : merchantController.showSearchMerchantPage(request, response, merchant, bindingResult,
                model, session);
        modelAndView.addObject(Constants.ERROR, deleteMerchantResponse.getErrorMessage());
      }
    } catch (Exception e) {
      logger.error("ERROR:: MerchantController:: deleteMerchant method", e);
      modelAndView.addObject(Constants.ERROR,
          messageSource.getMessage(Constants.CHATAK_GENERAL_ERROR, null, LocaleContextHolder.getLocale()));
    }
    modelAndView.addObject(Constants.MERCHANT, merchant);
    logger.info("EXITING :: MerchantController :: deleteMerchant");
    return modelAndView;
  }

  @RequestMapping(value = CHATAK_ADMIN_SUB_MERCHANT_STATUS_CHANGE, method = RequestMethod.POST)
  public ModelAndView changeSubMerchantStatus(final HttpSession session,
      @FormParam("suspendActiveId") final Long subMerchantId,
      @FormParam("suspendActiveStatus") final String subMerchantStatus,
      @FormParam("reason") final String reason, Map<String, Serializable> model,
      HttpServletRequest request, HttpServletResponse response) {
    logger.info("Entering:: MerchantController:: changeSubMerchantStatus method");

    ModelAndView modelAndView = new ModelAndView(CHATAK_SUB_MERCHANT_ACCOUNT_SEARCH_PAGE);
    String existingFeature = (String) session.getAttribute(Constants.EXISTING_FEATURES);
    if (!existingFeature.contains(FeatureConstants.ADMIN_SERVICE_SUB_MERCHANT_FEATURE_ID)) {
      return setInvalidRequestPage(session, modelAndView);
    }
    try {
      MerchantData merchantData = new MerchantData();
      merchantData.setId(subMerchantId);
      merchantData.setStatus(subMerchantStatus);
      merchantData.setReason(reason);
      Response merchantResponse = merchantValidateService.changeMerchantStatus(merchantData);
      modelAndView = getSubMerchantPaginationList(session,
          StringUtil.isNull((Integer) session.getAttribute(Constants.PAGE_NUMBER)) ? 1
              : (Integer) session.getAttribute(Constants.PAGE_NUMBER),
          (Integer) session.getAttribute(Constants.TOTAL_RECORDS), model);
      if (ActionErrorCode.ERROR_CODE_00.equals(merchantResponse.getErrorCode())) {
        model.put(Constants.SUCESS, merchantResponse.getErrorMessage());
      } else {
        model.put(Constants.ERROR, merchantResponse.getErrorMessage());
      }
    } catch (Exception e) {
      logger.error("ERROR:: MerchantController:: changeSubMerchantStatus method", e);
      model.put(Constants.ERROR,
          messageSource.getMessage(Constants.CHATAK_GENERAL_ERROR, null, LocaleContextHolder.getLocale()));
    }
    logger.info("Exiting:: MerchantController:: changeSubMerchantStatus method");
    return modelAndView;
  }
}
