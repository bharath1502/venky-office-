/**
 * 
 */
package com.chatak.pg.user.bean;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

/**
 * Request class to hold search request with page details
 * 
 * @author Girmiti Software
 * @date 01-Sep-2014 11:59:31 AM
 * @version 1.0
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class SearchRequest extends Request {

  /**
   * 
   */
  private static final long serialVersionUID = 2650148679968778359L;

  private Integer pageIndex;

  private Integer pageSize;
  
  private Integer noOfRecords;

  public Integer getNoOfRecords() {
	return noOfRecords;
}

public void setNoOfRecords(Integer noOfRecords) {
	this.noOfRecords = noOfRecords;
}

/**
   * @return the pageIndex
   */
  public Integer getPageIndex() {
    return pageIndex;
  }

  /**
   * @param pageIndex
   *          the pageIndex to set
   */
  public void setPageIndex(Integer pageIndex) {
    this.pageIndex = pageIndex;
  }

  /**
   * @return the pageSize
   */
  public Integer getPageSize() {
    return pageSize;
  }

  /**
   * @param pageSize
   *          the pageSize to set
   */
  public void setPageSize(Integer pageSize) {
    this.pageSize = pageSize;
  }
}
