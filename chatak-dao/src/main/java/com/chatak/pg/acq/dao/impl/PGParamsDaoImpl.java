/**
 * 
 */
package com.chatak.pg.acq.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import com.chatak.pg.acq.dao.PGParamsDao;
import com.chatak.pg.acq.dao.model.PGParams;
import com.chatak.pg.acq.dao.repository.PGParamsRepository;

/**
 * @Author: Girmiti Software
 * @Date: Apr 28, 2015
 * @Time: 9:07:17 PM
 * @Version: 1.0
 * @Comments:
 */
@Repository("paramsDao")
public class PGParamsDaoImpl implements PGParamsDao {

  @Autowired
  private PGParamsRepository paramsRepository;

  /**
   * @return
   * @throws DataAccessException
   */
  @Override
  public List<PGParams> getAllPGParams() throws DataAccessException {
    return paramsRepository.findAll();
  }

}
