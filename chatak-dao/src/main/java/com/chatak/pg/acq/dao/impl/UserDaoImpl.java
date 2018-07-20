package com.chatak.pg.acq.dao.impl;

import java.sql.Timestamp;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.chatak.pg.acq.dao.UserDao;
import com.chatak.pg.acq.dao.model.PGUserAuthentication;
import com.chatak.pg.acq.dao.model.PGUserProfile;
import com.chatak.pg.acq.dao.repository.UserAuthenticationRepository;
import com.chatak.pg.acq.dao.repository.UserProfileRepository;
import com.chatak.pg.constants.ActionErrorCode;
import com.chatak.pg.constants.PGConstants;
import com.chatak.pg.user.bean.AuthenticateUserResponse;
import com.chatak.pg.user.bean.DeleteUserRequest;
import com.chatak.pg.user.bean.DeleteUserResponse;
import com.chatak.pg.user.bean.LoginUserRequest;
import com.chatak.pg.user.bean.Response;
import com.chatak.pg.user.bean.UpdateUserRequest;
import com.chatak.pg.user.bean.UpdateUserResponse;
import com.chatak.pg.util.EncryptionUtil;

@Repository("userDao")
public class UserDaoImpl implements UserDao {

  Logger logger = Logger.getLogger(this.getClass());

  @Autowired
  private UserProfileRepository userProfileRepository;

  @Autowired
  private UserAuthenticationRepository userAuthenticationRepository;

  public PGUserProfile createUser(PGUserProfile pgUserProfile) {
    logger.info("UserDaoImpl | createUser | Entering");
    PGUserProfile pgUserProfile2 = userProfileRepository.save(pgUserProfile);
    logger.info("UserDaoImpl | createUser | Exiting");
    return pgUserProfile2;
  }

  public void activateUser(PGUserProfile pgUserProfile) {
    logger.info("UserDaoImpl | activateUser | Entering");
    userProfileRepository.save(pgUserProfile);
    logger.info("UserDaoImpl | activateUser | Exiting");
  }


  public PGUserProfile loginUser(LoginUserRequest request) {
    logger.info("UserDaoImpl | loginUser | Entering");

    List<PGUserProfile> userProfileList = null;
    try {
      userProfileList = userProfileRepository.findByEmailAndPassword(request.getUser_name(),
          EncryptionUtil.encrypt(request.getPassword()));
    } catch (Exception e) {
      logger.error("UserDaoImpl | loginUser | Exception", e);
    }

    logger.info("UserDaoImpl | loginUser | Exiting");
    return userProfileList != null ? userProfileList.get(0) : null;
  }

  public boolean createAuthUser(Long profileId, String clientIp, String token) {
    boolean flag = false;
    try {
      PGUserAuthentication pgUserAuthentication = new PGUserAuthentication();
      List<PGUserAuthentication> pgUserAuthentications =
          userAuthenticationRepository.findByProfileId(profileId);

      if (pgUserAuthentications != null) {
        pgUserAuthentication.setId(pgUserAuthentications.get(0).getId());
      }
      pgUserAuthentication.setClientIP(clientIp);
      pgUserAuthentication.setToken(token);
      pgUserAuthentication.setProfileId(profileId);
      userAuthenticationRepository.save(pgUserAuthentication);
      flag = true;
    } catch (Exception e) {
      logger.error("TransactionDaoImpl | createAuthUser | Exception " + e.getMessage(), e);
    }

    return flag;
  }

  public AuthenticateUserResponse authenticateUser(String token, String clientIp) {
    logger.info("UserDaoImpl | authenticateUser | Entering");

    AuthenticateUserResponse response = new AuthenticateUserResponse();
    try {
      List<PGUserAuthentication> userAuthentications =
          userAuthenticationRepository.findByTokenAndClientIP(token, clientIp);

      if (userAuthentications != null) {
        logger.info("UserDaoImpl | authenticateUser | user login successfull");
        response.setErrorCode(ActionErrorCode.ERROR_CODE_00);
        response.setErrorMessage(
            ActionErrorCode.getInstance().getMessage(ActionErrorCode.ERROR_CODE_00));
      } else {
        logger.info("UserDaoImpl | authenticateUser | Authentication failed");
        response.setErrorCode(ActionErrorCode.ERROR_CODE_Z5);
        response.setErrorMessage(PGConstants.AUTHENTICATION_FAILED);
      }

    } catch (Exception e) {
      logger.error("TransactionDaoImpl | authenticateUser | Exception " + e.getMessage(), e);
      response.setErrorCode(ActionErrorCode.ERROR_CODE_Z5);
      response
          .setErrorMessage(ActionErrorCode.getInstance().getMessage(ActionErrorCode.ERROR_CODE_Z5));
    }
    logger.info("UserDaoImpl | authenticateUser | Exiting");
    return response;
  }

  public Response isValidUser(Long profileId) {
    logger.info("UserDaoImpl | isValidUser| Entering");
    Response response = new Response();

    List<PGUserProfile> pgUserProfileList = userProfileRepository.findByProfileId(profileId);

    if (pgUserProfileList != null) {
      logger.info("UserDaoImpl | isValidUser| User Record found for store Id:" + profileId);
      response.setErrorCode(ActionErrorCode.ERROR_CODE_00);
      response
          .setErrorMessage(ActionErrorCode.getInstance().getMessage(ActionErrorCode.ERROR_CODE_00));
    } else {
      logger.info("UserDaoImpl | isValidUser| No User Record found for store Id:" + profileId);
      response.setErrorCode(ActionErrorCode.ERROR_CODE_Z5);
      response.setErrorMessage(PGConstants.INVALID_MERCHANT_ID);
    }

    logger.info("MerchantDaoImpl | isValidMerchant | Exiting");
    return response;
  }

  public UpdateUserResponse updateUser(UpdateUserRequest updateUserRequest) {
    UpdateUserResponse updateUserResponse = new UpdateUserResponse();
    try {
      //check for valid merchant
      Response response = isValidUser(updateUserRequest.getProfile_id());
      if (!response.getErrorCode().equals(ActionErrorCode.ERROR_CODE_00)) {
        updateUserResponse.setErrorCode(response.getErrorCode());
        updateUserResponse.setErrorMessage(PGConstants.INVALID_USER_FOUND);
      } else {

        Timestamp currentDate = new Timestamp(System.currentTimeMillis());

        PGUserProfile pgUserProfile = new PGUserProfile();
        pgUserProfile.setProfileId(updateUserRequest.getProfile_id());
        pgUserProfile.setFirstName(updateUserRequest.getFirst_name());
        pgUserProfile.setMiddleName(updateUserRequest.getMiddle_name());
        pgUserProfile.setLastName(updateUserRequest.getLast_name());
        pgUserProfile.setPhone1(updateUserRequest.getPhone1());
        pgUserProfile.setPhone2(updateUserRequest.getPhone2());
        pgUserProfile.setAddress1(updateUserRequest.getAddress1());
        pgUserProfile.setAddress2(updateUserRequest.getAddress2());
        pgUserProfile.setCity(updateUserRequest.getCity());
        pgUserProfile.setState(updateUserRequest.getState());
        pgUserProfile.setZip(updateUserRequest.getZip());
        pgUserProfile.setUpdatedDate(currentDate);
        pgUserProfile.setStatus(PGConstants.STATUS_SUCCESS);

        userProfileRepository.save(pgUserProfile);

        logger.info("UserDaoImpl | updateUser | User details updated successfully");
        updateUserResponse.setErrorCode(ActionErrorCode.ERROR_CODE_00);
        updateUserResponse.setErrorMessage(
            ActionErrorCode.getInstance().getMessage(ActionErrorCode.ERROR_CODE_00));
      }
    } catch (Exception e) {
      logger.error("ERROR:: UserDaoImpl:: updateUser method", e);
      updateUserResponse.setErrorCode(ActionErrorCode.ERROR_CODE_Z5);
      updateUserResponse
          .setErrorMessage(ActionErrorCode.getInstance().getMessage(ActionErrorCode.ERROR_CODE_Z5));
    }

    return updateUserResponse;
  }

  public DeleteUserResponse deleteUser(DeleteUserRequest deleteUserRequest) {
    DeleteUserResponse deleteUserResponse = new DeleteUserResponse();

    try {

      //check for valid merchant
      Response response = isValidUser(deleteUserRequest.getProfile_id());
      if (!response.getErrorCode().equals(ActionErrorCode.ERROR_CODE_00)) {
        deleteUserResponse.setErrorCode(response.getErrorCode());
        deleteUserResponse.setErrorMessage(PGConstants.INVALID_MERCHANT_ID);
      } else {

        Timestamp currentDate = new Timestamp(System.currentTimeMillis());

        PGUserProfile pgUserProfile = new PGUserProfile();
        pgUserProfile.setProfileId(deleteUserRequest.getProfile_id());

        pgUserProfile.setUpdatedDate(currentDate);
        pgUserProfile.setStatus(PGConstants.STATUS_DELETED);

        userProfileRepository.save(pgUserProfile);

        logger.info("UserDaoImpl | deleteUser | User details soft delete success");
        deleteUserResponse.setErrorCode(ActionErrorCode.ERROR_CODE_00);
        deleteUserResponse.setErrorMessage(
            ActionErrorCode.getInstance().getMessage(ActionErrorCode.ERROR_CODE_00));

      }
    } catch (Exception e) {
      logger.error("UserDaoImpl | deleteUser | Exception" + e);
      deleteUserResponse.setErrorCode(ActionErrorCode.ERROR_CODE_Z5);
      deleteUserResponse
          .setErrorMessage(ActionErrorCode.getInstance().getMessage(ActionErrorCode.ERROR_CODE_Z5));
    }

    logger.info("UserDaoImpl | deleteUser | Exiting");
    return deleteUserResponse;
  }
}
