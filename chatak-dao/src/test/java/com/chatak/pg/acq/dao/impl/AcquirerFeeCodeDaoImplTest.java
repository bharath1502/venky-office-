/**
 * 
 */
package com.chatak.pg.acq.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import com.chatak.pg.acq.dao.model.PGAcquirerFeeCode;
import com.chatak.pg.acq.dao.repository.AcquirerFeeCodeRepository;

/**
 * @Author: Girmiti Software
 * @Date: 15-Feb-2018
 * @Time: 2:36:20 pm
 * @Version: 1.0
 * @Comments:
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class AcquirerFeeCodeDaoImplTest {

	@InjectMocks
	AcquirerFeeCodeDaoImpl acquirerFeeCodeDaoImpl;

	@Mock
	AcquirerFeeCodeRepository acquirerFeeCodeRepository;

	@Mock
	PGAcquirerFeeCode pgAcquirerFeeCode;

	@Test
	public void testGetAllFeeCodes() {
		acquirerFeeCodeDaoImpl.getAllFeeCodes();
	}

	@Test
	public void testGetAcquirerFeeCodeByPartnerId() {
		acquirerFeeCodeDaoImpl.getAcquirerFeeCodeByPartnerId(Long.parseLong("1234"));
	}

	@Test
	public void testUpdateAcquirerFeecode() {
		acquirerFeeCodeDaoImpl.updateAcquirerFeecode(pgAcquirerFeeCode);
	}

	@Test
	public void testGetAcquirerFeeCodeByFeeCodeId() {
		List<PGAcquirerFeeCode> list = new ArrayList<>();
		PGAcquirerFeeCode acquirerFeeCode = new PGAcquirerFeeCode();
		list.add(acquirerFeeCode);
		Mockito.when(acquirerFeeCodeRepository.findByAcquirerFeeCodeId(Matchers.anyLong())).thenReturn(list);
		acquirerFeeCodeDaoImpl.getAcquirerFeeCodeByFeeCodeId(Long.parseLong("1234"));
	}

	@Test
	public void testGetAcquirerFeeCodeByFeeCodeIdElse() {
		acquirerFeeCodeDaoImpl.getAcquirerFeeCodeByFeeCodeId(Long.parseLong("1234"));
	}

	@Test
	public void testAddAcquirerFeecode() {
		acquirerFeeCodeDaoImpl.addAcquirerFeecode(pgAcquirerFeeCode);
	}

	@Test
	public void testGetAcquirerFeeCodeByAcquirerNameAndPartnerIdAndMerchantCode() {
		acquirerFeeCodeDaoImpl.getAcquirerFeeCodeByAcquirerNameAndPartnerIdAndMerchantCode("1", Long.parseLong("1234"),
				"1");
	}

	@Test
	public void testGetFeeCodesByPartnerIdAndMerchantCode() {
		acquirerFeeCodeDaoImpl.getFeeCodesByPartnerIdAndMerchantCode(Long.parseLong("1234"), "1");
	}

	@Test
	public void testGetAcquirerFeeCodesByMerchantCode() {
		acquirerFeeCodeDaoImpl.getAcquirerFeeCodesByMerchantCode("1");
	}

}
