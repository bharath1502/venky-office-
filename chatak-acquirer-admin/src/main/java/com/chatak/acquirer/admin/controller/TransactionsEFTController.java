package com.chatak.acquirer.admin.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
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
import com.chatak.acquirer.admin.exception.ChatakAdminException;
import com.chatak.acquirer.admin.service.FundTransfersService;
import com.chatak.acquirer.admin.service.RestPaymentService;
import com.chatak.acquirer.admin.service.SettlementService;
import com.chatak.acquirer.admin.service.TransactionService;
import com.chatak.acquirer.admin.util.ExportUtil;
import com.chatak.acquirer.admin.util.JsonUtil;
import com.chatak.acquirer.admin.util.PaginationUtil;
import com.chatak.pg.bean.LitleEFTRequest;
import com.chatak.pg.bean.TransactionPopUpDataDto;
import com.chatak.pg.constants.PGConstants;
import com.chatak.pg.enums.ExportType;
import com.chatak.pg.model.LitleEFTDTO;
import com.chatak.pg.model.LitleEFTDTOsList;
import com.chatak.pg.model.SettlementActionDTOList;
import com.chatak.pg.model.SettlemetActionDTO;
import com.chatak.pg.user.bean.GetTransactionsListRequest;
import com.chatak.pg.user.bean.GetTransactionsListResponse;
import com.chatak.pg.user.bean.Response;
import com.chatak.pg.user.bean.Transaction;
import com.chatak.pg.util.Constants;
import com.chatak.pg.util.DateUtil;
import com.chatak.pg.util.StringUtils;

@SuppressWarnings({"rawtypes", "unchecked"})
@Controller
public class TransactionsEFTController implements URLMappingConstants {

  private static Logger logger = Logger.getLogger(TransactionsEFTController.class);

  @Autowired
  private FundTransfersService fundTransfersService;

  @Autowired
  MessageSource messageSource;

  @Autowired
  private TransactionService transactionService;

  @Autowired
  private SettlementService settlementService;

  @Autowired
  RestPaymentService paymentService;

  @Autowired
  TransactionsController transactionsController;

  @RequestMapping(value = LITLE_EFT_EXECUTED_TRANSACTIONS, method = RequestMethod.GET)
  public ModelAndView getLitleEFTTransactionListToDashBoard(final HttpSession session, Map model) {
    logger.info(
        "Entering :: TransactionsEFTController :: getLitleEFTTransactionListToDashBoard method");

    ModelAndView modelAndView = new ModelAndView(LITLE_EFT_EXECUTED_TRANSACTIONS_SHOW);
    try {

      session.removeAttribute(PGConstants.BULK_SETTLEMENT_LIST_OBJ);
      session.removeAttribute(PGConstants.BULK_SETTLEMENT_LIST);

      LitleEFTRequest litleEFTRequest = new LitleEFTRequest();
      litleEFTRequest.setPageIndex(Constants.ONE);
      litleEFTRequest.setNoOfRecords(fundTransfersService.getLitleExecutedTransactionsCount());
      litleEFTRequest.setPageSize(Constants.MAX_ENTITIES_PORTAL_DISPLAY_SIZE);
      litleEFTRequest = fundTransfersService.getLitleExecutedTransactions(litleEFTRequest);

      if (litleEFTRequest != null && !CollectionUtils.isEmpty(litleEFTRequest.getLitleEFTDTOs())) {
        modelAndView =
            PaginationUtil.getPagenationModel(modelAndView, litleEFTRequest.getNoOfRecords());
        session.setAttribute("litleEFTExecutedTransList", litleEFTRequest.getLitleEFTDTOs());
        for (LitleEFTDTO litleEFTDTO : litleEFTRequest.getLitleEFTDTOs()) {
          litleEFTDTO.setTxnJsonString(JsonUtil.convertObjectToJSON(litleEFTDTO.getTxnDto()));
        }
      }
      if (litleEFTRequest != null) {
        modelAndView.addObject("eftTransactionsList", litleEFTRequest.getLitleEFTDTOs());
      }
      modelAndView.addObject("litleEFTDTO", new LitleEFTDTO());
      session.setAttribute("litleEFTRequest", litleEFTRequest);
    } catch (Exception e) {
      modelAndView.addObject(Constants.ERROR,
          messageSource.getMessage(Constants.CHATAK_GENERAL_ERROR, null, LocaleContextHolder.getLocale()));
      logger.error(
          "ERROR :: TransactionsEFTController :: getLitleEFTTransactionListToDashBoard method", e);
    }

    logger.info(
        "Exiting :: TransactionsEFTController :: getLitleEFTTransactionListToDashBoard method");
    return modelAndView;
  }

  @RequestMapping(value = LITLE_EFT_EXECUTED_TRANSACTIONS_PAGINATION, method = RequestMethod.POST)
  public ModelAndView getPaginationListToEFTTransactions(HttpServletRequest request,
      final HttpSession session, @FormParam("pageNumber") final Integer pageNumber,
      @FormParam("totalRecords") final Integer totalRecords,
      @FormParam("requestObject") String requestObject,
      @FormParam("removedTxns") String[] removedTxns, Map model) {
    logger
        .info("Entering :: TransactionsEFTController :: getPaginationListToEFTTransactions method");
    ModelAndView modelAndView = new ModelAndView(LITLE_EFT_EXECUTED_TRANSACTIONS_SHOW);
    try {
      LitleEFTRequest litleEFTRequest = (LitleEFTRequest) session.getAttribute("litleEFTRequest");
      litleEFTRequest.setPageIndex(pageNumber);
      litleEFTRequest.setNoOfRecords(fundTransfersService.getLitleExecutedTransactionsCount());
      litleEFTRequest.setPageSize(Constants.MAX_ENTITIES_PORTAL_DISPLAY_SIZE);
      litleEFTRequest = fundTransfersService.getLitleExecutedTransactions(litleEFTRequest);

      LitleEFTDTOsList litleEFTDTOsList = new LitleEFTDTOsList();
      transactionService.configureLitleReqObj(request, session, litleEFTDTOsList, requestObject,
          removedTxns);
      if (litleEFTRequest != null && !CollectionUtils.isEmpty(litleEFTRequest.getLitleEFTDTOs())) {
        modelAndView = PaginationUtil.getPagenationModelSuccessive(modelAndView, pageNumber,
            litleEFTRequest.getNoOfRecords());
        session.setAttribute("litleEFTExecutedTransList", litleEFTRequest.getLitleEFTDTOs());
        for (LitleEFTDTO litleEFTDTO : litleEFTRequest.getLitleEFTDTOs()) {
          litleEFTDTO.setTxnJsonString(JsonUtil.convertObjectToJSON(litleEFTDTO.getTxnDto()));
        }
      }
      if (litleEFTRequest != null) {
        modelAndView.addObject("eftTransactionsList", litleEFTRequest.getLitleEFTDTOs());
      }
    } catch (Exception e) {
      modelAndView.addObject(Constants.ERROR,
          messageSource.getMessage(Constants.CHATAK_GENERAL_ERROR, null, LocaleContextHolder.getLocale()));
      logger.error(
          "ERROR :: TransactionsEFTController :: getPaginationListToEFTTransactions method", e);
    }
    modelAndView.addObject(Constants.SETTLEMENT_DTO, new SettlemetActionDTO());
    modelAndView.addObject("litleEFTDTO", new LitleEFTDTO());
    logger
        .info("Exiting :: TransactionsEFTController :: getPaginationListToEFTTransactions method");
    return modelAndView;
  }

  @RequestMapping(value = CHATAK_ADMIN_BULK_EFT_ACTION, method = RequestMethod.POST)
  public ModelAndView processBulkLitleEFTAction(HttpServletRequest request,
      HttpServletResponse response, HttpSession session,
      @FormParam("requestObject") String requestObject, Map model) {
    ModelAndView modelAndView = new ModelAndView(CHATAK_ADMIN_SEARCH_TRANSACTION_PAGE);
    try {
      String jsonRequest = "{\"litleEFTDTOs\":[" + requestObject + "]}";

      LitleEFTDTOsList litleEFTDTOs =
          (LitleEFTDTOsList) JsonUtil.convertJSONToObject(jsonRequest, LitleEFTDTOsList.class);
      Response transfersResponse =
          fundTransfersService.processLitleEFT(litleEFTDTOs.getLitleEFTDTOs());
      modelAndView = getLitleEFTTransactionListToDashBoard(session, model);

      if (transfersResponse.getErrorCode().equals(Constants.SUCCESS_CODE)) {
        model.put(Constants.SUCESS,
            messageSource.getMessage("chatak.admin.bulk.eft.transfers.action.success", null,
                LocaleContextHolder.getLocale()));
      } else {
        model.put(Constants.ERROR,
            messageSource.getMessage("chatak.admin.bulk.eft.transfers.action.failure", null,
                LocaleContextHolder.getLocale()));
      }
    } catch (ChatakAdminException e) {
      logger.error("ERROR :: TransactionsEFTController :: processBulkLitleEFTAction method", e);
      modelAndView = getLitleEFTTransactionListToDashBoard(session, model);
      model.put(Constants.ERROR, e.getMessage());
      modelAndView.addObject(Constants.SETTLEMENT_DTO, new SettlemetActionDTO());
      modelAndView.addObject("litleEFTDTO", new LitleEFTDTO());
    } catch (Exception e) {
      logger.error("ERROR :: TransactionsEFTController :: processBulkLitleEFTAction method", e);
      modelAndView = getLitleEFTTransactionListToDashBoard(session, model);
      model.put(Constants.ERROR,
          messageSource.getMessage(Constants.CHATAK_GENERAL_ERROR, null, LocaleContextHolder.getLocale()));
      modelAndView.addObject(Constants.SETTLEMENT_DTO, new SettlemetActionDTO());
      modelAndView.addObject("litleEFTDTO", new LitleEFTDTO());
    }
    logger.info("Exiting :: TransactionsEFTController :: processBulkLitleEFTAction method");
    return modelAndView;
  }

  @RequestMapping(value = DASH_BOARD_EFT_SETTLEMENT_ACTION, method = RequestMethod.POST)
  public ModelAndView processDashBoardEFTTransfersAction(HttpServletRequest request,
      HttpServletResponse response, HttpSession session, LitleEFTDTO litleEFTDTO,
      BindingResult bindingResult, Map model) {
    logger
        .info("Entering :: TransactionsEFTController :: processDashBoardEFTTransfersAction method");
    ModelAndView modelAndView = new ModelAndView(LITLE_EFT_EXECUTED_TRANSACTIONS_SHOW);
    try {
      Response transfersResponse = fundTransfersService.processIndividualLitleEFT(litleEFTDTO);
      modelAndView = getLitleEFTTransactionListToDashBoard(session, model);
      if (transfersResponse.getErrorCode().equals(Constants.SUCCESS_CODE)) {
        model.put(Constants.SUCESS, messageSource.getMessage(
            "chatak.admin.eft.transfers.action.success", null, LocaleContextHolder.getLocale()));
      } else {
        model.put(Constants.ERROR, messageSource.getMessage(
            "chatak.admin.eft.transfers.action.failure", null, LocaleContextHolder.getLocale()));
      }
    } catch (ChatakAdminException e) {
      logger.error(
          "ERROR :: TransactionsEFTController :: processDashBoardEFTTransfersAction method", e);
      modelAndView = getLitleEFTTransactionListToDashBoard(session, model);
      model.put(Constants.ERROR, e.getMessage());
      modelAndView.addObject("litleEFTDTO", new LitleEFTDTO());
    } catch (Exception e) {
      logger.error(
          "ERROR :: TransactionsEFTController :: processDashBoardEFTTransfersAction method", e);
      modelAndView = getLitleEFTTransactionListToDashBoard(session, model);
      model.put(Constants.ERROR,
          messageSource.getMessage(Constants.CHATAK_GENERAL_ERROR, null, LocaleContextHolder.getLocale()));
      modelAndView.addObject("litleEFTDTO", new LitleEFTDTO());
    }
    logger
        .info("Exiting :: TransactionsEFTController :: processDashBoardEFTTransfersAction method");
    return modelAndView;
  }

  @RequestMapping(value = DOWNLOAD_EFT_TRANSACTIONS_FROM_DASHBOARD, method = RequestMethod.POST)
  public ModelAndView downloadEFTTransactionsFromDashBoard(HttpSession session, Map model,
      HttpServletRequest request, @FormParam("downLoadPageNumber") final Integer downLoadPageNumber,
      @FormParam("downloadType") final String downloadType, HttpServletResponse response) {
    logger.info(
        "Entering :: TransactionsEFTController :: downloadEFTTransactionsFromDashBoard method");
    ModelAndView modelAndView = new ModelAndView(LITLE_EFT_EXECUTED_TRANSACTIONS_SHOW);
    List<LitleEFTDTO> litleEFTRequestFromDashBoard =
        (List<LitleEFTDTO>) session.getAttribute("litleEFTExecutedTransList");
    ExportDetails exportDetails = new ExportDetails();
    try {
      if (Constants.PDF_FILE_FORMAT.equalsIgnoreCase(downloadType)) {
    	  exportDetails.setExportType(ExportType.PDF);
      } else if (Constants.XLS_FILE_FORMAT.equalsIgnoreCase(downloadType)) {
    	  exportDetails.setExportType(ExportType.XLS);
			exportDetails.setExcelStartRowNumber(Integer.parseInt("4"));
      }
      setExportDetailsDataForDownloadRoleRprt(litleEFTRequestFromDashBoard, exportDetails, response);	
	 	ExportUtil.exportData(exportDetails, response, messageSource);
      modelAndView.addObject(litleEFTRequestFromDashBoard);
    } catch (Exception e) {
      modelAndView.addObject(litleEFTRequestFromDashBoard);
      modelAndView.addObject(Constants.ERROR,
          messageSource.getMessage(Constants.CHATAK_GENERAL_ERROR, null, LocaleContextHolder.getLocale()));
      logger.error(
          "ERROR :: TransactionsEFTController :: downloadEFTTransactionsFromDashBoard method", e);
    }
    logger.info(
        "Exiting :: TransactionsEFTController :: downloadEFTTransactionsFromDashBoard method");
    return null;
  }
	private void setExportDetailsDataForDownloadRoleRprt(List<LitleEFTDTO> transactionEFT,
	      ExportDetails exportDetails, HttpServletResponse response) {
		Date date = new Date();
		String dateString = new SimpleDateFormat(Constants.EXPORT_FILE_NAME_DATE_FORMAT).format(date);
		String selectedFlag = response.getHeader("selectedFlag");
	    if (null == selectedFlag) {
	      selectedFlag = "";
	    }
	    String filename = "Transactions" + selectedFlag + dateString + ".xls";
		
	    exportDetails.setReportName(filename);
	    exportDetails.setHeaderMessageProperty("chatak.header.eftexecuted.transaction.messages");

	    exportDetails.setHeaderList(getRoleHeaderLists());
	    exportDetails.setFileData(getRoleFilesData(transactionEFT));
	  }
	private List<String> getRoleHeaderLists() {
	    String[] headerArr = {
	        messageSource.getMessage("comm.program.exportutil.date.time", null,
	            LocaleContextHolder.getLocale()),
	        messageSource.getMessage("transaction-file-exportutil-txnId", null,
		        LocaleContextHolder.getLocale()),
		    messageSource.getMessage("fundtransferfile.merchant.code", null,
		        LocaleContextHolder.getLocale()),
		    messageSource.getMessage("fundtransferfile.amount", null,
		        LocaleContextHolder.getLocale())};
	    return new ArrayList<String>(Arrays.asList(headerArr));
	  }
  private static List<Object[]> getRoleFilesData(List<LitleEFTDTO> list) {
	    List<Object[]> fileData = new ArrayList<Object[]>();

	    for (LitleEFTDTO litleEFTDTO: list) {
	    	Object[] rowData = new Object[Integer.parseInt("4")];
			  rowData[0] = (litleEFTDTO.getDateTime() != null) ? DateUtil
		                .toDateStringFormat(litleEFTDTO.getDateTime(), DateUtil.VIEW_DATE_TIME_FORMAT) + ""
		                : "";
			  rowData[1] = litleEFTDTO.getTransactionId();
			  rowData[Integer.parseInt("2")]= litleEFTDTO.getMerchantCode();
			  rowData[Integer.parseInt("3")]=(litleEFTDTO.getAmount() != null)
			            ? (PGConstants.DOLLAR_SYMBOL + StringUtils.amountToString(litleEFTDTO.getAmount())) + ""
			                    : "";
	      fileData.add(rowData);
	    }
	    return fileData;
	  }

  @RequestMapping(value = CHATAK_ADMIN_SETTLEMRNT_ACTION, method = RequestMethod.POST)
  public ModelAndView processSettlementAction(HttpServletRequest request,
      HttpServletResponse response, HttpSession session, SettlemetActionDTO actionDTO,
      BindingResult bindingResult, Map model) {
    logger.info("Entering :: TransactionsEFTController :: processSettlementAction");
    ModelAndView modelAndView = new ModelAndView(CHATAK_ADMIN_SEARCH_TRANSACTION_PAGE);
    String userName = (String)session.getAttribute(CHATAK_ADMIN_USER_NAME);
    try {
      boolean isSuccess = settlementService.updateSettlementStatus(actionDTO.getMerchantId(),
          actionDTO.getTerminalId(), actionDTO.getTxnId(), actionDTO.getTxnType(),
          actionDTO.getSettlementStatus(), actionDTO.getComments(), userName,
          actionDTO.getTimeZoneOffset(), actionDTO.getTimeZoneRegion());
      GetTransactionsListRequest transaction =
          (GetTransactionsListRequest) session.getAttribute(Constants.TRANSACTIONS_REQ_OBJ_MODEL);

      session.removeAttribute(PGConstants.BULK_SETTLEMENT_LIST_OBJ);
      session.removeAttribute(PGConstants.BULK_SETTLEMENT_LIST);

      modelAndView = transactionsController.getPaginationList(request, session,
          transaction.getPageIndex(), transaction.getNoOfRecords(), model);

      if (isSuccess) {
        if (PGConstants.PG_SETTLEMENT_REJECTED.equals(actionDTO.getSettlementStatus())) {
          modelAndView.addObject(Constants.SUCESS, messageSource.getMessage(
              "chatak.admin.settlement.txn.cancel.success", null, LocaleContextHolder.getLocale()));
          model.put(Constants.SUCESS, messageSource.getMessage(
              "chatak.admin.settlement.txn.cancel.success", null, LocaleContextHolder.getLocale()));
        } else {
          modelAndView.addObject(Constants.SUCESS, messageSource.getMessage(
              "chatak.admin.settlement.action.success", null, LocaleContextHolder.getLocale()));
          model.put(Constants.SUCESS, messageSource.getMessage(
              "chatak.admin.settlement.action.success", null, LocaleContextHolder.getLocale()));
        }
      } else {
        model.put(Constants.ERROR, messageSource.getMessage(
            "chatak.admin.settlement.action.failure", null, LocaleContextHolder.getLocale()));
      }
    } catch (ChatakAdminException e) {
      logger.error("Error :: TransactionsEFTController :: processSettlementAction ChatakAdminException", e);
      GetTransactionsListRequest transaction =
          (GetTransactionsListRequest) session.getAttribute(Constants.TRANSACTIONS_REQ_OBJ_MODEL);

      modelAndView = transactionsController.getPaginationList(request, session,
          transaction.getPageIndex(), transaction.getNoOfRecords(), model);
      model.put(Constants.ERROR,
          messageSource.getMessage(Constants.CHATAK_GENERAL_ERROR, null, LocaleContextHolder.getLocale()));
      modelAndView.addObject(Constants.SETTLEMENT_DTO, new SettlemetActionDTO());
    } catch (Exception e) {
      logger.error("Error :: TransactionsEFTController :: processSettlementAction Exception", e);
      GetTransactionsListRequest transaction =
          (GetTransactionsListRequest) session.getAttribute(Constants.TRANSACTIONS_REQ_OBJ_MODEL);

      modelAndView = transactionsController.getPaginationList(request, session,
          transaction.getPageIndex(), transaction.getNoOfRecords(), model);
      model.put(Constants.ERROR,
          messageSource.getMessage(Constants.CHATAK_GENERAL_ERROR, null, LocaleContextHolder.getLocale()));
      modelAndView.addObject(Constants.SETTLEMENT_DTO, new SettlemetActionDTO());
    }
    logger.info("Exiting :: TransactionsEFTController :: processSettlementAction method");
    return modelAndView;

  }

  @RequestMapping(value = CHATAK_ADMIN_DASH_BOARD_SETTLEMRNT_ACTION, method = RequestMethod.POST)
  public ModelAndView processDashBoardSettlementAction(HttpServletRequest request,
      HttpServletResponse response, HttpSession session, SettlemetActionDTO actionDTO,
      BindingResult bindingResult, Map model) {
    logger.info("Entering :: TransactionsEFTController :: processDashBoardSettlementAction");
    ModelAndView modelAndView = new ModelAndView(CHATAK_ADMIN_DASH_BOARD_SEARCH_TRANSACTION_PAGE);
    try {
      String userName = (String)session.getAttribute(CHATAK_ADMIN_USER_NAME);
      boolean isSuccess = settlementService.updateSettlementStatus(actionDTO.getMerchantId(),
          actionDTO.getTerminalId(), actionDTO.getTxnId(), actionDTO.getTxnType(),
          actionDTO.getSettlementStatus(), actionDTO.getComments(), userName,
          actionDTO.getTimeZoneOffset(), actionDTO.getTimeZoneRegion());
      GetTransactionsListRequest transaction =
          (GetTransactionsListRequest) session.getAttribute(Constants.TRANSACTIONS_REQ_OBJ_MODEL);

      session.removeAttribute(PGConstants.BULK_SETTLEMENT_LIST_OBJ);
      session.removeAttribute(PGConstants.BULK_SETTLEMENT_LIST);

      modelAndView = transactionsController.getPaginationList(request, session,
          transaction.getPageIndex(), transaction.getNoOfRecords(), model);

      if (isSuccess) {
        model.put(Constants.SUCESS, messageSource.getMessage(
            "chatak.admin.settlement.action.success", null, LocaleContextHolder.getLocale()));
      } else {
        model.put(Constants.ERROR, messageSource.getMessage(
            "chatak.admin.settlement.action.failure", null, LocaleContextHolder.getLocale()));
      }
    } catch (ChatakAdminException e) {
      GetTransactionsListRequest transaction =
          (GetTransactionsListRequest) session.getAttribute(Constants.TRANSACTIONS_REQ_OBJ_MODEL);
      logger.error("ERROR :: TransactionsEFTController :: processDashBoardSettlementAction method",
          e);
      modelAndView = transactionsController.getPaginationList(request, session,
          transaction.getPageIndex(), transaction.getNoOfRecords(), model);
      model.put(Constants.ERROR,
          messageSource.getMessage(Constants.CHATAK_GENERAL_ERROR, null, LocaleContextHolder.getLocale()));
      modelAndView.addObject(Constants.SETTLEMENT_DTO, new SettlemetActionDTO());
    } catch (Exception e) {
      GetTransactionsListRequest transaction =
          (GetTransactionsListRequest) session.getAttribute(Constants.TRANSACTIONS_REQ_OBJ_MODEL);
      modelAndView = transactionsController.getPaginationList(request, session,
          transaction.getPageIndex(), transaction.getNoOfRecords(), model);
      logger.error("ERROR :: TransactionsEFTController :: processDashBoardSettlementAction method",
          e);
      model.put(Constants.ERROR,
          messageSource.getMessage(Constants.CHATAK_GENERAL_ERROR, null, LocaleContextHolder.getLocale()));
      modelAndView.addObject(Constants.SETTLEMENT_DTO, new SettlemetActionDTO());
    }
    logger.info("Exiting :: TransactionsEFTController :: processDashBoardSettlementAction");
    return modelAndView;
  }

  @RequestMapping(value = CHATAK_ADMIN_SEARCH_TRANSACTION_ON_STATUS, method = RequestMethod.POST)
  public ModelAndView searchTransactionsBySettlementType(HttpServletRequest request,
      HttpServletResponse response, GetTransactionsListRequest transaction,
      BindingResult bindingResult, Map model, HttpSession session) {
    logger.info("Entering :: TransactionsEFTController :: searchMerchant method");

    session.removeAttribute(PGConstants.BULK_SETTLEMENT_LIST_OBJ);
    session.removeAttribute(PGConstants.BULK_SETTLEMENT_LIST);

    ModelAndView modelAndView = new ModelAndView(CHATAK_ADMIN_DASH_BOARD_SEARCH_TRANSACTION_PAGE);
    modelAndView.addObject(Constants.ERROR, null);
    session.setAttribute(Constants.ERROR, null);
    transaction.setPageIndex(Constants.ONE);
    transaction.setPageSize(Constants.MAX_ENTITIES_PORTAL_DISPLAY_SIZE);
    session.setAttribute(Constants.TRANSACTIONS_REQ_OBJ_MODEL, transaction);
    List<Transaction> transactionList = new ArrayList<>();
    try {
      GetTransactionsListResponse transactionResponse =
          transactionService.searchTransactionsBySettlementStatus(transaction);
      if (transactionResponse.getTransactionList() != null
          && !CollectionUtils.isEmpty(transactionResponse.getTransactionList())) {
        transactionList = transactionResponse.getTransactionList();

        modelAndView = PaginationUtil.getPagenationModel(modelAndView,
            transactionResponse.getTotalResultCount());

        transactionService.prepareAndBindTxnPopupData(transactionList);

        session.setAttribute("transactionsFromDashBoard", transactionList);
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
      logger.error("ERROR :: TransactionsEFTController :: searchMerchant method", e);
    }
    model.put("transaction", transaction);
    modelAndView.addObject(Constants.SETTLEMENT_DTO, new SettlemetActionDTO());
    logger.info("Exiting :: TransactionsEFTController :: searchMerchant method");
    return modelAndView;
  }

  @RequestMapping(value = CHATAK_PARTIAL_REFUND_BALANCE, method = RequestMethod.POST)
  public @ResponseBody String fetchPartialRefundBalance(HttpSession session, Map model,
      HttpServletRequest request,
      @FormParam("accountTransactionId") final String accountTransactionId,
      @FormParam("merchantCode") final String merchantCode, HttpServletResponse response) {
    logger.info("Entring :: TransactionsEFTController :: fetchPartialRefundBalance method");
    return transactionService.getPartialRefundBalance(accountTransactionId, merchantCode);
  }

  @RequestMapping(value = CHATAK_TRANSACTION_POPUP_DETAILS, method = RequestMethod.POST)
  public @ResponseBody TransactionPopUpDataDto fetchTransactionPopupDetails(HttpSession session,
      Map model, HttpServletRequest request,
      @FormParam("accountTransactionId") final String accountTransactionId,
      HttpServletResponse response) {
    return transactionService.fetchTransactionDetails(accountTransactionId);
  }

  @RequestMapping(value = CHATAK_ADMIN_TRANSACTION_PAGINATION_ON_STATUS,
      method = RequestMethod.POST)
  public ModelAndView getPaginationListToDashBoard(HttpServletRequest request,
      final HttpSession session, @FormParam("pageNumber") final Integer pageNumber,
      @FormParam("totalRecords") final Integer totalRecords,
      @FormParam("requestObject") String requestObject,
      @FormParam("removedTxns") String[] removedTxns, Map model) {

    logger.info("Entering :: TransactionsEFTController :: getPaginationList method");

    ModelAndView modelAndView = new ModelAndView(CHATAK_ADMIN_DASH_BOARD_SEARCH_TRANSACTION_PAGE);
    try {
      GetTransactionsListRequest transaction =
          (GetTransactionsListRequest) session.getAttribute(Constants.TRANSACTIONS_REQ_OBJ_MODEL);
      model.put("transaction", transaction);
      transaction.setPageIndex(pageNumber);
      transaction.setNoOfRecords(totalRecords);
      transaction.setPageSize(Constants.MAX_ENTITIES_PORTAL_DISPLAY_SIZE);

      SettlementActionDTOList actionDTOList = new SettlementActionDTOList();
      transactionService.configureReqObj(request, session, actionDTOList, requestObject,
          removedTxns);
      GetTransactionsListResponse transactionsList =
          transactionService.searchTransactionsBySettlementStatus(transaction);
      if (transactionsList != null
          && !CollectionUtils.isEmpty(transactionsList.getTransactionList())) {
        modelAndView = PaginationUtil.getPagenationModelSuccessive(modelAndView, pageNumber,
            transactionsList.getTotalResultCount());
        session.setAttribute("transactionsFromDashBoard", transactionsList.getTransactionList());
        transactionService.prepareAndBindTxnPopupData(transactionsList.getTransactionList());
      }
      if (transactionsList != null) {
        modelAndView.addObject(Constants.TRANSACTIONS_MODEL, transactionsList.getTransactionList());
      }
    } catch (Exception e) {
      modelAndView.addObject(Constants.ERROR,
          messageSource.getMessage(Constants.CHATAK_GENERAL_ERROR, null, LocaleContextHolder.getLocale()));
      logger.error("ERROR :: TransactionsEFTController :: getPaginationList method", e);
    }
    modelAndView.addObject(Constants.SETTLEMENT_DTO, new SettlemetActionDTO());
    logger.info("Exiting :: TransactionsEFTController :: getPaginationList method");
    return modelAndView;
  }

  @RequestMapping(value = DOWNLOAD_TRANSACTIONS_FROM_DASHBOARD, method = RequestMethod.POST)
  public ModelAndView downloadTransactionsFromDashBoard(HttpSession session, Map model,
      HttpServletRequest request, @FormParam("downLoadPageNumber") final Integer downLoadPageNumber,
      @FormParam("downloadType") final String downloadType, HttpServletResponse response) {
    logger
        .info("Entering :: TransactionsEFTController :: downloadTransactionsFromDashBoard method");
    ModelAndView modelAndView = new ModelAndView(CHATAK_ADMIN_DASH_BOARD_SEARCH_TRANSACTION_PAGE);
    List<Transaction> transactionListFromDashBoard =
        (List<Transaction>) session.getAttribute("transactionsFromDashBoard");
    ExportDetails exportDetails = new ExportDetails();
    try {
      if (Constants.PDF_FILE_FORMAT.equalsIgnoreCase(downloadType)) {
      	exportDetails.setExportType(ExportType.PDF);
      } else if (Constants.XLS_FILE_FORMAT.equalsIgnoreCase(downloadType)) {
    	  exportDetails.setExportType(ExportType.XLS);
			exportDetails.setExcelStartRowNumber(Integer.parseInt("4"));
      }
      setExportDetailsDataForDownloadRoleReport(transactionListFromDashBoard, exportDetails,response);	
	 	ExportUtil.exportData(exportDetails, response, messageSource);
      modelAndView.addObject(transactionListFromDashBoard);
    } catch (Exception e) {
      modelAndView.addObject(transactionListFromDashBoard);
      modelAndView.addObject(Constants.ERROR,
          messageSource.getMessage(Constants.CHATAK_GENERAL_ERROR, null, LocaleContextHolder.getLocale()));
      logger.error("ERROR :: TransactionsEFTController :: downloadTransactionsFromDashBoard method",
          e);
    }
    logger.info("Exiting :: TransactionsEFTController :: downloadTransactionsFromDashBoard method");
    return null;
  }
  
  private void setExportDetailsDataForDownloadRoleReport(List<Transaction> transactionList,
	      ExportDetails exportDetails,HttpServletResponse response) {
	    Date date = new Date();
		String dateString = new SimpleDateFormat(Constants.EXPORT_FILE_NAME_DATE_FORMAT).format(date);
		String selectedFlag = response.getHeader("selectedFlag");
	    if (null == selectedFlag) {
	      selectedFlag = "";
	    }
	    String filename = "Transactions" + selectedFlag + dateString + ".xls";
	  
	    exportDetails.setReportName(filename);
	    exportDetails.setHeaderMessageProperty("chatak.header.transaction.messages");

	    exportDetails.setHeaderList(getRoleHeaderList());
	    exportDetails.setFileData(getRoleFileData(transactionList));
	  }
  
  private List<String> getRoleHeaderList() {
	    String[] headerArr = {
	        messageSource.getMessage("comm.program.exportutil.date.time", null,
	            LocaleContextHolder.getLocale()),
	        messageSource.getMessage("transaction-file-exportutil-txnId", null,
	            LocaleContextHolder.getLocale()),
	        messageSource.getMessage("fundtransferfile.proc.txn.id", null,
	            LocaleContextHolder.getLocale()),
	        messageSource.getMessage("fundtransferfile.card.number", null,
	            LocaleContextHolder.getLocale()),
	        messageSource.getMessage("transaction-file-exportutil-merchantCode", null,
	            LocaleContextHolder.getLocale()),
	        messageSource.getMessage("transaction-file-exportutil-txnType", null,
		        LocaleContextHolder.getLocale()),
		    messageSource.getMessage("transaction-file-exportutil-amount", null,
		        LocaleContextHolder.getLocale()),
		    messageSource.getMessage("transaction-file-exportutil-description", null,
		        LocaleContextHolder.getLocale()),
		    messageSource.getMessage("fundtransferfile.status", null,
		        LocaleContextHolder.getLocale())};
	    return new ArrayList<String>(Arrays.asList(headerArr));
	  }
  
  private static List<Object[]> getRoleFileData(List<Transaction> list) {
	    List<Object[]> fileData = new ArrayList<Object[]>();

	    for (Transaction transactionData : list) {
	    	Object[] rowData = new Object[Integer.parseInt("9")];
			  rowData[0] = transactionData.getTransactionDate();
			  rowData[1] = transactionData.getTransactionId();
			  rowData[Integer.parseInt("2")]= ((transactionId(transactionData) != null)
		                ? validateTransactionId(transactionData)
		                        : "");
			  rowData[Integer.parseInt("3")]= transactionData.getMaskCardNumber();
			  rowData[Integer.parseInt("4")]= transactionData.getMerchant_code();
			  rowData[Integer.parseInt("5")]= transactionData.getTransaction_type();
			  rowData[Integer.parseInt("6")]= transactionData.getTransactionAmount();
			  rowData[Integer.parseInt("7")]= transactionData.getTxnDescription();
			  rowData[Integer.parseInt("8")]= transactionData.getMerchantSettlementStatus();
	      fileData.add(rowData);
	    }
	    return fileData;
	  }
  
  private static Long transactionId(Transaction transaction) {
	    return transaction.getRef_transaction_id();
	  }
  private static Object validateTransactionId(Transaction transaction) {
	    return 0L == transactionId(transaction) ? "NA"
	        : transactionId(transaction);
	  }
}

