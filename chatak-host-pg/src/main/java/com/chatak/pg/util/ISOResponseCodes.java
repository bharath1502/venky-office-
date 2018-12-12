/**
 * 
 */
package com.chatak.pg.util;

/**
 * << Add Comments Here >>
 *
 * @author Girmiti Software
 * @date 19-Dec-2014 5:24:21 pm
 * @version 1.0
 */
public enum ISOResponseCodes {
  VALID_DATA("10000", "Valid"),
  INACTIVE_CARD("", ""),
  INACTIVE_ACCOUNT("", ""),
  INACTIVE_CUSTOMER("", ""),
  APPROVED("00", "Approved"),
  CALL_ISSUER_("01", "Call Issuer"),
  INVALID_MERCHANT("03", "Invalid Merchant"),
  DECLINED_PICKUP("04", "Declined pick up card"),
  DO_NOT_HONOUR("05", "Do not honour trans declined"),
  ERROR("06", "Invalid Format"),
  PICKUP_CARD_SPECIAL("07", "Pick-up card, special conditions"),
  APPROVED_PARTIAL_AMOUNT("10", "Approved for partial amount"),
  INVALID_TRANSACTION("12", "Invalid transaction"),
  INVALID_AMOUNT("13", "Invalid amount"),
  INVALID_CARD("14", "Invalid card number"),
  NO_ISSUER("15", "No such issuer"),
  INVALID_RESPONSE("20", "Invalid response"),
  UNACCEPTABLE_TXN_FEE("23", "Unacceptable transaction fee"),
  FILE_UPDATE_302("24", "File update not supported by receiver"), // (0302
                                                                  // message
                                                                  // only)
  LOCATE_RECORD_302("25", "Unable to locate record on file"), // (0302 message
                                                              // only)
  DUPLICATE_FILE_302("26", "Duplicate file update record, no action"), // (0302
                                                                       // message
                                                                       // only)
  FILE_EDIT_302("27", "File update field edit error"), // (0302 message only)
  RECORED_LOCK_302("28", "File update record locked out"), // (0302 message
                                                           // only)
  FILE_UPDATE_NS_302("29", "File update not successful, contact acquirer"), // (0302
                                                                            // message
                                                                            // only)
  REVERSAL_FORMAT_ERROR("30", "Format error"), // (may also be a reversal)
  BANK_NOT_SUPPORTED("31", "Bank not supported by switch"),
  PICKUP_EXPIRED_CARD("33", "Expired card, pick-up"),
  PICKUP_SUSPECTED_FRAUD("34", "Suspected fraud, pick-up"),
  SP_PICKUP_CONTACT_ACQUIRER("35", "Card acceptor contact acquirer, pick-up"),
  PICKUP_RESTRICTED_CARD("36", "Restricted card, pick-up"),
  PICKUP_CONTACT_ACQUIRER("37", "Card acceptor call acquirer security, pick-up"),
  INSUFFICIENT_FUNDS("51", "Insufficient funds"),
  NO_CHECKING_ACCOUNT("52", "No checking account"),
  NO_SAVING_ACCOUNT("53", "No savings account"),
  EXPIRED_CARD("54", "Expired card"),
  INCORRECT_PIN("55", "Incorrect PIN"),
  NO_CARD_RECORD("56", "No card record"),
  CARDHOLDER_TRANSACTION_NOT_PERMITTED("57", "Transaction not permitted to cardholder"),
  TERMINAL_TRANSACTION_NOT_PERMITTED("58", "Transaction not permitted to terminal"),
  SUSPECTED_FRAUD("59", "Suspected fraud"),
  CONTACT_AQUIRER("60", "Card acceptor contact acquirer"),
  EXCEEDS_WITHDRAW_AMOUNT_LIMIT("61", "Exceeds withdrawal amount limit"),
  RESTRICTED_CARD("62", "Restricted card"),
  SECURITY_VIOLATION("63", "Security violation"),
  EXCEEDS_WITHDRAW_FREQUENCY_LIMIT("65", "Exceeds withdrawal frequency limit"),
  CALL_AQUIRER_SECURITY("66", "Card acceptor call acquirer security"),
  PICKUP_HARD_CAPTURE("67", "Hard capture, pick-up"),
  PIN_TRIES_EXCEEDED("75", "Allowable number of PIN tries exceeded"),
  CUSTOMER_NOT_ELIGIBLE_POS("78", "Customer not eligible for POS"),
  INVALID_DIGITAL_SIGN("79", "Invalid Digital Signature"),
  STALE_TRANSACTION("80", "Stale dated transaction"),
  ISSUER_STAND_IN("81", "Issuer requested stand-in"),
  CVV_FAILED("89", "Card verification value (CVV) verification failed (no pick-up)"),
  CUTOFF_INPROGRESS("90", "Cutoff in progress"),
  ISSUER_SWITCH_INOPERATIVE("91", "Issuer or switch is inoperative"),
  FIN_INSTITUTION_UNKNOWN("92", "Financial institution or intermediate network unknown for routing"),
  DUPLICATE_TRANSACTION("94", "Duplication transaction"),
  RECONCIL_ERROR("95", "Total Mismatch Reconcile error"), // Only for
                                                          // Settlements
  SYSTEM_MALFUNCTION("96", "System Malfunction"), // If Mandatory fields are
                                                  // absent OR the Packet
                                                  // contains invalid characters
  BAD_CVV2("SR", "BAD CVV2");

  private String statusCode;

  private String statusMessage;

  private ISOResponseCodes(String statusCode, String statusMessage) {
    this.statusCode = statusCode;
    this.statusMessage = statusMessage;
  }

  /**
   * @return the statusCode
   */
  public String getStatusCode() {
    return statusCode;
  }

  /**
   * @return the statusMessage
   */
  public String getStatusMessage() {
    return statusMessage;
  }

  @Override
  public String toString() {
    return "Status code:" + statusCode + "-- Status message:" + statusMessage;
  }
}
