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
import com.chatak.acquirer.admin.controller.model.Option;
import com.chatak.acquirer.admin.exception.ChatakAdminException;
import com.chatak.acquirer.admin.service.BankService;
import com.chatak.acquirer.admin.service.CurrencyConfigService;
import com.chatak.acquirer.admin.service.PartnerService;
import com.chatak.acquirer.admin.service.ProgramManagerService;
import com.chatak.acquirer.admin.util.ExportUtil;
import com.chatak.acquirer.admin.util.JsonUtil;
import com.chatak.acquirer.admin.util.PaginationUtil;
import com.chatak.acquirer.admin.util.StringUtil;
import com.chatak.pg.bean.Request;
import com.chatak.pg.bean.Response;
import com.chatak.pg.enums.ExportType;
import com.chatak.pg.user.bean.BankProgramManagerMapRequest;
import com.chatak.pg.user.bean.BankResponse;
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
  private BankService bankService;

  @Autowired
  private CurrencyConfigService currencyConfigService;

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
      session.setAttribute("currencyList", currencyCodeList);
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
  @RequestMapping(value = PREPAID_ADMIN_CREATE_PROGRAM_MANAGER, method = RequestMethod.POST)
  public ModelAndView processCreateProgramManager(HttpServletRequest request,
      HttpServletResponse response, Map<String, Object> model, HttpSession session,
      @RequestParam("programManagerLogo") MultipartFile file,
      @FormParam("checkDefaultPMValue") final String checkDefaultPMValue,
      ProgramManagerRequest programManagerRequest, BindingResult bindingResult)
          throws CloneNotSupportedException {
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
      logger.info("Entering:: getFileAndBankData method");
      getFileAndBankData(programManagerRequest, file);
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
      } else if (programManagerCreateResponse != null && programManagerCreateResponse.getErrorCode()
          .equals(Constants.PROGRAM_MANAGER_NAME_EXIST)) {
        modelAndView = showCreateProgramManager(request, response, model, session);
        model.put("programManagerRequest", programManagerRequest);
        modelAndView.addObject(Constants.ERROR, programManagerCreateResponse.getErrorMessage());
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
      model.put(Constants.ERROR, e.getMessage());
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
      setEnumValuesForSearchPage(model);
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
      programManagerRequest.setId(programManagerId);
      setAuditData(programManagerRequest, session, "ProgramManager", "View");
      ProgramManagerResponse programManagerResponse =
          programManagerService.editProgramManager(programManagerRequest);
      List<ProgramManagerRequest> programManagerList =
          programManagerResponse.getProgramManagersList();
      defaultPMValue = programManagerList.get(0).getDefaultProgramManager();
      model.put("selectedBankList", programManagerList.get(0).getBankProgramManagerMapRequests());
      model.put("programManagerRequest", programManagerList.get(0));
      List<Option> bankOptions = bankService.getBankData();
      modelAndView.addObject("bankList", bankOptions);
      byte[] image = programManagerList.get(0).getProgramManagerLogo();
      session.setAttribute("programManagerImage",
          programManagerList.get(0).getProgramManagerLogo());
      model.put("imageData", image);
      String type = "jpg";
      String traineeImage = StringUtil.encodeToString(image, type);
      model.put("image", traineeImage);

      // to get the list of currency
      List<Option> currencyCodeList = currencyConfigService.getCurrencyConfigCode();
      modelAndView.addObject("currencyList", currencyCodeList);
      session.setAttribute("currencyList", currencyCodeList);
    } catch (Exception e) {
      logger.error("ERROR:: ProgramManagerController:: showEditProgramManager method", e);
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
      logger.info("Entering:: getFileAndBankDataForUpdatePm method");
      getFileAndBankDataForUpdatePm(programManagerRequest, file, session);
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
          && respon2.getErrorCode().equals(Constants.PROGRAM_MANAGER_NAME_EXIST)) {
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

  private void getFileAndBankData(ProgramManagerRequest programManagerRequest, MultipartFile file) {
    logger.info("Entering:: ProgramManagerController:: getFileAndBankData method");
    java.util.List<BankProgramManagerMapRequest> bankRequestList = new ArrayList<>();
    byte[] bytes = null;
    if (!file.isEmpty()) {
      try {
        bytes = file.getBytes();
      } catch (Exception e) {
        logger.error("ERROR:: ProgramManagerController:: getFileAndBankData method", e);
      }
    }
    String[] arrayList = programManagerRequest.getBankNames().split(",");
    for (int i = 0; i < arrayList.length; i++) {
      if (arrayList[i] != null) {
        BankProgramManagerMapRequest bankProgramManagerMapRequest =
            new BankProgramManagerMapRequest();
        bankProgramManagerMapRequest.setBankId(Long.valueOf(arrayList[i]));
        bankRequestList.add(bankProgramManagerMapRequest);
        
        programManagerRequest.setBankProgramManagerMapRequests(bankRequestList);
      }
    }
    programManagerRequest.setProgramManagerLogo(bytes);
    logger.info("Exiting:: ProgramManagerController:: getFileAndBankData method");
  }

  private void getFileAndBankDataForUpdatePm(ProgramManagerRequest programManagerRequest,
      MultipartFile file, HttpSession session) throws CloneNotSupportedException, IOException {
    logger.info("Entering:: ProgramManagerController:: getFileAndBankDataForUpdatePm method");
    java.util.List<BankProgramManagerMapRequest> bankRequestList = new ArrayList<>();

    byte[] bytes = null;
    if (!file.isEmpty()) {
      bytes = file.getBytes();
      programManagerRequest.setProgramManagerLogo(bytes);
    } else {
      bytes = (byte[]) session.getAttribute("programManagerImage");
      programManagerRequest.setProgramManagerLogo(bytes);

    }

    String[] arrayList2 = programManagerRequest.getBankNames().split(",");
    for (int i = 0; i < arrayList2.length; i++) {
      if (arrayList2[i] != null) {
        BankProgramManagerMapRequest bankProgramManagerMapRequest =
            new BankProgramManagerMapRequest();
        bankProgramManagerMapRequest.setBankId(Long.valueOf(arrayList2[i]));
        bankRequestList.add(bankProgramManagerMapRequest);
        programManagerRequest.setBankProgramManagerMapRequests(bankRequestList);
      }
    }
    logger.info("Exiting:: ProgramManagerController:: getFileAndBankDataForUpdatePm method");
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
}

