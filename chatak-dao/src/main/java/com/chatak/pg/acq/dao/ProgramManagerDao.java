package com.chatak.pg.acq.dao;

import java.util.List;
import java.util.Set;

import org.springframework.dao.DataAccessException;

import com.chatak.pg.acq.dao.model.BankProgramManagerMap;
import com.chatak.pg.acq.dao.model.ProgramManager;
import com.chatak.pg.acq.dao.model.ProgramManagerAccount;
import com.chatak.pg.exception.PrepaidAdminException;
import com.chatak.pg.user.bean.BankRequest;
import com.chatak.pg.user.bean.ProgramManagerAccountRequest;
import com.chatak.pg.user.bean.ProgramManagerRequest;

public interface ProgramManagerDao {

  public Long getProgramManagerAccountNumber() throws DataAccessException;

  public Long getRevenueProgramManagerAccountNumber() throws DataAccessException;

  public ProgramManager saveOrUpdateProgramManager(ProgramManager programManager)
      throws DataAccessException;

  public ProgramManagerAccount saveOrUpdateProgramManagerAccount(
      ProgramManagerAccount programManagerAccount) throws DataAccessException;

  public void deleteBankProgramManager(Set<BankProgramManagerMap> bankProgramManagerMap)
      throws DataAccessException;

  public Set<BankProgramManagerMap> findBankProgramManagerMapByProgramManagerId(
      Long programManagerId) throws DataAccessException;

  public List<ProgramManager> findByProgramManagerName(String programManagerName)
      throws DataAccessException;

  public ProgramManagerRequest findProgramManagerById(ProgramManagerRequest programManagerRequest)
      throws PrepaidAdminException;

  public List<ProgramManagerRequest> searchProgramManagers(
      ProgramManagerRequest programManagerRequest) throws DataAccessException;

  public List<ProgramManagerRequest> searchProgramManagersAccounts(
      ProgramManagerRequest programManagerRequest) throws DataAccessException;

  public List<BankRequest> getAllBanksForProgramManager(ProgramManagerRequest programManagerRequest)
      throws DataAccessException;

  public void changeStatus(ProgramManager programManager) throws DataAccessException;

  public List<ProgramManagerRequest> getAllProgramManagers(
      ProgramManagerRequest programManagerRequest) throws DataAccessException;

  public ProgramManagerAccount getProgramManagerAccountById(Long programManagerAccountId)
      throws DataAccessException;

  public ProgramManagerAccount getProgramManagerAccountByIdAndAccountType(
      Long programManagerAccountId, String accountType) throws DataAccessException;

  public ProgramManager searchSystemProgramManager(ProgramManagerRequest programManagerRequest)
      throws DataAccessException;

  public Set<BankProgramManagerMap> findByBankId(Long bankId) throws DataAccessException;

  public ProgramManagerAccount findByProgramManagerIdAndAccountNumber(Long pmId, Long accountNumber)
      throws DataAccessException;

  public List<ProgramManagerAccount> findByAccountNumber(Long accountNumber)
      throws DataAccessException;

  public List<Long> getProgramManagerAllAccountsByPmId(Long programManagerId)
      throws DataAccessException;

  public List<ProgramManager> findAllProgramManagerDetails() throws DataAccessException;

  public List<ProgramManagerAccountRequest> findPMAccountsToAutoSweep() throws DataAccessException;

  public ProgramManagerAccount findByProgramManagerIdAndAccountType(Long programManagerAccountId,
      String accountType) throws DataAccessException;

  public ProgramManagerAccount findByAccountId(Long accountId) throws DataAccessException;

  public ProgramManagerAccountRequest findBankDetailsByPMId(
      ProgramManagerAccountRequest programManagerAccountRequest) throws DataAccessException;

  public ProgramManagerRequest findProgramManagerById(Long id);

  public List<ProgramManagerAccount> getProgramManagerAccountByProgramManagerId(
      Long programManagerAccountId) throws DataAccessException;

  public void changeStatusPMAccnt(ProgramManager programManager) throws DataAccessException;
}
