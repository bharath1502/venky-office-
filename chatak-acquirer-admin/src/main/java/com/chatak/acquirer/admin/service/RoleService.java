package com.chatak.acquirer.admin.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.chatak.acquirer.admin.controller.model.AddRoleResponse;
import com.chatak.acquirer.admin.exception.ChatakAdminException;
import com.chatak.acquirer.admin.model.FeatureResponse;
import com.chatak.pg.acq.dao.model.PGRolesFeatureMapping;
import com.chatak.pg.bean.Response;
import com.chatak.pg.bean.RoleResponse;
import com.chatak.pg.model.EditRoleResponse;
import com.chatak.pg.model.FeatureDTO;
import com.chatak.pg.model.MposFeatures;
import com.chatak.pg.model.UserRoleDTO;
import com.chatak.pg.model.UserRolesDTO;

public interface RoleService {
	
	public AddRoleResponse addRole(HttpServletRequest request)throws ChatakAdminException;
	
	public List<UserRolesDTO> roleList(UserRolesDTO userRolesDTO)throws ChatakAdminException;
	
	public FeatureResponse getFeature(Long roleId)throws ChatakAdminException;
	
	public AddRoleResponse updateRole(HttpServletRequest request)throws ChatakAdminException;
	
	public List<UserRolesDTO> getRoleList()throws ChatakAdminException;
	
	public void deletRole(Long roleId)throws ChatakAdminException;
	
	public Map<Long, List<String>> getFeatureList(List<UserRolesDTO> roleList);

	public List<PGRolesFeatureMapping> getAdminFeatureForEntityType(PGRolesFeatureMapping adminFeatureRequest) throws ChatakAdminException;

	public FeatureResponse getAdminFeatureForEntityType(FeatureDTO featureDTO) throws ChatakAdminException;
	
	public Response createRole(UserRoleDTO userRoleDTO)throws ReflectiveOperationException;
	
	public EditRoleResponse getRoleDetails(Long userRoleId) throws ReflectiveOperationException;
	
	public Response updateRoles(UserRoleDTO userRolesDTO) throws ChatakAdminException;

	public List<UserRoleDTO> getRoleListByType(String rolesType);
	
	public Response changeRoleStatus(UserRoleDTO userRoleDTO) throws ChatakAdminException;
	
	public RoleResponse validateRolename(String roleName)throws ChatakAdminException;
	
	public List<Long> getFeaturesByEntity(String entity)throws ChatakAdminException;
	
	public FeatureResponse getFeatureDataByIds(List<Long> featureIds);
	
	public List<MposFeatures> getMposeFeature() throws ReflectiveOperationException;

} 
