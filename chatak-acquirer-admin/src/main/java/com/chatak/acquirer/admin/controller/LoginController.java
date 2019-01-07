/**
 * 
 */
package com.chatak.acquirer.admin.controller;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.ws.rs.FormParam;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.core.session.SessionInformation;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.chatak.acquirer.admin.constants.URLMappingConstants;
import com.chatak.acquirer.admin.controller.model.ChangePasswordRequest;
import com.chatak.acquirer.admin.controller.model.ForgotPasswordRequest;
import com.chatak.acquirer.admin.controller.model.LoginDetails;
import com.chatak.acquirer.admin.controller.model.LoginResponse;
import com.chatak.acquirer.admin.controller.model.ResetPasswordData;
import com.chatak.acquirer.admin.exception.ChatakAdminException;
import com.chatak.acquirer.admin.model.TransactionResponse;
import com.chatak.acquirer.admin.model.UserProfileRequest;
import com.chatak.acquirer.admin.service.FundTransfersService;
import com.chatak.acquirer.admin.service.LoginService;
import com.chatak.acquirer.admin.service.MerchantUpdateService;
import com.chatak.acquirer.admin.service.RoleService;
import com.chatak.acquirer.admin.service.TransactionService;
import com.chatak.acquirer.admin.util.DateUtils;
import com.chatak.acquirer.admin.util.PasswordHandler;
import com.chatak.acquirer.admin.util.StringUtil;
import com.chatak.pg.acq.dao.IssSettlementDataDao;
import com.chatak.pg.acq.dao.MerchantDao;
import com.chatak.pg.acq.dao.TransactionDao;
import com.chatak.pg.acq.dao.model.PGIssSettlementData;
import com.chatak.pg.acq.dao.model.PGMerchant;
import com.chatak.pg.bean.settlement.SettlementEntity;
import com.chatak.pg.constants.AccountTransactionCode;
import com.chatak.pg.constants.PGConstants;
import com.chatak.pg.exception.PrepaidAdminUserNotActiveException;
import com.chatak.pg.model.AccountTransactionDTO;
import com.chatak.pg.model.Merchant;
import com.chatak.pg.model.UserRolesDTO;
import com.chatak.pg.user.bean.GetTransactionsListRequest;
import com.chatak.pg.user.bean.GetTransactionsListResponse;
import com.chatak.pg.util.Constants;
import com.chatak.pg.util.Properties;
import com.chatak.pg.util.validator.CSRFTokenManager;

/**
 * << Add Comments Here >>
 * 
 * @author Girmiti Software
 * @date 03-Jan-2015 10:24:04 AM
 * @version 1.0
 */
@SuppressWarnings({"rawtypes", "unchecked", "unused"})
@Controller
public class LoginController implements URLMappingConstants {

  private static Logger logger = Logger.getLogger(LoginController.class);

  @Autowired
  private LoginValidator loginValidator;

  @Autowired
  private LoginService loginService;

  @Autowired
  private SessionRegistryImpl sessionRegistry;

  @Autowired
  private RoleService roleService;

  @Autowired
  private TransactionDao transactionDao;

  @Autowired
  private MerchantDao merchantDao;

  @Autowired
  private FundTransfersService fundTransfersService;

  @Autowired
  private TransactionService transactionService;

  @Autowired
  private MerchantUpdateService merchantUpdateService;

  @Autowired
  private MessageSource messageSource;
  
  @Autowired
  private IssSettlementDataDao issSettlementDataDao;
  
  private static final String EXECUTED_LIST_SIZE="executedListSize";

  /**
   * Method to show login page
   * 
   * @param model
   * @param session
   * @return
   */
  @RequestMapping(value = CHATAK_ADMIN_LOGIN, method = RequestMethod.GET)
  public ModelAndView showLogin(HttpServletRequest request, Map model, HttpSession session) {
    logger.info("Entering:: LoginController:: showLogin method");

    ModelAndView modelAndView = new ModelAndView(CHATAK_ADMIN_LOGIN);
    modelAndView.addObject(Constants.ERROR, null);
    session.setAttribute(Constants.ERROR, null);
    model.put(Constants.LOGIN_DETAILS, new LoginDetails());
    String token = CSRFTokenManager.getTokenForSession(session);
    logger.info("Genarated CSRF token before login : " + token);
    session.setAttribute("tokenval", token);
    logger.info("Exiting:: LoginController:: showLogin method");
    return modelAndView;
  }

  /**
   * Method to authenticate acquirer admin login request if any error occurs,
   * displays the login page with error message other wise it will load the
   * Dash board page
   * 
   * @param request
   * @param response
   * @param loginDetails
   * @param bindingResult
   * @param model
   * @param session
   * @return
   */
  @RequestMapping(value = CHATAK_ADMIN_AUTHENTICATE, method = RequestMethod.POST)
  public ModelAndView authenticate(HttpServletRequest request, HttpServletResponse response,
      LoginDetails loginDetails, BindingResult bindingResult, Map model, HttpSession session) {
    logger.info("Entering:: LoginController:: authenticate method");
    ModelAndView modelAndView = new ModelAndView(CHATAK_ADMIN_LOGIN);
    try {
      modelAndView.addObject(Constants.ERROR, null);
      session.setAttribute(Constants.ERROR, null);
      modelAndView.addObject(Constants.MERCHANT, new Merchant());
      model.put("transaction", new GetTransactionsListRequest());
      loginDetails.setLoginIpAddress(request.getRemoteAddr() +" | " + request.getParameter("privateIp"));//Setting remote public ipaddress and remote private ipaddress
      loginValidator.validate(loginDetails, bindingResult);
      if (bindingResult.hasErrors()) {
        getError(bindingResult, modelAndView);
        return modelAndView;
      }
      String userAgent = getUserAgent(request);

      //to maintain current login time
      Date today = new Date();
      DateFormat df = new SimpleDateFormat(Constants.TIMEZONE_DATE_FORMAT);
      df.setTimeZone(TimeZone.getTimeZone(loginDetails.getTimeZoneRegion()));
      loginDetails.setCurrentLoginTime(df.format(today));

      LoginResponse loginResponse = loginService.authenticate(loginDetails);
      validateLoginResponse(loginResponse);
      if (checkUserType(loginResponse)) {
        session.setAttribute(Constants.LOGIN_USER_ID, loginResponse.getUserId());
        session.setAttribute("loginUserType", loginResponse.getUserType());
        modelAndView = changePassword(model, request, session);
      } else {
        
    	  boolean isValid = validateSessionAndRequest(modelAndView, request, response, loginDetails, userAgent,session);
		  if(!isValid) {
					return modelAndView;
		  }
    	  model.put("loginType", loginResponse.getUserType());
    	  session.setAttribute(CSRFTokenManager.CSRF_TOKEN_FOR_SESSION_ATTR_NAME,
    	  			(String) session.getAttribute(CSRFTokenManager.CSRF_TOKEN_FOR_SESSION_ATTR_NAME));
    	  
    	  if (loginResponse.getMessage() == Properties
    	      .getProperty("admin.service.login.password.expiration.error.message")) {
    	    modelAndView = setModel(request, model, session, modelAndView, loginResponse);
    	  } else if (checkValidStatus(loginResponse)) {
          modelAndView = setLoginSuccessResponse(response, session, modelAndView, loginResponse,
              loginDetails, userAgent);
          processSettlement(session, loginResponse);
          session.setAttribute("adminId", loginResponse.getUserId());
          List<Merchant> merchants = getMerchantsList(loginResponse);
          validateMerchantList(session, merchants);
          
          GetTransactionsListRequest transaction = new GetTransactionsListRequest();
          TransactionResponse transactionResponse = new TransactionResponse();
          List<String> txnCodeList = new ArrayList<String>(Constants.ELEVEN);
          txnCodeList.add(AccountTransactionCode.CC_AMOUNT_CREDIT);
          txnCodeList.add(AccountTransactionCode.CC_AMOUNT_DEBIT);
          txnCodeList.add(AccountTransactionCode.CC_FEE_CREDIT);
          txnCodeList.add(AccountTransactionCode.CC_FEE_DEBIT);
          txnCodeList.add(AccountTransactionCode.ACCOUNT_CREDIT);
          txnCodeList.add(AccountTransactionCode.ACCOUNT_DEBIT);
          txnCodeList.add(AccountTransactionCode.EFT_DEBIT);
          txnCodeList.add(AccountTransactionCode.FT_BANK);
          txnCodeList.add(AccountTransactionCode.FT_CHECK);
          transaction.setTransactionCodeList(txnCodeList);
          transaction.setSettlementStatus(PGConstants.PG_SETTLEMENT_PROCESSING);
          GetTransactionsListResponse processingTxnList =
              transactionService.searchAccountTransactions(transaction);
          transaction.setSettlementStatus(PGConstants.PG_SETTLEMENT_EXECUTED);
          GetTransactionsListResponse executedTxnList =
              getExecutedTxnList(loginResponse, transaction);
          loginProcess(session, modelAndView, transactionResponse, processingTxnList, executedTxnList);
          modelAndView.addObject(EXECUTED_LIST_SIZE, getTotalResultCount(executedTxnList));
          model.put("transaction", new GetTransactionsListRequest());
        } 
        else
            modelAndView = setModel(request, model, session, modelAndView, loginResponse);
      }
    } catch (ChatakAdminException e) {
      logger.error("ERROR:: LoginController:: authenticate method", e);
      modelAndView.addObject(Constants.ERROR, e.getMessage());
      modelAndView.addObject(Constants.MERCHANT, new Merchant());
      modelAndView.addObject("dashBoardRecords", null);
    } catch (Exception e) {
      logger.error("ERROR:: LoginController:: authenticate method", e);
      modelAndView.addObject(Constants.ERROR,
          Properties.getProperty("chatak.admin.login.error.message"));
      modelAndView.addObject(Constants.MERCHANT, new Merchant());
      modelAndView.addObject("dashBoardRecords", null);
    }
    logger.info("Exiting:: LoginController:: authenticate method");
    return modelAndView;
  }

  private GetTransactionsListResponse getExecutedTxnList(LoginResponse loginResponse,
      GetTransactionsListRequest transaction) {
    GetTransactionsListResponse executedTxnList = null;
    if (loginResponse.getUserType().equals(Constants.PM_USER_TYPE)
    	  || loginResponse.getUserType().equals(Constants.ISO_USER_TYPE)) {
      executedTxnList = transactionService.searchAccountTransactionsForEntityId(transaction, loginResponse.getEntityId(), loginResponse.getUserType());
    } else {
      executedTxnList = transactionService.searchAccountTransactions(transaction);
    }
    return executedTxnList;
  }

  private void validateLoginResponse(LoginResponse loginResponse) throws ChatakAdminException {
    if (loginResponse == null)
      throw new ChatakAdminException(messageSource.getMessage("chatak.admin.login.error.message",
          null, LocaleContextHolder.getLocale()));
    if (null != loginResponse.getStatus() && loginResponse.getStatus().equals(Boolean.FALSE))
        throw new ChatakAdminException(loginResponse.getMessage());
  }

  private String getUserAgent(HttpServletRequest request) {
    String userAgent = request.getHeader("user-agent");
    if (null != userAgent) {
      userAgent = userAgent.replaceAll("\\ ", "");
    }
    return userAgent;
  }

  private List<Merchant> getMerchantsList(LoginResponse loginResponse) {
	  List<Merchant> merchants = null;
	  if (loginResponse.getUserType().equals(PGConstants.ADMIN)) {
		  merchants = merchantUpdateService.getMerchantByStatusPendingandDecline();
	  } else if (loginResponse.getUserType().equals(PGConstants.PROGRAM_MANAGER_NAME)) {
		  merchants = merchantUpdateService.getPmMerchantByEntityIdandEntityType(loginResponse.getEntityId(),
				  loginResponse.getUserType());
	  }
	  return merchants;
  }

  private Integer getTotalResultCount(GetTransactionsListResponse executedTxnList) {
	  return executedTxnList != null ? executedTxnList.getTotalResultCount() : Constants.ZERO;
  }

	private void processSettlement(HttpSession session, LoginResponse loginResponse) {
		if (loginResponse.getUserType().equals(PGConstants.PROGRAM_MANAGER_NAME)) {
			List<PGIssSettlementData> list = issSettlementDataDao.findByProgramManagerIdByStatus(loginResponse.getEntityId(), PGConstants.S_STATUS_PENDING);
			List<SettlementEntity> settlementData = new ArrayList<>();
			processSettlementData(session, list, settlementData);
		} else if (loginResponse.getUserType().equals(PGConstants.ADMIN)) {
			List<PGIssSettlementData> list = issSettlementDataDao.getAllPendingPM();
			List<SettlementEntity> settlementData = new ArrayList<>();
			processSettlementData(session, list, settlementData);
		}
	}

	private boolean checkValidStatus(LoginResponse loginResponse) {
		return loginResponse.getStatus() != null && loginResponse.getStatus();
	}

	private boolean checkUserType(LoginResponse loginResponse) {
		return null != loginResponse.getUserType() && loginResponse.getUserType().equals(PGConstants.NEW_USER);
	}
  
/**
 * @param session
 * @param list
 * @param settlementData
 */
	private void processSettlementData(HttpSession session, List<PGIssSettlementData> list,
			List<SettlementEntity> settlementData) {
		logger.info("Entering :: LoginController :: processSettlementData");
		if (StringUtil.isListNotNullNEmpty(list)) {
			for (PGIssSettlementData data : list) {
				SettlementEntity issSettlementData = new SettlementEntity();
				issSettlementData.setAcqPmId(String.valueOf(data.getAcqPmId()));
				issSettlementData.setProgramManagerName(data.getProgramManagerName());
				issSettlementData.setBatchDate(data.getBatchDate());
				issSettlementData.setTxnTotalAmount(new BigDecimal(data.getTotalAmount()).divide(PGConstants.BIG_DECIMAL_HUNDRED));
				issSettlementData.setTotalTxnCount(data.getTotalTxnCount());
				settlementData.add(issSettlementData);
			}
			session.setAttribute("settlementDataList", new ArrayList(settlementData));
  			session.setAttribute("processSettlementDataList", settlementData.size());

		}
		logger.info("Exiting :: LoginController :: processSettlementData");
	}

  private void validateMerchantList(HttpSession session, List<Merchant> merchants) {
	if (StringUtil.isListNotNullNEmpty(merchants)) {
		session.setAttribute("merchantDataList", merchants.size());
	    if (merchants.size() > Constants.MAX_ENTITY_DISPLAY_SIZE) {
	      session.setAttribute("merchantSubList", merchants.subList(0, Constants.MAX_ENTITY_DISPLAY_SIZE));
	    } else {
	      session.setAttribute("merchantSubList", merchants);
	    }
	    session.setAttribute("pendingMerchants", merchants);
	  }
  }

  private boolean validateSessionAndRequest(ModelAndView modelAndView, HttpServletRequest request, HttpServletResponse response,
	      LoginDetails loginDetails, String userAgent,HttpSession session) {
	  String exIpAdress = loginDetails.getLoginIpAddress();
	for (Object object : sessionRegistry.getAllPrincipals()) {
		LoginDetails loginResponseData = (LoginDetails) object;
		if (loginResponseData.getAcqU().trim().equals(loginDetails.getAcqU())) {
			Cookie[] cookies = request.getCookies();
			String cookieValue = "";

			cookieValue = validateCookie(cookies, cookieValue);

			String encUName = PasswordHandler.encrypt(loginDetails.getAcqU());
			SessionInformation sessionInformation = sessionRegistry.getSessionInformation(encUName);
			if (null == sessionInformation) {
				modelAndView.setViewName(Constants.CHATAK_INVALID_ACCESS);
				return false;
				} else if (loginResponseData.getLoginIpAddress().equals(exIpAdress)
						&& !StringUtil.isNullAndEmpty(cookieValue)) {
				sessionInformation.refreshLastRequest();	
				return true;
			}else {
				validateSessionInformation(response, modelAndView, userAgent, object, loginResponseData,
						cookieValue, encUName, sessionInformation);
				sessionInformation.refreshLastRequest();
				return false;
			}
		}
	}
	  return true;
  }

  private String validateCookie(Cookie[] cookies, String cookieValue) {
	for (Cookie cookie : cookies) {
		if (Constants.COOKIE_CHATAK_NAME.equalsIgnoreCase(cookie.getName())) {
			cookieValue = cookie.getValue();
			break;
		}
	}
	return cookieValue;
  }

  private ModelAndView validateSessionInformation(HttpServletResponse response, ModelAndView modelAndView,
      String userAgent, Object object, LoginDetails loginResponseData, String cookieValue,
      String encUName, SessionInformation sessionInformation) {
    if (!loginResponseData.getjSession().equals(userAgent + cookieValue)) {
        Date lastSessionRequestDate = sessionInformation.getLastRequest();
        Date lastRequestDate = new Date(lastSessionRequestDate.getTime());
        lastRequestDate.setTime(lastRequestDate.getTime()
            + (Integer.parseInt(messageSource.getMessage("chatak.admin.session.timeout",
                null, LocaleContextHolder.getLocale())) * Constants.TIME_OUT));
        Date curDate = new Date();
        if (lastRequestDate.after(curDate)) {
          modelAndView.setViewName(Constants.CHATAK_INVALID_ACCESS);
          return modelAndView;
        } else {
          sessionRegistry.getAllSessions(object, false);
          sessionInformation.expireNow();
          sessionRegistry.removeSessionInformation(encUName);
          Cookie myCookie = new Cookie(Constants.COOKIE_CHATAK_NAME, null);
          myCookie.setMaxAge(0);
          response.addCookie(myCookie);
          modelAndView.setViewName(Constants.CHATAK_INVALID_SESSION);
          return modelAndView;
        }
      }
    modelAndView.setViewName(ACCESS_INVALID);
    return modelAndView;
  }

private void loginProcess(HttpSession session, ModelAndView modelAndView, TransactionResponse transactionResponse,
        GetTransactionsListResponse processingTxnList, GetTransactionsListResponse executedTxnList) {
    if (null != processingTxnList && null != processingTxnList.getAccountTransactionList()) {
        int listSize = processingTxnList.getAccountTransactionList().size();
        transactionResponse
            .setAccountTxnList(listSize < Constants.MAX_ENTITY_DISPLAY_SIZE ? processingTxnList.getAccountTransactionList()
                : processingTxnList.getAccountTransactionList().subList(0, Constants.MAX_ENTITY_DISPLAY_SIZE));
        transactionResponse.setErrorCode(processingTxnList.getErrorCode());
        transactionResponse.setErrorMessage(processingTxnList.getErrorMessage());
        transactionResponse.setTotalNoOfRows(processingTxnList.getTotalResultCount());
        List<AccountTransactionDTO> transactionList = transactionResponse.getAccountTxnList() != null
            ? transactionResponse.getAccountTxnList() : new ArrayList<AccountTransactionDTO>();
        modelAndView.addObject("processingListSize", listSize);
        session.setAttribute("processingListSize", listSize);
        modelAndView.addObject(Constants.PROCESSING_TXN_LIST, transactionList);
        session.setAttribute(Constants.PROCESSING_TXN_LIST, transactionList);
        modelAndView.addObject(Constants.MODEL_ATTRIBUTE_PORTAL_TOTAL_RECORDS_PAGE_NUM, processingTxnList.getTotalResultCount());
      }
      if (null != executedTxnList && null != executedTxnList.getAccountTransactionList()) {
        int listSize = executedTxnList.getAccountTransactionList().size();
        transactionResponse
            .setAccountTxnList(listSize < Constants.MAX_ENTITY_DISPLAY_SIZE ? executedTxnList.getAccountTransactionList()
                : executedTxnList.getAccountTransactionList().subList(0, Constants.MAX_ENTITY_DISPLAY_SIZE));
        List<AccountTransactionDTO> transactionList = transactionResponse.getAccountTxnList() != null
            ? transactionResponse.getAccountTxnList() : new ArrayList<AccountTransactionDTO>();
        modelAndView.addObject("executedListSize", listSize);
        session.setAttribute("executedListSize", listSize);
        modelAndView.addObject(Constants.EXECUTED_TXN_LIST, transactionList);
        session.setAttribute(Constants.EXECUTED_TXN_LIST, transactionList);
        modelAndView.addObject(Constants.MODEL_ATTRIBUTE_PORTAL_TOTAL_RECORDS_PAGE_NUM, executedTxnList.getTotalResultCount());

      }
      modelAndView.addObject(Constants.MODEL_ATTRIBUTE_PORTAL_LIST_PAGE_NUMBER, Constants.ONE);
}


private ModelAndView setModel(HttpServletRequest request, Map model, HttpSession session, ModelAndView modelAndView,
        LoginResponse loginResponse) {
      logger.info("LoginController::login::false::status");
      modelAndView.addObject(Constants.ERROR, loginResponse.getMessage());
      if (loginResponse.getMessage() == Properties
          .getProperty("admin.service.login.password.expiration.error.message")) {
        session.setAttribute(Constants.LOGIN_USER_ID, loginResponse.getUserId());
        modelAndView = changePassword(model, request, session);
        modelAndView.addObject(Constants.ERROR,
            messageSource.getMessage("admin.service.login.password.expiration.error.message",
                null, LocaleContextHolder.getLocale()));
        logger.info("Exiting:: LoginController:: authenticate :: method Password Expired");
        return modelAndView;
      }
    return modelAndView;
}

  /**
   * Method used to display the invalid request page
   * 
   * @param request
   * @param response
   * @param session
   * @return
   */
  @RequestMapping(value = INVALID_REQUEST_PAGE, method = RequestMethod.GET)
  public ModelAndView reloadErrorPage(HttpServletRequest request, HttpServletResponse response,
      HttpSession session) {
    logger.info("Entering:: LoginController:: reloadErrorPage method");
    resetCookie(request, response, session);
    session.invalidate();
    logger.info("Exiting:: LoginController:: reloadErrorPage method");
    return new ModelAndView(INVALID_REQUEST_PAGE);
  }

  /**
   * Method used for log outs from an application
   * 
   * @param request
   * @param response
   * @param model
   * @param session
   * @return
   */
  @RequestMapping(value = CHATAK_ADMIN_LOG_OUT, method = RequestMethod.GET)
  public ModelAndView logout(HttpServletRequest request, HttpServletResponse response, Map model,
      HttpSession session) {
    logger.info("Entering:: LoginController:: logout method");
    try {
      resetCookie(request, response, session);
    } catch (Exception e) {
      logger.error("ERROR:: LoginController:: logout method", e);
    }
    session.invalidate();
    logger.info("Exiting:: LoginController:: logout method");
    return new ModelAndView(CHATAK_ADMIN_LOG_OUT);
  }

  /**
   * Binding the erro object add to the model value
   * 
   * @param results
   * @param modelAndView
   * @return
   */
  private ModelAndView getError(BindingResult results, ModelAndView modelAndView) {
    logger.info("Entering:: LoginController:: getError method");
    if (results.hasErrors()) {
      for (Object object : results.getAllErrors()) {
        if (object instanceof FieldError) {
          FieldError fieldError = (FieldError) object;
          modelAndView.addObject(fieldError.getField() + "Error", fieldError.getCode());
        }
      }
    }
    logger.info("Exiting:: LoginController:: getError method");
    return modelAndView;
  }

  /**
   * @param modelAndView
   * @param sessionInformation
   * @param request
   * @param response
   * @param session
   * @return
   */
  protected ModelAndView resetSessionRegistry(ModelAndView modelAndView,
      SessionInformation sessionInformation, HttpServletRequest request,
      HttpServletResponse response, HttpSession session) {
    sessionInformation.expireNow();
    resetCookie(request, response, session);
    session.invalidate();
    modelAndView.setViewName(Constants.INVALID_SESSION);
    return modelAndView;
  }

  /**
   * Method to set Login Response on Success
   * 
   * @param response
   * @param session
   * @param modelAndView
   * @param loginResponse
   * @param loginDetails
   * @param userAgent
   * @return
   */
  private ModelAndView setLoginSuccessResponse(HttpServletResponse response, HttpSession session,
      ModelAndView modelAndView, LoginResponse loginResponse, LoginDetails loginDetails,
      String userAgent) {
    logger.info("Entering:: LoginController:: setLoginSuccessResponse method");
    session.setAttribute(CHATAK_ADMIN_USER_NAME, loginResponse.getUserName());
    session.setAttribute(Constants.LOGIN_RESPONSE_DATA, loginResponse);
    session.setAttribute(Constants.ERROR, null);
    session.setAttribute(Constants.LOGIN_USER_ID, loginResponse.getUserId());
    session.setAttribute(Constants.LOGIN_ROLE_ID, loginResponse.getUserRoleId());
    session.setAttribute(Constants.LOGIN_USER_TYPE, loginResponse.getUserType());
    // Widget page data loading
    modelAndView.setViewName(CHATAK_ADMIN_HOME);
    session.setAttribute("loginResponse", loginResponse);
    String existingFeatures = "";
    for (String feature : loginResponse.getExistingFeature()) {
      existingFeatures += "|" + feature;
    }
    existingFeatures += "|";
    session.setAttribute(Constants.EXISTING_FEATURES, existingFeatures);
    session.setAttribute(Constants.EXISTING_FEATURE_DATA, loginResponse.getExistingFeature());
    // Service code
    String encUName = PasswordHandler.encrypt(loginDetails.getAcqU());
    Cookie myCookie = new Cookie(Constants.COOKIE_CHATAK_NAME, encUName + session.getId());
    myCookie.setMaxAge(Constants.MAX_AGE);
    response.addCookie(myCookie);
    loginDetails.setjSession(userAgent + encUName + session.getId());
    // Registering logged in user to Spring Session registry
    sessionRegistry.registerNewSession(encUName, loginDetails);
    logger.info("Exiting:: LoginController:: setLoginSuccessResponse method");
    return modelAndView;
  }

  /**
   * @param request
   * @param response
   * @param session
   */
  private void resetCookie(HttpServletRequest request, HttpServletResponse response,
      HttpSession session) {
    logger.info("UserName in session : " + (String) session.getAttribute(CHATAK_ADMIN_USER_NAME));
    String encUName =
        PasswordHandler.encrypt((String) session.getAttribute(CHATAK_ADMIN_USER_NAME));
    sessionRegistry.removeSessionInformation(encUName);
    String cookieValue = StringUtil.getCookieValue(request);
    sessionRegistry.removeSessionInformation(cookieValue);
    Cookie myCookie = new Cookie(Constants.COOKIE_CHATAK_NAME, null);
    myCookie.setMaxAge(0);
    response.addCookie(myCookie);
  }

  @RequestMapping(value = CHATAK_ADMIN_CHANGE_PSWD, method = RequestMethod.GET)
  public ModelAndView changePassword(Map model, HttpServletRequest request, HttpSession session) {
    // Check if user directly hit the URL from the browser
    Long userId = (Long) session.getAttribute(Constants.LOGIN_USER_ID);
    if (userId == null) {
      ModelAndView modelAndView = new ModelAndView(CHATAK_INVALID_SESSION);
      session.invalidate();
      return modelAndView;
    }

    ModelAndView modelAndView = new ModelAndView(CHATAK_ADMIN_CHANGE_PSWD);
    ChangePasswordRequest changePasswordRequest = new ChangePasswordRequest();
    // If user came from a change password case when still not logged into the portal,
    // Ex. from forgot password scenario, set the new user attribute.
    String loginUserType = (String) session.getAttribute(Constants.LOGIN_USER_TYPE);
    if (loginUserType != null && loginUserType.equals(PGConstants.NEW_USER)) {
      changePasswordRequest.setIsNewUser(true);
    }
    model.put("changePasswordRequest", changePasswordRequest);
    return modelAndView;
  }

  @RequestMapping(value = CHATAK_ADMIN_CHANGE_PSWD, method = RequestMethod.POST)
  public ModelAndView changePasswordData(Map model, HttpServletRequest request,
      HttpServletResponse response, ChangePasswordRequest changePasswordRequest,
      HttpSession session) {
    logger.info("Entering:: LoginController:: changePasswordData method");
    ModelAndView modelAndView = new ModelAndView(CHATAK_ADMIN_CHANGE_PSWD);
    try {
      Long userId = (Long) session.getAttribute(Constants.LOGIN_USER_ID);
      loginService.changdPassword(userId, changePasswordRequest.getCurrentPassword(),
          changePasswordRequest.getNewPassword());
      model.put(Constants.SUCESS, messageSource.getMessage("change.password.error.message", null,
          LocaleContextHolder.getLocale()));
      resetCookie(request, response, session);
      modelAndView.setViewName(CHATAK_ADMIN_LOGIN);
      model.put(Constants.LOGIN_DETAILS, new LoginDetails());
    } catch(PrepaidAdminUserNotActiveException e) {
      logger.error("Error :: LoginController :: changePasswordData :: User Is not active", e);
      modelAndView = showLogin(request, model, session);
      model.put(Constants.ERROR, e.getMessage());
      modelAndView.addObject(Constants.ERROR, e.getMessage());
      model.put(Constants.LOGIN_DETAILS, new LoginDetails());
    } catch (ChatakAdminException e) {
      logger.error(" LoginController:: changePasswordData method1" + e);
      model.put(Constants.ERROR, e.getMessage());
    } catch (Exception e) {
      logger.error(" LoginController:: changePasswordData method2" + e);
      model.put(Constants.ERROR, messageSource.getMessage(Constants.CHATAK_NORMAL_ERROR_MESSAGE, null,
          LocaleContextHolder.getLocale()));
    }
    logger.info("Exit:: LoginController:: changePasswordData method");
    return modelAndView;
  }

  @RequestMapping(value = CHATAK_ADMIN_MYPROFILE, method = RequestMethod.GET)
  public ModelAndView myProfile(Map model, HttpServletRequest request, HttpSession session) {
    logger.info("Entering:: LoginController:: myProfile method");
    ModelAndView modelAndView = new ModelAndView(CHATAK_ADMIN_MYPROFILE);
    try {
      Long userId = (Long) session.getAttribute(Constants.LOGIN_USER_ID);
      List<UserRolesDTO> userRoleList = roleService.getRoleList();
      session.setAttribute("userRoleListData", userRoleList);
      UserProfileRequest userProfileRequest = loginService.getUserProfile(userId);
      model.put(Constants.USER_PROFILE_REQUEST, userProfileRequest);
    } catch (Exception e) {
      logger.error("Error:: LoginController:: myProfile method",e);
      model.put(Constants.ERROR, messageSource.getMessage(Constants.CHATAK_NORMAL_ERROR_MESSAGE, null,
          LocaleContextHolder.getLocale()));
    }
    logger.info("Exit:: LoginController:: myProfile method");
    return modelAndView;
  }

  @RequestMapping(value = UPDATE_USER_PROFILE, method = RequestMethod.POST)
  public ModelAndView updateUserProfile(Map model, HttpServletRequest request,
      HttpServletResponse response, UserProfileRequest userProfileRequest, HttpSession session) {
    logger.info("Entering:: LoginController:: updateUserProfile method");
    ModelAndView modelAndView = new ModelAndView(CHATAK_ADMIN_MYPROFILE);
    try {
      Long userId = (Long) session.getAttribute(Constants.LOGIN_USER_ID);
      userProfileRequest.setUserId(userId);

      model.put(Constants.USER_PROFILE_REQUEST, userProfileRequest);
      loginService.changeUserProfile(userProfileRequest);
      model.put(Constants.SUCESS, messageSource.getMessage(
          "chatak.changeProfile.request.error.message", null, LocaleContextHolder.getLocale()));
    } catch (ChatakAdminException e) {
      logger.error("Error:: LoginController:: updateUserProfile method" + e);
      model.put(Constants.ERROR, e.getMessage());
    } catch (Exception e) {
      logger.error("Error:: LoginController:: updateUserProfile method2" + e);
      model.put(Constants.ERROR, messageSource.getMessage(Constants.CHATAK_NORMAL_ERROR_MESSAGE, null,
          LocaleContextHolder.getLocale()));
    }
    logger.info("Exit:: LoginController:: updateUserProfile method");
    return modelAndView;
  }

  @RequestMapping(value = FORGOT_PSWD, method = RequestMethod.GET)
  public ModelAndView forgotPassword(Map model, HttpServletRequest request) {
    logger.info("Entering:: LoginController:: forgotPassword method");
    ForgotPasswordRequest forgotPasswordRequest = new ForgotPasswordRequest();
    model.put("forgotPasswordRequest", forgotPasswordRequest);
    logger.info("Exit:: LoginController:: forgotPassword method");
    return new ModelAndView(FORGOT_PSWD);
  }

  @RequestMapping(value = FORGOT_PSWD, method = RequestMethod.POST)
  public ModelAndView submiPassword(Map model, HttpServletRequest request,
      ForgotPasswordRequest forgotPasswordRequest) {
    logger.info("Entering:: LoginController:: submiPassword method");
    ModelAndView modelAndView = null;
    try {
      String url = request.getRequestURL().toString();
      String uri = request.getRequestURI();
      String ctx = request.getContextPath();
      String baseUrl = url.substring(0, url.length() - uri.length() + ctx.length()) + "/";
      loginService.userExist(forgotPasswordRequest.getUserName(), baseUrl);
      model.put(Constants.SUCESS, messageSource.getMessage("chatak.admin.user.message", null,
          LocaleContextHolder.getLocale()));
      model.put(Constants.LOGIN_DETAILS, new LoginDetails());
      modelAndView = new ModelAndView(CHATAK_ADMIN_LOGIN);
    } catch (ChatakAdminException e) {
      logger.error("Error:: LoginController:: submiPassword method2" + e);
      modelAndView = new ModelAndView(FORGOT_PSWD);
      model.put(Constants.ERROR, e.getMessage());
    } catch (Exception e) {
      logger.error("Error:: LoginController:: submiPassword method2" + e);
      modelAndView = new ModelAndView(FORGOT_PSWD);
      model.put(Constants.ERROR,
          messageSource.getMessage(Constants.CHATAK_GENERAL_ERROR, null, LocaleContextHolder.getLocale()));
    }
    logger.info("Exit:: LoginController:: submiPassword method");
    return modelAndView;
  }

  @RequestMapping(value = PSWD_RESET, method = RequestMethod.GET)
  public ModelAndView getResetPassword(Map model, HttpServletRequest request, HttpSession session,
      @FormParam("t") final String t) {
    logger.info("Entering:: LoginController:: getResetPassword method");
    ModelAndView modelAndView = new ModelAndView(INVALID_TOKEN);
    String[] paramsArray = null;
    try {
      if (StringUtil.isNullAndEmpty(t)) {
        modelAndView.addObject(Constants.TOKEN_ERROR, messageSource
            .getMessage("chatak.admin.invalid.token", null, LocaleContextHolder.getLocale()));
        return modelAndView;
      }
      paramsArray = StringUtil.parseEmailToken(t);
      String userId = paramsArray[1];
      String email = paramsArray[Constants.TWO];
      if (!loginService.validToken(Long.parseLong(userId), t))
        throw new ChatakAdminException(messageSource.getMessage("chatak.admin.invalid.token", null,
            LocaleContextHolder.getLocale()));
      session.setAttribute(Constants.RESET_USER_ID, userId);
      session.setAttribute(Constants.RESET_EMAIL, email);
      ResetPasswordData resetPasswordData = new ResetPasswordData();
      model.put("resetPasswordData", resetPasswordData);
      modelAndView.setViewName(RESET_PSWD);
    } catch (ChatakAdminException e) {
      logger.error("Error:: LoginController:: getResetPassword method1" + e);
      model.put(Constants.ERROR, e.getMessage());
    } catch (Exception e) {
      logger.error("Error:: LoginController:: getResetPassword method2" + e);
      model.put(Constants.ERROR,
          messageSource.getMessage(Constants.CHATAK_GENERAL_ERROR, null, LocaleContextHolder.getLocale()));
    }
    logger.info("Exit:: LoginController:: getResetPassword method");
    return modelAndView;
  }

  @RequestMapping(value = PSWD_RESET, method = RequestMethod.POST)
  public ModelAndView resetPassword(Map model, HttpServletRequest request, HttpSession session,
      ResetPasswordData resetPasswordData) {
    logger.info("Entering:: LoginController:: resetPassword method");
    ModelAndView modelAndView = new ModelAndView(CHATAK_ADMIN_LOGIN);
    try {
      String userId = (String) session.getAttribute(Constants.RESET_USER_ID);
      loginService.resetPassword(resetPasswordData, Long.parseLong(userId));
      model.put(Constants.SUCESS, messageSource.getMessage("chatak.admin.reset.password.message",
          null, LocaleContextHolder.getLocale()));
      model.put(Constants.LOGIN_DETAILS, new LoginDetails());
      session.removeAttribute("tokenval");
      session = request.getSession();
      String token = CSRFTokenManager.getTokenForSession(session);
      session.setAttribute("tokenval", token);
    } catch (ChatakAdminException e) {
      logger.error("Error:: LoginController:: resetPassword method1" + e);
      modelAndView = new ModelAndView(RESET_PSWD);
      model.put(Constants.ERROR, e.getMessage());
    } catch (Exception e) {
      logger.error("Error:: LoginController:: resetPassword method2" + e);
      modelAndView = new ModelAndView(RESET_PSWD);
      model.put(Constants.ERROR,
          messageSource.getMessage(Constants.CHATAK_GENERAL_ERROR, null, LocaleContextHolder.getLocale()));
    }
    logger.info("Exit:: LoginController:: resetPassword method");
    return modelAndView;
  }

  @RequestMapping(value = ACCESS_INVALID, method = RequestMethod.GET)
  public ModelAndView accessInvalid(Map model, HttpServletRequest request,
      ForgotPasswordRequest forgotPasswordRequest) {
    logger.info("Entering:: LoginController:: submiPassword method");
    return new ModelAndView(ACCESS_INVALID);
  }

  @RequestMapping(value = CHATAK_INVALID_SESSION, method = RequestMethod.GET)
  public ModelAndView sessionInvalid(Map model, HttpServletRequest request) {
    logger.info("Entering:: LoginController:: sessionInvalid method");
    return new ModelAndView(CHATAK_INVALID_SESSION);
  }

  @RequestMapping(value = "switchToIssuance", method = RequestMethod.GET)
  public ModelAndView switchToIssuance(HttpServletRequest request, HttpServletResponse response,
      HttpSession session) {
    logger.info("Entering:: LoginController:: switchToIssuance method");
    ModelAndView modelAndView = new ModelAndView("issuance-loading");
    // To do getnerate token using logged in user session details
    modelAndView.addObject("token", "dummyToken");
    modelAndView.addObject("redirectUrl", Properties.getProperty("chatak-issuance.portal.url"));
    logger.info("Exit:: LoginController:: switchToIssuance method");
    return modelAndView;
  }

  @RequestMapping(value = CHATAK_ADMIN_AUTHENTICATE, method = RequestMethod.GET)
  public ModelAndView accessAuthenticateInvalid(HttpServletRequest request,
      HttpServletResponse response, HttpSession session, SessionInformation sessionInformation) {
    logger.info("Entering:: LoginController:: accessAuthenticateInvalid method");
    sessionInformation.expireNow();
    resetCookie(request, response, session);
    session.invalidate();
    return new ModelAndView(Constants.INVALID_SESSION);
  }
}
