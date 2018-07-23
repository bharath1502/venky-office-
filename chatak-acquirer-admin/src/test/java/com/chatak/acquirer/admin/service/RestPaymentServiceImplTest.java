package com.chatak.acquirer.admin.service;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.context.MessageSource;

import com.chatak.acquirer.admin.exception.ChatakAdminException;
import com.chatak.acquirer.admin.exception.ChatakPayException;
import com.chatak.acquirer.admin.service.impl.RestPaymentServiceImpl;
import com.chatak.pg.acq.dao.MerchantDao;
import com.chatak.pg.acq.dao.MerchantUpdateDao;
import com.chatak.pg.acq.dao.RefundTransactionDao;
import com.chatak.pg.acq.dao.TerminalDao;
import com.chatak.pg.acq.dao.TransactionDao;
import com.chatak.pg.acq.dao.VoidTransactionDao;
import com.chatak.pg.acq.dao.model.PGMerchant;
import com.chatak.pg.acq.dao.model.PGTerminal;
import com.chatak.pg.acq.dao.model.PGTransaction;
import com.chatak.pg.acq.dao.repository.AccountRepository;
import com.chatak.pg.acq.dao.repository.AccountTransactionsRepository;
import com.chatak.pg.model.TransactionRequest;
import com.chatak.pg.model.VirtualTerminalAdjustmentDTO;
import com.chatak.pg.model.VirtualTerminalCaptureDTO;
import com.chatak.pg.model.VirtualTerminalPreAuthDTO;
import com.chatak.pg.model.VirtualTerminalRefundDTO;
import com.chatak.pg.model.VirtualTerminalSaleDTO;
import com.chatak.pg.model.VirtualTerminalVoidDTO;

@RunWith(MockitoJUnitRunner.class)
public class RestPaymentServiceImplTest {

	@InjectMocks
	RestPaymentServiceImpl restPaymentServiceImpl = new RestPaymentServiceImpl();

	@Mock
	MerchantDao merchantDao;

	@Mock
	TransactionDao transactionDao;

	@Mock
	TerminalDao terminalDao;

	@Mock
	MessageSource messageSource;

	@Mock
	AccountRepository accountRepository;

	@Mock
	AccountTransactionsRepository accountTransactionsRepository;

	@Mock
	RefundTransactionDao refundTransactionDao;

	@Mock
	VoidTransactionDao voidTransactionDao;

	@Mock
	MerchantUpdateDao merchantUpdateDao;

	@Test(expected=ChatakPayException.class)
	public void testDoSale() throws ChatakPayException {
		VirtualTerminalSaleDTO terminalSaleDTO = new VirtualTerminalSaleDTO();
		terminalSaleDTO.setCity("abc");
		terminalSaleDTO.setCountry("in");
		terminalSaleDTO.setEmail("abcd");
		terminalSaleDTO.setState("ka");
		terminalSaleDTO.setZip("z");
		restPaymentServiceImpl.doSale(terminalSaleDTO);
	}

	@Test(expected=ChatakPayException.class)
	public void testDoVoid() throws ChatakPayException {
		VirtualTerminalVoidDTO voidDTO = new VirtualTerminalVoidDTO();
		restPaymentServiceImpl.doVoid(voidDTO);
	}

	@Test(expected=ChatakPayException.class)
	public void testDoPreAuth() throws ChatakPayException {
		VirtualTerminalPreAuthDTO preAuthDTO = new VirtualTerminalPreAuthDTO();
		preAuthDTO.setCvv(1);
		preAuthDTO.setStreet("654");
		preAuthDTO.setEmail("USA");
		restPaymentServiceImpl.doPreAuth(preAuthDTO);
	}

	@Test(expected=ChatakPayException.class)
	public void testDoPreAuthCapture() throws ChatakPayException {
		VirtualTerminalCaptureDTO captureDTO = new VirtualTerminalCaptureDTO();
		restPaymentServiceImpl.doPreAuthCapture(captureDTO);
	}

	@Test(expected=ChatakPayException.class)
	public void testDoRefund() throws ChatakPayException {
		VirtualTerminalRefundDTO refundDTO = new VirtualTerminalRefundDTO();
		refundDTO.setSubTotal(Double.parseDouble("6543"));
		refundDTO.setFeeAmount(Double.parseDouble("653"));
		refundDTO.setTxnAmount(Double.parseDouble("65"));
		refundDTO.setTxnRefNum("number");
		restPaymentServiceImpl.doRefund(refundDTO);
	}

	@Test(expected=ChatakPayException.class)
	public void testDoRefundElse() throws ChatakPayException {
		VirtualTerminalRefundDTO refundDTO = new VirtualTerminalRefundDTO();
		refundDTO.setSubTotal(Double.parseDouble("6543"));
		refundDTO.setFeeAmount(Double.parseDouble("653"));
		refundDTO.setTxnAmount(Double.parseDouble("65"));
		restPaymentServiceImpl.doRefund(refundDTO);
	}

	@Test(expected=ChatakPayException.class)
	public void testDoRefundException() throws ChatakPayException {
		VirtualTerminalRefundDTO refundDTO = new VirtualTerminalRefundDTO();
		restPaymentServiceImpl.doRefund(refundDTO);
	}

	@Test(expected=ChatakPayException.class)
	public void testDoAdjust() throws ChatakPayException {
		VirtualTerminalAdjustmentDTO adjustmentDTO = new VirtualTerminalAdjustmentDTO();
		restPaymentServiceImpl.doAdjust(adjustmentDTO);
	}

	@Test
	public void testGetTransaction() throws ChatakPayException {
		PGTransaction pgTransaction = new PGTransaction();
		pgTransaction.setPan("654");
		Mockito.when(voidTransactionDao.findTransaction(Matchers.anyString(), Matchers.anyString(),
				Matchers.anyString(), Matchers.anyString(), Matchers.anyString())).thenReturn(pgTransaction);
		restPaymentServiceImpl.getTransaction("123", "54", "341", "143", "143");

	}

	@Test
	public void testGetTransactionElse() throws ChatakPayException {
		restPaymentServiceImpl.getTransaction("123", "54", "341", "143", "143");

	}

	@Test
	public void testGetTransactionByRefId() throws ChatakPayException {
		PGTransaction pgTransaction = new PGTransaction();
		pgTransaction.setPan("654");
		pgTransaction.setTransactionId("4243");
		pgTransaction.setTxnAmount(Long.parseLong("4234"));
		pgTransaction.setTxnTotalAmount(Long.parseLong("4234"));
		Mockito.when(transactionDao.getTransaction(Matchers.anyString(), Matchers.anyString(), Matchers.anyString()))
				.thenReturn(pgTransaction);
		Mockito.when(refundTransactionDao.getRefundedAmountOnTxnId(Matchers.anyString())).thenReturn(Long.parseLong("212"));
		restPaymentServiceImpl.getTransactionByRefId("123", "54", "341", "sale");
	}

	@Test
	public void testGetTransactionByRefIdElse() throws ChatakPayException {
		PGTransaction pgTransaction = new PGTransaction();
		pgTransaction.setPan("654");
		pgTransaction.setTransactionId("4243");
		pgTransaction.setTxnAmount(Long.parseLong("0"));
		pgTransaction.setFeeAmount(Long.parseLong("432"));
		Mockito.when(transactionDao.getTransactionOnTxnIdAndTxnType(Matchers.anyString(), Matchers.anyString(),
				Matchers.anyString(), Matchers.anyString())).thenReturn(pgTransaction);
		Mockito.when(refundTransactionDao.getRefundedAmountOnTxnId(Matchers.anyString())).thenReturn(null);
		restPaymentServiceImpl.getTransactionByRefId("123", "54", "341", "abcd");
	}

	@Test
	public void testGetTransactionByRefIdNull() throws ChatakPayException {
		restPaymentServiceImpl.getTransactionByRefId("123", "54", "341", "abcd");
	}

	@Test
	public void testGetTransactionByRefIdException() throws ChatakPayException {
		Mockito.when(transactionDao.getTransactionOnTxnIdAndTxnType(Matchers.anyString(), Matchers.anyString(),
				Matchers.anyString(), Matchers.anyString())).thenThrow(new NullPointerException());
		restPaymentServiceImpl.getTransactionByRefId("123", "54", "341", "abcd");
	}

	@Test
	public void testGetMerchantIdAndTerminalId() throws ChatakPayException {
		PGTerminal pgTerminal = new PGTerminal();
		PGMerchant pgMerchant = new PGMerchant();
		pgMerchant.setId(Long.parseLong("5435"));
		pgTerminal.setTerminalId(Long.parseLong("34"));
		Mockito.when(terminalDao.getTerminalonMerchantCode(Matchers.anyLong())).thenReturn(pgTerminal);
		Mockito.when(merchantUpdateDao.getMerchant(Matchers.anyString())).thenReturn(pgMerchant);
		restPaymentServiceImpl.getMerchantIdAndTerminalId("123");

	}

	@Test
	public void testGetMerchantIdAndTerminalIdElse() throws ChatakPayException {
		PGMerchant pgMerchant = new PGMerchant();
		Mockito.when(merchantUpdateDao.getMerchant(Matchers.anyString())).thenReturn(pgMerchant);
		restPaymentServiceImpl.getMerchantIdAndTerminalId("123");

	}

	@Test
	public void testGetMerchantIdAndTerminalIdNull() throws ChatakPayException {
		restPaymentServiceImpl.getMerchantIdAndTerminalId("123");

	}

	@Test(expected = NullPointerException.class)
	public void testProcessPopupVoidOrRefund() throws ChatakAdminException {
		TransactionRequest transactionRequest = new TransactionRequest();
		PGTransaction pgTransaction = new PGTransaction();
		Mockito.when(refundTransactionDao.getTransactionForVoidOrRefundByAccountTransactionId(Matchers.anyString(),
				Matchers.anyString())).thenReturn(pgTransaction);
		restPaymentServiceImpl.processPopupVoidOrRefund(transactionRequest);
	}

	@Test(expected = NullPointerException.class)
	public void testProcessPopupVoidOrRefundNull() throws ChatakAdminException {
		TransactionRequest transactionRequest = new TransactionRequest();
		restPaymentServiceImpl.processPopupVoidOrRefund(transactionRequest);

	}

	@Test
	public void testGetTransactionByRefIdForRefund() throws ChatakPayException {
		List<PGTransaction> pgList = new ArrayList<>();
		PGTransaction pgTransaction = new PGTransaction();
		pgList.add(pgTransaction);
		Mockito.when(refundTransactionDao
				.findByMerchantIdAndTerminalIdAndTransactionIdAndStatusAndMerchantSettlementStatusInAndRefundStatusNotLike(
						Matchers.anyString(), Matchers.anyString(), Matchers.anyString(), Matchers.anyInt(),
						Matchers.anyInt(), Matchers.anyList()))
				.thenReturn(pgList);
		Mockito.when(transactionDao.getTransactionOnTxnIdAndTxnType(Matchers.anyString(), Matchers.anyString(),
				Matchers.anyString(), Matchers.anyString())).thenReturn(pgTransaction);
		Mockito.when(refundTransactionDao.getRefundedAmountOnTxnId(Matchers.anyString())).thenReturn(Long.parseLong("312"));
		Mockito.when(transactionDao.getTransaction(Matchers.anyString(), Matchers.anyString(), Matchers.anyString()))
				.thenReturn(pgTransaction);
		restPaymentServiceImpl.getTransactionByRefIdForRefund("2", "4", "2", "4");

	}

	@Test
	public void testGetTransactionByRefIdForRefundElse() throws ChatakPayException {
		List<PGTransaction> pgList = new ArrayList<>();
		PGTransaction pgTransaction = new PGTransaction();
		pgList.add(pgTransaction);
		Mockito.when(refundTransactionDao
				.findByMerchantIdAndTerminalIdAndTransactionIdAndStatusAndMerchantSettlementStatusInAndRefundStatusNotLike(
						Matchers.anyString(), Matchers.anyString(), Matchers.anyString(), Matchers.anyInt(),
						Matchers.anyInt(), Matchers.anyList()))
				.thenReturn(pgList);
		restPaymentServiceImpl.getTransactionByRefIdForRefund("123", "234", "4234", "4234");

	}

	@Test
	public void testGetTransactionByRefIdForRefundNull() throws ChatakPayException {
		restPaymentServiceImpl.getTransactionByRefIdForRefund("123", "234", "4234", "4234");

	}
}
