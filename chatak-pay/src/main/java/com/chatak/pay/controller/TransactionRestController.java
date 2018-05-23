/**
 * 
 */
package com.chatak.pay.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.ws.rs.core.MediaType;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.chatak.pay.constants.ChatakPayErrorCode;
import com.chatak.pay.constants.Constant;
import com.chatak.pay.constants.URLMappingConstants;
import com.chatak.pay.controller.model.CardData;
import com.chatak.pay.controller.model.LoginRequest;
import com.chatak.pay.controller.model.LoginResponse;
import com.chatak.pay.controller.model.Request;
import com.chatak.pay.controller.model.Response;
import com.chatak.pay.controller.model.SplitStatusRequest;
import com.chatak.pay.controller.model.SplitStatusResponse;
import com.chatak.pay.controller.model.TransactionRequest;
import com.chatak.pay.controller.model.TransactionResponse;
import com.chatak.pay.controller.model.topup.GetOperatorsResponse;
import com.chatak.pay.controller.model.topup.GetTopupCategoriesResponse;
import com.chatak.pay.controller.model.topup.GetTopupOffersResponse;
import com.chatak.pay.controller.model.topup.TopupRequest;
import com.chatak.pay.controller.model.topup.TopupResponse;
import com.chatak.pay.exception.ChatakPayException;
import com.chatak.pay.exception.InvalidRequestException;
import com.chatak.pay.model.TSMRequest;
import com.chatak.pay.model.TSMResponse;
import com.chatak.pay.util.JsonUtil;
import com.chatak.pay.util.StringUtil;
import com.chatak.pg.acq.dao.model.PGMerchant;
import com.chatak.pg.bean.ChangePasswordRequest;
import com.chatak.pg.bean.ForgotPasswordRequest;
import com.chatak.pg.enums.EntryModeEnum;
import com.chatak.pg.enums.TransactionType;
import com.chatak.pg.util.Constants;
import com.chatak.pg.util.DateUtil;
import com.chatak.pg.util.LogHelper;
import com.chatak.pg.util.LoggerMessage;
import com.chatak.pg.util.Properties;
import com.litle.sdk.generate.MethodOfPaymentTypeEnum;
import com.sleepycat.je.utilint.Timestamp;

/**
 * @Author: Girmiti Software
 * @Date: Apr 23, 2015
 * @Time: 10:41:08 PM
 * @Version: 1.0
 * @Comments:
 */
@RestController
@RequestMapping(value = "/transaction", consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON)
public class TransactionRestController extends BaseController implements URLMappingConstants, Constant {

  @Autowired
  private MessageSource messageSource;	

  private Logger logger = Logger.getLogger(TransactionRestController.class);

  @RequestMapping(value = "/process", method = RequestMethod.POST)
  public Response process(HttpServletRequest request,
                          HttpServletResponse response,
                          HttpSession session,
                          @RequestBody TransactionRequest transactionRequest) {
    logger.info("Entering:: TransactionRestController:: process method");
    TransactionResponse transactionResponse = new TransactionResponse();
    try {
      String ipPort = request.getRemoteAddr() + ":" + request.getRemotePort();
      transactionRequest.setIp_port(ipPort); 
      
      getTxnRequest(transactionRequest);
      
      if(null != transactionRequest.getEntryMode() && EntryModeEnum.QR_SALE.name().equalsIgnoreCase(transactionRequest.getEntryMode().name())) {
    	  logger.info("Processing:: TransactionRestController:: process method :: Type QR_SALE");
    	  CardData card = transactionRequest.getCardData();
    	  String track2 = card.getTrack2();
    	  if (!StringUtil.isNullEmpty(track2)) {
    	  card.setCardNumber(track2.substring(0, Constants.SIXTEEN));
    	  card.setExpDate(track2.substring(Constants.SEVENTEEN, Constants.TWENTY_ONE));
    	  transactionRequest.setCardData(card);
    	  } else {
    		  transactionResponse.setErrorCode(ChatakPayErrorCode.TXN_0024.name());
	          transactionResponse.setErrorMessage(messageSource.getMessage(ChatakPayErrorCode.TXN_0024.name(), null, LocaleContextHolder.getLocale()));
	          logger.info("Exiting:: TransactionRestController:: process method :: track2 data is null");
	          return transactionResponse;
    	  }
      }
      
      if(null != transactionRequest.getTransactionType() 
    		  && TransactionType.LOAD_FUND.name().equals(transactionRequest.getTransactionType().name())) {
    	  
    	  return validateTransactionType(transactionRequest);
    	  
      } else {
      
	      if(null != transactionRequest.getShareMode()) {
	        validateSplitTransactionData(transactionRequest);
	        validateProcessRequest(transactionRequest);
	        
	        logger.info("Processing the transaction");
	
	        Response responseLoadFund = pgTransactionService.processTransaction(transactionRequest);
	        
	        if(null != responseLoadFund) {
	          return responseLoadFund;
	        } else {
	        	 transactionResponse = new TransactionResponse();
	             transactionResponse.setErrorCode(ChatakPayErrorCode.TXN_0999.name());
	             transactionResponse.setErrorMessage(messageSource.getMessage(ChatakPayErrorCode.TXN_0999.name(), null, LocaleContextHolder.getLocale()));
	        }
	      } else {
	        validateProcessRequest(transactionRequest);
	        logger.info("Processing the transaction");
	        logger.info("Transaction Currency : " + transactionRequest.getCurrencyCode());
	        transactionResponse = (TransactionResponse) pgTransactionService.processTransaction(transactionRequest);
	      }
      
      }

      getTransactionResponse(transactionResponse);

    } catch(ChatakPayException ce) {
      logger.error("Error :: TransactionRestController :: process: ChatakPayException", ce);
      transactionResponse.setErrorCode(ChatakPayErrorCode.TXN_0099.name());
      transactionResponse.setErrorMessage(messageSource.getMessage(ChatakPayErrorCode.TXN_0999.name(), null, LocaleContextHolder.getLocale()));
    } catch(InvalidRequestException e) {
      logger.error("Error :: TransactionRestController :: process: InvalidRequestException", e);
      transactionResponse.setErrorCode(e.getErrorCode());
      transactionResponse.setErrorMessage(messageSource.getMessage(e.getErrorCode(), null, LocaleContextHolder.getLocale()));
    } catch(Exception e) {
      logger.error("Error :: TransactionRestController :: process: Exception", e);
      transactionResponse = new TransactionResponse();
      logger.error("ERROR: " + e.getMessage());
      transactionResponse.setErrorCode(ChatakPayErrorCode.TXN_0099.name());
      transactionResponse.setErrorMessage(messageSource.getMessage(ChatakPayErrorCode.TXN_0999.name(), null, LocaleContextHolder.getLocale()));
    }

    logger.info("Exiting:: SaleTransactionController:: process method");
    if(transactionResponse != null) {
      Timestamp timestamp = new Timestamp(System.currentTimeMillis());
      LogHelper.logInfo(logger, LoggerMessage.getCallerName(), "Time : " + timestamp);
      transactionResponse.setDeviceLocalTxnTime(DateUtil.convertTimeZone(transactionRequest.getTimeZoneOffset(), timestamp.toString()));
      LogHelper.logInfo(logger, LoggerMessage.getCallerName(), "Time after changed to merchant's timezone : " + transactionResponse.getDeviceLocalTxnTime());
    }
    return transactionResponse;
  }

  private Response validateTransactionType(TransactionRequest transactionRequest) throws InvalidRequestException {
	TSMRequest tmsRequest = new TSMRequest();
	  tmsRequest.setMerchantCode(transactionRequest.getMerchantId());
	  tmsRequest.setTid(transactionRequest.getTerminalId());
	
	  TSMResponse tsmResponse = (TSMResponse) JsonUtil
			.sendToTSM(TSMResponse.class, tmsRequest, Properties .getProperty("chatak-tsm.service.fetch.merchant.tid"));
	  
	  if(tsmResponse.getErrorCode() != null && tsmResponse.getErrorCode().equals("0") && 
	  		tsmResponse.getErrorMessage() != null && tsmResponse.getErrorMessage().equalsIgnoreCase("success")) {
		logger.info("validateProcessRequest :: LOAD_FUND");        	  
		PGMerchant pgMerchant = cardPaymentProcessor.validateMerchantId(transactionRequest.getMerchantId());
	  	
	  	return processRequest(transactionRequest, pgMerchant);
	  	
	  } else {
	      logger.info("Invalid Merchant/Terminal ID: "+transactionRequest.getMerchantId());
	      throw new InvalidRequestException(ChatakPayErrorCode.TXN_0114.name(), ChatakPayErrorCode.TXN_0114.value());
	  }
  }

  private void getTxnRequest(TransactionRequest transactionRequest) {
    if (transactionRequest.getCardData() != null
        && transactionRequest.getCardData().getCardType() == null && Constants.FLAG_TRUE
            .equals(Properties.getProperty("chatak-pay.skip.card.type.check", "false"))) {
      transactionRequest.getCardData().setCardType(MethodOfPaymentTypeEnum.BLANK);
    }

    transactionRequest.setPosEntryMode(null != transactionRequest.getEntryMode()
        ? transactionRequest.getEntryMode().value().toString() : "000");//Setting POS entry mode
  }

  private TransactionResponse getTransactionResponse(TransactionResponse transactionResponse) {
    if(transactionResponse == null) {
      logger.info("null reponse: transactionResponse");
      transactionResponse = new TransactionResponse();
      transactionResponse.setErrorCode(ChatakPayErrorCode.TXN_0999.name());
      transactionResponse.setErrorMessage(messageSource.getMessage(ChatakPayErrorCode.TXN_0999.name(), null, LocaleContextHolder.getLocale()));
    }
    return transactionResponse;
  }

  private Response processRequest(TransactionRequest transactionRequest, PGMerchant pgMerchant)
      throws InvalidRequestException {
    if(null != pgMerchant) {
    	logger.info("validateProcessRequest :: LOAD_FUND :: Proceeding");  
    	return pgTransactionService.processTransaction(transactionRequest, pgMerchant);
    } else {
    	logger.info("Invalid Merchant: "+transactionRequest.getMerchantId());
        throw new InvalidRequestException(ChatakPayErrorCode.TXN_0007.name(), ChatakPayErrorCode.TXN_0007.value());
    }
  }

  @RequestMapping(value = "/enquiry", method = RequestMethod.POST)
  public Response enquiry(HttpServletRequest request,
                          HttpServletResponse response,
                          HttpSession session,
                          @RequestBody SplitStatusRequest splitStatusRequest) {
    logger.info("Entering:: TransactionRestController:: process method");
    SplitStatusResponse statusResponse = new SplitStatusResponse();

    try {

      validateSplitStatusRequest(splitStatusRequest);
      statusResponse = pgSplitTransactionService.getSplitTxnStatus(splitStatusRequest);

    } catch(InvalidRequestException e) {
      logger.error("Error :: TransactionRestController :: enquiry: InvalidRequestException", e);
      statusResponse.setStatusInfo(false);
    } catch(Exception e) {
      statusResponse.setStatusInfo(false);
      logger.error("Error :: TransactionRestController :: enquiry", e);
    }

    return statusResponse;
  }
  
  @RequestMapping(value = "/login", method = RequestMethod.POST)
  public Response login(HttpServletRequest request,
                          HttpServletResponse response,
                          HttpSession session,
                          @RequestBody LoginRequest loginRequest) {
    logger.info("Entering:: TransactionRestController:: login method");
    LoginResponse loginResponse = new LoginResponse();
		try {
			if (loginRequest.getUsername() == null || loginRequest.getUsername().equals("")) {
				loginResponse.setErrorCode(ChatakPayErrorCode.GEN_002.name());
				loginResponse.setErrorMessage(
						messageSource.getMessage("chatak.username.required", null, LocaleContextHolder.getLocale()));
				return loginResponse;
			} else if (loginRequest.getPassword() == null || loginRequest.getPassword().equals("")) {
				loginResponse.setErrorCode(ChatakPayErrorCode.GEN_002.name());
				loginResponse.setErrorMessage(
						messageSource.getMessage("chatak.password.required", null, LocaleContextHolder.getLocale()));
				return loginResponse;
			}

			loginResponse = pgMerchantService.authenticateMerchantUser(loginRequest);

		} catch (Exception e) {
		  logger.error("Error :: TransactionRestController :: login: ", e);
		}

    logger.info("Exiting:: TransactionRestController:: login method");

    return loginResponse;
  }
  
	@RequestMapping(value = "/getOperators", method = RequestMethod.POST)
	public Response getOperatorList(HttpServletRequest request, HttpServletResponse response, HttpSession session,
			@RequestBody Request getOperatorsRequest) {
		logger.info("Entering:: TransactionRestController:: getOperatorList method");
		GetOperatorsResponse getOperatorsResponse = new GetOperatorsResponse();
		try {
			getOperatorsResponse = issuanceService.getOperators(getOperatorsRequest);
		} catch (Exception e) {
		  logger.error("Error :: TransactionRestController :: getOperatorList", e);
			getOperatorsResponse.setErrorCode(ChatakPayErrorCode.TXN_0992.name());
			getOperatorsResponse.setErrorMessage(messageSource.getMessage(ChatakPayErrorCode.TXN_0999.name(), null,
					LocaleContextHolder.getLocale()));
		}

		logger.info("Exiting:: TransactionRestController:: getOperatorList method");

		return getOperatorsResponse;
	}
	
	@RequestMapping(value = "/getOfferCategories", method = RequestMethod.POST)
	public Response getOfferCategories(HttpServletRequest request, HttpServletResponse response, HttpSession session,
			@RequestBody TopupRequest topupRequest) {
		logger.info("Entering:: TransactionRestController:: getOfferCategories method");
		GetTopupCategoriesResponse getTopupCategoriesResponse = new GetTopupCategoriesResponse();
		try {
			getTopupCategoriesResponse = issuanceService.getTopupCategories(topupRequest);
		} catch (Exception e) {
		  logger.error("Error :: TransactionRestController :: getOfferCategories", e);
			getTopupCategoriesResponse.setErrorCode(ChatakPayErrorCode.TXN_0992.name());
			getTopupCategoriesResponse.setErrorMessage(messageSource.getMessage(ChatakPayErrorCode.TXN_0999.name(), null,
					LocaleContextHolder.getLocale()));
		}

		logger.info("Exiting:: TransactionRestController:: getOfferCategories method");

		return getTopupCategoriesResponse;
	}
	
	@RequestMapping(value = "/getOfferDetails", method = RequestMethod.POST)
	public Response getOfferDetails(HttpServletRequest request, HttpServletResponse response, HttpSession session,
			@RequestBody TopupRequest topupRequest) {
		logger.info("Entering:: TransactionRestController:: getOfferDetails method");
		GetTopupOffersResponse getTopupOffersResponse = new GetTopupOffersResponse();
		try {
			getTopupOffersResponse = issuanceService.getTopupOffers(topupRequest);
		} catch (Exception e) {
		  logger.error("Error :: TransactionRestController :: getOfferDetails", e);
			getTopupOffersResponse.setErrorCode(ChatakPayErrorCode.TXN_0992.name());
			getTopupOffersResponse.setErrorMessage(messageSource.getMessage(ChatakPayErrorCode.TXN_0999.name(), null,
					LocaleContextHolder.getLocale()));
		}

		logger.info("Exiting:: TransactionRestController:: getOfferDetails method");

		return getTopupOffersResponse;
	}

	@RequestMapping(value = "/doTopup", method = RequestMethod.POST)
	public Response doTopup(HttpServletRequest request, HttpServletResponse response, HttpSession session,
			@RequestBody TopupRequest topupRequest) {
		logger.info("Entering:: TransactionRestController:: doTopup method");
		TopupResponse topupResponse = new TopupResponse();
		try {

			topupResponse = issuanceService.doTopup(topupRequest);
		} catch (Exception e) {
			logger.error("Error :: TransactionRestController :: doTopup: ", e);
			topupResponse.setErrorCode(ChatakPayErrorCode.TXN_0992.name());
			topupResponse.setErrorMessage(messageSource.getMessage(ChatakPayErrorCode.TXN_0999.name(), null,
					LocaleContextHolder.getLocale()));
		}

		logger.info("Exiting:: SaleTransactionController:: doTopup method");

		return topupResponse;
	}
	
	@RequestMapping(value = "/getMerchants", method = RequestMethod.POST)
	public Response getMerchants(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
		Response resp = null;
		logger.info("Entering:: TransactionRestController:: getMerchants method");
		resp = pgMerchantService.getMerchantNamesAndMerchantCode();
		logger.info("Exiting:: TransactionRestController:: getMerchants method");
		return resp;
	}
	
	@RequestMapping(value = "/changePassword", method = RequestMethod.POST)	
	public Response changePassword(@RequestBody ChangePasswordRequest changePassword ) throws ChatakPayException 
	{		
	Boolean booleanResp = null;		
	Response response = new Response();
	logger.info("Entering:: TransactionRestController:: changePassword method");	
	try {
	booleanResp = pgMerchantService.changedPassword(changePassword.getUserName(), changePassword.getCurrentPassword(), changePassword.getNewPassword());
	if(booleanResp.equals(true)){
		response.setErrorCode(ChatakPayErrorCode.GEN_001.name());
		response.setErrorMessage(messageSource.getMessage(ChatakPayErrorCode.GEN_001.name(), null,LocaleContextHolder.getLocale()));
	}
	}catch(ChatakPayException e){
		logger.info("Error:: TransactionRestController:: changePassword method",e);
		response.setErrorMessage(e.getMessage());
		return response;	
	}
	logger.info("Exiting:: TransactionRestController:: changePassword method");		
	return response;	
	}		
	
	@RequestMapping(value = "/forgotPassword", method = RequestMethod.POST)	
	public Response forgotPassword(HttpServletRequest request ,@RequestBody ForgotPasswordRequest changePassword ) throws ChatakPayException {		
		Boolean booleanResp = null;	
		Response response = new Response();
		logger.info("Entering:: TransactionRestController:: forgotPassword method");		
		String url = request.getRequestURL().toString();
        String uri = request.getRequestURI();
        String baseUrl = url.substring(0, url.length() - uri.length()) + "/" + Properties.getProperty("chatak.merchant.portal");
        LogHelper.logInfo(logger, LoggerMessage.getCallerName(), "Base URL -->> " + baseUrl);
		try {
			booleanResp = pgMerchantService.forgotPassword(changePassword.getUserName(), baseUrl);
			if(booleanResp.equals(true)){
				response.setErrorCode(ChatakPayErrorCode.GEN_001.name());
				response.setErrorMessage(messageSource.getMessage(ChatakPayErrorCode.GEN_001.name(), null,LocaleContextHolder.getLocale()));
			}
		}catch(ChatakPayException e){
			logger.info("Error:: TransactionRestController:: changePassword method",e);
			response.setErrorMessage(e.getMessage());
			return response;
		}
		logger.info("Exiting:: TransactionRestController:: forgotPassword method");		
		return response;	
		}

}
