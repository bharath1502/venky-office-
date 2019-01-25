package com.chatak.pay.constants;

public interface Constant {

  public static final String ERROR = "error";
  
  public static final String MSG = "msg";

  public static final String SUCESS = "sucess";

  public static final String URL_SPERATOR = "/pg/";

  public static final String INVALID_SESSION = "session-invalid";

  public static final String INVALID_ACCESS = "invalid-access";
  
  public static final String UNKNOWN_ERROR = "unknown-error";

  public static final String CHATAK_PAY_SESSION = "CHATAK_PAY";

  public static final String CHATAK_PAY_INIT_SESSION = "CHATAK_PAY_INIT";

  public static final String CHATAK_PAY_INIT_SESSION_ID_SESSION = "CHATAK_PAY_INIT_SESSION";
  
  public static final String CHATAK_PAY_SAVE_CARD_SESSION = "saveRequired";
  
  public static final String CHATAK_PAY_MODE_TYPE = "PROCESS_MODE";
  
  public static final String CHATAK_PAY_CARD_TYPE = "CARD_TYPE";

  public static final String CHATAK_PAY_PASS_KEY_SESSION = "CHATA_PAY_PASS_KEY";

  public static final String CHATAK_PAY_INIT_REQUEST_ID = "CHATAK_PAY_INIT_REQUEST_ID";

  public static final String CHATAK_PAY_INIT_REQUEST_UUID_SESSION = "CHATAK_PAY_INIT_UUID";

  public static final String CHATAK_PAY_TXN_SESSION = "CHATAK_PAY_TXN";

  public static final String CHATAK_PAY_TXN_STATE_SESSION = "CHATAK_PAY_TXN_STATE";

  public static final String CHATAK_PAY_JS_CRYPT_KEYS_SESSION = "jCryptionKeys";

  public static final String CHATAK_PAY_JS_CRYPT_KEY_SESSION = "jCryptionKey";
  
  public static final String CHATAK_PAY_ENC_KEY_SESSION = "key";
  
  public static final String CHATAK_PAY_ENC_ACTION = "action";
  
  public static final String CHATAK_PAY_ENC_GENERATE = "generate";
  
  public static final String CHATAK_PAY_ENC_HANDSHAKE = "handshake";

  public static final String CHATAK_PAY_WRAP_TEXT = "cHataK>";

  public static final String CHATAK_PAY_MERCHANT_ID = "CHATA_PAY_MERCHANT";

  public static final String CHATAK_PAY_RETURN_URL = "CHATAK_RETURN_URL";

  public static final String CHATAK_PAY_APPROVED = "Approved";

  public static final String CHATAK_PAY_ECOMMERCE_POS_ENTRY_MODE = "012";
  
  public static final String STATUS_F = "F";
  
  public static final String STATUS_S = "S";

  // Field Lengths
  public static final Integer LENGTH_TXN_REF_NUM = 12;

  public static final Integer LENGTH_AUTH_ID = 6;

  // Status
  public static final Integer STATUS_SUCCESS = 0;

  public static final Integer STATUS_INPROCESS = 1;

  public static final Integer STATUS_FAILED = 2;

  public static final Integer STATUS_DECLINED = 3;
  
 public static final String INITIATE = "1";

 public static final String PROGRESS = "2";

 public static final String COMPLETED = "3";
 
 public static final String PAYMENT_STEP_1 = "1";
 
 public static final String PAYMENT_STEP_2 = "2";
 
 public static final String PAYMENT_STEP_3 = "3";
 
 public static final String PAYMENT_STEP_4 = "4";
 
 public static final String PAYMENT_STEP_5 = "5";
 
 public static final String PAYMENT_STEP_6 = "6";
 
 public static final String PAYMENT_STEP_7 = "7";
 
 public static final Integer SIXTEEN = 16;
 
 public static final String SIXTEEN_STR = "16";
 
 public static final Integer ELEVEN = 11;
 
 public static final String ELEVEN_STR = "11";


  // Acquiring channel
  public static final String ACQUIRING_CHANNEL_WEB = "Web";
  
  public static final String YES = "Y";
  
  public static final String NO = "N";
  
  public static final Integer ZERO = 0;

  public static final Integer ONE = 1;
  
  public static final String SEPARATOR = "|";
  
//url for paygate
 public static final String ROOT_URL="http://192.168.0.92:8062/paygate/pg/transaction";

}
