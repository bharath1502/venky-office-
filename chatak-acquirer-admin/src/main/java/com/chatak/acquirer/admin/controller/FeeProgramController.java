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
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.chatak.acquirer.admin.constants.FeatureConstants;
import com.chatak.acquirer.admin.constants.URLMappingConstants;
import com.chatak.acquirer.admin.controller.model.ExportDetails;
import com.chatak.acquirer.admin.controller.model.LoginResponse;
import com.chatak.acquirer.admin.exception.ChatakAdminException;
import com.chatak.acquirer.admin.model.FeeCodeResponseDetails;
import com.chatak.acquirer.admin.model.FeeProgramResponseDetails;
import com.chatak.acquirer.admin.service.FeeCodeService;
import com.chatak.acquirer.admin.service.FeeProgramService;
import com.chatak.acquirer.admin.util.ExportUtil;
import com.chatak.acquirer.admin.util.JsonUtil;
import com.chatak.acquirer.admin.util.PaginationUtil;
import com.chatak.acquirer.admin.util.StringUtil;
import com.chatak.pg.bean.FeeprogramNameResponse;
import com.chatak.pg.bean.Response;
import com.chatak.pg.constants.ActionErrorCode;
import com.chatak.pg.enums.ExportType;
import com.chatak.pg.model.FeeCodeDTO;
import com.chatak.pg.model.FeeProgramDTO;
import com.chatak.pg.util.Constants;
import com.chatak.pg.util.Properties;

@SuppressWarnings({"rawtypes", "unchecked"})
@Controller
public class FeeProgramController implements URLMappingConstants {
	
  @Autowired
  MessageSource messageSource;

  private static Logger logger = Logger.getLogger(FeeProgramController.class);

  @Autowired
  FeeCodeService feeCodeService;

  @Autowired
  FeeProgramService feeProgramService;

  @RequestMapping(value = SHOW_FEE_PROGRAM_SEARCH, method = RequestMethod.GET)
  public ModelAndView showFeeProgramSearch(HttpServletRequest request, HttpServletResponse response,
      Map model, HttpSession session) {

    logger.info("Entering:: FeeProgramController:: showFeeProgramSearch method");
    ModelAndView modelAndView = new ModelAndView(FEE_PROGRAM_SEARCH_PAGE);
    String existingFeature = (String) session.getAttribute(Constants.EXISTING_FEATURES);
    if (!existingFeature.contains(FeatureConstants.ADMIN_SERVICE_FEE_PROGRAMS_FEATURE_ID)) {
      session.invalidate();
      modelAndView.setViewName(INVALID_REQUEST_PAGE);
      return modelAndView;
    }
    try {
      FeeProgramDTO feeProgramDTO = new FeeProgramDTO();
      model.put("feeProgramDTO", feeProgramDTO);
      LoginResponse loginResponse =
          (LoginResponse) session.getAttribute(Constants.LOGIN_RESPONSE_DATA);
      feeProgramDTO.setCreatedBy(loginResponse.getUserId().toString());
      feeProgramDTO.setPageIndex(Constants.ONE);
      feeProgramDTO.setPageSize(Constants.INITIAL_ENTITIES_PORTAL_DISPLAY_SIZE);
      session.setAttribute(Constants.FEECODE_PROGRAM_REQUEST_LIST_EXPORTDATA, feeProgramDTO);
      List<FeeProgramDTO> searchList = new ArrayList<FeeProgramDTO>();
      session.setAttribute(Constants.SEARCH_LIST, searchList);
      modelAndView.addObject(Constants.SEARCH_LIST, searchList);
    } 
    catch (Exception e) {
      logger.error("ERROR:: FeeProgramController:: searchFeeProgram method2", e);
      modelAndView = showFeeProgramSearch(request, response, model, session);
      modelAndView.addObject(Constants.ERROR,
          Properties.getProperty("prepaid.agentadmin.login.error.message"));
    }
    modelAndView.addObject("flag", false);
    logger.info("Exiting:: FeeProgramController:: searchFeeProgram method");
    return modelAndView;
  }

  @RequestMapping(value = SHOW_FEE_PROGRAM_CREATE, method = RequestMethod.GET)
  public ModelAndView showFeeProgramCreate(HttpServletRequest request, HttpServletResponse response,
      Map model, HttpSession session) {
    logger.info("Entering:: FeeProgramController:: showFeeProgramCreate method");
    ModelAndView modelAndView = new ModelAndView(FEE_PROGRAM_CREATE_PAGE);
    String existingFeature = (String) session.getAttribute(Constants.EXISTING_FEATURES);
    if (!existingFeature.contains(FeatureConstants.ADMIN_SERVICE_FEE_PROGRAMS_CREATE_FEATURE_ID)) {
      session.invalidate();
      modelAndView.setViewName(INVALID_REQUEST_PAGE);
      return modelAndView;
    }
    try {
      LoginResponse loginResponse =
          (LoginResponse) session.getAttribute(Constants.LOGIN_RESPONSE_DATA);
      FeeProgramDTO feeProgramDTO = new FeeProgramDTO();
      model.put("feeProgramDTO", feeProgramDTO);
      FeeCodeResponseDetails codeResponse = feeCodeService.getAllFeeCodeList();
      session.setAttribute(Constants.LOGIN_RESPONSE_DATA, loginResponse);
      model.put("feeCodeList", codeResponse.getFeeCodeList());
      List<FeeCodeDTO> codeResponseName = codeResponse.getFeeCodeList();
      if (StringUtil.isListNotNullNEmpty(codeResponseName)) {
        modelAndView.addObject("feeCodeList", codeResponseName);
      }
    } catch (Exception e) {
      logger.error("ERROR:: FeeProgramController:: showFeeProgramCreate method", e);
      modelAndView.addObject(Constants.ERROR,
          messageSource.getMessage(Constants.CHATAK_GENERAL_ERROR, null, LocaleContextHolder.getLocale()));
    }
    logger.info("Exiting:: FeeProgramController:: showFeeProgramCreate method");
    return modelAndView;
  }

  @RequestMapping(value = FEE_PROGRAM_CREATE, method = RequestMethod.POST)
  public ModelAndView createFeeProgram(HttpServletRequest request, HttpServletResponse response,
      @ModelAttribute FeeProgramDTO feeProgramDTO, HttpSession session, Map model) {
    logger.info("Entering:: FeeProgramController:: createFeeProgram method");

    ModelAndView modelAndView = new ModelAndView(FEE_PROGRAM_SEARCH_PAGE);

    String existingFeature = (String) session.getAttribute(Constants.EXISTING_FEATURES);
    if (!existingFeature.contains(FeatureConstants.ADMIN_SERVICE_FEE_PROGRAMS_CREATE_FEATURE_ID)) {
      session.invalidate();
      modelAndView.setViewName(INVALID_REQUEST_PAGE);
      return modelAndView;
    }

    try {
      LoginResponse loginResponse =
          (LoginResponse) session.getAttribute(Constants.LOGIN_RESPONSE_DATA);
      feeProgramDTO.setCreatedBy(loginResponse.getUserId().toString());
      model.put("feeProgramDTO", feeProgramDTO);
      Response feeprogramResponse = feeProgramService.createFeeProgram(feeProgramDTO);
      if (feeprogramResponse.getErrorMessage().equals(Constants.SUCESS)) {
        modelAndView.setViewName(FEE_PROGRAM_SEARCH_PAGE);
        modelAndView = showFeeProgramSearch(request, response, model, session);
        modelAndView.addObject(Constants.SUCESS,
            messageSource.getMessage("chatak.acquirer.createfeeprogram.success.message", null,
                LocaleContextHolder.getLocale()));
      } else {
        modelAndView.setViewName(FEE_PROGRAM_CREATE_PAGE);
        modelAndView = showFeeProgramCreate(request, response, model, session);
        modelAndView.addObject(Constants.ERROR, messageSource
            .getMessage(feeprogramResponse.getErrorCode(), null, LocaleContextHolder.getLocale()));
        model.put("feeProgramDTO", feeProgramDTO);
      }
    } catch (ChatakAdminException e) {
      logger.error("ERROR:: FeeProgramController:: createFeeProgram method", e);
      modelAndView = showFeeProgramCreate(request, response, model, session);
      modelAndView.addObject(Constants.ERROR, e.getMessage());
    } catch (Exception e) {
      logger.error("ERROR:: FeeProgramController:: createFeeProgram method", e);
      model.put(Constants.ERROR, messageSource.getMessage(
          "chatak.acquirer.createfeeprogram.error.message", null, LocaleContextHolder.getLocale()));
    }
    logger.info("Exiting  :: FeeProgramController:: createFeeProgram method");
    return modelAndView;

  }

  @RequestMapping(value = UPDATE_FEE_PROGRAM, method = RequestMethod.POST)
  public ModelAndView updateFeeProgram(HttpServletRequest request, HttpServletResponse response,
      @ModelAttribute FeeProgramDTO feeProgramDTO, HttpSession session, Map model) {
    logger.info("Entering:: FeeProgramController:: updateFeeProgram method");

    ModelAndView modelAndView = new ModelAndView(FEE_PROGRAM_SEARCH_PAGE);

    String existingFeature = (String) session.getAttribute(Constants.EXISTING_FEATURES);
    if (!existingFeature.contains(FeatureConstants.ADMIN_SERVICE_FEE_PROGRAMS_EDIT_FEATURE_ID)) {
      session.invalidate();
      modelAndView.setViewName(INVALID_REQUEST_PAGE);
      return modelAndView;
    }

    try {
      LoginResponse loginResponse =
          (LoginResponse) session.getAttribute(Constants.LOGIN_RESPONSE_DATA);
      FeeProgramResponseDetails feeProgramResponse =
          feeProgramService.getByFeeProgramId(feeProgramDTO);
      feeProgramDTO.setCreatedBy(feeProgramResponse.getFeeCodeList().get(0).getCreatedBy());
      feeProgramDTO.setCreatedDate(feeProgramResponse.getFeeCodeList().get(0).getCreatedDate());
      feeProgramDTO.setUpdatedBy(loginResponse.getUserId().toString());
      feeProgramDTO.setStatus(Constants.ACTIVE);
      Response responseDetails = feeProgramService.updateFeeProgram(feeProgramDTO);
      if (responseDetails.getErrorMessage().equals(Constants.SUCESS)) {
        modelAndView.setViewName(FEE_PROGRAM_SEARCH_PAGE);
        modelAndView = showFeeProgramSearch(request, response, model, session);
        modelAndView.addObject(Constants.SUCESS,
            messageSource.getMessage("chatak.acquirer.updatefeeprogram.success.message", null,
                LocaleContextHolder.getLocale()));
      } else {
        modelAndView.setViewName(FEE_PROGRAM_SEARCH_PAGE);
        modelAndView = showFeeProgramSearch(request, response, model, session);
        modelAndView.addObject(Constants.ERROR,
            messageSource.getMessage("chatak.acquirer.updatefeeprogram.error.message", null,
                LocaleContextHolder.getLocale()));
      }
    } catch (ChatakAdminException e) {
      logger.error("ERROR:: FeeProgramController:: updateFeeProgram method1", e);
      modelAndView = showFeeProgramSearch(request, response, model, session);
      model.put(Constants.ERROR, e.getMessage());
    } catch (Exception e) {
      logger.error("ERROR:: FeeProgramController:: updateFeeProgram method", e);
      modelAndView = showFeeProgramSearch(request, response, model, session);
      model.put(Constants.ERROR, messageSource.getMessage(
          "chatak.acquirer.updatefeeprogram.error.message", null, LocaleContextHolder.getLocale()));
    }
    logger.info("Exiting  :: FeeProgramController:: updateFeeProgram method");
    return modelAndView;
  }

  @RequestMapping(value = FEE_PROGRAM_SEARCH, method = RequestMethod.POST)
  public ModelAndView searchFeeProgram(HttpServletRequest request, FeeProgramDTO feeProgramDTO,
      HttpServletResponse response, Map model, HttpSession session) {
    logger.info("Entering:: FeeProgramController:: searchFeeProgram method");
    ModelAndView modelAndView = new ModelAndView(FEE_PROGRAM_SEARCH_PAGE);

    String existingFeature = (String) session.getAttribute(Constants.EXISTING_FEATURES);
    if (!existingFeature.contains(FeatureConstants.ADMIN_SERVICE_FEE_PROGRAMS_FEATURE_ID)) {
      session.invalidate();
      modelAndView.setViewName(INVALID_REQUEST_PAGE);
      return modelAndView;
    }
    logger.info("Entering:: FeeProgramController:: searchFeeProgram method");
    try {
      LoginResponse loginResponse =
          (LoginResponse) session.getAttribute(Constants.LOGIN_RESPONSE_DATA);
      model.put("feeProgramDTO", feeProgramDTO);
      session.setAttribute(Constants.FEECODE_PROGRAM_REQUEST_LIST_EXPORTDATA, feeProgramDTO);
      feeProgramDTO.setCreatedBy(loginResponse.getUserId().toString());
      feeProgramDTO.setPageIndex(Constants.ONE);
      FeeProgramResponseDetails feeProgramResponse =
          feeProgramService.searchFeeProgramForJpa(feeProgramDTO);
      List<FeeProgramDTO> searchList = feeProgramResponse.getFeeCodeList();
      session.setAttribute(Constants.SEARCH_LIST, searchList);
      if (StringUtil.isListNotNullNEmpty(searchList)) {
        modelAndView.addObject("pageSize", feeProgramDTO.getPageSize());
        modelAndView =
            PaginationUtil.getPagenationModel(modelAndView, feeProgramResponse.getTotalNoOfRows());
        session.setAttribute(Constants.PAGE_NUMBER, Constants.ONE);
        modelAndView.addObject(Constants.SEARCH_LIST, searchList);
      } else {
        modelAndView.addObject(Constants.SEARCH_LIST, searchList);
        session.setAttribute(Constants.SEARCH_LIST, searchList);
        modelAndView.addObject(Constants.ERROR, Properties.getProperty(""));
      }
    } catch (ChatakAdminException e) {
      logger.error("ERROR:: FeeProgramController:: searchFeeProgram method1", e);
      modelAndView = showFeeProgramSearch(request, response, model, session);
      modelAndView.addObject(Constants.ERROR, e.getMessage());
    } catch (Exception e) {
      logger.error("ERROR:: FeeProgramController:: searchFeeProgram method2", e);
      modelAndView = showFeeProgramSearch(request, response, model, session);
      modelAndView.addObject(Constants.ERROR,
          Properties.getProperty("prepaid.agentadmin.login.error.message"));
    }
    modelAndView.addObject("flag", true);
    logger.info("Exit:: FeeProgramController:: searchFeeProgram method");
    return modelAndView;
  }

  @RequestMapping(value = SHOW_FEE_PROGRAM_EDIT, method = RequestMethod.POST)
  public ModelAndView showFeeProgramEdit(HttpServletRequest request, HttpServletResponse response,
      Map model, HttpSession session, @FormParam("getFeeProgramId") final Long getFeeProgramId) {
    logger.info("Entering:: FeeProgramController:: showFeeProgramEdit method");
    ModelAndView modelAndView = new ModelAndView(FEE_PROGRAM_EDIT_PAGE);

    String existingFeature = (String) session.getAttribute(Constants.EXISTING_FEATURES);
    if (!existingFeature.contains(FeatureConstants.ADMIN_SERVICE_FEE_PROGRAMS_EDIT_FEATURE_ID)) {
      session.invalidate();
      modelAndView.setViewName(INVALID_REQUEST_PAGE);
      return modelAndView;
    }

    try {
      validateFeeProgramResponseDetails(model, session, getFeeProgramId, modelAndView);
    } catch (ChatakAdminException exp) {
      logger.error("ERROR:: FeeProgramController:: showFeeProgramEdit method1", exp);
      model.put(Constants.ERROR, exp.getMessage());
    } catch (Exception e) {
      logger.error("ERROR:: FeeProgramController:: showFeeProgramEdit method2", e);
      model.put(Constants.ERROR, Properties.getProperty("prepaid.admin.general.error.message"));
    }
    logger.info("Exiting:: FeeProgramController:: showFeeProgramEdit method");
    return modelAndView;
  }

  private void validateFeeProgramResponseDetails(Map model, HttpSession session, final Long getFeeProgramId,
		ModelAndView modelAndView) throws ChatakAdminException {
	  FeeProgramDTO feeProgramDTO = new FeeProgramDTO();
      model.put("feeProgramDTO", feeProgramDTO);
      LoginResponse loginResponse =
          (LoginResponse) session.getAttribute(Constants.LOGIN_RESPONSE_DATA);
      feeProgramDTO.setFeeProgramId(getFeeProgramId);
      feeProgramDTO.setCreatedBy(loginResponse.getUserId().toString());
      FeeProgramResponseDetails feeProgramResponse =
          feeProgramService.getFeeProgramDetails(feeProgramDTO);
      feeProgramDTO
          .setFeeProgramName(feeProgramResponse.getFeeCodeList().get(0).getFeeProgramName());
      feeProgramDTO.setFeeProgramDescription(
          feeProgramResponse.getFeeCodeList().get(0).getFeeProgramDescription());
      feeProgramDTO.setStatus(feeProgramResponse.getFeeCodeList().get(0).getStatus());
      feeProgramDTO.setProcessor(feeProgramResponse.getFeeCodeList().get(0).getProcessor());
      feeProgramDTO.setFeeValueList(feeProgramResponse.getFeeCodeList().get(0).getFeeValueList());
      feeProgramDTO.setOtherFee(feeProgramResponse.getFeeCodeList().get(0).getOtherFee());
      model.put("feeValuesList", feeProgramResponse.getFeeCodeList().get(0).getFeeValueList());
      session.setAttribute("feeValuesList",
          feeProgramResponse.getFeeCodeList().get(0).getFeeValueList());
      model.put("acquirerFeeValueList",
          feeProgramResponse.getFeeCodeList().get(0).getFeeValueList());
      session.setAttribute("acquirerFeeValueList",
          feeProgramResponse.getFeeCodeList().get(0).getFeeValueList());
      modelAndView.addObject("feeProgramDTO", feeProgramDTO);
  }

  @RequestMapping(value = SHOW_FEE_PROGRAM_VIEW, method = RequestMethod.POST)
  public ModelAndView showFeeProgramView(HttpServletRequest request, HttpServletResponse response,
      Map model, HttpSession session, @FormParam("getFeeProgramId") final Long getFeeProgramId) {
    logger.info("Entering:: FeeProgramController:: showFeeProgramEdit method");
    ModelAndView modelAndView = new ModelAndView(SHOW_FEE_PROGRAM_VIEW);

    String existingFeature = (String) session.getAttribute(Constants.EXISTING_FEATURES);
    if (!existingFeature.contains(FeatureConstants.ADMIN_SERVICE_FEE_PROGRAMS_VIEW_FEATURE_ID)) {
      session.invalidate();
      modelAndView.setViewName(INVALID_REQUEST_PAGE);
      return modelAndView;
    }

    try {
      validateFeeProgramResponseDetails(model, session, getFeeProgramId, modelAndView);
    } catch (ChatakAdminException e) {
      logger.error("ERROR:: FeeProgramController:: showFeeProgramEdit method1", e);
      model.put(Constants.ERROR, e.getMessage());
    } catch (Exception e) {
      logger.error("ERROR:: FeeProgramController:: showFeeProgramEdit method2", e);
      model.put(Constants.ERROR, Properties.getProperty("prepaid.admin.general.error.message"));
    }
    logger.info("Exiting:: FeeProgramController:: showFeeProgramEdit method");
    return modelAndView;
  }


  @RequestMapping(value = FEE_PROGRAM_PAGINATION_ACTION, method = RequestMethod.POST)
  public ModelAndView getfeeProgramPagination(final HttpSession session,
      @FormParam(Constants.PAGE_NUMBER) final Integer pageNumber,
      @FormParam("totalRecords") final Integer totalRecords, Map model) {
    logger.info("Entering:: FeeProgramController:: getfeeProgramPagination method");
    ModelAndView modelAndView = new ModelAndView(FEE_PROGRAM_SEARCH_PAGE);
    try {
      FeeProgramDTO feeProgramDTO = new FeeProgramDTO();
      model.put("feeProgramDTO", feeProgramDTO);
      LoginResponse loginResponse =
          (LoginResponse) session.getAttribute(Constants.LOGIN_RESPONSE_DATA);
      feeProgramDTO =
          (FeeProgramDTO) session.getAttribute(Constants.FEECODE_PROGRAM_REQUEST_LIST_EXPORTDATA);
      feeProgramDTO.setCreatedBy(loginResponse.getUserId().toString());
      feeProgramDTO.setPageIndex(pageNumber);
      feeProgramDTO.setNoOfRecords(totalRecords);
      FeeProgramResponseDetails feeProgramResponse =
          feeProgramService.searchFeeProgramForJpa(feeProgramDTO);
      List<FeeProgramDTO> feeProgramRequestList = feeProgramResponse.getFeeCodeList();

      if (StringUtil.isListNotNullNEmpty(feeProgramRequestList)) {
        modelAndView.addObject("pageSize", feeProgramDTO.getPageSize());
        modelAndView = PaginationUtil.getPagenationModelSuccessive(modelAndView, pageNumber,
            feeProgramResponse.getTotalNoOfRows());
        session.setAttribute(Constants.PAGE_NUMBER, pageNumber);
        session.setAttribute("totalRecords", totalRecords);
        modelAndView.addObject(Constants.SEARCH_LIST, feeProgramRequestList);
      }
    } catch (Exception e) {
      modelAndView.addObject(Constants.ERROR,
          messageSource.getMessage(Constants.CHATAK_GENERAL_ERROR, null, LocaleContextHolder.getLocale()));
      logger.error("ERROR:: FeeProgramController:: getfeeProgramPagination method", e);
    }
    logger.info("Exiting:: FeeProgramController:: getfeeProgramPagination method");
    return modelAndView;
  }

  @RequestMapping(value = DOWNLOAD_FEE_PROGRAM_REPORT, method = RequestMethod.POST)
  public ModelAndView downloadFeeProgramReport(HttpServletRequest request,
      HttpServletResponse response, HttpSession session, Map model,
      @FormParam("downLoadPageNumber") final Integer downLoadPageNumber,
      @FormParam("downloadType") final String downloadType,
      @FormParam("totalRecords") final Integer totalRecords,
      @FormParam("downloadAllRecords") final boolean downloadAllRecords) {
    logger.info("Entering:: FeeCodeController:: downloadFeeCodeReport method");
    List<FeeProgramDTO> exportList = null;
    FeeProgramDTO feeProgramDTO = null;
    try {
      feeProgramDTO =
          (FeeProgramDTO) session.getAttribute(Constants.FEECODE_PROGRAM_REQUEST_LIST_EXPORTDATA);
      LoginResponse loginResponse =
          (LoginResponse) session.getAttribute(Constants.LOGIN_RESPONSE_DATA);
      feeProgramDTO.setCreatedBy(loginResponse.getUserId().toString());
      feeProgramDTO.setPageIndex(downLoadPageNumber);
      Integer pageSize = feeProgramDTO.getPageSize();
      if (downloadAllRecords) {
        feeProgramDTO.setPageIndex(Constants.ONE);
        feeProgramDTO.setPageSize(totalRecords);
      }
      FeeProgramResponseDetails feeProgramResponse =
          feeProgramService.searchFeeProgramForJpa(feeProgramDTO);
      exportList = feeProgramResponse.getFeeCodeList();
      ExportDetails exportDetails = new ExportDetails();
      if (StringUtil.isListNotNullNEmpty(exportList)) {
        if (Constants.PDF_FILE_FORMAT.equalsIgnoreCase(downloadType)) {
        	exportDetails.setExportType(ExportType.PDF);
        } else if (Constants.XLS_FILE_FORMAT.equalsIgnoreCase(downloadType)) {
        	exportDetails.setExportType(ExportType.XLS);
			exportDetails.setExcelStartRowNumber(Integer.parseInt("5"));
        }
        setExportDetailsDataForDownloadFeeProgramReport(exportList, exportDetails);	
	 	ExportUtil.exportData(exportDetails, response, messageSource);
      }
      feeProgramDTO.setPageSize(pageSize);
    } catch (Exception e) {
      logger.error("ERROR:: FeeProgramController:: downloadFeeProgramReport method", e);
    }
    logger.info("Exiting:: FeeProgramController:: downloadFeeProgramReport method");
    return null;
  }
  
  private void setExportDetailsDataForDownloadFeeProgramReport(List<FeeProgramDTO> feeProgramList,
      ExportDetails exportDetails) {
    exportDetails.setReportName("Prepaid_FeeProgram_");
    exportDetails.setHeaderMessageProperty("chatak.header.feeProgram.messages");

    exportDetails.setHeaderList(getFeeProgramHeaderList());
    exportDetails.setFileData(getFeeProgramFileData(feeProgramList));
  }

  @RequestMapping(value = VALIDATE_FEEPGM_ACCOUNTNUM, method = RequestMethod.POST)
  public @ResponseBody String validateAccountNumFromCI(HttpServletRequest request,
      HttpServletResponse response, HttpSession session,
      @FormParam("specificAccountNumber") String specificAccountNumber) {
    logger.info("Entering:: FeeProgramController:: createFeeProgram method");

    boolean isValid = false;
    try {
      isValid = feeProgramService.validateFeePgmAccNo(specificAccountNumber);
    } catch (Exception e) {
      logger.error("ERROR:: FeeProgramController:: createFeeProgram method", e);
    }
    logger.info("Exiting  :: FeeProgramController:: createFeeProgram method");
    return String.valueOf(isValid);

  }

  @RequestMapping(value = SHOW_FEE_PROGRAM_DELETE, method = RequestMethod.GET)
  public @ResponseBody String deleteFeeProgram(HttpServletRequest request,
      HttpServletResponse response, HttpSession session, Map model) {
    logger.info("Entering :: FeeProgramController :: deleteFeeProgram method");
    ModelAndView modelAndView = new ModelAndView(FEE_PROGRAM_SEARCH);

    modelAndView.addObject(Constants.ERROR, null);
    modelAndView.addObject(Constants.SUCESS, null);
    String feeProgramId = request.getParameter("feeProgramIds");
    try {
      Response deleteResponse = feeProgramService.deleteFeeProgram(Long.valueOf(feeProgramId));
      if (deleteResponse != null) {
        return JsonUtil.convertObjectToJSON(deleteResponse);
      }
    } catch (Exception e) {
      modelAndView.addObject(Constants.ERROR,
          messageSource.getMessage(Constants.CHATAK_GENERAL_ERROR, null, LocaleContextHolder.getLocale()));
      logger.error("ERROR:: FeeProgramController:: deleteFeeProgram method", e);
    }
    logger.info("EXITING :: FeeProgramController :: deleteFeeProgram method");
    return null;
  }

  @RequestMapping(value = CHATAK_ADMIN_FEEPROGRAM_VALIDATE, method = RequestMethod.GET)
  public @ResponseBody String validateFeeprogramName(Map model, HttpServletRequest request,
      HttpServletResponse response, HttpSession session) {
    ModelAndView modelAndView = new ModelAndView(FEE_PROGRAM_CREATE_PAGE);
    modelAndView.addObject(Constants.ERROR, null);
    logger.info("Entering :: FeeProgramController :: validateFeeprogramName method");
    String feeprogramName = request.getParameter("feeProgramId");
    FeeprogramNameResponse feeprogramNameResponse = null;
    try {
      feeprogramNameResponse = feeProgramService.validateFeeprogramName(feeprogramName);
      return JsonUtil.convertObjectToJSON(feeprogramNameResponse);
    } catch (Exception e) {
      feeprogramNameResponse = new FeeprogramNameResponse();
      feeprogramNameResponse.setErrorCode(ActionErrorCode.ERROR_CODE_Z5);
      feeprogramNameResponse
          .setErrorMessage(ActionErrorCode.getInstance().getMessage(ActionErrorCode.ERROR_CODE_Z5));
      logger.error("ERROR:: FeeProgramController:: validateFeeprogramName method", e);
      modelAndView.addObject(Constants.ERROR,
          messageSource.getMessage(Constants.CHATAK_GENERAL_ERROR, null, LocaleContextHolder.getLocale()));
      try {
        return JsonUtil.convertObjectToJSON(feeprogramNameResponse);
      } catch (ChatakAdminException e1) {
        logger.error(
            "ERROR:: FeeProgramController:: validateFeeprogramName method ::convertObjectToJSON",
            e1);
      }
    }
    logger.info("Exiting:: FeeProgramController:: validateFeeprogramName method");
    return null;
  }

  @RequestMapping(value = FEE_PROGRAM_STATUS_CHANGE, method = RequestMethod.POST)
  public ModelAndView changeFeeProgramStatus(final HttpSession session,
      @FormParam("suspendActiveId") final Long feeProgramId,
      @FormParam("suspendActiveStatus") final String feeProgramStatus,
      @FormParam("reason") final String reason, Map<String, Serializable> model,
      HttpServletRequest request, HttpServletResponse response) {
    logger.info("Entering:: FeeProgramController:: changeFeeProgramStatus method");

    ModelAndView modelAndView = new ModelAndView(FEE_PROGRAM_SEARCH_PAGE);
    String existingFeature = (String) session.getAttribute(Constants.EXISTING_FEATURES);
    if (!existingFeature.contains(FeatureConstants.ADMIN_SERVICE_FEE_PROGRAMS_FEATURE_ID)) {
      session.invalidate();
      modelAndView.setViewName(INVALID_REQUEST_PAGE);
      return modelAndView;
    }
    try {
      FeeProgramDTO feeProgramDTO = new FeeProgramDTO();
      feeProgramDTO.setFeeProgramId(feeProgramId);
      feeProgramDTO.setStatus(feeProgramStatus);
      feeProgramDTO.setReason(reason);
      Response responseDetails = feeProgramService.changeFeeProgramStatus(feeProgramDTO);
      modelAndView = getfeeProgramPagination(session,
          StringUtil.isNull((Integer) session.getAttribute(Constants.PAGE_NUMBER)) ? 1
              : (Integer) session.getAttribute(Constants.PAGE_NUMBER),
          (Integer) session.getAttribute("totalRecords"), model);
      if (ActionErrorCode.ERROR_CODE_00.equals(responseDetails.getErrorCode())) {
        model.put(Constants.SUCESS, messageSource.getMessage("fee.program.status.change.sucess",
            null, LocaleContextHolder.getLocale()));
      } else {
        model.put(Constants.ERROR, messageSource.getMessage(Constants.CHATAK_GENERAL_ERROR, null,
            LocaleContextHolder.getLocale()));
      }
    } catch (Exception e) {
      logger.error("ERROR:: FeeProgramController:: changeFeeProgramStatus method", e);
      model.put(Constants.ERROR,
          messageSource.getMessage(Constants.CHATAK_GENERAL_ERROR, null, LocaleContextHolder.getLocale()));
    }
    logger.info("Exiting:: FeeProgramController:: changeFeeProgramStatus method");
    return modelAndView;
  }
  
  private List<String> getFeeProgramHeaderList() {
    String[] headerArr = {
        messageSource.getMessage("fee-programlist-file-exportutil-feeprogramname", null,
            LocaleContextHolder.getLocale()),
        messageSource.getMessage("switch-file-exportutil-status", null,
            LocaleContextHolder.getLocale())};
    return new ArrayList<String>(Arrays.asList(headerArr));
  }

  private static List<Object[]> getFeeProgramFileData(List<FeeProgramDTO> feeProgramList) {
    List<Object[]> fileData = new ArrayList<Object[]>();

    for (FeeProgramDTO feeProgram : feeProgramList) {

      Object[] rowData = {feeProgram.getFeeProgramName(), feeProgram.getStatus()

      };
      fileData.add(rowData);
    }

    return fileData;
  }
}
