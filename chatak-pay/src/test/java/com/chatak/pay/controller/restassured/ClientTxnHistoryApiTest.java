package com.chatak.pay.controller.restassured;

import static io.restassured.RestAssured.given;

import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Test;

import com.chatak.pay.constants.Constant;
import com.chatak.pay.controller.model.LoginRequest;
import com.chatak.pg.model.TransactionHistoryRequest;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class ClientTxnHistoryApiTest {
	private static final String CLIENT_SSO_LOGIN = "/clientSsoLogin";
	private static final String CLIENT_TXN_HISTORY = "/clientTxnHistory";
	private static final String AUTH_HEADER = "Authorization";
	private static final String TOKEN_BEARER = "Bearer ";
	private static final String GEN_001 = "GEN_001";
	private static final String ERROR_CODE_01 = "01";
	private static final String TXN_0131 = "TXN_0131";

	private LoginRequest loginRequest;
	private TransactionHistoryRequest transactionRequest;

	public ClientTxnHistoryApiTest() {
		loginRequest = new LoginRequest();
		loginRequest.setUsername("haneef");
		loginRequest.setPassword("Ipsidy@1234!");
		loginRequest.setCurrentAppVersion("1.3.6");
		loginRequest.setDeviceSerial("352308061370442");

		transactionRequest = new TransactionHistoryRequest();
		transactionRequest.setTransactionDate("2019-01-24");
		transactionRequest.setMerchantCode("654454706617480");
		transactionRequest.setTransactionId("128");
		transactionRequest.setUserName("haneef");

	}

	private JSONObject getAccessToken() {
		Response responseBody = (Response) given().contentType(ContentType.JSON).body(loginRequest).when()
				.post(Constant.ROOT_URL + CLIENT_SSO_LOGIN).then().extract().body();
		JSONObject responseJson = new JSONObject(responseBody.asString());
		return responseJson;
	}

	private JSONObject clientTxnResponse() {
		JSONObject responseJson = getAccessToken();
		Response responseBody = given().contentType(ContentType.JSON)
				.header(AUTH_HEADER, TOKEN_BEARER + responseJson.getString("accessToken")).body(transactionRequest)
				.when().post(Constant.ROOT_URL + CLIENT_TXN_HISTORY);
		responseJson = new JSONObject(responseBody.asString());
		return responseJson;
	}

	@Test
	public void testWithTransactionHistoryValidCredentials() {
		JSONObject responseJson = getAccessToken();
		Assert.assertEquals(GEN_001, responseJson.get("errorCode"));
	}

	@Test
	public void TransactionHistoryWithWrongUserName() {
		transactionRequest.setUserName("hanef");
		JSONObject responseJson = clientTxnResponse();
		Assert.assertEquals(TXN_0131, responseJson.get("errorCode"));
	}

	@Test
	public void TransactionHistoryWithWrongTransactionId() {
		transactionRequest.setTransactionId("18");
		JSONObject responseJson = clientTxnResponse();
		Assert.assertEquals(ERROR_CODE_01, responseJson.get("errorCode"));
	}
}
