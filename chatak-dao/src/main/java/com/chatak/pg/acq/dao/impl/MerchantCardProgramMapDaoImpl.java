/**
 * 
 */
package com.chatak.pg.acq.dao.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.chatak.pg.acq.dao.MerchantCardProgramMapDao;
import com.chatak.pg.acq.dao.model.PGMerchantCardProgramMap;
import com.chatak.pg.acq.dao.model.QCardProgram;
import com.chatak.pg.acq.dao.model.QPGMerchantCardProgramMap;
import com.chatak.pg.acq.dao.model.QProgramManager;
import com.chatak.pg.acq.dao.repository.MerchantCardProgramMapRepository;
import com.chatak.pg.user.bean.CardProgramRequest;
import com.chatak.pg.user.bean.MerchantResponse;
import com.mysema.query.Tuple;
import com.mysema.query.jpa.impl.JPAQuery;

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
	public MerchantResponse findByMerchantId(Long merchantId) {
		MerchantResponse response = new MerchantResponse();
		JPAQuery query = new JPAQuery(entityManager);
		List<Tuple> tupleList = query
				.from(QProgramManager.programManager, QCardProgram.cardProgram,
						QPGMerchantCardProgramMap.pGMerchantCardProgramMap)
				.where(QPGMerchantCardProgramMap.pGMerchantCardProgramMap.merchantId.eq(merchantId)
						.and(QPGMerchantCardProgramMap.pGMerchantCardProgramMap.entityId
								.eq(QProgramManager.programManager.id)).and(QPGMerchantCardProgramMap.pGMerchantCardProgramMap.cardProgramId.eq(QCardProgram.cardProgram.cardProgramId)))
				.distinct().list(QProgramManager.programManager.id, QProgramManager.programManager.programManagerName,
						QCardProgram.cardProgram.cardProgramId, QCardProgram.cardProgram.cardProgramName,
						QCardProgram.cardProgram.iin, QCardProgram.cardProgram.iinExt,
						QCardProgram.cardProgram.partnerName, QCardProgram.cardProgram.partnerIINCode,QProgramManager.programManager.accountCurrency);
		List<CardProgramRequest> requests = new ArrayList<>();
		CardProgramRequest cardProgramRequest = null;
		for (Tuple tuple : tupleList) {
			cardProgramRequest = new CardProgramRequest();
			cardProgramRequest.setProgramManagerId(tuple.get(QProgramManager.programManager.id));
			cardProgramRequest.setEntityName(tuple.get(QProgramManager.programManager.programManagerName));
			cardProgramRequest.setCardProgramId(tuple.get(QCardProgram.cardProgram.cardProgramId));
			cardProgramRequest.setPartnerName(tuple.get(QCardProgram.cardProgram.partnerName));
			cardProgramRequest.setPartnerCode(tuple.get(QCardProgram.cardProgram.partnerIINCode));
			cardProgramRequest.setCardProgramName(tuple.get(QCardProgram.cardProgram.cardProgramName));
			cardProgramRequest.setIin(tuple.get(QCardProgram.cardProgram.iin));
			cardProgramRequest.setIinExt((tuple.get(QCardProgram.cardProgram.iinExt)));
			cardProgramRequest.setCurrency(tuple.get(QProgramManager.programManager.accountCurrency));
			requests.add(cardProgramRequest);
		}
		response.setCardProgramRequests(requests);
		return response;
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
