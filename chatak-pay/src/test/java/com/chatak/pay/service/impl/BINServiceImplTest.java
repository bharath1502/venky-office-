package com.chatak.pay.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import com.chatak.pay.controller.model.TransactionRequest;
import com.chatak.pay.exception.InvalidRequestException;
import com.chatak.pg.acq.dao.TransactionDao;
import com.chatak.pg.acq.dao.model.PGBINRange;
import com.chatak.pg.acq.dao.repository.BINRepository;

@RunWith(MockitoJUnitRunner.class)
public class BINServiceImplTest {

	@InjectMocks
	BINServiceImpl bINServiceImpl = new BINServiceImpl();
	
	@Mock
	TransactionRequest transactionRequests;
	
	@Mock
	private BINRepository binRepository;

	@Mock
	TransactionDao transactionDao;
	 
	private static final String CARD_NUMBER="78686778789878";
	private static final Long Value=Long.valueOf("78686778789");
	@Test
	public void testValidateBin() throws   InvalidRequestException, InstantiationException, IllegalAccessException {
		transactionRequests=new TransactionRequest();
		List<Long> cardNumberList=new ArrayList<>();
		cardNumberList.add(Value);
		PGBINRange binRange = new PGBINRange();
		binRange.setStatus(1);
		Mockito.when(binRepository.findByBin(Matchers.anyLong())).thenReturn(binRange);
		Mockito.when(transactionDao.fetchCardProgramDetailsByMerchantCode(Matchers.any(com.chatak.pg.model.TransactionRequest.class))).thenReturn(cardNumberList);
		bINServiceImpl.validateCardProgram(CARD_NUMBER, transactionRequests);
	}
  
}
