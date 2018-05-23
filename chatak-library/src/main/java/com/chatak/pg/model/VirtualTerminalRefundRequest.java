/*@Author harish.babu */

/**
 * @Author harish.babu
 */
package com.chatak.pg.model;

/**
 * 
 *
 */
public class VirtualTerminalRefundRequest {
  
  private String expDate;
  
  private String cardNum;

  private String merchantId;
  
  private Long txnAmount;

  private String terminalId;

  private String txnRefNum;

  private String invoiceNumber;

  /**
   * @return the txnRefNum
   */
  public String getTxnRefNum() {
    return txnRefNum;
  }

  /**
   * @return the cardNum
   */
  public String getCardNum() {
    return cardNum;
  }
  
  /**
   * @param txnRefNum
   *          the txnRefNum to set
   */
  public void setTxnRefNum(String txnRefNum) {
    this.txnRefNum = txnRefNum;
  }

  /**
   * @return the expDate
   */
  public String getExpDate() {
    return expDate;
  }
  
  /**
   * @param cardNum
   *          the cardNum to set
   */
  public void setCardNum(String cardNum) {
    this.cardNum = cardNum;
  }

  /**
   * @param expDate
   *          the expDate to set
   */
  public void setExpDate(String expDate) {
    this.expDate = expDate;
  }

  /**
   * @param txnAmount
   *          the txnAmount to set
   */
  public void setTxnAmount(Long txnAmount) {
    this.txnAmount = txnAmount;
  }

  /**
   * @param merchantId
   *          the merchantId to set
   */
  public void setMerchantId(String merchantId) {
    this.merchantId = merchantId;
  }

  /**
   * @return the terminalId
   */
  public String getTerminalId() {
    return terminalId;
  }
  
  /**
   * @return the txnAmount
   */
  public Long getTxnAmount() {
    return txnAmount;
  }

  /**
   * @param terminalId
   *          the terminalId to set
   */
  public void setTerminalId(String terminalId) {
    this.terminalId = terminalId;
  }

  /**
   * @return the invoiceNumber
   */
  public String getInvoiceNumber() {
    return invoiceNumber;
  }
  
  /**
   * @return the merchantId
   */
  public String getMerchantId() {
    return merchantId;
  }

  /**
   * @param invoiceNumber
   *          the invoiceNumber to set
   */
  public void setInvoiceNumber(String invoiceNumber) {
    this.invoiceNumber = invoiceNumber;
  }

}
