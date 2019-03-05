package com.chatak.pg.model;

import java.io.Serializable;

import com.chatak.pg.bean.Request;

public class TransactionRequest extends Request implements Serializable{
	
	private static final long serialVersionUID = 1L;

  private String registerNumber;

  private String cardToken;

  private CardData cardData;

  private String orderId;

  private String authId;
  
  private String ip_port;
  
  private String cgRefNumber;
  
  private Long merchantAmount;
  
  private Long feeAmount;
  
  private Integer partialRefundFlag;
  
  private String accountTransactionId;
  
  private String timeZoneOffset;
  
  private String timeZoneRegion;
  
  private String merchantCode;

  @Override
  public String getMerchantCode() {
    return merchantCode;
  }

  @Override
  public void setMerchantCode(String merchantCode) {
    this.merchantCode = merchantCode;
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
   * @return the cardData
   */
  public CardData getCardData() {
    return cardData;
  }

  /**
   * @return the cardToken
   */
  public String getCardToken() {
    return cardToken;
  }
  
  /**
   * @param cardData the cardData to set
   */
  public void setCardData(CardData cardData) {
    this.cardData = cardData;
  }

  /**
   * @param cardToken the cardToken to set
   */
  public void setCardToken(String cardToken) {
    this.cardToken = cardToken;
  }

  /**
   * @param orderId the orderId to set
   */
  public void setOrderId(String orderId) {
    this.orderId = orderId;
  }

  /**
   * @return the authId
   */
  public String getAuthId() {
    return authId;
  }
  
  /**
   * @return the billingData
   */

  public String getOrderId() {
    return orderId;
  }

  /**
   * @param authId the authId to set
   */
  public void setAuthId(String authId) {
    this.authId = authId;
  }
  
  /**
   * @return the cgRefNumber
   */
  public String getCgRefNumber() {
    return cgRefNumber;
  }

  /**
   * @return the ip_port
   */
  public String getIp_port() {
    return ip_port;
  }
  
  /**
   * @param cgRefNumber the cgRefNumber to set
   */
  public void setCgRefNumber(String cgRefNumber) {
    this.cgRefNumber = cgRefNumber;
  }

  /**
   * @param ip_port the ip_port to set
   */
  public void setIp_port(String ip_port) {
    this.ip_port = ip_port;
  }

  /**
   * @return the merchatntAmount
   */
  public Long getMerchantAmount() {
    return merchantAmount;
  }
  
  /**
   * @return the feeAmount
   */
  public Long getFeeAmount() {
    return feeAmount;
  }

  /**
   * @param merchatntAmount the merchatntAmount to set
   */
  public void setMerchantAmount(Long merchantAmount) {
    this.merchantAmount = merchantAmount;
  }

  /**
   * @param feeAmount the feeAmount to set
   */
  public void setFeeAmount(Long feeAmount) {
    this.feeAmount = feeAmount;
  }

  public Integer getPartialRefundFlag() {
    return partialRefundFlag;
  }

  public void setPartialRefundFlag(Integer partialRefundFlag) {
    this.partialRefundFlag = partialRefundFlag;
  }

  public String getAccountTransactionId() {
    return accountTransactionId;
  }

  public void setAccountTransactionId(String accountTransactionId) {
    this.accountTransactionId = accountTransactionId;
  }

  @Override
  public String getTimeZoneOffset() {
    return timeZoneOffset;
  }

  @Override
  public void setTimeZoneOffset(String timeZoneOffset) {
    this.timeZoneOffset = timeZoneOffset;
  }

  @Override
  public String getTimeZoneRegion() {
    return timeZoneRegion;
  }

  @Override
  public void setTimeZoneRegion(String timeZoneRegion) {
    this.timeZoneRegion = timeZoneRegion;
  }
  
}
