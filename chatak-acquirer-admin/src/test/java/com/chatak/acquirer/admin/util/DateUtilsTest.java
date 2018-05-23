package com.chatak.acquirer.admin.util;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.security.core.session.SessionInformation;

import com.chatak.pg.util.Properties;

@RunWith(MockitoJUnitRunner.class)
public class DateUtilsTest {

	@InjectMocks
	DateUtils dateUtils;

	@Mock
	Calendar cal;

	@Mock
	Object principal;

	@Mock
	Date lastRequest;

	@Before
	public void pro() {
		java.util.Properties properties = new java.util.Properties();
		properties.setProperty("chatak-acq-admin.session.timeout", "abcd");
		Properties.mergeProperties(properties);
	}

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
	public void testGetCurrentDateInString() {
		SimpleDateFormat formatter = new SimpleDateFormat();
		dateUtils.getCurrentDateInString(cal, formatter);
	}

	@Test
	public void testGetMonthlyFrequency() {
		dateUtils.getMonthlyFrequency();
	}

	@Test
	public void testIsEligOrderPrinting() {
		dateUtils.isEligOrderPrinting("5435");
	}

	@Test
	public void testGetCurrentDateInMMddyyyy() {
		dateUtils.getCurrentDateInMMddyyyy();
	}

	@Test
	public void testGetDateOfDaysBeforeCurrentDateAndTime() {
		dateUtils.getDateOfDaysBeforeCurrentDateAndTime(1);
	}

	@Test
	public void testGetDateOfDaysBeforeCurrentDateInMMddyyyy() {
		dateUtils.getDateOfDaysBeforeCurrentDateInMMddyyyy(1);
	}

	@Test
	public void testGetDateOfDaysBeforeCurrentDateInMMddyyyyElse() {
		dateUtils.getDateOfDaysBeforeCurrentDateInMMddyyyy(0);
	}

	@Test
	public void testGetTimestamp() {
		dateUtils.getTimestamp("19/01/3424");
	}

	@Test
	public void testGetTimestampException() {
		dateUtils.getTimestamp("1");
	}

	@Test
	public void testConvertTo8583FormattedAmount() {
		dateUtils.convertTo8583FormattedAmount(Double.parseDouble("4"), Integer.parseInt("5"));
	}

	@Test
	public void testGetCurrentDayOfWeek() {
		dateUtils.getCurrentDayOfWeek();
	}

	@Test
	public void testValidateStEndDate() {
		dateUtils.validateStEndDate("5465465465465349", "435435");
	}

	@Test
	public void testFormatTranDate() {
		dateUtils.formatTranDate("1");
	}

	@Test
	public void testValidateTranMonth() {
		dateUtils.validateTranMonth("true");
	}

	@Test
	public void testFormatTranMonth() {
		dateUtils.formatTranMonth("5463");
	}

	@Test
	public void testGetSqlDateFromDateStr() {
		dateUtils.getSqlDateFromDateStr("yyyy-MM-dd HH:mm:ss");
	}

	@Test
	public void testGetDateStrFromSqlTimestatmp() {
		Timestamp dateWithTime = new Timestamp(1);
		dateUtils.getDateStrFromSqlTimestatmp(dateWithTime);
	}

	@Test
	public void testGetFormatDate() {
		Timestamp dateWithTime = new Timestamp(1);
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
	public void testTimestampToString() {
		Timestamp dateWithTime = new Timestamp(1);
		dateUtils.timestampToString(dateWithTime);
	}

	@Test(expected = NumberFormatException.class)
	public void testIsValidRequestSessionRegistryTime() {
		SessionInformation sessionInformation = new SessionInformation(principal, "abcd", lastRequest);
		dateUtils.isValidRequestSessionRegistryTime(sessionInformation);
	}

	@Test
	public void testGetResponseTime() {
		dateUtils.getResponseTime();

	}

}
