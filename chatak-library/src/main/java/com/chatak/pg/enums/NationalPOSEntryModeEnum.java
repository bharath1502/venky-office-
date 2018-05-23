package com.chatak.pg.enums;

/**
 * << DE 58(National POS entry mode) from pulse spec
 * PULSE_ISO_8583_Specifications_Manual_15.1_FINAL_v1.0_101714_REDLINE (Pg.no:
 * 207) >> The national POS condition code field consists of four sub-elements:
 *  Terminal Class (positions 1-3).  Presentation Type (positions 4-7). 
 * Security Condition Code (position 8) and  Terminal Type (positions 9-10) . 
 * Card Data Input Capability (position 11).
 *
 * @author Girmiti Software
 * @date Jul 21, 2015 3:20:37 PM
 * @version 1.0
 */
public enum NationalPOSEntryModeEnum {
  UNSPECIFIED_DE58("00000004250"),
  MANUAL_DE58("00000004011"),
  MAGNETIC_STRIP_DE58("00000004013"),
  BARCODE_DE58("00000004011"),
  OCR_DE58("00000004014"),
  ICC_DE58("00000004015"),
  MANUAL_KEY_ENTRY_DE58("00000004016"),
  PAN_AUTO_ENTRY_CONTACTLESS_M_CHIP_DE58("0000000425M"),
  PAN_AUTO_ENTRY_ECOMMERCE_DE58("00000004251"),
  PAN_MANUAL_ENTRY_CHIP_DE58("0000000425T"),
  PAN_SWIPE_CHIP_DE58("00000004255"),
  PAN_MANUAL_ENTRY_ECOMMERCE_DE58("00000004251"),
  PAN_SWIPE_CONTACTLESS_DE58("0000000425A"),
  PAN_MANUAL_ENTRY_CONTACTLESS_DE58("0000000425T"),
  CARD_TOKEN_DE58("00000004259"),//Card token (File) -9
  PAN_TAP_NFC_DE58("00000004250"),//Adding dummy vlaues for demo purpose default unspecified
  PAN_SCAN_BAR_DE58("00000004250"),//Adding dummy vlaues for demo purpose default unspecified
  PAN_SCAN_QR_DE58("00000004250"),//Adding dummy vlaues for demo purpose default unspecified
  PAN_SCAN_BLE_DE58("00000004250"),//Adding dummy vlaues for demo purpose default unspecified
  QR_SALE_DE58("00000004033"), 
  CARD_TAP_DE58("00000004034");
  private final String value;

  NationalPOSEntryModeEnum(String v) {
    value = v;
  }

  public String value() {
    return value;
  }

  public static NationalPOSEntryModeEnum fromValue(String v) {
    for(NationalPOSEntryModeEnum c : NationalPOSEntryModeEnum.values()) {
      if(c.value.equals(v)) {
        return c;
      }
    }
    throw new IllegalArgumentException(v);
  }

}
