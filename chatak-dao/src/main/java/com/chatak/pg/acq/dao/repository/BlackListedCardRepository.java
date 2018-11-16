/**
 * 
 */
package com.chatak.pg.acq.dao.repository;

import java.math.BigInteger;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import com.chatak.pg.acq.dao.model.PGBlackListedCard;

/**
 * @Author: Girmiti Software
 * @Date: Aug 5, 2016
 * @Time: 4:33:51 PM
 * @Version: 1.0
 * @Comments: 
 *
 */
public interface BlackListedCardRepository extends JpaRepository<PGBlackListedCard, Long>,
QuerydslPredicateExecutor<PGBlackListedCard> {

	public PGBlackListedCard findByCardNumber(BigInteger cardNumber);

	public Optional<PGBlackListedCard> findById(Long id);
	
	public PGBlackListedCard findByCardNumberAndStatusNotLike(BigInteger cardNumber, Integer status);
}