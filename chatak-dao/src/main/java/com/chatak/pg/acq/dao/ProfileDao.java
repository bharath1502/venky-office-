package com.chatak.pg.acq.dao;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.chatak.pg.acq.dao.model.PGAdminUser;

public interface ProfileDao {
	
	public PGAdminUser saveOrUpdate(PGAdminUser adminUser)  throws DataAccessException;
	
	public List<PGAdminUser> findByUserName(String userName) throws DataAccessException;
	
	public List<PGAdminUser> findByadminUserId(Long adminUserId) throws DataAccessException;

}
