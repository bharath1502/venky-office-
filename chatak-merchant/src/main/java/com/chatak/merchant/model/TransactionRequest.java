package com.chatak.merchant.model;

import java.io.Serializable;

import com.chatak.pg.bean.Request;

public class TransactionRequest extends Request implements Serializable{
	
  private static final long serialVersionUID = 1L;

  private String cardToken;

  private CardData cardData;
  
  private String registerNumber;

  private String orderId;

  private String authId;
  
  private String cgRefNumber;
  
  private Long merchantAmount;
  
  private String ip_port;
  
  private Long feeAmount;
  
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
   * @return the cardToken
   */
  public String getCardToken() {
    return cardToken;
  }

  /**
   * @param cardToken the cardToken to set
   */
  public void setCardToken(String cardToken) {
    this.cardToken = cardToken;
  }

  /**
   * @return the cardData
   */
  public CardData getCardData() {
    return cardData;
  }

  /**
   * @param cardData the cardData to set
   */
  public void setCardData(CardData cardData) {
    this.cardData = cardData;
  }

  /**
   * @return the billingData
   */

  public String getOrderId() {
    return orderId;
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
   * @param authId the authId to set
   */
  public void setAuthId(String authId) {
    this.authId = authId;
  }

  /**
   * @return the ip_port
   */
  public String getIp_port() {
    return ip_port;
  }

  /**
   * @param ip_port the ip_port to set
   */
  public void setIp_port(String ip_port) {
    this.ip_port = ip_port;
  }

  /**
   * @return the cgRefNumber
   */
  public String getCgRefNumber() {
    return cgRefNumber;
  }

  /**
   * @param cgRefNumber the cgRefNumber to set
   */
  public void setCgRefNumber(String cgRefNumber) {
    this.cgRefNumber = cgRefNumber;
  }

  /**
   * @return the merchatntAmount
   */
  public Long getMerchantAmount() {
    return merchantAmount;
  }

  /**
   * @param merchatntAmount the merchatntAmount to set
   */
  public void setMerchantAmount(Long merchantAmount) {
    this.merchantAmount = merchantAmount;
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

}
