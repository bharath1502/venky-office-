package com.chatak.acquirer.admin.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import com.chatak.acquirer.admin.exception.ChatakAdminException;
import com.chatak.acquirer.admin.service.impl.RoleServiceImpl;
import com.chatak.pg.acq.dao.RoleDao;
import com.chatak.pg.acq.dao.RolesFeatureMappingDao;
import com.chatak.pg.acq.dao.UserRoleFeatureMapDao;
import com.chatak.pg.acq.dao.UsersRoleDao;
import com.chatak.pg.acq.dao.model.PGFeature;
import com.chatak.pg.acq.dao.model.PGRolesFeatureMapping;
import com.chatak.pg.acq.dao.model.PGUserRoles;
import com.chatak.pg.model.FeatureDTO;
import com.chatak.pg.model.UserRoleDTO;
import com.chatak.pg.model.UserRolesDTO;

@RunWith(MockitoJUnitRunner.class)
public class RoleServiceImplTest {

	@InjectMocks
	RoleServiceImpl roleServiceImpl = new RoleServiceImpl();

	@Mock
	UsersRoleDao usersRoleDao;

	@Mock
	RolesFeatureMappingDao rolesFeatureMappingDao;

	@Mock
	RoleDao roleDao;

	@Mock
	UserRoleFeatureMapDao userRoleFeatureMapDao;

	@Mock
	HttpServletRequest request;

	@Test
	public void testAddRole() throws ChatakAdminException {
		PGUserRoles pgUserRoles = new PGUserRoles();

		Mockito.when(usersRoleDao.saveRole(Matchers.any(PGUserRoles.class))).thenReturn(pgUserRoles);
		roleServiceImpl.addRole(request);
	}

	@Test
	public void testAddRoleChatakAdminException() throws ChatakAdminException {
		PGUserRoles pgRoleData = new PGUserRoles();
		Mockito.when(usersRoleDao.getRoleOnRoleName(Matchers.anyString())).thenReturn(pgRoleData);
		roleServiceImpl.addRole(request);
	}

	@Test
	public void testAddRoleException() throws ChatakAdminException {
		roleServiceImpl.addRole(request);
	}

	@Test
	public void testRoleList() throws ChatakAdminException {
		UserRolesDTO userRolesDTO = new UserRolesDTO();
		roleServiceImpl.roleList(userRolesDTO);
	}

	@Test(expected = ChatakAdminException.class)
	public void testRoleListException() throws ChatakAdminException {
		UserRolesDTO userRolesDTO = new UserRolesDTO();
		Mockito.when(usersRoleDao.searchRoles(Matchers.any(UserRolesDTO.class))).thenThrow(new NullPointerException());
		roleServiceImpl.roleList(userRolesDTO);
	}

	@Test
	public void testGetFeature() throws ChatakAdminException {
		roleServiceImpl.getFeature(Long.parseLong("543"));
	}

	@Test
	public void testUpdateRole() throws ChatakAdminException {
		PGUserRoles userRole = new PGUserRoles();
		Mockito.when(usersRoleDao.findByRoleId(Matchers.anyLong())).thenReturn(userRole);
		roleServiceImpl.updateRole(request);
	}

	@Test
	public void testUpdateRoleException() throws ChatakAdminException {
		roleServiceImpl.updateRole(request);
	}

	@Test
	public void testGetRoleList() throws ChatakAdminException {
		List<UserRolesDTO> userRolesDTO = new ArrayList<>();
		UserRolesDTO dto = new UserRolesDTO();
		userRolesDTO.add(dto);
		Mockito.when(usersRoleDao.getRoles()).thenReturn(userRolesDTO);
		roleServiceImpl.getRoleList();
	}

	@Test(expected = ChatakAdminException.class)
	public void testGetRoleListException() throws ChatakAdminException {
		Mockito.when(usersRoleDao.getRoles()).thenThrow(new NullPointerException());
		roleServiceImpl.getRoleList();
	}

	@Test
	public void testDeletRole() throws ChatakAdminException {
		PGUserRoles pgUserRoles = new PGUserRoles();
		Mockito.when(usersRoleDao.findByRoleId(Matchers.anyLong())).thenReturn(pgUserRoles);
		roleServiceImpl.deletRole(Long.parseLong("534"));
	}

	@Test
	public void testDeletRoleException() throws ChatakAdminException {
	    PGUserRoles pgUserRoles=new PGUserRoles();
        pgUserRoles.setCreatedBy("admin");
        Mockito.when(usersRoleDao.findByRoleId(Matchers.anyLong())).thenReturn(pgUserRoles);
		roleServiceImpl.deletRole(Long.parseLong("534"));
	}

	@Test
	public void testGetFeatureList() throws ChatakAdminException {
		List<UserRolesDTO> roleList = new ArrayList<>();
		UserRolesDTO dto = new UserRolesDTO();
		roleList.add(dto);
		roleServiceImpl.getFeatureList(roleList);
	}

	@Test
	public void testGetAdminFeatureForEntityType() throws ChatakAdminException {
		PGRolesFeatureMapping adminFeatureRequest = new PGRolesFeatureMapping();
		roleServiceImpl.getAdminFeatureForEntityType(adminFeatureRequest);
	}

	@Test
	public void testGetAdminFeatureForEntity() throws ChatakAdminException {
		List<PGFeature> pgFeature = new ArrayList<>();
		PGFeature feature = new PGFeature();
		FeatureDTO featureDTO = new FeatureDTO();
		pgFeature.add(feature);
		Mockito.when(roleDao.searchRole(Matchers.anyString())).thenReturn(pgFeature);
		roleServiceImpl.getAdminFeatureForEntityType(featureDTO);
	}

	@Test
	public void testCreateRole() throws ReflectiveOperationException {
		UserRoleDTO userRoleDTO = new UserRoleDTO();
		List<PGUserRoles> roles = new ArrayList<>();
		PGUserRoles pgUserRoles = new PGUserRoles();
		userRoleDTO.setRoleName("abcd");
		pgUserRoles.setStatus(0);
		roles.add(pgUserRoles);
		Mockito.when(roleDao.findByRoleName(Matchers.anyString())).thenReturn(roles);
		roleServiceImpl.createRole(userRoleDTO);
	}

	@Test
	public void testCreateRoleElse() throws ReflectiveOperationException {
		UserRoleDTO userRoleDTO = new UserRoleDTO();
		List<PGUserRoles> roles = new ArrayList<>();
		PGUserRoles pgUserRoles = new PGUserRoles();
		userRoleDTO.setRoleName("abcd");
		userRoleDTO.setFeature(new String[] { "1", "2" });
		pgUserRoles.setStatus(1);
		roles.add(pgUserRoles);
		Mockito.when(roleDao.findByRoleName(Matchers.anyString())).thenReturn(roles);
		Mockito.when(roleDao.createOrUpdatePGRole(Matchers.any(PGUserRoles.class))).thenReturn(pgUserRoles);
		roleServiceImpl.createRole(userRoleDTO);
	}

	@Test
	public void testCreateRoleNull() throws ReflectiveOperationException {
		UserRoleDTO userRoleDTO = new UserRoleDTO();
		roleServiceImpl.createRole(userRoleDTO);
	}

	@Test(expected = NullPointerException.class)
	public void testGetRoleDetails() throws ReflectiveOperationException {
		PGUserRoles hostUserRoles = new PGUserRoles();
		UserRoleDTO userRolesDTO = new UserRoleDTO();
		Map<Long, List<FeatureDTO>> featureMap = new HashMap<>();
		List<FeatureDTO> dtos = new ArrayList<>();
		List<Long> existingFeature = new ArrayList<>();
		Long long1 = new Long("1212");
		existingFeature.add(long1);
		FeatureDTO feature = new FeatureDTO();
		feature.setFeatureLevel(0l);
		userRolesDTO.setRoleId(Long.parseLong("543"));
		feature.setFeatureId(Long.parseLong("543"));
		List<PGFeature> hostKTCFeature = new ArrayList<>();
		PGFeature pgFeature = new PGFeature();
		hostKTCFeature.add(pgFeature);
		dtos.add(feature);
		featureMap.put(Long.parseLong("0"), dtos);
		Mockito.when(roleDao.findByUserRoleId(Matchers.anyLong())).thenReturn(hostUserRoles);
		Mockito.when(roleDao.searchRole(Matchers.anyString())).thenReturn(hostKTCFeature);
		Mockito.when(roleDao.getFeatureDataOnRoleIdData(Matchers.anyLong())).thenReturn(existingFeature);
		roleServiceImpl.getRoleDetails(Long.parseLong("543"));
	}

	@Test
	public void testGetRoleDetailsElse() throws ReflectiveOperationException {
		PGUserRoles hostUserRoles = new PGUserRoles();
		Mockito.when(roleDao.findByUserRoleId(Matchers.anyLong())).thenReturn(hostUserRoles);
		roleServiceImpl.getRoleDetails(Long.parseLong("543"));
	}

	@Test
	public void testUpdateRoles() throws ChatakAdminException {
		PGUserRoles pgUserRoles = new PGUserRoles();
		UserRoleDTO userRolesDTO = new UserRoleDTO();
		userRolesDTO.setFeature(new String[] { "1", "2" });
		Mockito.when(roleDao.findByUserRoleId(Matchers.anyLong())).thenReturn(pgUserRoles);
		Mockito.when(roleDao.createOrUpdatePGRole(Matchers.any(PGUserRoles.class))).thenReturn(pgUserRoles);
		roleServiceImpl.updateRoles(userRolesDTO);
	}

	@Test
	public void testGetRoleListByType() throws ChatakAdminException {
		List<PGUserRoles> list = new ArrayList<>();
		PGUserRoles pgUserRoles = new PGUserRoles();
		list.add(pgUserRoles);
		Mockito.when(roleDao.findByRoleType(Matchers.anyString())).thenReturn(list);
		roleServiceImpl.getRoleListByType("3454");

	}

	@Test
	public void testChangeRoleStatus() throws ChatakAdminException {
		PGUserRoles pgRoles = new PGUserRoles();
		UserRoleDTO userRolesDTO = new UserRoleDTO();
		userRolesDTO.setStatus("Active");
		Mockito.when(roleDao.findByUserRoleId(Matchers.anyLong())).thenReturn(pgRoles);
		roleServiceImpl.changeRoleStatus(userRolesDTO);
	}

	@Test
	public void testChangeRoleStatusElse() throws ChatakAdminException {
		PGUserRoles pgRoles = new PGUserRoles();
		UserRoleDTO userRolesDTO = new UserRoleDTO();
		Mockito.when(roleDao.findByUserRoleId(Matchers.anyLong())).thenReturn(pgRoles);
		roleServiceImpl.changeRoleStatus(userRolesDTO);
	}

	@Test
	public void testValidateRolename() throws ChatakAdminException {
		PGUserRoles pgRoleData = new PGUserRoles();
		pgRoleData.setStatus(0);
		Mockito.when(usersRoleDao.getRoleOnRoleName(Matchers.anyString())).thenReturn(pgRoleData);
		roleServiceImpl.validateRolename("abcde");
	}

	@Test
	public void testValidateRolenameElse() throws ChatakAdminException {
		PGUserRoles pgRoleData = new PGUserRoles();
		pgRoleData.setStatus(0);
		Mockito.when(usersRoleDao.getRoleOnRoleName(Matchers.anyString())).thenReturn(pgRoleData);
		roleServiceImpl.validateRolename("abcde");
	}

	@Test
	public void testValidateRolenameNull() throws ChatakAdminException {
		roleServiceImpl.validateRolename("abcde");
	}

}
