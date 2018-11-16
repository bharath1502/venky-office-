package com.chatak.pg.acq.dao.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import com.chatak.pg.acq.dao.model.PGReseller;

/**
 * DAO Repository class to process Merchants
 * 
 * @author Girmiti Software
 * @date 01-Jan-2015 4:13:38 PM
 * @version 1.0
 */
public interface ResellerRepository extends JpaRepository<PGReseller, Long>, QuerydslPredicateExecutor<PGReseller> {

  public PGReseller findByEmailId(String emailId);

  public PGReseller findByResellerName(String resellerName);
  
  public PGReseller findByResellerId(Long resellerId);
  
  public List<PGReseller> findByEmailIdOrderByUpdatedDateDesc(String emailId);

  public List<PGReseller> findByResellerNameOrderByUpdatedDateDesc(String resellerName);

  public List<PGReseller> findByEmailIdOrResellerNameOrderByCreatedDateDesc(String emailId, String resellerName);

}