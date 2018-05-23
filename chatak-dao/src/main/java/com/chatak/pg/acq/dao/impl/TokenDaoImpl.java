/**
 * 
 */
package com.chatak.pg.acq.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import com.chatak.pg.acq.dao.TokenDao;
import com.chatak.pg.acq.dao.model.PGCardTokenDetails;
import com.chatak.pg.acq.dao.repository.PGCardTokenRepository;

/**
 *
 * << Add Comments Here >>
 *
 * @author Girmiti Software
 * @date Apr 29, 2015 9:23:55 PM
 * @version 1.0
 */
@Repository("tokenDao")
public class TokenDaoImpl implements TokenDao {
  
  @Autowired
  PGCardTokenRepository pgCardTokenRepository;
  
  /**
   * @param token
   * @return
   */
  @Override
  public PGCardTokenDetails getPgCardTokenDetails(String token) {
 
    return pgCardTokenRepository.findByToken(token);
  }

  /**
   * @param pgTokenDetails
   */
  @Override
  public void createTokenData(PGCardTokenDetails pgTokenDetails) {
   
    pgCardTokenRepository.save(pgTokenDetails);
  }

  /**
   * @param email
   * @param lstFour
   * @param cardType
   * @return
   */
  @Override
  public PGCardTokenDetails findByEmailAndCardLastFourAndCardType(String email, String lastFour, String cardType) {
   return pgCardTokenRepository.findByCardUserEmailAndCardLastFourDigitsAndCardType(email, lastFour, cardType);
  }
  
  @Override
  public PGCardTokenDetails findByPan(String pan) throws DataAccessException{
	  return pgCardTokenRepository.findByPan(pan);
  }

/**
 * @param tokenCustomerId
 * @return
 * @throws DataAccessException
 */
@Override
public List<PGCardTokenDetails> findByTokenCustomerId(Long pgTokenCustomerId)
		throws DataAccessException {
	return pgCardTokenRepository.findByPgTokenCustomerId(pgTokenCustomerId);
}

@Override
public PGCardTokenDetails findByTokenAndTokenCustomerId(String token,Long pgTokenCustomerId) throws DataAccessException{
	  return pgCardTokenRepository.findByTokenAndPgTokenCustomerId(token, pgTokenCustomerId);
}

/**
 * @param email
 * @return
 * @throws DataAccessException
 */
@Override
public PGCardTokenDetails findByCardUserEmail(String email) throws DataAccessException {
  return pgCardTokenRepository.findByCardUserEmail(email);
}
}
