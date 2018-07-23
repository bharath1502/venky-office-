package com.chatak.pg.model;

import java.io.Serializable;

public class MerchantName implements Serializable{

  /**
   * 
   */
  private static final long serialVersionUID = -8686579704790148438L;
  
  private String name;

  private String merchantCode;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getMerchantCode() {
    return merchantCode;
  }

  public void setMerchantCode(String merchantCode) {
    this.merchantCode = merchantCode;
  }

}
