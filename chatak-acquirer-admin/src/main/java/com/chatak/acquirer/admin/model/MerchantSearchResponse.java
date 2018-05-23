package com.chatak.acquirer.admin.model;

import java.util.List;

import com.chatak.pg.acq.dao.model.PGMerchant;

public class MerchantSearchResponse extends Response {

  /**
   * 
   */
  private static final long serialVersionUID = 6306783400381206195L;

  private List<MerchantData> merchants;
  
  private List<MerchantData> subMerchants;
  
  private PGMerchant merchantData;

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

  /**
   * @return the subMerchants
   */
  public List<MerchantData> getSubMerchants() {
    return subMerchants;
  }

  /**
   * @param subMerchants the subMerchants to set
   */
  public void setSubMerchants(List<MerchantData> subMerchants) {
    this.subMerchants = subMerchants;
  }

public PGMerchant getMerchantData() {
	return merchantData;
}

public void setMerchantData(PGMerchant merchantData) {
	this.merchantData = merchantData;
}
  


}
