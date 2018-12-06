/**
 * 
 */
package com.chatak.pg.acq.dao;

import java.util.List;

import com.chatak.pg.acq.dao.model.PGAccount;
import com.chatak.pg.acq.dao.model.PGMerchant;
import com.chatak.pg.acq.dao.model.PGState;
import com.chatak.pg.bean.Response;
import com.chatak.pg.model.AccountBalanceReportDTO;
import com.chatak.pg.model.Merchant;
import com.chatak.pg.model.ReportsDTO;
import com.chatak.pg.user.bean.AddMerchantBankRequest;
import com.chatak.pg.user.bean.AddMerchantBankResponse;
import com.chatak.pg.user.bean.DeleteMerchantRequest;
import com.chatak.pg.user.bean.DeleteMerchantResponse;
import com.chatak.pg.user.bean.FeeProgramNameListDTO;
import com.chatak.pg.user.bean.GetMerchantListRequest;
import com.chatak.pg.user.bean.GetMerchantListResponse;

/**
 * @Author: Girmiti Software
 * @Date: Aug 22, 2017
 * @Time: 5:56:30 PM
 * @Version: 1.0
 * @Comments: 
 *
 */
public interface MerchantProfileDao {

  public List<PGMerchant> getAllMerchants();

  public Response getUserByEmailId(String email);

  public List<ReportsDTO> getAccountHistoryList();

  public PGMerchant getMerchantById(Long merchantId);

  public Response getUserByUsername(String userName);

  public PGAccount getPgAccount(String merchantCode);

  public List<PGState> getStateById(String countryId);

  public FeeProgramNameListDTO getActiveFeePrograms();

  public PGMerchant getMerchantByEmailId(String email);

  public DeleteMerchantResponse deleteMerchant(Long id);

  public List<PGState> getStateByCountryId(Long countryId);

  public DeleteMerchantResponse deleteMerchantAndAssociatedAccounts(Long id);

  public List<AccountBalanceReportDTO> getAllAccountsBalanceReport(Merchant merchant);

  public GetMerchantListResponse getMerchantlist(GetMerchantListRequest searchMerchant);

  public GetMerchantListResponse getAllMerchantlist(GetMerchantListRequest searchMerchant);

  public DeleteMerchantResponse deleteMerchant(DeleteMerchantRequest deleteMerchantRequest);

  public AddMerchantBankResponse addMerchantBank(AddMerchantBankRequest addMerchantBankRequest);

  int getTotalNumberOfRecords(GetMerchantListRequest searchMerchant);

}
