package com.chatak.acquirer.admin.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chatak.acquirer.admin.exception.ChatakAdminException;
import com.chatak.acquirer.admin.service.TransactionService;
import com.chatak.acquirer.admin.util.JsonUtil;
import com.chatak.acquirer.admin.util.StringUtil;
import com.chatak.pg.acq.dao.AccountDao;
import com.chatak.pg.acq.dao.AccountTransactionsDao;
import com.chatak.pg.acq.dao.CurrencyConfigDao;
import com.chatak.pg.acq.dao.ExecutedTransactionDao;
import com.chatak.pg.acq.dao.MerchantUpdateDao;
import com.chatak.pg.acq.dao.RefundTransactionDao;
import com.chatak.pg.acq.dao.TransactionDao;
import com.chatak.pg.acq.dao.TransactionReportDao;
import com.chatak.pg.acq.dao.VoidTransactionDao;
import com.chatak.pg.acq.dao.model.PGAccount;
import com.chatak.pg.acq.dao.model.PGAccountTransactions;
import com.chatak.pg.acq.dao.model.PGCurrencyConfig;
import com.chatak.pg.acq.dao.model.PGMerchant;
import com.chatak.pg.acq.dao.model.PGTransaction;
import com.chatak.pg.bean.TransactionPopUpDataDto;
import com.chatak.pg.constants.AccountTransactionCode;
import com.chatak.pg.constants.PGConstants;
import com.chatak.pg.enums.EntryModeEnum;
import com.chatak.pg.enums.EntryModePortalDisplayEnum;
import com.chatak.pg.model.LitleEFTDTO;
import com.chatak.pg.model.LitleEFTDTOsList;
import com.chatak.pg.model.ReportsDTO;
import com.chatak.pg.model.SettlementActionDTOList;
import com.chatak.pg.model.SettlemetActionDTO;
import com.chatak.pg.user.bean.GetTransactionsListRequest;
import com.chatak.pg.user.bean.GetTransactionsListResponse;
import com.chatak.pg.user.bean.Transaction;
import com.chatak.pg.util.CommonUtil;
import com.chatak.pg.util.Constants;
import com.chatak.pg.util.DateUtil;
import com.chatak.pg.util.StringUtils;

@Service
public class TransactionServiceImpl implements TransactionService {

  private Logger logger = Logger.getLogger(TransactionServiceImpl.class);

  @Autowired
  private TransactionDao transactionDao;

  @Autowired
  private AccountDao accountDao;

  @Autowired
  private AccountTransactionsDao accountTransactionsDao;

  @Autowired
  private CurrencyConfigDao currencyConfigDao;

  @Autowired
  private TransactionReportDao transactionReportDao;

  @Autowired
  private VoidTransactionDao voidTransactionDao;

  @Autowired
  private RefundTransactionDao refundTransactionDao;

  @Autowired
  private MerchantUpdateDao merchantUpdateDao;

  @Autowired
  private ExecutedTransactionDao executedTransactionDao;

  @Override
  public GetTransactionsListResponse searchTransactions(
      GetTransactionsListRequest getTransactionsListRequest , Long entityId) throws ChatakAdminException {
    logger.info("Entering :: TransactionServiceImpl :: searchTransactions method");
    GetTransactionsListResponse response = new GetTransactionsListResponse();
    try {
      List<Transaction> transactions = transactionDao.getTransactions(getTransactionsListRequest , entityId);

      if (transactions != null) {
        response.setTransactionList(transactions);
        response.setTotalResultCount(getTransactionsListRequest.getNoOfRecords());
        response.setErrorMessage(Constants.SUCESS);
        response.setErrorCode(Constants.SUCCESS_CODE);
      } else {
        response.setTransactionList(transactions);
        response.setTotalResultCount(getTransactionsListRequest.getNoOfRecords());
        response.setErrorMessage(Constants.ERROR);
        response.setErrorCode(Constants.ERROR_CODE);
      }
      logger.info("Exiting :: TransactionServiceImpl :: searchTransactions method");
      return response;
    } catch (Exception exp) {
      logger.error("Error :: TransactionServiceImpl :: searchTransactions method", exp);
      response.setErrorCode(Constants.ERROR);
      response.setErrorMessage(Constants.ERROR_DATA);
    }
    return null;
  }

  /**
   * @param status
   * @return
   * @throws ChatakAdminException
   */
  @Override
  public GetTransactionsListResponse searchTransactionsBySettlementStatus(
      GetTransactionsListRequest getTransactionsListRequest) throws ChatakAdminException {
    logger
        .info("Entering :: TransactionServiceImpl :: searchTransactionsBySettlementStatus method");
    GetTransactionsListResponse response = new GetTransactionsListResponse();
    try {
      List<Transaction> transactions =
          voidTransactionDao.getTransactionListToDashBoard(getTransactionsListRequest);
      setTransactionsListResponse(getTransactionsListRequest, response, transactions);
    } catch (Exception e) {
      logger.error("Error :: TransactionServiceImpl :: searchTransactionsBySettlementStatus method",
          e);
      response.setErrorCode(Constants.ERROR);
      response.setErrorMessage(Constants.ERROR_DATA);
    }
    logger.info("Exiting :: TransactionServiceImpl :: searchTransactionsBySettlementStatus method");
    return response;
  }

  @Override
  public GetTransactionsListResponse getAllTransactions(
      GetTransactionsListRequest getTransactionsListRequest, Long entityID) throws ChatakAdminException {
    logger.info("Entering :: TransactionServiceImpl :: getAllTransactions method");
    GetTransactionsListResponse response = new GetTransactionsListResponse();
    try {
      List<Transaction> transactions = transactionDao.getTransactions(getTransactionsListRequest,entityID);
      List<PGTransaction> pgTransactions = transactionDao.getAllTransactions();
      if (pgTransactions != null) {
        transactions = new ArrayList<>(pgTransactions.size());
        Transaction transactionResp = null;

        for (PGTransaction pgTransaction : pgTransactions) {
          PGAccount account = accountDao.getPgAccount(pgTransaction.getMerchantId());
          PGMerchant merchant = merchantUpdateDao.getMerchant(pgTransaction.getMerchantId());
          transactionResp = new Transaction();
          transactionResp.setTransactionId(pgTransaction.getTransactionId());
          transactionResp.setTransactionAmount(
              PGConstants.DOLLAR_SYMBOL + StringUtils.amountToString(pgTransaction.getTxnAmount()));
          transactionResp.setTransactionDate(DateUtil
              .toDateStringFormat(pgTransaction.getCreatedDate(), DateUtil.VIEW_DATE_TIME_FORMAT));
          transactionResp.setTransaction_type(pgTransaction.getTransactionType());
          transactionResp.setMerchant_code(pgTransaction.getMerchantId());
          transactionResp.setMaskCardNumber(pgTransaction.getPanMasked());
          transactionResp.setProcTxnCode(pgTransaction.getProcCode());
          transactionResp
              .setRef_transaction_id((StringUtils.isEmpty(pgTransaction.getIssuerTxnRefNum()) ? 0L
                  : Long.valueOf(pgTransaction.getIssuerTxnRefNum())));
          transactionResp.setMerchantFeeAmount(pgTransaction.getMerchantFeeAmount());
          transactionResp.setTxnDescription(pgTransaction.getTxnDescription());
          transactionResp.setMerchantSettlementStatus(pgTransaction.getMerchantSettlementStatus());
          transactionResp.setTerminal_id(Long.valueOf(pgTransaction.getTerminalId()));
          transactionResp.setId(pgTransaction.getId());
          transactionResp.setAccountNumber(account.getAccountNum());
          transactionResp.setMerchantType(account.getEntityType());
          transactionResp.setMerchantName(merchant.getFirstName());
          transactionResp.setTxn_ref_num((StringUtils.isEmpty(pgTransaction.getRefTransactionId())
              ? 0L : Long.valueOf(pgTransaction.getRefTransactionId())));
          transactionResp.setAcqChannel(pgTransaction.getAcqChannel());
          pgTransaction = setPosEntryMode(pgTransaction);
          transactionResp.setEntryMode(EntryModePortalDisplayEnum
              .valueOf(getEntryModeEnumFromPosEntryMode(pgTransaction.getPosEntryMode())).value());
          transactionResp = setStatusMessage(transactionResp, pgTransaction);
          transactions.add(transactionResp);
        }
        setErrorCodeAndMsg(response, transactions);
      } else {
        setErrorCodeAndMsg(response, transactions);
      }
      logger.info("Exiting :: TransactionServiceImpl :: getAllTransactions method");
      return response;
    } catch (Exception exp) {
      logger.error("Error :: TransactionServiceImpl :: getAllTransactions Method", exp);
      response.setErrorCode(Constants.ERROR);
      response.setErrorMessage(Constants.ERROR_DATA);
    }
    return null;
  }

  private void setErrorCodeAndMsg(GetTransactionsListResponse response, List<Transaction> transactions) {
	response.setTransactionList(transactions);
	response.setErrorCode(Constants.SUCCESS_CODE);
	response.setErrorMessage(Constants.SUCESS);
  }

  private PGTransaction setPosEntryMode(PGTransaction pgTransaction) {
    if (pgTransaction.getPosEntryMode() == null) {
      pgTransaction.setPosEntryMode(Constants.SUCCESS_CODE);
    }
    return pgTransaction;
  }

  private Transaction setStatusMessage(Transaction transactionResp, PGTransaction pgTransaction) {
    if (pgTransaction.getStatus() == 0) {
      transactionResp.setStatusMessage("Approved");
    } else if (pgTransaction.getStatus() == 1) {
      transactionResp.setStatusMessage("Declined");
    } else {
      transactionResp.setStatusMessage("Failed");
    }
    return transactionResp;
  }

  @Override
  public List<ReportsDTO> getAllAccountsExecutedTransactions() {
    return executedTransactionDao.getAllAccountsExecutedTransactionsReport();
  }

  @Override
  public GetTransactionsListResponse getAllTransactionsOnMerchantCode(
      GetTransactionsListRequest getTransactionsListRequest) {
    GetTransactionsListResponse response = new GetTransactionsListResponse();
    try {
      List<Transaction> transactions = null;
      List<ReportsDTO> reportsDTOList =
          transactionReportDao.getAllTransactionsReport(getTransactionsListRequest);
      if (reportsDTOList != null) {
        transactions = new ArrayList<Transaction>(reportsDTOList.size());
        Transaction transactionResp = null;

        for (ReportsDTO reportsDTO : reportsDTOList) {
          transactionResp = new Transaction();
          transactionResp.setTransactionId(reportsDTO.getTransactionId());
          transactionResp.setTransactionAmount(reportsDTO.getAmount());
          transactionResp.setAvailableBalance(reportsDTO.getAvailableBalance());
          transactionResp.setTransactionDate(reportsDTO.getDateTime());
          transactionResp.setTransaction_type(reportsDTO.getCardType());
          transactionResp.setPayment_method(reportsDTO.getPaymentMethod());
          transactionResp.setTxnDescription(reportsDTO.getDescription());

          transactionResp.setStatusMessage(reportsDTO.getStatus());
          transactionResp
              .setTxnJsonString(JsonUtil.convertObjectToJSON(reportsDTO.getTxnPopupDto()));

          transactions.add(transactionResp);

        }
        setErrorCodeAndMsg(response, transactions);
      } else {
        setErrorCodeAndMsg(response, transactions);
      }
      logger.info("Exiting :: TransactionServiceImpl :: getAllTransactionsOnMerchantCode method");
      return response;
    } catch (Exception e) {
      logger.error("Error :: TransactionServiceImpl :: getAllTransactionsOnMerchantCode method", e);
      response.setErrorCode(Constants.ERROR);
      response.setErrorMessage(Constants.ERROR_DATA);
    }
    return null;
  }

  @Override
  public GetTransactionsListResponse searchTransactionsReport(
      GetTransactionsListRequest getTransactionsListRequest) throws ChatakAdminException {
    logger.info("Entering :: TransactionServiceImpl :: searchTransactionsReport method");
    GetTransactionsListResponse response = new GetTransactionsListResponse();
    try {
      List<Transaction> transactions = null;
      List<ReportsDTO> pgTransactions =
          transactionReportDao.getTransactionListReport(getTransactionsListRequest);
      if (pgTransactions != null) {
        transactions = new ArrayList<Transaction>(pgTransactions.size());
        Transaction transactionResp = null;

        for (ReportsDTO pgTransaction : pgTransactions) {
          transactionResp = new Transaction();
          transactionResp.setTransactionId(pgTransaction.getTransactionId());
          transactionResp.setTransactionAmount(pgTransaction.getAmount());
          transactionResp.setTransactionDate(pgTransaction.getDateTime());
          transactionResp.setTxnDescription(pgTransaction.getDescription());
          transactionResp.setTransaction_type(pgTransaction.getCardType());
          transactionResp.setPayment_method(pgTransaction.getPaymentMethod());
          transactionResp.setAvailableBalance(pgTransaction.getAvailableBalance());
          transactionResp
              .setTxnJsonString(JsonUtil.convertObjectToJSON(pgTransaction.getTxnPopupDto()));
          transactions.add(transactionResp);
        }
        setTransactionsListResponse(getTransactionsListRequest, response, transactions);
      } else {
        setTransactionsListResponse(getTransactionsListRequest, response, transactions);
      }
      logger.info("Exiting :: TransactionServiceImpl :: searchTransactionsReport method");
      return response;
    } catch (Exception exp) {
      logger.error("Error :: TransactionServiceImpl :: searchTransactionsReport method", exp);
      response.setErrorCode(Constants.ERROR);
      response.setErrorMessage(Constants.ERROR_DATA);
    }
    return null;
  }

  private void setTransactionsListResponse(GetTransactionsListRequest getTransactionsListRequest,
		GetTransactionsListResponse response, List<Transaction> transactions) {
	response.setTransactionList(transactions);
	response.setTotalResultCount(getTransactionsListRequest.getNoOfRecords());
	response.setErrorCode(Constants.SUCCESS_CODE);
	response.setErrorMessage(Constants.SUCESS);
  }

  @Override
  public List<ReportsDTO> getAllAccountsExecutedTransactionsOnDate(
      GetTransactionsListRequest getTransactionsListRequest) {
    return executedTransactionDao.getAllAccountsExecutedTransactionsOnDate(getTransactionsListRequest);
  }

  @Override
  public GetTransactionsListResponse getAllAccountsPendingTransactionsOnDate(
      GetTransactionsListRequest getTransactionsListRequest) {
    GetTransactionsListResponse response = new GetTransactionsListResponse();
    try {
      List<Transaction> transactions = null;
      List<ReportsDTO> pgTransactions = executedTransactionDao
          .getAllAccountsExecutedTransactionsOnDate(getTransactionsListRequest);
      if (pgTransactions != null) {
        transactions = new ArrayList<Transaction>(pgTransactions.size());
        Transaction transactionResp = null;
        for (ReportsDTO pgTransaction : pgTransactions) {
          transactionResp = new Transaction();
          transactionResp
              .setTxnJsonString(JsonUtil.convertObjectToJSON(pgTransaction.getTxnPopupDto()));
          transactions.add(transactionResp);
        }
        setTransactionsListResponse(getTransactionsListRequest, response, transactions);
      } else {
        setTransactionsListResponse(getTransactionsListRequest, response, transactions);
      }
      logger.info(
          "Exiting :: TransactionServiceImpl :: getAllAccountsPendingTransactionsOnDate method");
      return response;
    } catch (Exception e) {
      logger.error(
          "Error :: TransactionServiceImpl :: getAllAccountsPendingTransactionsOnDate method", e);
      response.setErrorCode(Constants.ERROR);
      response.setErrorMessage(Constants.ERROR_DATA);
    }
    return null;
  }

  @Override
  public List<ReportsDTO> getAllManualTransactionListReport(
      GetTransactionsListRequest getTransactionsListRequest) {
    return transactionReportDao.getTransactionListReport(getTransactionsListRequest);
  }

  @Override
  public List<ReportsDTO> getAllTransactionsOnDate(
      GetTransactionsListRequest getTransactionsListRequest) {
    return transactionReportDao.getAllTransactionsOnDate(getTransactionsListRequest);
  }

  @Override
  public List<ReportsDTO> getAllExecutedAccTransFeeOnDate(
      GetTransactionsListRequest getTransactionsListRequest) {
    return executedTransactionDao.getAllExecutedAccFeeOnDate(getTransactionsListRequest);
  }

  @Override
  public List<ReportsDTO> getSystemOverViewData() {
    return accountDao.getOverViewData();
  }

  public String getEntryModeEnumFromPosEntryMode(String posEntryMode) {
    try {
      return EntryModeEnum.fromValue(posEntryMode.substring(0, Constants.TWO)).name();
    } catch (Exception e) {
      logger.error("Error :: TransactionServiceImpl :: getEntryModeEnumFromPosEntryMode method", e);
      return EntryModeEnum.UNSPECIFIED.name();
    }
  }

  @Override
  public List<ReportsDTO> getVirtualAccountFeeLogOnDate(
      GetTransactionsListRequest getTransactionsListRequest) {
    List<ReportsDTO> virtualFeeLogList =
        transactionReportDao.getVirtualAccountFeeLogOnDate(getTransactionsListRequest);
    if (StringUtil.isListNotNullNEmpty(virtualFeeLogList)) {
      for (ReportsDTO virtualFeeList : virtualFeeLogList) {
        if (virtualFeeList.getStatus().equalsIgnoreCase("0")) {
          virtualFeeList.setStatus("SUCCESS");
        }
        if (virtualFeeList.getStatus().equalsIgnoreCase("2")) {
          virtualFeeList.setStatus("DECLINED");
        }
        if (virtualFeeList.getStatus().equalsIgnoreCase("3")) {
          virtualFeeList.setStatus("NETWORK FAIL");
        }
      }
    }
    return virtualFeeLogList;
  }

  @Override
  public GetTransactionsListResponse searchAllTransactions(
      GetTransactionsListRequest getTransactionsListRequest) throws ChatakAdminException {
    logger.info("Entering :: TransactionServiceImpl :: method");
    GetTransactionsListResponse response = new GetTransactionsListResponse();
    try {
      List<Transaction> transactions =
          voidTransactionDao.getSearchTransactions(getTransactionsListRequest);
      setTransactionsListResponse(getTransactionsListRequest, response, transactions);
      logger.info("Exiting :: TransactionServiceImpl :: searchAllTransactions method");
      return response;
    } catch (Exception e) {
      logger.error("Error :: TransactionServiceImpl :: searchAllTransactions method", e);
      response.setErrorCode(Constants.ERROR);
      response.setErrorMessage(Constants.ERROR_DATA);
    }
    return null;
  }

  @Override
  public void configureReqObj(HttpServletRequest request, HttpSession session,
      SettlementActionDTOList actionDTOList, String requestObject, String[] removedTxns)
          throws ChatakAdminException {

    if (null != session.getAttribute(PGConstants.BULK_SETTLEMENT_LIST_OBJ)) {
      if (!StringUtil.isNullAndEmpty(requestObject)) {
        String jsonRequest = "{\"actionDTOs\":[" + requestObject + "]}";
        actionDTOList = (SettlementActionDTOList) JsonUtil.convertJSONToObject(jsonRequest,
            SettlementActionDTOList.class);
      }
      SettlementActionDTOList tempList =
          (SettlementActionDTOList) session.getAttribute(PGConstants.BULK_SETTLEMENT_LIST_OBJ);
      List<SettlemetActionDTO> settlemetActionDTOList = tempList.getActionDTOs();
      List<String> removedTxnsList = Arrays.asList((null != removedTxns && removedTxns.length > 0
          && !StringUtil.isNullAndEmpty(removedTxns[0])) ? removedTxns : new String[] {});
      
      removeDuplicateSettlementList(settlemetActionDTOList, removedTxnsList);

      actionDTOList = setActionDTOs(actionDTOList, settlemetActionDTOList);
      session.setAttribute(PGConstants.BULK_SETTLEMENT_LIST_OBJ, actionDTOList);
      session.setAttribute(PGConstants.BULK_SETTLEMENT_LIST,
          (null != actionDTOList.getActionDTOs() && actionDTOList.getActionDTOs().size() > 0)
              ? JsonUtil.convertObjectToJSON(actionDTOList)
              : JsonUtil.convertObjectToJSON(new SettlementActionDTOList()));
    } else if (!StringUtil.isNullAndEmpty(requestObject)) {
      String jsonRequest = "{\"actionDTOs\":[" + requestObject + "]}";
      actionDTOList = (SettlementActionDTOList) JsonUtil.convertJSONToObject(jsonRequest,
          SettlementActionDTOList.class);
      session.setAttribute(PGConstants.BULK_SETTLEMENT_LIST_OBJ, actionDTOList);
      session.setAttribute(PGConstants.BULK_SETTLEMENT_LIST,
          (null != actionDTOList.getActionDTOs() && actionDTOList.getActionDTOs().size() > 0)
              ? JsonUtil.convertObjectToJSON(actionDTOList)
              : JsonUtil.convertObjectToJSON(new SettlementActionDTOList()));
    }
  }

  private SettlementActionDTOList setActionDTOs(SettlementActionDTOList actionDTOList,
      List<SettlemetActionDTO> settlemetActionDTOList) {
    if (null != actionDTOList.getActionDTOs()) {
      actionDTOList.getActionDTOs().addAll(settlemetActionDTOList);
    } else {
      actionDTOList.setActionDTOs(settlemetActionDTOList);
    }
    return actionDTOList;
  }

  private void removeDuplicateSettlementList(List<SettlemetActionDTO> settlemetActionDTOList,
      List<String> removedTxnsList) {
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

  @Override
  public void configureLitleReqObj(HttpServletRequest request, HttpSession session,
      LitleEFTDTOsList litleEFTDTOsList, String requestObject, String[] removedTxns)
          throws ChatakAdminException {

    if (null != session.getAttribute(PGConstants.BULK_SETTLEMENT_LIST_OBJ)) {
      if (!StringUtil.isNullAndEmpty(requestObject)) {
        String jsonRequest = "{\"litleEFTDTOs\":[" + requestObject + "]}";
        litleEFTDTOsList =
            (LitleEFTDTOsList) JsonUtil.convertJSONToObject(jsonRequest, LitleEFTDTOsList.class);
      }

      LitleEFTDTOsList tempList =
          (LitleEFTDTOsList) session.getAttribute(PGConstants.BULK_SETTLEMENT_LIST_OBJ);
      List<LitleEFTDTO> litleEFTDTOList = tempList.getLitleEFTDTOs();
      List<String> removedTxnsList = Arrays.asList(removedTxns);
      
      removeDuplicateEFTDTOTxns(litleEFTDTOList, removedTxnsList);

      if (null != litleEFTDTOsList.getLitleEFTDTOs()) {
        litleEFTDTOsList.getLitleEFTDTOs().addAll(litleEFTDTOList);
      } else {
        litleEFTDTOsList.setLitleEFTDTOs(litleEFTDTOList);
      }
      session.setAttribute(PGConstants.BULK_SETTLEMENT_LIST_OBJ, litleEFTDTOsList);
      session.setAttribute(PGConstants.BULK_SETTLEMENT_LIST,
          (null != litleEFTDTOsList.getLitleEFTDTOs()
              && litleEFTDTOsList.getLitleEFTDTOs().size() > 0)
                  ? JsonUtil.convertObjectToJSON(litleEFTDTOsList)
                  : JsonUtil.convertObjectToJSON(new LitleEFTDTOsList()));
    } else if (!StringUtil.isNullAndEmpty(requestObject)) {
      String jsonRequest = "{\"litleEFTDTOs\":[" + requestObject + "]}";
      litleEFTDTOsList =
          (LitleEFTDTOsList) JsonUtil.convertJSONToObject(jsonRequest, LitleEFTDTOsList.class);
      session.setAttribute(PGConstants.BULK_SETTLEMENT_LIST_OBJ, litleEFTDTOsList);
      session.setAttribute(PGConstants.BULK_SETTLEMENT_LIST,
          (null != litleEFTDTOsList.getLitleEFTDTOs()
              && litleEFTDTOsList.getLitleEFTDTOs().size() > 0)
                  ? JsonUtil.convertObjectToJSON(litleEFTDTOsList)
                  : JsonUtil.convertObjectToJSON(new LitleEFTDTOsList()));
    }
  }

  private void removeDuplicateEFTDTOTxns(List<LitleEFTDTO> litleEFTDTOList,
      List<String> removedTxnsList) {
    if (removedTxnsList.size() > 0) {
      String txnId = "";
      Iterator<LitleEFTDTO> listItr = litleEFTDTOList.iterator();
      while (listItr.hasNext()) {
        txnId = listItr.next().getTransactionId();
        if (removedTxnsList.contains(txnId))
          listItr.remove();
      }
    }
  }

  public void prepareAndBindTxnPopupData(List<Transaction> transactionList)
      throws ChatakAdminException {
    TransactionPopUpDataDto txnDto = new TransactionPopUpDataDto();
    for (Transaction txn : transactionList) {
      String currency = null;
      if (null != txn.getFeeString() && !"".equals(txn.getFeeString()))
        currency = txn.getFeeString().substring(0, Constants.FOUR);
      else
        currency = PGConstants.DOLLAR_SYMBOL;
      txnDto.setTransaction_type(getTxnDtoData(txn.getTransaction_type()));
      txnDto.setAuth_id(getTxnDtoData(txn.getAuth_id()));
      txnDto.setTransactionId(getTxnDtoData(txn.getTransactionId()));
      txnDto.setRef_transaction_id(((txn.getTxn_ref_num() != null) && (txn.getTxn_ref_num()) != 0L)
          ? txn.getTxn_ref_num().toString() : "N/A");
      txnDto.setTerminal_id(getTxnDtoData(CommonUtil.validateTerminalId(txn.getTerminal_id().toString())));
      txnDto.setInvoice_number(getTxnDtoData(txn.getInvoice_number()));
      txnDto.setMaskCardNumber(getTxnDtoData(txn.getMaskCardNumber()));
      txnDto.setMerchant_code(getTxnDtoData(txn.getMerchant_code()));
      txnDto.setAcqTxnMode(getTxnDtoData(txn.getAcqTxnMode()));
      txnDto.setAcqChannel(getTxnDtoData(txn.getAcqChannel()));
      txnDto.setTransactionDate(getTxnDtoData(txn.getTransactionDate()));
      txnDto.setTransactionAmount((txn.getTransactionAmount() != null) ? txn.getTransactionAmount()
          : PGConstants.DOLLAR_SYMBOL + "0.00");
      txnDto.setFee_amount(
          (txn.getFeeString() != null) ? txn.getFeeString() : PGConstants.DOLLAR_SYMBOL + "0.00");
      txnDto.setTxn_total_amount((txn.getTxn_total_amount() != null)
          ? currency + txn.getTxn_total_amount() : currency + "0.00");
      txnDto.setProcessor(getTxnDtoData(txn.getProcessor()));
      txnDto.setStatusMessage(getTxnDtoData(txn.getStatusMessage()));
      txnDto.setMerchantSettlementStatus(getTxnDtoData(txn.getMerchantSettlementStatus()));
      txnDto.setTxnDescription(getTxnDtoData(txn.getTxnDescription()));
      txnDto.setCardHolderName(getTxnDtoData(txn.getCardHolderName()));
      txnDto.setMerchantBusinessName(getTxnDtoData(txn.getMerchantBusinessName()));
      txnDto.setMerchantType(getTxnDtoData(txn.getMerchantType()));
      txnDto.setBatchId(getTxnDtoData(txn.getBatchId()));
      txnDto.setDeviceLocalTxnTime(txn.getDeviceLocalTxnTime());
      txnDto.setTimeZoneOffset(txn.getTimeZoneOffset());
      txn.setTxnJsonString(JsonUtil.convertObjectToJSON(txnDto));
    }
  }

  private String getTxnDtoData(String txnData) {
    return ((txnData != null) ? txnData : "");
  }

  @Override
  public GetTransactionsListResponse searchAccountTransactions(
      GetTransactionsListRequest getTransactionsListRequest) {
    return accountTransactionsDao.getAccountTransactions(getTransactionsListRequest);
  }

  @Override
  public TransactionPopUpDataDto fetchTransactionDetails(String accountTransactionId) {
    logger.info("Entering :: TransactionServiceImpl :: fetchTransactionDetails method");
    TransactionPopUpDataDto transactionPopUpDto = null;
    List<PGAccountTransactions> pgAccountTransactions = null;
    PGAccountTransactions accountTransactions = null;
    try {
      pgAccountTransactions = accountTransactionsDao.getAccountTransactions(accountTransactionId);
      if (CommonUtil.isListNotNullAndEmpty(pgAccountTransactions)) {
        accountTransactions = pgAccountTransactions.get(0);
        if (null != accountTransactions.getPgTransactionId()) {
          logger.info(
              "Entering :: TransactionServiceImpl :: fetchTransactionDetails method:: CC Transactions");
          transactionPopUpDto = refundTransactionDao
              .getTransactionPopUpDataDto(accountTransactions.getPgTransactionId());
          transactionPopUpDto.setAccountTransactionId(accountTransactionId);
        } else {
          logger.info("TransactionServiceImpl :: fetchTransactionDetails type :: "
              + accountTransactions.getTransactionType());
          String txnAmount = PGConstants.DOLLAR_SYMBOL
              + StringUtils.amountToString(accountTransactions.getDebit());
          transactionPopUpDto = new TransactionPopUpDataDto();
          transactionPopUpDto = getTransactionPopupData(accountTransactions, transactionPopUpDto, txnAmount);
        }
      } else {
        logger.info(
            " TransactionServiceImpl :: fetchTransactionDetails method:: emppty account transaction list");
        transactionPopUpDto = new TransactionPopUpDataDto();
        transactionPopUpDto.setDtoType(Constants.TXN_UNKNOWN);
      }
    } catch (Exception e) {
      logger.error(" ERROR :TransactionServiceImpl :: fetchTransactionDetails method", e);
      transactionPopUpDto = new TransactionPopUpDataDto();
      transactionPopUpDto.setDtoType(Constants.TXN_UNKNOWN);
    }
    logger.info("Exiting :: TransactionServiceImpl :: fetchTransactionDetails method");
    return transactionPopUpDto;
  }

  private TransactionPopUpDataDto getTransactionPopupData(PGAccountTransactions accountTransactions,
      TransactionPopUpDataDto transactionPopUpDto, String txnAmount) {
    switch (accountTransactions.getTransactionType()) {
      case PGConstants.FUND_TRANSFER_EFT:
      case PGConstants.FUND_TRANSFER_CHECK:
        transactionPopUpDto.setFromAccount(accountTransactions.getAccountNumber());
        transactionPopUpDto.setTransactionAmount(PGConstants.DOLLAR_SYMBOL
            + StringUtils.amountToString(accountTransactions.getDebit()));
        transactionPopUpDto.setDtoType(Constants.TXN_EFT);
        break;
      case AccountTransactionCode.MANUAL_DEBIT:
        transactionPopUpDto.setFromAccount(accountTransactions.getAccountNumber());
        transactionPopUpDto.setTransactionAmount(
            PGConstants.DOLLAR_SYMBOL + StringUtils.amountToString(accountTransactions.getDebit()));
        transactionPopUpDto.setDtoType(Constants.TXN_MANUAL_DEBIT);
        break;
      case AccountTransactionCode.MANUAL_CREDIT:
        transactionPopUpDto.setToAccount(accountTransactions.getAccountNumber());
        transactionPopUpDto.setTransactionAmount(PGConstants.DOLLAR_SYMBOL
            + StringUtils.amountToString(accountTransactions.getCredit()));
        transactionPopUpDto.setDtoType(Constants.TXN_MANUAL_CREDIT);
        break;
      case AccountTransactionCode.ACCOUNT_CREDIT:
      case AccountTransactionCode.ACCOUNT_DEBIT:
        transactionPopUpDto.setToAccount(accountTransactions.getAccountNumber());
        transactionPopUpDto.setFromAccount(accountTransactions.getToAccountNumber());
        transactionPopUpDto.setTransactionAmount(txnAmount);
        transactionPopUpDto.setDtoType(Constants.TXN_ACCOUNT);
        break;
      default:
        transactionPopUpDto.setDtoType(Constants.TXN_UNKNOWN);
        break;
    }
    return transactionPopUpDto;
  }

  @Override
  public String getPartialRefundBalance(String accountTransactionId, String merchantCode) {
    PGTransaction pgTransaction = null;
    Long refundedAmount = null;
    String pgTransactionId =
        accountTransactionsDao.getSaleAccountTransactionId(accountTransactionId, merchantCode);
    if (pgTransactionId != null) {
      refundedAmount = refundTransactionDao.getRefundedAmountOnTxnId(pgTransactionId);
      PGMerchant pgMerchant = merchantUpdateDao.getMerchantByCode(merchantCode);
      String localCurrency = pgMerchant.getLocalCurrency();
      pgTransaction = transactionDao.getTransaction(merchantCode,
          merchantCode.substring(merchantCode.length() - Constants.EIGHT, merchantCode.length()),
          pgTransactionId);
      if (null != refundedAmount && null != pgTransaction && null != localCurrency) {
        // Returning balance amount for refund
        return localCurrency + " "
            + StringUtils.amountToString(pgTransaction.getTxnTotalAmount() - refundedAmount);

      } else if (null != pgTransaction) {
        // Returning total transaction amount
        return localCurrency + " " + StringUtils.amountToString(pgTransaction.getTxnTotalAmount());
      }
    }
    return null;
  }
  @Override
  public GetTransactionsListResponse searchAccountTransactionsForEntityId(GetTransactionsListRequest getTransactionsListRequest,
		  Long entityId, String userType) {
	  return accountTransactionsDao.searchAccountTransactionsForEntityId(getTransactionsListRequest, entityId, userType);
  }
}
