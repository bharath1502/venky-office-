package com.chatak.acquirer.admin.service;

import java.security.NoSuchAlgorithmException;
import java.util.Locale;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.context.MessageSource;

import com.chatak.acquirer.admin.controller.model.LoginDetails;
import com.chatak.acquirer.admin.controller.model.ResetPasswordData;
import com.chatak.acquirer.admin.exception.ChatakAdminException;
import com.chatak.acquirer.admin.model.UserProfileRequest;
import com.chatak.acquirer.admin.service.impl.LoginServiceImpl;
import com.chatak.mailsender.service.MailServiceManagement;
import com.chatak.pg.acq.dao.AdminUserDao;
import com.chatak.pg.acq.dao.RoleDao;
import com.chatak.pg.acq.dao.UserActivityLogDao;
import com.chatak.pg.acq.dao.UsersRoleDao;
import com.chatak.pg.acq.dao.model.PGAdminUser;
import com.chatak.pg.acq.dao.model.PGUserRoles;
import com.chatak.pg.util.Properties;
import com.chatak.prepaid.velocity.IVelocityTemplateCreator;

@RunWith(MockitoJUnitRunner.class)

public class LoginServiceImplTest {

	@InjectMocks
	LoginServiceImpl loginServiceImpl = new LoginServiceImpl();

	@Mock
	AdminUserDao adminUserDao;

	@Mock
	UsersRoleDao usersRoleDao;

	@Mock
	RoleDao roleDao;

	@Mock
	MailServiceManagement mailingServiceManagement;

	@Mock
	IVelocityTemplateCreator iVelocityTemplateCreator;

	@Mock
	UserActivityLogDao userActivityLogDao;

	@Mock
	MessageSource messageSource;

	@Before
	public void pro() {
		java.util.Properties properties = new java.util.Properties();
		properties.setProperty("password.policy.format", "abcd");
		Properties.mergeProperties(properties);
	}

	@Test
	public void testAuthenticate() throws NoSuchAlgorithmException, ReflectiveOperationException {
		LoginDetails loginDetails = new LoginDetails();
		PGAdminUser adminUser = new PGAdminUser();
		PGUserRoles role = new PGUserRoles();
		adminUser.setUserRoleId(Long.parseLong("6546"));
		role.setStatus(0);
		adminUser.setStatus(Integer.parseInt("2"));
		adminUser.setPassRetryCount(Integer.parseInt("2"));
		adminUser.setPassword("asd2");
		loginDetails.setAcqP("asd2");
		Mockito.when(roleDao.findByUserRoleId(Matchers.anyLong())).thenReturn(role);
		Mockito.when(adminUserDao.findByUserNameAndUserType(Matchers.anyString(), Matchers.anyString()))
				.thenReturn(adminUser);
		Mockito.when(adminUserDao.createOrUpdateUser(Matchers.any(PGAdminUser.class))).thenReturn(adminUser);
		loginServiceImpl.authenticate(loginDetails);
	}

	@Test
	public void testAuthenticateStatus() throws NoSuchAlgorithmException, ReflectiveOperationException {
		LoginDetails loginDetails = new LoginDetails();
		PGAdminUser adminUser = new PGAdminUser();
		PGUserRoles role = new PGUserRoles();
		adminUser.setUserRoleId(Long.parseLong("6546"));
		role.setStatus(0);
		adminUser.setStatus(Integer.parseInt("3"));
		adminUser.setPassRetryCount(Integer.parseInt("3"));
		Mockito.when(roleDao.findByUserRoleId(Matchers.anyLong())).thenReturn(role);
		Mockito.when(adminUserDao.findByUserNameAndUserType(Matchers.anyString(), Matchers.anyString()))
				.thenReturn(adminUser);
		loginServiceImpl.authenticate(loginDetails);
	}

	@Test
	public void testAuthenticateSetAdminElse() throws NoSuchAlgorithmException, ReflectiveOperationException {
		LoginDetails loginDetails = new LoginDetails();
		PGAdminUser adminUser = new PGAdminUser();
		PGUserRoles role = new PGUserRoles();
		adminUser.setUserRoleId(Long.parseLong("6546"));
		role.setStatus(0);
		adminUser.setStatus(Integer.parseInt("3"));
		adminUser.setPassRetryCount(Integer.parseInt("3"));
		adminUser.setPassword("423");
		loginDetails.setAcqP("423");
		Mockito.when(roleDao.findByUserRoleId(Matchers.anyLong())).thenReturn(role);
		Mockito.when(adminUserDao.findByUserNameAndUserType(Matchers.anyString(), Matchers.anyString()))
				.thenReturn(adminUser);
		loginServiceImpl.authenticate(loginDetails);
	}

	@Test
	public void testAuthenticateAdminUserElse() throws NoSuchAlgorithmException, ReflectiveOperationException {
		LoginDetails loginDetails = new LoginDetails();
		PGAdminUser adminUser = new PGAdminUser();
		PGUserRoles role = new PGUserRoles();
		adminUser.setUserRoleId(Long.parseLong("6546"));
		role.setStatus(0);
		adminUser.setStatus(1);
		Mockito.when(roleDao.findByUserRoleId(Matchers.anyLong())).thenReturn(role);
		Mockito.when(adminUserDao.findByUserNameAndUserType(Matchers.anyString(), Matchers.anyString()))
				.thenReturn(adminUser);
		loginServiceImpl.authenticate(loginDetails);
	}

	@Test
	public void testAuthenticateElse() throws NoSuchAlgorithmException, ReflectiveOperationException {
		LoginDetails loginDetails = new LoginDetails();
		loginServiceImpl.authenticate(loginDetails);
	}

	@Test(expected = ChatakAdminException.class)
	public void testChangdPassword() throws NumberFormatException, ChatakAdminException {
		PGAdminUser adminUser = new PGAdminUser();
		adminUser.setPassword("5435");
		Mockito.when(adminUserDao.findByAdminUserId(Matchers.anyLong())).thenReturn(adminUser);
		loginServiceImpl.changdPassword(Long.parseLong("423"), "asd2", "asdf2");
	}

	@Test(expected = ChatakAdminException.class)
	public void testChangdPasswordChatakAdminException() throws NumberFormatException, ChatakAdminException {
		PGAdminUser adminUser = new PGAdminUser();
		Mockito.when(adminUserDao.findByAdminUserId(Matchers.anyLong())).thenReturn(adminUser);
		loginServiceImpl.changdPassword(Long.parseLong("423"), "asd2", "asdf2");
	}

	@Test(expected = ChatakAdminException.class)
	public void testChangdPasswordException() throws NumberFormatException, ChatakAdminException {
		loginServiceImpl.changdPassword(Long.parseLong("423"), "asd2", "asdf2");
	}

	@Test
	public void testGetUserProfile() throws NumberFormatException, ChatakAdminException {
		PGAdminUser adminUser = new PGAdminUser();
		PGUserRoles pgUserRoles = new PGUserRoles();
		Mockito.when(adminUserDao.findByAdminUserId(Matchers.anyLong())).thenReturn(adminUser);
		Mockito.when(usersRoleDao.findByRoleId(Matchers.anyLong())).thenReturn(pgUserRoles);
		loginServiceImpl.getUserProfile(Long.parseLong("423"));
	}

	@Test(expected = ChatakAdminException.class)
	public void testGetUserProfileException() throws NumberFormatException, ChatakAdminException {
		Mockito.when(adminUserDao.findByAdminUserId(Matchers.anyLong())).thenThrow(new NullPointerException());
		loginServiceImpl.getUserProfile(Long.parseLong("423"));
	}

	@Test
	public void testChangeUserProfile() throws ChatakAdminException {
		UserProfileRequest userProfileRequest = new UserProfileRequest();
		PGAdminUser pgServiceAdminUser = new PGAdminUser();
		Mockito.when(adminUserDao.findByAdminUserId(Matchers.anyLong())).thenReturn(pgServiceAdminUser);
		loginServiceImpl.changeUserProfile(userProfileRequest);
	}

	@Test(expected = ChatakAdminException.class)
	public void testChangeUserProfileException() throws ChatakAdminException {
		UserProfileRequest userProfileRequest = new UserProfileRequest();
		Mockito.when(adminUserDao.findByAdminUserId(Matchers.anyLong())).thenThrow(new NullPointerException());
		loginServiceImpl.changeUserProfile(userProfileRequest);
	}

	@Test(expected = ChatakAdminException.class)
	public void testUserExist() throws ChatakAdminException {
		loginServiceImpl.userExist("a", "www");
	}

	@Test
	public void testUserExistElse() throws ChatakAdminException {
		PGAdminUser pgAdminUser = new PGAdminUser();
		pgAdminUser.setStatus(0);
		pgAdminUser.setAdminUserId(Long.parseLong("5435"));
		Mockito.when(adminUserDao.findByUserNameAndStatus(Matchers.anyString())).thenReturn(pgAdminUser);
		Mockito.when(adminUserDao.createOrUpdateUser(Matchers.any(PGAdminUser.class))).thenReturn(pgAdminUser);
		loginServiceImpl.userExist("a", "www");
	}

	@Test(expected = ChatakAdminException.class)
	public void testUserExistElseNull() throws ChatakAdminException {
		PGAdminUser pgAdminUser = new PGAdminUser();
		pgAdminUser.setStatus(1);
		pgAdminUser.setAdminUserId(Long.parseLong("5435"));
		Mockito.when(adminUserDao.findByUserNameAndStatus(Matchers.anyString())).thenReturn(pgAdminUser);
		Mockito.when(adminUserDao.createOrUpdateUser(Matchers.any(PGAdminUser.class))).thenReturn(pgAdminUser);
		loginServiceImpl.userExist("a", "www");
	}

	@Test
	public void testValidToken() throws ChatakAdminException {
		loginServiceImpl.validToken(Long.parseLong("534"), "www");
	}

	@Test(expected = ChatakAdminException.class)
	public void testResetPassword() throws ChatakAdminException {
		ResetPasswordData resetPasswordData = new ResetPasswordData();
		PGAdminUser adminUser = new PGAdminUser();
		adminUser.setEmailVerified(0);
		resetPasswordData.setNewPassword("564");
		Mockito.when(adminUserDao.findByAdminUserId(Matchers.anyLong())).thenReturn(adminUser);
		loginServiceImpl.resetPassword(resetPasswordData, Long.parseLong("534"));
	}

	@Test(expected = ChatakAdminException.class)
	public void testResetPasswordChatakAdminException() throws ChatakAdminException {
		ResetPasswordData resetPasswordData = new ResetPasswordData();
		PGAdminUser adminUser = new PGAdminUser();
		adminUser.setEmailVerified(0);
		resetPasswordData.setNewPassword("564");
		Mockito.when(adminUserDao.findByAdminUserId(Matchers.anyLong())).thenReturn(adminUser);
		Mockito.when(messageSource.getMessage(Matchers.anyString(), Matchers.any(Object[].class),
				Matchers.any(Locale.class))).thenReturn("abcde");
		loginServiceImpl.resetPassword(resetPasswordData, Long.parseLong("534"));
	}

	@Test(expected = ChatakAdminException.class)
	public void testResetPasswordException() throws ChatakAdminException {
		ResetPasswordData resetPasswordData = new ResetPasswordData();
		PGAdminUser adminUser = new PGAdminUser();
		adminUser.setEmailVerified(0);
		Mockito.when(adminUserDao.findByAdminUserId(Matchers.anyLong())).thenReturn(adminUser);
		loginServiceImpl.resetPassword(resetPasswordData, Long.parseLong("534"));
	}

}
