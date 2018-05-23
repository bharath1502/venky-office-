/**
 * 
 */
package com.chatak.pg.acq.dao.model;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @Author: Girmiti Software
 * @Date: Aug 14, 2015
 * @Time: 8:59:52 PM
 * @Version: 1.0
 * @Comments:
 */
@Entity
@Table(name = "PG_TRANSFERS")
public class PGTransfers implements Serializable {

  private static final long serialVersionUID = 7652423010176549128L;

  @Id
  /*@SequenceGenerator(name = "SEQ_PG_TRANSFERS_ID", sequenceName = "SEQ_PG_TRANSFERS")
  @GeneratedValue(generator = "SEQ_PG_TRANSFERS_ID")*/
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "PG_TRANSFERS_ID")
  private Long pgTransfersId;

  @Column(name = "AMOUNT")
  private Long amount;

  @Column(name = "MERCHANT_ID")
  private Long merchantId;

  @Column(name = "TO_ACCOUNT")
  private String toAccount;

  @Column(name = "FROM_ACCOUNT")
  private String fromAccount;

  @Column(name = "NAME_ON_ACCOUNT")
  private String nameOnAccount;

  @Column(name = "CITY")
  private String city;

  @Column(name = "STATE")
  private String state;

  @Column(name = "ACCOUNT_TYPE")
  private String accountType;

  @Column(name = "BANK_ROUTING_NUMBER")
  private String bankRoutingNumber;

  @Column(name = "TRANSFER_MODE")
  private String transferMode;

  @Column(name = "CREATED_DATE")
  private Timestamp createdDate;

  @Column(name = "UPDATED_DATE")
  private Timestamp updatedDate;

  @Column(name = "STATUS")
  private String status;

  @Column(name = "TXN_DESCRIPTION")
  private String txnDescription;

  public Long getPgTransfersId() {
    return pgTransfersId;
  }

  public void setPgTransfersId(Long pgTransfersId) {
    this.pgTransfersId = pgTransfersId;
  }

  public Long getAmount() {
    return amount;
  }

  public void setAmount(Long amount) {
    this.amount = amount;
  }

  public Long getMerchantId() {
    return merchantId;
  }

  public void setMerchantId(Long merchantId) {
    this.merchantId = merchantId;
  }

  public String getToAccount() {
    return toAccount;
  }

  public void setToAccount(String toAccount) {
    this.toAccount = toAccount;
  }

  public String getFromAccount() {
    return fromAccount;
  }

  public void setFromAccount(String fromAccount) {
    this.fromAccount = fromAccount;
  }

  public String getNameOnAccount() {
    return nameOnAccount;
  }

  public void setNameOnAccount(String nameOnAccount) {
    this.nameOnAccount = nameOnAccount;
  }

  public String getCity() {
    return city;
  }

  public void setCity(String city) {
    this.city = city;
  }

  public String getState() {
    return state;
  }

  public void setState(String state) {
    this.state = state;
  }

  public String getAccountType() {
    return accountType;
  }

  public void setAccountType(String accountType) {
    this.accountType = accountType;
  }

  public String getBankRoutingNumber() {
    return bankRoutingNumber;
  }

  public void setBankRoutingNumber(String bankRoutingNumber) {
    this.bankRoutingNumber = bankRoutingNumber;
  }

  public String getTransferMode() {
    return transferMode;
  }

  public void setTransferMode(String transferMode) {
    this.transferMode = transferMode;
  }

  public Timestamp getCreatedDate() {
    return createdDate;
  }

  public void setCreatedDate(Timestamp createdDate) {
    this.createdDate = createdDate;
  }

  public Timestamp getUpdatedDate() {
    return updatedDate;
  }

  public void setUpdatedDate(Timestamp updatedDate) {
    this.updatedDate = updatedDate;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public String getTxnDescription() {
    return txnDescription;
  }

  public void setTxnDescription(String txnDescription) {
    this.txnDescription = txnDescription;
  }
  

}
