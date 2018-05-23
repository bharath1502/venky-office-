package com.chatak.merchant.util;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.chatak.merchant.exception.ChatakMerchantException;
import com.sun.jersey.api.client.ClientHandlerException;

@RunWith(MockitoJUnitRunner.class)
public class JsonUtilTest {

	private Logger logger = Logger.getLogger(JsonUtil.class);

	@InjectMocks
	JsonUtil jsonUtil;

	@Mock
	Object object;

	@Test(expected = ChatakMerchantException.class)
	public void testConvertObjectToJSON() throws ChatakMerchantException {
		jsonUtil.convertObjectToJSON(object);
	}

	@Test
	public void testPostRequest() {
		try {
			jsonUtil.postRequest(object, "serviceEndPoint");
		} catch (Exception e) {
			logger.error("ERROR:: JsonUtil::testPostRequest ", e);

		}
	}

	@Test(expected = ClientHandlerException.class)
	public void testPostRequestString() {
		jsonUtil.postRequest("serviceEndPoint");
	}

	@Test(expected = NullPointerException.class)
	public void testSendToIssuance() {
		jsonUtil.sendToIssuance(object, "serviceEndPoint", "mode");
	}

	@Test
	public void testPostIssuanceRequest() {
		try {
			jsonUtil.postIssuanceRequest(object, "serviceEndPoint");
		} catch (Exception e) {
			logger.error("ERROR:: JsonUtil::testPostIssuanceRequest ", e);

		}
	}

}
