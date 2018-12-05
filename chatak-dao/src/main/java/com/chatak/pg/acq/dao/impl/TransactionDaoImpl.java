package com.chatak.pg.acq.dao.impl;

import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import com.chatak.pg.acq.dao.TransactionDao;
import com.chatak.pg.acq.dao.model.PGAccountTransactions;
import com.chatak.pg.acq.dao.model.PGTransaction;
import com.chatak.pg.acq.dao.model.QCardProgram;
import com.chatak.pg.acq.dao.model.QPGAccount;
import com.chatak.pg.acq.dao.model.QPGAccountTransactions;
import com.chatak.pg.acq.dao.model.QPGBankCurrencyMapping;
import com.chatak.pg.acq.dao.model.QPGCurrencyConfig;
import com.chatak.pg.acq.dao.model.QPGMerchant;
import com.chatak.pg.acq.dao.model.QPGMerchantCardProgramMap;
import com.chatak.pg.acq.dao.model.QPGOnlineTxnLog;
import com.chatak.pg.acq.dao.model.QPGTransaction;
import com.chatak.pg.acq.dao.repository.AccountTransactionsRepository;
import com.chatak.pg.acq.dao.repository.TransactionRepository;
import com.chatak.pg.bean.settlement.SettlementEntity;
import com.chatak.pg.constants.PGConstants;
import com.chatak.pg.dao.util.StringUtil;
import com.chatak.pg.enums.EntryModeEnum;
import com.chatak.pg.enums.EntryModePortalDisplayEnum;
import com.chatak.pg.enums.OriginalChannelEnum;
import com.chatak.pg.model.TransactionRequest;
import com.chatak.pg.user.bean.CardProgramRequest;
import com.chatak.pg.user.bean.GetTransactionsListRequest;
import com.chatak.pg.user.bean.Transaction;
import com.chatak.pg.util.CommonUtil;
import com.chatak.pg.util.Constants;
import com.chatak.pg.util.DateUtil;
import com.chatak.pg.util.DateUtils;
import com.chatak.pg.util.StringUtils;
import com.mysema.query.Tuple;
import com.mysema.query.jpa.impl.JPAQuery;
import com.mysema.query.types.OrderSpecifier;
import com.mysema.query.types.expr.BooleanExpression;

/**
 * Dao class to perform transaction record related insert, update and search
 * operation
 */
@Repository("transactionDao")
public class TransactionDaoImpl implements TransactionDao {

  private static Logger log = Logger.getLogger(TransactionDaoImpl.class);

  @Autowired
  private TransactionRepository transactionRepository;

  @PersistenceContext
  private EntityManager entityManager;
  
  @Autowired
  private AccountTransactionsRepository accountTransactionsRepository;

  /**
   * Method to search transaction record on reference transaction number
   * 
   * @param merchantId
   * @param terminalId
   * @param refTxnId
   * @return Transaction record
   * @throws DataAccessException
   */
  @Override
  public PGTransaction getTransactionOnRefNumber(String merchantId, String terminalId,
      String refTxnId) {
    log.debug("TransactionDaoImpl | getTransactionOnRefNumber | Entering");
    PGTransaction transaction = null;
    List<PGTransaction> pgTxnlist = transactionRepository
        .findByMerchantIdAndTerminalIdAndRefTransactionId(merchantId, terminalId, refTxnId);
    transaction = getPGTransaction(pgTxnlist);
    log.debug("TransactionDaoImpl | getTransactionOnRefNumber | Exiting");
    return transaction;
  }

  /**
   * Method to search transaction record on transaction number
   * 
   * @param merchantId
   * @param terminalId
   * @param transactionrefNum
   *            /RRN
   * @return Transaction record
   * @throws DataAccessException
   */
  @Override
  public PGTransaction getTransaction(String merchantId, String terminalId, String txnId) {
    log.debug("TransactionDaoImpl | getTransaction | Entering");
    PGTransaction transaction = null;
    List<PGTransaction> pgTxnlist =
        transactionRepository.findByMerchantIdAndId(merchantId, new BigInteger(txnId));
    transaction = getPGTransaction(pgTxnlist);
    log.debug("TransactionDaoImpl | getTransaction | Exiting");
    return transaction;
  }

  /**
   * @param merchantId
   * @param terminalId
   * @param txnId
   * @param authId
   * @param txnType
   * @return Transaction record
   * @throws DataAccessException
   */
  @Override
  public PGTransaction getTransaction(String merchantId, String terminalId, String txnId,
      String authId, String txnType) {
    log.debug("TransactionDaoImpl | getTransaction | Entering");
    PGTransaction transaction = null;
    List<PGTransaction> pgTxnlist = transactionRepository
        .findByMerchantIdAndTerminalIdAndTransactionIdAndTransactionTypeAndAuthId(merchantId,
            terminalId, txnId, txnType, authId);
    transaction = getPGTransaction(pgTxnlist);
    log.debug("TransactionDaoImpl | getTransaction | Exiting");
    return transaction;
  }

  /**
   * Method to search transaction record on Invoice number
   * 
   * @param merchantId
   * @param terminalId
   * @param inVoiceNum
   * @return Transaction record
   * @throws DataAccessException
   */
  public PGTransaction getTransactionOnInvoiceNum(String merchantId, String terminalId,
      String inVoiceNum) {
    log.debug("TransactionDaoImpl | getTransactionOnInvoiceNum | Entering");
    PGTransaction transaction = null;
    List<PGTransaction> pgTxnlist = transactionRepository
        .findByMerchantIdAndTerminalIdAndInvoiceNumber(merchantId, terminalId, inVoiceNum);
    transaction = getPGTransaction(pgTxnlist);
    log.debug("TransactionDaoImpl | getTransactionOnInvoiceNum | Exiting");
    return transaction;
  }

  /**
   * Method to get all transactions
   * 
   * @return
   * @throws DataAccessException
   */
  public List<PGTransaction> getAllTransactions() {
    return transactionRepository.findAll();
  }

  /**
   * Method to get all transactions on search criteria
   * 
   * @return
   * @throws DataAccessException
   */
  @Override
  public List<PGTransaction> getTransactionList(
      GetTransactionsListRequest getTransactionsListRequest) {
    log.info("TransactionDaoImpl | getTransactionList | Entering");
    List<PGTransaction> transactions = null;
    try {
      Integer totalRecords = getTransactionsListRequest.getNoOfRecords();
      int offset = 0;
      int limit = 0;
      if (getTransactionsListRequest.getPageIndex() == null
          || getTransactionsListRequest.getPageIndex().intValue() == 1) {
        totalRecords = getTotalNumberOfRecordsOnSearch(getTransactionsListRequest);
        getTransactionsListRequest.setNoOfRecords(totalRecords);
      }
      getTransactionsListRequest.setNoOfRecords(totalRecords);
      if (getTransactionsListRequest.getPageSize() == null
          || getTransactionsListRequest.getPageIndex() == null) {
        offset = 0;
        limit = Constants.DEFAULT_PAGE_SIZE;
      } else {
        offset = (getTransactionsListRequest.getPageIndex() - 1)
            * getTransactionsListRequest.getPageSize();
        limit = getTransactionsListRequest.getPageSize();
      }
      Timestamp endDate = null;
      Timestamp startDate = null;
      if (!CommonUtil.isNullAndEmpty(getTransactionsListRequest.getFrom_date())) {
        startDate = DateUtil.getStartDayTimestamp(getTransactionsListRequest.getFrom_date(),
            PGConstants.DD_MM_YYYY);
      }
      if (!CommonUtil.isNullAndEmpty(getTransactionsListRequest.getTo_date())) {
        endDate = DateUtil.getEndDayTimestamp(getTransactionsListRequest.getTo_date(),
            PGConstants.DD_MM_YYYY);
      }
      JPAQuery query = new JPAQuery(entityManager);
      List<Tuple> tupleList = query.from(QPGTransaction.pGTransaction, QPGMerchant.pGMerchant)
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
              isMerchantNameLike(getTransactionsListRequest.getMerchantName()),
              isValidDate(startDate, endDate))
          .offset(offset).limit(limit).orderBy(orderByCreatedDateDesc())
          .list(QPGTransaction.pGTransaction.merchantId, QPGTransaction.pGTransaction.id,
              QPGTransaction.pGTransaction.issuerTxnRefNum, QPGTransaction.pGTransaction.procCode,
              QPGTransaction.pGTransaction.panMasked, QPGTransaction.pGTransaction.createdDate,
              QPGTransaction.pGTransaction.transactionType, QPGTransaction.pGTransaction.txnAmount,
              QPGTransaction.pGTransaction.feeAmount,
              QPGTransaction.pGTransaction.merchantFeeAmount,
              QPGTransaction.pGTransaction.txnDescription, QPGTransaction.pGTransaction.status,
              QPGTransaction.pGTransaction.merchantSettlementStatus,
              QPGTransaction.pGTransaction.terminalId, QPGTransaction.pGTransaction.feeAmount,
              QPGTransaction.pGTransaction.posEntryMode, QPGTransaction.pGTransaction.acqChannel,
              QPGTransaction.pGTransaction.cardHolderName, QPGTransaction.pGTransaction.updatedDate,
              QPGTransaction.pGTransaction.authId, QPGTransaction.pGTransaction.invoiceNumber,
              QPGTransaction.pGTransaction.acqTxnMode, QPGTransaction.pGTransaction.txnTotalAmount,
              QPGTransaction.pGTransaction.processor, QPGTransaction.pGTransaction.txnMode,
              QPGTransaction.pGTransaction.refTransactionId);
      if (!CollectionUtils.isEmpty(tupleList)) {
        transactions = getTransactionsExisting(tupleList);
      }
    } catch (Exception e) {
      log.error("TransactionDaoImpl | getMerchantlist | Exception ", e);
    }
    log.info("TransactionDaoImpl | getTransactionList | Exiting");
    return transactions;
  }

  private List<PGTransaction> getTransactionsExisting(List<Tuple> tupleList) {
    List<PGTransaction> transactions;
    transactions = new ArrayList<>();
    PGTransaction pgTransaction = null;
    for (Tuple tuple : tupleList) {
      pgTransaction = new PGTransaction();
      pgTransaction.setTransactionId(tuple.get(QPGTransaction.pGTransaction.transactionId));
      pgTransaction
          .setIssuerTxnRefNum(tuple.get(QPGTransaction.pGTransaction.issuerTxnRefNum) != null
              ? tuple.get(QPGTransaction.pGTransaction.issuerTxnRefNum) : "");
      pgTransaction.setCreatedDate(tuple.get(QPGTransaction.pGTransaction.createdDate));
      pgTransaction.setProcCode(tuple.get(QPGTransaction.pGTransaction.procCode) != null
          ? tuple.get(QPGTransaction.pGTransaction.procCode) : "");
      pgTransaction.setPanMasked(StringUtils.lastFourDigits(tuple.get(QPGTransaction.pGTransaction.panMasked)));
      pgTransaction.setTerminalId(tuple.get(QPGTransaction.pGTransaction.terminalId));
      pgTransaction.setMerchantId(tuple.get(QPGTransaction.pGTransaction.merchantId));
      pgTransaction.setTransactionType(tuple.get(QPGTransaction.pGTransaction.transactionType));
      pgTransaction.setTxnAmount(tuple.get(QPGTransaction.pGTransaction.txnAmount));
      pgTransaction.setFeeAmount(tuple.get(QPGTransaction.pGTransaction.feeAmount));
      pgTransaction.setStatus(tuple.get(QPGTransaction.pGTransaction.status));
      pgTransaction
          .setMerchantFeeAmount(tuple.get(QPGTransaction.pGTransaction.merchantFeeAmount));
      pgTransaction.setTxnDescription(tuple.get(QPGTransaction.pGTransaction.txnDescription));
      pgTransaction.setMerchantSettlementStatus(
          tuple.get(QPGTransaction.pGTransaction.merchantSettlementStatus));
      pgTransaction.setFeeAmount(tuple.get(QPGTransaction.pGTransaction.feeAmount));
      pgTransaction.setAcqChannel(tuple.get(QPGTransaction.pGTransaction.acqChannel));
      pgTransaction.setPosEntryMode(tuple.get(QPGTransaction.pGTransaction.posEntryMode));
      pgTransaction.setCardHolderName(tuple.get(QPGTransaction.pGTransaction.cardHolderName));
      pgTransaction.setUpdatedDate(tuple.get(QPGTransaction.pGTransaction.updatedDate));
      pgTransaction.setAuthId(tuple.get(QPGTransaction.pGTransaction.authId));
      pgTransaction.setTxnTotalAmount(tuple.get(QPGTransaction.pGTransaction.txnTotalAmount));
      pgTransaction.setAcqTxnMode(tuple.get(QPGTransaction.pGTransaction.acqTxnMode));
      pgTransaction.setInvoiceNumber(tuple.get(QPGTransaction.pGTransaction.invoiceNumber));
      pgTransaction.setProcessor(tuple.get(QPGTransaction.pGTransaction.processor));
      pgTransaction.setTxnMode(tuple.get(QPGTransaction.pGTransaction.txnMode));
      pgTransaction
          .setRefTransactionId(tuple.get(QPGTransaction.pGTransaction.refTransactionId));

      transactions.add(pgTransaction);
    }
    return transactions;
  }

  protected Integer getTotalNumberOfRecordsOnSearch(
      GetTransactionsListRequest getTransactionsListRequest) {
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
    JPAQuery query = new JPAQuery(entityManager);
    List<Tuple> list =
        query
            .from(QPGTransaction.pGTransaction, QPGMerchant.pGMerchant, QPGAccount.pGAccount,
                QPGBankCurrencyMapping.pGBankCurrencyMapping, QPGCurrencyConfig.pGCurrencyConfig)
            .where(isMerchantId(getTransactionsListRequest.getMerchant_code()),
                isTxnId(getTransactionsListRequest.getTransactionId()),
                isProcessTxnId(getTransactionsListRequest.getProcessCode()),
                isCardNumberLike(getTransactionsListRequest.getCardNumber()),
                isTxnTypeLike(getTransactionsListRequest.getTransaction_type()),
                isTxnStatusLike(getTransactionsListRequest.getStatus()),
                isEntryModeLike(getTransactionsListRequest.getEntryMode()),
                isAcqChannel(getTransactionsListRequest.getAcqChannel()), isCardHolderNameLike(
                    getTransactionsListRequest.getCardHolderName()),
            isMerchantSettlementStatus(getTransactionsListRequest.getMerchantSettlementStatus()),
            isMerchantBusinessNameLike(getTransactionsListRequest.getMerchantBusinessName()),
            isValidTxn(getTransactionsListRequest.getFromAmtRange(),
                getTransactionsListRequest.getToAmtRange()),
            QPGTransaction.pGTransaction.merchantId.eq(QPGMerchant.pGMerchant.merchantCode),
            QPGTransaction.pGTransaction.merchantId.eq(QPGAccount.pGAccount.entityId),
            QPGAccount.pGAccount.status.eq(Constants.ACTIVE)
                .and(QPGAccount.pGAccount.category.eq(PGConstants.PRIMARY_ACCOUNT)),
            QPGMerchant.pGMerchant.bankId.eq(QPGBankCurrencyMapping.pGBankCurrencyMapping.bankId),
            QPGBankCurrencyMapping.pGBankCurrencyMapping.currencyCodeAlpha
                .eq(QPGCurrencyConfig.pGCurrencyConfig.currencyCodeAlpha),
            isMerchantNameLike(getTransactionsListRequest.getMerchantName()),
            isValidDate(startDate, endDate)).orderBy(orderByCreatedDateDesc())
        .list(QPGTransaction.pGTransaction.merchantId, QPGTransaction.pGTransaction.id,
            QPGTransaction.pGTransaction.issuerTxnRefNum, QPGTransaction.pGTransaction.procCode,
            QPGTransaction.pGTransaction.panMasked, QPGTransaction.pGTransaction.createdDate,
            QPGTransaction.pGTransaction.transactionType, QPGTransaction.pGTransaction.txnAmount,
            QPGTransaction.pGTransaction.feeAmount, QPGTransaction.pGTransaction.merchantFeeAmount,
            QPGTransaction.pGTransaction.txnDescription, QPGTransaction.pGTransaction.status,
            QPGTransaction.pGTransaction.merchantSettlementStatus,
            QPGTransaction.pGTransaction.terminalId, QPGTransaction.pGTransaction.posEntryMode,
            QPGTransaction.pGTransaction.acqChannel, QPGTransaction.pGTransaction.cardHolderName,
            QPGTransaction.pGTransaction.updatedDate, QPGTransaction.pGTransaction.authId,
            QPGTransaction.pGTransaction.txnTotalAmount, QPGTransaction.pGTransaction.acqTxnMode,
            QPGTransaction.pGTransaction.invoiceNumber, QPGTransaction.pGTransaction.processor,
            QPGTransaction.pGTransaction.txnMode, QPGMerchant.pGMerchant.firstName,
            QPGMerchant.pGMerchant.businessName, QPGMerchant.pGMerchant.merchantType,
            QPGAccount.pGAccount.accountNum, QPGAccount.pGAccount.entityType,
            QPGTransaction.pGTransaction.refTransactionId,
            QPGCurrencyConfig.pGCurrencyConfig.currencyCodeAlpha,
            QPGBankCurrencyMapping.pGBankCurrencyMapping.currencyCodeAlpha,
            QPGTransaction.pGTransaction.deviceLocalTxnTime,
            QPGTransaction.pGTransaction.timeZoneOffset);

    return (StringUtils.isListNotNullNEmpty(list) ? list.size() : 0);
  }
 
  protected BooleanExpression toGetCurrentDayTxns(boolean toGetCurrentDayTxns) {
    return (toGetCurrentDayTxns) ? QPGTransaction.pGTransaction.createdDate.stringValue()
        .like(DateUtils.getCurrentDateInDDMMYY() + "%") : null;
  }

  protected BooleanExpression isValidDate(Timestamp fromDate, Timestamp toDate) {
    if ((fromDate != null && toDate == null)) {
      return QPGTransaction.pGTransaction.createdDate.gt(fromDate);
    } else if ((fromDate == null && toDate != null)) {
      return QPGTransaction.pGTransaction.createdDate.lt(toDate);
    } else if ((fromDate == null))
      return null;
    return QPGTransaction.pGTransaction.createdDate.between(fromDate, toDate);
  }

  protected BooleanExpression isValidTxn(String fromAmount, String toAmount) {
    if ((fromAmount == null && toAmount == null) || ("".equals(fromAmount) && "".equals(toAmount)))
      return null;
    else if ((fromAmount != null && (toAmount == null || "".equals(toAmount)))) {
      Long startAmount = Long.valueOf(fromAmount) * Integer.parseInt("100");
      return QPGTransaction.pGTransaction.txnAmount.gt((startAmount));

    } else if ((fromAmount == null || "".equals(fromAmount))) {
      Long endAmount = Long.valueOf(toAmount) * Integer.parseInt("100");
      return QPGTransaction.pGTransaction.txnAmount.lt(endAmount);
    }
    Long startAmount = Long.valueOf(fromAmount) * Integer.parseInt("100");
    Long endAmount = Long.valueOf(toAmount) * Integer.parseInt("100");
    return QPGTransaction.pGTransaction.txnAmount.between(startAmount, endAmount);
  }

  protected BooleanExpression isTxnStatusLike(Integer status) {
    return status != null ? QPGTransaction.pGTransaction.status.eq(status)
        : null;
  }

  protected BooleanExpression isTxnTypeLike(String transactionType) {
    return (transactionType != null && !"".equals(transactionType))
        ? (QPGTransaction.pGTransaction.transactionType).toUpperCase()
            .eq(transactionType.toUpperCase())
        : null;
  }

  protected BooleanExpression isCardNumberLike(String cardNumber) {
    return (cardNumber != null && !"".equals(cardNumber))
        ? QPGTransaction.pGTransaction.panMasked.like("%" + cardNumber) : null;
  }

  protected BooleanExpression isProcessTxnId(String processCode) {
    return (processCode != null && !"".equals(processCode))
        ? QPGTransaction.pGTransaction.issuerTxnRefNum.eq((processCode)) : null;
  }

  protected BooleanExpression isTxnId(String transactionId) {
    return (transactionId != null && !"".equals(transactionId))
        ? QPGTransaction.pGTransaction.id.eq(new BigInteger(transactionId)) : null;
  }

  protected BooleanExpression isAcqChannel(String acqChannel) {
    return (acqChannel != null && !"".equals(acqChannel))
        ? QPGTransaction.pGTransaction.acqChannel.eq(acqChannel) : null;
  }

  protected BooleanExpression isCardHolderNameLike(String cardHolderName) {
    return (cardHolderName != null && !"".equals(cardHolderName))
        ? QPGTransaction.pGTransaction.cardHolderName.eq(cardHolderName) : null;
  }

  protected BooleanExpression isMerchantId(String merchantCode) {
    return (merchantCode != null && !"".equals(merchantCode))
        ? QPGTransaction.pGTransaction.merchantId.eq(merchantCode) : null;
  }

  protected OrderSpecifier<Timestamp> orderByCreatedDateDesc() {
    return QPGTransaction.pGTransaction.createdDate.desc();
  }

  /**
   * @param merchantId
   * @param terminalId
   * @param txnId
   * @param authId
   * @param txnType
   * @return
   * @throws DataAccessException
   */
  @Override
  public PGTransaction getTransactionOnTxnIdAndTxnType(String merchantId, String terminalId,
      String txnId, String txnType) {
    log.debug("TransactionDaoImpl | getTransactionOnTxnIdAndTxnType | Entering");
    PGTransaction transaction = null;
    List<PGTransaction> pgTxnlist =
        transactionRepository.findByMerchantIdAndTerminalIdAndIdAndTransactionType(
            merchantId, terminalId, new BigInteger(txnId), txnType);
            

    transaction = getPGTransaction(pgTxnlist);
    log.debug("TransactionDaoImpl | getTransactionOnTxnIdAndTxnType | Exiting");
    return transaction;
  }

  protected BooleanExpression isEntryModeLike(String entryMode) {
    return (entryMode != null && !"".equals(entryMode))
        ? QPGTransaction.pGTransaction.posEntryMode.like(entryMode + "%") : null;
  }

  protected BooleanExpression isMerchantNameLike(String merchantName) {
    return (merchantName != null && !"".equals(merchantName)) ? QPGMerchant.pGMerchant.firstName
        .eq(merchantName).or(QPGMerchant.pGMerchant.lastName.eq(merchantName)) : null;
  }
  
  protected BooleanExpression isMerchantBusinessNameLike(String merchantBusinessName) {
		return (merchantBusinessName != null && !"".equals(merchantBusinessName)) ? QPGMerchant.pGMerchant.businessName
				.eq(merchantBusinessName).or(
						QPGMerchant.pGMerchant.businessName.eq(merchantBusinessName))
				: null;
	}

  protected BooleanExpression isRevenueType(String revenueType) {
    if (revenueType != null && !"".equals(revenueType)) {
      if (revenueType.equals(OriginalChannelEnum.MERCHANT_WEB.value())
          || revenueType.equals(OriginalChannelEnum.ADMIN_WEB.value())) {
        return QPGTransaction.pGTransaction.acqChannel.eq(revenueType);
      } else {
        return QPGTransaction.pGTransaction.acqChannel
            .notIn(OriginalChannelEnum.MERCHANT_WEB.value(), OriginalChannelEnum.ADMIN_WEB.value());
      }
    } else {
      return null;
    }
  }

  @Override
  public List<Transaction> getTransactions(GetTransactionsListRequest getTransactionsListRequest, Long entityId) {
    log.info("TransactionDaoImpl | getTransactions | Entering");
    List<Transaction> transactions = null;
    try {
      int offset = 0;
      int limit = 0;
      Integer totalRecords = getTransactionsListRequest.getNoOfRecords();
      if (getTransactionsListRequest.getPageIndex().intValue() == 1 
    		  || getTransactionsListRequest.getPageIndex() == null) {
        totalRecords = getTotalNumberOfTxnRecordsForPM(getTransactionsListRequest, entityId);
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
      Timestamp endDate = null;
      Timestamp startDate = null;
      if (!CommonUtil.isNullAndEmpty(getTransactionsListRequest.getTo_date())) {
          endDate = DateUtil.getEndDayTimestamp(getTransactionsListRequest.getTo_date(),
              PGConstants.DD_MM_YYYY);
        }
      if (!CommonUtil.isNullAndEmpty(getTransactionsListRequest.getFrom_date())) {
        startDate = DateUtil.getStartDayTimestamp(getTransactionsListRequest.getFrom_date(),
            PGConstants.DD_MM_YYYY);
      }
      JPAQuery query = new JPAQuery(entityManager);
      List<Tuple> tupleList = query
          .from(QPGTransaction.pGTransaction, QPGMerchant.pGMerchant, QPGAccount.pGAccount,
              QPGBankCurrencyMapping.pGBankCurrencyMapping, QPGCurrencyConfig.pGCurrencyConfig)
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
              isMerchantSettlementStatus(getTransactionsListRequest.getMerchantSettlementStatus()),
              QPGTransaction.pGTransaction.merchantId.eq(QPGMerchant.pGMerchant.merchantCode),
              QPGTransaction.pGTransaction.merchantId.eq(QPGAccount.pGAccount.entityId),
              QPGAccount.pGAccount.status.eq(Constants.ACTIVE)
                  .and(QPGAccount.pGAccount.category.eq(PGConstants.PRIMARY_ACCOUNT)),
              QPGMerchant.pGMerchant.bankId.eq(QPGBankCurrencyMapping.pGBankCurrencyMapping.bankId),
              QPGBankCurrencyMapping.pGBankCurrencyMapping.currencyCodeAlpha
                  .eq(QPGCurrencyConfig.pGCurrencyConfig.currencyCodeAlpha),
                  isMerchantBusinessNameLike(getTransactionsListRequest.getMerchantBusinessName()),
              isValidDate(startDate, endDate), isEntityIdLike(entityId))
          .offset(offset).limit(limit).orderBy(orderByCreatedDateDesc())
          .list(QPGTransaction.pGTransaction.merchantId, QPGTransaction.pGTransaction.id,
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
              QPGMerchant.pGMerchant.businessName, QPGMerchant.pGMerchant.merchantType,
              QPGAccount.pGAccount.accountNum, QPGAccount.pGAccount.entityType,
              QPGTransaction.pGTransaction.refTransactionId,
              QPGTransaction.pGTransaction.batchId,
              QPGCurrencyConfig.pGCurrencyConfig.currencyCodeAlpha,
              QPGBankCurrencyMapping.pGBankCurrencyMapping.currencyCodeAlpha,
              QPGMerchant.pGMerchant.localCurrency, QPGTransaction.pGTransaction.userName,
              QPGTransaction.pGTransaction.deviceLocalTxnTime,QPGTransaction.pGTransaction.timeZoneOffset);
      if (!CollectionUtils.isEmpty(tupleList)) {
        transactions = new ArrayList<>();
        Transaction transactionResp = null;
        for (Tuple tuple : tupleList) {
          transactionResp = new Transaction();
          String posEntryMode = getTransactionstTupleList(transactionResp, tuple);
          log.info("TransactionDaoImpl | getTransactions :: posEntryMode for enum: "
              + getEntryModeEnumFromPosEntryMode(posEntryMode));
          transactionResp.setEntryMode(EntryModePortalDisplayEnum
              .valueOf(getEntryModeEnumFromPosEntryMode(posEntryMode)).value());
          log.info("TransactionDaoImpl | getTransactions :: set posEntryMode");
          getTransactionsStatus(transactionResp, tuple);
          
          transactions.add(transactionResp);
        }
      }
    } catch (Exception e) {
      log.error("TransactionDaoImpl | getTransactions | Exception " + e);
    }
    log.info("TransactionDaoImpl | getTransactions | Exiting");
    return transactions;
  }

  protected String getTransactionstTupleList(Transaction transactionResp, Tuple tuple) {
    transactionResp.setTransactionId(tuple.get(QPGTransaction.pGTransaction.id).toString());
    transactionResp.setTransactionAmount((StringUtils.amountToString(tuple.get(QPGTransaction.pGTransaction.txnAmount))));
    transactionResp.setTransactionDate(DateUtil.toDateStringFormat(
        tuple.get(QPGTransaction.pGTransaction.createdDate), PGConstants.DATE_FORMAT));
    transactionResp.setUpdated_date(DateUtil.toDateStringFormat(
        tuple.get(QPGTransaction.pGTransaction.updatedDate), PGConstants.DATE_FORMAT));
    transactionResp
        .setTransaction_type(tuple.get(QPGTransaction.pGTransaction.transactionType));
    transactionResp.setMerchant_code((tuple.get(QPGTransaction.pGTransaction.merchantId)));
    transactionResp.setMaskCardNumber(StringUtils.lastFourDigits(tuple.get(QPGTransaction.pGTransaction.panMasked)));
    transactionResp.setProcTxnCode(tuple.get(QPGTransaction.pGTransaction.procCode) != null
        ? tuple.get(QPGTransaction.pGTransaction.procCode) : "");
    transactionResp.setRef_transaction_id(
        getTransactionsIssuerTxnRefNum(tuple));
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
        getTransactionsRefTransactionId(tuple));
    transactionResp.setUserName(tuple.get(QPGTransaction.pGTransaction.userName));
    String posEntryMode = tuple.get(QPGTransaction.pGTransaction.posEntryMode);
    log.info("TransactionDaoImpl | getTransactions :: posEntryMode: " + posEntryMode);
    if (null == posEntryMode) {
      posEntryMode = "00";
    }
    return posEntryMode;
  }

  private long getTransactionsRefTransactionId(Tuple tuple) {
    return StringUtils.isEmpty(tupleRefTransactionId(tuple))
            ? 0L
            : Long
                .valueOf(tupleRefTransactionId(tuple));
  }

  private String tupleRefTransactionId(Tuple tuple) {
    return tuple.get(QPGTransaction.pGTransaction.refTransactionId) != null
        ? tuple.get(QPGTransaction.pGTransaction.refTransactionId) : "";
  }

  private long getTransactionsIssuerTxnRefNum(Tuple tuple) {
    return StringUtils.isEmpty(tupleIssuerTxnRefNum(tuple))
            ? 0L
            : Long.valueOf(tupleIssuerTxnRefNum(tuple));
  }

  private String tupleIssuerTxnRefNum(Tuple tuple) {
    return tuple.get(QPGTransaction.pGTransaction.issuerTxnRefNum) != null
        ? tuple.get(QPGTransaction.pGTransaction.issuerTxnRefNum) : "";
  }

  protected void getTransactionsStatus(Transaction transactionResp, Tuple tuple) {
    if (0 == tuple.get(QPGTransaction.pGTransaction.status)) {
      transactionResp.setStatusMessage("Approved");
    } else if (1 == tuple.get(QPGTransaction.pGTransaction.status)) {
      transactionResp.setStatusMessage("Declined");
    } else {
      transactionResp.setStatusMessage("Failed");
    }
    transactionResp
        .setFeeString(tuple.get(QPGCurrencyConfig.pGCurrencyConfig.currencyCodeAlpha) + " "
            + StringUtils.amountToString(tuple.get(QPGTransaction.pGTransaction.feeAmount)));
    transactionResp.setAuth_id(tuple.get(QPGTransaction.pGTransaction.authId));
    transactionResp.setTxn_total_amount(tuple.get(QPGTransaction.pGTransaction.txnTotalAmount).doubleValue()/Integer.parseInt("100"));
    transactionResp.setAcqTxnMode(tuple.get(QPGTransaction.pGTransaction.acqTxnMode));
    transactionResp.setInvoice_number(tuple.get(QPGTransaction.pGTransaction.invoiceNumber));
    transactionResp.setProcessor(tuple.get(QPGTransaction.pGTransaction.processor));
    transactionResp.setTxnMode(tuple.get(QPGTransaction.pGTransaction.txnMode));
    transactionResp.setCardHolderName(tuple.get(QPGTransaction.pGTransaction.cardHolderName));
    transactionResp.setMerchantBusinessName(tuple.get(QPGMerchant.pGMerchant.businessName));
    transactionResp.setMerchantType(tuple.get(QPGMerchant.pGMerchant.merchantType));
    transactionResp.setLocalCurrency(tuple.get(QPGMerchant.pGMerchant.localCurrency));
    transactionResp.setBatchId(tuple.get(QPGTransaction.pGTransaction.batchId));
    transactionResp.setTransactionAmount((StringUtils.amountToString(tuple.get(QPGTransaction.pGTransaction.txnAmount))));
    transactionResp.setMerchantFeeAmount(tuple.get(QPGTransaction.pGTransaction.merchantFeeAmount));
    if(!transactionResp.getAcqTxnMode().equals("ACCOUNT_PAY")) {
    transactionResp.setFee_amount(tuple.get(QPGTransaction.pGTransaction.feeAmount).doubleValue()/Integer.parseInt("100"));
    }
    transactionResp.setTerminal_id(Long.valueOf((tuple.get(QPGTransaction.pGTransaction.terminalId))));
    transactionResp.setUserName(tuple.get(QPGTransaction.pGTransaction.userName));
    transactionResp.setDeviceLocalTxnTime(tuple.get(QPGTransaction.pGTransaction.deviceLocalTxnTime));
    transactionResp.setTimeZoneOffset(tuple.get(QPGTransaction.pGTransaction.timeZoneOffset));
  }

  public String getEntryModeEnumFromPosEntryMode(String posEntryMode) {
    log.info("Entering :: TransactionDaoImpl :: getEntryModeEnumFromPosEntryMode Method");
    try {
      return EntryModeEnum.fromValue(posEntryMode.substring(0, Integer.parseInt("2"))).name();
    } catch (Exception e) {
      log.error("Error :: TransactionDaoImpl :: getEntryModeEnumFromPosEntryMode Method", e);
      return EntryModeEnum.UNSPECIFIED.name();
    }
  }

  protected BooleanExpression isMerchantIdorParentMerchantId(List<String> merchantCodeList) {
    return QPGTransaction.pGTransaction.merchantId.in(merchantCodeList);
  }

  public int getTotalNumberOfPendingRecords(GetTransactionsListRequest getTransactionsListRequest) {
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
    JPAQuery query = new JPAQuery(entityManager);
    List<Tuple> infoList = query.distinct()
        .from(QPGTransaction.pGTransaction, QPGMerchant.pGMerchant, QPGAccount.pGAccount,
            QPGOnlineTxnLog.pGOnlineTxnLog, QPGBankCurrencyMapping.pGBankCurrencyMapping,
            QPGCurrencyConfig.pGCurrencyConfig)
        .where(
            QPGMerchant.pGMerchant.merchantCode
                .eq(QPGTransaction.pGTransaction.merchantId.stringValue())
                .and(QPGTransaction.pGTransaction.transactionId
                    .eq(QPGOnlineTxnLog.pGOnlineTxnLog.pgTxnId.stringValue()))
                .and(isValidDate(startDate, endDate))
                .and(QPGAccount.pGAccount.entityId.eq(QPGMerchant.pGMerchant.merchantCode)),
            QPGTransaction.pGTransaction.merchantSettlementStatus
                .eq(getTransactionsListRequest.getSettlementStatus()),
            QPGMerchant.pGMerchant.bankId.eq(QPGBankCurrencyMapping.pGBankCurrencyMapping.bankId),
            QPGBankCurrencyMapping.pGBankCurrencyMapping.currencyCodeAlpha
                .eq(QPGCurrencyConfig.pGCurrencyConfig.currencyCodeAlpha))
        .orderBy(orderByCreatedDateDesc()).list(QPGMerchant.pGMerchant.userName,
            QPGMerchant.pGMerchant.businessName, QPGMerchant.pGMerchant.merchantType,
            QPGAccount.pGAccount.accountNum, QPGAccount.pGAccount.entityType,
            QPGAccount.pGAccount.currency, QPGTransaction.pGTransaction.txnTotalAmount,
            QPGTransaction.pGTransaction.txnDescription, QPGTransaction.pGTransaction.paymentMethod,
            QPGTransaction.pGTransaction.transactionId, QPGTransaction.pGTransaction.createdDate,
            QPGTransaction.pGTransaction.transactionType, QPGTransaction.pGTransaction.authId,
            QPGTransaction.pGTransaction.refTransactionId, QPGTransaction.pGTransaction.terminalId,
            QPGTransaction.pGTransaction.invoiceNumber, QPGTransaction.pGTransaction.panMasked,
            QPGTransaction.pGTransaction.merchantId, QPGTransaction.pGTransaction.acqChannel,
            QPGTransaction.pGTransaction.acqTxnMode, QPGTransaction.pGTransaction.txnAmount,
            QPGTransaction.pGTransaction.feeAmount, QPGTransaction.pGTransaction.processor,
            QPGTransaction.pGTransaction.txnMode,
            QPGTransaction.pGTransaction.merchantSettlementStatus,
            QPGTransaction.pGTransaction.status, QPGTransaction.pGTransaction.cardHolderName,
            QPGCurrencyConfig.pGCurrencyConfig.currencyCodeAlpha,
            QPGBankCurrencyMapping.pGBankCurrencyMapping.currencyCodeAlpha,QPGTransaction.pGTransaction.deviceLocalTxnTime,
            QPGTransaction.pGTransaction.timeZoneOffset,QPGTransaction.pGTransaction.batchId);
    return (StringUtils.isListNotNullNEmpty(infoList) ? infoList.size() : 0);
  }

  protected PGTransaction getPGTransaction(List<PGTransaction> pgTxnlist) {
    PGTransaction transaction = null;
    if (pgTxnlist != null && pgTxnlist.size() > 0) {
      transaction = pgTxnlist.get(0);
    }
    return transaction;
  }

  protected BooleanExpression isMerchantSettlementStatus(String settlementStatus) {
    return (!StringUtil.isNullAndEmpty(settlementStatus))
        ? QPGTransaction.pGTransaction.merchantSettlementStatus.eq(settlementStatus) : null;
  }
  
	@Override
	public List<Long> fetchCardProgramDetailsByMerchantCode(TransactionRequest transactionData) {
		JPAQuery query = new JPAQuery(entityManager);
		CardProgramRequest cardProgramRequest = null;
		List<CardProgramRequest> CardProgramRequestList = new ArrayList<>();
		List<Tuple> tupleList = query.distinct()
				.from(QPGMerchant.pGMerchant, QPGMerchantCardProgramMap.pGMerchantCardProgramMap,
						QCardProgram.cardProgram)
				.where(QPGMerchant.pGMerchant.id.eq(QPGMerchantCardProgramMap.pGMerchantCardProgramMap.merchantId)
						.and(QPGMerchantCardProgramMap.pGMerchantCardProgramMap.cardProgramId
								.eq(QCardProgram.cardProgram.cardProgramId))
						.and(isMerchantCodeEq(transactionData.getMerchantCode())))
				.list(QCardProgram.cardProgram.iin, QCardProgram.cardProgram.iinExt,
						QCardProgram.cardProgram.partnerIINCode);
		if (!CollectionUtils.isEmpty(tupleList)) {
			for (Tuple tuple : tupleList) {
			  cardProgramRequest = new CardProgramRequest();
			  cardProgramRequest.setIin((tuple.get(QCardProgram.cardProgram.iin) != null)
		                ? (tuple.get(QCardProgram.cardProgram.iin)) : null);
		        cardProgramRequest.setIinExt(((tuple.get(QCardProgram.cardProgram.iinExt) != null)
		                ?  (tuple.get(QCardProgram.cardProgram.iinExt)) : null));
		        cardProgramRequest.setPartnerIINCode((tuple.get(QCardProgram.cardProgram.partnerIINCode) != null)
		                ? (tuple.get(QCardProgram.cardProgram.partnerIINCode)) : null);
		        CardProgramRequestList.add(cardProgramRequest);
			}
		}
		
		List<Long> cardNumberList = new ArrayList<>();
			for (CardProgramRequest card : CardProgramRequestList) {
				String cardNumber = cardInfo(card);
				cardNumberList.add(Long.valueOf(cardNumber));
			}
		
		return cardNumberList;
	}

	/**
	 * @param card
	 * @return
	 */
	private String cardInfo(CardProgramRequest card) {
		return ((card.getIin() != null) ? card.getIin() : "")
				+ ((card.getPartnerIINCode() != null) ? card.getPartnerIINCode() : "")
				+ ((card.getIinExt() != null) ? card.getIinExt() : "");
	}

	private BooleanExpression isMerchantCodeEq(String merchantCode) {
		return (!StringUtil.isNullAndEmpty(merchantCode)) ? QPGMerchant.pGMerchant.merchantCode.eq(merchantCode) : null;
	}

	/**
	 * @param transaction
	 * @return
	 */
	@Override
	public List<SettlementEntity> getPgTransactions(String merchantId, String terminalId,
		      String issuerTxnRefNum, String transactionId) {
	  log.info("TransactionDaoImpl | getPgTransactions :: Entry");
	  
		List<SettlementEntity> list = new ArrayList<>();
		JPAQuery query = new JPAQuery(entityManager);
		List<Tuple> tuples = query.from(QPGTransaction.pGTransaction,QPGAccountTransactions.pGAccountTransactions)
				.where(isTerminalIdEq(terminalId), isTransactionIdEq(transactionId),
						isIssuanceIdEq(issuerTxnRefNum), isMerchantIdEq(merchantId),
						isSettlementBatchStatus()
						.and(QPGAccountTransactions.pGAccountTransactions.pgTransactionId.eq(transactionId)))
				.list(QPGTransaction.pGTransaction.id, QPGTransaction.pGTransaction.txnTotalAmount,
						QPGTransaction.pGTransaction.terminalId, QPGTransaction.pGTransaction.issuerTxnRefNum,
						QPGTransaction.pGTransaction.createdDate, QPGTransaction.pGTransaction.updatedDate,
						QPGTransaction.pGTransaction.merchantId, QPGTransaction.pGTransaction.batchId,
						QPGTransaction.pGTransaction.deviceLocalTxnTime, QPGTransaction.pGTransaction.isoId,
						QPGTransaction.pGTransaction.pmId, QPGTransaction.pGTransaction.batchDate,
						QPGAccountTransactions.pGAccountTransactions.entityId,
						QPGAccountTransactions.pGAccountTransactions.entityType,
						QPGAccountTransactions.pGAccountTransactions.credit, QPGTransaction.pGTransaction.settlementBatchStatus,
						QPGAccountTransactions.pGAccountTransactions.debit, QPGAccountTransactions.pGAccountTransactions.transactionType);

		log.info("TransactionDaoImpl | getPgTransactions :: List: " + tuples.size());
		
		if (!CollectionUtils.isEmpty(tuples)) {
		  SettlementEntity  settlementEntity = new SettlementEntity();
		  
			for (Tuple tuple : tuples) {
				
			  log.info("TransactionDaoImpl | getPgTransactions :: Reading first row: " + tuple.get((QPGTransaction.pGTransaction.transactionId))
			      + ", Entity: " + tuple.get(QPGAccountTransactions.pGAccountTransactions.entityType));
			  
				settlementEntity.setPgTxnId(tuple.get((QPGTransaction.pGTransaction.id)).toString());
				settlementEntity.setAcquirerAmount(tuple.get(QPGTransaction.pGTransaction.txnTotalAmount)!=null ? 
				    tuple.get(QPGTransaction.pGTransaction.txnTotalAmount).toString() : "0");
				settlementEntity.setTerminalId(tuple.get(QPGTransaction.pGTransaction.terminalId));
				settlementEntity.setIssTxnId(tuple.get(QPGTransaction.pGTransaction.issuerTxnRefNum));
				settlementEntity.setCreatedDate(tuple.get(QPGTransaction.pGTransaction.createdDate));
				settlementEntity.setUpdatedDate(tuple.get(QPGTransaction.pGTransaction.updatedDate));
				settlementEntity.setMerchantId(tuple.get(QPGTransaction.pGTransaction.merchantId));
				settlementEntity.setBatchId(tuple.get(QPGTransaction.pGTransaction.batchId));
				settlementEntity.setDeviceLocalTxnTime(tuple.get(QPGTransaction.pGTransaction.deviceLocalTxnTime));
				settlementEntity.setAcqPmId(tuple.get(QPGTransaction.pGTransaction.pmId).toString());
				settlementEntity.setBatchDate(tuple.get(QPGTransaction.pGTransaction.batchDate));
				settlementEntity.setSettlementBatchStatus(tuple.get(QPGTransaction.pGTransaction.settlementBatchStatus));
				settlementEntity.setTransactionType(tuple.get(QPGAccountTransactions.pGAccountTransactions.transactionType));
				
				settlementEntityAmoutAndId(settlementEntity, tuple);
				
				if(isSaleWithPmEntityType(tuple)){
					settlementEntity.setPmAmount((tuple.get(QPGAccountTransactions.pGAccountTransactions.credit)));
				} else if (isRefundWithPmEntityType(tuple)) {
					settlementEntity.setPmAmount((tuple.get(QPGAccountTransactions.pGAccountTransactions.debit)));
				}
				
				if(isSaleWithEntityTypeMerchant(tuple)){
					settlementEntity.setMerchantAmount((tuple.get(QPGAccountTransactions.pGAccountTransactions.credit)));
				} else if (tuple.get(QPGAccountTransactions.pGAccountTransactions.entityType) != null
						&& tuple.get(QPGAccountTransactions.pGAccountTransactions.entityType)
								.equals(PGConstants.MERCHANT)
						&& tuple.get(QPGAccountTransactions.pGAccountTransactions.transactionType)
								.equalsIgnoreCase(Constants.REFUND)) {
					settlementEntity.setMerchantAmount((tuple.get(QPGAccountTransactions.pGAccountTransactions.debit)));
				}
								  
			}
			list.add(settlementEntity);
		}
		log.info("TransactionDaoImpl | getPgTransactions :: Returning records: " + list.size());
		log.info("TransactionDaoImpl | getPgTransactions :: Exit");
		return list;
	}

  private boolean isSaleWithEntityTypeMerchant(Tuple tuple) {
    return tuple.get(QPGAccountTransactions.pGAccountTransactions.entityType)!=null 
        && tuple.get(QPGAccountTransactions.pGAccountTransactions.entityType).equals(PGConstants.MERCHANT) 
        &&  tuple.get(QPGAccountTransactions.pGAccountTransactions.transactionType).equalsIgnoreCase(Constants.SALE);
  }

  private boolean isRefundWithPmEntityType(Tuple tuple) {
    return tuple.get(QPGAccountTransactions.pGAccountTransactions.entityType)!=null 
        && tuple.get(QPGAccountTransactions.pGAccountTransactions.entityType).equals(Constants.PM_USER_TYPE) && 
        tuple.get(QPGAccountTransactions.pGAccountTransactions.transactionType).equalsIgnoreCase(Constants.REFUND);
  }

  private boolean isSaleWithPmEntityType(Tuple tuple) {
    return tuple.get(QPGAccountTransactions.pGAccountTransactions.entityType)!=null 
        && tuple.get(QPGAccountTransactions.pGAccountTransactions.entityType).equals(Constants.PM_USER_TYPE) && 
        tuple.get(QPGAccountTransactions.pGAccountTransactions.transactionType).equalsIgnoreCase(Constants.SALE);
  }

	private void settlementEntityAmoutAndId(SettlementEntity settlementEntity, Tuple tuple) {
		if (tuple.get(QPGAccountTransactions.pGAccountTransactions.entityType) != null
				&& tuple.get(QPGAccountTransactions.pGAccountTransactions.entityType).equals(Constants.ISO_USER_TYPE)
				&&  tuple.get(QPGAccountTransactions.pGAccountTransactions.transactionType).equalsIgnoreCase(Constants.SALE)) {
			settlementEntity.setIsoAmount((tuple.get(QPGAccountTransactions.pGAccountTransactions.credit)));
			settlementEntity.setIsoId(tuple.get(QPGAccountTransactions.pGAccountTransactions.entityId));
		} else if (tuple.get(QPGAccountTransactions.pGAccountTransactions.entityType) != null
				&& tuple.get(QPGAccountTransactions.pGAccountTransactions.entityType).equals(Constants.ISO_USER_TYPE)
				&& tuple.get(QPGAccountTransactions.pGAccountTransactions.transactionType)
						.equalsIgnoreCase(Constants.REFUND)) {
			settlementEntity.setIsoAmount((tuple.get(QPGAccountTransactions.pGAccountTransactions.debit)));
			settlementEntity.setIsoId(tuple.get(QPGAccountTransactions.pGAccountTransactions.entityId));
		}
	}

	private BooleanExpression isTerminalIdEq(String terminalId) {

		return (terminalId != null && !"".equals(terminalId)) ? QPGTransaction.pGTransaction.terminalId.eq(terminalId)
				: null;
	}

	private BooleanExpression isTransactionIdEq(String transactionId) {

		return (transactionId != null && !"".equals(transactionId))
				? QPGTransaction.pGTransaction.id.eq(new BigInteger(transactionId)) : null;
	}

	private BooleanExpression isIssuanceIdEq(String issuerTxnRefNum) {

		return (issuerTxnRefNum != null && !"".equals(issuerTxnRefNum))
				? QPGTransaction.pGTransaction.issuerTxnRefNum.eq(issuerTxnRefNum) : null;
	}

	private BooleanExpression isMerchantIdEq(String merchantId) {

		return (merchantId != null && !"".equals(merchantId)) ? QPGTransaction.pGTransaction.merchantId.eq(merchantId)
				: null;
	}
	 
	@Override
	public List<PGTransaction> getTransactionsByBatchId(String batchId) {
		return transactionRepository.findByBatchId(batchId);
	}
	
	public List<PGTransaction> getPGTransactionListNotInAcquiring(String batchId, List<String> pgTxnIds) {
		List<PGTransaction> pgTransactionList = new ArrayList<>();
		StringBuilder sb = new StringBuilder();
		sb.append(
				" select ID, MERCHANT_ID, TERMINAL_ID, TXN_AMOUNT, ISSUER_TXN_REF_NUM,  CREATED_DATE, BATCH_ID, MERCHANT_SETTLEMENT_STATUS from PG_TRANSACTION ");
		sb.append(" where BATCH_ID=:batchId ");
		sb.append(" and ID not in (:pg_Txnid) ");
		sb.append(" and MERCHANT_SETTLEMENT_STATUS not like (:Status) ");
		Query pgTransactionReportParam = entityManager.createNativeQuery(sb.toString());
		pgTransactionReportParam.setParameter("batchId", batchId);
		pgTransactionReportParam.setParameter("pg_Txnid", pgTxnIds);
		pgTransactionReportParam.setParameter("Status", PGConstants.PG_TXN_VOIDED);
		List<Object> listOfReport = pgTransactionReportParam.getResultList();

		if (StringUtil.isListNotNullNEmpty(listOfReport)) {
			Iterator it = listOfReport.iterator();
			while (it.hasNext()) {
				Object[] obj = (Object[]) it.next();
				setPGTransactionData(pgTransactionList, obj);
			}
		}
		return pgTransactionList;
	}
	
	private void setPGTransactionData(List<PGTransaction> pgTransactionList, Object[] obj) {
		PGTransaction pgTransaction = new PGTransaction();
		pgTransaction.setTransactionId(StringUtil.isNull(obj[Integer.parseInt("0")]) ? null
				: (((BigInteger) obj[Integer.parseInt("0")]).toString()));
		pgTransaction.setMerchantId((StringUtil.isNull(obj[Integer.parseInt("1")]) ? null
				: ((String) obj[Integer.parseInt("1")])));
		pgTransaction.setTerminalId((StringUtil.isNull(obj[Integer.parseInt("2")]) ? null
				: (((Integer) obj[Integer.parseInt("2")]).toString())));
		pgTransaction.setTxnAmount((StringUtil.isNull(obj[Integer.parseInt("3")]) ? null
				: (Long.valueOf(((BigInteger) obj[Integer.parseInt("3")]).toString()))));
		pgTransaction.setIssuerTxnRefNum((StringUtil.isNull(obj[Integer.parseInt("4")]) ? null
				: (((BigInteger) obj[Integer.parseInt("4")]).toString())));
		pgTransaction.setCreatedDate((StringUtil.isNull(obj[Integer.parseInt("5")]) ? null
				: (Timestamp)obj[Integer.parseInt("5")]));
		pgTransaction.setBatchId((StringUtil.isNull(obj[Integer.parseInt("6")]) ? null
				: ((String) obj[Integer.parseInt("6")])));
		pgTransactionList.add(pgTransaction);
	}
	
	public List<PGAccountTransactions> getPGAccTransactionsByTxnId(String pgTxnId) {
		 return accountTransactionsRepository.findByPgTransactionId(pgTxnId);
	}
	
	public void saveorUpdate(List<String> pgTxnIdsList) {
		log.info("Entering :: TransactionDaoImpl :: saveorUpdate");
		StringBuilder sb = new StringBuilder();
		sb.append(" update PG_TRANSACTION, PG_ACCOUNT_TRANSACTIONS set PG_TRANSACTION.SETTLEMENT_BATCH_STATUS = :status, ");
		sb.append(" PG_TRANSACTION.MERCHANT_SETTLEMENT_STATUS = :merchantSettlementStatus , PG_ACCOUNT_TRANSACTIONS.STATUS = :status ");
		sb.append(" where PG_TRANSACTION.ID  in (:pg_Txnid) ");
		sb.append(" and PG_ACCOUNT_TRANSACTIONS.PG_TRANSACTION_ID in (:pg_Txnid) ");
		Query qry = entityManager.createNativeQuery(sb.toString());
		qry.setParameter("pg_Txnid", pgTxnIdsList);
		qry.setParameter("status", Constants.EXECUTED_STATUS);
		qry.setParameter("merchantSettlementStatus", Constants.SETTLEMENT_STATUS);
		qry.executeUpdate();
		log.info("Exiting :: TransactionDaoImpl :: saveorUpdate :: Updated Successfully");
	}
	
	public synchronized String generateTransactionRefNumber() throws DataAccessException {
	    log.info("Entering :: TransactionDaoImpl :: generateTransactionRefNumber");
		String transRefNumber = null;
		List list = entityManager.createNativeQuery("select `udf_txn_ref_num`();").getResultList();
		if (StringUtil.isListNotNullNEmpty(list)) {
			transRefNumber = list.get(0).toString();
		}
		log.info(" : " + "Generated trans ref number is" + transRefNumber);
		log.info("Exiting :: TransactionDaoImpl :: generateTransactionRefNumber");
		return transRefNumber;
	}
	
	private BooleanExpression isSettlementBatchStatus() {
		return  QPGTransaction.pGTransaction.settlementBatchStatus.isNull();
	}
	
	protected Integer getTotalNumberOfTxnRecordsForPM(GetTransactionsListRequest getTransactionsListRequest,
			Long entityId) {
		Timestamp startDate = null;
		Timestamp endDate = null;
		if (!CommonUtil.isNullAndEmpty(getTransactionsListRequest.getFrom_date())) {
			startDate = DateUtil.getStartDayTimestamp(getTransactionsListRequest.getFrom_date(),
					PGConstants.DD_MM_YYYY);
		}
		if (!CommonUtil.isNullAndEmpty(getTransactionsListRequest.getTo_date())) {
			endDate = DateUtil.getEndDayTimestamp(getTransactionsListRequest.getTo_date(), PGConstants.DD_MM_YYYY);
		}
		JPAQuery query = new JPAQuery(entityManager);
		List<Tuple> list = query
				.from(QPGTransaction.pGTransaction, QPGMerchant.pGMerchant, QPGAccount.pGAccount,
						QPGBankCurrencyMapping.pGBankCurrencyMapping, QPGCurrencyConfig.pGCurrencyConfig)
				.where(isMerchantId(getTransactionsListRequest.getMerchant_code()),
						isTxnId(getTransactionsListRequest.getTransactionId()),
						isProcessTxnId(getTransactionsListRequest.getProcessCode()),
						isCardNumberLike(getTransactionsListRequest.getCardNumber()),
						isTxnTypeLike(getTransactionsListRequest.getTransaction_type()),
						isTxnStatusLike(getTransactionsListRequest.getStatus()),
						isEntryModeLike(getTransactionsListRequest.getEntryMode()),
						isAcqChannel(getTransactionsListRequest.getAcqChannel()),
						isCardHolderNameLike(getTransactionsListRequest.getCardHolderName()),
						isMerchantSettlementStatus(getTransactionsListRequest.getMerchantSettlementStatus()),
						isMerchantBusinessNameLike(getTransactionsListRequest.getMerchantBusinessName()),
						isValidTxn(getTransactionsListRequest.getFromAmtRange(),
								getTransactionsListRequest.getToAmtRange()),
						QPGTransaction.pGTransaction.merchantId.eq(QPGMerchant.pGMerchant.merchantCode),
						QPGTransaction.pGTransaction.merchantId.eq(QPGAccount.pGAccount.entityId),
						QPGAccount.pGAccount.status.eq(Constants.ACTIVE)
								.and(QPGAccount.pGAccount.category.eq(PGConstants.PRIMARY_ACCOUNT)),
						QPGMerchant.pGMerchant.bankId.eq(QPGBankCurrencyMapping.pGBankCurrencyMapping.bankId),
						QPGBankCurrencyMapping.pGBankCurrencyMapping.currencyCodeAlpha
								.eq(QPGCurrencyConfig.pGCurrencyConfig.currencyCodeAlpha),
						isMerchantNameLike(getTransactionsListRequest.getMerchantName()),
						isValidDate(startDate, endDate),isEntityIdLike(entityId))
				.orderBy(orderByCreatedDateDesc()).list(QPGTransaction.pGTransaction.merchantId,
						QPGTransaction.pGTransaction.id, QPGTransaction.pGTransaction.issuerTxnRefNum,
						QPGTransaction.pGTransaction.procCode, QPGTransaction.pGTransaction.panMasked,
						QPGTransaction.pGTransaction.createdDate, QPGTransaction.pGTransaction.transactionType,
						QPGTransaction.pGTransaction.txnAmount, QPGTransaction.pGTransaction.feeAmount,
						QPGTransaction.pGTransaction.merchantFeeAmount, QPGTransaction.pGTransaction.txnDescription,
						QPGTransaction.pGTransaction.status, QPGTransaction.pGTransaction.merchantSettlementStatus,
						QPGTransaction.pGTransaction.terminalId, QPGTransaction.pGTransaction.posEntryMode,
						QPGTransaction.pGTransaction.acqChannel, QPGTransaction.pGTransaction.cardHolderName,
						QPGTransaction.pGTransaction.updatedDate, QPGTransaction.pGTransaction.authId,
						QPGTransaction.pGTransaction.txnTotalAmount, QPGTransaction.pGTransaction.acqTxnMode,
						QPGTransaction.pGTransaction.invoiceNumber, QPGTransaction.pGTransaction.processor,
						QPGTransaction.pGTransaction.txnMode, QPGMerchant.pGMerchant.firstName,
						QPGMerchant.pGMerchant.businessName, QPGMerchant.pGMerchant.merchantType,
						QPGAccount.pGAccount.accountNum, QPGAccount.pGAccount.entityType,
						QPGTransaction.pGTransaction.refTransactionId,
						QPGCurrencyConfig.pGCurrencyConfig.currencyCodeAlpha,
						QPGBankCurrencyMapping.pGBankCurrencyMapping.currencyCodeAlpha,
						QPGTransaction.pGTransaction.deviceLocalTxnTime, QPGTransaction.pGTransaction.timeZoneOffset);

		return (StringUtils.isListNotNullNEmpty(list) ? list.size() : 0);
	}
	
	protected BooleanExpression isEntityIdLike(Long entityId) {
		return (entityId != null)
				? QPGTransaction.pGTransaction.pmId.eq(entityId) : null;
	}
}
