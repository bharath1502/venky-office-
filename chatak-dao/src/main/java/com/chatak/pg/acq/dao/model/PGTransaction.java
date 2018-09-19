package com.chatak.pg.acq.dao.model;

import java.io.Serializable;
import java.math.BigInteger;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "PG_TRANSACTION")
public class PGTransaction implements Serializable {

  /**
   * 
   */
  private static final long serialVersionUID = 5593907271831728272L;

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "ID")
  private BigInteger id;

  @Column(name = "TRANSACTION_ID")
  private String transactionId;

  @Column(name = "REF_TRANSACTION_ID")
  private String refTransactionId;

  @Column(name = "AUTH_ID")
  private String authId;

  @Column(name = "SYS_TRACE_NUM")
  private String sysTraceNum;

  @Column(name = "TXN_TYPE")
  private String transactionType;

  @Column(name = "PAYMENT_METHOD")
  private String paymentMethod;

  @Column(name = "TXN_AMOUNT")
  private Long txnAmount;

  @Column(name = "ADJ_AMOUNT")
  private Long adjAmount;

  @Column(name = "FEE_AMOUNT")
  private Long feeAmount;

  @Column(name = "TOTAL_AMOUNT")
  private Long txnTotalAmount;

  @Column(name = "MERCHANT_ID")
  private String merchantId;

  @Column(name = "TERMINAL_ID")
  private String terminalId;

  @Column(name = "ACQ_CHANNEL")
  private String acqChannel;

  @Column(name = "ACQ_TXN_MODE")
  private String acqTxnMode;

  @Column(name = "ISSUER_TXN_REF_NUM")
  private String issuerTxnRefNum;

  @Column(name = "CREATED_DATE")
  private Timestamp createdDate;

  @Column(name = "UPDATED_DATE")
  private Timestamp updatedDate;

  @Column(name = "STATUS")
  private Integer status;

  @Column(name = "CHIP_TRANSACTION")
  private Integer chipTransaction;

  @Column(name = "CHIP_FALLBACK_TRANSACTION")
  private Integer chipFallbackTransaction;

  @Column(name = "SETTLEMENT_BATCH_ID")
  private Long settlementBatchId;

  @Column(name = "SETTLEMENT_BATCH_STATUS")
  private String settlementBatchStatus;

  @Column(name = "INVOICE_NUMBER")
  private String invoiceNumber;

  @Column(name = "MTI")
  private String mti;

  @Column(name = "PROC_CODE")
  private String procCode;

  @Column(name = "PAN")
  private String pan;

  @Column(name = "EXP_DATE")
  private Long expDate;
  
  @Column(name = "PAN_MASKED")
  private String panMasked;

  @Column(name = "POS_TXN_TIME")
  private String posTxnTime;
  
  @Column(name = "MCC")
  private String mcc;

  @Column(name = "POS_TXN_DATE")
  private String posTxnDate;

  @Column(name = "POS_ENTRY_MODE")
  private String posEntryMode;

  @Column(name = "TXN_COUNTRY_CODE")
  private String txnCountryCode;
  
  @Column(name = "AI_COUNTRY_CODE")
  private String aiCountryCode;

  @Column(name = "TXN_CURRENCY_CODE")
  private String txnCurrencyCode;

  @Column(name = "PAN_COUNTRY_CODE")
  private String panCountryCode;
  
  @Column(name = "PROCESSOR")
  private String processor;

  @Column(name = "FWD_COUNTRY_CODE")
  private String fwdCountryCode;

  @Column(name = "MERCHANT_SETTLEMENT_ID")
  private String merchantSettlementId;

  @Column(name = "MERCHANT_SETTLEMENT_STATUS")
  private String merchantSettlementStatus;

  @Column(name = "MERCHANT_FEE_AMOUNT")
  private Long merchantFeeAmount;

  @Column(name = "TXN_DESCRIPTION")
  private String txnDescription;

  @Column(name = "REASON")
  private String reason;

  @Column(name = "CARD_HOLDER_NAME")
  private String cardHolderName;

  @Column(name = "TXN_MODE")
  private String txnMode;

  @Column(name = "EFT_STATUS")
  private String eftStatus;

  @Column(name = "EFT_ID")
  private String eftId;

  @Column(name = "EFT_MODE")
  private String eftMode;

  @Column(name = "CARD_HOLDER_EMAIL")
  private String cardHolderEmail;

  @Column(name = "REFUND_STATUS")
  private Integer refundStatus;// 1-For fully completed, 0-For partially
                               // completed
  
  @Column(name= "BATCH_ID")
  private String batchId;
  
  @Column(name="AUTO_SETTLEMENT_STATUS")
  private String autoSettlementStatus;
  
  @Column(name="USERNAME")
  private String userName;

  @Column(name = "BATCH_DATE")
  private Timestamp batchDate;
  
  @Column(name = "TIME_ZONE_OFFSET")
  private String timeZoneOffset;
  
  @Column(name = "TIME_ZONE_REGION")
  private String timeZoneRegion;
  
  @Column(name = "DEVICE_LOCAL_TXN_TIME")
  private String deviceLocalTxnTime;
  
  @Column(name = "PM_ID")
  private Long pmId;
  
  @Column(name = "ISO_ID")
  private Long isoId;
  
  @Column(name = "CP_ID")
  private Long cpId;
  
  @Column(name = "ISS_PARTNER")
  private String issuancePartner;

  public BigInteger getId() {
    return id;
  }

  public void setId(BigInteger id) {
    this.id = id;
  }

  public String getTransactionId() {
    return transactionId;
  }

  public void setTransactionId(String transactionId) {
    this.transactionId = transactionId;
  }

  public String getRefTransactionId() {
    return refTransactionId;
  }

  public void setRefTransactionId(String refTransactionId) {
    this.refTransactionId = refTransactionId;
  }

  public String getAuthId() {
    return authId;
  }

  public void setAuthId(String authId) {
    this.authId = authId;
  }

  public String getSysTraceNum() {
    return sysTraceNum;
  }

  public void setSysTraceNum(String sysTraceNum) {
    this.sysTraceNum = sysTraceNum;
  }

  public String getTransactionType() {
    return transactionType;
  }

  public void setTransactionType(String transactionType) {
    this.transactionType = transactionType;
  }

  public String getPaymentMethod() {
    return paymentMethod;
  }

  public void setPaymentMethod(String paymentMethod) {
    this.paymentMethod = paymentMethod;
  }

  public Long getTxnAmount() {
    return txnAmount;
  }

  public void setTxnAmount(Long txnAmount) {
    this.txnAmount = txnAmount;
  }

  public Long getAdjAmount() {
    return adjAmount;
  }

  public void setAdjAmount(Long adjAmount) {
    this.adjAmount = adjAmount;
  }

  public Long getFeeAmount() {
    return feeAmount;
  }

  public void setFeeAmount(Long feeAmount) {
    this.feeAmount = feeAmount;
  }

  public Long getTxnTotalAmount() {
    return txnTotalAmount;
  }

  public void setTxnTotalAmount(Long txnTotalAmount) {
    this.txnTotalAmount = txnTotalAmount;
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

  public String getAcqChannel() {
    return acqChannel;
  }

  public void setAcqChannel(String acqChannel) {
    this.acqChannel = acqChannel;
  }

  public String getAcqTxnMode() {
    return acqTxnMode;
  }

  public void setAcqTxnMode(String acqTxnMode) {
    this.acqTxnMode = acqTxnMode;
  }

  public String getIssuerTxnRefNum() {
    return issuerTxnRefNum;
  }

  public void setIssuerTxnRefNum(String issuerTxnRefNum) {
    this.issuerTxnRefNum = issuerTxnRefNum;
  }

  public Timestamp getCreatedDate() {
    return createdDate;
  }

  public void setCreatedDate(Timestamp createdDate) {
    this.createdDate = createdDate;
  }
  
  public Integer getStatus() {
    return status;
  }

  public void setStatus(Integer status) {
    this.status = status;
  }

  public Timestamp getUpdatedDate() {
    return updatedDate;
  }

  public void setUpdatedDate(Timestamp updatedDate) {
    this.updatedDate = updatedDate;
  }

  public Integer getChipTransaction() {
    return chipTransaction;
  }

  public void setChipTransaction(Integer chipTransaction) {
    this.chipTransaction = chipTransaction;
  }

  public Integer getChipFallbackTransaction() {
    return chipFallbackTransaction;
  }

  public void setChipFallbackTransaction(Integer chipFallbackTransaction) {
    this.chipFallbackTransaction = chipFallbackTransaction;
  }

  public Long getSettlementBatchId() {
    return settlementBatchId;
  }

  public void setSettlementBatchId(Long settlementBatchId) {
    this.settlementBatchId = settlementBatchId;
  }

  public String getSettlementBatchStatus() {
    return settlementBatchStatus;
  }

  public void setSettlementBatchStatus(String settlementBatchStatus) {
    this.settlementBatchStatus = settlementBatchStatus;
  }

  public String getInvoiceNumber() {
    return invoiceNumber;
  }

  public void setInvoiceNumber(String invoiceNumber) {
    this.invoiceNumber = invoiceNumber;
  }

  public String getMti() {
    return mti;
  }

  public void setMti(String mti) {
    this.mti = mti;
  }

  public String getProcCode() {
    return procCode;
  }

  public void setProcCode(String procCode) {
    this.procCode = procCode;
  }

  public String getPan() {
    return pan;
  }

  public void setPan(String pan) {
    this.pan = pan;
  }

  public void setPanMasked(String panMasked) {
    this.panMasked = panMasked;
  }

  public void setExpDate(Long expDate) {
    this.expDate = expDate;
  }

  public String getPosTxnTime() {
    return posTxnTime;
  }
  
  public void setPosTxnDate(String posTxnDate) {
	 this.posTxnDate = posTxnDate;
  }

  public void setPosTxnTime(String posTxnTime) {
    this.posTxnTime = posTxnTime;
  }

  public String getPosTxnDate() {
    return posTxnDate;
  }

  public String getMcc() {
    return mcc;
  }
  
  public String getPanMasked() {
	 return panMasked;
  }

  public void setMcc(String mcc) {
    this.mcc = mcc;
  }
  
  public Long getExpDate() {
	return expDate;
  }

  public String getPosEntryMode() {
    return posEntryMode;
  }

  public void setPosEntryMode(String posEntryMode) {
    this.posEntryMode = posEntryMode;
  }

  public String getTxnCountryCode() {
    return txnCountryCode;
  }
  
  public String getFwdCountryCode() {
	return fwdCountryCode;
  }

  public void setTxnCountryCode(String txnCountryCode) {
    this.txnCountryCode = txnCountryCode;
  }
  
  public void setFwdCountryCode(String fwdCountryCode) {
	 this.fwdCountryCode = fwdCountryCode;
  }

  public String getTxnCurrencyCode() {
    return txnCurrencyCode;
  }
  
  public void setAiCountryCode(String aiCountryCode) {
	 this.aiCountryCode = aiCountryCode;
  }

  public void setTxnCurrencyCode(String txnCurrencyCode) {
    this.txnCurrencyCode = txnCurrencyCode;
  }

  public String getAiCountryCode() {
    return aiCountryCode;
  }

  public String getPanCountryCode() {
    return panCountryCode;
  }

  public void setPanCountryCode(String panCountryCode) {
    this.panCountryCode = panCountryCode;
  }

  

  public String getProcessor() {
    return processor;
  }

  public void setProcessor(String processor) {
    this.processor = processor;
  }

  public String getMerchantSettlementId() {
    return merchantSettlementId;
  }

  public void setMerchantSettlementId(String merchantSettlementId) {
    this.merchantSettlementId = merchantSettlementId;
  }

  public String getMerchantSettlementStatus() {
    return merchantSettlementStatus;
  }

  public void setMerchantSettlementStatus(String merchantSettlementStatus) {
    this.merchantSettlementStatus = merchantSettlementStatus;
  }

  public Long getMerchantFeeAmount() {
    return merchantFeeAmount;
  }

  public void setMerchantFeeAmount(Long merchantFeeAmount) {
    this.merchantFeeAmount = merchantFeeAmount;
  }

  public String getTxnDescription() {
    return txnDescription;
  }

  public void setTxnDescription(String txnDescription) {
    this.txnDescription = txnDescription;
  }

  public String getReason() {
    return reason;
  }

  public void setReason(String reason) {
    this.reason = reason;
  }

  public String getCardHolderName() {
    return cardHolderName;
  }

  public void setCardHolderName(String cardHolderName) {
    this.cardHolderName = cardHolderName;
  }

  public String getTxnMode() {
    return txnMode;
  }

  public void setTxnMode(String txnMode) {
    this.txnMode = txnMode;
  }

  public String getEftStatus() {
    return eftStatus;
  }

  public void setEftStatus(String eftStatus) {
    this.eftStatus = eftStatus;
  }

  public String getEftId() {
    return eftId;
  }

  public void setEftId(String eftId) {
    this.eftId = eftId;
  }

  public String getEftMode() {
    return eftMode;
  }

  public void setEftMode(String eftMode) {
    this.eftMode = eftMode;
  }

  public String getCardHolderEmail() {
    return cardHolderEmail;
  }

  public void setCardHolderEmail(String cardHolderEmail) {
    this.cardHolderEmail = cardHolderEmail;
  }

  public Integer getRefundStatus() {
    return refundStatus;
  }

  public void setRefundStatus(Integer refundStatus) {
    this.refundStatus = refundStatus;
  }

  public String getBatchId() {
    return batchId;
  }

  public void setBatchId(String batchId) {
    this.batchId = batchId;
  }

  public String getAutoSettlementStatus() {
    return autoSettlementStatus;
  }

  public void setAutoSettlementStatus(String autoSettlementStatus) {
    this.autoSettlementStatus = autoSettlementStatus;
  }

  public String getUserName() {
    return userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }

  public Timestamp getBatchDate() {
    return batchDate;
  }

  public void setBatchDate(Timestamp batchDate) {
    this.batchDate = batchDate;
  }

  public String getTimeZoneOffset() {
    return timeZoneOffset;
  }

  public void setTimeZoneOffset(String timeZoneOffset) {
    this.timeZoneOffset = timeZoneOffset;
  }

  public String getTimeZoneRegion() {
    return timeZoneRegion;
  }

  public void setTimeZoneRegion(String timeZoneRegion) {
    this.timeZoneRegion = timeZoneRegion;
  }

  public String getDeviceLocalTxnTime() {
    return deviceLocalTxnTime;
  }

  public void setDeviceLocalTxnTime(String deviceLocalTxnTime) {
    this.deviceLocalTxnTime = deviceLocalTxnTime;
  }

/**
 * @return the pmId
 */
public Long getPmId() {
	return pmId;
}

/**
 * @param pmId the pmId to set
 */
public void setPmId(Long pmId) {
	this.pmId = pmId;
}

/**
 * @return the isoId
 */
public Long getIsoId() {
	return isoId;
}

/**
 * @param isoId the isoId to set
 */
public void setIsoId(Long isoId) {
	this.isoId = isoId;
}

/**
 * @return the cpId
 */
public Long getCpId() {
	return cpId;
}

/**
 * @param cpId the cpId to set
 */
public void setCpId(Long cpId) {
	this.cpId = cpId;
}

/**
 * @return the issuancePartner
 */
public String getIssuancePartner() {
	return issuancePartner;
}

/**
 * @param issuancePartner the issuancePartner to set
 */
public void setIssuancePartner(String issuancePartner) {
	this.issuancePartner = issuancePartner;
}

}
