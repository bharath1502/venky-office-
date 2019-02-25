package com.chatak.pay.controller.restassured;

import static io.restassured.RestAssured.given;

import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Test;

import com.chatak.pay.constants.Constant;
import com.chatak.pay.controller.model.LoginRequest;
import com.chatak.pay.controller.model.TransactionRequest;
import com.chatak.pg.enums.EntryModeEnum;
import com.chatak.pg.enums.TransactionType;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class ClientProcessForRefundApiTest {
	private static final String CLIENT_SSO_LOGIN = "/clientSsoLogin";
	private static final String CLIENT_PROCESS = "/clientProcess";
	private static final String AUTH_HEADER = "Authorization";
	private static final String TOKEN_BEARER = "Bearer ";
	private static final String ERROR_CODE_00 = "ERROR_CODE_00";
	private static final String TXN_0131 = "TXN_0131";
	private static final String TXN_0121 = "TXN_0121";
	private static final String TXN_0133 = "TXN_0133";
	private static final String TXN_0129 = "TXN_0129";
	private static final String TXN_0140 = "TXN_0140";
	private static final String TXN_0136 = "TXN_0136";
	private static final String TXN_0141 = "TXN_0141";
	private static final String TXN_0175 = "TXN_0175";
	private static final String TXN_0142 = "TXN_0142";
	private static final String TXN_0137 = "TXN_0137";
	private static final String TXN_0143 = "TXN_0143";
	private static final String TXN_0138 = "TXN_0138";
	private static final String TXN_0135 = "TXN_0135";
	private static final String TXN_0139 = "TXN_0139";
	private static final String R3 = "R3";
	private static final String TXN_0146 = "TXN_0146";
	private static final String TXN_0144 = "TXN_0144";
	private static final String TXN_0147 = "TXN_0147";
	private static final String TXN_0145 = "TXN_0145";

	private LoginRequest loginRequest;
	private TransactionRequest refundRequest;

	public ClientProcessForRefundApiTest() {

		loginRequest = new LoginRequest();
		loginRequest.setUsername("anoop123");
		loginRequest.setPassword("Ipsidy@123!");
		loginRequest.setCurrentAppVersion("1.3.6");
		loginRequest.setDeviceSerial("352308061370442");

		refundRequest = new TransactionRequest();
		refundRequest.setCgRefNumber("4000042300");
		refundRequest.setTxnRefNumber("475524812570");
		refundRequest.setUserName("anoop123");
		refundRequest.setDeviceSerial("352308061370442");
		refundRequest.setMerchantCode("219141112224310");
		refundRequest.setTerminalId("33705059");
		refundRequest.setTimeZoneOffset("GMT+0530");
		refundRequest.setTimeZoneRegion("Asia/Calcutta");
		refundRequest.setTransactionType(TransactionType.REFUND);
		refundRequest.setEntryMode(EntryModeEnum.MANUAL);

	}

	private JSONObject getAccessToken() {
		Response responseBody = (Response) given().contentType(ContentType.JSON).body(loginRequest).when()
				.post(Constant.ROOT_URL + CLIENT_SSO_LOGIN).then().extract().body();
		JSONObject responseJson = new JSONObject(responseBody.asString());
		return responseJson;
	}

	private JSONObject clientProcessResponse(JSONObject responseJson) {
		Response responseBody = given().contentType(ContentType.JSON)
				.header(AUTH_HEADER, TOKEN_BEARER + responseJson.getString("accessToken")).body(refundRequest).when()
				.post(Constant.ROOT_URL + CLIENT_PROCESS);
		responseJson = new JSONObject(responseBody.asString());
		return responseJson;
	}

	@Test
	public void testWithClientProcessValidCredentials() {
		JSONObject responseJson = getAccessToken();
		responseJson = clientProcessResponse(responseJson);
		Assert.assertEquals(ERROR_CODE_00, responseJson.get("errorCode"));
	}

	@Test
	public void ClientProcessWithWrongCfgNumber() {
		refundRequest.setCgRefNumber("400004230");
		JSONObject responseJson = getAccessToken();
		responseJson = clientProcessResponse(responseJson);
		Assert.assertEquals(TXN_0146, responseJson.get("errorCode"));
	}

	@Test
	public void ClientProcessWithCfgNumberNull() {
		refundRequest.setCgRefNumber(null);
		JSONObject responseJson = getAccessToken();
		responseJson = clientProcessResponse(responseJson);
		Assert.assertEquals(TXN_0144, responseJson.get("errorCode"));
	}

	@Test
	public void ClientProcessWithWrongTxnRefNumber() {
		refundRequest.setTxnRefNumber("48695723698");
		JSONObject responseJson = getAccessToken();
		responseJson = clientProcessResponse(responseJson);
		Assert.assertEquals(TXN_0147, responseJson.get("errorCode"));
	}

	@Test
	public void ClientProcessWithTxnRefNumberNull() {
		refundRequest.setTxnRefNumber(null);
		JSONObject responseJson = getAccessToken();
		responseJson = clientProcessResponse(responseJson);
		Assert.assertEquals(TXN_0145, responseJson.get("errorCode"));
	}

	@Test
	public void ClientProcessWithUserNameWrong() {
		refundRequest.setUserName("anoop");
		JSONObject responseJson = getAccessToken();
		responseJson = clientProcessResponse(responseJson);
		Assert.assertEquals(TXN_0131, responseJson.get("errorCode"));
	}

	@Test
	public void ClientProcessWithUserNameNull() {
		refundRequest.setUserName(null);
		JSONObject responseJson = getAccessToken();
		responseJson = clientProcessResponse(responseJson);
		Assert.assertEquals(TXN_0121, responseJson.get("errorCode"));
	}

	@Test
	public void ClientProcessWithWrongDeviceSerial() {
		refundRequest.setDeviceSerial("5245879635");
		JSONObject responseJson = getAccessToken();
		responseJson = clientProcessResponse(responseJson);
		Assert.assertEquals(TXN_0133, responseJson.get("errorCode"));
	}

	@Test
	public void ClientProcessWithDeviceSerialNull() {
		refundRequest.setDeviceSerial(null);
		JSONObject responseJson = getAccessToken();
		responseJson = clientProcessResponse(responseJson);
		Assert.assertEquals(TXN_0129, responseJson.get("errorCode"));
	}

	@Test
	public void ClientProcessWithWrongMarchantCode() {
		refundRequest.setMerchantCode("5689566555");
		JSONObject responseJson = getAccessToken();
		responseJson = clientProcessResponse(responseJson);
		Assert.assertEquals(TXN_0140, responseJson.get("errorCode"));
	}

	@Test
	public void ClientProcessWithMerchantCodeNull() {
		refundRequest.setMerchantCode(null);
		JSONObject responseJson = getAccessToken();
		responseJson = clientProcessResponse(responseJson);
		Assert.assertEquals(TXN_0136, responseJson.get("errorCode"));
	}

	@Test
	public void ClientProcessWithWrongTerminalId() {
		refundRequest.setTerminalId("421589");
		JSONObject responseJson = getAccessToken();
		responseJson = clientProcessResponse(responseJson);
		Assert.assertEquals(TXN_0141, responseJson.get("errorCode"));
	}

	@Test
	public void ClientProcessWithTerminalIdNull() {
		refundRequest.setTerminalId(null);
		JSONObject responseJson = getAccessToken();
		responseJson = clientProcessResponse(responseJson);
		Assert.assertEquals(TXN_0175, responseJson.get("errorCode"));
	}

	@Test
	public void ClientProcessWithWrongTimeZoneOffset() {
		refundRequest.setTimeZoneOffset("GMT");
		JSONObject responseJson = getAccessToken();
		responseJson = clientProcessResponse(responseJson);
		Assert.assertEquals(TXN_0142, responseJson.get("errorCode"));
	}

	@Test
	public void ClientProcessWithTimeZoneOffsetNull() {
		refundRequest.setTimeZoneOffset(null);
		JSONObject responseJson = getAccessToken();
		responseJson = clientProcessResponse(responseJson);
		Assert.assertEquals(TXN_0137, responseJson.get("errorCode"));
	}

	@Test
	public void ClientProcessWithWrongTimeZoneRegion() {
		refundRequest.setTimeZoneRegion("UK");
		JSONObject responseJson = getAccessToken();
		responseJson = clientProcessResponse(responseJson);
		Assert.assertEquals(TXN_0143, responseJson.get("errorCode"));
	}

	@Test
	public void ClientProcessWithTimeZoneRegionNull() {
		refundRequest.setTimeZoneRegion(null);
		JSONObject responseJson = getAccessToken();
		responseJson = clientProcessResponse(responseJson);
		Assert.assertEquals(TXN_0138, responseJson.get("errorCode"));
	}

	@Test
	public void ClientProcessWithTransactionTypeNull() {
		refundRequest.setTransactionType(null);
		JSONObject responseJson = getAccessToken();
		responseJson = clientProcessResponse(responseJson);
		Assert.assertEquals(TXN_0135, responseJson.get("errorCode"));
	}

	@Test
	public void ClientProcessWithWrongTransactionType() {
		refundRequest.setTransactionType(TransactionType.AUTH);
		JSONObject responseJson = getAccessToken();
		responseJson = clientProcessResponse(responseJson);
		Assert.assertEquals(TXN_0139, responseJson.get("errorCode"));
	}

	@Test
	public void ClientProcessWithWrongEntryModeEnum() {
		refundRequest.setEntryMode(EntryModeEnum.ACCOUNT_PAY);
		JSONObject responseJson = getAccessToken();
		responseJson = clientProcessResponse(responseJson);
		Assert.assertEquals(R3, responseJson.get("errorCode"));
	}
}
