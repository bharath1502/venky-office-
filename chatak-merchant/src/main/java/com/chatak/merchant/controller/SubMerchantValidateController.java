package com.chatak.merchant.controller;

import java.text.NumberFormat;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.chatak.merchant.constants.URLMappingConstants;
import com.chatak.merchant.controller.model.ExportDetails;
import com.chatak.merchant.controller.model.LoginDetails;
import com.chatak.merchant.controller.model.Option;
import com.chatak.merchant.exception.ChatakMerchantException;
import com.chatak.merchant.model.MerchantData;
import com.chatak.merchant.model.MerchantSearchResponse;
import com.chatak.merchant.service.MerchantInfoService;
import com.chatak.merchant.service.MerchantService;
import com.chatak.merchant.util.ExportUtil;
import com.chatak.merchant.util.JsonUtil;
import com.chatak.merchant.util.StringUtil;
import com.chatak.pg.bean.Response;
import com.chatak.pg.constants.ActionErrorCode;
import com.chatak.pg.constants.PGConstants;
import com.chatak.pg.enums.ExportType;
import com.chatak.pg.model.AgentDTOResponse;
import com.chatak.pg.model.Merchant;
import com.chatak.pg.user.bean.AddMerchantResponse;
import com.chatak.pg.util.Constants;

@SuppressWarnings({"rawtypes", "unchecked"})
@Controller
public class SubMerchantValidateController implements URLMappingConstants {

  private static Logger logger = Logger.getLogger(SubMerchantValidateController.class);

  @Autowired
  private MerchantService merchantService;

  @Autowired
  private MessageSource messageSource;

  @Autowired
  MerchantInfoService merchantInfoService;

  /**
   * Method to validate unique user
   * 
   * @param model
   * @param session
   * @return
   */
  @RequestMapping(value = UNIQUE_USER, method = RequestMethod.GET)
  public @ResponseBody String validateUniqueUserName(Map model, HttpServletRequest request,
      HttpServletResponse response, HttpSession session) {
    ModelAndView modelAndView = new ModelAndView("");
    modelAndView.addObject(Constants.ERROR, null);
    logger.info("Entering :: SubMerchantController :: validateUniqueUserName method");
    String userName = request.getParameter("userName");
    Response response2 = null;
    try {
      response2 = merchantInfoService.validateUserName(userName);
      return JsonUtil.convertObjectToJSON(response2);

    } catch (ChatakMerchantException e) {
      logger.error("ERROR:: SubMerchantValidateController:: validateUniqueUserName method", e);
      response2 = setErrorCodeAndErrorMessage(modelAndView);
      try {
        return JsonUtil.convertObjectToJSON(response2);
      } catch (ChatakMerchantException e1) {
        logger.error(
            "ERROR:: SubMerchantValidateController:: validateUniqueUserName method :: convertObjectToJSON",
            e1);
      }
    } catch (Exception e) {
    	logger.error("ERROR:: SubMerchantValidateController:: validateUniqueUserName method", e);
      response2 = setErrorCodeAndErrorMessage(modelAndView);
      try {
        return JsonUtil.convertObjectToJSON(response2);
      } catch (ChatakMerchantException e1) {
        logger.error(
            "ERROR:: SubMerchantValidateController:: validateUniqueUserName method :: convertObjectToJSON",
            e1);
      }
    }
    logger.info("Exiting:: SubMerchantValidateController:: validateUniqueUserName method");
    return null;
  }

  private Response setErrorCodeAndErrorMessage(ModelAndView modelAndView) {
	  Response response2 = new Response();
      response2.setErrorCode(ActionErrorCode.ERROR_CODE_Z5);
      response2
          .setErrorMessage(ActionErrorCode.getInstance().getMessage(ActionErrorCode.ERROR_CODE_Z5));
      modelAndView.addObject(Constants.ERROR,
          messageSource.getMessage(Constants.CHATAK_GENERAL_ERROR, null, LocaleContextHolder.getLocale()));
	return response2;
  }

  @RequestMapping(value = GET_STATES, method = RequestMethod.GET)
  public @ResponseBody String getStatesById(Map model, HttpServletRequest request,
      HttpServletResponse response, HttpSession session) {
    ModelAndView modelAndView = new ModelAndView("");
    modelAndView.addObject(Constants.ERROR, null);
    logger.info("Entering :: SubMerchantValidateController :: getStatesById method");
    String country = request.getParameter("countryid");
    try {
      Response response2 = merchantInfoService.getStatesByCountry(country);
      if (response2 != null) {
        return JsonUtil.convertObjectToJSON(response2);
      }

    } catch (ChatakMerchantException e) {
      logger.error("ERROR:: SubMerchantValidateController:: getStatesById method", e);
      modelAndView.addObject(Constants.ERROR,
          messageSource.getMessage(Constants.CHATAK_GENERAL_ERROR, null, LocaleContextHolder.getLocale()));
    }
    logger.info("Exiting:: SubMerchantValidateController:: getStatesById method");
    return null;
  }

  @RequestMapping(value = UNIQUE_EMAIL, method = RequestMethod.GET)
  public @ResponseBody String validateUniqueEmailId(Map model, HttpServletRequest request,
      HttpServletResponse response, HttpSession session) {
    ModelAndView modelAndView = new ModelAndView("");
    modelAndView.addObject(Constants.ERROR, null);
    logger.info("Entering :: SubMerchantValidateController :: validateUniqueEmailId method");
    String emailId = request.getParameter("emailId");
    Response response2 = null;
    try {
      response2 = merchantInfoService.validateEmailId(emailId);
      return JsonUtil.convertObjectToJSON(response2);

    } catch (ChatakMerchantException e2) {
      logger.error("ERROR:: SubMerchantValidateController:: validateUniqueEmailId method", e2);
      response2 = setErrorCodeAndErrorMessage(modelAndView);
      try {
        return JsonUtil.convertObjectToJSON(response2);
      } catch (ChatakMerchantException e1) {
        logger.error(
            "ERROR:: SubMerchantValidateController:: validateUniqueEmailId method::convertObjectToJSON",
            e1);
      }
    } catch (Exception e2) {
      logger.error("ERROR:: SubMerchantValidateController:: validateUniqueEmailId method", e2);
      response2 = setErrorCodeAndErrorMessage(modelAndView);
      try {
        return JsonUtil.convertObjectToJSON(response2);
      } catch (ChatakMerchantException e1) {
        logger.error(
            "ERROR:: SubMerchantValidateController:: validateUniqueEmailId method::convertObjectToJSON",
            e1);
      }
    }
    logger.info("Exiting:: SubMerchantValidateController:: validateUniqueEmailId method");
    return null;
  }

  @RequestMapping(value = UNIQUE_EMAIL_EDIT, method = RequestMethod.GET)
  public @ResponseBody String validateUniqueEmailIdEdit(Map model, HttpServletRequest request,
      HttpServletResponse response, HttpSession session) {
    ModelAndView modelAndView = new ModelAndView("");
    modelAndView.addObject(Constants.ERROR, null);
    logger.info("Entering :: SubMerchantValidateController :: validateUniqueEmailIdEdit method");
    String emailId = request.getParameter("emailId");
    String merchantCode = request.getParameter("merchantCode");
    Response response2 = null;
    try {
      response2 = merchantInfoService.validateEmailIdEdit(emailId, merchantCode);
      return JsonUtil.convertObjectToJSON(response2);

    } catch (ChatakMerchantException e3) {
      logger.error("ERROR:: SubMerchantValidateController:: validateUniqueEmailIdEdit method", e3);
      response2 = setErrorCodeAndErrorMessage(modelAndView);
      try {
        return JsonUtil.convertObjectToJSON(response2);
      } catch (ChatakMerchantException e1) {
        logger.error(
            "ERROR:: SubMerchantValidateController:: validateUniqueEmailIdEdit method :: convertObjectToJSON",
            e1);
      }
    } catch (Exception e3) {
      logger.error("ERROR:: SubMerchantValidateController:: validateUniqueEmailIdEdit method", e3);
      response2 = setErrorCodeAndErrorMessage(modelAndView);
      try {
        return JsonUtil.convertObjectToJSON(response2);
      } catch (ChatakMerchantException e1) {
        logger.error(
            "ERROR:: SubMerchantValidateController:: validateUniqueEmailIdEdit method :: convertObjectToJSON",
            e1);
      }
    }
    logger.info("Exiting:: SubMerchantValidateController:: validateUniqueEmailIdEdit method");
    return null;
  }

  @RequestMapping(value = UNIQUE_USER_EDIT, method = RequestMethod.GET)
  public @ResponseBody String validateUniqueUserNameEdit(Map model, HttpServletRequest request,
      HttpServletResponse response, HttpSession session) {
    ModelAndView modelAndView = new ModelAndView("");
    modelAndView.addObject(Constants.ERROR, null);
    logger.info("Entering :: SubMerchantValidateController :: validateUniqueUserNameEdit method");
    String userName = request.getParameter("userName");
    String merchantCode = request.getParameter("merchantCode");
    Response response2 = null;

    try {
      response2 = merchantInfoService.validateUserNameEdit(userName, merchantCode);
      return JsonUtil.convertObjectToJSON(response2);

    } catch (ChatakMerchantException e4) {
      logger.error("ERROR:: SubMerchantValidateController:: validateUniqueUserNameEdit method", e4);
      response2 = setErrorCodeAndErrorMessage(modelAndView);
      try {
        return JsonUtil.convertObjectToJSON(response2);
      } catch (ChatakMerchantException e1) {
        logger.error(
            "ERROR:: SubMerchantValidateController:: validateUniqueUserNameEdit method::convertObjectToJSON",
            e1);
      }
    } catch (Exception e4) {
      logger.error("ERROR:: SubMerchantValidateController:: validateUniqueUserNameEdit method", e4);
      response2 = setErrorCodeAndErrorMessage(modelAndView);
      try {
        return JsonUtil.convertObjectToJSON(response2);
      } catch (ChatakMerchantException e1) {
        logger.error(
            "ERROR:: SubMerchantValidateController:: validateUniqueUserNameEdit method::convertObjectToJSON",
            e1);
      }
    }
    logger.info("Exiting:: SubMerchantValidateController:: validateUniqueUserNameEdit method");
    return null;
  }

  @RequestMapping(value = CHATAK_MERCHANT_SIGNUP, method = RequestMethod.GET)
  public ModelAndView showCreateMerchantSignUp(Map map, HttpServletRequest request,
      HttpServletResponse response, HttpSession session) {
    logger.info("Entering :: SubMerchantValidateController :: showCreateMerchantSignUp method");
    ModelAndView modelAndView = new ModelAndView(SHOW_CHATAK_MERCHANT_SIGNUP);

    List<Option> processorNames = null;
    List<Option> options;
    try {
      options = merchantInfoService.getFeeProgramNames();
      modelAndView.addObject("feeprogramnames", options);
      session.setAttribute("feeprogramnames", options);
      session.setAttribute("updateMerchantId", session.getAttribute("updateMerchantId"));
      List<Option> countryList = merchantInfoService.getCountries();
      modelAndView.addObject(Constants.COUNTRY_LIST, countryList);
      session.setAttribute(Constants.COUNTRY_LIST, countryList);
      processorNames = merchantInfoService.getProcessorNames();
      modelAndView.addObject("processorNames", processorNames);
      modelAndView.addObject(Constants.MERCHANT_SIGN_UP_REQUEST, new Merchant());
    } catch (ChatakMerchantException e) {
      logger.error("Error :: SubMerchantValidateController :: showCreateMerchantSignUp ", e);
      modelAndView.addObject(Constants.ERROR, "Error Processing Merchant Signup");
    }

    logger.info("Exiting:: SubMerchantValidateController:: showCreateMerchantSignUp method");
    return modelAndView;
  }

  @RequestMapping(value = CHATAK_MERCHANT_SIGNUP_PROCESS, method = RequestMethod.POST)
  public ModelAndView processMerchantSignUp(HttpServletRequest request,
      HttpServletResponse response, HttpSession session, Map model, Merchant merchant,
      BindingResult bindingResult) {
    ModelAndView modelAndView = new ModelAndView(CHATAK_MERCHANT_LOGIN);
    logger.info("Entering :: SubMerchantValidateController :: showCreateMerchantSignUp method");
    try {
      merchant.setStatus(PGConstants.STATUS_SELF_REGISTERATION_PENDING);
      if (!PGConstants.SUB_MERCHANT.equals(merchant.getMerchantType())) {
        merchant.setMerchantType(PGConstants.MERCHANT);
      }

      merchant.setCategory(PGConstants.PRIMARY_ACCOUNT);

      NumberFormat format = NumberFormat.getCurrencyInstance();
      Number number = format.parse(merchant.getLegalAnnualCard());
      merchant.setLegalAnnualCard(number.toString());

      AddMerchantResponse addMerchantResponse = merchantService.addMerchant(merchant, "0");//0 For self register

      if (null != addMerchantResponse
          && addMerchantResponse.getErrorCode().equals(ActionErrorCode.ERROR_CODE_00)) {
        merchantInfoService.sendMerchantSignUPNotification(addMerchantResponse, merchant);
        new Merchant();
        modelAndView.addObject(Constants.SUCESS, messageSource
            .getMessage("chatak.user.signup.success", null, LocaleContextHolder.getLocale()));
      }
    } catch (ChatakMerchantException e) {
      logger.error("ERROR:: SubMerchantValidateController:: showCreateMerchantSignUp method", e);
      modelAndView = new ModelAndView(SHOW_CHATAK_MERCHANT_SIGNUP);
      List<Option> countryList = merchantInfoService.getCountries();
      modelAndView.addObject(Constants.COUNTRY_LIST, countryList);
      session.setAttribute(Constants.COUNTRY_LIST, countryList);
      modelAndView.addObject(Constants.MERCHANT_SIGN_UP_REQUEST, merchant);
      session.setAttribute(Constants.ERROR, "Error signing up ,please try again");
      return modelAndView;
    } catch (Exception e) {
      logger.error("ERROR:: SubMerchantValidateController:: showCreateMerchantSignUp method", e);
      modelAndView = new ModelAndView(SHOW_CHATAK_MERCHANT_SIGNUP);
      List<Option> countryList = merchantInfoService.getCountries();
      modelAndView.addObject(Constants.COUNTRY_LIST, countryList);
      session.setAttribute(Constants.COUNTRY_LIST, countryList);
      modelAndView.addObject(Constants.MERCHANT_SIGN_UP_REQUEST, merchant);
      session.setAttribute(Constants.ERROR, "Error signing up ,please try again");
      return modelAndView;
    }
    LoginDetails loginDetails = new LoginDetails();
    model.put("loginDetails", loginDetails);
    session.setAttribute(Constants.ERROR, null);
    session.setAttribute(Constants.SUCESS, null);
    logger.info("Exiting:: SubMerchantValidateController:: showCreateMerchantSignUp method");
    return modelAndView;
  }

  @RequestMapping(value = VALIDATE_MERCHANT_CODE, method = RequestMethod.GET)
  public @ResponseBody String validateParentMerchantCode(Map model, HttpServletRequest request,
      HttpServletResponse response, HttpSession session) {
    ModelAndView modelAndView = new ModelAndView("");
    modelAndView.addObject(Constants.ERROR, null);
    logger.info("Entering :: SubMerchantValidateController :: validateParentMerchantCode method");
    String merchantCode = request.getParameter("parentMerchantCode");
    Response response2 = null;
    try {
      response2 = merchantInfoService.validateParentMerchantCode(merchantCode);
      return JsonUtil.convertObjectToJSON(response2);

    } catch (ChatakMerchantException e5) {
      logger.error("ERROR:: SubMerchantValidateController:: validateParentMerchantCode method", e5);
      response2 = setErrorCodeAndErrorMessage(modelAndView);
      try {
        return JsonUtil.convertObjectToJSON(response2);
      } catch (ChatakMerchantException e1) {
        logger.error(
            "ERROR:: SubMerchantValidateController:: validateParentMerchantCode method :: convertObjectToJSON",
            e1);
      }
    } catch (Exception e5) {
      logger.error("ERROR:: SubMerchantValidateController:: validateParentMerchantCode method", e5);
      response2 = setErrorCodeAndErrorMessage(modelAndView);
      try {
        return JsonUtil.convertObjectToJSON(response2);
      } catch (ChatakMerchantException e1) {
        logger.error(
            "ERROR:: SubMerchantValidateController:: validateParentMerchantCode method :: convertObjectToJSON",
            e1);
      }
    }
    logger.info("Exiting:: SubMerchantValidateController:: validateParentMerchantCode method");
    return null;
  }

  @RequestMapping(value = CHATAK_MERCHANT_VALIDATE_AGENT_DETAILS, method = RequestMethod.GET)
  public @ResponseBody String validateAgentData(Map model, HttpServletRequest request,
      HttpServletResponse response, HttpSession session) {
    ModelAndView modelAndView = new ModelAndView(CHATAK_ADMIN_CREATE_MERCHANT_PAGE);
    modelAndView.addObject(Constants.ERROR, null);
    logger.info("Entering :: MerchantController :: validateAgentData method");

    String agentClientId = request.getParameter("agentClientId");
    String agentAccountNumber = request.getParameter("agentAccountNumber");
    String agentANI = request.getParameter("agentANI");
    String currencyCodeAlpha = request.getParameter("currencyId");

    String clientResponse = null;

    try {

      clientResponse = merchantInfoService.validateAgentDetails(agentAccountNumber, agentClientId,
          agentANI, currencyCodeAlpha);

    } catch (Exception e) {
      logger.error("ERROR:: SubMerchantValidateController:: validateAgentData method", e);
      modelAndView.addObject(Constants.ERROR,
          messageSource.getMessage(Constants.CHATAK_GENERAL_ERROR, null, LocaleContextHolder.getLocale()));
    }
    logger.info("Exiting:: SubMerchantValidateController:: validateAgentData method");
    return clientResponse;
  }

  @RequestMapping(value = GET_AGENT_DATA_BY_ID, method = RequestMethod.GET)
  public @ResponseBody String getAgentById(Map model, HttpServletRequest request,
      HttpServletResponse response, HttpSession session) {
    ModelAndView modelAndView = new ModelAndView(CHATAK_ADMIN_CREATE_MERCHANT_PAGE);
    modelAndView.addObject(Constants.ERROR, null);
    logger.info("Entering :: SubMerchantValidateController :: getAgentById method");
    Long agentId = Long.parseLong(request.getParameter("agentId"));
    try {
      AgentDTOResponse merchantAgent = merchantInfoService.getAgentDataById(agentId);
      if (merchantAgent != null) {
        return JsonUtil.convertObjectToJSON(merchantAgent);
      }
    } catch (ChatakMerchantException e) {
      logger.error("ERROR:: MerchantController:: getAgentById method", e);
      modelAndView.addObject(Constants.ERROR,
          messageSource.getMessage(Constants.CHATAK_GENERAL_ERROR, null, LocaleContextHolder.getLocale()));
    }
    logger.info("Exiting:: SubMerchantValidateController:: getAgentById method");
    return null;
  }

  @RequestMapping(value = GET_AGENT_NAMES_BY_CURRENCY_ALPHA, method = RequestMethod.GET)
  public @ResponseBody String getAgentNamesByCurrency(Map model, HttpServletRequest request,
      HttpServletResponse response, HttpSession session) {
	String currencyAlpha = request.getParameter("currencyAlpha");
    ModelAndView modelAndView = new ModelAndView(CHATAK_ADMIN_CREATE_MERCHANT_PAGE);
    modelAndView.addObject(Constants.ERROR, null);
    logger.info("Entering :: SubMerchantValidateController :: getAgentNamesByCurrency method");
    try {
      Response agentnamesList = merchantInfoService.getAgentNames(currencyAlpha);
      if (agentnamesList != null) {
        model.put("agentnamesList", agentnamesList);
        return JsonUtil.convertObjectToJSON(agentnamesList);
      }
    } catch (ChatakMerchantException e) {
      logger.error("ERROR:: SubMerchantValidateController:: getAgentNamesByCurrency method", e);
      modelAndView.addObject(Constants.ERROR,
          messageSource.getMessage(Constants.CHATAK_GENERAL_ERROR, null, LocaleContextHolder.getLocale()));
    }
    logger.info("Exiting:: SubMerchantValidateController:: getAgentNamesByCurrency method");
    return null;
  }

  /**
   * Method to update merchant page
   * 
   * @param model
   * @param session
   * @return
   */
  @RequestMapping(value = CHATAK_SUB_MERCHANT_REPORT, method = RequestMethod.POST)
  public ModelAndView downloadMerchantReport(HttpServletRequest request,
      HttpServletResponse response, HttpSession session, Map model,
      @FormParam("downLoadPageNumber") final Integer downLoadPageNumber,
      @FormParam("totalRecords") final Integer totalRecords,
      @FormParam("downloadAllRecords") final boolean downloadAllRecords) {
    String downloadType=request.getParameter("downloadType");
    
    logger.info("Entering:: SubMerchantValidateController:: downloadMerchantReport method");
    ModelAndView modelAndView = new ModelAndView(CHATAK_SHOW_SUB_MERCHANT_SEARCH_PAGE);
    try {
      Merchant merchantRequest = null;
      MerchantSearchResponse searchResponse = null;
      Long merchantId = (Long) session.getAttribute(Constants.LOGIN_USER_MERCHANT_ID);
      logger.info("Download request start for merchant Id ::" + merchantId);
      merchantRequest = (Merchant) session.getAttribute(Constants.MERCHANTS_MODEL);

      merchantRequest.setParentMerchantId(merchantId);
      merchantRequest.setPageIndex(downLoadPageNumber);
      Integer pageSize = merchantRequest.getPageSize();

      if (downloadAllRecords) {
        merchantRequest.setPageIndex(Constants.ONE);
        merchantRequest.setPageSize(totalRecords);
      }
      searchResponse = merchantInfoService.searchSubMerchantList(merchantRequest);
      List<MerchantData> list = searchResponse.getMerchants();
      ExportDetails exportDetails = new ExportDetails();
      if (StringUtil.isListNotNullNEmpty(list)) {
        if(Constants.PDF_FILE_FORMAT.equalsIgnoreCase(downloadType)){
        	exportDetails.setExportType(ExportType.PDF);
        } else if (Constants.XLS_FILE_FORMAT.equalsIgnoreCase(downloadType)) {
        	exportDetails.setExportType(ExportType.XLS);
			exportDetails.setExcelStartRowNumber(Integer.parseInt("4"));
        }
        setExportDetailsDataForDownloadRoleReport(list, exportDetails);	
	 	ExportUtil.exportData(exportDetails, response, messageSource);
      }
      merchantRequest.setPageSize(pageSize);
      logger.info("Download request end for merchant Id ::" + merchantId);
    } catch (Exception e) {
      modelAndView.addObject(Constants.ERROR,
          messageSource.getMessage(Constants.CHATAK_GENERAL_ERROR, null, LocaleContextHolder.getLocale()));
      logger.error("ERROR:: SubMerchantValidateController:: downloadMerchantReport method", e);
    }
    logger.info("Exiting:: SubMerchantValidateController:: downloadMerchantReport method");
    return null;
  }
  private void setExportDetailsDataForDownloadRoleReport(List<MerchantData> list,
	      ExportDetails exportDetails) {
	    exportDetails.setReportName("Merchant_");
	    exportDetails.setHeaderMessageProperty("chatak.header.sub.merchant.messages");

	    exportDetails.setHeaderList(getRoleHeaderList());
	    exportDetails.setFileData(getRoleFileData(list));
	  }
  private List<String> getRoleHeaderList() {
	    String[] headerArr = {
	        messageSource.getMessage("merchantFileExportUtil.merchant.code", null,
	            LocaleContextHolder.getLocale()),
	        messageSource.getMessage("search-sub-merchant.label.merchantcompanyname", null,
	            LocaleContextHolder.getLocale()),
	        messageSource.getMessage("search-sub-merchant.label.currencycode", null,
	            LocaleContextHolder.getLocale()),
	        messageSource.getMessage("merchantFileExportUtil.first.name", null,
	            LocaleContextHolder.getLocale()),
	        messageSource.getMessage("merchantFileExportUtil.last.name", null,
	            LocaleContextHolder.getLocale()),
	        messageSource.getMessage("merchantFileExportUtil.email", null,
		        LocaleContextHolder.getLocale()),
		    messageSource.getMessage("merchantFileExportUtil.phone", null,
		        LocaleContextHolder.getLocale()),
		    messageSource.getMessage("merchantFileExportUtil.city", null,
		        LocaleContextHolder.getLocale()),
		    messageSource.getMessage("merchantFileExportUtil.country", null,
		        LocaleContextHolder.getLocale()),
		    messageSource.getMessage("merchantFileExportUtil.status", null,
		        LocaleContextHolder.getLocale())};
	    return new ArrayList<String>(Arrays.asList(headerArr));
	  }
  private static List<Object[]> getRoleFileData(List<MerchantData> list) {
	    List<Object[]> fileData = new ArrayList<Object[]>();

	    for (MerchantData merchantData : list) {
	    	Object[] rowData = new Object[Integer.parseInt("10")];
			  rowData[0] = merchantData.getMerchantCode();
			  rowData[1] = merchantData.getBusinessName();
			  rowData[Integer.parseInt("2")]= merchantData.getLocalCurrency();
			  rowData[Integer.parseInt("3")]= merchantData.getFirstName();
			  rowData[Integer.parseInt("4")]= merchantData.getLastName();
			  rowData[Integer.parseInt("5")]= merchantData.getEmailId();
			  rowData[Integer.parseInt("6")]= merchantData.getPhone().toString();
			  rowData[Integer.parseInt("7")]= merchantData.getCity();
			  rowData[Integer.parseInt("8")]= merchantData.getCountry();
			  rowData[Integer.parseInt("9")]= merchantData.getStatus();
	      fileData.add(rowData);
	    }

	    return fileData;
	  }
}
