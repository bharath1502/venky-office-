/*@Author harish.babu */

/**
 * @Author harish.babu
 */
package com.chatak.pg.model;

/**
 * 
 *
 */
public class VirtualTerminalAdjustmentRequest {

  private String merchantId;
  
  private String expDate;

  private String terminalId;

  private Long txnAmount;
  
  private String invoiceNumber;
  
  private Long adjAmount;

  private String cardNum;

  private String authId;

  private String txnRefNum;

  /**
   * @return the txnAmount
   */
  public Long getTxnAmount() {
    return txnAmount;
  }
  
  /**
   * @return the expDate
   */
  public String getExpDate() {
    return expDate;
  }

  /**
   * @param txnAmount
   *          the txnAmount to set
   */
  public void setTxnAmount(Long txnAmount) {
    this.txnAmount = txnAmount;
  }

  /**
   * @return the merchantId
   */
  public String getMerchantId() {
    return merchantId;
  }
  
  /**
   * @param expDate
   *          the expDate to set
   */
  public void setExpDate(String expDate) {
    this.expDate = expDate;
  }
  
  /**
   * @return the terminalId
   */
  public String getTerminalId() {
    return terminalId;
  }

  /**
   * @param merchantId
   *          the merchantId to set
   */
  public void setMerchantId(String merchantId) {
    this.merchantId = merchantId;
  }

  /**
   * @return the invoiceNumber
   */
  public String getInvoiceNumber() {
    return invoiceNumber;
  }
  
  /**
   * @param terminalId
   *          the terminalId to set
   */
  public void setTerminalId(String terminalId) {
    this.terminalId = terminalId;
  }

  /**
   * @param invoiceNumber
   *          the invoiceNumber to set
   */
  public void setInvoiceNumber(String invoiceNumber) {
    this.invoiceNumber = invoiceNumber;
  }

  /**
   * @return the authId
   */
  public String getAuthId() {
    return authId;
  }
  
  /**
   * @return the cardNum
   */
  public String getCardNum() {
    return cardNum;
  }

  /**
   * @param authId
   *          the authId to set
   */
  public void setAuthId(String authId) {
    this.authId = authId;
  }
  
  /**
   * @return the txnRefNum
   */
  public String getTxnRefNum() {
    return txnRefNum;
  }

  /**
   * @param txnRefNum
   *          the txnRefNum to set
   */
  public void setTxnRefNum(String txnRefNum) {
    this.txnRefNum = txnRefNum;
  }
  /**
   * @return the adjAmount
   */
  public Long getAdjAmount() {
    return adjAmount;
  }
  
  /**
   * @param cardNum
   *          the cardNum to set
   */
  public void setCardNum(String cardNum) {
    this.cardNum = cardNum;
  }

  /**
   * @param adjAmount
   *          the adjAmount to set
   */
  public void setAdjAmount(Long adjAmount) {
    this.adjAmount = adjAmount;
  }

}
