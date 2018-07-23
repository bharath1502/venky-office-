package com.chatak.pg.acq.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.chatak.pg.acq.dao.ReportDao;
import com.chatak.pg.acq.dao.repository.TransactionRepository;

@Repository("reportDao")
public class ReportDaoImpl implements ReportDao{
	 
  @Autowired
  private TransactionRepository transactionRepository;
  
}
