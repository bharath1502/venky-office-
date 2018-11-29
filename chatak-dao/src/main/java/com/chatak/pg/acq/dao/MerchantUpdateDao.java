/**
 * 
 */
package com.chatak.pg.acq.dao;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chatak.pg.acq.dao.model.PGAccount;
import com.chatak.pg.acq.dao.model.PGMerchant;
import com.chatak.pg.acq.dao.model.PGMerchantConfig;
import com.chatak.pg.acq.dao.model.PGMerchantEntityMap;
import com.chatak.pg.acq.dao.model.PGMerchantUsers;
import com.chatak.pg.bean.Response;
import com.chatak.pg.user.bean.AddMerchantRequest;
import com.chatak.pg.user.bean.AddMerchantResponse;
import com.chatak.pg.user.bean.UpdateMerchantRequest;
import com.chatak.pg.user.bean.UpdateMerchantResponse;

/**
 * @Author: Girmiti Software
 * @Date: Aug 23, 2017
 * @Time: 2:59:23 PM
 * @Version: 1.0
 * @Comments: 
 *
 */
public interface MerchantUpdateDao {

  public Long generateAccountNum();

  public PGMerchant findByEmailId(String emailId);

  public List<PGMerchant> findByBankId(Long bankId);

  public Response getUserByUserName(String userName);

  public List<Map<String, String>> getMerchantList();

  public PGMerchant getMerchant(String merchantCode);

  public PGMerchant getMerchantByCode(String merchantCode);

  public String getMerchantCompanyName(String merchantCode);

  public boolean updateMerchantProfile(PGMerchant pgMerchant);

  public PGAccount createOrUpdateAccouunt(PGAccount pgAccount);

  public List<PGMerchant> getMerchantsByStatus(Integer status);

  public List<PGMerchant> findByMcc(String merchantCategoryCode);

  public List<PGMerchant> getMerchantByStatusPendingandDecline();

  public PGMerchant createOrUpdateMerchant(PGMerchant pgMerchant);

  public PGMerchantConfig getConfigDetails(PGMerchantConfig pgMerchantConfig);

  public AddMerchantResponse addMerchant(AddMerchantRequest addMerchantRequest);

  public PGMerchantConfig updateAutoSettlement(PGMerchantConfig pgMerchantConfig);

  public PGMerchantUsers createOrUpdateMerchantUsers(PGMerchantUsers pgMerchantUsers);

  public List<PGAccount> findByEntityIdAndStatusNotLike(String entityId, String status);

  public List<PGMerchant> findByParentMerchantIdAndStatus(Long merchantId, Integer status);

  public UpdateMerchantResponse updateMerchant(UpdateMerchantRequest updateMerchantRequest);

  public Long getMerchantAccountNumberSeries(String accountNumber)throws DataAccessException;
  
  public List<PGMerchantEntityMap> findByEntityIdAndEntitytype(Long entityId, String entityType);
  
  public PGMerchant getMerchantAutoSettlementByCode(String merchantCode);
  
  public List<PGMerchant> getPmMerchantByEntityIdandEntityType(Long entityId, String entityType);

  public PGMerchant getMerchantOnCodeAndEntityDetails(String merchantCode, String entityType, Long entityId);
}
