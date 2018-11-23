package com.chatak.pay.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.chatak.pay.service.VaultService;
import com.chatak.pg.bean.GetCardTokensRequest;
import com.chatak.pg.bean.RegisterCardRequest;

@RunWith(MockitoJUnitRunner.class)
public class VaultControllerTest {

	@InjectMocks
	VaultController vaultController = new VaultController();

	@Mock
	private VaultService vaultService;

	@Mock
	HttpServletRequest request;

	@Mock
	HttpServletResponse response;

	@Mock
	HttpSession session;

	@Test
	public void testRegisterCard() {
		RegisterCardRequest registerCardRequest = new RegisterCardRequest();
		vaultController.registerCard(request, response, session, registerCardRequest);
	}

	@Test
	public void testGetTokens() {
		GetCardTokensRequest getCardTokensRequest = new GetCardTokensRequest();
		vaultController.getTokens(request, response, session, getCardTokensRequest);
	}

}
