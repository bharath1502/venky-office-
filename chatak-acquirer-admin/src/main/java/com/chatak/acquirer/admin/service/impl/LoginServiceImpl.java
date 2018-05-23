/**
 * 
 */
package com.chatak.acquirer.admin.service.impl;

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
import org.springframework.stereotype.Service;

import com.chatak.acquirer.admin.controller.model.LoginDetails;
import com.chatak.acquirer.admin.controller.model.LoginResponse;
import com.chatak.acquirer.admin.controller.model.ResetPasswordData;
import com.chatak.acquirer.admin.exception.ChatakAdminException;
import com.chatak.acquirer.admin.model.UserProfileRequest;
import com.chatak.acquirer.admin.service.LoginService;
import com.chatak.acquirer.admin.util.CommonUtil;
import com.chatak.acquirer.admin.util.EncryptionUtil;
import com.chatak.acquirer.admin.util.PasswordHandler;
import com.chatak.acquirer.admin.util.StringUtil;
import com.chatak.mailsender.exception.PrepaidNotificationException;
import com.chatak.mailsender.service.MailServiceManagement;
import com.chatak.pg.acq.dao.AdminUserDao;
import com.chatak.pg.acq.dao.RoleDao;
import com.chatak.pg.acq.dao.UserActivityLogDao;
import com.chatak.pg.acq.dao.UsersRoleDao;
import com.chatak.pg.acq.dao.model.PGAdminUser;
import com.chatak.pg.acq.dao.model.PGRolesFeatureMapping;
import com.chatak.pg.acq.dao.model.PGUserRoles;
import com.chatak.pg.constants.PGConstants;
import com.chatak.pg.model.RolesFeatureMappingDTO;
import com.chatak.pg.util.Constants;
import com.chatak.pg.util.Properties;
import com.chatak.prepaid.velocity.IVelocityTemplateCreator;

/**
 * << Add Comments Here >>
 * 
 * @author Girmiti Software
 * @date 03-Jan-2015 11:00:27 AM
 * @version 1.0
 */
@Service
public class LoginServiceImpl implements LoginService {

  private static Logger logger = Logger.getLogger(LoginServiceImpl.class);
	
  @Autowired
  AdminUserDao adminUserDao;

  @Autowired
  UsersRoleDao usersRoleDao;

  @Autowired
  RoleDao roleDao;

  @Autowired
  MailServiceManagement mailingServiceManagement;

  @Autowired
  IVelocityTemplateCreator iVelocityTemplateCreator;

  @Autowired
  UserActivityLogDao userActivityLogDao;

  @Autowired
  MessageSource messageSource;

  /**
   * Service method to authenticate the user
   * 
   * @param loginDetails
   * @return
   * @throws ChatakAdminException
   */
  public LoginResponse authenticate(LoginDetails loginDetails) throws NoSuchAlgorithmException, ReflectiveOperationException {
    try {
      LoginResponse loginResponse = new LoginResponse();
      PGAdminUser adminUser = adminUserDao.findByUserNameAndUserType(loginDetails.getAcqU(), Constants.ADMIN_USER_TYPE);
      if (null != adminUser) {
        PGUserRoles role = roleDao.findByUserRoleId(adminUser.getUserRoleId());
        
        processLogin(loginDetails, loginResponse, adminUser, role);
      } else {
        loginResponse.setStatus(false);
        loginResponse.setMessage(messageSource.getMessage("admin.service.login.error.message", null,
            LocaleContextHolder.getLocale()));

      }
      loginResponse.setErrorCode(Constants.SUCESS);
      return loginResponse;
    } catch (Exception e) {
    	logger.error("Error :: LoginServiceImpl :: authenticate", e);
    }
    return null;
  }

	private void processLogin(LoginDetails loginDetails, LoginResponse loginResponse, PGAdminUser adminUser,
			PGUserRoles role) throws NoSuchAlgorithmException, ReflectiveOperationException {
		if (role.getStatus() == 0) {
			adminUser(loginDetails, loginResponse, adminUser);
		} else {
			loginResponse.setStatus(false);
			loginResponse.setMessage("Role is Inactive or Deleted");
		}
	}

	private void adminUser(LoginDetails loginDetails, LoginResponse loginResponse, PGAdminUser adminUser)
			throws NoSuchAlgorithmException, ReflectiveOperationException {
		int passRetryCount;
		if (adminUser.getStatus() != 1) {
			passRetryCount = adminUser.getPassRetryCount();
			if (passRetryCount < Constants.PASS_RETRY_COUNT) {
				if (EncryptionUtil.encodePassword(loginDetails.getAcqP()).equalsIgnoreCase(adminUser.getPassword())) {
					adminUser.setPassRetryCount(Constants.ACTIVE_STATUS);
					adminUser.setUpdatedDate(new Timestamp(System.currentTimeMillis()));
					adminUserDao.createOrUpdateUser(adminUser);
					// Check if new user
					setLoginResponse(loginResponse, adminUser);
				} else {
					setAdminUser(passRetryCount, loginResponse, adminUser);
				}
			} else {
				loginResponse.setStatus(false);
				loginResponse.setMessage(messageSource.getMessage("chatak.merchant.login.locked.error.message", null,
						LocaleContextHolder.getLocale()));
			}
		} else {
			loginResponse.setStatus(false);
			loginResponse.setMessage("User is Inactive or Deleted");
		}
	}

	private void setLoginResponse(LoginResponse loginResponse, PGAdminUser adminUser)
			throws InstantiationException, IllegalAccessException {
		if (adminUser != null && adminUser.getEmailVerified().equals(Constants.ONE)) {

			if (adminUser.getStatus() == PGConstants.STATUS_SUCCESS) {
				long currentPassCreateOrModifiedDateTimeMillis = 0;
				currentPassCreateOrModifiedDateTimeMillis = getAdminUser(adminUser);
				long currDateTimeMillis = new Timestamp(System.currentTimeMillis()).getTime();
				processLoginResponse(loginResponse, adminUser, currentPassCreateOrModifiedDateTimeMillis, currDateTimeMillis);
			} else {
				loginResponse.setStatus(false);
				loginResponse.setMessage(Properties.getProperty("admin.service.login.inactive.error.message"));
			}
		} else if (adminUser != null && adminUser.getEmailVerified().equals(Constants.ZERO)) {
			loginResponse.setUserType(PGConstants.NEW_USER);
			loginResponse.setUserId(adminUser.getAdminUserId());
		}
	}

	private void processLoginResponse(LoginResponse loginResponse, PGAdminUser adminUser,
			long currentPassCreateOrModifiedDateTimeMillis, long currDateTimeMillis)
					throws InstantiationException, IllegalAccessException {
		long currentPassAgeTimeMillis = currDateTimeMillis - currentPassCreateOrModifiedDateTimeMillis;
		if ((Constants.PASS_EXPIRATION_TIME_DAYS * Constants.DAY_TO_MILLISECONDS) > currentPassAgeTimeMillis) {
			loginResponse.setEmail(adminUser.getEmail());
			// FeatureList from database
			List<String> featureIdList = new ArrayList<String>();
			List<PGRolesFeatureMapping> pgRoleFeatureMap = roleDao.findByRoleId(adminUser.getUserRoleId());
			List<RolesFeatureMappingDTO> roleFeatureMap = null;
			roleFeatureMap = CommonUtil.copyListBeanProperty(pgRoleFeatureMap, RolesFeatureMappingDTO.class);
			setPortalRoleFeatureMap(featureIdList, roleFeatureMap);
			loginResponse.setExistingFeature(featureIdList);
			loginResponse.setMessage(Properties.getProperty("admin.service.login.sucess.message"));
			loginResponse.setStatus(true);
			loginResponse.setEmail(adminUser.getEmail());
			loginResponse.setUserId(adminUser.getAdminUserId());
			loginResponse.setUserRoleId(adminUser.getUserRoleId());
			loginResponse.setFirstName(adminUser.getFirstName());
			loginResponse.setLastName(adminUser.getLastName());
			loginResponse.setEmailVerified(adminUser.getEmailVerified());
			loginResponse.setUserType(adminUser.getUserType());
			loginResponse.setUserName(adminUser.getUserName());
		} else {
			loginResponse.setStatus(false);
			loginResponse.setUserId(adminUser.getAdminUserId());
			loginResponse
					.setErrorMessage(Properties.getProperty("admin.service.login.password.expiration.error.message"));
		}
	}

private long getAdminUser(PGAdminUser adminUser) {
	long currentPassCreateOrModifiedDateTimeMillis;
	if (null != adminUser.getLastPassWordChange()) {
	  currentPassCreateOrModifiedDateTimeMillis =
	      adminUser.getLastPassWordChange().getTime();
	} 
	else {
	  currentPassCreateOrModifiedDateTimeMillis =
	      adminUser.getCreatedDate().getTime();
	}
	return currentPassCreateOrModifiedDateTimeMillis;
}

private void setPortalRoleFeatureMap(List<String> featureIdList, List<RolesFeatureMappingDTO> roleFeatureMap) {
	for (RolesFeatureMappingDTO portalRoleFeatureMap : roleFeatureMap) {
	    featureIdList.add(String.valueOf(portalRoleFeatureMap.getFeatureId()));
	  }
}

private void setAdminUser(int passRetryCount, LoginResponse loginResponse, PGAdminUser adminUser) {
	if (passRetryCount != Constants.PASS_RETRY_COUNT) {
	  adminUser.setPassRetryCount(passRetryCount + 1);
	  loginResponse.setStatus(false);
	  int retryCount = (Constants.PASS_RETRY_COUNT - adminUser.getPassRetryCount());

	  loginResponse.setMessage(
	      Properties.getProperty("admin.service.login.password.count.error.message")
	          + " You have " + retryCount + " attempt(s) left.");
	  if (passRetryCount + 1 == Constants.PASS_RETRY_COUNT) {
	    adminUser.setStatus(PGConstants.STATUS_SUSPENDED);
	    adminUser.setReason(PGConstants.USER_LOCKED_ERROR_MSG_FOR_REASON);
	    loginResponse.setMessage(
	        messageSource.getMessage("admin.service.login.password.error.message", null,
	            LocaleContextHolder.getLocale()));
	  }
	  adminUser.setUpdatedDate(new Timestamp(System.currentTimeMillis()));
	  adminUserDao.createOrUpdateUser(adminUser);
	}
}

  @Override
  public Boolean changdPassword(Long userId, String currentPassword, String newPassword)
      throws ChatakAdminException {
    try {
      PGAdminUser adminUser = adminUserDao.findByAdminUserId(userId);
      if (!(EncryptionUtil.encodePassword(currentPassword)).equals(adminUser.getPassword()))
        throw new ChatakAdminException(messageSource.getMessage("current.password.error.message",
            null, LocaleContextHolder.getLocale()));
      PasswordHandler passwordHandler = new PasswordHandler();
      if (!passwordHandler.validate(newPassword))
        throw new ChatakAdminException(messageSource.getMessage("password.policy.format", null,
            LocaleContextHolder.getLocale()));
      String encryptionPassword = EncryptionUtil.encodePassword(newPassword);
      checkPreviousPassword(adminUser, encryptionPassword,
          Integer.parseInt(Properties.getProperty("admin.user.previous.password.count")));

      if (adminUser.getEmailVerified().equals(Constants.ZERO)
          || adminUser.getStatus().equals(Constants.ONE)) {
        adminUser.setEmailVerified(PGConstants.ONE.intValue());
        adminUser.setStatus(PGConstants.ZERO.intValue());
      }

      adminUser.setPassword(encryptionPassword);
      adminUser.setLastPassWordChange(new Timestamp(System.currentTimeMillis()));
      adminUserDao.createOrUpdateUser(adminUser);
      return true;

    } catch (ChatakAdminException e) {
    	logger.error("Error :: LoginServiceImpl :: changdPassword", e);
      throw new ChatakAdminException(e.getMessage());
    } catch (Exception e) {
    	logger.error("Error :: LoginServiceImpl :: changdPassword", e);
      throw new ChatakAdminException(messageSource.getMessage(Constants.CHATAK_NORMAL_ERROR_MESSAGE, null,
          LocaleContextHolder.getLocale()));
    }
  }

  private PGAdminUser checkPreviousPassword(PGAdminUser pgAdminUser, String newPassword,
      Integer count) throws ChatakAdminException {
    if (StringUtil.isNullAndEmpty(pgAdminUser.getPreviousPasswords())) {
    	verifyPassword(pgAdminUser, newPassword);
    } else if (pgAdminUser.getPreviousPasswords()
        .contains(Constants.SAPARETOR + newPassword + Constants.SAPARETOR)) {
      throw new ChatakAdminException(messageSource.getMessage(
          "chatak.previous.password.error.message", null, LocaleContextHolder.getLocale()));
    } else {
      String[] passwordData = pgAdminUser.getPreviousPasswords()
          .substring(1, pgAdminUser.getPreviousPasswords().length()).trim()
          .split("\\" + Constants.SAPARETOR);
      if (passwordData.length < count.intValue()) {
        pgAdminUser.setPreviousPasswords(
            Constants.SAPARETOR + newPassword + pgAdminUser.getPreviousPasswords());
      } else {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < passwordData.length - 1; i++) {
          sb.append((i == 0) ? Constants.SAPARETOR + passwordData[i] + Constants.SAPARETOR
              : passwordData[i] + Constants.SAPARETOR);
        }
        pgAdminUser.setPreviousPasswords(Constants.SAPARETOR + newPassword + sb.toString());
      }
    }
    return pgAdminUser;
  }

private void verifyPassword(PGAdminUser pgAdminUser, String newPassword) throws ChatakAdminException {
	if (pgAdminUser.getPassword().equals(newPassword)) {
        throw new ChatakAdminException(
            messageSource.getMessage("chatak.current.new.password.equal.error.message", null,
                LocaleContextHolder.getLocale()));
      } else {
        pgAdminUser.setPreviousPasswords(Constants.SAPARETOR + newPassword + Constants.SAPARETOR
            + pgAdminUser.getPassword() + Constants.SAPARETOR);
      }
}

  @Override
  public UserProfileRequest getUserProfile(Long userId) throws ChatakAdminException {
    UserProfileRequest userProfileRequest = new UserProfileRequest();
    try {
      PGAdminUser adminUser = adminUserDao.findByAdminUserId(userId);
      if (adminUser != null) {
        userProfileRequest.setEmailId(adminUser.getEmail());
        userProfileRequest.setFirstName(adminUser.getFirstName());
        userProfileRequest.setLastName(adminUser.getLastName());
        userProfileRequest.setPhone(adminUser.getPhone());
        userProfileRequest.setRoleId(adminUser.getUserRoleId());
        userProfileRequest.setUserId(adminUser.getAdminUserId());
        userProfileRequest.setUserName(adminUser.getUserName());
        PGUserRoles pgUserRoles = usersRoleDao.findByRoleId(adminUser.getUserRoleId());
        userProfileRequest.setRoleName(pgUserRoles.getRoleName());
      }
    } catch (Exception e) {
    	logger.error("Error :: LoginServiceImpl :: getUserProfile", e);
      throw new ChatakAdminException(Properties.getProperty(Constants.CHATAK_NORMAL_ERROR_MESSAGE));
    }
    return userProfileRequest;
  }

  @Override
  public void changeUserProfile(UserProfileRequest userProfileRequest) throws ChatakAdminException {
    try {
      PGAdminUser pgServiceAdminUser =
          adminUserDao.findByAdminUserId(userProfileRequest.getUserId());
      if (pgServiceAdminUser != null) {
        pgServiceAdminUser.setUserName(userProfileRequest.getUserName());
        pgServiceAdminUser.setFirstName(userProfileRequest.getFirstName());
        pgServiceAdminUser.setLastName(userProfileRequest.getLastName());
        pgServiceAdminUser.setPhone(userProfileRequest.getPhone());
        pgServiceAdminUser.setEmail(userProfileRequest.getEmailId());
        adminUserDao.createOrUpdateUser(pgServiceAdminUser);
      }
    } catch (Exception e) {
    	logger.error("Error :: LoginServiceImpl :: changeUserProfile", e);
      throw new ChatakAdminException(Properties.getProperty(Constants.CHATAK_NORMAL_ERROR_MESSAGE));
    }
  }

  @Override
  public Boolean userExist(String username, String baseUrl) throws ChatakAdminException {
    PGAdminUser pgAdminUser = adminUserDao.findByUserNameAndStatus(username);
    if (pgAdminUser == null)
      throw new ChatakAdminException(messageSource.getMessage(
          "chatak.admin.user.not.exist.error.message", null, LocaleContextHolder.getLocale()));
    else if (pgAdminUser.getStatus().equals(Constants.ZERO)) {
      String emailToken =
          StringUtil.getEmailToken(pgAdminUser.getAdminUserId().toString(), pgAdminUser.getEmail());
      pgAdminUser.setEmailToken(emailToken);
      pgAdminUser.setEmailVerified(0);
      adminUserDao.createOrUpdateUser(pgAdminUser);
      String url = baseUrl + Properties.getProperty("chatak.admin.forgot.password.redirection.url")
          + "?t=" + emailToken;
      Map<String, String> map = new HashMap<String, String>();
      map.put("url", url);
      map.put("username", username);
      try {
        String body = iVelocityTemplateCreator.createEmailTemplate(map,
            Properties.getProperty("prepaid.email.template.file.path")
                + "/admin_forgot_password.vm");
        mailingServiceManagement.sendMailHtml(Properties.getProperty("prepaid.from.email.id"), body,
            pgAdminUser.getEmail(),
            messageSource.getMessage("chatak.email.admin.forgot.password.subject", null,
                LocaleContextHolder.getLocale()));
      } catch (PrepaidNotificationException e) {
    	  logger.error("Error :: LoginServiceImpl :: userExist", e);
        throw new ChatakAdminException(messageSource.getMessage(
            "chatak.admin.user.inactive.error.message", null, LocaleContextHolder.getLocale()));
      } catch (Exception e) {
    	  logger.error("Error :: LoginServiceImpl :: userExist", e);
        throw new ChatakAdminException(e.getMessage());
      }
      logger.info("Url is " + url);
      return true;
    } else
      throw new ChatakAdminException(messageSource.getMessage(
          "chatak.admin.user.inactive.error.message", null, LocaleContextHolder.getLocale()));
  }

  @Override
  public Boolean validToken(Long userId, final String token) throws ChatakAdminException {
    PGAdminUser pgAdminUser = adminUserDao.findByAdminUserIdAndEmailToken(userId, token);
    return (pgAdminUser != null && pgAdminUser.getEmailVerified().equals(Constants.ZERO)) ? true
        : false;
  }

  @Override
  public void resetPassword(ResetPasswordData resetPasswordData, Long userId)
      throws ChatakAdminException {
    try {
      PGAdminUser adminUser = adminUserDao.findByAdminUserId(userId);
      PasswordHandler passwordHandler = new PasswordHandler();
      if (adminUser.getEmailVerified().equals(Constants.ZERO)) {
        if (!passwordHandler.validate(resetPasswordData.getNewPassword()))
          throw new ChatakAdminException(messageSource.getMessage("password.policy.format", null,
              LocaleContextHolder.getLocale()));
        String encryptionPassword =
            EncryptionUtil.encodePassword(resetPasswordData.getNewPassword());
        checkPreviousPassword(adminUser, encryptionPassword,
            Integer.parseInt(Properties.getProperty("admin.user.previous.password.count")));
        adminUser.setEmailVerified(Constants.ONE);
        adminUser.setPassword(encryptionPassword);
        adminUser.setLastPassWordChange(new Timestamp(System.currentTimeMillis()));
        adminUserDao.createOrUpdateUser(adminUser);
      } else {
        throw new ChatakAdminException(messageSource.getMessage("Password.already.has.been.changed",
            null, LocaleContextHolder.getLocale()));
      }
    } catch (ChatakAdminException e) {
    	  logger.error("Error :: LoginServiceImpl :: resetPassword", e);
      throw new ChatakAdminException(e.getMessage());
    } catch (Exception e) {
    	  logger.error("Error :: LoginServiceImpl :: resetPassword", e);
      throw new ChatakAdminException(Properties.getProperty(Constants.CHATAK_NORMAL_ERROR_MESSAGE));
    }
  }
}
