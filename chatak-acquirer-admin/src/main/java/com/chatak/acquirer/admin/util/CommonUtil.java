package com.chatak.acquirer.admin.util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeansException;

import com.chatak.acquirer.admin.constants.StatusConstants;
import com.chatak.acquirer.admin.controller.model.LoginResponse;
import com.chatak.pg.bean.Response;
import com.chatak.pg.user.bean.IsoRequest;
import com.chatak.pg.user.bean.ProgramManagerRequest;
import com.chatak.pg.util.Constants;

/**
 * @author Girmiti Software
 *
 */
@SuppressWarnings({"rawtypes", "unchecked"})
public final class CommonUtil {

  CommonUtil() {
    super();
  }

  private static Logger logger = Logger.getLogger(CommonUtil.class);

  public static boolean isNullAndEmpty(String data) {
    return (data == null || "".equals(data.trim()));
  }

  public static String generateRandomNumber(int length) {
    StringBuilder sb = new StringBuilder();
    Random random = new Random();
    int n;
    for (n = 0; n < length; n++) {
      int j = (random.nextInt() % Constants.MAX_ENTITY_DISPLAY_SIZE);
      // If First digit is "0", skip that and get next random
      if (n == 0 && j == 0) {
        n--;
        continue;
      }
      sb.append(Integer.toString(j));
    }
    return sb.toString();
  }

  public static boolean isListNotNullAndEmpty(List list) {
    return (list != null && !list.isEmpty());
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
    } catch (NumberFormatException exp) {
      logger.error("ERROR :: CommonUtil ::toAmount ", exp);
    }
    return amount;
  }

  public static Response getSuccessResponse() {
    Response response = new Response();
    response.setErrorCode(StatusConstants.STATUS_CODE_SUCCESS);
    response.setErrorMessage(StatusConstants.STATUS_MESSAGE_SUCCESS);
    return response;
  }

  /**
   * This method generate the random numeric value based on length
   * 
   * @param length
   * @return String
   */
  public static String generateRandNumeric(int length) {
    String finalRandString = "";
    Random randomObj = new Random();
    for (int j = 0; j < length; j++) {
      int rand_int = randomObj.nextInt(Constants.SEVENTYTWO);
      finalRandString += Integer.toString(rand_int);
      if (finalRandString.length() >= length) {
        finalRandString = finalRandString.substring(0, length);
        break;
      }
    }
    return finalRandString;
  }

  public static Response getErrorResponse() {
    Response response = new Response();
    response.setErrorCode(StatusConstants.STATUS_CODE_FAILED);
    response.setErrorMessage(StatusConstants.STATUS_MESSAGE_FAILURE);
    return response;
  }

  public static String getUniqueId() {
    return String.valueOf(System.nanoTime());
  }

  public static Date getCurrentDate() {
    return new Date(System.currentTimeMillis());
  }

  public static Long generateNumericString(int length) {
    StringBuilder sb = new StringBuilder();
    Random random = new Random();
    for (int n = 0; n < length; n++) {
      int j = (random.nextInt() % Constants.MAX_ENTITY_DISPLAY_SIZE);
      sb.append((char) (j + Constants.FORTYEIGHT));
    }
    return Long.parseLong(sb.toString());
  }

  /**
   * This method used to generate the alpha numeric code based on length.
   * 
   * @param length
   * @return String
   */
  public static String generateAlphaNumericString(int length) {
    String charString = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    Random rnd = new Random();
    StringBuilder sb = new StringBuilder(length);
    for (int i = 0; i < length; i++) {
      sb.append(charString.charAt(rnd.nextInt(charString.length())));
    }
    return sb.toString();
  }

  public static Date getDateFromMMDD(String mmddDate) {
    Calendar cal = Calendar.getInstance();
    cal.set(Calendar.MONTH, Integer.parseInt(mmddDate.substring(0, Constants.TWO)) - 1);
    cal.set(Calendar.YEAR, Integer.parseInt("20" + mmddDate.substring(Constants.TWO)));
    cal.set(Calendar.DATE, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
    return new Date(cal.getTimeInMillis());
  }


  /**
   * Method to copy the the list of beans properties from source to destination class bean
   * @param sourceList
   * @param D
   * @return
   * @throws InstantiationException
   * @throws IllegalAccessException
   */
  public static <Source, Destination> List<Destination> copyListBeanProperty(
      Iterable<Source> sourceList, Class Destiniation) {
    List<Destination> list = new ArrayList<Destination>();
    try {
      for (Source source : sourceList) {
        list.add((Destination) copyBeanProperties(source, Destiniation));
      }
    } catch (ReflectiveOperationException ie) {
      logger.error("Error :: CommonUtil :: copyListBeanProperty", ie);
    }
    return list;
  }

  /**
   * Method to copy the the set of beans properties from source to destination class bean
   * @param sourceList
   * @param D
   * @return
   * @throws ReflectiveOperationException 
   */
  public static <Source, Destination> Set<Destination> copySetBeanProperty(
      Iterable<Source> sourceList, Class Destiniation) throws ReflectiveOperationException {
    Set<Destination> list = new LinkedHashSet<Destination>();
    for (Source source : sourceList) {
      list.add((Destination) copyBeanProperties(source, Destiniation));
    }
    return list;
  }



  /**
   * Method to copy the the bean properties from source to destination class bean
   * @param source
   * @param Destination
   * @return
   * @throws InstantiationException
   * @throws IllegalAccessException
   */

  public static <Source, Destination> Destination copyBeanProperties(Source source,
      Class Destination) throws ReflectiveOperationException {
    Destination destination = (Destination) Destination.newInstance();
    try {
      BeanUtils.copyProperties(source, destination);
    } catch (BeansException e) {
      logger.error("Error :: CommonUtil :: copyBeanProperties", e);
    }
    return destination;
  }

  /**
   * This method is used to copy the not null properties from source object to destination object.
   * 
   * @param source
   * @param destination
   * @throws Exception
   */
  public static <Source, Destination> void copyProperties(Source source, Destination destination) {
    for (Method method : source.getClass().getDeclaredMethods()) {
      try {
        String methodName = method.getName();
        verifyMethodName(source, destination, method, methodName);
      } catch (Exception e) {
        logger.error("ERROR :: CommonUtil ::copyProperties ", e);
      }
    }
  }

  private static <Destination, Source> void verifyMethodName(Source source, Destination destination,
      Method method, String methodName)
          throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
    if (methodName.startsWith("get")) {
      methodName = methodName.replaceFirst("get", "set");
      Object value = method.invoke(source, null);
      if (value != null) {
        Method method2 = destination.getClass().getMethod(methodName, value.getClass());
        method2.invoke(destination, value);
      }
    }
  }

  public static BigDecimal stringToBigDecimal(String stringValue) {
    BigDecimal bigDecimalValue = new BigDecimal(stringValue.replaceAll(",", ""));
    return bigDecimalValue;
  }

  public static String encodeToString(byte[] image, String type) {
    String imageString = null;
    String encodedImage = null;

    try {
      encodedImage = org.apache.commons.codec.binary.Base64.encodeBase64String(image);
      imageString = "data:image/" + type + ";base64," + encodedImage;
    } catch (Exception e) {
      logger.error("ERROR :: CommonUtil ::copyProperties ", e);
    }
    return imageString;
  }

  public static Long stringToLong(String stringValue) {
    Long longValue = Long.valueOf(stringValue.replaceAll(",", ""));
    return longValue;
  }

  public static Response getFailureResponse(String errorCode, String errorMessage) {
    Response response = new Response();
    response.setErrorCode(errorCode);
    response.setErrorMessage(errorMessage);
    return response;
  }

  public static Double getDoubleAmount(Long amount) {
    return (amount == null ? null : getDoubleValue(amount));
  }

  private static double getDoubleValue(Long amount) {
    return amount == 0 ? 0d : (double) (amount.doubleValue() / Integer.parseInt("100"));
  }

  public static Response getResponse(Response response, String errorCode, String errorMessage) {
    response.setErrorCode(errorCode);
    response.setErrorMessage(errorMessage);
    return response;
  }

  public static Long getLongAmount(Double amount) {
    if (amount == null) {
      return null;
    } else {
      BigDecimal usd = new BigDecimal(amount);
      BigDecimal rounded = usd.setScale(Integer.parseInt("2"), BigDecimal.ROUND_HALF_UP);
      BigDecimal bigDecimalInCents = rounded.multiply(new BigDecimal(Integer.parseInt("100")));
      return bigDecimalInCents.longValueExact();
    }
  }

  public static boolean isSetNotNullNEmpty(Set set) {
    return (set != null && !set.isEmpty());
  }

  public static boolean isSetNullNEmpty(Set set) {
    return (set == null || set.isEmpty());
  }
  
  public static void setEntityIdsFromUserType(ProgramManagerRequest programManagerRequest, IsoRequest isoRequest, HttpSession session) {
	  LoginResponse loginResponse = (LoginResponse) session.getAttribute(Constants.LOGIN_RESPONSE_DATA);
	  if(loginResponse.getUserType().equalsIgnoreCase(Constants.PM_USER_TYPE)) {
    		programManagerRequest.setProgramManagerId(loginResponse.getEntityId());
		}else if(loginResponse.getUserType().equalsIgnoreCase(Constants.ISO_USER_TYPE)) {
			isoRequest.setId(loginResponse.getEntityId());
		}
  }

  public static Double doubleAmountConversion(String amount) {
		return (amount == null ? null : getDoubleValidation(amount));
  }

  private static double getDoubleValidation(String amount) {
		return amount == null ? 0d : (double) (Double.valueOf(amount) / Integer.parseInt("100"));
  }

}
