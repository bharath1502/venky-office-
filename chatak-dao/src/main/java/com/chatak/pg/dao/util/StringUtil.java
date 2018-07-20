/**
 * 
 */
package com.chatak.pg.dao.util;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

import com.chatak.pg.acq.dao.model.PGApplicationClient;
import com.chatak.pg.util.Constants;
import com.chatak.pg.util.DateUtil;
import com.chatak.pg.util.StringUtils;


/**
 * @author Girmiti Software
 */
public final class StringUtil {

  private StringUtil() {
  }

  private static Logger logger = Logger.getLogger(StringUtil.class);
  
  public static boolean isNullAndEmpty(String data) {
    return (data == null || "".equals(data.trim()));
  }

  @SuppressWarnings("rawtypes")
  public static boolean isListNullNEmpty(List list) {
    return (list == null || list.isEmpty());
  }
  
  @SuppressWarnings("rawtypes")
  public static boolean isListNotNullNEmpty(List list) {
    return (list != null && !list.isEmpty());
  }

  /**
   * @param number
   * @return
   */
  public static String toString(Number number) {
    if(null == number) {
      return "";
    }
    else {
      return String.valueOf(number);
    }
  }


  public static String getDateValueForWSAPI(String raw, String time) {
    if(raw == null || "".equals(raw.trim()))
      return null;
    try {
      if(raw.indexOf('/') != -1 || raw.indexOf('-') != -1 || raw.indexOf('.') != -1) {
        String splitVariable = (raw.indexOf('/') != -1) ? "/" : validateRaw(raw);
        String[] raws = raw.split(splitVariable);
        String result = "";
        result = (raws[0].length() < Constants.TWO) ? "0" + raws[0] : raws[0];
        result += "/" + ((raws[1].length() < Constants.TWO) ? "0" + raws[1] : raws[1]);
        result += "/" + raws[Constants.TWO];
        result += " " + time;
        return result;
      }
    }
    catch(Exception e) {
      logger.error("ERROR:: StringUtil::getDateValueForWSAPI ", e);
    }

    return null;
  }

  private static String validateRaw(String raw) {
    return (raw.indexOf('-') != -1) ? "-" : "\\.";
  }
  
  public static boolean isNullEmpty(String input) {
	    return (input == null || "".equals(input.trim()));
	  }

  public static String toAmount(Object object) {
    String amount = "0.00";
    try {
      Double doubleValue = null;
      if(object != null) {
        doubleValue = Double.valueOf(object.toString());
        if(doubleValue != null) {
          amount = String.format("%.2f", doubleValue);
        }
      }
    }
    catch(NumberFormatException e) {
      logger.error("ERROR:: StringUtil::toAmount ", e);
    }
    return amount;
  }

  @SuppressWarnings({ "rawtypes", "unchecked" })
  public static List endIndex(int size, int index) {
    List list = new ArrayList();
    if(size == 0) {
      list.add(1);
    }
    for(int i = 1; i <= size / index; i++) {
      list.add((i * index));

    }
    return list;
  }
  
  @SuppressWarnings({ "rawtypes", "unchecked" })
  public static List startIndexList(int size, int index) {
    List list = new ArrayList();
    for(int i = 0; i <= size / index; i++) {
      list.add((i * index) + 1);

    }
    return list;
  }

  public static String[] converArray(String data) {
    if(!isNullEmpty(data)) {
      String arrayData[] = data.split(",");
      return arrayData;
    }
    return new String[0];
  }

  public static String convertString(String[] arrayData) {
    if(arrayData.length > 0) {
      StringBuilder sb = new StringBuilder();
      for(String arr : arrayData) {
        sb.append(arr + ",");
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

  /**
   * Method to convert String List to String array
   * 
   * @param featureList
   * @return
   */
  public static String convertListToString(List<String> featureList) {

    if(StringUtil.isListNotNullNEmpty(featureList)) {
      StringBuilder sb = new StringBuilder();
      for(String feature : featureList) {
        sb.append(feature + ",");
      }
      return sb.toString().substring(0, sb.toString().length() - 1);
    }
    return "";
  }

  public static Boolean checkEquality(String newValue, String oldValue, boolean mand) {
    if(mand)
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
  public static Long getLong(String value) {
    if(null == value) {
      return null;
    }
    try {
      return Long.valueOf(value);
    }
    catch(NumberFormatException e) {
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
    if(null == value) {
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
  public static Long getLong(Long value) {
    if(null == value) {
      return 0L;
    }
    return value;
  }

  public static List<String> getSubCodeType(String codeType) {
    codeType = codeType.substring(1, codeType.length() - 1);
    List<String> codeTypeList = new ArrayList<String>();
    if(codeType.contains("\\|")) {
      String[] codeTypeData = codeType.split("\\|");
      for(String data : codeTypeData)
        codeTypeList.add(data);
    }
    else
      codeTypeList.add(codeType);

    return codeTypeList;
  }
  
  public static String getSupportedType(String BarType, String qrType) {
	    if(!StringUtil.isNullAndEmpty(BarType) && StringUtil.isNullAndEmpty(qrType))
	      return "|" + BarType + "|";
	    else if(StringUtil.isNullAndEmpty(BarType) && !StringUtil.isNullAndEmpty(qrType))
	      return "|" + qrType + "|";
	    return "|" + BarType + "|" + qrType + "|";

	  }

  public static boolean validateUserName(String userName) {
    Pattern pattern;
    Matcher matcher;
    final String USERNAME_PATTERN = "^[A-Za-z0-9 _]+$";
    pattern = Pattern.compile(USERNAME_PATTERN);
    matcher = pattern.matcher(userName);
    return matcher.matches();
  }

  /**
   * @param object
   * @return
   */
  public static boolean isNull(Object object) {
    if(object == null) {
      return true;
    }
    else {
      return false;
    }
  }

  public static PGApplicationClient getApplicationClientDTO() {
    PGApplicationClient applicationClient = new PGApplicationClient();
    applicationClient.setAppName(Constants.APP_NAME);
    applicationClient.setAppDescription(Constants.APP_DESCRIPTION);
    applicationClient.setAppAdminEmail(Constants.APP_ADMIN_EMAIL);
    applicationClient.setAppClientName(Constants.APP_CLIENT_NAME);
    applicationClient.setAppClientEmail(Constants.APP_ADMIN_EMAIL);
    applicationClient.setAppClientPhone(Constants.APP_CLIENT_PHONE);
    applicationClient.setAppClientAddress(Constants.APP_CLIENT_ADDRESS);
    applicationClient.setAppClientCity(Constants.APP_CLIENT_CITY);
    applicationClient.setAppClientCountry(Constants.APP_CLIENT_COUNTRY);
    applicationClient.setAppClientZip(Constants.APP_CLIENT_ZIP);
    applicationClient.setAppClientRole(Constants.APP_CLIENT_ROLE);
    applicationClient.setStatus(0);
    applicationClient.setActiveFrom(new Timestamp(System.currentTimeMillis()));
    applicationClient.setActiveTill(new Timestamp(System.currentTimeMillis()));
    applicationClient.setAppClientAccess(Constants.APP_CLIENT_ACCESS);
    applicationClient.setAppAuthPass(StringUtils.randomNumeric(Integer.parseInt("12")));
    applicationClient.setCreatedBy(Constants.CREATED_BY);
    applicationClient.setCreatedDate(DateUtil.getCurrentTimestamp());
    return applicationClient;
  }
}
