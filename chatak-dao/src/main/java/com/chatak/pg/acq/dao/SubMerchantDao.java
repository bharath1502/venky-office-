/**
 * 
 */
package com.chatak.pg.acq.dao;

import java.util.List;
import java.util.Map;

import com.chatak.pg.acq.dao.model.PGMerchant;
import com.chatak.pg.user.bean.GetMerchantListRequest;
import com.chatak.pg.user.bean.GetMerchantListResponse;

/**
 * @Author: Girmiti Software
 * @Date: Aug 21, 2017
 * @Time: 7:30:15 PM
 * @Version: 1.0
 * @Comments: 
 *
 */
public interface SubMerchantDao {

  /**
   * @param searchMerchant
   * @return
   */
  public GetMerchantListResponse getMerchantlistOnSubMerchantCode(GetMerchantListRequest searchMerchant);

  /**
   * @param searchMerchant
   * @return
   */
  public GetMerchantListResponse getSubMerchantListOnMerchantId(GetMerchantListRequest searchMerchant);

  /**
   * @param searchMerchant
   * @return
   */
  public GetMerchantListResponse getSubMerchantList(GetMerchantListRequest searchMerchant);

  /**
   * @param merchantCode
   * @return
   */
  public List<String> getMerchantAndSubMerchantList(String merchantCode);

  /**
   * @param submerchantsList
   */
  public void updateSubMerchantsPartnerAndAgentId(List<PGMerchant> submerchantsList);

  public List<Map<String, String>> getSubMerchantCodeAndCompanyName(String merchantCode);
}
