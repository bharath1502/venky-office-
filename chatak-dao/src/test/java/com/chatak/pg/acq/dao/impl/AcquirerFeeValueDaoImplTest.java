/**
 * 
 */
package com.chatak.pg.acq.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.chatak.pg.acq.dao.FeeProgramDao;
import com.chatak.pg.acq.dao.MerchantDao;
import com.chatak.pg.acq.dao.model.PGAcquirerFeeValue;
import com.chatak.pg.acq.dao.repository.AcquirerFeeValueRepository;

/**
 * @Author: Girmiti Software
 * @Date: 15-Feb-2018
 * @Time: 3:04:13 pm
 * @Version: 1.0
 * @Comments:
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class AcquirerFeeValueDaoImplTest {

	@InjectMocks
	AcquirerFeeValueDaoImpl acquirerFeeValueDaoImpl;

	@Mock
	AcquirerFeeValueRepository acquirerFeeValueRepository;

	@Mock
	MerchantDao merchantDao;

	@Mock
	FeeProgramDao feeProgramDao;

	@Test
	public void testGetAcquirerFeeValuesByFeeProgramId() {
		acquirerFeeValueDaoImpl.getAcquirerFeeValuesByFeeProgramId(Long.parseLong("123"));
	}

	@Test
	public void testRemoveAcquirerFeeValues() {
		List<PGAcquirerFeeValue> acquirerFeeValuesDaoDetails = new ArrayList<>();
		acquirerFeeValueDaoImpl.removeAcquirerFeeValues(acquirerFeeValuesDaoDetails);
	}

}
