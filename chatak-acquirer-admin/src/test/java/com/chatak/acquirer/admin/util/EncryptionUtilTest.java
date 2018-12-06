package com.chatak.acquirer.admin.util;

import java.security.NoSuchAlgorithmException;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;


@SuppressWarnings("static-access")
@RunWith(MockitoJUnitRunner.class)
public class EncryptionUtilTest {

	private static Logger logger = Logger.getLogger(EncryptionUtil.class);

	@InjectMocks
	EncryptionUtil encryptionUtil;

	@Test
	public void testEncodePassword() throws NoSuchAlgorithmException {
		encryptionUtil.encodePassword("123");
	}

	@Test
	public void testGeneratePassword() throws NoSuchAlgorithmException {
		encryptionUtil.generatePassword(1);
	}

	@Test
	public void testGenerateRandNumeric() throws NoSuchAlgorithmException {
		encryptionUtil.generateRandNumeric(1);
	}

	@Test
	public void testGeneratePin() throws NoSuchAlgorithmException {
		encryptionUtil.generatePin(1);
	}

	@Test
	public void testHex2byte() {
		byte[] b = { 1, 1, 0 };
		encryptionUtil.hex2byte(b, 1, 1);
	}

	@Test
	public void testMain() {
		String[] arg = { "abc", "bca" };
		try {
			encryptionUtil.main(arg);
		} catch (Exception e) {
			logger.error("Error :: EncryptionUtilTest :: testMain", e);
		}
	}

	@Test
	public void testHex2bytes() {
		encryptionUtil.hex2byte("1");
	}

	@Test
	public void testHexString() {
		byte[] b = { 1, 0, 1 };
		encryptionUtil.hexString(b);
	}

}
