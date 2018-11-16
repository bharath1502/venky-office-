/**
 * 
 */
package com.chatak.pg.acq.dao.impl;

import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import com.chatak.pg.acq.dao.MerchantUpdateDao;
import com.chatak.pg.acq.dao.model.PGAccount;
import com.chatak.pg.acq.dao.model.PGApplicationClient;
import com.chatak.pg.acq.dao.model.PGMerchant;
import com.chatak.pg.acq.dao.model.PGMerchantBank;
import com.chatak.pg.acq.dao.model.PGMerchantCardProgramMap;
import com.chatak.pg.acq.dao.model.PGMerchantConfig;
import com.chatak.pg.acq.dao.model.PGMerchantEntityMap;
import com.chatak.pg.acq.dao.model.PGMerchantUsers;
import com.chatak.pg.acq.dao.model.PGTerminal;
import com.chatak.pg.acq.dao.model.QPGMerchant;
import com.chatak.pg.acq.dao.model.QPGMerchantEntityMap;
import com.chatak.pg.acq.dao.repository.AccountRepository;
import com.chatak.pg.acq.dao.repository.ApplicationClientRepository;
import com.chatak.pg.acq.dao.repository.MerchantCardProgramMapRepository;
import com.chatak.pg.acq.dao.repository.MerchantConfigRepositrory;
import com.chatak.pg.acq.dao.repository.MerchantEntityMapRepository;
import com.chatak.pg.acq.dao.repository.MerchantRepository;
import com.chatak.pg.acq.dao.repository.MerchantUserRepository;
import com.chatak.pg.acq.dao.repository.TerminalRepository;
import com.chatak.pg.bean.Response;
import com.chatak.pg.constants.ActionErrorCode;
import com.chatak.pg.constants.PGConstants;
import com.chatak.pg.dao.util.StringUtil;
import com.chatak.pg.user.bean.AddMerchantRequest;
import com.chatak.pg.user.bean.AddMerchantResponse;
import com.chatak.pg.user.bean.UpdateMerchantRequest;
import com.chatak.pg.user.bean.UpdateMerchantResponse;
import com.chatak.pg.util.CommonUtil;
import com.chatak.pg.util.Constants;
import com.chatak.pg.util.DateUtil;
import com.chatak.pg.util.Properties;
import com.chatak.pg.util.StringUtils;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;

/**
 * @Author: Girmiti Software
 * @Date: Aug 23, 2017
 * @Time: 2:58:45 PM
 * @Version: 1.0
 * @Comments: 
 *
 */

@Repository("merchantUpdateDao")
public class MerchantUpdateDaoImpl implements MerchantUpdateDao {

  private static Logger logger = Logger.getLogger(MerchantUpdateDaoImpl.class);

  @Autowired
  private AccountRepository accountRepository;

  @Autowired
  private MerchantRepository merchantRepository;

  @Autowired
  private TerminalRepository terminalRepository;

  @Autowired
  private MerchantUserRepository merchantUserRepository;

  @Autowired
  private MerchantConfigRepositrory merchantConfigRepositrory;
  
  @Autowired
  private MerchantEntityMapRepository merchantEntityMapRepository;

  @PersistenceContext
  private EntityManager entityManager;
  
  @Autowired
  private MerchantCardProgramMapRepository merchantCardProgramMapRepository;

  @Autowired
  ApplicationClientRepository applicationClientRepository;

  @Override
  public AddMerchantResponse addMerchant(AddMerchantRequest addMerchantRequest) {
    logger.info("MerchantUpdateDaoImpl | addmerchant | Entering");
    AddMerchantResponse addMerchantResponse = new AddMerchantResponse();
    PGMerchant pgMerchant = null;
    PGMerchant parentMerchant = null;
    PGMerchantUsers pgMerchantUser = null;
    String emailID = addMerchantRequest.getEmailId().toLowerCase();

    if (null != addMerchantRequest.getParentMerchantId()) {
      parentMerchant = merchantRepository.findById(addMerchantRequest.getParentMerchantId()).orElse(null);
    }

    if (null != parentMerchant && null != parentMerchant.getParentMerchantId()) {
      addMerchantResponse.setErrorCode(ActionErrorCode.ERROR_CODE_Z5);
      addMerchantResponse.setErrorMessage(PGConstants.SUB_MERCHANT_CREATE_BLOCK);
      return addMerchantResponse;
    }
    pgMerchant = merchantRepository.findByUserName(addMerchantRequest.getUserName());
    if (pgMerchant != null) {
      addMerchantResponse.setErrorCode(ActionErrorCode.ERROR_CODE_Z8);
      addMerchantResponse
          .setErrorMessage(ActionErrorCode.getInstance().getMessage(ActionErrorCode.ERROR_CODE_Z8));
      return addMerchantResponse;
    }

    Timestamp currentDate = new Timestamp(System.currentTimeMillis());
    Timestamp businessStartDate = DateUtil
        .getStartDayTimestamp(addMerchantRequest.getBusinessStartDate(), DateUtil.VIEW_DATE_FORMAT);
    String merchantCode = generateMerchantCode();

    Integer status = (null != addMerchantRequest.getStatus()) ? addMerchantRequest.getStatus()
        : PGConstants.STATUS_PENDING;
    try {

      PGMerchant merchant = new PGMerchant();
      setPGMerchantDetails(addMerchantRequest, emailID, currentDate, businessStartDate, merchantCode, status, merchant);

      PGMerchantConfig pgMerchantConfig = new PGMerchantConfig();
      setPGMerchantConfig(addMerchantRequest, currentDate, pgMerchantConfig);
      // END

      merchant.setMerchantConfig(pgMerchantConfig);

      List<PGMerchantUsers> pgMerchantUsers = new ArrayList<>();
      pgMerchantUser = new PGMerchantUsers();
      pgMerchantUser.setMerPassword(addMerchantRequest.getPassword());
      pgMerchantUser.setUserName(addMerchantRequest.getUserName());
      pgMerchantUser.setUserType(Constants.SUPERADMIN);
      pgMerchantUser.setStatus(status);
      pgMerchantUser.setUpdatedBy(addMerchantRequest.getCreatedBy());
      pgMerchantUser.setCreatedBy(addMerchantRequest.getCreatedBy());
      pgMerchantUser.setCreatedDate(currentDate);
      pgMerchantUser.setUpdatedDate(currentDate);
      pgMerchantUser.setPassRetryCount(0);
      pgMerchantUser.setEmailVerified(Constants.ZERO);
      pgMerchantUser.setUserRoleId(Long.parseLong("2"));// Setting default MerchantAdmin role
      pgMerchantUser.setFirstName(addMerchantRequest.getFirstName());
      pgMerchantUser.setLastName(addMerchantRequest.getLastName());
      pgMerchantUser.setUserRoleType(addMerchantRequest.getMerchantType());
      pgMerchantUser.setEmail(emailID);
      pgMerchantUser.setPhone("" + addMerchantRequest.getPhone());
      pgMerchantUser.setAddress(addMerchantRequest.getAddress1());
      pgMerchantUsers.add(pgMerchantUser);
      merchant.setPgMerchantUsers(pgMerchantUsers);
      if(addMerchantRequest.getMerchantType() != PGConstants.SUB_MERCHANT){
      //Merchant to Entity Mapping
      Set<PGMerchantEntityMap> pgMerchantEntityMaps = new HashSet<>();
		for(Long id : addMerchantRequest.getEntitiesId()){
			PGMerchantEntityMap merchantEntityMap = new PGMerchantEntityMap();
			merchantEntityMap.setEntityId(id);
			merchantEntityMap.setEntitytype(addMerchantRequest.getAssociatedTo());
			pgMerchantEntityMaps.add(merchantEntityMap);
		}
		merchant.setPgMerchantEntityMaps(pgMerchantEntityMaps);
		//Merchant to CardProgram Mapping
		Set<PGMerchantCardProgramMap> pgMerchantCardProgramMaps = new HashSet<>();
		for(Map.Entry<Long, Long> id : addMerchantRequest.getCardProgramAndEntityId().entrySet()){
			PGMerchantCardProgramMap merchantCardProgramMap = new PGMerchantCardProgramMap();
			merchantCardProgramMap.setCardProgramId(id.getKey());
			merchantCardProgramMap.setEntityId(id.getValue());
			merchantCardProgramMap.setEntitytype(addMerchantRequest.getAssociatedTo());
			pgMerchantCardProgramMaps.add(merchantCardProgramMap);
		}
		merchant.setPgMerchantCardProgramMaps(pgMerchantCardProgramMaps);
      }
      merchant = merchantRepository.save(merchant);

      PGAccount pgAccount = new PGAccount();

      //Inserting into PgApplicationClient table for OAuth Token Related changes
      if (merchant != null) {
        PGApplicationClient applicationClient = com.chatak.pg.dao.util.StringUtil.getApplicationClientDTO();
        applicationClient.setAppClientId(addMerchantRequest.getUserName());
        applicationClient.setAppAuthUser(addMerchantRequest.getUserName());
        applicationClientRepository.save(applicationClient);

        pgAccount.setEntityId(merchant.getMerchantCode());
      }

      merchantRepository.findByUserName(addMerchantRequest.getUserName());
      pgAccount.setAccountDesc(PGConstants.ACC_DESC);
   // Generating Merchant AccountNumber
      String accountNumber = Properties.getProperty("merchant.account.number.series");
      Long accNum = getMerchantAccountNumberSeries(accountNumber);
      pgAccount.setAccountNum(accNum);
      pgAccount.setAutoPaymentLimit(addMerchantRequest.getAutoTransferLimit());

      pgAccount.setAutoPaymentMethod(addMerchantRequest.getAutoPaymentMethod());
      pgAccount.setAutoTransferDay(addMerchantRequest.getAutoTransferDay());
      pgAccount.setAvailableBalance(PGConstants.ZERO);
      pgAccount.setCategory(addMerchantRequest.getCategory());
      pgAccount.setCreatedDate(DateUtil.getCurrentTimestamp());
      pgAccount.setCurrentBalance(PGConstants.ZERO);
      pgAccount.setFeeBalance(PGConstants.ZERO);
      pgAccount.setCurrency(addMerchantRequest.getLocalCurrency());
      pgAccount.setAutoSettlement(addMerchantRequest.getAutoSettlement());
      if (null != parentMerchant) {
        pgAccount.setEntityType(PGConstants.SUB_MERCHANT);
      } else {
        pgAccount.setEntityType(PGConstants.MERCHANT);
      }
      pgAccount.setStatus(StringUtils.getStringStatusLiteral(status.toString()));

      PGMerchantBank pgMerchantBank = new PGMerchantBank();
      pgAccount.setPgMerchantBank(pgMerchantBank);
      pgAccount.getPgMerchantBank().setBankAccNum(addMerchantRequest.getBankAccountNumber());
      pgAccount.getPgMerchantBank().setAccountType(addMerchantRequest.getBankAccountType());
      pgAccount.getPgMerchantBank().setRoutingNumber(addMerchantRequest.getBankRoutingNumber());
      pgAccount.getPgMerchantBank().setBankName(addMerchantRequest.getBankName());
      pgAccount.getPgMerchantBank().setBankCode(addMerchantRequest.getBankCode());
      pgAccount.getPgMerchantBank().setCreatedBy(Long.valueOf(addMerchantRequest.getCreatedBy()));
      pgAccount.getPgMerchantBank().setCurrencyCode(addMerchantRequest.getBankCurrencyCode()); // Need to change later
      pgAccount.getPgMerchantBank().setCreatedDate(DateUtil.getCurrentTimestamp());
      pgAccount.getPgMerchantBank().setMerchantId(getMerchantCode(merchant));
      pgAccount.getPgMerchantBank().setStatus(addMerchantRequest.getBankStatus());
      pgAccount.getPgMerchantBank().setUpdatedDate(pgAccount.getPgMerchantBank().getCreatedDate());
      pgAccount.getPgMerchantBank().setNameOnAccount(addMerchantRequest.getBankNameOnAccount());
      pgAccount.getPgMerchantBank().setAddress1(addMerchantRequest.getBankAddress1());
      pgAccount.getPgMerchantBank().setAddress2(addMerchantRequest.getBankAddress2());
      pgAccount.getPgMerchantBank().setCity(addMerchantRequest.getBankCity());
      pgAccount.getPgMerchantBank().setCountry(addMerchantRequest.getBankCountry());
      pgAccount.getPgMerchantBank().setPin(addMerchantRequest.getBankPin());
      pgAccount.getPgMerchantBank().setState(addMerchantRequest.getBankState());

      pgAccount = accountRepository.save(pgAccount);

      PGTerminal pgTerminal = new PGTerminal();
      pgTerminal.setComments(addMerchantRequest.getUserName());
      if(merchant!=null){
      pgTerminal.setMerchantId(merchant.getId());
       }
      pgTerminal.setStatus(PGConstants.STATUS_SUCCESS);
      int length = merchant.getMerchantCode().length();
      pgTerminal
          .setTerminalId(Long.valueOf(merchant.getMerchantCode().substring(length - Integer.parseInt("8"), length)));
      pgTerminal.setCreatedBy(null != addMerchantRequest.getCreatedBy()
          ? addMerchantRequest.getCreatedBy() : "Self register");
      pgTerminal.setCreatedDate(currentDate);
      terminalRepository.save(pgTerminal);

      logger.info("MerchantUpdateDaoImpl | addmerchant | Merchant details inserted successfully");
      addMerchantResponse.setMerchantCode(merchant.getMerchantCode());
      addMerchantResponse.setAccNum(pgAccount.getAccountNum());
      addMerchantResponse.setErrorCode(ActionErrorCode.ERROR_CODE_00);
      addMerchantResponse
          .setErrorMessage(ActionErrorCode.getInstance().getMessage(ActionErrorCode.ERROR_CODE_00));
      addMerchantResponse.setPassword(addMerchantRequest.getPassword());
    } catch (Exception e) {
      logger.error("MerchantUpdateDaoImpl | addmerchant | Exception" + e);
      addMerchantResponse.setErrorCode(ActionErrorCode.ERROR_CODE_Z5);
      addMerchantResponse
          .setErrorMessage(ActionErrorCode.getInstance().getMessage(ActionErrorCode.ERROR_CODE_Z5));
    }

    logger.info("MerchantUpdateDaoImpl | addmerchant | Exiting");
    return addMerchantResponse;
  }
  
	/**
	 * @param merchant
	 * @return
	 */
	private String getMerchantCode(PGMerchant merchant) {
		return merchant.getMerchantCode();
	}

  private void setPGMerchantConfig(AddMerchantRequest addMerchantRequest, Timestamp currentDate,
		PGMerchantConfig pgMerchantConfig) {
	  pgMerchantConfig.setCreatedDate(currentDate);
      pgMerchantConfig.setFeeProgram(addMerchantRequest.getFeeProgram());
      pgMerchantConfig.setProcessor(addMerchantRequest.getProcessor());
      pgMerchantConfig.setRefunds(
          null != addMerchantRequest.getRefunds() && addMerchantRequest.getRefunds() ? 1 : 0);
      pgMerchantConfig.setShippingAmount(
          null != addMerchantRequest.getShippingAmount() && addMerchantRequest.getShippingAmount()
              ? 1 : 0);
      pgMerchantConfig.setTaxAmount(
          null != addMerchantRequest.getTaxAmount() && addMerchantRequest.getTaxAmount() ? 1 : 0);
      pgMerchantConfig.setTipAmount(
          null != addMerchantRequest.getTipAmount() && addMerchantRequest.getTipAmount() ? 1 : 0);
      pgMerchantConfig.setAutoSettlement(addMerchantRequest.getAutoSettlement());
      pgMerchantConfig.setUpdatedDate(currentDate);

      setPGMerchantConfigDetails(addMerchantRequest, pgMerchantConfig);
  }

  private void setPGMerchantConfigDetails(AddMerchantRequest addMerchantRequest, PGMerchantConfig pgMerchantConfig) {
	// NEW ADDED FIELDS
      pgMerchantConfig.setVirtualTerminal(
          null != addMerchantRequest.getVirtualTerminal() && addMerchantRequest.getVirtualTerminal()
              ? 1 : 0);
      pgMerchantConfig.setPosTerminal(
          null != addMerchantRequest.getPosTerminal() && addMerchantRequest.getPosTerminal() ? 1
              : 0);
      pgMerchantConfig.setOnline(
          null != addMerchantRequest.getOnline() && addMerchantRequest.getOnline() ? 1 : 0);
      pgMerchantConfig.setWebSiteAddress(addMerchantRequest.getWebSiteAddress());
      pgMerchantConfig.setReturnUrl(addMerchantRequest.getReturnUrl());
      pgMerchantConfig.setCancelUrl(addMerchantRequest.getCancelUrl());
      pgMerchantConfig.setPayPageConfig(
          null != addMerchantRequest.getPayPageConfig() && addMerchantRequest.getPayPageConfig() ? 1
              : 0);
      pgMerchantConfig.setPayOutAt(addMerchantRequest.getPayOutAt());
  }

  private void setPGMerchantDetails(AddMerchantRequest addMerchantRequest, String emailID, Timestamp currentDate,
		Timestamp businessStartDate, String merchantCode, Integer status, PGMerchant merchant) {
	merchant.setAddress1(CommonUtil.isNullAndEmpty(addMerchantRequest.getAddress1()) ? null
          : addMerchantRequest.getAddress1());
      merchant.setCity(CommonUtil.isNullAndEmpty(addMerchantRequest.getCity()) ? null
              : addMerchantRequest.getCity());
      merchant.setAddress2(CommonUtil.isNullAndEmpty(addMerchantRequest.getAddress2()) ? null
          : addMerchantRequest.getAddress2());
      merchant.setCountry(addMerchantRequest.getCountry());
      merchant.setPhone(addMerchantRequest.getPhone());
      merchant.setCreatedDate(currentDate);
      merchant.setFax(addMerchantRequest.getFax());
      merchant.setStatus(status);
      merchant.setState(CommonUtil.isNullAndEmpty(addMerchantRequest.getState()) ? null
          : addMerchantRequest.getState());
      merchant.setUpdatedDate(currentDate);
      merchant.setBusinessName(addMerchantRequest.getBusinessName());
      merchant.setAppMode(addMerchantRequest.getAppMode());
      merchant.setBusinessURL(addMerchantRequest.getBusinessURL());
      merchant.setBusinessStartDate(businessStartDate);
      merchant.setEmailId(emailID);
      merchant.setEstimatedYearlySale(addMerchantRequest.getEstimatedYearlySale());
      merchant.setFirstName(addMerchantRequest.getFirstName());
      merchant.setFederalTaxId(addMerchantRequest.getFederalTaxId());
      merchant.setLastName(addMerchantRequest.getLastName());
      merchant.setOwnership(addMerchantRequest.getOwnership());
      merchant.setNoOfEmployee(addMerchantRequest.getNoOfEmployee());
      merchant.setPin(addMerchantRequest.getPin());
      merchant.setSalesTaxId(addMerchantRequest.getSalesTaxId());
      merchant.setRole(addMerchantRequest.getRole());
      merchant.setStateTaxId(addMerchantRequest.getStateTaxId());
      merchant.setTimeZone(addMerchantRequest.getTimeZone());
      merchant.setUserName(addMerchantRequest.getUserName());
      merchant.setMerchantCode(merchantCode);
      merchant.setMerchantCallBack(addMerchantRequest.getMerchantCallBackURL());
      merchant.setMerchantType(addMerchantRequest.getMerchantType());
      merchant.setLitleMID(addMerchantRequest.getLitleMID());
      merchant.setParentMerchantId(addMerchantRequest.getParentMerchantId());
      merchant.setBusinessType(addMerchantRequest.getBusinessType());
      merchant.setLookingFor(addMerchantRequest.getLookinFor());
      merchant.setAgentId(addMerchantRequest.getAgentId());
      merchant.setMerchantCategory(addMerchantRequest.getMerchantCategory());
      merchant.setAllowAdvancedFraudFilter(null != addMerchantRequest.getAllowAdvancedFraudFilter()
          && addMerchantRequest.getAllowAdvancedFraudFilter() ? 1 : 0);
      merchant.setBankId(addMerchantRequest.getBankId());
      merchant.setResellerId(addMerchantRequest.getResellerId());
      merchant.setCreatedBy(addMerchantRequest.getCreatedBy());
      merchant.setDccEnable(addMerchantRequest.getDccEnable());
      merchant.setMcc(addMerchantRequest.getMcc());
      merchant.setLocalCurrency(addMerchantRequest.getLocalCurrency());
      merchant.setAgentAccountNumber(addMerchantRequest.getAgentAccountNumber());
      merchant.setAgentANI(addMerchantRequest.getAgentANI());
      merchant.setAgentClientId(addMerchantRequest.getAgentClientId());
      merchant.setAgentId(addMerchantRequest.getAgentId());
  }

  @Override
  public UpdateMerchantResponse updateMerchant(UpdateMerchantRequest updateMerchantRequest) {
    logger.info("Entering:: MerchantController:: updateMerchant method");
    UpdateMerchantResponse updateMerchantResponse = new UpdateMerchantResponse();
    PGMerchant merchantDb =
        merchantRepository.findByMerchantCode(updateMerchantRequest.getMerchantCode());

    statusValidation(updateMerchantRequest, updateMerchantResponse, merchantDb);
    if (!merchantDb.getUserName().equalsIgnoreCase(updateMerchantRequest.getUserName())) {
      PGMerchant merchantDb1 =
          merchantRepository.findByUserName(updateMerchantRequest.getUserName());
      if (merchantDb1 != null && !merchantDb.getUserName().equals(merchantDb1.getUserName())) {
        logger.error("Error:: MerchantController:: updateMerchant method");
        updateMerchantResponse.setErrorCode(ActionErrorCode.ERROR_CODE_Z8);
        updateMerchantResponse.setErrorMessage(
            ActionErrorCode.getInstance().getMessage(ActionErrorCode.ERROR_CODE_Z8));
        return updateMerchantResponse;
      }
    }
    if (null == merchantDb.getParentMerchantId()
        && (PGConstants.STATUS_SUCCESS != updateMerchantRequest.getStatus())) {
      validateParentMerchantId(updateMerchantRequest, merchantDb);
    }
    PGAccount pgAccount = accountRepository.findByEntityIdAndCategory(merchantDb.getMerchantCode(),
        PGConstants.PRIMARY_ACCOUNT);
    if (null != pgAccount) {
      setPGMerchantAndPGAccount(updateMerchantRequest, updateMerchantResponse, merchantDb, pgAccount);
    } else {
      updateMerchantResponse.setUpdated(false);
      updateMerchantResponse.setErrorCode(ActionErrorCode.ERROR_CODE_Z5);
      updateMerchantResponse
          .setErrorMessage(ActionErrorCode.getInstance().getMessage(ActionErrorCode.ERROR_CODE_Z5));
      logger.error("Error:: MerchantController:: updateMerchant method");
    }
    return updateMerchantResponse;
  }

  private void setPGMerchantAndPGAccount(UpdateMerchantRequest updateMerchantRequest,
		UpdateMerchantResponse updateMerchantResponse, PGMerchant merchantDb, PGAccount pgAccount) {
	merchantDb.setAddress1(updateMerchantRequest.getAddress1());
      merchantDb.setAddress2(updateMerchantRequest.getAddress2());
      merchantDb.setAppMode(updateMerchantRequest.getAppMode());
      merchantDb.setBusinessName(updateMerchantRequest.getBusinessName());
      merchantDb.setBusinessURL(updateMerchantRequest.getBusinessURL());
      merchantDb.setCity(updateMerchantRequest.getCity());
      merchantDb.setCountry(updateMerchantRequest.getCountry());
      merchantDb.setEmailId(updateMerchantRequest.getEmailId());
      merchantDb.getPgMerchantUsers().get(0).setEmail(updateMerchantRequest.getEmailId());
      merchantDb.setEstimatedYearlySale(updateMerchantRequest.getEstimatedYearlySale());
      merchantDb.setFax(updateMerchantRequest.getFax());
      merchantDb.setFederalTaxId(updateMerchantRequest.getFederalTaxId());
      merchantDb.setFirstName(updateMerchantRequest.getFirstName());
      merchantDb.setLastName(updateMerchantRequest.getLastName());
      merchantDb.setNoOfEmployee(updateMerchantRequest.getNoOfEmployee());
      merchantDb.setOwnership(updateMerchantRequest.getOwnership());
      merchantDb.setPhone(updateMerchantRequest.getPhone());
      merchantDb.setPin(updateMerchantRequest.getPin());
      merchantDb.setRole(updateMerchantRequest.getRole());
      merchantDb.setSalesTaxId(updateMerchantRequest.getSalesTaxId());
      merchantDb.setState(updateMerchantRequest.getState());
      merchantDb.setStateTaxId(updateMerchantRequest.getStateTaxId());
      merchantDb.setStatus(updateMerchantRequest.getStatus() == null ? merchantDb.getStatus()
          : updateMerchantRequest.getStatus());
      merchantDb.setTimeZone(updateMerchantRequest.getTimeZone());
      merchantDb.setUpdatedDate(DateUtil.getCurrentTimestamp());
      merchantDb.setUserName(updateMerchantRequest.getUserName());
      merchantDb.setMerchantCallBack(updateMerchantRequest.getMerchantCallBackURL());
      merchantDb.setMerchantCode(updateMerchantRequest.getMerchantCode());
      merchantDb.setMerchantCategory(updateMerchantRequest.getMerchantCategory());
      merchantDb.setDccEnable(updateMerchantRequest.getDccEnable());
      merchantDb.setLocalCurrency(updateMerchantRequest.getLocalCurrency());
      merchantDb.setDeclineReason(updateMerchantRequest.getDeclineReason() == null
          ? merchantDb.getDeclineReason() : updateMerchantRequest.getDeclineReason());
      if (null != merchantDb.getPgMerchantUsers().get(0)) {
        merchantDb.getPgMerchantUsers().get(0).setStatus(updateMerchantRequest.getStatus() == null
            ? merchantDb.getStatus() : updateMerchantRequest.getStatus());
      }
      merchantDb.setLitleMID(updateMerchantRequest.getLitleMID());
      merchantDb.setBusinessType(updateMerchantRequest.getBusinessType());
      merchantDb.setLookingFor(updateMerchantRequest.getLookingFor());
      merchantDb.setAgentId(updateMerchantRequest.getAgentId());
      merchantDb
          .setAllowAdvancedFraudFilter(null != updateMerchantRequest.getAllowAdvancedFraudFilter()
              && updateMerchantRequest.getAllowAdvancedFraudFilter() ? 1 : 0);
      merchantDb.setBankId(updateMerchantRequest.getBankId());
      merchantDb.setResellerId(updateMerchantRequest.getResellerId());
      merchantDb.setMcc(updateMerchantRequest.getMcc());
      merchantDb.setAgentAccountNumber(updateMerchantRequest.getAgentAccountNumber());
      merchantDb.setAgentANI(updateMerchantRequest.getAgentANI());
      merchantDb.setAgentClientId(updateMerchantRequest.getAgentClientId());
      merchantDb.setAgentId(updateMerchantRequest.getAgentId());

      PGMerchantConfig merchantConfig = new PGMerchantConfig();
      merchantConfig.setAutoSettlement(updateMerchantRequest.getAutoSettlement());
      merchantConfig.setCreatedDate(merchantDb.getMerchantConfig().getCreatedDate());
      merchantConfig.setFeeProgram(updateMerchantRequest.getFeeProgram());
      merchantConfig.setId(merchantDb.getMerchantConfig().getId());
      merchantConfig.setProcessor(updateMerchantRequest.getProcessor());
      merchantConfig.setRefunds(updateRefundRequest(updateMerchantRequest));
      merchantConfig.setShippingAmount(updateShippingAmountRequest(updateMerchantRequest));
      merchantConfig.setTaxAmount(updateMerchantRequest.getTaxAmount() ? 1 : 0);
      merchantConfig.setTipAmount(updateMerchantRequest.getTipAmount() ? 1 : 0);
      merchantConfig.setUpdatedDate(DateUtil.getCurrentTimestamp());

      // NEW ADDED Support Terminals
      validateTerminals(updateMerchantRequest, merchantConfig);
      merchantConfig.setWebSiteAddress(updateMerchantRequest.getWebSiteAddress());
      merchantConfig.setReturnUrl(updateMerchantRequest.getReturnUrl());
      merchantConfig.setCancelUrl(updateMerchantRequest.getCancelUrl());
      if (updateMerchantRequest.getPayPageConfig() != null) {
        merchantConfig.setPayPageConfig(updateMerchantRequest.getPayPageConfig() ? 1 : 0);// updating pay page config
      }
      merchantConfig.setPayOutAt(updateMerchantRequest.getPayOutAt());
      // END

      merchantDb.setMerchantConfig(merchantConfig);
      merchantDb.setPgMerchantUsers(merchantDb.getPgMerchantUsers());
      merchantDb.getPgMerchantUsers().get(0).setStatus(updateMerchantRequest.getStatus());

      pgAccount.setAutoPaymentLimit(updateMerchantRequest.getAutoTransferLimit());
      pgAccount.setAutoPaymentMethod(updateMerchantRequest.getAutoPaymentMethod());
      pgAccount.setAutoTransferDay(updateMerchantRequest.getAutoTransferDay());
      pgAccount.setCategory(updateMerchantRequest.getCategory());
      pgAccount.setAutoSettlement(updateMerchantRequest.getAutoSettlement());
      pgAccount.setCreatedDate(pgAccount.getCreatedDate());
      pgAccount.setUpdatedDate(DateUtil.getCurrentTimestamp());
      pgAccount.setStatus(StringUtils.getStringStatusLiteral((updateMerchantRequest.getStatus() == null ? merchantDb.getStatus()
          : updateMerchantRequest.getStatus()).toString()));

      PGMerchantBank pgMerchantBank = pgAccount.getPgMerchantBank();

      pgMerchantBank.setAccountType(updateMerchantRequest.getBankAccountType());
      pgMerchantBank.setBankAccNum(updateMerchantRequest.getBankAccountNumber());
      pgMerchantBank.setRoutingNumber(updateMerchantRequest.getBankRoutingNumber());
      pgMerchantBank.setBankCode(updateMerchantRequest.getBankCode());
      pgMerchantBank.setBankName(updateMerchantRequest.getBankName());
      pgMerchantBank.setCreatedDate(DateUtil.getCurrentTimestamp());
      pgMerchantBank.setCurrencyCode(updateMerchantRequest.getBankCurrencyCode()); // Need to change later
      pgMerchantBank.setMerchantId(updateMerchantRequest.getMerchantCode());
      pgMerchantBank.setUpdatedDate(pgAccount.getPgMerchantBank().getCreatedDate());
      pgMerchantBank.setNameOnAccount(updateMerchantRequest.getBankNameOnAccount());
      pgMerchantBank.setAddress1(updateMerchantRequest.getBankAddress1());
      pgMerchantBank.setAddress2(updateMerchantRequest.getBankAddress2());
      pgMerchantBank.setCity(updateMerchantRequest.getBankCity());
      pgMerchantBank.setState(updateMerchantRequest.getBankState());
      pgMerchantBank.setCountry(updateMerchantRequest.getBankCountry());
      pgMerchantBank.setPin(updateMerchantRequest.getBankPin());
      
		if (updateMerchantRequest.getMerchantType() != PGConstants.SUB_MERCHANT
				&& updateMerchantRequest.getProcess().equals(PGConstants.UPDATE)) {
			processUpdateMerchant(updateMerchantRequest, merchantDb);
		}
		merchantRepository.save(merchantDb);
		accountRepository.save(pgAccount);
		validateStatus(updateMerchantResponse, merchantDb);
		updateMerchantResponse.setUpdated(true);
		updateMerchantResponse.setErrorCode(ActionErrorCode.ERROR_CODE_00);
		updateMerchantResponse.setErrorMessage(ActionErrorCode.getInstance().getMessage(ActionErrorCode.ERROR_CODE_00));
	}

	/**
	 * @param updateMerchantRequest
	 * @return
	 */
	private int updateShippingAmountRequest(UpdateMerchantRequest updateMerchantRequest) {
		return updateMerchantRequest.getShippingAmount() ? 1 : 0;
	}

	/**
	 * @param updateMerchantRequest
	 * @return
	 */
	private int updateRefundRequest(UpdateMerchantRequest updateMerchantRequest) {
		return updateMerchantRequest.getRefunds() ? 1 : 0;
	}

	private void processUpdateMerchant(UpdateMerchantRequest updateMerchantRequest, PGMerchant merchantDb) {
		// Delete existing references
		merchantEntityMapRepository.deleteMerchantEntityMapByMerchantId(updateMerchantRequest.getId());
		merchantCardProgramMapRepository.deleteMerchantCpMapByMerchantId(updateMerchantRequest.getId());
		// UpdateMerchant to Entity Mapping
		Set<PGMerchantEntityMap> pgMerchantEntityMaps = new HashSet<>();
		for (Long id : updateMerchantRequest.getEntitiesId()) {
			PGMerchantEntityMap merchantEntityMap = new PGMerchantEntityMap();
			merchantEntityMap.setEntityId(id);
			merchantEntityMap.setEntitytype(updateMerchantRequest.getAssociatedTo());
			pgMerchantEntityMaps.add(merchantEntityMap);
		}
		merchantDb.setPgMerchantEntityMaps(pgMerchantEntityMaps);
		// UpdateMerchant to CardProgram Mapping
		Set<PGMerchantCardProgramMap> pgMerchantCardProgramMaps = new HashSet<>();
		
		for (Map.Entry<Long, Long> id : updateMerchantRequest.getCardProgramAndEntityId().entrySet()) {
			PGMerchantCardProgramMap merchantCardProgramMap = new PGMerchantCardProgramMap();
			merchantCardProgramMap.setCardProgramId(id.getKey());
			merchantCardProgramMap.setEntityId(id.getValue());
			merchantCardProgramMap.setEntitytype(updateMerchantRequest.getAssociatedTo());
			pgMerchantCardProgramMaps.add(merchantCardProgramMap);
		}
		merchantDb.setPgMerchantCardProgramMaps(pgMerchantCardProgramMaps);
	}

  private void validateStatus(UpdateMerchantResponse updateMerchantResponse, PGMerchant merchantDb) {
	if (null != merchantDb.getStatus() && merchantDb.getStatus().equals(Integer.parseInt("3"))) {
        updateMerchantResponse.setDeclined(true);
      } else {
        updateMerchantResponse.setDeclined(false);
      }
  }

  private void statusValidation(UpdateMerchantRequest updateMerchantRequest,
		UpdateMerchantResponse updateMerchantResponse, PGMerchant merchantDb) {
	if ((PGConstants.STATUS_PENDING.equals(merchantDb.getStatus())
        || PGConstants.STATUS_SELF_REGISTERATION_PENDING.equals(merchantDb.getStatus()))
        && PGConstants.STATUS_SUCCESS.equals(updateMerchantRequest.getStatus())) {
      merchantDb.getPgMerchantUsers().get(0)
          .setMerPassword(updateMerchantRequest.getUserPassword());
      updateMerchantResponse.setPassword(updateMerchantRequest.getUserPassword());
    }
  }

  private void validateTerminals(UpdateMerchantRequest updateMerchantRequest, PGMerchantConfig merchantConfig) {
	if (updateMerchantRequest.getVirtualTerminal() != null) {
        merchantConfig.setVirtualTerminal(updateMerchantRequest.getVirtualTerminal() ? 1 : 0);
      }
      if (updateMerchantRequest.getPosTerminal() != null) {
        merchantConfig.setPosTerminal(updateMerchantRequest.getPosTerminal() ? 1 : 0);
      }
      if (updateMerchantRequest.getOnline() != null) {
        merchantConfig.setOnline(updateMerchantRequest.getOnline() ? 1 : 0);
      }
  }

  private void validateParentMerchantId(UpdateMerchantRequest updateMerchantRequest, PGMerchant merchantDb) {
	List<PGMerchant> subMerchantLists =
          merchantRepository.findByParentMerchantId(merchantDb.getId());
      if (CommonUtil.isListNotNullAndEmpty(subMerchantLists)) {
        for (PGMerchant subMerchant : subMerchantLists) {
          PGAccount pgSubMerchantAccount = accountRepository.findByEntityIdAndCategory(
              subMerchant.getMerchantCode(), PGConstants.PRIMARY_ACCOUNT);
          if (null != pgSubMerchantAccount) {
            validatePGMerchant(updateMerchantRequest, merchantDb, subMerchant, pgSubMerchantAccount);
          }
        }
      }
  }

  private void validatePGMerchant(UpdateMerchantRequest updateMerchantRequest, PGMerchant merchantDb,
		PGMerchant subMerchant, PGAccount pgSubMerchantAccount) {
	if (PGConstants.STATUS_DELETED != subMerchant.getStatus()
	    && PGConstants.STATUS_INACTIVE != subMerchant.getStatus()) {
	  subMerchant.setStatus(updateMerchantRequest.getStatus());
	  subMerchant.setUpdatedDate(DateUtil.getCurrentTimestamp());
	  subMerchant.getPgMerchantUsers().get(0)
	      .setStatus(updateMerchantRequest.getStatus() == null ? merchantDb.getStatus()
	          : updateMerchantRequest.getStatus());
	}
	merchantRepository.save(subMerchant);
	accountRepository.save(pgSubMerchantAccount);
	logger.info("MerchantUpdateDaoImpl:: showCreateMerchantPage method");
  }

  @Override
  public boolean updateMerchantProfile(PGMerchant pgMerchant) {
    logger.info("Entering:: MerchantController:: updateMerchantProfile method");
    boolean response = false;
    PGMerchant merchantDb = merchantRepository.findById(pgMerchant.getId()).orElse(null);
    if (null != merchantDb) {
      merchantDb.setAddress1(pgMerchant.getAddress1());
      merchantDb.setAddress2(pgMerchant.getAddress2());
      merchantDb.setAppMode(pgMerchant.getAppMode());
      merchantDb.setBusinessName(pgMerchant.getBusinessName());
      merchantDb.setBusinessURL(pgMerchant.getBusinessURL());
      merchantDb.setCity(pgMerchant.getCity());
      merchantDb.setCountry(pgMerchant.getCountry());
      merchantDb.setEmailId(pgMerchant.getEmailId());
      merchantDb.setEstimatedYearlySale(pgMerchant.getEstimatedYearlySale());
      merchantDb.setFax(pgMerchant.getFax());
      merchantDb.setFederalTaxId(pgMerchant.getFederalTaxId());
      merchantDb.setFirstName(pgMerchant.getFirstName());
      merchantDb.setLastName(pgMerchant.getLastName());
      merchantDb.setNoOfEmployee(pgMerchant.getNoOfEmployee());
      merchantDb.setOwnership(pgMerchant.getOwnership());
      merchantDb.setPhone(pgMerchant.getPhone());
      merchantDb.setPin(pgMerchant.getPin());
      merchantDb.setRole(pgMerchant.getRole());
      merchantDb.setSalesTaxId(pgMerchant.getSalesTaxId());
      merchantDb.setState(pgMerchant.getState());
      merchantDb.setStateTaxId(pgMerchant.getStateTaxId());
      merchantDb.setStatus(pgMerchant.getStatus());
      merchantDb.setTimeZone(pgMerchant.getTimeZone());
      merchantDb.setUpdatedDate(DateUtil.getCurrentTimestamp());
      merchantDb.setUserName(pgMerchant.getUserName());
      merchantDb.setMerchantCode(pgMerchant.getMerchantCode());
      merchantDb.setAgentAccountNumber(pgMerchant.getAgentAccountNumber());
      merchantDb.setAgentANI(pgMerchant.getAgentANI());
      merchantDb.setAgentClientId(pgMerchant.getAgentClientId());

      PGMerchantConfig merchantConfig1 = pgMerchant.getMerchantConfig();
      merchantConfig1.setId(merchantDb.getMerchantConfig().getId());
      merchantConfig1.setUpdatedDate(DateUtil.getCurrentTimestamp());
      merchantConfig1.setCreatedDate(merchantDb.getMerchantConfig().getCreatedDate());
      merchantDb.setMerchantConfig(merchantConfig1);

      merchantDb.setPgMerchantUsers(merchantDb.getPgMerchantUsers());
      merchantRepository.save(merchantDb);
      response = true;

    }
    logger.info("Exiting:: MerchantController:: updateMerchantProfile method");
    return response;
  }

  @Override
  public PGMerchantConfig updateAutoSettlement(PGMerchantConfig pgMerchantConfig) {
    return merchantConfigRepositrory.save(pgMerchantConfig);
  }

  @Override
  public PGMerchant createOrUpdateMerchant(PGMerchant pgMerchant) {
    PGAccount pgAccount = accountRepository.findByEntityIdAndCategory(pgMerchant.getMerchantCode(),
        PGConstants.PRIMARY_ACCOUNT);
    pgAccount.setStatus(StringUtils.getStringStatusLiteral(pgMerchant.getStatus().toString()));
    accountRepository.save(pgAccount);
    return merchantRepository.save(pgMerchant);
  }

  @Override
  public PGMerchantUsers createOrUpdateMerchantUsers(PGMerchantUsers pgMerchantUsers) {
    return merchantUserRepository.save(pgMerchantUsers);
  }

  @Override
  public PGAccount createOrUpdateAccouunt(PGAccount pgAccount) {
    return accountRepository.save(pgAccount);
  }

  private String generateMerchantCode() {

    String merchantCode = CommonUtil.generateRandNumeric(PGConstants.LENGTH_MERCHANT_ID);
    // restricting merchant code starting with 0
    while (merchantCode.startsWith("0")) {
      merchantCode = CommonUtil.generateRandNumeric(PGConstants.LENGTH_MERCHANT_ID);
    }
    PGMerchant response = getMerchant(merchantCode);

    if (response != null) {
      return generateMerchantCode();
    }
    return merchantCode;
  }

  public PGMerchant getMerchant(String merchantCode) {
    return merchantRepository.findByMerchantCode(merchantCode);
  }

  public Long generateAccountNum() {
    String accountNum = CommonUtil.generateRandNumeric(PGConstants.LENGTH_MER_ACC_NUM);
    PGAccount response = getAccount(Long.valueOf(accountNum));
    if (response != null) {
      return generateAccountNum();
    }
    return Long.valueOf(accountNum);
  }

  private PGAccount getAccount(Long accountNum) {
    return accountRepository.findByAccountNum(accountNum);
  }

  @Override
  public PGMerchant findByEmailId(String emailId) {
    return merchantRepository.findByEmailIdAndStatusNotLike(emailId, PGConstants.STATUS_DELETED);
  }

  @Override
  public String getMerchantCompanyName(String merchantCode) {
    return merchantRepository.getMerchantBusinessNameOnMerchantCode(merchantCode);
  }

  @Override
  public List<PGMerchant> getMerchantsByStatus(Integer status) {
    return merchantRepository.findByStatus(status);
  }

  @Override
  public List<Map<String, String>> getMerchantList() {
    return merchantRepository.getMerchantList();
  }

  @Override
  public PGMerchant getMerchantByCode(String merchantCode) {
    return merchantRepository.findByMerchantCode(merchantCode);
  }

  @Override
  public List<PGMerchant> getMerchantByStatusPendingandDecline() {
    JPAQuery<PGMerchant> query = new JPAQuery<PGMerchant>(entityManager);
    return query.from(QPGMerchant.pGMerchant).select(QPGMerchant.pGMerchant)
        .where(isMerchantNotEq(), QPGMerchant.pGMerchant.status.in(1, Integer.parseInt("4")))
        .fetch();
  }

  @Override
  public PGMerchantConfig getConfigDetails(PGMerchantConfig pgMerchantConfig) {
    return merchantConfigRepositrory.findById(pgMerchantConfig.getId()).orElse(null);
  }

  @Override
  public List<PGMerchant> findByBankId(Long bankId) {
    return merchantRepository.findByBankId(bankId);
  }

  @Override
  public List<PGMerchant> findByParentMerchantIdAndStatus(Long merchantId, Integer status) {
    return merchantRepository.findByParentMerchantIdAndStatus(merchantId, status);
  }

  @Override
  public List<PGAccount> findByEntityIdAndStatusNotLike(String entityId, String status) {
    return accountRepository.findByEntityIdAndStatusNotLike(entityId, status);
  }

  @Override
  public List<PGMerchant> findByMcc(String merchantCategoryCode) {
    return merchantRepository.findByMcc(merchantCategoryCode);
  }

  @Override
  public Response getUserByUserName(String userName) {
    PGMerchant merchant = merchantRepository.findByUserName(userName);
    Response response = new Response();
    if (merchant != null) {
      response.setErrorCode(ActionErrorCode.ERROR_CODE_DUPLICATE_ENTRY);
      response.setErrorMessage(
          ActionErrorCode.getInstance().getMessage(ActionErrorCode.ERROR_CODE_DUPLICATE_ENTRY));
      return response;
    }
    PGMerchantUsers merchantUsers =
        merchantUserRepository.findByUserNameAndStatusNotLike(userName, PGConstants.STATUS_DELETED);
    if (merchantUsers != null) {
      response.setErrorCode(ActionErrorCode.ERROR_CODE_DUPLICATE_ENTRY);
      response.setErrorMessage(
          ActionErrorCode.getInstance().getMessage(ActionErrorCode.ERROR_CODE_DUPLICATE_ENTRY));
      return response;
    } else {
      response.setErrorCode(ActionErrorCode.ERROR_CODE_00);
      response
          .setErrorMessage(ActionErrorCode.getInstance().getMessage(ActionErrorCode.ERROR_CODE_00));
      return response;
    }
  }

  protected BooleanExpression isMerchantNotEq() {
    return (QPGMerchant.pGMerchant.merchantType.ne("TEST"));
  }
  
	@Override
	public Long getMerchantAccountNumberSeries(String accountNumber) throws DataAccessException {
		Long serialNumber = Long.valueOf(accountNumber);
		Query qry = entityManager
				.createNativeQuery("SELECT IFNULL( MAX( ACCOUNT_NUM ), :accountNumber ) + 1 FROM PG_ACCOUNT");
		qry.setParameter("accountNumber", accountNumber);
		List<Double> list = qry.getResultList();
		if (StringUtils.isListNotNullNEmpty(list)) {
			serialNumber = list.get(0).longValue();
		}
		return serialNumber;
	}

	/**
	 * @param entityId
	 * @param entityType
	 * @return
	 */
	@Override
	public List<PGMerchantEntityMap> findByEntityIdAndEntitytype(Long entityId, String entityType) {
		return merchantEntityMapRepository.findByEntityIdAndEntitytype(entityId, entityType);
	}

	@Override
	public PGMerchant getMerchantAutoSettlementByCode(String merchantCode) {
		//return merchantRepository.getMerchantAutoSettlementByCode(merchantCode);
		// PERF >> Return only 2 columns instead of the complete object
		PGMerchant merchant = new PGMerchant();
		
		Query qry = entityManager
				.createNativeQuery("select pgm.ID, pgmc.AUTO_SETTLEMENT from PG_MERCHANT pgm join PG_MERCHANT_CONFIG pgmc on pgm.MER_CONFIG_ID = pgmc.MER_CONFIG_ID "
						+ "and pgm.MERCHANT_CODE = :merchantCode");
		qry.setParameter("merchantCode", merchantCode);

		List<Object> list = qry.getResultList();
		if (StringUtil.isListNotNullNEmpty(list)) {
			Iterator it = list.iterator();
			while (it.hasNext()) {
				Object[] objs = (Object[]) it.next();

				Long id = StringUtil.isNull(objs[0]) ? null : ((BigInteger) objs[0]).longValue();
				Integer autoSettlement = StringUtil.isNull(objs[1]) ? null : ((BigInteger) objs[1]).intValue();
				
				PGMerchantConfig config = new PGMerchantConfig();
				config.setAutoSettlement(autoSettlement);
				
				merchant.setId(id);
				merchant.setMerchantConfig(config);
			}
		}
		
		return merchant;
	}

	@Override
	public List<PGMerchant> getPmMerchantByEntityIdandEntityType(Long entityId, String entityType) {
		JPAQuery<PGMerchant> query = new JPAQuery<PGMerchant>(entityManager);
		return query.from(QPGMerchant.pGMerchant, QPGMerchantEntityMap.pGMerchantEntityMap)
				.select(QPGMerchant.pGMerchant)
				.where(isMerchantNotEq(),
						QPGMerchantEntityMap.pGMerchantEntityMap.entityId.eq(entityId)
								.and(QPGMerchantEntityMap.pGMerchantEntityMap.entitytype.eq(entityType))
								.and(QPGMerchantEntityMap.pGMerchantEntityMap.merchantId.eq(QPGMerchant.pGMerchant.id))
								.and(QPGMerchant.pGMerchant.status.in(1, Integer.parseInt("4"))))
				.fetch();
	}
	  @Override
	  public PGMerchant getMerchantOnCodeAndEntityDetails(String merchantCode, String entityType,
	      Long entityId) {
	    PGMerchant merchant = new PGMerchant();
	    Query qry = null;
	    StringBuilder query = null;
	    if (entityType.equals(Constants.PM_USER_TYPE)) {
			query = new StringBuilder(" select a.MERCHANT_CODE,a.ID from ( select PGM.MERCHANT_CODE,PGM.ID ")
					.append(" FROM PG_MERCHANT as PGM INNER JOIN PG_MERCHANT_ENTITY_MAPPING PMEM ON PGM.ID = PMEM.MERCHANT_ID AND PMEM.ENTITY_ID=:entityId AND PGM.MERCHANT_CODE=:merchantCode ")
					.append(" union ").append(" select PGM.MERCHANT_CODE,PGM.ID ")
					.append(" from PG_MERCHANT as PGM INNER JOIN PG_MERCHANT_ENTITY_MAPPING AS PMEM ON PGM.ID = PMEM.MERCHANT_ID")
					.append(" INNER JOIN PG_PM_ISO_MAPPING AS PMIM ON PMEM.ENTITY_ID = PMIM.ISO_ID AND PMIM.PM_ID =:entityId AND PGM.MERCHANT_CODE =:merchantCode ")
					.append(" )a ");
	    } else if (entityType.equals(Constants.ISO_USER_TYPE)) {
	    	query = new StringBuilder("select PGM.MERCHANT_CODE,PGM.ID ")
	    		   .append(" FROM PG_MERCHANT as PGM INNER JOIN PG_MERCHANT_ENTITY_MAPPING AS PMEM ON PGM.ID = PMEM.MERCHANT_ID PMEM.ENTITY_ID =:entityId AND PGM.MERCHANT_CODE =:merchantCode");
	    } else {
	      return merchantRepository.findByMerchantCode(merchantCode);
	    }
	    qry = entityManager.createNativeQuery(query.toString());
	    qry.setParameter("entityId", entityId);
	    qry.setParameter("merchantCode", merchantCode);
	    List<Object> list = qry.getResultList();
	    if (StringUtil.isListNotNullNEmpty(list)) {
	      Iterator it = list.iterator();
	      while (it.hasNext()) {
	        Object[] objs = (Object[]) it.next();
	        merchant.setMerchantCode(StringUtil.isNull(objs[0]) ? null : ((String) objs[0]));
	        merchant.setId(StringUtil.isNull(objs[1]) ? null : ((BigInteger) objs[1]).longValue());
	        return merchant;
	      }
	    }
	    return null;
	  }
}
