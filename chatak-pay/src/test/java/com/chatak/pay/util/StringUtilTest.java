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

@SuppressWarnings("rawtypes")
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
		StringUtil.isListNotNullNEmpty(list);
	}
	
	@Test
	public void testIsListNullNEmpty() {
		List list = new ArrayList<>();
		StringUtil.isListNullNEmpty(list);
	}
	
	@Test
	public void testIsNullAndEmpty() {
		StringUtil.isNullAndEmpty("abc");
	}
	
	@Test
	public void testToString() {
		Number number=null;
		StringUtil.toString(number);
	}
	
	@Test
	public void testToStringNumber() {
		Number number=Integer.parseInt("123");
		StringUtil.toString(number);
	}
	
	@Test
	public void testIsNullEmpty() {
		StringUtil.isNullEmpty("243");
	}
	
	@Test
	public void testStartIndexList() {
		StringUtil.startIndexList(1,1);
	}
	
	@Test
	public void testToAmount() {
		Object object=null;
		StringUtil.toAmount(object);
	}
	
	@Test
	public void testGetDateValueForWSAPI() {
		StringUtil.getDateValueForWSAPI("1/2/3","12");
	}
	
	@Test
	public void testEndIndex(){
		StringUtil.endIndex(0, 1);
	}
	
	@Test
	public void testEndIndexOne(){
		StringUtil.endIndex(1, 1);
	}
	
	@Test
	public void testConvertListToString(){
		List<String> featureList=new ArrayList<>();
		featureList.add("");
		StringUtil.convertListToString(featureList);
	}
	
	@Test
	public void testConvertListToStringNull(){
		List<String> featureList=new ArrayList<>();
		StringUtil.convertListToString(featureList);
	}
	
	@Test
	public void testConvertString(){
		String[] arrayData={"abc","bcd","cde"};
		StringUtil.convertString(arrayData);
	}
	
	@Test
	public void testConvertStringNull(){
		String[] arrayData=null;
		StringUtil.convertString(arrayData);
	}
	
	@Test
	public void testConverArray(){
		StringUtil.converArray("abc");
	}
	
	@Test
	public void testConverArrayNull(){
		StringUtil.converArray(null);
	}

	@Test
	public void testCheckScriptData() throws ChatakPayException{
		StringUtil.checkScriptData("abc");
	}
	
	@Test
	public void testValidatePhone(){
		StringUtil.validatePhone("12345");
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testParseEmailToken() throws ChatakPayException{
		StringUtil.parseEmailToken("123");
	}
	
	@Test
	public void testGetLong(){
		StringUtil.getLong("123");
	}

	@Test
	public void testGetString(){
		StringUtil.getString(Long.parseLong("1234"));
	}
	
	@Test
	public void testGetStringNull(){
		StringUtil.getString(null);
	}
	
	@Test
	public void testCheckEquality(){
		StringUtil.checkEquality("ab","bc",false);
	}
	
	@Test
	public void testCheckEqualityTrue(){
		StringUtil.checkEquality("ab","bc",true);
	}
	
	@Test
	public void testGetLongValue(){
		StringUtil.getLong(Long.parseLong("1234"));
	}
	
	@Test
	public void testGetLongValueNull(){
		Long value=null;
		StringUtil.getLong(value);
	}
	
	@Test
	public void testGetSupportedType(){
		StringUtil.getSupportedType("ab","ab");
	}
	
	@Test
	public void testDecode(){
		StringUtil.decode("123");
	}
	
	@Test
	public void testDecodeNull(){
		StringUtil.decode(null);
	}
	
	@Test
	public void testGetSubCodeType(){
		List<String> codeTypeList = new ArrayList<>();
		codeTypeList.add("");
		StringUtil.getSubCodeType("abc\\|");
	}
	
	@Test
	public void testGetPaymentSessionToken(){
		StringUtil.getPaymentSessionToken(request,session,"mid");
	}
	
	@Test
	public void testGetDefaultTerminalID(){
		StringUtil.getDefaultTerminalID("10000001");
	}
	
	@Test
	public void testGetDefaultTerminalIDNull(){
		StringUtil.getDefaultTerminalID(null);
	}
	
	@Test
	public void testGetDefaultTerminalIDLength(){
		StringUtil.getDefaultTerminalID("100000011");
	}
	
	@Test
	public void testGetStatusResponse(){
		StringUtil.getStatusResponse("1234");
	}
	
	@Test
	public void testHexToAscii(){
		StringUtil.hexToAscii("1234");

	}
	
	@Test
	public void testGetEmailToken(){
		StringUtil.getEmailToken("123","456");
	}


}
