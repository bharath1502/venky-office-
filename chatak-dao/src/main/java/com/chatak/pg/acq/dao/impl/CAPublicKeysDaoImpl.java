package com.chatak.pg.acq.dao.impl;


import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import com.chatak.pg.acq.dao.CAPublicKeysDao;
import com.chatak.pg.acq.dao.model.PGCaPublicKeys;
import com.chatak.pg.acq.dao.model.QPGCaPublicKeys;
import com.chatak.pg.acq.dao.repository.CAPublicKeysRepository;
import com.chatak.pg.bean.Response;
import com.chatak.pg.constants.ActionErrorCode;
import com.chatak.pg.constants.PGConstants;
import com.chatak.pg.dao.util.StringUtil;
import com.chatak.pg.model.CAPublicKeysDTO;
import com.chatak.pg.util.Constants;
import com.mysema.query.Tuple;
import com.mysema.query.jpa.impl.JPAQuery;
import com.mysema.query.types.OrderSpecifier;
import com.mysema.query.types.expr.BooleanExpression;

/**
 * @Author: Girmiti Software
 * @Date: Aug 4, 2016
 * @Time: 6:37:30 PM
 * @Version: 1.0
 * @Comments:
 *
 */
@Repository("caPublicKeysDao")
public class CAPublicKeysDaoImpl implements CAPublicKeysDao {

	private static Logger logger = Logger.getLogger(CAPublicKeysDaoImpl.class);

	@PersistenceContext
	private EntityManager entityManager;

	@Autowired
	CAPublicKeysRepository caPublickeysRepository;

	@Override
	public List<CAPublicKeysDTO> searchCAPublicKeys(
			CAPublicKeysDTO caPublicKeysDTO) {
		logger.info("caPublicKeysService :: CAPublicKeysDaoImpl :: searchCAPublicKeys Entering ");
		List<CAPublicKeysDTO> caPublicKeysRequestList = new ArrayList<CAPublicKeysDTO>();
		int offset = 0;
		int limit = 0;

		Integer totalRecords;

		if (caPublicKeysDTO.getPageIndex() == null
				|| caPublicKeysDTO.getPageIndex() == 1) {
			totalRecords = getTotalNumberOfRecords(caPublicKeysDTO);
			caPublicKeysDTO.setNoOfRecords(totalRecords);
		}
		if (caPublicKeysDTO.getPageIndex() == null
				&& caPublicKeysDTO.getPageSize() == null) {
			offset = 0;
			limit = Constants.DEFAULT_PAGE_SIZE;
		} else {
			offset = (caPublicKeysDTO.getPageIndex() - 1)
					* caPublicKeysDTO.getPageSize();
			limit = caPublicKeysDTO.getPageSize();
		}
		JPAQuery query = new JPAQuery(entityManager);

		List<Tuple> tuplePublicKeyList = query
				.from(QPGCaPublicKeys.pGCaPublicKeys)
				.where(isCAPublicKeyName(caPublicKeysDTO.getPublicKeyName()),
						isRID(caPublicKeysDTO.getRid()),
						isPublicKeyIndex(caPublicKeysDTO.getPublicKeyIndex()),
						isPublicKeyModulus(caPublicKeysDTO
								.getPublicKeyModulus()),
						isPublicKeyExponent(caPublicKeysDTO
								.getPublicKeyExponent()),
						isExpiryDate(caPublicKeysDTO.getExpiryDate()),
						isStatusEq(caPublicKeysDTO.getStatus()),
						isStatusNotEq())
				.offset(offset)
				.limit(limit)
				.orderBy(orderByCAPublicKeysIdDsc())
				.list(QPGCaPublicKeys.pGCaPublicKeys.publicKeyName,
						QPGCaPublicKeys.pGCaPublicKeys.publicKeyIndex,
						QPGCaPublicKeys.pGCaPublicKeys.publicKeyModulus,
						QPGCaPublicKeys.pGCaPublicKeys.publicKeyExponent,
						QPGCaPublicKeys.pGCaPublicKeys.rId,
						QPGCaPublicKeys.pGCaPublicKeys.expiryDate,
						QPGCaPublicKeys.pGCaPublicKeys.status,
						QPGCaPublicKeys.pGCaPublicKeys.publicKeyId);
		if (!CollectionUtils.isEmpty(tuplePublicKeyList)) {
			for (Tuple tuple : tuplePublicKeyList) {
				CAPublicKeysDTO cAPublicKeysDTOs = new CAPublicKeysDTO();
				cAPublicKeysDTOs.setPublicKeyId(tuple
						.get(QPGCaPublicKeys.pGCaPublicKeys.publicKeyId));
				cAPublicKeysDTOs.setPublicKeyName(tuple
						.get(QPGCaPublicKeys.pGCaPublicKeys.publicKeyName));
				cAPublicKeysDTOs.setPublicKeyIndex(tuple
						.get(QPGCaPublicKeys.pGCaPublicKeys.publicKeyIndex));
				cAPublicKeysDTOs.setPublicKeyModulus(tuple
						.get(QPGCaPublicKeys.pGCaPublicKeys.publicKeyModulus));
				cAPublicKeysDTOs.setPublicKeyExponent(tuple
						.get(QPGCaPublicKeys.pGCaPublicKeys.publicKeyExponent));
				cAPublicKeysDTOs.setRid(tuple
						.get(QPGCaPublicKeys.pGCaPublicKeys.rId));
				cAPublicKeysDTOs.setExpiryDate(tuple
						.get(QPGCaPublicKeys.pGCaPublicKeys.expiryDate));
				cAPublicKeysDTOs.setStatus(tuple
						.get(QPGCaPublicKeys.pGCaPublicKeys.status));
				caPublicKeysRequestList.add(cAPublicKeysDTOs);
			}
		}
		return caPublicKeysRequestList;
	}

	private int getTotalNumberOfRecords(CAPublicKeysDTO caPublicKeysDTO) {
		JPAQuery query = new JPAQuery(entityManager);
		List<Long> tuplePublicKeyList = query
				.from(QPGCaPublicKeys.pGCaPublicKeys)
				.where(isCAPublicKeyName(caPublicKeysDTO.getPublicKeyName()),
						isStatusEq(caPublicKeysDTO.getStatus()),
						isExpiryDate(caPublicKeysDTO.getExpiryDate()),
						isPublicKeyIndex(caPublicKeysDTO.getPublicKeyIndex()),
						isRID(caPublicKeysDTO.getRid()),
						isPublicKeyModulus(caPublicKeysDTO
								.getPublicKeyModulus()),
						isStatusNotEq(),
						isPublicKeyExponent(caPublicKeysDTO
								.getPublicKeyExponent()))
				.list(QPGCaPublicKeys.pGCaPublicKeys.publicKeyId);

		return (StringUtil.isListNotNullNEmpty(tuplePublicKeyList) ? tuplePublicKeyList
				.size() : 0);
	}

	private BooleanExpression isCAPublicKeyName(String publicKeyName) {
		return (publicKeyName != null && !"".equals(publicKeyName)) ? QPGCaPublicKeys.pGCaPublicKeys.publicKeyName
				.like("%" + publicKeyName.replace("*", "") + "%") : null;
	}

	private BooleanExpression isRID(String rid) {
		return (null != rid && !"".equals(rid)) ? QPGCaPublicKeys.pGCaPublicKeys.rId
				.eq(rid) : null;
	}

	private BooleanExpression isPublicKeyIndex(String publicKeyIndex) {
		return (null != publicKeyIndex && !"".equals(publicKeyIndex)) ? QPGCaPublicKeys.pGCaPublicKeys.publicKeyIndex
				.eq(publicKeyIndex) : null;
	}

	private BooleanExpression isExpiryDate(String expiryDate) {
		return (null != expiryDate && !"".equals(expiryDate)) ? QPGCaPublicKeys.pGCaPublicKeys.expiryDate
				.like("%" + expiryDate.replace("*", "") + "%") : null;
	}

	private BooleanExpression isPublicKeyModulus(String publicKeyModulus) {
		return (null != publicKeyModulus && !"".equals(publicKeyModulus)) ? QPGCaPublicKeys.pGCaPublicKeys.publicKeyModulus
				.eq(publicKeyModulus) : null;
	}

	private BooleanExpression isPublicKeyExponent(Long publicKeyExponent) {
		return null != publicKeyExponent ? QPGCaPublicKeys.pGCaPublicKeys.publicKeyExponent
				.eq(publicKeyExponent) : null;
	}

	private BooleanExpression isStatusEq(String status) {
		return (status != null && !"".equals(status)) ? QPGCaPublicKeys.pGCaPublicKeys.status
				.eq(status) : null;
	}
	
	 private BooleanExpression isStatusNotEq(){
		  return(QPGCaPublicKeys.pGCaPublicKeys.status.ne(PGConstants.S_STATUS_DELETED));
	  }

	private OrderSpecifier<Long> orderByCAPublicKeysIdDsc() {
		return QPGCaPublicKeys.pGCaPublicKeys.publicKeyId.desc();
	}

	@Override
	public PGCaPublicKeys createCAPublicKeys(
			PGCaPublicKeys caPublicKeysDaoDetails) throws DataAccessException {

		return caPublickeysRepository.save(caPublicKeysDaoDetails);

	}

	@Override
	public PGCaPublicKeys updateCAPublicKeys(
			PGCaPublicKeys caPublicKeysDaoDetails) {

		logger.info("CAPublicKeysDaoImpl | updateCAPublicKeys | Entering");
		PGCaPublicKeys pgcaPublicKeys;
		Response response = new Response();
		pgcaPublicKeys = caPublickeysRepository
				.findByPublicKeyId(caPublicKeysDaoDetails.getPublicKeyId());
		if (null == pgcaPublicKeys) {
			response.setErrorCode(ActionErrorCode.ERROR_CODE_B5);
			response.setErrorMessage(ActionErrorCode.getInstance().getMessage(
					ActionErrorCode.ERROR_CODE_B5));
			return pgcaPublicKeys;
		}
		pgcaPublicKeys.setPublicKeyName(caPublicKeysDaoDetails
				.getPublicKeyName());
		pgcaPublicKeys.setrId(caPublicKeysDaoDetails.getrId());
		pgcaPublicKeys.setPublicKeyModulus(caPublicKeysDaoDetails
				.getPublicKeyModulus());
		pgcaPublicKeys.setPublicKeyExponent(caPublicKeysDaoDetails
				.getPublicKeyExponent());
		pgcaPublicKeys.setExpiryDate(caPublicKeysDaoDetails.getExpiryDate());
		pgcaPublicKeys.setPublicKeyIndex(caPublicKeysDaoDetails.getPublicKeyIndex());
		pgcaPublicKeys.setStatus(caPublicKeysDaoDetails.getStatus());

		logger.info("CAPublicKeysDaoImpl | updateCAPublicKeys | CAPublicKeysDao updated successfully");
		logger.info("CAPublicKeysDaoImpl | updateCAPublicKeys | Exiting");
		return caPublickeysRepository.save(pgcaPublicKeys);
	}

	@Override
	public PGCaPublicKeys caPublicKeysById(Long getCAPublicKeysId) {
		logger.info("CAPublicKeysDaoImpl | caPublicKeysById | Entering");
		return caPublickeysRepository.findByPublicKeyId(getCAPublicKeysId);
	}

	/**
	 * @param publicKeyName
	 * @return
	 */
	@Override
	public PGCaPublicKeys getpublicKeyName(String publicKeyName) {
		return caPublickeysRepository.findByPublicKeyName(publicKeyName);
	}

	/**
	 * @param caPublicKeys
	 * @return
	 * @throws DataAccessException
	 */
	@Override
	public PGCaPublicKeys saveCAPublicKey(PGCaPublicKeys pgCaPublicKey) throws DataAccessException {
		logger.info("CAPublicKeysDaoImpl | saveCAPublicKey | Entering");
		try {
			caPublickeysRepository.save(pgCaPublicKey);
		} catch (Exception e) {
			logger.error("CAPublicKeysDaoImpl | update | Exception " + e);
		}
		logger.info("CAPublicKeysDaoImpl | saveCAPublicKey | Exiting");
		return pgCaPublicKey;
	}

	/**
	 * @param getCAPublicKeysId
	 * @return
	 */
	@Override
	public CAPublicKeysDTO findByPublicKeyId(Long getCAPublicKeysId) throws DataAccessException {
		logger.info("CAPublicKeysDaoImpl | findByPublicKeyId | Entering");
		CAPublicKeysDTO cAPublicKeysDTO = new CAPublicKeysDTO();
		PGCaPublicKeys pgCaPublicKeys = caPublickeysRepository.findByPublicKeyId(getCAPublicKeysId);
		if (pgCaPublicKeys != null) {
			cAPublicKeysDTO.setPublicKeyName(pgCaPublicKeys.getPublicKeyName());
			if (pgCaPublicKeys.getStatus().equals(Constants.ZERO.toString())) {
				cAPublicKeysDTO.setStatus(PGConstants.S_STATUS_ACTIVE);
			} else if (pgCaPublicKeys.getStatus().equals(Constants.ONE.toString())) {
				cAPublicKeysDTO.setStatus(PGConstants.S_STATUS_PENDING);
			}
			cAPublicKeysDTO.setRid(pgCaPublicKeys.getrId());
			cAPublicKeysDTO.setPublicKeyModulus(pgCaPublicKeys.getPublicKeyModulus());
			cAPublicKeysDTO.setPublicKeyExponent(pgCaPublicKeys.getPublicKeyExponent());
			cAPublicKeysDTO.setExpiryDate(pgCaPublicKeys.getExpiryDate());
			cAPublicKeysDTO.setPublicKeyIndex(pgCaPublicKeys.getPublicKeyIndex());
			cAPublicKeysDTO.setCreatedBy(pgCaPublicKeys.getCreatedBy());
			cAPublicKeysDTO.setCreateDate(pgCaPublicKeys.getCreatedDate());
			
		}
		logger.info("CAPublicKeysDaoImpl | findByPublicKeyId | Exiting");
		return cAPublicKeysDTO;
	}
}
