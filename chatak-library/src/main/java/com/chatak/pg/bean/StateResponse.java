package com.chatak.pg.bean;

import java.util.List;

public class StateResponse extends Response {

  private static final long serialVersionUID = -8794243608714409190L;

  private List<StateRequest> listOfStateRequests;

  public List<StateRequest> getListOfStateRequests() {
    return listOfStateRequests;
  }

  public void setListOfStateRequests(List<StateRequest> listOfStateRequests) {
    this.listOfStateRequests = listOfStateRequests;
  }
}
