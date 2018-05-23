package com.chatak.merchant.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import com.chatak.merchant.exception.ChatakMerchantException;
import com.chatak.pg.acq.dao.AccountDao;
import com.chatak.pg.acq.dao.AccountTransactionsDao;
import com.chatak.pg.acq.dao.MerchantBankDao;
import com.chatak.pg.acq.dao.MerchantDao;
import com.chatak.pg.acq.dao.MerchantUpdateDao;
import com.chatak.pg.acq.dao.PGTransfersDao;
import com.chatak.pg.acq.dao.RefundTransactionDao;
import com.chatak.pg.acq.dao.TransactionDao;
import com.chatak.pg.acq.dao.model.PGAccount;
import com.chatak.pg.acq.dao.model.PGAccountTransactions;
import com.chatak.pg.acq.dao.model.PGMerchant;
import com.chatak.pg.acq.dao.model.PGMerchantBank;
import com.chatak.pg.acq.dao.model.PGTransfers;
import com.chatak.pg.bean.AccountTransferRequest;
import com.chatak.pg.bean.DebitAccount;
import com.chatak.pg.model.EFTRefTxnData;
import com.chatak.pg.model.FundTransferDTO;
import com.chatak.pg.model.GetTransactionIdsListResponse;
import com.chatak.pg.model.ReportsDTO;
import com.chatak.pg.user.bean.GetTransferListRequest;

@RunWith(MockitoJUnitRunner.class)
public class FundTransferServiceImplTest {

	@InjectMocks
	private FundTransferServiceImpl fundTransferServiceImpl = new FundTransferServiceImpl();

	@Mock
	PGTransfersDao pgTransfersDao;

	@Mock
	AccountDao accountDao;

	@Mock
	MerchantBankDao merchantBankDao;

	@Mock
	MerchantDao merchantDao;

	@Mock
	TransactionDao transactionDao;

	@Mock
	AccountTransactionsDao accountTransactionsDao;

	@Mock
	RefundTransactionDao refundTransactionDao;

	@Mock
	MerchantUpdateDao merchantUpdateDao;

	@Mock
	FundTransferDTO fundTransferDTO;

	@Mock
	PGMerchantBank pgMerchantBank;

	@Mock
	DebitAccount debitAccount;

	@Mock
	PGAccountTransactions eftAccountTransaction;

	@Mock
	PGTransfers pgTransfers;

	@Mock
	PGAccount pgAccount;

	@Mock
	PGMerchant pgMerchant;

	@Test
	public void testPopulateFundTransferDTO() throws ChatakMerchantException {
		pgMerchantBank = new PGMerchantBank();
		pgMerchantBank.setAccountType("C");
		pgAccount.setAvailableBalance(Long.parseLong("123"));
		Mockito.when(merchantBankDao.getMerchantBankByMerchantId(Matchers.anyString())).thenReturn(pgMerchantBank);
		Mockito.when(accountDao.getPgAccount(Matchers.anyString())).thenReturn(pgAccount);
		Mockito.when(merchantUpdateDao.getMerchant(Matchers.anyString())).thenReturn(pgMerchant);
		Assert.assertNotNull(fundTransferServiceImpl.populateFundTransferDTO("123", "FT_BANK"));

	}

	@Test
	public void testPopulateFundTransferDTOElse() throws ChatakMerchantException {
		pgMerchantBank = new PGMerchantBank();
		pgMerchantBank.setAccountType("A");
		pgAccount.setAvailableBalance(Long.parseLong("123"));
		Mockito.when(merchantBankDao.getMerchantBankByMerchantId(Matchers.anyString())).thenReturn(pgMerchantBank);
		Mockito.when(accountDao.getPgAccount(Matchers.anyString())).thenReturn(pgAccount);
		Mockito.when(merchantUpdateDao.getMerchant(Matchers.anyString())).thenReturn(pgMerchant);
		Assert.assertNotNull(fundTransferServiceImpl.populateFundTransferDTO("123", "FT_BANK"));

	}

	@Test
	public void testPopulateFundTransferDTOForElseIf() throws ChatakMerchantException {
		pgMerchantBank = new PGMerchantBank();
		pgMerchantBank.setAccountType("X");
		pgAccount.setAvailableBalance(Long.parseLong("123"));
		Mockito.when(merchantBankDao.getMerchantBankByMerchantId(Matchers.anyString())).thenReturn(pgMerchantBank);
		Mockito.when(accountDao.getPgAccount(Matchers.anyString())).thenReturn(pgAccount);
		Mockito.when(merchantUpdateDao.getMerchant(Matchers.anyString())).thenReturn(pgMerchant);
		Assert.assertNotNull(fundTransferServiceImpl.populateFundTransferDTO("123", "FT_CHECK"));

	}

	@Test(expected = ChatakMerchantException.class)
	public void testProcessEFTFundsTransfer() throws ChatakMerchantException {
		fundTransferDTO = new FundTransferDTO();
		fundTransferDTO.setAmountToTransfer("0");
		fundTransferServiceImpl.processEFTFundsTransfer(fundTransferDTO);

	}

	@Test
	public void testProcessEFTFundsTransferForIf() throws ChatakMerchantException {
		pgTransfers = new PGTransfers();
		pgTransfers.setPgTransfersId(1l);
		pgTransfers.setMerchantId(Long.parseLong("123"));
		fundTransferDTO = new FundTransferDTO();
		fundTransferDTO.setAmountToTransfer("324");
		fundTransferDTO.setMerchantCode("2354");
		fundTransferDTO.setDebitAccount(debitAccount);
		fundTransferDTO.setFundTransferMode("FT_BANK");
		debitAccount = new DebitAccount();
		debitAccount.setAvaliableBalance("12345.00");
		debitAccount.setAccountNumber(Long.parseLong("12343233465"));
		fundTransferDTO.setDebitAccount(debitAccount);
		Mockito.when(pgTransfersDao.createOrUpdateTransferRecord(Matchers.any(PGTransfers.class)))
				.thenReturn(pgTransfers);
		Mockito.when(accountTransactionsDao.generateAccountTransactionId()).thenReturn("234");
		fundTransferServiceImpl.processEFTFundsTransfer(fundTransferDTO);

	}

	@Test(expected = ChatakMerchantException.class)
	public void testProcessCheckFundsTransfer() throws ChatakMerchantException {
		fundTransferDTO = new FundTransferDTO();
		fundTransferDTO.setAmountToTransfer("0");
		fundTransferServiceImpl.processCheckFundsTransfer(fundTransferDTO);

	}

	@Test
	public void testProcessCheckFundsTransferForIf() throws ChatakMerchantException {
		pgTransfers = new PGTransfers();
		pgTransfers.setPgTransfersId(1l);
		pgTransfers.setMerchantId(Long.parseLong("123"));
		fundTransferDTO = new FundTransferDTO();
		fundTransferDTO.setAmountToTransfer("324");
		fundTransferDTO.setMerchantCode("2354");
		fundTransferDTO.setDebitAccount(debitAccount);
		fundTransferDTO.setFundTransferMode("FT_BANK");
		debitAccount = new DebitAccount();
		debitAccount.setAvaliableBalance("12345.00");
		debitAccount.setAccountNumber(Long.parseLong("12343233465"));
		fundTransferDTO.setDebitAccount(debitAccount);
		Mockito.when(pgTransfersDao.createOrUpdateTransferRecord(Matchers.any(PGTransfers.class)))
				.thenReturn(pgTransfers);
		Mockito.when(accountTransactionsDao.generateAccountTransactionId()).thenReturn("234");
		fundTransferServiceImpl.processCheckFundsTransfer(fundTransferDTO);

	}

	@Test
	public void testLogTransfersForIf() {
		fundTransferDTO = new FundTransferDTO();
		debitAccount = new DebitAccount();
		fundTransferDTO.setMerchantCode("12345");
		debitAccount.setAccountType("savings");
		debitAccount.setAccountNumber(Long.parseLong("12345"));
		fundTransferDTO.setDebitAccount(debitAccount);
		fundTransferDTO.setFundTransferMode("FT_BANK");
		fundTransferDTO.setAmountToTransfer("1.0");
		Mockito.when(pgTransfersDao.createOrUpdateTransferRecord(Matchers.any(PGTransfers.class)))
				.thenReturn(pgTransfers);
		Mockito.when(accountTransactionsDao.generateAccountTransactionId()).thenReturn("234");
		fundTransferServiceImpl.logTransfers(fundTransferDTO);

	}

	@Test
	public void testLogTransfersForElseIf() {
		fundTransferDTO = new FundTransferDTO();
		debitAccount = new DebitAccount();
		fundTransferDTO.setMerchantCode("12345");
		debitAccount.setAccountType("savings");
		debitAccount.setAccountNumber(Long.parseLong("12345"));
		fundTransferDTO.setDebitAccount(debitAccount);
		fundTransferDTO.setFundTransferMode("FT_CHECK");
		fundTransferDTO.setAmountToTransfer("1.0");
		Mockito.when(pgTransfersDao.createOrUpdateTransferRecord(Matchers.any(PGTransfers.class)))
				.thenReturn(pgTransfers);
		Mockito.when(accountTransactionsDao.generateAccountTransactionId()).thenReturn("234");
		fundTransferServiceImpl.logTransfers(fundTransferDTO);

	}

	@Test
	public void testSplitReportsAmount() {
		List<ReportsDTO> list = new ArrayList<ReportsDTO>();
		ReportsDTO reportsDTO = new ReportsDTO();
		reportsDTO.setMerchantCode("123");
		reportsDTO.setAmount("200000");
		list.add(reportsDTO);
		Assert.assertNotNull(fundTransferServiceImpl.splitReportsAmount(list));

	}

	@Test
	public void testGetReportsEFTAmount() throws ChatakMerchantException {
		Map<String, Long> splitList = new HashMap<>();
		splitList.put("1", Long.parseLong("2"));
		fundTransferServiceImpl.getReportsEFTAmount(splitList);
		Assert.assertNotNull(fundTransferServiceImpl.getReportsEFTAmount(splitList));

	}

	@Test
	public void testGetAllEftTransfersListOnMerchantCode() throws ChatakMerchantException {
		GetTransferListRequest request = new GetTransferListRequest();
		Assert.assertNotNull(fundTransferServiceImpl.getAllEftTransfersListOnMerchantCode(request));

	}

	@Test
	public void testGetTransactionIdListOnTransferId() throws ChatakMerchantException {
		GetTransactionIdsListResponse response = new GetTransactionIdsListResponse();
		EFTRefTxnData eFTRefTxnData = new EFTRefTxnData();
		eFTRefTxnData.setTransactionId("15");
		List<EFTRefTxnData> transactionIdsList = new ArrayList<>();
		transactionIdsList.add(eFTRefTxnData);
		response.setErrorCode("00");
		response.setTransactionIdsList(transactionIdsList);
		Mockito.when(refundTransactionDao.getEFTRefTxnDataList(Matchers.anyString())).thenReturn(transactionIdsList);
		Assert.assertNotNull(fundTransferServiceImpl.getTransactionIdListOnTransferId("123"));

	}

	@Test
	public void testFetchAccountDetails() throws ChatakMerchantException {
		PGAccount account = new PGAccount();
		account.setStatus("active");
		Mockito.when(accountDao.getAccountonAccountNumber(Matchers.anyLong())).thenReturn(account);
		account.setEntityId("000");
		Assert.assertNotNull(fundTransferServiceImpl.fetchAccountDetails("123", "000"));

	}

	@Test
	public void testFetchAccountDetailsNotEqualEntityId() throws ChatakMerchantException {
		PGAccount account = new PGAccount();
		account.setStatus("active");
		Mockito.when(accountDao.getAccountonAccountNumber(Matchers.anyLong())).thenReturn(account);
		account.setEntityId("123");
		Assert.assertNotNull(fundTransferServiceImpl.fetchAccountDetails("123", "000"));

	}

	@Test(expected = ChatakMerchantException.class)
	public void testFetchAccountDetailsException() throws ChatakMerchantException {
		PGAccount account = new PGAccount();
		account.setStatus("active");
		Mockito.when(accountDao.getAccountonAccountNumber(Matchers.anyLong())).thenThrow(new NullPointerException());
		account.setEntityId("000");
		Assert.assertNotNull(fundTransferServiceImpl.fetchAccountDetails("123", "000"));

	}

	@Test(expected = ChatakMerchantException.class)
	public void testProcessAccountTransfer() throws ChatakMerchantException {
		AccountTransferRequest accountTransferRequest = new AccountTransferRequest();
		PGAccount sourceAccount = new PGAccount();
		PGAccount destinationAccount = new PGAccount();
		accountTransferRequest.setDestinationAccountNumber("1456");
		accountTransferRequest.setSourceAccountNumber("1236547989");
		sourceAccount.setCurrentBalance(Long.parseLong("103"));
		sourceAccount.setEntityId("123");
		sourceAccount.setAvailableBalance(Long.parseLong("12"));
		destinationAccount.setEntityId("12");
		String parentMerchantCode = "12";
		Mockito.when(accountDao.getAccountonAccountNumber(Matchers.anyLong())).thenReturn(sourceAccount);
		Mockito.when(accountDao
				.getAccountonAccountNumber(Long.valueOf(accountTransferRequest.getDestinationAccountNumber())))
				.thenReturn(destinationAccount);
		Mockito.when(merchantDao.getParentMerchantCode(Matchers.anyString())).thenReturn(parentMerchantCode);
		Assert.assertNotNull(fundTransferServiceImpl.processAccountTransfer(accountTransferRequest));

	}

	@Test(expected = ChatakMerchantException.class)
	public void testProcessAccountTransferIf() throws ChatakMerchantException {
		AccountTransferRequest accountTransferRequest = new AccountTransferRequest();
		PGAccount sourceAccount = new PGAccount();
		PGAccount destinationAccount = new PGAccount();
		accountTransferRequest.setDestinationAccountNumber("1456");
		accountTransferRequest.setSourceAccountNumber("1236547989");
		accountTransferRequest.setTransferAmount(Double.parseDouble("1230"));
		sourceAccount.setCurrentBalance(0l);
		sourceAccount.setEntityId("12");
		sourceAccount.setAvailableBalance(Long.parseLong("12"));
		destinationAccount.setEntityId("12");
		String parentMerchantCode = "12";
		sourceAccount.setAccountNum(Long.parseLong("12"));
		destinationAccount.setEntityId("12");
		destinationAccount.setAvailableBalance(Long.parseLong("12"));
		destinationAccount.setCurrentBalance(Long.parseLong("1432"));
		destinationAccount.setAccountNum(Long.parseLong("14524"));
		accountTransferRequest.setDescription("active");
		sourceAccount.setCurrentBalance(Long.parseLong("14"));
		Mockito.when(accountDao.getAccountonAccountNumber(Matchers.anyLong())).thenReturn(sourceAccount);
		Mockito.when(accountDao
				.getAccountonAccountNumber(Long.valueOf(accountTransferRequest.getDestinationAccountNumber())))
				.thenReturn(destinationAccount);
		Mockito.when(merchantDao.getParentMerchantCode(Matchers.anyString())).thenReturn(parentMerchantCode);
		Assert.assertNotNull(fundTransferServiceImpl.processAccountTransfer(accountTransferRequest));

	}

	@Test
	public void testProcessAccountTransferElse() throws ChatakMerchantException {
		AccountTransferRequest accountTransferRequest = new AccountTransferRequest();
		PGAccount sourceAccount = new PGAccount();
		PGAccount destinationAccount = new PGAccount();
		accountTransferRequest.setDestinationAccountNumber("1456");
		accountTransferRequest.setSourceAccountNumber("1236547989");
		accountTransferRequest.setTransferAmount(Double.parseDouble("1230"));
		sourceAccount.setCurrentBalance(Long.parseLong("103"));
		sourceAccount.setEntityId("12");
		sourceAccount.setAccountNum(Long.parseLong("12"));
		sourceAccount.setAvailableBalance(Long.parseLong("1111111"));
		destinationAccount.setEntityId("12");
		destinationAccount.setAvailableBalance(Long.parseLong("12"));
		destinationAccount.setCurrentBalance(Long.parseLong("1432"));
		destinationAccount.setAccountNum(Long.parseLong("14524"));
		accountTransferRequest.setDescription("active");
		sourceAccount.setCurrentBalance(Long.parseLong("14"));
		String parentMerchantCode = "12";
		Mockito.when(accountDao.getAccountonAccountNumber(Matchers.anyLong())).thenReturn(sourceAccount);
		Mockito.when(accountDao
				.getAccountonAccountNumber(Long.valueOf(accountTransferRequest.getDestinationAccountNumber())))
				.thenReturn(destinationAccount);
		Mockito.when(merchantDao.getParentMerchantCode(Matchers.anyString())).thenReturn(parentMerchantCode);
		Assert.assertNotNull(fundTransferServiceImpl.processAccountTransfer(accountTransferRequest));

	}

	@Test
	public void testlogTransferAccountTransaction() throws ChatakMerchantException {
		String merchantCode = "12547896";
		pgAccount.setAccountDesc("54562");
		pgAccount.setAccountNum(Long.parseLong("124564"));
		pgAccount.setAvailableBalance(Long.parseLong("12525"));
		pgAccount.setCurrentBalance(Long.parseLong("12525"));
		List<PGAccount> accountLists = new ArrayList<PGAccount>();
		accountLists.add(pgAccount);
		Mockito.when(accountDao.getActivePGAccounts(merchantCode)).thenReturn(accountLists);
		Assert.assertNotNull(fundTransferServiceImpl.getAccountList("12547896"));

	}
}
