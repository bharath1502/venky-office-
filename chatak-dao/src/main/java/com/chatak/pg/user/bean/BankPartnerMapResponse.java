package com.chatak.pg.user.bean;

import java.util.List;

public class BankPartnerMapResponse extends Response {

  private static final long serialVersionUID = 3434664229825490439L;

  private List<BankPartnerMapRequest> bankPartnerMapList;

  public List<BankPartnerMapRequest> getBankPartnerMapList() {
    return bankPartnerMapList;
  }

  public void setBankPartnerMapList(List<BankPartnerMapRequest> bankPartnerMapList) {
    this.bankPartnerMapList = bankPartnerMapList;
  }
}
