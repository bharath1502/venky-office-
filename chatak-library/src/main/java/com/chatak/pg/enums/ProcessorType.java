package com.chatak.pg.enums;

/**
 * @author Girmiti
 * 
 */
public enum ProcessorType {
  LITLE("Vantiv"), CHATAK("ChatakSwitch"),PULSE("Pulse");

  private final String value;

  ProcessorType(String v) {
    value = v;
  }

  public String value() {
    return value;
  }
  
  public String getValue(){
    return value;
  }

  public static ProcessorType fromValue(String v) {
    for (ProcessorType c : ProcessorType.values()) {
      if (c.value.equals(v)) {
        return c;
      }
    }
    throw new IllegalArgumentException(v);
  }

}
