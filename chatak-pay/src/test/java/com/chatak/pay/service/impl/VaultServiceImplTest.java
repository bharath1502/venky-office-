package com.chatak.pay.service.impl;

import java.util.ArrayList;
import java.util.List;

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
import com.chatak.pg.bean.CardToken;
import com.chatak.pg.bean.CardTokenData;
import com.chatak.pg.bean.GetCardTokensRequest;
import com.chatak.pg.bean.RegisterCardRequest;
import com.chatak.pg.enums.EntryModeEnum;
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

	@Test(expected=ChatakVaultException.class)
	public void testRegisterCardTokenIf() throws ChatakVaultException {
		RegisterCardRequest registerCardRequest = new RegisterCardRequest();
		PGCardTokenDetails duplicateTokenEntry = new PGCardTokenDetails();
		CardData cardData = new CardData();
		registerCardRequest.setCardData(cardData);
		registerCardRequest.setPassword("35454");
		registerCardRequest.setEntryMode(EntryModeEnum.MAGNETIC_STRIP);
		Mockito.when(tokenDao.findByPan(Matchers.anyString())).thenReturn(duplicateTokenEntry);
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

	@Test(expected=ChatakVaultException.class)
	public void testGetCardTokens() throws ChatakVaultException {
		GetCardTokensRequest getCardTokensRequest = new GetCardTokensRequest();
		List<PGCardTokenDetails> list = new ArrayList<>();
		List<CardToken> tokenList = new ArrayList<>();
		CardToken cardToken = new CardToken();
		PGCardTokenDetails cardTokenDetails = new PGCardTokenDetails();
		PGTokenCustomer tokenCustomer = new PGTokenCustomer();
		getCardTokensRequest.setPassword("1");
		cardTokenDetails.setCardLastFourDigits("7979");
		cardTokenDetails.setToken("a");
		cardTokenDetails.setCardUserEmail("a");
		list.add(cardTokenDetails);
		tokenList.add(cardToken);
		Mockito.when(tokenCustomerDao.getTokenCustomerByUserIdAndPassword(Matchers.anyString(), Matchers.anyString()))
				.thenReturn(tokenCustomer);
		Mockito.when(tokenDao.findByTokenCustomerId(Matchers.anyLong())).thenReturn(list);
		vaultServiceImpl.getCardTokens(getCardTokensRequest);
	}

	@Test(expected=ChatakVaultException.class)
	public void testGetCardTokensElse() throws ChatakVaultException {
		GetCardTokensRequest getCardTokensRequest = new GetCardTokensRequest();
		List<PGCardTokenDetails> list = new ArrayList<>();
		List<CardToken> tokenList = new ArrayList<>();
		CardToken cardToken = new CardToken();
		PGCardTokenDetails cardTokenDetails = new PGCardTokenDetails();
		getCardTokensRequest.setPassword("1");
		cardTokenDetails.setCardLastFourDigits("7979");
		cardTokenDetails.setToken("a");
		cardTokenDetails.setCardUserEmail("a");
		list.add(cardTokenDetails);
		tokenList.add(cardToken);
		vaultServiceImpl.getCardTokens(getCardTokensRequest);
	}

	@Test(expected=ChatakVaultException.class)
	public void testGetCardTokensException() throws ChatakVaultException {
		GetCardTokensRequest getCardTokensRequest = new GetCardTokensRequest();
		vaultServiceImpl.getCardTokens(getCardTokensRequest);
	}

	@Test
	public void testGetCardDataOnTokenData() throws ChatakVaultException {
		CardTokenData cardTokenData = new CardTokenData();
		PGTokenCustomer tokenCustomer = new PGTokenCustomer();
		PGCardTokenDetails tokenDetails = new PGCardTokenDetails();
		cardTokenData.setPassword("54");
		cardTokenData.setToken("1234");
		tokenCustomer.setId(Long.parseLong("2"));
		tokenDetails.setPan("543");
		tokenDetails.setExpiryDate("234");
		tokenDetails.setCardType("MC");
		Mockito.when(tokenCustomerDao.getTokenCustomerByUserIdAndPassword(Matchers.anyString(), Matchers.anyString()))
				.thenReturn(tokenCustomer);
		Mockito.when(tokenDao.findByTokenAndTokenCustomerId(Matchers.anyString(), Matchers.anyLong()))
				.thenReturn(tokenDetails);
		vaultServiceImpl.getCardDataOnTokenData(cardTokenData);
	}

	@Test(expected=ChatakVaultException.class)
	public void testGetCardDataOnTokenDataException() throws ChatakVaultException {
		CardTokenData cardTokenData = new CardTokenData();
		PGTokenCustomer tokenCustomer = new PGTokenCustomer();
		Mockito.when(tokenCustomerDao.getTokenCustomerByUserIdAndPassword(Matchers.anyString(), Matchers.anyString()))
				.thenReturn(tokenCustomer);
		vaultServiceImpl.getCardDataOnTokenData(cardTokenData);
	}

}
