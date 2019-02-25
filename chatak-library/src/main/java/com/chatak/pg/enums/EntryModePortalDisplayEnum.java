package com.chatak.pg.enums;

public enum EntryModePortalDisplayEnum {
  UNSPECIFIED("UN SPECIFIED"),
  MANUAL("MANUAL"),
  MAGNETIC_STRIP("MAGSTRIPE"),
  BARCODE("BARCODE"),
  OCR("OCR"), //Optical card reader
  ICC("ICC"),
  MANUAL_KEY_ENTRY("MANUAL"),
  PAN_AUTO_ENTRY_CONTACTLESS_M_CHIP("MANUAL CONTACTLESS M CHIP"),
  PAN_AUTO_ENTRY_ECOMMERCE("AUTO ENTRY E COMMERCE"),
  PAN_MANUAL_ENTRY_CHIP("MANUAL ENTRY CHIP"),
  PAN_SWIPE_CHIP("SWIPE CHIP"),
  PAN_MANUAL_ENTRY_ECOMMERCE("MANUAL E COMMERCE"),
  PAN_SWIPE_CONTACTLESS("CONTACTLESS SWIPE"),
  PAN_MANUAL_ENTRY_CONTACTLESS("MANUAL CONTACTLESS"), 
  CARD_TOKEN("CARD TOKEN"),
  PAN_TAP_NFC("NFC"),
  PAN_SCAN_BAR("BAR CODE"),
  PAN_SCAN_QR("QR"),
  QR_SALE("QR_SALE"),
  PAN_SCAN_BLE("BLE"),
  CARD_TAP("CARD_TAP"),
  ACCOUNT_PAY("ACCOUNT_PAY");

  private final String value;

  EntryModePortalDisplayEnum(String v) {
    value = v;
  }

  public String value() {
    return value;
  }
  public static EntryModePortalDisplayEnum fromValue(String v) {
    for (EntryModePortalDisplayEnum c : EntryModePortalDisplayEnum.values()) {
      if (c.value.equals(v)) {
        return c;
      }
    }
    throw new IllegalArgumentException(v);
  }

}
