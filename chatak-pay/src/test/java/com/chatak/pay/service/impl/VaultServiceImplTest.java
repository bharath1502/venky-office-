package com.chatak.pay.service.impl;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import com.chatak.pay.exception.ChatakVaultException;
import com.chatak.pg.acq.dao.TokenCustomerDao;
import com.chatak.pg.acq.dao.TokenDao;
import com.chatak.pg.acq.dao.model.PGCardTokenDetails;
import com.chatak.pg.acq.dao.model.PGTokenCustomer;
import com.chatak.pg.bean.GetCardTokensRequest;
import com.chatak.pg.bean.RegisterCardRequest;
import com.chatak.pg.model.CardData;

@RunWith(MockitoJUnitRunner.class)
public class VaultServiceImplTest {

	@InjectMocks
	VaultServiceImpl vaultServiceImpl = new VaultServiceImpl();

	@Mock
	private TokenDao tokenDao;

	@Mock
	private TokenCustomerDao tokenCustomerDao;

	@Test
	public void testRegisterCardToken() throws ChatakVaultException {
		RegisterCardRequest registerCardRequest = new RegisterCardRequest();
		CardData cardData = new CardData();
		PGTokenCustomer pgTokenCustomer = new PGTokenCustomer();
		registerCardRequest.setCardData(cardData);
		registerCardRequest.setPassword("35454");
		Mockito.when(tokenCustomerDao.getTokenCustomerByUserId(Matchers.anyString())).thenReturn(pgTokenCustomer);
		vaultServiceImpl.registerCardToken(registerCardRequest);

	}

	@Test
	public void testRegisterCardTokenElse() throws ChatakVaultException {
		RegisterCardRequest registerCardRequest = new RegisterCardRequest();
		PGTokenCustomer pgTokenCustomer = new PGTokenCustomer();
		CardData cardData = new CardData();
		cardData.setCardNumber("6544");
		cardData.setExpDate("5435");
		registerCardRequest.setCardData(cardData);
		registerCardRequest.setPassword("35454");
		Mockito.when(tokenCustomerDao.createOrUpdateTokenCustomer(Matchers.any(PGTokenCustomer.class)))
				.thenReturn(pgTokenCustomer);
		vaultServiceImpl.registerCardToken(registerCardRequest);
	}

	@Test
	public void testRegisterCardTokenElseErrorCode() throws ChatakVaultException {
		RegisterCardRequest registerCardRequest = new RegisterCardRequest();
		CardData cardData = new CardData();
		cardData.setCardNumber("6544");
		cardData.setExpDate("5435");
		registerCardRequest.setCardData(cardData);
		registerCardRequest.setPassword("35454");
		vaultServiceImpl.registerCardToken(registerCardRequest);
	}

	@Test
	public void testRegisterCardTokenCardRequestNull() throws ChatakVaultException {
		RegisterCardRequest registerCardRequest = new RegisterCardRequest();
		vaultServiceImpl.registerCardToken(registerCardRequest);
	}

	@Test
	public void testRegisterCardTokenNull() throws ChatakVaultException {
		RegisterCardRequest registerCardRequest = new RegisterCardRequest();
		PGCardTokenDetails duplicateTokenEntry = new PGCardTokenDetails();
		CardData cardData = new CardData();
		cardData.setCardNumber("54");
		registerCardRequest.setCardData(cardData);
		registerCardRequest.setPassword("35454");
		Mockito.when(tokenDao.findByPan(Matchers.anyString())).thenReturn(duplicateTokenEntry);
		vaultServiceImpl.registerCardToken(registerCardRequest);
	}

	@Test(expected=ChatakVaultException.class)
	public void testValidateRegisterCardRequestSetUserId() throws ChatakVaultException {
		RegisterCardRequest registerCardRequest = new RegisterCardRequest();
		vaultServiceImpl.validateRegisterCardRequest(registerCardRequest);
	}

	@Test(expected=ChatakVaultException.class)
	public void testValidateTokensRequest() throws ChatakVaultException {
		GetCardTokensRequest getCardTokensRequest = new GetCardTokensRequest();
		getCardTokensRequest.setUserId("abcd");
		vaultServiceImpl.validateTokensRequest(getCardTokensRequest);
	}

	@Test
	public void testValidateTokensRequestLogger() throws ChatakVaultException {
		GetCardTokensRequest getCardTokensRequest = new GetCardTokensRequest();
		getCardTokensRequest.setUserId("abcd");
		getCardTokensRequest.setPassword("abcd");
		vaultServiceImpl.validateTokensRequest(getCardTokensRequest);
	}

}
