package com.chatak.pg.bean.settlement;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class SettlementEntity {

  private String merchantId;
  
  private String terminalId;
  
  private String issTxnId;
  
  private String pgTxnId;
  
  private String acquirerAmount;
  
  private String issAmount;
  
  private String batchId;
  
  private Timestamp txnDate;
  
  private String acqPmId;
  
  private String issPmId;
  
  private Timestamp batchFileDate;
  
  private Timestamp batchFileProcessedDate;
  
  private String status;
  
  private Long pmAmount;
  
  private Long isoAmount;
  
  private Long merchantAmount;
  
  private Long isoId;
  
  private Timestamp createdDate;

  private Timestamp updatedDate;
  
  private String deviceLocalTxnTime;
  
  private Long entityId;
  
  private String entityType;
  
  private Timestamp batchDate;
  
  private String txnType;
  
  private String programManagerName;
  
  private String isoName;
  
  private String bankAccountNumber;
  
  private String bankRoutingNumber;
  
  private String settlementBatchStatus;
  
  private Long issuanceSettlementEntityId;

  private String transactionType;

  private String issPartner;
  
  private String userName;
  
  private String cardHolderName;
  
  private String txnDesc;
  
  private String merchantSettlementStatus;
  
  private String txnCurrencyCode;
  
  private String panMasked;
  
  private String acqTxnMode;
  
  private String acqChannel;
  
  private String inVoiceNumber;
  
  private String timeZoneRegion; 
  
  private BigDecimal txnTotalAmount;
  
  private Integer totalTxnCount;
  
  public String getIssTxnId() {
    return issTxnId;
  }

  public void setIssTxnId(String issTxnId) {
    this.issTxnId = issTxnId;
  }

  public String getPgTxnId() {
    return pgTxnId;
  }

  public void setPgTxnId(String pgTxnId) {
    this.pgTxnId = pgTxnId;
  }

  public Timestamp getTxnDate() {
    return txnDate;
  }

  public void setTxnDate(Timestamp txnDate) {
    this.txnDate = txnDate;
  }

  public String getAcqPmId() {
    return acqPmId;
  }

  public void setAcqPmId(String acqPmId) {
    this.acqPmId = acqPmId;
  }

  public String getIssPmId() {
    return issPmId;
  }

  public void setIssPmId(String issPmId) {
    this.issPmId = issPmId;
  }

  public Timestamp getBatchFileDate() {
    return batchFileDate;
  }

  public void setBatchFileDate(Timestamp batchFileDate) {
    this.batchFileDate = batchFileDate;
  }

  public Timestamp getBatchFileProcessedDate() {
    return batchFileProcessedDate;
  }

  public void setBatchFileProcessedDate(Timestamp batchFileProcessedDate) {
    this.batchFileProcessedDate = batchFileProcessedDate;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public String getMerchantId() {
    return merchantId;
  }

  public void setMerchantId(String merchantId) {
    this.merchantId = merchantId;
  }

  public String getTerminalId() {
    return terminalId;
  }

  public void setTerminalId(String terminalId) {
    this.terminalId = terminalId;
  }
  
  public String getAcquirerAmount() {
    return acquirerAmount;
  }

  public void setAcquirerAmount(String acquirerAmount) {
    this.acquirerAmount = acquirerAmount;
  }

  public String getIssAmount() {
    return issAmount;
  }

  public void setIssAmount(String issAmount) {
    this.issAmount = issAmount;
  }

  public String getBatchId() {
    return batchId;
  }

  public void setBatchId(String batchId) {
    this.batchId = batchId;
  }

  public Long getPmAmount() {
    return pmAmount;
  }

  public void setPmAmount(Long pmAmount) {
    this.pmAmount = pmAmount;
  }

  public Long getIsoAmount() {
    return isoAmount;
  }

  public void setIsoAmount(Long isoAmount) {
    this.isoAmount = isoAmount;
  }

  public Long getMerchantAmount() {
    return merchantAmount;
  }

  public void setMerchantAmount(Long merchantAmount) {
    this.merchantAmount = merchantAmount;
  }

  public Long getIsoId() {
    return isoId;
  }

  public void setIsoId(Long isoId) {
    this.isoId = isoId;
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

  public String getDeviceLocalTxnTime() {
    return deviceLocalTxnTime;
  }

  public void setDeviceLocalTxnTime(String deviceLocalTxnTime) {
    this.deviceLocalTxnTime = deviceLocalTxnTime;
  }

  public Long getEntityId() {
    return entityId;
  }

  public void setEntityId(Long entityId) {
    this.entityId = entityId;
  }

  public String getEntityType() {
    return entityType;
  }

  public void setEntityType(String entityType) {
    this.entityType = entityType;
  }

  public Timestamp getBatchDate() {
    return batchDate;
  }

  public void setBatchDate(Timestamp batchDate) {
    this.batchDate = batchDate;
  }

  public String getTxnType() {
    return txnType;
  }

  public void setTxnType(String txnType) {
    this.txnType = txnType;
  }

  public String getProgramManagerName() {
    return programManagerName;
  }

  public void setProgramManagerName(String programManagerName) {
    this.programManagerName = programManagerName;
  }

  public String getIsoName() {
    return isoName;
  }

  public void setIsoName(String isoName) {
    this.isoName = isoName;
  }

  public String getBankAccountNumber() {
    return bankAccountNumber;
  }

  public void setBankAccountNumber(String bankAccountNumber) {
    this.bankAccountNumber = bankAccountNumber;
  }

  public String getBankRoutingNumber() {
    return bankRoutingNumber;
  }

  public void setBankRoutingNumber(String bankRoutingNumber) {
    this.bankRoutingNumber = bankRoutingNumber;
  }

  public String getSettlementBatchStatus() {
    return settlementBatchStatus;
  }

  public void setSettlementBatchStatus(String settlementBatchStatus) {
    this.settlementBatchStatus = settlementBatchStatus;
  }

  public Long getIssuanceSettlementEntityId() {
    return issuanceSettlementEntityId;
  }

  public void setIssuanceSettlementEntityId(Long issuanceSettlementEntityId) {
    this.issuanceSettlementEntityId = issuanceSettlementEntityId;
  }

  public String getTransactionType() {
    return transactionType;
  }

  public void setTransactionType(String transactionType) {
    this.transactionType = transactionType;
  }

  public BigDecimal getTxnTotalAmount() {
    return txnTotalAmount;
  }

  public void setTxnTotalAmount(BigDecimal txnTotalAmount) {
    this.txnTotalAmount = txnTotalAmount;
  }

  public String getIssPartner() {
    return issPartner;
  }

  public void setIssPartner(String issPartner) {
    this.issPartner = issPartner;
  }

  public String getUserName() {
    return userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }

  public String getCardHolderName() {
    return cardHolderName;
  }

  public void setCardHolderName(String cardHolderName) {
    this.cardHolderName = cardHolderName;
  }

  public String getTxnDesc() {
    return txnDesc;
  }

  public void setTxnDesc(String txnDesc) {
    this.txnDesc = txnDesc;
  }

  public String getMerchantSettlementStatus() {
    return merchantSettlementStatus;
  }

  public void setMerchantSettlementStatus(String merchantSettlementStatus) {
    this.merchantSettlementStatus = merchantSettlementStatus;
  }

  public String getTxnCurrencyCode() {
    return txnCurrencyCode;
  }

  public void setTxnCurrencyCode(String txnCurrencyCode) {
    this.txnCurrencyCode = txnCurrencyCode;
  }

  public String getPanMasked() {
    return panMasked;
  }

  public void setPanMasked(String panMasked) {
    this.panMasked = panMasked;
  }

  public String getAcqTxnMode() {
    return acqTxnMode;
  }

  public void setAcqTxnMode(String acqTxnMode) {
    this.acqTxnMode = acqTxnMode;
  }

  public String getAcqChannel() {
    return acqChannel;
  }

  public void setAcqChannel(String acqChannel) {
    this.acqChannel = acqChannel;
  }

  public String getInVoiceNumber() {
    return inVoiceNumber;
  }

  public void setInVoiceNumber(String inVoiceNumber) {
    this.inVoiceNumber = inVoiceNumber;
  }

  public String getTimeZoneRegion() {
    return timeZoneRegion;
  }

  public void setTimeZoneRegion(String timeZoneRegion) {
    this.timeZoneRegion = timeZoneRegion;
  }

  public Integer getTotalTxnCount() {
    return totalTxnCount;
  }

  public void setTotalTxnCount(Integer totalTxnCount) {
    this.totalTxnCount = totalTxnCount;
  }
  
}
