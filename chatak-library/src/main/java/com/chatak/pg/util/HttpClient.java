package com.chatak.pg.util;

import java.io.IOException;

import org.apache.http.Header;
import org.apache.http.HttpStatus;
import org.apache.http.entity.ContentType;
import org.apache.http.message.BasicHeader;
import org.apache.log4j.Logger;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.chatak.pg.exception.HttpClientException;
import com.chatak.pg.exception.PrepaidAdminException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

public class HttpClient {

  private static Logger logger = Logger.getLogger(ByteConversionUtils.class);

  private static final String CONTENT_TYPE = "Content-Type";

  private static final String AUTHORIZATION = "Authorization";

  private static final String AUTHORIZATION_PREFIX = "Bearer ";

  private static final String AUTHORIZATION_BASIC = "Basic ";

  private static final ObjectMapper objectMapper = new ObjectMapper();

  public static final ObjectWriter objectWriter = new ObjectMapper().writer();

  private static final String HTTP_ERROR_CODE = "Failed with HTTP error code : ";
  
  private static final int THREAD_POOL_SIZE = Integer.parseInt(Properties.getProperty("thread.pool.size"));

  private static final int THREAD_MAX_PER_ROUTE = Integer.parseInt(Properties.getProperty("thread.max.per.route"));

  private RestTemplate restTemplate = null;

  private final String finalURL;

  public HttpClient(String baseURIPath, String apiEndPoint) {
    this.finalURL = baseURIPath + apiEndPoint;
    this.restTemplate = HttpConfig.getInstance().getRestTemplate();
  }

  /**
   * Method to call GET REST Service API
   * 
   * @param response
   *          - Response Class
   * @return
   * @throws IOException
   */
  public <T extends Object> T invokeGet(Class<T> response, Header[] headers) throws IOException {
    return invokeGetCommon(response, headers);
  }

  /**
   * Method to call GET REST Service API
   * 
   * @param response
   *          - Response Class
   * @param accessToken
   *          - Access Token
   * @return
   * @throws IOException
   */
  public <T extends Object> T invokeGet(Class<T> response, String accessToken) throws IOException {
    return invokeGetCommon(response, accessToken);
  }

  private <T extends Object> T invokeGetCommon(Class<T> response, String accessToken) throws IOException {
    logger.info("Entering :: HttpClient :: invokeGetCommon");

    try {
      logger.info("Calling GET API - " + (finalURL));
      setHeadersEntity(accessToken);
      
      ResponseEntity<T> resultantObject = restTemplate.getForEntity(finalURL, response);
      validateResponseStatusCodeNotOK(resultantObject.getStatusCode().value());
      logger.info("Resultant Object After convertion: " + resultantObject.getBody());
      logger.info("Exiting :: HttpClient :: invokeGetCommon");
      return resultantObject.getBody();
    } catch (Exception e) {
      logger.error("ERROR in invokeGetCommon method", e);
    }
    logger.info("Exiting :: HttpClient :: invokeGetCommon ::   ERROR in calling GET API and rerurning NULL " + (finalURL));
    return null;
  }

  /**
   * Method to call POST REST Service API
   * 
   * @param request
   *          - Request Pay Load object
   * @param response
   *          - Response Class
   * @return
   */
  public <T> T invokePost(Object request, Class<T> response) {

    return invokePostCommon(request, response, null);
  }

  /**
   * @param request
   * @param response
   * @return
   */
  public <T> T invokePost(Object request, Class<T> response, boolean basicAuth, String serviceToken) {
    if(basicAuth) {
      return invokeBasicAuth(request, response, serviceToken);
    }
    else {
      return invokePostCommon(request, response, serviceToken);
    }

  }

  /**
   * Method to call POST REST Service API
   * 
   * @param request
   *          - Request Pay Load object
   * @param response
   *          - Response Class
   * @param accessToken
   *          - Access Token
   * @return
   */
  public <T> T invokePost(Object request, Class<T> response, String accessToken) {
    return invokePostCommon(request, response, accessToken);
  }

  private <T> T invokePostCommon(Object request, Class<T> response, String accessToken) {
    logger.info("Entering :: HttpClient :: invokePostCommon");
    try {
      logger.info("Calling POST API - " + (finalURL));
      
      ResponseEntity<T> resultantObject = restTemplate.exchange(finalURL, HttpMethod.POST, new HttpEntity<Object>(request, setHeadersEntity(accessToken)), response);
      
      validateResponseStatusCodeNotOK(resultantObject.getStatusCode().value());
      logger.info("Resultant Object After convertion: " + resultantObject.getBody());
      logger.info("Exiting :: HttpClient :: invokePostCommon");
      return resultantObject.getBody();
    } catch (Exception ex) {
      logger.error("ERROR in invokePostCommon method", ex);
    }
    logger.info("Exiting :: HttpClient :: invokePostCommon ::  ERROR in calling POST API and rerurning NULL " + (finalURL));
    return null;
  }

  private <T> T invokeBasicAuth(Object request, Class<T> response, String accessToken) {
    logger.info("Entering :: HttpClient :: invokeBasicAuth");
    try {
      logger.info("Calling POST API - " + (finalURL));
      
      
      ResponseEntity<T> resultantObject = restTemplate.exchange(finalURL, HttpMethod.POST, new HttpEntity<Object>(request, setHeadersEntity(accessToken)), response);
      
      validateResponseStatusCodeNotOK(resultantObject.getStatusCode().value());
      logger.info("Resultant Object After convertion: " + resultantObject.getBody());
      logger.info("Exiting :: HttpClient :: invokeBasicAuth");
      return resultantObject.getBody();
    } catch (Exception e) {
      logger.error("ERROR in invokeBasicAuth method", e);
    }
    logger.info("Exiting :: HttpClient :: invokeBasicAuth :: ERROR in calling POST API and rerurning NULL " + (finalURL));
    return null;
  }


  public <T> T invokePost(Object request, Class<T> response, Header[] headers, Boolean isSensitiveData) throws PrepaidAdminException,HttpClientException {
    logger.info("Entering :: HttpClient :: invokePost");
    try {
      logger.info("Calling POST API - " + (finalURL));
      
      ResponseEntity<T> resultantObject = restTemplate.exchange(finalURL, HttpMethod.POST, new HttpEntity<Object>(request, setHeadersEntity(null, headers)), response);
      validateResponseStatusCode(resultantObject.getStatusCode().value());
      
      // PERF >> This method is causing object wait timeout
      //processSensitiveData(isSensitiveData, resultantObject, "Resultant Object After convertion: ");
      logger.info("Exiting :: HttpaygatepClient :: invokePost");
      return resultantObject.getBody();
    } catch (RuntimeException e) {
      logger.error("ERROR in invokePost method", e);
      throw e;
    } catch (HttpClientException e) {
      logger.error("ERROR in invokePost method", e);
      throw e;
    } catch (PrepaidAdminException e) {
      logger.info("Status : " + HttpStatus.SC_UNAUTHORIZED);
      throw e;
    } catch (Exception e) {
      logger.error("ERROR in invokePost method", e);
    }
    return null;
  }

  public static Header[] getHeaders(String token) {
    Header[] headers = { new BasicHeader(CONTENT_TYPE, ContentType.APPLICATION_JSON.getMimeType()),
                         new BasicHeader(AUTHORIZATION, getBasicAuthValue(token)) };
    return headers;
  }

  private static String getBasicAuthValue(String token) {
    return "Basic " + token;
  }

  public <T> T invokePost(Class<T> response, Header[] headers) throws HttpClientException {
    logger.info("Entering :: HttpClient :: invokePost");
    try {
      logger.info("Calling POST API - " + (finalURL));
      ResponseEntity<T> resultantObject = restTemplate.exchange(finalURL, HttpMethod.POST, new HttpEntity<T>(null, setHeadersEntity(null, headers)), response);
      validateResponseStatusCode(resultantObject.getStatusCode().value());
      logger.info("Resultant Object After convertion: " + resultantObject.getBody());
      logger.info("Exiting :: HttpClient :: invokePost");
      return resultantObject.getBody();
    } catch (RuntimeException e) {
      logger.error("ERROR in invokePost method", e);
      throw e;
    } catch (HttpClientException e) {
      logger.error("ERROR in invokePost method", e);
      throw e;
    } catch (Exception e) {
      logger.error("ERROR in invokePost method", e);
    }
    return null;
  }

  public <T extends Object> T invokeGetCommon(Class<T> response, Header[] headers) throws IOException {
    logger.info("Entering :: DateUtil :: invokeGetCommon");
    try {
      logger.info("Calling GET API - " + (finalURL));
      
      ResponseEntity<T> resultantObject = restTemplate.exchange(finalURL, HttpMethod.GET, new HttpEntity<T>(null, setHeadersEntity(null, headers)), response);
      validateResponseStatusCode(resultantObject.getStatusCode().value());
      
      logger.info("Resultant Object After convertion: " + resultantObject.getBody());
      logger.info("Exiting :: HttpClient :: invokeGetCommon");
      return resultantObject.getBody();
    } catch (Exception e) {
      logger.error("ERROR in calling GET API " + (finalURL), e);
    }
    logger.info("Exiting :: HttpClient :: invokeGetCommon :: ERROR in calling GET API and rerurning NULL " + (finalURL));
    return null;
  }
  
  private void processSensitiveData(Boolean isSensitiveData, String jsonRequest, String logInfo) {
    if(!StringUtils.isNull(isSensitiveData) && isSensitiveData) {
      String maskedjsonStringRes = CommonUtil.maskJsonString((String)jsonRequest);
          logger.info(logInfo + maskedjsonStringRes);
    } else {
          logger.info(logInfo + jsonRequest);
    }
  }
  
  private HttpHeaders setHeadersEntity(String accessToken, Header[] headerArray) {
    HttpHeaders headers = new HttpHeaders();
    if(null != headerArray) {
      for (Header header : headerArray) {
        headers.add(header.getName(), header.getValue());
      }
    }
    return headers;
  }
  
  private HttpHeaders setHeadersEntity(String accessToken) {
    
    HttpHeaders headers = new HttpHeaders();
    headers.add(CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
    if (accessToken != null) {
      headers.add(AUTHORIZATION, AUTHORIZATION_PREFIX + accessToken);
    }
    return headers;
  }
  
  private void validateResponseStatusCodeNotOK(int statusCode) throws HttpClientException {
    logger.info("HTTP Status Code: " + statusCode);
    if (statusCode != HttpStatus.SC_OK) {
      logger.info("Error Status Code : " + statusCode);
      throw new HttpClientException(HTTP_ERROR_CODE, statusCode);
    }
  }
  
  private void validateResponseStatusCode(int statusCode) throws HttpClientException, PrepaidAdminException {
    logger.info("HTTP Status Code: " + statusCode);
    if (statusCode == HttpStatus.SC_UNAUTHORIZED) {
      logger.info("Error Status Code : 401");
      throw new PrepaidAdminException("401");
    }
    if (statusCode != HttpStatus.SC_OK) {
      logger.info("Error Status Code : " + statusCode);
      throw new HttpClientException(HTTP_ERROR_CODE, statusCode);
    }
  }
  
}
