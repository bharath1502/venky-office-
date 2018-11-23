/**
 * 
 */
package com.chatak.pg.acq.dao.impl;

import java.math.BigDecimal;
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
import org.springframework.stereotype.Repository;

import com.chatak.pg.acq.dao.FeeReportDao;
import com.chatak.pg.acq.dao.model.PGAccountTransactions;
import com.chatak.pg.acq.dao.model.QPGTransaction;
import com.chatak.pg.acq.dao.model.settlement.QPGSettlementEntityHistory;
import com.chatak.pg.acq.dao.model.settlement.QPGSettlementTransactionHistory;
import com.chatak.pg.acq.dao.repository.AccountTransactionsRepository;
import com.chatak.pg.bean.settlement.SettlementEntity;
import com.chatak.pg.constants.PGConstants;
import com.chatak.pg.dao.util.StringUtil;
import com.chatak.pg.model.FeeReportDto;
import com.chatak.pg.model.FeeReportRequest;
import com.chatak.pg.model.FeeReportResponse;
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
 * @Date: Jun 26, 2018
 * @Time: 12:42:48 PM
 * @Version: 1.0
 * @Comments: 
 *
 */
@Repository("feeReportDao")
public class FeeReportDaoImpl implements FeeReportDao {
	private static Logger logger = Logger.getLogger(FeeReportDaoImpl.class);
	
	private static final String TRANSACTION_CODES="transactionCodes";
	private static final String START_DATE="startDate";
	private static final String END_DATE="endDate";
	

	/**
	 * @param feeReportRequest
	 * @return
	 */
	
	@PersistenceContext
	private EntityManager entityManager;
	
	@Autowired
	private AccountTransactionsRepository accountTransactionsRepository;
	
	@Override
	public FeeReportResponse fetchFeeTransactions(FeeReportRequest feeReportRequest) {
		logger.info("Entering :: FeeReportDaoImpl :: fetchFeeTransactions");
		Integer pageIndex = feeReportRequest.getPageIndex();
        Integer pageSize = feeReportRequest.getPageSize();
        Integer offset = 0;
        Integer limit = 0;
        Integer totalRecords;
		FeeReportResponse feeReportResponse = new FeeReportResponse();
		List<FeeReportDto> feeReportList = new ArrayList<>();
		try {
			if (pageIndex == null || pageIndex == 1) {
	            totalRecords = getTotalNumberOfFeeReportRecords(feeReportRequest);
	            feeReportRequest.setNoOfRecords(totalRecords);
	        }

	        if (pageIndex == null && pageSize == null) {
	            offset = 0;
	            limit = Constants.DEFAULT_PAGE_SIZE;
	        } else {
	            offset = (pageIndex - 1) * pageSize;
	            limit = pageSize;
	        }
			StringBuilder feeReportBuilder = new StringBuilder();
			feeReportBuilder.append("select a.isoid,a.ISO_NAME,sum(a.TotalAmount) from (");
			feeReportBuilder.append(" select distinct iso.ID as isoid,iso.ISO_NAME,pgact.CREDIT as TotalAmount");
			feeReportBuilder.append(" from PG_PM_ISO_MAPPING pmisom  left join PG_PROGRAM_MANAGER pm on pm.id=pmisom.PM_ID");
			feeReportBuilder.append(" left join PG_ISO iso on iso.id=pmisom.ISO_ID left join PG_ACCOUNT_TRANSACTIONS pgact ");
			feeReportBuilder.append(" on pgact.ENTITY_ID= pm.id or pgact.ENTITY_ID= iso.id");
			feeReportBuilder.append(" where pm.id=:pmId and pgact.TRANSACTION_TIME >= :startDate ");
			feeReportBuilder.append(" and pgact.TRANSACTION_TIME <= :endDate and pgact.TRANSACTION_CODE in (:transactionCodes) )a");
			feeReportBuilder.append(" group by a.isoid,a.ISO_NAME LIMIT :offset, :limit");
			Query feeReportParam = entityManager.createNativeQuery(feeReportBuilder.toString());
			feeReportParam.setParameter("pmId", feeReportRequest.getProgramManagerId());
			feeReportParam.setParameter(TRANSACTION_CODES, feeReportRequest.getTransactionCodeList());
			feeReportParam.setParameter(START_DATE, DateUtil.getStartDayTimestamp(feeReportRequest.getFromDate(), PGConstants.DD_MM_YYYY));
			feeReportParam.setParameter(END_DATE, DateUtil.getEndDayTimestamp(feeReportRequest.getToDate(), PGConstants.DD_MM_YYYY));
			feeReportParam.setParameter("offset", offset);
			feeReportParam.setParameter("limit", limit);
			List<Object> objectList = feeReportParam.getResultList();
			if(StringUtil.isListNotNullNEmpty(objectList)) {
				iterateFeeReportDetails(feeReportList, objectList);
			}
			feeReportResponse.setFeeReportDto(feeReportList);
		}catch(Exception e) {
			logger.error("Error :: FeeReportDaoImpl :: fetchFeeTransactions : " + e.getMessage(), e);
		}
		logger.info("Exiting :: FeeReportDaoImpl :: fetchFeeTransactions");
		return feeReportResponse;
	}

	private void iterateFeeReportDetails(List<FeeReportDto> feeReportList, List<Object> objectList) {
		Iterator<Object> itr = objectList.iterator();
		while(itr.hasNext()){
			Object[] objs = (Object[]) itr.next();
			FeeReportDto feeReportDto = new FeeReportDto();
			feeReportDto.setIsoId(StringUtil.isNull(objs[0]) ? null : ((BigInteger) objs[0]).longValue());
			feeReportDto.setIsoName(StringUtil.isNull(objs[1]) ? null : ((String) objs[1]));
			feeReportDto.setIsoEarnedAmount(StringUtil.isNull(objs[Integer.parseInt("2")]) ? null : ((BigDecimal) objs[Integer.parseInt("2")]).longValue());
			feeReportList.add(feeReportDto);
		}
	}

	/**
	 * @param feeReportRequest
	 * @return
	 */
	@Override
	public FeeReportResponse fetchISOFeeTransactions(FeeReportRequest feeReportRequest) {
			List<SettlementEntity> settlementEntityList = new ArrayList<>();
			FeeReportResponse feeReportResponse = new FeeReportResponse();
			try {
				StringBuilder feeReportBuilder = new StringBuilder();
				feeReportBuilder.append("select distinct pat.MERCHANT_CODE,pat.DEVICE_LOCAL_TXN_TIME,pat.TRANSACTION_TIME,pat.CREDIT");
				feeReportBuilder.append(" from PG_ISO iso join PG_ISO_ACCOUNT isa on iso.ID=isa.ISO_ID");
				feeReportBuilder.append(" join PG_ACCOUNT_TRANSACTIONS pat on pat.ENTITY_ID=iso.id where iso.ID=:isoId");
				feeReportBuilder.append(" and (:startDate is null or pat.TRANSACTION_TIME >= :startDate) and (:endDate is null or pat.TRANSACTION_TIME <= :endDate) ");
				feeReportBuilder.append(" and pat.TRANSACTION_CODE in (:transactionCodes) ");
				Query isoFeeReportParam = entityManager.createNativeQuery(feeReportBuilder.toString());
				isoFeeReportParam.setParameter("isoId", feeReportRequest.getIsoId());
				isoFeeReportParam.setParameter(TRANSACTION_CODES, feeReportRequest.getTransactionCodeList());
				isoFeeReportParam.setParameter(START_DATE, StringUtil.isNullAndEmpty(feeReportRequest.getFromDate())?null:DateUtil.getStartDayTimestamp(feeReportRequest.getFromDate(), PGConstants.DD_MM_YYYY));
				isoFeeReportParam.setParameter(END_DATE, StringUtil.isNullAndEmpty(feeReportRequest.getToDate())?null:DateUtil.getEndDayTimestamp(feeReportRequest.getToDate(), PGConstants.DD_MM_YYYY));
				List<Object> objectList = isoFeeReportParam.getResultList();
				Iterator<Object> itr = objectList.iterator();
				if(StringUtil.isListNotNullNEmpty(objectList)) {
					iterateISOFeeReportDetails(settlementEntityList, itr);
				}
				feeReportResponse.setSettlementEntity(settlementEntityList);
			}catch(Exception e) {
				logger.error("Error :: FeeReportDaoImpl :: fetchISOFeeTransactions : " + e.getMessage(), e);
			}
		return feeReportResponse;
	}

	private void iterateISOFeeReportDetails(List<SettlementEntity> settlementEntityList, Iterator<Object> itr) {
		while(itr.hasNext()){
			Object[] objs = (Object[]) itr.next();
			SettlementEntity settlementEntity = new SettlementEntity();
			settlementEntity.setMerchantId(StringUtil.isNull(objs[0]) ? null : ((String) objs[0]));
			settlementEntity.setDeviceLocalTxnTime(StringUtil.isNull(objs[1]) ? null : DateUtil.toDateStringFormat( ((Timestamp) objs[1]),Constants.DATE_TIME_FORMAT));
			settlementEntity.setTxnDate(StringUtil.isNull(objs[Integer.parseInt("2")]) ? null : ((Timestamp) objs[Integer.parseInt("2")]));
			settlementEntity.setIsoAmount(StringUtil.isNull(objs[Integer.parseInt("3")]) ? null : ((BigInteger) objs[Integer.parseInt("3")]).longValue());
			settlementEntityList.add(settlementEntity);
		}
	}
	
	private int getTotalNumberOfFeeReportRecords(FeeReportRequest feeReportRequest) {
		logger.info("Entering :: FeeReportDaoImpl :: getTotalNumberOfFeeReportRecords");
		    StringBuilder feeReportBuilder = new StringBuilder();
			feeReportBuilder.append("select a.isoid,a.ISO_NAME,sum(a.TotalAmount) from (");
			feeReportBuilder.append(" select distinct iso.ID as isoid,iso.ISO_NAME,pgact.CREDIT as TotalAmount");
			feeReportBuilder.append(" from PG_PM_ISO_MAPPING pmisom  left join PG_PROGRAM_MANAGER pm on pm.id=pmisom.PM_ID");
			feeReportBuilder.append(" left join PG_ISO iso on iso.id=pmisom.ISO_ID left join PG_ACCOUNT_TRANSACTIONS pgact ");
			feeReportBuilder.append(" on pgact.ENTITY_ID= pm.id or pgact.ENTITY_ID= iso.id");
			feeReportBuilder.append(" where pm.id=:pmId and pgact.TRANSACTION_TIME >= :startDate ");
			feeReportBuilder.append(" and pgact.TRANSACTION_TIME <= :endDate and pgact.TRANSACTION_CODE in (:transactionCodes) )a");
			feeReportBuilder.append(" group by a.isoid,a.ISO_NAME");
			Query qry = entityManager.createNativeQuery(feeReportBuilder.toString());
			qry.setParameter("pmId", feeReportRequest.getProgramManagerId());
			qry.setParameter(TRANSACTION_CODES, feeReportRequest.getTransactionCodeList());
			qry.setParameter(START_DATE, DateUtil.getStartDayTimestamp(feeReportRequest.getFromDate(), PGConstants.DD_MM_YYYY));
			qry.setParameter(END_DATE, DateUtil.getEndDayTimestamp(feeReportRequest.getToDate(), PGConstants.DD_MM_YYYY));
			List<Object> objectList = qry.getResultList();
			logger.info("Exiting :: FeeReportDaoImpl :: getTotalNumberOfFeeReportRecords");
        return (objectList != null && !objectList.isEmpty() ? objectList
                .size() : 0);
    }
	
	@Override
	public FeeReportResponse fetchISORevenueTransactions(FeeReportRequest feeReportRequest) {
		logger.info("Entering :: FeeReportDaoImpl :: fetchISORevenueTransactions");
		Timestamp startDate = null;
		Timestamp endDate = null;
		Integer pageIndex = feeReportRequest.getPageIndex();
        
		Integer pageSize = feeReportRequest.getPageSize();
        Integer offset = 0;
        Integer limit = 0;
        Integer totalRecords = feeReportRequest.getNoOfRecords();
		FeeReportResponse feeReportResponse = new FeeReportResponse();
		List<SettlementEntity> feeReportList = new ArrayList<>();
		try {
			if (pageIndex == null || pageIndex == 1) {
	            totalRecords = getTotalNumberOfIsoRevenueReportRecords(feeReportRequest);
	            feeReportRequest.setNoOfRecords(totalRecords);
	        }
			feeReportResponse.setTotalNoOfRows(totalRecords);
	        if (pageIndex == null && pageSize == null) {
	        	offset = 0;
	        	limit = Constants.DEFAULT_PAGE_SIZE;
	        } else {
	            offset = (pageIndex - 1) * pageSize;
	            limit = pageSize;
	        }
			if (!CommonUtil.isNullAndEmpty(feeReportRequest.getFromDate())) {
				startDate = DateUtil.getStartDayTimestamp(feeReportRequest.getFromDate(), PGConstants.DD_MM_YYYY);
			}
			if (!CommonUtil.isNullAndEmpty(feeReportRequest.getToDate())) {
				endDate = DateUtil.getEndDayTimestamp(feeReportRequest.getToDate(), PGConstants.DD_MM_YYYY);
			}
			JPAQuery query = new JPAQuery(entityManager);
			List<Tuple> infoList = query.from(QPGSettlementEntityHistory.pGSettlementEntityHistory, QPGSettlementTransactionHistory.pGSettlementTransactionHistory)
					.where(QPGSettlementEntityHistory.pGSettlementEntityHistory.acqPmId
							.eq(Long.valueOf(feeReportRequest.getProgramManagerId()))
							.and(isValidDate(startDate, endDate))
							.and(QPGSettlementEntityHistory.pGSettlementEntityHistory.id.eq(QPGSettlementTransactionHistory.pGSettlementTransactionHistory.issuanceSettlementEntityId)),
							 isIsoIdEq(feeReportRequest.getIsoId()))
							.offset(offset).limit(limit).orderBy(orderByCreatedDateDesc())
					.list(QPGSettlementEntityHistory.pGSettlementEntityHistory.merchantId,
							QPGSettlementEntityHistory.pGSettlementEntityHistory.acqSaleAmount,
							QPGSettlementEntityHistory.pGSettlementEntityHistory.issSaleAmount,
							QPGSettlementEntityHistory.pGSettlementEntityHistory.batchid,
							QPGSettlementTransactionHistory.pGSettlementTransactionHistory.isoAmount,QPGSettlementTransactionHistory.pGSettlementTransactionHistory.issuanceSettlementEntityId);
			if (StringUtil.isListNotNullNEmpty(infoList)) {
				for (Tuple tuple : infoList) {
					SettlementEntity settlementEntity = new SettlementEntity();
					settlementEntity
							.setIssuanceSettlementEntityId(tuple.get(QPGSettlementTransactionHistory.pGSettlementTransactionHistory.issuanceSettlementEntityId));
					settlementEntity.setMerchantId(tuple.get(QPGSettlementEntityHistory.pGSettlementEntityHistory.merchantId));
					settlementEntity.setAcquirerAmount(
							tuple.get(QPGSettlementEntityHistory.pGSettlementEntityHistory.acqSaleAmount).toString());
					settlementEntity.setIsoAmount(tuple.get(QPGSettlementTransactionHistory.pGSettlementTransactionHistory.isoAmount));
					settlementEntity.setBatchId(tuple.get(QPGSettlementEntityHistory.pGSettlementEntityHistory.batchid));
					settlementEntity
							.setIssAmount(tuple.get(QPGSettlementEntityHistory.pGSettlementEntityHistory.issSaleAmount).toString());
					feeReportList.add(settlementEntity);
				}
			}
			feeReportResponse.setSettlementEntity(feeReportList);
		} catch (Exception e) {
			logger.error("Error :: FeeReportDaoImpl :: fetchISORevenueTransactions : " + e.getMessage(), e);
		}
		logger.info("Exiting :: FeeReportDaoImpl :: fetchISORevenueTransactions");
		return feeReportResponse;
		
	}
	
	protected BooleanExpression isValidDate(Timestamp fromDate, Timestamp toDate) {
		if ((fromDate != null && toDate == null)) {
			return QPGSettlementEntityHistory.pGSettlementEntityHistory.batchFileProcessedDate.gt(fromDate);
		} else if ((fromDate == null && toDate != null)) {
			return QPGSettlementEntityHistory.pGSettlementEntityHistory.batchFileProcessedDate.lt(toDate);
		} else if ((fromDate == null))
			return null;
		return QPGSettlementEntityHistory.pGSettlementEntityHistory.batchFileProcessedDate.between(fromDate, toDate);
	}
	
	protected OrderSpecifier<Timestamp> orderByCreatedDateDesc() {
		return QPGSettlementEntityHistory.pGSettlementEntityHistory.batchFileProcessedDate.desc();
	}
	
	private int getTotalNumberOfIsoRevenueReportRecords(FeeReportRequest feeReportRequest) {
		logger.info("Entering :: FeeReportDaoImpl :: getTotalNumberOfIsoRevenueReportRecords");
		Timestamp startDate = null;
		Timestamp endDate = null;
		if (!CommonUtil.isNullAndEmpty(feeReportRequest.getFromDate())) {
			startDate = DateUtil.getStartDayTimestamp(feeReportRequest.getFromDate(), PGConstants.DD_MM_YYYY);
		}
		if (!CommonUtil.isNullAndEmpty(feeReportRequest.getToDate())) {
			endDate = DateUtil.getEndDayTimestamp(feeReportRequest.getToDate(), PGConstants.DD_MM_YYYY);
		}
		JPAQuery query = new JPAQuery(entityManager);
		List<Tuple> list = query.from(QPGSettlementEntityHistory.pGSettlementEntityHistory, QPGSettlementTransactionHistory.pGSettlementTransactionHistory)
				.where(QPGSettlementEntityHistory.pGSettlementEntityHistory.acqPmId
						.eq(Long.valueOf(feeReportRequest.getProgramManagerId()))
						.and(isValidDate(startDate, endDate))
						.and(QPGSettlementEntityHistory.pGSettlementEntityHistory.id.eq(QPGSettlementTransactionHistory.pGSettlementTransactionHistory.issuanceSettlementEntityId)),
						 isIsoIdEq(feeReportRequest.getIsoId()))
				.list(QPGSettlementEntityHistory.pGSettlementEntityHistory.merchantId,
						QPGSettlementEntityHistory.pGSettlementEntityHistory.acqSaleAmount,
						QPGSettlementEntityHistory.pGSettlementEntityHistory.issSaleAmount,
						QPGSettlementEntityHistory.pGSettlementEntityHistory.batchid,
						QPGSettlementTransactionHistory.pGSettlementTransactionHistory.isoAmount,QPGSettlementTransactionHistory.pGSettlementTransactionHistory.issuanceSettlementEntityId);
		logger.info("Exiting :: FeeReportDaoImpl :: getTotalNumberOfIsoRevenueReportRecords");
		return (StringUtils.isListNotNullNEmpty(list) ? list.size() : 0);
	}
	
	private BooleanExpression isIsoIdEq(Long isoid) {

		return (isoid != null) ? QPGSettlementTransactionHistory.pGSettlementTransactionHistory.isoId.eq(isoid)
				: null;
	}
	
	private BooleanExpression isIssuanceSettlementEntityIdIdEq(Long issuanceSettlementEntityId) {

		return QPGSettlementTransactionHistory.pGSettlementTransactionHistory.issuanceSettlementEntityId.eq(issuanceSettlementEntityId);
	}
	
	@Override
	public List<SettlementEntity> getAllMatchedTxnsByEntityId(Long issuanceSettlementEntityId) {
		logger.info("Entering :: FeeReportDaoImpl :: getAllMatchedTxnsByEntityId");
		List<SettlementEntity> feeReportList = new ArrayList<>();
		JPAQuery query = new JPAQuery(entityManager);
		logger.info(String.valueOf(issuanceSettlementEntityId));
		List<Tuple> infoList = query
				.from(QPGSettlementTransactionHistory.pGSettlementTransactionHistory, QPGTransaction.pGTransaction, QPGSettlementEntityHistory.pGSettlementEntityHistory)
				.where(isIssuanceSettlementEntityIdIdEq(issuanceSettlementEntityId)
						.and(QPGTransaction.pGTransaction.id.stringValue()
								.eq(QPGSettlementTransactionHistory.pGSettlementTransactionHistory.pgTransactionId))
						.and(QPGSettlementEntityHistory.pGSettlementEntityHistory.id.eq(QPGSettlementTransactionHistory.pGSettlementTransactionHistory.issuanceSettlementEntityId)))
				.list(QPGSettlementTransactionHistory.pGSettlementTransactionHistory.terminalId,
						QPGSettlementTransactionHistory.pGSettlementTransactionHistory.pgTransactionId,
						QPGSettlementTransactionHistory.pGSettlementTransactionHistory.issuerTxnID,
						QPGSettlementTransactionHistory.pGSettlementTransactionHistory.isoAmount,
						QPGTransaction.pGTransaction.merchantId, QPGTransaction.pGTransaction.transactionType,
						QPGTransaction.pGTransaction.batchId, QPGTransaction.pGTransaction.txnTotalAmount,
						QPGTransaction.pGTransaction.deviceLocalTxnTime, QPGTransaction.pGTransaction.issuancePartner,
						QPGTransaction.pGTransaction.timeZoneRegion, QPGTransaction.pGTransaction.batchDate,
						QPGTransaction.pGTransaction.userName, QPGTransaction.pGTransaction.cardHolderName,
						QPGTransaction.pGTransaction.txnDescription,
						QPGTransaction.pGTransaction.merchantSettlementStatus,
						QPGTransaction.pGTransaction.txnCurrencyCode, QPGTransaction.pGTransaction.panMasked,
						QPGTransaction.pGTransaction.settlementBatchStatus, QPGTransaction.pGTransaction.acqTxnMode,
						QPGTransaction.pGTransaction.acqChannel, QPGTransaction.pGTransaction.transactionType,
						QPGTransaction.pGTransaction.invoiceNumber, QPGSettlementEntityHistory.pGSettlementEntityHistory.acqPmId, QPGSettlementTransactionHistory.pGSettlementTransactionHistory.isoId);
		if (StringUtil.isListNotNullNEmpty(infoList)) {
			for (Tuple tuple : infoList) {
				SettlementEntity settlementEntity = new SettlementEntity();
				settlementEntity.setTerminalId(tuple.get(QPGSettlementTransactionHistory.pGSettlementTransactionHistory.terminalId));
				settlementEntity.setMerchantId(tuple.get(QPGTransaction.pGTransaction.merchantId));
				settlementEntity.setPgTxnId(tuple.get(QPGSettlementTransactionHistory.pGSettlementTransactionHistory.pgTransactionId));
				settlementEntity.setIssTxnId(tuple.get(QPGSettlementTransactionHistory.pGSettlementTransactionHistory.issuerTxnID));
				settlementEntity.setBatchId(tuple.get(QPGTransaction.pGTransaction.batchId));
				settlementEntity.setDeviceLocalTxnTime(tuple.get(QPGTransaction.pGTransaction.deviceLocalTxnTime));
				settlementEntity.setTransactionType(tuple.get(QPGTransaction.pGTransaction.transactionType));
				settlementEntity.setTxnTotalAmount(BigDecimal.valueOf(tuple.get(QPGTransaction.pGTransaction.txnTotalAmount)));
				settlementEntity.setBatchDate(tuple.get(QPGTransaction.pGTransaction.batchDate));
				settlementEntity.setUserName(tuple.get(QPGTransaction.pGTransaction.userName));
				settlementEntity.setIssPartner(tuple.get(QPGTransaction.pGTransaction.issuancePartner));
				settlementEntity.setTimeZoneRegion(tuple.get(QPGTransaction.pGTransaction.timeZoneRegion));
				settlementEntity.setCardHolderName(tuple.get(QPGTransaction.pGTransaction.cardHolderName));
				settlementEntity.setTxnDesc(tuple.get(QPGTransaction.pGTransaction.txnDescription));
				settlementEntity.setMerchantSettlementStatus(tuple.get(QPGTransaction.pGTransaction.merchantSettlementStatus));
				settlementEntity.setTxnCurrencyCode(tuple.get(QPGTransaction.pGTransaction.txnCurrencyCode));
				settlementEntity.setPanMasked(tuple.get(QPGTransaction.pGTransaction.panMasked));
				settlementEntity.setSettlementBatchStatus(tuple.get(QPGTransaction.pGTransaction.settlementBatchStatus));
				settlementEntity.setAcqTxnMode(tuple.get(QPGTransaction.pGTransaction.acqTxnMode));
				settlementEntity.setAcqChannel(tuple.get(QPGTransaction.pGTransaction.acqChannel));
				settlementEntity.setTxnType(tuple.get(QPGTransaction.pGTransaction.transactionType));
				settlementEntity.setInVoiceNumber(tuple.get(QPGTransaction.pGTransaction.invoiceNumber));
				settlementEntity.setAcqPmId(tuple.get(QPGSettlementEntityHistory.pGSettlementEntityHistory.acqPmId).toString());
				settlementEntity.setIsoId(tuple.get(QPGSettlementTransactionHistory.pGSettlementTransactionHistory.isoId));
				
				List<PGAccountTransactions> pgAccountTransactionsList = accountTransactionsRepository.findByPgTransactionId(tuple.get(QPGSettlementTransactionHistory.pGSettlementTransactionHistory.pgTransactionId));
				for (PGAccountTransactions pgAccountTransaction : pgAccountTransactionsList) {
					if(pgAccountTransaction.getEntityType().equals(Constants.ISO_USER_TYPE)){
						Long isoAmount = pgAccountTransaction.getCredit();
						settlementEntity.setIsoAmount(isoAmount);
					} 
					setPmAmount(settlementEntity, pgAccountTransaction);
					if(pgAccountTransaction.getEntityType().equals(PGConstants.MERCHANT)){
						Long merchantAmount = pgAccountTransaction.getCredit();
						settlementEntity
						.setMerchantAmount(merchantAmount);
					}
				}
				feeReportList.add(settlementEntity);
			}
		}
		logger.info("Exiting :: FeeReportDaoImpl :: getAllMatchedTxnsByEntityId");
		return feeReportList;
	}

	/**
	 * @param settlementEntity
	 * @param pgAccountTransaction
	 */
	private void setPmAmount(SettlementEntity settlementEntity, PGAccountTransactions pgAccountTransaction) {
		if (pgAccountTransaction.getEntityType().equals(Constants.PM_USER_TYPE)) {
			Long pmAmount = pgAccountTransaction.getCredit();
			settlementEntity.setPmAmount(pmAmount);
		}
	}
	
	@Override
	public FeeReportResponse fetchMerchantRevenueTransactions(FeeReportRequest feeReportRequest) {
		logger.info("Entering :: FeeReportDaoImpl :: fetchMerchantRevenueTransactions");
		Integer pageIndex = feeReportRequest.getPageIndex();
        Integer pageSize = feeReportRequest.getPageSize();
        Integer offset = 0;
        Integer limit = 0;
        Integer totalRecords;
		List<SettlementEntity> feeReportList = new ArrayList<>();
		FeeReportResponse feeReportResponse = new FeeReportResponse();
		try {			
			if (pageIndex == null || pageIndex == 1) {
	            totalRecords = getTotalNoOfMerchantRevenueRecords(feeReportRequest);
	            feeReportRequest.setNoOfRecords(totalRecords);
	        }

	        if (pageIndex == null && pageSize == null) {
	            offset = 0;
	            limit = Constants.DEFAULT_PAGE_SIZE;
	        } else {
	            offset = (pageIndex - 1) * pageSize;
	            limit = pageSize;
	        } 
	        StringBuilder feeReportBuilder = new StringBuilder();
	        feeReportBuilder.append(" select b.MID,b.ACQ_SALE_AMOUNT,b.ISS_SALE_AMOUNT,b.MERCHANT_AMOUNT,min(b.ISS_SETTLEMENT_ENTITY_ID) as ISS_SETTLEMENT_ENTITY_ID, b.BATCH_ID ");
			feeReportBuilder.append("from(select a.MID as MID, a.ACQ_SALE_AMOUNT as ACQ_SALE_AMOUNT,a.ISS_SALE_AMOUNT, a.MERCHANT_AMOUNT, a.ISS_SETTLEMENT_ENTITY_ID, a.BATCH_ID");
			feeReportBuilder.append(" from (select pgise.MID, pgise.ACQ_SALE_AMOUNT,pgise.ISS_SALE_AMOUNT, pgise.MERCHANT_AMOUNT, pgise.BATCH_ID,pgist.ISO_ID, pgist.ISS_SETTLEMENT_ENTITY_ID, pgise.ACQ_PM_ID, pgise.BATCH_FILE_PROCESSED_DATE ");  
			feeReportBuilder.append(" from PG_ISS_SETTLEMENT_ENTITY pgise  ");
			feeReportBuilder.append(" join PG_ISS_SETTLEMENT_TXN pgist on pgise.id=pgist.ISS_SETTLEMENT_ENTITY_ID  ");
			feeReportBuilder.append(" where (:isoId is null or pgist.ISO_ID=:isoId) ");
			feeReportBuilder.append(" and (:acqPmId is null or pgise.ACQ_PM_ID=:acqPmId) ");
			feeReportBuilder.append(" and pgise.BATCH_FILE_PROCESSED_DATE >= :startDate ");
			feeReportBuilder.append(" and pgise.BATCH_FILE_PROCESSED_DATE <= :endDate )a ");
			feeReportBuilder.append(" group by a.MID, a.ACQ_SALE_AMOUNT, a.ISS_SALE_AMOUNT, a.MERCHANT_AMOUNT, a.ISS_SETTLEMENT_ENTITY_ID, a.BATCH_ID )b ");
			feeReportBuilder.append(" group by b.MID, b.ACQ_SALE_AMOUNT, b.ISS_SALE_AMOUNT, b.MERCHANT_AMOUNT, b.BATCH_ID ");
			feeReportBuilder.append(" LIMIT :offset, :limit");
			Query merchantFeeReportParam = entityManager.createNativeQuery(feeReportBuilder.toString());
			merchantFeeReportParam.setParameter("isoId", feeReportRequest.getIsoId());
			merchantFeeReportParam.setParameter("acqPmId", feeReportRequest.getProgramManagerId());
			merchantFeeReportParam.setParameter(START_DATE, DateUtil.getStartDayTimestamp(feeReportRequest.getFromDate(), PGConstants.DD_MM_YYYY));
			merchantFeeReportParam.setParameter(END_DATE, DateUtil.getEndDayTimestamp(feeReportRequest.getToDate(), PGConstants.DD_MM_YYYY));
			merchantFeeReportParam.setParameter("offset", offset);
			merchantFeeReportParam.setParameter("limit", limit);
			List<Object> objectList = merchantFeeReportParam.getResultList();
			Iterator<Object> itr = objectList.iterator();
			if(StringUtil.isListNotNullNEmpty(objectList)) {
				iterateMerchantRevenueFeeReportDetails(feeReportList, itr);
			}
			feeReportResponse.setSettlementEntity(feeReportList);
		}catch(Exception e) {
			logger.error("Error :: FeeReportDaoImpl :: fetchMerchantRevenueTransactions : " + e.getMessage(), e);
		}
		return feeReportResponse;		
	}
	
	private void iterateMerchantRevenueFeeReportDetails(List<SettlementEntity> feeReportList, Iterator<Object> itr) {
		while(itr.hasNext()){
			Object[] objs = (Object[]) itr.next();
			SettlementEntity settlementEntity = new SettlementEntity();
			settlementEntity.setMerchantId(StringUtil.isNull(objs[0]) ? null : ((String) objs[0]));
			settlementEntity.setAcquirerAmount(StringUtil.isNull(objs[Integer.parseInt("1")]) ? null : ((BigInteger) objs[Integer.parseInt("1")]).toString());
			settlementEntity.setIssAmount(StringUtil.isNull(objs[Integer.parseInt("2")]) ? null : ((BigInteger) objs[Integer.parseInt("2")]).toString());
			settlementEntity.setMerchantAmount((StringUtil.isNull(objs[Integer.parseInt("3")]) ? null : ((BigInteger) objs[Integer.parseInt("3")]).longValue()));
			settlementEntity.setBatchId((StringUtil.isNull(objs[Integer.parseInt("5")]) ? null : ((String) objs[Integer.parseInt("5")])));
			settlementEntity.setIssuanceSettlementEntityId((StringUtil.isNull(objs[Integer.parseInt("4")]) ? null : (Long.valueOf(((BigInteger) objs[Integer.parseInt("4")]).toString()))));
			feeReportList.add(settlementEntity);
		}
	}
	
	public int getTotalNoOfMerchantRevenueRecords(FeeReportRequest feeReportRequest) {
		logger.info("Entering :: FeeReportDaoImpl :: getTotalNoOfMerchantRevenueRecords");
		StringBuilder feeReportBuilder = new StringBuilder();
		feeReportBuilder.append(" select b.MID,b.ACQ_SALE_AMOUNT,b.ISS_SALE_AMOUNT,b.MERCHANT_AMOUNT,min(b.ISS_SETTLEMENT_ENTITY_ID) as ISS_SETTLEMENT_ENTITY_ID, b.BATCH_ID ");
		feeReportBuilder.append("from(select a.MID as MID, a.ACQ_SALE_AMOUNT as ACQ_SALE_AMOUNT,a.ISS_SALE_AMOUNT, a.MERCHANT_AMOUNT, a.ISS_SETTLEMENT_ENTITY_ID, a.BATCH_ID ");
		feeReportBuilder.append(" from (select pgise.MID, pgise.ACQ_SALE_AMOUNT,pgise.ISS_SALE_AMOUNT, pgise.MERCHANT_AMOUNT, pgise.BATCH_ID,pgist.ISO_ID, pgist.ISS_SETTLEMENT_ENTITY_ID, pgise.ACQ_PM_ID, pgise.BATCH_FILE_PROCESSED_DATE ");  
		feeReportBuilder.append(" from PG_ISS_SETTLEMENT_ENTITY pgise  ");
		feeReportBuilder.append(" join PG_ISS_SETTLEMENT_TXN pgist on pgise.id=pgist.ISS_SETTLEMENT_ENTITY_ID  ");
		feeReportBuilder.append(" where (:isoId is null or pgist.ISO_ID=:isoId) ");
		feeReportBuilder.append(" and (:acqPmId is null or pgise.ACQ_PM_ID=:acqPmId) ");
		feeReportBuilder.append(" and pgise.BATCH_FILE_PROCESSED_DATE >= :startDate ");
		feeReportBuilder.append(" and pgise.BATCH_FILE_PROCESSED_DATE <= :endDate )a ");
		feeReportBuilder.append(" group by a.MID, a.ACQ_SALE_AMOUNT, a.ISS_SALE_AMOUNT, a.MERCHANT_AMOUNT, a.ISS_SETTLEMENT_ENTITY_ID, a.BATCH_ID )b ");
		feeReportBuilder.append(" group by b.MID, b.ACQ_SALE_AMOUNT, b.ISS_SALE_AMOUNT, b.MERCHANT_AMOUNT, b.BATCH_ID ");
		Query merchantFeeReportParam = entityManager.createNativeQuery(feeReportBuilder.toString());
		merchantFeeReportParam.setParameter("isoId", feeReportRequest.getIsoId());
		merchantFeeReportParam.setParameter("acqPmId", feeReportRequest.getProgramManagerId());
		merchantFeeReportParam.setParameter(START_DATE, DateUtil.getStartDayTimestamp(feeReportRequest.getFromDate(), PGConstants.DD_MM_YYYY));
		merchantFeeReportParam.setParameter(END_DATE, DateUtil.getEndDayTimestamp(feeReportRequest.getToDate(), PGConstants.DD_MM_YYYY));
		List<Object> objectList = merchantFeeReportParam.getResultList();
		logger.info("Exiting :: FeeReportDaoImpl :: getTotalNoOfMerchantRevenueRecords");
		return (StringUtils.isListNotNullNEmpty(objectList) ? objectList.size() : 0);
	}
	
	@Override
	public FeeReportResponse fetchPmRevenueTransactions(FeeReportRequest feeReportRequest) {
		logger.info("Entering :: FeeReportDaoImpl :: fetchPmRevenueTransactions");
		Timestamp startDate = null;
		Timestamp endDate = null;
		Integer pageIndex = feeReportRequest.getPageIndex();
        Integer pageSize = feeReportRequest.getPageSize();
        Integer offset = 0;
        Integer limit = 0;
        Integer totalRecords = feeReportRequest.getNoOfRecords();
		FeeReportResponse feeReportResponse = new FeeReportResponse();
		List<SettlementEntity> feeReportList = new ArrayList<>();
		try {
			if (pageIndex == null || pageIndex == 1) {
	            totalRecords = getTotalNumberOfPmRevenueReportRecords(feeReportRequest);
	            feeReportRequest.setNoOfRecords(totalRecords);
	        }
			feeReportResponse.setTotalNoOfRows(totalRecords);
	        if (pageIndex == null && pageSize == null) {
	        	offset = 0;
	        	limit = Constants.DEFAULT_PAGE_SIZE;
	        } else {
	            offset = (pageIndex - 1) * pageSize;
	            limit = pageSize;
	        }
			if (!CommonUtil.isNullAndEmpty(feeReportRequest.getFromDate())) {
				startDate = DateUtil.getStartDayTimestamp(feeReportRequest.getFromDate(), PGConstants.DD_MM_YYYY);
			}
			if (!CommonUtil.isNullAndEmpty(feeReportRequest.getToDate())) {
				endDate = DateUtil.getEndDayTimestamp(feeReportRequest.getToDate(), PGConstants.DD_MM_YYYY);
			}
			JPAQuery query = new JPAQuery(entityManager);
			List<Tuple> infoList = query.from(QPGSettlementEntityHistory.pGSettlementEntityHistory)
					.where(QPGSettlementEntityHistory.pGSettlementEntityHistory.acqPmId
							.eq(Long.valueOf(feeReportRequest.getProgramManagerId()))
							.and(isValidDate(startDate, endDate)))
					 .offset(offset).limit(limit).orderBy(orderByCreatedDateDesc())
					.list(QPGSettlementEntityHistory.pGSettlementEntityHistory.merchantId,
							QPGSettlementEntityHistory.pGSettlementEntityHistory.acqSaleAmount,
							QPGSettlementEntityHistory.pGSettlementEntityHistory.issSaleAmount,
							QPGSettlementEntityHistory.pGSettlementEntityHistory.batchid,
							QPGSettlementEntityHistory.pGSettlementEntityHistory.pmAmount, QPGSettlementEntityHistory.pGSettlementEntityHistory.id);
			if (StringUtil.isListNotNullNEmpty(infoList)) {
				for (Tuple tuple : infoList) {
					SettlementEntity settlementEntity = new SettlementEntity();
					settlementEntity
							.setIssuanceSettlementEntityId(tuple.get(QPGSettlementEntityHistory.pGSettlementEntityHistory.id));
					settlementEntity.setMerchantId(tuple.get(QPGSettlementEntityHistory.pGSettlementEntityHistory.merchantId));
					settlementEntity.setAcquirerAmount(
							tuple.get(QPGSettlementEntityHistory.pGSettlementEntityHistory.acqSaleAmount).toString());
					settlementEntity.setPmAmount(tuple.get(QPGSettlementEntityHistory.pGSettlementEntityHistory.pmAmount));
					settlementEntity.setBatchId(tuple.get(QPGSettlementEntityHistory.pGSettlementEntityHistory.batchid));
					settlementEntity
							.setIssAmount(tuple.get(QPGSettlementEntityHistory.pGSettlementEntityHistory.issSaleAmount).toString());
					feeReportList.add(settlementEntity);
				}
			}
			feeReportResponse.setSettlementEntity(feeReportList);
		} catch (Exception e) {
			logger.error("Error :: FeeReportDaoImpl :: fetchPmRevenueTransactions : " + e.getMessage(), e);
		}
		logger.info("Exiting :: FeeReportDaoImpl :: fetchPmRevenueTransactions");
		return feeReportResponse;
	}
	
	private int getTotalNumberOfPmRevenueReportRecords(FeeReportRequest feeReportRequest) {
		logger.info("Entering :: FeeReportDaoImpl :: getTotalNumberOfPmRevenueReportRecords");
		Timestamp startDate = null;
		Timestamp endDate = null;
		if (!CommonUtil.isNullAndEmpty(feeReportRequest.getFromDate())) {
			startDate = DateUtil.getStartDayTimestamp(feeReportRequest.getFromDate(), PGConstants.DD_MM_YYYY);
		}
		if (!CommonUtil.isNullAndEmpty(feeReportRequest.getToDate())) {
			endDate = DateUtil.getEndDayTimestamp(feeReportRequest.getToDate(), PGConstants.DD_MM_YYYY);
		}
		JPAQuery query = new JPAQuery(entityManager);
		List<Tuple> list = query
				.from(QPGSettlementEntityHistory.pGSettlementEntityHistory)
				.where(QPGSettlementEntityHistory.pGSettlementEntityHistory.acqPmId
						.eq(Long.valueOf(feeReportRequest.getProgramManagerId())).and(isValidDate(startDate, endDate)))
				.orderBy(orderByCreatedDateDesc()).list(QPGSettlementEntityHistory.pGSettlementEntityHistory.merchantId,
						QPGSettlementEntityHistory.pGSettlementEntityHistory.acqSaleAmount,
						QPGSettlementEntityHistory.pGSettlementEntityHistory.issSaleAmount,
						QPGSettlementEntityHistory.pGSettlementEntityHistory.batchid, QPGSettlementEntityHistory.pGSettlementEntityHistory.pmAmount,
						QPGSettlementEntityHistory.pGSettlementEntityHistory.id);
		logger.info("Exiting :: FeeReportDaoImpl :: getTotalNumberOfPmRevenueReportRecords");
		return (StringUtils.isListNotNullNEmpty(list) ? list.size() : 0);
	}

}
