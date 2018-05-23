package com.chatak.pg.bean;

import java.util.List;

import com.chatak.pg.model.MerchantName;

public class MerchantNameResponse extends Response{

  /**
   * 
   */
  private static final long serialVersionUID = 2331330969251536295L;

  private List<MerchantName> merchantNames;

  /**
   * @return the merchantNames
   */
  public List<MerchantName> getMerchantNames() {
    return merchantNames;
  }

  /**
   * @param merchantNames the merchantNames to set
   */
  public void setMerchantNames(List<MerchantName> merchantNames) {
    this.merchantNames = merchantNames;
  }
  
  
}
