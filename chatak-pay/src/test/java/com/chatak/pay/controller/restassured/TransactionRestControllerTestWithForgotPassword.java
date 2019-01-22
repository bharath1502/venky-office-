package com.chatak.pay.controller.restassured;

import org.json.JSONObject;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import com.chatak.pg.bean.ForgotPasswordRequest;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.ResponseBody;

public class TransactionRestControllerTestWithForgotPassword {
	@BeforeClass
	public static void configure() {
		RestAssured.baseURI = "http://localhost:8080/paygate/pg/transaction";
	}

	final static String For_Got_Password = "/forgotPassword";
	final static String GEN_001 = "GEN_001";
	final static String TXN_0125 = "TXN_0125";
	final static String TXN_0121 = "TXN_0121";

	private ForgotPasswordRequest request;

	public TransactionRestControllerTestWithForgotPassword() {
		request = new ForgotPasswordRequest();
		request.setUserName("haneef");
		request.setEmail("shaik.haneef@girmiti.com");
	}

	@Test
	public void testWithForgotPassword() {
		ResponseBody responseBody = RestAssured.given().contentType(ContentType.JSON).body(request).when()
				.post(For_Got_Password).getBody();
		JSONObject responseJson = new JSONObject(responseBody.asString());
		Assert.assertEquals(GEN_001, responseJson.get("errorCode"));
	}

	@Test
	public void forgotPasswordTestWithPassWrongUserName() {
		request.setUserName("hanee");
		ResponseBody responseBody = RestAssured.given().contentType(ContentType.JSON).body(request).when()
				.post(For_Got_Password).getBody();
		JSONObject responseJson = new JSONObject(responseBody.asString());
		Assert.assertEquals(TXN_0125, responseJson.get("errorCode"));
	}

	@Test
	public void forgotPasswordTestWithPassUserNameNull() {
		request.setUserName(null);
		ResponseBody responseBody = RestAssured.given().contentType(ContentType.JSON).body(request).when()
				.post(For_Got_Password).getBody();
		JSONObject responseJson = new JSONObject(responseBody.asString());
		Assert.assertEquals(TXN_0121, responseJson.get("errorCode"));
	}
}
