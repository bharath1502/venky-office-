/**
 * 
 */
package com.chatak.acquirer.admin.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
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

import com.chatak.acquirer.admin.constants.URLMappingConstants;
import com.chatak.acquirer.admin.controller.model.ExportDetails;
import com.chatak.acquirer.admin.controller.model.LoginResponse;
import com.chatak.acquirer.admin.exception.ChatakAdminException;
import com.chatak.acquirer.admin.exception.ChatakPayException;
import com.chatak.acquirer.admin.service.RestPaymentService;
import com.chatak.acquirer.admin.service.SettlementService;
import com.chatak.acquirer.admin.service.TransactionService;
import com.chatak.acquirer.admin.util.CommonUtil;
import com.chatak.acquirer.admin.util.ExportUtil;
import com.chatak.acquirer.admin.util.JsonUtil;
import com.chatak.acquirer.admin.util.PaginationUtil;
import com.chatak.acquirer.admin.util.StringUtil;
import com.chatak.pg.constants.PGConstants;
import com.chatak.pg.enums.ExportType;
import com.chatak.pg.model.BulkSettlementResponse;
import com.chatak.pg.model.GetMerchantDetailsResponse;
import com.chatak.pg.model.GetTransactionResponse;
import com.chatak.pg.model.SettlementActionDTOList;
import com.chatak.pg.model.SettlemetActionDTO;
import com.chatak.pg.model.VirtualTerminalRefundDTO;
import com.chatak.pg.model.VirtualTerminalVoidDTO;
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
  private SettlementService settlementService;

  @Autowired
  RestPaymentService paymentService;

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
  @RequestMapping(value = CHATAK_ADMIN_SEARCH_TRANSACTION_PAGE, method = RequestMethod.GET)
  public ModelAndView showSearchTransactionsPage(HttpServletRequest request,
      HttpServletResponse response, GetTransactionsListRequest transaction,
      BindingResult bindingResult, Map model, HttpSession session) {
    logger.info("Entering :: TransactionsController :: showSearchMerchantPage method");
    ModelAndView modelAndView = new ModelAndView(CHATAK_ADMIN_SEARCH_TRANSACTION_PAGE);

    session.removeAttribute(PGConstants.BULK_SETTLEMENT_LIST_OBJ);
    session.removeAttribute(PGConstants.BULK_SETTLEMENT_LIST);

    modelAndView.addObject(Constants.ERROR, null);
    session.setAttribute(Constants.ERROR, null);
    transaction.setPageIndex(Constants.ONE);
    transaction.setPageSize(Constants.MAX_TRANSACTION_ENTITY_DISPLAY_SIZE);
    transaction.setToGetCurrentDayTxns(true);
    session.setAttribute(Constants.TRANSACTIONS_REQ_OBJ_MODEL, transaction);
    try {
      GetTransactionsListResponse transactionsList = new GetTransactionsListResponse();
      session.setAttribute(Constants.TRANSACTIONS_REPORT, transactionsList);
      // Added for exporting all transactions
      List<Transaction> transactionList = new ArrayList<>();
      modelAndView.addObject(Constants.TRANSACTIONS_MODEL, transactionList);
    } catch (Exception e) {
      logger.error("ERROR :: TransactionsController :: showSearchMerchantPage method", e);
      modelAndView.addObject(Constants.TRANSACTIONS_MODEL, new ArrayList<Transaction>());
      modelAndView.addObject(Constants.ERROR,
          messageSource.getMessage(Constants.CHATAK_GENERAL_ERROR, null, LocaleContextHolder.getLocale()));
    }
    modelAndView.addObject("flag", false);
    model.put(Constants.TRANSACTION, new GetTransactionsListRequest());
    modelAndView.addObject(Constants.SETTLEMENT_DTO, new SettlemetActionDTO());
    logger.info("Exiting :: TransactionsController :: showSearchMerchantPage method");
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
  @RequestMapping(value = CHATAK_ADMIN_SEARCH_TRANSACTION, method = RequestMethod.POST)
  public ModelAndView searchTransactions(HttpServletRequest request, HttpServletResponse response,
      GetTransactionsListRequest transaction, BindingResult bindingResult, Map model,
      HttpSession session) {
    logger.info("Entering :: TransactionsController :: searchMerchant method");
    ModelAndView modelAndView = new ModelAndView(CHATAK_ADMIN_SEARCH_TRANSACTION_PAGE);
    modelAndView.addObject(Constants.ERROR, null);
    session.setAttribute(Constants.ERROR, null);
    transaction.setPageIndex(Constants.ONE);
    transaction.setPageSize(Constants.MAX_ENTITIES_PORTAL_DISPLAY_SIZE);
    transaction.setToGetCurrentDayTxns(false);
    session.setAttribute(Constants.TRANSACTIONS_REQ_OBJ_MODEL, transaction);
    List<Transaction> transactionList = new ArrayList<>();
    try {
    	LoginResponse loginResponse = (LoginResponse) session.getAttribute(Constants.LOGIN_RESPONSE_DATA);
      session.removeAttribute(PGConstants.BULK_SETTLEMENT_LIST_OBJ);
      session.removeAttribute(PGConstants.BULK_SETTLEMENT_LIST);
      GetTransactionsListResponse transactionResponse =
          transactionService.searchTransactions(transaction, loginResponse.getEntityId());
      session.setAttribute(Constants.TRANSACTIONS_REPORT, transactionResponse);
      if (transactionResponse.getTransactionList() != null
          && !CollectionUtils.isEmpty(transactionResponse.getTransactionList())) {
        transactionList = transactionResponse.getTransactionList();
        modelAndView.addObject(Constants.TRANSACTIONS_MODEL, transactionList);
        modelAndView = PaginationUtil.getPagenationModel(modelAndView,
            transactionResponse.getTotalResultCount());
        transactionService.prepareAndBindTxnPopupData(transactionList);
        modelAndView.addObject(Constants.TRANSACTIONS_MODEL, transactionList);
      } else {
        modelAndView.addObject(Constants.TRANSACTIONS_MODEL, transactionList);
        modelAndView = PaginationUtil.getPagenationModel(modelAndView,
            transactionResponse.getTotalResultCount());
      }
    } catch (Exception e) {
      modelAndView.addObject(Constants.TRANSACTIONS_MODEL, new ArrayList<Transaction>());
      modelAndView.addObject(Constants.ERROR,
          messageSource.getMessage(Constants.CHATAK_GENERAL_ERROR, null, LocaleContextHolder.getLocale()));
      logger.error("ERROR :: TransactionsController :: searchMerchant method", e);
    }
    modelAndView.addObject("flag", true);
    model.put(Constants.TRANSACTION, transaction);
    modelAndView.addObject(Constants.SETTLEMENT_DTO, new SettlemetActionDTO());
    logger.info("Exiting :: TransactionsController :: searchMerchant method");
    return modelAndView;
  }

	@RequestMapping(value = CHATAK_ADMIN_SEARCH_TRANSACTION, method = RequestMethod.GET)
	public ModelAndView searchTransactionsGetMethod(HttpServletRequest request, HttpServletResponse response,
			GetTransactionsListRequest transaction, BindingResult bindingResult, Map model, HttpSession session) {
		logger.info("Entering :: TransactionsController :: searchMerchant method");
		ModelAndView modelAndView = showSearchTransactionsPage(request, response, transaction, bindingResult, model, session);
		logger.info("Exiting :: TransactionsController :: searchMerchant method");
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
  @RequestMapping(value = CHATAK_ADMIN_TRANSACTION_PAGINATION, method = RequestMethod.POST)
  public ModelAndView getPaginationList(HttpServletRequest request, final HttpSession session,
      @FormParam("pageNumber") final Integer pageNumber,
      @FormParam(PGConstants.TOTAL_RECORDS) final Integer totalRecords, Map model) {
    logger.info("Entering :: TransactionsController :: getPaginationList method");

    ModelAndView modelAndView = new ModelAndView(CHATAK_ADMIN_SEARCH_TRANSACTION_PAGE);
    LoginResponse loginResponse = (LoginResponse) session.getAttribute(Constants.LOGIN_RESPONSE_DATA);
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
      transaction.setPageSize(Constants.MAX_TRANSACTION_ENTITY_DISPLAY_SIZE);

      GetTransactionsListResponse transactionsList =
          transactionService.searchTransactions(transaction, loginResponse.getEntityId());
      session.setAttribute(Constants.TRANSACTIONS_REPORT, transactionsList);

      if (transactionsList != null
          && !CollectionUtils.isEmpty(transactionsList.getTransactionList())) {
        modelAndView = PaginationUtil.getPagenationModelSuccessive(modelAndView, pageNumber,
            transactionsList.getTotalResultCount());
        transactionService.prepareAndBindTxnPopupData(transactionsList.getTransactionList());
      }
      if (transactionsList != null) {
        modelAndView.addObject(Constants.TRANSACTIONS_MODEL, transactionsList.getTransactionList());
      }
    } catch (Exception e) {
      modelAndView.addObject(Constants.ERROR,
          messageSource.getMessage(Constants.CHATAK_GENERAL_ERROR, null, LocaleContextHolder.getLocale()));
      logger.error("ERROR :: TransactionsController :: getPaginationList method", e);
    }
    modelAndView.addObject(Constants.SETTLEMENT_DTO, new SettlemetActionDTO());
    logger.info("Exiting :: TransactionsController :: getPaginationList method");
    return modelAndView;
  }

  @RequestMapping(value = CHATA_ADMIN_TRANSACTION_EXPORT, method = RequestMethod.POST)
  public ModelAndView downloadTransactionReport(HttpServletRequest request,
      HttpServletResponse response,
      @FormParam("downLoadPageNumber") final Integer downLoadPageNumber,
      @FormParam("downloadType") final String downloadType,
      @FormParam("downloadReportObject") final String downloadReportObject,
      @FormParam(PGConstants.TOTAL_RECORDS) final Integer totalRecords,
      @FormParam("downloadAllRecords") final boolean downloadAllRecords, HttpSession session,
      Map model) {
    logger.info("Entering :: TransactionController ;; downloadTransactionReport method ");
    ModelAndView modelAndView = new ModelAndView(CHATAK_ADMIN_SEARCH_TRANSACTION_PAGE);
    GetTransactionsListRequest transaction = new GetTransactionsListRequest();
    String jsonRequest = null;
    LoginResponse loginResponse = (LoginResponse) session.getAttribute(Constants.LOGIN_RESPONSE_DATA);
    try {
      if (null != downloadReportObject) {
        jsonRequest = "{\"actionDTOs\":[" + downloadReportObject + "]}";
      }
      SettlementActionDTOList actionDTOList = (SettlementActionDTOList) JsonUtil
          .convertJSONToObject(jsonRequest, SettlementActionDTOList.class);
      transaction.setPageIndex(downLoadPageNumber);
      transaction.setPageSize(Constants.MAX_ENTITIES_PORTAL_DISPLAY_SIZE);
      GetTransactionsListRequest transactionList =
          (GetTransactionsListRequest) session.getAttribute(Constants.TRANSACTIONS_REQ_OBJ_MODEL);
      
      transactionList.setPageIndex(downLoadPageNumber);
      transactionList.setPageSize(Constants.MAX_ENTITIES_PORTAL_DISPLAY_SIZE);
      
      transactionList = setTransactionList(totalRecords, downloadAllRecords, transactionList);
      
      GetTransactionsListResponse transactionResponse =
          transactionService.searchTransactions(transactionList, loginResponse.getEntityId());
      session.setAttribute(Constants.ALL_TRANSACTIONS_MODEL,
          transactionResponse.getTransactionList());
      List<Transaction> list = transactionResponse.getTransactionList();
      if (null != actionDTOList && actionDTOList.getActionDTOs().size() > 0) {// Checking if selected transaction exists
                                                                              // and exporting only those else exporting all
        List<SettlemetActionDTO> selectedListDetails = actionDTOList.getActionDTOs();
        List<Transaction> tempList = new ArrayList<>();
        
        list = getTransactionList(list, selectedListDetails, tempList);
        
        response.setHeader("selectedFlag", "_selected_");
      }
      ExportDetails exportDetails = new ExportDetails();
      if (Constants.PDF_FILE_FORMAT.equalsIgnoreCase(downloadType)) {
    	  exportDetails.setExportType(ExportType.PDF);
      } else if (Constants.XLS_FILE_FORMAT.equalsIgnoreCase(downloadType)) {
    	  exportDetails.setExportType(ExportType.XLS);
		  exportDetails.setExcelStartRowNumber(Integer.parseInt("5"));
      }
      setExportDetailsDataForDownloadTransactionReport(list, exportDetails);	
	  ExportUtil.exportData(exportDetails, response, messageSource);
    } catch (Exception e) {
      modelAndView.addObject(Constants.ERROR,
          messageSource.getMessage(Constants.CHATAK_GENERAL_ERROR, null, LocaleContextHolder.getLocale()));
      logger.error("ERROR :: TrasactionController :: downloadTransactionReport method", e);
    }
    logger.info("Exiting :: TransactionController :: downloadTransactionReport method");
    modelAndView.addObject("transactions", transaction);
    return null;
  }
  
  private void setExportDetailsDataForDownloadTransactionReport(List<Transaction> transactionList,
      ExportDetails exportDetails) {
    exportDetails.setReportName("Transactions");
    exportDetails.setHeaderMessageProperty("chatak.header.transaction.messages");

    exportDetails.setHeaderList(getTransactionHeaderList());
    exportDetails.setFileData(getTransactionFileData(transactionList));
  }

  private List<Transaction> getTransactionList(List<Transaction> list,
      List<SettlemetActionDTO> selectedListDetails, List<Transaction> tempList) {
    for (SettlemetActionDTO s : selectedListDetails) {
      for (Transaction t : list) {
        if (s.getTxnId().equals(t.getTransactionId())) {
          tempList.add(t);
        }
      }
    }
    list = tempList;
    return list;
  }

  private GetTransactionsListRequest setTransactionList(final Integer totalRecords,
      final boolean downloadAllRecords, GetTransactionsListRequest transactionList) {
    if (transactionList == null) {
      transactionList = new GetTransactionsListRequest();
    }
    if (downloadAllRecords) {
      transactionList.setPageIndex(Constants.ONE);
      transactionList.setPageSize(totalRecords);
    }
    return transactionList;
  }

  /**
   * <Method to do bulk settlement for pending transactions>
   * 
   * @param request
   * @param response
   * @param session
   * @param requestObject
   * @param model
   * @return
   * @throws ParseException
   */
  @RequestMapping(value = CHATAK_ADMIN_BULK_SETTLEMRNT_ACTION, method = RequestMethod.POST)
  public ModelAndView processBulkSettlementAction(HttpServletRequest request,
      HttpServletResponse response, HttpSession session,
      @FormParam("requestObject") String requestObject, @FormParam("status") String status,
      @FormParam("removedTxns") String[] removedTxns, Map model) {
    ModelAndView modelAndView = new ModelAndView(CHATAK_ADMIN_SEARCH_TRANSACTION_PAGE);
    try {
      String userName = (String)session.getAttribute(CHATAK_ADMIN_USER_NAME);
      SettlementActionDTOList actionDTOList = new SettlementActionDTOList();
      if (null != session.getAttribute(PGConstants.BULK_SETTLEMENT_LIST_OBJ)) {
        if (!StringUtil.isNullAndEmpty(requestObject)) {
          String jsonRequest = "{\"actionDTOs\":[" + requestObject + "]}";
          actionDTOList = (SettlementActionDTOList) JsonUtil.convertJSONToObject(jsonRequest,
              SettlementActionDTOList.class);
        }

        SettlementActionDTOList tempList =
            (SettlementActionDTOList) session.getAttribute(PGConstants.BULK_SETTLEMENT_LIST_OBJ);

        List<SettlemetActionDTO> settlemetActionDTOList = tempList.getActionDTOs();

        List removedTxnsList = Arrays.asList(removedTxns);
        removeDuplicateList(settlemetActionDTOList, removedTxnsList);

        if (null != actionDTOList.getActionDTOs()) {
          actionDTOList.getActionDTOs().addAll(settlemetActionDTOList);
        } else {
          actionDTOList.setActionDTOs(settlemetActionDTOList);
        }

      }
      if (!StringUtil.isNullAndEmpty(requestObject)) {
        String jsonRequest = "{\"actionDTOs\":[" + requestObject + "]}";
        actionDTOList = (SettlementActionDTOList) JsonUtil.convertJSONToObject(jsonRequest,
            SettlementActionDTOList.class);
        session.setAttribute(PGConstants.BULK_SETTLEMENT_LIST_OBJ, actionDTOList);
        session.setAttribute(PGConstants.BULK_SETTLEMENT_LIST,
            (null != actionDTOList.getActionDTOs() && actionDTOList.getActionDTOs().size() > 0)
                ? JsonUtil.convertObjectToJSON(actionDTOList)
                : JsonUtil.convertObjectToJSON(new SettlementActionDTOList()));
      }

      BulkSettlementResponse bulkSettlementResponse =
          settlementService.updateBulkSettlementStatus(actionDTOList, status, "Bulk settlement", userName);
      boolean isSuccess = bulkSettlementResponse.isSuccess();
      GetTransactionsListRequest transaction =
          (GetTransactionsListRequest) session.getAttribute(Constants.TRANSACTIONS_REQ_OBJ_MODEL);

      modelAndView = getPaginationList(request, session, transaction.getPageIndex(),
          transaction.getNoOfRecords(), model);

      if (isSuccess) {
        model.put(Constants.SUCESS,
            messageSource.getMessage("chatak.admin.bulk.settlement.action.success", null,
                LocaleContextHolder.getLocale()) + " for "
            + bulkSettlementResponse.getSuccessCount() + " transactions");
        session.removeAttribute(PGConstants.BULK_SETTLEMENT_LIST_OBJ);
        session.removeAttribute(PGConstants.BULK_SETTLEMENT_LIST);
      } else {
        model.put(Constants.ERROR, messageSource.getMessage(
            "chatak.admin.bulk.settlement.action.failure", null, LocaleContextHolder.getLocale()));
      }
    } catch (ChatakAdminException e) {
      GetTransactionsListRequest transaction =
          (GetTransactionsListRequest) session.getAttribute(Constants.TRANSACTIONS_REQ_OBJ_MODEL);
      logger.error("ERROR :: TransactionsController :: processBulkSettlementAction ChatakAdminException", e);
      modelAndView = getPaginationList(request, session, transaction.getPageIndex(),
          transaction.getNoOfRecords(), model);
      model.put(Constants.ERROR,
          messageSource.getMessage(Constants.CHATAK_GENERAL_ERROR, null, LocaleContextHolder.getLocale()));
      modelAndView.addObject(Constants.SETTLEMENT_DTO, new SettlemetActionDTO());
    } catch (Exception e) {
      GetTransactionsListRequest transaction =
          (GetTransactionsListRequest) session.getAttribute(Constants.TRANSACTIONS_REQ_OBJ_MODEL);
      logger.error("ERROR :: TransactionsController :: processBulkSettlementAction Exception", e);
      modelAndView = getPaginationList(request, session, transaction.getPageIndex(),
          transaction.getNoOfRecords(), model);
      model.put(Constants.ERROR,
          messageSource.getMessage(Constants.CHATAK_GENERAL_ERROR, null, LocaleContextHolder.getLocale()));
      modelAndView.addObject(Constants.SETTLEMENT_DTO, new SettlemetActionDTO());
    }
    logger.info("Exiting :: TransactionsController :: processBulkSettlementAction method");
    return modelAndView;
  }

  private void removeDuplicateList(List<SettlemetActionDTO> settlemetActionDTOList,
      List removedTxnsList) {
    if (removedTxnsList.size() > 0) {
      String txnId = "";
      Iterator<SettlemetActionDTO> listItr = settlemetActionDTOList.iterator();
      while (listItr.hasNext()) {
        txnId = listItr.next().getTxnId();
        if (removedTxnsList.contains(txnId))
          listItr.remove();
      }
    }
  }

  @RequestMapping(value = CHATAK_ADMIN_TRANSACTION_REFUND_PAGE, method = RequestMethod.POST)
  public ModelAndView showTransactionRefund(HttpServletRequest request,
      HttpServletResponse response, HttpSession session, Map model,
      @FormParam("getRefundMerchantId") final String getRefundMerchantId,
      @FormParam("getRefundTxnId") final String getRefundTxnId) {
    logger.info("Entering :: TransactionsController :: showTransactionRefund method");
    ModelAndView modelAndView = new ModelAndView(SHOW_CHATAK_ADMIN_TRANSACTION_REFUND);
    GetTransactionResponse getTransactionResponse;
    GetMerchantDetailsResponse merchantDetailsResponse;
    VirtualTerminalRefundDTO virtualTeminalRefund = new VirtualTerminalRefundDTO();
    try {
      merchantDetailsResponse = paymentService.getMerchantIdAndTerminalId(getRefundMerchantId);
      if (merchantDetailsResponse != null) {
        getTransactionResponse =
            paymentService.getTransactionByRefId(merchantDetailsResponse.getMerchantId(),
                merchantDetailsResponse.getTerminalId(), getRefundTxnId, "sale");
        if (null != getTransactionResponse) {
          virtualTeminalRefund.setCardNum(getTransactionResponse.getCardNumMasked());
          virtualTeminalRefund.setExpDate(getTransactionResponse.getExpDate());
          virtualTeminalRefund.setInvoiceNumber(getTransactionResponse.getInvoiceNumber());
          virtualTeminalRefund.setFeeAmount(getTransactionResponse.getFeeAmount());
          virtualTeminalRefund.setSubTotal(getTransactionResponse.getSubTotal());
          virtualTeminalRefund.setTxnAmount(getTransactionResponse.getTxnAmount());
          virtualTeminalRefund.setCardHolderName(getTransactionResponse.getCardHolderName());
          virtualTeminalRefund.setCgRefNumber(getTransactionResponse.getCgRefNumber());
          virtualTeminalRefund.setMerchantId(getTransactionResponse.getMerchantId());
          virtualTeminalRefund.setAuthId(getTransactionResponse.getAuthId());
          virtualTeminalRefund.setTxnRefNumber(getTransactionResponse.getTxnRefNum());
        }
        modelAndView.addObject(Constants.VIRTUAL_TEMINAL_REFUND, virtualTeminalRefund);
      }
    } catch (Exception e) {
      modelAndView.addObject(Constants.ERROR,
          messageSource.getMessage(Constants.CHATAK_GENERAL_ERROR, null, LocaleContextHolder.getLocale()));
      logger.error("ERROR :: TransactionsController :: fetchTransactionbyRefId method:" + e);
    }
    logger.info("Exiting :: TransactionsController :: showTransactionRefund method");
    return modelAndView;

  }

  @RequestMapping(value = CHATAK_ADMIN_TRANSACTION_VOID_PROCESS, method = RequestMethod.POST)
  public ModelAndView fetchTransactionForVoid(Map model, HttpServletRequest request,
      HttpServletResponse response, @FormParam("merchantID") final String merchantID,
      @FormParam("transactionID") final String transactionID, HttpSession session) {
    ModelAndView modelAndView = new ModelAndView(CHATAK_ADMIN_TRANSACTION_VOID_PAGE);
    modelAndView.addObject(Constants.ERROR, null);
    logger.info("Entering :: TransactionsController :: fetchTransactionForVoid method");

    GetTransactionResponse getTransactionResponse;
    GetMerchantDetailsResponse merchantDetailsResponse;
    VirtualTerminalVoidDTO virtualTeminalVoid = new VirtualTerminalVoidDTO();
    try {
      merchantDetailsResponse = paymentService.getMerchantIdAndTerminalId(merchantID);
      if (merchantDetailsResponse != null) {
        getTransactionResponse =
            paymentService.getTransactionByRefId(merchantDetailsResponse.getMerchantId(),
                merchantDetailsResponse.getTerminalId(), transactionID, PGConstants.TXN_TYPE_SALE);
        if (null != getTransactionResponse) {
          virtualTeminalVoid.setCardNum(getTransactionResponse.getCardNumMasked());
          virtualTeminalVoid.setExpDate(getTransactionResponse.getExpDate());
          virtualTeminalVoid.setInvoiceNumber(getTransactionResponse.getInvoiceNumber());
          virtualTeminalVoid.setFeeAmount(getTransactionResponse.getFeeAmount());
          virtualTeminalVoid.setSubTotal(getTransactionResponse.getSubTotal());
          virtualTeminalVoid.setTxnAmount(getTransactionResponse.getTxnAmount());
          virtualTeminalVoid.setCardHolderName(getTransactionResponse.getCardHolderName());
          virtualTeminalVoid.setCgRefNumber(getTransactionResponse.getCgRefNumber());
          virtualTeminalVoid.setMerchantId(getTransactionResponse.getMerchantId());
          virtualTeminalVoid.setAuthId(getTransactionResponse.getAuthId());
          virtualTeminalVoid.setTxnRefNumber(getTransactionResponse.getTxnRefNum());
        }
      }
      modelAndView.addObject(Constants.VIRTUAL_TEMINAL_VOID, virtualTeminalVoid);
    } catch (Exception e) {
      modelAndView.addObject(Constants.ERROR,
          messageSource.getMessage(Constants.CHATAK_GENERAL_ERROR, null, LocaleContextHolder.getLocale()));
      logger.error("ERROR :: TransactionsController :: fetchTransactionForVoid method:" + e);
    }
    logger.info("Exiting :: TransactionsController :: fetchTransactionForVoid method");
    return modelAndView;
  }

  @RequestMapping(value = PROCESS_CHATAK_ADMIN_TRANSACTION_VOID, method = RequestMethod.POST)
  public @ResponseBody ModelAndView processVoid(HttpServletRequest request,
      HttpServletResponse response, HttpSession session,
      @FormParam("txnRefNum") final String txnRefNum, VirtualTerminalVoidDTO virtualTerminalVoidDTO,
      BindingResult bindingResult, Map model) {

    logger.info("Entering :: TransactionsController :: processVoid method");
    ModelAndView modelAndView = new ModelAndView(CHATAK_ADMIN_TRANSACTION_VOID_PAGE);
    String merchantId = virtualTerminalVoidDTO.getMerchantId();
    if (null != merchantId) {
      try {
        virtualTerminalVoidDTO.setUserName((String)session.getAttribute(CHATAK_ADMIN_USER_NAME));
        GetMerchantDetailsResponse merchantDetailsResponse =
            paymentService.getMerchantIdAndTerminalId(merchantId);
        if (merchantDetailsResponse.getErrorCode().equals(Constants.SUCCESS_CODE)) {
          setMerchantAndTerminalId(virtualTerminalVoidDTO, merchantDetailsResponse);
        } else {
          modelAndView.addObject(Constants.ERROR, merchantDetailsResponse.getErrorMessage());
          modelAndView.addObject(Constants.VIRTUAL_TEMINAL_VOID, virtualTerminalVoidDTO);
          return modelAndView;
        }
        com.chatak.pg.model.TransactionResponse voidResponse =
            paymentService.doVoid(virtualTerminalVoidDTO);
        if (voidResponse != null && voidResponse.getErrorCode().equals(Constants.SUCCESS_CODE)) {
          model.put("txnRefNum", voidResponse);
          model.put("refFlag", true);
          model.put("mCode", virtualTerminalVoidDTO.getMerchantId());
          modelAndView.addObject(Constants.SUCESS, voidResponse.getErrorMessage());
          virtualTerminalVoidDTO.setSuccessDiv(true);
          modelAndView.addObject(Constants.VIRTUAL_TEMINAL_VOID, virtualTerminalVoidDTO);
        } else {
          setRefundError(modelAndView, voidResponse);
          modelAndView.addObject(Constants.VIRTUAL_TEMINAL_VOID, virtualTerminalVoidDTO);
        }
      } catch (ChatakPayException e) {
        modelAndView.addObject(Constants.ERROR, e.getMessage());
        modelAndView.addObject(Constants.VIRTUAL_TEMINAL_VOID, virtualTerminalVoidDTO);
        logger.error("ERROR :: TransactionsController :: processVoid method:", e);
      } catch (Exception exp) {
        modelAndView.addObject(Constants.ERROR, messageSource.getMessage(Constants.CHATAK_GENERAL_ERROR,
            null, LocaleContextHolder.getLocale()));
        modelAndView.addObject(Constants.VIRTUAL_TEMINAL_VOID, virtualTerminalVoidDTO);
        logger.error("ERROR :: TransactionsController :: processVoid method:" + exp);
      }
      logger.info("Exiting :: TransactionsController :: processVoid method");
      return modelAndView;
    } else {
      modelAndView.addObject(Constants.ERROR,
          messageSource.getMessage(Constants.CHATAK_GENERAL_ERROR, null, LocaleContextHolder.getLocale()));
      modelAndView.addObject(Constants.VIRTUAL_TEMINAL_VOID, virtualTerminalVoidDTO);
      logger.error("Exiting :: TransactionsController :: processVoid method");
      return modelAndView;
    }
  }

  private void setMerchantAndTerminalId(VirtualTerminalVoidDTO virtualTerminalVoidDTO,
		GetMerchantDetailsResponse merchantDetailsResponse) {
	virtualTerminalVoidDTO.setMerchantId(merchantDetailsResponse.getMerchantId());
	  virtualTerminalVoidDTO.setTerminalId(merchantDetailsResponse.getTerminalId());
  }

  @RequestMapping(value = PROCESS_CHATAK_ADMIN_TRANSACTION_REFUND, method = RequestMethod.POST)
  public @ResponseBody ModelAndView processRefund(HttpServletRequest request,
      HttpServletResponse response, HttpSession session,

      VirtualTerminalRefundDTO virtualTerminalRefundDTO, BindingResult bindingResult, Map model) {

    logger.info("Entering :: TransactionsController :: processRefund method");
    ModelAndView modelAndView = new ModelAndView(SHOW_CHATAK_ADMIN_TRANSACTION_REFUND);
    String merchantId = virtualTerminalRefundDTO.getMerchantId();
    if (null != merchantId) {
      virtualTerminalRefundDTO.setUserName((String)session.getAttribute(CHATAK_ADMIN_USER_NAME));
      try {
        GetMerchantDetailsResponse merchantDetailsResponse =
            paymentService.getMerchantIdAndTerminalId(merchantId);
        if (merchantDetailsResponse.getErrorCode().equals(Constants.SUCCESS_CODE)) {
          virtualTerminalRefundDTO.setMerchantId(merchantDetailsResponse.getMerchantId());
          virtualTerminalRefundDTO.setTerminalId(merchantDetailsResponse.getTerminalId());
        } else {
          modelAndView.addObject(Constants.ERROR, merchantDetailsResponse.getErrorMessage());
          modelAndView.addObject(Constants.VIRTUAL_TEMINAL_REFUND, virtualTerminalRefundDTO);
          return modelAndView;
        }
        com.chatak.pg.model.TransactionResponse refundResponse =
            paymentService.doRefund(virtualTerminalRefundDTO);
        if (refundResponse != null
            && refundResponse.getErrorCode().equals(Constants.SUCCESS_CODE)) {
          model.put("txnRefNum", refundResponse);
          model.put("refFlag", true);
          model.put("mCode", virtualTerminalRefundDTO.getMerchantId());
          modelAndView.addObject(Constants.SUCESS, refundResponse.getErrorMessage());
          virtualTerminalRefundDTO.setSuccessDiv(true);
          modelAndView.addObject(Constants.VIRTUAL_TEMINAL_REFUND, virtualTerminalRefundDTO);
        } else {
          setRefundError(modelAndView, refundResponse);
          modelAndView.addObject(Constants.VIRTUAL_TEMINAL_REFUND, virtualTerminalRefundDTO);
        }
      } catch (ChatakPayException e) {
        modelAndView.addObject(Constants.ERROR, e.getMessage());
        modelAndView.addObject(Constants.VIRTUAL_TEMINAL_REFUND, virtualTerminalRefundDTO);
        logger.error("ERROR :: TransactionsController :: processRefund method:" + e);
      } catch (Exception e) {
        modelAndView.addObject(Constants.ERROR, messageSource.getMessage(Constants.CHATAK_GENERAL_ERROR,
            null, LocaleContextHolder.getLocale()));
        modelAndView.addObject(Constants.VIRTUAL_TEMINAL_REFUND, virtualTerminalRefundDTO);
        logger.error("ERROR :: TransactionsController :: processRefund method:" + e);
      }
      logger.info("Exiting :: TransactionsController :: processRefund method");
      return modelAndView;
    } else {
      modelAndView.addObject(Constants.ERROR,
          messageSource.getMessage(Constants.CHATAK_GENERAL_ERROR, null, LocaleContextHolder.getLocale()));
      modelAndView.addObject(Constants.VIRTUAL_TEMINAL_REFUND, virtualTerminalRefundDTO);
      logger.error("Exiting :: TransactionsController :: processRefund method");
      return modelAndView;
    }
  }
  private ModelAndView setRefundError(ModelAndView modelAndView, 
      com.chatak.pg.model.TransactionResponse refundResponse) {
    if (refundResponse != null) {
      modelAndView.addObject(Constants.ERROR, refundResponse.getErrorMessage());
    } else {
      modelAndView.addObject(Constants.ERROR, "");
    }
    return modelAndView;
  }
  
  private List<String> getTransactionHeaderList() {
    String[] headerArr = {
        messageSource.getMessage(PGConstants.REPORT_LABEL_TRANSACTIONS_DATEORTIME, null,
            LocaleContextHolder.getLocale()),
        messageSource.getMessage("admin.common-deviceLocalTxnTime", null,
            LocaleContextHolder.getLocale()),
        messageSource.getMessage("transaction-file-exportutil-transactionId", null,
            LocaleContextHolder.getLocale()),
        messageSource.getMessage("reports.label.balancereports.merchantorsubmerchantName", null,
            LocaleContextHolder.getLocale()),
        messageSource.getMessage("reports.label.balancereports.merchantorsubmerchantcode", null,
            LocaleContextHolder.getLocale()),
        messageSource.getMessage("transaction-file-exportutil-terminalid", null,
            LocaleContextHolder.getLocale()),
        messageSource.getMessage("transaction-file-exportutil-description", null,
            LocaleContextHolder.getLocale()),
        messageSource.getMessage("transaction-file-exportutil-procTxnId", null,
            LocaleContextHolder.getLocale()),
        messageSource.getMessage(PGConstants.TRANSACTION_REPORT_BATCHID, null,
            LocaleContextHolder.getLocale()),
        messageSource.getMessage("reports.label.transactions.cardnumberField", null,
            LocaleContextHolder.getLocale()),
        messageSource.getMessage("currency-search-page.label.currency", null,
            LocaleContextHolder.getLocale()),
        messageSource.getMessage("transactionFileExportUtil.admin.txn.amt", null,
            LocaleContextHolder.getLocale()),
        messageSource.getMessage("transactions-search.label.merchantfee", null,
            LocaleContextHolder.getLocale()),
        messageSource.getMessage("transaction-file-exportutil-totaltxnamt", null,
            LocaleContextHolder.getLocale()),
        messageSource.getMessage("transaction-file-exportutil-txnType", null,
            LocaleContextHolder.getLocale()),
        messageSource.getMessage("reports.label.transactions.txnstatus", null,
            LocaleContextHolder.getLocale()),
        messageSource.getMessage("login.label.username", null, LocaleContextHolder.getLocale())};
    return new ArrayList<String>(Arrays.asList(headerArr));
  }

  private static List<Object[]> getTransactionFileData(List<Transaction> transactionList) {
    List<Object[]> fileData = new ArrayList<Object[]>();

    for (Transaction transaction : transactionList) {

      if (!"".equals(transaction.getTimeZoneOffset()) && null != transaction.getTimeZoneOffset()) {
        transaction.setTimeZoneOffset("("+transaction.getTimeZoneOffset()+")");
      }

      Object[] rowData = {transaction.getTransactionDate(),
          transaction.getDeviceLocalTxnTime() + transaction.getTimeZoneOffset(),
          Long.parseLong(transaction.getTransactionId()),
          transaction.getMerchantBusinessName(), Long.parseLong(transaction.getMerchant_code()),
          transaction.getTerminal_id(),
          transaction.getTxnDescription(), transaction.getRef_transaction_id(),
          transaction.getBatchId(), Long.parseLong(transaction.getMaskCardNumber()), transaction.getLocalCurrency(),
          CommonUtil.toAmount(transaction.getTransactionAmount()), CommonUtil.toAmount(transaction.getFee_amount()),
          CommonUtil.toAmount(transaction.getTxn_total_amount()),
          transaction.getTransaction_type().toUpperCase(),
          transaction.getMerchantSettlementStatus(), transaction.getUserName()

      };
      fileData.add(rowData);
    }

    return fileData;
  }
}
