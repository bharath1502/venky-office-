package com.chatak.merchant.util;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.security.core.session.SessionInformation;

public class DateUtilsTest {

	@InjectMocks
	DateUtils dateUtils;

	@Mock
	SessionInformation sessionInformation;

	@Test
	public void testGetDailyFrequency() {
		Date today = new Date();
		dateUtils.getDailyFrequency(today);

	}

	@Test
	public void testGetWeeklyFrequency() {
		dateUtils.getWeeklyFrequency();

	}

	@Test
	public void testGetMonthlyFrequency() {
		dateUtils.getMonthlyFrequency();
	}

	@Test
	public void testIsEligOrderPrinting() {
		String orderReqDateTime = "10";
		dateUtils.isEligOrderPrinting(orderReqDateTime);
	}

	@Test
	public void testGetCurrentDateInString() {
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat formatter = new SimpleDateFormat("EEEE MMM dd yyyy hh:mma");
		dateUtils.getCurrentDateInString(cal, formatter);
	}

	@Test
	public void testGetCurrentDateInMMddyyyy() {
		dateUtils.getCurrentDateInMMddyyyy();
	}

	@Test
	public void testGetDateOfDaysBeforeCurrentDateInMMddyyyy() {
		dateUtils.getDateOfDaysBeforeCurrentDateInMMddyyyy(Integer.parseInt("432"));
	}

	@Test
	public void testGetDateOfDaysBeforeCurrentDateInMMddyyyyElse() {
		dateUtils.getDateOfDaysBeforeCurrentDateInMMddyyyy(Integer.parseInt("0"));
	}

	@Test
	public void testGetDateOfDaysBeforeCurrentDateAndTime() {
		dateUtils.getDateOfDaysBeforeCurrentDateAndTime(Integer.parseInt("432"));
	}

	@Test
	public void testGetTimestamp() {
		dateUtils.getTimestamp("432");
	}

	@Test
	public void testGetCurrentDayOfWeek() {
		dateUtils.getCurrentDayOfWeek();
	}

	@Test
	public void tesConvertTo8583FormattedAmount() {
		dateUtils.convertTo8583FormattedAmount(Double.parseDouble("2"), Integer.parseInt("1"));
	}

	@Test
	public void testValidateStEndDate() {
		dateUtils.validateStEndDate("435", "15");
	}

	@Test
	public void testFormatTranDate() {
		dateUtils.formatTranDate("yyyyMMdd");
	}

	@Test
	public void testFormatTranMonth() {
		dateUtils.formatTranMonth("4");
	}

	@Test
	public void testValidateTranMonth() {
		dateUtils.validateTranMonth("4");
	}

	@Test
	public void testGetSqlDateFromDateStr() {
		dateUtils.getSqlDateFromDateStr("dateMonYear");
	}

	@Test
	public void testGetDateStrFromSqlTimestatmp() {
		Timestamp dateWithTime = new Timestamp(Long.parseLong("120"));
		dateUtils.getDateStrFromSqlTimestatmp(dateWithTime);
	}

	@Test
	public void testGetFormatDate() {
		Timestamp dateWithTime = new Timestamp(Long.parseLong("120"));
		dateUtils.getFormatDate(dateWithTime);
	}

	@Test
	public void testGetFormattedTime() {
		dateUtils.getFormattedTime();
	}

	@Test
	public void testGetCurrentTime() {
		dateUtils.getCurrentTime();
	}

	@Test
	public void testGetResponseTime() {
		dateUtils.getResponseTime();
	}

	@Test(expected = NullPointerException.class)
	public void testIsValidRequestSessionRegistryTime() {
		dateUtils.isValidRequestSessionRegistryTime(sessionInformation);
	}

	@Test
	public void testTimestampToString() {
		Timestamp timestamp = new Timestamp(Long.parseLong("543"));
		dateUtils.timestampToString(timestamp);
	}

}
