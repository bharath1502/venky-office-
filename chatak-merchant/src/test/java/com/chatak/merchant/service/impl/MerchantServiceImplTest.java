package com.chatak.merchant.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import com.chatak.mailsender.service.MailServiceManagement;
import com.chatak.merchant.exception.ChatakMerchantException;
import com.chatak.pg.acq.dao.AdminUserDao;
import com.chatak.pg.acq.dao.LegalEntityDao;
import com.chatak.pg.acq.dao.MerchantDao;
import com.chatak.pg.acq.dao.MerchantProfileDao;
import com.chatak.pg.acq.dao.MerchantUpdateDao;
import com.chatak.pg.acq.dao.PartnerDao;
import com.chatak.pg.acq.dao.ProgramManagerDao;
import com.chatak.pg.acq.dao.SubMerchantDao;
import com.chatak.pg.acq.dao.SwitchDao;
import com.chatak.pg.acq.dao.model.PGAccount;
import com.chatak.pg.acq.dao.model.PGLegalEntity;
import com.chatak.pg.acq.dao.model.PGMerchant;
import com.chatak.pg.acq.dao.model.PGMerchantBank;
import com.chatak.pg.acq.dao.model.PGMerchantConfig;
import com.chatak.pg.acq.dao.model.Partner;
import com.chatak.pg.acq.dao.repository.CurrencyConfigRepository;
import com.chatak.pg.model.Merchant;
import com.chatak.pg.user.bean.AddMerchantRequest;
import com.chatak.pg.user.bean.AddMerchantResponse;
import com.chatak.pg.user.bean.GetMerchantListRequest;
import com.chatak.pg.user.bean.GetMerchantListResponse;
import com.chatak.pg.user.bean.ProgramManagerRequest;
import com.chatak.pg.user.bean.UpdateMerchantRequest;
import com.chatak.pg.user.bean.UpdateMerchantResponse;
import com.chatak.prepaid.velocity.IVelocityTemplateCreator;

@RunWith(MockitoJUnitRunner.class)
public class MerchantServiceImplTest {

	@InjectMocks
	private MerchantServiceImpl merchantServiceImpl = new MerchantServiceImpl();

	@Mock
	private MerchantDao merchantDao;

	@Mock
	private IVelocityTemplateCreator iVelocityTemplateCreator;

	@Mock
	private LegalEntityDao legalEntityDao;

	@Mock
	SwitchDao switchDao;

	@Mock
	MailServiceManagement mailingServiceManagement;

	@Mock
	AdminUserDao adminUserDao;

	@Mock
	CurrencyConfigRepository currencyConfigRepository;

	@Mock
	MerchantUpdateDao merchantUpdateDao;

	@Mock
	SubMerchantDao subMerchantDao;

	@Mock
	MerchantProfileDao merchantProfileDao;
	
	@Mock
	Merchant merchant;
	
	@Mock
	PartnerDao partnerDao;
	
	@Mock
	ProgramManagerDao programManagerDao;

	@Test
	public void testAddMerchantDaily() throws ChatakMerchantException {
		merchant = new Merchant();
		AddMerchantResponse addMerchantResponse = new AddMerchantResponse();
		PGMerchant pgMerchant = new PGMerchant();
		merchant.setAutoTransferDay("D");
		addMerchantResponse.setErrorCode("00");
		addMerchantResponse.setMerchantCode("234");
		merchant.setStatus(0);
		Mockito.when(merchantProfileDao.getMerchantById(Matchers.anyLong())).thenReturn(pgMerchant);
		Mockito.when(merchantUpdateDao.addMerchant(Matchers.any(AddMerchantRequest.class))).thenReturn(addMerchantResponse);
		Assert.assertNotNull(merchantServiceImpl.addMerchant(merchant, "abcd"));
	}

	@Test
	public void testAddMerchantWeekly() throws ChatakMerchantException {
		merchant = new Merchant();
		AddMerchantResponse addMerchantResponse = new AddMerchantResponse();
		PGMerchant pgMerchant = new PGMerchant();
		merchant.setAutoTransferDay("W");
		addMerchantResponse.setErrorCode("00");
		addMerchantResponse.setMerchantCode("234");
		merchant.setStatus(0);
		Mockito.when(merchantProfileDao.getMerchantById(Matchers.anyLong())).thenReturn(pgMerchant);
		Mockito.when(merchantUpdateDao.addMerchant(Matchers.any(AddMerchantRequest.class))).thenReturn(addMerchantResponse);
		Assert.assertNotNull(merchantServiceImpl.addMerchant(merchant, "abcd"));
	}

	@Test
	public void testAddMerchantMonthly() throws ChatakMerchantException {
		merchant = new Merchant();
		AddMerchantResponse addMerchantResponse = new AddMerchantResponse();
		PGMerchant pgMerchant = new PGMerchant();
		merchant.setAutoTransferDay("M");
		addMerchantResponse.setErrorCode("00");
		addMerchantResponse.setMerchantCode("234");
		merchant.setStatus(0);
		Mockito.when(merchantProfileDao.getMerchantById(Matchers.anyLong())).thenReturn(pgMerchant);
		Mockito.when(merchantUpdateDao.addMerchant(Matchers.any(AddMerchantRequest.class))).thenReturn(addMerchantResponse);
		Assert.assertNotNull(merchantServiceImpl.addMerchant(merchant, "abcd"));
	}

	@Test
	public void testAddMerchantElse() throws ChatakMerchantException {
		merchant = new Merchant();
		AddMerchantResponse addMerchantResponse = new AddMerchantResponse();
		PGMerchant pgMerchant = new PGMerchant();
		BigDecimal bigDecimal = new BigDecimal(Integer.parseInt("1234"));
		merchant.setAutoTransferDay("K");
		addMerchantResponse.setErrorCode("00");
		addMerchantResponse.setMerchantCode("234");
		merchant.setStatus(0);
		merchant.setLitleMID("Vantiv");
		merchant.setAutoTransferLimit(bigDecimal);
		Mockito.when(merchantProfileDao.getMerchantById(Matchers.anyLong())).thenReturn(pgMerchant);
		Mockito.when(merchantUpdateDao.addMerchant(Matchers.any(AddMerchantRequest.class))).thenReturn(addMerchantResponse);
		Assert.assertNotNull(merchantServiceImpl.addMerchant(merchant, "abcd"));
	}

	@Test
	public void testSearchMerchantStatusSuccess() throws ChatakMerchantException {
		List<PGMerchant> subMerchants = new ArrayList<PGMerchant>();
		PGMerchant pgMerchant = new PGMerchant();
		pgMerchant.setBusinessName("asdg");
		subMerchants.add(pgMerchant);
		merchant = new Merchant();
		pgMerchant.setStatus(0);
		GetMerchantListResponse getMerchantListResponse = new GetMerchantListResponse();
		getMerchantListResponse.setSubMerchants(subMerchants);
		getMerchantListResponse.setMerchants(subMerchants);
		Mockito.when(subMerchantDao.getMerchantlistOnSubMerchantCode(Matchers.any(GetMerchantListRequest.class)))
				.thenReturn(getMerchantListResponse);
		Mockito.when(merchantProfileDao.getMerchantlist(Matchers.any(GetMerchantListRequest.class))).thenReturn(getMerchantListResponse);
		Assert.assertNotNull(merchantServiceImpl.searchMerchant(merchant));
	}

	@Test
	public void testSearchMerchantStatusPending() throws ChatakMerchantException {
		List<PGMerchant> subMerchants = new ArrayList<PGMerchant>();
		PGMerchant pgMerchant = new PGMerchant();
		pgMerchant.setBusinessName("asdg");
		subMerchants.add(pgMerchant);
		merchant = new Merchant();
		pgMerchant.setStatus(1);
		GetMerchantListResponse getMerchantListResponse = new GetMerchantListResponse();
		getMerchantListResponse.setSubMerchants(subMerchants);
		getMerchantListResponse.setMerchants(subMerchants);
		Mockito.when(subMerchantDao.getMerchantlistOnSubMerchantCode(Matchers.any(GetMerchantListRequest.class)))
				.thenReturn(getMerchantListResponse);
		Mockito.when(merchantProfileDao.getMerchantlist(Matchers.any(GetMerchantListRequest.class))).thenReturn(getMerchantListResponse);
		Assert.assertNotNull(merchantServiceImpl.searchMerchant(merchant));
	}

	@Test
	public void testSearchMerchantStatusDeleted() throws ChatakMerchantException {
		List<PGMerchant> subMerchants = new ArrayList<PGMerchant>();
		PGMerchant pgMerchant = new PGMerchant();
		pgMerchant.setBusinessName("asdg");
		subMerchants.add(pgMerchant);
		merchant = new Merchant();
		pgMerchant.setStatus(Integer.parseInt("3"));
		GetMerchantListResponse getMerchantListResponse = new GetMerchantListResponse();
		getMerchantListResponse.setSubMerchants(subMerchants);
		getMerchantListResponse.setMerchants(subMerchants);
		Mockito.when(subMerchantDao.getMerchantlistOnSubMerchantCode(Matchers.any(GetMerchantListRequest.class)))
				.thenReturn(getMerchantListResponse);
		Mockito.when(merchantProfileDao.getMerchantlist(Matchers.any(GetMerchantListRequest.class))).thenReturn(getMerchantListResponse);
		Assert.assertNotNull(merchantServiceImpl.searchMerchant(merchant));
	}

	@Test
	public void testSearchMerchantStatusSuspended() throws ChatakMerchantException {
		List<PGMerchant> subMerchants = new ArrayList<PGMerchant>();
		PGMerchant pgMerchant = new PGMerchant();
		pgMerchant.setBusinessName("asdg");
		subMerchants.add(pgMerchant);
		merchant = new Merchant();
		pgMerchant.setStatus(Integer.parseInt("2"));
		GetMerchantListResponse getMerchantListResponse = new GetMerchantListResponse();
		getMerchantListResponse.setSubMerchants(subMerchants);
		getMerchantListResponse.setMerchants(subMerchants);
		Mockito.when(subMerchantDao.getMerchantlistOnSubMerchantCode(Matchers.any(GetMerchantListRequest.class)))
				.thenReturn(getMerchantListResponse);
		Mockito.when(merchantProfileDao.getMerchantlist(Matchers.any(GetMerchantListRequest.class))).thenReturn(getMerchantListResponse);
		Assert.assertNotNull(merchantServiceImpl.searchMerchant(merchant));
	}

	@Test
	public void testSearchMerchantStatusSelfRegistrationPending() throws ChatakMerchantException {
		List<PGMerchant> subMerchants = new ArrayList<PGMerchant>();
		PGMerchant pgMerchant = new PGMerchant();
		pgMerchant.setBusinessName("asdg");
		subMerchants.add(pgMerchant);
		merchant = new Merchant();
		pgMerchant.setStatus(Integer.parseInt("5"));
		GetMerchantListResponse getMerchantListResponse = new GetMerchantListResponse();
		getMerchantListResponse.setSubMerchants(subMerchants);
		getMerchantListResponse.setMerchants(subMerchants);
		Mockito.when(subMerchantDao.getMerchantlistOnSubMerchantCode(Matchers.any(GetMerchantListRequest.class)))
				.thenReturn(getMerchantListResponse);
		Mockito.when(merchantProfileDao.getMerchantlist(Matchers.any(GetMerchantListRequest.class))).thenReturn(getMerchantListResponse);
		Assert.assertNotNull(merchantServiceImpl.searchMerchant(merchant));
	}

	@Test(expected=NullPointerException.class)
	public void testGetMerchantDaily() throws ChatakMerchantException {
		merchant = new Merchant();
		merchant.setLegalAnnualCard("abcd.abcd");
		PGMerchant pgMerchant = new PGMerchant();
		PGAccount pgAccount = new PGAccount();
		PGMerchantConfig pgMerchantConfig = new PGMerchantConfig();
		BigDecimal bigDecimal = new BigDecimal(Integer.parseInt("12345"));
		PGMerchantBank merchantBank = new PGMerchantBank();
		PGLegalEntity pgLegalEntity = new PGLegalEntity();
		pgLegalEntity.setPgLegalEntityId(Long.parseLong("12"));
		pgMerchantConfig.setAutoSettlement(Integer.parseInt("12"));
		pgMerchantConfig.setRefunds(1);
		pgMerchantConfig.setShippingAmount(1);
		pgMerchantConfig.setTaxAmount(1);
		pgMerchantConfig.setTipAmount(1);
		pgMerchant.setMerchantConfig(pgMerchantConfig);
		pgAccount.setAutoTransferDay("D");
		pgAccount.setAutoPaymentLimit(bigDecimal);
		merchantBank.setBankName("asdfg");
		pgMerchant.setMerchantCode("abcdef");
		pgMerchant.setPartnerId("1545456");
		pgAccount.setPgMerchantBank(merchantBank);
		pgLegalEntity.setCountryOfCitizenship("2222");
		Partner partner = new Partner();
		partner.setProgramManagerId(Long.parseLong("4154565"));
		ProgramManagerRequest programManager = new ProgramManagerRequest();
		programManager.setProgramManagerName("john");
		Mockito.when(programManagerDao.findProgramManagerById(Matchers.anyLong())).thenReturn(programManager);
		Mockito.when(partnerDao.findByPartnerId(Matchers.anyLong())).thenReturn(partner);
		Mockito.when(legalEntityDao.getLegalEntityByMerchantId(Matchers.anyString())).thenReturn(pgLegalEntity);
		Mockito.when(merchantProfileDao.getMerchantById(Matchers.anyLong())).thenReturn(pgMerchant);
		Mockito.when(merchantProfileDao.getPgAccount(Matchers.anyString())).thenReturn(pgAccount);
		Assert.assertNotNull(merchantServiceImpl.getMerchant(merchant));
	}

	@Test(expected=ArrayIndexOutOfBoundsException.class)
	public void testGetMerchantWeekly() throws ChatakMerchantException {
		merchant = new Merchant();
		PGMerchant pgMerchant = new PGMerchant();
		PGAccount pgAccount = new PGAccount();
		PGMerchantConfig pgMerchantConfig = new PGMerchantConfig();
		BigDecimal bigDecimal = new BigDecimal(Integer.parseInt("12345"));
		PGMerchantBank merchantBank = new PGMerchantBank();
		PGLegalEntity pgLegalEntity = new PGLegalEntity();
		pgMerchantConfig.setAutoSettlement(Integer.parseInt("12"));
		pgMerchantConfig.setRefunds(1);
		pgMerchantConfig.setShippingAmount(1);
		pgMerchantConfig.setTaxAmount(1);
		pgMerchantConfig.setTipAmount(1);
		pgMerchant.setMerchantConfig(pgMerchantConfig);
		pgAccount.setAutoTransferDay("W");
		pgAccount.setAutoPaymentLimit(bigDecimal);
		merchantBank.setBankName("asdfg");
		pgMerchant.setMerchantCode("abcdef");
		Mockito.when(legalEntityDao.getLegalEntityByMerchantId(Matchers.anyString())).thenReturn(pgLegalEntity);
		Mockito.when(merchantProfileDao.getMerchantById(Matchers.anyLong())).thenReturn(pgMerchant);
		Mockito.when(merchantProfileDao.getPgAccount(Matchers.anyString())).thenReturn(pgAccount);
		Assert.assertNotNull(merchantServiceImpl.getMerchant(merchant));
	}

	@Test(expected=ArrayIndexOutOfBoundsException.class)
	public void testGetMerchantMonthly() throws ChatakMerchantException {
		merchant = new Merchant();
		PGMerchant pgMerchant = new PGMerchant();
		PGAccount pgAccount = new PGAccount();
		PGMerchantConfig pgMerchantConfig = new PGMerchantConfig();
		BigDecimal bigDecimal = new BigDecimal(Integer.parseInt("12345"));
		PGMerchantBank merchantBank = new PGMerchantBank();
		PGLegalEntity pgLegalEntity = new PGLegalEntity();
		pgMerchantConfig.setAutoSettlement(Integer.parseInt("12"));
		pgMerchantConfig.setRefunds(1);
		pgMerchantConfig.setShippingAmount(1);
		pgMerchantConfig.setTaxAmount(1);
		pgMerchantConfig.setTipAmount(1);
		pgMerchant.setMerchantConfig(pgMerchantConfig);
		pgAccount.setAutoTransferDay("M");
		merchant.setAutoTransferWeeklyDay("W");
		pgAccount.setAutoPaymentLimit(bigDecimal);
		merchantBank.setBankName("asdfg");
		pgMerchant.setMerchantCode("abcdef");
		Mockito.when(legalEntityDao.getLegalEntityByMerchantId(Matchers.anyString())).thenReturn(pgLegalEntity);
		Mockito.when(merchantProfileDao.getMerchantById(Matchers.anyLong())).thenReturn(pgMerchant);
		Mockito.when(merchantProfileDao.getPgAccount(Matchers.anyString())).thenReturn(pgAccount);
		Assert.assertNotNull(merchantServiceImpl.getMerchant(merchant));
	}

	@Test
	public void testUpdateMerchantDaily() throws ChatakMerchantException {
		merchant = new Merchant();
		PGMerchant pgMerchant = new PGMerchant();
		UpdateMerchantRequest updateMerchantRequest = new UpdateMerchantRequest();
		UpdateMerchantResponse updateMerchantResponse = new UpdateMerchantResponse();
		merchant.setIssuancePartnerId("12345");
		merchant.setProgramManagerId("abc");
		merchant.setAutoTransferDay("D");
		merchant.setProcessor("Vantiv");
		merchant.setMerchantCode("111");
		updateMerchantResponse.setUpdated(true);
		merchant.setLegalAnnualCard("1234");
		updateMerchantRequest.setMerchantCode("2345");
		Mockito.when(merchantDao.getParentMerchantCode(Matchers.anyString())).thenReturn("ababab");
		Mockito.when(merchantUpdateDao.getMerchantByCode(Matchers.anyString())).thenReturn(pgMerchant);
		Mockito.when(merchantUpdateDao.updateMerchant(Matchers.any(UpdateMerchantRequest.class))).thenReturn(updateMerchantResponse);
		Assert.assertNotNull(merchantServiceImpl.updateMerchant(merchant));
	}

	@Test(expected=NullPointerException.class)
	public void testUpdateMerchantWeekly() throws ChatakMerchantException {
		merchant = new Merchant();
		merchant.setIssuancePartnerId("12345");
		merchant.setProgramManagerId("abc");
		merchant.setAutoTransferDay("W");
		Assert.assertNotNull(merchantServiceImpl.updateMerchant(merchant));
	}

	@Test(expected=NullPointerException.class)
	public void testUpdateMerchantMonthly() throws ChatakMerchantException {
		merchant = new Merchant();
		merchant.setIssuancePartnerId("12345");
		merchant.setProgramManagerId("abc");
		merchant.setAutoTransferDay("M");
		Assert.assertNotNull(merchantServiceImpl.updateMerchant(merchant));
	}
	
	@Test
	public void testFindPartnerByMerchantCode() {
	  PGMerchant pgMerchant = new PGMerchant();
	  pgMerchant.setPartnerId("156156");
	  Partner partner = new Partner();
	  partner.setProgramManagerId(Long.parseLong("54864654"));
	  partner.setPartnerName("partnerName");
	  ProgramManagerRequest programManager = new ProgramManagerRequest();
	  programManager.setProgramManagerName("programManagerName");
      Mockito.when(programManagerDao.findProgramManagerById(Matchers.anyLong())).thenReturn(programManager);
	  Mockito.when(partnerDao.findByPartnerId(Matchers.anyLong())).thenReturn(partner);
	  Mockito.when(merchantDao.findBymerchantConfig(Matchers.anyString())).thenReturn(pgMerchant);
	  Assert.assertNotNull(merchantServiceImpl.findPartnerByMerchantCode("5156455"));
	}
}
