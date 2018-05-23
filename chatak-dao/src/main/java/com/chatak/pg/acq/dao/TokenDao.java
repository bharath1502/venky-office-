/**
 * 
 */
package com.chatak.pg.acq.dao;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.chatak.pg.acq.dao.model.PGCardTokenDetails;

/**
 * << Add Comments Here >>
 *
 * @author Girmiti Software
 * @date Apr 29, 2015 9:22:28 PM
 * @version 1.0
 */

public interface TokenDao {

  public PGCardTokenDetails getPgCardTokenDetails(String token);

  public void createTokenData(PGCardTokenDetails pgTokenDetails);

  public PGCardTokenDetails findByEmailAndCardLastFourAndCardType(String email, String lastFour, String cardType);

  public PGCardTokenDetails findByPan(String pan) throws DataAccessException;

  public List<PGCardTokenDetails> findByTokenCustomerId(Long pgTokenCustomerId) throws DataAccessException;

  public PGCardTokenDetails findByTokenAndTokenCustomerId(String token, Long pgTokenCustomerId) throws DataAccessException;
  
  public PGCardTokenDetails findByCardUserEmail(String email) throws DataAccessException;

}
