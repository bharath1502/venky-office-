package com.chatak.pay.util;

/**
 * 
 */

import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.codec.binary.Base64;
import org.apache.log4j.Logger;

import com.chatak.pay.constants.Constant;
import com.chatak.pay.exception.ChatakPayException;
import com.chatak.pg.util.Constants;
import com.chatak.pg.util.EncryptionUtil;
import com.chatak.pg.util.Properties;

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
  
  @SuppressWarnings("rawtypes")
  public static boolean isListNotNullNEmpty(List list) {
    return (list != null && !list.isEmpty());
  }

  @SuppressWarnings("rawtypes")
  public static boolean isListNullNEmpty(List list) {
    return (list == null || list.isEmpty());
  }

  public static boolean isNullAndEmpty(String data) {
    return (data == null || "".equals(data.trim()));
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
  
  @SuppressWarnings({"rawtypes", "unchecked"})
  public static List startIndexList(int size, int index) {
    List list = new ArrayList();
    for (int i = 0; i <= size / index; i++) {
      list.add((i * index) + 1);

    }
    return list;
  }
  
  public static String toAmount(Object object) {
    String amount = "0.00";
    try {
      Double doubleValue = null;
      if (null != object) {
        doubleValue = Double.valueOf(object.toString());
        if (doubleValue != null) {
          amount = String.format("%.2f", doubleValue);
        }
      }
    } catch (NumberFormatException e) {
      logger.error("Error :: StringUtil :: toAmount", e);
    }
    return amount;
  }

  public static String getDateValueForWSAPI(String raw, String time) {
    if (raw == null || "".equals(raw.trim()))
      return null;
    try {
      if (raw.indexOf('-') != -1 || raw.indexOf('/') != -1 || raw.indexOf('.') != -1) {
        String splitVariable =
            (raw.indexOf('/') != -1) ? "/" : validateRaw(raw);
        String result = "";
        String[] raws = raw.split(splitVariable);
        result = (raws[0].length() < Constants.TWO) ? "0" + raws[0] : raws[0];
        result += "/" + ((raws[1].length() < Constants.TWO) ? "0" + raws[1] : raws[1]);
        result += "/" + raws[Constants.TWO];
        result += " " + time;
        return result;
      }
    } catch (Exception exp) {
      logger.error("Error :: StringUtil :: getDateValueForWSAPI", exp);
    }

    return null;
  }

  private static String validateRaw(String raw) {
    return (raw.indexOf('-') != -1) ? "-" : "\\.";
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

  public static String[] converArray(String data) {
    if (!isNullEmpty(data)) {
      String arrayData[] = data.split(",");
      return arrayData;
    }
    return new String[0];
  }

  /**
   * Method to replace all injected Scripts
   * 
   * @param description
   * @return
   */
  private static String replaceXSS(String description) {
    String newDesc = description.replaceAll("\\s{2,}", "");
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
  public static void checkScriptData(String description) throws ChatakPayException {
    if (!StringUtil.isNullAndEmpty(description)
        && description.trim().length() > StringUtil.replaceXSS(description.trim()).length()) {
      throw new ChatakPayException(Properties.getProperty("chatak.script.inject.error"));
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
   * @throws ChatakPayException 
   * @throws IllegalArgumentException
   * @throws Exception
   */
  public static String[] parseEmailToken(String emailToken) throws ChatakPayException {
    if (isNullAndEmpty(emailToken)) {
      throw new IllegalArgumentException();
    }
    String token = null;
    try {
      token = new String(Base64.decodeBase64(emailToken.getBytes()));
      token = EncryptionUtil.decrypt(token);
    } catch (Exception exp) {
      logger.error("Error :: StringUtil :: parseEmailToken", exp);
      throw new IllegalArgumentException();
    }
    if (token.contains(TOKEN_DELIMITER)) {
      String[] emailTokens = token.split(TOKEN_DELIMITER);
      if (emailTokens.length != Constants.FIVE) {
        throw new IllegalArgumentException();
      } else {
        String tokeTime = emailTokens[emailTokens.length - 1];

        int minutes =
            Integer.parseInt(Properties.getProperty("admin.user.email.token.expiry.time"));
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(Long.valueOf(tokeTime));
        cal.add(Calendar.MINUTE, minutes);
        Calendar now = Calendar.getInstance();
        if (now.after(cal)) {
          throw new ChatakPayException(Properties.getProperty("admin.user.email.token.expiry.time"));
        }
      }
      return emailTokens;
    } else {
      throw new IllegalArgumentException();
    }
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
      logger.error("Error :: StringUtil :: getLong", e);
      return null;
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
  public static Long getLong(Long value) {
    if (null == value) {
      return 0L;
    }
    return value;
  }

  public static String getSupportedType(String BarType, String qrType) {
    if (!StringUtil.isNullAndEmpty(BarType) && StringUtil.isNullAndEmpty(qrType))
      return "|" + BarType + "|";
    else if (StringUtil.isNullAndEmpty(BarType) && !StringUtil.isNullAndEmpty(qrType))
      return "|" + qrType + "|";
    return "|" + BarType + "|" + qrType + "|";

  }
  
  public static String decode(String encoded) {
    if (null == encoded || "".equals(encoded.trim())) {
      return "";
    }

    try {
      return URLDecoder.decode(encoded, "UTF-8");
    } catch (Exception e) {
      logger.error("Error :: StringUtil :: decode", e);
    }
    return encoded;
  }

  public static List<String> getSubCodeType(String codeType) {
    List<String> codeTypeList = new ArrayList<String>();
    codeType = codeType.substring(1, codeType.length() - 1);
    if (codeType.contains("\\|")) {
      String[] codeTypeData = codeType.split("\\|");
      for (String data : codeTypeData)
        codeTypeList.add(data);
    } else
      codeTypeList.add(codeType);

    return codeTypeList;
  }

  /**
   * Method to get Payment Session Id
   * 
   * @param request
   * @param session
   * @param mId
   * @return
   */
  public static String getPaymentSessionToken(HttpServletRequest request, HttpSession session,
      String mId) {
    return request.getRemoteAddr() + Constant.CHATAK_PAY_WRAP_TEXT + mId
        + Constant.CHATAK_PAY_WRAP_TEXT + session.getId();
  }

  /**
   * Method to get default terminal id
   * 
   * @param merchantId
   * @return
   */
  public static String getDefaultTerminalID(String merchantId) {
    if (null == merchantId) {
      return "10000001";
    } else if (merchantId.length() > Constants.EIGHT) {
      return merchantId.substring(Constants.EIGHT);
    } else {
      return "10000001";
    }
  }

  /**
   * Method to get Status Response
   * 
   * @param status
   * @return
   */
  public static String getStatusResponse(String status) {
    return "{\"status\":\"" + status + "\"}";
  }

  public static String hexToAscii(String hex) {
    StringBuilder output = new StringBuilder();
    try {
      for (int i = 0; i < hex.length(); i += Constants.TWO) {
        String str = hex.substring(i, i + Constants.TWO);
        output.append((char) Integer.parseInt(str, Constants.SIXTEEN));
      }
      return new String(output);
    } catch (Exception e) {
      logger.error("Error :: StringUtil :: hexToAscii", e);
      return null;
    }
  }

  public static String getEmailToken(String userId, String email) {
	String token = null;
	StringBuilder sb = new StringBuilder();
    sb.append(TOKEN_DELIMITER);
    sb.append(userId);
    sb.append(TOKEN_DELIMITER);
    sb.append(email);
    sb.append(TOKEN_DELIMITER);
    sb.append(System.currentTimeMillis());
    try {
      token = EncryptionUtil.encrypt(sb.toString());
      token = new String(Base64.encodeBase64(token.getBytes()));
    } catch (Exception exp) {
      logger.error("Error :: StringUtil :: getEmailToken", exp);
    }
    return token;
  }
  
  public static Double getDouble(Double value) {
    if (null == value) {
      return 0.0;
    }
    return value;
  }

  public static boolean isNull(Object input) {
    return (input == null);
  }
}
