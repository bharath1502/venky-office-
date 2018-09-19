package com.chatak.pg.util;


import java.sql.Time;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import org.apache.log4j.Logger;

import com.chatak.pg.constants.PGConstants;

/**
 * @author Kumar
 */
@SuppressWarnings("deprecation")
public final class DateUtil {
  
  private static Logger logger = Logger.getLogger(DateUtil.class);

	public static final String VIEW_DATE_FORMAT = "dd/MM/yyyy";

	public static final String VIEW_DATE_TIME_FORMAT = "dd/MM/yyyy hh:mm:ss a";

	public static final String SIMPLE_DATE_FORMAT = "dd/MM/yyyy";

	private static String dateRegex1 = "^[0-9][0-9](0[1-9]|1[0-2])$";

	private static Pattern expDatePattern = Pattern.compile(dateRegex1);

	private DateUtil() {
    //To avoid instantiation
  }
	/**
	 * Method to get Current Timestamp
	 * 
	 * @return
	 */
	public static Timestamp getCurrentTimestamp() {
		return new Timestamp(System.currentTimeMillis());
	}

	/**
	 * Method to get java.sql.Timestamp from given date string and pattern
	 * 
	 * @param date
	 * @param pattern
	 * @return
	 */
	public static Timestamp toTimestamp(String date, String pattern) {
		try {
      SimpleDateFormat dateFormat = new SimpleDateFormat();
			dateFormat.applyPattern(pattern);
			return new Timestamp(dateFormat.parse(date).getTime());
		}
		catch(ParseException e) {
		  logger.error("Error :: DateUtil :: toTimestamp", e);
		}
		return null;
	}

	public static Date toDate(Date date, String pattern) {
    SimpleDateFormat dateFormat = new SimpleDateFormat();
		dateFormat.applyPattern(pattern);
		return new Date(date.getTime());

	}

	public static Time toTime(String time, String pattern) {

		try {
      SimpleDateFormat dateFormat = new SimpleDateFormat();
			dateFormat.applyPattern(pattern);
			return new Time(dateFormat.parse(time).getTime());

		}
		catch(ParseException e) {
		  logger.error("Error :: DateUtil :: toTime", e);
		}
		return null;
	}

	/**
	 * Method to get Date as string from given date time stamp and pattern
	 * 
	 * @param date
	 * @param pattern
	 * @return
	 */
	public static String toDateStringFormat(Timestamp date, String pattern) {
		if(null != date) {
      SimpleDateFormat dateFormat = new SimpleDateFormat();
			dateFormat.applyPattern(pattern);
			return dateFormat.format(date);
		}
		else {
			return null;
		}
	}

	/**
	 * Method to get Current Day's EOD Timestamp
	 * 
	 * @return
	 */
	public static Timestamp getCurrentDayEODTimestamp() {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt("23"));
		calendar.set(Calendar.MINUTE, Integer.parseInt("59"));
		calendar.set(Calendar.SECOND, Integer.parseInt("59"));
		calendar.set(Calendar.MILLISECOND, 1);
		return new Timestamp(calendar.getTimeInMillis());
	}

	/**
	 * Method to get java.sql.Timestamp from given date string and pattern
	 * 
	 * @param date
	 * @param pattern
	 * @return
	 */
	public static Timestamp getStartDayTimestamp(String date, String pattern) {
	  try {
      if(null == date || "".equals(date)) {
        return null;
      }
      SimpleDateFormat dateFormat = new SimpleDateFormat();
      dateFormat.applyPattern(pattern);
      Date date2 = dateFormat.parse(date);
      date2.setHours(00);
      date2.setSeconds(00);
      date2.setMinutes(00);
      return new Timestamp(date2.getTime());
    }
    catch(ParseException e) {
      logger.error("ERROR:: DateUtil::getStartDayTimestamp ", e);
    }
	  
		return null;
	}

	/**
	 * Method to get java.sql.Timestamp from given date string and pattern
	 * 
	 * @param date
	 * @param pattern
	 * @return
	 */
  public static Timestamp getEndDayTimestamp(String date, String pattern) {
		try {
			if(null == date || "".equals(date)) {
				return null;
			}
			SimpleDateFormat dateFormat = new SimpleDateFormat();
			dateFormat.applyPattern(pattern);
			Date date2 = dateFormat.parse(date);
			date2.setHours(Integer.parseInt("23"));
			date2.setMinutes(Integer.parseInt("59"));
			date2.setSeconds(Integer.parseInt("59"));
			return new Timestamp(date2.getTime());
		}
		catch(ParseException e) {
		  logger.error("Error :: DateUtil :: getEndDayTimestamp", e);
		}
		return null;
	}

	public static XMLGregorianCalendar convertStringToXMLGregorianCalendar(String dateStr,String pattern) 
	{
		XMLGregorianCalendar calendar = null;
		try
		{
			DateFormat format = new SimpleDateFormat(pattern);
			Date date = format.parse(dateStr);

			GregorianCalendar gregory = new GregorianCalendar();
			gregory.setTime(date);

			calendar = DatatypeFactory.newInstance()
					.newXMLGregorianCalendar(
							gregory);
		}
		catch (ParseException p)
		{
		  logger.error("ERROR:: DateUtil:: XMLGregorianCalendar method", p);
		}
		catch (DatatypeConfigurationException e) {
		  logger.error("ERROR:: DateUtil:: XMLGregorianCalendar method", e);
		}
		StringBuilder dateBuffer = new StringBuilder();
		if(calendar != null) {
		  dateBuffer.append(calendar.getDay()+"/"+calendar.getMonth()+"/"+calendar.getYear()+" "+calendar.getHour()+":"+calendar.getMinute()+":"+calendar.getSecond() );
		}
		logger.info(dateBuffer.toString());
		return calendar;
	}

	public static String convertXMLGregorianCalendartoString(XMLGregorianCalendar gregorianCalendar)
	{
	  StringBuilder dateBuffer = new StringBuilder();
		dateBuffer.append(gregorianCalendar.getDay()+"/"+gregorianCalendar.getMonth()+"/"+gregorianCalendar.getYear());
		return dateBuffer.toString();
	}

	public static String convertTime(long time)
	{
		long dividend=time/Integer.parseInt("60");
		long remainder=time%Integer.parseInt("60");
		long journeyDay=dividend/Integer.parseInt("24");

		long hour=dividend%Integer.parseInt("24");
		long min=remainder;

		String mins=String.valueOf(min);
		String hours=String.valueOf(hour);

		if(min/Integer.parseInt("10")==0 || min==0){
			mins="0"+mins;
		}
		if(hour/Integer.parseInt("10")==0 || hour==0){
			hours="0"+hours;
		}
		String timeString=hours+":"+mins;


		if(journeyDay!=0){
			timeString=timeString+". Arrives Next day";
		}
		logger.info(timeString);
		return timeString;
	}

	public static String converToTime(long time)
	{
		long timeReminder = time%Integer.parseInt("60");
		long timeQuotient = time/Integer.parseInt("60");
		long hour = timeQuotient%Integer.parseInt("24");
		String endParameter = (hour>Integer.parseInt("12"))?" pm":" am";
		hour = (hour>Integer.parseInt("12"))?hour - Integer.parseInt("12"):hour;
		String arrivalTime = String.valueOf(hour)+":"+((timeReminder<Integer.parseInt("10"))?"0":"")+String.valueOf(timeReminder)+endParameter;
		return arrivalTime;
	}

	public static String getValidDates(String dates,String pat,String oldFormat) throws ParseException
	{
		DateFormat originalFormat = new SimpleDateFormat(oldFormat);
		DateFormat targetFormat = new SimpleDateFormat(pat);
		Date date = originalFormat.parse(dates);
		String formattedDate = targetFormat.format(date);
		return formattedDate;
	}

	public static String getValidSysDate(String dates,String pat,String oldFormat)
	{
		if ( oldFormat.indexOf('.')>-1)
		{
			oldFormat=oldFormat.replace(".","/");
			dates=dates.replace(".","/");
		}
		String formattedDate=null;
		try{
			DateFormat originalFormat = new SimpleDateFormat(oldFormat);
			DateFormat targetFormat = new SimpleDateFormat(pat);
			Date date = originalFormat.parse(dates);
			formattedDate = targetFormat.format(date);

		}
		catch(ParseException dpe)
		{
		  logger.error("Error DateUtil::getValidSysDate()"+dpe);
		}
		return formattedDate;
	}
		  
	  /**
	   * 
	   * <Method to check YYMM date format>>
	   * @param expiryDate
	   * @return
	   */
	  public static boolean isValidCardExpiryDate(String expiryDate){
	    try{
	    Matcher matcher = expDatePattern.matcher(expiryDate);
      return matcher.matches();
	    }catch(Exception e){
	      logger.error("ERROR:: DateUtil:: isValidCardExpiryDate method", e);
	      return false;
	    }
	  }

    public static String convertTimeZone(String offSet, String time) {
      String format = PGConstants.DATE_FORMAT;
      Date date = null;
      String convertedTime = null;
      try {
        SimpleDateFormat estFormatter = new SimpleDateFormat(format);
        date = estFormatter.parse(time);
        SimpleDateFormat utcFormatter = new SimpleDateFormat(format);
        utcFormatter.setTimeZone(TimeZone.getTimeZone(offSet));
        convertedTime = utcFormatter.format(date);
      }
      catch(ParseException e) {
        logger.error("ERROR:: DateUtil:: convertTimeZone method", e);
      }
      return convertedTime;
    }
}
