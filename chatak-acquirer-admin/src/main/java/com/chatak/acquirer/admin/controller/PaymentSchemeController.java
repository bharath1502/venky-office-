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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.chatak.acquirer.admin.constants.FeatureConstants;
import com.chatak.acquirer.admin.constants.URLMappingConstants;
import com.chatak.acquirer.admin.controller.model.ExportDetails;
import com.chatak.acquirer.admin.exception.ChatakAdminException;
import com.chatak.acquirer.admin.service.PaymentSchemeService;
import com.chatak.acquirer.admin.util.ExportUtil;
import com.chatak.acquirer.admin.util.JsonUtil;
import com.chatak.acquirer.admin.util.PaginationUtil;
import com.chatak.acquirer.admin.util.StringUtil;
import com.chatak.pg.bean.PaymentSchemeNameResponse;
import com.chatak.pg.bean.Response;
import com.chatak.pg.constants.ActionErrorCode;
import com.chatak.pg.constants.PGConstants;
import com.chatak.pg.enums.ExportType;
import com.chatak.pg.model.PaymentScheme;
import com.chatak.pg.user.bean.PaymentSchemeRequest;
import com.chatak.pg.user.bean.PaymentSchemeResponse;
import com.chatak.pg.util.Constants;

@SuppressWarnings({"unchecked", "rawtypes"})
@Controller
public class PaymentSchemeController implements URLMappingConstants {
  private static Logger logger = Logger.getLogger(PaymentSchemeController.class);

  @Autowired
  private PaymentSchemeService paymentSchemeService;

  @Autowired
  private MessageSource messageSource;

  @RequestMapping(value = CHATAK_ADMIN_PAYMENT_SCHEME_SHOW_CREATE_PAGE, method = RequestMethod.GET)
  public ModelAndView showCreatePaymentSchemePage(Map model, HttpSession session) {
    logger.info("Entering:: PaymentSchemeController:: showCreatePaymentScheme method");
    ModelAndView modelAndView = new ModelAndView(CHATAK_ADMIN_PAYMENT_SCHEME_SHOW_CREATE_PAGE);

    String existingFeature = (String) session.getAttribute(Constants.EXISTING_FEATURES);
    if (!existingFeature
        .contains(FeatureConstants.ADMIN_SERVICE_PAYMENT_SCHEME_CREATE_FEATURE_ID)) {
      session.invalidate();
      modelAndView.setViewName(INVALID_REQUEST_PAGE);
      return modelAndView;
    }

    modelAndView.addObject(Constants.ERROR, null);
    session.setAttribute(Constants.ERROR, null);
    PaymentScheme paymentSchemeInfo = new PaymentScheme();
    model.put(Constants.PAYMENT_SCHEME, paymentSchemeInfo);
    logger.info("Exiting:: PaymentSchemeController:: showCreatePaymentSchemePage method");
    return modelAndView;
  }

  @RequestMapping(value = CHATAK_ADMIN_PAYMENT_SCHEME_CREATE, method = RequestMethod.POST)
  public ModelAndView createPaymentScheme(HttpServletRequest request, HttpServletResponse response,
      PaymentScheme paymentScheme, BindingResult bindingResult, Map model, HttpSession session) {
    logger.info("Entering:: PaymentSchemeController:: createPaymentScheme method");
    ModelAndView modelAndView = new ModelAndView(CHATAK_ADMIN_PAYMENT_SCHEME_SHOW_CREATE_PAGE);

    String existingFeature = (String) session.getAttribute(Constants.EXISTING_FEATURES);
    if (!existingFeature
        .contains(FeatureConstants.ADMIN_SERVICE_PAYMENT_SCHEME_CREATE_FEATURE_ID)) {
      session.invalidate();
      modelAndView.setViewName(INVALID_REQUEST_PAGE);
      return modelAndView;
    }
    modelAndView.addObject(Constants.ERROR, null);
    PaymentSchemeRequest paymentSchemeRequest = new PaymentSchemeRequest();
    try {
      Long userid = (Long) session.getAttribute(Constants.LOGIN_USER_ID);

      PaymentSchemeResponse addPaymentSchemeResponse =
          paymentSchemeService.addPaymentSchemeInformation(paymentScheme, String.valueOf(userid));
      if (null != addPaymentSchemeResponse
          && addPaymentSchemeResponse.getErrorCode().equals(ActionErrorCode.ERROR_CODE_00)) {
        modelAndView = showPaymentSchemePage(request, response, paymentSchemeRequest, bindingResult,
            model, session);
        modelAndView.addObject(Constants.SUCESS, messageSource.getMessage(
            "chatak.paymentScheme.create.success", null, LocaleContextHolder.getLocale()));
      } else {
        modelAndView.addObject(Constants.ERROR, messageSource.getMessage(Constants.CHATAK_GENERAL_ERROR,
            null, LocaleContextHolder.getLocale()));
       
      }
    } catch (Exception e) {
      logger.error("ERROR:: PaymentSchemeController:: createPaymentScheme method", e);
      modelAndView.addObject(Constants.ERROR, messageSource
          .getMessage(ActionErrorCode.ERROR_CODE_Z5, null, LocaleContextHolder.getLocale()));
    }
    model.put(Constants.PAYMENT_SCHEME, new PaymentScheme());
    logger.info("Exiting:: PaymentSchemeController:: createPaymentScheme method");
    return modelAndView;
  }

  @RequestMapping(value = SHOW_CHATAK_PAYMENT_SCHEME_SEARCH, method = RequestMethod.GET)
  public ModelAndView showPaymentSchemePage(HttpServletRequest request,
      HttpServletResponse response, PaymentSchemeRequest paymentSchemeSearchData,
      BindingResult bindingResult, Map model, HttpSession session) {
    logger.info("Entering:: PaymentSchemeController:: showPaymentSchemeSearchPage method");
    ModelAndView modelAndView = new ModelAndView(CHATAK_PAYMENT_SCHEME_SEARCH_PAGE);
    String existingFeature = (String) session.getAttribute(Constants.EXISTING_FEATURES);
    if (!existingFeature.contains(FeatureConstants.ADMIN_SERVICE_PAYMENT_SCHEME_FEATURE_ID)) {
      session.invalidate();
      modelAndView.setViewName(INVALID_REQUEST_PAGE);
      return modelAndView;
    }
    session.setAttribute(Constants.PAYMENT_SCHEME_INFO, paymentSchemeSearchData);
    paymentSchemeSearchData.setPageSize(Constants.INITIAL_ENTITIES_PORTAL_DISPLAY_SIZE);
    paymentSchemeSearchData.setPageIndex(Constants.ONE);
    String succMsg = request.getParameter("succMsg");
    try {
      List<PaymentSchemeRequest> searchPaymentSchemaRequestList =
          new ArrayList<>();
      modelAndView.addObject(Constants.PAYMENT_SCHEME_INFO, searchPaymentSchemaRequestList);
      modelAndView.addObject(Constants.SUCESS, succMsg);
    } catch (Exception e) {
      modelAndView.addObject(Constants.ERROR,
          messageSource.getMessage(Constants.CHATAK_GENERAL_ERROR, null, LocaleContextHolder.getLocale()));
      logger.error("ERROR:: PaymentSchemeController:: showPaymentSchemeSearchPage method", e);
    }
    modelAndView.addObject("flag", false);
    modelAndView.addObject(Constants.PAYMENT_SCHEME, paymentSchemeSearchData);
    logger.info("Exiting:: PaymentSchemeController:: showPaymentSchemeSearchPage method");
    return modelAndView;
  }

  @RequestMapping(value = CHATAK_PAYMENT_SCHEME_SEARCH_ACTION, method = RequestMethod.POST)
  public ModelAndView searchPaymentSchemeAccount(HttpServletRequest request,
      HttpServletResponse response, PaymentSchemeRequest paymentSchemeDto,
      BindingResult bindingResult, Map model, HttpSession session) throws ChatakAdminException {
    logger.info("Entering:: PaymentSchemeController:: searchPaymentSchemeAccount method");
    ModelAndView modelAndView = new ModelAndView(CHATAK_PAYMENT_SCHEME_SEARCH_PAGE);

    String existingFeature = (String) session.getAttribute(Constants.EXISTING_FEATURES);
    if (!existingFeature.contains(FeatureConstants.ADMIN_SERVICE_PAYMENT_SCHEME_FEATURE_ID)) {
      session.invalidate();
      modelAndView.setViewName(INVALID_REQUEST_PAGE);
      return modelAndView;
    }
    paymentSchemeDto.setPageIndex(Constants.ONE);
    session.setAttribute(Constants.PAYMENT_SCHEME_INFO, paymentSchemeDto);
    session.setAttribute(Constants.PAGE_NUMBER, Constants.ONE);
    try {
      PaymentSchemeResponse paymentschemesearchResponse =
          paymentSchemeService.searchPaymentScheme(paymentSchemeDto);
      List<PaymentSchemeRequest> searchPaymentRequestList = new ArrayList<>();
      if (paymentschemesearchResponse != null
          && !CollectionUtils.isEmpty(paymentschemesearchResponse.getPaymentSchemesRequest())) {
        searchPaymentRequestList = paymentschemesearchResponse.getPaymentSchemesRequest();
        modelAndView.addObject(PGConstants.PAGE_SIZE, paymentSchemeDto.getPageSize());
        modelAndView.addObject(Constants.TOTAL_RECORDS, paymentSchemeDto.getNoOfRecords());
        modelAndView = PaginationUtil.getPagenationModel(modelAndView,
            paymentschemesearchResponse.getTotalNoOfRows());
      }
      modelAndView.addObject(Constants.PAYMENT_SCHEME_INFO, searchPaymentRequestList);
    } catch (Exception e) {
      modelAndView.addObject(Constants.ERROR,
          messageSource.getMessage(Constants.CHATAK_GENERAL_ERROR, null, LocaleContextHolder.getLocale()));
      logger.error("ERROR:: BlackListedCardController:: searchBlackListedCardInfo method", e);
    }
    modelAndView.addObject("flag", true);
    
    model.put(Constants.PAYMENT_SCHEME, paymentSchemeDto);
    logger.info("Exiting:: BlackListedCardController:: searchBlackListedCardInfo method");
    return modelAndView;
  }


  @RequestMapping(value = CHATAK_ADMIN_SHOW_EDIT_PAYMENT_SCHEME, method = RequestMethod.POST)
  public ModelAndView showEditPaymentSchemes(HttpServletRequest request,
      HttpServletResponse response, @FormParam("getpaymentschemeId") final Long getpaymentschemeId,
      HttpSession session, Map model) {
    logger.info("Entering :: PaymentSchemeController :: showEditMerchant method ");
    ModelAndView modelAndView = new ModelAndView(CHATAK_ADMIN_EDIT_PAYMENT_SCHEME);

    String existingFeature = (String) session.getAttribute(Constants.EXISTING_FEATURES);
    if (!existingFeature.contains(FeatureConstants.ADMIN_SERVICE_PAYMENT_SCHEME_EDIT_FEATURE_ID)) {
      session.invalidate();
      modelAndView.setViewName(INVALID_REQUEST_PAGE);
      return modelAndView;
    }
    setPaymentSchemeRequest(session, modelAndView);
    try {
      PaymentSchemeRequest paymentSchemesRequestInfo =
          paymentSchemeService.getpaymentSchemeyInfoId(getpaymentschemeId);
      model.put(Constants.PAYMENT_SCHEME, paymentSchemesRequestInfo);
    } catch (Exception exp) {
      logger.error("ERROR:: PaymentSchemesController:: showEditPaymentSchemes method", exp);
      modelAndView.addObject(Constants.ERROR,
          messageSource.getMessage(Constants.CHATAK_GENERAL_ERROR, null, LocaleContextHolder.getLocale()));
    }
    logger.info("Exiting :: PaymentSchemesController :: showEditPaymentSchemes");
    return modelAndView;
  }

  private void setPaymentSchemeRequest(HttpSession session, ModelAndView modelAndView) {
	modelAndView.addObject(Constants.ERROR, null);
    session.setAttribute(Constants.ERROR, null);
    PaymentSchemeRequest paymentSchemesRequest = new PaymentSchemeRequest();
    new DataBinder(paymentSchemesRequest);
    modelAndView.addObject(Constants.ERROR, null);
    modelAndView.addObject(Constants.SUCESS, null);
  }

  @RequestMapping(value = CHATAK_ADMIN_VIEW_PAYMENT_SCHEME, method = RequestMethod.POST)
  public ModelAndView showViewPaymentSchemes(HttpServletRequest request,
      HttpServletResponse response, @FormParam("getpaymentschemeId") final Long getpaymentschemeId,
      HttpSession session, Map model) {
    logger.info("Entering :: PaymentSchemeController :: showEditMerchant method ");
    ModelAndView modelAndView = new ModelAndView(CHATAK_ADMIN_VIEW_PAYMENT_SCHEME);

    String existingFeature = (String) session.getAttribute(Constants.EXISTING_FEATURES);
    if (!existingFeature.contains(FeatureConstants.ADMIN_SERVICE_PAYMENT_SCHEME_EDIT_FEATURE_ID)) {
      session.invalidate();
      modelAndView.setViewName(INVALID_REQUEST_PAGE);
      return modelAndView;
    }
    setPaymentSchemeRequest(session, modelAndView);
    try {
      PaymentSchemeRequest paymentSchemesRequestInfo =
          paymentSchemeService.getpaymentSchemeyInfoId(getpaymentschemeId);
      model.put(Constants.PAYMENT_SCHEME, paymentSchemesRequestInfo);
    } catch (Exception e) {
      logger.error("ERROR:: PaymentSchemesController:: showEditPaymentSchemes method", e);
      modelAndView.addObject(Constants.ERROR,
          messageSource.getMessage(Constants.CHATAK_GENERAL_ERROR, null, LocaleContextHolder.getLocale()));
    }
    logger.info("Exiting :: PaymentSchemesController :: showEditPaymentSchemes");
    return modelAndView;
  }

  @RequestMapping(value = CHATAK_ADMIN_UPDATE_PAYMENT_SCHEME, method = RequestMethod.POST)
  public ModelAndView updatePaymentScheme(HttpServletRequest request, HttpServletResponse response,
      PaymentSchemeRequest updatePaymentSchemeRequest, BindingResult bindingResult,
      HttpSession session, Map model) {
    logger.info("Entering ::  PaymentSchemesController :: updatePaymentScheme method");
    ModelAndView modelAndView = new ModelAndView(CHATAK_ADMIN_EDIT_PAYMENT_SCHEME);

    String existingFeature = (String) session.getAttribute(Constants.EXISTING_FEATURES);
    if (!existingFeature.contains(FeatureConstants.ADMIN_SERVICE_PAYMENT_SCHEME_EDIT_FEATURE_ID)) {
      session.invalidate();
      modelAndView.setViewName(INVALID_REQUEST_PAGE);
      return modelAndView;
    }
    modelAndView.addObject(Constants.ERROR, null);
    session.setAttribute(Constants.ERROR, null);
    updatePaymentSchemeRequest.setPageSize(Constants.MAX_ENTITIES_PORTAL_DISPLAY_SIZE);
    updatePaymentSchemeRequest.setPageIndex(Constants.ONE);
    try {
      Long userid = (Long) session.getAttribute(Constants.LOGIN_USER_ID);
      PaymentSchemeResponse updatePaymentSchemeResponse = paymentSchemeService
          .updatePaymentSchemeInformation(updatePaymentSchemeRequest, String.valueOf(userid));
      if (updatePaymentSchemeResponse != null
          && updatePaymentSchemeResponse.getErrorCode().equals(ActionErrorCode.ERROR_CODE_00)) {
        modelAndView = showPaymentSchemePage(request, response, new PaymentSchemeRequest(),
            bindingResult, model, session);
        modelAndView.addObject(Constants.SUCESS,
            messageSource.getMessage("chatak.admin.updatepaymentscheme.success.message", null,
                LocaleContextHolder.getLocale()));
      } else {
        modelAndView.addObject(Constants.PAYMENT_SCHEME, updatePaymentSchemeRequest);
        modelAndView.addObject(Constants.ERROR, messageSource
            .getMessage(ActionErrorCode.ERROR_CODE_Z5, null, LocaleContextHolder.getLocale()));
       
      }
    } catch (ChatakAdminException e) {
      logger.error("ERROR::  PaymentSchemesController:: updatePaymentScheme method", e);
      modelAndView.addObject(Constants.ERROR,
          messageSource.getMessage(Constants.CHATAK_GENERAL_ERROR, null, LocaleContextHolder.getLocale()));
    }
    logger.info("Exiting::  PaymentSchemesController:: updatePaymentScheme method");
    return modelAndView;

  }

  // DUPLICATE EMAIL-ID // 

  @RequestMapping(value = CHATAK_ADMIN_UNIQUE_PAYMENT_SCHEME_EMAIL_ID, method = RequestMethod.GET)
  public @ResponseBody String validateUniqueEmailId(Map model, HttpServletRequest request,
      HttpServletResponse response, HttpSession session) {
    ModelAndView modelAndView = new ModelAndView(CHATAK_ADMIN_PAYMENT_SCHEME_SHOW_CREATE_PAGE);
    modelAndView.addObject(Constants.ERROR, null);
    logger.info("Entering :: PaymentSchemeController :: validateUniqueEmailId method");
    String contactEmail = request.getParameter("contactEmail");
    Response response2 = null;
    try {
      response2 = paymentSchemeService.validateEmailId(contactEmail);
      return JsonUtil.convertObjectToJSON(response2);
    } catch (ChatakAdminException e) {
      response2 = new Response();
      response2.setErrorCode(ActionErrorCode.ERROR_CODE_Z5);
      response2
          .setErrorMessage(ActionErrorCode.getInstance().getMessage(ActionErrorCode.ERROR_CODE_Z5));
      logger.error("ERROR:: PaymentSchemeController:: validateUniqueEmailId method", e);
      modelAndView.addObject(Constants.ERROR,
          messageSource.getMessage(Constants.CHATAK_GENERAL_ERROR, null, LocaleContextHolder.getLocale()));
      try {
        return JsonUtil.convertObjectToJSON(response2);
      } catch (ChatakAdminException e1) {
        logger.error(
            "ERROR:: PaymentSchemeController:: validateUniqueEmailId method::convertObjectToJSON",
            e1);
      }
    } catch (Exception e) {
      response2 = new Response();
      response2.setErrorCode(ActionErrorCode.ERROR_CODE_Z5);
      response2
          .setErrorMessage(ActionErrorCode.getInstance().getMessage(ActionErrorCode.ERROR_CODE_Z5));
      logger.error("ERROR:: PaymentSchemeController:: validateUniqueEmailId method", e);
      modelAndView.addObject(Constants.ERROR,
          messageSource.getMessage(Constants.CHATAK_GENERAL_ERROR, null, LocaleContextHolder.getLocale()));
      try {
        return JsonUtil.convertObjectToJSON(response2);
      } catch (ChatakAdminException e1) {
        logger.error(
            "ERROR:: PaymentSchemeController:: validateUniqueEmailId method ::convertObjectToJSON",
            e1);
      }
    }
    logger.info("Exiting:: PaymentSchemeController:: validateUniqueEmailId method");
    return null;
  }

  @RequestMapping(value = CHATAK_ADMIN_PAYMENT_SCHEME_ACTIVATION_SUSPENTION,
      method = RequestMethod.POST)
  public ModelAndView changePaymentSchemeStatus(final HttpSession session,
      @FormParam("suspendActiveId") final Long paymentSchemeId,
      @FormParam("suspendActiveStatus") final String paymentSchemeStatus,
      @FormParam("reason") final String reason, Map<String, Serializable> model,
      HttpServletRequest request, HttpServletResponse response) {
    logger.info("Entering:: PaymentSchemeController:: changePaymentSchemeStatus method");

    ModelAndView modelAndView = new ModelAndView(CHATAK_PAYMENT_SCHEME_SEARCH_PAGE);
    String existingFeature = (String) session.getAttribute(Constants.EXISTING_FEATURES);
    if (!existingFeature.contains(FeatureConstants.ADMIN_SERVICE_PAYMENT_SCHEME_FEATURE_ID)) {
      session.invalidate();
      modelAndView.setViewName(INVALID_REQUEST_PAGE);
      return modelAndView;
    }
    try {
      PaymentSchemeRequest paymentSchemeRequest = new PaymentSchemeRequest();
      paymentSchemeRequest.setId(paymentSchemeId);
      paymentSchemeRequest.setReason(reason);
      PaymentSchemeResponse paymentSchemeResponse =
          paymentSchemeService.changePaymentSchemeStatus(paymentSchemeRequest, paymentSchemeStatus);
      if (ActionErrorCode.ERROR_CODE_00.equals(paymentSchemeResponse.getErrorCode())) {
        model.put(Constants.SUCESS,
            messageSource.getMessage("chatak.admin.updatepaymentscheme.status.sucess", null,
                LocaleContextHolder.getLocale()));
      } else {
        model.put(Constants.ERROR, messageSource.getMessage(Constants.CHATAK_GENERAL_ERROR, null,
            LocaleContextHolder.getLocale()));
      }
      modelAndView = getPaginationList(session,
          StringUtil.isNull((Integer) session.getAttribute(Constants.PAGE_NUMBER)) ? 1
              : (Integer) session.getAttribute(Constants.PAGE_NUMBER),
          (Integer) session.getAttribute(Constants.TOTAL_RECORDS), model);
    } catch (Exception e) {
      logger.error("ERROR:: PaymentSchemeController:: changePaymentSchemeStatus method", e);
      model.put(Constants.ERROR,
          messageSource.getMessage(Constants.CHATAK_GENERAL_ERROR, null, LocaleContextHolder.getLocale()));
    }
    logger.info("Exiting:: PaymentSchemeController:: changePaymentSchemeStatus method");
    return modelAndView;
  }

  @RequestMapping(value = CHATAK_ADMIN_PAGINATION_PAYMENT_SCHEME, method = RequestMethod.POST)
  public ModelAndView getPaginationList(final HttpSession session,
      @FormParam(Constants.PAGE_NUMBER) final Integer pageNumber,
      @FormParam(Constants.TOTAL_RECORDS) final Integer totalRecords, Map model) {
    logger.info("Entering:: PaymentSchemeController:: getPaginationList method");
    ModelAndView modelAndView = new ModelAndView(CHATAK_PAYMENT_SCHEME_SEARCH_PAGE);
    return paymentSchemeData(session, pageNumber, totalRecords, model, modelAndView);
  }

  private ModelAndView paymentSchemeData(final HttpSession session, final Integer pageNumber,
      final Integer totalRecords, Map model, ModelAndView modelAndView) {
    merchantSchemeData(session, pageNumber, totalRecords, model, modelAndView);
    logger.info("Exiting:: PaymentSchemeController:: getPaginationList method");
    return modelAndView;
  }

  public void merchantSchemeData(final HttpSession session, final Integer pageNumber,
      final Integer totalRecords, Map model, ModelAndView modelAndView) {
    try {
      PaymentSchemeRequest paymentSchemeSearchData =
          (PaymentSchemeRequest) session.getAttribute(Constants.PAYMENT_SCHEME_INFO);
      model.put(Constants.PAYMENT_SCHEME, paymentSchemeSearchData);
      paymentSchemeSearchData.setPageIndex(pageNumber);
      paymentSchemeSearchData.setNoOfRecords(totalRecords);
      merchantData(session, pageNumber, totalRecords, modelAndView, paymentSchemeSearchData);
    } catch (Exception e) {
      logger.error("ERROR:: PaymentSchemeController:: getPaginationList method", e);
      modelAndView.addObject(Constants.ERROR,
          messageSource.getMessage(Constants.CHATAK_GENERAL_ERROR, null, LocaleContextHolder.getLocale()));
    }
  }

  public void merchantData(final HttpSession session, final Integer pageNumber,
      final Integer totalRecords, ModelAndView modelAndView,
      PaymentSchemeRequest paymentSchemeSearchData) {
    try {
      PaymentSchemeResponse paymentSchemeResponse =
          paymentSchemeService.searchPaymentScheme(paymentSchemeSearchData);
      List<PaymentSchemeRequest> paymentShemeSearchList = new ArrayList<>();
      if (paymentSchemeResponse != null
          && !CollectionUtils.isEmpty(paymentSchemeResponse.getPaymentSchemesRequest())) {
        paymentShemeSearchList = paymentSchemeResponse.getPaymentSchemesRequest();
        modelAndView.addObject(PGConstants.PAGE_SIZE, paymentSchemeSearchData.getPageSize());
        PaginationUtil.getPagenationModelSuccessive(modelAndView, pageNumber,
            paymentSchemeResponse.getTotalNoOfRows());
        session.setAttribute(Constants.PAGE_NUMBER, pageNumber);
        session.setAttribute(Constants.TOTAL_RECORDS, totalRecords);
      }
      modelAndView.addObject(Constants.PAYMENT_SCHEME_INFO, paymentShemeSearchList);
    } catch (Exception e) {
      modelAndView.addObject(Constants.ERROR, messageSource.getMessage(Constants.CHATAK_GENERAL_ERROR,
          null, LocaleContextHolder.getLocale()));
      logger.error("ERROR:: PaymentSchemeController:: getPaginationList method", e);
    }
  }

  @RequestMapping(value = CHATAK_ADMIN_REPORT_PAYMENT_SCHEME, method = RequestMethod.POST)
  public ModelAndView downloadPaymentShemeReport(HttpSession session, Map model,
      HttpServletRequest request, @FormParam("downLoadPageNumber") final Integer downLoadPageNumber,
      HttpServletResponse response,@FormParam(Constants.TOTAL_RECORDS) final Integer totalRecords,
      @FormParam("downloadAllRecords") final boolean downloadAllRecords) {
    String downloadType=request.getParameter("downloadType");
    logger.info("Entering:: PaymentSchemeController:: downloadPaymentShemeReport method");
    ModelAndView modelAndView = new ModelAndView(CHATAK_PAYMENT_SCHEME_SEARCH_PAGE);
    try {
      PaymentSchemeRequest paymentSchemeSearchData = null;
      PaymentSchemeResponse paymentSchemeResponse = null;
      paymentSchemeSearchData =
          (PaymentSchemeRequest) session.getAttribute(Constants.PAYMENT_SCHEME_INFO);
      paymentSchemeSearchData.setPageIndex(downLoadPageNumber);
      Integer pageSize = paymentSchemeSearchData.getPageSize();

      if (downloadAllRecords) {
        paymentSchemeSearchData.setPageIndex(Constants.ONE);
        paymentSchemeSearchData.setPageSize(totalRecords);
      }
      paymentSchemeResponse = paymentSchemeService.searchPaymentScheme(paymentSchemeSearchData);
      ExportDetails exportDetails = new ExportDetails();

      if (null != paymentSchemeResponse
          && StringUtil.isListNotNullNEmpty(paymentSchemeResponse.getPaymentSchemesRequest())) {
        List<PaymentSchemeRequest> list = paymentSchemeResponse.getPaymentSchemesRequest();
        if (Constants.PDF_FILE_FORMAT.equalsIgnoreCase(downloadType)) {
        	exportDetails.setExportType(ExportType.PDF);
        } else if (Constants.XLS_FILE_FORMAT.equalsIgnoreCase(downloadType)) {
        	exportDetails.setExportType(ExportType.XLS);
			exportDetails.setExcelStartRowNumber(Integer.parseInt("5"));
        }
        setExportDetailsDataForDownloadPaymentReport(list, exportDetails);	
	 	ExportUtil.exportData(exportDetails, response, messageSource);
      }
      paymentSchemeSearchData.setPageSize(pageSize);
    } catch (Exception e) {
      modelAndView.addObject(Constants.ERROR,
          messageSource.getMessage(Constants.CHATAK_GENERAL_ERROR, null, LocaleContextHolder.getLocale()));
      logger.error("ERROR:: PaymentSchemeController:: downloadPaymentShemeReport method", e);
    }
    logger.info("Exiting:: PaymentSchemeController:: downloadPaymentShemeReport method");
    return null;
  }
  
  private void setExportDetailsDataForDownloadPaymentReport(
      List<PaymentSchemeRequest> paymentSchemeRequest, ExportDetails exportDetails) {
    exportDetails.setReportName("PaymentScheme_");
    exportDetails.setHeaderMessageProperty("payment.label.paymentscheme");

    exportDetails.setHeaderList(getRoleHeaderList());
    exportDetails.setFileData(getRoleFileData(paymentSchemeRequest));
  }


  @RequestMapping(value = CHATAK_ADMIN_PAYMENT_SCHEME_VALIDATE, method = RequestMethod.GET)
  public @ResponseBody String validatepaymentSchemeName(Map model, HttpServletRequest request,
      HttpServletResponse response, HttpSession session) {
    ModelAndView modelAndView = new ModelAndView(CHATAK_ADMIN_PAYMENT_SCHEME_SHOW_CREATE_PAGE);
    modelAndView.addObject(Constants.ERROR, null);
    logger.info("Entering :: PaymentSchemeController :: validatepaymentSchemeName method");
    String paymentScheme = request.getParameter("paymentSchemeId");
    PaymentSchemeNameResponse paymentSchemeNameResponse = null;
    try {
      paymentSchemeNameResponse = paymentSchemeService.validatePaymentSchemeName(paymentScheme);
      return JsonUtil.convertObjectToJSON(paymentSchemeNameResponse);
    } catch (Exception e) {
      paymentSchemeNameResponse = new PaymentSchemeNameResponse();
      paymentSchemeNameResponse.setErrorCode(ActionErrorCode.ERROR_CODE_Z5);
      paymentSchemeNameResponse
          .setErrorMessage(ActionErrorCode.getInstance().getMessage(ActionErrorCode.ERROR_CODE_Z5));
      logger.error("ERROR:: PaymentSchemeController:: validatepaymentSchemeName method", e);
      modelAndView.addObject(Constants.ERROR,
          messageSource.getMessage(Constants.CHATAK_GENERAL_ERROR, null, LocaleContextHolder.getLocale()));
      try {
        return JsonUtil.convertObjectToJSON(paymentSchemeNameResponse);
      } catch (ChatakAdminException e1) {
        logger.error(
            "ERROR :: PaymentSchemeController :: validatepaymentSchemeName :: convertObjectToJSON",
            e1);
      }
    }
    logger.info("Exiting:: PaymentSchemeController:: validatepaymentSchemeName method");
    return null;
  }
  
  private List<String> getRoleHeaderList() {
    String[] headerArr = {
        messageSource.getMessage("payment.label.paymentname", null,
            LocaleContextHolder.getLocale()),
        messageSource.getMessage("payment.label.contactname", null,
            LocaleContextHolder.getLocale()),
        messageSource.getMessage("payment.label.contactemail", null,
            LocaleContextHolder.getLocale()),
        messageSource.getMessage("payment.label.contactphone", null,
            LocaleContextHolder.getLocale()),
        messageSource.getMessage("payment.label.rid", null, LocaleContextHolder.getLocale()),
        messageSource.getMessage("payment.label.typeofcard", null, LocaleContextHolder.getLocale()),
        messageSource.getMessage("common.label.status", null, LocaleContextHolder.getLocale())};
    return new ArrayList<String>(Arrays.asList(headerArr));
  }

  private static List<Object[]> getRoleFileData(List<PaymentSchemeRequest> paymentSchemeRequest) {
    List<Object[]> fileData = new ArrayList<Object[]>();

    for (PaymentSchemeRequest psData : paymentSchemeRequest) {

      Object[] rowData = {psData.getPaymentSchemeName(), psData.getContactName(),
          psData.getContactEmail(), Long.parseLong(psData.getContactPhone()), psData.getRid(),
          psData.getTypeOfCard(), psData.getStatus().intValue() == 0 ? "Active" : "InActive"

      };
      fileData.add(rowData);
    }

    return fileData;
  }

}


