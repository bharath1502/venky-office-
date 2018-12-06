/**
 * 
 */
package com.chatak.pg.acq.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import com.chatak.pg.acq.dao.TransactionCategoryCodeDao;
import com.chatak.pg.acq.dao.model.PGTransactionCategoryCode;
import com.chatak.pg.acq.dao.repository.TransactionCategoryCodeRepository;
import com.chatak.pg.bean.TransactionCategoryCodeRequest;
import com.chatak.pg.util.CommonUtil;

/**
 * @Author: Girmiti Software
 * @Date: Aug 20, 2016
 * @Time: 2:53:10 PM
 * @Version: 1.0
 * @Comments:
 *
 */

@Repository
public class TransactionCategoryCodeDaoImpl implements
		TransactionCategoryCodeDao {
	private static Logger logger = Logger.getLogger(ISOCountryDaoImpl.class);
	/**
	 * @return
	 * @throws DataAccessException
	 */
	@Autowired
	private TransactionCategoryCodeRepository tccRepository;
	
	@Override
	public List<TransactionCategoryCodeRequest> findAllTCC()
			throws DataAccessException {

		List<TransactionCategoryCodeRequest> list = new ArrayList<>();

		List<PGTransactionCategoryCode> transactionCategoryCodes = tccRepository.findAll();

		try {
			list = CommonUtil.copyListBeanProperty(transactionCategoryCodes,
					TransactionCategoryCodeRequest.class);
		}catch (InstantiationException|IllegalAccessException  e) {
			logger.error("Error in retrieving the list of TransactionCategoryCode", e);
		} 

		return list;
	}

}
