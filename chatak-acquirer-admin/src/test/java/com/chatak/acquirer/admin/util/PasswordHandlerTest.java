package com.chatak.acquirer.admin.util;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;
@RunWith(MockitoJUnitRunner.class)
public class PasswordHandlerTest {
	

	@InjectMocks
	PasswordHandler passwordHandler;

	@Test
	public void testEncrypt() {
		passwordHandler.encrypt("34");
	}
	
	@Test
	public void testValidate() {
		passwordHandler.validate("5435");
		
	}
	
	@Test
	public void testGetSystemGeneratedPassword() {
		passwordHandler.getSystemGeneratedPassword(Integer.parseInt("543"));
		
	}
	
	@Test
	public void testGenerateRandomNumber() {
		passwordHandler.generateRandomNumber(Integer.parseInt("3"));
	}
	
	@Test
	public void testIsValidPassword() {
		passwordHandler.isValidPassword("1","0");
	}

}
