package com.chatak.pay.controller.model;

import java.io.Serializable;

public class ClientCurrencyDTO implements Serializable {

  private static final long serialVersionUID = 1764570753979840096L;

  private long txnResponseTime;

  private String currencyCodeAlpha;

  private Integer currencyExponent;

  private Integer currencySeparatorPosition;

  private Character currencyMinorUnit;

  private Character currencyThousandsUnit;

  private String currencyCodeNumeric;

  public long getTxnResponseTime() {
    return txnResponseTime;
  }

  public void setTxnResponseTime(long txnResponseTime) {
    this.txnResponseTime = txnResponseTime;
  }

  public String getCurrencyCodeAlpha() {
    return currencyCodeAlpha;
  }

  public void setCurrencyCodeAlpha(String currencyCodeAlpha) {
    this.currencyCodeAlpha = currencyCodeAlpha;
  }

  public Integer getCurrencyExponent() {
    return currencyExponent;
  }

  public void setCurrencyExponent(Integer currencyExponent) {
    this.currencyExponent = currencyExponent;
  }

  public Integer getCurrencySeparatorPosition() {
    return currencySeparatorPosition;
  }

  public void setCurrencySeparatorPosition(Integer currencySeparatorPosition) {
    this.currencySeparatorPosition = currencySeparatorPosition;
  }

  public Character getCurrencyMinorUnit() {
    return currencyMinorUnit;
  }

  public void setCurrencyMinorUnit(Character currencyMinorUnit) {
    this.currencyMinorUnit = currencyMinorUnit;
  }

  public Character getCurrencyThousandsUnit() {
    return currencyThousandsUnit;
  }

  public void setCurrencyThousandsUnit(Character currencyThousandsUnit) {
    this.currencyThousandsUnit = currencyThousandsUnit;
  }

  public String getCurrencyCodeNumeric() {
    return currencyCodeNumeric;
  }

  public void setCurrencyCodeNumeric(String currencyCodeNumeric) {
    this.currencyCodeNumeric = currencyCodeNumeric;
  }
}
