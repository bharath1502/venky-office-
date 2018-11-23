package com.chatak.acquirer.admin.service.impl;

import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

import com.chatak.acquirer.admin.exception.ChatakAdminException;
import com.chatak.acquirer.admin.model.UserData;
import com.chatak.acquirer.admin.service.UserService;
import com.chatak.acquirer.admin.util.PasswordHandler;
import com.chatak.acquirer.admin.util.StringUtil;
import com.chatak.mailsender.exception.PrepaidNotificationException;
import com.chatak.mailsender.service.MailServiceManagement;
import com.chatak.pg.acq.dao.AdminUserDao;
import com.chatak.pg.acq.dao.MerchantDao;
import com.chatak.pg.acq.dao.MerchantProfileDao;
import com.chatak.pg.acq.dao.MerchantTerminalDao;
import com.chatak.pg.acq.dao.MerchantUpdateDao;
import com.chatak.pg.acq.dao.MerchantUserDao;
import com.chatak.pg.acq.dao.UserActivityLogDao;
import com.chatak.pg.acq.dao.model.PGAdminUser;
import com.chatak.pg.acq.dao.model.PGApplicationClient;
import com.chatak.pg.acq.dao.model.PGMerchant;
import com.chatak.pg.acq.dao.model.PGMerchantUsers;
import com.chatak.pg.bean.Response;
import com.chatak.pg.constants.ActionErrorCode;
import com.chatak.pg.constants.PGConstants;
import com.chatak.pg.model.AccessLogsDTO;
import com.chatak.pg.model.AdminUserDTO;
import com.chatak.pg.model.GenericUserDTO;
import com.chatak.pg.util.Constants;
import com.chatak.pg.util.DateUtil;
import com.chatak.pg.util.EncryptionUtil;
import com.chatak.pg.util.Properties;
import com.chatak.prepaid.velocity.IVelocityTemplateCreator;

@Service("userService")
public class UserServiceImpl implements UserService, PGConstants {

  private static Logger logger = Logger.getLogger(UserServiceImpl.class);
  
  public static final String EMAIL_TEMPLATE_FILE_PATH = "prepaid.email.template.file.path";
  
  public static final String SOURCE_EMAIL_ID = "prepaid.from.email.id";

  @Autowired
  AdminUserDao adminUserDao;

  @Autowired
  UserActivityLogDao userActivityLogDao;

  @Autowired
  MerchantUserDao merchantUserDao;

  @Autowired
  MerchantDao merchantDao;

  @Autowired
  MessageSource messageSource;

  @Autowired
  MailServiceManagement mailingServiceManagement;

  @Autowired
  private IVelocityTemplateCreator iVelocityTemplateCreator;

  @Autowired
  MerchantUpdateDao merchantUpdateDao;

  @Autowired
  MerchantProfileDao merchantProfileDao;

  @Autowired
  MerchantTerminalDao merchantTerminalDao;

  @Override
  public void saveUser(UserData userData) throws ChatakAdminException {
    try {
      String emailId = userData.getEmailId().toLowerCase();

      if (null != userData.getRoleType()) {
        if (userData.getRoleType().equalsIgnoreCase(Constants.TYPE_MERCHANT)) {
          PGMerchant merchant = merchantUpdateDao.getMerchant(userData.getMerchantId());
          updateMerchantUser(userData, emailId, merchant);
        } else {
          PGAdminUser adminUser = new PGAdminUser();
          adminUser.setUserRoleId(userData.getRoleId());
          adminUser.setUserName(userData.getUserName());
          adminUser.setFirstName(userData.getFirstName());
          adminUser.setLastName(userData.getLastName());
          adminUser.setPhone(userData.getPhone());
          adminUser.setEmail(emailId);
          adminUser.setEmailVerified(Constants.ZERO);
          adminUser.setServiceType(Constants.ONE);
          adminUser.setStatus(PGConstants.STATUS_SUCCESS);
          adminUser.setUserRoleType(userData.getRoleType());
          adminUser.setUserType(userData.getRoleType());
          adminUser.setPassRetryCount(0);
          adminUser.setAddress1(userData.getAddress());
          String password = PasswordHandler.getSystemGeneratedPassword(Constants.EIGHT);
          adminUser.setPassword(EncryptionUtil.encodePassword(password));
          adminUser.setPreviousPasswords(EncryptionUtil.encodePassword(password));
          adminUser.setCreatedDate(DateUtil.getCurrentTimestamp());
          adminUser.setCreatedBy(userData.getCreatedBy().toString());
          adminUser.setEntityId(userData.getEntityId());
          adminUserDao.createOrUpdateUser(adminUser);
          Map<String, String> map = new HashMap<>();
          map.put("firstName", adminUser.getFirstName());
          map.put("userName", adminUser.getUserName());
          map.put("tempPassword", password);
          map.put("hLink", userData.getMerchantLink());
          String vmFileName = "/user_create.vm";
          String mailSubjectKey = "chatak.email.admin.creation";
          String toEmailAddress = adminUser.getEmail();
          sendMailNotification(map, vmFileName, mailSubjectKey, toEmailAddress);
        }
      } else {
        throw new ChatakAdminException("userData empty");
      }
    } catch (Exception e) {
      logger.error("ERROR:: UserServiceImpl:: saveUser method2", e);
      throw new ChatakAdminException(
          Properties.getProperty(Constants.CHATAK_NORMAL_ERROR_MESSAGE) + " ," + e.getMessage());
    }
  }

  private void updateMerchantUser(UserData userData, String emailId, PGMerchant merchant)
      throws ChatakAdminException, NoSuchAlgorithmException {
    if (null != merchant) {
      PGMerchantUsers merchantUser = new PGMerchantUsers();
      merchantUser.setUserRoleId(userData.getRoleId());
      merchantUser.setUserName(userData.getUserName());
      merchantUser.setServiceType(Constants.ONE);
      merchantUser.setStatus(PGConstants.STATUS_SUCCESS);
      merchantUser.setEmail(emailId);
      merchantUser.setPassRetryCount(0);
      merchantUser.setUserRoleType(userData.getRoleType());
      merchantUser.setUserType(userData.getRoleType());
      String pswd = PasswordHandler.getSystemGeneratedPassword(Constants.EIGHT);//Setting generated Password
      try {
        merchantUser.setMerPassword(EncryptionUtil.encodePassword(pswd));
        merchantUser.setPreviousPasswords(EncryptionUtil.encodePassword(pswd));
      } catch (Exception e1) {
        logger.error("Error :: UserServiceImpl :: saveUser Method encodePassword", e1);
        pswd = "Girmiti@1234";//Setting default password
        merchantUser.setMerPassword(EncryptionUtil.encodePassword(pswd));
      }
      merchantUser.setCreatedDate(new Timestamp(System.currentTimeMillis()));
      merchantUser.setCreatedBy(userData.getCreatedBy().toString());
      merchantUser.setEmailVerified(Constants.ZERO);
      merchantUser.setPgMerchantId(merchant.getId());
      merchantUser.setFirstName(userData.getFirstName());
      merchantUser.setLastName(userData.getLastName());
      merchantUser.setPhone(userData.getPhone());
      merchantUser.setAddress(userData.getAddress());
      PGMerchantUsers pgMerchantUsers = merchantUserDao.createOrUpdateUser(merchantUser);

      //Inserting into PgApplicationClient table for OAuth Token Related changes
      PGApplicationClient applicationClient =
          com.chatak.pg.dao.util.StringUtil.getApplicationClientDTO();
      applicationClient.setAppClientId(Properties.getProperty("prepaid.mpos.oauth.clientId"));
      applicationClient.setAppAuthUser(merchantUser.getUserName());
      merchantUserDao.saveOrUpdateApplicationClient(applicationClient);

      if (null != pgMerchantUsers) {
        Map<String, String> map = new HashMap<>();
        map.put("firstName", pgMerchantUsers.getFirstName());
        map.put("userName", pgMerchantUsers.getUserName());
        map.put("tempPassword", pswd);
        map.put("hLink", userData.getMerchantLink());
        String vmFileName = "/merchant_create.vm";
        String mailSubjectKey = "chatak.admin.email.admin.merchant.creation";
        String toEmailAddress = merchantUser.getEmail();
        
        sendMailNotification(map, vmFileName, mailSubjectKey, toEmailAddress);

      }

    } else {
      logger.error("Error :: UserServiceImpl :: saveUser Method Invalid Merchant");
      throw new ChatakAdminException("Invalid Merchant");
    }
  }

  private void sendMailNotification(Map<String, String> map, String vmFileName,
      String mailSubjectKey, String toEmailAddress) throws ChatakAdminException {
    try {
      
      String body = iVelocityTemplateCreator.createEmailTemplate(map,
          Properties.getProperty(EMAIL_TEMPLATE_FILE_PATH) + vmFileName);
      
      mailingServiceManagement.sendMailHtml(Properties.getProperty(SOURCE_EMAIL_ID), body,
          toEmailAddress, messageSource.getMessage(mailSubjectKey, null, LocaleContextHolder.getLocale()));
      
    } catch (PrepaidNotificationException e) {
      logger.error("Error :: UserServiceImpl :: saveUser Method Email sending", e);
      throw new ChatakAdminException(
          Properties.getProperty("chatak.admin.user.inactive.error.message"));
    } catch (Exception e) {
      logger.error("Error :: UserServiceImpl :: saveUser Method Exception", e);
      throw new ChatakAdminException(e.getMessage());
    }
  }

  @Override
  public List<AdminUserDTO> searchUser(AdminUserDTO adminUserDTO) throws ChatakAdminException {
    try {
      return adminUserDao.searchUser(adminUserDTO);
    } catch (Exception e) {
      logger.error("ERROR:: UserServiceImpl:: searchUser method2", e);
      throw new ChatakAdminException(Properties.getProperty(Constants.CHATAK_NORMAL_ERROR_MESSAGE));
    }
  }

  @Override
  public UserData getUserData(Long userId) throws ChatakAdminException {
    UserData userData = new UserData();
    try {
      PGAdminUser adminUser = adminUserDao.findByAdminUserId(userId);
      if (adminUser != null) {
        userData.setEmailId(adminUser.getEmail());
        userData.setFirstName(adminUser.getFirstName());
        userData.setLastName(adminUser.getLastName());
        userData.setPhone(adminUser.getPhone());
        userData.setRoleId(adminUser.getUserRoleId());
        userData.setUserId(adminUser.getAdminUserId());
        userData.setUserName(adminUser.getUserName());
        userData.setName(adminUser.getAddress1());
      }
    } catch (Exception e) {
      logger.error("ERROR:: UserServiceImpl:: getUserData method2", e);
      throw new ChatakAdminException(Properties.getProperty(Constants.CHATAK_NORMAL_ERROR_MESSAGE));
    }
    return userData;
  }

  @Override
  public void updateUser(UserData userData) throws ChatakAdminException {
    try {
      if (null != userData.getRoleType()) {
        if (userData.getRoleType().equalsIgnoreCase(Constants.TYPE_MERCHANT)
            || userData.getRoleType().equalsIgnoreCase(Constants.TYPE_SUB_MERCHANT)) {
          PGMerchantUsers pgMerchantUsers =
              merchantUserDao.findByMerchantUserId(userData.getUserId());
          PGMerchant pgMerchant = merchantUserDao.findById(pgMerchantUsers.getPgMerchantId());
          
          updateMerchantUser(userData, pgMerchantUsers, pgMerchant);
        
        } else {
          List<PGAdminUser> adminUser = adminUserDao.findByUserName(userData.getUserName());
          
			if (StringUtil.isListNotNullNEmpty(adminUser)) {
				getPgAdmin(userData, adminUser);
			} else {
		        throw new ChatakAdminException("userData empty");
		      }
        
        }
      } else {
        throw new ChatakAdminException("userData empty");
      }
    } catch (Exception e) {
      logger.error("ERROR:: UserServiceImpl:: updateUser method2", e);
      throw new ChatakAdminException(e.getMessage());
    }
  }

	/**
	 * @param userData
	 * @param adminUser
	 * @throws ChatakAdminException
	 */
	private void getPgAdmin(UserData userData, List<PGAdminUser> adminUser) throws ChatakAdminException {
		PGAdminUser pGAdminUser = adminUser.get(0);
		if (pGAdminUser.getUserType().equalsIgnoreCase(Constants.ADMIN_USER_TYPE)
				|| pGAdminUser.getUserType().equalsIgnoreCase(Constants.ISO_USER_TYPE)
				|| pGAdminUser.getUserType().equalsIgnoreCase(Constants.PM_USER_TYPE)) {
			updateAdminUser(userData, pGAdminUser);
		}
	}

  private void updateMerchantUser(UserData userData, PGMerchantUsers pgMerchantUsers,
      PGMerchant pgMerchant) throws ChatakAdminException {
    if (pgMerchant != null && pgMerchant.getStatus() != 0
        && pgMerchant.getEmailId().equalsIgnoreCase(pgMerchantUsers.getEmail())) {
      throw new ChatakAdminException(messageSource.getMessage("chatak.user.active.message",
          null, LocaleContextHolder.getLocale()));
    }
    if (null != pgMerchantUsers) {
      pgMerchantUsers.setUserRoleId(userData.getRoleId());
      pgMerchantUsers.setUserName(userData.getUserName());
      pgMerchantUsers.setServiceType(Constants.ONE);
      pgMerchantUsers.setEmail(userData.getEmailId());
      pgMerchantUsers.setPassRetryCount(0);
      pgMerchantUsers.setUserRoleType(userData.getRoleType());
      pgMerchantUsers.setUserType(userData.getRoleType());
      pgMerchantUsers.setCreatedDate(new Timestamp(System.currentTimeMillis()));
      pgMerchantUsers.setCreatedBy(userData.getCreatedBy());
      pgMerchantUsers.setFirstName(userData.getFirstName());
      pgMerchantUsers.setLastName(userData.getLastName());
      pgMerchantUsers.setPhone(userData.getPhone());
      pgMerchantUsers.setAddress(userData.getAddress());
      merchantUserDao.createOrUpdateUser(pgMerchantUsers);
    }
  }

  private void updateAdminUser(UserData userData, PGAdminUser adminUserListByEmail)
      throws ChatakAdminException {
    if (null != adminUserListByEmail) {
      if (adminUserListByEmail.getAdminUserId().equals(userData.getUserId())) {
        adminUserListByEmail.setUserRoleId(userData.getRoleId());
        adminUserListByEmail.setUserName(userData.getUserName());
        adminUserListByEmail.setFirstName(userData.getFirstName());
        adminUserListByEmail.setLastName(userData.getLastName());
        adminUserListByEmail.setPhone(userData.getPhone());
        adminUserListByEmail.setEmail(userData.getEmailId());
        adminUserListByEmail.setEmailVerified(Constants.ONE);
        adminUserListByEmail.setServiceType(Constants.ONE);
        adminUserListByEmail.setUserRoleType(userData.getRoleType());
        adminUserListByEmail.setUserType(userData.getUserType());
        adminUserListByEmail.setPassRetryCount(0);
        adminUserListByEmail.setAddress1(userData.getAddress());
        adminUserListByEmail.setCreatedDate(DateUtil.getCurrentTimestamp());
        adminUserListByEmail.setCreatedBy(userData.getCreatedBy());
        adminUserDao.createOrUpdateUser(adminUserListByEmail);
      } else {
        throw new ChatakAdminException("Duplicate email-Id");
      }
    } else {
      throw new ChatakAdminException("userData empty");
    }
  }

  @Override
  public Response changeUserStatus(UserData userData, String userStatus)
      throws ChatakAdminException {
    logger.info("Entering:: " + "changeUserStatus");
    Response response = new Response();
    PGMerchantUsers pgMerchantUsers = null;
    PGAdminUser pgAdminUser = null;
    try {
      if (null != userData.getRoleType()) {
        if (userData.getRoleType().equalsIgnoreCase(Constants.TYPE_MERCHANT)
            || userData.getRoleType().equalsIgnoreCase(Constants.TYPE_SUB_MERCHANT)) {
          pgMerchantUsers = merchantUserDao.findByMerchantUserId(userData.getUserId());
          PGMerchant pgMerchant = merchantUserDao.findById(pgMerchantUsers.getPgMerchantId());
          changeMerchantUserStatus(userData, userStatus, pgMerchantUsers, pgMerchant);
        } else {
          pgAdminUser = adminUserDao.findByAdminUserId(userData.getUserId());
          changeAdminUserStatus(userData, userStatus, pgAdminUser);
        }
        response.setErrorCode(ActionErrorCode.ERROR_CODE_00);
        response.setErrorMessage(messageSource.getMessage("chatak.updateuser.status.sucess", null,
            LocaleContextHolder.getLocale()));
      } else {
        throw new ChatakAdminException();
      }
      String vmFileName = null;
      String mailSubjectKey = null;
      String toEmailAddress = null;
      if (S_STATUS_ACTIVE.equals(userStatus)) {
        if ((userData.getRoleType().equalsIgnoreCase(Constants.TYPE_MERCHANT)
            || userData.getRoleType().equalsIgnoreCase(Constants.TYPE_SUB_MERCHANT)) && pgMerchantUsers != null) {
          Map<String, String> map = new HashMap<>();
          map.put("firstName", pgMerchantUsers.getFirstName());
          vmFileName = "/merchant_change_status.vm";
          mailSubjectKey = "chatak.email.admin.merchant.activation";
          toEmailAddress = pgMerchantUsers.getEmail();
          mailSubjectKey = getMailSubjectKey(pgMerchantUsers, mailSubjectKey);
          sendMailNotification(map, vmFileName, mailSubjectKey, toEmailAddress);
        } else if (pgAdminUser != null) {
          Map<String, String> map = new HashMap<>();
          map.put("firstName", pgAdminUser.getFirstName());
          map.put("previousStatus", S_STATUS_SUSPENDED);
          map.put("changedStatus", S_STATUS_ACTIVE);
          vmFileName = "/user_status_change.vm";
          mailSubjectKey = "chatak.email.admin.activation";
          toEmailAddress = pgAdminUser.getEmail();
          sendMailNotification(map, vmFileName, mailSubjectKey, toEmailAddress);
        }
        response.setErrorMessage(messageSource.getMessage("chatak.updateuser.status.sucess", null,
            LocaleContextHolder.getLocale()) + " "
            + messageSource.getMessage("chatak.email.admin.email.send.success", null,
                LocaleContextHolder.getLocale()));
      }
    } catch (ChatakAdminException e) {
      logger.error("Error :: UserServiceImpl :: changeUserStatus Exception", e);
      response.setErrorCode(ActionErrorCode.ERROR_CODE_01);
      response.setErrorMessage(e.getMessage());
    } catch (Exception e) {
      response.setErrorCode(ActionErrorCode.ERROR_CODE_01);
      response.setErrorMessage(
          messageSource.getMessage(Constants.CHATAK_GENERAL_ERROR, null, LocaleContextHolder.getLocale()));
      logger.error("", e);
    }
    logger.info("Exiting :: UserServiceImpl :: changeUserStatus");
    return response;
  }

  private String getMailSubjectKey(PGMerchantUsers pgMerchantUsers, String mailSubjectKey) {
    if (PGConstants.SUB_MERCHANT.equalsIgnoreCase(pgMerchantUsers.getUserRoleType())) {
      mailSubjectKey = "chatak.email.admin.submerchant.activation";
    }
    return mailSubjectKey;
  }

  private void changeMerchantUserStatus(UserData userData, String userStatus,
      PGMerchantUsers pgMerchantUsers, PGMerchant pgMerchant) throws ChatakAdminException {
    if (pgMerchant != null && pgMerchant.getStatus() != 0) {
      throw new ChatakAdminException(messageSource.getMessage(
          "chatak.user.merchant.suspend.state", null, LocaleContextHolder.getLocale()));
    }
    if (null != pgMerchantUsers) {
      if (userStatus.equals("Active")) {
        pgMerchantUsers.setStatus(PGConstants.STATUS_ACTIVE);
      } else {
        pgMerchantUsers.setStatus(PGConstants.STATUS_SUSPENDED);
      }
      pgMerchantUsers.setUpdatedDate(new Timestamp(System.currentTimeMillis()));
      pgMerchantUsers.setReason(userData.getReason());
      merchantUserDao.createOrUpdateUser(pgMerchantUsers);
    }
  }

  private void changeAdminUserStatus(UserData userData, String userStatus, PGAdminUser pgAdminUser)
      throws ChatakAdminException {
    if (null != pgAdminUser) {
      if (pgAdminUser.getAdminUserId().equals(userData.getUserId())) {
        if (userStatus.equals("Active")) {
          pgAdminUser.setStatus(PGConstants.STATUS_ACTIVE);
        } else {
          pgAdminUser.setStatus(PGConstants.STATUS_SUSPENDED);
        }
        pgAdminUser.setUpdatedDate(new Timestamp(System.currentTimeMillis()));
        pgAdminUser.setReason(userData.getReason());
        adminUserDao.createOrUpdateUser(pgAdminUser);
      } else {
        throw new ChatakAdminException(messageSource.getMessage(Constants.CHATAK_GENERAL_ERROR, null,
            LocaleContextHolder.getLocale()));
      }
    } else {
      throw new ChatakAdminException(messageSource.getMessage(Constants.CHATAK_GENERAL_ERROR, null,
          LocaleContextHolder.getLocale()));
    }
  }

  @Override
  public List<AccessLogsDTO> getAllPgUserActivityLogs() throws ChatakAdminException {
    return userActivityLogDao.getAllPgUserActivityLogs();
  }

  @Override
  public List<GenericUserDTO> searchGenericUser(GenericUserDTO adminUserDTO)
      throws ChatakAdminException {
    try {

      List<GenericUserDTO> adminList = adminUserDao.searchGenericUser(adminUserDTO);
      List<GenericUserDTO> merchantList = merchantUserDao.searchMerchantUsers(adminUserDTO);
      if (null != adminList) {
        adminList.addAll(merchantList);
      } else {
        adminList = merchantList;
      }
      return adminList;
    } catch (Exception e) {
      logger.error("ERROR:: UserServiceImpl:: searchUser method2", e);
      throw new ChatakAdminException(Properties.getProperty(Constants.CHATAK_NORMAL_ERROR_MESSAGE));
    }
  }

  public List<GenericUserDTO> searchAdminUser(GenericUserDTO adminUserDTO)
      throws ChatakAdminException {
    try {
      return adminUserDao.searchGenericUser(adminUserDTO);
    } catch (Exception e) {
      logger.error("ERROR:: UserServiceImpl:: searchUser method2", e);
      throw new ChatakAdminException(Properties.getProperty(Constants.CHATAK_NORMAL_ERROR_MESSAGE));
    }
  }

  public List<GenericUserDTO> searchMerchantUser(GenericUserDTO adminUserDTO)
      throws ChatakAdminException {
    try {
      if (adminUserDTO.getLoginuserType().equals(Constants.PM_USER_TYPE)) {
        return merchantUserDao.searchMerchantUsersForPM(adminUserDTO, adminUserDTO.getEntityId());
      } else {
        return merchantUserDao.searchMerchantUsers(adminUserDTO);
      }
    } catch (Exception e) {
      logger.error("ERROR:: UserServiceImpl:: searchUser method2", e);
      throw new ChatakAdminException(Properties.getProperty(Constants.CHATAK_NORMAL_ERROR_MESSAGE));
    }
  }

  @Override
  public UserData getUserDataOnUsersGroupType(Long userId, String usersGroupType)
      throws ChatakAdminException {
    UserData userData = new UserData();
    if (Constants.USERS_GROUP_MERCHANT.equalsIgnoreCase(usersGroupType)
        || Constants.TYPE_SUB_MERCHANT.equalsIgnoreCase(usersGroupType)) {
      try {

        PGMerchantUsers merchantUser = merchantUserDao.findByMerchantUserId(userId);
        if (merchantUser != null) {
          PGMerchant merchant =
              merchantProfileDao.getMerchantById(merchantUser.getPgMerchantId());
          userData.setMerchantName(merchant.getBusinessName());
          userData.setEmailId(merchantUser.getEmail());
          userData.setFirstName(merchantUser.getFirstName());
          userData.setLastName(merchantUser.getLastName());
          userData.setPhone(merchantUser.getPhone());
          userData.setRoleId(merchantUser.getUserRoleId());
          userData.setUserId(merchantUser.getId());
          userData.setAddress(merchantUser.getAddress());
          userData.setUserType(merchantUser.getUserType());
          userData.setRoleType(merchantUser.getUserRoleType());
          userData.setStatus(merchantUser.getStatus());
          userData.setUserName(merchantUser.getUserName());
          userData.setMerchantId(merchant.getMerchantCode());
        }
      } catch (Exception e) {
        logger.error("ERROR:: UserServiceImpl:: getUserData method2", e);
        throw new ChatakAdminException(Properties.getProperty(Constants.CHATAK_NORMAL_ERROR_MESSAGE));
      }
    } else {
      PGAdminUser adminUser = adminUserDao.findByAdminUserId(userId);
      if (adminUser != null) {
        userData.setEmailId(adminUser.getEmail());
        userData.setFirstName(adminUser.getFirstName());
        userData.setLastName(adminUser.getLastName());
        userData.setPhone(adminUser.getPhone());
        userData.setRoleId(adminUser.getUserRoleId());
        userData.setUserId(adminUser.getAdminUserId());
        userData.setUserName(adminUser.getUserName());
        userData.setAddress(adminUser.getAddress1());
        userData.setRoleType(adminUser.getUserRoleType());
        userData.setUserType(adminUser.getUserType());
        userData.setAddress(adminUser.getAddress1());
        userData.setStatus(adminUser.getStatus());
        userData.setEntityId(adminUser.getEntityId());
      }
    }
    return userData;
  }

  @Override
  public UserData validateEmailId(String emailId) throws ChatakAdminException {
    UserData response = new UserData();
    // Check in both merchant and merchant users
    // This will also check by status as not deleted
    Response pgMerchantEmail = merchantProfileDao.getUserByEmailId(emailId);
    if (pgMerchantEmail != null && pgMerchantEmail.getErrorCode()
        .equalsIgnoreCase(ActionErrorCode.ERROR_CODE_DUPLICATE_ENTRY)) {
      response.setEmailId(emailId);
      return response;
    }
    // Check for admin user by the same email and status not as deleted
    String email = emailId.toLowerCase();
    PGAdminUser merchantUsers = adminUserDao.findByEmail(email);
    if (null != merchantUsers) {
      response.setEmailId(merchantUsers.getEmail());
    }
    return response;
  }

  @Override
  public UserData deleteMerchantUser(Long userId, String usersGroupType)
      throws ChatakAdminException {
    UserData userData = new UserData();
    try {
      if (Constants.USERS_GROUP_MERCHANT.equalsIgnoreCase(usersGroupType)
          || Constants.USERS_GROUP_SUBMERCHANT.equalsIgnoreCase(usersGroupType)) {
        PGMerchantUsers merchantUser = merchantUserDao.findByMerchantUserId(userId);
        PGMerchant pgMerchant = merchantUserDao.findById(merchantUser.getPgMerchantId());
        if (pgMerchant != null && pgMerchant.getStatus() != PGConstants.STATUS_DELETED
            && pgMerchant.getEmailId().equalsIgnoreCase(merchantUser.getEmail())) {
          throw new ChatakAdminException();
        }
          merchantUser.setStatus(PGConstants.STATUS_DELETED);
          PGMerchantUsers user = merchantUserDao.createOrUpdateUser(merchantUser);
          userData = getMerchantUserResponse(userData, user);
      } else {
        PGAdminUser adminUser = adminUserDao.findByAdminUserId(userId);
        if (null != adminUser) {
          adminUser.setStatus(PGConstants.STATUS_DELETED);
          PGAdminUser user = adminUserDao.createOrUpdateUser(adminUser);
          userData = getAdminUserResponse(userData, user);
        }
      }
    } catch (Exception e) {
      throw new ChatakAdminException(e);
    }
    return userData;
  }

  private UserData getMerchantUserResponse(UserData userData, PGMerchantUsers user) {
    if (null != user) {
      userData.setErrorCode(PGConstants.SUCCESS);
      userData.setErrorMessage("Merchant user has been deleted successfully");
    }
    return userData;
  }

  private UserData getAdminUserResponse(UserData userData, PGAdminUser user) {
    if (null != user) {
      userData.setErrorCode(PGConstants.SUCCESS);
      userData.setErrorMessage("admin user has been deleted successfully");
    }
    return userData;
  }

  @Override
  public UserData validateEmailForAdminId(String emailId) throws ChatakAdminException {
    UserData response = new UserData();
    PGAdminUser merchantUsers = adminUserDao.findByEmail(emailId);
    if (null != merchantUsers) {
      response.setEmailId(merchantUsers.getEmail());
    }
    return response;
  }

  @Override
  public List<Long> getAdminRoleList() {
    return adminUserDao.getRoleListAdmin();
  }

  @Override
  public List<Long> getMerchantRoleList() {
    return merchantUserDao.getRoleListMerchant();
  }

  @Override
  public UserData validateUserName(String userName) throws ChatakAdminException {
    UserData response = new UserData();
    // Check in both merchant and merchant users
    // This will also check by status as not deleted
    Response pgMerchantEmail = merchantUpdateDao.getUserByUserName(userName);
    if (pgMerchantEmail != null && pgMerchantEmail.getErrorCode()
        .equalsIgnoreCase(ActionErrorCode.ERROR_CODE_DUPLICATE_ENTRY)) {
      response.setUserName(userName);
      return response;
    }
    // Check for admin user by the same userName and status not as deleted
    PGAdminUser merchantUsers = adminUserDao.findByUserNameAndStatus(userName);
    if (null != merchantUsers) {
      response.setUserName(merchantUsers.getUserName());
    }
    return response;
  }

  @Override
  public List<AdminUserDTO> searchAdminUserList(String userType) throws ChatakAdminException {
    return adminUserDao.searchAdminUserList(userType);
  }

  @Override
  public List<AdminUserDTO> searchMerchantUserList() throws ChatakAdminException {
    return merchantUserDao.searchMerchantUserList();
  }

  @Override
  public Response unblockAdminUser(String userName) throws ChatakAdminException {
	  logger.info("Entering :: UserServiceImpl :: unblockAdminUser method");
    PGAdminUser adminUser = adminUserDao.findByUserNameAndStatus(userName);
    Response response = new Response();
    if (null != adminUser) {
      adminUser.setPassRetryCount(PGConstants.STATUS_ACTIVE);
      adminUser.setStatus(STATUS_ACTIVE);
      adminUserDao.createOrUpdateUser(adminUser);
      response.setErrorCode("00");
      response.setErrorMessage("SUCCESS");
    }
    logger.info("Exit :: UserServiceImpl :: unblockAdminUser method");
    return response;
  }

  @Override
  public Response unblockMerchantUser(String userName) throws ChatakAdminException {
	  logger.info("Entering :: UserServiceImpl :: unblockMerchantUser method");
    PGMerchantUsers merchantUsers = merchantUserDao.findByUserName(userName);
    Response response = new Response();
    if (null != merchantUsers) {
      merchantUsers.setPassRetryCount(PGConstants.STATUS_ACTIVE);
      merchantUsers.setStatus(STATUS_ACTIVE);
      merchantUserDao.createOrUpdateUser(merchantUsers);
      response.setErrorCode("00");
      response.setErrorMessage("SUCCESS");
    }
    logger.info("Exit :: UserServiceImpl :: unblockMerchantUser method");
    return response;
  }

  @Override
  public UserData merchantIdByMerchantName(String merchantId, Long entityId, String userType) throws ChatakAdminException {
    PGMerchant pgMerchant = merchantTerminalDao.validateMerchantId(merchantId, entityId, userType);
    UserData userResponse = new UserData();
    if(null != pgMerchant && !StringUtil.isNullAndEmpty(pgMerchant.getBusinessName())){
      userResponse.setMerchantName(pgMerchant.getBusinessName());
      userResponse.setErrorCode(ActionErrorCode.ERROR_CODE_00);
    }
    return userResponse;
  }
}
