package com.chatak.pg.model;

import java.io.Serializable;

import com.chatak.pg.bean.CheckBeneficiary;
import com.chatak.pg.bean.CreditAccount;
import com.chatak.pg.bean.DebitAccount;

public class FundTransferDTO implements Serializable {
  
  public FundTransferDTO() {
    this.creditAccount=new CreditAccount();
    this.debitAccount=new DebitAccount();
    this.checkBeneficiary=new CheckBeneficiary();
  }

  /**
   * 
   */
  private static final long serialVersionUID = -3008706621837528177L;
  
  private CheckBeneficiary checkBeneficiary;
  private CreditAccount creditAccount;
  private DebitAccount debitAccount;
  private String amountToTransfer;
  private String fundTransferMode;
  private String merchantCode;
  private String batchId;
  
  public CheckBeneficiary getCheckBeneficiary() {
    return checkBeneficiary;
  }
  public void setCheckBeneficiary(CheckBeneficiary checkBeneficiary) {
    this.checkBeneficiary = checkBeneficiary;
  }
  public CreditAccount getCreditAccount() {
    return creditAccount;
  }
  public void setCreditAccount(CreditAccount creditAccount) {
    this.creditAccount = creditAccount;
  }
  public DebitAccount getDebitAccount() {
    return debitAccount;
  }
  public void setDebitAccount(DebitAccount debitAccount) {
    this.debitAccount = debitAccount;
  }
  public String getFundTransferMode() {
    return fundTransferMode;
  }
  public void setFundTransferMode(String fundTransferMode) {
    this.fundTransferMode = fundTransferMode;
  }
  public String getMerchantCode() {
    return merchantCode;
  }
  public void setMerchantCode(String merchantCode) {
    this.merchantCode = merchantCode;
  }
  public String getAmountToTransfer() {
    return amountToTransfer;
  }
  public void setAmountToTransfer(String amountToTransfer) {
    this.amountToTransfer = amountToTransfer;
  }
  /**
   * @return the batchId
   */
  public String getBatchId() {
    return batchId;
  }
  /**
   * @param batchId the batchId to set
   */
  public void setBatchId(String batchId) {
    this.batchId = batchId;
  }
}
