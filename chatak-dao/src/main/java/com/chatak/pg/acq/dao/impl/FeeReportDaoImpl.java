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
import com.chatak.pg.acq.dao.model.settlement.QPGSettlementEntity;
import com.chatak.pg.acq.dao.model.settlement.QPGSettlementTransaction;
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
import com.chatak.pg.util.LogHelper;
import com.chatak.pg.util.LoggerMessage;
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
		LogHelper.logEntry(logger, LoggerMessage.getCallerName());
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
			feeReportParam.setParameter(START_DATE, feeReportRequest.getFromDate());
			feeReportParam.setParameter(END_DATE, feeReportRequest.getToDate());
			feeReportParam.setParameter("offset", offset);
			feeReportParam.setParameter("limit", limit);
			List<Object> objectList = feeReportParam.getResultList();
			if(StringUtil.isListNotNullNEmpty(objectList)) {
				iterateFeeReportDetails(feeReportList, objectList);
			}
			feeReportResponse.setFeeReportDto(feeReportList);
		}catch(Exception e) {
			LogHelper.logError(logger, LoggerMessage.getCallerName(), e, Constants.EXCEPTION);
		}
		LogHelper.logExit(logger, LoggerMessage.getCallerName());
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
				isoFeeReportParam.setParameter(START_DATE, StringUtil.isNullAndEmpty(feeReportRequest.getFromDate())?null:feeReportRequest.getFromDate());
				isoFeeReportParam.setParameter(END_DATE, StringUtil.isNullAndEmpty(feeReportRequest.getToDate())?null:feeReportRequest.getToDate());
				List<Object> objectList = isoFeeReportParam.getResultList();
				Iterator<Object> itr = objectList.iterator();
				if(StringUtil.isListNotNullNEmpty(objectList)) {
					iterateISOFeeReportDetails(settlementEntityList, itr);
				}
				feeReportResponse.setSettlementEntity(settlementEntityList);
			}catch(Exception e) {
				LogHelper.logError(logger, LoggerMessage.getCallerName(), e, Constants.EXCEPTION);
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
		LogHelper.logEntry(logger, LoggerMessage.getCallerName());
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
			qry.setParameter(START_DATE, feeReportRequest.getFromDate());
			qry.setParameter(END_DATE, feeReportRequest.getToDate());
			List<Object> objectList = qry.getResultList();
			LogHelper.logExit(logger, LoggerMessage.getCallerName());
        return (objectList != null && !objectList.isEmpty() ? objectList
                .size() : 0);
    }
	
	@Override
	public FeeReportResponse fetchISORevenueTransactions(FeeReportRequest feeReportRequest) {
		LogHelper.logEntry(logger, LoggerMessage.getCallerName());
		
		Timestamp startDate = null;
		Timestamp endDate = null;
		Integer pageIndex = feeReportRequest.getPageIndex();
        
		// Required for future implementation
		/*Integer pageSize = feeReportRequest.getPageSize();
        Integer offset = 0;
        Integer limit = 0;*/
        Integer totalRecords;
		FeeReportResponse feeReportResponse = new FeeReportResponse();
		List<SettlementEntity> feeReportList = new ArrayList<>();
		try {
			if (pageIndex == null || pageIndex == 1) {
	            totalRecords = getTotalNumberOfIsoRevenueReportRecords(feeReportRequest);
	            feeReportRequest.setNoOfRecords(totalRecords);
	        }
			
			// Required for future implementation
	        /*if (pageIndex == null && pageSize == null) {
	        	offset = 0;
	        	limit = Constants.DEFAULT_PAGE_SIZE;
	        } else {
	            offset = (pageIndex - 1) * pageSize;
	            limit = pageSize;
	        }*/
			if (!CommonUtil.isNullAndEmpty(feeReportRequest.getFromDate())) {
				startDate = DateUtil.getStartDayTimestamp(feeReportRequest.getFromDate(), PGConstants.DD_MM_YYYY);
			}
			if (!CommonUtil.isNullAndEmpty(feeReportRequest.getToDate())) {
				endDate = DateUtil.getEndDayTimestamp(feeReportRequest.getToDate(), PGConstants.DD_MM_YYYY);
			}
			JPAQuery query = new JPAQuery(entityManager);
			List<Tuple> infoList = query.from(QPGSettlementEntity.pGSettlementEntity, QPGSettlementTransaction.pGSettlementTransaction)
					.where(QPGSettlementEntity.pGSettlementEntity.acqPmId
							.eq(Long.valueOf(feeReportRequest.getProgramManagerId()))
							.and(isValidDate(startDate, endDate))
							.and(QPGSettlementEntity.pGSettlementEntity.id.eq(QPGSettlementTransaction.pGSettlementTransaction.issuanceSettlementEntityId)),
							 isIsoIdEq(feeReportRequest.getIsoId()))
					.list(QPGSettlementEntity.pGSettlementEntity.merchantId,
							QPGSettlementEntity.pGSettlementEntity.acqSaleAmount,
							QPGSettlementEntity.pGSettlementEntity.issSaleAmount,
							QPGSettlementEntity.pGSettlementEntity.batchid,
							QPGSettlementTransaction.pGSettlementTransaction.isoAmount,QPGSettlementTransaction.pGSettlementTransaction.issuanceSettlementEntityId);
			if (StringUtil.isListNotNullNEmpty(infoList)) {
				for (Tuple tuple : infoList) {
					SettlementEntity settlementEntity = new SettlementEntity();
					settlementEntity
							.setIssuanceSettlementEntityId(tuple.get(QPGSettlementTransaction.pGSettlementTransaction.issuanceSettlementEntityId));
					settlementEntity.setMerchantId(tuple.get(QPGSettlementEntity.pGSettlementEntity.merchantId));
					settlementEntity.setAcquirerAmount(
							tuple.get(QPGSettlementEntity.pGSettlementEntity.acqSaleAmount).toString());
					settlementEntity.setIsoAmount(tuple.get(QPGSettlementTransaction.pGSettlementTransaction.isoAmount));
					settlementEntity.setBatchId(tuple.get(QPGSettlementEntity.pGSettlementEntity.batchid));
					settlementEntity
							.setIssAmount(tuple.get(QPGSettlementEntity.pGSettlementEntity.issSaleAmount).toString());
					feeReportList.add(settlementEntity);
				}
			}
			feeReportResponse.setSettlementEntity(feeReportList);
		} catch (Exception e) {
			LogHelper.logError(logger, LoggerMessage.getCallerName(), e, Constants.EXCEPTION);
		}
		LogHelper.logExit(logger, LoggerMessage.getCallerName());
		return feeReportResponse;
		
	}
	
	protected BooleanExpression isValidDate(Timestamp fromDate, Timestamp toDate) {
		if ((fromDate != null && toDate == null)) {
			return QPGSettlementEntity.pGSettlementEntity.batchFileProcessedDate.gt(fromDate);
		} else if ((fromDate == null && toDate != null)) {
			return QPGSettlementEntity.pGSettlementEntity.batchFileProcessedDate.lt(toDate);
		} else if ((fromDate == null))
			return null;
		return QPGSettlementEntity.pGSettlementEntity.batchFileProcessedDate.between(fromDate, toDate);
	}
	
	protected OrderSpecifier<Timestamp> orderByCreatedDateDesc() {
		return QPGSettlementEntity.pGSettlementEntity.batchFileProcessedDate.desc();
	}
	
	private int getTotalNumberOfIsoRevenueReportRecords(FeeReportRequest feeReportRequest) {
		LogHelper.logEntry(logger, LoggerMessage.getCallerName());
		Timestamp startDate = null;
		Timestamp endDate = null;
		if (!CommonUtil.isNullAndEmpty(feeReportRequest.getFromDate())) {
			startDate = DateUtil.getStartDayTimestamp(feeReportRequest.getFromDate(), PGConstants.DD_MM_YYYY);
		}
		if (!CommonUtil.isNullAndEmpty(feeReportRequest.getToDate())) {
			endDate = DateUtil.getEndDayTimestamp(feeReportRequest.getToDate(), PGConstants.DD_MM_YYYY);
		}
		JPAQuery query = new JPAQuery(entityManager);
		List<Tuple> list = query.from(QPGSettlementEntity.pGSettlementEntity, QPGSettlementTransaction.pGSettlementTransaction)
				.where(QPGSettlementEntity.pGSettlementEntity.acqPmId
						.eq(Long.valueOf(feeReportRequest.getProgramManagerId()))
						.and(isValidDate(startDate, endDate))
						.and(QPGSettlementEntity.pGSettlementEntity.id.eq(QPGSettlementTransaction.pGSettlementTransaction.issuanceSettlementEntityId)),
						 isIsoIdEq(feeReportRequest.getIsoId()))
				.list(QPGSettlementEntity.pGSettlementEntity.merchantId,
						QPGSettlementEntity.pGSettlementEntity.acqSaleAmount,
						QPGSettlementEntity.pGSettlementEntity.issSaleAmount,
						QPGSettlementEntity.pGSettlementEntity.batchid,
						QPGSettlementTransaction.pGSettlementTransaction.isoAmount,QPGSettlementTransaction.pGSettlementTransaction.issuanceSettlementEntityId);
		LogHelper.logExit(logger, LoggerMessage.getCallerName());
		return (StringUtils.isListNotNullNEmpty(list) ? list.size() : 0);
	}
	
	private BooleanExpression isIsoIdEq(Long isoid) {

		return (isoid != null && !"".equals(isoid)) ? QPGSettlementTransaction.pGSettlementTransaction.isoId.eq(isoid)
				: null;
	}
	
	private BooleanExpression isIssuanceSettlementEntityIdIdEq(Long issuanceSettlementEntityId) {

		return (issuanceSettlementEntityId != null && !"".equals(issuanceSettlementEntityId)) ? QPGSettlementTransaction.pGSettlementTransaction.issuanceSettlementEntityId.eq(issuanceSettlementEntityId)
				: null;
	}
	
	@Override
	public List<SettlementEntity> getAllMatchedTxnsByEntityId(Long issuanceSettlementEntityId) {
		List<SettlementEntity> feeReportList = new ArrayList<>();
		JPAQuery query = new JPAQuery(entityManager);
		List<Tuple> infoList = query
				.from(QPGSettlementTransaction.pGSettlementTransaction, QPGTransaction.pGTransaction, QPGSettlementEntity.pGSettlementEntity)
				.where(isIssuanceSettlementEntityIdIdEq(issuanceSettlementEntityId)
						.and(QPGTransaction.pGTransaction.transactionId
								.eq(QPGSettlementTransaction.pGSettlementTransaction.pgTransactionId))
						.and(QPGSettlementEntity.pGSettlementEntity.id.eq(QPGSettlementTransaction.pGSettlementTransaction.issuanceSettlementEntityId)))
				.list(QPGSettlementTransaction.pGSettlementTransaction.terminalId,
						QPGSettlementTransaction.pGSettlementTransaction.pgTransactionId,
						QPGSettlementTransaction.pGSettlementTransaction.issuerTxnID,
						QPGSettlementTransaction.pGSettlementTransaction.isoAmount,
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
						QPGTransaction.pGTransaction.invoiceNumber, QPGSettlementEntity.pGSettlementEntity.acqPmId, QPGSettlementTransaction.pGSettlementTransaction.isoId);
		if (StringUtil.isListNotNullNEmpty(infoList)) {
			for (Tuple tuple : infoList) {
				SettlementEntity settlementEntity = new SettlementEntity();
				settlementEntity.setTerminalId(tuple.get(QPGSettlementTransaction.pGSettlementTransaction.terminalId));
				settlementEntity.setMerchantId(tuple.get(QPGTransaction.pGTransaction.merchantId));
				settlementEntity.setPgTxnId(tuple.get(QPGSettlementTransaction.pGSettlementTransaction.pgTransactionId));
				settlementEntity.setIssTxnId(tuple.get(QPGSettlementTransaction.pGSettlementTransaction.issuerTxnID));
				settlementEntity.setBatchId(tuple.get(QPGTransaction.pGTransaction.batchId));
				settlementEntity.setDeviceLocalTxnTime(tuple.get(QPGTransaction.pGTransaction.deviceLocalTxnTime));
				settlementEntity.setTransactionType(tuple.get(QPGTransaction.pGTransaction.transactionType));
				settlementEntity.setTxnTotalAmount(tuple.get(QPGTransaction.pGTransaction.txnTotalAmount));
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
				settlementEntity.setAcqPmId(tuple.get(QPGSettlementEntity.pGSettlementEntity.acqPmId).toString());
				settlementEntity.setIsoId(tuple.get(QPGSettlementTransaction.pGSettlementTransaction.isoId));
				
				List<PGAccountTransactions> pgAccountTransactionsList = accountTransactionsRepository.findByPgTransactionId(tuple.get(QPGSettlementTransaction.pGSettlementTransaction.pgTransactionId));
				for (PGAccountTransactions pgAccountTransaction : pgAccountTransactionsList) {
					if(pgAccountTransaction.getEntityType().equals(Constants.ISO_USER_TYPE)){
						Long isoAmount = pgAccountTransaction.getCredit();
						settlementEntity.setIsoAmount(isoAmount);
					} 
					if(pgAccountTransaction.getEntityType().equals(Constants.PM_USER_TYPE)){
						Long pmAmount = pgAccountTransaction.getCredit();
						settlementEntity.setPmAmount(pmAmount);
					}
					if(pgAccountTransaction.getEntityType().equals(PGConstants.MERCHANT)){
						Long merchantAmount = pgAccountTransaction.getCredit();
						settlementEntity
						.setMerchantAmount(merchantAmount);
					}
				}
				feeReportList.add(settlementEntity);
			}
		}
		return feeReportList;
	}
	
	@Override
	public FeeReportResponse fetchMerchantRevenueTransactions(FeeReportRequest feeReportRequest) {
		LogHelper.logEntry(logger, LoggerMessage.getCallerName());
		
		Timestamp startDate = null;
		Timestamp endDate = null;
		Integer pageIndex = feeReportRequest.getPageIndex();
        Integer pageSize = feeReportRequest.getPageSize();
        Integer offset = 0;
        Integer limit = 0;
        Integer totalRecords;
		FeeReportResponse feeReportResponse = new FeeReportResponse();
		List<SettlementEntity> feeReportList = new ArrayList<>();
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
			if (!CommonUtil.isNullAndEmpty(feeReportRequest.getFromDate())) {
				startDate = DateUtil.getStartDayTimestamp(feeReportRequest.getFromDate(), PGConstants.DD_MM_YYYY);
			}
			if (!CommonUtil.isNullAndEmpty(feeReportRequest.getToDate())) {
				endDate = DateUtil.getEndDayTimestamp(feeReportRequest.getToDate(), PGConstants.DD_MM_YYYY);
			}
			JPAQuery query = new JPAQuery(entityManager);
			List<Tuple> infoList = query.from(QPGSettlementEntity.pGSettlementEntity, QPGSettlementTransaction.pGSettlementTransaction)
					.where(QPGSettlementEntity.pGSettlementEntity.acqPmId
							.eq(Long.valueOf(feeReportRequest.getProgramManagerId()))
							.and(isValidDate(startDate, endDate))
							.and(QPGSettlementEntity.pGSettlementEntity.id.eq(QPGSettlementTransaction.pGSettlementTransaction.issuanceSettlementEntityId)),
							 isIsoIdEq(feeReportRequest.getIsoId()))
					 .offset(offset).limit(limit).orderBy(orderByCreatedDateDesc())
					.list(QPGSettlementEntity.pGSettlementEntity.merchantId,
							QPGSettlementEntity.pGSettlementEntity.acqSaleAmount,
							QPGSettlementEntity.pGSettlementEntity.issSaleAmount,
							QPGSettlementEntity.pGSettlementEntity.batchid,
							QPGSettlementEntity.pGSettlementEntity.merchantAmount, QPGSettlementTransaction.pGSettlementTransaction.issuanceSettlementEntityId);
			if (StringUtil.isListNotNullNEmpty(infoList)) {
				for (Tuple tuple : infoList) {
					SettlementEntity settlementEntity = new SettlementEntity();
					settlementEntity
							.setIssuanceSettlementEntityId(tuple.get(QPGSettlementTransaction.pGSettlementTransaction.issuanceSettlementEntityId));
					settlementEntity.setMerchantId(tuple.get(QPGSettlementEntity.pGSettlementEntity.merchantId));
					settlementEntity.setAcquirerAmount(
							tuple.get(QPGSettlementEntity.pGSettlementEntity.acqSaleAmount).toString());
					settlementEntity.setMerchantAmount(tuple.get(QPGSettlementEntity.pGSettlementEntity.merchantAmount));
					settlementEntity.setBatchId(tuple.get(QPGSettlementEntity.pGSettlementEntity.batchid));
					settlementEntity
							.setIssAmount(tuple.get(QPGSettlementEntity.pGSettlementEntity.issSaleAmount).toString());
					feeReportList.add(settlementEntity);
				}
			}
			feeReportResponse.setSettlementEntity(feeReportList);
		} catch (Exception e) {
			LogHelper.logError(logger, LoggerMessage.getCallerName(), e, Constants.EXCEPTION);
		}
		LogHelper.logExit(logger, LoggerMessage.getCallerName());
		return feeReportResponse;		
	}
	
	public int getTotalNoOfMerchantRevenueRecords(FeeReportRequest feeReportRequest) {
		LogHelper.logEntry(logger, LoggerMessage.getCallerName());
		Timestamp startDate = null;
		Timestamp endDate = null;
		if (!CommonUtil.isNullAndEmpty(feeReportRequest.getFromDate())) {
			startDate = DateUtil.getStartDayTimestamp(feeReportRequest.getFromDate(), PGConstants.DD_MM_YYYY);
		}
		if (!CommonUtil.isNullAndEmpty(feeReportRequest.getToDate())) {
			endDate = DateUtil.getEndDayTimestamp(feeReportRequest.getToDate(), PGConstants.DD_MM_YYYY);
		}
		JPAQuery query = new JPAQuery(entityManager);
		List<Tuple> list = query.from(QPGSettlementEntity.pGSettlementEntity, QPGSettlementTransaction.pGSettlementTransaction)
				.where(QPGSettlementEntity.pGSettlementEntity.acqPmId
						.eq(Long.valueOf(feeReportRequest.getProgramManagerId()))
						.and(isValidDate(startDate, endDate))
						.and(QPGSettlementEntity.pGSettlementEntity.id.eq(QPGSettlementTransaction.pGSettlementTransaction.issuanceSettlementEntityId)),
						 isIsoIdEq(feeReportRequest.getIsoId()))
				.list(QPGSettlementEntity.pGSettlementEntity.merchantId,
						QPGSettlementEntity.pGSettlementEntity.acqSaleAmount,
						QPGSettlementEntity.pGSettlementEntity.issSaleAmount,
						QPGSettlementEntity.pGSettlementEntity.batchid,
						QPGSettlementEntity.pGSettlementEntity.merchantAmount, QPGSettlementTransaction.pGSettlementTransaction.issuanceSettlementEntityId);
		LogHelper.logExit(logger, LoggerMessage.getCallerName());
		return (StringUtils.isListNotNullNEmpty(list) ? list.size() : 0);
	}
	
	@Override
	public FeeReportResponse fetchPmRevenueTransactions(FeeReportRequest feeReportRequest) {
		LogHelper.logEntry(logger, LoggerMessage.getCallerName());
		Timestamp startDate = null;
		Timestamp endDate = null;
		Integer pageIndex = feeReportRequest.getPageIndex();
        Integer pageSize = feeReportRequest.getPageSize();
        Integer offset = 0;
        Integer limit = 0;
        Integer totalRecords;
		FeeReportResponse feeReportResponse = new FeeReportResponse();
		List<SettlementEntity> feeReportList = new ArrayList<>();
		try {
			if (pageIndex == null || pageIndex == 1) {
	            totalRecords = getTotalNumberOfPmRevenueReportRecords(feeReportRequest);
	            feeReportRequest.setNoOfRecords(totalRecords);
	        }

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
			List<Tuple> infoList = query.from(QPGSettlementEntity.pGSettlementEntity)
					.where(QPGSettlementEntity.pGSettlementEntity.acqPmId
							.eq(Long.valueOf(feeReportRequest.getProgramManagerId()))
							.and(isValidDate(startDate, endDate)))
					 .offset(offset).limit(limit).orderBy(orderByCreatedDateDesc())
					.list(QPGSettlementEntity.pGSettlementEntity.merchantId,
							QPGSettlementEntity.pGSettlementEntity.acqSaleAmount,
							QPGSettlementEntity.pGSettlementEntity.issSaleAmount,
							QPGSettlementEntity.pGSettlementEntity.batchid,
							QPGSettlementEntity.pGSettlementEntity.pmAmount, QPGSettlementEntity.pGSettlementEntity.id);
			if (StringUtil.isListNotNullNEmpty(infoList)) {
				for (Tuple tuple : infoList) {
					SettlementEntity settlementEntity = new SettlementEntity();
					settlementEntity
							.setIssuanceSettlementEntityId(tuple.get(QPGSettlementEntity.pGSettlementEntity.id));
					settlementEntity.setMerchantId(tuple.get(QPGSettlementEntity.pGSettlementEntity.merchantId));
					settlementEntity.setAcquirerAmount(
							tuple.get(QPGSettlementEntity.pGSettlementEntity.acqSaleAmount).toString());
					settlementEntity.setPmAmount(tuple.get(QPGSettlementEntity.pGSettlementEntity.pmAmount));
					settlementEntity.setBatchId(tuple.get(QPGSettlementEntity.pGSettlementEntity.batchid));
					settlementEntity
							.setIssAmount(tuple.get(QPGSettlementEntity.pGSettlementEntity.issSaleAmount).toString());
					feeReportList.add(settlementEntity);
				}
			}
			feeReportResponse.setSettlementEntity(feeReportList);
		} catch (Exception e) {
			LogHelper.logError(logger, LoggerMessage.getCallerName(), e, Constants.EXCEPTION);
		}
		LogHelper.logExit(logger, LoggerMessage.getCallerName());
		return feeReportResponse;
	}
	
	private int getTotalNumberOfPmRevenueReportRecords(FeeReportRequest feeReportRequest) {
		LogHelper.logEntry(logger, LoggerMessage.getCallerName());
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
				.from(QPGSettlementEntity.pGSettlementEntity)
				.where(QPGSettlementEntity.pGSettlementEntity.acqPmId
						.eq(Long.valueOf(feeReportRequest.getProgramManagerId())).and(isValidDate(startDate, endDate)))
				.orderBy(orderByCreatedDateDesc()).list(QPGSettlementEntity.pGSettlementEntity.merchantId,
						QPGSettlementEntity.pGSettlementEntity.acqSaleAmount,
						QPGSettlementEntity.pGSettlementEntity.issSaleAmount,
						QPGSettlementEntity.pGSettlementEntity.batchid, QPGSettlementEntity.pGSettlementEntity.pmAmount,
						QPGSettlementEntity.pGSettlementEntity.id);
		LogHelper.logExit(logger, LoggerMessage.getCallerName());
		return (StringUtils.isListNotNullNEmpty(list) ? list.size() : 0);
	}

}
