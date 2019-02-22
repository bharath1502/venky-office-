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

public class ClientProcessForVoidApiTest {
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
	private TransactionRequest voidRequest;

	public ClientProcessForVoidApiTest() {

		loginRequest = new LoginRequest();
		loginRequest.setUsername("anoop123");
		loginRequest.setPassword("Ipsidy@123!");
		loginRequest.setCurrentAppVersion("1.3.6");
		loginRequest.setDeviceSerial("352308061370442");

		voidRequest = new TransactionRequest();
		voidRequest.setCgRefNumber("4000042300");
		voidRequest.setTxnRefNumber("475524812570");
		voidRequest.setUserName("anoop123");
		voidRequest.setDeviceSerial("352308061370442");
		voidRequest.setMerchantCode("219141112224310");
		voidRequest.setTerminalId("33705059");
		voidRequest.setTimeZoneOffset("GMT+0530");
		voidRequest.setTimeZoneRegion("Asia/Calcutta");
		voidRequest.setTransactionType(TransactionType.REFUND);
		voidRequest.setEntryMode(EntryModeEnum.MANUAL);

	}

	private JSONObject getAccessToken() {
		Response responseBody = (Response) given().contentType(ContentType.JSON).body(loginRequest).when()
				.post(Constant.ROOT_URL + CLIENT_SSO_LOGIN).then().extract().body();
		JSONObject responseJson = new JSONObject(responseBody.asString());
		return responseJson;
	}

	private JSONObject clientProcessResponse(JSONObject responseJson) {
		Response responseBody = given().contentType(ContentType.JSON)
				.header(AUTH_HEADER, TOKEN_BEARER + responseJson.getString("accessToken")).body(voidRequest).when()
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
		voidRequest.setCgRefNumber("400004230");
		JSONObject responseJson = getAccessToken();
		responseJson = clientProcessResponse(responseJson);
		Assert.assertEquals(TXN_0146, responseJson.get("errorCode"));
	}

	@Test
	public void ClientProcessWithCfgNumberNull() {
		voidRequest.setCgRefNumber(null);
		JSONObject responseJson = getAccessToken();
		responseJson = clientProcessResponse(responseJson);
		Assert.assertEquals(TXN_0144, responseJson.get("errorCode"));
	}

	@Test
	public void ClientProcessWithWrongTxnRefNumber() {
		voidRequest.setTxnRefNumber("475524812");
		JSONObject responseJson = getAccessToken();
		responseJson = clientProcessResponse(responseJson);
		Assert.assertEquals(TXN_0147, responseJson.get("errorCode"));
	}

	@Test
	public void ClientProcessWithTxnRefNumberNull() {
		voidRequest.setTxnRefNumber(null);
		JSONObject responseJson = getAccessToken();
		responseJson = clientProcessResponse(responseJson);
		Assert.assertEquals(TXN_0145, responseJson.get("errorCode"));
	}

	@Test
	public void ClientProcessWithUserNameWrong() {
		voidRequest.setUserName("anoop");
		JSONObject responseJson = getAccessToken();
		responseJson = clientProcessResponse(responseJson);
		Assert.assertEquals(TXN_0131, responseJson.get("errorCode"));
	}

	@Test
	public void ClientProcessWithUserNameNull() {
		voidRequest.setUserName(null);
		JSONObject responseJson = getAccessToken();
		responseJson = clientProcessResponse(responseJson);
		Assert.assertEquals(TXN_0121, responseJson.get("errorCode"));
	}

	@Test
	public void ClientProcessWithWrongDeviceSerial() {
		voidRequest.setDeviceSerial("352308061370");
		JSONObject responseJson = getAccessToken();
		responseJson = clientProcessResponse(responseJson);
		Assert.assertEquals(TXN_0133, responseJson.get("errorCode"));
	}

	@Test
	public void ClientProcessWithDeviceSerialNull() {
		voidRequest.setDeviceSerial(null);
		JSONObject responseJson = getAccessToken();
		responseJson = clientProcessResponse(responseJson);
		Assert.assertEquals(TXN_0129, responseJson.get("errorCode"));
	}

	@Test
	public void ClientProcessWithWrongMarchantCode() {
		voidRequest.setMerchantCode("21914111222431");
		JSONObject responseJson = getAccessToken();
		responseJson = clientProcessResponse(responseJson);
		Assert.assertEquals(TXN_0140, responseJson.get("errorCode"));
	}

	@Test
	public void ClientProcessWithMerchantCodeNull() {
		voidRequest.setMerchantCode(null);
		JSONObject responseJson = getAccessToken();
		responseJson = clientProcessResponse(responseJson);
		Assert.assertEquals(TXN_0136, responseJson.get("errorCode"));
	}

	@Test
	public void ClientProcessWithWrongTerminalId() {
		voidRequest.setTerminalId("337050");
		JSONObject responseJson = getAccessToken();
		responseJson = clientProcessResponse(responseJson);
		Assert.assertEquals(TXN_0141, responseJson.get("errorCode"));
	}

	@Test
	public void ClientProcessWithTerminalIdNull() {
		voidRequest.setTerminalId(null);
		JSONObject responseJson = getAccessToken();
		responseJson = clientProcessResponse(responseJson);
		Assert.assertEquals(TXN_0175, responseJson.get("errorCode"));
	}

	@Test
	public void ClientProcessWithWrongTimeZoneOffset() {
		voidRequest.setTimeZoneOffset("GMT");
		JSONObject responseJson = getAccessToken();
		responseJson = clientProcessResponse(responseJson);
		Assert.assertEquals(TXN_0142, responseJson.get("errorCode"));
	}

	@Test
	public void ClientProcessWithTimeZoneOffsetNull() {
		voidRequest.setTimeZoneOffset(null);
		JSONObject responseJson = getAccessToken();
		responseJson = clientProcessResponse(responseJson);
		Assert.assertEquals(TXN_0137, responseJson.get("errorCode"));
	}

	@Test
	public void ClientProcessWithWrongTimeZoneRegion() {
		voidRequest.setTimeZoneRegion("USA");
		JSONObject responseJson = getAccessToken();
		responseJson = clientProcessResponse(responseJson);
		Assert.assertEquals(TXN_0143, responseJson.get("errorCode"));
	}

	@Test
	public void ClientProcessWithTimeZoneRegionNull() {
		voidRequest.setTimeZoneRegion(null);
		JSONObject responseJson = getAccessToken();
		responseJson = clientProcessResponse(responseJson);
		Assert.assertEquals(TXN_0138, responseJson.get("errorCode"));
	}

	@Test
	public void ClientProcessWithTransactionTypeNull() {
		voidRequest.setTransactionType(null);
		JSONObject responseJson = getAccessToken();
		responseJson = clientProcessResponse(responseJson);
		Assert.assertEquals(TXN_0135, responseJson.get("errorCode"));
	}

	@Test
	public void ClientProcessWithWrongTransactionType() {
		voidRequest.setTransactionType(TransactionType.AUTH);
		JSONObject responseJson = getAccessToken();
		responseJson = clientProcessResponse(responseJson);
		Assert.assertEquals(TXN_0139, responseJson.get("errorCode"));
	}

	@Test
	public void ClientProcessWithWrongEntryModeEnum() {
		voidRequest.setEntryMode(EntryModeEnum.ACCOUNT_PAY);
		JSONObject responseJson = getAccessToken();
		responseJson = clientProcessResponse(responseJson);
		Assert.assertEquals(R3, responseJson.get("errorCode"));
	}

}
