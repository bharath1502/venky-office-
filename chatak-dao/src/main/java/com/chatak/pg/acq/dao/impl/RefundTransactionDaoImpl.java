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

import com.chatak.pg.acq.dao.AccountTransactionsDao;
import com.chatak.pg.acq.dao.RefundTransactionDao;
import com.chatak.pg.acq.dao.SubMerchantDao;
import com.chatak.pg.acq.dao.model.PGAccountTransactions;
import com.chatak.pg.acq.dao.model.PGMerchant;
import com.chatak.pg.acq.dao.model.PGTransaction;
import com.chatak.pg.acq.dao.model.QPGAccount;
import com.chatak.pg.acq.dao.model.QPGBankCurrencyMapping;
import com.chatak.pg.acq.dao.model.QPGCurrencyConfig;
import com.chatak.pg.acq.dao.model.QPGMerchant;
import com.chatak.pg.acq.dao.model.QPGTransaction;
import com.chatak.pg.acq.dao.repository.AccountTransactionsRepository;
import com.chatak.pg.acq.dao.repository.MerchantRepository;
import com.chatak.pg.acq.dao.repository.TransactionRepository;
import com.chatak.pg.bean.TransactionPopUpDataDto;
import com.chatak.pg.constants.PGConstants;
import com.chatak.pg.dao.util.StringUtil;
import com.chatak.pg.enums.EntryModePortalDisplayEnum;
import com.chatak.pg.model.EFTRefTxnData;
import com.chatak.pg.model.TransactionHistoryRequest;
import com.chatak.pg.user.bean.GetTransactionsListRequest;
import com.chatak.pg.user.bean.Transaction;
import com.chatak.pg.user.bean.TransactionHistory;
import com.chatak.pg.util.CommonUtil;
import com.chatak.pg.util.Constants;
import com.chatak.pg.util.DateUtil;
import com.chatak.pg.util.StringUtils;
import com.mysema.query.Tuple;
import com.mysema.query.jpa.impl.JPAQuery;
import com.mysema.query.types.expr.BooleanExpression;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

/**
 * @Author: Girmiti Software
 * @Date: Aug 23, 2017
 * @Time: 6:27:55 PM
 * @Version: 1.0
 * @Comments: 
 *
 */

@Repository("refundTransactionDao")
public class RefundTransactionDaoImpl extends TransactionDaoImpl implements RefundTransactionDao {

  private static Logger log = Logger.getLogger(RefundTransactionDaoImpl.class);

  @PersistenceContext
  private EntityManager entityManager;

  @Autowired
  private AccountTransactionsDao accountTransactionsDao;

  @Autowired
  private MerchantRepository merchantRepository;

  @Autowired
  private SubMerchantDao subMerchantDao;

  @Autowired
  private TransactionRepository transactionRepository;

  @Autowired
  private AccountTransactionsRepository accountTransactionsRepository;

  @Override
  public Integer getRefundStatus(String pgTransactionId) {
    PGTransaction pgTransaction =
        transactionRepository.findById(new BigInteger(pgTransactionId));
    return pgTransaction.getRefundStatus();
  }

  @Override
  public List<PGTransaction> findByMerchantIdAndTerminalIdAndTransactionIdAndStatusAndMerchantSettlementStatusInAndRefundStatusNotLike(
      String merchantId, String terminalId, String refId, Integer status, Integer refundStatus,
      List<String> merchantSettlementStatus) {
    return Collections.emptyList();
  }

  @Override
  public Long getRefundedAmountOnTxnId(String pgTransactionId) {
    return transactionRepository.getRefundedAmountByPGTxnId(pgTransactionId);
  }

  @Override
  public PGTransaction getTransactionForVoidOrRefundByAccountTransactionId(
      String accountTransactionId, String merchantCode) {
    String pgTransactionId =
        accountTransactionsDao.getSaleAccountTransactionId(accountTransactionId, merchantCode);
    if (null != pgTransactionId) {
    	return transactionRepository.findById(new BigInteger(pgTransactionId));
    }
    return null;
  }

  @Override
  public PGTransaction findTransactionToRefundByPGTxnIdAndIssuerTxnIdAndMerchantId(
      String transactionId, String issuerTxnRefNum, String merchantId) {
    log.debug(
        "TransactionDaoImpl | findTransactionToRefundByPGTxnIdAndIssuerTxnIdAndMerchantId | Entering");
    PGTransaction transaction = null;
    List<PGTransaction> pgTxnlist =
        transactionRepository.findTransactionToRefundByPGTxnIdAndIssuerTxnIdAndMerchantId(
           new BigInteger(transactionId), issuerTxnRefNum, merchantId);
    transaction = getPGTransaction(pgTxnlist);
    log.debug(
        "TransactionDaoImpl | findTransactionToRefundByPGTxnIdAndIssuerTxnIdAndMerchantId | Exiting");
    return transaction;
  }

  @Override
  public PGTransaction getTransactionToRefund(String merchantId, String terminalId, String txnId) {
    log.debug("TransactionDaoImpl | getTransactionToRefund | Entering");
    PGTransaction transaction = null;
    List<PGTransaction> pgTxnlist =
        transactionRepository.findBySaleTransactionType(merchantId, terminalId, txnId, "sale");
    transaction = getPGTransaction(pgTxnlist);
    log.debug("TransactionDaoImpl | getTransactionToRefund | Exiting");
    return transaction;
  }

  @Override
  public PGTransaction findRefundTransactionToVoidByPGTxnIdAndIssuerTxnIdAndMerchantIdAndTerminalId(
      String transactionId, String issuerTxnRefNum, String merchantId, String terminalId) {
    log.debug(
        "TransactionDaoImpl | findRefundTransactionToVoidByPGTxnIdAndIssuerTxnIdAndMerchantIdAndTerminalId | Entering");
    PGTransaction transaction = null;
    List<PGTransaction> pgTxnlist = transactionRepository
        .findRefundTransactionToVoidByPGTxnIdAndIssuerTxnIdAndMerchantIdAndTerminalId(transactionId,
            issuerTxnRefNum, merchantId, terminalId);
    transaction = getPGTransaction(pgTxnlist);
    log.debug(
        "TransactionDaoImpl | findRefundTransactionToVoidByPGTxnIdAndIssuerTxnIdAndMerchantIdAndTerminalId | Exiting");
    return transaction;
  }

  @Override
  public PGTransaction findTransactionToRefundByPGTxnIdAndMerchantIdAndTerminalId(
      String transactionId, String merchantId, String terminalId) {
    log.debug(
        "TransactionDaoImpl | findTransactionToRefundByPGTxnIdAndMerchantIdAndTerminalId | Entering");
    PGTransaction transaction = null;
    List<PGTransaction> pgTxnlist =
        transactionRepository.findTransactionToRefundByPGTxnIdAndMerchantIdAndTerminalId(
            transactionId, merchantId, terminalId);
    transaction = getPGTransaction(pgTxnlist);
    log.debug(
        "TransactionDaoImpl | findTransactionToRefundByPGTxnIdAndMerchantIdAndTerminalId | Exiting");
    return transaction;
  }

  @Override
  public Long findMerchantFeeByMerchantId(String merchantId) {
    Long totalCreditedFeeAmount = transactionRepository.getMerchantFeeByMerchantId(merchantId);
    Long totalDebitedFeeAmount =
        accountTransactionsRepository.getMerchantDebitedFeeByMerchantId(merchantId);
    if (totalCreditedFeeAmount == null || totalDebitedFeeAmount == null) {
      return 0l;
    }
    return (totalCreditedFeeAmount - totalDebitedFeeAmount);
  }

  @Override
  public String getBatchidOnEFTId(String eftId) {
    return null;
  }

  @Override
  public boolean isDuplicateBatchId(String batchId) {
    return true;
  }

  @Override
  public String generateBatchId() {
    return null;
  }


  @Override
  public TransactionPopUpDataDto getTransactionPopUpDataDto(String pgTransactionId) {
    TransactionPopUpDataDto txnDto = null;

    List<PGAccountTransactions> pgAccountTransactions =
        accountTransactionsDao.getAccountTransactionsOnTransactionId(pgTransactionId);
    //Extra code for Currency start
    PGMerchant pgMerchant =
        merchantRepository.findByMerchantCode(pgAccountTransactions.get(0).getMerchantCode());
    String currencyCodeAlpha = pgMerchant.getLocalCurrency();
    JPAQuery query = new JPAQuery(entityManager);
    List<Tuple> tupleList =
        query.from(QPGTransaction.pGTransaction, QPGMerchant.pGMerchant)
            .where(QPGTransaction.pGTransaction.id.eq(new BigInteger(pgTransactionId))
                .and(QPGTransaction.pGTransaction.merchantId
                    .eq(QPGMerchant.pGMerchant.merchantCode)))
        .list(QPGTransaction.pGTransaction.merchantId, QPGTransaction.pGTransaction.id,
            QPGTransaction.pGTransaction.issuerTxnRefNum, QPGTransaction.pGTransaction.procCode,
            QPGTransaction.pGTransaction.panMasked, QPGTransaction.pGTransaction.createdDate,
            QPGTransaction.pGTransaction.transactionType, QPGTransaction.pGTransaction.txnAmount,
            QPGTransaction.pGTransaction.feeAmount, QPGTransaction.pGTransaction.merchantFeeAmount,
            QPGTransaction.pGTransaction.txnDescription, QPGTransaction.pGTransaction.status,
            QPGTransaction.pGTransaction.merchantSettlementStatus,
            QPGTransaction.pGTransaction.terminalId, QPGTransaction.pGTransaction.feeAmount,
            QPGTransaction.pGTransaction.posEntryMode, QPGTransaction.pGTransaction.acqChannel,
            QPGTransaction.pGTransaction.cardHolderName, QPGTransaction.pGTransaction.updatedDate,
            QPGTransaction.pGTransaction.authId, QPGTransaction.pGTransaction.invoiceNumber,
            QPGTransaction.pGTransaction.acqTxnMode, QPGTransaction.pGTransaction.txnTotalAmount,
            QPGTransaction.pGTransaction.processor, QPGTransaction.pGTransaction.txnMode,
            QPGTransaction.pGTransaction.refTransactionId, QPGMerchant.pGMerchant.merchantType,
            QPGMerchant.pGMerchant.businessName, QPGTransaction.pGTransaction.deviceLocalTxnTime,
            QPGTransaction.pGTransaction.timeZoneOffset,QPGTransaction.pGTransaction.batchId);
    if (!CollectionUtils.isEmpty(tupleList)) {
      Tuple tuple = tupleList.get(0);
      txnDto = new TransactionPopUpDataDto();
      transactionTupleList(txnDto, tuple);
      txnDto
          .setTransactionAmount(
              (tuple.get(QPGTransaction.pGTransaction.txnAmount) != null)
                  ? currencyCodeAlpha + " "
                      + StringUtils
                          .amountToString(tuple.get(QPGTransaction.pGTransaction.txnAmount))
                  : currencyCodeAlpha + ":" + "0.00");
      txnDto
          .setFee_amount(
              (tuple.get(QPGTransaction.pGTransaction.feeAmount) != null)
                  ? currencyCodeAlpha + " "
                      + StringUtils
                          .amountToString(tuple.get(QPGTransaction.pGTransaction.feeAmount))
                  : currencyCodeAlpha + ":" + "0.00");
      txnDto
          .setTxn_total_amount((tuple.get(QPGTransaction.pGTransaction.txnTotalAmount) != null)
              ? currencyCodeAlpha + " "
                  + StringUtils
                      .amountToString(tuple.get(QPGTransaction.pGTransaction.txnTotalAmount))
              : currencyCodeAlpha + ":" + "0.00");
      txnDto.setProcessor(((tuple.get(QPGTransaction.pGTransaction.processor) != null)
          ? tuple.get(QPGTransaction.pGTransaction.processor) : ""));
	  txnDto.setDeviceLocalTxnTime(DateUtil
					.toDateStringFormat(DateUtil.toTimestamp(tuple.get(QPGTransaction.pGTransaction.deviceLocalTxnTime),
							Constants.HYPHEN_DATE_FORMAT), PGConstants.DATE_FORMAT));
      txnDto.setTimeZoneOffset(tuple.get(QPGTransaction.pGTransaction.timeZoneOffset));
      txnDto.setBatchId(tuple.get(QPGTransaction.pGTransaction.batchId));
      transactionStatusMessage(txnDto, tuple);
    }
    return txnDto;
  }

  private void transactionTupleList(TransactionPopUpDataDto txnDto, Tuple tuple) {
    txnDto.setTransaction_type(((tuple.get(QPGTransaction.pGTransaction.transactionType) != null)
        ? tuple.get(QPGTransaction.pGTransaction.transactionType) : ""));
    txnDto.setAuth_id((tuple.get(QPGTransaction.pGTransaction.authId) != null)
        ? tuple.get(QPGTransaction.pGTransaction.authId) : "");
    txnDto.setTransactionId(((tuple.get(QPGTransaction.pGTransaction.id) != null)
        ? tuple.get(QPGTransaction.pGTransaction.id).toString() : ""));
    txnDto
        .setRef_transaction_id(((tuple.get(QPGTransaction.pGTransaction.refTransactionId) != null)
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
    txnDto.setTransactionDate(((tuple.get(QPGTransaction.pGTransaction.createdDate) != null)
        ? DateUtil.toDateStringFormat(tuple.get(QPGTransaction.pGTransaction.createdDate),
            PGConstants.DATE_FORMAT)
        : ""));
    txnDto.setDeviceLocalTxnTime(DateUtil.toDateStringFormat(
		  DateUtil.toTimestamp(tuple.get(QPGTransaction.pGTransaction.deviceLocalTxnTime),
					Constants.HYPHEN_DATE_FORMAT), PGConstants.DATE_FORMAT));
    txnDto.setTimeZoneOffset(tuple.get(QPGTransaction.pGTransaction.timeZoneOffset));
  }

  private void transactionStatusMessage(TransactionPopUpDataDto txnDto, Tuple tuple) {
    if (tuple.get(QPGTransaction.pGTransaction.status) == 0) {
      txnDto.setStatusMessage("Approved");
    } else if (tuple.get(QPGTransaction.pGTransaction.status) == 1) {
      txnDto.setStatusMessage("Declined");
    } else {
      txnDto.setStatusMessage("Failed");
    }
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
    txnDto.setDtoType(Constants.TXN_CC);
  }

  @Override
  public List<Transaction> getMerchantAndItsSubMerchantTransactionList(
      GetTransactionsListRequest getTransactionsListRequest) {
    log.info("TransactionDaoImpl | getMerchantAndItsSubMerchantTransactionList | Entering");

    List<Transaction> transactions = null;
    try {
      int offset = 0;
      int limit = 0;
      List<String> merchantList = null;
      if (getTransactionsListRequest.isSubMerchantsTxnsRequired()) {
        merchantList = subMerchantDao
            .getMerchantAndSubMerchantList(getTransactionsListRequest.getMerchant_code());
      } else {
        merchantList = new ArrayList<>();
        merchantList.add(getTransactionsListRequest.getMerchant_code());
      }
      Integer totalRecords = getTransactionsListRequest.getNoOfRecords();

      if (getTransactionsListRequest.getPageIndex() == null
          || getTransactionsListRequest.getPageIndex().intValue() == 1) {
        totalRecords =
            getTotalNumberOfMerchantAndItsSubMerchantTransactionRecords(getTransactionsListRequest);
        getTransactionsListRequest.setNoOfRecords(totalRecords);
      }
      getTransactionsListRequest.setNoOfRecords(totalRecords);
      if (getTransactionsListRequest.getPageSize() == null
          || getTransactionsListRequest.getPageIndex() == null) {
        offset = 0;
        limit = Constants.DEFAULT_PAGE_SIZE;
      } else {
    	  limit = getTransactionsListRequest.getPageSize();
        offset = (getTransactionsListRequest.getPageIndex() - 1)
            * getTransactionsListRequest.getPageSize();
      }

      Timestamp startDate = null;
      if (!CommonUtil.isNullAndEmpty(getTransactionsListRequest.getFrom_date())) {
        startDate = DateUtil.getStartDayTimestamp(getTransactionsListRequest.getFrom_date(),
            PGConstants.DD_MM_YYYY);
      }
      Timestamp endDate = null;
      if (!CommonUtil.isNullAndEmpty(getTransactionsListRequest.getTo_date())) {
          endDate = DateUtil.getEndDayTimestamp(getTransactionsListRequest.getTo_date(),
              PGConstants.DD_MM_YYYY);
        }

      JPAQuery query = new JPAQuery(entityManager);
      List<Tuple> tupleList = query
          .from(QPGTransaction.pGTransaction, QPGMerchant.pGMerchant, QPGAccount.pGAccount,
              QPGBankCurrencyMapping.pGBankCurrencyMapping, QPGCurrencyConfig.pGCurrencyConfig)
          .where(isMerchantIdorParentMerchantId(merchantList),
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
              QPGAccount.pGAccount.entityId.eq(QPGTransaction.pGTransaction.merchantId),
              QPGMerchant.pGMerchant.bankId.eq(QPGBankCurrencyMapping.pGBankCurrencyMapping.bankId),
              QPGBankCurrencyMapping.pGBankCurrencyMapping.currencyCodeAlpha
                  .eq(QPGCurrencyConfig.pGCurrencyConfig.currencyCodeAlpha),
              isMerchantNameLike(getTransactionsListRequest.getMerchantName()),
              isValidDate(startDate, endDate),
              toGetCurrentDayTxns(getTransactionsListRequest.isToGetCurrentDayTxns()))
          .offset(offset).limit(limit).distinct().orderBy(orderByCreatedDateDesc())
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
              QPGTransaction.pGTransaction.refTransactionId, QPGAccount.pGAccount.entityId,
              QPGAccount.pGAccount.entityType, QPGAccount.pGAccount.accountNum,
              QPGMerchant.pGMerchant.firstName, QPGMerchant.pGMerchant.businessName,
              QPGMerchant.pGMerchant.merchantType,
              QPGCurrencyConfig.pGCurrencyConfig.currencyCodeAlpha,
              QPGBankCurrencyMapping.pGBankCurrencyMapping.currencyCodeAlpha,
              QPGMerchant.pGMerchant.localCurrency, QPGTransaction.pGTransaction.userName,
              QPGTransaction.pGTransaction.deviceLocalTxnTime,
              QPGTransaction.pGTransaction.timeZoneOffset);
      if (!CollectionUtils.isEmpty(tupleList)) {
        transactions = new ArrayList<>(tupleList.size());
        for (Tuple tuple : tupleList) {
          Transaction transactionResp = null;
          TransactionPopUpDataDto txnDto = null;
          txnDto = new TransactionPopUpDataDto();
          transactionResp = new Transaction();
          subMerchantTupleList(tuple, transactionResp);

          subMerchantTransactionList(transactions, tuple, transactionResp, txnDto);
        }
      }
    } catch (Exception exception) {
      log.error("TransactionDaoImpl | getMerchantAndItsSubMerchantTransactionList | Exception ",
          exception);
    }
    log.info("TransactionDaoImpl | getMerchantAndItsSubMerchantTransactionList | Exiting");
    return transactions;
  }

  private void subMerchantTupleList(Tuple tuple, Transaction transactionResp) {
    transactionResp.setTransactionId(tuple.get(QPGTransaction.pGTransaction.id).toString());
    transactionResp.setTransactionAmount((StringUtils.amountToString(tuple.get(QPGTransaction.pGTransaction.txnAmount))));
    transactionResp.setTxn_total_amount(tuple.get(QPGTransaction.pGTransaction.txnTotalAmount).doubleValue()/Integer.parseInt("100"));
    transactionResp.setTransactionDate(DateUtil.toDateStringFormat(
        tuple.get(QPGTransaction.pGTransaction.createdDate), PGConstants.DATE_FORMAT));
    transactionResp
        .setTransaction_type(tuple.get(QPGTransaction.pGTransaction.transactionType));
    transactionResp.setMerchant_code(tuple.get(QPGTransaction.pGTransaction.merchantId));
    transactionResp.setTerminal_id(
        StringUtil.getLong(tuple.get(QPGTransaction.pGTransaction.terminalId)));
    transactionResp.setMaskCardNumber(StringUtils.lastFourDigits(tuple.get(QPGTransaction.pGTransaction.panMasked)));
    transactionResp.setProcTxnCode(tuple.get(QPGTransaction.pGTransaction.issuerTxnRefNum));
    transactionResp.setFee_amount(
        CommonUtil.getDoubleAmount(tuple.get(QPGTransaction.pGTransaction.feeAmount)) == null
            ? 0.00
            : CommonUtil.getDoubleAmount(tuple.get(QPGTransaction.pGTransaction.feeAmount)));
    transactionResp.setRef_transaction_id(
        (StringUtils.isEmpty(tuple.get(QPGTransaction.pGTransaction.issuerTxnRefNum)) ? null
            : Long.valueOf(tuple.get(QPGTransaction.pGTransaction.issuerTxnRefNum))));
    transactionResp.setId(tuple.get(QPGTransaction.pGTransaction.id));
    transactionResp
        .setMerchantFeeAmount(tuple.get(QPGTransaction.pGTransaction.merchantFeeAmount));
    transactionResp.setTxnDescription(tuple.get(QPGTransaction.pGTransaction.txnDescription));
    transactionResp.setMerchantSettlementStatus(
        tuple.get(QPGTransaction.pGTransaction.merchantSettlementStatus));
    transactionResp.setAcqChannel(tuple.get(QPGTransaction.pGTransaction.acqChannel));
    if (null != tuple.get(QPGTransaction.pGTransaction.posEntryMode)) {
      transactionResp
          .setEntryMode(EntryModePortalDisplayEnum.valueOf(getEntryModeEnumFromPosEntryMode(
              tuple.get(QPGTransaction.pGTransaction.posEntryMode))).value());
    } else {
      transactionResp.setEntryMode(EntryModePortalDisplayEnum
          .valueOf(getEntryModeEnumFromPosEntryMode("000")).value());
    }
    transactionResp.setUserName(tuple.get(QPGTransaction.pGTransaction.userName));
  }

  private void subMerchantTransactionList(List<Transaction> transactions, Tuple tuple,
      Transaction transactionResp, TransactionPopUpDataDto txnDto) {
    if (tuple.get(QPGTransaction.pGTransaction.status) == 0) {
      transactionResp.setStatus("Approved");
    } else if (tuple.get(QPGTransaction.pGTransaction.status) == 1) {
      transactionResp.setStatus("Declined");
    } else {
      transactionResp.setStatus("Failed");
    }
    transactionResp.setAccountNumber((tuple.get(QPGAccount.pGAccount.accountNum)));
    transactionResp.setMerchantType((tuple.get(QPGMerchant.pGMerchant.merchantType)));
    transactionResp.setMerchantName((tuple.get(QPGMerchant.pGMerchant.firstName)));
    transactionResp.setLocalCurrency(tuple.get(QPGMerchant.pGMerchant.localCurrency));
    transactionResp.setMerchantBusinessName(tuple.get(QPGMerchant.pGMerchant.businessName));
    transactionTupleList(txnDto, tuple);
    txnDto
        .setTransactionAmount((tuple.get(QPGTransaction.pGTransaction.txnAmount) != null)
            ? tuple.get(QPGCurrencyConfig.pGCurrencyConfig.currencyCodeAlpha) + " "
                + StringUtils
                    .amountToString(tuple.get(QPGTransaction.pGTransaction.txnAmount))
            : tuple.get(QPGCurrencyConfig.pGCurrencyConfig.currencyCodeAlpha) + ":" + "0.00");
    txnDto
        .setFee_amount((tuple.get(QPGTransaction.pGTransaction.feeAmount) != null)
            ? tuple.get(QPGCurrencyConfig.pGCurrencyConfig.currencyCodeAlpha) + " "
                + StringUtils
                    .amountToString(tuple.get(QPGTransaction.pGTransaction.feeAmount))
            : tuple.get(QPGCurrencyConfig.pGCurrencyConfig.currencyCodeAlpha) + ":" + "0.00");
    txnDto
        .setTxn_total_amount(
            (tuple.get(QPGTransaction.pGTransaction.txnTotalAmount) != null)
                ? tuple.get(QPGCurrencyConfig.pGCurrencyConfig.currencyCodeAlpha) + " "
                    + StringUtils.amountToString(
                        tuple.get(QPGTransaction.pGTransaction.txnTotalAmount))
                : tuple.get(QPGCurrencyConfig.pGCurrencyConfig.currencyCodeAlpha) + ":"
                    + "0.00");
    txnDto.setProcessor(((tuple.get(QPGTransaction.pGTransaction.processor) != null)
        ? tuple.get(QPGTransaction.pGTransaction.processor) : ""));
    txnDto.setStatus(transactionResp.getStatus());
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
    transactionResp.setTxnJsonString(convertObjectToJSON(txnDto));
	transactionResp.setDeviceLocalTxnTime(DateUtil.toDateStringFormat(DateUtil
			.toTimestamp(tuple.get(QPGTransaction.pGTransaction.deviceLocalTxnTime), 
					Constants.HYPHEN_DATE_FORMAT),PGConstants.DATE_FORMAT));
    transactionResp.setTimeZoneOffset(tuple.get(QPGTransaction.pGTransaction.timeZoneOffset));
    transactions.add(transactionResp);
  }

  private Integer getTotalNumberOfMerchantAndItsSubMerchantTransactionRecords(
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
    List<String> merchantList = null;
    if (getTransactionsListRequest.isSubMerchantsTxnsRequired()) {
      merchantList = subMerchantDao
          .getMerchantAndSubMerchantList(getTransactionsListRequest.getMerchant_code());
    } else {
      merchantList = new ArrayList<>();
      merchantList.add(getTransactionsListRequest.getMerchant_code());
    }
    JPAQuery query = new JPAQuery(entityManager);
    List<BigInteger> list =
        query.from(QPGTransaction.pGTransaction, QPGMerchant.pGMerchant, QPGAccount.pGAccount)
            .where(isMerchantIdorParentMerchantId(merchantList),
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
            isValidDate(startDate, endDate),
            toGetCurrentDayTxns(getTransactionsListRequest.isToGetCurrentDayTxns())).distinct()
        .list(QPGTransaction.pGTransaction.id);

    return (StringUtils.isListNotNullNEmpty(list) ? list.size() : 0);
  }

  private String convertObjectToJSON(Object object) {
    String input = "";
    ObjectWriter objectWriter = new ObjectMapper().writer().withDefaultPrettyPrinter();
    try {
      input = objectWriter.writeValueAsString(object);
      return input;
    } catch (Exception e) {
      log.error("Error :: TransactionDaoImpl :: convertObjectToJSON Method", e);
    }
    return input;
  }

  @Override
  public List<EFTRefTxnData> getEFTRefTxnDataList(String eftId) {
    JPAQuery query = new JPAQuery(entityManager);
    List<EFTRefTxnData> transactionIdList = null;
    EFTRefTxnData eftRefTxnData = null;
    List<Tuple> list = query.from(QPGTransaction.pGTransaction)
        .where(QPGTransaction.pGTransaction.eftId.eq(eftId)).orderBy(orderByCreatedDateDesc())
        .list(QPGTransaction.pGTransaction.transactionId, QPGTransaction.pGTransaction.createdDate,
            QPGTransaction.pGTransaction.panMasked, QPGTransaction.pGTransaction.merchantId,
            QPGTransaction.pGTransaction.txnTotalAmount);

    if (StringUtil.isListNotNullNEmpty(list)) {
      transactionIdList = new ArrayList<>();
      for (Tuple tuple : list) {
        eftRefTxnData = new EFTRefTxnData();
        eftRefTxnData.setDateTime(DateUtil.toDateStringFormat(
            tuple.get(QPGTransaction.pGTransaction.createdDate), DateUtil.VIEW_DATE_TIME_FORMAT));
        eftRefTxnData.setMerchantCode(tuple.get(QPGTransaction.pGTransaction.merchantId));
        eftRefTxnData.setTransactionId(tuple.get(QPGTransaction.pGTransaction.transactionId));
        eftRefTxnData.setTxnAmount(PGConstants.DOLLAR_SYMBOL
            + StringUtils.amountToString(tuple.get(QPGTransaction.pGTransaction.txnTotalAmount)));
        eftRefTxnData.setMaskedCardNumber(StringUtils.lastFourDigits(tuple.get(QPGTransaction.pGTransaction.panMasked)));
        transactionIdList.add(eftRefTxnData);
      }
    }
    return transactionIdList;
  }

  @Override
  public List<String> getTransactionIdsOnEftIds(String eftId) {
    JPAQuery query = new JPAQuery(entityManager);
    return query.from(QPGTransaction.pGTransaction)
        .where(QPGTransaction.pGTransaction.eftId.eq(eftId)).orderBy(orderByCreatedDateDesc())
        .list(QPGTransaction.pGTransaction.transactionId);
  }

  @Override
  public PGTransaction getTransactionOnId(String id) {
	  return transactionRepository.findByTransactionId(id);
  }
  
  @Override
  protected BooleanExpression isMerchantIdorParentMerchantId(List<String> merchantCodeList) {
    return QPGTransaction.pGTransaction.merchantId.in(merchantCodeList);
  }
  
	@Override
	public List<TransactionHistory> getMerchantTransactionList(TransactionHistoryRequest transactionHistoryRequest) {
		log.info("TransactionDaoImpl | getMerchantTransactionList | Entering");
		List<TransactionHistory> transactions = null;
		try {
			Timestamp startDate = null;
			Timestamp endDate = null;
			if (!CommonUtil.isNullAndEmpty(transactionHistoryRequest.getTransactionDate())) {
				startDate = DateUtil.getStartDayTimestamp(transactionHistoryRequest.getTransactionDate(),
						PGConstants.DD_MM_YYYY);
			}
			if (!CommonUtil.isNullAndEmpty(transactionHistoryRequest.getTransactionDate())) {
				endDate = DateUtil.getEndDayTimestamp(transactionHistoryRequest.getTransactionDate(),
						PGConstants.DD_MM_YYYY);
			}
			JPAQuery query = new JPAQuery(entityManager);
			List<Tuple> tupleList = query
					.from(QPGTransaction.pGTransaction, QPGMerchant.pGMerchant, QPGAccount.pGAccount,
							QPGBankCurrencyMapping.pGBankCurrencyMapping, QPGCurrencyConfig.pGCurrencyConfig)
					.where(isTxnId(transactionHistoryRequest.getTransactionId()),
							isMerchantId(transactionHistoryRequest.getMerchantCode()),
							QPGTransaction.pGTransaction.merchantId.eq(QPGMerchant.pGMerchant.merchantCode),
							QPGAccount.pGAccount.entityId.eq(QPGTransaction.pGTransaction.merchantId),
							QPGMerchant.pGMerchant.bankId.eq(QPGBankCurrencyMapping.pGBankCurrencyMapping.bankId),
							QPGBankCurrencyMapping.pGBankCurrencyMapping.currencyCodeAlpha
									.eq(QPGCurrencyConfig.pGCurrencyConfig.currencyCodeAlpha),
							isValidDate(startDate, endDate))
					.distinct().orderBy(orderByCreatedDateDesc()).list(QPGTransaction.pGTransaction.merchantId,
							QPGTransaction.pGTransaction.id, QPGTransaction.pGTransaction.issuerTxnRefNum,
							QPGTransaction.pGTransaction.procCode, QPGTransaction.pGTransaction.panMasked,
							QPGTransaction.pGTransaction.createdDate, QPGTransaction.pGTransaction.transactionType,
							QPGTransaction.pGTransaction.txnAmount, QPGTransaction.pGTransaction.feeAmount,
							QPGTransaction.pGTransaction.merchantFeeAmount, QPGTransaction.pGTransaction.txnDescription,
							QPGTransaction.pGTransaction.status, QPGTransaction.pGTransaction.merchantSettlementStatus,
							QPGTransaction.pGTransaction.terminalId, QPGTransaction.pGTransaction.feeAmount,
							QPGTransaction.pGTransaction.posEntryMode, QPGTransaction.pGTransaction.acqChannel,
							QPGTransaction.pGTransaction.cardHolderName, QPGTransaction.pGTransaction.updatedDate,
							QPGTransaction.pGTransaction.authId, QPGTransaction.pGTransaction.invoiceNumber,
							QPGTransaction.pGTransaction.acqTxnMode, QPGTransaction.pGTransaction.txnTotalAmount,
							QPGTransaction.pGTransaction.processor, QPGTransaction.pGTransaction.txnMode,
							QPGTransaction.pGTransaction.refTransactionId, QPGAccount.pGAccount.entityId,
							QPGAccount.pGAccount.entityType, QPGAccount.pGAccount.accountNum,
							QPGMerchant.pGMerchant.firstName, QPGMerchant.pGMerchant.businessName,
							QPGMerchant.pGMerchant.merchantType, QPGCurrencyConfig.pGCurrencyConfig.currencyCodeAlpha,
							QPGBankCurrencyMapping.pGBankCurrencyMapping.currencyCodeAlpha,
							QPGMerchant.pGMerchant.localCurrency, QPGTransaction.pGTransaction.userName,
							QPGTransaction.pGTransaction.deviceLocalTxnTime,
							QPGTransaction.pGTransaction.timeZoneOffset, QPGMerchant.pGMerchant.address1,
							QPGMerchant.pGMerchant.city, QPGMerchant.pGMerchant.state, QPGMerchant.pGMerchant.city,
							QPGMerchant.pGMerchant.pin, QPGMerchant.pGMerchant.country,
							QPGTransaction.pGTransaction.expDate);
			if (!CollectionUtils.isEmpty(tupleList)) {
				transactions = new ArrayList<>(tupleList.size());
				for (Tuple tuple : tupleList) {
					TransactionHistory transactionResp = null;
					transactionResp = new TransactionHistory();
					merchantTxnHistoryTupleList(tuple, transactionResp, transactions);
				}
			}
		} catch (Exception exception) {
			log.error("TransactionDaoImpl | getMerchantTransactionList | Exception ", exception);
		}
		log.info("TransactionDaoImpl | getMerchantTransactionList | Exiting");
		return transactions;
	}

	private void merchantTxnHistoryTupleList(Tuple tuple, TransactionHistory transactionResp,
			List<TransactionHistory> transactions) {
		if (tuple.get(QPGTransaction.pGTransaction.status) == 0) {
			transactionResp.setErrorMessage("Approved");
		} else if (tuple.get(QPGTransaction.pGTransaction.status) == 1) {
			transactionResp.setErrorMessage("Declined");
		} else {
			transactionResp.setErrorMessage("Failed");
		}
		transactionResp.setStatus(transactionResp.getStatus());
		transactionResp.setCardHolderName(tuple.get(QPGTransaction.pGTransaction.cardHolderName) != null
				? tuple.get(QPGTransaction.pGTransaction.cardHolderName)
				: "");
		transactionResp.setMerchantName(
				tuple.get(QPGMerchant.pGMerchant.businessName) != null ? tuple.get(QPGMerchant.pGMerchant.businessName)
						: "");
		transactionResp.setDeviceLocalTxnTime(tuple.get(QPGTransaction.pGTransaction.deviceLocalTxnTime));
		transactionResp.setMerchantCode(tuple.get(QPGTransaction.pGTransaction.merchantId) != null
				? tuple.get(QPGTransaction.pGTransaction.merchantId)
				: "");

		transactionResp.setTotalAmount((StringUtils.amountToString(tuple.get(QPGTransaction.pGTransaction.txnAmount))));
		transactionResp.setTransactionamount(
				tuple.get(QPGTransaction.pGTransaction.txnTotalAmount).doubleValue() / Integer.parseInt("100"));
		transactionResp.setTxnDateTime(DateUtil.toDateStringFormat(tuple.get(QPGTransaction.pGTransaction.createdDate),
				PGConstants.DATE_FORMAT));
		transactionResp.setTransactionType(tuple.get(QPGTransaction.pGTransaction.transactionType));
		transactionResp.setTerminalId(tuple.get(QPGTransaction.pGTransaction.terminalId));
		transactionResp
				.setMaskedCardNumber(StringUtils.lastFourDigits(tuple.get(QPGTransaction.pGTransaction.panMasked)));
		transactionResp.setProcessorTxnId(tuple.get(QPGTransaction.pGTransaction.issuerTxnRefNum));
		transactionResp
				.setTransactionfee((StringUtils.amountToString(tuple.get(QPGTransaction.pGTransaction.feeAmount))));
		transactionResp.setTransactionId(tuple.get(QPGTransaction.pGTransaction.id).toString());
		if (null != tuple.get(QPGTransaction.pGTransaction.posEntryMode)) {
			transactionResp.setEntrymode(EntryModePortalDisplayEnum
					.valueOf(getEntryModeEnumFromPosEntryMode(tuple.get(QPGTransaction.pGTransaction.posEntryMode)))
					.value());
		} else {
			transactionResp
					.setEntrymode(EntryModePortalDisplayEnum.valueOf(getEntryModeEnumFromPosEntryMode("000")).value());
		}
		transactionResp.setTxnRefNumber(tuple.get(QPGTransaction.pGTransaction.id).toString());
		transactionResp.setAddress(tuple.get(QPGMerchant.pGMerchant.address1));
		transactionResp.setCity(tuple.get(QPGMerchant.pGMerchant.city));
		transactionResp.setCountry(tuple.get(QPGMerchant.pGMerchant.country));
		transactionResp.setPin(tuple.get(QPGMerchant.pGMerchant.pin));
		transactionResp.setState(tuple.get(QPGMerchant.pGMerchant.state));
		transactionResp.setAuthId(tuple.get(QPGTransaction.pGTransaction.authId));
		transactionResp.setInvoiceno(tuple.get(QPGTransaction.pGTransaction.invoiceNumber));
		transactionResp.setStatus(tuple.get(QPGTransaction.pGTransaction.merchantSettlementStatus));
		if (!Constants.ACCOUNT_PAY_VALUE.equals(tuple.get(QPGTransaction.pGTransaction.posEntryMode))) {
			transactionResp.setExpDate(tuple.get(QPGTransaction.pGTransaction.expDate).toString());
		}
		transactionResp.setCgRefNumber(tuple.get(QPGTransaction.pGTransaction.issuerTxnRefNum));
		transactions.add(transactionResp);
	}
}
