package com.chatak.merchant.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.context.MessageSource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import com.chatak.merchant.constants.URLMappingConstants;
import com.chatak.merchant.controller.model.LoginResponse;
import com.chatak.merchant.controller.model.Option;
import com.chatak.merchant.exception.ChatakMerchantException;
import com.chatak.merchant.model.GetFraudDetailsResponse;
import com.chatak.merchant.service.FraudService;
import com.chatak.pg.bean.Response;
import com.chatak.pg.model.AdvancedFraudDTO;
import com.chatak.pg.model.FraudBasicDTO;
import com.chatak.pg.util.Constants;
import com.chatak.pg.util.Properties;

@RunWith(MockitoJUnitRunner.class)
public class FraudControllerTest {

	private static Logger logger = Logger.getLogger(FraudController.class);

	@InjectMocks
	FraudController fraudController = new FraudController();

	@Mock
	FraudService fraudService;

	@Mock
	AdvancedFraudDTO advancedFraudDTO = new AdvancedFraudDTO();

	@Mock
	private MessageSource messageSource;

	private MockMvc mockMvc;

	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
		viewResolver.setPrefix("/pages/");
		viewResolver.setSuffix(".jsp");
		mockMvc = MockMvcBuilders.standaloneSetup(fraudController).setViewResolvers(viewResolver).build();
	}

	@Before
	public void pro() {
		java.util.Properties properties = new java.util.Properties();
		properties.setProperty("merchant.services.fraud.basic.feature.id", "abcd");
		properties.setProperty("merchant.services.fraud.advanced.feature.id", "existing");
		Properties.mergeProperties(properties);
	}

	@Test
	public void testShowFraudBasic() throws ChatakMerchantException {
		List<Option> isoCountryList = new ArrayList<>();
		Option option = new Option();
		isoCountryList.add(option);
		GetFraudDetailsResponse fraudDetailsResponse = new GetFraudDetailsResponse();
		fraudDetailsResponse.setErrorCode(Constants.SUCCESS_CODE);
		Mockito.when(fraudService.getISOCountries()).thenReturn(isoCountryList);
		Mockito.when(fraudService.getFraudDetails(Matchers.anyLong())).thenReturn(fraudDetailsResponse);
		try {
			mockMvc.perform(get("/" + URLMappingConstants.CHATAK_MERCHANT_FRAUD_BASIC_PAGE)
					.sessionAttr("existingFeatures", "abcd").sessionAttr("loginUserMerchantId", Long.parseLong("23")))
					.andExpect(view().name(URLMappingConstants.CHATAK_MERCHANT_FRAUD_BASIC_PAGE));
		} catch (Exception e) {
			logger.error("ERROR :: FraudControllerTest :: testShowFraudBasicExistingFeature method", e);

		}
	}

	@Test
	public void testShowFraudBasicExistingFeature() {
		try {
			mockMvc.perform(get("/" + URLMappingConstants.CHATAK_MERCHANT_FRAUD_BASIC_PAGE)
					.sessionAttr("existingFeatures", "existingFeatures")
					.sessionAttr("loginUserMerchantId", "loginUserMerchantId"))
					.andExpect(view().name(URLMappingConstants.INVALID_REQUEST_PAGE));
		} catch (Exception e) {
			logger.error("ERROR :: FraudControllerTest :: testShowFraudBasicExistingFeature method", e);

		}

	}

	@Test
	public void testShowFraudBasicException() {
		Mockito.when(fraudService.getISOCountries()).thenThrow(ChatakMerchantException.class);
		try {
			mockMvc.perform(get("/" + URLMappingConstants.CHATAK_MERCHANT_FRAUD_BASIC_PAGE)
					.sessionAttr("existingFeatures", "abcd").sessionAttr("loginUserMerchantId", Long.parseLong("23")))
					.andExpect(view().name(URLMappingConstants.CHATAK_MERCHANT_FRAUD_BASIC_PAGE));
		} catch (Exception e) {
			logger.error("ERROR :: FraudControllerTest :: testShowFraudBasicException method", e);

		}
	}

	@Test
	public void testCreateFraudBasic() throws ChatakMerchantException {
		Response response2 = new Response();
		GetFraudDetailsResponse fraudDetailsResponse = new GetFraudDetailsResponse();
		response2.setErrorCode(Constants.SUCCESS_CODE);
		fraudDetailsResponse.setErrorCode(Constants.SUCCESS_CODE);
		Mockito.when(fraudService.getFraudDetails(Matchers.anyLong())).thenReturn(fraudDetailsResponse);
		Mockito.when(fraudService.createFraudBasic(Matchers.any(FraudBasicDTO.class))).thenReturn(response2);
		try {
			mockMvc.perform(post("/" + URLMappingConstants.CHATAK_MERCHANT_FRAUD_BASIC_CREATE)
					.sessionAttr("existingFeatures", "abcd").sessionAttr("loginUserMerchantId", Long.parseLong("2423"))
					.header("referer", "referer"))
					.andExpect(view().name(URLMappingConstants.CHATAK_MERCHANT_FRAUD_BASIC_PAGE));
		} catch (Exception e) {
			logger.error("ERROR :: FraudControllerTest :: testCreateFraudBasic method", e);

		}
	}

	@Test
	public void testCreateFraudBasicElse() throws ChatakMerchantException {
		Response response2 = new Response();
		GetFraudDetailsResponse fraudDetailsResponse = new GetFraudDetailsResponse();
		response2.setErrorCode(Constants.ERROR);
		fraudDetailsResponse.setErrorCode(Constants.SUCCESS_CODE);
		Mockito.when(fraudService.getFraudDetails(Matchers.anyLong())).thenReturn(fraudDetailsResponse);
		Mockito.when(fraudService.createFraudBasic(Matchers.any(FraudBasicDTO.class))).thenReturn(response2);
		try {
			mockMvc.perform(post("/" + URLMappingConstants.CHATAK_MERCHANT_FRAUD_BASIC_CREATE)
					.sessionAttr("existingFeatures", "abcd").sessionAttr("loginUserMerchantId", Long.parseLong("2423"))
					.header("referer", "referer"))
					.andExpect(view().name(URLMappingConstants.CHATAK_MERCHANT_FRAUD_BASIC_PAGE));
		} catch (Exception e) {
			logger.error("ERROR :: FraudControllerTest :: testCreateFraudBasicElse method", e);

		}
	}

	@Test
	public void testCreateFraudBasicHeader() {
		try {
			mockMvc.perform(post("/" + URLMappingConstants.CHATAK_MERCHANT_FRAUD_BASIC_CREATE)
					.sessionAttr("existingFeatures", "existingFeatures")
					.sessionAttr("loginUserMerchantId", "loginUserMerchantId"))
					.andExpect(view().name(URLMappingConstants.INVALID_REQUEST_PAGE));
		} catch (Exception e) {
			logger.error("ERROR :: FraudControllerTest :: testCreateFraudBasicHeader method", e);

		}
	}

	@Test
	public void testCreateFraudBasicNotNull() {
		try {
			mockMvc.perform(post("/" + URLMappingConstants.CHATAK_MERCHANT_FRAUD_BASIC_CREATE)
					.sessionAttr("existingFeatures", "existingFeatures")
					.sessionAttr("loginUserMerchantId", "loginUserMerchantId").header("referer", "referer"))
					.andExpect(view().name(URLMappingConstants.INVALID_REQUEST_PAGE));
		} catch (Exception e) {
			logger.error("ERROR :: FraudControllerTest :: testCreateFraudBasicNotNull method", e);

		}
	}

	@Test
	public void testShowAdvancedFraud() throws ChatakMerchantException {
		LoginResponse loginResponse = new LoginResponse();
		List<AdvancedFraudDTO> advancedFraudList = new ArrayList<>();
		advancedFraudDTO = new AdvancedFraudDTO();
		advancedFraudList.add(advancedFraudDTO);
		loginResponse.setUserId(Long.parseLong("23"));
		advancedFraudDTO.setId(Long.parseLong("435"));
		Mockito.when(fraudService.searchAdvancedFraudByCreatedBy(Matchers.any(AdvancedFraudDTO.class)))
				.thenReturn(advancedFraudList);
		try {
			mockMvc.perform(get("/" + URLMappingConstants.CHATAK_MERCHANT_FRAUD_ADVANCED_PAGE)
					.sessionAttr("existingFeatures", "existingFeatures").sessionAttr("loginResponse", loginResponse))
					.andExpect(view().name(URLMappingConstants.CHATAK_MERCHANT_FRAUD_ADVANCED_PAGE));
		} catch (Exception e) {
			logger.error("ERROR :: FraudControllerTest :: testShowAdvancedFraud method", e);

		}
	}

	@Test
	public void testAddNewAdvancedFraudHeader() {
		try {
			mockMvc.perform(post("/" + URLMappingConstants.CHATAK_MERCHANT_ADD_NEW_ADVANCED_FRAUD)
					.sessionAttr("existingFeatures", "existingFeatures").sessionAttr("loginResponse", "bcd"))
					.andExpect(view().name(URLMappingConstants.INVALID_REQUEST_PAGE));
		} catch (Exception e) {
			logger.error("ERROR :: FraudControllerTest :: testAddNewAdvancedFraudHeader method", e);

		}
	}

	@Test
	public void testShowAdvancedFraudException() throws ChatakMerchantException {
		Mockito.when(fraudService.searchAdvancedFraudByCreatedBy(Matchers.any(AdvancedFraudDTO.class)))
				.thenThrow(new NullPointerException());
		try {
			mockMvc.perform(get("/" + URLMappingConstants.CHATAK_MERCHANT_FRAUD_ADVANCED_PAGE)
					.sessionAttr("existingFeatures", "existingFeatures"))
					.andExpect(view().name(URLMappingConstants.CHATAK_MERCHANT_FRAUD_ADVANCED_PAGE));
		} catch (Exception e) {
			logger.error("ERROR :: FraudControllerTest :: testShowAdvancedFraudException method", e);

		}
	}

	@Test
	public void testAddNewAdvancedFraud() throws ChatakMerchantException {
		LoginResponse loginResponse = new LoginResponse();
		List<AdvancedFraudDTO> advancedFraudList = new ArrayList<>();
		advancedFraudDTO = new AdvancedFraudDTO();
		advancedFraudList.add(advancedFraudDTO);
		loginResponse.setUserId(Long.parseLong("23"));
		advancedFraudDTO.setId(Long.parseLong("435"));
		advancedFraudDTO.setErrorCode(Constants.SUCCESS_CODE);
		Mockito.when(fraudService.searchAdvancedFraudByCreatedBy(Matchers.any(AdvancedFraudDTO.class)))
				.thenReturn(advancedFraudList);
		Mockito.when(fraudService.createAdvancedFraud(Matchers.any(AdvancedFraudDTO.class)))
				.thenReturn(advancedFraudDTO);
		try {
			mockMvc.perform(post("/" + URLMappingConstants.CHATAK_MERCHANT_ADD_NEW_ADVANCED_FRAUD)
					.sessionAttr("existingFeatures", "existingFeatures").sessionAttr("loginResponse", loginResponse)
					.header("referer", "referer"))
					.andExpect(view().name(URLMappingConstants.CHATAK_MERCHANT_FRAUD_ADVANCED_PAGE));
		} catch (Exception e) {
			logger.error("ERROR :: FraudControllerTest :: testAddNewAdvancedFraud method", e);

		}
	}

	@Test
	public void testAddNewAdvancedFraudElse() throws ChatakMerchantException {
		LoginResponse loginResponse = new LoginResponse();
		List<AdvancedFraudDTO> advancedFraudList = new ArrayList<>();
		advancedFraudDTO = new AdvancedFraudDTO();
		advancedFraudList.add(advancedFraudDTO);
		loginResponse.setUserId(Long.parseLong("23"));
		advancedFraudDTO.setId(Long.parseLong("435"));
		advancedFraudDTO.setErrorCode(Constants.ERROR);
		Mockito.when(fraudService.searchAdvancedFraudByCreatedBy(Matchers.any(AdvancedFraudDTO.class)))
				.thenReturn(advancedFraudList);
		Mockito.when(fraudService.createAdvancedFraud(Matchers.any(AdvancedFraudDTO.class)))
				.thenReturn(advancedFraudDTO);
		try {
			mockMvc.perform(post("/" + URLMappingConstants.CHATAK_MERCHANT_ADD_NEW_ADVANCED_FRAUD)
					.sessionAttr("existingFeatures", "existingFeatures").sessionAttr("loginResponse", loginResponse)
					.header("referer", "referer"))
					.andExpect(view().name(URLMappingConstants.CHATAK_MERCHANT_FRAUD_ADVANCED_PAGE));
		} catch (Exception e) {
			logger.error("ERROR :: FraudControllerTest :: testAddNewAdvancedFraudElse method", e);

		}
	}

	@Test
	public void testAddNewAdvancedFraudException() {
		LoginResponse loginResponse = new LoginResponse();
		try {
			mockMvc.perform(post("/" + URLMappingConstants.CHATAK_MERCHANT_ADD_NEW_ADVANCED_FRAUD)
					.sessionAttr("existingFeatures", "existingFeatures").sessionAttr("loginResponse", loginResponse)
					.header("referer", "referer"))
					.andExpect(view().name(URLMappingConstants.CHATAK_MERCHANT_FRAUD_ADVANCED_PAGE));
		} catch (Exception e) {
			logger.error("ERROR :: FraudControllerTest :: testAddNewAdvancedFraudException method", e);

		}
	}

	@Test
	public void testShowAdvancedFraudEditPage() throws ChatakMerchantException {
		LoginResponse loginResponse = new LoginResponse();
		advancedFraudDTO = new AdvancedFraudDTO();
		Mockito.when(fraudService.searchAdvancedFraudByIdAndMerchantCode(Matchers.any(AdvancedFraudDTO.class)))
				.thenReturn(advancedFraudDTO);
		try {
			mockMvc.perform(post("/" + URLMappingConstants.CHATAK_MERCHANT_SHOW_ADVANCED_FRAUD_EDIT_PAGE)
					.sessionAttr("existingFeatures", "existingFeatures").sessionAttr("loginResponse", loginResponse)
					.header("referer", "referer"))
					.andExpect(view().name(URLMappingConstants.CHATAK_MERCHANT_ADVANCED_FRAUD_EDIT_PAGE));
		} catch (Exception e) {
			logger.error("ERROR :: FraudControllerTest :: testShowAdvancedFraudEditPage method", e);

		}
	}

	@Test
	public void testShowAdvancedFraudEditPageElse() {
		LoginResponse loginResponse = new LoginResponse();
		try {
			mockMvc.perform(post("/" + URLMappingConstants.CHATAK_MERCHANT_SHOW_ADVANCED_FRAUD_EDIT_PAGE)
					.sessionAttr("existingFeatures", "existingFeatures").sessionAttr("loginResponse", loginResponse)
					.header("referer", "referer"))
					.andExpect(view().name(URLMappingConstants.CHATAK_MERCHANT_FRAUD_ADVANCED_PAGE));
		} catch (Exception e) {
			logger.error("ERROR :: FraudControllerTest :: testShowAdvancedFraudEditPageElse method", e);

		}
	}

	@Test
	public void testShowAdvancedFraudEditPageException() throws ChatakMerchantException {
		LoginResponse loginResponse = new LoginResponse();
		Mockito.when(fraudService.searchAdvancedFraudByIdAndMerchantCode(Matchers.any(AdvancedFraudDTO.class)))
				.thenThrow(new NullPointerException());
		try {
			mockMvc.perform(post("/" + URLMappingConstants.CHATAK_MERCHANT_SHOW_ADVANCED_FRAUD_EDIT_PAGE)
					.sessionAttr("existingFeatures", "existingFeatures").sessionAttr("loginResponse", loginResponse)
					.header("referer", "referer"))
					.andExpect(view().name(URLMappingConstants.CHATAK_MERCHANT_ADVANCED_FRAUD_EDIT_PAGE));
		} catch (Exception e) {
			logger.error("ERROR :: FraudControllerTest :: testShowAdvancedFraudEditPageException method", e);

		}
	}

	@Test
	public void testUpdateRecurringPayment() throws ChatakMerchantException {
		LoginResponse loginResponse = new LoginResponse();
		List<AdvancedFraudDTO> advancedFraudList = new ArrayList<>();
		advancedFraudDTO = new AdvancedFraudDTO();
		advancedFraudList.add(advancedFraudDTO);
		loginResponse.setUserId(Long.parseLong("22"));
		advancedFraudDTO.setId(Long.parseLong("43"));
		Mockito.when(fraudService.searchAdvancedFraudByCreatedBy(Matchers.any(AdvancedFraudDTO.class)))
				.thenReturn(advancedFraudList);
		Mockito.when(fraudService.searchAdvancedFraudByIdAndMerchantCode(Matchers.any(AdvancedFraudDTO.class)))
				.thenReturn(advancedFraudDTO);
		try {
			mockMvc.perform(post("/" + URLMappingConstants.CHATAK_MERCHANT_ADVANCED_FRAUD_UPDATE)
					.sessionAttr("existingFeatures", "existingFeatures").sessionAttr("loginResponse", loginResponse)
					.header("referer", "referer").param("errorCode", "00"))
					.andExpect(view().name(URLMappingConstants.CHATAK_MERCHANT_FRAUD_ADVANCED_PAGE));
		} catch (Exception e) {
			logger.error("ERROR :: FraudControllerTest :: testUpdateRecurringPayment method", e);

		}
	}

	@Test
	public void testUpdateRecurringPaymentElse() throws ChatakMerchantException {
		LoginResponse loginResponse = new LoginResponse();
		List<AdvancedFraudDTO> advancedFraudList = new ArrayList<>();
		advancedFraudDTO = new AdvancedFraudDTO();
		advancedFraudList.add(advancedFraudDTO);
		loginResponse.setUserId(Long.parseLong("22"));
		advancedFraudDTO.setId(Long.parseLong("43"));
		Mockito.when(fraudService.searchAdvancedFraudByCreatedBy(Matchers.any(AdvancedFraudDTO.class)))
				.thenReturn(advancedFraudList);
		Mockito.when(fraudService.searchAdvancedFraudByIdAndMerchantCode(Matchers.any(AdvancedFraudDTO.class)))
				.thenReturn(advancedFraudDTO);
		try {
			mockMvc.perform(post("/" + URLMappingConstants.CHATAK_MERCHANT_ADVANCED_FRAUD_UPDATE)
					.sessionAttr("existingFeatures", "existingFeatures").sessionAttr("loginResponse", loginResponse)
					.header("referer", "referer").param("errorCode", "01"))
					.andExpect(view().name(URLMappingConstants.CHATAK_MERCHANT_FRAUD_ADVANCED_PAGE));
		} catch (Exception e) {
			logger.error("ERROR :: FraudControllerTest :: testUpdateRecurringPaymentElse method", e);

		}

	}

	@Test
	public void testUpdateRecurringPaymentNotNull() throws ChatakMerchantException {
		Mockito.when(fraudService.searchAdvancedFraudByIdAndMerchantCode(Matchers.any(AdvancedFraudDTO.class)))
				.thenReturn(advancedFraudDTO);
		try {
			mockMvc.perform(post("/" + URLMappingConstants.CHATAK_MERCHANT_ADVANCED_FRAUD_UPDATE)
					.sessionAttr("existingFeatures", "123"))
					.andExpect(view().name(URLMappingConstants.INVALID_REQUEST_PAGE));
		} catch (Exception e) {
			logger.error("ERROR :: FraudControllerTest :: testUpdateRecurringPaymentNotNull method", e);

		}
	}

	@Test
	public void testUpdateRecurringPaymentException() throws ChatakMerchantException {
		LoginResponse loginResponse = new LoginResponse();
		Mockito.when(fraudService.searchAdvancedFraudByIdAndMerchantCode(Matchers.any(AdvancedFraudDTO.class)))
				.thenThrow(new NullPointerException());
		try {
			mockMvc.perform(post("/" + URLMappingConstants.CHATAK_MERCHANT_ADVANCED_FRAUD_UPDATE)
					.sessionAttr("existingFeatures", "existingFeatures").sessionAttr("loginResponse", loginResponse)
					.header("referer", "referer"))
					.andExpect(view().name(URLMappingConstants.CHATAK_MERCHANT_FRAUD_ADVANCED_PAGE));
		} catch (Exception e) {
			logger.error("ERROR :: FraudControllerTest :: testUpdateRecurringPaymentException method", e);

		}
	}

	@Test
	public void testDeleteAdvancedFraud() throws ChatakMerchantException {
		LoginResponse loginResponse = new LoginResponse();
		List<AdvancedFraudDTO> advancedFraudList = new ArrayList<>();
		advancedFraudDTO = new AdvancedFraudDTO();
		advancedFraudList.add(advancedFraudDTO);
		loginResponse.setUserId(Long.parseLong("22"));
		Mockito.when(fraudService.searchAdvancedFraudByCreatedBy(Matchers.any(AdvancedFraudDTO.class)))
				.thenReturn(advancedFraudList);
		Mockito.when(fraudService.searchAdvancedFraudByIdAndMerchantCode(Matchers.any(AdvancedFraudDTO.class)))
				.thenReturn(advancedFraudDTO);
		try {
			mockMvc.perform(post("/" + URLMappingConstants.CHATAK_MERCHANT_DELETE_ADVANCED_FRAUD)
					.sessionAttr("existingFeatures", "existingFeatures").sessionAttr("loginResponse", loginResponse)
					.param("getId", "12").header("referer", "referer"))
					.andExpect(view().name(URLMappingConstants.CHATAK_MERCHANT_FRAUD_ADVANCED_PAGE));
		} catch (Exception e) {
			logger.error("ERROR :: FraudControllerTest :: testDeleteAdvancedFraud method", e);

		}
	}

	@Test
	public void testDeleteAdvancedFraudNotNull() throws ChatakMerchantException {
		Mockito.when(fraudService.searchAdvancedFraudByIdAndMerchantCode(Matchers.any(AdvancedFraudDTO.class)))
				.thenReturn(advancedFraudDTO);
		try {
			mockMvc.perform(post("/" + URLMappingConstants.CHATAK_MERCHANT_DELETE_ADVANCED_FRAUD)
					.sessionAttr("existingFeatures", "123"))
					.andExpect(view().name(URLMappingConstants.INVALID_REQUEST_PAGE));
		} catch (Exception e) {
			logger.error("ERROR :: FraudControllerTest :: testDeleteAdvancedFraudNotNull method", e);
		}
	}

	@Test
	public void testDeleteAdvancedFraudException() throws ChatakMerchantException {
		LoginResponse loginResponse = new LoginResponse();
		Mockito.when(fraudService.searchAdvancedFraudByIdAndMerchantCode(Matchers.any(AdvancedFraudDTO.class)))
				.thenThrow(new NullPointerException());
		try {
			mockMvc.perform(post("/" + URLMappingConstants.CHATAK_MERCHANT_DELETE_ADVANCED_FRAUD)
					.sessionAttr("existingFeatures", "existingFeatures").sessionAttr("loginResponse", loginResponse)
					.param("getId", "123").header("referer", "referer"))
					.andExpect(view().name(URLMappingConstants.CHATAK_MERCHANT_FRAUD_ADVANCED_PAGE));
		} catch (Exception e) {
			logger.error("ERROR :: FraudControllerTest :: testDeleteAdvancedFraudException method", e);
		}
	}

}
