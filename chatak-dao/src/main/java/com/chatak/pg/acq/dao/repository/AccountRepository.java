package com.chatak.pg.acq.dao.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.repository.query.Param;

import com.chatak.pg.acq.dao.model.PGAccount;

/**
 * DAO Repository class to create merchant account
 * @Author: Girmiti Software
 * @Date: Apr 28, 2015
 * @Time: 9:00:14 PM
 * @Version: 1.0
 * @Comments: 
 *
 */
public interface AccountRepository extends JpaRepository<PGAccount, Long>,
                                      QueryDslPredicateExecutor<PGAccount> {
  
  
  
  public PGAccount findByAccountNum(Long accountNum);
  
  public PGAccount findByEntityId(String entityId);
  
  @Query("select t from PGAccount t where t.autoTransferDay LIKE :autoTransferDay% and t.autoPaymentMethod =:autoPaymentMethod and t.availableBalance >0L ")
  public List<PGAccount> findByPayoutFrequencyAndautoPaymentMethod(@Param("autoTransferDay")  String payoutFrequency,@Param("autoPaymentMethod")  String autoPaymentMethod);
  
  @Query("select sum(t.availableBalance) from PGAccount t where t.entityType=:entityType and t.currency=:currency ")
  public Long getBalanceOnAccount(@Param("entityType") String entityType,@Param("currency") String currency);
  
  @Query("select count(*) from PGAccount t where t.entityType=:entityType and t.currency=:currency")
  public Long getAccountCountOnEntityType(@Param("entityType") String entityType, @Param("currency") String pgCurrency);
  
  @Query("select t from PGAccount t where t.autoTransferDay LIKE :autoTransferDay% and t.availableBalance >0L ")
  public List<PGAccount> findByPayoutFrequency(@Param("autoTransferDay")  String payoutFrequency);

  public PGAccount findById(Long accountId);

  public PGAccount findByEntityIdAndCategory(String merchantCode, String primaryAccount);
  
  public List<PGAccount> findByEntityIdAndCategoryAndStatus(String merchantCode, String primaryAccount,String status);
 
  public List<PGAccount> findByEntityIdAndStatus(String merchantCode, String status);
  
  @Query("select t from PGAccount t where t.entityId =:merchantCode and t.status <> :status ")
  public List<PGAccount> findByEntityIdAndStatusNotLike(@Param("merchantCode") String merchantCode, @Param("status") String status);
  
  public PGAccount findByEntityTypeAndCurrencyAndStatus(String entityType, String currency, String status);

/**
 * @return
 */
  @Query("select distinct currency from PGAccount")
public List<String> getAllCurrency();

}