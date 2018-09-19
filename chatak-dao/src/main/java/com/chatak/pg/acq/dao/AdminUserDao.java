package com.chatak.pg.acq.dao;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.chatak.pg.acq.dao.model.PGAdminUser;
import com.chatak.pg.model.AdminUserDTO;
import com.chatak.pg.model.GenericUserDTO;


public interface AdminUserDao {
	
	public List<PGAdminUser> findByUserName(String userName) throws DataAccessException;
	
	public List<PGAdminUser> findByEmail(String email,String userType) throws DataAccessException;
	
	public PGAdminUser findByAdminUserId(Long userId) throws DataAccessException;
	
	public List<PGAdminUser> findByUserRoleId(Long userRoleId) throws DataAccessException;
	
	public PGAdminUser createOrUpdateUser(PGAdminUser adminUser) throws DataAccessException;
	
	public List<PGAdminUser> createOrUpdateUsers(List<PGAdminUser> adminUserList) throws DataAccessException;
	
	public List<AdminUserDTO> searchUser(AdminUserDTO userTo);
	
	public PGAdminUser findByUserNameAndUserType(String userName,String userType) throws DataAccessException;
	
	public boolean authenticateAcquirerAdmin(String email, String pgPass) throws DataAccessException;
	
	public PGAdminUser authenticateAcquirerAdminUser(String email, String pgPass) throws DataAccessException;
	
	public PGAdminUser findByEmail(String email);
	
	public PGAdminUser findByAdminUserIdAndEmailToken(Long userId,String token);
	
	public List<GenericUserDTO> searchGenericUser(GenericUserDTO userTo);
	
	public PGAdminUser findByEmailAndUserType(String email,String userType) throws DataAccessException;

	/**
	 * @return
	 */
	public List<Long> getRoleListAdmin() throws DataAccessException;
	
	public PGAdminUser findByUserNameAndStatus(String userName);
	
	public List<AdminUserDTO> searchAdminUserList(String userType);
	
}
