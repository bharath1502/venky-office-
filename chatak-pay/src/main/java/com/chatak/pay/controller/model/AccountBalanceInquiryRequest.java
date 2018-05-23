package com.chatak.pay.controller.model;

import java.util.List;

public class AccountBalanceInquiryRequest {

  private List<Long> accountNumbers;

  public List<Long> getAccountNumbers() {
    return accountNumbers;
  }

  public void setAccountNumbers(List<Long> accountNumbers) {
    this.accountNumbers = accountNumbers;
  }
}
