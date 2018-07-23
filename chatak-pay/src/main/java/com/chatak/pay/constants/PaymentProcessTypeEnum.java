/**
 * 
 */
package com.chatak.pay.constants;


/**
 * << Add Comments Here >>
 * 
 * @author Girmiti Software
 * @date 01-Apr-2015 3:42:44 PM
 * @version 1.0
 */
public enum PaymentProcessTypeEnum {

  C("1"), T("2"), IB("3"), N("9");
  
  private final String value;

  PaymentProcessTypeEnum(String v) {
    value = v;
  }

  public String value() {
    return value;
  }

  public static PaymentProcessTypeEnum fromValue(String v) {
    for(PaymentProcessTypeEnum c : PaymentProcessTypeEnum.values()) {
      if(c.value.equals(v)) {
        return c;
      }
    }
    throw new IllegalArgumentException(v);
  }
}
