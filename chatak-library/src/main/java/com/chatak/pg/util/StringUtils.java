package com.chatak.pg.util;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

import com.chatak.pg.constants.CurrencyAlpha;
import com.chatak.pg.constants.PGConstants;

/**
 * This class has helps for string utilization.
 */
@SuppressWarnings({ "rawtypes" })
public class StringUtils {
  
  private static Logger logger = Logger.getLogger(StringUtils.class);

  private static final String ZERO25 = "0000000000000000000000000";

  private static final String SPACE25 = "                         ";

  /**
   * This method used for encrypted the message.
   * 
   * @param message
   * @return String - encrypted
   * @throws NoSuchAlgorithmException
   */
  public static String md5(String message) throws NoSuchAlgorithmException {
    MessageDigest md5 = null;
    try {
      md5 = MessageDigest.getInstance("MD5");
    }
    catch(java.security.NoSuchAlgorithmException ex) {
      Logger.getLogger(StringUtils.class).error("Error :: StringUtils :: md5", ex);
      throw ex;
    }
    byte[] dig = md5.digest((byte[]) message.getBytes());
    StringBuilder code = new StringBuilder();
    for(int i = 0; i < dig.length; ++i) {
      code.append(Integer.toHexString(0x0100 + (dig[i] & 0x00FF)).substring(1));
    }
    return code.toString();
  }

  /**
   * Print the stack trace into a string
   * 
   * @param t
   * @return String
   */
  public static String getStackTrace(Throwable t) {
    StringWriter sw = new StringWriter();
    PrintWriter pw = new PrintWriter(sw, true);
    t.printStackTrace(pw);
    pw.flush();
    sw.flush();
    return sw.toString();
  }

  /**
   * This method used for remove the NonAlpabetical with Upper case
   * 
   * @param input
   * @return String
   */
  public static String removeNonAlpabeticalAndToUppercase(String input) {
    input = input.toUpperCase();
    StringBuilder sb = new StringBuilder();
    for(int i = 0; i < input.length(); i++) {
      char ch = input.charAt(i);
      if(ch > Integer.parseInt("64") && ch < Integer.parseInt("91")) {
        sb.append(ch);
      }
    }
    return sb.toString();
  }

  /**
   * Generate numerical string of given length
   * <p/>
   * 
   * @param length
   * @return String
   * @throws NoSuchAlgorithmException 
   */
  public static String generateNumericString(int length){
    StringBuilder sb = new StringBuilder();
    SecureRandom random = new SecureRandom();
    for(int n = 0; n < length; n++) {
      int j = random.nextInt() % Integer.parseInt("10");
      sb.append((char) (j + Integer.parseInt("48")));
    }
    return sb.toString();
  }

  /**
   * Generate a random alphabetic password of given length
   * 
   * @param length
   * @return String
   * @throws NoSuchAlgorithmException 
   */
  public static String generateAlphabeticString(int length){
    StringBuilder sb = new StringBuilder();
    SecureRandom random = new SecureRandom();
    for(int n = 0; n < length; n++) {
      int j = random.nextInt() % Integer.parseInt("26");
      sb.append((char) (j + Integer.parseInt("65")));
    }
    return sb.toString();
  }

  /**
   * This method used for convert object value in to double format.
   * 
   * @param value
   * @return Double
   */
  public static Double roundDouble(Object value) {
    if(value == null) {
      value = 0;
    }
    String strValue = "" + ((BigDecimal) value).setScale(Integer.parseInt("2"), BigDecimal.ROUND_HALF_UP);
    return Double.parseDouble(strValue);
  }

  /**
   * This method used for convert object value in to double format.
   * 
   * @param value
   * @return Double
   */
  public static Double roundDouble(Double value) {
    if(value == null) {
      value = 0D;
    }
    BigDecimal bd = new BigDecimal(value);
    bd = bd.setScale(Integer.parseInt("2"), BigDecimal.ROUND_HALF_UP);
    value = bd.doubleValue();
    return value;
  }

  /**
   * This method used for convert List Objects To String.
   * 
   * @param inputList
   * @return String
   */
  public static String convertListObjToString(List inputList) {
    String ouputStr = "";
    if(null != inputList && !inputList.isEmpty()) {
      ouputStr = Arrays.toString(inputList.toArray());
      ouputStr = ouputStr.substring(1, ouputStr.length() - 1);
    }
    return ouputStr;
  }

  /**
   * This Method used for getting fixed length string with left zero padded.
   * 
   * @param String
   * @param length
   *          of string.
   * @return String of fixed length.
   */
  public static String getFixedLengthZEROLeftPad(String inputString, int len) {
    String sFinal = null;
    if(inputString == null) {
      sFinal = ZERO25.substring(0, len);
    }
    else if(inputString.length() < len) {
      sFinal = ZERO25.substring(0, len - inputString.length()) + inputString;
    }
    else if(inputString.length() > len) {
      sFinal = inputString.substring(0, len);
    }
    else {
      sFinal = inputString;
    }
    return sFinal;
  }

  /**
   * This Method used for getting fixed length string with left zero padded.
   * 
   * @param String
   * @param length
   *          of string.
   * @return String of fixed length.
   */
  public static String getFixedLengthSPACELeftPad(String inputString, int len) {
    String sFinal = null;
    if(inputString.length() < len) {
      sFinal = SPACE25.substring(0, len - inputString.length()) + inputString;
    }
    else if(inputString.length() > len) {
      sFinal = inputString.substring(0, len);
    }
    else {
      sFinal = inputString;
    }
    return sFinal;
  }

  /**
   * This Method used for getting fixed length string with left zero padded.
   * 
   * @param String
   * @param length
   *          of string.
   * @return String of fixed length.
   */
  public static String getFixedLengthSPACERightPad(String inputString, int len) {
    String sFinal = null;
    if(inputString.length() < len) {
      sFinal = inputString + SPACE25.substring(0, len - inputString.length());
    }
    else if(inputString.length() > len) {
      sFinal = inputString.substring(0, len);
    }
    else {
      sFinal = inputString;
    }
    return sFinal;
  }

  /**
   * Method to get Value with 2 decimal only
   * 
   * @param rawVal
   * @return
   */
  public static Double getValueWithTwoDecimalOnly(Double rawVal) {
    if(null == rawVal)
      return 0D;
    String rawString = rawVal.toString();
    if(rawString.indexOf('.') != -1) {
      String[] rawStringAr = rawString.split("\\.");
      if(rawStringAr[1].length() > Integer.parseInt("2"))
        rawString = rawString.substring(0, rawString.indexOf('.') + Integer.parseInt("3"));
      return Double.valueOf(rawString);
    }
    return rawVal;
  }

  /**
   * This method used for mask the value.
   * 
   * @param input
   * @param showLength
   * @return String
   */
  public static String getMaskedString(String input, int showLength) {
    if(null == input)
      return null;
    StringBuilder sb = new StringBuilder();
    int maskLength = input.length() - showLength;
    maskLength = (maskLength < 0) ? input.length() : maskLength;
    for(int i = 0; i < maskLength; i++) {
      sb.append("X");
    }
    sb.append(input.substring(maskLength));
    return sb.toString();
  }

  /**
   * This method used for mask the value.
   * 
   * @param input
   * @param firstDigitsShow
   * @param lastDigitsShow
   * @return
   */
  public static String getMaskedString(String input, int firstDigitsShow, int lastDigitsShow) {
    if(null == input)
      return null;
    if(lastDigitsShow > input.length() - firstDigitsShow) {
      return input;
    }
    else {
      StringBuilder sb = new StringBuilder();
      sb.append(input.substring(0, firstDigitsShow));
      int maskLength = input.length() - lastDigitsShow;
      maskLength = (maskLength <= 0) ? input.length() - firstDigitsShow : maskLength;
      for(int i = firstDigitsShow; i < maskLength; i++) {
        sb.append("X");
      }
      sb.append(input.substring(input.length() - lastDigitsShow));
      return sb.toString();
    }
  }

  /**
   * This method returns the formatted string for the given double amount
   * 
   * @param amount
   * @return String
   */
  public static String formatAmount(double amount) {
    DecimalFormat formatter = new DecimalFormat("###,##0.00");
    return formatter.format(amount);
  }

  /**
   * This method used for formating the amount in string format
   * 
   * @param amount
   * @return String
   */
  public static String formatDoubleAmount(Double amount) {
    DecimalFormat formatter = new DecimalFormat("#0.00");
    if(amount == null) {
      amount = 0D;
    }
    return formatter.format(amount);
  }

  /**
   * This method used for formating the amount in string format
   * 
   * @param amount
   * @return String
   */
  public static String formatIntegerCount(Double amount) {
    DecimalFormat formatter = new DecimalFormat("#0");
    if(amount == null) {
      amount = 0D;
    }
    return formatter.format(amount);
  }

  /**
   * This method used for convert object to integer.
   * 
   * @param object
   * @return integer
   */
  public static int convertToInt(Object object) {
    int outInt = 0;
    if(object != null && !"".equals(object.toString())) {
      outInt = Integer.parseInt(object.toString());
    }
    return outInt;
  }

  /**
   * This method used for convert object to string.
   * 
   * @param object
   * @return long
   */
  public static long convertToLong(Object object) {
    long outLong = 0L;
    if(object != null && !"".equals(object.toString())) {
      outLong = Long.parseLong(object.toString());
    }
    return outLong;
  }

  /**
   * This method is used to get the Decimal format value.
   * 
   * @return double
   */
  public static double convertToDouble(Object object) {
    double outDouble = 0D;
    if(object != null && !"".equals(object.toString())) {
      outDouble = Double.parseDouble(object.toString());
    }
    return outDouble;
  }

  /**
   * This method used for convert object to string.
   * 
   * @param object
   * @return String
   */
  public static String convertToString(Object object) {
    String outString = "";
    if(object != null && !"".equals(object.toString())) {
      outString = object.toString().trim();
    }
    return outString;
  }

  /**
   * This method will format the number to digits specified by digitsNo
   * 
   * @param inputNumber
   * @param digitsNo
   * @return String
   */
  public static String formatNumber(int inputNumber, int digitsNo) {
    NumberFormat myFormat = NumberFormat.getInstance();
    myFormat.setMinimumIntegerDigits(digitsNo);
    String outputNumber = myFormat.format(inputNumber);
    return outputNumber;
  }

  /**
   * Method to format given Timestamp with the given format
   * 
   * @param timestamp
   * @param format
   * @return
   */
  public static String getDateInFormat(Timestamp timestamp, String format) {
    if(null == timestamp || null == format)
      return "";
    SimpleDateFormat sdf = new SimpleDateFormat(format);
    return sdf.format(new Date(timestamp.getTime()));
  }

  /**
   * This method used for validate date format like YYYYMMDD
   * 
   * @param date
   * @return boolean
   */
  public static boolean validateDate(String date) {
    return date.matches("(\\d{4})(0?[1-9]|1[012])(0?[1-9]|[12][0-9]|3[01])");
  }

  /**
   * This method used valid phone number like 1234567890
   * 
   * @param phone
   * @return boolean
   */
  public static boolean validatePhone(String phone) {
    return phone.matches("\\d{10}$");
  }

  /**
   * This method used validate the Alphabets only based on max number of
   * characters
   * 
   * @param str
   * @param max
   * @return boolean
   */
  public static boolean validateAlpanumericWithoutSpace(String str, int max) {
    return str.matches("[a-zA-Z0-9]{" + Constants.ONE + "," + max + "}");
  }

  /**
   * This method used validate the Alphabets only based on max number of
   * characters
   * 
   * @param str
   * @param max
   * @return boolean
   */
  public static boolean validateAlpanumericOnly(String str, int max) {
    return str.matches("[a-zA-Z 0-9]{" + Constants.ONE + "," + max + "}");
  }

  /**
   * Method to get Amount from a String
   * 
   * @param input
   * @return
   * @throws IllegalAccessException 
   * @throws Exception
   */
  public static String getAmountFromString(String input) throws IllegalAccessException {
    
    return getNegativeAmountFromString(input);
  }

  /**
   * Method to get Negative Amount from a String
   * 
   * @param input
   * @return
   * @throws IllegalAccessException 
   * @throws Exception
   */
  public static String getNegativeAmountFromString(String input) throws IllegalAccessException {
    String amount = null;
    try {
      Matcher matcher = Pattern.compile("@@-[0-9]+").matcher(input);
      matcher.find();
      amount = input.substring(matcher.start(), matcher.end());
      if(amount == null)
        return null;
      if(input.indexOf(' ') != -1) {
        boolean isInValidAmount = true;
        String[] strArray = input.split(" ");
        isInValidAmount = amountValidation(amount, strArray);
        if(isInValidAmount) {
          throw new IllegalAccessException("Invalid String");
        }
      }
      Matcher newMatcher = Pattern.compile("-[0-9]+").matcher(amount);
      newMatcher.find();
      amount = amount.substring(newMatcher.start(), newMatcher.end());
      if(matcher.find()) {
        throw new IllegalAccessException("Invalid String");
      }
    }
    catch(Exception e) {
      logger.error("ERROR:: StringUtils:: getNegativeAmountFromString method", e);
      if(null != amount)
        throw new IllegalAccessException("Invalid String");
    }
    return amount;
  }

  private static boolean amountValidation(String amount, String[] strArray) {
    boolean isInValidAmount = true;
    for(String strPiece : strArray) {
      if(strPiece.equals(amount)) {
        isInValidAmount = false;
        break;
      }
    }
    return isInValidAmount;
  }

  /**
   * Method to get amount in XXX.00 format
   * 
   * @param price
   * @return
   */
  public static String getPrice(String price) {
    return String.format("%.2f", Float.parseFloat(price));
  }

  /**
   * String utility function to check null or empty string
   * 
   * @param str
   * @return true if String is not null or empty
   */
  public static boolean isValidString(String str) {
    return (str != null && !"".equalsIgnoreCase(str.trim()));
  }

  public static void main(String arg[]) throws Exception {
    String amount = "$12,345.00";
    String number = amount.replace("$", "");
    number = number.replaceAll(",", "");
    logger.info(number);
  }

  /**
   * Method to get amount format string from long - cents
   * 
   * @param cents
   * @return
   */
  public static String amountToString(Long cents) {
    String amount = "0.00";
    if(null != cents) {
      amount = String.format("%.2f", (cents / Double.parseDouble("100.00d")));
    }
    return amount;
  }

  @SuppressWarnings("unchecked")
  public static <T extends Number> T getValidValue(T value) {
    if(null == value) {
      return (T) new Integer("0");
    }
    return value;
  }

  /**
   * Method to check the string is empty or not
   * 
   * @param val
   * @return
   */
  public static boolean isEmpty(String val) {
    return (null == val || "".equals(val.trim()));
  }

  public static boolean isListNotNullNEmpty(List list) {
    return (list != null && !list.isEmpty());
  }

  public static boolean isListNullNEmpty(List list) {
    return (list == null || list.isEmpty());
  }

  /**
   * Method to get Tokens
   * 
   * @param length
   * @return
   */
  public static String[] getTokens(int length) {
    String uuIdStr = UUID.randomUUID().toString().replaceAll("\\-", "M").toUpperCase();
    return new String[] { new RandomValueStringGenerator(length).generate().toLowerCase(), uuIdStr };
  }

  @SuppressWarnings("unchecked")
  public static <T extends Number> T getValidLongValue(T value) {
    if(null == value) {
      return (T) new Long("0");
    }
    return value;
  }

  public static String hexToAscii(String hex) {
    StringBuilder output = new StringBuilder();
    try {
      for(int i = 0; i < hex.length(); i += Integer.parseInt("2")) {
        String str = hex.substring(i, i + Integer.parseInt("2"));
        output.append((char) Integer.parseInt(str, Integer.parseInt("16")));
      }
      return new String(output);
    }
    catch(Exception e) {
      logger.error("ERROR:: StringUtils:: hexToAscii method", e);
      return null;
    }
  }

  public static boolean isNull(Object input) {
    return (input == null);
  }

  public static List<CurrencyAlpha> currency() {
    return Arrays.asList(CurrencyAlpha.CAD, CurrencyAlpha.COP, CurrencyAlpha.USD);
  }

  public static String lastFourDigits(String cardNumber) {
    if(StringUtils.isNull(cardNumber)) {
      return "";
    }
    return cardNumber.substring(cardNumber.length() - Integer.parseInt("4"), cardNumber.length());
  }

  public static String getAmount(String amount) {
    String symbol = "";
    //C means -ve balance and D means +ve
    if(amount.charAt(Constants.SEVEN) != 'C')
      symbol = "-";
    amount = amount.substring(amount.length()-Constants.TWELVE, amount.length());
    Float f = (Float.valueOf(amount))/Integer.parseInt("100");
    return (symbol + String.valueOf(f));
  }

  public static boolean isNullAndEmpty(Object input) {
    return (input == null || input.equals(""));
  }

  public static boolean isNotNullAndEmpty(Object input) {
    return (input != null && !input.equals(""));
  }

  public static String getStringStatusLiteral(String status) {
    if(status.equals(PGConstants.STATUS_ACTIVE.toString())) {              // 0
      return "Active";
    } else if(status.equals(PGConstants.STATUS_PENDING.toString())) {      //1
      return "Pending";
    } else if(status.equals(PGConstants.STATUS_INACTIVE.toString())) {     //2
      return "Suspended";
    } else if(status.equals(PGConstants.STATUS_DELETED.toString())) {      //3
      return "Deleted";
    } else if(status.equals(PGConstants.STATUS_HOLD.toString())) {         //4
      return "Declined";
    }

    return null;
  }

  public static String randomNumeric(int numberOfDigit) {
    SecureRandom rnd = new SecureRandom();
    StringBuilder ret = new StringBuilder(numberOfDigit);
    for(int i = 0; i < numberOfDigit; i++) {
      ret.append(rnd.nextInt(Integer.parseInt("10")));
    }
    return ret.toString();
  }
}