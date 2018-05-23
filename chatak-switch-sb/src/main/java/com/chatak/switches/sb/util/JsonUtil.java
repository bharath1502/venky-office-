package com.chatak.switches.sb.util;

import java.io.IOException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.util.Calendar;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import javax.ws.rs.core.MediaType;

import org.apache.http.HttpStatus;
import org.apache.log4j.Logger;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.ObjectWriter;

import com.chatak.pg.model.OAuthToken;
import com.chatak.pg.util.Constants;
import com.chatak.pg.util.Properties;
import com.chatak.switches.sb.exception.ChatakSwitchException;
import com.chatak.switches.sb.exception.ServiceException;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.WebResource.Builder;
import com.sun.jersey.core.util.Base64;

public class JsonUtil {

   JsonUtil() {
    super();
  }
  private static final Logger logger = Logger.getLogger(JsonUtil.class);

  public static final String BASE_SERVICE_URL = Properties.getProperty("chatak-pay.service.url");

  public static final ObjectWriter objectWriter = new ObjectMapper().writer();
  
  private static String issuanceBaseServiceUrl = "";
  
  private static ObjectMapper mapper = new ObjectMapper();

  public static final String ISSUANCE_BASE_ADMIN_OAUTH_SERVICE_URL = Properties.getProperty("chatak-issuance.oauth.service.url");
  
  private static String TOKEN_TYPE_BEARER = "Bearer ";

  public static final String ISSUANCE_BASE_OAUTH_REFRESH_SERVICE_URL = Properties.getProperty("chatak-issuance.oauth.refresh.service.url");
  
  private static String TOKEN_TYPE_BASIC = "Basic ";

  private static String OAUTH_TOKEN_FEE = null;
  
  private static String AUTH_HEADER = "Authorization";

  private static String OAUTH_REFRESH_TOKEN_FEE = null;
  
  private static int refershRequestCount = 0;

  private static Calendar tokenValidity_fee = null;
  
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
    ObjectMapper mapper = new ObjectMapper();
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

  /**
   * Method to invoke REST service with Payload object
   * 
   * @param request
   * @param serviceEndPoint
   * @return
   * @throws ServiceException 
   */
  public static Object postRequest(Class<?> responseClass, Object request, String serviceEndPoint) throws ServiceException {
    logger.info("Inside::PostReqeust::Method");
    Client client = Client.create();
    logger.info("Connecting to Gate way URL ::"+BASE_SERVICE_URL);
    WebResource webResource = client.resource(BASE_SERVICE_URL + serviceEndPoint);
    ClientResponse response = null;
    ObjectWriter objectPrettyWriter = objectWriter.withDefaultPrettyPrinter();
    String input = "";
    try {
      input = objectPrettyWriter.writeValueAsString(request);
      response = webResource.header("Content-Type", MediaType.APPLICATION_JSON).post(ClientResponse.class, input);
      if(response.getStatus() == HttpStatus.SC_OK) {
        return validateStatus(responseClass, response);
        }
    }
    catch(Exception e) {
      logger.error("Error::PostReqeust::Method",e);
      throw new ServiceException(e.getMessage());
    }
    logger.info("Exiting::PostReqeust::Method");
    return null;

  }

private static Object validateStatus(Class<?> responseClass, ClientResponse response)
		throws IOException, JsonParseException, JsonMappingException {
	String output = response.getEntity(String.class);
	  return mapper.readValue(output, responseClass);
}

  /**
   * Method to invoke REST service object
   * 
   * @param serviceEndPoint
   * @return
   */
  public static ClientResponse postRequest(String serviceEndPoint) {
    ClientResponse response = validateMediaType(serviceEndPoint);
    return response;
  }

private static ClientResponse validateMediaType(String serviceEndPoint) {
	Client client = Client.create();
    WebResource webResource = client.resource(BASE_SERVICE_URL + serviceEndPoint);
  
    ClientResponse response = webResource.type(MediaType.APPLICATION_JSON).post(ClientResponse.class);
	return response;
}
  public static ClientResponse postRequestLogin(String serviceEndPoint) {
	    ClientResponse response = validateMediaType(serviceEndPoint);
	   
	    return response;
	  }
  
  public static ClientResponse sendToIssuance(Object request, String serviceEndPoint,String mode) throws Exception{
    Client client = Client.create();
    client.setConnectTimeout(Constants.TIME_OUT);
    issuanceBaseServiceUrl=ProcessorConfig.get(ProcessorConfig.FEE_SERVICE+mode);
    logger.info("Connecting to Issuance URL :: "+issuanceBaseServiceUrl);
    String input = "";
    Builder webResource = client.resource(issuanceBaseServiceUrl + serviceEndPoint).header("consumerClientId",
                                                                                     Properties.getProperty("chatak-issuance.consumer.client.id")).header("consumerSecret",
                                                                                                                                                          Properties.getProperty("chatak-issuance.consumer.client.secret"));
    webResource.header(AUTH_HEADER, TOKEN_TYPE_BEARER + getValidOAuth2TokenForFee());
    ObjectWriter objectPrettyWriter = objectWriter.withDefaultPrettyPrinter();
    ClientResponse response = null;
    try {
      input = objectPrettyWriter.writeValueAsString(request);
      response = webResource.type(MediaType.APPLICATION_JSON).post(ClientResponse.class, input);
      if (null != response && refershRequestCount == 0 && response.getStatus() == HttpStatus.SC_UNAUTHORIZED) {
        return validateResponseAndStatus(request, serviceEndPoint, mode);
      }
    }
    catch(Exception e) {
      logger.info("Error:: JsonUtil:: postFee method "+e);
      throw new ChatakSwitchException("Unable to connect to API server,Please try again");
    }
    return response;
  }

private static ClientResponse validateResponseAndStatus(Object request, String serviceEndPoint, String mode)
		throws Exception {
	refreshOAuth2Token_fee();
	refershRequestCount++;
	return sendToIssuance(request, serviceEndPoint, mode);
}
private static String getValidOAuth2TokenForFee() {
    if(isValidToken_fee()) {
      return OAUTH_TOKEN_FEE;
    }
    else {

      Client client = Client.create();
      if(issuanceBaseServiceUrl.startsWith("https")) {
        TrustManager[] trustAllCerts = new TrustManager[]{new X509TrustManager() {
          
        	@Override
            public void checkServerTrusted(java.security.cert.X509Certificate[] arg0, String arg1) throws CertificateException {
             
        		// need to implement based on requirement
            }
        	
          @Override
          public java.security.cert.X509Certificate[] getAcceptedIssuers() {
            
            return null;
          }
          
          
          
          @Override
          public void checkClientTrusted(java.security.cert.X509Certificate[] arg0, String arg1) throws CertificateException {
        	// need to implement based on requirement
          }
        } };
        

      // Install the all-trusting trust manager
      try {
          SSLContext sc = SSLContext.getInstance("TLS");
          sc.init(null, trustAllCerts, new SecureRandom());
          HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
      } catch (Exception e) {
        logger.info("Error:: JsonUtil:: getValidOAuth2Token method "+e); ;
      }

      }
      ClientResponse response = null;
      Builder webResource = client.resource(issuanceBaseServiceUrl
                                            + ISSUANCE_BASE_ADMIN_OAUTH_SERVICE_URL).header(AUTH_HEADER, getBasicAuthValueForFee());

      try {
        response = webResource.type(MediaType.APPLICATION_JSON).get(ClientResponse.class);
        String output = response.getEntity(String.class);
        OAuthToken apiResponse = new ObjectMapper().readValue(output, OAuthToken.class);
        OAUTH_REFRESH_TOKEN_FEE = apiResponse.getRefresh_token();
        OAUTH_TOKEN_FEE = apiResponse.getAccess_token();
        tokenValidity_fee = Calendar.getInstance();
        tokenValidity_fee.add(Calendar.SECOND, apiResponse.getExpires_in());
      }
      catch(Exception e) {
        logger.info("Error:: JsonUtil:: getValidOAuth2Token method "+e); ;
      }
    }
    return OAUTH_TOKEN_FEE;
  }

private static boolean isValidToken_fee() {
  if( tokenValidity_fee == null || OAUTH_TOKEN_FEE == null) {
    return false;
  }
  else if(Calendar.getInstance().after(tokenValidity_fee)) {
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
    basicAuth = TOKEN_TYPE_BASIC + new String(Base64.encode(basicAuth));
    return basicAuth;
  }
private static String refreshOAuth2Token_fee() {
  Client client = Client.create();
  ClientResponse response = null;
  Builder webResource = client.resource(issuanceBaseServiceUrl
                                        + ISSUANCE_BASE_OAUTH_REFRESH_SERVICE_URL + OAUTH_REFRESH_TOKEN_FEE).header(AUTH_HEADER,
                                                                                                                    getBasicAuthValueForFee());
  try {
    response = webResource.type(MediaType.APPLICATION_JSON).get(ClientResponse.class);
    String output = response.getEntity(String.class);
    OAuthToken apiResponse = new ObjectMapper().readValue(output, OAuthToken.class);
    OAUTH_REFRESH_TOKEN_FEE = apiResponse.getRefresh_token();
    OAUTH_TOKEN_FEE = apiResponse.getAccess_token();
    tokenValidity_fee = Calendar.getInstance();
    tokenValidity_fee.add(Calendar.SECOND, apiResponse.getExpires_in());
    return OAUTH_TOKEN_FEE;
  }
  catch(Exception e) {
    logger.info("Error:: JsonUtil:: refreshOAuth2Token_fee method "+e);
  }
  return null;
}

}
