package com.chatak.pg.acq.dao.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import com.chatak.pg.acq.dao.CommissionDao;
import com.chatak.pg.acq.dao.model.PGCommission;
import com.chatak.pg.acq.dao.model.PGOtherCommission;
import com.chatak.pg.acq.dao.model.QPGCommission;
import com.chatak.pg.acq.dao.repository.CommissionProgramRepository;
import com.chatak.pg.acq.dao.repository.OtherCommissionRepository;
import com.chatak.pg.dao.util.StringUtil;
import com.chatak.pg.model.CommissionDTO;
import com.chatak.pg.util.Constants;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;

@Repository("commissionDao")
public class CommissionProgramDaoImpl implements CommissionDao
{

	@Autowired
	private CommissionProgramRepository commissionProgramRepository;
	
	@Autowired
	private OtherCommissionRepository otherCommissionRepository;
	
	@PersistenceContext
	private EntityManager entityManager;
	
	@Override
	public PGCommission createCommissionProgram(PGCommission commissionDaoDetails) throws DataAccessException {
		commissionProgramRepository.save(commissionDaoDetails);
		
		return commissionDaoDetails;
	}

	
	@Override
	public List<PGCommission> searchCommissionProgram(CommissionDTO commissionDTO) {
		List<PGCommission> commissionDTORequestList = new ArrayList<PGCommission>();
		int offset = 0;
        int limit = 0;
        Integer totalRecords;
        
        if(commissionDTO.getPageIndex() == null || commissionDTO.getPageIndex() == 1){
            totalRecords = getTotalNumberOfRecords(commissionDTO);
            commissionDTO.setNoOfRecords(totalRecords);
        }      
        if(commissionDTO.getPageIndex() == null && commissionDTO.getPageSize() == null){
            offset = 0;
            limit = Constants.DEFAULT_PAGE_SIZE;
        } else {
            offset = (commissionDTO.getPageIndex()-1) * commissionDTO.getPageSize();
            limit = commissionDTO.getPageSize();
        }
		JPAQuery<PGCommission> query = new JPAQuery<PGCommission>(entityManager);
		 QPGCommission qpgcommission = QPGCommission.pGCommission;
			List<PGCommission> tupleCardList = query
					.from(qpgcommission)
					.select(qpgcommission)
					.where(isCommissionProgramName(commissionDTO.getCommissionName()),
							isStatusEq(commissionDTO.getStatus()))
							.offset(offset)
							.limit(limit)
							.orderBy(orderByCreatedDateDesc())
							.fetch();
			for (PGCommission tuple : tupleCardList) {
				PGCommission commissionDTO1 = new PGCommission();
				commissionDTO1.setCommissionName(tuple.getCommissionName());
				commissionDTO1.setStatus(tuple.getStatus());
				commissionDTO1.setCommissionProgramId(tuple.getCommissionProgramId());
				commissionDTORequestList.add(commissionDTO1);
			}
			return commissionDTORequestList;
	}
	
	private OrderSpecifier<Timestamp> orderByCreatedDateDesc() {
	    return QPGCommission.pGCommission.createdDate.desc();
	}

	private int getTotalNumberOfRecords(CommissionDTO commissionDTO) {
		JPAQuery<Long> query = new JPAQuery<Long>(entityManager);
        QPGCommission qpgcommission = QPGCommission.pGCommission;
		List<Long> tupleCardList = query
				.from(qpgcommission)
				.select(qpgcommission.commissionProgramId)
				.where(isCommissionProgramName(commissionDTO.getCommissionName()),
						isStatusEq(commissionDTO.getStatus()))
						.fetch();
		return (StringUtil.isListNotNullNEmpty(tupleCardList) ? tupleCardList.size() : 0);
	}
		
		
	private BooleanExpression isCommissionProgramName(String commissionName) {
		return (commissionName != null && !"".equals(commissionName))
				?  QPGCommission.pGCommission.commissionName.toUpperCase().like(
						"%" + commissionName.toUpperCase().replace("*", "") + "%")
			
						: null;
	}
	private BooleanExpression isStatusEq(String status) {
		return (status != null && !"".equals(status))
				?  QPGCommission.pGCommission.status.eq(status)
				: null;
	}

	@Override
	public List<PGCommission> getByCommProgramId(Long commissionProgramId) throws DataAccessException {
		return commissionProgramRepository.findByCommissionProgramId(commissionProgramId);
	}

	@Override
	public List<PGCommission> findByCommissionName(String commissionName) {
		return commissionProgramRepository.findByCommissionName(commissionName);
	}

	@Override
	public List<PGOtherCommission> getOtherCommissionByCommissionProgramId(Long commissionProgramId) {
		return otherCommissionRepository.findByCommissionProgramId(commissionProgramId);
	}

	@Override
	public void removeOthCommission(List<PGOtherCommission> othCommission) {
		otherCommissionRepository.deleteAll(othCommission);
	}
}
	
	

