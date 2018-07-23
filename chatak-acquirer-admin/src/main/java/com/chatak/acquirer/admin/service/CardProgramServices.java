package com.chatak.acquirer.admin.service;

import java.util.List;

import com.chatak.acquirer.admin.exception.ChatakAdminException;
import com.chatak.pg.user.bean.CardProgramRequest;
import com.chatak.pg.user.bean.CardProgramResponse;

public interface CardProgramServices {

	public List<CardProgramRequest> getCardProgramByBankId(Long bankId) throws ChatakAdminException;
	public CardProgramResponse getCardProgramListForFeeProgram() throws ChatakAdminException;
}
