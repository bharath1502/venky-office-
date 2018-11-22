/**
 * 
 */
package com.chatak.pg.acq.dao;

import com.chatak.pg.acq.dao.model.PGMerchantCardProgramMap;
import com.chatak.pg.user.bean.MerchantResponse;

/**
 * @Author: Girmiti Software
 * @Date: May 10, 2018
 * @Time: 3:56:14 PM
 * @Version: 1.0
 * @Comments: 
 *
 */
public interface MerchantCardProgramMapDao {
	
	public MerchantResponse findCardProgramByMerchantId(Long merchantId);
	
	public PGMerchantCardProgramMap findByMerchantIdAndCardProgramId(Long merchantId, Long cardProgramId);

}
