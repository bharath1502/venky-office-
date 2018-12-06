package com.chatak.acquirer.admin.service;

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

import com.chatak.acquirer.admin.exception.ChatakAdminException;
import com.chatak.acquirer.admin.model.CurrenyValue;
import com.chatak.acquirer.admin.service.impl.CurrencyConfigServiceImpl;
import com.chatak.pg.acq.dao.CurrencyConfigDao;
import com.chatak.pg.acq.dao.CurrencyDao;
import com.chatak.pg.acq.dao.model.PGBank;
import com.chatak.pg.acq.dao.model.PGCurrencyCode;
import com.chatak.pg.acq.dao.model.PGCurrencyConfig;
import com.chatak.pg.acq.dao.model.PGMerchant;
import com.chatak.pg.acq.dao.repository.BankRepository;
import com.chatak.pg.acq.dao.repository.MerchantRepository;
import com.chatak.pg.model.CurrencyDTO;
import com.chatak.pg.user.bean.CurrencyDTOList;

@RunWith(MockitoJUnitRunner.class)
public class CurrencyConfigServiceImplTest {

	@InjectMocks
	CurrencyConfigServiceImpl currencyConfigServiceImpl = new CurrencyConfigServiceImpl();

	@Mock
	CurrencyConfigDao currencyConfigDao;

	@Mock
	CurrencyDao currencyDao;

	@Mock
	BankRepository bankRepository;

	@Mock
	MerchantRepository merchantRepository;

	@Mock
	MessageSource messageSource;

	@Test
	public void testSaveCurrencyConfig() throws ChatakAdminException {
		CurrencyDTO currencyDTO = new CurrencyDTO();
		PGCurrencyConfig pgCurrencyConfig = new PGCurrencyConfig();
		currencyDTO.setCurrencyName("r");
		currencyDTO.setCurrencyCodeAlpha("135");
		Mockito.when(currencyConfigDao.saveCurrencyConfig(Matchers.any(PGCurrencyConfig.class)))
				.thenReturn(pgCurrencyConfig);
		currencyConfigServiceImpl.saveCurrencyConfig(currencyDTO);
	}

	@Test
	public void testSaveCurrencyConfigElse() throws ChatakAdminException {
		CurrencyDTO currencyDTO = new CurrencyDTO();
		currencyDTO.setCurrencyName("r");
		currencyDTO.setCurrencyCodeAlpha("135");
		currencyConfigServiceImpl.saveCurrencyConfig(currencyDTO);
	}

	@Test(expected = ChatakAdminException.class)
	public void testSaveCurrencyConfigException() throws ChatakAdminException {
		CurrencyDTO currencyDTO = new CurrencyDTO();
		Mockito.when(currencyConfigDao.saveCurrencyConfig(Matchers.any(PGCurrencyConfig.class)))
				.thenThrow(ChatakAdminException.class);
		currencyConfigServiceImpl.saveCurrencyConfig(currencyDTO);
	}

	@Test
	public void testSearchCurrencyConfigStatusSuccess() throws ChatakAdminException {
		List<CurrenyValue> currencyDTOs = new ArrayList<>();
		CurrenyValue currenyValue = new CurrenyValue();
		List<PGCurrencyConfig> pgCurrencyConfigList = new ArrayList<>();
		PGCurrencyConfig pgCurrencyConfig = new PGCurrencyConfig();
		pgCurrencyConfigList.add(pgCurrencyConfig);
		currencyDTOs.add(currenyValue);
		pgCurrencyConfig.setStatus(0);
		CurrencyDTO currencyDTO = new CurrencyDTO();
		Mockito.when(currencyConfigDao.searchCurrencyConfig(Matchers.any(CurrencyDTO.class)))
				.thenReturn(pgCurrencyConfigList);
		currencyConfigServiceImpl.searchCurrencyConfig(currencyDTO);
	}

	@Test
	public void testSearchCurrencyConfigStatusPending() throws ChatakAdminException {
		List<CurrenyValue> currencyDTOs = new ArrayList<>();
		CurrenyValue currenyValue = new CurrenyValue();
		List<PGCurrencyConfig> pgCurrencyConfigList = new ArrayList<>();
		PGCurrencyConfig pgCurrencyConfig = new PGCurrencyConfig();
		pgCurrencyConfigList.add(pgCurrencyConfig);
		currencyDTOs.add(currenyValue);
		pgCurrencyConfig.setStatus(1);
		CurrencyDTO currencyDTO = new CurrencyDTO();
		Mockito.when(currencyConfigDao.searchCurrencyConfig(Matchers.any(CurrencyDTO.class)))
				.thenReturn(pgCurrencyConfigList);
		currencyConfigServiceImpl.searchCurrencyConfig(currencyDTO);
	}

	@Test
	public void testSearchCurrencyConfigStatusDeleted() throws ChatakAdminException {
		List<CurrenyValue> currencyDTOs = new ArrayList<>();
		CurrenyValue currenyValue = new CurrenyValue();
		List<PGCurrencyConfig> pgCurrencyConfigList = new ArrayList<>();
		PGCurrencyConfig pgCurrencyConfig = new PGCurrencyConfig();
		pgCurrencyConfigList.add(pgCurrencyConfig);
		currencyDTOs.add(currenyValue);
		pgCurrencyConfig.setStatus(Integer.parseInt("3"));
		CurrencyDTO currencyDTO = new CurrencyDTO();
		Mockito.when(currencyConfigDao.searchCurrencyConfig(Matchers.any(CurrencyDTO.class)))
				.thenReturn(pgCurrencyConfigList);
		currencyConfigServiceImpl.searchCurrencyConfig(currencyDTO);
	}

	@Test
	public void testSearchCurrencyConfigStatusSuspended() throws ChatakAdminException {
		List<CurrenyValue> currencyDTOs = new ArrayList<>();
		CurrenyValue currenyValue = new CurrenyValue();
		List<PGCurrencyConfig> pgCurrencyConfigList = new ArrayList<>();
		PGCurrencyConfig pgCurrencyConfig = new PGCurrencyConfig();
		pgCurrencyConfigList.add(pgCurrencyConfig);
		currencyDTOs.add(currenyValue);
		pgCurrencyConfig.setStatus(Integer.parseInt("2"));
		CurrencyDTO currencyDTO = new CurrencyDTO();
		Mockito.when(currencyConfigDao.searchCurrencyConfig(Matchers.any(CurrencyDTO.class)))
				.thenReturn(pgCurrencyConfigList);
		currencyConfigServiceImpl.searchCurrencyConfig(currencyDTO);
	}

	@Test
	public void testGetCurrencyConfigById() throws ChatakAdminException {
		PGCurrencyConfig currencyConfig = new PGCurrencyConfig();
		Mockito.when(currencyConfigDao.findByCurrencyConfigId(Matchers.anyLong())).thenReturn(currencyConfig);
		currencyConfigServiceImpl.getCurrencyConfigById(Long.parseLong("543"));
	}

	@Test
	public void testGetCurrencyConfigByIdNull() throws ChatakAdminException {
		currencyConfigServiceImpl.getCurrencyConfigById(Long.parseLong("543"));
	}

	@Test
	public void testUpdateCurrencyConfig() throws ChatakAdminException {
		CurrencyDTO currencyDTO = new CurrencyDTO();
		PGCurrencyConfig currencyConfig = new PGCurrencyConfig();
		currencyDTO.setId(Long.parseLong("534"));
		currencyDTO.setCurrencyName("r");
		currencyDTO.setCurrencyCodeAlpha("135");
		Mockito.when(currencyConfigDao.findByCurrencyConfigId(Matchers.anyLong())).thenReturn(currencyConfig);
		Mockito.when(currencyConfigDao.updateCurrencyConfig(Matchers.any(PGCurrencyConfig.class)))
				.thenReturn(currencyConfig);
		currencyConfigServiceImpl.updateCurrencyConfig(currencyDTO);
	}

	@Test
	public void testUpdateCurrencyConfigElse() throws ChatakAdminException {
		CurrencyDTO currencyDTO = new CurrencyDTO();
		PGCurrencyConfig currencyConfig = new PGCurrencyConfig();
		currencyDTO.setId(Long.parseLong("534"));
		currencyDTO.setCurrencyName("r");
		currencyDTO.setCurrencyCodeAlpha("135");
		Mockito.when(currencyConfigDao.findByCurrencyConfigId(Matchers.anyLong())).thenReturn(currencyConfig);
		currencyConfigServiceImpl.updateCurrencyConfig(currencyDTO);
	}

	@Test(expected = ChatakAdminException.class)
	public void testUpdateCurrencyConfigException() throws ChatakAdminException {
		CurrencyDTO currencyDTO = new CurrencyDTO();
		Mockito.when(currencyConfigDao.findByCurrencyConfigId(Matchers.anyLong()))
				.thenThrow(new NullPointerException());
		currencyConfigServiceImpl.updateCurrencyConfig(currencyDTO);
	}

	@Test
	public void testDeleteCurrencyConfig() throws ChatakAdminException {
		PGCurrencyConfig pgCurrencyConfig = new PGCurrencyConfig();
		List<PGBank> pgBanks = new ArrayList<>();
		PGBank bank = new PGBank();
		pgBanks.add(bank);
		List<PGMerchant> pgMerchant = new ArrayList<>();
		PGMerchant merchant = new PGMerchant();
		pgMerchant.add(merchant);
		pgCurrencyConfig.setCurrencyCodeAlpha("2354");
		Mockito.when(currencyConfigDao.findByCurrencyConfigId(Matchers.anyLong())).thenReturn(pgCurrencyConfig);
		Mockito.when(bankRepository.findByCurrencyIdAndStatusLike(Matchers.anyLong(), Matchers.anyString()))
				.thenReturn(pgBanks);
		currencyConfigServiceImpl.deleteCurrencyConfig(Long.parseLong("543"));
	}

	@Test
	public void testDeleteCurrencyConfigElse() throws ChatakAdminException {
		PGCurrencyConfig pgCurrencyConfig = new PGCurrencyConfig();
		List<PGBank> pgBanks = new ArrayList<>();
		PGBank bank = new PGBank();
		pgBanks.add(bank);
		List<PGMerchant> pgMerchant = new ArrayList<>();
		PGMerchant merchant = new PGMerchant();
		pgMerchant.add(merchant);
		pgCurrencyConfig.setCurrencyCodeAlpha("2354");
		Mockito.when(currencyConfigDao.findByCurrencyConfigId(Matchers.anyLong())).thenReturn(pgCurrencyConfig);
		Mockito.when(merchantRepository.getMerchantByLocalCurrency(Matchers.anyString())).thenReturn(pgMerchant);
		currencyConfigServiceImpl.deleteCurrencyConfig(Long.parseLong("543"));
	}

	@Test
	public void testDeleteCurrencyConfigNull() throws ChatakAdminException {
		PGCurrencyConfig pgCurrencyConfig = new PGCurrencyConfig();
		List<PGBank> pgBanks = new ArrayList<>();
		PGBank bank = new PGBank();
		pgBanks.add(bank);
		List<PGMerchant> pgMerchant = new ArrayList<>();
		PGMerchant merchant = new PGMerchant();
		pgMerchant.add(merchant);
		pgCurrencyConfig.setCurrencyCodeAlpha("2354");
		Mockito.when(currencyConfigDao.findByCurrencyConfigId(Matchers.anyLong())).thenReturn(pgCurrencyConfig);
		Mockito.when(currencyConfigDao.updateCurrencyConfig(Matchers.any(PGCurrencyConfig.class)))
				.thenReturn(pgCurrencyConfig);
		currencyConfigServiceImpl.deleteCurrencyConfig(Long.parseLong("543"));
	}

	@Test(expected = ChatakAdminException.class)
	public void testDeleteCurrencyConfigException() throws ChatakAdminException {
		Mockito.when(currencyConfigDao.findByCurrencyConfigId(Matchers.anyLong()))
				.thenThrow(new NullPointerException());
		currencyConfigServiceImpl.deleteCurrencyConfig(Long.parseLong("543"));
	}

	@Test
	public void testGetCurrencyConfigCode() {
		CurrencyDTOList currencyDTOList = new CurrencyDTOList();
		List<PGCurrencyConfig> configs = new ArrayList<>();
		PGCurrencyConfig pgCurrencyConfig = new PGCurrencyConfig();
		configs.add(pgCurrencyConfig);
		currencyDTOList.setPgCurrencyDTOLists(configs);
		Mockito.when(currencyConfigDao.getActiveCurrencyConfigCode()).thenReturn(currencyDTOList);
		currencyConfigServiceImpl.getCurrencyConfigCode();
	}

	@Test
	public void testGetCurrencyCodeNumeric() {
		PGCurrencyConfig pgCurrencyConfig = new PGCurrencyConfig();
		Mockito.when(currencyConfigDao.getCurrencyCodeNumeric(Matchers.anyString())).thenReturn(pgCurrencyConfig);
		currencyConfigServiceImpl.getCurrencyCodeNumeric("234");
	}

	@Test
	public void testGetcurrencyCodeAlpha() {
		PGCurrencyConfig pgCurrencyConfig = new PGCurrencyConfig();
		pgCurrencyConfig.setCurrencyCodeAlpha("135");
		Mockito.when(currencyConfigDao.getcurrencyCodeAlpha(Matchers.anyString())).thenReturn(pgCurrencyConfig);
		currencyConfigServiceImpl.getcurrencyCodeAlpha("234");
	}

	@Test
	public void testFindByCurrencyName() {
		PGCurrencyCode pgCurrencyCode = new PGCurrencyCode();
		pgCurrencyCode.setCurrencyCodeAlpha("135");
		Mockito.when(currencyDao.findByCurrencyName(Matchers.anyString())).thenReturn(pgCurrencyCode);
		currencyConfigServiceImpl.findByCurrencyName("234");
	}

	@Test
	public void testGetAllCurrencies() {
		List<PGCurrencyCode> pgCurrencyCode = new ArrayList<>();
		PGCurrencyCode code = new PGCurrencyCode();
		pgCurrencyCode.add(code);
		Mockito.when(currencyDao.findCurrencies()).thenReturn(pgCurrencyCode);
		currencyConfigServiceImpl.getAllCurrencies();
	}

	@Test
	public void testChangeCurrencyStatus() {
		CurrencyDTO currencyDTO = new CurrencyDTO();
		PGCurrencyConfig pgCurrencyConfig = new PGCurrencyConfig();
		currencyDTO.setId(Long.parseLong("432"));
		Mockito.when(currencyConfigDao.findByCurrencyConfigId(Matchers.anyLong())).thenReturn(pgCurrencyConfig);
		currencyConfigServiceImpl.changeCurrencyStatus(currencyDTO, "Active");
	}

	@Test
	public void testChangeCurrencyStatusSuspended() {
		CurrencyDTO currencyDTO = new CurrencyDTO();
		PGCurrencyConfig pgCurrencyConfig = new PGCurrencyConfig();
		currencyDTO.setId(Long.parseLong("432"));
		Mockito.when(currencyConfigDao.findByCurrencyConfigId(Matchers.anyLong())).thenReturn(pgCurrencyConfig);
		currencyConfigServiceImpl.changeCurrencyStatus(currencyDTO, "Suspended");
	}

}
