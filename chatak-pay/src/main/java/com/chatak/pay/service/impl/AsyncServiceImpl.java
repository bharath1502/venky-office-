package com.chatak.pay.service.impl;

import java.math.BigInteger;
import java.sql.Timestamp;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.dao.DataAccessException;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.chatak.pay.exception.ChatakPayException;
import com.chatak.pay.service.AsyncService;
import com.chatak.pg.acq.dao.AccountDao;
import com.chatak.pg.acq.dao.AccountHistoryDao;
import com.chatak.pg.acq.dao.AccountTransactionsDao;
import com.chatak.pg.acq.dao.CurrencyConfigDao;
import com.chatak.pg.acq.dao.FeeProgramDao;
import com.chatak.pg.acq.dao.MerchantDao;
import com.chatak.pg.acq.dao.OnlineTxnLogDao;
import com.chatak.pg.acq.dao.VoidTransactionDao;
import com.chatak.pg.acq.dao.model.PGAccount;
import com.chatak.pg.acq.dao.model.PGAccountHistory;
import com.chatak.pg.acq.dao.model.PGAccountTransactions;
import com.chatak.pg.acq.dao.model.PGAcquirerFeeValue;
import com.chatak.pg.acq.dao.model.PGCurrencyCode;
import com.chatak.pg.acq.dao.model.PGCurrencyConfig;
import com.chatak.pg.acq.dao.model.PGOnlineTxnLog;
import com.chatak.pg.acq.dao.model.PGTransaction;
import com.chatak.pg.acq.dao.repository.AccountRepository;
import com.chatak.pg.acq.dao.repository.CurrencyCodeRepository;
import com.chatak.pg.acq.dao.repository.TransactionRepository;
import com.chatak.pg.constants.AccountTransactionCode;
import com.chatak.pg.constants.PGConstants;
import com.chatak.pg.enums.ProcessorType;
import com.chatak.pg.enums.TransactionStatus;
import com.chatak.pg.model.ProcessingFee;
import com.chatak.pg.util.CommonUtil;
import com.chatak.pg.util.PGUtils;
import com.chatak.pg.util.StringUtils;

@EnableAsync
@Component
@ComponentScan(basePackages = "com.chatak")
public class AsyncServiceImpl implements AsyncService {

  private static Logger log = LogManager.getLogger(AsyncServiceImpl.class);

  @Autowired
  private OnlineTxnLogDao onlineTxnLogDao;

  @Autowired
  private VoidTransactionDao voidTransactionDao;

  @Autowired
  private FeeProgramDao feeProgramDao;
  
  @Autowired
  private MessageSource messageSource;
  
  @Autowired
  private CurrencyCodeRepository currencyCodeRepository;
  
  @Autowired
  private AccountDao accountDao;
  
  @Autowired
  private AccountHistoryDao accountHistoryDao;
  
  @Autowired
  private AccountTransactionsDao accountTransactionsDao;
  
  @Autowired
  private AccountRepository accountRepository;
  
  @Autowired
  private TransactionRepository transactionRepository;
  
  @Autowired
  private MerchantDao merchantDao;
  
  @Autowired
  private CurrencyConfigDao currencyConfigDao;
  
  /**
   * @param pgOnlineTxnLog
   * @param txnState
   * @param reason
   * @param pgTxnId
   * @param processorResponse
   * @param processTxnId
   */
  @Async
  public void logExit(PGOnlineTxnLog pgOnlineTxnLog, TransactionStatus txnState, String reason,
      String pgTxnId, String processorResponse, String processTxnId) {
    pgOnlineTxnLog.setPgTxnId(pgTxnId);
    pgOnlineTxnLog.setProcessorResponse(processorResponse);
    pgOnlineTxnLog.setProcessorTxnId(processTxnId);
    pgOnlineTxnLog.setResponseDateTime(new Timestamp(System.currentTimeMillis()));
    pgOnlineTxnLog.setTxnState(txnState.name());
    pgOnlineTxnLog.setTxnReason(reason);
    onlineTxnLogDao.logRequest(pgOnlineTxnLog);
  }

  @Async
  @Transactional
  public void updateSettlementStatus(String merchantId, String terminalId, String txnId,
      String txnType, String status, String comments, long feeAmount, String batchId,
      PGOnlineTxnLog pgOnlineTxnLog) throws Exception {
	  try {
		  log.info("Entering :: AsyncServiceImpl :: updateSettlementStatus method");
		  
		  // PERF >> Using primary key transaction id as reference
		  //PGTransaction pgTransaction = transactionDao.getTransactionOnTxnIdAndTxnType(merchantId, terminalId, txnId, txnType);
		  
		  PGTransaction pgTransaction = transactionRepository.findById(new BigInteger(txnId));
		  
		  if(null != pgTransaction) {
			  //PGOnlineTxnLog pgOnlineTxnLog = onlineTxnLogDao.getTransactionOnPgTxnIdAndMerchantId(pgTransaction.getTransactionId(), pgTransaction.getMerchantId());
			  if(status.equals(PGConstants.PG_SETTLEMENT_EXECUTED)) {
				  Long chatakFeeAmountTotal;
				  List<Object> objectResult = getProcessingFee(PGUtils.getCCType(),
						  pgTransaction.getMerchantId(), pgTransaction.getTxnTotalAmount());
				  chatakFeeAmountTotal = (Long) objectResult.get(1);

				  validatePGCurrencyCode(status, comments, feeAmount, pgTransaction, pgOnlineTxnLog, chatakFeeAmountTotal);
				  
                  pgTransaction.setBatchId(batchId);
                  pgTransaction.setBatchDate(new Timestamp(System.currentTimeMillis()));
                  
				  logAccountHistory(pgTransaction.getMerchantId(), PGConstants.PAYMENT_METHOD_CREDIT, pgTransaction.getTransactionId());
			  }
			  pgTransaction.setMerchantSettlementStatus(status);
			  pgTransaction.setUpdatedDate(new Timestamp(System.currentTimeMillis()));
			  voidTransactionDao.createTransaction(pgTransaction);
			  log.info("Exiting :: AsyncServiceImpl :: updateSettlementStatus method");
		  }
	  } catch(Exception e) {
		  log.error("ERROR :: AsyncServiceImpl :: updateSettlementStatus method", e);
		  throw new ChatakPayException(e.getMessage());
	  }
  }

  private List<Object> getProcessingFee(String cardType, String merchantCode, Long txnTotalAmount)
      throws DataAccessException {
    log.info("Entering :: AsyncServiceImpl :: getProcessingFee method ");
    List<Object> results = new ArrayList<Object>(Integer.parseInt("2"));
    List<ProcessingFee> calculatedProcessingFeeList = new ArrayList<ProcessingFee>(0);
    Double calculatedProcessingFee = null;
    Long chatakFeeAmountTotal = 0l;
    List<PGAcquirerFeeValue> acquirerFeeValueList =
        feeProgramDao.getAcquirerFeeValueByMerchantIdAndCardType(merchantCode, cardType);
    if (CommonUtil.isListNotNullAndEmpty(acquirerFeeValueList)) {

      log.info("AsyncServiceImpl :: getProcessingFee method :: Applying this merchant fee code ");
      ProcessingFee processingFee = null;
      for (PGAcquirerFeeValue acquirerFeeValue : acquirerFeeValueList) {
        calculatedProcessingFee = 0.00;
        processingFee =
            getProcessingFeeItem(acquirerFeeValue, txnTotalAmount, calculatedProcessingFee);
        chatakFeeAmountTotal =
            chatakFeeAmountTotal + CommonUtil.getLongAmount(processingFee.getChatakProcessingFee());
        calculatedProcessingFeeList.add(processingFee);
      }
    }
    log.info("Exiting :: AsyncServiceImpl :: getProcessingFee method ");
    results.add(calculatedProcessingFeeList);
    results.add(chatakFeeAmountTotal);
    return results;
  }


  private ProcessingFee getProcessingFeeItem(PGAcquirerFeeValue acquirerFeeValue,
      Long txnTotalAmount, Double calculatedProcessingFee) {
    log.info("Entering :: AsyncServiceImpl :: getProcessingFeeItem method ");
    ProcessingFee processingFee = new ProcessingFee();
    Double flatFee = CommonUtil.getDoubleAmountNotNull(acquirerFeeValue.getFlatFee());
    Double percentageFee = acquirerFeeValue.getFeePercentageOnly();
    percentageFee = txnTotalAmount * (CommonUtil.getDoubleAmountNotNull(percentageFee));
    calculatedProcessingFee =
        (CommonUtil.getDoubleAmountNotNull(calculatedProcessingFee + percentageFee)) + flatFee;
    processingFee.setAccountNumber(acquirerFeeValue.getAccountNumber());
    processingFee.setChatakProcessingFee(calculatedProcessingFee);
    log.info("Exiting :: AsyncServiceImpl :: getProcessingFeeItem method ");
    return processingFee;
  }

  private void validatePGCurrencyCode(String status, String comments, long feeAmount, PGTransaction pgTransaction,
			PGOnlineTxnLog pgOnlineTxnLog, Long chatakFeeAmountTotal) {
		Long totalFeeAmount = pgTransaction.getTxnTotalAmount() - pgTransaction.getTxnAmount();
		Long merchantFeeAmount = 0l;

		  if(totalFeeAmount > chatakFeeAmountTotal) {
			  merchantFeeAmount = totalFeeAmount - chatakFeeAmountTotal;
		  }
		  updateAccountCCTransactions(pgTransaction.getTransactionId(), pgTransaction.getTransactionType(), status, totalFeeAmount);
		  String descriptionTemplate = messageSource.getMessage("chatak-pay.sale.description.template", null, LocaleContextHolder.getLocale());
		  if(null != pgOnlineTxnLog) {
			  PGCurrencyCode pGCurrencyCode = currencyCodeRepository.findByCurrencyCodeNumeric(pgTransaction.getTxnCurrencyCode());
			  descriptionTemplate = MessageFormat.format(descriptionTemplate,
					  pGCurrencyCode.getCurrencyCodeAlpha() + " " + StringUtils.amountToString(pgOnlineTxnLog.getMerchantAmount()),
					  pGCurrencyCode.getCurrencyCodeAlpha() + " " + StringUtils.amountToString(feeAmount));
		  }

		  pgTransaction.setMerchantFeeAmount(merchantFeeAmount);
		  pgTransaction.setTxnDescription(descriptionTemplate);
		  pgTransaction.setReason(comments);
		  if(ProcessorType.LITLE.value().equals(pgTransaction.getProcessor())) {
			  pgTransaction.setEftStatus(PGConstants.LITLE_EXECUTED);
		  }
	}

  private void logAccountHistory(String merchantId, String paymentMethod, String transactionId)
      throws Exception {
    log.info("Entering :: AsyncServiceImpl :: logAccountHistory method");

    PGAccount updatedAccount = accountDao.getPgAccount(merchantId);
    if (null != updatedAccount) {
      PGAccountHistory pgAccountHistory = new PGAccountHistory();
      pgAccountHistory.setEntityId(updatedAccount.getEntityId());
      pgAccountHistory.setAccountDesc(updatedAccount.getAccountDesc());
      pgAccountHistory.setEntityType(updatedAccount.getEntityType());
      pgAccountHistory.setTransactionId(transactionId);
      pgAccountHistory.setCategory(updatedAccount.getCategory());
      pgAccountHistory.setAvailableBalance(updatedAccount.getAvailableBalance());
      pgAccountHistory.setCurrentBalance(updatedAccount.getCurrentBalance());
      pgAccountHistory.setTransactionId(transactionId);
      pgAccountHistory.setCurrency(updatedAccount.getCurrency());
      pgAccountHistory.setAutoPaymentMethod(updatedAccount.getAutoPaymentMethod());
      pgAccountHistory.setAutoPaymentLimit(updatedAccount.getAutoPaymentLimit());
      pgAccountHistory.setTransactionId(transactionId);
      pgAccountHistory.setAutoTransferDay(updatedAccount.getAutoTransferDay());
      pgAccountHistory.setUpdatedDate(new Timestamp(System.currentTimeMillis()));
      pgAccountHistory.setStatus(updatedAccount.getStatus());
      pgAccountHistory.setTransactionId(transactionId);
      pgAccountHistory.setAccountNum(updatedAccount.getAccountNum());
      pgAccountHistory.setPaymentMethod(paymentMethod);
      pgAccountHistory.setFeeBalance(updatedAccount.getFeeBalance());
      accountHistoryDao.createOrSave(pgAccountHistory);
    }
    log.info("Exiting :: AsyncServiceImpl :: logAccountHistory method");
  }

  public void updateAccountCCTransactions(String pgTransactionId, String txnType, String newStatus, Long totalFeeAmount) {
    PGAccount account = null;
    Timestamp currentTime = new Timestamp(System.currentTimeMillis());
    log.info("Entering :: AsyncServiceImpl :: updateAccountCCTransactions method ");
    List<PGAccountTransactions> accountTxns = accountTransactionsDao.getAccountTransactionsOnTransactionIdAndTransactionType(pgTransactionId, txnType);
    for(PGAccountTransactions accTxn : accountTxns) {

        if(PGConstants.PG_SETTLEMENT_REJECTED.equals(newStatus)) {
            accTxn.setStatus(PGConstants.PG_SETTLEMENT_REJECTED);
        } else {

            switch(accTxn.getTransactionCode()) {
            case AccountTransactionCode.CC_AMOUNT_CREDIT:
                // updating pg account debting refund amount
                account=accountDao.getPgAccount(accTxn.getMerchantCode());
                setPGAccountDetails(currentTime, accTxn, account, totalFeeAmount);
                break;
            case AccountTransactionCode.CC_FEE_DEBIT:
                account=accountDao.getPgAccount(accTxn.getMerchantCode());
                account.setAvailableBalance(account.getAvailableBalance() - accTxn.getDebit());
                account.setCurrentBalance(account.getCurrentBalance() - accTxn.getDebit());
                setPGAccDetails(account, currentTime, accTxn);
                break;
            case AccountTransactionCode.CC_MERCHANT_FEE_CREDIT:
                validateForMerchantFeeCredit(currentTime, accTxn, totalFeeAmount);
                break;
            case AccountTransactionCode.CC_ACQUIRER_FEE_CREDIT:
                log.info("AsyncServiceImpl :: updateAccountCCTransactions method fetching transactions by PG TRANS ID: " + accTxn.getPgTransactionId());
                PGTransaction transaction = transactionRepository.findByTransactionId(accTxn.getPgTransactionId());

                validateForEntityTypeAndCurrencyAndStatus(currentTime, accTxn, transaction, totalFeeAmount);
                break;
            default:
            }
        }
        accTxn.setProcessedTime(new Timestamp(System.currentTimeMillis()));
        accountTransactionsDao.createOrUpdate(accTxn);
    }

    log.info("Exiting :: AsyncServiceImpl :: updateAccountCCTransactions method ");
}
  
	private void validateForMerchantFeeCredit(Timestamp currentTime, PGAccountTransactions accTxn,
			Long totalFeeAmount) {
		PGAccount account;
		account = accountDao.getPgAccount(merchantDao.getParentMerchantCode(accTxn.getMerchantCode()));
		if (null == account) {
			account = accountDao.getPgAccount(accTxn.getMerchantCode());
		}
		setPGAccountDetails(currentTime, accTxn, account, totalFeeAmount);
	}

	private void setPGAccDetails(PGAccount account, Timestamp currentTime, PGAccountTransactions accTxn) {
		accountDao.savePGAccount(account);
		accTxn.setCurrentBalance(account.getCurrentBalance());
		accTxn.setProcessedTime(currentTime);
		accTxn.setStatus(PGConstants.PG_SETTLEMENT_EXECUTED);
	}

	private void validateForEntityTypeAndCurrencyAndStatus(Timestamp currentTime, PGAccountTransactions accTxn,
			PGTransaction transaction, Long totalFeeAmount) {
		PGAccount account;
		PGCurrencyConfig currencyConfig = currencyConfigDao.getcurrencyCodeAlpha(transaction.getTxnCurrencyCode());
		account = accountRepository.findByEntityTypeAndCurrencyAndStatus(PGConstants.DEFAULT_ENTITY_TYPE,
				currencyConfig.getCurrencyCodeAlpha(), PGConstants.S_STATUS_ACTIVE);

		setPGAccountDetails(currentTime, accTxn, account, totalFeeAmount);
	}

	private void setPGAccountDetails(Timestamp currentTime, PGAccountTransactions accTxn, PGAccount account,
			Long totalFeeAmount) {
		account.setAvailableBalance(account.getAvailableBalance() + (accTxn.getCredit() - totalFeeAmount));
		account.setCurrentBalance(account.getCurrentBalance() + (accTxn.getCredit() - totalFeeAmount));
		account.setFeeBalance(totalFeeAmount);
		setPGAccDetails(account, currentTime, accTxn);
	}
}
