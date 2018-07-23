package com.chatak.pay.model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.map.annotate.JsonSerialize;


@JsonIgnoreProperties(ignoreUnknown = true)
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class TransactionDTO implements Serializable {

  /**
   * 
   */
  private static final long serialVersionUID = -3827967464863946684L;

  private Long id;

  private Long accountId;

  private String transNumber;

  private String agentAccountNumber;

  private String customerAccountNumber;

  private String cardNumber;

  private Long cardId;

  private String agentANI;

  private String txnType;

  private String txnStatus;

  private Double txnAmount;

  private Double feeAmount;

  private String txnCode;

  private String txnDescription;

  private String txnRefNumber;

  private String remarks;

  private String feeType;

  private String toDate;

  private String fromDate;

  private String accountFlag;

  private Timestamp createdDate;

  private Timestamp updatedDate;

  private Double txnValueFrom;

  private Double txnValueTo;

  private Double availableBalance;

  private String securityKey;

  private String approvalType;

  private String description1;

  private String description2;

  private String description3;

  private String description4;

  private Double currentBalance;

  private String approverId;

  private String txnDate;

  private List<String> txnCodes;

  private String updateViewDetailsFlag;

  private String cardChangeStatus;

  private String loadCardHoursFlag;

  private String cardLoadApprovalType;

  private String cardLoadApprovalName;

  private String approverSecurityKey;

  private String cardLoadApprovalFlag;

  private String rrn;

  private String additionalData;

  private String authorizationIdentity;

  private String txnDesc;

  private String srcAccountType;

  private String tgtAccountType;

  private String agentUserLoginName;

  private String customerPhoneNumber;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Long getAccountId() {
    return accountId;
  }

  public void setAccountId(Long accountId) {
    this.accountId = accountId;
  }

  public String getTransNumber() {
    return transNumber;
  }

  public void setTransNumber(String transNumber) {
    this.transNumber = transNumber;
  }

  public String getAgentAccountNumber() {
    return agentAccountNumber;
  }

  public void setAgentAccountNumber(String agentAccountNumber) {
    this.agentAccountNumber = agentAccountNumber;
  }

  public String getCustomerAccountNumber() {
    return customerAccountNumber;
  }

  public void setCustomerAccountNumber(String customerAccountNumber) {
    this.customerAccountNumber = customerAccountNumber;
  }

  public String getCardNumber() {
    return cardNumber;
  }

  public void setCardNumber(String cardNumber) {
    this.cardNumber = cardNumber;
  }

  public Long getCardId() {
    return cardId;
  }

  public void setCardId(Long cardId) {
    this.cardId = cardId;
  }

  public String getAgentANI() {
    return agentANI;
  }

  public void setAgentANI(String agentANI) {
    this.agentANI = agentANI;
  }

  public String getTxnType() {
    return txnType;
  }

  public void setTxnType(String txnType) {
    this.txnType = txnType;
  }

  public String getTxnStatus() {
    return txnStatus;
  }

  public void setTxnStatus(String txnStatus) {
    this.txnStatus = txnStatus;
  }

  public Double getTxnAmount() {
    return txnAmount;
  }

  public void setTxnAmount(Double txnAmount) {
    this.txnAmount = txnAmount;
  }

  public Double getFeeAmount() {
    return feeAmount;
  }

  public void setFeeAmount(Double feeAmount) {
    this.feeAmount = feeAmount;
  }

  public String getTxnCode() {
    return txnCode;
  }

  public void setTxnCode(String txnCode) {
    this.txnCode = txnCode;
  }

  public String getTxnDescription() {
    return txnDescription;
  }

  public void setTxnDescription(String txnDescription) {
    this.txnDescription = txnDescription;
  }

  public String getTxnRefNumber() {
    return txnRefNumber;
  }

  public void setTxnRefNumber(String txnRefNumber) {
    this.txnRefNumber = txnRefNumber;
  }

  public String getRemarks() {
    return remarks;
  }

  public void setRemarks(String remarks) {
    this.remarks = remarks;
  }

  public String getFeeType() {
    return feeType;
  }

  public void setFeeType(String feeType) {
    this.feeType = feeType;
  }

  public String getToDate() {
    return toDate;
  }

  public void setToDate(String toDate) {
    this.toDate = toDate;
  }

  public String getFromDate() {
    return fromDate;
  }

  public void setFromDate(String fromDate) {
    this.fromDate = fromDate;
  }

  public String getAccountFlag() {
    return accountFlag;
  }

  public void setAccountFlag(String accountFlag) {
    this.accountFlag = accountFlag;
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

  public Double getTxnValueFrom() {
    return txnValueFrom;
  }

  public void setTxnValueFrom(Double txnValueFrom) {
    this.txnValueFrom = txnValueFrom;
  }

  public Double getTxnValueTo() {
    return txnValueTo;
  }

  public void setTxnValueTo(Double txnValueTo) {
    this.txnValueTo = txnValueTo;
  }

  public Double getAvailableBalance() {
    return availableBalance;
  }

  public void setAvailableBalance(Double availableBalance) {
    this.availableBalance = availableBalance;
  }

  public String getSecurityKey() {
    return securityKey;
  }

  public void setSecurityKey(String securityKey) {
    this.securityKey = securityKey;
  }

  public String getApprovalType() {
    return approvalType;
  }

  public void setApprovalType(String approvalType) {
    this.approvalType = approvalType;
  }

  public String getDescription1() {
    return description1;
  }

  public void setDescription1(String description1) {
    this.description1 = description1;
  }

  public String getDescription2() {
    return description2;
  }

  public void setDescription2(String description2) {
    this.description2 = description2;
  }

  public String getDescription3() {
    return description3;
  }

  public void setDescription3(String description3) {
    this.description3 = description3;
  }

  public String getDescription4() {
    return description4;
  }

  public void setDescription4(String description4) {
    this.description4 = description4;
  }

  public Double getCurrentBalance() {
    return currentBalance;
  }

  public void setCurrentBalance(Double currentBalance) {
    this.currentBalance = currentBalance;
  }

  public String getApproverId() {
    return approverId;
  }

  public void setApproverId(String approverId) {
    this.approverId = approverId;
  }

  public String getTxnDate() {
    return txnDate;
  }

  public void setTxnDate(String txnDate) {
    this.txnDate = txnDate;
  }

  public List<String> getTxnCodes() {
    return txnCodes;
  }

  public void setTxnCodes(List<String> txnCodes) {
    this.txnCodes = txnCodes;
  }

  public String getUpdateViewDetailsFlag() {
    return updateViewDetailsFlag;
  }

  public void setUpdateViewDetailsFlag(String updateViewDetailsFlag) {
    this.updateViewDetailsFlag = updateViewDetailsFlag;
  }

  public String getCardChangeStatus() {
    return cardChangeStatus;
  }

  public void setCardChangeStatus(String cardChangeStatus) {
    this.cardChangeStatus = cardChangeStatus;
  }

  public String getLoadCardHoursFlag() {
    return loadCardHoursFlag;
  }

  public void setLoadCardHoursFlag(String loadCardHoursFlag) {
    this.loadCardHoursFlag = loadCardHoursFlag;
  }

  public String getCardLoadApprovalType() {
    return cardLoadApprovalType;
  }

  public void setCardLoadApprovalType(String cardLoadApprovalType) {
    this.cardLoadApprovalType = cardLoadApprovalType;
  }

  public String getCardLoadApprovalName() {
    return cardLoadApprovalName;
  }

  public void setCardLoadApprovalName(String cardLoadApprovalName) {
    this.cardLoadApprovalName = cardLoadApprovalName;
  }

  public String getApproverSecurityKey() {
    return approverSecurityKey;
  }

  public void setApproverSecurityKey(String approverSecurityKey) {
    this.approverSecurityKey = approverSecurityKey;
  }

  public String getCardLoadApprovalFlag() {
    return cardLoadApprovalFlag;
  }

  public void setCardLoadApprovalFlag(String cardLoadApprovalFlag) {
    this.cardLoadApprovalFlag = cardLoadApprovalFlag;
  }

  public String getRrn() {
    return rrn;
  }

  public void setRrn(String rrn) {
    this.rrn = rrn;
  }

  public String getAdditionalData() {
    return additionalData;
  }

  public void setAdditionalData(String additionalData) {
    this.additionalData = additionalData;
  }

  public String getAuthorizationIdentity() {
    return authorizationIdentity;
  }

  public void setAuthorizationIdentity(String authorizationIdentity) {
    this.authorizationIdentity = authorizationIdentity;
  }

  public String getTxnDesc() {
    return txnDesc;
  }

  public void setTxnDesc(String txnDesc) {
    this.txnDesc = txnDesc;
  }

  public String getSrcAccountType() {
    return srcAccountType;
  }

  public void setSrcAccountType(String srcAccountType) {
    this.srcAccountType = srcAccountType;
  }

  public String getTgtAccountType() {
    return tgtAccountType;
  }

  public void setTgtAccountType(String tgtAccountType) {
    this.tgtAccountType = tgtAccountType;
  }

  public String getAgentUserLoginName() {
    return agentUserLoginName;
  }

  public void setAgentUserLoginName(String agentUserLoginName) {
    this.agentUserLoginName = agentUserLoginName;
  }

  public String getCustomerPhoneNumber() {
    return customerPhoneNumber;
  }

  public void setCustomerPhoneNumber(String customerPhoneNumber) {
    this.customerPhoneNumber = customerPhoneNumber;
  }

}
