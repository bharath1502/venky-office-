package com.chatak.merchant.service.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.context.MessageSource;
import org.springframework.dao.DataAccessException;

import com.chatak.mailsender.service.MailServiceManagement;
import com.chatak.merchant.controller.model.LoginDetails;
import com.chatak.merchant.controller.model.LoginResponse;
import com.chatak.merchant.controller.model.ResetPasswordData;
import com.chatak.merchant.exception.ChatakMerchantException;
import com.chatak.merchant.model.UserProfileRequest;
import com.chatak.pg.acq.dao.MerchantProfileDao;
import com.chatak.pg.acq.dao.MerchantUserDao;
import com.chatak.pg.acq.dao.RoleDao;
import com.chatak.pg.acq.dao.model.PGMerchant;
import com.chatak.pg.acq.dao.model.PGMerchantUsers;
import com.chatak.pg.acq.dao.model.PGUserRoles;
import com.chatak.pg.acq.dao.repository.MerchantRepository;
import com.chatak.prepaid.velocity.IVelocityTemplateCreator;

import junit.framework.Assert;

@RunWith(MockitoJUnitRunner.class)
public class LoginServiceImplTest {

	@InjectMocks
	private LoginServiceImpl loginServiceImpl = new LoginServiceImpl();

	@Mock
	private MerchantUserDao merchantUserDao;

	@Mock
	private MailServiceManagement mailingServiceManagement;

	@Mock
	private IVelocityTemplateCreator iVelocityTemplateCreator;;

	@Mock
	private MerchantRepository merchantRepository;

	@Mock
	private MessageSource messageSource;

	@Mock
	RoleDao roleDao;

	@Mock
	MerchantProfileDao merchantProfileDao;

	@Mock
	LoginDetails loginDetails;

	@Mock
	LoginResponse loginResponse;

	@Mock
	PGMerchantUsers pgMerchantUsers;

	@Mock
	PGUserRoles role;

	@Mock
	DataAccessException dataAccessException;

	@Test
	public void testAuthenticate() throws ChatakMerchantException {
		List<PGMerchant> pgMerchantList = new ArrayList<>();
		PGMerchant pgMerchant = new PGMerchant();
		pgMerchant.setParentMerchantId(Long.parseLong("123"));
		pgMerchantList.add(pgMerchant);
		loginDetails = new LoginDetails();
		pgMerchantUsers = new PGMerchantUsers();
		loginDetails.setAcqP("1233");
		pgMerchantUsers.setPassRetryCount(Integer.parseInt("2"));
		pgMerchantUsers.setMerPassword("E034FB6B66AACC1D48F445DDFB08DA98");
		pgMerchantUsers.setStatus(0);
		pgMerchantUsers.setEmailVerified(1);
		pgMerchantUsers.setPgMerchantId(Long.parseLong("123"));
		pgMerchantUsers.setId(Long.parseLong("123"));
		role.setStatus(0);
		pgMerchantUsers.setLastPassWordChange(new Timestamp(Long.parseLong("57777777777777777")));
		pgMerchantUsers.setId(Long.parseLong("123"));
		pgMerchantUsers.setEmail("xyz@gmail");
		Mockito.when(roleDao.findByUserRoleId(Matchers.anyLong())).thenReturn(role);
		Mockito.when(merchantUserDao.createOrUpdateUser(Matchers.any(PGMerchantUsers.class))).thenReturn(pgMerchantUsers);
		Mockito.when(merchantUserDao.getMerchant(Matchers.anyLong())).thenReturn(pgMerchantList);
		Mockito.when(merchantUserDao.findByUserName(Matchers.anyString())).thenReturn(pgMerchantUsers);
		Assert.assertNotNull(loginServiceImpl.authenticate(loginDetails));
	}

	@Test
	public void testAuthenticateStatus() throws ChatakMerchantException {
		List<PGMerchant> pgMerchantList = new ArrayList<>();
		PGMerchant pgMerchant = new PGMerchant();
		pgMerchant.setParentMerchantId(Long.parseLong("123"));
		pgMerchantList.add(pgMerchant);
		loginDetails = new LoginDetails();
		pgMerchantUsers = new PGMerchantUsers();
		loginDetails.setAcqP("1233");
		pgMerchantUsers.setPassRetryCount(Integer.parseInt("2"));
		pgMerchantUsers.setMerPassword("E034FB6B66AACC1D48F445DDFB08DA98");
		pgMerchantUsers.setStatus(0);
		pgMerchantUsers.setEmailVerified(1);
		pgMerchantUsers.setPgMerchantId(Long.parseLong("123"));
		pgMerchantUsers.setId(Long.parseLong("123"));
		role.setStatus(0);
		pgMerchantUsers.setLastPassWordChange(new Timestamp(0));
		pgMerchantUsers.setId(Long.parseLong("123"));
		pgMerchantUsers.setEmail("xyz@gmail");
		Mockito.when(roleDao.findByUserRoleId(Matchers.anyLong())).thenReturn(role);
		Mockito.when(merchantUserDao.createOrUpdateUser(Matchers.any(PGMerchantUsers.class))).thenReturn(pgMerchantUsers);
		Mockito.when(merchantUserDao.getMerchant(Matchers.anyLong())).thenReturn(pgMerchantList);
		Mockito.when(merchantUserDao.findByUserName(Matchers.anyString())).thenReturn(pgMerchantUsers);
		Assert.assertNotNull(loginServiceImpl.authenticate(loginDetails));
	}

	@Test
	public void testAuthenticateDataAccessException() throws ChatakMerchantException {
		List<PGMerchant> pgMerchantList = new ArrayList<>();
		PGMerchant pgMerchant = new PGMerchant();
		pgMerchant.setParentMerchantId(Long.parseLong("123"));
		pgMerchantList.add(pgMerchant);
		loginDetails = new LoginDetails();
		pgMerchantUsers = new PGMerchantUsers();
		loginDetails.setAcqP("1233");
		pgMerchantUsers.setPassRetryCount(Integer.parseInt("2"));
		pgMerchantUsers.setMerPassword("E034FB6B66AACC1D48F445DDFB08DA98");
		pgMerchantUsers.setStatus(0);
		pgMerchantUsers.setEmailVerified(1);
		pgMerchantUsers.setPgMerchantId(Long.parseLong("123"));
		pgMerchantUsers.setId(Long.parseLong("123"));
		role.setStatus(0);
		pgMerchantUsers.setLastPassWordChange(new Timestamp(Long.parseLong("57777777777777777")));
		pgMerchantUsers.setId(Long.parseLong("123"));
		pgMerchantUsers.setEmail("xyz@gmail");
		Mockito.when(roleDao.findByUserRoleId(Matchers.anyLong())).thenReturn(role);
		Mockito.when(merchantUserDao.createOrUpdateUser(Matchers.any(PGMerchantUsers.class))).thenReturn(pgMerchantUsers);
		Mockito.when(merchantUserDao.getMerchant(Matchers.anyLong())).thenReturn(pgMerchantList);
		Mockito.when(merchantUserDao.findByUserName(Matchers.anyString())).thenThrow(dataAccessException);
		Assert.assertNull(loginServiceImpl.authenticate(loginDetails));
	}

	@Test
	public void testAuthenticateLoginResponseStatus() throws ChatakMerchantException {
		loginDetails = new LoginDetails();
		pgMerchantUsers = new PGMerchantUsers();
		loginDetails.setAcqP("123");
		pgMerchantUsers.setPassRetryCount(Integer.parseInt("2"));
		pgMerchantUsers.setMerPassword("E034FB6B66AACC1D48F445DDFB08DA98");
		pgMerchantUsers.setStatus(1);
		pgMerchantUsers.setEmailVerified(0);
		role.setStatus(1);
		pgMerchantUsers.setLastPassWordChange(new Timestamp(Long.parseLong("57777777777777777")));
		pgMerchantUsers.setId(Long.parseLong("123"));
		pgMerchantUsers.setEmail("xyz@gmail");
		Mockito.when(roleDao.findByUserRoleId(Matchers.anyLong())).thenReturn(role);
		Mockito.when(merchantUserDao.createOrUpdateUser(Matchers.any(PGMerchantUsers.class))).thenReturn(pgMerchantUsers);
		Mockito.when(merchantUserDao.findByUserName(Matchers.anyString())).thenReturn(pgMerchantUsers);
		Assert.assertNotNull(loginServiceImpl.authenticate(loginDetails));
	}

	@Test
	public void testAuthenticateElse() throws ChatakMerchantException {
		loginDetails = new LoginDetails();
		pgMerchantUsers = null;
		Mockito.when(merchantUserDao.findByUserName(Matchers.anyString())).thenReturn(pgMerchantUsers);
		Assert.assertNotNull(loginServiceImpl.authenticate(loginDetails));
	}

	@Test
	public void testAuthenticateException() throws ChatakMerchantException {
		Mockito.when(merchantUserDao.findByUserName(Matchers.anyString())).thenThrow(new NullPointerException());
		Assert.assertNull(loginServiceImpl.authenticate(loginDetails));

	}

	@Test
	public void testChangdPassword() throws ChatakMerchantException {
		pgMerchantUsers = new PGMerchantUsers();
		pgMerchantUsers.setEmailVerified(0);
		pgMerchantUsers.setMerPassword("900150983CD24FB0D6963F7D28E17F72");
		Mockito.when(merchantUserDao.findByMerchantUserId(Matchers.anyLong())).thenReturn(pgMerchantUsers);
		Mockito.when(messageSource.getMessage(Matchers.anyString(), Matchers.any(Object[].class), Matchers.any(Locale.class))).thenReturn("12");
		Assert.assertNotNull(loginServiceImpl.changdPassword(Long.parseLong("123"), "abc", "Chatak@123!"));  //ReBrand
	}

	@Test
	public void testChangdPasswordNull() throws ChatakMerchantException {
		pgMerchantUsers = new PGMerchantUsers();
		pgMerchantUsers.setEmailVerified(0);
		pgMerchantUsers.setMerPassword("900150983CD24FB0D6963F7D28E17F72");
		pgMerchantUsers.setPreviousPasswords("E8CE7A0A002060AAADC0ACF3C4158339");
		Mockito.when(merchantUserDao.findByMerchantUserId(Matchers.anyLong())).thenReturn(pgMerchantUsers);
		Mockito.when(messageSource.getMessage(Matchers.anyString(), Matchers.any(Object[].class), Matchers.any(Locale.class))).thenReturn("12");
		Assert.assertNotNull(loginServiceImpl.changdPassword(Long.parseLong("123"), "abc", "Chatak@123!"));  //ReBrand
	}

	@Test(expected = ChatakMerchantException.class)
	public void testChangdPasswordexp() throws ChatakMerchantException {
		pgMerchantUsers = new PGMerchantUsers();
		pgMerchantUsers.setMerPassword("900150983CD24FB0D6963F7D28E17F72");
		Mockito.when(merchantUserDao.findByMerchantUserId(Matchers.anyLong())).thenReturn(pgMerchantUsers);
		Assert.assertNotNull(loginServiceImpl.changdPassword(Long.parseLong("123"), "abc", "xyz"));
	}

	@Test(expected = ChatakMerchantException.class)
	public void testChangdPasswordException() throws ChatakMerchantException {
		pgMerchantUsers = new PGMerchantUsers();
		pgMerchantUsers.setEmailVerified(0);
		pgMerchantUsers.setMerPassword("900150983CD24FB0D6963F7D28E17F72");
		pgMerchantUsers.setPreviousPasswords("E8CE7A0A002060AAADC0ACF3C4158339");
		Mockito.when(merchantUserDao.findByMerchantUserId(Matchers.anyLong())).thenReturn(pgMerchantUsers);
		Mockito.when(messageSource.getMessage(Matchers.anyString(), Matchers.any(Object[].class), Matchers.any(Locale.class)))
				.thenThrow(new NullPointerException());
		Assert.assertNotNull(loginServiceImpl.changdPassword(Long.parseLong("123"), "abc", "Chatak@123!"));  //ReBrand
	}

	@Test
	public void testGetUserProfile() throws ChatakMerchantException {
		List<PGMerchantUsers> pgMerchantUsersList = new ArrayList<>();
		pgMerchantUsers = new PGMerchantUsers();
		pgMerchantUsers.setAddress("asdf");
		pgMerchantUsers.setPgMerchantId(Long.parseLong("123"));
		pgMerchantUsers.setId(Long.parseLong("12"));
		pgMerchantUsersList.add(pgMerchantUsers);
		PGMerchant pgMerchant = new PGMerchant();
		pgMerchant.setPgMerchantUsers(pgMerchantUsersList);
		Mockito.when(merchantUserDao.findByMerchantUserId(Matchers.anyLong())).thenReturn(pgMerchantUsers);
		Mockito.when(merchantProfileDao.getMerchantById(Matchers.anyLong())).thenReturn(pgMerchant);
		Assert.assertNotNull(loginServiceImpl.getUserProfile(Long.parseLong("12345")));

	}

	@Test(expected = ChatakMerchantException.class)
	public void testGetUserProfileException() throws ChatakMerchantException {
		List<PGMerchantUsers> pgMerchantUsersList = new ArrayList<>();
		pgMerchantUsers = new PGMerchantUsers();
		pgMerchantUsers.setAddress("asdf");
		pgMerchantUsers.setPgMerchantId(Long.parseLong("123"));
		pgMerchantUsers.setId(Long.parseLong("12"));
		pgMerchantUsersList.add(pgMerchantUsers);
		PGMerchant pgMerchant = new PGMerchant();
		pgMerchant.setPgMerchantUsers(pgMerchantUsersList);
		Mockito.when(merchantUserDao.findByMerchantUserId(Matchers.anyLong())).thenReturn(pgMerchantUsers);
		Mockito.when(merchantProfileDao.getMerchantById(Matchers.anyLong())).thenThrow(new NullPointerException());
		Assert.assertNotNull(loginServiceImpl.getUserProfile(Long.parseLong("12345")));

	}

	@Test
	public void testChangeUserProfile() throws ChatakMerchantException {
		UserProfileRequest userProfileRequest = new UserProfileRequest();
		pgMerchantUsers = new PGMerchantUsers();
		PGMerchant pgMerchant = new PGMerchant();
		pgMerchantUsers.setId(Long.parseLong("123"));
		pgMerchantUsers.setPgMerchantId(Long.parseLong("123"));
		Mockito.when(merchantProfileDao.getMerchantById(Matchers.anyLong())).thenReturn(pgMerchant);
		Mockito.when(merchantUserDao.createOrUpdateUser(Matchers.any(PGMerchantUsers.class))).thenReturn(pgMerchantUsers);
		Mockito.when(merchantUserDao.findByMerchantUserId(Matchers.anyLong())).thenReturn(pgMerchantUsers);
		loginServiceImpl.changeUserProfile(userProfileRequest);

	}

	@Test(expected = ChatakMerchantException.class)
	public void testChangeUserProfileException() throws ChatakMerchantException {
		UserProfileRequest userProfileRequest = new UserProfileRequest();
		pgMerchantUsers = new PGMerchantUsers();
		PGMerchant pgMerchant = new PGMerchant();
		pgMerchantUsers.setId(Long.parseLong("123"));
		pgMerchantUsers.setPgMerchantId(Long.parseLong("123"));
		Mockito.when(merchantProfileDao.getMerchantById(Matchers.anyLong())).thenReturn(pgMerchant);
		Mockito.when(merchantUserDao.createOrUpdateUser(Matchers.any(PGMerchantUsers.class))).thenReturn(pgMerchantUsers);
		Mockito.when(merchantUserDao.findByMerchantUserId(Matchers.anyLong())).thenThrow(new NullPointerException());
		loginServiceImpl.changeUserProfile(userProfileRequest);
	}

	@Test
	public void testUserExist() throws ChatakMerchantException {
		pgMerchantUsers = new PGMerchantUsers();
		pgMerchantUsers.setStatus(0);
		pgMerchantUsers.setEmail("xyz@gmail.com");
		pgMerchantUsers.setId(Long.parseLong("123"));
		Mockito.when(merchantUserDao.findByUserName(Matchers.anyString())).thenReturn(pgMerchantUsers);
		Assert.assertNotNull(loginServiceImpl.userExist("aaa", "www.google.com"));
	}

	@Test(expected = ChatakMerchantException.class)
	public void testUserExistNull() throws ChatakMerchantException {
		Assert.assertNotNull(loginServiceImpl.userExist("aaa", "www.google.com"));
	}

	@Test
	public void testValidToken() throws ChatakMerchantException {
		pgMerchantUsers = new PGMerchantUsers();
		pgMerchantUsers.setId(Long.parseLong("222"));
		pgMerchantUsers.setEmailVerified(0);
		Mockito.when(merchantUserDao.findByAdminUserIdAndEmailToken(Matchers.anyLong(), Matchers.anyString()))
				.thenReturn(pgMerchantUsers);
		Mockito.when(merchantUserDao.findByMerchantUserId(Matchers.anyLong())).thenReturn(pgMerchantUsers);
		Assert.assertNotNull(loginServiceImpl.validToken(Long.parseLong("12345"), "bbbb"));
	}

	@Test(expected = ChatakMerchantException.class)
	public void testResetPassword() throws ChatakMerchantException {
		ResetPasswordData resetPasswordData = new ResetPasswordData();
		pgMerchantUsers = new PGMerchantUsers();
		pgMerchantUsers.setEmailVerified(0);
		resetPasswordData.setNewPassword("Ipsdiy@123!");
		pgMerchantUsers.setMerPassword("900150983CD24FB0D6963F7D28E17F72");
		pgMerchantUsers.setPreviousPasswords("E8CE7A0A002060AAADC0ACF3C4158339");
		Mockito.when(merchantUserDao.createOrUpdateUser(Matchers.any(PGMerchantUsers.class))).thenReturn(pgMerchantUsers);
		Mockito.when(merchantUserDao.findByMerchantUserId(Matchers.anyLong())).thenReturn(pgMerchantUsers);

		loginServiceImpl.resetPassword(resetPasswordData, Long.parseLong("123"));
	}

	@Test(expected = ChatakMerchantException.class)
	public void testResetPasswordNotEqulZero() throws ChatakMerchantException {
		ResetPasswordData resetPasswordData = new ResetPasswordData();
		pgMerchantUsers = new PGMerchantUsers();
		pgMerchantUsers.setEmailVerified(1);
		resetPasswordData.setNewPassword("MD5");
		pgMerchantUsers.setMerPassword("900150983CD24FB0D6963F7D28E17F72");
		pgMerchantUsers.setPreviousPasswords("E8CE7A0A002060AAADC0ACF3C4158339");
		Mockito.when(merchantUserDao.createOrUpdateUser(Matchers.any(PGMerchantUsers.class))).thenReturn(pgMerchantUsers);
		Mockito.when(merchantUserDao.findByMerchantUserId(Matchers.anyLong())).thenReturn(pgMerchantUsers);
		loginServiceImpl.resetPassword(resetPasswordData, Long.parseLong("123"));
	}

	@Test(expected = ChatakMerchantException.class)
	public void testResetPasswordException() throws ChatakMerchantException {
		ResetPasswordData resetPasswordData = new ResetPasswordData();
		pgMerchantUsers = new PGMerchantUsers();
		pgMerchantUsers.setEmailVerified(1);
		resetPasswordData.setNewPassword("MD5");
		pgMerchantUsers.setMerPassword("900150983CD24FB0D6963F7D28E17F72");
		pgMerchantUsers.setPreviousPasswords("E8CE7A0A002060AAADC0ACF3C4158339");
		Mockito.when(merchantUserDao.createOrUpdateUser(Matchers.any(PGMerchantUsers.class))).thenReturn(pgMerchantUsers);
		Mockito.when(merchantUserDao.findByMerchantUserId(Matchers.anyLong())).thenThrow(new NullPointerException());
		loginServiceImpl.resetPassword(resetPasswordData, Long.parseLong("123"));
	}

}
