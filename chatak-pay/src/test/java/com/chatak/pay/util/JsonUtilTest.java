package com.chatak.pay.util;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.context.MessageSource;

import com.chatak.pay.exception.ChatakPayException;
import com.chatak.pg.exception.HttpClientException;
import com.chatak.pg.util.Constants;
import com.chatak.pg.util.HttpClient;
@SuppressWarnings("static-access")
@RunWith(MockitoJUnitRunner.class)
public class JsonUtilTest {
	
	private static Logger logger = Logger.getLogger(JsonUtil.class);

	@InjectMocks
	JsonUtil jsonUtil;
	
	HttpClient httpClient;

	@Mock
	private static MessageSource messageSource;

	private static final String URL="https://www.google.com/";
	
	@Test(expected = ChatakPayException.class)
	public void testConvertObjectToJSON() throws ChatakPayException {
		Object object = new Object();
		jsonUtil.convertObjectToJSON(object);
	}

	
	@Test
	public void testConvertJSONToObject() throws ChatakPayException {
		Class<?> c = String.class;
		jsonUtil.convertJSONToObject("111", c);
	}


	@Test
	public void testSendToIssuance() throws ChatakPayException {
		Object request = new Object();
		try {
			Mockito.when(httpClient.invokePost(request, String.class, Constants.ACC_ACTIVE)).thenReturn(Constants.ACC_TERMINATED);
			jsonUtil.sendToTSM(String.class, request, URL);
		} catch (Exception e) {
			logger.error("ERROR:: JsonUtilTest:: testSendToIssuance method", e);

		}
	}

}
