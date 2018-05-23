package com.chatak.pay.util;

import java.io.IOException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.util.Calendar;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import javax.ws.rs.core.MediaType;

import org.apache.commons.httpclient.HttpStatus;
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
import com.chatak.pg.constants.PGConstants;
import com.chatak.pg.model.OAuthToken;
import com.chatak.pg.util.Constants;
import com.chatak.pg.util.LogHelper;
import com.chatak.pg.util.LoggerMessage;
import com.chatak.pg.util.Properties;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.WebResource.Builder;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.client.urlconnection.HTTPSProperties;
import com.sun.jersey.core.util.Base64;


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

  private static final ObjectMapper mapper = new ObjectMapper();

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
  public static Object postRequest(Class<?> responseClass, Object request, String serviceEndPoint)
      throws ChatakPayException {
    logger.info("Inside::PostReqeust::Method");
    Client client = Client.create();
    WebResource webResource = client.resource(BASE_SERVICE_URL + serviceEndPoint);
    ObjectWriter objectPrettyWriter = objectWriter.withDefaultPrettyPrinter();
    String input = "";
    ClientResponse response = null;
    try {
      input = objectPrettyWriter.writeValueAsString(request);
      response = webResource.header("Content-Type", MediaType.APPLICATION_JSON)
          .post(ClientResponse.class, input);
      if (response.getStatus() == HttpStatus.SC_OK) {
        String output = response.getEntity(String.class);
        return mapper.readValue(output, responseClass);
      }
    } catch (Exception e) {
      logger.error("Error::PostReqeust::Method", e);
      throw new ChatakPayException(e.getMessage());
    }
    logger.info("Exiting::PostReqeust::Method");
    return null;

  }

  /**
   * Method to invoke REST service object
   * 
   * @param serviceEndPoint
   * @return
   */
  public static ClientResponse postRequest(String serviceEndPoint) {
    Client client = Client.create();
    WebResource webResource = client.resource(BASE_SERVICE_URL + serviceEndPoint);

    ClientResponse response =
        webResource.type(MediaType.APPLICATION_JSON).post(ClientResponse.class);
    return response;
  }

  public static ClientResponse postRequestLogin(String serviceEndPoint) {
    Client client = Client.create();
    WebResource webResource = client.resource(BASE_SERVICE_URL + serviceEndPoint);
    ClientResponse response =
        webResource.type(MediaType.APPLICATION_JSON).post(ClientResponse.class);

    return response;
  }

  public static ClientResponse sendToIssuance(Object request, String serviceEndPoint) {
    Client client = Client.create();
    client.setConnectTimeout(Constants.TIME_OUT);
    issuanceBaseServiceUrl = Properties.getProperty("chatak-issuance.service.url");
    logger.info("Connecting to Issuance URL :: " + issuanceBaseServiceUrl);
    Builder webResource = client.resource(issuanceBaseServiceUrl + serviceEndPoint)
        .header("consumerClientId", Properties.getProperty("chatak-issuance.consumer.client.id"))
        .header("consumerSecret", Properties.getProperty("chatak-issuance.consumer.client.secret"));
    webResource.header(AUTH_HEADER, TOKEN_TYPE_BEARER + getValidIssuanceOAuth2Token());
    webResource.header(PGConstants.DEFAULT_LOCALE, LocaleContextHolder.getLocale());
    ObjectWriter objectPrettyWriter = objectWriter.withDefaultPrettyPrinter();
    String input = "";
    ClientResponse response = null;
    try {
      input = objectPrettyWriter.writeValueAsString(request);
      response = webResource.type(MediaType.APPLICATION_JSON).post(ClientResponse.class, input);
    } catch (Exception e) {
      logger.info("Error:: JsonUtil:: postFee method " + e);
      throw new NoSuchMessageException(messageSource.getMessage(
          ActionErrorCode.ERROR_CODE_API_CONNECT, null, LocaleContextHolder.getLocale()));
    }
    return response;
  }

  public static Object sendToIssuance(Class<?> responseClass, Object request,
      String serviceEndPoint) {

    Client client = Client.create();
    client.setConnectTimeout(Constants.TIME_OUT);
    issuanceBaseServiceUrl = Properties.getProperty("chatak-issuance.service.url");
    logger.info("Connecting to Issuance URL :: " + issuanceBaseServiceUrl + serviceEndPoint);
    Builder webResource = client.resource(issuanceBaseServiceUrl + serviceEndPoint)
        .header("consumerClientId", Properties.getProperty("chatak-issuance.consumer.client.id"))
        .header("consumerSecret", Properties.getProperty("chatak-issuance.consumer.client.secret"));
    webResource.header(AUTH_HEADER, TOKEN_TYPE_BEARER + getValidIssuanceOAuth2Token());
    webResource.header(PGConstants.DEFAULT_LOCALE, LocaleContextHolder.getLocale());
    ObjectWriter objectPrettyWriter = objectWriter.withDefaultPrettyPrinter();
    String input = "";
    ClientResponse response = null;
    try {
      logger.info("sendToIssuance :: " + request);
      input = objectPrettyWriter.writeValueAsString(request);
      response = webResource.type(MediaType.APPLICATION_JSON).post(ClientResponse.class, input);

      logger.info("sendToIssuance :: response: " + response.getStatus());

      if (response.getStatus() == HttpStatus.SC_OK) {
        String output = response.getEntity(String.class);

        logger.info("sendToIssuance :: response output: " + output);

        return mapper.readValue(output, responseClass);
      }
    } catch (Exception e) {
      logger.info("Error:: JsonUtil:: postFee method " + e);
      throw new NoSuchMessageException(messageSource.getMessage(
          ActionErrorCode.ERROR_CODE_API_CONNECT, null, LocaleContextHolder.getLocale()));
    }
    return null;
  }

  private static String getValidIssuanceOAuth2Token() {
    if (isValidToken_fee()) {
      logger.info("getValidIssuanceOAuth2Token :: returning same auth token");
      return OAUTH_TOKEN_FEE;
    } else {

      Client client = Client.create();
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

          HostnameVerifier hostnameVerifier = HttpsURLConnection.getDefaultHostnameVerifier();
          ClientConfig config = new DefaultClientConfig();
          SSLContext ctx = SSLContext.getInstance("SSL");
          ctx.init(null, trustAllCerts, null);
          config.getProperties().put(HTTPSProperties.PROPERTY_HTTPS_PROPERTIES,
              new HTTPSProperties(hostnameVerifier, ctx));
          client = Client.create(config);
        } catch (Exception e) {
          logger.info("Error:: JsonUtil:: getValidOAuth2Token method " + e);;
        }

      }
      Builder webResource =
          client.resource(BASE_PREPAID_SERVICE_URL + ISSUANCE_BASE_ADMIN_OAUTH_SERVICE_URL)
              .header(AUTH_HEADER, getBasicAuthValueForFee());

      logger.info("getValidIssuanceOAuth2Token :: connecting to: " + BASE_PREPAID_SERVICE_URL
          + ISSUANCE_BASE_ADMIN_OAUTH_SERVICE_URL);

      ClientResponse response = null;
      try {
        response = webResource.type(MediaType.APPLICATION_JSON).get(ClientResponse.class);
        String output = response.getEntity(String.class);
        OAuthToken apiResponse = new ObjectMapper().readValue(output, OAuthToken.class);
        OAUTH_TOKEN_FEE = apiResponse.getAccess_token();
        OAUTH_REFRESH_TOKEN_FEE = apiResponse.getRefresh_token();
        tokenValidity_fee = Calendar.getInstance();
        tokenValidity_fee.add(Calendar.SECOND, apiResponse.getExpires_in());

        logger.info("getValidIssuanceOAuth2Token :: retrieved token: " + OAUTH_TOKEN_FEE);
      } catch (Exception e) {
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
  
  private static String refreshOAuth2Token_fee() {
	ClientResponse response = null;
    Client client = Client.create();
    Builder webResource =
        client.resource(issuanceBaseServiceUrl + ISSUANCE_BASE_OAUTH_REFRESH_SERVICE_URL
            + OAUTH_REFRESH_TOKEN_FEE).header(AUTH_HEADER, getBasicAuthValueForFee());
    try {
      response = webResource.type(MediaType.APPLICATION_JSON).get(ClientResponse.class);
      String output = response.getEntity(String.class);
      OAuthToken apiResponse = new ObjectMapper().readValue(output, OAuthToken.class);
      tokenValidity_fee = Calendar.getInstance();
      OAUTH_REFRESH_TOKEN_FEE = apiResponse.getRefresh_token();
      OAUTH_TOKEN_FEE = apiResponse.getAccess_token();
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
    basicAuth = TOKEN_TYPE_BASIC + new String(Base64.encode(basicAuth));
    return basicAuth;
  }

  public static Object sendToTSM(Class<?> responseClass, Object request, String serviceEndPoint) {
    Client client = Client.create();
    client.setConnectTimeout(Constants.TIME_OUT);
    String tsmURL = Properties.getProperty("chatak-tsm.service.url");
    logger.info("Connecting to TSM URL :: " + tsmURL);
    Builder webResource = client.resource(tsmURL + serviceEndPoint)
        .header("consumerClientId", Properties.getProperty("chatak-issuance.consumer.client.id"))
        .header("consumerSecret", Properties.getProperty("chatak-issuance.consumer.client.secret"));

    ObjectWriter objectPrettyWriter = objectWriter.withDefaultPrettyPrinter();
    String input = "";
    ClientResponse response = null;
    try {
      input = objectPrettyWriter.writeValueAsString(request);
      response = webResource.type(MediaType.APPLICATION_JSON).post(ClientResponse.class, input);
      if (response == null){
        LogHelper.logInfo(logger, LoggerMessage.getCallerName(), "TSM Response : " + response);
      } else if (response.getStatus() == HttpStatus.SC_OK) {
        String output = response.getEntity(String.class);
        logger.info("Response from TSM :: " + output);
        return mapper.readValue(output, responseClass);
      } else  {
        LogHelper.logInfo(logger, LoggerMessage.getCallerName(), "TSM Response Status : " + response.getStatus());
      }
    } catch (Exception e) {
      logger.info("Error:: sendToTSM:: postFee method " + e);
      throw new NoSuchMessageException(messageSource.getMessage(
          ActionErrorCode.ERROR_CODE_API_CONNECT, null, LocaleContextHolder.getLocale()));
    }
    return null;
  }
}
