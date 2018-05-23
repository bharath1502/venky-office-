package com.chatak.pay.util;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class DateUtilsTest {

	@InjectMocks
	DateUtils dateUtils;

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
	public void testIsEligOrderPrinting() {
		dateUtils.isEligOrderPrinting("11/12/19");
	}

	@Test
	public void testGetMonthlyFrequency() {
		dateUtils.getMonthlyFrequency();
	}

	@Test
	public void testGetCurrentDateInString() {
		Calendar calendar = Calendar.getInstance();
		SimpleDateFormat dateFormat2 = new SimpleDateFormat("MM/dd/yyyy hh:mma");
		dateUtils.getCurrentDateInString(calendar, dateFormat2);
	}

	@Test
	public void testGetCurrentDateInMMddyyyy() {
		dateUtils.getCurrentDateInMMddyyyy();
	}

	@Test
	public void testGetDateOfDaysBeforeCurrentDateInMMddyyyy() {
		dateUtils.getDateOfDaysBeforeCurrentDateInMMddyyyy(1);
	}

	@Test
	public void testGetDateOfDaysBeforeCurrentDateAndTime() {
		dateUtils.getDateOfDaysBeforeCurrentDateAndTime(1);
	}

	@Test
	public void testConvertTo8583FormattedAmount() {
		dateUtils.convertTo8583FormattedAmount(Double.parseDouble("543"), 1);
	}

	@Test
	public void testGetTimestamp() {
		dateUtils.getTimestamp("3/12/43");
	}

	@Test
	public void testGetTimestampElse() {
		dateUtils.getTimestamp("33");
	}

	@Test
	public void testGetCurrentDayOfWeek() {
		dateUtils.getCurrentDayOfWeek();
	}

	@Test
	public void testValidateStEndDate() {
		dateUtils.validateStEndDate("15345434", "53454354");
	}

	@Test
	public void testFormatTranMonth() {
		dateUtils.formatTranMonth("3356");
	}

	@Test
	public void testFormatTranDate() {
		dateUtils.formatTranDate("20111112");
	}

	@Test
	public void testFormatTranDateException() {
		dateUtils.formatTranDate("2011");
	}

	@Test
	public void testValidateTranMonth() {
		dateUtils.validateTranMonth("2011");
	}

	@Test
	public void testGetSqlDateFromDateStr() {
		dateUtils.getSqlDateFromDateStr("2011-11-12 12:45:55");
	}

	@Test
	public void testGetFormatDate() {
		Timestamp dateWithTime = new Timestamp(Long.parseLong("5435"));
		dateUtils.getFormatDate(dateWithTime);
	}

	@Test
	public void testGetDateStrFromSqlTimestatmp() {
		Timestamp dateWithTime = new Timestamp(Long.parseLong("5435"));
		dateUtils.getDateStrFromSqlTimestatmp(dateWithTime);
	}

	@Test
	public void testGetFormattedTime() {
		dateUtils.getFormattedTime();
	}

	@Test
	public void testGetResponseTime() {
		dateUtils.getResponseTime();
	}

	@Test
	public void testGetCurrentTime() {
		dateUtils.getCurrentTime();
	}

	@Test
	public void testTimestampToString() {
		Timestamp timestamp = new Timestamp(Long.parseLong("543"));
		dateUtils.timestampToString(timestamp);
	}

}
