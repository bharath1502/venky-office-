/**
 * 
 */
package com.chatak.pg.util;

import java.io.IOException;
import java.util.Calendar;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import org.apache.log4j.Logger;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.ObjectWriter;

import com.chatak.pg.model.OAuthToken;
import com.google.gson.Gson;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource.Builder;
import com.sun.jersey.core.util.Base64;

/**
 * @author Raj
 * 
 */
public final class RestUtil {
  
  private static Logger logger = Logger.getLogger(RestUtil.class);

	org.springframework.beans.factory.config.PropertyPlaceholderConfigurer propertyConfigurer;
	
	private static Gson gSon = new Gson();

	public static final String BASE_SERVICE_URL = Properties
			.getProperty("prepaid.service.url") + "/rest";

	public static final String BASE_ADMIN_OAUTH_SERVICE_URL = Properties
			.getProperty("prepaid.admin.oauth.service.url");

	public static final String BASE_OAUTH_REFRESH_SERVICE_URL = Properties
			.getProperty("prepaid.oauth.refresh.service.url");

	public static final ObjectWriter objectWriter = new ObjectMapper().writer();

	private static String TOKEN_TYPE_BEARER = "Bearer ";

	private static String TOKEN_TYPE_BASIC = "Basic ";

	private static String AUTH_HEADER = "Authorization";

	private static String OAUTH_TOKEN = null;

	private static String OAUTH_REFRESH_TOKEN = null;

	private static Calendar tokenValidity = null;

	/**
		 * 
		 */
	private RestUtil() {
		
	}

	/**
	 * @param request
	 * @param serviceEndPoint
	 * @return
	 */
	public static ClientResponse postRequest(Object request,
			String serviceEndPoint) {
		Client client = Client.create();
		Builder webResource = client
				.resource(BASE_SERVICE_URL + serviceEndPoint)
				.header("consumerClientId",
						Properties
								.getProperty("prepaid-admin.consumer.client.id"))
				.header("consumerSecret",
						Properties
								.getProperty("prepaid-admin.consumer.client.secret"))
				.header(Properties
						.getProperty("prepaid-admin.header.param.user.type"),
						Properties
								.getProperty("prepaid-admin.header.user.type"));
		webResource.header(AUTH_HEADER, TOKEN_TYPE_BEARER
				+ getValidOAuth2Token());
		ObjectWriter objectPrettyWriter = objectWriter
				.withDefaultPrettyPrinter();
		String input = "";
		ClientResponse response = null;
		try {
			input = objectPrettyWriter.writeValueAsString(request);
			response = webResource.type(MediaType.APPLICATION_JSON).post(
					ClientResponse.class, input);
		} catch (Exception e) {
		  logger.error("ERROR:: RestUtil::postRequest ", e);
		}
		return response;

	}

	/**
	 * @param serviceEndPoint
	 * @return
	 */
	public static ClientResponse postRequest(String serviceEndPoint) {
		Client client = Client.create();
		Builder webResource = client
				.resource(BASE_SERVICE_URL + serviceEndPoint)
				.header("consumerClientId",
						Properties
								.getProperty("prepaid-admin.consumer.client.id"))
				.header("consumerSecret",
						Properties
								.getProperty("prepaid-admin.consumer.client.secret"))
				.header(Properties
						.getProperty("prepaid-admin.header.param.user.type"),
						Properties
								.getProperty("prepaid-admin.header.user.type"));
		webResource.header(AUTH_HEADER, TOKEN_TYPE_BEARER
				+ getValidOAuth2Token());
		ClientResponse response = webResource.type(MediaType.APPLICATION_JSON)
				.post(ClientResponse.class);
		return response;

	}

	/**
	 * Method to get OAUTH token
	 * 
	 * @return
	 */
	private static String getValidOAuth2Token() {
		if (isValidToken()) {
			return OAUTH_TOKEN;
		} else {

			Client client = Client.create();
			Builder webResource = client.resource(
					Properties.getProperty("prepaid.service.url")
							+ BASE_ADMIN_OAUTH_SERVICE_URL).header(AUTH_HEADER,
					getBasicAuthValue());
			try {
				validateResponse(webResource);
			} catch (Exception e) {
			  logger.error("ERROR:: RestUtil::getValidOAuth2Token ", e);
			}
		}
		return OAUTH_TOKEN;
	}

  private static void validateResponse(Builder webResource) throws IOException,
                                                            JsonParseException,
                                                            JsonMappingException {
    ClientResponse response;
    response = webResource.type(MediaType.APPLICATION_JSON).get(
    		ClientResponse.class);
    String output = response.getEntity(String.class);
    OAuthToken apiResponse = new ObjectMapper().readValue(output,
    		OAuthToken.class);
    OAUTH_TOKEN = apiResponse.getAccess_token();
    OAUTH_REFRESH_TOKEN = apiResponse.getRefresh_token();
    tokenValidity = Calendar.getInstance();
    tokenValidity.add(Calendar.SECOND, apiResponse.getExpires_in());
  }
	
	/**
   * Method to get Basic Auth value
   * 
   * @return
   */
  private static String getBasicAuthValue() {
    String basicAuth = Properties
        .getProperty("prepaid.admin.oauth.basic.auth.username")
        + ":"
        + Properties
            .getProperty("prepaid.admin.oauth.basic.auth.password");
    basicAuth = TOKEN_TYPE_BASIC + new String(Base64.encode(basicAuth));
    return basicAuth;
  }

	/**
	 * Method to refresh the Oauth token when token is getting expired
	 * 
	 * @return
	 */
	private static String refreshOAuth2Token() {
		Client client = Client.create();
		Builder webResource = client.resource(
				Properties.getProperty("prepaid.service.url")
						+ BASE_OAUTH_REFRESH_SERVICE_URL + OAUTH_REFRESH_TOKEN)
				.header(AUTH_HEADER, getBasicAuthValue());
		try {
			validateResponse(webResource);
			return OAUTH_TOKEN;
		} catch (Exception e) {
		  logger.error("ERROR:: RestUtil::refreshOAuth2Token ", e);
		}
		return null;
	}

	/**
	 * Method to convert object to Response
	 * 
	 * @param object
	 * @return
	 */
	public static Response toResponse(Object object) {
		ResponseBuilder response = Response.ok();
		response.entity(object);
		return response.build();
	}

	/**
	 * Construct JSON from Object
	 * 
	 * @param object
	 * @return
	 */
	public static String toJSON(Object object) {
		if (object == null) {
			return "{}";
		} else {
			return gSon.toJson(object);
		}
	}
	
	/**
   * Method to check valid token
   * 
   * @return
   */
  private static boolean isValidToken() {
    if (OAUTH_TOKEN == null || tokenValidity == null) {
      return false;
    } else if (Calendar.getInstance().after(tokenValidity)) {
      OAUTH_TOKEN = null;
      return (null != refreshOAuth2Token());
    } else {
      return true;
    }
  }

	/**
	 * Construct Object of given class from JSON String
	 * 
	 * @param jsonString
	 * @param type
	 * @return
	 * @throws Exception
	 */
	public static Object toObject(String jsonString, Class<?> type) {
		if (jsonString == null) {
			return null;
		} else {
			return gSon.fromJson(jsonString, type);
		}
	}

}
