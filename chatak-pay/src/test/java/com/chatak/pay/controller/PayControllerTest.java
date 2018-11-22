package com.chatak.pay.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import com.chatak.pay.constants.Constant;
import com.chatak.pay.constants.URLMappingConstants;
import com.chatak.pay.controller.model.PaymentDetails;
import com.chatak.pay.processor.CardPaymentProcessor;
import com.chatak.pay.processor.TokenizationService;
import com.chatak.pg.acq.dao.PGParamsDao;

@RunWith(MockitoJUnitRunner.class)
public class PayControllerTest {

	private static Logger logger = LogManager.getLogger(PayController.class.getName());

	@InjectMocks
	PayController payController = new PayController();

	@Mock
	private CardPaymentProcessor cardPaymentProcessor;

	@Mock
	private DefaultTokenServices tokenServices;

	@Mock
	private TokenizationService tokenizationService;

	@Mock
	private PGParamsDao paramsDao;

	@Mock
	HttpServletRequest request;

	@Mock
	HttpServletResponse response;

	@Mock
	HttpSession session;

	private MockMvc mockMvc;

	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
		viewResolver.setPrefix("/pages/");
		viewResolver.setSuffix(".jsp");
		mockMvc = MockMvcBuilders.standaloneSetup(payController).setViewResolvers(viewResolver).build();
	}

	@Test
	public void testShowPaymentProcessingElse() {
		payController.showPaymentProcessing(request, response, session, "543");
	}

	@Test
	public void testShowPayment() {
		payController.showPayment(request, response, session);

	}

	@Test
	public void testError() {
		payController.error(request, response, session);

	}

	@Test
	public void testInvalidAccess() {
		try {
			mockMvc.perform(get("/" + URLMappingConstants.CHATAK_PAY_INVALID_ACCESS_PAGE))
					.andExpect(view().name(Constant.INVALID_ACCESS));
		} catch (Exception e) {
			logger.error("ERROR :: PayControllerTest:: testInvalidAccess method", e);
		}

	}

	@Test
	public void testSessionExpired() {
		PaymentDetails paymentDetails = new PaymentDetails();
		try {
			mockMvc.perform(get("/" + URLMappingConstants.CHATAK_PAY_SESSION_EXPIRED_PAGE)
					.sessionAttr(Constant.CHATAK_PAY_SESSION, paymentDetails))
					.andExpect(view().name(Constant.UNKNOWN_ERROR));
		} catch (Exception e) {
			logger.error("ERROR :: PayControllerTest:: testSessionExpired method", e);
		}
	}

	@Test
	public void testEncrypt() {
		payController.encrypt(request, response, session);

	}

	@Test
	public void testShowPaymentInfo() {
		payController.showPaymentInfo(request, response, session);

	}

}
