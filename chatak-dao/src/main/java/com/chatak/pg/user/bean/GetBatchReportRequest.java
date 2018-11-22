/**
 * 
 */
package com.chatak.pg.user.bean;

import java.io.Serializable;

/**
 * @Author: Girmiti Software
 * @Date: Sep 20, 2017
 * @Time: 2:14:01 PM
 * @Version: 1.0
 * @Comments: 
 *
 */
public class GetBatchReportRequest implements Serializable {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  private String merchantCode;

  private String subMerchantCode;

  private String fromDate;
  
  private Integer pageIndex;

  private String toDate;

  private Integer pageSize;
  
  private Long id;

  private Integer noOfRecords;
  
  public void setId(Long id) {
    this.id = id;
  }

  public String getFromDate() {
    return fromDate;
  }
  
  public Long getId() {
    return id;
  }

  public void setFromDate(String fromDate) {
    this.fromDate = fromDate;
  }

  public String getToDate() {
    return toDate;
  }
  
  public String getMerchantCode() {
    return merchantCode;
  }

  public void setToDate(String toDate) {
    this.toDate = toDate;
  }

  public Integer getPageIndex() {
    return pageIndex;
  }
  
  public String getSubMerchantCode() {
    return subMerchantCode;
  }

  public void setPageIndex(Integer pageIndex) {
    this.pageIndex = pageIndex;
  }

  public Integer getPageSize() {
    return pageSize;
  }
  
  public void setMerchantCode(String merchantCode) {
    this.merchantCode = merchantCode;
  }

  public void setPageSize(Integer pageSize) {
    this.pageSize = pageSize;
  }
  
  public void setSubMerchantCode(String subMerchantCode) {
    this.subMerchantCode = subMerchantCode;
  }

  public Integer getNoOfRecords() {
    return noOfRecords;
  }

  public void setNoOfRecords(Integer noOfRecords) {
    this.noOfRecords = noOfRecords;
  }

}
