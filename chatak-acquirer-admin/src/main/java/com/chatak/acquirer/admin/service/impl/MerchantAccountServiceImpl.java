package com.chatak.acquirer.admin.service.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

import com.chatak.acquirer.admin.exception.ChatakAdminException;
import com.chatak.acquirer.admin.model.MerchantAccountSearchResponse;
import com.chatak.acquirer.admin.service.MerchantAccountService;
import com.chatak.acquirer.admin.util.StringUtil;
import com.chatak.mailsender.service.MailServiceManagement;
import com.chatak.pg.acq.dao.AccountDao;
import com.chatak.pg.acq.dao.AccountHistoryDao;
import com.chatak.pg.acq.dao.AccountTransactionsDao;
import com.chatak.pg.acq.dao.CurrencyConfigDao;
import com.chatak.pg.acq.dao.CurrencyDao;
import com.chatak.pg.acq.dao.MerchantDao;
import com.chatak.pg.acq.dao.MerchantProfileDao;
import com.chatak.pg.acq.dao.MerchantUpdateDao;
import com.chatak.pg.acq.dao.model.PGAccount;
import com.chatak.pg.acq.dao.model.PGAccountHistory;
import com.chatak.pg.acq.dao.model.PGAccountTransactions;
import com.chatak.pg.acq.dao.model.PGCurrencyConfig;
import com.chatak.pg.acq.dao.model.PGMerchant;
import com.chatak.pg.acq.dao.model.PGMerchantBank;
import com.chatak.pg.acq.dao.model.PGMerchantConfig;
import com.chatak.pg.acq.dao.repository.CurrencyConfigRepository;
import com.chatak.pg.acq.dao.repository.MerchantRepository;
import com.chatak.pg.bean.Response;
import com.chatak.pg.constants.AccountTransactionCode;
import com.chatak.pg.constants.PGConstants;
import com.chatak.pg.model.AccountBalanceDTO;
import com.chatak.pg.model.AccountBalanceReportDTO;
import com.chatak.pg.model.Merchant;
import com.chatak.pg.user.bean.MerchantAccountSearchDto;
import com.chatak.pg.user.bean.MerchantDetailsForAccountCreate;
import com.chatak.pg.user.bean.MerchantDetailsForAccountResponse;
import com.chatak.pg.util.CommonUtil;
import com.chatak.pg.util.Constants;
import com.chatak.pg.util.DateUtil;
import com.chatak.pg.util.Properties;
import com.chatak.pg.util.StringUtils;
import com.chatak.prepaid.velocity.IVelocityTemplateCreator;

@Service
public class MerchantAccountServiceImpl implements MerchantAccountService, PGConstants {

  private static Logger logger = Logger.getLogger(MerchantAccountServiceImpl.class);

  @Autowired
  private MerchantDao merchantDao;

  @Autowired
  CurrencyDao currencyDao;

  @Autowired
  private MessageSource messageSource;

  @Autowired
  private AccountDao accountDao;

  @Autowired
  MailServiceManagement mailingServiceManagement;

  @Autowired
  IVelocityTemplateCreator iVelocityTemplateCreator;

  @Autowired
  private AccountHistoryDao accountHistoryDao;

  @Autowired
  private AccountTransactionsDao accountTransactionsDao;

  @Autowired
  MerchantRepository merchantRepository;

  @Autowired
  CurrencyConfigRepository currencyConfigRepository;

  @Autowired
  CurrencyConfigDao currencyConfigDao;
  
  @Autowired
  MerchantProfileDao merchantProfileDao;
  
  @Autowired
  MerchantUpdateDao merchantUpdateDao;
  
  @Override
  public Merchant getMerchantAccountDetails(String merchantCode) throws ChatakAdminException {
    Merchant merchant = new Merchant();
    PGAccount pgAccount = merchantProfileDao.getPgAccount(merchantCode);
    merchant.setAvailableBalance(
        PGConstants.DOLLAR_SYMBOL + StringUtils.amountToString(pgAccount.getAvailableBalance()));
    merchant.setCurrentBalance(
        PGConstants.DOLLAR_SYMBOL + StringUtils.amountToString(pgAccount.getCurrentBalance()));
    merchant.setCurrency(pgAccount.getCurrency());
    merchant.setBankAccountNumber(Long.toString(pgAccount.getAccountNum()));
    merchant.setBankAccountType(pgAccount.getEntityType());
    merchant.setCreatedDate(
        DateUtil.toDateStringFormat(pgAccount.getCreatedDate(), DateUtil.VIEW_DATE_TIME_FORMAT));
    merchant.setAccountStatus(pgAccount.getStatus());
    merchant.setCurrency(pgAccount.getCurrency());
    return merchant;
  }

  @Override
  public List<AccountBalanceReportDTO> getAllAccountsBalanceReport(Merchant merchant)
      throws ChatakAdminException {
     return merchantProfileDao.getAllAccountsBalanceReport(merchant);
  }

  @Override
  public List<AccountBalanceReportDTO> getAllAccountsBalanceReportPagination(Merchant merchant)
      throws ChatakAdminException {
    return merchantProfileDao.getAllAccountsBalanceReport(merchant);
  }

  @Override
  public AccountBalanceDTO getAccountBalanceDTO(String merchantId) {
    logger.info("Entering:: MerchantServiceImpl:: getAccountBalanceDTO method");
    PGMerchant pgMerchant = merchantUpdateDao.getMerchant(merchantId);
    PGAccount pgAccount = accountDao.getPgAccount(merchantId);
    PGCurrencyConfig pgCurrencyConfig =
        currencyConfigDao.getCurrencyCodeNumeric(pgMerchant.getLocalCurrency());
    AccountBalanceDTO accountBalanceDTO = new AccountBalanceDTO();
    if (null != pgAccount && null != pgMerchant && null != pgCurrencyConfig
        && !(pgAccount.getStatus().equals(PGConstants.S_STATUS_TERMINATED)
            || pgAccount.getStatus().equals(PGConstants.S_STATUS_INACTIVE)
            || pgAccount.getStatus().equals(PGConstants.S_STATUS_PENDING)
            || pgAccount.getStatus().equals(PGConstants.S_STATUS_DELETED))) {

      if (null != pgAccount && null != pgMerchant
          && !(pgAccount.getStatus().equals(PGConstants.S_STATUS_TERMINATED)
              || pgAccount.getStatus().equals(PGConstants.S_STATUS_SUSPENDED)
              || pgAccount.getStatus().equals(PGConstants.S_STATUS_PENDING)
              || pgAccount.getStatus().equals(PGConstants.S_STATUS_DELETED))) {

        accountBalanceDTO.setAccountNumber(pgAccount.getAccountNum());
        accountBalanceDTO.setAvailableBalance(pgAccount.getAvailableBalance());
        accountBalanceDTO.setCurrentBalance(pgAccount.getCurrentBalance());
        accountBalanceDTO.setEntityType(pgAccount.getEntityType());
        accountBalanceDTO.setMerchantCode(merchantId);
        accountBalanceDTO.setMerchantName(pgMerchant.getBusinessName());
        accountBalanceDTO.setErrorCode(Constants.SUCCESS_CODE);
        accountBalanceDTO.setMerchantCurrencyAlpha(pgMerchant.getLocalCurrency());
        accountBalanceDTO.setCurrencyExponent(pgCurrencyConfig.getCurrencyExponent());
        accountBalanceDTO
            .setCurrencySeparatorPosition(pgCurrencyConfig.getCurrencySeparatorPosition());
        accountBalanceDTO.setCurrencyMinorUnit(pgCurrencyConfig.getCurrencyMinorUnit());
        accountBalanceDTO.setCurrencyThousandsUnit(pgCurrencyConfig.getCurrencyThousandsUnit());
      } else {
        accountBalanceDTO.setErrorCode(Constants.ERROR_CODE);
        accountBalanceDTO.setErrorMessage(
            Properties.getProperty("chatak.admin.virtual.terminal.invalid.merchant"));
      }
      logger.info("Exiting:: MerchantServiceImpl:: getAccountBalanceDTO method");
    }
    return accountBalanceDTO;
  }

  @Override
  public Response processMerchantAccountBalanceUpdate(
      AccountBalanceDTO accountBalanceDTO, String type) {
    logger.info("Entering:: MerchantServiceImpl:: processMerchantAccountBalanceUpdate method");
    PGMerchant pgMerchant = merchantUpdateDao.getMerchant(accountBalanceDTO.getMerchantCode());
    PGAccount pgAccount = accountDao.getPgAccount(accountBalanceDTO.getMerchantCode());
    PGCurrencyConfig pgCurrencyConfig =
        currencyConfigDao.getCurrencyCodeNumeric(pgMerchant.getLocalCurrency());
    accountBalanceDTO.setCurrencyExponent(pgCurrencyConfig.getCurrencyExponent());
    accountBalanceDTO.setCurrencySeparatorPosition(pgCurrencyConfig.getCurrencySeparatorPosition());
    accountBalanceDTO.setCurrencyMinorUnit(pgCurrencyConfig.getCurrencyMinorUnit());
    accountBalanceDTO.setCurrencyThousandsUnit(pgCurrencyConfig.getCurrencyThousandsUnit());
    Response response = new Response();
    if (pgMerchant.getStatus().equals(PGConstants.STATUS_SUCCESS)) {
      if (null != pgMerchant && null != pgAccount) {
        if (PGConstants.PAYMENT_METHOD_CREDIT.equals(type)) {
          pgAccount.setAvailableBalance(
              pgAccount.getAvailableBalance() + accountBalanceDTO.getInputAmount());
          pgAccount.setCurrentBalance(
              pgAccount.getCurrentBalance() + accountBalanceDTO.getInputAmount());
          accountDao.savePGAccount(pgAccount);
          // Logging into Account Transactions
          logManualAccountTransaction(pgAccount, accountBalanceDTO.getInputAmount(),
              AccountTransactionCode.MANUAL_CREDIT, accountBalanceDTO);
          // Logging in to account history
          logPgAccountHistory(accountBalanceDTO.getMerchantCode(), type);
          response.setErrorCode(PGConstants.SUCCESS);
          response.setErrorMessage("Success");
          logger.info("Exiting:: MerchantServiceImpl:: processMerchantAccountBalanceUpdate method");
          return response;
        } else if (PGConstants.PAYMENT_METHOD_DEBIT.equals(type)) {
          processsPGAccount(accountBalanceDTO, type, pgAccount, response);
        }
      }
    } else {
      response.setErrorCode(Constants.ERROR_CODE);
      response.setErrorMessage(messageSource.getMessage("merchant.should.be.active", null,
          LocaleContextHolder.getLocale()));

      return response;
    }
    logger.info("Entering:: MerchantServiceImpl:: processMerchantAccountBalanceUpdate method");
    return response;
  }

private Response processsPGAccount(AccountBalanceDTO accountBalanceDTO, String type, PGAccount pgAccount,
		Response response) {
	if (pgAccount.getAvailableBalance() >= accountBalanceDTO.getInputAmount()) {
	    pgAccount.setAvailableBalance(
	        pgAccount.getAvailableBalance() - accountBalanceDTO.getInputAmount());
	    pgAccount.setCurrentBalance(
	        pgAccount.getCurrentBalance() - accountBalanceDTO.getInputAmount());
	    accountDao.savePGAccount(pgAccount);
	    // Logging into Account Transactions
	    logManualAccountTransaction(pgAccount, accountBalanceDTO.getInputAmount(),
	        AccountTransactionCode.MANUAL_DEBIT, accountBalanceDTO);
	    // Logging in to account history
	    logPgAccountHistory(accountBalanceDTO.getMerchantCode(), type);
	    response.setErrorCode(PGConstants.SUCCESS);
	    response.setErrorMessage("Success");
	  } 
	  else {
	    response.setErrorCode(Constants.ERROR_CODE);
	    response.setErrorMessage(messageSource.getMessage("Manual_debit_insufficient_Amount",
	        null, LocaleContextHolder.getLocale()));
	  }
	return response;
}

  public void logPgAccountHistory(String merchantId, String paymentMethod) {
    PGAccount updatedAccount = accountDao.getPgAccount(merchantId);
    if (null != updatedAccount) {
      logger.info("Entering:: MerchantServiceImpl:: logPgAccountHistory method");
      PGAccountHistory pgAccountHistory = new PGAccountHistory();
      pgAccountHistory.setEntityId(updatedAccount.getEntityId());
      pgAccountHistory.setAccountDesc(updatedAccount.getAccountDesc());
      pgAccountHistory.setEntityType(updatedAccount.getEntityType());
      pgAccountHistory.setCategory(updatedAccount.getCategory());
      pgAccountHistory.setAvailableBalance(updatedAccount.getAvailableBalance());
      pgAccountHistory.setCurrentBalance(updatedAccount.getCurrentBalance());
      pgAccountHistory.setCurrency(updatedAccount.getCurrency());
      pgAccountHistory.setAutoPaymentMethod(updatedAccount.getAutoPaymentMethod());
      pgAccountHistory.setAutoPaymentLimit(updatedAccount.getAutoPaymentLimit());
      pgAccountHistory.setAutoTransferDay(updatedAccount.getAutoTransferDay());
      pgAccountHistory.setUpdatedDate(new Timestamp(System.currentTimeMillis()));
      pgAccountHistory.setStatus(updatedAccount.getStatus());
      pgAccountHistory.setAccountNum(updatedAccount.getAccountNum());
      pgAccountHistory.setPaymentMethod(paymentMethod);
      pgAccountHistory.setFeeBalance(updatedAccount.getFeeBalance());
      accountHistoryDao.createOrSave(pgAccountHistory);
      logger.info("Exiting:: MerchantServiceImpl:: logPgAccountHistory method");
    }
  }

  @Override
  public void getMerchantConfigDetailsForAccountCreate(Merchant merchant) {
    PGMerchant pgMerchant = merchantUpdateDao.getMerchant(merchant.getMerchantCode());
    merchant.setMerchantCallBackURL(pgMerchant.getMerchantCallBack());
    merchant.setMerchantConfigId(pgMerchant.getMerchantConfig().getId());
    merchant.setLitleMID(pgMerchant.getLitleMID());
    merchant.setAutoSettlementStatus(pgMerchant.getMerchantConfig().getAutoSettlement().toString());
    merchantDao.getMerchantConfigDetailsForAccountCreate(merchant);
  }

  @Override
  public Response createMerchantAccount(
      Merchant merchant, Long loggedInUserId) {

    PGAccount pgAccount = new PGAccount();
    Long accNum = merchantUpdateDao.generateAccountNum();
    pgAccount.setAccountNum(accNum);
    pgAccount.setEntityId(merchant.getMerchantCode());
    pgAccount.setEntityType(merchant.getMerchantType());
    pgAccount.setAccountDesc("Manual Account Creation");
    pgAccount.setCategory(merchant.getCategory());
    pgAccount.setAutoPaymentLimit(
        null != merchant.getAutoTransferLimit() ? merchant.getAutoTransferLimit() : null);
    pgAccount.setAutoPaymentMethod(merchant.getAutoPaymentMethod());
    pgAccount.setAvailableBalance(PGConstants.ZERO);
    pgAccount.setCreatedDate(DateUtil.getCurrentTimestamp());
    pgAccount.setCurrentBalance(PGConstants.ZERO);
    pgAccount.setFeeBalance(PGConstants.ZERO);
    pgAccount.setCurrency(PGConstants.USD);
    pgAccount.setStatus(Constants.ACTIVE);

    if (!StringUtil.isNullAndEmpty(merchant.getAutoTransferDay())) {
      if (merchant.getAutoTransferDay().startsWith(Constants.DAILY)) {
        pgAccount.setAutoTransferDay(merchant.getAutoTransferDay());
      } else if (merchant.getAutoTransferDay().startsWith(Constants.WEEKLY)) {
        pgAccount.setAutoTransferDay(
            merchant.getAutoTransferDay() + ":" + merchant.getAutoTransferWeeklyDay());
      } else if (merchant.getAutoTransferDay().startsWith(Constants.MONTHLY)) {
        pgAccount.setAutoTransferDay(
            merchant.getAutoTransferDay() + ":" + merchant.getAutoTransferMonthlyDay());
      }
    } else {
      pgAccount.setAutoTransferDay(merchant.getAutoTransferDay());
    }

    if (null != merchant.getAutoTransferLimit()) {
      pgAccount.setAutoPaymentLimit(merchant.getAutoTransferLimit());
    }
    PGMerchantBank merchantBank = new PGMerchantBank();
    pgAccount.setPgMerchantBank(merchantBank);
    pgAccount.getPgMerchantBank().setAccountType(merchant.getBankAccountType());
    pgAccount.getPgMerchantBank().setBankAccNum(merchant.getBankAccountNumber());
    pgAccount.getPgMerchantBank().setRoutingNumber(merchant.getBankRoutingNumber());
    pgAccount.getPgMerchantBank().setBankName(merchant.getBankAccountName());
    pgAccount.getPgMerchantBank().setCreatedBy(loggedInUserId);
    pgAccount.getPgMerchantBank().setCreatedDate(DateUtil.getCurrentTimestamp());
    pgAccount.getPgMerchantBank().setCurrencyCode(merchant.getBankCurrencyCode());
    pgAccount.getPgMerchantBank().setMerchantId(merchant.getMerchantCode());
    pgAccount.getPgMerchantBank().setStatus(PGConstants.STATUS_SUCCESS);
    pgAccount.getPgMerchantBank().setUpdatedDate(pgAccount.getPgMerchantBank().getCreatedDate());
    pgAccount.getPgMerchantBank().setNameOnAccount(merchant.getBankNameOnAccount());
    pgAccount.getPgMerchantBank().setAddress1(merchant.getBankAddress1());
    pgAccount.getPgMerchantBank().setAddress2(merchant.getBankAddress2());
    pgAccount.getPgMerchantBank().setCity(merchant.getBankCity());
    pgAccount.getPgMerchantBank().setState(merchant.getBankState());
    pgAccount.getPgMerchantBank().setCountry(merchant.getBankCountry());
    pgAccount.getPgMerchantBank().setPin(merchant.getBankPin());
    accountDao.addMerchantAccount(pgAccount);
    Response response = new Response();
    response.setErrorCode(PGConstants.SUCCESS);
    response.setErrorMessage("Success");
    return response;
  }

  @Override
  public MerchantDetailsForAccountResponse getMerchantDetailsForAccountCreation(Merchant merchant) {
    List<PGMerchant> pgMerchantsList = merchantDao.getMerchantDetailsForAccountCreation(merchant);
    List<MerchantDetailsForAccountCreate> responseList = null;
    if (StringUtil.isListNotNullNEmpty(pgMerchantsList)) {
      MerchantDetailsForAccountCreate response = null;
      responseList = new ArrayList<MerchantDetailsForAccountCreate>(merchant.getPageSize());
      int listSize = pgMerchantsList.size();
      PGMerchant pgMerchant = null;

      for (int i = 0; i < listSize; i++) {
        response = new MerchantDetailsForAccountCreate();
        pgMerchant = pgMerchantsList.get(i);

        if (pgMerchant.getStatus() == STATUS_SUCCESS) {
          response.setStatus(S_STATUS_ACTIVE);
        } else if (pgMerchant.getStatus() == STATUS_PENDING) {
          response.setStatus(S_STATUS_PENDING);
        } else if (pgMerchant.getStatus() == STATUS_DELETED) {
          response.setStatus(S_STATUS_DELETED);
        } else if (pgMerchant.getStatus() == STATUS_SUSPENDED) {
          response.setStatus(S_STATUS_SUSPENDED);
        } else if (pgMerchant.getStatus() == STATUS_SELF_REGISTERATION_PENDING) {
          response.setStatus(S_STATUS_SELFREGISTERED);
        }
        response.setMerchantCode(pgMerchant.getMerchantCode());
        response.setMerchantName(pgMerchant.getBusinessName());

        responseList.add(response);
      }
    }
    MerchantDetailsForAccountResponse response = new MerchantDetailsForAccountResponse();
    response.setMerchantDetailsList(responseList);
    response.setTotalRecords(merchant.getNoOfRecords());
    return response;
  }



  @Override
  public MerchantAccountSearchResponse searchMerchantAccount(
      MerchantAccountSearchDto merchantAccountSearchDto, Map<String, String> merchantDataMap) {

    MerchantAccountSearchResponse response = new MerchantAccountSearchResponse();
    List<MerchantAccountSearchDto> merchantAccountSearchDtoList =
        accountDao.searchMerchantAccount(merchantAccountSearchDto);

    if (merchantAccountSearchDto.getMerchantType().equals(PGConstants.SUB_MERCHANT)
        && StringUtil.isListNotNullNEmpty(merchantAccountSearchDtoList)) {

      if (null == merchantDataMap) {
        merchantDataMap = getMerchantMapByMerchantType(PGConstants.MERCHANT);
      }
      getMerchantAccountDto(merchantDataMap, merchantAccountSearchDtoList);
    }

    response.setTotalNoOfRows(merchantAccountSearchDto.getNoOfRecords());
    response.setMerchantAccountSearchDtoList(merchantAccountSearchDtoList);
    return response;
  }

private void getMerchantAccountDto(Map<String, String> merchantDataMap,
		List<MerchantAccountSearchDto> merchantAccountSearchDtoList) {
	for (MerchantAccountSearchDto merchantAccountDto : merchantAccountSearchDtoList) {
        if (merchantAccountDto.getParentMerchantCode() != null) {
          String businessName =
              merchantDataMap.get(merchantAccountDto.getParentMerchantCode().toString());
          if (businessName != null) {
            merchantAccountDto.setParentBusinessName(merchantDataMap
                .get(merchantAccountDto.getParentMerchantCode().toString()).split("-")[1].trim());
          }
        }
      }
}

  @Override
  public void changeAccountStatus(MerchantAccountSearchDto merchantAccountSearchDto) {
    accountDao.changeAccountStatus(merchantAccountSearchDto.getAccountId(),
        merchantAccountSearchDto.getAccountStatus(), merchantAccountSearchDto.getReason());
  }

  @Override
  public Merchant getAccount(Merchant reqMerchant) {
    PGAccount pgAccount = accountDao.getAccountOnId(reqMerchant.getAccountId());
    Merchant merchant = null;
    if (null != pgAccount) {
      merchant = new Merchant();
      merchant.setAccountId(pgAccount.getId());
      merchant.setMerchantCode(pgAccount.getEntityId());
      merchant.setMerchantType(pgAccount.getEntityType());
      merchant.setCategory(pgAccount.getCategory());
      merchant.setAutoPaymentMethod(pgAccount.getAutoPaymentMethod());
      merchant.setAccountStatus(pgAccount.getStatus());
      merchant.setBankAccountType(pgAccount.getPgMerchantBank().getAccountType());
      merchant.setBankAccountNumber(pgAccount.getPgMerchantBank().getBankAccNum());
      merchant.setBankRoutingNumber(pgAccount.getPgMerchantBank().getRoutingNumber());
      merchant.setBankAccountName(pgAccount.getPgMerchantBank().getBankName());
      merchant.setBankNameOnAccount(pgAccount.getPgMerchantBank().getNameOnAccount());
      merchant.setBankAddress1(pgAccount.getPgMerchantBank().getAddress1());
      merchant.setBankAddress2(pgAccount.getPgMerchantBank().getAddress2());
      merchant.setBankCity(pgAccount.getPgMerchantBank().getCity());
      merchant.setBankState(pgAccount.getPgMerchantBank().getState());
      merchant.setBankCountry(pgAccount.getPgMerchantBank().getCountry());
      merchant.setBankPin(pgAccount.getPgMerchantBank().getPin());
      merchant.setAutoSettlement(pgAccount.getAutoSettlement());

      if (null != pgAccount.getAutoTransferDay()) {
        if (pgAccount.getAutoTransferDay().startsWith(Constants.DAILY)) {
          merchant.setAutoTransferDay(pgAccount.getAutoTransferDay());
        } else if (pgAccount.getAutoTransferDay().startsWith(Constants.WEEKLY)) {
          merchant.setAutoTransferWeeklyDay(pgAccount.getAutoTransferDay().split(":")[1]);
          merchant.setAutoTransferDay(pgAccount.getAutoTransferDay().split(":")[0]);
        }
        if (pgAccount.getAutoTransferDay().startsWith(Constants.MONTHLY)) {
          merchant.setAutoTransferDay(pgAccount.getAutoTransferDay().split(":")[0]);
          merchant.setAutoTransferMonthlyDay(pgAccount.getAutoTransferDay().split(":")[1]);
        }
      }
      if (null != pgAccount.getAutoPaymentLimit()) {
        merchant.setAutoTransferLimit(pgAccount.getAutoPaymentLimit());
      }
    }
    return merchant;
  }

  @Override
  public void updateMerchantAccount(Merchant merchant) {

    PGAccount pgAccount = accountDao.getAccountOnId(merchant.getAccountId());

    if (null != pgAccount) {
      pgAccount.setCategory(merchant.getCategory());
      pgAccount.setAutoPaymentLimit(
          null != merchant.getAutoTransferLimit() ? merchant.getAutoTransferLimit() : null);
      pgAccount.setAutoPaymentMethod(merchant.getAutoPaymentMethod());
      pgAccount.setUpdatedDate(DateUtil.getCurrentTimestamp());
      pgAccount.setAutoSettlement(merchant.getAutoSettlement());

      if (!StringUtil.isNullAndEmpty(merchant.getAutoTransferDay())) {
        if (merchant.getAutoTransferDay().startsWith(Constants.DAILY)) {
          pgAccount.setAutoTransferDay(merchant.getAutoTransferDay());
        } else if (merchant.getAutoTransferDay().startsWith(Constants.WEEKLY)) {
          pgAccount.setAutoTransferDay(
              merchant.getAutoTransferDay() + ":" + merchant.getAutoTransferWeeklyDay());
        } else if (merchant.getAutoTransferDay().startsWith(Constants.MONTHLY)) {
          pgAccount.setAutoTransferDay(
              merchant.getAutoTransferDay() + ":" + merchant.getAutoTransferMonthlyDay());
        }
      } else {
        pgAccount.setAutoTransferDay(merchant.getAutoTransferDay());
      }

      PGMerchant pgMerchant = merchantUpdateDao.getMerchantByCode(merchant.getMerchantCode());
      PGMerchantConfig pgMerchantConfig =
          merchantUpdateDao.getConfigDetails(pgMerchant.getMerchantConfig());
      pgMerchantConfig.setAutoSettlement(merchant.getAutoSettlement());
      PGMerchantBank merchantBank = new PGMerchantBank();
      pgAccount.setPgMerchantBank(merchantBank);
      pgAccount.getPgMerchantBank().setAccountType(merchant.getBankAccountType());
      pgAccount.getPgMerchantBank().setBankAccNum(merchant.getBankAccountNumber());
      pgAccount.getPgMerchantBank().setRoutingNumber(merchant.getBankRoutingNumber());
      pgAccount.getPgMerchantBank().setBankName(merchant.getBankAccountName());
      pgAccount.getPgMerchantBank().setUpdatedDate(pgAccount.getUpdatedDate());
      pgAccount.getPgMerchantBank().setNameOnAccount(merchant.getBankNameOnAccount());
      pgAccount.getPgMerchantBank().setAddress1(merchant.getBankAddress1());
      pgAccount.getPgMerchantBank().setAddress2(merchant.getBankAddress2());
      pgAccount.getPgMerchantBank().setCity(merchant.getBankCity());
      pgAccount.getPgMerchantBank().setState(merchant.getBankState());
      pgAccount.getPgMerchantBank().setCountry(merchant.getBankCountry());
      pgAccount.getPgMerchantBank().setPin(merchant.getBankPin());
      accountDao.addMerchantAccount(pgAccount);
      merchantUpdateDao.updateAutoSettlement(pgMerchantConfig);
    }
  }

  public void logManualAccountTransaction(PGAccount account, Long amountToTransfer,
      String transactionCode, AccountBalanceDTO accountBalanceDTO) {
    logger.info("Entering:: MerchantServiceImpl:: logManualAccountTransaction method");
    Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());
    PGAccountTransactions pgAccountTransactions = new PGAccountTransactions();
    pgAccountTransactions.setAccountNumber(account.getAccountNum().toString());
    pgAccountTransactions
        .setAccountTransactionId(accountTransactionsDao.generateAccountTransactionId());
    pgAccountTransactions.setCurrentBalance(account.getCurrentBalance());
    pgAccountTransactions.setMerchantCode(account.getEntityId());
    pgAccountTransactions.setProcessedTime(currentTimestamp);
    pgAccountTransactions.setTransactionTime(currentTimestamp);
    pgAccountTransactions.setCreatedDate(currentTimestamp);
    pgAccountTransactions.setTransactionCode(transactionCode);
    pgAccountTransactions.setTransactionType(transactionCode);
    pgAccountTransactions.setStatus(PGConstants.PG_SETTLEMENT_EXECUTED);
    pgAccountTransactions.setTimeZoneOffset(accountBalanceDTO.getTimeZoneOffset());
    pgAccountTransactions.setTimeZoneRegion(accountBalanceDTO.getTimeZoneRegion());
    logger.info("MerchantServiceImpl:: logManualAccountTransaction method::" + transactionCode);
    Timestamp timestamp = new Timestamp(System.currentTimeMillis());
    switch (transactionCode) {
      case AccountTransactionCode.MANUAL_CREDIT:
			pgAccountTransactions.setCredit(amountToTransfer);
			String amount = StringUtils.amountToString(amountToTransfer);
			pgAccountTransactions.setDescription("Manual Credit : " + setTxnDescription(accountBalanceDTO, amount));
			pgAccountTransactions.setDeviceLocalTxnTime(DateUtil.convertTimeZone(accountBalanceDTO.getTimeZoneOffset(), timestamp.toString()));
        break;
      case AccountTransactionCode.MANUAL_DEBIT:
			pgAccountTransactions.setDebit(amountToTransfer);
			String amt = StringUtils.amountToString(amountToTransfer);
			pgAccountTransactions.setDescription("Manual Debit : " + setTxnDescription(accountBalanceDTO, amt));
			pgAccountTransactions.setDeviceLocalTxnTime(DateUtil.convertTimeZone(accountBalanceDTO.getTimeZoneOffset(), timestamp.toString()));
			break;
      default:
        return;
    }
    logger.info("Exiting:: MerchantServiceImpl:: logManualAccountTransaction method");
    accountTransactionsDao.createOrUpdate(pgAccountTransactions);
  }

private String setTxnDescription(AccountBalanceDTO accountBalanceDTO, String amount) {
	return CommonUtil.getCurrencyPattern(amount,
			accountBalanceDTO.getCurrencySeparatorPosition(), accountBalanceDTO.getCurrencyThousandsUnit(),
			accountBalanceDTO.getCurrencyExponent(), accountBalanceDTO.getCurrencyMinorUnit());
}

  @Override
  public Map<String, String> getMerchantMapByMerchantType(String merchantType) {
    List<Map<String, String>> merchantList = merchantDao.getMerchantMapByMerchantType(merchantType);
    Map<String, String> merchantMap = new HashMap<String, String>();
    if (StringUtil.isListNotNullNEmpty(merchantList)) {
      for (Map<String, String> map : merchantList) {
        merchantMap.put(String.valueOf(map.get("0")), map.get("1"));
      }
    }
    return merchantMap;
  }
}
