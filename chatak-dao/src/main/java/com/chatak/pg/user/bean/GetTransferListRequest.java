/**
 * 
 */
package com.chatak.pg.user.bean;

import java.io.Serializable;
import java.util.List;

import com.chatak.pg.acq.dao.model.PGTransfers;

/**
 * @Author: Girmiti Software
 * @Date: Aug 18, 2015
 * @Time: 12:28:01 PM
 * @Version: 1.0
 * @Comments: 
 *
 */
public class GetTransferListRequest implements Serializable {
  
  /**
   * 
   */
  private static final long serialVersionUID = -352222328359013148L;
  
  private List<PGTransfers> pgTransfersList;

  private Integer pageIndex;

  private Integer pageSize;
    
  private Integer noOfRecords;
  
  private String transferMode;
  
  private String status;
  
  private String from_date;
  
  private String to_date;
  
  private String currency;
  
  private String merchantCode;
  

  public Integer getPageIndex() {
    return pageIndex;
  }

  public void setPageIndex(Integer pageIndex) {
    this.pageIndex = pageIndex;
  }
  
  public void setPgTransfersList(List<PGTransfers> pgTransfersList) {
    this.pgTransfersList = pgTransfersList;
  }

  public Integer getPageSize() {
    return pageSize;
  }

  public void setPageSize(Integer pageSize) {
    this.pageSize = pageSize;
  }
  
  public List<PGTransfers> getPgTransfersList() {
    return pgTransfersList;
  }

  public Integer getNoOfRecords() {
    return noOfRecords;
  }

  public void setNoOfRecords(Integer noOfRecords) {
    this.noOfRecords = noOfRecords;
  }

  public String getTransferMode() {
    return transferMode;
  }

  public void setTransferMode(String transferMode) {
    this.transferMode = transferMode;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

public String getFrom_date() {
	return from_date;
}

public void setFrom_date(String from_date) {
	this.from_date = from_date;
}

public String getTo_date() {
	return to_date;
}

public void setTo_date(String to_date) {
	this.to_date = to_date;
}

public String getCurrency() {
	return currency;
}

public void setCurrency(String currency) {
	this.currency = currency;
}

public String getMerchantCode() {
	return merchantCode;
}

public void setMerchantCode(String merchantCode) {
	this.merchantCode = merchantCode;
}
  

}
