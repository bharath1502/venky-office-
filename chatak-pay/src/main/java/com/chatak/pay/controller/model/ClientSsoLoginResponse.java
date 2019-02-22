package com.chatak.pay.controller.model;

import java.util.List;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.chatak.pay.model.TSMResponse;
import com.chatak.pg.model.MposFeatures;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ClientSsoLoginResponse extends Response {

  private static final long serialVersionUID = 2989867872759614602L;

  private String accessToken;

  private String refreshToken;

  private Boolean status;
  
  private String message;

  private String merchantCode;

  private String bussinessName;

  private TSMResponse terminalData;

  private String address;

  private String city;

  private String state;

  private String country;

  private String pin;

  private ClientCurrencyDTO currencyDTO;
  
  private List<String> mposFeatures;

  public List<String> getMpsoFeatures() {
    return mposFeatures;
  }

  public void setMpsoFeatures(List<String> mpsoFeatures) {
    this.mposFeatures = mpsoFeatures;
  }

  public String getAccessToken() {
    return accessToken;
  }

  public void setAccessToken(String accessToken) {
    this.accessToken = accessToken;
  }

  public String getRefreshToken() {
    return refreshToken;
  }

  public void setRefreshToken(String refreshToken) {
    this.refreshToken = refreshToken;
  }

  public String getMerchantCode() {
    return merchantCode;
  }

  public void setMerchantCode(String merchantCode) {
    this.merchantCode = merchantCode;
  }

  public String getBussinessName() {
    return bussinessName;
  }

  public void setBussinessName(String bussinessName) {
    this.bussinessName = bussinessName;
  }

  public TSMResponse getTerminalData() {
    return terminalData;
  }

  public void setTerminalData(TSMResponse terminalData) {
    this.terminalData = terminalData;
  }

  public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    this.address = address;
  }

  public String getCity() {
    return city;
  }

  public void setCity(String city) {
    this.city = city;
  }

  public String getState() {
    return state;
  }

  public void setState(String state) {
    this.state = state;
  }

  public String getCountry() {
    return country;
  }

  public void setCountry(String country) {
    this.country = country;
  }

  public String getPin() {
    return pin;
  }

  public void setPin(String pin) {
    this.pin = pin;
  }

  public ClientCurrencyDTO getCurrencyDTO() {
    return currencyDTO;
  }

  public void setCurrencyDTO(ClientCurrencyDTO currencyDTO) {
    this.currencyDTO = currencyDTO;
  }

  public Boolean getStatus() {
    return status;
  }

  public void setStatus(Boolean status) {
    this.status = status;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }
}
