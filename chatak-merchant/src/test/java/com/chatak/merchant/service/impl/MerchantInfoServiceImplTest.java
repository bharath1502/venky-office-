package com.chatak.merchant.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.context.MessageSource;
import org.springframework.dao.DataAccessException;

import com.chatak.mailsender.exception.PrepaidNotificationException;
import com.chatak.mailsender.service.MailServiceManagement;
import com.chatak.merchant.controller.model.Option;
import com.chatak.merchant.exception.ChatakMerchantException;
import com.chatak.merchant.model.MerchantData;
import com.chatak.pg.acq.dao.AdminUserDao;
import com.chatak.pg.acq.dao.CountryDao;
import com.chatak.pg.acq.dao.MerchantDao;
import com.chatak.pg.acq.dao.MerchantProfileDao;
import com.chatak.pg.acq.dao.MerchantUpdateDao;
import com.chatak.pg.acq.dao.SubMerchantDao;
import com.chatak.pg.acq.dao.SwitchDao;
import com.chatak.pg.acq.dao.model.PGAdminUser;
import com.chatak.pg.acq.dao.model.PGCurrencyConfig;
import com.chatak.pg.acq.dao.model.PGFeeProgram;
import com.chatak.pg.acq.dao.model.PGMerchant;
import com.chatak.pg.acq.dao.model.PGState;
import com.chatak.pg.acq.dao.model.PGSwitch;
import com.chatak.pg.acq.dao.repository.CurrencyConfigRepository;
import com.chatak.pg.bean.CountryRequest;
import com.chatak.pg.bean.Response;
import com.chatak.pg.enums.ProcessorType;
import com.chatak.pg.model.Merchant;
import com.chatak.pg.user.bean.AddMerchantResponse;
import com.chatak.pg.user.bean.FeeProgramNameListDTO;
import com.chatak.pg.user.bean.GetMerchantListRequest;
import com.chatak.pg.user.bean.GetMerchantListResponse;
import com.chatak.prepaid.velocity.IVelocityTemplateCreator;
import com.sun.jersey.api.client.ClientResponse;

@RunWith(MockitoJUnitRunner.class)
public class MerchantInfoServiceImplTest {

	@InjectMocks
	private MerchantInfoServiceImpl merchantInfoServiceImpl = new MerchantInfoServiceImpl();

	@Mock
	private MerchantDao merchantDao;

	@Mock
	private CountryDao countryDao;

	@Mock
	private IVelocityTemplateCreator iVelocityTemplateCreator;

	@Mock
	SwitchDao switchDao;

	@Mock
	MailServiceManagement mailingServiceManagement;

	@Mock
	AdminUserDao adminUserDao;

	@Mock
	CurrencyConfigRepository currencyConfigRepository;

	@Mock
	private MessageSource messageSource;

	@Mock
	MerchantUpdateDao merchantUpdateDao;

	@Mock
	MerchantProfileDao merchantProfileDao;

	@Mock
	SubMerchantDao subMerchantDao;

	@Mock
	FeeProgramNameListDTO feeProgramNameListDTO;

	@Mock
	PrepaidNotificationException prepaidNotificationException;

	@Mock
	ClientResponse clientResponse;

	@Mock
	DataAccessException dataAccessException;

	@Test
	public void testValidateUserName() throws ChatakMerchantException {
		Assert.assertNull(merchantInfoServiceImpl.validateUserName("aaa"));
	}

	@Test
	public void testChangeMerchantStatusNull() throws ChatakMerchantException {
		Merchant merchant = new Merchant();
		Assert.assertNull(merchantInfoServiceImpl.changeMerchantStatus(merchant, 1));
	}

	@Test
	public void testGetActiveMerchants() throws ChatakMerchantException {
		Assert.assertNotNull(merchantInfoServiceImpl.getActiveMerchants());
	}

	@Test
	public void testDeleteMerchantResponse() throws ChatakMerchantException {
		Assert.assertNull(merchantInfoServiceImpl.deleteMerchant(Long.parseLong("123")));
	}

	@Test
	public void testGetFeeProgramNames() throws ChatakMerchantException {
		feeProgramNameListDTO = new FeeProgramNameListDTO();
		List<PGFeeProgram> feeProgramDTOs = new ArrayList<>();
		PGFeeProgram pGFeeProgram = new PGFeeProgram();
		feeProgramNameListDTO.setFeeProgramDTOs(feeProgramDTOs);
		pGFeeProgram.setFeeProgramId(Long.parseLong("12345"));
		pGFeeProgram.setFeeProgramName("abcd");
		feeProgramDTOs.add(pGFeeProgram);
		Mockito.when(merchantProfileDao.getActiveFeePrograms()).thenReturn(feeProgramNameListDTO);
		Assert.assertNotNull(merchantInfoServiceImpl.getFeeProgramNames());

	}

	@Test
	public void testGetFeeProgramNamesNull() throws ChatakMerchantException {
		feeProgramNameListDTO = new FeeProgramNameListDTO();
		Mockito.when(merchantProfileDao.getActiveFeePrograms()).thenReturn(feeProgramNameListDTO);
		Assert.assertNotNull(merchantInfoServiceImpl.getFeeProgramNames());

	}

	@Test
	public void testGetStatesByCountry() throws ChatakMerchantException {
		CountryRequest countryRequest = new CountryRequest();
		List<PGState> pgStates = new ArrayList<>();
		PGState state = new PGState();
		state.setName("bbbb");
		pgStates.add(state);
		countryRequest.setName("aaaa");
		Mockito.when(merchantProfileDao.getStateByCountryId(Matchers.anyLong())).thenReturn(pgStates);
		Mockito.when(countryDao.getCountryByName(Matchers.anyString())).thenReturn(countryRequest);
		Assert.assertNotNull(merchantInfoServiceImpl.getStatesByCountry("abcde"));
	}

	@Test
	public void testGetStatesByCountryElse() throws ChatakMerchantException {
		CountryRequest countryRequest = new CountryRequest();
		countryRequest.setName(null);
		Mockito.when(countryDao.getCountryByName(Matchers.anyString())).thenReturn(countryRequest);
		Assert.assertNotNull(merchantInfoServiceImpl.getStatesByCountry("abcde"));
	}

	@Test
	public void testSetCountries() throws ChatakMerchantException {
		List<CountryRequest> countryRequests = new ArrayList<>();
		CountryRequest countryRequest = new CountryRequest();
		countryRequest.setName("cccc");
		countryRequests.add(countryRequest);
		Mockito.when(countryDao.findAllCountries()).thenReturn(countryRequests);
		Assert.assertNotNull(merchantInfoServiceImpl.getCountries());
	}

	@Test
	public void testValidateEmailId() throws ChatakMerchantException {
		PGAdminUser adminUsers = new PGAdminUser();
		Response response = new Response();
		Mockito.when(adminUserDao.findByEmail(Matchers.anyString())).thenReturn(adminUsers);
		Mockito.when(merchantProfileDao.getUserByEmailId(Matchers.anyString())).thenReturn(response);
		Assert.assertNotNull(merchantInfoServiceImpl.validateEmailId("abc@gmail.com"));
	}

	@Test
	public void testValidateEmailIdElse() throws ChatakMerchantException {
		PGAdminUser adminUsers = null;
		Response response = new Response();
		Mockito.when(adminUserDao.findByEmail(Matchers.anyString())).thenReturn(adminUsers);
		Mockito.when(merchantProfileDao.getUserByEmailId(Matchers.anyString())).thenReturn(response);
		Assert.assertNotNull(merchantInfoServiceImpl.validateEmailId("abc@gmail.com"));
	}

	@Test
	public void testSearchSubMerchantList() throws ChatakMerchantException {
		List<PGMerchant> subMerchants = new ArrayList<PGMerchant>();
		PGMerchant pgMerchant = new PGMerchant();
		pgMerchant.setBusinessName("asdg");
		subMerchants.add(pgMerchant);
		Merchant merchant = new Merchant();
		GetMerchantListResponse getMerchantListResponse = new GetMerchantListResponse();
		getMerchantListResponse.setSubMerchants(subMerchants);
		Mockito.when(subMerchantDao.getSubMerchantListOnMerchantId(Matchers.any(GetMerchantListRequest.class))).thenReturn(getMerchantListResponse);
		Assert.assertNotNull(merchantInfoServiceImpl.searchSubMerchantList(merchant));
	}

	@Test
	public void testSearchSubMerchantListStatusSuccess() throws ChatakMerchantException {
		List<PGMerchant> subMerchants = new ArrayList<PGMerchant>();
		PGMerchant pgMerchant = new PGMerchant();
		pgMerchant.setBusinessName("asdf");
		subMerchants.add(pgMerchant);
		pgMerchant.setStatus(0);
		Merchant merchant = new Merchant();
		GetMerchantListResponse getMerchantListResponse = new GetMerchantListResponse();
		getMerchantListResponse.setSubMerchants(subMerchants);
		Mockito.when(subMerchantDao.getSubMerchantListOnMerchantId(Matchers.any(GetMerchantListRequest.class))).thenReturn(getMerchantListResponse);
		Assert.assertNotNull(merchantInfoServiceImpl.searchSubMerchantList(merchant));
	}

	@Test
	public void testSearchSubMerchantListStatusPending() throws ChatakMerchantException {
		List<PGMerchant> subMerchants = new ArrayList<PGMerchant>();
		PGMerchant pgMerchant = new PGMerchant();
		pgMerchant.setBusinessName("asdf");
		subMerchants.add(pgMerchant);
		pgMerchant.setStatus(1);
		Merchant merchant = new Merchant();
		GetMerchantListResponse getMerchantListResponse = new GetMerchantListResponse();
		getMerchantListResponse.setSubMerchants(subMerchants);
		Mockito.when(subMerchantDao.getSubMerchantListOnMerchantId(Matchers.any(GetMerchantListRequest.class))).thenReturn(getMerchantListResponse);
		Assert.assertNotNull(merchantInfoServiceImpl.searchSubMerchantList(merchant));
	}

	@Test
	public void testSearchSubMerchantListStatusDeleted() throws ChatakMerchantException {
		List<PGMerchant> subMerchants = new ArrayList<PGMerchant>();
		PGMerchant pgMerchant = new PGMerchant();
		pgMerchant.setBusinessName("asdf");
		subMerchants.add(pgMerchant);
		pgMerchant.setStatus(Integer.parseInt("3"));
		Merchant merchant = new Merchant();
		GetMerchantListResponse getMerchantListResponse = new GetMerchantListResponse();
		getMerchantListResponse.setSubMerchants(subMerchants);
		Mockito.when(subMerchantDao.getSubMerchantListOnMerchantId(Matchers.any(GetMerchantListRequest.class))).thenReturn(getMerchantListResponse);
		Assert.assertNotNull(merchantInfoServiceImpl.searchSubMerchantList(merchant));
	}

	@Test
	public void testSearchSubMerchantListStatusSuspended() throws ChatakMerchantException {
		List<PGMerchant> subMerchants = new ArrayList<PGMerchant>();
		PGMerchant pgMerchant = new PGMerchant();
		pgMerchant.setBusinessName("asdf");
		subMerchants.add(pgMerchant);
		pgMerchant.setStatus(Integer.parseInt("2"));
		Merchant merchant = new Merchant();
		GetMerchantListResponse getMerchantListResponse = new GetMerchantListResponse();
		getMerchantListResponse.setSubMerchants(subMerchants);
		Mockito.when(subMerchantDao.getSubMerchantListOnMerchantId(Matchers.any(GetMerchantListRequest.class))).thenReturn(getMerchantListResponse);
		Assert.assertNotNull(merchantInfoServiceImpl.searchSubMerchantList(merchant));
	}

	@Test
	public void testSearchSubMerchantListStatusSelfRegistrationPending() throws ChatakMerchantException {
		List<PGMerchant> subMerchants = new ArrayList<PGMerchant>();
		PGMerchant pgMerchant = new PGMerchant();
		pgMerchant.setBusinessName("asdf");
		subMerchants.add(pgMerchant);
		pgMerchant.setStatus(Integer.parseInt("5"));
		Merchant merchant = new Merchant();
		GetMerchantListResponse getMerchantListResponse = new GetMerchantListResponse();
		getMerchantListResponse.setSubMerchants(subMerchants);
		Mockito.when(subMerchantDao.getSubMerchantListOnMerchantId(Matchers.any(GetMerchantListRequest.class))).thenReturn(getMerchantListResponse);
		Assert.assertNotNull(merchantInfoServiceImpl.searchSubMerchantList(merchant));
	}

	@Test
	public void testGetProcessorNames() throws ChatakMerchantException {
		List<PGSwitch> processorList = new ArrayList<>();
		PGSwitch pgSwitch = new PGSwitch();
		pgSwitch.setSwitchName(ProcessorType.LITLE.toString());
		processorList.add(pgSwitch);
		Mockito.when(switchDao.getAllSwitchNamesByStatus(Matchers.anyInt())).thenReturn(processorList);
		Assert.assertNotNull(merchantInfoServiceImpl.getProcessorNames());
	}

	@Test
	public void testGetProcessorNamesIsNotAvailable() throws ChatakMerchantException {
		List<PGSwitch> processorList = new ArrayList<>();
		PGSwitch pgSwitch = new PGSwitch();
		pgSwitch.setSwitchName("asdf");
		processorList.add(pgSwitch);
		Mockito.when(switchDao.getAllSwitchNamesByStatus(Matchers.anyInt())).thenReturn(processorList);
		Assert.assertNotNull(merchantInfoServiceImpl.getProcessorNames());
	}

	@Test
	public void testGetProcessorNamesEmpty() throws ChatakMerchantException {
		List<PGSwitch> processorList = new ArrayList<>();
		Mockito.when(switchDao.getAllSwitchNamesByStatus(Matchers.anyInt())).thenReturn(processorList);
		Assert.assertNotNull(merchantInfoServiceImpl.getProcessorNames());
	}

	@Test
	public void testValidateEmailIdEdit() throws ChatakMerchantException {
		Assert.assertNull(merchantInfoServiceImpl.validateEmailIdEdit("abc", "12345"));
	}

	@Test
	public void testValidateUserNameEdit() throws ChatakMerchantException {
		Assert.assertNull(merchantInfoServiceImpl.validateUserNameEdit("abcd", "123456"));
	}

	@Test
	public void testSendMerchantSignUPNotification() throws ChatakMerchantException {
		AddMerchantResponse addMerchantResponse = new AddMerchantResponse();
		Merchant merchant = new Merchant();
		merchant.setPhone(Long.parseLong("1234567890"));
		addMerchantResponse.setMerchantCode("abcde");
		merchantInfoServiceImpl.sendMerchantSignUPNotification(addMerchantResponse, merchant);
	}

	@Test
	public void testGetMerchantOnId() throws ChatakMerchantException {
		Assert.assertNull(merchantInfoServiceImpl.getMerchantOnId(Long.parseLong("54321")));
	}

	@Test
	public void testGetParentMerchantCode() throws ChatakMerchantException {
		Assert.assertNull(merchantInfoServiceImpl.getParentMerchantCode("654321"));
	}

	@Test
	public void testGetMerchantAndSubMerchantCode() throws ChatakMerchantException {
		Assert.assertNotNull(merchantInfoServiceImpl.getMerchantAndSubMerchantCode("7654321"));
	}

	@Test
	public void testGetMerchantCodeAndName() throws ChatakMerchantException {
		List<String> merchantCodes = new ArrayList<>();
		String string = new String();
		merchantCodes.add(string);
		List<Option> merchantNames = new ArrayList<>();
		Option merchantData = new Option();
		PGMerchant pgMerchant = new PGMerchant();
		pgMerchant.setFirstName("ababab");
		merchantNames.add(merchantData);
		Mockito.when(subMerchantDao.getMerchantAndSubMerchantList(Matchers.anyString())).thenReturn(merchantCodes);
		Mockito.when(merchantUpdateDao.getMerchant(Matchers.anyString())).thenReturn(pgMerchant);
		Assert.assertNotNull(merchantInfoServiceImpl.getMerchantCodeAndName("87654321"));
	}

	@Test
	public void testValidateParentMerchantCode() throws ChatakMerchantException {
		Assert.assertNull(merchantInfoServiceImpl.validateParentMerchantCode("987654321"));

	}

	@Test(expected = NullPointerException.class)
	public void testGetPartnerList() throws ChatakMerchantException, IOException {
	  clientResponse.setStatus(1);
		Assert.assertNull(merchantInfoServiceImpl.getPartnerList("987654321"));

	}

	@Test
	public void testGetExistingAgentList() {
		Assert.assertNotNull(merchantInfoServiceImpl.getExistingAgentList("54321"));

	}

	@Test
	public void testGetlinkedAgents() {
		Assert.assertNotNull(merchantInfoServiceImpl.getlinkedAgents("4321"));

	}

	@Test
	public void testGetlinkedPartners() {
		Assert.assertNotNull(merchantInfoServiceImpl.getlinkedPartners());

	}

	@Test
	public void testGetPartnerLinkedToMerchant() {
		Assert.assertNull(merchantInfoServiceImpl.getPartnerLinkedToMerchant(Long.parseLong("54321")));

	}

	@Test
	public void testGetFeeProgramNamesForEdit() {
		feeProgramNameListDTO = new FeeProgramNameListDTO();
		List<PGFeeProgram> pgFeePrograms = new ArrayList<>();
		feeProgramNameListDTO.setFeeProgramDTOs(pgFeePrograms);
		PGFeeProgram pGFeeProgram = new PGFeeProgram();
		pGFeeProgram.setFeeProgramId(Long.parseLong("6789"));
		pGFeeProgram.setFeeProgramName("Mark");
		pgFeePrograms.add(pGFeeProgram);
		Mockito.when(merchantDao.getActiveAndCurrentFeePrograms(Matchers.anyString()))
				.thenReturn(feeProgramNameListDTO);
		Assert.assertNotNull(merchantInfoServiceImpl.getFeeProgramNamesForEdit("54321"));

	}

	@Test
	public void testGetFeeProgramNamesForEditIf() {
		feeProgramNameListDTO = new FeeProgramNameListDTO();
		Mockito.when(merchantDao.getActiveAndCurrentFeePrograms(Matchers.anyString()))
				.thenReturn(feeProgramNameListDTO);
		Assert.assertNotNull(merchantInfoServiceImpl.getFeeProgramNamesForEdit("54321"));

	}

	@Test
	public void testValidateAgentDetails() throws ChatakMerchantException {
		PGCurrencyConfig currencyConfig = new PGCurrencyConfig();
		Mockito.when(currencyConfigRepository.findByCurrencyCodeAlpha(Matchers.anyString())).thenReturn(currencyConfig);
		Assert.assertNull(merchantInfoServiceImpl.validateAgentDetails("1", "2", "3", "4"));

	}

	@Test
	public void testGetAgentDataById() throws ChatakMerchantException {

		Assert.assertNull(merchantInfoServiceImpl.getAgentDataById(Long.parseLong("4351")));
	}

	@Test
	public void testGetAgentNames() {
		Assert.assertNull(merchantInfoServiceImpl.getAgentNames("4321"));

	}

	@Test
	public void testChangeMerchantStatus() {
		MerchantData merchantData = new MerchantData();
		PGMerchant pgMerchant = new PGMerchant();
		merchantData.setStatus("Active");
		Mockito.when(merchantProfileDao.getMerchantById(Matchers.anyLong())).thenReturn(pgMerchant);
		Assert.assertNotNull(merchantInfoServiceImpl.changeMerchantStatus(merchantData));

	}

	@Test
	public void testChangeMerchantStatusExc() {
		MerchantData merchantData = new MerchantData();
		merchantData.setStatus("Active");
		Mockito.when(merchantProfileDao.getMerchantById(Matchers.anyLong())).thenThrow(dataAccessException);
		Assert.assertNotNull(merchantInfoServiceImpl.changeMerchantStatus(merchantData));

	}

	@Test
	public void testChangeMerchantStatusElse() {
		MerchantData merchantData = new MerchantData();
		PGMerchant pgMerchant = new PGMerchant();
		merchantData.setStatus("2");
		pgMerchant.setMerchantType("SubMerchant");
		Mockito.when(merchantProfileDao.getMerchantById(Matchers.anyLong())).thenReturn(pgMerchant);
		Assert.assertNotNull(merchantInfoServiceImpl.changeMerchantStatus(merchantData));

	}

	@Test
	public void testChangeMerchantStatusException() {
		MerchantData merchantData = new MerchantData();
		Mockito.when(merchantProfileDao.getMerchantById(Matchers.anyLong())).thenThrow(new NullPointerException());
		Assert.assertNotNull(merchantInfoServiceImpl.changeMerchantStatus(merchantData));

	}
	
	@Test
	public void testGetMerchantMapByMerchantType() {
	  List<Map<String, String>> merchantList = new ArrayList<>();
	  Map<String, String> merchantMap = new HashMap<>();
	  merchantMap.put("type", "subMerchant");
	  merchantList.add(merchantMap);
	  Mockito.when(merchantDao.getMerchantMapByMerchantType(Matchers.anyString())).thenReturn(merchantList);
	  Assert.assertNotNull(merchantInfoServiceImpl.getMerchantMapByMerchantType("merchantType"));
	}

}
