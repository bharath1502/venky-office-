package com.chatak.pg.acq.dao;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.chatak.pg.acq.dao.model.PGApplicationClient;
import com.chatak.pg.acq.dao.model.PGMerchant;
import com.chatak.pg.acq.dao.model.PGMerchantUserFeatureMapping;
import com.chatak.pg.acq.dao.model.PGMerchantUsers;
import com.chatak.pg.model.AdminUserDTO;
import com.chatak.pg.model.GenericUserDTO;

public interface MerchantUserDao {

  /**
   * DAO method to authenticate PG service Merchant users
   * 
   * @param email
   * @param pgPass
   * @return
   * @throws DataAccessException
   */
  public PGMerchantUsers authenticateMerchant(String email, String pgPass) throws DataAccessException;

  /**
   * DAO method to authenticate PG service Merchant users
   * 
   * @param email
   * @param pgPass
   * @return
   * @throws DataAccessException
   */
  public List<PGMerchant> getMerchant(Long id) throws DataAccessException;

  public PGMerchantUsers findByUserName(String userName) throws DataAccessException;

  public PGMerchantUsers findByMerchantUserId(Long userId) throws DataAccessException;

  public PGMerchantUsers createOrUpdateUser(PGMerchantUsers merchantUsers) throws DataAccessException;

  public List<PGMerchantUsers> createOrUpdateUsers(List<PGMerchantUsers> pgMerchantUsersList) throws DataAccessException;

  public PGMerchantUsers findByAdminUserIdAndEmailToken(Long userId, String token);
  
  public List<GenericUserDTO> searchMerchantUsers(GenericUserDTO genericUserDTO);
  
  public PGMerchantUsers findByEmail(String email) throws DataAccessException;
  
  public List<PGMerchantUsers> findByUserNameAndType(String acqU, String userType) throws DataAccessException;

  public List<Long> getRoleListMerchant() throws DataAccessException;
  
  public PGMerchant findById(Long pgMerchantId);
  
  public  List<AdminUserDTO> searchMerchantUserList();

  public PGApplicationClient getApplicationClientAuth(String appAuthUser);

  public void saveOrUpdateApplicationClient(PGApplicationClient applicationClient);
  
  public PGMerchantUsers getMerchantUserByStatus(String userName) throws DataAccessException;
  
  public List<GenericUserDTO> searchMerchantUsersForPM(GenericUserDTO genericUserDTO, Long entityId);
  
  public PGMerchantUserFeatureMapping saveOrUpdateUserRoleFeatureMap(PGMerchantUserFeatureMapping gGMerchantUserFeatureMapping);
}
