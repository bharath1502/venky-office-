package com.chatak.pg.acq.dao.model;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "PG_CA_PUBLIC_KEYS")
public class PGCaPublicKeys implements Serializable {

	private static final long serialVersionUID = -3661207085000520142L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "PUBLIC_KEY_ID")
	private Long publicKeyId;

	@Column(name = "PUBLIC_KEY_NAME")
	private String publicKeyName;

	@Column(name = "CREATED_BY")
	private String createdBy;

	@Column(name = "CREATED_DATE")
	private Timestamp createdDate;

	@Column(name = "STATUS")
	private String status;

	@Column(name = "R_ID")
	private String rId;

	@Column(name = "PUBLIC_KEY_MODULUS")
	private String publicKeyModulus;

	@Column(name = "PUBLIC_KEY_EXPONENT")
	private Long publicKeyExponent;

	@Column(name = "EXPIRY_DATE")
	private String expiryDate;

	@Column(name = "PUBLIC_KEY_INDEX")
	private String publicKeyIndex;
	
	@Column(name = "REASON")
	private String reason;

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Long getPublicKeyId() {
		return publicKeyId;
	}

	public void setPublicKeyId(Long publicKeyId) {
		this.publicKeyId = publicKeyId;
	}

	public String getPublicKeyName() {
		return publicKeyName;
	}

	public void setPublicKeyName(String publicKeyName) {
		this.publicKeyName = publicKeyName;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Timestamp getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Timestamp createdDate) {
		this.createdDate = createdDate;
	}

	public String getrId() {
		return rId;
	}

	public void setrId(String rId) {
		this.rId = rId;
	}

	public String getPublicKeyModulus() {
		return publicKeyModulus;
	}

	public void setPublicKeyModulus(String publicKeyModulus) {
		this.publicKeyModulus = publicKeyModulus;
	}

	public Long getPublicKeyExponent() {
		return publicKeyExponent;
	}

	public void setPublicKeyExponent(Long publicKeyExponent) {
		this.publicKeyExponent = publicKeyExponent;
	}

	public String getExpiryDate() {
		return expiryDate;
	}

	public void setExpiryDate(String expiryDate) {
		this.expiryDate = expiryDate;
	}

	public String getPublicKeyIndex() {
		return publicKeyIndex;
	}

	public void setPublicKeyIndex(String publicKeyIndex) {
		this.publicKeyIndex = publicKeyIndex;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

}
