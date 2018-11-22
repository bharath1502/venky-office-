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
import com.chatak.pg.acq.dao.model.PGMerchant;
import com.chatak.pg.acq.dao.repository.BINRepository;
import com.chatak.pg.acq.dao.repository.MerchantRepository;
import com.chatak.pg.constants.PGConstants;

@RunWith(MockitoJUnitRunner.class)
public class BINServiceImplTest {

	@InjectMocks
	BINServiceImpl bINServiceImpl = new BINServiceImpl();
	
	@Mock
	TransactionRequest transactionRequests;

	@Mock
	PGMerchant pgMerchant;
	@Mock
	private BINRepository binRepository;

	@Mock
	TransactionDao transactionDao;

	@Mock
    MerchantRepository merchantRepository;

	private static final String CARD_NUMBER="78686778789878";
	private static final Long VALUE = Long.valueOf("78686778789");

	@Test
	public void testValidateBin() throws   InvalidRequestException, InstantiationException, IllegalAccessException {
		transactionRequests=new TransactionRequest();
		pgMerchant = new PGMerchant();
		pgMerchant.setMerchantType(PGConstants.SUB_MERCHANT);
        pgMerchant.setParentMerchantId(1l);
        pgMerchant.setStatus(PGConstants.STATUS_ACTIVE);
		List<Long> cardNumberList=new ArrayList<>();
		cardNumberList.add(VALUE);
		PGBINRange binRange = new PGBINRange();
		binRange.setStatus(1);
		Mockito.when(binRepository.findByBin(Matchers.anyLong())).thenReturn(binRange);
		Mockito.when(transactionDao.fetchCardProgramDetailsByMerchantCode(Matchers.any(com.chatak.pg.model.TransactionRequest.class))).thenReturn(cardNumberList);
		Mockito.when(merchantRepository.findById(Matchers.anyLong())).thenReturn(pgMerchant);
		bINServiceImpl.validateCardProgram(CARD_NUMBER, transactionRequests, pgMerchant);
	}
  
}
