/**
 * 
 */
package com.chatak.pg.acq.spring.util;

import org.springframework.stereotype.Service;

import com.chatak.license.exception.InvalidChatakLicenseException;
import com.chatak.license.validator.ChatakLicenseValidator;

/**
 *
 * << Add Comments Here >>
 *
 * @author Girmiti Software
 * @date 31-Dec-2014 7:05:25 PM
 * @version 1.0
 */
@Service
public class ChatakPGLicense {

  public ChatakPGLicense() throws InvalidChatakLicenseException {
  //  ChatakLicenseValidator.getInstance().validateChatakLicenseKey();
  }
  
}
