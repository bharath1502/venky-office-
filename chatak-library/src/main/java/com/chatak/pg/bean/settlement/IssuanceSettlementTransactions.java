package com.chatak.pg.bean.settlement;

import java.math.BigInteger;
import java.sql.Timestamp;

/**
 *
 * Represents a single settlement transaction.
 * Is mapped from SettlementTransactionEntity.
 * 
 * @see IssuanceSettlementTransactionEntity.java
 *
 * @author Girmiti Software
 * @date 01-Jul-2018 4:54:40 PM
 * @version 1.0
 */
public class IssuanceSettlementTransactions {
  
  private String terminalId;

  private String issuerTxnID;

  private String pgTransactionId;

  private Timestamp txnDate;

  private Long isoId;

  private Long isoAmount;
  
  private BigInteger issuanceSettlementEntityId;
  
  private String txnType;
  
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

  public void setIsoId(Long isoId) {
    this.isoId = isoId;
  }

  public void setIsoAmount(Long isoAmount) {
    this.isoAmount = isoAmount;
  }

  public Long getIsoId() {
    return isoId;
  }

  public Long getIsoAmount() {
    return isoAmount;
  }

  public BigInteger getIssuanceSettlementEntityId() {
    return issuanceSettlementEntityId;
  }

  public void setIssuanceSettlementEntityId(BigInteger issuanceSettlementEntityId) {
    this.issuanceSettlementEntityId = issuanceSettlementEntityId;
  }

  public String getTxnType() {
    return txnType;
  }

  public void setTxnType(String txnType) {
    this.txnType = txnType;
  }
  
}
