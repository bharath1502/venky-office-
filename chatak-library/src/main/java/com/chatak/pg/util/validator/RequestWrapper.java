package com.chatak.pg.util.validator;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import org.apache.log4j.Logger;

public final class RequestWrapper extends HttpServletRequestWrapper {
  private static Logger logger = Logger.getLogger(RequestWrapper.class);

  public RequestWrapper(HttpServletRequest servletRequest) {
    super(servletRequest);
  }

  public String[] getParameterValues(String parameter) {
    logger.debug("InarameterValues .. parameter .......");
    String[] values = super.getParameterValues(parameter);
    if(values == null) {
      return new String[0];
    }
    int count = values.length;
    String[] encodedValues = new String[count];
    for(int i = 0; i < count; i++) {
      encodedValues[i] = cleanXSS(values[i]);
    }
    return encodedValues;
  }

  public String getParameter(String parameter) {
    logger.debug("Inarameter .. parameter .......");
    String value = super.getParameter(parameter);
    if(value == null) {
      return null;
    }
    logger.debug("Inarameter RequestWrapper ........ value .......");
    return cleanXSS(value);
  }

  @Override
  public String getHeader(String name) {
    logger.debug("Ineader .. parameter .......");
    String value = super.getHeader(name);
    if(value == null) {
      return null;
    }
    logger.debug("Ineader RequestWrapper ........... value ....");
    return cleanXSS(value);
  }

  private String cleanXSS(String value) {
    value = value.replaceAll("%(?![0-9a-fA-F]{2})", "%25");
    value = value.replaceAll("\\+", "%2B");
    try {
      value = URLDecoder.decode(value, "utf-8");
    } catch(UnsupportedEncodingException e) {
      logger.error("Error :: RequestWrapper :: cleanXSS", e);
      return "";
    }

    value = value.replaceAll("<", "").replaceAll(">", "");
    value = value.replaceAll("\\(", "&#40;").replaceAll("\\)", "&#41;");
    value = value.replaceAll("'", "&#39;");

    value = value.replaceAll("\\<.*?>", "");
    value = value.replaceAll("\\\">", "");
    value = value.replaceAll("\\\"", "");
    if(!super.getRequestURI().contains("password-reset")) {
      value = value.replaceAll("=", "");
    }
    value = value.replaceAll("'", "");
    value = value.replaceAll("%27", "");

    value = value.replaceAll("eval\\((.*)\\)", "");
    value = value.replaceAll("[\\\"\\\'][\\s]*javascript:(.*)[\\\"\\\']", "\"\"");

    value = value.replaceAll("(?i)<script.*?>.*?<script.*?>", "");
    value = value.replaceAll("(?i)<script.*?>.*?</script.*?>", "");
    value = value.replaceAll("(?i)<.*?javascript:.*?>.*?</.*?>", "");
    value = value.replaceAll("(?i)<.*?\\s+on.*?>.*?</.*?>", "");
    value = value.replaceAll("alert(.*)", "");
    value = value.replaceAll("accesskey=.*", "");
    value = value.replaceAll("confirm(.*)", "");
    value = value.replaceAll("prompt(.*)", "");

    logger.debug("OutnXSS RequestWrapper ........ value ......." + value);
    return value;
  }

}
