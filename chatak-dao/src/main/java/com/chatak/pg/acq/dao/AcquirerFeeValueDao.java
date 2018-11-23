/**
 * 
 */
package com.chatak.pg.acq.dao;

import java.util.List;

import com.chatak.pg.acq.dao.model.PGAcquirerFeeValue;

/**
 * @Author: Girmiti Software
 * @Date: Feb 6, 2016
 * @Time: 1:36:16 PM
 * @Version: 1.0
 * @Comments: 
 *
 */
public interface AcquirerFeeValueDao {
  
  public List<PGAcquirerFeeValue> getAcquirerFeeValuesByFeeProgramId(Long feeProgramId);
  
  public void removeAcquirerFeeValues(List<PGAcquirerFeeValue> acquirerFeeValuesDaoDetails);
  
}
