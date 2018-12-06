package com.chatak.switches.services;

import java.sql.Timestamp;
import java.text.MessageFormat;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.chatak.pg.acq.dao.AccountDao;
import com.chatak.pg.acq.dao.AccountTransactionsDao;
import com.chatak.pg.acq.dao.CurrencyConfigDao;
import com.chatak.pg.acq.dao.FeeProgramDao;
import com.chatak.pg.acq.dao.IsoServiceDao;
import com.chatak.pg.acq.dao.MerchantDao;
import com.chatak.pg.acq.dao.ProgramManagerDao;
import com.chatak.pg.acq.dao.TransactionDao;
import com.chatak.pg.acq.dao.model.IsoAccount;
import com.chatak.pg.acq.dao.model.PGAccount;
import com.chatak.pg.acq.dao.model.PGAccountTransactions;
import com.chatak.pg.acq.dao.model.PGAcquirerFeeValue;
import com.chatak.pg.acq.dao.model.PGCurrencyConfig;
import com.chatak.pg.acq.dao.model.PGFeeProgram;
import com.chatak.pg.acq.dao.model.PGTransaction;
import com.chatak.pg.acq.dao.model.ProgramManagerAccount;
import com.chatak.pg.acq.dao.repository.AccountRepository;
import com.chatak.pg.acq.dao.repository.TransactionRepository;
import com.chatak.pg.bean.Request;
import com.chatak.pg.constants.AccountTransactionCode;
import com.chatak.pg.constants.PGConstants;
import com.chatak.pg.dao.util.StringUtil;
import com.chatak.pg.enums.AccountType;
import com.chatak.pg.model.ProcessingFee;
import com.chatak.pg.util.CommonUtil;
import com.chatak.pg.util.Constants;
import com.chatak.pg.util.PGUtils;
import com.chatak.pg.util.Properties;
import com.chatak.pg.util.StringUtils;

/**
 * << Service to manage account transactions >>
 *
 * @author Girmiti Software
 * @date Mar 6, 2016 12:37:16 AM
 * @version 1.0
 */
public abstract class AccountTransactionService {
  @Autowired
  protected TransactionDao transactionDao;

  @Autowired
  protected AccountDao accountDao;

  @Autowired
  protected AccountTransactionsDao accountTransactionsDao;

  @Autowired
  protected MerchantDao merchantDao;

  @Autowired
  protected FeeProgramDao feeProgramDao;
  
  @Autowired		
  private AccountRepository accountRepository;
  
  @Autowired
  private CurrencyConfigDao currencyConfigDao;
  
  @Autowired
  private TransactionRepository transactionRepository;
  
  @Autowired
  private ProgramManagerDao programManagerDao;
  
  @Autowired
  private IsoServiceDao isoServiceDao;

  protected Timestamp timestamp = new Timestamp(System.currentTimeMillis());

  protected static Logger logger = Logger.getLogger(AccountTransactionService.class);

  protected PGAccount logAccountTransaction(PGTransaction pgTransaction, Request request) {
    logger.info("Entering:: AccountTransactionService:: logAccountTransaction method  with TxnType : " + pgTransaction.getTransactionType());
    PGAccount pgAccount = null;
    switch(pgTransaction.getTransactionType()) {
      case PGConstants.TXN_TYPE_SALE:
    	  pgAccount = logSaleToAccountTransaction(pgTransaction, request);
        break;
      case PGConstants.TXN_TYPE_VOID:
    	  pgAccount = logVoidToAccountTransaction(pgTransaction);
        break;
      case PGConstants.TXN_TYPE_REFUND:
    	  logRefundToAccountTransaction(pgTransaction);
        break;
      case PGConstants.TXN_TYPE_AUTH:
        break;
      default:
        break;
    }
    return pgAccount;
  }

  private PGAccount logSaleToAccountTransaction(PGTransaction pgTransaction,  Request request) {
    logger.info("Entering:: AccountTransactionService:: logSaleToAccountTransaction method ");
    String accountTxnId = accountTransactionsDao.generateAccountTransactionId();
    PGAccount account = accountDao.getPgAccount(pgTransaction.getMerchantId());
    // Required for future implementation
    /*List<Object> objectResult = getProcessingFee(PGUtils.getCCType(),
                                                 pgTransaction.getMerchantId(),
                                                 pgTransaction.getTxnTotalAmount());
    Long chatakFeeAmountTotal = (Long) objectResult.get(1);
    Long merchantFeeAmount = 0l;

    Long totalFeeAmount = pgTransaction.getTxnTotalAmount() - pgTransaction.getTxnAmount();

    if(totalFeeAmount > chatakFeeAmountTotal) {
      merchantFeeAmount = totalFeeAmount - chatakFeeAmountTotal;
    }
    else {
      chatakFeeAmountTotal = totalFeeAmount;
    }*/

    String descriptionTemplate = Properties.getProperty("chatak-pay.account.sale.description.template");
    descriptionTemplate = MessageFormat.format(descriptionTemplate,
                                               pgTransaction.getCardHolderName(),
                                               pgTransaction.getIssuerTxnRefNum(),
                                               null != pgTransaction.getCardHolderEmail() ? pgTransaction.getCardHolderEmail() : " ",
                                               null != pgTransaction.getReason() ? pgTransaction.getReason() : " ",
                                               StringUtils.amountToString(pgTransaction.getTxnTotalAmount()));
    PGAccountTransactions pgAccountTransactions = new PGAccountTransactions();
    pgAccountTransactions.setTransactionTime(pgTransaction.getCreatedDate());
    pgAccountTransactions.setPgTransactionId(pgTransaction.getTransactionId());
    pgAccountTransactions.setTransactionType(pgTransaction.getTransactionType());
    pgAccountTransactions.setAccountTransactionId(accountTxnId);
    pgAccountTransactions.setTransactionCode(AccountTransactionCode.CC_AMOUNT_CREDIT);
    pgAccountTransactions.setStatus(PGConstants.PG_SETTLEMENT_PENDING);
    pgAccountTransactions.setCredit(pgTransaction.getTxnTotalAmount());
    pgAccountTransactions.setMerchantCode(pgTransaction.getMerchantId());
    pgAccountTransactions.setCreatedDate(timestamp);
    pgAccountTransactions.setAccountNumber(account.getAccountNum().toString());
    pgAccountTransactions.setDescription(descriptionTemplate);
    pgAccountTransactions.setRefundableAmount(pgTransaction.getTxnTotalAmount());
    pgAccountTransactions.setTxnCurrencyCode(pgTransaction.getTxnCurrencyCode());
    pgAccountTransactions.setTimeZoneOffset(pgTransaction.getTimeZoneOffset());
    pgAccountTransactions.setTimeZoneRegion(pgTransaction.getTimeZoneRegion());
    pgAccountTransactions.setDeviceLocalTxnTime(pgTransaction.getDeviceLocalTxnTime());
    pgAccountTransactions.setEntityType(PGConstants.MERCHANT);
    pgAccountTransactions.setEntityId(Long.valueOf(request.getMerchantId()));
    // Step-1 : Initially logging total amount into account transactions
    pgAccountTransactions = accountTransactionsDao.createOrUpdate(pgAccountTransactions);

    // Step-2 : Debting total fee amount from Step-1
    // Commenting the below fee credit since it is being charged to PM and ISO as per the acquiring hierarchy
//    descriptionTemplate = Properties.getProperty("chatak-pay.account.fee.description.template");
//    descriptionTemplate = MessageFormat.format(descriptionTemplate,
//                                               StringUtils.amountToString(chatakFeeAmountTotal),
//                                               StringUtils.amountToString(merchantFeeAmount));
//    
//    logFeeAmount(pgAccountTransactions, totalFeeAmount, AccountTransactionCode.CC_FEE_DEBIT, descriptionTemplate);
    
    // Step-3 : Crediting Merchant Fee
    // Commenting the below since this will be required for different merchant fees like settlement fee, chargeback fee etc,
    // This to be taken in phase 2
//    descriptionTemplate = "Merchant Fee: " + StringUtils.amountToString(merchantFeeAmount);
//    logFeeAmount(pgAccountTransactions,
//                 merchantFeeAmount,
//                 AccountTransactionCode.CC_MERCHANT_FEE_CREDIT,
//                 descriptionTemplate);
    
    // Step-4 : Crediting Chatak system Fee
    // Commenting the below fee credit since it is being charged to PM and ISO as per the acquiring hierarchy
//    descriptionTemplate = "Processing Fee: " + StringUtils.amountToString(chatakFeeAmountTotal);
//    logFeeAmount(pgAccountTransactions,
//                 totalFeeAmount,
//                 AccountTransactionCode.CC_ACQUIRER_FEE_CREDIT,
//                 descriptionTemplate);

    List<PGFeeProgram> feeProgram = feeProgramDao.findByCardProgramId(pgTransaction.getCpId());
    Double pmShare = feeProgram.get(0).getPmShare();
    Double isoShare = feeProgram.get(0).getIsoShare();
    Long pmFee = PGUtils.calculateAmountByPercentage((pgTransaction.getFeeAmount() / Double.parseDouble("100")), pmShare);
    Long isoFee = PGUtils.calculateAmountByPercentage((pgTransaction.getFeeAmount() / Double.parseDouble("100")), isoShare);
    if(request.getPmId() != null){
      ProgramManagerAccount programManagerAccount = programManagerDao.findByProgramManagerIdAndAccountType(request.getPmId(), Constants.REVENUE_ACCOUNT);
      // Crediting PM fee
      descriptionTemplate = "PM Fee: " + StringUtils.amountToString(pmFee);
      pgAccountTransactions.setAccountNumber(String.valueOf(programManagerAccount.getAccountNumber()));
      pgAccountTransactions.setEntityType(Constants.PM_USER_TYPE);
      pgAccountTransactions.setEntityId(request.getPmId());
      logFeeAmount(pgAccountTransactions,
          pmFee,
          AccountTransactionCode.CC_PM_FEE_CREDIT,
          descriptionTemplate);  
    }
    if(request.getIsoId() != null){
    	List<IsoAccount> isoAccount = isoServiceDao.findByIsoIdAndAccountType(request.getIsoId(), AccountType.REVENUE_ACCOUNT.name());
      // Crediting ISO fee
      descriptionTemplate = "ISO Fee: " + StringUtils.amountToString(isoFee);
      pgAccountTransactions.setAccountNumber(String.valueOf(isoAccount.get(0).getAccountNumber()));
      pgAccountTransactions.setEntityType(Constants.ISO_USER_TYPE);
      pgAccountTransactions.setEntityId(request.getIsoId());
      logFeeAmount(pgAccountTransactions,
          isoFee,
          AccountTransactionCode.CC_ISO_FEE_CREDIT,
          descriptionTemplate);      
    }
    
    logger.info("Exiting:: AccountTransactionService:: logSaleToAccountTransaction method ");
    return account;
  }

  private PGAccount logVoidToAccountTransaction(PGTransaction pgTransaction) {
    logger.info("Entering:: AccountTransactionService:: logVoidAccountTransaction method ");
    List<PGAccountTransactions> saleTxnList = accountTransactionsDao.getAccountTransactionsOnTransactionId(pgTransaction.getRefTransactionId());
    PGAccountTransactions voidTxn = null;
    String txnId = accountTransactionsDao.generateAccountTransactionId();
    String descriptionTemplate = null;
    PGAccount account = accountDao.getPgAccount(pgTransaction.getMerchantId());
    for(PGAccountTransactions pgAccTxn : saleTxnList) {
      pgAccTxn.setDeviceLocalTxnTime(pgTransaction.getDeviceLocalTxnTime());
      pgAccTxn.setTimeZoneOffset(pgTransaction.getTimeZoneOffset());
      pgAccTxn.setTimeZoneRegion(pgTransaction.getTimeZoneRegion());
      pgAccTxn.setStatus(PGConstants.PG_TXN_VOIDED);
      pgAccTxn.setUpdatedTime(timestamp);
      voidTxn = new PGAccountTransactions();
      voidTxn.setAccountTransactionId(txnId);
      voidTxn.setAccountNumber(pgAccTxn.getAccountNumber());
      voidTxn.setPgTransactionId(pgTransaction.getTransactionId());
      voidTxn.setTransactionTime(pgTransaction.getCreatedDate());
      voidTxn.setProcessedTime(timestamp);
      voidTxn.setCurrentBalance(account.getCurrentBalance());
      voidTxn.setMerchantCode(pgTransaction.getMerchantId());
      voidTxn.setTransactionType(pgTransaction.getTransactionType());
      voidTxn.setCreatedDate(timestamp);
      switch(pgAccTxn.getTransactionCode()) {
        case AccountTransactionCode.CC_AMOUNT_CREDIT:
          voidTxn.setDebit(0l);
          descriptionTemplate = setMessageFormat(pgTransaction, pgAccTxn);
          voidTxn.setTransactionCode(AccountTransactionCode.CC_AMOUNT_DEBIT);
          voidTxn.setRefundableAmount(pgAccTxn.getRefundableAmount());
          break;
        case AccountTransactionCode.CC_FEE_DEBIT:
          descriptionTemplate = pgAccTxn.getDescription();
          voidTxn.setCredit(pgAccTxn.getDebit());
          voidTxn.setTransactionCode(AccountTransactionCode.CC_FEE_CREDIT);
          break;
        case AccountTransactionCode.CC_MERCHANT_FEE_CREDIT:
          voidTxn.setDebit(pgAccTxn.getCredit());
          descriptionTemplate = pgAccTxn.getDescription();
          voidTxn.setTransactionCode(AccountTransactionCode.CC_MERCHANT_FEE_DEBIT);
          break;
        case AccountTransactionCode.CC_ACQUIRER_FEE_CREDIT:
          voidTxn.setDebit(pgAccTxn.getCredit());
          descriptionTemplate = pgAccTxn.getDescription();
          voidTxn.setTransactionCode(AccountTransactionCode.CC_ACQUIRER_FEE_DEBIT);
          break;
        default:
      }
      voidTxn.setStatus(PGConstants.PG_SETTLEMENT_EXECUTED);
      voidTxn.setDescription(descriptionTemplate);
      voidTxn.setDebit(pgTransaction.getTxnTotalAmount());
      voidTxn.setTimeZoneOffset(pgAccTxn.getTimeZoneOffset());
      voidTxn.setTimeZoneRegion(pgAccTxn.getTimeZoneRegion());
      voidTxn.setDeviceLocalTxnTime(pgAccTxn.getDeviceLocalTxnTime());
      accountTransactionsDao.createOrUpdate(voidTxn, pgAccTxn);
      logger.info("Exiting:: AccountTransactionService:: logVoidAccountTransaction method ");
    }
    return account;
  }

  private String setMessageFormat(PGTransaction pgTransaction, PGAccountTransactions pgAccTxn) {
    String descriptionTemplate;
    descriptionTemplate = Properties.getProperty("chatak-pay.account.void.description.template");
    descriptionTemplate =
        MessageFormat.format(descriptionTemplate, pgAccTxn.getAccountTransactionId(),
            pgTransaction.getCardHolderName(), pgTransaction.getIssuerTxnRefNum(),
            null != pgTransaction.getCardHolderEmail() ? pgTransaction.getCardHolderEmail() : " ",
            null != pgTransaction.getReason() ? pgTransaction.getReason() : " ", "0.00");
    return descriptionTemplate;
  }

  private void logRefundToAccountTransaction(PGTransaction pgTransaction) {
    logger.info("Entering:: AccountTransactionService:: logRefundAccountTransaction method ");
    List<PGAccountTransactions> saleTxnList = accountTransactionsDao.getAccountTransactionsOnTransactionId(pgTransaction.getRefTransactionId());
    PGAccountTransactions refundTxn = null;
    String txnId = accountTransactionsDao.generateAccountTransactionId();
    String descriptionTemplate = null;
    String refundType=" ";
    PGAccount account = null;
    timestamp = new Timestamp(System.currentTimeMillis());
    for(PGAccountTransactions pgAccTxn : saleTxnList) {
      pgAccTxn.setDeviceLocalTxnTime(pgTransaction.getDeviceLocalTxnTime());
      pgAccTxn.setTimeZoneOffset(pgTransaction.getTimeZoneOffset());
      pgAccTxn.setTimeZoneRegion(pgTransaction.getTimeZoneRegion());
      pgAccTxn.setStatus(PGConstants.PG_TXN_REFUNDED);
      pgAccTxn.setUpdatedTime(timestamp);
      refundTxn = new PGAccountTransactions();
      refundTxn.setAccountTransactionId(txnId);
      refundTxn.setAccountNumber(pgAccTxn.getAccountNumber());
      refundTxn.setPgTransactionId(pgTransaction.getTransactionId());
      refundTxn.setTransactionTime(pgTransaction.getCreatedDate());
      refundTxn.setProcessedTime(timestamp);
      refundTxn.setMerchantCode(pgTransaction.getMerchantId());
      refundTxn.setTransactionType(pgTransaction.getTransactionType());
      switch(pgAccTxn.getTransactionCode()) {
        case AccountTransactionCode.CC_AMOUNT_CREDIT:
          refundTxn.setDebit(pgTransaction.getTxnTotalAmount());
          refundType = validateTxnTotalAmount(pgTransaction, pgAccTxn);
          descriptionTemplate = validateForMessageFormat(pgTransaction, refundType, pgAccTxn);
          // updating pg account debting refund amount
          account = setPGAccountTransactionsAndPGAccountForAmountDebit(pgTransaction, refundTxn, pgAccTxn);
          break;
        case AccountTransactionCode.CC_FEE_DEBIT:
          if("".equals(refundType.trim())){
          account=accountDao.getPgAccount(pgAccTxn.getMerchantCode());
          descriptionTemplate = setPGAccountForFeeDebit(refundTxn, account, pgAccTxn);
          }
          break;
        case AccountTransactionCode.CC_MERCHANT_FEE_CREDIT:
          if("".equals(refundType.trim())){
            descriptionTemplate = validateForMerchantFeeCredit(refundTxn, account, pgAccTxn);
          }
          break;
        case AccountTransactionCode.CC_ACQUIRER_FEE_CREDIT:
          if("".equals(refundType.trim())){
          account = validationForAcquirerFeeCredit(refundTxn, pgAccTxn);
          descriptionTemplate = pgAccTxn.getDescription();
          refundTxn.setTransactionCode(AccountTransactionCode.CC_ACQUIRER_FEE_DEBIT); }
          break;
        default:
      }
      refundTypeValidation(refundTxn, descriptionTemplate, refundType, pgAccTxn);
      accountTransactionsDao.createOrUpdate(pgAccTxn);
      logger.info("Exiting:: AccountTransactionService:: logRefundAccountTransaction method ");
    }
  }

  private void refundTypeValidation(PGAccountTransactions refundTxn, String descriptionTemplate,
      String refundType, PGAccountTransactions pgAccTxn) {
    if("".equals(refundType.trim())||((PGConstants.PARTIAL.equals(refundType))&&AccountTransactionCode.CC_AMOUNT_CREDIT.equals(pgAccTxn.getTransactionCode()))){
      refundTxn.setDescription(descriptionTemplate);
      refundTxn.setCreatedDate(timestamp);
      refundTxn.setStatus(PGConstants.PG_SETTLEMENT_EXECUTED);
      refundTxn.setTimeZoneOffset(pgAccTxn.getTimeZoneOffset());
      refundTxn.setTimeZoneRegion(pgAccTxn.getTimeZoneRegion());
      refundTxn.setDeviceLocalTxnTime(pgAccTxn.getDeviceLocalTxnTime());
      accountTransactionsDao.createOrUpdate(refundTxn);
    }
  }

  private String validateForMerchantFeeCredit(PGAccountTransactions refundTxn, PGAccount account,
      PGAccountTransactions pgAccTxn) {
    String descriptionTemplate;
    String parentMerchantCode = merchantDao.getParentMerchantCode(pgAccTxn.getMerchantCode());
    validateParentMerchantCodeAndAccount(refundTxn, account, pgAccTxn, parentMerchantCode);
     refundTxn.setDebit(pgAccTxn.getCredit());
     descriptionTemplate = pgAccTxn.getDescription();
     if(account != null){
    refundTxn.setCurrentBalance(account.getCurrentBalance());
     }
     refundTxn.setTransactionCode(AccountTransactionCode.CC_MERCHANT_FEE_DEBIT);
    return descriptionTemplate;
  }

  private PGAccount setPGAccountTransactionsAndPGAccountForAmountDebit(PGTransaction pgTransaction,
      PGAccountTransactions refundTxn, PGAccountTransactions pgAccTxn) {
    PGAccount account;
    account=accountDao.getPgAccount(pgAccTxn.getMerchantCode());
    account.setAvailableBalance(account.getAvailableBalance() - pgTransaction.getTxnAmount());
    account.setCurrentBalance(account.getCurrentBalance() - pgTransaction.getTxnAmount());
    accountDao.savePGAccount(account);
    refundTxn.setCurrentBalance(account.getCurrentBalance());
    refundTxn.setTransactionCode(AccountTransactionCode.CC_AMOUNT_DEBIT);
    return account;
  }

  private String validateForMessageFormat(PGTransaction pgTransaction, String refundType,
      PGAccountTransactions pgAccTxn) {
    String descriptionTemplate;
    descriptionTemplate = Properties.getProperty("chatak-pay.account.refund.description.template");
    descriptionTemplate = MessageFormat.format(descriptionTemplate,
                                               pgAccTxn.getAccountTransactionId(),
                                               StringUtil.isNullAndEmpty(pgTransaction.getCardHolderName())? " ": pgTransaction.getCardHolderName(),
                                               pgTransaction.getIssuerTxnRefNum(),
                                               null != pgTransaction.getReason() ? pgTransaction.getReason() : " ",
                                               StringUtils.amountToString(pgTransaction.getTxnTotalAmount()),
                                               refundType);
    return descriptionTemplate;
  }

  private String validateTxnTotalAmount(PGTransaction pgTransaction,
      PGAccountTransactions pgAccTxn) {
    String refundType = " ";
    if(pgTransaction.getTxnTotalAmount() != null && pgTransaction.getTxnTotalAmount().longValue() < pgAccTxn.getCredit().longValue()) {
      refundType = PGConstants.PARTIAL;
    }
    return refundType;
  }

  private PGAccount validationForAcquirerFeeCredit(PGAccountTransactions refundTxn,
      PGAccountTransactions pgAccTxn) {
    PGAccount account;
    
   	logger.info("AccountTransactionService:: logRefundAccountTransaction method fetching transactions by PG TRANS ID: " + pgAccTxn.getPgTransactionId());
   	PGTransaction transaction = transactionRepository.findByTransactionId(pgAccTxn.getPgTransactionId());
    
   	logger.info("AccountTransactionService:: logRefundAccountTransaction method :: fetching currencyConfig for fee credit with numeric code: " + transaction.getTxnCurrencyCode());
 		PGCurrencyConfig currencyConfig = currencyConfigDao.getcurrencyCodeAlpha(transaction.getTxnCurrencyCode());
 		logger.info("AccountTransactionService:: logRefundAccountTransaction method :: currency code alpha for the above: " + currencyConfig.getCurrencyCodeAlpha());
 		account = accountRepository.findByEntityTypeAndCurrencyAndStatus(PGConstants.DEFAULT_ENTITY_TYPE, currencyConfig.getCurrencyCodeAlpha(), PGConstants.S_STATUS_ACTIVE);
    
    account.setAvailableBalance(account.getAvailableBalance() - pgAccTxn.getCredit());
    account.setCurrentBalance(account.getCurrentBalance() - pgAccTxn.getCredit());
    accountDao.savePGAccount(account);
    refundTxn.setCurrentBalance(account.getCurrentBalance());
     refundTxn.setDebit(pgAccTxn.getCredit());
    return account;
  }

  private String setPGAccountForFeeDebit(PGAccountTransactions refundTxn, PGAccount account,
      PGAccountTransactions pgAccTxn) {
    String descriptionTemplate;
    account.setAvailableBalance(account.getAvailableBalance());
    account.setCurrentBalance(account.getCurrentBalance());
    accountDao.savePGAccount(account);
    refundTxn.setCurrentBalance(account.getCurrentBalance());
    descriptionTemplate = pgAccTxn.getDescription();
    setPGAccountTransactionsData(refundTxn, account, pgAccTxn);
    return descriptionTemplate;
  }
  private PGAccountTransactions populateAccountTransactions(PGAccountTransactions accountTransaction) {
    logger.info("Entering:: AccountTransactionService:: populateAccountTransactions method ");
    PGAccountTransactions pgAccountTransactions = new PGAccountTransactions();
    pgAccountTransactions.setAccountTransactionId(accountTransaction.getAccountTransactionId());
    pgAccountTransactions.setCreatedDate(timestamp);
    pgAccountTransactions.setMerchantCode(accountTransaction.getMerchantCode());
    pgAccountTransactions.setPgTransactionId(accountTransaction.getPgTransactionId());
    pgAccountTransactions.setStatus(accountTransaction.getStatus());
    pgAccountTransactions.setTransactionTime(accountTransaction.getTransactionTime());
    pgAccountTransactions.setTransactionType(accountTransaction.getTransactionType());
    pgAccountTransactions.setTimeZoneOffset(accountTransaction.getTimeZoneOffset());
    pgAccountTransactions.setTimeZoneRegion(accountTransaction.getTimeZoneRegion());
    pgAccountTransactions.setDeviceLocalTxnTime(accountTransaction.getDeviceLocalTxnTime());
    pgAccountTransactions.setEntityType(accountTransaction.getEntityType());
    pgAccountTransactions.setEntityId(accountTransaction.getEntityId());
    logger.info("Exiting:: AccountTransactionService:: populateAccountTransactions method ");
    return pgAccountTransactions;
  }

  private void logFeeAmount(PGAccountTransactions pgAccountTransactions,
                           Long feeAmount,
                           String transactionCode,
                           String descrilption) {
    logger.info("Entering:: AccountTransactionService:: logFeeAmount method ");
    PGAccount account = null;
    PGAccountTransactions feeTransactionLog = populateAccountTransactions(pgAccountTransactions);
    feeTransactionLog.setTransactionCode(transactionCode);
    feeTransactionLog.setDescription(descrilption);
    switch(transactionCode) {
      case AccountTransactionCode.CC_FEE_DEBIT:
        feeTransactionLog.setDebit(feeAmount);
        feeTransactionLog.setRefundableAmount(feeAmount);
        feeTransactionLog.setAccountNumber(pgAccountTransactions.getAccountNumber());
        break;
      case AccountTransactionCode.CC_MERCHANT_FEE_CREDIT:
        feeTransactionLog.setCredit(0l);
        feeTransactionLog.setRefundableAmount(0l);
        String parentMerchantCode = merchantDao.getParentMerchantCode(pgAccountTransactions.getMerchantCode());
        validateParentMerchantCodeAndPGAccount(pgAccountTransactions, account, feeTransactionLog, parentMerchantCode);
        break;
      case AccountTransactionCode.CC_ACQUIRER_FEE_CREDIT:
        feeTransactionLog.setCredit(feeAmount);
        feeTransactionLog.setRefundableAmount(feeAmount);
        //account = accountDao.getPgAccount("1");// 1 For Rapid account
        
        validateForAcquirerFeeCredit(pgAccountTransactions, feeTransactionLog);
        break;
      case AccountTransactionCode.CC_PM_FEE_CREDIT:
        setFee(feeTransactionLog, feeAmount,pgAccountTransactions);
        break;
      case AccountTransactionCode.CC_ISO_FEE_CREDIT:
        setFee(feeTransactionLog, feeAmount,pgAccountTransactions);
        break;
      default:
        break;
    }
    accountTransactionsDao.createOrUpdate(feeTransactionLog);
    logger.info("Exiting:: AccountTransactionService:: logFeeAmount method ");
  }

  private void setFee(PGAccountTransactions feeTransactionLog, Long feeAmount,PGAccountTransactions pgAccountTransactions) {
    feeTransactionLog.setCredit(feeAmount);
    feeTransactionLog.setRefundableAmount(feeAmount);
    feeTransactionLog.setAccountNumber(pgAccountTransactions.getAccountNumber());
    feeTransactionLog.setEntityType(pgAccountTransactions.getEntityType());
    feeTransactionLog.setEntityId(pgAccountTransactions.getEntityId());
  }
  
  private void validateForAcquirerFeeCredit(PGAccountTransactions pgAccountTransactions,
      PGAccountTransactions feeTransactionLog) {
    PGAccount account;
    logger.info("AccountTransactionService:: logFeeAmount method fetching transactions by PG TRANS ID: " + pgAccountTransactions.getPgTransactionId());
    PGTransaction transaction = transactionRepository.findByTransactionId(pgAccountTransactions.getPgTransactionId());
    
    logger.info("AccountTransactionService:: logFeeAmount method :: fetching currencyConfig for fee credit with numeric code: " + transaction.getTxnCurrencyCode());
    PGCurrencyConfig currencyConfig = currencyConfigDao.getcurrencyCodeAlpha(transaction.getTxnCurrencyCode());
    logger.info("AccountTransactionService:: logFeeAmount method :: currency code alpha for the above: " + currencyConfig.getCurrencyCodeAlpha());
    account = accountRepository.findByEntityTypeAndCurrencyAndStatus(PGConstants.DEFAULT_ENTITY_TYPE, currencyConfig.getCurrencyCodeAlpha(), PGConstants.S_STATUS_ACTIVE);
    feeTransactionLog.setAccountNumber(account.getAccountNum().toString());
  }

  private void validateParentMerchantCodeAndPGAccount(PGAccountTransactions pgAccountTransactions,
      PGAccount account, PGAccountTransactions feeTransactionLog, String parentMerchantCode) {
    if(null != parentMerchantCode) {
      account = accountDao.getPgAccount(parentMerchantCode);
      feeTransactionLog.setMerchantCode(parentMerchantCode);
    }
    if(null != account) {
      feeTransactionLog.setAccountNumber(account.getAccountNum().toString());
    }
    else {
      feeTransactionLog.setAccountNumber(pgAccountTransactions.getAccountNumber());
    }
  }

  //Required for future implementation
  /*private List<Object> getProcessingFee(String cardType, String merchantCode, Long txnTotalAmount) {
    logger.info("Entering:: AccountTransactionService:: getProcessingFee method ");
    List<Object> results = new ArrayList<Object>(Integer.parseInt("2"));
    List<ProcessingFee> calculatedProcessingFeeList = new ArrayList<ProcessingFee>(0);
    Long chatakFeeAmountTotal = 0l;
    List<PGAcquirerFeeValue> acquirerFeeValueList = feeProgramDao.getAcquirerFeeValueByMerchantIdAndCardType(merchantCode,cardType);
    if(CommonUtil.isListNotNullAndEmpty(acquirerFeeValueList)) {

      logger.info(" AccountTransactionService:: getProcessingFee method :: Applying this merchant fee code ");
      fetchPGAcquirerFeeValue(txnTotalAmount, calculatedProcessingFeeList, chatakFeeAmountTotal,
			acquirerFeeValueList);
    }
    else {
      String parentMerchantCode = merchantDao.getParentMerchantCode(merchantCode);
      if(null != parentMerchantCode) {
        acquirerFeeValueList = feeProgramDao.getAcquirerFeeValueByMerchantIdAndCardType(parentMerchantCode,cardType);
        if(CommonUtil.isListNotNullAndEmpty(acquirerFeeValueList)) {
          logger.info("Exiting:: AccountTransactionService:: getProcessingFee method :: Applying parentMerchantCode fee ");
          fetchPGAcquirerFeeValue(txnTotalAmount, calculatedProcessingFeeList,
				chatakFeeAmountTotal, acquirerFeeValueList);
        }
      }
    }
    logger.info("Exiting:: AccountTransactionService:: getProcessingFee method ");
    results.add(calculatedProcessingFeeList);
    results.add(chatakFeeAmountTotal);
    return results;
  }

private void fetchPGAcquirerFeeValue(Long txnTotalAmount, List<ProcessingFee> calculatedProcessingFeeList,
		Long chatakFeeAmountTotal, List<PGAcquirerFeeValue> acquirerFeeValueList) {
	Double calculatedProcessingFee;
	for(PGAcquirerFeeValue acquirerFeeValue : acquirerFeeValueList) {
        calculatedProcessingFee = 0.00;
        ProcessingFee processingFee = getProcessingFeeItem(acquirerFeeValue, txnTotalAmount, calculatedProcessingFee);
        chatakFeeAmountTotal = chatakFeeAmountTotal + CommonUtil.getLongAmount(processingFee.getChatakProcessingFee());
        calculatedProcessingFeeList.add(processingFee);
      }
}

  private ProcessingFee getProcessingFeeItem(PGAcquirerFeeValue acquirerFeeValue,
                                             Long txnTotalAmount,
                                             Double calculatedProcessingFee) {
    logger.info("Entering:: AccountTransactionService:: getProcessingFeeItem method ");
    Double flatFee = CommonUtil.getDoubleAmountNotNull(acquirerFeeValue.getFlatFee());
    Double percentageFee = acquirerFeeValue.getFeePercentageOnly();
    percentageFee = txnTotalAmount * (CommonUtil.getDoubleAmountNotNull(percentageFee));
    calculatedProcessingFee = (CommonUtil.getDoubleAmountNotNull(calculatedProcessingFee + percentageFee)) + flatFee;
    ProcessingFee processingFee = new ProcessingFee();
    processingFee.setAccountNumber(acquirerFeeValue.getAccountNumber());
    processingFee.setChatakProcessingFee(calculatedProcessingFee);
    logger.info("Exiting:: AccountTransactionService:: getProcessingFeeItem method ");
    return processingFee;
  }*/
  protected void logPartialRefundToAccountTransaction(PGTransaction pgTransaction) {
	    logger.info("Entering:: AccountTransactionService:: logPartialRefundToAccountTransaction method ");
	    List<PGAccountTransactions> saleTxnList = accountTransactionsDao.getAccountTransactionsOnTransactionId(pgTransaction.getRefTransactionId());
	    PGAccountTransactions refundTxn = null;
	    String txnId = accountTransactionsDao.generateAccountTransactionId();
	    String descriptionTemplate = null;
	    Long refundAmount=pgTransaction.getTxnTotalAmount();
	    PGAccount account = null;
	    timestamp = new Timestamp(System.currentTimeMillis());
	    for(PGAccountTransactions pgAccTxn : saleTxnList) {
	      pgAccTxn.setDeviceLocalTxnTime(pgTransaction.getDeviceLocalTxnTime());
	      pgAccTxn.setTimeZoneOffset(pgTransaction.getTimeZoneOffset());
	      pgAccTxn.setTimeZoneRegion(pgTransaction.getTimeZoneRegion());
	      pgAccTxn.setStatus(PGConstants.PG_TXN_REFUNDED);
	      pgAccTxn.setUpdatedTime(timestamp);
	      pgAccTxn.setProcessedTime(timestamp);
	      refundTxn = new PGAccountTransactions();
	      refundTxn.setAccountTransactionId(txnId);
	      refundTxn.setAccountNumber(pgAccTxn.getAccountNumber());
	      refundTxn.setPgTransactionId(pgTransaction.getTransactionId());
	      refundTxn.setTransactionTime(pgTransaction.getCreatedDate());
	      refundTxn.setMerchantCode(pgAccTxn.getMerchantCode());
	      refundTxn.setTransactionType(pgTransaction.getTransactionType());
	      refundTxn.setProcessedTime(timestamp);
	      String refundType = " ";
	      switch(pgAccTxn.getTransactionCode()) {
	        case AccountTransactionCode.CC_AMOUNT_CREDIT:
	          if(null!=pgAccTxn.getRefundableAmount()){
	            refundAmount = fetchRefundAmount(pgTransaction, pgAccTxn);
	            
	            descriptionTemplate = validateForMessageFormatAndRefundType(pgTransaction, refundTxn, refundAmount, pgAccTxn, refundType);
	         // updating pg account debting refund amount
	            account = setPGAccountForAmountDebit(pgTransaction, refundTxn, pgAccTxn); }
	         
	          break;
	        case AccountTransactionCode.CC_FEE_DEBIT:
	          
	          if(null!=pgAccTxn.getRefundableAmount()){
	        	  account = setPGAccountData(refundTxn, refundAmount, pgAccTxn);
	              descriptionTemplate ="Refund Fee: " + StringUtils.amountToString(refundTxn.getCredit());
	              setPGAccountTransactionsData(refundTxn, account, pgAccTxn); }
	          
	            break;
	        case AccountTransactionCode.CC_MERCHANT_FEE_CREDIT:
	          if(null!=pgAccTxn.getRefundableAmount()){
	        	  
	        	refundAmountValidation(refundTxn, refundAmount, account, pgAccTxn);
	            descriptionTemplate = "Merchant Fee: " + StringUtils.amountToString(refundTxn.getDebit());
	            refundTxn.setTransactionCode(AccountTransactionCode.CC_MERCHANT_FEE_DEBIT); }
	          break;
	        case AccountTransactionCode.CC_ACQUIRER_FEE_CREDIT:
	          if(null!=pgAccTxn.getRefundableAmount()){
	        	  //account=accountDao.getPgAccount("1");//1 FOr Rapid account
	        	  
	        	account = fetchPGTransactionList(pgAccTxn);
	        	validatePGAccountTransactionsAndRefundAmount(refundTxn, refundAmount, account, pgAccTxn);
	            descriptionTemplate = validationForAcquirerFeeDebit(refundTxn); }
	          break;
	        default:
	      }
	      refundTxn.setProcessedTime(new Timestamp(System.currentTimeMillis()));
	      refundTxn.setDescription(descriptionTemplate);
	      refundTxn.setCreatedDate(timestamp);
	      refundTxn.setStatus(PGConstants.PG_SETTLEMENT_EXECUTED);
	      accountTransactionsDao.createOrUpdate(refundTxn);
	      accountTransactionsDao.createOrUpdate(pgAccTxn);
	      logger.info("Exiting:: AccountTransactionService:: logRefundAccountTransaction method ");
	    }
	  }

  private Long fetchRefundAmount(PGTransaction pgTransaction, PGAccountTransactions pgAccTxn) {
    Long refundAmount;
    refundAmount=(pgTransaction.getTxnTotalAmount()>=pgAccTxn.getRefundableAmount())?(pgTransaction.getTxnTotalAmount()-pgAccTxn.getRefundableAmount()):0L;
    return refundAmount;
  }

  private String validateForMessageFormatAndRefundType(PGTransaction pgTransaction,
      PGAccountTransactions refundTxn, Long refundAmount, PGAccountTransactions pgAccTxn,
      String refundType) {
    String descriptionTemplate;
    pgAccTxn.setRefundableAmount((refundAmount>0)?0L:(pgAccTxn.getRefundableAmount()-pgTransaction.getTxnTotalAmount()));
      refundTxn.setDebit(pgTransaction.getTxnTotalAmount());
      descriptionTemplate = Properties.getProperty("chatak-pay.account.refund.description.template");
      
      descriptionTemplate = MessageFormat.format(descriptionTemplate,
              pgAccTxn.getAccountTransactionId(),
              StringUtil.isNullAndEmpty(pgTransaction.getCardHolderName())? " ": pgTransaction.getCardHolderName(),
              pgTransaction.getIssuerTxnRefNum(),
              null != pgTransaction.getReason() ? pgTransaction.getReason() : " ",
              StringUtils.amountToString(pgTransaction.getTxnTotalAmount()),
              refundType);
    return descriptionTemplate;
  }

  private PGAccount setPGAccountForAmountDebit(PGTransaction pgTransaction, PGAccountTransactions refundTxn,
      PGAccountTransactions pgAccTxn) {
    PGAccount account;
    account=accountDao.getPgAccount(pgAccTxn.getMerchantCode());
    
    account.setAvailableBalance(account.getAvailableBalance() - (pgTransaction.getMerchantFeeAmount()+pgTransaction.getTxnAmount()));
    account.setCurrentBalance(account.getCurrentBalance() - (pgTransaction.getMerchantFeeAmount()+pgTransaction.getTxnAmount()));
    refundTxn.setDebit(pgTransaction.getTxnTotalAmount());
    
    accountDao.savePGAccount(account);
    refundTxn.setCurrentBalance(account.getCurrentBalance());
    refundTxn.setTransactionCode(AccountTransactionCode.CC_AMOUNT_DEBIT);
    return account;
  }

  private String validationForAcquirerFeeDebit(PGAccountTransactions refundTxn) {
    String descriptionTemplate;
    descriptionTemplate = "Processing Fee: " + StringUtils.amountToString(refundTxn.getDebit());
    refundTxn.setTransactionCode(AccountTransactionCode.CC_ACQUIRER_FEE_DEBIT);
    return descriptionTemplate;
  }

  private PGAccount fetchPGTransactionList(PGAccountTransactions pgAccTxn) {
    PGAccount account;
    logger.info("AccountTransactionService:: logPartialRefundToAccountTransaction method fetching transactions by PG TRANS ID: " + pgAccTxn.getPgTransactionId());
    PGTransaction transaction = transactionRepository.findByTransactionId(pgAccTxn.getPgTransactionId());
      
    logger.info("AccountTransactionService:: logPartialRefundToAccountTransaction method :: fetching currencyConfig for fee credit with numeric code: " + transaction.getTxnCurrencyCode());
    PGCurrencyConfig currencyConfig = currencyConfigDao.getcurrencyCodeAlpha(transaction.getTxnCurrencyCode());
    logger.info("AccountTransactionService:: logPartialRefundToAccountTransaction method :: currency code alpha for the above: " + currencyConfig.getCurrencyCodeAlpha());
    account = accountRepository.findByEntityTypeAndCurrencyAndStatus(PGConstants.DEFAULT_ENTITY_TYPE, currencyConfig.getCurrencyCodeAlpha(), PGConstants.S_STATUS_ACTIVE);
    return account;
  }

  private void validatePGAccountTransactionsAndRefundAmount(PGAccountTransactions refundTxn,
      Long refundAmount, PGAccount account, PGAccountTransactions pgAccTxn) {
    Long balance;
    balance=refundAmount;
    refundTxn.setCurrentBalance(account.getCurrentBalance());
    refundAmount=(refundAmount>=pgAccTxn.getRefundableAmount())?(refundAmount-pgAccTxn.getRefundableAmount()):0L;
    refundTxn.setDebit(balance-refundAmount);
    pgAccTxn.setRefundableAmount((refundAmount>0)?0L:(pgAccTxn.getRefundableAmount()-balance));
  }

  private void refundAmountValidation(PGAccountTransactions refundTxn, Long refundAmount,
      PGAccount account, PGAccountTransactions pgAccTxn) {
    Long balance;
    String parentMerchantCode = merchantDao.getParentMerchantCode(pgAccTxn.getMerchantCode());
    validateParentMerchantCodeAndAccount(refundTxn, account, pgAccTxn, parentMerchantCode);
    
    balance=refundAmount;
    refundAmount=(refundAmount>=pgAccTxn.getRefundableAmount())?(refundAmount-pgAccTxn.getRefundableAmount()):0L;
    pgAccTxn.setRefundableAmount((refundAmount>0)?0L:(pgAccTxn.getRefundableAmount()-balance));
    refundTxn.setDebit(balance-refundAmount);
  }

  private void setPGAccountTransactionsData(PGAccountTransactions refundTxn, PGAccount account,
      PGAccountTransactions pgAccTxn) {
    refundTxn.setCurrentBalance(account.getCurrentBalance());
    refundTxn.setCredit(pgAccTxn.getDebit());
    refundTxn.setTransactionCode(AccountTransactionCode.CC_FEE_CREDIT);
  }

  private PGAccount setPGAccountData(PGAccountTransactions refundTxn, Long refundAmount,
      PGAccountTransactions pgAccTxn) {
    PGAccount account;
    refundTxn.setCredit(refundAmount);
    pgAccTxn.setRefundableAmount(pgAccTxn.getRefundableAmount()-refundAmount);
    account=accountDao.getPgAccount(pgAccTxn.getMerchantCode());
    account.setAvailableBalance(account.getAvailableBalance() + pgAccTxn.getDebit());
    account.setCurrentBalance(account.getCurrentBalance() + pgAccTxn.getDebit());
    accountDao.savePGAccount(account);
    refundTxn.setCurrentBalance(account.getCurrentBalance());
    return account;
  }

	private void validateParentMerchantCodeAndAccount(PGAccountTransactions refundTxn, PGAccount account,
			PGAccountTransactions pgAccTxn, String parentMerchantCode) {
		if (null != parentMerchantCode) {
			account = accountDao.getPgAccount(parentMerchantCode);
			refundTxn.setMerchantCode(parentMerchantCode);
		}
		if (null == account) {
			account = accountDao.getPgAccount(pgAccTxn.getMerchantCode());
		}
		account.setAvailableBalance(account.getAvailableBalance() - pgAccTxn.getCredit());
		account.setCurrentBalance(account.getCurrentBalance() - pgAccTxn.getCredit());
		accountDao.savePGAccount(account);
		refundTxn.setCurrentBalance(account.getCurrentBalance());
	}
}
