package com.chatak.acquirer.admin.service;

import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.map.ObjectWriter;
import org.junit.Before;
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
import com.chatak.pg.model.VirtualTerminalAdjustmentRequest;
import com.chatak.pg.util.Properties;
import com.fasterxml.jackson.databind.ObjectMapper;

@SuppressWarnings("unchecked")
@RunWith(MockitoJUnitRunner.class)
public class RestPaymentServiceImplTest {

	@InjectMocks
	RestPaymentServiceImpl restPaymentServiceImpl = new RestPaymentServiceImpl();
	
	@Mock
	ObjectWriter objectWriter;

	@Mock
	MerchantDao merchantDao;
	
	@Mock
	TransactionRequest transactionRequest;

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
	
	@Mock
	VirtualTerminalAdjustmentRequest virtualTerminalAdjustmentRequest;
	
	@Mock
	ObjectMapper mapper;
	
	private static final String uri ="/transaction/process/";
	
	private static final String ADJUSTMENT_URI ="/transactionService/transaction/adjustment/";
	
	@Before
	public void init() {
	  java.util.Properties propsExportedLocal = new java.util.Properties();
      propsExportedLocal.setProperty("thread.pool.size", "500");
      propsExportedLocal.setProperty(uri, "https://dev.ipsidy.net/paygate/pg");
      propsExportedLocal.setProperty(ADJUSTMENT_URI,"https://dev.ipsidy.net/paygate/pg");
      propsExportedLocal.setProperty("chatak-tms.rest.service.url", "https://dev.ipsidy.net/tms/admin/management/");
      propsExportedLocal.setProperty("thread.max.per.route", "500");
      propsExportedLocal.setProperty("CONNECT", "CONNECT");
      propsExportedLocal.setProperty("chatak-issuance.service.url", "https://dev.ipsidy.net/issuance-admin");
      propsExportedLocal.setProperty("chatak-merchant.service.url", "https://dev.ipsidy.net/paygate/pg");
      propsExportedLocal.setProperty("prepaid-admin.consumer.client.secret", "JfTZY1DhHSN96");
      propsExportedLocal.setProperty("chatak-merchant.consumer.client.id", "resgpcqlmg8lydip");
      propsExportedLocal.setProperty("chatak-merchant.oauth.service.url",
    		  "/oauth/token?grant_type=password&username=resgpcqlmg8lydip&password=9570AFBEMA36EM4130M9B72M44C9D3C9703C");
      propsExportedLocal.setProperty("chatak-merchant.oauth.basic.auth.username", "izf8p5t73ffcshzq1lpa2adho0tgm6zt");
      propsExportedLocal.setProperty("chatak-merchant.oauth.basic.auth.password", "C7511182M9FEFM4D5DM84A5M68B1188F6220");
	  Properties.mergeProperties(propsExportedLocal);
	}

	@Test
	public void testGetTransaction() throws ChatakPayException {
		PGTransaction pgTransaction = new PGTransaction();
		pgTransaction.setPan("z+DrxgikaCRTpgnHR8xXwA+XKJra06us6DmM1Zm63BU=");
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
		pgTransaction.setPan("z+DrxgikaCRTpgnHR8xXwA+XKJra06us6DmM1Zm63BU=");
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
		pgTransaction.setPan("z+DrxgikaCRTpgnHR8xXwA+XKJra06us6DmM1Zm63BU=");
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

	@Test
	public void testProcessPopupVoidOrRefundNull() throws ChatakAdminException {
		TransactionRequest transactionRequest = new TransactionRequest();
		transactionRequest.setAccountTransactionId("1000000000001");
		transactionRequest.setMerchantId(1l);
		restPaymentServiceImpl.processPopupVoidOrRefund(transactionRequest);

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
