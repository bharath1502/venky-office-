package com.chatak.pg.user.bean;

import java.util.List;

import com.chatak.pg.acq.dao.model.PGMerchant;
import com.chatak.pg.model.MerchantRequest;

public class GetMerchantListResponse extends Response {

  /**
   * 
   */
  private static final long serialVersionUID = 6306783400381206195L;

  private List<PGMerchant> merchants;
  private List<PGMerchant> subMerchants;
  private List<MerchantRequest> merchantRequestList;
  private Integer noOfRecords;
  private List<MerchantRequest>merchantRequests;


  /**
   * @return the merchantRequestList
   */
  public List<MerchantRequest> getMerchantRequestList() {
	return merchantRequestList;
  }

  /**
   * @param merchantRequestList
   *            the merchantRequestList to set
   */
  public void setMerchantRequestList(List<MerchantRequest> merchantRequestList) {
	  this.merchantRequestList = merchantRequestList;
  }

  /**
   * @return the merchants
   */
  public List<PGMerchant> getMerchants() {
    return merchants;
  }

  /**
   * @param merchants
   *          the merchants to set
   */
  public void setMerchants(List<PGMerchant> merchants) {
    this.merchants = merchants;
  }

  /**
   * @return the noOfRecords
   */
  public Integer getNoOfRecords() {
    return noOfRecords;
  }

  /**
   * @param noOfRecords
   *          the noOfRecords to set
   */
  public void setNoOfRecords(Integer noOfRecords) {
    this.noOfRecords = noOfRecords;
  }

  /**
   * @return the subMerchants
   */
  public List<PGMerchant> getSubMerchants() {
    return subMerchants;
  }

  /**
   * @param subMerchants the subMerchants to set
   */
  public void setSubMerchants(List<PGMerchant> subMerchants) {
    this.subMerchants = subMerchants;
  }

 public List<MerchantRequest> getMerchantRequests() {
	return merchantRequests;
 }

 public void setMerchantRequests(List<MerchantRequest> merchantRequests) {
	this.merchantRequests = merchantRequests;
 }
}
