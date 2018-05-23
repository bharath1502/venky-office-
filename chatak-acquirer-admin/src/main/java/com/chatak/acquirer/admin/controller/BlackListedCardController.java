package com.chatak.acquirer.admin.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.ws.rs.FormParam;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.chatak.acquirer.admin.constants.FeatureConstants;
import com.chatak.acquirer.admin.constants.URLMappingConstants;
import com.chatak.acquirer.admin.controller.model.ExportDetails;
import com.chatak.acquirer.admin.exception.ChatakAdminException;
import com.chatak.acquirer.admin.service.BlackListedCardService;
import com.chatak.acquirer.admin.util.ExportUtil;
import com.chatak.acquirer.admin.util.JsonUtil;
import com.chatak.acquirer.admin.util.PaginationUtil;
import com.chatak.acquirer.admin.util.StringUtil;
import com.chatak.pg.bean.CardNumberResponse;
import com.chatak.pg.constants.ActionErrorCode;
import com.chatak.pg.enums.ExportType;
import com.chatak.pg.model.BlackListedCard;
import com.chatak.pg.user.bean.BlackListedCardRequest;
import com.chatak.pg.user.bean.BlackListedCardResponse;
import com.chatak.pg.util.Constants;

@SuppressWarnings({"rawtypes", "unchecked"})
@Controller
public class BlackListedCardController implements URLMappingConstants {

  @Autowired
  MessageSource messageSource;

  @Autowired
  private BlackListedCardService blackListedCardService;

  private static Logger logger = Logger.getLogger(BlackListedCardController.class);

  private static final String SHOW_EDIT_BLACK_LISTED_CARD = ":: showEditBlackListedCard method";

  private static final String CLASS_NAME = "BlackListedCardController";

  private static final String ERROR = "ERROR:: ";

  private static final String ENTERING = "Entering:: ";

  private static final String EXITING = "Exiting:: ";

  @RequestMapping(value = CHATAK_ADMIN_CREATE_BLACK_LISTED_CARD_PAGE, method = RequestMethod.GET)
  public ModelAndView showCreateBlackListedCardPage(Map model, HttpSession session) {
    logger.info("Entering:: BlackListedCardController:: showCreateBlackListedCardPage method");
    ModelAndView modelAndView = new ModelAndView(CHATAK_ADMIN_CREATE_BLACK_LISTED_CARD_PAGE);

    String existingFeature = (String) session.getAttribute(Constants.EXISTING_FEATURES);
    if (!existingFeature
        .contains(FeatureConstants.ADMIN_SERVICE_BLACKLIST_CARD_CREATE_FEATURE_ID)) {
      session.invalidate();
      modelAndView.setViewName(INVALID_REQUEST_PAGE);
      return modelAndView;
    }

    modelAndView.addObject(Constants.ERROR, null);
    session.setAttribute(Constants.ERROR, null);
    BlackListedCard blackListedCard = new BlackListedCard();
    model.put(Constants.BLACK_LISTED_CARD, blackListedCard);
    logger.info("Exiting:: BlackListedCardController:: showCreateBlackListedCardPage method");
    return modelAndView;
  }

  @RequestMapping(value = CHATAK_ADMIN_CREATE_BLACK_LISTED_CARD, method = RequestMethod.POST)
  public ModelAndView blackListedCard(HttpServletRequest request, HttpServletResponse response,
      BlackListedCard blackListedCard, BindingResult bindingResult, Map model,
      HttpSession session) {
    logger.info("Entering:: BlackListedCardController:: blackListedCard method");
    ModelAndView modelAndView = new ModelAndView(CHATAK_ADMIN_SEARCH_BLACK_LISTED_CARD_PAGE);
    String existingFeature = (String) session.getAttribute(Constants.EXISTING_FEATURES);
    if (!existingFeature
        .contains(FeatureConstants.ADMIN_SERVICE_BLACKLIST_CARD_CREATE_FEATURE_ID)) {
      session.invalidate();
      modelAndView.setViewName(INVALID_REQUEST_PAGE);
      return modelAndView;
    }
    modelAndView.addObject(Constants.ERROR, null);
    try {
      Long userid = (Long) session.getAttribute(Constants.LOGIN_USER_ID);
      BlackListedCardResponse addBlackListedCardResponse =
          blackListedCardService.addBlackListedCardInfo(blackListedCard, String.valueOf(userid));
      if (null != addBlackListedCardResponse
          && addBlackListedCardResponse.getErrorCode().equals(ActionErrorCode.ERROR_CODE_00)) {
        modelAndView = showSearchBlackListedCardPage(request, response,
            new BlackListedCardRequest(), bindingResult, model, session);
        modelAndView.addObject(Constants.SUCESS, messageSource.getMessage(
            "chatak.blacklistedcard.create.success", null, LocaleContextHolder.getLocale()));
      } else if (null != addBlackListedCardResponse
          && addBlackListedCardResponse.getErrorCode().equals(ActionErrorCode.ERROR_CODE_Z5)) {
        modelAndView = showSearchBlackListedCardPage(request, response,
            new BlackListedCardRequest(), bindingResult, model, session);
        modelAndView.addObject(Constants.ERROR, messageSource
            .getMessage(ActionErrorCode.ERROR_CODE_Z5, null, LocaleContextHolder.getLocale()));
      } else {
        modelAndView.setViewName(CHATAK_ADMIN_SEARCH_BLACK_LISTED_CARD_PAGE);
        modelAndView.addObject(Constants.ERROR, messageSource
            .getMessage(ActionErrorCode.ERROR_CODE_Z5, null, LocaleContextHolder.getLocale()));
      }
    } catch (Exception e) {
      logger.error("ERROR:: BlackListedCardController:: blackListedCard method", e);
      modelAndView.addObject(Constants.ERROR, messageSource
          .getMessage(Constants.CHATAK_GENERAL_ERROR, null, LocaleContextHolder.getLocale()));
    }
    model.put(Constants.BLACK_LISTED_CARD, new BlackListedCard());
    logger.info("Exiting:: BlackListedCardController:: blackListedCard method");
    return modelAndView;
  }

  @RequestMapping(value = CHATAK_ADMIN_SEARCH_BLACK_LISTED_CARD_PAGE, method = RequestMethod.GET)
  public ModelAndView showSearchBlackListedCardPage(HttpServletRequest request,
      HttpServletResponse response, BlackListedCardRequest searchBlackListedCardRequest,
      BindingResult bindingResult, Map model, HttpSession session) {
    logger.info("Entering:: BlackListedCardController:: showSearchBlackListedCardPage method");
    ModelAndView modelAndView = new ModelAndView(CHATAK_ADMIN_SEARCH_BLACK_LISTED_CARD_PAGE);
    String existingFeature = (String) session.getAttribute(Constants.EXISTING_FEATURES);
    if (!existingFeature.contains(FeatureConstants.ADMIN_SERVICE_BLACKLIST_CARD_FEATURE_ID)) {
      session.invalidate();
      modelAndView.setViewName(INVALID_REQUEST_PAGE);
      return modelAndView;
    }

    modelAndView.addObject(Constants.ERROR, null);
    session.setAttribute(Constants.ERROR, null);
    session.setAttribute(Constants.BLACK_LISTED_CARD_INFO, searchBlackListedCardRequest);
    searchBlackListedCardRequest.setPageSize(Constants.INITIAL_ENTITIES_PORTAL_DISPLAY_SIZE);
    searchBlackListedCardRequest.setPageIndex(Constants.ONE);
    String succMsg = request.getParameter("succMsg");
    try {
      List<BlackListedCardRequest> searchBlackListedCardRequestList = new ArrayList<>();
      modelAndView.addObject(Constants.BLACK_LISTED_CARD_INFO, searchBlackListedCardRequestList);

      modelAndView.addObject(Constants.SUCESS, succMsg);
    } catch (Exception e) {
      modelAndView.addObject(Constants.ERROR, messageSource
          .getMessage(Constants.CHATAK_GENERAL_ERROR, null, LocaleContextHolder.getLocale()));
      logger.error("ERROR:: BlackListedCardController:: showSearchBlackListedCardPage method", e);
    }
    modelAndView.addObject("flag", false);
    model.put(Constants.BLACK_LISTED_CARD, searchBlackListedCardRequest);
    logger.info("Exiting:: BlackListedCardController:: showSearchBlackListedCardPage method");
    return modelAndView;
  }

  @RequestMapping(value = CHATAK_ADMIN_SEARCH_BLACK_LISTED_CARD, method = RequestMethod.POST)
  public ModelAndView searchBlackListedCardInfo(HttpServletRequest request,
      HttpServletResponse response, BlackListedCardRequest searchBlackListedCardRequest,
      BindingResult bindingResult, Map model, HttpSession session) {
    logger.info("Entering:: BlackListedCardController:: searchBlackListedCardInfo method");
    ModelAndView modelAndView = new ModelAndView(CHATAK_ADMIN_SEARCH_BLACK_LISTED_CARD_PAGE);
    String existingFeature = (String) session.getAttribute(Constants.EXISTING_FEATURES);
    if (!existingFeature.contains(FeatureConstants.ADMIN_SERVICE_BLACKLIST_CARD_FEATURE_ID)) {
      session.invalidate();
      modelAndView.setViewName(INVALID_REQUEST_PAGE);
      return modelAndView;
    }
    modelAndView.addObject(Constants.ERROR, null);
    session.setAttribute(Constants.ERROR, null);
    searchBlackListedCardRequest.setPageIndex(Constants.ONE);
    session.setAttribute(Constants.BLACK_LISTED_CARD_INFO, searchBlackListedCardRequest);
    try {
      BlackListedCardResponse searchBlackListedCardResponse =
          blackListedCardService.searchBlackListedCardInformation(searchBlackListedCardRequest);
      List<BlackListedCardRequest> searchBlackListedCardRequestList = new ArrayList<>();
      if (searchBlackListedCardResponse != null
          && !CollectionUtils.isEmpty(searchBlackListedCardResponse.getBlackListedCardRequest())) {
        searchBlackListedCardRequestList =
            searchBlackListedCardResponse.getBlackListedCardRequest();
        modelAndView.addObject("pageSize", searchBlackListedCardRequest.getPageSize());
        modelAndView = PaginationUtil.getPagenationModel(modelAndView,
            searchBlackListedCardResponse.getTotalNoOfRows());
        session.setAttribute(Constants.PAGE_NUMBER, Constants.ONE);
      }
      if (searchBlackListedCardResponse != null) {
        session.setAttribute(Constants.TOTAL_RECORDS,
            searchBlackListedCardResponse.getTotalNoOfRows());
        modelAndView.addObject(Constants.BLACK_LISTED_CARD_INFO, searchBlackListedCardRequestList);
      }
    } catch (Exception e) {
      modelAndView.addObject(Constants.ERROR, messageSource
          .getMessage(Constants.CHATAK_GENERAL_ERROR, null, LocaleContextHolder.getLocale()));
      logger.error("ERROR:: BlackListedCardController:: searchBlackListedCardInfo method", e);
    }
    modelAndView.addObject("flag", true);
    model.put(Constants.BLACK_LISTED_CARD, searchBlackListedCardRequest);
    logger.info("Exiting:: BlackListedCardController:: searchBlackListedCardInfo method");
    return modelAndView;
  }

  @RequestMapping(value = CHATAK_ADMIN_SHOW_EDIT_BLACKLISTED_CARD, method = RequestMethod.POST)
  public ModelAndView showEditBlackListedCard(HttpServletRequest request,
      HttpServletResponse response,
      @FormParam("getBlackListedCardId") final Long getBlackListedCardId, HttpSession session,
      Map model) {
    logger.info(ENTERING + CLASS_NAME + SHOW_EDIT_BLACK_LISTED_CARD);
    ModelAndView modelAndView = new ModelAndView(CHATAK_ADMIN_EDIT_BLACK_LISTED_CARD);
    String existingFeature = (String) session.getAttribute(Constants.EXISTING_FEATURES);
    if (!existingFeature.contains(FeatureConstants.ADMIN_SERVICE_BLACKLIST_CARD_EDIT_FEATURE_ID)) {
      session.invalidate();
      modelAndView.setViewName(INVALID_REQUEST_PAGE);
      return modelAndView;
    }

    modelAndView.addObject(Constants.ERROR, null);
    modelAndView.addObject(Constants.SUCESS, null);
    try {
      BlackListedCardRequest blackListedCardInfo =
          blackListedCardService.getBlackListedCardInfoById(getBlackListedCardId);
      model.put(Constants.BLACK_LISTED_CARD, blackListedCardInfo);
    } catch (Exception e) {
      logger.error(ERROR + CLASS_NAME + SHOW_EDIT_BLACK_LISTED_CARD, e);
      modelAndView.addObject(Constants.ERROR, messageSource
          .getMessage(Constants.CHATAK_GENERAL_ERROR, null, LocaleContextHolder.getLocale()));
    }
    logger.info(EXITING + CLASS_NAME + SHOW_EDIT_BLACK_LISTED_CARD);
    return modelAndView;
  }

  @RequestMapping(value = CHATAK_ADMIN_SHOW_VIEW_BLACKLISTED_CARD, method = RequestMethod.POST)
  public ModelAndView showViewBlackListedCard(HttpServletRequest request,
      HttpServletResponse response,
      @FormParam("getBlackListedCardId") final Long getBlackListedCardId, HttpSession session,
      Map model) {
    logger.info("Entering :: BlackListedCardController :: showEditBlackListedCard method ");
    ModelAndView modelAndView = new ModelAndView(CHATAK_ADMIN_VIEW_BLACKLISTED_CARD);
    modelAndView.addObject(Constants.ERROR, null);
    modelAndView.addObject(Constants.SUCESS, null);
    try {
      BlackListedCardRequest blackListedCardInfo =
          blackListedCardService.getBlackListedCardInfoById(getBlackListedCardId);
      model.put(Constants.BLACK_LISTED_CARD, blackListedCardInfo);
    } catch (Exception e) {
      logger.error("ERROR:: BlackListedCardController:: showEditSwitch method", e);
      modelAndView.addObject(Constants.ERROR, messageSource
          .getMessage(Constants.CHATAK_GENERAL_ERROR, null, LocaleContextHolder.getLocale()));
    }
    logger.info("Exiting :: BlackListedCardController :: showEditBlackListedCard method");
    return modelAndView;
  }

  @RequestMapping(value = CHATAK_ADMIN_UPDATE_BLACK_LISTD_CARD, method = RequestMethod.POST)
  public ModelAndView updateBlackListedCard(HttpServletRequest request,
      HttpServletResponse response, BlackListedCardRequest updateBlackListedCardRequest,
      BindingResult bindingResult, HttpSession session, Map model) {
    logger.info("Entering :: BlackListedCardController :: updateBlackListedCard method");
    ModelAndView modelAndView = new ModelAndView(CHATAK_ADMIN_SEARCH_BLACK_LISTED_CARD_PAGE);
    String existingFeature = (String) session.getAttribute(Constants.EXISTING_FEATURES);
    if (!existingFeature.contains(FeatureConstants.ADMIN_SERVICE_BLACKLIST_CARD_EDIT_FEATURE_ID)) {
      session.invalidate();
      modelAndView.setViewName(INVALID_REQUEST_PAGE);
      return modelAndView;
    }
    modelAndView.addObject(Constants.ERROR, null);
    session.setAttribute(Constants.ERROR, null);
    updateBlackListedCardRequest.setPageSize(Constants.MAX_ENTITIES_PORTAL_DISPLAY_SIZE);
    updateBlackListedCardRequest.setPageIndex(Constants.ONE);
    try {
      Long userid = (Long) session.getAttribute(Constants.LOGIN_USER_ID);
      BlackListedCardResponse updateBlackListedCardResponse = blackListedCardService
          .updateBlackListedCardInformation(updateBlackListedCardRequest, String.valueOf(userid));
      if (updateBlackListedCardResponse != null
          && updateBlackListedCardResponse.getErrorCode().equals(ActionErrorCode.ERROR_CODE_00)) {
        modelAndView = showSearchBlackListedCardPage(request, response,
            new BlackListedCardRequest(), bindingResult, model, session);
        modelAndView.addObject(Constants.SUCESS,
            messageSource.getMessage("chatak.admin.updateblacklistedcard.success.message", null,
                LocaleContextHolder.getLocale()));
      } else {
        modelAndView.addObject(Constants.ERROR, messageSource
            .getMessage(ActionErrorCode.ERROR_CODE_01, null, LocaleContextHolder.getLocale()));
      }
    } catch (Exception e) {
      logger.error("ERROR:: BlackListedCardController:: updateBlackListedCard method", e);
      modelAndView.addObject(Constants.ERROR, messageSource
          .getMessage(Constants.CHATAK_GENERAL_ERROR, null, LocaleContextHolder.getLocale()));
    }
    logger.info("Exiting:: BlackListedCardController:: updateBlackListedCard method");
    return modelAndView;
  }

  @RequestMapping(value = CHATAK_BLACK_LISTED_CARD_PAGINATION, method = RequestMethod.POST)
  public ModelAndView getPaginationList(final HttpSession session,
      @FormParam("pageNumber") final Integer pageNumber,
      @FormParam("totalRecords") final Integer totalRecords, Map model) {
    logger.info("Entering:: BlackListedCardController:: getPaginationList method");
    ModelAndView modelAndView = new ModelAndView(CHATAK_ADMIN_SEARCH_BLACK_LISTED_CARD_PAGE);
    try {
      BlackListedCardRequest blackListedCardInfo =
          (BlackListedCardRequest) session.getAttribute(Constants.BLACK_LISTED_CARD_INFO);
      model.put(Constants.BLACK_LISTED_CARD, blackListedCardInfo);
      blackListedCardInfo.setPageIndex(pageNumber);
      blackListedCardInfo.setNoOfRecords(totalRecords);
      blackListedCardInfo.setPageSize(blackListedCardInfo.getPageSize());
      BlackListedCardResponse searchResponse =
          blackListedCardService.searchBlackListedCardInformation(blackListedCardInfo);
      List<BlackListedCardRequest> blackListedCardSearchList = new ArrayList<>();
      if (searchResponse != null
          && !CollectionUtils.isEmpty(searchResponse.getBlackListedCardRequest())) {
        blackListedCardSearchList = searchResponse.getBlackListedCardRequest();
        modelAndView.addObject("pageSize", blackListedCardInfo.getPageSize());
        modelAndView = PaginationUtil.getPagenationModelSuccessive(modelAndView, pageNumber,
            searchResponse.getTotalNoOfRows());
        session.setAttribute(Constants.PAGE_NUMBER, pageNumber);
        session.setAttribute(Constants.TOTAL_RECORDS, totalRecords);
      }
      modelAndView.addObject(Constants.BLACK_LISTED_CARD_INFO, blackListedCardSearchList);
    } catch (Exception e) {
      logger.error("ERROR:: BlackListedCardController:: getPaginationList method", e);
      modelAndView.addObject(Constants.ERROR, messageSource
          .getMessage(Constants.CHATAK_GENERAL_ERROR, null, LocaleContextHolder.getLocale()));
    }
    logger.info("Exiting:: BlackListedCardController:: getPaginationList method");
    return modelAndView;
  }

  @RequestMapping(value = CHATAK_ADMIN_BLACK_LISTED_CARD_REPORT, method = RequestMethod.POST)
  public ModelAndView downloadBlackListedCardReport(HttpSession session, HttpServletRequest request,
      @FormParam("downLoadPageNumber") final Integer downLoadPageNumber,
      HttpServletResponse response, @FormParam("downloadType") final String downloadType,
      @FormParam("totalRecords") final Integer totalRecords,
      @FormParam("downloadAllRecords") final boolean downloadAllRecords) {
    logger.info("Entering:: BlackListedCardController:: downloadBlackListedCardReport method");
    ModelAndView modelAndView = new ModelAndView(CHATAK_ADMIN_SEARCH_BLACK_LISTED_CARD_PAGE);
    try {
      BlackListedCardRequest blackListedCardInfo = null;
      BlackListedCardResponse searchResponse = null;
      blackListedCardInfo =
          (BlackListedCardRequest) session.getAttribute(Constants.BLACK_LISTED_CARD_INFO);
      blackListedCardInfo.setPageIndex(downLoadPageNumber);
      Integer pageSize = blackListedCardInfo.getPageSize();

      if (downloadAllRecords) {
        blackListedCardInfo.setPageIndex(Constants.ONE);
        blackListedCardInfo.setPageSize(totalRecords);
      }
      searchResponse = blackListedCardService.searchBlackListedCardInformation(blackListedCardInfo);
      ExportDetails exportDetails = new ExportDetails();

      if (null != searchResponse
          && StringUtil.isListNotNullNEmpty(searchResponse.getBlackListedCardRequest())) {
        List<BlackListedCardRequest> list = searchResponse.getBlackListedCardRequest();
        if (Constants.PDF_FILE_FORMAT.equalsIgnoreCase(downloadType)) {
        	exportDetails.setExportType(ExportType.PDF);

        } else if (Constants.XLS_FILE_FORMAT.equalsIgnoreCase(downloadType)) {
        	exportDetails.setExportType(ExportType.XLS);
			exportDetails.setExcelStartRowNumber(Integer.parseInt("5"));
        }
        setExportDetailsDataForDownloadBlackListedCardReport(list, exportDetails);	
	 	ExportUtil.exportData(exportDetails, response, messageSource);
      }
      blackListedCardInfo.setPageSize(pageSize);
    } catch (Exception e) {
      modelAndView.addObject(Constants.ERROR, messageSource
          .getMessage(Constants.CHATAK_GENERAL_ERROR, null, LocaleContextHolder.getLocale()));
      logger.error("ERROR:: BlackListedCardController:: downloadBlackListedCardReport method", e);
    }
    logger.info("Exiting:: BlackListedCardController:: downloadBlackListedCardReport method");
    return null;
  }
  
  private void setExportDetailsDataForDownloadBlackListedCardReport(
      List<BlackListedCardRequest> blackListedCardData, ExportDetails exportDetails) {
    exportDetails.setReportName("BlackListedCard_");
    exportDetails.setHeaderMessageProperty("chatak.header.black.listed.card.messages");

    exportDetails.setHeaderList(getBlackListedCardHeaderList());
    exportDetails.setFileData(getBlackListedCardFileData(blackListedCardData));
  }

  @RequestMapping(value = CHATAK_ADMIN_ACTIVATION_SUSPENTION_BLACK_LISTD_CARD,
      method = RequestMethod.POST)
  public ModelAndView changeBlackListedCardStatus(final HttpSession session,
      @FormParam("suspendActiveId") final Long cardId,
      @FormParam("suspendActiveStatus") final String cardStatus,
      @FormParam("reason") final String reason, Map<String, Serializable> model,
      HttpServletRequest request, HttpServletResponse response) {
    logger.info("Entering:: BlackListedCardController:: changeBlackListedCardStatus method");

    ModelAndView modelAndView = new ModelAndView(CHATAK_ADMIN_SEARCH_BLACK_LISTED_CARD_PAGE);
    String existingFeature = (String) session.getAttribute(Constants.EXISTING_FEATURES);
    if (!existingFeature.contains(FeatureConstants.ADMIN_SERVICE_BLACKLIST_CARD_FEATURE_ID)) {
      session.invalidate();
      modelAndView.setViewName(INVALID_REQUEST_PAGE);
      return modelAndView;
    }
    try {
      BlackListedCardRequest blackListedCardRequest = new BlackListedCardRequest();
      blackListedCardRequest.setId(cardId);
      blackListedCardRequest.setReason(reason);
      BlackListedCardResponse blackListedCardResponse =
          blackListedCardService.changeBlackListedCardStatus(blackListedCardRequest, cardStatus);
      modelAndView = getPaginationList(session,
          StringUtil.isNull((Integer) session.getAttribute(Constants.PAGE_NUMBER)) ? 1
              : (Integer) session.getAttribute(Constants.PAGE_NUMBER),
          (Integer) session.getAttribute(Constants.TOTAL_RECORDS), model);
      if (ActionErrorCode.ERROR_CODE_00.equals(blackListedCardResponse.getErrorCode())) {
        model.put(Constants.SUCESS,
            messageSource.getMessage("chatak.black.listed.card.sucess.activate.suspend", null,
                LocaleContextHolder.getLocale()));
      } else {
        model.put(Constants.ERROR, messageSource.getMessage(Constants.CHATAK_GENERAL_ERROR, null,
            LocaleContextHolder.getLocale()));
      }
    } catch (Exception e) {
      logger.error("ERROR:: BlackListedCardController:: changeBlackListedCardStatus method", e);
      model.put(Constants.ERROR, messageSource.getMessage(Constants.CHATAK_GENERAL_ERROR, null,
          LocaleContextHolder.getLocale()));
    }
    logger.info("Exiting:: BlackListedCardController:: changeBlackListedCardStatus method");
    return modelAndView;
  }

  @RequestMapping(value = CHATAK_ADMIN_CARDNUMBER_VALIDATE, method = RequestMethod.GET)
  public @ResponseBody String validateuniqueCardNumber(Map model, HttpServletRequest request,
      HttpServletResponse response, HttpSession session) {
    logger.info("Entering :: BlackListedCardController :: validateuniqueCardNumber method");

    ModelAndView modelAndView = new ModelAndView(CHATAK_ADMIN_CREATE_BLACK_LISTED_CARD_PAGE);
    modelAndView.addObject(Constants.ERROR, null);
    Long cardNum = Long.parseLong(request.getParameter("cardId"));
    CardNumberResponse cardNumberResponse = null;
    try {
      cardNumberResponse = blackListedCardService.validateCardNumber(cardNum);
      modelAndView.addObject(Constants.ERROR, cardNumberResponse.getErrorMessage());
      return JsonUtil.convertObjectToJSON(cardNumberResponse);
    } catch (Exception e) {
      cardNumberResponse = new CardNumberResponse();
      cardNumberResponse.setErrorCode(ActionErrorCode.ERROR_CODE_Z5);
      cardNumberResponse
          .setErrorMessage(ActionErrorCode.getInstance().getMessage(ActionErrorCode.ERROR_CODE_Z5));
      logger.error("ERROR:: BlackListedCardController:: validateuniqueCardNumber method", e);
      modelAndView.addObject(Constants.ERROR, messageSource
          .getMessage(Constants.CHATAK_GENERAL_ERROR, null, LocaleContextHolder.getLocale()));
      try {
        return JsonUtil.convertObjectToJSON(cardNumberResponse);
      } catch (ChatakAdminException e1) {
        logger.error(
            "ERROR:: BlackListedCardController:: validateuniqueCardNumber ::convertObjectToJSON",
            e1);
      }
    }
    logger.info("Exiting:: BlackListedCardController:: validateuniqueCardNumber method");
    return null;
  }
  
  private List<String> getBlackListedCardHeaderList() {
    String[] headerArr = {
        messageSource.getMessage("reports.label.transactions.cardnumber", null,
            LocaleContextHolder.getLocale()),
        messageSource.getMessage("reports.label.transactions.status", null,
            LocaleContextHolder.getLocale())};
    return new ArrayList<String>(Arrays.asList(headerArr));
  }

  private static List<Object[]> getBlackListedCardFileData(
      List<BlackListedCardRequest> blackListedCardData) {
    List<Object[]> fileData = new ArrayList<Object[]>();

    for (BlackListedCardRequest blcData : blackListedCardData) {

      Object[] rowData = {blcData.getCardNumber(), blcData.getStatusDisp()

      };
      fileData.add(rowData);
    }

    return fileData;
  }
}
