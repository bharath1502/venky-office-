package com.chatak.pg.acq.dao.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "PG_USER_ROLES")
public class PGUserRoles 
{
	@Id
	/*@SequenceGenerator(name = "SEQ_PG_USER_ROLES_ID", sequenceName = "SEQ_PG_USER_ROLES")
    @GeneratedValue(generator= "SEQ_PG_USER_ROLES_ID")*/
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ROLE_ID")
	private Long roleId;
	
	@Column(name = "ROLE_NAME")
	private String roleName;
	
	@Column(name = "STATUS")
	private Integer status;
	
	@Column(name = "ROLE_TYPE")
	private String roleType;
	
	@Column(name = "ROLE_DESCRIPTION")
	private String description;
	
	@Column(name = "CREATED_DATE")
	private Timestamp createdDate;

	@Column(name = "CREATED_BY")
	private String createdBy;

	@Column(name = "UPDATED_DATE")
	private Timestamp updatedDate;

	@Column(name = "UPDATED_BY")
	private String updatedBy;

	@Column(name = "REASON")
	private String reason;
	
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

	/**
	 * @return the roleId
	 */
	public Long getRoleId() {
		return roleId;
	}

	/**
	 * @param roleId the roleId to set
	 */
	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}

	/**
	 * @return the roleName
	 */
	public String getRoleName() {
		return roleName;
	}

	/**
	 * @param roleName the roleName to set
	 */
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	/**
	 * @return the status
	 */
	public Integer getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(Integer status) {
		this.status = status;
	}

	public Timestamp getCreatedDate() {
		return createdDate;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}

	public void setCreatedDate(Timestamp createdDate) {
		this.createdDate = createdDate;
	}

	public String getCreatedBy() {
		return createdBy;
	}
	
	public String getDescription() {
		return description;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Timestamp getUpdatedDate() {
		return updatedDate;
	}
	
	public String getRoleType() {
		return roleType;
	}

	public void setUpdatedDate(Timestamp updatedDate) {
		this.updatedDate = updatedDate;
	}

	public String getUpdatedBy() {
		return updatedBy;
	}
	
	public void setRoleType(String roleType) {
		this.roleType = roleType;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

}
