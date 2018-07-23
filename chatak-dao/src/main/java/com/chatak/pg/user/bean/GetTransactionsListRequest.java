package com.chatak.pg.user.bean;

import java.io.Serializable;
import java.util.List;

public class GetTransactionsListRequest implements Serializable

{

  private static final long serialVersionUID = -4018965738226434782L;

  private String merchant_code;

  private String transaction_type;

  private String from_date;

  private String to_date;

  private Integer status;

  private String transactionId;

  private String processCode;

  private String cardNumber;

  private String fromAmtRange;

  private String toAmtRange;

  private Integer pageIndex;

  private Integer pageSize;

  private Integer noOfRecords;

  private String cardHolderName;

  private String settlementStatus;

  private String entryMode;

  private String acqChannel;

  private String merchantName;

  private boolean isSubMerchantsTxnsRequired;

  private boolean toGetCurrentDayTxns;
  
  private List<String> transactionCodeList;
  
  private String accountNumber;
  
  private String statusString;
  
  private String merchantSettlementStatus;
  
  private String merchantBusinessName;

  private String batchID;
  
  /**
   * @return the noOfRecords
   */
  public Integer getNoOfRecords() {
    return noOfRecords;
  }

  /**
   * @return the pageIndex
   */
  public Integer getPageIndex() {
    return pageIndex;
  }

  public void setMerchantBusinessName(String merchantBusinessName) {
	this.merchantBusinessName = merchantBusinessName;
  }

  /**
   * @param pageSize
   *          the pageSize to set
   */
  public void setPageSize(Integer pageSize) {
    this.pageSize = pageSize;
  }

  public String getMerchantBusinessName() {
	return merchantBusinessName;
  }

  /**
   * @param noOfRecords
   *          the noOfRecords to set
   */
  public void setNoOfRecords(Integer noOfRecords) {
    this.noOfRecords = noOfRecords;
  }

  /**
   * @return the cardNumber
   */
  public String getCardNumber() {
    return cardNumber;
  }
  
  /**
   * @param pageIndex
   *          the pageIndex to set
   */
  public void setPageIndex(Integer pageIndex) {
    this.pageIndex = pageIndex;
  }

  /**
   * @return the pageSize
   */
  public Integer getPageSize() {
    return pageSize;
  }

  /**
   * @param cardNumber
   *          the cardNumber to set
   */
  public void setCardNumber(String cardNumber) {
    this.cardNumber = cardNumber;
  }

  /**
   * @return the merchant_code
   */
  public String getMerchant_code() {
    return merchant_code;
  }

  /**
   * @param merchant_code
   *          the merchant_code to set
   */
  public void setMerchant_code(String merchant_code) {
    this.merchant_code = merchant_code;
  }

  /**
   * @return the transaction_type
   */
  public String getTransaction_type() {
    return transaction_type;
  }

  /**
   * @param transaction_type
   *          the transaction_type to set
   */
  public void setTransaction_type(String transaction_type) {
    this.transaction_type = transaction_type;
  }

  /**
   * @return the from_date
   */
  public String getFrom_date() {
    return from_date;
  }

  /**
   * @param from_date
   *          the from_date to set
   */
  public void setFrom_date(String from_date) {
    this.from_date = from_date;
  }

  /**
   * @return the to_date
   */
  public String getTo_date() {
    return to_date;
  }

  /**
   * @param to_date
   *          the to_date to set
   */
  public void setTo_date(String to_date) {
    this.to_date = to_date;
  }

  /**
   * @return the status
   */
  public Integer getStatus() {
    return status;
  }

  /**
   * @param status
   *          the status to set
   */
  public void setStatus(Integer status) {
    this.status = status;
  }

  /**
   * @return the transactionId
   */
  public String getTransactionId() {
    return transactionId;
  }

  /**
   * @param transactionId
   *          the transactionId to set
   */
  public void setTransactionId(String transactionId) {
    this.transactionId = transactionId;
  }

  /**
   * @return the procCode
   */
  public String getProcessCode() {
    return processCode;
  }

  /**
   * @param procCode
   *          the procCode to set
   */
  public void setProcessCode(String processCode) {
    this.processCode = processCode;
  }

  /**
   * @return the fromAmtRange
   */
  public String getFromAmtRange() {
    return fromAmtRange;
  }

  /**
   * @param fromAmtRange
   *          the fromAmtRange to set
   */
  public void setFromAmtRange(String fromAmtRange) {
    this.fromAmtRange = fromAmtRange;
  }

  /**
   * @return the toAmtRange
   */
  public String getToAmtRange() {
    return toAmtRange;
  }

  /**
   * @param toAmtRange
   *          the toAmtRange to set
   */
  public void setToAmtRange(String toAmtRange) {
    this.toAmtRange = toAmtRange;
  }

  /**
   * @return the cardHolderName
   */
  public String getCardHolderName() {
    return cardHolderName;
  }

  /**
   * @param cardHolderName
   *          the cardHolderName to set
   */
  public void setCardHolderName(String cardHolderName) {
    this.cardHolderName = cardHolderName;
  }

  /**
   * @return the settlementStatus
   */
  public String getSettlementStatus() {
    return settlementStatus;
  }

  /**
   * @param settlementStatus
   *          the settlementStatus to set
   */
  public void setSettlementStatus(String settlementStatus) {
    this.settlementStatus = settlementStatus;
  }

  /**
   * @return the entryMode
   */
  public String getEntryMode() {
    return entryMode;
  }

  /**
   * @param entryMode
   *          the entryMode to set
   */
  public void setEntryMode(String entryMode) {
    this.entryMode = entryMode;
  }

  /**
   * @return the acqChannel
   */
  public String getAcqChannel() {
    return acqChannel;
  }

  /**
   * @param acqChannel
   *          the acqChannel to set
   */
  public void setAcqChannel(String acqChannel) {
    this.acqChannel = acqChannel;
  }

  public String getMerchantName() {
    return merchantName;
  }

  public void setMerchantName(String merchantName) {
    this.merchantName = merchantName;
  }

  /**
   * @return the isSubMerchantsTxnsRequired
   */
  public boolean isSubMerchantsTxnsRequired() {
    return isSubMerchantsTxnsRequired;
  }

  /**
   * @param isSubMerchantsTxnsRequired
   *          the isSubMerchantsTxnsRequired to set
   */
  public void setSubMerchantsTxnsRequired(boolean isSubMerchantsTxnsRequired) {
    this.isSubMerchantsTxnsRequired = isSubMerchantsTxnsRequired;
  }

  /**
   * @return the toGetCurrentDayTxns
   */
  public boolean isToGetCurrentDayTxns() {
    return toGetCurrentDayTxns;
  }

  /**
   * @param toGetCurrentDayTxns
   *          the toGetCurrentDayTxns to set
   */
  public void setToGetCurrentDayTxns(boolean toGetCurrentDayTxns) {
    this.toGetCurrentDayTxns = toGetCurrentDayTxns;
  }

  public List<String> getTransactionCodeList() {
    return transactionCodeList;
  }

  public void setTransactionCodeList(List<String> transactionCodeList) {
    this.transactionCodeList = transactionCodeList;
  }

/**
 * @return the accountNumber
 */
public String getAccountNumber() {
	return accountNumber;
}

/**
 * @param accountNumber the accountNumber to set
 */
public void setAccountNumber(String accountNumber) {
	this.accountNumber = accountNumber;
}

/**
 * @return the statusString
 */
public String getStatusString() {
	return statusString;
}

/**
 * @param statusString the statusString to set
 */
public void setStatusString(String statusString) {
	this.statusString = statusString;
}

public String getMerchantSettlementStatus() {
	return merchantSettlementStatus;
}

public void setMerchantSettlementStatus(String merchantSettlementStatus) {
	this.merchantSettlementStatus = merchantSettlementStatus;
}

public String getBatchID() {
  return batchID;
}

public void setBatchID(String batchID) {
  this.batchID = batchID;
}
  

}
