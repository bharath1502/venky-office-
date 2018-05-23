package com.chatak.merchant.service;
import com.chatak.merchant.controller.model.LoginDetails;
import com.chatak.merchant.controller.model.LoginResponse;
import com.chatak.merchant.controller.model.ResetPasswordData;
import com.chatak.merchant.exception.ChatakMerchantException;
import com.chatak.merchant.model.UserProfileRequest;

public interface LoginService {

	  /**
	   * Service method to authenticate the user
	   * 
	   * @param loginDetails
	   * @return
	   * @throws ChatakMerchantException
	   */
	  public LoginResponse authenticate(LoginDetails loginDetails) throws ChatakMerchantException;
	  
	  /**
	   * Change the password
	 * @param userId
	 * @return
	 * @throws ChatakMerchantException
	 */
	public Boolean changdPassword(Long userId,String currentPassword,String newPassword)throws ChatakMerchantException;

	/**
	 * get User profile
	 * @param userId
	 * @return
	 * @throws ChatakMerchantException
	 */
	public UserProfileRequest getUserProfile(Long userId)throws ChatakMerchantException;

	/**
	 * @param userProfileRequest
	 * @throws ChatakMerchantException
	 */
	public void changeUserProfile(UserProfileRequest userProfileRequest)throws ChatakMerchantException;

	/**
	 * @param username
	 * @return
	 * @throws ChatakMerchantException
	 */
	public Boolean userExist(String username,String baseUrl)throws ChatakMerchantException;

	/**
	 * @param userId
	 * @param token
	 * @return
	 * @throws ChatakMerchantException
	 */
	public Boolean validToken(Long userId,final String token)throws ChatakMerchantException;

	/**
	 * @param resetPasswordData
	 * @param userId
	 * @throws ChatakMerchantException
	 */
	public void resetPassword(ResetPasswordData resetPasswordData,Long userId)throws ChatakMerchantException;
	
}
