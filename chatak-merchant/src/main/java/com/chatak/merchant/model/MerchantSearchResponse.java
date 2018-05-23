package com.chatak.merchant.model;

import java.util.List;

public class MerchantSearchResponse extends Response {

  /**
   * 
   */
  private static final long serialVersionUID = 6306783400381206195L;

  private List<MerchantData> merchants;

  /**
   * @return the merchants
   */
  public List<MerchantData> getMerchants() {
    return merchants;
  }

  /**
   * @param merchants
   *          the merchants to set
   */
  public void setMerchants(List<MerchantData> merchants) {
    this.merchants = merchants;
  }


}
