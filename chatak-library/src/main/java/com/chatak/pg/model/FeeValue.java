package com.chatak.pg.model;

import com.chatak.pg.bean.SearchRequest;

public class FeeValue extends SearchRequest
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 8790445025401459685L;
	private Long feeCode;
	private String feeShortCode;
	private Long feeProgramId;
	private Double feeMaxValue;
	private Double feeMinValue;
	private String feePercentage;
	private Long feePgmValueId;
	private String cardType;
	private String flatFee;
	private String accountType;
	private Long feeValueId;
	private String accountNumber;
	
	
	/**
	 * @return the feeCode
	 */
	public Long getFeeCode() {
		return feeCode;
	}
	/**
	 * @param feeCode the feeCode to set
	 */
	public void setFeeCode(Long feeCode) {
		this.feeCode = feeCode;
	}
	/**
	 * @return the feeShortCode
	 */
	public String getFeeShortCode() {
		return feeShortCode;
	}
	/**
	 * @param feeShortCode the feeShortCode to set
	 */
	public void setFeeShortCode(String feeShortCode) {
		this.feeShortCode = feeShortCode;
	}
	/**
	 * @return the feeProgramId
	 */
	public Long getFeeProgramId() {
		return feeProgramId;
	}
	/**
	 * @param feeProgramId the feeProgramId to set
	 */
	public void setFeeProgramId(Long feeProgramId) {
		this.feeProgramId = feeProgramId;
	}
	/**
	 * @return the feeMaxValue
	 */
	public Double getFeeMaxValue() {
		return feeMaxValue;
	}
	/**
	 * @param feeMaxValue the feeMaxValue to set
	 */
	public void setFeeMaxValue(Double feeMaxValue) {
		this.feeMaxValue = feeMaxValue;
	}
	/**
	 * @return the feeMinValue
	 */
	public Double getFeeMinValue() {
		return feeMinValue;
	}
	/**
	 * @param feeMinValue the feeMinValue to set
	 */
	public void setFeeMinValue(Double feeMinValue) {
		this.feeMinValue = feeMinValue;
	}
	/**
	 * @return the feePgmValueId
	 */
	public Long getFeePgmValueId() {
		return feePgmValueId;
	}
	/**
	 * @param feePgmValueId the feePgmValueId to set
	 */
	public void setFeePgmValueId(Long feePgmValueId) {
		this.feePgmValueId = feePgmValueId;
	}
  /**
   * @return the cardType
   */
  public String getCardType() {
    return cardType;
  }
  /**
   * @param cardType the cardType to set
   */
  public void setCardType(String cardType) {
    this.cardType = cardType;
  }
  /**
   * @return the feeValueId
   */
  public Long getFeeValueId() {
    return feeValueId;
  }
  /**
   * @param feeValueId the feeValueId to set
   */
  public void setFeeValueId(Long feeValueId) {
    this.feeValueId = feeValueId;
  }
  /**
   * @return the accountType
   */
  public String getAccountType() {
    return accountType;
  }
  /**
   * @param accountType the accountType to set
   */
  public void setAccountType(String accountType) {
    this.accountType = accountType;
  }
  /**
   * @return the accountNumber
   */
  public String getAccountNumber() {
    return accountNumber;
  }
  /**
   * @param accountNumber the accountNumber to set
   */
  public void setAccountNumber(String accountNumber) {
    this.accountNumber = accountNumber;
  }
  
	
  public String getFeePercentage() {
    return feePercentage;
  }
  public void setFeePercentage(String feePercentage) {
    this.feePercentage = feePercentage;
  }
  public String getFlatFee() {
    return flatFee;
  }
  public void setFlatFee(String flatFee) {
    this.flatFee = flatFee;
  }
  /**
   * 
   * Method to check if the object is empty or not
   * @return
   */
  public boolean isEmpty(){
    if((null!=this.feeCode)||(null!=this.feeShortCode)||(null!=this.feeProgramId)||(null!=this.feeMaxValue)||(null!=this.feeMinValue)||(null!=this.feePercentage)||(null!=this.feePgmValueId)||(null!=this.cardType)||(null!=this.flatFee)||(null!=this.accountType)||(null!=this.feeValueId)||(null!=this.accountNumber)){
      return false;
    }
    return true;
  }
	
}
