/**
 * 
 */
package com.chatak.pg.acq.dao;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.chatak.pg.acq.dao.model.PGUserActivityLog;
import com.chatak.pg.model.AccessLogsDTO;

/**
 * @Author: Girmiti Software
 * @Date: Sep 15, 2015
 * @Time: 6:04:47 PM
 * @Version: 1.0
 * @Comments: 
 *
 */
public interface UserActivityLogDao {
public void logUserActivity(PGUserActivityLog pgUserActivityLog) throws DataAccessException;
public List<AccessLogsDTO> getAllPgUserActivityLogs() throws DataAccessException;
}
