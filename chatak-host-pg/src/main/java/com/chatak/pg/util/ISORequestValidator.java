package com.chatak.pg.util;

import java.util.HashMap;

import org.apache.log4j.Logger;
import org.jpos.iso.ISOMsg;

import com.chatak.pg.exception.ValidationException;

/**
 * Validator class to validate incoming ISO Request packet - check for mandatory
 * data element fields
 *
 * @author Girmiti Software
 * @date 19-Dec-2014 5:29:32 pm
 * @version 1.0
 */
public class ISORequestValidator {
	
	private ISORequestValidator() {
		
	}

  private static Logger log = Logger.getLogger(ISORequestValidator.class);

  private static final int[] BASIC_FIELDS = { 0, 3, 41, 42 };

  /**
   * auth(100) 
   * Field 52 pin block
   * Transaction Fields to check Manual entry transaction - 0, 2, 3, 4, 11, 14, 22, 41, 42, 62 
   * Fields to check Swipe transaction - 0, 3, 4, 11, 22, 35, 41, 42, 62 
   * Fields to ICC transaction - 0, 3, 4, 11, 22, 41, 42, 52(c), 55, 62 
   * 
   */
  private static final int[] AUTH_MANUAL_PIN_FIELDS = { 2, 4, 11, 14, 22, 52, 62 };

  private static final int[] AUTH_MANUAL_PINLESS_FIELDS = { 2, 4, 11, 14, 22, 62 };

  private static final int[] AUTH_SWIPE_PIN_FIELDS = { 4, 11, 22, 35, 52, 62 };

  private static final int[] AUTH_SWIPE_PINLESS_FIELDS = { 4, 11, 22, 35, 62 };

  private static final int[] AUTH_ICC_PIN_FIELDS = { 4, 11, 22, 35, 52, 55, 62 };

  private static final int[] AUTH_ICC_PINLESS_FIELDS = { 4, 11, 22, 35, 55, 62 };

  private static final HashMap<String, int[]> ISO_MANDATE_FIELDS_MAP = new HashMap<String, int[]>();

  static {
    ISO_MANDATE_FIELDS_MAP.put(POSEntryMode.MANUAL_WITH_PIN, AUTH_MANUAL_PIN_FIELDS);
    ISO_MANDATE_FIELDS_MAP.put(POSEntryMode.MANUAL_WITH_NO_PIN, AUTH_MANUAL_PINLESS_FIELDS);
    ISO_MANDATE_FIELDS_MAP.put(POSEntryMode.SWIPE_WITH_PIN, AUTH_SWIPE_PIN_FIELDS);
    ISO_MANDATE_FIELDS_MAP.put(POSEntryMode.SWIPE_WITH_NO_PIN, AUTH_SWIPE_PINLESS_FIELDS);
    ISO_MANDATE_FIELDS_MAP.put(POSEntryMode.ICC_READ_WITH_PIN, AUTH_ICC_PIN_FIELDS);
    ISO_MANDATE_FIELDS_MAP.put(POSEntryMode.ICC_READ_WITH_PIN_9, AUTH_ICC_PIN_FIELDS);
    ISO_MANDATE_FIELDS_MAP.put(POSEntryMode.ICC_READ_WITH_NO_PIN, AUTH_ICC_PINLESS_FIELDS);
    ISO_MANDATE_FIELDS_MAP.put(POSEntryMode.ICC_READ_WITH_NO_PIN_9, AUTH_ICC_PINLESS_FIELDS);
  }

  /**
   * Method to do basic ISOMsg validation
   * 
   * @param isoMsg
   * @return
   * @throws ValidationException
   */
  public static boolean validateBasicISOMsg(ISOMsg isoMsg) throws ValidationException {
    // basic mandatory fields check
    if(!isoMsg.hasFields(BASIC_FIELDS)) {
      log.error("Basic ISOMsg validation failed");
      throw new ValidationException(ISOResponseCodes.SYSTEM_MALFUNCTION);
    }
    return true;
  }

  /**
   * Method to validate the mandatory fields present based on the transaction
   * type
   * 
   * @param isoMsg
   * @param txnType
   * @return
   * @throws ValidationException
   */
  public static boolean validateMandatoryFields(ISOMsg isoMsg, String txnType) throws ValidationException {
    txnType = (txnType != null && txnType.length() > 3) ? txnType.substring(1) : txnType;
    if(!isoMsg.hasFields(ISO_MANDATE_FIELDS_MAP.get(txnType))) {
      log.error("Error | validateMadatory | Mandatory fields check failed for:" + txnType);
      throw new ValidationException(ISOResponseCodes.INVALID_TRANSACTION);
    }
    return true;
  }
}
