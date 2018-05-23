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
import com.chatak.acquirer.admin.model.ResellerSearchResponse;
import com.chatak.acquirer.admin.model.ResellerValue;
import com.chatak.acquirer.admin.service.MerchantUpdateService;
import com.chatak.acquirer.admin.service.ResellerService;
import com.chatak.acquirer.admin.util.ExportUtil;
import com.chatak.acquirer.admin.util.PaginationUtil;
import com.chatak.acquirer.admin.util.StringUtil;
import com.chatak.pg.bean.Response;
import com.chatak.pg.constants.ActionErrorCode;
import com.chatak.pg.enums.ExportType;
import com.chatak.pg.model.ResellerData;
import com.chatak.pg.user.bean.AddResellerResponse;
import com.chatak.pg.user.bean.DeleteResellerResponse;
import com.chatak.pg.user.bean.UpdateResellerResponse;
import com.chatak.pg.util.Constants;

@Controller
@SuppressWarnings({"rawtypes", "unchecked", "unused"})
public class ResellerController implements URLMappingConstants {

  private static Logger logger = Logger.getLogger(ResellerController.class);

  @Autowired
  MessageSource messageSource;

  @Autowired
  private MerchantUpdateService merchantUpdateService;

  @Autowired
  private ResellerService resellerService;

  @RequestMapping(value = CHATAK_ADMIN_SEARCH_RESELLER_PAGE, method = RequestMethod.GET)
  public ModelAndView showSearchResellerPage(HttpServletRequest request,
      HttpServletResponse response, Map model, HttpSession session) {
    logger.info("Entering:: ResellerController:: showSearchResellerPage method");
    ModelAndView modelAndView = new ModelAndView(CHATAK_ADMIN_SEARCH_RESELLER);
    String existingFeature = (String) session.getAttribute(Constants.EXISTING_FEATURES);
    if (!existingFeature.contains(FeatureConstants.ADMIN_SERVICE_RESELLER_FEATURE_ID)) {
      session.invalidate();
      modelAndView.setViewName(INVALID_REQUEST_PAGE);
      return modelAndView;
    }
   
    ResellerData resellerData = new ResellerData();
    session.setAttribute(Constants.RESELLER_MODEL, resellerData);
    resellerData.setPageSize(Constants.MAX_ENTITIES_PORTAL_DISPLAY_SIZE);
    resellerData.setPageIndex(Constants.ONE);
    try {
      List<Option> countryList = merchantUpdateService.getCountries();
      modelAndView.addObject(Constants.COUNTRY_LIST, countryList);
      session.setAttribute(Constants.COUNTRY_LIST, countryList);
      ResellerSearchResponse resellerValues = resellerService.searchReseller(resellerData);
      modelAndView.addObject(Constants.RESELLER_DATA, resellerData);
      if (resellerValues != null && !CollectionUtils.isEmpty(resellerValues.getReseller())) {
        modelAndView.addObject(Constants.PAGE_SIZE, resellerData.getPageSize());
        modelAndView =
            PaginationUtil.getPagenationModel(modelAndView, resellerValues.getTotalNoOfRows());
      }
      if (resellerValues != null) {
    	  modelAndView.addObject(Constants.RESELLERS_MODEL, resellerValues.getReseller());
      }
		
    } catch (Exception e) {
      modelAndView.addObject(Constants.ERROR,
          messageSource.getMessage(Constants.CHATAK_GENERAL_ERROR, null, LocaleContextHolder.getLocale()));
      logger.error("ERROR:: ResellerController:: showSearchResellerPage method", e);
    }
    logger.info("Exiting:: ResellerController:: showSearchResellerPage method");
    return modelAndView;
  }

  @RequestMapping(value = CHATAK_ADMIN_RESELLER_CREATE_PAGE, method = RequestMethod.GET)
  public ModelAndView showCreateReseller(Map model, HttpSession session) {
    logger.info("Entering:: ResellerController:: showCreateReseller method");
    ModelAndView modelAndView = new ModelAndView(CHATAK_ADMIN_SHOW_CREATE_RESELLER);

    String existingFeature = (String) session.getAttribute(Constants.EXISTING_FEATURES);
    if (!existingFeature.contains(FeatureConstants.ADMIN_SERVICE_RESELLER_CREATE_FEATURE_ID)) {
      session.invalidate();
      modelAndView.setViewName(INVALID_REQUEST_PAGE);
      return modelAndView;
    }
    modelAndView.addObject(Constants.ERROR, null);
    session.setAttribute(Constants.ERROR, null);
    ResellerData resellerData = new ResellerData();
    try {
      List<Option> countryList = merchantUpdateService.getCountries();
      modelAndView.addObject(Constants.COUNTRY_LIST, countryList);
      session.setAttribute(Constants.COUNTRY_LIST, countryList);
      Response stateList = merchantUpdateService.getStatesByCountry("USA");
      modelAndView.addObject(Constants.STATE_LIST, stateList.getResponseList());
      session.setAttribute(Constants.STATE_LIST, stateList.getResponseList());
      model.put(Constants.RESELLER_DATA, resellerData);
    } catch (Exception e) {
      modelAndView.addObject(Constants.ERROR,
          messageSource.getMessage(Constants.CHATAK_GENERAL_ERROR, null, LocaleContextHolder.getLocale()));
      logger.error("ERROR:: ResellerController:: showCreateReseller method", e);
    }
    return modelAndView;
  }

  @RequestMapping(value = CHATAK_ADMIN_CREATE_RESELLER, method = RequestMethod.POST)
  public ModelAndView createReseller(HttpServletRequest request, HttpServletResponse response,
      ResellerData resellerData, BindingResult bindingResult, Map model, HttpSession session) {
    logger.info("Entering:: ResellerController:: createReseller method");
    ModelAndView modelAndView = new ModelAndView(CHATAK_ADMIN_SHOW_CREATE_RESELLER);
    String existingFeature = (String) session.getAttribute(Constants.EXISTING_FEATURES);
    if (!existingFeature.contains(FeatureConstants.ADMIN_SERVICE_RESELLER_CREATE_FEATURE_ID)) {
      session.invalidate();
      modelAndView.setViewName(INVALID_REQUEST_PAGE);
      return modelAndView;
    }
    modelAndView.addObject(Constants.ERROR, null);
    try {
      Long userid = (Long) session.getAttribute(Constants.LOGIN_USER_ID);

      String sequense = resellerService.getResllerAccountNumber();
      resellerData.setAccountNumber(Long.valueOf(sequense));
      resellerData.setCreatedBy(userid.toString());
      Response stateList = merchantUpdateService.getStatesByCountry("USA");
      modelAndView.addObject(Constants.STATE_LIST, stateList.getResponseList());
      session.setAttribute(Constants.STATE_LIST, stateList.getResponseList());
      AddResellerResponse addResellerResponse = resellerService.addReseller(resellerData);
      if (null != addResellerResponse
          && addResellerResponse.getErrorCode().equals(ActionErrorCode.ERROR_CODE_00)) {
        model.put(Constants.RESELLER_DATA, new ResellerData());
        model.put(Constants.SUCESS, messageSource.getMessage("chatak.reseller.create.success", null,
            LocaleContextHolder.getLocale()));
        return showSearchResellerPage(request, response, model, session);
      } else if (null != addResellerResponse && addResellerResponse.getErrorCode()
          .equals(ActionErrorCode.ERROR_CODE_DUPLICATE_ENTRY)) {
        modelAndView.addObject(Constants.ERROR, messageSource.getMessage(
            ActionErrorCode.ERROR_CODE_DUPLICATE_ENTRY, null, LocaleContextHolder.getLocale()));
      
      } else {
        modelAndView.addObject(Constants.ERROR, messageSource.getMessage(
            ActionErrorCode.ERROR_CODE_DUPLICATE_ENTRY, null, LocaleContextHolder.getLocale()));
       
      }
    } catch (Exception e) {
      logger.error("ERROR:: ResellerController:: createReseller method", e);
      modelAndView.addObject(Constants.ERROR,
          messageSource.getMessage(Constants.CHATAK_GENERAL_ERROR, null, LocaleContextHolder.getLocale()));
    }
    logger.info("Exiting:: ResellerController:: createReseller method");
    return modelAndView;
  }

  @RequestMapping(value = CHATAK_ADMIN_SEARCH_PROCESS_RESELLER_PAGE, method = RequestMethod.POST)
  public ModelAndView processSearchResellerPage(HttpServletRequest request,
      HttpServletResponse response, ResellerData resellerData, BindingResult bindingResult,
      Map model, HttpSession session) {
    logger.info("Entering:: ResellerController:: processSearchResellerPage method");
    ModelAndView modelAndView = new ModelAndView(CHATAK_ADMIN_SEARCH_RESELLER);
    String existingFeature = (String) session.getAttribute(Constants.EXISTING_FEATURES);
    if (!existingFeature.contains(FeatureConstants.ADMIN_SERVICE_RESELLER_FEATURE_ID)) {
      session.invalidate();
      modelAndView.setViewName(INVALID_REQUEST_PAGE);
      return modelAndView;
    }
    modelAndView.addObject(Constants.ERROR, null);
    session.setAttribute(Constants.ERROR, null);
    session.setAttribute(Constants.RESELLER_MODEL, resellerData);
    resellerData.setPageIndex(Constants.ONE);
    try {
      List<Option> countryList = merchantUpdateService.getCountries();
      modelAndView.addObject(Constants.COUNTRY_LIST, countryList);
      session.setAttribute(Constants.COUNTRY_LIST, countryList);
      ResellerSearchResponse resellerResponseList = resellerService.searchReseller(resellerData);
      modelAndView.addObject("resellerValues", resellerResponseList.getReseller());
      if (resellerResponseList != null
          && !CollectionUtils.isEmpty(resellerResponseList.getReseller())) {
        modelAndView.addObject(Constants.PAGE_SIZE, resellerData.getPageSize());
        modelAndView = PaginationUtil.getPagenationModel(modelAndView,
            resellerResponseList.getTotalNoOfRows());
      }
      modelAndView.addObject(Constants.RESELLER_MODEL, resellerData);
    } catch (Exception e) {
      modelAndView.addObject(Constants.ERROR,
          messageSource.getMessage(Constants.CHATAK_GENERAL_ERROR, null, LocaleContextHolder.getLocale()));
      logger.error("ERROR:: ResellerController:: processSearchResellerPage method", e);
    }
    modelAndView.addObject(Constants.RESELLER_MODEL, resellerData);
    logger.info("Exiting:: ResellerController:: processSearchResellerPage method");
    return modelAndView;
  }



  @RequestMapping(value = CHATAK_ADMIN_SHOW_EDIT_RESELLER, method = RequestMethod.POST)
  public ModelAndView showEditReseller(HttpServletRequest request, HttpServletResponse response,
      @FormParam("getResellerId") final Long getResellerId, HttpSession session, Map model) {
    logger.info("Entering :: ResellerController :: showEditReseller method ");
    ModelAndView modelAndView = new ModelAndView(CHATAK_ADMIN_EDIT_RESELLER);
    String existingFeature = (String) session.getAttribute(Constants.EXISTING_FEATURES);
    if (!existingFeature.contains(FeatureConstants.ADMIN_SERVICE_RESELLER_EDIT_FEATURE_ID)) {
      session.invalidate();
      modelAndView.setViewName(INVALID_REQUEST_PAGE);
      return modelAndView;
    }
    ResellerData resellerData = new ResellerData();
    new DataBinder(resellerData);
    modelAndView.addObject(Constants.ERROR, null);
    modelAndView.addObject(Constants.SUCESS, null);
    try {
      List<Option> countryList = merchantUpdateService.getCountries();
      modelAndView.addObject(Constants.COUNTRY_LIST, countryList);
      session.setAttribute(Constants.COUNTRY_LIST, countryList);
      resellerData.setResellerId(getResellerId);
      resellerData = resellerService.getReseller(resellerData);

      if (null == resellerData) {
        new ResellerData();
        throw new ChatakAdminException();
      } else {
        Response stateList = merchantUpdateService.getStatesByCountry(resellerData.getCountry());
        modelAndView.addObject(Constants.STATE_LIST, stateList.getResponseList());
        session.setAttribute(Constants.STATE_LIST, stateList.getResponseList());
        session.setAttribute("getResellerId", getResellerId);
        modelAndView.addObject(Constants.RESELLER_DATA, resellerData);
      }
    } catch (ChatakAdminException e) {
      modelAndView = showSearchResellerPage(request, response, model, session);
      modelAndView.addObject(Constants.ERROR,
          messageSource.getMessage(Constants.CHATAK_GENERAL_ERROR, null, LocaleContextHolder.getLocale()));
      modelAndView.addObject(Constants.RESELLER_DATA, new ResellerData());
      logger.error("ERROR:: ResellerController:: showEditReseller method", e);
    } catch (Exception e) {
      modelAndView.addObject(Constants.ERROR,
          messageSource.getMessage(Constants.CHATAK_GENERAL_ERROR, null, LocaleContextHolder.getLocale()));
      logger.error("ERROR:: ResellerController:: showEditReseller method", e);
    }
    logger.info("EXITING :: ResellerController :: showEditReseller");
    return modelAndView;
  }

  @RequestMapping(value = CHATAK_ADMIN_UPDATE_RESELLER, method = RequestMethod.POST)
  public ModelAndView updateReseller(HttpServletRequest request, HttpServletResponse response,
      ResellerData resellerData, BindingResult bindingResult, HttpSession session, Map model) {
    logger.info("Entering :: MerchantController :: updateReseller method");
    ModelAndView modelAndView = new ModelAndView(CHATAK_ADMIN_EDIT_RESELLER);
    String existingFeature = (String) session.getAttribute(Constants.EXISTING_FEATURES);
    if (!existingFeature.contains(FeatureConstants.ADMIN_SERVICE_RESELLER_EDIT_FEATURE_ID)) {
      session.invalidate();
      modelAndView.setViewName(INVALID_REQUEST_PAGE);
      return modelAndView;
    }
    modelAndView.addObject(Constants.ERROR, null);
    session.setAttribute(Constants.ERROR, null);
    resellerData.setPageSize(Constants.MAX_ENTITIES_PORTAL_DISPLAY_SIZE);
    resellerData.setPageIndex(Constants.ONE);
    try {
     
      Long userid = (Long) session.getAttribute(Constants.LOGIN_USER_ID);
      resellerData.setUpdatedBy(userid.toString());

      UpdateResellerResponse updateResellerResponse = resellerService.updateReseller(resellerData);
      if (updateResellerResponse.isUpdated()
          && updateResellerResponse.getErrorCode().equals(ActionErrorCode.ERROR_CODE_00)) {

        modelAndView = showSearchResellerPage(request, response, model, session);
        modelAndView.addObject(Constants.SUCESS, messageSource
            .getMessage("chatak.reseller.update.success", null, LocaleContextHolder.getLocale()));
      } else {
        modelAndView.addObject(Constants.RESELLER_DATA, resellerData);
        modelAndView.addObject(Constants.ERROR, messageSource
            .getMessage(ActionErrorCode.ERROR_CODE_Z5, null, LocaleContextHolder.getLocale()));
      }
    } catch (Exception e) {
      logger.error("ERROR:: ResellerController:: updateReseller method", e);
      modelAndView.addObject(Constants.ERROR,
          messageSource.getMessage(Constants.CHATAK_GENERAL_ERROR, null, LocaleContextHolder.getLocale()));
    }
    logger.info("Exiting:: ResellerController:: updateReseller method");
    return modelAndView;
  }


  @RequestMapping(value = CHATAK_ADMIN_DELETE_RESELLER, method = RequestMethod.POST)
  public ModelAndView deleteReseller(HttpServletRequest request, HttpServletResponse response,
      @FormParam("getResellerId") final Long getResellerId, ResellerData resellerData,
      BindingResult bindingResult, HttpSession session, Map model) {
    logger.info("Entering :: MerchantController :: deleteReseller method ");
    ModelAndView modelAndView = new ModelAndView(CHATAK_ADMIN_SEARCH_RESELLER);
    String existingFeature = (String) session.getAttribute(Constants.EXISTING_FEATURES);
    if (!existingFeature.contains(FeatureConstants.ADMIN_SERVICE_RESELLER_DELETE_FEATURE_ID)) {
      session.invalidate();
      modelAndView.setViewName(INVALID_REQUEST_PAGE);
      return modelAndView;
    }
    modelAndView.addObject(Constants.ERROR, null);
    modelAndView.addObject(Constants.SUCESS, null);
    try {
      resellerData.setResellerId(getResellerId);
      DeleteResellerResponse deleteResellerResponse = resellerService.deleteReseller(getResellerId);
      if (deleteResellerResponse != null && deleteResellerResponse.isIsdeleated()) {
        modelAndView = showSearchResellerPage(request, response, model, session);
        modelAndView.addObject(Constants.SUCESS, messageSource
            .getMessage("chatak.reseller.delete.success", null, LocaleContextHolder.getLocale()));
      } else {
        modelAndView = showSearchResellerPage(request, response, model, session);
        modelAndView.addObject(Constants.ERROR, messageSource
            .getMessage("chatak.merchant.delete.failure", null, LocaleContextHolder.getLocale()));
      }
    } catch (Exception e) {
      modelAndView.addObject(Constants.ERROR, messageSource
          .getMessage("chatak.reseller.delete.failure", null, LocaleContextHolder.getLocale()));
      logger.error("ERROR:: ResellerController:: deleteMerchant method", e);
    }
    modelAndView.addObject(Constants.RESELLER_DATA, new ResellerData());
    logger.info("EXITING :: ResellerController :: deleteMerchant");
    return modelAndView;
  }

  @RequestMapping(value = CHATAK_ADMIN_RESELLER_PAGE_VIEW, method = RequestMethod.POST)
  public ModelAndView showViewReseller(HttpServletRequest request, HttpServletResponse response,
      @FormParam("resellerViewresellerId") final Long resellerViewresellerId, HttpSession session,
      Map model) {
    logger.info("Entering :: ResellerController :: showViewReseller method ");
    ModelAndView modelAndView = new ModelAndView(CHATAK_ADMIN_VIEW_RESELLERS_PAGE);
    String existingFeature = (String) session.getAttribute(Constants.EXISTING_FEATURES);
    if (!existingFeature.contains(FeatureConstants.ADMIN_SERVICE_RESELLER_VIEW_FEATURE_ID)) {
      session.invalidate();
      modelAndView.setViewName(INVALID_REQUEST_PAGE);
      return modelAndView;
    }
    ResellerData resellerData = new ResellerData();
    new DataBinder(resellerData);
    try {
      List<Option> countryList = merchantUpdateService.getCountries();
      modelAndView.addObject(Constants.COUNTRY_LIST, countryList);
      session.setAttribute(Constants.COUNTRY_LIST, countryList);
      resellerData.setResellerId(resellerViewresellerId);
      resellerData = resellerService.getReseller(resellerData);
      if (null == resellerData) {
        resellerData = new ResellerData();
      } else {
        Response stateList = merchantUpdateService.getStatesByCountry(resellerData.getCountry());
        modelAndView.addObject(Constants.STATE_LIST, stateList.getResponseList());
        session.setAttribute(Constants.STATE_LIST, stateList.getResponseList());
      }
    } catch (ChatakAdminException e) {
      modelAndView = showSearchResellerPage(request, response, model, session);
      modelAndView.addObject(Constants.ERROR,
          messageSource.getMessage(Constants.CHATAK_GENERAL_ERROR, null, LocaleContextHolder.getLocale()));
      modelAndView.addObject(Constants.RESELLER_DATA, new ResellerData());
      logger.error("ERROR:: ResellerController:: showViewReseller method", e);
    }
    model.put(Constants.RESELLER_DATA, resellerData);
    logger.info("EXITING :: ResellerController :: showViewReseller");
    return modelAndView;
  }

  @RequestMapping(value = CHATAK_ADMIN_RESELLER_PAGINATION, method = RequestMethod.POST)
  public ModelAndView getPaginationList(final HttpSession session,
      @FormParam("pageNumber") final Integer pageNumber,
      @FormParam("totalRecords") final Integer totalRecords, Map model) {
    logger.info("Entering:: ResellerController:: getPaginationList method");
    ModelAndView modelAndView = new ModelAndView(CHATAK_ADMIN_SEARCH_RESELLER);
    try {
      ResellerData resellerData = (ResellerData) session.getAttribute(Constants.RESELLER_MODEL);
      model.put(Constants.RESELLER_DATA, resellerData);
      resellerData.setPageIndex(pageNumber);
      resellerData.setNoOfRecords(totalRecords);
      modelAndView = setResellerSearchResponce(pageNumber, modelAndView, resellerData);
    } catch (Exception e) {
      logger.error("ERROR:: ResellerController:: getPaginationList method", e);
      modelAndView.addObject(Constants.ERROR,
          messageSource.getMessage(Constants.CHATAK_GENERAL_ERROR, null, LocaleContextHolder.getLocale()));
    }
    logger.info("Exiting:: ResellerController:: getPaginationList method");
    return modelAndView;
  }

  private ModelAndView setResellerSearchResponce(final Integer pageNumber,
      ModelAndView modelAndView, ResellerData resellerData) {
    try {
      ResellerSearchResponse resellerSearchResponse =
          resellerService.searchReseller(resellerData);
      List<ResellerValue> resellerValues;
      if (resellerSearchResponse != null
          && !CollectionUtils.isEmpty(resellerSearchResponse.getReseller())) {
        resellerValues = resellerSearchResponse.getReseller();
        modelAndView.addObject(Constants.PAGE_SIZE, resellerData.getPageSize());
        PaginationUtil.getPagenationModelSuccessive(modelAndView, pageNumber,
            resellerSearchResponse.getTotalNoOfRows());
        modelAndView.addObject("resellerValues", resellerValues);
      }

    } catch (Exception e) {
      modelAndView.addObject(Constants.ERROR, messageSource.getMessage(Constants.CHATAK_GENERAL_ERROR,
          null, LocaleContextHolder.getLocale()));
      logger.error("ERROR:: ResellerController:: searchReseller method", e);
    }
    return modelAndView;
  }

  @RequestMapping(value = CHATAK_ADMIN_RESELLER_REPORT, method = RequestMethod.POST)
  public ModelAndView downloadResellerReport(HttpSession session, Map model,
      HttpServletRequest request, @FormParam("downLoadPageNumber") final Integer downLoadPageNumber,
      HttpServletResponse response,
      @FormParam("totalRecords") final Integer totalRecords,
      @FormParam("downloadAllRecords") final boolean downloadAllRecords) {
    String downloadType=request.getParameter("downloadType");
    logger.info("Entering:: ResellerController:: downloadResellerReport method");
    ModelAndView modelAndView = new ModelAndView(CHATAK_ADMIN_SEARCH_RESELLER);
    ResellerData resellerData = null;
    try {
      resellerData = (ResellerData) session.getAttribute(Constants.RESELLER_MODEL);
      resellerData.setPageIndex(downLoadPageNumber);
      Integer pageSize = resellerData.getPageSize();
      if (downloadAllRecords) {
        resellerData.setPageIndex(Constants.ONE);
        resellerData.setPageSize(totalRecords);
      }
      ResellerSearchResponse resellerSearchResponse = resellerService.searchReseller(resellerData);
      List<ResellerValue> list = resellerSearchResponse.getReseller();
      ExportDetails exportDetails = new ExportDetails();
      if (StringUtil.isListNotNullNEmpty(list)) {
        if (Constants.PDF_FILE_FORMAT.equalsIgnoreCase(downloadType)) {
          exportDetails.setExportType(ExportType.PDF);
        } else if (Constants.XLS_FILE_FORMAT.equalsIgnoreCase(downloadType)) {
          exportDetails.setExportType(ExportType.XLS);
          exportDetails.setExcelStartRowNumber(Integer.parseInt("5"));
      }
      setExportDetailsDataForDownloadResellerReport(list, exportDetails); 
      ExportUtil.exportData(exportDetails, response, messageSource);
      }
      resellerData.setPageSize(pageSize);
    } catch (Exception e) {
      modelAndView.addObject(Constants.ERROR,
          messageSource.getMessage(Constants.CHATAK_GENERAL_ERROR, null, LocaleContextHolder.getLocale()));
      logger.error("ERROR:: ResellerController:: downloadResellerReport method", e);
    }
    logger.info("Exiting:: ResellerController:: downloadResellerReport method");
    return null;
  }
  
  private void setExportDetailsDataForDownloadResellerReport(List<ResellerValue> resellerValues,
      ExportDetails exportDetails) {
    exportDetails.setReportName("Reseller");
    exportDetails.setHeaderMessageProperty("chatak.header.reseller.messages");

    exportDetails.setHeaderList(getResellerHeaderList());
    exportDetails.setFileData(getResellerFileData(resellerValues));
  }

  private List<String> getResellerHeaderList() {
    String[] headerArr = {
        messageSource.getMessage("reseller-file-exportutil-resellerName", null,
            LocaleContextHolder.getLocale()),
        messageSource.getMessage("reseller-file-exportutil-AccountNumber", null,
            LocaleContextHolder.getLocale()),
        messageSource.getMessage("reseller-file-exportutil-accountBalance", null,
            LocaleContextHolder.getLocale()),
        messageSource.getMessage("reseller-file-exportutil-phone", null,
            LocaleContextHolder.getLocale()),
        messageSource.getMessage("reseller-file-exportutil-emailId", null,
            LocaleContextHolder.getLocale()),
        messageSource.getMessage("reseller-file-exportutil-city", null,
            LocaleContextHolder.getLocale()),
        messageSource.getMessage("reseller-file-exportutil-country", null,
            LocaleContextHolder.getLocale()),
        messageSource.getMessage("reseller-file-exportutil-status", null,
            LocaleContextHolder.getLocale())};
    return new ArrayList<String>(Arrays.asList(headerArr));
  }

  private static List<Object[]> getResellerFileData(List<ResellerValue> resellerValues) {
    List<Object[]> fileData = new ArrayList<Object[]>();

    for (ResellerValue resData : resellerValues) {

      Object[] rowData = {resData.getResellerName(), resData.getAccountNumber(),
          resData.getAccountBalance(), Long.parseLong(resData.getPhone()), resData.getEmailId(), resData.getCity(),
          resData.getCountry(), resData.getStatus()

      };
      fileData.add(rowData);
    }

    return fileData;
  }
}
