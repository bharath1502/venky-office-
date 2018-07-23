package com.chatak.pg.acq.dao.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.repository.query.Param;

import com.chatak.pg.acq.dao.model.PGAdminUser;
import com.chatak.pg.acq.dao.model.PGMerchantUsers;

public interface MerchantUserRepository extends
                                       JpaRepository<PGMerchantUsers, Long>,
                                       QueryDslPredicateExecutor<PGMerchantUsers> {

  public List<PGMerchantUsers> findByUserNameAndMerPassword(String userName, String merPassword);

  public PGMerchantUsers findById(Long id);

  @Query("select t from PGMerchantUsers t where t.userName=:userName and t.status <> :status")
  public PGMerchantUsers findByUserNameAndStatusNotLike(@Param("userName") String userName, @Param("status") Integer status);

  public PGMerchantUsers findByIdAndEmailToken(Long id, String emailToken);
  
  @Query("select t from PGMerchantUsers t where t.email=:email and t.status = 0")
  public PGMerchantUsers findByEmail(@Param("email") String email);
  
  @Query("select t from PGMerchantUsers t where t.email=:email and t.status <> :status")
  public PGMerchantUsers findByEmailIdAndStatusNotLike(@Param("email") String email, @Param("status") Integer status);
  
  public List<PGMerchantUsers> findByUserNameAndUserType(String userName, String userType);

/**
 * @param pgMerchantId
 * @return
 */
public PGMerchantUsers findByPgMerchantId(Long pgMerchantId);

/**
 * @return
 */
@Query("select userRoleId from PGMerchantUsers where status != 3")
public List<Long> getRoleList();

public List<PGMerchantUsers> findByPassRetryCount(Integer status);

}
