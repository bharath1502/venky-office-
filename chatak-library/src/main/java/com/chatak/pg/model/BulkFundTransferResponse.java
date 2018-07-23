package com.chatak.pg.model;

public class BulkFundTransferResponse {
  
private int successCount;
private boolean isSuccess;
public boolean isSuccess() {
  return isSuccess;
}

public int getSuccessCount() {
  return successCount;
}
public void setSuccess(boolean isSuccess) {
  this.isSuccess = isSuccess;
}

public void setSuccessCount(int successCount) {
  this.successCount = successCount;
}

}
