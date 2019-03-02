/**
 * 
 */
package com.chatak.pg.acq.dao.impl;

import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import com.chatak.pg.acq.dao.MerchantDao;
import com.chatak.pg.acq.dao.TransactionReportDao;
import com.chatak.pg.acq.dao.model.PGTransaction;
import com.chatak.pg.acq.dao.model.QPGAccount;
import com.chatak.pg.acq.dao.model.QPGAccountFeeLog;
import com.chatak.pg.acq.dao.model.QPGAccountHistory;
import com.chatak.pg.acq.dao.model.QPGBankCurrencyMapping;
import com.chatak.pg.acq.dao.model.QPGCurrencyConfig;
import com.chatak.pg.acq.dao.model.QPGMerchant;
import com.chatak.pg.acq.dao.model.QPGOnlineTxnLog;
import com.chatak.pg.acq.dao.model.QPGTransaction;
import com.chatak.pg.acq.dao.repository.TransactionRepository;
import com.chatak.pg.bean.TransactionPopUpDataDto;
import com.chatak.pg.constants.PGConstants;
import com.chatak.pg.dao.util.StringUtil;
import com.chatak.pg.model.ReportsDTO;
import com.chatak.pg.user.bean.GetTransactionsListRequest;
import com.chatak.pg.util.CommonUtil;
import com.chatak.pg.util.Constants;
import com.chatak.pg.util.DateUtil;
import com.chatak.pg.util.StringUtils;
import com.mysema.query.Tuple;
import com.mysema.query.jpa.impl.JPAQuery;
import com.mysema.query.types.OrderSpecifier;
import com.mysema.query.types.expr.BooleanExpression;

/**
 * @Author: Girmiti Software
 * @Date: Aug 24, 2017
 * @Time: 2:33:10 PM
 * @Version: 1.0
 * @Comments: 
 *
 */

@Repository("transactionReportDao")
public class TransactionReportDaoImpl extends TransactionDaoImpl implements TransactionReportDao {

  private static Logger log = Logger.getLogger(TransactionReportDaoImpl.class);

  @Autowired
  private MerchantDao merchantDao;

  @PersistenceContext
  private EntityManager entityManager;

  @Autowired
  private TransactionRepository transactionRepository;

  @Override
  public List<ReportsDTO> getAllTransactionsReport(
      GetTransactionsListRequest getTransactionsListRequest) {
    List<ReportsDTO> reportList = null;
    ReportsDTO transactionsReports = null;
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
            QPGOnlineTxnLog.pGOnlineTxnLog)
        .where(
            QPGMerchant.pGMerchant.merchantCode.eq(getTransactionsListRequest.getMerchant_code())
                .and(QPGMerchant.pGMerchant.merchantCode
                    .eq(QPGTransaction.pGTransaction.merchantId.stringValue())
                    .and(QPGAccount.pGAccount.entityId
                        .eq(QPGTransaction.pGTransaction.merchantId.stringValue()))
                    .and(QPGTransaction.pGTransaction.transactionId
                        .eq(QPGOnlineTxnLog.pGOnlineTxnLog.pgTxnId.stringValue()))),
            isValidDate(startDate, endDate))
        .orderBy(orderByCreatedDateDesc()).list(QPGTransaction.pGTransaction.createdDate,
            QPGTransaction.pGTransaction.transactionId, QPGTransaction.pGTransaction.txnDescription,
            QPGTransaction.pGTransaction.paymentMethod, QPGTransaction.pGTransaction.txnAmount,
            QPGTransaction.pGTransaction.status, QPGTransaction.pGTransaction.transactionType,
            QPGTransaction.pGTransaction.authId, QPGTransaction.pGTransaction.refTransactionId,
            QPGTransaction.pGTransaction.terminalId, QPGTransaction.pGTransaction.invoiceNumber,
            QPGTransaction.pGTransaction.panMasked, QPGTransaction.pGTransaction.merchantId,
            QPGTransaction.pGTransaction.acqTxnMode, QPGTransaction.pGTransaction.acqChannel,
            QPGTransaction.pGTransaction.feeAmount, QPGTransaction.pGTransaction.txnTotalAmount,
            QPGTransaction.pGTransaction.processor, QPGTransaction.pGTransaction.txnMode,
            QPGTransaction.pGTransaction.merchantSettlementStatus,
            QPGAccount.pGAccount.availableBalance, QPGOnlineTxnLog.pGOnlineTxnLog.cardAssciation);
    if (StringUtil.isListNotNullNEmpty(infoList)) {
      reportList = new ArrayList<>();
      TransactionPopUpDataDto txnDto = null;
      String statusMsg = null;
      for (Tuple tuple : infoList) {
        transactionsReports = new ReportsDTO();
        txnDto = new TransactionPopUpDataDto();
        transactionsReports.setDateTime(DateUtil.toDateStringFormat(
            tuple.get(QPGTransaction.pGTransaction.createdDate), DateUtil.VIEW_DATE_TIME_FORMAT));
        transactionsReports.setTransactionId(tuple.get(QPGTransaction.pGTransaction.transactionId));
        transactionsReports.setDescription(tuple.get(QPGTransaction.pGTransaction.txnDescription));
        transactionsReports.setCardType(tuple.get(QPGOnlineTxnLog.pGOnlineTxnLog.cardAssciation));
        transactionsReports.setPaymentMethod(tuple.get(QPGTransaction.pGTransaction.paymentMethod));
        transactionsReports.setAmount(PGConstants.DOLLAR_SYMBOL
            + StringUtils.amountToString(tuple.get(QPGTransaction.pGTransaction.txnAmount)));
        transactionsReports.setStatus(tuple.get(QPGTransaction.pGTransaction.status).toString());
        transactionsReports.setAvailableBalance(PGConstants.DOLLAR_SYMBOL
            + StringUtils.amountToString(tuple.get(QPGAccount.pGAccount.availableBalance)));

        if (0 == tuple.get(QPGTransaction.pGTransaction.status)) {
          statusMsg = Constants.APPROVED;
        } else if (1 == tuple.get(QPGTransaction.pGTransaction.status)) {
          statusMsg = Constants.DECLINED;
        } else {
          statusMsg = Constants.FAILED;
        }
        transactionsReports.setStatus(statusMsg);
        getAllTransactionsReportStatusMsg(txnDto, statusMsg, tuple);
        transactionsReports.setTxnPopupDto(txnDto);

        reportList.add(transactionsReports);
      }
    }
    return reportList;
  }

  private void getAllTransactionsReportStatusMsg(TransactionPopUpDataDto txnDto, String statusMsg,
      Tuple tuple) {
    txnDto
        .setTransaction_type(((tuple.get(QPGTransaction.pGTransaction.transactionType) != null)
            ? tuple.get(QPGTransaction.pGTransaction.transactionType) : ""));
    txnDto.setAuth_id(((tuple.get(QPGTransaction.pGTransaction.authId) != null)
        ? tuple.get(QPGTransaction.pGTransaction.authId) : ""));
    txnDto.setTransactionId(((tuple.get(QPGTransaction.pGTransaction.transactionId) != null)
        ? tuple.get(QPGTransaction.pGTransaction.transactionId) : ""));
    txnDto.setRef_transaction_id(
        ((tuple.get(QPGTransaction.pGTransaction.refTransactionId) != null)
            ? tuple.get(QPGTransaction.pGTransaction.refTransactionId) : "N/A"));
    txnDto.setTerminal_id(((tuple.get(QPGTransaction.pGTransaction.terminalId) != null)
        ? tuple.get(QPGTransaction.pGTransaction.terminalId) : ""));
    txnDto.setInvoice_number(((tuple.get(QPGTransaction.pGTransaction.invoiceNumber) != null)
        ? tuple.get(QPGTransaction.pGTransaction.invoiceNumber) : ""));
    txnDto.setMaskCardNumber(((StringUtils.lastFourDigits(tuple.get(QPGTransaction.pGTransaction.panMasked)) != null)
        ? StringUtils.lastFourDigits(tuple.get(QPGTransaction.pGTransaction.panMasked)) : ""));
    txnDto.setMerchant_code(((tuple.get(QPGTransaction.pGTransaction.merchantId) != null)
        ? tuple.get(QPGTransaction.pGTransaction.merchantId) : ""));
    txnDto.setAcqTxnMode(((tuple.get(QPGTransaction.pGTransaction.acqTxnMode) != null)
        ? tuple.get(QPGTransaction.pGTransaction.acqTxnMode) : ""));
    txnDto.setAcqChannel(((tuple.get(QPGTransaction.pGTransaction.acqChannel) != null)
        ? tuple.get(QPGTransaction.pGTransaction.acqChannel) : ""));
    txnDto.setTransactionDate(DateUtil.toDateStringFormat(
        tuple.get(QPGTransaction.pGTransaction.createdDate), DateUtil.VIEW_DATE_TIME_FORMAT));
    txnDto.setTransactionAmount((tuple.get(QPGTransaction.pGTransaction.txnAmount) != null)
        ? PGConstants.DOLLAR_SYMBOL + StringUtils
            .amountToString(pGTransactionTxnAmount(tuple))
        : PGConstants.DOLLAR_SYMBOL + "0.00");
    txnDto.setFee_amount((tuple.get(QPGTransaction.pGTransaction.feeAmount) != null)
        ? PGConstants.DOLLAR_SYMBOL + StringUtils
            .amountToString(pGTransactionFeeAmount(tuple))
        : PGConstants.DOLLAR_SYMBOL + "0.00");
    txnDto.setTxn_total_amount((tuple.get(QPGTransaction.pGTransaction.txnTotalAmount) != null)
        ? PGConstants.DOLLAR_SYMBOL + StringUtils
            .amountToString(pGTransactionTxnTotalAmount(tuple))
        : PGConstants.DOLLAR_SYMBOL + "0.00");
    pGTransactionExisting(txnDto, statusMsg, tuple);
  }

  private void pGTransactionExisting(TransactionPopUpDataDto txnDto, String statusMsg,
      Tuple tuple) {
    txnDto.setProcessor(((tuple.get(QPGTransaction.pGTransaction.processor) != null)
        ? tuple.get(QPGTransaction.pGTransaction.processor) : ""));
    txnDto.setMerchantSettlementStatus(
        ((tuple.get(QPGTransaction.pGTransaction.merchantSettlementStatus) != null)
            ? tuple.get(QPGTransaction.pGTransaction.merchantSettlementStatus) : ""));
    txnDto.setTxnDescription(((tuple.get(QPGTransaction.pGTransaction.txnDescription) != null)
        ? tuple.get(QPGTransaction.pGTransaction.txnDescription) : ""));
    txnDto.setStatusMessage(statusMsg);
  }

  private long pGTransactionTxnTotalAmount(Tuple tuple) {
    return tuple.get(QPGTransaction.pGTransaction.txnTotalAmount).longValue();
  }

  private long pGTransactionFeeAmount(Tuple tuple) {
    return tuple.get(QPGTransaction.pGTransaction.feeAmount).longValue();
  }

  private long pGTransactionTxnAmount(Tuple tuple) {
    return tuple.get(QPGTransaction.pGTransaction.txnAmount).longValue();
  }

  @Override
  public List<ReportsDTO> getTransactionListReport(
      GetTransactionsListRequest getTransactionsListRequest) {
    log.info("TransactionDaoImpl | getTransactionListReport | Entering");
    List<ReportsDTO> reportList = null;
    try {
      int offset = 0;
      int limit = 0;
      Integer totalRecords = getTransactionsListRequest.getNoOfRecords();
      if (getTransactionsListRequest.getPageIndex() == null
          || getTransactionsListRequest.getPageIndex().intValue() == 1) {
        totalRecords = getTotalNumberOfRecords(getTransactionsListRequest);
        getTransactionsListRequest.setNoOfRecords(totalRecords);
      }
      getTransactionsListRequest.setNoOfRecords(totalRecords);
      if (getTransactionsListRequest.getPageIndex() == null
          || getTransactionsListRequest.getPageSize() == null) {
        limit = Constants.DEFAULT_PAGE_SIZE;
        offset = 0;
      } else {
        offset = (getTransactionsListRequest.getPageIndex() - 1)
            * getTransactionsListRequest.getPageSize();
        limit = getTransactionsListRequest.getPageSize();
      }
      Timestamp startDate = null;
      Timestamp endDate = null;
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
          .from(QPGTransaction.pGTransaction, QPGMerchant.pGMerchant,
              QPGAccountHistory.pGAccountHistory, QPGOnlineTxnLog.pGOnlineTxnLog,
              QPGBankCurrencyMapping.pGBankCurrencyMapping, QPGCurrencyConfig.pGCurrencyConfig)
          .where(isMerchantId(getTransactionsListRequest.getMerchant_code()),
              QPGTransaction.pGTransaction.merchantId.eq(QPGMerchant.pGMerchant.merchantCode),
              isValidDate(startDate, endDate),
              QPGTransaction.pGTransaction.transactionId
                  .eq(QPGAccountHistory.pGAccountHistory.transactionId)
                  .and(QPGTransaction.pGTransaction.merchantId
                      .eq(QPGAccountHistory.pGAccountHistory.entityId)),
              QPGTransaction.pGTransaction.transactionId
                  .eq(QPGOnlineTxnLog.pGOnlineTxnLog.pgTxnId.stringValue()),
              QPGTransaction.pGTransaction.transactionId
                  .eq((null != QPGOnlineTxnLog.pGOnlineTxnLog.pgTxnId)
                      ? QPGOnlineTxnLog.pGOnlineTxnLog.pgTxnId.stringValue() : null),
              QPGMerchant.pGMerchant.bankId.eq(QPGBankCurrencyMapping.pGBankCurrencyMapping.bankId),
              QPGBankCurrencyMapping.pGBankCurrencyMapping.currencyCodeAlpha
                  .eq(QPGCurrencyConfig.pGCurrencyConfig.currencyCodeAlpha),
              isValidTransactionAcqChannel(getTransactionsListRequest.getAcqChannel()))
          .offset(offset).limit(limit).orderBy(orderByCreatedDateDesc())
          .list(QPGTransaction.pGTransaction.merchantId, QPGTransaction.pGTransaction.transactionId,
              QPGTransaction.pGTransaction.issuerTxnRefNum, QPGTransaction.pGTransaction.procCode,
              QPGTransaction.pGTransaction.panMasked, QPGTransaction.pGTransaction.createdDate,
              QPGTransaction.pGTransaction.updatedDate,
              QPGTransaction.pGTransaction.transactionType, QPGTransaction.pGTransaction.txnAmount,
              QPGTransaction.pGTransaction.feeAmount, QPGTransaction.pGTransaction.txnDescription,
              QPGTransaction.pGTransaction.status,
              QPGTransaction.pGTransaction.merchantSettlementStatus,
              QPGTransaction.pGTransaction.terminalId, QPGTransaction.pGTransaction.feeAmount,
              QPGTransaction.pGTransaction.posEntryMode, QPGTransaction.pGTransaction.acqChannel,
              QPGTransaction.pGTransaction.cardHolderName,
              QPGAccountHistory.pGAccountHistory.availableBalance,
              QPGOnlineTxnLog.pGOnlineTxnLog.cardAssciation,
              QPGTransaction.pGTransaction.paymentMethod,
              QPGAccountHistory.pGAccountHistory.updatedDate,
              QPGAccountHistory.pGAccountHistory.accountNum,
              QPGAccountHistory.pGAccountHistory.entityType, QPGMerchant.pGMerchant.userName,
              QPGMerchant.pGMerchant.businessName, QPGMerchant.pGMerchant.merchantType,
              QPGTransaction.pGTransaction.authId, QPGTransaction.pGTransaction.refTransactionId,
              QPGTransaction.pGTransaction.invoiceNumber, QPGTransaction.pGTransaction.acqTxnMode,
              QPGTransaction.pGTransaction.txnTotalAmount, QPGTransaction.pGTransaction.processor,
              QPGTransaction.pGTransaction.txnMode,
              QPGBankCurrencyMapping.pGBankCurrencyMapping.currencyCodeAlpha);
      if (!CollectionUtils.isEmpty(tupleList)) {
        reportList = new ArrayList<>();
        ReportsDTO statementReports = null;
        TransactionPopUpDataDto txnDto = null;
        
        for (Tuple tuple : tupleList) {
          statementReports = new ReportsDTO();
          txnDto = new TransactionPopUpDataDto();
          statementReports.setDateTime(
              DateUtil.toDateStringFormat(tuple.get(QPGAccountHistory.pGAccountHistory.updatedDate),
                  DateUtil.VIEW_DATE_TIME_FORMAT));
          statementReports.setTransactionId(tuple.get(QPGTransaction.pGTransaction.transactionId));
          statementReports.setDescription(tuple.get(QPGTransaction.pGTransaction.txnDescription));
          statementReports.setCardType(tuple.get(QPGOnlineTxnLog.pGOnlineTxnLog.cardAssciation));
          statementReports.setPaymentMethod(tuple.get(QPGTransaction.pGTransaction.paymentMethod));
          statementReports.setAmount(
              StringUtils.amountToString(tuple.get(QPGTransaction.pGTransaction.txnAmount)));
          statementReports.setAvailableBalance(StringUtils
              .amountToString(tuple.get(QPGAccountHistory.pGAccountHistory.availableBalance)));
          statementReports.setUserName(tuple.get(QPGMerchant.pGMerchant.userName));
          statementReports
              .setAccountNumber(tuple.get(QPGAccountHistory.pGAccountHistory.accountNum));
          statementReports.setAccountType(tuple.get(QPGAccountHistory.pGAccountHistory.entityType));
          statementReports.setCompanyName(tuple.get(QPGMerchant.pGMerchant.businessName));
          statementReports.setCurrency(
              tuple.get(QPGBankCurrencyMapping.pGBankCurrencyMapping.currencyCodeAlpha));
          getTransactionListReportStatus(reportList, statementReports, txnDto, tuple);
        }
      }

    } catch (Exception e) {
      log.error("TransactionDaoImpl | getTransactionListReport | Exception ", e);
    }
    log.info("TransactionDaoImpl | getTransactionListReport | Exiting");
    return reportList;
  }

  private void getTransactionListReportStatus(List<ReportsDTO> reportList,
      ReportsDTO statementReports, TransactionPopUpDataDto txnDto, Tuple tuple) {
    String statusMsg;
    if (0 == tuple.get(QPGTransaction.pGTransaction.status)) {
      statusMsg = Constants.APPROVED;
    } else if (1 == tuple.get(QPGTransaction.pGTransaction.status)) {
      statusMsg = Constants.DECLINED;
    } else {
      statusMsg = Constants.FAILED;
    }
    txnDto.setTransaction_type(
        ((tuple.get(QPGTransaction.pGTransaction.transactionType) != null)
            ? tuple.get(QPGTransaction.pGTransaction.transactionType) : ""));
    txnDto.setAuth_id(((tuple.get(QPGTransaction.pGTransaction.authId) != null)
        ? tuple.get(QPGTransaction.pGTransaction.authId) : ""));
    txnDto.setTransactionId(((tuple.get(QPGTransaction.pGTransaction.transactionId) != null)
        ? tuple.get(QPGTransaction.pGTransaction.transactionId) : ""));
    txnDto.setRef_transaction_id(
        ((tuple.get(QPGTransaction.pGTransaction.refTransactionId) != null)
            ? tuple.get(QPGTransaction.pGTransaction.refTransactionId) : "N/A"));
    txnDto.setTerminal_id(((tuple.get(QPGTransaction.pGTransaction.terminalId) != null)
        ? tuple.get(QPGTransaction.pGTransaction.terminalId) : ""));
    txnDto.setInvoice_number(((tuple.get(QPGTransaction.pGTransaction.invoiceNumber) != null)
        ? tuple.get(QPGTransaction.pGTransaction.invoiceNumber) : ""));
    txnDto.setMaskCardNumber(((StringUtils.lastFourDigits(tuple.get(QPGTransaction.pGTransaction.panMasked)) != null)
        ? StringUtils.lastFourDigits(tuple.get(QPGTransaction.pGTransaction.panMasked)) : ""));
    txnDto.setMerchant_code(((tuple.get(QPGTransaction.pGTransaction.merchantId) != null)
        ? tuple.get(QPGTransaction.pGTransaction.merchantId) : ""));
    getTransactionListReportExisting(txnDto, tuple);
    txnDto.setStatusMessage(statusMsg);
    statementReports.setTxnPopupDto(txnDto);

    reportList.add(statementReports);
  }

  private void getTransactionListReportExisting(TransactionPopUpDataDto txnDto, Tuple tuple) {
    txnDto.setAcqTxnMode(((tuple.get(QPGTransaction.pGTransaction.acqTxnMode) != null)
        ? tuple.get(QPGTransaction.pGTransaction.acqTxnMode) : ""));
    txnDto.setAcqChannel(((tuple.get(QPGTransaction.pGTransaction.acqChannel) != null)
        ? tuple.get(QPGTransaction.pGTransaction.acqChannel) : ""));
    txnDto.setTransactionDate(DateUtil.toDateStringFormat(
        tuple.get(QPGTransaction.pGTransaction.createdDate), DateUtil.VIEW_DATE_TIME_FORMAT));
    txnDto.setTransactionAmount(
        (tuple.get(QPGTransaction.pGTransaction.txnAmount) != null) ? StringUtils
            .amountToString(pGTransactionTxnAmount(tuple))
            : "0.00");
    txnDto.setFee_amount(
        (tuple.get(QPGTransaction.pGTransaction.feeAmount) != null) ? StringUtils
            .amountToString(pGTransactionFeeAmount(tuple))
            : "0.00");
    txnDto
        .setTxn_total_amount(
            (tuple.get(QPGTransaction.pGTransaction.txnTotalAmount) != null)
                ? StringUtils.amountToString(
                    pGTransactionTxnTotalAmount(tuple))
                : "0.00");
    txnDto.setProcessor(((tuple.get(QPGTransaction.pGTransaction.processor) != null)
        ? tuple.get(QPGTransaction.pGTransaction.processor) : ""));
    txnDto.setMerchantSettlementStatus(
        ((tuple.get(QPGTransaction.pGTransaction.merchantSettlementStatus) != null)
            ? tuple.get(QPGTransaction.pGTransaction.merchantSettlementStatus) : ""));
    txnDto.setTxnDescription(((tuple.get(QPGTransaction.pGTransaction.txnDescription) != null)
        ? tuple.get(QPGTransaction.pGTransaction.txnDescription) : ""));
    txnDto.setCardHolderName(tuple.get(QPGTransaction.pGTransaction.cardHolderName) != null
        ? tuple.get(QPGTransaction.pGTransaction.cardHolderName) : "");
    txnDto.setMerchantType(tuple.get(QPGMerchant.pGMerchant.merchantType) != null
        ? tuple.get(QPGMerchant.pGMerchant.merchantType) : "");
    txnDto.setMerchantBusinessName(tuple.get(QPGMerchant.pGMerchant.businessName) != null
        ? tuple.get(QPGMerchant.pGMerchant.businessName) : "");
  }

  @Override
  public List<ReportsDTO> getAllTransactionsOnDate(
      GetTransactionsListRequest getTransactionsListRequest) {
    List<ReportsDTO> reportList = null;
    ReportsDTO transactionsReports = null;
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
            QPGOnlineTxnLog.pGOnlineTxnLog)
        .where(QPGMerchant.pGMerchant.merchantCode
            .eq(QPGTransaction.pGTransaction.merchantId.stringValue())
            .and(QPGTransaction.pGTransaction.transactionId
                .eq(QPGOnlineTxnLog.pGOnlineTxnLog.pgTxnId.stringValue()))
            .and(isValidDate(startDate, endDate))
            .and(QPGAccount.pGAccount.entityId.eq(QPGMerchant.pGMerchant.merchantCode)))
        .orderBy(orderByCreatedDateDesc()).list(QPGMerchant.pGMerchant.userName,
            QPGMerchant.pGMerchant.businessName, QPGAccount.pGAccount.accountNum,
            QPGAccount.pGAccount.entityType, QPGAccount.pGAccount.currency,
            QPGTransaction.pGTransaction.txnTotalAmount,
            QPGTransaction.pGTransaction.txnDescription, QPGTransaction.pGTransaction.paymentMethod,
            QPGTransaction.pGTransaction.transactionId, QPGTransaction.pGTransaction.createdDate,
            QPGTransaction.pGTransaction.transactionType, QPGTransaction.pGTransaction.authId,
            QPGTransaction.pGTransaction.refTransactionId, QPGTransaction.pGTransaction.terminalId,
            QPGTransaction.pGTransaction.invoiceNumber, QPGTransaction.pGTransaction.panMasked,
            QPGTransaction.pGTransaction.merchantId, QPGTransaction.pGTransaction.acqChannel,
            QPGTransaction.pGTransaction.acqTxnMode, QPGTransaction.pGTransaction.txnAmount,
            QPGTransaction.pGTransaction.feeAmount, QPGTransaction.pGTransaction.processor,
            QPGTransaction.pGTransaction.status,
            QPGTransaction.pGTransaction.merchantSettlementStatus,
            QPGTransaction.pGTransaction.txnMode);

    if (StringUtil.isListNotNullNEmpty(infoList)) {
      reportList = new ArrayList<>();
      TransactionPopUpDataDto txnDto = null;
      for (Tuple tuple : infoList) {
        transactionsReports = new ReportsDTO();
        txnDto = new TransactionPopUpDataDto();
        transactionsReports.setUserName(tuple.get(QPGMerchant.pGMerchant.userName));
        transactionsReports.setAccountNumber(tuple.get(QPGAccount.pGAccount.accountNum));
        transactionsReports.setCompanyName(tuple.get(QPGMerchant.pGMerchant.businessName));
        transactionsReports.setAccountType(tuple.get(QPGAccount.pGAccount.entityType));
        transactionsReports.setAmount(PGConstants.DOLLAR_SYMBOL
                + StringUtils.amountToString(tuple.get(QPGTransaction.pGTransaction.txnTotalAmount)));
        transactionsReports.setCurrency(tuple.get(QPGAccount.pGAccount.currency));
        transactionsReports.setDescription(tuple.get(QPGTransaction.pGTransaction.txnDescription));
        transactionsReports.setTransactionId(tuple.get(QPGTransaction.pGTransaction.transactionId));
        transactionsReports.setPaymentMethod(tuple.get(QPGTransaction.pGTransaction.paymentMethod));
        transactionsReports.setDateTime(DateUtil.toDateStringFormat(
            tuple.get(QPGTransaction.pGTransaction.createdDate), DateUtil.VIEW_DATE_TIME_FORMAT));
        statusMsgValidation(reportList, transactionsReports, txnDto, tuple);
      }
    }
    return reportList;
  }

private void statusMsgValidation(List<ReportsDTO> reportList, ReportsDTO transactionsReports, TransactionPopUpDataDto txnDto,
		Tuple tuple) {
	String statusMsg;
	if (0 == tuple.get(QPGTransaction.pGTransaction.status)) {
	  statusMsg = Constants.APPROVED;
	} else if (1 == tuple.get(QPGTransaction.pGTransaction.status)) {
	  statusMsg = Constants.DECLINED;
	} else {
	  statusMsg = Constants.FAILED;
	}
	getAllTransactionsReportStatusMsg(txnDto, statusMsg, tuple);
	transactionsReports.setTxnPopupDto(txnDto);

	reportList.add(transactionsReports);
}

  @Override
  public List<ReportsDTO> getVirtualAccountFeeLogOnDate(
      GetTransactionsListRequest getTransactionsListRequest) {
    List<ReportsDTO> reportList = null;
    ReportsDTO transactionsReports = null;
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
    List<Tuple> infoList =
        query.distinct().from(QPGAccountFeeLog.pGAccountFeeLog, QPGTransaction.pGTransaction)
            .where((QPGAccountFeeLog.pGAccountFeeLog.transactionId
                .eq(QPGTransaction.pGTransaction.transactionId))
                    .and(isValidDateFromVirtualAcc(startDate, endDate)).and(isStatusLike()))
        .orderBy(orderByCreatedDateDescVirtualAcc())
        .list(QPGAccountFeeLog.pGAccountFeeLog.transactionId,
            QPGAccountFeeLog.pGAccountFeeLog.paymentMethod,
            QPGAccountFeeLog.pGAccountFeeLog.merchantFee,
            QPGAccountFeeLog.pGAccountFeeLog.chatakFee,
            QPGAccountFeeLog.pGAccountFeeLog.issuanceFeeTxnId,
            QPGAccountFeeLog.pGAccountFeeLog.issuanceMessage,
            QPGAccountFeeLog.pGAccountFeeLog.createdDate,
            QPGAccountFeeLog.pGAccountFeeLog.feePostStatus,
            QPGAccountFeeLog.pGAccountFeeLog.txnAmount, QPGTransaction.pGTransaction.merchantId,
            QPGTransaction.pGTransaction.transactionType, QPGTransaction.pGTransaction.authId,
            QPGTransaction.pGTransaction.transactionId,
            QPGTransaction.pGTransaction.refTransactionId, QPGTransaction.pGTransaction.terminalId,
            QPGTransaction.pGTransaction.invoiceNumber, QPGTransaction.pGTransaction.panMasked,
            QPGTransaction.pGTransaction.acqChannel, QPGTransaction.pGTransaction.acqTxnMode,
            QPGTransaction.pGTransaction.createdDate, QPGTransaction.pGTransaction.txnAmount,
            QPGTransaction.pGTransaction.txnTotalAmount, QPGTransaction.pGTransaction.txnMode,
            QPGTransaction.pGTransaction.txnDescription,
            QPGTransaction.pGTransaction.merchantSettlementStatus,
            QPGTransaction.pGTransaction.feeAmount, QPGTransaction.pGTransaction.status,
            QPGTransaction.pGTransaction.processor);

    if (StringUtil.isListNotNullNEmpty(infoList)) {
      reportList = new ArrayList<>();
      TransactionPopUpDataDto txnDto = null;
      String statusMsg = null;
      Map<String, String> merchantMap = merchantDao.getAllMerchantMap();
      for (Tuple tuple : infoList) {
        transactionsReports = new ReportsDTO();
        txnDto = new TransactionPopUpDataDto();
        transactionsReports
            .setTransactionId(tuple.get(QPGAccountFeeLog.pGAccountFeeLog.transactionId));
        transactionsReports
            .setTransactionType(tuple.get(QPGAccountFeeLog.pGAccountFeeLog.paymentMethod));
        transactionsReports.setMerchantCode(tuple.get(QPGTransaction.pGTransaction.merchantId));
        transactionsReports.setFee(PGConstants.DOLLAR_SYMBOL
            + StringUtils.amountToString(tuple.get(QPGAccountFeeLog.pGAccountFeeLog.merchantFee)));
        transactionsReports.setChatakFee(PGConstants.DOLLAR_SYMBOL
            + StringUtils.amountToString(tuple.get(QPGAccountFeeLog.pGAccountFeeLog.chatakFee)));
        transactionsReports
            .setIssuanceFeeTxnId(tuple.get(QPGAccountFeeLog.pGAccountFeeLog.issuanceFeeTxnId));
        transactionsReports
            .setDescription(tuple.get(QPGAccountFeeLog.pGAccountFeeLog.issuanceMessage));
        transactionsReports
            .setStatus(tuple.get(QPGAccountFeeLog.pGAccountFeeLog.feePostStatus).toString());
        transactionsReports.setDateTime(DateUtil.toDateStringFormat(
            tuple.get(QPGAccountFeeLog.pGAccountFeeLog.createdDate), DateUtil.VIEW_DATE_TIME_FORMAT));
        transactionsReports.setAmount(PGConstants.DOLLAR_SYMBOL
            + StringUtils.amountToString(tuple.get(QPGAccountFeeLog.pGAccountFeeLog.txnAmount)));
        transactionsReports.setCompanyName(merchantMap.get(transactionsReports.getMerchantCode()));
        statusMsgValidation(reportList, transactionsReports, txnDto, tuple);
      }
    }
    return reportList;
  }

  private Integer getTotalNumberOfRecords(GetTransactionsListRequest getTransactionsListRequest) {
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
    List<BigInteger> list = query.from(QPGTransaction.pGTransaction)
        .where(isMerchantId(getTransactionsListRequest.getMerchant_code()),
            isTxnId(getTransactionsListRequest.getTransactionId()),
            isProcessTxnId(getTransactionsListRequest.getProcessCode()),
            isCardNumberLike(getTransactionsListRequest.getCardNumber()),
            isTxnTypeLike(getTransactionsListRequest.getTransaction_type()),
            isTxnStatusLike(getTransactionsListRequest.getStatus()),
            isEntryModeLike(getTransactionsListRequest.getEntryMode()),
            isAcqChannel(getTransactionsListRequest.getAcqChannel()),
            isValidTxn(getTransactionsListRequest.getFromAmtRange(),
                getTransactionsListRequest.getToAmtRange()),
        isMerchantNameLike(getTransactionsListRequest.getMerchantName()),
        isValidDate(startDate, endDate)).list(QPGTransaction.pGTransaction.id);

    return (StringUtils.isListNotNullNEmpty(list) ? list.size() : 0);
  }

  private BooleanExpression isValidTransactionAcqChannel(String acqChannel) {
    return (null != acqChannel ? QPGTransaction.pGTransaction.acqChannel.eq(acqChannel) : null);
  }

  private BooleanExpression isValidDateFromVirtualAcc(Timestamp fromDate, Timestamp toDate) {
    if ((fromDate != null && toDate == null)) {
      return QPGAccountFeeLog.pGAccountFeeLog.feeTxnDate.gt(fromDate);
    } else if ((fromDate == null && toDate != null)) {
      return QPGAccountFeeLog.pGAccountFeeLog.feeTxnDate.lt(toDate);
    } else if ((fromDate == null)) {
      return null;
    }
    else {
    return QPGAccountFeeLog.pGAccountFeeLog.feeTxnDate.between(fromDate, toDate);
    }
  }

  private OrderSpecifier<Timestamp> orderByCreatedDateDescVirtualAcc() {
    return QPGAccountFeeLog.pGAccountFeeLog.createdDate.desc();
  }

  private BooleanExpression isStatusLike() {
    return QPGAccountFeeLog.pGAccountFeeLog.feePostStatus.notIn(1)
        .and(QPGAccountFeeLog.pGAccountFeeLog.feePostStatus.isNotNull());
  }

  @Override
  public PGTransaction getAuthTransaction(String merchantId, String terminalId, String txnId,
      String txnType, String authId) {
    log.debug("TransactionDaoImpl | getAuthTransaction | Entering");
    PGTransaction transaction = null;
    List<PGTransaction> pgTxnlist = transactionRepository
        .findByMerchantIdAndTerminalIdAndTransactionIdAndTransactionTypeAndAuthId(merchantId,
            terminalId, txnId, txnType, authId);
    transaction = getPGTransaction(pgTxnlist);
    log.debug("TransactionDaoImpl | getAuthTransaction | Exiting");
    return transaction;
  }

  @Override
  public PGTransaction findDuplicateTransactionOnPanAndInvoiceNumberAndMerchantIdAndTerminalIdAndTxnAmount(
      String pan, String invoiceNumber, String merchantId, String terminalId, Long txnAmount) {
    log.debug(
        "TransactionDaoImpl | findDuplicateTransactionOnPanAndInvoiceNumberAndMerchantIdAndTerminalIdAndTxnAmount | Entering");
    PGTransaction transaction = null;
    List<PGTransaction> pgTxnlist =
        transactionRepository.findByMerchantIdAndTerminalIdAndInvoiceNumberAndTxnAmountAndPan(
            merchantId, terminalId, invoiceNumber, txnAmount, pan);
    transaction = getPGTransaction(pgTxnlist);
    log.debug(
        "TransactionDaoImpl | findDuplicateTransactionOnPanAndInvoiceNumberAndMerchantIdAndTerminalIdAndTxnAmount | Exiting");
    return transaction;
  }
}
