package com.chatak.pg.acq.dao;

import java.util.List;
import java.util.Set;

import com.chatak.pg.acq.dao.model.BankProgramManagerMap;
import com.chatak.pg.acq.dao.model.PmCardProgamMapping;
import com.chatak.pg.acq.dao.model.ProgramManager;
import com.chatak.pg.acq.dao.model.ProgramManagerAccount;
import com.chatak.pg.bean.Response;
import com.chatak.pg.exception.PrepaidAdminException;
import com.chatak.pg.user.bean.BankRequest;
import com.chatak.pg.user.bean.CardProgramRequest;
import com.chatak.pg.user.bean.CardProgramResponse;
import com.chatak.pg.user.bean.MerchantResponse;
import com.chatak.pg.user.bean.PanRangeRequest;
import com.chatak.pg.user.bean.ProgramManagerAccountRequest;
import com.chatak.pg.user.bean.ProgramManagerRequest;

public interface ProgramManagerDao {

  public Long getProgramManagerAccountNumber();

  public Long getRevenueProgramManagerAccountNumber();

  public ProgramManager saveOrUpdateProgramManager(ProgramManager programManager);

  public ProgramManagerAccount saveOrUpdateProgramManagerAccount(
      ProgramManagerAccount programManagerAccount);

  public void deleteBankProgramManagerMap(Long pmId);

  public Set<BankProgramManagerMap> findBankProgramManagerMapByProgramManagerId(
      Long programManagerId);

  public List<ProgramManager> findByProgramManagerName(String programManagerName);

  public ProgramManagerRequest findProgramManagerById(ProgramManagerRequest programManagerRequest)
      throws PrepaidAdminException;

  public List<ProgramManagerRequest> searchProgramManagers(
      ProgramManagerRequest programManagerRequest);

  public List<ProgramManagerRequest> searchProgramManagersAccounts(
      ProgramManagerRequest programManagerRequest);

  public List<BankRequest> getAllBanksForProgramManager(ProgramManagerRequest programManagerRequest);

  public void changeStatus(ProgramManager programManager);

  public List<ProgramManagerRequest> getAllProgramManagers(
      ProgramManagerRequest programManagerRequest);

  public ProgramManagerAccount getProgramManagerAccountById(Long programManagerAccountId);

  public ProgramManagerAccount getProgramManagerAccountByIdAndAccountType(
      Long programManagerAccountId, String accountType);

  public ProgramManager searchSystemProgramManager(ProgramManagerRequest programManagerRequest);

  public Set<BankProgramManagerMap> findByBankId(Long bankId);

  public ProgramManagerAccount findByProgramManagerIdAndAccountNumber(Long pmId, Long accountNumber);

  public List<ProgramManagerAccount> findByAccountNumber(Long accountNumber);

  public List<Long> getProgramManagerAllAccountsByPmId(Long programManagerId);

  public List<ProgramManager> findAllProgramManagerDetails();

  public List<ProgramManagerAccountRequest> findPMAccountsToAutoSweep();

  public ProgramManagerAccount findByProgramManagerIdAndAccountType(Long programManagerAccountId,
      String accountType);

  public ProgramManagerAccount findByAccountId(Long accountId);

  public ProgramManagerAccountRequest findBankDetailsByPMId(
      ProgramManagerAccountRequest programManagerAccountRequest);

  public ProgramManagerRequest findProgramManagerById(Long id);

  public List<ProgramManagerAccount> getProgramManagerAccountByProgramManagerId(
      Long programManagerAccountId);

  public void changeStatusPMAccnt(ProgramManager programManager);

  public List<ProgramManager> getProgramManagerNameByAccountCurrency(String currencyId);

  public MerchantResponse getProgramManagerNameByCurrencyAndId(Long id, String currencyId);

  public void deleteCpProgramManagerMap(Long pmId);

  public List<ProgramManager> findByIssuancePmid(Long issuancePmId);
  
  public List<ProgramManager> findByProgramManagerIdAndAccountCurrency(Long pmId,String currencyId);
  
  public List<ProgramManager> findByBatchTime(String pmSystemConvertedTime);
  
  public CardProgramResponse fetchPMCardProgramByMerchantId(Long merchantId);
  
  public PmCardProgamMapping findByCardProgramId(Long cardProgramId);
  
  public ProgramManager findByProgramManagerId(Long pmId);
  
  public Set<PmCardProgamMapping> findPmCardProgamMappingMapByProgramManagerId(
	      Long programManagerId);
  
  public ProgramManagerRequest findStatusAndBatchPrefixByProgramManagerId(Long id);
  
  public Response findProgramManagerNameAndIdByEntityId(Long entityId, Long merchantId);
  
  public List<PanRangeRequest> getPanListForIso(Long isoId);
  
  public List<ProgramManagerRequest> fetchProgramManagerNameByAccountCurrency(String currency);
  
}
