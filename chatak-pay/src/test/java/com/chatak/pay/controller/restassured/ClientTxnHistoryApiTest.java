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
import io.restassured.response.ResponseBody;

public class ClientTxnHistoryApiTest {
	final static String CLIENT_SSO_LOGIN = "/clientSsoLogin";
	final static String CLIENT_TXN_HISTORY = "/clientTxnHistory";
	private static final String AUTH_HEADER = "Authorization";
	private static final String TOKEN_BEARER = "Bearer ";
	final static String ERROR_CODE_00 = "00";
	final static String ERROR_CODE_01 = "01";
	final static String TXN_0131 = "TXN_0131";

	private LoginRequest loginrequest;
	private TransactionHistoryRequest transactionrequest;

	public ClientTxnHistoryApiTest() {
		loginrequest = new LoginRequest();
		loginrequest.setUsername("haneef");
		loginrequest.setPassword("Ipsidy@1234!");
		loginrequest.setCurrentAppVersion("1.3.6");
		loginrequest.setDeviceSerial("352308061370442");

		transactionrequest = new TransactionHistoryRequest();
		transactionrequest.setTransactionDate("2019-01-24");
		transactionrequest.setMerchantCode("654454706617480");
		transactionrequest.setTransactionId("128");
		transactionrequest.setUserName("haneef");

	}

	private JSONObject getAccessToken() {
		Response responseBody = (Response) given().contentType(ContentType.JSON).body(loginrequest).when()
				.post(Constant.ROOT_URL + CLIENT_SSO_LOGIN).then().extract().body();
		JSONObject responseJson = new JSONObject(responseBody.asString());
		return responseJson;
	}

	@Test
	public void testWithTransactionHistoryValidCredentials() {
		JSONObject responseJson = getAccessToken();
		ResponseBody responseBody = given().contentType(ContentType.JSON)
				.header(AUTH_HEADER, TOKEN_BEARER + responseJson.getString("accessToken")).body(transactionrequest)
				.when().post(Constant.ROOT_URL + CLIENT_TXN_HISTORY).getBody();
		responseJson = new JSONObject(responseBody.asString());
		Assert.assertEquals("00", responseJson.get("errorCode"));
	}

	@Test
	public void TransactionHistoryWithWrongUserName() {
		transactionrequest.setUserName("hanef");
		JSONObject responseJson = getAccessToken();
		ResponseBody responseBody = given().contentType(ContentType.JSON)
				.header(AUTH_HEADER, TOKEN_BEARER + responseJson.getString("accessToken")).body(transactionrequest)
				.when().post(Constant.ROOT_URL + CLIENT_TXN_HISTORY).getBody();
		responseJson = new JSONObject(responseBody.asString());
		Assert.assertEquals("TXN_0131", responseJson.get("errorCode"));
	}

	@Test
	public void TransactionHistoryWithWrongTransactionId() {
		transactionrequest.setTransactionId("18");
		JSONObject responseJson = getAccessToken();
		ResponseBody responseBody = given().contentType(ContentType.JSON)
				.header(AUTH_HEADER, TOKEN_BEARER + responseJson.getString("accessToken")).body(transactionrequest)
				.when().post(Constant.ROOT_URL + CLIENT_TXN_HISTORY).getBody();
		responseJson = new JSONObject(responseBody.asString());
		Assert.assertEquals("01", responseJson.get("errorCode"));
	}
}
