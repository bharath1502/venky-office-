/**
 * 
 */
package com.chatak.pg.acq.dao.model;

import java.io.Serializable;
import java.math.BigInteger;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @Author: Girmiti Software
 * @Date: Jun 6, 2018
 * @Time: 6:41:37 PM
 * @Version: 1.0
 * @Comments: 
 *
 */
@Entity
@Table(name = "PG_SETTLEMENT_REPORT")
public class PGSettlementReport implements Serializable {

	private static final long serialVersionUID = 5593907271831728272L;

	  @Id
	  @GeneratedValue(strategy = GenerationType.AUTO)
	  @Column(name = "ID")
	  private Long id;
	  
	  @Column(name = "PM_ID")
	  private Long programManagerId;
	  
	  @Column(name = "MERCHANT_ID")
	  private String merchantId;
	  
	  @Column(name = "SETTLEMENT_AMOUNT")
	  private BigInteger settlementAmount;
	  
	  @Column(name= "BATCH_ID")
	  private String batchId;
	  
	  @Column(name = "BATCH_TIME")
	  private Timestamp batchTime;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getProgramManagerId() {
		return programManagerId;
	}

	public void setProgramManagerId(Long programManagerId) {
		this.programManagerId = programManagerId;
	}

	public String getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}

	public BigInteger getSettlementAmount() {
		return settlementAmount;
	}

	public void setSettlementAmount(BigInteger settlementAmount) {
		this.settlementAmount = settlementAmount;
	}

	public String getBatchId() {
		return batchId;
	}

	public void setBatchId(String batchId) {
		this.batchId = batchId;
	}

	public Timestamp getBatchTime() {
		return batchTime;
	}

	public void setBatchTime(Timestamp batchTime) {
		this.batchTime = batchTime;
	}
}
