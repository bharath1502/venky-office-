/**
 * 
 */
package com.chatak.pg.acq.dao.impl;

import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import com.chatak.pg.acq.dao.MerchantUpdateDao;
import com.chatak.pg.acq.dao.PGTransfersDao;
import com.chatak.pg.acq.dao.VoidTransactionDao;
import com.chatak.pg.acq.dao.model.PGAccount;
import com.chatak.pg.acq.dao.model.PGMerchant;
import com.chatak.pg.acq.dao.model.PGTransaction;
import com.chatak.pg.acq.dao.model.PGTransfers;
import com.chatak.pg.acq.dao.model.PGTxnCardInfo;
import com.chatak.pg.acq.dao.model.QPGAccount;
import com.chatak.pg.acq.dao.model.QPGMerchant;
import com.chatak.pg.acq.dao.model.QPGTransaction;
import com.chatak.pg.acq.dao.repository.AccountRepository;
import com.chatak.pg.acq.dao.repository.CardInfoRepository;
import com.chatak.pg.acq.dao.repository.TransactionRepository;
import com.chatak.pg.constants.PGConstants;
import com.chatak.pg.enums.EntryModePortalDisplayEnum;
import com.chatak.pg.model.DashBoardRecords;
import com.chatak.pg.model.ReportsDTO;
import com.chatak.pg.user.bean.GetTransactionsListRequest;
import com.chatak.pg.user.bean.GetTransferListRequest;
import com.chatak.pg.user.bean.Transaction;
import com.chatak.pg.util.CommonUtil;
import com.chatak.pg.util.Constants;
import com.chatak.pg.util.DateUtil;
import com.chatak.pg.util.StringUtils;
import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQuery;

/**
 * @Author: Girmiti Software
 * @Date: Aug 24, 2017
 * @Time: 11:45:59 AM
 * @Version: 1.0
 * @Comments: 
 *
 */

@Repository("voidTransactionDao")
public class VoidTransactionDaoImpl extends TransactionDaoImpl implements VoidTransactionDao {

  private static Logger log = Logger.getLogger(VoidTransactionDaoImpl.class);

  @Autowired
  private CardInfoRepository cardInfoRepository;

  @Autowired
  private TransactionRepository transactionRepository;

  @Autowired
  private AccountRepository accountRepository;

  @Autowired
  private PGTransfersDao pgTransferDao;

  @PersistenceContext
  private EntityManager entityManager;

  @Autowired
  private MerchantUpdateDao merchantUpdateDao;

  @Override
  public PGTransaction getTransactionToVoid(String merchantId, String terminalId, String txnId,
      String authId) {
    log.debug("TransactionDaoImpl | getTransactionToVoid | Entering");
    PGTransaction transaction = null;
    List<PGTransaction> pgTxnlist = transactionRepository.findByVoidTransactionType(merchantId,
        terminalId, new BigInteger(txnId), "sale", "refund");
    transaction = getPGTransaction(pgTxnlist);
    log.debug("TransactionDaoImpl | getTransactionToVoid | Exiting");
    return transaction;
  }

  @Override
  public PGTransaction findTransactionToVoidByIssuerTxnId(String issuerTxnRefNum) {
    log.debug("TransactionDaoImpl | findTransactionToVoidByIssuerTxnId | Entering");
    PGTransaction transaction = null;
    List<PGTransaction> pgTxnlist =
        transactionRepository.findTransactionToVoidByIssuerTxnId(issuerTxnRefNum);
    transaction = getPGTransaction(pgTxnlist);
    log.debug("TransactionDaoImpl | findTransactionToVoidByIssuerTxnId | Exiting");
    return transaction;
  }

  @Override
  public PGTransaction findTransactionToVoidByPGTxnIdAndIssuerTxnId(String txnId,
      String issuerTxnRefNum) {
    log.debug("TransactionDaoImpl | findTransactionToVoidByPGTxnIdAndIssuerTxnId | Entering");
    PGTransaction transaction = null;
    List<PGTransaction> pgTxnlist =
        transactionRepository.findTransactionToVoidByPGTxnIdAndIssuerTxnId(txnId, issuerTxnRefNum);
    transaction = getPGTransaction(pgTxnlist);
    log.debug("TransactionDaoImpl | findTransactionToVoidByPGTxnIdAndIssuerTxnId | Exiting");
    return transaction;
  }

  @Override
  public PGTransaction findTransactionToVoidByPGTxnIdAndIssuerTxnIdAndMerchantIdAndTerminalId(
      String transactionId, String issuerTxnRefNum, String merchantId, String terminalId) {
    log.debug(
        "TransactionDaoImpl | findTransactionToVoidByPGTxnIdAndIssuerTxnIdAndMerchantIdAndTerminalId | Entering");
    PGTransaction transaction = null;
    List<PGTransaction> pgTxnlist = transactionRepository
        .findTransactionToVoidByPGTxnIdAndIssuerTxnIdAndMerchantIdAndTerminalId(new BigInteger(transactionId),
            issuerTxnRefNum, merchantId, terminalId);
    transaction = getPGTransaction(pgTxnlist);
    log.debug(
        "TransactionDaoImpl | findTransactionToVoidByPGTxnIdAndIssuerTxnIdAndMerchantIdAndTerminalId | Exiting");
    return transaction;
  }

  @Override
  public PGTransaction findTransactionToVoidByPGTxnIdAndMerchantIdAndTerminalId(
      String transactionId, String merchantId, String terminalId) {
    log.debug(
        "TransactionDaoImpl | findTransactionToVoidByPGTxnIdAndMerchantIdAndTerminalId | Entering");
    PGTransaction transaction = null;
    List<PGTransaction> pgTxnlist =
        transactionRepository.findTransactionToVoidByPGTxnIdAndMerchantIdAndTerminalId(
            transactionId, merchantId, terminalId);
    transaction = getPGTransaction(pgTxnlist);
    log.debug(
        "TransactionDaoImpl | findTransactionToVoidByPGTxnIdAndMerchantIdAndTerminalId | Exiting");
    return transaction;
  }

  @Override
  public PGTransaction createTransaction(PGTransaction pgTransaction) {
    log.debug("TransactionDaoImpl | createTransaction | Entering");
    if (null == pgTransaction.getAcqChannel()) {
      pgTransaction.setAcqChannel("POS");
    }
    if (null == pgTransaction.getAcqTxnMode()) {
      pgTransaction.setAcqTxnMode("POS");
    }
    log.debug("TransactionDaoImpl | createTransaction | Exiting");
    return transactionRepository.save(pgTransaction);
  }

  @Override
  public void createCardInfo(PGTxnCardInfo txnCardInfo) {
    log.debug("TransactionDaoImpl | createCardInfo | Entering");
    cardInfoRepository.save(txnCardInfo);
    log.debug("TransactionDaoImpl | createCardInfo | Exiting");
  }

  @Override
  public List<Transaction> getTransactionListToDashBoard(
      GetTransactionsListRequest getTransactionsListRequest) {
    log.info("TransactionDaoImpl | getTransactionListToDashBoard | Entering");
    List<Transaction> transactions = null;
    try {
      
      Integer totalRecords = getTransactionsListRequest.getNoOfRecords();
      List<Tuple> tupleList =
          getTransactionListToDashBoarRequest(getTransactionsListRequest, totalRecords);
      if (!CollectionUtils.isEmpty(tupleList)) {
        transactions = new ArrayList<>();
        Transaction transaction = null;
        for (Tuple tuple : tupleList) {
          transaction = new Transaction();

          transaction
              .setTransactionId(tuple.get(QPGTransaction.pGTransaction.transactionId).toString());
          transaction.setTransactionAmount(PGConstants.DOLLAR_SYMBOL
              + StringUtils.amountToString(tuple.get(QPGTransaction.pGTransaction.txnAmount)));
          transaction.setTransactionDate(DateUtil.toDateStringFormat(
              tuple.get(QPGTransaction.pGTransaction.createdDate), DateUtil.VIEW_DATE_TIME_FORMAT));
          transaction.setUpdated_date(DateUtil.toDateStringFormat(
              tuple.get(QPGTransaction.pGTransaction.updatedDate), DateUtil.VIEW_DATE_TIME_FORMAT));
          transaction.setTransaction_type(tuple.get(QPGTransaction.pGTransaction.transactionType));
          transaction.setMerchant_code((tuple.get(QPGTransaction.pGTransaction.merchantId)));
          transaction.setMaskCardNumber(StringUtils.lastFourDigits(tuple.get(QPGTransaction.pGTransaction.panMasked)));
          transaction.setProcTxnCode(tuple.get(QPGTransaction.pGTransaction.procCode) != null
              ? tuple.get(QPGTransaction.pGTransaction.procCode) : "");
          transaction.setRef_transaction_id(
              pgTransactionIssuerTxnRefNum(tuple));
          transaction
              .setMerchantFeeAmount(tuple.get(QPGTransaction.pGTransaction.merchantFeeAmount));
          transaction.setTxnDescription(tuple.get(QPGTransaction.pGTransaction.txnDescription));
          transaction.setMerchantSettlementStatus(
              tuple.get(QPGTransaction.pGTransaction.merchantSettlementStatus));
          transaction
              .setTerminal_id(Long.valueOf(tuple.get(QPGTransaction.pGTransaction.terminalId)));
          transaction.setAccountNumber(tuple.get(QPGAccount.pGAccount.accountNum));
          transaction.setMerchantType(tuple.get(QPGAccount.pGAccount.entityType));
          transaction.setMerchantName(tuple.get(QPGMerchant.pGMerchant.firstName));
          transaction.setAcqChannel(tuple.get(QPGTransaction.pGTransaction.acqChannel));
          transaction.setTxn_ref_num(
              pGTransactionRefTransactionId(tuple));
          validateForPosEntryMode(transaction, tuple);
          getTransactionListToDashBoardStatus(transactions, transaction, tuple);
        }
      }
    } catch (Exception e) {
      log.error("TransactionDaoImpl | getTransactionListToDashBoard | Exception ", e);
    }
    log.info("TransactionDaoImpl | getTransactionListToDashBoard | Exiting");
    return transactions;

  }

  private void validateForPosEntryMode(Transaction transaction, Tuple tuple) {
    String posEntryMode = tuple.get(QPGTransaction.pGTransaction.posEntryMode);
    if (null == posEntryMode) {
      posEntryMode = "00";
    }
    transaction.setEntryMode(EntryModePortalDisplayEnum
        .valueOf(getEntryModeEnumFromPosEntryMode(posEntryMode)).value());
  }

  private long pGTransactionRefTransactionId(Tuple tuple) {
    return StringUtils.isEmpty(refTransationTuple(tuple))
            ? 0L
            : Long
                .valueOf(refTransationTuple(tuple));
  }

  private String refTransationTuple(Tuple tuple) {
    return tuple.get(QPGTransaction.pGTransaction.refTransactionId) != null
        ? tuple.get(QPGTransaction.pGTransaction.refTransactionId).toString() : "";
  }

  private long pgTransactionIssuerTxnRefNum(Tuple tuple) {
    return StringUtils.isEmpty(issuerTxnRefNumTuple(tuple))
            ? 0L
            : Long.valueOf(issuerTxnRefNumTuple(tuple));
  }

  private String issuerTxnRefNumTuple(Tuple tuple) {
    return tuple.get(QPGTransaction.pGTransaction.issuerTxnRefNum) != null
        ? tuple.get(QPGTransaction.pGTransaction.issuerTxnRefNum).toString() : "";
  }

  private void getTransactionListToDashBoardStatus(List<Transaction> transactions,
      Transaction transaction, Tuple tuple) {
    if (0 == tuple.get(QPGTransaction.pGTransaction.status)) {
      transaction.setStatusMessage("Approved");
    } else if (1 == tuple.get(QPGTransaction.pGTransaction.status)) {
      transaction.setStatusMessage("Declined");
    } else {
      transaction.setStatusMessage("Failed");
    }
    transaction.setFeeString(PGConstants.DOLLAR_SYMBOL
        + StringUtils.amountToString(tuple.get(QPGTransaction.pGTransaction.feeAmount)));
    transaction.setAuth_id(tuple.get(QPGTransaction.pGTransaction.authId));
    transaction
        .setTxn_total_amount((tuple.get(QPGTransaction.pGTransaction.txnTotalAmount) != null)
            ? tuple.get(QPGTransaction.pGTransaction.txnTotalAmount).doubleValue() : null);
    transaction.setAcqTxnMode(tuple.get(QPGTransaction.pGTransaction.acqTxnMode));
    transaction.setInvoice_number(tuple.get(QPGTransaction.pGTransaction.invoiceNumber));
    transaction.setProcessor(tuple.get(QPGTransaction.pGTransaction.processor));
    transaction.setTxnMode(tuple.get(QPGTransaction.pGTransaction.txnMode));
    transaction.setCardHolderName(tuple.get(QPGTransaction.pGTransaction.cardHolderName));
    transaction.setMerchantBusinessName(tuple.get(QPGMerchant.pGMerchant.businessName));
    transaction.setMerchantType(tuple.get(QPGMerchant.pGMerchant.merchantType));

    transactions.add(transaction);
  }

  private List<Tuple> getTransactionListToDashBoarRequest(
      GetTransactionsListRequest getTransactionsListRequest, Integer totalRecords) {
    int offset;
    int limit;
    if (getTransactionsListRequest.getPageIndex() == null
        || getTransactionsListRequest.getPageIndex().intValue() == 1) {
      getTotalNumberOfRecordsOnSettlementStatus(getTransactionsListRequest);
      getTransactionsListRequest.setNoOfRecords(totalRecords);
    }
    getTransactionsListRequest.setNoOfRecords(totalRecords);
    if (getTransactionsListRequest.getPageIndex() == null
        || getTransactionsListRequest.getPageSize() == null) {
      offset = 0;
      limit = Constants.DEFAULT_PAGE_SIZE;
    } else {
      offset = (getTransactionsListRequest.getPageIndex() - 1)
          * getTransactionsListRequest.getPageSize();
      limit = getTransactionsListRequest.getPageSize();
    }
    JPAQuery<Tuple> query = new JPAQuery<Tuple>(entityManager);
    List<Tuple> tupleList = query.from(QPGTransaction.pGTransaction, QPGMerchant.pGMerchant)
    	.select(QPGTransaction.pGTransaction.merchantId, QPGTransaction.pGTransaction.transactionId,
            QPGTransaction.pGTransaction.issuerTxnRefNum, QPGTransaction.pGTransaction.procCode,
            QPGTransaction.pGTransaction.panMasked, QPGTransaction.pGTransaction.createdDate,
            QPGTransaction.pGTransaction.transactionType, QPGTransaction.pGTransaction.txnAmount,
            QPGTransaction.pGTransaction.feeAmount,
            QPGTransaction.pGTransaction.merchantFeeAmount,
            QPGTransaction.pGTransaction.txnDescription, QPGTransaction.pGTransaction.status,
            QPGTransaction.pGTransaction.merchantSettlementStatus,
            QPGTransaction.pGTransaction.terminalId, QPGTransaction.pGTransaction.feeAmount,
            QPGTransaction.pGTransaction.posEntryMode, QPGTransaction.pGTransaction.acqChannel,
            QPGTransaction.pGTransaction.updatedDate, QPGTransaction.pGTransaction.cardHolderName,
            QPGTransaction.pGTransaction.authId, QPGTransaction.pGTransaction.invoiceNumber,
            QPGTransaction.pGTransaction.acqTxnMode, QPGTransaction.pGTransaction.txnTotalAmount,
            QPGTransaction.pGTransaction.processor, QPGTransaction.pGTransaction.refTransactionId,
            QPGMerchant.pGMerchant.businessName, QPGMerchant.pGMerchant.merchantType)
        .where(isMerchantSettlementStatus(getTransactionsListRequest.getSettlementStatus()),
            QPGTransaction.pGTransaction.merchantId.eq(QPGMerchant.pGMerchant.merchantCode))
        .offset(offset).limit(limit).orderBy(orderByCreatedDateDesc())
        .fetch();
            return tupleList;
  }

  @Override
  public DashBoardRecords findDashBoardRecords() {
    DashBoardRecords dashBoardRecords = new DashBoardRecords();
    dashBoardRecords.setCanceledCount(0L);
    dashBoardRecords.setTotal(0L);
    final Long pendingCount =
        transactionRepository.getTransactionsCountOnStatus(PGConstants.PG_SETTLEMENT_PENDING);
    final Long processingCount =
        transactionRepository.getTransactionsCountOnStatus(PGConstants.PG_SETTLEMENT_PROCESSING);
    final Long executedCount =
        transactionRepository.getTransactionsCountOnStatus(PGConstants.PG_SETTLEMENT_EXECUTED);
    final Long rejectedCount =
        transactionRepository.getTransactionsCountOnStatus(PGConstants.PG_SETTLEMENT_REJECTED);
    dashBoardRecords.setPendingCount((pendingCount != null) ? pendingCount : 0L);
    dashBoardRecords.setExecutedCount((executedCount != null) ? executedCount : 0L);
    dashBoardRecords.setProcessingCount((processingCount != null) ? processingCount : 0L);
    dashBoardRecords.setRejectedCount((rejectedCount != null) ? rejectedCount : 0L);
    return dashBoardRecords;
  }

  private Integer getTotalNumberOfRecordsOnSettlementStatus(
      GetTransactionsListRequest getTransactionsListRequest) {
    JPAQuery<BigInteger> query = new JPAQuery<BigInteger>(entityManager);
    List<BigInteger> list = query.from(QPGTransaction.pGTransaction, QPGMerchant.pGMerchant)
    	.select(QPGTransaction.pGTransaction.id)
        .where(isMerchantSettlementStatus(getTransactionsListRequest.getSettlementStatus()),
            QPGTransaction.pGTransaction.merchantId.eq(QPGMerchant.pGMerchant.merchantCode))
        .fetch();

    return (StringUtils.isListNotNullNEmpty(list) ? list.size() : 0);
  }

  @Override
  public List<ReportsDTO> getAllEftTransfers() {
    List<ReportsDTO> list = new ArrayList<>();
    GetTransferListRequest getTransferListRequest = new GetTransferListRequest();
    getTransferListRequest.setTransferMode("EFT");
    List<PGTransfers> pgTransfers = pgTransferDao.getTransferList(getTransferListRequest);
    for (PGTransfers pgTransfer : pgTransfers) {
      ReportsDTO eftTransfers = new ReportsDTO();
      PGMerchant pgMerchant = merchantUpdateDao.getMerchant(pgTransfer.getMerchantId().toString());
      PGAccount pgAccount = accountRepository.findByEntityId(pgTransfer.getMerchantId().toString());
      if ((null != pgMerchant) && (null != pgAccount)) {
        eftTransfers.setUserName(pgMerchant.getUserName());
        eftTransfers.setCompanyName(pgMerchant.getBusinessName());
        eftTransfers.setAccountNumber(pgAccount.getAccountNum());
        eftTransfers.setAccountType(pgAccount.getEntityType());
        eftTransfers.setCurrency(pgAccount.getCurrency());
        eftTransfers.setAmount(CommonUtil.toAmount(pgTransfer.getAmount()));
        eftTransfers.setDescription(pgTransfer.getTxnDescription());
        eftTransfers.setPaymentMethod(PGConstants.PAYMENT_METHOD_DEBIT);
        eftTransfers.setTransactionId(pgTransfer.getPgTransfersId().toString());
        eftTransfers.setDateTime(
            DateUtil.toDateStringFormat(pgTransfer.getCreatedDate(), DateUtil.VIEW_DATE_TIME_FORMAT));
        list.add(eftTransfers);
      }
    }
    return Collections.emptyList();
  }

  @Override
  public PGTransaction findTransaction(String merchantId, String terminalId, String authId,
      String cardNum, String invoiceNum) {
    return transactionRepository.findByMerchantIdAndTerminalIdAndAuthIdAndPanAndInvoiceNumber(
        merchantId, terminalId, authId, cardNum, invoiceNum);
  }

  @Override
  public List<PGTransaction> findByMerchantIdAndTerminalIdAndRefTransactionId(String merchantId,
      String terminalId, String refId) {
    return transactionRepository.findByMerchantIdAndTerminalIdAndRefTransactionId(merchantId,
        terminalId, refId);
  }

  @Override
  public List<PGTransaction> findByMerchantIdAndTerminalIdAndRefTransactionIdAndStatus(
      String merchantId, String terminalId, String refId, Integer status) {
    return transactionRepository.findByMerchantIdAndTerminalIdAndRefTransactionIdAndStatus(
        merchantId, terminalId, refId, status);
  }

  @Override
  public PGTransaction findTransactionToCaptureByPGTxnIdAndIssuerTxnIdAndMerchantIdAndTerminalId(
      String transactionId, String issuerTxnRefNum, String merchantId, String terminalId) {
    log.debug(
        "TransactionDaoImpl | findTransactionToCaptureByPGTxnIdAndIssuerTxnIdAndMerchantIdAndTerminalId | Entering");
    PGTransaction transaction = null;
    List<PGTransaction> pgTxnlist = transactionRepository
        .findTransactionToCaptureByPGTxnIdAndIssuerTxnIdAndMerchantIdAndTerminalId(transactionId,
            issuerTxnRefNum, merchantId, terminalId);
    transaction = getPGTransaction(pgTxnlist);
    log.debug(
        "TransactionDaoImpl | findTransactionToCaptureByPGTxnIdAndIssuerTxnIdAndMerchantIdAndTerminalId | Exiting");
    return transaction;
  }

  @Override
  public PGTransaction findTransactionToReversalByMerchantIdAndPGTxnId(String merchantId,
      String transactionId) {
    log.debug("TransactionDaoImpl | findTransactionToReversalByMerchantIdAndPGTxnId | Entering");
    PGTransaction transaction = null;
    List<PGTransaction> pgTxnlist = transactionRepository
        .findTransactionToReverseByPGTxnIdAndMerchantId(transactionId, merchantId);
    transaction = getPGTransaction(pgTxnlist);
    log.debug("TransactionDaoImpl | findTransactionToReversalByMerchantIdAndPGTxnId | Exiting");
    return transaction;
  }

  @Override
  public PGTransaction findTransactionToCaptureByPGTxnIdAndMerchantIdAndTerminalId(
      String transactionId, String merchantId, String terminalId) {
    log.debug(
        "TransactionDaoImpl | findTransactionToCaptureByPGTxnIdAndMerchantIdAndTerminalId | Entering");
    PGTransaction transaction = null;
    List<PGTransaction> pgTxnlist =
        transactionRepository.findTransactionToCaptureByPGTxnIdAndMerchantIdAndTerminalId(
            transactionId, merchantId, terminalId);
    transaction = getPGTransaction(pgTxnlist);
    log.debug(
        "TransactionDaoImpl | findTransactionToCaptureByPGTxnIdAndMerchantIdAndTerminalId | Exiting");
    return transaction;
  }
  
  @Override
  public List<Transaction> getSearchTransactions(
      GetTransactionsListRequest getTransactionsListRequest) {
    log.info("TransactionDaoImpl | getSearchTransactions | Entering");
    List<Transaction> transactions = null;
    try {
      Timestamp startDate = null;
      Timestamp endDate = null;
      if (!CommonUtil.isNullAndEmpty(getTransactionsListRequest.getFrom_date())) {
        startDate = DateUtil.getStartDayTimestamp(getTransactionsListRequest.getFrom_date(),
            PGConstants.DD_MM_YYYY);
      }
      if (!CommonUtil.isNullAndEmpty(getTransactionsListRequest.getTo_date())) {
        endDate = DateUtil.getEndDayTimestamp(getTransactionsListRequest.getTo_date(),
            PGConstants.DD_MM_YYYY);
      }
      JPAQuery<Tuple> query = new JPAQuery<Tuple>(entityManager);
      List<Tuple> tupleList =
          query.from(QPGTransaction.pGTransaction, QPGMerchant.pGMerchant, QPGAccount.pGAccount)
          		.select(QPGTransaction.pGTransaction.merchantId, QPGTransaction.pGTransaction.transactionId,
          				QPGTransaction.pGTransaction.issuerTxnRefNum, QPGTransaction.pGTransaction.procCode,
          				QPGTransaction.pGTransaction.panMasked, QPGTransaction.pGTransaction.createdDate,
          				QPGTransaction.pGTransaction.transactionType, QPGTransaction.pGTransaction.txnAmount,
          				QPGTransaction.pGTransaction.feeAmount,
          				QPGTransaction.pGTransaction.merchantFeeAmount,
          				QPGTransaction.pGTransaction.txnDescription, QPGTransaction.pGTransaction.status,
          				QPGTransaction.pGTransaction.merchantSettlementStatus,
          				QPGTransaction.pGTransaction.terminalId, QPGTransaction.pGTransaction.posEntryMode,
          				QPGTransaction.pGTransaction.acqChannel, QPGTransaction.pGTransaction.cardHolderName,
          				QPGTransaction.pGTransaction.updatedDate, QPGTransaction.pGTransaction.authId,
          				QPGTransaction.pGTransaction.txnTotalAmount, QPGTransaction.pGTransaction.acqTxnMode,
          				QPGTransaction.pGTransaction.invoiceNumber, QPGTransaction.pGTransaction.processor,
          				QPGTransaction.pGTransaction.txnMode, QPGMerchant.pGMerchant.firstName,
          				QPGAccount.pGAccount.accountNum, QPGAccount.pGAccount.entityType,
          				QPGTransaction.pGTransaction.refTransactionId)
              .where(isMerchantId(getTransactionsListRequest.getMerchant_code()),
                  isTxnId(getTransactionsListRequest.getTransactionId()),
                  isProcessTxnId(getTransactionsListRequest.getProcessCode()),
                  isCardNumberLike(getTransactionsListRequest.getCardNumber()),
                  isTxnTypeLike(getTransactionsListRequest.getTransaction_type()),
                  isTxnStatusLike(getTransactionsListRequest.getStatus()),
                  isEntryModeLike(getTransactionsListRequest.getEntryMode()),
                  isAcqChannel(getTransactionsListRequest.getAcqChannel()),
                  isCardHolderNameLike(getTransactionsListRequest.getCardHolderName()),
                  isValidTxn(getTransactionsListRequest.getFromAmtRange(),
                      getTransactionsListRequest.getToAmtRange()),
              QPGTransaction.pGTransaction.merchantId.eq(QPGMerchant.pGMerchant.merchantCode),
              QPGTransaction.pGTransaction.merchantId.eq(QPGAccount.pGAccount.entityId),
              isMerchantNameLike(getTransactionsListRequest.getMerchantName()),
              isValidDate(startDate, endDate)).orderBy(orderByCreatedDateDesc())
          .fetch();
      if (!CollectionUtils.isEmpty(tupleList)) {
        transactions = validateTupleList(tupleList);
      }
    } catch (Exception e) {
      log.error("TransactionDaoImpl | getSearchTransactions | Exception " + e);
    }
    log.info("TransactionDaoImpl | getSearchTransactions | Exiting");
    return transactions;
  }

  private List<Transaction> validateTupleList(List<Tuple> tupleList) {
	List<Transaction> transactions;
	transactions = new ArrayList<>();
	Transaction transactionResp = null;
	for (Tuple tuple : tupleList) {
	  transactionResp = new Transaction();
	  String posEntryMode = getSearchTransactionsTupleList(transactionResp, tuple);
	  if (null == posEntryMode) {
	    posEntryMode = "00";
	  }
	  transactionResp.setEntryMode(EntryModePortalDisplayEnum
	      .valueOf(getEntryModeEnumFromPosEntryMode(posEntryMode)).value());
	  getSearchTransactionsStatus(transactions, transactionResp, tuple);
	}
	return transactions;
  }

  private String getSearchTransactionsTupleList(Transaction transactionResp, Tuple tuple) {
    transactionResp.setTransactionId(tuple.get(QPGTransaction.pGTransaction.transactionId));
    transactionResp.setTransactionAmount(PGConstants.DOLLAR_SYMBOL
        + StringUtils.amountToString(tuple.get(QPGTransaction.pGTransaction.txnAmount)));
    transactionResp.setTransactionDate(DateUtil.toDateStringFormat(
        tuple.get(QPGTransaction.pGTransaction.createdDate), DateUtil.VIEW_DATE_TIME_FORMAT));
    transactionResp.setUpdated_date(DateUtil.toDateStringFormat(
        tuple.get(QPGTransaction.pGTransaction.updatedDate), DateUtil.VIEW_DATE_TIME_FORMAT));
    transactionResp
        .setTransaction_type(tuple.get(QPGTransaction.pGTransaction.transactionType));
    transactionResp.setMerchant_code((tuple.get(QPGTransaction.pGTransaction.merchantId)));
    transactionResp.setMaskCardNumber(StringUtils.lastFourDigits(tuple.get(QPGTransaction.pGTransaction.panMasked)));
    transactionResp.setProcTxnCode(tuple.get(QPGTransaction.pGTransaction.procCode) != null
        ? tuple.get(QPGTransaction.pGTransaction.procCode) : "");
    transactionResp.setRef_transaction_id(
        (StringUtils.isEmpty(getSearchTransactionsIssuerTxnRefNum(tuple)) ? 0L
                : Long.valueOf(getSearchTransactionsIssuerTxnRefNum(tuple))));
    transactionResp
        .setMerchantFeeAmount(tuple.get(QPGTransaction.pGTransaction.merchantFeeAmount));
    transactionResp.setTxnDescription(tuple.get(QPGTransaction.pGTransaction.txnDescription));
    transactionResp.setMerchantSettlementStatus(
        tuple.get(QPGTransaction.pGTransaction.merchantSettlementStatus));
    transactionResp
        .setTerminal_id(Long.valueOf(tuple.get(QPGTransaction.pGTransaction.terminalId)));
    transactionResp.setAccountNumber(tuple.get(QPGAccount.pGAccount.accountNum));
    transactionResp.setMerchantType(tuple.get(QPGAccount.pGAccount.entityType));
    transactionResp.setMerchantName(tuple.get(QPGMerchant.pGMerchant.firstName));
    transactionResp.setAcqChannel(tuple.get(QPGTransaction.pGTransaction.acqChannel));
    transactionResp.setTxn_ref_num(
        (StringUtils.isEmpty(getSearchTransactionsRefTransactionId(tuple))
                ? 0L
                : Long
                    .valueOf(getSearchTransactionsRefTransactionId(tuple))));
    String posEntryMode = tuple.get(QPGTransaction.pGTransaction.posEntryMode);
    return posEntryMode;
  }

  private String getSearchTransactionsRefTransactionId(Tuple tuple) {
    return tuple.get(QPGTransaction.pGTransaction.refTransactionId) != null
        ? tuple.get(QPGTransaction.pGTransaction.refTransactionId) : "";
  }

  private String getSearchTransactionsIssuerTxnRefNum(Tuple tuple) {
    return tuple.get(QPGTransaction.pGTransaction.issuerTxnRefNum) != null
        ? tuple.get(QPGTransaction.pGTransaction.issuerTxnRefNum) : "";
  }

  private void getSearchTransactionsStatus(List<Transaction> transactions,
      Transaction transactionResp, Tuple tuple) {
    if (0 == tuple.get(QPGTransaction.pGTransaction.status)) {
      transactionResp.setStatusMessage("Approved");
    } else if (1 == tuple.get(QPGTransaction.pGTransaction.status)) {
      transactionResp.setStatusMessage("Declined");
    } else {
      transactionResp.setStatusMessage("Failed");
    }
    transactionResp.setAuth_id(tuple.get(QPGTransaction.pGTransaction.authId));
    transactionResp.setPayment_method(tuple.get(QPGTransaction.pGTransaction.paymentMethod));
    transactionResp.setAdj_amount((tuple.get(QPGTransaction.pGTransaction.adjAmount) != null)
        ? tuple.get(QPGTransaction.pGTransaction.adjAmount).doubleValue() : null);
    transactionResp
        .setTxn_total_amount((tuple.get(QPGTransaction.pGTransaction.txnTotalAmount) != null)
            ? tuple.get(QPGTransaction.pGTransaction.txnTotalAmount).doubleValue() : null);
    transactionResp.setAcqTxnMode(tuple.get(QPGTransaction.pGTransaction.acqTxnMode));
    transactionResp.setInvoice_number(tuple.get(QPGTransaction.pGTransaction.invoiceNumber));
    transactionResp.setProcessor(tuple.get(QPGTransaction.pGTransaction.processor));
    transactionResp.setTxnMode(tuple.get(QPGTransaction.pGTransaction.txnMode));
    transactions.add(transactionResp);
  }
  
  @Override
  public List<PGTransaction> getAllTransactionsOnMerchantCode(String merchantCode) {
    return transactionRepository.getAllTransactionsOnMerchantCode(merchantCode);
  }
}
