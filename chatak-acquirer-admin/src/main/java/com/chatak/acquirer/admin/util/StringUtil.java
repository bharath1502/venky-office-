package com.chatak.acquirer.admin.util;

/**
 * 
 */

import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.log4j.Logger;

import com.chatak.acquirer.admin.controller.model.Option;
import com.chatak.acquirer.admin.exception.ChatakAdminException;
import com.chatak.pg.util.Constants;
import com.chatak.pg.util.EncryptionUtil;
import com.chatak.pg.util.Properties;

import jxl.write.NumberFormats;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WriteException;

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

  public static boolean isNullAndEmpty(String data) {
    return (data == null || "".equals(data.trim()));
  }

  @SuppressWarnings("rawtypes")
  public static boolean isListNotNullNEmpty(List list) {
    return (list != null && !list.isEmpty());
  }

  /**
   * 
   */
  private StringUtil() {

  }

  @SuppressWarnings("rawtypes")
  public static boolean isListNullNEmpty(List list) {
    return (list == null || list.isEmpty());
  }

  public static String getDateValueForWSAPI(String raw, String time) {
    if (raw == null || "".equals(raw.trim()))
      return null;
    try {
      if (raw.indexOf('/') != -1 || raw.indexOf('-') != -1 || raw.indexOf('.') != -1) {
        String splitVariable =
            (raw.indexOf('/') != -1) ? "/" : validateRaw(raw);
        String[] raws = raw.split(splitVariable);
        String result = "";
        result = (raws[0].length() < Constants.TWO) ? "0" + raws[0] : raws[0];
        result += "/" + ((raws[1].length() < Constants.TWO) ? "0" + raws[1] : raws[1]);
        result += "/" + raws[Constants.TWO];
        result += " " + time;
        return result;
      }
    } catch (Exception e) {
      logger.error("Error :: StringUtil :: getDateValueForWSAPI", e);
    }

    return null;
  }

  private static String validateRaw(String raw) {
    return (raw.indexOf('-') != -1) ? "-" : "\\.";
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
    } catch (NumberFormatException exp) {
      logger.error("Error :: StringUtil :: toAmount", exp);
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

  public static String[] converArray(String data) {
    if (!isNullEmpty(data)) {
      return data.split(",");
    }
    return new String[0];
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
   * Check given input has any scripts
   * 
   * @param description
   * @throws Exception
   */
  public static void checkScriptData(String description) throws ChatakAdminException {
    if (!StringUtil.isNullAndEmpty(description)
        && description.trim().length() > StringUtil.replaceXSS(description.trim()).length()) {
      throw new ChatakAdminException(Properties.getProperty("chatak.script.inject.error"));
    }
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
      logger.error("Error :: StringUtil :: getEmailToken", e);
    }
    return token;
  }

  /**
   * Method to parse email token
   * 
   * @param emailToken
   * @return
   * @throws IllegalArgumentException
   * @throws Exception
   */
  public static String[] parseEmailToken(String emailToken) throws ChatakAdminException {
    String token = null;
    if (isNullAndEmpty(emailToken)) {
      throw new IllegalArgumentException();
    }
    try {
      token = new String(Base64.decodeBase64(emailToken.getBytes()));
      token = EncryptionUtil.decrypt(token);
    } catch (Exception exp) {
      logger.error("Error :: StringUtil :: parseEmailToken", exp);
      throw new IllegalArgumentException();
    }
    if (token.contains(TOKEN_DELIMITER)) {
      String[] emailTokens = token.split(TOKEN_DELIMITER);
      if (Constants.FOUR != emailTokens.length) {
        throw new IllegalArgumentException();
      } else {
        String tokeTime = emailTokens[emailTokens.length - 1];

        int minutes =
            Integer.parseInt(Properties.getProperty("chatak.admin.email.token.expiry.time"));
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(Long.valueOf(tokeTime));
        cal.add(Calendar.MINUTE, minutes);
        Calendar now = Calendar.getInstance();
        if (now.after(cal)) {
          throw new ChatakAdminException(
              Properties.getProperty("chatak.admin.email.token.expiry.time"));
        }
      }
      return emailTokens;
    } else {
      logger.info("Exiting :: StringUtil :: parseEmailToken");
      throw new ChatakAdminException(
          Properties.getProperty("chatak.admin.email.token.expiry.time"));
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

  public static String getSupportedType(String barType, String qrType) {
    if (!StringUtil.isNullAndEmpty(barType) && StringUtil.isNullAndEmpty(qrType))
      return "|" + barType + "|";
    else if (StringUtil.isNullAndEmpty(barType) && !StringUtil.isNullAndEmpty(qrType))
      return "|" + qrType + "|";
    return "|" + barType + "|" + qrType + "|";

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

  public static List<String> getSubCodeType(String codeType) {
    List<String> codeTypeList = new ArrayList<>();
    codeType = codeType.substring(1, codeType.length() - 1);
    if (codeType.contains("\\|")) {
      String[] codeTypeData = codeType.split("\\|");
      for (String data : codeTypeData)
        codeTypeList.add(data);
    } else
      codeTypeList.add(codeType);

    return codeTypeList;
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


  public static String listToString(List<String> featureList) {

    if (StringUtil.isListNotNullNEmpty(featureList)) {
      StringBuilder sb = new StringBuilder();
      for (String feature : featureList) {
        sb.append(feature + "\r\n ");
      }
      return sb.toString();
    }
    return "";
  }

  public static boolean isNull(Object input) {
    return (input == null);
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

  public static WritableCellFormat getExportUtilCellFormat() throws WriteException {
    WritableFont cellFont = new WritableFont(WritableFont.ARIAL, Constants.TEN);
    cellFont.setBoldStyle(WritableFont.BOLD);
    return new WritableCellFormat(cellFont);
  }

  public static jxl.write.Number getAmountInFloat(int i, int j, Double amount) {
    WritableCellFormat floatFormat = new WritableCellFormat(NumberFormats.FLOAT);
    return new jxl.write.Number(i, j, ((amount != null) ? amount : 0d), floatFormat);
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

  public static String encodeToString(byte[] image, String type) {
    String imageString = null;
    String encodedImage = null;

    try {
      encodedImage = org.apache.commons.codec.binary.Base64.encodeBase64String(image);
      imageString = "data:image/" + type + ";base64," + encodedImage;
    } catch (Exception e) {
      Logger.getLogger(StringUtil.class).error("Error :: StringUtil :: encodeToString", e);
    }
    return imageString;
  }

  public static String escapeHTMLChars(String value) {
    return StringEscapeUtils.escapeHtml3(value);
  }

  public static final Comparator<Option> ALPHABETICAL_ORDER = ((Option str1, Option str2) -> {
    int res = String.CASE_INSENSITIVE_ORDER.compare(str1.getValue(), str2.getValue());
    if (0 == res) {
      res = str1.getValue().compareTo(str2.getValue());
    }
    return res;
  });
}
