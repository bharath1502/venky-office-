package com.chatak.pg.acq.dao;

import java.util.List;
import java.util.Set;

import org.springframework.dao.DataAccessException;

import com.chatak.pg.acq.dao.model.BankPartnerMap;
import com.chatak.pg.acq.dao.model.Partner;
import com.chatak.pg.acq.dao.model.PartnerAccount;
import com.chatak.pg.acq.dao.model.ProgramManager;
import com.chatak.pg.enums.AccountType;
import com.chatak.pg.exception.PrepaidAdminException;
import com.chatak.pg.user.bean.PartnerAccountRequest;
import com.chatak.pg.user.bean.PartnerRequest;

public interface PartnerDao {

  /**
   * DAO method used to create or update partner
   * 
   * @param request
   * @param partnerRequest
   * @return
   */
  public Partner saveOrUpdatePartner(Partner partner);

  /**
   * DAO method used to find bank partner map by partner Id
   * @param partnerId
   * @return
   * @throws DataAccessException
   */
  public Set<BankPartnerMap> findBankPartnerMapByPartnerId(Long partnerId);

  /**
   * Service method to find partner name already exists
   * 
   * @param request
   * @param partnerRequest
   * @return
   */
  public List<Partner> findByPartnerName(String partnerName);

  /**
   * DAO method used to delete the bank partner map for a given partner
   * @param bankProgramManagerMap
   * @throws DataAccessException
   */
  public void deleteBankPartner(Set<BankPartnerMap> bankPartnerMap);

  /**
   * Service method to get partner details from partner Id
   * @param partnerRequest
   * @return
   * @throws PrepaidAdminException 
   * @
   * @throws DataAccessException
   */
  public PartnerRequest getDetailsByPartnerId(PartnerRequest partnerRequest)
      throws PrepaidAdminException;

  /**
   * 
   * @param partnerId
   * @return
   * @
   * @throws DataAccessException
   */
  public Partner findByPartnerId(Long partnerId);

  /**
   * Service method for search partner
   * 
   * @param request
   * @param partnerRequest
   * @return
   */
  public List<PartnerRequest> searchPartner(PartnerRequest partnerRequest);

  /**
   * Service method for search partner
   * 
   * @param request
   * @param partnerRequest
   * @return
   */
  public List<PartnerRequest> searchAccountPartner(PartnerRequest partnerRequest);

  /**
   * Service method for getting all partner id
   * 
   * @param request
   * @param partnerRequest
   * @return
   */
  public List<PartnerRequest> getAllPartners(PartnerRequest partnerRequest);

  /**
   * Service method for getting all partner id
   * 
   * @param request
   * @param partnerRequest
   * @return
   */
  public List<PartnerRequest> getAllPartnersForAdminUser(PartnerRequest partnerRequest);

  /**
   * @param partnerAccount
   * @return
   * @throws DataAccessException
   */
  public PartnerAccount saveOrUpdatePartnerAccount(PartnerAccount partnerAccount);


  /**
   * @return
   * @throws DataAccessException
   */
  public Long revenuePartnerAccount();

  /**
   * @return
   * @throws DataAccessException
   */
  public Long systemPartnerAccount();

  /**
   * @return
   * @throws DataAccessException
   */
  public Long networkPartnerAccount();

  /**
   * @param partnerAccountid
   * @return
   * @throws DataAccessException
   */
  public PartnerAccount getPartnerAccountByAccountIdAndActType(Long partnerAccountid,
      String accountType);

  public List<Partner> findByPartnerClientId(String partnerClientId);

  public PartnerAccountRequest findBankDetailsByPartnerId(PartnerAccountRequest parnAccountRequest);

  public List<PartnerRequest> searchPartnerAccountByPm(PartnerRequest partnerRequest);

  public List<PartnerAccount> getPartnerAccountsByPartnerId(Long partnerId,
      AccountType accountType);

  public List<PartnerRequest> findAllPartners();
  
  public ProgramManager findProgramManagerByPartnerId(String partnerId);

  public List<Partner> getPartnersByPMId(Long programManagerId);

  public List<Partner> getActivePartners();
}
