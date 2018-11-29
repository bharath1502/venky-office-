/**
 * 
 */
package com.chatak.pg.acq.dao.impl;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.chatak.pg.acq.dao.MerchantCardProgramMapDao;
import com.chatak.pg.acq.dao.model.PGMerchantCardProgramMap;
import com.chatak.pg.acq.dao.repository.MerchantCardProgramMapRepository;
import com.chatak.pg.dao.util.StringUtil;
import com.chatak.pg.user.bean.CardProgramRequest;
import com.chatak.pg.user.bean.MerchantResponse;

/**
 * @Author: Girmiti Software
 * @Date: May 10, 2018
 * @Time: 3:56:59 PM
 * @Version: 1.0
 * @Comments: 
 *
 */
@Repository("merchantCardProgramMapDao")
public class MerchantCardProgramMapDaoImpl implements MerchantCardProgramMapDao {

	@PersistenceContext
	private EntityManager entityManager;
	
	@Autowired
	MerchantCardProgramMapRepository merchantCardProgramMapRepository;

	@Override
	public MerchantResponse findCardProgramByMerchantId(Long merchantId) {
		MerchantResponse response = new MerchantResponse();
		List<CardProgramRequest> allCardProgramList = new ArrayList<>(0);
        List<CardProgramRequest> selectedCardProgramList = new ArrayList<>(0);

        StringBuilder allCardProgramQuery = new StringBuilder("select subqry.ID,subqry.CARD_PROGRAM_NAME,subqry.IIN,")
        .append(" subqry.IIN_EXT,subqry.ISSUANCE_PARTNER_NAME, subqry.CURRENCY,")
        .append(" subqry.PROGRAM_MANAGER_NAME,subqry.IIN_PARTNER_EXT, subqry.PM_ID ")
        .append(" from (select distinct cp.ID,cp.CARD_PROGRAM_NAME,cp.IIN,")
        .append(" cp.IIN_EXT,cp.ISSUANCE_PARTNER_NAME,cp.CURRENCY,pm.PROGRAM_MANAGER_NAME,cp.IIN_PARTNER_EXT,pmcp.PM_ID")
        .append(" from PG_MERCHANT_ENTITY_MAPPING as merchant_entity")
        .append(" left join PG_PM_CARD_PROGRAM_MAPPING as pmcp on merchant_entity.ENTITY_ID =pmcp.PM_ID")
        .append(" right join PG_PROGRAM_MANAGER as pm on merchant_entity.ENTITY_ID = pm.ID")
        .append(" left join PG_CARD_PROGRAM as cp on pmcp.CARD_PROGRAM_ID = cp.ID")
        .append(" where merchant_entity.MERCHANT_ID = :merchantId )as subqry");
                              
        Query qry = entityManager.createNativeQuery(allCardProgramQuery.toString());
        qry.setParameter("merchantId", merchantId);
        List<Object> cardProgramResponse = qry.getResultList();
        if(StringUtil.isListNotNullNEmpty(cardProgramResponse)){
            Iterator<Object> itr = cardProgramResponse.iterator();
            setCardPrograms(allCardProgramList, itr);         
        }
        
        //selected merchant-pm cp's
        StringBuilder selectedCardProgramQuery = new StringBuilder("select subqry.ID,subqry.CARD_PROGRAM_NAME,subqry.IIN,")
        .append(" subqry.IIN_EXT,subqry.ISSUANCE_PARTNER_NAME, subqry.CURRENCY,")
        .append(" subqry.PROGRAM_MANAGER_NAME,subqry.IIN_PARTNER_EXT, subqry.ENTITY_ID ")
        .append(" from (select distinct cp.ID,cp.CARD_PROGRAM_NAME,cp.IIN,")
        .append(" cp.IIN_EXT,cp.ISSUANCE_PARTNER_NAME,cp.CURRENCY,pm.PROGRAM_MANAGER_NAME,cp.IIN_PARTNER_EXT,merchantCpMap.ENTITY_ID")
        .append(" from PG_MERCHANT_CARD_PROGRAM_MAPPING as merchantCpMap")
        .append(" right join PG_PROGRAM_MANAGER as pm on merchantCpMap.ENTITY_ID = pm.ID")
        .append(" left join PG_CARD_PROGRAM as cp on merchantCpMap.CARD_PROGRAM_ID = cp.ID")
        .append(" where merchantCpMap.MERCHANT_ID = :merchantId )as subqry");
        
        Query qry1 = entityManager.createNativeQuery(selectedCardProgramQuery.toString());
        qry1.setParameter("merchantId", merchantId);
        List<Object> selectedCardProgramResponse = qry1.getResultList();
        if(StringUtil.isListNotNullNEmpty(selectedCardProgramResponse)){
            Iterator<Object> itr = selectedCardProgramResponse.iterator();
            setCardPrograms(selectedCardProgramList, itr);            
        }
        
        Map<String, CardProgramRequest> masterCpMap = new HashMap<>();
        for(CardProgramRequest masterCp : allCardProgramList){
          masterCpMap.put(getKey(masterCp.getProgramManagerId(),masterCp), masterCp);
        }
        for(CardProgramRequest selectedCp : selectedCardProgramList){
          if(masterCpMap.containsKey(getKey(selectedCp.getProgramManagerId(),selectedCp))){
            CardProgramRequest cardProgram = masterCpMap.get(getKey(selectedCp.getProgramManagerId(),selectedCp));
            if(selectedCp.getCardProgramId().equals(cardProgram.getCardProgramId()) 
                // Compare the ambiguity ID
                && (selectedCp.getProgramManagerId().equals(cardProgram.getProgramManagerId()))) {
                // Set the card program as selected in the master card list
                cardProgram.setSelected(true);
            }
          }
        }
        allCardProgramList = new ArrayList<>(masterCpMap.values());
		response.setCardProgramRequests(allCardProgramList);
		return response;
	}
	
	private String getKey(Long entityId, CardProgramRequest cardProgramRequest){
      return entityId+"_"+cardProgramRequest.getCardProgramId();
    }
	
	private void setCardPrograms(List<CardProgramRequest> cardProgramList,Iterator<Object> itr) {
      CardProgramRequest cardProgramRequest;
      while(itr.hasNext()){
          Object[] objs = (Object[]) itr.next();
          cardProgramRequest= new CardProgramRequest();
          cardProgramRequest.setCardProgramId(StringUtil.isNull(objs[0]) ? null : ((BigInteger) objs[0]).longValue());
          cardProgramRequest.setCardProgramName(requestCardProgramName(objs));
          cardProgramRequest.setIin(requestIin(objs));
          cardProgramRequest.setIinExt(requestIinExt(objs));
          cardProgramRequest.setPartnerName(StringUtil.isNull(objs[4]) ? null : ((String) objs[4]));
          cardProgramRequest.setCurrency(StringUtil.isNull(objs[5]) ? null : ((String) objs[5]));
          cardProgramRequest.setEntityName(StringUtil.isNull(objs[6]) ? null : ((String) objs[6]));
          cardProgramRequest.setPartnerCode(StringUtil.isNull(objs[7]) ? null : ((String)objs[7]));
          cardProgramRequest.setProgramManagerId(StringUtil.isNull(objs[8]) ? null : ((BigInteger) objs[8]).longValue());
          cardProgramList.add(cardProgramRequest);
      }
  }
	
  private String requestIinExt(Object[] objs) {
    return StringUtil.isNull(objs[3]) ? null : ((String) objs[3]);
  }

  private String requestIin(Object[] objs) {
    return StringUtil.isNull(objs[2]) ? null : ((String) objs[2]);
  }
  
  private String requestCardProgramName(Object[] objs) {
    return StringUtil.isNull(objs[1]) ? null : (String) objs[1];
  }

	/**
	 * @param merchantId
	 * @param cardProgramId
	 * @return
	 */
	@Override
	public PGMerchantCardProgramMap findByMerchantIdAndCardProgramId(Long merchantId, Long cardProgramId) {
		return merchantCardProgramMapRepository.findByMerchantIdAndCardProgramId(merchantId, cardProgramId);
	}
 }
