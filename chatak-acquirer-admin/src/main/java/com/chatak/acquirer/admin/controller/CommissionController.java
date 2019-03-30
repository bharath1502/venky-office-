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
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.chatak.acquirer.admin.constants.FeatureConstants;
import com.chatak.acquirer.admin.constants.URLMappingConstants;
import com.chatak.acquirer.admin.controller.model.ExportDetails;
import com.chatak.acquirer.admin.controller.model.LoginResponse;
import com.chatak.acquirer.admin.exception.ChatakAdminException;
import com.chatak.acquirer.admin.model.CommissionProgramResponseDetails;
import com.chatak.acquirer.admin.service.CommissionService;
import com.chatak.acquirer.admin.util.ExportUtil;
import com.chatak.acquirer.admin.util.PaginationUtil;
import com.chatak.acquirer.admin.util.StringUtil;
import com.chatak.pg.bean.Response;
import com.chatak.pg.enums.ExportType;
import com.chatak.pg.model.CommissionDTO;
import com.chatak.pg.util.Constants;
import com.chatak.pg.util.Properties;

@SuppressWarnings({"rawtypes", "unchecked"})
@Controller
public class CommissionController implements URLMappingConstants {

  private static Logger logger = Logger.getLogger(CommissionController.class);

  @Autowired
  CommissionService commissionService;

  @Autowired
  private MessageSource messageSource;

  @RequestMapping(value = SHOW_COMMISION_PROGRAM_SEARCH, method = RequestMethod.GET)
  public ModelAndView showCommissionProgramSearch(HttpServletRequest request,
      HttpServletResponse response, Map model, HttpSession session) {
    logger.info("Entering:: FeeProgramController:: showCommissionProgramSearch method");
    ModelAndView modelAndView = new ModelAndView(COMMISION_PROGRAM_SEARCH_PAGE);
    String existingFeature = (String) session.getAttribute(Constants.EXISTING_FEATURES);
    if (!existingFeature.contains(FeatureConstants.ADMIN_SERVICE_COMMISSION_PROGRAMS_FEATURE_ID)) {
      session.invalidate();
      modelAndView.setViewName(INVALID_REQUEST_PAGE);
      return modelAndView;
    }
    try {
      CommissionDTO commissionDTO = new CommissionDTO();
      commissionDTO.setPageIndex(Constants.ONE);
      commissionDTO.setPageSize(Constants.MAX_ENTITIES_PORTAL_DISPLAY_SIZE);
      model.put(Constants.COMMISSION_DTO, commissionDTO);
      Response commissionDTOList = commissionService.searchCommissionProgram(commissionDTO);
      session.setAttribute(Constants.COMMISSION_PROGRAM_REQUEST_LIST_EXPORTDATA, commissionDTO);
      modelAndView = getResponseList(session, modelAndView, commissionDTO, commissionDTOList);

    } catch (Exception e) {
      logger.error("ERROR:: CommissionProgramController:: showCommissionProgramSearch method2", e);
      modelAndView = showCommissionProgramSearch(request, response, model, session);
      modelAndView.addObject(Constants.ERROR,
          Properties.getProperty("prepaid.agentadmin.login.error.message"));
    }
    logger.info("Exiting:: CommissionProgramController:: showCommissionProgramSearch method");
    return modelAndView;

  }

  private ModelAndView getResponseList(HttpSession session, ModelAndView modelAndView,
      CommissionDTO commissionDTO, Response commissionDTOList) {
    if (StringUtil.isListNotNullNEmpty(commissionDTOList.getResponseList())) {
      session.setAttribute(Constants.COMMISION_MODEL, commissionDTO);
      modelAndView.addObject(Constants.PAGE_SIZE, commissionDTO.getPageSize());
      modelAndView = PaginationUtil.getPagenationModel(modelAndView, commissionDTOList.getTotalNoOfRows());
      modelAndView.addObject(Constants.COMMISSION_DTO_LIST, commissionDTOList.getResponseList());
    } else {
      modelAndView.addObject(Constants.COMMISSION_DTO_LIST, commissionDTOList.getResponseList());
      modelAndView.addObject(Constants.ERROR, Properties.getProperty(""));
    }
    return modelAndView;
  }

  @RequestMapping(value = COMMISION_PROGRAM_SEARCH, method = RequestMethod.POST)
  public ModelAndView processCommissionProgramSearch(HttpServletRequest request,
      HttpServletResponse response, Map model, @ModelAttribute CommissionDTO commissionDTO,
      HttpSession session) {
    logger.info("Entering:: CommissionProgramController:: showFeeProgramSearch method");
    ModelAndView modelAndView = new ModelAndView(COMMISION_PROGRAM_SEARCH_PAGE);
    String existingFeature = (String) session.getAttribute(Constants.EXISTING_FEATURES);
    if (!existingFeature.contains(FeatureConstants.ADMIN_SERVICE_COMMISSION_PROGRAMS_FEATURE_ID)) {
      session.invalidate();
      modelAndView.setViewName(INVALID_REQUEST_PAGE);
      return modelAndView;
    }
    try {
      session.setAttribute(Constants.COMMISION_MODEL, commissionDTO);
      commissionDTO.setPageIndex(Constants.ONE);
      model.put(Constants.COMMISSION_DTO, commissionDTO);
      session.setAttribute(Constants.COMMISSION_PROGRAM_REQUEST_LIST_EXPORTDATA, commissionDTO);
      Response commissionDTOList = commissionService.searchCommissionProgram(commissionDTO);
      if (StringUtil.isListNotNullNEmpty(commissionDTOList.getResponseList())) {
        modelAndView.addObject(Constants.PAGE_SIZE, commissionDTO.getPageSize());
        modelAndView =
            PaginationUtil.getPagenationModel(modelAndView, commissionDTOList.getTotalNoOfRows());
        modelAndView.addObject(Constants.COMMISSION_DTO_LIST, commissionDTOList.getResponseList());
      } else {
        modelAndView.addObject(Constants.COMMISSION_DTO_LIST, commissionDTOList.getResponseList());
        modelAndView.addObject(Constants.ERROR, Properties.getProperty(""));
      }

    } catch (Exception e) {
      logger.error("ERROR:: CommissionProgramController:: showCommissionProgramSearch method2", e);
      modelAndView = showCommissionProgramSearch(request, response, model, session);
      modelAndView.addObject(Constants.ERROR,
          Properties.getProperty("prepaid.agentadmin.login.error.message"));
    }
    logger.info("Exiting:: CommissionProgramController:: showCommissionProgramSearch method");
    return modelAndView;

  }

  @RequestMapping(value = SHOW_COMMISION_PROGRAM_CREATE, method = RequestMethod.GET)
  public ModelAndView showCommissionProgramCreate(HttpServletRequest request,
      HttpServletResponse response, Map model, HttpSession session)

  {
    logger.info("Entering:: CommissionProgramController:: showCommissionProgramCreate method");
    ModelAndView modelAndView = new ModelAndView(COMMISION_PROGRAM_CREATE_PAGE);
    String existingFeature = (String) session.getAttribute(Constants.EXISTING_FEATURES);
    if (!existingFeature
        .contains(FeatureConstants.ADMIN_SERVICE_COMMISSION_PROGRAMS_CREATE_FEATURE_ID)) {
      session.invalidate();
      modelAndView.setViewName(INVALID_REQUEST_PAGE);
      return modelAndView;
    }
    try {
      LoginResponse loginResponse =
          (LoginResponse) session.getAttribute(Constants.LOGIN_RESPONSE_DATA);
      CommissionDTO commissionDTO = new CommissionDTO();
      model.put(Constants.COMMISSION_DTO, commissionDTO);
      session.setAttribute(Constants.LOGIN_RESPONSE_DATA, loginResponse);
    } catch (Exception e) {
      logger.error("ERROR:: CommissionProgramController:: showCommissionProgramCreate method", e);
      modelAndView.addObject(Constants.ERROR, messageSource
          .getMessage(Constants.CHATAK_GENERAL_ERROR, null, LocaleContextHolder.getLocale()));
    }
    logger.info("Exiting:: CommissionProgramController:: showCommissionProgramCreate method");
    return modelAndView;
  }

  @RequestMapping(value = COMMISION_PROGRAM_CREATE, method = RequestMethod.POST)
  public ModelAndView processCommissionProgramCreate(HttpServletRequest request,
      HttpServletResponse response, @ModelAttribute CommissionDTO commissionDTO, Map model,
      HttpSession session) {
    logger.info("Entering:: CommissionProgramController:: processCommissionProgramCreate method");
    ModelAndView modelAndView = new ModelAndView(COMMISION_PROGRAM_SEARCH_PAGE);
    String existingFeature = (String) session.getAttribute(Constants.EXISTING_FEATURES);
    if (!existingFeature
        .contains(FeatureConstants.ADMIN_SERVICE_COMMISSION_PROGRAMS_CREATE_FEATURE_ID)) {
      session.invalidate();
      modelAndView.setViewName(INVALID_REQUEST_PAGE);
      return modelAndView;
    }
    try {
      LoginResponse loginResponse =
          (LoginResponse) session.getAttribute(Constants.LOGIN_RESPONSE_DATA);
      commissionDTO.setCreatedBy(loginResponse.getUserId().toString());
      model.put(Constants.COMMISSION_DTO, commissionDTO);
      if (commissionDTO.getMerchantOnBoardingFee() == null
          || commissionDTO.getMerchantOnBoardingFee().equals("")) {
        commissionDTO.setMerchantOnBoardingFee("0");
      }
      Response commissionprogramResponse = commissionService.createCommission(commissionDTO);
      if (commissionprogramResponse.getErrorMessage().equals(Constants.SUCESS)) {
        modelAndView.setViewName(COMMISION_PROGRAM_SEARCH_PAGE);
        modelAndView = showCommissionProgramSearch(request, response, model, session);
        modelAndView.addObject(Constants.SUCESS,
            messageSource.getMessage("chatak.acquirer.createCommissionprogram.success.message",
                null, LocaleContextHolder.getLocale()));
      } else {
        modelAndView.setViewName(COMMISION_PROGRAM_CREATE_PAGE);
        modelAndView = showCommissionProgramCreate(request, response, model, session);
        modelAndView.addObject(Constants.ERROR,
            messageSource.getMessage("chatak.acquirer.createCommissionprogram.error.message", null,
                LocaleContextHolder.getLocale()));
      }
    } catch (ChatakAdminException e) {
      logger.error("ERROR:: FeeProgramController:: createFeeProgram method", e);
      modelAndView = showCommissionProgramCreate(request, response, model, session);
      modelAndView.addObject(Constants.ERROR, e.getMessage());
    } catch (Exception e) {
      logger.error("ERROR:: CommissionProgramController:: processCommissionProgramCreate method",
          e);
      model.put(Constants.ERROR,
          messageSource.getMessage("chatak.acquirer.createCommissionprogram.error.message", null,
              LocaleContextHolder.getLocale()));
    }
    logger.info("Exiting  :: CommissionProgramController:: processCommissionProgramCreate method");
    return modelAndView;
  }

  @RequestMapping(value = CHATAK_ADMIN_SHOW_EDIT_COMMISION_PROGRAM, method = RequestMethod.POST)
  public ModelAndView showCommissionProgramEdit(HttpServletRequest request,
      HttpServletResponse response, Map model, HttpSession session,
      @FormParam("getCommissionId") final Long getCommissionId) {
    logger.info("Entering:: CommissionProgramController:: showCommissionProgramEdit method");
    ModelAndView modelAndView = new ModelAndView(CHATAK_ADMIN_EDIT_COMMISION_PROGRAM);
    String existingFeature = (String) session.getAttribute(Constants.EXISTING_FEATURES);
    if (!existingFeature
        .contains(FeatureConstants.ADMIN_SERVICE_COMMISSION_PROGRAMS_EDIT_FEATURE_ID)) {
      session.invalidate();
      modelAndView.setViewName(INVALID_REQUEST_PAGE);
      return modelAndView;
    }

    try {
      CommissionDTO commissionDTO = new CommissionDTO();
      model.put(Constants.COMMISSION_DTO, commissionDTO);
      LoginResponse loginResponse =
          (LoginResponse) session.getAttribute(Constants.LOGIN_RESPONSE_DATA);
      commissionDTO.setCommissionProgramId(getCommissionId);
      commissionDTO.setCreatedBy(loginResponse.getUserId().toString());
      CommissionProgramResponseDetails commProgramResponse =
          commissionService.getCommissionProgramDetails(commissionDTO);
      commissionDTO
          .setCommissionName(commProgramResponse.getCommProgList().get(0).getCommissionName());
      commissionDTO.setStatus(commProgramResponse.getCommProgList().get(0).getStatus());
      commissionDTO.setMerchantOnBoardingFee(
          commProgramResponse.getCommProgList().get(0).getMerchantOnBoardingFee());
      commissionDTO.setOtherCommissionDTO(
          commProgramResponse.getCommProgList().get(0).getOtherCommissionDTO());
      model.put(Constants.COMMISSION_DTO, commissionDTO);
    } catch (ChatakAdminException e) {
      logger.error("ERROR:: CommissionProgramController:: showCommissionProgramEdit method1", e);
      model.put(Constants.ERROR, e.getMessage());
    } catch (Exception e) {
      logger.error("ERROR:: CommissionProgramController:: showCommissionProgramEdit method2", e);
      model.put(Constants.ERROR, Properties.getProperty("prepaid.admin.general.error.message"));
    }
    logger.info("Exiting:: CommissionProgramController:: showCommissionProgramEdit method");
    return modelAndView;
  }

  @RequestMapping(value = CHATAK_ADMIN_UPDATE_COMMISION_PROGRAM, method = RequestMethod.POST)
  public ModelAndView updateCommissionProgram(HttpServletRequest request,
      HttpServletResponse response, @ModelAttribute CommissionDTO commissionDTO,
      HttpSession session, Map model) {
    logger.info("Entering:: CommissionProgramController:: updateCommissionProgram method");

    ModelAndView modelAndView = new ModelAndView(COMMISION_PROGRAM_SEARCH_PAGE);
    String existingFeature = (String) session.getAttribute(Constants.EXISTING_FEATURES);
    if (!existingFeature
        .contains(FeatureConstants.ADMIN_SERVICE_COMMISSION_PROGRAMS_EDIT_FEATURE_ID)) {
      session.invalidate();
      modelAndView.setViewName(INVALID_REQUEST_PAGE);
      return modelAndView;
    }

    try {
      LoginResponse loginResponse =
          (LoginResponse) session.getAttribute(Constants.LOGIN_RESPONSE_DATA);
      CommissionProgramResponseDetails commProgramResponse =
          commissionService.getByCommProgramId(commissionDTO);
      commissionDTO.setCreatedBy(commProgramResponse.getCommProgList().get(0).getCreatedBy());
      commissionDTO.setCreatedDate(commProgramResponse.getCommProgList().get(0).getCreatedDate());
      commissionDTO.setUpdatedBy(loginResponse.getUserId().toString());
      if (commissionDTO.getMerchantOnBoardingFee() == null
          || commissionDTO.getMerchantOnBoardingFee().equals("")) {
        commissionDTO.setMerchantOnBoardingFee("0");
      }
      Response responseDetails = commissionService.updateCommissionProgram(commissionDTO);
      if (responseDetails.getErrorMessage().equals(Constants.SUCESS)) {
        modelAndView.setViewName(COMMISION_PROGRAM_SEARCH_PAGE);
        modelAndView = showCommissionProgramSearch(request, response, model, session);
        modelAndView.addObject(Constants.SUCESS,
            messageSource.getMessage("chatak.acquirer.update.commission.program.success.message",
                null, LocaleContextHolder.getLocale()));
      } else {
        modelAndView.setViewName(COMMISION_PROGRAM_SEARCH_PAGE);
        modelAndView = showCommissionProgramSearch(request, response, model, session);
        modelAndView.addObject(Constants.ERROR,
            messageSource.getMessage("chatak.acquirer.update.commission.program.error.message",
                null, LocaleContextHolder.getLocale()));
      }
    } catch (ChatakAdminException e) {
      logger.error("ERROR:: CommissionProgramController:: updateCommissionProgram method1", e);
      modelAndView = showCommissionProgramSearch(request, response, model, session);
      model.put(Constants.ERROR, e.getMessage());
    } catch (Exception e) {
      logger.error("ERROR:: CommissionProgramController:: updateCommissionProgram method", e);
      modelAndView = showCommissionProgramSearch(request, response, model, session);
      model.put(Constants.ERROR,
          messageSource.getMessage("chatak.acquirer.update.commission.program.error.message", null,
              LocaleContextHolder.getLocale()));
    }
    logger.info("Exiting  :: CommissionProgramController:: updateCommissionProgram method");
    return modelAndView;
  }

  @RequestMapping(value = COMMISION_PROGRAM_PAGINATION, method = RequestMethod.POST)
  public ModelAndView commissionPagination(final HttpSession session,
      @FormParam("pageNumber") final Integer pageNumber,
      @FormParam("totalRecords") final Integer totalRecords, Map model) {
    logger.info("Entering:: CommissionProgramController:: commissionPagination method");
    ModelAndView modelAndView = new ModelAndView(COMMISION_PROGRAM_SEARCH_PAGE);
    try {
      Long userid = (Long) session.getAttribute(Constants.LOGIN_USER_ID);
      Response response = null;

      CommissionDTO commissionDTO = (CommissionDTO) session.getAttribute(Constants.COMMISION_MODEL);
      model.put(Constants.COMMISSION_DTO, commissionDTO);
      commissionDTO.setCreatedBy(userid.toString());
      commissionDTO.setPageIndex(pageNumber);
      commissionDTO.setNoOfRecords(totalRecords);
      response = commissionService.searchCommissionProgram(commissionDTO);
      List list = response.getResponseList();
      if (StringUtil.isListNotNullNEmpty(list)) {
        modelAndView.addObject(Constants.PAGE_SIZE, commissionDTO.getPageSize());
        modelAndView =
            PaginationUtil.getPagenationModelSuccessive(modelAndView, pageNumber, totalRecords);
        modelAndView.addObject(Constants.COMMISSION_DTO_LIST, list);
      }
    } catch (Exception e) {
      logger.error("ERROR:: CommissionProgramController:: commissionPagination method", e);
      modelAndView.addObject(Constants.ERROR, messageSource
          .getMessage(Constants.CHATAK_GENERAL_ERROR, null, LocaleContextHolder.getLocale()));
    }
    logger.info("Exiting:: CommissionProgramController:: commissionPagination method");
    return modelAndView;
  }

  @RequestMapping(value = CHATAK_ADMIN_COMMISION_PROGRAM_REPORT, method = RequestMethod.POST)
  public ModelAndView downloadCommProgramReport(HttpSession session, Map model,
      @FormParam("downLoadPageNumber") final Integer downLoadPageNumber,
      @FormParam("downloadType") final String downloadType,
      @FormParam("totalRecords") final Integer totalRecords,
      @FormParam("downloadAllRecords") final boolean downloadAllRecords,
      HttpServletResponse response) {
    logger.info("Entering:: CommissionProgramController:: downloadCommProgramReport method");
    try {
      LoginResponse loginResponse =
          (LoginResponse) session.getAttribute(Constants.LOGIN_RESPONSE_DATA);
      CommissionDTO commProgramDTO = new CommissionDTO();
      commProgramDTO.setPageIndex(downLoadPageNumber);
      Integer pageSize = commProgramDTO.getPageSize();

      commProgramDTO.setCreatedBy(loginResponse.getUserId().toString());
      CommissionDTO searchList = (CommissionDTO) session
          .getAttribute(Constants.COMMISSION_PROGRAM_REQUEST_LIST_EXPORTDATA);
      if (downloadAllRecords) {
        searchList.setPageIndex(Constants.ONE);
        searchList.setPageSize(totalRecords);
      }
      if (searchList == null) {
        Response responsecommList = commissionService.searchCommissionProgram(searchList);
        List<CommissionDTO> commList = (List<CommissionDTO>) responsecommList.getResponseList();
        if (StringUtil.isListNotNullNEmpty(commList)) {
          downloadCommProgramReportConditions(downloadType, commList, response);
        }
        commProgramDTO.setPageSize(pageSize);
      } else {
        Response responsecommList = commissionService.searchCommissionProgram(searchList);
        List<CommissionDTO> commList = (List<CommissionDTO>) responsecommList.getResponseList();
        if (StringUtil.isListNotNullNEmpty(commList)) {
          downloadCommProgramReportStatements(downloadType, commList, response);
        }
        commProgramDTO.setPageSize(pageSize);
      }
    } catch (Exception e) {
      logger.error("ERROR:: CommissionProgramController:: downloadCommProgramReport method", e);
    }
    logger.info("Exiting:: CommissionProgramController:: downloadCommProgramReport method");
    return null;
  }

  private void downloadCommProgramReportConditions(String downloadType,
      List<CommissionDTO> commList, HttpServletResponse response) throws IOException {
    ExportDetails exportDetails = new ExportDetails();

    if (Constants.PDF_FILE_FORMAT.equalsIgnoreCase(downloadType)) {
      exportDetails.setExportType(ExportType.PDF);
    } else if (Constants.XLS_FILE_FORMAT.equalsIgnoreCase(downloadType)) {
      exportDetails.setExportType(ExportType.XLS);
      exportDetails.setExcelStartRowNumber(Integer.parseInt("5"));
    }
    setExportDetailsDataForDownloadCommProgramReport(commList, exportDetails);
    ExportUtil.exportData(exportDetails, response, messageSource);

  }

  private void downloadCommProgramReportStatements(String downloadType,
      List<CommissionDTO> commList, HttpServletResponse response) throws IOException {
    ExportDetails exportDetails = new ExportDetails();
    if (Constants.PDF_FILE_FORMAT.equalsIgnoreCase(downloadType)) {
      exportDetails.setExportType(ExportType.PDF);
    } else if (Constants.XLS_FILE_FORMAT.equalsIgnoreCase(downloadType)) {
      exportDetails.setExportType(ExportType.XLS);
      exportDetails.setExcelStartRowNumber(Integer.parseInt("5"));
    }
    setExportDetailsDataForDownloadCommProgramReport(commList, exportDetails);
    ExportUtil.exportData(exportDetails, response, messageSource);

  }

  private void setExportDetailsDataForDownloadCommProgramReport(List<CommissionDTO> commProgramList,
      ExportDetails exportDetails) {
    exportDetails.setReportName("Commission_Program");
    exportDetails.setHeaderMessageProperty("chatak.header.commProgram.messages");

    exportDetails.setHeaderList(getCommProgramHeaderList());
    exportDetails.setFileData(getCommProgramFileData(commProgramList));
  }

  private List<String> getCommProgramHeaderList() {
    String[] headerArr = {
        messageSource.getMessage("comm.program.exportutil.prg.name", null,
            LocaleContextHolder.getLocale()),
        messageSource.getMessage("merchant-file-exportutil-status", null,
            LocaleContextHolder.getLocale())};
    return new ArrayList<String>(Arrays.asList(headerArr));
  }

  private static List<Object[]> getCommProgramFileData(List<CommissionDTO> commProgramList) {
    List<Object[]> fileData = new ArrayList<Object[]>();

    for (CommissionDTO commProgram : commProgramList) {

      Object[] rowData =
          {commProgram.getCommissionName() != null ? commProgram.getCommissionName() + "" : "",
              commProgram.getStatus() != null ? commProgram.getStatus() + "" : ""

          };
      fileData.add(rowData);
    }

    return fileData;
  }
}
