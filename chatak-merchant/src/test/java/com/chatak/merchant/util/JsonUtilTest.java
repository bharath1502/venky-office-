package com.chatak.merchant.util;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import com.chatak.merchant.exception.ChatakMerchantException;
import com.chatak.pg.exception.HttpClientException;
import com.chatak.pg.util.Constants;
import com.chatak.pg.util.HttpClient;

@RunWith(MockitoJUnitRunner.class)
public class JsonUtilTest {

	private Logger logger = Logger.getLogger(JsonUtil.class);

	@InjectMocks
	JsonUtil jsonUtil;

	@Mock
	Object object;
	
	HttpClient httpClient;

	@Test(expected = ChatakMerchantException.class)
	public void testConvertObjectToJSON() throws ChatakMerchantException {
		jsonUtil.convertObjectToJSON(object);
	}
	
	@Test
	public void testConvertJSONToObject() throws ChatakMerchantException {
		Class<?> c = String.class;
		jsonUtil.convertJSONToObject("111", c);
	}

	@Test
	public void testSendToIssuance() throws ChatakMerchantException {
		Object request = new Object();
		try {
			Mockito.when(httpClient.invokePost(request, String.class, Constants.ACC_ACTIVE)).thenReturn(Constants.ACC_TERMINATED);
			jsonUtil.sendToIssuance(request, "123", "543", String.class);
		} catch (Exception e) {
			logger.error("ERROR:: JsonUtilTest:: testSendToIssuance method", e);

		}
	}
}
