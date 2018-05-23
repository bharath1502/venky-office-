package com.chatak.pg.acq.dao;

import java.util.List;

import com.chatak.pg.acq.dao.model.PGUserRoles;
import com.chatak.pg.model.UserRolesDTO;



public interface UsersRoleDao
{
   public PGUserRoles saveRole(PGUserRoles pGUserRoles);
   
   public List<UserRolesDTO> searchRoles(UserRolesDTO userRolesDTO);
   
   public PGUserRoles getRoleOnRoleName(String roleName);
   
   public List<UserRolesDTO> getRoles();
   
   public PGUserRoles findByRoleId(Long roleId);
   
   public boolean isActiveRole(Long roleId);

}
