package com.chatak.acquirer.admin.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.chatak.acquirer.admin.controller.model.LoginDetails;

/**
 * << Add Comments Here >>
 * 
 * @author Girmiti Software
 * @date 03-Jan-2015 10:54:21 AM
 * @version 1.0
 */
@Service
public class LoginValidator implements Validator {


  @Autowired
  private MessageSource messageSource;

  public LoginValidator() {
	  //User defined Constructor
  }

  @Override
  public boolean supports(Class<?> clazz) {
    return LoginDetails.class.isAssignableFrom(clazz);
  }

  @Override
  public void validate(Object object, Errors errors) {
    ValidationUtils.rejectIfEmptyOrWhitespace(errors, "acqU", messageSource
        .getMessage("chatak.username.required", null, LocaleContextHolder.getLocale()));
    ValidationUtils.rejectIfEmptyOrWhitespace(errors, "acqP", messageSource
        .getMessage("chatak.password.required", null, LocaleContextHolder.getLocale()));

  }

}
