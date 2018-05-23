/**
 * 
 */
package com.chatak.pg.acq.dao;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.chatak.pg.bean.TransactionCategoryCodeRequest;

/**
 * @Author: Girmiti Software
 * @Date: Aug 20, 2016
 * @Time: 12:48:07 PM
 * @Version: 1.0
 * @Comments:
 *
 */
public interface TransactionCategoryCodeDao {
	public List<TransactionCategoryCodeRequest> findAllTCC()
			throws DataAccessException;
	
}
