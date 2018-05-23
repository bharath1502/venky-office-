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

import com.chatak.merchant.exception.ChatakMerchantException;
import com.chatak.merchant.exception.ChatakPayException;
import com.chatak.pg.acq.dao.CountryDao;
import com.chatak.pg.acq.dao.MerchantProfileDao;
import com.chatak.pg.acq.dao.RecurringServicesDao;
import com.chatak.pg.acq.dao.model.PGState;
import com.chatak.pg.acq.dao.model.RecurringContractInfo;
import com.chatak.pg.acq.dao.model.RecurringCustomerInfo;
import com.chatak.pg.acq.dao.model.RecurringPaymentInfo;
import com.chatak.pg.bean.CountryRequest;
import com.chatak.pg.constants.Status;
import com.chatak.pg.model.RecurringContractInfoDTO;
import com.chatak.pg.model.RecurringCustomerInfoDTO;
import com.chatak.pg.model.RecurringPaymentInfoDTO;
import com.chatak.pg.util.Constants;

import junit.framework.Assert;

@RunWith(MockitoJUnitRunner.class)
public class RecurringServiceImplTest {

	@InjectMocks
	private RecurringServiceImpl recurringServiceImpl = new RecurringServiceImpl();

	@Mock
	RecurringServicesDao recurringServicesDao;

	@Mock
	private CountryDao countryDao;

	@Mock
	MerchantProfileDao merchantProfileDao;

	public static final String STATUS_INACTIVE = "Inactive";

	public static final String STATUS_ACTIVE = "Active";

	@Test(expected = ChatakPayException.class)
	public void testCreateCustomerInfo() throws ChatakPayException {
		RecurringCustomerInfoDTO recurringCustomerInfoDTO = new RecurringCustomerInfoDTO();
		RecurringCustomerInfo recurringCustomerInfo = new RecurringCustomerInfo();
		Mockito.when(recurringServicesDao.findByCustomerId(Matchers.anyString())).thenReturn(recurringCustomerInfo);
		Mockito.when(recurringServicesDao.saveOrUpdateRecurringCustomerInfo(Matchers.any(RecurringCustomerInfo.class)))
				.thenReturn(recurringCustomerInfo);
		Mockito.when(recurringServicesDao.findByCustomerId(Matchers.anyString())).thenReturn(recurringCustomerInfo);
		Assert.assertNull(recurringServiceImpl.createCustomerInfo(recurringCustomerInfoDTO));
	}

	@Test
	public void testUpdateCustomerInfo() throws ChatakPayException {
		RecurringCustomerInfoDTO recurringCustomerInfoDTO = new RecurringCustomerInfoDTO();
		RecurringCustomerInfo recurringCustomerInfo = new RecurringCustomerInfo();
		recurringCustomerInfo.setStatus(STATUS_INACTIVE);
		recurringCustomerInfo.setCustomerId("23423");
		Mockito.when(recurringServicesDao.findByRecurringCustomerInfoId(Matchers.anyLong()))
				.thenReturn(recurringCustomerInfo);
		Mockito.when(recurringServicesDao.saveOrUpdateRecurringCustomerInfo(Matchers.any(RecurringCustomerInfo.class)))
				.thenReturn(recurringCustomerInfo);
		Assert.assertNotNull(recurringServiceImpl.updateCustomerInfo(recurringCustomerInfoDTO));
	}

	@Test(expected = ChatakPayException.class)
	public void testUpdateCustomerInfoException() throws ChatakPayException {
		RecurringCustomerInfoDTO recurringCustomerInfoDTO = new RecurringCustomerInfoDTO();
		Mockito.when(recurringServicesDao.saveOrUpdateRecurringCustomerInfo(Matchers.any(RecurringCustomerInfo.class)))
				.thenThrow(new NullPointerException());
		Assert.assertNotNull(recurringServiceImpl.updateCustomerInfo(recurringCustomerInfoDTO));
	}

	@Test(expected = ChatakPayException.class)
	public void testUpdateCustomerInfoInactive() throws ChatakPayException {
		RecurringCustomerInfoDTO recurringCustomerInfoDTO = new RecurringCustomerInfoDTO();
		RecurringCustomerInfo recurringCustomerInfo = new RecurringCustomerInfo();
		recurringCustomerInfo.setStatus(STATUS_ACTIVE);
		Assert.assertNull(recurringServiceImpl.updateCustomerInfo(recurringCustomerInfoDTO));
	}

	@Test
	public void testSearchCustomerInfo() throws InstantiationException, IllegalAccessException, ChatakPayException {
		List<RecurringCustomerInfoDTO> recurringCustomerInfoDTOs = new ArrayList<>();
		RecurringCustomerInfoDTO recurringCustomerInfoDTO = new RecurringCustomerInfoDTO();
		Mockito.when(recurringServicesDao.searchCustomerInfo(Matchers.any(RecurringCustomerInfoDTO.class))).thenReturn(recurringCustomerInfoDTOs);
		Assert.assertNotNull(recurringServiceImpl.searchCustomerInfo(recurringCustomerInfoDTO));
	}

	@Test(expected = ChatakPayException.class)
	public void testSearchCustomerInfoException() throws ChatakPayException, InstantiationException, IllegalAccessException {
		RecurringCustomerInfoDTO recurringCustomerInfoDTO = new RecurringCustomerInfoDTO();
		Mockito.when(recurringServicesDao.searchCustomerInfo(Matchers.any(RecurringCustomerInfoDTO.class))).thenThrow(new NullPointerException());
		Assert.assertNull(recurringServiceImpl.searchCustomerInfo(recurringCustomerInfoDTO));
	}

	@Test
	public void testSearchCustomerInfoById() throws ChatakPayException {
		RecurringCustomerInfoDTO recurringCustomerInfoDTO = new RecurringCustomerInfoDTO();
		RecurringCustomerInfo recurringCustomerInfo = new RecurringCustomerInfo();
		Mockito.when(recurringServicesDao.findByRecurringCustomerInfoId(Matchers.anyLong()))
				.thenReturn(recurringCustomerInfo);
		Assert.assertNotNull(recurringServiceImpl.searchCustomerInfoById(recurringCustomerInfoDTO));

	}

	@Test(expected = ChatakPayException.class)
	public void testSearchCustomerInfoByIdException() throws ChatakPayException {
		RecurringCustomerInfoDTO recurringCustomerInfoDTO = new RecurringCustomerInfoDTO();
		Mockito.when(recurringServicesDao.findByRecurringCustomerInfoId(Matchers.anyLong()))
				.thenThrow(new NullPointerException());
		Assert.assertNull(recurringServiceImpl.searchCustomerInfoById(recurringCustomerInfoDTO));

	}

	@Test
	public void testCreatePaymentInfo() throws ChatakPayException {
		RecurringPaymentInfoDTO recurringPaymentInfoDTO = new RecurringPaymentInfoDTO();
		RecurringPaymentInfo recurringPaymentInfo = new RecurringPaymentInfo();

		recurringPaymentInfo.setRecurringPaymentInfoId(Long.parseLong("2345"));
		recurringPaymentInfo.setNameOnCard("VISA");
		Mockito.when(recurringServicesDao.saveOrUpdateRecurringPaymentInfo(Matchers.any(RecurringPaymentInfo.class)))
				.thenReturn(recurringPaymentInfo);
		Assert.assertNotNull(recurringServiceImpl.createPaymentInfo(recurringPaymentInfoDTO));

	}

	@Test(expected = ChatakPayException.class)
	public void testCreatePaymentInfoException() throws ChatakPayException {
		RecurringPaymentInfoDTO recurringPaymentInfoDTO = new RecurringPaymentInfoDTO();
		Mockito.when(recurringServicesDao.saveOrUpdateRecurringPaymentInfo(Matchers.any(RecurringPaymentInfo.class)))
				.thenThrow(new NullPointerException());
		Assert.assertNotNull(recurringServiceImpl.createPaymentInfo(recurringPaymentInfoDTO));

	}

	@Test
	public void testSearchPaymentInfoByCustomerId() throws ChatakPayException {
		List<RecurringPaymentInfoDTO> RecurringPaymentInfoDTOs = new ArrayList<>();
		RecurringPaymentInfoDTO recurringPaymentInfoDTO = new RecurringPaymentInfoDTO();
		Mockito.when(recurringServicesDao.searchPaymentInfo(Matchers.any(RecurringPaymentInfoDTO.class))).thenReturn(RecurringPaymentInfoDTOs);
		Assert.assertNotNull(recurringServiceImpl.searchPaymentInfoByCustomerId(recurringPaymentInfoDTO));

	}

	@Test(expected = ChatakPayException.class)
	public void testSearchPaymentInfoByCustomerIdException() throws ChatakPayException {
		RecurringPaymentInfoDTO recurringPaymentInfoDTO = new RecurringPaymentInfoDTO();
		Mockito.when(recurringServicesDao.searchPaymentInfo(Matchers.any(RecurringPaymentInfoDTO.class))).thenThrow(new NullPointerException());
		Assert.assertNotNull(recurringServiceImpl.searchPaymentInfoByCustomerId(recurringPaymentInfoDTO));

	}

	@Test
	public void testSearchPaymentInfoById() throws ChatakPayException {
		RecurringPaymentInfoDTO recurringPaymentInfoDTO = new RecurringPaymentInfoDTO();
		RecurringPaymentInfo recurringPaymentInfo = new RecurringPaymentInfo();
		Mockito.when(recurringServicesDao.findByRecurringPaymentInfoId(Matchers.anyLong()))
				.thenReturn(recurringPaymentInfo);
		Assert.assertNotNull(recurringServiceImpl.searchPaymentInfoById(recurringPaymentInfoDTO));

	}

	@Test(expected = ChatakPayException.class)
	public void testSearchPaymentInfoByIdException() throws ChatakPayException {
		RecurringPaymentInfoDTO recurringPaymentInfoDTO = new RecurringPaymentInfoDTO();
		Mockito.when(recurringServicesDao.findByRecurringPaymentInfoId(Matchers.anyLong()))
				.thenThrow(new NullPointerException());
		Assert.assertNotNull(recurringServiceImpl.searchPaymentInfoById(recurringPaymentInfoDTO));

	}

	@Test
	public void testUpdatePaymentInfo() throws ChatakPayException {
		RecurringPaymentInfoDTO recurringPaymentInfoDTO = new RecurringPaymentInfoDTO();
		RecurringPaymentInfo recurringPaymentInfo = new RecurringPaymentInfo();
		recurringPaymentInfo.setStatus(STATUS_INACTIVE);
		Mockito.when(recurringServicesDao.findByRecurringPaymentInfoId(Matchers.anyLong()))
				.thenReturn(recurringPaymentInfo);
		Mockito.when(recurringServicesDao.saveOrUpdateRecurringPaymentInfo(Matchers.any(RecurringPaymentInfo.class)))
				.thenReturn(recurringPaymentInfo);
		recurringServiceImpl.updatePaymentInfo(recurringPaymentInfoDTO);
	}

	@Test
	public void testUpdatePaymentInfoActive() throws ChatakPayException {
		RecurringPaymentInfoDTO recurringPaymentInfoDTO = new RecurringPaymentInfoDTO();
		RecurringPaymentInfo recurringPaymentInfo = new RecurringPaymentInfo();
		recurringPaymentInfo.setStatus(STATUS_ACTIVE);
		Mockito.when(recurringServicesDao.findByRecurringPaymentInfoId(Matchers.anyLong()))
				.thenReturn(recurringPaymentInfo);
		Mockito.when(recurringServicesDao.saveOrUpdateRecurringPaymentInfo(Matchers.any(RecurringPaymentInfo.class)))
				.thenReturn(recurringPaymentInfo);
		recurringServiceImpl.updatePaymentInfo(recurringPaymentInfoDTO);
	}

	@Test
	public void tesDeletePaymentInfo() throws ChatakPayException {
		RecurringPaymentInfoDTO recurringPaymentInfoDTO = new RecurringPaymentInfoDTO();
		RecurringPaymentInfo paymentInfo = new RecurringPaymentInfo();
		recurringPaymentInfoDTO.setUpdatedBy("2345");
		paymentInfo.setStatus(Status.ACTIVE.name());
		Mockito.when(recurringServicesDao.findByRecurringPaymentInfoId(Matchers.anyLong())).thenReturn(paymentInfo);
		Mockito.when(recurringServicesDao.saveOrUpdateRecurringPaymentInfo(Matchers.any(RecurringPaymentInfo.class))).thenReturn(paymentInfo);
		recurringServiceImpl.deletePaymentInfo(recurringPaymentInfoDTO);
	}

	@Test(expected = ChatakPayException.class)
	public void tesDeletePaymentInfoInactive() throws ChatakPayException {
		RecurringPaymentInfoDTO recurringPaymentInfoDTO = new RecurringPaymentInfoDTO();
		RecurringPaymentInfo paymentInfo = new RecurringPaymentInfo();
		recurringPaymentInfoDTO.setUpdatedBy("2345");
		paymentInfo.setStatus(STATUS_INACTIVE);
		Mockito.when(recurringServicesDao.findByRecurringPaymentInfoId(Matchers.anyLong())).thenReturn(paymentInfo);
		Mockito.when(recurringServicesDao.saveOrUpdateRecurringPaymentInfo(Matchers.any(RecurringPaymentInfo.class))).thenReturn(paymentInfo);
		recurringServiceImpl.deletePaymentInfo(recurringPaymentInfoDTO);
	}

	@Test(expected = ChatakPayException.class)
	public void testCreateContractInfoStatusInactive() throws ChatakPayException {
		RecurringContractInfoDTO recurringContractInfoDTO = new RecurringContractInfoDTO();
		RecurringPaymentInfo recurringPaymentInfo = new RecurringPaymentInfo();
		recurringPaymentInfo.setStatus(STATUS_INACTIVE);
		Mockito.when(recurringServicesDao.findByRecurringPaymentInfoId(Matchers.anyLong()))
				.thenReturn(recurringPaymentInfo);
		recurringServiceImpl.createContractInfo(recurringContractInfoDTO);

	}

	@Test
	public void testCreateContractInfoStatusActive() throws ChatakPayException {
		RecurringContractInfoDTO recurringContractInfoDTO = new RecurringContractInfoDTO();
		RecurringPaymentInfo recurringPaymentInfo = new RecurringPaymentInfo();
		recurringPaymentInfo.setStatus(Constants.STATUS_ACTIVE);
		recurringContractInfoDTO.setStartDate("2005-12-12 01:54:42");
		recurringContractInfoDTO.setErrorCode(Constants.ERROR_CODE);
		Mockito.when(recurringServicesDao.findByRecurringPaymentInfoId(Matchers.anyLong()))
				.thenReturn(recurringPaymentInfo);
		recurringServiceImpl.createContractInfo(recurringContractInfoDTO);

	}

	@Test(expected = ChatakPayException.class)
	public void testDeleteContractInfo() throws ChatakPayException {
		RecurringContractInfoDTO recurringContractInfoDTO = new RecurringContractInfoDTO();
		RecurringContractInfo contractInfo = new RecurringContractInfo();
		Mockito.when(recurringServicesDao.findByRecurringContractInfoId(Matchers.anyLong())).thenReturn(contractInfo);
		recurringServiceImpl.deleteContractInfo(recurringContractInfoDTO);
	}

	@Test
	public void testDeleteContractInfoStatusInactive() throws ChatakPayException {
		RecurringContractInfoDTO recurringContractInfoDTO = new RecurringContractInfoDTO();
		RecurringContractInfo contractInfo = new RecurringContractInfo();
		contractInfo.setStatus(Status.ACTIVE.name());
		Mockito.when(recurringServicesDao.findByRecurringContractInfoId(Matchers.anyLong())).thenReturn(contractInfo);
		Mockito.when(recurringServicesDao.saveOrUpdateRecurringContractInfo(Matchers.any(RecurringContractInfo.class))).thenReturn(contractInfo);
		recurringServiceImpl.deleteContractInfo(recurringContractInfoDTO);
	}

	@Test
	public void testSearchContractInfoByCustomerInfoIdDaily() throws ChatakPayException {
		List<RecurringContractInfoDTO> contractList = new ArrayList<>();
		RecurringContractInfoDTO recurringContractInfoDTO = new RecurringContractInfoDTO();
		recurringContractInfoDTO.setContractExecution("Daily");
		recurringContractInfoDTO.setLastBillDate("12/05/2005");
		contractList.add(recurringContractInfoDTO);
		Mockito.when(recurringServicesDao.searchContractInfoByCustomerInfoId(Matchers.any(RecurringContractInfoDTO.class))).thenReturn(contractList);
		Assert.assertNotNull(recurringServiceImpl.searchContractInfoByCustomerInfoId(recurringContractInfoDTO, Long.parseLong("445")));
	}

	@Test
	public void testSearchContractInfoByCustomerInfoIdWeekly() throws ChatakPayException {
		List<RecurringContractInfoDTO> contractList = new ArrayList<>();
		RecurringContractInfoDTO recurringContractInfoDTO = new RecurringContractInfoDTO();
		recurringContractInfoDTO.setContractExecution("Weekly");
		recurringContractInfoDTO.setLastBillDate("12/05/2005");
		contractList.add(recurringContractInfoDTO);
		Mockito.when(recurringServicesDao.searchContractInfoByCustomerInfoId(Matchers.any(RecurringContractInfoDTO.class))).thenReturn(contractList);
		Assert.assertNotNull(recurringServiceImpl.searchContractInfoByCustomerInfoId(recurringContractInfoDTO, Long.parseLong("445")));
	}

	@Test
	public void testSearchContractInfoByCustomerInfoIdBiWeekly() throws ChatakPayException {
		List<RecurringContractInfoDTO> contractList = new ArrayList<>();
		RecurringContractInfoDTO recurringContractInfoDTO = new RecurringContractInfoDTO();
		recurringContractInfoDTO.setContractExecution("Bi-Weekly");
		recurringContractInfoDTO.setLastBillDate("12/05/2005");
		contractList.add(recurringContractInfoDTO);
		Mockito.when(recurringServicesDao.searchContractInfoByCustomerInfoId(Matchers.any(RecurringContractInfoDTO.class))).thenReturn(contractList);
		Assert.assertNotNull(recurringServiceImpl.searchContractInfoByCustomerInfoId(recurringContractInfoDTO, Long.parseLong("445")));
	}

	@Test
	public void testSearchContractInfoByCustomerInfoIdMonthly() throws ChatakPayException {
		List<RecurringContractInfoDTO> contractList = new ArrayList<>();
		RecurringContractInfoDTO recurringContractInfoDTO = new RecurringContractInfoDTO();
		recurringContractInfoDTO.setContractExecution("Monthly");
		recurringContractInfoDTO.setLastBillDate("12/05/2005");
		contractList.add(recurringContractInfoDTO);
		Mockito.when(recurringServicesDao.searchContractInfoByCustomerInfoId(Matchers.any(RecurringContractInfoDTO.class))).thenReturn(contractList);
		Assert.assertNotNull(recurringServiceImpl.searchContractInfoByCustomerInfoId(recurringContractInfoDTO, Long.parseLong("445")));
	}

	@Test
	public void testSearchContractInfoByCustomerInfoIdQuarterly() throws ChatakPayException {
		List<RecurringContractInfoDTO> contractList = new ArrayList<>();
		RecurringContractInfoDTO recurringContractInfoDTO = new RecurringContractInfoDTO();
		recurringContractInfoDTO.setContractExecution("Quarterly");
		recurringContractInfoDTO.setLastBillDate("12/05/2005");
		contractList.add(recurringContractInfoDTO);
		Mockito.when(recurringServicesDao.searchContractInfoByCustomerInfoId(Matchers.any(RecurringContractInfoDTO.class))).thenReturn(contractList);
		Assert.assertNotNull(recurringServiceImpl.searchContractInfoByCustomerInfoId(recurringContractInfoDTO, Long.parseLong("445")));
	}

	@Test
	public void testSearchContractInfoByCustomerInfoIdAnnually() throws ChatakPayException {
		List<RecurringContractInfoDTO> contractList = new ArrayList<>();
		RecurringContractInfoDTO recurringContractInfoDTO = new RecurringContractInfoDTO();
		recurringContractInfoDTO.setContractExecution("Annually");
		recurringContractInfoDTO.setLastBillDate("12/05/2005");
		contractList.add(recurringContractInfoDTO);
		Mockito.when(recurringServicesDao.searchContractInfoByCustomerInfoId(Matchers.any(RecurringContractInfoDTO.class))).thenReturn(contractList);
		Assert.assertNotNull(recurringServiceImpl.searchContractInfoByCustomerInfoId(recurringContractInfoDTO, Long.parseLong("445")));
	}

	@Test(expected = ChatakPayException.class)
	public void testSearchContractInfoByCustomerInfoIdException() throws ChatakPayException {
		List<RecurringContractInfoDTO> contractList = new ArrayList<>();
		RecurringContractInfoDTO recurringContractInfoDTO = new RecurringContractInfoDTO();
		recurringContractInfoDTO.setContractExecution("Half-Quarterly");
		recurringContractInfoDTO.setLastBillDate("12/05/2005");
		contractList.add(recurringContractInfoDTO);
		Mockito.when(recurringServicesDao.searchContractInfoByCustomerInfoId(Matchers.any(RecurringContractInfoDTO.class)))
				.thenThrow(new NullPointerException());
		Assert.assertNotNull(recurringServiceImpl.searchContractInfoByCustomerInfoId(recurringContractInfoDTO, Long.parseLong("445")));
	}

	@Test(expected = ChatakPayException.class)
	public void testUpdateContractInfo() throws ChatakPayException {
		RecurringContractInfoDTO recurringContractInfoDTO = new RecurringContractInfoDTO();
		RecurringContractInfo recurringContractInfo = new RecurringContractInfo();
		Mockito.when(recurringServicesDao.findByRecurringContractInfoId(Matchers.anyLong()))
				.thenReturn(recurringContractInfo);
		Mockito.when(recurringServicesDao.saveOrUpdateRecurringContractInfo(Matchers.any(RecurringContractInfo.class)))
				.thenReturn(recurringContractInfo);
		Assert.assertNotNull(recurringServiceImpl.updateContractInfo(recurringContractInfoDTO));
	}

	@Test
	public void testUpdateContractInfoStatusInactive() throws ChatakPayException {
		RecurringContractInfoDTO recurringContractInfoDTO = new RecurringContractInfoDTO();
		RecurringContractInfo recurringContractInfo = new RecurringContractInfo();
		recurringContractInfo.setStatus(STATUS_INACTIVE);
		recurringContractInfoDTO.setStartDate("2005-12-12 01:54:42");
		recurringContractInfoDTO.setEndDate("2005-12-12 01:54:42");
		recurringContractInfoDTO.setLastBillDate("12/05/2005");
		Mockito.when(recurringServicesDao.findByRecurringContractInfoId(Matchers.anyLong()))
				.thenReturn(recurringContractInfo);
		Mockito.when(recurringServicesDao.saveOrUpdateRecurringContractInfo(Matchers.any(RecurringContractInfo.class)))
				.thenReturn(recurringContractInfo);
		Assert.assertNotNull(recurringServiceImpl.updateContractInfo(recurringContractInfoDTO));
	}

	@Test(expected = ChatakPayException.class)
	public void testUpdateContractInfoException() throws ChatakPayException {
		RecurringContractInfoDTO recurringContractInfoDTO = new RecurringContractInfoDTO();
		RecurringContractInfo recurringContractInfo = null;
		Mockito.when(recurringServicesDao.findByRecurringContractInfoId(Matchers.anyLong()))
				.thenReturn(recurringContractInfo);
		Mockito.when(recurringServicesDao.saveOrUpdateRecurringContractInfo(Matchers.any(RecurringContractInfo.class)))
				.thenThrow(new NullPointerException());
		Assert.assertNotNull(recurringServiceImpl.updateContractInfo(recurringContractInfoDTO));

	}

	@Test(expected = ChatakPayException.class)
	public void testUpdateContractInfoChatakPayException() throws ChatakPayException {
		RecurringContractInfoDTO recurringContractInfoDTO = new RecurringContractInfoDTO();
		RecurringContractInfo recurringContractInfo = null;
		Mockito.when(recurringServicesDao.findByRecurringContractInfoId(Matchers.anyLong()))
				.thenReturn(recurringContractInfo);
		Mockito.when(recurringServicesDao.saveOrUpdateRecurringContractInfo(Matchers.any(RecurringContractInfo.class)))
				.thenThrow(ChatakPayException.class);
		Assert.assertNotNull(recurringServiceImpl.updateContractInfo(recurringContractInfoDTO));

	}

	@Test(expected = ChatakPayException.class)
	public void testDeleteCustomerInfo() throws ChatakPayException {
		RecurringCustomerInfoDTO recurringCustomerInfoDTO = new RecurringCustomerInfoDTO();
		RecurringCustomerInfo customerInfo = new RecurringCustomerInfo();
		Mockito.when(recurringServicesDao.findByRecurringCustomerInfoId(Matchers.anyLong())).thenReturn(customerInfo);
		recurringServiceImpl.deleteCustomerInfo(recurringCustomerInfoDTO);
	}

	@Test
	public void testDeleteCustomerInfoInactive() throws ChatakPayException {
		RecurringCustomerInfoDTO recurringCustomerInfoDTO = new RecurringCustomerInfoDTO();
		RecurringCustomerInfo customerInfo = new RecurringCustomerInfo();
		customerInfo.setStatus(Status.ACTIVE.name());
		Mockito.when(recurringServicesDao.findByRecurringCustomerInfoId(Matchers.anyLong())).thenReturn(customerInfo);
		Mockito.when(recurringServicesDao.saveOrUpdateRecurringCustomerInfo(Matchers.any(RecurringCustomerInfo.class)))
				.thenReturn(customerInfo);
		recurringServiceImpl.deleteCustomerInfo(recurringCustomerInfoDTO);
	}

	@Test
	public void testSerachContractInfoByContractId() throws ChatakMerchantException {
		RecurringContractInfoDTO recurringContractInfoDTO = new RecurringContractInfoDTO();
		Mockito.when(
				recurringServicesDao.searchContractInfoByContractInfoIdCon(Matchers.anyLong(), Matchers.anyString()))
				.thenReturn(recurringContractInfoDTO);
		Assert.assertNotNull(recurringServiceImpl.serachContractInfoByContractId(Long.parseLong("2345"), STATUS_ACTIVE));

	}

	@Test(expected = ChatakMerchantException.class)
	public void testSerachContractInfoByContractIdException() throws ChatakMerchantException {
		Mockito.when(
				recurringServicesDao.searchContractInfoByContractInfoIdCon(Matchers.anyLong(), Matchers.anyString()))
				.thenThrow(new NullPointerException());
		Assert.assertNotNull(recurringServiceImpl.serachContractInfoByContractId(Long.parseLong("2345"), STATUS_ACTIVE));
	}

	@Test
	public void testgetStatesByCountry() throws ChatakMerchantException {
		CountryRequest countryRequest = new CountryRequest();
		List<PGState> pgStates = new ArrayList<>();
		PGState pgState = new PGState();
		pgStates.add(pgState);
		countryRequest.setName("Asdgk");
		Mockito.when(merchantProfileDao.getStateByCountryId(Matchers.anyLong())).thenReturn(pgStates);
		Mockito.when(countryDao.getCountryByName(Matchers.anyString())).thenReturn(countryRequest);
		Assert.assertNotNull(recurringServiceImpl.getStatesByCountry(STATUS_ACTIVE));

	}

	@Test
	public void testgetStatesByCountryElse() throws ChatakMerchantException {
		CountryRequest countryRequest = new CountryRequest();
		List<PGState> pgStates = new ArrayList<>();
		Mockito.when(merchantProfileDao.getStateByCountryId(Matchers.anyLong())).thenReturn(pgStates);
		Mockito.when(countryDao.getCountryByName(Matchers.anyString())).thenReturn(countryRequest);
		Assert.assertNotNull(recurringServiceImpl.getStatesByCountry(STATUS_ACTIVE));

	}

	@Test
	public void testGetCountries() {
		List<CountryRequest> countryRequests = new ArrayList<>();
		CountryRequest countryRequest = new CountryRequest();
		countryRequest.setName("Alex");
		countryRequests.add(countryRequest);
		Mockito.when(countryDao.findAllCountries()).thenReturn(countryRequests);
		Assert.assertNotNull(recurringServiceImpl.getCountries());
	}

	@Test
	public void testValidateEmailId() throws ChatakMerchantException {
		Assert.assertNull(recurringServiceImpl.validateEmailId(STATUS_INACTIVE));
	}

	@Test
	public void testValidateUpdateEmailId() throws ChatakMerchantException {
		Assert.assertNull(recurringServiceImpl.validateUpdateEmailId(STATUS_INACTIVE, STATUS_INACTIVE));
	}
}
