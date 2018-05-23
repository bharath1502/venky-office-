package com.chatak.merchant.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import com.chatak.pg.util.Properties;

public class PayControllerTest {

	private Logger logger = Logger.getLogger(PayController.class);

	@InjectMocks
	PayController payController = new PayController();

	private MockMvc mockMvc;

	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
		viewResolver.setPrefix("/pages/");
		viewResolver.setSuffix(".jsp");
		mockMvc = MockMvcBuilders.standaloneSetup(payController).setViewResolvers(viewResolver).build();
	}

	@Before
	public void pro() {
		java.util.Properties properties = new java.util.Properties();
		properties.setProperty("merchant.service.dashboard.feature.id", "existing");
		Properties.mergeProperties(properties);
	}

	@Test
	public void testShowPaymentProcessing() {
		try {
			mockMvc.perform(post("/" + "show").sessionAttr("existingFeatures", "existingFeatures"));
		} catch (Exception e) {
			logger.error("ERROR :: PayControllerTest :: testShowPaymentProcessing method", e);

		}
	}

}
