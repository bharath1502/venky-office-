package com.chatak.pg.model;

import java.io.Serializable;

public class MerchantName implements Serializable{

  /**
   * 
   */
  private static final long serialVersionUID = -8686579704790148438L;
  
  private String merchantName;

  private String merchantCode;

  public String getMerchantName() {
    return merchantName;
  }

  public void setMerchantName(String merchantName) {
    this.merchantName = merchantName;
  }

  public String getMerchantCode() {
    return merchantCode;
  }

  public void setMerchantCode(String merchantCode) {
    this.merchantCode = merchantCode;
  }

}
