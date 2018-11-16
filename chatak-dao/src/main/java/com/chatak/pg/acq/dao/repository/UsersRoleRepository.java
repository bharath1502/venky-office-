package com.chatak.pg.acq.dao.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import com.chatak.pg.acq.dao.model.PGUserRoles;

public interface UsersRoleRepository  extends JpaRepository<PGUserRoles, Long>,QuerydslPredicateExecutor<PGUserRoles> 
{
  public PGUserRoles findByRoleName(String roleName);
  
  public PGUserRoles findByRoleId(Long roleID);
  
  public PGUserRoles findByRoleIdAndStatus(Long roleID,Integer status);
  
  public List<PGUserRoles> findByRoleTypeAndStatus(String roleType,Integer status);
}
