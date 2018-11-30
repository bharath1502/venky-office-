/**
 * 
 */
package com.chatak.pg.acq.dao.impl;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import com.chatak.pg.acq.dao.SettlementReportDao;
import com.chatak.pg.acq.dao.model.PGSettlementReport;
import com.chatak.pg.acq.dao.model.QPGAccount;
import com.chatak.pg.acq.dao.model.QPGBankCurrencyMapping;
import com.chatak.pg.acq.dao.model.QPGCurrencyConfig;
import com.chatak.pg.acq.dao.model.QPGMerchant;
import com.chatak.pg.acq.dao.model.QPGMerchantBank;
import com.chatak.pg.acq.dao.model.QPGTransaction;
import com.chatak.pg.acq.dao.repository.SettlementRepository;
import com.chatak.pg.bean.settlement.SettlementEntity;
import com.chatak.pg.bean.settlement.SettlementMerchantDetails;
import com.chatak.pg.constants.AccountTransactionCode;
import com.chatak.pg.constants.PGConstants;
import com.chatak.pg.dao.util.StringUtil;
import com.chatak.pg.enums.EntryModePortalDisplayEnum;
import com.chatak.pg.model.FeeReportRequest;
import com.chatak.pg.user.bean.GetBatchReportRequest;
import com.chatak.pg.user.bean.GetTransactionsListRequest;
import com.chatak.pg.user.bean.Transaction;
import com.chatak.pg.util.CommonUtil;
import com.chatak.pg.util.Constants;
import com.chatak.pg.util.DateUtil;
import com.chatak.pg.util.StringUtils;
import com.mysema.query.Tuple;
import com.mysema.query.jpa.impl.JPAQuery;
import com.mysema.query.types.expr.BooleanExpression;

/**
 * @Author: Girmiti Software
 * @Date: Sep 16, 2017
 * @Time: 12:53:07 PM
 * @Version: 1.0
 * @Comments: 
 *
 */
@Repository("settlementReportDao")
public class SettlementReportDaoImpl extends TransactionDaoImpl implements SettlementReportDao {

  private static Logger log = Logger.getLogger(SettlementReportDaoImpl.class);

  @PersistenceContext
  private EntityManager entityManager;
  
  @Autowired
  private SettlementRepository settlementRepository;
  
  @Override
  public List<Transaction> getSettlementReportTransactions(
      GetTransactionsListRequest getTransactionsListRequest) {
    log.info("TransactionDaoImpl | getTransactions | Entering");
    List<Transaction> transactions = null;
    try {
      int offset = 0;
      int limit = 0;
      Integer totalRecords = getTransactionsListRequest.getNoOfRecords();
      if (getTransactionsListRequest.getPageIndex() == null
          || getTransactionsListRequest.getPageIndex().intValue() == 1) {
        totalRecords = getTotalNumberOfRecordsOnSearch(getTransactionsListRequest);
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
              QPGTransaction.pGTransaction.merchantId.eq(QPGMerchant.pGMerchant.merchantCode),
              QPGTransaction.pGTransaction.merchantSettlementStatus
                  .in(PGConstants.PG_SETTLEMENT_EXECUTED, PGConstants.PG_TXN_REFUNDED),
              QPGTransaction.pGTransaction.merchantId.eq(QPGAccount.pGAccount.entityId),
              QPGAccount.pGAccount.status.eq(Constants.ACTIVE)
                  .and(QPGAccount.pGAccount.category.eq(PGConstants.PRIMARY_ACCOUNT)),
              QPGMerchant.pGMerchant.bankId.eq(QPGBankCurrencyMapping.pGBankCurrencyMapping.bankId),
              QPGBankCurrencyMapping.pGBankCurrencyMapping.currencyCodeAlpha
                  .eq(QPGCurrencyConfig.pGCurrencyConfig.currencyCodeAlpha),
              isMerchantBusinessNameLike(getTransactionsListRequest.getMerchantBusinessName()),
              isValidDate(startDate, endDate))
          .offset(offset).limit(limit).orderBy(orderByCreatedDateDesc())
          .list(QPGTransaction.pGTransaction.merchantId, QPGTransaction.pGTransaction.transactionId,
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
              QPGTransaction.pGTransaction.refTransactionId, QPGTransaction.pGTransaction.batchId,
              QPGCurrencyConfig.pGCurrencyConfig.currencyCodeAlpha,
              QPGBankCurrencyMapping.pGBankCurrencyMapping.currencyCodeAlpha,
              QPGMerchant.pGMerchant.localCurrency, QPGTransaction.pGTransaction.userName,
              QPGTransaction.pGTransaction.deviceLocalTxnTime,
              QPGTransaction.pGTransaction.timeZoneOffset);
      if (!CollectionUtils.isEmpty(tupleList)) {
        transactions = new ArrayList<>();
        Transaction transactionResp = null;
        for (Tuple tuple : tupleList) {
          transactionResp = new Transaction();
          String posEntryMode = getSettlementReportTransactionsExisting(transactionResp, tuple);
          log.info("TransactionDaoImpl | getTransactions :: posEntryMode for enum: "
              + getEntryModeEnumFromPosEntryMode(posEntryMode));
          transactionResp.setEntryMode(EntryModePortalDisplayEnum
              .valueOf(getEntryModeEnumFromPosEntryMode(posEntryMode)).value());
          log.info("TransactionDaoImpl | getTransactions :: set posEntryMode");
          getSettlementReportTransactionsStatus(transactionResp, tuple);
          transactions.add(transactionResp);
        }
      }
    } catch (Exception e) {
      log.error("TransactionDaoImpl | getTransactions | Exception " + e);
    }
    log.info("TransactionDaoImpl | getTransactions | Exiting");
    return transactions;
  }

  private String getSettlementReportTransactionsExisting(Transaction transactionResp, Tuple tuple) {
    transactionResp.setTransactionId(tuple.get(QPGTransaction.pGTransaction.id).toString());
    transactionResp.setTransactionAmount(
        (StringUtils.amountToString(tuple.get(QPGTransaction.pGTransaction.txnAmount))));
    transactionResp.setTransactionDate(DateUtil.toDateStringFormat(
        tuple.get(QPGTransaction.pGTransaction.createdDate), PGConstants.DATE_FORMAT));
    transactionResp.setBatchDate(DateUtil.toDateStringFormat(
        tuple.get(QPGTransaction.pGTransaction.batchDate), PGConstants.DATE_FORMAT));
    transactionResp.setUpdated_date(DateUtil.toDateStringFormat(
        tuple.get(QPGTransaction.pGTransaction.updatedDate), PGConstants.DATE_FORMAT));
    transactionResp
        .setTransaction_type(tuple.get(QPGTransaction.pGTransaction.transactionType));
    transactionResp.setMaskCardNumber(StringUtils.lastFourDigits(tuple.get(QPGTransaction.pGTransaction.panMasked)));
    transactionResp.setMerchant_code((tuple.get(QPGTransaction.pGTransaction.merchantId)));
    transactionResp.setProcTxnCode(tuple.get(QPGTransaction.pGTransaction.procCode) != null
        ? tuple.get(QPGTransaction.pGTransaction.procCode) : "");
    transactionResp.setRef_transaction_id(
        getSettlementReportTransactionsIssuerTxnRefNum(tuple));
    transactionResp.setTxnDescription(tuple.get(QPGTransaction.pGTransaction.txnDescription));
    transactionResp
        .setMerchantFeeAmount(tuple.get(QPGTransaction.pGTransaction.merchantFeeAmount));
    transactionResp.setMerchantSettlementStatus(
        tuple.get(QPGTransaction.pGTransaction.merchantSettlementStatus));
    transactionResp.setAccountNumber(tuple.get(QPGAccount.pGAccount.accountNum));
    transactionResp
        .setTerminal_id(Long.valueOf(tuple.get(QPGTransaction.pGTransaction.terminalId)));
    transactionResp.setMerchantType(tuple.get(QPGAccount.pGAccount.entityType));
    transactionResp.setAcqChannel(tuple.get(QPGTransaction.pGTransaction.acqChannel));
    transactionResp.setMerchantName(tuple.get(QPGMerchant.pGMerchant.firstName));
	transactionResp.setDeviceLocalTxnTime(DateUtil.toDateStringFormat(DateUtil
		.toTimestamp(tuple.get(QPGTransaction.pGTransaction.deviceLocalTxnTime), Constants.HYPHEN_DATE_FORMAT),
		PGConstants.DATE_FORMAT));
    transactionResp.setTimeZoneOffset(tuple.get(QPGTransaction.pGTransaction.timeZoneOffset));
    transactionResp.setTxn_ref_num(
        getSettlementReportTransactionsRefTransactionId(tuple));
    String posEntryMode = tuple.get(QPGTransaction.pGTransaction.posEntryMode);
    log.info("TransactionDaoImpl | getTransactions :: posEntryMode: " + posEntryMode);
    if (null == posEntryMode) {
      posEntryMode = "00";
    }
    return posEntryMode;
  }

  private long getSettlementReportTransactionsRefTransactionId(Tuple tuple) {
    return StringUtils.isEmpty(settlementTransactionsRefTransactionId(tuple))
            ? 0L
            : Long
                .valueOf(settlementTransactionsRefTransactionId(tuple));
  }

  private String settlementTransactionsRefTransactionId(Tuple tuple) {
    return tuple.get(QPGTransaction.pGTransaction.refTransactionId) != null
        ? tuple.get(QPGTransaction.pGTransaction.refTransactionId).toString() : "";
  }

  private long getSettlementReportTransactionsIssuerTxnRefNum(Tuple tuple) {
    return StringUtils.isEmpty(settlementTransactionsIssuerTxnRefNum(tuple))
            ? 0L
            : Long.valueOf(settlementTransactionsIssuerTxnRefNum(tuple));
  }

  private String settlementTransactionsIssuerTxnRefNum(Tuple tuple) {
    return tuple.get(QPGTransaction.pGTransaction.issuerTxnRefNum) != null
        ? tuple.get(QPGTransaction.pGTransaction.issuerTxnRefNum).toString() : "";
  }

  private void getSettlementReportTransactionsStatus(Transaction transactionResp, Tuple tuple) {
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
    transactionResp.setTxn_total_amount((tuple.get(QPGTransaction.pGTransaction.txnTotalAmount) != null)
            ? tuple.get(QPGTransaction.pGTransaction.txnTotalAmount).doubleValue()/Integer.parseInt("100") : null);
    transactionResp.setAcqTxnMode(tuple.get(QPGTransaction.pGTransaction.acqTxnMode));
    transactionResp.setTxnMode(tuple.get(QPGTransaction.pGTransaction.txnMode));
    transactionResp.setInvoice_number(tuple.get(QPGTransaction.pGTransaction.invoiceNumber));
    transactionResp.setProcessor(tuple.get(QPGTransaction.pGTransaction.processor));
    transactionResp.setCardHolderName(tuple.get(QPGTransaction.pGTransaction.cardHolderName));
    transactionResp.setMerchantType(tuple.get(QPGMerchant.pGMerchant.merchantType));
    transactionResp.setMerchantBusinessName(tuple.get(QPGMerchant.pGMerchant.businessName));
    transactionResp.setLocalCurrency(tuple.get(QPGMerchant.pGMerchant.localCurrency));
    transactionResp.setBatchId(tuple.get(QPGTransaction.pGTransaction.batchId));
    transactionResp.setUserName(tuple.get(QPGTransaction.pGTransaction.userName));
    transactionResp.setFee_amount((tuple.get(QPGTransaction.pGTransaction.feeAmount) != null)
        ? tuple.get(QPGTransaction.pGTransaction.feeAmount).doubleValue()/Integer.parseInt("100") : null);
    transactionResp.setTxn_amount((tuple.get(QPGTransaction.pGTransaction.txnAmount) != null)
        ? tuple.get(QPGTransaction.pGTransaction.txnAmount).doubleValue()/Integer.parseInt("100") : null);
  }

  /**
   * @param batchReportRequest
   * @return
   */
  @Override
  public List<Transaction> getBatchReportTransactions(GetBatchReportRequest batchReportRequest) {
    log.info("TransactionDaoImpl | getTransactions | Entering");
    List<Transaction> transactions = null;
    try {
      int offset = 0;
      int limit = 0;
      Integer totalRecords = batchReportRequest.getNoOfRecords();
      if (batchReportRequest.getPageIndex() == null
          || batchReportRequest.getPageIndex().intValue() == 1) {
        totalRecords = getTotalNumberOfRecordsOnSearch(batchReportRequest);
        batchReportRequest.setNoOfRecords(totalRecords);
      }
      batchReportRequest.setNoOfRecords(totalRecords);
      if (batchReportRequest.getPageIndex() == null || batchReportRequest.getPageSize() == null) {
        limit = Constants.DEFAULT_PAGE_SIZE;
        offset = 0;
      } else {
        offset = (batchReportRequest.getPageIndex() - 1) * batchReportRequest.getPageSize();
        limit = batchReportRequest.getPageSize();
      }
      Timestamp endDate = null;
      Timestamp startDate = null;
      if (!CommonUtil.isNullAndEmpty(batchReportRequest.getFromDate())) {
        startDate =
            DateUtil.getStartDayTimestamp(batchReportRequest.getFromDate(), PGConstants.YYYY_MM_DD);
      }
      if (!CommonUtil.isNullAndEmpty(batchReportRequest.getToDate())) {
        endDate =
            DateUtil.getEndDayTimestamp(batchReportRequest.getToDate(), PGConstants.YYYY_MM_DD);
      }
      JPAQuery query = new JPAQuery(entityManager);
      List<Tuple> tupleList = query
          .from(QPGTransaction.pGTransaction, QPGMerchant.pGMerchant, QPGAccount.pGAccount,
              QPGBankCurrencyMapping.pGBankCurrencyMapping, QPGCurrencyConfig.pGCurrencyConfig)
          .where(
              isMerchantId(batchReportRequest.getMerchantCode(),
                  batchReportRequest.getSubMerchantCode()),
              QPGTransaction.pGTransaction.merchantId.eq(QPGMerchant.pGMerchant.merchantCode),
              QPGTransaction.pGTransaction.merchantSettlementStatus
                  .in(PGConstants.PG_SETTLEMENT_EXECUTED, PGConstants.PG_TXN_REFUNDED, Constants.SETTLEMENT_STATUS),
              QPGTransaction.pGTransaction.merchantId.eq(QPGAccount.pGAccount.entityId),
              QPGAccount.pGAccount.status.eq(Constants.ACTIVE)
                  .and(QPGAccount.pGAccount.category.eq(PGConstants.PRIMARY_ACCOUNT)),
              QPGMerchant.pGMerchant.bankId.eq(QPGBankCurrencyMapping.pGBankCurrencyMapping.bankId),
              QPGBankCurrencyMapping.pGBankCurrencyMapping.currencyCodeAlpha
                  .eq(QPGCurrencyConfig.pGCurrencyConfig.currencyCodeAlpha),
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
              QPGTransaction.pGTransaction.terminalId, QPGTransaction.pGTransaction.posEntryMode,
              QPGTransaction.pGTransaction.acqChannel, QPGTransaction.pGTransaction.cardHolderName,
              QPGTransaction.pGTransaction.updatedDate, QPGTransaction.pGTransaction.authId,
              QPGTransaction.pGTransaction.txnTotalAmount, QPGTransaction.pGTransaction.acqTxnMode,
              QPGTransaction.pGTransaction.invoiceNumber, QPGTransaction.pGTransaction.processor,
              QPGTransaction.pGTransaction.txnMode, QPGMerchant.pGMerchant.firstName,
              QPGMerchant.pGMerchant.businessName, QPGMerchant.pGMerchant.merchantType,
              QPGAccount.pGAccount.accountNum, QPGAccount.pGAccount.entityType,
              QPGTransaction.pGTransaction.refTransactionId, QPGTransaction.pGTransaction.batchId,
              QPGCurrencyConfig.pGCurrencyConfig.currencyCodeAlpha,
              QPGBankCurrencyMapping.pGBankCurrencyMapping.currencyCodeAlpha,
              QPGMerchant.pGMerchant.localCurrency, QPGTransaction.pGTransaction.batchDate,
              QPGTransaction.pGTransaction.deviceLocalTxnTime,
              QPGTransaction.pGTransaction.timeZoneOffset);
      if (!CollectionUtils.isEmpty(tupleList)) {
        transactions = new ArrayList<>();
        Transaction transactionResp = null;
        for (Tuple tuple : tupleList) {
          transactionResp = new Transaction();
          String posEntryMode = getSettlementReportTransactionsExisting(transactionResp, tuple);
          log.info("TransactionDaoImpl | getTransactions :: posEntryMode for enum: "
              + getEntryModeEnumFromPosEntryMode(posEntryMode));
          transactionResp.setEntryMode(EntryModePortalDisplayEnum
              .valueOf(getEntryModeEnumFromPosEntryMode(posEntryMode)).value());
          log.info("TransactionDaoImpl | getTransactions :: set posEntryMode");
          getBatchReportTransactionsStatus(transactionResp, tuple);
          transactions.add(transactionResp);
        }
      }
    } catch (Exception e) {
      log.error("TransactionDaoImpl | getTransactions | Exception " + e);
    }
    log.info("TransactionDaoImpl | getTransactions | Exiting");
    return transactions;
  }

  protected Integer getTotalNumberOfRecordsOnSearch(GetBatchReportRequest batchReportRequest) {
    Timestamp startDate = null;
    Timestamp endDate = null;
    if (!CommonUtil.isNullAndEmpty(batchReportRequest.getFromDate())) {
      startDate =
          DateUtil.getStartDayTimestamp(batchReportRequest.getFromDate(), PGConstants.YYYY_MM_DD);
    }
    if (!CommonUtil.isNullAndEmpty(batchReportRequest.getToDate())) {
      endDate = DateUtil.getEndDayTimestamp(batchReportRequest.getToDate(), PGConstants.YYYY_MM_DD);
    }
    JPAQuery query = new JPAQuery(entityManager);
    List<Tuple> list = query
        .from(QPGTransaction.pGTransaction, QPGMerchant.pGMerchant, QPGAccount.pGAccount,
            QPGBankCurrencyMapping.pGBankCurrencyMapping, QPGCurrencyConfig.pGCurrencyConfig)
        .where(
            isMerchantId(batchReportRequest.getMerchantCode(),
                batchReportRequest.getSubMerchantCode()),
            QPGTransaction.pGTransaction.merchantId.eq(QPGMerchant.pGMerchant.merchantCode),
            QPGTransaction.pGTransaction.merchantSettlementStatus
                .in(PGConstants.PG_SETTLEMENT_EXECUTED, PGConstants.PG_TXN_REFUNDED, Constants.SETTLEMENT_STATUS),
            QPGTransaction.pGTransaction.merchantId.eq(QPGAccount.pGAccount.entityId),
            QPGAccount.pGAccount.status.eq(Constants.ACTIVE)
                .and(QPGAccount.pGAccount.category.eq(PGConstants.PRIMARY_ACCOUNT)),
            QPGMerchant.pGMerchant.bankId.eq(QPGBankCurrencyMapping.pGBankCurrencyMapping.bankId),
            QPGBankCurrencyMapping.pGBankCurrencyMapping.currencyCodeAlpha
                .eq(QPGCurrencyConfig.pGCurrencyConfig.currencyCodeAlpha),
            isValidDate(startDate, endDate))
        .orderBy(orderByCreatedDateDesc()).list(QPGTransaction.pGTransaction.merchantId,
            QPGTransaction.pGTransaction.id,
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

  protected BooleanExpression isMerchantId(String merchantCode, String subMerchantCode) {
    if (!"".equals(merchantCode) && merchantCode != null && !"".equals(subMerchantCode)
        && subMerchantCode != null) {
      return QPGTransaction.pGTransaction.merchantId.in(merchantCode, subMerchantCode);
    } else if (!"".equals(subMerchantCode) && subMerchantCode != null) {
        return QPGTransaction.pGTransaction.merchantId.eq(subMerchantCode);
    } else if (!"".equals(merchantCode) && merchantCode != null) {
      return QPGTransaction.pGTransaction.merchantId.eq(merchantCode);
    } else {
      return null;
    }
  }
  
  private void getBatchReportTransactionsStatus(Transaction transactionResp, Tuple tuple) {
    if (0 == tuple.get(QPGTransaction.pGTransaction.status)) {
      transactionResp.setStatusMessage("Approved");
    } else if (1 == tuple.get(QPGTransaction.pGTransaction.status)) {
      transactionResp.setStatusMessage("Declined");
    } else {
      transactionResp.setStatusMessage("Failed");
    }
    transactionResp.setAuth_id(tuple.get(QPGTransaction.pGTransaction.authId));
    transactionResp
        .setFeeString(tuple.get(QPGCurrencyConfig.pGCurrencyConfig.currencyCodeAlpha) + " "
            + StringUtils.amountToString(tuple.get(QPGTransaction.pGTransaction.feeAmount)));
    transactionResp
        .setTxn_total_amount((tuple.get(QPGTransaction.pGTransaction.txnTotalAmount) != null)
            ? tuple.get(QPGTransaction.pGTransaction.txnTotalAmount).doubleValue() : null);
    transactionResp.setAcqTxnMode(tuple.get(QPGTransaction.pGTransaction.acqTxnMode));
    transactionResp.setProcessor(tuple.get(QPGTransaction.pGTransaction.processor));
    transactionResp.setInvoice_number(tuple.get(QPGTransaction.pGTransaction.invoiceNumber));
    transactionResp.setTxnMode(tuple.get(QPGTransaction.pGTransaction.txnMode));
    transactionResp.setMerchantBusinessName(tuple.get(QPGMerchant.pGMerchant.businessName));
    transactionResp.setCardHolderName(tuple.get(QPGTransaction.pGTransaction.cardHolderName));
    transactionResp.setMerchantType(tuple.get(QPGMerchant.pGMerchant.merchantType));
    transactionResp.setBatchId(tuple.get(QPGTransaction.pGTransaction.batchId));
    transactionResp.setLocalCurrency(tuple.get(QPGMerchant.pGMerchant.localCurrency));
  }

/**
 * @param pgSettlementReport
 * @return
 */
@Override
public PGSettlementReport save(PGSettlementReport pgSettlementReport) {
	return settlementRepository.save(pgSettlementReport);
}

/**
 * @param pgTxnIds
 * @return
 */
	@Override
	public List<SettlementEntity> getAllMatchedTxnsByPgTxns(FeeReportRequest transactionRequest) {
	    log.info("Entering :: SettlementReportDaoImpl :: getAllMatchedTxnsByPgTxns");
		List<SettlementEntity> settlementEntityList = new ArrayList<>();
		String pgTxnIds = transactionRequest.getPgTxnIds().replaceAll("\\[", "").replaceAll("\\]","");
		String[] txnIdsArr = pgTxnIds.split(",");
		log.info("Pg Txn ids " + transactionRequest.getPgTxnIds());
		try {
			StringBuilder matchedBuilder = new StringBuilder();
			matchedBuilder.append("SELECT pgTxn.MERCHANT_ID,pgTxn.TERMINAL_ID,pgAccTxn.PG_TRANSACTION_ID,");
			matchedTxnsQuery(matchedBuilder);
			matchedBuilder.append(
					" pgAccTxn.DEVICE_LOCAL_TXN_TIME, pgAccTxn.TRANSACTION_TYPE, pm.PROGRAM_MANAGER_NAME, iso.ISO_NAME, iso.BANK_ACC_NUM, iso.ROUTING_NUMBER");
			Query qry = entityManager.createNativeQuery(matchedBuilder.toString());
			qry.setParameter("pgTxnIds", Arrays.asList(txnIdsArr));
			qry.setParameter("merchantCode", Arrays.asList(AccountTransactionCode.CC_AMOUNT_CREDIT, AccountTransactionCode.CC_AMOUNT_DEBIT));
			qry.setParameter("pmCode", Arrays.asList(AccountTransactionCode.CC_PM_FEE_CREDIT, AccountTransactionCode.CC_PM_FEE_DEBIT));
			qry.setParameter("isoCode", Arrays.asList(AccountTransactionCode.CC_ISO_FEE_CREDIT, AccountTransactionCode.CC_ISO_FEE_DEBIT));
			List<Object> objectList = qry.getResultList();
			log.info("Retrieved txns size" + objectList.size());
			if (StringUtil.isListNotNullNEmpty(objectList)) {
				iterateFeeReportDetails(settlementEntityList, objectList);
			}
		} catch (Exception e) {
		  log.error("Error :: SettlementReportDaoImpl :: getAllMatchedTxnsByPgTxns : " + e.getMessage(), e);
		}
		log.info("settlementEntityList size: " + settlementEntityList.size());
		log.info("Entering :: SettlementReportDaoImpl :: getAllMatchedTxnsByPgTxns");
		return settlementEntityList;
	}

	private void matchedTxnsQuery(StringBuilder matchedBuilder) {
		matchedBuilder.append(" pgAccTxn.TRANSACTION_TIME,pgAccTxn.DEVICE_LOCAL_TXN_TIME,pgAccTxn.TRANSACTION_TYPE,");
		matchedBuilder.append(" pm.PROGRAM_MANAGER_NAME,iso.ISO_NAME, iso.BANK_ACC_NUM, iso.ROUTING_NUMBER, ");
		matchedBuilder.append(
				" IFNULL(SUM(CASE WHEN pgAccTxn.TRANSACTION_CODE IN (:merchantCode) THEN pgAccTxn.CREDIT END),0)+ ");
		matchedBuilder.append(" IFNULL(SUM(CASE WHEN pgAccTxn.TRANSACTION_CODE IN (:merchantCode) THEN pgAccTxn.DEBIT END),0) AS 'MerchantCreditAmt', ");
		matchedBuilder
				.append(" IFNULL(SUM( CASE WHEN pgAccTxn.TRANSACTION_CODE IN (:pmCode) THEN pgAccTxn.CREDIT END),0)+ ");
		matchedBuilder.append(" IFNULL (SUM( CASE WHEN pgAccTxn.TRANSACTION_CODE IN (:pmCode) THEN pgAccTxn.DEBIT END),0) AS 'PmCreditAmt', ");
		matchedBuilder
				.append(" IFNULL(SUM( CASE WHEN pgAccTxn.TRANSACTION_CODE IN (:isoCode) THEN pgAccTxn.CREDIT END),0)+ ");
		matchedBuilder.append(" IFNULL(SUM( CASE WHEN pgAccTxn.TRANSACTION_CODE IN (:isoCode) THEN pgAccTxn.DEBIT END),0) AS 'IsoCreditAmt' ");
		matchedBuilder.append(" FROM PG_TRANSACTION pgTxn");
		matchedBuilder.append(" LEFT JOIN PG_ACCOUNT_TRANSACTIONS pgAccTxn");
		matchedBuilder.append(" ON pgTxn.ID=pgAccTxn.PG_TRANSACTION_ID");
		matchedBuilder.append(" LEFT JOIN PG_PROGRAM_MANAGER pm ON pm.ID=pgTxn.PM_ID");
		matchedBuilder.append(" LEFT JOIN PG_ISO iso ON iso.id = pgTxn.ISO_ID ");
		matchedBuilder.append(" WHERE pgTxn.ID    IN(:pgTxnIds)");
		matchedBuilder.append(
				" AND pgAccTxn.TRANSACTION_CODE IN('CC_AMOUNT_CREDIT','CC_PM_FEE_CREDIT','CC_ISO_FEE_CREDIT', 'CC_AMOUNT_DEBIT', 'CC_PM_FEE_DEBIT','CC_ISO_FEE_DEBIT')");
		matchedBuilder.append(
				" GROUP BY pgTxn.MERCHANT_ID, pgTxn.TERMINAL_ID, pgAccTxn.PG_TRANSACTION_ID, pgAccTxn.TRANSACTION_TIME,");
	}

	private void iterateFeeReportDetails(List<SettlementEntity> settlementEntityList, List<Object> objectList) {
		Iterator<Object> itr = objectList.iterator();
		while (itr.hasNext()) {
			Object[] objs = (Object[]) itr.next();
			setTxnsDetails(settlementEntityList, objs);
		}
	}

private void setTxnsDetails(List<SettlementEntity> settlementEntityList, Object[] objs) {
	SettlementEntity feeReportDto = new SettlementEntity();
	feeReportDto.setMerchantId(StringUtil.isNull(objs[0]) ? null : ((String) objs[0]));
	feeReportDto.setTerminalId(StringUtil.isNull(objs[1]) ? null : String.valueOf((Integer) objs[1]));
	feeReportDto.setPgTxnId(StringUtil.isNull(objs[Integer.parseInt("2")]) ? null : ((String) objs[Integer.parseInt("2")]));
	feeReportDto.setTxnDate(StringUtil.isNull(objs[Integer.parseInt("3")]) ? null : DateUtil.toTimestamp(DateUtil.toDateStringFormat((Timestamp)(objs[Integer.parseInt("3")]),Constants.DATE_TIME_FORMAT), Constants.DATE_TIME_FORMAT));
	feeReportDto.setDeviceLocalTxnTime(StringUtil.isNull(objs[Integer.parseInt("4")]) ? null : DateUtil.toDateStringFormat(((Timestamp) objs[Integer.parseInt("4")]),Constants.DATE_TIME_FORMAT));
	feeReportDto.setTxnType(StringUtil.isNull(objs[Integer.parseInt("5")]) ? null : ((String) objs[Integer.parseInt("5")]));
	feeReportDto.setProgramManagerName(StringUtil.isNull(objs[Integer.parseInt("6")]) ? null : ((String) objs[Integer.parseInt("6")]));
	feeReportDto.setIsoName(StringUtil.isNull(objs[Integer.parseInt("7")]) ? null : ((String) objs[Integer.parseInt("7")]));
	feeReportDto.setBankAccountNumber(StringUtil.isNull(objs[Integer.parseInt("8")]) ? null : ((String) objs[Integer.parseInt("8")]));
	feeReportDto.setBankRoutingNumber(StringUtil.isNull(objs[Integer.parseInt("9")]) ? null : ((String) objs[Integer.parseInt("9")]));
	feeReportDto.setPmAmount(StringUtil.isNull(objs[Integer.parseInt("11")]) ? null : ((BigDecimal) objs[Integer.parseInt("11")]).longValue());
	feeReportDto.setIsoAmount(StringUtil.isNull(objs[Integer.parseInt("12")]) ? 0 : ((BigDecimal) objs[Integer.parseInt("12")]).longValue());
	Long totalAmount = StringUtil.isNull(objs[Integer.parseInt("10")]) ? 0 : ((BigDecimal) objs[Integer.parseInt("10")]).longValue();
	Long merchantAmount = totalAmount - (feeReportDto.getPmAmount()+feeReportDto.getIsoAmount());
	feeReportDto.setMerchantAmount(merchantAmount);
	settlementEntityList.add(feeReportDto);
}

/**
 * @param merchantCode
 * @return
 */
	@Override
	public List<SettlementMerchantDetails> fetchMerchantDetailsByMerchantCode(String merchantCode) {
		log.info("SettlementReportDaoImpl | fetchMerchantDetails | Entering");
		List<SettlementMerchantDetails> list = new ArrayList<>();
		try {
			JPAQuery query = new JPAQuery(entityManager);
			List<Tuple> tupleList = query.from(QPGMerchant.pGMerchant, QPGMerchantBank.pGMerchantBank)
					.where(QPGMerchant.pGMerchant.merchantCode.eq(merchantCode)
							.and(QPGMerchant.pGMerchant.merchantCode.eq(QPGMerchantBank.pGMerchantBank.merchantId)))
					.list(QPGMerchant.pGMerchant.businessName, QPGMerchantBank.pGMerchantBank.bankName,
							QPGMerchantBank.pGMerchantBank.bankAccNum, QPGMerchantBank.pGMerchantBank.routingNumber,
							QPGMerchant.pGMerchant.localCurrency, QPGMerchant.pGMerchant.merchantCode);
			if (!CollectionUtils.isEmpty(tupleList)) {
				SettlementMerchantDetails merchantDetails = new SettlementMerchantDetails();
				for (Tuple tuple : tupleList) {
					merchantDetails.setBusinessName(tuple.get((QPGMerchant.pGMerchant.businessName)));
					merchantDetails.setBankNmae(tuple.get((QPGMerchantBank.pGMerchantBank.bankName)));
					merchantDetails.setBankAccountNumber(tuple.get((QPGMerchantBank.pGMerchantBank.bankAccNum)));
					merchantDetails.setBankRoutingNumber(tuple.get((QPGMerchantBank.pGMerchantBank.routingNumber)));
					merchantDetails.setLocalCurrency(tuple.get((QPGMerchant.pGMerchant.localCurrency)));
					merchantDetails.setMerchantCode(tuple.get((QPGMerchant.pGMerchant.merchantCode)));
					list.add(merchantDetails);
				}
			}
		} catch (Exception e) {
			log.error("SettlementReportDaoImpl | fetchMerchantDetails | Exception " + e);
		}
		return list;
	}
 }
