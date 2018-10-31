/**
 * 
 */
package com.chatak.merchant.controller.model;

import java.io.Serializable;
import java.sql.Timestamp;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Model class to hold basic details for every request
 * 
 * @author Girmiti Software
 * @date 02-Jul-2014 10:49:42 AM
 * @version 1.0
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Request implements Serializable {

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

	/**
	 * @return the pageIndex
	 */
	public Integer getPageIndex() {
		return pageIndex;
	}

	/**
	 * @param pageIndex the pageIndex to set
	 */
	public void setPageIndex(Integer pageIndex) {
		this.pageIndex = pageIndex;
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
	 * @return the pageSize
	 */
	public Integer getPageSize() {
		return pageSize;
	}

	/**
	 * @param pageSize the pageSize to set
	 */
	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	/**
	 * @return the noOfRecords
	 */
	public Integer getNoOfRecords() {
		return noOfRecords;
	}

	/**
	 * @param noOfRecords the noOfRecords to set
	 */
	public void setNoOfRecords(Integer noOfRecords) {
		this.noOfRecords = noOfRecords;
	}

	/**
	 * @return the updatedBy
	 */
	public String getUpdatedBy() {
		return updatedBy;
	}

	/**
	 * @param updatedBy the updatedBy to set
	 */
	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	/**
	 * @return the updatedDate
	 */
	public Timestamp getUpdatedDate() {
		return updatedDate;
	}

	/**
	 * @param updatedDate the updatedDate to set
	 */
	public void setUpdatedDate(Timestamp updatedDate) {
		this.updatedDate = updatedDate;
	}

	/**
	 * @return the createdDate
	 */
	public Timestamp getCreatedDate() {
		return createdDate;
	}

	/**
	 * @param createdDate the createdDate to set
	 */
	public void setCreatedDate(Timestamp createdDate) {
		this.createdDate = createdDate;
	}
	
	
}
