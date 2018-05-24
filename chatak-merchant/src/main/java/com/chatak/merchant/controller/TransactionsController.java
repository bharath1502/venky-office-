package com.chatak.merchant.controller;

import java.util.ArrayList;
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

import com.chatak.merchant.constants.URLMappingConstants;
import com.chatak.merchant.exception.ChatakPayException;
import com.chatak.merchant.model.GetMerchantDetailsResponse;
import com.chatak.merchant.model.GetTransactionResponse;
import com.chatak.merchant.model.TransactionListResponse;
import com.chatak.merchant.service.AccountService;
import com.chatak.merchant.service.RestPaymentService;
import com.chatak.merchant.service.TransactionService;
import com.chatak.merchant.util.JsonUtil;
import com.chatak.merchant.util.PaginationUtil;
import com.chatak.pg.acq.dao.model.PGAccount;
import com.chatak.pg.constants.AccountTransactionCode;
import com.chatak.pg.constants.FeatureConstants;
import com.chatak.pg.constants.PGConstants;
import com.chatak.pg.model.AccountTransactionDTO;
import com.chatak.pg.model.SettlementActionDTOList;
import com.chatak.pg.model.SettlemetActionDTO;
import com.chatak.pg.user.bean.GetTransactionsListRequest;
import com.chatak.pg.user.bean.GetTransactionsListResponse;
import com.chatak.pg.user.bean.Transaction;
import com.chatak.pg.util.Constants;

/**
 * << Add Comments Here >>
 *
 * @author Girmiti Software
 * @date 07-Jan-2015 4:59:28 PM
 * @version 1.0
 */
@SuppressWarnings({"rawtypes", "unchecked"})
@Controller
public class TransactionsController implements URLMappingConstants {

  private static Logger logger = Logger.getLogger(TransactionsController.class);

  @Autowired
  MessageSource messageSource;

  @Autowired
  private TransactionService transactionService;

  @Autowired
  private RestPaymentService paymentService;

  @Autowired
  AccountService accountService;

  /**
   * Method to show search transaction page
   * 
   * @param request
   * @param response
   * @param transaction
   * @param bindingResult
   * @param model
   * @param session
   * @return
   */
  @RequestMapping(value = CHATAK_MERCHANT_SEARCH_TRANSACTION_PAGE, method = RequestMethod.GET)
  public ModelAndView showSearchTransactionPage(HttpServletRequest request,
      HttpServletResponse response, GetTransactionsListRequest transaction,
      BindingResult bindingResult, Map model, HttpSession session) {
    logger.info("Entering:: TransactionsController:: showSearchMerchantPage method");
    ModelAndView modelAndView = new ModelAndView(CHATAK_MERCHANT_SEARCH_TRANSACTION_PAGE);
    String existingFeature = (String) session.getAttribute(Constants.EXISTING_FEATURES);
    if (!existingFeature
        .contains(FeatureConstants.MERCHANT_SERVICE_REPORTS_TRANSACTION_FEATURE_ID)) {
      session.invalidate();
      modelAndView.setViewName(INVALID_REQUEST_PAGE);
      return modelAndView;
    }
    modelAndView.addObject(Constants.ERROR, null);
    session.setAttribute(Constants.ERROR, null);
    transaction.setPageIndex(Constants.ONE);
    transaction.setPageSize(Constants.MAX_ENTITIES_PORTAL_DISPLAY_SIZE);
    if (null != request.getParameter("req-from")
        && request.getParameter("req-from").trim().equals("dash-board")) {
      transaction.setToGetCurrentDayTxns(false);
    } else {
      transaction.setToGetCurrentDayTxns(true);
    }
    session.setAttribute(Constants.TRANSACTIONS_REQ_OBJ_MODEL, transaction);
    TransactionListResponse transactionResponse = new TransactionListResponse();
    List<Transaction> transactionList = new ArrayList<>();
    try {

      session.removeAttribute(PGConstants.BULK_SETTLEMENT_LIST_OBJ);
      session.removeAttribute(PGConstants.BULK_SETTLEMENT_LIST);

      Long merchantId = (Long) session.getAttribute(Constants.LOGIN_USER_MERCHANT_ID);
      if (merchantId != null) {
        GetMerchantDetailsResponse merchantDetailsResponse =
            paymentService.getMerchantIdAndTerminalId(merchantId.toString());
        transaction.setMerchant_code(merchantDetailsResponse.getMerchantId());

        boolean hasAccess =
            transactionService.hasAccessTo(FeatureConstants.VIEW_SUB_MERCHANT_TRANSACTIONS,
                merchantDetailsResponse.getMerchantId(), session);

        transaction.setSubMerchantsTxnsRequired(hasAccess);

        GetTransactionsListResponse transactionsList =
            transactionService.searchTransactions(transaction);
        PGAccount accountDetails =
            accountService.getAccountDetailsByEntityId(merchantDetailsResponse.getMerchantId());

        Long feeBalance =
            transactionService.findMerchantFeeByMerchantId(merchantDetailsResponse.getMerchantId());
        accountDetails.setFeeBalance(feeBalance);

        modelAndView.addObject(Constants.ACCOUNT_DETAILS, accountDetails);
        modelAndView.addObject("currencyAlpha", accountDetails.getCurrency());

        if (transactionsList != null) {
          transactionResponse.setTransactionList(transactionsList.getTransactionList());
          transactionResponse.setErrorCode(transactionsList.getErrorCode());
          transactionResponse.setErrorMessage(transactionsList.getErrorMessage());
          transactionResponse.setTotalNoOfRows(transactionsList.getTotalResultCount());

          modelAndView.addObject(Constants.ALL_TRANSACTIONS_MODEL, transactionList);

        } else {
          modelAndView.addObject(Constants.ALL_TRANSACTIONS_MODEL, new ArrayList<Transaction>());
        }
      }
    } catch (Exception e) {
      modelAndView.addObject(Constants.ALL_TRANSACTIONS_MODEL, new ArrayList<Transaction>());
      modelAndView.addObject(Constants.ERROR,
          messageSource.getMessage(Constants.CHATAK_GENERAL_ERROR, null, LocaleContextHolder.getLocale()));
      logger.error("ERROR:: TransactionsController:: showSearchMerchantPage method", e);
    }
    modelAndView.addObject(Constants.SETTLEMENT_DTO, new SettlemetActionDTO());
    modelAndView.addObject("flag", false);
    model.put(Constants.TRANSACTION, new GetTransactionsListRequest());

    logger.info("Exiting:: TransactionsController:: showSearchMerchantPage method");
    return modelAndView;
  }

  /**
   * Method to show search transaction page
   * 
   * @param request
   * @param response
   * @param transaction
   * @param bindingResult
   * @param model
   * @param session
   * @return
   */
  @RequestMapping(value = CHATAK_MERCHANT_SEARCH_TRANSACTION, method = RequestMethod.POST)
  public ModelAndView searchTransactions(HttpServletRequest request, HttpServletResponse response,
      GetTransactionsListRequest transaction, BindingResult bindingResult, Map model,
      HttpSession session) {
    logger.info("Entering:: TransactionsController:: searchMerchant method");
    ModelAndView modelAndView = new ModelAndView(CHATAK_MERCHANT_SEARCH_TRANSACTION_PAGE);
    String existingFeature = (String) session.getAttribute(Constants.EXISTING_FEATURES);

    if (request.getHeader(Constants.REFERER) == null) {
      session.invalidate();
      modelAndView.setViewName(INVALID_REQUEST_PAGE);
      return modelAndView;
    }

    if (!existingFeature
        .contains(FeatureConstants.MERCHANT_SERVICE_REPORTS_TRANSACTION_FEATURE_ID)) {
      session.invalidate();
      modelAndView.setViewName(INVALID_REQUEST_PAGE);
      return modelAndView;
    }
    modelAndView.addObject(Constants.ERROR, null);
    session.setAttribute(Constants.ERROR, null);

    transaction.setToGetCurrentDayTxns(false);
    transaction.setPageIndex(Constants.ONE);
    transaction.setPageSize(Constants.MAX_ENTITIES_PORTAL_DISPLAY_SIZE);
    session.setAttribute(Constants.TRANSACTIONS_REQ_OBJ_MODEL, transaction);
    List<Transaction> transactionList = new ArrayList<>();
    try {

      session.removeAttribute(PGConstants.BULK_SETTLEMENT_LIST_OBJ);
      session.removeAttribute(PGConstants.BULK_SETTLEMENT_LIST);

      Long merchantId = (Long) session.getAttribute(Constants.LOGIN_USER_MERCHANT_ID);
      GetMerchantDetailsResponse merchantDetailsResponse =
          paymentService.getMerchantIdAndTerminalId(merchantId.toString());
      transaction.setMerchant_code(merchantDetailsResponse.getMerchantId());

      boolean hasAccess =
          transactionService.hasAccessTo(FeatureConstants.VIEW_SUB_MERCHANT_TRANSACTIONS,
              merchantDetailsResponse.getMerchantId(), session);

      transaction.setSubMerchantsTxnsRequired(hasAccess);

      GetTransactionsListResponse transactionResponse =
          transactionService.searchTransactions(transaction);
      GetTransactionsListResponse allTransactionsResponse =
          transactionService.getAllTransactions(merchantDetailsResponse.getMerchantId());
      session.setAttribute(Constants.ALL_TRANSACTIONS_MODEL,
          allTransactionsResponse.getTransactionList());
      PGAccount accountDetails =
          accountService.getAccountDetailsByEntityId(merchantDetailsResponse.getMerchantId());

      Long feeBalance =
          transactionService.findMerchantFeeByMerchantId(merchantDetailsResponse.getMerchantId());
      accountDetails.setFeeBalance(feeBalance);

      modelAndView.addObject(Constants.ACCOUNT_DETAILS, accountDetails);
      modelAndView.addObject("currencyAlpha", accountDetails.getCurrency());
      session.setAttribute(Constants.ACCOUNT_DETAILS, accountDetails);
      if (transactionResponse.getTransactionList() != null
          && !CollectionUtils.isEmpty(transactionResponse.getTransactionList())) {
        transactionList = transactionResponse.getTransactionList();

        modelAndView.addObject(Constants.ALL_TRANSACTIONS_MODEL, transactionList);
        session.setAttribute(Constants.TRANSACTIONS_MODEL, transactionList);
        modelAndView = PaginationUtil.getPagenationModel(modelAndView,
            transactionResponse.getTotalResultCount());
      } else {
        modelAndView.addObject(Constants.ALL_TRANSACTIONS_MODEL, transactionList);
        session.setAttribute(Constants.TRANSACTIONS_MODEL, transactionList);
        modelAndView = PaginationUtil.getPagenationModel(modelAndView,
            transactionResponse.getTotalResultCount());
      }
    } catch (Exception e) {
      modelAndView.addObject(Constants.ALL_TRANSACTIONS_MODEL, new ArrayList<Transaction>());
      modelAndView.addObject(Constants.ERROR,
          messageSource.getMessage(Constants.CHATAK_GENERAL_ERROR, null, LocaleContextHolder.getLocale()));
      logger.error("ERROR:: TransactionsController:: searchMerchant method", e);
    }
    modelAndView.addObject(Constants.SETTLEMENT_DTO, new SettlemetActionDTO());
    modelAndView.addObject("flag", true);
    model.put(Constants.TRANSACTION, transaction);
    logger.info("Exiting:: TransactionsController:: searchMerchant method");
    return modelAndView;
  }

  /**
   * Method used for Pagination Util
   * 
   * @param session
   * @param pageNumber
   * @param model
   * @return
   */
  @RequestMapping(value = CHATAK_MERCHANT_TRANSACTION_PAGINATION, method = RequestMethod.POST)
  public ModelAndView getPaginationList(HttpServletRequest request, final HttpSession session,
      @FormParam("pageNumber") final Integer pageNumber,
      @FormParam("totalRecords") final Integer totalRecords, Map model) {
    logger.info("Entering:: TransactionsController:: getPaginationList method");

    ModelAndView modelAndView = new ModelAndView(CHATAK_MERCHANT_SEARCH_TRANSACTION_PAGE);

    if (request.getHeader(Constants.REFERER) == null) {
      session.invalidate();
      modelAndView.setViewName(INVALID_REQUEST_PAGE);
      return modelAndView;
    }

    try {

      String requestObject = request.getParameter("requestObject");
      String[] removedTxns = request.getParameterValues("removedTxns");

      SettlementActionDTOList actionDTOList = new SettlementActionDTOList();

      transactionService.configureReqObj(request, session, actionDTOList, requestObject,
          removedTxns);

      GetTransactionsListRequest transaction =
          (GetTransactionsListRequest) session.getAttribute(Constants.TRANSACTIONS_REQ_OBJ_MODEL);
      model.put(Constants.TRANSACTION, transaction);
      transaction.setPageIndex(pageNumber);
      transaction.setNoOfRecords(totalRecords);
      transaction.setPageSize(Constants.MAX_ENTITIES_PORTAL_DISPLAY_SIZE);

      Long merchantId = (Long) session.getAttribute(Constants.LOGIN_USER_MERCHANT_ID);
      GetMerchantDetailsResponse merchantDetailsResponse =
          paymentService.getMerchantIdAndTerminalId(merchantId.toString());
      boolean hasAccess =
          transactionService.hasAccessTo(FeatureConstants.VIEW_SUB_MERCHANT_TRANSACTIONS,
              merchantDetailsResponse.getMerchantId(), session);

      transaction.setSubMerchantsTxnsRequired(hasAccess);

      GetTransactionsListResponse transactionsList =
          transactionService.searchTransactions(transaction);

      if (transactionsList != null
          && !CollectionUtils.isEmpty(transactionsList.getTransactionList())) {
        modelAndView = PaginationUtil.getPagenationModelSuccessive(modelAndView, pageNumber,
            transactionsList.getTotalResultCount());
        modelAndView.addObject(Constants.ALL_TRANSACTIONS_MODEL,
            transactionsList.getTransactionList());
        session.setAttribute(Constants.TRANSACTIONS_MODEL, transactionsList.getTransactionList());
      }
    } catch (Exception e) {
      modelAndView.addObject(Constants.ERROR,
          messageSource.getMessage(Constants.CHATAK_GENERAL_ERROR, null, LocaleContextHolder.getLocale()));
      logger.error("ERROR:: TransactionsController:: getPaginationList method", e);
    }
    logger.info("Exiting:: TransactionsController:: getPaginationList method");
    modelAndView.addObject(Constants.SETTLEMENT_DTO, new SettlemetActionDTO());
    return modelAndView;
  }

  @RequestMapping(value = CHATAK_MERCHANT_PROCESSING_TRANSACTIONS, method = RequestMethod.GET)
  public ModelAndView showProcessingTransactions(HttpServletRequest request,
      HttpServletResponse response, HttpSession session, Map model) {

    logger.info("Entering :: TransactionsController :: showProcessingTransactions method");
    ModelAndView modelAndView = new ModelAndView(CHATAK_MERCHANT_PROCESSING_TRANSACTIONS);
    String existingFeature = (String) session.getAttribute(Constants.EXISTING_FEATURES);
    if (!existingFeature
        .contains(FeatureConstants.MERCHANT_SERVICE_REPORTS_TRANSACTION_FEATURE_ID)) {
      session.invalidate();
      modelAndView.setViewName(INVALID_REQUEST_PAGE);
      return modelAndView;
    }
    Long merchantId = (Long) session.getAttribute(Constants.LOGIN_USER_MERCHANT_ID);
    GetMerchantDetailsResponse merchantDetailsResponse;
    GetTransactionsListRequest transaction = new GetTransactionsListRequest();
    TransactionListResponse transactionResponse = new TransactionListResponse();
    List<AccountTransactionDTO> transactionList = new ArrayList<>();
    if (merchantId != null) {

      try {
        merchantDetailsResponse = paymentService.getMerchantIdAndTerminalId(merchantId.toString());
        transaction.setMerchant_code(merchantDetailsResponse.getMerchantId());

        setTxnCodeList(transaction);
        transaction.setPageIndex(Constants.ONE);
        transaction.setPageSize(Constants.MAX_ENTITIES_PORTAL_DISPLAY_SIZE);

        transaction.setSettlementStatus(PGConstants.PG_SETTLEMENT_PROCESSING);
        GetTransactionsListResponse processingTxnList =
            transactionService.searchAccountTransactions(transaction);

        if (null != processingTxnList && null != processingTxnList.getAccountTransactionList()) {

          setErrorCodeMessageNoOfRecords(session, modelAndView, transactionResponse, processingTxnList);

          modelAndView = PaginationUtil.getPagenationModel(modelAndView,
              processingTxnList.getTotalResultCount());

        } else {
          modelAndView.addObject(Constants.PROCESSING_TXN_LIST, transactionList);
          session.setAttribute(Constants.PROCESSING_TXN_LIST, transactionList);
        }

      } catch (Exception exp) {
        logger.error("Error :: TransactionsController :: showProcessingTransactions method", exp);
        modelAndView.setViewName(INVALID_REQUEST_PAGE);
        return modelAndView;
      }
    }

    session.setAttribute(Constants.ERROR, null);
    session.setAttribute(Constants.SUCESS, null);
    logger.info("Exiting :: TransactionsController :: showProcessingTransactions method");
    return modelAndView;
  }

  private void setErrorCodeMessageNoOfRecords(HttpSession session, ModelAndView modelAndView,
		TransactionListResponse transactionResponse, GetTransactionsListResponse processingTxnList) {
	List<AccountTransactionDTO> transactionList;
	transactionResponse.setAccountTxnList(processingTxnList.getAccountTransactionList());
	transactionResponse.setErrorCode(processingTxnList.getErrorCode());
	transactionResponse.setErrorMessage(processingTxnList.getErrorMessage());
	transactionResponse.setTotalNoOfRows(processingTxnList.getTotalResultCount());
	transactionList = transactionResponse.getAccountTxnList() != null
	    ? transactionResponse.getAccountTxnList() : new ArrayList<AccountTransactionDTO>();
	modelAndView.addObject(Constants.PROCESSING_TXN_LIST, transactionList);
	session.setAttribute(Constants.PROCESSING_TXN_LIST, transactionList);
  }

  @RequestMapping(value = CHATAK_MERCHANT_PROCESSING_TRANSACTIONS_PAGINATION,
      method = RequestMethod.POST)
  public ModelAndView processingTransactionsPagination(HttpServletRequest request,
      final HttpSession session, @FormParam("pageNumber") final Integer pageNumber,
      @FormParam("totalRecords") final Integer totalRecords, Map model) {

    logger.info("Entering :: TransactionsController :: processingTransactionsPagination method");
    ModelAndView modelAndView = new ModelAndView(CHATAK_MERCHANT_PROCESSING_TRANSACTIONS);

    if (request.getHeader(Constants.REFERER) == null) {
      session.invalidate();
      modelAndView.setViewName(INVALID_REQUEST_PAGE);
      return modelAndView;
    }

    Long merchantId = (Long) session.getAttribute(Constants.LOGIN_USER_MERCHANT_ID);
    TransactionListResponse transactionResponse = new TransactionListResponse();
    GetMerchantDetailsResponse merchantDetailsResponse;
    GetTransactionsListRequest transaction = new GetTransactionsListRequest();
    List<AccountTransactionDTO> transactionList = new ArrayList<>();

    if (merchantId != null) {

      try {
        transaction.setPageIndex(pageNumber);
        transaction.setNoOfRecords(totalRecords);
        transaction.setPageSize(Constants.MAX_ENTITIES_PORTAL_DISPLAY_SIZE);

        merchantDetailsResponse = paymentService.getMerchantIdAndTerminalId(merchantId.toString());
        transaction.setMerchant_code(merchantDetailsResponse.getMerchantId());

        setTxnCodeList(transaction);

        transaction.setSettlementStatus(PGConstants.PG_SETTLEMENT_PROCESSING);
        GetTransactionsListResponse processingTxnList =
            transactionService.searchAccountTransactions(transaction);

        if (null != processingTxnList && null != processingTxnList.getAccountTransactionList()) {

          setErrorCodeMessageNoOfRecords(session, modelAndView, transactionResponse, processingTxnList);

          modelAndView = PaginationUtil.getPagenationModelSuccessive(modelAndView, pageNumber,
              processingTxnList.getTotalResultCount());

        } else {
          modelAndView.addObject(Constants.PROCESSING_TXN_LIST, transactionList);
          session.setAttribute(Constants.PROCESSING_TXN_LIST, transactionList);
        }

      } catch (Exception e) {
        logger.error("Error :: TransactionsController :: processingTransactionsPagination method",
            e);
        modelAndView.setViewName(INVALID_REQUEST_PAGE);
        return modelAndView;
      }
    }

    session.setAttribute(Constants.ERROR, null);
    session.setAttribute(Constants.SUCESS, null);
    logger.info("Exiting :: TransactionsController :: processingTransactionsPagination method");
    return modelAndView;

  }

  @RequestMapping(value = CHATAK_MERCHANT_EXECUTED_TRANSACTIONS, method = RequestMethod.GET)
  public ModelAndView showExecutedTransactions(HttpServletRequest request,
      HttpServletResponse response, HttpSession session, Map model) {

    logger.info("Entering :: TransactionsController :: showExecutedTransactions method");
    ModelAndView modelAndView = new ModelAndView(CHATAK_MERCHANT_EXECUTED_TRANSACTIONS);
    String existingFeature = (String) session.getAttribute(Constants.EXISTING_FEATURES);
    if (!existingFeature
        .contains(FeatureConstants.MERCHANT_SERVICE_REPORTS_TRANSACTION_FEATURE_ID)) {
      session.invalidate();
      modelAndView.setViewName(INVALID_REQUEST_PAGE);
      return modelAndView;
    }
    Long merchantId = (Long) session.getAttribute(Constants.LOGIN_USER_MERCHANT_ID);
    List<AccountTransactionDTO> transactionList = new ArrayList<>();
    GetMerchantDetailsResponse merchantDetailsResponse;
    GetTransactionsListRequest transaction = new GetTransactionsListRequest();
    TransactionListResponse transactionResponse = new TransactionListResponse();
    if (merchantId != null) {
      try {
        merchantDetailsResponse = paymentService.getMerchantIdAndTerminalId(merchantId.toString());
        transaction.setMerchant_code(merchantDetailsResponse.getMerchantId());

        setTxnCodeList(transaction);
        transaction.setPageIndex(Constants.ONE);
        transaction.setPageSize(Constants.MAX_ENTITIES_PORTAL_DISPLAY_SIZE);
        transaction.setSettlementStatus(PGConstants.PG_SETTLEMENT_EXECUTED);
        GetTransactionsListResponse executedTxnList =
            transactionService.searchAccountTransactions(transaction);

        if (null != executedTxnList && null != executedTxnList.getAccountTransactionList()) {

          setTransactionResponseDetails(session, modelAndView, transactionResponse, executedTxnList);
          modelAndView = PaginationUtil.getPagenationModel(modelAndView,
              executedTxnList.getTotalResultCount());
        } else {
          modelAndView.addObject(Constants.EXECUTED_TXN_LIST, transactionList);
          session.setAttribute(Constants.EXECUTED_TXN_LIST, transactionList);
        }
      } catch (Exception e) {
        logger.error("Error :: TransactionsController :: showExecutedTransactions method", e);
        modelAndView.setViewName(INVALID_REQUEST_PAGE);
        return modelAndView;
      }
    }
    session.setAttribute(Constants.ERROR, null);
    session.setAttribute(Constants.SUCESS, null);
    logger.info("Exiting :: TransactionsController :: showExecutedTransactions method");
    return modelAndView;

  }

  @RequestMapping(value = CHATAK_MERCHANT_EXECUTED_TRANSACTIONS_PAGINATION,
      method = RequestMethod.POST)
  public ModelAndView executedTransactionsPagination(HttpServletRequest request,
      final HttpSession session, @FormParam("pageNumber") final Integer pageNumber,
      @FormParam("totalRecords") final Integer totalRecords, Map model) {
    logger.info("Entering :: TransactionsController :: executedTransactionsPagination method");

    ModelAndView modelAndView = new ModelAndView(CHATAK_MERCHANT_EXECUTED_TRANSACTIONS);
    if (request.getHeader(Constants.REFERER) == null) {
      session.invalidate();
      modelAndView.setViewName(INVALID_REQUEST_PAGE);
      return modelAndView;
    }

    Long merchantId = (Long) session.getAttribute(Constants.LOGIN_USER_MERCHANT_ID);
    GetTransactionsListRequest transaction = new GetTransactionsListRequest();
    TransactionListResponse transactionResponse = new TransactionListResponse();
    List<AccountTransactionDTO> transactionList = new ArrayList<>();
    GetMerchantDetailsResponse merchantDetailsResponse;
    if (merchantId != null) {

      try {
        transaction.setPageIndex(pageNumber);
        transaction.setNoOfRecords(totalRecords);
        transaction.setPageSize(Constants.MAX_ENTITIES_PORTAL_DISPLAY_SIZE);

        merchantDetailsResponse = paymentService.getMerchantIdAndTerminalId(merchantId.toString());
        transaction.setMerchant_code(merchantDetailsResponse.getMerchantId());

        setTxnCodeList(transaction);
        transaction.setSettlementStatus(PGConstants.PG_SETTLEMENT_EXECUTED);
        GetTransactionsListResponse executedTxnList =
            transactionService.searchAccountTransactions(transaction);

        if (null != executedTxnList && null != executedTxnList.getAccountTransactionList()) {
          setTransactionResponseDetails(session, modelAndView, transactionResponse, executedTxnList);

          modelAndView = PaginationUtil.getPagenationModelSuccessive(modelAndView, pageNumber,
              executedTxnList.getTotalResultCount());
        } else {
          modelAndView.addObject(Constants.EXECUTED_TXN_LIST, transactionList);
          session.setAttribute(Constants.EXECUTED_TXN_LIST, transactionList);
        }
      } catch (Exception exp) {
        logger.error("Error :: TransactionsController :: executedTransactionsPagination method", exp);
        modelAndView.setViewName(INVALID_REQUEST_PAGE);
        return modelAndView;
      }
    }
    session.setAttribute(Constants.ERROR, null);
    session.setAttribute(Constants.SUCESS, null);
    logger.info("Entering :: TransactionsController :: executedTransactionsPagination method");
    return modelAndView;
  }

  private void setTxnCodeList(GetTransactionsListRequest transaction) {
	List<String> txnCodeList = new ArrayList<>(Constants.ELEVEN);
	txnCodeList.add(AccountTransactionCode.CC_AMOUNT_CREDIT);
	txnCodeList.add(AccountTransactionCode.CC_AMOUNT_DEBIT);
	txnCodeList.add(AccountTransactionCode.ACCOUNT_CREDIT);
	txnCodeList.add(AccountTransactionCode.ACCOUNT_DEBIT);
	txnCodeList.add(AccountTransactionCode.CC_FEE_CREDIT); 
	txnCodeList.add(AccountTransactionCode.CC_FEE_DEBIT); 
	txnCodeList.add(AccountTransactionCode.EFT_DEBIT);
	txnCodeList.add(AccountTransactionCode.FT_BANK);
	txnCodeList.add(AccountTransactionCode.FT_CHECK);

	transaction.setTransactionCodeList(txnCodeList);
  }

  private void setTransactionResponseDetails(final HttpSession session, ModelAndView modelAndView,
		TransactionListResponse transactionResponse, GetTransactionsListResponse executedTxnList) {
	List<AccountTransactionDTO> transactionList;
	transactionResponse.setAccountTxnList(executedTxnList.getAccountTransactionList());
	transactionResponse.setErrorCode(executedTxnList.getErrorCode());
	transactionResponse.setErrorMessage(executedTxnList.getErrorMessage());
	transactionResponse.setTotalNoOfRows(executedTxnList.getTotalResultCount());
	transactionList = transactionResponse.getAccountTxnList() != null
	    ? transactionResponse.getAccountTxnList() : new ArrayList<AccountTransactionDTO>();
	modelAndView.addObject(Constants.EXECUTED_TXN_LIST, transactionList);
	session.setAttribute(Constants.EXECUTED_TXN_LIST, transactionList);
  }

  /**
   * @param model
   * @param request
   * @param response
   * @param session
   * @return
   */
  @RequestMapping(value = CHATAK_MERCHANT_VIRTUAL_TERMINAL_FETCH_TRAN_ADJUST,
      method = RequestMethod.GET)
  public @ResponseBody String fetchTransaction(Map model, HttpServletRequest request,
      HttpServletResponse response, HttpSession session) {
    ModelAndView modelAndView = new ModelAndView(CHATAK_MERCHANT_VIRTUAL_TERMINAL_SALE_PAGE);
    modelAndView.addObject(Constants.ERROR, null);
    logger.info("Entering :: VirtualTerminalController :: fetchTransaction method");
    String cardNum = request.getParameter("cardNum");
    String authId = request.getParameter("authId");
    String invoiceNum = request.getParameter("invoiceNum");
    Long merchantId = (Long) session.getAttribute(Constants.LOGIN_USER_MERCHANT_ID);
    GetTransactionResponse getTransactionResponse;
    GetMerchantDetailsResponse merchantDetailsResponse;
    try {
      merchantDetailsResponse = paymentService.getMerchantIdAndTerminalId(merchantId.toString());
      if (merchantDetailsResponse != null) {
        getTransactionResponse = paymentService.getTransaction(merchantId.toString(),
            merchantDetailsResponse.getTerminalId(), authId, cardNum, invoiceNum);
        return JsonUtil.convertObjectToJSON(getTransactionResponse);
      }
    } catch (ChatakPayException e) {
      modelAndView.addObject(Constants.ERROR, e.getMessage());
      logger.error("ERROR :: VirtualTerminalController :: fetchTransaction method:" + e);
    } catch (Exception e) {
      modelAndView.addObject(Constants.ERROR,
          messageSource.getMessage(Constants.CHATAK_GENERAL_ERROR, null, LocaleContextHolder.getLocale()));
      logger.error("ERROR :: VirtualTerminalController :: fetchTransaction method:" + e);
    }
    logger.info("Exiting :: VirtualTerminalController :: fetchTransaction method");
    return invoiceNum;

  }

  @RequestMapping(value = CHATAK_MERCHANT_VIRTUAL_TERMINAL_FETCH_TRAN, method = RequestMethod.GET)
  public @ResponseBody String fetchTransactionbyRefId(Map model, HttpServletRequest request,
      HttpServletResponse response, HttpSession session) {
    ModelAndView modelAndView = new ModelAndView(CHATAK_MERCHANT_VIRTUAL_TERMINAL_SALE_PAGE);
    modelAndView.addObject(Constants.ERROR, null);
    logger.info("Entering :: VirtualTerminalController :: fetchTransactionbyRefId method");
    String refId = request.getParameter("refId");
    String txnType = request.getParameter("txnType");
    Long merchantId = (Long) session.getAttribute(Constants.LOGIN_USER_MERCHANT_ID);

    GetTransactionResponse getTransactionResponse;
    GetMerchantDetailsResponse merchantDetailsResponse;
    try {
      merchantDetailsResponse = paymentService.getMerchantIdAndTerminalId(merchantId.toString());
      if (null != merchantDetailsResponse) {

        if ("sale".equalsIgnoreCase(txnType)) {
          getTransactionResponse =
              paymentService.getTransactionByRefId(merchantDetailsResponse.getMerchantId(),
                  merchantDetailsResponse.getTerminalId(), refId, txnType);
        } else {
          getTransactionResponse =
              paymentService.getTransactionByRefIdForRefund(merchantDetailsResponse.getMerchantId(),
                  merchantDetailsResponse.getTerminalId(), refId, txnType);
        }
        return JsonUtil.convertObjectToJSON(getTransactionResponse);
      }
    } catch (ChatakPayException exp) {
      modelAndView.addObject(Constants.ERROR, exp.getMessage());
      logger.error("ERROR :: VirtualTerminalController :: fetchTransactionbyRefId method:" + exp);
    } catch (Exception e) {
      modelAndView.addObject(Constants.ERROR,
          messageSource.getMessage(Constants.CHATAK_GENERAL_ERROR, null, LocaleContextHolder.getLocale()));
      logger.error("ERROR :: VirtualTerminalController :: fetchTransactionbyRefId method:" + e);
    }
    logger.info("Exiting :: VirtualTerminalController :: fetchTransactionbyRefId method");
    return refId;
  }
}
