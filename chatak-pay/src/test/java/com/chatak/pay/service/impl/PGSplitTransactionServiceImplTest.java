package com.chatak.pay.service.impl;

import java.sql.Timestamp;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.context.MessageSource;

import com.chatak.pay.controller.model.CardData;
import com.chatak.pay.controller.model.SplitStatusRequest;
import com.chatak.pay.controller.model.SplitTxnData;
import com.chatak.pay.controller.model.TransactionRequest;
import com.chatak.pay.controller.model.TransactionResponse;
import com.chatak.pay.exception.SplitTransactionException;
import com.chatak.pay.service.PGTransactionService;
import com.chatak.pg.acq.dao.RefundTransactionDao;
import com.chatak.pg.acq.dao.SplitTransactionDao;
import com.chatak.pg.acq.dao.TransactionDao;
import com.chatak.pg.acq.dao.VoidTransactionDao;
import com.chatak.pg.acq.dao.model.PGSplitTransaction;
import com.chatak.pg.acq.dao.model.PGTransaction;
import com.chatak.pg.acq.dao.repository.SplitTransactionRepository;
import com.chatak.pg.acq.dao.repository.TransactionRepository;
import com.chatak.pg.enums.ShareModeEnum;

@RunWith(MockitoJUnitRunner.class)
public class PGSplitTransactionServiceImplTest {

	@InjectMocks
	PGSplitTransactionServiceImpl pgSplitTransactionServiceImpl = new PGSplitTransactionServiceImpl();

	@Mock
	private MessageSource messageSource;

	@Mock
	SplitTransactionDao splitTransactionDao;

	@Mock
	SplitTransactionRepository splitTransactionRepository;

	@Mock
	PGTransactionService pgTransactionService;

	@Mock
	TransactionDao transactionDao;

	@Mock
	TransactionRepository transactionRepository;

	@Mock
	VoidTransactionDao voidTransactionDao;

	@Mock
	RefundTransactionDao refundTransactionDao;

	@Test(expected = SplitTransactionException.class)
	public void testProcessLogSplitTransactionSplitPay() throws SplitTransactionException {
		TransactionRequest transactionRequest = new TransactionRequest();
		TransactionResponse transactionResponse = new TransactionResponse();
		transactionRequest.setShareMode(ShareModeEnum.SPLIT_PAY);
		pgSplitTransactionServiceImpl.processLogSplitTransaction(transactionRequest, transactionResponse);
	}

	@Test
	public void testProcessLogSplitTransaction() throws SplitTransactionException {
		TransactionRequest transactionRequest = new TransactionRequest();
		TransactionResponse transactionResponse = new TransactionResponse();
		SplitTxnData splitTxnData = new SplitTxnData();
		transactionRequest.setSplitTxnData(splitTxnData);
		transactionRequest.setShareMode(ShareModeEnum.PAY_SOMEONE);
		pgSplitTransactionServiceImpl.processLogSplitTransaction(transactionRequest, transactionResponse);
	}

	@Test
	public void testProcessLogSplitTransactionElse() throws SplitTransactionException {
		TransactionRequest transactionRequest = new TransactionRequest();
		TransactionResponse transactionResponse = new TransactionResponse();
		pgSplitTransactionServiceImpl.processLogSplitTransaction(transactionRequest, transactionResponse);
	}

	@Test
	public void testGetSplitTxnStatus() throws SplitTransactionException {
		SplitStatusRequest splitStatusRequest = new SplitStatusRequest();
		PGSplitTransaction pgSplitTransaction = new PGSplitTransaction();
		pgSplitTransaction.setStatus(Long.parseLong("0"));
		Mockito.when(splitTransactionDao.getPGSplitTransactionByMerchantIdAndPgRefTransactionIdAndSplitAmount(
				Matchers.anyString(), Matchers.anyString(), Matchers.anyLong())).thenReturn(pgSplitTransaction);
		pgSplitTransactionServiceImpl.getSplitTxnStatus(splitStatusRequest);
	}

	@Test
	public void testGetSplitTxnStatusElse() throws SplitTransactionException {
		SplitStatusRequest splitStatusRequest = new SplitStatusRequest();
		PGTransaction txnToVoid = new PGTransaction();
		Timestamp requestTime = new Timestamp(Long.parseLong("5"));
		PGSplitTransaction pgSplitTransaction = new PGSplitTransaction();
		pgSplitTransaction.setStatus(Long.parseLong("1"));
		pgSplitTransaction.setCreatedDate(requestTime);
		Mockito.when(splitTransactionDao.getPGSplitTransactionByMerchantIdAndPgRefTransactionIdAndSplitAmount(
				Matchers.anyString(), Matchers.anyString(), Matchers.anyLong())).thenReturn(pgSplitTransaction);
		Mockito.when(voidTransactionDao.findTransactionToReversalByMerchantIdAndPGTxnId(Matchers.anyString(),
				Matchers.anyString())).thenReturn(txnToVoid);
		pgSplitTransactionServiceImpl.getSplitTxnStatus(splitStatusRequest);
	}

	@Test
	public void testGetSplitTxnStatusNull() {
		SplitStatusRequest splitStatusRequest = new SplitStatusRequest();
		pgSplitTransactionServiceImpl.getSplitTxnStatus(splitStatusRequest);
	}

	@Test(expected = SplitTransactionException.class)
	public void testValidateSplitTransactionIf() throws SplitTransactionException {
		TransactionRequest transactionRequest = new TransactionRequest();
		PGSplitTransaction pgSplitTransaction = new PGSplitTransaction();
		transactionRequest.setMobileNumber("234");
		pgSplitTransaction.setStatus(Long.parseLong("1"));
		Mockito.when(splitTransactionDao.getPGSplitTransactionByMerchantIdAndPgRefTransactionIdAndSplitAmount(
				Matchers.anyString(), Matchers.anyString(), Matchers.anyLong())).thenReturn(pgSplitTransaction);
		pgSplitTransactionServiceImpl.validateSplitTransaction(transactionRequest);
	}

	@Test(expected = SplitTransactionException.class)
	public void testValidateSplitTransactionElseIf() throws SplitTransactionException {
		TransactionRequest transactionRequest = new TransactionRequest();
		PGSplitTransaction pgSplitTransaction = new PGSplitTransaction();
		transactionRequest.setMobileNumber("234");
		pgSplitTransaction.setStatus(Long.parseLong("0"));
		Mockito.when(splitTransactionDao.getPGSplitTransactionByMerchantIdAndPgRefTransactionIdAndSplitAmount(
				Matchers.anyString(), Matchers.anyString(), Matchers.anyLong())).thenReturn(pgSplitTransaction);
		pgSplitTransactionServiceImpl.validateSplitTransaction(transactionRequest);
	}

	@Test
	public void testValidateSplitTransaction() throws SplitTransactionException {
		TransactionRequest transactionRequest = new TransactionRequest();
		PGSplitTransaction pgSplitTransaction = new PGSplitTransaction();
		PGTransaction pgTransaction = new PGTransaction();
		transactionRequest.setMobileNumber("234");
		pgSplitTransaction.setStatus(Long.parseLong("2"));
		Mockito.when(splitTransactionDao.getPGSplitTransactionByMerchantIdAndPgRefTransactionIdAndSplitAmount(
				Matchers.anyString(), Matchers.anyString(), Matchers.anyLong())).thenReturn(pgSplitTransaction);
		Mockito.when(voidTransactionDao.findTransactionToReversalByMerchantIdAndPGTxnId(Matchers.anyString(),
				Matchers.anyString())).thenReturn(pgTransaction);
		pgSplitTransactionServiceImpl.validateSplitTransaction(transactionRequest);
	}

	@Test
	public void testUpdateSplitTransactionLog() throws SplitTransactionException {
		TransactionRequest transactionRequest = new TransactionRequest();
		TransactionResponse transactionResponse = new TransactionResponse();
		PGSplitTransaction pgSplitTransaction = new PGSplitTransaction();
		CardData cardData = new CardData();
		cardData.setCardNumber("23423");
		transactionRequest.setCardData(cardData);
		transactionResponse.setErrorCode("00");
		Mockito.when(splitTransactionDao.getPGSplitTransactionByMerchantIdAndPgRefTransactionIdAndSplitAmount(
				Matchers.anyString(), Matchers.anyString(), Matchers.anyLong())).thenReturn(pgSplitTransaction);
		pgSplitTransactionServiceImpl.updateSplitTransactionLog(transactionRequest, transactionResponse);
	}

}
