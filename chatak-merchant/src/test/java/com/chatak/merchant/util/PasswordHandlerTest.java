package com.chatak.merchant.util;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class PasswordHandlerTest {

	@InjectMocks
	PasswordHandler passwordHandler;

	@Test
	public void testEncodePassword() {
		passwordHandler.encodePassword("password");
	}

	@Test
	public void testGetSystemGeneratedPassword() {
		passwordHandler.getSystemGeneratedPassword(Integer.parseInt("3"));
	}

	@Test
	public void testGenerateRandomNumber() {
		passwordHandler.generateRandomNumber(Integer.parseInt("3"));
	}

	@Test
	public void testValidate() {
		passwordHandler.validate("36");
	}

	@Test
	public void testEncrypt() {
		passwordHandler.encrypt("3612");
	}

	@Test
	public void testDecrypt() {
		passwordHandler.decrypt("_crypt_chatak_C3K681A2M19A02N20V11I==");
	}

}
