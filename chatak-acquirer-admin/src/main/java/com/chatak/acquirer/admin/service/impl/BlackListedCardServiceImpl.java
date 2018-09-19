package com.chatak.acquirer.admin.service.impl;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.chatak.acquirer.admin.exception.ChatakAdminException;
import com.chatak.acquirer.admin.service.BlackListedCardService;
import com.chatak.pg.acq.dao.BINDao;
import com.chatak.pg.acq.dao.BlackListedCardDao;
import com.chatak.pg.acq.dao.CardProgramDao;
import com.chatak.pg.acq.dao.model.PGBlackListedCard;
import com.chatak.pg.bean.CardNumberResponse;
import com.chatak.pg.bean.Response;
import com.chatak.pg.constants.ActionErrorCode;
import com.chatak.pg.constants.PGConstants;
import com.chatak.pg.model.BlackListedCard;
import com.chatak.pg.user.bean.BlackListedCardRequest;
import com.chatak.pg.user.bean.BlackListedCardResponse;
import com.chatak.pg.util.CommonUtil;

@Service
public class BlackListedCardServiceImpl implements BlackListedCardService, PGConstants {

  private static Logger logger = Logger.getLogger(BlackListedCardServiceImpl.class);

  @Autowired
  private BlackListedCardDao blackListedCardDao;

  @Autowired
  private BINDao bINDao;
  
  @Autowired
  private CardProgramDao cardProgramDao;

  @Override
  public BlackListedCardResponse addBlackListedCardInfo(BlackListedCard blacklistedcardInfo,
      String userid) throws ChatakAdminException {
    logger.info("Entering:: BlackListedCardServiceImpl:: addBlackListedCardInformation method");
    BlackListedCardResponse addBlackListedResponse = new BlackListedCardResponse();
    try {
      BlackListedCardRequest addBlackListedCardRequest = new BlackListedCardRequest();
      addBlackListedCardRequest.setCardNumber(blacklistedcardInfo.getCardNumber());
      addBlackListedCardRequest.setStatus(STATUS_ACTIVE);
      addBlackListedResponse = blackListedCardDao.addBlackListedCardInfo(addBlackListedCardRequest);
    } catch (Exception e) {
      logger.error("BlackListedCardDaoImpl | addBlackListedCardInformation | Exception" + e);
    }
    logger.info("Exiting:: BlackListedCardServiceImpl:: addBlackListedCardInformation method");
    return addBlackListedResponse;
  }

  @Override
  public BlackListedCardResponse searchBlackListedCardInformation(
      BlackListedCardRequest searchBlackListedCardRequest) throws ChatakAdminException {
    logger.info("Entering:: BlackListedCardServiceImpl:: searchBlackListedCardInformation method");
    BlackListedCardResponse searchBlackListedCardResponse = new BlackListedCardResponse();
    try {
      List<BlackListedCardRequest> searchBlackListedCardRequestList =
          blackListedCardDao.searchBlackListedCardInformation(searchBlackListedCardRequest);
      if (!CollectionUtils.isEmpty(searchBlackListedCardRequestList)) {
        List<BlackListedCardRequest> list =
            new ArrayList<BlackListedCardRequest>(searchBlackListedCardRequestList.size());
        BlackListedCardRequest searchBlackListedCard = null;
        for (BlackListedCardRequest blRequest : searchBlackListedCardRequestList) {
          searchBlackListedCard = new BlackListedCardRequest();
          searchBlackListedCard.setId(blRequest.getId());
          searchBlackListedCard.setCardNumber(blRequest.getCardNumber());
          searchBlackListedCard.setStatus(blRequest.getStatus());
          searchBlackListedCard.setCreatedDate(blRequest.getCreatedDate());
          verifyStatus(searchBlackListedCard, blRequest);
          list.add(searchBlackListedCard);
        }
        searchBlackListedCardResponse.setBlackListedCardRequest(list);
        searchBlackListedCardResponse.setErrorCode(ActionErrorCode.ERROR_CODE_00);
      }

    } catch (Exception e) {
      logger.info("ERROR:: BlackListedCardServiceImpl:: searchBlackListedCardInformation method",
          e);
    }
    searchBlackListedCardResponse.setTotalNoOfRows(searchBlackListedCardRequest.getNoOfRecords());
    logger.info("Exiting:: BlackListedCardServiceImpl:: searchBlackListedCardInformation method");
    return searchBlackListedCardResponse;
  }

  private void verifyStatus(BlackListedCardRequest searchBlackListedCard,
      BlackListedCardRequest blRequest) {
    if (blRequest.getStatus() == STATUS_SUCCESS) {
      searchBlackListedCard.setStatusDisp(S_STATUS_ACTIVE);
    } else if (blRequest.getStatus() == STATUS_PENDING) {
      searchBlackListedCard.setStatusDisp(S_STATUS_PENDING);
    } else if (blRequest.getStatus() == STATUS_SUSPENDED) {
      searchBlackListedCard.setStatusDisp(S_STATUS_SUSPENDED);
    }
  }

  @Override
  public BlackListedCardResponse updateBlackListedCardInformation(
      BlackListedCardRequest blackListedCardUpdate, String userid) {
    logger.info("Entering:: SwitchServiceImpl:: updateSwitch method");
    BlackListedCardResponse updateBlackListedCardResponse = new BlackListedCardResponse();
    try {
      updateBlackListedCardResponse =
          blackListedCardDao.updateBlackListedCardInformation(blackListedCardUpdate, userid);
    } catch (Exception e) {
      logger.error(
          "ERROR:: BlackListedCardServiceImpl:: updateBlackListedCardInformation method" + e);
    }
    logger.info("Exiting:: BlackListedCardServiceImpl:: updateBlackListedCardInformation method");
    return updateBlackListedCardResponse;
  }

  @Override
  public BlackListedCardRequest getBlackListedCardInfoById(Long getBlackListedCardId) {
    logger.info("Entering:: BlackListedCardServiceImpl:: getBlackListedCardInformationById method");
    BlackListedCardRequest blacklistedcardRequest = new BlackListedCardRequest();
    try {
      PGBlackListedCard pgBlackListedCard =
          blackListedCardDao.getBlackListedCardInfoById(getBlackListedCardId);
      if (pgBlackListedCard != null) {
        blacklistedcardRequest.setId(pgBlackListedCard.getId());
        blacklistedcardRequest.setCardNumber(pgBlackListedCard.getCardNumber());
        blacklistedcardRequest.setStatus(pgBlackListedCard.getStatus());
      }
    } catch (Exception e) {
      logger.error(
          "ERROR:: BlackListedCardServiceImpl:: getBlackListedCardInformationById method" + e);
    }
    logger.info("Exiting:: BlackListedCardServiceImpl:: getBlackListedCardInformationById method");
    return blacklistedcardRequest;
  }

  @Override
  public Response findByCardNumber(String cardNum) {
    BigInteger cardNumber = new BigInteger(cardNum);
    Response response = blackListedCardDao.getCardDataByCardNumber(cardNumber);
    return response;
  }

  @Override
  public BlackListedCardResponse changeBlackListedCardStatus(
      BlackListedCardRequest blackListedCardRequest, String blackListedCardStatus) {
    logger.info("Entering:: BlackListedCardServiceImpl:: changeBlackListedCardStatus method");
    BlackListedCardResponse blackListedCardResponse = new BlackListedCardResponse();
    try {
      if (null != blackListedCardRequest) {
        PGBlackListedCard pGBlackListedCard =
            blackListedCardDao.getBlackListedCardInfoById(blackListedCardRequest.getId());
        if (S_STATUS_ACTIVE.equals(blackListedCardStatus)) {
          pGBlackListedCard.setStatus(STATUS_ACTIVE);
        } else {
          pGBlackListedCard.setStatus(STATUS_SUSPENDED);
        }
        pGBlackListedCard.setReason(blackListedCardRequest.getReason());
        blackListedCardDao.createOrUpdateBlackListedCard(pGBlackListedCard);
        blackListedCardResponse.setErrorCode(ActionErrorCode.ERROR_CODE_00);
      }
    } catch (DataAccessException e) {
      blackListedCardResponse.setErrorCode(ActionErrorCode.ERROR_CODE_01);
      logger.error("", e);
    }
    return blackListedCardResponse;
  }

  @Override
  public CardNumberResponse validateCardNumber(BigInteger cardNum) {
    logger.info("Entering:: BlackListedCardServiceImpl:: validateCardNumber method");
    CardNumberResponse cardNumberResponse = new CardNumberResponse();
    
	String cardnumber = cardNum.toString();
	Long cardProgramId = cardProgramDao.findCardProgramByIIN(CommonUtil.getIIN(cardnumber),
			CommonUtil.getPartnerIINExt(cardnumber), CommonUtil.getIINExt(cardnumber));
	if (cardProgramId == null) {
		cardNumberResponse.setErrorCode(ActionErrorCode.ERROR_CODE_201);
		cardNumberResponse
				.setErrorMessage(ActionErrorCode.getInstance().getMessage(ActionErrorCode.ERROR_CODE_201));
		return cardNumberResponse;
	} else {
		cardNumberResponse.setErrorCode(ActionErrorCode.ERROR_CODE_00);
		cardNumberResponse.setErrorMessage(ActionErrorCode.getInstance().getMessage(ActionErrorCode.ERROR_CODE_00));
		return cardNumberResponse;
	}
  }
}
