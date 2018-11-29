package com.chatak.acquirer.admin.controller;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.context.MessageSource;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;

import com.chatak.acquirer.admin.controller.model.LoginDetails;
import com.chatak.pg.util.Properties;

@RunWith(MockitoJUnitRunner.class)
public class LoginValidatorTest {

  @InjectMocks
  LoginValidator loginValidator = new LoginValidator();

  @Mock
  Object object;

  @Mock
  private MessageSource messageSource;

  @Before
  public void setProperties() throws IOException {
    java.util.Properties propertiesUtil = new java.util.Properties();
    propertiesUtil.setProperty("chatak.username.required", "test");
    propertiesUtil.setProperty("chatak.password.required", "12345");
    Properties.mergeProperties(propertiesUtil);
  }

  @Test
  public void testValidate() {
    object = new LoginDetails();
    Errors errors = new BeanPropertyBindingResult(object, "object");
    loginValidator.validate(object, errors);
  }

  @Test
  public void testSupports() {
    loginValidator.supports(getClass());
  }
}
