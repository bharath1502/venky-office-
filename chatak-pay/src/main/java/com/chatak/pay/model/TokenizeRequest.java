package com.chatak.pay.model;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import com.chatak.pay.controller.model.Request;

/**
 * << Add Comments Here >>
 * 
 * @author Girmiti Software
 * @date 25-Mar-2015 2:47:52 PM
 * @version 1.0
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class TokenizeRequest extends Request {

  /**
   * 
   */
  private static final long serialVersionUID = 8697483267490355297L;

  private String versionNum;

  private Long tokenRequestorId;

  private String panNum;

  private String expDate;

  private String tokenAssuranceLevel;

  private String storageLoc;

  private Integer idvPerformed;

  private Integer accVerificationResults;

  private String accVerificationRefNum;

  private Integer cardHolderDataLen;

  private String cardHolderData;

  private Integer tokenDeviceInfolen;

  private String deviceInfoData;

  public String getVersionNum() {
    return versionNum;
  }

  public void setVersionNum(String versionNum) {
    this.versionNum = versionNum;
  }

  public Long getTokenRequestorId() {
    return tokenRequestorId;
  }

  public void setTokenRequestorId(Long tokenRequestorId) {
    this.tokenRequestorId = tokenRequestorId;
  }

  public String getPanNum() {
    return panNum;
  }

  public void setPanNum(String panNum) {
    this.panNum = panNum;
  }

  public String getExpDate() {
    return expDate;
  }

  public void setExpDate(String expDate) {
    this.expDate = expDate;
  }

  public String getTokenAssuranceLevel() {
    return tokenAssuranceLevel;
  }

  public void setTokenAssuranceLevel(String tokenAssuranceLevel) {
    this.tokenAssuranceLevel = tokenAssuranceLevel;
  }

  public String getStorageLoc() {
    return storageLoc;
  }

  public void setStorageLoc(String storageLoc) {
    this.storageLoc = storageLoc;
  }

  public Integer getIdvPerformed() {
    return idvPerformed;
  }

  public void setIdvPerformed(Integer idvPerformed) {
    this.idvPerformed = idvPerformed;
  }

  public Integer getAccVerificationResults() {
    return accVerificationResults;
  }

  public void setAccVerificationResults(Integer accVerificationResults) {
    this.accVerificationResults = accVerificationResults;
  }

  public String getAccVerificationRefNum() {
    return accVerificationRefNum;
  }

  public void setAccVerificationRefNum(String accVerificationRefNum) {
    this.accVerificationRefNum = accVerificationRefNum;
  }

  public Integer getCardHolderDataLen() {
    return cardHolderDataLen;
  }

  public void setCardHolderDataLen(Integer cardHolderDataLen) {
    this.cardHolderDataLen = cardHolderDataLen;
  }

  public String getCardHolderData() {
    return cardHolderData;
  }

  public void setCardHolderData(String cardHolderData) {
    this.cardHolderData = cardHolderData;
  }

  public Integer getTokenDeviceInfolen() {
    return tokenDeviceInfolen;
  }

  public void setTokenDeviceInfolen(Integer tokenDeviceInfolen) {
    this.tokenDeviceInfolen = tokenDeviceInfolen;
  }

  public String getDeviceInfoData() {
    return deviceInfoData;
  }

  public void setDeviceInfoData(String deviceInfoData) {
    this.deviceInfoData = deviceInfoData;
  }

}
