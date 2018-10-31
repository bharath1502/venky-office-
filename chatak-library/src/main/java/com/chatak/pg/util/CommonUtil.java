package com.chatak.pg.util;

import static com.chatak.pg.util.Constants.HUNDRED;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.DateFormatSymbols;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.apache.log4j.Logger;

import org.springframework.beans.BeanUtils;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@SuppressWarnings("rawtypes")
public final class CommonUtil {
  
  private static Logger logger = Logger.getLogger(CommonUtil.class);
  
  private static final String CARD_EXP_DATE = "expDate";
  private static final String CARD_NUMBER = "cardNumber";
  private static final String TRACK2 = "track2";

  public static boolean isNullAndEmpty(String data) {
    return (data == null || "".equals(data.trim()));
  }

  public static boolean isListNotNullAndEmpty(List list) {
    return (list != null && !list.isEmpty());
  }

  public static String generateRandomNumber(int length) {
    StringBuilder sb = new StringBuilder();
    Random random = new Random();
    int n;
    for(n = 0; n < length; n++) {
      int j = random.nextInt() % Integer.parseInt("10");
      // If First digit is "0", skip that and get next random
      if(n == 0 && j == 0) {
        n--;
        continue;
      }
      sb.append(Integer.toString(j));
    }
    return sb.toString();
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
    for(int j = 0; j < length; j++) {
      int randomInt = randomObj.nextInt(Integer.parseInt("72"));
      finalRandString += Integer.toString(randomInt);
      if(finalRandString.length() >= length) {
        finalRandString = finalRandString.substring(0, length);
        break;
      }
    }
    return finalRandString;
  }
  
  public static String toAmount(Object object) {
    String amount = "0.00";
    try {
      Double doubleValue = null;
      if(null != object) {
        doubleValue = Double.valueOf(object.toString());
        if(null != doubleValue) {
          amount = String.format("%.2f", doubleValue);
        }
      }
    }
    catch(NumberFormatException e) {
      logger.error("ERROR:: CommonUtil::toAmount ", e);
    }
    return amount;
  }

  /**
   * Method to copy the the list of beans properties from source to destination
   * class bean
   * 
   * @param sourceList
   * @param D
   * @return
   * @throws InstantiationException
   * @throws IllegalAccessException
   */
  @SuppressWarnings("unchecked")
  public static <Source, Destination> List<Destination> copyListBeanProperty(Iterable<Source> sourceList,
                                                                             Class destinationClass) throws InstantiationException,
                                                                                                 IllegalAccessException {
    List<Destination> list = new ArrayList<Destination>();
    for(Source source : sourceList) {
      list.add((Destination) copyBeanProperties(source, destinationClass));
    }
    return list;
  }

  /**
   * Method to copy the the bean properties from source to destination class
   * bean
   * 
   * @param source
   * @param classDestination
   * @return
   * @throws InstantiationException
   * @throws IllegalAccessException
   */
  @SuppressWarnings("unchecked")
  public static <Source, Destination> Destination copyBeanProperties(Source source,
                                                                     Class destinationClass) throws InstantiationException,
                                                                                        IllegalAccessException {
    Destination destination = (Destination) destinationClass.newInstance();
    BeanUtils.copyProperties(source, destination);
    return destination;
  }

  /**
   * This method is used to copy the not null properties from source object to
   * destination object.
   * 
   * @param source
   * @param destination
   * @throws Exception
   */
  public static <Source, Destination> void copyProperties(Source source, Destination destination) {
    for(Method method : source.getClass().getDeclaredMethods()) {
      try {
        String methodName = method.getName();
        if(methodName.startsWith("get")) {
          methodName = methodName.replaceFirst("get", "set");
          Object value = method.invoke(source, null);
          getClassAndMethod(destination, methodName, value);
        }
      }
      catch(Exception e) {
        logger.error("ERROR:: CommonUtil:: copyProperties method", e);
      }
    }
  }

  private static <Destination> void getClassAndMethod(Destination destination,
                                        String methodName,
                                        Object value) throws NoSuchMethodException,
                                                      IllegalAccessException,
                                                      InvocationTargetException {
    if(value != null) {
      Method method2 = destination.getClass().getMethod(methodName, value.getClass());
      method2.invoke(destination, value);
    }
  }

  public static Long getLongAmount(Double amount) {
    return (amount == null ? null : ((long) (amount * HUNDRED)));
  }

  public static Long getLongAmountNotNull(Double amount) {
    return (amount == null ? 0L : ((long) (amount * HUNDRED)));
  }

  public static Double getDoubleAmount(Long amount) {
    return (amount == null ? null : ((double) (amount / HUNDRED)));
  }

  public static Double getDoubleAmountNotNull(Long amount) {
    return (amount == null ? 0.00 : ((double) (amount / HUNDRED)));
  }

  public static Double getDoubleAmountNotNull(Double amount) {
    return (amount == null ? 0.00 : ((double) (amount / HUNDRED)));
  }

  public static Long getLongAmountFromDoubleString(String amount) {
    return ((amount == null || "" == amount) ? 0l : ((long) (Double.valueOf(amount) * HUNDRED)));
  }

  public static void main(String[] args) {
    Double amount = Double.parseDouble("1.10");

    logger.info("Amount:" + amount + " " + ((long) Math.ceil((amount * HUNDRED))));
  }

  public static List<Long> convertStringListToLongList(List<String> list) {
    List<Long> longList = new ArrayList<Long>();

    for(String str : list) {
      longList.add(Long.valueOf(str));
    }

    return longList;
  }

  public static String[] getMonthForInt(int createdmonth, int currentmonth, int difference) {
    String[] month = new String[Integer.parseInt("12")];
    DateFormatSymbols dfs = new DateFormatSymbols();
    String[] monthname = dfs.getMonths();

    if(difference == 0) {
      for(int j = createdmonth; j <= currentmonth; j++) {
        month[j] = monthname[j];
      }
    }
    else {
      for(int j = 0; j <= currentmonth; j++) {
        month[j] = monthname[j];
      }
    }
    for(int i = 0; i < month.length; i++) {
      if((month[i]) == null) {
        month[i] = "";
      }
    }
    return month;
  }

  public static String formatAmount(Object object) {
    String amount = "0.00";
    try {
      Double doubleValue = null;
      if(!StringUtils.isNull(object)) {
        doubleValue = Double.valueOf(object.toString());
        if(null != doubleValue) {
          DecimalFormat formatAmount = new DecimalFormat(Constants.AMOUNT_FORMAT);
          amount = formatAmount.format(doubleValue);
        }
      }
    }
    catch(Exception e) {
      logger.error("ERROR:: CommonUtil:: formatAmount method", e);
    }
    return amount;
  }

  public static Integer getIntForMonth(String currentmonth) {
    DateFormatSymbols dfs = new DateFormatSymbols();
    String[] monthname = dfs.getMonths();
    int i = 1;
    for(int count = 0; count <= Integer.parseInt("11"); count++) {
      if(currentmonth.equals(monthname[count])) {
        i = i + count;
      }
    }
    return i;
  }

  public static String getPGFormatAmount(String raw) {
    if(StringUtils.isEmpty(raw)) {
      return "0.00";
    }
    String number = raw.replace("$", "");
    return number.replaceAll(",", "");
  }

  public static String getCurrencyPattern(String amount,
                                          Integer currencySeparatorPosition,
                                          Character currencyThousandsUnit,
                                          Integer currencyExponent,
                                          Character currencyMinorUnit) {

    if(amount == null || "".equals(amount)) {
      return "";
    }
    String currencyFmt = "";
    String number = "";
    String pattern = "";
    BigInteger amt = null;
    BigDecimal bd = null;
    try {
      if(!"".equals(amount)) { 
        String decimalValues = "";
        if(amount.contains("."))
          decimalValues = amount.split("\\.")[1];

        bd = new BigDecimal(amount);
        amt = bd.toBigInteger();

        if(amt.longValue() > 0) {
          currencyFmt = validateCurrency(currencySeparatorPosition,
                                         currencyThousandsUnit,
                                         currencyExponent,
                                         currencyMinorUnit,
                                         pattern,
                                         amt,
                                         decimalValues);

        }
        else {
          return amount;
        }
      }

      if(amt != null && !"".equals(currencyFmt) && amt.longValue() > 0) {
        DecimalFormat decimalFormat = new DecimalFormat(currencyFmt);
        decimalFormat.setMinimumFractionDigits(currencyExponent);
        number = decimalFormat.format(new BigDecimal(amount));
      }
    }
    catch(Exception e) {
      logger.error("ERROR:: CommonUtil:: getCurrencyPattern method", e);
      return "";
    }

    return number;
  }

  private static String validateCurrency(Integer currencySeparatorPosition,
                                         Character currencyThousandsUnit,
                                         Integer currencyExponent,
                                         Character currencyMinorUnit,
                                         String pattern,
                                         BigInteger amt,
                                         String decimalValues) {
    String currencyFmt = "";
    StringBuilder sb = new StringBuilder();
    int replacePosition;
    int currencyLength = 0;

    currencyLength = String.valueOf(amt).length();

    if(currencyLength > currencySeparatorPosition) { 
      for(int i = 0; i < currencyLength; i++) {
        pattern += "#";
      }
      sb = sb.append(pattern.toString());

      while(currencyLength > currencySeparatorPosition) {
        replacePosition = currencyLength - currencySeparatorPosition;
        currencyFmt = sb.insert(replacePosition, currencyThousandsUnit).toString();
        currencyLength = replacePosition;
      }
      currencyLength = String.valueOf(amt).length();
    }

    if(currencyLength <= currencySeparatorPosition) {
      for(int i = 0; i < currencyLength; i++) {
        pattern += "#";
      }
      currencyFmt = pattern;
    }

    if(null == decimalValues || "".equals(decimalValues)) { 
      currencyFmt += currencyMinorUnit;
      for(int i = 0; i < currencyExponent; i++) {
        currencyFmt += "#";
      }
    }

    if(null != decimalValues && !"".equals(decimalValues)) {
      currencyFmt = checkForDecimalValues(currencyMinorUnit, decimalValues, currencyFmt);
    }
    return currencyFmt;
  }

  private static String checkForDecimalValues(Character currencyMinorUnit, String decimalValues, String currencyFmt) {
    currencyFmt += currencyMinorUnit;
    for(int i = 0; i < decimalValues.length(); i++) {
      currencyFmt += "#";
    }
    return currencyFmt;
  }

  public static String validateTerminalId(String terminalId) {
    if(terminalId.length() == Integer.parseInt("7")) {
      terminalId = "0" + terminalId;
    }
    return terminalId;
  }
  
  public static String getIIN(String cardNumber){
    if(StringUtils.isValidString(cardNumber))
      return cardNumber.substring(0, Integer.parseInt("5"));
    return "";
  }
  
  public static String getPartnerIINExt(String cardNumber) {
    if(StringUtils.isValidString(cardNumber)) {
      return cardNumber.substring(Integer.parseInt("5"), Integer.parseInt("8"));
    }
    return "";
  }
  
  public static String getIINExt(String cardNumber){
    if(StringUtils.isValidString(cardNumber))
      return cardNumber.substring(Integer.parseInt("8"), Integer.parseInt("11"));
    return "";
  }
  
  public static String maskJsonString(String json) {
      Map<String, Object> beforeMaskMap = new HashMap<String, Object>();
      ObjectMapper mapper = new ObjectMapper();
      String maskedJsonString = null;
      try {
        // convert JSON string to Map
        beforeMaskMap = mapper.readValue(json, new TypeReference<Map<String, Object>>(){});
        
        maskJsonStringValues(beforeMaskMap);
        
        //convert Map to JSON string
        maskedJsonString = mapper.writeValueAsString(beforeMaskMap);
      } catch (JsonParseException e) {
        logger.error("Error :: CommonUtil :: maskJsonString :: JsonParseException : ", e);
      } catch (JsonMappingException e) {
        logger.error("Error :: CommonUtil :: maskJsonString :: JsonMappingException : ", e);
      } catch (IOException e) {
        logger.error("Error :: CommonUtil :: maskJsonString :: IOException : ", e);
      } catch (Exception e) {
        logger.error("Error :: CommonUtil :: maskJsonString : ", e);
      }
      return maskedJsonString;
    }
  
  private static void maskJsonStringValues(Map<String, Object> beforeMaskMap) {
    if(beforeMaskMap.containsKey("cardNum")) {
      beforeMaskMap.put("cardNum", "***");
    }
    if(beforeMaskMap.containsKey("pin")) {
      beforeMaskMap.put("pin", "***");
    }
    if(beforeMaskMap.containsKey(CARD_EXP_DATE)) {
      beforeMaskMap.put(CARD_EXP_DATE, "***");
    }
    if(beforeMaskMap.containsKey(TRACK2)) {
      beforeMaskMap.put(TRACK2, "***");
    }
    if(beforeMaskMap.containsKey("cvv")) {
      beforeMaskMap.put("cvv", StringUtils.lastFourDigits((String)beforeMaskMap.get("pan")));
    }
    if(beforeMaskMap.containsKey("cardData")) {
      HashMap<String, Object> nestedMaskMap = (HashMap<String, Object>)beforeMaskMap.get("cardData");
      if(nestedMaskMap.containsKey(CARD_EXP_DATE)) {
        nestedMaskMap.put(CARD_EXP_DATE, "***");
      }
      if(nestedMaskMap.containsKey(CARD_NUMBER)) {
        nestedMaskMap.put(CARD_NUMBER, StringUtils.lastFourDigits((String)beforeMaskMap.get(CARD_NUMBER)));
      }
      if(nestedMaskMap.containsKey("cvv")) {
        nestedMaskMap.put("cvv", "***");
      }
      if(nestedMaskMap.containsKey(TRACK2)) {
        nestedMaskMap.put(TRACK2, "***");
      }
    }
  }
  
}
