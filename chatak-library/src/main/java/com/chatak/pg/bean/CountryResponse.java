package com.chatak.pg.bean;

import java.util.List;

public class CountryResponse extends Response {

  private static final long serialVersionUID = 4837915395324470808L;

  private List<CountryRequest> listOfCountryRequests;

  public List<CountryRequest> getListOfCountryRequests() {
    return listOfCountryRequests;
  }

  public void setListOfCountryRequests(List<CountryRequest> listOfCountryRequests) {
    this.listOfCountryRequests = listOfCountryRequests;
  }
}
