package com.chatak.pg.acq.dao.impl;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import com.chatak.pg.acq.dao.SwitchTransactionDao;
import com.chatak.pg.acq.dao.model.PGSwitchTransaction;
import com.chatak.pg.acq.dao.repository.SwitchTransactionRepository;

/**
 * Dao class to perform switch transaction record related insert, update and search
 * operation
 */
@Repository("switchTransactionDao")
public class SwitchTransactionDaoImpl implements SwitchTransactionDao {
  private static Logger log = Logger.getLogger(SwitchTransactionDaoImpl.class);

  @Autowired
  private SwitchTransactionRepository switchTransactionRepository;

  /**
   * Creates a Switch Transaction record with pre populated object
   * 
   * @param pgSwitchTransaction
   * @throws DataAccessException
   */
  @Override
  public void createTransaction(PGSwitchTransaction pgSwitchTransaction) {
    log.info("SwitchTransactionDaoImpl | createTransaction | Entering");
    switchTransactionRepository.save(pgSwitchTransaction);
    log.info("SwitchTransactionDaoImpl | createTransaction | Exiting");
  }

}
