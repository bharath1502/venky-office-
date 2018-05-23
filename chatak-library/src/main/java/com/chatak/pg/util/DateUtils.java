package com.chatak.pg.util;

import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.log4j.Logger;

public class DateUtils {
  
  private static Logger logger = Logger.getLogger(DateUtils.class);

  private static final SimpleDateFormat mmddyyyyformatter = new SimpleDateFormat("MM/dd/yyyy");

  private static final int DECEMBER = 12;

  private static final int TWO_DIGITS = 2;

  /**
   * This method used for getting the start date time per today.
   * 
   * @return Timestamp
   */
  public static Timestamp getDailyFrequency(Date today) {
    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    Timestamp dateWithTime = new Timestamp(today.getTime());
    String strTodays = String.valueOf(dateWithTime);
    String year = strTodays.substring(0, Integer.parseInt("4"));
    String month = strTodays.substring(Integer.parseInt("5"), Integer.parseInt("7"));
    String date = strTodays.substring(Integer.parseInt("8"), Integer.parseInt("10"));
    Date convertedDateFormat = null;
    try {
      convertedDateFormat = formatter.parse(year + "-" + month + "-" + date + " " + "00:00:00");
    }
    catch(ParseException e) {
      logger.error("ERROR:: DateUtils:: getDailyFrequency method", e);
    }
    long dateLong = 0l;
    if(convertedDateFormat != null) {
      dateLong = convertedDateFormat.getTime();
    }
    return new Timestamp(dateLong);
  }


  /**
   * This method used for getting the start date time of this month based on
   * today.
   * 
   * @return Timestamp
   */
  public static Timestamp getMonthlyFrequency() {
    Calendar cal = Calendar.getInstance();
    cal.set(Calendar.DATE, 1);
    return getDailyFrequency(cal.getTime());
  }
  
  /**
   * This method used for getting the start date time of this week based on
   * today.
   * 
   * @return Timestamp
   */
  public static Timestamp getWeeklyFrequency() {
    Calendar cal = Calendar.getInstance();
    cal.set(Calendar.DAY_OF_WEEK, 1);
    return getDailyFrequency(cal.getTime());
  }
  
  /**
   * This method used for get CurrentDate In String
   * 
   * @param cal
   * @param formatter
   * @return String
   */
  public static String getCurrentDateInString(Calendar cal, SimpleDateFormat formatter) {
    String currentDate = "";
    Date date = new Date();
    cal.setTime(date);
    cal.set(Calendar.MINUTE, 0);
    cal.set(Calendar.HOUR_OF_DAY, 0);
    cal.set(Calendar.MILLISECOND, 0);
    cal.set(Calendar.SECOND, 0);
    date = cal.getTime();
    currentDate = formatter.format(date);
    return currentDate;
  }

  /**
   * This method used for get Date of Days Before Current Days In MM/dd/yyyy
   * format
   * 
   * @param days
   * @return String - String
   */
  public static String getDateOfDaysBeforeCurrentDateInMMddyyyy(int days) {
    String currentDate = "";
    Calendar cal = Calendar.getInstance();
    if(days > 0) {
      cal.add(Calendar.DATE, days * -1);
      currentDate = mmddyyyyformatter.format(cal.getTime());
    }
    else {
      currentDate = mmddyyyyformatter.format(cal.getTime());
    }
    return currentDate;
  }
  
  /**
   * This method used for get CurrentDate In MM/dd/yyyy format
   * 
   * @return String - String
   */
  public static String getCurrentDateInMMddyyyy() {
    String currentDate;
    Calendar cal = Calendar.getInstance();
    currentDate = mmddyyyyformatter.format(cal.getTime());
    return currentDate;
  }

  /**
   * This method used for get Date of Days Before Current Days In MM/dd/yyyy
   * format
   * 
   * @param days
   * @return String - String
   */
  public static Timestamp getDateOfDaysBeforeCurrentDateAndTime(int days) {
    Calendar cal = Calendar.getInstance();
    if(days > 0) {
      cal.add(Calendar.DATE, days * -1);
    }
    return (new Timestamp(cal.getTimeInMillis()));
  }

  /**
   * This method will validate the start Date (YYYYMMdd) and end Date
   * (YYYYMMdd), if present
   * 
   * @param startDate
   * @param endDate
   * @return boolean
   */
  public static boolean validateStEndDate(String startDate, String endDate) {
    boolean validFlag = true;
    if(startDate == null || startDate.equals("") || startDate.length() != Integer.parseInt("8")) {
      validFlag = false;
    }
    try {
      if(validFlag) {
        // If the date is today also then allow
        Timestamp currentDateTime = new Timestamp(System.currentTimeMillis());
        String dTime = new SimpleDateFormat("yyyyMMdd").format(currentDateTime);
        dTime = formatTranDate(dTime);
        String yyyy = dTime.substring(0, Integer.parseInt("4"));
        String mm = dTime.substring(Integer.parseInt("4"), Integer.parseInt("6"));
        String dd = dTime.substring(Integer.parseInt("6"));
        String dt = yyyy + "-" + mm + "-" + dd + " 00:00:00";
        currentDateTime = java.sql.Timestamp.valueOf(dt);

        // starting or ending year should not be 0
        int startingYear = StringUtils.convertToInt(startDate.substring(0, Integer.parseInt("4")));

        Timestamp currentDate = null;
        currentDate = getSqlDateFromDateStr(getDateStrFromSqlTimestatmp(currentDateTime));

        if(!StringUtils.validateDate(startDate)) {
          validFlag = false;
        }
        else if(startingYear == 0) {
          validFlag = false;
        }
        else if(!getSqlDateFromDateStr(startDate).before(currentDate)) {
          validFlag = false;
        }
        else if(endDate != null && !endDate.equals("")) {

          validFlag = validateFlag(startDate, endDate, currentDate);

        }
      }
    }
    catch(Exception exp) {
      validFlag = false;
      logger.error("ERROR:: DateUtils:: validateTranMonth method", exp);
    }

    return validFlag;
  }


  private static boolean validateFlag(String startDate, String endDate, Timestamp currentDate) {
    if(endDate.length() != Integer.parseInt("8")) {
      return false;
    }
    else if(!StringUtils.validateDate(endDate)) {
      return false;
    }
    else {
      // starting or ending year should not be 0
      int endingYear = StringUtils.convertToInt(endDate.substring(0, Integer.parseInt("4")));
      if(endingYear == 0) {
        return false;
      }
      else if(!getSqlDateFromDateStr(endDate).before(currentDate)) {
        return false;
      }
      else if(!getSqlDateFromDateStr(startDate).before(getSqlDateFromDateStr(formatTranDate(endDate)))) {
        return false;
      }
    }
    return true;
  }

  /**
   * This method will format specific date (YYYYMMdd) for transaction, it will
   * add one day and return the date in YYYYMMdd format.
   * 
   * @param tranDate
   * @return String
   */
  public static String formatTranDate(String inputDate) {
    String outputDate = "";

    try {
      SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
      Calendar calender = Calendar.getInstance();
      calender.setTime(sdf.parse(inputDate));
      calender.add(Calendar.DATE, 1); // Add one Day
      outputDate = sdf.format(calender.getTime());
    }
    catch(ParseException e) {
      logger.error("ERROR:: DateUtils:: formatTranDate method", e);
    }

    return outputDate;
  }

  /**
   * This method will format specific month (YYMM) for transaction
   * 
   * @param tranMonth
   * @return String
   */
  public static String formatTranMonth(String tranMonth) {
    String stMonth = "";
    String stYear = "";
    String endMonth = "";
    String endYear = "";
    if(tranMonth != null && tranMonth.length() == Integer.parseInt("4")) {
      stYear = tranMonth.substring(0, Integer.parseInt("2"));
      stMonth = tranMonth.substring(Integer.parseInt("2"));
      if(StringUtils.convertToInt(stMonth) == DECEMBER) {
        endMonth = "01";
        endYear = StringUtils.formatNumber((StringUtils.convertToInt(stYear) + 1), TWO_DIGITS);
      }
      else {
        endMonth = StringUtils.formatNumber((StringUtils.convertToInt(stMonth) + 1), TWO_DIGITS);
        endYear = stYear;
      }
    }
    return endYear + endMonth;
  }

  /**
   * This method will validate the month (YYMM) and return true if it is valid
   * 
   * @param tranMonth
   * @return boolean
   */
  public static boolean validateTranMonth(String tranMonth) {
    boolean validFlag = true;

    if(tranMonth == null || tranMonth.length() != Integer.parseInt("4")) {
      validFlag = false;
    }

    try {
      if(validFlag) {
        String yy = tranMonth.substring(0, Integer.parseInt("2"));
        String mm = tranMonth.substring(Integer.parseInt("2"));
        if(Integer.parseInt(yy) < 0 || Integer.parseInt(yy) > Integer.parseInt("99") || Integer.parseInt(mm) <= 0
            || Integer.parseInt(mm) > Integer.parseInt("12")) {
          validFlag = false;
        }
      }
    }
    catch(Exception exp) {
      validFlag = false;
      logger.error("ERROR:: DateUtils:: validateTranMonth method", exp);
    }

    return validFlag;
  }

  /**
   * This method used for convert String of Date(YYYYMMdd) to Sql_Timestamp.
   * 
   * @param date
   * @return TimeStamp
   */
  public static Timestamp getSqlDateFromDateStr(String dateMonYear) {
    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    String strTodays = String.valueOf(dateMonYear);
    String year = strTodays.substring(0, Integer.parseInt("4"));
    String month = strTodays.substring(Integer.parseInt("4"), Integer.parseInt("6"));
    String date = strTodays.substring(Integer.parseInt("6"));
    Date convertedDateFormat = null;
    try {
      convertedDateFormat = formatter.parse(year + "-" + month + "-" + date + " " + "00:00:00");
    }
    catch(ParseException e) {
      logger.error("ERROR:: DateUtils:: getSqlDateFromDateStr method", e);
    }
    long dateLong = 0l;
    if(convertedDateFormat != null) {
      dateLong = convertedDateFormat.getTime();
    }
    return new Timestamp(dateLong);
  }

  /**
   * This method used for convert Sql_Timestamp to Date String in YYYYMMdd
   * format.
   * 
   * @param dateWithTime
   *          - String
   * @return TimeStamp (YYYYMMdd)
   */
  public static String getDateStrFromSqlTimestatmp(Timestamp dateWithTime) {
    String strTodays = dateWithTime.toString();
    String year = strTodays.substring(0, Integer.parseInt("4"));
    String month = strTodays.substring(Integer.parseInt("5"), Integer.parseInt("7"));
    String date = strTodays.substring(Integer.parseInt("8"), Integer.parseInt("10"));
    return year + month + date;
  }

  /**
   * This method used for convert Sql_Timestamp to Date String in YYYYMMdd
   * format.
   * 
   * @param dateWithTime
   *          - String
   * @return TimeStamp (YYYYMMdd)
   */
  public static String getFormatDate(Timestamp dateWithTime) {
    String strTodays = dateWithTime.toString();
    return strTodays.substring(0, Integer.parseInt("4")) + strTodays.substring(Integer.parseInt("5"), Integer.parseInt("7")) + strTodays.substring(Integer.parseInt("8"), Integer.parseInt("10"))
        + strTodays.substring(Integer.parseInt("11"), Integer.parseInt("12")) + strTodays.substring(Integer.parseInt("13"), Integer.parseInt("14"));
  }

  public static String getFormattedTime() {
    StringBuilder formattedTime = new StringBuilder();
    // CCYYMMddHHMMSS
    DecimalFormat formatter = new DecimalFormat("00");
    Calendar c = Calendar.getInstance();
    c.setTime(new Date());
    formattedTime.append(c.get(Calendar.YEAR));
    formattedTime.append(formatter.format(c.get(Calendar.MONTH) + 1l));
    formattedTime.append(formatter.format(c.get(Calendar.DATE)));
    formattedTime.append(formatter.format(c.get(Calendar.HOUR_OF_DAY)));
    formattedTime.append(formatter.format(c.get(Calendar.MINUTE)));
    formattedTime.append(formatter.format(c.get(Calendar.SECOND)));
    return formattedTime.toString();
  }

  public static String getCurrentTime() {
    StringBuilder formattedTime = new StringBuilder();
    // CCYYMMddHHMMSS
    DecimalFormat formatter = new DecimalFormat("00");
    Calendar c = Calendar.getInstance();
    c.setTime(new Date());
    formattedTime.append(formatter.format(c.get(Calendar.HOUR_OF_DAY)));
    formattedTime.append(formatter.format(c.get(Calendar.MINUTE)));
    formattedTime.append(formatter.format(c.get(Calendar.SECOND)));
    return formattedTime.toString();
  }

  public static String getResponseTime() {
    StringBuilder formattedTime = new StringBuilder();
    // CCYYMMddHHMMSS
    DecimalFormat formatter = new DecimalFormat("00");
    Calendar c = Calendar.getInstance();
    c.setTime(new Date());
    formattedTime.append(formatter.format(c.get(Calendar.HOUR_OF_DAY)));
    formattedTime.append(formatter.format(c.get(Calendar.MINUTE)));
    return formattedTime.toString();
  }

  // Method to get current Transaction date MMdd format
  public static String getTransactionDate() {
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MMdd");
    simpleDateFormat.setLenient(false);
    return simpleDateFormat.format(new Date());
  }

  // Method to get transmission date and time MMddhhmmss Format
  public static String getTransmissionDate() {
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MMddhhmmss");
    simpleDateFormat.setLenient(false);
    return simpleDateFormat.format(new Date());
  }

  // Method to get local time of transaction origination hhmmss Format
  public static String getLocalTransactionTime() {
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("hhmmss");
    simpleDateFormat.setLenient(false);
    return simpleDateFormat.format(new Date());
  }

  // Method to get local date of transaction origination MMdd Format
  public static String getLocalTransactionDate() {
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MMdd");
    simpleDateFormat.setLenient(false);
    return simpleDateFormat.format(new Date());
  }

  // Method to get settlement date of transaction MMdd Format
  public static String getSettlementTransactionDate(Date date) {
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MMdd");
    simpleDateFormat.setLenient(false);
    return simpleDateFormat.format(date);
  }

  public static void main(String[] args) {
    logger.info(getLocalTransactionDate());
  }

  public static int getCurrentDayOfWeekNumber() {
    Calendar c = Calendar.getInstance();
    c.setTime(new Date(System.currentTimeMillis()));
    int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);
    return dayOfWeek;
  }

  public static int getCurrentDayOfMonthNumber() {
    Calendar c = Calendar.getInstance();
    c.setTime(new Date(System.currentTimeMillis()));
    int dayOfWeek = c.get(Calendar.DAY_OF_MONTH);
    return dayOfWeek;
  }

  public static String getCurrentDateInDDMMMYY() {
    Date date = new Date();
    return new SimpleDateFormat("dd-MMM-yy").format(date).toUpperCase();
  }
  
  public static String getCurrentDateInDDMMYY() {
    Date date = new Date();
    return new SimpleDateFormat("dd-MM-yy").format(date).toUpperCase();
  }
}