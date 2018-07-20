package com.chatak.merchant.service.impl;

import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.chatak.mailsender.exception.PrepaidNotificationException;
import com.chatak.mailsender.service.MailServiceManagement;
import com.chatak.merchant.controller.model.LoginDetails;
import com.chatak.merchant.controller.model.LoginResponse;
import com.chatak.merchant.controller.model.ResetPasswordData;
import com.chatak.merchant.exception.ChatakMerchantException;
import com.chatak.merchant.model.UserProfileRequest;
import com.chatak.merchant.service.LoginService;
import com.chatak.merchant.util.EncryptionUtil;
import com.chatak.merchant.util.PasswordHandler;
import com.chatak.merchant.util.StringUtil;
import com.chatak.pg.acq.dao.MerchantProfileDao;
import com.chatak.pg.acq.dao.MerchantUserDao;
import com.chatak.pg.acq.dao.RoleDao;
import com.chatak.pg.acq.dao.model.PGMerchant;
import com.chatak.pg.acq.dao.model.PGMerchantUsers;
import com.chatak.pg.acq.dao.model.PGRolesFeatureMapping;
import com.chatak.pg.acq.dao.model.PGUserRoles;
import com.chatak.pg.acq.dao.repository.MerchantRepository;
import com.chatak.pg.constants.PGConstants;
import com.chatak.pg.model.RolesFeatureMappingDTO;
import com.chatak.pg.util.Constants;
import com.chatak.pg.util.Properties;
import com.chatak.prepaid.velocity.IVelocityTemplateCreator;

/**
 * << Add Comments Here >>
 *
 * @author Girmiti Software
 * @date 16-Mar-2015 6:46:51 PM
 * @version 1.0
 */
@Service("loginService")
public class LoginServiceImpl implements LoginService {

  private static Logger logger = Logger.getLogger(LoginServiceImpl.class);

  @Autowired
  private MerchantUserDao merchantUserDao;

  @Autowired
  private MailServiceManagement mailingServiceManagement;

  @Autowired
  private IVelocityTemplateCreator iVelocityTemplateCreator;

  @Autowired
  private MerchantRepository merchantRepository;

  @Autowired
  private MessageSource messageSource;

  @Autowired
  RoleDao roleDao;

  @Autowired
  MerchantProfileDao merchantProfileDao;

  /**
   * Service method to authenticate the user
   * 
   * @param loginDetails
   * @return
   * @throws ChatakMerchantException
   */
  public LoginResponse authenticate(LoginDetails loginDetails) throws ChatakMerchantException {

    logger.info("Entering :: LoginServiceImpl :: authenticate method");

    int passRetryCount = 0;
    try {

      LoginResponse loginResponse = new LoginResponse();

      PGMerchantUsers merchantUsers = merchantUserDao.findByUserName(loginDetails.getAcqU());

      // Check for password retry counts
      if (merchantUsers != null) {

        passRetryCount = merchantUsers.getPassRetryCount();
        if (passRetryCount < Constants.PASS_RETRY_COUNT) {
          loginResponse.setLastLonginTime(merchantUsers.getLastLonginTime());
          usersEncodePassword(loginDetails, passRetryCount, loginResponse, merchantUsers);
        } else {
          loginResponse.setStatus(false);
          loginResponse.setMessage(messageSource.getMessage(
              "chatak.merchant.login.locked.error.message", null, LocaleContextHolder.getLocale()));
          loginResponse.setErrorCode(Constants.ERROR_CODE);
        }

      } else {
        loginResponse.setErrorCode(Constants.ERROR_CODE);
        loginResponse.setMessage(messageSource.getMessage("chatak.merchant.login.error.message",
            null, LocaleContextHolder.getLocale()));
      }

      return loginResponse;
    } catch (DataAccessException e) {
      logger.error("Error :: LoginServiceImpl :: authenticate method", e);
    } catch (Exception e) {
      logger.error("Error :: LoginServiceImpl :: authenticate method Exception", e);
    }

    return null;
  }

  private void usersEncodePassword(LoginDetails loginDetails, int passRetryCount,
      LoginResponse loginResponse, PGMerchantUsers merchantUsers)
      throws InstantiationException, IllegalAccessException, NoSuchAlgorithmException {
    if (EncryptionUtil.encodePassword(loginDetails.getAcqP())
        .equals(merchantUsers.getMerPassword())) {
      merchantUsers.setPassRetryCount(Constants.ACTIVE_STATUS);
      merchantUsers.setUpdatedDate(new Timestamp(System.currentTimeMillis()));
      merchantUsers.setLastLonginTime(loginDetails.getCurrentLoginTime());
      merchantUserDao.createOrUpdateUser(merchantUsers);

      // Process existing user
      if (merchantUsers.getStatus() == PGConstants.STATUS_SUCCESS) {

        merchantEmailVerified(loginResponse, merchantUsers);
      } else {
        loginResponse.setStatus(false);
        loginResponse.setErrorCode(Constants.ERROR_CODE);
        loginResponse.setMessage(
            messageSource.getMessage("chatak.merchant.user.inactive.error.message", null,
                LocaleContextHolder.getLocale()));
      }

      // End new user check

    } else {
      merchantUsersPassRetryCount(passRetryCount, loginResponse, merchantUsers);
    }
  }

  private void merchantUsersPassRetryCount(int passRetryCount, LoginResponse loginResponse,
      PGMerchantUsers merchantUsers) {
    if (passRetryCount != Constants.PASS_RETRY_COUNT) {
      merchantUsers.setPassRetryCount(passRetryCount + 1);
      loginResponse.setStatus(false);
      int retryCount = (Constants.PASS_RETRY_COUNT - merchantUsers.getPassRetryCount());
      loginResponse.setErrorCode("401");
      loginResponse.setMessage(
          messageSource.getMessage("chatak.merchant.login.password.count.error.message",
              null, LocaleContextHolder.getLocale()) + " You have " + retryCount
          + " attempt(s) left.");
      if (passRetryCount + 1 == Constants.PASS_RETRY_COUNT) {
        merchantUsers.setStatus(PGConstants.STATUS_INACTIVE);
        merchantUsers.setReason(PGConstants.USER_LOCKED_ERROR_MSG_FOR_REASON);
        loginResponse.setMessage(
            messageSource.getMessage("chatak.merchant.login.locked.error.message", null,
                LocaleContextHolder.getLocale()));
      }
      merchantUsers.setUpdatedDate(new Timestamp(System.currentTimeMillis()));
      merchantUserDao.createOrUpdateUser(merchantUsers);
    }
  }

  private void merchantEmailVerified(LoginResponse loginResponse, PGMerchantUsers merchantUsers)
      throws InstantiationException, IllegalAccessException {
    // Check if new user
    if (merchantUsers.getEmailVerified().equals(Constants.ONE)) {

      PGUserRoles role = roleDao.findByUserRoleId(merchantUsers.getUserRoleId());
      if (role.getStatus() == 0) {
        long currentPassCreateOrModifiedDateTimeMillis = 0;
        if (null != merchantUsers.getLastPassWordChange()) {
          currentPassCreateOrModifiedDateTimeMillis =
              merchantUsers.getLastPassWordChange().getTime();
        } else {
          currentPassCreateOrModifiedDateTimeMillis =
              merchantUsers.getCreatedDate().getTime();
        }
        long currDateTimeMillis = new Timestamp(System.currentTimeMillis()).getTime();
        long currentPassAgeTimeMillis =
            currDateTimeMillis - currentPassCreateOrModifiedDateTimeMillis;

        if ((Constants.PASS_EXPIRATION_TIME_DAYS
            * Constants.DAY_TO_MILLISECONDS) > currentPassAgeTimeMillis) {

          loginResponseStatus(loginResponse, merchantUsers);

        } else {
          loginResponse.setStatus(false);
          loginResponse.setUserId(Long.valueOf(merchantUsers.getId()));
          loginResponse.setErrorCode(Constants.SUCCESS_CODE);
          loginResponse.setErrorMessage(Properties
              .getProperty("chatak.merchant.login.password.expiration.error.message"));
        }

      } else {
        loginResponse.setStatus(false);
        loginResponse.setMessage("Role is Inactive or Deleted");
      }

    } else if (merchantUsers.getEmailVerified().equals(Constants.ZERO)) {
      loginResponse.setUserType(PGConstants.NEW_USER);
      loginResponse.setUserId(merchantUsers.getId());
    } else {
      loginResponse.setMessage(messageSource.getMessage(
          "chatak.merchant.login.error.message", null, LocaleContextHolder.getLocale()));
    }
  }

  private void loginResponseStatus(LoginResponse loginResponse, PGMerchantUsers merchantUsers)
      throws InstantiationException, IllegalAccessException {
    loginResponse.setStatus(Boolean.TRUE);
    loginResponse.setEmail(merchantUsers.getEmail());
    List<PGMerchant> pgMerchant =
        merchantUserDao.getMerchant(Long.valueOf(merchantUsers.getId()));
    loginResponse.setUserId(Long.valueOf(merchantUsers.getId()));
    loginResponse.setUserRoleId(
        null != merchantUsers.getUserRoleId() ? merchantUsers.getUserRoleId() : 0L);
    loginResponse.setUserType(merchantUsers.getUserRoleType());
    loginResponse.setMerchantCode(pgMerchant.get(0).getMerchantCode());
    // FeatureList from database
    List<String> featureIdList = new ArrayList<>();
    List<PGRolesFeatureMapping> pgRoleFeatureMap =
        roleDao.findByRoleId(merchantUsers.getUserRoleId());
    List<RolesFeatureMappingDTO> roleFeatureMap = null;
    roleFeatureMap = com.chatak.merchant.util.CommonUtil
        .copyListBeanProperty(pgRoleFeatureMap, RolesFeatureMappingDTO.class);
    for (RolesFeatureMappingDTO portalRoleFeatureMap : roleFeatureMap) {
      featureIdList.add(String.valueOf(portalRoleFeatureMap.getFeatureId()));
    }
    loginResponse.setExistingFeature(featureIdList);
    loginResponse.setParentMerchantId(pgMerchant.get(0).getParentMerchantId());
    loginResponse.setUserMerchantId(Long.valueOf(merchantUsers.getPgMerchantId()));
    loginResponse.setUserName(merchantUsers.getUserName());
  }

  /**
   * @param userId
   * @param currentPassword
   * @param newPassword
   * @return
   * @throws ChatakMerchantException
   */
  @Override
  public Boolean changdPassword(Long userId, String currentPassword, String newPassword)
      throws ChatakMerchantException {
    try {
      PGMerchantUsers pgMerchantUsers = merchantUserDao.findByMerchantUserId(userId);
      if (!(EncryptionUtil.encodePassword(currentPassword))
          .equals(pgMerchantUsers.getMerPassword()))
        throw new ChatakMerchantException(messageSource.getMessage("current.password.error.message",
            null, LocaleContextHolder.getLocale()));
      PasswordHandler passwordHandler = new PasswordHandler();
      if (!passwordHandler.validate(newPassword))
        throw new ChatakMerchantException(messageSource.getMessage("password.policy.format", null,
            LocaleContextHolder.getLocale()));
      String encryptionPassword = EncryptionUtil.encodePassword(newPassword);
      checkPreviousPassword(pgMerchantUsers, encryptionPassword,
          Integer.parseInt(messageSource.getMessage("merchant.user.previous.password.count", null,
              LocaleContextHolder.getLocale())));
      if (pgMerchantUsers.getEmailVerified().equals(Constants.ZERO)
          || pgMerchantUsers.getStatus().equals(1)) {
        pgMerchantUsers.setEmailVerified(PGConstants.ONE.intValue());
        pgMerchantUsers.setStatus(PGConstants.ZERO.intValue());
      }
      pgMerchantUsers.setMerPassword(encryptionPassword);
      pgMerchantUsers.setLastPassWordChange(new Timestamp(System.currentTimeMillis()));
      merchantUserDao.createOrUpdateUser(pgMerchantUsers);
      return true;

    } catch (ChatakMerchantException e) {
      logger.error("Error :: LoginServiceImpl :: changdPassword", e);
      throw new ChatakMerchantException(e.getMessage());
    } catch (Exception e) {
      logger.error("Error :: LoginServiceImpl :: changdPassword - Exception", e);
      throw new ChatakMerchantException(Properties.getProperty(Constants.CHATAK_NORMAL_ERROR_MESSAGE));
    }
  }

  /**
   * @param userId
   * @return
   * @throws ChatakMerchantException
   */

  @Override
  public UserProfileRequest getUserProfile(Long userId) throws ChatakMerchantException {
    UserProfileRequest userProfileRequest = new UserProfileRequest();
    try {

      PGMerchantUsers pgMerchantUsers = merchantUserDao.findByMerchantUserId(userId);

      if (pgMerchantUsers != null) {
        PGMerchant pgMerchant =
            merchantProfileDao.getMerchantById(Long.valueOf(pgMerchantUsers.getPgMerchantId()));
        userProfileRequest.setEmailId(pgMerchantUsers.getEmail());
        userProfileRequest.setFirstName(pgMerchantUsers.getFirstName());
        userProfileRequest.setLastName(pgMerchantUsers.getLastName());
        userProfileRequest.setPhone(pgMerchantUsers.getPhone());
        userProfileRequest.setUserId(pgMerchantUsers.getId());
        userProfileRequest.setMerchantUserId(pgMerchant.getPgMerchantUsers().get(0).getId());
        userProfileRequest.setMerchantId(pgMerchantUsers.getPgMerchantId());
        userProfileRequest.setUserName(pgMerchantUsers.getUserName());
        userProfileRequest.setMailAddress1(pgMerchant.getAddress1());
      }
    } catch (Exception e) {
      logger.error("Error :: LoginServiceImpl :: getUserProfile", e);
      throw new ChatakMerchantException(Properties.getProperty(Constants.CHATAK_NORMAL_ERROR_MESSAGE));

    }
    return userProfileRequest;
  }

  /**
   * @param userProfileRequest
   * @throws ChatakMerchantException
   */
  @Override
  public void changeUserProfile(UserProfileRequest userProfileRequest)
      throws ChatakMerchantException {
    try {

      PGMerchantUsers pgMerchantUsers =
          merchantUserDao.findByMerchantUserId(userProfileRequest.getUserId());

      if (pgMerchantUsers != null) {
        processUpdateUserName(userProfileRequest, pgMerchantUsers);
        pgMerchantUsers.setUserName(userProfileRequest.getUserName());
        pgMerchantUsers.setFirstName(userProfileRequest.getFirstName());
        pgMerchantUsers.setLastName(userProfileRequest.getLastName());
        pgMerchantUsers.setPhone(userProfileRequest.getPhone());
        pgMerchantUsers.setEmail(userProfileRequest.getEmailId());
        merchantUserDao.createOrUpdateUser(pgMerchantUsers);
      }
    } catch (Exception e) {
      logger.error("Error :: LoginServiceImpl :: changeUserProfile", e);
      throw new ChatakMerchantException(Properties.getProperty(Constants.CHATAK_NORMAL_ERROR_MESSAGE));

    }

  }

  private void processUpdateUserName(UserProfileRequest userProfileRequest,
      PGMerchantUsers pgMerchantUsers) {
    if (pgMerchantUsers.getId().equals(Long.valueOf(pgMerchantUsers.getPgMerchantId()))) {
      PGMerchant pgMerchant = merchantProfileDao.getMerchantById(pgMerchantUsers.getId());
      if (null != pgMerchant) {
        pgMerchant.setUserName(userProfileRequest.getUserName());
        merchantRepository.save(pgMerchant);
      }
    }
  }

  /**
   * @param username
   * @return
   * @throws ChatakMerchantException
   */
  @Override
  public Boolean userExist(String userName, String baseUrl) throws ChatakMerchantException {

    PGMerchantUsers pgMerchantUsers = merchantUserDao.findByUserName(userName);
    if (pgMerchantUsers == null)
      throw new ChatakMerchantException(messageSource.getMessage(
          "chatak.merchant.user.not.exist.error.message", null, LocaleContextHolder.getLocale()));

    if (pgMerchantUsers.getStatus().equals(0)) {
      String emailToken =
          StringUtil.getEmailToken(pgMerchantUsers.getId().toString(), pgMerchantUsers.getEmail());
      pgMerchantUsers.setEmailToken(emailToken);
      pgMerchantUsers.setEmailVerified(0);
      merchantUserDao.createOrUpdateUser(pgMerchantUsers);
      String url =
          baseUrl + Properties.getProperty("chatak.merchant.forgot.password.redirection.url")
              + "?t=" + emailToken;

      Map<String, String> map = new HashMap<>();
      map.put("url", url);
      map.put("username", pgMerchantUsers.getUserName());
      try {
        String body = iVelocityTemplateCreator.createEmailTemplate(map,
            Properties.getProperty("prepaid.email.template.file.path")
                + "/merchant_forgot_password.vm");
        mailingServiceManagement.sendMailHtml(Properties.getProperty("prepaid.from.email.id"), body,
            pgMerchantUsers.getEmail(),
            messageSource.getMessage("prepaid.email.merchant.forgot.password.subject", null,
                LocaleContextHolder.getLocale()));
      } catch (PrepaidNotificationException e) {
        logger.error("Error :: LoginServiceImpl :: userExist", e);
        throw new ChatakMerchantException(messageSource.getMessage(
            "chatak.merchant.user.inactive.error.message", null, LocaleContextHolder.getLocale()));
      } catch (Exception e) {
        logger.error("Error :: LoginServiceImpl :: userExist - Exception", e);
        throw new ChatakMerchantException(e.getMessage());
      }
      logger.info("");
      logger.info("Token URL is " + url);
      logger.info("");
      return true;
    } else {
      throw new ChatakMerchantException(messageSource.getMessage(
          "chatak.merchant.user.inactive.error.message", null, LocaleContextHolder.getLocale()));
    }
  }

  /**
   * @param userId
   * @param token
   * @return
   * @throws ChatakMerchantException
   */
  @Override
  public Boolean validToken(Long userId, final String token) throws ChatakMerchantException {

    PGMerchantUsers pgMerchantUsers = merchantUserDao.findByMerchantUserId(userId);
    pgMerchantUsers =
        merchantUserDao.findByAdminUserIdAndEmailToken(pgMerchantUsers.getId(), token);
    return (pgMerchantUsers != null && pgMerchantUsers.getEmailVerified().equals(Constants.ZERO))
        ? true : false;
  }

  @Override
  public void resetPassword(ResetPasswordData resetPasswordData, Long userId)
      throws ChatakMerchantException {

    try {

      PGMerchantUsers pgMerchantUsers = merchantUserDao.findByMerchantUserId(userId);
      PasswordHandler passwordHandler = new PasswordHandler();
      if (pgMerchantUsers.getEmailVerified().equals(Constants.ZERO)) {
        if (!passwordHandler.validate(resetPasswordData.getNewPassword()))
          throw new ChatakMerchantException(messageSource.getMessage("password.policy.format", null,
              LocaleContextHolder.getLocale()));
        String encryptionPassword =
            EncryptionUtil.encodePassword(resetPasswordData.getNewPassword());
        checkPreviousPassword(pgMerchantUsers, encryptionPassword,
            Integer.parseInt(messageSource.getMessage("merchant.user.previous.password.count", null,
                LocaleContextHolder.getLocale())));
        pgMerchantUsers.setEmailVerified(Constants.ONE);
        pgMerchantUsers.setMerPassword(encryptionPassword);
        pgMerchantUsers.setLastPassWordChange(new Timestamp(System.currentTimeMillis()));
        merchantUserDao.createOrUpdateUser(pgMerchantUsers);

      } else {
        throw new ChatakMerchantException(messageSource.getMessage(
            "Password.already.has.been.changed", null, LocaleContextHolder.getLocale()));
      }
    } catch (ChatakMerchantException e) {
      logger.error("Error :: LoginServiceImpl :: resetPassword", e);
      throw new ChatakMerchantException(e.getMessage());
    } catch (Exception e) {
      logger.error("Error :: LoginServiceImpl :: resetPassword - Exception", e);
      throw new ChatakMerchantException(Properties.getProperty(Constants.CHATAK_NORMAL_ERROR_MESSAGE));
    }
  }

  private PGMerchantUsers checkPreviousPassword(PGMerchantUsers pgMerchantUsers, String newPassword,
      Integer count) throws ChatakMerchantException {
    if (StringUtil.isNullAndEmpty(pgMerchantUsers.getPreviousPasswords())) {

      pgMerchantMessageSource(pgMerchantUsers, newPassword);
    } else if (pgMerchantUsers.getPreviousPasswords()
        .contains(Constants.SAPARETOR + newPassword + Constants.SAPARETOR)) {
      throw new ChatakMerchantException(messageSource.getMessage(
          "chatak.previous.password.error.message", null, LocaleContextHolder.getLocale()));
    } else {
      String[] passwordData = pgMerchantUsers.getPreviousPasswords()
          .substring(1, pgMerchantUsers.getPreviousPasswords().length()).trim()
          .split("\\" + Constants.SAPARETOR);
      if (passwordData.length < count.intValue()) {
        pgMerchantUsers.setPreviousPasswords(
            Constants.SAPARETOR + newPassword + pgMerchantUsers.getPreviousPasswords());
      } else {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < passwordData.length - 1; i++) {
          sb.append((i == 0) ? Constants.SAPARETOR + passwordData[i] + Constants.SAPARETOR
              : passwordData[i] + Constants.SAPARETOR);
        }
        pgMerchantUsers.setPreviousPasswords(Constants.SAPARETOR + newPassword + sb.toString());
      }
    }
    return pgMerchantUsers;
  }

  private void pgMerchantMessageSource(PGMerchantUsers pgMerchantUsers, String newPassword)
      throws ChatakMerchantException {
    if (pgMerchantUsers.getMerPassword().equals(newPassword)) {
      throw new ChatakMerchantException(
          messageSource.getMessage("chatak.current.new.password.equal.error.message", null,
              LocaleContextHolder.getLocale()));
    } else {
      pgMerchantUsers.setPreviousPasswords(Constants.SAPARETOR + newPassword + Constants.SAPARETOR
          + pgMerchantUsers.getMerPassword() + Constants.SAPARETOR);
    }
  }
}
