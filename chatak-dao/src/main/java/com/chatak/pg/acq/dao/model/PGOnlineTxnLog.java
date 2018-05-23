package com.chatak.pg.acq.dao.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "PG_ONLINE_TXN_LOG")
public class PGOnlineTxnLog implements java.io.Serializable {

  /**
   * 
   */
  private static final long serialVersionUID = -7908115919870215132L;

  @Id
  /*@SequenceGenerator(name = "SEQ_PG_ONLINE_TXN_LOG_ID", sequenceName = "SEQ_PG_ONLINE_TXN_LOG")
  @GeneratedValue(generator = "SEQ_PG_ONLINE_TXN_LOG_ID")*/
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "ID")
  private Long id;

  @Column(name = "ORDER_ID")
  private String orderId;

  @Column(name = "PROCESSOR_TXN_ID")
  private String processorTxnId;

  @Column(name = "PG_TXN_ID")
  private String pgTxnId;

  @Column(name = "TRANSACTION_TYPE")
  private String txnType;

  @Column(name = "REQUEST_IP_PORT")
  private String requestIPPort;

  @Column(name = "MERCHANT_ID")
  private String merchantId;

  @Column(name = "TXN_TOTAL_AMOUNT")
  private Long txnTotalAmount;

  @Column(name = "MERCHANT_AMOUNT")
  private Long merchantAmount;

  @Column(name = "CARD_ASSOCIATION")
  private String cardAssciation;

  @Column(name = "TXN_DESCRIPTION")
  private String txnDescription;

  @Column(name = "BILLER_NAME")
  private String billerName;

  @Column(name = "BILLER_EMAIL")
  private String billerEmail;

  @Column(name = "BILLER_ADDRESS")
  private String billerAddress;

  @Column(name = "BILLER_ADDRESS2")
  private String billerAddress2;

  @Column(name = "BILLER_CITY")
  private String billerCity;

  @Column(name = "BILLER_STATE")
  private String billerState;

  @Column(name = "BILLER_COUNTRY")
  private String billerCountry;

  @Column(name = "BILLER_ZIP")
  private String billerZip;

  @Column(name = "MERCHANT_RETURN_URL")
  private String merchantReturnUrl;

  @Column(name = "PAN_MASKED")
  private String panMasked;

  @Column(name = "PAN_DATA")
  private String panData;

  @Column(name = "POS_TXN_DATE_TIME")
  private Long posTxnDate;

  @Column(name = "PROCESSOR_RESPONSE")
  private String processorResponse;

  @Column(name = "REQUEST_DATE_TIME")
  private Timestamp requestDateTime;

  @Column(name = "RESPONSE_DATE_TIME")
  private Timestamp responseDateTime;

  @Column(name = "TXN_STATE")
  private String txnState;
  
  @Column(name = "TXN_REASON")
  private String txnReason;
  
  @Column(name = "TXN_PAYMENT_PROCESS_TYPE")
  private Integer paymentProcessType;

  @Column(name = "INVOICE_NUMBER")
  private String invoceNumber;
  
  @Column(name = "REGISTER_NUMBER")
  private String registerNumber;
  
  @Column(name = "TXN_FEE_AMOUNT")
  private Long feeAmount;
  
  @Column(name = "CARD_HOLDER_NAME")
  private String cardHolderName;
  
  @Column(name = "MERCHANT_NAME")
  private String merchantName;

  @Column(name = "POS_ENTRY_MODE")
  private String posEntryMode;
  
  @Column(name = "APP_MODE")
  private String appMode;
  
  /**
   * @return the id
   */
  public Long getId() {
    return id;
  }

  /**
   * @param id
   *          the id to set
   */
  public void setId(Long id) {
    this.id = id;
  }

  /**
   * @return the orderId
   */
  public String getOrderId() {
    return orderId;
  }

  /**
   * @param orderId
   *          the orderId to set
   */
  public void setOrderId(String orderId) {
    this.orderId = orderId;
  }

  /**
   * @return the processorTxnId
   */
  public String getProcessorTxnId() {
    return processorTxnId;
  }

  /**
   * @param processorTxnId
   *          the processorTxnId to set
   */
  public void setProcessorTxnId(String processorTxnId) {
    this.processorTxnId = processorTxnId;
  }

  /**
   * @return the pgTxnId
   */
  public String getPgTxnId() {
    return pgTxnId;
  }

  /**
   * @param pgTxnId
   *          the pgTxnId to set
   */
  public void setPgTxnId(String pgTxnId) {
    this.pgTxnId = pgTxnId;
  }

  /**
   * @return the txnType
   */
  public String getTxnType() {
    return txnType;
  }

  /**
   * @param txnType
   *          the txnType to set
   */
  public void setTxnType(String txnType) {
    this.txnType = txnType;
  }

  /**
   * @return the requestIPPort
   */
  public String getRequestIPPort() {
    return requestIPPort;
  }

  /**
   * @param requestIPPort
   *          the requestIPPort to set
   */
  public void setRequestIPPort(String requestIPPort) {
    this.requestIPPort = requestIPPort;
  }

  /**
   * @return the merchantId
   */
  public String getMerchantId() {
    return merchantId;
  }

  /**
   * @param merchantId
   *          the merchantId to set
   */
  public void setMerchantId(String merchantId) {
    this.merchantId = merchantId;
  }

  /**
   * @return the txnTotalAmount
   */
  public Long getTxnTotalAmount() {
    return txnTotalAmount;
  }

  /**
   * @param txnTotalAmount
   *          the txnTotalAmount to set
   */
  public void setTxnTotalAmount(Long txnTotalAmount) {
    this.txnTotalAmount = txnTotalAmount;
  }

  /**
   * @return the merchantAmount
   */
  public Long getMerchantAmount() {
    return merchantAmount;
  }

  /**
   * @param merchantAmount
   *          the merchantAmount to set
   */
  public void setMerchantAmount(Long merchantAmount) {
    this.merchantAmount = merchantAmount;
  }

  /**
   * @return the cardAssciation
   */
  public String getCardAssciation() {
    return cardAssciation;
  }

  /**
   * @param cardAssciation
   *          the cardAssciation to set
   */
  public void setCardAssciation(String cardAssciation) {
    this.cardAssciation = cardAssciation;
  }

  /**
   * @return the txnDescription
   */
  public String getTxnDescription() {
    return txnDescription;
  }

  /**
   * @param txnDescription
   *          the txnDescription to set
   */
  public void setTxnDescription(String txnDescription) {
    this.txnDescription = txnDescription;
  }

  /**
   * @return the billerName
   */
  public String getBillerName() {
    return billerName;
  }

  /**
   * @param billerName
   *          the billerName to set
   */
  public void setBillerName(String billerName) {
    this.billerName = billerName;
  }

  /**
   * @return the billerEmail
   */
  public String getBillerEmail() {
    return billerEmail;
  }

  /**
   * @param billerEmail
   *          the billerEmail to set
   */
  public void setBillerEmail(String billerEmail) {
    this.billerEmail = billerEmail;
  }

  /**
   * @return the billerAddress
   */
  public String getBillerAddress() {
    return billerAddress;
  }

  /**
   * @param billerAddress
   *          the billerAddress to set
   */
  public void setBillerAddress(String billerAddress) {
    this.billerAddress = billerAddress;
  }

  /**
   * @return the billerAddress2
   */
  public String getBillerAddress2() {
    return billerAddress2;
  }

  /**
   * @param billerAddress2
   *          the billerAddress2 to set
   */
  public void setBillerAddress2(String billerAddress2) {
    this.billerAddress2 = billerAddress2;
  }

  /**
   * @return the billerCity
   */
  public String getBillerCity() {
    return billerCity;
  }

  /**
   * @param billerCity
   *          the billerCity to set
   */
  public void setBillerCity(String billerCity) {
    this.billerCity = billerCity;
  }

  /**
   * @return the billerState
   */
  public String getBillerState() {
    return billerState;
  }

  /**
   * @param billerState
   *          the billerState to set
   */
  public void setBillerState(String billerState) {
    this.billerState = billerState;
  }

  /**
   * @return the billerCountry
   */
  public String getBillerCountry() {
    return billerCountry;
  }

  /**
   * @param billerCountry
   *          the billerCountry to set
   */
  public void setBillerCountry(String billerCountry) {
    this.billerCountry = billerCountry;
  }

  /**
   * @return the billerZip
   */
  public String getBillerZip() {
    return billerZip;
  }

  /**
   * @param billerZip
   *          the billerZip to set
   */
  public void setBillerZip(String billerZip) {
    this.billerZip = billerZip;
  }

  /**
   * @return the merchantReturnUrl
   */
  public String getMerchantReturnUrl() {
    return merchantReturnUrl;
  }

  /**
   * @param merchantReturnUrl
   *          the merchantReturnUrl to set
   */
  public void setMerchantReturnUrl(String merchantReturnUrl) {
    this.merchantReturnUrl = merchantReturnUrl;
  }

  /**
   * @return the panMasked
   */
  public String getPanMasked() {
    return panMasked;
  }

  /**
   * @param panMasked
   *          the panMasked to set
   */
  public void setPanMasked(String panMasked) {
    this.panMasked = panMasked;
  }

  /**
   * @return the panData
   */
  public String getPanData() {
    return panData;
  }

  /**
   * @param panData
   *          the panData to set
   */
  public void setPanData(String panData) {
    this.panData = panData;
  }

  /**
   * @return the posTxnDate
   */
  public Long getPosTxnDate() {
    return posTxnDate;
  }

  /**
   * @param posTxnDate
   *          the posTxnDate to set
   */
  public void setPosTxnDate(Long posTxnDate) {
    this.posTxnDate = posTxnDate;
  }

  /**
   * @return the processorResponse
   */
  public String getProcessorResponse() {
    return processorResponse;
  }

  /**
   * @param processorResponse
   *          the processorResponse to set
   */
  public void setProcessorResponse(String processorResponse) {
    this.processorResponse = processorResponse;
  }

  /**
   * @return the requestDateTime
   */
  public Timestamp getRequestDateTime() {
    return requestDateTime;
  }

  /**
   * @param requestDateTime
   *          the requestDateTime to set
   */
  public void setRequestDateTime(Timestamp requestDateTime) {
    this.requestDateTime = requestDateTime;
  }

  /**
   * @return the responseDateTime
   */
  public Timestamp getResponseDateTime() {
    return responseDateTime;
  }

  /**
   * @param responseDateTime
   *          the responseDateTime to set
   */
  public void setResponseDateTime(Timestamp responseDateTime) {
    this.responseDateTime = responseDateTime;
  }

  /**
   * @return the txnState
   */
  public String getTxnState() {
    return txnState;
  }

  /**
   * @param txnState
   *          the txnState to set
   */
  public void setTxnState(String txnState) {
    this.txnState = txnState;
  }

  /**
   * @return the txnReason
   */
  public String getTxnReason() {
    return txnReason;
  }

  /**
   * @param txnReason the txnReason to set
   */
  public void setTxnReason(String txnReason) {
    this.txnReason = txnReason;
  }

  /**
   * @return the paymentProcessType
   */
  public Integer getPaymentProcessType() {
    return paymentProcessType;
  }

  /**
   * @param paymentProcessType the paymentProcessType to set
   */
  public void setPaymentProcessType(Integer paymentProcessType) {
    this.paymentProcessType = paymentProcessType;
  }

  /**
   * @return the invoceNumber
   */
  public String getInvoceNumber() {
    return invoceNumber;
  }

  /**
   * @param invoceNumber the invoceNumber to set
   */
  public void setInvoceNumber(String invoceNumber) {
    this.invoceNumber = invoceNumber;
  }

  /**
   * @return the registerNumber
   */
  public String getRegisterNumber() {
    return registerNumber;
  }

  /**
   * @param registerNumber the registerNumber to set
   */
  public void setRegisterNumber(String registerNumber) {
    this.registerNumber = registerNumber;
  }

  /**
   * @return the feeAmount
   */
  public Long getFeeAmount() {
    return feeAmount;
  }

  /**
   * @param feeAmount the feeAmount to set
   */
  public void setFeeAmount(Long feeAmount) {
    this.feeAmount = feeAmount;
  }

  /**
   * @return the cardHolderName
   */
  public String getCardHolderName() {
    return cardHolderName;
  }

  /**
   * @param cardHolderName the cardHolderName to set
   */
  public void setCardHolderName(String cardHolderName) {
    this.cardHolderName = cardHolderName;
  }

  /**
   * @return the merchantName
   */
  public String getMerchantName() {
    return merchantName;
  }

  /**
   * @param merchantName the merchantName to set
   */
  public void setMerchantName(String merchantName) {
    this.merchantName = merchantName;
  }

  /**
   * @return the posEntryMode
   */
  public String getPosEntryMode() {
    return posEntryMode;
  }

  /**
   * @param posEntryMode the posEntryMode to set
   */
  public void setPosEntryMode(String posEntryMode) {
    this.posEntryMode = posEntryMode;
  }

  /**
   * @return the appMode
   */
  public String getAppMode() {
    return appMode;
  }

  /**
   * @param appMode the appMode to set
   */
  public void setAppMode(String appMode) {
    this.appMode = appMode;
  }
  
  

}
