/**
 * 
 */
package com.chatak.pg.bean;

import java.io.Serializable;
import java.sql.Timestamp;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

/**
 * Request class to hold search request with page details
 * 
 * @author Girmiti Software
 * @date 01-Sep-2014 11:59:31 AM
 * @version 1.0
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class SearchRequest extends Response implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2650148679968778359L;

	private Integer pageIndex;

	private String createdBy;

	private Integer pageSize;

	private Integer noOfRecords;
	
	private String updatedBy;
	
	private Timestamp updatedDate;
	
	private Timestamp createdDate;
	
	private String reason;

	private String userName;

	private Boolean isAuditable;

	private String dataChange;

  private String timeZoneOffset;
  
  private String timeZoneRegion;	

  public Integer getNoOfRecords() {
		return noOfRecords;
	}
	
	/**
   * @return the pageIndex
   */
  public Integer getPageIndex() {
    return pageIndex;
  }

	public void setNoOfRecords(Integer noOfRecords) {
		this.noOfRecords = noOfRecords;
	}

	/**
	 * @return the pageSize
	 */
	public Integer getPageSize() {
		return pageSize;
	}
	
	/**
   * @param pageIndex
   *          the pageIndex to set
   */
  public void setPageIndex(Integer pageIndex) {
    this.pageIndex = pageIndex;
  }

	/**
	 * @param pageSize
	 *          the pageSize to set
	 */
	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	/**
	 * @return the createdBy
	 */
	public String getCreatedBy() {
		return createdBy;
	}

	/**
	 * @param createdBy the createdBy to set
	 */
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	/**
	 * @return the updatedBy
	 */
	public String getUpdatedBy() {
		return updatedBy;
	}
	
	/**
   * @return the updatedDate
   */
  public Timestamp getUpdatedDate() {
    return updatedDate;
  }

	/**
	 * @param updatedBy the updatedBy to set
	 */
	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	/**
	 * @return the createdDate
	 */
	public Timestamp getCreatedDate() {
		return createdDate;
	}
	
	/**
   * @param updatedDate the updatedDate to set
   */
  public void setUpdatedDate(Timestamp updatedDate) {
    this.updatedDate = updatedDate;
  }

	/**
	 * @param createdDate the createdDate to set
	 */
	public void setCreatedDate(Timestamp createdDate) {
		this.createdDate = createdDate;
	}
	
	/**
   * @return the reason
   */
	public String getReason() {
    return reason;
  }

	/**
   * @param reason the reason to set
   */
  public void setReason(String reason) {
    this.reason = reason;
  }

  public String getUserName() {
    return userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }

  public Boolean getIsAuditable() {
    return isAuditable;
  }

  public void setIsAuditable(Boolean isAuditable) {
    this.isAuditable = isAuditable;
  }

  public String getDataChange() {
    return dataChange;
  }

  public void setDataChange(String dataChange) {
    this.dataChange = dataChange;
  }

  public String getTimeZoneOffset() {
    return timeZoneOffset;
  }

  public void setTimeZoneOffset(String timeZoneOffset) {
    this.timeZoneOffset = timeZoneOffset;
  }

  public String getTimeZoneRegion() {
    return timeZoneRegion;
  }

  public void setTimeZoneRegion(String timeZoneRegion) {
    this.timeZoneRegion = timeZoneRegion;
  }
	
}
