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
public class GetDailyFundingReportRequest implements Serializable {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  private String fromDate;

  private String toDate;

  private Integer pageIndex;

  private Integer pageSize;

  private Integer noOfRecords;
  
  private Long id;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Integer getPageIndex() {
    return pageIndex;
  }
  
  public void setFromDate(String fromDate) {
    this.fromDate = fromDate;
  }

  public void setPageIndex(Integer pageIndex) {
    this.pageIndex = pageIndex;
  }
  
  public String getToDate() {
    return toDate;
  }

  public Integer getPageSize() {
    return pageSize;
  }
  
  public String getFromDate() {
    return fromDate;
  }

  public void setPageSize(Integer pageSize) {
    this.pageSize = pageSize;
  }

  public Integer getNoOfRecords() {
    return noOfRecords;
  }
  
  public void setToDate(String toDate) {
    this.toDate = toDate;
  }

  public void setNoOfRecords(Integer noOfRecords) {
    this.noOfRecords = noOfRecords;
  }

}
