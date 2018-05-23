package com.chatak.pg.acq.dao.repository;



import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;

import com.chatak.pg.acq.dao.model.UserRole;

public interface UserRoleDaoRepository extends JpaRepository<UserRole,Long>,QueryDslPredicateExecutor<UserRole>{
	
	/**
	 * @param userRoleId
	 * @return
	 */
	public UserRole findByUserRoleId(Long userRoleId) throws DataAccessException;
	
	/**
	 * @param roleName
	 * @return
	 * @throws DataAccessException
	 */
	public List<UserRole> findByRoleNameIgnoreCase(String roleName) throws DataAccessException;
	
	
	public List<UserRole> findByStatusIgnoreCase(String status) throws DataAccessException;

	public List<UserRole> findByRoleType(String roleType) throws DataAccessException;

}
