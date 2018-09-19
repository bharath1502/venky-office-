package com.chatak.acquirer.admin.service.impl;

import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.chatak.acquirer.admin.exception.ChatakAdminException;
import com.chatak.acquirer.admin.model.EntityMappedTransactions;
import com.chatak.acquirer.admin.model.TransactionResponse;
import com.chatak.acquirer.admin.service.SettlementReportService;
import com.chatak.acquirer.admin.service.SettlementService;
import com.chatak.pg.acq.dao.AccountDao;
import com.chatak.pg.acq.dao.AccountTransactionsDao;
import com.chatak.pg.acq.dao.IsoServiceDao;
import com.chatak.pg.acq.dao.IssSettlementDataDao;
import com.chatak.pg.acq.dao.IssuanceSettlementDao;
import com.chatak.pg.acq.dao.IssuanceSettlementTransactionHistoryDao;
import com.chatak.pg.acq.dao.ProgramManagerDao;
import com.chatak.pg.acq.dao.SettlementReportDao;
import com.chatak.pg.acq.dao.TransactionDao;
import com.chatak.pg.acq.dao.model.IsoAccount;
import com.chatak.pg.acq.dao.model.PGAccount;
import com.chatak.pg.acq.dao.model.PGIssSettlementData;
import com.chatak.pg.acq.dao.model.ProgramManagerAccount;
import com.chatak.pg.acq.dao.model.settlement.PGSettlementEntityHistory;
import com.chatak.pg.bean.settlement.IssuanceSettlementTransactionEntity;
import com.chatak.pg.bean.settlement.IssuanceSettlementTransactions;
import com.chatak.pg.bean.settlement.SettlementEntity;
import com.chatak.pg.bean.settlement.SettlementMerchantDetails;
import com.chatak.pg.constants.AccountTransactionCode;
import com.chatak.pg.dao.util.StringUtil;
import com.chatak.pg.model.FeeReportRequest;
import com.chatak.pg.user.bean.GetBatchReportRequest;
import com.chatak.pg.user.bean.GetTransactionsListRequest;
import com.chatak.pg.user.bean.GetTransactionsListResponse;
import com.chatak.pg.user.bean.Transaction;
import com.chatak.pg.util.Constants;

@Service
public class SettlementReportServiceImpl implements SettlementReportService {

	private Logger logger = Logger.getLogger(SettlementReportServiceImpl.class);

	@Autowired
	private SettlementReportDao settlementReportDao;

	@Autowired
	ProgramManagerDao programManagerDao;

	@Autowired
	IsoServiceDao isoServiceDao;

	@Autowired
	private SettlementService settlementService;

	@Autowired
	AccountDao accountDao;

	@Autowired
	AccountTransactionsDao accountTransactionsDao;

	@Autowired
	private IssuanceSettlementDao issuanceSettlementDao;
	
	@Autowired
	private IssuanceSettlementTransactionHistoryDao issuanceSettlementTransactionHistoryDao;
	
	@Autowired
	private IssSettlementDataDao issSettlementDataDao;
	
	@Autowired
	private TransactionDao transactionDao;

	@Override
	public GetTransactionsListResponse searchSettlementReportTransactions(
			GetTransactionsListRequest getTransactionsListRequest) throws ChatakAdminException {
		logger.info("Entering :: TransactionServiceImpl :: searchTransactions method");
		GetTransactionsListResponse response = new GetTransactionsListResponse();
		try {
			List<Transaction> transactions = settlementReportDao
					.getSettlementReportTransactions(getTransactionsListRequest);

			if (transactions != null) {
				response.setTransactionList(transactions);
				response.setTotalResultCount(getTransactionsListRequest.getNoOfRecords());
				response.setErrorCode(Constants.SUCCESS_CODE);
				response.setErrorMessage(Constants.SUCESS);
			} else {
				response.setTransactionList(transactions);
				response.setTotalResultCount(getTransactionsListRequest.getNoOfRecords());
				response.setErrorCode(Constants.ERROR_CODE);
				response.setErrorMessage(Constants.ERROR);
			}
			logger.info("Exiting :: TransactionServiceImpl :: searchTransactions method");
			return response;
		} catch (Exception exp) {
			logger.error("Error :: TransactionServiceImpl :: searchTransactions method", exp);
			response.setErrorCode(Constants.ERROR);
			response.setErrorMessage(Constants.ERROR_DATA);
		}
		return null;
	}

	@Override
	public GetTransactionsListResponse searchBatchReportTransactions(GetBatchReportRequest batchReportRequest)
			throws ChatakAdminException {
		logger.info("Entering :: TransactionServiceImpl :: searchTransactions method");
		GetTransactionsListResponse response = new GetTransactionsListResponse();
		try {
			List<Transaction> transactions = settlementReportDao.getBatchReportTransactions(batchReportRequest);

			if (transactions != null) {
				response.setTransactionList(transactions);
				response.setErrorCode(Constants.SUCCESS_CODE);
				response.setTotalResultCount(batchReportRequest.getNoOfRecords());
				response.setErrorMessage(Constants.SUCESS);
			} else {
				response.setTransactionList(transactions);
				response.setErrorCode(Constants.ERROR_CODE);
				response.setTotalResultCount(batchReportRequest.getNoOfRecords());
				response.setErrorMessage(Constants.ERROR);
			}
			logger.info("Exiting :: TransactionServiceImpl :: searchTransactions method");
			return response;
		} catch (Exception exp) {
			logger.error("Error :: TransactionServiceImpl :: searchTransactions method", exp);
			response.setErrorCode(Constants.ERROR);
			response.setErrorMessage(Constants.ERROR_DATA);
		}
		return null;
	}

	@Transactional(rollbackFor = Exception.class)
	public TransactionResponse calculateSettlementAmounts(Long pmId) {
		logger.info("Entering :: SettlementReportServiceImpl :: calculateSettlementAmounts");
		TransactionResponse transactionResponse = new TransactionResponse();
		
		// Start with the funds received from Issuance
		List<PGIssSettlementData> issSettlementData = issSettlementDataDao.findByProgramManagerId(pmId);
		BigInteger pmEarnedAmount = issSettlementData.get(0).getTotalAmount();
		
		// ISO total amounts
		Map<Long, EntityMappedTransactions> isoTotalRevenue = new HashMap<Long, EntityMappedTransactions>();

		// Merchant total amounts
		Map<String, EntityMappedTransactions> isoMappedMerchantTotalRevenue = new HashMap<String, EntityMappedTransactions>();
		
		// Merchant total amounts
		Map<String, EntityMappedTransactions> pmMappedMerchantTotalRevenue = new HashMap<String, EntityMappedTransactions>();
		
		// Retrieve all matched transactions where batch Id is not null
		// Since these are transactions which have found a match in Acquiring.
		List<IssuanceSettlementTransactionEntity> matchedTransactions = issuanceSettlementDao.getAllMatchedTransactions(pmId);
		logger.info("matched txns size : " + matchedTransactions.size());

		for (IssuanceSettlementTransactionEntity transaction : matchedTransactions) {
		  logger.info("Iterating PG_ISS_SETTLEMENT_ENTITY, MID : " + transaction.getMerchantId());
			
			List<IssuanceSettlementTransactions> settlementTransactionsList = transaction.getSettlementTransactionsList();
			
			for (IssuanceSettlementTransactions issuanceSettlementTransactions : settlementTransactionsList) {
				// Check if transaction belongs to an ISO or PM
				if (issuanceSettlementTransactions.getIsoId() != null) {
				  logger.info("Iterating PG_ISS_SETTLEMENT_ENTITY, ISO Transaction, ISO Id : " 
							+ issuanceSettlementTransactions.getIsoId());
					
					// Transaction belongs to ISO
					// ***** START ISO total revenue calculation ***** 
					EntityMappedTransactions isoMappedTransaction = isoTotalRevenue.get(issuanceSettlementTransactions.getIsoId());
					isoMappedTransaction = createEntityMappedTransactions(isoMappedTransaction);
					
					logger.info("Iterating PG_ISS_SETTLEMENT_ENTITY, ISO Transaction, ISO Id : " 
							+ issuanceSettlementTransactions.getIsoId() + " : Adding PG Txn ID: " + issuanceSettlementTransactions.getPgTransactionId());
					// Add the PG transaction ID
					isoMappedTransaction.getpGTransactionIds().add(issuanceSettlementTransactions.getPgTransactionId());
					BigInteger totalISOAmount = isoMappedTransaction.getTotalEntityAmount();
					Long isoFeeEarned = issuanceSettlementTransactions.getIsoAmount();
					
					totalISOAmount = totalISOAmount.add(setIsoFeeEarned(isoFeeEarned));
					logger.info("Iterating PG_ISS_SETTLEMENT_ENTITY, ISO Transaction, ISO Id : " 
							+ issuanceSettlementTransactions.getIsoId() + " : totalISOAmount: " + totalISOAmount);
					
					// Set the total entity amount back
					isoMappedTransaction.setTotalEntityAmount(totalISOAmount);
					
					isoTotalRevenue.put(issuanceSettlementTransactions.getIsoId(), isoMappedTransaction);
					// *****  END ISO total revenue calculation ***** 
					
					// *****  START ISO mapped Merchants grouping ***** 
					logger.info("Iterating PG_ISS_SETTLEMENT_ENTITY, ISO Transaction, MID : " 
					+ transaction.getMerchantId()
					+ " : ISO transaction: ISO Id: " + issuanceSettlementTransactions.getIsoId());
					
					EntityMappedTransactions isoMappedMerchant = isoMappedMerchantTotalRevenue.get(transaction.getMerchantId());
					isoMappedMerchant = createEntityMappedTransactions(isoMappedMerchant);
					
					logger.info("Iterating PG_ISS_SETTLEMENT_ENTITY, ISO Transaction, MID : " 
					+ transaction.getMerchantId()
					+ " : Adding PG Txn ID: " + issuanceSettlementTransactions.getPgTransactionId());
					// Add the PG transaction ID
					isoMappedMerchant.getpGTransactionIds().add(issuanceSettlementTransactions.getPgTransactionId());
					
					logger.info("Iterating PG_ISS_SETTLEMENT_ENTITY, ISO Transaction, MID : " 
					+ transaction.getMerchantId()
					+ " : Gross merchant amount: " + transaction.getMerchantAmount());
					// Need not add with the previous amount, since it will be the gross amount itself
					isoMappedMerchant.setTotalEntityAmount(BigInteger.valueOf(transaction.getMerchantAmount()));
					
					isoMappedMerchantTotalRevenue.put(transaction.getMerchantId(), isoMappedMerchant);
					// *****  END ISO mapped Merchants grouping ***** 
					
					// Subtract the ISO earned amount from the PM received amount
					pmEarnedAmount = pmEarnedAmount.subtract(setIsoFeeEarned(isoFeeEarned));
					logger.info("Iterating PG_ISS_SETTLEMENT_ENTITY, ISO Transaction, MID : " 
							+ transaction.getMerchantId()
							+ " : pmEarnedAmount: " + pmEarnedAmount);
					
				} else {
					
					// Transaction belongs to PM
				  logger.info("Iterating PG_ISS_SETTLEMENT_ENTITY, PM Transaction, PM Id : " 
							+ transaction.getAcqPmId());
					
					// *****  START PM mapped Merchants grouping ***** 
					EntityMappedTransactions pmMappedTransactions = pmMappedMerchantTotalRevenue.get(transaction.getMerchantId());
					pmMappedTransactions = createEntityMappedTransactions(pmMappedTransactions);
					
					logger.info("Iterating PG_ISS_SETTLEMENT_ENTITY, PM Transaction, MID : " 
							+ transaction.getMerchantId()
							+ " : Adding PG Txn ID: " + issuanceSettlementTransactions.getPgTransactionId());
					// Add the PG transaction ID
					pmMappedTransactions.getpGTransactionIds().add(issuanceSettlementTransactions.getPgTransactionId());
					
					logger.info("Iterating PG_ISS_SETTLEMENT_ENTITY, PM Transaction, MID : " 
							+ transaction.getMerchantId()
							+ " : Gross merchant amount: " + transaction.getMerchantAmount());
					// Need not add with the previous amount, since it will be the gross amount itself
					pmMappedTransactions.setTotalEntityAmount(BigInteger.valueOf(transaction.getMerchantAmount()));
					
					pmMappedMerchantTotalRevenue.put(transaction.getMerchantId(), pmMappedTransactions);
					// *****  END PM mapped Merchants grouping ***** 
				}
				
			}
			
			pmEarnedAmount = pmEarnedAmount.subtract(BigInteger.valueOf(transaction.getMerchantAmount()));
			logger.info("Iterating PG_ISS_SETTLEMENT_ENTITY, MID : " + transaction.getMerchantId()
			+ ", pmEarnedAmount: " + pmEarnedAmount);
		}
		
		transactionResponse.setIsoMappedMerchantTotalRevenue(isoMappedMerchantTotalRevenue);
		transactionResponse.setIsoTotalRevenue(isoTotalRevenue);
		transactionResponse.setPmMappedMerchantTotalRevenue(pmMappedMerchantTotalRevenue);
		transactionResponse.setPmDebitAmount(pmEarnedAmount);
		
		logger.info("Exiting :: SettlementReportServiceImpl :: calculateSettlementAmounts");
		return transactionResponse;
	}

	private EntityMappedTransactions createEntityMappedTransactions(EntityMappedTransactions isoMappedTransaction) {
		if(isoMappedTransaction == null) {
			isoMappedTransaction = new EntityMappedTransactions();
		}
		return isoMappedTransaction;
	}

	private BigInteger setIsoFeeEarned(Long isoFeeEarned) {
		return isoFeeEarned == null ? BigInteger.valueOf(0) : BigInteger.valueOf(isoFeeEarned);
	}
	
	@Transactional(rollbackFor = Exception.class)
	public TransactionResponse executeSettlement(Long pmId, String timeZoneOffset, String timeZoneRegion) {
		logger.info("Entering :: SettlementReportServiceImpl :: executeSettlement");

		TransactionResponse response = new TransactionResponse(); 
		
		// Generate a common AccountTransactionId for all transactions.
		String accountTransactionId = accountTransactionsDao.generateAccountTransactionId();
		logger.info("Generated Account Txn id : " + accountTransactionId);
		
		// Fetch PM account
		ProgramManagerAccount pmAccount = programManagerDao.findByProgramManagerIdAndAccountType(pmId, "System Account");
		
		// Retrieve all matched transactions where batch Id is not null
		// Since these are transactions which have found a match in Acquiring.
		List<IssuanceSettlementTransactionEntity> matchedTransactions = issuanceSettlementDao.getAllMatchedTransactions(pmId);
		logger.info("matched txns size : " + matchedTransactions.size());

		List<String> pgTxnIdsList = new ArrayList<>();
		
		for (IssuanceSettlementTransactionEntity transaction : matchedTransactions) {
		  logger.info("Iterating PG_ISS_SETTLEMENT_ENTITY, calculating for MID : " + transaction.getMerchantId());
			
			Long merchantAmount = transaction.getMerchantAmount();
			
			logger.info("PM balance before merchant amount debit : " + pmAccount.getCurrentBalance());
			
			// ****** PM DEBIT *******
			// Debit the merchant gross amount
			pmAccount.setAvailableBalance(pmAccount.getAvailableBalance() - merchantAmount);
			pmAccount.setCurrentBalance(pmAccount.getCurrentBalance() - merchantAmount);
			programManagerDao.saveOrUpdateProgramManagerAccount(pmAccount);

			// Log the debit transaction in PG Account Transaction
			settlementService.logRevenueAccountTransaction(transaction.getBatchid(), pmAccount.getAccountNumber(),
					pmAccount.getCurrentBalance(), pmAccount.getProgramManagerId(), merchantAmount,
					AccountTransactionCode.SYSTEM_REVENUE_DEBIT, Constants.PM_USER_TYPE, timeZoneOffset, timeZoneRegion,
					accountTransactionId);
			logger.info("Logging PM dedit amount: " + merchantAmount + ", for MID: " + transaction.getMerchantId());
			
			// ******* MERCHANT CREDIT *******
			// Credit merchant account
			PGAccount merchantAccount = isoServiceDao.findByEntityId(transaction.getMerchantId());
			merchantAccount.setCurrentBalance(merchantAccount.getCurrentBalance() + merchantAmount);
			merchantAccount.setAvailableBalance(merchantAccount.getAvailableBalance() + merchantAmount);
			accountDao.savePGAccount(merchantAccount);

			// Log the transaction in PG Account Transaction
			settlementService.logRevenueAccountTransaction(transaction.getBatchid(), merchantAccount.getAccountNum(),
					merchantAccount.getCurrentBalance(), Long.parseLong(merchantAccount.getEntityId()),
					merchantAmount, AccountTransactionCode.SYSTEM_REVENUE_CREDIT, Constants.TYPE_MERCHANT,
					timeZoneOffset, timeZoneRegion, accountTransactionId);
			logger.info("Logging Merchant credit amount: " + merchantAmount + ", for MID: " + transaction.getMerchantId());
			
			List<IssuanceSettlementTransactions> settlementTransactionsList = transaction.getSettlementTransactionsList();
			
			BigInteger totalISORevenue = new BigInteger("0");
			for (IssuanceSettlementTransactions issuanceSettlementTransactions : settlementTransactionsList) {
				// Check if transaction belongs to an ISO or PM
				if (issuanceSettlementTransactions.getIsoId() != null) {
					totalISORevenue = totalISORevenue.add(BigInteger.valueOf(issuanceSettlementTransactions.getIsoAmount()));
				}
			}
			logger.info("Calculated total ISO revenue earned at MID : " + transaction.getMerchantId()
			+ ", by ISO: " + settlementTransactionsList.get(0).getIsoId() + " : " + totalISORevenue);
			
			if(totalISORevenue.compareTo(BigInteger.valueOf(0)) > 0) {
				// ****** PM DEBIT *******
				// Debit the ISO gross amount
				pmAccount.setAvailableBalance(pmAccount.getAvailableBalance() - totalISORevenue.longValue());
				pmAccount.setCurrentBalance(pmAccount.getCurrentBalance() - totalISORevenue.longValue());
				programManagerDao.saveOrUpdateProgramManagerAccount(pmAccount);

				// Log the debit transaction in PG Account Transaction
				settlementService.logRevenueAccountTransaction(transaction.getBatchid(), pmAccount.getAccountNumber(),
						pmAccount.getCurrentBalance(), pmAccount.getProgramManagerId(), totalISORevenue.longValue(),
						AccountTransactionCode.SYSTEM_REVENUE_DEBIT, Constants.PM_USER_TYPE, timeZoneOffset, timeZoneRegion,
						accountTransactionId);
				logger.info("Logging PM dedit amount: " + totalISORevenue.longValue() 
				+ ", for ISO: " + settlementTransactionsList.get(0).getIsoId());
				
				// ******* ISO CREDIT *******
				// Credit ISO account
				IsoAccount isoAccount = isoServiceDao.findAccountByIsoId(settlementTransactionsList.get(0).getIsoId()).get(0);
				isoAccount.setAvailableBalance(isoAccount.getAvailableBalance() + totalISORevenue.longValue());
				isoAccount.setCurrentBalance(isoAccount.getCurrentBalance() + totalISORevenue.longValue());
				isoServiceDao.saveIsoAccount(isoAccount);

				// Log the credit transaction in PG Account Transaction
				settlementService.logRevenueAccountTransaction(transaction.getBatchid(), isoAccount.getAccountNumber(),
						isoAccount.getCurrentBalance(), isoAccount.getIsoId(), totalISORevenue.longValue(),
						AccountTransactionCode.SYSTEM_REVENUE_CREDIT, Constants.ISO_USER_TYPE, timeZoneOffset, timeZoneRegion,
						accountTransactionId);
				logger.info("Logging ISO credit amount: " + totalISORevenue.longValue() 
				+ ", for ISO: " + settlementTransactionsList.get(0).getIsoId());
			}
			
				// Update the PGTransactionTable with status executed
				List<IssuanceSettlementTransactions> settlementTransactions = transaction.getSettlementTransactionsList();

				for (IssuanceSettlementTransactions issuanceSettlementTransactions : settlementTransactions) {
					pgTxnIdsList.add(issuanceSettlementTransactions.getPgTransactionId());
				}
				logger.info("Added pgTxnIds" + pgTxnIdsList);
			}

			transactionDao.saveorUpdate(pgTxnIdsList);
			logger.info("Final remaining PM balance: " + pmAccount.getCurrentBalance());		
			
			//Update the PG_ISS_Settlement data table status as Executed
			if(StringUtil.isListNotNullNEmpty(matchedTransactions)) {
			logger.info("Acqpmid and BatchfileDate : " + matchedTransactions.get(0).getAcqPmId() + matchedTransactions.get(0).getBatchFileDate());	
			PGIssSettlementData isssettlementdata = issSettlementDataDao.getIssSettlementData(matchedTransactions.get(0).getAcqPmId(), matchedTransactions.get(0).getBatchFileDate()).get(0);
			isssettlementdata.setStatus(Constants.EXECUTED_STATUS);
			issSettlementDataDao.saveIssSettlementData(isssettlementdata);
			}
			
			response.setErrorCode(Constants.SUCCESS_CODE);
			response.setErrorMessage(Constants.SUCCESS);
		
			logger.info("Exiting :: SettlementReportServiceImpl :: executeSettlement");
		
			return response;
	}
	
	public void insertDataFromIssuanceSettlementTransaction() {
		issuanceSettlementTransactionHistoryDao.insertDataFromIssuanceSettlementTransaction();
	}

	@Override
	public TransactionResponse getAllMatchedTxnsByPgTxns(FeeReportRequest transactionRequest) throws ChatakAdminException{
		logger.info("Entering :: SettlementReportServiceImpl :: getAllMatchedTxnsByPgTxns");
		TransactionResponse transactionResponse = new TransactionResponse();
		try {
			List<SettlementEntity> settlementEntityList = settlementReportDao.getAllMatchedTxnsByPgTxns(transactionRequest);
			if(StringUtil.isListNotNullNEmpty(settlementEntityList)) {
				transactionResponse.setSettlementEntity(settlementEntityList);
			}
		}catch(Exception e) {
			logger.error("Error :: SettlementReportServiceImpl :: getAllMatchedTxnsByPgTxns : " + e.getMessage(), e);
		}
		logger.info("Exiting :: SettlementReportServiceImpl :: getAllMatchedTxnsByPgTxns");
		return transactionResponse;
	}
	
	@Override
	public List<SettlementMerchantDetails> fetchMerchantDetailsByMerchantCode(String merchantCode) {
		return settlementReportDao.fetchMerchantDetailsByMerchantCode(merchantCode);
	}
	
	@Override
	public PGSettlementEntityHistory findByBatchFileDateandAcqpmid(Long pmId, Timestamp date) {
		return issuanceSettlementTransactionHistoryDao.findByBatchFileDateandAcqpmid(pmId, date);
	
	}
}
