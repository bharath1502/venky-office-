/**
 * 
 */
package com.chatak.pg.user.bean;

import java.util.List;

/**
 * @Author: Girmiti Software
 * @Date: Jun 17, 2015
 * @Time: 11:30:01 AM
 * @Version: 1.0
 * @Comments: 
 *
 */
public class GetAccountHistoryListResponse extends Response{
  /**
   * 
   */
  private static final long serialVersionUID = 177513787584914520L;
  private List<MerchantAccountHistory> accountHistoryList;
  private Integer fetchedRecordsCount;
  private Integer totalResultCount;
  /**
   * @return the accountHistoryList
   */
  public List<MerchantAccountHistory> getAccountHistoryList() {
    return accountHistoryList;
  }
  /**
   * @return the totalResultCount
   */
  public Integer getTotalResultCount() {
    return totalResultCount;
  }
  /**
   * @param accountHistoryList the accountHistoryList to set
   */
  public void setAccountHistoryList(List<MerchantAccountHistory> accountHistoryList) {
    this.accountHistoryList = accountHistoryList;
  }
  /**
   * @return the fetchedRecordsCount
   */
  public Integer getFetchedRecordsCount() {
    return fetchedRecordsCount;
  }
  /**
   * @param totalResultCount the totalResultCount to set
   */
  public void setTotalResultCount(Integer totalResultCount) {
    this.totalResultCount = totalResultCount;
  }
  /**
   * @param fetchedRecordsCount the fetchedRecordsCount to set
   */
  public void setFetchedRecordsCount(Integer fetchedRecordsCount) {
    this.fetchedRecordsCount = fetchedRecordsCount;
  }
  

}
