/**
 * 
 */
package com.chatak.merchant.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chatak.merchant.controller.model.LoginResponse;
import com.chatak.merchant.exception.ChatakMerchantException;
import com.chatak.merchant.service.TransactionService;
import com.chatak.merchant.util.JsonUtil;
import com.chatak.merchant.util.StringUtil;
import com.chatak.pg.acq.dao.AccountDao;
import com.chatak.pg.acq.dao.AccountHistoryDao;
import com.chatak.pg.acq.dao.AccountTransactionsDao;
import com.chatak.pg.acq.dao.ExecutedTransactionDao;
import com.chatak.pg.acq.dao.MerchantDao;
import com.chatak.pg.acq.dao.MerchantUpdateDao;
import com.chatak.pg.acq.dao.MerchantUserDao;
import com.chatak.pg.acq.dao.RefundTransactionDao;
import com.chatak.pg.acq.dao.TransactionDao;
import com.chatak.pg.acq.dao.VoidTransactionDao;
import com.chatak.pg.acq.dao.model.PGAccount;
import com.chatak.pg.acq.dao.model.PGAccountHistory;
import com.chatak.pg.acq.dao.model.PGAccountTransactions;
import com.chatak.pg.acq.dao.model.PGMerchant;
import com.chatak.pg.acq.dao.model.PGTransaction;
import com.chatak.pg.acq.dao.repository.AccountRepository;
import com.chatak.pg.acq.dao.repository.MerchantRepository;
import com.chatak.pg.bean.TransactionPopUpDataDto;
import com.chatak.pg.constants.AccountTransactionCode;
import com.chatak.pg.constants.FeatureConstants;
import com.chatak.pg.constants.PGConstants;
import com.chatak.pg.enums.EntryModeEnum;
import com.chatak.pg.enums.EntryModePortalDisplayEnum;
import com.chatak.pg.model.ReportsDTO;
import com.chatak.pg.model.SettlementActionDTOList;
import com.chatak.pg.model.SettlemetActionDTO;
import com.chatak.pg.user.bean.GetAccountHistoryListResponse;
import com.chatak.pg.user.bean.GetTransactionsListRequest;
import com.chatak.pg.user.bean.GetTransactionsListResponse;
import com.chatak.pg.user.bean.MerchantAccountHistory;
import com.chatak.pg.user.bean.Transaction;
import com.chatak.pg.util.CommonUtil;
import com.chatak.pg.util.Constants;
import com.chatak.pg.util.DateUtil;
import com.chatak.pg.util.StringUtils;

/**
 * << Add Comments Here >>
 * 
 * @author Girmiti Software
 * @date 05-Jan-2015 10:05:28 PM
 * @version 1.0
 */
@SuppressWarnings("rawtypes")
@Service
public class TransactionServiceImpl implements TransactionService {

  private Logger logger = Logger.getLogger(TransactionServiceImpl.class);

  @Autowired
  private TransactionDao transactionDao;

  @Autowired
  private AccountHistoryDao accountHistoryDao;

  @Autowired
  private MerchantDao merchantDao;

  @Autowired
  private AccountDao accountDao;

  @Autowired
  private MerchantUserDao merchantUserDao;

  @Autowired
  private AccountTransactionsDao accountTransactionsDao;

  @Autowired
  AccountRepository accountRepository;

  @Autowired
  MerchantRepository merchantRepository;

  @Autowired
  VoidTransactionDao voidTransactionDao;

  @Autowired
  RefundTransactionDao refundTransactionDao;

  @Autowired
  MerchantUpdateDao merchantUpdateDao;

  @Autowired
  ExecutedTransactionDao executedTransactionDao;
  
  @Override
  public GetTransactionsListResponse searchTransactions(
      GetTransactionsListRequest getTransactionsListRequest) throws ChatakMerchantException {

    logger.info("Entering :: TransactionServiceImpl :: searchTransactions method");
    GetTransactionsListResponse response = new GetTransactionsListResponse();
    try {

      List<Transaction> transactions = refundTransactionDao
          .getMerchantAndItsSubMerchantTransactionList(getTransactionsListRequest);
      if (transactions != null) {
        for (Transaction transaction : transactions) {
          transaction
              .setMaskCardNumber(StringUtils.lastFourDigits(transaction.getMaskCardNumber()));
        }
        setResponseData(getTransactionsListRequest, response, transactions);
      } else {
        setResponseData(getTransactionsListRequest, response, transactions);
      }
      logger.info("Exiting :: TransactionServiceImpl :: searchTransactions method");
      return response;
    } catch (Exception exp) {
      logger.info("Error :: TransactionServiceImpl :: searchTransactions method", exp);
      response.setErrorCode(Constants.ERROR);
      response.setErrorMessage(Constants.ERROR_DATA);
    }
    return null;
  }

  /**
   * @param accountNumber
   * @return
   * @throws ChatakMerchantException
   */
  @Override
  public GetAccountHistoryListResponse getAccountHistory(String accountNumber, int pageNumber)
      throws ChatakMerchantException {
    logger.info("Entering :: TransactionServiceImpl :: getAccountHistory method");
    GetAccountHistoryListResponse response = new GetAccountHistoryListResponse();
    try {
      List<MerchantAccountHistory> history = null;
      List<PGAccountHistory> historyList =
          accountHistoryDao.getHistoryByAccountNum(Long.valueOf(accountNumber));
      if (historyList != null) {
        history = new ArrayList<>(historyList.size());
        MerchantAccountHistory historyResp = null;
        response.setTotalResultCount(historyList.size());
        
        historyList = historyList.subList((pageNumber - 1) * Constants.DEFAULT_PAGE_SIZE,
            (pageNumber * Constants.DEFAULT_PAGE_SIZE) > historyList.size() ? historyList.size()
                : (pageNumber * Constants.DEFAULT_PAGE_SIZE));
        
        for (PGAccountHistory accountHistory : historyList) {
          historyResp = new MerchantAccountHistory();
          historyResp.setEntityId(accountHistory.getEntityId());
          historyResp.setAccountNum(accountHistory.getAccountNum());
          historyResp.setAvailableBalance(accountHistory.getAvailableBalance());
          historyResp.setCurrentBalance(accountHistory.getCurrentBalance());
          historyResp.setFeeBalance(accountHistory.getFeeBalance());
          historyResp.setUpdatedDate(accountHistory.getUpdatedDate());
          historyResp.setCurrency(accountHistory.getCurrency());
          historyResp.setStatus(accountHistory.getStatus());
          historyResp.setPaymentMethod(accountHistory.getPaymentMethod());
          historyResp.setTransactionId(accountHistory.getTransactionId());
          historyResp.setUpdatedDateString(DateUtil
              .toDateStringFormat(accountHistory.getUpdatedDate(), PGConstants.DATE_FORMAT));
          history.add(historyResp);
        }
        response.setAccountHistoryList(history);
        response.setErrorCode(Constants.SUCCESS_CODE);
        response.setErrorMessage(Constants.SUCESS);
      } else {
        response.setAccountHistoryList(history);
        response.setTotalResultCount(0);
        response.setErrorCode(Constants.SUCCESS_CODE);
        response.setErrorMessage(Constants.SUCESS);
      }
      logger.info("Exiting :: TransactionServiceImpl :: getAccountHistory method");
      return response;
    } catch (Exception ex) {
      logger.info("Error :: TransactionServiceImpl :: getAccountHistory method", ex);
      response.setErrorCode(Constants.ERROR);
      response.setErrorMessage(Constants.ERROR_DATA);
    }
    return null;

  }

  @Override
  public GetTransactionsListResponse getAllTransactions(String merchantCode)
      throws ChatakMerchantException {
    logger.info("Entering :: TransactionServiceImpl :: getAllTransactions method");
    GetTransactionsListResponse response = new GetTransactionsListResponse();
    try {
      List<Transaction> transactions = null;
      List<PGTransaction> pgTransactions =
          voidTransactionDao.getAllTransactionsOnMerchantCode(merchantCode);
      if (pgTransactions != null) {
        merchantPGTransaction(response, pgTransactions);
      } else {
        response.setTransactionList(transactions);
        response.setErrorCode(Constants.SUCCESS_CODE);
        response.setErrorMessage(Constants.SUCESS);
      }
      logger.info("Exiting :: TransactionServiceImpl :: getAllTransactions method");
      return response;
    } catch (Exception e) {
      logger.info("Error :: TransactionServiceImpl :: getAllTransactions method", e);
      response.setErrorCode(Constants.ERROR);
      response.setErrorMessage(Constants.ERROR_DATA);
    }
    return null;
  }

  private void merchantPGTransaction(GetTransactionsListResponse response,
      List<PGTransaction> pgTransactions) {
    List<Transaction> transactions;
    transactions = new ArrayList<>(pgTransactions.size());
    Transaction transactionResp = null;

    for (PGTransaction pgTransaction : pgTransactions) {
      PGAccount account = accountDao.getPgAccount(pgTransaction.getMerchantId());
      PGMerchant merchant = merchantUpdateDao.getMerchant(pgTransaction.getMerchantId());
      PGMerchant pgMerchant =
          merchantRepository.findByMerchantCode(pgTransaction.getMerchantId());
      transactionResp = new Transaction();
      transactionResp.setTransactionId(pgTransaction.getTransactionId());
      transactionResp.setTransactionAmount(pgMerchant.getLocalCurrency()
          + StringUtils.amountToString(pgTransaction.getTxnAmount()));
      transactionResp.setTransactionDate(DateUtil
          .toDateStringFormat(pgTransaction.getCreatedDate(), DateUtil.VIEW_DATE_TIME_FORMAT));
      transactionResp.setTransaction_type(pgTransaction.getTransactionType());
      transactionResp.setMerchant_code(pgTransaction.getMerchantId());
      transactionResp.setMaskCardNumber(pgTransaction.getPanMasked());
      transactionResp.setProcTxnCode(pgTransaction.getProcCode());
      transactionResp.setFee_amount(CommonUtil.getDoubleAmount(
          pgTransaction.getFeeAmount() == null ? 0 : pgTransaction.getFeeAmount()));
      transactionResp
          .setRef_transaction_id((StringUtils.isEmpty(pgTransaction.getIssuerTxnRefNum()) ? null
              : Long.valueOf(pgTransaction.getIssuerTxnRefNum())));
      transactionResp.setId(pgTransaction.getId());
      transactionResp.setMerchantFeeAmount(pgTransaction.getMerchantFeeAmount());
      transactionResp.setTxnDescription(pgTransaction.getTxnDescription());
      transactionResp.setMerchantSettlementStatus(pgTransaction.getMerchantSettlementStatus());
      transactionResp.setAcqChannel(pgTransaction.getAcqChannel());
      if (pgTransaction.getPosEntryMode() == null) {
        pgTransaction.setPosEntryMode(Constants.SUCCESS_CODE);
      }
      transactionResp.setEntryMode(EntryModePortalDisplayEnum
          .valueOf(getEntryModeEnumFromPosEntryMode(pgTransaction.getPosEntryMode())).value());
      if (pgTransaction.getStatus() == 0) {
        transactionResp.setStatusMessage("Approved");
      } else if (pgTransaction.getStatus() == 1) {
        transactionResp.setStatusMessage("Declined");
      } else {
        transactionResp.setStatusMessage("Failed");
      }
      transactionResp.setAccountNumber(account.getAccountNum());
      transactionResp.setMerchantType(account.getEntityType());
      transactionResp.setMerchantName(merchant.getFirstName());
      transactions.add(transactionResp);

    }
    response.setTransactionList(transactions);
    response.setErrorCode(Constants.SUCCESS_CODE);
    response.setErrorMessage(Constants.SUCESS);
  }

  @Override
  public List<ReportsDTO> getAllExecutedAccTransFeeOnDate(
      GetTransactionsListRequest getTransactionsListRequest) {
    return executedTransactionDao.getAllExecutedAccFeeOnDate(getTransactionsListRequest);
  }

  public String getEntryModeEnumFromPosEntryMode(String posEntryMode) {
    try {
      return EntryModeEnum.fromValue(posEntryMode.substring(0, Constants.TWO)).name();
    } catch (Exception e) {
      logger.info("Error :: TransactionServiceImpl :: getEntryModeEnumFromPosEntryMode method", e);
      return EntryModeEnum.UNSPECIFIED.name();
    }
  }

  @Override
  public GetTransactionsListResponse searchAllTransactions(
      GetTransactionsListRequest getTransactionsListRequest) throws ChatakMerchantException {
    logger.info("Entering :: TransactionServiceImpl :: searchAllTransactions method");
    GetTransactionsListResponse response = new GetTransactionsListResponse();
    try {
      List<Transaction> transactions =
          voidTransactionDao.getSearchTransactions(getTransactionsListRequest);

      setResponseData(getTransactionsListRequest, response, transactions);
      logger.info("Exiting :: TransactionServiceImpl :: searchAllTransactions method");
      return response;
    } catch (Exception e) {
      logger.info("Error :: TransactionServiceImpl :: searchAllTransactions method", e);
      response.setErrorCode(Constants.ERROR);
      response.setErrorMessage(Constants.ERROR_DATA);
    }
    return null;
  }

  private void setResponseData(GetTransactionsListRequest getTransactionsListRequest,
		GetTransactionsListResponse response, List<Transaction> transactions) {
	response.setTransactionList(transactions);
      response.setTotalResultCount(getTransactionsListRequest.getNoOfRecords());
      response.setErrorCode(Constants.SUCCESS_CODE);
      response.setErrorMessage(Constants.SUCESS);
  }

  @Override
  public boolean hasAccessTo(String operation, String merchantId, HttpSession session) {
    LoginResponse loginResponse = (LoginResponse) session.getAttribute("loginResponse");

    if (null != loginResponse) {
      if (null == merchantId) {
        PGMerchant loginMerchant = merchantUserDao.getMerchant(loginResponse.getUserId()).get(0);
        loginMerchant.getMerchantCode();
      }
      if (null == loginResponse.getParentMerchantId()) {
        String merchantType = merchantDao.getMerchantTypeOnMerchantCode(merchantId);
        if (null != merchantType) {
          return isExistingFeatureAvailable(operation, loginResponse, merchantType);
        }
      } else if (operation.equalsIgnoreCase(FeatureConstants.VOID_SUB_MERCHANT_TRANSACTIONS)
          || operation.equalsIgnoreCase(FeatureConstants.REFUND_SUB_MERCHANT_TRANSACTIONS)) {
        return true;
      }
    }
    return false;
  }

  private boolean isExistingFeatureAvailable(String operation, LoginResponse loginResponse,
      String merchantType) {
    if (!merchantType.equalsIgnoreCase("Merchant")) {
      List<String> existingFeature = loginResponse.getExistingFeature();
      if (existingFeature.contains(operation)) {
        return true;
      }
    } else if (operation.equalsIgnoreCase(FeatureConstants.VOID_SUB_MERCHANT_TRANSACTIONS)
        || operation.equalsIgnoreCase(FeatureConstants.REFUND_SUB_MERCHANT_TRANSACTIONS)
        || operation.equalsIgnoreCase(FeatureConstants.VIEW_SUB_MERCHANT_TRANSACTIONS)) {
      if (operation.equalsIgnoreCase(FeatureConstants.VIEW_SUB_MERCHANT_TRANSACTIONS)) {
        List<String> existingFeature = loginResponse.getExistingFeature();
        return existingFeature.contains(operation);
      }
      return true;
    }
    return false;
  }

  @Override
  public void configureReqObj(HttpServletRequest request, HttpSession session,
      SettlementActionDTOList actionDTOList, String requestObject, String[] removedTxns)
          throws ChatakMerchantException {

    if (null != session.getAttribute(PGConstants.BULK_SETTLEMENT_LIST_OBJ)) {
      settlementAction(session, actionDTOList, requestObject, removedTxns);
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

  private void settlementAction(HttpSession session, SettlementActionDTOList actionDTOList,
      String requestObject, String[] removedTxns) throws ChatakMerchantException {
    if (!StringUtil.isNullAndEmpty(requestObject)) {
      String jsonRequest = "{\"actionDTOs\":[" + requestObject + "]}";
      actionDTOList = (SettlementActionDTOList) JsonUtil.convertJSONToObject(jsonRequest,
          SettlementActionDTOList.class);
    }

    SettlementActionDTOList tempList =
        (SettlementActionDTOList) session.getAttribute(PGConstants.BULK_SETTLEMENT_LIST_OBJ);

    List<SettlemetActionDTO> settlemetActionDTOList = tempList.getActionDTOs();

    List removedTxnsList = Arrays.asList((null != removedTxns && removedTxns.length > 0
        && !StringUtil.isNullAndEmpty(removedTxns[0])) ? removedTxns : new String[] {});
    if (removedTxnsList.size() > 0) {
      String txnId = "";
      Iterator<SettlemetActionDTO> listItr = settlemetActionDTOList.iterator();
      while (listItr.hasNext()) {
        txnId = listItr.next().getTxnId();
        if (removedTxnsList.contains(txnId))
          listItr.remove();
      }
    }

    if (null != actionDTOList.getActionDTOs()) {
      actionDTOList.getActionDTOs().addAll(settlemetActionDTOList);
    } else {
      actionDTOList.setActionDTOs(settlemetActionDTOList);
    }
    session.setAttribute(PGConstants.BULK_SETTLEMENT_LIST_OBJ, actionDTOList);
    session.setAttribute(PGConstants.BULK_SETTLEMENT_LIST,
        (null != actionDTOList.getActionDTOs() && actionDTOList.getActionDTOs().size() > 0)
            ? JsonUtil.convertObjectToJSON(actionDTOList)
            : JsonUtil.convertObjectToJSON(new SettlementActionDTOList()));
  }

  @Override
  public GetTransactionsListResponse searchAccountTransactions(
      GetTransactionsListRequest getTransactionsListRequest) {
     return accountTransactionsDao.getAccountTransactions(getTransactionsListRequest);
  }

  @Override
  public TransactionPopUpDataDto fetchTransactionDetails(String accountTransactionId) {
    logger.info("Entering :: TransactionServiceImpl :: fetchTransactionDetails method");
    List<PGAccountTransactions> pgAccountTransactions = null;
    PGAccountTransactions accountTransactions = null;
    TransactionPopUpDataDto transactionPopUpDto = null;
    try {
      pgAccountTransactions = accountTransactionsDao.getAccountTransactions(accountTransactionId);
      if (CommonUtil.isListNotNullAndEmpty(pgAccountTransactions)) {
        accountTransactions = pgAccountTransactions.get(0);

        PGAccount pgAccount = accountRepository
            .findByAccountNum(Long.parseLong(accountTransactions.getAccountNumber()));

        if (null != accountTransactions.getPgTransactionId()) {
          logger.info(
              "Entering :: TransactionServiceImpl :: fetchTransactionDetails method:: CC Transactions");
          transactionPopUpDto = refundTransactionDao
              .getTransactionPopUpDataDto(accountTransactions.getPgTransactionId());
          transactionPopUpDto.setAccountTransactionId(accountTransactionId);
        } else {
           transactionPopUpDto = getTxnPopupDtoDetails(accountTransactions, pgAccount);
        }
      } else {
        logger.info(
            " TransactionServiceImpl :: fetchTransactionDetails method:: emppty account transaction list");
        transactionPopUpDto = new TransactionPopUpDataDto();
        transactionPopUpDto.setDtoType(Constants.TXN_UNKNOWN);
      }
    } catch (Exception e) {
      logger.info(" ERROR :TransactionServiceImpl :: fetchTransactionDetails method", e);
      transactionPopUpDto = new TransactionPopUpDataDto();
      transactionPopUpDto.setDtoType(Constants.TXN_UNKNOWN);
    }
    logger.info("Exiting :: TransactionServiceImpl :: fetchTransactionDetails method");
    return transactionPopUpDto;
  }

  private TransactionPopUpDataDto getTxnPopupDtoDetails(PGAccountTransactions accountTransactions,
       PGAccount pgAccount) {
    TransactionPopUpDataDto transactionPopUpDto = new TransactionPopUpDataDto();
    logger.info(" TransactionServiceImpl :: fetchTransactionDetails method :: "
        + accountTransactions.getTransactionType());
    switch (accountTransactions.getTransactionType()) {
      case PGConstants.FUND_TRANSFER_EFT:
      case PGConstants.FUND_TRANSFER_CHECK:
        transactionPopUpDto.setFromAccount(accountTransactions.getAccountNumber());
        transactionPopUpDto.setTransactionAmount(getTxnAmount(accountTransactions, pgAccount));
        transactionPopUpDto.setDtoType(Constants.TXN_EFT);
        break;
      case AccountTransactionCode.MANUAL_DEBIT:
        transactionPopUpDto.setFromAccount(accountTransactions.getAccountNumber());
        transactionPopUpDto.setTransactionAmount(getTxnAmount(accountTransactions, pgAccount));
        transactionPopUpDto.setDtoType(Constants.TXN_MANUAL_DEBIT);
        break;
      case AccountTransactionCode.MANUAL_CREDIT:
        transactionPopUpDto.setToAccount(accountTransactions.getAccountNumber());
        transactionPopUpDto.setTransactionAmount(pgAccount.getCurrency()
            + StringUtils.amountToString(accountTransactions.getCredit()));
        transactionPopUpDto.setDtoType(Constants.TXN_MANUAL_CREDIT);
        break;
      case AccountTransactionCode.ACCOUNT_CREDIT:
      case AccountTransactionCode.ACCOUNT_DEBIT:
        transactionPopUpDto.setToAccount(accountTransactions.getAccountNumber());
        transactionPopUpDto.setFromAccount(accountTransactions.getToAccountNumber());
        transactionPopUpDto.setTransactionAmount(getTxnAmount(accountTransactions, pgAccount));
        transactionPopUpDto.setDtoType(Constants.TXN_ACCOUNT);
        break;
      default:
        transactionPopUpDto.setDtoType(Constants.TXN_UNKNOWN);
        break;
    }
    return transactionPopUpDto;
  }

  private String getTxnAmount(PGAccountTransactions accountTransactions, PGAccount pgAccount) {
    return pgAccount.getCurrency()
        + StringUtils.amountToString(accountTransactions.getDebit());
  }

  @Override
  public String getPartialRefundBalance(String accountTransactionId, String merchantCode) {
    PGTransaction pgTransaction = null;
    Long refundedAmount = null;
    String pgTransactionId =
        accountTransactionsDao.getSaleAccountTransactionId(accountTransactionId, merchantCode);
    if (null != pgTransactionId) {
      refundedAmount = refundTransactionDao.getRefundedAmountOnTxnId(pgTransactionId);
      PGMerchant pgMerchant = merchantUpdateDao.getMerchantByCode(merchantCode);
      String localCurrency = pgMerchant.getLocalCurrency();
      pgTransaction = transactionDao.getTransaction(merchantCode,
          merchantCode.substring(merchantCode.length() - Constants.EIGHT, merchantCode.length()),
          pgTransactionId);
      if (null != refundedAmount && null != pgTransaction) {
        //Returning balance amount for refund
        return localCurrency + " "
            + StringUtils.amountToString(pgTransaction.getTxnTotalAmount() - refundedAmount);

      } else if (null != pgTransaction) {
        //Returning total transaction amount 
        return localCurrency + " " + StringUtils.amountToString(pgTransaction.getTxnTotalAmount());
      }
    }
    return null;
  }

  @Override
  public Long findMerchantFeeByMerchantId(String merchantId) {
    return refundTransactionDao.findMerchantFeeByMerchantId(merchantId);
  }

  @Override
  public GetTransactionsListResponse searchManulAccountTransactions(
      GetTransactionsListRequest getTransactionsListRequest) {
    return accountTransactionsDao.getManulAccountTransactions(getTransactionsListRequest);
  }

  @Override
  public GetTransactionsListResponse searchAccountTransactionsForEntityId(
		  GetTransactionsListRequest getTransactionsListRequest, Long entityId, String userType) {
	  return accountTransactionsDao.searchAccountTransactionsForEntityId(getTransactionsListRequest, entityId, userType);
  }

  @Override
  public GetTransactionsListResponse searchManualAccountTransactionsForEntityId(
		  GetTransactionsListRequest getTransactionsListRequest, Long entityId, String userType) {
	  return accountTransactionsDao.searchManualAccountTransactionsForEntityId(getTransactionsListRequest, entityId, userType);
  }
}
