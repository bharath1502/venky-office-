/**
 * 
 */
package com.chatak.pg.util;

import org.apache.log4j.MDC;
import org.apache.logging.log4j.ThreadContext;

public class MDCLoggerUtil {

  private MDCLoggerUtil() {
    // Do nothing
  }

  /**
   * Set MDC logger paramters
   * 
   * @param invoiceNumber
   */
  public static void setMDCLoggerParamsAdmin(String invoiceNumber) {
    MDC.put("invoiceNumber", "[I:" + invoiceNumber + "]");
    ThreadContext.put("invoiceNumber", "[I:" + invoiceNumber + "]");
  }

  public static void clearMDCLoggerParams() {
    MDC.clear();
  }

}
