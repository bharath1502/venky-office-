package com.chatak.acquirer.admin.util;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.chatak.acquirer.admin.exception.ChatakAdminException;

import jxl.write.WriteException;

@RunWith(MockitoJUnitRunner.class)
public class StringUtilTest {

	@InjectMocks
	StringUtil stringUtil;

	@Mock
	Number number;

	@Test
	public void testIsNullAndEmpty() {
		stringUtil.isNullAndEmpty("abc");
	}

	@Test
	public void testIsListNotNullNEmpty() {
		List<String> l = new ArrayList<>();
		stringUtil.isListNotNullNEmpty(l);
	}

	@Test
	public void testIsListNullNEmpty() {
		List<String> l = new ArrayList<>();
		stringUtil.isListNullNEmpty(l);
	}

	@Test
	public void testGetDateValueForWSAPI() {
		stringUtil.getDateValueForWSAPI("2/2/2", "2/34");
	}

	@Test
	public void testToString() {
		number = 1;
		stringUtil.toString(number);
	}

	@Test
	public void testIsNullEmpty() {
		stringUtil.isNullEmpty("ab");
	}

	@Test
	public void testToAmount() {
		Object object = 1;
		stringUtil.toAmount(object);
	}

	@Test
	public void testStartIndexList() {
		stringUtil.startIndexList(1, 1);
	}

	@Test
	public void testConverArray() {
		stringUtil.converArray("1");
	}

	@Test
	public void testEndIndex() {
		stringUtil.endIndex(1, 1);
	}

	@Test
	public void testConvertString() {
		String[] arrayData = { "a", "b", "c" };
		stringUtil.convertString(arrayData);
	}

	@Test
	public void testCheckScriptData() throws ChatakAdminException {
		stringUtil.checkScriptData("2");
	}

	@Test
	public void testConvertListToString() {
		List<String> featureList = new ArrayList<>();
		String s = new String();
		featureList.add(s);
		stringUtil.convertListToString(featureList);
	}

	@Test
	public void testValidatePhone() {
		stringUtil.validatePhone("345435");
	}

	@Test
	public void testGetEmailToken() {
		stringUtil.getEmailToken("23", "abcd");
	}

	@Test(expected = NullPointerException.class)
	public void testParseEmailToken() throws ChatakAdminException {
		stringUtil.parseEmailToken("123");
	}

	@Test
	public void testCheckEquality() throws ChatakAdminException {
		stringUtil.checkEquality("123", "123", true);
	}

	@Test
	public void testCheckEqualityfalse() throws ChatakAdminException {
		stringUtil.checkEquality("123", "123", false);
	}

	@Test
	public void testGetSupportedType() throws ChatakAdminException {
		stringUtil.getSupportedType("123", "324");
	}

	@Test
	public void testGetLong() {
		stringUtil.getLong(Long.parseLong("2"));
	}

	@Test
	public void testGetSubCodeType() {
		stringUtil.getSubCodeType("1//2");
	}

	@Test
	public void testDecode() {
		stringUtil.decode("1/2/");
	}

	@Test
	public void testListToString() {
		List<String> featureList = new ArrayList<>();
		String s = new String();
		featureList.add(s);
		stringUtil.listToString(featureList);
	}

	@Test
	public void testIsNull() {
		Object input = new Object();
		stringUtil.isNull(input);
	}

	@Test
	public void testGetStringl() {
		stringUtil.getString(Long.parseLong("534"));
	}

	@Test
	public void testGetExportUtilCellFormat() throws WriteException {
		stringUtil.getExportUtilCellFormat();
	}

	@Test
	public void testGetAmountInFloat() throws WriteException {
		stringUtil.getAmountInFloat(1, 1, Double.parseDouble("5"));
	}

	@Test
	public void testGetLongString() throws WriteException {
		stringUtil.getLong("534");
	}

	@Test
	public void testEncodeToString() throws WriteException {
		byte[] image = { 1, 1, 0 };
		stringUtil.encodeToString(image, "534");
	}

	@Test
	public void testEscapeHTMLChars() throws WriteException {
		stringUtil.escapeHTMLChars("534");
	}

}
