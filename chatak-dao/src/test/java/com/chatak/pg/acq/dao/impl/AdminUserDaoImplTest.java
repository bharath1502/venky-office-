/**
 * 
 */
package com.chatak.pg.acq.dao.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import com.chatak.pg.acq.dao.model.PGAdminUser;
import com.chatak.pg.acq.dao.repository.AdminUserDaoRepository;
import com.chatak.pg.model.AdminUserDTO;
import com.chatak.pg.model.GenericUserDTO;

/**
 * @Author: Girmiti Software
 * @Date: 15-Feb-2018
 * @Time: 3:12:31 pm
 * @Version: 1.0
 * @Comments:
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class AdminUserDaoImplTest {

  private static final String USERNAME = "userName";
  private static final String EMAIL = "email";
  private static final String ROLENAME = "roleName";
  private static final String USERTYPE = "userType";
  private static final Long ID = Long.parseLong("12");
  private static final String NAME = "name";
  private static final String PHONE = "phone";
  private static final String STATUS = "status";
  private static final Integer STATUS_INT = Integer.parseInt("10");
  
	@InjectMocks
	AdminUserDaoImpl adminUserDaoImpl;

	@Mock
	private EntityManager entityManager;

	@Mock
	Query query;

	@Mock
	private EntityManagerFactory emf;

	@Mock
	AdminUserDaoRepository adminUserDaoRepository;

	@Test
	public void testFindByUserNameAndUserType() {
		adminUserDaoImpl.findByUserNameAndUserType("123", "234");
	}

	@Test
	public void testFindByUserName() {
		adminUserDaoImpl.findByUserName("123");
	}

	@Test
	public void testFindByEmail() {
		adminUserDaoImpl.findByEmail("123", "54");
	}

	@Test
	public void testFindByEmailAndUserType() {
		adminUserDaoImpl.findByEmailAndUserType("123", "54");
	}

	@Test
	public void testFindByAdminUserId() {
		adminUserDaoImpl.findByAdminUserId(Long.parseLong("123"));
	}

	@Test
	public void testCreateOrUpdateUser() {
		PGAdminUser adminUser = new PGAdminUser();
		adminUserDaoImpl.createOrUpdateUser(adminUser);
	}

	@Test
	public void testFindByUserRoleId() {
		adminUserDaoImpl.findByUserRoleId(Long.parseLong("123"));
	}

	@Test
	public void testCreateOrUpdateUsers() {
		List<PGAdminUser> adminUserList = new ArrayList<>();
		adminUserDaoImpl.createOrUpdateUsers(adminUserList);
	}

	@Test
	public void testSearchUser() {
		List<AdminUserDTO> userRespList;
		AdminUserDTO userTo = new AdminUserDTO();
		userTo.setPageIndex(1);
		userTo.setPageSize(Integer.parseInt("2"));
		List<PGAdminUser> adminuserList = new ArrayList<>();
		PGAdminUser pGAdminUser = new PGAdminUser();
		adminuserList.add(pGAdminUser);

		List<Object> tuplelist = new ArrayList<>();
		Object objects[] = new Object[Integer.parseInt("8")];
		objects[Integer.parseInt("0")] = ID;
		objects[Integer.parseInt("1")] = NAME;
		objects[Integer.parseInt("2")] = NAME;
		objects[Integer.parseInt("3")] = STATUS_INT;
		objects[Integer.parseInt("4")] = PHONE;
		objects[Integer.parseInt("5")] = NAME;
		objects[Integer.parseInt("6")] = STATUS;
		objects[Integer.parseInt("7")] = PHONE;
		tuplelist.add(objects);

		Mockito.when(entityManager.getDelegate()).thenReturn(Object.class);
		Mockito.when(entityManager.createQuery(Matchers.anyString())).thenReturn(query);
		Mockito.when(entityManager.getEntityManagerFactory()).thenReturn(emf);
		Mockito.when(query.getResultList()).thenReturn(adminuserList, tuplelist);
		userRespList = adminUserDaoImpl.searchUser(userTo);
		Assert.assertNotNull(userRespList);
	}

	@Test
	public void testAuthenticateAcquirerAdmin() {
		List<PGAdminUser> adminUsers = new ArrayList<>();
		PGAdminUser adminUser = new PGAdminUser();
		adminUsers.add(adminUser);
		Mockito.when(adminUserDaoRepository.findByEmailAndPassword(Matchers.anyString(), Matchers.anyString()))
				.thenReturn(adminUsers);
		adminUserDaoImpl.authenticateAcquirerAdmin("123", "111");
	}

	@Test
	public void testAuthenticateAcquirerAdminException() {
		Mockito.when(adminUserDaoRepository.findByEmailAndPassword(Matchers.anyString(), Matchers.anyString()))
				.thenThrow(new NullPointerException());
		adminUserDaoImpl.authenticateAcquirerAdmin("123", "111");
	}

	@Test
	public void testAuthenticateAcquirerAdminUser() {
		List<PGAdminUser> adminUsers = new ArrayList<>();
		PGAdminUser adminUser = new PGAdminUser();
		adminUser.setStatus(0);
		adminUsers.add(adminUser);
		Mockito.when(adminUserDaoRepository.findByEmailAndPassword(Matchers.anyString(), Matchers.anyString()))
				.thenReturn(adminUsers);
		adminUserDaoImpl.authenticateAcquirerAdminUser("123", "111");
	}

	@Test
	public void testAuthenticateAcquirerAdminUserException() {
		Mockito.when(adminUserDaoRepository.findByEmailAndPassword(Matchers.anyString(), Matchers.anyString()))
				.thenThrow(new NullPointerException());
		adminUserDaoImpl.authenticateAcquirerAdminUser("123", "111");
	}

	@Test
	public void testFindByEmailString() {
		adminUserDaoImpl.findByEmail("123");
	}

	@Test
	public void testFindByAdminUserIdAndEmailToken() {
		adminUserDaoImpl.findByAdminUserIdAndEmailToken(Long.parseLong("43"), "123");
	}

	@Test
	public void testSearchGenericUser() {
		List<GenericUserDTO> userRespList;
		GenericUserDTO userTo = new GenericUserDTO();
		userTo.setPageIndex(1);
		userTo.setPageSize(Integer.parseInt("25"));
		List<PGAdminUser> adminuserList = new ArrayList<>();
		PGAdminUser pGAdminUser = new PGAdminUser();
		adminuserList.add(pGAdminUser);
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());

		List<Object> tuplelist = new ArrayList<>();
		Object objects[] = new Object[Integer.parseInt("11")];
	    objects[Integer.parseInt("0")] = ID;
	    objects[Integer.parseInt("1")] = NAME;
		objects[Integer.parseInt("2")] = NAME;
		objects[Integer.parseInt("3")] = STATUS_INT;
		objects[Integer.parseInt("4")] = PHONE;
		objects[Integer.parseInt("5")] = USERNAME;
		objects[Integer.parseInt("6")] = EMAIL;
		objects[Integer.parseInt("7")] = ROLENAME;
		objects[Integer.parseInt("8")] = USERTYPE;
		objects[Integer.parseInt("9")] = timestamp;
		objects[Integer.parseInt("10")] = timestamp;

		tuplelist.add(objects);

		Mockito.when(entityManager.getDelegate()).thenReturn(Object.class);
		Mockito.when(entityManager.createQuery(Matchers.anyString())).thenReturn(query);
		Mockito.when(entityManager.getEntityManagerFactory()).thenReturn(emf);
		Mockito.when(query.getResultList()).thenReturn(adminuserList, tuplelist);
		userRespList = adminUserDaoImpl.searchGenericUser(userTo);
		Assert.assertNotNull(userRespList);

		adminUserDaoImpl.searchGenericUser(userTo);
	}

	@Test
	public void testGetRoleListAdmin() {
		adminUserDaoImpl.getRoleListAdmin();
	}

	@Test
	public void testFindByUserNameAndStatus() {
		adminUserDaoImpl.findByUserNameAndStatus("543");
	}

	@Test
	public void testSearchAdminUserList() {
		List<PGAdminUser> userAdminList = new ArrayList<>();
		PGAdminUser adminUser = new PGAdminUser();
		userAdminList.add(adminUser);
		Mockito.when(adminUserDaoRepository.findByPassRetryCount(Matchers.anyInt())).thenReturn(userAdminList);
		adminUserDaoImpl.searchAdminUserList();
	}

}
