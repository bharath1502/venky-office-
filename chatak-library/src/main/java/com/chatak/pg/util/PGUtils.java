package com.chatak.pg.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.sql.Blob;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import java.util.TimeZone;

import org.apache.log4j.Logger;

public class PGUtils {
  
  private static Logger logger = Logger.getLogger(PGUtils.class);
  
  private static final BigDecimal x100 = new BigDecimal(100);

  /**
   * Generate 4 digit pin
   * 
   * @param length
   * @return String
   * @throws NoSuchAlgorithmException 
   */
  public static String generatePin(int length) {
    StringBuilder sb = new StringBuilder();
    SecureRandom random = new SecureRandom();
    for(int n = 0; n < length; n++) {
      int j = random.nextInt() % Integer.parseInt("10");
      sb.append(Integer.toString(j));
    }
    return sb.toString();
  }

  /**
   * This method used for convert the phone number in track2 data format.
   * 
   * @param phone
   * @return String - Phone number as track2 data format
   */
  public static String formatPhone(String phone) {
    if(phone != null && phone.length() > Integer.parseInt("6")) {
      return phone.substring(0, Integer.parseInt("3")) + "-" + phone.substring(Integer.parseInt("3"), Integer.parseInt("6")) + "-" + phone.substring(Integer.parseInt("6"), phone.length());
    }
    else {
      return phone;
    }
  }

  /**
   * This method used for convert the phone number in track2 data format.
   * 
   * @param phone
   * @return String - Phone number as track2 data format
   */
  public static String formatXMLPhone(String phone) {
    if(phone != null && phone.length() > Integer.parseInt("10")) {
      return phone.substring(0, Integer.parseInt("3")) + phone.substring(Integer.parseInt("4"), Integer.parseInt("7")) + phone.substring(Integer.parseInt("8"), phone.length());
    }
    else {
      return phone;
    }
  }

  /**
   * This method used for get last four digit number for mask the credit card
   * number.
   * 
   * @param cardNumber
   * @return String
   */
  public static String getLastFour(String cardNumber) {
    if(cardNumber == null || cardNumber.length() < Integer.parseInt("5")) {
      return cardNumber;
    }
    else {
      return cardNumber.substring(cardNumber.length() - Integer.parseInt("4"));
    }
  }

  /**
   * This method used for check the number as positive or not.
   * 
   * @param s
   * @return boolean
   */
  public static boolean isPositive(String s) {
    boolean result = false;
    try {
      if(s == null || "".equals(s.trim()) || Integer.parseInt(s) < 0) {
        // do nothing
      }
      else {
        result = true;
      }
    }
    catch(Exception e) {
      logger.error("ERROR:: PGUtils:: isPositive method", e);
    }
    return result;
  }

  /**
   * Method to merge values to Template
   * 
   * @param model
   * @param _return
   * @return
   */
  public static String mergeValueToTemplate(Map model, String _return) {
    Iterator it = model.keySet().iterator();
    while(it.hasNext()) {
      Object o = it.next();
      _return = _return.replaceAll(o.toString(), (String) model.get(o));
    }
    return _return;
  }

  /**
   * Method to get Formated Amount
   * 
   * @param amount
   * @return
   */
  public static String getFormatedAmount(Double amount) {
    if(null == amount)
      return "0.00";
    return String.format("%.2f", amount);
  }

  /**
   * Method to get Current Date and Time for given Time Zone ID
   * 
   * @param timeZoneID
   * @return
   * @throws ParseException
   */
  public static Timestamp getCurrentDateTimeOnTimezone(String timeZoneID) {
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    if(null != timeZoneID && !"".equals(timeZoneID)) {
      TimeZone zone = null;
      zone = TimeZone.getTimeZone(timeZoneID);
      sdf.setTimeZone(zone);
    }
    String s = sdf.format(new Date());
    return getTimestampForGivenDate(s, "yyyy-MM-dd HH:mm:ss");
  }

  /**
   * method to get java.sql.Timestamp to given date string and format of date
   * 
   * @param date
   * @param format
   * @return Timestamp
   */
  public static Timestamp getTimestampForGivenDate(String date, String format) {
    SimpleDateFormat formatter = new SimpleDateFormat(format);
    formatter.setLenient(false);
    Calendar cal = Calendar.getInstance();
    try {
      cal.setTime(formatter.parse(date));
      return (new Timestamp(cal.getTimeInMillis()));
    }
    catch(ParseException e) {
      logger.error("Error :: PGUtils :: getTimestampForGivenDate", e);
    }
    return null;
  }

  /**
   * Method to get String from BLOB object
   * 
   * @param blob
   * @return
   */
  public static String getStringValue(byte[] value) {
    if(null == value)
      return "";
    return new String(value);
  }

  /**
   * Method is used to validate given value is numbers only.
   * 
   * @param value
   * @return boolean true/false
   */
  public static boolean validateNumbersOnly(String value) {
    if(null == value)
      return false;
    return value.matches("[0-9]*");
  }

  /**
   * Method is used to validate CC exp. date should be MMYY format.
   * 
   * @param value
   * @return boolean true/false
   */
  public static boolean validateCCExpDate(String value) {
    if(null == value || value.trim().length() != Integer.parseInt("4"))
      return false;
    int month = Integer.parseInt(value.substring(0, Integer.parseInt("2")));
    if(month > Integer.parseInt("12"))
      return false;
    Calendar time = Calendar.getInstance();
    SimpleDateFormat formatter = new SimpleDateFormat("yyMM");
    if(Integer.parseInt(formatter.format(time.getTime())) > Integer.parseInt(value.substring(Integer.parseInt("2"), value.length())
                                                                             + value.substring(0, Integer.parseInt("2"))))
      return false;
    return true;
  }

  /**
   * Method to validate Date Range
   * 
   * @param stDate
   * @param endDate
   * @return
   * @throws ParseException
   */
  public static boolean validateDateRange(String stDate, String endDate) {
    try {
      if(null == stDate && null == endDate)
        return true;
      else {
        return dateValidation(stDate, endDate);
      }
    }
    catch(Exception e) {
      logger.error("ERROR:: PGUtils:: getTimestampYYYYMMDDStart method", e);
      return false;
    }
  }

  private static boolean dateValidation(String stDate, String endDate) {
    if((null != stDate && stDate.length() != Integer.parseInt("8")) || (null != endDate && endDate.length() != Integer.parseInt("8")))
      return false;
    Calendar time = Calendar.getInstance();
    SimpleDateFormat formatter = new SimpleDateFormat(Constants.YYYY_MM_DD);
    if(null != stDate && null != endDate) {
      return validateDate(stDate, endDate, time, formatter);
    }
    if(null != stDate  &&
        Integer.parseInt(formatter.format(time.getTime())) < Integer.parseInt(stDate))
        return false;
    if(null != endDate &&
        Integer.parseInt(formatter.format(time.getTime())) < Integer.parseInt(endDate))
        return false;
    return true;
  }

  private static boolean validateDate(String stDate, String endDate, Calendar time, SimpleDateFormat formatter) {
    boolean status = false;
    if(Integer.parseInt(stDate) <= Integer.parseInt(endDate)) {
      if(Integer.parseInt(formatter.format(time.getTime())) >= Integer.parseInt(endDate))
        status = true;
      else
        status = false;
    }
    return status;
  }

  /**
   * Method to get Start Timestamp for given date with time 00:00:00
   * 
   * @param date
   * @return
   */
  public static Timestamp getTimestampYYYYMMDDStart(String date) {
    try {
      SimpleDateFormat formatter = new SimpleDateFormat(Constants.YYYY_MM_DD);
      return new Timestamp(formatter.parse(date).getTime());
    }
    catch(Exception e) {
      logger.error("ERROR:: PGUtils:: validateDateRange method", e);
    }
    return null;
  }

  /**
   * Method to get Start Timestamp for given date with time 00:00:00
   * 
   * @param date
   * @return
   */
  public static Timestamp getTimestampYYYYMMDDEnd(String date) {
    try {
      SimpleDateFormat formatter = new SimpleDateFormat(Constants.YYYY_MM_DD);
      return new Timestamp(formatter.parse(date).getTime() + (Long.parseLong("86400000") - 1L));
    }
    catch(Exception e) {
      logger.error("ERROR:: PGUtils:: getTimestampYYYYMMDDEnd method", e);
    }
    return null;
  }

  /**
   * Method to get Start Timestamp for given date with time 00:00:00 on format
   * 
   * @param date
   * @param format
   * @return
   */
  public static Timestamp getTimestampStart(String date, String format) {
    try {
      SimpleDateFormat formatter = new SimpleDateFormat(format);
      return new Timestamp(formatter.parse(date).getTime());
    }
    catch(Exception e) {
      logger.error("ERROR:: PGUtils:: getTimestampStart method", e);
    }
    return null;
  }

  /**
   * Method to get string for given Timestamp
   * 
   * @param date
   * @param format
   * @return
   */
  public static String getStringForTimestamp(Timestamp date, String format) {
    try {
      SimpleDateFormat formatter = new SimpleDateFormat(format);
      return formatter.format(new Date(date.getTime()));
    }
    catch(Exception e) {
      logger.error("ERROR:: PGUtils:: getStringForTimestamp method", e);
    }
    return null;
  }

  /**
   * Method to get End of Day Timestamp for given date with time 23:59:59.999 on
   * format
   * 
   * @param date
   * @param format
   * @return
   */
  public static Timestamp getTimestampEnd(String date, String format) {
    try {
      SimpleDateFormat formatter = new SimpleDateFormat(format);
      return new Timestamp(formatter.parse(date).getTime() + (Long.parseLong("86400000") - 1L));
    }
    catch(Exception e) {
      logger.error("ERROR:: PGUtils:: getTimestampEnd method", e);
    }
    return null;
  }

  /**
   * Method to get Start Timestamp for current Date
   * 
   * @param date
   * @return
   */
  public static Timestamp getStartCurrentDayTimestamp() {
    try {
      SimpleDateFormat formatter = new SimpleDateFormat(Constants.YYYY_MM_DD);
      return new Timestamp(formatter.parse(formatter.format(new Date())).getTime());
    }
    catch(Exception e) {
      logger.error("ERROR:: PGUtils:: getStartCurrentDayTimestamp method", e);
    }
    return null;
  }

  /**
   * Method to get End Timestamp for current Date
   * 
   * @param date
   * @return
   */
  public static Timestamp getEndCurrentDayTimestamp() {
    try {
      SimpleDateFormat formatter = new SimpleDateFormat(Constants.YYYY_MM_DD);
      return new Timestamp(formatter.parse(formatter.format(new Date())).getTime() + (Long.parseLong("86400000") - 1L));
    }
    catch(Exception e) {
      logger.error("ERROR:: PGUtils:: getEndCurrentDayTimestamp method", e);
    }
    return null;
  }

  /**
   * Method to Convert from Blob to String
   * 
   * @param blob
   * @return
   */
  public static String toString(Blob blob) {
    if(null == blob)
      return "";
    return getStringValue(toByteArray(blob));
  }

  /**
   * Method to Convert Blob to byte[]
   * 
   * @param blob
   * @return
   */
  public static byte[] toByteArray(Blob blob) {
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    try {
      byte buf[] = new byte[Integer.parseInt("4000")];
      int dataSize;
      InputStream is = blob.getBinaryStream();
      try {
        while((dataSize = is.read(buf)) != -1) {
          baos.write(buf, 0, dataSize);
        }
      }
      finally {
          is.close();
        }
    }
    catch(SQLException e) {
      logger.error("ERROR:: PGUtils:: toByteArray method", e);
    }
    catch(IOException exp) {
      logger.error("ERROR:: PGUtils:: toByteArray method", exp);
    }
    return baos.toByteArray();
  }

  /**
   * Method to get Non-Zero String for Link Bank Account
   * 
   * @param max
   * @return
   */
  public static String getNonzeroStringLessThan(int max) {
    int length = Integer.toString(max).length();
    int result = 0;
    String result2 = "00";
    while(result < Integer.parseInt("10") || result > max) {
      result2 = EncryptionUtil.generatePin(length);
      result = Integer.parseInt(result2);
    }
    return result2;
  }

  /**
   * validates ABA routing number
   * <p/>
   * see http://answers.google.com/answers/threadview?id=43619
   * 
   * @param routingNumber
   * @param errors
   */
  public static boolean validateRouting(String routingNumber) {
    if(routingNumber == null || routingNumber.trim().length() != Integer.parseInt("9")) {
      return false;
    } else {
      try {
        int routNumber = Integer.parseInt(routingNumber);
        Logger.getLogger(PGUtils.class).info("Routing Number : " + routNumber + " is valid");
      }
      catch(NumberFormatException nfe) {
        return false;
      }
      char[] chars = routingNumber.toCharArray();
      int[] ints = new int[Integer.parseInt("9")];
      for(int i = 0; i < chars.length; i++) {
        ints[i] = chars[i] - Integer.parseInt("48");
      }
      int total = ints[0] * Integer.parseInt("3") + ints[1] * Integer.parseInt("7") + ints[Integer.parseInt("2")] + ints[Integer.parseInt("3")] * Integer.parseInt("3") + ints[Integer.parseInt("4")] * Integer.parseInt("7") + ints[Integer.parseInt("5")] + ints[Integer.parseInt("6")] * Integer.parseInt("3") + ints[Integer.parseInt("7")] * Integer.parseInt("7")
                  + ints[Integer.parseInt("8")];
      if(total % Integer.parseInt("10") != 0) {
        return false;
      }
    }
    return true;
  }

  /**
   * Method to check Day Of the Week eligibility
   * 
   * @param dow
   *          - Day Of the Week - in the form [1,2,3,4,5] 1-Monday
   * @return
   */
  public static boolean isDOW(String dow) {
    if(null == dow || "".equals(dow.trim()))
      return true;
    Calendar cal = Calendar.getInstance();
    int curDay = (cal.get(Calendar.DAY_OF_WEEK)) - 1;
    if(dow.indexOf(("" + Integer.toString(curDay))) != -1) {
      return true;
    }
    return false;
  }

  /**
   * Method to check Time of the Day eligibility
   * 
   * @param tod
   *          - Time of the Day - in the form [hh:mm,hh:mm] - 24Hrs, [From
   *          time,To time]
   * @param sdf
   * @return
   */
  public static boolean isTOD(String tod, SimpleDateFormat sdf) {
    try {
      if(null == tod || "".equals(tod.trim()))
        return true;
      String curHHMM = sdf.format(new Date());
      curHHMM = curHHMM.replace(":", "");
      if(tod.contains(":") && tod.contains(",")) {
        tod = tod.replaceAll(":", "");
        int fromTime = Integer.parseInt(tod.split(",")[0]);
        int toTime = Integer.parseInt(tod.split(",")[1]);
        if(fromTime <= Integer.parseInt(curHHMM) && toTime >= Integer.parseInt(curHHMM))
          return true;
      }
    }
    catch(Exception e) {
      logger.error("ERROR:: PGUtils:: isTOD method", e);
    }
    return false;
  }

  public static boolean isValidCardNumber(String ccNumber) {
    int sum = 0;
    boolean alternate = false;
    for(int i = ccNumber.length() - 1; i >= 0; i--) {
      int n = Integer.parseInt(ccNumber.substring(i, i + 1));
      if(alternate) {
        n *= Integer.parseInt("2");
        if(n > Integer.parseInt("9")) {
          n = (n % Integer.parseInt("10")) + 1;
        }
      }
      sum += n;
      alternate = !alternate;
    }
    return (sum % Integer.parseInt("10") == 0);
  }

  public static boolean isValidCardExpiryDate(String expiryDate) {
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMM");
    simpleDateFormat.setLenient(false);
    Date expiry = null;
    try {
      if(expiryDate != null && expiryDate.length() == Integer.parseInt("4")) {
        expiryDate = "20" + expiryDate; // Just added to check the Date was
                                        // getting 1946 for 46 yy
      }
      expiry = simpleDateFormat.parse(expiryDate);
      Calendar calendar = Calendar.getInstance();
      calendar.setTime(expiry);
      calendar.add(Calendar.MONTH, 1);
      calendar.set(Calendar.DAY_OF_MONTH, 1);
      calendar.add(Calendar.DATE, -1);
      expiry = calendar.getTime();
    }
    catch(ParseException e) {
      logger.error("ERROR:: PGUtils:: isValidCardExpiryDate method", e);
      return false;
    }
    return expiry.after(new Date());
  }

  public static void main(String[] args) {
    logger.info(isValidCardExpiryDate("4612"));
  }

  /**
   * @param hex
   * @return
   */
  public static String convertHexToString(String hex) {
    StringBuilder sb = new StringBuilder();
    StringBuilder temp = new StringBuilder();
    for(int i = 0; i < hex.length() - 1; i += Integer.parseInt("2")) {
      // grab the hex in pairs
      String output = hex.substring(i, (i + Integer.parseInt("2")));
      // convert hex to decimal
      int decimal = Integer.parseInt(output, Integer.parseInt("16"));
      // convert the decimal to character
      sb.append((char) decimal);
      temp.append(decimal);
    }
    return sb.toString();
  }

  public static String getCCType() {
    if(Constants.FLAG_TRUE.equals(Properties.getProperty("chatak-pay.skip.card.type.check", "false"))) {
      return "IP"; // Return type for Chatak
    }
    return "BLANK";
  }
  
  /**
   * Utility method to calculate amount by given percentage
   * @param amount as double value
   * @param percent as double value 
   * @return calculated amount based on % and amount
   * <p>
     * <b>Note:</b>
     * <li> The calculated amount will be multiplied of 100
     * 
   */
  public static Long calculateAmountByPercentage(Double amount, double percent){
    if(amount == null){
      return null;
    }else{
      double entityFeeShare = (amount * (percent / Integer.parseInt("100")));
      BigDecimal usd = BigDecimal.valueOf(entityFeeShare);
      BigDecimal rounded = usd.setScale(2, BigDecimal.ROUND_HALF_UP);
      BigDecimal bigDecimalInCents = rounded.multiply(x100);
      return bigDecimalInCents.longValueExact();
    }
  }
}