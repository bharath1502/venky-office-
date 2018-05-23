package com.chatak.acquirer.admin.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.context.MessageSource;

import com.chatak.acquirer.admin.exception.ChatakAdminException;
import com.chatak.acquirer.admin.model.UserData;
import com.chatak.acquirer.admin.service.impl.UserServiceImpl;
import com.chatak.mailsender.service.MailServiceManagement;
import com.chatak.pg.acq.dao.AdminUserDao;
import com.chatak.pg.acq.dao.MerchantDao;
import com.chatak.pg.acq.dao.MerchantProfileDao;
import com.chatak.pg.acq.dao.MerchantUpdateDao;
import com.chatak.pg.acq.dao.MerchantUserDao;
import com.chatak.pg.acq.dao.UserActivityLogDao;
import com.chatak.pg.acq.dao.model.PGAdminUser;
import com.chatak.pg.acq.dao.model.PGMerchant;
import com.chatak.pg.acq.dao.model.PGMerchantUsers;
import com.chatak.pg.bean.Response;
import com.chatak.pg.model.AdminUserDTO;
import com.chatak.pg.model.GenericUserDTO;
import com.chatak.pg.util.Constants;
import com.chatak.prepaid.velocity.IVelocityTemplateCreator;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceImplTest {

	@InjectMocks
	UserServiceImpl userServiceImpl = new UserServiceImpl();

	@Mock
	AdminUserDao adminUserDao;

	@Mock
	UserActivityLogDao userActivityLogDao;

	@Mock
	MerchantUserDao merchantUserDao;

	@Mock
	MerchantDao merchantDao;

	@Mock
	MessageSource messageSource;

	@Mock
	MailServiceManagement mailingServiceManagement;

	@Mock
	private IVelocityTemplateCreator iVelocityTemplateCreator;

	@Mock
	MerchantUpdateDao merchantUpdateDao;

	@Mock
	MerchantProfileDao merchantProfileDao;

	@Test
	public void testSaveUser() throws ChatakAdminException {
		UserData userData = new UserData();
		PGMerchant merchant = new PGMerchant();
		PGMerchantUsers pgMerchantUsers = new PGMerchantUsers();
		userData.setEmailId("abcde");
		userData.setRoleType(Constants.TYPE_MERCHANT);
		userData.setCreatedBy("abc");
		Mockito.when(merchantUpdateDao.getMerchant(Matchers.anyString())).thenReturn(merchant);
		Mockito.when(merchantUserDao.createOrUpdateUser(Matchers.any(PGMerchantUsers.class)))
				.thenReturn(pgMerchantUsers);
		userServiceImpl.saveUser(userData);
	}

	@Test
	public void testSaveUserElse() throws ChatakAdminException {
		UserData userData = new UserData();
		PGMerchant merchant = new PGMerchant();
		PGMerchantUsers pgMerchantUsers = new PGMerchantUsers();
		userData.setEmailId("abcde");
		userData.setRoleType("adcd");
		userData.setCreatedBy("abc");
		Mockito.when(merchantUpdateDao.getMerchant(Matchers.anyString())).thenReturn(merchant);
		Mockito.when(merchantUserDao.createOrUpdateUser(Matchers.any(PGMerchantUsers.class)))
				.thenReturn(pgMerchantUsers);
		userServiceImpl.saveUser(userData);
	}

	@Test(expected = ChatakAdminException.class)
	public void testSaveUserException() throws ChatakAdminException {
		UserData userData = new UserData();
		PGMerchant merchant = null;
		userData.setEmailId("abcde");
		userData.setRoleType(Constants.TYPE_MERCHANT);
		userData.setCreatedBy("abc");
		Mockito.when(merchantUpdateDao.getMerchant(Matchers.anyString())).thenReturn(merchant);
		userServiceImpl.saveUser(userData);
	}

	@Test
	public void testSearchUser() throws ChatakAdminException {
		AdminUserDTO adminUserDTO = new AdminUserDTO();
		userServiceImpl.searchUser(adminUserDTO);
	}

	@Test(expected = ChatakAdminException.class)
	public void testSearchUserException() throws ChatakAdminException {
		AdminUserDTO adminUserDTO = new AdminUserDTO();
		Mockito.when(adminUserDao.searchUser(Matchers.any(AdminUserDTO.class))).thenThrow(new NullPointerException());
		userServiceImpl.searchUser(adminUserDTO);
	}

	@Test
	public void testGetUserData() throws ChatakAdminException {
		PGAdminUser adminUser = new PGAdminUser();
		Mockito.when(adminUserDao.findByAdminUserId(Matchers.anyLong())).thenReturn(adminUser);
		userServiceImpl.getUserData(Long.parseLong("5234"));
	}

	@Test(expected = ChatakAdminException.class)
	public void testGetUserDataException() throws ChatakAdminException {
		Mockito.when(adminUserDao.findByAdminUserId(Matchers.anyLong())).thenThrow(new NullPointerException());
		userServiceImpl.getUserData(Long.parseLong("5234"));
	}

	@Test(expected = ChatakAdminException.class)
	public void testUpdateUser() throws ChatakAdminException {
		UserData userData = new UserData();
		PGMerchantUsers pgMerchantUsers = new PGMerchantUsers();
		PGMerchant pgMerchant = new PGMerchant();
		pgMerchant.setEmailId("asdf");
		pgMerchantUsers.setEmail("asdf");
		pgMerchant.setStatus(1);
		userData.setRoleType(Constants.TYPE_MERCHANT);
		Mockito.when(merchantUserDao.findByMerchantUserId(Matchers.anyLong())).thenReturn(pgMerchantUsers);
		Mockito.when(merchantUserDao.findById(Matchers.anyLong())).thenReturn(pgMerchant);
		userServiceImpl.updateUser(userData);
	}

	@Test
	public void testUpdateUserStatus() throws ChatakAdminException {
		UserData userData = new UserData();
		PGMerchantUsers pgMerchantUsers = new PGMerchantUsers();
		PGMerchant pgMerchant = new PGMerchant();
		pgMerchant.setEmailId("asdf");
		pgMerchantUsers.setEmail("asdf");
		pgMerchant.setStatus(0);
		userData.setRoleType(Constants.TYPE_MERCHANT);
		Mockito.when(merchantUserDao.findByMerchantUserId(Matchers.anyLong())).thenReturn(pgMerchantUsers);
		Mockito.when(merchantUserDao.findById(Matchers.anyLong())).thenReturn(pgMerchant);
		userServiceImpl.updateUser(userData);
	}

	@Test
	public void testUpdateUserElse() throws ChatakAdminException {
		UserData userData = new UserData();
		PGAdminUser adminUserListByEmail = new PGAdminUser();
		userData.setRoleType("pqrs");
		adminUserListByEmail.setAdminUserId(Long.parseLong("53"));
		userData.setUserId(Long.parseLong("53"));
		Mockito.when(adminUserDao.findByUserNameAndUserType(Matchers.anyString(), Matchers.anyString()))
				.thenReturn(adminUserListByEmail);
		userServiceImpl.updateUser(userData);
	}

	@Test
	public void testChangeUserStatus() throws ChatakAdminException {
		UserData userData = new UserData();
		PGMerchantUsers pgMerchantUsers = new PGMerchantUsers();
		PGMerchant pgMerchant = new PGMerchant();
		userData.setUserId(Long.parseLong("43"));
		userData.setRoleType(Constants.TYPE_MERCHANT);
		pgMerchantUsers.setPgMerchantId(Long.parseLong("43"));
		Mockito.when(merchantUserDao.findByMerchantUserId(Long.parseLong("545"))).thenReturn(pgMerchantUsers);
		Mockito.when(merchantUserDao.findById(Matchers.anyLong())).thenReturn(pgMerchant);
		userServiceImpl.changeUserStatus(userData, "Active");
	}

	@Test
	public void testChangeUserStatusActive() throws ChatakAdminException {
		UserData userData = new UserData();
		PGAdminUser pgAdminUser = new PGAdminUser();
		userData.setUserId(Long.parseLong("43"));
		pgAdminUser.setAdminUserId(Long.parseLong("43"));
		userData.setRoleType("xyz");
		Mockito.when(adminUserDao.findByAdminUserId(Matchers.anyLong())).thenReturn(pgAdminUser);
		Mockito.when(adminUserDao.createOrUpdateUser(Matchers.any(PGAdminUser.class))).thenReturn(pgAdminUser);
		userServiceImpl.changeUserStatus(userData, "Active");
	}

	@Test
	public void testChangeUserStatusSuspended() throws ChatakAdminException {
		UserData userData = new UserData();
		PGAdminUser pgAdminUser = new PGAdminUser();
		userData.setUserId(Long.parseLong("43"));
		pgAdminUser.setAdminUserId(Long.parseLong("43"));
		userData.setRoleType("xyz");
		Mockito.when(adminUserDao.findByAdminUserId(Matchers.anyLong())).thenReturn(pgAdminUser);
		Mockito.when(adminUserDao.createOrUpdateUser(Matchers.any(PGAdminUser.class))).thenReturn(pgAdminUser);
		userServiceImpl.changeUserStatus(userData, "2");
	}

	@Test
	public void testChangeUserStatusException() throws ChatakAdminException {
		UserData userData = new UserData();
		userData.setRoleType(Constants.TYPE_MERCHANT);
		userServiceImpl.changeUserStatus(userData, "status");
	}

	@Test
	public void testChangeUserStatusChatakAdminException() throws ChatakAdminException {
		UserData userData = new UserData();
		userServiceImpl.changeUserStatus(userData, "status");
	}

	@Test
	public void testGetAllPgUserActivityLogs() throws ChatakAdminException {
		userServiceImpl.getAllPgUserActivityLogs();
	}

	@Test
	public void testSearchGenericUser() throws ChatakAdminException {
		GenericUserDTO adminUserDTO = new GenericUserDTO();
		userServiceImpl.searchGenericUser(adminUserDTO);
	}

	@Test(expected = ChatakAdminException.class)
	public void testSearchGenericUserException() throws ChatakAdminException {
		GenericUserDTO adminUserDTO = new GenericUserDTO();
		Mockito.when(adminUserDao.searchGenericUser(Matchers.any(GenericUserDTO.class)))
				.thenThrow(new NullPointerException());
		userServiceImpl.searchGenericUser(adminUserDTO);
	}

	@Test
	public void testSearchAdminUser() throws ChatakAdminException {
		GenericUserDTO adminUserDTO = new GenericUserDTO();
		userServiceImpl.searchAdminUser(adminUserDTO);
	}

	@Test(expected = ChatakAdminException.class)
	public void testSearchAdminUserException() throws ChatakAdminException {
		GenericUserDTO adminUserDTO = new GenericUserDTO();
		Mockito.when(adminUserDao.searchGenericUser(Matchers.any(GenericUserDTO.class)))
				.thenThrow(new NullPointerException());
		userServiceImpl.searchAdminUser(adminUserDTO);
	}

	@Test
	public void testSearchMerchantUser() throws ChatakAdminException {
		GenericUserDTO adminUserDTO = new GenericUserDTO();
		userServiceImpl.searchMerchantUser(adminUserDTO);
	}

	@Test(expected = ChatakAdminException.class)
	public void testSearchMerchantUserException() throws ChatakAdminException {
		GenericUserDTO adminUserDTO = new GenericUserDTO();
		Mockito.when(merchantUserDao.searchMerchantUsers(Matchers.any(GenericUserDTO.class)))
				.thenThrow(new NullPointerException());
		userServiceImpl.searchMerchantUser(adminUserDTO);
	}

	@Test
	public void testGetUserDataOnUsersGroupType() throws ChatakAdminException {
		PGMerchantUsers merchantUser = new PGMerchantUsers();
		PGMerchant merchant = new PGMerchant();
		Mockito.when(merchantUserDao.findByMerchantUserId(Matchers.anyLong())).thenReturn(merchantUser);
		Mockito.when(merchantProfileDao.getMerchantById(Matchers.anyLong())).thenReturn(merchant);
		userServiceImpl.getUserDataOnUsersGroupType(Long.parseLong("543"), Constants.TYPE_MERCHANT);
	}

	@Test
	public void testGetUserDataOnUsersGroupTypeElse() throws ChatakAdminException {
		PGAdminUser adminUser = new PGAdminUser();
		Mockito.when(adminUserDao.findByAdminUserId(Matchers.anyLong())).thenReturn(adminUser);
		userServiceImpl.getUserDataOnUsersGroupType(Long.parseLong("543"), "abcd");
	}

	@Test(expected = ChatakAdminException.class)
	public void testGetUserDataOnUsersGroupTypeException() throws ChatakAdminException {
		Mockito.when(merchantUserDao.findByMerchantUserId(Matchers.anyLong())).thenThrow(new NullPointerException());
		userServiceImpl.getUserDataOnUsersGroupType(Long.parseLong("543"), Constants.TYPE_MERCHANT);
	}

	@Test
	public void testValidateEmailId() throws ChatakAdminException {
		Response pgMerchantEmail = new Response();
		pgMerchantEmail.setErrorCode("R1");
		Mockito.when(merchantProfileDao.getUserByEmailId(Matchers.anyString())).thenReturn(pgMerchantEmail);
		userServiceImpl.validateEmailId("123");
	}

	@Test
	public void testValidateEmailIdElse() throws ChatakAdminException {
		PGAdminUser merchantUsers = new PGAdminUser();
		Mockito.when(adminUserDao.findByEmail(Matchers.anyString())).thenReturn(merchantUsers);
		userServiceImpl.validateEmailId("123");
	}

	@Test
	public void testDeleteMerchantUser() throws ChatakAdminException {
		PGMerchantUsers merchantUser = new PGMerchantUsers();
		PGMerchant pgMerchant = new PGMerchant();
		pgMerchant.setStatus(Integer.parseInt("3"));
		pgMerchant.setEmailId("abcd");
		merchantUser.setEmail("abcd");
		Mockito.when(merchantUserDao.findByMerchantUserId(Matchers.anyLong())).thenReturn(merchantUser);
		Mockito.when(merchantUserDao.findById(Matchers.anyLong())).thenReturn(pgMerchant);
		Mockito.when(merchantUserDao.createOrUpdateUser(Matchers.any(PGMerchantUsers.class))).thenReturn(merchantUser);
		userServiceImpl.deleteMerchantUser(Long.parseLong("543"), Constants.TYPE_MERCHANT);
	}

	@Test
	public void testDeleteMerchantUserElse() throws ChatakAdminException {
		PGAdminUser adminUser = new PGAdminUser();
		Mockito.when(adminUserDao.findByAdminUserId(Matchers.anyLong())).thenReturn(adminUser);
		Mockito.when(adminUserDao.createOrUpdateUser(Matchers.any(PGAdminUser.class))).thenReturn(adminUser);
		userServiceImpl.deleteMerchantUser(Long.parseLong("543"), "abcd");
	}

	@Test
	public void testValidateEmailForAdminId() throws ChatakAdminException {
		PGAdminUser merchantUsers = new PGAdminUser();
		Mockito.when(adminUserDao.findByEmail(Matchers.anyString())).thenReturn(merchantUsers);
		userServiceImpl.validateEmailForAdminId("123");
	}

	@Test
	public void testGetAdminRoleList() throws ChatakAdminException {
		userServiceImpl.getAdminRoleList();
	}

	@Test
	public void testGetMerchantRoleList() throws ChatakAdminException {
		userServiceImpl.getMerchantRoleList();
	}

	@Test
	public void testValidateUserName() throws ChatakAdminException {
		Response pgMerchantEmail = new Response();
		pgMerchantEmail.setErrorCode("R1");
		Mockito.when(merchantUpdateDao.getUserByUserName(Matchers.anyString())).thenReturn(pgMerchantEmail);
		userServiceImpl.validateUserName("56");
	}

	@Test
	public void testValidateUserNameElse() throws ChatakAdminException {
		PGAdminUser merchantUsers = new PGAdminUser();
		Mockito.when(adminUserDao.findByUserNameAndStatus(Matchers.anyString())).thenReturn(merchantUsers);
		userServiceImpl.validateUserName("56");
	}

	@Test
	public void testSearchAdminUserList() throws ChatakAdminException {
		userServiceImpl.searchAdminUserList();
	}

	@Test
	public void testSearchMerchantUserList() throws ChatakAdminException {
		userServiceImpl.searchMerchantUserList();
	}

	@Test
	public void testUnblockAdminUser() throws ChatakAdminException {
		PGAdminUser adminUser = new PGAdminUser();
		Mockito.when(adminUserDao.findByUserNameAndStatus(Matchers.anyString())).thenReturn(adminUser);
		userServiceImpl.unblockAdminUser("56");
	}

	@Test
	public void testUnblockMerchantUser() throws ChatakAdminException {
		PGMerchantUsers merchantUsers = new PGMerchantUsers();
		Mockito.when(merchantUserDao.findByUserName(Matchers.anyString())).thenReturn(merchantUsers);
		userServiceImpl.unblockMerchantUser("56");
	}

	@Test
	public void testMerchantIdByMerchantName() throws ChatakAdminException {
		PGMerchant pgMerchant = new PGMerchant();
		Mockito.when(merchantUpdateDao.getMerchant(Matchers.anyString())).thenReturn(pgMerchant);
		userServiceImpl.merchantIdByMerchantName("56");
	}

}
