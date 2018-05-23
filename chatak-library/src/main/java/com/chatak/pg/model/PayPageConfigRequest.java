package com.chatak.pg.model;

import com.chatak.pg.bean.SearchRequest;

public class PayPageConfigRequest extends SearchRequest {

  /**
   * 
   */
  private static final long serialVersionUID = -5109638154708882285L;

  private Long merchantId;

  private String header;

  private String footer;

  private byte[] payPageLogo;

  public Long getMerchantId() {
    return merchantId;
  }
  
  public void setPayPageLogo(byte[] payPageLogo) {
    this.payPageLogo = payPageLogo;
  }
  
  public String getHeader() {
    return header;
  }

  public void setMerchantId(Long merchantId) {
    this.merchantId = merchantId;
  }

  public void setHeader(String header) {
    this.header = header;
  }

  public String getFooter() {
    return footer;
  }
  
  public byte[] getPayPageLogo() {
    return payPageLogo;
  }

  public void setFooter(String footer) {
    this.footer = footer;
  }

}
