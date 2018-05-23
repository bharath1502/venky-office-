package com.chatak.pg.constants;

public class FeePostingStatus {
  
  private FeePostingStatus() {
 // Do nothing
  }

  public static final Integer FEE_POST_SUCCESS = 0;

  public static final Integer FEE_POST_PENDING = 1;

  public static final Integer FEE_POST_DECLINED = 2;

  public static final Integer FEE_POST_NETWORK_FAIL = 3;

}
