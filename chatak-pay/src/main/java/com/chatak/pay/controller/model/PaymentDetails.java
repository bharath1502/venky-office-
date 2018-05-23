/**
 * 
 */
package com.chatak.pay.controller.model;

import java.io.Serializable;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import com.chatak.pay.constants.PaymentProcessTypeEnum;
import com.chatak.pay.util.StringUtil;
import com.chatak.pg.enums.CardAssociationEnum;
import com.chatak.pg.util.Constants;
import com.chatak.switches.enums.TransactionType;
import com.litle.sdk.generate.CountryTypeEnum;
import com.litle.sdk.generate.CurrencyCodeEnum;

/**
 *
 * << Add Comments Here >>
 *
 * @author Girmiti Software
 * @date 19-Feb-2015 11:34:27 AM
 * @version 1.0
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class PaymentDetails implements Serializable {
  
  /**
   * 
   */
  private static final long serialVersionUID = -1957875555692672911L;
  
  public PaymentDetails() {
    
  }
  
  
  
  public PaymentDetails(Long transactionId,
                        TransactionType transactionType,
                        String orderId,
                        Long totalAmount,
                        Long merchantAmount,
                        CardAssociationEnum cardAssociation,
                        String description,
                        String billerName,
                        String billerEmail,
                        String billerCity,
                        String billerState,
                        CountryTypeEnum billerCountry,
                        String billerZip,
                        String address,
                        String address2,
                        String merchantId,
                        String returnURL,
                        CurrencyCodeEnum currencyCode,
                        PaymentProcessTypeEnum paymentProcessTypeEnum,
                        String token,
                        String accessToken) {
    super();
    this.transactionId = transactionId;
    this.transactionType = transactionType;
    this.orderId = orderId;
    this.totalAmount = totalAmount;
    this.merchantAmount = merchantAmount;
    this.cardAssociation = cardAssociation;
    this.description = description;
    this.billerName = billerName;
    this.billerEmail = billerEmail;
    this.billerCity = billerCity;
    this.billerState = billerState;
    this.billerCountry = billerCountry;
    this.billerZip = billerZip;
    this.address = address;
    this.address2 = address2;
    this.returnURL = returnURL;
    this.merchantId = merchantId;
    this.formatedTotalAmt = StringUtil.toAmount(this.totalAmount/Constants.ONE_HUNDRED);
    this.currencyCode = currencyCode;
    this.paymentProcessTypeEnum = paymentProcessTypeEnum;
    this.token = token;
    this.accessToken = accessToken;
  }



  private Long transactionId;
  private TransactionType transactionType;
  private String orderId;
  private Long totalAmount;
  private Long merchantAmount;
  private CardAssociationEnum cardAssociation;
  private String description;
  private String billerName;
  private String billerEmail;
  private String billerCity;
  private String billerState;
  private CountryTypeEnum billerCountry;
  private String billerZip;
  private String address;
  private String address2;
  private String returnURL;
  private String merchantId;
  private String clientIP;
  private Integer clientPort;
  private Long originTime;
  private CurrencyCodeEnum currencyCode;
  private String formatedTotalAmt;
  private PaymentProcessTypeEnum paymentProcessTypeEnum;
  private String token;
  private String accessToken;
  private String mode;
  private String processorMid;
  
  
  /**
   * @return the transactionType
   */
  public TransactionType getTransactionType() {
    return transactionType;
  }
  /**
   * @param transactionType the transactionType to set
   */
  public void setTransactionType(TransactionType transactionType) {
    this.transactionType = transactionType;
  }
  /**
   * @return the orderId
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
   * @return the totalAmount
   */
  public Long getTotalAmount() {
    return totalAmount;
  }
  /**
   * @param totalAmount the totalAmount to set
   */
  public void setTotalAmount(Long totalAmount) {
    this.totalAmount = totalAmount;
  }
  /**
   * @return the merchantAmount
   */
  public Long getMerchantAmount() {
    return merchantAmount;
  }
  /**
   * @param merchantAmount the merchantAmount to set
   */
  public void setMerchantAmount(Long merchantAmount) {
    this.merchantAmount = merchantAmount;
  }
  /**
   * @return the cardAssociation
   */
  public CardAssociationEnum getCardAssociation() {
    return cardAssociation;
  }
  /**
   * @param cardAssociation the cardAssociation to set
   */
  public void setCardAssociation(CardAssociationEnum cardAssociation) {
    this.cardAssociation = cardAssociation;
  }
  /**
   * @return the description
   */
  public String getDescription() {
    return description;
  }
  /**
   * @param description the description to set
   */
  public void setDescription(String description) {
    this.description = description;
  }
  /**
   * @return the billerName
   */
  public String getBillerName() {
    return billerName;
  }
  /**
   * @param billerName the billerName to set
   */
  public void setBillerName(String billerName) {
    this.billerName = billerName;
  }
  /**
   * @return the billerCity
   */
  public String getBillerCity() {
    return billerCity;
  }
  /**
   * @param billerCity the billerCity to set
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
   * @param billerState the billerState to set
   */
  public void setBillerState(String billerState) {
    this.billerState = billerState;
  }
  /**
   * @return the billerCountry
   */
  public CountryTypeEnum getBillerCountry() {
    return billerCountry;
  }
  /**
   * @param billerCountry the billerCountry to set
   */
  public void setBillerCountry(CountryTypeEnum billerCountry) {
    this.billerCountry = billerCountry;
  }
  /**
   * @return the billerZip
   */
  public String getBillerZip() {
    return billerZip;
  }
  /**
   * @param billerZip the billerZip to set
   */
  public void setBillerZip(String billerZip) {
    this.billerZip = billerZip;
  }
  /**
   * @return the address
   */
  public String getAddress() {
    return address;
  }
  /**
   * @param address the address to set
   */
  public void setAddress(String address) {
    this.address = address;
  }
  /**
   * @return the address2
   */
  public String getAddress2() {
    return address2;
  }
  /**
   * @param address2 the address2 to set
   */
  public void setAddress2(String address2) {
    this.address2 = address2;
  }
  /**
   * @return the billerEmail
   */
  public String getBillerEmail() {
    return billerEmail;
  }
  /**
   * @param billerEmail the billerEmail to set
   */
  public void setBillerEmail(String billerEmail) {
    this.billerEmail = billerEmail;
  }
  /**
   * @return the transactionId
   */
  public Long getTransactionId() {
    return transactionId;
  }
  /**
   * @param transactionId the transactionId to set
   */
  public void setTransactionId(Long transactionId) {
    this.transactionId = transactionId;
  }
  /**
   * @return the returnURL
   */
  public String getReturnURL() {
    return returnURL;
  }
  /**
   * @param returnURL the returnURL to set
   */
  public void setReturnURL(String returnURL) {
    this.returnURL = returnURL;
  }



  /**
   * @return the merchantId
   */
  public String getMerchantId() {
    return merchantId;
  }



  /**
   * @param merchantId the merchantId to set
   */
  public void setMerchantId(String merchantId) {
    this.merchantId = merchantId;
  }



  /**
   * @return the clientIP
   */
  public String getClientIP() {
    return clientIP;
  }



  /**
   * @param clientIP the clientIP to set
   */
  public void setClientIP(String clientIP) {
    this.clientIP = clientIP;
  }



  /**
   * @return the clientPort
   */
  public Integer getClientPort() {
    return clientPort;
  }



  /**
   * @param clientPort the clientPort to set
   */
  public void setClientPort(Integer clientPort) {
    this.clientPort = clientPort;
  }



  /**
   * @return the originTime
   */
  public Long getOriginTime() {
    return originTime;
  }



  /**
   * @param originTime the originTime to set
   */
  public void setOriginTime(Long originTime) {
    this.originTime = originTime;
  }



  /**
   * @return the currencyCode
   */
  public CurrencyCodeEnum getCurrencyCode() {
    return currencyCode;
  }



  /**
   * @param currencyCode the currencyCode to set
   */
  public void setCurrencyCode(CurrencyCodeEnum currencyCode) {
    this.currencyCode = currencyCode;
  }



  /**
   * @return the formatedTotalAmt
   */
  public String getFormatedTotalAmt() {
    return formatedTotalAmt;
  }



  /**
   * @param formatedTotalAmt the formatedTotalAmt to set
   */
  public void setFormatedTotalAmt(String formatedTotalAmt) {
    this.formatedTotalAmt = formatedTotalAmt;
  }



  /**
   * @return the token
   */
  public String getToken() {
    return token;
  }



  /**
   * @param token the token to set
   */
  public void setToken(String token) {
    this.token = token;
  }



  /**
   * @return the paymentProcessTypeEnum
   */
  public PaymentProcessTypeEnum getPaymentProcessTypeEnum() {
    return paymentProcessTypeEnum;
  }



  /**
   * @param paymentProcessTypeEnum the paymentProcessTypeEnum to set
   */
  public void setPaymentProcessTypeEnum(PaymentProcessTypeEnum paymentProcessTypeEnum) {
    this.paymentProcessTypeEnum = paymentProcessTypeEnum;
  }



  /**
   * @return the accessToken
   */
  public String getAccessToken() {
    return accessToken;
  }



  /**
   * @param accessToken the accessToken to set
   */
  public void setAccessToken(String accessToken) {
    this.accessToken = accessToken;
  }



  /**
   * @return the mode
   */
  public String getMode() {
    return mode;
  }



  /**
   * @param mode the mode to set
   */
  public void setMode(String mode) {
    this.mode = mode;
  }



  /**
   * @return the processorMid
   */
  public String getProcessorMid() {
    return processorMid;
  }



  /**
   * @param processorMid the processorMid to set
   */
  public void setProcessorMid(String processorMid) {
    this.processorMid = processorMid;
  }
  
  

}
