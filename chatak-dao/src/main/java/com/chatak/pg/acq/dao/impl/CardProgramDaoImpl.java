/**
 * 
 */
package com.chatak.pg.acq.dao.impl;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.chatak.pg.acq.dao.CardProgramDao;
import com.chatak.pg.acq.dao.model.CardProgram;
import com.chatak.pg.acq.dao.model.PmCardProgamMapping;
import com.chatak.pg.acq.dao.model.QCardProgram;
import com.chatak.pg.acq.dao.model.QPmCardProgamMapping;
import com.chatak.pg.acq.dao.model.QProgramManager;
import com.chatak.pg.acq.dao.repository.CardProgramRepository;
import com.chatak.pg.acq.dao.repository.PmCardProgramMappingRepository;
import com.chatak.pg.dao.util.StringUtil;
import com.chatak.pg.user.bean.CardProgramRequest;
import com.mysema.query.Tuple;
import com.mysema.query.jpa.impl.JPAQuery;

/**
 * @Author: Girmiti Software
 * @Date: May 10, 2018
 * @Time: 8:31:23 PM
 * @Version: 1.0
 * @Comments: 
 *
 */
@Repository
public class CardProgramDaoImpl implements CardProgramDao {
	
	
	@Autowired
	private CardProgramRepository cardProgramRepository;
	
	@PersistenceContext
	private EntityManager entityManager;
	  
	@Autowired
	private PmCardProgramMappingRepository cardProgamMappingRepository;

	@Override
    public CardProgram createCardProgram(CardProgram cardProgramRequest)
            throws DataAccessException {
        return cardProgramRepository.save(cardProgramRequest);
    }
	
	@Override
	@Transactional
    public PmCardProgamMapping createCardProgramMapping(PmCardProgamMapping cardProgramRequest)
            throws DataAccessException {
        return cardProgamMappingRepository.save(cardProgramRequest);
    }

	/**
	 * @param cardProgramId
	 * @return
	 */
	@Override
	public CardProgram findByCardProgramId(Long cardProgramId) {
		return cardProgramRepository.findByCardProgramId(cardProgramId);
	}
	/**
	 * @param programManagerId
	 * @return
	 */
	@Override
	public List<CardProgramRequest> findCardProgramByPmId(Long programManagerId) {
		List<CardProgramRequest> cardProgramRequestList = new ArrayList<>();
		JPAQuery query = new JPAQuery(entityManager);
		List<Tuple> list = query
				.from(QProgramManager.programManager, QPmCardProgamMapping.pmCardProgamMapping, QCardProgram.cardProgram)
				.where(QProgramManager.programManager.id.eq(programManagerId),
						QPmCardProgamMapping.pmCardProgamMapping.programManagerId.eq(QProgramManager.programManager.id),
						QCardProgram.cardProgram.cardProgramId.eq(QPmCardProgamMapping.pmCardProgamMapping.cardProgramId))
				.distinct().list(QCardProgram.cardProgram.cardProgramId, QCardProgram.cardProgram.cardProgramName);
		for (Tuple tuple : list) {
			CardProgramRequest cardProgramRequest = new CardProgramRequest();
			cardProgramRequest.setCardProgramId(tuple.get(QCardProgram.cardProgram.cardProgramId));
			cardProgramRequest.setCardProgramName(tuple.get(QCardProgram.cardProgram.cardProgramName));
			cardProgramRequestList.add(cardProgramRequest);
		}
		return cardProgramRequestList;
	}
	
	/**
	 * @param bankId
	 * @return
	 */
	@Override
	public List<CardProgram> findByCurrency(String currency) {
		return cardProgramRepository.findByCurrency(currency);
	}
	
	@Override
	public CardProgram findByIssuanceCardProgramId(Long issuanceCardProgramId){
	  return cardProgramRepository.findByIssuanceCradProgramId(issuanceCardProgramId);
	}

	@Override
	public Long findCardProgramByIIN(String iin, String partnerIINCode, String iinExt) {
		return cardProgramRepository.findCardProgramIdByIinAndPartnerIINCodeAndIinExt(iin, partnerIINCode, iinExt);
	}
	
	@Override
	public CardProgram findCardProgramIdByIinAndPartnerIINCodeAndIinExt(String iin, String partnerIINCode, String iinExt) {
		return cardProgramRepository.findByIinAndPartnerIINCodeAndIinExt(iin, partnerIINCode, iinExt);
	}
	
	public List<CardProgramRequest> getCardProgramListForFeeProgram() {
	  List<CardProgramRequest> cardProgramRequestList = new ArrayList<>();
	  StringBuilder query = new StringBuilder("select cp.id, cp.CARD_PROGRAM_NAME from PG_CARD_PROGRAM as cp where cp.id not in ")
	                        .append("(SELECT ifnull(CARD_PROGRAM_ID,0) FROM PG_FEE_PROGRAM )"); 
	  
	  Query qry = entityManager.createNativeQuery(query.toString());
	  List<Object> obj = qry.getResultList();
	  
	  if(StringUtil.isListNotNullNEmpty(obj)){
	    setCardProgramRequest(obj, cardProgramRequestList);
	  }
      return cardProgramRequestList;
  }
	private void setCardProgramRequest(List<Object> obj,List<CardProgramRequest> cardProgramRequestList){
	  Iterator<Object> itr = obj.iterator();
      while(itr.hasNext()){
        CardProgramRequest cardProgramRequest = new CardProgramRequest();
        Object[] cpList = (Object[])itr.next();
        cardProgramRequest.setCardProgramId(cpList!=null ? ((BigInteger)cpList[0]).longValue() : null);
        cardProgramRequest.setCardProgramName(cpList!=null ? (String)cpList[1] : null);
        cardProgramRequestList.add(cardProgramRequest);
      }
	}
	
	@Override
	public List<CardProgramRequest> getUnselectedCpByPm(Long programManagerId){
	  List<CardProgramRequest> cardProgramRequestList = new ArrayList<>();
      StringBuilder query = new StringBuilder("SELECT cp.ID,cp.CARD_PROGRAM_NAME FROM PG_CARD_PROGRAM as cp ")
                            .append("where cp.ACQ_PM_ID=:pmId ")
                            .append("and cp.id not in (select CARD_PROGRAM_ID from PG_PM_CARD_PROGRAM_MAPPING where CARD_PROGRAM_ID is not null)");
      
      Query qry = entityManager.createNativeQuery(query.toString());
      qry.setParameter("pmId", programManagerId);
      List<Object> obj = qry.getResultList();
      if(StringUtil.isListNotNullNEmpty(obj)){
        setCardProgramRequest(obj, cardProgramRequestList);
      }
      return cardProgramRequestList;
	}
	
	@Override
    public List<CardProgramRequest> getUnselectedCpForIndependentPm(Long programManagerId, String currency){
      List<CardProgramRequest> cardProgramRequestList = new ArrayList<>();
      StringBuilder query = new StringBuilder("SELECT cp.ID,cp.CARD_PROGRAM_NAME FROM PG_CARD_PROGRAM as cp ")
                            .append(" where cp.CURRENCY=:currency ")
                            .append(" and cp.id not in (select pmcp.CARD_PROGRAM_ID from PG_PM_CARD_PROGRAM_MAPPING as pmcp")
                            .append(" where pmcp.CARD_PROGRAM_ID is not null and pmcp.PM_ID=:pmId)");
      
      Query qry = entityManager.createNativeQuery(query.toString());
      qry.setParameter("pmId", programManagerId);
      qry.setParameter("currency", currency);
      List<Object> obj = qry.getResultList();
      if(StringUtil.isListNotNullNEmpty(obj)){
        setCardProgramRequest(obj, cardProgramRequestList);
      }
      return cardProgramRequestList;
    }
 }
