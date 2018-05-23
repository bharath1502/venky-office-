package com.chatak.pg.acq.dao;

import org.springframework.dao.DataAccessException;

import com.chatak.pg.acq.dao.model.PGIPWhitelist;

public interface IPWhitelistDao {

	public void createIPWhitelist(PGIPWhitelist pgipWhitelist)throws DataAccessException;
}
