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
 * @Date: Jun 27, 2018
 * @Time: 7:05:31 PM
 * @Version: 1.0
 * @Comments:
 *
 */
@Entity
@Table(name = "PG_ISS_SETTLEMENT_TXN_H")
public class PGSettlementTransactionHistory implements Serializable {

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

	@Column(name = "ISS_SETTLEMENT_ENTITY_ID")
	private Long issuanceSettlementEntityId;

	@Column(name = "TID")
	private String terminalId;

	@Column(name = "ISS_TXN_ID")
	private String issuerTxnID;

	@Column(name = "PG_TXN_ID")
	private String pgTransactionId;

	@Column(name = "TXN_DATE_TIME")
	private Timestamp txnDate;

	@Column(name = "ISO_ID")
	private Long isoId;

	@Column(name = "ISO_AMOUNT")
	private Long isoAmount;
	
	@Column(name = "TXN_TYPE")
	private String transactionType;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTerminalId() {
		return terminalId;
	}

	public void setTerminalId(String terminalId) {
		this.terminalId = terminalId;
	}

	public String getIssuerTxnID() {
		return issuerTxnID;
	}

	public void setIssuerTxnID(String issuerTxnID) {
		this.issuerTxnID = issuerTxnID;
	}

	public String getPgTransactionId() {
		return pgTransactionId;
	}

	public void setPgTransactionId(String pgTransactionId) {
		this.pgTransactionId = pgTransactionId;
	}

	public Timestamp getTxnDate() {
		return txnDate;
	}

	public void setTxnDate(Timestamp txnDate) {
		this.txnDate = txnDate;
	}

	public Long getIsoId() {
		return isoId;
	}

	public Long getIsoAmount() {
		return isoAmount;
	}

	public void setIsoId(Long isoId) {
		this.isoId = isoId;
	}

	public void setIsoAmount(Long isoAmount) {
		this.isoAmount = isoAmount;
	}

	public Long getIssuanceSettlementEntityId() {
		return issuanceSettlementEntityId;
	}

	public void setIssuanceSettlementEntityId(Long issuanceSettlementEntityId) {
		this.issuanceSettlementEntityId = issuanceSettlementEntityId;
	}

	public String getTransactionType() {
		return transactionType;
	}

	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}

}
