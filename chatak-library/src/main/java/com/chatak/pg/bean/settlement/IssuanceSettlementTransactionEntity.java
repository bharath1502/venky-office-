package com.chatak.pg.bean.settlement;

import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * Class represents a single aggregated transaction row.
 * This will be mapped to multiple SettlementTransactions.
 * 
 * @see IssuanceSettlementTransactions.java
 *
 * @author Girmiti Software
 * @date 01-Jul-2018 4:53:01 PM
 * @version 1.0
 */
public class IssuanceSettlementTransactionEntity {
  
  private String merchantId;

  private BigInteger acqSaleAmount = new BigInteger("0");

  private BigInteger issSaleAmount = new BigInteger("0");

  private Long acqPmId;

  private Long issPmId;

  private String batchid;

  private Timestamp batchFileDate;

  private Timestamp batchFileProcessedDate;

  private String status;

  private Long pmAmount;

  private Long merchantAmount;
  
  private List<IssuanceSettlementTransactions> settlementTransactionsList = new ArrayList<IssuanceSettlementTransactions>();
  
  private String issuerTxnID;

  private String pgTransactionId;
  
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

  public Long getMerchantAmount() {
    return merchantAmount;
  }

  public void setPmAmount(Long pmAmount) {
    this.pmAmount = pmAmount;
  }

  public void setMerchantAmount(Long merchantAmount) {
    this.merchantAmount = merchantAmount;
  }

  public List<IssuanceSettlementTransactions> getSettlementTransactionsList() {
    return settlementTransactionsList;
  }

  public void setSettlementTransactionsList(List<IssuanceSettlementTransactions> settlementTransactionsList) {
    this.settlementTransactionsList = settlementTransactionsList;
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
}
