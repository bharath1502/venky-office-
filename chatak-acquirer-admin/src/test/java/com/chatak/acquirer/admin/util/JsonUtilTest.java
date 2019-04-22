package com.chatak.acquirer.admin.util;



import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectWriter;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import com.chatak.acquirer.admin.exception.ChatakAdminException;
import com.chatak.pg.exception.HttpClientException;
import com.chatak.pg.util.Constants;
import com.chatak.pg.util.HttpClient;

@SuppressWarnings("static-access")
@RunWith(MockitoJUnitRunner.class)
public class JsonUtilTest {

	private static Logger logger = Logger.getLogger(JsonUtil.class);

	@InjectMocks
	JsonUtil jsonUtil;

	ObjectWriter objectWriter;

	HttpClient httpClient;

	String output;

	@Test(expected = ChatakAdminException.class)
	public void testConvertObjectToJSON() throws ChatakAdminException {
		Object object = new Object();
		jsonUtil.convertObjectToJSON(object);
	}

	
	@Test
	public void testConvertJSONToObject() throws ChatakAdminException {
		Class<?> c = String.class;
		jsonUtil.convertJSONToObject("111", c);
	}

	@Test
	public void testSendToIssuance() throws ChatakAdminException {
		Object request = new Object();
		try {
			Mockito.when(httpClient.invokePost(request, String.class, Constants.ACC_ACTIVE)).thenReturn(Constants.ACC_TERMINATED);
			jsonUtil.sendToIssuance(request, "123", "543", String.class);
		} catch (Exception e) {
			logger.error("ERROR:: JsonUtilTest:: testSendToIssuance method", e);

		}
	}

	
}
