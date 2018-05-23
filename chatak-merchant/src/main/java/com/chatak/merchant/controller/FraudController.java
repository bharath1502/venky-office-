package com.chatak.merchant.controller;

import java.io.IOException;
import java.sql.Timestamp;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.chatak.merchant.constants.FeatureConstants;
import com.chatak.merchant.constants.URLMappingConstants;
import com.chatak.merchant.controller.model.LoginResponse;
import com.chatak.merchant.controller.model.Option;
import com.chatak.merchant.exception.ChatakMerchantException;
import com.chatak.merchant.model.GetFraudDetailsResponse;
import com.chatak.merchant.service.FraudService;
import com.chatak.pg.bean.Response;
import com.chatak.pg.dao.util.StringUtil;
import com.chatak.pg.model.AdvancedFraudDTO;
import com.chatak.pg.model.FraudBasicDTO;
import com.chatak.pg.util.Constants;

@Controller
public class FraudController implements URLMappingConstants {

  private static Logger logger = Logger.getLogger(FraudController.class);

  @Autowired
  FraudService fraudService;

  @Autowired
  private MessageSource messageSource;

  /**
   * Method get the Fraud Basic page
   * 
   * @param request
   * @param response
   * @param session
   * @param model
   * @return
   */
  @RequestMapping(value = CHATAK_MERCHANT_FRAUD_BASIC_PAGE, method = RequestMethod.GET)
  public ModelAndView showFraudBasic(HttpServletRequest request, HttpServletResponse response,
      HttpSession session, Map<String, List<String>> model) {

    logger.info("Entering :: FraudController :: showFraudBasic method");
    ModelAndView modelAndView = new ModelAndView(CHATAK_MERCHANT_FRAUD_BASIC_PAGE);
    String existingFeature = (String) session.getAttribute(Constants.EXISTING_FEATURES);
    if (!existingFeature.contains(FeatureConstants.MERCHANT_SERVICE_FRAUD_BASIC_FEATURE_ID)) {
      return validateHeader(session, modelAndView);
    }

    session.setAttribute(Constants.ERROR, null);
    session.setAttribute(Constants.SUCESS, null);
    GetFraudDetailsResponse fraudDetailsResponse = new GetFraudDetailsResponse();
    try {
      List<Option> isoCountryList = fraudService.getISOCountries();

      modelAndView.addObject("isoCountryList", isoCountryList);
      session.setAttribute("isoCountryList", isoCountryList);

      Long userid = (Long) session.getAttribute(Constants.LOGIN_USER_MERCHANT_ID);
      fraudDetailsResponse = fraudService.getFraudDetails(userid);

      if (fraudDetailsResponse != null
          && fraudDetailsResponse.getErrorCode().equals(Constants.SUCCESS_CODE)) {

        model.put("iPMultipleList", fraudDetailsResponse.getiPMultiple() != null
            ? fraudDetailsResponse.getiPMultiple() : null);
        model.put("binMultipleList", fraudDetailsResponse.getBinMultiple() != null
            ? fraudDetailsResponse.getBinMultiple() : null);
        model.put("eMailMultipleList", fraudDetailsResponse.geteMailMultiple() != null
            ? fraudDetailsResponse.geteMailMultiple() : null);
        model.put("countryMultipleList", fraudDetailsResponse.getCountryMultiple() != null
            ? fraudDetailsResponse.getCountryMultiple() : null);

      }
    } catch (ChatakMerchantException e) {
      logger.error("ERROR:: FraudController:: showFraudBasic method", e);
      modelAndView.addObject(Constants.ERROR, messageSource
          .getMessage(Constants.CHATAK_GENERAL_ERROR, null, LocaleContextHolder.getLocale()));
    }
    modelAndView.addObject(Constants.FRAUD_DTO, new FraudBasicDTO());
    logger.info("Exiting :: FraudController :: showFraudBasic method");
    return modelAndView;

  }

  @RequestMapping(value = CHATAK_MERCHANT_FRAUD_BASIC_CREATE, method = RequestMethod.POST)
  public ModelAndView createFraudBasic(HttpServletRequest request, HttpServletResponse response,
      HttpSession session, FraudBasicDTO fraudBasicDTO, BindingResult bindingResult,
      Map<String, List<String>> model) {

    logger.info("Entering :: FraudController :: createFraudBasic method");
    ModelAndView modelAndView = new ModelAndView(CHATAK_MERCHANT_FRAUD_BASIC_PAGE);
    String existingFeature = (String) session.getAttribute(Constants.EXISTING_FEATURES);

    if (request.getHeader(Constants.REFERER) == null) {
      return validateHeader(session, modelAndView);
    }

    if (!existingFeature.contains(FeatureConstants.MERCHANT_SERVICE_FRAUD_BASIC_FEATURE_ID)) {
      return validateHeader(session, modelAndView);
    }
    session.setAttribute(Constants.ERROR, null);
    session.setAttribute(Constants.SUCESS, null);
    Long userid = (Long) session.getAttribute(Constants.LOGIN_USER_MERCHANT_ID);
    if (null != userid) {

      try {

        fraudBasicDTO.setMerchantID(userid);
        Response response2 = fraudService.createFraudBasic(fraudBasicDTO);
        if (null != response2 && response2.getErrorCode().equals(Constants.SUCCESS_CODE)) {
          modelAndView = showFraudBasic(request, response, session, model);
          modelAndView.addObject(Constants.SUCESS, messageSource.getMessage(
              "chatak.fraudbasic.create.success", null, LocaleContextHolder.getLocale()));

        } else {
          GetFraudDetailsResponse fraudDetailsResponse = fraudService.getFraudDetails(userid);

          processFraudResponse(model, modelAndView, fraudDetailsResponse);
        }
      } catch (ChatakMerchantException e) {

        logger.error("ERROR:: FraudController:: createFraudBasic method", e);

        modelAndView = new ModelAndView(CHATAK_MERCHANT_FRAUD_BASIC_PAGE);
      }
      logger.info("Exiting :: FraudController :: createFraudBasic method");
      return modelAndView;
    } else {
      modelAndView.addObject(Constants.ERROR, "invalid session,retry after login");
      modelAndView.addObject(Constants.FRAUD_DTO, new FraudBasicDTO());
      try {
        response.sendRedirect("/chatak-merchant//session-invalid.jsp");
      } catch (IOException e) {

        logger.error("ERROR:: FraudController:: createFraudBasic method", e);
      }
      return modelAndView;
    }

  }

  private void processFraudResponse(Map<String, List<String>> model, ModelAndView modelAndView,
      GetFraudDetailsResponse fraudDetailsResponse) {
    if (fraudDetailsResponse != null) {
      populateFraudResponse(model, modelAndView, fraudDetailsResponse);
    }
  }

private void populateFraudResponse(Map<String, List<String>> model, ModelAndView modelAndView,
		GetFraudDetailsResponse fraudDetailsResponse) {
	model.put("iPMultipleList", fraudDetailsResponse.getiPMultiple() != null
	    ? fraudDetailsResponse.getiPMultiple() : null);
	model.put("binMultipleList", fraudDetailsResponse.getBinMultiple() != null
	    ? fraudDetailsResponse.getBinMultiple() : null);
	model.put("eMailMultipleList", fraudDetailsResponse.geteMailMultiple() != null
	    ? fraudDetailsResponse.geteMailMultiple() : null);
	model.put("countryMultipleList", fraudDetailsResponse.getCountryMultiple() != null
	    ? fraudDetailsResponse.getCountryMultiple() : null);
	modelAndView.addObject(Constants.FRAUD_DTO, new FraudBasicDTO());
	modelAndView.addObject(Constants.ERROR, messageSource.getMessage(
	    "chatak.fraudbasic.create.failure", null, LocaleContextHolder.getLocale()));
	
}

  /**
   * Method to show Advanced Fraud information
   * 
   * @param request
   * @param response
   * @param session
   * @param model
   * @param advancedFraudDTO
   * @return
   */

  @RequestMapping(value = CHATAK_MERCHANT_FRAUD_ADVANCED_PAGE, method = RequestMethod.GET)
  public ModelAndView showAdvancedFraud(HttpServletRequest request, HttpServletResponse response,
      HttpSession session, AdvancedFraudDTO advancedFraudDTO, Map<String, Object> model) {

    logger.info("Entering :: FraudController :: showAdvancedFraud method");
    ModelAndView modelAndView = new ModelAndView(CHATAK_MERCHANT_FRAUD_ADVANCED_PAGE);
    String existingFeature = (String) session.getAttribute(Constants.EXISTING_FEATURES);
    if (!existingFeature.contains(FeatureConstants.MERCHANT_SERVICE_FRAUD_ADVANCED_FEATURE_ID)) {
      return validateHeader(session, modelAndView);
    }
    session.setAttribute(Constants.ERROR, null);
    session.setAttribute(Constants.SUCESS, null);
    try {
      LoginResponse loginResponse = (LoginResponse) session.getAttribute(Constants.LOGIN_RESPONSE);
      advancedFraudDTO.setCreatedBy(String.valueOf(loginResponse.getUserId()));
      advancedFraudDTO.setId(Long.valueOf(loginResponse.getUserId()));

      advancedFraudDTO.setId(advancedFraudDTO.getId());
      advancedFraudDTO.setPageIndex(Constants.ONE);
      advancedFraudDTO.setPageSize(Constants.MAX_ENTITIES_PORTAL_DISPLAY_SIZE);
      List<AdvancedFraudDTO> advancedFraudList =
          fraudService.searchAdvancedFraudByCreatedBy(advancedFraudDTO);
      model.put(Constants.ADVANCED_FRAUD_LIST, advancedFraudList);
      model.put(Constants.ADVANCED_FRAUD_DTO, advancedFraudDTO);
    } catch (Exception e) {
      logger.error("ERROR:: FraudController:: createAdvancedFraud method", e);
      modelAndView.addObject(Constants.ERROR,
          messageSource.getMessage("chatak.general.error", null, LocaleContextHolder.getLocale()));
    }
    modelAndView.addObject(Constants.ADVANCED_FRAUD_DTO, new AdvancedFraudDTO());
    logger.info("Exiting :: FraudController :: showAdvancedFraud method");
    return modelAndView;

  }

  /**
   * Method to add the new Advanced Fraud information
   * 
   * @param request
   * @param response
   * @param session
   * @param model
   * @param advancedFraudDTO
   * @return
   */
  @RequestMapping(value = CHATAK_MERCHANT_ADD_NEW_ADVANCED_FRAUD, method = RequestMethod.POST)
  public ModelAndView addNewAdvancedFraud(HttpServletRequest request, HttpServletResponse response,
      HttpSession session, Map<String, Object> model, AdvancedFraudDTO advancedFraudDTO) {
    logger.info("Entering :: FraudController :: addNewAdvancedFraud method");
    ModelAndView modelAndView = new ModelAndView(CHATAK_MERCHANT_FRAUD_ADVANCED_PAGE);
    String existingFeature = (String) session.getAttribute(Constants.EXISTING_FEATURES);

    if (request.getHeader(Constants.REFERER) == null) {
      return validateHeader(session, modelAndView);
    }

    if (!existingFeature.contains(FeatureConstants.MERCHANT_SERVICE_FRAUD_ADVANCED_FEATURE_ID)) {
      return validateHeader(session, modelAndView);
    }
    try {
      LoginResponse loginResponse = (LoginResponse) session.getAttribute(Constants.LOGIN_RESPONSE);
      advancedFraudDTO.setCreatedBy(String.valueOf(loginResponse.getUserId()));
      advancedFraudDTO.setId(Long.valueOf(loginResponse.getUserId()));
      advancedFraudDTO.setCreatedDate(new Timestamp(System.currentTimeMillis()));
      advancedFraudDTO = fraudService.createAdvancedFraud(advancedFraudDTO);

      if (advancedFraudDTO.getErrorCode().equals(Constants.SUCCESS_CODE)) {
        model.put(Constants.SUCCESS, messageSource.getMessage("chatak.advancedfraud.create.success",
            null, LocaleContextHolder.getLocale()));
      } else {
        model.put(Constants.ERROR, messageSource.getMessage("chatak.advancedfraud.create.failure",
            null, LocaleContextHolder.getLocale()));
      }

      advancedFraudDTO.setId(advancedFraudDTO.getId());
      advancedFraudDTO.setPageIndex(Constants.ONE);
      advancedFraudDTO.setPageSize(Constants.MAX_ENTITIES_PORTAL_DISPLAY_SIZE);
      List<AdvancedFraudDTO> advancedFraudList =
          fraudService.searchAdvancedFraudByCreatedBy(advancedFraudDTO);
      model.put(Constants.ADVANCED_FRAUD_LIST, advancedFraudList);
      model.put(Constants.ADVANCED_FRAUD_DTO, advancedFraudDTO);
    } catch (Exception e) {
      model.put(Constants.ERROR,
          messageSource.getMessage("chatak.header.recurring.paymentinfo.update.failure.message",
              null, LocaleContextHolder.getLocale()));
      model.put(Constants.ADVANCED_FRAUD_DTO, advancedFraudDTO);
      logger.error("ERROR :: FraudController :: addNewAdvancedFraud method",e);
    }
    logger.info("Exiting :: FraudController :: addNewAdvancedFraud method");
    return modelAndView;
  }

  private ModelAndView validateHeader(HttpSession session, ModelAndView modelAndView) {
	  session.invalidate();
      modelAndView.setViewName(INVALID_REQUEST_PAGE);
      return modelAndView;
  }

  /**
   * Method used to show Edit the Advanced Fraud info 
   * 
   * @param request
   * @param response
   * @param session
   * @param model
   * @param getId
   * @param getMerchantCode
   * @return
   */
  @RequestMapping(value = CHATAK_MERCHANT_SHOW_ADVANCED_FRAUD_EDIT_PAGE,
      method = RequestMethod.POST)
  public ModelAndView showAdvancedFraudEditPage(HttpServletRequest request,
      HttpServletResponse response, HttpSession session, Map<String, Object> model,
      @FormParam("getId") final Long getId,
      @FormParam("getMerchantCode") final String getMerchantCode) {
    logger.info("Entering :: FraudController :: showAdvancedFraudEditPage method");
    ModelAndView modelAndView = new ModelAndView(CHATAK_MERCHANT_ADVANCED_FRAUD_EDIT_PAGE);
    String existingFeature = (String) session.getAttribute(Constants.EXISTING_FEATURES);


    if (!existingFeature.contains(FeatureConstants.MERCHANT_SERVICE_FRAUD_ADVANCED_FEATURE_ID)) {
      return validateHeader(session, modelAndView);
    }
    try {
      AdvancedFraudDTO advancedFraudDTO = new AdvancedFraudDTO();
      advancedFraudDTO.setId(getId);
      advancedFraudDTO.setMerchantCode(getMerchantCode);
      advancedFraudDTO = fraudService.searchAdvancedFraudByIdAndMerchantCode(advancedFraudDTO);

      if (null != advancedFraudDTO) {
        model.put(Constants.ADVANCED_FRAUD_DTO, advancedFraudDTO);
      } else {
        modelAndView.setViewName(CHATAK_MERCHANT_FRAUD_ADVANCED_PAGE);
      }
    } catch (Exception e) {
      modelAndView.addObject(Constants.ERROR, messageSource
          .getMessage(Constants.CHATAK_GENERAL_ERROR, null, LocaleContextHolder.getLocale()));
      logger.error("ERROR :: FraudController :: showRecurringPaymentEditPage method",e);
    }
    logger.info("Exiting ::  FraudController :: showRecurringPaymentEditPage method");
    return modelAndView;
  }

  /**
   * Method used to update the Advanced Fraud info
   * 
   * @param request
   * @param response
   * @param session
   * @param model
   * @param recurringPaymentInfoDTO
   * @return
   */
  @RequestMapping(value = CHATAK_MERCHANT_ADVANCED_FRAUD_UPDATE, method = RequestMethod.POST)
  public ModelAndView updateRecurringPayment(HttpServletRequest request,
      HttpServletResponse response, HttpSession session, Map<String, Object> model,
      AdvancedFraudDTO advancedFraudDTO) {
    logger.info("Entering :: FraudController :: updateRecurringPayment method");
    ModelAndView modelAndView = new ModelAndView(CHATAK_MERCHANT_FRAUD_ADVANCED_PAGE);
    String existingFeature = (String) session.getAttribute(Constants.EXISTING_FEATURES);

    if (request.getHeader(Constants.REFERER) == null) {
      return validateHeader(session, modelAndView);
    }

    if (!existingFeature.contains(FeatureConstants.MERCHANT_SERVICE_FRAUD_ADVANCED_FEATURE_ID)) {
      return validateHeader(session, modelAndView);
    }
    try {
      LoginResponse loginResponse = (LoginResponse) session.getAttribute(Constants.LOGIN_RESPONSE);
      AdvancedFraudDTO advancedFraudResponse =
          fraudService.searchAdvancedFraudByIdAndMerchantCode(advancedFraudDTO);
      if (null != advancedFraudResponse) {
        advancedFraudDTO.setUpdatedBy(loginResponse.getUserId().toString());
        advancedFraudDTO.setUpdatedDate(new Timestamp(System.currentTimeMillis()));
        advancedFraudDTO.setCreatedBy(advancedFraudResponse.getCreatedBy());
        advancedFraudDTO.setCreatedDate(advancedFraudResponse.getCreatedDate());
        fraudService.updateAdvancedFraud(advancedFraudDTO);

        if (advancedFraudDTO.getErrorCode().equals(Constants.SUCCESS_CODE)) {
          model.put(Constants.SUCCESS, "Advanced Fraud Information is Updated successfully");
        } else {
          model.put(Constants.ERROR, "Failed to Updated the Advanced Fraud Information.");
        }

        modelAndView.setViewName(CHATAK_MERCHANT_FRAUD_ADVANCED_PAGE);

        advancedFraudDTO.setId(advancedFraudDTO.getId());
        advancedFraudDTO.setPageIndex(Constants.ONE);
        advancedFraudDTO.setPageSize(Constants.MAX_ENTITIES_PORTAL_DISPLAY_SIZE);
        List<AdvancedFraudDTO> advancedFraudList =
            fraudService.searchAdvancedFraudByCreatedBy(advancedFraudDTO);
        model.put(Constants.ADVANCED_FRAUD_LIST, advancedFraudList);
        model.put(Constants.ADVANCED_FRAUD_DTO, advancedFraudDTO);
      }
    } catch (Exception e) {
      modelAndView.addObject(Constants.ERROR, messageSource
          .getMessage(Constants.CHATAK_GENERAL_ERROR, null, LocaleContextHolder.getLocale()));
      logger.error("ERROR :: FraudController :: updateRecurringPayment method",e);
    }
    logger.info("Exiting ::  FraudController :: updateRecurringPayment method");
    return modelAndView;
  }

  /**
   * Method used to delete the Advanced Fraud
   * 
   * @param request
   * @param response
   * @param session
   * @param model
   * @param getAdvancedFraudInfoId
   * @return
   */
  @RequestMapping(value = CHATAK_MERCHANT_DELETE_ADVANCED_FRAUD, method = RequestMethod.POST)
  public ModelAndView deleteAdvancedFraud(HttpServletRequest request, HttpServletResponse response,
      HttpSession session, Map<String, Object> model, @FormParam("getId1") final Long getId,
      @FormParam("getMerchantCode1") final String getMerchantCode) {
    logger.info("Entering :: FraudController :: deleteAdvancedFraud method");
    ModelAndView modelAndView = new ModelAndView(CHATAK_MERCHANT_FRAUD_ADVANCED_PAGE);
    String existingFeature = (String) session.getAttribute(Constants.EXISTING_FEATURES);

    if (request.getHeader(Constants.REFERER) == null) {
      return validateHeader(session, modelAndView);
    }

    if (!existingFeature.contains(FeatureConstants.MERCHANT_SERVICE_FRAUD_ADVANCED_FEATURE_ID)) {
      return validateHeader(session, modelAndView);
    }
    try {
      LoginResponse loginResponse = (LoginResponse) session.getAttribute(Constants.LOGIN_RESPONSE);
      AdvancedFraudDTO advancedFraudDTO = new AdvancedFraudDTO();
      advancedFraudDTO.setCreatedBy(loginResponse.getUserId().toString());
      advancedFraudDTO.setId(Long.valueOf(getId));
      advancedFraudDTO.setMerchantCode(getMerchantCode);
      advancedFraudDTO = fraudService.searchAdvancedFraudByIdAndMerchantCode(advancedFraudDTO);

      if (null != advancedFraudDTO) {
        fraudService.deleteAdvancedFraud(advancedFraudDTO.getId());
        advancedFraudDTO.setId(advancedFraudDTO.getId());
        advancedFraudDTO.setPageIndex(Constants.ONE);
        advancedFraudDTO.setPageSize(Constants.MAX_ENTITIES_PORTAL_DISPLAY_SIZE);
        model.put(Constants.SUCCESS, "Deleted the Advanced Fraud Information Successfully");
      }

      List<AdvancedFraudDTO> advancedFraudList =
          fraudService.searchAdvancedFraudByCreatedBy(advancedFraudDTO);
      if (StringUtil.isListNotNullNEmpty(advancedFraudList)) {
        model.put(Constants.ADVANCED_FRAUD_LIST, advancedFraudList);
        model.put(Constants.ADVANCED_FRAUD_DTO, advancedFraudDTO);
      }
    } catch (Exception e) {
      modelAndView.addObject(Constants.ERROR, messageSource
          .getMessage(Constants.CHATAK_GENERAL_ERROR, null, LocaleContextHolder.getLocale()));
      logger.error("ERROR :: FraudController :: deleteAdvancedFraud method",e);
    }
    logger.info("Exiting :: FraudController :: deleteAdvancedFraud method");
    return modelAndView;
  }
}
