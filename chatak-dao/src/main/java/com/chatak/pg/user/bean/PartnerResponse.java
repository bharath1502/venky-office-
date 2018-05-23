package com.chatak.pg.user.bean;

import java.util.List;

public class PartnerResponse extends com.chatak.pg.bean.Response {
  /**
   * 
   */
  private static final long serialVersionUID = -5400205502452729221L;
  private List<PartnerRequest> partnerList;

  /**
   * @return the partnerList
   */
  public List<PartnerRequest> getPartnerList() {
    return partnerList;
  }

  /**
   * @param partnerList the partnerList to set
   */
  public void setPartnerList(List<PartnerRequest> partnerList) {
    this.partnerList = partnerList;
  }

}
