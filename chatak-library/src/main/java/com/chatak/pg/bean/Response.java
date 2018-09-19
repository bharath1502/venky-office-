package com.chatak.pg.bean;

import java.io.Serializable;
import java.util.List;

import com.chatak.pg.model.AgentDTO;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@SuppressWarnings("rawtypes")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Response implements Serializable {

  /**
   * 
   */
  private static final long serialVersionUID = 3932424007318695615L;

  private String errorCode;

  private String errorMessage;

  private String upStreamMessage;

  private String upStreamAuthCode;

  private Integer upStreamStatus;

  private String upStreamResponse;

  private Integer totalNoOfRows;

  private transient List responseList;

  private String upStreamTxnRefNum;

  private long txnResponseTime;

  private Long parentMerchantId;

  private String currencyCodeAlpha;

  private String currencyCodeNumeric;

  private Long currencyId;

  private Long agentAccountNumber;

  private String agentClientId;

  private String agentAni;

  private List<AgentDTO> agentDTOlist;

  private String programManagerName;

  private String partnerName;

  private Long partnerId;

  String entityType;
  
  private String issuancePartner;
  
  private String issuanceTxnTime;
  
  private String deviceTimeZoneOffset;
  
  private String deviceTimeZoneRegion;

  public List<AgentDTO> getAgentDTOlist() {
    return agentDTOlist;
  }

  public void setAgentDTOlist(List<AgentDTO> agentDTOlist) {
    this.agentDTOlist = agentDTOlist;
  }

  /**
   * @return the currencyId
   */
  public Long getCurrencyId() {
    return currencyId;
  }

  /**
   * @param currencyId
   *          the currencyId to set
   */
  public void setCurrencyId(Long currencyId) {
    this.currencyId = currencyId;
  }

  /**
   * @return the currencyCodeNumeric
   */
  public String getCurrencyCodeNumeric() {
    return currencyCodeNumeric;
  }

  /**
   * @param currencyCodeNumeric
   *          the currencyCodeNumeric to set
   */
  public void setCurrencyCodeNumeric(String currencyCodeNumeric) {
    this.currencyCodeNumeric = currencyCodeNumeric;
  }

  /**
   * @return the currencyCodeAlpha
   */
  public String getCurrencyCodeAlpha() {
    return currencyCodeAlpha;
  }

  /**
   * @param currencyCodeAlpha
   *          the currencyCodeAlpha to set
   */
  public void setCurrencyCodeAlpha(String currencyCodeAlpha) {
    this.currencyCodeAlpha = currencyCodeAlpha;
  }

  private String emv;

  /**
   * @return the errorCode
   */
  public String getErrorCode() {
    return errorCode;
  }

  /**
   * @return the totalNoOfRows
   */
  public Integer getTotalNoOfRows() {
    return totalNoOfRows;
  }

  /**
   * @return the errorMessage
   */
  public String getErrorMessage() {
    return errorMessage;
  }

  /**
   * @param errorCode
   *          the errorCode to set
   */
  public void setErrorCode(String errorCode) {
    this.errorCode = errorCode;
  }

  /**
   * @param totalNoOfRows
   *          the totalNoOfRows to set
   */
  public void setTotalNoOfRows(Integer totalNoOfRows) {
    this.totalNoOfRows = totalNoOfRows;
  }

  /**
   * @param errorMessage
   *          the errorMessage to set
   */
  public void setErrorMessage(String errorMessage) {
    this.errorMessage = errorMessage;
  }

  /**
   * @return the responseList
   */
  public List getResponseList() {
    return responseList;
  }

  /**
   * @param responseList
   *          the responseList to set
   */
  public void setResponseList(List responseList) {
    this.responseList = responseList;
  }

  /**
   * @return the txnResponseTime
   */
  public long getTxnResponseTime() {
    return txnResponseTime;
  }

  /**
   * @param txnResponseTime
   *          the txnResponseTime to set
   */
  public void setTxnResponseTime(long txnResponseTime) {
    this.txnResponseTime = txnResponseTime;
  }

  /**
   * @return the upStreamMessage
   */
  public String getUpStreamMessage() {
    return upStreamMessage;
  }

  /**
   * @param upStreamMessage
   *          the upStreamMessage to set
   */
  public void setUpStreamMessage(String upStreamMessage) {
    this.upStreamMessage = upStreamMessage;
  }

  /**
   * @return the upStreamAuthCode
   */
  public String getUpStreamAuthCode() {
    return upStreamAuthCode;
  }

  /**
   * @param upStreamAuthCode
   *          the upStreamAuthCode to set
   */
  public void setUpStreamAuthCode(String upStreamAuthCode) {
    this.upStreamAuthCode = upStreamAuthCode;
  }

  /**
   * @return the upStreamResponse
   */
  public String getUpStreamResponse() {
    return upStreamResponse;
  }

  /**
   * @param upStreamResponse
   *          the upStreamResponse to set
   */
  public void setUpStreamResponse(String upStreamResponse) {
    this.upStreamResponse = upStreamResponse;
  }

  /**
   * @return the upStreamStatus
   */
  public Integer getUpStreamStatus() {
    return upStreamStatus;
  }

  /**
   * @param upStreamStatus
   *          the upStreamStatus to set
   */
  public void setUpStreamStatus(Integer upStreamStatus) {
    this.upStreamStatus = upStreamStatus;
  }

  /**
   * @return the upStreamTxnRefNum
   */
  public String getUpStreamTxnRefNum() {
    return upStreamTxnRefNum;
  }

  /**
   * @param upStreamTxnRefNum
   *          the upStreamTxnRefNum to set
   */
  public void setUpStreamTxnRefNum(String upStreamTxnRefNum) {
    this.upStreamTxnRefNum = upStreamTxnRefNum;
  }

  public Long getParentMerchantId() {
    return parentMerchantId;
  }

  public void setParentMerchantId(Long parentMerchantId) {
    this.parentMerchantId = parentMerchantId;
  }

  public String getEmv() {
    return emv;
  }

  public void setEmv(String emv) {
    this.emv = emv;
  }

  public Long getAgentAccountNumber() {
    return agentAccountNumber;
  }

  public void setAgentClientId(String agentClientId) {
    this.agentClientId = agentClientId;
  }

  public String getAgentAni() {
    return agentAni;
  }

  public void setAgentAccountNumber(Long agentAccountNumber) {
    this.agentAccountNumber = agentAccountNumber;
  }

  public void setAgentAni(String agentAni) {
    this.agentAni = agentAni;
  }

  public String getAgentClientId() {
    return agentClientId;
  }

  public String getProgramManagerName() {
    return programManagerName;
  }

  public void setProgramManagerName(String programManagerName) {
    this.programManagerName = programManagerName;
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

  /**
   * @return the entityType
   */
  public String getEntityType() {
    return entityType;
  }

  /**
   * @param entityType the entityType to set
   */
  public void setEntityType(String entityType) {
    this.entityType = entityType;
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

  /**
   * @return the issuanceTxnTime
   */
  public String getIssuanceTxnTime() {
    return issuanceTxnTime;
  }

  /**
   * @param issuanceTxnTime the issuanceTxnTime to set
   */
  public void setIssuanceTxnTime(String issuanceTxnTime) {
    this.issuanceTxnTime = issuanceTxnTime;
  }

  public String getDeviceTimeZoneOffset() {
    return deviceTimeZoneOffset;
  }

  public void setDeviceTimeZoneOffset(String deviceTimeZoneOffset) {
    this.deviceTimeZoneOffset = deviceTimeZoneOffset;
  }

  public String getDeviceTimeZoneRegion() {
    return deviceTimeZoneRegion;
  }

  public void setDeviceTimeZoneRegion(String deviceTimeZoneRegion) {
    this.deviceTimeZoneRegion = deviceTimeZoneRegion;
  }

}
