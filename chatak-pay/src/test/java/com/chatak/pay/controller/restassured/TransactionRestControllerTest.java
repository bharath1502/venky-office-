package com.chatak.pay.controller.restassured;

import org.json.JSONObject;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import com.chatak.pay.controller.model.LoginRequest;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.ResponseBody;

//Test with clientSsoLogin API
public class TransactionRestControllerTest {
	@BeforeClass
	public static void configure() {
		RestAssured.baseURI = "http://localhost:8080/paygate/pg/transaction";
	}

	final static String CLIENT_SSO_LOGIN = "/clientSsoLogin";
	private LoginRequest request;

	public TransactionRestControllerTest() {
		request = new LoginRequest();
		request.setUsername("haneef");
		request.setPassword("Chatak@123!");
		request.setCurrentAppVersion("1.3.6");
		request.setDeviceSerial("352308061370442");
	}

	@Test
	public void testClientSsoLogin() {
		ResponseBody<?> responseBody = RestAssured.given().contentType(ContentType.JSON).body(request).when()
				.post(CLIENT_SSO_LOGIN).getBody();
		JSONObject responseJson = new JSONObject(responseBody.asString());
		Assert.assertEquals("errorCode code of GEN_001 is expected", "GEN_001", responseJson.get("errorCode"));
	}

	@Test
	public void clientSsoLoginTestWithPassWrongUserName() {
		request.setUsername("hneef");
		ResponseBody<?> responseBody = RestAssured.given().contentType(ContentType.JSON).body(request).when()
				.post(CLIENT_SSO_LOGIN).getBody();
		JSONObject responseJson = new JSONObject(responseBody.asString());
		Assert.assertEquals("errorCode code of TXN_0131 is expected", "TXN_0131", responseJson.get("errorCode"));
	}

	@Test
	public void clientSsoLoginTestWithPassWrongPassword() {
		request.setPassword("Chatak@12");
		ResponseBody<?> responseBody = RestAssured.given().contentType(ContentType.JSON).body(request).when()
				.post(CLIENT_SSO_LOGIN).getBody();
		JSONObject responseJson = new JSONObject(responseBody.asString());
		Assert.assertEquals("errorCode code of GEN_002 is expected", "GEN_002", responseJson.get("errorCode"));
	}

	@Test
	public void clientSsoLoginTesWithPassWrongCurrentAppVersion() {
		request.setCurrentAppVersion("1");
		ResponseBody<?> responseBody = RestAssured.given().contentType(ContentType.JSON).body(request).when()
				.post(CLIENT_SSO_LOGIN).getBody();
		JSONObject responseJson = new JSONObject(responseBody.asString());
		Assert.assertEquals("errorCode code of TXN_0134 is expected", "TXN_0134", responseJson.get("errorCode"));
	}

	@Test
	public void clientSsoLoginTesWithPassWrongDeviceSerial() {
		request.setDeviceSerial("35230806137044");
		ResponseBody<?> responseBody = RestAssured.given().contentType(ContentType.JSON).body(request).when()
				.post(CLIENT_SSO_LOGIN).getBody();
		JSONObject responseJson = new JSONObject(responseBody.asString());
		Assert.assertEquals("errorCode code of TXN_0133 is expected", "TXN_0133", responseJson.get("errorCode"));
	}

	@Test
	public void clientSsoLoginTestWithPassUserNameNull() {
		request.setUsername(null);
		ResponseBody<?> responseBody = RestAssured.given().contentType(ContentType.JSON).body(request).when()
				.post(CLIENT_SSO_LOGIN).getBody();
		JSONObject responseJson = new JSONObject(responseBody.asString());
		Assert.assertEquals("errorCode code of GEN_002 is expected", "GEN_002", responseJson.get("errorCode"));
	}

	@Test
	public void clientSsoLoginTestWithPassPasswordNull() {
		request.setPassword(null);
		ResponseBody<?> responseBody = RestAssured.given().contentType(ContentType.JSON).body(request).when()
				.post(CLIENT_SSO_LOGIN).getBody();
		JSONObject responseJson = new JSONObject(responseBody.asString());
		Assert.assertEquals("errorCode code of GEN_002 is expected", "GEN_002", responseJson.get("errorCode"));
	}

	@Test
	public void clientSsoLoginTesPassNullWithCurrentAppVersion() {
		request.setCurrentAppVersion(null);
		ResponseBody<?> responseBody = RestAssured.given().contentType(ContentType.JSON).body(request).when()
				.post(CLIENT_SSO_LOGIN).getBody();
		JSONObject responseJson = new JSONObject(responseBody.asString());
		Assert.assertEquals("errorCode code of TXN_0130 is expected", "TXN_0130", responseJson.get("errorCode"));
	}

	@Test
	public void clientSsoLoginTesWithDeviceSerialPassNull() {
		request.setDeviceSerial(null);
		ResponseBody<?> responseBody = RestAssured.given().contentType(ContentType.JSON).body(request).when()
				.post(CLIENT_SSO_LOGIN).getBody();
		JSONObject responseJson = new JSONObject(responseBody.asString());
		Assert.assertEquals("errorCode code of TXN_0129 is expected", "TXN_0129", responseJson.get("errorCode"));
	}
}
