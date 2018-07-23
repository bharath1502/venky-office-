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
@Table(name = "PG_FEE_DETAIL")
public class PGFeeDetail implements Serializable{
	
	/**
   * 
   */
  private static final long serialVersionUID = -3667695203007325695L;

  @Id
  /*@SequenceGenerator(name = "SEQ_PG_FEE_DETAIL_ID", sequenceName = "SEQ_PG_FEE_DETAIL")
  @GeneratedValue(generator = "SEQ_PG_FEE_DETAIL_ID")*/
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "ID")
  private Long id;  
	
  @Column(name = "TXN_TYPE")
	private String txnType;  
	
  @Column(name = "FEE_AMOUNT")
	private Long feeAmount;
	
  @Column(name = "CREATED_DATE")
	private Timestamp ceratedDate;
  
  @Column(name = "STATUS")
  private Integer status;

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the txnType
	 */
	public String getTxnType() {
		return txnType;
	}

	/**
	 * @param txnType the txnType to set
	 */
	public void setTxnType(String txnType) {
		this.txnType = txnType;
	}

	/**
	 * @return the feeAmount
	 */
	public Long getFeeAmount() {
		return feeAmount;
	}

	/**
	 * @param feeAmount the feeAmount to set
	 */
	public void setFeeAmount(Long feeAmount) {
		this.feeAmount = feeAmount;
	}

	/**
	 * @return the ceratedDate
	 */
	public Timestamp getCeratedDate() {
		return ceratedDate;
	}

	/**
	 * @param ceratedDate the ceratedDate to set
	 */
	public void setCeratedDate(Timestamp ceratedDate) {
		this.ceratedDate = ceratedDate;
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

}
