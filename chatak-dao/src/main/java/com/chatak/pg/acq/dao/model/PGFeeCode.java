package com.chatak.pg.acq.dao.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * << Add Comments Here >>
 *
 * @author Girmiti Software
 * @date 21-Apr-2015 2:12:40 PM
 * @version 1.0
 */
@Entity
@Table(name="PG_FEE_CODE")
public class PGFeeCode  implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 8909348947207108746L;

	@Id
	/*@SequenceGenerator(name="seq_fee_code_id", sequenceName="SEQ_FEE_CODE")
	@GeneratedValue(generator="seq_fee_code_id")*/
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "FEE_CODE")
	private Long feeCode;
	
	@Column(name = "FEE_SHORT_CODE")
	private String feeShortCode;
	
	@Column(name = "FEE_DESCRIPTION")
	private String feeDescription;
	
	@Column(name = "PROC_CODE_TXN_TYPE")
	private String txnShortCode;

	
	@Column(name = "RESPONSE_CODE")
	private String txnResponseCode;


	/**
	 * @return the feeCode
	 */
	public Long getFeeCode() {
		return feeCode;
	}


	/**
	 * @param feeCode the feeCode to set
	 */
	public void setFeeCode(Long feeCode) {
		this.feeCode = feeCode;
	}


	/**
	 * @return the feeShortCode
	 */
	public String getFeeShortCode() {
		return feeShortCode;
	}


	/**
	 * @param feeShortCode the feeShortCode to set
	 */
	public void setFeeShortCode(String feeShortCode) {
		this.feeShortCode = feeShortCode;
	}


	/**
	 * @return the feeDescription
	 */
	public String getFeeDescription() {
		return feeDescription;
	}


	/**
	 * @param feeDescription the feeDescription to set
	 */
	public void setFeeDescription(String feeDescription) {
		this.feeDescription = feeDescription;
	}


	/**
	 * @return the txnShortCode
	 */
	public String getTxnShortCode() {
		return txnShortCode;
	}


	/**
	 * @param txnShortCode the txnShortCode to set
	 */
	public void setTxnShortCode(String txnShortCode) {
		this.txnShortCode = txnShortCode;
	}


	/**
	 * @return the txnResponseCode
	 */
	public String getTxnResponseCode() {
		return txnResponseCode;
	}


	/**
	 * @param txnResponseCode the txnResponseCode to set
	 */
	public void setTxnResponseCode(String txnResponseCode) {
		this.txnResponseCode = txnResponseCode;
	}

	
	
}
