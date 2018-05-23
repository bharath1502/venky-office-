package com.chatak.pg.bean;

import java.util.List;

public class DCCCurrency {
  
  private String baseCurrency;
  
  private String transactionCurrency;
  
  private Double markUpFee;

  private  List<String> currencyCodes;
  
  public List<String> getCurrencyCodes() {
    return currencyCodes;
  }

  public void setCurrencyCodes(List<String> currencyCodes) {
    this.currencyCodes = currencyCodes;
  }

  /**
   * @return the baseCurrency
   */
  public String getBaseCurrency() {
    return baseCurrency;
  }

  /**
   * @param baseCurrency the baseCurrency to set
   */
  public void setBaseCurrency(String baseCurrency) {
    this.baseCurrency = baseCurrency;
  }

  /**
   * @return the transactionCurrency
   */
  public String getTransactionCurrency() {
    return transactionCurrency;
  }

  /**
   * @param transactionCurrency the transactionCurrency to set
   */
  public void setTransactionCurrency(String transactionCurrency) {
    this.transactionCurrency = transactionCurrency;
  }

  /**
   * @return the markUpFee
   */
  public Double getMarkUpFee() {
    return markUpFee;
  }

  /**
   * @param markUpFee the markUpFee to set
   */
  public void setMarkUpFee(Double markUpFee) {
    this.markUpFee = markUpFee;
  } 

}
