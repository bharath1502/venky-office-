package com.chatak.pg.util;

import org.apache.log4j.Logger;

public class LogHelper {

  private LogHelper() {
    // Do nothing
  }

  public static void logEntry(Logger logger, String classMethodInfo) {
    StringBuilder sb = new StringBuilder();
    sb.append("Entering :: ").append(classMethodInfo);
    logger.info(sb.toString());
  }

  public static void logExit(Logger logger, String classMethodInfo) {
    StringBuilder sb = new StringBuilder();
    sb.append("Exiting :: ").append(classMethodInfo);
    logger.info(sb.toString());
  }

  public static void logPerformance(Logger logger, String classMethodInfo, Long startTs, Long endTs) {
    StringBuilder sb = new StringBuilder();
    Long timeDiff = (endTs - startTs) / Long.parseLong("1000");
    sb.append(classMethodInfo).append(" took ").append(timeDiff).append(" milliseconds");
    logger.debug(sb.toString());
  }

  public static void logInfo(Logger logger, String classMethodInfo, String message) {
    StringBuilder sb = new StringBuilder();
    sb.append(classMethodInfo).append(" :: ").append(message);
    logger.info(sb.toString());
  }

  public static void logDebug(Logger logger, String classMethodInfo, String message) {
    StringBuilder sb = new StringBuilder();
    sb.append(classMethodInfo).append(":: ").append(message);
    logger.debug(sb.toString());
  }

  public static void logError(Logger logger, String classMethodInfo, String message) {
    StringBuilder sb = new StringBuilder();
    sb.append("ERROR :: ").append(classMethodInfo).append(" :: ").append(message);
    logger.error(sb.toString());
  }

  public static void logError(Logger logger, String classMethodInfo, Throwable e, String message) {
    StringBuilder sb = new StringBuilder();
    sb.append("ERROR :: ").append(classMethodInfo).append(" :: ").append(message);
    logger.error(sb.toString(), e);
  }

}
