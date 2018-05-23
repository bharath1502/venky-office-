package com.chatak.merchant.service.impl;

import java.sql.Timestamp;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chatak.merchant.controller.model.Option;
import com.chatak.merchant.exception.ChatakMerchantException;
import com.chatak.merchant.service.FundTransferService;
import com.chatak.pg.acq.dao.AccountDao;
import com.chatak.pg.acq.dao.AccountTransactionsDao;
import com.chatak.pg.acq.dao.MerchantBankDao;
import com.chatak.pg.acq.dao.MerchantDao;
import com.chatak.pg.acq.dao.MerchantUpdateDao;
import com.chatak.pg.acq.dao.PGTransfersDao;
import com.chatak.pg.acq.dao.RefundTransactionDao;
import com.chatak.pg.acq.dao.TransactionDao;
import com.chatak.pg.acq.dao.model.PGAccount;
import com.chatak.pg.acq.dao.model.PGAccountHistory;
import com.chatak.pg.acq.dao.model.PGAccountTransactions;
import com.chatak.pg.acq.dao.model.PGMerchant;
import com.chatak.pg.acq.dao.model.PGMerchantBank;
import com.chatak.pg.acq.dao.model.PGTransfers;
import com.chatak.pg.bean.AccountTransferRequest;
import com.chatak.pg.bean.CheckBeneficiary;
import com.chatak.pg.bean.CreditAccount;
import com.chatak.pg.bean.DebitAccount;
import com.chatak.pg.constants.AccountTransactionCode;
import com.chatak.pg.constants.PGConstants;
import com.chatak.pg.model.AccountBalanceDTO;
import com.chatak.pg.model.EFTDetails;
import com.chatak.pg.model.EFTRefTxnData;
import com.chatak.pg.model.FundTransferDTO;
import com.chatak.pg.model.GetTransactionIdsListResponse;
import com.chatak.pg.model.ReportsDTO;
import com.chatak.pg.user.bean.GetTransferListRequest;
import com.chatak.pg.util.CommonUtil;
import com.chatak.pg.util.Constants;
import com.chatak.pg.util.Properties;
import com.chatak.pg.util.StringUtils;

@Service
public class FundTransferServiceImpl implements FundTransferService {

  private static final Logger logger = Logger.getLogger(FundTransferServiceImpl.class);

  @Autowired
  PGTransfersDao pgTransfersDao;

  @Autowired
  AccountDao accountDao;

  @Autowired
  MerchantBankDao merchantBankDao;

  @Autowired
  MerchantDao merchantDao;

  @Autowired
  TransactionDao transactionDao;

  @Autowired
  AccountTransactionsDao accountTransactionsDao;

  @Autowired
  RefundTransactionDao refundTransactionDao;

  @Autowired
  MerchantUpdateDao merchantUpdateDao;

  @Override
  public FundTransferDTO populateFundTransferDTO(String merchantCode, String fundTransferMode)
      throws ChatakMerchantException {
    FundTransferDTO fundTransferDTO = new FundTransferDTO();
    PGMerchantBank pgMerchantBank = merchantBankDao.getMerchantBankByMerchantId(merchantCode);
    PGAccount pgAccount = accountDao.getPgAccount(merchantCode);
    PGMerchant pgMerchant = merchantUpdateDao.getMerchant(merchantCode);
    CreditAccount creditAccount = new CreditAccount();
    DebitAccount debitAccount = new DebitAccount();
    CheckBeneficiary checkBeneficiary = new CheckBeneficiary();
    if (null != pgAccount && null != pgMerchantBank) {
      fundTransferDTO.setMerchantCode(merchantCode);
      debitAccount.setAccountNumber(pgAccount.getAccountNum());
      debitAccount.setAccountType(pgMerchantBank.getAccountType());
      if ("C".equals(pgMerchantBank.getAccountType()))
        debitAccount.setAccountTypeValue(PGConstants.CHECKING_ACCOUNT);
      else
        debitAccount.setAccountTypeValue(PGConstants.SAVINGS_ACCOUNT);
      debitAccount.setAvaliableBalance(
          CommonUtil.getDoubleAmount(pgAccount.getAvailableBalance()).toString());
      fundTransferDTO.setDebitAccount(debitAccount);
      if (fundTransferMode.equals(PGConstants.FUND_TRANSFER_EFT)) {
        creditAccount.setAccountNumber(pgMerchantBank.getBankAccNum());
        creditAccount.setAccountType(pgMerchantBank.getAccountType());
        if ("C".equals(pgMerchantBank.getAccountType()))
          creditAccount.setAccountTypeValue(PGConstants.CHECKING_ACCOUNT);
        else
          creditAccount.setAccountTypeValue(PGConstants.SAVINGS_ACCOUNT);
        creditAccount.setBankName(pgMerchantBank.getBankName());
        creditAccount.setBankRoutingNumber(pgMerchantBank.getRoutingNumber());
        creditAccount.setCity(pgMerchantBank.getCity());
        creditAccount.setState(pgMerchantBank.getState());
        creditAccount.setAddress(pgMerchantBank.getAddress1());
        creditAccount.setNameOnAcccount(pgMerchantBank.getNameOnAccount());
        fundTransferDTO.setCreditAccount(creditAccount);
      } else if (fundTransferMode.equals(PGConstants.FUND_TRANSFER_CHECK)) {
        checkBeneficiary.setAddress1(pgMerchant.getAddress1());
        checkBeneficiary.setAddress2(pgMerchant.getAddress2());
        checkBeneficiary.setBeneficiaryName(pgMerchant.getFirstName());
        checkBeneficiary.setCity(pgMerchant.getCity());
        checkBeneficiary.setCountry(pgMerchant.getCountry());
        checkBeneficiary.setState(pgMerchant.getState());
        checkBeneficiary.setZip(pgMerchant.getPin());
        fundTransferDTO.setCheckBeneficiary(checkBeneficiary);
      }
      fundTransferDTO.setFundTransferMode(fundTransferMode);
    }
    return fundTransferDTO;
  }

  @Override
  public void processEFTFundsTransfer(FundTransferDTO fundTransferDTO)
      throws ChatakMerchantException {
    if (Double.valueOf(fundTransferDTO.getAmountToTransfer()) <= 0) {
      throw new ChatakMerchantException("Invalid amount");
    }
    if (Double.valueOf(fundTransferDTO.getAmountToTransfer()) > Double
        .valueOf(fundTransferDTO.getDebitAccount().getAvaliableBalance())) {
      throw new ChatakMerchantException("Insufficient account balance");
    }
    logTransfers(fundTransferDTO);
  }

  @Override
  public void processCheckFundsTransfer(FundTransferDTO fundTransferDTO)
      throws ChatakMerchantException {
    if (Double.valueOf(fundTransferDTO.getAmountToTransfer()) <= 0) {
      throw new ChatakMerchantException("Invalid amount");
    }
    if (Double.valueOf(fundTransferDTO.getAmountToTransfer()) > Double
        .valueOf(fundTransferDTO.getDebitAccount().getAvaliableBalance())) {
      throw new ChatakMerchantException("Insufficient account balance");
    }
    logTransfers(fundTransferDTO);
  }

  @Override
  public void logTransfers(FundTransferDTO fundTransferDTO) {
    PGTransfers pgTransfers = new PGTransfers();
    String transactionCode = null;
    pgTransfers.setMerchantId(Long.valueOf(fundTransferDTO.getMerchantCode()));
    pgTransfers.setAccountType(fundTransferDTO.getDebitAccount().getAccountType());
    pgTransfers.setTransferMode(fundTransferDTO.getFundTransferMode());
    pgTransfers
        .setAmount(CommonUtil.getLongAmount(Double.valueOf(fundTransferDTO.getAmountToTransfer())));
    pgTransfers.setCreatedDate(new Timestamp(System.currentTimeMillis()));
    pgTransfers.setFromAccount(fundTransferDTO.getDebitAccount().getAccountNumber().toString());
    pgTransfers.setStatus(PGConstants.PG_SETTLEMENT_PENDING);

    String descriptionTemplate =
        Properties.getProperty("chatak-fund.transfer.description.template");
    descriptionTemplate =
        MessageFormat.format(descriptionTemplate, fundTransferDTO.getFundTransferMode(),
            StringUtils.amountToString(
                CommonUtil.getLongAmount(Double.valueOf(fundTransferDTO.getAmountToTransfer()))),
        StringUtils.amountToString(0L));

    pgTransfers.setTxnDescription(descriptionTemplate);
    if (fundTransferDTO.getFundTransferMode().equals(PGConstants.FUND_TRANSFER_EFT)) {
      pgTransfers.setBankRoutingNumber(fundTransferDTO.getCreditAccount().getBankRoutingNumber());
      pgTransfers.setState(fundTransferDTO.getCreditAccount().getState());
      pgTransfers.setCity(fundTransferDTO.getCreditAccount().getCity());
      pgTransfers.setToAccount(fundTransferDTO.getCreditAccount().getAccountNumber());
      pgTransfers.setNameOnAccount(fundTransferDTO.getCreditAccount().getNameOnAcccount());
      transactionCode = AccountTransactionCode.FT_BANK;
    } else if (fundTransferDTO.getFundTransferMode().equals(PGConstants.FUND_TRANSFER_CHECK)) {
      pgTransfers.setState(fundTransferDTO.getCheckBeneficiary().getState());
      pgTransfers.setCity(fundTransferDTO.getCheckBeneficiary().getState());
      transactionCode = AccountTransactionCode.FT_CHECK;
    }
    pgTransfers.setUpdatedDate(null);
    pgTransfers = pgTransfersDao.createOrUpdateTransferRecord(pgTransfers);
    logEFTAccountTransaction(pgTransfers, transactionCode);
  }

  @Override
  public Map<String, Long> splitReportsAmount(List<ReportsDTO> list) {
    Map<String, Long> splitList = new HashMap<>();
    Long tempAmount = null;
    for (ReportsDTO yo : list) {
      tempAmount = splitList.get(yo.getMerchantCode());
      if (tempAmount == null) {
        tempAmount = Long.valueOf(yo.getAmount());
        splitList.put(yo.getMerchantCode(), tempAmount);
      } else {
        tempAmount = tempAmount + Long.valueOf(yo.getAmount());
        splitList.put(yo.getMerchantCode(), tempAmount);
      }
    }
    return splitList;
  }

  @Override
  public List<EFTDetails> getReportsEFTAmount(Map<String, Long> splitList)
      throws ChatakMerchantException {
    List<EFTDetails> balanceCountList = new ArrayList<>();
    for (Map.Entry<String, Long> entry : splitList.entrySet()) {
      EFTDetails dto = new EFTDetails();
      dto.setMerchantCode(entry.getKey());
      dto.setTranferAmountDebit(entry.getValue());
      dto.setCompanyName(merchantDao.getMerchantCompanyNameOnMerchantCode(entry.getKey()));
      balanceCountList.add(dto);
    }
    return balanceCountList;
  }

  @Override
  public List<ReportsDTO> getAllEftTransfersListOnMerchantCode(GetTransferListRequest request)
      throws ChatakMerchantException {
    return pgTransfersDao.getAllEftTransfersListOnMerchantCode(request);
  }

  @Override
  public GetTransactionIdsListResponse getTransactionIdListOnTransferId(String transferId)
      throws ChatakMerchantException {
    GetTransactionIdsListResponse response = null;
    List<EFTRefTxnData> transactionIdsList = refundTransactionDao.getEFTRefTxnDataList(transferId);
    if (CommonUtil.isListNotNullAndEmpty(transactionIdsList)) {
      response = new GetTransactionIdsListResponse();
      response.setErrorCode(Constants.SUCCESS_CODE);
      response.setTransactionIdsList(transactionIdsList);
    }
    return response;
  }

  public void logEFTAccountTransaction(PGTransfers pgTransfers, String transactionCode) {
    logger.info("FundTransferServiceImpl | logEFTAccountTransaction | Entering");
    PGAccountTransactions eftAccountTransaction = new PGAccountTransactions();
    eftAccountTransaction
        .setAccountTransactionId(accountTransactionsDao.generateAccountTransactionId());
    eftAccountTransaction.setPgTransferId(pgTransfers.getPgTransfersId().toString());
    eftAccountTransaction.setDebit(pgTransfers.getAmount());
    eftAccountTransaction.setAccountNumber(pgTransfers.getFromAccount());
    if (transactionCode.equals(AccountTransactionCode.FT_BANK)) {
      logger.info("FundTransferServiceImpl | logEFTAccountTransaction | Send funds to bank (EFT)");
      eftAccountTransaction.setDescription("Send funds to bank (EFT)");
    } else if (transactionCode.equals(AccountTransactionCode.FT_CHECK)) {
      logger.info("FundTransferServiceImpl | logEFTAccountTransaction | Send funds by Check");
      eftAccountTransaction.setDescription("Send funds by Check");
    }
    eftAccountTransaction.setTransactionCode(transactionCode);
    eftAccountTransaction.setStatus(PGConstants.PG_SETTLEMENT_PROCESSING);
    eftAccountTransaction.setCreatedDate(new Timestamp(System.currentTimeMillis()));
    eftAccountTransaction.setTransactionTime(eftAccountTransaction.getCreatedDate());
    eftAccountTransaction.setMerchantCode(pgTransfers.getMerchantId().toString());
    eftAccountTransaction.setTransactionType(transactionCode);
    accountTransactionsDao.createOrUpdate(eftAccountTransaction);
    logger.info("FundTransferServiceImpl | logEFTAccountTransaction | Exiting");
  }

  @Override
  public AccountBalanceDTO fetchAccountDetails(String accountNumber, String merchantCode)
      throws ChatakMerchantException {
    logger.info("FundTransferServiceImpl | logEFTAccountTransaction | Send funds by Check");
    AccountBalanceDTO accountBalanceDTO = new AccountBalanceDTO();
    try {
      logger.info("Entering |FundTransferServiceImpl | fetchAccountDetails ");
      PGAccount account = accountDao.getAccountonAccountNumber(Long.valueOf(accountNumber));
      String accParentMerchantCode = merchantDao.getParentMerchantCode(account.getEntityId());
      String userParentMerchantCode = merchantDao.getParentMerchantCode(merchantCode);
      //Validation for Merchant-Merchant and Sub Merchant-Merchant 
      if (!(account.getEntityId().equals(merchantCode) || (null != userParentMerchantCode
          ? userParentMerchantCode.equals(accParentMerchantCode) : false)
          || merchantCode.equals(accParentMerchantCode))) {
        logger.error(Constants.ERROR_FUND_TRANSFER_SERVICE_IMPL_PROCESS_ACCOUNT_TRANSFER);
        accountBalanceDTO.setErrorCode(Constants.ERROR_CODE);
        accountBalanceDTO
            .setErrorMessage(Properties.getProperty("chatak.account.invalid.merchant"));
        return accountBalanceDTO;
      }
      if (account.getStatus().equalsIgnoreCase(PGConstants.ACTION_ACTIVE)) {
        accountBalanceDTO.setAccountNumber(account.getAccountNum());
        accountBalanceDTO.setAvailableBalance(account.getAvailableBalance());
        accountBalanceDTO
            .setMerchantName(merchantUpdateDao.getMerchantCompanyName(account.getEntityId()));
        accountBalanceDTO.setErrorCode(Constants.SUCCESS_CODE);
        accountBalanceDTO.setErrorMessage(Constants.SUCESS);
      } else {
        accountBalanceDTO.setErrorCode(Constants.ERROR_CODE);
        accountBalanceDTO.setErrorMessage(Constants.ERROR);
      }
    } catch (Exception e) {
      logger.error("Error |FundTransferServiceImpl | fetchAccountDetails ", e);
      throw new ChatakMerchantException(e.getMessage());
    }
    logger.info("Exiting |FundTransferServiceImpl | fetchAccountDetails ");
    return accountBalanceDTO;
  }

  @Override
  public String processAccountTransfer(AccountTransferRequest accountTransferRequest)
      throws ChatakMerchantException {
    logger.info("Entering |FundTransferServiceImpl | processAccountTransfer ");
    String parentMerchantCode = null;

    accountTransferRequest.setDestinationAccountNumber(
        accountTransferRequest.getDestinationAccountNumber().replace(",", "").trim());
    PGAccount sourceAccount = accountDao
        .getAccountonAccountNumber(Long.valueOf(accountTransferRequest.getSourceAccountNumber()));
    PGAccount destinationAccount = accountDao.getAccountonAccountNumber(
        Long.valueOf(accountTransferRequest.getDestinationAccountNumber()));
    Long currentbal = sourceAccount.getCurrentBalance();
    Long amountToTransfer = CommonUtil.getLongAmount(accountTransferRequest.getTransferAmount());
    if (null != destinationAccount) {
      parentMerchantCode = merchantDao.getParentMerchantCode(destinationAccount.getEntityId());
      if (!(sourceAccount.getEntityId().equals(destinationAccount.getEntityId())
          || sourceAccount.getEntityId().equals(parentMerchantCode))) {
        logger.error(Constants.ERROR_FUND_TRANSFER_SERVICE_IMPL_PROCESS_ACCOUNT_TRANSFER);
        throw new ChatakMerchantException(
            Properties.getProperty("chatak.account.transfer.insufficient.funds"));
      }
      if ((sourceAccount.getAvailableBalance() < amountToTransfer)) {
        logger.error(Constants.ERROR_FUND_TRANSFER_SERVICE_IMPL_PROCESS_ACCOUNT_TRANSFER);
        throw new ChatakMerchantException(
            Properties.getProperty("chatak.account.transfer.insufficient.funds"));
      } else {
        sourceAccount.setAvailableBalance(sourceAccount.getAvailableBalance() - amountToTransfer);
        sourceAccount.setCurrentBalance(sourceAccount.getCurrentBalance() - amountToTransfer);
        destinationAccount
            .setAvailableBalance(destinationAccount.getAvailableBalance() + amountToTransfer);
        destinationAccount
            .setCurrentBalance(destinationAccount.getCurrentBalance() + amountToTransfer);
        accountDao.savePGAccount(sourceAccount);
        accountDao.savePGAccount(destinationAccount);

        logTransferAccountTransaction(accountTransferRequest, sourceAccount, destinationAccount,
            currentbal);
        logger.info("Exiting |FundTransferServiceImpl | processAccountTransfer ");
        return StringUtils.amountToString(sourceAccount.getAvailableBalance());
      }
    }
    logger.info("Exiting |FundTransferServiceImpl | processAccountTransfer ");
    return null;
  }

  /**
   * <<Method to populate account transfer transactions>>
   * 
   * @param accountTransferRequest
   * @param sourceAccount
   * @param destinationAccount
   */
  private void logTransferAccountTransaction(AccountTransferRequest accountTransferRequest,
      PGAccount sourceAccount, PGAccount destinationAccount, Long currentbal) {
    logger.info("Entering |FundTransferServiceImpl | logTransferAccountTransaction ");

    PGAccountTransactions transferAccountDebitLog = null;
    PGAccountTransactions transferAccountCreditLog = null;
    Timestamp currentTimeStamp = new Timestamp(System.currentTimeMillis());
    String accountTransactionId = accountTransactionsDao.generateAccountTransactionId();
    try {
      transferAccountDebitLog = new PGAccountTransactions();
      transferAccountDebitLog.setAccountTransactionId(accountTransactionId);
      transferAccountDebitLog.setAccountNumber(sourceAccount.getAccountNum().toString());
      transferAccountDebitLog.setToAccountNumber(destinationAccount.getAccountNum().toString());
      transferAccountDebitLog.setCreatedDate(currentTimeStamp);
      transferAccountDebitLog.setTransactionTime(currentTimeStamp);
      transferAccountDebitLog.setMerchantCode(sourceAccount.getEntityId());
      transferAccountDebitLog.setProcessedTime(currentTimeStamp);
      transferAccountDebitLog.setStatus(PGConstants.PG_SETTLEMENT_EXECUTED);
      transferAccountDebitLog.setDescription(accountTransferRequest.getDescription());
      transferAccountDebitLog.setTransactionType(AccountTransactionCode.ACCOUNT_DEBIT);
      transferAccountDebitLog.setTransactionCode(AccountTransactionCode.ACCOUNT_DEBIT);
      transferAccountDebitLog.setCurrentBalance(sourceAccount.getCurrentBalance());
      transferAccountDebitLog
          .setDebit(CommonUtil.getLongAmount(accountTransferRequest.getTransferAmount()));
      accountTransactionsDao.createOrUpdate(transferAccountDebitLog);
      transferAccountCreditLog = transferAccountDebitLog;
      transferAccountCreditLog.setId(null);
      transferAccountCreditLog.setDebit(null);
      transferAccountCreditLog
          .setCredit(CommonUtil.getLongAmount(accountTransferRequest.getTransferAmount()));
      transferAccountCreditLog.setTransactionType(AccountTransactionCode.ACCOUNT_CREDIT);
      transferAccountCreditLog.setTransactionCode(AccountTransactionCode.ACCOUNT_CREDIT);
      transferAccountCreditLog.setMerchantCode(destinationAccount.getEntityId());
      transferAccountCreditLog.setCurrentBalance(destinationAccount.getCurrentBalance());
      accountTransactionsDao.createOrUpdate(transferAccountCreditLog);

      PGAccountHistory pgActHistory = new PGAccountHistory();
      if (currentbal < (sourceAccount.getCurrentBalance())) {
        pgActHistory.setPaymentMethod(AccountTransactionCode.ACC_CREDIT);
      } else {
        pgActHistory.setPaymentMethod(AccountTransactionCode.ACC_DEBIT);
      }
      pgActHistory.setEntityId(sourceAccount.getEntityId());
      pgActHistory.setEntityType(sourceAccount.getEntityType());
      pgActHistory.setAccountDesc(sourceAccount.getAccountDesc());
      pgActHistory.setCategory(sourceAccount.getCategory());
      pgActHistory.setCurrentBalance(sourceAccount.getCurrentBalance());
      pgActHistory.setAvailableBalance(sourceAccount.getAvailableBalance());
      pgActHistory.setCurrency(sourceAccount.getCurrency());
      pgActHistory.setAutoPaymentLimit(sourceAccount.getAutoPaymentLimit());
      pgActHistory.setAutoPaymentMethod(sourceAccount.getAutoPaymentMethod());
      pgActHistory.setAutoTransferDay(sourceAccount.getAutoTransferDay());
      pgActHistory.setStatus(sourceAccount.getStatus());
      pgActHistory.setUpdatedDate(transferAccountDebitLog.getCreatedDate());
      pgActHistory.setAccountNum(sourceAccount.getAccountNum());
      pgActHistory.setFeeBalance(sourceAccount.getFeeBalance());
      pgActHistory.setTransactionId(accountTransactionId);

      accountTransactionsDao.saveAccountHistory(pgActHistory);

    } catch (Exception e) {
      logger.error("Error |FundTransferServiceImpl | logTransferAccountTransaction ", e);
    }
    logger.info("Exiting |FundTransferServiceImpl | logTransferAccountTransaction ");
  }

  @Override
  public List<Option> getAccountList(String merchantCode) throws ChatakMerchantException {

    List<Option> optionList = null;
    Option option = null;
    List<PGAccount> accountLists = accountDao.getActivePGAccounts(merchantCode);
    if (CommonUtil.isListNotNullAndEmpty(accountLists)) {
      optionList = new ArrayList<>(accountLists.size());
      for (PGAccount account : accountLists) {
        option = new Option();
        option.setLabel(account.getAccountNum().toString());
        option.setValue(StringUtils.amountToString(account.getAvailableBalance()));
        optionList.add(option);
      }
    }
    return optionList;
  }

}
