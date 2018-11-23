package com.chatak.acquirer.admin.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.context.MessageSource;

import com.chatak.acquirer.admin.exception.ChatakAdminException;
import com.chatak.acquirer.admin.service.impl.MerchantAccountServiceImpl;
import com.chatak.mailsender.service.MailServiceManagement;
import com.chatak.pg.acq.dao.AccountDao;
import com.chatak.pg.acq.dao.AccountHistoryDao;
import com.chatak.pg.acq.dao.AccountTransactionsDao;
import com.chatak.pg.acq.dao.CurrencyConfigDao;
import com.chatak.pg.acq.dao.CurrencyDao;
import com.chatak.pg.acq.dao.MerchantDao;
import com.chatak.pg.acq.dao.MerchantProfileDao;
import com.chatak.pg.acq.dao.MerchantUpdateDao;
import com.chatak.pg.acq.dao.model.PGAccount;
import com.chatak.pg.acq.dao.model.PGCurrencyConfig;
import com.chatak.pg.acq.dao.model.PGMerchant;
import com.chatak.pg.acq.dao.model.PGMerchantBank;
import com.chatak.pg.acq.dao.model.PGMerchantConfig;
import com.chatak.pg.acq.dao.repository.CurrencyConfigRepository;
import com.chatak.pg.acq.dao.repository.MerchantRepository;
import com.chatak.pg.model.AccountBalanceDTO;
import com.chatak.pg.model.AccountBalanceReportDTO;
import com.chatak.pg.model.Merchant;
import com.chatak.pg.user.bean.MerchantAccountSearchDto;
import com.chatak.prepaid.velocity.IVelocityTemplateCreator;

@RunWith(MockitoJUnitRunner.class)
public class MerchantAccountServiceImplTest {

	@InjectMocks
	MerchantAccountServiceImpl merchantAccountServiceImpl = new MerchantAccountServiceImpl();

	@Mock
	private MerchantDao merchantDao;

	@Mock
	CurrencyDao currencyDao;

	@Mock
	private MessageSource messageSource;

	@Mock
	private AccountDao accountDao;

	@Mock
	MailServiceManagement mailingServiceManagement;

	@Mock
	IVelocityTemplateCreator iVelocityTemplateCreator;

	@Mock
	private AccountHistoryDao accountHistoryDao;

	@Mock
	private AccountTransactionsDao accountTransactionsDao;

	@Mock
	MerchantRepository merchantRepository;

	@Mock
	CurrencyConfigRepository currencyConfigRepository;

	@Mock
	CurrencyConfigDao currencyConfigDao;

	@Mock
	MerchantProfileDao merchantProfileDao;

	@Mock
	MerchantUpdateDao merchantUpdateDao;

	@Test
	public void testGetMerchantAccountDetails() throws ChatakAdminException {
		PGAccount pgAccount = new PGAccount();
		pgAccount.setAccountNum(Long.parseLong("123456"));
		Mockito.when(merchantProfileDao.getPgAccount(Mockito.anyString())).thenReturn(pgAccount);
		merchantAccountServiceImpl.getMerchantAccountDetails("987654321");
	}

	@Test
	public void testGetAllAccountsBalanceReport() throws ChatakAdminException {
		Merchant merchant = new Merchant();
		List<AccountBalanceReportDTO> balanceReportDTOs = new ArrayList<>();
		AccountBalanceReportDTO accountBalanceReportDTO = new AccountBalanceReportDTO();
		balanceReportDTOs.add(accountBalanceReportDTO);
		PGCurrencyConfig pgCurrencyConfig = new PGCurrencyConfig();
		Mockito.when(currencyConfigDao.getCurrencyCodeNumeric(Matchers.anyString())).thenReturn(pgCurrencyConfig);
		Mockito.when(merchantProfileDao.getAllAccountsBalanceReport(Matchers.any(Merchant.class)))
				.thenReturn(balanceReportDTOs);
		merchantAccountServiceImpl.getAllAccountsBalanceReport(merchant);
	}

	@Test
	public void testGetAllAccountsBalanceReportPagination() throws ChatakAdminException {
		Merchant merchant = new Merchant();
		List<AccountBalanceReportDTO> balanceReportDTOs = new ArrayList<>();
		AccountBalanceReportDTO accountBalanceReportDTO = new AccountBalanceReportDTO();
		balanceReportDTOs.add(accountBalanceReportDTO);
		PGCurrencyConfig pgCurrencyConfig = new PGCurrencyConfig();
		Mockito.when(currencyConfigDao.getCurrencyCodeNumeric(Matchers.anyString())).thenReturn(pgCurrencyConfig);
		Mockito.when(merchantProfileDao.getAllAccountsBalanceReport(Matchers.any(Merchant.class)))
				.thenReturn(balanceReportDTOs);
		merchantAccountServiceImpl.getAllAccountsBalanceReportPagination(merchant);
	}

	@Test(expected = ChatakAdminException.class)
	public void testGetAccountBalanceDTO() throws ChatakAdminException {
		PGMerchant pgMerchant = new PGMerchant();
		PGAccount pgAccount = new PGAccount();
		PGCurrencyConfig pgCurrencyConfig = new PGCurrencyConfig();
		pgAccount.setStatus("abcd");
		Mockito.when(merchantUpdateDao.getMerchant(Matchers.anyString())).thenReturn(pgMerchant);
		Mockito.when(accountDao.getPgAccount(Matchers.anyString())).thenReturn(pgAccount);
		Mockito.when(currencyConfigDao.getCurrencyCodeNumeric(Matchers.anyString())).thenReturn(pgCurrencyConfig);
		merchantAccountServiceImpl.getAccountBalanceDTO("abcd", 1l, "abcd");
	}

	@Test
	public void testProcessMerchantAccountBalanceUpdate() {
		AccountBalanceDTO accountBalanceDTO = new AccountBalanceDTO();
		PGMerchant pgMerchant = new PGMerchant();
		PGAccount pgAccount = new PGAccount();
		PGCurrencyConfig pgCurrencyConfig = new PGCurrencyConfig();
		pgMerchant.setStatus(0);
		pgAccount.setAvailableBalance(Long.parseLong("5435"));
		accountBalanceDTO.setInputAmount(Long.parseLong("555"));
		Mockito.when(merchantUpdateDao.getMerchant(Matchers.anyString())).thenReturn(pgMerchant);
		Mockito.when(accountDao.getPgAccount(Matchers.anyString())).thenReturn(pgAccount);
		Mockito.when(currencyConfigDao.getCurrencyCodeNumeric(Matchers.anyString())).thenReturn(pgCurrencyConfig);
		merchantAccountServiceImpl.processMerchantAccountBalanceUpdate(accountBalanceDTO, "Active");
	}

	@Test
	public void testLogPgAccountHistory() {
		PGAccount updatedAccount = new PGAccount();
		Mockito.when(accountDao.getPgAccount(Matchers.anyString())).thenReturn(updatedAccount);
		merchantAccountServiceImpl.logPgAccountHistory("aaaa", "bbb");
	}

	@Test
	public void testGetMerchantConfigDetailsForAccountCreate() {
		Merchant merchant = new Merchant();
		PGMerchant pgMerchant = new PGMerchant();
		PGMerchantConfig pgMerchantConfig = new PGMerchantConfig();
		pgMerchant.setId(Long.parseLong("2"));
		merchant.setMerchantCode("abc");
		pgMerchantConfig.setAutoSettlement(Integer.parseInt("543"));
		pgMerchant.setMerchantConfig(pgMerchantConfig);
		Mockito.when(merchantUpdateDao.getMerchant(Matchers.anyString())).thenReturn(pgMerchant);
		merchantAccountServiceImpl.getMerchantConfigDetailsForAccountCreate(merchant);
	}

	@Test
	public void testCreateMerchantAccountDaily() {
		Merchant merchant = new Merchant();
		merchant.setAutoTransferDay("D");
		merchantAccountServiceImpl.createMerchantAccount(merchant, Long.parseLong("555"));
	}

	@Test
	public void testCreateMerchantAccountWeekly() {
		Merchant merchant = new Merchant();
		merchant.setAutoTransferDay("W");
		merchantAccountServiceImpl.createMerchantAccount(merchant, Long.parseLong("555"));
	}

	@Test
	public void testCreateMerchantAccountMonthly() {
		Merchant merchant = new Merchant();
		BigDecimal bigDecimal = new BigDecimal("5466");
		merchant.setAutoTransferDay("M");
		merchant.setAutoTransferLimit(bigDecimal);
		merchantAccountServiceImpl.createMerchantAccount(merchant, Long.parseLong("555"));
	}

	@Test
	public void testCreateMerchantAccountElse() {
		Merchant merchant = new Merchant();
		merchantAccountServiceImpl.createMerchantAccount(merchant, Long.parseLong("555"));
	}

	@Test
	public void testGetMerchantDetailsForAccountCreation() {
		Merchant merchant = new Merchant();
		List<PGMerchant> pgMerchantsList = new ArrayList<>();
		PGMerchant pgMerchant = new PGMerchant();
		pgMerchant.setStatus(0);
		pgMerchantsList.add(pgMerchant);
		merchant.setPageSize(Integer.parseInt("7565"));
		merchant.setNoOfRecords(Integer.parseInt("32"));
		Mockito.when(merchantDao.getMerchantDetailsForAccountCreation(Matchers.any(Merchant.class)))
				.thenReturn(pgMerchantsList);
		merchantAccountServiceImpl.getMerchantDetailsForAccountCreation(merchant);
	}

	@Test
	public void testGetMerchantDetailsForAccountCreationPending() {
		Merchant merchant = new Merchant();
		List<PGMerchant> pgMerchantsList = new ArrayList<>();
		PGMerchant pgMerchant = new PGMerchant();
		pgMerchant.setStatus(1);
		pgMerchantsList.add(pgMerchant);
		merchant.setPageSize(Integer.parseInt("7565"));
		merchant.setNoOfRecords(Integer.parseInt("32"));
		Mockito.when(merchantDao.getMerchantDetailsForAccountCreation(Matchers.any(Merchant.class)))
				.thenReturn(pgMerchantsList);
		merchantAccountServiceImpl.getMerchantDetailsForAccountCreation(merchant);
	}

	@Test
	public void testGetMerchantDetailsForAccountCreationDeleted() {
		Merchant merchant = new Merchant();
		List<PGMerchant> pgMerchantsList = new ArrayList<>();
		PGMerchant pgMerchant = new PGMerchant();
		pgMerchant.setStatus(Integer.parseInt("3"));
		pgMerchantsList.add(pgMerchant);
		merchant.setPageSize(Integer.parseInt("7565"));
		merchant.setNoOfRecords(Integer.parseInt("32"));
		Mockito.when(merchantDao.getMerchantDetailsForAccountCreation(Matchers.any(Merchant.class)))
				.thenReturn(pgMerchantsList);
		merchantAccountServiceImpl.getMerchantDetailsForAccountCreation(merchant);
	}

	@Test
	public void testGetMerchantDetailsForAccountCreationSuspended() {
		Merchant merchant = new Merchant();
		List<PGMerchant> pgMerchantsList = new ArrayList<>();
		PGMerchant pgMerchant = new PGMerchant();
		pgMerchant.setStatus(Integer.parseInt("3"));
		pgMerchantsList.add(pgMerchant);
		merchant.setPageSize(Integer.parseInt("7565"));
		merchant.setNoOfRecords(Integer.parseInt("32"));
		Mockito.when(merchantDao.getMerchantDetailsForAccountCreation(Matchers.any(Merchant.class)))
				.thenReturn(pgMerchantsList);
		merchantAccountServiceImpl.getMerchantDetailsForAccountCreation(merchant);
	}

	@Test
	public void testGetMerchantDetailsForAccountCreationSelfRegistration() {
		Merchant merchant = new Merchant();
		List<PGMerchant> pgMerchantsList = new ArrayList<>();
		PGMerchant pgMerchant = new PGMerchant();
		pgMerchant.setStatus(Integer.parseInt("5"));
		pgMerchantsList.add(pgMerchant);
		merchant.setPageSize(Integer.parseInt("7565"));
		merchant.setNoOfRecords(Integer.parseInt("32"));
		Mockito.when(merchantDao.getMerchantDetailsForAccountCreation(Matchers.any(Merchant.class)))
				.thenReturn(pgMerchantsList);
		merchantAccountServiceImpl.getMerchantDetailsForAccountCreation(merchant);
	}

	@Test
	public void testSearchMerchantAccount() {
		Map<String, String> merchantDataMap = new HashMap<>();
		List<MerchantAccountSearchDto> merchantAccountSearchDtoList = new ArrayList<>();
		MerchantAccountSearchDto merchantAccountSearchDto = new MerchantAccountSearchDto();
		merchantAccountSearchDto.setMerchantType("SubMerchant");
		merchantAccountSearchDtoList.add(merchantAccountSearchDto);
		merchantAccountServiceImpl.searchMerchantAccount(merchantAccountSearchDto, merchantDataMap);
	}

	@Test
	public void testChangeAccountStatus() {
		MerchantAccountSearchDto merchantAccountSearchDto = new MerchantAccountSearchDto();
		merchantAccountServiceImpl.changeAccountStatus(merchantAccountSearchDto);
	}

	@Test
	public void testGetAccount() {
		Merchant reqMerchant = new Merchant();
		PGAccount pgAccount = new PGAccount();
		reqMerchant.setAccountId(Long.parseLong("654"));
		PGMerchantBank bank = new PGMerchantBank();
		bank.setAccountType("active");
		pgAccount.setPgMerchantBank(bank);
		pgAccount.setAutoTransferDay("D");
		Mockito.when(accountDao.getAccountOnId(Matchers.anyLong())).thenReturn(pgAccount);
		merchantAccountServiceImpl.getAccount(reqMerchant);
	}

	@Test(expected = ArrayIndexOutOfBoundsException.class)
	public void testGetAccountWeekly() {
		Merchant reqMerchant = new Merchant();
		PGAccount pgAccount = new PGAccount();
		reqMerchant.setAccountId(Long.parseLong("654"));
		PGMerchantBank bank = new PGMerchantBank();
		bank.setAccountType("active");
		pgAccount.setPgMerchantBank(bank);
		pgAccount.setAutoTransferDay("W");
		Mockito.when(accountDao.getAccountOnId(Matchers.anyLong())).thenReturn(pgAccount);
		merchantAccountServiceImpl.getAccount(reqMerchant);
	}

	@Test
	public void testUpdateMerchantAccount() {
		Merchant merchant = new Merchant();
		PGAccount pgAccount = new PGAccount();
		PGMerchant pgMerchant = new PGMerchant();
		PGMerchantConfig pgMerchantConfig = new PGMerchantConfig();
		merchant.setAutoTransferDay("D");
		Mockito.when(merchantUpdateDao.getMerchantByCode(Matchers.anyString())).thenReturn(pgMerchant);
		Mockito.when(accountDao.getAccountOnId(Matchers.anyLong())).thenReturn(pgAccount);
		Mockito.when(merchantUpdateDao.getConfigDetails(Matchers.any(PGMerchantConfig.class)))
				.thenReturn(pgMerchantConfig);
		merchantAccountServiceImpl.updateMerchantAccount(merchant);
	}

	@Test
	public void testUpdateMerchantAccountWeekly() {
		Merchant merchant = new Merchant();
		PGAccount pgAccount = new PGAccount();
		PGMerchant pgMerchant = new PGMerchant();
		PGMerchantConfig pgMerchantConfig = new PGMerchantConfig();
		merchant.setAutoTransferDay("W");
		Mockito.when(merchantUpdateDao.getMerchantByCode(Matchers.anyString())).thenReturn(pgMerchant);
		Mockito.when(accountDao.getAccountOnId(Matchers.anyLong())).thenReturn(pgAccount);
		Mockito.when(merchantUpdateDao.getConfigDetails(Matchers.any(PGMerchantConfig.class)))
				.thenReturn(pgMerchantConfig);
		merchantAccountServiceImpl.updateMerchantAccount(merchant);
	}

	@Test
	public void testUpdateMerchantAccountMonthly() {
		Merchant merchant = new Merchant();
		PGAccount pgAccount = new PGAccount();
		PGMerchant pgMerchant = new PGMerchant();
		PGMerchantConfig pgMerchantConfig = new PGMerchantConfig();
		merchant.setAutoTransferDay("M");
		Mockito.when(merchantUpdateDao.getMerchantByCode(Matchers.anyString())).thenReturn(pgMerchant);
		Mockito.when(accountDao.getAccountOnId(Matchers.anyLong())).thenReturn(pgAccount);
		Mockito.when(merchantUpdateDao.getConfigDetails(Matchers.any(PGMerchantConfig.class)))
				.thenReturn(pgMerchantConfig);
		merchantAccountServiceImpl.updateMerchantAccount(merchant);
	}

	@Test
	public void testLogManualAccountTransactionManualCredit() {
		PGAccount account = new PGAccount();
		account.setAccountNum(Long.parseLong("4234"));
		AccountBalanceDTO accountBalanceDTO = getAccountBalanceDTO();
		merchantAccountServiceImpl.logManualAccountTransaction(account, Long.parseLong("423"), "MANUAL_CREDIT",
				accountBalanceDTO);
	}

	private AccountBalanceDTO getAccountBalanceDTO() {
		AccountBalanceDTO accountBalanceDTO = new AccountBalanceDTO();
		accountBalanceDTO.setTimeZoneOffset("timeZoneOffset");
		accountBalanceDTO.setTimeZoneRegion("timeZoneRegion");
		accountBalanceDTO.setCurrencySeparatorPosition(2);
		accountBalanceDTO.setCurrencyThousandsUnit(',');
		accountBalanceDTO.setCurrencyExponent(2);
		accountBalanceDTO.setCurrencyMinorUnit('.');
		return accountBalanceDTO;
	}

	@Test
	public void testLogManualAccountTransactionManualDebit() {
		PGAccount account = new PGAccount();
		AccountBalanceDTO accountBalanceDTO = getAccountBalanceDTO();
		account.setAccountNum(Long.parseLong("4234"));
		merchantAccountServiceImpl.logManualAccountTransaction(account, Long.parseLong("423"), "MANUAL_DEBIT",
				accountBalanceDTO);
	}

	@Test
	public void testLogManualAccountTransactionDefault() {
		PGAccount account = new PGAccount();
		AccountBalanceDTO accountBalanceDTO = new AccountBalanceDTO();
		account.setAccountNum(Long.parseLong("4234"));
		merchantAccountServiceImpl.logManualAccountTransaction(account, Long.parseLong("423"), "Default",
				accountBalanceDTO);
	}

	@Test
	public void testGetMerchantMapByMerchantType() {
		List<Map<String, String>> merchantList = new ArrayList<>();
		Map<String, String> map = new HashMap<>();
		merchantList.add(map);
		map.put("key", "xyz");
		Mockito.when(merchantDao.getMerchantMapByMerchantType(Matchers.anyString())).thenReturn(merchantList);
		merchantAccountServiceImpl.getMerchantMapByMerchantType("Default");

	}

}
