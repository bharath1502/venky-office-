/*@Author harish.babu */

/**
 * @Author harish.babu
 */
package com.chatak.merchant.model;

/**
 * 
 *
 */
public class VirtualTerminalRefundRequest {
  
  private String cardNum;

  private String expDate;

  private String merchantId;

  private String terminalId;

  private Long txnAmount;

  private String invoiceNumber;

  private String txnRefNum;

  /**
   * @return the cardNum
   */
  public String getCardNum() {
    return cardNum;
  }

  /**
   * @param cardNum
   *          the cardNum to set
   */
  public void setCardNum(String cardNum) {
    this.cardNum = cardNum;
  }
  
  /**
   * @return the txnRefNum
   */
  public String getTxnRefNum() {
    return txnRefNum;
  }

  /**
   * @return the expDate
   */
  public String getExpDate() {
    return expDate;
  }

  /**
   * @param expDate
   *          the expDate to set
   */
  public void setExpDate(String expDate) {
    this.expDate = expDate;
  }
  
  /**
   * @param txnRefNum
   *          the txnRefNum to set
   */
  public void setTxnRefNum(String txnRefNum) {
    this.txnRefNum = txnRefNum;
  }

  /**
   * @return the merchantId
   */
  public String getMerchantId() {
    return merchantId;
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
   * @param txnAmount
   *          the txnAmount to set
   */
  public void setTxnAmount(Long txnAmount) {
    this.txnAmount = txnAmount;
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
   * @param invoiceNumber
   *          the invoiceNumber to set
   */
  public void setInvoiceNumber(String invoiceNumber) {
    this.invoiceNumber = invoiceNumber;
  }

}
