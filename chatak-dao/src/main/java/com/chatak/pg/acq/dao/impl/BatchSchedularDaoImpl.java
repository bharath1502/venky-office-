/**
 * 
 */
package com.chatak.pg.acq.dao.impl;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import com.chatak.pg.acq.dao.BatchSchedularDao;
import com.chatak.pg.acq.dao.model.PGMerchant;
import com.chatak.pg.acq.dao.model.PGMerchantEntityMap;
import com.chatak.pg.acq.dao.model.QPGAccount;
import com.chatak.pg.acq.dao.model.QPGBankCurrencyMapping;
import com.chatak.pg.acq.dao.model.QPGCurrencyConfig;
import com.chatak.pg.acq.dao.model.QPGFundingReport;
import com.chatak.pg.acq.dao.model.QPGMerchant;
import com.chatak.pg.acq.dao.model.QPGMerchantEntityMap;
import com.chatak.pg.acq.dao.model.QPGTransaction;
import com.chatak.pg.acq.dao.repository.MerchantRepository;
import com.chatak.pg.bean.Response;
import com.chatak.pg.bean.settlement.SettlementEntity;
import com.chatak.pg.constants.PGConstants;
import com.chatak.pg.dao.util.StringUtil;
import com.chatak.pg.enums.EntryModePortalDisplayEnum;
import com.chatak.pg.model.FeeReportResponse;
import com.chatak.pg.user.bean.DailyFundingReport;
import com.chatak.pg.user.bean.GetBatchReportRequest;
import com.chatak.pg.user.bean.GetDailyFundingReportRequest;
import com.chatak.pg.user.bean.GetTransactionsListRequest;
import com.chatak.pg.user.bean.Transaction;
import com.chatak.pg.util.CommonUtil;
import com.chatak.pg.util.Constants;
import com.chatak.pg.util.DateUtil;
import com.chatak.pg.util.StringUtils;
import com.mysema.query.Tuple;
import com.mysema.query.jpa.impl.JPAQuery;
import com.mysema.query.types.OrderSpecifier;
import com.mysema.query.types.expr.BooleanExpression;
import com.mysema.query.types.path.NumberPath;
import com.mysema.query.types.path.StringPath;

/**
 * @Author: Girmiti Software
 * @Date: Sep 21, 2017
 * @Time: 12:37:19 PM
 * @Version: 1.0
 * @Comments: 
 *
 */
@SuppressWarnings("unchecked")
@Repository("batchSchedularDao")
public class BatchSchedularDaoImpl extends TransactionDaoImpl implements BatchSchedularDao {

  private static Logger logger = Logger.getLogger(BatchSchedularDaoImpl.class);

  @PersistenceContext
  private EntityManager entityManager;
  
  @Autowired
  private MerchantRepository merchantRepository;

  @Override
  public List<DailyFundingReport> searchDailyFundingReportDetails(
      GetDailyFundingReportRequest reportRequest) {
    logger.info("Entering :: BatchSchedularDaoImpl :: searchDailyFundingReportDetails");
    Integer pageIndex = reportRequest.getPageIndex();
    Integer pageSize = reportRequest.getPageSize();
    Integer offset = 0;
    Integer limit = 0;
    Integer totalRecords;
    List<DailyFundingReport> transactions = new ArrayList<>();
	try {			
		if (pageIndex == null || pageIndex == 1) {
            totalRecords = getTotalNumberOfRecords(reportRequest);
            reportRequest.setNoOfRecords(totalRecords);
        }

        if (pageIndex == null && pageSize == null) {
            offset = 0;
            limit = Constants.DEFAULT_PAGE_SIZE;
        } else {
            offset = (pageIndex - 1) * pageSize;
            limit = pageSize;
        } 
        StringBuilder dailyFundingReportBuilder = new StringBuilder();
        dailyFundingReportBuilder.append(" select distinct pgfr.BATCH_ID, pgfr.MERCHANT_NAME, pgfr.MERCHANT_CODE, pgfr.SUB_MERCHANT_CODE, ");
        dailyFundingReportBuilder.append(" pgfr.CURRENCY, pgfr.SUB_MERCHANT_NAME, pgfr.BANK_ACCOUNT_NUMBER, pgfr.BANK_ROUTING_NUMBER,");
        dailyFundingReportBuilder.append(" pgfr.NET_FUNDING_AMOUNT, pgfr.FEE_BILLED_AMOUNT, pgfr.FUNDING_AMOUNT, pgfr.CREATED_DATE, pgm.MERCHANT_CODE as merchantcode, pgm.ID, pgmem.ENTITY_ID, pgmem.MERCHANT_ID ");  
        dailyFundingReportBuilder.append(" from PG_FUNDING_REPORT pgfr ");
        dailyFundingReportBuilder.append(" join PG_MERCHANT pgm on pgm.MERCHANT_CODE=pgfr.MERCHANT_CODE ");
        dailyFundingReportBuilder.append(" join PG_MERCHANT_ENTITY_MAPPING pgmem on pgmem.MERCHANT_ID=pgm.ID ");
        dailyFundingReportBuilder.append(" where (:entityId is null or pgmem.ENTITY_ID=:entityId) ");
        dailyFundingReportBuilder.append(" and pgfr.CREATED_DATE >= :startDate ");
        dailyFundingReportBuilder.append(" and pgfr.CREATED_DATE <= :endDate  ");
        dailyFundingReportBuilder.append(" ORDER BY pgfr.CREATED_DATE DESC LIMIT :offset, :limit");
		Query merchantFeeReportParam = entityManager.createNativeQuery(dailyFundingReportBuilder.toString());
		merchantFeeReportParam.setParameter("entityId", reportRequest.getId());
		merchantFeeReportParam.setParameter("startDate", DateUtil.getStartDayTimestamp(reportRequest.getFromDate(), PGConstants.DD_MM_YYYY));
		merchantFeeReportParam.setParameter("endDate", DateUtil.getEndDayTimestamp(reportRequest.getToDate(), PGConstants.DD_MM_YYYY));
		merchantFeeReportParam.setParameter("offset", offset);
		merchantFeeReportParam.setParameter("limit", limit);
		List<Object> objectList = merchantFeeReportParam.getResultList();
		Iterator<Object> itr = objectList.iterator();
		if(StringUtil.isListNotNullNEmpty(objectList)) {
			iterateDailyFundingReportDetails(transactions, itr);
		}
    } catch (Exception e) {
    	logger.error("Error :: BatchSchedularDaoImpl :: searchDailyFundingReportDetails ", e);
    }
    logger.info("Exiting :: BatchSchedularDaoImpl :: searchDailyFundingReportDetails");
    return transactions;
  }

  private void iterateDailyFundingReportDetails(List<DailyFundingReport> transactions, Iterator<Object> itr) {
		while(itr.hasNext()){
			Object[] objs = (Object[]) itr.next();
			 DailyFundingReport report = new DailyFundingReport();
			report.setBatchId(fetchObjectData(objs[0]));
			report.setParentBusinessName(fetchObjectData(objs[1]));
			report.setParentMerchantCode(fetchObjectData(objs[2]));
			report.setMerchantCode(fetchObjectData(objs[3]));
			report.setCurrency(fetchObjectData(objs[4]));
			report.setBusinessName(fetchObjectData(objs[5]));
			report.setBankAccountNumber(fetchObjectData(objs[6]));
			report.setBankRoutingNumber(fetchObjectData(objs[7]));
			report.setTotalAmount(StringUtil.isNull(objs[8]) ? null : ((BigDecimal) objs[8]).doubleValue()/100);
			report.setFeeAmount(StringUtil.isNull(objs[9]) ? null : ((BigDecimal) objs[9]).doubleValue()/100);
			report.setTxnAmount(StringUtil.isNull(objs[10]) ? null : ((BigDecimal) objs[10]).doubleValue()/100);
			report.setDate(StringUtil.isNull(objs[11]) ? null : (DateUtil.toDateStringFormat(((Timestamp) objs[11]), PGConstants.DATE_FORMAT)));
			transactions.add(report);
		}
	}

  private String fetchObjectData(Object obj) {
	  	return StringUtil.isNull(obj) ? null : ((String) obj);
  	}
  
  public int getTotalNumberOfRecords(GetDailyFundingReportRequest reportRequest) {
    StringBuilder dailyFundingReportBuilder = new StringBuilder();
    dailyFundingReportBuilder.append(" select distinct pgfr.BATCH_ID, pgfr.MERCHANT_NAME, pgfr.MERCHANT_CODE, pgfr.SUB_MERCHANT_CODE, ");
    dailyFundingReportBuilder.append(" pgfr.CURRENCY, pgfr.SUB_MERCHANT_NAME, pgfr.BANK_ACCOUNT_NUMBER, pgfr.BANK_ROUTING_NUMBER,");
    dailyFundingReportBuilder.append(" pgfr.NET_FUNDING_AMOUNT, pgfr.FEE_BILLED_AMOUNT, pgfr.FUNDING_AMOUNT, pgfr.CREATED_DATE, pgm.MERCHANT_CODE as merchantcode ,pgm.ID, pgmem.ENTITY_ID, pgmem.MERCHANT_ID ");  
    dailyFundingReportBuilder.append(" from PG_FUNDING_REPORT pgfr ");
    dailyFundingReportBuilder.append(" join PG_MERCHANT pgm on pgm.MERCHANT_CODE=pgfr.MERCHANT_CODE ");
    dailyFundingReportBuilder.append(" join PG_MERCHANT_ENTITY_MAPPING pgmem on pgmem.MERCHANT_ID=pgm.ID ");
    dailyFundingReportBuilder.append(" where (:entityId is null or pgmem.ENTITY_ID=:entityId) ");
    dailyFundingReportBuilder.append(" and pgfr.CREATED_DATE >= :startDate ");
    dailyFundingReportBuilder.append(" and pgfr.CREATED_DATE <= :endDate ");
	Query dailyFundingReportList = entityManager.createNativeQuery(dailyFundingReportBuilder.toString());
	dailyFundingReportList.setParameter("entityId", reportRequest.getId());
	dailyFundingReportList.setParameter("startDate", DateUtil.getStartDayTimestamp(reportRequest.getFromDate(), PGConstants.DD_MM_YYYY));
	dailyFundingReportList.setParameter("endDate", DateUtil.getEndDayTimestamp(reportRequest.getToDate(), PGConstants.DD_MM_YYYY));
	List<Object> list = dailyFundingReportList.getResultList();
    return (StringUtils.isListNotNullNEmpty(list) ? list.size() : 0);
  }

  protected BooleanExpression fetchBetween(Timestamp fromDate, Timestamp toDate) {
    return QPGFundingReport.pGFundingReport.createdDate.between(fromDate, toDate);
  }

  protected OrderSpecifier<Timestamp> orderByCreatedDateDsc() {
    return QPGFundingReport.pGFundingReport.createdDate.desc();
  }

  protected OrderSpecifier<Timestamp> orderByTxnDateDesc() {
    return QPGTransaction.pGTransaction.createdDate.desc();
  }
  @Override
  public void insertFundingReport() {
    logger.info("Entering :: BatchSchedularDaoImpl :: insertFundingReport ");
    try {
      StringBuilder sb = new StringBuilder("call usp_dailyfunds_report()");
      Query qry = entityManager.createNativeQuery(sb.toString());
      qry.getResultList();
    } catch (Exception e) {
      logger.error("Error :: BatchSchedularDaoImpl :: insertFundingReport ", e);
    }
    logger.info("Exiting :: BatchSchedularDaoImpl :: insertFundingReport ");
  }
  
  public Response showManualFundingReport() {    
    logger.info("Entering :: BatchSchedularDaoImpl :: showManualFundingReport ");
    
    List<String> str = new ArrayList<>();
    Response response = new Response();
    try {
      StringBuilder sb = new StringBuilder("call usp_dailyfunds_report()");
      Query qry = entityManager.createNativeQuery(sb.toString());
      str = qry.getResultList();
    } catch (Exception e) {
      logger.error("BatchSchedularDaoImpl :: showManualFundingReport ", e);
    }
    response.setErrorCode(str.get(0));
    logger.info("Exiting :: BatchSchedularDaoImpl :: showManualFundingReport ");
    return response;
  }

  /**
   * @param userid
   * @return
   */
  @Override
  public PGMerchant getMerchantCodeAndCompanyName(Long userid) {
    return merchantRepository.findById(userid);
  }

  /**
   * @param reportRequest
   * @return
   */
  @Override
  public List<DailyFundingReport> searchMerchantDailyFundingReportDetails(
      GetDailyFundingReportRequest reportRequest, String userType) {
    logger.info("Entering :: BatchSchedularDaoImpl :: searchMerchantDailyFundingReportDetails");
    List<DailyFundingReport> transactions = new ArrayList<>();
    try {     
      int offset = 0;
      int limit = 0;

      Timestamp startDate = null;
      Timestamp endDate = null;
      if (!CommonUtil.isNullAndEmpty(reportRequest.getFromDate())) {
        startDate =
            DateUtil.getStartDayTimestamp(reportRequest.getFromDate(), PGConstants.DD_MM_YYYY);
      }
      if (!CommonUtil.isNullAndEmpty(reportRequest.getToDate())) {
        endDate = DateUtil.getEndDayTimestamp(reportRequest.getToDate(), PGConstants.DD_MM_YYYY);
      }
      List<String> merchantList = merchantRepository.findMerchantsList(reportRequest.getId());
      Integer totalRecords = reportRequest.getNoOfRecords();
      if (reportRequest.getPageIndex() == null || reportRequest.getPageIndex().intValue() == 1) {
        totalRecords = getMerchantTotalNumberOfRecords(startDate, endDate,merchantList, userType);
        reportRequest.setNoOfRecords(totalRecords);
      }
      reportRequest.setNoOfRecords(totalRecords);
      if (reportRequest.getPageIndex() == null || reportRequest.getPageSize() == null) {
        offset = 0;
        limit = Constants.DEFAULT_PAGE_SIZE;
      } else {
        offset = (reportRequest.getPageIndex() - 1) * reportRequest.getPageSize();
        limit = reportRequest.getPageSize();
      }

      JPAQuery query = new JPAQuery(entityManager);
      
      StringPath type = new StringPath("");
      if (userType.equals("Merchant")) {
        type = QPGFundingReport.pGFundingReport.merchantCode;
      } else if (userType.equals("SubMerchant")) {
        type = QPGFundingReport.pGFundingReport.subMerchantCode;
      }
      
      List<Tuple> tupleList = query.from(QPGFundingReport.pGFundingReport, QPGMerchant.pGMerchant)
          .where(fetchBetween(startDate, endDate), isMerchatsListLike(merchantList),
              type.eq(QPGMerchant.pGMerchant.merchantCode))
          .offset(offset).limit(limit).orderBy(orderByCreatedDateDsc())
          .list(QPGFundingReport.pGFundingReport.id, QPGFundingReport.pGFundingReport.batchId,
              QPGFundingReport.pGFundingReport.merchantCode,
              QPGFundingReport.pGFundingReport.merchantName,
              QPGFundingReport.pGFundingReport.subMerchantCode,
              QPGFundingReport.pGFundingReport.subMerchantName,
              QPGFundingReport.pGFundingReport.currency,
              QPGFundingReport.pGFundingReport.bankAccountNumber,
              QPGFundingReport.pGFundingReport.bankRoutingNumber,
              QPGFundingReport.pGFundingReport.fundingAmount,
              QPGFundingReport.pGFundingReport.feeBilledAmount,
              QPGFundingReport.pGFundingReport.netFundingAmount,
              QPGFundingReport.pGFundingReport.createdDate);
      if (!CollectionUtils.isEmpty(tupleList)) {
        DailyFundingReport report = null;
        for (Tuple tuple : tupleList) {

          report = new DailyFundingReport();
          report.setBatchId(tuple.get(QPGFundingReport.pGFundingReport.batchId));
          report.setParentMerchantCode(tuple.get(QPGFundingReport.pGFundingReport.merchantCode));
          report.setParentBusinessName(tuple.get(QPGFundingReport.pGFundingReport.merchantName));
          report.setMerchantCode(tuple.get(QPGFundingReport.pGFundingReport.subMerchantCode));
          report.setBusinessName(tuple.get(QPGFundingReport.pGFundingReport.subMerchantName));
          report.setCurrency(tuple.get(QPGFundingReport.pGFundingReport.currency));
          report
              .setBankAccountNumber(tuple.get(QPGFundingReport.pGFundingReport.bankAccountNumber));
          report
              .setBankRoutingNumber(tuple.get(QPGFundingReport.pGFundingReport.bankRoutingNumber));
          report.setTotalAmount(tuple.get(QPGFundingReport.pGFundingReport.netFundingAmount).doubleValue()/Integer.parseInt("100"));
          report.setTxnAmount(tuple.get(QPGFundingReport.pGFundingReport.fundingAmount).doubleValue()/Integer.parseInt("100"));
          report.setFeeAmount(tuple.get(QPGFundingReport.pGFundingReport.feeBilledAmount).doubleValue()/Integer.parseInt("100"));
          report.setDate(DateUtil.toDateStringFormat(tuple.get(QPGFundingReport.pGFundingReport.createdDate), PGConstants.DATE_FORMAT));
          
          transactions.add(report);
        }
      }
    } catch (Exception e) {
      logger.error("Error :: BatchSchedularDaoImpl :: searchMerchantDailyFundingReportDetails ", e);
    }
    logger.info("Exiting :: BatchSchedularDaoImpl :: searchMerchantDailyFundingReportDetails");
    return transactions;
  }
  
  public int getMerchantTotalNumberOfRecords(Timestamp fromDate, Timestamp toDate,
      List<String> merchantList, String userType) {
    JPAQuery query = new JPAQuery(entityManager);
    StringPath type = new StringPath("");
    if (userType.equals("Merchant")) {
      type = QPGFundingReport.pGFundingReport.merchantCode;
    } else if (userType.equals("SubMerchant")) {
      type = QPGFundingReport.pGFundingReport.subMerchantCode;
    }
    List<Long> list =
        query.from(QPGFundingReport.pGFundingReport, QPGMerchant.pGMerchant)
        .where(fetchBetween(fromDate, toDate), isMerchatsListLike(merchantList),
            type.eq(QPGMerchant.pGMerchant.merchantCode))
        .list(QPGFundingReport.pGFundingReport.id);
    return (StringUtils.isListNotNullNEmpty(list) ? list.size() : 0);
  }

  /**
   * @param merchantList
   * @return
   */
  private BooleanExpression isMerchatsListLike(List<String> merchantList) {
    return merchantList!=null? QPGMerchant.pGMerchant.merchantCode.in(merchantList) : null;
  }

  /**
   * @param batchReportRequest
   * @return
   */
  @Override
  public List<Transaction> getMerchantBatchReportTransactions(
      GetBatchReportRequest batchReportRequest) {
    logger.info("Entering :: TransactionDaoImpl :: getMerchantBatchReportTransactions");
    List<Transaction> transactions = null;
    try {
      int offset = 0;
      int limit = 0;
      List<String> merchantList = merchantRepository.findMerchantsList(batchReportRequest.getId());
      Integer totalRecords = batchReportRequest.getNoOfRecords();
      if (batchReportRequest.getPageIndex() == null
          || batchReportRequest.getPageIndex().intValue() == 1) {
        totalRecords = getTotalNumberOfRecordsOnSearch(batchReportRequest,merchantList);
        batchReportRequest.setNoOfRecords(totalRecords);
      }
      batchReportRequest.setNoOfRecords(totalRecords);
      if (batchReportRequest.getPageIndex() == null || batchReportRequest.getPageSize() == null) {
        offset = 0;
        limit = Constants.DEFAULT_PAGE_SIZE;
      } else {
        offset = (batchReportRequest.getPageIndex() - 1) * batchReportRequest.getPageSize();
        limit = batchReportRequest.getPageSize();
      }
      Timestamp startDate = null;
      Timestamp endDate = null;
      if (!CommonUtil.isNullAndEmpty(batchReportRequest.getFromDate())) {
        startDate =
            DateUtil.getStartDayTimestamp(batchReportRequest.getFromDate(), PGConstants.DD_MM_YYYY);
      }
      if (!CommonUtil.isNullAndEmpty(batchReportRequest.getToDate())) {
        endDate =
            DateUtil.getEndDayTimestamp(batchReportRequest.getToDate(), PGConstants.DD_MM_YYYY);
      }
      JPAQuery query = new JPAQuery(entityManager);
      List<Tuple> tupleList = query.distinct()
          .from(QPGTransaction.pGTransaction, QPGMerchant.pGMerchant, QPGAccount.pGAccount,
              QPGBankCurrencyMapping.pGBankCurrencyMapping, QPGCurrencyConfig.pGCurrencyConfig)
          .where(
              isMerchantId(batchReportRequest.getMerchantCode(),
                  batchReportRequest.getSubMerchantCode()),
              isMerchatsListLike(merchantList),
              QPGTransaction.pGTransaction.merchantId.eq(QPGMerchant.pGMerchant.merchantCode),
              QPGTransaction.pGTransaction.merchantId.eq(QPGAccount.pGAccount.entityId),
              QPGTransaction.pGTransaction.merchantSettlementStatus
              .in(PGConstants.PG_SETTLEMENT_EXECUTED, PGConstants.PG_TXN_REFUNDED),
              QPGAccount.pGAccount.currency.eq(QPGCurrencyConfig.pGCurrencyConfig.currencyCodeAlpha),
              QPGAccount.pGAccount.status.eq(Constants.ACTIVE)
                  .and(QPGAccount.pGAccount.category.eq(PGConstants.PRIMARY_ACCOUNT)),
                  QPGMerchant.pGMerchant.merchantCode.eq(QPGTransaction.pGTransaction.merchantId),
              QPGBankCurrencyMapping.pGBankCurrencyMapping.currencyCodeAlpha
                  .eq(QPGCurrencyConfig.pGCurrencyConfig.currencyCodeAlpha),
              isValidDate(startDate, endDate))
          .offset(offset).limit(limit).orderBy(orderByTxnDateDesc())
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
              QPGMerchant.pGMerchant.localCurrency, QPGTransaction.pGTransaction.batchDate,
              QPGTransaction.pGTransaction.deviceLocalTxnTime,
              QPGTransaction.pGTransaction.timeZoneOffset);
      if (!CollectionUtils.isEmpty(tupleList)) {
        transactions = new ArrayList<>();
        Transaction transactionResp = null;
        for (Tuple tuple : tupleList) {
          transactionResp = new Transaction();
          String posEntryMode = getSettlementReportTransactionsExisting(transactionResp, tuple);
          logger.info("TransactionDaoImpl :: getTransactions :: posEntryMode for enum: "
              + getEntryModeEnumFromPosEntryMode(posEntryMode));
          transactionResp.setEntryMode(EntryModePortalDisplayEnum
              .valueOf(getEntryModeEnumFromPosEntryMode(posEntryMode)).value());
          logger.info("TransactionDaoImpl :: getTransactions :: set posEntryMode");
          getSettlementReportTransactionsStatus(transactionResp, tuple);
          transactions.add(transactionResp);
        }
      }
    } catch (Exception e) {
      logger.error("Error :: TransactionDaoImpl :: getMerchantBatchReportTransactions ", e);
    }
    logger.info("Exiting :: TransactionDaoImpl :: getMerchantBatchReportTransactions");
    return transactions;
  }

  private String getSettlementReportTransactionsExisting(Transaction transactionResp, Tuple tuple) {
    transactionResp.setTransactionId(tuple.get(QPGTransaction.pGTransaction.transactionId));
    transactionResp.setTransactionAmount(
        (StringUtils.amountToString(tuple.get(QPGTransaction.pGTransaction.txnAmount))));
    transactionResp.setTransactionDate(DateUtil.toDateStringFormat(
        tuple.get(QPGTransaction.pGTransaction.createdDate), PGConstants.DATE_FORMAT));
    transactionResp.setBatchDate(DateUtil.toDateStringFormat(
        tuple.get(QPGTransaction.pGTransaction.batchDate), PGConstants.DATE_FORMAT));
    transactionResp.setUpdated_date(DateUtil.toDateStringFormat(
        tuple.get(QPGTransaction.pGTransaction.updatedDate), PGConstants.DATE_FORMAT));
    transactionResp.setTransaction_type(tuple.get(QPGTransaction.pGTransaction.transactionType));
    transactionResp.setMerchant_code((tuple.get(QPGTransaction.pGTransaction.merchantId)));
    transactionResp.setMaskCardNumber(StringUtils.lastFourDigits(tuple.get(QPGTransaction.pGTransaction.panMasked)));
    transactionResp.setProcTxnCode(tuple.get(QPGTransaction.pGTransaction.procCode) != null
        ? tuple.get(QPGTransaction.pGTransaction.procCode) : "");
    transactionResp.setRef_transaction_id(getSettlementReportTransactionsIssuerTxnRefNum(tuple));
    transactionResp.setMerchantFeeAmount(tuple.get(QPGTransaction.pGTransaction.merchantFeeAmount));
    transactionResp.setTxnDescription(tuple.get(QPGTransaction.pGTransaction.txnDescription));
    transactionResp.setMerchantSettlementStatus(
        tuple.get(QPGTransaction.pGTransaction.merchantSettlementStatus));
    transactionResp
        .setTerminal_id(Long.valueOf(tuple.get(QPGTransaction.pGTransaction.terminalId)));
    transactionResp.setAccountNumber(tuple.get(QPGAccount.pGAccount.accountNum));
    transactionResp.setMerchantType(tuple.get(QPGAccount.pGAccount.entityType));
    transactionResp.setMerchantName(tuple.get(QPGMerchant.pGMerchant.firstName));
    transactionResp.setAcqChannel(tuple.get(QPGTransaction.pGTransaction.acqChannel));
    transactionResp.setTxn_ref_num(getSettlementReportTransactionsRefTransactionId(tuple));
    transactionResp.setDeviceLocalTxnTime(tuple.get(QPGTransaction.pGTransaction.deviceLocalTxnTime));
    transactionResp.setTimeZoneOffset(tuple.get(QPGTransaction.pGTransaction.timeZoneOffset));
    String posEntryMode = tuple.get(QPGTransaction.pGTransaction.posEntryMode);
    logger.info("TransactionDaoImpl :: getTransactions :: posEntryMode: " + posEntryMode);
    if (null == posEntryMode) {
      posEntryMode = "00";
    }
    return posEntryMode;
  }

  private long getSettlementReportTransactionsRefTransactionId(Tuple tuple) {
    return StringUtils.isEmpty(settlementTransactionsRefTransactionId(tuple)) ? 0L
        : Long.valueOf(settlementTransactionsRefTransactionId(tuple));
  }

  private String settlementTransactionsRefTransactionId(Tuple tuple) {
    return tuple.get(QPGTransaction.pGTransaction.refTransactionId) != null
        ? tuple.get(QPGTransaction.pGTransaction.refTransactionId) : "";
  }

  private long getSettlementReportTransactionsIssuerTxnRefNum(Tuple tuple) {
    return StringUtils.isEmpty(settlementTransactionsIssuerTxnRefNum(tuple)) ? 0L
        : Long.valueOf(settlementTransactionsIssuerTxnRefNum(tuple));
  }

  private String settlementTransactionsIssuerTxnRefNum(Tuple tuple) {
    return tuple.get(QPGTransaction.pGTransaction.issuerTxnRefNum) != null
        ? tuple.get(QPGTransaction.pGTransaction.issuerTxnRefNum) : "";
  }

  protected Integer getTotalNumberOfRecordsOnSearch(GetBatchReportRequest batchReportRequest,List<String> merchantList) {
    Timestamp startDate = null;
    Timestamp endDate = null;
    if (!CommonUtil.isNullAndEmpty(batchReportRequest.getFromDate())) {
      startDate =
          DateUtil.getStartDayTimestamp(batchReportRequest.getFromDate(), PGConstants.DD_MM_YYYY);
    }
    if (!CommonUtil.isNullAndEmpty(batchReportRequest.getToDate())) {
      endDate = DateUtil.getEndDayTimestamp(batchReportRequest.getToDate(), PGConstants.DD_MM_YYYY);
    }
    JPAQuery query = new JPAQuery(entityManager);
    List<Tuple> list = query.distinct()
        .from(QPGTransaction.pGTransaction, QPGMerchant.pGMerchant, QPGAccount.pGAccount,
            QPGBankCurrencyMapping.pGBankCurrencyMapping, QPGCurrencyConfig.pGCurrencyConfig)
        .where(
            isMerchantId(batchReportRequest.getMerchantCode(),
                batchReportRequest.getSubMerchantCode()),
            isMerchatsListLike(merchantList),
            QPGTransaction.pGTransaction.merchantId.eq(QPGMerchant.pGMerchant.merchantCode),
            QPGTransaction.pGTransaction.merchantId.eq(QPGAccount.pGAccount.entityId),
            QPGTransaction.pGTransaction.merchantSettlementStatus
            .in(PGConstants.PG_SETTLEMENT_EXECUTED, PGConstants.PG_TXN_REFUNDED),
            QPGAccount.pGAccount.currency.eq(QPGCurrencyConfig.pGCurrencyConfig.currencyCodeAlpha),
            QPGAccount.pGAccount.status.eq(Constants.ACTIVE)
                .and(QPGAccount.pGAccount.category.eq(PGConstants.PRIMARY_ACCOUNT)),
                QPGMerchant.pGMerchant.merchantCode.eq(QPGTransaction.pGTransaction.merchantId),
            QPGBankCurrencyMapping.pGBankCurrencyMapping.currencyCodeAlpha
                .eq(QPGCurrencyConfig.pGCurrencyConfig.currencyCodeAlpha),
            isValidDate(startDate, endDate))
        .orderBy(orderByTxnDateDesc()).list(QPGTransaction.pGTransaction.merchantId,
            QPGTransaction.pGTransaction.transactionId,
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
    } else if (!"".equals(merchantCode) && merchantCode != null) {
      return QPGTransaction.pGTransaction.merchantId.eq(merchantCode);
    } else if (!"".equals(subMerchantCode) && subMerchantCode != null) {
      return QPGTransaction.pGTransaction.merchantId.eq(subMerchantCode);
    } else {
      return null;
    }
  }

  private void getSettlementReportTransactionsStatus(Transaction transactionResp, Tuple tuple) {
    if (0 == tuple.get(QPGTransaction.pGTransaction.status)) {
      transactionResp.setStatusMessage("Approved");
    } else if (1 == tuple.get(QPGTransaction.pGTransaction.status)) {
      transactionResp.setStatusMessage("Declined");
    } else {
      transactionResp.setStatusMessage("Failed");
    }
    transactionResp.setFeeString(tuple.get(QPGCurrencyConfig.pGCurrencyConfig.currencyCodeAlpha)
        + " " + StringUtils.amountToString(tuple.get(QPGTransaction.pGTransaction.feeAmount)));
    transactionResp.setAuth_id(tuple.get(QPGTransaction.pGTransaction.authId));
    transactionResp
        .setTxn_total_amount((tuple.get(QPGTransaction.pGTransaction.txnTotalAmount) != null)
            ? tuple.get(QPGTransaction.pGTransaction.txnTotalAmount).doubleValue() : null);
    transactionResp.setAcqTxnMode(tuple.get(QPGTransaction.pGTransaction.acqTxnMode));
    transactionResp.setInvoice_number(tuple.get(QPGTransaction.pGTransaction.invoiceNumber));
    transactionResp.setProcessor(tuple.get(QPGTransaction.pGTransaction.processor));
    transactionResp.setTxnMode(tuple.get(QPGTransaction.pGTransaction.txnMode));
    transactionResp.setCardHolderName(tuple.get(QPGTransaction.pGTransaction.cardHolderName));
    transactionResp.setMerchantBusinessName(tuple.get(QPGMerchant.pGMerchant.businessName));
    transactionResp.setMerchantType(tuple.get(QPGMerchant.pGMerchant.merchantType));
    transactionResp.setLocalCurrency(tuple.get(QPGMerchant.pGMerchant.localCurrency));
    transactionResp.setBatchId(tuple.get(QPGTransaction.pGTransaction.batchId));
  }

  /**
   * @param transaction
   * @return
   */
  @Override
  public List<Transaction> getTxnHistoryOnId(GetTransactionsListRequest transaction) {
    logger.info("Entering :: BatchSchedularDaoImpl :: getTxnHistoryOnId");
    List<Transaction> transactions = null;
    try {
      int offset = 0;
      int limit = 0;
      Integer totalRecords = transaction.getNoOfRecords();
      if (transaction.getPageIndex() == null
          || transaction.getPageIndex().intValue() == 1) {
        totalRecords = getNumberOfRecords(transaction);
        transaction.setNoOfRecords(totalRecords);
      }
      transaction.setNoOfRecords(totalRecords);
      if (transaction.getPageIndex() == null
          || transaction.getPageSize() == null) {
        offset = 0;
        limit = Constants.DEFAULT_PAGE_SIZE;
      } else {
        offset = (transaction.getPageIndex() - 1)
            * transaction.getPageSize();
        limit = transaction.getPageSize();
      }
      JPAQuery query = new JPAQuery(entityManager);
      List<Tuple> tupleList = query
          .from(QPGTransaction.pGTransaction, QPGMerchant.pGMerchant, QPGAccount.pGAccount,
              QPGBankCurrencyMapping.pGBankCurrencyMapping, QPGCurrencyConfig.pGCurrencyConfig)
          .where(isMerchantIdLike(transaction.getMerchant_code()), 
              QPGTransaction.pGTransaction.merchantId.eq(QPGMerchant.pGMerchant.merchantCode),
              QPGTransaction.pGTransaction.merchantId.eq(QPGAccount.pGAccount.entityId),
              QPGAccount.pGAccount.status.eq(Constants.ACTIVE)
                  .and(QPGAccount.pGAccount.category.eq(PGConstants.PRIMARY_ACCOUNT)),
              QPGMerchant.pGMerchant.bankId.eq(QPGBankCurrencyMapping.pGBankCurrencyMapping.bankId),
              QPGBankCurrencyMapping.pGBankCurrencyMapping.currencyCodeAlpha
                  .eq(QPGCurrencyConfig.pGCurrencyConfig.currencyCodeAlpha),
                  isBatchID(transaction.getBatchID()))
          .offset(offset).limit(limit).orderBy(orderByCreatedDate())
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
          String posEntryMode = getTransactionstTupleList(transactionResp, tuple);
          logger.info("BatchSchedularDaoImpl :: getTxnHistoryOnId :: posEntryMode for enum: "
              + getEntryModeEnumFromPosEntryMode(posEntryMode));
          transactionResp.setEntryMode(EntryModePortalDisplayEnum
              .valueOf(getEntryModeEnumFromPosEntryMode(posEntryMode)).value());
          logger.info("BatchSchedularDaoImpl :: getTxnHistoryOnId :: set posEntryMode");
          getTransactionsStatus(transactionResp, tuple);
          
          transactions.add(transactionResp);
        }
      }
    } catch (Exception e) {
      logger.error("Error :: BatchSchedularDaoImpl :: getTxnHistoryOnId ", e);
    }
    logger.info("Exiting :: BatchSchedularDaoImpl :: getTxnHistoryOnId");
    return transactions;
  }

  private BooleanExpression isMerchantIdLike(String merchantCode) {
    return (merchantCode != null && !"".equals(merchantCode))
        ? QPGTransaction.pGTransaction.merchantId.eq(merchantCode) : null;
  }

  private BooleanExpression isBatchID(String batchID) {
    return (batchID != null && !"".equals(batchID))
        ? QPGTransaction.pGTransaction.batchId.eq(batchID) : null;
  }

  private OrderSpecifier<Timestamp> orderByCreatedDate() {
    return QPGTransaction.pGTransaction.createdDate.desc();
  }

  private Integer getNumberOfRecords(GetTransactionsListRequest transaction) {
    JPAQuery query = new JPAQuery(entityManager);
    List<Tuple> list = query
        .from(QPGTransaction.pGTransaction, QPGMerchant.pGMerchant, QPGAccount.pGAccount,
            QPGBankCurrencyMapping.pGBankCurrencyMapping, QPGCurrencyConfig.pGCurrencyConfig)
        .where(isMerchantIdLike(transaction.getMerchant_code()),
            QPGTransaction.pGTransaction.merchantId.eq(QPGMerchant.pGMerchant.merchantCode),
            QPGTransaction.pGTransaction.merchantId.eq(QPGAccount.pGAccount.entityId),
            QPGAccount.pGAccount.status.eq(Constants.ACTIVE)
                .and(QPGAccount.pGAccount.category.eq(PGConstants.PRIMARY_ACCOUNT)),
            QPGMerchant.pGMerchant.bankId.eq(QPGBankCurrencyMapping.pGBankCurrencyMapping.bankId),
            QPGBankCurrencyMapping.pGBankCurrencyMapping.currencyCodeAlpha
                .eq(QPGCurrencyConfig.pGCurrencyConfig.currencyCodeAlpha),
                isBatchID(transaction.getBatchID()))
        .orderBy(orderByCreatedDate()).list(QPGTransaction.pGTransaction.merchantId,
            QPGBankCurrencyMapping.pGBankCurrencyMapping.currencyCodeAlpha);

    return (StringUtils.isListNotNullNEmpty(list) ? list.size() : 0);
  } 
}
