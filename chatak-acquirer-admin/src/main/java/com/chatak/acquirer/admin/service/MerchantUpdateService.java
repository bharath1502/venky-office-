package com.chatak.acquirer.admin.service;

import java.util.List;
import java.util.Map;

import com.chatak.acquirer.admin.controller.model.Option;
import com.chatak.acquirer.admin.exception.ChatakAdminException;
import com.chatak.acquirer.admin.model.MerchantSearchResponse;
import com.chatak.pg.bean.Response;
import com.chatak.pg.model.Merchant;
import com.chatak.pg.user.bean.DeleteMerchantResponse;
import com.chatak.pg.user.bean.GetMerchantListRequest;
import com.chatak.pg.user.bean.MerchantResponse;

public interface MerchantUpdateService {
  
  public Response changeMerchantStatus(Merchant merchant, Integer status)
      throws ChatakAdminException;

  public MerchantSearchResponse searchMerchant(Merchant merchant, String userType, Long entityId) throws ChatakAdminException;

  public List<Option> getActiveMerchants() throws ChatakAdminException;

  public DeleteMerchantResponse deleteMerchant(Long id);

  public Response getStatesByCountry(String countryId) throws ChatakAdminException;

  public List<Option> getCountries();

  public MerchantSearchResponse searchSubMerchantList(Merchant merchant)
      throws ChatakAdminException;

  public MerchantSearchResponse searchAllMerchant(Merchant merchant) throws ChatakAdminException;

  public int getTotalNumberOfRecords(GetMerchantListRequest searchMerchant);

  public Map<String, String> getMerchantNameByMerchantCodeAsMap();

  public void updateSubMerchantsPartnerId(String merchantCode);

  public Map<String, String> getMerchantNameAndMerchantCodeAsMapByMerchantType(String merchantType);

  public Long getMerchantIdOnMerchantCode(Long parentMerchantId);

  public MerchantSearchResponse searchSubMerchants(Merchant merchant, String userType, Long entityId) throws ChatakAdminException;

  public Map<String, String> getMerchantCodeAndCompanyName(String merchantType);

  public List<Merchant> getMerchantByStatus(Integer status);

  public List<Option> getCurrencies();

  public MerchantResponse findByMerchantId(Long getMerchantId) throws InstantiationException, IllegalAccessException;

  public MerchantSearchResponse getMerchantCode(String merchantCode);

  public List<Merchant> getMerchantByStatusPendingandDecline();

  public Response getAgentNames(String currencyAlpha);
  
  public List<Long> findByEntityIdAndEntitytype(List<Long> entityIds, String entityType);
  
  public List<Merchant> getPmMerchantByEntityIdandEntityType(Long entityId, String entityType);

}
