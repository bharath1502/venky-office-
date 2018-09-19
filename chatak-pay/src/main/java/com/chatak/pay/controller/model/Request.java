/**
 * 
 */
package com.chatak.pay.controller.model;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import com.chatak.pg.enums.EntryModeEnum;
import com.chatak.pg.enums.ShareModeEnum;
import com.chatak.pg.enums.TransactionType;

/**
 * Model class to hold basic details for every request
 * 
 * @author Girmiti Software
 * @date 02-Jul-2014 10:49:42 AM
 * @version 1.0
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Request implements Serializable {

  /**
   * 
   */
  private static final long serialVersionUID = 3463794052777695279L;

  private String createdBy;

  private String originChannel;

  private String merchantCode;

  private String terminalId;

  private TransactionType transactionType;

  private EntryModeEnum entryMode;

  private ShareModeEnum shareMode;

  private String posEntryMode;
  
  private String mode;
  
  private String processorMid;

  /**
   * @return the createdBy
   */
  public String getCreatedBy() {
    return createdBy;
  }

  /**
   * @param createdBy
   *          the createdBy to set
   */
  public void setCreatedBy(String createdBy) {
    this.createdBy = createdBy;
  }

  /**
   * @return the orginChannel
   */
  public String getOriginChannel() {
    return originChannel;
  }

  /**
   * @param orginChannel
   *          the orginChannel to set
   */
  public void setOriginChannel(String orginChannel) {
    this.originChannel = orginChannel;
  }

  /**
   * @return the merchantCode
   */
  public String getMerchantCode() {
    return merchantCode;
  }

  /**
   * @param merchantCode
   *          the merchantCode to set
   */
  public void setMerchantCode(String merchantCode) {
    this.merchantCode = merchantCode;
  }

  /**
   * @return the terminalId
   */
  public String getTerminalId() {
    return terminalId;
  }

  /**
   * @param terminalId
   *          the terminalId to set
   */
  public void setTerminalId(String terminalId) {
    this.terminalId = terminalId;
  }

  /**
   * @return the transactionType
   */
  public TransactionType getTransactionType() {
    return transactionType;
  }

  /**
   * @param transactionType
   *          the transactionType to set
   */
  public void setTransactionType(TransactionType transactionType) {
    this.transactionType = transactionType;
  }

  /**
   * @return the entryMode
   */
  public EntryModeEnum getEntryMode() {
    return entryMode;
  }

  /**
   * @param entryMode
   *          the entryMode to set
   */
  public void setEntryMode(EntryModeEnum entryMode) {
    this.entryMode = entryMode;
  }

  /**
   * @return the shareMode
   */
  public ShareModeEnum getShareMode() {
    return shareMode;
  }

  /**
   * @param shareMode
   *          the shareMode to set
   */
  public void setShareMode(ShareModeEnum shareMode) {
    this.shareMode = shareMode;
  }

  /**
   * @return the posEntryMode
   */
  public String getPosEntryMode() {
    return posEntryMode;
  }

  /**
   * @param posEntryMode the posEntryMode to set
   */
  public void setPosEntryMode(String posEntryMode) {
    this.posEntryMode = posEntryMode;
  }

  /**
   * @return the mode
   */
  public String getMode() {
    return mode;
  }

  /**
   * @param mode the mode to set
   */
  public void setMode(String mode) {
    this.mode = mode;
  }

  /**
   * @return the processorMid
   */
  public String getProcessorMid() {
    return processorMid;
  }

  /**
   * @param processorMid the processorMid to set
   */
  public void setProcessorMid(String processorMid) {
    this.processorMid = processorMid;
  }
  

}
