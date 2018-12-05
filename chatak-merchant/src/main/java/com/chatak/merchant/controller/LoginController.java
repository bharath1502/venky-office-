/**
 * 
 */
package com.chatak.merchant.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
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

import com.chatak.merchant.constants.URLMappingConstants;
import com.chatak.merchant.controller.model.ForgotPasswordRequest;
import com.chatak.merchant.controller.model.LoginDetails;
import com.chatak.merchant.controller.model.LoginResponse;
import com.chatak.merchant.exception.ChatakMerchantException;
import com.chatak.merchant.model.GetMerchantDetailsResponse;
import com.chatak.merchant.model.TransactionListResponse;
import com.chatak.merchant.service.AccountService;
import com.chatak.merchant.service.LoginService;
import com.chatak.merchant.service.MerchantProfileService;
import com.chatak.merchant.service.RestPaymentService;
import com.chatak.merchant.service.TransactionService;
import com.chatak.merchant.util.PasswordHandler;
import com.chatak.merchant.util.StringUtil;
import com.chatak.pg.acq.dao.AdminUserDao;
import com.chatak.pg.acq.dao.MerchantDao;
import com.chatak.pg.acq.dao.MerchantProfileDao;
import com.chatak.pg.acq.dao.MerchantUserDao;
import com.chatak.pg.acq.dao.model.PGAccount;
import com.chatak.pg.acq.dao.model.PGAdminUser;
import com.chatak.pg.acq.dao.model.PGMerchant;
import com.chatak.pg.acq.dao.model.PGMerchantUsers;
import com.chatak.pg.constants.AccountTransactionCode;
import com.chatak.pg.constants.PGConstants;
import com.chatak.pg.exception.HttpClientException;
import com.chatak.pg.model.AccountBalanceDTO;
import com.chatak.pg.model.AccountTransactionDTO;
import com.chatak.pg.model.CurrencyDTO;
import com.chatak.pg.user.bean.GetTransactionsListRequest;
import com.chatak.pg.user.bean.GetTransactionsListResponse;
import com.chatak.pg.user.bean.MerchantAccountHistory;
import com.chatak.pg.util.CommonUtil;
import com.chatak.pg.util.Constants;
import com.chatak.pg.util.Properties;
import com.chatak.pg.util.validator.CSRFTokenManager;

/**
 * << Add Comments Here >>
 * 
 * @author Girmiti Software
 * @date 20-Feb-2015 7:00:40 PM
 * @version 1.0
 */
@SuppressWarnings({"rawtypes", "unchecked"})
@Controller
public class LoginController implements URLMappingConstants {

  private Logger logger = Logger.getLogger(LoginController.class);

  @Autowired
  private LoginValidator loginValidator;

  @Autowired
  private LoginService loginSevice;

  @Autowired
  private SessionRegistryImpl sessionRegistry;

  @Autowired
  private TransactionService transactionService;

  @Autowired
  private RestPaymentService paymentService;

  @Autowired
  AccountService accountService;

  @Autowired
  MerchantProfileService merchantProfileService;

  @Autowired
  private MerchantUserDao merchantUserDao;

  @Autowired
  private MerchantDao merchantDao;

  @Autowired
  private MessageSource messageSource;

  @Autowired
  MerchantProfileDao merchantProfileDao;
  
  @Autowired
  private AdminUserDao adminUserDao;

  /**
   * Method to show Login Page
   * 
   * @param request
   * @param response
   * @param session
   * @param mId
   * @return
   */
  @RequestMapping(value = CHATAK_MERCHANT_LOGIN, method = RequestMethod.GET)
  public ModelAndView showLogin(HttpServletRequest request, HttpServletResponse response,
      HttpSession session, Map model) {
    logger.info("Entering :: LoginController :: showLogin method");

    ModelAndView modelAndView = new ModelAndView(CHATAK_MERCHANT_LOGIN);

    try {
      LoginDetails loginDetails = new LoginDetails();
      model.put("loginDetails", loginDetails);
      
      session.setAttribute(Constants.ERROR, null);
      session.setAttribute(Constants.SUCESS, null);
    } catch (Exception e) {
      modelAndView.addObject(Constants.ERROR,
          messageSource.getMessage("chatak.general.error", null, LocaleContextHolder.getLocale()));
      logger.error("Constants.ERROR :: LoginController :: showLogin method", e);

    }
    model.put("currentBuildRelease", Properties.getProperty("current.release.version"));
    String token = CSRFTokenManager.getTokenForSession(session);
    logger.info("Genarated CSRF token before login : " + token);
    session.setAttribute("tokenval", token);
    logger.info("Exiting:: LoginController:: showLogin method");
    return modelAndView;
  }

  /**
   * Method to authenticate Merchant
   * 
   * @param request
   * @param response
   * @param session
   * @param model
   * @param loginDetails
   * @param bindingResult
   * @return
   */
  @RequestMapping(value = CHATAK_MERCHANT_AUTHENTICATE, method = RequestMethod.POST)
  public ModelAndView authenticateUser(HttpServletRequest request, HttpServletResponse response,
      HttpSession session, Map model, LoginDetails loginDetails, BindingResult bindingResult) {

    logger.info("Entering :: LoginController :: authenticateUser method");
    ModelAndView modelAndView = new ModelAndView(CHATAK_MERCHANT_LOGIN);
    String userAgent = request.getHeader("user-agent");
    try {
      modelAndView.addObject(Constants.ERROR, null);
      session.setAttribute(Constants.ERROR, null);
      loginValidator.validate(loginDetails, bindingResult);
      if (bindingResult.hasErrors()) {
        getError(bindingResult, modelAndView);
        return modelAndView;
      }
      loginDetails.setLoginIpAddress(request.getRemoteAddr());//Setting remote ipaddress
      userAgent = removeWhiteSpace(userAgent);

      //to maintain current login time
      Date today = new Date();
      DateFormat df = new SimpleDateFormat(Constants.TIMEZONE_DATE_FORMAT);
      df.setTimeZone(TimeZone.getTimeZone(loginDetails.getTimeZoneRegion()));
      loginDetails.setCurrentLoginTime(df.format(today));

      LoginResponse loginResponse = loginSevice.authenticate(loginDetails);
      getChatakException(loginResponse);
      if (null != loginResponse.getUserType()
          && loginResponse.getUserType().equals(PGConstants.NEW_USER)) {
        session.setAttribute(Constants.LOING_USER_ID, loginResponse.getUserId());
        session.setAttribute("loginUserType", loginResponse.getUserType());
        modelAndView = changePassword(model, session);
      } else if (null != loginResponse.getUserMerchantId()
          && null != loginResponse.getExistingFeature()) {
        String existngFeatues = "";
        existngFeatues = loginExistingFeature(loginResponse, existngFeatues);
        session.setAttribute("existingFeatues", existngFeatues);
        session.setAttribute("userType", loginResponse.getUserType());
        modelAndView.addObject("userType", loginResponse.getUserType());
        session.setAttribute("existingFeatuesData", loginResponse.getExistingFeature());
        session.setAttribute("merchantCode", loginResponse.getMerchantCode());
        for (Object object : sessionRegistry.getAllPrincipals()) {
        	validateLoginResponseData(request, response, session, loginDetails, modelAndView, userAgent, object);

        }
        session.setAttribute(CSRFTokenManager.CSRF_TOKEN_FOR_SESSION_ATTR_NAME,
	  			(String) session.getAttribute(CSRFTokenManager.CSRF_TOKEN_FOR_SESSION_ATTR_NAME));

        getStatusResponse(response, session, loginDetails, modelAndView, userAgent, loginResponse);
      } else if (loginResponse.getErrorCode().equals(Constants.ERROR_CODE)) {
        modelAndView.addObject(Constants.ERROR, loginResponse.getMessage());
      } else {
        modelAndView = populateChangePasswordModel(session, model, loginResponse);
        return modelAndView;
      }
    } catch (ChatakMerchantException e) {
      logger.error("ERROR:: LoginController:: authenticateUser method", e);
      modelAndView.addObject(Constants.ERROR, e.getMessage());
    } catch (HttpClientException e) {
        logger.error("ERROR:: LoginController:: authenticateUser method :: HttpClientException ", e);
        modelAndView.setViewName(Constants.CHATAK_INVALID_ACCESS);
		return modelAndView;
    } catch (Exception e) {
      logger.error("ERROR:: LoginController:: authenticateUser method", e);
      modelAndView.addObject(Constants.ERROR, messageSource.getMessage(
          "chatak.merchant.login.error.message", null, LocaleContextHolder.getLocale()));
    }
    logger.info("Exiting:: LoginController:: authenticateUser method");
    return modelAndView;
  }

	private ModelAndView validateLoginResponseData(HttpServletRequest request, HttpServletResponse response,
			HttpSession session, LoginDetails loginDetails, ModelAndView modelAndView, String userAgent, Object object)
			throws HttpClientException {
		String exIpAdress = loginDetails.getLoginIpAddress();
		LoginDetails loginResponseData = (LoginDetails) object;
		if (null != loginResponseData && loginResponseData.getAcqU().trim().equals(loginDetails.getAcqU())) {
			Cookie[] cookies = request.getCookies();
			String cookieValue = "";
			cookieValue = getCookiesValue(cookies, cookieValue);
			getInvalidSessionResponse(response, loginDetails, userAgent, modelAndView, loginResponseData,
					cookieValue, object, exIpAdress, session);
		}
		return modelAndView;
	}
  
	private ModelAndView getInvalidSessionResponse(HttpServletResponse response, LoginDetails loginDetails,
			String userAgent, ModelAndView modelAndView, LoginDetails loginResponseData, String cookieValue,
			Object object,String exIpAdress,HttpSession session) throws HttpClientException {
		String encUName = PasswordHandler.encrypt(loginDetails.getAcqU());
		SessionInformation sessionInformation = sessionRegistry.getSessionInformation(encUName);

		if (null == sessionInformation) {
			modelAndView.setViewName(Constants.CHATAK_INVALID_ACCESS);
			return modelAndView;
		}else if (loginResponseData.getLoginIpAddress().equals(exIpAdress)
				&& null != session.getAttribute("exUserAgent")
				&& session.getAttribute("exUserAgent").equals(userAgent)) {
			return modelAndView;
		}else {
			if (!loginResponseData.getjSession().equals(userAgent + cookieValue)) {
				Date lastSessionRequestDate = sessionInformation.getLastRequest();
				Date lastRequestDate = new Date(lastSessionRequestDate.getTime());
				lastRequestDate.setTime(lastRequestDate.getTime()
						+ (Integer.parseInt(messageSource.getMessage("chatak.merchant.session.timeout", null,
								LocaleContextHolder.getLocale())) * Constants.TIME_OUT));
				Date curDate = new Date();
				if (lastRequestDate.after(curDate)) {
					throw new HttpClientException(Constants.CHATAK_INVALID_SESSION,01);
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
			sessionInformation.refreshLastRequest();
		}
		return modelAndView;
	}

	/**
	 * @param response
	 * @param session
	 * @param loginDetails
	 * @param modelAndView
	 * @param userAgent
	 * @param loginResponse
	 * @return
	 */
	private ModelAndView getStatusResponse(HttpServletResponse response, HttpSession session, LoginDetails loginDetails,
			ModelAndView modelAndView, String userAgent, LoginResponse loginResponse) {
		if (loginResponse.getStatus() != null && loginResponse.getStatus()) {
			setLoginSuccessResponse(response, session, modelAndView, loginResponse, loginDetails,
					userAgent);
			session.setAttribute("loginResponse", loginResponse);
		}
		return modelAndView;
	}

	/**
	 * @param cookies
	 * @param cookieValue
	 * @return
	 */
	private String getCookiesValue(Cookie[] cookies, String cookieValue) {
		for (Cookie cookie : cookies) {
			if (Constants.COOKIE_CHATAK_NAME.equalsIgnoreCase(cookie.getName())) {
				cookieValue = cookie.getValue();
				break;
			}
		}
		return cookieValue;
	}

	/**
	 * @param loginResponse
	 * @param existngFeatues
	 * @return
	 */
	private String loginExistingFeature(LoginResponse loginResponse, String existngFeatues) {
		for (String str : loginResponse.getExistingFeature()) {
			existngFeatues += str;
		}
		return existngFeatues;
	}

	/**
	 * @param loginResponse
	 * @throws ChatakMerchantException
	 */
	private void getChatakException(LoginResponse loginResponse) throws ChatakMerchantException {
		if (loginResponse == null) {
			throw new ChatakMerchantException(messageSource.getMessage("chatak.merchant.login.error.message", null,
					LocaleContextHolder.getLocale()));
		}
	}

	/**
	 * @param userAgent
	 * @return
	 */
	private String removeWhiteSpace(String userAgent) {
		if (userAgent != null) {
			userAgent = userAgent.replaceAll("\\ ", "");
		}
		return userAgent;
	}

  private ModelAndView populateChangePasswordModel(HttpSession session, Map model,
      LoginResponse loginResponse) {
    logger.info("LoginController::login::false::status");
    ModelAndView modelAndView = new ModelAndView(CHATAK_MERCHANT_LOGIN);
    modelAndView.addObject(Constants.ERROR, StringUtil.isNullEmpty(loginResponse.getMessage())
        ? loginResponse.getErrorMessage() : loginResponse.getMessage());

    if (loginResponse.getErrorMessage() == Properties
        .getProperty("chatak.merchant.login.password.expiration.error.message")) {
      session.setAttribute(Constants.LOING_USER_ID, loginResponse.getUserId());
      modelAndView = changePassword(model, session);
      modelAndView.addObject(Constants.ERROR,
          messageSource.getMessage("chatak.merchant.login.password.expiration.error.message", null,
              LocaleContextHolder.getLocale()));
      logger.info("Exiting:: LoginController:: authenticateUser :: method Password Expired");
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
    ModelAndView modelAndView = new ModelAndView(INVALID_REQUEST_PAGE);
    logger.info("Exiting:: LoginController:: reloadErrorPage method");
    return modelAndView;
  }

  /**
   * Binding the error object add to the model value
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
  @SuppressWarnings("unused")
  private ModelAndView resetSessionRegistry(ModelAndView modelAndView,
      SessionInformation sessionInformation, HttpServletRequest request,
      HttpServletResponse response, HttpSession session) {
    sessionInformation.expireNow();
    resetCookie(request, response, session);
    session.invalidate();
    modelAndView.setViewName(Constants.INVALID_SESSION);
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
    session.setAttribute(CHATAK_ADMIN_USER_NAME, loginResponse.getUserName());
    session.setAttribute(Constants.LOGIN_RESPONSE_DATA, loginResponse);
    session.setAttribute(Constants.ERROR, null);
    session.setAttribute(Constants.LOING_USER_ID, loginResponse.getUserId());
    session.setAttribute(Constants.LOGIN_ROLE_ID, loginResponse.getUserRoleId());
    session.setAttribute(Constants.LOGIN_USER_TYPE, loginResponse.getUserType());
    session.setAttribute(Constants.LOGIN_USER_PARENT_ID, loginResponse.getParentMerchantId());
    session.setAttribute(Constants.LOGIN_USER_MERCHANT_ID, loginResponse.getUserMerchantId());
    // Widget page data loading
    modelAndView.setViewName(CHATAK_MERCHANT_DASH_BOARD);
    logger.info("Entering:: LoginController:: setLoginSuccessResponse method");
      Long merchantId = (Long) session.getAttribute(Constants.LOGIN_USER_MERCHANT_ID);
      GetMerchantDetailsResponse merchantDetailsResponse;
      GetTransactionsListRequest transaction = new GetTransactionsListRequest();
      GetTransactionsListRequest manualTransactionRequest = new GetTransactionsListRequest();
      TransactionListResponse transactionResponse = new TransactionListResponse();
      if (merchantId != null) {

        try {
          merchantDetailsResponse =
              paymentService.getMerchantIdAndTerminalId(merchantId.toString());
          PGAccount account =
              accountService.getAccountDetailsByEntityId(merchantDetailsResponse.getMerchantId());
          modelAndView.addObject(Constants.MERCHANT_BUSINESS_NAME,
                  merchantDetailsResponse.getBusinessName());
          session.setAttribute(Constants.MERCHANT_BUSINESS_NAME,
              merchantDetailsResponse.getBusinessName());
          AccountBalanceDTO accountDetails = new AccountBalanceDTO();
          accountDetails.setEntityId(account.getEntityId());
          accountDetails.setAccountNum(account.getAccountNum());
          CurrencyDTO currencyConfig = accountService.getCurrencyConfigOnCurrencyCodeAlpha(account.getCurrency());
          accountDetails.setAvailableBalanceString(
        		  CommonUtil.formatAmountOnCurrency(account.getAvailableBalance().toString(),
        				  currencyConfig.getCurrencyExponent(), currencyConfig.getCurrencySeparatorPosition(),
        				  currencyConfig.getCurrencyMinorUnit(), currencyConfig.getCurrencyThousandsUnit()));
          accountDetails.setCurrentBalanceString(
        		  CommonUtil.formatAmountOnCurrency(account.getCurrentBalance().toString(),
        				  currencyConfig.getCurrencyExponent(), currencyConfig.getCurrencySeparatorPosition(),
        				  currencyConfig.getCurrencyMinorUnit(), currencyConfig.getCurrencyThousandsUnit()));
          
          modelAndView.addObject("accountDetails", accountDetails);
          modelAndView.addObject("currencyAlpha", account.getCurrency());
          session.setAttribute("accountDetails", accountDetails);

          transaction.setMerchant_code(merchantDetailsResponse.getMerchantId());

          List<String> txnCodeList = new ArrayList<>(Constants.ELEVEN);
          txnCodeList.add(AccountTransactionCode.CC_AMOUNT_CREDIT);
          txnCodeList.add(AccountTransactionCode.CC_AMOUNT_DEBIT);
          txnCodeList.add(AccountTransactionCode.FT_BANK);
          txnCodeList.add(AccountTransactionCode.FT_CHECK);
          txnCodeList.add(AccountTransactionCode.CC_FEE_CREDIT);
          txnCodeList.add(AccountTransactionCode.CC_FEE_DEBIT);
          txnCodeList.add(AccountTransactionCode.EFT_DEBIT);
          txnCodeList.add(AccountTransactionCode.ACCOUNT_CREDIT);
          txnCodeList.add(AccountTransactionCode.ACCOUNT_DEBIT);
          transaction.setTransactionCodeList(txnCodeList);

          transaction.setSettlementStatus(PGConstants.PG_SETTLEMENT_PROCESSING);
          logger.info("LoginController:: fetching processing txn");
          GetTransactionsListResponse processingTxnList =
              transactionService.searchAccountTransactions(transaction);
          //fetching entityId and entityType
          PGAdminUser pgAdminUser  = adminUserDao.findByAdminUserId(Long.valueOf(merchantDetailsResponse.getCreatedBy()));
          if(null != pgAdminUser){
        	  transaction.setEntityId(pgAdminUser.getEntityId());
        	  transaction.setUserType(pgAdminUser.getUserType());
          }
          transaction.setSettlementStatus(PGConstants.PG_SETTLEMENT_EXECUTED);
          GetTransactionsListResponse executedTxnList = null;
          
          if (transaction.getUserType().equals(Constants.PM_USER_TYPE)
        		  || transaction.getUserType().equals(Constants.ISO_USER_TYPE)) {
        	  logger.info("LoginController:: fetching executed txn for entityType");
        	  executedTxnList = transactionService.searchAccountTransactionsForEntityId(transaction, transaction.getEntityId(), transaction.getUserType());
          } else {
        	  logger.info("LoginController:: fetching executed txn for Merchant");
        	  executedTxnList = transactionService.searchAccountTransactions(transaction);
          }
          
          List<AccountTransactionDTO> transactionList = fetchProcessingTxnList(session, modelAndView, transactionResponse,
              processingTxnList, executedTxnList);

          manualTransactionRequest.setAcqChannel("web");
          List<String> manualTxnCodeList = new ArrayList<>(Integer.parseInt("2"));
          manualTxnCodeList.add(AccountTransactionCode.MANUAL_CREDIT);
          manualTxnCodeList.add(AccountTransactionCode.MANUAL_DEBIT);
          manualTransactionRequest.setTransactionCodeList(manualTxnCodeList);
          manualTransactionRequest.setSettlementStatus(PGConstants.PG_SETTLEMENT_EXECUTED);
          GetTransactionsListResponse manualTransactionsReportList = null;
          
          if (transaction.getUserType().equals(Constants.PM_USER_TYPE)
        		  || transaction.getUserType().equals(Constants.ISO_USER_TYPE)) {
        	  logger.info("LoginController:: fetching manual txn for entityType");
        	  manualTransactionsReportList = transactionService.searchAccountTransactionsForEntityId(manualTransactionRequest, transaction.getEntityId(), transaction.getUserType());
          } else {
        	  logger.info("LoginController:: fetching manual txn for Merchant");
        	  manualTransactionsReportList = transactionService.searchAccountTransactions(manualTransactionRequest);
          }
          
          PGMerchant parentMerchant = merchantProfileDao.getMerchantById(merchantId);
          List<PGMerchant> subMerchantList = merchantDao.findById(merchantId);
          List<String> merchantCodeList = new ArrayList<>();
          merchantCodeList.add(parentMerchant.getMerchantCode());
          if (CommonUtil.isListNotNullAndEmpty(subMerchantList)) {
            getSubMerchantCodes(subMerchantList, merchantCodeList);
          }
          String merchantCodes = StringUtils.join(merchantCodeList, "|");

          if (null != manualTransactionsReportList
              && null != manualTransactionsReportList.getAccountTransactionList()) {

            fetchManualTransactionsReport(session, modelAndView, manualTransactionsReportList, merchantCodes);

          } else {
            modelAndView.addObject(Constants.MANUAL_TRANSACTIONS_LIST_SIZE, 0);
            session.setAttribute(Constants.MANUAL_TRANSACTIONS_LIST_SIZE, 0);
            modelAndView.addObject(Constants.MANUAL_TXN_LIST, transactionList);
            session.setAttribute(Constants.MANUAL_TXN_LIST, transactionList);
          }
          modelAndView.addObject("accountHistoryList", new ArrayList<MerchantAccountHistory>());

          PGMerchantUsers pgMerchantUsers =
              merchantUserDao.findByMerchantUserId(loginResponse.getUserId());
          PGMerchant pgMerchant =
              merchantProfileDao.getMerchantById(pgMerchantUsers.getPgMerchantId());
          modelAndView.addObject("paypage", pgMerchant.getMerchantConfig().getPayPageConfig());
          session.setAttribute("paypage", pgMerchant.getMerchantConfig().getPayPageConfig());

        } catch (Exception e) {
          modelAndView.setViewName(INVALID_REQUEST_PAGE);
          logger.info("Error:: LoginController:: setLoginSuccessResponse method", e);
          return modelAndView;
        }
      }
    logger.info("LoginController:: fetching features");
    session.setAttribute("loginResponse", loginResponse);
    String existingFeatures = "";
    for (String feature : loginResponse.getExistingFeature()) {
      existingFeatures += "|" + feature;
    }
    existingFeatures += "|";
    session.setAttribute(Constants.EXISTING_FEATURE_DATA, loginResponse.getExistingFeature());
    session.setAttribute(Constants.EXISTING_FEATURES, existingFeatures);
    // Service code
    String encUName = PasswordHandler.encrypt(loginDetails.getAcqU());
    Cookie myCookie = new Cookie(Constants.COOKIE_CHATAK_NAME, encUName + session.getId());
    myCookie.setMaxAge(Constants.MAX_AGE);
    response.addCookie(myCookie);
    loginDetails.setjSession(userAgent + encUName + session.getId());
    session.setAttribute("exUserAgent", userAgent);
    // Registering logged in user to Spring Session registry
    sessionRegistry.registerNewSession(encUName, loginDetails);
    logger.info("Exiting:: LoginController:: setLoginSuccessResponse method");
    return modelAndView;
  }

  private void getSubMerchantCodes(List<PGMerchant> subMerchantList,
      List<String> merchantCodeList) {
    for (PGMerchant subMerchant : subMerchantList) {
      merchantCodeList.add(subMerchant.getMerchantCode());
    }
  }

  private void fetchManualTransactionsReport(HttpSession session, ModelAndView modelAndView,
      GetTransactionsListResponse manualTransactionsReportList, String merchantCodes) {
  List<AccountTransactionDTO> accountTransactionDTOList =
      manualTransactionsReportList.getAccountTransactionList();
  Iterator accountTransactionDTOListItr = accountTransactionDTOList.iterator();
  List<AccountTransactionDTO> accountTransactionDTOs = new ArrayList<>();
  while (accountTransactionDTOListItr.hasNext()) {
    AccountTransactionDTO accountTransactionDTO =
        (AccountTransactionDTO) accountTransactionDTOListItr.next();
    if (merchantCodes.contains(accountTransactionDTO.getMerchantCode())) {
      accountTransactionDTOs.add(accountTransactionDTO);
    }
  }
  manualTransactionsReportList.setAccountTransactionList(accountTransactionDTOs);

  if (manualTransactionsReportList.getAccountTransactionList()
      .size() > Constants.MAX_ENTITY_DISPLAY_SIZE) {
    session.setAttribute(Constants.MANUAL_TRANSACTIONS_REPORT_LIST,
        manualTransactionsReportList.getAccountTransactionList().subList(0,
            Constants.MAX_ENTITY_DISPLAY_SIZE));
    modelAndView.addObject(Constants.MANUAL_TRANSACTIONS_REPORT_LIST,
        manualTransactionsReportList.getAccountTransactionList().subList(0,
            Constants.MAX_ENTITY_DISPLAY_SIZE));
  } else {
    session.setAttribute(Constants.MANUAL_TRANSACTIONS_REPORT_LIST,
        manualTransactionsReportList.getAccountTransactionList());
    modelAndView.addObject(Constants.MANUAL_TRANSACTIONS_REPORT_LIST,
        manualTransactionsReportList.getAccountTransactionList());
  }
  modelAndView.addObject(Constants.MANUAL_TRANSACTIONS_LIST_SIZE,
      manualTransactionsReportList.getAccountTransactionList().size());
  session.setAttribute(Constants.MANUAL_TRANSACTIONS_LIST_SIZE,
      manualTransactionsReportList.getAccountTransactionList().size());
}

private List<AccountTransactionDTO> fetchProcessingTxnList(HttpSession session, ModelAndView modelAndView,
      TransactionListResponse transactionResponse,
      GetTransactionsListResponse processingTxnList, GetTransactionsListResponse executedTxnList) {
  List<AccountTransactionDTO> transactionList = null;
  if (null != processingTxnList && null != processingTxnList.getAccountTransactionList()) {

      int listSize = processingTxnList.getAccountTransactionList().size();
      transactionResponse.setAccountTxnList(listSize < Constants.MAX_ENTITY_DISPLAY_SIZE
          ? processingTxnList.getAccountTransactionList()
          : processingTxnList.getAccountTransactionList().subList(0,
              Constants.MAX_ENTITY_DISPLAY_SIZE));
      transactionResponse.setErrorCode(processingTxnList.getErrorCode());
      transactionResponse.setErrorMessage(processingTxnList.getErrorMessage());
      transactionResponse.setTotalNoOfRows(processingTxnList.getTotalResultCount());
      transactionList = transactionResponse.getAccountTxnList() != null
          ? transactionResponse.getAccountTxnList() : new ArrayList<AccountTransactionDTO>();
      modelAndView.addObject(Constants.PROCESSING_LIST_SIZE, listSize);
      session.setAttribute(Constants.PROCESSING_LIST_SIZE, listSize);
      modelAndView.addObject(Constants.PROCESSING_TXN_LIST, transactionList);
      session.setAttribute(Constants.PROCESSING_TXN_LIST, transactionList);
      modelAndView.addObject(Constants.MODEL_ATTRIBUTE_PORTAL_TOTAL_RECORDS_PAGE_NUM, processingTxnList.getTotalResultCount());
    } else {
      modelAndView.addObject(Constants.PROCESSING_LIST_SIZE, 0);
      session.setAttribute(Constants.PROCESSING_LIST_SIZE, 0);
      modelAndView.addObject(Constants.PROCESSING_TXN_LIST, transactionList);
      session.setAttribute(Constants.PROCESSING_TXN_LIST, transactionList);
    }
    if (null != executedTxnList && null != executedTxnList.getAccountTransactionList()) {

      int listSize = executedTxnList.getAccountTransactionList().size();
      transactionResponse.setAccountTxnList(listSize < Constants.MAX_ENTITY_DISPLAY_SIZE
          ? executedTxnList.getAccountTransactionList()
          : executedTxnList.getAccountTransactionList().subList(0,
              Constants.MAX_ENTITY_DISPLAY_SIZE));
      transactionList = transactionResponse.getAccountTxnList() != null
          ? transactionResponse.getAccountTxnList() : new ArrayList<AccountTransactionDTO>();
      modelAndView.addObject(Constants.EXECUTED_LIST_SIZE, listSize);
      session.setAttribute(Constants.EXECUTED_LIST_SIZE, listSize);
      modelAndView.addObject(Constants.EXECUTED_TXN_LIST, transactionList);
      session.setAttribute(Constants.EXECUTED_TXN_LIST, transactionList);
      modelAndView.addObject(Constants.MODEL_ATTRIBUTE_PORTAL_TOTAL_RECORDS_PAGE_NUM, executedTxnList.getTotalResultCount());
    } else {
      modelAndView.addObject(Constants.EXECUTED_LIST_SIZE, 0);
      session.setAttribute(Constants.EXECUTED_LIST_SIZE, 0);
      modelAndView.addObject(Constants.EXECUTED_TXN_LIST, transactionList);
      session.setAttribute(Constants.EXECUTED_TXN_LIST, transactionList);
    }
     modelAndView.addObject(Constants.MODEL_ATTRIBUTE_PORTAL_LIST_PAGE_NUMBER, Constants.ONE);
  return transactionList;
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
  @RequestMapping(value = CHATAK_MERCHANT_LOG_OUT, method = RequestMethod.GET)
  public ModelAndView logout(HttpServletRequest request, HttpServletResponse response, Map model,
      HttpSession session) {
    logger.info("Entering:: LoginController:: logout method");
    try {
      resetCookie(request, response, session);
    } catch (Exception e) {
      logger.error("ERROR:: LoginController:: logout method", e);
    }
    session.invalidate();
    ModelAndView modelAndView = new ModelAndView(CHATAK_MERCHANT_LOG_OUT);
    logger.info("Exiting:: LoginController:: logout method");
    return modelAndView;
  }

  @RequestMapping(value = CHATAK_INVALID_ACCESS, method = RequestMethod.GET)
  public ModelAndView accessInvalid(Map model, HttpServletRequest request) {
    logger.info("Entering:: LoginController:: submiPassword method");
    return new ModelAndView(CHATAK_INVALID_ACCESS);
  }

  @RequestMapping(value = CHATAK_INVALID_SESSION, method = RequestMethod.GET)
  public ModelAndView sessionInvalid(Map model, HttpServletRequest request) {
    logger.info("Entering:: LoginController:: submiPassword method");
    return new ModelAndView(CHATAK_INVALID_SESSION);
  }

  public ModelAndView changePassword(Map model, HttpSession session) {
    logger.info("Entering:: LoginController:: forgotPassword method");
    ForgotPasswordRequest forgotPasswordRequest = new ForgotPasswordRequest();

    // If user came from a change password case when still not logged into the portal,
    // Ex. from forgot password scenario, set the new user attribute.
    String loginUserType = (String) session.getAttribute(Constants.LOGIN_USER_TYPE);
    if (loginUserType != null && loginUserType.equals(PGConstants.NEW_USER)) {
      forgotPasswordRequest.setIsNewUser(true);
    }
    model.put("forgotPasswordRequest", forgotPasswordRequest);
    ModelAndView modelAndView = new ModelAndView(NEW_USER_PSWD_MANAGEMENT);
    logger.info("Exit:: LoginController:: forgotPassword method");

    return modelAndView;
  }

  @RequestMapping(value = CHATAK_MERCHANT_AUTHENTICATE, method = {RequestMethod.GET})
  public ModelAndView authenticateLogin(HttpServletRequest request,
          HttpServletResponse response, HttpSession httpSession,
          LoginDetails loginDetails, BindingResult bindingResult, Map model) {
      logger.info("Entering:: LoginController:: authenticateLogin method");
      ModelAndView modelAndView = new ModelAndView(CHATAK_MERCHANT_LOGIN);
      //Do nothing... This method is used to handle refresh on dashboard
      
      String user = (String) httpSession.getAttribute(CHATAK_ADMIN_USER_NAME);
      logger.info("Entering:: LoginController:: authenticateLogin method");
      if (user != null) {
        modelAndView.addObject(Constants.MERCHANT_BUSINESS_NAME,
            httpSession.getAttribute(Constants.MERCHANT_BUSINESS_NAME));
        modelAndView.setViewName(CHATAK_MERCHANT_DASH_BOARD);
      }
      return modelAndView;
  }
}
