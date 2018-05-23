package com.chatak.acquirer.admin.util;

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

import com.chatak.acquirer.admin.exception.ChatakAdminException;
import com.chatak.pg.model.OAuthToken;
import com.chatak.pg.util.Constants;
import com.chatak.pg.util.Properties;
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

	private static Calendar tokenValidity = null;

	private static String OAUTH_TOKEN_FEE = null;

	private static String OAUTH_REFRESH_TOKEN_FEE = null;

	private static Calendar tokenValidity_fee = null;
	
	private static int refershRequestCount = 0;
	
	private static int refershRequestCountPay = 0;
	
	private static final int MAX_RETRY_COUNT = 3;
	
	private static final String CHATAK_ISSUENCE_SERVICE_URL = BASE_PREPAID_SERVICE_URL + "/rest";
	
	private static String OAUTH_TOKEN_ISSUANCE = null;
	
	private static Calendar issuanceTokenValidity = null;

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
	 */
	public static ClientResponse postRequest(Object request, String serviceEndPoint) throws Exception {
		
		Client client = Client.create();
		client.setConnectTimeout(Constants.TIME_OUT);
		Builder webResource = client.resource(BASE_SERVICE_URL + serviceEndPoint).header("consumerClientId",
				Properties.getProperty("chatak-merchant.consumer.client.id")).header("consumerSecret",
						Properties.getProperty("chatak-merchant.consumer.client.secret"));
		webResource.header(AUTH_HEADER, TOKEN_TYPE_BEARER + getValidOAuth2Token());
		ObjectWriter objectPrettyWriter = objectWriter.withDefaultPrettyPrinter();
		String input = "";
		ClientResponse response = null;
		try {
		    logger.info("Connecting to Gate way URL ::"+BASE_SERVICE_URL);
			input = objectPrettyWriter.writeValueAsString(request);
			logger.info("Sent data to Gate way URL ::");
			response = webResource.type(MediaType.APPLICATION_JSON).post(ClientResponse.class, input);
			logger.info("Received response from Gate way URL :: response: " + response + ", status: " + response.getStatus() + ", refershRequestCountPay: " + refershRequestCountPay);
			
			if (response != null && refershRequestCountPay < MAX_RETRY_COUNT && response.getStatus() == HttpStatus.SC_UNAUTHORIZED) {
				logger.info("Requesting oauth ::");
		        refreshOAuth2Token();
		        refershRequestCountPay++;
		        return postRequest(request, serviceEndPoint);
		      }
			
			refershRequestCountPay = 0;
			logger.info("Exiting JsonUtil :: postRequest");
		} catch(Exception e) {
			logger.error("Error:: JsonUtil:: postRequest method ", e);
			throw new ChatakAdminException("Unable to connect to API server,Please try again");
		}
		return response;
	}
	
	/**
	 * Method to get Basic Auth value
	 * 
	 * @return
	 */
	private static String getBasicAuthValue() {

		String basicAuth = Properties.getProperty("chatak-merchant.oauth.basic.auth.username") + ":"
				+ Properties.getProperty("chatak-merchant.oauth.basic.auth.password");
		basicAuth = TOKEN_TYPE_BASIC + new String(Base64.encode(basicAuth));
		return basicAuth;
	}

	/**
	 * @param serviceEndPoint
	 * @return
	 */
	public static ClientResponse postRequest(String serviceEndPoint) {
		
		Client client = Client.create();
		Builder webResource = client.resource(BASE_SERVICE_URL + serviceEndPoint).header("consumerClientId",
				Properties.getProperty("chatak-merchant.consumer.client.id")).header("consumerSecret",
						Properties.getProperty("chatak-merchant.consumer.client.secret"));
		webResource.header(AUTH_HEADER, TOKEN_TYPE_BEARER + getValidOAuth2Token());
		return webResource.type(MediaType.APPLICATION_JSON).post(ClientResponse.class);

	}

	/**
	 * Method to get OAUTH token
	 * 
	 * @return
	 */
	private static String getValidOAuth2Token() {
		
		if(isValidToken()) {
			return OAUTH_TOKEN;
		} else {

			Client client = Client.create();
			if(BASE_SERVICE_URL.startsWith("https")) {
				TrustManager[] trustAllCerts = new TrustManager[]{new X509TrustManager() {
					
					@Override
					public void checkServerTrusted(java.security.cert.X509Certificate[] arg0, String arg1) throws CertificateException {
					//Need to implement based on requirement
					}
					
					@Override
					public void checkClientTrusted(java.security.cert.X509Certificate[] arg0, String arg1) throws CertificateException {
					//Need to implement based on requirement
					}

					@Override
					public java.security.cert.X509Certificate[] getAcceptedIssuers() {

						return null;
					}
					
				} };

				// Install the all-trusting trust manager
				try {
					SSLContext sc = SSLContext.getInstance("TLS");
					sc.init(null, trustAllCerts, new SecureRandom());
					HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
				} catch (Exception e) {
					logger.error("Error:: JsonUtil:: getValidOAuth2Token method ", e);
				}

			}
			Builder webResource = client.resource(BASE_SERVICE_URL
					+ BASE_ADMIN_OAUTH_SERVICE_URL).header(AUTH_HEADER, getBasicAuthValue());

			ClientResponse response = null;
			try {
				response = webResource.type(MediaType.APPLICATION_JSON).get(ClientResponse.class);
				String output = response.getEntity(String.class);
				OAuthToken apiResponse = new ObjectMapper().readValue(output, OAuthToken.class);
				OAUTH_REFRESH_TOKEN = apiResponse.getRefresh_token();
				OAUTH_TOKEN = apiResponse.getAccess_token();
				tokenValidity = Calendar.getInstance();
				tokenValidity.add(Calendar.SECOND, apiResponse.getExpires_in());
			} catch(Exception e) {
				logger.error("Error:: JsonUtil:: getValidOAuth2Token method ", e);
			}
		}
		return OAUTH_TOKEN;
	}

	/**
	 * Method to refresh the Oauth token when token is getting expired
	 * 
	 * @return
	 */
	private static String refreshOAuth2Token() {
		
		Client client = Client.create();
		Builder webResource = client.resource(BASE_SERVICE_URL
				+ BASE_OAUTH_REFRESH_SERVICE_URL + OAUTH_REFRESH_TOKEN).header(AUTH_HEADER,
						getBasicAuthValue());
		ClientResponse response = null;
		try {
			response = webResource.type(MediaType.APPLICATION_JSON).get(ClientResponse.class);
			String output = response.getEntity(String.class);
			OAuthToken apiResponse = new ObjectMapper().readValue(output, OAuthToken.class);
			OAUTH_REFRESH_TOKEN = apiResponse.getRefresh_token();
			OAUTH_TOKEN = apiResponse.getAccess_token();
			tokenValidity = Calendar.getInstance();
			tokenValidity.add(Calendar.SECOND, apiResponse.getExpires_in());
			logger.info("Received new auth token :: OAUTH_TOKEN: " + OAUTH_TOKEN);
			return OAUTH_TOKEN;
		}
		catch(Exception e) {
			logger.error("Error:: JsonUtil:: refreshOAuth2Token method ", e);
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
		} else if(Calendar.getInstance().after(tokenValidity)) {
			OAUTH_TOKEN = null;
			return (null != refreshOAuth2Token());
		} else {
			return true;
		}
	}
	
	public static ClientResponse sendToIssuance(Object request, String serviceEndPoint,String mode) throws ChatakAdminException {
		
		Client client = Client.create();
		client.setConnectTimeout(Constants.TIME_OUT);
		
		// For validating account no in fee program create and edit.
		if(null == mode) {
			mode = "LIVE";
		}
		
		issuanceBaseServiceUrl=ProcessorConfig.get(ProcessorConfig.FEE_SERVICE + mode);
		logger.info("Connecting to Issuance URL :: "+issuanceBaseServiceUrl);
		Builder webResource = client.resource(issuanceBaseServiceUrl + serviceEndPoint).header("consumerClientId",
				Properties.getProperty("chatak-issuance.consumer.client.id")).header("consumerSecret",
						Properties.getProperty("chatak-issuance.consumer.client.secret"));
		webResource.header(AUTH_HEADER, TOKEN_TYPE_BEARER + getValidOAuth2TokenForFee());
		ObjectWriter objectPrettyWriter = objectWriter.withDefaultPrettyPrinter();
		String input = "";
		ClientResponse response = null;
		try {
			input = objectPrettyWriter.writeValueAsString(request);
			response = webResource.type(MediaType.APPLICATION_JSON).post(ClientResponse.class, input);
			if (null != response && refershRequestCount == 0 && response.getStatus() == HttpStatus.SC_UNAUTHORIZED) {
			  refreshOAuth2TokenFee();
			  refershRequestCount++;
			  return sendToIssuance(request, serviceEndPoint, mode);
			}
		} catch(Exception e) {
			logger.error("Error:: JsonUtil:: postFee method ", e);
			throw new ChatakAdminException("Unable to connect to API server,Please try again");
		}
		return response;
	}
	
	private static String getValidOAuth2TokenForFee() {
		
		if(isValidTokenFee()) {
			return OAUTH_TOKEN_FEE;
		} else {

			Client client = Client.create();
			if(issuanceBaseServiceUrl.startsWith("https")) {
				TrustManager[] trustAllCerts = new TrustManager[]{new X509TrustManager() {
					
					@Override
					public void checkClientTrusted(java.security.cert.X509Certificate[] arg0, String arg1) throws CertificateException {
					  //Need to implement based on requirement
					}

					@Override
					public java.security.cert.X509Certificate[] getAcceptedIssuers() {

						return null;
					}

					@Override
					public void checkServerTrusted(java.security.cert.X509Certificate[] arg0, String arg1) throws CertificateException {
					//Need to implement based on requirement
					}

				} };


				// Install the all-trusting trust manager
				try {
					SSLContext sc = SSLContext.getInstance("TLS");
					sc.init(null, trustAllCerts, new SecureRandom());
					HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
				} catch (Exception e) {
					logger.error("Error:: JsonUtil:: getValidOAuth2Token method ", e);
				}

			}
			Builder webResource = client.resource(issuanceBaseServiceUrl
					+ ISSUANCE_BASE_ADMIN_OAUTH_SERVICE_URL).header(AUTH_HEADER, getBasicAuthValueForFee());

			ClientResponse response = null;
			try {
				response = webResource.type(MediaType.APPLICATION_JSON).get(ClientResponse.class);
				String output = response.getEntity(String.class);
				OAuthToken apiResponse = new ObjectMapper().readValue(output, OAuthToken.class);
				OAUTH_REFRESH_TOKEN_FEE = apiResponse.getRefresh_token();
				OAUTH_TOKEN_FEE = apiResponse.getAccess_token();
				tokenValidity_fee = Calendar.getInstance();
				tokenValidity_fee.add(Calendar.SECOND, apiResponse.getExpires_in());
			} catch(Exception e) {
				logger.error("Error:: JsonUtil:: getValidOAuth2Token method ", e);
			}
		}
		return OAUTH_TOKEN_FEE;
	}
	
	private static String getBasicAuthValueForFee() {
		
		String basicAuth = Properties.getProperty("chatak-issuance.oauth.basic.auth.username") + ":"
				+ Properties.getProperty("chatak-issuance.oauth.basic.auth.password");
		basicAuth = TOKEN_TYPE_BASIC + new String(Base64.encode(basicAuth));
		return basicAuth;
	}
	
	private static boolean isValidTokenFee() {
		
		if(OAUTH_TOKEN_FEE == null || tokenValidity_fee == null) {
			return false;
		} else if(Calendar.getInstance().after(tokenValidity_fee)) {
			OAUTH_TOKEN_FEE = null;
			return (null != refreshOAuth2TokenFee());
		} else {
			return true;
		}
	}
	
	private static String refreshOAuth2TokenFee() {
		
		Client client = Client.create();
		Builder webResource = client.resource(issuanceBaseServiceUrl
				+ ISSUANCE_BASE_OAUTH_REFRESH_SERVICE_URL + OAUTH_REFRESH_TOKEN_FEE).header(AUTH_HEADER,
						getBasicAuthValueForFee());
		ClientResponse response = null;
		try {
			response = webResource.type(MediaType.APPLICATION_JSON).get(ClientResponse.class);
			String output = response.getEntity(String.class);
			OAuthToken apiResponse = new ObjectMapper().readValue(output, OAuthToken.class);
			tokenValidity_fee = Calendar.getInstance();
			OAUTH_TOKEN_FEE = apiResponse.getAccess_token();
			OAUTH_REFRESH_TOKEN_FEE = apiResponse.getRefresh_token();
			tokenValidity_fee.add(Calendar.SECOND, apiResponse.getExpires_in());
			return OAUTH_TOKEN_FEE;
		} catch(Exception e) {
			logger.error("Error:: JsonUtil:: refreshOAuth2Token_fee method ", e);
		}
		return null;
	}
	
	public static ClientResponse postDCCRequest(Object request,
			String serviceEndPoint) throws Exception {

		Client client = Client.create();
		client.setConnectTimeout(Constants.TIME_OUT);
		Builder webResource = client.resource(DCC_BASE_SERVICE_URL + serviceEndPoint).header("consumerClientId",
						Properties.getProperty("chatak-merchant.consumer.client.id")).header("consumerSecret",
						Properties.getProperty("chatak-merchant.consumer.client.secret"));
		webResource.header(AUTH_HEADER, TOKEN_TYPE_BEARER + getValidOAuth2Token());
		ObjectWriter objectPrettyWriter = objectWriter.withDefaultPrettyPrinter();
		String input = "";
		ClientResponse response = null;
		try {
			logger.info("Connecting to Gate way URL ::" + DCC_BASE_SERVICE_URL);
			input = objectPrettyWriter.writeValueAsString(request);
			response = webResource.type(MediaType.APPLICATION_JSON).post(
					ClientResponse.class, input);
			if (null != response && refershRequestCountPay == 0 && response.getStatus() == HttpStatus.SC_UNAUTHORIZED) {
				refreshOAuth2Token();
				refershRequestCountPay++;
				return postDCCRequest(request, serviceEndPoint);
			}
		} catch (Exception e) {
			logger.error("Error:: JsonUtil:: postRequest method ", e);
			throw new ChatakAdminException("Unable to connect to API server,Please try again");
		}
		return response;
	}

	public static ClientResponse postDCCRequest(String serviceEndPoint) {
		Client client = Client.create();
		WebResource webResource = client.resource(DCC_BASE_SERVICE_URL + serviceEndPoint);

		return webResource.type(MediaType.APPLICATION_JSON).post(ClientResponse.class);
	}
	
	public static ClientResponse getRequest(String serviceEndPoint) {
		Client client = Client.create();
		WebResource webResource = client.resource(DCC_BASE_SERVICE_URL + serviceEndPoint);
		return webResource.type(MediaType.APPLICATION_JSON).get(ClientResponse.class);
	}
	
	public static ClientResponse postIssuanceRequest(Object request, String serviceEndPoint) throws Exception {
		
		Client client = Client.create();
		client.setConnectTimeout(Constants.TIME_OUT);
		
		Builder webResource = client.resource(CHATAK_ISSUENCE_SERVICE_URL + serviceEndPoint).header("consumerClientId",
				Properties.getProperty("prepaid-admin.consumer.client.id")).header("consumerSecret",
						Properties.getProperty("prepaid-admin.consumer.client.secret"));
		webResource.header(AUTH_HEADER, TOKEN_TYPE_BEARER + getValidOAuthToken());
		ClientResponse response = null;
		ObjectWriter objectPrettyWriter = objectWriter.withDefaultPrettyPrinter();
		String input = "";
		try {
		    logger.info("Connecting to Gate way URL ::"+ CHATAK_ISSUENCE_SERVICE_URL + serviceEndPoint);
		    if(null != request && !"".equals(request) && "{}".equals(request))
		    	input = "{}";
		    else
		    	input = objectPrettyWriter.writeValueAsString(request);

			  response = webResource.type(MediaType.APPLICATION_JSON).post(ClientResponse.class, input);
			logger.info("Received response from Gate way URL :: response: " + response + ", refershRequestCountPay: " + refershRequestCountPay);
			
			if (response != null && response.getStatus() == HttpStatus.SC_UNAUTHORIZED && refershRequestCountPay < MAX_RETRY_COUNT) {
				logger.info("Requesting oauth ::");
				
				OAUTH_TOKEN_ISSUANCE = null;
				issuanceTokenValidity = null;
				
				getValidOAuthToken();
		        refershRequestCountPay++;
		        return postIssuanceRequest(request, serviceEndPoint);
		      }
			
			refershRequestCountPay = 0;
			logger.info("Exiting JsonUtil :: postRequest");
		}
		catch(Exception e) {
			logger.error("Error:: JsonUtil:: postRequest method ", e);
			throw new ChatakAdminException("Unable to connect to API server,Please try again");
		}
		return response;
	}
	
	private static String getBasicAuthTokenValue() {

		String basicAuth = Properties.getProperty("prepaid.admin.oauth.basic.auth.username") + ":"
				+ Properties.getProperty("prepaid.admin.oauth.basic.auth.password");
		basicAuth = TOKEN_TYPE_BASIC + new String(Base64.encode(basicAuth));
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
		} else if (Calendar.getInstance().after(issuanceTokenValidity)) {
			OAUTH_TOKEN_ISSUANCE = null;
			return (null != refreshIssuanceOAuthToken());
		} else {
			return true;
		}
	}
	
	private static String refreshIssuanceOAuthToken() {
		logger.info("Requesting for new auth token :: refreshIssuanceOAuthToken");
		
		ClientResponse response = null;
		Client client = Client.create();
		Builder webResource = client.resource(BASE_PREPAID_SERVICE_URL
				+ BASE_PREPAID_ADMIN_OAUTH_SERVICE_URL).header(AUTH_HEADER, getBasicAuthTokenValue());
		
		logger.info("URL to generate token : " + (BASE_PREPAID_SERVICE_URL + BASE_PREPAID_ADMIN_OAUTH_SERVICE_URL));
		
		try {
			response = webResource.type(MediaType.APPLICATION_JSON).post(ClientResponse.class);
			String output = response.getEntity(String.class);
			OAuthToken apiResponse = new ObjectMapper().readValue(output, OAuthToken.class);
			OAUTH_TOKEN_ISSUANCE = apiResponse.getAccess_token();
			issuanceTokenValidity = Calendar.getInstance();
			issuanceTokenValidity.add(Calendar.SECOND, apiResponse.getExpires_in());
		} catch(Exception e) {
			logger.error("Error:: JsonUtil:: refreshIssuanceOAuthToken method ", e);
		}
		logger.info("refreshIssuanceOAuthToken auth token :: OAUTH_TOKEN_ISSUANCE : " + OAUTH_TOKEN_ISSUANCE);
		return OAUTH_TOKEN_ISSUANCE;
	}
}
