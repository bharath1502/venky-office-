package com.chatak.acquirer.admin.service;

import java.math.BigDecimal;
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

import com.chatak.acquirer.admin.exception.ChatakAdminException;
import com.chatak.acquirer.admin.service.impl.MerchantServiceImpl;
import com.chatak.mailsender.service.MailServiceManagement;
import com.chatak.pg.acq.dao.CurrencyDao;
import com.chatak.pg.acq.dao.LegalEntityDao;
import com.chatak.pg.acq.dao.MerchantDao;
import com.chatak.pg.acq.dao.MerchantProfileDao;
import com.chatak.pg.acq.dao.MerchantUpdateDao;
import com.chatak.pg.acq.dao.PartnerDao;
import com.chatak.pg.acq.dao.ProgramManagerDao;
import com.chatak.pg.acq.dao.SubMerchantDao;
import com.chatak.pg.acq.dao.model.PGMerchant;
import com.chatak.pg.acq.dao.model.PGMerchantCurrencyMapping;
import com.chatak.pg.acq.dao.model.Partner;
import com.chatak.pg.acq.dao.model.ProgramManager;
import com.chatak.pg.model.Merchant;
import com.chatak.pg.model.MerchantCurrencyMapping;
import com.chatak.pg.user.bean.AddMerchantRequest;
import com.chatak.pg.user.bean.AddMerchantResponse;
import com.chatak.pg.user.bean.ProgramManagerRequest;
import com.chatak.pg.user.bean.UpdateMerchantRequest;
import com.chatak.pg.user.bean.UpdateMerchantResponse;
import com.chatak.pg.util.Constants;
import com.chatak.prepaid.velocity.IVelocityTemplateCreator;

@RunWith(MockitoJUnitRunner.class)
public class MerchantServiceImplTest {

	@InjectMocks
	MerchantServiceImpl merchantServiceImpl = new MerchantServiceImpl();

	@Mock
	private CurrencyDao currencyDao;

	@Mock
	private MessageSource messageSource;

	@Mock
	private MailServiceManagement mailingServiceManagement;

	@Mock
	private IVelocityTemplateCreator iVelocityTemplateCreator;

	@Mock
	private LegalEntityDao legalEntityDao;

	@Mock
	private MerchantProfileDao merchantProfileDao;

	@Mock
	private MerchantUpdateDao merchantUpdateDao;

	@Mock
	SubMerchantDao subMerchantDao;

	@Mock
	MerchantDao merchantDao;
	
	@Mock
	PartnerDao partnerDao;
	
	@Mock
	ProgramManagerDao programManagerDao;
	
	@Mock
	UpdateMerchantRequest updateMerchantRequest;

	@Test
	public void testAddMerchant() throws ChatakAdminException {
		Merchant merchant = new Merchant();
		List<MerchantCurrencyMapping> currencyMappingList = new ArrayList<>();
		MerchantCurrencyMapping currencyMapping = new MerchantCurrencyMapping();
		List<PGMerchantCurrencyMapping> pgMerchantCurrencyMappingList = new ArrayList<>();
		PGMerchantCurrencyMapping pgMerchantCurrencyMapping = new PGMerchantCurrencyMapping();
		AddMerchantResponse addMerchantResponse = new AddMerchantResponse();
		pgMerchantCurrencyMappingList.add(pgMerchantCurrencyMapping);
		currencyMappingList.add(currencyMapping);
		merchant.setIssuancePartnerId("6456");
		merchant.setLitleMID("7567");
		merchant.setLegalAnnualCard("8935");
		addMerchantResponse.setMerchantCode("436");
		addMerchantResponse.setErrorCode("00");
		merchant.setMerchantType(Constants.TYPE_SUB_MERCHANT);
		merchant.setStatus(0);
		merchant.setCreatedBy("35");
		merchant.setSelectedCurrencyMapping(currencyMappingList);
		Mockito.when(merchantUpdateDao.addMerchant(Matchers.any(AddMerchantRequest.class)))
				.thenReturn(addMerchantResponse);
		Mockito.when(currencyDao.findByMerchantCode(Matchers.anyString())).thenReturn(pgMerchantCurrencyMappingList);
		merchantServiceImpl.addMerchant(merchant, "2111");
	}

	@Test
	public void testAddMerchantGetMerchantBankId() throws ChatakAdminException {
		Merchant merchant = new Merchant();
		PGMerchant pgMerchant = new PGMerchant();
		List<MerchantCurrencyMapping> currencyMappingList = new ArrayList<>();
		MerchantCurrencyMapping currencyMapping = new MerchantCurrencyMapping();
		List<PGMerchantCurrencyMapping> pgMerchantCurrencyMappingList = new ArrayList<>();
		PGMerchantCurrencyMapping pgMerchantCurrencyMapping = new PGMerchantCurrencyMapping();
		AddMerchantResponse addMerchantResponse = new AddMerchantResponse();
		pgMerchantCurrencyMappingList.add(pgMerchantCurrencyMapping);
		currencyMappingList.add(currencyMapping);
		merchant.setIssuancePartnerId("6456");
		merchant.setLitleMID("7567");
		merchant.setLegalAnnualCard("8935");
		addMerchantResponse.setMerchantCode("436");
		addMerchantResponse.setErrorCode("00");
		merchant.setMerchantType(Constants.TYPE_SUB_MERCHANT);
		merchant.setStatus(0);
		merchant.setCreatedBy("35");
		merchant.setParentMerchantId(Long.parseLong("643"));
		merchant.setSelectedCurrencyMapping(currencyMappingList);
		Mockito.when(merchantUpdateDao.addMerchant(Matchers.any(AddMerchantRequest.class)))
				.thenReturn(addMerchantResponse);
		Mockito.when(currencyDao.findByMerchantCode(Matchers.anyString())).thenReturn(pgMerchantCurrencyMappingList);
		Mockito.when(merchantProfileDao.getMerchantById(Matchers.anyLong())).thenReturn(pgMerchant);
		merchantServiceImpl.addMerchant(merchant, "2111");
	}

	@Test
	public void testAddMerchantDaily() throws ChatakAdminException {
		Merchant merchant = new Merchant();
		List<MerchantCurrencyMapping> currencyMappingList = new ArrayList<>();
		MerchantCurrencyMapping currencyMapping = new MerchantCurrencyMapping();
		BigDecimal bigDecimal = new BigDecimal("54");
		List<PGMerchantCurrencyMapping> pgMerchantCurrencyMappingList = new ArrayList<>();
		PGMerchantCurrencyMapping pgMerchantCurrencyMapping = new PGMerchantCurrencyMapping();
		AddMerchantResponse addMerchantResponse = new AddMerchantResponse();
		pgMerchantCurrencyMappingList.add(pgMerchantCurrencyMapping);
		currencyMappingList.add(currencyMapping);
		merchant.setIssuancePartnerId("6456");
		merchant.setLitleMID("7567");
		merchant.setLegalAnnualCard("8935");
		addMerchantResponse.setMerchantCode("436");
		addMerchantResponse.setErrorCode("00");
		merchant.setMerchantType(Constants.TYPE_SUB_MERCHANT);
		merchant.setStatus(0);
		merchant.setCreatedBy("35");
		merchant.setAutoTransferDay("D");
		merchant.setAutoTransferLimit(bigDecimal);
		merchant.setSelectedCurrencyMapping(currencyMappingList);
		Mockito.when(merchantUpdateDao.addMerchant(Matchers.any(AddMerchantRequest.class)))
				.thenReturn(addMerchantResponse);
		Mockito.when(currencyDao.findByMerchantCode(Matchers.anyString())).thenReturn(pgMerchantCurrencyMappingList);
		merchantServiceImpl.addMerchant(merchant, "2111");
	}

	@Test
	public void testAddMerchantWeekly() throws ChatakAdminException {
		Merchant merchant = new Merchant();
		List<MerchantCurrencyMapping> currencyMappingList = new ArrayList<>();
		BigDecimal bigDecimal = new BigDecimal("54");
		MerchantCurrencyMapping currencyMapping = new MerchantCurrencyMapping();
		List<PGMerchantCurrencyMapping> pgMerchantCurrencyMappingList = new ArrayList<>();
		PGMerchantCurrencyMapping pgMerchantCurrencyMapping = new PGMerchantCurrencyMapping();
		AddMerchantResponse addMerchantResponse = new AddMerchantResponse();
		pgMerchantCurrencyMappingList.add(pgMerchantCurrencyMapping);
		currencyMappingList.add(currencyMapping);
		merchant.setIssuancePartnerId("6456");
		merchant.setLitleMID("7567");
		merchant.setLegalAnnualCard("8935");
		addMerchantResponse.setMerchantCode("436");
		addMerchantResponse.setErrorCode("00");
		merchant.setMerchantType(Constants.TYPE_SUB_MERCHANT);
		merchant.setStatus(0);
		merchant.setCreatedBy("35");
		merchant.setSelectedCurrencyMapping(currencyMappingList);
		merchant.setAutoTransferLimit(bigDecimal);
		merchant.setAutoTransferDay("W");
		Mockito.when(merchantUpdateDao.addMerchant(Matchers.any(AddMerchantRequest.class)))
				.thenReturn(addMerchantResponse);
		Mockito.when(currencyDao.findByMerchantCode(Matchers.anyString())).thenReturn(pgMerchantCurrencyMappingList);
		merchantServiceImpl.addMerchant(merchant, "2111");
	}

	@Test
	public void testAddMerchantMonthly() throws ChatakAdminException {
		Merchant merchant = new Merchant();
		List<MerchantCurrencyMapping> currencyMappingList = new ArrayList<>();
		MerchantCurrencyMapping currencyMapping = new MerchantCurrencyMapping();
		List<PGMerchantCurrencyMapping> pgMerchantCurrencyMappingList = new ArrayList<>();
		PGMerchantCurrencyMapping pgMerchantCurrencyMapping = new PGMerchantCurrencyMapping();
		AddMerchantResponse addMerchantResponse = new AddMerchantResponse();
		pgMerchantCurrencyMappingList.add(pgMerchantCurrencyMapping);
		currencyMappingList.add(currencyMapping);
		merchant.setIssuancePartnerId("6456");
		merchant.setLitleMID("7567");
		merchant.setLegalAnnualCard("8935");
		addMerchantResponse.setMerchantCode("436");
		addMerchantResponse.setErrorCode("00");
		merchant.setMerchantType(Constants.TYPE_SUB_MERCHANT);
		merchant.setStatus(0);
		merchant.setCreatedBy("35");
		merchant.setSelectedCurrencyMapping(currencyMappingList);
		merchant.setAutoTransferDay("M");
		Mockito.when(merchantUpdateDao.addMerchant(Matchers.any(AddMerchantRequest.class)))
				.thenReturn(addMerchantResponse);
		Mockito.when(currencyDao.findByMerchantCode(Matchers.anyString())).thenReturn(pgMerchantCurrencyMappingList);
		merchantServiceImpl.addMerchant(merchant, "2111");
	}

	@Test
	public void testAddMerchantSaveCurrencyMapElse() throws ChatakAdminException {
		Merchant merchant = new Merchant();
		List<MerchantCurrencyMapping> currencyMappingList = new ArrayList<>();
		MerchantCurrencyMapping currencyMapping = new MerchantCurrencyMapping();
		List<PGMerchantCurrencyMapping> pgMerchantCurrencyMappingList = new ArrayList<>();
		AddMerchantResponse addMerchantResponse = new AddMerchantResponse();
		currencyMappingList.add(currencyMapping);
		merchant.setIssuancePartnerId("6456");
		merchant.setLitleMID("7567");
		merchant.setLegalAnnualCard("8935");
		addMerchantResponse.setMerchantCode("436");
		addMerchantResponse.setErrorCode("00");
		merchant.setMerchantType(Constants.TYPE_SUB_MERCHANT);
		merchant.setStatus(0);
		merchant.setCreatedBy("35");
		merchant.setSelectedCurrencyMapping(currencyMappingList);
		Mockito.when(merchantUpdateDao.addMerchant(Matchers.any(AddMerchantRequest.class)))
				.thenReturn(addMerchantResponse);
		Mockito.when(currencyDao.findByMerchantCode(Matchers.anyString())).thenReturn(pgMerchantCurrencyMappingList);
		merchantServiceImpl.addMerchant(merchant, "2111");
	}

	@Test(expected=NullPointerException.class)
	public void testAddMerchantException() throws ChatakAdminException {
		Merchant merchant = new Merchant();
		merchantServiceImpl.addMerchant(merchant, "2111");
	}

	@Test
	public void testUpdateMerchant() throws ChatakAdminException {
		Merchant merchant = new Merchant();
		PGMerchant pgMerchant = new PGMerchant();
		updateMerchantRequest = new UpdateMerchantRequest();
		UpdateMerchantResponse updateMerchantResponse = new UpdateMerchantResponse();
		List<MerchantCurrencyMapping> currencyMappingList = new ArrayList<>();
		MerchantCurrencyMapping currencyMapping = new MerchantCurrencyMapping();
		List<PGMerchantCurrencyMapping> pgMerchantCurrencyMappingList = new ArrayList<>();
		PGMerchantCurrencyMapping pgMerchantCurrencyMapping = new PGMerchantCurrencyMapping();
		pgMerchantCurrencyMappingList.add(pgMerchantCurrencyMapping);
		currencyMappingList.add(currencyMapping);
		merchant.setParentMerchantId(Long.parseLong("654"));
		merchant.setIssuancePartnerId("35");
		merchant.setProgramManagerId("54");
		updateMerchantResponse.setUpdated(true);
		merchant.setLegalAnnualCard("654");
		merchant.setLitleMID("54");
		merchant.setMerchantCode("45");
		updateMerchantResponse.setDeclined(true);
		merchant.setStatus(0);
		merchant.setSessionStatus(Integer.parseInt("2"));
		merchant.setMerchantType(Constants.TYPE_SUB_MERCHANT);
		updateMerchantResponse.setPassword("4343");
		merchant.setSelectedCurrencyMapping(currencyMappingList);
		Mockito.when(merchantUpdateDao.getMerchantByCode(Matchers.anyString())).thenReturn(pgMerchant);
		Mockito.when(merchantUpdateDao.getMerchant(Matchers.anyString())).thenReturn(pgMerchant);
		Mockito.when(merchantUpdateDao.updateMerchant(Matchers.any(UpdateMerchantRequest.class)))
				.thenReturn(updateMerchantResponse);
		Mockito.when(currencyDao.findByMerchantCode(Matchers.anyString())).thenReturn(pgMerchantCurrencyMappingList);
		merchantServiceImpl.updateMerchant(merchant);
	}

	@Test
	public void testUpdateMerchantDaily() throws ChatakAdminException {
		Merchant merchant = new Merchant();
		PGMerchant pgMerchant = new PGMerchant();
		updateMerchantRequest = new UpdateMerchantRequest();
		UpdateMerchantResponse updateMerchantResponse = new UpdateMerchantResponse();
		List<MerchantCurrencyMapping> currencyMappingList = new ArrayList<>();
		MerchantCurrencyMapping currencyMapping = new MerchantCurrencyMapping();
		List<PGMerchantCurrencyMapping> pgMerchantCurrencyMappingList = new ArrayList<>();
		PGMerchantCurrencyMapping pgMerchantCurrencyMapping = new PGMerchantCurrencyMapping();
		pgMerchantCurrencyMappingList.add(pgMerchantCurrencyMapping);
		currencyMappingList.add(currencyMapping);
		merchant.setParentMerchantId(Long.parseLong("654"));
		merchant.setIssuancePartnerId("35");
		merchant.setProgramManagerId("54");
		updateMerchantResponse.setUpdated(true);
		merchant.setLegalAnnualCard("654");
		merchant.setLitleMID("54");
		merchant.setMerchantCode("45");
		updateMerchantResponse.setDeclined(true);
		merchant.setStatus(0);
		merchant.setSessionStatus(Integer.parseInt("2"));
		merchant.setMerchantType(Constants.TYPE_SUB_MERCHANT);
		updateMerchantResponse.setPassword("4343");
		merchant.setAutoTransferDay("D");
		merchant.setSelectedCurrencyMapping(currencyMappingList);
		Mockito.when(merchantUpdateDao.getMerchantByCode(Matchers.anyString())).thenReturn(pgMerchant);
		Mockito.when(merchantUpdateDao.getMerchant(Matchers.anyString())).thenReturn(pgMerchant);
		Mockito.when(merchantUpdateDao.updateMerchant(Matchers.any(UpdateMerchantRequest.class)))
				.thenReturn(updateMerchantResponse);
		Mockito.when(currencyDao.findByMerchantCode(Matchers.anyString())).thenReturn(pgMerchantCurrencyMappingList);
		merchantServiceImpl.updateMerchant(merchant);
	}

	@Test
	public void testUpdateMerchantWeekly() throws ChatakAdminException {
		Merchant merchant = new Merchant();
		PGMerchant pgMerchant = new PGMerchant();
		updateMerchantRequest = new UpdateMerchantRequest();
		UpdateMerchantResponse updateMerchantResponse = new UpdateMerchantResponse();
		List<MerchantCurrencyMapping> currencyMappingList = new ArrayList<>();
		MerchantCurrencyMapping currencyMapping = new MerchantCurrencyMapping();
		List<PGMerchantCurrencyMapping> pgMerchantCurrencyMappingList = new ArrayList<>();
		PGMerchantCurrencyMapping pgMerchantCurrencyMapping = new PGMerchantCurrencyMapping();
		pgMerchantCurrencyMappingList.add(pgMerchantCurrencyMapping);
		currencyMappingList.add(currencyMapping);
		merchant.setParentMerchantId(Long.parseLong("654"));
		merchant.setIssuancePartnerId("35");
		merchant.setProgramManagerId("54");
		updateMerchantResponse.setUpdated(true);
		merchant.setLegalAnnualCard("654");
		merchant.setLitleMID("54");
		merchant.setMerchantCode("45");
		updateMerchantResponse.setDeclined(true);
		merchant.setStatus(0);
		merchant.setSessionStatus(Integer.parseInt("2"));
		merchant.setMerchantType(Constants.TYPE_SUB_MERCHANT);
		updateMerchantResponse.setPassword("4343");
		merchant.setAutoTransferDay("W");
		merchant.setSelectedCurrencyMapping(currencyMappingList);
		Mockito.when(merchantUpdateDao.getMerchantByCode(Matchers.anyString())).thenReturn(pgMerchant);
		Mockito.when(merchantUpdateDao.getMerchant(Matchers.anyString())).thenReturn(pgMerchant);
		Mockito.when(merchantUpdateDao.updateMerchant(Matchers.any(UpdateMerchantRequest.class)))
				.thenReturn(updateMerchantResponse);
		Mockito.when(currencyDao.findByMerchantCode(Matchers.anyString())).thenReturn(pgMerchantCurrencyMappingList);
		merchantServiceImpl.updateMerchant(merchant);
	}

	@Test
	public void testUpdateMerchantMonthly() throws ChatakAdminException {
		Merchant merchant = new Merchant();
		PGMerchant pgMerchant = new PGMerchant();
		BigDecimal bigDecimal = new BigDecimal("54343");
		updateMerchantRequest = new UpdateMerchantRequest();
		UpdateMerchantResponse updateMerchantResponse = new UpdateMerchantResponse();
		List<MerchantCurrencyMapping> currencyMappingList = new ArrayList<>();
		MerchantCurrencyMapping currencyMapping = new MerchantCurrencyMapping();
		List<PGMerchantCurrencyMapping> pgMerchantCurrencyMappingList = new ArrayList<>();
		PGMerchantCurrencyMapping pgMerchantCurrencyMapping = new PGMerchantCurrencyMapping();
		pgMerchantCurrencyMappingList.add(pgMerchantCurrencyMapping);
		currencyMappingList.add(currencyMapping);
		merchant.setParentMerchantId(Long.parseLong("654"));
		merchant.setIssuancePartnerId("35");
		merchant.setProgramManagerId("54");
		updateMerchantResponse.setUpdated(true);
		merchant.setLegalAnnualCard("654");
		merchant.setLitleMID("54");
		merchant.setMerchantCode("45");
		updateMerchantResponse.setDeclined(true);
		merchant.setStatus(0);
		merchant.setSessionStatus(Integer.parseInt("2"));
		merchant.setMerchantType(Constants.TYPE_SUB_MERCHANT);
		updateMerchantResponse.setPassword("4343");
		merchant.setAutoTransferLimit(bigDecimal);
		merchant.setAutoTransferDay("M");
		merchant.setSelectedCurrencyMapping(currencyMappingList);
		Mockito.when(merchantUpdateDao.getMerchantByCode(Matchers.anyString())).thenReturn(pgMerchant);
		Mockito.when(merchantUpdateDao.getMerchant(Matchers.anyString())).thenReturn(pgMerchant);
		Mockito.when(merchantUpdateDao.updateMerchant(Matchers.any(UpdateMerchantRequest.class)))
				.thenReturn(updateMerchantResponse);
		Mockito.when(currencyDao.findByMerchantCode(Matchers.anyString())).thenReturn(pgMerchantCurrencyMappingList);
		merchantServiceImpl.updateMerchant(merchant);
	}

	@Test(expected = NullPointerException.class)
	public void testUpdateMerchantElse() throws ChatakAdminException {
		Merchant merchant = new Merchant();
		merchantServiceImpl.updateMerchant(merchant);
	}

	@Test
	public void testGetSubMerchantCodeAndCompanyName() {
		List<Map<String, String>> dataList = new ArrayList<>();
		Map<String, String> map = new HashMap<>();
		dataList.add(map);
		Mockito.when(merchantDao.getMerchantCodeAndCompanyName(Matchers.anyString())).thenReturn(dataList);
		merchantServiceImpl.getSubMerchantCodeAndCompanyName("");
	}

	@Test
	public void testGetSubMerchantCodeAndCompanyNameElse() {
		merchantServiceImpl.getSubMerchantCodeAndCompanyName("5435");

	}
	
	@Test
	public void testFindProgramManagerByPartnerId() throws ChatakAdminException {
	  ProgramManager programManager = new ProgramManager();
	  programManager.setProgramManagerName("programManagerName");
	  Mockito.when(partnerDao.findProgramManagerByPartnerId(Matchers.anyString())).thenReturn(programManager);
	  Assert.assertNotNull(merchantServiceImpl.findProgramManagerByPartnerId("12345"));
	}
	
	@Test
	public void testFindPartnerByMerchantCode() {
	  PGMerchant merchant = new PGMerchant();
	  merchant.setPartnerId("1515");
	  Partner partner = new Partner();
	  partner.setProgramManagerId(Long.parseLong("165456"));
	  ProgramManagerRequest programManager = new ProgramManagerRequest();
	  programManager.setProgramManagerName("programManagerName");
	  Mockito.when(merchantDao.findBymerchantConfig(Matchers.anyString())).thenReturn(merchant);
	  Mockito.when(partnerDao.findByPartnerId(Matchers.anyLong())).thenReturn(partner);
	  Mockito.when(programManagerDao.findProgramManagerById(Matchers.anyLong())).thenReturn(programManager);
	  Assert.assertNotNull(merchantServiceImpl.findPartnerByMerchantCode("1565"));
	}
}
