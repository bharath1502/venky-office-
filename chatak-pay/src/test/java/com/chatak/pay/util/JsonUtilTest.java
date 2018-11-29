package com.chatak.pay.util;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.context.MessageSource;

import com.chatak.pay.exception.ChatakPayException;
import com.chatak.pg.exception.HttpClientException;

@RunWith(MockitoJUnitRunner.class)
public class JsonUtilTest {

	@InjectMocks
	JsonUtil jsonUtil;

	@Mock
	private static MessageSource messageSource;

	private static final String URL="https://www.google.com/";
	
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

	@Test
	public void testPostRequest() throws ChatakPayException, HttpClientException {
		Object request = new Object();
		jsonUtil.postRequest(String.class, request, URL);
	}

	@Test(expected = NullPointerException.class)
	public void testSendToIssuanceObject() throws ChatakPayException, HttpClientException {
		Object request = new Object();
		jsonUtil.sendToIssuance(String.class, request, URL);
	}

	@Test
	public void testSendToTSM() throws ChatakPayException, HttpClientException {
		Object request = new Object();
		jsonUtil.sendToTSM(String.class, request, URL);
	}

}
