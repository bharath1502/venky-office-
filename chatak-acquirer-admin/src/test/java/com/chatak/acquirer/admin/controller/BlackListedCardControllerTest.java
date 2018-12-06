package com.chatak.acquirer.admin.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import com.chatak.acquirer.admin.constants.TestConstants;
import com.chatak.acquirer.admin.constants.URLMappingConstants;
import com.chatak.acquirer.admin.controller.model.LoginDetails;
import com.chatak.acquirer.admin.controller.model.Option;
import com.chatak.acquirer.admin.model.Response;
import com.chatak.acquirer.admin.service.BlackListedCardService;
import com.chatak.pg.bean.CardNumberResponse;
import com.chatak.pg.constants.ActionErrorCode;
import com.chatak.pg.model.BlackListedCard;
import com.chatak.pg.user.bean.BlackListedCardRequest;
import com.chatak.pg.user.bean.BlackListedCardResponse;

@RunWith(MockitoJUnitRunner.class)
public class BlackListedCardControllerTest {

  private static Logger logger = LoggerFactory.getLogger(BlackListedCardControllerTest.class);

  @InjectMocks
  BlackListedCardController blackListedCardController = new BlackListedCardController();

  @Mock
  HttpServletRequest request;

  @Mock
  HttpServletResponse response;

  @Mock
  private Response responseval;

  @Mock
  BindingResult bindingResult;

  @Mock
  private List<Option> optionList;

  private MockMvc mockMvc;

  @Mock
  private MessageSource messageSource;

  @Mock
  private NullPointerException nullPointerException;

  @Mock
  private BlackListedCard blackListedCard;

  @Mock
  private BlackListedCardService blackListedCardService;

  @Mock
  private BlackListedCardResponse addBlackListedCardResponse;

  @Mock
  private BlackListedCardRequest searchBlackListedCardRequest;

  @Mock
  private List<BlackListedCardRequest> blacklistedcardRequest;

  @Mock
  private LoginDetails loginDetails;

  @Mock
  private CardNumberResponse cardNumberResponse;

  @Before
  public void setup() {
    MockitoAnnotations.initMocks(this);
    InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
    viewResolver.setPrefix("/pages/");
    viewResolver.setSuffix(".jsp");
    mockMvc = MockMvcBuilders.standaloneSetup(blackListedCardController)
        .setViewResolvers(viewResolver).build();
    optionList = new ArrayList<>();
  }

  @Test
  public void testShowCreateBlackListedCardPage() {
    try {
      mockMvc
          .perform(get("/" + URLMappingConstants.CHATAK_ADMIN_CREATE_BLACK_LISTED_CARD_PAGE)
              .sessionAttr(TestConstants.EXISTING_FEATURES, TestConstants.EXISTING_FEATURES))
          .andExpect(view().name(URLMappingConstants.CHATAK_ADMIN_CREATE_BLACK_LISTED_CARD_PAGE));
    } catch (Exception e) {
      logger.error("BlackListedCardControllerTest | testShowCreateBlackListedCardPage | Exception ",
          e);
    }
  }

  @Test
  public void testBlackListedCard() {
    addBlackListedCardResponse = new BlackListedCardResponse();
    addBlackListedCardResponse.setErrorCode(ActionErrorCode.ERROR_CODE_00);
    try {
      Mockito.when(blackListedCardService
          .addBlackListedCardInfo(Matchers.any(BlackListedCard.class), Matchers.anyString()))
          .thenReturn(addBlackListedCardResponse);
      mockMvc
          .perform(post("/" + URLMappingConstants.CHATAK_ADMIN_CREATE_BLACK_LISTED_CARD)
              .sessionAttr(TestConstants.EXISTING_FEATURES, TestConstants.EXISTING_FEATURES)
              .sessionAttr(TestConstants.LOGIN_USER_ID, Long.valueOf(TestConstants.ONE)))
          .andExpect(view().name(URLMappingConstants.CHATAK_ADMIN_SEARCH_BLACK_LISTED_CARD_PAGE));
    } catch (Exception e) {
      logger.error("BlackListedCardControllerTest | testShowCreateBlackListedCardPage | Exception ",
          e);
    }
  }

  @Test
  public void testBlackListedCardCodeZ5() {
    addBlackListedCardResponse = new BlackListedCardResponse();
    addBlackListedCardResponse.setErrorCode(ActionErrorCode.ERROR_CODE_Z5);
    try {
      Mockito.when(blackListedCardService
          .addBlackListedCardInfo(Matchers.any(BlackListedCard.class), Matchers.anyString()))
          .thenReturn(addBlackListedCardResponse);
      mockMvc
          .perform(post("/" + URLMappingConstants.CHATAK_ADMIN_CREATE_BLACK_LISTED_CARD)
              .sessionAttr(TestConstants.EXISTING_FEATURES, TestConstants.EXISTING_FEATURES)
              .sessionAttr(TestConstants.LOGIN_USER_ID, Long.valueOf(TestConstants.ONE)))
          .andExpect(view().name(URLMappingConstants.CHATAK_ADMIN_SEARCH_BLACK_LISTED_CARD_PAGE));
    } catch (Exception e) {
      logger.error("BlackListedCardControllerTest | testBlackListedCardCodeZ5 | Exception ", e);
    }
  }

  @Test
  public void testBlackListedCardElse() {
    addBlackListedCardResponse = new BlackListedCardResponse();
    addBlackListedCardResponse.setErrorCode(ActionErrorCode.ERROR_CODE_Z10);
    try {
      Mockito.when(blackListedCardService
          .addBlackListedCardInfo(Matchers.any(BlackListedCard.class), Matchers.anyString()))
          .thenReturn(addBlackListedCardResponse);
      mockMvc
          .perform(post("/" + URLMappingConstants.CHATAK_ADMIN_CREATE_BLACK_LISTED_CARD)
              .sessionAttr(TestConstants.EXISTING_FEATURES, TestConstants.EXISTING_FEATURES)
              .sessionAttr(TestConstants.LOGIN_USER_ID, Long.valueOf(TestConstants.ONE)))
          .andExpect(view().name(URLMappingConstants.CHATAK_ADMIN_SEARCH_BLACK_LISTED_CARD_PAGE));
    } catch (Exception e) {
      logger.error("BlackListedCardControllerTest | testBlackListedCardElse | Exception ", e);
    }
  }

  @Test
  public void testBlackListedCardException() {
    try {
      Mockito.when(blackListedCardService
          .addBlackListedCardInfo(Matchers.any(BlackListedCard.class), Matchers.anyString()))
          .thenThrow(nullPointerException);
      mockMvc
          .perform(post("/" + URLMappingConstants.CHATAK_ADMIN_CREATE_BLACK_LISTED_CARD)
              .sessionAttr(TestConstants.EXISTING_FEATURES, TestConstants.EXISTING_FEATURES)
              .sessionAttr(TestConstants.LOGIN_USER_ID, Long.valueOf(TestConstants.ONE)))
          .andExpect(view().name(URLMappingConstants.CHATAK_ADMIN_SEARCH_BLACK_LISTED_CARD_PAGE));
    } catch (Exception e) {
      logger.error("BlackListedCardControllerTest | testBlackListedCardException | Exception ", e);
    }
  }

  @Test
  public void testShowSearchBlackListedCardPage() {
    try {
      mockMvc
          .perform(get("/" + URLMappingConstants.CHATAK_ADMIN_SEARCH_BLACK_LISTED_CARD_PAGE)
              .sessionAttr(TestConstants.EXISTING_FEATURES, TestConstants.EXISTING_FEATURES)
              .sessionAttr(TestConstants.LOGIN_USER_ID, Long.valueOf(TestConstants.ONE)))
          .andExpect(view().name(URLMappingConstants.CHATAK_ADMIN_SEARCH_BLACK_LISTED_CARD_PAGE));
    } catch (Exception e) {
      logger.error("BlackListedCardControllerTest | testShowSearchBlackListedCardPage | Exception ",
          e);
    }
  }

  @Test
  public void testSearchBlackListedCardInfo() {
    addBlackListedCardResponse = new BlackListedCardResponse();
    addBlackListedCardResponse.setBlackListedCardRequest(blacklistedcardRequest);
    addBlackListedCardResponse.setTotalNoOfRows(TestConstants.TEN.intValue());
    try {
      Mockito
          .when(blackListedCardService
              .searchBlackListedCardInformation(Matchers.any(BlackListedCardRequest.class)))
          .thenReturn(addBlackListedCardResponse);
      mockMvc
          .perform(post("/" + URLMappingConstants.CHATAK_ADMIN_SEARCH_BLACK_LISTED_CARD)
              .sessionAttr(TestConstants.EXISTING_FEATURES, TestConstants.EXISTING_FEATURES)
              .sessionAttr(TestConstants.LOGIN_USER_ID, Long.valueOf(TestConstants.ONE)))
          .andExpect(view().name(URLMappingConstants.CHATAK_ADMIN_SEARCH_BLACK_LISTED_CARD_PAGE));
    } catch (Exception e) {
      logger.error("BlackListedCardControllerTest | testSearchBlackListedCardInfo | Exception ", e);
    }
  }

  @Test
  public void testSearchBlackListedCardInfoIf() {
    try {
      Mockito
          .when(blackListedCardService
              .searchBlackListedCardInformation(Matchers.any(BlackListedCardRequest.class)))
          .thenReturn(addBlackListedCardResponse);
      mockMvc
          .perform(post("/" + URLMappingConstants.CHATAK_ADMIN_SEARCH_BLACK_LISTED_CARD)
              .sessionAttr(TestConstants.EXISTING_FEATURES, TestConstants.EXISTING_FEATURES)
              .sessionAttr(TestConstants.LOGIN_USER_ID, Long.valueOf(TestConstants.ONE)))
          .andExpect(view().name(URLMappingConstants.CHATAK_ADMIN_SEARCH_BLACK_LISTED_CARD_PAGE));
    } catch (Exception e) {
      logger.error("BlackListedCardControllerTest | testSearchBlackListedCardInfoIf | Exception ",
          e);
    }
  }

  @Test
  public void testSearchBlackListedCardInfoException() {
    try {
      Mockito
          .when(blackListedCardService
              .searchBlackListedCardInformation(Matchers.any(BlackListedCardRequest.class)))
          .thenThrow(nullPointerException);
      mockMvc
          .perform(post("/" + URLMappingConstants.CHATAK_ADMIN_SEARCH_BLACK_LISTED_CARD)
              .sessionAttr(TestConstants.EXISTING_FEATURES, TestConstants.EXISTING_FEATURES)
              .sessionAttr(TestConstants.LOGIN_USER_ID, Long.valueOf(TestConstants.ONE)))
          .andExpect(view().name(URLMappingConstants.CHATAK_ADMIN_SEARCH_BLACK_LISTED_CARD_PAGE));
    } catch (Exception e) {
      logger.error(
          "BlackListedCardControllerTest | testSearchBlackListedCardInfoException | Exception ", e);
    }
  }

  @Test
  public void testShowEditBlackListedCard() {
    try {
      Mockito.when(blackListedCardService.getBlackListedCardInfoById(Matchers.anyLong()))
          .thenReturn(searchBlackListedCardRequest);
      mockMvc
          .perform(post("/" + URLMappingConstants.CHATAK_ADMIN_SHOW_EDIT_BLACKLISTED_CARD)
              .sessionAttr(TestConstants.EXISTING_FEATURES, TestConstants.EXISTING_FEATURES)
              .sessionAttr(TestConstants.LOGIN_USER_ID, Long.valueOf(TestConstants.ONE))
              .param("getBlackListedCardId", TestConstants.ONE.toString()))
          .andExpect(view().name(URLMappingConstants.CHATAK_ADMIN_EDIT_BLACK_LISTED_CARD));
    } catch (Exception e) {
      logger.error("BlackListedCardControllerTest | testShowEditBlackListedCard | Exception ", e);
    }
  }

  @Test
  public void testShowEditBlackListedCardException() {
    try {
      Mockito.when(blackListedCardService.getBlackListedCardInfoById(Matchers.anyLong()))
          .thenThrow(nullPointerException);
      mockMvc
          .perform(post("/" + URLMappingConstants.CHATAK_ADMIN_SHOW_EDIT_BLACKLISTED_CARD)
              .sessionAttr(TestConstants.EXISTING_FEATURES, TestConstants.EXISTING_FEATURES)
              .sessionAttr(TestConstants.LOGIN_USER_ID, Long.valueOf(TestConstants.ONE))
              .param("getBlackListedCardId", TestConstants.ONE.toString()))
          .andExpect(view().name(URLMappingConstants.CHATAK_ADMIN_EDIT_BLACK_LISTED_CARD));
    } catch (Exception e) {
      logger.error(
          "BlackListedCardControllerTest | testShowEditBlackListedCardException | Exception ", e);
    }
  }

  @Test
  public void testShowViewBlackListedCard() {
    try {
      Mockito.when(blackListedCardService.getBlackListedCardInfoById(Matchers.anyLong()))
          .thenReturn(searchBlackListedCardRequest);
      mockMvc
          .perform(post("/" + URLMappingConstants.CHATAK_ADMIN_SHOW_VIEW_BLACKLISTED_CARD)
              .sessionAttr(TestConstants.EXISTING_FEATURES, TestConstants.EXISTING_FEATURES)
              .sessionAttr(TestConstants.LOGIN_USER_ID, Long.valueOf(TestConstants.ONE))
              .param("getBlackListedCardId", TestConstants.ONE.toString()))
          .andExpect(view().name(URLMappingConstants.CHATAK_ADMIN_VIEW_BLACKLISTED_CARD));
    } catch (Exception e) {
      logger.error("BlackListedCardControllerTest | testShowViewBlackListedCard | Exception ", e);
    }
  }

  @Test
  public void testShowViewBlackListedCardException() {
    try {
      Mockito.when(blackListedCardService.getBlackListedCardInfoById(Matchers.anyLong()))
          .thenThrow(nullPointerException);
      mockMvc
          .perform(post("/" + URLMappingConstants.CHATAK_ADMIN_SHOW_VIEW_BLACKLISTED_CARD)
              .sessionAttr(TestConstants.EXISTING_FEATURES, TestConstants.EXISTING_FEATURES)
              .sessionAttr(TestConstants.LOGIN_USER_ID, Long.valueOf(TestConstants.ONE))
              .param("getBlackListedCardId", TestConstants.ONE.toString()))
          .andExpect(view().name(URLMappingConstants.CHATAK_ADMIN_VIEW_BLACKLISTED_CARD));
    } catch (Exception e) {
      logger.error(
          "BlackListedCardControllerTest | testShowViewBlackListedCardException | Exception ", e);
    }
  }

  @Test
  public void testUpdateBlackListedCard() {
    addBlackListedCardResponse = new BlackListedCardResponse();
    addBlackListedCardResponse.setErrorCode(ActionErrorCode.ERROR_CODE_00);
    try {
      Mockito
          .when(blackListedCardService.updateBlackListedCardInformation(
              Matchers.any(BlackListedCardRequest.class), Matchers.anyString()))
          .thenReturn(addBlackListedCardResponse);
      mockMvc
          .perform(post("/" + URLMappingConstants.CHATAK_ADMIN_UPDATE_BLACK_LISTD_CARD)
              .sessionAttr(TestConstants.EXISTING_FEATURES, TestConstants.EXISTING_FEATURES)
              .sessionAttr(TestConstants.LOGIN_USER_ID, Long.valueOf(TestConstants.ONE))
              .param("getBlackListedCardId", TestConstants.ONE.toString()))
          .andExpect(view().name(URLMappingConstants.CHATAK_ADMIN_SEARCH_BLACK_LISTED_CARD_PAGE));
    } catch (Exception e) {
      logger.error("BlackListedCardControllerTest | testUpdateBlackListedCard | Exception ", e);
    }
  }

  @Test
  public void testUpdateBlackListedCardElse() {
    addBlackListedCardResponse = new BlackListedCardResponse();
    addBlackListedCardResponse.setErrorCode(ActionErrorCode.ERROR_CODE_01);
    try {
      Mockito
          .when(blackListedCardService.updateBlackListedCardInformation(
              Matchers.any(BlackListedCardRequest.class), Matchers.anyString()))
          .thenReturn(addBlackListedCardResponse);
      mockMvc
          .perform(post("/" + URLMappingConstants.CHATAK_ADMIN_UPDATE_BLACK_LISTD_CARD)
              .sessionAttr(TestConstants.EXISTING_FEATURES, TestConstants.EXISTING_FEATURES)
              .sessionAttr(TestConstants.LOGIN_USER_ID, Long.valueOf(TestConstants.ONE))
              .param("getBlackListedCardId", TestConstants.ONE.toString()))
          .andExpect(view().name(URLMappingConstants.CHATAK_ADMIN_SEARCH_BLACK_LISTED_CARD_PAGE));
    } catch (Exception e) {
      logger.error("BlackListedCardControllerTest | testUpdateBlackListedCardElse | Exception ", e);
    }
  }

  @Test
  public void testUpdateBlackListedCardException() {
    try {
      Mockito
          .when(blackListedCardService.updateBlackListedCardInformation(
              Matchers.any(BlackListedCardRequest.class), Matchers.anyString()))
          .thenThrow(nullPointerException);
      mockMvc
          .perform(post("/" + URLMappingConstants.CHATAK_ADMIN_UPDATE_BLACK_LISTD_CARD)
              .sessionAttr(TestConstants.EXISTING_FEATURES, TestConstants.EXISTING_FEATURES)
              .sessionAttr(TestConstants.LOGIN_USER_ID, Long.valueOf(TestConstants.ONE))
              .param("getBlackListedCardId", TestConstants.ONE.toString()))
          .andExpect(view().name(URLMappingConstants.CHATAK_ADMIN_SEARCH_BLACK_LISTED_CARD_PAGE));
    } catch (Exception e) {
      logger.error(
          "BlackListedCardControllerTest | testUpdateBlackListedCardException | Exception ", e);
    }
  }

  @Test
  public void testGetPaginationList() {
    addBlackListedCardResponse = new BlackListedCardResponse();
    addBlackListedCardResponse.setBlackListedCardRequest(blacklistedcardRequest);
    addBlackListedCardResponse.setTotalNoOfRows(TestConstants.TEN);
    try {
      Mockito
          .when(blackListedCardService
              .searchBlackListedCardInformation(Matchers.any(BlackListedCardRequest.class)))
          .thenReturn(addBlackListedCardResponse);
      mockMvc
          .perform(post("/" + URLMappingConstants.CHATAK_BLACK_LISTED_CARD_PAGINATION)
              .sessionAttr(TestConstants.EXISTING_FEATURES, TestConstants.EXISTING_FEATURES)
              .sessionAttr(TestConstants.BLACK_LISTED_CARD_INFO, searchBlackListedCardRequest)
              .param("pageNumber", TestConstants.ONE.toString())
              .param(TestConstants.TOTAL_RECORDS, TestConstants.TEN.toString()))
          .andExpect(view().name(URLMappingConstants.CHATAK_ADMIN_SEARCH_BLACK_LISTED_CARD_PAGE));
    } catch (Exception e) {
      logger.error("BlackListedCardControllerTest | testGetPaginationList | Exception ", e);
    }
  }

  @Test
  public void testGetPaginationListException() {
    try {
      Mockito
          .when(blackListedCardService
              .searchBlackListedCardInformation(Matchers.any(BlackListedCardRequest.class)))
          .thenThrow(nullPointerException);
      mockMvc
          .perform(post("/" + URLMappingConstants.CHATAK_BLACK_LISTED_CARD_PAGINATION)
              .sessionAttr(TestConstants.EXISTING_FEATURES, TestConstants.EXISTING_FEATURES)
              .sessionAttr(TestConstants.BLACK_LISTED_CARD_INFO, searchBlackListedCardRequest)
              .param("pageNumber", TestConstants.ONE.toString())
              .param(TestConstants.TOTAL_RECORDS, TestConstants.TEN.toString()))
          .andExpect(view().name(URLMappingConstants.CHATAK_ADMIN_SEARCH_BLACK_LISTED_CARD_PAGE));
    } catch (Exception e) {
      logger.error("BlackListedCardControllerTest | testGetPaginationListException | Exception ",
          e);
    }
  }

  @Test
  public void testDownloadBlackListedCardReport() {
    addBlackListedCardResponse = new BlackListedCardResponse();
    addBlackListedCardResponse.setBlackListedCardRequest(blacklistedcardRequest);
    addBlackListedCardResponse.setTotalNoOfRows(TestConstants.TEN);
    try {
      Mockito
          .when(blackListedCardService
              .searchBlackListedCardInformation(Matchers.any(BlackListedCardRequest.class)))
          .thenReturn(addBlackListedCardResponse);
      mockMvc.perform(post("/" + URLMappingConstants.CHATAK_ADMIN_BLACK_LISTED_CARD_REPORT)
          .sessionAttr(TestConstants.EXISTING_FEATURES, TestConstants.EXISTING_FEATURES)
          .sessionAttr(TestConstants.BLACK_LISTED_CARD_INFO, searchBlackListedCardRequest)
          .param("downLoadPageNumber", TestConstants.ONE.toString()).param("downloadType", "XLS")
          .param(TestConstants.TOTAL_RECORDS, TestConstants.TEN.toString()).param("downloadAllRecords", "true"));
    } catch (Exception e) {
      logger.error("BlackListedCardControllerTest | testDownloadBlackListedCardReport | Exception ",
          e);
    }
  }

  @Test
  public void testDownloadBlackListedCardReportPDF() {
    addBlackListedCardResponse = new BlackListedCardResponse();
    addBlackListedCardResponse.setBlackListedCardRequest(blacklistedcardRequest);
    addBlackListedCardResponse.setTotalNoOfRows(TestConstants.TEN);
    try {
      Mockito
          .when(blackListedCardService
              .searchBlackListedCardInformation(Matchers.any(BlackListedCardRequest.class)))
          .thenReturn(addBlackListedCardResponse);
      mockMvc.perform(post("/" + URLMappingConstants.CHATAK_ADMIN_BLACK_LISTED_CARD_REPORT)
          .sessionAttr(TestConstants.EXISTING_FEATURES, TestConstants.EXISTING_FEATURES)
          .sessionAttr(TestConstants.BLACK_LISTED_CARD_INFO, searchBlackListedCardRequest)
          .param("downLoadPageNumber", TestConstants.ONE.toString()).param("downloadType", "PDF")
          .param(TestConstants.TOTAL_RECORDS, TestConstants.TEN.toString()).param("downloadAllRecords", "true"));
    } catch (Exception e) {
      logger.error(
          "BlackListedCardControllerTest | testDownloadBlackListedCardReportPDF | Exception ", e);
    }
  }

  @Test
  public void testChangeBlackListedCardStatus() {
    addBlackListedCardResponse = new BlackListedCardResponse();
    addBlackListedCardResponse.setBlackListedCardRequest(blacklistedcardRequest);
    addBlackListedCardResponse.setErrorCode(ActionErrorCode.ERROR_CODE_00);
    try {
      Mockito
          .when(blackListedCardService.changeBlackListedCardStatus(
              Matchers.any(BlackListedCardRequest.class), Matchers.anyString()))
          .thenReturn(addBlackListedCardResponse);
      mockMvc
          .perform(
              post("/" + URLMappingConstants.CHATAK_ADMIN_ACTIVATION_SUSPENTION_BLACK_LISTD_CARD)
                  .sessionAttr(TestConstants.EXISTING_FEATURES, TestConstants.EXISTING_FEATURES)
                  .sessionAttr(TestConstants.BLACK_LISTED_CARD_INFO, searchBlackListedCardRequest)
                  .sessionAttr("TestConstants.PAGE_NUMBER", TestConstants.TEN)
                  .param("suspendActiveId", TestConstants.ONE.toString())
                  .param("suspendActiveStatus", "suspendActiveStatus").param("reason", "reason"))
          .andExpect(view().name(URLMappingConstants.CHATAK_ADMIN_SEARCH_BLACK_LISTED_CARD_PAGE));
    } catch (Exception e) {
      logger.error("BlackListedCardControllerTest | testChangeBlackListedCardStatus | Exception ",
          e);
    }
  }

  @Test
  public void testChangeBlackListedCardStatusElse() {
    addBlackListedCardResponse = new BlackListedCardResponse();
    addBlackListedCardResponse.setBlackListedCardRequest(blacklistedcardRequest);
    addBlackListedCardResponse.setErrorCode(ActionErrorCode.ERROR_CODE_01);
    try {
      Mockito
          .when(blackListedCardService.changeBlackListedCardStatus(
              Matchers.any(BlackListedCardRequest.class), Matchers.anyString()))
          .thenReturn(addBlackListedCardResponse);
      mockMvc
          .perform(
              post("/" + URLMappingConstants.CHATAK_ADMIN_ACTIVATION_SUSPENTION_BLACK_LISTD_CARD)
                  .sessionAttr(TestConstants.EXISTING_FEATURES, TestConstants.EXISTING_FEATURES)
                  .sessionAttr(TestConstants.BLACK_LISTED_CARD_INFO, searchBlackListedCardRequest)
                  .sessionAttr("TestConstants.PAGE_NUMBER", TestConstants.TEN)
                  .param("suspendActiveId", TestConstants.ONE.toString())
                  .param("suspendActiveStatus", "suspendActiveStatus").param("reason", "reason"))
          .andExpect(view().name(URLMappingConstants.CHATAK_ADMIN_SEARCH_BLACK_LISTED_CARD_PAGE));
    } catch (Exception e) {
      logger.error(
          "BlackListedCardControllerTest | testChangeBlackListedCardStatusElse | Exception ", e);
    }
  }

  @Test
  public void testChangeBlackListedCardStatusException() {
    try {
      Mockito
          .when(blackListedCardService.changeBlackListedCardStatus(
              Matchers.any(BlackListedCardRequest.class), Matchers.anyString()))
          .thenThrow(nullPointerException);
      mockMvc
          .perform(
              post("/" + URLMappingConstants.CHATAK_ADMIN_ACTIVATION_SUSPENTION_BLACK_LISTD_CARD)
                  .sessionAttr(TestConstants.EXISTING_FEATURES, TestConstants.EXISTING_FEATURES)
                  .sessionAttr(TestConstants.BLACK_LISTED_CARD_INFO, searchBlackListedCardRequest)
                  .sessionAttr("TestConstants.PAGE_NUMBER", TestConstants.TEN)
                  .param("suspendActiveId", TestConstants.ONE.toString())
                  .param("suspendActiveStatus", "suspendActiveStatus").param("reason", "reason"))
          .andExpect(view().name(URLMappingConstants.CHATAK_ADMIN_SEARCH_BLACK_LISTED_CARD_PAGE));
    } catch (Exception e) {
      logger.error(
          "BlackListedCardControllerTest | testChangeBlackListedCardStatusException | Exception ",
          e);   
    }
  }

  @Test
  public void testValidateuniqueCardNumber() {
    try {
      Mockito.when(blackListedCardService.validateCardNumber((BigInteger) Matchers.any()))
          .thenReturn(cardNumberResponse);
      mockMvc.perform(get("/" + URLMappingConstants.CHATAK_ADMIN_CARDNUMBER_VALIDATE)
          .param("cardId", TestConstants.ONE.toString()));
    } catch (Exception e) {
      logger.error("BlackListedCardControllerTest | testValidateuniqueCardNumber | Exception ", e);
    }
  }

}
