package com.chatak.pg.model;

import java.util.List;

import com.chatak.pg.bean.SearchRequest;

public class BinRequest extends SearchRequest {

  private static final long serialVersionUID = -4893727735566527821L;

  private Long bin;

  private Long bankId;

  private String paymentSchemeName;

  private Long panLength;

  private String effectiveDate;

  private Long binType;

  private String binExt;

  private Long binCurrency;

  private String status;

  private String bankName;

  private String reason;

  private String partnerName;

  private List<String> statusList;

  public Long getBin() {
    return bin;
  }

  public void setBin(Long bin) {
    this.bin = bin;
  }

  public Long getBankId() {
    return bankId;
  }

  public void setBankId(Long bankId) {
    this.bankId = bankId;
  }

  public String getPaymentSchemeName() {
    return paymentSchemeName;
  }

  public void setPaymentSchemeName(String paymentSchemeName) {
    this.paymentSchemeName = paymentSchemeName;
  }

  public Long getPanLength() {
    return panLength;
  }

  public void setPanLength(Long panLength) {
    this.panLength = panLength;
  }

  /**
   * @return the effectiveDate
   */
  public String getEffectiveDate() {
    return effectiveDate;
  }

  /**
   * @param effectiveDate
   *          the effectiveDate to set
   */
  public void setEffectiveDate(String effectiveDate) {
    this.effectiveDate = effectiveDate;
  }

  public Long getBinType() {
    return binType;
  }

  public void setBinType(Long binType) {
    this.binType = binType;
  }

  public String getBinExt() {
    return binExt;
  }

  public void setBinExt(String binExt) {
    this.binExt = binExt;
  }

  public Long getBinCurrency() {
    return binCurrency;
  }

  public void setBinCurrency(Long binCurrency) {
    this.binCurrency = binCurrency;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public String getBankName() {
    return bankName;
  }

  public void setBankName(String bankName) {
    this.bankName = bankName;
  }

  @Override
  public String getReason() {
    return reason;
  }

  @Override
  public void setReason(String reason) {
    this.reason = reason;
  }

  @Override
  public String getPartnerName() {
    return partnerName;
  }

  @Override
  public void setPartnerName(String partnerName) {
    this.partnerName = partnerName;
  }

  /**
   * @return the statusList
   */
  public List<String> getStatusList() {
    return statusList;
  }

  /**
   * @param statusList
   *          the statusList to set
   */
  public void setStatusList(List<String> statusList) {
    this.statusList = statusList;
  }

}
