package com.chatak.acquirer.admin.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

import com.chatak.acquirer.admin.constants.StatusConstants;
import com.chatak.acquirer.admin.controller.model.Option;
import com.chatak.acquirer.admin.exception.ChatakAdminException;
import com.chatak.acquirer.admin.service.CardProgramServices;
import com.chatak.pg.acq.dao.CardProgramDao;
import com.chatak.pg.acq.dao.model.CardProgram;
import com.chatak.pg.bean.Response;
import com.chatak.pg.user.bean.CardProgramRequest;
import com.chatak.pg.user.bean.CardProgramResponse;
import com.chatak.pg.util.Constants;

@Service
public class CardProgramServiceImpl implements CardProgramServices{

	private static Logger logger = Logger.getLogger(CardProgramServiceImpl.class);
	
	@Autowired
	CardProgramDao cardProgramDao;
	
	@Autowired
	MessageSource messageSource;
	
	@Override
	public Response getCardProgramsByCurrency(String currency) throws ChatakAdminException {
		Response response = new Response();
		try {
			logger.info("Entering :: CardProgramServiceImpl :: getCardProgramsByCurrency");
			List<CardProgram> cardProgramList = cardProgramDao.findByCurrency(currency);

			if (cardProgramList != null) {
				List<Option> options = new ArrayList<Option>(cardProgramList.size());
				Option option = null;
				for (CardProgram cpList : cardProgramList) {
					option = new Option();
					option.setValue(cpList.getCardProgramId().toString());
					option.setLabel(cpList.getCardProgramName());
					options.add(option);
				}
				response.setResponseList(options);
				response.setErrorCode(StatusConstants.STATUS_CODE_SUCCESS);
				response.setErrorMessage(StatusConstants.STATUS_MESSAGE_SUCCESS);
				logger.info("Exiting :: CardProgramServiceImpl :: getCardProgramsByCurrency");
			}
			return response;
		} catch (Exception e) {
			logger.error("Error :: CardProgramServiceImpl :: getCardProgramsByCurrency : " + e.getMessage(), e);
			throw new ChatakAdminException(
					messageSource.getMessage(Constants.CHATAK_GENERAL_ERROR, null, LocaleContextHolder.getLocale()), e);
		}
	}
	
	public CardProgramResponse getCardProgramListForFeeProgram() throws ChatakAdminException {
	  logger.info("Entering :: CardProgramServiceImpl :: getCardProgramListForFeeProgram");
	  CardProgramResponse response = new CardProgramResponse();
	  try{
	    List<CardProgramRequest> cardProgramList = cardProgramDao.getCardProgramListForFeeProgram();
	    response.setCardProgramList(cardProgramList);
	    response.setErrorCode(StatusConstants.STATUS_CODE_SUCCESS);
        response.setErrorMessage(StatusConstants.STATUS_MESSAGE_SUCCESS);
	  }catch(Exception e){
	    logger.error("Error :: CardProgramServiceImpl :: getCardProgramListForFeeProgram : " + e.getMessage(), e);
        throw new ChatakAdminException(
                messageSource.getMessage(Constants.CHATAK_GENERAL_ERROR, null, LocaleContextHolder.getLocale()), e);
	  }
	  logger.info("Exiting :: CardProgramServiceImpl :: getCardProgramListForFeeProgram");
	  return response;
	}
}
