package com.chatak.merchant.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import com.chatak.merchant.exception.ChatakMerchantException;
import com.chatak.pg.acq.dao.RolesFeatureMappingDao;
import com.chatak.pg.acq.dao.UsersRoleDao;
import com.chatak.pg.acq.dao.model.PGUserRoles;
import com.chatak.pg.model.UserRolesDTO;

@RunWith(MockitoJUnitRunner.class)
public class RoleServiceImplTest {

	@InjectMocks
	private RoleServiceImpl roleServiceImpl = new RoleServiceImpl();

	@Mock
	UsersRoleDao usersRoleDao;

	@Mock
	RolesFeatureMappingDao rolesFeatureMappingDao;

	@Mock
	UserRolesDTO userRolesDTO;

	@Mock
	PGUserRoles pgUserRoles;

	@Test
	public void testRoleList() throws ChatakMerchantException {
		List<UserRolesDTO> roleList = new ArrayList<>();
		Mockito.when(usersRoleDao.searchRoles(Matchers.any(UserRolesDTO.class))).thenReturn(roleList);
		roleServiceImpl.roleList(userRolesDTO);
	}

	@Test(expected = ChatakMerchantException.class)
	public void testRoleListException() throws ChatakMerchantException {
		Mockito.when(usersRoleDao.searchRoles(Matchers.any(UserRolesDTO.class))).thenThrow(new NullPointerException());
		roleServiceImpl.roleList(userRolesDTO);
	}

	@Test
	public void testGetFeature() throws NumberFormatException, ChatakMerchantException {
		roleServiceImpl.getFeature(Long.parseLong("12"));
	}

	@Test
	public void testGetRoleList() throws ChatakMerchantException {
		List<UserRolesDTO> roleList = new ArrayList<>();
		Mockito.when(usersRoleDao.getRoles()).thenReturn(roleList);
		roleServiceImpl.getRoleList();
	}

	@Test(expected = ChatakMerchantException.class)
	public void testGetRoleListException() throws ChatakMerchantException {
		Mockito.when(usersRoleDao.getRoles()).thenThrow(new NullPointerException());
		roleServiceImpl.getRoleList();
	}

	@Test
	public void testDeletRole() throws NumberFormatException, ChatakMerchantException {
		Mockito.when(usersRoleDao.findByRoleId(Long.parseLong("123"))).thenReturn(pgUserRoles);
		roleServiceImpl.deletRole(Long.parseLong("123"));
	}

	@Test(expected = ChatakMerchantException.class)
	public void testDeletRoleException() throws NumberFormatException, ChatakMerchantException {
		Mockito.when(usersRoleDao.findByRoleId(Long.parseLong("123"))).thenThrow(new NullPointerException());
		roleServiceImpl.deletRole(Long.parseLong("123"));
	}

	@Test
	public void testGetFeatureList() {
		List<UserRolesDTO> roleList = new ArrayList<>();
		roleServiceImpl.getFeatureList(roleList);
	}

}
