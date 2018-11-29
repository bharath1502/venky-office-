package com.chatak.switches.sb.util;

import java.io.IOException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.util.Base64;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.http.Header;
import org.apache.http.entity.ContentType;
import org.apache.http.message.BasicHeader;
import org.apache.log4j.Logger;


import com.chatak.pg.exception.HttpClientException;
import com.chatak.pg.exception.PrepaidAdminException;
import com.chatak.pg.model.OAuthToken;
import com.chatak.pg.util.HttpClient;
import com.chatak.pg.util.Properties;
import com.chatak.switches.sb.exception.ChatakSwitchException;
import com.chatak.switches.sb.exception.ServiceException;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

public class JsonUtil {

   JsonUtil() {
    super();
  }
  private static final Logger logger = Logger.getLogger(JsonUtil.class);

  public static final String BASE_SERVICE_URL = Properties.getProperty("chatak-pay.service.url");

  public static final ObjectWriter objectWriter = new ObjectMapper().writer();
  
  private static String issuanceBaseServiceUrl = Properties.getProperty("prepaid.service.url");
  
  private static ObjectMapper mapper = new ObjectMapper();

  public static final String ISSUANCE_BASE_ADMIN_OAUTH_SERVICE_URL = Properties.getProperty("chatak-issuance.oauth.service.url");
  
  private static String TOKEN_TYPE_BEARER = "Bearer ";

  public static final String ISSUANCE_BASE_OAUTH_REFRESH_SERVICE_URL = Properties.getProperty("chatak-issuance.oauth.refresh.service.url");
  
  private static String TOKEN_TYPE_BASIC = "Basic ";

  private static String OAUTH_TOKEN_FEE = null;
  
  private static String AUTH_HEADER = "Authorization";

  private static String OAUTH_REFRESH_TOKEN_FEE = null;
  
  private static int refershRequestCount = 0;

  private static Long tokenValidityFee = null;
  
  /**
   * Method to convert Java object to JSON
   * 
   * @param object
   * @return
   * @throws PLMarketPlaceException
   */
  public static String convertObjectToJSON(Object object) throws ServiceException {
    String input = "";
    ObjectWriter objectWriter = new ObjectMapper().writer().withDefaultPrettyPrinter();
    try {
      input = objectWriter.writeValueAsString(object);
      return input;
    }
    catch(JsonGenerationException e) {
      logger.error("JsonUtil :: convertObjectToJSON method :", e);
    	throw new ServiceException(e.getMessage());
    } catch(JsonMappingException e) {
      logger.error("JsonUtil :: convertObjectToJSON method :JsonMappingException", e);
      throw new ServiceException(e.getMessage()); 
  }
    catch(IOException e) {
      logger.error("JsonUtil :: convertObjectToJSON method :IOException", e);
    	throw new ServiceException(e.getMessage()); 
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
  public static Object convertJSONToObject(String jsonData, Class<?> c) throws ServiceException {
    try {
      return mapper.readValue(jsonData, c);
    }
    catch(JsonGenerationException e) {
      logger.error("JsonUtil :: convertObjectToJSON method :", e);
        throw new ServiceException(e.getMessage());
    } catch(JsonMappingException e) {
      logger.error("JsonUtil :: convertObjectToJSON method :JsonMappingException", e);
      throw new ServiceException(e.getMessage()); 
  }
    catch(IOException e) {
      logger.error("JsonUtil :: convertObjectToJSON method :IOException", e);
        throw new ServiceException(e.getMessage()); 
    }
    
  }
  
  public static <T extends Object> T sendToIssuance(Object request, String serviceEndPoint,String mode,Class<T> response) throws HttpClientException, ChatakSwitchException {
		T resultantObject = null;
		logger.info("Connecting to Issuance URL :: " + issuanceBaseServiceUrl);
		HttpClient httpClient = new HttpClient(issuanceBaseServiceUrl, serviceEndPoint);
		try {
			Header[] headers = new Header[] {
					new BasicHeader("consumerClientId", Properties.getProperty("chatak-issuance.consumer.client.id")),
					new BasicHeader("consumerSecret", Properties.getProperty("chatak-issuance.consumer.client.secret")),
					new BasicHeader(AUTH_HEADER, TOKEN_TYPE_BEARER + getValidOAuth2TokenForFee()),
					new BasicHeader("content-type", ContentType.APPLICATION_JSON.getMimeType()) };
			resultantObject = httpClient.invokePost(request, response, headers, false);
		} catch (PrepaidAdminException e) {
		  logger.error("Error :: JsonUtil :: sendToIssuance : " + e.getMessage(), e);
			logger.info("Requesting oauth ::");
			if (refershRequestCount == 0) {
				refreshOAuth2Token_fee();
				refershRequestCount++;
				return sendToIssuance(request, serviceEndPoint, mode, response);
			}
		} catch (Exception e) {
			logger.info("Error:: JsonUtil:: postFee method " + e);
		    throw new ChatakSwitchException("Unable to connect to API server,Please try again");
		} 
		return resultantObject;
	}


private static String getValidOAuth2TokenForFee() {
	String output = null;
	if (isValidTokenFee()) {
		return OAUTH_TOKEN_FEE;
	} else {
		if(issuanceBaseServiceUrl.startsWith("https")) {
	        TrustManager[] trustAllCerts = new TrustManager[]{new X509TrustManager() {
	          
	        	@Override
	            public void checkServerTrusted(java.security.cert.X509Certificate[] arg0, String arg1) throws CertificateException {
	        		try {
						arg0[0].checkValidity();
					} catch (CertificateException e) {
						logger.info("Error:: JsonUtil:: checkServerTrusted method ");
						throw new CertificateException("Certificate not valid or trusted.");
					}
	            }
	        	
	          @Override
	          public java.security.cert.X509Certificate[] getAcceptedIssuers() {
	            
	            return new java.security.cert.X509Certificate[0];
	          }
	          
	          
	          
	          @Override
	          public void checkClientTrusted(java.security.cert.X509Certificate[] arg0, String arg1) throws CertificateException {
	        	  try {
						arg0[0].checkValidity();
					} catch (CertificateException e) {
						logger.info("Error:: JsonUtil:: checkClientTrusted method ");
						throw new CertificateException("Certificate not valid or trusted.");
					}
	          }
	        } };
	        

	      // Install the all-trusting trust manager
	      try {
	          SSLContext sc = SSLContext.getInstance("TLSv1.2");
	          sc.init(null, trustAllCerts, new SecureRandom());
	          HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
	      } catch (Exception e) {
	        logger.info("Error:: JsonUtil:: getValidOAuth2Token method "+e); ;
	      }

	      }
		HttpClient httpClient = new HttpClient(issuanceBaseServiceUrl, ISSUANCE_BASE_ADMIN_OAUTH_SERVICE_URL);
		try {
			Header[] headers = new Header[] { new BasicHeader(AUTH_HEADER, getBasicAuthValueForFee()),
					new BasicHeader("content-type", ContentType.APPLICATION_JSON.getMimeType()) };
			output = httpClient.invokePost(String.class, headers);
			OAuthToken apiResponse = new ObjectMapper().readValue(output, OAuthToken.class);
			OAUTH_TOKEN_FEE = apiResponse.getAccess_token();
			OAUTH_REFRESH_TOKEN_FEE = apiResponse.getRefresh_token();
			tokenValidityFee = System.currentTimeMillis() + (apiResponse.getExpires_in() * 60);
		} catch (Exception e) {
			logger.info("Error:: JsonUtil:: getValidOAuth2Token method " + e);
		}
	}
	return OAUTH_TOKEN_FEE;
}

private static boolean isValidTokenFee() {
  if( tokenValidityFee == null || OAUTH_TOKEN_FEE == null) {
    return false;
  }
  else if(System.currentTimeMillis() > tokenValidityFee) {
    OAUTH_TOKEN_FEE = null;
    return (null != refreshOAuth2Token_fee());
  }
  else {
    return true;
  }
}

private static String getBasicAuthValueForFee() {
    String basicAuth = Properties.getProperty("chatak-issuance.oauth.basic.auth.username") + ":"
                       + Properties.getProperty("chatak-issuance.oauth.basic.auth.password");
    basicAuth = TOKEN_TYPE_BASIC + new String(Base64.getEncoder().encode(basicAuth.getBytes()));
    return basicAuth;
  }

private static <T> String refreshOAuth2Token_fee() {
	T resultantObject = null;
	HttpClient paymentHttpClient = new HttpClient(issuanceBaseServiceUrl + ISSUANCE_BASE_OAUTH_REFRESH_SERVICE_URL,
			OAUTH_REFRESH_TOKEN_FEE);

	try {
		Class<T> response = null;
		Header[] headers = new Header[] { new BasicHeader(AUTH_HEADER, getBasicAuthValueForFee()),
				new BasicHeader("content-type", ContentType.APPLICATION_JSON.getMimeType()), };
		resultantObject = paymentHttpClient.invokeGet(response, headers);
		OAuthToken apiResponse = (OAuthToken) resultantObject;
		OAUTH_TOKEN_FEE = apiResponse.getAccess_token();
		OAUTH_REFRESH_TOKEN_FEE = apiResponse.getRefresh_token();
		tokenValidityFee = System.currentTimeMillis() + (apiResponse.getExpires_in() * 60);
		return OAUTH_TOKEN_FEE;
	} catch (Exception e) { 
		logger.info("Error:: JsonUtil:: refreshOAuth2Token_fee method " + e);
	}
	return null;
}

}
