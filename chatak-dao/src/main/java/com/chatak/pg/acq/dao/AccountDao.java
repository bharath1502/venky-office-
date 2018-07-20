/**
 * 
 */
package com.chatak.pg.acq.dao;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.chatak.pg.acq.dao.model.PGAccount;
import com.chatak.pg.model.AccountBalanceDTO;
import com.chatak.pg.model.ReportsDTO;
import com.chatak.pg.user.bean.AddMerchantRequest;
import com.chatak.pg.user.bean.AddMerchantResponse;
import com.chatak.pg.user.bean.MerchantAccountSearchDto;

/**
 * @Author: Girmiti Software
 * @Date: Apr 28, 2015
 * @Time: 9:05:27 PM
 * @Version: 1.0
 * @Comments:
 */
public interface AccountDao {

  /**
   * @param addMerchantRequest
   * @param addMerchantResponse
   * @return
   * @throws DataAccessException
   */
  AddMerchantResponse savePgAcc(AddMerchantRequest addMerchantRequest, AddMerchantResponse addMerchantResponse) throws DataAccessException;

  PGAccount getPgAccount(String entityId) throws DataAccessException;

  public void savePGAccount(PGAccount pgAccount) throws DataAccessException;
  
  public List<PGAccount> getPGAccountsOnPayoutFrequencyAndAutoPaymentMethod(String payoutFrequency,String autoPaymentMethod) throws DataAccessException;
  
  public List<ReportsDTO> getOverViewData() throws DataAccessException;
  
  public List<PGAccount> getPGAccountsOnPayoutFrequency(String payoutFrequency) throws DataAccessException;

  public PGAccount addMerchantAccount(PGAccount pgAccount);
  
  public List<MerchantAccountSearchDto> searchMerchantAccount(MerchantAccountSearchDto merchantAccountSearchDto);
  
  public void changeAccountStatus(Long accountId, String accountStatus, String reason);

  public PGAccount getAccountOnId(Long accountId);
  
  public PGAccount getAccountonAccountNumber(Long accountNumber);
  
  public PGAccount getSecondaryAccount(String entityId);
  
  public List<PGAccount> getActivePGAccounts(String entityId);
  
  public List<AccountBalanceDTO> getAccDetailsOnAccNums(List<Long> accounts);
  
  public List<MerchantAccountSearchDto> searchMerchantAccountOnMerchantCode(MerchantAccountSearchDto merchantAccountSearchDto);
}
