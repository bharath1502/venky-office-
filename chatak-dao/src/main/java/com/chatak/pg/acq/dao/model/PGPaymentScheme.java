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
 * @Date: Aug 5, 2016
 * @Time: 8:09:09 PM
 * @Version: 1.0
 * @Comments:
 *
 */
@Entity
@Table(name = "PG_PAYMENT_SCHEME")
public class PGPaymentScheme implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -9034230275554576965L;

	@Id
	/*@SequenceGenerator(name = "SEQ_PG_PAYMENT_SCHEME_ID", sequenceName = "SEQ_PG_PAYMENT_SCHEME_ID")
	@GeneratedValue(generator = "SEQ_PG_PAYMENT_SCHEME_ID")*/
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID")
	private Long id;

	@Column(name = "PAYMENT_SCHEME_NAME")
	private String paymentSchemeName;

	@Column(name = "CONTACT_NAME")
	private String contactName;

	@Column(name = "CONTACT_EMAIL")
	private String contactEmail;

	@Column(name = "CONTACT_PHONE")
	private String contactPhone;

	@Column(name = "RID")
	private String rid;

	@Column(name = "STATUS")
	private Integer status;

	@Column(name = "TYPE_OF_CARD")
	private String typeOfCard;

	@Column(name = "CREATED_BY")
	private String createdBy;

	@Column(name = "CREATED_DATE")
	private Timestamp createdDate;

	@Column(name = "UPDATED_DATE")
	private Timestamp updatedDate;

	@Column(name = "UPDATED_BY")
	private String updatedBy;
	
	@Column(name = "REASON")
	private String reason;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getPaymentSchemeName() {
		return paymentSchemeName;
	}

	public void setContactName(String contactName) {
		this.contactName = contactName;
	}

	public String getContactEmail() {
		return contactEmail;
	}
	
	public void setPaymentSchemeName(String paymentSchemeName) {
		this.paymentSchemeName = paymentSchemeName;
	}

	public String getContactName() {
		return contactName;
	}

	public void setContactPhone(String contactPhone) {
		this.contactPhone = contactPhone;
	}

	public String getRid() {
		return rid;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getTypeOfCard() {
		return typeOfCard;
	}
	
	public void setRid(String rid) {
		this.rid = rid;
	}

	public void setTypeOfCard(String typeOfCard) {
		this.typeOfCard = typeOfCard;
	}

	public String getCreatedBy() {
		return createdBy;
	}
	
	public Integer getStatus() {
		return status;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Timestamp getCreatedDate() {
		return createdDate;
	}
	
	public void setContactEmail(String contactEmail) {
		this.contactEmail = contactEmail;
	}

	public void setCreatedDate(Timestamp createdDate) {
		this.createdDate = createdDate;
	}

	public Timestamp getUpdatedDate() {
		return updatedDate;
	}
	
	public String getContactPhone() {
		return contactPhone;
	}

	public void setUpdatedDate(Timestamp updatedDate) {
		this.updatedDate = updatedDate;
	}
	
	public String getReason() {
		return reason;
	}

	public String getUpdatedBy() {
		return updatedBy;
	}
	
	public void setReason(String reason) {
		this.reason = reason;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

}
