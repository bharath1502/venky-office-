package com.chatak.acquirer.admin.util;

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


import com.chatak.acquirer.admin.exception.ChatakAdminException;
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

	 JsonUtil() {
		super();
	}

	private static final Logger logger = Logger.getLogger(JsonUtil.class);

	public static final String BASE_SERVICE_URL = Properties.getProperty("chatak-merchant.service.url");

	public static final String BASE_ADMIN_OAUTH_SERVICE_URL = Properties.getProperty("chatak-merchant.oauth.service.url");

	public static final String BASE_PREPAID_SERVICE_URL = Properties.getProperty("prepaid.service.url");
	
	public static final String BASE_PREPAID_ADMIN_OAUTH_SERVICE_URL = Properties.getProperty("prepaid.admin.oauth.service.url");
	
	public static final String BASE_OAUTH_REFRESH_SERVICE_URL = Properties.getProperty("chatak-merchant.oauth.refresh.service.url");

	private static String issuanceBaseServiceUrl = "";
	
	public static final String DCC_BASE_SERVICE_URL = Properties.getProperty("chatak-dcc.service.url");

	public static final String ISSUANCE_BASE_ADMIN_OAUTH_SERVICE_URL = Properties.getProperty("chatak-issuance.oauth.service.url");

	public static final String ISSUANCE_BASE_OAUTH_REFRESH_SERVICE_URL = Properties.getProperty("chatak-issuance.oauth.refresh.service.url");

	public static final ObjectWriter objectWriter = new ObjectMapper().writer();

	private static final String TOKEN_TYPE_BEARER = "Bearer ";

	private static final String TOKEN_TYPE_BASIC = "Basic ";

	private static final String AUTH_HEADER = "Authorization";

	private static String OAUTH_TOKEN = null;

	private static String OAUTH_REFRESH_TOKEN = null;

	private static Long tokenValidity = null;

	private static String OAUTH_TOKEN_FEE = null;

	private static String OAUTH_REFRESH_TOKEN_FEE = null;

	private static Long tokenValidityFee = null;
	
	private static int refershRequestCount = 0;
	
	private static int refershRequestCountPay = 0;
	
	private static final int MAX_RETRY_COUNT = 3;
	
	private static final String CHATAK_ISSUENCE_SERVICE_URL = BASE_PREPAID_SERVICE_URL + "/rest";
	
	private static String OAUTH_TOKEN_ISSUANCE = null;
	
	private static Long issuanceTokenValidity = null;

	public static String convertObjectToJSON(Object object) throws ChatakAdminException {
		
		String input = "";
		ObjectWriter objectWriter = new ObjectMapper().writer()
				.withDefaultPrettyPrinter();
		try {
			input = objectWriter.writeValueAsString(object);
			return input;
		} catch (JsonGenerationException e) {
		  logger.error("ERROR :: JsonUtil ::convertObjectToJSON JsonGenerationException", e);
			throw new ChatakAdminException(e.getMessage());
		} catch (JsonMappingException e) {
		  logger.error("ERROR :: JsonUtil ::convertObjectToJSON JsonMappingException", e);
			throw new ChatakAdminException(e.getMessage());
		} catch (IOException e) {
		  logger.error("ERROR :: JsonUtil ::convertObjectToJSON IOException", e);
			throw new ChatakAdminException(e.getMessage());
		}
	}

	public static Object convertJSONToObject(String jsonData, Class<?> c) throws ChatakAdminException {
		
		ObjectMapper mapper = new ObjectMapper();
		try {
			return mapper.readValue(jsonData, c);
		} catch (JsonParseException e) {
		  logger.error("ERROR :: JsonUtil ::convertJSONToObject JsonParseException", e);
			throw new ChatakAdminException(e.getMessage());
		} catch (JsonMappingException e) {
		  logger.error("ERROR :: JsonUtil ::convertJSONToObject JsonMappingException", e);
			throw new ChatakAdminException(e.getMessage());
		} catch (IOException e) {
		  logger.error("ERROR :: JsonUtil ::convertJSONToObject IOException", e);
			throw new ChatakAdminException(e.getMessage());
		}
	}
	/**
	 * @param request
	 * @param serviceEndPoint
	 * @return
	 * @throws ChatakAdminException 
	 */

	public static <T extends Object> T  postRequest(Object request,Class<T> response, String serviceEndPoint) throws HttpClientException, ChatakAdminException {
	T resultantObject = null;
	HttpClient client=new HttpClient(BASE_SERVICE_URL, serviceEndPoint);

		try {
			
			Header[] headers = new Header[] {
					new BasicHeader("content-type", ContentType.APPLICATION_JSON.getMimeType()),	
					new BasicHeader("consumerClientId",Properties.getProperty("chatak-merchant.consumer.client.id")),
					new BasicHeader("consumerSecret",Properties.getProperty("chatak-merchant.consumer.client.secret")),
					new BasicHeader(AUTH_HEADER, TOKEN_TYPE_BEARER + getValidOAuth2Token()),
	                	
			};
			 resultantObject = client.invokePost(request, response, headers, true);
		} catch(PrepaidAdminException e) {
		  logger.error("ERROR: JsonUtil :: postRequest method" + e.getMessage(), e);
            logger.info("Requesting oauth ::");
            if (refershRequestCountPay <=MAX_RETRY_COUNT) {
				logger.info("Requesting oauth ::");
		        refreshOAuth2Token();
		        refershRequestCountPay++;
		        return postRequest(request,response,serviceEndPoint);
		      }
		}
		catch (Exception e) {
			logger.error("ERROR: JsonUtil :: postRequest method", e);
			throw new ChatakAdminException("Unable to connect to API server,Please try again");
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
	 * @param serviceEndPoint
	 * @return
	 */
	
	public static <T extends Object> T postRequest(String serviceEndPoint, Class<T> response)
			throws HttpClientException, ChatakAdminException {
		T resultantObject = null;
		HttpClient client = new HttpClient(BASE_SERVICE_URL, serviceEndPoint);
		try {
			Header[] headers = new Header[] {
					new BasicHeader("consumerClientId", Properties.getProperty("chatak-merchant.consumer.client.id")),
					new BasicHeader("consumerSecret", Properties.getProperty("chatak-merchant.consumer.client.secret")),
					new BasicHeader(AUTH_HEADER, TOKEN_TYPE_BEARER + getValidOAuth2Token()),
					new BasicHeader("content-type", ContentType.APPLICATION_JSON.getMimeType()) };
			resultantObject = client.invokePost(response, headers);
		} catch (Exception e) {
			logger.error("ERROR: JsonUtil :: postRequest method", e);
			throw new ChatakAdminException("Unable to connect to API server,Please try again");
		}
		return resultantObject;
	}

	/**
	 * Method to get OAUTH token
	 * 
	 * @return
	 */

	private static String getValidOAuth2Token() {
		String output = null;
		if (isValidToken()) {
			return OAUTH_TOKEN;
		} else {
			if (BASE_SERVICE_URL.startsWith("https")) {
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
			HttpClient httpClient = new HttpClient(BASE_SERVICE_URL, BASE_ADMIN_OAUTH_SERVICE_URL);
			try {
				Header[] headers = new Header[] { new BasicHeader(AUTH_HEADER, getBasicAuthValue()),
						new BasicHeader("content-type", ContentType.APPLICATION_JSON.getMimeType()) };
				output = httpClient.invokePost(String.class, headers);
				OAuthToken apiResponse = new ObjectMapper().readValue(output, OAuthToken.class);
				OAUTH_TOKEN = apiResponse.getAccess_token();
				OAUTH_REFRESH_TOKEN = apiResponse.getRefresh_token();
				tokenValidity = System.currentTimeMillis() + (apiResponse.getExpires_in() * 60);
			} catch (Exception e) {
				logger.info("Error:: JsonUtil:: getValidOAuth2Token method " + e);
			}
		}
		return OAUTH_TOKEN;
	}

	/**
	 * Method to refresh the Oauth token when token is getting expired
	 * 
	 * @return
	 */
	
	 private static  String refreshOAuth2Token() {
		  String output = null;
		    HttpClient httpClient=new HttpClient(BASE_SERVICE_URL
	                + BASE_OAUTH_REFRESH_SERVICE_URL, OAUTH_REFRESH_TOKEN);
		    try {
				Header[] headers = new Header[] {
						new BasicHeader(AUTH_HEADER,
	                            getBasicAuthValue()),
						new BasicHeader("content-type", ContentType.APPLICATION_JSON.getMimeType())};
				output = httpClient.invokePost(String.class, headers);
			  OAuthToken apiResponse = new ObjectMapper().readValue(output, OAuthToken.class);
		      OAUTH_TOKEN = apiResponse.getAccess_token();
		      OAUTH_REFRESH_TOKEN = apiResponse.getRefresh_token();
		      tokenValidity = System.currentTimeMillis() + (apiResponse.getExpires_in() * 60);
		    }
		    catch(Exception e) {
		      logger.info("Error:: JsonUtil:: refreshOAuth2Token method "+e);
		    }
		    return OAUTH_TOKEN;
		  }
	
	/**
	 * Method to check valid token
	 * 
	 * @return
	 */
	private static boolean isValidToken() {
		
		if(OAUTH_TOKEN == null || tokenValidity == null) {
			return false;
		} else if(System.currentTimeMillis() > tokenValidity) {
			OAUTH_TOKEN = null;
			return (null != refreshOAuth2Token());
		} else {
			return true;
		}
	}
	
	public static <T extends Object> T sendToIssuance(Object request, String serviceEndPoint,String mode,Class<T> response) throws ChatakAdminException,HttpClientException {
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
		  logger.error("ERROR: JsonUtil :: postRequest method" + e.getMessage(), e);
			logger.info("Requesting oauth ::");
			if (refershRequestCount == 0) {
				refreshOAuth2TokenFee();
				refershRequestCount++;
				return sendToIssuance(request, serviceEndPoint, mode, response);
			}
		} catch (Exception e) {
			logger.info("Error:: JsonUtil:: postFee method " + e);
			throw new ChatakAdminException(ActionErrorCode.ERROR_CODE_API_CONNECT);
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
					;
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
			return (null != refreshOAuth2TokenFee());
		} else {
			return true;
		}
	}
	
	 private static <T> String refreshOAuth2TokenFee() {
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
	
	public static <T extends Object> T postDCCRequest(Object request,
			String serviceEndPoint,Class<T> response) throws  ChatakAdminException {
		 T resultantObject = null;
		  HttpClient paymentHttpClient = new HttpClient(DCC_BASE_SERVICE_URL, serviceEndPoint);
		  try {
				Header[] headers = new Header[] {
						new BasicHeader("consumerClientId",
								Properties.getProperty("chatak-merchant.consumer.client.id")),
						new BasicHeader("consumerSecret",
								Properties.getProperty("chatak-merchant.consumer.client.secret")),
						new BasicHeader(AUTH_HEADER, TOKEN_TYPE_BEARER + getValidOAuth2Token()),
						new BasicHeader("content-type", ContentType.APPLICATION_JSON.getMimeType())};
				 resultantObject = paymentHttpClient.invokePost(request, response, headers, false);
				 if (null != response && refershRequestCountPay == 0 ) {
						refreshOAuth2Token();
						refershRequestCountPay++;
						return postDCCRequest(request,serviceEndPoint, response);
					}
		  } catch (Exception e) {
				logger.error("Error:: JsonUtil:: postRequest method ", e);
				throw new ChatakAdminException("Unable to connect to API server,Please try again");
			}
		    return resultantObject;
	  }
	
	public static <T extends Object> T postDCCRequest(String serviceEndPoint,Class<T> response) throws IOException	{
		T resultantObject = null;
		HttpClient httpClient = new HttpClient(DCC_BASE_SERVICE_URL, serviceEndPoint);
		
		Header[] headers = new Header[] { new BasicHeader("content-type", ContentType.APPLICATION_JSON.getMimeType()) };
		
			resultantObject = httpClient.invokeGetCommon(response, headers);
		return resultantObject;
	}
	
	public static <T extends Object> T getRequest(String serviceEndPoint, Class<T> response) throws HttpClientException {
		T resultantObject = null;
		HttpClient httpClient = new HttpClient(DCC_BASE_SERVICE_URL, serviceEndPoint);
		
		Header[] headers = new Header[] { new BasicHeader("content-type", ContentType.APPLICATION_JSON.getMimeType()) };
		try {
			resultantObject = httpClient.invokeGetCommon(response, headers);
		} catch (Exception e) {
			logger.error("ERROR:: JsonUtil::postRequest ", e);
		}
		return resultantObject;
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
		    logger.error("ERROR: JsonUtil :: postRequest method" + e.getMessage(), e);
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
		} catch (Exception e) {
			logger.info("Error:: JsonUtil:: postRequest method " + e);
			throw new ChatakAdminException("Unable to connect to API server,Please try again");
		}
		return resultantObject;
	}
	
	private static String getBasicAuthTokenValue() {

		String basicAuth = Properties.getProperty("prepaid.admin.oauth.basic.auth.username") + ":"
				+ Properties.getProperty("prepaid.admin.oauth.basic.auth.password");
		basicAuth = TOKEN_TYPE_BASIC + new String(Base64.getEncoder().encode(basicAuth.getBytes()));
		return basicAuth;
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
	
	private static boolean isValidIssuanceToken() {

		if (issuanceTokenValidity == null || OAUTH_TOKEN_ISSUANCE == null) {
			return false;
		} else if (System.currentTimeMillis() > issuanceTokenValidity) {
			OAUTH_TOKEN_ISSUANCE = null;
			return (null != refreshIssuanceOAuthToken());
		} else {
			return true;
		}
	}
	
	private static String refreshIssuanceOAuthToken() {
		logger.info("Requesting for new auth token :: refreshIssuanceOAuthToken");
		HttpClient httpClient = new HttpClient(BASE_PREPAID_SERVICE_URL, BASE_PREPAID_ADMIN_OAUTH_SERVICE_URL);
		try {
			Header[] headers = new Header[] { new BasicHeader(AUTH_HEADER, getBasicAuthTokenValue()),
					new BasicHeader("content-type", ContentType.APPLICATION_JSON.getMimeType()) };
			OAuthToken apiResponse = httpClient.invokeGet(OAuthToken.class, headers);
			logger.info("URL to generate token : " + (BASE_PREPAID_SERVICE_URL + BASE_PREPAID_ADMIN_OAUTH_SERVICE_URL));
			OAUTH_TOKEN_ISSUANCE = apiResponse.getAccess_token();
			issuanceTokenValidity = System.currentTimeMillis() + (apiResponse.getExpires_in() * 60);
		} catch (Exception e) {
			logger.info("Error:: JsonUtil:: refreshIssuanceOAuthToken method " + e);
		}
		logger.info("refreshIssuanceOAuthToken auth token :: OAUTH_TOKEN_ISSUANCE : " + OAUTH_TOKEN_ISSUANCE);
		return OAUTH_TOKEN_ISSUANCE;
	}
}
