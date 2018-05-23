package com.chatak.pg.acq.dao.model;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "PG_PROGRAM_MANAGER_ACCOUNT")
public class ProgramManagerAccount implements Serializable {

  private static final long serialVersionUID = 4942042527701114008L;

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "ID")
  private Long id;

  @Column(name = "PROGRAM_MANAGER_ID")
  private Long programManagerId;

  @Column(name = "ACCOUNT_NUMBER", updatable = false)
  private Long accountNumber;

  @Column(name = "ACCOUNT_TYPE")
  private String accountType;

  @Column(name = "AVAILABLE_BALANCE")
  private Long availableBalance;

  @Column(name = "CURRENT_BALANCE")
  private Long currentBalance;

  @Column(name = "STATUS")
  private String status;

  @Column(name = "CREATED_DATE", updatable = false)
  private Timestamp createdDate;

  @Column(name = "UPDATED_DATE")
  private Timestamp updatedDate;

  @Column(name = "CREATED_BY", updatable = false)
  private String createdBy;

  @Column(name = "UPDATED_BY")
  private String updatedBy;

  @Column(name = "NICK_NAME")
  private String nickName;

  @Column(name = "ACCOUNT_THRESHOLD_LIMIT")
  private Long accountThresholdLimit;

  @Column(name = "SEND_FUNDS_MODE")
  private String sendFundsMode;

  @Column(name = "SEND_FUNDS_BANK_ID")
  private Long bankId;

  @Column(name = "AUTO_REPLENISH")
  private Boolean autoReplenish;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Long getProgramManagerId() {
    return programManagerId;
  }

  public void setProgramManagerId(Long programManagerId) {
    this.programManagerId = programManagerId;
  }

  public Long getAccountNumber() {
    return accountNumber;
  }

  public void setAccountNumber(Long accountNumber) {
    this.accountNumber = accountNumber;
  }

  public String getAccountType() {
    return accountType;
  }

  public void setAccountType(String accountType) {
    this.accountType = accountType;
  }

  public Long getAvailableBalance() {
    return availableBalance;
  }

  public void setAvailableBalance(Long availableBalance) {
    this.availableBalance = availableBalance;
  }

  public Long getCurrentBalance() {
    return currentBalance;
  }

  public void setCurrentBalance(Long currentBalance) {
    this.currentBalance = currentBalance;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public Timestamp getCreatedDate() {
    return createdDate;
  }

  public void setCreatedDate(Timestamp createdDate) {
    this.createdDate = createdDate;
  }

  public Timestamp getUpdatedDate() {
    return updatedDate;
  }

  public void setUpdatedDate(Timestamp updatedDate) {
    this.updatedDate = updatedDate;
  }

  public String getCreatedBy() {
    return createdBy;
  }

  public void setCreatedBy(String createdBy) {
    this.createdBy = createdBy;
  }

  public String getUpdatedBy() {
    return updatedBy;
  }

  public void setUpdatedBy(String updatedBy) {
    this.updatedBy = updatedBy;
  }

  /**
   * @return the nickName
   */
  public String getNickName() {
    return nickName;
  }

  /**
   * @param nickName the nickName to set
   */
  public void setNickName(String nickName) {
    this.nickName = nickName;
  }

  /**
   * @return the accountThresholdLimit
   */
  public Long getAccountThresholdLimit() {
    return accountThresholdLimit;
  }

  /**
   * @param accountThresholdLimit the accountThresholdLimit to set
   */
  public void setAccountThresholdLimit(Long accountThresholdLimit) {
    this.accountThresholdLimit = accountThresholdLimit;
  }

  /**
   * @return the sendFundsMode
   */
  public String getSendFundsMode() {
    return sendFundsMode;
  }

  /**
   * @param sendFundsMode the sendFundsMode to set
   */
  public void setSendFundsMode(String sendFundsMode) {
    this.sendFundsMode = sendFundsMode;
  }

  /**
   * @return the bankId
   */
  public Long getBankId() {
    return bankId;
  }

  /**
   * @param bankId the bankId to set
   */
  public void setBankId(Long bankId) {
    this.bankId = bankId;
  }

  public Boolean getAutoReplenish() {
    return autoReplenish;
  }

  public void setAutoReplenish(Boolean autoReplenish) {
    this.autoReplenish = autoReplenish;
  }
}
