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
import org.springframework.validation.BindingResult;
import org.springframework.validation.DataBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.chatak.acquirer.admin.constants.FeatureConstants;
import com.chatak.acquirer.admin.constants.URLMappingConstants;
import com.chatak.acquirer.admin.controller.model.ExportDetails;
import com.chatak.acquirer.admin.controller.model.LoginResponse;
import com.chatak.acquirer.admin.exception.ChatakAdminException;
import com.chatak.acquirer.admin.model.CAPublicKeysResponse;
import com.chatak.acquirer.admin.model.Response;
import com.chatak.acquirer.admin.service.CAPublicKeysService;
import com.chatak.acquirer.admin.util.ExportUtil;
import com.chatak.acquirer.admin.util.JsonUtil;
import com.chatak.acquirer.admin.util.PaginationUtil;
import com.chatak.acquirer.admin.util.StringUtil;
import com.chatak.pg.acq.dao.model.PGCaPublicKeys;
import com.chatak.pg.bean.PublickeyNameResponse;
import com.chatak.pg.constants.ActionErrorCode;
import com.chatak.pg.enums.ExportType;
import com.chatak.pg.model.CAPublicKeysDTO;
import com.chatak.pg.util.Constants;
import com.chatak.pg.util.Properties;

@SuppressWarnings({"unchecked", "rawtypes"})
@Controller
public class CAPublicKeysController implements URLMappingConstants {

  @Autowired
  MessageSource messageSource;
  private static Logger logger = Logger.getLogger(CAPublicKeysController.class);

  @Autowired
  private CAPublicKeysService caPublicKeysService;

  @RequestMapping(value = SHOW_CA_PUBLIC_KEYS_SEARCH_PAGE, method = RequestMethod.GET)
  public ModelAndView showCAPublicKeysSearchPage(HttpServletRequest request,
      HttpServletResponse response, Map model, HttpSession session) {
    logger.info("Entering:: CAPublicKeysController:: showCAPublicKeysSearchPage method");
    ModelAndView modelAndView = new ModelAndView(CA_PUBLIC_KEYS_SEARCH_PAGE);
    String existingFeature = (String) session.getAttribute(Constants.EXISTING_FEATURES);
    if (!existingFeature.contains(FeatureConstants.ADMIN_SERVICE_CA_PUBLIC_KEYS_FEATURE_ID)) {
      session.invalidate();
      modelAndView.setViewName(INVALID_REQUEST_PAGE);
      return modelAndView;
    }
    try {
      CAPublicKeysDTO caPublicKeysDTO = new CAPublicKeysDTO();
      model.put(Constants.CAPUBLIC_KEYSDTO, caPublicKeysDTO);
      caPublicKeysDTO.setPageIndex(Constants.ONE);
      caPublicKeysDTO.setPageSize(Constants.INITIAL_ENTITIES_PORTAL_DISPLAY_SIZE);
      session.setAttribute(Constants.PUBLIC_KEY_REQUEST, caPublicKeysDTO);
    } catch (Exception e) {
      logger.error("ERROR:: CAPublicKeysController:: showCAPublicKeysSearchPage method", e);
      modelAndView.addObject(Constants.ERROR, messageSource
          .getMessage(Constants.CHATAK_GENERAL_ERROR, null, LocaleContextHolder.getLocale()));
    }
    modelAndView.addObject("flag", false);
    logger.info("Exiting:: CAPublicKeysController:: showCAPublicKeysSearchPage method");
    return modelAndView;
  }

  @RequestMapping(value = CA_PUBLIC_KEYS_SEARCH, method = RequestMethod.POST)
  public ModelAndView caPublicKeysSearch(HttpServletRequest request, HttpServletResponse response,
      CAPublicKeysDTO caPublicKeysDTO, BindingResult bindingResult, Map model,
      @FormParam("expiryDate") final String expiryDate, HttpSession session) {
    logger.info("Entering:: CAPublicKeysController:: caPublicKeysSearch method");
    ModelAndView modelAndView = new ModelAndView(CA_PUBLIC_KEYS_SEARCH_PAGE);
    String existingFeature = (String) session.getAttribute(Constants.EXISTING_FEATURES);
    if (!existingFeature.contains(FeatureConstants.ADMIN_SERVICE_CA_PUBLIC_KEYS_FEATURE_ID)) {
      session.invalidate();
      modelAndView.setViewName(INVALID_REQUEST_PAGE);
      return modelAndView;
    }

    logger.info("Entering:: CAPublicKeysController:: caPublicKeysSearch method");
    caPublicKeysDTO.setExpiryDate(expiryDate);
    try {
      model.put(Constants.CAPUBLIC_KEYSDTO, caPublicKeysDTO);
      caPublicKeysDTO.setPageIndex(Constants.ONE);
      session.setAttribute(Constants.PUBLIC_KEY_REQUEST, caPublicKeysDTO);
      CAPublicKeysResponse caPublicKeysResponse =
          caPublicKeysService.searchCAPublicKeys(caPublicKeysDTO);
      List<CAPublicKeysDTO> searchList = caPublicKeysResponse.getCaPublicKeysList();
      modelAndView.addObject(Constants.PAGE_SIZE, caPublicKeysDTO.getPageSize());
      modelAndView =
          PaginationUtil.getPagenationModel(modelAndView, caPublicKeysResponse.getTotalNoOfRows());
      session.setAttribute(Constants.PAGE_NUMBER, Constants.ONE);
      session.setAttribute(Constants.TOTAL_RECORDS, caPublicKeysResponse.getTotalNoOfRows());
      modelAndView.addObject(Constants.SEARCH_LIST, searchList);

    } catch (ChatakAdminException e) {
      logger.error("ERROR:: CAPublicKeysController:: caPublicKeysSearch method1", e);
      modelAndView = caPublicKeysSearch(request, response, caPublicKeysDTO, bindingResult, model,
          expiryDate, session);
      modelAndView.addObject(Constants.ERROR, e.getMessage());
    } catch (Exception e) {
      logger.error("ERROR:: CAPublicKeysController:: caPublicKeysSearch method2", e);
      modelAndView = caPublicKeysSearch(request, response, caPublicKeysDTO, bindingResult, model,
          expiryDate, session);
      modelAndView.addObject(Constants.ERROR, messageSource
          .getMessage(Constants.CHATAK_GENERAL_ERROR, null, LocaleContextHolder.getLocale()));
    }
    modelAndView.addObject("flag", true);
    logger.info("Exit:: CAPublicKeysController:: searchCAPublicKeys method");
    return modelAndView;
  }

  @RequestMapping(value = CA_PUBLIC_KEYS_PAGINATION, method = RequestMethod.POST)
  public ModelAndView getPaginationList(final HttpSession session,
      @FormParam("pageNumber") final Integer pageNumber,
      @FormParam("totalRecords") final Integer totalRecords, Map model) {
    logger.info("Entering:: CAPublicKeysController:: getPaginationList method");
    ModelAndView modelAndView = new ModelAndView(CA_PUBLIC_KEYS_SEARCH_PAGE);
    try {
      CAPublicKeysDTO caPublicKeysDTO =
          (CAPublicKeysDTO) session.getAttribute(Constants.PUBLIC_KEY_REQUEST);
      model.put(Constants.CAPUBLIC_KEYSDTO, caPublicKeysDTO);
      caPublicKeysDTO.setPageIndex(pageNumber);
      caPublicKeysDTO.setNoOfRecords(totalRecords);
      modelAndView.addObject("pageSize", caPublicKeysDTO.getPageSize());
      CAPublicKeysResponse caPublicKeysResponse =
          caPublicKeysService.searchCAPublicKeys(caPublicKeysDTO);
      List<CAPublicKeysDTO> searchList = caPublicKeysResponse.getCaPublicKeysList();
      if (StringUtil.isListNotNullNEmpty(searchList)) {
        modelAndView = PaginationUtil.getPagenationModelSuccessive(modelAndView, pageNumber,
            caPublicKeysDTO.getNoOfRecords());
        session.setAttribute(Constants.PAGE_NUMBER, pageNumber);
        session.setAttribute(Constants.TOTAL_RECORDS, totalRecords);
        modelAndView.addObject("searchList", searchList);
      }
    } catch (Exception e) {
      logger.error("ERROR:: CAPublicKeysController:: getPaginationList method", e);
      modelAndView.addObject(Constants.ERROR, messageSource
          .getMessage(Constants.CHATAK_GENERAL_ERROR, null, LocaleContextHolder.getLocale()));
    }
    logger.info("Exiting:: CAPublicKeysController:: getPaginationList method");
    return modelAndView;
  }

  @RequestMapping(value = DOWNLOAD_CA_PUBLIC_KEYS_REPORT, method = RequestMethod.POST)
  public ModelAndView downloadCaPublicKeysReport(HttpServletRequest request,
      HttpServletResponse response, HttpSession session, Map model,
      @FormParam("downLoadPageNumber") final Integer downLoadPageNumber,
      @FormParam("downloadType") final String downloadType) {
    logger.info("Entering:: CAPublicKeysController:: downloadCaPublicKeysReport method");
    try {
      CAPublicKeysDTO caPublicKeysDTO =
          (CAPublicKeysDTO) session.getAttribute(Constants.PUBLIC_KEY_REQUEST);
      caPublicKeysDTO.setPageIndex(downLoadPageNumber);
      CAPublicKeysResponse caPublicKeysResponse =
          caPublicKeysService.searchCAPublicKeys(caPublicKeysDTO);
      List<CAPublicKeysDTO> exportList = caPublicKeysResponse.getCaPublicKeysList();
      ExportDetails exportDetails = new ExportDetails();
      if (StringUtil.isListNotNullNEmpty(exportList)) {
        if (Constants.PDF_FILE_FORMAT.equalsIgnoreCase(downloadType)) {
          exportDetails.setExportType(ExportType.PDF);
        } else if (Constants.XLS_FILE_FORMAT.equalsIgnoreCase(downloadType)) {
          exportDetails.setExportType(ExportType.XLS);
          exportDetails.setExcelStartRowNumber(Integer.parseInt("5"));
      }
      setExportDetailsDataForDownloadCaPublicKeysReport(exportList, exportDetails); 
      ExportUtil.exportData(exportDetails, response, messageSource);
      }
    } catch (Exception e) {
      logger.error("ERROR:: CAPublicKeysController:: downloadCaPublicKeysReport method", e);
    }
    logger.info("Exiting:: CAPublicKeysController:: downloadCaPublicKeysReport method");
    return null;
  }
  
  private void setExportDetailsDataForDownloadCaPublicKeysReport(List<CAPublicKeysDTO> publicKeysList,
      ExportDetails exportDetails) {
    exportDetails.setReportName("PublicKeys_List");
    exportDetails.setHeaderMessageProperty("chatak.header.capublickeys.messages");

    exportDetails.setHeaderList(getCaPublicKeysHeaderList());
    exportDetails.setFileData(getCaPublicKeysFileData(publicKeysList));
  }

  @RequestMapping(value = CHATAK_ADMIN_CREATE_CA_PUBLIC_KEYS_PAGE, method = RequestMethod.GET)
  public ModelAndView showCreateCApublicKeysPage(Map model, HttpSession session,
      CAPublicKeysDTO capublickeysDTO) {
    logger.info("Entering:: CAPublicKeysController:: showCreateCApublicKeysPage method");
    ModelAndView modelAndView = new ModelAndView(CHATAK_ADMIN_CREATE_CA_PUBLIC_KEYS_PAGE);

    String existingFeature = (String) session.getAttribute(Constants.EXISTING_FEATURES);
    if (!existingFeature
        .contains(FeatureConstants.ADMIN_SERVICE_CA_PUBLIC_KEYS_CREATE_FEATURE_ID)) {
      session.invalidate();
      modelAndView.setViewName(INVALID_REQUEST_PAGE);
      return modelAndView;
    }

    modelAndView.addObject(Constants.ERROR, null);
    session.setAttribute(Constants.ERROR, null);
    LoginResponse loginResponse =
        (LoginResponse) session.getAttribute(Constants.LOGIN_RESPONSE_DATA);
    capublickeysDTO.setCreatedBy(loginResponse.getUserId().toString());
    model.put(Constants.CA_PUBLIC_KEYS_DTO, capublickeysDTO);
    return modelAndView;
  }

  @RequestMapping(value = CHATAK_ADMIN_CREATE_CA_PUBLIC_KEYS, method = RequestMethod.POST)
  public ModelAndView createCAPublicKeys(HttpServletRequest request, HttpServletResponse response,
      CAPublicKeysDTO capublickeysDTO, HttpSession session, BindingResult bindingResult,
      Map model) {
    logger.info("Entering:: CAPublicKeysController:: createCAPublicKeys method");
    ModelAndView modelAndView = new ModelAndView(CHATAK_ADMIN_CREATE_CA_PUBLIC_KEYS_PAGE);
    String existingFeature = (String) session.getAttribute(Constants.EXISTING_FEATURES);
    if (!existingFeature
        .contains(FeatureConstants.ADMIN_SERVICE_CA_PUBLIC_KEYS_CREATE_FEATURE_ID)) {
      session.invalidate();
      modelAndView.setViewName(INVALID_REQUEST_PAGE);
      return modelAndView;
    }
    try {
      LoginResponse loginResponse =
          (LoginResponse) session.getAttribute(Constants.LOGIN_RESPONSE_DATA);
      capublickeysDTO.setCreatedBy(loginResponse.getUserId().toString());
      model.put(Constants.CAPUBLIC_KEYSDTO, capublickeysDTO);
      CAPublicKeysDTO caSearchByNamePublickeysDTO = new CAPublicKeysDTO();
      caSearchByNamePublickeysDTO.setPublicKeyName(capublickeysDTO.getPublicKeyName());
      Response caPublicKeys = caPublicKeysService.searchCAPublicKeys(caSearchByNamePublickeysDTO);

      if (caPublicKeys.getTotalNoOfRows() != null && caPublicKeys.getTotalNoOfRows() > 0) {
        modelAndView.addObject(Constants.ERROR, messageSource.getMessage(
            "chatak.acquirer.duplicate.capublickeys", null, LocaleContextHolder.getLocale()));
        modelAndView.addObject(Constants.CAPUBLIC_KEYSDTO, new CAPublicKeysDTO());
        logger.info("Exiting  :: CAPublicKeysController:: createcapublickeys method");
        return modelAndView;
      }

      Response caPublicKeysResponse = caPublicKeysService.createCAPublicKeys(capublickeysDTO);
      if (caPublicKeysResponse.getErrorMessage().equals(Constants.SUCESS)) {
        modelAndView.setViewName(CHATAK_ADMIN_CREATE_CA_PUBLIC_KEYS_PAGE);
        modelAndView.addObject(Constants.CAPUBLIC_KEYSDTO, new CAPublicKeysDTO());
        model.remove(Constants.CAPUBLIC_KEYSDTO);
        modelAndView = showCAPublicKeysSearchPage(request, response, model, session);
        modelAndView.addObject(Constants.SUCESS,
            messageSource.getMessage("chatak.acquirer.createcapublickeys.success.message", null,
                LocaleContextHolder.getLocale()));
      } else {
        modelAndView.setViewName(CHATAK_ADMIN_CREATE_CA_PUBLIC_KEYS);
        modelAndView.addObject(Constants.ERROR,
            messageSource.getMessage("chatak.acquirer.createcapublickeys.error.message", null,
                LocaleContextHolder.getLocale()));
      }

    } catch (ChatakAdminException e) {
      logger.error("ERROR:: CAPublicKeysController:: createcapublickeys method", e);
      modelAndView.addObject(Constants.ERROR, e.getMessage());
    } catch (Exception e) {
      logger.error("ERROR:: CAPublicKeysController:: createcapublickeys method", e);
      model.put(Constants.ERROR,
          messageSource.getMessage("chatak.acquirer.createcapublickeys.error.message", null,
              LocaleContextHolder.getLocale()));
    }
    logger.info("Exiting  :: CAPublicKeysController:: createcapublickeys method");
    return modelAndView;

  }

  @RequestMapping(value = SHOW_CA_PUBLIC_KEYS_EDIT, method = RequestMethod.POST)
  public ModelAndView showCAPublicKeysEdit(HttpServletRequest request, HttpServletResponse response,
      Map model, HttpSession session,
      @FormParam("getCAPublicKeysId") final Long getCAPublicKeysId) {
    logger.info("Entering:: CAPublicKeysController:: showCAPublicKeysEdit method");
    ModelAndView modelAndView = new ModelAndView(CA_PUBLIC_KEYS_EDIT_PAGE);
    String existingFeature = (String) session.getAttribute(Constants.EXISTING_FEATURES);
    if (!existingFeature.contains(FeatureConstants.ADMIN_SERVICE_CA_PUBLIC_KEYS_EDIT_FEATURE_ID)) {
      session.invalidate();
      modelAndView.setViewName(INVALID_REQUEST_PAGE);
      return modelAndView;
    }
    getCAPublicKeyData(model, session, getCAPublicKeysId, modelAndView);
    logger.info("Exiting:: CAPublicKeysController:: showFeeProgramEdit method");
    return modelAndView;
  }

  @RequestMapping(value = SHOW_CA_PUBLIC_KEYS_VIEW, method = RequestMethod.POST)
  public ModelAndView showCAPublicKeysView(HttpServletRequest request, HttpServletResponse response,
      Map model, HttpSession session,
      @FormParam("getCAPublicKeysId") final Long getCAPublicKeysId) {
    logger.info("Entering:: CAPublicKeysController:: showCAPublicKeysEdit method");
    ModelAndView modelAndView = new ModelAndView(SHOW_CA_PUBLIC_KEYS_VIEW);

    String existingFeature = (String) session.getAttribute(Constants.EXISTING_FEATURES);
    if (!existingFeature.contains(FeatureConstants.ADMIN_SERVICE_CA_PUBLIC_KEYS_EDIT_FEATURE_ID)) {
      session.invalidate();
      modelAndView.setViewName(INVALID_REQUEST_PAGE);
      return modelAndView;
    }
    getCAPublicKeyData(model, session, getCAPublicKeysId, modelAndView);
    logger.info("Exiting:: CAPublicKeysController:: showFeeProgramEdit method");
    return modelAndView;
  }

  private ModelAndView getCAPublicKeyData(Map model, HttpSession session,
      final Long getCAPublicKeysId, ModelAndView modelAndView) {
    try {
      CAPublicKeysDTO caPublicKeysDTO = new CAPublicKeysDTO();
      model.put(Constants.CAPUBLIC_KEYSDTO, caPublicKeysDTO);
      PGCaPublicKeys pgcapublickeys = caPublicKeysService.caPublicKeysById(getCAPublicKeysId);
      LoginResponse loginResponse =
          (LoginResponse) session.getAttribute(Constants.LOGIN_RESPONSE_DATA);
      caPublicKeysDTO.setPublicKeyId(getCAPublicKeysId);
      caPublicKeysDTO.setCreatedBy(loginResponse.getUserId().toString());
      caPublicKeysDTO.setPublicKeyName(pgcapublickeys.getPublicKeyName());
      caPublicKeysDTO.setStatus(pgcapublickeys.getStatus());
      caPublicKeysDTO.setRid(pgcapublickeys.getrId());
      caPublicKeysDTO.setPublicKeyModulus(pgcapublickeys.getPublicKeyModulus());
      caPublicKeysDTO.setPublicKeyExponent(pgcapublickeys.getPublicKeyExponent());

      caPublicKeysDTO.setExpiryDate(pgcapublickeys.getExpiryDate());
      caPublicKeysDTO.setPublicKeyIndex(pgcapublickeys.getPublicKeyIndex());
      modelAndView.addObject(Constants.CAPUBLIC_KEYSDTO, caPublicKeysDTO);
    } catch (Exception e) {
      logger.error("ERROR:: CAPublicKeysController:: showCAPublicKeysEdit method2", e);
      model.put(Constants.ERROR, Properties.getProperty("prepaid.admin.general.error.message"));
    }
    return modelAndView;
  }

  @RequestMapping(value = UPDATE_CA_PUBLIC_KEYS, method = RequestMethod.POST)
  public ModelAndView updateCAPublicKeys(HttpServletRequest request, HttpServletResponse response,
      CAPublicKeysDTO caPublicKeysDTO, HttpSession session, Map model) {
    logger.info("Entering:: CAPublicKeysController:: updateCAPublicKeys method");

    ModelAndView modelAndView = new ModelAndView(CA_PUBLIC_KEYS_EDIT_PAGE);
    String existingFeature = (String) session.getAttribute(Constants.EXISTING_FEATURES);
    if (!existingFeature.contains(FeatureConstants.ADMIN_SERVICE_CA_PUBLIC_KEYS_EDIT_FEATURE_ID)) {
      session.invalidate();
      modelAndView.setViewName(INVALID_REQUEST_PAGE);
      return modelAndView;
    }

    CAPublicKeysDTO caPublicKeysDTO2 = new CAPublicKeysDTO();
    DataBinder dataBinder = new DataBinder(caPublicKeysDTO2);
    try {
      LoginResponse loginResponse =
          (LoginResponse) session.getAttribute(Constants.LOGIN_RESPONSE_DATA);
      caPublicKeysDTO.setCreatedBy(loginResponse.getUserId().toString());
      model.put(Constants.CAPUBLIC_KEYSDTO, caPublicKeysDTO);
      Response responseDetails = caPublicKeysService.updateCAPublicKeys(caPublicKeysDTO);
      session.setAttribute("response", responseDetails);
      if (responseDetails.getErrorMessage().equals(Constants.SUCESS)) {
        modelAndView.setViewName(CA_PUBLIC_KEYS_SEARCH);
        modelAndView = showCAPublicKeysSearchPage(request, response, model, session);
        modelAndView.addObject(Constants.SUCESS,
            messageSource.getMessage("chatak.acquirer.updatecapublickeys.success.message", null,
                LocaleContextHolder.getLocale()));
      } else {
        modelAndView.setViewName(CA_PUBLIC_KEYS_SEARCH);
        modelAndView = caPublicKeysSearch(request, response, caPublicKeysDTO,
            dataBinder.getBindingResult(), model, null, session);
        modelAndView.addObject(Constants.ERROR,
            messageSource.getMessage("chatak.acquirer.updatecapublickeys.error.message", null,
                LocaleContextHolder.getLocale()));
      }
    } catch (ChatakAdminException e) {
      logger.error("ERROR:: CAPublicKeysController:: updateCAPublicKeys method1", e);
      modelAndView = caPublicKeysSearch(request, response, caPublicKeysDTO2,
          dataBinder.getBindingResult(), model, null, session);
      model.put(Constants.ERROR, e.getMessage());
    } catch (Exception e) {
      logger.error("ERROR:: CAPublicKeysController:: updateCAPublicKeys method", e);
      modelAndView = caPublicKeysSearch(request, response, caPublicKeysDTO2,
          dataBinder.getBindingResult(), model, null, session);
      model.put(Constants.ERROR, messageSource.getMessage(
          "chatak.acquirer.updatefeeprogram.error.message", null, LocaleContextHolder.getLocale()));
    }
    logger.info("Exiting  :: CAPublicKeysController:: updateCAPublicKeys method");
    return modelAndView;
  }

  @RequestMapping(value = CA_PUBLIC_KEYS_ACTIVATION_SUSPENTION, method = RequestMethod.POST)
  public ModelAndView changeCAPublicKeysStatus(final HttpSession session,
      @FormParam("suspendActiveId") final Long caPublicKeyId,
      @FormParam("suspendActiveStatus") final String caPublicKeyStatus,
      @FormParam("reason") final String reason, Map<String, Serializable> model,
      HttpServletRequest request, HttpServletResponse response) {
    logger.info("Entering:: CAPublicKeysController:: changeCAPublicKeysStatus method");

    ModelAndView modelAndView = new ModelAndView(CA_PUBLIC_KEYS_SEARCH_PAGE);
    String existingFeature = (String) session.getAttribute(Constants.EXISTING_FEATURES);
    if (!existingFeature.contains(FeatureConstants.ADMIN_SERVICE_CA_PUBLIC_KEYS_FEATURE_ID)) {
      session.invalidate();
      modelAndView.setViewName(INVALID_REQUEST_PAGE);
      return modelAndView;
    }
    try {
      CAPublicKeysDTO caPublicKeysDTO2 = new CAPublicKeysDTO();
      caPublicKeysDTO2.setPublicKeyId(caPublicKeyId);
      caPublicKeysDTO2.setStatus(caPublicKeyStatus);
      caPublicKeysDTO2.setReason(reason);
      CAPublicKeysResponse caPublicKeysResponse =
          caPublicKeysService.changeCAPublicKeysStatus(caPublicKeysDTO2);
      modelAndView = getPaginationList(session,
          StringUtil.isNull((Integer) session.getAttribute(Constants.PAGE_NUMBER)) ? 1
              : (Integer) session.getAttribute("pageNumber"),
          (Integer) session.getAttribute(Constants.TOTAL_RECORDS), model);
      if (ActionErrorCode.ERROR_CODE_00.equals(caPublicKeysResponse.getErrorCode())) {
        model.put(Constants.SUCESS,
            messageSource.getMessage("chatak.acquirer.change.statuscapublickeys.success.message",
                null, LocaleContextHolder.getLocale()));
      } else {
        model.put(Constants.ERROR, messageSource.getMessage(Constants.CHATAK_GENERAL_ERROR, null,
            LocaleContextHolder.getLocale()));
      }
    } catch (Exception e) {
      logger.error("ERROR:: CAPublicKeysController:: changeCAPublicKeysStatus method", e);
      model.put(Constants.ERROR, messageSource.getMessage(Constants.CHATAK_GENERAL_ERROR, null,
          LocaleContextHolder.getLocale()));
    }
    logger.info("Exiting:: CAPublicKeysController:: changeCAPublicKeysStatus method");
    return modelAndView;
  }

  @RequestMapping(value = CHATAK_ADMIN_PUBLICKEYNAME_VALIDATE, method = RequestMethod.GET)
  public @ResponseBody String validateuniquePublickeyName(Map model, HttpServletRequest request,
      HttpServletResponse response, HttpSession session) {
    ModelAndView modelAndView = new ModelAndView(CHATAK_ADMIN_CREATE_CA_PUBLIC_KEYS_PAGE);
    modelAndView.addObject(Constants.ERROR, null);
    logger.info("Entering :: CAPublicKeysController :: validateuniquePublickeyName method");
    String publicKeyName = request.getParameter("publicKeyNameId");
    PublickeyNameResponse publickeyNameResponse = null;
    try {

      publickeyNameResponse = caPublicKeysService.validatePublickeyName(publicKeyName);
      return JsonUtil.convertObjectToJSON(publickeyNameResponse);
    } catch (Exception e) {
      publickeyNameResponse = new PublickeyNameResponse();
      publickeyNameResponse.setErrorCode(ActionErrorCode.ERROR_CODE_Z5);
      publickeyNameResponse
          .setErrorMessage(ActionErrorCode.getInstance().getMessage(ActionErrorCode.ERROR_CODE_Z5));
      logger.error("ERROR:: CAPublicKeysController:: validateuniquePublickeyName method", e);
      modelAndView.addObject(Constants.ERROR, messageSource
          .getMessage(Constants.CHATAK_GENERAL_ERROR, null, LocaleContextHolder.getLocale()));
      try {
        return JsonUtil.convertObjectToJSON(publickeyNameResponse);
      } catch (ChatakAdminException e1) {
        logger.error(
            "ERROR:: CAPublicKeysController:: validateuniquePublickeyName method ::convertObjectToJSON",
            e1);
      }
    }
    logger.info("Exiting:: CAPublicKeysController:: validateuniquePublickeyName method");
    return null;
  }

  @RequestMapping(value = DELETE_CA_PUBLIC_KEY, method = RequestMethod.POST)
  public ModelAndView caPublicKeyDelete(HttpServletRequest request, HttpServletResponse response,
      @FormParam("getCAPublicKeyID") final Long getCAPublicKeyID, Map model, HttpSession session) {
    logger.info("Entering:: CAPublicKeysController:: caPublicKeyDelete method");
    ModelAndView modelAndView = new ModelAndView(CA_PUBLIC_KEYS_SEARCH_PAGE);
    String existingFeature = (String) session.getAttribute(Constants.EXISTING_FEATURES);
    if (!existingFeature
        .contains(FeatureConstants.ADMIN_SERVICE_CA_PUBLIC_KEYS_DELETE_FEATURE_ID)) {
      session.invalidate();
      modelAndView.setViewName(INVALID_REQUEST_PAGE);
      return modelAndView;
    }
    try {
      CAPublicKeysDTO caPublicKeyDTO = caPublicKeysService.findCAPublicKeyById(getCAPublicKeyID);
      caPublicKeyDTO.setStatus(Constants.DELETED);
      caPublicKeyDTO.setPublicKeyId(getCAPublicKeyID);
      CAPublicKeysDTO dtoResponse = caPublicKeysService.saveOrUpdateCAPublicKey(caPublicKeyDTO);
      if (null != dtoResponse && dtoResponse.getErrorCode().equals(ActionErrorCode.ERROR_CODE_00)) {
        modelAndView = showCAPublicKeysSearchPage(request, response, model, session);
        modelAndView.addObject(Constants.SUCESS, messageSource.getMessage(
            "chatak.capublickey.delete.success", null, LocaleContextHolder.getLocale()));
      } else {
        modelAndView.addObject(Constants.ERROR, messageSource
            .getMessage(ActionErrorCode.ERROR_CODE_Z5, null, LocaleContextHolder.getLocale()));
      }
    } catch (Exception e) {
      logger.info("Exiting:: CAPublicKeysController:: caPublicKeyDelete method",e);
      modelAndView.addObject(Constants.ERROR, messageSource
          .getMessage(Constants.CHATAK_GENERAL_ERROR, null, LocaleContextHolder.getLocale()));
    }
    return modelAndView;
  }
  
  private List<String> getCaPublicKeysHeaderList() {
    String[] headerArr = {
        messageSource.getMessage("capublic-file-exportutil-publickeyname", null,
            LocaleContextHolder.getLocale()),
        messageSource.getMessage("reports-file-exportutil-status", null,
            LocaleContextHolder.getLocale())};
    return new ArrayList<String>(Arrays.asList(headerArr));
  }

  private static List<Object[]> getCaPublicKeysFileData(List<CAPublicKeysDTO> publicKeysList) {
    List<Object[]> fileData = new ArrayList<Object[]>();

    for (CAPublicKeysDTO publicKeys : publicKeysList) {

      Object[] rowData =
          {publicKeys.getPublicKeyName() != null ? publicKeys.getPublicKeyName() + "" : "",
              publicKeys.getStatus() != null ? publicKeys.getStatus() : ""

          };
      fileData.add(rowData);
    }

    return fileData;
  }

}
