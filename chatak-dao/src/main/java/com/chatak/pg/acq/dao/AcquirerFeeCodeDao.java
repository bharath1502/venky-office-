/**
 * 
 */
package com.chatak.pg.acq.dao;

import java.util.List;

import com.chatak.pg.acq.dao.model.PGAcquirerFeeCode;


/**
 * @Author: Girmiti Software
 * @Date: Aug 8, 2015
 * @Time: 3:25:48 PM
 * @Version: 1.0
 * @Comments: 
 *
 */
public interface AcquirerFeeCodeDao {
  
  public List<PGAcquirerFeeCode> getAllFeeCodes();
  public List<PGAcquirerFeeCode> getAcquirerFeeCodeByPartnerId(Long partnerId);
  public PGAcquirerFeeCode getAcquirerFeeCodeByFeeCodeId(Long feeCodeId);
  public void updateAcquirerFeecode(PGAcquirerFeeCode pgAcquirerFeeCode);
  public void addAcquirerFeecode(PGAcquirerFeeCode pgAcquirerFeeCode);
  public PGAcquirerFeeCode getAcquirerFeeCodeByAcquirerNameAndPartnerIdAndMerchantCode(String acquirerName,Long partnerId,String merchantCode);
  public List<PGAcquirerFeeCode> getFeeCodesByPartnerIdAndMerchantCode(Long partnerId,String merchantCode);
  public List<PGAcquirerFeeCode> getAcquirerFeeCodesByMerchantCode(String merchantCode);

}
