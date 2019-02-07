package com.chatak.acquirer.admin.controller;

import java.io.IOException;
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
import org.springframework.web.bind.annotation.ModelAttribute;
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
import com.chatak.acquirer.admin.service.CardProgramServices;
import com.chatak.acquirer.admin.service.CurrencyConfigService;
import com.chatak.acquirer.admin.service.IsoService;
import com.chatak.acquirer.admin.service.PartnerService;
import com.chatak.acquirer.admin.service.ProgramManagerService;
import com.chatak.acquirer.admin.util.ExportUtil;
import com.chatak.acquirer.admin.util.JsonUtil;
import com.chatak.acquirer.admin.util.PaginationUtil;
import com.chatak.acquirer.admin.util.StringUtil;
import com.chatak.pg.bean.Request;
import com.chatak.pg.bean.Response;
import com.chatak.pg.bean.TimeZoneRequest;
import com.chatak.pg.bean.TimeZoneResponse;
import com.chatak.pg.enums.ExportType;
import com.chatak.pg.user.bean.CardProgramRequest;
import com.chatak.pg.user.bean.CardProgramResponse;
import com.chatak.pg.user.bean.PartnerGroupPartnerMapRequest;
import com.chatak.pg.user.bean.PartnerRequest;
import com.chatak.pg.user.bean.PartnerResponse;
import com.chatak.pg.user.bean.ProgramManagerRequest;
import com.chatak.pg.user.bean.ProgramManagerResponse;
import com.chatak.pg.util.Constants;

@Controller
@SuppressWarnings({"unchecked", "rawtypes"})
public class ProgramManagerController implements URLMappingConstants {

  Boolean defaultPMValue = false;

  private static Logger logger = Logger.getLogger(ProgramManagerController.class);

  @Autowired
  private MessageSource messageSource;

  @Autowired
  ProgramManagerService programManagerService;

  @Autowired
  PartnerService partnerService;
  
  @Autowired
  CardProgramServices cardProgramServices;

  @Autowired
  private BankService bankService;

  @Autowired
  private CurrencyConfigService currencyConfigService;
  
  @Autowired
  IsoService isoService;

  /**
   * @param request
   * @param response
   * @param model
   * @param session
   * @return
   */
  @RequestMapping(value = SHOW_PREPAID_ADMIN_SEARCH_PROGRAM_MANAGER, method = RequestMethod.GET)
  public ModelAndView showSearchProgramManager(HttpServletRequest request,
      HttpServletResponse response, Map<String, Object> model, HttpSession session) {
    logger.info("Entering:: ProgramManagerController:: showSearchProgramManager method");
    ModelAndView modelAndView = new ModelAndView(PREPAID_ADMIN_SEARCH_PROGRAM_MANAGER_PAGE);
    try {
      String existingFeature = (String) session.getAttribute(Constants.EXISTING_FEATURES);
      if (!existingFeature.contains(FeatureConstants.ADMIN_SERVICE_PM_FEATURE_ID)) {
        session.invalidate();
        modelAndView.setViewName(INVALID_REQUEST_PAGE);
        return modelAndView;
      }
      setEnumValuesForSearchPage(model);
      // To show the search page if existing feature contains view, edit,
      // suspend or activate feature
      ProgramManagerRequest programManagerRequest = new ProgramManagerRequest();
      List<Option> bankOptions = bankService.getBankData();
      modelAndView.addObject("bankList", bankOptions);
      programManagerRequest.setPageIndex(Constants.ONE);
      programManagerRequest.setPageSize(Constants.MAX_ENTITIES_PAGINATION_DISPLAY_SIZE);

      model.put("programManagerRequest", programManagerRequest);
      model.put("searchList", "yes");
    } catch (ChatakAdminException e) {
      logger.error("ERROR:: ProgramManagerController:: showSearchProgramManager method1", e);
      model.put(Constants.ERROR, e.getMessage());
    } catch (Exception e) {
      logger.error("ERROR:: ProgramManagerController:: showSearchProgramManager method2", e);
      model.put(Constants.ERROR, messageSource.getMessage("prepaid.admin.general.error.message",
          null, LocaleContextHolder.getLocale()));
    }
    logger.info("Exit:: ProgramManagerController:: showSearchProgramManager method");
    return modelAndView;
  }

  /**
   * @param request
   * @param response
   * @param model
   * @param session
   * @return
   */
  @RequestMapping(value = SHOW_PREPAID_ADMIN_CREATE_PROGRAM_MANAGER, method = RequestMethod.GET)
  public ModelAndView showCreateProgramManager(HttpServletRequest request,
      HttpServletResponse response, Map<String, Object> model, HttpSession session) {
    logger.info("Entering:: ProgramManagerController:: showCreateProgramManager method");
    ModelAndView modelAndView = new ModelAndView(PREPAID_ADMIN_CREATE_PROGRAM_MANAGER_PAGE);
    try {
      String existingFeature = (String) session.getAttribute(Constants.EXISTING_FEATURES);
      if (!existingFeature.contains(FeatureConstants.ADMIN_SERVICE_PM_CREATE_FEATURE_ID)) {
        session.invalidate();
        modelAndView.setViewName(INVALID_REQUEST_PAGE);
        return modelAndView;
      }
      ProgramManagerRequest programManagerRequest = new ProgramManagerRequest();
      model.put("programManagerRequest", programManagerRequest);

      List<Option> bankOptions = bankService.getBankData();
      modelAndView.addObject("bankList", bankOptions);

      // to get the list of currencycurrencyList
      List<Option> currencyCodeList = currencyConfigService.getCurrencyConfigCode();
      modelAndView.addObject("currencyList", currencyCodeList);
      session.setAttribute("currencyList",new ArrayList(currencyCodeList));
   // to get the list of program manager Countries
      List<Option> countryList = bankService.getCountry();
      modelAndView.addObject(Constants.COUNTRY_LIST, countryList);
      session.setAttribute(Constants.COUNTRY_LIST,new ArrayList(countryList));	
    } catch (Exception e) {
      logger.error("ERROR:: ProgramManagerController:: showCreateProgramManager method", e);
    }
    logger.info("Exiting:: ProgramManagerController:: showCreateProgramManager method");
    return modelAndView;
  }

  /**
   * @param request
   * @param response
   * @param programManagerRequest
   * @param programManagerBankId
   * @param model
   * @param session
   * @return
   * @throws CloneNotSupportedException
   */
  @RequestMapping(value = SHOW_PREPAID_ADMIN_SEARCH_PROGRAM_MANAGER, method = RequestMethod.POST)
  public ModelAndView processCreateProgramManager(HttpServletRequest request,
      HttpServletResponse response, Map<String, Object> model, HttpSession session,
      @RequestParam("programManagerLogo") MultipartFile file,
      @FormParam("checkDefaultPMValue") final String checkDefaultPMValue,
      ProgramManagerRequest programManagerRequest, BindingResult bindingResult){
    logger.info("Entering:: ProgramManagerController:: processCreateProgramManager method");

    ModelAndView modelAndView = new ModelAndView(PREPAID_ADMIN_SEARCH_PROGRAM_MANAGER_PAGE);

    String existingFeature = (String) session.getAttribute(Constants.EXISTING_FEATURES);
    if (!(existingFeature.contains(FeatureConstants.ADMIN_SERVICE_PM_CREATE_FEATURE_ID))) {
      session.invalidate();
      modelAndView.setViewName(INVALID_REQUEST_PAGE);
      return modelAndView;
    }
    try {
      setEnumValuesForSearchPage(model);
      logger.info("Entering:: getLogo method");
        getLogo(programManagerRequest, file);
      programManagerRequest.setCreatedBy(session.getAttribute(Constants.LOGIN_USER_ID).toString());
      setAuditData(programManagerRequest, session, "ProgramManager", "Create");
      
      com.chatak.pg.bean.Response programManagerCreateResponse =
          programManagerService.createProgramManager(programManagerRequest);

      if (programManagerCreateResponse != null
          && programManagerCreateResponse.getErrorCode().equals(Constants.STATUS_CODE_SUCCESS)) {
        modelAndView = showSearchProgramManager(request, response, model, session);

        modelAndView.addObject(Constants.SUCESS,
            messageSource.getMessage("prepaid.admin.addprogrammanager.success.message", null,
                LocaleContextHolder.getLocale()));
      } else {
        modelAndView = showCreateProgramManager(request, response, model, session);
        modelAndView.addObject(Constants.ERROR,
            messageSource.getMessage("prepaid.admin.addprogrammanager.error.message", null,
                LocaleContextHolder.getLocale()));
      }
      if ((checkDefaultPMValue.equals("true")) && (programManagerCreateResponse != null)
          && (programManagerCreateResponse.getErrorCode().equals(Constants.STATUS_CODE_SUCCESS))) {
        session.setAttribute(Constants.DEFAULT_PM_ID,
            (long) programManagerCreateResponse.getTotalNoOfRows());
        modelAndView = showSearchProgramManager(request, response, model, session);
        modelAndView.addObject(Constants.SUCESS,
            messageSource.getMessage("prepaid.admin.addprogrammanager.success.message", null,
                LocaleContextHolder.getLocale()));
      }
    } catch (ChatakAdminException e) {
      logger.error("ERROR:: ProgramManagerController:: processCreateProgramManager method2", e);
      modelAndView = showCreateProgramManager(request, response, model, session);
      model.put("programManagerRequest", programManagerRequest);
      modelAndView.addObject(Constants.ERROR, e.getErrorMessage());
    } catch (Exception e) {
      logger.error("ERROR:: ProgramManagerController:: processCreateProgramManager method3", e);
      model.put(Constants.ERROR, messageSource.getMessage("prepaid.admin.general.error.message",
          null, LocaleContextHolder.getLocale()));
    }
    logger.info("Exiting  :: ProgramManagerController:: processCreateProgramManager method");
    return modelAndView;
  }

  /**
   * @param request
   * @param programManagerRequest
   * @param response
   * @param model
   * @param session
   * @return
   */
  @RequestMapping(value = PREPAID_ADMIN_SEARCH_PROGRAM_MANAGER, method = RequestMethod.POST)
  public ModelAndView processSearchProgramManager(HttpServletRequest request,
      ProgramManagerRequest programManagerRequest, HttpServletResponse response,
      Map<String, Object> model, HttpSession session) {
    logger.info("Entering:: ProgramManagerController:: processSearchProgramManager method");
    ModelAndView modelAndView = new ModelAndView(PREPAID_ADMIN_SEARCH_PROGRAM_MANAGER_PAGE);
    String existingFeature = (String) session.getAttribute(Constants.EXISTING_FEATURES);
    if (!(existingFeature.contains(FeatureConstants.ADMIN_SERVICE_PM_FEATURE_ID))) {
      session.invalidate();
      modelAndView.setViewName(INVALID_REQUEST_PAGE);
      return modelAndView;
    }
    try {
    	LoginResponse loginResponse = (LoginResponse) session.getAttribute(Constants.LOGIN_RESPONSE_DATA);
    	setEnumValuesForSearchPage(model);
    	if(loginResponse.getUserType().equalsIgnoreCase(Constants.PM_USER_TYPE)) {
    		programManagerRequest.setId(loginResponse.getEntityId());
		}
      model.put("programManagerRequest", programManagerRequest);
      programManagerRequest.setCreatedBy(session.getAttribute(Constants.LOGIN_USER_ID).toString());
      programManagerRequest.setPageIndex(Constants.ONE);
      if (programManagerRequest.getPageSize() != null) {
        programManagerRequest.setPageSize(programManagerRequest.getPageSize());
      } else {
        programManagerRequest.setPageSize(Constants.MAX_ENTITIES_PAGINATION_DISPLAY_SIZE);
      }
      session.setAttribute(Constants.PROGRAM_MANAGER_REQUEST_LIST_EXPORTDATA,
          programManagerRequest);
      List<Option> bankOptions = bankService.getBankData();
      modelAndView.addObject("bankList", bankOptions);
      setAuditData(programManagerRequest, session, "ProgramManager", "Search");
      ProgramManagerResponse programManagerResponse =
          programManagerService.searchProgramManagerDetails(programManagerRequest);
      modelAndView = PaginationUtil.getPagenationModel(modelAndView,
          programManagerResponse.getTotalNoOfRows(), programManagerRequest.getPageSize());
      if (StringUtil.isListNotNullNEmpty(programManagerResponse.getProgramManagersList())) {
        List<ProgramManagerRequest> searchList = programManagerResponse.getProgramManagersList();
        session.setAttribute(Constants.SEARCH_LIST, searchList);
        for (ProgramManagerRequest programManagerRequest1 : searchList) {
          programManagerRequest1.setProgramManagerName(
              StringUtil.escapeHTMLChars(programManagerRequest1.getProgramManagerName()));

          modelAndView.addObject("searchList", searchList);

        }
      } else {
        modelAndView.addObject("searchList", new ArrayList<ProgramManagerRequest>());
        session.setAttribute(Constants.SEARCH_LIST, new ArrayList<ProgramManagerRequest>());
      }
      session.setAttribute(Constants.TOTAL_RECORDS, programManagerResponse.getTotalNoOfRows());
    } catch (ChatakAdminException e) {
      modelAndView =
          processException(request, response, model, session, e, "processSearchProgramManager");
      modelAndView.addObject(Constants.ERROR, e.getMessage());

    } catch (Exception e) {
      modelAndView =
          processException(request, response, model, session, e, "processSearchProgramManager");
      modelAndView.addObject(Constants.ERROR, e.getMessage());
    }
    logger.info("Exit:: ProgramManagerController:: processSearchProgramManager method");
    return modelAndView;
  }
  
  
	@RequestMapping(value = PREPAID_ADMIN_SEARCH_PROGRAM_MANAGER, method = RequestMethod.GET)
	public ModelAndView processSearchProgramManagerGetMethod(HttpServletRequest request,
			ProgramManagerRequest programManagerRequest, HttpServletResponse response, Map<String, Object> model,
			HttpSession session) {
		logger.info("Entering:: ProgramManagerController:: processSearchProgramManager method");
		ModelAndView modelAndView = showSearchProgramManager(request, response, model, session);
		logger.info("Exit:: ProgramManagerController:: processSearchProgramManager method");
		return modelAndView;
	}

  private ModelAndView processException(HttpServletRequest request, HttpServletResponse response,
      Map<String, Object> model, HttpSession session, Exception e, String methodName) {
    ModelAndView modelAndView;
    logger.error("ERROR:: ProgramManagerController:: " + methodName + " method2", e);
    modelAndView = showSearchProgramManager(request, response, model, session);
    return modelAndView;
  }

  /**
   * @param session
   * @param programManagerPageData
   * @param totalRecords
   * @param model
   * @return
   */
  @RequestMapping(value = PREPAID_ADMIN_PROGRAM_MANAGER_PAGINATION_ACTION,
      method = RequestMethod.POST)
  public ModelAndView getProgramManagerPagination(final HttpSession session,
      @FormParam("programManagerPageData") final Integer programManagerPageData,
      @FormParam("totalRecords") final Integer totalRecords, Map model) {
    logger.info("Entering:: ProgramManagerController:: getProgramManagerPagination method");
    ModelAndView modelAndView = new ModelAndView(PREPAID_ADMIN_SEARCH_PROGRAM_MANAGER_PAGE);

    try {
      setEnumValuesForSearchPage(model);
      ProgramManagerRequest programManagerRequest = new ProgramManagerRequest();
      model.put("programManagerRequest", programManagerRequest);
      programManagerRequest.setCreatedBy(session.getAttribute(Constants.LOGIN_USER_ID).toString());
      programManagerRequest.setPageIndex(programManagerPageData);
      programManagerRequest.setNoOfRecords(totalRecords);
      List<Option> bankOptions = bankService.getBankData();
      modelAndView.addObject("bankList", bankOptions);
      ProgramManagerRequest sessionSearchList = (ProgramManagerRequest) session
          .getAttribute(Constants.PROGRAM_MANAGER_REQUEST_LIST_EXPORTDATA);
      programManagerRequest.setPageSize(sessionSearchList.getPageSize());
      sessionSearchList.setCreatedBy(session.getAttribute(Constants.LOGIN_USER_ID).toString());
      sessionSearchList.setPageIndex(programManagerPageData);
      sessionSearchList.setNoOfRecords(totalRecords);
      logger.info("Entering:: getPmPaginationList method");
      getPmPaginationList(session, modelAndView, programManagerPageData, sessionSearchList, model);

    } catch (Exception e) {
      logger.error("ERROR:: ProgramManagerController:: getProgramManagerPagination method", e);
    }
    logger.info("Exiting:: ProgramManagerController:: getProgramManagerPagination method");
    return modelAndView;
  }

  /**
   * @param session
   * @param model
   * @param downLoadPageNumber
   * @param downloadType
   * @param request
   * @param response
   * @return
   */
  @RequestMapping(value = PREPAID_ADMIN_PROGRAM_MANAGER_REPORT, method = RequestMethod.POST)
  public ModelAndView downloadProgramManagerReport(HttpSession session, Map model,
      @FormParam("downLoadPageNumber") final Integer downLoadPageNumber,
      @FormParam("downloadType") final String downloadType,
      @FormParam("totalRecords") final Integer totalRecords,
      @FormParam("downloadAllRecords") final boolean downloadAllRecords, HttpServletRequest request,
      HttpServletResponse response) {
    logger.info("Entering:: ProgramManagerController:: downloadProgramManagerReport method");
    try {
      ProgramManagerRequest searchList = (ProgramManagerRequest) session
          .getAttribute(Constants.PROGRAM_MANAGER_REQUEST_LIST_EXPORTDATA);
      searchList.setPageIndex(downLoadPageNumber);
      Integer pSize = searchList.getPageSize();
      if (downloadAllRecords) {
        searchList.setPageSize(totalRecords);
        searchList.setPageIndex(Constants.ONE);
      }
      ProgramManagerResponse programManagerResponse =
          programManagerService.searchProgramManagerDetails(searchList);
      List<ProgramManagerRequest> exportList = programManagerResponse.getProgramManagersList();
      if (StringUtil.isListNotNullNEmpty(exportList)) {
        ExportDetails exportDetails = new ExportDetails();
        if (Constants.PDF_FILE_FORMAT.equalsIgnoreCase(downloadType)) {
          exportDetails.setExportType(ExportType.PDF);
        } else if (Constants.XLS_FILE_FORMAT.equalsIgnoreCase(downloadType)) {
          exportDetails.setExportType(ExportType.XLS);
          exportDetails.setExcelStartRowNumber(Integer.parseInt("5"));
        }
        setDataForDownloadPmReport(exportList, exportDetails);
        ExportUtil.exportData(exportDetails, response, messageSource);
      }
      searchList.setPageSize(pSize);
    } catch (Exception e) {
      logger.error("ERROR:: ProgramManagerController:: downloadProgramManagerReport method2", e);
    }
    logger.info("Exiting:: ProgramManagerController:: downloadProgramManagerReport method");
    return null;
  }

  /**
   * @param request
   * @param programManagerRequest
   * @param response
   * @param model
   * @param session
   * @return
   */
  @RequestMapping(value = SHOW_PREPAID_ADMIN_EDIT_PROGRAM_MANAGER, method = RequestMethod.POST)
  public ModelAndView showEditProgramManager(HttpServletRequest request,
      @FormParam("programManagerId") final Long programManagerId,
      ProgramManagerRequest programManagerRequest, HttpServletResponse response,
      Map<String, Object> model, HttpSession session) {

    logger.info("Entering:: ProgramManagerController:: showEditProgramManager method");
    ModelAndView modelAndView = new ModelAndView(PREPAID_ADMIN_EDIT_PROGRAM_MANAGER_PAGE);
    String existingFeature = (String) session.getAttribute(Constants.EXISTING_FEATURES);
    if (!(existingFeature.contains(FeatureConstants.ADMIN_SERVICE_PM_EDIT_FEATURE_ID))) {
      session.invalidate();
      modelAndView.setViewName(INVALID_REQUEST_PAGE);
      return modelAndView;
    }
    try {
      List<CardProgramRequest> unselectedCardPrograms;
      programManagerRequest.setId(programManagerId);
      setAuditData(programManagerRequest, session, "ProgramManager", "View");
      ProgramManagerResponse programManagerResponse =
          programManagerService.editProgramManager(programManagerRequest);
      List<ProgramManagerRequest> programManagerList =
          programManagerResponse.getProgramManagersList();
      defaultPMValue = programManagerList.get(0).getDefaultProgramManager();
      model.put("panRangeList", JsonUtil.convertObjectToJSON(programManagerResponse.getProgramManagersList().get(0).getPanRangeList())+"");
      model.put("selectedBankList", programManagerResponse.getProgramManagersList().get(0).getBankRequest());
      Response currency = currencyConfigService.getCurrencyCodeNumeric(programManagerResponse.getProgramManagersList().get(0).getAccountCurrency());
      Response result=bankService.getBankName(currency.getCurrencyId());
      model.put("allBankList", result.getResponseList());
      modelAndView.addObject("selectedCardProgramList", programManagerResponse.getProgramManagersList().get(0).getCardProgamMapping());
      if(programManagerList.get(0).getIssuancepmid()!=null){
        unselectedCardPrograms = programManagerService.getUnselectedCpByPm(programManagerId);        
      }else {
        unselectedCardPrograms = programManagerService.getUnselectedCpForIndependentPm(programManagerId, programManagerList.get(0).getAccountCurrency());
      }
      modelAndView.addObject("unselectedCardProgramList", unselectedCardPrograms);
      model.put("programManagerRequest", programManagerList.get(0));
      byte[] image = programManagerList.get(0).getProgramManagerLogo();
      session.setAttribute("programManagerImage",
          programManagerList.get(0).getProgramManagerLogo());
      model.put("imageData", image);
      String type = "jpg";
      String traineeImage = StringUtil.encodeToString(image, type);
      model.put("image", traineeImage);
      model.put("schedulerRunTime", programManagerList.get(0).getSchedulerRunTime());
      
      // to get the list of currencycurrencyList
      List<Option> currencyCodeList = currencyConfigService.getCurrencyConfigCode();
      modelAndView.addObject("currencyList", currencyCodeList);
      session.setAttribute("currencyList",new ArrayList(currencyCodeList));
      
      // to get the list of program manager Countries
      List<Option> countryList = bankService.getCountry();
      modelAndView.addObject(Constants.COUNTRY_LIST, countryList);
      session.setAttribute(Constants.COUNTRY_LIST,new ArrayList(countryList));
      
      Response stateList = bankService.getStatesByCountry(programManagerList.get(0).getCountry());
      modelAndView.addObject(Constants.STATE_LIST, stateList.getResponseList());
      session.setAttribute(Constants.STATE_LIST, new ArrayList(stateList.getResponseList()));
      for(Option option : countryList){
    	  if(programManagerList.get(0).getCountry().equals(option.getValue())){
    		  TimeZoneResponse timeZon = bankService.searchAllTimeZone(Long.valueOf(option.getLabel()));
    	      modelAndView.addObject("timeZoneList", timeZon.getListOfTimeZoneRequests());
    	      session.setAttribute("timeZoneList", timeZon.getListOfTimeZoneRequests());
          }
      }
      
    } catch (Exception e) {
      logger.error("ERROR:: ProgramManagerController:: showEditProgramManager method", e);
      modelAndView = processException(request, response, model, session, e, "showEditProgramManager");
      modelAndView.addObject(Constants.ERROR, messageSource.getMessage("chatak.general.error", null, LocaleContextHolder.getLocale()));
    }
    logger.info("Exiting:: ProgramManagerController:: showEditProgramManager method");
    return modelAndView;
  }

  /**
   * @param request
   * @param response
   * @param programManagerRequest
   * @param programManagerBankId
   * @param model
   * @param session
   * @return
   */
  @RequestMapping(value = PREPAID_ADMIN_UPDATE_PROGRAM_MANAGER, method = RequestMethod.POST)
  public ModelAndView updateProgramManager(HttpServletRequest request, HttpServletResponse response,
      @ModelAttribute ProgramManagerRequest programManagerRequest, BindingResult bindingResult,
      @RequestParam("programManagerLogo") MultipartFile file, Map<String, Object> model,
      HttpSession session) {
    logger.info("Entering:: ProgramManagerController:: updateProgramManager method");
    ModelAndView modelAndView = new ModelAndView(PREPAID_ADMIN_SEARCH_PROGRAM_MANAGER_PAGE);

    String existingFeature = (String) session.getAttribute(Constants.EXISTING_FEATURES);
    setAuditData(programManagerRequest, session, "ProgramManager", "Update");
    if (!existingFeature.contains(FeatureConstants.ADMIN_SERVICE_PM_EDIT_FEATURE_ID)) {
      session.invalidate();
      modelAndView.setViewName(INVALID_REQUEST_PAGE);
      return modelAndView;
    }

    try {
      setEnumValuesForSearchPage(model);
      logger.info("Entering:: getLogo method");
      getLogo(programManagerRequest, file, session);
      programManagerRequest.setUpdatedBy(session.getAttribute(Constants.LOGIN_USER_ID).toString());
      Response respon2 = programManagerService.updateProgramManager(programManagerRequest);

      if (respon2 != null && respon2.getErrorCode().equals(Constants.STATUS_CODE_SUCCESS)) {
        modelAndView.setViewName(PREPAID_ADMIN_SEARCH_PROGRAM_MANAGER_PAGE);
        modelAndView = showSearchProgramManager(request, response, model, session);
        modelAndView.addObject(Constants.SUCESS,
            messageSource.getMessage("prepaid.admin.updateprogrammanager.success.message", null,
                LocaleContextHolder.getLocale()));
        logger.info("Entering:: defaultPmMessage method");
      } else if (respon2 != null
          && respon2.getErrorCode().equals(Constants.PROGRAM_MANAGER_ALREADY_EXISTS_WITH_NAME)) {
        modelAndView = showCreateProgramManager(request, response, model, session);
        modelAndView.addObject(Constants.ERROR, respon2.getErrorMessage());
      } else {
        modelAndView.setViewName(PREPAID_ADMIN_SEARCH_PROGRAM_MANAGER_PAGE);
        modelAndView = showSearchProgramManager(request, response, model, session);
        modelAndView.addObject(Constants.ERROR,
            messageSource.getMessage("prepaid.admin.updateprogrammanager.error.message", null,
                LocaleContextHolder.getLocale()));
      }

    } catch (ChatakAdminException e) {
      modelAndView = processException(request, response, model, session, e, "updateProgramManager");
      model.put(Constants.ERROR, e.getMessage());
    } catch (Exception e) {
      modelAndView = processException(request, response, model, session, e, "updateProgramManager");
      model.put(Constants.ERROR, messageSource.getMessage("prepaid.admin.general.error.message",
          null, LocaleContextHolder.getLocale()));
    }
    logger.info("Exiting  :: ProgramManagerController:: updateProgramManager method");
    return modelAndView;
  }

  @RequestMapping(value = PREPAID_ADMIN_CHANGE_PROGRAM_MANAGER_STATUS, method = RequestMethod.POST)
  public ModelAndView changeProgramManagerStatus(HttpServletRequest request,
      HttpServletResponse response, Map<String, Object> model, final HttpSession session,
      @FormParam("manageProgramManagerId") final Long manageProgramManagerId,
      @FormParam("manageProgramManagerStatus") final String manageProgramManagerStatus,
      @FormParam("reason") String reason) {

    logger.info("Entering :: ProgramManagerController :: changeProgramManagerStatus");
    ModelAndView modelAndView = new ModelAndView(PREPAID_ADMIN_SEARCH_PROGRAM_MANAGER_PAGE);
    String existingFeature = (String) session.getAttribute(Constants.EXISTING_FEATURES);
    if (manageProgramManagerStatus.equals(Constants.ACTIVE)) {
      if (!existingFeature.contains(FeatureConstants.ADMIN_SERVICE_PM_FEATURE_ID)) {
        session.invalidate();
        modelAndView.setViewName(INVALID_REQUEST_PAGE);
        return modelAndView;
      }
    } else if (manageProgramManagerStatus.equals(Constants.ACC_SUSPENDED)
        && !existingFeature.contains(FeatureConstants.ADMIN_SERVICE_PM_FEATURE_ID)) {
      session.invalidate();
      modelAndView.setViewName(INVALID_REQUEST_PAGE);
      return modelAndView;
    }
    try {
      setEnumValuesForSearchPage(model);
      ProgramManagerRequest programManagerRequest = new ProgramManagerRequest();
      model.put("programManagerRequest", programManagerRequest);
      programManagerRequest.setId(manageProgramManagerId);
      programManagerRequest.setStatus(manageProgramManagerStatus);
      programManagerRequest.setReason(reason);
      programManagerRequest.setUpdatedBy(session.getAttribute(Constants.LOGIN_USER_ID).toString());
      programManagerRequest.setDefaultProgramManager(Boolean.FALSE);
      Response respon = programManagerService.updateProgramManagerStatus(programManagerRequest);
      if (respon != null && respon.getErrorCode().equals(Constants.STATUS_CODE_SUCCESS)) {
        modelAndView.setViewName(PREPAID_ADMIN_SEARCH_PROGRAM_MANAGER_PAGE);
        modelAndView = showSearchProgramManager(request, response, model, session);
        modelAndView.addObject(Constants.SUCESS,
            messageSource.getMessage("prepaid.admin.changeprogrammanagerstatus.success.message",
                null, LocaleContextHolder.getLocale()));
      } else {
        modelAndView.setViewName(PREPAID_ADMIN_SEARCH_PROGRAM_MANAGER_PAGE);
        modelAndView = showSearchProgramManager(request, response, model, session);

        if (respon != null)
          modelAndView.addObject(Constants.ERROR, respon.getErrorMessage());
      }

    } catch (ChatakAdminException e) {
      modelAndView =
          processException(request, response, model, session, e, "changeProgramManagerStatus");
      model.put(Constants.ERROR, e.getMessage());
    } catch (Exception e) {
      modelAndView =
          processException(request, response, model, session, e, "changeProgramManagerStatus");
      model.put(Constants.ERROR, messageSource.getMessage("prepaid.admin.general.error.message",
          null, LocaleContextHolder.getLocale()));
    }
    logger.info("Exiting  :: ProgramManagerController:: changeProgramManagerStatus method");
    return modelAndView;
  }

  public void setEnumValuesForSearchPage(Map model) {
    model.put("pmstatusList", Arrays.asList(Constants.ACTIVE, Constants.ACC_SUSPENDED));
  }

  @RequestMapping(value = PREPAID_ADMIN_FETCH_CURRENCY, method = RequestMethod.GET)
  public @ResponseBody String fetchCurrency(HttpServletRequest request,
      HttpServletResponse response, HttpSession session, @FormParam("entityId") final Long entityId,
      @FormParam("entityType") final String entityType) {
    logger.info("Entering :: ProgramManagerController :: fetchCurrency ");
    try {
      if (entityType.equalsIgnoreCase(Constants.PROGRAM_MANAGER)) {

        ProgramManagerRequest programManagerRequest = new ProgramManagerRequest();
        programManagerRequest.setId(entityId);
        ProgramManagerResponse programManagerResponse =
            programManagerService.editProgramManager(programManagerRequest);

        if (programManagerResponse != null
            && StringUtil.isListNotNullNEmpty(programManagerResponse.getProgramManagersList())) {
          return JsonUtil
              .convertObjectToJSON(programManagerResponse.getProgramManagersList().get(0));
        }

      } else if (entityType.equalsIgnoreCase(Constants.PARTNER)) {
        PartnerRequest partnerRequest = new PartnerRequest();
        partnerRequest.setPartnerId(entityId);
        PartnerResponse partnerResponse = partnerService.findByPartnerId(partnerRequest);
        if (partnerResponse != null
            && StringUtil.isListNotNullNEmpty(partnerResponse.getPartnerList())) {
          return JsonUtil.convertObjectToJSON(partnerResponse.getPartnerList().get(0));
        }
      }
    } catch (Exception e) {
      logger.error("ERROR:: ProgramManagerController:: fetchCurrency method", e);
    }
    return null;
  }

  private void getLogo(ProgramManagerRequest programManagerRequest, MultipartFile file) {
    logger.info("Entering:: ProgramManagerController:: getLogo method");
    byte[] bytes = null;
    if (!file.isEmpty()) {
      try {
        bytes = file.getBytes();
      } catch (Exception e) {
        logger.error("ERROR:: ProgramManagerController:: getLogo method", e);
      }
    }
    programManagerRequest.setProgramManagerLogo(bytes);
    logger.info("Exiting:: ProgramManagerController:: getLogo method");
  }

  private void getLogo(ProgramManagerRequest programManagerRequest,
      MultipartFile file, HttpSession session) throws CloneNotSupportedException, IOException {
    logger.info("Entering:: ProgramManagerController:: getLogo method");
    byte[] bytes = null;
    if (!file.isEmpty()) {
      bytes = file.getBytes();
      programManagerRequest.setProgramManagerLogo(bytes);
    } else {
      bytes = (byte[]) session.getAttribute("programManagerImage");
      programManagerRequest.setProgramManagerLogo(bytes);

    }

    logger.info("Exiting:: ProgramManagerController:: getLogo method");
  }

  private void getPmPaginationList(HttpSession session, ModelAndView modelAndView,
      Integer programManagerPageData, ProgramManagerRequest sessionSearchList, Map model)
          throws ChatakAdminException {
    logger.info("Entering:: ProgramManagerController:: getPmPaginationList method");
    ProgramManagerResponse programMgrResponse =
        programManagerService.searchProgramManagerDetails(sessionSearchList);
    if (programMgrResponse != null) {
      List<ProgramManagerRequest> programManagerResponse =
          programMgrResponse.getProgramManagersList();
      for (ProgramManagerRequest programManagerRequest1 : programManagerResponse) {
        programManagerRequest1.setProgramManagerName(
            StringUtil.escapeHTMLChars(programManagerRequest1.getProgramManagerName()));
      }
      session.setAttribute(Constants.SEARCH_LIST, programManagerResponse);
      if (StringUtil.isListNotNullNEmpty(programManagerResponse)) {
        modelAndView =
            PaginationUtil.getPagenationModelSuccessive(modelAndView, programManagerPageData,
                programMgrResponse.getTotalNoOfRows(), sessionSearchList.getPageSize());
        model.put("programManagerRequest", sessionSearchList);
        modelAndView.addObject("searchList", programManagerResponse);
      }
    }
    logger.info("Exiting:: ProgramManagerController:: getPmPaginationList method");
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

  private void setDataForDownloadPmReport(List<ProgramManagerRequest> exportList,
      ExportDetails exportDetails) {
    exportDetails.setReportName("Program_Manager_");
    exportDetails.setHeaderMessageProperty("admin.Program.manager.report");

    exportDetails.setHeaderList(getPmAccountReportHeaderList());
    exportDetails.setFileData(getPmAccountReportFileData(exportList));
  }

  private List<String> getPmAccountReportHeaderList() {
    String[] headerArr =
        {messageSource.getMessage("admin.pm.Name.message", null, LocaleContextHolder.getLocale()),
            messageSource.getMessage("reports.label.transactions.companyname", null,
                LocaleContextHolder.getLocale()),
        messageSource.getMessage("admin.BusinessEntityName.message", null,
            LocaleContextHolder.getLocale()),
        messageSource.getMessage("home.label.status", null, LocaleContextHolder.getLocale()),};
    return new ArrayList<>(Arrays.asList(headerArr));
  }

  private static List<Object[]> getPmAccountReportFileData(List<ProgramManagerRequest> list) {
    List<Object[]> fileData = new ArrayList<>();

    for (ProgramManagerRequest request : list) {

      Object[] rowData = {request.getProgramManagerName(), request.getCompanyName(),
          request.getBusinessName(), request.getStatus()};
      fileData.add(rowData);
    }

    return fileData;
  }
  
  @RequestMapping(value = GET_ISSUANCE_PM_DETAILS_BY_PROGRAM_MANAGER_TYPE, method = RequestMethod.GET)
  public @ResponseBody String getIssunacePMDetailsByPmType(Map model, HttpServletRequest request,
      HttpServletResponse response, HttpSession session) {
    logger.info("Entering :: ProgramManagerController :: getPartnersByProgramManagerId method");
    try {
    	ProgramManagerResponse programManagerResponse =null;
    	ProgramManagerRequest programManagerRequest = new ProgramManagerRequest();
		programManagerRequest.setStatuses(Arrays.asList(Constants.ACTIVE));
		programManagerResponse = programManagerService.getAllIssuanceProgramManagers(programManagerRequest);
      if (programManagerResponse.getProgramManagersList() != null) {
        return JsonUtil.convertObjectToJSON(programManagerResponse.getProgramManagersList());
      }
    } catch (ChatakAdminException e) {
      logger.error("Error :: ProgramManagerController :: getPartnersByProgramManagerId method", e);
    }
    logger.info("Exiting :: ProgramManagerController :: getPartnersByProgramManagerId method");
    return null;
  }
  
  @RequestMapping(value = GET_ISSUANCE_PM_DETAILS_BY_PROGRAM_MANAGER_ID, method = RequestMethod.GET)
  public @ResponseBody String getIssunacePMDetailsByPmId(Map model, HttpServletRequest request,
      HttpServletResponse response, HttpSession session) {
    logger.info("Entering :: ProgramManagerController :: getIssunacePMDetailsByPmId method");
    String programManagerId = request.getParameter("programManagerId");
    long programManagerReq = Long.parseLong(programManagerId);
    try {
    	ProgramManagerRequest programManagerRequest = new ProgramManagerRequest();
		programManagerRequest.setProgramManagerId(programManagerReq);
		ProgramManagerResponse programManagerResponse = programManagerService.getIssuanceProgramManagerById(programManagerRequest);
      if (programManagerResponse.getProgramManagersList() != null) {
    	  byte[] image = programManagerResponse.getProgramManagersList().get(0).getProgramManagerLogo();
			String type = "jpg";
			String programManagerImage = encodeToString(image, type);
			programManagerResponse.getProgramManagersList().get(0).setIssuanceProgramManagerLogo(programManagerImage);
			return JsonUtil.convertObjectToJSON(programManagerResponse.getProgramManagersList());
      }
    } catch (ChatakAdminException e) {
      logger.error("Error :: ProgramManagerController :: getIssunacePMDetailsByPmId method", e);
    }
    logger.info("Exiting :: ProgramManagerController :: getIssunacePMDetailsByPmId method");
    return null;
  }
  
  @RequestMapping(value = GET_ISSUANCE_PM_CARD_PROGRAMS_BY_PROGRAM_MANAGER_ID, method = RequestMethod.GET)
  public @ResponseBody String getIssunacePMCardProgramsByPmId(Map model, HttpServletRequest request,
      HttpServletResponse response, HttpSession session) {
    logger.info("Entering :: ProgramManagerController :: getIssunacePMCardProgramsByPmId method");
    String programManagerId = request.getParameter("programManagerId");
   List<Long> programManagerReq = new ArrayList<>();
   programManagerReq.add(Long.parseLong(programManagerId));
    try {
    	PartnerGroupPartnerMapRequest partnerGroupPartnerMapRequest = new PartnerGroupPartnerMapRequest();
    	partnerGroupPartnerMapRequest.setProgramManagerId(programManagerReq);
    	CardProgramResponse cardProgramResponse = programManagerService.searchCardProgramByProgramManager(partnerGroupPartnerMapRequest);
      if (cardProgramResponse.getCardProgramList() != null) {
        return JsonUtil.convertObjectToJSON(cardProgramResponse.getCardProgramList());
      }
    } catch (ChatakAdminException e) {
      logger.error("Error :: ProgramManagerController :: getIssunacePMCardProgramsByPmId method", e);
    }
    logger.info("Exiting :: ProgramManagerController :: getIssunacePMCardProgramsByPmId method");
    return null;
  }
  
  public static String encodeToString(byte[] image, String type) {
		String imageString = null;
		String encodedImage = null;

		try {
			encodedImage = org.apache.commons.codec.binary.Base64.encodeBase64String(image);
			imageString = "data:image/" + type + ";base64," + encodedImage;
		} catch (Exception e) {
			logger.error("ERROR::method:: ProgramManagerController::encodeToString", e);
		}
		return imageString;
	}
  
  @RequestMapping(value = GET_INDEPENDENT_PM_DETAILS_PM_DETAILS_BY_PROGRAM_MANAGER_TYPE, method = RequestMethod.GET)
  public @ResponseBody String getIndependentPMDetails(Map model, HttpServletRequest request,
      HttpServletResponse response, HttpSession session) {
    logger.info("Entering :: ProgramManagerController :: getIndependentPMDetails method");
    try {
    	List<Option> currencyList = currencyConfigService.getCurrencyConfigCode();
      if (currencyList != null) {
			return JsonUtil.convertObjectToJSON(currencyList);
      }
    } catch (ChatakAdminException e) {
      logger.error("Error :: ProgramManagerController :: getIndependentPMDetails method", e);
    }
    logger.info("Exiting :: ProgramManagerController :: getIndependentPMDetails method");
    return null;
  }
  
  @RequestMapping(value = GET_BANK_DETAILS_BY_CURRENCY, method = RequestMethod.GET)
  public @ResponseBody String getBankDetailsByCurrency(Map model, HttpServletRequest request,
      HttpServletResponse response, HttpSession session) {
    ModelAndView modelAndView = new ModelAndView(CHATAK_ADMIN_CREATE_MERCHANT_PAGE);
    modelAndView.addObject(Constants.ERROR, null);
    logger.info("Entering :: ProgramManagerController :: getBankDetailsByCurrency method");
    String currency = request.getParameter("currency");
    Response currencyCodes = currencyConfigService.getCurrencyCodeNumeric(currency);
    Long currencyId = currencyCodes.getCurrencyId();
    try {
      Response responseVal = bankService.getBankName(currencyId);

      if (responseVal != null) {
        return JsonUtil.convertObjectToJSON(responseVal.getResponseList());
      }
    } catch (ChatakAdminException e) {
      logger.error("ERROR:: ProgramManagerController:: getBankDetailsByCurrency method", e);
      modelAndView.addObject(Constants.ERROR, messageSource
          .getMessage(Constants.CHATAK_GENERAL_ERROR, null, LocaleContextHolder.getLocale()));
    }
    logger.info("Exiting:: ProgramManagerController:: getBankDetailsByCurrency method");
    return null;
  }
  
  @RequestMapping(value = GET_CARD_PROGRAMS_BY_CURRENCY, method = RequestMethod.GET)
  public @ResponseBody String getCardProgramsByCurrency(Map model, HttpServletRequest request,
      HttpServletResponse response, HttpSession session) {
    ModelAndView modelAndView = new ModelAndView(CHATAK_ADMIN_CREATE_MERCHANT_PAGE);
    modelAndView.addObject(Constants.ERROR, null);
    logger.info("Entering :: ProgramManagerController :: getCardProgramsByCurrency");
    String currency =request.getParameter("currency");
    try {
    	Response responseVal	= cardProgramServices.getCardProgramsByCurrency(currency);
      if (responseVal != null) {
        return JsonUtil.convertObjectToJSON(responseVal.getResponseList());
      }
    } catch (ChatakAdminException e) {
      logger.error("Error :: ProgramManagerController :: getCardProgramsByCurrency :: " + e.getMessage(), e);
      modelAndView.addObject(Constants.ERROR, messageSource
          .getMessage(Constants.CHATAK_GENERAL_ERROR, null, LocaleContextHolder.getLocale()));
    }
    logger.info("Exiting :: ProgramManagerController :: getCardProgramsByCurrency");
    return null;
  }
  
  @RequestMapping(value = GET_PM_STATES_BY_COUNTRY_ID, method = RequestMethod.GET)
  public @ResponseBody String getPmStatesById(Map model, HttpServletRequest request,
      HttpServletResponse response, HttpSession session) {
    ModelAndView modelAndView = new ModelAndView(BANK_CREATE);
    modelAndView.addObject(Constants.ERROR, null);
    logger.info("Entering :: BankController :: getStatesById method");
    Long countryId = Long.valueOf(request.getParameter("countryid"));
    try {
      Response response2 = bankService.getPmCountryById(countryId);
      if (response2 != null) {
        return JsonUtil.convertObjectToJSON(response2);
      }
    } catch (ChatakAdminException e) {
      logger.error("ERROR:: BankController:: getStatesById method", e);
      modelAndView.addObject(Constants.ERROR, messageSource
          .getMessage(Constants.CHATAK_GENERAL_ERROR, null, LocaleContextHolder.getLocale()));
    }
    logger.info("Exiting:: BankController:: getStatesById method");
    return null;
  }
  
  
  @RequestMapping(value = FETCH_COUNTRY_TIME_ZONE, method = RequestMethod.GET)
	public @ResponseBody
	String fetchTimeZone(HttpServletRequest request,
			HttpServletResponse response, HttpSession session,
			Map<String, Object> model) {

	  logger.info("Entering :: ProgramManagerController :: fetchTimeZone method");

		Long countryId = Long.parseLong(request.getParameter("countryId"));
		if (countryId != null) {
			TimeZoneRequest timeZoneRequest = new TimeZoneRequest();
			timeZoneRequest.setCountryId(countryId);
			try {
				TimeZoneResponse timeZon = bankService.searchAllTimeZone(timeZoneRequest.getCountryId());
				logger.info("Exiting :: BankController :: fetchTimeZone method ");
				 if (timeZon != null) {
				        return JsonUtil.convertObjectToJSON(timeZon.getListOfTimeZoneRequests());
				      }
			} catch (ChatakAdminException ex4) {
				logger.info("Error :: BankController :: fetchTimeZone method "+ ex4);
				model.put(Constants.ERROR, ex4.getMessage());
			} catch (Exception ex1) {
				logger.info("Error :: BankController :: fetchTimeZone method ");
				model.put(Constants.ERROR, messageSource.getMessage("prepaid.admin.error.message", null, LocaleContextHolder.getLocale()));
			}
		}
		logger.info("Exiting :: ProgramManagerController :: fetchTimeZone method");
		return null;
	}
  
  @RequestMapping(value = GET_ISSUANCE_CARD_PROGRAMS_BY_PM_ID, method = RequestMethod.GET)
  public @ResponseBody String getIssuanceCardProgramsByPmId(Map model, HttpServletRequest request,
      HttpServletResponse response, HttpSession session) {
    logger.info("Entering :: ProgramManagerController :: getIssuanceCardProgramsByPmId method");
    String issuancePmId = request.getParameter("issuancePmId");
    String acquirerPmId = request.getParameter("acquirerPmId");
    String currency = request.getParameter("currency"); 
   List<Long> programManagerReq = new ArrayList<>();
   programManagerReq.add(Long.parseLong(issuancePmId));
    try {
        PartnerGroupPartnerMapRequest partnerGroupPartnerMapRequest = new PartnerGroupPartnerMapRequest();
        partnerGroupPartnerMapRequest.setProgramManagerId(programManagerReq);
      CardProgramResponse cardProgramResponse = programManagerService.searchIssuanceCardProgramByPM(partnerGroupPartnerMapRequest,
              currency, session.getAttribute(Constants.LOGIN_USER_ID).toString(), acquirerPmId);
      if (cardProgramResponse!=null && cardProgramResponse.getCardProgramList() != null) {
        return JsonUtil.convertObjectToJSON(cardProgramResponse);
      }
    } catch (Exception e) {
      logger.error("Error :: ProgramManagerController :: getIssuanceCardProgramsByPmId method", e);
    }
    logger.info("Exiting :: ProgramManagerController :: getIssuanceCardProgramsByPmId method");
    return null;
  }
}

