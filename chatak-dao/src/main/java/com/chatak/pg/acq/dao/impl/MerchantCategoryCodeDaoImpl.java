/**
 * 
 */
package com.chatak.pg.acq.dao.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import com.chatak.pg.acq.dao.MerchantCategoryCodeDao;
import com.chatak.pg.acq.dao.model.PGMCCode;
import com.chatak.pg.acq.dao.model.PGMerchantCategoryCode;
import com.chatak.pg.acq.dao.model.QPGMerchantCategoryCode;
import com.chatak.pg.acq.dao.repository.MCCCodeRepository;
import com.chatak.pg.acq.dao.repository.MerchantCategoryCodeRepository;
import com.chatak.pg.constants.ActionErrorCode;
import com.chatak.pg.constants.PGConstants;
import com.chatak.pg.user.bean.GetMerchantCategoryCodeListResponse;
import com.chatak.pg.user.bean.MerchantCategoryCodeRequest;
import com.chatak.pg.user.bean.MerchantCategoryCodeResponse;
import com.chatak.pg.util.Constants;
import com.chatak.pg.util.StringUtils;
import com.mysema.query.Tuple;
import com.mysema.query.jpa.impl.JPAQuery;
import com.mysema.query.types.OrderSpecifier;
import com.mysema.query.types.expr.BooleanExpression;

/**
 * @Author: Girmiti Software
 * @Date: Aug 22, 2016
 * @Time: 2:48:35 PM
 * @Version: 1.0
 * @Comments:
 *
 */
@Repository
public class MerchantCategoryCodeDaoImpl implements MerchantCategoryCodeDao {

	private static Logger logger = Logger.getLogger(MerchantDaoImpl.class);

	@Autowired
	private MerchantCategoryCodeRepository mccRepository;
	
	@Autowired
	MCCCodeRepository mccCodeRepository;
	
	@PersistenceContext
	private EntityManager entityManager;

	/**
	 * @param mccRequest
	 * @return
	 */
	@Override
	public MerchantCategoryCodeResponse createMCC(
			MerchantCategoryCodeRequest mccRequest) {
		logger.info("MerchantCategoryCodeDaoImpl | createMCC | Entering");
		PGMerchantCategoryCode pgMCC = null;
		MerchantCategoryCodeResponse mccResponse = new MerchantCategoryCodeResponse();
		pgMCC = mccRepository.findByMerchantCategoryCode(mccRequest
				.getMerchantCategoryCode());

		if (null != pgMCC) {
			mccResponse.setErrorCode(ActionErrorCode.ERROR_CODE_MCC1);
			mccResponse.setErrorMessage(ActionErrorCode.getInstance()
					.getMessage(ActionErrorCode.ERROR_CODE_MCC1));
			return mccResponse;
		}

		PGMerchantCategoryCode mcc = new PGMerchantCategoryCode();
		mcc.setMerchantCategoryCode(mccRequest.getMerchantCategoryCode());
		mcc.setSelectedTcc(mccRequest.getSelectedTcc());
		mcc.setDescription(mccRequest.getDescription());
		mcc.setCreatedBy(mccRequest.getCreatedBy());
		mcc.setCreatedDate(new java.sql.Timestamp(System.currentTimeMillis()));
		mcc.setStatus(PGConstants.S_STATUS_ACTIVE);
		mccRepository.save(mcc);
		logger.info("MerchantCategoryCodeDaoImpl | createMCC | MCC details inserted successfully");
		mccResponse.setErrorCode(ActionErrorCode.ERROR_CODE_MCC0);
		mccResponse.setErrorMessage(ActionErrorCode.getInstance().getMessage(
				ActionErrorCode.ERROR_CODE_MCC0));
		logger.info("MerchantCategoryCodeDaoImpl | createMCC | Exiting");
		return mccResponse;
	}

	/**
	 * @param mccRequest
	 * @return
	 */
	@Override
	public GetMerchantCategoryCodeListResponse getMCCList(
			MerchantCategoryCodeRequest mccRequest) {
		logger.info("MerchantCategoryCodeDaoImpl | getMCCList | Entering");
		GetMerchantCategoryCodeListResponse mccListResponse = new GetMerchantCategoryCodeListResponse();
		List<PGMerchantCategoryCode> pgMCCList = null;

		try {
			int offset = 0;
			int limit = 0;
			Integer totalRecords = mccRequest.getNoOfRecords();

			if (mccRequest.getPageIndex() == null
					|| mccRequest.getPageIndex() == 1) {
				totalRecords = getTotalNumberOfMCCRecords(mccRequest);
				mccRequest.setNoOfRecords(totalRecords);
			}
			mccListResponse.setNoOfRecords(totalRecords);

			if (mccRequest.getPageIndex() == null
					&& mccRequest.getPageSize() == null) {
				offset = 0;
				limit = Constants.DEFAULT_PAGE_SIZE;
			} else {
				offset = (mccRequest.getPageIndex() - 1)
						* mccRequest.getPageSize();
				limit = mccRequest.getPageSize();
			}
			JPAQuery query = new JPAQuery(entityManager);
			List<Tuple> tupleList = query
					.from(QPGMerchantCategoryCode.pGMerchantCategoryCode)
					.where(isMCCLike(mccRequest.getMerchantCategoryCode()),
							isStatusEq(mccRequest.getStatus()),
							isMCCStatusNotEq())
					.offset(offset)
					.limit(limit)
					.orderBy(orderByCreatedDateDesc())
					.list(QPGMerchantCategoryCode.pGMerchantCategoryCode.id,
							QPGMerchantCategoryCode.pGMerchantCategoryCode.merchantCategoryCode,
							QPGMerchantCategoryCode.pGMerchantCategoryCode.selectedTcc,
							QPGMerchantCategoryCode.pGMerchantCategoryCode.description,
							QPGMerchantCategoryCode.pGMerchantCategoryCode.status);
			if (!CollectionUtils.isEmpty(tupleList)) {
				pgMCCList = new ArrayList<PGMerchantCategoryCode>();
				PGMerchantCategoryCode pgMCC = null;
				for (Tuple tuple : tupleList) {
					pgMCC = new PGMerchantCategoryCode();
					pgMCC.setId(tuple
							.get(QPGMerchantCategoryCode.pGMerchantCategoryCode.id));
					pgMCC.setMerchantCategoryCode(tuple
							.get(QPGMerchantCategoryCode.pGMerchantCategoryCode.merchantCategoryCode));
					pgMCC.setSelectedTcc(tuple
							.get(QPGMerchantCategoryCode.pGMerchantCategoryCode.selectedTcc));
					pgMCC.setDescription(tuple
							.get(QPGMerchantCategoryCode.pGMerchantCategoryCode.description));
					pgMCC.setStatus(tuple
							.get(QPGMerchantCategoryCode.pGMerchantCategoryCode.status));

					pgMCCList.add(pgMCC);
				}
			}

			if (pgMCCList != null && !pgMCCList.isEmpty()) {
				mccListResponse.setPgMCC(pgMCCList);
				mccListResponse.setErrorCode(ActionErrorCode.ERROR_CODE_00);
				mccListResponse.setErrorMessage(ActionErrorCode.getInstance()
						.getMessage(ActionErrorCode.ERROR_CODE_00));
			} else {
				mccListResponse.setErrorCode(ActionErrorCode.ERROR_CODE_Z5);
				mccListResponse.setErrorMessage(PGConstants.NO_RECORDS_FOUND);
			}

		} catch (Exception e) {
			logger.error("MerchantCategoryCodeDaoImpl | getMCCList | Exception "+ e);
			mccListResponse.setErrorCode(ActionErrorCode.ERROR_CODE_Z5);
			mccListResponse.setErrorMessage(ActionErrorCode.getInstance()
					.getMessage(ActionErrorCode.ERROR_CODE_Z5));
		}

		logger.info("MerchantCategoryCodeDaoImpl | getMCCList | Exiting");
		return mccListResponse;
	}

	/**
	 * @return
	 */
	private BooleanExpression isMCCStatusNotEq() {
		return(QPGMerchantCategoryCode.pGMerchantCategoryCode.status.ne("Deleted"));
	}

	/**
	 * @param mcc
	 * @return
	 * @throws DataAccessException
	 */
	@Override
	public PGMerchantCategoryCode findById(Long id) throws DataAccessException {
		return mccRepository.findById(id);
	}

	/**
	 * @param mccRequest
	 * @return
	 */
	@Override
	public MerchantCategoryCodeResponse updateMCC(
			MerchantCategoryCodeRequest mccRequest) {
		logger.info("MerchantCategoryCodeDaoImpl | updateMCC | Entering");
		PGMerchantCategoryCode pgMCCId = null;
		PGMerchantCategoryCode pgMCC = null;
		MerchantCategoryCodeResponse mccResponse = new MerchantCategoryCodeResponse();
		pgMCCId = mccRepository.findById(mccRequest.getId());
		if (null != pgMCCId) {
			pgMCC = mccRepository.findByMerchantCategoryCode(mccRequest.getMerchantCategoryCode());
			if (null != pgMCC && pgMCC.getMerchantCategoryCode().equals(pgMCCId.getMerchantCategoryCode())) {

				updateMerchantCategoryCode(mccRequest, pgMCCId, mccResponse);

			} else if (null == pgMCC) {
				
				updateMerchantCategoryCode(mccRequest, pgMCCId, mccResponse);
								
			} else {
		
				mccResponse.setErrorCode(ActionErrorCode.ERROR_CODE_MCC1);
				mccResponse.setErrorMessage(ActionErrorCode.getInstance().getMessage(ActionErrorCode.ERROR_CODE_MCC1));
				return mccResponse;
			}

		}else{
			mccResponse.setErrorCode(ActionErrorCode.ERROR_CODE_MCC3);
			mccResponse.setErrorMessage(ActionErrorCode.getInstance().getMessage(ActionErrorCode.ERROR_CODE_MCC3));
		}
		logger.info("MerchantCategoryCodeDaoImpl | updateMCC | Exiting");
		return mccResponse;
}

	private void updateMerchantCategoryCode(MerchantCategoryCodeRequest mccRequest, PGMerchantCategoryCode pgMCCId,
			MerchantCategoryCodeResponse mccResponse) {
		pgMCCId.setMerchantCategoryCode(mccRequest.getMerchantCategoryCode());
		pgMCCId.setSelectedTcc(mccRequest.getSelectedTcc());
		pgMCCId.setDescription(mccRequest.getDescription());
		pgMCCId.setUpdatedBy(mccRequest.getUpdatedBy());
		pgMCCId.setUpdatedDate(new java.sql.Timestamp(System.currentTimeMillis()));
		pgMCCId.setStatus(mccRequest.getStatus());
		mccRepository.save(pgMCCId);
		
		logger.info("MerchantCategoryCodeDaoImpl | updateMCC | MCC details inserted successfully");
		mccResponse.setErrorCode(ActionErrorCode.ERROR_CODE_MCC2);
		mccResponse.setErrorMessage(ActionErrorCode.getInstance().getMessage(ActionErrorCode.ERROR_CODE_MCC2));
	}

	private int getTotalNumberOfMCCRecords(
			MerchantCategoryCodeRequest mccRequest) {
		JPAQuery query = new JPAQuery(entityManager);
		List<Long> list = query
				.from(QPGMerchantCategoryCode.pGMerchantCategoryCode)
				.where(isMCCLike(mccRequest.getMerchantCategoryCode()),
						isStatusEq(mccRequest.getStatus()),
						isMCCStatusNotEq())
				.list(QPGMerchantCategoryCode.pGMerchantCategoryCode.id);

		return (StringUtils.isListNotNullNEmpty(list) ? list.size() : 0);
	}

	private BooleanExpression isMCCLike(String mcc) {
		return (mcc != null && !"".equals(mcc)) ? QPGMerchantCategoryCode.pGMerchantCategoryCode.merchantCategoryCode
				.toUpperCase().like(
						"%" + mcc.toUpperCase().replace("*", "") + "%") : null;
	}

	private BooleanExpression isStatusEq(String status) {
		return (status != null && !"".equals(status)) ? QPGMerchantCategoryCode.pGMerchantCategoryCode.status
				.eq(status) : null;
	}

	private OrderSpecifier<Timestamp> orderByCreatedDateDesc() {
		return QPGMerchantCategoryCode.pGMerchantCategoryCode.createdDate
				.desc();
	}

	/**
	 * @return
	 */
	@Override
	public List<String> getAllMCC() {
		List<String> mccList = new LinkedList<>();
		List<PGMCCode> categoryCodes = mccCodeRepository.findAll();
		if (null != categoryCodes) {
			for (PGMCCode code : categoryCodes) {
				mccList.add(code.getMerchantCategoryCode());
			}

		}
		return mccList;
	}
	
	@Override
	public PGMerchantCategoryCode createOrUpdateMerchantCategoryCode(PGMerchantCategoryCode pgMerchantCategoryCode) throws DataAccessException {
	    return mccRepository.save(pgMerchantCategoryCode);
	}
}
