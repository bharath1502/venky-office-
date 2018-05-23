package com.chatak.switches.sb.util;

import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectWriter;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import com.chatak.switches.sb.exception.ServiceException;
import com.sun.jersey.api.client.ClientHandlerException;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.WebResource.Builder;

@RunWith(MockitoJUnitRunner.class)
public class JsonUtilTest {

	private static Logger logger = Logger.getLogger(JsonUtil.class);

	@InjectMocks
	JsonUtil jsonUtil;

	@Mock
	ObjectWriter objectWriter;

	@Mock
	WebResource webResource;

	@Mock
	Builder builder;

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

	@Test(expected = ServiceException.class)
	public void testPostRequest() throws ServiceException {
		Object object = new Object();
		jsonUtil.postRequest(null, object, "123");
	}

	@Test(expected = ServiceException.class)
	public void testPostRequestElse() throws ServiceException {
		Object object = null;
		jsonUtil.postRequest(null, object, "123");
	}

	@Test(expected = ClientHandlerException.class)
	public void testPostRequestString() throws ServiceException {
		jsonUtil.postRequest("123");
	}

	@Test(expected = ClientHandlerException.class)
	public void testPostRequestLogin() throws ServiceException {
		jsonUtil.postRequestLogin("123");
	}

	@Test
	public void testSendToIssuance() {
		Object request = new Object();
		Mockito.when(webResource.header(Matchers.anyString(), Matchers.any(Object.class))).thenReturn(builder);
		try {
			jsonUtil.sendToIssuance(request, "123", "543");
		} catch (Exception e) {
			logger.error("ERROR:: JsonUtilTest:: testSendToIssuance method", e);

		}
	}

}
