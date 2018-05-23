package com.chatak.pay.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.context.MessageSource;

import com.chatak.mailsender.service.MailServiceManagement;
import com.chatak.pay.controller.model.LoginRequest;
import com.chatak.pay.exception.ChatakPayException;
import com.chatak.pay.model.Merchant;
import com.chatak.pg.acq.dao.CurrencyConfigDao;
import com.chatak.pg.acq.dao.MerchantDao;
import com.chatak.pg.acq.dao.MerchantProfileDao;
import com.chatak.pg.acq.dao.MerchantUpdateDao;
import com.chatak.pg.acq.dao.MerchantUserDao;
import com.chatak.pg.acq.dao.TerminalDao;
import com.chatak.pg.acq.dao.model.PGCurrencyConfig;
import com.chatak.pg.acq.dao.model.PGMerchant;
import com.chatak.pg.acq.dao.model.PGMerchantUsers;
import com.chatak.pg.acq.dao.model.PGTerminal;
import com.chatak.pg.user.bean.AddMerchantBankRequest;
import com.chatak.pg.user.bean.AddMerchantBankResponse;
import com.chatak.pg.user.bean.AddMerchantRequest;
import com.chatak.pg.user.bean.DeleteMerchantRequest;
import com.chatak.pg.user.bean.DeleteMerchantResponse;
import com.chatak.pg.user.bean.GetMerchantBankDetailsRequest;
import com.chatak.pg.user.bean.GetMerchantBankDetailsResponse;
import com.chatak.pg.user.bean.GetMerchantListRequest;
import com.chatak.pg.user.bean.GetMerchantListResponse;
import com.chatak.pg.user.bean.UpdateMerchantRequest;
import com.chatak.pg.user.bean.UpdateMerchantResponse;
import com.chatak.pg.util.Properties;
import com.chatak.prepaid.velocity.IVelocityTemplateCreator;

@RunWith(MockitoJUnitRunner.class)
public class PGMerchantServiceImplTest {

	@InjectMocks
	PGMerchantServiceImpl pgMerchantServiceImpl = new PGMerchantServiceImpl();

	@Mock
	private MessageSource messageSource;

	@Mock
	private MerchantDao merchantDao;

	@Mock
	private TerminalDao terminalDao;

	@Mock
	private MerchantUserDao merchantUserDao;

	@Mock
	private CurrencyConfigDao currencyConfigDao;

	@Mock
	MailServiceManagement mailingServiceManagement;

	@Mock
	IVelocityTemplateCreator iVelocityTemplateCreator;

	@Mock
	MerchantUpdateDao merchantUpdateDao;

	@Mock
	MerchantProfileDao merchantProfileDao;

	@Before
	public void pro() {
		java.util.Properties properties = new java.util.Properties();
		properties.setProperty("chatak-tms.enabled", "10");
		Properties.mergeProperties(properties);
	}

	@Test(expected = NullPointerException.class)
	public void testAddMerchant() throws ChatakPayException {
		AddMerchantRequest addMerchantRequest = new AddMerchantRequest();
		pgMerchantServiceImpl.addMerchant(addMerchantRequest);
	}

	@Test
	public void testUpdateMerchantt() throws ChatakPayException {
		UpdateMerchantRequest updateMerchantRequest = new UpdateMerchantRequest();
		UpdateMerchantResponse updateMerchantResponse = new UpdateMerchantResponse();
		updateMerchantResponse.setErrorCode("00");
		Mockito.when(merchantUpdateDao.updateMerchant(Matchers.any(UpdateMerchantRequest.class)))
				.thenReturn(updateMerchantResponse);
		pgMerchantServiceImpl.updateMerchant(updateMerchantRequest);
	}

	@Test
	public void testUpdateMerchanttException() throws ChatakPayException {
		UpdateMerchantRequest updateMerchantRequest = new UpdateMerchantRequest();
		UpdateMerchantResponse updateMerchantResponse = new UpdateMerchantResponse();
		Mockito.when(merchantUpdateDao.updateMerchant(Matchers.any(UpdateMerchantRequest.class)))
				.thenReturn(updateMerchantResponse);
		pgMerchantServiceImpl.updateMerchant(updateMerchantRequest);
	}

	@Test
	public void testDeleteMerchant() throws ChatakPayException {
		DeleteMerchantRequest deleteMerchantRequest = new DeleteMerchantRequest();
		pgMerchantServiceImpl.deleteMerchant(deleteMerchantRequest);
	}

	@Test
	public void testDeleteMerchantElse() throws ChatakPayException {
		DeleteMerchantRequest deleteMerchantRequest = new DeleteMerchantRequest();
		DeleteMerchantResponse deleteMerchantResponse = new DeleteMerchantResponse();
		deleteMerchantRequest.setMerchantCode("abc");
		deleteMerchantResponse.setErrorCode("00");
		Mockito.when(merchantProfileDao.deleteMerchant(Matchers.any(DeleteMerchantRequest.class)))
				.thenReturn(deleteMerchantResponse);
		pgMerchantServiceImpl.deleteMerchant(deleteMerchantRequest);
	}

	@Test
	public void testDeleteMerchantElseInfo() throws ChatakPayException {
		DeleteMerchantRequest deleteMerchantRequest = new DeleteMerchantRequest();
		DeleteMerchantResponse deleteMerchantResponse = new DeleteMerchantResponse();
		deleteMerchantRequest.setMerchantCode("abc");
		deleteMerchantResponse.setErrorCode("01");
		Mockito.when(merchantProfileDao.deleteMerchant(Matchers.any(DeleteMerchantRequest.class)))
				.thenReturn(deleteMerchantResponse);
		pgMerchantServiceImpl.deleteMerchant(deleteMerchantRequest);
	}

	@Test
	public void testDeleteMerchantException() throws ChatakPayException {
		DeleteMerchantRequest deleteMerchantRequest = new DeleteMerchantRequest();
		deleteMerchantRequest.setMerchantCode("abc");
		Mockito.when(merchantProfileDao.deleteMerchant(Matchers.any(DeleteMerchantRequest.class)))
				.thenThrow(new NullPointerException());
		pgMerchantServiceImpl.deleteMerchant(deleteMerchantRequest);
	}

	@Test
	public void testGetMerchantList() throws ChatakPayException {
		GetMerchantListRequest getMerchantListRequest = new GetMerchantListRequest();
		GetMerchantListResponse getMerchantListResponse = new GetMerchantListResponse();
		getMerchantListResponse.setErrorCode("00");
		Mockito.when(merchantProfileDao.getMerchantlist(Matchers.any(GetMerchantListRequest.class)))
				.thenReturn(getMerchantListResponse);
		pgMerchantServiceImpl.getMerchantList(getMerchantListRequest);
	}

	@Test
	public void testGetMerchantListException() throws ChatakPayException {
		GetMerchantListRequest getMerchantListRequest = new GetMerchantListRequest();
		Mockito.when(merchantProfileDao.getMerchantlist(Matchers.any(GetMerchantListRequest.class)))
				.thenThrow(new NullPointerException());
		pgMerchantServiceImpl.getMerchantList(getMerchantListRequest);
	}

	@Test
	public void testAddMerchantBank() throws ChatakPayException {
		AddMerchantBankRequest addMerchantBankRequest = new AddMerchantBankRequest();
		pgMerchantServiceImpl.addMerchantBank(addMerchantBankRequest);
	}

	@Test
	public void testAddMerchantBankElse() throws ChatakPayException {
		AddMerchantBankRequest addMerchantBankRequest = new AddMerchantBankRequest();
		AddMerchantBankResponse addMerchantBankResponse = new AddMerchantBankResponse();
		addMerchantBankResponse.setErrorCode("4234");
		Mockito.when(merchantProfileDao.addMerchantBank(Matchers.any(AddMerchantBankRequest.class)))
				.thenReturn(addMerchantBankResponse);
		pgMerchantServiceImpl.addMerchantBank(addMerchantBankRequest);
	}

	@Test
	public void testGetMerchantBankDetails() throws ChatakPayException {
		GetMerchantBankDetailsRequest getMerchantBankDetailsRequest = new GetMerchantBankDetailsRequest();
		GetMerchantBankDetailsResponse getMerchantBankResponse = new GetMerchantBankDetailsResponse();
		getMerchantBankResponse.setErrorCode("00");
		Mockito.when(merchantDao.getMerchantBankDetails(Matchers.any(GetMerchantBankDetailsRequest.class)))
				.thenReturn(getMerchantBankResponse);
		pgMerchantServiceImpl.getMerchantBankDetails(getMerchantBankDetailsRequest);
	}

	@Test
	public void testGetMerchantBankDetailsInfo() throws ChatakPayException {
		GetMerchantBankDetailsRequest getMerchantBankDetailsRequest = new GetMerchantBankDetailsRequest();
		GetMerchantBankDetailsResponse getMerchantBankResponse = new GetMerchantBankDetailsResponse();
		getMerchantBankResponse.setErrorCode("01");
		Mockito.when(merchantDao.getMerchantBankDetails(Matchers.any(GetMerchantBankDetailsRequest.class)))
				.thenReturn(getMerchantBankResponse);
		pgMerchantServiceImpl.getMerchantBankDetails(getMerchantBankDetailsRequest);
	}

	@Test
	public void testGetMerchantBankDetailsException() throws ChatakPayException {
		GetMerchantBankDetailsRequest getMerchantBankDetailsRequest = new GetMerchantBankDetailsRequest();
		Mockito.when(merchantDao.getMerchantBankDetails(Matchers.any(GetMerchantBankDetailsRequest.class)))
				.thenThrow(new NullPointerException());
		pgMerchantServiceImpl.getMerchantBankDetails(getMerchantBankDetailsRequest);
	}

	@Test
	public void testAuthenticateMerchantUser() {
		LoginRequest loginRequest = new LoginRequest();
		PGMerchantUsers merchantUsers = new PGMerchantUsers();
		PGCurrencyConfig pgCurrencyConfig = new PGCurrencyConfig();
		PGMerchant pgMerchant = new PGMerchant();
		PGTerminal pgTerminal = new PGTerminal();
		loginRequest.setPassword("abc");
		merchantUsers.setMerPassword("900150983CD24FB0D6963F7D28E17F72");
		merchantUsers.setPgMerchantId(Long.parseLong("45"));
		pgMerchant.setMerchantCode("432");
		pgMerchant.setLocalCurrency("534");
		Mockito.when(merchantUserDao.findByUserName(Matchers.anyString())).thenReturn(merchantUsers);
		Mockito.when(merchantProfileDao.getMerchantById(Matchers.anyLong())).thenReturn(pgMerchant);
		Mockito.when(currencyConfigDao.getCurrencyCodeNumeric(Matchers.anyString())).thenReturn(pgCurrencyConfig);
		Mockito.when(terminalDao.getTerminalonMerchantCode(Matchers.anyLong())).thenReturn(pgTerminal);
		Mockito.when(messageSource.getMessage(Matchers.anyString(), Matchers.any(Object[].class),
				Matchers.any(Locale.class))).thenReturn("abcde");
		pgMerchantServiceImpl.authenticateMerchantUser(loginRequest);
	}

	@Test
	public void testAuthenticateMerchantUserElse() {
		LoginRequest loginRequest = new LoginRequest();
		pgMerchantServiceImpl.authenticateMerchantUser(loginRequest);
	}

	@Test
	public void testAuthenticateMerchantUserException() {
		LoginRequest loginRequest = new LoginRequest();
		PGMerchantUsers merchantUsers = new PGMerchantUsers();
		Mockito.when(merchantUserDao.findByUserName(Matchers.anyString())).thenReturn(merchantUsers);
		Mockito.when(currencyConfigDao.getCurrencyCodeNumeric(Matchers.anyString()))
				.thenThrow(new NullPointerException());
		pgMerchantServiceImpl.authenticateMerchantUser(loginRequest);
	}

	@Test
	public void testGetMerchantNamesAndMerchantCode() {
		List<Map<String, String>> merchantList = new ArrayList<>();
		Map<String, String> map = new HashMap<>();
		List<Merchant> merchants = new ArrayList<>();
		Merchant merchant = new Merchant();
		String string = new String();
		map.get(string);
		merchantList.add(map);
		merchants.add(merchant);
		Mockito.when(merchantUpdateDao.getMerchantList()).thenReturn(merchantList);
		pgMerchantServiceImpl.getMerchantNamesAndMerchantCode();
	}

	@Test(expected = ChatakPayException.class)
	public void testChangedPassword() throws ChatakPayException {
		PGMerchantUsers pgMerchantUsers = new PGMerchantUsers();
		Mockito.when(merchantUserDao.findByUserName(Matchers.anyString())).thenReturn(pgMerchantUsers);
		pgMerchantServiceImpl.changedPassword("abc", "abc", "abc");
	}

	@Test(expected = ChatakPayException.class)
	public void testChangedPasswordCurrentPassword() throws ChatakPayException {
		PGMerchantUsers pgMerchantUsers = new PGMerchantUsers();
		pgMerchantUsers.setMerPassword("900150983CD24FB0D6963F7D28E17F72");
		Mockito.when(merchantUserDao.findByUserName(Matchers.anyString())).thenReturn(pgMerchantUsers);
		Mockito.when(messageSource.getMessage(Matchers.anyString(), Matchers.any(Object[].class),
				Matchers.any(Locale.class))).thenReturn("abcd");
		pgMerchantServiceImpl.changedPassword("abcd", "abc", "abcd");
	}

	@Test(expected = ChatakPayException.class)
	public void testChangedPasswordNewPassword() throws ChatakPayException {
		PGMerchantUsers pgMerchantUsers = new PGMerchantUsers();
		pgMerchantUsers.setMerPassword("900150983CD24FB0D6963F7D28E17F72");
		pgMerchantUsers.setPreviousPasswords("900150983CD24FB0D6963F7D28E17F72");
		Mockito.when(merchantUserDao.findByUserName(Matchers.anyString())).thenReturn(pgMerchantUsers);
		Mockito.when(messageSource.getMessage(Matchers.anyString(), Matchers.any(Object[].class),
				Matchers.any(Locale.class))).thenReturn("abcd");
		pgMerchantServiceImpl.changedPassword("abcd", "abc", "900150983CD24FB0D6963F7D28E17F72");
	}

	@Test
	public void testForgotPassword() throws ChatakPayException {
		PGMerchantUsers pgMerchantUsers = new PGMerchantUsers();
		pgMerchantUsers.setStatus(0);
		pgMerchantUsers.setId(Long.parseLong("123"));
		pgMerchantUsers.setEmail("1234");
		Mockito.when(merchantUserDao.findByUserName(Matchers.anyString())).thenReturn(pgMerchantUsers);
		pgMerchantServiceImpl.forgotPassword("abcd", "www.ggg.com");
	}

	@Test(expected = ChatakPayException.class)
	public void testForgotPasswordNull() throws ChatakPayException {
		pgMerchantServiceImpl.forgotPassword("abcd", "www.ggg.com");
	}

	@Test(expected = ChatakPayException.class)
	public void testForgotPasswordElse() throws ChatakPayException {
		PGMerchantUsers pgMerchantUsers = new PGMerchantUsers();
		pgMerchantUsers.setStatus(1);
		Mockito.when(merchantUserDao.findByUserName(Matchers.anyString())).thenReturn(pgMerchantUsers);
		pgMerchantServiceImpl.forgotPassword("abcd", "www.ggg.com");
	}

	@Test(expected = ChatakPayException.class)
	public void testForgotPasswordException() throws ChatakPayException {
		PGMerchantUsers pgMerchantUsers = new PGMerchantUsers();
		pgMerchantUsers.setStatus(0);
		pgMerchantUsers.setId(Long.parseLong("123"));
		pgMerchantUsers.setEmail("1234");
		Mockito.when(merchantUserDao.findByUserName(Matchers.anyString())).thenReturn(pgMerchantUsers);
		Mockito.when(merchantUserDao.createOrUpdateUser(Matchers.any(PGMerchantUsers.class)))
				.thenReturn(pgMerchantUsers);
		Mockito.when(messageSource.getMessage(Matchers.anyString(), Matchers.any(Object[].class),
				Matchers.any(Locale.class))).thenThrow(new NullPointerException());
		pgMerchantServiceImpl.forgotPassword("abcd", "www.ggg.com");
	}

}
