/**
 * 
 */
package com.chatak.pg.emv.util;

/**
 * << Add Comments Here >>
 * 
 * @author Girmiti Software
 * @date 04-Dec-2014 7:09:32 PM
 * @version 1.0
 */
public interface ChatakEMVTags {

  public static final int ISSUER_SCRIPT_TEMPLATE_1 = 0x71;

  public static final int ISSUER_SCRIPT_TEMPLATE = 0x72;

  public static final int AIP = 0x82; // Application Interchange Profile

  public static final int AID = 0x84; // Dedicated File Name

  public static final int IAD = 0x91; // Issuer Authentication Data

  public static final int TVR = 0x95; // Terminal Verification Results

  public static final int APPLICATION_EXPIRATION_DATE = 0x5F24;

  public static final int FCI = 0x6F; // File Control Information (FCI) Template

  public static final int FCIP = 0xA5; // File Control Information (FCI)
                                       // Proprietary Template

  public static final int TRANSACTION_DATE = 0x9A;

  public static final int TRANSACTION_STATUS_INFORMATION = 0x9B;

  public static final int TRANSACTION_TYPE = 0x9C;

  public static final int TXN_CURRENCY_CODE = 0x5F2A;

  public static final int PSN = 0x5F34; // PAN sequence number

  public static final int AMOUNT = 0x9F02; // Authorized Amount

  public static final int AMOUNT_OTHER = 0x9F03;

  public static final int APPLICATION_VERSION_NUMBER = 0x9F09; // Terminal
                                                               // Application
                                                               // Version Number

  public static final int ISSUER_APPLICATION_DATA = 0x9F10;

  public static final int TERMINAL_COUNTRY_CODE = 0x9F1A;

  public static final int IFD = 0x9F1E; // Interface Device (IFD) Serial number

  public static final int APPLICATION_CRYPTOGRAM = 0x9F26;

  public static final int CRYPTOGRAM_INFORMATION_DATA = 0x9F27;

  public static final int TERMINAL_CAPABILITIES = 0x9F33; // Terminal
                                                          // Capabilities

  public static final int CVMR = 0x9F34; // Cardholder Verification Method
                                         // Results

  public static final int TERMINAL_TYPE = 0x9F35;

  public static final int APPLICATION_TRANSACTION_COUNTER = 0x9F36;

  public static final int UNPREDICTABLE_NUMBER = 0x9F37;

  public static final int TSN = 0x9F41; // Transaction Sequence Number

  public static final int TCC = 0x9F53; // Transaction Category Code

  public static final int ISR = 0x9F5B; // Issuer Script Result

  public static final int LAN = 0x5F2D; // Language Preference
  
  public final static String[] EMV_TAGS = { "71","72",
                                            "82",
                                            "84",
                                            "90",
                                            "91",
                                            "95",
                                            "5A",
                                            "6F",
                                            "9A",
                                            "9B",
                                            "9C",
                                            "A5",
                                            "5F24",
                                            "5F2A",
                                            "5F34",
                                            "9F02",
                                            "9F03",
                                            "9F09",
                                            "9F10",
                                            "9F1A",
                                            "9F1E",
                                            "9F26",
                                            "9F27",
                                            "9F33",
                                            "9F34",
                                            "9F35",
                                            "9F36",
                                            "9F37",
                                            "9F41",
                                            "9F53",
                                            "9F5B",
                                            "5F2D" };

  

}
