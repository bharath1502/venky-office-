/**
 * 
 */
package com.chatak.pay.constants;

/**
 * << Add Comments Here >>
 * 
 * @author Girmiti Software
 * @date 23-Feb-2015 6:01:59 PM
 * @version 1.0
 */
public enum ChatakPayErrorCode {

  TXN_0999("Unable to process your request at this moment. Please try again."),
  TXN_0998("System is in under maintenance, please contact support team."),
  TXN_0997("Invalid request. Please try again."),
  TXN_0996("Invalid access. Please contact support team."),
  TXN_0995("Payment is cancelled by user"),
  TXN_0994("Problem in connecting to host processor, please contact support team."),
  TXN_0993("Invalid request. Please try with valid request data."),
  TXN_0992("There seems to be a wrong request data. Please try again with valid request."),
  TXN_0001("Invalid Transaction Type."),
  TXN_0002("Invalid Order Id."),
  TXN_0003("Invalid amount."),
  TXN_0004("Invalid Card."),
  TXN_0005("Invalid address."),
  TXN_0006("Invalid callback url."),
  TXN_0007("Invalid merchant."),
  TXN_0008("Invalid Transaction ID or Transaction already cancelled or refunded"),
  TXN_0009("Invalid card expiry."),
  TXN_0010("Invalid card type."),
  TXN_0011("Invalid session."),
  TXN_0012("Duplicate Transaction. Seems same Order Id is being used for another transaction. Please try with different Order Id."),
  TXN_0013("Invalid transaction amount"),
  TXN_0014("Invalid merchant name"),
  TXN_0015("Invalid card security code."),
  TXN_0016("Invalid billing data."),
  TXN_0017("Invalid billing address1."),
  TXN_0018("Invalid billing city."),
  TXN_0019("Invalid billing country."),
  TXN_0020("Invalid billing state."),
  TXN_0021("Invalid billing zip."),
  TXN_0022("Invalid billing email."),
  TXN_0023("Invalid Transaction reference number."),
  TXN_0024("Invalid track2 data"),
  TXN_0025("Duplicate Transaction. Seems same Invoice number is being used for another transaction. Please try with different Invoice number."),
  TXN_0026("Invalid Transaction Token."),
  TXN_0099("Transaction blocked,duplicate Transaction with same card, please try after some time"),
  
  TXN_0100("Approved"),
  TXN_0101("Ecommerce purchase is not processed. Please try again."),
  TXN_0102("Ecommerce void transaction is not processed. Please try again."),
  TXN_0103("Invalid Transaction Id or transaction already refunded "),
  TXN_0104("Invalid split transaction data"),
  TXN_0105("Invalid split transaction amount"),
  TXN_0106("Invalid split reference details"),
  TXN_0107("Invalid ShareMode Type."),
  TXN_0108("Unable to process split payment with given data, please try again"),
  TXN_0109("Invalid split transaction reference number"),
  TXN_0110("Invalid Key serial number"),
  TXN_0111("Invalid Request With Both Card And Token Details"),
  TXN_0112("Invalid Card Token Details"),
  TXN_0113("Transaction is in processing state"),
  TXN_0114("Invalid merchantId/terminalId"),
  TXN_0115("Card not accepted"),
  TXN_0116("Fee program is not created"),
  TXN_0117("Fee amount should not be more than transaction amount"),
  TXN_0403("Access Denied"),
  TXN_0027("Invalid transaction"),
	
  GEN_001("Success"),
  GEN_002("Error"),
  GEN_003("New User");

  private final String value;

  ChatakPayErrorCode(String v) {
    value = v;
  }

  public String value() {
    return value;
  }

  public static ChatakPayErrorCode fromValue(String v) {
    for(ChatakPayErrorCode c : ChatakPayErrorCode.values()) {
      if(c.value.equals(v)) {
        return c;
      }
    }
    throw new IllegalArgumentException(v);
  }

}
