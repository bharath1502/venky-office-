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

public class ChangePasswordApiTest {

	private static final String CLIENT_SSO_LOGIN = "/clientSsoLogin";
	private static final String CLIENT_CHANGE_PASSWORD = "/changePassword";
	private static final String AUTH_HEADER = "Authorization";
	private static final String TOKEN_BEARER = "Bearer ";
	private static final String GEN_001 = "GEN_001";
	private static final String GEN_002 = "GEN_002";
	private static final String TXN_0119 = "TXN_0119";
	private static final String TXN_0120 = "TXN_0120";
	private static final String TXN_0121 = "TXN_0121";
	private static final String TXN_0122 = "TXN_0122";
	private static final String TXN_0123 = "TXN_0123";
	private static final String TXN_0124 = "TXN_0124";
	private static final String TXN_0126 = "TXN_0126";
	private static final String TXN_0127 = "TXN_0127";
	private static final String TXN_0128 = "TXN_0128";
	private static final String TXN_0131 = "TXN_0131";
	private static final String TXN_0403 = "TXN_0403";

	private LoginRequest loginRequest;
	private ChangePasswordRequest changePasswordRequest;

	public ChangePasswordApiTest() {
		loginRequest = new LoginRequest();
		loginRequest.setUsername("haneef");
		loginRequest.setPassword("Ipsidy@1234!");
		loginRequest.setCurrentAppVersion("1.3.6");
		loginRequest.setDeviceSerial("352308061370442");

		changePasswordRequest = new ChangePasswordRequest();
		changePasswordRequest.setUserName("haneef");
		changePasswordRequest.setCurrentPassword("Ipsidy@123!");
		changePasswordRequest.setNewPassword("Ipsidy@1234!");
		changePasswordRequest.setConfirmPassword("Ipsidy@1234!");

	}

	private JSONObject getAccessToken() {
		Response responseBody = (Response) given().contentType(ContentType.JSON).body(loginRequest).when()
				.post(Constant.ROOT_URL + CLIENT_SSO_LOGIN).then().extract().body();
		JSONObject responseJson = new JSONObject(responseBody.asString());
		return responseJson;
	}

	@Test
	public void testWithChangePassword() {
		JSONObject responseJson = changePasswordResponse();
		Assert.assertEquals(GEN_001, responseJson.get("errorCode"));
	}

	@Test
	public void changePasswordWithWrongUserName() {
		changePasswordRequest.setUserName("haeef");

		JSONObject responseJson = changePasswordResponse();
		Assert.assertEquals(TXN_0131, responseJson.get("errorCode"));
	}

	private JSONObject changePasswordResponse() {
		JSONObject responseJson = getAccessToken();
		Response responseBody = given().contentType(ContentType.JSON)
				.header(AUTH_HEADER, TOKEN_BEARER + responseJson.getString("accessToken")).body(changePasswordRequest)
				.when().post(Constant.ROOT_URL + CLIENT_CHANGE_PASSWORD);
		responseJson = new JSONObject(responseBody.asString());
		return responseJson;
	}

	@Test
	public void changePasswordWithWrongPassword() {
		changePasswordRequest.setCurrentPassword("Ipsidy@123!");
		JSONObject responseJson = changePasswordResponse();
		Assert.assertEquals(GEN_002, responseJson.get("errorCode"));
	}

	@Test
	public void changePasswordWithUserNameNull() {
		changePasswordRequest.setUserName(null);
		JSONObject responseJson = changePasswordResponse();
		Assert.assertEquals(TXN_0121, responseJson.get("errorCode"));
	}

	@Test
	public void changePasswordWithCurrentPasswordNull() {
		changePasswordRequest.setCurrentPassword(null);
		JSONObject responseJson = changePasswordResponse();
		Assert.assertEquals(TXN_0122, responseJson.get("errorCode"));
	}

	@Test
	public void changePasswordWithNewPasswordNull() {
		changePasswordRequest.setNewPassword(null);
		JSONObject responseJson = changePasswordResponse();
		Assert.assertEquals(TXN_0123, responseJson.get("errorCode"));
	}

	@Test
	public void changePasswordWithConfirmPasswordNull() {
		changePasswordRequest.setConfirmPassword(null);
		JSONObject responseJson = changePasswordResponse();
		Assert.assertEquals(TXN_0124, responseJson.get("errorCode"));
	}

	@Test
	public void changePasswordWithCurrentAndNewPasswordSame() {
		changePasswordRequest.setNewPassword("Ipsidy@123!");
		changePasswordRequest.setConfirmPassword("Ipsidy@123!");
		JSONObject responseJson = changePasswordResponse();
		Assert.assertEquals(TXN_0119, responseJson.get("errorCode"));
	}

	@Test
	public void changePasswordWithNewAndConfirmPasswordNotSame() {
		changePasswordRequest.setConfirmPassword("Ipsidy@123!");
		JSONObject responseJson = changePasswordResponse();
		Assert.assertEquals(TXN_0120, responseJson.get("errorCode"));
	}

	@Test
	public void changePasswordWithPasswordWithCapsLetters() {
		changePasswordRequest.setCurrentPassword("IPSIDY@1234!");
		JSONObject responseJson = changePasswordResponse();
		Assert.assertEquals(TXN_0126, responseJson.get("errorCode"));
	}

	@Test
	public void changePasswordWithUserNameWithCapsLetters() {
		changePasswordRequest.setUserName("HANEEF");
		JSONObject responseJson = changePasswordResponse();
		Assert.assertEquals(TXN_0403, responseJson.get("errorCode"));
	}

	@Test
	public void changePasswordNewPasswordWithCapsLetters() {
		changePasswordRequest.setNewPassword("IPSIDY@123!");
		JSONObject responseJson = changePasswordResponse();
		Assert.assertEquals(TXN_0127, responseJson.get("errorCode"));
	}

	@Test
	public void changePasswordWithConfirmPasswordWithCapsLetters() {
		changePasswordRequest.setConfirmPassword("IPSIDY@123!");
		JSONObject responseJson = changePasswordResponse();
		Assert.assertEquals(TXN_0128, responseJson.get("errorCode"));
	}

}
