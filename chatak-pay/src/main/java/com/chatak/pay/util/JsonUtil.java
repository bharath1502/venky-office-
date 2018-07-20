package com.chatak.pay.util;

import java.io.IOException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.util.Base64;
import java.util.Calendar;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.http.Header;
import org.apache.http.entity.ContentType;
import org.apache.http.message.BasicHeader;
import org.apache.log4j.Logger;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.ObjectWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.context.i18n.LocaleContextHolder;

import com.chatak.pay.exception.ChatakPayException;
import com.chatak.pg.constants.ActionErrorCode;
import com.chatak.pg.exception.HttpClientException;
import com.chatak.pg.model.ApplicationClientDTO;
import com.chatak.pg.model.OAuthToken;
import com.chatak.pg.util.HttpClient;
import com.chatak.pg.util.LogHelper;
import com.chatak.pg.util.LoggerMessage;
import com.chatak.pg.util.Properties;


public class JsonUtil {

  private JsonUtil() {
    super();
  }

  @Autowired
  private static MessageSource messageSource;

  private static final Logger logger = Logger.getLogger(JsonUtil.class);

  public static final String BASE_SERVICE_URL = Properties.getProperty("chatak-pay.service.url");

  public static final String BASE_PREPAID_SERVICE_URL = Properties.getProperty("prepaid.service.url");

  public static final ObjectWriter objectWriter = new ObjectMapper().writer();

  private static String issuanceBaseServiceUrl = "";

  public static final String ISSUANCE_BASE_ADMIN_OAUTH_SERVICE_URL =
      Properties.getProperty("chatak-issuance.oauth.service.url");

  public static final String ISSUANCE_BASE_OAUTH_REFRESH_SERVICE_URL =
      Properties.getProperty("chatak-issuance.oauth.refresh.service.url");

  private static String TOKEN_TYPE_BEARER = "Bearer ";

  private static String TOKEN_TYPE_BASIC = "Basic ";

  private static String AUTH_HEADER = "Authorization";

  private static String OAUTH_TOKEN_FEE = null;

  private static String OAUTH_REFRESH_TOKEN_FEE = null;

  private static Calendar tokenValidity_fee = null;

  private static final ObjectMapper mapper = new ObjectMapper();

  /**
   * Method to convert Java object to JSON
   * 
   * @param object
   * @return
   * @throws PLMarketPlaceException
   */
  public static String convertObjectToJSON(Object object) throws ChatakPayException {
    String input = "";
    ObjectWriter objectWriter = new ObjectMapper().writer().withDefaultPrettyPrinter();
    try {
      input = objectWriter.writeValueAsString(object);
      return input;
    } catch (JsonGenerationException e) {
      logger.error("Error :: JsonUtil :: convertObjectToJSON JsonGenerationException", e);
      throw new ChatakPayException(e.getMessage());
    } catch (JsonMappingException e) {
      logger.error("Error :: JsonUtil :: convertObjectToJSON JsonMappingException", e);
      throw new ChatakPayException(e.getMessage());
    } catch (IOException e) {
      logger.error("Error :: JsonUtil :: convertObjectToJSON IOException", e);
      throw new ChatakPayException(e.getMessage());
    }

  }

  /**
   * Method to convert JSON data to given class object
   *  
   * @param jsonData
   * @param c
   * @return
   * @throws PLMarketPlaceException
   */
  public static Object convertJSONToObject(String jsonData, Class<?> c) throws ChatakPayException {
    ObjectMapper mapper = new ObjectMapper();
    try {
      return mapper.readValue(jsonData, c);
    } catch (JsonParseException e) {
      logger.error("Error :: JsonUtil :: convertJSONToObject JsonParseException", e);
      throw new ChatakPayException(e.getMessage());
    } catch (JsonMappingException e) {
      logger.error("Error :: JsonUtil :: convertJSONToObject JsonMappingException", e);
      throw new ChatakPayException(e.getMessage());
    } catch (IOException e) {
      logger.error("Error :: JsonUtil :: convertJSONToObject IOException", e);
      throw new ChatakPayException(e.getMessage());
    }

  }

  /**
   * Method to invoke REST service with Payload object
   * 
   * @param request
   * @param serviceEndPoint
   * @return
   * @throws ChatakPayException 
   */
  
  
  public static <T extends Object> T postRequest(Class<T> className,Object request,String serviceEndPoint) throws ChatakPayException, HttpClientException {
		T resultantObject = null;
		HttpClient httpClient = new HttpClient(BASE_SERVICE_URL, serviceEndPoint);
	    try {
	    	Header[] headers = new Header[] { new BasicHeader("content-type", ContentType.APPLICATION_JSON.getMimeType()),
					new BasicHeader(AUTH_HEADER, TOKEN_TYPE_BEARER ) };
	      resultantObject = httpClient.invokePost(request,className, headers);
	    }catch (HttpClientException hce) {
	    	LogHelper.logError(logger, LoggerMessage.getCallerName(), hce, hce.getHttpErrorCode() + hce.getMessage());
	    	throw hce;
	    }catch (Exception e) {
	        logger.error("Error::PostReqeust::Method", e);
	        throw new ChatakPayException(e.getMessage());
	      }
	    logger.info("Exiting::PostReqeust::Method");
	    return resultantObject;
	}

  
  /**
   * Method to invoke REST service object
   * 
   * @param serviceEndPoint
   * @return
 * @throws IOException 
   */

  public static <T extends Object> T sendToIssuance(Class<T> className,Object request,String serviceEndPoint) throws  HttpClientException {
		T resultantObject = null;
		issuanceBaseServiceUrl = Properties.getProperty("chatak-issuance.service.url");
		logger.info("Connecting to Issuance URL :: " + issuanceBaseServiceUrl + serviceEndPoint);
		HttpClient httpClient = new HttpClient(issuanceBaseServiceUrl,serviceEndPoint);
	    try {
	    	logger.info("sendToIssuance :: " + request);
	    	Header[] headers = new Header[] { new BasicHeader("content-type", ContentType.APPLICATION_JSON.getMimeType()),
	    			new BasicHeader("consumerClientId", Properties.getProperty("chatak-issuance.consumer.client.id")),
	    			new BasicHeader("consumerSecret", Properties.getProperty("chatak-issuance.consumer.client.secret")),
	    			new BasicHeader(AUTH_HEADER, TOKEN_TYPE_BEARER + getValidIssuanceOAuth2Token()),
	    			};
	      resultantObject = httpClient.invokePost(request,className, headers);
	    } catch (HttpClientException hce) {
	    	LogHelper.logError(logger, LoggerMessage.getCallerName(), hce, hce.getHttpErrorCode() + hce.getMessage());
	    	throw hce;
	    }catch (Exception e) {
	    	logger.info("Error:: JsonUtil:: postFee method " + e);
	        throw new NoSuchMessageException(messageSource.getMessage(
	        ActionErrorCode.ERROR_CODE_API_CONNECT, null, LocaleContextHolder.getLocale()));
	    }
	    return resultantObject;
	}
  
  private static String getValidIssuanceOAuth2Token() throws IOException {
		String apiResponse = null;
		if (isValidToken_fee()) {
			logger.info("getValidIssuanceOAuth2Token :: returning same auth token");
		      return OAUTH_TOKEN_FEE;
		} else {
			 if (issuanceBaseServiceUrl.startsWith("https")) {
			        TrustManager[] trustAllCerts = new TrustManager[] {new X509TrustManager() {

			          @Override
			          public java.security.cert.X509Certificate[] getAcceptedIssuers() {
			            return new java.security.cert.X509Certificate[] {};
			          }

			          @Override
			          public void checkClientTrusted(java.security.cert.X509Certificate[] arg0, String arg1)
			              throws CertificateException {
			            //Need to Implement Based on Requirement
			          }
			          
			          @Override
			          public void checkServerTrusted(java.security.cert.X509Certificate[] arg0, String arg1)
			              throws CertificateException {
			            //Need to Implement Based on Requirement
			          }
			        }};


			        // Install the all-trusting trust manager
			        try {
			          SSLContext sc = SSLContext.getInstance("TLS");
			          sc.init(null, trustAllCerts, new SecureRandom());
			          HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());

			          SSLContext ctx = SSLContext.getInstance("SSL");
			          ctx.init(null, trustAllCerts, null);
			        } catch (Exception e) {
			          logger.info("Error:: JsonUtil:: getValidOAuth2Token method " + e);;
			        }

			      }
			
			HttpClient paymentHttpClient = new HttpClient(BASE_PREPAID_SERVICE_URL,ISSUANCE_BASE_ADMIN_OAUTH_SERVICE_URL);
			try {
				Header[] headers = new Header[] {
						new BasicHeader("content-type", ContentType.APPLICATION_JSON.getMimeType()),
						new BasicHeader(AUTH_HEADER, getBasicAuthValueForFee()) };

				apiResponse = paymentHttpClient.invokePost(String.class, headers);
				OAuthToken oAuthToken = new ObjectMapper().readValue(apiResponse, OAuthToken.class);
				OAUTH_TOKEN_FEE = oAuthToken.getAccess_token();
		        OAUTH_REFRESH_TOKEN_FEE = oAuthToken.getRefresh_token();
		        tokenValidity_fee = Calendar.getInstance();
		        tokenValidity_fee.add(Calendar.SECOND, oAuthToken.getExpires_in());
		        
		        logger.info("getValidIssuanceOAuth2Token :: retrieved token: " + OAUTH_TOKEN_FEE);
		        
		}catch (Exception e) {
	        logger.info("Error:: JsonUtil:: getValidOAuth2Token method " + e);;
	      }
		}
		return OAUTH_TOKEN_FEE;
	}

  private static boolean isValidToken_fee() {
    if (OAUTH_TOKEN_FEE == null || tokenValidity_fee == null) {
      return false;
    } else if (Calendar.getInstance().after(tokenValidity_fee)) {
      OAUTH_TOKEN_FEE = null;
      return (refreshOAuth2Token_fee() != null);
    } else {
      return true;
    }
  }
  
  
    private static <T> String refreshOAuth2Token_fee() {
		
	    HttpClient paymentHttpClient = new HttpClient(issuanceBaseServiceUrl + ISSUANCE_BASE_OAUTH_REFRESH_SERVICE_URL
	            , OAUTH_REFRESH_TOKEN_FEE);

	        T resultantObject = null;
	        try {
	          Class<T> response = null;
	          Header[] headers =
	              new Header[] {new BasicHeader("content-type", ContentType.APPLICATION_JSON.getMimeType()),
	                  new BasicHeader(AUTH_HEADER, getBasicAuthValueForFee())};

	          resultantObject = paymentHttpClient.invokeGet(response, headers);
	          OAuthToken apiResponse = (OAuthToken) resultantObject;
	          OAUTH_TOKEN_FEE = apiResponse.getAccess_token();
	          OAUTH_REFRESH_TOKEN_FEE = apiResponse.getRefresh_token();
	          tokenValidity_fee = Calendar.getInstance();
	          tokenValidity_fee.add(Calendar.SECOND, apiResponse.getExpires_in());
	          return OAUTH_TOKEN_FEE;
	        } catch (Exception e) {
	        	logger.info("Error:: JsonUtil:: refreshOAuth2Token_fee method " + e);
	        }
	        return null;
    }
	   
  
  private static String getBasicAuthValueForFee() {
    String basicAuth = Properties.getProperty("chatak-issuance.oauth.basic.auth.username") + ":"
        + Properties.getProperty("chatak-issuance.oauth.basic.auth.password");
    basicAuth = TOKEN_TYPE_BASIC + new String(Base64.getEncoder().encode(basicAuth.getBytes()));
    return basicAuth;
  }

    public static <T extends Object> T sendToTSM(Class<T> className,Object request,String serviceEndPoint) throws  HttpClientException {
		T resultantObject = null;
		String tsmURL = Properties.getProperty("chatak-tsm.service.url");
		HttpClient httpClient = new HttpClient(tsmURL , serviceEndPoint);
	    try {
	    	Header[] headers = new Header[] { new BasicHeader("content-type", ContentType.APPLICATION_JSON.getMimeType()),
	    			new BasicHeader("consumerClientId", Properties.getProperty("chatak-issuance.consumer.client.id")),
	    			new BasicHeader("consumerSecret", Properties.getProperty("chatak-issuance.consumer.client.secret"))};
	      resultantObject = httpClient.invokePost(request,className, headers);
	     
	       LogHelper.logInfo(logger, LoggerMessage.getCallerName(), "TSM Response Status : " );
	    } catch (HttpClientException hce) {
	    	LogHelper.logError(logger, LoggerMessage.getCallerName(), hce, hce.getHttpErrorCode() + hce.getMessage());
	    	throw hce;
	    }catch (Exception e) {
	    	 logger.info("Error:: sendToTSM:: postFee method " + e);
	         throw new NoSuchMessageException(messageSource.getMessage(
	         ActionErrorCode.ERROR_CODE_API_CONNECT, null, LocaleContextHolder.getLocale()));
	    }
	    return resultantObject;
	}

    /**
     * Method to get OAUTH token for given credentials
     * 
     * @return 
     */
    public static OAuthToken getValidOAuth2TokenLoginRefresh(ApplicationClientDTO applicationClient) {
      LogHelper.logEntry(logger, LoggerMessage.getCallerName());
      OAuthToken apiResponse= null;    
      try {
        String serviceEndPoint = Properties.getProperty("chatak.pay.token.url").replace("$", applicationClient.getAppClientId())
            .replace("#", applicationClient.getAppClientAccess());
        Header[] headers =
            new Header[] {new BasicHeader("content-type", ContentType.APPLICATION_JSON.getMimeType()),
                new BasicHeader(AUTH_HEADER, getBasicAuthValueOnApplicationClientDTO(applicationClient))};
        HttpClient paymentHttpClient =
            new HttpClient(BASE_SERVICE_URL, serviceEndPoint);
        String output = paymentHttpClient.invokePost(String.class, headers);
        apiResponse = mapper.readValue(output, OAuthToken.class);
      } catch (Exception e) {
        logger.error("ERROR: JsonUtil :: getValidOAuth2Token method", e);
      }
      LogHelper.logExit(logger, LoggerMessage.getCallerName() + "Got API Response");
      return apiResponse;
    }

      private static String getBasicAuthValueOnApplicationClientDTO(ApplicationClientDTO applicationClient) {
          String basicAuth = applicationClient.getAppAuthUser() + ":" + applicationClient.getAppAuthPass();
          basicAuth = TOKEN_TYPE_BASIC + new String(Base64.getEncoder().encode(basicAuth.getBytes()));
          return basicAuth;
      }

    /**
     * Method to get OAUTH token for given credentials
     * 
     * @return 
     */
    public static OAuthToken getValidOAuth2TokenLogin(ApplicationClientDTO applicationClient) {

      String tokenEndpointUrl =
          Properties.getProperty("chatak.pay.token.url").replace("$", applicationClient.getAppAuthUser());
      tokenEndpointUrl = tokenEndpointUrl.replace("#", applicationClient.getAppAuthPass());

      OAuthToken apiResponse= null;
      try {

        Header[] headers =
            new Header[] {new BasicHeader("content-type", ContentType.APPLICATION_JSON.getMimeType()),
                new BasicHeader(AUTH_HEADER, getBasicAuthValue())};
       HttpClient paymentHttpClient =
            new HttpClient(BASE_SERVICE_URL, tokenEndpointUrl);

       String output = paymentHttpClient.invokePost(String.class, headers);
       apiResponse = mapper.readValue(output, OAuthToken.class);
      } catch (Exception e) {
        logger.error("ERROR: JsonUtil :: getValidOAuth2Token method", e);
      }
      return apiResponse;
    }

    private static String getBasicAuthValue() {
      String basicAuth = Properties.getProperty("chatak-merchant.oauth.basic.auth.username") + ":"
                         + Properties.getProperty("chatak-merchant.oauth.basic.auth.password");
      basicAuth = TOKEN_TYPE_BASIC + new String(Base64.getEncoder().encode(basicAuth.getBytes()));
      return basicAuth;
    }
}
