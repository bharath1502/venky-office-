/**
 * 
 */
package com.chatak.pg.acq.dao;

import java.util.List;

import com.chatak.pg.acq.dao.model.PGPartnerFeeCode;

/**
 * @Author: Girmiti Software
 * @Date: Aug 8, 2015
 * @Time: 3:25:48 PM
 * @Version: 1.0
 * @Comments: 
 *
 */
public interface PartnerFeeCodeDao {
  
  public List<PGPartnerFeeCode> getAllFeeCodes();
  public PGPartnerFeeCode getPartnerFeeCodeByEntityId(String partnerEntityId);
  public void createPartnerFeecode(PGPartnerFeeCode pgPartnerFeeCode);
  public void updatePartnerFeeCode(PGPartnerFeeCode pgPartnerFeeCode);
  public PGPartnerFeeCode getPartnerFeeCodeByAccountNumber(Long accountNumber);

}
