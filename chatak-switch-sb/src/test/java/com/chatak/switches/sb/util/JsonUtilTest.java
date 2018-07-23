package com.chatak.switches.sb.util;

import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectWriter;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import com.chatak.pg.exception.PrepaidAdminException;
import com.chatak.pg.util.Constants;
import com.chatak.pg.util.HttpClient;
import com.chatak.switches.sb.exception.ServiceException;

@SuppressWarnings("static-access")
@RunWith(MockitoJUnitRunner.class)
public class JsonUtilTest {

	private static Logger logger = Logger.getLogger(JsonUtil.class);

	@InjectMocks
	JsonUtil jsonUtil;

	ObjectWriter objectWriter;

	HttpClient httpClient;

	String output;

	@Test(expected = ServiceException.class)
	public void testConvertObjectToJSON() throws ServiceException {
		Object object = new Object();
		jsonUtil.convertObjectToJSON(object);
	}

	@Test
	public void testConvertObjectToJSONElse() throws ServiceException {
		Object object = null;
		jsonUtil.convertObjectToJSON(object);
	}

	@Test(expected = NullPointerException.class)
	public void testConvertJSONToObject() throws ServiceException {
		Class<?> c = null;
		jsonUtil.convertJSONToObject("111", c);
	}

	@Test
	public void testSendToIssuance() throws PrepaidAdminException {
		Object request = new Object();
		try {
			Mockito.when(httpClient.invokePost(request, String.class, Constants.ACC_ACTIVE)).thenReturn(Constants.ACC_TERMINATED);

			jsonUtil.sendToIssuance(request, "123", "543", String.class);
		} catch (Exception e) {
			logger.error("ERROR:: JsonUtilTest:: testSendToIssuance method", e);

		}
	}
}
