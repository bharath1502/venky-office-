package com.chatak.acquirer.admin.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import com.chatak.acquirer.admin.exception.ChatakAdminException;
import com.chatak.acquirer.admin.service.impl.MerchantUpdateServiceImpl;
import com.chatak.mailsender.service.MailServiceManagement;
import com.chatak.pg.acq.dao.CountryDao;
import com.chatak.pg.acq.dao.CurrencyConfigDao;
import com.chatak.pg.acq.dao.CurrencyDao;
import com.chatak.pg.acq.dao.MerchantDao;
import com.chatak.pg.acq.dao.MerchantProfileDao;
import com.chatak.pg.acq.dao.MerchantUpdateDao;
import com.chatak.pg.acq.dao.SubMerchantDao;
import com.chatak.pg.acq.dao.model.PGMerchant;
import com.chatak.pg.acq.dao.model.PGMerchantCurrencyMapping;
import com.chatak.pg.acq.dao.model.PGState;
import com.chatak.pg.acq.dao.repository.CurrencyConfigRepository;
import com.chatak.pg.acq.dao.repository.MerchantRepository;
import com.chatak.pg.bean.CountryRequest;
import com.chatak.pg.bean.CurrencyRequest;
import com.chatak.pg.model.Merchant;
import com.chatak.pg.user.bean.GetMerchantListRequest;
import com.chatak.pg.user.bean.GetMerchantListResponse;
import com.chatak.pg.util.Properties;
import com.chatak.prepaid.velocity.IVelocityTemplateCreator;

@RunWith(MockitoJUnitRunner.class)
public class MerchantUpdateServiceImplTest {

	@InjectMocks
	MerchantUpdateServiceImpl merchantUpdateServiceImpl = new MerchantUpdateServiceImpl();

	@Mock
	private MerchantDao merchantDao;

	@Mock
	CurrencyDao currencyDao;

	@Mock
	private CountryDao countryDao;

	@Mock
	MailServiceManagement mailingServiceManagement;

	@Mock
	IVelocityTemplateCreator iVelocityTemplateCreator;

	@Mock
	MerchantRepository merchantRepository;

	@Mock
	CurrencyConfigRepository currencyConfigRepository;

	@Mock
	CurrencyConfigDao currencyConfigDao;

	@Mock
	SubMerchantDao subMerchantDao;

	@Mock
	MerchantProfileDao merchantProfileDao;

	@Mock
	MerchantUpdateDao merchantUpdateDao;

	@Before
    public void init() {
      java.util.Properties propsExportedLocal = new java.util.Properties();
      propsExportedLocal.setProperty("thread.pool.size", "500");
      propsExportedLocal.setProperty("thread.max.per.route", "500");
      Properties.mergeProperties(propsExportedLocal);
    }

	@Test(expected = NullPointerException.class)
	public void testSearchMerchant() throws ChatakAdminException {
		Merchant merchant = new Merchant();
		GetMerchantListResponse getMerchantListResponse = new GetMerchantListResponse();
		List<PGMerchant> pgMerchants = new ArrayList<>();
		List<PGMerchant> subMerchants = new ArrayList<>();
		PGMerchant pgMerchant = new PGMerchant();
		merchant.setSubMerchantCode("5454");
		getMerchantListResponse.setMerchants(pgMerchants);
		getMerchantListResponse.setSubMerchants(pgMerchants);
		pgMerchants.add(pgMerchant);
		subMerchants.add(pgMerchant);
		Mockito.when(subMerchantDao.getMerchantlistOnSubMerchantCode(Matchers.any(GetMerchantListRequest.class)))
				.thenReturn(getMerchantListResponse);
		merchantUpdateServiceImpl.searchMerchant(merchant, "Admin", 1l);
	}

	@Test(expected = NullPointerException.class)
	public void testSearchMerchantElse() throws ChatakAdminException {
		Merchant merchant = new Merchant();
		GetMerchantListResponse getMerchantListResponse = new GetMerchantListResponse();
		List<PGMerchant> pgMerchants = new ArrayList<>();
		PGMerchant pgMerchant = new PGMerchant();
		getMerchantListResponse.setMerchants(pgMerchants);
		pgMerchants.add(pgMerchant);
		merchantUpdateServiceImpl.searchMerchant(merchant, "Admin", 1l);
	}

	@Test
	public void testchangeMerchantStatus() throws ChatakAdminException {
		Merchant merchant = new Merchant();
		merchantUpdateServiceImpl.changeMerchantStatus(merchant, 1);
	}

	@Test
	public void testGetActiveMerchants() throws ChatakAdminException {
		merchantUpdateServiceImpl.getActiveMerchants();
	}

	@Test
	public void testDeleteMerchant() throws ChatakAdminException {
		merchantUpdateServiceImpl.deleteMerchant(Long.parseLong("435"));
	}

	@Test
	public void testGetStatesByCountry() throws ChatakAdminException {
		CountryRequest countryRequest = new CountryRequest();
		List<PGState> pgStates = new ArrayList<>();
		PGState pgState = new PGState();
		pgStates.add(pgState);
		countryRequest.setName("abcd");
		Mockito.when(countryDao.getCountryByName(Matchers.anyString())).thenReturn(countryRequest);
		Mockito.when(merchantProfileDao.getStateByCountryId(Matchers.anyLong())).thenReturn(pgStates);
		merchantUpdateServiceImpl.getStatesByCountry("abcde");
	}

	@Test
	public void testGetStatesByCountryElse() throws ChatakAdminException {
		CountryRequest countryRequest = new CountryRequest();
		Mockito.when(countryDao.getCountryByName(Matchers.anyString())).thenReturn(countryRequest);
		merchantUpdateServiceImpl.getStatesByCountry("abcde");
	}

	@Test
	public void testGetCountries() throws ChatakAdminException {
		List<CountryRequest> countryRequests = new ArrayList<>();
		CountryRequest countryRequest = new CountryRequest();
		countryRequests.add(countryRequest);
		Mockito.when(countryDao.findAllCountries()).thenReturn(countryRequests);
		merchantUpdateServiceImpl.getCountries();
	}

	@Test
	public void testSearchSubMerchantList() throws ChatakAdminException {
		Merchant merchant = new Merchant();
		GetMerchantListResponse getMerchantListResponse = new GetMerchantListResponse();
		List<PGMerchant> pgMerchants = new ArrayList<>();
		PGMerchant pgMerchant = new PGMerchant();
		getMerchantListResponse.setSubMerchants(pgMerchants);
		pgMerchant.setStatus(0);
		pgMerchants.add(pgMerchant);
		Mockito.when(subMerchantDao.getSubMerchantListOnMerchantId(Matchers.any(GetMerchantListRequest.class)))
				.thenReturn(getMerchantListResponse);
		merchantUpdateServiceImpl.searchSubMerchantList(merchant);
	}

	@Test
	public void testSearchAllMerchant() throws ChatakAdminException {
		Merchant merchant = new Merchant();
		GetMerchantListResponse getMerchantListResponse = new GetMerchantListResponse();
		List<PGMerchant> pgMerchants = new ArrayList<>();
		PGMerchant pgMerchant = new PGMerchant();
		getMerchantListResponse.setMerchants(pgMerchants);
		pgMerchant.setStatus(0);
		pgMerchants.add(pgMerchant);
		Mockito.when(merchantProfileDao.getAllMerchantlist(Matchers.any(GetMerchantListRequest.class)))
				.thenReturn(getMerchantListResponse);
		merchantUpdateServiceImpl.searchAllMerchant(merchant);
	}

	@Test
	public void testSearchAllMerchantStatusSuccess() throws ChatakAdminException {
		Merchant merchant = new Merchant();
		GetMerchantListResponse getMerchantListResponse = new GetMerchantListResponse();
		List<PGMerchant> pgMerchants = new ArrayList<>();
		PGMerchant pgMerchant = new PGMerchant();
		getMerchantListResponse.setMerchants(pgMerchants);
		pgMerchant.setStatus(0);
		pgMerchants.add(pgMerchant);
		Mockito.when(merchantProfileDao.getAllMerchantlist(Matchers.any(GetMerchantListRequest.class)))
				.thenReturn(getMerchantListResponse);
		merchantUpdateServiceImpl.searchAllMerchant(merchant);
	}

	@Test
	public void testSearchAllMerchantStatusDeleted() throws ChatakAdminException {
		Merchant merchant = new Merchant();
		GetMerchantListResponse getMerchantListResponse = new GetMerchantListResponse();
		List<PGMerchant> pgMerchants = new ArrayList<>();
		PGMerchant pgMerchant = new PGMerchant();
		getMerchantListResponse.setMerchants(pgMerchants);
		pgMerchant.setStatus(Integer.parseInt("3"));
		pgMerchants.add(pgMerchant);
		Mockito.when(merchantProfileDao.getAllMerchantlist(Matchers.any(GetMerchantListRequest.class)))
				.thenReturn(getMerchantListResponse);
		merchantUpdateServiceImpl.searchAllMerchant(merchant);
	}

	@Test
	public void testSearchAllMerchantStatusPending() throws ChatakAdminException {
		Merchant merchant = new Merchant();
		GetMerchantListResponse getMerchantListResponse = new GetMerchantListResponse();
		List<PGMerchant> pgMerchants = new ArrayList<>();
		PGMerchant pgMerchant = new PGMerchant();
		getMerchantListResponse.setMerchants(pgMerchants);
		pgMerchant.setStatus(1);
		pgMerchants.add(pgMerchant);
		Mockito.when(merchantProfileDao.getAllMerchantlist(Matchers.any(GetMerchantListRequest.class)))
				.thenReturn(getMerchantListResponse);
		merchantUpdateServiceImpl.searchAllMerchant(merchant);
	}

	@Test
	public void testSearchAllMerchantStatusSelfRegistration() throws ChatakAdminException {
		Merchant merchant = new Merchant();
		GetMerchantListResponse getMerchantListResponse = new GetMerchantListResponse();
		List<PGMerchant> pgMerchants = new ArrayList<>();
		PGMerchant pgMerchant = new PGMerchant();
		getMerchantListResponse.setMerchants(pgMerchants);
		pgMerchant.setStatus(Integer.parseInt("5"));
		pgMerchants.add(pgMerchant);
		Mockito.when(merchantProfileDao.getAllMerchantlist(Matchers.any(GetMerchantListRequest.class)))
				.thenReturn(getMerchantListResponse);
		merchantUpdateServiceImpl.searchAllMerchant(merchant);
	}

	@Test
	public void testSearchAllMerchantStatusSuspended() throws ChatakAdminException {
		Merchant merchant = new Merchant();
		GetMerchantListResponse getMerchantListResponse = new GetMerchantListResponse();
		List<PGMerchant> pgMerchants = new ArrayList<>();
		PGMerchant pgMerchant = new PGMerchant();
		getMerchantListResponse.setMerchants(pgMerchants);
		pgMerchant.setStatus(Integer.parseInt("2"));
		pgMerchants.add(pgMerchant);
		Mockito.when(merchantProfileDao.getAllMerchantlist(Matchers.any(GetMerchantListRequest.class)))
				.thenReturn(getMerchantListResponse);
		merchantUpdateServiceImpl.searchAllMerchant(merchant);
	}

	@Test
	public void testGetTotalNumberOfRecords() throws ChatakAdminException {
		GetMerchantListRequest searchMerchant = new GetMerchantListRequest();
		merchantUpdateServiceImpl.getTotalNumberOfRecords(searchMerchant);
	}

	@Test
	public void testGetMerchantNameByMerchantCodeAsMap() throws ChatakAdminException {
		List<Map<String, String>> merchantList = new ArrayList<>();
		Map<String, String> map = new HashMap<>();
		merchantList.add(map);
		Mockito.when(merchantDao.getMerchantNamesAndMerchantCode()).thenReturn(merchantList);
		merchantUpdateServiceImpl.getMerchantNameByMerchantCodeAsMap();
	}

	@Test
	public void testUpdateSubMerchantsPartnerId() throws ChatakAdminException {
		merchantUpdateServiceImpl.updateSubMerchantsPartnerId("123");
	}

	@Test
	public void testGetMerchantNameAndMerchantCodeAsMapByMerchantType() throws ChatakAdminException {
		List<Map<String, String>> merchantList = new ArrayList<>();
		Map<String, String> map = new HashMap<>();
		merchantList.add(map);
		Mockito.when(merchantDao.getMerchantNameAndMerchantCodeByMerchantType(Matchers.anyString()))
				.thenReturn(merchantList);
		merchantUpdateServiceImpl.getMerchantNameAndMerchantCodeAsMapByMerchantType("423");
	}

	@Test
	public void testGetMerchantIdOnMerchantCode() throws ChatakAdminException {
		merchantUpdateServiceImpl.getMerchantIdOnMerchantCode(Long.parseLong("534"));
	}

	@Test
	public void testSearchSubMerchants() throws ChatakAdminException {
		Merchant merchant = new Merchant();
		GetMerchantListResponse getMerchantListResponse = new GetMerchantListResponse();
		List<PGMerchant> pgMerchants = new ArrayList<>();
		PGMerchant pgMerchant = new PGMerchant();
		getMerchantListResponse.setSubMerchants(pgMerchants);
		pgMerchants.add(pgMerchant);
		Mockito.when(subMerchantDao.getSubMerchantList(Matchers.any(GetMerchantListRequest.class)))
				.thenReturn(getMerchantListResponse);
		merchantUpdateServiceImpl.searchSubMerchants(merchant, "Admin", 1l);
	}

	@Test
	public void testGetMerchantCodeAndCompanyName() throws ChatakAdminException {
		List<Map<String, String>> dataList = new ArrayList<>();
		Map<String, String> map = new HashMap<>();
		dataList.add(map);
		Mockito.when(merchantDao.getMerchantCodeAndCompanyName(Matchers.anyString())).thenReturn(dataList);
		merchantUpdateServiceImpl.getMerchantCodeAndCompanyName("43");
	}

	@Test
	public void testGetMerchantByStatus() throws ChatakAdminException {
		List<PGMerchant> merchant = new ArrayList<>();
		PGMerchant pgMerchant = new PGMerchant();
		merchant.add(pgMerchant);
		Mockito.when(merchantUpdateDao.getMerchantsByStatus(Matchers.anyInt())).thenReturn(merchant);
		merchantUpdateServiceImpl.getMerchantByStatus(1);
	}

	@Test
	public void testGetCurrencies() throws ChatakAdminException {
		List<CurrencyRequest> currencyRequests = new ArrayList<>();
		CurrencyRequest currencyRequest = new CurrencyRequest();
		currencyRequests.add(currencyRequest);
		Mockito.when(currencyDao.findAllCurrencies()).thenReturn(currencyRequests);
		merchantUpdateServiceImpl.getCurrencies();
	}

	@Test(expected = NullPointerException.class)
	public void testFindByMerchantId() throws NumberFormatException, InstantiationException, IllegalAccessException {
		PGMerchant pGMerchant = new PGMerchant();
		List<PGMerchantCurrencyMapping> pgMerchantCurrencyMapping = new ArrayList<>();
		PGMerchantCurrencyMapping pgMerchantCurrency = new PGMerchantCurrencyMapping();
		pgMerchantCurrencyMapping.add(pgMerchantCurrency);
		Mockito.when(merchantProfileDao.getMerchantById(Matchers.anyLong())).thenReturn(pGMerchant);
		Mockito.when(currencyDao.findByMerchantCode(Matchers.anyString())).thenReturn(pgMerchantCurrencyMapping);
		merchantUpdateServiceImpl.findByMerchantId(Long.parseLong("23"));
	}

	@Test
	public void testGetMerchantCode() throws ChatakAdminException {
		merchantUpdateServiceImpl.getMerchantCode("7438");
	}

	@Test
	public void testGetMerchantByStatusPendingandDecline() throws ChatakAdminException {
		List<PGMerchant> merchant = new ArrayList<>();
		PGMerchant pgMerchant = new PGMerchant();
		merchant.add(pgMerchant);
		Mockito.when(merchantUpdateDao.getMerchantByStatusPendingandDecline()).thenReturn(merchant);
		merchantUpdateServiceImpl.getMerchantByStatusPendingandDecline();
	}

	@Test
	public void testDistinctList() throws ChatakAdminException {
		List<Merchant> merchants = new ArrayList<>();
		MerchantUpdateServiceImpl.distinctList(merchants);
	}

	@Test
	public void testDistinctListElse() throws ChatakAdminException {
		List<Merchant> merchants = new ArrayList<>();
		Merchant merchant = new Merchant();
		merchants.add(merchant);
		MerchantUpdateServiceImpl.distinctList(merchants);
	}

}
