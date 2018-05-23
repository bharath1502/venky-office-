package com.chatak.pg.acq.dao.impl;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import com.chatak.pg.acq.dao.ActivityLogDao;
import com.chatak.pg.acq.dao.model.PGActivityLog;
import com.chatak.pg.acq.dao.repository.ActivityLogRepository;

@Repository("activityLogDao")
public class ActivityLogDaoImpl implements ActivityLogDao {

  private static Logger log = Logger.getLogger(ActivityLogDaoImpl.class);

  @Autowired
  ActivityLogRepository activityLogRepository;

  /**
   * Method to log request packet
   * 
   * @param pgActivityLog
   */
  @Override
  public void logRequest(PGActivityLog pgActivityLog) throws DataAccessException {
    log.debug("ActivityLogDaoImpl | logRequest | Entering");
     activityLogRepository.save(pgActivityLog);
    log.debug("ActivityLogDaoImpl | logRequest | Exiting");
  }

}
