package com.chatak.merchant.util;

/**
 * 
 */

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.codec.binary.Base64;
import org.apache.log4j.Logger;


import com.chatak.merchant.exception.ChatakMerchantException;
import com.chatak.pg.util.Constants;
import com.chatak.pg.util.EncryptionUtil;
import com.chatak.pg.util.Properties;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

/**
 *
 * << Add Comments Here >>
 *
 * @author Girmiti Software
 * @date 03-Jan-2015 10:37:10 AM
 * @version 1.0
 */
public final class StringUtil {
	
  private static Logger logger = Logger.getLogger(StringUtil.class);
  
  private static final String TOKEN_DELIMITER = "@K@";

  /**
   * 
   */
  private StringUtil() {

  }

  public static boolean isNullAndEmpty(String data) {
    return (data == null || "".equals(data.trim()));
  }

  @SuppressWarnings("rawtypes")
  public static boolean isListNotNullNEmpty(List list) {
    return (list != null && !list.isEmpty());
  }

  @SuppressWarnings("rawtypes")
  public static boolean isListNullNEmpty(List list) {
    return (list == null || list.isEmpty());
  }

  /**
   * @param number
   * @return
   */
  public static String toString(Number number) {
    if (null == number) {
      return "";
    } else {
      return String.valueOf(number);
    }
  }

  public static boolean isNullEmpty(String input) {
    return (input == null || "".equals(input.trim()));
  }

  public static String getDateValueForWSAPI(String raw, String time) {
    if (raw == null || "".equals(raw.trim()))
      return null;
    try {
      if (raw.indexOf('/') != -1 || raw.indexOf('-') != -1 || raw.indexOf('.') != -1) {
        String result = "";
        String splitVariable =
            (raw.indexOf('/') != -1) ? "/" : validateRaw(raw);
        String[] raws = raw.split(splitVariable);
        result = (raws[0].length() < Constants.TWO) ? "0" + raws[0] : raws[0];
        result += "/" + ((raws[1].length() < Constants.TWO) ? "0" + raws[1] : raws[1]);
        result += "/" + raws[Constants.TWO];
        result += " " + time;
        return result;
      }
    } catch (Exception e) {
    	logger.error("ERROR :: StringUtil :: getDateValueForWSAPI method", e);
    }

    return null;
  }

  private static String validateRaw(String raw) {
    return (raw.indexOf('-') != -1) ? "-" : "\\.";
  }

  public static String toAmount(Object object) {
    String amount = "0.00";
    try {
      Double doubleValue = null;
      if (object != null) {
        doubleValue = Double.valueOf(object.toString());
        if (null != doubleValue) {
          amount = String.format("%.2f", doubleValue);
        }
      }
    } catch (NumberFormatException e) {
    	logger.error("ERROR :: StringUtil :: toAmount method", e);
    }
    return amount;
  }

  @SuppressWarnings({"rawtypes", "unchecked"})
  public static List startIndexList(int size, int index) {
    List list = new ArrayList();
    for (int i = 0; i <= size / index; i++) {
      list.add((i * index) + 1);

    }
    return list;
  }

  @SuppressWarnings({"rawtypes", "unchecked"})
  public static List endIndex(int size, int index) {
    List list = new ArrayList();
    if (size == 0) {
      list.add(1);
    }
    for (int i = 1; i <= size / index; i++) {
      list.add((i * index));

    }
    return list;
  }

  public static String[] converArray(String data) {
    if (!isNullEmpty(data)) {
      String arrayData[] = data.split(",");
      return arrayData;
    }
    return new String[0];
  }

  public static String convertString(String[] arrayData) {
    if (arrayData != null && arrayData.length > 0) {
      StringBuilder sb = new StringBuilder();
      for (String arr : arrayData) {
        sb.append(arr + ",");
      }
      return sb.toString().substring(0, sb.toString().length() - 1);
    }
    return "";
  }

  /**
   * Method to convert String List to String array
   * 
   * @param featureList
   * @return
   */
  public static String convertListToString(List<String> featureList) {

    if (StringUtil.isListNotNullNEmpty(featureList)) {
      StringBuilder sb = new StringBuilder();
      for (String feature : featureList) {
        sb.append(feature + ",");
      }
      return sb.toString().substring(0, sb.toString().length() - 1);
    }
    return "";
  }

  /**
   * Method to replace all injected Scripts
   * 
   * @param description
   * @return
   */
  private static String replaceXSS(String description) {
    String newDesc = description.replaceAll("\\s{Constants.TWO,}", "");
    if (newDesc.toLowerCase().contains("<script>")
        || newDesc.toLowerCase().contains("type='text/javascript'")
        || newDesc.toLowerCase().contains("javascript")) {
      return "";
    }
    description = description.replaceAll("<", "<").replaceAll(">", ">");
    description = description.replaceAll("eval\\((.*)\\)", "");
    description = description.replaceAll("[\\\"\\\'][\\s]*((?i)javascript):(.*)[\\\"\\\']", "\"\"");
    return description;
  }

  /**
   * Check given input has any scripts
   * 
   * @param description
   * @throws Exception
   */
  public static void checkScriptData(String description) throws ChatakMerchantException {
    if (!StringUtil.isNullAndEmpty(description)
        && description.trim().length() > StringUtil.replaceXSS(description.trim()).length()) {
      throw new ChatakMerchantException(Properties.getProperty("chatak.script.inject.error"));
    }
  }

  /**
   * Method to validate Mobile Number
   * 
   * @param phone
   * @return
   */
  public static boolean validatePhone(String phone) {
    boolean flag = true;
    Pattern pattern = Pattern.compile("^[0-9]+$");
    Matcher matcher = pattern.matcher(phone);
    flag = matcher.matches();
    return flag;
  }

  /**
   * Method to parse email token
   * 
   * @param emailToken
   * @return
   * @throws IllegalArgumentException
   * @throws Exception
   */
  public static String[] parseEmailToken(String emailToken)
      throws ChatakMerchantException {
    if (isNullAndEmpty(emailToken)) {
      throw new IllegalArgumentException();
    }
    String token = null;
    try {
      token = new String(Base64.decodeBase64(emailToken.getBytes()));
      token = EncryptionUtil.decrypt(token);
    } catch (Exception e) {
      logger.error("ERROR :: StringUtil :: parseEmailToken method", e);
      throw new IllegalArgumentException();
    }
    if (token.contains(TOKEN_DELIMITER)) {
      String[] emailTokens = token.split(TOKEN_DELIMITER);
      if (emailTokens.length != Constants.FOUR) {
        throw new IllegalArgumentException();
      } else {
        String tokeTime = emailTokens[emailTokens.length - 1];

        int minutes =
            Integer.parseInt(Properties.getProperty("merchant.user.email.token.expiry.time"));
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(Long.valueOf(tokeTime));
        cal.add(Calendar.MINUTE, minutes);
        Calendar now = Calendar.getInstance();
        if (now.after(cal)) {
          throw new ChatakMerchantException(Properties.getProperty("merchant.user.email.token.expiry.time"));
        }
      }
      return emailTokens;
    } else {
      throw new IllegalArgumentException();
    }
  }

  public static Boolean checkEquality(String newValue, String oldValue, boolean mand) {
    if (mand)
      return (newValue.trim().equals(oldValue.trim())) ? true : false;
    else {
      newValue = (StringUtil.isNullAndEmpty(newValue)) ? "" : newValue.trim();
      oldValue = (StringUtil.isNullAndEmpty(oldValue)) ? "" : oldValue.trim();
      return (newValue.equals(oldValue)) ? true : false;
    }

  }

  /**
   * Method to get String based on Long value
   * 
   * @param value
   * @return
   */
  public static String getString(Long value) {
    if (null == value) {
      return null;
    }
    return value.toString();
  }

  /**
   * Method to get String based on Long value
   * 
   * @param value
   * @return
   */
  public static Long getLong(String value) {
    if (null == value) {
      return null;
    }
    try {
      return Long.valueOf(value);
    } catch (NumberFormatException e) {
      return null;
    }
  }

  /**
   * Method to get String based on Long value
   * 
   * @param value
   * @return
   */
  public static Long getLong(Long value) {
    if (null == value) {
      return 0L;
    }
    return value;
  }

  /**
   * Method to get Cookie Value
   * 
   * @param request
   * @return
   */
  public static String getCookieValue(HttpServletRequest request) {
    Cookie[] cookies = request.getCookies();
    String cookieValue = "DUMMY_COOKIETEST";
    for (Cookie cookie : cookies) {
      if (Constants.COOKIE_CHATAK_NAME.equalsIgnoreCase(cookie.getName())) {
        cookieValue = cookie.getValue();
        break;
      }
    }
    return cookieValue;
  }

  public static String getEmailToken(String userId, String email) {
    StringBuilder sb = new StringBuilder();
    sb.append(TOKEN_DELIMITER);
    sb.append(userId);

    sb.append(TOKEN_DELIMITER);
    sb.append(email);
    sb.append(TOKEN_DELIMITER);
    sb.append(System.currentTimeMillis());
    String token = null;
    try {
      token = EncryptionUtil.encrypt(sb.toString());
      token = new String(Base64.encodeBase64(token.getBytes()));
    } catch (Exception e) {
    	logger.error("ERROR :: StringUtil :: getEmailToken method", e);
    }
    return token;
  }

  public static String convertObjectToJSON(Object object) throws ChatakMerchantException {

    String input = "";
    ObjectWriter objectWriter = new ObjectMapper().writer().withDefaultPrettyPrinter();
    try {
      input = objectWriter.writeValueAsString(object);
    } catch (JsonGenerationException e) {
    	logger.error("ERROR :: StringUtil :: convertObjectToJSON method", e);
      throw new ChatakMerchantException(e.getMessage());
    } catch (JsonMappingException e) {
    	logger.error("ERROR :: StringUtil :: convertObjectToJSON method", e);
      throw new ChatakMerchantException(e.getMessage());
    } catch (IOException e) {
    	logger.error("ERROR :: StringUtil :: convertObjectToJSON method", e);
      throw new ChatakMerchantException(e.getMessage());
    }
    return input;
  }

  public static String amountToString(Long cents) {
    String amount = "0.00";
    if (null != cents) {
      amount = String.format("%.2f", (cents / Constants.HUNDRED));
    }
    return amount;
  }

  public static boolean isNull(Object input) {
    return (input == null);
  }
  
}
