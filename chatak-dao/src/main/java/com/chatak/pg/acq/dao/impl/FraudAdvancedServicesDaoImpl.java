package com.chatak.pg.acq.dao.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import com.chatak.pg.acq.dao.FraudAdvancedServicesDao;
import com.chatak.pg.acq.dao.model.PGMerchant;
import com.chatak.pg.acq.dao.model.PGMerchantAdvancedFraud;
import com.chatak.pg.acq.dao.model.QPGMerchantAdvancedFraud;
import com.chatak.pg.acq.dao.repository.AdvancedFraudRepository;
import com.chatak.pg.acq.dao.repository.MerchantRepository;
import com.chatak.pg.dao.util.StringUtil;
import com.chatak.pg.model.AdvancedFraudDTO;
import com.chatak.pg.util.Constants;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.jpa.impl.JPAQuery;

/**
 * @Author: Girmiti Software
 * @Date: Aug 17, 2016
 * @Time: 6:11:52 PM
 * @Version: 1.0
 * @Comments: 
 */

@Repository("fraudAdvancedServicesDao")
public class FraudAdvancedServicesDaoImpl implements FraudAdvancedServicesDao{
	
	@PersistenceContext
	private EntityManager entityManager;
	
	@Autowired
	AdvancedFraudRepository advancedFraudRepository;
	
	@Autowired
	MerchantRepository merchantRepository;

	/**
	 * @param id
	 * @return
	 * @throws DataAccessException
	 */
	@Override
	public PGMerchant findById(Long id) throws DataAccessException {
		return merchantRepository.findById(id).orElse(null);
	}

	/**
	 * @param PGMerchantAdvancedFraud
	 * @return
	 * @throws DataAccessException
	 */
	@Override
	public PGMerchantAdvancedFraud saveOrUpdatePGMerchantAdvancedFraud(PGMerchantAdvancedFraud pGMerchantAdvancedFraud) throws DataAccessException {
		return advancedFraudRepository.save(pGMerchantAdvancedFraud);
	}

	/**
	 * @param advancedFraudDTO
	 * @return
	 * @throws DataAccessException
	 * @throws Exception
	 */
	@Override
	public List<AdvancedFraudDTO> searchAdvancedFraud(AdvancedFraudDTO advancedFraudDTO) {
		Integer offset = 0;
		Integer limit = 0;
		Integer pageIndex = advancedFraudDTO.getPageIndex();
		Integer pageSize = advancedFraudDTO.getPageSize();
		Integer totalRecords;
		List<AdvancedFraudDTO> advancedFraudInfoList = new ArrayList<AdvancedFraudDTO>();
		AdvancedFraudDTO advancedFraudInfoDTO = null;

		if(advancedFraudDTO.getPageIndex() == null || advancedFraudDTO.getPageIndex() == 1){
			totalRecords = getAdvancedFraudTotalRecords(advancedFraudDTO);
			advancedFraudDTO.setNoOfRecords(totalRecords);
		}

		if (pageIndex == null && pageSize == null) {
			offset = 0;
			limit = Constants.DEFAULT_PAGE_SIZE;
		} else {
			offset = (pageIndex - 1) * pageSize;
			limit = pageSize;
		}
		
		JPAQuery<Tuple> query = new JPAQuery<Tuple>(entityManager);
		List<Tuple> dataList = query.from(QPGMerchantAdvancedFraud.pGMerchantAdvancedFraud)
				.select(QPGMerchantAdvancedFraud.pGMerchantAdvancedFraud.id,
						QPGMerchantAdvancedFraud.pGMerchantAdvancedFraud.filterType,
						QPGMerchantAdvancedFraud.pGMerchantAdvancedFraud.filterOn,
						QPGMerchantAdvancedFraud.pGMerchantAdvancedFraud.duration,
						QPGMerchantAdvancedFraud.pGMerchantAdvancedFraud.transactionLimit,
						QPGMerchantAdvancedFraud.pGMerchantAdvancedFraud.maxLimit,
						QPGMerchantAdvancedFraud.pGMerchantAdvancedFraud.merchantCode,
						QPGMerchantAdvancedFraud.pGMerchantAdvancedFraud.action)
				.where(QPGMerchantAdvancedFraud.pGMerchantAdvancedFraud.createdBy.eq(advancedFraudDTO.getCreatedBy()))
								.offset(offset)
								.limit(limit).orderBy(orderByIdDesc()).fetch();
		for (Tuple tuple : dataList) {
			advancedFraudInfoDTO = new AdvancedFraudDTO();
			advancedFraudInfoDTO.setId(tuple.get(QPGMerchantAdvancedFraud.pGMerchantAdvancedFraud.id));
			advancedFraudInfoDTO.setFilterType(tuple.get(QPGMerchantAdvancedFraud.pGMerchantAdvancedFraud.filterType));
			advancedFraudInfoDTO.setFilterOn(tuple.get(QPGMerchantAdvancedFraud.pGMerchantAdvancedFraud.filterOn));
			advancedFraudInfoDTO.setDuration(tuple.get(QPGMerchantAdvancedFraud.pGMerchantAdvancedFraud.duration));
			advancedFraudInfoDTO.setTransactionLimit(tuple.get(QPGMerchantAdvancedFraud.pGMerchantAdvancedFraud.transactionLimit));
			advancedFraudInfoDTO.setMaxLimit(tuple.get(QPGMerchantAdvancedFraud.pGMerchantAdvancedFraud.maxLimit));
			advancedFraudInfoDTO.setMerchantCode(tuple.get(QPGMerchantAdvancedFraud.pGMerchantAdvancedFraud.merchantCode));
			advancedFraudInfoDTO.setAction(tuple.get(QPGMerchantAdvancedFraud.pGMerchantAdvancedFraud.action));
			advancedFraudInfoList.add(advancedFraudInfoDTO);
		}
		return advancedFraudInfoList;
	}
	
	/**
	 * @param advancedFraudDTO
	 * @return
	 */
	private Integer getAdvancedFraudTotalRecords(AdvancedFraudDTO advancedFraudDTO){
		JPAQuery<Long> query = new JPAQuery<Long>(entityManager);
		List<Long> dataList = query.from(QPGMerchantAdvancedFraud.pGMerchantAdvancedFraud)
				.select(QPGMerchantAdvancedFraud.pGMerchantAdvancedFraud.id)
				.where(QPGMerchantAdvancedFraud.pGMerchantAdvancedFraud.createdBy.eq(advancedFraudDTO.getCreatedBy())).orderBy(orderByIdDesc()).fetch();
		return (StringUtil.isListNotNullNEmpty(dataList) ? dataList.size() : 0);
	}
	
	private OrderSpecifier<Long> orderByIdDesc() {
		return QPGMerchantAdvancedFraud.pGMerchantAdvancedFraud.id.desc();
	}

	/**
	 * @param id
	 * @param merchantCode
	 * @return
	 * @throws DataAccessException
	 */
	@Override
	public PGMerchantAdvancedFraud findByIdAndMerchantCode(Long id,String merchantCode) throws DataAccessException {
		return advancedFraudRepository.findByIdAndMerchantCode(id,merchantCode);
	}

	/**
	 * @param id
	 */
	@Override
	public void deleteAdvancedFraud(Long id) throws DataAccessException {
		advancedFraudRepository.deleteById(id);
	}
	
}