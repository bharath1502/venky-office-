package com.chatak.pg.model;

import java.io.Serializable;

public class VirtualAccGetAgentsRequest implements Serializable {
  
/**
   * 
   */
  private static final long serialVersionUID = 6102435823019031127L;
private String partnerId;

public String getPartnerId() {
  return partnerId;
}

public void setPartnerId(String partnerId) {
  this.partnerId = partnerId;
}

}
