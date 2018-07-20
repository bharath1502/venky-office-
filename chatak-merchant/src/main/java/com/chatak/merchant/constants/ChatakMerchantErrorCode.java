/**
 * 
 */
package com.chatak.merchant.constants;

/**
 * << Add Comments Here >>
 * 
 * @author Girmiti Software
 * @date 23-Feb-2015 6:01:59 PM
 * @version 1.0
 */
public enum ChatakMerchantErrorCode {

  TXN_0999("Unable to process your request at this moment. Please try again."),
  TXN_0998("System is in under maintenance, please contact support team."),
  TXN_0997("Invalid request. Please try again."),
  TXN_0996("Invalid access. Please contact support team."),
  TXN_0001("Invalid Transaction Type."),
  TXN_0002("Invalid Order Id."),
  TXN_0003("Invalid amount."),
  TXN_0004("Invalid Card."),
  TXN_0005("Invalid address."),
  TXN_0006("Invalid callback url."),
  TXN_0007("Invalid merchant."),
  TXN_0008("Invalid Transaction ID."),
  TXN_0009("Invalid card expiry."),
  TXN_0010("Invalid card type."),
  
  TXN_0100("Approved"),
  TXN_0101("Ecommerce purchase is not processed. Please try again."),
  TXN_0102("Ecommerce void transaction is not processed. Please try again.");

  private final String value;

  ChatakMerchantErrorCode(String v) {
    value = v;
  }

  public String value() {
    return value;
  }

  public static ChatakMerchantErrorCode fromValue(String v) {
    for(ChatakMerchantErrorCode c : ChatakMerchantErrorCode.values()) {
      if(c.value.equals(v)) {
        return c;
      }
    }
    throw new IllegalArgumentException(v);
  }

}
