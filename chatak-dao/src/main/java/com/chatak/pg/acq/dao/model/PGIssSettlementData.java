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
 * @Date: Jun 12, 2018
 * @Time: 6:37:52 PM
 * @Version: 1.0
 * @Comments: 
 *
 */
@Entity
@Table(name = "PG_ISS_SETTLEMENT_DATA")
public class PGIssSettlementData implements Serializable {
	
	private static final long serialVersionUID = 5593907271831728272L;
	
	  @Id
	  @GeneratedValue(strategy = GenerationType.AUTO)
	  @Column(name = "ID")
	  private Long id;
	  
	  @Column(name = "ISS_PM_ID")
	  private Long programManagerId;
	  
	  @Column(name = "TOTAL_AMOUNT")
	  private BigInteger totalAmount;
	  
	  @Column(name = "BATCH_DATE")
	  private Timestamp batchDate;
	  
	  @Column(name = "TOTAL_TXN_COUNT")
	  private Integer totalTxnCount;
	  
	  @Column(name = "PM_NAME")
	  private String programManagerName;
	  
	  @Column(name = "STATUS")
	  private String status;
	  
	  @Column(name = "ACQ_PM_ID")
	  private Long acqPmId;

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

	public BigInteger getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(BigInteger totalAmount) {
		this.totalAmount = totalAmount;
	}

	public Timestamp getBatchDate() {
		return batchDate;
	}

	public void setBatchDate(Timestamp batchDate) {
		this.batchDate = batchDate;
	}

	public Integer getTotalTxnCount() {
		return totalTxnCount;
	}

	public void setTotalTxnCount(Integer totalTxnCount) {
		this.totalTxnCount = totalTxnCount;
	}

	public String getProgramManagerName() {
		return programManagerName;
	}

	public void setProgramManagerName(String programManagerName) {
		this.programManagerName = programManagerName;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Long getAcqPmId() {
		return acqPmId;
	}

	public void setAcqPmId(Long acqPmId) {
		this.acqPmId = acqPmId;
	}
}
