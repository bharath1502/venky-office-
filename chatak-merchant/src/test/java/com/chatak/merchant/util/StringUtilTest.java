package com.chatak.merchant.util;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.PatternSyntaxException;

import javax.servlet.http.HttpServletRequest;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.chatak.merchant.exception.ChatakMerchantException;
import com.chatak.pg.util.Properties;

@RunWith(MockitoJUnitRunner.class)
public class StringUtilTest {

	@InjectMocks
	StringUtil stringUtil;

	@Mock
	Number number;

	@Mock
	HttpServletRequest request;

	@Before
	public void pro() {
		java.util.Properties properties = new java.util.Properties();
		properties.setProperty("chatak.script.inject.error", "0");
		Properties.mergeProperties(properties);
	}

	@Test
	public void testIsNullAndEmpty() {
		stringUtil.isNullAndEmpty("abcd");
	}

	@Test
	public void testIsListNotNullNEmpty() {
		List list = new ArrayList<>();
		stringUtil.isListNotNullNEmpty(list);
	}

	@Test
	public void testIsListNullNEmpty() {
		List list = new ArrayList<>();
		stringUtil.isListNullNEmpty(list);
	}

	@Test
	public void testToString() {
		stringUtil.toString(number);
	}

	@Test
	public void testIsNullEmpty() {
		stringUtil.isNullEmpty("input");
	}

	@Test
	public void testGetDateValueForWSAPI() {
		stringUtil.getDateValueForWSAPI("raw/kj/kj.", "time");
	}

	@Test
	public void testGetDateValueForWSAPINull() {
		stringUtil.getDateValueForWSAPI(null, "time");
	}

	@Test
	public void testGetDateValueForWSAPIException() {
		stringUtil.getDateValueForWSAPI("/kj", "time");
	}

	@Test
	public void testToAmount() {
		Object object = "25.2";
		stringUtil.toAmount(object);
	}

	@Test
	public void testToAmountException() {
		Object object = new Object();
		stringUtil.toAmount(object);
	}

	@Test
	public void testStartIndexList() {
		stringUtil.startIndexList(1, 1);
	}

	@Test
	public void testEndIndex() {
		stringUtil.endIndex(1, 1);
	}

	@Test
	public void testConverArray() {
		stringUtil.converArray("data");
	}

	@Test
	public void testConvertString() {
		String[] arrayData = { "a", "b" };
		stringUtil.convertString(arrayData);
	}

	@Test
	public void testConvertListToString() {
		List<String> featureList = new ArrayList<>();
		featureList.add("");
		stringUtil.convertListToString(featureList);
	}

	@Test(expected = PatternSyntaxException.class)
	public void testCheckScriptData() throws ChatakMerchantException {
		stringUtil.checkScriptData("description/dg/dgdf/g");

	}

	@Test
	public void testValidatePhone() {
		stringUtil.validatePhone("phone");
	}

	@Test(expected = IllegalArgumentException.class)
	public void testParseEmailToken() throws ChatakMerchantException {
		stringUtil.parseEmailToken("phone");
	}

	@Test
	public void testCheckEqualityTrue() {
		stringUtil.checkEquality("6546", "645", true);
	}

	@Test
	public void testCheckEqualityFalse() {
		stringUtil.checkEquality("6546", "645", false);
	}

	@Test
	public void testGetString() {
		stringUtil.getString(Long.parseLong("534"));
	}

	@Test
	public void testGetLong() {
		stringUtil.getLong("54");
	}

	@Test
	public void testGetLongException() {
		stringUtil.getLong("value");
	}

	@Test
	public void testGetLongValue() {
		stringUtil.getLong(Long.parseLong("534"));
	}

	@Test(expected = NullPointerException.class)
	public void testGetCookieValue() {
		stringUtil.getCookieValue(request);
	}

	@Test
	public void testGetEmailToken() {
		stringUtil.getEmailToken("423", "asd");
	}

	@Test(expected = ChatakMerchantException.class)
	public void testConvertObjectToJSON() throws ChatakMerchantException {
		Object object = new Object();
		stringUtil.convertObjectToJSON(object);
	}

	@Test
	public void testamountToString() {
		stringUtil.amountToString(Long.parseLong("543"));
	}

	@Test
	public void testIsNull() {
		Object object = new Object();
		stringUtil.isNull(object);
	}

}