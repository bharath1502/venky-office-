package com.chatak.acquirer.admin.util;

import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectWriter;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.chatak.acquirer.admin.exception.ChatakAdminException;
import com.sun.jersey.api.client.ClientHandlerException;

@RunWith(MockitoJUnitRunner.class)
public class JsonUtilTest {

	private static Logger logger = Logger.getLogger(JsonUtil.class);

	@InjectMocks
	JsonUtil jsonUtil;

	@Mock
	ObjectWriter objectWriter;

	@Test(expected = ChatakAdminException.class)
	public void testConvertObjectToJSON() throws ChatakAdminException {
		Object object = new Object();
		jsonUtil.convertObjectToJSON(object);
	}

	@Test(expected = NullPointerException.class)
	public void testConvertJSONToObject() throws ChatakAdminException {
		Class<?> c = null;
		jsonUtil.convertJSONToObject("jsonData", c);
	}

	@Test
	public void testPostRequest() {
		Object request = new Object();
		try {
			jsonUtil.postRequest(request, "serviceEndPoint");
		} catch (Exception e) {
			logger.error("JsonUtilTest | testPostRequest | Exception ", e);

		}
	}

	@Test(expected = ClientHandlerException.class)
	public void testPostRequestString() {
		jsonUtil.postRequest("serviceEndPoint");
	}

	@Test(expected = NullPointerException.class)
	public void testSendToIssuance() throws ChatakAdminException {
		Object request = new Object();
		jsonUtil.sendToIssuance(request, "serviceEndPoint", "m");
	}

	@Test
	public void testPostDCCRequest() {
		Object request = new Object();
		try {
			jsonUtil.postDCCRequest(request, "serviceEndPoint");
		} catch (Exception e) {
			logger.error("JsonUtilTest | testPostDCCRequest | Exception ", e);

		}
	}

	@Test(expected = ClientHandlerException.class)
	public void testPostDCCRequestString() {
		jsonUtil.postDCCRequest("5435");
	}

	@Test(expected = ClientHandlerException.class)
	public void testGetRequest() {
		jsonUtil.getRequest("111");
	}

	@Test
	public void testPostIssuanceRequest() {
		Object request = new Object();
		try {
			jsonUtil.postIssuanceRequest(request, "435435");
		} catch (Exception e) {
			logger.error("JsonUtilTest | testPostIssuanceRequest | Exception ", e);

		}
	}

}
