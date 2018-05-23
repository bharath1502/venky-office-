package com.chatak.pg.user.bean;

import com.chatak.pg.bean.SearchRequest;

public class BankProgramManagerMapRequest extends SearchRequest {

  private static final long serialVersionUID = -8409862584893270958L;

  private Long id;

  private Long bankId;

  private Long programManagerId;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Long getBankId() {
    return bankId;
  }

  public void setBankId(Long bankId) {
    this.bankId = bankId;
  }

  public Long getProgramManagerId() {
    return programManagerId;
  }

  public void setProgramManagerId(Long programManagerId) {
    this.programManagerId = programManagerId;
  }

}
