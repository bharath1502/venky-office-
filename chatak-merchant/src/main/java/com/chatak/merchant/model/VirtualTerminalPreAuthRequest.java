package com.chatak.merchant.model;

/**
 * << Add Comments Here >>
 * 
 * @author Girmiti Software
 * @date 18-Mar-2015 4:04:47 PM
 * @version 1.0
 */
public class VirtualTerminalPreAuthRequest {

  private String expDate;

  private String merchantId;

  private String terminalId;

  private String invoiceNumber;

  private Long txnAmount;

  private String cardNum;

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
   * @return the terminalId
   */
  public String getTerminalId() {
    return terminalId;
  }

  /**
   * @param txnAmount
   *          the txnAmount to set
   */
  public void setTxnAmount(Long txnAmount) {
    this.txnAmount = txnAmount;
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
   * @return the txnAmount
   */
  public Long getTxnAmount() {
    return txnAmount;
  }

  /**
   * @param invoiceNumber
   *          the invoiceNumber to set
   */
  public void setInvoiceNumber(String invoiceNumber) {
    this.invoiceNumber = invoiceNumber;
  }

}
