package com.chatak.acquirer.admin.service.impl;

import java.sql.Timestamp;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chatak.acquirer.admin.exception.ChatakAdminException;
import com.chatak.acquirer.admin.model.FundTransferActionListModel;
import com.chatak.acquirer.admin.model.FundTransferActionModel;
import com.chatak.acquirer.admin.service.FundTransfersService;
import com.chatak.acquirer.admin.util.FundTransferUtils;
import com.chatak.acquirer.admin.util.StringUtil;
import com.chatak.pg.acq.dao.AccountDao;
import com.chatak.pg.acq.dao.AccountTransactionsDao;
import com.chatak.pg.acq.dao.ExecutedTransactionDao;
import com.chatak.pg.acq.dao.MerchantBankDao;
import com.chatak.pg.acq.dao.MerchantDao;
import com.chatak.pg.acq.dao.MerchantUpdateDao;
import com.chatak.pg.acq.dao.PGTransfersDao;
import com.chatak.pg.acq.dao.RefundTransactionDao;
import com.chatak.pg.acq.dao.TransactionDao;
import com.chatak.pg.acq.dao.VoidTransactionDao;
import com.chatak.pg.acq.dao.model.PGAccount;
import com.chatak.pg.acq.dao.model.PGAccountTransactions;
import com.chatak.pg.acq.dao.model.PGMerchant;
import com.chatak.pg.acq.dao.model.PGMerchantBank;
import com.chatak.pg.acq.dao.model.PGTransaction;
import com.chatak.pg.acq.dao.model.PGTransfers;
import com.chatak.pg.bean.AccountTransferRequest;
import com.chatak.pg.bean.CheckBeneficiary;
import com.chatak.pg.bean.CreditAccount;
import com.chatak.pg.bean.DebitAccount;
import com.chatak.pg.bean.LitleEFTRequest;
import com.chatak.pg.constants.AccountTransactionCode;
import com.chatak.pg.constants.ActionCode;
import com.chatak.pg.constants.PGConstants;
import com.chatak.pg.model.AccountBalanceDTO;
import com.chatak.pg.model.BulkFundTransferResponse;
import com.chatak.pg.model.EFTDetails;
import com.chatak.pg.model.EFTRefTxnData;
import com.chatak.pg.model.FundTransferDTO;
import com.chatak.pg.model.GetTransactionIdsListResponse;
import com.chatak.pg.model.LitleEFTDTO;
import com.chatak.pg.model.ReportsDTO;
import com.chatak.pg.model.TransferRequestsCount;
import com.chatak.pg.user.bean.GetTransferListRequest;
import com.chatak.pg.user.bean.Response;
import com.chatak.pg.util.CommonUtil;
import com.chatak.pg.util.Constants;
import com.chatak.pg.util.Properties;
import com.chatak.pg.util.StringUtils;

@Service
public class FundTransfersServiceImpl implements FundTransfersService {
  @Autowired
  AccountDao accountDao;

  @Autowired
  PGTransfersDao pgTransfersDao;

  @Autowired
  TransactionDao transactionDao;

  @Autowired
  MerchantBankDao merchantBankDao;

  @Autowired
  MerchantDao merchantDao;

  @Autowired
  AccountTransactionsDao accountTransactionsDao;

  @Autowired
  VoidTransactionDao voidTransactionDao;

  @Autowired
  RefundTransactionDao refundTransactionDao;

  @Autowired
  MerchantUpdateDao merchantUpdateDao;

  @Autowired
  ExecutedTransactionDao executedTransactionDao;

  private static final Logger logger = Logger.getLogger(FundTransfersServiceImpl.class);

  @Override
  public TransferRequestsCount getEFTTransferRequestsCount() {

    return pgTransfersDao.getTransferRequestCountOnTransferMode(PGConstants.FUND_TRANSFER_EFT);
  }

  @Override
  public TransferRequestsCount getCheckTransferRequestsCount() {
    return pgTransfersDao.getTransferRequestCountOnTransferMode(PGConstants.FUND_TRANSFER_CHECK);
  }

  @Override
  public List<PGTransfers> getPGTransfersList(GetTransferListRequest transferListRequest) {
    return pgTransfersDao.getTransferList(transferListRequest);
  }

  @Override
  public Response processTransferAction(FundTransferActionModel fundTransferActionModel)
      throws ChatakAdminException {
    Response response = new Response();
    PGAccountTransactions pgAccountTransactions = null;
    PGTransfers pgTransfers =
        pgTransfersDao.getPGTransferRecordById(fundTransferActionModel.getPgTransfersId());
    if (null != pgTransfers) {
      Long merchantId = pgTransfers.getMerchantId();
      PGAccount pgAccount = accountDao.getPgAccount(merchantId.toString());
      if (pgAccount.getAvailableBalance() < pgTransfers.getAmount()) {
        if (!fundTransferActionModel.getAction().equals(PGConstants.PG_TXN_VOIDED)) {
          throw new ChatakAdminException("Insufficient funds");
        } 
        else if (fundTransferActionModel.getAction().equals(PGConstants.PG_TXN_VOIDED)) {
          // Setting cancel status
          pgAccountTransactions = accountTransactionsDao.getAccountTransactionByTransferId(pgTransfers.getPgTransfersId().toString());
          setPGAccountTxn(pgAccountTransactions);
        }
      } 
      else if (fundTransferActionModel.getAction().equals(PGConstants.PG_SETTLEMENT_EXECUTED)) {
        pgAccount.setAvailableBalance(pgAccount.getAvailableBalance() - pgTransfers.getAmount());
        pgAccount.setCurrentBalance(pgAccount.getCurrentBalance() - pgTransfers.getAmount());
        accountDao.savePGAccount(pgAccount);
        pgAccountTransactions = accountTransactionsDao
            .getAccountTransactionByTransferId(pgTransfers.getPgTransfersId().toString());
        setPGAccountTx(pgAccountTransactions, pgAccount);
      }
      logEFTAccountTxn(fundTransferActionModel, pgTransfers);
      pgTransfers.setStatus(fundTransferActionModel.getAction());
      pgTransfersDao.createOrUpdateTransferRecord(pgTransfers);
      pgAccountTxn(pgAccountTransactions);
      response.setErrorCode(ActionCode.ERROR_CODE_00);
      response.setErrorMessage(ActionCode.getInstance().getMessage(ActionCode.ERROR_CODE_00));
      response.setEntityId(fundTransferActionModel.getPgTransfersId().toString());

    } else {
      response.setErrorCode(ActionCode.ERROR_CODE_Z5);
      response.setErrorMessage(ActionCode.getInstance().getMessage(ActionCode.ERROR_CODE_Z5));
    }
    return response;
  }

private void setPGAccountTxn(PGAccountTransactions pgAccountTransactions) {
	if (null != pgAccountTransactions) {
	    pgAccountTransactions.setUpdatedTime(new Timestamp(System.currentTimeMillis()));
	    pgAccountTransactions.setStatus(PGConstants.PG_TXN_VOIDED);
	  }
}

private void pgAccountTxn(PGAccountTransactions pgAccountTransactions) {
	if (null != pgAccountTransactions) {
        accountTransactionsDao.createOrUpdate(pgAccountTransactions);
      }
}

  @Override
  public BulkFundTransferResponse updateBulkFundTransferStatus(
      FundTransferActionListModel fundTransferActionDTOList, String status, String comments) throws ChatakAdminException{
    BulkFundTransferResponse bulkFundTransferResponse = new BulkFundTransferResponse();
    int successCount = 0;
    bulkFundTransferResponse.setSuccess(false);

    if (!fundTransferActionDTOList.getPgTransfersIds().isEmpty()) {
      for (FundTransferActionModel fundTransferActionModel : fundTransferActionDTOList
          .getPgTransfersIds()) {
        PGTransfers pgTransfers = pgTransfersDao.getPGTransferRecordById(fundTransferActionModel.getPgTransfersId());
        successCount = processsPGAccTxn(fundTransferActionDTOList, status, successCount, fundTransferActionModel,
				pgTransfers);
        bulkFundTransferResponse.setSuccess(true);

      }
      bulkFundTransferResponse.setSuccessCount(successCount);
    } else {
      bulkFundTransferResponse.setSuccess(false);
    }
    return bulkFundTransferResponse;
  }

private int processsPGAccTxn(FundTransferActionListModel fundTransferActionDTOList, String status, int successCount,
		FundTransferActionModel fundTransferActionModel, PGTransfers pgTransfers) throws ChatakAdminException {
	PGAccountTransactions pgAccountTransactions;
	if (null != pgTransfers) {
	  Long merchantId = pgTransfers.getMerchantId();
	  PGAccount pgAccount = accountDao.getPgAccount(merchantId.toString());
	  if (pgAccount.getAvailableBalance() < pgTransfers.getAmount()) {
	    throw new ChatakAdminException("Insufficient funds");
	  } 
	  else if (status.equals(PGConstants.PG_SETTLEMENT_EXECUTED)) {
	    pgAccount.setAvailableBalance(pgAccount.getAvailableBalance() - pgTransfers.getAmount());
	    pgAccount.setCurrentBalance(pgAccount.getCurrentBalance() - pgTransfers.getAmount());
	    accountDao.savePGAccount(pgAccount);
	    pgAccountTransactions = accountTransactionsDao.getAccountTransactionByTransferId(pgTransfers.getPgTransfersId().toString());
	    setPGAccountTx(pgAccountTransactions, pgAccount);
	  }
	  logEFTAccountTxn(fundTransferActionModel, pgTransfers);
	  pgTransfers.setStatus(fundTransferActionDTOList.getAction());
	  pgTransfersDao.createOrUpdateTransferRecord(pgTransfers);
	  successCount++;
	}
	return successCount;
}

private void logEFTAccountTxn(FundTransferActionModel fundTransferActionModel, PGTransfers pgTransfers) {
	if (fundTransferActionModel.getAction().equals(PGConstants.PG_SETTLEMENT_PENDING)) {
	    logEFTAccountTransaction(pgTransfers, AccountTransactionCode.FT_BANK);
	  }
}

private void setPGAccountTx(PGAccountTransactions pgAccountTransactions, PGAccount pgAccount) {
	if (null != pgAccountTransactions) {
	  pgAccountTransactions.setUpdatedTime(new Timestamp(System.currentTimeMillis()));
	  pgAccountTransactions.setProcessedTime(pgAccountTransactions.getUpdatedTime());
	  pgAccountTransactions.setStatus(PGConstants.PG_SETTLEMENT_EXECUTED);
	  pgAccountTransactions.setCurrentBalance(pgAccount.getCurrentBalance());
	}
}

  @Override
  public List<ReportsDTO> getPGTransfersListOnTransferMode(GetTransferListRequest request) {
    return pgTransfersDao.getPGTransfersListOnTransferMode(request);
  }

  @Override
  public List<ReportsDTO> getAllEftTransfersListOnMerchantCode(GetTransferListRequest request)
      throws ChatakAdminException {
    return pgTransfersDao.getAllEftTransfersListOnMerchantCode(request);
  }

  @Override
  public LitleEFTRequest getLitleExecutedTransactions(LitleEFTRequest litleEFTRequest) {
    return executedTransactionDao.getLitleExecutedTransactions(litleEFTRequest);
  }

  @Override
  public int getLitleExecutedTransactionsCount() {
    return executedTransactionDao.getLitleExecutedTransactionsCount();
  }

  @Override
  public Response processLitleEFT(List<LitleEFTDTO> list) throws ChatakAdminException {
    Map<String, List<LitleEFTDTO>> splitList =
        FundTransferUtils.splitTransferListOnMerchantCode(list);
    Response response = null;

    for (Map.Entry<String, List<LitleEFTDTO>> entry : splitList.entrySet()) {
      List<LitleEFTDTO> list2 = entry.getValue();
      FundTransferDTO fundTransferDTO = populateFundTransferDTO(entry.getKey(), PGConstants.FUND_TRANSFER_EFT);
      Double amount = 0.00;
      amount = setAmount(list2, amount);
      if (null != amount) {
        amount = amount / Constants.MAX_PAGE_SIZE;
        fundTransferDTO.setAmountToTransfer(amount.toString());
        response = processEFTFundsTransfer(fundTransferDTO);
      }
      processLitleEFT(response, list2);
    }
    return response;
  }

private void processLitleEFT(Response response, List<LitleEFTDTO> list2) {
	if (null != response && response.getErrorCode().equals(Constants.SUCCESS_CODE)) {
        for (LitleEFTDTO ts : list2) {
          PGTransaction transaction =
              refundTransactionDao.getTransactionOnId(ts.getTransactionId());
          transaction.setEftId(response.getEntityId());
          transaction.setEftStatus(PGConstants.LITLE_EFT_EXECUTED);
          if (Constants.SCHEDULER_EFT_PROCESS.equals(ts.getModeOfExecution())) {
            transaction.setEftMode(Constants.SCHEDULER_EFT_PROCESS);
          } else {
            transaction.setEftMode(Constants.MANUAL_EFT_PROCESS);
          }
          voidTransactionDao.createTransaction(transaction);
        }
      }
}

private Double setAmount(List<LitleEFTDTO> list2, Double amount) {
	for (LitleEFTDTO ts : list2) {
        amount = amount + ts.getAmount();
      }
	return amount;
}

  @Override
  public Response processIndividualLitleEFT(LitleEFTDTO litleEFTDTO) throws ChatakAdminException {
    Response response = null;
    FundTransferDTO fundTransferDTO =
        populateFundTransferDTO(litleEFTDTO.getMerchantCode(), PGConstants.FUND_TRANSFER_EFT);
    Double amount = 0.00 + litleEFTDTO.getAmount();
    if (null != amount) {
      amount = amount / Constants.MAX_PAGE_SIZE;
      fundTransferDTO.setAmountToTransfer(amount.toString());
      response = processEFTFundsTransfer(fundTransferDTO);
    }
    if (null != response && response.getErrorCode().equals(Constants.SUCCESS_CODE)) {
      PGTransaction transaction =
          refundTransactionDao.getTransactionOnId(litleEFTDTO.getTransactionId());
      transaction.setEftId(response.getEntityId());
      transaction.setEftStatus(PGConstants.LITLE_EFT_EXECUTED);
      if (Constants.SCHEDULER_EFT_PROCESS.equals(litleEFTDTO.getModeOfExecution())) {
        transaction.setEftMode(Constants.SCHEDULER_EFT_PROCESS);
      } else {
        transaction.setEftMode(Constants.MANUAL_EFT_PROCESS);
      }
      voidTransactionDao.createTransaction(transaction);
    }
    return response;
  }

  @Override
  public FundTransferDTO populateFundTransferDTO(String merchantCode, String fundTransferMode)
      throws ChatakAdminException {
    FundTransferDTO fundTransferDTO = new FundTransferDTO();
    PGMerchantBank pgMerchantBank = merchantBankDao.getMerchantBankByMerchantId(merchantCode);
    PGAccount pgAccount = accountDao.getPgAccount(merchantCode);
    PGMerchant pgMerchant = merchantUpdateDao.getMerchant(merchantCode);
    CreditAccount creditAccount = new CreditAccount();
    DebitAccount debitAccount = new DebitAccount();
    CheckBeneficiary checkBeneficiary = new CheckBeneficiary();
    if (null != pgMerchantBank && null != pgAccount) {
      fundTransferDTO.setMerchantCode(merchantCode);
      debitAccount.setAccountNumber(pgAccount.getAccountNum());
      debitAccount.setAccountType(pgMerchantBank.getAccountType());
      debitAccount.setAvaliableBalance(
          CommonUtil.getDoubleAmount(pgAccount.getAvailableBalance()).toString());
      fundTransferDTO.setDebitAccount(debitAccount);
      if (fundTransferMode.equals(PGConstants.FUND_TRANSFER_EFT)) {
        creditAccount.setAccountNumber(pgMerchantBank.getBankAccNum());
        creditAccount.setAccountType(pgMerchantBank.getAccountType());
        creditAccount.setBankName(pgMerchantBank.getBankName());
        creditAccount.setCity(pgMerchantBank.getCity());
        creditAccount.setBankRoutingNumber(pgMerchantBank.getRoutingNumber());
        creditAccount.setNameOnAcccount(pgMerchantBank.getNameOnAccount());
        creditAccount.setState(pgMerchantBank.getState());
        creditAccount.setAddress(pgMerchantBank.getAddress1());
        fundTransferDTO.setCreditAccount(creditAccount);
      } else if (fundTransferMode.equals(PGConstants.FUND_TRANSFER_CHECK)) {
    	checkBeneficiary.setState(pgMerchant.getState());
        checkBeneficiary.setAddress2(pgMerchant.getAddress2());
        checkBeneficiary.setBeneficiaryName(pgMerchant.getFirstName());
        checkBeneficiary.setAddress1(pgMerchant.getAddress1());
        checkBeneficiary.setCity(pgMerchant.getCity());
        checkBeneficiary.setCountry(pgMerchant.getCountry());
        fundTransferDTO.setCheckBeneficiary(checkBeneficiary);
        checkBeneficiary.setZip(pgMerchant.getPin());
      }
      fundTransferDTO.setFundTransferMode(fundTransferMode);
    }
    return fundTransferDTO;
  }

  @Override
  public Response processEFTFundsTransfer(FundTransferDTO fundTransferDTO)
      throws ChatakAdminException {
    if (Double.valueOf(fundTransferDTO.getAmountToTransfer()) <= 0) {
      throw new ChatakAdminException("Invalid amount");
    }
    if (Double.valueOf(fundTransferDTO.getAmountToTransfer()) > Double
        .valueOf(fundTransferDTO.getDebitAccount().getAvaliableBalance())) {
      throw new ChatakAdminException("Insufficient account balance");
    }
    PGTransfers pgTransfers = logTransfers(fundTransferDTO);
    if (null != pgTransfers) {
      FundTransferActionModel fundTransferActionModel = new FundTransferActionModel();
      fundTransferActionModel.setAction(PGConstants.PG_SETTLEMENT_EXECUTED);
      fundTransferActionModel.setPgTransfersId(pgTransfers.getPgTransfersId());
      return processTransferAction(fundTransferActionModel);
    } else {
      throw new ChatakAdminException("General Error");
    }
  }

  @Override
  public Response processCheckFundsTransfer(FundTransferDTO fundTransferDTO)
      throws ChatakAdminException {
    if (Double.valueOf(fundTransferDTO.getAmountToTransfer()) <= 0) {
      throw new ChatakAdminException("Invalid amount");
    }
    if (Double.valueOf(fundTransferDTO.getAmountToTransfer()) > Double
        .valueOf(fundTransferDTO.getDebitAccount().getAvaliableBalance())) {
      throw new ChatakAdminException("Insufficient account balance");
    }
    logTransfers(fundTransferDTO);
    return null;
  }

  @Override
  public PGTransfers logTransfers(FundTransferDTO fundTransferDTO) {
    PGTransfers pgTransfers = new PGTransfers();
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
            StringUtils.amountToString(pgTransfers.getAmount()), StringUtils.amountToString(0L));
    pgTransfers.setTxnDescription(descriptionTemplate);
    if (fundTransferDTO.getFundTransferMode().equals(PGConstants.FUND_TRANSFER_EFT)) {
      pgTransfers.setBankRoutingNumber(fundTransferDTO.getCreditAccount().getBankRoutingNumber());
      pgTransfers.setState(fundTransferDTO.getCreditAccount().getState());
      pgTransfers.setCity(fundTransferDTO.getCreditAccount().getCity());
      pgTransfers.setToAccount(fundTransferDTO.getCreditAccount().getAccountNumber());
      pgTransfers.setNameOnAccount(fundTransferDTO.getCreditAccount().getNameOnAcccount());
    } else if (fundTransferDTO.getFundTransferMode().equals(PGConstants.FUND_TRANSFER_CHECK)) {
      pgTransfers.setState(fundTransferDTO.getCheckBeneficiary().getState());
      pgTransfers.setCity(fundTransferDTO.getCheckBeneficiary().getState());
    }
    pgTransfers.setUpdatedDate(null);
    return pgTransfersDao.createOrUpdateTransferRecord(pgTransfers);
  }

  @Override
  public Map<String, Long> splitReportsAmount(List<ReportsDTO> list) {
    Map<String, Long> splitList = new HashMap<>();
    Long tempAmount = null;
    for (ReportsDTO yo : list) {
      tempAmount = splitList.get(yo.getMerchantCode());
      if (null == tempAmount) {
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
      throws ChatakAdminException {
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
  public GetTransactionIdsListResponse getTransactionIdListOnTransferId(String transferId)
      throws ChatakAdminException {
    GetTransactionIdsListResponse response = null;
    List<EFTRefTxnData> transactionIdsList = refundTransactionDao.getEFTRefTxnDataList(transferId);
    if (CommonUtil.isListNotNullAndEmpty(transactionIdsList)) {
      response = new GetTransactionIdsListResponse();
      response.setErrorCode(Constants.SUCCESS_CODE);
      response.setTransactionIdsList(transactionIdsList);
    }
    return response;
  }

  @Override
  public LitleEFTRequest getLitleExecutedTransactionsOnMerchantCodeAndPayoutFrequency(
      String merchantCode, Integer payoutFrequencyDays) {
    return executedTransactionDao.getLitleExecutedTransactionsOnMerchantCodeAndPayoutFrequency(
        merchantCode, payoutFrequencyDays);
  }

  public void logEFTAccountTransaction(PGTransfers pgTransfers, String transactionCode) {
    logger.info("FundTransferServiceImpl | logEFTAccountTransaction | Entering");
    PGAccountTransactions eftAccountTransaction = new PGAccountTransactions();
    eftAccountTransaction
        .setAccountTransactionId(accountTransactionsDao.generateAccountTransactionId());
    eftAccountTransaction.setPgTransferId(pgTransfers.getPgTransfersId().toString());
    eftAccountTransaction.setAccountNumber(pgTransfers.getFromAccount());
    eftAccountTransaction.setDebit(pgTransfers.getAmount());
    if (transactionCode.equals(AccountTransactionCode.FT_BANK)) {
      logger.info("FundTransferServiceImpl | logEFTAccountTransaction | Send funds to bank (EFT)");
      eftAccountTransaction.setDescription("Send funds to bank (EFT)");
    } else if (transactionCode.equals(AccountTransactionCode.FT_CHECK)) {
      logger.info("FundTransferServiceImpl | logEFTAccountTransaction | Send funds by Check");
      eftAccountTransaction.setDescription("Send funds by Check");
    }
    eftAccountTransaction.setTransactionCode(transactionCode);
    eftAccountTransaction.setCreatedDate(new Timestamp(System.currentTimeMillis()));
    eftAccountTransaction.setStatus(PGConstants.PG_SETTLEMENT_PROCESSING);
    eftAccountTransaction.setTransactionTime(eftAccountTransaction.getCreatedDate());
    eftAccountTransaction.setTransactionType(transactionCode);
    accountTransactionsDao.createOrUpdate(eftAccountTransaction);
    logger.info("FundTransferServiceImpl | logEFTAccountTransaction | Exiting");
  }

  @Override
  public AccountBalanceDTO fetchAccountDetails(String accountNumber) throws ChatakAdminException {
    logger.info("FundTransferServiceImpl | logEFTAccountTransaction | Send funds by Check");
    AccountBalanceDTO accountBalanceDTO = new AccountBalanceDTO();
    try {
      logger.info("Entering |FundTransferServiceImpl | fetchAccountDetails ");
      PGAccount account = accountDao.getAccountonAccountNumber(Long.valueOf(accountNumber));

      if (null != account && account.getStatus().equalsIgnoreCase(PGConstants.ACTION_ACTIVE)) {
        accountBalanceDTO.setAccountNumber(account.getAccountNum());
        accountBalanceDTO.setAvailableBalance(account.getAvailableBalance());
        accountBalanceDTO
            .setMerchantName(merchantUpdateDao.getMerchantCompanyName(account.getEntityId()));
        accountBalanceDTO.setErrorMessage(Constants.SUCESS);
        accountBalanceDTO.setErrorCode(Constants.SUCCESS_CODE);
      } else {
        accountBalanceDTO.setErrorCode(Constants.ERROR_CODE);
        accountBalanceDTO.setErrorMessage(Constants.ERROR);
      }
    } catch (Exception e) {
      logger.error("Error |FundTransferServiceImpl | fetchAccountDetails ",e);
      throw new ChatakAdminException(e.getMessage());
    }
    logger.info("Exiting |FundTransferServiceImpl | fetchAccountDetails ");
    return accountBalanceDTO;
  }

  @Override
  public String processAccountTransfer(AccountTransferRequest accountTransferRequest)
      throws ChatakAdminException {
    logger.info("Entering |FundTransferServiceImpl | processAccountTransfer ");
    PGAccount sourceAccount = accountDao
        .getAccountonAccountNumber(Long.valueOf(accountTransferRequest.getSourceAccountNumber()));
    PGAccount destinationAccount = accountDao.getAccountonAccountNumber(
        Long.valueOf(accountTransferRequest.getDestinationAccountNumber()));
    Long amountToTransfer = CommonUtil.getLongAmount(accountTransferRequest.getTransferAmount());
    if (null != sourceAccount && null != destinationAccount) {
      if ((!StringUtil.isNullAndEmpty(accountTransferRequest.getAccountCloseFlag())
          && accountTransferRequest.getAccountCloseFlag().equals("false"))
          && (sourceAccount.getAvailableBalance() < amountToTransfer)) {
        logger.error("ERROR |FundTransferServiceImpl | processAccountTransfer ");
        throw new ChatakAdminException(
            Properties.getProperty("chatak.account.transfer.insufficient.funds"));
      } else {
        PGAccount secondayAccount = null;
        if (accountTransferRequest.getAccountCloseFlag() != null
            && accountTransferRequest.getAccountCloseFlag().equals("true")) {
          amountToTransfer = sourceAccount.getAvailableBalance();
          sourceAccount.setAvailableBalance(0L);
          sourceAccount.setCurrentBalance(0L);
          sourceAccount.setStatus(Constants.ACC_TERMINATED);
          secondayAccount = setAccount(sourceAccount, secondayAccount);
        } else {
          sourceAccount.setAvailableBalance(sourceAccount.getAvailableBalance() - amountToTransfer);
          sourceAccount.setCurrentBalance(sourceAccount.getCurrentBalance() - amountToTransfer);
        }
        destinationAccount
            .setAvailableBalance(destinationAccount.getAvailableBalance() + amountToTransfer);
        destinationAccount
            .setCurrentBalance(destinationAccount.getCurrentBalance() + amountToTransfer);
        accountDao.savePGAccount(sourceAccount);
        if (null != secondayAccount
            && accountTransferRequest.getAccountCloseFlag().equals("false")) {
          accountDao.savePGAccount(secondayAccount);
        }
        accountDao.savePGAccount(destinationAccount);
        logTransferAccountTransaction(accountTransferRequest, sourceAccount, destinationAccount);

        logger.info("Exiting |FundTransferServiceImpl | processAccountTransfer ");
        return StringUtils.amountToString(sourceAccount.getAvailableBalance());

      }
    }
    logger.info("Exiting |FundTransferServiceImpl | processAccountTransfer ");
    return null;
  }

private PGAccount setAccount(PGAccount sourceAccount, PGAccount secondayAccount) {
	if (sourceAccount.getCategory().equals(PGConstants.PRIMARY_ACCOUNT)) {
	    secondayAccount = accountDao.getSecondaryAccount(sourceAccount.getEntityId());//Active secondary account
	    if (null != secondayAccount) {
	      logger.info(
	          "FundTransferServiceImpl | processAccountTransfer|Setting secondary Account to Primary ");
	      secondayAccount.setCategory(PGConstants.PRIMARY_ACCOUNT);
	    }
	  }
	return secondayAccount;
}

  /**
   * <<Method to populate account transfer transactions>>
   * 
   * @param accountTransferRequest
   * @param sourceAccount
   * @param destinationAccount
   */
  private void logTransferAccountTransaction(AccountTransferRequest accountTransferRequest,
      PGAccount sourceAccount, PGAccount destinationAccount) {
    logger.info("Entering |FundTransferServiceImpl | logTransferAccountTransaction ");

    PGAccountTransactions transferAccountDebitLog = null;
    PGAccountTransactions transferAccountCreditLog = null;
    Timestamp currentTimeStamp = new Timestamp(System.currentTimeMillis());
    String accountTransactionId = accountTransactionsDao.generateAccountTransactionId();
    try {
      transferAccountDebitLog = new PGAccountTransactions();
      transferAccountDebitLog.setAccountNumber(sourceAccount.getAccountNum().toString());
      transferAccountDebitLog.setToAccountNumber(destinationAccount.getAccountNum().toString());
      transferAccountDebitLog.setAccountTransactionId(accountTransactionId);
      transferAccountDebitLog.setCreatedDate(currentTimeStamp);
      transferAccountDebitLog.setTransactionTime(currentTimeStamp);
      transferAccountDebitLog.setProcessedTime(currentTimeStamp);
      transferAccountDebitLog.setStatus(PGConstants.PG_SETTLEMENT_EXECUTED);
      transferAccountDebitLog.setMerchantCode(sourceAccount.getEntityId());
      transferAccountDebitLog.setDescription(accountTransferRequest.getDescription());
      transferAccountDebitLog.setTransactionType(AccountTransactionCode.ACCOUNT_DEBIT);
      transferAccountDebitLog.setCurrentBalance(sourceAccount.getCurrentBalance());
      transferAccountDebitLog.setTransactionCode(AccountTransactionCode.ACCOUNT_DEBIT);
      transferAccountDebitLog
          .setDebit(CommonUtil.getLongAmount(accountTransferRequest.getTransferAmount()));
      accountTransactionsDao.createOrUpdate(transferAccountDebitLog);
      transferAccountCreditLog = transferAccountDebitLog;
      transferAccountCreditLog.setDebit(null);
      transferAccountCreditLog.setId(null);
      transferAccountCreditLog
          .setCredit(CommonUtil.getLongAmount(accountTransferRequest.getTransferAmount()));
      transferAccountCreditLog.setMerchantCode(destinationAccount.getEntityId());
      transferAccountCreditLog.setTransactionType(AccountTransactionCode.ACCOUNT_CREDIT);
      transferAccountCreditLog.setTransactionCode(AccountTransactionCode.ACCOUNT_CREDIT);
      transferAccountCreditLog.setCurrentBalance(destinationAccount.getCurrentBalance());
      accountTransactionsDao.createOrUpdate(transferAccountCreditLog);

    } catch (Exception e) {
      logger.error("Error |FundTransferServiceImpl | logTransferAccountTransaction ", e);
    }

    logger.info("Exiting |FundTransferServiceImpl | logTransferAccountTransaction ");

  }

}
