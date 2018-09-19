/**
 * 
 */
package com.chatak.pay.controller.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.chatak.pg.model.AccountBalanceDTO;

/**
 *
 * << Add Comments Here >>
 *
 * @author Girmiti Software
 * @date 19-May-2016 3:45:24 pm
 * @version 1.0
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL) 
public class AccountBalanceInquiryResponse extends Response {

  /**
   * 
   */
  private static final long serialVersionUID = -8932751187461719991L;
  
  private List<AccountBalanceDTO> accountBalances;

  public List<AccountBalanceDTO> getAccountBalances() {
    return accountBalances;
  }

  public void setAccountBalances(List<AccountBalanceDTO> accountBalances) {
    this.accountBalances = accountBalances;
  }

}
