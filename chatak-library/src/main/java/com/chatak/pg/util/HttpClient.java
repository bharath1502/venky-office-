package com.chatak.pg.util;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import org.apache.http.Header;
import org.apache.http.HttpHost;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.config.SocketConfig;
import org.apache.http.conn.routing.HttpRoute;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicHeader;
import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.ObjectWriter;

import com.chatak.pg.exception.HttpClientException;
import com.chatak.pg.exception.PrepaidAdminException;

public class HttpClient {

  private static Logger logger = Logger.getLogger(ByteConversionUtils.class);

  private static final String CONTENT_TYPE = "Content-Type";

  private static final String AUTHORIZATION = "Authorization";

  private static final String AUTHORIZATION_PREFIX = "Bearer ";

  private static final String AUTHORIZATION_BASIC = "Basic ";

  private static final ObjectMapper objectMapper = new ObjectMapper();

  public static final ObjectWriter objectWriter = new ObjectMapper().writer();

  private static final String HTTP_ERROR_CODE = "Failed with HTTP error code : ";

  private PoolingHttpClientConnectionManager cm = null;

  private final String finalURL;

  public HttpClient(String baseURIPath, String apiEndPoint) {
    this.finalURL = baseURIPath + apiEndPoint;
    try {
      cm = new PoolingHttpClientConnectionManager();
      cm.setMaxTotal(Integer.parseInt("500"));
      cm.setDefaultMaxPerRoute(Integer.parseInt("10"));
      URI uri = new URI(finalURL);
      HttpHost host = (uri.getPort() > 0) ? new HttpHost(uri.getHost(), uri.getPort()) : new HttpHost(uri.getHost());
      cm.setMaxPerRoute(new HttpRoute(host), Integer.parseInt("200"));
    }
    catch(URISyntaxException e) {
      logger.error("HttpClient :: HttpClient method", e);
    }
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
    cm = getHttpPoolManager();
    CloseableHttpClient httpClient = HttpClients.custom().setConnectionManager(cm).build();
    CloseableHttpResponse httpResponse = null;
    try {
      logger.info("Calling GET API - " + (finalURL));
      HttpGet getRequest = new HttpGet(finalURL);
      getRequest.addHeader(CONTENT_TYPE, ContentType.APPLICATION_JSON.getMimeType());
      if(accessToken != null) {
        getRequest.addHeader(AUTHORIZATION, AUTHORIZATION_PREFIX + accessToken);
      }
      httpResponse = httpClient.execute(getRequest);
      int statusCode = httpResponse.getStatusLine().getStatusCode();
      if(statusCode != HttpStatus.SC_OK) {
        throw new HttpClientException(HTTP_ERROR_CODE, statusCode);
      }

      T resultantObject = objectMapper.readValue(httpResponse.getEntity().getContent(), response);
      logger.info("Successfull GET API call - " + (finalURL));
      return resultantObject;
    }
    catch(Exception e) {
      logger.error("ERROR in calling GET API " + (finalURL), e);
    }
    finally {
      closeResources(httpClient, httpResponse, cm);
    }
    logger.error("ERROR in calling GET API and rerurning NULL " + (finalURL));
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

    cm = getHttpPoolManager();
    CloseableHttpClient httpClient = HttpClients.custom().setConnectionManager(cm).build();
    CloseableHttpResponse httpResponse = null;
    try {
      logger.info("Calling POST API - " + (finalURL));
      HttpPost postReq = new HttpPost(finalURL);
      postReq.addHeader(CONTENT_TYPE, ContentType.APPLICATION_JSON.getMimeType());
      if(accessToken != null) {
        postReq.addHeader(AUTHORIZATION, AUTHORIZATION_PREFIX + accessToken);
      }
      postReq.setEntity(new StringEntity(objectWriter.writeValueAsString(request)));
      httpResponse = httpClient.execute(postReq);
      int statusCode = httpResponse.getStatusLine().getStatusCode();
      if(statusCode != HttpStatus.SC_OK) {
        throw new HttpClientException(HTTP_ERROR_CODE, statusCode);
      }

      T resultantObject = objectMapper.readValue(httpResponse.getEntity().getContent(), response);
      logger.info("Successfull POST API call - " + (finalURL));
      return resultantObject;
    }
    catch(Exception ex) {
      logger.error("ERROR:: HttpClient:: invokePost method", ex);
    }
    finally {
      closeResources(httpClient, httpResponse, cm);
    }
    logger.error("ERROR in calling POST API and rerurning NULL " + (finalURL));
    return null;
  }

  private <T> T invokeBasicAuth(Object request, Class<T> response, String accessToken) {
    cm = getHttpPoolManager();
    CloseableHttpClient httpClient = HttpClients.custom().setConnectionManager(cm).build();
    CloseableHttpResponse httpResponse = null;
    try {
      logger.info("Calling POST API - " + (finalURL));
      HttpPost postRequest = new HttpPost(finalURL);
      postRequest.addHeader(CONTENT_TYPE, ContentType.APPLICATION_JSON.getMimeType());
      if(accessToken != null) {
        postRequest.addHeader(AUTHORIZATION, AUTHORIZATION_BASIC + accessToken);
      }
      postRequest.setEntity(new StringEntity(objectWriter.writeValueAsString(request)));
      httpResponse = httpClient.execute(postRequest);
      int statusCode = httpResponse.getStatusLine().getStatusCode();
      if(statusCode != HttpStatus.SC_OK) {
        throw new HttpClientException(HTTP_ERROR_CODE, statusCode);
      }

      T resultantObject = objectMapper.readValue(httpResponse.getEntity().getContent(), response);
      logger.info("Successfull POST API call - " + (finalURL));
      return resultantObject;
    }
    catch(Exception e) {
      logger.error("ERROR:: HttpClient:: invokePost method", e);
    }
    finally {
      closeResources(httpClient, httpResponse, cm);
    }
    logger.error("ERROR in calling POST API and rerurning NULL " + (finalURL));
    return null;
  }

  /**
   * @return
   */
  private PoolingHttpClientConnectionManager getHttpPoolManager() {
    cm = new PoolingHttpClientConnectionManager();
    cm.setMaxTotal(Integer.parseInt("30"));
    cm.setDefaultMaxPerRoute(Integer.parseInt("30"));

    SocketConfig socketConfig = SocketConfig.custom().setSoTimeout(Integer.parseInt("30000")).build();
    cm.setDefaultSocketConfig(socketConfig);
    return cm;
  }

  private void closeResources(CloseableHttpClient httpClient,
                              CloseableHttpResponse httpResponse,
                              PoolingHttpClientConnectionManager cm) {
    try {
      if(null != httpResponse) {
        httpResponse.close();
      }
      if(null != httpClient) {
        httpClient.close();
      }
      cm.close();
    }
    catch(Exception e) {
      logger.error("ERROR:: HttpClient:: closeResources method while closing the httpClient", e);
    }
  }

  @SuppressWarnings("unchecked")
  public <T> T invokePost(Object request, Class<T> response, Header[] headers) throws PrepaidAdminException,HttpClientException {

    CloseableHttpClient httpClient = null;
    CloseableHttpResponse httpResponse = null;
    T resultantObject = null;
    try {
      logger.info("Calling POST API - " + (finalURL));
      HttpPost postRequest = new HttpPost(finalURL);
      postRequest.addHeader(CONTENT_TYPE, ContentType.APPLICATION_JSON.getMimeType());
      postRequest.setHeaders(headers);
      httpClient = HttpClients.custom().setConnectionManager(cm).build();

      String jsonRequest = objectWriter.writeValueAsString(request);
      logger.info("Request PayLoad: " + jsonRequest);
      postRequest.setEntity(new StringEntity(jsonRequest));
      httpResponse = httpClient.execute(postRequest);
      int statusCode = httpResponse.getStatusLine().getStatusCode();
      if (statusCode == HttpStatus.SC_UNAUTHORIZED) {
        LogHelper.logInfo(logger, LoggerMessage.getCallerName(), "Error Status Code : 401");
        throw new PrepaidAdminException("401");
      }
      if(statusCode != HttpStatus.SC_OK) {
        LogHelper.logInfo(logger, LoggerMessage.getCallerName(), "Error Status Code : " + statusCode);
        throw new HttpClientException(HTTP_ERROR_CODE, statusCode);
      }
      if(response.getName() instanceof java.lang.String) {
        resultantObject = (T) objectMapper.readValue(httpResponse.getEntity().getContent(), Object.class);
        resultantObject = (T) objectWriter.writeValueAsString(resultantObject);
      }
      else {
        resultantObject = objectMapper.readValue(httpResponse.getEntity().getContent(), response);
      }
      logger.info("Successfull POST API call - " + (finalURL));

      return resultantObject;
    }
    catch(RuntimeException e) {
      throw e;
    }
    catch(PrepaidAdminException e) {
      
      throw e;
    }
    catch(Exception e) {
      logger.error("HttpClient :: invokePost method", e);
    }
    finally {
      try {
        if(null != httpClient) {
          httpClient.close();
        }
        if(null != httpResponse) {
          httpResponse.close();
        }
      }
      catch(IOException e) {
        logger.error("HttpClient :: invokePost method while closing the httpClient", e);
      }
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

  @SuppressWarnings("unchecked")
  public <T> T invokePost(Class<T> response, Header[] headers) throws HttpClientException {

    CloseableHttpClient httpClient = null;
    CloseableHttpResponse httpResponse = null;
    T resultantObject = null;
    try {
      logger.info("HttpClient :: invokePost method");
      HttpPost postRequest = new HttpPost(finalURL);
      postRequest.addHeader(CONTENT_TYPE, ContentType.APPLICATION_JSON.getMimeType());
      postRequest.setHeaders(headers);
      httpClient = HttpClients.custom().setConnectionManager(cm).build();
      LogHelper.logInfo(logger, LoggerMessage.getCallerName(), "connecting to : " + finalURL + " With Headers : " + headers);

      httpResponse = httpClient.execute(postRequest);
      int statusCode = httpResponse.getStatusLine().getStatusCode();
      if(statusCode != HttpStatus.SC_OK) {
        LogHelper.logInfo(logger, LoggerMessage.getCallerName(), "Error Status Code : " + statusCode);
        throw new HttpClientException(HTTP_ERROR_CODE, statusCode);
      }
      if(response.getName() instanceof java.lang.String) {
        resultantObject = (T) objectMapper.readValue(httpResponse.getEntity().getContent(), Object.class);
        resultantObject = (T) objectWriter.writeValueAsString(resultantObject);
      }
      else {
        resultantObject = objectMapper.readValue(httpResponse.getEntity().getContent(), response);
      }

      logger.info("HttpClient :: invokePost method");
      return resultantObject;
    }
    catch(RuntimeException e) {
      throw e;
    }
    catch(HttpClientException e) {
      logger.error("HttpClient :: invokePost method", e);
      throw e;
    }
    catch(Exception e) {
      logger.error("HttpClient :: invokePost method", e);
    }
    finally {
      try {
        if(null != httpClient) {
          httpClient.close();
        }
        if(null != httpResponse) {
          httpResponse.close();
        }
      }
      catch(IOException e) {
        logger.error("HttpClient :: invokePost method while closing the httpClient", e);
      }
    }
    return null;
  }

  public <T extends Object> T invokeGetCommon(Class<T> response, Header[] headers) throws IOException {
    cm = getHttpPoolManager();
    CloseableHttpClient httpClient = HttpClients.custom().setConnectionManager(cm).build();
    CloseableHttpResponse httpResponse = null;
    try {
      logger.info("Calling GET API - " + (finalURL));
      HttpGet getRequest = new HttpGet(finalURL);
      getRequest.addHeader(CONTENT_TYPE, ContentType.APPLICATION_JSON.getMimeType());
      getRequest.setHeaders(headers);
      httpResponse = httpClient.execute(getRequest);
      int statusCode = httpResponse.getStatusLine().getStatusCode();
      if(statusCode != HttpStatus.SC_OK) {
        throw new HttpClientException(HTTP_ERROR_CODE, statusCode);
      }
      T resultantObject = objectMapper.readValue(httpResponse.getEntity().getContent(), response);
      logger.info("Successfull GET API call - " + (finalURL));
      return resultantObject;
    }
    catch(Exception e) {
      logger.error("ERROR in calling GET API " + (finalURL), e);
    }
    finally {
      closeResources(httpClient, httpResponse, cm);
    }
    logger.error("ERROR in calling GET API and rerurning NULL " + (finalURL));
    return null;
  }
}