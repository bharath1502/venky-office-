package com.chatak.acquirer.admin.service.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

import com.chatak.acquirer.admin.constants.StatusConstants;
import com.chatak.acquirer.admin.exception.ChatakAdminException;
import com.chatak.acquirer.admin.service.CardProgramServices;
import com.chatak.pg.acq.dao.CardProgramDao;
import com.chatak.pg.bean.Response;
import com.chatak.pg.user.bean.CardProgramRequest;
import com.chatak.pg.user.bean.CardProgramResponse;
import com.chatak.pg.util.Constants;
import com.chatak.pg.util.LogHelper;
import com.chatak.pg.util.LoggerMessage;

@Service
public class CardProgramServiceImpl implements CardProgramServices{

	private static Logger logger = Logger.getLogger(CardProgramServiceImpl.class);
	
	@Autowired
	CardProgramDao cardProgramDao;
	
	@Autowired
	MessageSource messageSource;
	
	@Override
	public List<CardProgramRequest> getCardProgramByBankId(Long bankId) throws ChatakAdminException {
		Response response = new Response();
		try {
			List<CardProgramRequest> cardProgramResponse = cardProgramDao.findByBankId(bankId);
			response.setErrorCode(StatusConstants.STATUS_CODE_SUCCESS);
			response.setErrorMessage(StatusConstants.STATUS_MESSAGE_SUCCESS);
			logger.info("Exiting:: CardProgramServiceImpl :: getCardProgramByBankId method: ");
			return cardProgramResponse;
		} catch (Exception e) {
			logger.error("ERROR:: CardProgramServiceImpl :: getCardProgramByBankId method.", e);
			throw new ChatakAdminException(
					messageSource.getMessage(Constants.CHATAK_GENERAL_ERROR, null, LocaleContextHolder.getLocale()), e);
		}
	}
	
	public CardProgramResponse getCardProgramListForFeeProgram() throws ChatakAdminException {
	  LogHelper.logEntry(logger, LoggerMessage.getCallerName());
	  CardProgramResponse response = new CardProgramResponse();
	  try{
	    List<CardProgramRequest> cardProgramList = cardProgramDao.getCardProgramListForFeeProgram();
	    response.setCardProgramList(cardProgramList);
	    response.setErrorCode(StatusConstants.STATUS_CODE_SUCCESS);
        response.setErrorMessage(StatusConstants.STATUS_MESSAGE_SUCCESS);
	  }catch(Exception e){
	    LogHelper.logError(logger, LoggerMessage.getCallerName(), e, Constants.EXCEPTION);
        throw new ChatakAdminException(
                messageSource.getMessage(Constants.CHATAK_GENERAL_ERROR, null, LocaleContextHolder.getLocale()), e);
	  }
	  LogHelper.logExit(logger, LoggerMessage.getCallerName());
	  return response;
	}
}