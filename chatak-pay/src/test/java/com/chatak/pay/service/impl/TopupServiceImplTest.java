package com.chatak.pay.service.impl;

import java.util.Locale;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.context.MessageSource;

import com.chatak.pay.controller.model.Request;
import com.chatak.pay.controller.model.topup.TopupRequest;
import com.chatak.pg.acq.dao.MerchantUpdateDao;
import com.chatak.pg.acq.dao.TerminalDao;
import com.chatak.pg.acq.dao.model.PGMerchant;
import com.chatak.pg.acq.dao.model.PGTerminal;

@RunWith(MockitoJUnitRunner.class)
public class TopupServiceImplTest {

	@InjectMocks
	TopupServiceImpl topupServiceImpl = new TopupServiceImpl();

	@Mock
	private MessageSource messageSource;

	@Mock
	private TerminalDao terminalDao;

	@Mock
	MerchantUpdateDao merchantUpdateDao;

	@Test
	public void testGetOperators() {
		Request request = new Request();
		PGMerchant pgMerchant = new PGMerchant();
		request.setTerminalId("46");
		Mockito.when(merchantUpdateDao.getMerchant(Matchers.anyString())).thenReturn(pgMerchant);
		topupServiceImpl.getOperators(request);
	}

	@Test
	public void testGetOperatorsElse() {
		Request request = new Request();
		PGMerchant pgMerchant = new PGMerchant();
		PGTerminal pgTerminal = new PGTerminal();
		request.setTerminalId("46");
		Mockito.when(merchantUpdateDao.getMerchant(Matchers.anyString())).thenReturn(pgMerchant);
		Mockito.when(terminalDao.getTerminal(Matchers.anyLong())).thenReturn(pgTerminal);
		topupServiceImpl.getOperators(request);
	}

	@Test
	public void testGetOperatorsNull() {
		Request request = new Request();
		PGMerchant pgMerchant = new PGMerchant();
		Mockito.when(merchantUpdateDao.getMerchant(Matchers.anyString())).thenReturn(pgMerchant);
		topupServiceImpl.getOperators(request);
	}

	@Test
	public void testGetTopupCategories() {
		TopupRequest topupRequest = new TopupRequest();
		PGMerchant pgMerchant = new PGMerchant();
		topupRequest.setTerminalId("786");
		Mockito.when(merchantUpdateDao.getMerchant(Matchers.anyString())).thenReturn(pgMerchant);
		topupServiceImpl.getTopupCategories(topupRequest);
	}

	@Test
	public void testGetTopupCategoriesElse() {
		TopupRequest topupRequest = new TopupRequest();
		PGMerchant pgMerchant = new PGMerchant();
		PGTerminal pgTerminal = new PGTerminal();
		topupRequest.setTerminalId("786");
		Mockito.when(merchantUpdateDao.getMerchant(Matchers.anyString())).thenReturn(pgMerchant);
		Mockito.when(terminalDao.getTerminal(Matchers.anyLong())).thenReturn(pgTerminal);
		Mockito.when(messageSource.getMessage(Matchers.anyString(), Matchers.any(Object[].class),
				Matchers.any(Locale.class))).thenReturn("abcde");
		topupServiceImpl.getTopupCategories(topupRequest);
	}

	@Test
	public void testGetTopupCategoriesNull() {
		TopupRequest topupRequest = new TopupRequest();
		topupServiceImpl.getTopupCategories(topupRequest);
	}

	@Test
	public void testGetTopupOffers() {
		TopupRequest topupRequest = new TopupRequest();
		PGMerchant pgMerchant = new PGMerchant();
		topupRequest.setTerminalId("786");
		Mockito.when(merchantUpdateDao.getMerchant(Matchers.anyString())).thenReturn(pgMerchant);
		topupServiceImpl.getTopupOffers(topupRequest);
	}

	@Test
	public void testGetTopupOffersElse() {
		TopupRequest topupRequest = new TopupRequest();
		PGMerchant pgMerchant = new PGMerchant();
		PGTerminal pgTerminal = new PGTerminal();
		topupRequest.setTerminalId("786");
		Mockito.when(merchantUpdateDao.getMerchant(Matchers.anyString())).thenReturn(pgMerchant);
		Mockito.when(terminalDao.getTerminal(Matchers.anyLong())).thenReturn(pgTerminal);
		topupServiceImpl.getTopupOffers(topupRequest);
	}

	@Test
	public void testGetTopupOffersNull() {
		TopupRequest topupRequest = new TopupRequest();
		topupServiceImpl.getTopupOffers(topupRequest);
	}

	@Test
	public void testDoTopup() {
		TopupRequest topupRequest = new TopupRequest();
		PGMerchant pgMerchant = new PGMerchant();
		topupRequest.setTerminalId("786");
		Mockito.when(merchantUpdateDao.getMerchant(Matchers.anyString())).thenReturn(pgMerchant);
		topupServiceImpl.doTopup(topupRequest);
	}

	@Test
	public void testDoTopupElse() {
		TopupRequest topupRequest = new TopupRequest();
		PGMerchant pgMerchant = new PGMerchant();
		PGTerminal pgTerminal = new PGTerminal();
		topupRequest.setTerminalId("786");
		Mockito.when(merchantUpdateDao.getMerchant(Matchers.anyString())).thenReturn(pgMerchant);
		Mockito.when(terminalDao.getTerminal(Matchers.anyLong())).thenReturn(pgTerminal);
		topupServiceImpl.doTopup(topupRequest);
	}

	@Test
	public void testDoTopupNull() {
		TopupRequest topupRequest = new TopupRequest();
		topupServiceImpl.doTopup(topupRequest);
	}

}
