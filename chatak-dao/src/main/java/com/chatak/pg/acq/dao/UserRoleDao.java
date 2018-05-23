package com.chatak.pg.acq.dao;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.chatak.pg.acq.dao.model.UserRole;
import com.chatak.pg.model.RoleDTO;

public interface UserRoleDao {
	
	public UserRole createRole(UserRole role,Long[] featureList)throws DataAccessException;
	
	public UserRole updateRole(UserRole role,Long[] featureList)throws DataAccessException;
	
	public UserRole updateStatus(UserRole role)throws DataAccessException;
	
	public UserRole findByUserRoleId(Long roleId)throws DataAccessException;
	
	public List<UserRole> findByRoleName(String roleName)throws DataAccessException;
	
	public List<UserRole> findByRoleType(String roleType, String status)throws DataAccessException;
	
	public List<UserRole> searchRole(RoleDTO userRole);
	
	public List<UserRole> findByStatus(String status);

}
