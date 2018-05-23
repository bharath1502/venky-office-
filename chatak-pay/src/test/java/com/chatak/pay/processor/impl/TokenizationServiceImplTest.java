package com.chatak.pay.processor.impl;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import com.chatak.pay.controller.model.CardDetails;
import com.chatak.pay.exception.ChatakPayException;
import com.chatak.pay.model.TokenizeResponse;
import com.chatak.pg.acq.dao.TokenDao;
import com.chatak.pg.acq.dao.model.PGCardTokenDetails;
import com.chatak.pg.util.Properties;
import com.litle.sdk.generate.MethodOfPaymentTypeEnum;

@RunWith(MockitoJUnitRunner.class)
public class TokenizationServiceImplTest {

	@InjectMocks
	TokenizationServiceImpl tokenizationServiceImpl = new TokenizationServiceImpl();

	@Mock
	TokenDao tokenDao;

	@Before
	public void pro() {
		java.util.Properties properties = new java.util.Properties();
		properties.setProperty("chatak-pay.accVerificationResults", "2");
		properties.setProperty("chatak-pay.accCardHolderDataLen", "1");
		properties.setProperty("chatak-pay.idvPerformed", "3");
		properties.setProperty("chatak-pay.tokenDeviceInfolen", "6");
		properties.setProperty("chatak-pay.serviceEndPointTokenize", "8");
		Properties.mergeProperties(properties);
	}

	@Test
	public void testTokenize() throws ChatakPayException {
		CardDetails cardDetails = new CardDetails();
		TokenizeResponse response = new TokenizeResponse();
		cardDetails.setNumber("46565");
		cardDetails.setExpMonthYear("5462");
		cardDetails.setType(MethodOfPaymentTypeEnum.AX);
		response.setPaymentToken("456");
		tokenizationServiceImpl.tokenize(cardDetails);
	}

	@Test
	public void testTokenizeNotNull() throws ChatakPayException {
		CardDetails cardDetails = new CardDetails();
		TokenizeResponse response = new TokenizeResponse();
		PGCardTokenDetails tokenExists = new PGCardTokenDetails();
		cardDetails.setNumber("46565");
		cardDetails.setExpMonthYear("5462");
		cardDetails.setType(MethodOfPaymentTypeEnum.AX);
		response.setPaymentToken("546546");
		Mockito.when(tokenDao.findByEmailAndCardLastFourAndCardType(Matchers.anyString(), Matchers.anyString(),
				Matchers.anyString())).thenReturn(tokenExists);
		tokenizationServiceImpl.tokenize(cardDetails);
	}

	@Test
	public void testDeTokenize() throws ChatakPayException {
		PGCardTokenDetails pgCardTokenDetails = new PGCardTokenDetails();
		pgCardTokenDetails.setTokenExpDate("12/08/1991");
		Mockito.when(tokenDao.getPgCardTokenDetails(Matchers.anyString())).thenReturn(pgCardTokenDetails);
		tokenizationServiceImpl.deTokenize("123");
	}

	@Test
	public void testDeTokenizeNull() throws ChatakPayException {
		tokenizationServiceImpl.deTokenize("1");
	}

}
