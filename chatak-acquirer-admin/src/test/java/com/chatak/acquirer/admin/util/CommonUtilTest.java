package com.chatak.acquirer.admin.util;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.chatak.pg.util.Constants;
import com.chatak.pg.util.Properties;

@RunWith(MockitoJUnitRunner.class)
public class CommonUtilTest {

	@InjectMocks
	CommonUtil commonUtil;

	@Mock
	Constants constants;

	@Before
	public void init() {
		java.util.Properties propsExportedLocal = new java.util.Properties();
		propsExportedLocal.setProperty("max.download.limit", "12");
		Properties.mergeProperties(propsExportedLocal);
	}
	
	@Test
	public void testIsNullAndEmpty() {
		commonUtil.isNullAndEmpty("54");
	}

	@Test
	public void testGenerateRandomNumber() throws NoSuchAlgorithmException {
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
	public void testGenerateRandNumeric() throws NoSuchAlgorithmException {
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

	@Test
	public void testGenerateAlphaNumericString() throws NumberFormatException, NoSuchAlgorithmException {
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
