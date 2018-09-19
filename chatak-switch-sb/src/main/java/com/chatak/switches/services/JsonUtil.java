package com.chatak.switches.services;

import org.apache.http.Header;
import org.apache.http.entity.ContentType;
import org.apache.http.message.BasicHeader;
import org.apache.log4j.Logger;
import org.springframework.context.NoSuchMessageException;

import com.chatak.pg.exception.HttpClientException;
import com.chatak.pg.util.HttpClient;
import com.chatak.pg.util.Properties;

public class JsonUtil {

	private static final Logger logger = Logger.getLogger(JsonUtil.class);
	
	private JsonUtil() {
	    super();
	}
	
	public static <T extends Object> T sendToProcessor(Class<T> className, Object request, String serviceEndPoint) throws  HttpClientException {
		T resultantObject = null;
		String tsmURL = Properties.getProperty("processor.rest.service.url");
		HttpClient httpClient = new HttpClient(tsmURL , serviceEndPoint);
	    try {
	    	Header[] headers = new Header[] { new BasicHeader("content-type", ContentType.APPLICATION_JSON.getMimeType()),
	    			new BasicHeader("consumerClientId", Properties.getProperty("chatak-issuance.consumer.client.id")),
	    			new BasicHeader("consumerSecret", Properties.getProperty("chatak-issuance.consumer.client.secret"))};
	      resultantObject = httpClient.invokePost(request, className, headers, false);
	     
	    } catch (HttpClientException hce) {
	        logger.error("ERROR: JsonUtil :: sendToProcessor method" + hce.getHttpErrorCode() + hce.getMessage(), hce);
	    	throw hce;
	    }catch (Exception e) {
	    	 logger.error("Error:: sendToProcessor:: " + e);
	         throw new NoSuchMessageException("Unable to connect");
	    }
	    return resultantObject;
	}
}
