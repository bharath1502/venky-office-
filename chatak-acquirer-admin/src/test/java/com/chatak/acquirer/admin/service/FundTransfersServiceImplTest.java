package com.chatak.acquirer.admin.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import com.chatak.acquirer.admin.exception.ChatakAdminException;
import com.chatak.acquirer.admin.model.FundTransferActionListModel;
import com.chatak.acquirer.admin.model.FundTransferActionModel;
import com.chatak.acquirer.admin.service.impl.FundTransfersServiceImpl;
import com.chatak.pg.acq.dao.AccountDao;
import com.chatak.pg.acq.dao.AccountTransactionsDao;
import com.chatak.pg.acq.dao.ExecutedTransactionDao;
import com.chatak.pg.acq.dao.MerchantBankDao;
import com.chatak.pg.acq.dao.MerchantDao;
import com.chatak.pg.acq.dao.MerchantUpdateDao;
import com.chatak.pg.acq.dao.PGTransfersDao;
import com.chatak.pg.acq.dao.RefundTransactionDao;
import com.chatak.pg.acq.dao.TransactionDao;
import com.chatak.pg.acq.dao.VoidTransactionDao;
import com.chatak.pg.acq.dao.model.PGAccount;
import com.chatak.pg.acq.dao.model.PGAccountTransactions;
import com.chatak.pg.acq.dao.model.PGMerchant;
import com.chatak.pg.acq.dao.model.PGMerchantBank;
import com.chatak.pg.acq.dao.model.PGTransfers;
import com.chatak.pg.bean.AccountTransferRequest;
import com.chatak.pg.bean.DebitAccount;
import com.chatak.pg.bean.LitleEFTRequest;
import com.chatak.pg.model.EFTRefTxnData;
import com.chatak.pg.model.FundTransferDTO;
import com.chatak.pg.model.LitleEFTDTO;
import com.chatak.pg.model.ReportsDTO;
import com.chatak.pg.user.bean.GetTransferListRequest;

@RunWith(MockitoJUnitRunner.class)
public class FundTransfersServiceImplTest {

	@InjectMocks
	FundTransfersServiceImpl fundTransfersServiceImpl = new FundTransfersServiceImpl();

	@Mock
	AccountDao accountDao;

	@Mock
	PGTransfersDao pgTransfersDao;

	@Mock
	TransactionDao transactionDao;

	@Mock
	MerchantBankDao merchantBankDao;

	@Mock
	MerchantDao merchantDao;

	@Mock
	AccountTransactionsDao accountTransactionsDao;

	@Mock
	VoidTransactionDao voidTransactionDao;

	@Mock
	RefundTransactionDao refundTransactionDao;

	@Mock
	MerchantUpdateDao merchantUpdateDao;

	@Mock
	ExecutedTransactionDao executedTransactionDao;

	@Test
	public void testGetEFTTransferRequestsCount() {
		fundTransfersServiceImpl.getEFTTransferRequestsCount();
	}

	@Test
	public void testGetCheckTransferRequestsCount() {
		fundTransfersServiceImpl.getCheckTransferRequestsCount();
	}

	@Test
	public void testGetPGTransfersList() {
		GetTransferListRequest transferListRequest = new GetTransferListRequest();
		fundTransfersServiceImpl.getPGTransfersList(transferListRequest);
	}

	@Test
	public void testProcessTransferAction() throws ChatakAdminException {
		FundTransferActionModel fundTransferActionModel = new FundTransferActionModel();
		PGTransfers pgTransfers = new PGTransfers();
		PGAccount pgAccount = new PGAccount();
		PGAccountTransactions pgAccountTransactions = new PGAccountTransactions();
		pgTransfers.setMerchantId(Long.parseLong("645"));
		pgAccount.setAvailableBalance(Long.parseLong("2645"));
		pgTransfers.setAmount(Long.parseLong("11345"));
		fundTransferActionModel.setAction("Cancelled");
		pgTransfers.setPgTransfersId(Long.parseLong("145"));
		fundTransferActionModel.setPgTransfersId(Long.parseLong("9145"));
		Mockito.when(pgTransfersDao.getPGTransferRecordById(Matchers.anyLong())).thenReturn(pgTransfers);
		Mockito.when(accountDao.getPgAccount(Matchers.anyString())).thenReturn(pgAccount);
		Mockito.when(accountTransactionsDao.getAccountTransactionByTransferId(Matchers.anyString()))
				.thenReturn(pgAccountTransactions);
		fundTransfersServiceImpl.processTransferAction(fundTransferActionModel);
	}

	@Test
	public void testProcessTransferActionElse() throws ChatakAdminException {
		FundTransferActionModel fundTransferActionModel = new FundTransferActionModel();
		PGTransfers pgTransfers = new PGTransfers();
		PGAccount pgAccount = new PGAccount();
		pgTransfers.setMerchantId(Long.parseLong("645"));
		pgAccount.setAvailableBalance(Long.parseLong("2645"));
		pgTransfers.setAmount(Long.parseLong("1345"));
		fundTransferActionModel.setAction("Executed");
		pgTransfers.setPgTransfersId(Long.parseLong("145"));
		fundTransferActionModel.setPgTransfersId(Long.parseLong("9145"));
		pgAccount.setCurrentBalance(Long.parseLong("145"));
		Mockito.when(pgTransfersDao.getPGTransferRecordById(Matchers.anyLong())).thenReturn(pgTransfers);
		Mockito.when(accountDao.getPgAccount(Matchers.anyString())).thenReturn(pgAccount);
		Mockito.when(pgTransfersDao.createOrUpdateTransferRecord(Matchers.any(PGTransfers.class)))
				.thenReturn(pgTransfers);
		fundTransfersServiceImpl.processTransferAction(fundTransferActionModel);
	}

	@Test
	public void testProcessTransferActionNull() throws ChatakAdminException {
		FundTransferActionModel fundTransferActionModel = new FundTransferActionModel();
		fundTransfersServiceImpl.processTransferAction(fundTransferActionModel);
	}

	@Test(expected = NullPointerException.class)
	public void testUpdateBulkFundTransferStatus() throws ChatakAdminException {
		FundTransferActionListModel fundTransferActionDTOList = new FundTransferActionListModel();
		List<FundTransferActionModel> actionModels = new ArrayList<>();
		PGTransfers pgTransfers = new PGTransfers();
		PGAccountTransactions pgAccountTransactions = new PGAccountTransactions();
		FundTransferActionModel actionModel = new FundTransferActionModel();
		PGAccount pgAccount = new PGAccount();
		actionModel.setPgTransfersId(Long.parseLong("145"));
		pgTransfers.setMerchantId(Long.parseLong("534"));
		pgAccount.setAvailableBalance(Long.parseLong("2645"));
		pgTransfers.setAmount(Long.parseLong("1345"));
		pgTransfers.setPgTransfersId(Long.parseLong("9145"));
		pgAccount.setCurrentBalance(Long.parseLong("145"));
		actionModels.add(actionModel);
		fundTransferActionDTOList.setPgTransfersIds(actionModels);
		Mockito.when(pgTransfersDao.getPGTransferRecordById(Matchers.anyLong())).thenReturn(pgTransfers);
		Mockito.when(accountDao.getPgAccount(Matchers.anyString())).thenReturn(pgAccount);
		Mockito.when(accountTransactionsDao.getAccountTransactionByTransferId(Matchers.anyString()))
				.thenReturn(pgAccountTransactions);
		fundTransfersServiceImpl.updateBulkFundTransferStatus(fundTransferActionDTOList, "Executed", "comments");
	}

	@Test
	public void testUpdateBulkFundTransferStatusPending() throws ChatakAdminException {
		FundTransferActionListModel fundTransferActionDTOList = new FundTransferActionListModel();
		List<FundTransferActionModel> actionModels = new ArrayList<>();
		PGTransfers pgTransfers = new PGTransfers();
		PGAccountTransactions pgAccountTransactions = new PGAccountTransactions();
		FundTransferActionModel actionModel = new FundTransferActionModel();
		PGAccount pgAccount = new PGAccount();
		actionModel.setPgTransfersId(Long.parseLong("145"));
		pgTransfers.setMerchantId(Long.parseLong("534"));
		pgAccount.setAvailableBalance(Long.parseLong("2645"));
		pgTransfers.setAmount(Long.parseLong("1345"));
		pgTransfers.setPgTransfersId(Long.parseLong("9145"));
		pgAccount.setCurrentBalance(Long.parseLong("145"));
		actionModel.setAction("Pending");
		actionModels.add(actionModel);
		fundTransferActionDTOList.setPgTransfersIds(actionModels);
		Mockito.when(pgTransfersDao.getPGTransferRecordById(Matchers.anyLong())).thenReturn(pgTransfers);
		Mockito.when(accountDao.getPgAccount(Matchers.anyString())).thenReturn(pgAccount);
		Mockito.when(accountTransactionsDao.getAccountTransactionByTransferId(Matchers.anyString()))
				.thenReturn(pgAccountTransactions);
		fundTransfersServiceImpl.updateBulkFundTransferStatus(fundTransferActionDTOList, "Pending", "comments");
	}

	@Test
	public void testGetPGTransfersListOnTransferMode() {
		GetTransferListRequest request = new GetTransferListRequest();
		fundTransfersServiceImpl.getPGTransfersListOnTransferMode(request);
	}

	@Test
	public void testGetAllEftTransfersListOnMerchantCode() throws ChatakAdminException {
		GetTransferListRequest request = new GetTransferListRequest();
		fundTransfersServiceImpl.getAllEftTransfersListOnMerchantCode(request);
	}

	@Test
	public void testGetLitleExecutedTransactions() throws ChatakAdminException {
		LitleEFTRequest litleEFTRequest = new LitleEFTRequest();
		fundTransfersServiceImpl.getLitleExecutedTransactions(litleEFTRequest);
	}

	@Test
	public void testGetLitleExecutedTransactionsCount() {
		fundTransfersServiceImpl.getLitleExecutedTransactionsCount();
	}

	@Test(expected = NullPointerException.class)
	public void testProcessLitleEFT() throws ChatakAdminException {
		List<LitleEFTDTO> list = new ArrayList<>();
		LitleEFTDTO eftdto = new LitleEFTDTO();
		FundTransferDTO fundTransferDTO = new FundTransferDTO();
		DebitAccount debitAccount = new DebitAccount();
		fundTransferDTO.setAmountToTransfer("4.56");
		debitAccount.setAvaliableBalance("423");
		fundTransferDTO.setDebitAccount(debitAccount);
		eftdto.setAmount(Long.parseLong("456"));
		list.add(eftdto);
		fundTransfersServiceImpl.processLitleEFT(list);
	}

	@Test(expected = NullPointerException.class)
	public void testProcessIndividualLitleEFT() throws ChatakAdminException {
		LitleEFTDTO litleEFTDTO = new LitleEFTDTO();
		PGMerchantBank pgMerchantBank = new PGMerchantBank();
		PGAccount pgAccount = new PGAccount();
		PGMerchant pgMerchant = new PGMerchant();
		litleEFTDTO.setMerchantCode("546");
		litleEFTDTO.setAmount(Long.parseLong("456"));
		pgAccount.setAvailableBalance(Long.parseLong("5345"));
		pgMerchant.setState("FT_CHECK");

		Mockito.when(merchantBankDao.getMerchantBankByMerchantId(Matchers.anyString())).thenReturn(pgMerchantBank);
		Mockito.when(accountDao.getPgAccount(Matchers.anyString())).thenReturn(pgAccount);
		Mockito.when(merchantUpdateDao.getMerchant(Matchers.anyString())).thenReturn(pgMerchant);
		fundTransfersServiceImpl.processIndividualLitleEFT(litleEFTDTO);
	}

	@Test
	public void testPopulateFundTransferDTO() throws ChatakAdminException {
		PGMerchantBank pgMerchantBank = new PGMerchantBank();
		PGAccount pgAccount = new PGAccount();
		PGMerchant pgMerchant = new PGMerchant();
		pgAccount.setAvailableBalance(Long.parseLong("5345"));
		Mockito.when(merchantBankDao.getMerchantBankByMerchantId(Matchers.anyString())).thenReturn(pgMerchantBank);
		Mockito.when(accountDao.getPgAccount(Matchers.anyString())).thenReturn(pgAccount);
		Mockito.when(merchantUpdateDao.getMerchant(Matchers.anyString())).thenReturn(pgMerchant);
		fundTransfersServiceImpl.populateFundTransferDTO("5464", "FT_BANK");
	}

	@Test
	public void testPopulateFundTransferDTOElse() throws ChatakAdminException {
		PGMerchantBank pgMerchantBank = new PGMerchantBank();
		PGAccount pgAccount = new PGAccount();
		PGMerchant pgMerchant = new PGMerchant();
		pgAccount.setAvailableBalance(Long.parseLong("5345"));
		Mockito.when(merchantBankDao.getMerchantBankByMerchantId(Matchers.anyString())).thenReturn(pgMerchantBank);
		Mockito.when(accountDao.getPgAccount(Matchers.anyString())).thenReturn(pgAccount);
		Mockito.when(merchantUpdateDao.getMerchant(Matchers.anyString())).thenReturn(pgMerchant);

		fundTransfersServiceImpl.populateFundTransferDTO("5464", "FT_CHECK");
	}

	@Test(expected = ChatakAdminException.class)
	public void testProcessEFTFundsTransfer() throws ChatakAdminException {
		FundTransferDTO fundTransferDTO = new FundTransferDTO();
		DebitAccount account = new DebitAccount();
		account.setAvaliableBalance("5463");
		fundTransferDTO.setDebitAccount(account);
		fundTransferDTO.setAmountToTransfer("564356");
		fundTransferDTO.setMerchantCode("43654");
		fundTransfersServiceImpl.processEFTFundsTransfer(fundTransferDTO);
	}

	@Test(expected = ChatakAdminException.class)
	public void testProcessEFTFundsTransferElse() throws ChatakAdminException {
		FundTransferDTO fundTransferDTO = new FundTransferDTO();
		DebitAccount account = new DebitAccount();
		account.setAvaliableBalance("5463");
		fundTransferDTO.setDebitAccount(account);
		fundTransferDTO.setAmountToTransfer("564356");
		fundTransferDTO.setMerchantCode("43654");
		fundTransfersServiceImpl.processEFTFundsTransfer(fundTransferDTO);
	}

	@Test(expected = ChatakAdminException.class)
	public void testProcessCheckFundsTransfer() throws ChatakAdminException {
		FundTransferDTO fundTransferDTO = new FundTransferDTO();
		DebitAccount account = new DebitAccount();
		account.setAvaliableBalance("5463");
		fundTransferDTO.setDebitAccount(account);
		fundTransferDTO.setAmountToTransfer("564356");
		fundTransfersServiceImpl.processCheckFundsTransfer(fundTransferDTO);
	}

	@Test
	public void testLogTransfers() {
		FundTransferDTO fundTransferDTO = new FundTransferDTO();
		DebitAccount debitAccount = new DebitAccount();
		fundTransferDTO.setMerchantCode("65234");
		fundTransferDTO.setAmountToTransfer("8765");
		debitAccount.setAccountNumber(Long.parseLong("654321"));
		fundTransferDTO.setDebitAccount(debitAccount);
		fundTransferDTO.setFundTransferMode("FT_BANK");
		fundTransfersServiceImpl.logTransfers(fundTransferDTO);
	}

	@Test
	public void testLogTransfersElse() {
		FundTransferDTO fundTransferDTO = new FundTransferDTO();
		DebitAccount debitAccount = new DebitAccount();
		fundTransferDTO.setMerchantCode("65234");
		fundTransferDTO.setAmountToTransfer("8765");
		debitAccount.setAccountNumber(Long.parseLong("654321"));
		fundTransferDTO.setDebitAccount(debitAccount);
		fundTransferDTO.setFundTransferMode("FT_CHECK");
		fundTransfersServiceImpl.logTransfers(fundTransferDTO);
	}

	@Test
	public void testSplitReportsAmount() {
		List<ReportsDTO> list = new ArrayList<>();
		ReportsDTO dto = new ReportsDTO();
		dto.setAmount("5435");
		list.add(dto);
		fundTransfersServiceImpl.splitReportsAmount(list);
	}

	@Test
	public void testGetReportsEFTAmount() throws ChatakAdminException {
		Map<String, Long> splitList = new HashMap<>();
		splitList.put("111", Long.parseLong("5646"));
		fundTransfersServiceImpl.getReportsEFTAmount(splitList);
	}

	@Test
	public void testGetTransactionIdListOnTransferId() throws ChatakAdminException {
		List<EFTRefTxnData> transactionIdsList = new ArrayList<>();
		EFTRefTxnData data = new EFTRefTxnData();
		transactionIdsList.add(data);
		Mockito.when(refundTransactionDao.getEFTRefTxnDataList(Matchers.anyString())).thenReturn(transactionIdsList);
		fundTransfersServiceImpl.getTransactionIdListOnTransferId("6543");
	}

	@Test
	public void testGetLitleExecutedTransactionsOnMerchantCodeAndPayoutFrequency() throws ChatakAdminException {
		fundTransfersServiceImpl.getLitleExecutedTransactionsOnMerchantCodeAndPayoutFrequency("6543",
				Integer.parseInt("543"));
	}

	@Test
	public void testFetchAccountDetails() throws ChatakAdminException {
		PGAccount account = new PGAccount();
		account.setStatus("active");
		Mockito.when(accountDao.getAccountonAccountNumber(Matchers.anyLong())).thenReturn(account);
		fundTransfersServiceImpl.fetchAccountDetails("6543");
	}

	@Test
	public void testFetchAccountDetailsElse() throws ChatakAdminException {
		fundTransfersServiceImpl.fetchAccountDetails("6543");

	}

	public void testFetchAccountDetailsException() throws ChatakAdminException {
		PGAccount account = new PGAccount();
		Mockito.when(accountDao.getAccountonAccountNumber(Matchers.anyLong())).thenReturn(account);
		fundTransfersServiceImpl.fetchAccountDetails("6543");

	}

	@Test(expected = ChatakAdminException.class)
	public void testprocessAccountTransfer() throws ChatakAdminException {
		AccountTransferRequest accountTransferRequest = new AccountTransferRequest();
		PGAccount sourceAccount = new PGAccount();
		accountTransferRequest.setSourceAccountNumber("564");
		accountTransferRequest.setDestinationAccountNumber("5647");
		accountTransferRequest.setAccountCloseFlag("535");
		sourceAccount.setAvailableBalance(Long.parseLong("55"));
		accountTransferRequest.setTransferAmount(Double.parseDouble("4435"));
		accountTransferRequest.setAccountCloseFlag("false");
		Mockito.when(accountDao.getAccountonAccountNumber(Matchers.anyLong())).thenReturn(sourceAccount);
		fundTransfersServiceImpl.processAccountTransfer(accountTransferRequest);

	}

	@Test
	public void testprocessAccountTransferElse() throws ChatakAdminException {
		AccountTransferRequest accountTransferRequest = new AccountTransferRequest();
		PGAccount sourceAccount = new PGAccount();
		accountTransferRequest.setSourceAccountNumber("564");
		accountTransferRequest.setDestinationAccountNumber("5647");
		accountTransferRequest.setAccountCloseFlag("535");
		sourceAccount.setAvailableBalance(Long.parseLong("55"));
		accountTransferRequest.setTransferAmount(Double.parseDouble("4435"));
		accountTransferRequest.setAccountCloseFlag("true");
		sourceAccount.setCategory("primary");
		sourceAccount.setAccountNum(Long.parseLong("5467"));
		Mockito.when(accountDao.getAccountonAccountNumber(Matchers.anyLong())).thenReturn(sourceAccount);
		Mockito.when(accountDao.getSecondaryAccount(Matchers.anyString())).thenReturn(sourceAccount);
		Mockito.when(accountTransactionsDao.generateAccountTransactionId()).thenReturn("abcde");

		fundTransfersServiceImpl.processAccountTransfer(accountTransferRequest);

	}

}
