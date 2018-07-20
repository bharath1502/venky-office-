/**
 * 
 */
package com.chatak.acquirer.admin.service.impl;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.chatak.acquirer.admin.constants.StatusConstants;
import com.chatak.acquirer.admin.exception.ChatakAdminException;
import com.chatak.acquirer.admin.service.RestPaymentService;
import com.chatak.acquirer.admin.service.SettlementService;
import com.chatak.acquirer.admin.util.JsonUtil;
import com.chatak.pg.acq.dao.AccountDao;
import com.chatak.pg.acq.dao.AccountFeeLogDao;
import com.chatak.pg.acq.dao.AccountHistoryDao;
import com.chatak.pg.acq.dao.AccountTransactionsDao;
import com.chatak.pg.acq.dao.AcquirerFeeCodeDao;
import com.chatak.pg.acq.dao.CurrencyConfigDao;
import com.chatak.pg.acq.dao.FeeDetailDao;
import com.chatak.pg.acq.dao.FeeProgramDao;
import com.chatak.pg.acq.dao.IssuanceSettlementDao;
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
import com.chatak.pg.acq.dao.model.PGCurrencyCode;
import com.chatak.pg.acq.dao.model.PGCurrencyConfig;
import com.chatak.pg.acq.dao.model.PGOnlineTxnLog;
import com.chatak.pg.acq.dao.model.PGTransaction;
import com.chatak.pg.acq.dao.model.settlement.PGSettlementEntity;
import com.chatak.pg.acq.dao.model.settlement.PGSettlementTransaction;
import com.chatak.pg.acq.dao.repository.AccountRepository;
import com.chatak.pg.acq.dao.repository.CurrencyCodeRepository;
import com.chatak.pg.acq.dao.repository.TransactionRepository;
import com.chatak.pg.bean.settlement.IssuanceSettlementTransactionEntity;
import com.chatak.pg.bean.settlement.IssuanceSettlementTransactions;
import com.chatak.pg.bean.settlement.SettlementEntity;
import com.chatak.pg.constants.AccountTransactionCode;
import com.chatak.pg.constants.FeePostingStatus;
import com.chatak.pg.constants.PGConstants;
import com.chatak.pg.enums.ProcessorType;
import com.chatak.pg.exception.HttpClientException;
import com.chatak.pg.exception.PrepaidAdminException;
import com.chatak.pg.model.BulkSettlementResponse;
import com.chatak.pg.model.ProcessingFee;
import com.chatak.pg.model.Response;
import com.chatak.pg.model.SettlementActionDTOList;
import com.chatak.pg.model.SettlemetActionDTO;
import com.chatak.pg.model.VirtualAccFeeReversalRequest;
import com.chatak.pg.model.VirtualTerminalVoidDTO;
import com.chatak.pg.model.virtualAccFeePostRequest;
import com.chatak.pg.model.virtualAccFeePostResponse;
import com.chatak.pg.util.CommonUtil;
import com.chatak.pg.util.Constants;
import com.chatak.pg.util.DateUtil;
import com.chatak.pg.util.LogHelper;
import com.chatak.pg.util.LoggerMessage;
import com.chatak.pg.util.PGUtils;
import com.chatak.pg.util.Properties;
import com.chatak.pg.util.StringUtils;

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
  FeeDetailDao feeDetailDao;

  @Autowired
  AccountRepository accountRepository;
  
  @Autowired
  AccountDao accountDao;

  @Autowired
  OnlineTxnLogDao onlineTxnLogDao;

  @Autowired
  AccountHistoryDao accountHistoryDao;

  @Autowired
  MerchantDao merchantDao;

  @Autowired
  AccountFeeLogDao accountFeeLogDao;
  
  @Autowired
  AcquirerFeeCodeDao acquirerFeeCodeDao;

  @Autowired
  FeeProgramDao feeProgramDao;

  @Autowired
  AccountTransactionsDao accountTransactionsDao;

  @Autowired
  MessageSource messageSource;

  @Autowired
  CurrencyConfigDao currencyConfigDao;
  
  @Autowired
  RestPaymentService paymentService;

  @Autowired
  TransactionRepository transactionRepository;

  @Autowired
  CurrencyCodeRepository currencyCodeRepository;

  @Autowired
  VoidTransactionDao voidTransactionDao;

  @Autowired
  MerchantUpdateDao merchantUpdateDao;
  
  @Autowired
  private IssuanceSettlementDao issSettlementDao;

  private ObjectMapper mapper = new ObjectMapper();

  @Override
  public boolean updateSettlementStatus(String merchantId, String terminalId, String txnId,
      String txnType, String status, String comments, String userName, String timeZoneOffset,
      String timeZoneRegion) throws ChatakAdminException {
    LogHelper.logEntry(logger, LoggerMessage.getCallerName());
    try {
      PGTransaction pgTransaction =
          transactionDao.getTransactionOnTxnIdAndTxnType(merchantId, terminalId, txnId, txnType);
      if (null != pgTransaction) {
        return updateSettlement(status, comments, pgTransaction, userName,timeZoneOffset,timeZoneRegion);
      } else {
        return false;
      }
    } catch (Exception e) {
      logger.error("ERROR:: SettlementServiceImpl:: updateSettlementStatus method", e);
      throw new ChatakAdminException(e.getMessage());
    }
  }

  private boolean updateSettlement(String status, String comments, PGTransaction pgTransaction,
      String userName, String timeZoneOffset, String timeZoneRegion) {
    LogHelper.logEntry(logger, LoggerMessage.getCallerName());
    PGOnlineTxnLog pgOnlineTxnLog = onlineTxnLogDao.getTransactionOnPgTxnIdAndMerchantId(
        pgTransaction.getTransactionId(), pgTransaction.getMerchantId());
    if (status.equals(PGConstants.PG_SETTLEMENT_EXECUTED)) {
      List<Object> objectResult =
          getProcessingFee(PGUtils.getCCType(), 1L,
              pgTransaction.getMerchantId(), pgTransaction.getTxnTotalAmount());
      Long chatakFeeAmountTotal = (Long) objectResult.get(1);
      Long totalFeeAmount = pgTransaction.getTxnTotalAmount() - pgTransaction.getTxnAmount();
      Long merchantFeeAmount = 0l;

      if (totalFeeAmount > chatakFeeAmountTotal) {
        merchantFeeAmount = totalFeeAmount - chatakFeeAmountTotal;
      } else {
        chatakFeeAmountTotal = totalFeeAmount;
      }
      updateAccountCCTransactions(pgTransaction.getTransactionId(),
          pgTransaction.getTransactionType(), status);
      // **updating description for settlement status
      String descriptionTemplate = messageSource.getMessage("chatak-pay.sale.description.template",
          null, LocaleContextHolder.getLocale());
      descriptionTemplate = getDescriptionTemplate(pgTransaction, pgOnlineTxnLog,
          chatakFeeAmountTotal, descriptionTemplate);
      pgTransaction.setMerchantFeeAmount(merchantFeeAmount);
      pgTransaction.setTxnDescription(descriptionTemplate);
      pgTransaction.setReason(comments);// setting reason
      pgTransaction.setUserName(userName);
      pgTransaction = getPgTxnEftStatus(pgTransaction);
      
      Date date = new Date();
      String batchId = new SimpleDateFormat(Constants.BATCH_ID_DATE_FORMAT).format(date);
      pgTransaction.setBatchId(batchId + pgTransaction.getMerchantId());
      pgTransaction.setBatchDate(new Timestamp(System.currentTimeMillis()));
      
      logPgAccountHistory(pgTransaction.getMerchantId(), PGConstants.PAYMENT_METHOD_CREDIT,
          pgTransaction.getTransactionId());
    } else if (PGConstants.PG_SETTLEMENT_PROCESSING.equals(status)) {
      List<PGAccountFeeLog> pgAccountFeeLogList =
          accountFeeLogDao.getPGAccountFeeLogOnTransactionId(pgTransaction.getTransactionId());
      if (CommonUtil.isListNotNullAndEmpty(pgAccountFeeLogList)) {
        for (PGAccountFeeLog pgAccountFeeLog : pgAccountFeeLogList) {
          pgAccountFeeLog.setStatus(status.equals(PGConstants.PG_SETTLEMENT_PROCESSING)
              ? PGConstants.PG_SETTLEMENT_PROCESSING : PGConstants.PG_SETTLEMENT_REJECTED);
          pgTransaction.setReason(comments);
          pgAccountFeeLog.setUpdatedDate(new Timestamp(System.currentTimeMillis()));
          accountFeeLogDao.createOrSave(pgAccountFeeLog);
        }
      }
    } else if (PGConstants.PG_SETTLEMENT_REJECTED.equals(status)) {
      List<PGAccountFeeLog> pgAccountFeeLogList =
          accountFeeLogDao.getPGAccountFeeLogOnTransactionId(pgTransaction.getTransactionId());
      updatePGAccountFeeLog(pgAccountFeeLogList, PGConstants.PG_SETTLEMENT_REJECTED);
      return processVoidTransaction(pgTransaction, status, userName,timeZoneOffset,timeZoneRegion);
    }
    pgTransaction.setMerchantSettlementStatus(status);
    pgTransaction.setUpdatedDate(new Timestamp(System.currentTimeMillis()));
    voidTransactionDao.createTransaction(pgTransaction);
    logger.info("Exiting:: SettlementServiceImpl:: updateSettlementStatus method");
    return true;
  }

  private void updatePGAccountFeeLog(List<PGAccountFeeLog> pgAccountFeeLogList, String status) {
    if (CommonUtil.isListNotNullAndEmpty(pgAccountFeeLogList)) {
      for (PGAccountFeeLog pgAccountFeeLog : pgAccountFeeLogList) {
        pgAccountFeeLog.setStatus(status);
        pgAccountFeeLog.setUpdatedDate(new Timestamp(System.currentTimeMillis()));
        accountFeeLogDao.createOrSave(pgAccountFeeLog);
      }
    }
  }

  private PGTransaction getPgTxnEftStatus(PGTransaction pgTransaction) {
    if (ProcessorType.LITLE.value().equals(pgTransaction.getProcessor())) {
      pgTransaction.setEftStatus(PGConstants.LITLE_EXECUTED);
    }
    return pgTransaction;
  }

  private String getDescriptionTemplate(PGTransaction pgTransaction, PGOnlineTxnLog pgOnlineTxnLog,
      Long chatakFeeAmountTotal, String descriptionTemplate) {
    if (null != pgOnlineTxnLog) {
      PGCurrencyCode pGCurrencyCode =
          currencyCodeRepository.findByCurrencyCodeNumeric(pgTransaction.getTxnCurrencyCode());
      descriptionTemplate = MessageFormat.format(descriptionTemplate,
          pGCurrencyCode.getCurrencyCodeAlpha() + " "
              + StringUtils.amountToString(pgOnlineTxnLog.getMerchantAmount()),
          pGCurrencyCode.getCurrencyCodeAlpha() + " "
              + StringUtils.amountToString(chatakFeeAmountTotal));
    }
    return descriptionTemplate;
  }

  /**
  * @param pgTransaction
  * @param status
  * @return
  */
  private boolean processVoidTransaction(PGTransaction pgTransaction, String status,
      String userName, String timeZoneOffset, String timeZoneRegion) {
    VirtualTerminalVoidDTO virtualTerminalVoidDTO = new VirtualTerminalVoidDTO();
    virtualTerminalVoidDTO.setMerchantId(pgTransaction.getMerchantId());
    virtualTerminalVoidDTO.setTerminalId(pgTransaction.getTerminalId());
    virtualTerminalVoidDTO.setCgRefNumber(pgTransaction.getIssuerTxnRefNum());
    virtualTerminalVoidDTO.setTxnRefNumber(pgTransaction.getTransactionId());
    virtualTerminalVoidDTO.setTransactionType(com.chatak.pg.enums.TransactionType.VOID);
    virtualTerminalVoidDTO.setUserName(userName);
    virtualTerminalVoidDTO.setTimeZoneOffset(timeZoneOffset);
    virtualTerminalVoidDTO.setTimeZoneRegion(timeZoneRegion);
    try {
      com.chatak.pg.model.TransactionResponse response2 =
          paymentService.doVoid(virtualTerminalVoidDTO);
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
   * @throws ChatakAdminException
   */
  public void updateChatakAccount(Long feeAmount, PGTransaction transaction)
      throws ChatakAdminException {
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
      throw new ChatakAdminException(e.getMessage());
    }
  }

  public void logPgAccountHistory(String merchantId, String paymentMethod, String transactionId) {
    PGAccount updatedAccount = accountDao.getPgAccount(merchantId);
    if (null != updatedAccount) {
      PGAccountHistory pgAccountHistory = new PGAccountHistory();
      pgAccountHistory.setEntityType(updatedAccount.getEntityType());
      pgAccountHistory.setAccountDesc(updatedAccount.getAccountDesc());
      pgAccountHistory.setEntityId(updatedAccount.getEntityId());
      pgAccountHistory.setCategory(updatedAccount.getCategory());
      pgAccountHistory.setCurrentBalance(updatedAccount.getCurrentBalance());
      pgAccountHistory.setCurrency(updatedAccount.getCurrency());
      pgAccountHistory.setAutoPaymentLimit(updatedAccount.getAutoPaymentLimit());
      pgAccountHistory.setAvailableBalance(updatedAccount.getAvailableBalance());
      pgAccountHistory.setAutoPaymentMethod(updatedAccount.getAutoPaymentMethod());
      pgAccountHistory.setAutoTransferDay(updatedAccount.getAutoTransferDay());
      pgAccountHistory.setUpdatedDate(new Timestamp(System.currentTimeMillis()));
      pgAccountHistory.setAccountNum(updatedAccount.getAccountNum());
      pgAccountHistory.setStatus(updatedAccount.getStatus());
      pgAccountHistory.setTransactionId(transactionId);
      pgAccountHistory.setFeeBalance(updatedAccount.getFeeBalance());
      pgAccountHistory.setPaymentMethod(paymentMethod);
      accountHistoryDao.createOrSave(pgAccountHistory);
    }
  }

  @Override
  public BulkSettlementResponse updateBulkSettlementStatus(
      SettlementActionDTOList settlementActionDTOList, String status, String comments, String userName) {
    BulkSettlementResponse bulkSettlementResponse = new BulkSettlementResponse();
    int successCount = 0;
    bulkSettlementResponse.setSuccess(false);
    bulkSettlementResponse.setSuccessCount(successCount);

    if (!settlementActionDTOList.getActionDTOs().isEmpty()) {
      for (SettlemetActionDTO settlemetActionDTO : settlementActionDTOList.getActionDTOs()) {
        successCount = updateBulkSettlement(status, comments, bulkSettlementResponse, successCount,
            settlemetActionDTO, userName);
      }
      bulkSettlementResponse.setSuccess(true);
    } else {
      bulkSettlementResponse.setSuccess(false);
    }
    return bulkSettlementResponse;
  }

  private int updateBulkSettlement(String status, String comments,
      BulkSettlementResponse bulkSettlementResponse, int successCount,
      SettlemetActionDTO settlemetActionDTO, String userName) throws DataAccessException {
    LogHelper.logEntry(logger, LoggerMessage.getCallerName());
    PGTransaction pgTransaction;
    PGOnlineTxnLog pgOnlineTxnLog;
    LogHelper.logEntry(logger, LoggerMessage.getCallerName());
    pgTransaction = transactionDao.getTransactionOnTxnIdAndTxnType(
        settlemetActionDTO.getMerchantId(), settlemetActionDTO.getTerminalId(),
        settlemetActionDTO.getTxnId(), settlemetActionDTO.getTxnType());
    if (null != pgTransaction && !pgTransaction.getMerchantSettlementStatus().equals(status)) {

      pgOnlineTxnLog = onlineTxnLogDao.getTransactionOnPgTxnIdAndMerchantId(
          pgTransaction.getTransactionId(), pgTransaction.getMerchantId());
      if (status.equals(PGConstants.PG_SETTLEMENT_EXECUTED)) {
        List<Object> objectResult =
            getProcessingFee(PGUtils.getCCType(), 1L,
                pgTransaction.getMerchantId(), pgTransaction.getTxnTotalAmount());
        Long chatakFeeAmountTotal = (Long) objectResult.get(1);
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
        descriptionTemplate = getDescriptionTemplate(pgTransaction, pgOnlineTxnLog,
            chatakFeeAmountTotal, descriptionTemplate);
        getPgTxnEftStatus(pgTransaction);
        
        Date date = new Date();
        String batchId = new SimpleDateFormat(Constants.BATCH_ID_DATE_FORMAT).format(date);
        pgTransaction.setBatchId(batchId + pgTransaction.getMerchantId());
        pgTransaction.setBatchDate(new Timestamp(System.currentTimeMillis()));
        
        pgTransaction.setMerchantFeeAmount(merchantFeeAmount);
        pgTransaction.setTxnDescription(descriptionTemplate);
        pgTransaction.setReason(comments);// setting reason
      } else if (status.equals(PGConstants.PG_SETTLEMENT_PROCESSING)) {
        List<PGAccountFeeLog> pgAccountFeeLogList =
            accountFeeLogDao.getPGAccountFeeLogOnTransactionId(pgTransaction.getTransactionId());
        updatePGAccountFeeLog(pgAccountFeeLogList, PGConstants.PG_SETTLEMENT_PROCESSING);
      } else if (PGConstants.PG_SETTLEMENT_REJECTED.equals(status)) {
        List<PGAccountFeeLog> pgAccountFeeLogList =
            accountFeeLogDao.getPGAccountFeeLogOnTransactionId(pgTransaction.getTransactionId());
        updatePGAccountFeeLog(pgAccountFeeLogList, PGConstants.PG_SETTLEMENT_REJECTED);
        boolean isSuccess = processVoidTransaction(pgTransaction, status, userName,
            settlemetActionDTO.getTimeZoneOffset(), settlemetActionDTO.getTimeZoneRegion());
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

  public List<Object> getProcessingFee(String cardType, Long partnerId, String merchantCode,
      Long txnTotalAmount) throws DataAccessException {
    logger.info("Entering:: SettlementServiceImpl:: getProcessingFee method ");
    List<Object> results = new ArrayList<Object>(Constants.TWO);
    List<ProcessingFee> calculatedProcessingFeeList = new ArrayList<ProcessingFee>(0);
    Long chatakFeeAmountTotal = 0l;
    List<PGAcquirerFeeValue> acquirerFeeValueList =
        feeProgramDao.getAcquirerFeeValueByMerchantIdAndCardType(merchantCode, cardType);
    if (CommonUtil.isListNotNullAndEmpty(acquirerFeeValueList)) {
      logger.info(
          " TransactionService:: getProcessingFee method :: Applying this merchant fee code ");
      chatakFeeAmountTotal = getChatakFeeAmountTotal(txnTotalAmount, calculatedProcessingFeeList,
          chatakFeeAmountTotal, acquirerFeeValueList);
    } else {
      String parentMerchantCode = merchantDao.getParentMerchantCode(merchantCode);
      if (parentMerchantCode != null) {
        acquirerFeeValueList =
            feeProgramDao.getAcquirerFeeValueByMerchantIdAndCardType(parentMerchantCode, cardType);
        if (CommonUtil.isListNotNullAndEmpty(acquirerFeeValueList)) {
          logger.info(
              "Exiting:: TransactionService:: getProcessingFee method :: Applying parentMerchantCode fee ");
          chatakFeeAmountTotal = getChatakFeeAmountTotal(txnTotalAmount,
              calculatedProcessingFeeList, chatakFeeAmountTotal, acquirerFeeValueList);
          calculatedProcessingFeeList = getCalculatedProcessingFeeList(txnTotalAmount,
              calculatedProcessingFeeList, chatakFeeAmountTotal, acquirerFeeValueList);
        }
      }
    }
    logger.info("Exiting:: TransactionService:: getProcessingFee method ");
    results.add(calculatedProcessingFeeList);
    results.add(chatakFeeAmountTotal);
    return results;
  }

  private Long getChatakFeeAmountTotal(Long txnTotalAmount,
      List<ProcessingFee> calculatedProcessingFeeList, Long chatakFeeAmountTotal,
      List<PGAcquirerFeeValue> acquirerFeeValueList) {
    Double calculatedProcessingFee;
    ProcessingFee processingFee;
    for (PGAcquirerFeeValue acquirerFeeValue : acquirerFeeValueList) {
      calculatedProcessingFee = 0.00;
      processingFee =
          getProcessingFeeItem(acquirerFeeValue, txnTotalAmount, calculatedProcessingFee);
      chatakFeeAmountTotal =
          chatakFeeAmountTotal + CommonUtil.getLongAmount(processingFee.getChatakProcessingFee());
      calculatedProcessingFeeList.add(processingFee);
    }
    return chatakFeeAmountTotal;
  }

  private List<ProcessingFee> getCalculatedProcessingFeeList(Long txnTotalAmount,
      List<ProcessingFee> calculatedProcessingFeeList, Long chatakFeeAmountTotal,
      List<PGAcquirerFeeValue> acquirerFeeValueList) {
    Double calculatedProcessingFee;
    ProcessingFee processingFee;
    for (PGAcquirerFeeValue acquirerFeeValue : acquirerFeeValueList) {
      calculatedProcessingFee = 0.00;
      processingFee =
          getProcessingFeeItem(acquirerFeeValue, txnTotalAmount, calculatedProcessingFee);
      chatakFeeAmountTotal =
          chatakFeeAmountTotal + CommonUtil.getLongAmount(processingFee.getChatakProcessingFee());
      calculatedProcessingFeeList.add(processingFee);
    }
    return calculatedProcessingFeeList;
  }

  @Override
  public PGAccountFeeLog postVirtualAccFee(PGAccountFeeLog pgAccountFeeLog, String agentId,
      String partnerId, String mode, String programManagerId)
          throws ChatakAdminException, IOException {
    PGAccountFeeLog feeLog = pgAccountFeeLog;
    virtualAccFeePostRequest request = new virtualAccFeePostRequest();
    request.setAgentId(agentId);
    request.setRapidFee(feeLog.getChatakFee());
    request.setCaTransactionId(feeLog.getTransactionId());
    request.setMerchantFee(feeLog.getMerchantFee());
    request.setPartnerId(partnerId);
    request.setMode("Credit");
    request.setMerchantAmount(feeLog.getTxnAmount());// sending merchant sub merchant txn amount to issuance
    request.setProgramManagerId(programManagerId);
    request
        .setTotalTxnAmount(feeLog.getTxnAmount() + feeLog.getChatakFee() + feeLog.getMerchantFee());// sending total txn amount to issuance
    request.setSpecificAccountNumber(pgAccountFeeLog.getSpecificAccNumber());// Setting specific account number from fee program
    /* Start posting fee to issuance */
    try {
      /* End posting fee to issuance */
    feeLog.setFeeTxnDate(new Timestamp(System.currentTimeMillis()));
      String output =  JsonUtil.sendToIssuance(request,
      Properties.getProperty("chatak-issuance.virtual.post.fee"), mode,String.class);
      virtualAccFeePostResponse feeResponse =
      mapper.readValue(output, virtualAccFeePostResponse.class);
      if (feeResponse != null) {
        if (feeResponse.getErrorCode().equals("CEC_0001")) {
          feeLog.setFeePostStatus(FeePostingStatus.FEE_POST_SUCCESS);
          feeLog.setIssuanceFeeTxnId(feeResponse.getCiVirtualAccTxnId());
        } else {
          feeLog.setFeePostStatus(FeePostingStatus.FEE_POST_DECLINED);
        }
        feeLog.setIssuanceMessage(feeResponse.getErrorMessage());
      }
    } catch (HttpClientException exp) {
        logger.error("ERROR:: SettlementServiceImpl:: postVirtualAccFee method ", exp);
      }
    return feeLog;
  }

  @Override
  public PGAccountFeeLog postVirtualAccFeeReversal(PGAccountFeeLog pgAccountFeeLog, String agentId,
      String ciVirtualAccTxnId, String mode) throws ChatakAdminException, IOException {
    PGAccountFeeLog feeLog = pgAccountFeeLog;
    VirtualAccFeeReversalRequest request = new VirtualAccFeeReversalRequest();
    request.setCiVirtualAccTxnId(ciVirtualAccTxnId);
    try {
    feeLog.setFeeTxnDate(new Timestamp(System.currentTimeMillis()));
      String output =  JsonUtil.sendToIssuance(request,
      Properties.getProperty("chatak-issuance.virtual.reverse.fee"), mode,String.class);
      virtualAccFeePostResponse feeResponse =
      mapper.readValue(output, virtualAccFeePostResponse.class);
      if (feeResponse != null) {
        if (feeResponse.getErrorCode().equals("CEC_0001")) {
          feeLog.setFeePostStatus(FeePostingStatus.FEE_POST_SUCCESS);
        } else {
          feeLog.setFeePostStatus(FeePostingStatus.FEE_POST_DECLINED);
        }
        feeLog.setIssuanceMessage(feeResponse.getErrorMessage());
        feeLog.setFeeTxnDate(new Timestamp(System.currentTimeMillis()));
      }
    } catch (IOException exp) {
        logger.error("ERROR:: SettlementServiceImpl:: postVirtualAccFeeReversal method ", exp);
        throw new IOException(exp.getMessage());
    }catch (Exception exp) {
        logger.error("ERROR:: SettlementServiceImpl:: postVirtualAccFeeReversal method ", exp);
        throw new ChatakAdminException(exp.getMessage());
    }
    return feeLog;
  }

  /**
  * @param pgTransactionId
  * @param txnType
  * @param newStatus
  */
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
            accTxn = udpateAccountCcAmountCredit(currentTime, accTxn);
            break;
          case AccountTransactionCode.CC_FEE_DEBIT:
            accTxn = updateAccountForCcFeeDebit(currentTime, accTxn);
            break;
          case AccountTransactionCode.CC_MERCHANT_FEE_CREDIT:
            accTxn = updateAccountForMerchantFeeCredit(currentTime, accTxn);
            break;
          case AccountTransactionCode.CC_ACQUIRER_FEE_CREDIT:
            accTxn = updateAccountForAcquirerFeeCredit(currentTime, accTxn);
            break;
          default:
        }
      }
      accTxn.setProcessedTime(new Timestamp(System.currentTimeMillis()));
      accountTransactionsDao.createOrUpdate(accTxn);
    }

    logger.info("Exiting:: SettlementServiceImpl:: updateAccountCCTransactions method ");
  }
  
  /**
   * @param acquirerFeeValue
   * @param txnTotalAmount
   * @param calculatedProcessingFee
   * @return
   */
  private ProcessingFee getProcessingFeeItem(PGAcquirerFeeValue acquirerFeeValue,
      Long txnTotalAmount, Double calculatedProcessingFee) {
    logger.info("Entering:: SettlementServiceImpl:: getProcessingFeeItem method ");
    ProcessingFee processingFee = new ProcessingFee();
    Double percentageFee = acquirerFeeValue.getFeePercentageOnly();
    Double flatFee = CommonUtil.getDoubleAmountNotNull(acquirerFeeValue.getFlatFee());
    percentageFee = txnTotalAmount * (CommonUtil.getDoubleAmountNotNull(percentageFee));
    calculatedProcessingFee =
        (CommonUtil.getDoubleAmountNotNull(calculatedProcessingFee + percentageFee)) + flatFee;
    processingFee.setAccountNumber(acquirerFeeValue.getAccountNumber());
    processingFee.setChatakProcessingFee(calculatedProcessingFee);
    logger.info("Exiting:: SettlementServiceImpl:: getProcessingFeeItem method ");
    return processingFee;
  }

  private PGAccountTransactions udpateAccountCcAmountCredit(Timestamp currentTime,
      PGAccountTransactions accTxn) {
    PGAccount account;
    account = accountDao.getPgAccount(accTxn.getMerchantCode());
    account.setAvailableBalance(account.getAvailableBalance() + accTxn.getCredit());
    account.setCurrentBalance(account.getCurrentBalance() + accTxn.getCredit());
    accountDao.savePGAccount(account);
    accTxn.setCurrentBalance(account.getCurrentBalance());
    accTxn.setProcessedTime(currentTime);
    accTxn.setStatus(PGConstants.PG_SETTLEMENT_EXECUTED);
    return accTxn;
  }

  private PGAccountTransactions updateAccountForCcFeeDebit(Timestamp currentTime,
      PGAccountTransactions accTxn) {
    PGAccount account;
    account = accountDao.getPgAccount(accTxn.getMerchantCode());
    account.setAvailableBalance(account.getAvailableBalance() - accTxn.getDebit());
    account.setCurrentBalance(account.getCurrentBalance() - accTxn.getDebit());
    accountDao.savePGAccount(account);
    accTxn.setCurrentBalance(account.getCurrentBalance());
    accTxn.setProcessedTime(currentTime);
    accTxn.setStatus(PGConstants.PG_SETTLEMENT_EXECUTED);
    return accTxn;
  }

  private PGAccountTransactions updateAccountForMerchantFeeCredit(Timestamp currentTime,
      PGAccountTransactions accTxn) {
    PGAccount account;
    account = accountDao.getPgAccount(merchantDao.getParentMerchantCode(accTxn.getMerchantCode()));
    if (null == account) {
      account = accountDao.getPgAccount(accTxn.getMerchantCode());
    }
    account.setAvailableBalance(account.getAvailableBalance() + accTxn.getCredit());
    account.setCurrentBalance(account.getCurrentBalance() + accTxn.getCredit());
    accountDao.savePGAccount(account);
    accTxn.setCurrentBalance(account.getCurrentBalance());
    accTxn.setProcessedTime(currentTime);
    accTxn.setStatus(PGConstants.PG_SETTLEMENT_EXECUTED);
    return accTxn;
  }

  private PGAccountTransactions updateAccountForAcquirerFeeCredit(Timestamp currentTime,
      PGAccountTransactions accTxn) {
    PGAccount account;
    logger.info(
        "SettlementServiceImpl:: updateAccountCCTransactions method fetching transactions by PG TRANS ID: "
            + accTxn.getPgTransactionId());
    List<PGTransaction> transactions =
        transactionRepository.findByTransactionId(accTxn.getPgTransactionId());
    PGTransaction transaction = transactions.get(0);

    logger.info(
        "SettlementServiceImpl:: updateAccountCCTransactions method :: fetching currencyConfig for fee credit with numeric code: "
            + transaction.getTxnCurrencyCode());
    PGCurrencyConfig currencyConfig =
        currencyConfigDao.getcurrencyCodeAlpha(transaction.getTxnCurrencyCode());
    logger.info(
        "SettlementServiceImpl:: updateAccountCCTransactions method :: currency code alpha for the above: "
            + currencyConfig.getCurrencyCodeAlpha());
    account =
        accountRepository.findByEntityTypeAndCurrencyAndStatus(PGConstants.DEFAULT_ENTITY_TYPE,
            currencyConfig.getCurrencyCodeAlpha(), PGConstants.S_STATUS_ACTIVE);

    account.setAvailableBalance(account.getAvailableBalance() + accTxn.getCredit());
    account.setCurrentBalance(account.getCurrentBalance() + accTxn.getCredit());
    accountDao.savePGAccount(account);
    accTxn.setCurrentBalance(account.getCurrentBalance());
    accTxn.setProcessedTime(currentTime);
    accTxn.setStatus(PGConstants.PG_SETTLEMENT_EXECUTED);
    return accTxn;
  }
  
	public List<SettlementEntity> getPgTransactions(String merchantId, String terminalId, String issuerTxnRefNum,
			String transactionId) {
		return transactionDao.getPgTransactions(merchantId, terminalId, issuerTxnRefNum,
				transactionId);
	}

	@Transactional(rollbackFor=ChatakAdminException.class)
	public Response saveIssuanceSettlementTransaction(IssuanceSettlementTransactionEntity pgSettlementTransaction, Integer batchCount, Integer batchSize) 
	    throws InstantiationException, IllegalAccessException, ChatakAdminException {
		LogHelper.logEntry(logger, LoggerMessage.getCallerName());
		
		Response response = new Response();
		try {
		    Long START_TIME = System.currentTimeMillis();
		    PGSettlementEntity pgSettlementEntity = seEntityAndTxnValues(pgSettlementTransaction);
		    LogHelper.logInfo(logger, LoggerMessage.getCallerName(), "seEntityAndTxnValues DB Processing time: " + (System.currentTimeMillis() -  START_TIME));
		    
		    START_TIME = System.currentTimeMillis();
		    issSettlementDao.addIssuanceSettlementTransaction(pgSettlementEntity, batchCount, batchSize);
	        LogHelper.logInfo(logger, LoggerMessage.getCallerName(), "addIssuanceSettlementTransaction DB Processing time: " + (System.currentTimeMillis() -  START_TIME));
	        response.setErrorCode(PGConstants.SUCCESS);
	        response.setErrorMessage(StatusConstants.STATUS_MESSAGE_SUCCESS);
	        LogHelper.logExit(logger, LoggerMessage.getCallerName());
	        return response;
	        
		} catch(Exception ex){
		  LogHelper.logError(logger, LoggerMessage.getCallerName(), ex, Constants.EXCEPTION);
          response.setErrorCode(StatusConstants.STATUS_CODE_FAILED);
          response.setErrorMessage(StatusConstants.STATUS_MESSAGE_FAILED);
          throw new ChatakAdminException(StatusConstants.STATUS_CODE_FAILED,StatusConstants.STATUS_MESSAGE_FAILED);
        }
	}

	private PGSettlementEntity seEntityAndTxnValues(IssuanceSettlementTransactionEntity pgSettlementTransaction) {
		LogHelper.logEntry(logger, LoggerMessage.getCallerName());
		PGSettlementEntity pgSettlementEntity = new PGSettlementEntity();
		List<PGSettlementTransaction> pgSettlementTransactions = new ArrayList<>();
		pgSettlementEntity.setMerchantId(pgSettlementTransaction.getMerchantId());
		pgSettlementEntity.setAcqSaleAmount(pgSettlementTransaction.getAcqSaleAmount());
		pgSettlementEntity.setIssSaleAmount(pgSettlementTransaction.getIssSaleAmount());
		pgSettlementEntity.setAcqPmId(pgSettlementTransaction.getAcqPmId());
		pgSettlementEntity.setIssPmId(pgSettlementTransaction.getIssPmId());
		pgSettlementEntity.setBatchid(pgSettlementTransaction.getBatchid());
		pgSettlementEntity.setBatchFileDate(pgSettlementTransaction.getBatchFileDate());
		pgSettlementEntity.setBatchFileProcessedDate(pgSettlementTransaction.getBatchFileDate());
		pgSettlementEntity.setStatus(pgSettlementTransaction.getStatus());
		pgSettlementEntity.setPmAmount(pgSettlementTransaction.getPmAmount());
		pgSettlementEntity.setMerchantAmount(pgSettlementTransaction.getMerchantAmount());
		
		LogHelper.logInfo(logger, LoggerMessage.getCallerName(), "IssuanceSettlementTransactions size: " + pgSettlementTransaction.getSettlementTransactionsList());
		
		for(IssuanceSettlementTransactions issuanceSettlementTransactions : pgSettlementTransaction.getSettlementTransactionsList()) {
		  LogHelper.logInfo(logger, LoggerMessage.getCallerName(), "Saving transaction with Pg txn ID: " + issuanceSettlementTransactions.getPgTransactionId()
		      + ", Iss txn Id: " + issuanceSettlementTransactions.getIssuerTxnID());
		  
			PGSettlementTransaction pgSettlementTxn = new PGSettlementTransaction();
			pgSettlementTxn.setIsoAmount(issuanceSettlementTransactions.getIsoAmount());
			pgSettlementTxn.setIsoId(issuanceSettlementTransactions.getIsoId());
			pgSettlementTxn.setIssuerTxnID(issuanceSettlementTransactions.getIssuerTxnID());
			pgSettlementTxn.setPgTransactionId(issuanceSettlementTransactions.getPgTransactionId());
			pgSettlementTxn.setTerminalId(issuanceSettlementTransactions.getTerminalId());
			pgSettlementTxn.setTxnDate(issuanceSettlementTransactions.getTxnDate());
			pgSettlementTransactions.add(pgSettlementTxn);
		}
		
		pgSettlementEntity.setSettlementTransactionsList(pgSettlementTransactions);
		LogHelper.logInfo(logger, LoggerMessage.getCallerName(), "Size of the PGSettlementTransaction array is " +pgSettlementTransactions.size());
		LogHelper.logExit(logger, LoggerMessage.getCallerName());
		return pgSettlementEntity;
	}

	public void deleteAllIssuanceSettlementData(String programManagerId) throws PrepaidAdminException {
		issSettlementDao.deleteAllIssuanceSettlementData(programManagerId);
	}

	public List<PGTransaction> getPGTransactionListNotInAcquiring(String batchId, List<String> pgTxnIds) {
		return transactionDao.getPGTransactionListNotInAcquiring(batchId, pgTxnIds);
	}
	
	public List<PGAccountTransactions> getPGAccTransactionsByTxnId(String pgTxnId) {
		return transactionDao.getPGAccTransactionsByTxnId(pgTxnId);
	}
	
	public void logRevenueAccountTransaction(String batchId,
			Long accountNumber, Long currentBalance, Long entityId, 
			Long amountToTransfer, String transactionCode, String entityType,
			String timeZoneOffset, String timeZoneRegion, String accountTransactionId) {
		logger.info("Entering:: SettlementServiceImpl:: logRevenueAccountTransaction method with Txn Code : " + transactionCode);
		Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());
		
		PGAccountTransactions pgAccountTransactions = new PGAccountTransactions();
		pgAccountTransactions.setAccountNumber(accountNumber.toString());
		pgAccountTransactions.setAccountTransactionId(accountTransactionId);
		pgAccountTransactions.setCurrentBalance(currentBalance);
		pgAccountTransactions.setEntityId(entityId);
		pgAccountTransactions.setEntityType(entityType);
		pgAccountTransactions.setBatchId(batchId);
		
		pgAccountTransactions.setProcessedTime(currentTimestamp);
		pgAccountTransactions.setTransactionTime(currentTimestamp);
		pgAccountTransactions.setCreatedDate(currentTimestamp);
		
		pgAccountTransactions.setTransactionCode(transactionCode);
		pgAccountTransactions.setTransactionType(transactionCode);
		
		pgAccountTransactions.setStatus(PGConstants.PG_SETTLEMENT_EXECUTED);
		pgAccountTransactions.setTimeZoneOffset(timeZoneOffset);
		pgAccountTransactions.setTimeZoneRegion(timeZoneRegion);
		
		logger.info("SettlementServiceImpl:: logRevenueAccountTransaction method::" + transactionCode);

		switch (transactionCode) {
		case AccountTransactionCode.SYSTEM_REVENUE_CREDIT:
			pgAccountTransactions.setCredit(amountToTransfer);
			String amount = StringUtils.amountToString(amountToTransfer);
			pgAccountTransactions.setDescription("System Revenue Credit : " + amount);
			pgAccountTransactions.setDeviceLocalTxnTime(
					DateUtil.convertTimeZone(timeZoneOffset, currentTimestamp.toString()));
			break;
		case AccountTransactionCode.SYSTEM_REVENUE_DEBIT:
			pgAccountTransactions.setDebit(amountToTransfer);
			String amt = StringUtils.amountToString(amountToTransfer);
			pgAccountTransactions.setDescription("System Revenue Debit : " + amt);
			pgAccountTransactions.setDeviceLocalTxnTime(
					DateUtil.convertTimeZone(timeZoneOffset, currentTimestamp.toString()));
			break;
		default:
			return;
		}
		logger.info("Exiting:: SettlementServiceImpl:: logRevenueAccountTransaction method");
		accountTransactionsDao.createOrUpdate(pgAccountTransactions);
	}

	@Override
	public List<PGAccountTransactions> getAccountTransactionsOnTransactionId(String pgTransactionId) {
		return accountTransactionsDao.getAccountTransactionsOnTransactionId(pgTransactionId);
	}
}