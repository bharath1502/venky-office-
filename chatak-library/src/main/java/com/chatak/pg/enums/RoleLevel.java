package com.chatak.pg.enums;

public enum RoleLevel {
  
  CP_SUPER_ADMIN("Admin"),
  
  CPSA(""), CPA(""), CPPA(""),
  
  CP_MERCHANT("Merchant"),
  CP_RESELLER("Reseller"),
  CP_TMS("Tms"),
  CP_PM("Program Manager"),
  CP_ISO("ISO");
  
  private String value;

  RoleLevel(String v) {
      this.value = v;
  }

  public String getValue() {
      return value;
  }

  public static RoleLevel fromValue(String v) {
      for (RoleLevel c: RoleLevel.values()) {
          if (c.value.equals(v)) {
              return c;
          }
      }
      throw new IllegalArgumentException(v);
  }
  
  
}
