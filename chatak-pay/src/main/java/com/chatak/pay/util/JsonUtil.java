package com.chatak.pay.util;

import java.io.IOException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.util.Base64;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.core.Response;

import org.apache.http.Header;
import org.apache.http.entity.ContentType;
import org.apache.http.message.BasicHeader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.ObjectWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.chatak.pay.controller.model.LoyaltyProgramRequest;
import com.chatak.pay.exception.ChatakPayException;
import com.chatak.pg.constants.ActionErrorCode;
import com.chatak.pg.constants.PGConstants;
import com.chatak.pg.exception.HttpClientException;
import com.chatak.pg.exception.PrepaidAdminException;
import com.chatak.pg.model.ApplicationClientDTO;
import com.chatak.pg.model.OAuthToken;
import com.chatak.pg.util.HttpClient;
import com.chatak.pg.util.Properties;


public class JsonUtil {

  private JsonUtil() {
    super();
  }

  @Autowired
  private static MessageSource messageSource;

  private static final Logger logger =  LogManager.getLogger(JsonUtil.class);

  public static final String BASE_SERVICE_URL = Properties.getProperty("chatak-pay.service.url");
  
  public static final String HSM_SERVICE_URL = Properties.getProperty("hsm.service.url");
  
  public static final String TMS_SERVICE_URL = Properties.getProperty("tms.service.url");

  public static final String BASE_PREPAID_SERVICE_URL = Properties.getProperty("prepaid.service.url");

  public static final ObjectWriter objectWriter = new ObjectMapper().writer();

  private static String issuanceBaseServiceUrl = "";

  public static final String ISSUANCE_BASE_ADMIN_OAUTH_SERVICE_URL =
      Properties.getProperty("chatak-issuance.oauth.service.url");

  public static final String ISSUANCE_BASE_OAUTH_REFRESH_SERVICE_URL =
      Properties.getProperty("chatak-issuance.oauth.refresh.service.url");

  public static String TOKEN_TYPE_BEARER = "Bearer ";

  public static String TOKEN_TYPE_BASIC = "Basic ";

  private static String AUTH_HEADER = "Authorization";

  private static String OAUTH_TOKEN_FEE = null;

  private static String OAUTH_REFRESH_TOKEN_FEE = null;

  private static Long tokenValidityFee = null;

  private static final ObjectMapper mapper = new ObjectMapper();

  public static final String PAYGATE_SERVICE_URL = Properties.getProperty("chatak-merchant.service.url");
  
  private static String tmsTokenServiceUrl = Properties.getProperty("chatak-tms.oauth.service.url");
  
  private static String tmsRestServiceUrl = Properties.getProperty("chatak-tms.rest.service.url");
  
  private static String tmsBaseServiceUrl = Properties.getProperty("chatak-tms.base.service.url");
  
  private static String baseOauthRefreshServiceUrl = Properties.getProperty("chatak-tms.oauth.refresh.service.url");
  
  protected static Long tmsTokenValidity = null;
  
  protected static String tmsOauthToken = null;
  
  protected static String tmsOauthRefreshToken = null;

  private static final int MAX_RETRY_COUNT = 3;

  private static int refershRequestCount = 0;
  
  private static final String CHATAK_LOYALTY_SERVICE_URL = Properties.getProperty("loyalty.service.url");
  
  private static String OAUTH_TOKEN_LOYALTY = null;
	
  private static Long loyaltyTokenValidity = null;
	
  public static final String BASE_LOYALTY_OAUTH_SERVICE_URL = Properties.getProperty("oauth.token.generation.url");

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
	      resultantObject = httpClient.invokePost(request,className, headers, false);
	    }catch (HttpClientException hce) {
	        logger.error("ERROR: JsonUtil :: postRequest method "+ hce.getHttpErrorCode() + hce.getMessage(), hce);
	    	throw hce;
	    }catch (Exception e) {
	        logger.error("Error::PostReqeust::Method", e);
	        throw new ChatakPayException(e.getMessage());
	      }
	    logger.info("Exiting::PostReqeust::Method");
	    return resultantObject;
	}
  
  public static <T extends Object> T postRequestForRKI(Class<T> className,Object request,String serviceEndPoint,String tokenType) throws ChatakPayException, HttpClientException {
		T resultantObject = null;
		HttpClient httpClient = new HttpClient("", serviceEndPoint);
	    try {
	    	Header[] headers = new Header[] { new BasicHeader("content-type", ContentType.APPLICATION_JSON.getMimeType()),
					new BasicHeader(AUTH_HEADER, tokenType ) };
	      resultantObject = httpClient.invokePost(request,className, headers, false);
	    }catch (HttpClientException hce) {
	        logger.error("ERROR: JsonUtil :: postRequest method "+ hce.getHttpErrorCode() + hce.getMessage(), hce);
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
	      resultantObject = httpClient.invokePost(request,className, headers, false);
	    } catch (HttpClientException hce) {
	        logger.error("ERROR: JsonUtil :: sendToIssuance method "+ hce.getHttpErrorCode() + hce.getMessage(), hce);
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
		if (isValidTokenFee()) {
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
			        	  try {
								arg0[0].checkValidity();
							} catch (CertificateException e) {
								logger.info("Error:: JsonUtil:: checkClientTrusted method ");
								throw new CertificateException("Certificate not valid or trusted.");
							}
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
			        }};


			        // Install the all-trusting trust manager
			        try {
			          SSLContext sc = SSLContext.getInstance("TLSv1.2");
			          sc.init(null, trustAllCerts, new SecureRandom());
			          HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());

			          SSLContext ctx = SSLContext.getInstance("TLSv1.2");
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
		        tokenValidityFee = System.currentTimeMillis() + (oAuthToken.getExpires_in() * 60);
		        
		        logger.info("getValidIssuanceOAuth2Token :: retrieved token: " + OAUTH_TOKEN_FEE);
		        
		}catch (Exception e) {
	        logger.info("Error:: JsonUtil:: getValidOAuth2Token method " + e);;
	      }
		}
		return OAUTH_TOKEN_FEE;
	}

  private static boolean isValidTokenFee() {
    if (OAUTH_TOKEN_FEE == null || tokenValidityFee == null) {
      return false;
    } else if (System.currentTimeMillis() > tokenValidityFee) {
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
	          tokenValidityFee = System.currentTimeMillis() + (apiResponse.getExpires_in() * 60);
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
		HttpClient httpClient = new HttpClient(tmsRestServiceUrl , serviceEndPoint);
        logger.info("TMS URL -->> " + tmsRestServiceUrl + serviceEndPoint);
	    try {
	    	Header[] headers = new Header[] { new BasicHeader("content-type", ContentType.APPLICATION_JSON.getMimeType())
	    	                  ,new BasicHeader(AUTH_HEADER, TOKEN_TYPE_BEARER + getTmsOauthToken())};
	      resultantObject = httpClient.invokePost(request,className, headers, false);
	     logger.info("Reponse : Resultant Object "+resultantObject);
	    } catch(PrepaidAdminException pae) {
        	logger.info("Requesting oauth ::");
        	if (refershRequestCount < MAX_RETRY_COUNT) {
        		logger.info("Retrying.. : " + (refershRequestCount + 1));
        		tmsOauthToken = null;
        		tmsTokenValidity = null;
        		tmsOauthRefreshToken = null;
        		refershRequestCount++;
        		return sendToTSM(className, request, serviceEndPoint);
        	}
	    } catch (HttpClientException hce) {
	        logger.error("ERROR: JsonUtil :: sendToTSM method" + hce.getHttpErrorCode() + hce.getMessage(), hce);
	    	throw hce;
	    }catch (Exception e) {
	    	 logger.error("Error:: sendToTSM:: postFee method " + e);
	         throw new NoSuchMessageException(messageSource.getMessage(
	         ActionErrorCode.ERROR_CODE_API_CONNECT, null, LocaleContextHolder.getLocale()));
	    }
	    return resultantObject;
	}

    private static <T> String getTmsOauthToken() {
      logger.info("Entering:: JsonUtil:: getTmsOauthToken method ");
      if (isValidToken()) {
        return tmsOauthToken;
      } else {
        HttpClient paymentHttpClient = new HttpClient(tmsBaseServiceUrl, tmsTokenServiceUrl);
        try {
          Header[] headers = new Header[] {new BasicHeader(AUTH_HEADER, getBasicAuthValueForTms())};

          String response = paymentHttpClient.invokeTmsOauthGet(String.class, headers);
          OAuthToken apiResponse = mapper.readValue(response, OAuthToken.class);
          tmsTokenValidity = System.currentTimeMillis() + (apiResponse.getExpires_in() * 60);
          tmsOauthRefreshToken = apiResponse.getRefreshToken().getAccess_token();
          tmsOauthToken = apiResponse.getAccess_token();
          refershRequestCount = 0;
          return tmsOauthToken;
        } catch(PrepaidAdminException pae) {
        	logger.info("Requesting oauth ::");
        	if (refershRequestCount < MAX_RETRY_COUNT) {
        		logger.info("Retrying.. : " + (refershRequestCount + 1));
        		tmsOauthToken = null;
        		tmsTokenValidity = null;
        		tmsOauthRefreshToken = null;
        		refershRequestCount++;
        		return getTmsOauthToken();
        	}
        } catch (Exception e) {
          logger.error("Error:: JsonUtil:: getTmsOauthToken method " + e);
        }
        return null;
      }
    }
    
    protected static boolean isValidToken() {
      if (tmsOauthToken == null || tmsTokenValidity == null) {
        return false;
      } else if (System.currentTimeMillis() > tmsTokenValidity) {
        tmsOauthToken = null;
        return (null != refreshOAuth2Token(tmsBaseServiceUrl, baseOauthRefreshServiceUrl));
      } else {
        return true;
      }
    }
    
    private static String refreshOAuth2Token(String baseServiceUrl, String tokenServiceUrl){
      logger.info("Entering:: JsonUtil:: refreshOAuth2Token method ");
      HttpClient httpClient = new HttpClient(baseServiceUrl, tokenServiceUrl + tmsOauthRefreshToken);
      try {
        Header[] headers = new Header[] {new BasicHeader(AUTH_HEADER, getBasicAuthValueForTms())};
        String response = httpClient.invokeTmsOauthGet(String.class, headers);
        OAuthToken apiResponse = mapper.readValue(response, OAuthToken.class);
        tmsTokenValidity = System.currentTimeMillis() + (apiResponse.getExpires_in() * 60);
        tmsOauthRefreshToken = apiResponse.getRefreshToken().getAccess_token();
        tmsOauthToken = apiResponse.getAccess_token();
        return tmsOauthToken;
      } catch (Exception e) {
        logger.error("Error:: JsonUtil:: refreshOAuth2Token method " + e);
      }
      return null;
    }

  private static String getBasicAuthValueForTms() {
    String basicAuth = Properties.getProperty("chatak-tms.oauth.basic.auth.username") + ":"
                      + Properties.getProperty("chatak-tms.oauth.basic.auth.password");
           basicAuth = TOKEN_TYPE_BASIC + new String(Base64.getEncoder().encode(basicAuth.getBytes()));
           return basicAuth;
  }

    /**
     * Method to get OAUTH token for given credentials
     * 
     * @return 
     */
    public static OAuthToken getValidOAuth2TokenLoginRefresh(ApplicationClientDTO applicationClient) {
      logger.info("Entering :: JsonUtil :: getValidOAuth2TokenLoginRefresh");
      OAuthToken apiResponse= null;    
      try {
        String serviceEndPoint = Properties.getProperty("chatak-merchant.oauth.refresh.service.url").trim().concat(applicationClient.getRefreshToken().trim());
        logger.info("URL :: " + PAYGATE_SERVICE_URL + serviceEndPoint);
        Header[] headers =
            new Header[] {new BasicHeader("content-type", ContentType.APPLICATION_JSON.getMimeType()),
                new BasicHeader(AUTH_HEADER, getBasicAuthValueOnApplicationClientDTO(applicationClient))};
        HttpClient paymentHttpClient =
            new HttpClient(PAYGATE_SERVICE_URL, serviceEndPoint);
        String output = paymentHttpClient.invokePost(String.class, headers);
        logger.info(output);
        apiResponse = mapper.readValue(output, OAuthToken.class);
      } catch (Exception e) {
        logger.error("ERROR: JsonUtil :: getValidOAuth2Token method", e);
      }
      logger.info("Exiting :: JsonUtil :: getValidOAuth2TokenLoginRefresh");
      return apiResponse;
    }

      private static String getBasicAuthValueOnApplicationClientDTO(ApplicationClientDTO applicationClient) {
        String basicAuth = applicationClient.getAppAuthUser().trim() + ":" + applicationClient.getAppAuthPass().trim();
        logger.info("AppAuthUserAndPass : " + basicAuth);
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
          Properties.getProperty("chatak.pay.token.url").trim().replace("$", applicationClient.getAppClientId().trim()).
          replace("#", applicationClient.getAppAuthPass().trim());
      logger.info("URL :: " + PAYGATE_SERVICE_URL + tokenEndpointUrl);
      OAuthToken apiResponse= null;
      try {

        Header[] headers =
            new Header[] {new BasicHeader("content-type", ContentType.APPLICATION_JSON.getMimeType()),
                new BasicHeader(AUTH_HEADER,
                    TOKEN_TYPE_BASIC + new String(Base64.getEncoder().encode(
                        (applicationClient.getAppAuthUser().trim() + ":" + applicationClient.getAppAuthPass().trim())
                                .getBytes())))};
       HttpClient paymentHttpClient =
            new HttpClient(PAYGATE_SERVICE_URL, tokenEndpointUrl);

       String output = paymentHttpClient.invokePost(String.class, headers);
       logger.info(output);
       apiResponse = mapper.readValue(output, OAuthToken.class);
      } catch (Exception e) {
        logger.error("ERROR: JsonUtil :: getValidOAuth2Token method", e);
      }
      return apiResponse;
    }
    
    public static <T extends Object> T postLoyaltyRequest(Object request, String serviceEndPoint, Class<T> response)
			throws Exception {
		T resultantObject = null;
		HttpClient httpClient = new HttpClient(CHATAK_LOYALTY_SERVICE_URL, serviceEndPoint);
		try {
			Header[] headers = new Header[] {
					new BasicHeader("content-type", ContentType.APPLICATION_JSON.getMimeType()) };
			resultantObject = httpClient.invokePost(request, response, headers, false);
			logger.info("Connecting to Gate way URL ::" + CHATAK_LOYALTY_SERVICE_URL + serviceEndPoint);
		} catch (PrepaidAdminException e) {
		    logger.error("ERROR: JsonUtil :: postRequest method" + e.getMessage(), e);
			logger.info("Requesting oauth ::");
			logger.info("Exiting JsonUtil :: postRequest");
		} catch (Exception e) {
			logger.info("Error:: JsonUtil:: postRequest method " + e);
			throw new ChatakPayException("Unable to connect to API server,Please try again");
		}
		return resultantObject;
	}
    

	
	public static Response awardLoyatyTransaction(LoyaltyProgramRequest loyaltyProgramAwardRequest, String loyaltyUrl,
			String accessToken) throws ChatakPayException{
		try {
			Response response = null;
			String output = JsonUtil.postRequestToLoyaltiyPlatform(loyaltyProgramAwardRequest, loyaltyUrl, "/loyaltyService/loyalty/awardLoyatyTransaction", accessToken, String.class);
			response = mapper.readValue(output, Response.class);
			return response;
		} catch(HttpClientException hce) {
			logger.error("Error :: JsonUtil :: awardLoyatyTransaction :: HttpClientException" + hce.getMessage(), hce);
			throw new ChatakPayException(Properties.getProperty("prepaid.agentadmin.general.error.message"));
		}  catch (Exception e) {
			logger.error("Error :: JsonUtil :: awardLoyatyTransaction : " + e.getMessage(), e);
			try {
				throw new ChatakPayException(Properties.getProperty("prepaid.agentadmin.general.error.message"));
			} catch (Exception e1) {
				logger.error("Error :: JsonUtil :: awardLoyatyTransaction : " + e1.getMessage(), e1);
		        throw new ChatakPayException(e.getMessage());
			}
		}
		
	}
	
	public static <T> T postRequestToLoyaltiyPlatform(Object request, String loyaltyURL, String serviceEndPoint,
			String accessToken, Class<T> response) throws HttpClientException {

		T resultantObject = null;
		HttpClient httpClient = new HttpClient(loyaltyURL, serviceEndPoint);
		Header[] headers = new Header[] { new BasicHeader("content-type", ContentType.APPLICATION_JSON.getMimeType()),
				new BasicHeader(AUTH_HEADER, TOKEN_TYPE_BEARER + accessToken) };
		try {
			resultantObject = httpClient.invokePost(request, response, headers, false);
		} catch (HttpClientException hce) {
			logger.error(
					"Error :: JsonUtil :: postRequestToLoyaltiyPlatform" + hce.getHttpErrorCode() + hce.getMessage(),
					hce);
			throw hce;
		} catch (Exception e) {
			logger.error("Error :: JsonUtil :: postRequestToLoyaltiyPlatform", e);
		}
		return resultantObject;
	}
}
