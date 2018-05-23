package com.chatak.pg.acq.dao.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import com.chatak.pg.acq.dao.FeeDetailDao;
import com.chatak.pg.acq.dao.model.PGFeeDetail;
import com.chatak.pg.acq.dao.repository.FeeDetailRepository;

/**
 * Fee Utility Dao class to get Fee details associated with PG Transactions 
 * @author Kumar
 *
 */
@Repository("feeDetailDao")
public class FeeDetailDaoImpl implements FeeDetailDao {

	private static Logger log = Logger.getLogger(FeeDetailDaoImpl.class);
	
	@Autowired
	private FeeDetailRepository  feeDetailRepository;
	
	/**
	 * Method to obtain Fee Details associated with PG
	 * @return Fee Detail list 
	 * @throws DataAccessException
	 */
	@Override
	public List<PGFeeDetail> getPGFeeDetail() throws DataAccessException {
		log.debug("FeeDetailDaoImpl | getPGFeeDetail | Entering");
		List<PGFeeDetail> pgFeeDetailsList = feeDetailRepository.findAll();
		log.debug("FeeDetailDaoImpl | getPGFeeDetail | Exiting");
		return pgFeeDetailsList;	
	}

	/**
	 * Method to obtain Fee Detail associated with PG transaction type
	 * @param transactionType
	 * @return PGFeeDetail
	 * @throws DataAccessException
	 */
	@Override
	public PGFeeDetail getPGFeeDetail(String transactionType) throws DataAccessException {

		log.debug("FeeDetailDaoImpl | getPGFeeDetail | Entering");
		PGFeeDetail feeDetail = null;
		List<PGFeeDetail> pgFeeDetailsList = feeDetailRepository.findByTxnType(transactionType);
		
		if(null != pgFeeDetailsList && !pgFeeDetailsList.isEmpty()) {
		  feeDetail = pgFeeDetailsList.get(0);
		}
		log.debug("FeeDetailDaoImpl | getPGFeeDetail | Exiting");
		return feeDetail;	
	}

	/**
	 * Method to obtain Fee amount associated with PG transaction type
	 * @param transactionType
	 * @return amount
	 * @throws DataAccessException
	 */
	@Override
	public Long getPGFeeAmount(String transactionType) throws DataAccessException {

		log.debug("FeeDetailDaoImpl | getPGFeeAmount | Entering");

		Long feeAmount = 0l;
    List<PGFeeDetail> pgFeeDetailsList = feeDetailRepository.findByTxnType(transactionType);
    
    if(null != pgFeeDetailsList && !pgFeeDetailsList.isEmpty()) {
      feeAmount = pgFeeDetailsList.get(0).getFeeAmount();
      if(null == feeAmount) {
        feeAmount = 0l;
      }
    }

		log.debug("FeeDetailDaoImpl | getPGFeeAmount | Exiting");

		
		return feeAmount;	
	}
	
}
