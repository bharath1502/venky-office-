package com.chatak.pg.util;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collections;
import java.util.List;

import org.apache.http.HttpHost;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.conn.routing.HttpRoute;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.log4j.Logger;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;

public class HttpConfig {

  private static Logger logger = Logger.getLogger(HttpConfig.class);
  
	private static final int DEFAULT_MAX_TOTAL_CONNECTIONS = Integer.parseInt(Properties.getProperty("thread.pool.size"));

	private static final int DEFAULT_MAX_CONNECTIONS_PER_ROUTE = Integer.parseInt(Properties.getProperty("thread.max.per.route"));

	private static final int DEFAULT_READ_TIMEOUT_MILLISECONDS = (120 * 1000);
	
	private static HttpConfig httpConfig = new HttpConfig();
	
	private RestTemplate restTemplate = null;
	
	private HttpEntity<String> headerEntity = null;
	
	private HttpConfig() {
		
	}
	
	public static HttpConfig getInstance() {
		return httpConfig;
	}
	
	public RestTemplate getRestTemplate() {
		if(restTemplate == null) {
			restTemplate = restTemplate();
		}
		return restTemplate;
	}
	
	public HttpEntity<String> getHeadersEntity() {
		if(null == headerEntity) {
			HttpHeaders headers = new HttpHeaders();
			headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
			headers.setContentType(MediaType.APPLICATION_JSON);
			headerEntity = new HttpEntity<String>("parameters", headers);
		}
		return headerEntity;
	}

	private ClientHttpRequestFactory httpRequestFactory() {
		return new HttpComponentsClientHttpRequestFactory(httpClient());
	}

	private RestTemplate restTemplate() {
		RestTemplate restTemplate = new RestTemplate(httpRequestFactory());
		List<HttpMessageConverter<?>> converters = restTemplate.getMessageConverters();
		for (HttpMessageConverter<?> converter : converters) {
			if (converter instanceof MappingJackson2HttpMessageConverter) {
				MappingJackson2HttpMessageConverter jsonConverter = (MappingJackson2HttpMessageConverter) converter;
				jsonConverter.setObjectMapper(new ObjectMapper());
			}
		}
		return restTemplate;
	}

	private CloseableHttpClient httpClient() {
	  CloseableHttpClient defaultHttpClient = null;
	  PoolingHttpClientConnectionManager connectionManager = null;

    try {
      connectionManager = new PoolingHttpClientConnectionManager();
      connectionManager.setMaxTotal(DEFAULT_MAX_TOTAL_CONNECTIONS);
      connectionManager.setDefaultMaxPerRoute(DEFAULT_MAX_CONNECTIONS_PER_ROUTE);
      URI uri = null;
      HttpHost host = null;
      if(!StringUtils.isNullAndEmpty(Properties.getProperty("chatak-dcc.service.url"))) {
        uri = new URI(Properties.getProperty("chatak-dcc.service.url"));
        host = (uri.getPort() > 0) ? new HttpHost(uri.getHost(), uri.getPort()) : new HttpHost(uri.getHost());

        connectionManager.setMaxPerRoute(new HttpRoute(host), DEFAULT_MAX_CONNECTIONS_PER_ROUTE);
      }

      if(!StringUtils.isNullAndEmpty(Properties.getProperty("chatak-merchant.service.url"))) {
        uri = new URI(Properties.getProperty("chatak-merchant.service.url"));
        host = (uri.getPort() > 0) ? new HttpHost(uri.getHost(), uri.getPort()) : new HttpHost(uri.getHost());
        connectionManager.setMaxPerRoute(new HttpRoute(host), DEFAULT_MAX_CONNECTIONS_PER_ROUTE);
      }

      if(!StringUtils.isNullAndEmpty(Properties.getProperty("prepaid.service.url"))) {
        uri = new URI(Properties.getProperty("prepaid.service.url"));
        host = (uri.getPort() > 0) ? new HttpHost(uri.getHost(), uri.getPort()) : new HttpHost(uri.getHost());
        connectionManager.setMaxPerRoute(new HttpRoute(host), DEFAULT_MAX_CONNECTIONS_PER_ROUTE);
      }
      if(!StringUtils.isNullAndEmpty(Properties.getProperty("chatak-pay.service.url"))) {
        uri = new URI(Properties.getProperty("chatak-pay.service.url"));
        host = (uri.getPort() > 0) ? new HttpHost(uri.getHost(), uri.getPort()) : new HttpHost(uri.getHost());
        connectionManager.setMaxPerRoute(new HttpRoute(host), DEFAULT_MAX_CONNECTIONS_PER_ROUTE);
      }
      if(!StringUtils.isNullAndEmpty(Properties.getProperty("chatak-issuance.service.url"))) {
        uri = new URI(Properties.getProperty("chatak-issuance.service.url"));
        host = (uri.getPort() > 0) ? new HttpHost(uri.getHost(), uri.getPort()) : new HttpHost(uri.getHost());
        connectionManager.setMaxPerRoute(new HttpRoute(host), DEFAULT_MAX_CONNECTIONS_PER_ROUTE);
      }
      if(!StringUtils.isNullAndEmpty(Properties.getProperty("chatak-tms.base.service.url"))) {
        uri = new URI(Properties.getProperty("chatak-tms.base.service.url"));
        host = (uri.getPort() > 0) ? new HttpHost(uri.getHost(), uri.getPort()) : new HttpHost(uri.getHost());
        connectionManager.setMaxPerRoute(new HttpRoute(host), DEFAULT_MAX_CONNECTIONS_PER_ROUTE);
      }
      if(!StringUtils.isNullAndEmpty(Properties.getProperty("chatak-tms.rest.service.url"))) {
        uri = new URI(Properties.getProperty("chatak-tms.rest.service.url"));
        host = (uri.getPort() > 0) ? new HttpHost(uri.getHost(), uri.getPort()) : new HttpHost(uri.getHost());
        connectionManager.setMaxPerRoute(new HttpRoute(host), DEFAULT_MAX_CONNECTIONS_PER_ROUTE);
      }
      RequestConfig config = RequestConfig.custom().setConnectTimeout(DEFAULT_READ_TIMEOUT_MILLISECONDS).build();
      defaultHttpClient = HttpClientBuilder.create().setConnectionManager(connectionManager)
          .setDefaultRequestConfig(config).build();
    }
    catch(NumberFormatException e) {
      logger.error("ERROR:: HttpConfig :: NumberFormatException Exception", e);
    }
    catch(URISyntaxException e) {
      logger.error("ERROR:: HttpConfig :: URISyntaxException Exception", e);
    }
		
		return defaultHttpClient;
	}

}
