package com.chatak.pg.util;

public class LoggerMessage {

  private LoggerMessage() {
    // Do Nothing
  }

  public static String getCallerName() {
    final StackTraceElement[] stacktrace = Thread.currentThread().getStackTrace();
    StackTraceElement callerObj = stacktrace[2];
    return callerObj.getClassName() + " :: " + callerObj.getMethodName() + " method";
  }

  public static String testGetCallerName() {
    final StackTraceElement[] stacktrace = Thread.currentThread().getStackTrace();
    StackTraceElement callerObj = stacktrace[2];
    return "ERROR :: " + callerObj.getClassName() + " :: " + callerObj.getMethodName() + " method";
  }
}
