package com.chatak.pg.bean;

import java.io.Serializable;

import org.jpos.iso.ISOMsg;

import com.chatak.pg.emv.util.EMVData;
import com.chatak.pg.enums.EntryModeEnum;
import com.chatak.pg.enums.NationalPOSEntryModeEnum;
import com.chatak.pg.enums.TransactionType;

public class Request implements Serializable {
  
  private static final long serialVersionUID = 6371643115256025876L;

  private String cardNum;

  private String pin;

  private String expDate;

  private String sysTraceNum;

  private String merchantCode;

  private String terminalId;

  private String invoiceNumber;

  private String channel;

  private String track2;

  private String acq_channel;

  private String acq_mode;

  private Boolean chipTransaction;

  private Boolean chipFallback;

  private String mti;

  private String processingCode;

  private EMVData emvData;

  private Long txnAmount;

  private String posEntryMode;

  private ISOMsg isoMsg;

  private String txnRefNumber; // Reference number for an original transaction such as for a refund or void.

  private String cvv;

  private Long totalTxnAmount;

  private Long txnFee;

  private EntryModeEnum entryMode;

  private String cardHolderName;

  private String description;

  private BillingData billingData;

  private String track;// Added for complete read

  private String cardAcceptorName;// DE 43

  private NationalPOSEntryModeEnum nationalPOSEntryMode;// DE 58

  private String pulseData;// DE 63

  private String merchantName;// DE 48
  
  private String reversalReason;// DE 48
  
  private boolean isSaleDependentRefund;
  
  private String cardType;
  
  private String mode;
  
  private String processorMid;
  
  private String originChannel;

  private TransactionType transactionType;
  
  private String cardHolderEmail;
  
  // Property which will be used for MPOS EMV data
  private String emv;
  
  private String qrCode;
  
  private String currencyCode;

  private String businessName;

  private String city;

  private String uid;
  
  private String userName;

  private String loginSessionId;

  private String auditSection;

  private String auditEvent;

  private String auditService;

  private String auditedBy;
  
  private String timeZoneOffset;
  
  private String timeZoneRegion;
  
  private String batchId;
  
  private Long merchantId;
  
  private Long isoId;
  
  private Long pmId;
  
  private String transactionId;
  
  public String getCurrencyCode() {
    return currencyCode;
  }

  public void setCurrencyCode(String currencyCode) {
    this.currencyCode = currencyCode;
  }

  public String getQrCode() {
    return qrCode;
  }

  public void setQrCode(String qrCode) {
    this.qrCode = qrCode;
  }

  public String getCardNum() {
    return cardNum;
  }

  public void setCardNum(String cardNum) {
    this.cardNum = cardNum;
  }

  public String getPin() {
    return pin;
  }

  public void setPin(String pin) {
    this.pin = pin;
  }

  public String getMerchantCode() {
    return merchantCode;
  }

  public void setMerchantCode(String merchantCode) {
    this.merchantCode = merchantCode;
  }

  public String getTerminalId() {
    return terminalId;
  }

  public void setTerminalId(String terminalId) {
    this.terminalId = terminalId;
  }

  public String getExpDate() {
    return expDate;
  }

  public void setExpDate(String expDate) {
    this.expDate = expDate;
  }

  public String getChannel() {
    return channel;
  }

  public void setChannel(String channel) {
    this.channel = channel;
  }

  public String getSysTraceNum() {
    return sysTraceNum;
  }

  public void setSysTraceNum(String sysTraceNum) {
    this.sysTraceNum = sysTraceNum;
  }

  public String getInvoiceNumber() {
    return invoiceNumber;
  }

  public void setInvoiceNumber(String invoiceNumber) {
    this.invoiceNumber = invoiceNumber;
  }

  public String getTrack2() {
    return track2;
  }

  public void setTrack2(String track2) {
    this.track2 = track2;
  }

  public String getAcq_channel() {
    return acq_channel;
  }

  public void setAcq_channel(String acq_channel) {
    this.acq_channel = acq_channel;
  }

  public String getAcq_mode() {
    return acq_mode;
  }

  public void setAcq_mode(String acq_mode) {
    this.acq_mode = acq_mode;
  }

  public Boolean getChipTransaction() {
    return chipTransaction;
  }

  public void setChipTransaction(Boolean chipTransaction) {
    this.chipTransaction = chipTransaction;
  }

  public Boolean getChipFallback() {
    return chipFallback;
  }

  public void setChipFallback(Boolean chipFallback) {
    this.chipFallback = chipFallback;
  }

  public String getMti() {
    return mti;
  }

  public void setMti(String mti) {
    this.mti = mti;
  }

  public String getProcessingCode() {
    return processingCode;
  }

  public void setProcessingCode(String processingCode) {
    this.processingCode = processingCode;
  }

  public EMVData getEmvData() {
    return emvData;
  }

  public void setEmvData(EMVData emvData) {
    this.emvData = emvData;
  }

  public Long getTxnAmount() {
    return txnAmount;
  }

  public void setTxnAmount(Long txnAmount) {
    this.txnAmount = txnAmount;
  }

  public String getPosEntryMode() {
    return posEntryMode;
  }

  public void setPosEntryMode(String posEntryMode) {
    this.posEntryMode = posEntryMode;
  }

  public ISOMsg getIsoMsg() {
    return isoMsg;
  }

  public void setIsoMsg(ISOMsg isoMsg) {
    this.isoMsg = isoMsg;
  }

  public String getTxnRefNumber() {
    return txnRefNumber;
  }

  public void setTxnRefNumber(String txnRefNumber) {
    this.txnRefNumber = txnRefNumber;
  }

  public String getCvv() {
    return cvv;
  }

  public void setCvv(String cvv) {
    this.cvv = cvv;
  }

  public Long getTotalTxnAmount() {
    return totalTxnAmount;
  }

  public void setTotalTxnAmount(Long totalTxnAmount) {
    this.totalTxnAmount = totalTxnAmount;
  }

  public Long getTxnFee() {
    return txnFee;
  }

  public void setTxnFee(Long txnFee) {
    this.txnFee = txnFee;
  }

  public EntryModeEnum getEntryMode() {
    return entryMode;
  }

  public void setEntryMode(EntryModeEnum entryMode) {
    this.entryMode = entryMode;
  }

  public String getCardHolderName() {
    return cardHolderName;
  }

  public void setCardHolderName(String cardHolderName) {
    this.cardHolderName = cardHolderName;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public BillingData getBillingData() {
    return billingData;
  }

  public void setBillingData(BillingData billingData) {
    this.billingData = billingData;
  }

  public String getTrack() {
    return track;
  }

  public void setTrack(String track) {
    this.track = track;
  }

  public String getCardAcceptorName() {
    return cardAcceptorName;
  }

  public void setCardAcceptorName(String cardAcceptorName) {
    this.cardAcceptorName = cardAcceptorName;
  }

  public String getPulseData() {
    return pulseData;
  }

  public void setPulseData(String pulseData) {
    this.pulseData = pulseData;
  }

  public String getMerchantName() {
    return merchantName;
  }

  public void setMerchantName(String merchantName) {
    this.merchantName = merchantName;
  }

  public NationalPOSEntryModeEnum getNationalPOSEntryMode() {
    return nationalPOSEntryMode;
  }

  public void setNationalPOSEntryMode(NationalPOSEntryModeEnum nationalPOSEntryMode) {
    this.nationalPOSEntryMode = nationalPOSEntryMode;
  }

  public String getReversalReason() {
    return reversalReason;
  }

  public void setReversalReason(String reversalReason) {
    this.reversalReason = reversalReason;
  }

  public boolean isSaleDependentRefund() {
    return isSaleDependentRefund;
  }

  public void setSaleDependentRefund(boolean isSaleDependentRefund) {
    this.isSaleDependentRefund = isSaleDependentRefund;
  }

  public String getCardType() {
    return cardType;
  }

  public void setCardType(String cardType) {
    this.cardType = cardType;
  }

  public String getMode() {
    return mode;
  }

  public void setMode(String mode) {
    this.mode = mode;
  }

  public String getProcessorMid() {
    return processorMid;
  }

  public void setProcessorMid(String processorMid) {
    this.processorMid = processorMid;
  }

  public String getOriginChannel() {
    return originChannel;
  }

  public void setOriginChannel(String originChannel) {
    this.originChannel = originChannel;
  }

  public TransactionType getTransactionType() {
    return transactionType;
  }

  public void setTransactionType(TransactionType transactionType) {
    this.transactionType = transactionType;
  }

  public String getCardHolderEmail() {
    return cardHolderEmail;
  }

  public void setCardHolderEmail(String cardHolderEmail) {
    this.cardHolderEmail = cardHolderEmail;
  }

  public String getEmv() {
    return emv;
  }

  public void setEmv(String emv) {
    this.emv = emv;
  }

  public String getBusinessName() {
    return businessName;
  }

  public void setBusinessName(String businessName) {
    this.businessName = businessName;
  }

  public String getCity() {
    return city;
  }

  public void setCity(String city) {
    this.city = city;
  }

  public String getUid() {
    return uid;
  }

  public void setUid(String uid) {
    this.uid = uid;
  }

  public String getUserName() {
    return userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }

  public String getLoginSessionId() {
    return loginSessionId;
  }

  public void setLoginSessionId(String loginSessionId) {
    this.loginSessionId = loginSessionId;
  }

  public String getAuditSection() {
    return auditSection;
  }

  public void setAuditSection(String auditSection) {
    this.auditSection = auditSection;
  }

  public String getAuditEvent() {
    return auditEvent;
  }

  public void setAuditEvent(String auditEvent) {
    this.auditEvent = auditEvent;
  }

  public String getAuditService() {
    return auditService;
  }

  public void setAuditService(String auditService) {
    this.auditService = auditService;
  }

  public String getAuditedBy() {
    return auditedBy;
  }

  public void setAuditedBy(String auditedBy) {
    this.auditedBy = auditedBy;
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

  public String getBatchId() {
    return batchId;
  }

  public void setBatchId(String batchId) {
    this.batchId = batchId;
  }

  /**
   * @return the merchantId
   */
  public Long getMerchantId() {
    return merchantId;
  }

  /**
   * @param merchantId the merchantId to set
   */
  public void setMerchantId(Long merchantId) {
    this.merchantId = merchantId;
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
   * @return the transactionId
   */
  public String getTransactionId() {
    return transactionId;
  }

  /**
   * @param transactionId the transactionId to set
   */
  public void setTransactionId(String transactionId) {
    this.transactionId = transactionId;
  }

}
