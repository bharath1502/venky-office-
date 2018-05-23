/**
 * 
 */
package com.chatak.pg.acq.dao;

import java.util.List;

import org.springframework.dao.DataAccessException;
import com.chatak.pg.acq.dao.model.PGBlackListedCard;
import com.chatak.pg.bean.Response;
import com.chatak.pg.user.bean.BlackListedCardRequest;
import com.chatak.pg.user.bean.BlackListedCardResponse;

/**
 *
 * DAO class to handle Switch DB operations
 *
 * @author Girmiti Software
 * @date 22-Dec-2014 5:17:01 pm
 * @version 1.0
 */
public interface BlackListedCardDao {

	
	public BlackListedCardResponse addBlackListedCardInfo(BlackListedCardRequest addBlackListedCardRequest);

	public List<BlackListedCardRequest> searchBlackListedCardInformation(BlackListedCardRequest addBlackListedCardRequest);

	public BlackListedCardResponse updateBlackListedCardInformation(BlackListedCardRequest updateBlackListedCardRequest, String userid);

	public PGBlackListedCard getBlackListedCardInfoById(Long getBlackListedCardId);

	public Response getCardDataByCardNumber(long cardNumber);
	
	public PGBlackListedCard createOrUpdateBlackListedCard(PGBlackListedCard pgBlackListedCard) throws DataAccessException;
	
	public PGBlackListedCard getCardNumber(Long cardNumber);

}