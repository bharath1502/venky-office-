/**
 * 
 */
package com.chatak.pg.acq.dao;

import com.chatak.pg.acq.dao.model.PGLegalEntity;

/**
 * @Author: Girmiti Software
 * @Date: Aug 16, 2015
 * @Time: 5:10:05 PM
 * @Version: 1.0
 * @Comments: 
 *
 */
public interface LegalEntityDao {
  public PGLegalEntity getLegalEntityByMerchantId(String merchantId);
  public void addLegalEntity(PGLegalEntity pgLegalEntity);
  public void updateLegalEntity(PGLegalEntity pgLegalEntity);

}
