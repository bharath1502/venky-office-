/**
 * 
 */
package com.chatak.pg.acq.dao;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.chatak.pg.acq.dao.model.PGParams;

/**
 * @Author: Girmiti Software
 * @Date: Apr 28, 2015
 * @Time: 9:05:27 PM
 * @Version: 1.0
 * @Comments:
 */
public interface PGParamsDao {

  public List<PGParams> getAllPGParams() throws DataAccessException;
 
}
