package com.chatak.pay.controller.restassured;

import static io.restassured.RestAssured.given;

import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Test;

import com.chatak.pay.constants.Constant;
import com.chatak.pay.controller.model.LoginRequest;
import com.chatak.pg.bean.ChangePasswordRequest;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;

public class ChangePasswordApiTest {
	final static String CLIENT_SSO_LOGIN = "/clientSsoLogin";
	final static String CLIENT_CHANGE_PASSWORD = "/changePassword";
	private static final String AUTH_HEADER = "Authorization";
	private static final String TOKEN_BEARER = "Bearer ";
	final static String GEN_001 = "GEN_001";
	final static String GEN_002 = "GEN_002";
	final static String TXN_0119 = "TXN_0119";
	final static String TXN_0120 = "TXN_0120";
	final static String TXN_0121 = "TXN_0121";
	final static String TXN_0122 = "TXN_0122";
	final static String TXN_0123 = "TXN_0123";
	final static String TXN_0124 = "TXN_0124";
	final static String TXN_0126 = "TXN_0126";
	final static String TXN_0127 = "TXN_0127";
	final static String TXN_0128 = "TXN_0128";
	final static String TXN_0131 = "TXN_0131";
	final static String TXN_0403 = "TXN_0403";

	private LoginRequest loginrequest;
	private ChangePasswordRequest changepasswordrequest;

	public ChangePasswordApiTest() {
		loginrequest = new LoginRequest();
		loginrequest.setUsername("haneef");
		loginrequest.setPassword("Ipsidy@1234!");
		loginrequest.setCurrentAppVersion("1.3.6");
		loginrequest.setDeviceSerial("352308061370442");

		changepasswordrequest = new ChangePasswordRequest();
		changepasswordrequest.setUserName("haneef");
		changepasswordrequest.setCurrentPassword("Ipsidy@123!");
		changepasswordrequest.setNewPassword("Ipsidy@1234!");
		changepasswordrequest.setConfirmPassword("Ipsidy@1234!");

	}

	private JSONObject getAccessToken() {
		Response responseBody = (Response) given().contentType(ContentType.JSON).body(loginrequest).when()
				.post(Constant.ROOT_URL + CLIENT_SSO_LOGIN).then().extract().body();
		JSONObject responseJson = new JSONObject(responseBody.asString());
		return responseJson;
	}

	@Test
	public void testWithChangePassword() {
		JSONObject responseJson = getAccessToken();
		ResponseBody responseBody = given().contentType(ContentType.JSON)
				.header("Authorization", "Bearer " + responseJson.getString("accessToken")).body(changepasswordrequest)
				.when().post(Constant.ROOT_URL + CLIENT_CHANGE_PASSWORD).getBody();
		responseJson = new JSONObject(responseBody.asString());
		Assert.assertEquals("GEN_001", responseJson.get("errorCode"));
	}

	@Test
	public void changePasswordWithWrongUserName() {
		changepasswordrequest.setUserName("haeef");

		JSONObject responseJson = getAccessToken();
		ResponseBody responseBody = given().contentType(ContentType.JSON)
				.header("Authorization", "Bearer " + responseJson.getString("accessToken")).body(changepasswordrequest)
				.when().post(Constant.ROOT_URL + CLIENT_CHANGE_PASSWORD).getBody();
		responseJson = new JSONObject(responseBody.asString());
		Assert.assertEquals("TXN_0131", responseJson.get("errorCode"));
	}

	@Test
	public void changePasswordWithWrongPassword() {
		changepasswordrequest.setCurrentPassword("Ipsidy@123!");
		JSONObject responseJson = getAccessToken();
		ResponseBody responseBody = given().contentType(ContentType.JSON)
				.header("Authorization", "Bearer " + responseJson.getString("accessToken")).body(changepasswordrequest)
				.when().post(Constant.ROOT_URL + CLIENT_CHANGE_PASSWORD).getBody();
		responseJson = new JSONObject(responseBody.asString());
		Assert.assertEquals("GEN_002", responseJson.get("errorCode"));
	}

	@Test
	public void changePasswordWithUserNameNull() {
		changepasswordrequest.setUserName(null);
		JSONObject responseJson = getAccessToken();
		ResponseBody responseBody = given().contentType(ContentType.JSON)
				.header("Authorization", "Bearer " + responseJson.getString("accessToken")).body(changepasswordrequest)
				.when().post(Constant.ROOT_URL + CLIENT_CHANGE_PASSWORD).getBody();
		responseJson = new JSONObject(responseBody.asString());
		Assert.assertEquals("TXN_0121", responseJson.get("errorCode"));
	}

	@Test
	public void changePasswordWithCurrentPasswordNull() {
		changepasswordrequest.setCurrentPassword(null);
		JSONObject responseJson = getAccessToken();
		ResponseBody responseBody = given().contentType(ContentType.JSON)
				.header("Authorization", "Bearer " + responseJson.getString("accessToken")).body(changepasswordrequest)
				.when().post(Constant.ROOT_URL + CLIENT_CHANGE_PASSWORD).getBody();
		responseJson = new JSONObject(responseBody.asString());
		Assert.assertEquals("TXN_0122", responseJson.get("errorCode"));
	}

	@Test
	public void changePasswordWithNewPasswordNull() {
		changepasswordrequest.setNewPassword(null);
		JSONObject responseJson = getAccessToken();
		ResponseBody responseBody = given().contentType(ContentType.JSON)
				.header("Authorization", "Bearer " + responseJson.getString("accessToken")).body(changepasswordrequest)
				.when().post(Constant.ROOT_URL + CLIENT_CHANGE_PASSWORD).getBody();
		responseJson = new JSONObject(responseBody.asString());
		Assert.assertEquals("TXN_0123", responseJson.get("errorCode"));
	}

	@Test
	public void changePasswordWithConfirmPasswordNull() {
		changepasswordrequest.setConfirmPassword(null);
		JSONObject responseJson = getAccessToken();
		ResponseBody responseBody = given().contentType(ContentType.JSON)
				.header("Authorization", "Bearer " + responseJson.getString("accessToken")).body(changepasswordrequest)
				.when().post(Constant.ROOT_URL + CLIENT_CHANGE_PASSWORD).getBody();
		responseJson = new JSONObject(responseBody.asString());
		Assert.assertEquals("TXN_0124", responseJson.get("errorCode"));
	}

	@Test
	public void changePasswordWithCurrentAndNewPasswordSame() {
		changepasswordrequest.setNewPassword("Ipsidy@123!");
		changepasswordrequest.setConfirmPassword("Ipsidy@123!");
		JSONObject responseJson = getAccessToken();
		ResponseBody responseBody = given().contentType(ContentType.JSON)
				.header("Authorization", "Bearer " + responseJson.getString("accessToken")).body(changepasswordrequest)
				.when().post(Constant.ROOT_URL + CLIENT_CHANGE_PASSWORD).getBody();
		responseJson = new JSONObject(responseBody.asString());
		Assert.assertEquals("TXN_0119", responseJson.get("errorCode"));
	}

	@Test
	public void changePasswordWithNewAndConfirmPasswordNotSame() {
		changepasswordrequest.setConfirmPassword("Ipsidy@123!");
		JSONObject responseJson = getAccessToken();
		ResponseBody responseBody = given().contentType(ContentType.JSON)
				.header("Authorization", "Bearer " + responseJson.getString("accessToken")).body(changepasswordrequest)
				.when().post(Constant.ROOT_URL + CLIENT_CHANGE_PASSWORD).getBody();
		responseJson = new JSONObject(responseBody.asString());
		Assert.assertEquals("TXN_0120", responseJson.get("errorCode"));
	}

	@Test
	public void changePasswordWithPasswordWithCapsLetters() {
		changepasswordrequest.setCurrentPassword("IPSIDY@1234!");
		JSONObject responseJson = getAccessToken();
		ResponseBody responseBody = given().contentType(ContentType.JSON)
				.header("Authorization", "Bearer " + responseJson.getString("accessToken")).body(changepasswordrequest)
				.when().post(Constant.ROOT_URL + CLIENT_CHANGE_PASSWORD).getBody();
		responseJson = new JSONObject(responseBody.asString());
		Assert.assertEquals("TXN_0126", responseJson.get("errorCode"));
	}

	@Test
	public void changePasswordWithUserNameWithCapsLetters() {
		changepasswordrequest.setUserName("HANEEF");
		JSONObject responseJson = getAccessToken();
		ResponseBody responseBody = given().contentType(ContentType.JSON)
				.header("Authorization", "Bearer " + responseJson.getString("accessToken")).body(changepasswordrequest)
				.when().post(Constant.ROOT_URL + CLIENT_CHANGE_PASSWORD).getBody();
		responseJson = new JSONObject(responseBody.asString());
		Assert.assertEquals("TXN_0403", responseJson.get("errorCode"));
	}

	@Test
	public void changePasswordNewPasswordWithCapsLetters() {
		changepasswordrequest.setNewPassword("IPSIDY@123!");
		JSONObject responseJson = getAccessToken();
		ResponseBody responseBody = given().contentType(ContentType.JSON)
				.header("Authorization", "Bearer " + responseJson.getString("accessToken")).body(changepasswordrequest)
				.when().post(Constant.ROOT_URL + CLIENT_CHANGE_PASSWORD).getBody();
		responseJson = new JSONObject(responseBody.asString());
		Assert.assertEquals("TXN_0127", responseJson.get("errorCode"));
	}

	@Test
	public void changePasswordWithConfirmPasswordWithCapsLetters() {
		changepasswordrequest.setConfirmPassword("IPSIDY@123!");
		JSONObject responseJson = getAccessToken();
		ResponseBody responseBody = given().contentType(ContentType.JSON)
				.header("Authorization", "Bearer " + responseJson.getString("accessToken")).body(changepasswordrequest)
				.when().post(Constant.ROOT_URL + CLIENT_CHANGE_PASSWORD).getBody();
		responseJson = new JSONObject(responseBody.asString());
		Assert.assertEquals("TXN_0128", responseJson.get("errorCode"));
	}

}
