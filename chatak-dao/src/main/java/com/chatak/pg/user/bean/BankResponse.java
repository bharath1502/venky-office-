/**
 * 
 */
package com.chatak.pg.user.bean;

import java.util.List;

/**
 * @Author: Girmiti Software
 * @Date: Aug 1, 2016
 * @Time: 3:41:29 PM
 * @Version: 1.0
 * @Comments:
 *
 */
public class BankResponse extends Response {

  /**
   * 
   */
  private static final long serialVersionUID = -3109773965857723019L;

  private Long id;

  private String bankName;

  private List<BankRequest> bankRequests;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getBankName() {
    return bankName;
  }

  public void setBankName(String bankName) {
    this.bankName = bankName;
  }

  public List<BankRequest> getBankRequests() {
    return bankRequests;
  }

  public void setBankRequests(List<BankRequest> bankRequests) {
    this.bankRequests = bankRequests;
  }

}
