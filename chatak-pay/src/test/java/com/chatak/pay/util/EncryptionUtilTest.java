package com.chatak.pay.util;

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
	public void testGeneratePin() {
		encryptionUtil.generatePin(1);
	}

	@Test
	public void testEncodePassword() throws NoSuchAlgorithmException {
		encryptionUtil.encodePassword("abcd");
	}

	@Test
	public void testGeneratePassword() {
		encryptionUtil.generatePassword(1);
	}

	@Test
	public void testGenerateRandNumeric() {
		encryptionUtil.generateRandNumeric(1);
	}

	@Test
	public void testHexString() {
		byte[] b = { 1, 1, 1 };
		encryptionUtil.hexString(b);
	}

	@Test
	public void testHex2byte() {
		encryptionUtil.hex2byte("1");
	}

	@Test
	public void testGetJCryptoKeyPair() {
		encryptionUtil.getJCryptoKeyPair();
	}

	@Test(expected = NullPointerException.class)
	public void testDecrypt() {
		encryptionUtil.decrypt(null, "abc");
	}

}
