/**
 * 
 */
package com.chatak.pg.user.bean;

import java.sql.Timestamp;

/**
 * @Author: Girmiti Software
 * @Date: Aug 1, 2016
 * @Time: 11:19:44 AM
 * @Version: 1.0
 * @Comments: 
 *
 */
public class SwitchRequest extends SearchRequest {
	
	private static final long serialVersionUID = 1L;

	private Long id;
	
	private String switchName;
	  
	private String switchType;
	  
	private String primarySwitchURL;
	  
	private Integer primarySwitchPort;
	  
	private String secondarySwitchURL;
	  
	private Integer secondarySwitchPort;
	  
	private Integer priority;
	  
	private Integer status;
	
	private String statusDisp;
	
	private String createdBy;
	
	private Timestamp createdDate;
	
	private Timestamp updatedDate;
	
	private String updatedBy;
	
	private String reason;
	

	public String getCreatedBy() {
		return createdBy;
	}
	
	public String getSwitchType() {
		return switchType;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	
	public Long getId() {
		return id;
	}

	public Timestamp getCreatedDate() {
		return createdDate;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Timestamp getUpdatedDate() {
		return updatedDate;
	}
	
	public void setSwitchName(String switchName) {
		this.switchName = switchName;
	}
	
	public void setCreatedDate(Timestamp createdDate) {
		this.createdDate = createdDate;
	}

	public void setUpdatedDate(Timestamp updatedDate) {
		this.updatedDate = updatedDate;
	}
	
	public String getSwitchName() {
		return switchName;
	}

	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	public void setSwitchType(String switchType) {
		this.switchType = switchType;
	}

	public String getPrimarySwitchURL() {
		return primarySwitchURL;
	}

	public void setPrimarySwitchURL(String primarySwitchURL) {
		this.primarySwitchURL = primarySwitchURL;
	}

	public Integer getPrimarySwitchPort() {
		return primarySwitchPort;
	}

	public void setPrimarySwitchPort(Integer primarySwitchPort) {
		this.primarySwitchPort = primarySwitchPort;
	}

	public String getSecondarySwitchURL() {
		return secondarySwitchURL;
	}

	public void setSecondarySwitchURL(String secondarySwitchURL) {
		this.secondarySwitchURL = secondarySwitchURL;
	}

	public Integer getSecondarySwitchPort() {
		return secondarySwitchPort;
	}

	public void setSecondarySwitchPort(Integer secondarySwitchPort) {
		this.secondarySwitchPort = secondarySwitchPort;
	}

	public Integer getPriority() {
		return priority;
	}

	public void setPriority(Integer priority) {
		this.priority = priority;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getStatusDisp() {
		return statusDisp;
	}

	public void setStatusDisp(String statusDisp) {
		this.statusDisp = statusDisp;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

}
