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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.chatak.acquirer.admin.constants.FeatureConstants;
import com.chatak.acquirer.admin.constants.URLMappingConstants;
import com.chatak.acquirer.admin.controller.model.ExportDetails;
import com.chatak.acquirer.admin.exception.ChatakAdminException;
import com.chatak.acquirer.admin.service.BinService;
import com.chatak.acquirer.admin.service.SwitchService;
import com.chatak.acquirer.admin.util.ExportUtil;
import com.chatak.acquirer.admin.util.JsonUtil;
import com.chatak.acquirer.admin.util.PaginationUtil;
import com.chatak.acquirer.admin.util.StringUtil;
import com.chatak.pg.bean.BinDuplicateResponse;
import com.chatak.pg.constants.ActionErrorCode;
import com.chatak.pg.enums.ExportType;
import com.chatak.pg.model.BinDTO;
import com.chatak.pg.model.BinResponse;
import com.chatak.pg.user.bean.SwitchRequest;
import com.chatak.pg.user.bean.SwitchResponse;
import com.chatak.pg.util.Constants;

/**
*
* << Add Comments Here >>
*
* @author Girmiti Software
* @date 06-Jan-2015 8:48:01 PM
* @version 1.0
*/
@SuppressWarnings({"rawtypes", "unchecked"})
@Controller
public class BinController implements URLMappingConstants {

  @Autowired
  MessageSource messageSource;

  private static Logger logger = Logger.getLogger(BinController.class);

  @Autowired
  private SwitchService switchService;

  @Autowired
  private BinService binService;

  @RequestMapping(value = ONUS_BIN_CONFIGURATION_SHOW, method = RequestMethod.GET)
  public ModelAndView showBinSearch(HttpServletRequest request, HttpServletResponse response,
      BinDTO binDTO, BindingResult bindingResult, HttpSession session) {
    logger.info("Entering:: BinController:: showBinSearch method");

    ModelAndView modelAndView = new ModelAndView(ONUS_BIN_CONFIGURATION_SHOW);
    String existingFeature = (String) session.getAttribute(Constants.EXISTING_FEATURES);
    showBinSearchCondition(session, modelAndView, existingFeature);
    Integer status = 0;
    session.setAttribute(Constants.SEARCH_BIN_INFO, binDTO);
    binDTO.setPageIndex(Constants.ONE);
    binDTO.setPageSize(Constants.INITIAL_ENTITIES_PORTAL_DISPLAY_SIZE);
    try {
      SwitchResponse searchSwitchResponse = switchService.getSwitchByStatus(status);
      if (searchSwitchResponse != null) {
        List<SwitchRequest> searchSwitchRequestList = searchSwitchResponse.getSwitchRequest();

        session.setAttribute(Constants.SWITCH_NAME, searchSwitchRequestList);
      }
    } catch (Exception e) {
      modelAndView.addObject(Constants.ERROR, messageSource
          .getMessage(Constants.CHATAK_GENERAL_ERROR, null, LocaleContextHolder.getLocale()));
      logger.error("ERROR:: BinController:: showBinSearch method", e);
    }
    modelAndView.addObject("flag", false);
    return modelAndView;
  }

  private ModelAndView showBinSearchCondition(HttpSession session, ModelAndView modelAndView,
      String existingFeature) {
    if (!existingFeature.contains(FeatureConstants.ADMIN_SERVICE_ONUS_BIN_FEATURE_ID)) {
      return validateExistingFeature(session, modelAndView);
    }
    return modelAndView;
  }

  @RequestMapping(value = ONUS_BIN_SEARCH, method = RequestMethod.POST)
  public ModelAndView processBinSearch(HttpServletRequest request, HttpServletResponse response,
      BinDTO binDTO, BindingResult bindingResult, HttpSession session) {
    logger.info("Entering:: BinController:: processBinSearch method");
    ModelAndView modelAndView = new ModelAndView(ONUS_BIN_CONFIGURATION_SHOW);
    String existingFeature = (String) session.getAttribute(Constants.EXISTING_FEATURES);
    showBinSearchCondition(session, modelAndView, existingFeature);
    Integer status = 0;
    session.setAttribute(Constants.SEARCH_BIN_INFO, binDTO);
    binDTO.setPageIndex(Constants.ONE);
    try {
      SwitchResponse searchSwitchResponse = switchService.getSwitchByStatus(status);
      if (searchSwitchResponse != null) {
        List<SwitchRequest> searchSwitchRequestList = searchSwitchResponse.getSwitchRequest();
        modelAndView.addObject(Constants.SWITCH_NAME, searchSwitchRequestList);
      }

      BinResponse binList = binService.searchBins(binDTO);
      if (binList != null) {
        modelAndView.addObject("pageSize", binDTO.getPageSize());
        modelAndView = PaginationUtil.getPagenationModel(modelAndView, binList.getNoOfRecords());
        session.setAttribute(Constants.TOTAL_RECORDS, binList.getNoOfRecords());
        session.setAttribute(Constants.PAGE_NUMBER, Constants.ONE);
        modelAndView.addObject(Constants.BIN_LIST, binList.getBins());
      }
      modelAndView.addObject(Constants.SEARCH_BIN_INFO, binDTO);
    } catch (Exception e) {
      modelAndView.addObject(Constants.ERROR, messageSource
          .getMessage(Constants.CHATAK_GENERAL_ERROR, null, LocaleContextHolder.getLocale()));
      logger.error("ERROR:: BinController:: processBinSearch method", e);
    }
    modelAndView.addObject("flag", true);
    return modelAndView;
  }

  @RequestMapping(value = ONUS_BIN_CREATE_SHOW, method = RequestMethod.GET)
  public ModelAndView saveBin(HttpServletRequest request, HttpServletResponse response,
      BinDTO binDTO, BindingResult bindingResult, HttpSession session) {
    logger.info("Entering:: BinController:: saveBin method");
    ModelAndView modelAndView = new ModelAndView(ONUS_BIN_CREATE);
    String existingFeature = (String) session.getAttribute(Constants.EXISTING_FEATURES);
    if (!existingFeature.contains(FeatureConstants.ADMIN_SERVICE_ONUS_BIN_CREATE_FEATURE_ID)) {
      return validateExistingFeature(session, modelAndView);
    }

    Integer status = 0;
    try {
      SwitchResponse searchSwitchResponse = switchService.getSwitchByStatus(status);
      if (searchSwitchResponse != null) {
        List<SwitchRequest> searchSwitchRequestList = searchSwitchResponse.getSwitchRequest();
        modelAndView.addObject(Constants.SWITCH_NAME, searchSwitchRequestList);
      }

    } catch (Exception e) {
      modelAndView.addObject(Constants.ERROR, messageSource
          .getMessage(Constants.CHATAK_GENERAL_ERROR, null, LocaleContextHolder.getLocale()));

      logger.error("ERROR:: BinController:: saveBin method", e);
    }
    return modelAndView;
  }

  @RequestMapping(value = ONUS_BIN_SAVE_PROCESS, method = RequestMethod.POST)
  public ModelAndView saveBinProcess(HttpServletRequest request, HttpServletResponse response,
      BinDTO binDTO, BindingResult bindingResult, Map model, HttpSession session) {
    logger.info("Entering:: BinController:: saveBinProcess method");
    ModelAndView modelAndView = new ModelAndView(ONUS_BIN_CONFIGURATION_SHOW);
    String existingFeature = (String) session.getAttribute(Constants.EXISTING_FEATURES);
    if (!existingFeature.contains(FeatureConstants.ADMIN_SERVICE_ONUS_BIN_CREATE_FEATURE_ID)) {
      return validateExistingFeature(session, modelAndView);
    }
    try {
      binDTO.setStatus("Active");
      BinDTO dtoResponse = binService.saveOrUpdateBin(binDTO);
      if (dtoResponse != null && dtoResponse.getErrorCode().equals(ActionErrorCode.ERROR_CODE_00)) {
        modelAndView = showBinSearch(request, response, new BinDTO(), bindingResult, session);
        modelAndView.addObject(Constants.SUCESS, messageSource
            .getMessage("chatak.bin.create.success", null, LocaleContextHolder.getLocale()));
        model.put(Constants.BIN_DTO, new BinDTO());
      } else {
        validateErrorCode(binDTO, model, modelAndView);
      }
    } catch (Exception exp) {
      logger.info("Exiting:: BinController:: saveBinProcess method",exp);
      modelAndView.addObject(Constants.ERROR, messageSource
          .getMessage(Constants.CHATAK_GENERAL_ERROR, null, LocaleContextHolder.getLocale()));
    }
    return modelAndView;
  }

  private ModelAndView validateExistingFeature(HttpSession session, ModelAndView modelAndView) {
    session.invalidate();
    modelAndView.setViewName(INVALID_REQUEST_PAGE);
    return modelAndView;
  }

  private void validateErrorCode(BinDTO binDTO, Map model, ModelAndView modelAndView) {
    modelAndView.addObject(Constants.ERROR, messageSource.getMessage(ActionErrorCode.ERROR_CODE_Z5,
        null, LocaleContextHolder.getLocale()));
    model.put(Constants.BIN_DTO, binDTO);
  }

  @RequestMapping(value = ONUS_BIN_EDIT, method = RequestMethod.POST)
  public ModelAndView editBin(HttpServletRequest request, HttpServletResponse response,
      @FormParam("getBinId") final Long getBinId, BinDTO binDTO, BindingResult bindingResult,
      HttpSession session) {
    logger.info("Entering:: BinController:: editBin method");
    ModelAndView modelAndView = new ModelAndView(ONUS_BIN_EDIT);

    String existingFeature = (String) session.getAttribute(Constants.EXISTING_FEATURES);
    if (!existingFeature.contains(FeatureConstants.ADMIN_SERVICE_ONUS_BIN_EDIT_FEATURE_ID)) {
      return validateExistingFeature(session, modelAndView);
    }
    modelAndView.addObject(Constants.ERROR, null);
    modelAndView.addObject(Constants.SUCESS, null);
    Integer status = 0;
    try {
      validateSwitchResponse(getBinId, modelAndView, status);
    } catch (Exception e) {
      logger.info("exiting:: BinController:: editBin method",e);
      modelAndView.addObject(Constants.ERROR, messageSource
          .getMessage(Constants.CHATAK_GENERAL_ERROR, null, LocaleContextHolder.getLocale()));
    }
    return modelAndView;
  }

  private void validateSwitchResponse(final Long getBinId, ModelAndView modelAndView, Integer status)
		throws ChatakAdminException {
	SwitchRequest switchRequest;
	SwitchResponse searchSwitchResponse = switchService.getSwitchByStatus(status);
      if (searchSwitchResponse != null) {
        List<SwitchRequest> searchSwitchRequestList = searchSwitchResponse.getSwitchRequest();
        modelAndView.addObject("allSwitch", searchSwitchRequestList);
      }
      BinDTO bin = binService.searchBinById(getBinId);
      if (null != bin) {
        switchRequest = switchService.getSwtichInformationById(bin.getSwitchId().longValue());
        if (null != switchRequest) {
          bin.setSwitchName(switchRequest.getSwitchName());
        }
        modelAndView.addObject(Constants.BIN_DTO, bin);
      }
  }

  @RequestMapping(value = ONUS_BIN_VIEW, method = RequestMethod.POST)
  public ModelAndView editView(HttpServletRequest request, HttpServletResponse response,
      @FormParam("getBinId") final Long getBinId, BinDTO binDTO, BindingResult bindingResult,
      HttpSession session) {
    logger.info("Entering:: BinController:: editView method");
    ModelAndView modelAndView = new ModelAndView(ONUS_BIN_VIEW);

    String existingFeature = (String) session.getAttribute(Constants.EXISTING_FEATURES);
    if (!existingFeature.contains(FeatureConstants.ADMIN_SERVICE_ONUS_BIN_VIEW_FEATURE_ID)) {
      return validateExistingFeature(session, modelAndView);
    }

    modelAndView.addObject(Constants.ERROR, null);
    modelAndView.addObject(Constants.SUCESS, null);
    Integer status = 0;
    try {
      validateSwitchResponse(getBinId, modelAndView, status);

    } catch (Exception e) {
      logger.info("exiting:: BinController:: editView method",e);
      modelAndView.addObject(Constants.ERROR,
          messageSource.getMessage("chatak.general.error", null, LocaleContextHolder.getLocale()));
    }

    return modelAndView;
  }

  @RequestMapping(value = ONUS_BIN_UPDATE, method = RequestMethod.POST)
  public ModelAndView updateBin(HttpServletRequest request, HttpServletResponse response,
      BinDTO binDTO, BindingResult bindingResult, Map model, HttpSession session) {
    logger.info("Entering:: BinController:: updateBin method");
    ModelAndView modelAndView = new ModelAndView(ONUS_BIN_CONFIGURATION_SHOW);
    String existingFeature = (String) session.getAttribute(Constants.EXISTING_FEATURES);
    if (!existingFeature.contains(FeatureConstants.ADMIN_SERVICE_ONUS_BIN_EDIT_FEATURE_ID)) {
      return validateExistingFeature(session, modelAndView);
    }
    try {
      binDTO.setStatus("Active");
      BinDTO dtoResponse = binService.saveOrUpdateBin(binDTO);
      if (null != dtoResponse && dtoResponse.getErrorCode().equals(ActionErrorCode.ERROR_CODE_00)) {
        modelAndView = showBinSearch(request, response, new BinDTO(), bindingResult, session);
        modelAndView.addObject(Constants.SUCESS, messageSource
            .getMessage("chatak.bin.update.success", null, LocaleContextHolder.getLocale()));
        model.put(Constants.BIN_DTO, new BinDTO());
      } else {
        validateErrorCode(binDTO, model, modelAndView);
      }
    } catch (Exception e) {
      logger.info("Exiting:: BinController:: updateBin method",e);
      modelAndView.addObject(Constants.ERROR, messageSource
          .getMessage(Constants.CHATAK_GENERAL_ERROR, null, LocaleContextHolder.getLocale()));

    }
    return modelAndView;
  }

  @RequestMapping(value = ONUS_BIN_DELETE, method = RequestMethod.POST)
  public ModelAndView binDelete(HttpServletRequest request, HttpServletResponse response,
      @FormParam("getBinID") final Long getBinID, BinDTO binDTO, BindingResult bindingResult,
      HttpSession session) {
    logger.info("Entering:: BinController:: binDelete method");
    ModelAndView modelAndView = new ModelAndView(ONUS_BIN_CONFIGURATION_SHOW);
    String existingFeature = (String) session.getAttribute(Constants.EXISTING_FEATURES);
    if (!existingFeature.contains(FeatureConstants.ADMIN_SERVICE_ONUS_BIN_DELETE_FEATURE_ID)) {
      return validateExistingFeature(session, modelAndView);
    }
    try {
      BinDTO bin = binService.searchBinById(getBinID);
      bin.setStatus("Deleted");
      BinDTO dtoResponse = binService.saveOrUpdateBin(bin);
      if (null != dtoResponse && dtoResponse.getErrorCode().equals(ActionErrorCode.ERROR_CODE_00)) {
        modelAndView = showBinSearch(request, response, binDTO, bindingResult, session);
        modelAndView.addObject(Constants.SUCESS, messageSource
            .getMessage("chatak.bin.delete.success", null, LocaleContextHolder.getLocale()));
      } else {
        modelAndView.addObject(Constants.ERROR, messageSource
            .getMessage(ActionErrorCode.ERROR_CODE_Z5, null, LocaleContextHolder.getLocale()));
      }
    } catch (Exception e) {
      logger.info("Exiting:: BinController:: deleteBin method",e);
      modelAndView.addObject(Constants.ERROR, messageSource
          .getMessage(Constants.CHATAK_GENERAL_ERROR, null, LocaleContextHolder.getLocale()));

    }
    return modelAndView;
  }

  @RequestMapping(value = GET_BINS_FOR_PAGINATIONS, method = RequestMethod.POST)
  public ModelAndView getPaginationList(final HttpSession session,
      @FormParam("pageNumber") final Integer pageNumber,
      @FormParam("totalRecords") final Integer totalRecords) {
    logger.info("Entering:: BinController:: getPaginationList method");
    ModelAndView modelAndView = new ModelAndView(ONUS_BIN_CONFIGURATION_SHOW);

    BinResponse binResponse = new BinResponse();
    try {

      BinDTO binDTO;
      binDTO = (BinDTO) session.getAttribute(Constants.SEARCH_BIN_INFO);
      binDTO.setPageIndex(pageNumber);
      binDTO.setNoOfRecords(totalRecords);
      binResponse = binService.searchBins(binDTO);
      modelAndView.addObject("binList", binResponse.getBins());
      if (!CollectionUtils.isEmpty(binResponse.getBins())) {
        modelAndView.addObject("pageSize", binDTO.getPageSize());
        modelAndView = PaginationUtil.getPagenationModelSuccessive(modelAndView, pageNumber,
            binResponse.getNoOfRecords());
        session.setAttribute("pageNumber", pageNumber);
        session.setAttribute("totalRecords", totalRecords);
      }
      modelAndView.addObject("binDTO", binDTO);

    } catch (Exception e) {
      logger.error("ERROR:: BinController:: getPaginationList method", e);
      modelAndView.addObject(Constants.ERROR,
          messageSource.getMessage("chatak.general.error", null, LocaleContextHolder.getLocale()));
    }
    logger.info("Exiting:: BinController:: getPaginationList method");
    return modelAndView;
  }

  @RequestMapping(value = GET_BIN_REPORT, method = RequestMethod.POST)
  public ModelAndView downloadBinReport(HttpSession session, HttpServletRequest request,
      @FormParam("totalRecords") final Integer totalRecords,
      @FormParam("downloadAllRecords") final boolean downloadAllRecords, BinDTO binDTO,
      BindingResult bindingResult, HttpServletResponse response) {
    logger.info("Entering:: BinController:: downloadBinReport method");
    Integer downLoadPageNumber = Integer.valueOf(request.getParameter("downLoadPageNumber"));
    String downloadType = request.getParameter("downloadType");
    ModelAndView modelAndView = new ModelAndView(ONUS_BIN_CONFIGURATION_SHOW);
    BinDTO bin = new BinDTO();
    try {
      bin.setPageIndex(downLoadPageNumber);
      bin.setPageSize(Constants.INITIAL_ENTITIES_PORTAL_DISPLAY_SIZE);
      if (downloadAllRecords) {
        bin.setPageIndex(Constants.ONE);
        bin.setPageSize(totalRecords);
      }
      BinResponse searchResponse = binService.searchBins(bin);
      List<BinDTO> list = searchResponse.getBins();
      ExportDetails exportDetails = new ExportDetails();

      if (Constants.PDF_FILE_FORMAT.equalsIgnoreCase(downloadType)) {
    	  exportDetails.setExportType(ExportType.PDF);
      } else if (Constants.XLS_FILE_FORMAT.equalsIgnoreCase(downloadType)) {
    	  exportDetails.setExportType(ExportType.XLS);
		  exportDetails.setExcelStartRowNumber(Integer.parseInt("5"));
      }
      setExportDetailsDataForDownloadRoleReport(list, exportDetails);	
	  ExportUtil.exportData(exportDetails, response, messageSource);
      modelAndView.addObject(Constants.BIN_LIST, searchResponse.getBins());
    } catch (Exception e) {
      modelAndView.addObject(Constants.ERROR, messageSource
          .getMessage(Constants.CHATAK_GENERAL_ERROR, null, LocaleContextHolder.getLocale()));
      logger.error("ERROR:: BinController:: downloadBinReport method", e);
    }
    logger.info("Exiting:: BinController:: downloadBinReport method");
    return null;

  }
  
  private void setExportDetailsDataForDownloadRoleReport(List<BinDTO> binData,
      ExportDetails exportDetails) {
    exportDetails.setReportName("ONUS_BIN_");
    exportDetails.setHeaderMessageProperty("chatak.header.onus.bin.messages");

    exportDetails.setHeaderList(getBinHeaderList());
    exportDetails.setFileData(getBinFileData(binData, messageSource));
  }

  @RequestMapping(value = CHATAK_ADMIN_UNIQUE_BIN_VALIDATE, method = RequestMethod.GET)
  public @ResponseBody String validateUniqueBinId(Map model, HttpServletRequest request,
      HttpServletResponse response, HttpSession session) {
    ModelAndView modelAndView = new ModelAndView(ONUS_BIN_CREATE);
    modelAndView.addObject(Constants.ERROR, null);
    logger.info("Entering :: BinController :: validateUniqueBinId method");
    Long bin = Long.parseLong(request.getParameter("binId"));
    BinDuplicateResponse reBinResponse = null;
    try {
      reBinResponse = binService.validateBin(bin);
      return JsonUtil.convertObjectToJSON(reBinResponse);
    } catch (ChatakAdminException e) {
      reBinResponse = new BinDuplicateResponse();
      reBinResponse.setErrorCode(ActionErrorCode.ERROR_CODE_Z5);
      reBinResponse
          .setErrorMessage(ActionErrorCode.getInstance().getMessage(ActionErrorCode.ERROR_CODE_Z5));
      logger.error("ERROR:: BinController:: validateUniqueBinId method", e);
      modelAndView.addObject(Constants.ERROR, messageSource
          .getMessage(Constants.CHATAK_GENERAL_ERROR, null, LocaleContextHolder.getLocale()));
      try {
        return JsonUtil.convertObjectToJSON(reBinResponse);
      } catch (ChatakAdminException e1) {
        logger.error("ERROR:: BinController:: validateUniqueBinId method::convertObjectToJSON", e1);
      }
    } catch (Exception e) {
      reBinResponse = new BinDuplicateResponse();
      reBinResponse.setErrorCode(ActionErrorCode.ERROR_CODE_Z5);
      reBinResponse
          .setErrorMessage(ActionErrorCode.getInstance().getMessage(ActionErrorCode.ERROR_CODE_Z5));
      logger.error("ERROR:: BinController:: validateUniqueBinIdEdit method", e);
      modelAndView.addObject(Constants.ERROR, messageSource
          .getMessage(Constants.CHATAK_GENERAL_ERROR, null, LocaleContextHolder.getLocale()));

      try {
        return JsonUtil.convertObjectToJSON(reBinResponse);
      } catch (ChatakAdminException e1) {
        logger.error("ERROR:: BinController:: validateUniqueBinIdEdit method ::convertObjectToJSON",
            e1);
      }
    }
    logger.info("Exiting:: BinController:: validateUniqueBinId method");
    return null;
  }

  @RequestMapping(value = ONUS_BIN_ACTIVATION_SUSPENTION, method = RequestMethod.POST)
  public ModelAndView changeBinStatus(final HttpSession session,
      @FormParam("suspendActiveId") final Long binId,
      @FormParam("suspendActiveStatus") final String binStatus,
      @FormParam("reason") final String reason, Map<String, Serializable> model,
      HttpServletRequest request, HttpServletResponse response) {
    logger.info("Entering:: BinController:: changeBinStatus method");

    ModelAndView modelAndView = new ModelAndView(ONUS_BIN_CONFIGURATION_SHOW);
    String existingFeature = (String) session.getAttribute(Constants.EXISTING_FEATURES);
    modelAndView = showBinSearchCondition(session, modelAndView, existingFeature);
    try {
      BinDTO dtoResponse = new BinDTO();
      dtoResponse.setId(binId);
      dtoResponse.setStatus(binStatus);
      dtoResponse.setReason(reason);
      BinResponse binResponse = binService.changeBinStatus(dtoResponse);
      modelAndView = getPaginationList(session,
          StringUtil.isNull((Integer) session.getAttribute(Constants.PAGE_NUMBER)) ? 1
              : (Integer) session.getAttribute(Constants.PAGE_NUMBER),
          (Integer) session.getAttribute(Constants.TOTAL_RECORDS));
      if (ActionErrorCode.ERROR_CODE_00.equals(binResponse.getErrorCode())) {
        model.put(Constants.SUCESS, messageSource.getMessage("chatak.bin.sucess.activate.suspend",
            null, LocaleContextHolder.getLocale()));
      } else {
        model.put(Constants.ERROR, messageSource.getMessage(Constants.CHATAK_GENERAL_ERROR, null,
            LocaleContextHolder.getLocale()));
      }
    } catch (Exception e) {
      logger.error("ERROR:: BinController:: changeBinStatus method", e);
      model.put(Constants.ERROR, messageSource.getMessage(Constants.CHATAK_GENERAL_ERROR, null,
          LocaleContextHolder.getLocale()));
    }
    logger.info("Exiting:: BinController:: changeBinStatus method");
    return modelAndView;
  }
  
  private List<String> getBinHeaderList() {
    String[] headerArr =
        {messageSource.getMessage("bin.label.binnumber", null, LocaleContextHolder.getLocale()),
            messageSource.getMessage("bin.label.emvsupport", null, LocaleContextHolder.getLocale()),
            messageSource.getMessage("bin.label.dccsupport", null, LocaleContextHolder.getLocale()),
            messageSource.getMessage("switch.label.switchname", null,
                LocaleContextHolder.getLocale()),
            messageSource.getMessage("common.label.status", null, LocaleContextHolder.getLocale())};
    return new ArrayList<String>(Arrays.asList(headerArr));
  }

  private static List<Object[]> getBinFileData(List<BinDTO> binData, MessageSource messageSource) {
    List<Object[]> fileData = new ArrayList<Object[]>();

    for (BinDTO bin : binData) {

      Object[] rowData = {bin.getBinNumber(),
          bin.getEmvSupported().equals(1)
              ? messageSource.getMessage("bin.label.yes", null, LocaleContextHolder.getLocale())
              : messageSource.getMessage("bin.label.no", null, LocaleContextHolder.getLocale()),
          bin.getDccSupported().equals(1)
              ? messageSource.getMessage("bin.label.yes", null, LocaleContextHolder.getLocale())
              : messageSource.getMessage("bin.label.no", null, LocaleContextHolder.getLocale()),
          bin.getSwitchName(), bin.getStatus()

      };
      fileData.add(rowData);
    }

    return fileData;
  }
}
