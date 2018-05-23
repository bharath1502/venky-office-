package com.chatak.merchant.util;

import java.security.NoSuchAlgorithmException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;
@RunWith(MockitoJUnitRunner.class)
public class EncryptionUtilTest {

	@InjectMocks
	EncryptionUtil encryptionUtil;
	
	@Test
	public void testEncodePassword() throws NoSuchAlgorithmException {
		encryptionUtil.encodePassword("123456789");
	}

	@Test
	public void testGeneratePin() throws NoSuchAlgorithmException {
		encryptionUtil.generatePin(Integer.parseInt("13"));
	}
	
	@Test
	public void testGeneratePassword() throws NoSuchAlgorithmException {
		encryptionUtil.generatePassword(Integer.parseInt("13"));
	}
	
	@Test
	public void testGenerateRandNumeric() throws NoSuchAlgorithmException {
		encryptionUtil.generateRandNumeric(Integer.parseInt("13"));
	}
	

	@Test
	public void testHex2byte(){
		int len=1;
		byte[] b=new byte[Integer.parseInt("14")];
		int offset=Integer.parseInt("2"); 
		encryptionUtil.hex2byte(b,offset,len);
	}
	
	@Test
	public void testHex2byteString() {
		encryptionUtil.hex2byte("Hex2Byte");
	}
	
	@Test
	public void testHex2byteStringValue() {
		encryptionUtil.hex2byte(0+"Hex2Byte");
	}


	@Test
	public void testHexString() {
		byte[] b={1,Byte.parseByte("3")};
		encryptionUtil.hexString(b);
	}
	
}
