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
import com.chatak.acquirer.admin.controller.model.Option;
import com.chatak.acquirer.admin.exception.ChatakAdminException;
import com.chatak.acquirer.admin.model.MerchantCategoryCodeSearchResponse;
import com.chatak.acquirer.admin.service.MerchantCategoryCodeService;
import com.chatak.acquirer.admin.util.ExportUtil;
import com.chatak.acquirer.admin.util.PaginationUtil;
import com.chatak.acquirer.admin.util.StringUtil;
import com.chatak.pg.constants.ActionErrorCode;
import com.chatak.pg.enums.ExportType;
import com.chatak.pg.model.MerchantCategoryCode;
import com.chatak.pg.user.bean.MerchantCategoryCodeResponse;
import com.chatak.pg.util.Constants;

@SuppressWarnings({"rawtypes", "unchecked"})
@Controller
public class MerchantCategoryCodeController implements URLMappingConstants {
  private static Logger logger = Logger.getLogger(MerchantCategoryCodeController.class);

  @Autowired
  MerchantCategoryCodeService mccService;

  @Autowired
  private MessageSource messageSource;

  @Autowired
  private MerchantCategoryCodeService merchantCategoryCodeService;

  @RequestMapping(value = CHATAK_ADMIN_MERCHANT_CATEGORY_CODE_CREATE, method = RequestMethod.GET)
  public ModelAndView showCreateMCCPage(Map model, HttpSession session) {
    logger.info("Entering:: MerchantCategoryCodeController:: showCreateMCCPage method");
    ModelAndView modelAndView = new ModelAndView(CHATAK_ADMIN_MERCHANT_CATEGORY_CODE_CREATE);

    String existingFeature = (String) session.getAttribute(Constants.EXISTING_FEATURES);
    if (!existingFeature
        .contains(FeatureConstants.ADMIN_SERVICE_MERCHANT_CATEGORY_CODE_CREATE_FEATURE_ID)) {
      session.invalidate();
      modelAndView.setViewName(INVALID_REQUEST_PAGE);
      return modelAndView;
    }
    modelAndView.addObject(Constants.ERROR, null);
    session.setAttribute(Constants.ERROR, null);
    try {

      setOptionList(session, model, modelAndView);

    } catch (Exception e) {
      logger.error("ERROR:: MerchantCategoryCodeController:: showCreateMCCPage method", e);
      modelAndView.addObject(Constants.ERROR,
          messageSource.getMessage(Constants.CHATAK_GENERAL_ERROR, null, LocaleContextHolder.getLocale()));
    }
    model.put("mcc", new MerchantCategoryCode());
    logger.info("Exiting:: MerchantCategoryCodeController:: showCreateMCCPage method");
    return modelAndView;
  }

  @RequestMapping(value = CHATAK_ADMIN_MERCHANT_CATEGORY_CODE_CREATE, method = RequestMethod.POST)
  public ModelAndView createMCC(HttpServletRequest request, HttpServletResponse response,
      HttpSession session, MerchantCategoryCode mcc, BindingResult bindingResult, Map model) {
    logger.info("Entering:: MerchantCategoryCodeController:: createMCC method");
    ModelAndView modelAndView = new ModelAndView(CHATAK_ADMIN_MERCHANT_CATEGORY_CODE_CREATE);
    String existingFeature = (String) session.getAttribute(Constants.EXISTING_FEATURES);
    if (!existingFeature
        .contains(FeatureConstants.ADMIN_SERVICE_MERCHANT_CATEGORY_CODE_CREATE_FEATURE_ID)) {
      session.invalidate();
      modelAndView.setViewName(INVALID_REQUEST_PAGE);
      return modelAndView;
    }

    session.setAttribute(Constants.ERROR, null);
    session.setAttribute(Constants.SUCESS, null);
    DataBinder dataBinder = new DataBinder(new MerchantCategoryCode());
    Long createdBy = (Long) session.getAttribute(Constants.LOGIN_USER_ID);
    mcc.setCreatedBy(String.valueOf(createdBy));

      try {
        MerchantCategoryCodeResponse mccResponse = mccService.createMerchantCategoryCode(mcc);
        if (null != mccResponse
            && mccResponse.getErrorCode().equals(ActionErrorCode.ERROR_CODE_MCC0)) {
          modelAndView = showSearchMCC(request, response, new MerchantCategoryCode(),
              dataBinder.getBindingResult(), model, session);
          modelAndView.addObject(Constants.SUCESS, messageSource
              .getMessage(ActionErrorCode.ERROR_CODE_MCC0, null, LocaleContextHolder.getLocale()));
         
        } else {
          modelAndView.addObject(Constants.ERROR, messageSource
              .getMessage(ActionErrorCode.ERROR_CODE_MCC1, null, LocaleContextHolder.getLocale()));
        
        }
      } catch (ChatakAdminException e) {
        logger.error("ERROR:: MerchantCategoryCodeController:: createMCC method", e);
        modelAndView.addObject(Constants.ERROR, messageSource.getMessage(Constants.CHATAK_GENERAL_ERROR,
            null, LocaleContextHolder.getLocale()));
      }
    model.put("mcc", mcc);
    logger.info("Entering:: MerchantCategoryCodeController:: createMCC method");
    return modelAndView;
  }

  @RequestMapping(value = CHATAK_ADMIN_MERCHANT_CATEGORY_CODE_SEARCH, method = RequestMethod.GET)
  public ModelAndView showSearchMCC(HttpServletRequest request, HttpServletResponse response,
      MerchantCategoryCode mcc, BindingResult bindingResult, Map model, HttpSession session) {
    logger.info("Entering:: MerchantCategoryCodeController:: searchMCC method");
    ModelAndView modelAndView = new ModelAndView(CHATAK_ADMIN_MERCHANT_CATEGORY_CODE_SEARCH);
    String existingFeature = (String) session.getAttribute(Constants.EXISTING_FEATURES);
    if (!existingFeature
        .contains(FeatureConstants.ADMIN_SERVICE_MERCHANT_CATEGORY_CODE_FEATURE_ID)) {
      session.invalidate();
      modelAndView.setViewName(INVALID_REQUEST_PAGE);
      return modelAndView;
    }
    session.setAttribute(Constants.MCC_MODEL, mcc);
    mcc.setPageSize(Constants.INITIAL_ENTITIES_PORTAL_DISPLAY_SIZE);
    mcc.setPageIndex(Constants.ONE);
    try {
      MerchantCategoryCodeSearchResponse searchResponse = new MerchantCategoryCodeSearchResponse();
      List<MerchantCategoryCode> mccs = new ArrayList<>();
      modelAndView.addObject(Constants.TOTAL_RECORDS, searchResponse.getTotalNoOfRows());
      modelAndView.addObject("mccs", mccs);
      modelAndView.addObject(Constants.MCC_MODEL, mcc);
    } catch (Exception e) {
      modelAndView.addObject(Constants.ERROR,
          messageSource.getMessage(Constants.CHATAK_GENERAL_ERROR, null, LocaleContextHolder.getLocale()));
      logger.error("ERROR:: MerchantCategoryCodeController:: searchMCC method", e);
    }
    modelAndView.addObject("flag", false);
    logger.info("Exiting:: MerchantCategoryCodeController:: searchMCC method");
    return modelAndView;
  }

  @RequestMapping(value = CHATAK_ADMIN_MERCHANT_CATEGORY_CODE_SEARCH, method = RequestMethod.POST)
  public ModelAndView searchMCC(HttpServletRequest request, HttpServletResponse response,
      MerchantCategoryCode mcc, BindingResult bindingResult, Map model, HttpSession session) {
    logger.info("Entering:: MerchantCategoryCodeController:: searchMCC method");
    ModelAndView modelAndView = new ModelAndView(CHATAK_ADMIN_MERCHANT_CATEGORY_CODE_SEARCH);
    String existingFeature = (String) session.getAttribute(Constants.EXISTING_FEATURES);
    if (!existingFeature
        .contains(FeatureConstants.ADMIN_SERVICE_MERCHANT_CATEGORY_CODE_FEATURE_ID)) {
      session.invalidate();
      modelAndView.setViewName(INVALID_REQUEST_PAGE);
      return modelAndView;
    }
    modelAndView.addObject(Constants.ERROR, null);
    session.setAttribute(Constants.ERROR, null);
    session.setAttribute(Constants.MCC_MODEL, mcc);
    mcc.setPageIndex(Constants.ONE);
    try {
      MerchantCategoryCodeSearchResponse searchResponse =
          mccService.searchMerchantCategoryCode(mcc);
      List<MerchantCategoryCode> mccs = new ArrayList<>();
      if (searchResponse != null && !CollectionUtils.isEmpty(searchResponse.getMccs())) {
        mccs = searchResponse.getMccs();
        modelAndView.addObject("pageSize", mcc.getPageSize());
        modelAndView =
            PaginationUtil.getPagenationModel(modelAndView, searchResponse.getTotalNoOfRows());
        session.setAttribute(Constants.PAGE_NUMBER, Constants.ONE);
        session.setAttribute(Constants.TOTAL_RECORDS, searchResponse.getTotalNoOfRows());
      }
      if (searchResponse != null) {
    	  modelAndView.addObject(Constants.TOTAL_RECORDS, searchResponse.getTotalNoOfRows());
      } else {
    	  modelAndView.addObject(Constants.TOTAL_RECORDS, Constants.ZERO);
      }
      modelAndView.addObject("mccs", mccs);
      modelAndView.addObject(Constants.MCC_MODEL, mcc);
    } catch (Exception e) {
      modelAndView.addObject(Constants.ERROR,
          messageSource.getMessage(Constants.CHATAK_GENERAL_ERROR, null, LocaleContextHolder.getLocale()));
      logger.error("ERROR:: MerchantCategoryCodeController:: searchMCC method", e);
    }
    modelAndView.addObject("flag", true);
    logger.info("Exiting:: MerchantCategoryCodeController:: searchMCC method");
    return modelAndView;
  }

  @RequestMapping(value = CHATAK_ADMIN_MERCHANT_CATEGORY_CODE_EDIT, method = RequestMethod.POST)
  public ModelAndView showEditMCC(HttpServletRequest request, HttpServletResponse response,
      @FormParam("editId") final Long editId, HttpSession session, Map model) {
    logger.info("Entering:: MerchantCategoryCodeController:: showEditMCC method");
    ModelAndView modelAndView = new ModelAndView(CHATAK_ADMIN_MERCHANT_CATEGORY_CODE_EDIT);

    String existingFeature = (String) session.getAttribute(Constants.EXISTING_FEATURES);
    if (!existingFeature
        .contains(FeatureConstants.ADMIN_SERVICE_MERCHANT_CATEGORY_CODE_EDIT_FEATURE_ID)) {
      session.invalidate();
      modelAndView.setViewName(INVALID_REQUEST_PAGE);
      return modelAndView;
    }
    MerchantCategoryCode mcc = new MerchantCategoryCode();
    DataBinder dataBinder = setMerchantCategoryCodeOnSession(session, modelAndView, mcc);
    try {
      List<Option> tccMultipleList = setOptionList(session, model, modelAndView);
      mcc = mccService.getMCCDetails(editId);

      validateMerchantCategoryCode(session, model, modelAndView, mcc, tccMultipleList);

    } catch (ChatakAdminException exp) {
      modelAndView =
          searchMCC(request, response, mcc, dataBinder.getBindingResult(), model, session);
      modelAndView.addObject(Constants.ERROR,
          messageSource.getMessage(Constants.CHATAK_GENERAL_ERROR, null, LocaleContextHolder.getLocale()));
      modelAndView.addObject("mcc", new MerchantCategoryCode());
      logger.error("ERROR:: MerchantCategoryCodeController:: showEditMCC method", exp);
    } catch (Exception e) {
      modelAndView.addObject(Constants.ERROR,
          messageSource.getMessage(Constants.CHATAK_GENERAL_ERROR, null, LocaleContextHolder.getLocale()));
      logger.error("ERROR:: MerchantCategoryCodeController:: showEditMCC method", e);
    }

    logger.info("Exiting:: MerchantCategoryCodeController:: showEditMCC method");
    return modelAndView;

  }

  private List<Option> setOptionList(HttpSession session, Map model, ModelAndView modelAndView) {
    List<Option> tccMultipleList = mccService.getAllTCCs();
    modelAndView.addObject(Constants.TCC_MULTIPLE_LIST, tccMultipleList);
    model.put(Constants.TCC_MULTIPLE_LIST, tccMultipleList != null ? tccMultipleList : null);
    session.setAttribute(Constants.TCC_MULTIPLE_LIST, tccMultipleList);
    return tccMultipleList;
  }

  private DataBinder setMerchantCategoryCodeOnSession(HttpSession session,
      ModelAndView modelAndView, MerchantCategoryCode mcc) {
    DataBinder dataBinder = new DataBinder(mcc);

    modelAndView.addObject(Constants.ERROR, null);
    session.setAttribute(Constants.ERROR, null);
    session.setAttribute("mcc", mcc);
    return dataBinder;
  }

  private void validateMerchantCategoryCode(HttpSession session, Map model,
      ModelAndView modelAndView, MerchantCategoryCode mcc, List<Option> tccMultipleList)
      throws ChatakAdminException {
    if (null != mcc) {
      session.setAttribute("mcc", mcc);
      modelAndView.addObject("mcc", mcc);
      model.put("selectedTCCMultiple",
          mcc.getSelectedTCCMultiple() != null ? mcc.getSelectedTCCMultiple() : null);
      modelAndView.addObject(Constants.TCC_MULTIPLE_LIST, tccMultipleList);
      model.put(Constants.TCC_MULTIPLE_LIST, tccMultipleList != null ? tccMultipleList : null);
      session.setAttribute(Constants.TCC_MULTIPLE_LIST, tccMultipleList);

    } else {
      new MerchantCategoryCode();
      throw new ChatakAdminException();
    }
  }

  @RequestMapping(value = CHATAK_ADMIN_MERCHANT_CATEGORY_CODE_VIEW, method = RequestMethod.POST)
  public ModelAndView showViewMCC(HttpServletRequest request, HttpServletResponse response,
      @FormParam("viewId") final Long viewId, HttpSession session, Map model) {
    logger.info("Entering:: MerchantCategoryCodeController:: showViewMCC method");
    ModelAndView modelAndView = new ModelAndView(CHATAK_ADMIN_MERCHANT_CATEGORY_CODE_VIEW);
    String existingFeature = (String) session.getAttribute(Constants.EXISTING_FEATURES);
    if (!existingFeature
        .contains(FeatureConstants.ADMIN_SERVICE_MERCHANT_CATEGORY_CODE_VIEW_FEATURE_ID)) {
      session.invalidate();
      modelAndView.setViewName(INVALID_REQUEST_PAGE);
      return modelAndView;
    }
    MerchantCategoryCode mcc = new MerchantCategoryCode();
    DataBinder dataBinder = setMerchantCategoryCodeOnSession(session, modelAndView, mcc);
    try {
      List<Option> tccMultipleList = setOptionList(session, model, modelAndView);
      mcc = mccService.getMCCDetails(viewId);

      validateMerchantCategoryCode(session, model, modelAndView, mcc, tccMultipleList);

    } catch (ChatakAdminException e) {
      modelAndView =
          searchMCC(request, response, mcc, dataBinder.getBindingResult(), model, session);
      modelAndView.addObject(Constants.ERROR,
          messageSource.getMessage(Constants.CHATAK_GENERAL_ERROR, null, LocaleContextHolder.getLocale()));
      modelAndView.addObject("mcc", new MerchantCategoryCode());
      logger.error("ERROR:: MerchantCategoryCodeController:: showViewMCC method", e);
    } catch (Exception e) {
      modelAndView.addObject(Constants.ERROR,
          messageSource.getMessage(Constants.CHATAK_GENERAL_ERROR, null, LocaleContextHolder.getLocale()));
      logger.error("ERROR:: MerchantCategoryCodeController:: showViewMCC method", e);
    }

    logger.info("Exiting:: MerchantCategoryCodeController:: showViewMCC method");
    return modelAndView;

  }

  @RequestMapping(value = CHATAK_ADMIN_MERCHANT_CATEGORY_CODE_UPDATE, method = RequestMethod.POST)
  public ModelAndView updateMCC(HttpServletRequest request, HttpServletResponse response,
      MerchantCategoryCode mcc, BindingResult bindingResult, Map model, HttpSession session) {
    logger.info("Entering:: MerchantCategoryCodeController:: updateMCC method");
    ModelAndView modelAndView = new ModelAndView(CHATAK_ADMIN_MERCHANT_CATEGORY_CODE_EDIT);

    String existingFeature = (String) session.getAttribute(Constants.EXISTING_FEATURES);
    if (!existingFeature
        .contains(FeatureConstants.ADMIN_SERVICE_MERCHANT_CATEGORY_CODE_EDIT_FEATURE_ID)) {
      session.invalidate();
      modelAndView.setViewName(INVALID_REQUEST_PAGE);
      return modelAndView;
    }

    MerchantCategoryCode mcctemp = new MerchantCategoryCode();
    DataBinder dataBinder = new DataBinder(mcctemp);

    modelAndView.addObject(Constants.ERROR, null);
    try {
      Long updatedBy = (Long) session.getAttribute(Constants.LOGIN_USER_ID);
      mcc.setUpdatedBy(String.valueOf(updatedBy));

      MerchantCategoryCodeResponse mccResponse = mccService.updateMerchantCategoryCode(mcc);
      if (null != mccResponse
          && mccResponse.getErrorCode().equals(ActionErrorCode.ERROR_CODE_MCC2)) {
        new MerchantCategoryCode();
        modelAndView =
            showSearchMCC(request, response, mcc, dataBinder.getBindingResult(), model, session);
        modelAndView.addObject(Constants.SUCESS, messageSource
            .getMessage(ActionErrorCode.ERROR_CODE_MCC2, null, LocaleContextHolder.getLocale()));
     
      } else {
        modelAndView.addObject(Constants.ERROR, messageSource
            .getMessage(ActionErrorCode.ERROR_CODE_MCC3, null, LocaleContextHolder.getLocale()));
     
      }
    } catch (Exception e) {
      logger.error("ERROR:: MerchantCategoryCodeController:: updateMCC method", e);
      modelAndView.addObject(Constants.ERROR,
          messageSource.getMessage(Constants.CHATAK_GENERAL_ERROR, null, LocaleContextHolder.getLocale()));
    }
    model.put(Constants.MCC_MODEL, mcc);
    logger.info("Exiting:: MerchantCategoryCodeController:: updateMCC method");
    return modelAndView;
  }

  @RequestMapping(value = CHATAK_ADMIN_MERCHANT_CATEGORY_CODE_DELERE, method = RequestMethod.POST)
  public ModelAndView deleteMCC(Map model, HttpServletRequest request, MerchantCategoryCode mcc,
      HttpSession session, @FormParam("getDeleteMCCId") final Long getDeleteMCCId,
      HttpServletResponse response) {
    ModelAndView modelAndView = new ModelAndView(CHATAK_ADMIN_MERCHANT_CATEGORY_CODE_SEARCH);
    logger.info("Entering:: UserManagementController:: editUSer method");
    String existingFeature = (String) session.getAttribute(Constants.EXISTING_FEATURES);
    if (!existingFeature
        .contains(FeatureConstants.ADMIN_SERVICE_MERCHANT_CATEGORY_CODE_DELETE_FEATURE_ID)) {
      session.invalidate();
      modelAndView.setViewName(INVALID_REQUEST_PAGE);
      return modelAndView;
    }
    MerchantCategoryCode mcctemp = new MerchantCategoryCode();
    DataBinder dataBinder = new DataBinder(mcctemp);
    try {
      MerchantCategoryCodeResponse mccs = new MerchantCategoryCodeResponse();
      model.put("mccs", mccs);
      mccs = mccService.deleteMcc(getDeleteMCCId);
      if (null != mccs && "00".equals(mccs.getErrorCode())) {
        model.put(Constants.SUCESS, mccs.getErrorMessage());
        return showSearchMCC(request, response, mcc, dataBinder.getBindingResult(), model, session);
      } else if (null != mccs) {
        model.put(Constants.ERROR, mccs.getErrorMessage());
        return showSearchMCC(request, response, mcc, dataBinder.getBindingResult(), model, session);
      } else {
    	  model.put(Constants.ERROR, Constants.EMPTY_STRING);
          return showSearchMCC(request, response, mcc, dataBinder.getBindingResult(), model, session);
      }
    } catch (Exception e) {
      logger.error("ERROR:: MerchantCategoryCodeController:: deleteMCC method", e);
      model.put(Constants.ERROR, messageSource.getMessage("chatak.normal.error.message", null,
          LocaleContextHolder.getLocale()));
    }
    logger.info("Exit:: MerchantCategoryCodeController:: deleteMCC method");
    return modelAndView;
  }

  @RequestMapping(value = CHATAK_ADMIN_MERCHANT_CATEGORY_CODE_PAGINATION,
      method = RequestMethod.POST)
  public ModelAndView getPaginationList(final HttpSession session,
      @FormParam(Constants.PAGE_NUMBER) final Integer pageNumber,
      @FormParam(Constants.TOTAL_RECORDS) final Integer totalRecords, Map model) {
    logger.info("Entering:: MerchantCategoryCodeController:: getPaginationList method");
    ModelAndView modelAndView = new ModelAndView(CHATAK_ADMIN_MERCHANT_CATEGORY_CODE_SEARCH);
    try {
      MerchantCategoryCode mcc = (MerchantCategoryCode) session.getAttribute(Constants.MCC_MODEL);
      model.put(Constants.MCC_MODEL, mcc);
      mcc.setPageIndex(pageNumber);
      mcc.setNoOfRecords(totalRecords);
      mcc.setPageSize(Constants.INITIAL_ENTITIES_PORTAL_DISPLAY_SIZE);
      try {
        MerchantCategoryCodeSearchResponse searchResponse =
            mccService.searchMerchantCategoryCode(mcc);
        List<MerchantCategoryCode> mccs = new ArrayList<>();
        if (searchResponse != null && !CollectionUtils.isEmpty(searchResponse.getMccs())) {
          mccs = searchResponse.getMccs();
          modelAndView.addObject("pageSize", mcc.getPageSize());
          modelAndView = PaginationUtil.getPagenationModelSuccessive(modelAndView, pageNumber,
              searchResponse.getTotalNoOfRows());
          session.setAttribute(Constants.PAGE_NUMBER, pageNumber);
          session.setAttribute(Constants.TOTAL_RECORDS, totalRecords);
        }
        modelAndView.addObject("mccs", mccs);
      } catch (Exception e) {
        modelAndView.addObject(Constants.ERROR, messageSource.getMessage(Constants.CHATAK_GENERAL_ERROR,
            null, LocaleContextHolder.getLocale()));
        logger.error("ERROR:: MerchantCategoryCodeController:: getPaginationList method", e);
      }
    } catch (Exception e) {
      logger.error("ERROR:: MerchantCategoryCodeController:: getPaginationList method", e);
      modelAndView.addObject(Constants.ERROR,
          messageSource.getMessage(Constants.CHATAK_GENERAL_ERROR, null, LocaleContextHolder.getLocale()));
    }
    logger.info("Exiting:: MerchantCategoryCodeController:: getPaginationList method");
    return modelAndView;
  }


  /**
   * Method for download report
   * 
   * @param session
   * @param model
   * @param request
   * @param downLoadPageNumber
   * @param downloadType
   * @param response
   * @return
   */
  @RequestMapping(value = GET_MERCHANT_CATEGORY_CODE_REPORT, method = RequestMethod.POST)
  public ModelAndView downloadMerchantCategoryCodeList(HttpSession session, Map model,
      HttpServletRequest request, HttpServletResponse response,
      @FormParam("downLoadPageNumber") final Integer downLoadPageNumber,
      @FormParam(Constants.TOTAL_RECORDS) final Integer totalRecords,
      @FormParam("downloadAllRecords") final boolean downloadAllRecords) {
    String downloadType=request.getParameter("downloadType");
    logger
        .info("Entering:: MerchatCategoryCodeController:: downloadMerchantCategoryCodeList method");
    MerchantCategoryCodeSearchResponse merchantCategoryCodeResponse = null;
    MerchantCategoryCode merchantCategoryCode = null;
    List<MerchantCategoryCode> list = null;
    try {
      merchantCategoryCode = (MerchantCategoryCode) session.getAttribute(Constants.MCC_MODEL);
      merchantCategoryCode.setPageIndex(downLoadPageNumber);
      Integer pageSize = merchantCategoryCode.getPageSize();
      if (downloadAllRecords) {
        merchantCategoryCode.setPageIndex(Constants.ONE);
        merchantCategoryCode.setPageSize(totalRecords);
      }
      merchantCategoryCodeResponse =
          merchantCategoryCodeService.searchMerchantCategoryCode(merchantCategoryCode);
      list = merchantCategoryCodeResponse.getMccs();
      ExportDetails exportDetails = new ExportDetails();
      if (StringUtil.isListNotNullNEmpty(list)) {
        if (Constants.PDF_FILE_FORMAT.equalsIgnoreCase(downloadType)) {
          exportDetails.setExportType(ExportType.PDF);
        } else if (Constants.XLS_FILE_FORMAT.equalsIgnoreCase(downloadType)) {
          exportDetails.setExportType(ExportType.XLS);
          exportDetails.setExcelStartRowNumber(Integer.parseInt("5"));
      }
      setExportDetailsDataForDownloadMerchantCategoryCodeReport(list, exportDetails); 
      ExportUtil.exportData(exportDetails, response, messageSource);
      }
      merchantCategoryCode.setPageSize(pageSize);
    } catch (Exception e) {
      logger.error(
          "ERROR:: MerchatCategoryCodeController :: downloadMerchantCategoryCodeList method", e);
    }
    logger
        .info("Exiting:: MerchatCategoryCodeController :: downloadMerchantCategoryCodeList method");
    return null;
  }
  
  private void setExportDetailsDataForDownloadMerchantCategoryCodeReport(List<MerchantCategoryCode> list,
      ExportDetails exportDetails) {
    exportDetails.setReportName("MerchantCategoryCodeList");
    exportDetails.setHeaderMessageProperty("chatak.header.merchantcategorycode.messages");

    exportDetails.setHeaderList(getMerchantCategoryCodeHeaderList());
    exportDetails.setFileData(getMerchantCategoryCodeFileData(list));
  }

  @RequestMapping(value = CHATAK_ADMIN_MERCHANT_CATEGORY_CODE_ACTIVATION_SUSPENTION,
      method = RequestMethod.POST)
  public ModelAndView changeMCCStatus(final HttpSession session,
      @FormParam("suspendActiveId") final Long merchantCategoryCodeId,
      @FormParam("suspendActiveStatus") final String merchantCategoryCodeStatus,
      @FormParam("reason") final String reason, Map<String, Serializable> model,
      HttpServletRequest request, HttpServletResponse response) {
    logger.info("Entering:: MerchatCategoryCodeController:: changeMCCStatus method");

    ModelAndView modelAndView = new ModelAndView(CHATAK_ADMIN_MERCHANT_CATEGORY_CODE_SEARCH);
    String existingFeature = (String) session.getAttribute(Constants.EXISTING_FEATURES);
    if (!existingFeature
        .contains(FeatureConstants.ADMIN_SERVICE_MERCHANT_CATEGORY_CODE_FEATURE_ID)) {
      session.invalidate();
      modelAndView.setViewName(INVALID_REQUEST_PAGE);
      return modelAndView;
    }
    try {
      MerchantCategoryCode merchantCategoryCode = new MerchantCategoryCode();
      merchantCategoryCode.setId(merchantCategoryCodeId);
      merchantCategoryCode.setStatus(merchantCategoryCodeStatus);
      merchantCategoryCode.setReason(reason);
      MerchantCategoryCodeResponse mccResponse =
          mccService.changeMerchantCategoryCodeStatus(merchantCategoryCode);
      modelAndView = getPaginationList(session,
          StringUtil.isNull((Integer) session.getAttribute(Constants.PAGE_NUMBER)) ? 1
              : (Integer) session.getAttribute(Constants.PAGE_NUMBER),
          (Integer) session.getAttribute(Constants.TOTAL_RECORDS), model);
      if (ActionErrorCode.ERROR_CODE_00.equals(mccResponse.getErrorCode())) {
        model.put(Constants.SUCESS,
            messageSource.getMessage("chatak.merchantcategorycode.status.change.sucess", null,
                LocaleContextHolder.getLocale()));
      } else {
        model.put(Constants.ERROR, messageSource.getMessage(Constants.CHATAK_GENERAL_ERROR, null,
            LocaleContextHolder.getLocale()));
      }
    } catch (Exception e) {
      logger.error("ERROR:: MerchatCategoryCodeController:: changeMCCStatus method", e);
      model.put(Constants.ERROR,
          messageSource.getMessage(Constants.CHATAK_GENERAL_ERROR, null, LocaleContextHolder.getLocale()));
    }
    logger.info("Exiting:: MerchatCategoryCodeController:: changeMCCStatus method");
    return modelAndView;
  }
  
  private List<String> getMerchantCategoryCodeHeaderList() {
    String[] headerArr = {
        messageSource.getMessage("merchantCategoryCode-file-exportutil-merchantcategorycode", null,
            LocaleContextHolder.getLocale()),
        messageSource.getMessage("merchantCategoryCode-file-exportutil-transactioncategorycode",
            null, LocaleContextHolder.getLocale()),
        messageSource.getMessage("merchantCategoryCode-file-exportutil-description", null,
            LocaleContextHolder.getLocale()),
        messageSource.getMessage("merchantCategoryCode-file-exportutil-status", null,
            LocaleContextHolder.getLocale())};
    return new ArrayList<String>(Arrays.asList(headerArr));
  }

  private static List<Object[]> getMerchantCategoryCodeFileData(List<MerchantCategoryCode> list) {
    List<Object[]> fileData = new ArrayList<Object[]>();

    for (MerchantCategoryCode merchantCategoryCodeData : list) {

      Object[] rowData = {
          merchantCategoryCodeData.getMerchantCategoryCode() != null
              ? merchantCategoryCodeData.getMerchantCategoryCode() : " " + "",
          merchantCategoryCodeData.getSelectedTcc() != null
              ? merchantCategoryCodeData.getSelectedTcc() : " " + "",
          merchantCategoryCodeData.getDescription() != null
              ? merchantCategoryCodeData.getDescription() : " " + "",
          merchantCategoryCodeData.getStatus() != null ? merchantCategoryCodeData.getStatus()
              : " " + ""

      };
      fileData.add(rowData);
    }

    return fileData;
  }
}
