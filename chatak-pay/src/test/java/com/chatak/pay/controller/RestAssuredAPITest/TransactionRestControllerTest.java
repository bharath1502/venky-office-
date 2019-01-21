package com.chatak.pay.controller.RestAssuredAPITest;

import org.json.JSONObject;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.ResponseBody;

public class TransactionRestControllerTest {

	final static String CLIENT_SSO_LOGIN = "/clientSsoLogin";

	@BeforeClass
	public static void configure() {
		RestAssured.baseURI = "http://localhost:8080/paygate/pg/transaction";
	}

	// Test with clientSoLogin API
	@Test
	public void testclientSsoLogin() {
		String requestBody = "{\"currentAppVersion\": \"1.3.6\",\"password\": \"Chatak@123!\",\"username\": \"haneef\",\"deviceSerial\": "
				+ "\"352308061370442\",\"timeZoneOffset\": \"GMT+0530\",\"timeZoneRegion\": \"Asia/Calcutta\"}";
		ResponseBody<?> responseBody = RestAssured.given().contentType(ContentType.JSON).body(requestBody).when()
				.post(CLIENT_SSO_LOGIN).getBody();
		JSONObject responseJson = new JSONObject(responseBody.asString());
		Assert.assertEquals("errorCode code of GEN_001 is expected", "GEN_001", responseJson.get("errorCode"));
		Assert.assertEquals("Response message of Success is expected", "Success",
				responseJson.getString("errorMessage"));
	}

	@Test
	public void clientSsoLoginTestWithWrongUserName() {
		String requestBody = "{\"currentAppVersion\": \"1.3.6\",\"password\": \"Chatak@123!\",\"username\": \"hanef\",\"deviceSerial\": "
				+ "\"352308061370442\",\"timeZoneOffset\": \"GMT+0530\",\"timeZoneRegion\": \"Asia/Calcutta\"}";
		ResponseBody<?> responseBody = RestAssured.given().contentType(ContentType.JSON).body(requestBody).when()
				.post(CLIENT_SSO_LOGIN).getBody();
		JSONObject responseJson = new JSONObject(responseBody.asString());
		Assert.assertEquals("errorCode code of TXN_0131 is expected", "TXN_0131", responseJson.get("errorCode"));
		Assert.assertEquals("Response message of Invalid username is expected", "Invalid username",
				responseJson.getString("errorMessage"));
	}

	@Test
	public void clientSsoLoginTestWithWrongPassword() {
		String requestBody = "{\"currentAppVersion\": \"1.3.6\",\"password\": \"Chatak@1234!\",\"username\": \"haneef\",\"deviceSerial\": "
				+ "\"352308061370442\",\"timeZoneOffset\": \"GMT+0530\",\"timeZoneRegion\": \"Asia/Calcutta\"}";
		ResponseBody<?> responseBody = RestAssured.given().contentType(ContentType.JSON).body(requestBody).when()
				.post(CLIENT_SSO_LOGIN).getBody();
		JSONObject responseJson = new JSONObject(responseBody.asString());
		Assert.assertEquals("errorCode code of GEN_002 is expected", "GEN_002", responseJson.get("errorCode"));
		Assert.assertEquals("Response message of The username or password that you entered is incorrect is expected",
				"The username or password that you entered is incorrect", responseJson.getString("errorMessage"));
	}

	@Test
	public void clientSsoLoginTestNoValues() {
		String requestBody = "{\"currentAppVersion\": \"\",\"password\": \"\",\"username\": "
				+ "\"\",\"deviceSerial\": \"\",\"timeZoneOffset\": \"\",\"timeZoneRegion\": \"\"}";
		ResponseBody<?> responseBody = RestAssured.given().contentType(ContentType.JSON).body(requestBody).when()
				.post(CLIENT_SSO_LOGIN).getBody();
		JSONObject responseJson = new JSONObject(responseBody.asString());
		Assert.assertEquals("errorCode code of GEN_002 is expected", "GEN_002", responseJson.get("errorCode"));
		Assert.assertEquals("Response message of userName is required field is expected", "Username is required",
				responseJson.getString("errorMessage"));
	}

	@Test
	public void clientSsoLoginTestWithoutCurrentAppVersion() {
		String requestBody = "{\"password\": \"Chatak@123!\",\"username\": \"haneef\",\"deviceSerial\": "
				+ "\"352308061370442\",\"timeZoneOffset\": \"GMT+0530\",\"timeZoneRegion\": \"Asia/Calcutta\"}";
		ResponseBody<?> responseBody = RestAssured.given().contentType(ContentType.JSON).body(requestBody).when()
				.post(CLIENT_SSO_LOGIN).getBody();
		JSONObject responseJson = new JSONObject(responseBody.asString());
		Assert.assertEquals("errorCode code of TXN_0130 is expected", "TXN_0130", responseJson.get("errorCode"));
		Assert.assertEquals("Response message of currentAppVersion is required field",
				"currentAppVersion is required field", responseJson.getString("errorMessage"));
	}

	@Test
	public void clientSsoLoginTestEmptyCurrentAppVersion() {
		String requestBody = "{\"currentAppVersion\": \"\",\"password\": \"Chatak@123!\",\"username\": \"haneef\",\"deviceSerial\": \"352308061370442\"}";
		ResponseBody<?> responseBody = RestAssured.given().contentType(ContentType.JSON).body(requestBody).when()
				.post(CLIENT_SSO_LOGIN).getBody();
		JSONObject responseJson = new JSONObject(responseBody.asString());
		Assert.assertEquals("errorCode code of TXN_0134 is expected", "TXN_0134", responseJson.get("errorCode"));
		Assert.assertEquals("Response message of Invalid currentAppVersion ", "Invalid currentAppVersion",
				responseJson.getString("errorMessage"));
	}

	@Test
	public void clientSsoLoginTestWithNodeviceSerial() {
		String requestBody = "{\"password\": \"Chatak@123!\",\"username\": \"haneef\",\"timeZoneOffset\": \"GMT+0530\",\"timeZoneRegion\": \"Asia/Calcutta\"}";
		ResponseBody<?> responseBody = RestAssured.given().contentType(ContentType.JSON).body(requestBody).when()
				.post(CLIENT_SSO_LOGIN).getBody();
		JSONObject responseJson = new JSONObject(responseBody.asString());
		Assert.assertEquals("errorCode code of TXN_0129 is expected", "TXN_0129", responseJson.get("errorCode"));
		Assert.assertEquals("Response message of deviceSerial is required field", "deviceSerial is required field",
				responseJson.getString("errorMessage"));
	}

	@Test
	public void clientSsoLoginTestWithdeviceSerialPassWrong() {
		String requestBody = "{\"currentAppVersion\": \"1.3.6\",\"password\": \"Chatak@123!\",\"username\": \"haneef\",\"deviceSerial\": "
				+ "\"35230806137044\",\"timeZoneOffset\": \"GMT+0530\",\"timeZoneRegion\": \"Asia/Calcutta\"}";
		ResponseBody<?> responseBody = RestAssured.given().contentType(ContentType.JSON).body(requestBody).when()
				.post(CLIENT_SSO_LOGIN).getBody();
		JSONObject responseJson = new JSONObject(responseBody.asString());
		Assert.assertEquals("errorCode code of TXN_0133 is expected", "TXN_0133", responseJson.get("errorCode"));
		Assert.assertEquals("Response message of Invalid deviceSerial", "Invalid deviceSerial",
				responseJson.getString("errorMessage"));
	}
}
