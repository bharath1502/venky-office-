/**
 * 
 */
package com.chatak.merchant.service;

import com.chatak.merchant.exception.ChatakMerchantException;
import com.chatak.pg.acq.dao.model.PGAccount;

/**
 *
 * << Add Comments Here >>
 *
 * @author Girmiti Software
 * @date May 15, 2015 1:00:35 PM
 * @version 1.0
 */
public interface AccountService {
  
  public PGAccount getAccountDetailsByEntityId(String entityId) throws ChatakMerchantException;

}
