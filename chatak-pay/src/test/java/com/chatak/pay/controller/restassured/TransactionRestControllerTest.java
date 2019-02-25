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
	final static String GEN_001 = "GEN_001";
	final static String GEN_002 = "GEN_002";
	final static String TXN_0129 = "TXN_0129";
	final static String TXN_0130 = "TXN_0130";
	final static String TXN_0131 = "TXN_0131";
	final static String TXN_0133 = "TXN_0133";
	final static String TXN_0134 = "TXN_0134";

	private LoginRequest request;

	public TransactionRestControllerTest() {
		request = new LoginRequest();
		request.setUsername("haneef");
		request.setPassword("Chatak@1234!");
		request.setCurrentAppVersion("1.3.6");
		request.setDeviceSerial("352308061370442");
	}

	@Test
	public void testClientSsoLogin() {
		ResponseBody responseBody = RestAssured.given().contentType(ContentType.JSON).body(request).when()
				.post(CLIENT_SSO_LOGIN).getBody();
		JSONObject responseJson = new JSONObject(responseBody.asString());
		Assert.assertEquals(GEN_001, responseJson.get("errorCode"));
	}

	@Test
	public void clientSsoLoginTestWithPassWrongUserName() {
		request.setUsername("hneef");
		ResponseBody responseBody = RestAssured.given().contentType(ContentType.JSON).body(request).when()
				.post(CLIENT_SSO_LOGIN).getBody();
		JSONObject responseJson = new JSONObject(responseBody.asString());
		Assert.assertEquals(TXN_0131, responseJson.get("errorCode"));
	}

	@Test
	public void clientSsoLoginTestWithPassWrongPassword() {
		request.setPassword("Chatak@12");
		ResponseBody responseBody = RestAssured.given().contentType(ContentType.JSON).body(request).when()
				.post(CLIENT_SSO_LOGIN).getBody();
		JSONObject responseJson = new JSONObject(responseBody.asString());
		Assert.assertEquals(GEN_002, responseJson.get("errorCode"));
	}

	@Test
	public void clientSsoLoginTesWithPassWrongCurrentAppVersion() {
		request.setCurrentAppVersion("1");
		ResponseBody responseBody = RestAssured.given().contentType(ContentType.JSON).body(request).when()
				.post(CLIENT_SSO_LOGIN).getBody();
		JSONObject responseJson = new JSONObject(responseBody.asString());
		Assert.assertEquals(TXN_0134, responseJson.get("errorCode"));
	}

	@Test
	public void clientSsoLoginTesWithPassWrongDeviceSerial() {
		request.setDeviceSerial("35230806137044");
		ResponseBody responseBody = RestAssured.given().contentType(ContentType.JSON).body(request).when()
				.post(CLIENT_SSO_LOGIN).getBody();
		JSONObject responseJson = new JSONObject(responseBody.asString());
		Assert.assertEquals(TXN_0133, responseJson.get("errorCode"));
	}

	@Test
	public void clientSsoLoginTestWithPassUserNameNull() {
		request.setUsername(null);
		ResponseBody responseBody = RestAssured.given().contentType(ContentType.JSON).body(request).when()
				.post(CLIENT_SSO_LOGIN).getBody();
		JSONObject responseJson = new JSONObject(responseBody.asString());
		Assert.assertEquals(GEN_002, responseJson.get("errorCode"));
	}

	@Test
	public void clientSsoLoginTestWithPassPasswordNull() {
		request.setPassword(null);
		ResponseBody responseBody = RestAssured.given().contentType(ContentType.JSON).body(request).when()
				.post(CLIENT_SSO_LOGIN).getBody();
		JSONObject responseJson = new JSONObject(responseBody.asString());
		Assert.assertEquals(GEN_002, responseJson.get("errorCode"));
	}

	@Test
	public void clientSsoLoginTesPassNullWithCurrentAppVersion() {
		request.setCurrentAppVersion(null);
		ResponseBody responseBody = RestAssured.given().contentType(ContentType.JSON).body(request).when()
				.post(CLIENT_SSO_LOGIN).getBody();
		JSONObject responseJson = new JSONObject(responseBody.asString());
		Assert.assertEquals(TXN_0130, responseJson.get("errorCode"));
	}

	@Test
	public void clientSsoLoginTesWithDeviceSerialPassNull() {
		request.setDeviceSerial(null);
		ResponseBody responseBody = RestAssured.given().contentType(ContentType.JSON).body(request).when()
				.post(CLIENT_SSO_LOGIN).getBody();
		JSONObject responseJson = new JSONObject(responseBody.asString());
		Assert.assertEquals(TXN_0129, responseJson.get("errorCode"));
	}
}
