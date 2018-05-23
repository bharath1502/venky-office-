package com.chatak.pg.acq.dao.model;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.log4j.Logger;

import com.chatak.pg.user.bean.PartnerAccountRequest;
import com.chatak.pg.util.CommonUtil;

@Entity
@Table(name = "PG_PARTNER_ACCOUNT")
public class PartnerAccount implements Serializable {

  private static Logger logger = Logger.getLogger(PartnerAccount.class);

  private static final long serialVersionUID = -7035601627239287599L;

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "PARTNER_ACCOUNT_ID")
  private Long partnerAccountId;

  @Column(name = "ACCOUNT_NUMBER", updatable = false)
  private Long accountNumber;

  @Column(name = "PARTNER_ID")
  private Long partnerId;

  @Column(name = "AVAILABLE_BALANCE")
  private Long availableBalance;

  @Column(name = "ACCOUNT_TYPE")
  private String accountType;

  @Column(name = "STATUS")
  private String status;

  @Column(name = "CURRENT_BALANCE")
  private Long currentBalance;

  @Column(name = "UPDATED_DATE")
  private Timestamp updatedDate;

  @Column(name = "CREATED_DATE", updatable = false)
  private Timestamp createdDate;

  @Column(name = "UPDATED_BY")
  private String updatedBy;

  @Column(name = "CREATED_BY", updatable = false)
  private String createdBy;

  @Column(name = "ACCOUNT_THRESHOLD_LIMIT")
  private Long accountThresholdLimit;

  @Column(name = "NICK_NAME")
  private String nickName;

  @Column(name = "SEND_FUNDS_BANK_ID")
  private Long bankId;

  @Column(name = "SEND_FUNDS_MODE")
  private String sendFundsMode;

  @Column(name = "AUTO_REPLENISH")
  private Boolean autoReplenish;

  public PartnerAccountRequest convertToRequest() {
    PartnerAccountRequest partnerAccountRequest = new PartnerAccountRequest();
    try {
      partnerAccountRequest = CommonUtil.copyBeanProperties(this, PartnerAccountRequest.class);
    } catch (Exception e) {
      logger.error("Error :: PartnerAccount :: convertToRequest", e);
    }
    return partnerAccountRequest;
  }

  public static PartnerAccount convertToDAO(PartnerAccountRequest partnerAccountRequest) {
    PartnerAccount partnerAccount = new PartnerAccount();

    try {
      partnerAccount = CommonUtil.copyBeanProperties(partnerAccountRequest, PartnerAccount.class);
    } catch (Exception e) {
      logger.error("Error :: PartnerAccount :: convertToDAO", e);
    }
    return partnerAccount;
  }

  public Long getPartnerAccountId() {
    return partnerAccountId;
  }

  public void setPartnerAccountId(Long partnerAccountId) {
    this.partnerAccountId = partnerAccountId;
  }

  public Long getAccountNumber() {
    return accountNumber;
  }

  public void setAccountNumber(Long accountNumber) {
    this.accountNumber = accountNumber;
  }

  public Long getPartnerId() {
    return partnerId;
  }

  public void setPartnerId(Long partnerId) {
    this.partnerId = partnerId;
  }

  public Long getAvailableBalance() {
    return availableBalance;
  }

  public void setAvailableBalance(Long availableBalance) {
    this.availableBalance = availableBalance;
  }

  public String getAccountType() {
    return accountType;
  }

  public void setAccountType(String accountType) {
    this.accountType = accountType;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public Long getCurrentBalance() {
    return currentBalance;
  }

  public void setCurrentBalance(Long currentBalance) {
    this.currentBalance = currentBalance;
  }

  public Timestamp getUpdatedDate() {
    return updatedDate;
  }

  public void setUpdatedDate(Timestamp updatedDate) {
    this.updatedDate = updatedDate;
  }

  public Timestamp getCreatedDate() {
    return createdDate;
  }

  public void setCreatedDate(Timestamp createdDate) {
    this.createdDate = createdDate;
  }

  public String getUpdatedBy() {
    return updatedBy;
  }

  public void setUpdatedBy(String updatedBy) {
    this.updatedBy = updatedBy;
  }

  public String getCreatedBy() {
    return createdBy;
  }

  public void setCreatedBy(String createdBy) {
    this.createdBy = createdBy;
  }

  public Long getAccountThresholdLimit() {
    return accountThresholdLimit;
  }

  public void setAccountThresholdLimit(Long accountThresholdLimit) {
    this.accountThresholdLimit = accountThresholdLimit;
  }

  public String getNickName() {
    return nickName;
  }

  public void setNickName(String nickName) {
    this.nickName = nickName;
  }

  public Long getBankId() {
    return bankId;
  }

  public void setBankId(Long bankId) {
    this.bankId = bankId;
  }

  public String getSendFundsMode() {
    return sendFundsMode;
  }

  public void setSendFundsMode(String sendFundsMode) {
    this.sendFundsMode = sendFundsMode;
  }

  public Boolean getAutoReplenish() {
    return autoReplenish;
  }

  public void setAutoReplenish(Boolean autoReplenish) {
    this.autoReplenish = autoReplenish;
  }

}
