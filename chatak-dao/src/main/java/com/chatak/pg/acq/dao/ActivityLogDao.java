package com.chatak.pg.acq.dao;

import org.springframework.dao.DataAccessException;

import com.chatak.pg.acq.dao.model.PGActivityLog;


public interface ActivityLogDao {
	
	/**
	 * Method to log request packet
	 * @param pgActivityLog
	 */
	public void logRequest(PGActivityLog pgActivityLog) throws DataAccessException;
	
}
