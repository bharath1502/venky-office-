package com.chatak.pay.controller.restassured;

import static io.restassured.RestAssured.*;

import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Test;

import com.chatak.pay.constants.Constant;
import com.chatak.pay.controller.model.CardData;
import com.chatak.pay.controller.model.LoginRequest;
import com.chatak.pay.controller.model.TransactionRequest;
import com.chatak.pg.bean.BillingData;
import com.chatak.pg.enums.EntryModeEnum;
import com.chatak.pg.enums.TransactionType;
import com.litle.sdk.generate.MethodOfPaymentTypeEnum;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class ClientProcessForSaleApiTest {

	private static final String CLIENT_SSO_LOGIN = "/clientSsoLogin";
	private static final String CLIENT_PROCESS = "/clientProcess";
	private static final String AUTH_HEADER = "Authorization";
	private static final String TOKEN_BEARER = "Bearer ";
	private static final String ERROR_CODE_00 = "00";
	private static final String TXN_0025 = "TXN_0025";
	private static final String TXN_0161 = "TXN_0161";
	private static final String TXN_0163 = "TXN_0163";
	private static final String TXN_0140 = "TXN_0140";
	private static final String TXN_0136 = "TXN_0136";
	private static final String TXN_0017 = "TXN_0017";
	private static final String TXN_0018 = "TXN_0018";
	private static final String TXN_0019 = "TXN_0019";
	private static final String TXN_0020 = "TXN_0020";
	private static final String TXN_0021 = "TXN_0021";
	private static final String TXN_0022 = "TXN_0022";
	private static final String TXN_0179 = "TXN_0179";
	private static final String TXN_0131 = "TXN_0131";
	private static final String TXN_0121 = "TXN_0121";
	private static final String TXN_0150 = "TXN_0150";
	private static final String TXN_0160 = "TXN_0160";
	private static final String TXN_0155 = "TXN_0155";
	private static final String TXN_0151 = "TXN_0151";
	private static final String TXN_0156 = "TXN_0156";
	private static final String TXN_0152 = "TXN_0152";
	private static final String TXN_0157 = "TXN_0157";
	private static final String TXN_0153 = "TXN_0153";
	private static final String TXN_0133 = "TXN_0133";
	private static final String TXN_0129 = "TXN_0129";
	private static final String TXN_0141 = "TXN_0141";
	private static final String TXN_0175 = "TXN_0175";
	private static final String TXN_0142 = "TXN_0142";
	private static final String TXN_0137 = "TXN_0137";
	private static final String TXN_0143 = "TXN_0143";
	private static final String TXN_0138 = "TXN_0138";
	private static final String TXN_0135 = "TXN_0135";
	private static final String TXN_0139 = "TXN_0139";
	private static final String TXN_0159 = "TXN_0159";
	private static final String TXN_0177 = "TXN_0177";
	private static final String TXN_0168 = "TXN_0168";

	private LoginRequest loginRequest;
	private BillingData billingData;
	private CardData cardData;
	private TransactionRequest saleRequest;

	public ClientProcessForSaleApiTest() {

		loginRequest = new LoginRequest();
		loginRequest.setUsername("anoop123");
		loginRequest.setPassword("Ipsidy@123!");
		loginRequest.setCurrentAppVersion("1.3.6");
		loginRequest.setDeviceSerial("352308061370442");

		billingData = new BillingData();
		billingData.setAddress1("banglore");
		billingData.setAddress1("ITPL");
		billingData.setCity("Canada");
		billingData.setState("New York");
		billingData.setCountry("USA");
		billingData.setZipCode("551200");
		billingData.setEmail("shaik.haneef@girmiti.com");

		saleRequest = new TransactionRequest();
		saleRequest.setBillingData(billingData);
		saleRequest.setEntryMode(EntryModeEnum.MANUAL);
		saleRequest.setFeeAmount(0L);
		saleRequest.setInvoiceNumber("2545656892532689");
		saleRequest.setMerchantAmount(20L);
		saleRequest.setMerchantName("Role Test");
		saleRequest.setOrderId("256");
		saleRequest.setRegisterNumber("11225");
		saleRequest.setTotalTxnAmount(20L);
		saleRequest.setUserName("anoop123");

		cardData = new CardData();
		cardData.setCardHolderName("Test");
		cardData.setCardNumber("5652641358617317680");
		cardData.setCvv("675");
		cardData.setEmv("");
		cardData.setExpDate("2001");
		cardData.setCardType(MethodOfPaymentTypeEnum.IP);
		cardData.setTrack2("");
		saleRequest.setCardData(cardData);

		saleRequest.setDeviceSerial("352308061370442");
		saleRequest.setMerchantCode("219141112224310");
		saleRequest.setTerminalId("33705059");
		saleRequest.setTimeZoneOffset("GMT+0530");
		saleRequest.setTimeZoneRegion("Asia/Calcutta");

		saleRequest.setEntryMode(EntryModeEnum.MANUAL);
		saleRequest.setTransactionType(TransactionType.SALE);

	}

	private JSONObject getAccessToken() {
		Response responseBody = (Response) given().contentType(ContentType.JSON).body(loginRequest).when()
				.post(Constant.ROOT_URL + CLIENT_SSO_LOGIN).then().extract().body();
		JSONObject responseJson = new JSONObject(responseBody.asString());
		return responseJson;
	}

	private JSONObject clientProcessResponse(JSONObject responseJson) {
		Response responseBody = given().contentType(ContentType.JSON)
				.header(AUTH_HEADER, TOKEN_BEARER + responseJson.getString("accessToken")).body(saleRequest).when()
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
	public void ClientProcessWithNullBillingAddress() {
		billingData.setAddress1(null);
		saleRequest.setBillingData(billingData);
		JSONObject responseJson = getAccessToken();
		responseJson = clientProcessResponse(responseJson);
		Assert.assertEquals(TXN_0017, responseJson.get("errorCode"));
	}

	@Test
	public void ClientProcessWithNullCountryName() {
		billingData.setCity(null);
		saleRequest.setBillingData(billingData);
		JSONObject responseJson = getAccessToken();
		responseJson = clientProcessResponse(responseJson);
		Assert.assertEquals(TXN_0018, responseJson.get("errorCode"));
	}

	@Test
	public void ClientProcessWithNullCityName() {
		billingData.setCountry(null);
		saleRequest.setBillingData(billingData);
		JSONObject responseJson = getAccessToken();
		responseJson = clientProcessResponse(responseJson);
		Assert.assertEquals(TXN_0019, responseJson.get("errorCode"));
	}

	@Test
	public void ClientProcessWithNullEmail() {
		billingData.setEmail(null);
		saleRequest.setBillingData(billingData);
		JSONObject responseJson = getAccessToken();
		responseJson = clientProcessResponse(responseJson);
		Assert.assertEquals(TXN_0022, responseJson.get("errorCode"));
	}

	@Test
	public void ClientProcessWithNullZipcode() {
		billingData.setZipCode(null);
		saleRequest.setBillingData(billingData);
		JSONObject responseJson = getAccessToken();
		responseJson = clientProcessResponse(responseJson);
		Assert.assertEquals(TXN_0021, responseJson.get("errorCode"));
	}

	@Test
	public void ClientProcessWithNullState() {
		billingData.setState(null);
		saleRequest.setBillingData(billingData);
		JSONObject responseJson = getAccessToken();
		responseJson = clientProcessResponse(responseJson);
		Assert.assertEquals(TXN_0020, responseJson.get("errorCode"));
	}

	@Test
	public void ClientProcessWithZeroTotalTxnAmount() {
		saleRequest.setTotalTxnAmount(0L);
		JSONObject responseJson = getAccessToken();
		responseJson = clientProcessResponse(responseJson);
		Assert.assertEquals(TXN_0179, responseJson.get("errorCode"));
	}

	@Test
	public void ClientProcessWithUserNameWrong() {
		saleRequest.setUserName("anoop");
		JSONObject responseJson = getAccessToken();
		responseJson = clientProcessResponse(responseJson);
		Assert.assertEquals(TXN_0131, responseJson.get("errorCode"));
	}

	@Test
	public void ClientProcessWithUserNameNull() {
		saleRequest.setUserName(null);
		JSONObject responseJson = getAccessToken();
		responseJson = clientProcessResponse(responseJson);
		Assert.assertEquals(TXN_0121, responseJson.get("errorCode"));
	}

	@Test
	public void ClientProcessWithSameInvoiceNumber() {
		saleRequest.setInvoiceNumber("25456568955698");
		JSONObject responseJson = getAccessToken();
		responseJson = clientProcessResponse(responseJson);
		Assert.assertEquals(TXN_0025, responseJson.get("errorCode"));
	}

	@Test
	public void ClientProcessWithNullInvoiceNumber() {
		saleRequest.setInvoiceNumber(null);
		JSONObject responseJson = getAccessToken();
		responseJson = clientProcessResponse(responseJson);
		Assert.assertEquals(TXN_0161, responseJson.get("errorCode"));
	}

	@Test
	public void ClientProcessWithNullOederId() {
		saleRequest.setOrderId(null);
		JSONObject responseJson = getAccessToken();
		responseJson = clientProcessResponse(responseJson);
		Assert.assertEquals(TXN_0163, responseJson.get("errorCode"));
	}

	@Test
	public void ClientProcessWithWrongMarchantCode() {
		saleRequest.setMerchantCode("21914111222431");
		JSONObject responseJson = getAccessToken();
		responseJson = clientProcessResponse(responseJson);
		Assert.assertEquals(TXN_0140, responseJson.get("errorCode"));
	}

	@Test
	public void ClientProcessWithMerchantCodeNull() {
		saleRequest.setMerchantCode(null);
		JSONObject responseJson = getAccessToken();
		responseJson = clientProcessResponse(responseJson);
		Assert.assertEquals(TXN_0136, responseJson.get("errorCode"));
	}

	@Test
	public void ClientProcessWithCardHolderNameNull() {
		cardData.setCardHolderName(null);
		saleRequest.setCardData(cardData);
		JSONObject responseJson = getAccessToken();
		responseJson = clientProcessResponse(responseJson);
		Assert.assertEquals(TXN_0150, responseJson.get("errorCode"));
	}

	@Test
	public void ClientProcessWithWrongCardHolderName() {
		cardData.setCardHolderName("Te");
		saleRequest.setCardData(cardData);
		JSONObject responseJson = getAccessToken();
		responseJson = clientProcessResponse(responseJson);
		Assert.assertEquals(TXN_0160, responseJson.get("errorCode"));
	}

	@Test
	public void ClientProcessWithWrongCardNumber() {
		cardData.setCardNumber("56526413586173180");
		saleRequest.setCardData(cardData);
		JSONObject responseJson = getAccessToken();
		responseJson = clientProcessResponse(responseJson);
		Assert.assertEquals(TXN_0155, responseJson.get("errorCode"));
	}

	@Test
	public void ClientProcessWithCardNumberNull() {
		cardData.setCardNumber(null);
		saleRequest.setCardData(cardData);
		JSONObject responseJson = getAccessToken();
		responseJson = clientProcessResponse(responseJson);
		Assert.assertEquals(TXN_0151, responseJson.get("errorCode"));
	}

	@Test
	public void ClientProcessWithWrongMethodOfPaymentTypeEnum() {
		cardData.setCardType(MethodOfPaymentTypeEnum.AX);
		saleRequest.setCardData(cardData);
		JSONObject responseJson = getAccessToken();
		responseJson = clientProcessResponse(responseJson);
		Assert.assertEquals(TXN_0177, responseJson.get("errorCode"));
	}

	@Test
	public void ClientProcessWithMethodOfPaymentTypeEnumNull() {
		cardData.setCardType(null);
		saleRequest.setCardData(cardData);
		JSONObject responseJson = getAccessToken();
		responseJson = clientProcessResponse(responseJson);
		Assert.assertEquals(TXN_0168, responseJson.get("errorCode"));
	}

	@Test
	public void ClientProcessWithWrongCvv() {
		cardData.setCvv("67");
		saleRequest.setCardData(cardData);
		JSONObject responseJson = getAccessToken();
		responseJson = clientProcessResponse(responseJson);
		Assert.assertEquals(TXN_0156, responseJson.get("errorCode"));
	}

	@Test
	public void ClientProcessWithCvvNull() {
		cardData.setCvv(null);
		saleRequest.setCardData(cardData);
		JSONObject responseJson = getAccessToken();
		responseJson = clientProcessResponse(responseJson);
		Assert.assertEquals(TXN_0152, responseJson.get("errorCode"));
	}

	@Test
	public void ClientProcessWithWrongExpDate() {
		cardData.setExpDate("200");
		saleRequest.setCardData(cardData);
		JSONObject responseJson = getAccessToken();
		responseJson = clientProcessResponse(responseJson);
		Assert.assertEquals(TXN_0157, responseJson.get("errorCode"));
	}

	@Test
	public void ClientProcessWithExpDateNull() {
		cardData.setExpDate(null);
		saleRequest.setCardData(cardData);
		JSONObject responseJson = getAccessToken();
		responseJson = clientProcessResponse(responseJson);
		Assert.assertEquals(TXN_0153, responseJson.get("errorCode"));
	}

	@Test
	public void ClientProcessWithWrongDeviceSerial() {
		saleRequest.setDeviceSerial("352308061370");
		JSONObject responseJson = getAccessToken();
		responseJson = clientProcessResponse(responseJson);
		Assert.assertEquals(TXN_0133, responseJson.get("errorCode"));
	}

	@Test
	public void ClientProcessWithDeviceSerialNull() {
		saleRequest.setDeviceSerial(null);
		JSONObject responseJson = getAccessToken();
		responseJson = clientProcessResponse(responseJson);
		Assert.assertEquals(TXN_0129, responseJson.get("errorCode"));
	}

	@Test
	public void ClientProcessWithWrongTerminalId() {
		saleRequest.setTerminalId("337050");
		JSONObject responseJson = getAccessToken();
		responseJson = clientProcessResponse(responseJson);
		Assert.assertEquals(TXN_0141, responseJson.get("errorCode"));
	}

	@Test
	public void ClientProcessWithTerminalIdNull() {
		saleRequest.setTerminalId(null);
		JSONObject responseJson = getAccessToken();
		responseJson = clientProcessResponse(responseJson);
		Assert.assertEquals(TXN_0175, responseJson.get("errorCode"));
	}

	@Test
	public void ClientProcessWithWrongTimeZoneOffset() {
		saleRequest.setTimeZoneOffset("GMT");
		JSONObject responseJson = getAccessToken();
		responseJson = clientProcessResponse(responseJson);
		Assert.assertEquals(TXN_0142, responseJson.get("errorCode"));
	}

	@Test
	public void ClientProcessWithTimeZoneOffsetNull() {
		saleRequest.setTimeZoneOffset(null);
		JSONObject responseJson = getAccessToken();
		responseJson = clientProcessResponse(responseJson);
		Assert.assertEquals(TXN_0137, responseJson.get("errorCode"));
	}

	@Test
	public void ClientProcessWithWrongTimeZoneRegion() {
		saleRequest.setTimeZoneRegion("USA");
		JSONObject responseJson = getAccessToken();
		responseJson = clientProcessResponse(responseJson);
		Assert.assertEquals(TXN_0143, responseJson.get("errorCode"));
	}

	@Test
	public void ClientProcessWithTimeZoneRegionNull() {
		saleRequest.setTimeZoneRegion(null);
		JSONObject responseJson = getAccessToken();
		responseJson = clientProcessResponse(responseJson);
		Assert.assertEquals(TXN_0138, responseJson.get("errorCode"));
	}

	@Test
	public void ClientProcessWithTransactionTypeNull() {
		saleRequest.setTransactionType(null);
		JSONObject responseJson = getAccessToken();
		responseJson = clientProcessResponse(responseJson);
		Assert.assertEquals(TXN_0135, responseJson.get("errorCode"));
	}

	@Test
	public void ClientProcessWithWrongTransactionType() {
		saleRequest.setTransactionType(TransactionType.AUTH);
		JSONObject responseJson = getAccessToken();
		responseJson = clientProcessResponse(responseJson);
		Assert.assertEquals(TXN_0139, responseJson.get("errorCode"));
	}

	@Test
	public void ClientProcessWithWrongEntryModeEnum() {
		saleRequest.setEntryMode(EntryModeEnum.ACCOUNT_PAY);
		JSONObject responseJson = getAccessToken();
		responseJson = clientProcessResponse(responseJson);
		Assert.assertEquals(TXN_0159, responseJson.get("errorCode"));
	}
}
