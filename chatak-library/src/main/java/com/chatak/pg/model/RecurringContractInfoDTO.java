package com.chatak.pg.model;

import com.chatak.pg.bean.SearchRequest;


/**
 * @author Balaranga
 *
 */
public class RecurringContractInfoDTO extends SearchRequest
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2215196192441905816L;

	private Long recurringContractInfoId;
	
	private Long recurringCustInfoId;

	private String contractId;
	
	private String contractName;
	
	private String startDate;
	
	private String endDate;
	
	private Long contractAmount;
	
	private Long tax;
	
	private String total;
	
	private String status;
	
	private String contractExecution;
	
	private Long recurringpaymentInfoId;
	
	private String transactionApprovedEmail;
	
	private String transactionDeclinedEmail;
	
	private String nextBilldate;
	
	private Long billAmount;
	
	private String nameOnCard;
	
	private String lastBillDate;
	
	private String contractExecutionReprocess;
	
	private String merchantId;
	

	/**
   * @return the merchantId
   */
  public String getMerchantId() {
    return merchantId;
  }

  /**
   * @param merchantId the merchantId to set
   */
  public void setMerchantId(String merchantId) {
    this.merchantId = merchantId;
  }

  /**
	 * @return the lastBillDate
	 */
	public String getLastBillDate() {
		return lastBillDate;
	}

	/**
	 * @param lastBillDate the lastBillDate to set
	 */
	public void setLastBillDate(String lastBillDate) {
		this.lastBillDate = lastBillDate;
	}

	/**  
	 * @return the nameOnCard
	 */
	public String getNameOnCard() {
		return nameOnCard;
	}

	/**
	 * @param nameOnCard the nameOnCard to set
	 */
	public void setNameOnCard(String nameOnCard) {
		this.nameOnCard = nameOnCard;
	}
	
	/**
   * @param contractId the contractId to set
   */
  public void setContractId(String contractId) {
    this.contractId = contractId;
  }

	/**
	 * @return the recurringContractInfoId
	 */
	public Long getRecurringContractInfoId() {
		return recurringContractInfoId;
	}
	
	/**
   * @return the contractId
   */
  public String getContractId() {
    return contractId;
  }

	/**
	 * @param recurringContractInfoId the recurringContractInfoId to set
	 */
	public void setRecurringContractInfoId(Long recurringContractInfoId) {
		this.recurringContractInfoId = recurringContractInfoId;
	}

	/**
	 * @return the contractName
	 */
	public String getContractName() {
		return contractName;
	}

	/**
	 * @param contractName the contractName to set
	 */
	public void setContractName(String contractName) {
		this.contractName = contractName;
	}

	/**
	 * @return the startDate
	 */
	public String getStartDate() {
		return startDate;
	}

	/**
	 * @param startDate the startDate to set
	 */
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	/**
	 * @return the endDate
	 */
	public String getEndDate() {
		return endDate;
	}

	/**
	 * @return the contractAmount
	 */
	public Long getContractAmount() {
		return contractAmount;
	}
	
	/**
   * @param endDate the endDate to set
   */
  public void setEndDate(String endDate) {
    this.endDate = endDate;
  }

	/**
	 * @param contractAmount the contractAmount to set
	 */
	public void setContractAmount(Long contractAmount) {
		this.contractAmount = contractAmount;
	}

	/**
	 * @param tax the tax to set
	 */
	public void setTax(Long tax) {
		this.tax = tax;
	}

	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
   * @return the tax
   */
  public Long getTax() {
    return tax;
  }

	/**
	 * @return the contractExecution
	 */
	public String getContractExecution() {
		return contractExecution;
	}
	
	/**
   * @param status the status to set
   */
  public void setStatus(String status) {
    this.status = status;
  }

	/**
	 * @param contractExecution the contractExecution to set
	 */
	public void setContractExecution(String contractExecution) {
		this.contractExecution = contractExecution;
	}

	/**
	 * @return the recurringpaymentInfoId
	 */
	public Long getRecurringpaymentInfoId() {
		return recurringpaymentInfoId;
	}

	/**
	 * @param recurringpaymentInfoId the recurringpaymentInfoId to set
	 */
	public void setRecurringpaymentInfoId(Long recurringpaymentInfoId) {
		this.recurringpaymentInfoId = recurringpaymentInfoId;
	}

	/**
	 * @return the transactionApprovedEmail
	 */
	public String getTransactionApprovedEmail() {
		return transactionApprovedEmail;
	}

	/**
	 * @param transactionApprovedEmail the transactionApprovedEmail to set
	 */
	public void setTransactionApprovedEmail(String transactionApprovedEmail) {
		this.transactionApprovedEmail = transactionApprovedEmail;
	}

	/**
	 * @return the transactionDeclinedEmail
	 */
	public String getTransactionDeclinedEmail() {
		return transactionDeclinedEmail;
	}

	/**
	 * @param transactionDeclinedEmail the transactionDeclinedEmail to set
	 */
	public void setTransactionDeclinedEmail(String transactionDeclinedEmail) {
		this.transactionDeclinedEmail = transactionDeclinedEmail;
	}

	/**
	 * @return the nextBilldate
	 */
	public String getNextBilldate() {
		return nextBilldate;
	}

	/**
	 * @param nextBilldate the nextBilldate to set
	 */
	public void setNextBilldate(String nextBilldate) {
		this.nextBilldate = nextBilldate;
	}

	/**
	 * @return the billAmount
	 */
	public Long getBillAmount() {
		return billAmount;
	}

	/**
	 * @param billAmount the billAmount to set
	 */
	public void setBillAmount(Long billAmount) {
		this.billAmount = billAmount;
	}
	/**
	 * @return the recurringCustInfoId
	 */
	public Long getRecurringCustInfoId() {
		return recurringCustInfoId;
	}

	/**
	 * @param recurringCustInfoId the recurringCustInfoId to set
	 */
	public void setRecurringCustInfoId(Long recurringCustInfoId) {
		this.recurringCustInfoId = recurringCustInfoId;
	}
	/**
	 * @return the total
	 */
	public String getTotal() {
		return total;
	}

	/**
	 * @param total the total to set
	 */
	public void setTotal(String total) {
		this.total = total;
	}

  /**
   * @return the contractExecutionReprocess
   */
  public String getContractExecutionReprocess() {
    return contractExecutionReprocess;
  }

  /**
   * @param contractExecutionReprocess the contractExecutionReprocess to set
   */
  public void setContractExecutionReprocess(String contractExecutionReprocess) {
    this.contractExecutionReprocess = contractExecutionReprocess;
  }
		
}
