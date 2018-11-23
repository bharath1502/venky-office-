/**
 * 
 */
package com.chatak.pg.acq.dao.model;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @Author: Girmiti Software
 * @Date: Sep 15, 2015
 * @Time: 5:55:21 PM
 * @Version: 1.0
 * @Comments:
 *
 */
@Entity
@Table(name = "PG_USER_ACTIVITY_LOG")
public class PGUserActivityLog implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8292905819906805930L;

	@Id
	/*@SequenceGenerator(name = "SEQ_PG_USER_ACTIVITY_LOG_ID", sequenceName = "SEQ_PG_USER_ACTIVITY_LOG")
	@GeneratedValue(generator = "SEQ_PG_USER_ACTIVITY_LOG_ID")*/
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ACTIVITY_LOG_ID")
	private Long activityLogId;
	@Column(name = "USER_ID")
	private String userId;
	@Column(name = "LOGIN_SESSION_ID")
	private String loginSessionId;
	@Column(name = "AUDIT_SECTION")
	private String auditSection;
	@Column(name = "AUDIT_EVENT")
	private String auditEvent;
	@Column(name = "AUDIT_SERVICE")
	private String auditService;
	@Column(name = "REQUEST_DATA")
	private String requestData;
	@Column(name = "DATA_CHANGE")
	private String dataChange;
	@Column(name = "STATUS")
	private String status;
	@Column(name = "DESCRIPTION")
	private String description;
	@Column(name = "IP_ADDRESS")
	private String ipAddress;
	@Column(name = "ACTIVITY_DATE")
	private Timestamp activityDate;

	public Long getActivityLogId() {
		return activityLogId;
	}

	public void setActivityLogId(Long activityLogId) {
		this.activityLogId = activityLogId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getLoginSessionId() {
		return loginSessionId;
	}

	public void setLoginSessionId(String loginSessionId) {
		this.loginSessionId = loginSessionId;
	}

	public String getAuditSection() {
		return auditSection;
	}

	public void setAuditSection(String auditSection) {
		this.auditSection = auditSection;
	}

	public String getAuditEvent() {
		return auditEvent;
	}

	public void setAuditEvent(String auditEvent) {
		this.auditEvent = auditEvent;
	}

	public String getAuditService() {
		return auditService;
	}

	public void setAuditService(String auditService) {
		this.auditService = auditService;
	}

	public String getRequestData() {
		return requestData;
	}

	public void setRequestData(String requestData) {
		this.requestData = requestData;
	}

	public String getDataChange() {
		return dataChange;
	}

	public void setDataChange(String dataChange) {
		this.dataChange = dataChange;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	public Timestamp getActivityDate() {
		return activityDate;
	}

	public void setActivityDate(Timestamp activityDate) {
		this.activityDate = activityDate;
	}

}
