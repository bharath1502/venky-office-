package com.chatak.merchant.util;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class CommonUtilTest {

	@InjectMocks
	CommonUtil commonUtil;

	@Test
	public void testIsNullAndEmpty() {
		commonUtil.isNullAndEmpty("value");
	}

	@Test
	public void testIsListNotNullAndEmpty() {
		List list = new ArrayList<>();
		commonUtil.isListNotNullAndEmpty(list);
	}

	@Test
	public void testGenerateRandomNumber() {
		commonUtil.generateRandomNumber(Integer.parseInt("543"));
	}

	@Test
	public void testToAmount() {
		Object object = Double.parseDouble("22.0");
		commonUtil.toAmount(object);
	}

	@Test
	public void testToAmountException() {
		Object object = new Object();
		commonUtil.toAmount(object);
	}

	@Test
	public void testGenerateRandNumeric() {
		commonUtil.generateRandNumeric(Integer.parseInt("453"));
	}

	@Test
	public void testGetSuccessResponse() {
		commonUtil.getSuccessResponse();
	}

	@Test
	public void testGetErrorResponse() {
		commonUtil.getErrorResponse();
	}

	@Test
	public void testGetCurrentDate() {
		commonUtil.getCurrentDate();
	}

	@Test
	public void testGetUniqueId() {
		commonUtil.getUniqueId();
	}

	@Test
	public void testGetDateFromMMDD() {
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(Long.parseLong("534"));
		commonUtil.getDateFromMMDD("5435");
	}

	@Test
	public void testGenerateAlphaNumericString() {
		commonUtil.generateAlphaNumericString(Integer.parseInt("6546"));
	}

	@Test
	public void testEncodeToString() {
		byte[] bytes = { 1, 1, 1 };
		String type = "hi";
		commonUtil.encodeToString(bytes, type);

	}

	@Test
	public void testStringToBigDecimal() {
		commonUtil.stringToBigDecimal("234");
	}

	@Test
	public void testStringToLong() {
		commonUtil.stringToLong("235434");
	}

}
