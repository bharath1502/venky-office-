package com.chatak.pay.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.context.MessageSource;

import com.chatak.pay.controller.model.FeeData;
import com.chatak.pay.processor.CardPaymentProcessor;
import com.chatak.pay.processor.TokenizationService;
import com.chatak.pg.acq.dao.model.PGMerchant;

@RunWith(MockitoJUnitRunner.class)
public class FeeControllerTest {

	@InjectMocks
	FeeController feeController = new FeeController();

	@Mock
	private MessageSource messageSource;

	@Mock
	private CardPaymentProcessor cardPaymentProcessor;

	@Mock
	private TokenizationService tokenizationService;

	@Mock
	HttpServletRequest request;

	@Mock
	HttpServletResponse response;

	@Mock
	HttpSession session;

	@Test
	public void testGetFeeDetails() {
		feeController.getFeeDetails(request, response, session,null, Long.parseLong("45"));
	}

	@Test
	public void testGetFeeDetailsTxnAmount() {
		feeController.getFeeDetails(request, response, session, "abcd", Long.parseLong("0"));
	}

	@Test
	public void testGetFeeDetailsElse() {
		FeeData feeData = new FeeData();
		PGMerchant pgMerchant = new PGMerchant();
		feeData.setFeeValue(Long.parseLong("10"));
		Mockito.when(cardPaymentProcessor.validMerchant(Matchers.anyString())).thenReturn(pgMerchant);
		feeController.getFeeDetails(request, response, session, "123", Long.parseLong("10"));
	}

	@Test
	public void testGetFeeDetailsNull() {
		feeController.getFeeDetails(request, response, session, "123", Long.parseLong("10"));
	}

	@Test
	public void testGetFeeDetailsException() {
		Mockito.when(cardPaymentProcessor.validMerchant(Matchers.anyString())).thenThrow(new NullPointerException());
		feeController.getFeeDetails(request, response, session, "123", Long.parseLong("10"));
	}

}
