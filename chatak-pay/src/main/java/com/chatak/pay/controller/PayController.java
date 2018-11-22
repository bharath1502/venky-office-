/**
 * 
 */
package com.chatak.pay.controller;

import java.io.PrintWriter;
import java.security.KeyPair;
import java.util.List;
import java.util.UUID;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.chatak.pay.constants.ChatakPayErrorCode;
import com.chatak.pay.constants.Constant;
import com.chatak.pay.constants.PaymentProcessTypeEnum;
import com.chatak.pay.constants.URLMappingConstants;
import com.chatak.pay.controller.model.CardDetails;
import com.chatak.pay.controller.model.PaymentDetails;
import com.chatak.pay.exception.ChatakPayException;
import com.chatak.pay.processor.CardPaymentProcessor;
import com.chatak.pay.processor.TokenizationService;
import com.chatak.pay.util.AesCtr;
import com.chatak.pay.util.EncryptionUtil;
import com.chatak.pay.util.StringUtil;
import com.chatak.pg.acq.dao.PGParamsDao;
import com.chatak.pg.acq.dao.model.PGMerchant;
import com.chatak.pg.acq.dao.model.PGOnlineTxnLog;
import com.chatak.pg.acq.dao.model.PGParams;
import com.chatak.pg.util.Constants;
import com.chatak.switches.enums.TransactionType;
import com.chatak.switches.sb.util.ProcessorConfig;
import com.litle.sdk.generate.CountryTypeEnum;
import com.litle.sdk.generate.CurrencyCodeEnum;

/**
 * << Add Comments Here >>
 * 
 * @author Girmiti Software
 * @date 20-Feb-2015 7:00:40 PM
 * @version 1.0
 */
@Controller
public class PayController implements URLMappingConstants, Constant {

  private static Logger logger = LogManager.getLogger(PayController.class.getName());

  @Autowired
  private CardPaymentProcessor cardPaymentProcessor;

  @Autowired
  private DefaultTokenServices tokenServices;

  @Autowired
  private TokenizationService tokenizationService;
  
  @Autowired
  private PGParamsDao paramsDao;  

  @PostConstruct
  private void loadConfiguration() {
    List<PGParams> pgParams = paramsDao.getAllPGParams();
    ProcessorConfig.setProcessorConfiguration(pgParams);
  }

  /**
   * Method to show Payment Progress page
   * 
   * @param request
   * @param response
   * @param session
   * @param mId
   * @return
   */
  @RequestMapping(value = CHATAK_PAY_PAYMENT_PROCESING_PAGE_URI, method = RequestMethod.POST)
  public ModelAndView showPaymentProcessing(HttpServletRequest request,
                                            HttpServletResponse response,
                                            HttpSession session,
                                            @PathVariable String mId) {
    logger.debug("Entering:: PayController:: showPayment method");
    ModelAndView modelAndView = new ModelAndView(CHATAK_PAY_PAYMENT_PROCESSING_PAGE);

    try {
      PGMerchant pgMerchant = cardPaymentProcessor.validMerchant(mId);
      if(null != pgMerchant) {
        PaymentDetails paymentDetails = loadPaymentDetailsOnRequest(modelAndView, request, session, mId);
        paymentDetails.setMode(pgMerchant.getAppMode());
        paymentDetails.setProcessorMid(pgMerchant.getLitleMID());
        //Checking for duplicate txn
        cardPaymentProcessor.isDuplicateRequest(paymentDetails.getMerchantId(), paymentDetails.getOrderId());
        
        String modeType = (String) session.getAttribute(CHATAK_PAY_MODE_TYPE);

        if(null != modeType && PaymentProcessTypeEnum.T.name().equals(modeType)) {
          CardDetails cardDetails = tokenizationService.deTokenize(paymentDetails.getToken());
          session.setAttribute(paymentDetails.getToken(), cardDetails);
          cardDetails.setRequestId(cardDetails.getNumber().substring(cardDetails.getNumber().length() - Constants.FOUR));
          session.setAttribute("CARD", cardDetails.getRequestId());
        }

        session.setAttribute(CHATAK_PAY_SAVE_CARD_SESSION, request.getParameter(CHATAK_PAY_SAVE_CARD_SESSION));

        session.setAttribute(CHATAK_PAY_INIT_SESSION_ID_SESSION,
                             StringUtil.getPaymentSessionToken(request, session, mId));
        modelAndView.addObject(CHATAK_PAY_MERCHANT_ID, mId);
        UUID uuid = UUID.randomUUID();
        session.setAttribute(CHATAK_PAY_INIT_REQUEST_UUID_SESSION, uuid);
        PGOnlineTxnLog onlineTxnLog = cardPaymentProcessor.initializeCardPayment(paymentDetails, null);
        session.setAttribute(CHATAK_PAY_TXN_SESSION, onlineTxnLog);
        session.setAttribute(CHATAK_PAY_TXN_STATE_SESSION, PAYMENT_STEP_1);
      }
      else {
        logger.info("Unauthorized merchant! and returning.");
        modelAndView = new ModelAndView(INVALID_ACCESS);
        modelAndView.addObject(CHATAK_PAY_RETURN_URL, request.getParameter("returnURL"));
      }
    } catch (ChatakPayException e) {
      logger.error("ERROR :: PayController:: showPaymentProcessing ChatakPayException", e);
      modelAndView.addObject(MSG, ChatakPayErrorCode.TXN_0012.value());
      modelAndView.addObject(ERROR, e.getMessage());
    } catch (Exception e) {
      logger.error("ERROR :: PayController:: showPaymentProcessing method", e);
      modelAndView.addObject(ERROR, ChatakPayErrorCode.TXN_0997.value());
    }
    modelAndView.addObject(CHATAK_PAY_RETURN_URL, request.getParameter("returnURL"));
    logger.debug("Exiting :: PayController:: showPaymentProcessing method");
    return modelAndView;
  }

  /**
   * Method to show Payment Page
   * 
   * @param request
   * @param response
   * @param mId
   * @param paymentDetails
   * @return
   */
  @RequestMapping(value = CHATAK_PAY_PAYMENT_BILLING_PAGE_URI, method = RequestMethod.GET)
  public ModelAndView showPayment(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
    logger.debug("Entering:: PayController:: showPayment method");
    ModelAndView modelAndView = new ModelAndView(CHATAK_PAY_PAYMENT_BILLING_PAGE);
    try {
      //Need to implement based on requirement
    }
    catch(Exception e) {
      logger.error("ERROR:: PayController:: showPayment method", e);
    }
    logger.debug("Exiting:: PayController:: showPayment method");
    return modelAndView;
  }

  /**
   * Method to show ERROR Page
   * 
   * @param request
   * @param response
   * @param mId
   * @param paymentDetails
   * @return
   */
  @RequestMapping(value = CHATAK_PAY_ERROR_PAGE, method = RequestMethod.GET)
  public ModelAndView error(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
    logger.debug("Entering:: PayController:: error method");
    ModelAndView modelAndView = new ModelAndView(CHATAK_PAY_ERROR_PAGE);
    try {
      //Need to implement based on requirement
    }
    catch(Exception e) {
      logger.error("ERROR:: PayController:: error method", e);
    }
    logger.debug("Exiting:: PayController:: error method");
    return modelAndView;
  }

  /**
   * Method to show invalid access
   * 
   * @param request
   * @param response
   * @param session
   * @return
   */
  @RequestMapping(value = CHATAK_PAY_INVALID_ACCESS_PAGE, method = RequestMethod.GET)
  public ModelAndView invalidAccess(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
    logger.debug("Entering:: PayController:: invalidAccess method");
    ModelAndView modelAndView = new ModelAndView(INVALID_ACCESS);
    try {
      session.invalidate();
    }
    catch(Exception e) {
      logger.error("ERROR:: PayController:: invalidAccess method", e);
    }
    logger.debug("Exiting:: PayController:: invalidAccess method");
    return modelAndView;
  }

  /**
   * Method to show session expired
   * 
   * @param request
   * @param response
   * @param session
   * @return
   */
  @RequestMapping(value = CHATAK_PAY_SESSION_EXPIRED_PAGE, method = RequestMethod.GET)
  public ModelAndView sessionExpired(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
    logger.debug("Entering:: PayController:: sessionExpired method");
    ModelAndView modelAndView = new ModelAndView(UNKNOWN_ERROR);
    try {
      PaymentDetails paymentDetails = (PaymentDetails) session.getAttribute(CHATAK_PAY_SESSION);
      if(null != paymentDetails) {
        modelAndView.addObject(CHATAK_PAY_RETURN_URL, paymentDetails.getReturnURL());
      }
      session.removeAttribute(CHATAK_PAY_INIT_SESSION_ID_SESSION);
      session.removeAttribute(CHATAK_PAY_INIT_REQUEST_UUID_SESSION);
      session.removeAttribute(CHATAK_PAY_SESSION);
      session.removeAttribute(CHATAK_PAY_TXN_STATE_SESSION);
      session.removeAttribute(CHATAK_PAY_TXN_SESSION);
    }
    catch(Exception e) {
      logger.error("ERROR:: PayController:: sessionExpired method", e);
    }
    logger.debug("Exiting:: PayController:: sessionExpired method");
    return modelAndView;
  }

  /**
   * Method to show Payment Page
   * 
   * @param request
   * @param response
   * @param session
   * @param page
   * @return
   */
  @RequestMapping(value = CHATAK_PAY_CHECK_ACCESS_PAGE, method = RequestMethod.GET)
  public void checkValidSession(HttpServletRequest request,
                                HttpServletResponse response,
                                HttpSession session,
                                @PathVariable String page) {
    logger.debug("Entering:: PayController:: checkValidSession method");
    try {
      String respJSON = "";
      String processState = (String) session.getAttribute(CHATAK_PAY_TXN_STATE_SESSION);
      if(null == processState || !processState.equalsIgnoreCase(PAYMENT_STEP_4)) {
        respJSON = StringUtil.getStatusResponse(STATUS_F);
      }
      else if(INITIATE.equals(page) && processState.equalsIgnoreCase(PAYMENT_STEP_4)) {
        respJSON = StringUtil.getStatusResponse(STATUS_S);
      }
      else if((PROGRESS.equals(page) || COMPLETED.equals(page)) && processState.equalsIgnoreCase(PAYMENT_STEP_4)) {
        respJSON = StringUtil.getStatusResponse(STATUS_S);
      }
      else {
        respJSON = StringUtil.getStatusResponse(STATUS_F);
      }
      PrintWriter out = response.getWriter();
      out.print(respJSON);
    }
    catch(Exception e) {
      logger.error("ERROR:: PayController:: checkValidSession method", e);
    }
    logger.debug("Exiting:: PayController:: checkValidSession method");
  }

  /**
   * Method to encrypt keys
   * 
   * @param request
   * @param response
   * @param mId
   * @param paymentDetails
   * @return
   */
  @RequestMapping(value = CHATAK_PAY_ENC_URI, method = RequestMethod.GET)
  public void encrypt(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
    logger.debug("Entering:: PayController:: showPayment method");
    try {

      String processState = (String) session.getAttribute(CHATAK_PAY_TXN_STATE_SESSION);

      /** Generates a KeyPair for RSA **/
      String action = request.getParameter(CHATAK_PAY_ENC_ACTION);
      if(action != null && action.equals(CHATAK_PAY_ENC_GENERATE)) {

        // Checking the Flow state
        if(StringUtil.isNullEmpty(processState) || !processState.equalsIgnoreCase(PAYMENT_STEP_2)) {
          response.sendRedirect(INVALID_ACCESS);
          session.invalidate(); // Invalidating the session and returning
          return;
        }

        session.setAttribute(CHATAK_PAY_JS_CRYPT_KEYS_SESSION, (KeyPair)EncryptionUtil.getJCryptoKeyPair()[0]);
        PrintWriter out = response.getWriter();
        out.print(EncryptionUtil.getJCryptoKeyPair()[1].toString());
        session.setAttribute(CHATAK_PAY_TXN_STATE_SESSION, PAYMENT_STEP_3);
        return;
      }
      /** jCryption handshake **/
      else if(action != null && action.equals(CHATAK_PAY_ENC_HANDSHAKE)) {

        // Checking the Flow state
        if(StringUtil.isNullEmpty(processState) || !processState.equalsIgnoreCase(PAYMENT_STEP_3)) {
          response.sendRedirect(INVALID_ACCESS);
          session.invalidate(); // Invalidating the session and returning
          return;
        }

        String key = EncryptionUtil.decrypt((KeyPair) session.getAttribute(CHATAK_PAY_JS_CRYPT_KEYS_SESSION),
                                            request.getParameter(CHATAK_PAY_ENC_KEY_SESSION));
        session.removeAttribute(CHATAK_PAY_JS_CRYPT_KEYS_SESSION);
        session.setAttribute(CHATAK_PAY_JS_CRYPT_KEY_SESSION, key);

        /** Encrypts password using AES **/
        String ct = AesCtr.encrypt(key, key, Constants.TWO_FIVE_SIX);

        /** Sends response **/
        PrintWriter out = response.getWriter();
        out.print("{\"challenge\":\"" + ct + "\"}");
        session.setAttribute(CHATAK_PAY_TXN_STATE_SESSION, PAYMENT_STEP_4);
        return;
      }
    }
    catch(Exception e) {
      logger.error("ERROR:: PayController:: showPayment method", e);
    }
    logger.debug("Exiting:: PayController:: showPayment method");
  }

  /**
   * Method to show Payment capture Page
   * 
   * @param request
   * @param response
   * @param mId
   * @param paymentDetails
   * @return
   */
  @RequestMapping(value = CHATAK_PAY_PAYMENT_INFO_PAGE_URI, method = RequestMethod.GET)
  public ModelAndView showPaymentInfo(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
    logger.debug("Entering:: PayController:: showPaymentInfo method");
    ModelAndView modelAndView = new ModelAndView(CHATAK_PAY_PAYMENT_INFO_PAGE);
    try {
      String processState = (String) session.getAttribute(CHATAK_PAY_TXN_STATE_SESSION);

      // Checking the Flow state
      if(StringUtil.isNullEmpty(processState) || !processState.equalsIgnoreCase(PAYMENT_STEP_1)) {
        modelAndView.setViewName(INVALID_ACCESS);
        session.invalidate(); // Invalidating the session and returning
        return modelAndView;
      }

      PaymentDetails paymentDetails = (PaymentDetails) session.getAttribute(CHATAK_PAY_SESSION);
      String sessionId = (String) session.getAttribute(CHATAK_PAY_INIT_SESSION_ID_SESSION);
      UUID uuid = (UUID) session.getAttribute(CHATAK_PAY_INIT_REQUEST_UUID_SESSION);

      // Checking the Flow state and Session values
      if(null == paymentDetails
         || null == uuid
         || StringUtil.isNullEmpty(sessionId)
         || !sessionId.equalsIgnoreCase(StringUtil.getPaymentSessionToken(request,
                                                                          session,
                                                                          paymentDetails.getMerchantId()))) {
        modelAndView.setViewName(CHATAK_PAY_INVALID_ACCESS_PAGE);
        session.invalidate(); // Invalidating the session and returning
        return modelAndView;
      }
      modelAndView.addObject(CHATAK_PAY_INIT_REQUEST_ID, uuid.getMostSignificantBits());
      modelAndView.addObject(CHATAK_PAY_MERCHANT_ID, paymentDetails.getMerchantId());
      modelAndView.addObject(CHATAK_PAY_PASS_KEY_SESSION, uuid.toString());
      session.setAttribute(CHATAK_PAY_TXN_STATE_SESSION, PAYMENT_STEP_2);

    }
    catch(Exception e) {
      logger.error("ERROR:: PayController:: showPaymentInfo method", e);
    }
    logger.debug("Exiting:: PayController:: showPaymentInfo method");
    return modelAndView;
  }

  /**
   * Method to load payment details from request
   * 
   * @param modelAndView
   * @param request
   * @param session
   * @param mId
   * @return
   * @throws ChatakPayException
   */
  private PaymentDetails loadPaymentDetailsOnRequest(ModelAndView modelAndView,
                                                     HttpServletRequest request,
                                                     HttpSession session,
                                                     String mId) throws ChatakPayException {
    String totalAmount = request.getParameter("totalAmount");
    String merchantAmount = request.getParameter("merchantAmount");
    String transactionType = request.getParameter("transactionType");
    String orderId = request.getParameter("orderId");
    String description = request.getParameter("description");
    String billerName = request.getParameter("billerName");
    String address = request.getParameter("address");
    String address2 = request.getParameter("address2");
    String billerEmail = request.getParameter("billerEmail");
    String billerCity = request.getParameter("billerCity");
    String billerState = request.getParameter("billerState");
    String billerCountry = request.getParameter("billerCountry");
    String billerZip = request.getParameter("billerZip");
    String returnURL = request.getParameter("returnURL");
    String currency = request.getParameter("currency");
    String accessToken = request.getParameter("cot");

    String type = request.getParameter("type");
    String cardToken = null;
    String cardType = null;

    Long totAmt = Long.valueOf(totalAmount);
    Long merAmt = Long.valueOf(merchantAmount);

    if(totAmt <= 0L || merAmt < 0L) {
      modelAndView.addObject(ERROR, ChatakPayErrorCode.TXN_0003.value());
    } else if(StringUtil.isNullEmpty(orderId)) {
      modelAndView.addObject(ERROR, ChatakPayErrorCode.TXN_0002.value());
    } else if(StringUtil.isNullEmpty(accessToken)) {
      modelAndView.addObject(ERROR, ChatakPayErrorCode.TXN_0997.value());
    } else if(isValidBillData(billerName, address, billerEmail, billerCity, billerState, billerCountry,
        billerZip)) {
      modelAndView.addObject(ERROR, ChatakPayErrorCode.TXN_0005.value());
    } else if(StringUtil.isNullEmpty(returnURL)) {
      modelAndView.addObject(ERROR, ChatakPayErrorCode.TXN_0006.value());
    } else {
      PaymentProcessTypeEnum typeEnum = PaymentProcessTypeEnum.N;
      if(isValidType(type)) {
          if(PaymentProcessTypeEnum.C.name().equals(type)) {
            typeEnum = PaymentProcessTypeEnum.C;
          } else if(PaymentProcessTypeEnum.T.name().equals(type)) {
            cardToken = request.getParameter("ct");
            validateCardToken(cardToken);
            cardType = request.getParameter("cType");
            getCardType(request, cardType);
            session.setAttribute(CHATAK_PAY_CARD_TYPE, cardType);
            typeEnum = PaymentProcessTypeEnum.T;
          }
      }

      logger.info("Request Token: " + accessToken);
      OAuth2Authentication auth2Authentication = tokenServices.loadAuthentication(accessToken);
      validateRequestToken(auth2Authentication);

      PaymentDetails paymentDetails = new PaymentDetails(null,
                                                         TransactionType.valueOf(transactionType),
                                                         orderId,
                                                         totAmt,
                                                         merAmt,
                                                         null,
                                                         description,
                                                         billerName,
                                                         billerEmail,
                                                         billerCity,
                                                         billerState,
                                                         CountryTypeEnum.valueOf(billerCountry),
                                                         billerZip,
                                                         address,
                                                         address2,
                                                         mId,
                                                         returnURL,
                                                         (StringUtil.isNullEmpty(currency)) ? CurrencyCodeEnum.USD
                                                                                           : CurrencyCodeEnum.valueOf(currency),
                                                         typeEnum,
                                                         cardToken,
                                                         accessToken); // Default
      // USD
      // will
      // be
      // taken
      // if
      // no
      // currency
      paymentDetails.setClientIP(request.getRemoteAddr());
      session.setAttribute(CHATAK_PAY_SESSION, paymentDetails);
      session.setAttribute(CHATAK_PAY_MODE_TYPE, typeEnum.name());
      session.setAttribute(CHATAK_PAY_RETURN_URL, returnURL);
      modelAndView.addObject(CHATAK_PAY_RETURN_URL, returnURL);
      return paymentDetails;
    }
    throw new ChatakPayException(ChatakPayErrorCode.TXN_0997.value());
  }

  private boolean isValidType(String type) {
    return !StringUtil.isNullAndEmpty(type) && PaymentProcessTypeEnum.valueOf(type) != null;
  }

  private void validateRequestToken(
      OAuth2Authentication auth2Authentication)
          throws ChatakPayException {
    if(null == auth2Authentication || !auth2Authentication.isAuthenticated()) {
      logger.info("Invalid request token");
      throw new ChatakPayException(ChatakPayErrorCode.TXN_0997.value());
    }
  }

  private boolean isValidBillData(String billerName, String address, String billerEmail,
      String billerCity, String billerState, String billerCountry, String billerZip) {
    return StringUtil.isNullEmpty(billerName) || StringUtil.isNullEmpty(billerEmail)
            || StringUtil.isNullEmpty(billerCity) || StringUtil.isNullEmpty(billerState)
            || StringUtil.isNullEmpty(billerCountry) || StringUtil.isNullEmpty(billerZip)
            || StringUtil.isNullEmpty(address);
  }

  private void validateCardToken(String cardToken) throws ChatakPayException {
    if(StringUtil.isNullEmpty(cardToken)) {
      throw new ChatakPayException(ChatakPayErrorCode.TXN_0997.value());
    }
  }

  private String getCardType(HttpServletRequest request, String cardType) {
    if(StringUtil.isNullAndEmpty(cardType)) {
      cardType = request.getParameter("cardAssociation");
    }
    return cardType;
  }

}
