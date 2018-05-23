package com.chatak.pay.service.impl;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import com.chatak.pay.exception.InvalidRequestException;
import com.chatak.pg.acq.dao.model.PGBINRange;
import com.chatak.pg.acq.dao.repository.BINRepository;

@RunWith(MockitoJUnitRunner.class)
public class BINServiceImplTest {

	@InjectMocks
	BINServiceImpl bINServiceImpl = new BINServiceImpl();

	@Mock
	private BINRepository binRepository;

	@Test(expected = InvalidRequestException.class)
	public void testValidateBin() throws InvalidRequestException {
		PGBINRange binRange = new PGBINRange();
		binRange.setStatus(1);
		Mockito.when(binRepository.findByBin(Matchers.anyLong())).thenReturn(binRange);
		bINServiceImpl.validateBin("123456");
	}

	@Test(expected = InvalidRequestException.class)
	public void testValidateBinException() throws InvalidRequestException {
		bINServiceImpl.validateBin(null);
	}

}
