package com.chatak.pg.acq.dao.repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;

import com.chatak.pg.acq.dao.model.PGMerchant;
import com.chatak.pg.acq.dao.model.PGMerchantConfig;

/**
 * DAO Repository class to process Merchants
 * 
 * @author Girmiti Software
 * @date 01-Jan-2015 4:13:38 PM
 * @version 1.0
 */
public interface MerchantRepository extends JpaRepository<PGMerchant, Long>, QuerydslPredicateExecutor<PGMerchant> {

  public Optional<PGMerchant> findById(Long id);

  public PGMerchant findByMerchantCode(String merchantCode);

  public List<PGMerchant> findByMerchantCodeAndStatus(String merchantCode, Integer status);
  
  @Query("select t from PGMerchant t where t.merchantCode=:merchantCode and t.status = :status")
  public PGMerchant findOneMerchantCodeAndStatus(@Param("merchantCode") String merchantCode, @Param("status") Integer status);

  @Query("select t from PGMerchant t where t.userName=:userName and t.status <> 3")
  public PGMerchant findByUserName(@Param("userName") String userName);

  public PGMerchant findByEmailId(String email);
  
  @Query("select t from PGMerchant t where t.emailId=:email and t.status <> :status")
  public PGMerchant findByEmailIdAndStatusNotLike(@Param("email") String email, @Param("status") Integer status);
  
  @Query("select t from PGMerchant t where t.parentMerchantId=:parentMerchantId and t.status=0")
  public List<PGMerchant> findByParentMerchantId(@Param("parentMerchantId") Long parentMerchantId);
  
  public List<PGMerchant> findByParentMerchantIdAndStatus(Long parentMerchantId, Integer status);
  
  @Query("select t.merchantCode from PGMerchant t where t.id in(select t1.parentMerchantId from PGMerchant t1 where t1.merchantCode=:merchantCode)")
  public String getParentMerchantCode(@Param("merchantCode") String merchantCode);

  @Query("select t.agentId from PGMerchant t where t.merchantCode=:merchantCode")
  public String getAgentId(@Param("merchantCode") String merchantCode);
  
  @Query("select t.appMode from PGMerchant t where t.merchantCode=:merchantCode")
  public String getApplicationMode(@Param("merchantCode") String merchantCode);
  
  @Query("select new Map(t.merchantCode, t.businessName) from PGMerchant t")
  public List<Map<String,String>> getMerchantNameByMerchantCode();
  
  @Query("select new Map(t.merchantCode, t.businessName) from PGMerchant t where t.merchantType=:merchantType and t.status='0' ")
  public List<Map<String, String>> getMerchantNameByMerchantCodeByMerchantType(@Param("merchantType") String merchantType);
  
  @Query("select new Map(t.id, t.merchantCode || ' - ' || t.businessName) from PGMerchant t where t.merchantType=:merchantType and t.merchantCategory='Group' and t.status=0")
  public List<Map<String, String>> getMerchantMapByMerchantType(@Param("merchantType") String merchantType);
  
  @Query("select t.merchantType from PGMerchant t where t.merchantCode=:merchantCode")
  public String getMerchantTypeOnMerchantCode(@Param("merchantCode") String merchantCode);
  
  @Query("select t.businessName from PGMerchant t where t.merchantCode=:merchantCode")
  public String getMerchantBusinessNameOnMerchantCode(@Param("merchantCode") String merchantCode) ;

  public List<PGMerchant> findByMerchantCodeAndBusinessName(String merchantCode, String businessName, Pageable pageable);
  
  @Query("select new Map(t.merchantCode, t.merchantCode || ' - ' || t.businessName) from PGMerchant t where t.merchantType=:merchantType and t.status=0")
  public List<Map<String, String>> getMerchantCodeAndCompanyName(@Param("merchantType") String merchantType);
  
  @Query("select new Map(t.id, t.merchantCode || ' - ' || t.businessName) from PGMerchant t where status = 0")
  public List<Map<String, String>>  getMerchantList();
  
  @Query("select new Map(t.id, t.merchantCode || ' - ' || t.businessName) from PGMerchant t where t.id=:id")
  public List<Map<String, String>> getMerchantMapByMerchantId(@Param("id") Long id);
  
  public List<PGMerchant> findByStatus(Integer status);

/**
 * @param currencyName
 * @return
 */
  public List<PGMerchant> getMerchantByLocalCurrency(String currencyName);

/**
 * @param bankId
 * @return
 */
  public List<PGMerchant> findByBankId(Long bankId);

/**
 * @param merchantCategoryCode
 * @return
 */
  public List<PGMerchant> findByMcc(String merchantCategoryCode);

  @Query("select new Map(t.merchantCode, t.merchantCode || ' - ' || t.businessName) from PGMerchant t where t.parentMerchantId=(select p.id from PGMerchant p where p.merchantCode=:merchantCode) and t.status=0")
  public List<Map<String, String>> getSubMerchantMapByMerchantId(@Param("merchantCode") String merchantCode);

  @Query("select t.merchantCode from PGMerchant t where t.parentMerchantId=:id or id=:id")
  public List<String> findMerchantsList(@Param("id") Long id);

  @Query(value = "select t From PGMerchant t where t.merchantConfig=:deviceManufactId")
  public PGMerchant findByMerchantConfig(@Param("deviceManufactId") PGMerchantConfig deviceManufactId);
  
//  @Query(value = "select pgm.id, pgmc.autoSettlement from PGMerchant pgm join PGMerchantConfig pgmc on pgm.merchantConfig = pgmc.id and pgm.merchantCode = :merchantCode")
//  public PGMerchant getMerchantAutoSettlementByCode(@Param("merchantCode") String merchantCode);
}
