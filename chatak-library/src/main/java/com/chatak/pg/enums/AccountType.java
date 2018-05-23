/**
 * 
 */
package com.chatak.pg.enums;

/**
 * << Add Comments Here >>
 * 
 * @author Girmiti Software
 * @date 17-Aug-2015 11:26:54 PM
 * @version 1.0
 */
public enum AccountType {

  S("Savings"), C("Current");

  private final String value;

  AccountType(String v) {
    value = v;
  }

  public String value() {
    return value;
  }
  
  public static AccountType fromValue(String v) {
    for (AccountType c: AccountType.values()) {
        if (c.value.equals(v)) {
            return c;
        }
    }
    throw new IllegalArgumentException(v);
}

}
