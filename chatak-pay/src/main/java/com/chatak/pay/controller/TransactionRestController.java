/**
 * 
 */
package com.chatak.pay.controller;

import java.io.IOException;
import java.util.Map;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.ws.rs.core.MediaType;

import org.apache.http.HttpStatus;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.chatak.pay.constants.ChatakPayErrorCode;
import com.chatak.pay.constants.Constant;
import com.chatak.pay.constants.URLMappingConstants;
import com.chatak.pay.controller.model.CardData;
import com.chatak.pay.controller.model.ClientCurrencyDTO;
import com.chatak.pay.controller.model.ClientSsoLoginResponse;
import com.chatak.pay.controller.model.LoginRequest;
import com.chatak.pay.controller.model.LoginResponse;
import com.chatak.pay.controller.model.Request;
import com.chatak.pay.controller.model.Response;
import com.chatak.pay.controller.model.SplitStatusRequest;
import com.chatak.pay.controller.model.SplitStatusResponse;
import com.chatak.pay.controller.model.TransactionHistoryResponse;
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
import com.chatak.pg.acq.dao.model.PGApplicationClient;
import com.chatak.pg.acq.dao.model.PGMerchant;
import com.chatak.pg.bean.ChangePasswordRequest;
import com.chatak.pg.bean.ForgotPasswordRequest;
import com.chatak.pg.constants.PGConstants;
import com.chatak.pg.enums.EntryModeEnum;
import com.chatak.pg.enums.TransactionType;
import com.chatak.pg.exception.HttpClientException;
import com.chatak.pg.model.ApplicationClientDTO;
import com.chatak.pg.model.OAuthToken;
import com.chatak.pg.model.TransactionHistoryRequest;
import com.chatak.pg.util.CommonUtil;
import com.chatak.pg.util.Constants;
import com.chatak.pg.util.DateUtil;
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

  private static Logger logger = LogManager.getLogger(TransactionRestController.class.getName());

  @Autowired
  private MessageSource messageSource;	

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
			
      if(isvalidQrSaleEntryMode(transactionRequest)) {
    	  logger.info("Processing:: TransactionRestController:: process method :: Type QR_SALE");
    	  CardData card = transactionRequest.getCardData();
    	  String track2 = card.getTrack2();
    	  if (!StringUtil.isNullEmpty(track2)) {
    	  card.setCardNumber(track2.substring(0, track2.indexOf('D')));
    	  card.setExpDate(track2.substring(track2.indexOf('D') + 1, track2.indexOf('D') + Integer.parseInt("5")));
    	  transactionRequest.setCardData(card);
    	  } else {
    		  transactionResponse.setErrorCode(ChatakPayErrorCode.TXN_0024.name());
	          transactionResponse.setErrorMessage(messageSource.getMessage(ChatakPayErrorCode.TXN_0024.name(), null, LocaleContextHolder.getLocale()));
	          logger.info("Exiting:: TransactionRestController:: process method :: track2 data is null");
	          return transactionResponse;
    	  }
      }
      
      if(isvalidLoadFundRequest(transactionRequest)) {
    	  
    	  return validateTransactionType(transactionRequest);
    	  
      } else {
      
	      if(null != transactionRequest.getShareMode()) {
	        validateSplitTransactionData(transactionRequest);
	        PGMerchant merchant = validateProcessRequest(transactionRequest);
	        Response responseLoadFund = pgTransactionService.processTransaction(transactionRequest, merchant);
	        
	        if(null != responseLoadFund) {
	          return responseLoadFund;
	        }
	        transactionResponse = new TransactionResponse();
	        transactionResponse.setErrorCode(ChatakPayErrorCode.TXN_0999.name());
	        transactionResponse.setErrorMessage(messageSource.getMessage(ChatakPayErrorCode.TXN_0999.name(), null, LocaleContextHolder.getLocale()));
	      } else {
	    	PGMerchant merchant = validateProcessRequest(transactionRequest);
	        logger.info("Transaction Currency : " + transactionRequest.getCurrencyCode());
	        transactionResponse = (TransactionResponse) pgTransactionService.processTransaction(transactionRequest, merchant);
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
    validateTxnResponse(transactionRequest, transactionResponse);
    return transactionResponse;
  }

private void validateTxnResponse(TransactionRequest transactionRequest, TransactionResponse transactionResponse) {
	if(transactionResponse != null) {
      Timestamp timestamp = new Timestamp(System.currentTimeMillis());
      transactionResponse.setDeviceLocalTxnTime(DateUtil.convertTimeZone(transactionRequest.getTimeZoneOffset(), timestamp.toString()));
    }
}

private boolean isvalidLoadFundRequest(TransactionRequest transactionRequest) {
	return null != transactionRequest.getTransactionType() 
    		  && TransactionType.LOAD_FUND.name().equals(transactionRequest.getTransactionType().name());
}

private boolean isvalidQrSaleEntryMode(TransactionRequest transactionRequest) {
	return null != transactionRequest.getEntryMode() && EntryModeEnum.QR_SALE.name().equalsIgnoreCase(transactionRequest.getEntryMode().name());
}

  private Response validateTransactionType(TransactionRequest transactionRequest) throws InvalidRequestException,IOException, HttpClientException {
	TSMRequest tmsRequest = new TSMRequest();
	  tmsRequest.setMerchantCode(transactionRequest.getMerchantCode());
	  tmsRequest.setTid(transactionRequest.getTerminalId());
	
	  TSMResponse tsmResponse = (TSMResponse) JsonUtil
			.sendToTSM(TSMResponse.class, tmsRequest, Properties .getProperty("chatak-tsm.service.fetch.merchant.tid"));
	  if(tsmResponse.getErrorCode() != null && tsmResponse.getErrorCode().equals("0") && 
	  		tsmResponse.getErrorMessage() != null && tsmResponse.getErrorMessage().equalsIgnoreCase("success")) {
		logger.info("validateProcessRequest :: LOAD_FUND");        	  
		PGMerchant pgMerchant = cardPaymentProcessor.validateMerchantId(transactionRequest.getMerchantCode());
	  	
	  	return processRequest(transactionRequest, pgMerchant);
	  	
	  } else {
	      logger.info("Invalid Merchant/Terminal ID: "+transactionRequest.getMerchantCode());
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
    	logger.info("Invalid Merchant: "+transactionRequest.getMerchantCode());
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
			} else if (isInvalidPassword(loginRequest)) {
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
    public Response changePassword(@RequestBody ChangePasswordRequest changePassword)
        throws ChatakPayException {
      Boolean booleanResp = null;
      Response response = new Response();
      logger.info("Entering:: TransactionRestController:: changePassword method");
      try {
        if (changePassword == null || changePassword.getUserName() == null) {
          return processInvalidRequest(PGConstants.TXN_0121, messageSource);
        } else if (changePassword.getCurrentPassword() == null) {
          return processInvalidRequest(PGConstants.TXN_0122, messageSource);
        } else if (changePassword.getNewPassword() == null) {
          return processInvalidRequest(PGConstants.TXN_0123, messageSource);
        } else if (changePassword.getConfirmPassword() == null) {
          return processInvalidRequest(PGConstants.TXN_0124, messageSource);
        } else if (!Pattern.compile(PGConstants.USER_NAME_REGEX).matcher(changePassword.getUserName())
            .matches()) {
          return processInvalidRequest(PGConstants.TXN_0131, messageSource);
        } else if (!Pattern.compile(PGConstants.PSWD_REGEX).matcher(changePassword.getCurrentPassword())
            .matches()) {
          return processInvalidRequest(PGConstants.TXN_0126, messageSource);
        } else if (!Pattern.compile(PGConstants.PSWD_REGEX).matcher(changePassword.getNewPassword())
            .matches()) {
          return processInvalidRequest(PGConstants.TXN_0127, messageSource);
        } else if (!Pattern.compile(PGConstants.PSWD_REGEX).matcher(changePassword.getConfirmPassword())
            .matches()) {
          return processInvalidRequest(PGConstants.TXN_0128, messageSource);
        }
        validateCustomerUserName(changePassword.getUserName());
        if (changePassword.getCurrentPassword().equals(changePassword.getNewPassword())) {
          return processInvalidRequest(PGConstants.TXN_0119, messageSource);
        } else if (!changePassword.getNewPassword().equals(changePassword.getConfirmPassword())) {
          return processInvalidRequest(PGConstants.TXN_0120, messageSource);
        }
        booleanResp = pgMerchantService.changedPassword(changePassword.getUserName(),
            changePassword.getCurrentPassword(), changePassword.getNewPassword());
        if (booleanResp.equals(true)) {
          response.setErrorCode(ChatakPayErrorCode.GEN_001.name());
          response.setErrorMessage(messageSource.getMessage("password.change.success", null,
              LocaleContextHolder.getLocale()));
        }
      } catch (HttpClientException e) {
        logger.error("Error :: TransactionRestController :: login : Invalid Access Token.." + e.getMessage(), e);

        return getAccessDeniedResponse(messageSource);
      } catch (ChatakPayException e) {
        logger.info("Error:: TransactionRestController:: changePassword method", e);
        response.setErrorCode(ChatakPayErrorCode.GEN_002.name());
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
        if (changePassword == null || changePassword.getUserName() == null) {
          return processInvalidRequest(PGConstants.TXN_0121, messageSource);
        } else if (!Pattern.compile(PGConstants.USER_NAME_REGEX).matcher(changePassword.getUserName())
            .matches()) {
          return processInvalidRequest(PGConstants.TXN_0125, messageSource);
        }
		String url = request.getRequestURL().toString();
        String uri = request.getRequestURI();
        String baseUrl = url.substring(0, url.length() - uri.length()) + "/" + Properties.getProperty("chatak.merchant.portal");
		try {
			booleanResp = pgMerchantService.forgotPassword(changePassword.getUserName(), baseUrl);
			if(booleanResp.equals(true)){
				response.setErrorCode(ChatakPayErrorCode.GEN_001.name());
				response.setErrorMessage(messageSource.getMessage(ChatakPayErrorCode.GEN_001.name(), null,LocaleContextHolder.getLocale()));
			}
		}catch(ChatakPayException e){
			logger.info("Error:: TransactionRestController:: changePassword method",e);
			response.setErrorCode(ChatakPayErrorCode.GEN_002.name());
			response.setErrorMessage(e.getMessage());
			return response;
		}
		logger.info("Exiting:: TransactionRestController:: forgotPassword method");		
		return response;	
		}

        @RequestMapping(value = "/clientSsoLogin", method = RequestMethod.POST)
        public Response clientSsoLogin(HttpServletRequest request, HttpServletResponse response,
            HttpSession session, @RequestBody LoginRequest loginRequest) {
          logger.info("Entering:: TransactionRestController:: clientSsoLogin method");
          ClientSsoLoginResponse clientLoginResponse = new ClientSsoLoginResponse();
          try {
            if (loginRequest.getUsername() == null || loginRequest.getUsername().equals("")) {
              clientLoginResponse.setErrorCode(ChatakPayErrorCode.GEN_002.name());
              clientLoginResponse.setErrorMessage(messageSource.getMessage("chatak.username.required", null,
                  LocaleContextHolder.getLocale()));
              return clientLoginResponse;
            } else if (isInvalidPassword(loginRequest)) {
              clientLoginResponse.setErrorCode(ChatakPayErrorCode.GEN_002.name());
              clientLoginResponse.setErrorMessage(messageSource.getMessage("chatak.password.required", null,
                  LocaleContextHolder.getLocale()));
              return clientLoginResponse;
            }
            
            validateLoginRequest(loginRequest);
      
            LoginResponse loginResponse = pgMerchantService.authenticateMerchantUser(loginRequest);
            if(loginResponse.getErrorCode().equals(SIXTEEN_STR)){
            	clientLoginResponse.setErrorCode(ChatakPayErrorCode.GEN_002.name());
            	clientLoginResponse.setErrorMessage(messageSource.getMessage("chatak.invalid.appversion", null,LocaleContextHolder.getLocale()));
            	return clientLoginResponse;
            }
            if(loginResponse.getErrorCode().equals(ELEVEN_STR)){
            	clientLoginResponse.setErrorCode(ChatakPayErrorCode.GEN_002.name());
            	clientLoginResponse.setErrorMessage(messageSource.getMessage("chatak.deviceSerial.error", null,
                        LocaleContextHolder.getLocale()));
            	return clientLoginResponse;
            }
            clientLoginResponse = CommonUtil.copyBeanProperties(loginResponse, ClientSsoLoginResponse.class);
            if (loginResponse.getCurrencyDTO() != null) {
              ClientCurrencyDTO currencyDTO = CommonUtil.copyBeanProperties(loginResponse.getCurrencyDTO(), ClientCurrencyDTO.class);
              clientLoginResponse.setCurrencyDTO(currencyDTO);
            }
            if (isLoginSuccess(clientLoginResponse)) {
              // OAuth generation
              logger.info("OAuth Genartion : " + clientLoginResponse.getErrorCode());
              ApplicationClientDTO applicationClientDTO =
                  pgMerchantService.getApplicationClientAuth(loginRequest.getUsername());
      
              if (!StringUtil.isNull(applicationClientDTO)) {
                logger.info("ApplicationClientDTO is not NULL");
                OAuthToken token = getOAuthToken(applicationClientDTO);
      
                if (StringUtil.isNull(token)) {
                  logger.info("Token is NULL");
                  clientLoginResponse.setStatus(false);
                  clientLoginResponse.setMessage("Failed");
                  return clientLoginResponse;
                }
                clientLoginResponse.setAccessToken(token.getAccess_token());
                clientLoginResponse.setRefreshToken(token.getRefresh_token());
                PGApplicationClient applicationClient =
                    CommonUtil.copyBeanProperties(applicationClientDTO, PGApplicationClient.class);
                applicationClient.setRefreshToken(token.getRefresh_token());
                pgMerchantService.saveOrUpdateApplicationClient(applicationClient);
              } else {
                clientLoginResponse.setStatus(false);
                clientLoginResponse.setMessage("Failed");
                logger.info("Exiting :: TransactionRestController :: clientSsoLogin");
                return clientLoginResponse;
              }
            }
          } catch (InvalidRequestException e) {
            logger.error("Error :: TransactionRestController :: clientSsoLogin : ", e);
            return processInvalidRequest(e.getLocalizedMessage(), messageSource);
          } catch (Exception e) {
            logger.error("Error :: TransactionRestController :: clientSsoLogin : ", e);
          }
      
          logger.info("Exiting:: TransactionRestController:: clientSsoLogin method");
      
          return clientLoginResponse;
        }

        private OAuthToken getOAuthToken(ApplicationClientDTO applicationClientDTO)
            throws InstantiationException, IllegalAccessException {
          OAuthToken token = null;
   
          if (!StringUtil.isNullAndEmpty(applicationClientDTO.getRefreshToken())) {
            logger.info("Refresh Token is Not Empty");
            token = JsonUtil.getValidOAuth2TokenLoginRefresh(applicationClientDTO);
            
            // refresh token expired, need to update here
            token = updatingTokenOnExpiration(applicationClientDTO, token);
          } else {
            logger.info("Refresh Token is Empty");
            token = JsonUtil.getValidOAuth2TokenLogin(applicationClientDTO);
   
            // save the refresh token for the first time
            saveFirstTimeRefreshToken(applicationClientDTO, token);
          }
          return token;
        }

        private void validateLoginRequest(LoginRequest loginRequest) throws InvalidRequestException {
          if (loginRequest.getDeviceSerial() == null) {
            throw new InvalidRequestException(PGConstants.TXN_0129);
          } else if (loginRequest.getCurrentAppVersion() == null) {
            throw new InvalidRequestException(PGConstants.TXN_0130);
          } else if (!Pattern.compile(PGConstants.USER_NAME_REGEX).matcher(loginRequest.getUsername())
              .matches()) {
            throw new InvalidRequestException(PGConstants.TXN_0131);
          } else if (!Pattern.compile(PGConstants.PSWD_REGEX).matcher(loginRequest.getPassword())
              .matches()) {
            throw new InvalidRequestException(PGConstants.TXN_0132);
          } else if (!Pattern.compile(PGConstants.IMEI_REGEX).matcher(loginRequest.getDeviceSerial())
              .matches() || loginRequest.getDeviceSerial().startsWith("0")) {
            throw new InvalidRequestException(PGConstants.TXN_0133);
          } else if (!Pattern.compile(PGConstants.APP_VERSION_REGEX)
              .matcher(loginRequest.getCurrentAppVersion()).matches()
              || loginRequest.getCurrentAppVersion().contains("..")
              || loginRequest.getCurrentAppVersion().endsWith(".")
              || loginRequest.getCurrentAppVersion().startsWith(".")) {
            throw new InvalidRequestException(PGConstants.TXN_0134);
          }
        }

        private boolean isInvalidPassword(LoginRequest loginRequest) {
          return loginRequest.getPassword() == null || loginRequest.getPassword().equals("");
        }

        private boolean isLoginSuccess(ClientSsoLoginResponse loginResponse) {
          return loginResponse.getErrorCode().equals(ChatakPayErrorCode.GEN_001.name())
              || loginResponse.getErrorCode().equals(ChatakPayErrorCode.GEN_003.name());
        }

        private void saveFirstTimeRefreshToken(ApplicationClientDTO applicationClientDTO,
            OAuthToken token) throws InstantiationException, IllegalAccessException {
          if (!StringUtil.isNull(token)) {
            logger.info("Generated Token is valid");
            PGApplicationClient applicationClient =
                CommonUtil.copyBeanProperties(applicationClientDTO, PGApplicationClient.class);
            pgMerchantService.saveOrUpdateApplicationClient(applicationClient);
          }
        }
      
        @RequestMapping(value = "getAccessToken", method = RequestMethod.POST)
        public Response generateAccessTokenOnRefreshToken(HttpServletRequest request,
            HttpServletResponse response, HttpSession session, @RequestBody LoginRequest loginRequest) {
          logger.info("Entering :: TransactionRestController :: generateAccessTokenOnRefreshToken");
          LoginResponse loginResponse = new LoginResponse();
          try {
            if (loginRequest.getUsername() == null || loginRequest.getUsername().equals("")) {
              loginResponse.setErrorCode(ChatakPayErrorCode.GEN_002.name());
              loginResponse.setErrorMessage(messageSource.getMessage("chatak.username.required", null,
                  LocaleContextHolder.getLocale()));
              logger.info("UserName is null");
              return loginResponse;
            }
            String headerRefreshToken = request.getHeader("authorization")
                .substring(request.getHeader("authorization").indexOf(' ') + 1);
            logger.info("Fetched Refresh Token from Headers : " + headerRefreshToken);
            ApplicationClientDTO applicationClientDTO =
                pgMerchantService.getApplicationClientAuth(loginRequest.getUsername());
            logger.info("Validating Refresh Token -->> Application Client DTO : " + applicationClientDTO);
            if(applicationClientDTO == null) {
              return processInvalidRequest(PGConstants.TXN_0131, messageSource);
            }
            if (!applicationClientDTO.getRefreshToken().equals(headerRefreshToken)) {
              loginResponse.setErrorCode("ERROR_1685");
              loginResponse.setErrorMessage(messageSource.getMessage(loginResponse.getErrorCode(), null,
                  LocaleContextHolder.getLocale()));
              logger.info("Exiting :: TransactionRestController :: generateAccessTokenOnRefreshToken :: Refresh Token Not Matched");
              return loginResponse;
            }
            OAuthToken token = null;
            // OAuth generation
            logger.info("OAuth Genartion");
            if (null != applicationClientDTO) {
              if (!StringUtil.isNullAndEmpty(applicationClientDTO.getRefreshToken())) {
                logger.info("Refresh Token is Not Empty");
                token = JsonUtil.getValidOAuth2TokenLoginRefresh(applicationClientDTO);

                token = updatingTokenOnExpiration(applicationClientDTO, token);
              } else {
                logger.info("Refresh Token is Empty");
                token = JsonUtil.getValidOAuth2TokenLogin(applicationClientDTO);

                // save the fresh token for the first time
                if (!StringUtil.isNull(token)) {
                  PGApplicationClient applicationClient =
                      CommonUtil.copyBeanProperties(applicationClientDTO, PGApplicationClient.class);
                  pgMerchantService.saveOrUpdateApplicationClient(applicationClient);
                }
              }
              loginResponse.setAccessToken(token.getAccess_token());
              loginResponse.setRefreshToken(token.getRefresh_token());
              PGApplicationClient applicationClient =
                  CommonUtil.copyBeanProperties(applicationClientDTO, PGApplicationClient.class);
              applicationClient.setRefreshToken(token.getRefresh_token());
              pgMerchantService.saveOrUpdateApplicationClient(applicationClient);
              logger.info("Updated Application Client >> Exiting");
            }
            logger.info("Exiting :: TransactionRestController :: generateAccessTokenOnRefreshToken");
          } catch (Exception e) {
            logger.error("Error :: TransactionRestController :: generateAccessTokenOnRefreshToken : " + e.getMessage(), e);
            loginResponse.setErrorCode("TXN_0993");
            loginResponse.setErrorMessage(messageSource.getMessage(loginResponse.getErrorCode(), null,
                LocaleContextHolder.getLocale()));
          }
          return loginResponse;
        }

        private OAuthToken updatingTokenOnExpiration(ApplicationClientDTO applicationClientDTO, OAuthToken token) {
          if (token == null) {
              token = JsonUtil.getValidOAuth2TokenLogin(applicationClientDTO);
              applicationClientDTO.setRefreshToken(token.getRefresh_token());
          }
          return token;
      }

      @RequestMapping(value = "/clientProcess", method = RequestMethod.POST)
      public Response clientProcess(HttpServletRequest request, HttpServletResponse response,
          HttpSession session, @RequestBody TransactionRequest transactionRequest) {
        try {
          validateClientProcessRequest(transactionRequest);
          validateCustomerUserName(transactionRequest.getUserName());
          return process(request, response, session, transactionRequest);
		} catch (InvalidRequestException e) {
			logger.error("Error :: TransactionRestController :: clientSsoLogin : ", e);
			return processInvalidRequest(e.getLocalizedMessage(), messageSource);
		} catch (HttpClientException e) {
          logger.error("Error :: TransactionRestController :: clientProcess : " + e.getMessage(), e);
          return getAccessDeniedResponse(messageSource);
        }
      }

      public String getCustomerUserName() {
        OAuth2Authentication auth =
            (OAuth2Authentication) SecurityContextHolder.getContext().getAuthentication();
        if (null != auth) {
          Map<String, String> map = auth.getAuthorizationRequest().getAuthorizationParameters();
          logger.info(" With UserName : " + auth.getName());
          return map.get("client_id");
        }
        return null;
      }

      public void validateCustomerUserName(String customerUserName) throws HttpClientException {
        if (!getCustomerUserName().equals(customerUserName)) {
        	logger.info("Invalid Access Token..");
        	logger.info("UserName in JSON Request : "
              + customerUserName + " UserName in Authentication : " + getCustomerUserName());
          throw new HttpClientException("403", HttpStatus.SC_FORBIDDEN);
        }
      }

      public Response getAccessDeniedResponse(MessageSource messageSource) {
        Response response = new Response();
        response.setErrorCode(ChatakPayErrorCode.TXN_0403.name());
        response.setErrorMessage(
            messageSource.getMessage(response.getErrorCode(), null, LocaleContextHolder.getLocale()));
        return response;
      }

      public Response processInvalidRequest(String errorCode, MessageSource messageSource) {
        Response loginResponse = new Response();
        loginResponse.setErrorCode(errorCode);
        loginResponse.setErrorMessage(
                messageSource.getMessage(loginResponse.getErrorCode(), null, LocaleContextHolder.getLocale()));
        logger.info("Exiting :: TransactionRestController :: processInvalidRequest :: " + loginResponse.getErrorMessage());
        return loginResponse;
    }

	private void validateClientProcessRequest(TransactionRequest transactionRequest) throws InvalidRequestException {
		if (transactionRequest == null || transactionRequest.getUserName() == null) {
			throw new InvalidRequestException(PGConstants.TXN_0121);
		} else if (transactionRequest.getTransactionType() == null) {
			throw new InvalidRequestException(PGConstants.TXN_0135);
		} else if (transactionRequest.getMerchantCode() == null) {
			throw new InvalidRequestException(PGConstants.TXN_0136);
		} else if (transactionRequest.getTerminalId() == null) {
			throw new InvalidRequestException(PGConstants.TXN_0175);
		} else if (transactionRequest.getDeviceSerial() == null) {
			throw new InvalidRequestException(PGConstants.TXN_0129);
		} else if (!Pattern.compile(PGConstants.USER_NAME_REGEX).matcher(transactionRequest.getUserName()).matches()) {
			throw new InvalidRequestException(PGConstants.TXN_0125);
		} else if (!isValidTransactionType(transactionRequest)) {
			throw new InvalidRequestException(PGConstants.TXN_0139);
		} else if (!Pattern.compile(PGConstants.IMEI_REGEX).matcher(transactionRequest.getMerchantCode()).matches()
				|| transactionRequest.getMerchantCode().startsWith("0")) {
			throw new InvalidRequestException(PGConstants.TXN_0140);
		} else if (!Pattern.compile(PGConstants.TID_REGEX).matcher(transactionRequest.getTerminalId()).matches()) {
			throw new InvalidRequestException(PGConstants.TXN_0141);
		} else if (!Pattern.compile(PGConstants.IMEI_REGEX).matcher(transactionRequest.getDeviceSerial()).matches()
				|| transactionRequest.getDeviceSerial().startsWith("0")) {
			throw new InvalidRequestException(PGConstants.TXN_0133);
		} else {
			validateTimeZoneDetails(transactionRequest);
		}
		validateRequestOnTxnType(transactionRequest);
	}

	private void validateTimeZoneDetails(TransactionRequest transactionRequest) throws InvalidRequestException {
		if (transactionRequest.getTimeZoneOffset() == null) {
			throw new InvalidRequestException(PGConstants.TXN_0137);
		} else if (transactionRequest.getTimeZoneRegion() == null) {
			throw new InvalidRequestException(PGConstants.TXN_0138);
		} else if (transactionRequest.getTimeZoneOffset().length() < 5
				|| transactionRequest.getTimeZoneOffset().length() > 20) {
			throw new InvalidRequestException(PGConstants.TXN_0142);
		} else if (transactionRequest.getTimeZoneRegion().length() < 5
				|| transactionRequest.getTimeZoneRegion().length() > 20) {
			throw new InvalidRequestException(PGConstants.TXN_0143);
		}
	}

	private void validateRequestOnTxnType(TransactionRequest transactionRequest) throws InvalidRequestException {
		if (transactionRequest.getTransactionType().equals(TransactionType.SALE)) {
			validateSaleTxnRequest(transactionRequest);
		} else if (transactionRequest.getTransactionType().equals(TransactionType.REFUND)) {
			validateRefundTxnRequest(transactionRequest);
		} else if (transactionRequest.getTransactionType().equals(TransactionType.BALANCE)) {
			validateBalanceEnquiryRequest(transactionRequest);
		} else {
			throw new InvalidRequestException(PGConstants.TXN_0139);
		}
	}

	private void validateRefundTxnRequest(TransactionRequest transactionRequest) throws InvalidRequestException {
		if (transactionRequest.getCgRefNumber() == null) {
			throw new InvalidRequestException(PGConstants.TXN_0144);
		} else if (transactionRequest.getTxnRefNumber() == null) {
			throw new InvalidRequestException(PGConstants.TXN_0145);
		} else if (!Pattern.compile(PGConstants.REF_NUMBER_REGEX).matcher(transactionRequest.getCgRefNumber()).matches()
				|| transactionRequest.getCgRefNumber().startsWith("0")) {
			throw new InvalidRequestException(PGConstants.TXN_0146);
		} else if (!Pattern.compile(PGConstants.REF_NUMBER_REGEX).matcher(transactionRequest.getTxnRefNumber())
				.matches() || transactionRequest.getTxnRefNumber().startsWith("0")) {
			throw new InvalidRequestException(PGConstants.TXN_0147);
		}
	}

	private void validateSaleTxnRequest(TransactionRequest transactionRequest) throws InvalidRequestException {
		if (transactionRequest.getInvoiceNumber() == null) {
			throw new InvalidRequestException(PGConstants.TXN_0161);
		} else if (transactionRequest.getMerchantAmount() == null) {
			throw new InvalidRequestException(PGConstants.TXN_0162);
		} else if (transactionRequest.getOrderId() == null) {
			throw new InvalidRequestException(PGConstants.TXN_0163);
		} else if (transactionRequest.getTotalTxnAmount() == null) {
			throw new InvalidRequestException(PGConstants.TXN_0164);
		} else if (transactionRequest.getCardData() == null) {
			throw new InvalidRequestException(PGConstants.TXN_0165);
		} else if (transactionRequest.getCardData().getCardType() == null) {
			throw new InvalidRequestException(PGConstants.TXN_0168);
		} else if (!Pattern.compile(PGConstants.INVOICE_NUMBER_REGEX).matcher(transactionRequest.getInvoiceNumber())
				.matches() || transactionRequest.getInvoiceNumber().startsWith("0")) {
			throw new InvalidRequestException(PGConstants.TXN_0171);
		} else if (transactionRequest.getMerchantAmount() <= 0) {
			throw new InvalidRequestException(PGConstants.TXN_0178);
		} else if (!Pattern.compile(PGConstants.ORDER_ID_REGEX).matcher(transactionRequest.getOrderId()).matches()
				|| transactionRequest.getOrderId().startsWith("0")) {
			throw new InvalidRequestException(PGConstants.TXN_0173);
        } else if (transactionRequest.getTotalTxnAmount() <= 0
            || !transactionRequest.getTotalTxnAmount().equals(transactionRequest.getMerchantAmount())) {
			throw new InvalidRequestException(PGConstants.TXN_0179);
        } else if (!transactionRequest.getCardData().getCardType().toString().equals(PGConstants.CARD_TYPE)) {
			throw new InvalidRequestException(PGConstants.TXN_0177);
		}
		validateCardHolderName(transactionRequest);
		validateSaleRequest(transactionRequest);
	}

	private void validateBalanceEnquiryRequest(TransactionRequest transactionRequest) throws InvalidRequestException {
		if (transactionRequest.getEntryMode() == null) {
			throw new InvalidRequestException(PGConstants.TXN_0148);
		} else if (transactionRequest.getCardData() == null) {
			throw new InvalidRequestException(PGConstants.TXN_0149);
		} else if (transactionRequest.getCardData().getCardNumber() == null) {
			throw new InvalidRequestException(PGConstants.TXN_0151);
		} else if (transactionRequest.getCardData().getCvv() == null) {
			throw new InvalidRequestException(PGConstants.TXN_0152);
		} else if (transactionRequest.getCardData().getExpDate() == null) {
			throw new InvalidRequestException(PGConstants.TXN_0153);
		} else if (transactionRequest.getCardData().getUid() == null) {
			throw new InvalidRequestException(PGConstants.TXN_0154);
		} else if (!Pattern.compile(PGConstants.CARD_NUMBER_REGEX)
				.matcher(transactionRequest.getCardData().getCardNumber()).matches()
				|| transactionRequest.getCardData().getCardNumber().startsWith("0")) {
			throw new InvalidRequestException(PGConstants.TXN_0155);
		} else if (!Pattern.compile(PGConstants.CVV_REGEX).matcher(transactionRequest.getCardData().getCvv()).matches()
				|| transactionRequest.getCardData().getCvv().startsWith("0")) {
			throw new InvalidRequestException(PGConstants.TXN_0156);
		} else if (isValidExpDate(transactionRequest)) {
			throw new InvalidRequestException(PGConstants.TXN_0157);
		} else if (isNotValidCardUid(transactionRequest)) {
			throw new InvalidRequestException(PGConstants.TXN_0158);
		} else if (!isValidEntryMode(transactionRequest)) {
			throw new InvalidRequestException(PGConstants.TXN_0159);
		} else {
			validateCardHolderName(transactionRequest);
		}
	}

	private boolean isNotValidCardUid(TransactionRequest transactionRequest) {
		return !Pattern.compile(PGConstants.CARD_UID_REGEX).matcher(transactionRequest.getCardData().getUid())
				.matches();
	}

	private void validateCardHolderName(TransactionRequest transactionRequest) throws InvalidRequestException {
		if (transactionRequest.getCardData().getCardHolderName() == null) {
			throw new InvalidRequestException(PGConstants.TXN_0150);
		} else if (!Pattern.compile(PGConstants.CARD_HOLDER_NAME_REGEX)
				.matcher(transactionRequest.getCardData().getCardHolderName()).matches()) {
			throw new InvalidRequestException(PGConstants.TXN_0160);
		}
	}

	private void validateSaleRequest(TransactionRequest transactionRequest) throws InvalidRequestException {
		if (transactionRequest.getEntryMode().equals(EntryModeEnum.MANUAL)) {
			validateManualRequest(transactionRequest);
		} else if (transactionRequest.getEntryMode().equals(EntryModeEnum.QR_SALE)) {
			validateQrSaleRequest(transactionRequest);
		} else if (transactionRequest.getEntryMode().equals(EntryModeEnum.PAN_SWIPE_CONTACTLESS)) {
			validateNfcRequest(transactionRequest);
		} else if (transactionRequest.getEntryMode().equals(EntryModeEnum.CARD_TAP)) {
			validateCardTapRequest(transactionRequest);
		} else {
			throw new InvalidRequestException(PGConstants.TXN_0159);
		}
	}

	private void validateManualRequest(TransactionRequest transactionRequest) throws InvalidRequestException {
		if (transactionRequest.getCardData().getCardNumber() == null) {
			throw new InvalidRequestException(PGConstants.TXN_0151);
		} else if (transactionRequest.getCardData().getCvv() == null) {
			throw new InvalidRequestException(PGConstants.TXN_0152);
		} else if (transactionRequest.getCardData().getExpDate() == null) {
			throw new InvalidRequestException(PGConstants.TXN_0153);
		} else if (isInvalidData(transactionRequest.getCardData().getTrack2())) {
			throw new InvalidRequestException(PGConstants.TXN_0170);
		} else if (!Pattern.compile(PGConstants.CARD_NUMBER_REGEX)
				.matcher(transactionRequest.getCardData().getCardNumber()).matches()
				|| transactionRequest.getCardData().getCardNumber().startsWith("0")) {
			throw new InvalidRequestException(PGConstants.TXN_0155);
		} else if (!Pattern.compile(PGConstants.CVV_REGEX).matcher(transactionRequest.getCardData().getCvv()).matches()
				|| transactionRequest.getCardData().getCvv().startsWith("0")) {
			throw new InvalidRequestException(PGConstants.TXN_0156);
		} else if (isValidExpDate(transactionRequest)) {
			throw new InvalidRequestException(PGConstants.TXN_0157);
		}
	}

	private void validateQrSaleRequest(TransactionRequest transactionRequest) throws InvalidRequestException {
		if (transactionRequest.getQrCode() == null) {
			throw new InvalidRequestException(PGConstants.TXN_0176);
		} else if (transactionRequest.getCardData().getTrack2() == null) {
			throw new InvalidRequestException(PGConstants.TXN_0172);
		} else if (!Pattern.compile(PGConstants.QR_CODE).matcher(transactionRequest.getQrCode()).matches()
				|| transactionRequest.getQrCode().startsWith("0")) {
			throw new InvalidRequestException(PGConstants.TXN_0174);
		} else if (!Pattern.compile(PGConstants.TRACK_2).matcher(transactionRequest.getCardData().getTrack2()).matches()
				|| transactionRequest.getCardData().getTrack2().startsWith("0")) {
			throw new InvalidRequestException(PGConstants.TXN_0170);
		} else if (isInvalidData(transactionRequest.getCardData().getCardNumber())) {
			throw new InvalidRequestException(PGConstants.TXN_0155);
		} else if (isInvalidData(transactionRequest.getCardData().getExpDate())) {
			throw new InvalidRequestException(PGConstants.TXN_0157);
		}
	}

	private void validateNfcRequest(TransactionRequest transactionRequest) throws InvalidRequestException {
		if (transactionRequest.getCardData().getCardNumber() == null) {
			throw new InvalidRequestException(PGConstants.TXN_0151);
		} else if (transactionRequest.getCardData().getExpDate() == null) {
			throw new InvalidRequestException(PGConstants.TXN_0153);
		} else if (transactionRequest.getCardData().getTrack2() == null) {
			throw new InvalidRequestException(PGConstants.TXN_0170);
		} else if (!Pattern.compile(PGConstants.CARD_NUMBER_REGEX)
				.matcher(transactionRequest.getCardData().getCardNumber()).matches()
				|| transactionRequest.getCardData().getCardNumber().startsWith("0")) {
			throw new InvalidRequestException(PGConstants.TXN_0155);
		} else if (isValidExpDate(transactionRequest)) {
			throw new InvalidRequestException(PGConstants.TXN_0157);
		} else if (!Pattern.compile(PGConstants.NFC_TRACK_2).matcher(transactionRequest.getCardData().getTrack2())
				.matches() || transactionRequest.getCardData().getTrack2().startsWith("0")) {
			throw new InvalidRequestException(PGConstants.TXN_0170);
		}
	}

	private void validateCardTapRequest(TransactionRequest transactionRequest) throws InvalidRequestException {
		if (transactionRequest.getCardData().getCardNumber() == null) {
			throw new InvalidRequestException(PGConstants.TXN_0151);
		} else if (transactionRequest.getCardData().getCvv() == null) {
			throw new InvalidRequestException(PGConstants.TXN_0152);
		} else if (transactionRequest.getCardData().getExpDate() == null) {
			throw new InvalidRequestException(PGConstants.TXN_0153);
		} else if (transactionRequest.getCardData().getUid() == null) {
			throw new InvalidRequestException(PGConstants.TXN_0154);
		} else if (isInvalidData(transactionRequest.getCardData().getTrack2())) {
			throw new InvalidRequestException(PGConstants.TXN_0170);
		} else if (!Pattern.compile(PGConstants.CVV_REGEX).matcher(transactionRequest.getCardData().getCvv()).matches()
				|| transactionRequest.getCardData().getCvv().startsWith("0")) {
			throw new InvalidRequestException(PGConstants.TXN_0156);
		} else if (isNotValidCardUid(transactionRequest)) {
			throw new InvalidRequestException(PGConstants.TXN_0158);
		} else if (isValidExpDate(transactionRequest)) {
			throw new InvalidRequestException(PGConstants.TXN_0157);
		} else if (!Pattern.compile(PGConstants.CARD_NUMBER_REGEX)
				.matcher(transactionRequest.getCardData().getCardNumber()).matches()
				|| transactionRequest.getCardData().getCardNumber().startsWith("0")) {
			throw new InvalidRequestException(PGConstants.TXN_0155);
		}
	}

	private boolean isValidExpDate(TransactionRequest transactionRequest) {
		String expDate = transactionRequest.getCardData().getExpDate();
		int expMonth = Integer.parseInt(expDate.substring(2));
		return !Pattern.compile(PGConstants.EXP_DATE_REGEX).matcher(expDate).matches() || expDate.startsWith("0")
				|| expMonth < 1 || expMonth > 12;
	}

	private boolean isInvalidData(String track2) {
		return (!(track2 == null || track2.equals("")));
	}

	private boolean isValidTransactionType(TransactionRequest transactionRequest) {
		for (TransactionType type : TransactionType.values()) {
			if (type.name().equals(transactionRequest.getTransactionType().toString())) {
				return true;
			}
		}
		return false;
	}

	private boolean isValidEntryMode(TransactionRequest transactionRequest) {
		for (EntryModeEnum type : EntryModeEnum.values()) {
			if (type.name().equals(transactionRequest.getEntryMode().toString())) {
				return true;
			}
		}
		return false;
	}
	
	@RequestMapping(value = "/clientTxnHistory", method = RequestMethod.POST)
	public TransactionHistoryResponse clientTxnHistory(HttpServletRequest request, HttpServletResponse response,
			HttpSession session, @RequestBody TransactionHistoryRequest transactionHistoryRequest) {
		logger.info("Entering:: TransactionRestController:: clientTxnHistory method");
		TransactionHistoryResponse transactionHistoryResponse = new TransactionHistoryResponse();
		try {
			validateCustomerUserName(transactionHistoryRequest.getUserName());
			transactionHistoryResponse = pgTransactionService.getMerchantTransactionList(transactionHistoryRequest);
			if (null != transactionHistoryResponse
					&& transactionHistoryResponse.getErrorCode().equals(Constants.ERROR_CODE)) {
				transactionHistoryResponse = new TransactionHistoryResponse();
				transactionHistoryResponse.setErrorCode(Constants.ERROR_CODE);
				transactionHistoryResponse.setErrorMessage(
						messageSource.getMessage("chatak.transaction.error", null, LocaleContextHolder.getLocale()));
			}
		} catch (Exception e) {
			logger.error("Error :: TransactionRestController :: clientTxnHistory", e);
		}
		logger.info("Exiting:: TransactionRestController:: clientTxnHistory method");
		return transactionHistoryResponse;
	}
}
