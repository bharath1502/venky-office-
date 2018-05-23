package com.chatak.acquirer.admin.service;

import java.util.Arrays;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import com.chatak.acquirer.admin.exception.ChatakAdminException;
import com.chatak.acquirer.admin.service.impl.BlackListedCardServiceImpl;
import com.chatak.pg.acq.dao.BlackListedCardDao;
import com.chatak.pg.acq.dao.model.PGBlackListedCard;
import com.chatak.pg.constants.ActionErrorCode;
import com.chatak.pg.model.BlackListedCard;
import com.chatak.pg.user.bean.BlackListedCardRequest;
import com.chatak.pg.user.bean.BlackListedCardResponse;
import com.chatak.pg.util.Constants;

@RunWith(MockitoJUnitRunner.class)
public class BlackListedCardServiceTest {

  @InjectMocks
  BlackListedCardServiceImpl blackListedCardService = new BlackListedCardServiceImpl();

  @Mock
  private BlackListedCardDao blackListedCardDao;

  @Mock
  private BlackListedCardRequest addBlackListedRequest = new BlackListedCardRequest();

  @Before
  public void setup() {
    MockitoAnnotations.initMocks(this);
  }

  @Test
  public void testaddBlackListedCardInfo() throws ChatakAdminException {
    BlackListedCardResponse addBlackListedResponse = Mockito.mock(BlackListedCardResponse.class);
    BlackListedCard blacklistedcardInfo = Mockito.mock(BlackListedCard.class);

    Mockito
        .when(blackListedCardDao.addBlackListedCardInfo(Matchers.any(BlackListedCardRequest.class)))
        .thenReturn(addBlackListedResponse);
    Mockito.when(addBlackListedResponse.getErrorCode()).thenReturn(ActionErrorCode.ERROR_CODE_00);
    addBlackListedResponse =
        blackListedCardService.addBlackListedCardInfo(blacklistedcardInfo, "1");
    Assert.assertNotNull(addBlackListedResponse);

  }

  @Test
  public void testsearchBlackListedCardInformation() throws ChatakAdminException {
    BlackListedCardResponse addBlackListedResponse = Mockito.mock(BlackListedCardResponse.class);
    BlackListedCardRequest addBlackListedCardReq = Mockito.mock(BlackListedCardRequest.class);

    Mockito.when(blackListedCardDao.searchBlackListedCardInformation(addBlackListedCardReq))
        .thenReturn(Arrays.asList(addBlackListedRequest));
    Mockito.when(addBlackListedRequest.getStatus()).thenReturn(0);
    Mockito.when(addBlackListedResponse.getErrorCode()).thenReturn(ActionErrorCode.ERROR_CODE_00);
    addBlackListedResponse =
        blackListedCardService.searchBlackListedCardInformation(addBlackListedCardReq);
    Assert.assertNotNull(addBlackListedResponse);
  }

  @Test
  public void testupdateBlackListedCardInformation() {
    BlackListedCardResponse addBlackListedResponse = Mockito.mock(BlackListedCardResponse.class);
    BlackListedCardRequest addBlackListedCardReq = Mockito.mock(BlackListedCardRequest.class);

    Mockito.when(blackListedCardDao.updateBlackListedCardInformation(addBlackListedCardReq, "2"))
        .thenReturn(addBlackListedResponse);
    addBlackListedResponse =
        blackListedCardService.updateBlackListedCardInformation(addBlackListedCardReq, "2");
    Assert.assertNotNull(addBlackListedResponse);

  }

  @Test
  public void testgetBlackListedCardInfoById() {
    PGBlackListedCard pgBlackListedCard = Mockito.mock(PGBlackListedCard.class);
    BlackListedCardResponse addBlackListedResponse = Mockito.mock(BlackListedCardResponse.class);

    Mockito.when(blackListedCardDao.getBlackListedCardInfoById(Constants.TWO_LONG)).thenReturn(pgBlackListedCard);
    addBlackListedRequest = blackListedCardService.getBlackListedCardInfoById(Constants.TWO_LONG);
    Assert.assertNotNull(addBlackListedResponse);
  }
}
