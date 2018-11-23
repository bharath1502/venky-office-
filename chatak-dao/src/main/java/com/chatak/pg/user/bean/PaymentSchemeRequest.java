/**
 * 
 */
package com.chatak.pg.user.bean;

import java.sql.Timestamp;

/**
 * @Author: Girmiti Software
 * @Date: Aug 5, 2016
 * @Time: 7:27:08 PM
 * @Version: 1.0
 * @Comments:
 *
 */
public class PaymentSchemeRequest extends SearchRequest {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1256299069042941952L;
	
	private Long id;

	private String paymentSchemeName;
	
	private String contactName;
	
	private String contactEmail;
	
	private String contactPhone;
	
	private String rid;
	
	private Integer status;
	
	private String typeOfCard;
	
	private String createdBy;
	
	private Timestamp createdDate;
	
	private Timestamp updatedDate;
	
	private String updatedBy;
	
	private String statusDisp;
	
	private String recordsPerPage;
	
	private String reason;

	public String getStatusDisp() {
		return statusDisp;
	}

	public void setStatusDisp(String statusDisp) {
		this.statusDisp = statusDisp;
	}

	public String getPaymentSchemeName() {
		return paymentSchemeName;
	}

	public void setPaymentSchemeName(String paymentSchemeName) {
		this.paymentSchemeName = paymentSchemeName;
	}

	public String getContactName() {
		return contactName;
	}

	public void setContactName(String contactName) {
		this.contactName = contactName;
	}

	public String getContactEmail() {
		return contactEmail;
	}

	public void setContactEmail(String contactEmail) {
		this.contactEmail = contactEmail;
	}

	public String getContactPhone() {
		return contactPhone;
	}

	public void setContactPhone(String contactPhone) {
		this.contactPhone = contactPhone;
	}

	public String getCreatedBy() {
		return createdBy;
	}
	
	public void setTypeOfCard(String typeOfCard) {
		this.typeOfCard = typeOfCard;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	
	public String getTypeOfCard() {
		return typeOfCard;
	}

	public Timestamp getCreatedDate() {
		return createdDate;
	}
	
	public void setStatus(Integer status) {
		this.status = status;
	}

	public void setCreatedDate(Timestamp createdDate) {
		this.createdDate = createdDate;
	}
	
	public Integer getStatus() {
		return status;
	}

	public Timestamp getUpdatedDate() {
		return updatedDate;
	}
	
	public void setRid(String rid) {
		this.rid = rid;
	}

	public void setUpdatedDate(Timestamp updatedDate) {
		this.updatedDate = updatedDate;
	}
	
	public String getRid() {
		return rid;
	}

	public String getUpdatedBy() {
		return updatedBy;
	}
	
	public Long getId() {
		return id;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getRecordsPerPage() {
		return recordsPerPage;
	}

	public void setRecordsPerPage(String recordsPerPage) {
		this.recordsPerPage = recordsPerPage;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

}
