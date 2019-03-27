/**
 * 
 */
package com.chatak.pg.acq.dao.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import com.chatak.pg.acq.dao.AccountTransactionsDao;
import com.chatak.pg.acq.dao.MerchantDao;
import com.chatak.pg.acq.dao.RefundTransactionDao;
import com.chatak.pg.acq.dao.TransactionDao;
import com.chatak.pg.acq.dao.model.PGAccountHistory;
import com.chatak.pg.acq.dao.model.PGAccountTransactions;
import com.chatak.pg.acq.dao.model.QPGAccount;
import com.chatak.pg.acq.dao.model.QPGAccountTransactions;
import com.chatak.pg.acq.dao.model.QPGTransaction;
import com.chatak.pg.acq.dao.repository.AccountHistoryRepository;
import com.chatak.pg.acq.dao.repository.AccountTransactionsRepository;
import com.chatak.pg.constants.AccountTransactionCode;
import com.chatak.pg.constants.PGConstants;
import com.chatak.pg.dao.util.StringUtil;
import com.chatak.pg.model.AccountTransactionDTO;
import com.chatak.pg.user.bean.GetTransactionsListRequest;
import com.chatak.pg.user.bean.GetTransactionsListResponse;
import com.chatak.pg.util.CommonUtil;
import com.chatak.pg.util.Constants;
import com.chatak.pg.util.DateUtil;
import com.chatak.pg.util.RandomGenerator;
import com.chatak.pg.util.StringUtils;
import com.mysema.query.Tuple;
import com.mysema.query.jpa.impl.JPAQuery;
import com.mysema.query.types.OrderSpecifier;
import com.mysema.query.types.expr.BooleanExpression;

/**
 * @Author: Girmiti Software
 * @Date: Feb 29, 2016
 * @Time: 11:30:51 AM
 * @Version: 1.0
 * @Comments:
 */
@Repository
public class AccountTransactionsDaoImpl implements AccountTransactionsDao {
  private static Logger log = Logger.getLogger(AccountTransactionsDaoImpl.class);

  @Autowired
  AccountTransactionsRepository accountTransactionsRepository;

  @Autowired
  MerchantDao merchantDao;

  @Autowired
  TransactionDao transactionDao;

  @PersistenceContext
  private EntityManager entityManager;

  @Autowired
  AccountHistoryRepository accountHistoryRepository;
  
  @Autowired
  RefundTransactionDao refundTransactionDao;

  /**
   * @param accountTransactionId
   * @return
   */
  @Override
  public boolean isDuplicateAccountTransactionId(String accountTransactionId) {

    List<PGAccountTransactions> accountTransactionsList =
        accountTransactionsRepository.findByAccountTransactionId(accountTransactionId);
    return (CommonUtil.isListNotNullAndEmpty(accountTransactionsList));
  }

  /**
   * @param pgAccountTransactions
   * @return
   */
  @Override
  public PGAccountTransactions createOrUpdate(PGAccountTransactions pgAccountTransactions) {
    return accountTransactionsRepository.save(pgAccountTransactions);
  }
  
  /**
 * @param pgAccountTransactions
 * @return
 */
@Override
  public List<PGAccountTransactions> createOrUpdate(PGAccountTransactions... pgAccountTransactions) {
    return accountTransactionsRepository.save(Arrays.asList(pgAccountTransactions));
  }

  /**
   * @return
   */
  @Override
  public String generateAccountTransactionId() {
    String accountTransactionId =
        RandomGenerator.generateRandNumeric(PGConstants.LENGTH_TXN_REF_NUM);
    if (isDuplicateAccountTransactionId(accountTransactionId)) {
      accountTransactionId = generateAccountTransactionId();
    }
    return accountTransactionId;
  }

  /**
   * @param transferId
   * @return
   */
  @Override
  public PGAccountTransactions getAccountTransactionByTransferId(String transferId) {
    return accountTransactionsRepository.findByPgTransferId(transferId);
  }

  /**
   * @param transactionId
   * @return
   */
  @Override
  public List<PGAccountTransactions> getAccountTransactionsOnTransactionId(String pgTransactionId) {
    return accountTransactionsRepository.findByPgTransactionId(pgTransactionId);
  }

  /**
   * @param pgTransactionId
   * @param transactionType
   * @return
   */
  @Override
  public List<PGAccountTransactions> getAccountTransactionsOnTransactionIdAndTransactionType(
      String pgTransactionId, String transactionType) {
    return accountTransactionsRepository.findByPgTransactionIdAndTransactionType(pgTransactionId,
        transactionType);
  }

  /**
   * @param pgTransactionId
   * @param transactionCode
   * @return
   */
  @Override
  public PGAccountTransactions getAccountTransactionsOnTransactionIdAndTransactionCode(
      String pgTransactionId, String transactionCode) {
    return accountTransactionsRepository.findByPgTransactionIdAndTransactionCode(pgTransactionId,
        transactionCode);
  }

  /**
   * @param getTransactionsListRequest
   * @return
   */
  @Override
  public GetTransactionsListResponse getAccountTransactions(
      GetTransactionsListRequest getTransactionsListRequest) {

    log.info("Entering ::AccountTransactionsDaoImpl ::getAccountTransactions");
    int limit = 0;
    int offset = 0;
    Timestamp fromDate = DateUtil.getStartDayTimestamp(getTransactionsListRequest.getFrom_date(),
        PGConstants.DD_MM_YYYY);
    Timestamp toDate = DateUtil.getEndDayTimestamp(getTransactionsListRequest.getTo_date(),
        PGConstants.DD_MM_YYYY);
    AccountTransactionDTO accountTransactionDTO = null;
    GetTransactionsListResponse getTransactionsListResponse = null;
    List<AccountTransactionDTO> accountTransactionDTOs = null;
    try {
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
        limit = Constants.MAX_PAGE_SIZE;
      } else {
        offset = (getTransactionsListRequest.getPageIndex() - 1)
            * getTransactionsListRequest.getPageSize();
        limit = getTransactionsListRequest.getPageSize();
      }
      List<Tuple> tupleList = null;
      JPAQuery query = new JPAQuery(entityManager);
      if (!getTransactionsListRequest.getTransaction_type().equals(AccountTransactionCode.MANUAL_CREDIT)
          && !getTransactionsListRequest.getTransaction_type().equals(AccountTransactionCode.MANUAL_DEBIT)
          && getTransactionsListRequest.getAcqChannel().equals("web")) {
        tupleList = query.from(QPGAccountTransactions.pGAccountTransactions, QPGAccount.pGAccount)
            .where(
                QPGAccountTransactions.pGAccountTransactions.merchantCode
                    .eq(QPGAccount.pGAccount.entityId),
                QPGAccountTransactions.pGAccountTransactions.transactionCode
                    .in(getTransactionsListRequest.getTransactionCodeList())
                    .and(QPGAccountTransactions.pGAccountTransactions.transactionType.in(
                        AccountTransactionCode.MANUAL_CREDIT, AccountTransactionCode.MANUAL_DEBIT)),
                isStatusLike(getTransactionsListRequest.getSettlementStatus()),
                isMerchantCode(getTransactionsListRequest.getMerchant_code()),
                isDateLike(fromDate, toDate))
            .offset(offset).limit(limit).orderBy(orderByCreatedDateDescVirtualAcc())
            .list(QPGAccountTransactions.pGAccountTransactions.transactionTime,
                QPGAccountTransactions.pGAccountTransactions.processedTime,
                QPGAccountTransactions.pGAccountTransactions.accountTransactionId,
                QPGAccountTransactions.pGAccountTransactions.transactionType,
                QPGAccountTransactions.pGAccountTransactions.description,
                QPGAccountTransactions.pGAccountTransactions.debit,
                QPGAccountTransactions.pGAccountTransactions.merchantCode,
                QPGAccountTransactions.pGAccountTransactions.transactionCode,
                QPGAccountTransactions.pGAccountTransactions.credit,
                QPGAccountTransactions.pGAccountTransactions.currentBalance,
                QPGAccountTransactions.pGAccountTransactions.status,
                QPGAccountTransactions.pGAccountTransactions.id,
                QPGAccountTransactions.pGAccountTransactions.pgTransactionId,
                QPGAccount.pGAccount.currency,
                QPGAccountTransactions.pGAccountTransactions.deviceLocalTxnTime,
                QPGAccountTransactions.pGAccountTransactions.timeZoneOffset);
      } else {
        tupleList = query
            .from(QPGAccountTransactions.pGAccountTransactions, QPGAccount.pGAccount,
                QPGTransaction.pGTransaction)
            .where(
                QPGAccountTransactions.pGAccountTransactions.merchantCode
                    .eq(QPGAccount.pGAccount.entityId)
                    .and(QPGTransaction.pGTransaction.merchantSettlementStatus.in(
                        getSettlementStatusList(getTransactionsListRequest.getSettlementStatus())))
                    .and(QPGAccountTransactions.pGAccountTransactions.pgTransactionId
                        .eq(QPGTransaction.pGTransaction.id.stringValue()))
                    .and(QPGAccountTransactions.pGAccountTransactions.transactionCode
                        .in(getTransactionsListRequest.getTransactionCodeList()))
                    .and(QPGAccountTransactions.pGAccountTransactions.transactionType.in(
                        PGConstants.TXN_TYPE_VOID, PGConstants.TXN_TYPE_REFUND,
                        PGConstants.TXN_TYPE_SALE))
                    .and(QPGAccountTransactions.pGAccountTransactions.status.in(
                        getSettlementStatusList(getTransactionsListRequest.getSettlementStatus()))),
                isStatusLike(getTransactionsListRequest.getSettlementStatus()),
                isValidDate(fromDate, toDate),
                isMerchantCode(getTransactionsListRequest.getMerchant_code()))
            .offset(offset).limit(limit).orderBy(orderByCreatedDateDescVirtualAcc())
            .list(QPGAccountTransactions.pGAccountTransactions.transactionTime,
                QPGAccountTransactions.pGAccountTransactions.processedTime,
                QPGAccountTransactions.pGAccountTransactions.accountTransactionId,
                QPGAccountTransactions.pGAccountTransactions.transactionType,
                QPGAccountTransactions.pGAccountTransactions.description,
                QPGAccountTransactions.pGAccountTransactions.debit,
                QPGAccountTransactions.pGAccountTransactions.merchantCode,
                QPGAccountTransactions.pGAccountTransactions.transactionCode,
                QPGAccountTransactions.pGAccountTransactions.credit,
                QPGAccountTransactions.pGAccountTransactions.currentBalance,
                QPGAccountTransactions.pGAccountTransactions.status,
                QPGAccountTransactions.pGAccountTransactions.id,
                QPGAccountTransactions.pGAccountTransactions.pgTransactionId,
                QPGAccount.pGAccount.currency,QPGAccountTransactions.pGAccountTransactions.deviceLocalTxnTime,
                QPGAccountTransactions.pGAccountTransactions.timeZoneOffset);
      }
      if (!CollectionUtils.isEmpty(tupleList)) {
        getTransactionsListResponse = validateListAndSetAccountTransactionDTO(totalRecords, tupleList);
      }
    } catch (Exception e) {
      log.info("ERROR ::AccountTransactionsDaoImpl :: getAccountTransactions " + e);
    }
    return getTransactionsListResponse;
  }

private GetTransactionsListResponse validateListAndSetAccountTransactionDTO(Integer totalRecords,
		List<Tuple> tupleList) {
	GetTransactionsListResponse getTransactionsListResponse = new GetTransactionsListResponse();
	List<AccountTransactionDTO> accountTransactionDTOs = new ArrayList<>();
	for (Tuple tuple : tupleList) {
	  AccountTransactionDTO accountTransactionDTO = new AccountTransactionDTO();
		accountTransactionDTO.setTransactionId(
			      tuple.get(QPGAccountTransactions.pGAccountTransactions.accountTransactionId));
	  accountTransactionDTO.setId(tuple.get(QPGAccountTransactions.pGAccountTransactions.id));
	  accountTransactionDTO.setTransactionTime(
	      null != tuple.get(QPGAccountTransactions.pGAccountTransactions.transactionTime)
	          ? DateUtil.toDateStringFormat(
	              tuple.get(QPGAccountTransactions.pGAccountTransactions.transactionTime),
	              PGConstants.DATE_FORMAT)
	          : "");
	  accountTransactionDTO
      .setCurrentBalance(
          null != tuple.get(QPGAccountTransactions.pGAccountTransactions.currentBalance)
              ? StringUtils.amountToString(
                  tuple.get(QPGAccountTransactions.pGAccountTransactions.currentBalance))
              : "");
	  accountTransactionDTO.setProcessedTime(
	      null != tuple.get(QPGAccountTransactions.pGAccountTransactions.processedTime)
	          ? DateUtil.toDateStringFormat(
	              tuple.get(QPGAccountTransactions.pGAccountTransactions.processedTime),
	              PGConstants.DATE_FORMAT)
	          : "");
	  accountTransactionDTO
      .setDescription(tuple.get(QPGAccountTransactions.pGAccountTransactions.description));
	  accountTransactionDTO.setType(getTransactionType(
	      tuple.get(QPGAccountTransactions.pGAccountTransactions.transactionType)));
	  accountTransactionDTO.setDebit(
	      null != tuple.get(QPGAccountTransactions.pGAccountTransactions.debit) ? StringUtils
	          .amountToString(tuple.get(QPGAccountTransactions.pGAccountTransactions.debit))
	          : "");
	  accountTransactionDTO.setCredit(
	      null != tuple.get(QPGAccountTransactions.pGAccountTransactions.credit) ? StringUtils
	          .amountToString(tuple.get(QPGAccountTransactions.pGAccountTransactions.credit))
	          : "");
	  accountTransactionDTO
	      .setStatus(tuple.get(QPGAccountTransactions.pGAccountTransactions.status));
	  accountTransactionDTO.setPgTransactionId(
		      tuple.get(QPGAccountTransactions.pGAccountTransactions.pgTransactionId));
	  accountTransactionDTO.setMerchantCode(
	      tuple.get(QPGAccountTransactions.pGAccountTransactions.merchantCode));
	  accountTransactionDTO.setCurrency(tuple.get(QPGAccount.pGAccount.currency));
	  accountTransactionDTO.setTransactionCode(
	      tuple.get(QPGAccountTransactions.pGAccountTransactions.transactionCode));
	  accountTransactionDTO.setDeviceLocalTxnTime(tuple.get(QPGAccountTransactions.pGAccountTransactions.deviceLocalTxnTime));
	  accountTransactionDTO.setTimeZoneOffset(tuple.get(QPGAccountTransactions.pGAccountTransactions.timeZoneOffset));
	  validateAccountTransactionType(accountTransactionDTO, tuple);
	  accountTransactionDTOs.add(accountTransactionDTO);
	}
	getTransactionsListResponse.setTotalResultCount(totalRecords);
	getTransactionsListResponse.setAccountTransactionList(accountTransactionDTOs);
	return getTransactionsListResponse;
}

  private OrderSpecifier<Timestamp> orderByCreatedDateDescVirtualAcc() {
    return QPGAccountTransactions.pGAccountTransactions.createdDate.desc();
  }

  private BooleanExpression isValidDate(Timestamp fromDate, Timestamp toDate) {
    if ((fromDate != null && toDate == null)) {
      return QPGAccountTransactions.pGAccountTransactions.createdDate.gt(fromDate);
    } else if ((fromDate == null && toDate != null)) {
      return QPGAccountTransactions.pGAccountTransactions.createdDate.lt(toDate);
    } 
    else if (fromDate == null)
      return null;

    return QPGAccountTransactions.pGAccountTransactions.createdDate.between(fromDate, toDate);
  }

  private BooleanExpression isStatusLike(String status) {
    if (!StringUtil.isNullAndEmpty(status)) {
      if (status.equals(PGConstants.PG_SETTLEMENT_EXECUTED)) {
        return QPGAccountTransactions.pGAccountTransactions.status
            .in(PGConstants.PG_SETTLEMENT_EXECUTED, PGConstants.PG_TXN_REFUNDED);
      } else {
        return QPGAccountTransactions.pGAccountTransactions.status.eq(status);
      }
    } else {
      return null;
    }
  }

  private Integer getTotalNumberOfRecordsOnSearch(
      GetTransactionsListRequest getTransactionsListRequest) {
    log.info("Entering ::AccountTransactionsDaoImpl ::getTotalNumberOfRecordsOnSearch ");
    try {
      if (!getTransactionsListRequest.getTransaction_type().equals(AccountTransactionCode.MANUAL_CREDIT)
          && !getTransactionsListRequest.getTransaction_type().equals(AccountTransactionCode.MANUAL_DEBIT)
          && getTransactionsListRequest.getAcqChannel().equals("web")) {
        JPAQuery query = new JPAQuery(entityManager);
        List<Long> list = query.from(QPGAccountTransactions.pGAccountTransactions,QPGAccount.pGAccount)
            .where(
                QPGAccountTransactions.pGAccountTransactions.merchantCode.eq(QPGAccount.pGAccount.entityId),
                QPGAccountTransactions.pGAccountTransactions.transactionCode
                    .in(getTransactionsListRequest.getTransactionCodeList())
                    .and(QPGAccountTransactions.pGAccountTransactions.transactionType.in(
                        AccountTransactionCode.MANUAL_CREDIT, AccountTransactionCode.MANUAL_DEBIT)),
            isStatusLike(getTransactionsListRequest.getSettlementStatus()),
            isMerchantCode(getTransactionsListRequest.getMerchant_code()))
            .orderBy(orderByCreatedDateDescVirtualAcc())
            .list(QPGAccountTransactions.pGAccountTransactions.id);
        log.info("Exiting ::AccountTransactionsDaoImpl ::getTotalNumberOfRecordsOnSearch ");
        return (StringUtils.isListNotNullNEmpty(list) ? list.size() : 0);
      } else {
        JPAQuery query = new JPAQuery(entityManager);
        List<Long> list = query
            .from(QPGAccountTransactions.pGAccountTransactions, QPGAccount.pGAccount,
                QPGTransaction.pGTransaction)
            .where(
                QPGAccountTransactions.pGAccountTransactions.merchantCode
                    .eq(QPGAccount.pGAccount.entityId)
                    .and(QPGTransaction.pGTransaction.merchantSettlementStatus.in(
                        getSettlementStatusList(getTransactionsListRequest.getSettlementStatus())))
                    .and(QPGAccountTransactions.pGAccountTransactions.pgTransactionId
                        .eq(QPGTransaction.pGTransaction.id.stringValue()))
                    .and(QPGAccountTransactions.pGAccountTransactions.transactionCode
                        .in(getTransactionsListRequest.getTransactionCodeList()))
                    .and(QPGAccountTransactions.pGAccountTransactions.transactionType.in(
                        PGConstants.TXN_TYPE_VOID, PGConstants.TXN_TYPE_REFUND,
                        PGConstants.TXN_TYPE_SALE))
                    .and(QPGAccountTransactions.pGAccountTransactions.status.in(
                        getSettlementStatusList(getTransactionsListRequest.getSettlementStatus()))),
                isStatusLike(getTransactionsListRequest.getSettlementStatus()),
                isMerchantCode(getTransactionsListRequest.getMerchant_code()))
            .orderBy(orderByCreatedDateDescVirtualAcc())
            .list(QPGAccountTransactions.pGAccountTransactions.id);
        log.info("Exiting ::AccountTransactionsDaoImpl ::getTotalNumberOfRecordsOnSearch ");
        return (StringUtils.isListNotNullNEmpty(list) ? list.size() : 0);
      }

    } catch (Exception e) {
      log.error("ERROR ::AccountTransactionsDaoImpl ::getTotalNumberOfRecordsOnSearch " + e);
    }
    return 0;
  }

  public String getTransactionType(String transactionType) {

    switch (transactionType) {
      case PGConstants.TXN_TYPE_SALE:
        return "S";
      case PGConstants.TXN_TYPE_VOID:
        return "V";
      case PGConstants.TXN_TYPE_REFUND:
        return "R";
      case PGConstants.TXN_TYPE_AUTH:
        return "A";
      case AccountTransactionCode.FT_BANK:
        return "E";
      case AccountTransactionCode.FT_CHECK:
        return "C";
      case AccountTransactionCode.MANUAL_CREDIT:
      case AccountTransactionCode.MANUAL_DEBIT:
        return "M";
      case AccountTransactionCode.ACCOUNT_CREDIT:
      case AccountTransactionCode.ACCOUNT_DEBIT:
        return "T";
      default:
        return "";
    }
  }

  private BooleanExpression isMerchantCode(String merchantCode) {
    return (merchantCode != null && !"".equals(merchantCode))
        ? QPGAccountTransactions.pGAccountTransactions.merchantCode.eq(merchantCode) : null;
  }
  
  /**
   * @param accountTransactionId
   * @param merchantCode
   * @return
   */
  @Override
  public String getSaleAccountTransactionId(String accountTransactionId, String merchantCode) {
    List<PGAccountTransactions> list =
        accountTransactionsRepository.findByAccountTransactionIdAndTransactionTypeAndMerchantCode(
            accountTransactionId, PGConstants.TXN_TYPE_SALE, merchantCode);
    if (CommonUtil.isListNotNullAndEmpty(list)) {
      PGAccountTransactions pgAccountTransactions = list.get(0);
      return pgAccountTransactions.getPgTransactionId();
    }
    return null;
  }

  @Override
  public List<PGAccountTransactions> getAccountTransactions(String accountTransactionId) {
    return accountTransactionsRepository.findByAccountTransactionId(accountTransactionId);
  }

  public GetTransactionsListResponse getAccountAllTransactions(
      GetTransactionsListRequest getTransactionsListRequest) {

    log.info("Entering :: AccountTransactionsDaoImpl :: getAccountAllTransactions");
    int offset = 0;
    int limit = Constants.DEFAULT_DASHBOARD_PAGE_SIZE;
    GetTransactionsListResponse getTransactionsListResponse = null;
    List<AccountTransactionDTO> accountTransactionDTOs = null;
    try {
      JPAQuery query = new JPAQuery(entityManager);
      List<Tuple> tupleList = query.from(QPGAccountTransactions.pGAccountTransactions)
          .where(
              QPGAccountTransactions.pGAccountTransactions.transactionCode
                  .in(getTransactionsListRequest.getTransactionCodeList()),
              isStatusLike(getTransactionsListRequest.getSettlementStatus()),
              isAccountNumberEq(getTransactionsListRequest.getAccountNumber()))
          .offset(offset).limit(limit)
          .orderBy(orderByVirtualAccDate(getTransactionsListRequest.getSettlementStatus()),
              QPGAccountTransactions.pGAccountTransactions.id.desc())
          .list(QPGAccountTransactions.pGAccountTransactions.transactionTime,
              QPGAccountTransactions.pGAccountTransactions.processedTime,
              QPGAccountTransactions.pGAccountTransactions.accountTransactionId,
              QPGAccountTransactions.pGAccountTransactions.transactionType,
              QPGAccountTransactions.pGAccountTransactions.description,
              QPGAccountTransactions.pGAccountTransactions.debit,
              QPGAccountTransactions.pGAccountTransactions.merchantCode,
              QPGAccountTransactions.pGAccountTransactions.transactionCode,
              QPGAccountTransactions.pGAccountTransactions.credit,
              QPGAccountTransactions.pGAccountTransactions.currentBalance,
              QPGAccountTransactions.pGAccountTransactions.status,
              QPGAccountTransactions.pGAccountTransactions.id,
              QPGAccountTransactions.pGAccountTransactions.createdDate,
              QPGAccountTransactions.pGAccountTransactions.updatedTime,
              QPGAccountTransactions.pGAccountTransactions.accountNumber,
              QPGAccountTransactions.pGAccountTransactions.pgTransactionId);
      if (!CollectionUtils.isEmpty(tupleList)) {
        Map<String, String> merchantMap = merchantDao.getAllMerchantMap();
        getTransactionsListResponse = new GetTransactionsListResponse();
        accountTransactionDTOs = new ArrayList<>();
        for (Tuple tuple : tupleList) {
          setAccountTransactionDetails(accountTransactionDTOs, merchantMap, tuple);
        }
        getTransactionsListResponse.setAccountTransactionList(accountTransactionDTOs);
      }
    } catch (Exception e) {
      log.info("ERROR :: AccountTransactionsDaoImpl :: getAccountAllTransactions " + e);
    }
    return getTransactionsListResponse;
  }

  private void setAccountTransactionDetails(List<AccountTransactionDTO> accountTransactionDTOs,
		Map<String, String> merchantMap, Tuple tuple) {
	AccountTransactionDTO accountTransactionDTO;
	accountTransactionDTO = new AccountTransactionDTO();
	  accountTransactionDTO.setId(tuple.get(QPGAccountTransactions.pGAccountTransactions.id));
	  accountTransactionDTO.setTransactionId(
	      tuple.get(QPGAccountTransactions.pGAccountTransactions.accountTransactionId));
	  accountTransactionDTO.setTransactionTime(
	      null != tuple.get(QPGAccountTransactions.pGAccountTransactions.transactionTime)
	          ? DateUtil.toDateStringFormat(
	              tuple.get(QPGAccountTransactions.pGAccountTransactions.transactionTime),
	              DateUtil.VIEW_DATE_TIME_FORMAT)
	          : "");
	  accountTransactionDTO.setProcessedTime(
	      null != tuple.get(QPGAccountTransactions.pGAccountTransactions.processedTime)
	          ? DateUtil.toDateStringFormat(
	              tuple.get(QPGAccountTransactions.pGAccountTransactions.processedTime),
	              DateUtil.VIEW_DATE_TIME_FORMAT)
	          : "");
	  accountTransactionDTO.setType(getTransactionType(
	      tuple.get(QPGAccountTransactions.pGAccountTransactions.transactionType)));
	  accountTransactionDTO
	      .setDescription(tuple.get(QPGAccountTransactions.pGAccountTransactions.description));
	  accountTransactionDTO.setDebit(
	      null != tuple.get(QPGAccountTransactions.pGAccountTransactions.debit) ? StringUtils
	          .amountToString(tuple.get(QPGAccountTransactions.pGAccountTransactions.debit))
	          : "");
	  accountTransactionDTO.setCredit(
	      null != tuple.get(QPGAccountTransactions.pGAccountTransactions.credit) ? StringUtils
	          .amountToString(tuple.get(QPGAccountTransactions.pGAccountTransactions.credit))
	          : "");
	  accountTransactionDTO
	      .setCurrentBalance(
	          null != tuple.get(QPGAccountTransactions.pGAccountTransactions.currentBalance)
	              ? StringUtils.amountToString(
	                  tuple.get(QPGAccountTransactions.pGAccountTransactions.currentBalance))
	              : "");
	  accountTransactionDTO
	      .setStatus(tuple.get(QPGAccountTransactions.pGAccountTransactions.status));
	  accountTransactionDTO.setMerchantCode(
	      tuple.get(QPGAccountTransactions.pGAccountTransactions.merchantCode));
	  accountTransactionDTO
	      .setMerchantCompanyName(merchantMap.get(accountTransactionDTO.getMerchantCode()));
	  accountTransactionDTO.setTransactionCode(
	      tuple.get(QPGAccountTransactions.pGAccountTransactions.transactionCode));
	  accountTransactionDTO.setAccountNumber(
	      tuple.get(QPGAccountTransactions.pGAccountTransactions.accountNumber));
	  validateAccountTransactionType(accountTransactionDTO, tuple);
	  accountTransactionDTOs.add(accountTransactionDTO);
  }

  private void validateAccountTransactionType(AccountTransactionDTO accountTransactionDTO, Tuple tuple) {
	if (accountTransactionDTO.getType().equals("S")) {
	    accountTransactionDTO.setRefundStatus(refundTransactionDao.getRefundStatus(
	        tuple.get(QPGAccountTransactions.pGAccountTransactions.pgTransactionId)));
	  }
  }

  private BooleanExpression isAccountNumberEq(String accountNumber) {
    return (accountNumber != null && !"".equals(accountNumber))
        ? ((QPGAccountTransactions.pGAccountTransactions.accountNumber.eq(accountNumber)
            .and(QPGAccountTransactions.pGAccountTransactions.transactionCode
                .ne(AccountTransactionCode.ACCOUNT_CREDIT))).or(
                    (QPGAccountTransactions.pGAccountTransactions.toAccountNumber.eq(accountNumber)
                        .and(QPGAccountTransactions.pGAccountTransactions.transactionCode
                            .eq(AccountTransactionCode.ACCOUNT_CREDIT)))))
        : null;
  }

  private OrderSpecifier<Timestamp> orderByVirtualAccDate(String status) {
    if (PGConstants.PG_SETTLEMENT_EXECUTED.equals(status)) {
      return QPGAccountTransactions.pGAccountTransactions.processedTime.desc();
    }
    return QPGAccountTransactions.pGAccountTransactions.createdDate.desc();
  }

  /**
   * @param transferAccountCreditLog
   */
  @Override
  public PGAccountHistory saveAccountHistory(PGAccountHistory pgActHistory) {
    return accountHistoryRepository.save(pgActHistory);

  }

  /**
   * @param getTransactionsListRequest
   * @return
   */
  @Override
  public GetTransactionsListResponse getManulAccountTransactions(
      GetTransactionsListRequest getTransactionsListRequest) {
    log.info("Entering ::AccountTransactionsDaoImpl :: getManulAccountTransactions");
    int offset = 0;
    int limit = 0;
    Timestamp fromDate = DateUtil.getStartDayTimestamp(getTransactionsListRequest.getFrom_date(),
        PGConstants.DD_MM_YYYY);
    Timestamp toDate = DateUtil.getEndDayTimestamp(getTransactionsListRequest.getTo_date(),
        PGConstants.DD_MM_YYYY);
    GetTransactionsListResponse getTransactionsListResponse = null;
    AccountTransactionDTO accountTransactionDTO = null;
    List<AccountTransactionDTO> accountTransactionDTOs = null;
    try {
      Integer totalRecords = getTransactionsListRequest.getNoOfRecords();

      if (getTransactionsListRequest.getPageIndex() == null
          || getTransactionsListRequest.getPageIndex().intValue() == 1) {
        totalRecords = getTotalNumberOfRecordsOnManulSearch(getTransactionsListRequest);
        getTransactionsListRequest.setNoOfRecords(totalRecords);
      }
      getTransactionsListRequest.setNoOfRecords(totalRecords);
      if (getTransactionsListRequest.getPageIndex() == null
          || getTransactionsListRequest.getPageSize() == null) {
        offset = 0;
        limit = Constants.MAX_PAGE_SIZE;
      } else {
        offset = (getTransactionsListRequest.getPageIndex() - 1)
            * getTransactionsListRequest.getPageSize();
        limit = getTransactionsListRequest.getPageSize();
      }

      List<String> merchantCode = new ArrayList<>();
      if (!StringUtil.isNullAndEmpty(getTransactionsListRequest.getMerchant_code())) {
        String[] merchant = getTransactionsListRequest.getMerchant_code().split("\\|");
        merchantCode = Arrays.asList(merchant);
      }

      JPAQuery query = new JPAQuery(entityManager);
      List<Tuple> tupleList = query
          .from(QPGAccountTransactions.pGAccountTransactions, QPGAccount.pGAccount)
          .where(
              QPGAccountTransactions.pGAccountTransactions.merchantCode
                  .eq(QPGAccount.pGAccount.entityId),
              QPGAccountTransactions.pGAccountTransactions.transactionCode
                  .in(getTransactionsListRequest.getTransactionCodeList())
                  .and(QPGAccountTransactions.pGAccountTransactions.merchantCode.in(merchantCode))
                  .and(QPGAccountTransactions.pGAccountTransactions.transactionType.in(
                      AccountTransactionCode.MANUAL_CREDIT, AccountTransactionCode.MANUAL_DEBIT)),
              isStatusLike(getTransactionsListRequest.getSettlementStatus()),
              isValidDate(fromDate, toDate))
          .offset(offset).limit(limit).orderBy(orderByCreatedDateDescVirtualAcc())
          .list(QPGAccountTransactions.pGAccountTransactions.transactionTime,
              QPGAccountTransactions.pGAccountTransactions.processedTime,
              QPGAccountTransactions.pGAccountTransactions.accountTransactionId,
              QPGAccountTransactions.pGAccountTransactions.transactionType,
              QPGAccountTransactions.pGAccountTransactions.description,
              QPGAccountTransactions.pGAccountTransactions.debit,
              QPGAccountTransactions.pGAccountTransactions.merchantCode,
              QPGAccountTransactions.pGAccountTransactions.transactionCode,
              QPGAccountTransactions.pGAccountTransactions.credit,
              QPGAccountTransactions.pGAccountTransactions.currentBalance,
              QPGAccountTransactions.pGAccountTransactions.status,
              QPGAccountTransactions.pGAccountTransactions.id,
              QPGAccountTransactions.pGAccountTransactions.pgTransactionId,
              QPGAccount.pGAccount.currency,
              QPGAccountTransactions.pGAccountTransactions.deviceLocalTxnTime,
              QPGAccountTransactions.pGAccountTransactions.timeZoneOffset);

      if (!CollectionUtils.isEmpty(tupleList)) {
        getTransactionsListResponse = validateListAndSetAccountTransactionDTO(totalRecords, tupleList);
      }
    } catch (Exception e) {
      log.info("ERROR :: AccountTransactionsDaoImpl :: getManulAccountTransactions " + e);
    }
    return getTransactionsListResponse;
  }

  private Integer getTotalNumberOfRecordsOnManulSearch(
      GetTransactionsListRequest getTransactionsListRequest) {
    log.info("Entering :: AccountTransactionsDaoImpl :: getTotalNumberOfRecordsOnManulSearch ");
    try {
      if (!getTransactionsListRequest.getTransaction_type().equals(AccountTransactionCode.MANUAL_CREDIT)
          && !getTransactionsListRequest.getTransaction_type().equals(AccountTransactionCode.MANUAL_DEBIT)
          && getTransactionsListRequest.getAcqChannel().equalsIgnoreCase("web")) {
        List<String> merchantCode = new ArrayList<>();
        if (!StringUtil.isNullEmpty(getTransactionsListRequest.getMerchant_code())) {
          String[] merchant = getTransactionsListRequest.getMerchant_code().split("\\|");
          merchantCode = Arrays.asList(merchant);
        }

        JPAQuery query = new JPAQuery(entityManager);
        List<Long> list =
            query.from(QPGAccountTransactions.pGAccountTransactions, QPGAccount.pGAccount)
                .where(QPGAccountTransactions.pGAccountTransactions.merchantCode
                    .eq(QPGAccount.pGAccount.entityId),
                QPGAccountTransactions.pGAccountTransactions.transactionCode
                    .in(getTransactionsListRequest.getTransactionCodeList())
                    .and(QPGAccountTransactions.pGAccountTransactions.merchantCode.in(merchantCode))
                    .and(QPGAccountTransactions.pGAccountTransactions.transactionType.in(
                        AccountTransactionCode.MANUAL_CREDIT, AccountTransactionCode.MANUAL_DEBIT)),
                isStatusLike(getTransactionsListRequest.getSettlementStatus()))
            .orderBy(orderByCreatedDateDescVirtualAcc())
            .list(QPGAccountTransactions.pGAccountTransactions.id);
        log.info("Exiting :: AccountTransactionsDaoImpl :: getTotalNumberOfRecordsOnManulSearch ");
        return (StringUtils.isListNotNullNEmpty(list) ? list.size() : 0);
      }
    } catch (Exception e) {
      log.error("ERROR ::AccountTransactionsDaoImpl ::getTotalNumberOfRecordsOnSearch " + e);
    }
    return 0;
  }

  private ArrayList<String> getSettlementStatusList(String status) {
    ArrayList<String> list = new ArrayList<>();
    if (status.equals(PGConstants.PG_SETTLEMENT_EXECUTED)) {
      list.add(PGConstants.PG_SETTLEMENT_EXECUTED);
      list.add(PGConstants.PG_TXN_REFUNDED);
      list.add(Constants.SETTLEMENT_STATUS);
      return list;
    } else {
      list.add(status);
      return list;
    }
  }
  
  private BooleanExpression isDateLike(Timestamp fromDate, Timestamp toDate) {
    if ((fromDate != null && toDate != null)) {
      return QPGAccountTransactions.pGAccountTransactions.createdDate.between(fromDate, toDate);
    } else if(fromDate == null && toDate == null) {
      return null;
    }
    return QPGAccountTransactions.pGAccountTransactions.createdDate.between(fromDate, toDate);
  }

/**
 * @param getTransactionsListRequest
 * @param entityId
 * @return
 */
  @Override
  public GetTransactionsListResponse searchAccountTransactionsForEntityId(GetTransactionsListRequest getTransactionsListRequest,
		  Long entityId, String userType) {
	  log.info("Entering ::AccountTransactionsDaoImpl ::searchAccountTransactionsForEntityId");
	  int limit = 0;
	  int offset = 0;
	  Timestamp fromDate = DateUtil.getStartDayTimestamp(getTransactionsListRequest.getFrom_date(),
			  PGConstants.DD_MM_YYYY);
	  Timestamp toDate = DateUtil.getEndDayTimestamp(getTransactionsListRequest.getTo_date(),
			  PGConstants.DD_MM_YYYY);
	  AccountTransactionDTO accountTransactionDTO = null;
	  GetTransactionsListResponse getTransactionsListResponse = null;
	  List<AccountTransactionDTO> accountTransactionDTOs = null;
	  try {
		  Integer totalRecords = getTransactionsListRequest.getNoOfRecords();

		  if (getTransactionsListRequest.getPageIndex() == null
				  || getTransactionsListRequest.getPageIndex().intValue() == 1) {
			  totalRecords = getTotalNumberOfRecordsOnSearchForEntity(getTransactionsListRequest, entityId, userType);
			  getTransactionsListRequest.setNoOfRecords(totalRecords);
		  }
		  getTransactionsListRequest.setNoOfRecords(totalRecords);
		  if (getTransactionsListRequest.getPageIndex() == null
				  || getTransactionsListRequest.getPageSize() == null) {
			  offset = 0;
			  limit = Constants.MAX_PAGE_SIZE;
		  } else {
			  offset = (getTransactionsListRequest.getPageIndex() - 1)
					  * getTransactionsListRequest.getPageSize();
			  limit = getTransactionsListRequest.getPageSize();
		  }
		  List<Tuple> tupleList = null;
		  JPAQuery query = new JPAQuery(entityManager);
		  if (!getTransactionsListRequest.getTransaction_type().equals(AccountTransactionCode.MANUAL_CREDIT) 
				  && !getTransactionsListRequest.getTransaction_type().equals(AccountTransactionCode.MANUAL_DEBIT)
				  && getTransactionsListRequest.getAcqChannel().equals("web")) {
			  tupleList = query.from(QPGAccountTransactions.pGAccountTransactions, QPGAccount.pGAccount)
					  .where(
							  QPGAccountTransactions.pGAccountTransactions.merchantCode
							  .eq(QPGAccount.pGAccount.entityId),
							  QPGAccountTransactions.pGAccountTransactions.transactionCode
							  .in(getTransactionsListRequest.getTransactionCodeList())
							  .and(QPGAccountTransactions.pGAccountTransactions.transactionType.in(
									  AccountTransactionCode.MANUAL_CREDIT, AccountTransactionCode.MANUAL_DEBIT)),
							  isEntityId(userType, entityId),
							  isStatusLike(getTransactionsListRequest.getSettlementStatus()),
							  isMerchantCode(getTransactionsListRequest.getMerchant_code()),
							  isDateLike(fromDate, toDate))
					  .offset(offset).limit(limit).orderBy(orderByCreatedDateDescVirtualAcc())
					  .list(QPGAccountTransactions.pGAccountTransactions.transactionTime,
							  QPGAccountTransactions.pGAccountTransactions.processedTime,
							  QPGAccountTransactions.pGAccountTransactions.accountTransactionId,
							  QPGAccountTransactions.pGAccountTransactions.transactionType,
							  QPGAccountTransactions.pGAccountTransactions.description,
							  QPGAccountTransactions.pGAccountTransactions.debit,
							  QPGAccountTransactions.pGAccountTransactions.merchantCode,
							  QPGAccountTransactions.pGAccountTransactions.transactionCode,
							  QPGAccountTransactions.pGAccountTransactions.credit,
							  QPGAccountTransactions.pGAccountTransactions.currentBalance,
							  QPGAccountTransactions.pGAccountTransactions.status,
							  QPGAccountTransactions.pGAccountTransactions.id,
							  QPGAccountTransactions.pGAccountTransactions.pgTransactionId,
							  QPGAccount.pGAccount.currency,
							  QPGAccountTransactions.pGAccountTransactions.deviceLocalTxnTime,
							  QPGAccountTransactions.pGAccountTransactions.timeZoneOffset);
		  } else {
			  tupleList = query
					  .from(QPGAccountTransactions.pGAccountTransactions, QPGAccount.pGAccount,
							  QPGTransaction.pGTransaction)
					  .where(
							  QPGAccountTransactions.pGAccountTransactions.merchantCode
							  .eq(QPGAccount.pGAccount.entityId)
							  .and(QPGTransaction.pGTransaction.merchantSettlementStatus.in(
									  getSettlementStatusList(getTransactionsListRequest.getSettlementStatus())))
							  .and(QPGAccountTransactions.pGAccountTransactions.pgTransactionId
									  .eq(QPGTransaction.pGTransaction.id.stringValue()))
							  .and(QPGAccountTransactions.pGAccountTransactions.transactionCode
									  .in(getTransactionsListRequest.getTransactionCodeList()))
							  .and(QPGAccountTransactions.pGAccountTransactions.transactionType.in(
									  PGConstants.TXN_TYPE_VOID, PGConstants.TXN_TYPE_REFUND,
									  PGConstants.TXN_TYPE_SALE))
							  .and(QPGAccountTransactions.pGAccountTransactions.status.in(
									  getSettlementStatusList(getTransactionsListRequest.getSettlementStatus()))),
							  isEntityId(userType, entityId),
							  isStatusLike(getTransactionsListRequest.getSettlementStatus()),
							  isValidDate(fromDate, toDate),
							  isMerchantCode(getTransactionsListRequest.getMerchant_code()))
					  .offset(offset).limit(limit).orderBy(orderByCreatedDateDescVirtualAcc())
					  .list(QPGAccountTransactions.pGAccountTransactions.transactionTime,
							  QPGAccountTransactions.pGAccountTransactions.processedTime,
							  QPGAccountTransactions.pGAccountTransactions.accountTransactionId,
							  QPGAccountTransactions.pGAccountTransactions.transactionType,
							  QPGAccountTransactions.pGAccountTransactions.description,
							  QPGAccountTransactions.pGAccountTransactions.debit,
							  QPGAccountTransactions.pGAccountTransactions.merchantCode,
							  QPGAccountTransactions.pGAccountTransactions.transactionCode,
							  QPGAccountTransactions.pGAccountTransactions.credit,
							  QPGAccountTransactions.pGAccountTransactions.currentBalance,
							  QPGAccountTransactions.pGAccountTransactions.status,
							  QPGAccountTransactions.pGAccountTransactions.id,
							  QPGAccountTransactions.pGAccountTransactions.pgTransactionId,
							  QPGAccount.pGAccount.currency,QPGAccountTransactions.pGAccountTransactions.deviceLocalTxnTime,
							  QPGAccountTransactions.pGAccountTransactions.timeZoneOffset);
		  }
		  if (!CollectionUtils.isEmpty(tupleList)) {
			  getTransactionsListResponse = validateListAndSetAccountTransactionDTO(totalRecords, tupleList);
		  }
	  } catch (Exception e) {
		  log.info("ERROR ::AccountTransactionsDaoImpl :: searchAccountTransactionsForEntityId " + e);
	  }
	  return getTransactionsListResponse;
  }
  
  private Integer getTotalNumberOfRecordsOnSearchForEntity(
		  GetTransactionsListRequest getTransactionsListRequest, Long entityId, String userType) {
	  log.info("Entering ::AccountTransactionsDaoImpl ::getTotalNumberOfRecordsOnSearchForEntity ");
	  try {
		  if (!getTransactionsListRequest.getTransaction_type().equals(AccountTransactionCode.MANUAL_CREDIT)
				  && !getTransactionsListRequest.getTransaction_type().equals(AccountTransactionCode.MANUAL_DEBIT)
				  && getTransactionsListRequest.getAcqChannel().equals("web")) {
			  JPAQuery query = new JPAQuery(entityManager);
			  List<Long> list = query.from(QPGAccountTransactions.pGAccountTransactions,QPGAccount.pGAccount)
					  .where(
							  QPGAccountTransactions.pGAccountTransactions.merchantCode.eq(QPGAccount.pGAccount.entityId),
							  QPGAccountTransactions.pGAccountTransactions.transactionCode
							  .in(getTransactionsListRequest.getTransactionCodeList())
							  .and(QPGAccountTransactions.pGAccountTransactions.transactionType.in(
									  AccountTransactionCode.MANUAL_CREDIT, AccountTransactionCode.MANUAL_DEBIT)),
							  isEntityId(userType, entityId),
							  isStatusLike(getTransactionsListRequest.getSettlementStatus()),
							  isMerchantCode(getTransactionsListRequest.getMerchant_code()))
					  .orderBy(orderByCreatedDateDescVirtualAcc())
					  .list(QPGAccountTransactions.pGAccountTransactions.id);
			  log.info("Exiting ::AccountTransactionsDaoImpl ::getTotalNumberOfRecordsOnSearch ");
			  return (StringUtils.isListNotNullNEmpty(list) ? list.size() : 0);
		  } else {
			  JPAQuery query = new JPAQuery(entityManager);
			  List<Long> list = query
					  .from(QPGAccountTransactions.pGAccountTransactions, QPGAccount.pGAccount,
							  QPGTransaction.pGTransaction)
					  .where(
							  QPGAccountTransactions.pGAccountTransactions.merchantCode
							  .eq(QPGAccount.pGAccount.entityId)
							  .and(QPGTransaction.pGTransaction.merchantSettlementStatus.in(
									  getSettlementStatusList(getTransactionsListRequest.getSettlementStatus())))
							  .and(QPGAccountTransactions.pGAccountTransactions.pgTransactionId
									  .eq(QPGTransaction.pGTransaction.id.stringValue()))
							  .and(QPGAccountTransactions.pGAccountTransactions.transactionCode
									  .in(getTransactionsListRequest.getTransactionCodeList()))
							  .and(QPGAccountTransactions.pGAccountTransactions.transactionType.in(
									  PGConstants.TXN_TYPE_VOID, PGConstants.TXN_TYPE_REFUND,
									  PGConstants.TXN_TYPE_SALE))
							  .and(QPGAccountTransactions.pGAccountTransactions.status.in(
									  getSettlementStatusList(getTransactionsListRequest.getSettlementStatus()))),
							  isEntityId(userType, entityId),
							  isStatusLike(getTransactionsListRequest.getSettlementStatus()),
							  isMerchantCode(getTransactionsListRequest.getMerchant_code()))
					  .orderBy(orderByCreatedDateDescVirtualAcc())
					  .list(QPGAccountTransactions.pGAccountTransactions.id);
			  log.info("Exiting ::AccountTransactionsDaoImpl ::getTotalNumberOfRecordsOnSearchForEntity ");
			  return (StringUtils.isListNotNullNEmpty(list) ? list.size() : 0);
		  }
	  } catch (Exception e) {
		  log.error("ERROR ::AccountTransactionsDaoImpl ::getTotalNumberOfRecordsOnSearchForEntity " + e);
	  }
	  return 0;
  }

  @Override
  public GetTransactionsListResponse searchManualAccountTransactionsForEntityId(
		  GetTransactionsListRequest getTransactionsListRequest, Long entityId, String userType) {
	  log.info("Entering ::AccountTransactionsDaoImpl :: searchManulAccountTransactionsForEntityId");
	  int offset = 0;
	  int limit = 0;
	  Timestamp fromDate = DateUtil.getStartDayTimestamp(getTransactionsListRequest.getFrom_date(),
			  PGConstants.DD_MM_YYYY);
	  Timestamp toDate = DateUtil.getEndDayTimestamp(getTransactionsListRequest.getTo_date(),
			  PGConstants.DD_MM_YYYY);
	  GetTransactionsListResponse getTransactionsListResponse = null;
	  AccountTransactionDTO accountTransactionDTO = null;
	  List<AccountTransactionDTO> accountTransactionDTOs = null;
	  Integer totalRecords = getTransactionsListRequest.getNoOfRecords();

	  if (getTransactionsListRequest.getPageIndex() == null
			  || getTransactionsListRequest.getPageIndex().intValue() == 1) {
		  totalRecords = getTotalNumberOfRecordsOnManualSearchForEntityId(getTransactionsListRequest, entityId, userType);
		  getTransactionsListRequest.setNoOfRecords(totalRecords);
	  }
	  getTransactionsListRequest.setNoOfRecords(totalRecords);
	  if (getTransactionsListRequest.getPageIndex() == null
			  || getTransactionsListRequest.getPageSize() == null) {
		  offset = 0;
		  limit = Constants.MAX_PAGE_SIZE;
	  } else {
		  offset = (getTransactionsListRequest.getPageIndex() - 1)
				  * getTransactionsListRequest.getPageSize();
		  limit = getTransactionsListRequest.getPageSize();
	  }

	  List<String> merchantCode = new ArrayList<>();
	  if (!StringUtil.isNullAndEmpty(getTransactionsListRequest.getMerchant_code())) {
		  String[] merchant = getTransactionsListRequest.getMerchant_code().split("\\|");
		  merchantCode = Arrays.asList(merchant);
	  }

	  JPAQuery query = new JPAQuery(entityManager);
	  List<Tuple> tupleList = query
			  .from(QPGAccountTransactions.pGAccountTransactions, QPGAccount.pGAccount)
			  .where(
					  QPGAccountTransactions.pGAccountTransactions.merchantCode
					  .eq(QPGAccount.pGAccount.entityId),
					  QPGAccountTransactions.pGAccountTransactions.transactionCode
					  .in(getTransactionsListRequest.getTransactionCodeList())
					  .and(QPGAccountTransactions.pGAccountTransactions.merchantCode.in(merchantCode))
					  .and(QPGAccountTransactions.pGAccountTransactions.transactionType.in(
							  AccountTransactionCode.MANUAL_CREDIT, AccountTransactionCode.MANUAL_DEBIT)),
					  isEntityId(userType, entityId),
					  isStatusLike(getTransactionsListRequest.getSettlementStatus()),
					  isValidDate(fromDate, toDate))
			  .offset(offset).limit(limit).orderBy(orderByCreatedDateDescVirtualAcc())
			  .list(QPGAccountTransactions.pGAccountTransactions.transactionTime,
					  QPGAccountTransactions.pGAccountTransactions.processedTime,
					  QPGAccountTransactions.pGAccountTransactions.accountTransactionId,
					  QPGAccountTransactions.pGAccountTransactions.transactionType,
					  QPGAccountTransactions.pGAccountTransactions.description,
					  QPGAccountTransactions.pGAccountTransactions.debit,
					  QPGAccountTransactions.pGAccountTransactions.merchantCode,
					  QPGAccountTransactions.pGAccountTransactions.transactionCode,
					  QPGAccountTransactions.pGAccountTransactions.credit,
					  QPGAccountTransactions.pGAccountTransactions.currentBalance,
					  QPGAccountTransactions.pGAccountTransactions.status,
					  QPGAccountTransactions.pGAccountTransactions.id,
					  QPGAccountTransactions.pGAccountTransactions.pgTransactionId,
					  QPGAccount.pGAccount.currency,
					  QPGAccountTransactions.pGAccountTransactions.deviceLocalTxnTime,
					  QPGAccountTransactions.pGAccountTransactions.timeZoneOffset);

	  if (!CollectionUtils.isEmpty(tupleList)) {
		  getTransactionsListResponse = validateListAndSetAccountTransactionDTO(totalRecords, tupleList);
	  }
	  return getTransactionsListResponse;
  }
  
  private Integer getTotalNumberOfRecordsOnManualSearchForEntityId(
		  GetTransactionsListRequest getTransactionsListRequest, Long entityId, String userType) {
	  log.info("Entering :: AccountTransactionsDaoImpl :: getTotalNumberOfRecordsOnManulSearchForEntityId ");

	  if (!getTransactionsListRequest.getTransaction_type().equals(AccountTransactionCode.MANUAL_CREDIT) 
			  && !getTransactionsListRequest.getTransaction_type().equals(AccountTransactionCode.MANUAL_DEBIT)
			  && getTransactionsListRequest.getAcqChannel().equalsIgnoreCase("web")) {
		  List<String> merchantCode = new ArrayList<>();
		  if (!StringUtil.isNullEmpty(getTransactionsListRequest.getMerchant_code())) {
			  String[] merchant = getTransactionsListRequest.getMerchant_code().split("\\|");
			  merchantCode = Arrays.asList(merchant);
		  }

		  JPAQuery query = new JPAQuery(entityManager);
		  List<Long> list =
				  query.from(QPGAccountTransactions.pGAccountTransactions, QPGAccount.pGAccount)
				  .where(QPGAccountTransactions.pGAccountTransactions.merchantCode
						  .eq(QPGAccount.pGAccount.entityId),
						  QPGAccountTransactions.pGAccountTransactions.transactionCode
						  .in(getTransactionsListRequest.getTransactionCodeList())
						  .and(QPGAccountTransactions.pGAccountTransactions.merchantCode.in(merchantCode))
						  .and(QPGAccountTransactions.pGAccountTransactions.transactionType.in(
								  AccountTransactionCode.MANUAL_CREDIT, AccountTransactionCode.MANUAL_DEBIT)),
						  isEntityId(userType, entityId),
						  isStatusLike(getTransactionsListRequest.getSettlementStatus()))
				  .orderBy(orderByCreatedDateDescVirtualAcc())
				  .list(QPGAccountTransactions.pGAccountTransactions.id);
		  log.info("Exiting :: AccountTransactionsDaoImpl :: getTotalNumberOfRecordsOnManulSearchForEntityId ");
		  return (StringUtils.isListNotNullNEmpty(list) ? list.size() : 0);
	  }
	  return 0;
  }
  
	private BooleanExpression isEntityId(String userType, Long entityId) {
		if (Constants.PM_USER_TYPE.equalsIgnoreCase(userType)) {
			return QPGTransaction.pGTransaction.pmId.eq(entityId);
		} else {
			return QPGTransaction.pGTransaction.isoId.eq(entityId);
		}
	}
}
