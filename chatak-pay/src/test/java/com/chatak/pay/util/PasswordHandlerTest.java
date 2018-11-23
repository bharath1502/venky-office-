package com.chatak.pay.util;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class PasswordHandlerTest {

	@InjectMocks
	PasswordHandler passwordHandler;

	@Test
	public void testGetSystemGeneratedPassword() {
		passwordHandler.getSystemGeneratedPassword(1);

	}

	@Test
	public void testGenerateRandomNumber() {
		passwordHandler.generateRandomNumber(1);
	}

	@Test
	public void testEncodePassword() {
		passwordHandler.encodePassword("MD5");
	}

	@Test
	public void testValidate() {
		passwordHandler.validate("MD5");
	}

	@Test(expected = StringIndexOutOfBoundsException.class)
	public void testDecrypt() {
		passwordHandler.decrypt("11");
	}

	@Test
	public void testEncrypt() {
		passwordHandler.encrypt("0");
	}

}
