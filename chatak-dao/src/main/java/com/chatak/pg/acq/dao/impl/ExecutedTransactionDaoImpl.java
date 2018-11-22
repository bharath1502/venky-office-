/**
 * 
 */
package com.chatak.pg.acq.dao.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.chatak.pg.acq.dao.ExecutedTransactionDao;
import com.chatak.pg.acq.dao.MerchantUpdateDao;
import com.chatak.pg.acq.dao.model.PGAccount;
import com.chatak.pg.acq.dao.model.PGMerchant;
import com.chatak.pg.acq.dao.model.PGTransaction;
import com.chatak.pg.acq.dao.model.QPGAccount;
import com.chatak.pg.acq.dao.model.QPGAccountFeeLog;
import com.chatak.pg.acq.dao.model.QPGBankCurrencyMapping;
import com.chatak.pg.acq.dao.model.QPGCurrencyConfig;
import com.chatak.pg.acq.dao.model.QPGMerchant;
import com.chatak.pg.acq.dao.model.QPGOnlineTxnLog;
import com.chatak.pg.acq.dao.model.QPGTransaction;
import com.chatak.pg.acq.dao.repository.AccountRepository;
import com.chatak.pg.acq.dao.repository.TransactionRepository;
import com.chatak.pg.bean.LitleEFTRequest;
import com.chatak.pg.bean.TransactionPopUpDataDto;
import com.chatak.pg.constants.PGConstants;
import com.chatak.pg.dao.util.StringUtil;
import com.chatak.pg.enums.OriginalChannelEnum;
import com.chatak.pg.model.LitleEFTDTO;
import com.chatak.pg.model.ReportsDTO;
import com.chatak.pg.user.bean.GetTransactionsListRequest;
import com.chatak.pg.util.CommonUtil;
import com.chatak.pg.util.Constants;
import com.chatak.pg.util.DateUtil;
import com.chatak.pg.util.StringUtils;
import com.mysema.query.Tuple;
import com.mysema.query.jpa.JPASubQuery;
import com.mysema.query.jpa.impl.JPAQuery;
import com.mysema.query.types.expr.BooleanExpression;

/**
 * @Author: Girmiti Software
 * @Date: Aug 23, 2017
 * @Time: 5:06:13 PM
 * @Version: 1.0
 * @Comments: 
 *
 */
@Repository("executedTransactionDao")
public class ExecutedTransactionDaoImpl extends TransactionDaoImpl
    implements ExecutedTransactionDao {

  @PersistenceContext
  private EntityManager entityManager;

  @Autowired
  private TransactionRepository transactionRepository;

  @Autowired
  private AccountRepository accountRepository;

  @Autowired
  private MerchantUpdateDao merchantUpdateDao;

  @Override
  public List<ReportsDTO> getAllAccountsExecutedTransactionsReport() {
    List<ReportsDTO> list = new ArrayList<>();
    List<PGTransaction> transactionliList =
        transactionRepository.getTransactionsOnStatus(PGConstants.PG_SETTLEMENT_EXECUTED);
    for (PGTransaction pgTransaction : transactionliList) {
      ReportsDTO executedTransactionsReport = new ReportsDTO();
      PGMerchant pgMerchant = merchantUpdateDao.getMerchant(pgTransaction.getMerchantId());
      PGAccount pgAccount = accountRepository.findByEntityId(pgTransaction.getMerchantId());
      if ((null != pgMerchant) && (null != pgAccount)) {
        executedTransactionsReport.setUserName(pgMerchant.getUserName());
        executedTransactionsReport.setCompanyName(pgMerchant.getBusinessName());
        executedTransactionsReport.setAccountNumber(pgAccount.getAccountNum());
        executedTransactionsReport.setAccountType(pgAccount.getEntityType());
        executedTransactionsReport.setCurrency(pgAccount.getCurrency());
        executedTransactionsReport.setAmount(CommonUtil.toAmount(pgTransaction.getTxnAmount()));
        executedTransactionsReport.setDescription(pgTransaction.getTxnDescription());
        executedTransactionsReport.setPaymentMethod(pgTransaction.getPaymentMethod());
        executedTransactionsReport.setTransactionId(pgTransaction.getTransactionId());
        executedTransactionsReport.setDateTime(DateUtil
            .toDateStringFormat(pgTransaction.getCreatedDate(), DateUtil.VIEW_DATE_TIME_FORMAT));
        list.add(executedTransactionsReport);
      }
    }
    return list;
  }

  @Override
  public List<ReportsDTO> getAllAccountsExecutedTransactionsOnDate(
      GetTransactionsListRequest getTransactionsListRequest) {

    Integer pageIndex = getTransactionsListRequest.getPageIndex();
    Integer pageSize = getTransactionsListRequest.getPageSize();
    Integer totalRecords;
    int offset = 0;
    int limit = 0;
    if (pageIndex == null || pageIndex == 1) {
      totalRecords = getTotalNumberOfPendingRecords(getTransactionsListRequest);
      getTransactionsListRequest.setNoOfRecords(totalRecords);
    }
    if (pageIndex == null && pageSize == null) {
      offset = 0;
      limit = Constants.DEFAULT_PAGE_SIZE;
    } else {
      offset = (pageIndex - 1) * pageSize;
      limit = pageSize;
    }
    List<ReportsDTO> reportList = null;
    ReportsDTO transactionsReports = null;
    Timestamp startDate = null;
    Timestamp endDate = null;
    startDate = getStartDate(getTransactionsListRequest, startDate);
    endDate = getEndDate(getTransactionsListRequest, endDate);
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
        .offset(offset).limit(limit).orderBy(orderByCreatedDateDesc())
        .list(QPGMerchant.pGMerchant.userName, QPGMerchant.pGMerchant.businessName,
            QPGMerchant.pGMerchant.merchantType, QPGAccount.pGAccount.accountNum,
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
            QPGTransaction.pGTransaction.txnMode,
            QPGTransaction.pGTransaction.merchantSettlementStatus,
            QPGTransaction.pGTransaction.status, QPGTransaction.pGTransaction.cardHolderName,
            QPGCurrencyConfig.pGCurrencyConfig.currencyCodeAlpha,
            QPGBankCurrencyMapping.pGBankCurrencyMapping.currencyCodeAlpha,
            QPGTransaction.pGTransaction.deviceLocalTxnTime,
            QPGTransaction.pGTransaction.timeZoneOffset,QPGTransaction.pGTransaction.batchId);
    if (StringUtil.isListNotNullNEmpty(infoList)) {
      reportList = new ArrayList<>();
      TransactionPopUpDataDto txnDto = null;
      String statusMsg = null;
      for (Tuple tuple : infoList) {
    	txnDto = new TransactionPopUpDataDto();
        transactionsReports = new ReportsDTO();
        transactionsReports.setUserName(tuple.get(QPGMerchant.pGMerchant.userName));
        transactionsReports.setCompanyName(tuple.get(QPGMerchant.pGMerchant.businessName));
        transactionsReports.setAccountNumber(tuple.get(QPGAccount.pGAccount.accountNum));
        transactionsReports.setAccountType(tuple.get(QPGAccount.pGAccount.entityType));
        transactionsReports
            .setCurrency(tuple.get(QPGBankCurrencyMapping.pGBankCurrencyMapping.currencyCodeAlpha));
        transactionsReports.setAmount(
            StringUtils.amountToString(tuple.get(QPGTransaction.pGTransaction.txnTotalAmount)));
        transactionsReports.setDescription(tuple.get(QPGTransaction.pGTransaction.txnDescription));
        transactionsReports.setPaymentMethod(tuple.get(QPGTransaction.pGTransaction.paymentMethod));
        transactionsReports.setTransactionId(tuple.get(QPGTransaction.pGTransaction.transactionId));
        transactionsReports.setDateTime(DateUtil.toDateStringFormat(
            tuple.get(QPGTransaction.pGTransaction.createdDate), PGConstants.DATE_FORMAT));
        transactionsReports.setDeviceLocalTxnTime(tuple.get(QPGTransaction.pGTransaction.deviceLocalTxnTime));
        transactionsReports.setTimeZoneOffset(tuple.get(QPGTransaction.pGTransaction.timeZoneOffset));
        transactionsReports.setBatchId(tuple.get(QPGTransaction.pGTransaction.batchId));
        statusMsg = getStatusMessage(tuple);
        setTxnPopUpDataDto(txnDto, tuple);
        setTxnPopUpDetails(txnDto, tuple);
        txnDto.setStatusMessage(statusMsg);
        transactionsReports.setTxnPopupDto(txnDto);
        reportList.add(transactionsReports);
      }
    }
    return reportList;
  }

  private void setTxnPopUpDetails(TransactionPopUpDataDto txnDto, Tuple tuple) {
	txnDto
	    .setTransactionAmount((tuple.get(QPGTransaction.pGTransaction.txnAmount) != null)
	        ? tuple.get(QPGCurrencyConfig.pGCurrencyConfig.currencyCodeAlpha) + " "
	            + StringUtils.amountToString(
	                tuple.get(QPGTransaction.pGTransaction.txnAmount))
	        : tuple.get(QPGCurrencyConfig.pGCurrencyConfig.currencyCodeAlpha) + "0.00");
	txnDto
	    .setFee_amount((tuple.get(QPGTransaction.pGTransaction.feeAmount) != null)
	        ? tuple.get(QPGCurrencyConfig.pGCurrencyConfig.currencyCodeAlpha) + " "
	            + StringUtils.amountToString(
	                tuple.get(QPGTransaction.pGTransaction.feeAmount))
	        : tuple.get(QPGCurrencyConfig.pGCurrencyConfig.currencyCodeAlpha) + "0.00");
	txnDto.setTxn_total_amount((tuple.get(QPGTransaction.pGTransaction.txnTotalAmount) != null)
	    ? tuple.get(QPGCurrencyConfig.pGCurrencyConfig.currencyCodeAlpha) + " "
	        + StringUtils.amountToString(
	            tuple.get(QPGTransaction.pGTransaction.txnTotalAmount))
	    : tuple.get(QPGCurrencyConfig.pGCurrencyConfig.currencyCodeAlpha) + "0.00");
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

  private void setTxnPopUpDataDto(TransactionPopUpDataDto txnDto, Tuple tuple) {
	txnDto
	    .setTransaction_type(((tuple.get(QPGTransaction.pGTransaction.transactionType) != null)
	        ? tuple.get(QPGTransaction.pGTransaction.transactionType) : ""));
	txnDto.setAuth_id(((tuple.get(QPGTransaction.pGTransaction.authId) != null)
	        ? tuple.get(QPGTransaction.pGTransaction.authId) : ""));
	txnDto.setTransactionId(((tuple.get(QPGTransaction.pGTransaction.transactionId) != null)
	        ? tuple.get(QPGTransaction.pGTransaction.transactionId).toString() : ""));
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
	    tuple.get(QPGTransaction.pGTransaction.createdDate), PGConstants.DATE_FORMAT));
	txnDto.setDeviceLocalTxnTime(tuple.get(QPGTransaction.pGTransaction.deviceLocalTxnTime));
	txnDto.setBatchId(tuple.get(QPGTransaction.pGTransaction.batchId));
	txnDto.setTimeZoneOffset(tuple.get(QPGTransaction.pGTransaction.timeZoneOffset));
  }

  /**
   * @param tuple
   * @return
   */
  private String getStatusMessage(Tuple tuple) {
    String statusMsg;
    if (0 == tuple.get(QPGTransaction.pGTransaction.status)) {
      statusMsg = "Approved";
    } else if (1 == tuple.get(QPGTransaction.pGTransaction.status)) {
      statusMsg = "Declined";
    } else {
      statusMsg = "Failed";
    }
    return statusMsg;
  }

  @Override
  public List<ReportsDTO> getAllExecutedAccFeeOnDate(
      GetTransactionsListRequest getTransactionsListRequest) {
    List<ReportsDTO> reportList = new ArrayList<>();
    ReportsDTO transactionsReports = null;
		Timestamp startDate = null;
		Timestamp endDate = null;
		 int offset = 0;
	      int limit = 0;
	      Integer totalRecords = getTransactionsListRequest.getNoOfRecords();

	      if (getTrasactionListRequestPageIndex(getTransactionsListRequest)) {
	        totalRecords = getTotalRecords(getTransactionsListRequest);
	      }
	      getTransactionsListRequest.setNoOfRecords(totalRecords);
	      if (getTrasactionListIndexAndSize(getTransactionsListRequest)) {
	        offset = 0;
	        limit = Constants.DEFAULT_PAGE_SIZE;
	      } else {
	        offset = (getTransactionsListRequest.getPageIndex() - 1) * getTransactionsListRequest.getPageSize();
	        limit = getTransactionsListRequest.getPageSize();
	      }
		startDate = getStartDate(getTransactionsListRequest, startDate);
		endDate = getEndDate(getTransactionsListRequest, endDate);
    QPGMerchant pp = new QPGMerchant("pp");
    JPAQuery query = new JPAQuery(entityManager);
    if (null != getTransactionsListRequest.getMerchant_code()) {
      List<Tuple> infoList = query.distinct()
          .from(QPGTransaction.pGTransaction, QPGMerchant.pGMerchant, QPGAccount.pGAccount,
              QPGOnlineTxnLog.pGOnlineTxnLog, QPGAccountFeeLog.pGAccountFeeLog)
          .where(QPGMerchant.pGMerchant.merchantCode.eq(QPGTransaction.pGTransaction.merchantId),
              QPGTransaction.pGTransaction.transactionId
                  .eq(QPGOnlineTxnLog.pGOnlineTxnLog.pgTxnId.stringValue()),
              QPGTransaction.pGTransaction.transactionId
                  .eq(QPGAccountFeeLog.pGAccountFeeLog.transactionId),
              QPGAccount.pGAccount.entityId.eq(QPGMerchant.pGMerchant.merchantCode),
              QPGTransaction.pGTransaction.merchantSettlementStatus
                  .eq(getTransactionsListRequest.getSettlementStatus()),
              QPGMerchant.pGMerchant.merchantCode
                  .eq(QPGTransaction.pGTransaction.merchantId.stringValue()),
              QPGTransaction.pGTransaction.transactionId
                  .eq(QPGAccountFeeLog.pGAccountFeeLog.transactionId),
              QPGTransaction.pGTransaction.transactionType.equalsIgnoreCase("sale"),
              QPGAccount.pGAccount.entityId.eq(QPGMerchant.pGMerchant.merchantCode),
              QPGTransaction.pGTransaction.merchantSettlementStatus
                  .eq(getTransactionsListRequest.getSettlementStatus()),
              isRevenueType(getTransactionsListRequest.getEntryMode()),
              isValidDate(startDate, endDate),
              QPGMerchant.pGMerchant.merchantCode.eq(getTransactionsListRequest.getMerchant_code())
                  .or((QPGMerchant.pGMerchant.parentMerchantId.in(new JPASubQuery().from(pp)
                      .where(pp.merchantCode.eq(getTransactionsListRequest.getMerchant_code()))
                      .list(pp.id)))))
          			  .offset(offset).limit(limit).orderBy(orderByCreatedDateDesc())
                      .list(QPGTransaction.pGTransaction.transactionId,
              QPGTransaction.pGTransaction.txnTotalAmount,
              QPGTransaction.pGTransaction.txnDescription,
              QPGTransaction.pGTransaction.paymentMethod, QPGTransaction.pGTransaction.txnAmount,
              QPGTransaction.pGTransaction.createdDate,
              QPGTransaction.pGTransaction.transactionType, QPGTransaction.pGTransaction.authId,
              QPGTransaction.pGTransaction.refTransactionId,
              QPGTransaction.pGTransaction.terminalId, QPGTransaction.pGTransaction.invoiceNumber,
              QPGTransaction.pGTransaction.panMasked, QPGTransaction.pGTransaction.merchantId,
              QPGTransaction.pGTransaction.acqChannel, QPGTransaction.pGTransaction.acqTxnMode,
              QPGTransaction.pGTransaction.feeAmount, QPGTransaction.pGTransaction.processor,
              QPGTransaction.pGTransaction.txnMode,
              QPGTransaction.pGTransaction.merchantSettlementStatus,
              QPGTransaction.pGTransaction.status, QPGTransaction.pGTransaction.cardHolderName,
              QPGMerchant.pGMerchant.userName, QPGMerchant.pGMerchant.businessName,
              QPGMerchant.pGMerchant.merchantCode, QPGMerchant.pGMerchant.merchantType,
              QPGAccount.pGAccount.accountNum, QPGAccount.pGAccount.entityType,
              QPGAccount.pGAccount.currency, QPGAccountFeeLog.pGAccountFeeLog.merchantFee,
              QPGAccountFeeLog.pGAccountFeeLog.chatakFee,
              QPGAccountFeeLog.pGAccountFeeLog.parentEntityId,
              QPGTransaction.pGTransaction.deviceLocalTxnTime,
              QPGTransaction.pGTransaction.timeZoneOffset);
      if (StringUtil.isListNotNullNEmpty(infoList)) {
        TransactionPopUpDataDto txnDto = null;
        String statusMsg = null;
        for (Tuple tuple : infoList) {
          transactionsReports = new ReportsDTO();
          txnDto = new TransactionPopUpDataDto();
          transactionsReports.setUserName(tuple.get(QPGMerchant.pGMerchant.userName));
          transactionsReports.setCompanyName(tuple.get(QPGMerchant.pGMerchant.businessName));
          transactionsReports.setMerchantCode((tuple.get(QPGMerchant.pGMerchant.merchantCode)));
          transactionsReports.setAccountNumber(tuple.get(QPGAccount.pGAccount.accountNum));
          transactionsReports.setAccountType(tuple.get(QPGAccount.pGAccount.entityType));
          transactionsReports.setCurrency(tuple.get(QPGAccount.pGAccount.currency));
          transactionsReports.setAmount(
              StringUtils.amountToString(tuple.get(QPGTransaction.pGTransaction.txnTotalAmount)));
          transactionsReports
              .setDescription(tuple.get(QPGTransaction.pGTransaction.txnDescription));
          transactionsReports
          .setTransactionId(tuple.get(QPGTransaction.pGTransaction.transactionId));
          transactionsReports
              .setPaymentMethod(tuple.get(QPGTransaction.pGTransaction.paymentMethod));
          transactionsReports.setDateTime(DateUtil.toDateStringFormat(
              tuple.get(QPGTransaction.pGTransaction.createdDate),PGConstants.DATE_FORMAT));
          transactionsReports
          .setChatakFee(tuple.get(QPGAccountFeeLog.pGAccountFeeLog.chatakFee).toString());
          transactionsReports
              .setFee(tuple.get(QPGAccountFeeLog.pGAccountFeeLog.merchantFee).toString());
          transactionsReports
              .setParentMerchantId(tuple.get(QPGAccountFeeLog.pGAccountFeeLog.parentEntityId));
          transactionsReports
              .setTotalTxnAmount(tuple.get(QPGTransaction.pGTransaction.txnAmount).toString());
          transactionsReports.setDeviceLocalTxnTime(tuple.get(QPGTransaction.pGTransaction.deviceLocalTxnTime));
          transactionsReports.setTimeZoneOffset(tuple.get(QPGTransaction.pGTransaction.timeZoneOffset));
          transactionsReports.setNoOfRecords(totalRecords);
          statusMsg = getStatusMessage(tuple);
          setTransactionPopUpData(txnDto, tuple);
          setTransactionPopUpDto(txnDto, statusMsg, tuple);
          transactionsReports.setTxnPopupDto(txnDto);
          reportList.add(transactionsReports);
        }
      }
    } else {
      JPAQuery query1 = new JPAQuery(entityManager);
      List<Tuple> infoList = query1.distinct()
          .from(QPGTransaction.pGTransaction, QPGMerchant.pGMerchant, QPGAccount.pGAccount,
              QPGOnlineTxnLog.pGOnlineTxnLog, QPGAccountFeeLog.pGAccountFeeLog)
          .where(
              QPGMerchant.pGMerchant.merchantCode
                  .eq(QPGTransaction.pGTransaction.merchantId.stringValue())
                  .and(QPGTransaction.pGTransaction.transactionId
                      .eq(QPGOnlineTxnLog.pGOnlineTxnLog.pgTxnId.stringValue()))
              .and(QPGTransaction.pGTransaction.transactionId
                  .eq(QPGAccountFeeLog.pGAccountFeeLog.transactionId))
              .and(isValidDate(startDate, endDate))
              .and(QPGAccount.pGAccount.entityId.eq(QPGMerchant.pGMerchant.merchantCode)),
              QPGTransaction.pGTransaction.merchantSettlementStatus
                  .eq(getTransactionsListRequest.getSettlementStatus()),
              isRevenueType(getTransactionsListRequest.getEntryMode()))
          .orderBy(orderByCreatedDateDesc()).list(QPGMerchant.pGMerchant.userName,
              QPGMerchant.pGMerchant.businessName, QPGAccount.pGAccount.accountNum,
              QPGAccount.pGAccount.entityType, QPGAccount.pGAccount.currency,
              QPGTransaction.pGTransaction.createdDate, QPGTransaction.pGTransaction.txnTotalAmount,
              QPGTransaction.pGTransaction.txnDescription,
              QPGTransaction.pGTransaction.paymentMethod,
              QPGTransaction.pGTransaction.transactionId, QPGTransaction.pGTransaction.txnAmount,
              QPGTransaction.pGTransaction.transactionType, QPGTransaction.pGTransaction.authId,
              QPGTransaction.pGTransaction.refTransactionId,
              QPGTransaction.pGTransaction.terminalId, QPGTransaction.pGTransaction.invoiceNumber,
              QPGTransaction.pGTransaction.panMasked, QPGTransaction.pGTransaction.merchantId,
              QPGTransaction.pGTransaction.acqChannel, QPGTransaction.pGTransaction.acqTxnMode,
              QPGTransaction.pGTransaction.feeAmount, QPGTransaction.pGTransaction.processor,
              QPGTransaction.pGTransaction.txnMode,
              QPGTransaction.pGTransaction.merchantSettlementStatus,
              QPGTransaction.pGTransaction.status, QPGAccountFeeLog.pGAccountFeeLog.merchantFee,
              QPGAccountFeeLog.pGAccountFeeLog.chatakFee,
              QPGAccountFeeLog.pGAccountFeeLog.parentEntityId,QPGTransaction.pGTransaction.deviceLocalTxnTime,
              QPGTransaction.pGTransaction.timeZoneOffset);
      if (StringUtil.isListNotNullNEmpty(infoList)) {
        reportList = new ArrayList<>();
        TransactionPopUpDataDto txnDto = null;
        String statusMsg = null;
        for (Tuple tuple : infoList) {
          transactionsReports = new ReportsDTO();
          txnDto = new TransactionPopUpDataDto();
          transactionsReports.setUserName(tuple.get(QPGMerchant.pGMerchant.userName));
          transactionsReports.setCompanyName(tuple.get(QPGMerchant.pGMerchant.businessName));
          transactionsReports.setAccountNumber(tuple.get(QPGAccount.pGAccount.accountNum));
          transactionsReports.setAccountType(tuple.get(QPGAccount.pGAccount.entityType));
          transactionsReports.setCurrency(tuple.get(QPGAccount.pGAccount.currency));
          transactionsReports.setAmount(PGConstants.DOLLAR_SYMBOL
              + StringUtils.amountToString(tuple.get(QPGTransaction.pGTransaction.txnTotalAmount)));
          transactionsReports
              .setDescription(tuple.get(QPGTransaction.pGTransaction.txnDescription));
          transactionsReports
              .setPaymentMethod(tuple.get(QPGTransaction.pGTransaction.paymentMethod));
          transactionsReports
              .setTransactionId(tuple.get(QPGTransaction.pGTransaction.transactionId));
          transactionsReports.setDateTime(DateUtil.toDateStringFormat(
              tuple.get(QPGTransaction.pGTransaction.createdDate), DateUtil.VIEW_DATE_TIME_FORMAT));
          transactionsReports
              .setFee(tuple.get(QPGAccountFeeLog.pGAccountFeeLog.merchantFee).toString());
          transactionsReports
              .setChatakFee(tuple.get(QPGAccountFeeLog.pGAccountFeeLog.chatakFee).toString());
          transactionsReports
              .setParentMerchantId(tuple.get(QPGAccountFeeLog.pGAccountFeeLog.parentEntityId));
          transactionsReports
              .setTotalTxnAmount(tuple.get(QPGTransaction.pGTransaction.txnAmount).toString());
          transactionsReports.setDeviceLocalTxnTime(tuple.get(QPGTransaction.pGTransaction.deviceLocalTxnTime));
          transactionsReports.setTimeZoneOffset(tuple.get(QPGTransaction.pGTransaction.timeZoneOffset));
          statusMsg = getStatusMessage(tuple);
          setTxnPopUpData(txnDto, tuple);
          setTxnPopUpDetails(txnDto, statusMsg, tuple);
          transactionsReports.setTxnPopupDto(txnDto);
          transactionsReports.setNoOfRecords(totalRecords);
          reportList.add(transactionsReports);
        }
      }
    }
    return reportList;
  }

	/**
	 * @param getTransactionsListRequest
	 * @return
	 */
	private boolean getTrasactionListRequestPageIndex(GetTransactionsListRequest getTransactionsListRequest) {
		return getTransactionsListRequest.getPageIndex() == null || getTransactionsListRequest.getPageIndex() == 1;
	}

	/**
	 * @param getTransactionsListRequest
	 * @return
	 */
	private boolean getTrasactionListIndexAndSize(GetTransactionsListRequest getTransactionsListRequest) {
		return getTransactionsListRequest.getPageIndex() == null && getTransactionsListRequest.getPageSize() == null;
	}

	/**
	 * @param getTransactionsListRequest
	 * @param endDate
	 * @return
	 */
	private Timestamp getEndDate(GetTransactionsListRequest getTransactionsListRequest, Timestamp endDate) {
		if (!CommonUtil.isNullAndEmpty(getTransactionsListRequest.getTo_date())) {
			endDate = DateUtil.getEndDayTimestamp(getTransactionsListRequest.getTo_date(), PGConstants.DD_MM_YYYY);
		}
		return endDate;
	}

	/**
	 * @param getTransactionsListRequest
	 * @param startDate
	 * @return
	 */
	private Timestamp getStartDate(GetTransactionsListRequest getTransactionsListRequest, Timestamp startDate) {
		if (!CommonUtil.isNullAndEmpty(getTransactionsListRequest.getFrom_date())) {
			startDate = DateUtil.getStartDayTimestamp(getTransactionsListRequest.getFrom_date(),
					PGConstants.DD_MM_YYYY);
		}
		return startDate;
	}

  private void setTxnPopUpDetails(TransactionPopUpDataDto txnDto, String statusMsg, Tuple tuple) {
	txnDto.setFee_amount((tuple.get(QPGTransaction.pGTransaction.feeAmount) != null)
	      ? PGConstants.DOLLAR_SYMBOL + StringUtils
	          .amountToString(tuple.get(QPGTransaction.pGTransaction.feeAmount))
	      : PGConstants.DOLLAR_SYMBOL + "0.00");
	  txnDto
	      .setTxn_total_amount((tuple.get(QPGTransaction.pGTransaction.txnTotalAmount) != null)
	          ? PGConstants.DOLLAR_SYMBOL + StringUtils.amountToString(
	              tuple.get(QPGTransaction.pGTransaction.txnTotalAmount))
	          : PGConstants.DOLLAR_SYMBOL + "0.00");
	  txnDto.setProcessor(((tuple.get(QPGTransaction.pGTransaction.processor) != null)
	      ? tuple.get(QPGTransaction.pGTransaction.processor) : ""));
	  txnDto.setMerchantSettlementStatus(
	      ((tuple.get(QPGTransaction.pGTransaction.merchantSettlementStatus) != null)
	          ? tuple.get(QPGTransaction.pGTransaction.merchantSettlementStatus) : ""));
	  txnDto.setTxnDescription(((tuple.get(QPGTransaction.pGTransaction.txnDescription) != null)
	      ? tuple.get(QPGTransaction.pGTransaction.txnDescription) : ""));
	  txnDto.setStatusMessage(statusMsg);
	  txnDto.setCardHolderName(tuple.get(QPGTransaction.pGTransaction.cardHolderName) != null
	      ? tuple.get(QPGTransaction.pGTransaction.cardHolderName) : "");
	  txnDto.setMerchantType((tuple.get(QPGMerchant.pGMerchant.merchantType) != null)
	      ? tuple.get(QPGMerchant.pGMerchant.merchantType) : "");
	  txnDto.setMerchantBusinessName(tuple.get(QPGMerchant.pGMerchant.businessName) != null
	      ? tuple.get(QPGMerchant.pGMerchant.businessName) : "");
  }

  private void setTxnPopUpData(TransactionPopUpDataDto txnDto, Tuple tuple) {
	txnDto.setAuth_id(((tuple.get(QPGTransaction.pGTransaction.authId) != null)
	      ? tuple.get(QPGTransaction.pGTransaction.authId) : ""));
	  txnDto.setTransaction_type(
	          ((tuple.get(QPGTransaction.pGTransaction.transactionType) != null)
	              ? tuple.get(QPGTransaction.pGTransaction.transactionType) : ""));
	  txnDto.setTransactionId(((tuple.get(QPGTransaction.pGTransaction.transactionId) != null)
	      ? tuple.get(QPGTransaction.pGTransaction.transactionId).toString() : ""));
	  txnDto.setInvoice_number(((tuple.get(QPGTransaction.pGTransaction.invoiceNumber) != null)
	          ? tuple.get(QPGTransaction.pGTransaction.invoiceNumber) : ""));
	  txnDto.setRef_transaction_id(
	      ((tuple.get(QPGTransaction.pGTransaction.refTransactionId) != null)
	          ? tuple.get(QPGTransaction.pGTransaction.refTransactionId) : "N/A"));
	  txnDto.setTerminal_id(((tuple.get(QPGTransaction.pGTransaction.terminalId) != null)
	      ? tuple.get(QPGTransaction.pGTransaction.terminalId) : ""));
	  txnDto.setAcqTxnMode(((tuple.get(QPGTransaction.pGTransaction.acqTxnMode) != null)
	          ? tuple.get(QPGTransaction.pGTransaction.acqTxnMode) : ""));
	  txnDto.setMaskCardNumber(((StringUtils.lastFourDigits(tuple.get(QPGTransaction.pGTransaction.panMasked)) != null)
	      ? StringUtils.lastFourDigits(tuple.get(QPGTransaction.pGTransaction.panMasked)) : ""));
	  txnDto.setMerchant_code(((tuple.get(QPGTransaction.pGTransaction.merchantId) != null)
	      ? tuple.get(QPGTransaction.pGTransaction.merchantId) : ""));
	  txnDto.setTransactionDate(DateUtil.toDateStringFormat(
	          tuple.get(QPGTransaction.pGTransaction.createdDate),PGConstants.DATE_FORMAT));
	  txnDto.setAcqChannel(((tuple.get(QPGTransaction.pGTransaction.acqChannel) != null)
	      ? tuple.get(QPGTransaction.pGTransaction.acqChannel) : ""));
	  txnDto.setTransactionAmount((tuple.get(QPGTransaction.pGTransaction.txnAmount) != null)
	      ? PGConstants.DOLLAR_SYMBOL + StringUtils
	          .amountToString(tuple.get(QPGTransaction.pGTransaction.txnAmount))
	      : PGConstants.DOLLAR_SYMBOL + "0.00");
	  txnDto.setDeviceLocalTxnTime(tuple.get(QPGTransaction.pGTransaction.deviceLocalTxnTime));
  }

  private void setTransactionPopUpData(TransactionPopUpDataDto txnDto, Tuple tuple) {
	txnDto.setTransaction_type(
	      ((tuple.get(QPGTransaction.pGTransaction.transactionType) != null)
	          ? tuple.get(QPGTransaction.pGTransaction.transactionType) : ""));
	  txnDto.setTransactionId(((tuple.get(QPGTransaction.pGTransaction.transactionId) != null)
	          ? tuple.get(QPGTransaction.pGTransaction.transactionId).toString() : ""));
	  txnDto.setAuth_id(((tuple.get(QPGTransaction.pGTransaction.authId) != null)
	      ? tuple.get(QPGTransaction.pGTransaction.authId) : ""));
	  txnDto.setRef_transaction_id(
	      ((tuple.get(QPGTransaction.pGTransaction.refTransactionId) != null)
	          ? tuple.get(QPGTransaction.pGTransaction.refTransactionId) : "N/A"));
	  txnDto.setInvoice_number(((tuple.get(QPGTransaction.pGTransaction.invoiceNumber) != null)
	          ? tuple.get(QPGTransaction.pGTransaction.invoiceNumber) : ""));
	  txnDto.setTerminal_id(((tuple.get(QPGTransaction.pGTransaction.terminalId) != null)
	      ? tuple.get(QPGTransaction.pGTransaction.terminalId) : ""));
	  txnDto.setMaskCardNumber(((StringUtils.lastFourDigits(tuple.get(QPGTransaction.pGTransaction.panMasked)) != null)
	      ? StringUtils.lastFourDigits(tuple.get(QPGTransaction.pGTransaction.panMasked)) : ""));
	  txnDto.setAcqTxnMode(((tuple.get(QPGTransaction.pGTransaction.acqTxnMode) != null)
	          ? tuple.get(QPGTransaction.pGTransaction.acqTxnMode) : ""));
	  txnDto.setMerchant_code(((tuple.get(QPGTransaction.pGTransaction.merchantId) != null)
	      ? tuple.get(QPGTransaction.pGTransaction.merchantId) : ""));
	  txnDto.setAcqChannel(((tuple.get(QPGTransaction.pGTransaction.acqChannel) != null)
	      ? tuple.get(QPGTransaction.pGTransaction.acqChannel) : ""));
	  txnDto.setTransactionDate(DateUtil.toDateStringFormat(
	      tuple.get(QPGTransaction.pGTransaction.createdDate), PGConstants.DATE_FORMAT));
	  txnDto.setDeviceLocalTxnTime(tuple.get(QPGTransaction.pGTransaction.deviceLocalTxnTime));
	  txnDto.setTimeZoneOffset(tuple.get(QPGTransaction.pGTransaction.timeZoneOffset));
  }

  private void setTransactionPopUpDto(TransactionPopUpDataDto txnDto, String statusMsg, Tuple tuple) {
	txnDto
	      .setTransactionAmount((tuple.get(QPGTransaction.pGTransaction.txnAmount) != null)
	          ? tuple.get(QPGAccount.pGAccount.currency) + " "
	              + StringUtils.amountToString(
	                  tuple.get(QPGTransaction.pGTransaction.txnAmount))
	          : PGConstants.DOLLAR_SYMBOL + "0.00");
	  txnDto
	      .setFee_amount((tuple.get(QPGTransaction.pGTransaction.feeAmount) != null)
	          ? tuple.get(QPGAccount.pGAccount.currency) + " "
	              + StringUtils.amountToString(
	                  tuple.get(QPGTransaction.pGTransaction.feeAmount))
	          : PGConstants.DOLLAR_SYMBOL + "0.00");
	  txnDto
	      .setTxn_total_amount((tuple.get(QPGTransaction.pGTransaction.txnTotalAmount) != null)
	          ? tuple.get(QPGAccount.pGAccount.currency) + " "
	              + StringUtils.amountToString(
	                  tuple.get(QPGTransaction.pGTransaction.txnTotalAmount))
	          : PGConstants.DOLLAR_SYMBOL + "0.00");
	  txnDto.setProcessor(((tuple.get(QPGTransaction.pGTransaction.processor) != null)
	      ? tuple.get(QPGTransaction.pGTransaction.processor) : ""));
	  txnDto.setMerchantSettlementStatus(
	      ((tuple.get(QPGTransaction.pGTransaction.merchantSettlementStatus) != null)
	          ? tuple.get(QPGTransaction.pGTransaction.merchantSettlementStatus) : ""));
	  txnDto.setTxnDescription(((tuple.get(QPGTransaction.pGTransaction.txnDescription) != null)
	      ? tuple.get(QPGTransaction.pGTransaction.txnDescription) : ""));
	  txnDto.setStatusMessage(statusMsg);
	  txnDto.setCardHolderName(tuple.get(QPGTransaction.pGTransaction.cardHolderName) != null
	      ? tuple.get(QPGTransaction.pGTransaction.cardHolderName) : "");
	  txnDto.setMerchantType((tuple.get(QPGMerchant.pGMerchant.merchantType) != null)
	      ? tuple.get(QPGMerchant.pGMerchant.merchantType) : "");
	  txnDto.setMerchantBusinessName(tuple.get(QPGMerchant.pGMerchant.businessName) != null
	      ? tuple.get(QPGMerchant.pGMerchant.businessName) : "");
  }

  @Override
  public LitleEFTRequest getLitleExecutedTransactions(LitleEFTRequest litleEFTRequest) {
    int offset = 0;
    int limit = 0;
    Integer totalRecords = getLitleExecutedTransactionsCount();
    if (litleEFTRequest.getPageIndex() == null || litleEFTRequest.getPageIndex().intValue() == 1) {
      totalRecords = getLitleExecutedTransactionsCount();
      litleEFTRequest.setNoOfRecords(totalRecords);
    }
    litleEFTRequest.setNoOfRecords(totalRecords);
    if (litleEFTRequest.getPageIndex() == null || litleEFTRequest.getPageSize() == null) {
      offset = 0;
      limit = Constants.DEFAULT_PAGE_SIZE;
    } else {
      offset = (litleEFTRequest.getPageIndex() - 1) * litleEFTRequest.getPageSize();
      limit = litleEFTRequest.getPageSize();
    }
    List<LitleEFTDTO> txntList = null;
    LitleEFTDTO litleEFTDTO = null;
    JPAQuery query = new JPAQuery(entityManager);
    List<Tuple> infoList = query.from(QPGTransaction.pGTransaction, QPGMerchant.pGMerchant)
        .where(QPGTransaction.pGTransaction.eftStatus.eq(PGConstants.LITLE_EXECUTED),
            QPGTransaction.pGTransaction.merchantId.eq(QPGMerchant.pGMerchant.merchantCode))
        .offset(offset).limit(limit).orderBy(orderByCreatedDateDesc())
        .list(QPGTransaction.pGTransaction.transactionId, QPGTransaction.pGTransaction.txnAmount,
            QPGTransaction.pGTransaction.merchantId, QPGTransaction.pGTransaction.createdDate,
            QPGTransaction.pGTransaction.transactionType, QPGTransaction.pGTransaction.authId,
            QPGTransaction.pGTransaction.refTransactionId, QPGTransaction.pGTransaction.terminalId,
            QPGTransaction.pGTransaction.invoiceNumber, QPGTransaction.pGTransaction.panMasked,
            QPGTransaction.pGTransaction.acqTxnMode, QPGTransaction.pGTransaction.acqChannel,
            QPGTransaction.pGTransaction.feeAmount, QPGTransaction.pGTransaction.txnTotalAmount,
            QPGTransaction.pGTransaction.processor, QPGTransaction.pGTransaction.txnMode,
            QPGTransaction.pGTransaction.merchantSettlementStatus,
            QPGTransaction.pGTransaction.txnDescription, QPGTransaction.pGTransaction.status,
            QPGTransaction.pGTransaction.cardHolderName, QPGMerchant.pGMerchant.businessName,
            QPGMerchant.pGMerchant.merchantType);
    if (StringUtil.isListNotNullNEmpty(infoList)) {
      txntList = new ArrayList<>();
      TransactionPopUpDataDto txnDto = null;
      String statusMsg = null;
      for (Tuple tuple : infoList) {
        litleEFTDTO = new LitleEFTDTO();
        txnDto = new TransactionPopUpDataDto();
        litleEFTDTO.setAmount(tuple.get(QPGTransaction.pGTransaction.txnAmount));
        litleEFTDTO.setMerchantCode((tuple.get(QPGTransaction.pGTransaction.merchantId)));
        litleEFTDTO.setDateTime((tuple.get(QPGTransaction.pGTransaction.createdDate)));
        litleEFTDTO.setTransactionId(tuple.get(QPGTransaction.pGTransaction.transactionId));
        statusMsg = getStatusMessage(tuple);
        setTransactionPopUpDataDto(txnDto, tuple);
        setTransactionPopUpDataDetails(txnDto, statusMsg, tuple);
        litleEFTDTO.setTxnDto(txnDto);
        txntList.add(litleEFTDTO);
      }
    }
    litleEFTRequest.setLitleEFTDTOs(txntList);
    return litleEFTRequest;
  }

  private void setTransactionPopUpDataDetails(TransactionPopUpDataDto txnDto, String statusMsg, Tuple tuple) {
	txnDto.setTxn_total_amount((tuple.get(QPGTransaction.pGTransaction.txnTotalAmount) != null)
	    ? PGConstants.DOLLAR_SYMBOL + StringUtils
	        .amountToString(tuple.get(QPGTransaction.pGTransaction.txnTotalAmount))
	    : PGConstants.DOLLAR_SYMBOL + "0.00");
	txnDto.setFee_amount((tuple.get(QPGTransaction.pGTransaction.feeAmount) != null)
	        ? PGConstants.DOLLAR_SYMBOL + StringUtils
	            .amountToString(tuple.get(QPGTransaction.pGTransaction.feeAmount))
	        : PGConstants.DOLLAR_SYMBOL + "0.00");
	txnDto.setProcessor(((tuple.get(QPGTransaction.pGTransaction.processor) != null)
	    ? tuple.get(QPGTransaction.pGTransaction.processor) : ""));
	txnDto.setMerchantSettlementStatus(
	    ((tuple.get(QPGTransaction.pGTransaction.merchantSettlementStatus) != null)
	        ? tuple.get(QPGTransaction.pGTransaction.merchantSettlementStatus) : ""));
	txnDto.setStatusMessage(statusMsg);
	txnDto.setMerchantType((tuple.get(QPGMerchant.pGMerchant.merchantType) != null)
	    ? tuple.get(QPGMerchant.pGMerchant.merchantType) : "");
	txnDto.setTxnDescription(((tuple.get(QPGTransaction.pGTransaction.txnDescription) != null)
	        ? tuple.get(QPGTransaction.pGTransaction.txnDescription) : ""));
	txnDto.setMerchantBusinessName(tuple.get(QPGMerchant.pGMerchant.businessName) != null
	    ? tuple.get(QPGMerchant.pGMerchant.businessName) : "");
  }

  private void setTransactionPopUpDataDto(TransactionPopUpDataDto txnDto, Tuple tuple) {
	txnDto.setAuth_id(((tuple.get(QPGTransaction.pGTransaction.authId) != null)
	    ? tuple.get(QPGTransaction.pGTransaction.authId) : ""));
	txnDto
	.setTransaction_type(((tuple.get(QPGTransaction.pGTransaction.transactionType) != null)
	    ? tuple.get(QPGTransaction.pGTransaction.transactionType) : ""));
	txnDto.setTransactionId(((tuple.get(QPGTransaction.pGTransaction.transactionId) != null)
	    ? tuple.get(QPGTransaction.pGTransaction.transactionId).toString() : ""));
	txnDto.setTerminal_id(((tuple.get(QPGTransaction.pGTransaction.terminalId) != null)
	    ? tuple.get(QPGTransaction.pGTransaction.terminalId) : ""));
	txnDto.setInvoice_number(((tuple.get(QPGTransaction.pGTransaction.invoiceNumber) != null)
	    ? tuple.get(QPGTransaction.pGTransaction.invoiceNumber) : ""));
	txnDto.setRef_transaction_id(
	        ((tuple.get(QPGTransaction.pGTransaction.refTransactionId) != null)
	            ? tuple.get(QPGTransaction.pGTransaction.refTransactionId) : "N/A"));
	txnDto.setMaskCardNumber(((StringUtils.lastFourDigits(tuple.get(QPGTransaction.pGTransaction.panMasked)) != null)
	    ? StringUtils.lastFourDigits(tuple.get(QPGTransaction.pGTransaction.panMasked)) : ""));
	txnDto.setCardHolderName(tuple.get(QPGTransaction.pGTransaction.cardHolderName) != null
	        ? tuple.get(QPGTransaction.pGTransaction.cardHolderName) : "");
	txnDto.setAcqTxnMode(((tuple.get(QPGTransaction.pGTransaction.acqTxnMode) != null)
	    ? tuple.get(QPGTransaction.pGTransaction.acqTxnMode) : ""));
	txnDto.setAcqChannel(((tuple.get(QPGTransaction.pGTransaction.acqChannel) != null)
	    ? tuple.get(QPGTransaction.pGTransaction.acqChannel) : ""));
	txnDto.setMerchant_code(((tuple.get(QPGTransaction.pGTransaction.merchantId) != null)
	        ? tuple.get(QPGTransaction.pGTransaction.merchantId) : ""));
	txnDto.setTransactionDate(DateUtil.toDateStringFormat(
	    tuple.get(QPGTransaction.pGTransaction.createdDate), DateUtil.VIEW_DATE_TIME_FORMAT));
	txnDto.setTransactionAmount((tuple.get(QPGTransaction.pGTransaction.txnAmount) != null)
	    ? PGConstants.DOLLAR_SYMBOL + StringUtils
	        .amountToString(tuple.get(QPGTransaction.pGTransaction.txnAmount))
	    : PGConstants.DOLLAR_SYMBOL + "0.00");
  }

  @Override
  public int getLitleExecutedTransactionsCount() {
    JPAQuery query = new JPAQuery(entityManager);
    List<Tuple> infoList = query.from(QPGTransaction.pGTransaction, QPGMerchant.pGMerchant)
        .where(QPGTransaction.pGTransaction.eftStatus.eq(PGConstants.LITLE_EXECUTED),
            QPGTransaction.pGTransaction.merchantId.eq(QPGMerchant.pGMerchant.merchantCode))
        .orderBy(orderByCreatedDateDesc()).list(QPGTransaction.pGTransaction.transactionId,
            QPGTransaction.pGTransaction.txnAmount, QPGTransaction.pGTransaction.merchantId,
            QPGTransaction.pGTransaction.createdDate);
    return infoList.size();
  }

  @Override
  public LitleEFTRequest getLitleExecutedTransactionsOnMerchantCodeAndPayoutFrequency(
      String merchantCode, Integer payoutFrequencyDays) {
    LitleEFTRequest litleEFTRequest = new LitleEFTRequest();
    List<LitleEFTDTO> txntList = null;
    LitleEFTDTO litleEFTDTO = null;
    Date now = new Date();
    Timestamp fromDate = new Timestamp(now.getTime() - Long.parseLong("86400000") * payoutFrequencyDays);
    Timestamp todate = new Timestamp(now.getTime());
    JPAQuery query = new JPAQuery(entityManager);
    List<Tuple> infoList = query.from(QPGTransaction.pGTransaction)
        .where(QPGTransaction.pGTransaction.eftStatus.eq(PGConstants.LITLE_EXECUTED),
            QPGTransaction.pGTransaction.merchantId.eq(merchantCode),
            QPGTransaction.pGTransaction.updatedDate.between(fromDate, todate))
        .orderBy(orderByCreatedDateDesc()).list(QPGTransaction.pGTransaction.transactionId,
            QPGTransaction.pGTransaction.txnAmount, QPGTransaction.pGTransaction.merchantId,
            QPGTransaction.pGTransaction.createdDate);
    if (StringUtil.isListNotNullNEmpty(infoList)) {
      txntList = new ArrayList<>();
      for (Tuple tuple : infoList) {
        litleEFTDTO = new LitleEFTDTO();
        litleEFTDTO.setAmount(tuple.get(QPGTransaction.pGTransaction.txnAmount));
        litleEFTDTO.setMerchantCode((tuple.get(QPGTransaction.pGTransaction.merchantId)));
        litleEFTDTO.setDateTime((tuple.get(QPGTransaction.pGTransaction.createdDate)));
        litleEFTDTO.setTransactionId(tuple.get(QPGTransaction.pGTransaction.transactionId));
        litleEFTDTO.setModeOfExecution(Constants.SCHEDULER_EFT_PROCESS);
        txntList.add(litleEFTDTO);
      }
    }
    litleEFTRequest.setLitleEFTDTOs(txntList);
    return litleEFTRequest;
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
  
  private int getTotalRecords(GetTransactionsListRequest getTransactionsListRequest) {
	  Timestamp startDate = null;
	    Timestamp endDate = null;
  QPGMerchant pp = new QPGMerchant("pp");
  JPAQuery query = new JPAQuery(entityManager);
  startDate = getStartDate(getTransactionsListRequest, startDate);
    endDate = getEndDate(getTransactionsListRequest, endDate);
  if (null != getTransactionsListRequest.getMerchant_code()) {
    List<Tuple> infoList = query.distinct()
        .from(QPGTransaction.pGTransaction, QPGMerchant.pGMerchant, QPGAccount.pGAccount,
            QPGOnlineTxnLog.pGOnlineTxnLog, QPGAccountFeeLog.pGAccountFeeLog)
        .where(QPGMerchant.pGMerchant.merchantCode.eq(QPGTransaction.pGTransaction.merchantId),
            QPGTransaction.pGTransaction.transactionId
                .eq(QPGOnlineTxnLog.pGOnlineTxnLog.pgTxnId.stringValue()),
            QPGTransaction.pGTransaction.transactionId
                .eq(QPGAccountFeeLog.pGAccountFeeLog.transactionId),
            QPGAccount.pGAccount.entityId.eq(QPGMerchant.pGMerchant.merchantCode),
            QPGTransaction.pGTransaction.merchantSettlementStatus
                .eq(getTransactionsListRequest.getSettlementStatus()),
            QPGMerchant.pGMerchant.merchantCode
                .eq(QPGTransaction.pGTransaction.merchantId.stringValue()),
            QPGTransaction.pGTransaction.transactionId
                .eq(QPGAccountFeeLog.pGAccountFeeLog.transactionId),
            QPGTransaction.pGTransaction.transactionType.equalsIgnoreCase("sale"),
            QPGAccount.pGAccount.entityId.eq(QPGMerchant.pGMerchant.merchantCode),
            QPGTransaction.pGTransaction.merchantSettlementStatus
                .eq(getTransactionsListRequest.getSettlementStatus()),
            isRevenueType(getTransactionsListRequest.getEntryMode()),
            isValidDate(startDate, endDate),
            QPGMerchant.pGMerchant.merchantCode.eq(getTransactionsListRequest.getMerchant_code())
                .or((QPGMerchant.pGMerchant.parentMerchantId.in(new JPASubQuery().from(pp)
                    .where(pp.merchantCode.eq(getTransactionsListRequest.getMerchant_code()))
                    .list(pp.id)))))
        .orderBy(orderByCreatedDateDesc()).list(QPGTransaction.pGTransaction.transactionId,
            QPGTransaction.pGTransaction.txnTotalAmount,
            QPGTransaction.pGTransaction.txnDescription,
            QPGTransaction.pGTransaction.paymentMethod, QPGTransaction.pGTransaction.txnAmount,
            QPGTransaction.pGTransaction.createdDate,
            QPGTransaction.pGTransaction.transactionType, QPGTransaction.pGTransaction.authId,
            QPGTransaction.pGTransaction.refTransactionId,
            QPGTransaction.pGTransaction.terminalId, QPGTransaction.pGTransaction.invoiceNumber,
            QPGTransaction.pGTransaction.panMasked, QPGTransaction.pGTransaction.merchantId,
            QPGTransaction.pGTransaction.acqChannel, QPGTransaction.pGTransaction.acqTxnMode,
            QPGTransaction.pGTransaction.feeAmount, QPGTransaction.pGTransaction.processor,
            QPGTransaction.pGTransaction.txnMode,
            QPGTransaction.pGTransaction.merchantSettlementStatus,
            QPGTransaction.pGTransaction.status, QPGTransaction.pGTransaction.cardHolderName,
            QPGMerchant.pGMerchant.userName, QPGMerchant.pGMerchant.businessName,
            QPGMerchant.pGMerchant.merchantCode, QPGMerchant.pGMerchant.merchantType,
            QPGAccount.pGAccount.accountNum, QPGAccount.pGAccount.entityType,
            QPGAccount.pGAccount.currency, QPGAccountFeeLog.pGAccountFeeLog.merchantFee,
            QPGAccountFeeLog.pGAccountFeeLog.chatakFee,
            QPGAccountFeeLog.pGAccountFeeLog.parentEntityId,
            QPGTransaction.pGTransaction.deviceLocalTxnTime,
            QPGTransaction.pGTransaction.timeZoneOffset);
      return (StringUtils.isListNotNullNEmpty(infoList) ? infoList.size() : 0);
    
      } else {
    JPAQuery merchantCodeNullQuery = new JPAQuery(entityManager);
    List<Tuple> infoList = merchantCodeNullQuery.distinct()
        .from(QPGTransaction.pGTransaction, QPGMerchant.pGMerchant, QPGAccount.pGAccount,
            QPGOnlineTxnLog.pGOnlineTxnLog, QPGAccountFeeLog.pGAccountFeeLog)
        .where(
            QPGMerchant.pGMerchant.merchantCode
                .eq(QPGTransaction.pGTransaction.merchantId.stringValue())
                .and(QPGTransaction.pGTransaction.transactionId
                    .eq(QPGOnlineTxnLog.pGOnlineTxnLog.pgTxnId.stringValue()))
            .and(QPGTransaction.pGTransaction.transactionId
                .eq(QPGAccountFeeLog.pGAccountFeeLog.transactionId))
            .and(QPGAccount.pGAccount.entityId.eq(QPGMerchant.pGMerchant.merchantCode)),
            QPGTransaction.pGTransaction.merchantSettlementStatus
                .eq(getTransactionsListRequest.getSettlementStatus()),
                isValidDate(startDate, endDate),
            isRevenueType(getTransactionsListRequest.getEntryMode()))
        .orderBy(orderByCreatedDateDesc()).list(QPGMerchant.pGMerchant.userName,
            QPGMerchant.pGMerchant.businessName, QPGAccount.pGAccount.accountNum,
            QPGAccount.pGAccount.entityType, QPGAccount.pGAccount.currency,
            QPGTransaction.pGTransaction.createdDate, QPGTransaction.pGTransaction.txnTotalAmount,
            QPGTransaction.pGTransaction.txnDescription,
            QPGTransaction.pGTransaction.paymentMethod,
            QPGTransaction.pGTransaction.transactionId, QPGTransaction.pGTransaction.txnAmount,
            QPGTransaction.pGTransaction.transactionType, QPGTransaction.pGTransaction.authId,
            QPGTransaction.pGTransaction.refTransactionId,
            QPGTransaction.pGTransaction.terminalId, QPGTransaction.pGTransaction.invoiceNumber,
            QPGTransaction.pGTransaction.panMasked, QPGTransaction.pGTransaction.merchantId,
            QPGTransaction.pGTransaction.acqChannel, QPGTransaction.pGTransaction.acqTxnMode,
            QPGTransaction.pGTransaction.feeAmount, QPGTransaction.pGTransaction.processor,
            QPGTransaction.pGTransaction.txnMode,
            QPGTransaction.pGTransaction.merchantSettlementStatus,
            QPGTransaction.pGTransaction.status, QPGAccountFeeLog.pGAccountFeeLog.merchantFee,
            QPGAccountFeeLog.pGAccountFeeLog.chatakFee,
            QPGAccountFeeLog.pGAccountFeeLog.parentEntityId,QPGTransaction.pGTransaction.deviceLocalTxnTime,
            QPGTransaction.pGTransaction.timeZoneOffset);
    return (StringUtils.isListNotNullNEmpty(infoList) ? infoList.size() : 0);
   }
 }
}
