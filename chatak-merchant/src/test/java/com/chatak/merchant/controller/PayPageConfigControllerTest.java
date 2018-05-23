package com.chatak.merchant.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.Locale;

import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.context.MessageSource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import com.chatak.merchant.constants.URLMappingConstants;
import com.chatak.merchant.exception.ChatakMerchantException;
import com.chatak.merchant.model.PayPageConfig;
import com.chatak.merchant.model.PayPageConfigResponse;
import com.chatak.merchant.service.PayPageConfigService;
import com.chatak.pg.model.Response;
import com.chatak.pg.util.Constants;
import com.chatak.pg.util.Properties;

public class PayPageConfigControllerTest {

	private Logger logger = Logger.getLogger(PayPageConfigController.class);

	@InjectMocks
	PayPageConfigController payPageConfigController = new PayPageConfigController();

	@Mock
	PayPageConfigService payPageConfigService;

	@Mock
	private MessageSource messageSource;

	private MockMvc mockMvc;

	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
		viewResolver.setPrefix("/pages/");
		viewResolver.setSuffix(".jsp");
		mockMvc = MockMvcBuilders.standaloneSetup(payPageConfigController).setViewResolvers(viewResolver).build();
	}

	@Before
	public void pro() {
		java.util.Properties properties = new java.util.Properties();
		properties.setProperty("merchant.services.reports.merchant.transaction.revenue.feature.id", "abcd");
		Properties.mergeProperties(properties);
	}

	@Test
	public void testShowPayPageConfig() throws ChatakMerchantException {
		PayPageConfigResponse payPageConfigResponse = new PayPageConfigResponse();
		byte[] bs = { Constants.TWO_INT, Constants.THREE_INT, Constants.FOUR_INT };
		payPageConfigResponse.setErrorMessage(Constants.SUCESS);
		payPageConfigResponse.setPayPageLogo(bs);
		Mockito.when(payPageConfigService.getPayPageConfigDetails(Matchers.anyLong()))
				.thenReturn(payPageConfigResponse);
		try {
			mockMvc.perform(get("/" + URLMappingConstants.CHATAK_MERCHANT_PAY_PAGE_CONFIG)
					.sessionAttr("loginUserMerchantId", Long.parseLong("21432")))
					.andExpect(view().name(URLMappingConstants.CHATAK_MERCHANT_PAY_PAGE_CONFIG));
		} catch (Exception e) {
			logger.error("Error:: PayPageConfigControllerTest:: testShowPayPageConfig method" + e);

		}
	}

	@Test
	public void testShowPayPageConfigElse()  {
		try {
			mockMvc.perform(get("/" + URLMappingConstants.CHATAK_MERCHANT_PAY_PAGE_CONFIG))
					.andExpect(view().name(URLMappingConstants.CHATAK_MERCHANT_PAY_PAGE_CONFIG));
		} catch (Exception e) {
			logger.error("Error:: PayPageConfigControllerTest:: testShowPayPageConfigElse method" + e);

		}
	}

	@Test
	public void testShowPayPageConfigChatakMerchantException() throws ChatakMerchantException  {
		Mockito.when(payPageConfigService.getPayPageConfigDetails(Matchers.anyLong()))
				.thenThrow(ChatakMerchantException.class);
		try {
			mockMvc.perform(get("/" + URLMappingConstants.CHATAK_MERCHANT_PAY_PAGE_CONFIG)
					.sessionAttr("loginUserMerchantId", Long.parseLong("21432")))
					.andExpect(view().name(URLMappingConstants.CHATAK_MERCHANT_PAY_PAGE_CONFIG));
		} catch (Exception e) {
			logger.error("Error:: PayPageConfigControllerTest:: testShowPayPageConfigChatakMerchantException method" + e);

		}
	}

	@Test
	public void testSaveOrUpdatePayPageConfigHeader()  {
		try {
			mockMvc.perform(post("/" + URLMappingConstants.CHATAK_MERCHANT_PAY_PAGE_CONFIG)
					.sessionAttr("loginUserMerchantId", Long.parseLong("21432")).param("payPageLogo", "payPageLogo"))
					.andExpect(view().name(URLMappingConstants.INVALID_REQUEST_PAGE));
		} catch (Exception e) {
			logger.error("Error:: PayPageConfigControllerTest:: testSaveOrUpdatePayPageConfigHeader method" + e);

		}
	}

	@Test
	public void testSaveOrUpdatePayPageConfig() throws ChatakMerchantException {
		Response response2 = new Response();
		response2.setErrorCode(Constants.SUCCESS_CODE);
		Mockito.when(payPageConfigService.saveOrUpdatePayPageConfig(Matchers.any(PayPageConfig.class)))
				.thenReturn(response2);
		Mockito.when(messageSource.getMessage(Matchers.anyString(), Matchers.any(Object[].class),
				Matchers.any(Locale.class))).thenReturn("hello");
		try {
			mockMvc.perform(post("/" + URLMappingConstants.CHATAK_MERCHANT_PAY_PAGE_CONFIG)
					.sessionAttr("loginUserMerchantId", Long.parseLong("21432")).param("payPageLogo", "null").header(Constants.REFERER, Constants.REFERER))
					.andExpect(view().name(URLMappingConstants.CHATAK_MERCHANT_PAY_PAGE_CONFIG));
		} catch (Exception e) {
			logger.error("Error:: PayPageConfigControllerTest:: testSaveOrUpdatePayPageConfig method" + e);

		}
	}

	@Test
	public void testSaveOrUpdatePayPageConfigResponseElse() {
		try {
			mockMvc.perform(post("/" + URLMappingConstants.CHATAK_MERCHANT_PAY_PAGE_CONFIG)
					.sessionAttr("loginUserMerchantId", Long.parseLong("21432")).param("payPageLogo", "payPageLogo")
					.header(Constants.REFERER, Constants.REFERER))
					.andExpect(view().name(URLMappingConstants.CHATAK_MERCHANT_PAY_PAGE_CONFIG));
		} catch (Exception e) {
			logger.error("Error:: PayPageConfigControllerTest:: testSaveOrUpdatePayPageConfigResponseElse method" + e);

		}
	}

	@Test
	public void testSaveOrUpdatePayPageConfigElse()  {
		try {
			mockMvc.perform(post("/" + URLMappingConstants.CHATAK_MERCHANT_PAY_PAGE_CONFIG)
					.param("payPageLogo", "payPageLogo").header(Constants.REFERER, Constants.REFERER))
					.andExpect(view().name(URLMappingConstants.CHATAK_MERCHANT_PAY_PAGE_CONFIG));
		} catch (Exception e) {
			logger.error("Error:: PayPageConfigControllerTest:: testSaveOrUpdatePayPageConfigElse method" + e);

		}
	}

	@Test
	public void testSaveOrUpdatePayPageConfigChatakMerchantException() throws ChatakMerchantException  {
		Response response2 = new Response();
		response2.setErrorCode(Constants.SUCCESS_CODE);
		Mockito.when(payPageConfigService.saveOrUpdatePayPageConfig(Matchers.any(PayPageConfig.class)))
				.thenThrow(ChatakMerchantException.class);
		try {
			mockMvc.perform(post("/" + URLMappingConstants.CHATAK_MERCHANT_PAY_PAGE_CONFIG)
					.sessionAttr("loginUserMerchantId", Long.parseLong("21432")).param("payPageLogo", "payPageLogo")
					.header(Constants.REFERER, Constants.REFERER))
					.andExpect(view().name(URLMappingConstants.CHATAK_MERCHANT_PAY_PAGE_CONFIG));
		} catch (Exception e) {
			logger.error("Error:: PayPageConfigControllerTest:: testSaveOrUpdatePayPageConfigChatakMerchantException method" + e);

		}
	}

}
