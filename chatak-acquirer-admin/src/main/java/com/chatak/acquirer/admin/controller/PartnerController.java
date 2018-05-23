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
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.chatak.acquirer.admin.constants.FeatureConstants;
import com.chatak.acquirer.admin.constants.URLMappingConstants;
import com.chatak.acquirer.admin.controller.model.ExportDetails;
import com.chatak.acquirer.admin.controller.model.LoginResponse;
import com.chatak.acquirer.admin.controller.model.Option;
import com.chatak.acquirer.admin.exception.ChatakAdminException;
import com.chatak.acquirer.admin.service.BankService;
import com.chatak.acquirer.admin.service.CurrencyConfigService;
import com.chatak.acquirer.admin.service.MerchantUpdateService;
import com.chatak.acquirer.admin.service.PartnerService;
import com.chatak.acquirer.admin.service.ProgramManagerService;
import com.chatak.acquirer.admin.util.ExportUtil;
import com.chatak.acquirer.admin.util.JsonUtil;
import com.chatak.acquirer.admin.util.PaginationUtil;
import com.chatak.pg.bean.Request;
import com.chatak.pg.bean.Response;
import com.chatak.pg.dao.util.StringUtil;
import com.chatak.pg.enums.ExportType;
import com.chatak.pg.exception.PrepaidException;
import com.chatak.pg.user.bean.BankPartnerMapRequest;
import com.chatak.pg.user.bean.BankRequest;
import com.chatak.pg.user.bean.BankResponse;
import com.chatak.pg.user.bean.PartnerRequest;
import com.chatak.pg.user.bean.PartnerResponse;
import com.chatak.pg.user.bean.ProgramManagerRequest;
import com.chatak.pg.user.bean.ProgramManagerResponse;
import com.chatak.pg.util.Constants;

@SuppressWarnings("unchecked")
@Controller
public class PartnerController implements URLMappingConstants {
  private static Logger logger = Logger.getLogger(PartnerController.class);

  @Autowired
  MessageSource messageSource;

  @Autowired
  ProgramManagerService programManagerService;

  @Autowired
  PartnerService partnerService;

  @Autowired
  MerchantUpdateService merchantUpdateService;

  @Autowired
  CurrencyConfigService currencyConfigService;

  @Autowired
  BankService bankService;

  /**
   * Method to display the Create Partner
   * 
   * @param request
   * @param response
   * @param model
   * @param session
   * @return
   */
  @RequestMapping(value = SHOW_PREPAID_ADMIN_CREATE_PARTNER, method = RequestMethod.GET)
  public ModelAndView showCreatePartner(HttpServletRequest request, HttpServletResponse response,
      Map<String, Object> model, HttpSession session) {
    logger.info("Entering:: PartnerController:: showCreatePartner method");
    model.put("flag", true);
    ModelAndView modelAndView = new ModelAndView(PREPAID_ADMIN_CREATE_PARTNER_PAGE);

    String existingFeature = (String) session.getAttribute(Constants.EXISTING_FEATURES);
    if (!existingFeature.contains(FeatureConstants.ADMIN_SERVICE_PARTNER_CREATE_FEATURE_ID)) {
      session.invalidate();
      modelAndView.setViewName(INVALID_REQUEST_PAGE);
      return modelAndView;
    }
    try {
      ProgramManagerResponse programManagerResponse = null;
      PartnerRequest partnerRequest = new PartnerRequest();
      ProgramManagerRequest programManagerRequest = new ProgramManagerRequest();
      programManagerRequest.setStatuses(Arrays.asList(Constants.ACTIVE));
      programManagerResponse = programManagerService.getAllProgramManagers(programManagerRequest);
      if (!StringUtil.isNull(programManagerResponse)) {
        model.put("programManagersList", programManagerResponse.getProgramManagersList());
      }

      //there can be no default program manager into the system
      Long defaultPmId = !StringUtil.isNull(session.getAttribute(Constants.DEFAULT_PM_ID))
          ? (Long) session.getAttribute(Constants.DEFAULT_PM_ID) : null;
      programManagerRequest.setId(defaultPmId);
      programManagerRequest.setStatuses(Arrays.asList(Constants.ACTIVE));
      BankResponse bankResponse =
          programManagerService.findBankByProgramManagerId(programManagerRequest);
      if (null != bankResponse && StringUtil.isListNotNullNEmpty(bankResponse.getBankRequests())) {
        model.put("bankList", bankResponse.getBankRequests());
      }

      //Get the currency for the default Program Manager
      logger.info("Entering:: getDefaultPmCurrency method");
      getDefaultPmCurrency(defaultPmId, programManagerResponse, programManagerRequest, model);

      //To get countries
      logger.info("Entering:: getCountryForCreate method");
      getCountryForCreate(model);
      model.put("partnerRequest", partnerRequest);
    } catch (Exception e) {
      logger.error("ERROR:: PartnerController:: showCreatePartner method", e);
    }

    logger.info("Exiting:: PartnerController:: showCreatePartner method");
    return modelAndView;
  }

  /**
   * Method used for Create Partner
   * 
   * @param request
   * @param response
   * @param model
   * @param partnerRequest
   * @return
   */

  @RequestMapping(value = PREPAID_ADMIN_CREATE_PARTNER, method = RequestMethod.POST)
  public ModelAndView processCreatePartner(HttpServletRequest request, HttpServletResponse response,
      PartnerRequest partnerRequest, BindingResult bindingResult, Map<String, Object> model,
      HttpSession session, @RequestParam("partnerLogo") MultipartFile file) {

    logger.info("Entering:: PartnerController:: processCreatePartner method");
    byte[] bytes = null;

    if (!file.isEmpty()) {
      try {
        bytes = file.getBytes();
      } catch (Exception e) {
        logger.error("ERROR:: file :: PartnerController:: processCreatePartner method", e);
      }
    }
    ModelAndView modelAndView = new ModelAndView(PREPAID_ADMIN_SEARCH_PARTNER_PAGE);
    String existingFeature = (String) session.getAttribute(Constants.EXISTING_FEATURES);
    if (!(existingFeature.contains(FeatureConstants.ADMIN_SERVICE_PARTNER_CREATE_FEATURE_ID))) {
      session.invalidate();
      modelAndView.setViewName(INVALID_REQUEST_PAGE);
      return modelAndView;
    }

    try {
      setEnumValuesForSearchPage(model);
      logger.info("Entering:: getCountryState method");
      Response stateList = merchantUpdateService.getStatesByCountry(partnerRequest.getCountry());
      modelAndView.addObject(Constants.STATE_LIST, stateList.getResponseList());
      session.setAttribute(Constants.STATE_LIST, stateList.getResponseList());

      logger.info("Entering:: getBankDetails method");
      getBankDetails(partnerRequest);
      partnerRequest.setPartnerType(Constants.NORMAL_PARTNER);
      partnerRequest.setCreatedBy(session.getAttribute(Constants.LOGIN_USER_ID).toString());
      partnerRequest.setPartnerLogo(bytes);
      setAuditData(partnerRequest, session, "Partner", "Create");
      Response partnerCreateResponse = partnerService.createPartner(partnerRequest);
      if (partnerCreateResponse != null
          && partnerCreateResponse.getErrorCode().equals(Constants.STATUS_CODE_SUCCESS)) {
        modelAndView = showPartner(request, response, model, session);
        modelAndView.addObject(Constants.SUCESS, messageSource.getMessage(
            "prepaid.admin.addpartner.success.message", null, LocaleContextHolder.getLocale()));
      } else if (partnerCreateResponse != null && (partnerCreateResponse.getErrorCode()
          .equals(Constants.PARTNER_ALREADY_EXISTS_WITH_SAME_NAME)
          || partnerCreateResponse.getErrorCode()
              .equals(Constants.PARTNER_ALREADY_EXISTS_WITH_SAME_CLIENT_ID))) {
        modelAndView = showCreatePartner(request, response, model, session);
        modelAndView.addObject(Constants.ERROR, partnerCreateResponse.getErrorMessage());
      }

    } catch (Exception e) {
      logger.error("ERROR:: PartnerController:: processCreatePartner method3", e);
      model.put(Constants.ERROR, messageSource.getMessage("prepaid.admin.general.error.message",
          null, LocaleContextHolder.getLocale()));
    }

    logger.info("Exiting  :: PartnerController:: processCreatePartner method");
    return modelAndView;
  }

  /**
   * Method for reload the Search Partner after Create partner
   * 
   * @param request
   * @param response
   * @param model
   * @param session
   * @return
   */
  @RequestMapping(value = SHOW_PREPAID_ADMIN_SEARCH_PARTNER, method = RequestMethod.GET)
  public ModelAndView showPartner(HttpServletRequest request, HttpServletResponse response,
      Map<String, Object> model, HttpSession session) {
    logger.info("Entering:: PartnerController:: showPartner method");
    model.put("flag", true);
    ModelAndView modelAndView = new ModelAndView(PREPAID_ADMIN_SEARCH_PARTNER_PAGE);

    try {
      String existingFeature = (String) session.getAttribute(Constants.EXISTING_FEATURES);
      if (!existingFeature.contains(FeatureConstants.ADMIN_SERVICE_PARTNER_FEATURE_ID)) {
        session.invalidate();
        modelAndView.setViewName(INVALID_REQUEST_PAGE);
        return modelAndView;
      }
      setEnumValuesForSearchPage(model);
      LoginResponse loginResponse =
          (LoginResponse) session.getAttribute(Constants.LOGIN_RESPONSE_DATA);
      PartnerRequest partnerRequest = new PartnerRequest();
      ProgramManagerRequest programManagerRequest = new ProgramManagerRequest();
      programManagerRequest.setStatuses(Arrays.asList(Constants.ACTIVE, Constants.ACC_SUSPENDED));
      getPmAndBankList(programManagerRequest, model);
      model.put("userType", loginResponse.getUserType());
      model.put("partnerRequest", partnerRequest);
      model.put("partnerResponse", "yes");

    } catch (PrepaidException e) {
      logger.error("ERROR:: PartnerController:: showPartner method1", e);
      model.put(Constants.ERROR, e.getMessage());
    } catch (Exception e) {
      logger.error("ERROR:: PartnerController:: showPartner method2", e);
      model.put(Constants.ERROR, messageSource.getMessage("prepaid.admin.general.error.message",
          null, LocaleContextHolder.getLocale()));
    }
    logger.info("Exit:: PartnerController:: showPartner method");
    return modelAndView;
  }

  /**
   * Method for Search Partner
   * 
   * @param request
   * @param response
   * @param model
   * @param session
   * @return
   */
  @RequestMapping(value = PREPAID_ADMIN_SEARCH_PARTNER, method = RequestMethod.POST)
  public ModelAndView searchPartner(HttpServletRequest request, PartnerRequest partnerRequest,
      HttpServletResponse response, Map<String, Object> model, HttpSession session) {
    logger.info("Entering:: PartnerController:: searchPartner method");
    model.put("flag", false);
    ModelAndView modelAndView = new ModelAndView(PREPAID_ADMIN_SEARCH_PARTNER_PAGE);
    String existingFeature = (String) session.getAttribute(Constants.EXISTING_FEATURES);
    if (!(existingFeature.contains(FeatureConstants.ADMIN_SERVICE_PARTNER_FEATURE_ID))) {
      session.invalidate();
      modelAndView.setViewName(INVALID_REQUEST_PAGE);
      return modelAndView;
    }
    try {
      setEnumValuesForSearchPage(model);
      LoginResponse loginResponse =
          (LoginResponse) session.getAttribute(Constants.LOGIN_RESPONSE_DATA);
      model.put("userType", loginResponse.getUserType());
      ProgramManagerRequest programManagerRequest = new ProgramManagerRequest();

      partnerRequest.setPageIndex(Constants.ONE);
      if (partnerRequest.getPageSize() != null) {
        partnerRequest.setPageSize(partnerRequest.getPageSize());
      } else {
        partnerRequest.setPageSize(Constants.MAX_ENTITIES_PAGINATION_DISPLAY_SIZE);
      }
      partnerRequest.setStatusList(Arrays.asList(Constants.ACTIVE, Constants.ACC_SUSPENDED));
      programManagerRequest.setStatuses(Arrays.asList(Constants.ACTIVE, Constants.ACC_SUSPENDED));
      session.setAttribute(Constants.PARTNER_REQUEST_LIST_EXPORTDATA, partnerRequest);

      logger.info("Entering:: getPmAndBankList method");
      getPmAndBankList(programManagerRequest, model);
      logger.info("Entering:: addPartnerList method");
      modelAndView = addPartnerList(partnerRequest, session, model);

    } catch (PrepaidException e) {
      logger.error("ERROR:: PartnerController:: searchPartner method1", e);
      model.put(Constants.ERROR, e.getMessage());
    } catch (Exception e) {
      logger.error("ERROR:: PartnerController:: searchPartner method2", e);
      model.put(Constants.ERROR, messageSource.getMessage("prepaid.admin.general.error.message",
          null, LocaleContextHolder.getLocale()));
    }
    logger.info("Exit:: PartnerController:: searchPartner method");
    return modelAndView;

  }

  /**
   * Method for edit partner
   * 
   * @param request
   * @param response
   * @param model
   * @param session
   * @return
   */
  @RequestMapping(value = SHOW_PREPAID_ADMIN_EDIT_PARTNER, method = RequestMethod.POST)
  public ModelAndView showEditPartner(HttpServletRequest request,
      @FormParam("partnerId") final Long partnerId, @FormParam("managerId") final Long managerId,
      PartnerRequest partnerRequest, HttpServletResponse response, Map<String, Object> model,
      HttpSession session) {
    logger.info("Entering:: PartnerController:: showEditPartner method");
    model.put("flag", false);

    ModelAndView modelAndView = new ModelAndView(PREPAID_ADMIN_EDIT_PARTNER_PAGE);
    String existingFeature = (String) session.getAttribute(Constants.EXISTING_FEATURES);
    if (!(existingFeature.contains(FeatureConstants.ADMIN_SERVICE_PARTNER_EDIT_FEATURE_ID))) {
      session.invalidate();
      modelAndView.setViewName(INVALID_REQUEST_PAGE);
      return modelAndView;
    }
    try {
      ProgramManagerRequest programManagerRequest = new ProgramManagerRequest();
      programManagerRequest.setId(managerId);
      partnerRequest.setPartnerId(partnerId);
      setAuditData(partnerRequest, session, "Partner", "View");
      PartnerResponse partnerResponse = partnerService.findByPartnerId(partnerRequest);
      PartnerRequest partner = partnerResponse.getPartnerList().get(0);
      if (partner.getWhiteListIPAddress() != null) {
        partner.setDefaultPartnerAPI(true);
      } else {
        partner.setDefaultPartnerAPI(false);
      }
      List<PartnerRequest> partnerList = partnerResponse.getPartnerList();
      List<BankRequest> bankList = partnerList.get(0).getBankRequests();
      if (bankList != null) {
        model.put("selectedbankList", bankList);
      }
      BankResponse bankResponse =
          programManagerService.findBankByProgramManagerId(programManagerRequest);
      model.put("pgmngBankList", bankResponse.getBankRequests());

      partnerList.get(0).getProgramManagerRequest()
          .setProgramManagerName(com.chatak.acquirer.admin.util.StringUtil.escapeHTMLChars(
              partnerList.get(0).getProgramManagerRequest().getProgramManagerName()));
      partnerList.get(0).setPartnerName(com.chatak.acquirer.admin.util.StringUtil
          .escapeHTMLChars(partnerList.get(0).getPartnerName()));
      model.put("partnerRequest", partnerList.get(0));

      // to get the list of currencycurrencyList
      List<Option> currencyCodeList = currencyConfigService.getCurrencyConfigCode();
      modelAndView.addObject("currencyList", currencyCodeList);
      session.setAttribute("currencyList", currencyCodeList);

      List<Option> countryList = merchantUpdateService.getCountries();
      modelAndView.addObject(Constants.COUNTRY_LIST, countryList);
      session.setAttribute(Constants.COUNTRY_LIST, countryList);

      // to get the states
      Response stateList = merchantUpdateService.getStatesByCountry(partnerList.get(0).getCountry());
      modelAndView.addObject(Constants.STATE_LIST, stateList.getResponseList());
      session.setAttribute(Constants.STATE_LIST, stateList.getResponseList());

      byte[] image = partnerList.get(0).getPartnerLogo();
      model.put("imageData", image);
      session.setAttribute("partnerImage", partnerList.get(0).getPartnerLogo());
      String type = "jpg";
      String traineeImage = com.chatak.acquirer.admin.util.StringUtil.encodeToString(image, type);
      model.put("image", traineeImage);
    } catch (Exception e) {
      logger.error("ERROR:: PartnerController:: showEditPartner method", e);
    }
    logger.info("Exiting:: PartnerController:: showEditPartner method");
    return modelAndView;
  }

  /**
   * Method used for update Partner
   * 
   * @param request
   * @param response
   * @param model
   * @param adminUserData
   */

  @RequestMapping(value = PREPAID_ADMIN_UPDATE_PARTNER, method = RequestMethod.POST)
  public ModelAndView updatePartner(HttpServletRequest request, HttpServletResponse response,
      PartnerRequest partnerRequest, BindingResult bindingResult, Map<String, Object> model,
      HttpSession session, @RequestParam("partnerLogo") MultipartFile file,
      @FormParam("managerId") Long managerId) {

    logger.info("Entering  :: PartnerController:: updatePartner method");
    model.put("flag", false);

    ModelAndView modelAndView = new ModelAndView(PREPAID_ADMIN_SEARCH_PARTNER_PAGE);
    List<BankPartnerMapRequest> bankRequestList = new ArrayList<BankPartnerMapRequest>();
    byte[] bytes = null;
    ProgramManagerRequest programManagerRequest = new ProgramManagerRequest();
    programManagerRequest.setId(managerId);
    if (!file.isEmpty()) {
      try {
        bytes = file.getBytes();
      } catch (Exception e) {
        logger.error("ERROR:: file :: PartnerController:: updatePartner method", e);
      }
    }
    String existingFeature = (String) session.getAttribute(Constants.EXISTING_FEATURES);
    if (!(existingFeature.contains(FeatureConstants.ADMIN_SERVICE_PARTNER_EDIT_FEATURE_ID))) {
      session.invalidate();
      modelAndView.setViewName(INVALID_REQUEST_PAGE);
      return modelAndView;
    }
    try {
      setEnumValuesForSearchPage(model);
      logger.info("Entering  :: getCountryState method");

      Response stateList = merchantUpdateService.getStatesByCountry(partnerRequest.getCountry());
      modelAndView.addObject(Constants.STATE_LIST, stateList.getResponseList());
      session.setAttribute(Constants.STATE_LIST, stateList.getResponseList());

      if (bytes != null) {
        partnerRequest.setPartnerLogo(bytes);
      } else {
        bytes = (byte[]) session.getAttribute("partnerImage");
        partnerRequest.setPartnerLogo(bytes);

      }
      logger.info("Entering  :: setBankDetails method");
      setBankDetails(partnerRequest, bankRequestList);
      partnerRequest.setBankPartnerMapRequests(bankRequestList);
      partnerRequest.setProgramManagerRequest(programManagerRequest);
      partnerRequest.setPartnerType(Constants.NORMAL_PARTNER);
      setAuditData(partnerRequest, session, "Partner", "Update");
      partnerRequest.setUpdatedBy(session.getAttribute(Constants.LOGIN_USER_ID).toString());
      Response partnerUpdateResponse = partnerService.updatePartner(partnerRequest);

      if (partnerUpdateResponse != null
          && partnerUpdateResponse.getErrorCode().equals(Constants.STATUS_CODE_SUCCESS)) {
        modelAndView = showPartner(request, response, model, session);
        modelAndView.addObject(Constants.SUCESS, messageSource.getMessage(
            "admin.updatepartner.success.message", null, LocaleContextHolder.getLocale()));
      } else if (partnerUpdateResponse != null && (partnerUpdateResponse.getErrorCode()
          .equals(Constants.PARTNER_ALREADY_EXISTS_WITH_SAME_NAME)
          || partnerUpdateResponse.getErrorCode()
              .equals(Constants.PARTNER_ALREADY_EXISTS_WITH_SAME_CLIENT_ID))) {
        modelAndView = showPartner(request, response, model, session);
        modelAndView.addObject(Constants.ERROR, partnerUpdateResponse.getErrorMessage());
      }
    } catch (ChatakAdminException e) {
      logger.error("ERROR:: PartnerController:: updatePartner method1", e);
      model.put(Constants.ERROR, e.getMessage());
    }
    return modelAndView;
  }

  @RequestMapping(value = CHANGE_PARTNER_STATUS, method = RequestMethod.POST)
  public ModelAndView changePartnerStatus(HttpServletRequest request, HttpServletResponse response,
      final HttpSession session, Map<String, Object> model,
      @FormParam("managePartnerId") final Long managePartnerId,
      @FormParam("partnerStatus") final String partnerStatus,
      @FormParam("reason") final String reason) {
    logger.info("Entering :: PartnerController :: changePartnerStatus method");
    model.put("flag", false);

    ModelAndView modelAndView = new ModelAndView(PREPAID_ADMIN_SEARCH_PARTNER_PAGE);

    try {
      model.put("partnerRequest", new PartnerRequest());
      setEnumValuesForSearchPage(model);
      PartnerRequest partnerRequest = new PartnerRequest();
      partnerRequest.setPartnerId(managePartnerId);
      partnerRequest.setPageIndex(null);
      partnerRequest.setStatus(partnerStatus);
      partnerRequest.setReason(reason);
      partnerRequest.setUpdatedBy(session.getAttribute(Constants.LOGIN_USER_ID).toString());

      setAuditData(partnerRequest, session, "Partner", "Update");
      Response status = partnerService.changePartnerStatus(partnerRequest);

      if (status != null && status.getErrorCode().equals(Constants.STATUS_CODE_SUCCESS)) {
        String successData = messageSource.getMessage(
            "prepaid.partner.sucess.status.change.message", null, LocaleContextHolder.getLocale());
        model.put(Constants.SUCESS, successData);
      } else {
        if (status != null)
          model.put(Constants.ERROR, status.getErrorMessage());

      }
      List<PartnerRequest> partnerList =
          (List<PartnerRequest>) modelAndView.getModel().get("partnerResponse");
      if (!StringUtil.isListNotNullNEmpty(partnerList)) {
        partnerRequest =
            (PartnerRequest) session.getAttribute(Constants.PARTNER_REQUEST_LIST_EXPORTDATA);
      }
      modelAndView = searchPartner(request, partnerRequest, response, model, session);

    } catch (Exception e) {
      logger.error("ERROR:: PartnerController :: changePartnerStatus method2", e);
      model.put(Constants.ERROR, messageSource.getMessage("prepaid.admin.general.error.message",
          null, LocaleContextHolder.getLocale()));
    }
    logger.info("Exiting :: PartnerController :: changePartnerStatus method");
    return modelAndView;
  }

  /**
   * Method used to fetch the list of the events for the next page when
   * pagination happen
   * 
   * @param session
   * @param partnerPageData
   * @param model
   * @return
   */
  @RequestMapping(value = PARTNER_PAGINATION_ACTION, method = RequestMethod.POST)
  public ModelAndView getPartnerPagination(final HttpSession session,
      @FormParam("partnerPageData") final Integer partnerPageData,
      @FormParam("totalRecords") final Integer totalRecords, Map<String, Object> model,
      PartnerRequest partnerRequest) {
    logger.info("Entering:: PartnerController:: getPartnerPagination method");

    ModelAndView modelAndView = new ModelAndView(PREPAID_ADMIN_SEARCH_PARTNER_PAGE);
    model.put("flag", false);

    try {
      session.setAttribute("pageNumber", partnerPageData);
      session.setAttribute("totalRecords", totalRecords);
      setEnumValuesForSearchPage(model);
      List<Option> bankOptions = bankService.getBankData();
      modelAndView.addObject("bankList", bankOptions);

      partnerRequest =
          (PartnerRequest) session.getAttribute(Constants.PARTNER_REQUEST_LIST_EXPORTDATA);
      if (partnerRequest != null) {
        partnerRequest.setPartnerType(Constants.NORMAL_PARTNER);
        partnerRequest.setPageIndex(partnerPageData);
        partnerRequest.setNoOfRecords(totalRecords);
        logger.info("Entering:: addPartnerListForPagination method");
        addPartnerListForPagination(partnerRequest, modelAndView, partnerPageData, totalRecords);
      }
    } catch (Exception e) {

      logger.error("ERROR:: PartnerController:: getPartnerPagination method", e);
    }
    logger.info("Exiting:: PartnerController:: getPartnerPagination method");
    return modelAndView;
  }

  public void setEnumValuesForSearchPage(Map<String, Object> model) {
    model.put("partnerStatusList", Arrays.asList(Constants.ACTIVE, Constants.ACC_SUSPENDED));
  }

  private void getDefaultPmCurrency(Long defaultPmId, ProgramManagerResponse programManagerResponse,
      ProgramManagerRequest programManagerRequest, Map<String, Object> model)
          throws ChatakAdminException {
    logger.info("Entering:: PartnerController:: getDefaultPmCurrency method");
    if (defaultPmId != null) {
      programManagerResponse = programManagerService.editProgramManager(programManagerRequest);
      if (programManagerResponse != null
          && StringUtil.isListNotNullNEmpty(programManagerResponse.getProgramManagersList())) {
        model.put("pmCurrency",
            programManagerResponse.getProgramManagersList().get(0).getAccountCurrency());

      }
    }
    logger.info("Exiting:: PartnerController:: getDefaultPmCurrency method");
  }

  private void getCountryForCreate(Map<String, Object> model) throws PrepaidException {
    logger.info("Entering:: PartnerController:: getCountryForCreate method");
    List<Option> countryList = merchantUpdateService.getCountries();
    model.put(Constants.COUNTRY_LIST, countryList);
    logger.info("Exiting:: PartnerController:: getCountryForCreate method");
  }

  private void getBankDetails(PartnerRequest partnerRequest) {
    logger.info("Entering:: PartnerController:: getBankDetails method");
    java.util.List<BankPartnerMapRequest> bankRequestList = new ArrayList<BankPartnerMapRequest>();
    String[] arrayList = partnerRequest.getBankName().split(",");
    for (int i = 0; i < arrayList.length; i++) {
      if (arrayList[i] != null) {
        BankPartnerMapRequest bankProgramManagerMapRequest = new BankPartnerMapRequest();
        bankProgramManagerMapRequest.setBankId(Long.valueOf(arrayList[i]));
        bankRequestList.add(bankProgramManagerMapRequest);
        partnerRequest.setBankPartnerMapRequests(bankRequestList);
      }
    }
    logger.info("Exiting:: PartnerController:: getBankDetails method");
  }

  private void getPmAndBankList(ProgramManagerRequest programManagerRequest,
      Map<String, Object> model) throws PrepaidException, ChatakAdminException {
    logger.info("Entering:: PartnerController:: getPmAndBankList method");
    ProgramManagerResponse programManagerResponse = null;
    BankRequest bankRequest = new BankRequest();
    programManagerResponse = programManagerService.getAllProgramManagers(programManagerRequest);
    if (!StringUtil.isNull(programManagerResponse)) {
      model.put("programManagersList", programManagerResponse.getProgramManagersList());
    }
    bankRequest.setStatusList(Arrays.asList(Constants.ACTIVE, Constants.ACC_SUSPENDED));
    List<Option> bankOptions = bankService.getBankData();
    model.put("bankList", bankOptions);
    logger.info("Exiting:: PartnerController:: getPmAndBankList method");
  }

  private ModelAndView addPartnerList(PartnerRequest partnerRequest, HttpSession session,
      Map<String, Object> model) throws ChatakAdminException {
    logger.info("Entering:: PartnerController:: addPartnerList method");
    ModelAndView modelAndView = new ModelAndView(PREPAID_ADMIN_SEARCH_PARTNER_PAGE);
    setAuditData(partnerRequest, session, "Partner", "Search");
    partnerRequest.setPartnerType(Constants.NORMAL_PARTNER);
    PartnerResponse partnerResponse = partnerService.searchPartner(partnerRequest);
    modelAndView = PaginationUtil.getPagenationModel(modelAndView,
        partnerResponse.getTotalNoOfRows(), partnerRequest.getPageSize());
    if (StringUtil.isListNotNullNEmpty(partnerResponse.getPartnerList())) {
      for (PartnerRequest partnerRequest1 : partnerResponse.getPartnerList()) {
        partnerRequest1.setPartnerName(com.chatak.acquirer.admin.util.StringUtil
            .escapeHTMLChars(partnerRequest1.getPartnerName()));
      }
      model.put("partnerResponse", partnerResponse.getPartnerList());
    }
    logger.info("Exiting:: PartnerController:: addPartnerList method");
    return modelAndView;
  }

  private void addPartnerListForPagination(PartnerRequest partnerRequest, ModelAndView modelAndView,
      Integer partnerPageData, Integer totalRecords) throws PrepaidException, ChatakAdminException {
    logger.info("Entering:: PartnerController:: addPartnerListForPagination method");
    PartnerResponse partnerResponse = partnerService.searchPartner(partnerRequest);
    List<PartnerRequest> partnerRequestList = partnerResponse.getPartnerList();
    if (StringUtil.isListNotNullNEmpty(partnerRequestList)) {
      for (PartnerRequest partnerRequest1 : partnerResponse.getPartnerList()) {
        partnerRequest1.setPartnerName(com.chatak.acquirer.admin.util.StringUtil
            .escapeHTMLChars(partnerRequest1.getPartnerName()));
      }
      modelAndView = PaginationUtil.getPagenationModelSuccessive(modelAndView, partnerPageData,
          totalRecords, partnerRequest.getPageSize());
      modelAndView.addObject("partnerResponse", partnerRequestList);
    }
    logger.info("Exiting:: PartnerController:: addPartnerListForPagination method");
  }

  private void setBankDetails(PartnerRequest partnerRequest,
      List<BankPartnerMapRequest> bankRequestList) {
    logger.info("Entering:: PartnerController:: setBankDetails method");
    String[] arrayList = partnerRequest.getBankName().split(",");
    for (int i = 0; i < arrayList.length; i++) {
      if (arrayList[i] != null) {
        BankPartnerMapRequest bankProgramManagerMapRequest = new BankPartnerMapRequest();
        bankProgramManagerMapRequest.setBankId(Long.valueOf(arrayList[i]));
        bankProgramManagerMapRequest.setPartnerId(partnerRequest.getPartnerId());
        bankRequestList.add(bankProgramManagerMapRequest);

      }
    }
    logger.info("Exiting:: PartnerController:: setBankDetails method");
  }

  @RequestMapping(value = FETCH_BANKS_OF_PGMNG, method = RequestMethod.GET)
  public @ResponseBody String fetchBanksofPgmng(HttpServletRequest request,
      HttpServletResponse response, Map<String, Object> model, HttpSession session) {
    logger.info("Entering  :: PartnerController:: fetchBanksofPgmng method");
    Long selectedProgramManagerId = Long.parseLong(request.getParameter("programManagerId"));
    if (selectedProgramManagerId != null) {
      ProgramManagerRequest programManagerRequest = new ProgramManagerRequest();
      programManagerRequest.setId(selectedProgramManagerId);
      programManagerRequest.setStatuses(Arrays.asList("Active"));
      try {
        BankResponse bankResponse =
            programManagerService.findBankByProgramManagerId(programManagerRequest);

        return JsonUtil.convertObjectToJSON(bankResponse);

      } catch (ChatakAdminException e) {
        logger.error("ERROR:: PartnerController:: fetchBanksofPgmng method", e);
        model.put(Constants.ERROR, e.getMessage());
      }
      logger.info("Exiting  :: PartnerController:: fetchBanksofPgmng method");
    }
    return null;
  }

  public void setAuditData(Object object, HttpSession session, String auditSection,
      String auditEvent) {
    if (object instanceof Request) {
      Request reqData = (Request) object;
      reqData.setLoginSessionId(session.getId());
      reqData.setAuditedBy(session.getAttribute(Constants.LOGIN_USER_ID).toString());
      reqData.setAuditSection(auditSection);
      reqData.setAuditEvent(auditEvent);
    }
  }

  @RequestMapping(value = DOWNLOAD_PARTNER_REPORT, method = RequestMethod.POST)
  public ModelAndView downloadPartnerReport(HttpSession session, Map<?, ?> model,
      @FormParam("downLoadPageNumber") final Integer downLoadPageNumber,
      @FormParam("downloadType") final String downloadType,
      @FormParam("totalRecords") final Integer totalRecords,
      @FormParam("downloadAllRecords") final boolean downloadAllRecords, HttpServletRequest request,
      HttpServletResponse response) {
    logger.info("Entering:: PartnerController:: downloadPartnerReport method");

    try {
      PartnerRequest searchlist =
          (PartnerRequest) session.getAttribute(Constants.PARTNER_REQUEST_LIST_EXPORTDATA);
      searchlist.setPageIndex(downLoadPageNumber);
      Integer pageSize = searchlist.getPageSize();
      if (downloadAllRecords) {
        searchlist.setPageIndex(Constants.ONE);
        searchlist.setPageSize(totalRecords);
      }
      PartnerResponse partnerResponse = partnerService.searchPartner(searchlist);
      List<PartnerRequest> exportList = partnerResponse.getPartnerList();
      if (StringUtil.isListNotNullNEmpty(exportList)) {
        ExportDetails exportDetails = new ExportDetails();
        if (Constants.PDF_FILE_FORMAT.equalsIgnoreCase(downloadType)) {
          exportDetails.setExportType(ExportType.PDF);
        } else if (Constants.XLS_FILE_FORMAT.equalsIgnoreCase(downloadType)) {
          exportDetails.setExportType(ExportType.XLS);
          exportDetails.setExcelStartRowNumber(Integer.parseInt("5"));
        }
        setDataForDownloadPartnerReport(exportList, exportDetails);
        ExportUtil.exportData(exportDetails, response, messageSource);
      }
      searchlist.setPageSize(pageSize);
    } catch (Exception e) {
      logger.error("ERROR:: PartnerController:: downloadPartnerReport method2", e);
    }
    logger.info("Exiting:: PartnerController:: downloadPartnerReport method");
    return null;
  }

  private void setDataForDownloadPartnerReport(List<PartnerRequest> exportList,
      ExportDetails exportDetails) {
    exportDetails.setReportName("Partner_");
    exportDetails.setHeaderMessageProperty("admin.partner.report");

    exportDetails.setHeaderList(getPartnerAccountReportHeaderList());
    exportDetails.setFileData(getPartnerAccountReportFileData(exportList));
  }

  private List<String> getPartnerAccountReportHeaderList() {
    String[] headerArr = {
        messageSource.getMessage("admin.PartnerName.message", null,
            LocaleContextHolder.getLocale()),
        messageSource.getMessage("home.label.companyname", null, LocaleContextHolder.getLocale()),
        messageSource.getMessage("admin.BusinessEntityName.message", null,
            LocaleContextHolder.getLocale()),
        messageSource.getMessage("home.label.status", null, LocaleContextHolder.getLocale()),};
    return new ArrayList<String>(Arrays.asList(headerArr));
  }

  private static List<Object[]> getPartnerAccountReportFileData(
      List<PartnerRequest> partnerRequest) {
    List<Object[]> fileData = new ArrayList<Object[]>();

    for (PartnerRequest partnerRequests : partnerRequest) {

      Object[] rowData = {partnerRequests.getPartnerName(), partnerRequests.getCompanyName(),
          partnerRequests.getBusinessEntityName(), partnerRequests.getStatus()};
      fileData.add(rowData);
    }

    return fileData;
  }
}
