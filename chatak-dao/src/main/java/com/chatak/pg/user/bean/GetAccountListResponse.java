/**
 * 
 */
package com.chatak.pg.user.bean;

import java.util.List;

/**
 * @Author: Girmiti Software
 * @Date: Dec 20, 2016
 * @Time: 2:46:13 PM
 * @Version: 1.0
 * @Comments: 
 *
 */
public class GetAccountListResponse extends Response
{
private static final long serialVersionUID = 1L;
private List<MerchantAccountHistory> accountHistoryList;
private Integer totalResultCount;
private Integer fetchedRecordsCount;
/**
 * @return the accountHistoryList
 */
public List<MerchantAccountHistory> getAccountHistoryList() {
	return accountHistoryList;
}
/**
 * @param accountHistoryList the accountHistoryList to set
 */
public void setAccountHistoryList(List<MerchantAccountHistory> accountHistoryList) {
	this.accountHistoryList = accountHistoryList;
}
/**
 * @return the totalResultCount
 */
public Integer getTotalResultCount() {
	return totalResultCount;
}
/**
 * @param totalResultCount the totalResultCount to set
 */
public void setTotalResultCount(Integer totalResultCount) {
	this.totalResultCount = totalResultCount;
}
/**
 * @return the fetchedRecordsCount
 */
public Integer getFetchedRecordsCount() {
	return fetchedRecordsCount;
}
/**
 * @param fetchedRecordsCount the fetchedRecordsCount to set
 */
public void setFetchedRecordsCount(Integer fetchedRecordsCount) {
	this.fetchedRecordsCount = fetchedRecordsCount;
}
	

}
