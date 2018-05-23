/**
 * 
 */
package com.chatak.pg.model;


/**
 *
 * << Add Comments Here >>
 *
 * @author Girmiti Software
 * @date 09-Sept-2015 3:30:50 PM
 * @version 1.0
 */
public class AccountBalanceReportDTO {

	private String userName;
	
	private String businessName;
	
	private String accCreationDate;
	
	private Long accountNumber;
	
	private String accountType;
	
	private String currency;
	
	private String availableBalance;
	
	private String currentBalance;
	
	private String status;
	
	private Integer pageIndex;

	private Integer pageSize;

	private Integer noOfRecords;
	
  private Integer currencyExponent;

  private Integer currencySeparatorPosition;

  private Character currencyMinorUnit;
  
  public Integer getCurrencyExponent() {
    return currencyExponent;
  }

  public Integer getCurrencySeparatorPosition() {
    return currencySeparatorPosition;
  }

  public Character getCurrencyMinorUnit() {
    return currencyMinorUnit;
  }
  
  public void setCurrencySeparatorPosition(Integer currencySeparatorPosition) {
    this.currencySeparatorPosition = currencySeparatorPosition;
  }

  public void setCurrencyMinorUnit(Character currencyMinorUnit) {
    this.currencyMinorUnit = currencyMinorUnit;
  }

  public Character getCurrencyThousandsUnit() {
    return currencyThousandsUnit;
  }
  
  public void setCurrencyExponent(Integer currencyExponent) {
    this.currencyExponent = currencyExponent;
  }

  public void setCurrencyThousandsUnit(Character currencyThousandsUnit) {
    this.currencyThousandsUnit = currencyThousandsUnit;
  }

  private Character currencyThousandsUnit;

	/**
	 * @return the userName
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * @param userName the userName to set
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}

	/**
	 * @return the businessName
	 */
	public String getBusinessName() {
		return businessName;
	}

	/**
	 * @param businessName the businessName to set
	 */
	public void setBusinessName(String businessName) {
		this.businessName = businessName;
	}

	/**
	 * @return the accCreationDate
	 */
	public String getAccCreationDate() {
		return accCreationDate;
	}

	/**
	 * @param accCreationDate the accCreationDate to set
	 */
	public void setAccCreationDate(String accCreationDate) {
		this.accCreationDate = accCreationDate;
	}

	/**
	 * @return the accountNumber
	 */
	public Long getAccountNumber() {
		return accountNumber;
	}

	/**
	 * @param accountNumber the accountNumber to set
	 */
	public void setAccountNumber(Long accountNumber) {
		this.accountNumber = accountNumber;
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
	 * @return the currency
	 */
	public String getCurrency() {
		return currency;
	}

	/**
	 * @param currency the currency to set
	 */
	public void setCurrency(String currency) {
		this.currency = currency;
	}

	/**
	 * @return the availableBalance
	 */
	public String getAvailableBalance() {
		return availableBalance;
	}

	/**
	 * @param availableBalance the availableBalance to set
	 */
	public void setAvailableBalance(String availableBalance) {
		this.availableBalance = availableBalance;
	}

	/**
	 * @return the currentBalance
	 */
	public String getCurrentBalance() {
		return currentBalance;
	}

	/**
	 * @param currentBalance the currentBalance to set
	 */
	public void setCurrentBalance(String currentBalance) {
		this.currentBalance = currentBalance;
	}

	/**
	 * @return the pageIndex
	 */
	public Integer getPageIndex() {
		return pageIndex;
	}
	
	/**
   * @param pageSize the pageSize to set
   */
  public void setPageSize(Integer pageSize) {
    this.pageSize = pageSize;
  }

	/**
	 * @param pageIndex the pageIndex to set
	 */
	public void setPageIndex(Integer pageIndex) {
		this.pageIndex = pageIndex;
	}

	/**
	 * @return the noOfRecords
	 */
	public Integer getNoOfRecords() {
		return noOfRecords;
	}
	
	/**
   * @return the status
   */
  public String getStatus() {
    return status;
  }
  
  /**
   * @return the pageSize
   */
  public Integer getPageSize() {
    return pageSize;
  }

  /**
   * @param status the status to set
   */
  public void setStatus(String status) {
    this.status = status;
  }

	/**
	 * @param noOfRecords the noOfRecords to set
	 */
	public void setNoOfRecords(Integer noOfRecords) {
		this.noOfRecords = noOfRecords;
	}

		
}
