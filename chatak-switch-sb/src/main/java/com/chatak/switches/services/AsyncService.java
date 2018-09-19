package com.chatak.switches.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;

import com.chatak.pg.acq.dao.SwitchTransactionDao;
import com.chatak.pg.acq.dao.model.PGSwitchTransaction;

@Service
@EnableAsync
public class AsyncService {
  
  @Autowired
  protected SwitchTransactionDao switchTransactionDao;

  @Async
  public void saveSwitchTransaction(PGSwitchTransaction pgSwitchTransaction) {
    switchTransactionDao.createTransaction(pgSwitchTransaction);
  } 
  
}
