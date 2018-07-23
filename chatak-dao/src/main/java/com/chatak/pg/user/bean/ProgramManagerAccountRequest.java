package com.chatak.pg.user.bean;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Map;

public class ProgramManagerAccountRequest implements Serializable {

  private static final long serialVersionUID = -1795438747399226342L;

  private Long id;

  private Long programManagerId;

  private Long accountNumber;

  private String accountType;

  private Double availableBalance;

  private Double currentBalance;

  private String status;

  private Timestamp createdDate;

  private Timestamp updatedDate;

  private String createdBy;

  private String updatedBy;

  private String nickName;

  private Double accountThresholdAmount;

  private Boolean autoRepenish;

  private Double differenceAmount;

  private String sendFundsMode;

  private Long bankId;

  private Map<Long, String> banks;

  public Long getId() {
    return id;
  }

  public Long getProgramManagerId() {
    return programManagerId;
  }

  public Long getAccountNumber() {
    return accountNumber;
  }

  public String getAccountType() {
    return accountType;
  }

  public Double getAvailableBalance() {
    return availableBalance;
  }

  public Double getCurrentBalance() {
    return currentBalance;
  }

  public String getStatus() {
    return status;
  }

  public Timestamp getCreatedDate() {
    return createdDate;
  }

  public Timestamp getUpdatedDate() {
    return updatedDate;
  }

  public String getCreatedBy() {
    return createdBy;
  }

  public String getUpdatedBy() {
    return updatedBy;
  }

  public String getNickName() {
    return nickName;
  }

  public Double getAccountThresholdAmount() {
    return accountThresholdAmount;
  }

  public Boolean getAutoRepenish() {
    return autoRepenish;
  }

  public Double getDifferenceAmount() {
    return differenceAmount;
  }

  public String getSendFundsMode() {
    return sendFundsMode;
  }

  public Long getBankId() {
    return bankId;
  }

  public Map<Long, String> getBanks() {
    return banks;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public void setProgramManagerId(Long programManagerId) {
    this.programManagerId = programManagerId;
  }

  public void setAccountNumber(Long accountNumber) {
    this.accountNumber = accountNumber;
  }

  public void setAccountType(String accountType) {
    this.accountType = accountType;
  }

  public void setAvailableBalance(Double availableBalance) {
    this.availableBalance = availableBalance;
  }

  public void setCurrentBalance(Double currentBalance) {
    this.currentBalance = currentBalance;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public void setCreatedDate(Timestamp createdDate) {
    this.createdDate = createdDate;
  }

  public void setUpdatedDate(Timestamp updatedDate) {
    this.updatedDate = updatedDate;
  }

  public void setCreatedBy(String createdBy) {
    this.createdBy = createdBy;
  }

  public void setUpdatedBy(String updatedBy) {
    this.updatedBy = updatedBy;
  }

  public void setNickName(String nickName) {
    this.nickName = nickName;
  }

  public void setAccountThresholdAmount(Double accountThresholdAmount) {
    this.accountThresholdAmount = accountThresholdAmount;
  }

  public void setAutoRepenish(Boolean autoRepenish) {
    this.autoRepenish = autoRepenish;
  }

  public void setDifferenceAmount(Double differenceAmount) {
    this.differenceAmount = differenceAmount;
  }

  public void setSendFundsMode(String sendFundsMode) {
    this.sendFundsMode = sendFundsMode;
  }

  public void setBankId(Long bankId) {
    this.bankId = bankId;
  }

  public void setBanks(Map<Long, String> banks) {
    this.banks = banks;
  }

}
