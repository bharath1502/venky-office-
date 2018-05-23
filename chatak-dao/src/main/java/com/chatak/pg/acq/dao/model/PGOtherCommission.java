package com.chatak.pg.acq.dao.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "PG_COMMISSION_PROGRAM_OTHER")
public class PGOtherCommission implements Serializable
{
	private static final long serialVersionUID = -5521117462265856433L;

	@Id
	/*@SequenceGenerator(name = "seq_pg_commission_other_id", sequenceName = "SEQ_PG_OTHER_COMMISSION")
	@GeneratedValue(generator = "seq_pg_commission_other_id")*/
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID")
	private Long id;

	 @Column(name = "COMMISSION_PROGRAM_ID")
	 private Long commissionProgramId;
	
	 @Column(name = "COMMISSION_TYPE")
	 private String commissionType;
	
	 @Column(name = "FLAT_FEE")
	 private Double flatFee;

	 @Column(name = "FROM_VALUE")
	 private Double fromValue;
	
	 @Column(name = "TO_VALUE")
	 private Double toValue;
	
	 @Column(name = "AMOUNT")
	 private Double amount;

	 public Long getId() {
		return id;
	 }

	 public void setId(Long id) {
		this.id = id;
	 }

	 public Long getCommissionProgramId() {
		return commissionProgramId;
	 }

	 public void setCommissionProgramId(Long commissionProgramId) {
		this.commissionProgramId = commissionProgramId;
	 }

	 public String getCommissionType() {
		return commissionType;
	 }

	 public void setCommissionType(String commissionType) {
		this.commissionType = commissionType;
	 }

	/**
	 * @return the flatFee
	 */
	public Double getFlatFee() {
		return flatFee;
	}

	/**
	 * @param flatFee the flatFee to set
	 */
	public void setFlatFee(Double flatFee) {
		this.flatFee = flatFee;
	}

	/**
	 * @return the fromValue
	 */
	public Double getFromValue() {
		return fromValue;
	}

	/**
	 * @param fromValue the fromValue to set
	 */
	public void setFromValue(Double fromValue) {
		this.fromValue = fromValue;
	}

	/**
	 * @return the toValue
	 */
	public Double getToValue() {
		return toValue;
	}

	/**
	 * @param toValue the toValue to set
	 */
	public void setToValue(Double toValue) {
		this.toValue = toValue;
	}

	/**
	 * @return the amount
	 */
	public Double getAmount() {
		return amount;
	}

	/**
	 * @param amount the amount to set
	 */
	public void setAmount(Double amount) {
		this.amount = amount;
	}

	
}