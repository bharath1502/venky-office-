package com.chatak.merchant.service.impl;

import java.sql.Timestamp;
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
import org.springframework.dao.DataAccessException;

import com.chatak.merchant.controller.model.Option;
import com.chatak.merchant.exception.ChatakMerchantException;
import com.chatak.merchant.exception.ChatakPayException;
import com.chatak.merchant.model.GetFraudDetailsResponse;
import com.chatak.pg.acq.dao.FraudAdvancedServicesDao;
import com.chatak.pg.acq.dao.FraudBasicServicesDao;
import com.chatak.pg.acq.dao.ISOCountryDao;
import com.chatak.pg.acq.dao.model.PGFraudBasic;
import com.chatak.pg.acq.dao.model.PGMerchant;
import com.chatak.pg.acq.dao.model.PGMerchantAdvancedFraud;
import com.chatak.pg.acq.dao.model.PGMerchantUsers;
import com.chatak.pg.bean.ISOCountryCodeRequest;
import com.chatak.pg.bean.Response;
import com.chatak.pg.model.AdvancedFraudDTO;
import com.chatak.pg.model.FraudBasicDTO;
import com.chatak.pg.util.DateUtil;

@RunWith(MockitoJUnitRunner.class)
public class FraudServiceImplTest {
	@InjectMocks
	private FraudServiceImpl fraudServiceImpl = new FraudServiceImpl();

	@Mock
	FraudBasicServicesDao fraudBasicServicesDao;

	@Mock
	FraudAdvancedServicesDao fraudAdvancedServicesDao;

	@Mock
	private ISOCountryDao isoCountryDao;

	@Mock
	PGMerchantAdvancedFraud pgMerchantAdvancedFraud;
	
	@Mock
	Response response;

	@Mock
	PGFraudBasic pgFraudBasic;

	@Mock
	FraudBasicDTO fraudBasicDTO;

	@Mock
	AdvancedFraudDTO advancedFraudDTO;

	@Mock
	PGMerchantUsers pgMerchantUsers;

	@Mock
	PGMerchant merchant;

	@Mock
	GetFraudDetailsResponse basicDTO = new GetFraudDetailsResponse();

	@Test
	public void testCreateFraudBasic() throws ChatakMerchantException {
		PGFraudBasic fraudBasic = new PGFraudBasic();
		fraudBasic.setId(Long.parseLong("101"));
		fraudBasic.setDeniedBin("102020");
		fraudBasic.setDeniedCountry("A");
		fraudBasic.setDeniedEMail("abc");
		fraudBasic.setDeniedIP("xyz");
		fraudBasic.setMerchantId(Long.parseLong("55"));
		fraudBasic.setCreatedDate(new Timestamp(Long.parseLong("2")));
		fraudBasic.setUpdatedDate(DateUtil.getCurrentTimestamp());
		Mockito.when(fraudBasicServicesDao.findByFraudBasicMerchantId(Matchers.anyLong())).thenReturn(fraudBasic);
		response = fraudServiceImpl.createFraudBasic(fraudBasicDTO);
		Assert.assertNotNull(response);
	}

	@Test
	public void testCreateFraudBasicNull() throws ChatakMerchantException {
		PGFraudBasic fraudBasic = new PGFraudBasic();
		fraudBasic.setId(Long.parseLong("101"));
		fraudBasic.setDeniedBin("102020");
		fraudBasic.setDeniedCountry("A");
		fraudBasic.setDeniedEMail("abc");
		fraudBasic.setDeniedIP("xyz");
		fraudBasic.setMerchantId(Long.parseLong("55"));
		fraudBasic.setCreatedDate(new Timestamp(Long.parseLong("2")));
		fraudBasic.setUpdatedDate(DateUtil.getCurrentTimestamp());
		response = fraudServiceImpl.createFraudBasic(fraudBasicDTO);
		Assert.assertNotNull(response);
	}

	@Test
	public void testCreateFraudBasicException() throws ChatakMerchantException {
		PGFraudBasic fraudBasic = new PGFraudBasic();
		fraudBasic.setId(Long.parseLong("101"));
		fraudBasic.setDeniedBin("102020");
		fraudBasic.setDeniedCountry("A");
		fraudBasic.setDeniedEMail("abc");
		fraudBasic.setDeniedIP("xyz");
		fraudBasic.setMerchantId(Long.parseLong("55"));
		fraudBasic.setCreatedDate(new Timestamp(Long.parseLong("2")));
		fraudBasic.setUpdatedDate(DateUtil.getCurrentTimestamp());
		Mockito.when(fraudBasicServicesDao.findByFraudBasicMerchantId(Matchers.anyLong()))
				.thenThrow(new NullPointerException());
		response = fraudServiceImpl.createFraudBasic(fraudBasicDTO);
		Assert.assertNotNull(response);
	}

	@Test
	public void testGetFraudDetails() throws ChatakMerchantException {

		pgFraudBasic = new PGFraudBasic();
		pgFraudBasic.setDeniedIP("abc");
		pgFraudBasic.setDeniedCountry("xyz");
		pgFraudBasic.setDeniedBin("123");
		pgFraudBasic.setDeniedEMail("123");
		Mockito.when(fraudBasicServicesDao.findByFraudBasicMerchantId(Matchers.anyLong())).thenReturn(pgFraudBasic);
		basicDTO = fraudServiceImpl.getFraudDetails(1l);
		Assert.assertNotNull(response);
	}

	@Test
	public void testGetFraudDetailsNull() throws ChatakMerchantException {
		pgFraudBasic = new PGFraudBasic();
		pgFraudBasic.setDeniedIP("abc");
		pgFraudBasic.setDeniedCountry("xyz");
		pgFraudBasic.setDeniedBin("123");
		pgFraudBasic.setDeniedEMail("123");
		basicDTO = fraudServiceImpl.getFraudDetails(1l);
		Assert.assertNotNull(response);
	}

	@Test
	public void testGetFraudDetailsException() throws ChatakMerchantException {
		pgFraudBasic = new PGFraudBasic();
		pgFraudBasic.setDeniedIP("abc");
		pgFraudBasic.setDeniedCountry("xyz");
		pgFraudBasic.setDeniedBin("123");
		pgFraudBasic.setDeniedEMail("123");
		Mockito.when(fraudBasicServicesDao.findByFraudBasicMerchantId(Matchers.anyLong()))
				.thenThrow(new NullPointerException());
		basicDTO = fraudServiceImpl.getFraudDetails(1l);
		Assert.assertNotNull(basicDTO);
	}

	@Test
	public void testGetISOCountries() throws ChatakMerchantException {
		List<ISOCountryCodeRequest> isoCountry = new ArrayList<>();
		ISOCountryCodeRequest iSOCountryCodeRequest = new ISOCountryCodeRequest();
		iSOCountryCodeRequest.setName("abc");
		iSOCountryCodeRequest.setCode("123");
		isoCountry.add(iSOCountryCodeRequest);
		Mockito.when(isoCountryDao.findAllISOCountries()).thenReturn(isoCountry);
		List<Option> list = fraudServiceImpl.getISOCountries();
		Assert.assertNotNull(list);
	}

	@Test
	public void testCreateAdvancedFraud() throws ChatakMerchantException {
		advancedFraudDTO.setId(Long.parseLong("123"));
		advancedFraudDTO.setParentMerchantId(Long.parseLong("1345"));
		merchant = new PGMerchant();
		merchant.setAllowAdvancedFraudFilter(1);
		Mockito.when(fraudBasicServicesDao.findById(Matchers.anyLong())).thenReturn(pgMerchantUsers);
		Mockito.when(fraudAdvancedServicesDao.findById(Matchers.anyLong())).thenReturn(merchant);
		advancedFraudDTO = fraudServiceImpl.createAdvancedFraud(advancedFraudDTO);
		Assert.assertNotNull(advancedFraudDTO);
	}

	@Test
	public void testCreateAdvancedFraudNotEqulOne() throws ChatakMerchantException {
		advancedFraudDTO.setId(Long.parseLong("123"));
		advancedFraudDTO.setParentMerchantId(Long.parseLong("1345"));
		merchant = new PGMerchant();
		merchant.setAllowAdvancedFraudFilter(Integer.parseInt("56"));
		Mockito.when(fraudBasicServicesDao.findById(Matchers.anyLong())).thenReturn(pgMerchantUsers);
		Mockito.when(fraudAdvancedServicesDao.findById(Matchers.anyLong())).thenReturn(merchant);
		advancedFraudDTO = fraudServiceImpl.createAdvancedFraud(advancedFraudDTO);
		Assert.assertNotNull(advancedFraudDTO);
	}

	@Test(expected = ChatakMerchantException.class)
	public void testCreateAdvancedFraudException() throws ChatakMerchantException {
		advancedFraudDTO.setId(Long.parseLong("123"));
		advancedFraudDTO.setParentMerchantId(Long.parseLong("1345"));
		merchant = new PGMerchant();
		merchant.setAllowAdvancedFraudFilter(1);
		Mockito.when(fraudBasicServicesDao.findById(Matchers.anyLong())).thenReturn(pgMerchantUsers);
		Mockito.when(fraudAdvancedServicesDao.findById(Matchers.anyLong())).thenThrow(new NullPointerException());
		advancedFraudDTO = fraudServiceImpl.createAdvancedFraud(advancedFraudDTO);
		Assert.assertNotNull(advancedFraudDTO);
	}

	@Test
	public void testSearchAdvancedFraudByCreatedBy() throws ChatakMerchantException {
		List<AdvancedFraudDTO> list = new ArrayList<AdvancedFraudDTO>();
		Mockito.when(fraudAdvancedServicesDao.searchAdvancedFraud(advancedFraudDTO)).thenReturn(list);
		list = fraudServiceImpl.searchAdvancedFraudByCreatedBy(advancedFraudDTO);
		Assert.assertNotNull(list);
	}

	@Test(expected = ChatakMerchantException.class)
	public void testSearchAdvancedFraudByCreatedByException() throws ChatakMerchantException {
		Mockito.when(fraudAdvancedServicesDao.searchAdvancedFraud(advancedFraudDTO))
				.thenThrow(new NullPointerException());
		List<AdvancedFraudDTO> list = fraudServiceImpl.searchAdvancedFraudByCreatedBy(advancedFraudDTO);
		Assert.assertNotNull(list);
	}

	@Test
	public void testSearchAdvancedFraudByIdAndMerchantCode()
			throws ChatakMerchantException, InstantiationException, IllegalAccessException {
		pgMerchantAdvancedFraud = new PGMerchantAdvancedFraud();
		advancedFraudDTO = new AdvancedFraudDTO();
		advancedFraudDTO.setId(Long.parseLong("123"));
		advancedFraudDTO.setMerchantCode("123");
		advancedFraudDTO.setMerchantCode("abc");
		Mockito.when(fraudAdvancedServicesDao.findByIdAndMerchantCode(Matchers.anyLong(), Matchers.anyString()))
				.thenReturn(pgMerchantAdvancedFraud);
		fraudServiceImpl.searchAdvancedFraudByIdAndMerchantCode(advancedFraudDTO);
	}

	@Test(expected = ChatakMerchantException.class)
	public void testSearchAdvancedFraudByIdAndMerchantCodeException()
			throws ChatakMerchantException, InstantiationException, IllegalAccessException {
		advancedFraudDTO = new AdvancedFraudDTO();
		advancedFraudDTO.setId(Long.parseLong("123"));
		advancedFraudDTO.setMerchantCode("123");
		Mockito.when(fraudAdvancedServicesDao.findByIdAndMerchantCode(null, null))
				.thenThrow(new NullPointerException());
		fraudServiceImpl.searchAdvancedFraudByIdAndMerchantCode(advancedFraudDTO);
	}

	@Test
	public void testUpdateAdvancedFraud() throws ChatakMerchantException, ChatakPayException {
		pgMerchantAdvancedFraud = new PGMerchantAdvancedFraud();
		advancedFraudDTO = new AdvancedFraudDTO();
		advancedFraudDTO.setId(Long.parseLong("123"));
		advancedFraudDTO.setMerchantCode("123");
		Mockito.when(fraudAdvancedServicesDao.findByIdAndMerchantCode(Matchers.anyLong(), Matchers.anyString()))
				.thenReturn(pgMerchantAdvancedFraud);
		fraudServiceImpl.searchAdvancedFraudByIdAndMerchantCode(advancedFraudDTO);
		Mockito.when(fraudAdvancedServicesDao.saveOrUpdatePGMerchantAdvancedFraud(pgMerchantAdvancedFraud))
				.thenReturn(pgMerchantAdvancedFraud);
		fraudServiceImpl.updateAdvancedFraud(advancedFraudDTO);
	}

	@Test(expected = ChatakPayException.class)
	public void testUpdateAdvancedFraudException() throws ChatakMerchantException, ChatakPayException {
		advancedFraudDTO = new AdvancedFraudDTO();
		Mockito.when(fraudAdvancedServicesDao.findByIdAndMerchantCode(Matchers.anyLong(), Matchers.anyString()))
				.thenThrow(new NullPointerException());
		fraudServiceImpl.updateAdvancedFraud(advancedFraudDTO);
	}

	@Test
	public void testDeleteAdvancedFraud() throws ChatakMerchantException {
		fraudServiceImpl.deleteAdvancedFraud(Long.parseLong("12"));

	}

	@Test(expected = ChatakMerchantException.class)
	public void testDeleteAdvancedFraudException() throws ChatakMerchantException {
		Mockito.doNothing().when(NullPointerException.class);
		fraudServiceImpl.deleteAdvancedFraud(null);

	}

}
