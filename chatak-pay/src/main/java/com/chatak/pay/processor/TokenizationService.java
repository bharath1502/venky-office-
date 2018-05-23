/**
 * 
 */
package com.chatak.pay.processor;

import com.chatak.pay.controller.model.CardDetails;
import com.chatak.pay.exception.ChatakPayException;

/**
 *
 * << Add Comments Here >>
 *
 * @author Girmiti Software
 * @date 01-Apr-2015 4:28:06 PM
 * @version 1.0
 */
public interface TokenizationService {

  /**
   * Method to tokenize the Card
   * 
   * @param cardDetails
   * @return
   */
  public String tokenize(CardDetails cardDetails)throws ChatakPayException;
  
  /**
   * Method to de-tokenize the token and get the card details
   * 
   * @param token
   * @return
   */
  public CardDetails deTokenize(String token);
  
}
