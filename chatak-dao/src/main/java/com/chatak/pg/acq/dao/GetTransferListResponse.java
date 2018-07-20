/**
 * 
 */
package com.chatak.pg.acq.dao;

import java.util.List;

import com.chatak.pg.acq.dao.model.PGTransfers;
import com.chatak.pg.user.bean.Response;

/**
 * @Author: Girmiti Software
 * @Date: Aug 18, 2015
 * @Time: 12:30:32 PM
 * @Version: 1.0
 * @Comments:
 */
public class GetTransferListResponse extends Response {

  /**
   * 
   */
  private static final long serialVersionUID = -6696062434128213675L;

  private List<PGTransfers> transferRequestsList;

  private Integer totalResultCount;

  private Integer fetchedRecordsCount;

  public List<PGTransfers> getTransferRequestsList() {
    return transferRequestsList;
  }

  public void setTransferRequestsList(List<PGTransfers> transferRequestsList) {
    this.transferRequestsList = transferRequestsList;
  }

  public Integer getTotalResultCount() {
    return totalResultCount;
  }

  public void setTotalResultCount(Integer totalResultCount) {
    this.totalResultCount = totalResultCount;
  }

  public Integer getFetchedRecordsCount() {
    return fetchedRecordsCount;
  }

  public void setFetchedRecordsCount(Integer fetchedRecordsCount) {
    this.fetchedRecordsCount = fetchedRecordsCount;
  }

}
