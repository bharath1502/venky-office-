package com.chatak.acquirer.admin.service;

import java.util.List;
import java.util.Map;

import org.springframework.context.NoSuchMessageException;

import com.chatak.acquirer.admin.exception.ChatakAdminException;
import com.chatak.acquirer.admin.model.MerchantAccountSearchResponse;
import com.chatak.pg.bean.Response;
import com.chatak.pg.exception.PrepaidAdminException;
import com.chatak.pg.model.AccountBalanceDTO;
import com.chatak.pg.model.AccountBalanceReportDTO;
import com.chatak.pg.model.Merchant;
import com.chatak.pg.user.bean.GetMerchantListRequest;
import com.chatak.pg.user.bean.MerchantAccountSearchDto;
import com.chatak.pg.user.bean.MerchantDetailsForAccountResponse;

public interface MerchantAccountService {
  
  public Merchant getMerchantAccountDetails(String merchantCode) throws ChatakAdminException;
  
  public List<AccountBalanceReportDTO> getAllAccountsBalanceReport(Merchant merchant)throws ChatakAdminException;
  
  public List<AccountBalanceReportDTO> getAllAccountsBalanceReportPagination(Merchant merchant)throws ChatakAdminException;
  
  public AccountBalanceDTO getAccountBalanceDTO(String merchantId, Long entityId, String userType )throws NoSuchMessageException, ChatakAdminException, PrepaidAdminException;
  
  public Response processMerchantAccountBalanceUpdate(AccountBalanceDTO accountBalanceDTO,String type) throws PrepaidAdminException;
  
  public MerchantDetailsForAccountResponse getMerchantDetailsForAccountCreation(Merchant merchant);
  
  public void getMerchantConfigDetailsForAccountCreate(Merchant merchant);
  
  public Response createMerchantAccount(Merchant merchant, Long loggedInUserId);
  
  public MerchantAccountSearchResponse searchMerchantAccount(MerchantAccountSearchDto merchantAccountSearchDto, Map<String, String> merchantDataMap);
  
  public void changeAccountStatus(MerchantAccountSearchDto merchantAccountSearchDto);
  
  public Merchant getAccount(Merchant merchant);
  
  public void updateMerchantAccount(Merchant merchant);
  
  public Map<String, String> getMerchantMapByMerchantType(String merchantType);
  
  public Map<String, String> getMerchantDataMapByMerchantType(String merchantType, Long entityId, String loginUserType);
  
}
