package com.chatak.pg.user.bean;

public class BankPartnerMapRequest extends SearchRequest {

  private static final long serialVersionUID = -7167004948053710806L;

  private Long bankId;

  private Long partnerId;

  private Long id;

  public Long getBankId() {
    return bankId;
  }

  public Long getPartnerId() {
    return partnerId;
  }

  public Long getId() {
    return id;
  }

  public void setBankId(Long bankId) {
    this.bankId = bankId;
  }

  public void setPartnerId(Long partnerId) {
    this.partnerId = partnerId;
  }

  public void setId(Long id) {
    this.id = id;
  }

}
