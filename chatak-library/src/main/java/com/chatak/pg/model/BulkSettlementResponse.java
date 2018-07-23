package com.chatak.pg.model;

public class BulkSettlementResponse {
  
private boolean isSuccess;
private int successCount;
public boolean isSuccess() {
  return isSuccess;
}
public void setSuccess(boolean isSuccess) {
  this.isSuccess = isSuccess;
}
public int getSuccessCount() {
  return successCount;
}
public void setSuccessCount(int successCount) {
  this.successCount = successCount;
}

}
