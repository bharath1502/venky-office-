package com.chatak.pg.constants;

import java.util.HashMap;

@SuppressWarnings({ "unchecked", "rawtypes" })
public class VaultErrorCodes {
  private static HashMap mMessages;

  private static VaultErrorCodes mInstance = null;

  public static final String ERROR_CODE_V00 = "V00";

  public static final String ERROR_CODE_V01 = "V01";

  public static final String ERROR_CODE_V02 = "V02";

  public static final String ERROR_CODE_V03 = "V03";

  public static final String ERROR_CODE_V04 = "V04";

  public static final String ERROR_CODE_V05 = "V05";

  public static final String ERROR_CODE_V06 = "V06";

  public static final String ERROR_CODE_V07 = "V07";

  public static final String ERROR_CODE_V08 = "V08";

  public static final String ERROR_CODE_V09 = "V09";

  public static final String ERROR_CODE_V10 = "V10";

  public static final String ERROR_CODE_V11 = "V11";

  public static final String ERROR_CODE_V12 = "V12";

  public static final String ERROR_CODE_V13 = "V13";

  public static final String ERROR_CODE_V14 = "V14";

  public static final String ERROR_CODE_V15 = "V15";

  public static final String ERROR_CODE_V16 = "V16";

  public static final String ERROR_CODE_V17 = "V17";

  /** Default Constructor */

  public VaultErrorCodes() {
    initMessages();
  }

  private static void initMessages() {
    mMessages = new HashMap(Integer.parseInt("125"));
    mMessages.put(ERROR_CODE_V00, "Success");
    mMessages.put(ERROR_CODE_V01, "Card Number Already Registered");
    mMessages.put(ERROR_CODE_V02, "Unable To Create User");
    mMessages.put(ERROR_CODE_V03, "Invalid Card Data");
    mMessages.put(ERROR_CODE_V04, "Invalid User Details");
    mMessages.put(ERROR_CODE_V05, "Invalid User Email");
    mMessages.put(ERROR_CODE_V06, "Card Details Not Present");
    mMessages.put(ERROR_CODE_V07, "Invalid Card Holder Name");
    mMessages.put(ERROR_CODE_V08, "Invalid Card Number");
    mMessages.put(ERROR_CODE_V09, "Invalid Card Expiry Date(Should be YYMM)");
    mMessages.put(ERROR_CODE_V10, "Invlaid Card Type");
    mMessages.put(ERROR_CODE_V11, "Invlaid Register Card Request");
    mMessages.put(ERROR_CODE_V12, "Invalid Authentication Details");
    mMessages.put(ERROR_CODE_V13, "Invalid Request");
    mMessages.put(ERROR_CODE_V14, "Unable to Process The Request");
    mMessages.put(ERROR_CODE_V15, "Password Should contain Only Numerics(Min 6 digit and Max 12 digit)");
    mMessages.put(ERROR_CODE_V16, "Token Generation Failed");
    mMessages.put(ERROR_CODE_V17, "Invalid track data");

  }

  /**
   * Returns the singleton instance.
   *
   * @return the singleton instance
   */
  public static final synchronized VaultErrorCodes getInstance() {
    if(mInstance == null) {
      mInstance = new VaultErrorCodes();
    }
    return mInstance;
  }
  
  /**
   * This method get the message based on code
   *
   * @param code
   * @return String
   */
  public String getMessage(String code) {
    Object obj = mMessages.get(code);
    String msg = null;
    if(obj != null)
      msg = (String) obj;
    else
      msg = "Unknown Error";
    return msg;
  }
}
