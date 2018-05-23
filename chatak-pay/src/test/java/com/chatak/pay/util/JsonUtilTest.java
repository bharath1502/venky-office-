package com.chatak.pay.util;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.context.MessageSource;

import com.chatak.pay.exception.ChatakPayException;
import com.sun.jersey.api.client.ClientHandlerException;

@RunWith(MockitoJUnitRunner.class)
public class JsonUtilTest {

	@InjectMocks
	JsonUtil jsonUtil;

	@Mock
	private static MessageSource messageSource;

	@Test(expected = ChatakPayException.class)
	public void testConvertObjectToJSON() throws ChatakPayException {
		Object object = new Object();
		jsonUtil.convertObjectToJSON(object);
	}

	@Test(expected = NullPointerException.class)
	public void testConvertJSONToObject() throws ChatakPayException {
		Class<?> c = null;
		jsonUtil.convertJSONToObject("abc", c);
	}

	@Test(expected = ChatakPayException.class)
	public void testPostRequest() throws ChatakPayException {
		Object request = new Object();
		Class<?> responseClass = null;
		jsonUtil.postRequest(responseClass, request, "243");
	}

	@Test(expected = ClientHandlerException.class)
	public void testPostRequestString() throws ChatakPayException {
		jsonUtil.postRequest("243");
	}

	@Test(expected = ClientHandlerException.class)
	public void testPostRequestLogin() throws ChatakPayException {
		jsonUtil.postRequestLogin("243");
	}

	@Test(expected = NullPointerException.class)
	public void testSendToIssuance() throws ChatakPayException {
		Object request = new Object();
		jsonUtil.sendToIssuance(request, "243");
	}

	@Test(expected = NullPointerException.class)
	public void testSendToIssuanceObject() throws ChatakPayException {
		Object request = new Object();
		Class<?> responseClass = null;
		jsonUtil.sendToIssuance(responseClass, request, "243");
	}

	@Test(expected = NullPointerException.class)
	public void testSendToTSM() throws ChatakPayException {
		Object request = new Object();
		Class<?> responseClass = null;
		jsonUtil.sendToTSM(responseClass, request, "243");
	}

}
