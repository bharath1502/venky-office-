package com.chatak.merchant.service.impl;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.context.MessageSource;

import com.chatak.merchant.exception.ChatakMerchantException;
import com.chatak.merchant.exception.ChatakPayException;
import com.chatak.pg.acq.dao.BlackListedCardDao;
import com.chatak.pg.acq.dao.MerchantDao;
import com.chatak.pg.acq.dao.MerchantProfileDao;
import com.chatak.pg.acq.dao.RefundTransactionDao;
import com.chatak.pg.acq.dao.TerminalDao;
import com.chatak.pg.acq.dao.TransactionDao;
import com.chatak.pg.acq.dao.VoidTransactionDao;
import com.chatak.pg.acq.dao.model.PGMerchant;
import com.chatak.pg.acq.dao.model.PGTerminal;
import com.chatak.pg.acq.dao.model.PGTransaction;
import com.chatak.pg.acq.dao.repository.AccountRepository;
import com.chatak.pg.bean.Response;
import com.chatak.pg.constants.ActionErrorCode;
import com.chatak.pg.model.TransactionRequest;
import com.chatak.pg.model.VirtualTerminalSaleDTO;
import com.chatak.pg.util.Constants;
import com.chatak.pg.util.Properties;

@RunWith(MockitoJUnitRunner.class)
public class RestPaymentServiceImplTest {

	@InjectMocks
	private RestPaymentServiceImpl restPaymentServiceImpl = new RestPaymentServiceImpl();

	@Mock
	TransactionDao transactionDao;

	@Mock
	MerchantDao merchantDao;

	@Mock
	TerminalDao terminalDao;

	@Mock
	private MessageSource messageSource;

	@Mock
	BlackListedCardDao blackListedCardDao;

	@Mock
	AccountRepository accountRepository;

	@Mock
	RefundTransactionDao refundTransactionDao;

	@Mock
	VoidTransactionDao voidTransactionDao;

	@Mock
	MerchantProfileDao merchantProfileDao;

	@Mock
	VirtualTerminalSaleDTO terminalSaleDTO;

	@Before
    public void init() {
      java.util.Properties propsExportedLocal = new java.util.Properties();
      propsExportedLocal.setProperty("max.download.limit", "12");
      propsExportedLocal.setProperty("thread.max.per.route", "500");
      propsExportedLocal.setProperty("thread.pool.size", "500");
      Properties.mergeProperties(propsExportedLocal);
    }

	@Test(expected = ChatakPayException.class)
	public void testDoSaleResponseNotnull() throws ChatakPayException {
		terminalSaleDTO = new VirtualTerminalSaleDTO();
		Response blackListedCardResponse = new Response();
		blackListedCardResponse.setErrorCode(ActionErrorCode.ERROR_CODE_Z5);
		terminalSaleDTO.setCardNum(Constants.CARD_NUM_MAXLEN);
		Mockito.when(blackListedCardDao.getCardDataByCardNumber((BigInteger) Matchers.any()))
				.thenReturn(blackListedCardResponse);
		Assert.assertNotNull(restPaymentServiceImpl.doSale(terminalSaleDTO));
	}

	@Test
	public void testGetTransaction() throws ChatakPayException {
		PGTransaction pgTransaction = new PGTransaction();
		Mockito.when(voidTransactionDao.findTransaction(Matchers.anyString(), Matchers.anyString(),
				Matchers.anyString(), Matchers.anyString(), Matchers.anyString())).thenReturn(pgTransaction);
		pgTransaction.setPan("z+DrxgikaCRTpgnHR8xXwA+XKJra06us6DmM1Zm63BU=");
		Assert.assertNotNull(restPaymentServiceImpl.getTransaction(Constants.CARD_NUM_MAXLEN, Constants.CARD_NUM_MAXLEN,
				Constants.CARD_NUM_MAXLEN, Constants.CARD_NUM_MAXLEN, Constants.CARD_NUM_MAXLEN));
	}

	@Test
	public void testGetTransactionNull() throws ChatakPayException {
		PGTransaction pgTransaction = null;
		Mockito.when(voidTransactionDao.findTransaction(Matchers.anyString(), Matchers.anyString(),
				Matchers.anyString(), Matchers.anyString(), Matchers.anyString())).thenReturn(pgTransaction);
		Assert.assertNotNull(restPaymentServiceImpl.getTransaction(Constants.CARD_NUM_MAXLEN, Constants.CARD_NUM_MAXLEN,
				Constants.CARD_NUM_MAXLEN, Constants.CARD_NUM_MAXLEN, Constants.CARD_NUM_MAXLEN));
	}

	@Test
	public void testGetTransactionByRefId() throws ChatakPayException {
		PGTransaction pgTransaction = new PGTransaction();
		pgTransaction.setPan("z+DrxgikaCRTpgnHR8xXwA+XKJra06us6DmM1Zm63BU=");
		pgTransaction.setTxnTotalAmount(Constants.ONE_THOUSAND_LONG);
		Mockito.when(transactionDao.getTransaction(Matchers.anyString(), Matchers.anyString(), Matchers.anyString()))
				.thenReturn(pgTransaction);
		Mockito.when(refundTransactionDao.getRefundedAmountOnTxnId(Matchers.anyString())).thenReturn(Long.parseLong("234"));
		Assert.assertNotNull(restPaymentServiceImpl.getTransactionByRefId(Constants.CARD_NUM_MAXLEN,
				Constants.CARD_NUM_MAXLEN, Constants.CARD_NUM_MAXLEN, "sale"));
	}

	@Test
	public void testGetTransactionByRefIdElse() throws ChatakPayException {
		PGTransaction pgTransaction = new PGTransaction();
		pgTransaction.setPan("z+DrxgikaCRTpgnHR8xXwA+XKJra06us6DmM1Zm63BU=");
		Mockito.when(transactionDao.getTransactionOnTxnIdAndTxnType(Matchers.anyString(), Matchers.anyString(),
				Matchers.anyString(), Matchers.anyString())).thenReturn(pgTransaction);
		Mockito.when(refundTransactionDao.getRefundedAmountOnTxnId(Matchers.anyString())).thenReturn(null);
		Assert.assertNotNull(restPaymentServiceImpl.getTransactionByRefId(Constants.CARD_NUM_MAXLEN,
				Constants.CARD_NUM_MAXLEN, Constants.CARD_NUM_MAXLEN, Constants.CARD_NUM_MAXLEN));
	}

	@Test
	public void testGetTransactionByRefIdNull() throws ChatakPayException {
		PGTransaction pgTransaction = null;
		Mockito.when(transactionDao.getTransactionOnTxnIdAndTxnType(Matchers.anyString(), Matchers.anyString(),
				Matchers.anyString(), Matchers.anyString())).thenReturn(pgTransaction);
		Mockito.when(refundTransactionDao.getRefundedAmountOnTxnId(Matchers.anyString())).thenReturn(null);
		Assert.assertNotNull(restPaymentServiceImpl.getTransactionByRefId(Constants.CARD_NUM_MAXLEN,
				Constants.CARD_NUM_MAXLEN, Constants.CARD_NUM_MAXLEN, Constants.CARD_NUM_MAXLEN));
	}

	@Test
	public void testGetTransactionByRefIdException() throws ChatakPayException {
		PGTransaction pgTransaction = new PGTransaction();
		pgTransaction.setPan("z+DrxgikaCRTpgnHR8xXwA+XKJra06us6DmM1Zm63BU=");
		Mockito.when(transactionDao.getTransactionOnTxnIdAndTxnType(Matchers.anyString(), Matchers.anyString(),
				Matchers.anyString(), Matchers.anyString())).thenReturn(pgTransaction);
		Mockito.when(refundTransactionDao.getRefundedAmountOnTxnId(Matchers.anyString()))
				.thenThrow(new NullPointerException());
		Assert.assertNotNull(restPaymentServiceImpl.getTransactionByRefId(Constants.CARD_NUM_MAXLEN,
				Constants.CARD_NUM_MAXLEN, Constants.CARD_NUM_MAXLEN, Constants.CARD_NUM_MAXLEN));
	}

	@Test
	public void testGetTransactionByRefIdForRefund() throws ChatakMerchantException {
		List<PGTransaction> pgList = new ArrayList<PGTransaction>();
		PGTransaction pgTransaction = new PGTransaction();
		pgTransaction.setAuthId(Constants.CARD_NUM_MAXLEN);
		pgList.add(pgTransaction);
		Mockito.when(refundTransactionDao
				.findByMerchantIdAndTerminalIdAndTransactionIdAndStatusAndMerchantSettlementStatusInAndRefundStatusNotLike(
						Matchers.anyString(), Matchers.anyString(), Matchers.anyString(), Matchers.anyInt(), Matchers.anyInt(),Matchers.anyList()))
				.thenReturn(pgList);
		Mockito.when(transactionDao.getTransactionOnTxnIdAndTxnType(Matchers.anyString(), Matchers.anyString(), Matchers.anyString(),
				Matchers.anyString())).thenReturn(pgTransaction);
		Mockito.when(refundTransactionDao.getRefundedAmountOnTxnId(Matchers.anyString())).thenReturn(Long.parseLong("123"));
		Mockito.when(transactionDao.getTransaction(Matchers.anyString(), Matchers.anyString(), Matchers.anyString(), Matchers.anyString(),
				Matchers.anyString())).thenReturn(pgTransaction);
		Assert.assertNotNull(restPaymentServiceImpl.getTransactionByRefIdForRefund(Constants.CARD_NUM_MAXLEN,
				Constants.CARD_NUM_MAXLEN, Constants.CARD_NUM_MAXLEN, Constants.CARD_NUM_MAXLEN));

	}

	@Test
	public void testGetTransactionByRefIdForRefundNull() throws ChatakMerchantException {
		List<PGTransaction> pgList = null;
		PGTransaction pgTransaction = null;
		Mockito.when(refundTransactionDao
				.findByMerchantIdAndTerminalIdAndTransactionIdAndStatusAndMerchantSettlementStatusInAndRefundStatusNotLike(
						Matchers.anyString(), Matchers.anyString(), Matchers.anyString(), Matchers.anyInt(), Matchers.anyInt(),Matchers.anyList()))
				.thenReturn(pgList);
		Mockito.when(transactionDao.getTransactionOnTxnIdAndTxnType(Matchers.anyString(), Matchers.anyString(), Matchers.anyString(),
				Matchers.anyString())).thenReturn(pgTransaction);
		Mockito.when(refundTransactionDao.getRefundedAmountOnTxnId(Matchers.anyString())).thenReturn(Long.parseLong("123"));
		Mockito.when(transactionDao.getTransaction(Matchers.anyString(), Matchers.anyString(), Matchers.anyString(), Matchers.anyString(),
				Matchers.anyString())).thenReturn(pgTransaction);
		Assert.assertNotNull(restPaymentServiceImpl.getTransactionByRefIdForRefund(Constants.CARD_NUM_MAXLEN,
				Constants.CARD_NUM_MAXLEN, Constants.CARD_NUM_MAXLEN, Constants.CARD_NUM_MAXLEN));

	}

	@Test
	public void testGetTransactionByRefIdForRefundNullElse() throws ChatakMerchantException {
		List<PGTransaction> pgList = new ArrayList<>();
		PGTransaction pgTransaction = null;
		pgList.add(pgTransaction);
		Mockito.when(refundTransactionDao
				.findByMerchantIdAndTerminalIdAndTransactionIdAndStatusAndMerchantSettlementStatusInAndRefundStatusNotLike(
						Matchers.anyString(), Matchers.anyString(), Matchers.anyString(), Matchers.anyInt(), Matchers.anyInt(),Matchers.anyList()))
				.thenReturn(pgList);
		Mockito.when(transactionDao.getTransactionOnTxnIdAndTxnType(Matchers.anyString(), Matchers.anyString(), Matchers.anyString(),
				Matchers.anyString())).thenReturn(pgTransaction);
		Mockito.when(refundTransactionDao.getRefundedAmountOnTxnId(Matchers.anyString())).thenReturn(Long.parseLong("123"));
		Mockito.when(transactionDao.getTransaction(Matchers.anyString(), Matchers.anyString(), Matchers.anyString(), Matchers.anyString(),
				Matchers.anyString())).thenReturn(pgTransaction);
		Assert.assertNotNull(restPaymentServiceImpl.getTransactionByRefIdForRefund(Constants.CARD_NUM_MAXLEN,
				Constants.CARD_NUM_MAXLEN, Constants.CARD_NUM_MAXLEN, Constants.CARD_NUM_MAXLEN));

	}

	@Test
	public void testGetMerchantIdAndTerminalId() throws ChatakPayException {
		PGMerchant pgMerchant = new PGMerchant();
		PGTerminal pgTerminal = new PGTerminal();
		pgTerminal.setTerminalId(Constants.ONE_THOUSAND_LONG);
		Mockito.when(merchantProfileDao.getMerchantById(Matchers.anyLong())).thenReturn(pgMerchant);
		Mockito.when(terminalDao.getTerminalonMerchantCode(Matchers.anyLong())).thenReturn(pgTerminal);
		Assert.assertNotNull(restPaymentServiceImpl.getMerchantIdAndTerminalId(Constants.CARD_NUM_MAXLEN));

	}

	@Test
	public void testGetMerchantIdAndTerminalIdElse() throws ChatakPayException {
		PGMerchant pgMerchant = null;
		PGTerminal pgTerminal = new PGTerminal();
		pgTerminal.setTerminalId(Constants.ONE_THOUSAND_LONG);
		Mockito.when(merchantProfileDao.getMerchantById(Matchers.anyLong())).thenReturn(pgMerchant);
		Mockito.when(terminalDao.getTerminalonMerchantCode(Matchers.anyLong())).thenReturn(pgTerminal);
		Assert.assertNotNull(restPaymentServiceImpl.getMerchantIdAndTerminalId(Constants.CARD_NUM_MAXLEN));

	}

	@Test
	public void testGetMerchantIdAndTerminalIdNull() throws ChatakPayException {
		PGMerchant pgMerchant = new PGMerchant();
		PGTerminal pgTerminal = null;
		Mockito.when(merchantProfileDao.getMerchantById(Matchers.anyLong())).thenReturn(pgMerchant);
		Mockito.when(terminalDao.getTerminalonMerchantCode(Matchers.anyLong())).thenReturn(pgTerminal);
		Assert.assertNotNull(restPaymentServiceImpl.getMerchantIdAndTerminalId(Constants.CARD_NUM_MAXLEN));

	}

	@Test(expected = NullPointerException.class)
	public void testProcessPopupVoidOrRefund() throws ChatakMerchantException {
		TransactionRequest transactionRequest = new TransactionRequest();
		PGTransaction pgTransaction = new PGTransaction();
		Mockito.when(refundTransactionDao.getTransactionForVoidOrRefundByAccountTransactionId(Matchers.anyString(),
				Matchers.anyString())).thenReturn(pgTransaction);
		Assert.assertNotNull(restPaymentServiceImpl.processPopupVoidOrRefund(transactionRequest));
	}

	@Test(expected = NullPointerException.class)
	public void testProcessPopupVoidOrRefundElse() throws ChatakMerchantException {
		TransactionRequest transactionRequest = new TransactionRequest();
		PGTransaction pgTransaction = null;
		Mockito.when(refundTransactionDao.getTransactionForVoidOrRefundByAccountTransactionId(Matchers.anyString(),
				Matchers.anyString())).thenReturn(pgTransaction);
		Assert.assertNotNull(restPaymentServiceImpl.processPopupVoidOrRefund(transactionRequest));
	}

}
