package com.chatak.pg.model;

/**
 * << Add Comments Here >>
 * 
 * @author Girmiti Software
 * @date 18-Mar-2015 4:04:47 PM
 * @version 1.0
 */
public class VirtualTerminalPreAuthRequest {

  private String merchantId;
  
  private String invoiceNumber;

  private String terminalId;

  private Long txnAmount;
  
  private String expDate;

  private String cardNum;

  
  /**
   * @return the expDate
   */
  public String getExpDate() {
    return expDate;
  }
  
  /**
   * @return the cardNum
   */
  public String getCardNum() {
    return cardNum;
  }

  /**
   * @param expDate
   *          the expDate to set
   */
  public void setExpDate(String expDate) {
    this.expDate = expDate;
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
   * @return the txnAmount
   */
  public Long getTxnAmount() {
    return txnAmount;
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
   * @param txnAmount
   *          the txnAmount to set
   */
  public void setTxnAmount(Long txnAmount) {
    this.txnAmount = txnAmount;
  }

}
