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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.chatak.acquirer.admin.constants.FeatureConstants;
import com.chatak.acquirer.admin.constants.URLMappingConstants;
import com.chatak.acquirer.admin.controller.model.ExportDetails;
import com.chatak.acquirer.admin.service.SwitchService;
import com.chatak.acquirer.admin.util.ExportUtil;
import com.chatak.acquirer.admin.util.PaginationUtil;
import com.chatak.acquirer.admin.util.StringUtil;
import com.chatak.pg.constants.ActionErrorCode;
import com.chatak.pg.enums.ExportType;
import com.chatak.pg.model.Switch;
import com.chatak.pg.user.bean.SwitchRequest;
import com.chatak.pg.user.bean.SwitchResponse;
import com.chatak.pg.util.Constants;

@SuppressWarnings({"rawtypes", "unchecked"})
@Controller
public class SwitchController implements URLMappingConstants {

  @Autowired
  private MessageSource messageSource;

  @Autowired
  private SwitchService switchService;

  private static Logger logger = Logger.getLogger(SwitchController.class);

  @RequestMapping(value = CHATAK_ADMIN_SWITCH_CREATE_PAGE, method = RequestMethod.GET)
  public ModelAndView showCreateSwitchPage(Map model, HttpSession session) {
    logger.info("Entering:: SwitchController:: showCreateSwitchPage method");
    ModelAndView modelAndView = new ModelAndView(CHATAK_ADMIN_SWITCH_CREATE_PAGE);
    String existingFeature = (String) session.getAttribute(Constants.EXISTING_FEATURES);
    if (!existingFeature.contains(FeatureConstants.ADMIN_SERVICE_SWITCH_CREATE_FEATURE_ID)) {
      session.invalidate();
      modelAndView.setViewName(INVALID_REQUEST_PAGE);
      return modelAndView;
    }
    modelAndView.addObject(Constants.ERROR, null);
    session.setAttribute(Constants.ERROR, null);
    Switch switchInfo = new Switch();
    model.put("switch", switchInfo);
    logger.info("Exiting:: SwitchController:: showCreateSwitchPage method");
    return modelAndView;
  }

  @RequestMapping(value = CHATAK_ADMIN_SWITCH_CREATE, method = RequestMethod.POST)
  public ModelAndView createSwitch(HttpServletRequest request, HttpServletResponse response,
      Switch switchInfo, BindingResult bindingResult, Map model, HttpSession session) {
    logger.info("Entering:: SwitchController:: createSwitch method");
    ModelAndView modelAndView = new ModelAndView(CHATAK_ADMIN_SWITCH_CREATE_PAGE);
    String existingFeature = (String) session.getAttribute(Constants.EXISTING_FEATURES);
    if (!existingFeature.contains(FeatureConstants.ADMIN_SERVICE_SWITCH_CREATE_FEATURE_ID)) {
      session.invalidate();
      modelAndView.setViewName(INVALID_REQUEST_PAGE);
      return modelAndView;
    }
    modelAndView.addObject(Constants.ERROR, null);
    try {
      Long userid = (Long) session.getAttribute(Constants.LOGIN_USER_ID);
      SwitchResponse addSwitchResponse =
          switchService.addSwitchInformation(switchInfo, String.valueOf(userid));
      if (null != addSwitchResponse
          && addSwitchResponse.getErrorCode().equals(ActionErrorCode.ERROR_CODE_00)) {
        modelAndView = showSearchSwitchPage(request, response, new SwitchRequest(), bindingResult,
            model, session);

        modelAndView.addObject(Constants.SUCESS, messageSource
            .getMessage("chatak.switch.create.success", null, LocaleContextHolder.getLocale()));

      } else if (null != addSwitchResponse
          && addSwitchResponse.getErrorCode().equals(ActionErrorCode.ERROR_CODE_Z11)) {
        model.put("switch", switchInfo);
        modelAndView.addObject(Constants.ERROR, messageSource
            .getMessage("chatak.duplicate.switch.name", null, LocaleContextHolder.getLocale()));
        return modelAndView;
      } else {
        modelAndView.addObject(Constants.ERROR, messageSource
            .getMessage(ActionErrorCode.ERROR_CODE_Z5, null, LocaleContextHolder.getLocale()));
      }
    } catch (Exception e) {
      logger.error("ERROR:: SwitchController:: createSwitch method", e);
      modelAndView.addObject(Constants.ERROR,
          messageSource.getMessage("chatak.general.error", null, LocaleContextHolder.getLocale()));
    }
    model.put("switch", new Switch());
    logger.info("Exiting:: SwitchController:: createSwitch method");
    return modelAndView;
  }

  @RequestMapping(value = CHATAK_ADMIN_SEARCH_SWITCH_PAGE, method = RequestMethod.GET)
  public ModelAndView showSearchSwitchPage(HttpServletRequest request, HttpServletResponse response,
      SwitchRequest searchSwitchRequest, BindingResult bindingResult, Map model,
      HttpSession session) {
    logger.info("Entering:: SwitchController:: showSearchSwitchPage method");
    ModelAndView modelAndView = new ModelAndView(CHATAK_ADMIN_SEARCH_SWITCH_PAGE);
    String existingFeature = (String) session.getAttribute(Constants.EXISTING_FEATURES);
    if (!existingFeature.contains(FeatureConstants.ADMIN_SERVICE_SWITCH_FEATURE_ID)) {
      session.invalidate();
      modelAndView.setViewName(INVALID_REQUEST_PAGE);
      return modelAndView;
    }
    modelAndView.addObject(Constants.ERROR, null);
    session.setAttribute(Constants.ERROR, null);
    session.setAttribute(Constants.SWITCH_INFO, searchSwitchRequest);
    searchSwitchRequest.setPageSize(Constants.INITIAL_ENTITIES_PORTAL_DISPLAY_SIZE);
    searchSwitchRequest.setPageIndex(Constants.ONE);
    String succMsg = request.getParameter("succMsg");
    try {
      SwitchResponse searchSwitchResponse =
          switchService.searchSwitchInformation(searchSwitchRequest);
      if (searchSwitchResponse != null) {
        List<SwitchRequest> searchSwitchRequestList = new ArrayList<>();
        modelAndView.addObject(Constants.PAGE_SIZE, null);
        modelAndView.addObject(Constants.SWITCH_INFO, searchSwitchRequestList);
      }
      modelAndView.addObject(Constants.SUCESS, succMsg);
    } catch (Exception e) {
      modelAndView.addObject(Constants.ERROR,
          messageSource.getMessage(Constants.CHATAK_GENERAL_ERROR, null, LocaleContextHolder.getLocale()));
      logger.error("ERROR:: SwitchController:: showSearchSwitchPage method", e);
    }
    modelAndView.addObject("flag", false);
    model.put("switch", searchSwitchRequest);
    logger.info("Exiting:: SwitchController:: showSearchSwitchPage method");
    return modelAndView;
  }

  @RequestMapping(value = CHATAK_ADMIN_SEARCH_SWITCH, method = RequestMethod.POST)
  public ModelAndView searchSwitchInfo(HttpServletRequest request, HttpServletResponse response,
      SwitchRequest searchSwitchRequest, Map model, HttpSession session) {
    logger.info("Entering:: SwitchController:: searchSwitchInfo method");
    ModelAndView modelAndView = new ModelAndView(CHATAK_ADMIN_SEARCH_SWITCH_PAGE);
    String existingFeature = (String) session.getAttribute(Constants.EXISTING_FEATURES);
    if (!existingFeature.contains(FeatureConstants.ADMIN_SERVICE_SWITCH_FEATURE_ID)) {
      session.invalidate();
      modelAndView.setViewName(INVALID_REQUEST_PAGE);
      return modelAndView;
    }
    modelAndView.addObject(Constants.ERROR, null);
    session.setAttribute(Constants.ERROR, null);
    searchSwitchRequest.setPageIndex(Constants.ONE);
    session.setAttribute(Constants.SWITCH_INFO, searchSwitchRequest);
    try {
      SwitchResponse searchSwitchResponse =
          switchService.searchSwitchInformation(searchSwitchRequest);
      List<SwitchRequest> searchSwitchRequestList = new ArrayList<>();
      if (searchSwitchResponse != null
          && !CollectionUtils.isEmpty(searchSwitchResponse.getSwitchRequest())) {
        searchSwitchRequestList = searchSwitchResponse.getSwitchRequest();
        modelAndView.addObject(Constants.PAGE_SIZE, searchSwitchRequest.getPageSize());
        modelAndView = PaginationUtil.getPagenationModel(modelAndView,
            searchSwitchResponse.getTotalNoOfRows());
        session.setAttribute("pageNumber", Constants.ONE);
      }
      if (searchSwitchResponse != null) {
        session.setAttribute(Constants.TOTAL_RECORDS, searchSwitchResponse.getTotalNoOfRows());
      } else {
        session.setAttribute(Constants.TOTAL_RECORDS, Constants.ZERO);
      }
      modelAndView.addObject(Constants.SWITCH_INFO, searchSwitchRequestList);

    } catch (Exception e) {
      modelAndView.addObject(Constants.ERROR,
          messageSource.getMessage(Constants.CHATAK_GENERAL_ERROR, null, LocaleContextHolder.getLocale()));
      logger.error("ERROR:: SwitchController:: searchSwitchInfo method", e);
    }
    modelAndView.addObject("flag", true);
    model.put("switch", searchSwitchRequest);
    logger.info("Exiting:: SwitchController:: searchSwitchInfo method");
    return modelAndView;
  }

  @RequestMapping(value = CHATAK_ADMIN_SHOW_EDIT_SWITCH, method = RequestMethod.POST)
  public ModelAndView showEditSwitch(HttpServletRequest request, HttpServletResponse response,
      @FormParam("getSwitchId") final Long getSwitchId, HttpSession session, Map model) {
    logger.info("Entering :: SwitchController :: showEditSwitch method ");
    ModelAndView modelAndView = new ModelAndView(CHATAK_ADMIN_EDIT_SWITCH);
    String existingFeature = (String) session.getAttribute(Constants.EXISTING_FEATURES);
    if (!existingFeature.contains(FeatureConstants.ADMIN_SERVICE_SWITCH_EDIT_FEATURE_ID)) {
      session.invalidate();
      modelAndView.setViewName(INVALID_REQUEST_PAGE);
      return modelAndView;
    }
    modelAndView.addObject(Constants.ERROR, null);
    modelAndView.addObject(Constants.SUCESS, null);
    try {
      SwitchRequest switchInfo = switchService.getSwtichInformationById(getSwitchId);
      model.put("switch", switchInfo);
    } catch (Exception e) {
      logger.error("ERROR:: SwitchController:: showEditSwitch method", e);
      modelAndView.addObject(Constants.ERROR,
          messageSource.getMessage(Constants.CHATAK_GENERAL_ERROR, null, LocaleContextHolder.getLocale()));
    }
    logger.info("Exiting :: SwitchController :: showEditSwitch");
    return modelAndView;
  }

  @RequestMapping(value = CHATAK_ADMIN_UPDATE_SWITCH, method = RequestMethod.POST)
  public ModelAndView updateSwitch(HttpServletRequest request, HttpServletResponse response,
      SwitchRequest updateSwitchRequest, BindingResult bindingResult, HttpSession session,
      Map model) {
    logger.info("Entering :: SwitchController :: updateSwitch method");
    ModelAndView modelAndView = new ModelAndView(CHATAK_ADMIN_SEARCH_SWITCH_PAGE);
    String existingFeature = (String) session.getAttribute(Constants.EXISTING_FEATURES);
    if (!existingFeature.contains(FeatureConstants.ADMIN_SERVICE_SWITCH_EDIT_FEATURE_ID)) {
      session.invalidate();
      modelAndView.setViewName(INVALID_REQUEST_PAGE);
      return modelAndView;
    }
    modelAndView.addObject(Constants.ERROR, null);
    session.setAttribute(Constants.ERROR, null);
    updateSwitchRequest.setPageSize(Constants.MAX_ENTITIES_PORTAL_DISPLAY_SIZE);
    updateSwitchRequest.setPageIndex(Constants.ONE);
    try {
      Long userid = (Long) session.getAttribute(Constants.LOGIN_USER_ID);
      SwitchResponse updateSwitchResponse =
          switchService.updateSwitchInformation(updateSwitchRequest, String.valueOf(userid));
      if (updateSwitchResponse != null
          && updateSwitchResponse.getErrorCode().equals(ActionErrorCode.ERROR_CODE_00)) {
        modelAndView = showSearchSwitchPage(request, response, new SwitchRequest(), bindingResult,
            model, session);
        modelAndView.addObject(Constants.SUCESS, messageSource.getMessage(
            "chatak.admin.updateswitch.success.message", null, LocaleContextHolder.getLocale()));
      } else {
        modelAndView.addObject(Constants.ERROR, messageSource
            .getMessage(ActionErrorCode.ERROR_CODE_Z5, null, LocaleContextHolder.getLocale()));
      }
    } catch (Exception e) {
      logger.error("ERROR:: SwitchController:: updateSwitch method", e);
      modelAndView.addObject(Constants.ERROR,
          messageSource.getMessage(Constants.CHATAK_GENERAL_ERROR, null, LocaleContextHolder.getLocale()));
    }
    logger.info("Exiting:: SwitchController:: updateSwitch method");
    return modelAndView;
  }

  @RequestMapping(value = CHATAK_ADMIN_VIEW_SWITCH_PAGE, method = RequestMethod.POST)
  public ModelAndView showViewSwitch(HttpServletRequest request, HttpServletResponse response,
      @FormParam("switchViewId") final Long switchViewId, HttpSession session, Map model) {
    logger.info("Entering :: SwitchController :: showViewSwitch method ");
    ModelAndView modelAndView = new ModelAndView(CHATAK_ADMIN_VIEW_SWITCH_PAGE);
    String existingFeature = (String) session.getAttribute(Constants.EXISTING_FEATURES);
    if (!existingFeature.contains(FeatureConstants.ADMIN_SERVICE_SWITCH_VIEW_FEATURE_ID)) {
      session.invalidate();
      modelAndView.setViewName(INVALID_REQUEST_PAGE);
      return modelAndView;
    }
    modelAndView.addObject(Constants.ERROR, null);
    modelAndView.addObject(Constants.SUCESS, null);
    try {
      SwitchRequest switchInfo = switchService.getSwtichInformationById(switchViewId);
      model.put("switch", switchInfo);
    } catch (Exception e) {
      logger.error("ERROR:: SwitchController:: showViewSwitch method", e);
      modelAndView.addObject(Constants.ERROR,
          messageSource.getMessage(Constants.CHATAK_GENERAL_ERROR, null, LocaleContextHolder.getLocale()));
    }
    logger.info("Exiting :: SwitchController :: showViewSwitch");
    return modelAndView;
  }

  @RequestMapping(value = CHATAK_ADMIN_SWITCH_PAGINATION, method = RequestMethod.POST)
  public ModelAndView getPaginationList(final HttpSession session,
      @FormParam("pageNumber") final Integer pageNumber,
      @FormParam(Constants.TOTAL_RECORDS) final Integer totalRecords, Map model) {
    logger.info("Entering:: SwitchController:: getPaginationList method");
    ModelAndView modelAndView = new ModelAndView(CHATAK_ADMIN_SEARCH_SWITCH_PAGE);
    SwitchResponse searchResponse = null;
    List<SwitchRequest> switchSearchList = null;
    try {
      SwitchRequest switchInfo = (SwitchRequest) session.getAttribute(Constants.SWITCH_INFO);
      model.put("switch", switchInfo);
      switchInfo.setPageIndex(pageNumber);
      switchInfo.setNoOfRecords(totalRecords);
      try {
        searchResponse = switchService.searchSwitchInformation(switchInfo);
        if (searchResponse != null && !CollectionUtils.isEmpty(searchResponse.getSwitchRequest())) {
          switchSearchList = searchResponse.getSwitchRequest();
          modelAndView.addObject(Constants.PAGE_SIZE, switchInfo.getPageSize());
          modelAndView = PaginationUtil.getPagenationModelSuccessive(modelAndView, pageNumber,
              searchResponse.getTotalNoOfRows());
          session.setAttribute(Constants.PAGE_NUMBER, pageNumber);
          session.setAttribute(Constants.TOTAL_RECORDS, totalRecords);
        }
        modelAndView.addObject(Constants.SWITCH_INFO, switchSearchList);
      } catch (Exception e) {
        modelAndView.addObject(Constants.ERROR, messageSource.getMessage(Constants.CHATAK_GENERAL_ERROR,
            null, LocaleContextHolder.getLocale()));
        logger.error("ERROR:: SwitchController:: searchMerchant method", e);
      }
    } catch (Exception e) {
      logger.error("ERROR:: SwitchController:: getPaginationList method", e);
      modelAndView.addObject(Constants.ERROR,
          messageSource.getMessage(Constants.CHATAK_GENERAL_ERROR, null, LocaleContextHolder.getLocale()));
    }
    logger.info("Exiting:: SwitchController:: getPaginationList method");
    return modelAndView;
  }

  @RequestMapping(value = CHATAK_ADMIN_SWITCH_REPORT, method = RequestMethod.POST)
  public ModelAndView downloadSwitchReport(HttpSession session, Map model,
      HttpServletRequest request, @FormParam("downLoadPageNumber") final Integer downLoadPageNumber,
      HttpServletResponse response, @FormParam("downloadType") final String downloadType,
      @FormParam(Constants.TOTAL_RECORDS) final Integer totalRecords,
      @FormParam("downloadAllRecords") final boolean downloadAllRecords) {
    logger.info("Entering:: SwitchController:: downloadSwitchReport method");
    ModelAndView modelAndView = new ModelAndView(CHATAK_ADMIN_SEARCH_SWITCH_PAGE);
    SwitchRequest switchInfo = null;
    SwitchResponse searchResponse = null;
    List<SwitchRequest> list = null;
    try {
      switchInfo = (SwitchRequest) session.getAttribute(Constants.SWITCH_INFO);
      switchInfo.setPageIndex(downLoadPageNumber);
      Integer pageSize = switchInfo.getPageSize();

      if (downloadAllRecords) {
        switchInfo.setPageIndex(Constants.ONE);
        switchInfo.setPageSize(totalRecords);
      }
      searchResponse = switchService.searchSwitchInformation(switchInfo);
      list = searchResponse.getSwitchRequest();

      ExportDetails exportDetails = new ExportDetails();
      if (StringUtil.isListNotNullNEmpty(list)) {
        if (Constants.PDF_FILE_FORMAT.equalsIgnoreCase(downloadType)) {
        	exportDetails.setExportType(ExportType.PDF);
        } else if (Constants.XLS_FILE_FORMAT.equalsIgnoreCase(downloadType)) {
        	exportDetails.setExportType(ExportType.XLS);
			exportDetails.setExcelStartRowNumber(Integer.parseInt("5"));
        }
        setExportDetailsDataForDownloadRoleReport(list, exportDetails);	
	 	ExportUtil.exportData(exportDetails, response, messageSource);
      }
      switchInfo.setPageSize(pageSize);
    } catch (Exception e) {
      modelAndView.addObject(Constants.ERROR,
          messageSource.getMessage(Constants.CHATAK_GENERAL_ERROR, null, LocaleContextHolder.getLocale()));
      logger.error("ERROR:: SwitchController:: downloadSwitchReport method", e);
    }
    logger.info("Exiting:: SwitchController:: downloadSwitchReport method");
    return null;
  }
  
  private void setExportDetailsDataForDownloadRoleReport(List<SwitchRequest> switchData,
      ExportDetails exportDetails) {
    exportDetails.setReportName("Switch_");
    exportDetails.setHeaderMessageProperty("chatak.header.switch.messages");

    exportDetails.setHeaderList(getSwitchHeaderList());
    exportDetails.setFileData(getSwitchFileData(switchData));
  }

  @RequestMapping(value = SWITCH_ACTIVATION_SUSPENTION, method = RequestMethod.POST)
  public ModelAndView changeSwitchStatus(final HttpSession session,
      @FormParam("suspendActiveId") final Long switchId,
      @FormParam("suspendActiveStatus") final String switchStatus,
      @FormParam("reason") final String reason, Map<String, Serializable> model,
      HttpServletRequest request, HttpServletResponse response) {
    logger.info("Entering:: SwitchController:: changeSwitchStatus method");

    ModelAndView modelAndView = new ModelAndView(CHATAK_ADMIN_SEARCH_SWITCH_PAGE);
    String existingFeature = (String) session.getAttribute(Constants.EXISTING_FEATURES);
    if (!existingFeature.contains(FeatureConstants.ADMIN_SERVICE_SWITCH_FEATURE_ID)) {
      session.invalidate();
      modelAndView.setViewName(INVALID_REQUEST_PAGE);
      return modelAndView;
    }
    try {
      SwitchRequest switchRequest = new SwitchRequest();
      switchRequest.setId(switchId);
      switchRequest.setReason(reason);
      SwitchResponse updateSwitchResponse =
          switchService.changeSwitchStatus(switchRequest, switchStatus);
      modelAndView = getPaginationList(session,
          StringUtil.isNull((Integer) session.getAttribute(Constants.PAGE_NUMBER)) ? 1
              : (Integer) session.getAttribute(Constants.PAGE_NUMBER),
          (Integer) session.getAttribute(Constants.TOTAL_RECORDS), model);
      if (ActionErrorCode.ERROR_CODE_00.equals(updateSwitchResponse.getErrorCode())) {
        model.put(Constants.SUCESS, messageSource.getMessage(
            "chatak.admin.updateswitch.status.sucess", null, LocaleContextHolder.getLocale()));
      } else {
        model.put(Constants.ERROR, messageSource.getMessage(Constants.CHATAK_GENERAL_ERROR, null,
            LocaleContextHolder.getLocale()));
      }
    } catch (Exception e) {
      logger.error("ERROR:: SwitchController:: changeSwitchStatus method", e);
      model.put(Constants.ERROR,
          messageSource.getMessage(Constants.CHATAK_GENERAL_ERROR, null, LocaleContextHolder.getLocale()));
    }
    logger.info("Exiting:: SwitchController:: changeSwitchStatus method");
    return modelAndView;
  }
  
  private List<String> getSwitchHeaderList() {
    String[] headerArr = {
        messageSource.getMessage("switch-file-exportutil-switchname", null,
            LocaleContextHolder.getLocale()),
        messageSource.getMessage("switch-file-exportutil-switchtype", null,
            LocaleContextHolder.getLocale()),
        messageSource.getMessage("switch-file-exportutil-primaryswitchURL", null,
            LocaleContextHolder.getLocale()),
        messageSource.getMessage("switch-file-exportutil-primaryswitchport", null,
            LocaleContextHolder.getLocale()),
        messageSource.getMessage("switch-file-exportutil-secondaryswitchURL", null,
            LocaleContextHolder.getLocale()),
        messageSource.getMessage("switch-file-exportutil-secondaryswitchport", null,
            LocaleContextHolder.getLocale()),
        messageSource.getMessage("switch-file-exportutil-priority", null,
            LocaleContextHolder.getLocale()),
        messageSource.getMessage("switch-file-exportutil-status", null,
            LocaleContextHolder.getLocale())};
    return new ArrayList<String>(Arrays.asList(headerArr));
  }

  private static List<Object[]> getSwitchFileData(List<SwitchRequest> switchData) {
    List<Object[]> fileData = new ArrayList<Object[]>();

    for (SwitchRequest swData : switchData) {

      Object[] rowData = {swData.getSwitchName(), swData.getSwitchType(),
          swData.getPrimarySwitchURL(), swData.getPrimarySwitchPort(),
          swData.getSecondarySwitchURL(), swData.getSecondarySwitchPort(),
          swData.getPriority(), swData.getStatusDisp()};
      fileData.add(rowData);
    }

    return fileData;
  }
}
