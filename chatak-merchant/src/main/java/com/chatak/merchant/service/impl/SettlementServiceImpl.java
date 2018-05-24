/**
 * 
 */
package com.chatak.merchant.service.impl;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpStatus;
import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.chatak.merchant.exception.ChatakMerchantException;
import com.chatak.merchant.model.TransactionResponse;
import com.chatak.merchant.service.RestPaymentService;
import com.chatak.merchant.service.SettlementService;
import com.chatak.merchant.util.JsonUtil;
import com.chatak.pg.acq.dao.AccountDao;
import com.chatak.pg.acq.dao.AccountFeeLogDao;
import com.chatak.pg.acq.dao.AccountHistoryDao;
import com.chatak.pg.acq.dao.AccountTransactionsDao;
import com.chatak.pg.acq.dao.AcquirerFeeCodeDao;
import com.chatak.pg.acq.dao.CurrencyConfigDao;
import com.chatak.pg.acq.dao.FeeDetailDao;
import com.chatak.pg.acq.dao.FeeProgramDao;
import com.chatak.pg.acq.dao.MerchantDao;
import com.chatak.pg.acq.dao.MerchantUpdateDao;
import com.chatak.pg.acq.dao.OnlineTxnLogDao;
import com.chatak.pg.acq.dao.TransactionDao;
import com.chatak.pg.acq.dao.VoidTransactionDao;
import com.chatak.pg.acq.dao.model.PGAccount;
import com.chatak.pg.acq.dao.model.PGAccountFeeLog;
import com.chatak.pg.acq.dao.model.PGAccountHistory;
import com.chatak.pg.acq.dao.model.PGAccountTransactions;
import com.chatak.pg.acq.dao.model.PGAcquirerFeeValue;
import com.chatak.pg.acq.dao.model.PGCurrencyConfig;
import com.chatak.pg.acq.dao.model.PGOnlineTxnLog;
import com.chatak.pg.acq.dao.model.PGTransaction;
import com.chatak.pg.acq.dao.repository.AccountRepository;
import com.chatak.pg.constants.AccountTransactionCode;
import com.chatak.pg.constants.FeePostingStatus;
import com.chatak.pg.constants.PGConstants;
import com.chatak.pg.enums.ProcessorType;
import com.chatak.pg.model.BulkSettlementResponse;
import com.chatak.pg.model.ProcessingFee;
import com.chatak.pg.model.SettlementActionDTOList;
import com.chatak.pg.model.SettlemetActionDTO;
import com.chatak.pg.model.VirtualAccFeeReversalRequest;
import com.chatak.pg.model.VirtualTerminalVoidDTO;
import com.chatak.pg.model.virtualAccFeePostRequest;
import com.chatak.pg.model.virtualAccFeePostResponse;
import com.chatak.pg.util.CommonUtil;
import com.chatak.pg.util.Constants;
import com.chatak.pg.util.EncryptionUtil;
import com.chatak.pg.util.PGUtils;
import com.chatak.pg.util.Properties;
import com.chatak.pg.util.StringUtils;
import com.sun.jersey.api.client.ClientResponse;

/**
 * << Add Comments Here >>
 *
 * @author Girmiti Software
 * @date May 15, 2015 4:45:41 PM
 * @version 1.0
 */
@Service
public class SettlementServiceImpl implements SettlementService {

  private static Logger logger = Logger.getLogger(SettlementServiceImpl.class);

  @Autowired
  TransactionDao transactionDao;

  @Autowired
  AccountDao accountDao;

  @Autowired
  FeeDetailDao feeDetailDao;

  @Autowired
  AccountRepository accountRepository;

  @Autowired
  OnlineTxnLogDao onlineTxnLogDao;

  @Autowired
  AccountHistoryDao accountHistoryDao;

  @Autowired
  AcquirerFeeCodeDao acquirerFeeCodeDao;

  @Autowired
  MerchantDao merchantDao;

  @Autowired
  AccountFeeLogDao accountFeeLogDao;

  @Autowired
  FeeProgramDao feeProgramDao;

  @Autowired
  AccountTransactionsDao accountTransactionsDao;

  @Autowired
  RestPaymentService paymentService;

  @Autowired
  MessageSource messageSource;

  @Autowired
  CurrencyConfigDao currencyConfigDao;

  @Autowired
  VoidTransactionDao voidTransactionDao;

  @Autowired
  MerchantUpdateDao merchantUpdateDao;

  private ObjectMapper mapper = new ObjectMapper();

  /**
   * <<Method to update settlement status and fee details in merchant account>>
   * 
   * @param merchantId
   * @param terminalId
   * @param feeAmount
   * @param status
   * @return
   * @throws ChatakMerchantException
   */
  @Override
  public boolean updateSettlementStatus(String merchantId, String terminalId, String txnId,
      String txnType, String status, String comments) throws ChatakMerchantException {
    try {
      logger.info("Entering:: SettlementServiceImpl:: updateSettlementStatus method");
      PGTransaction pgTransaction =
          transactionDao.getTransactionOnTxnIdAndTxnType(merchantId, terminalId, txnId, txnType);
      if (null != pgTransaction) {
        PGOnlineTxnLog pgOnlineTxnLog = onlineTxnLogDao.getTransactionOnPgTxnIdAndMerchantId(
            pgTransaction.getTransactionId(), pgTransaction.getMerchantId());
        if (status.equals(PGConstants.PG_SETTLEMENT_EXECUTED)) {
          updateSettlementStatusPGSettlementExecuted(status, comments, pgTransaction,
              pgOnlineTxnLog);
        } else if (PGConstants.PG_SETTLEMENT_PROCESSING.equals(status)) {
          updateSettlementStatusPGSettlementProcessing(status, pgTransaction);
        } else if (PGConstants.PG_SETTLEMENT_REJECTED.equals(status)) {
          List<PGAccountFeeLog> pgAccountFeeLogList =
              accountFeeLogDao.getPGAccountFeeLogOnTransactionId(pgTransaction.getTransactionId());
          updateAccountFeeLogData(pgAccountFeeLogList);
          return processVoidTransaction(pgTransaction, status);
        }
        pgTransaction.setMerchantSettlementStatus(status);
        pgTransaction.setUpdatedDate(new Timestamp(System.currentTimeMillis()));
        voidTransactionDao.createTransaction(pgTransaction);
        logger.info("Exiting:: SettlementServiceImpl:: updateSettlementStatus method");
        return true;
      } else {
        return false;
      }
    } catch (Exception e) {
      logger.error("ERROR:: SettlementServiceImpl:: updateSettlementStatus method", e);
      throw new ChatakMerchantException(e.getMessage());
    }
  }

  private void updateSettlementStatusPGSettlementProcessing(String status,
      PGTransaction pgTransaction) {
    List<PGAccountFeeLog> pgAccountFeeLogList =
        accountFeeLogDao.getPGAccountFeeLogOnTransactionId(pgTransaction.getTransactionId());
    if (CommonUtil.isListNotNullAndEmpty(pgAccountFeeLogList)) {
      for (PGAccountFeeLog pgAccountFeeLog : pgAccountFeeLogList) {
        pgAccountFeeLog.setStatus(status.equals(PGConstants.PG_SETTLEMENT_PROCESSING)
            ? PGConstants.PG_SETTLEMENT_PROCESSING : PGConstants.PG_SETTLEMENT_REJECTED);
        pgAccountFeeLog.setUpdatedDate(new Timestamp(System.currentTimeMillis()));
        accountFeeLogDao.createOrSave(pgAccountFeeLog);
      }
    }
  }

  private void updateSettlementStatusPGSettlementExecuted(String status, String comments,
      PGTransaction pgTransaction, PGOnlineTxnLog pgOnlineTxnLog)  {
    Long chatakFeeAmountTotal;
    List<Object> objectResult =
        getProcessingFee(PGUtils.getCCType(EncryptionUtil.decrypt(pgTransaction.getPan())),
            1L, pgTransaction.getMerchantId(), pgTransaction.getTxnTotalAmount());
    chatakFeeAmountTotal = (Long) objectResult.get(1);
    Long merchantFeeAmount = 0l;

    Long totalFeeAmount = pgTransaction.getTxnTotalAmount() - pgTransaction.getTxnAmount();

    if (chatakFeeAmountTotal < totalFeeAmount) {
      merchantFeeAmount = totalFeeAmount - chatakFeeAmountTotal;
    } else {
      chatakFeeAmountTotal = totalFeeAmount;
    }
    updateAccountCCTransactions(pgTransaction.getTransactionId(),
        pgTransaction.getTransactionType(), status);
    // **updating description for settlement status
    String descriptionTemplate = messageSource.getMessage(
        "chatak-pay.sale.description.template", null, LocaleContextHolder.getLocale());
    if (pgOnlineTxnLog != null) {
      String merchantName =
          merchantUpdateDao.getMerchant(pgTransaction.getMerchantId()).getBusinessName();
      descriptionTemplate =
          MessageFormat.format(descriptionTemplate, pgOnlineTxnLog.getOrderId(),
              StringUtils.amountToString(pgTransaction.getTxnAmount()),
              StringUtils.amountToString(chatakFeeAmountTotal), merchantName,
              pgTransaction.getTerminalId(), pgTransaction.getTransactionType(),
              pgTransaction.getIssuerTxnRefNum());
    }

    pgTransaction.setMerchantFeeAmount(merchantFeeAmount);
    pgTransaction.setTxnDescription(descriptionTemplate);
    pgTransaction.setReason(comments);// setting reason
    if (ProcessorType.LITLE.value().equals(pgTransaction.getProcessor())) {
      pgTransaction.setEftStatus(PGConstants.LITLE_EXECUTED);
    }
  }

  /**
   * @param pgTransaction
   * @param status
   * @return
   */
  private boolean processVoidTransaction(PGTransaction pgTransaction, String status) {
    VirtualTerminalVoidDTO virtualTerminalVoidDTO = new VirtualTerminalVoidDTO();
    virtualTerminalVoidDTO.setMerchantId(pgTransaction.getMerchantId());
    virtualTerminalVoidDTO.setTerminalId(pgTransaction.getTerminalId());
    virtualTerminalVoidDTO.setCgRefNumber(pgTransaction.getIssuerTxnRefNum());
    virtualTerminalVoidDTO.setTxnRefNumber(pgTransaction.getTransactionId());
    virtualTerminalVoidDTO.setTransactionType(com.chatak.pg.enums.TransactionType.VOID);

    try {
      TransactionResponse response2 = paymentService.doVoid(virtualTerminalVoidDTO);
      if (response2 != null && response2.getErrorCode().equals(Constants.SUCCESS_CODE)) {
        updateAccountCCTransactions(pgTransaction.getTransactionId(),
            pgTransaction.getTransactionType(), status);
        return true;
      }
    } catch (Exception e) {
      logger.error("ERROR:: SettlementServiceImpl:: processVoidTransaction method", e);
    }
    return false;
  }

  /**
   * <<Method to update chatak fee account >>
   * 
   * @param feeAmount
   * @throws ChatakMerchantException
   */
  public void updateChatakAccount(Long feeAmount, PGTransaction transaction)
      throws ChatakMerchantException {
    try {
      logger.info("Entering:: SettlementServiceImpl:: updateChatakAccount method");

      logger.info(
          "SettlementServiceImpl:: updateChatakAccount method :: fetching currencyConfig for fee credit with numeric code: "
              + transaction.getTxnCurrencyCode());
      PGCurrencyConfig currencyConfig =
          currencyConfigDao.getcurrencyCodeAlpha(transaction.getTxnCurrencyCode());
      logger.info(
          "SettlementServiceImpl:: updateChatakAccount method :: currency code alpha for the above: "
              + currencyConfig.getCurrencyCodeAlpha());
      PGAccount pgAccount =
          accountRepository.findByEntityTypeAndCurrencyAndStatus(PGConstants.DEFAULT_ENTITY_TYPE,
              currencyConfig.getCurrencyCodeAlpha(), PGConstants.S_STATUS_ACTIVE);

      pgAccount.setFeeBalance(pgAccount.getFeeBalance() + feeAmount);
      accountRepository.save(pgAccount);
      logPgAccountHistory(pgAccount.getEntityId(), transaction.getPaymentMethod(),
          transaction.getTransactionId());
      logger.info("Exiting:: SettlementServiceImpl:: updateChatakAccount method");
    } catch (Exception e) {
      logger.error("ERROR:: SettlementServiceImpl:: updateChatakAccount method", e);
      throw new ChatakMerchantException(e.getMessage());
    }

  }

  public void logPgAccountHistory(String merchantId, String paymentMethod, String transactionId)
      throws ChatakMerchantException {
    PGAccount updatedAccount = accountDao.getPgAccount(merchantId);
    if (null != updatedAccount) {
      PGAccountHistory pgAccountHistory = new PGAccountHistory();
      pgAccountHistory.setEntityId(updatedAccount.getEntityId());
      pgAccountHistory.setEntityType(updatedAccount.getEntityType());
      pgAccountHistory.setAccountDesc(updatedAccount.getAccountDesc());
      pgAccountHistory.setCategory(updatedAccount.getCategory());
      pgAccountHistory.setCurrentBalance(updatedAccount.getCurrentBalance());
      pgAccountHistory.setAvailableBalance(updatedAccount.getAvailableBalance());
      pgAccountHistory.setCurrency(updatedAccount.getCurrency());
      pgAccountHistory.setAutoPaymentLimit(updatedAccount.getAutoPaymentLimit());
      pgAccountHistory.setAutoPaymentMethod(updatedAccount.getAutoPaymentMethod());
      pgAccountHistory.setAutoTransferDay(updatedAccount.getAutoTransferDay());
      pgAccountHistory.setStatus(updatedAccount.getStatus());
      pgAccountHistory.setUpdatedDate(new Timestamp(System.currentTimeMillis()));
      pgAccountHistory.setAccountNum(updatedAccount.getAccountNum());
      pgAccountHistory.setFeeBalance(updatedAccount.getFeeBalance());
      pgAccountHistory.setPaymentMethod(paymentMethod);
      pgAccountHistory.setTransactionId(transactionId);
      accountHistoryDao.createOrSave(pgAccountHistory);
    }
  }

  @Override
  public BulkSettlementResponse updateBulkSettlementStatus(
      SettlementActionDTOList settlementActionDTOList, String status, String comments) {
    BulkSettlementResponse bulkSettlementResponse = new BulkSettlementResponse();
    int successCount = 0;
    bulkSettlementResponse.setSuccess(false);
    bulkSettlementResponse.setSuccessCount(successCount);

    if (!settlementActionDTOList.getActionDTOs().isEmpty()) {

      for (SettlemetActionDTO settlemetActionDTO : settlementActionDTOList.getActionDTOs()) {
        successCount = updateBulkStatementSettlementStatus(status, comments, bulkSettlementResponse,
            successCount, settlemetActionDTO);

      }
      bulkSettlementResponse.setSuccess(true);
    } else {
      bulkSettlementResponse.setSuccess(false);
    }
    return bulkSettlementResponse;
  }

  private int updateBulkStatementSettlementStatus(String status, String comments,
      BulkSettlementResponse bulkSettlementResponse, int successCount,
      SettlemetActionDTO settlemetActionDTO) {
    logger.info("Entering:: SettlementServiceImpl:: updateSettlementStatus method");
    PGTransaction pgTransaction = transactionDao.getTransactionOnTxnIdAndTxnType(
        settlemetActionDTO.getMerchantId(), settlemetActionDTO.getTerminalId(),
        settlemetActionDTO.getTxnId(), settlemetActionDTO.getTxnType());
    if (null != pgTransaction && !pgTransaction.getMerchantSettlementStatus().equals(status)) {

      PGOnlineTxnLog pgOnlineTxnLog = onlineTxnLogDao.getTransactionOnPgTxnIdAndMerchantId(
          pgTransaction.getTransactionId(), pgTransaction.getMerchantId());
      if (status.equals(PGConstants.PG_SETTLEMENT_EXECUTED)) {
        updateBulkSettlementStatusPGSettlementExecuted(status, comments, pgTransaction,
            pgOnlineTxnLog);

      } else if (status.equals(PGConstants.PG_SETTLEMENT_PROCESSING)) {
        updateBulkSettlementStatusPGSettlementProcessing(pgTransaction);
      } else if (PGConstants.PG_SETTLEMENT_REJECTED.equals(status)) {
        List<PGAccountFeeLog> pgAccountFeeLogList = accountFeeLogDao
            .getPGAccountFeeLogOnTransactionId(pgTransaction.getTransactionId());
        updateAccountFeeLogData(pgAccountFeeLogList);
        boolean isSuccess = processVoidTransaction(pgTransaction, status);
        bulkSettlementResponse.setSuccess(isSuccess);
        return successCount;
      }
      pgTransaction.setMerchantSettlementStatus(status);
      voidTransactionDao.createTransaction(pgTransaction);
      successCount++;
      bulkSettlementResponse.setSuccessCount(successCount);
      logger.info("Exiting:: SettlementServiceImpl:: updateSettlementStatus method");
    }
    return successCount;
  }

  private void updateAccountFeeLogData(List<PGAccountFeeLog> pgAccountFeeLogList) {
    if (CommonUtil.isListNotNullAndEmpty(pgAccountFeeLogList)) {
      for (PGAccountFeeLog pgAccountFeeLog : pgAccountFeeLogList) {
        pgAccountFeeLog.setStatus(PGConstants.PG_SETTLEMENT_REJECTED);
        pgAccountFeeLog.setUpdatedDate(new Timestamp(System.currentTimeMillis()));
        accountFeeLogDao.createOrSave(pgAccountFeeLog);
      }
    }
  }

  private void updateBulkSettlementStatusPGSettlementProcessing(PGTransaction pgTransaction) {
    List<PGAccountFeeLog> pgAccountFeeLogList = accountFeeLogDao
        .getPGAccountFeeLogOnTransactionId(pgTransaction.getTransactionId());
    if (CommonUtil.isListNotNullAndEmpty(pgAccountFeeLogList)) {
      for (PGAccountFeeLog pgAccountFeeLog : pgAccountFeeLogList) {
        pgAccountFeeLog.setStatus(PGConstants.PG_SETTLEMENT_PROCESSING);
        pgAccountFeeLog.setUpdatedDate(new Timestamp(System.currentTimeMillis()));
        accountFeeLogDao.createOrSave(pgAccountFeeLog);
      }
    }
  }

  private void updateBulkSettlementStatusPGSettlementExecuted(String status, String comments,
      PGTransaction pgTransaction, PGOnlineTxnLog pgOnlineTxnLog) {
    Long chatakFeeAmountTotal;
    List<Object> objectResult =
        getProcessingFee(PGUtils.getCCType(EncryptionUtil.decrypt(pgTransaction.getPan())),
            1L, pgTransaction.getMerchantId(), pgTransaction.getTxnTotalAmount());
    chatakFeeAmountTotal = (Long) objectResult.get(1);
    Long merchantFeeAmount = 0l;

    Long totalFeeAmount = pgTransaction.getTxnTotalAmount() - pgTransaction.getTxnAmount();

    if (totalFeeAmount > chatakFeeAmountTotal) {
      merchantFeeAmount = totalFeeAmount - chatakFeeAmountTotal;
    } else {
      chatakFeeAmountTotal = totalFeeAmount;
    }
    updateAccountCCTransactions(pgTransaction.getTransactionId(),
        pgTransaction.getTransactionType(), status);
    // **updating description for settlement status
    String descriptionTemplate = messageSource.getMessage(
        "chatak-pay.sale.description.template", null, LocaleContextHolder.getLocale());
    if (null != pgOnlineTxnLog) {
      String merchantName =
          merchantUpdateDao.getMerchant(pgTransaction.getMerchantId()).getBusinessName();
      descriptionTemplate =
          MessageFormat.format(descriptionTemplate, pgOnlineTxnLog.getOrderId(),
              StringUtils.amountToString(pgTransaction.getTxnAmount()),
              StringUtils.amountToString(chatakFeeAmountTotal), merchantName,
              pgTransaction.getTerminalId(), pgTransaction.getTransactionType(),
              pgTransaction.getIssuerTxnRefNum());
    }
    if (ProcessorType.LITLE.value().equals(pgTransaction.getProcessor())) {
      pgTransaction.setEftStatus(PGConstants.LITLE_EXECUTED);
    }
    pgTransaction.setMerchantFeeAmount(merchantFeeAmount);
    pgTransaction.setTxnDescription(descriptionTemplate);
    pgTransaction.setReason(comments);// setting reason
  }

  /**
   * Method to calculate acquirer processing fee
   * 
   * @param cardType
   * @param partnerId
   * @param merchantCode
   * @param txnAmount
   * @return
   * @throws Exception
   * @throws DataAccessException
   */
  public List<Object> getProcessingFee(String cardType, Long partnerId, String merchantCode,
      Long txnTotalAmount) {
    logger.info("Entering:: SettlementServiceImpl:: getProcessingFee method ");
    List<Object> results = new ArrayList<>(Constants.TWO);
    List<ProcessingFee> calculatedProcessingFeeList = new ArrayList<>(0);
    Double calculatedProcessingFee = null;
    Long chatakFeeAmountTotal = 0l;
    List<PGAcquirerFeeValue> acquirerFeeValueList =
        feeProgramDao.getAcquirerFeeValueByMerchantIdAndCardType(merchantCode, cardType);
    if (CommonUtil.isListNotNullAndEmpty(acquirerFeeValueList)) {

      logger.info(                                                                
          " SettlementServiceImpl:: getProcessingFee method :: Applying this merchant fee code ");
      for (PGAcquirerFeeValue acquirerFeeValue : acquirerFeeValueList) {
        calculatedProcessingFee = 0.00;
        ProcessingFee processingFee =
            getProcessingFeeItem(acquirerFeeValue, txnTotalAmount, calculatedProcessingFee);
        chatakFeeAmountTotal =
            chatakFeeAmountTotal + CommonUtil.getLongAmount(processingFee.getChatakProcessingFee());
        calculatedProcessingFeeList.add(processingFee);
      }
    } else {
      String parentMerchantCode = merchantDao.getParentMerchantCode(merchantCode);
      acquirerFeeValueList =
          feeProgramDao.getAcquirerFeeValueByMerchantIdAndCardType(parentMerchantCode, cardType);
      if (CommonUtil.isListNotNullAndEmpty(acquirerFeeValueList)) {
        logger.info(
            "SettlementServiceImpl:: getProcessingFee method :: Applying parentMerchantCode fee ");
        for (PGAcquirerFeeValue acquirerFeeValue : acquirerFeeValueList) {
          calculatedProcessingFee = 0.00;
          ProcessingFee processingFee =
              getProcessingFeeItem(acquirerFeeValue, txnTotalAmount, calculatedProcessingFee);
          chatakFeeAmountTotal = chatakFeeAmountTotal
              + CommonUtil.getLongAmount(processingFee.getChatakProcessingFee());
          calculatedProcessingFeeList.add(processingFee);
        }
      }
    }
    logger.info("Exiting:: SettlementServiceImpl:: getProcessingFee method ");
    results.add(calculatedProcessingFeeList);
    results.add(chatakFeeAmountTotal);
    return results;
  }

  @Override
  public PGAccountFeeLog postVirtualAccFee(PGAccountFeeLog pgAccountFeeLog, String agentId,
      String partnerId, String mode, String programManagerId)
          throws ChatakMerchantException, IOException {
    PGAccountFeeLog feeLog = pgAccountFeeLog;
    virtualAccFeePostRequest request = new virtualAccFeePostRequest();
    request.setAgentId(agentId);
    request.setMerchantFee(feeLog.getMerchantFee());
    request.setRapidFee(feeLog.getChatakFee());
    request.setCaTransactionId(feeLog.getTransactionId());
    request.setPartnerId(partnerId);
    request.setMode("Credit");
    request.setProgramManagerId(programManagerId);
    request.setMerchantAmount(feeLog.getTxnAmount());// sending merchant/sub merchant txn amount to issuance
    request
        .setTotalTxnAmount(feeLog.getTxnAmount() + feeLog.getChatakFee() + feeLog.getMerchantFee());// sending total txn
                                                                                                           // amount to issuance
                                                                                                           /* Start posting fee to issuance */
    ClientResponse response = new ClientResponse(null, null, null, null);
    try {

      response = JsonUtil.sendToIssuance(request,
          Properties.getProperty("chatak-issuance.virtual.post.fee"), mode);
      /* End posting fee to issuance */

    } catch (Exception e) {
      response.setStatus(HttpStatus.SC_SERVICE_UNAVAILABLE);// setting if any connectivity exception occurs
      logger.error("ERROR:: SettlementServiceImpl:: postVirtualAccFee method ", e);
    }
    feeLog.setFeeTxnDate(new Timestamp(System.currentTimeMillis()));
    if (response.getStatus() != HttpStatus.SC_OK) {
      feeLog.setFeePostStatus(FeePostingStatus.FEE_POST_NETWORK_FAIL);
    } else {
      String output = response.getEntity(String.class);
      virtualAccFeePostResponse feeResponse =
          mapper.readValue(output, virtualAccFeePostResponse.class);
      if (null != feeResponse) {
        if (feeResponse.getErrorCode().equals("CEC_0001")) {
          feeLog.setFeePostStatus(FeePostingStatus.FEE_POST_SUCCESS);
          feeLog.setIssuanceFeeTxnId(feeResponse.getCiVirtualAccTxnId());
        } else {
          feeLog.setFeePostStatus(FeePostingStatus.FEE_POST_DECLINED);
        }
        feeLog.setIssuanceMessage(feeResponse.getErrorMessage());
      }
    }
    return feeLog;
  }

  @Override
  public PGAccountFeeLog postVirtualAccFeeReversal(PGAccountFeeLog pgAccountFeeLog, String agentId,
      String ciVirtualAccTxnId, String mode) throws IOException {
    PGAccountFeeLog feeLog = pgAccountFeeLog;
    VirtualAccFeeReversalRequest request = new VirtualAccFeeReversalRequest();
    request.setCiVirtualAccTxnId(ciVirtualAccTxnId);
    ClientResponse response = new ClientResponse(null, null, null, null);
    try {
      /* Start posting fee to issuance */
      response = JsonUtil.sendToIssuance(request,
          Properties.getProperty("chatak-issuance.virtual.reverse.fee"), mode);
      /* End posting fee to issuance */
    } catch (Exception e) {
      response.setStatus(HttpStatus.SC_SERVICE_UNAVAILABLE);// setting if any connectivity exception occurs
      logger.info("ERROR:: SettlementServiceImpl:: postVirtualAccFeeReversal method ", e);
    }

    feeLog.setFeeTxnDate(new Timestamp(System.currentTimeMillis()));
    if (response.getStatus() != HttpStatus.SC_OK) {
      feeLog.setFeePostStatus(FeePostingStatus.FEE_POST_NETWORK_FAIL);
    } else {
      String output = response.getEntity(String.class);
      virtualAccFeePostResponse feeResponse =
          mapper.readValue(output, virtualAccFeePostResponse.class);
      if (null != feeResponse) {
        if (feeResponse.getErrorCode().equals("CEC_0001")) {
          feeLog.setFeePostStatus(FeePostingStatus.FEE_POST_SUCCESS);
        } else {
          feeLog.setFeePostStatus(FeePostingStatus.FEE_POST_DECLINED);

        }
        feeLog.setIssuanceMessage(feeResponse.getErrorMessage());
        feeLog.setFeeTxnDate(new Timestamp(System.currentTimeMillis()));
      }

    }
    return feeLog;
  }

  private ProcessingFee getProcessingFeeItem(PGAcquirerFeeValue acquirerFeeValue,
      Long txnTotalAmount, Double calculatedProcessingFee) {
    logger.info("Entering:: SettlementServiceImpl:: getProcessingFeeItem method ");
    ProcessingFee processingFee = new ProcessingFee();
    Double flatFee = CommonUtil.getDoubleAmountNotNull(acquirerFeeValue.getFlatFee());
    Double percentageFee = acquirerFeeValue.getFeePercentageOnly();
    percentageFee = txnTotalAmount * (CommonUtil.getDoubleAmountNotNull(percentageFee));
    calculatedProcessingFee =
        (CommonUtil.getDoubleAmountNotNull(calculatedProcessingFee + percentageFee)) + flatFee;
    processingFee.setAccountNumber(acquirerFeeValue.getAccountNumber());
    processingFee.setChatakProcessingFee(calculatedProcessingFee);
    logger.info("Exiting:: SettlementServiceImpl:: getProcessingFeeItem method ");
    return processingFee;
  }

  public void updateAccountCCTransactions(String pgTransactionId, String txnType,
      String newStatus) {
    Timestamp currentTime = new Timestamp(System.currentTimeMillis());
    logger.info("Entering:: SettlementServiceImpl:: updateAccountCCTransactions method ");
    List<PGAccountTransactions> accountTxns = accountTransactionsDao
        .getAccountTransactionsOnTransactionIdAndTransactionType(pgTransactionId, txnType);
    for (PGAccountTransactions accTxn : accountTxns) {
      if (PGConstants.PG_SETTLEMENT_REJECTED.equals(newStatus)) {
        accTxn.setStatus(PGConstants.PG_SETTLEMENT_REJECTED);
      } else {
        switch (accTxn.getTransactionCode()) {
          case AccountTransactionCode.CC_AMOUNT_CREDIT:
            // updating pg account debting refund amount
            updateAmountCreditCase(accTxn);
            accTxn.setProcessedTime(currentTime);
            accTxn.setStatus(PGConstants.PG_SETTLEMENT_EXECUTED);
            break;
          case AccountTransactionCode.CC_FEE_DEBIT:  
            updateFeeDebitCase(accTxn);
            accTxn.setProcessedTime(currentTime);
            accTxn.setStatus(PGConstants.PG_SETTLEMENT_EXECUTED);
            break;
          case AccountTransactionCode.CC_MERCHANT_FEE_CREDIT:
            updateMerchantFeeCreditcase(accTxn);
            accTxn.setProcessedTime(currentTime);
            accTxn.setStatus(PGConstants.PG_SETTLEMENT_EXECUTED);
            break;
            
            case AccountTransactionCode.CC_FEE_CREDIT:
            //ReBrand
        	updateFeeCreditCase(accTxn);
            accTxn.setProcessedTime(currentTime);
            accTxn.setStatus(PGConstants.PG_SETTLEMENT_EXECUTED);
            break;
          default:
        }
      }
      accountTransactionsDao.createOrUpdate(accTxn);
    }

    logger.info("Exiting:: SettlementServiceImpl:: updateAccountCCTransactions method ");
  }
//ReBrand
  private void updateFeeCreditCase(PGAccountTransactions accTxn) {
    logger.info(
        "SettlementServiceImpl:: updateAccountCCTransactions method :: fetching currencyConfig for fee credit with numeric code: "
            + accTxn.getTxnCurrencyCode());
    PGCurrencyConfig currencyConfig =
        currencyConfigDao.getcurrencyCodeAlpha(accTxn.getTxnCurrencyCode());
    logger.info(
        "SettlementServiceImpl:: updateAccountCCTransactions method :: currency code alpha for the above: "
            + currencyConfig.getCurrencyCodeAlpha());
    PGAccount account = accountRepository.findByEntityTypeAndCurrencyAndStatus(
        PGConstants.DEFAULT_ENTITY_TYPE, currencyConfig.getCurrencyCodeAlpha(),
        PGConstants.S_STATUS_ACTIVE);

    account.setAvailableBalance(account.getAvailableBalance() + accTxn.getCredit());
    account.setCurrentBalance(account.getCurrentBalance() + accTxn.getCredit());
    accountDao.savePGAccount(account);
    accTxn.setCurrentBalance(account.getCurrentBalance());
  }

  private void updateFeeDebitCase(PGAccountTransactions accTxn) {
    PGAccount account = accountDao.getPgAccount(accTxn.getMerchantCode());
    account.setAvailableBalance(account.getAvailableBalance() - accTxn.getDebit());
    account.setCurrentBalance(account.getCurrentBalance() - accTxn.getDebit());
    accountDao.savePGAccount(account);
    accTxn.setCurrentBalance(account.getCurrentBalance());
  }

  private void updateAmountCreditCase(PGAccountTransactions accTxn) {
    PGAccount account = accountDao.getPgAccount(accTxn.getMerchantCode());
    account.setAvailableBalance(account.getAvailableBalance() + accTxn.getCredit());
    account.setCurrentBalance(account.getCurrentBalance() + accTxn.getCredit());
    accountDao.savePGAccount(account);
    accTxn.setCurrentBalance(account.getCurrentBalance());
  }

  private void updateMerchantFeeCreditcase(PGAccountTransactions accTxn) {
     PGAccount account = accountDao
        .getPgAccount(merchantDao.getParentMerchantCode(accTxn.getMerchantCode()));
    if (null == account) {
      account = accountDao.getPgAccount(accTxn.getMerchantCode());
    }
    account.setAvailableBalance(account.getAvailableBalance() + accTxn.getCredit());
    account.setCurrentBalance(account.getCurrentBalance() + accTxn.getCredit());
    accountDao.savePGAccount(account);
    accTxn.setCurrentBalance(account.getCurrentBalance());
  }

}
