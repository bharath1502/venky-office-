package com.chatak.pay.service.impl;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import com.chatak.pay.exception.ChatakPayException;
import com.chatak.pg.acq.dao.ApplicationClientDao;
import com.chatak.pg.acq.dao.model.PGApplicationClient;

@RunWith(MockitoJUnitRunner.class)
public class ChatakPayServiceImplTest {

	@InjectMocks
	ChatakPayServiceImpl chatakPayServiceImpl = new ChatakPayServiceImpl();

	@Mock
	private ApplicationClientDao applicationClientDao;

	@Test
	public void testGetApplicationClient() throws ChatakPayException {
		PGApplicationClient pgApplicationClient = new PGApplicationClient();
		Mockito.when(applicationClientDao.getApplicationClient(Matchers.anyString(), Matchers.anyString()))
				.thenReturn(pgApplicationClient);
		chatakPayServiceImpl.getApplicationClient("123", "456");
	}

	@Test(expected = ChatakPayException.class)
	public void testGetApplicationClientException() throws ChatakPayException {
		chatakPayServiceImpl.getApplicationClient("123", "456");
	}

	@Test
	public void testGetApplicationClientAuth() throws ChatakPayException {
		PGApplicationClient pgApplicationClient = new PGApplicationClient();
		Mockito.when(applicationClientDao.getApplicationClientAuth(Matchers.anyString(), Matchers.anyString()))
				.thenReturn(pgApplicationClient);
		chatakPayServiceImpl.getApplicationClientAuth("123", "456");
	}

	@Test(expected = ChatakPayException.class)
	public void testGetApplicationClientAuthException() throws ChatakPayException {
		chatakPayServiceImpl.getApplicationClientAuth("123", "456");
	}

	@Test
	public void testGetApplicationClientString() throws ChatakPayException {
		PGApplicationClient pgApplicationClient = new PGApplicationClient();
		Mockito.when(applicationClientDao.getApplicationClient(Matchers.anyString())).thenReturn(pgApplicationClient);
		chatakPayServiceImpl.getApplicationClient("123");
	}

	@Test(expected = ChatakPayException.class)
	public void testGetApplicationClientStringException() throws ChatakPayException {
		chatakPayServiceImpl.getApplicationClient("123");
	}

	@Test
	public void testGetApplicationClientAuthString() throws ChatakPayException {
		PGApplicationClient pgApplicationClient = new PGApplicationClient();
		Mockito.when(applicationClientDao.getApplicationClientAuth(Matchers.anyString()))
				.thenReturn(pgApplicationClient);
		chatakPayServiceImpl.getApplicationClientAuth("123");
	}

	@Test(expected = ChatakPayException.class)
	public void testGetApplicationClientAuthStringException() throws ChatakPayException {
		chatakPayServiceImpl.getApplicationClientAuth("123");
	}

}
