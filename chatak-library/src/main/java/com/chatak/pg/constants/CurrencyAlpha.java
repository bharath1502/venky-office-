package com.chatak.pg.constants;

public enum CurrencyAlpha {
  USD("USD"), CAD("CAD"), COP("COP");
  private String currencyAlphaCode;

  public String getCurrencyAlphaCode() {
    return currencyAlphaCode;
  }

  private CurrencyAlpha() {

  }

  private CurrencyAlpha(String s) {
    this.currencyAlphaCode = s;
  }
}
