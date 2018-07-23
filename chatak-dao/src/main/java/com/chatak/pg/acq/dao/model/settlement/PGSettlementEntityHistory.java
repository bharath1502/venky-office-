/**
 * 
 */
package com.chatak.pg.acq.dao.model.settlement;

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
 * @Date: Jul 4, 2018
 * @Time: 6:43:25 PM
 * @Version: 1.0
 * @Comments:
 *
 */
@Entity
@Table(name = "PG_ISS_SETTLEMENT_ENTITY_H")
public class PGSettlementEntityHistory implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "HISTORY_ID")
	private Long historyId;

	@Column(name = "ID")
	private Long id;

	@Column(name = "MID")
	private String merchantId;

	@Column(name = "ACQ_SALE_AMOUNT")
	private BigInteger acqSaleAmount = new BigInteger("0");

	@Column(name = "ISS_SALE_AMOUNT")
	private BigInteger issSaleAmount = new BigInteger("0");

	@Column(name = "ACQ_PM_ID")
	private Long acqPmId;

	@Column(name = "ISS_PM_ID")
	private Long issPmId;

	@Column(name = "BATCH_ID")
	private String batchid;

	@Column(name = "BATCH_FILE_DATE")
	private Timestamp batchFileDate;

	@Column(name = "BATCH_FILE_PROCESSED_DATE")
	private Timestamp batchFileProcessedDate;

	@Column(name = "STATUS")
	private String status;

	@Column(name = "PM_AMOUNT")
	private Long pmAmount;

	@Column(name = "MERCHANT_AMOUNT")
	private Long merchantAmount;

	public Long getHistoryId() {
		return historyId;
	}

	public void setHistoryId(Long historyId) {
		this.historyId = historyId;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}

	public BigInteger getAcqSaleAmount() {
		return acqSaleAmount;
	}

	public void setAcqSaleAmount(BigInteger acqSaleAmount) {
		this.acqSaleAmount = acqSaleAmount;
	}

	public BigInteger getIssSaleAmount() {
		return issSaleAmount;
	}

	public void setIssSaleAmount(BigInteger issSaleAmount) {
		this.issSaleAmount = issSaleAmount;
	}

	public Long getAcqPmId() {
		return acqPmId;
	}

	public void setAcqPmId(Long acqPmId) {
		this.acqPmId = acqPmId;
	}

	public Long getIssPmId() {
		return issPmId;
	}

	public void setIssPmId(Long issPmId) {
		this.issPmId = issPmId;
	}

	public String getBatchid() {
		return batchid;
	}

	public void setBatchid(String batchid) {
		this.batchid = batchid;
	}

	public Timestamp getBatchFileDate() {
		return batchFileDate;
	}

	public void setBatchFileDate(Timestamp batchFileDate) {
		this.batchFileDate = batchFileDate;
	}

	public Timestamp getBatchFileProcessedDate() {
		return batchFileProcessedDate;
	}

	public void setBatchFileProcessedDate(Timestamp batchFileProcessedDate) {
		this.batchFileProcessedDate = batchFileProcessedDate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Long getPmAmount() {
		return pmAmount;
	}

	public void setPmAmount(Long pmAmount) {
		this.pmAmount = pmAmount;
	}

	public Long getMerchantAmount() {
		return merchantAmount;
	}

	public void setMerchantAmount(Long merchantAmount) {
		this.merchantAmount = merchantAmount;
	}

}
