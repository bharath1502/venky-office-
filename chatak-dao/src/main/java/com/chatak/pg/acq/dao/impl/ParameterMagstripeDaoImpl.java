package com.chatak.pg.acq.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.chatak.pg.acq.dao.ParameterMagstripeDao;
import com.chatak.pg.acq.dao.model.PGParameterMagstripe;
import com.chatak.pg.acq.dao.model.QPGParameterMagstripe;
import com.chatak.pg.acq.dao.repository.ParameterMagstripeRepository;
import com.chatak.pg.dao.util.StringUtil;
import com.chatak.pg.model.ParameterMagstripeDTO;
import com.chatak.pg.util.Constants;
import com.mysema.query.jpa.impl.JPAQuery;
import com.mysema.query.types.OrderSpecifier;
import com.mysema.query.types.expr.BooleanExpression;

@Repository("parameterMagstripeDao")
public class ParameterMagstripeDaoImpl implements ParameterMagstripeDao {

	@PersistenceContext
	private EntityManager entityManager;
	
	@Autowired
	ParameterMagstripeRepository parameterMagstripeRepository;
	
	@Override
	public PGParameterMagstripe createOrUpdateParameterMagstripe(PGParameterMagstripe parameterMagstripe){
		return parameterMagstripeRepository.save(parameterMagstripe);
	}

	@Override
	public List<PGParameterMagstripe> searchParameterMagstripe(ParameterMagstripeDTO parameterMagstripeTO) {
		Integer offset = 0;
		Integer limit = 0;
		Integer pageIndex = parameterMagstripeTO.getPageIndex();
		Integer pageSize = parameterMagstripeTO.getPageSize();
		Integer totalRecords;
		
		 if(parameterMagstripeTO.getPageIndex() == null || parameterMagstripeTO.getPageIndex() == 1){
	            totalRecords = getTotalNumberOfRecords(parameterMagstripeTO);
	            parameterMagstripeTO.setNoOfRecords(totalRecords);
	     }

		if (pageIndex == null && pageSize == null) {
			offset = 0;
			limit = Constants.DEFAULT_PAGE_SIZE;
		} else {
			offset = (pageIndex - 1) * pageSize;
			limit = pageSize;
		}
		JPAQuery query = new JPAQuery(entityManager);
		QPGParameterMagstripe qpgParameterMagstripe = QPGParameterMagstripe.pGParameterMagstripe;
		List<PGParameterMagstripe> list = query.from(qpgParameterMagstripe)
				                        .where(isMagstripeNameLike(parameterMagstripeTO.getMagstripeName()),
				                        		isPinLengthLike(parameterMagstripeTO.getPanLength()),
				                        		cardRangeCheck(parameterMagstripeTO.getCardRangeLow(), parameterMagstripeTO.getCardRangeHigh()),
								               isStatusEq(parameterMagstripeTO.getStatus()))
								       .offset(offset)
						               .limit(limit).orderBy(orderByMagstripeIdDesc())
						               .list(qpgParameterMagstripe);
		return list;
	}
	
	private int getTotalNumberOfRecords(ParameterMagstripeDTO parameterMagstripeTO) {
		JPAQuery query = new JPAQuery(entityManager);
		List<Long> list = query.from(QPGParameterMagstripe.pGParameterMagstripe)
				 .where(isMagstripeNameLike(parameterMagstripeTO.getMagstripeName()),
						 isPinLengthLike(parameterMagstripeTO.getPanLength()),
						 cardRangeCheck(parameterMagstripeTO.getCardRangeLow(), parameterMagstripeTO.getCardRangeHigh()),
			               isStatusEq(parameterMagstripeTO.getStatus()))
						.list(QPGParameterMagstripe.pGParameterMagstripe.magstripeId);
		return (StringUtil.isListNotNullNEmpty(list) ? list.size() : 0);
	}
	
	private OrderSpecifier<Long> orderByMagstripeIdDesc() {
        return QPGParameterMagstripe.pGParameterMagstripe.magstripeId.desc();
    }

	private BooleanExpression isMagstripeNameLike(String magstripeName) {
		return (magstripeName != null && !"".equals(magstripeName))? QPGParameterMagstripe.pGParameterMagstripe.magstripeName.toUpperCase().like("%" + magstripeName.toUpperCase().replace("*", "") + "%"): null;
	}
	
	private BooleanExpression isPinLengthLike(String pinLength) {
		return (pinLength != null && !"".equals(pinLength))? QPGParameterMagstripe.pGParameterMagstripe.panLength.contains(pinLength): null;
	}
	
	private BooleanExpression cardRangeCheck(Long cardRangeFrom, Long cardRangeTo) {
		return (cardRangeFrom != null && cardRangeTo != null) ? QPGParameterMagstripe.pGParameterMagstripe.cardRangeLow.loe(cardRangeFrom).and(QPGParameterMagstripe.pGParameterMagstripe.cardRangeHigh.goe(cardRangeTo)): null;
	}
    
	
	private BooleanExpression isStatusEq(String status) {
		return (status != null && !"".equals(status))? QPGParameterMagstripe.pGParameterMagstripe.status.eq(status): null;
	}
	@Override
	public PGParameterMagstripe findByMagstripeId(Long magstripeId) {
		return parameterMagstripeRepository.findOne(magstripeId);
	}

	@Override
	public List<PGParameterMagstripe> findByMagstripeName(String magstripeName) {
		return parameterMagstripeRepository.findByMagstripeName(magstripeName);
	}

}
