package com.chatak.acquirer.admin.service;

import java.math.BigInteger;

import com.chatak.acquirer.admin.exception.ChatakAdminException;
import com.chatak.pg.bean.CardNumberResponse;
import com.chatak.pg.bean.Response;
import com.chatak.pg.model.BlackListedCard;
import com.chatak.pg.user.bean.BlackListedCardRequest;
import com.chatak.pg.user.bean.BlackListedCardResponse;

public interface BlackListedCardService {
	
	public BlackListedCardResponse addBlackListedCardInfo(BlackListedCard blackListedCard, String userid) throws ChatakAdminException;
	
	public BlackListedCardResponse searchBlackListedCardInformation(BlackListedCardRequest searchBlackListedCardRequest) throws ChatakAdminException;

	public BlackListedCardResponse updateBlackListedCardInformation(BlackListedCardRequest updateBlackListedCard, String userid);

	public BlackListedCardRequest getBlackListedCardInfoById(Long getBlackListedCardId);

	public Response findByCardNumber(String cardNum);
	
	public BlackListedCardResponse changeBlackListedCardStatus(BlackListedCardRequest blackListedCardRequest, String cardStatus);
	
	public CardNumberResponse validateCardNumber(BigInteger cardNum);

}