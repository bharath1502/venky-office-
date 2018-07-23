package com.chatak.acquirer.admin.util;

import java.util.ArrayList;
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
		commonUtil.isNullAndEmpty("54");
	}

	@Test
	public void testGenerateRandomNumber() {
		commonUtil.generateRandomNumber(1);
	}

	public void testIsListNotNullAndEmpty() {
		List list = new ArrayList();
		commonUtil.isListNotNullAndEmpty(list);
	}

	@Test
	public void testToAmount() {
		commonUtil.toAmount(0);
	}

	@Test
	public void testToAmountException() {
		Object object = new Object();
		commonUtil.toAmount(object);
	}

	@Test
	public void testGetSuccessResponse() {
		commonUtil.getSuccessResponse();
	}

	@Test
	public void testGenerateRandNumeric() {
		commonUtil.generateRandNumeric(1);
	}

	@Test
	public void testGetErrorResponse() {
		commonUtil.getErrorResponse();
	}

	@Test
	public void testGetUniqueId() {
		commonUtil.getUniqueId();
	}

	@Test
	public void testGetCurrentDate() {
		commonUtil.getCurrentDate();
	}

	@Test(expected=NumberFormatException.class)
	public void testGenerateNumericString() {
		commonUtil.generateNumericString(Integer.parseInt("534"));

	}

	@Test
	public void testGenerateAlphaNumericString() {
		commonUtil.generateAlphaNumericString(Integer.parseInt("534"));

	}

	@Test
	public void testGetDateFromMMDD() {
		commonUtil.getDateFromMMDD("22011990");
	}

	@Test
	public void testStringToBigDecimal() {
		commonUtil.stringToBigDecimal("6546");
	}

	@Test
	public void testEncodeToString() {
		byte[] image = { 1, 0, 1 };
		commonUtil.encodeToString(image, "type");
	}

	@Test
	public void testStringToLong() {
		commonUtil.stringToLong("4234");
	}

}
