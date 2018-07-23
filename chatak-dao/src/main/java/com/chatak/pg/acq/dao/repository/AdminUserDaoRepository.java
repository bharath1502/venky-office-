package com.chatak.pg.acq.dao.repository;



import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.repository.query.Param;

import com.chatak.pg.acq.dao.model.PGAdminUser;

public interface AdminUserDaoRepository extends JpaRepository<PGAdminUser,Long>,QueryDslPredicateExecutor<PGAdminUser>{
	
	/**
	 * @param userName
	 * @return
	 */
	public List<PGAdminUser> findByUserNameIgnoreCase(String userName) throws DataAccessException;
	
	public List<PGAdminUser> findByEmailIgnoreCaseAndUserType(String email, String userType) throws DataAccessException;
	
	public List<PGAdminUser> findByUserRoleId(Long userRoleId) throws DataAccessException;
	
	public PGAdminUser findByAdminUserId(Long userId) throws DataAccessException;

	@Query("select t from PGAdminUser t where t.userName=:userName and t.userType=:userType and t.status <> 3")
	public PGAdminUser findByUserNameAndUserType(@Param("userName") String userName, @Param("userType") String userType) throws DataAccessException;
	
	public  List<PGAdminUser> findByEmailAndPassword(String userEmail, String pgPassword);
	
	public PGAdminUser findByEmail(String email);
	
	@Query("select t from PGAdminUser t where t.email=:email and t.userType=:userType and t.status <> 3")
	public PGAdminUser findByEmailAndUserType(@Param("email") String email, @Param("userType") String userType);
	
	@Query("select t from PGAdminUser t where t.email=:email and t.status <> :status")
	public PGAdminUser findByEmailIdAndStatusNotLike(@Param("email") String email, @Param("status") Integer status);
	
	@Query("select t from PGAdminUser t where t.userName=:userName and t.status <> :status")
	public PGAdminUser findByUserNameAndStatusNotLike(@Param("userName") String userName, @Param("status") Integer status);
	
	public PGAdminUser findByAdminUserIdAndEmailToken(Long userId,String token);

	@Query("select userRoleId from PGAdminUser where status != 3")
	public List<Long> getRoleList();
	
	public List<PGAdminUser> findByPassRetryCount(Integer status);
}
