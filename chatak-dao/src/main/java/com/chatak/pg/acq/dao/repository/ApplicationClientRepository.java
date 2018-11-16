package com.chatak.pg.acq.dao.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;

import com.chatak.pg.acq.dao.model.PGApplicationClient;

/**
 *
 * << Add Comments Here >>
 *
 * @author Girmiti Software
 * @date 06-Mar-2015 10:06:59 AM
 * @version 1.0
 */
public interface ApplicationClientRepository extends
                                            JpaRepository<PGApplicationClient, Long>,
                                            QuerydslPredicateExecutor<PGApplicationClient> {

  /**
   * DAO method to find Application Client on Primary Key
   * 
   * @param id
   * @return
   */
  public Optional<PGApplicationClient> findById(Long id);

  /**
   * DAO method to find Application Client on Client ID and Client Access
   * 
   * @param appClientId
   * @param appClientAccess
   * @return
   */
  public List<PGApplicationClient> findByAppClientIdAndAppClientAccess(String appClientId, String appClientAccess);

  /**
   * DAO method to find Application Client on auth user and auth password
   * 
   * @param appAuthUser
   * @param appAuthPass
   * @return
   */
  public List<PGApplicationClient> findByAppAuthUserAndAppAuthPass(String appAuthUser, String appAuthPass);
  
  /**
   * DAO method to find Application Client on Client ID
   * 
   * @param appClientId
   * @return
   */
  @Query("SELECT ac FROM PGApplicationClient ac where ac.id=(select min(a.id) from PGApplicationClient a where a.appClientId=:appClientId)")
  public List<PGApplicationClient> findByAppClientId(@Param("appClientId")String appClientId);

  /**
   * DAO method to find Application Client on auth user
   * 
   * @param appAuthUser
   * @return
   */
  public List<PGApplicationClient> findByAppAuthUser(String appAuthUser);

}
