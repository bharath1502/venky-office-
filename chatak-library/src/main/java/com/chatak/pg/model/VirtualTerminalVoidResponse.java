
package com.chatak.pg.model;

/**
 *
 * << Add Comments Here >>
 *
 * @author Girmiti Software
 * @date 18-Mar-2015 3:08:45 PM
 * @version 1.0
 */
public class VirtualTerminalVoidResponse extends Response {
	
  private static final long serialVersionUID = 4582276918849233461L;
	private String txnRefNum;
	private String authId;
	private String txnAmount;
	

	/**
	 * @return the txnRefNum
	 */
	public String getTxnRefNum() {
		return txnRefNum;
	}
	
	/**
   * @return the authId
   */
  public String getAuthId() {
    return authId;
  }
  
  /**
   * @param txnRefNum
   *            the txnRefNum to set
   */
  public void setTxnRefNum(String txnRefNum) {
    this.txnRefNum = txnRefNum;
  }
  
  /**
   * @param txnAmount
   *            the txnAmount to set
   */
  public void setTxnAmount(String txnAmount) {
    this.txnAmount = txnAmount;
  }

  /**
   * @param authId
   *            the authId to set
   */
  public void setAuthId(String authId) {
    this.authId = authId;
  }

	/**
	 * @return the txnAmount
	 */
	public String getTxnAmount() {
		return txnAmount;
	}

}
