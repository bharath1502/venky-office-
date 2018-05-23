package com.chatak.merchant.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import com.chatak.merchant.controller.model.MerchantProfile;
import com.chatak.merchant.exception.ChatakMerchantException;
import com.chatak.pg.acq.dao.CountryDao;
import com.chatak.pg.acq.dao.MerchantDao;
import com.chatak.pg.acq.dao.MerchantProfileDao;
import com.chatak.pg.acq.dao.MerchantUpdateDao;
import com.chatak.pg.acq.dao.MerchantUserAddressDao;
import com.chatak.pg.acq.dao.model.PGAccount;
import com.chatak.pg.acq.dao.model.PGMerchant;
import com.chatak.pg.acq.dao.model.PGMerchantConfig;
import com.chatak.pg.acq.dao.model.PGMerchantUserAddress;
import com.chatak.pg.acq.dao.model.PGMerchantUsers;
import com.chatak.pg.acq.dao.model.PGState;
import com.chatak.pg.bean.CountryRequest;
import com.chatak.pg.user.bean.UpdateMerchantResponse;

import junit.framework.Assert;
@RunWith(MockitoJUnitRunner.class)
public class MerchantProfileServiceImplTest {

	@InjectMocks
	MerchantProfileServiceImpl merchantProfileServiceImpl = new MerchantProfileServiceImpl();

	@Mock
	MerchantDao merchantDao;

	@Mock
	CountryDao countryDao;

	@Mock
	MerchantUserAddressDao merchantUserAddressDao;

	@Mock
	MerchantUpdateDao merchantUpdateDao;

	@Mock
	MerchantProfileDao merchantProfileDao;
	
	@Mock
	UpdateMerchantResponse updateMerchantResponse;

	@Test
	public void testGetMerchantProfile() throws ChatakMerchantException {
		 MerchantProfile merchantProfile =new MerchantProfile();
		 PGAccount pgAccount = new PGAccount();
		 PGMerchant pgMerchant = new PGMerchant();
		 PGMerchantConfig PGMerchantConfig = new PGMerchantConfig();
		 List<PGMerchantUsers> pgMerchantUsers = new ArrayList<>();
		 PGMerchantUsers pgMerchantUser =  new PGMerchantUsers();
		 PGMerchantUserAddress mailinAddress = new PGMerchantUserAddress();
		 PGMerchantConfig.setAutoSettlement(0);
		 mailinAddress.setAddress1("address");
		 mailinAddress.setMerchantUserId(Long.parseLong("12"));
		 PGMerchantConfig.setRefunds(1);
		 PGMerchantConfig.setShippingAmount(1);
		 PGMerchantConfig.setTaxAmount(1);
		 PGMerchantConfig.setTipAmount(1);
		 pgMerchantUser.setId(Long.parseLong("12"));
		 pgAccount.setId(Long.parseLong("1212"));
		 merchantProfile.setId(Long.parseLong("12"));
		 pgMerchant.setMerchantCode("212");
		 pgMerchantUsers.add(pgMerchantUser);
		 pgMerchant.setMerchantConfig(PGMerchantConfig);
		 pgMerchant.setPgMerchantUsers(pgMerchantUsers);
		 pgMerchant.setAddress1("address1");
		 Mockito.when(merchantProfileDao.getMerchantById(Matchers.anyLong())).thenReturn(pgMerchant);
		 Mockito.when(merchantProfileDao.getPgAccount(Matchers.anyString())).thenReturn(pgAccount);
		 Mockito.when(merchantUserAddressDao.createOrUpdateMerchantUserAddress(Matchers.any(PGMerchantUserAddress.class))).thenReturn(mailinAddress);
		 Assert.assertNotNull(merchantProfileServiceImpl.getMerchantProfile(merchantProfile));
	}
	
	
		
	@Test
	public void testUpdateMerchantProfile() throws ChatakMerchantException {
		 MerchantProfile merchantProfile =new MerchantProfile();
		 PGAccount pgAccount = new PGAccount();
		 PGMerchant pgMerchant = new PGMerchant();
		 PGMerchantConfig PGMerchantConfig = new PGMerchantConfig();
		 List<PGMerchantUsers> pgMerchantUsers = new ArrayList<>();
		 PGMerchantUsers pgMerchantUser =  new PGMerchantUsers();
		 PGMerchantUserAddress mailinAddress = new PGMerchantUserAddress();
		 PGMerchantConfig.setAutoSettlement(0);
		 mailinAddress.setAddress1("address");
		 mailinAddress.setMerchantUserId(Long.parseLong("122"));
		 PGMerchantConfig.setRefunds(1);
		 PGMerchantConfig.setShippingAmount(1);
		 PGMerchantConfig.setTaxAmount(1);
		 PGMerchantConfig.setTipAmount(1);
		 pgMerchantUser.setId(Long.parseLong("12"));
		 pgAccount.setId(Long.parseLong("1212"));
		 merchantProfile.setMailingAddress1("address");
		 merchantProfile.setMailingAddress2("address");
		 
		 pgMerchant.setMerchantCode("212");
		 pgMerchantUsers.add(pgMerchantUser);
		 pgMerchant.setMerchantConfig(PGMerchantConfig);
		 pgMerchant.setPgMerchantUsers(pgMerchantUsers);
		 
		 pgMerchant.setAddress1("address1");
		 Mockito.when(merchantProfileDao.getMerchantById(Matchers.anyLong())).thenReturn(pgMerchant);
		
		 Mockito.when(merchantUserAddressDao.getMerchantUserAddressByUserId(Matchers.anyString())).thenReturn(mailinAddress);
		 Mockito.when(merchantProfileDao.getPgAccount(Matchers.anyString())).thenReturn(pgAccount);
		 Mockito.when(merchantUserAddressDao.createOrUpdateMerchantUserAddress(Matchers.any(PGMerchantUserAddress.class))).thenReturn(mailinAddress);
		 updateMerchantResponse	= merchantProfileServiceImpl.updateMerchantProfile(merchantProfile);
		 Assert.assertNull(updateMerchantResponse);
	}
	
	@Test
	public void testGetStatesByCountry() throws ChatakMerchantException {
		 List<PGState> pgStates = new ArrayList<PGState>();
		 PGState pgState= new PGState();
		 pgState.setName("Name");
		 pgStates.add(pgState);
		 CountryRequest countryRequest = new CountryRequest();
		 PGMerchant pgMerchant = new PGMerchant();
		 countryRequest.setName("hi");
		 pgMerchant.setMerchantCode("212");
		 pgMerchant.setAddress1("address1");
		 Mockito.when(countryDao.getCountryByName(Matchers.anyString())).thenReturn(countryRequest);
		 Mockito.when(merchantProfileDao.getStateByCountryId(Matchers.anyLong())).thenReturn(pgStates);
		 Assert.assertNotNull(merchantProfileServiceImpl.getStatesByCountry("512"));
	}
	
	@Test
	public void testGetStatesByCountryElse() throws ChatakMerchantException {
		 CountryRequest countryRequest = new CountryRequest();
		 List<PGState> pgStates  = new ArrayList<>();
		 PGState pGState = new PGState();
		 pGState.setName("hi");
		 countryRequest.setId(Long.parseLong("122"));
		 pgStates.add(pGState);
		 PGMerchant pgMerchant = new PGMerchant();
		 pgMerchant.setMerchantCode("212");
		 pgMerchant.setAddress1("address1");
		 Mockito.when(countryDao.getCountryByName(Matchers.anyString())).thenReturn(countryRequest);
		 Mockito.when(merchantProfileDao.getStateByCountryId(Matchers.anyLong())).thenReturn(pgStates);
		 Assert.assertNotNull(merchantProfileServiceImpl.getStatesByCountry("512"));
	}
	
	@Test
	public void testGetCountries() throws ChatakMerchantException {
		 List<CountryRequest> countryRequests = new ArrayList<>();
		 CountryRequest countryRequest = new CountryRequest();
		 countryRequest.setName("India");
		 countryRequests.add(countryRequest);
		 Mockito.when(countryDao.findAllCountries()).thenReturn(countryRequests);
		 Assert.assertNotNull(merchantProfileServiceImpl.getCountries());
	}
}
