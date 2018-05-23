package com.chatak.pg.model;

import java.io.Serializable;

public class virtualAccFeePostResponse implements Serializable{

  /**
   * 
   */
  private static final long serialVersionUID = 4105058459357799274L;
  
  private String errorCode;
  private String errorMessage;
  private String ciVirtualAccTxnId;
  public String getErrorCode() {
    return errorCode;
  }
  public void setErrorCode(String errorCode) {
    this.errorCode = errorCode;
  }
  public String getErrorMessage() {
    return errorMessage;
  }
  public void setErrorMessage(String errorMessage) {
    this.errorMessage = errorMessage;
  }
  public String getCiVirtualAccTxnId() {
    return ciVirtualAccTxnId;
  }
  public void setCiVirtualAccTxnId(String ciVirtualAccTxnId) {
    this.ciVirtualAccTxnId = ciVirtualAccTxnId;
  }
}
