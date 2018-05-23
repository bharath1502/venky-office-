package com.chatak.pg.model;

/**
 *
 * << Add Comments Here >>
 *
 * @author Girmiti Software
 * @date 17-Mar-2015 9:19:26 AM
 * @version 1.0
 */
public class GetMerchantDetailsResponse extends Response {



/**
   * 
   */
  private static final long serialVersionUID = 1L;

private String merchantId;
 
 private String terminalId;

/**
 * @return the merchantId
 */
public String getMerchantId() {
  return merchantId;
}

/**
 * @param merchantId the merchantId to set
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
 * @param terminalId the terminalId to set
 */
public void setTerminalId(String terminalId) {
  this.terminalId = terminalId;
}

}
