package com.chatak.merchant.util;

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


import com.chatak.merchant.exception.ChatakMerchantException;
import com.chatak.pg.constants.ActionErrorCode;
import com.chatak.pg.exception.HttpClientException;
import com.chatak.pg.exception.PrepaidAdminException;
import com.chatak.pg.model.OAuthToken;
import com.chatak.pg.util.HttpClient;
import com.chatak.pg.util.Properties;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

public class JsonUtil {
  private JsonUtil() {
    super();
  }

  private static final Logger logger = Logger.getLogger(JsonUtil.class);

  public static final String BASE_SERVICE_URL = Properties.getProperty("chatak-merchant.service.url");

  public static final String BASE_ADMIN_OAUTH_SERVICE_URL = Properties.getProperty("chatak-merchant.oauth.service.url");

  public static final String BASE_OAUTH_REFRESH_SERVICE_URL = Properties.getProperty("chatak-merchant.oauth.refresh.service.url");

  public static final String BASE_PREPAID_SERVICE_URL = Properties.getProperty("prepaid.service.url");

  public static final String BASE_PREPAID_ADMIN_OAUTH_SERVICE_URL = Properties.getProperty("prepaid.admin.oauth.service.url");

  public static final ObjectWriter objectWriter = new ObjectMapper().writer();

  private static String TOKEN_TYPE_BEARER = "Bearer ";

  private static String TOKEN_TYPE_BASIC = "Basic ";

  private static String AUTH_HEADER = "Authorization";

  private static String OAUTH_TOKEN = null;

  private static String OAUTH_REFRESH_TOKEN = null;

  private static Long tokenValidity = null;

  private static String issuanceBaseServiceUrl = "";

  public static final String ISSUANCE_BASE_ADMIN_OAUTH_SERVICE_URL = Properties.getProperty("chatak-issuance.oauth.service.url");

  public static final String ISSUANCE_BASE_OAUTH_REFRESH_SERVICE_URL = Properties.getProperty("chatak-issuance.oauth.refresh.service.url");

  private static String OAUTH_TOKEN_FEE = null;

  private static String OAUTH_REFRESH_TOKEN_FEE = null;

  private static Long tokenValidityFee = null;

  private static int refershRequestCount = 0;

  private static int refershRequestCountPay = 0;

  private static final int MAX_RETRY_COUNT = 3;

  private static String CHATAK_ISSUENCE_SERVICE_URL = BASE_PREPAID_SERVICE_URL + "/rest";

  private static String OAUTH_TOKEN_ISSUANCE = null;

  private static Long issuanceTokenValidity = null;

  public static String convertObjectToJSON(Object object) throws ChatakMerchantException {
    String input = "";
    ObjectWriter objectWriter = new ObjectMapper().writer().withDefaultPrettyPrinter();
    try {
      input = objectWriter.writeValueAsString(object);
      return input;
    }
    catch(JsonGenerationException e) {
      logger.error("Error:: JsonUtil:: JsonGenerationException ",e);
      throw new ChatakMerchantException(e.getMessage());
    }
    catch(JsonMappingException e) {
      logger.error("Error:: JsonUtil:: JsonMappingException ",e); 
      throw new ChatakMerchantException(e.getMessage());
    }
    catch(IOException e) {
      logger.error("Error:: JsonUtil:: IOException ",e);
      throw new ChatakMerchantException(e.getMessage());
    }
  }

  public static Object convertJSONToObject(String jsonData, Class<?> c) throws ChatakMerchantException {
    ObjectMapper mapper = new ObjectMapper();
    try {
      return mapper.readValue(jsonData, c);
    }
    catch(JsonParseException e) {
      logger.error("Error:: JsonUtil:: JsonGenerationException ",e);
      throw new ChatakMerchantException(e.getMessage());
    }
    catch(JsonMappingException e) {
      logger.error("Error:: JsonUtil:: JsonMappingException ",e);
      throw new ChatakMerchantException(e.getMessage());
    }
    catch(IOException e) {
      logger.error("Error:: JsonUtil:: IOException ",e);
      throw new ChatakMerchantException(e.getMessage());
    }
  }

  /**
   * @param request
   * @param serviceEndPoint
   * @return
   */

  public static <T extends Object> T  postRequest(Object request, String serviceEndPoint,Class<T> response) throws Exception, HttpClientException{
		T resultantObject = null;
		HttpClient httpClient = new HttpClient(BASE_SERVICE_URL, serviceEndPoint);
		try {
			Header[] headers = new Header[] {
					new BasicHeader("consumerClientId", Properties.getProperty("chatak-merchant.consumer.client.id")),
					new BasicHeader("consumerSecret", Properties.getProperty("chatak-merchant.consumer.client.secret")),
					new BasicHeader(Properties.getProperty("prepaid-admin.header.param.user.type"),
							Properties.getProperty("prepaid-admin.header.user.type")),
					new BasicHeader(AUTH_HEADER, TOKEN_TYPE_BEARER + getValidOAuth2Token()),
					new BasicHeader("content-type", ContentType.APPLICATION_JSON.getMimeType()) };
			resultantObject = httpClient.invokePost(request, response, headers, true);
			logger.info("Connecting to Gate way URL ::" + BASE_SERVICE_URL);
			refershRequestCountPay = 0;
			logger.info("Exiting JsonUtil :: postRequest");
		} catch (PrepaidAdminException e) {
			logger.info("Error :: JsonUtil :: postRequest : " + e.getMessage(), e);
			logger.info("Requesting oauth ::");
			if (refershRequestCount < MAX_RETRY_COUNT) {
				refreshOAuth2Token();
				refershRequestCountPay++;
				return postRequest(request, serviceEndPoint, response);
			}
		} catch (Exception e) {
			logger.info("Error:: JsonUtil:: postRequest method " + e);
			throw new ChatakMerchantException(ActionErrorCode.ERROR_CODE_API_CONNECT);
		}
		return resultantObject;

	}

  /**
   * @param serviceEndPoint
   * @return
   */
  public static <T extends Object> T postRequest(String serviceEndPoint, Class<T> response) throws ChatakMerchantException, HttpClientException {
	  T resultantObject = null;
	    HttpClient httpClient=new HttpClient(BASE_SERVICE_URL, serviceEndPoint);
		  try {
				Header[] headers = new Header[] {
						new BasicHeader("consumerClientId",Properties.getProperty("chatak-merchant.consumer.client.id")),
						new BasicHeader("consumerSecret",Properties.getProperty("chatak-merchant.consumer.client.secret")),
						new BasicHeader(AUTH_HEADER, TOKEN_TYPE_BEARER + getValidOAuth2Token()),
						new BasicHeader("content-type", ContentType.APPLICATION_JSON.getMimeType())};
				resultantObject = httpClient.invokePost(response, headers);
		  }catch(Exception e) {
		      logger.info("Error:: JsonUtil:: postRequest method "+e);
		      throw new ChatakMerchantException(ActionErrorCode.ERROR_CODE_API_CONNECT);
		    }
    return resultantObject;
		  
  }

  /**
   * Method to get Basic Auth value
   * 
   * @return
   */
  private static String getBasicAuthValue() {
    String basicAuth = Properties.getProperty("chatak-merchant.oauth.basic.auth.username") + ":"
                       + Properties.getProperty("chatak-merchant.oauth.basic.auth.password");
    basicAuth = TOKEN_TYPE_BASIC + new String(Base64.getEncoder().encode(basicAuth.getBytes()));
    return basicAuth;
  }

  /**
   * Method to get OAUTH token
   * 
   * @return
   */
  private static String getValidOAuth2Token() {
	  String output = null;
    if(isValidToken()) {
      return OAUTH_TOKEN;
    }
    else {
      if (BASE_SERVICE_URL.startsWith("https")) {
        TrustManager[] trustAllCerts = new TrustManager[] {new X509TrustManager() {

          @Override
          public java.security.cert.X509Certificate[] getAcceptedIssuers() {

            return new java.security.cert.X509Certificate[0];
          }

          @Override
          public void checkServerTrusted(java.security.cert.X509Certificate[] arg0, String arg1)
              throws CertificateException {
        	  try {
					arg0[0].checkValidity();
				} catch (CertificateException e) {
					logger.info("Error:: JsonUtil:: checkServerTrusted method ");
					throw new CertificateException("Certificate not valid or trusted.");
				}
          }

          @Override
          public void checkClientTrusted(java.security.cert.X509Certificate[] arg0, String arg1)
              throws CertificateException {
        	  try {
					arg0[0].checkValidity();
				} catch (CertificateException e) {
					logger.info("Error:: JsonUtil:: checkClientTrusted method ");
					throw new CertificateException("Certificate not valid or trusted.");
				}
          }
        }};


      // Install the all-trusting trust manager
      try {
          SSLContext sc = SSLContext.getInstance("TLSv1.2");
          sc.init(null, trustAllCerts, new SecureRandom());
          HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
      } catch (Exception e) {
        logger.info("Error:: JsonUtil:: getValidOAuth2Token method "+e); 
      }

      }
      
      HttpClient httpClient=new HttpClient(BASE_SERVICE_URL, BASE_ADMIN_OAUTH_SERVICE_URL);
      try {
			Header[] headers = new Header[] {
					new BasicHeader(AUTH_HEADER,getBasicAuthValue()),
					new BasicHeader("content-type", ContentType.APPLICATION_JSON.getMimeType())};
			output = httpClient.invokePost(String.class, headers);
        OAuthToken apiResponse = new ObjectMapper().readValue(output, OAuthToken.class);
        OAUTH_TOKEN = apiResponse.getAccess_token();
        OAUTH_REFRESH_TOKEN = apiResponse.getRefresh_token();
        tokenValidity = System.currentTimeMillis() + (apiResponse.getExpires_in() * 60);
      }
      catch(Exception e) {
        logger.info("Error:: JsonUtil:: getValidOAuth2Token method "+e); ;
      }
    }
    return OAUTH_TOKEN;
  }
  
  /**
   * Method to refresh the Oauth token when token is getting expired
   * 
   * @return
   */  
  private static <T> String refreshOAuth2Token() {
		HttpClient httpClient = new HttpClient(BASE_SERVICE_URL + BASE_OAUTH_REFRESH_SERVICE_URL, OAUTH_REFRESH_TOKEN);
		T resultantObject = null;
		try {
			Class<T> response = null;
			Header[] headers = new Header[] { new BasicHeader(AUTH_HEADER, getBasicAuthValue()),
					new BasicHeader("content-type", ContentType.APPLICATION_JSON.getMimeType()) };
			resultantObject = httpClient.invokeGet(response, headers);
			OAuthToken apiResponse = (OAuthToken) resultantObject;
			OAUTH_TOKEN = apiResponse.getAccess_token();
			OAUTH_REFRESH_TOKEN = apiResponse.getRefresh_token();
			tokenValidity = System.currentTimeMillis() + (apiResponse.getExpires_in() * 60);
			return OAUTH_TOKEN;
		} catch (Exception e) {
			logger.info("Error:: JsonUtil:: refreshOAuth2Token method " + e);
		}
		return null;
	}

  /**
   * Method to check valid token
   * 
   * @return
   */
  private static boolean isValidToken() {
    if(OAUTH_TOKEN == null || tokenValidity == null) {
      return false;
    }
    else if(System.currentTimeMillis() > tokenValidity) {
      OAUTH_TOKEN = null;
      return (null != refreshOAuth2Token());
    }
    else {
      return true;
    }
  }
  
  public static <T extends Object> T sendToIssuance(Object request, String serviceEndPoint,String mode,Class<T> response) throws HttpClientException {
		T resultantObject = null;
		HttpClient httpClient = new HttpClient(issuanceBaseServiceUrl, serviceEndPoint);
		try {
			Header[] headers = new Header[] {
					new BasicHeader("consumerClientId", Properties.getProperty("chatak-issuance.consumer.client.id")),
					new BasicHeader("consumerSecret", Properties.getProperty("chatak-issuance.consumer.client.secret")),
					new BasicHeader(AUTH_HEADER, TOKEN_TYPE_BEARER + getValidOAuth2TokenForFee()),
					new BasicHeader("content-type", ContentType.APPLICATION_JSON.getMimeType()) };
			resultantObject = httpClient.invokePost(request, response, headers, false);
			issuanceBaseServiceUrl = ProcessorConfig.get(ProcessorConfig.FEE_SERVICE + mode);
			logger.info("Connecting to Issuance URL :: " + issuanceBaseServiceUrl);
		} catch (PrepaidAdminException e) {
		    logger.info("Error :: JsonUtil :: sendToIssuance : " + e.getMessage(), e);
			logger.info("Requesting oauth ::");
			if (refershRequestCount == 0) {
				refreshOAuth2Token_fee();
				refershRequestCount++;
				return sendToIssuance(request, serviceEndPoint, mode, response);
			}
		} catch (Exception e) {
			logger.info("Error:: JsonUtil:: postFee method " + e);
			throw new HttpClientException(ActionErrorCode.ERROR_CODE_API_CONNECT, Integer.parseInt("404"));
		}
		return resultantObject;
	}
  
  
  private static String getValidOAuth2TokenForFee() {
		String output = null;
		if (isValidTokenFee()) {
			return OAUTH_TOKEN_FEE;
		} else {
			if (issuanceBaseServiceUrl.startsWith("https")) {
				TrustManager[] trustAllCerts = new TrustManager[] { new X509TrustManager() {

					@Override
					public java.security.cert.X509Certificate[] getAcceptedIssuers() {

						return new java.security.cert.X509Certificate[0];
					}

					@Override
					public void checkServerTrusted(java.security.cert.X509Certificate[] arg0, String arg1)
							throws CertificateException {
						try {
							arg0[0].checkValidity();
						} catch (CertificateException e) {
							logger.info("Error:: JsonUtil:: checkServerTrusted method ");
							throw new CertificateException("Certificate not valid or trusted.");
						}
					}

					@Override
					public void checkClientTrusted(java.security.cert.X509Certificate[] arg0, String arg1)
							throws CertificateException {
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
					logger.info("Error:: JsonUtil:: getValidOAuth2Token method " + e);
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
  
  private static String getBasicAuthValueForFee() {
	    String basicAuth = Properties.getProperty("chatak-issuance.oauth.basic.auth.username") + ":"
	                       + Properties.getProperty("chatak-issuance.oauth.basic.auth.password");
	    basicAuth = TOKEN_TYPE_BASIC + new String(Base64.getEncoder().encode(basicAuth.getBytes()));
	    return basicAuth;
	  }
  private static boolean isValidTokenFee() {
  if(OAUTH_TOKEN_FEE == null || tokenValidityFee == null) {
    return false;
		} else if(System.currentTimeMillis() > tokenValidityFee) {
			OAUTH_TOKEN_FEE = null;
    return (null != refreshOAuth2Token_fee());
  }
  else {
    return true;
  }
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
  
	public static <T extends Object> T postIssuanceRequest(Object request, String serviceEndPoint, Class<T> response)
			throws Exception {
		T resultantObject = null;
		HttpClient httpClient = new HttpClient(CHATAK_ISSUENCE_SERVICE_URL, serviceEndPoint);
		try {
			Header[] headers = new Header[] {
					new BasicHeader("consumerClientId", Properties.getProperty("prepaid-admin.consumer.client.id")),
					new BasicHeader("consumerSecret", Properties.getProperty("prepaid-admin.consumer.client.secret")),
					new BasicHeader(AUTH_HEADER, TOKEN_TYPE_BEARER + getValidOAuthToken()),
					new BasicHeader("content-type", ContentType.APPLICATION_JSON.getMimeType()) };
			resultantObject = httpClient.invokePost(request, response, headers, false);
			logger.info("Connecting to Gate way URL ::" + CHATAK_ISSUENCE_SERVICE_URL + serviceEndPoint);
			logger.info("Received response from Gate way URL :: response: " + response + ", refershRequestCountPay: "
					+ refershRequestCountPay);
		} catch (PrepaidAdminException e) {
		    logger.info("Error :: JsonUtil :: postIssuanceRequest : " + e.getMessage(), e);
			logger.info("Requesting oauth ::");
			if (refershRequestCountPay < MAX_RETRY_COUNT) {
				logger.info("Requesting oauth ::");
				OAUTH_TOKEN_ISSUANCE = null;
				issuanceTokenValidity = null;
				getValidOAuthToken();
				refershRequestCountPay++;
				return postIssuanceRequest(request, serviceEndPoint, response);
			}
			refershRequestCountPay = 0;
			logger.info("Exiting JsonUtil :: postRequest");
		} catch(Exception e) {
			logger.info("Error:: JsonUtil:: postRequest method "+e);
			throw new ChatakMerchantException("Unable to connect to API server,Please try again");
		}
		return resultantObject;
	}
	
	private static String getValidOAuthToken() {
		
		if(isValidIssuanceToken()) {
			logger.info("isValidIssuanceToken :: OAUTH_TOKEN_ISSUANCE : " + OAUTH_TOKEN_ISSUANCE);
			return OAUTH_TOKEN_ISSUANCE;
		} else {
			logger.info("REquesting new auth token :: from refreshIssuanceOAuthToken");
			return refreshIssuanceOAuthToken();
		}
	}
	
	private static String getBasicAuthTokenValue() {
		
		String basicAuth = Properties.getProperty("prepaid.admin.oauth.basic.auth.username") + ":"
				+ Properties.getProperty("prepaid.admin.oauth.basic.auth.password");
		basicAuth = TOKEN_TYPE_BASIC + new String(Base64.getEncoder().encode(basicAuth.getBytes()));
		return basicAuth;
	}
	
	private static boolean isValidIssuanceToken() {
		
		if(OAUTH_TOKEN_ISSUANCE == null || issuanceTokenValidity == null) {
			return false;
		} else if(System.currentTimeMillis() > issuanceTokenValidity) {
			OAUTH_TOKEN_ISSUANCE = null;
			return (null != refreshIssuanceOAuthToken());
		} else {
			return true;
		}
	}
	
	private static <T> String refreshIssuanceOAuthToken() {
		logger.info("Requesting for new auth token :: refreshIssuanceOAuthToken");
		T resultantObject = null;
		HttpClient httpClient = new HttpClient(BASE_PREPAID_SERVICE_URL, BASE_PREPAID_ADMIN_OAUTH_SERVICE_URL);
		try {
			Class<T> response = null;

			Header[] headers = new Header[] { new BasicHeader(AUTH_HEADER, getBasicAuthTokenValue()),
					new BasicHeader("content-type", ContentType.APPLICATION_JSON.getMimeType()) };
			resultantObject = httpClient.invokeGet(response, headers);
			logger.info("URL to generate token : " + (BASE_PREPAID_SERVICE_URL + BASE_PREPAID_ADMIN_OAUTH_SERVICE_URL));
			OAuthToken apiResponse = (OAuthToken) resultantObject;
			OAUTH_TOKEN_ISSUANCE = apiResponse.getAccess_token();
			issuanceTokenValidity = System.currentTimeMillis() + (apiResponse.getExpires_in() * 60);
		} catch (Exception e) {
			logger.info("Error:: JsonUtil:: refreshIssuanceOAuthToken method " + e);
		}
		logger.info("refreshIssuanceOAuthToken auth token :: OAUTH_TOKEN_ISSUANCE : " + OAUTH_TOKEN_ISSUANCE);
		return OAUTH_TOKEN_ISSUANCE;
	}
}
