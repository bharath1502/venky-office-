/**
 * 
 */
package com.chatak.pg.acq.dao.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.chatak.pg.acq.dao.model.PGAccount;
import com.chatak.pg.acq.dao.model.PGMerchant;
import com.chatak.pg.acq.dao.model.PGMerchantBank;
import com.chatak.pg.acq.dao.model.PGMerchantConfig;
import com.chatak.pg.acq.dao.model.PGMerchantUsers;
import com.chatak.pg.acq.dao.repository.AccountRepository;
import com.chatak.pg.acq.dao.repository.MerchantRepository;
import com.chatak.pg.constants.PGConstants;
import com.chatak.pg.user.bean.AddMerchantRequest;
import com.chatak.pg.util.CommonUtil;
import com.chatak.pg.util.Constants;
import com.chatak.pg.util.DateUtil;

/**
 * @Author: Girmiti Software
 * @Date: Aug 4, 2016
 * @Time: 1:41:27 AM
 * @Version: 1.0
 * @Comments:
 *
 */
public class MerchantDaoHelper {
  
    private static Logger logger = Logger.getLogger(MerchantDaoHelper.class);

	@Autowired
	private MerchantRepository merchantRepository;

	@Autowired
	private AccountRepository accountRepository;

	public PGMerchant subMerchantData(AddMerchantRequest addMerchantRequest) {
		Timestamp currentDate = new Timestamp(System.currentTimeMillis());
		Timestamp businessStartDate = DateUtil.getStartDayTimestamp(addMerchantRequest.getBusinessStartDate(),
				DateUtil.VIEW_DATE_FORMAT);
		String merchantCode = generateMerchantCode();
		Integer status = (null != addMerchantRequest.getStatus()) ? addMerchantRequest.getStatus()
				: PGConstants.STATUS_PENDING;

		try {
			PGMerchant merchant = new PGMerchant();
			merchant.setAddress1(CommonUtil.isNullAndEmpty(addMerchantRequest.getAddress1()) ? null
					: addMerchantRequest.getAddress1());
			merchant.setAddress2(CommonUtil.isNullAndEmpty(addMerchantRequest.getAddress2()) ? null
					: addMerchantRequest.getAddress2());
			merchant.setCity(
					CommonUtil.isNullAndEmpty(addMerchantRequest.getCity()) ? null : addMerchantRequest.getCity());
			merchant.setCountry(addMerchantRequest.getCountry());
			merchant.setCreatedDate(currentDate);
			merchant.setPhone(addMerchantRequest.getPhone());
			merchant.setFax(addMerchantRequest.getFax());
			merchant.setState(
					CommonUtil.isNullAndEmpty(addMerchantRequest.getState()) ? null : addMerchantRequest.getState());
			merchant.setStatus(status);
			merchant.setUpdatedDate(currentDate);
			merchant.setAppMode(addMerchantRequest.getAppMode());
			merchant.setBusinessName(addMerchantRequest.getBusinessName());
			merchant.setBusinessStartDate(businessStartDate);
			merchant.setBusinessURL(addMerchantRequest.getBusinessURL());
			merchant.setEstimatedYearlySale(addMerchantRequest.getEstimatedYearlySale());
			merchant.setFederalTaxId(addMerchantRequest.getFederalTaxId());
			merchant.setFirstName(addMerchantRequest.getFirstName());
			merchant.setLastName(addMerchantRequest.getLastName());
			merchant.setNoOfEmployee(addMerchantRequest.getNoOfEmployee());
			merchant.setOwnership(addMerchantRequest.getOwnership());
			merchant.setPin(addMerchantRequest.getPin());
			merchant.setRole(addMerchantRequest.getRole());
			merchant.setSalesTaxId(addMerchantRequest.getSalesTaxId());
			merchant.setStateTaxId(addMerchantRequest.getStateTaxId());
			merchant.setTimeZone(addMerchantRequest.getTimeZone());
			merchant.setUpdatedDate(currentDate);
			merchant.setMerchantCode(merchantCode);
			merchant.setMerchantCallBack(addMerchantRequest.getMerchantCallBackURL());
			merchant.setMerchantType(PGConstants.SUB_MERCHANT);
			merchant.setLitleMID(addMerchantRequest.getLitleMID());
			merchant.setBusinessType(addMerchantRequest.getBusinessType());
			merchant.setLookingFor(addMerchantRequest.getLookinFor());
			merchant.setAgentId(addMerchantRequest.getAgentId());
			merchant.setIssuancePartnerId(addMerchantRequest.getIssuancePartnerId());
			merchant.setProgramManagerId(addMerchantRequest.getProgramManagerId());
			merchant.setMerchantCategory(addMerchantRequest.getMerchantCategory());

			PGMerchantConfig pgMerchantConfig = new PGMerchantConfig();
			setPGMerchantConfigDetails(addMerchantRequest, currentDate, pgMerchantConfig);
			merchant.setMerchantConfig(pgMerchantConfig);

			List<PGMerchantUsers> pgMerchantUsers = new ArrayList<PGMerchantUsers>();

			PGMerchantUsers pgMerchantUser = new PGMerchantUsers();
			pgMerchantUser.setMerPassword(addMerchantRequest.getPassword());
			pgMerchantUser.setUserType(Constants.SUPERADMIN);
			pgMerchantUser.setStatus(status);
			pgMerchantUser.setUpdatedBy(addMerchantRequest.getCreatedBy());
			pgMerchantUser.setCreatedBy(addMerchantRequest.getCreatedBy());
			pgMerchantUser.setCreatedDate(currentDate);
			pgMerchantUser.setUpdatedDate(currentDate);
			pgMerchantUser.setUserRoleId(Long.parseLong("2"));// Setting default
																// MerchantAdmin
																// role
			pgMerchantUsers.add(pgMerchantUser);
			merchant.setPgMerchantUsers(pgMerchantUsers);

			return merchant;

		} catch (Exception e) {
		  logger.error("ERROR:: MerchantDaoHelper:: subMerchantData method", e);

		}
		return null;
	}

	private void setPGMerchantConfigDetails(AddMerchantRequest addMerchantRequest, Timestamp currentDate,
			PGMerchantConfig pgMerchantConfig) {
		pgMerchantConfig.setCreatedDate(currentDate);
		pgMerchantConfig.setFeeProgram(addMerchantRequest.getFeeProgram());
		pgMerchantConfig.setProcessor(addMerchantRequest.getProcessor());
		pgMerchantConfig.setRefunds(null != addMerchantRequest.getRefunds() && addMerchantRequest.getRefunds() ? 1 : 0);
		pgMerchantConfig.setShippingAmount(
				null != addMerchantRequest.getShippingAmount() && addMerchantRequest.getShippingAmount() ? 1 : 0);
		pgMerchantConfig
				.setTaxAmount(null != addMerchantRequest.getTaxAmount() && addMerchantRequest.getTaxAmount() ? 1 : 0);
		pgMerchantConfig
				.setTipAmount(null != addMerchantRequest.getTipAmount() && addMerchantRequest.getTipAmount() ? 1 : 0);
		pgMerchantConfig.setAutoSettlement(null != addMerchantRequest.getAutoSettlement() ? 1 : 0);
		pgMerchantConfig.setUpdatedDate(currentDate);
		pgMerchantConfig.setPosTerminal(
				null != addMerchantRequest.getPosTerminal() && addMerchantRequest.getPosTerminal() ? 1 : 0);
		pgMerchantConfig.setVirtualTerminal(
				null != addMerchantRequest.getVirtualTerminal() && addMerchantRequest.getVirtualTerminal() ? 1 : 0);
		pgMerchantConfig.setOnline(null != addMerchantRequest.getOnline() && addMerchantRequest.getOnline() ? 1 : 0);
		pgMerchantConfig.setWebSiteAddress(addMerchantRequest.getWebSiteAddress());
		pgMerchantConfig.setReturnUrl(addMerchantRequest.getReturnUrl());
		pgMerchantConfig.setCancelUrl(addMerchantRequest.getCancelUrl());
		pgMerchantConfig.setPayOutAt(addMerchantRequest.getPayOutAt());
	}

	public PGAccount subMerchantAccountHelp(AddMerchantRequest addMerchantRequest) {
		try {
			Long accNum = generateAccountNum();
			PGAccount pgAccount = new PGAccount();
			pgAccount.setAccountDesc(PGConstants.ACC_DESC);
			pgAccount.setAccountNum(accNum);
			pgAccount.setAutoPaymentLimit(null != addMerchantRequest.getAutoTransferLimit()
					? addMerchantRequest.getAutoTransferLimit() : null);

			pgAccount.setAutoPaymentMethod(addMerchantRequest.getAutoPaymentMethod());
			pgAccount.setAutoTransferDay(addMerchantRequest.getAutoTransferDay());
			pgAccount.setAvailableBalance(PGConstants.ZERO);
			pgAccount.setCategory(addMerchantRequest.getCategory());
			pgAccount.setCreatedDate(DateUtil.getCurrentTimestamp());
			pgAccount.setCurrentBalance(PGConstants.ZERO);
			pgAccount.setFeeBalance(PGConstants.ZERO);
			pgAccount.setCurrency(PGConstants.USD);
			pgAccount.setStatus(Constants.ACTIVE);

			PGMerchantBank pgMerchantBank = new PGMerchantBank();
			pgAccount.setPgMerchantBank(pgMerchantBank);

			pgAccount.getPgMerchantBank().setAccountType(addMerchantRequest.getBankAccountType());
			pgAccount.getPgMerchantBank().setBankAccNum(addMerchantRequest.getBankAccountNumber());
			pgAccount.getPgMerchantBank().setRoutingNumber(addMerchantRequest.getBankRoutingNumber());
			pgAccount.getPgMerchantBank().setBankCode(addMerchantRequest.getBankCode());
			pgAccount.getPgMerchantBank().setBankName(addMerchantRequest.getBankName());
			pgAccount.getPgMerchantBank().setCreatedBy(Long.valueOf(addMerchantRequest.getCreatedBy()));
			pgAccount.getPgMerchantBank().setCreatedDate(DateUtil.getCurrentTimestamp());
			pgAccount.getPgMerchantBank().setCurrencyCode(addMerchantRequest.getBankCurrencyCode()); // Need
																										// to
																										// change
																										// later
			pgAccount.getPgMerchantBank().setStatus(addMerchantRequest.getBankStatus());
			pgAccount.getPgMerchantBank().setUpdatedDate(pgAccount.getPgMerchantBank().getCreatedDate());
			pgAccount.getPgMerchantBank().setNameOnAccount(addMerchantRequest.getBankNameOnAccount());
			pgAccount.getPgMerchantBank().setAddress1(addMerchantRequest.getBankAddress1());
			pgAccount.getPgMerchantBank().setAddress2(addMerchantRequest.getBankAddress2());
			pgAccount.getPgMerchantBank().setCity(addMerchantRequest.getBankCity());
			pgAccount.getPgMerchantBank().setCountry(addMerchantRequest.getBankCountry());
			pgAccount.getPgMerchantBank().setPin(addMerchantRequest.getBankPin());
			pgAccount.getPgMerchantBank().setState(addMerchantRequest.getBankState());

			return pgAccount;
		} catch (Exception e) {
		  logger.error("ERROR:: MerchantDaoHelper:: subMerchantAccountHelp method", e);

		}
		return null;
	}

	private String generateMerchantCode() {
		String merchantCode = CommonUtil.generateRandNumeric(PGConstants.LENGTH_MERCHANT_ID);
		try {
			// restricting merchant code starting with 0
			while (merchantCode.startsWith("0")) {
				merchantCode = CommonUtil.generateRandNumeric(PGConstants.LENGTH_MERCHANT_ID);
			}
			PGMerchant response = getMerchant(merchantCode);

			if (response != null) {
				return generateMerchantCode();
			}
		} catch (Exception e) {
		  logger.error("ERROR:: MerchantDaoHelper:: generateMerchantCode method", e);

		}
		return merchantCode;

	}

	/**
	 * Method to get merchant data on merchant code
	 * 
	 * @param merchantCode
	 * @return
	 */
	public PGMerchant getMerchant(String merchantCode) {
		return merchantRepository.findByMerchantCode(merchantCode);
	}

	public Long generateAccountNum() {

		String accountNum = CommonUtil.generateRandNumeric(PGConstants.LENGTH_MER_ACC_NUM);
		try {
			PGAccount response = getAccount(Long.valueOf(accountNum));

			if (response != null) {
				return generateAccountNum();
			}
		} catch (Exception e) {
		  logger.error("ERROR:: MerchantDaoHelper:: getMerchant method", e);

		}
		return Long.valueOf(accountNum);

	}

	private PGAccount getAccount(Long accountNum) {
		return accountRepository.findByAccountNum(accountNum);
	}
}
