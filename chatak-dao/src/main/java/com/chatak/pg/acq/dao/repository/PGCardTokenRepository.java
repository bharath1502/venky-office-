/**
 * 
 */
package com.chatak.pg.acq.dao.repository;

import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import com.chatak.pg.acq.dao.model.PGCardTokenDetails;

/**
 *
 * << Add Comments Here >>
 *
 * @author Girmiti Software
 * @date Apr 29, 2015 9:12:37 PM
 * @version 1.0
 */
public interface PGCardTokenRepository extends JpaRepository<PGCardTokenDetails,Long>,QuerydslPredicateExecutor<PGCardTokenDetails> {

  public PGCardTokenDetails findByToken(String token) throws DataAccessException;
  
  public PGCardTokenDetails findByCardUserEmailAndCardLastFourDigitsAndCardType(String email,String lstFour,String cardType )throws DataAccessException;;
  
  public PGCardTokenDetails findByPan(String pan) throws DataAccessException;
  
  public List<PGCardTokenDetails> findByPgTokenCustomerId(Long pgTokenCustomerId) throws DataAccessException;
  
  public PGCardTokenDetails findByTokenAndPgTokenCustomerId(String token,Long pgTokenCustomerId) throws DataAccessException;
  
  public PGCardTokenDetails findByCardUserEmail(String email);
  
}
