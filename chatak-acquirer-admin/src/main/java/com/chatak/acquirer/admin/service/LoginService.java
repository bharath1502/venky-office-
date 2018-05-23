/**
 * 
 */
package com.chatak.acquirer.admin.service;

import java.security.NoSuchAlgorithmException;

import com.chatak.acquirer.admin.controller.model.LoginDetails;
import com.chatak.acquirer.admin.controller.model.LoginResponse;
import com.chatak.acquirer.admin.controller.model.ResetPasswordData;
import com.chatak.acquirer.admin.exception.ChatakAdminException;
import com.chatak.acquirer.admin.model.UserProfileRequest;

/**
 *
 * << Add Comments Here >>
 *
 * @author Girmiti Software
 * @date 03-Jan-2015 10:59:57 AM
 * @version 1.0
 */
public interface LoginService {

  /**
   * Service method to authenticate the user
   * 
   * @param loginDetails
   * @return
   * @throws ChatakAdminException
   */
  public LoginResponse authenticate(LoginDetails loginDetails) throws NoSuchAlgorithmException, ReflectiveOperationException;
  
  /**
   * Change the password
 * @param userId
 * @return
 * @throws ChatakAdminException
 */
public Boolean changdPassword(Long userId,String currentPassword,String newPassword)throws ChatakAdminException;

/**
 * get User profile
 * @param userId
 * @return
 * @throws ChatakAdminException
 */
public UserProfileRequest getUserProfile(Long userId)throws ChatakAdminException;

/**
 * @param userProfileRequest
 * @throws ChatakAdminException
 */
public void changeUserProfile(UserProfileRequest userProfileRequest)throws ChatakAdminException;

/**
 * @param username
 * @param baseUrl
 * @return
 * @throws ChatakAdminException
 */
public Boolean userExist(String username,String baseUrl)throws ChatakAdminException;

/**
 * @param userId
 * @param token
 * @return
 * @throws ChatakAdminException
 */
public Boolean validToken(Long userId,final String token)throws ChatakAdminException;

/**
 * @param resetPasswordData
 * @param userId
 * @throws ChatakAdminException
 */
public void resetPassword(ResetPasswordData resetPasswordData,Long userId)throws ChatakAdminException;
}
