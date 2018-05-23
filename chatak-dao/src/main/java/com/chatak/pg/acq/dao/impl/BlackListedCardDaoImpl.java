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
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import com.chatak.pg.acq.dao.BlackListedCardDao;
import com.chatak.pg.acq.dao.model.PGBlackListedCard;
import com.chatak.pg.acq.dao.model.QPGBlackListedCard;
import com.chatak.pg.acq.dao.repository.BlackListedCardRepository;
import com.chatak.pg.bean.Response;
import com.chatak.pg.constants.ActionErrorCode;
import com.chatak.pg.constants.PGConstants;
import com.chatak.pg.user.bean.BlackListedCardRequest;
import com.chatak.pg.user.bean.BlackListedCardResponse;
import com.chatak.pg.util.Constants;
import com.chatak.pg.util.DateUtil;
import com.chatak.pg.util.StringUtils;
import com.mysema.query.Tuple;
import com.mysema.query.jpa.impl.JPAQuery;
import com.mysema.query.types.OrderSpecifier;
import com.mysema.query.types.expr.BooleanExpression;

/**
 * @Author: Girmiti Software
 * @Date: Aug 5, 2016
 * @Time: 3:28:52 PM
 * @Version: 1.0
 * @Comments: 
 *
 */
@Repository("blackListedCardDao")
public class BlackListedCardDaoImpl implements BlackListedCardDao {

	/**
	 * @param addBlackListedCardRequest
	 * @param userid
	 * @return
	 */
	@PersistenceContext
	EntityManager entityManager;
	
	@Autowired
	MessageSource messageSource;
	
	@Autowired
	BlackListedCardRepository blackListedCardRepository;
	
	private static Logger log = Logger.getLogger(BlackListedCardDaoImpl.class);

	@Override
	public BlackListedCardResponse addBlackListedCardInfo(BlackListedCardRequest addBlackListedCardRequest) {
		log.info("BlackListedCardDaoImpl | addBlackListedCardInfo | Entering");
		BlackListedCardResponse addBlackListedCardResponse = new BlackListedCardResponse();
		try {
			PGBlackListedCard pgBlackListedCard =  blackListedCardRepository.findByCardNumber(addBlackListedCardRequest.getCardNumber());
			if (null != pgBlackListedCard) {
				addBlackListedCardResponse.setErrorCode(ActionErrorCode.ERROR_CODE_Z5);
				addBlackListedCardResponse.setErrorMessage(PGConstants.DUPLICATE_BLACK_LISTED_CARD);
				return addBlackListedCardResponse;
			}
			PGBlackListedCard pgBlackListedCardDetails = new PGBlackListedCard();
			pgBlackListedCardDetails.setCreatedDate(DateUtil.getCurrentTimestamp());
			pgBlackListedCardDetails.setStatus(addBlackListedCardRequest.getStatus());
			pgBlackListedCardDetails.setCardNumber(addBlackListedCardRequest.getCardNumber());
			blackListedCardRepository.save(pgBlackListedCardDetails);
			addBlackListedCardResponse.setErrorCode(ActionErrorCode.ERROR_CODE_00);
		}catch(Exception e){
			log.error("BlackListedCardDaoImpl | addBlackListedCardInfo | Exception" + e);
			addBlackListedCardResponse.setErrorCode(ActionErrorCode.ERROR_CODE_Z5);
			addBlackListedCardResponse.setErrorMessage(ActionErrorCode.getInstance().getMessage(ActionErrorCode.ERROR_CODE_Z5));
		}
		log.info("BlackListedCardDaoImpl | addBlackListedCardInfo | Exiting");
		return addBlackListedCardResponse;
	}

	
	@Override
	public BlackListedCardResponse updateBlackListedCardInformation(BlackListedCardRequest updateBlackListedCardRequest, String userid) {
		log.info("BlackListedCardDaoImpl | updateBlackListedCardInformation | Entering");
		BlackListedCardResponse updateBlackListedCardResponse = new BlackListedCardResponse();
		PGBlackListedCard pgBlackListedCard = blackListedCardRepository.findById(updateBlackListedCardRequest.getId());
		try{
		if(pgBlackListedCard != null)
		{
			pgBlackListedCard.setId(updateBlackListedCardRequest.getId());
			pgBlackListedCard.setCardNumber(updateBlackListedCardRequest.getCardNumber());
			blackListedCardRepository.save(pgBlackListedCard);
			updateBlackListedCardResponse.setErrorCode(ActionErrorCode.ERROR_CODE_00);
		}else{
			updateBlackListedCardResponse.setErrorCode(ActionErrorCode.ERROR_CODE_01);
			}
		}
		catch(Exception e){
			log.error("BlackListedCardDaoImpl | updateBlackListedCardInformation | Exception " + e);
		}
		log.info("BlackListedCardDaoImpl | updateBlackListedCardInformation | Exiting");
		return updateBlackListedCardResponse;
	}
	/**
	 * @param getBlackListedCardId
	 * @return
	 */
	@Override
	public PGBlackListedCard getBlackListedCardInfoById(Long getBlackListedCardId) {
		PGBlackListedCard pgBlackListedCard = blackListedCardRepository.findById(getBlackListedCardId);
		return pgBlackListedCard;
	}
	
	@Override
	public List<BlackListedCardRequest> searchBlackListedCardInformation(BlackListedCardRequest searchBlackListedCardRequest) {
		log.info("BlackListedCardDaoImpl | searchBlackListedCardInformation | Entering");

		Integer pageIndex = searchBlackListedCardRequest.getPageIndex();
		Integer pageSize = searchBlackListedCardRequest.getPageSize();
		Integer totalRecords = searchBlackListedCardRequest.getNoOfRecords();
		List<BlackListedCardRequest> searchBlackListedCardList = new ArrayList<BlackListedCardRequest>();
		try{
		      
		int offset = 0;
		int limit = 0;
		
		if(pageIndex == null || pageIndex == 1){
			totalRecords = getTotalNumberOfRecords(searchBlackListedCardRequest);
			searchBlackListedCardRequest.setNoOfRecords(totalRecords);
		}

		if (pageIndex == null && pageSize == null) {
			offset = 0;
			limit = Constants.DEFAULT_PAGE_SIZE;
		} else {
			offset = (pageIndex - 1) * pageSize;
			limit = pageSize;
		}
		
		JPAQuery query = new JPAQuery(entityManager);
	      List<Tuple> tupleList = query.from(QPGBlackListedCard.pGBlackListedCard).
	          where(isCardNumberEq(searchBlackListedCardRequest.getCardNumber()),
	          isStatusEq(searchBlackListedCardRequest.getStatus())).
	          offset(offset).
	          limit(limit).
	          orderBy(orderByCreatedDateDesc()).
	          list(QPGBlackListedCard.pGBlackListedCard.id,
	        		  QPGBlackListedCard.pGBlackListedCard.cardNumber,
	        		  QPGBlackListedCard.pGBlackListedCard.status);
	             
	      if(!CollectionUtils.isEmpty(tupleList)) {
	    	  BlackListedCardRequest pgblackListedCard = null;
	          for(Tuple tuple : tupleList) {
	              
	        	  pgblackListedCard = new BlackListedCardRequest();
	        	  pgblackListedCard.setId(tuple.get(QPGBlackListedCard.pGBlackListedCard.id));
	        	  pgblackListedCard.setCardNumber(tuple.get(QPGBlackListedCard.pGBlackListedCard.cardNumber));
	        	  pgblackListedCard.setStatus(tuple.get(QPGBlackListedCard.pGBlackListedCard.status));
	        	  searchBlackListedCardList.add(pgblackListedCard);
	          }
	        }
		}
	      catch(Exception e) {
	        log.error("BlackListedCardDaoImpl | searchBlackListedCardInformation | Exception " + e);
	      }
	      log.info("BlackListedCardDaoImpl | searchBlackListedCardInformation | Exiting");
	      return searchBlackListedCardList;
	    }
	
	/**
	 * @param cardNumber
	 * @return
	 */
	private BooleanExpression isCardNumberEq(Long cardNumber) {
		return (cardNumber != null && !"".equals(cardNumber.toString())) ? QPGBlackListedCard.pGBlackListedCard.cardNumber.eq(cardNumber) : null;
	}

	/**
	 * @return
	 */
	private OrderSpecifier<Timestamp> orderByCreatedDateDesc() {
	    return QPGBlackListedCard.pGBlackListedCard.createdDate.desc();
	  }

	/**
	 * @param status
	 * @return
	 */
	private BooleanExpression isStatusEq(Integer status) {
		return (status != null && !"".equals(status.toString())) ? QPGBlackListedCard.pGBlackListedCard.status.eq(status) : null;
	}

	public int getTotalNumberOfRecords(BlackListedCardRequest blackListedCardRecords) {
	    JPAQuery query = new JPAQuery(entityManager);
	    List<Long> list = query.from(QPGBlackListedCard.pGBlackListedCard). where(
		          isCardNumberEq(blackListedCardRecords.getCardNumber()),
		          isStatusEq(blackListedCardRecords.getStatus())).list(QPGBlackListedCard.pGBlackListedCard.id);
	    return (StringUtils.isListNotNullNEmpty(list) ? list.size() : 0);
	  }
	
	@Override
	public Response getCardDataByCardNumber(long cardNumber) {
		Response response = new Response();
		PGBlackListedCard blackListedCard = blackListedCardRepository.findByCardNumberAndStatusNotLike(cardNumber, Constants.TWO);
		if (blackListedCard != null) {
			response.setErrorCode(ActionErrorCode.ERROR_CODE_Z5);
			response.setErrorMessage(messageSource.getMessage("chatak.transaction.blacklistedcard", null, LocaleContextHolder.getLocale()));
		}
		return response;
	}
	
	@Override
	public PGBlackListedCard createOrUpdateBlackListedCard(PGBlackListedCard pgBlackListedCard) throws DataAccessException {
	    return blackListedCardRepository.save(pgBlackListedCard);
	}


	/**
	 * @param cardNumber
	 * @return
	 */
	@Override
	public PGBlackListedCard getCardNumber(Long cardNumber) {
		return blackListedCardRepository.findByCardNumber(cardNumber);
	}

}
