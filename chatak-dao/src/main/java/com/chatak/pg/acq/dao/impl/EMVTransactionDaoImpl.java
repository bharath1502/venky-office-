package com.chatak.pg.acq.dao.impl;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import com.chatak.pg.acq.dao.EMVTransactionDao;
import com.chatak.pg.acq.dao.model.PGEMVTransaction;
import com.chatak.pg.acq.dao.repository.EMVTransactionRepository;

/**
 * Dao class to perform transaction record related insert, update and search
 * operation
 */
@Repository("emvTransactionDao")
public class EMVTransactionDaoImpl implements EMVTransactionDao {
  private static Logger log = Logger.getLogger(EMVTransactionDaoImpl.class);

  @Autowired
  private EMVTransactionRepository emvTransactionRepository;

  /**
   * Creates a Transaction record with pre populated object
   * 
   * @param pgTransaction
   * @throws DataAccessException
   */
  @Override
  public void createTransaction(PGEMVTransaction pgTransaction) throws DataAccessException {
    log.debug("EMVTransactionDaoImpl | createTransaction | Entering");
    emvTransactionRepository.save(pgTransaction);
    log.debug("EMVTransactionDaoImpl | createTransaction | Exiting");
  }


}
