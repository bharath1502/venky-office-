/**
 * 
 */
package com.chatak.pg.acq.dao.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;

import com.chatak.pg.acq.dao.model.settlement.PGSettlementEntity;

/**
 * @Author: Girmiti Software
 * @Date: 02-Jul-2018
 * @Time: 6:38:21 PM
 * @Version: 1.0
 * @Comments: 
 *
 */
public interface IssuanceSettlementEntityRepository
extends JpaRepository<PGSettlementEntity, Long>, QueryDslPredicateExecutor<PGSettlementEntity> {

@Override
public List<PGSettlementEntity> findAll();

}
