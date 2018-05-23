package com.chatak.pg.acq.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import com.chatak.pg.acq.dao.IPWhitelistDao;
import com.chatak.pg.acq.dao.model.PGIPWhitelist;
import com.chatak.pg.acq.dao.repository.IPWhitelistRepository;

@Repository("ipWhitelistDao")
public class IPWhitelistDaoImpl implements IPWhitelistDao{
	
	@Autowired
	IPWhitelistRepository ipWhitelistRepository;
	@Override
	public void createIPWhitelist(PGIPWhitelist pgipWhitelist)
			throws DataAccessException {
		ipWhitelistRepository.save(pgipWhitelist);
	}

}
