package com.chatak.pg.user.bean;

import java.io.Serializable;
import java.util.List;

import com.chatak.pg.bean.SearchRequest;

/**
 * @author karimullah.syed
 */
public class FundTransferRequestDTO extends SearchRequest implements Serializable {
  /**
   * 
   */
  private static final long serialVersionUID = -5931079405626420044L;

  private Long id;

  private Long agentAccountNumber;

  private Long accountNumber;

  private String entityType;

  private Long srcAccountId;

  private Long tgetAccountId;

  private Long approverId;

  private Double txnAmount;

  private String txnCode;

  private String txnRefNumber;

  private String bankName;

  private String routingNumber;

  private String zipCode;

  private Long bankAccountNumber;

  private String bankAccountName;

  private String status;

  private String comments;

  private Double currentBalance;

  private String agentName;

  private List<Long> requestList;

  private String bankAddress;

  private String bankCity;

  private String bankState;

  private String bankAccountType;

  private Long agentId;

  private Long customerId;

  private Long customerEFTPendingListSize;

  private Long customerChequePendingListSize;

  private Long customerEFTProcessingListSize;

  private Long customerChequeProcessingListSize;

  private Long customerEFTCancelledListSize;

  private Long customerChequeCancelledListSize;

  private Long customerEFTExecutedListSize;

  private Long customerChequeExecutedListSize;

  private Long agentEFTPendingListSize;

  private Long agentChequePendingListSize;

  private Long agentEFTProcessingListSize;

  private Long agentChequeProcessingListSize;

  private Long agentEFTCancelledListSize;

  private Long agentChequeCancelledListSize;

  private Long agentEFTExecutedListSize;

  private Long agentChequeExecutedListSize;

  private List<Long> partnerIdList;

  private List<Long> programManagerIdList;

  private List<String> statusList;

  private List<String> txnCodes;

  private boolean closeFromAccountflage;

  private String name;

  private String description;

  private String taxable;

  private String payerTin;

  private Double transferFee;

  private String securityKey;

  private byte[] browseFile;

  private String city;

  private String country;

  private String state;

  private String addressLine1;

  private String userName;

  private Long srcAccountNum;

  private String addressLine2;

  private boolean withHoldFee;

  private String feeCode;

  private String firstName;

  private String lastName;

  private String fromDate;

  private String toDate;

  private Double fromAmount;

  private Double toAmount;

  private String txnAmountFlag;

  private Integer noOfRecordsOnPage;

  private String beneficiaryDetails;

  private Long programManagerId;

  private String programManagerName;

  private String accountType;

  private Long bankId;

  private String partnerName;

  private Long partnerId;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Long getAgentAccountNumber() {
    return agentAccountNumber;
  }

  public void setAgentAccountNumber(Long agentAccountNumber) {
    this.agentAccountNumber = agentAccountNumber;
  }

  public Long getAccountNumber() {
    return accountNumber;
  }

  public void setAccountNumber(Long accountNumber) {
    this.accountNumber = accountNumber;
  }

  public String getEntityType() {
    return entityType;
  }

  public void setEntityType(String entityType) {
    this.entityType = entityType;
  }

  public Long getSrcAccountId() {
    return srcAccountId;
  }

  public void setSrcAccountId(Long srcAccountId) {
    this.srcAccountId = srcAccountId;
  }

  public Long getTgetAccountId() {
    return tgetAccountId;
  }

  public void setTgetAccountId(Long tgetAccountId) {
    this.tgetAccountId = tgetAccountId;
  }

  public Long getApproverId() {
    return approverId;
  }

  public void setApproverId(Long approverId) {
    this.approverId = approverId;
  }

  public Double getTxnAmount() {
    return txnAmount;
  }

  public void setTxnAmount(Double txnAmount) {
    this.txnAmount = txnAmount;
  }

  public String getTxnCode() {
    return txnCode;
  }

  public void setTxnCode(String txnCode) {
    this.txnCode = txnCode;
  }

  public String getTxnRefNumber() {
    return txnRefNumber;
  }

  public void setTxnRefNumber(String txnRefNumber) {
    this.txnRefNumber = txnRefNumber;
  }

  public String getBankName() {
    return bankName;
  }

  public void setBankName(String bankName) {
    this.bankName = bankName;
  }

  public String getRoutingNumber() {
    return routingNumber;
  }

  public void setRoutingNumber(String routingNumber) {
    this.routingNumber = routingNumber;
  }

  public String getZipCode() {
    return zipCode;
  }

  public void setZipCode(String zipCode) {
    this.zipCode = zipCode;
  }

  public Long getBankAccountNumber() {
    return bankAccountNumber;
  }

  public void setBankAccountNumber(Long bankAccountNumber) {
    this.bankAccountNumber = bankAccountNumber;
  }

  public String getBankAccountName() {
    return bankAccountName;
  }

  public void setBankAccountName(String bankAccountName) {
    this.bankAccountName = bankAccountName;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public String getComments() {
    return comments;
  }

  public void setComments(String comments) {
    this.comments = comments;
  }

  public Double getCurrentBalance() {
    return currentBalance;
  }

  public void setCurrentBalance(Double currentBalance) {
    this.currentBalance = currentBalance;
  }

  public String getAgentName() {
    return agentName;
  }

  public void setAgentName(String agentName) {
    this.agentName = agentName;
  }

  public List<Long> getRequestList() {
    return requestList;
  }

  public void setRequestList(List<Long> requestList) {
    this.requestList = requestList;
  }

  public String getBankAddress() {
    return bankAddress;
  }

  public void setBankAddress(String bankAddress) {
    this.bankAddress = bankAddress;
  }

  public String getBankCity() {
    return bankCity;
  }

  public void setBankCity(String bankCity) {
    this.bankCity = bankCity;
  }

  public String getBankState() {
    return bankState;
  }

  public void setBankState(String bankState) {
    this.bankState = bankState;
  }

  public String getBankAccountType() {
    return bankAccountType;
  }

  public void setBankAccountType(String bankAccountType) {
    this.bankAccountType = bankAccountType;
  }

  public Long getAgentId() {
    return agentId;
  }

  public void setAgentId(Long agentId) {
    this.agentId = agentId;
  }

  public Long getCustomerId() {
    return customerId;
  }

  public void setCustomerId(Long customerId) {
    this.customerId = customerId;
  }

  public Long getCustomerEFTPendingListSize() {
    return customerEFTPendingListSize;
  }

  public void setCustomerEFTPendingListSize(Long customerEFTPendingListSize) {
    this.customerEFTPendingListSize = customerEFTPendingListSize;
  }

  public Long getCustomerChequePendingListSize() {
    return customerChequePendingListSize;
  }

  public void setCustomerChequePendingListSize(Long customerChequePendingListSize) {
    this.customerChequePendingListSize = customerChequePendingListSize;
  }

  public Long getCustomerEFTProcessingListSize() {
    return customerEFTProcessingListSize;
  }

  public void setCustomerEFTProcessingListSize(Long customerEFTProcessingListSize) {
    this.customerEFTProcessingListSize = customerEFTProcessingListSize;
  }

  public Long getCustomerChequeProcessingListSize() {
    return customerChequeProcessingListSize;
  }

  public void setCustomerChequeProcessingListSize(Long customerChequeProcessingListSize) {
    this.customerChequeProcessingListSize = customerChequeProcessingListSize;
  }

  public Long getCustomerEFTCancelledListSize() {
    return customerEFTCancelledListSize;
  }

  public void setCustomerEFTCancelledListSize(Long customerEFTCancelledListSize) {
    this.customerEFTCancelledListSize = customerEFTCancelledListSize;
  }

  public Long getCustomerChequeCancelledListSize() {
    return customerChequeCancelledListSize;
  }

  public void setCustomerChequeCancelledListSize(Long customerChequeCancelledListSize) {
    this.customerChequeCancelledListSize = customerChequeCancelledListSize;
  }

  public Long getCustomerEFTExecutedListSize() {
    return customerEFTExecutedListSize;
  }

  public void setCustomerEFTExecutedListSize(Long customerEFTExecutedListSize) {
    this.customerEFTExecutedListSize = customerEFTExecutedListSize;
  }

  public Long getCustomerChequeExecutedListSize() {
    return customerChequeExecutedListSize;
  }

  public void setCustomerChequeExecutedListSize(Long customerChequeExecutedListSize) {
    this.customerChequeExecutedListSize = customerChequeExecutedListSize;
  }

  public Long getAgentEFTPendingListSize() {
    return agentEFTPendingListSize;
  }

  public void setAgentEFTPendingListSize(Long agentEFTPendingListSize) {
    this.agentEFTPendingListSize = agentEFTPendingListSize;
  }

  public Long getAgentChequePendingListSize() {
    return agentChequePendingListSize;
  }

  public void setAgentChequePendingListSize(Long agentChequePendingListSize) {
    this.agentChequePendingListSize = agentChequePendingListSize;
  }

  public Long getAgentEFTProcessingListSize() {
    return agentEFTProcessingListSize;
  }

  public void setAgentEFTProcessingListSize(Long agentEFTProcessingListSize) {
    this.agentEFTProcessingListSize = agentEFTProcessingListSize;
  }

  public Long getAgentChequeProcessingListSize() {
    return agentChequeProcessingListSize;
  }

  public void setAgentChequeProcessingListSize(Long agentChequeProcessingListSize) {
    this.agentChequeProcessingListSize = agentChequeProcessingListSize;
  }

  public Long getAgentEFTCancelledListSize() {
    return agentEFTCancelledListSize;
  }

  public void setAgentEFTCancelledListSize(Long agentEFTCancelledListSize) {
    this.agentEFTCancelledListSize = agentEFTCancelledListSize;
  }

  public Long getAgentChequeCancelledListSize() {
    return agentChequeCancelledListSize;
  }

  public void setAgentChequeCancelledListSize(Long agentChequeCancelledListSize) {
    this.agentChequeCancelledListSize = agentChequeCancelledListSize;
  }

  public Long getAgentEFTExecutedListSize() {
    return agentEFTExecutedListSize;
  }

  public void setAgentEFTExecutedListSize(Long agentEFTExecutedListSize) {
    this.agentEFTExecutedListSize = agentEFTExecutedListSize;
  }

  public Long getAgentChequeExecutedListSize() {
    return agentChequeExecutedListSize;
  }

  public void setAgentChequeExecutedListSize(Long agentChequeExecutedListSize) {
    this.agentChequeExecutedListSize = agentChequeExecutedListSize;
  }

  public List<Long> getPartnerIdList() {
    return partnerIdList;
  }

  public void setPartnerIdList(List<Long> partnerIdList) {
    this.partnerIdList = partnerIdList;
  }

  public List<Long> getProgramManagerIdList() {
    return programManagerIdList;
  }

  public void setProgramManagerIdList(List<Long> programManagerIdList) {
    this.programManagerIdList = programManagerIdList;
  }

  public List<String> getStatusList() {
    return statusList;
  }

  public void setStatusList(List<String> statusList) {
    this.statusList = statusList;
  }

  public List<String> getTxnCodes() {
    return txnCodes;
  }

  public void setTxnCodes(List<String> txnCodes) {
    this.txnCodes = txnCodes;
  }

  public boolean isCloseFromAccountflage() {
    return closeFromAccountflage;
  }

  public void setCloseFromAccountflage(boolean closeFromAccountflage) {
    this.closeFromAccountflage = closeFromAccountflage;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getTaxable() {
    return taxable;
  }

  public void setTaxable(String taxable) {
    this.taxable = taxable;
  }

  public String getPayerTin() {
    return payerTin;
  }

  public void setPayerTin(String payerTin) {
    this.payerTin = payerTin;
  }

  public Double getTransferFee() {
    return transferFee;
  }

  public void setTransferFee(Double transferFee) {
    this.transferFee = transferFee;
  }

  public String getSecurityKey() {
    return securityKey;
  }

  public void setSecurityKey(String securityKey) {
    this.securityKey = securityKey;
  }

  public byte[] getBrowseFile() {
    return browseFile;
  }

  public void setBrowseFile(byte[] browseFile) {
    this.browseFile = browseFile;
  }

  public String getCity() {
    return city;
  }

  public void setCity(String city) {
    this.city = city;
  }

  public String getCountry() {
    return country;
  }

  public void setCountry(String country) {
    this.country = country;
  }

  public String getState() {
    return state;
  }

  public void setState(String state) {
    this.state = state;
  }

  public String getAddressLine1() {
    return addressLine1;
  }

  public void setAddressLine1(String addressLine1) {
    this.addressLine1 = addressLine1;
  }

  public String getUserName() {
    return userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }

  public Long getSrcAccountNum() {
    return srcAccountNum;
  }

  public void setSrcAccountNum(Long srcAccountNum) {
    this.srcAccountNum = srcAccountNum;
  }

  public String getAddressLine2() {
    return addressLine2;
  }

  public void setAddressLine2(String addressLine2) {
    this.addressLine2 = addressLine2;
  }

  public boolean isWithHoldFee() {
    return withHoldFee;
  }

  public void setWithHoldFee(boolean withHoldFee) {
    this.withHoldFee = withHoldFee;
  }

  public String getFeeCode() {
    return feeCode;
  }

  public void setFeeCode(String feeCode) {
    this.feeCode = feeCode;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public String getFromDate() {
    return fromDate;
  }

  public void setFromDate(String fromDate) {
    this.fromDate = fromDate;
  }

  public String getToDate() {
    return toDate;
  }

  public void setToDate(String toDate) {
    this.toDate = toDate;
  }

  public Double getFromAmount() {
    return fromAmount;
  }

  public void setFromAmount(Double fromAmount) {
    this.fromAmount = fromAmount;
  }

  public Double getToAmount() {
    return toAmount;
  }

  public void setToAmount(Double toAmount) {
    this.toAmount = toAmount;
  }

  public String getTxnAmountFlag() {
    return txnAmountFlag;
  }

  public void setTxnAmountFlag(String txnAmountFlag) {
    this.txnAmountFlag = txnAmountFlag;
  }

  public Integer getNoOfRecordsOnPage() {
    return noOfRecordsOnPage;
  }

  public void setNoOfRecordsOnPage(Integer noOfRecordsOnPage) {
    this.noOfRecordsOnPage = noOfRecordsOnPage;
  }

  public String getBeneficiaryDetails() {
    return beneficiaryDetails;
  }

  public void setBeneficiaryDetails(String beneficiaryDetails) {
    this.beneficiaryDetails = beneficiaryDetails;
  }

  public Long getProgramManagerId() {
    return programManagerId;
  }

  public void setProgramManagerId(Long programManagerId) {
    this.programManagerId = programManagerId;
  }

  public String getProgramManagerName() {
    return programManagerName;
  }

  public void setProgramManagerName(String programManagerName) {
    this.programManagerName = programManagerName;
  }

  public String getAccountType() {
    return accountType;
  }

  public void setAccountType(String accountType) {
    this.accountType = accountType;
  }

  public Long getBankId() {
    return bankId;
  }

  public void setBankId(Long bankId) {
    this.bankId = bankId;
  }

  public String getPartnerName() {
    return partnerName;
  }

  public void setPartnerName(String partnerName) {
    this.partnerName = partnerName;
  }

  public Long getPartnerId() {
    return partnerId;
  }

  public void setPartnerId(Long partnerId) {
    this.partnerId = partnerId;
  }

}
