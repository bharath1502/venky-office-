package com.chatak.pg.user.bean;

import java.util.List;

public class BankProgramManagerMapResponse extends Response {

  private static final long serialVersionUID = -353213835875696054L;

  private List<BankProgramManagerMapRequest> bankProgramManagerMapList;

  public List<BankProgramManagerMapRequest> getBankProgramManagerMapList() {
    return bankProgramManagerMapList;
  }

  public void setBankProgramManagerMapList(
      List<BankProgramManagerMapRequest> bankProgramManagerMapList) {
    this.bankProgramManagerMapList = bankProgramManagerMapList;
  }

}
