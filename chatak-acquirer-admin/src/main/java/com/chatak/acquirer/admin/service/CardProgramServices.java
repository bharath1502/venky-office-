package com.chatak.acquirer.admin.service;

import com.chatak.acquirer.admin.exception.ChatakAdminException;
import com.chatak.pg.bean.Response;
import com.chatak.pg.user.bean.CardProgramResponse;

public interface CardProgramServices {

	public Response getCardProgramsByCurrency(String currency) throws ChatakAdminException;
	public CardProgramResponse getCardProgramListForFeeProgram() throws ChatakAdminException;
}
