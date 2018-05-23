package com.chatak.merchant.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.context.MessageSource;
import org.springframework.validation.Errors;

import com.chatak.merchant.controller.model.LoginDetails;

@RunWith(MockitoJUnitRunner.class)
public class LoginValidatorTest {

	@InjectMocks
	LoginValidator loginValidator = new LoginValidator();

	@Mock
	private MessageSource messageSource;

	@Mock
	Object object;

	@Mock
	Errors errors;

	@Test
	public void testValidate() {
		loginValidator.validate(object, errors);
	}

	@Test
	public void testSupports() {
		loginValidator.supports(LoginDetails.class);
	}

}
