package com.chatak.pay.util;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.chatak.pay.exception.ChatakPayException;

@RunWith(MockitoJUnitRunner.class)
public class StringUtilTest {

	@InjectMocks
	StringUtil stringUtil;
	
	@Mock
	HttpServletRequest request;
	
	@Mock
	HttpSession session;

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
	public void testIsNullAndEmpty() {
		stringUtil.isNullAndEmpty("abc");
	}
	
	@Test
	public void testToString() {
		Number number=null;
		stringUtil.toString(number);
	}
	
	@Test
	public void testToStringNumber() {
		Number number=Integer.parseInt("123");
		stringUtil.toString(number);
	}
	
	@Test
	public void testIsNullEmpty() {
		stringUtil.isNullEmpty("243");
	}
	
	@Test
	public void testStartIndexList() {
		stringUtil.startIndexList(1,1);
	}
	
	@Test
	public void testToAmount() {
		Object object=null;
		stringUtil.toAmount(object);
	}
	
	@Test
	public void testGetDateValueForWSAPI() {
		stringUtil.getDateValueForWSAPI("1.2/2","12");
	}
	
	@Test
	public void testEndIndex(){
		stringUtil.endIndex(0, 1);
	}
	
	@Test
	public void testEndIndexOne(){
		stringUtil.endIndex(1, 1);
	}
	
	@Test
	public void testConvertListToString(){
		List<String> featureList=new ArrayList<>();
		featureList.add("");
		stringUtil.convertListToString(featureList);
	}
	
	@Test
	public void testConvertListToStringNull(){
		List<String> featureList=new ArrayList<>();
		stringUtil.convertListToString(featureList);
	}
	
	@Test
	public void testConvertString(){
		String[] arrayData={"abc","bcd","cde"};
		stringUtil.convertString(arrayData);
	}
	
	@Test
	public void testConvertStringNull(){
		String[] arrayData=null;
		stringUtil.convertString(arrayData);
	}
	
	@Test
	public void testConverArray(){
		stringUtil.converArray("abc");
	}
	
	@Test
	public void testConverArrayNull(){
		stringUtil.converArray(null);
	}

	@Test
	public void testCheckScriptData() throws ChatakPayException{
		stringUtil.checkScriptData("abc");
	}
	
	@Test
	public void testValidatePhone(){
		stringUtil.validatePhone("12345");
	}
	
	@Test(expected=NullPointerException.class)
	public void testParseEmailToken() throws ChatakPayException{
		stringUtil.parseEmailToken("123");
	}
	
	@Test
	public void testGetLong(){
		stringUtil.getLong("123");
	}
	
	@Test
	public void testGetLongNumberFormatException(){
		stringUtil.getLong("abc");
	}
	
	@Test
	public void testGetString(){
		stringUtil.getString(Long.parseLong("1234"));
	}
	
	@Test
	public void testGetStringNull(){
		stringUtil.getString(null);
	}
	
	@Test
	public void testCheckEquality(){
		stringUtil.checkEquality("ab","bc",false);
	}
	
	@Test
	public void testCheckEqualityTrue(){
		stringUtil.checkEquality("ab","bc",true);
	}
	
	@Test
	public void testGetLongValue(){
		stringUtil.getLong(Long.parseLong("1234"));
	}
	
	@Test
	public void testGetLongValueNull(){
		Long value=null;
		stringUtil.getLong(value);
	}
	
	@Test
	public void testGetSupportedType(){
		stringUtil.getSupportedType("ab","ab");
	}
	
	@Test
	public void testDecode(){
		stringUtil.decode("123");
	}
	
	@Test
	public void testDecodeNull(){
		stringUtil.decode(null);
	}
	
	@Test
	public void testGetSubCodeType(){
		List<String> codeTypeList = new ArrayList<>();
		codeTypeList.add("");
		stringUtil.getSubCodeType("abc\\|");
	}
	
	@Test
	public void testGetPaymentSessionToken(){
		stringUtil.getPaymentSessionToken(request,session,"mid");
	}
	
	@Test
	public void testGetDefaultTerminalID(){
		stringUtil.getDefaultTerminalID("10000001");
	}
	
	@Test
	public void testGetDefaultTerminalIDNull(){
		stringUtil.getDefaultTerminalID(null);
	}
	
	@Test
	public void testGetDefaultTerminalIDLength(){
		stringUtil.getDefaultTerminalID("100000011");
	}
	
	@Test
	public void testGetStatusResponse(){
		stringUtil.getStatusResponse("1234");
	}
	
	@Test
	public void testHexToAscii(){
		stringUtil.hexToAscii("1234");

	}
	
	@Test
	public void testHexToAsciiNull(){
		stringUtil.hexToAscii(null);
	}
	
	@Test
	public void testGetEmailToken(){
		stringUtil.getEmailToken("123","456");
	}


}
