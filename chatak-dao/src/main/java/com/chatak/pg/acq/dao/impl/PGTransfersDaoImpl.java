/**
 * 
 */
package com.chatak.pg.acq.dao.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import com.chatak.pg.acq.dao.PGTransfersDao;
import com.chatak.pg.acq.dao.model.PGTransfers;
import com.chatak.pg.acq.dao.model.QPGMerchant;
import com.chatak.pg.acq.dao.model.QPGTransfers;
import com.chatak.pg.acq.dao.repository.TransfersRepository;
import com.chatak.pg.constants.PGConstants;
import com.chatak.pg.model.ReportsDTO;
import com.chatak.pg.model.TransferRequestsCount;
import com.chatak.pg.user.bean.GetTransferListRequest;
import com.chatak.pg.util.CommonUtil;
import com.chatak.pg.util.Constants;
import com.chatak.pg.util.DateUtil;
import com.chatak.pg.util.StringUtils;
import com.mysema.query.Tuple;
import com.mysema.query.jpa.JPASubQuery;
import com.mysema.query.jpa.impl.JPAQuery;
import com.mysema.query.types.OrderSpecifier;
import com.mysema.query.types.expr.BooleanExpression;

/**
 * @Author: Girmiti Software
 * @Date: Aug 16, 2015
 * @Time: 5:30:29 PM
 * @Version: 1.0
 * @Comments:
 *
 */
@Repository
public class PGTransfersDaoImpl implements PGTransfersDao {
	private static Logger log = Logger.getLogger(PGTransfersDaoImpl.class);
	@Autowired
	TransfersRepository transfersRepository;
	@PersistenceContext
	private EntityManager entityManager;

	/**
	 * @param merchantId
	 * @return
	 * @throws DataAccessException
	 */
	@Override
	public List<PGTransfers> getPGTransferRecordsByMerchantId(Long merchantId)
			throws DataAccessException {
		return transfersRepository.findByMerchantId(merchantId);
	}

	/**
	 * @param id
	 * @return
	 * @throws DataAccessException
	 */
	@Override
	public PGTransfers getPGTransferRecordById(Long id)
			throws DataAccessException {
		return transfersRepository.findByPgTransfersId(id);
	}

	/**
	 * @param pgTransfers
	 */
	@Override
	public PGTransfers createOrUpdateTransferRecord(PGTransfers pgTransfers) {
		return transfersRepository.save(pgTransfers);
	}

	/**
	 * @return
	 * @throws DataAccessException
	 */
	@Override
	public TransferRequestsCount getTransferRequestCountOnTransferMode(
			String transferMode) throws DataAccessException {
		TransferRequestsCount transferRequestsCount = new TransferRequestsCount();
		transferRequestsCount.setProcessingCount(Long
				.valueOf(transfersRepository
						.getProcessingTransactionsCountOnTransferMode(
								transferMode).size()));
		transferRequestsCount.setExecutedCount(Long.valueOf(transfersRepository
				.getExecutedTransactionsCountOnTransferMode(transferMode)
				.size()));
		transferRequestsCount.setPendingCount(Long.valueOf(transfersRepository
				.getPendingTransfersCountOnTransferMode(transferMode).size()));
		transferRequestsCount.setCanceledCount(Long.valueOf(transfersRepository
				.getCanceledTransactionsCountOnTransferMode(transferMode)
				.size()));
		return transferRequestsCount;
	}

	@Override
	public List<PGTransfers> getTransferList(GetTransferListRequest request)
			throws DataAccessException {
		List<PGTransfers> transferList = null;
		try {
			int offset = 0;
			int limit = 0;
			Integer totalRecords = request.getNoOfRecords();

			if (request.getPageIndex() == null
					|| request.getPageIndex().intValue() == 1) {
				totalRecords = getTotalNumberOfRecordsOnTransferStatus(request);
				request.setNoOfRecords(totalRecords);
			}
			request.setNoOfRecords(totalRecords);
			if (request.getPageIndex() == null || request.getPageSize() == null) {
				offset = 0;
				limit = Constants.DEFAULT_PAGE_SIZE;
			} else {
				offset = (request.getPageIndex() - 1) * request.getPageSize();
				limit = request.getPageSize();
			}
			JPAQuery query = new JPAQuery(entityManager);
			transferList = query
					.from(QPGTransfers.pGTransfers)
					.where(isTransferStatus(request.getStatus()),
							isTransferMode(request.getTransferMode()))
					.offset(offset).limit(limit)
					.orderBy(orderByCreatedDateDesc())
					.list(QPGTransfers.pGTransfers);

		} catch (Exception e) {
			log.error("MerchantDaoImpl | getMerchantlist | Exception "+ e);
		}
		return transferList;
	}

	private OrderSpecifier<Timestamp> orderByCreatedDateDesc() {
		return QPGTransfers.pGTransfers.createdDate.desc();
	}

	private BooleanExpression isTransferStatus(String status) {

		return (status != null && !"".equals(status.toString())) ? QPGTransfers.pGTransfers.status
				.eq(status) : null;
	}

	private BooleanExpression isTransferMode(String transferMode) {

		return (transferMode != null && !"".equals(transferMode.toString())) ? QPGTransfers.pGTransfers.transferMode
				.eq(transferMode) : null;
	}
	
	private BooleanExpression isMerchantCode(String merchantCode) {
		return (merchantCode != null && !"".equals(merchantCode.toString())) ? QPGTransfers.pGTransfers.merchantId.eq(Long.valueOf(merchantCode)) : null;
	}

	private Integer getTotalNumberOfRecordsOnTransferStatus(
			GetTransferListRequest request) {
		if (!CommonUtil.isNullAndEmpty(request.getFrom_date())) {
			DateUtil.getStartDayTimestamp(request.getFrom_date(),
					Constants.MM_DD_YYYY);
		}

		if (!CommonUtil.isNullAndEmpty(request.getTo_date())) {
			DateUtil.getEndDayTimestamp(request.getTo_date(),
					Constants.MM_DD_YYYY);
		}

		JPAQuery query = new JPAQuery(entityManager);
		List<Long> list = query
				.from(QPGTransfers.pGTransfers)
				.where(isTransferStatus(request.getStatus()),
						isTransferMode(request.getTransferMode()))
				.list(QPGTransfers.pGTransfers.pgTransfersId);

		return (StringUtils.isListNotNullNEmpty(list) ? list.size() : 0);
	}

	@Override
	public List<ReportsDTO> getPGTransfersListOnTransferMode(GetTransferListRequest request) {
		Timestamp startDate = null;
		Timestamp endDate = null;
		if(!CommonUtil.isNullAndEmpty(request.getFrom_date())) {
	        startDate = DateUtil.getStartDayTimestamp(request.getFrom_date(), Constants.MM_DD_YYYY);
	      }

	      if(!CommonUtil.isNullAndEmpty(request.getTo_date())) {
	        endDate = DateUtil.getEndDayTimestamp(request.getTo_date(), Constants.MM_DD_YYYY);
	      }
		
		List<ReportsDTO> reportsList = new ArrayList<ReportsDTO>();
		JPAQuery query = new JPAQuery(entityManager);
		List<Tuple> list = query
				.from(QPGTransfers.pGTransfers, QPGMerchant.pGMerchant)
				.where(QPGMerchant.pGMerchant.merchantCode.eq(QPGTransfers.pGTransfers.merchantId
						.stringValue()),QPGTransfers.pGTransfers.status.eq(PGConstants.PG_SETTLEMENT_EXECUTED),
						isTransferMode(request.getTransferMode()),
						isValidDate(startDate, endDate))
				.list(QPGTransfers.pGTransfers.createdDate,
						QPGTransfers.pGTransfers.accountType,
						QPGTransfers.pGTransfers.fromAccount,
						QPGTransfers.pGTransfers.pgTransfersId,
						QPGTransfers.pGTransfers.txnDescription,
						QPGTransfers.pGTransfers.amount,
						QPGMerchant.pGMerchant.userName,
						QPGMerchant.pGMerchant.businessName,
						QPGMerchant.pGMerchant);

		if (StringUtils.isListNotNullNEmpty(list)) {
			for (Tuple t : list) {
				ReportsDTO reportsDto = new ReportsDTO();
				reportsDto.setDateTime(DateUtil.toDateStringFormat(t.get(QPGTransfers.pGTransfers.createdDate), DateUtil.VIEW_DATE_TIME_FORMAT));
				reportsDto.setUserName(t.get(QPGMerchant.pGMerchant.userName));
				reportsDto.setAccountNumber(Long.valueOf(t.get(QPGTransfers.pGTransfers.fromAccount).toString()));
				reportsDto.setCompanyName(t.get(QPGMerchant.pGMerchant.businessName));
				if("C".equalsIgnoreCase(t.get(QPGTransfers.pGTransfers.accountType))){
					reportsDto.setAccountType("Checking");
				}else{
					reportsDto.setAccountType("Savings");
				}
				reportsDto.setCurrency("USD");
				reportsDto.setTransactionId(t.get(QPGTransfers.pGTransfers.pgTransfersId).toString());
				reportsDto.setDescription(t.get(QPGTransfers.pGTransfers.txnDescription));
				reportsDto.setAmount(t.get(QPGTransfers.pGTransfers.amount).toString());
				reportsList.add(reportsDto);
			}
		}
		return reportsList;
	}
	
	@Override
	public List<ReportsDTO> getAllEftTransfersListOnMerchantCode(GetTransferListRequest request) {
		Timestamp startDate = null;
		Timestamp endDate = null;
		if(!CommonUtil.isNullAndEmpty(request.getFrom_date())) {
	        startDate = DateUtil.getStartDayTimestamp(request.getFrom_date(), Constants.MM_DD_YYYY);
	      }

	      if(!CommonUtil.isNullAndEmpty(request.getTo_date())) {
	        endDate = DateUtil.getEndDayTimestamp(request.getTo_date(), Constants.MM_DD_YYYY);
	      }
		
		List<ReportsDTO> reportsList = new ArrayList<ReportsDTO>();
		JPAQuery query = new JPAQuery(entityManager);
		QPGMerchant p=new QPGMerchant("p");
		QPGMerchant pp=new QPGMerchant("pp");
		QPGMerchant s=new QPGMerchant("s");
		QPGTransfers qpt=new QPGTransfers("qpt");
		List<Tuple> list = query.distinct()
				.from(QPGTransfers.pGTransfers,p)
				.where(p.merchantCode.eq(QPGTransfers.pGTransfers.merchantId
						.stringValue()),QPGTransfers.pGTransfers.status.eq(PGConstants.PG_SETTLEMENT_EXECUTED),
						isMerchantCode(request.getMerchantCode()),
						isTransferMode(request.getTransferMode()),
						isValidDate(startDate, endDate)
						)
				.list(QPGTransfers.pGTransfers.createdDate,
						QPGTransfers.pGTransfers.accountType,
						QPGTransfers.pGTransfers.fromAccount,
						QPGTransfers.pGTransfers.pgTransfersId,
						QPGTransfers.pGTransfers.txnDescription,
						QPGTransfers.pGTransfers.amount,
						p.userName,
						p.businessName,
						p.parentMerchantId,
						p.merchantCode,
						p.id);

		if (StringUtils.isListNotNullNEmpty(list)) {
			for (Tuple t : list) {
				ReportsDTO reportsDto = new ReportsDTO();
				reportsDto.setDateTime(DateUtil.toDateStringFormat(t.get(QPGTransfers.pGTransfers.createdDate), DateUtil.VIEW_DATE_TIME_FORMAT));
				reportsDto.setUserName(t.get(p.userName));
				reportsDto.setCompanyName(t.get(p.businessName));
				reportsDto.setId(t.get(p.id));
				reportsDto.setMerchantCode(t.get(p.merchantCode));
				reportsDto.setAccountNumber(Long.valueOf(t.get(QPGTransfers.pGTransfers.fromAccount).toString()));
				if("C".equalsIgnoreCase(t.get(QPGTransfers.pGTransfers.accountType))){
					reportsDto.setAccountType("Checking");
				}else{
					reportsDto.setAccountType("Savings");
				}
				reportsDto.setTransactionId(t.get(QPGTransfers.pGTransfers.pgTransfersId).toString());
				reportsDto.setDescription(t.get(QPGTransfers.pGTransfers.txnDescription));
				reportsDto.setCurrency("USD");
				reportsDto.setAmount(t.get(QPGTransfers.pGTransfers.amount).toString());
				reportsList.add(reportsDto);
			}
		}
		if(null!=reportsList){
		List<Tuple> list1 = query.distinct()
				.from(qpt,s,pp)
				.where(s.merchantCode.eq(qpt.merchantId
						.stringValue()),qpt.status.eq(PGConstants.PG_SETTLEMENT_EXECUTED),
						(s.parentMerchantId.in(new JPASubQuery().from(pp).where(pp.merchantCode.eq(request.getMerchantCode())).list(pp.id))),
						isTransferMode(request.getTransferMode()),
						isValidDate(startDate, endDate)
						)
				.list(qpt.createdDate,
						qpt.accountType,
						qpt.fromAccount,
						qpt.pgTransfersId,
						qpt.txnDescription,
						qpt.amount,
						s.userName,
						s.businessName,
						s.parentMerchantId,
						s.merchantCode,
						s.id);

		if (StringUtils.isListNotNullNEmpty(list1)) {
			validateTupleList(reportsList, s, qpt, list1);
		}
		}
		return reportsList;
	}

	private void validateTupleList(List<ReportsDTO> reportsList, QPGMerchant s, QPGTransfers qpt, List<Tuple> list1) {
		for (Tuple t : list1) {
			ReportsDTO reportsDto = new ReportsDTO();
			reportsDto.setDateTime(DateUtil.toDateStringFormat(t.get(qpt.createdDate), DateUtil.VIEW_DATE_TIME_FORMAT));
			reportsDto.setUserName(t.get(s.userName));
			reportsDto.setCompanyName(t.get(s.businessName));
			reportsDto.setId(t.get(s.id));
			reportsDto.setMerchantCode(t.get(s.merchantCode));
			reportsDto.setAccountNumber(Long.valueOf(t.get(qpt.fromAccount).toString()));
			if("C".equalsIgnoreCase(t.get(qpt.accountType))){
				reportsDto.setAccountType("Checking");
			}else{
				reportsDto.setAccountType("Savings");
			}
			reportsDto.setTransactionId(t.get(qpt.pgTransfersId).toString());
			reportsDto.setDescription(t.get(qpt.txnDescription));
			reportsDto.setCurrency("USD");
			reportsDto.setAmount(t.get(qpt.amount).toString());
			reportsList.add(reportsDto);
		}
	}

	private BooleanExpression isValidDate(Timestamp fromDate, Timestamp toDate) {
		if ((fromDate != null && toDate == null)) {
			return QPGTransfers.pGTransfers.createdDate.gt(fromDate);
		} else if ((fromDate == null && toDate != null)) {
			return QPGTransfers.pGTransfers.createdDate.lt(toDate);
		} else if ((fromDate == null && toDate == null))
			return null;

		return QPGTransfers.pGTransfers.createdDate.between(fromDate, toDate);

	}

}
