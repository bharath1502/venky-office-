/**
 * 
 */
package com.chatak.pg.acq.dao;

import java.util.List;
import java.util.Map;

import com.chatak.pg.acq.dao.model.PGMerchantEntityMap;
import com.chatak.pg.user.bean.GetMerchantListRequest;
import com.chatak.pg.user.bean.GetMerchantListResponse;

/**
 * @Author: Girmiti Software
 * @Date: May 10, 2018
 * @Time: 3:55:45 PM
 * @Version: 1.0
 * @Comments: 
 *
 */
public interface MerchantEntityMapDao {
	
	public List<PGMerchantEntityMap> findByMerchantId(Long merchantId);
	
	public GetMerchantListResponse fetchMerchantsForPM(GetMerchantListRequest searchMerchant, Long entityId);
	
	public GetMerchantListResponse fetchMerchantsForISO(GetMerchantListRequest searchMerchant, Long entityId);
	
    public GetMerchantListResponse fetchSubMerchantsForPM(GetMerchantListRequest searchMerchant, Long entityId);
	
	public GetMerchantListResponse fetchSubMerchantsForISO(GetMerchantListRequest searchMerchant, Long entityId);
	
	public Map<String, String> getMerchantCodeForPMOrIso(Long entityId,  String loginUserType);
	 
}
