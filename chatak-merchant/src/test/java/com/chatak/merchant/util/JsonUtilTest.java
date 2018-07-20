package com.chatak.merchant.util;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.chatak.merchant.exception.ChatakMerchantException;
import com.chatak.pg.exception.HttpClientException;

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
			jsonUtil.postRequest(object, "serviceEndPoint",String.class);
		} catch (Exception e) {
			logger.error("ERROR:: JsonUtil::testPostRequest ", e);

		}
	}

	@Test
	public void testPostRequestString() {
		try {
		jsonUtil.postRequest("serviceEndPoint",String.class);
		} catch (Exception e) {
				logger.error("ERROR:: JsonUtil::testPostRequestString ", e);

			}
	}

	@Test
	public void testSendToIssuance() {
		try {
		jsonUtil.sendToIssuance(object, "serviceEndPoint", "mode", String.class);
		} catch (Exception e) {
			logger.error("ERROR:: JsonUtil::testSendToIssuance ", e);

		}
	}

	@Test
	public void testPostIssuanceRequest() {
		try {
			jsonUtil.postIssuanceRequest(object, "serviceEndPoint", String.class);
		} catch (Exception e) {
			logger.error("ERROR:: JsonUtil::testPostIssuanceRequest ", e);

		}
	}

}
