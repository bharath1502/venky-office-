package com.chatak.acquirer.admin.service;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.context.MessageSource;

import com.chatak.acquirer.admin.controller.model.Option;
import com.chatak.acquirer.admin.exception.ChatakAdminException;
import com.chatak.acquirer.admin.model.MerchantData;
import com.chatak.acquirer.admin.service.impl.MerchantValidateServiceImpl;
import com.chatak.mailsender.service.MailServiceManagement;
import com.chatak.pg.acq.dao.AdminUserDao;
import com.chatak.pg.acq.dao.LegalEntityDao;
import com.chatak.pg.acq.dao.MerchantDao;
import com.chatak.pg.acq.dao.MerchantProfileDao;
import com.chatak.pg.acq.dao.MerchantUpdateDao;
import com.chatak.pg.acq.dao.PartnerDao;
import com.chatak.pg.acq.dao.ProgramManagerDao;
import com.chatak.pg.acq.dao.SwitchDao;
import com.chatak.pg.acq.dao.model.PGAccount;
import com.chatak.pg.acq.dao.model.PGAdminUser;
import com.chatak.pg.acq.dao.model.PGCurrencyConfig;
import com.chatak.pg.acq.dao.model.PGFeeProgram;
import com.chatak.pg.acq.dao.model.PGLegalEntity;
import com.chatak.pg.acq.dao.model.PGMerchant;
import com.chatak.pg.acq.dao.model.PGMerchantBank;
import com.chatak.pg.acq.dao.model.PGMerchantConfig;
import com.chatak.pg.acq.dao.model.PGMerchantUsers;
import com.chatak.pg.acq.dao.model.PGSwitch;
import com.chatak.pg.acq.dao.model.Partner;
import com.chatak.pg.acq.dao.model.ProgramManager;
import com.chatak.pg.acq.dao.repository.CurrencyConfigRepository;
import com.chatak.pg.acq.dao.repository.MerchantRepository;
import com.chatak.pg.model.Merchant;
import com.chatak.pg.user.bean.FeeProgramNameListDTO;
import com.chatak.pg.user.bean.ProgramManagerRequest;
import com.chatak.prepaid.velocity.IVelocityTemplateCreator;

@RunWith(MockitoJUnitRunner.class)
public class MerchantValidateServiceImplTest {

	@InjectMocks
	MerchantValidateServiceImpl merchantValidateServiceImpl = new MerchantValidateServiceImpl();

	@Mock
	private MerchantDao merchantDao;

	@Mock
	private MailServiceManagement mailingServiceManagement;

	@Mock
	private IVelocityTemplateCreator iVelocityTemplateCreator;

	@Mock
	private AdminUserDao adminUserDao;

	@Mock
	MerchantRepository merchantRepository;

	@Mock
	private CurrencyConfigRepository currencyConfigRepository;

	@Mock
	private MessageSource messageSource;

	@Mock
	private SwitchDao switchDao;

	@Mock
	private LegalEntityDao legalEntityDao;

	@Mock
	MerchantProfileDao merchantProfileDao;

	@Mock
	MerchantUpdateDao merchantUpdateDao;
	
	@Mock
	PartnerDao partnerDao;
	
	@Mock
	ProgramManagerDao programManagerDao;

	@Test
	public void testValidateAgentDetails() {
		PGCurrencyConfig currencyConfig = new PGCurrencyConfig();
		Mockito.when(currencyConfigRepository.findByCurrencyCodeAlpha(Matchers.anyString())).thenReturn(currencyConfig);
		merchantValidateServiceImpl.validateAgentDetails("34242", "133", "5435", "4234");
	}

	@Test
	public void testvalidateUserName() throws ChatakAdminException {
		merchantValidateServiceImpl.validateUserName("agentAccountNumber");
	}

	@Test
	public void testvalidateEmailId() throws ChatakAdminException {
		PGAdminUser adminUsers = new PGAdminUser();
		Mockito.when(adminUserDao.findByEmail(Matchers.anyString())).thenReturn(adminUsers);
		merchantValidateServiceImpl.validateEmailId("currencyCodeAlpha");
	}

	@Test
	public void testvalidateEmailIdNull() throws ChatakAdminException {
		merchantValidateServiceImpl.validateEmailId("currencyCodeAlpha");
	}

	@Test
	public void testvalidateUserNameEdit() throws ChatakAdminException {
		merchantValidateServiceImpl.validateUserNameEdit("agentAccountNumber", "agentClientId");
	}

	@Test
	public void testvalidateEmailIdEdit() throws ChatakAdminException {
		merchantValidateServiceImpl.validateEmailIdEdit("agentAccountNumber", "agentClientId");
	}

	@Test
	public void testgetAgentDataById() throws ChatakAdminException {
		merchantValidateServiceImpl.getAgentDataById(Long.parseLong("123"));
	}

	@Test(expected = NullPointerException.class)
	public void testgetPartnerList() throws ChatakAdminException, IOException {
		merchantValidateServiceImpl.getPartnerList("currencyCodeAlpha");
	}

	@Test
	public void testgetPartnerLinkedToMerchant() throws ChatakAdminException, IOException {
		merchantValidateServiceImpl.getPartnerLinkedToMerchant(Long.parseLong("5432"));
	}

	@Test
	public void testgetlinkedPartners() throws ChatakAdminException, IOException {
		merchantValidateServiceImpl.getlinkedPartners();
	}

	@Test
	public void testgetlinkedAgents() {
		merchantValidateServiceImpl.getlinkedAgents("54");
	}

	@Test
	public void testgetFeeProgramNamesForEdit() {
		List<PGFeeProgram> feePrograms = new ArrayList<>();
		PGFeeProgram feeProgram = new PGFeeProgram();
		feeProgram.setFeeProgramId(Long.parseLong("35"));
		feePrograms.add(feeProgram);
		FeeProgramNameListDTO feeProgramNameListDTO = new FeeProgramNameListDTO();
		feeProgramNameListDTO.setFeeProgramDTOs(feePrograms);
		Mockito.when(merchantDao.getActiveAndCurrentFeePrograms(Matchers.anyString()))
				.thenReturn(feeProgramNameListDTO);
		merchantValidateServiceImpl.getFeeProgramNamesForEdit("54");
	}

	@Test
	public void testgetFeeProgramNamesForEditNull() {
		FeeProgramNameListDTO feeProgramNameListDTO = new FeeProgramNameListDTO();
		Mockito.when(merchantDao.getActiveAndCurrentFeePrograms(Matchers.anyString()))
				.thenReturn(feeProgramNameListDTO);
		merchantValidateServiceImpl.getFeeProgramNamesForEdit("54");
	}

	@Test
	public void testGetFeeProgramNames() throws ChatakAdminException {
		List<PGFeeProgram> feePrograms = new ArrayList<>();
		PGFeeProgram feeProgram = new PGFeeProgram();
		feeProgram.setFeeProgramId(Long.parseLong("35"));
		feePrograms.add(feeProgram);
		FeeProgramNameListDTO feeProgramNameListDTO = new FeeProgramNameListDTO();
		feeProgramNameListDTO.setFeeProgramDTOs(feePrograms);
		Mockito.when(merchantDao.getActiveAndCurrentFeePrograms(Matchers.anyString()))
				.thenReturn(feeProgramNameListDTO);
		Mockito.when(merchantProfileDao.getActiveFeePrograms()).thenReturn(feeProgramNameListDTO);
		merchantValidateServiceImpl.getFeeProgramNames();
	}

	@Test
	public void testGetFeeProgramNamesNull() throws ChatakAdminException {
		FeeProgramNameListDTO feeProgramNameListDTO = new FeeProgramNameListDTO();
		Mockito.when(merchantDao.getActiveAndCurrentFeePrograms(Matchers.anyString()))
				.thenReturn(feeProgramNameListDTO);
		Mockito.when(merchantProfileDao.getActiveFeePrograms()).thenReturn(feeProgramNameListDTO);
		merchantValidateServiceImpl.getFeeProgramNames();
	}

	@Test
	public void testgetLocalCurrency() throws ChatakAdminException {
		PGMerchant pgMerchant = new PGMerchant();
		Mockito.when(merchantProfileDao.getMerchantById(Matchers.anyLong())).thenReturn(pgMerchant);
		merchantValidateServiceImpl.getLocalCurrency(Long.parseLong("321"));
	}

	@Test
	public void testfetchMerchantCurrencyByCode() {
		PGMerchant pgMerchant = new PGMerchant();
		Mockito.when(merchantUpdateDao.getMerchantByCode(Matchers.anyString())).thenReturn(pgMerchant);
		merchantValidateServiceImpl.fetchMerchantCurrencyByCode("54");
	}

	@Test
	public void testchangeMerchantStatusActive() {
		MerchantData merchantData = new MerchantData();
		PGMerchant pgMerchant = new PGMerchant();
		merchantData.setStatus("Active");
		Mockito.when(merchantProfileDao.getMerchantById(Matchers.anyLong())).thenReturn(pgMerchant);
		merchantValidateServiceImpl.changeMerchantStatus(merchantData);
	}

	@Test
	public void testchangeMerchantStatusSubMerchant() {
		MerchantData merchantData = new MerchantData();
		List<PGMerchant> subMerchantList = new ArrayList<>();
		List<PGAccount> pgMerchantAccounts = new ArrayList<>();
		PGMerchant pgMerchant = new PGMerchant();
		PGAccount account = new PGAccount();
		List<PGMerchantUsers> pgMerchantUsersList = new ArrayList<>();
		PGMerchantUsers merchantUsers = new PGMerchantUsers();
		pgMerchant.setPgMerchantUsers(pgMerchantUsersList);
		pgMerchant.setMerchantCode("SubMerchant");
		merchantData.setStatus("Active");
		pgMerchantUsersList.add(merchantUsers);
		subMerchantList.add(pgMerchant);
		pgMerchantAccounts.add(account);
		Mockito.when(merchantProfileDao.getMerchantById(Matchers.anyLong())).thenReturn(pgMerchant);
		Mockito.when(merchantUpdateDao.createOrUpdateAccouunt(Matchers.any(PGAccount.class))).thenReturn(account);
		Mockito.when(merchantUpdateDao.findByParentMerchantIdAndStatus(Matchers.anyLong(), Matchers.anyInt()))
				.thenReturn(subMerchantList);
		merchantValidateServiceImpl.changeMerchantStatus(merchantData);
	}

	@Test
	public void testchangeMerchantStatusSubMerchantSuspended() {
		MerchantData merchantData = new MerchantData();
		PGMerchant pgMerchant = new PGMerchant();
		PGAccount account = new PGAccount();
		List<PGMerchantUsers> pgMerchantUsersList = new ArrayList<>();
		PGMerchantUsers merchantUsers = new PGMerchantUsers();
		pgMerchant.setPgMerchantUsers(pgMerchantUsersList);
		pgMerchant.setMerchantCode("SubMerchant");
		pgMerchantUsersList.add(merchantUsers);
		Mockito.when(merchantProfileDao.getMerchantById(Matchers.anyLong())).thenReturn(pgMerchant);
		Mockito.when(merchantUpdateDao.createOrUpdateAccouunt(Matchers.any(PGAccount.class))).thenReturn(account);
		merchantValidateServiceImpl.changeMerchantStatus(merchantData);
	}

	@Test
	public void testchangeMerchantStatusElse() {
		MerchantData merchantData = new MerchantData();
		PGMerchant pgMerchant = new PGMerchant();
		Mockito.when(merchantProfileDao.getMerchantById(Matchers.anyLong())).thenReturn(pgMerchant);
		merchantValidateServiceImpl.changeMerchantStatus(merchantData);
	}

	@Test
	public void testgetProcessorNames() throws ChatakAdminException {
		List<PGSwitch> processorList = new ArrayList<>();
		List<Option> processorNames = new ArrayList<>();
		Option option = new Option();
		PGSwitch pgSwitch = new PGSwitch();
		processorList.add(pgSwitch);
		processorNames.add(option);
		Mockito.when(switchDao.getAllSwitchNamesByStatus(Matchers.anyInt())).thenReturn(processorList);
		merchantValidateServiceImpl.getProcessorNames();
	}

	@Test
	public void testgetMerchant() throws ChatakAdminException {
		Merchant merchant = new Merchant();
		PGMerchant pgMerchant = new PGMerchant();
		PGAccount pgAccount = new PGAccount();
		PGMerchantBank merchantBank = new PGMerchantBank();
		PGMerchantConfig config = new PGMerchantConfig();
		config.setProcessor("5345");
		config.setShippingAmount(1);
		config.setTaxAmount(1);
		config.setRefunds(1);
		config.setTipAmount(1);
		pgAccount.setPgMerchantBank(merchantBank);
		pgMerchant.setMerchantConfig(config);
		pgMerchant.setMerchantType("Merchant");
		ProgramManager programManager = new ProgramManager();
		programManager.setProgramManagerName("programManagerName");
		Mockito.when(merchantProfileDao.getPgAccount(Matchers.anyString())).thenReturn(pgAccount);
		Mockito.when(merchantProfileDao.getMerchantById(Matchers.anyLong())).thenReturn(pgMerchant);
		Mockito.when(partnerDao.findProgramManagerByPartnerId(Matchers.anyString())).thenReturn(programManager);
		merchantValidateServiceImpl.getMerchant(merchant);
	}
	
	@Test
    public void testGetSubMerchant() throws ChatakAdminException {
        Merchant merchant = new Merchant();
        PGMerchant pgMerchant = new PGMerchant();
        PGAccount pgAccount = new PGAccount();
        PGMerchantBank merchantBank = new PGMerchantBank();
        PGMerchantConfig config = new PGMerchantConfig();
        config.setProcessor("5345");
        config.setShippingAmount(1);
        config.setTaxAmount(1);
        config.setRefunds(1);
        config.setTipAmount(1);
        pgAccount.setPgMerchantBank(merchantBank);
        pgMerchant.setMerchantConfig(config);
        pgMerchant.setPartnerId("4586456");
        pgMerchant.setMerchantType("SubMerchant");
        Partner partner = new Partner();
        partner.setPartnerName("partnerName");
        partner.setProgramManagerId(Long.parseLong("156456"));
        ProgramManagerRequest programManagerRequest = new ProgramManagerRequest();
        programManagerRequest.setProgramManagerName("programManagerName");
        Mockito.when(merchantProfileDao.getPgAccount(Matchers.anyString())).thenReturn(pgAccount);
        Mockito.when(merchantProfileDao.getMerchantById(Matchers.anyLong())).thenReturn(pgMerchant);
        Mockito.when(partnerDao.findByPartnerId(Matchers.anyLong())).thenReturn(partner);
        Mockito.when(programManagerDao.findProgramManagerById(Matchers.anyLong())).thenReturn(programManagerRequest);
        merchantValidateServiceImpl.getMerchant(merchant);
    }

	@Test
	public void testgetMerchantDaily() throws ChatakAdminException {
		Merchant merchant = new Merchant();
		PGMerchant pgMerchant = new PGMerchant();
		PGAccount pgAccount = new PGAccount();
		BigDecimal bigDecimal = new BigDecimal("5345");
		PGMerchantBank merchantBank = new PGMerchantBank();
		PGMerchantConfig config = new PGMerchantConfig();
		config.setProcessor("5345");
		config.setShippingAmount(1);
		config.setTaxAmount(1);
		config.setRefunds(1);
		config.setTipAmount(1);
		pgAccount.setPgMerchantBank(merchantBank);
		pgAccount.setAutoTransferDay("D");
		pgAccount.setAutoPaymentLimit(bigDecimal);
		pgMerchant.setMerchantConfig(config);
		Mockito.when(merchantProfileDao.getPgAccount(Matchers.anyString())).thenReturn(pgAccount);
		Mockito.when(merchantProfileDao.getMerchantById(Matchers.anyLong())).thenReturn(pgMerchant);
		merchantValidateServiceImpl.getMerchant(merchant);
	}

	@Test
	public void testgetMerchantWeekly() throws ChatakAdminException {
		Merchant merchant = new Merchant();
		PGMerchant pgMerchant = new PGMerchant();
		PGAccount pgAccount = new PGAccount();
		PGMerchantBank merchantBank = new PGMerchantBank();
		PGMerchantConfig config = new PGMerchantConfig();
		config.setProcessor("5345");
		config.setShippingAmount(1);
		config.setTaxAmount(1);
		config.setRefunds(1);
		config.setTipAmount(1);
		pgAccount.setPgMerchantBank(merchantBank);
		pgAccount.setAutoTransferDay("W:K");
		pgMerchant.setMerchantConfig(config);
		Mockito.when(merchantProfileDao.getPgAccount(Matchers.anyString())).thenReturn(pgAccount);
		Mockito.when(merchantProfileDao.getMerchantById(Matchers.anyLong())).thenReturn(pgMerchant);
		merchantValidateServiceImpl.getMerchant(merchant);
	}

	@Test
	public void testgetMerchantMonthly() throws ChatakAdminException {
		Merchant merchant = new Merchant();
		PGMerchant pgMerchant = new PGMerchant();
		PGAccount pgAccount = new PGAccount();
		PGMerchantBank merchantBank = new PGMerchantBank();
		PGMerchantConfig config = new PGMerchantConfig();
		config.setProcessor("5345");
		config.setShippingAmount(1);
		config.setTaxAmount(1);
		config.setRefunds(1);
		config.setTipAmount(1);
		pgAccount.setPgMerchantBank(merchantBank);
		pgAccount.setAutoTransferDay("M:K");
		pgMerchant.setMerchantConfig(config);
		Mockito.when(merchantProfileDao.getPgAccount(Matchers.anyString())).thenReturn(pgAccount);
		Mockito.when(merchantProfileDao.getMerchantById(Matchers.anyLong())).thenReturn(pgMerchant);
		merchantValidateServiceImpl.getMerchant(merchant);
	}

	@Test
	public void testgetMerchantpgLegalEntity() throws ChatakAdminException {
		Merchant merchant = new Merchant();
		PGMerchant pgMerchant = new PGMerchant();
		PGLegalEntity pgLegalEntity = new PGLegalEntity();
		PGAccount pgAccount = new PGAccount();
		PGMerchantBank merchantBank = new PGMerchantBank();
		PGMerchantConfig config = new PGMerchantConfig();
		config.setProcessor("5345");
		config.setShippingAmount(1);
		config.setTaxAmount(1);
		config.setRefunds(1);
		config.setTipAmount(1);
		pgAccount.setPgMerchantBank(merchantBank);
		pgAccount.setAutoTransferDay("5435");
		pgLegalEntity.setAnnualCardSale(Long.parseLong("5435"));
		merchant.setLegalAnnualCard("legalAnnualCard");
		pgMerchant.setMerchantConfig(config);
		Mockito.when(merchantProfileDao.getPgAccount(Matchers.anyString())).thenReturn(pgAccount);
		Mockito.when(merchantProfileDao.getMerchantById(Matchers.anyLong())).thenReturn(pgMerchant);
		Mockito.when(legalEntityDao.getLegalEntityByMerchantId(Matchers.anyString())).thenReturn(pgLegalEntity);
		merchantValidateServiceImpl.getMerchant(merchant);
	}

}
