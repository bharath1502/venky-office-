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
@Table(name = "PG_PARAMETER_MAGSTRIPE")
public class PGParameterMagstripe implements Serializable  {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	/*@SequenceGenerator(name = "SEQ_PG_PARAMETER_MAGSTRIPE_ID", sequenceName = "SEQ_PG_PARAMETER_MAGSTRIPE")
	@GeneratedValue(generator = "SEQ_PG_PARAMETER_MAGSTRIPE_ID")*/
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "MAGSTRIPE_ID")
	private Long magstripeId;
	
	@Column(name = "MAGSTRIPE_NAME")
	private String magstripeName;

	@Column(name = "CARD_RANGE_LOW")
	private Long cardRangeLow;
	
	@Column(name = "CARD_RANGE_HIGH")
	private Long cardRangeHigh;
	
	@Column(name = "CARD_LABEL")
	private String cardLabel;
	
	@Column(name = "PAN_LENGTH")
	private String panLength;
	
	@Column(name = "STATUS")
	private String status;
	
	@Column(name = "CREATED_DATE")
	private Timestamp createdDate;
	
	@Column(name = "UPDATED_DATE")
	private Timestamp updatedDate;
	
	@Column(name = "CREATED_BY")
	private String createdBy;

	@Column(name = "UPDATED_BY")
	private String updatedBy;

	/**
	 * @return the magstripeId
	 */
	public Long getMagstripeId() {
		return magstripeId;
	}

	/**
	 * @param magstripeId the magstripeId to set
	 */
	public void setMagstripeId(Long magstripeId) {
		this.magstripeId = magstripeId;
	}

	/**
	 * @return the magstripeName
	 */
	public String getMagstripeName() {
		return magstripeName;
	}

	/**
	 * @param magstripeName the magstripeName to set
	 */
	public void setMagstripeName(String magstripeName) {
		this.magstripeName = magstripeName;
	}

	/**
	 * @return the cardRangeLow
	 */
	public Long getCardRangeLow() {
		return cardRangeLow;
	}

	/**
	 * @param cardRangeLow the cardRangeLow to set
	 */
	public void setCardRangeLow(Long cardRangeLow) {
		this.cardRangeLow = cardRangeLow;
	}

	/**
	 * @return the cardRangeHigh
	 */
	public Long getCardRangeHigh() {
		return cardRangeHigh;
	}

	/**
	 * @param cardRangeHigh the cardRangeHigh to set
	 */
	public void setCardRangeHigh(Long cardRangeHigh) {
		this.cardRangeHigh = cardRangeHigh;
	}

	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}
	
	/**
	 * @return the cardLabel
	 */
	public String getCardLabel() {
		return cardLabel;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * @return the createdDate
	 */
	public Timestamp getCreatedDate() {
		return createdDate;
	}
	
	/**
	 * @param cardLabel the cardLabel to set
	 */
	public void setCardLabel(String cardLabel) {
		this.cardLabel = cardLabel;
	}

	/**
	 * @param createdDate the createdDate to set
	 */
	public void setCreatedDate(Timestamp createdDate) {
		this.createdDate = createdDate;
	}

	/**
	 * @return the updatedDate
	 */
	public Timestamp getUpdatedDate() {
		return updatedDate;
	}
	
	/**
	 * @return the panLength
	 */
	public String getPanLength() {
		return panLength;
	}

	/**
	 * @param updatedDate the updatedDate to set
	 */
	public void setUpdatedDate(Timestamp updatedDate) {
		this.updatedDate = updatedDate;
	}
	
	/**
	 * @param updatedBy the updatedBy to set
	 */
	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	/**
	 * @return the createdBy
	 */
	public String getCreatedBy() {
		return createdBy;
	}
	
	/**
	 * @param panLength the panLength to set
	 */
	public void setPanLength(String panLength) {
		this.panLength = panLength;
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

}
