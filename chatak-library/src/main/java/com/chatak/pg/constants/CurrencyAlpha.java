package com.chatak.pg.constants;

public enum CurrencyAlpha {
  USD("USD"), CAD("CAD"), COP("COP");
  private String currencyAlpha;

  public String getCurrencyAlpha() {
    return currencyAlpha;
  }

  private CurrencyAlpha() {

  }

  private CurrencyAlpha(String s) {
    this.currencyAlpha = s;
  }
}
