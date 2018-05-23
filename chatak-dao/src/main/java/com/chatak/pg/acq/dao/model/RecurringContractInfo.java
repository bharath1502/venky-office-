package com.chatak.pg.acq.dao.model;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name = "PG_RECURRING_CONTRACT_INFO")
public class RecurringContractInfo implements Serializable
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4566813149131139419L;

	@Id
	/*@SequenceGenerator(name = "SEQ_PG_RECURRING_CONTRA_ID", sequenceName = "SEQ_PG_RECURR_CONTRA_INFO_ID")
	@GeneratedValue(generator = "SEQ_PG_RECURRING_CONTRA_ID")*/
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "RECURRING_CONTRACT_INFO_ID")
	private Long recurringContractInfoId;

	@Column(name = "CONTRACT_ID")
	private String contractId;
	
	@Column(name = "CONTRACT_NAME")
	private String contractName;
	
	@Column(name = "START_DATE")
	private Timestamp startDate;
	
	@Column(name = "END_DATE")
	private Timestamp endDate;
	
	@Column(name = "CONTACT_AMOUNT")
	private Long contractAmount;
	
	@Column(name = "TAX")
	private Long tax;
	
	@Column(name = "STATUS")
	private String status;
	
	@Column(name = "CONTRACT_EXECUTION")
	private String contractExecution;
	
	@Column(name = "RECURRING_PAYMENT_INFO_ID")
	private Long recurringPaymentInfoId;
	
	@Column(name = "TRANSACTION_APPROVED_EMAIL")
	private String transactionApprovedEmail;
	
	@Column(name = "TRANSACTION_DECLINED_EMAIL")
	private String transactionDeclinedEmail;

	@Column(name = "CREATED_DATE")
	private Timestamp createdDate;
	
	@Column(name = "UPDATED_DATE")
	private Timestamp updatedDate;
	
	@Column(name = "CREATED_BY")
	private String createdBy;
	
	@Column(name = "UPDATED_BY")
	private String updatedBy;

	@Column(name = "LAST_BILL_DATE")
	private Timestamp lastBillDate;
	
	@Column(name="CONTRACT_EXECUTION_REPROCESS")
	private String contractExecutionReprocess;
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

	/**
	 * @return the lastBillDate
	 */
	public Timestamp getLastBillDate() {
		return lastBillDate;
	}

	/**
	 * @param lastBillDate the lastBillDate to set
	 */
	public void setLastBillDate(Timestamp lastBillDate) {
		this.lastBillDate = lastBillDate;
	}

	/**
	 * @return the recurringContractInfoId
	 */
	public Long getRecurringContractInfoId() {
		return recurringContractInfoId;
	}

	/**
	 * @param recurringContractInfoId the recurringContractInfoId to set
	 */
	public void setRecurringContractInfoId(Long recurringContractInfoId) {
		this.recurringContractInfoId = recurringContractInfoId;
	}

	/**
	 * @return the contractId
	 */
	public String getContractId() {
		return contractId;
	}

	/**
	 * @param contractId the contractId to set
	 */
	public void setContractId(String contractId) {
		this.contractId = contractId;
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
	public Timestamp getStartDate() {
		return startDate;
	}

	/**
	 * @param startDate the startDate to set
	 */
	public void setStartDate(Timestamp startDate) {
		this.startDate = startDate;
	}

	/**
	 * @return the endDate
	 */
	public Timestamp getEndDate() {
		return endDate;
	}

	/**
	 * @param endDate the endDate to set
	 */
	public void setEndDate(Timestamp endDate) {
		this.endDate = endDate;
	}

	/**
	 * @return the contractAmount
	 */
	public Long getContractAmount() {
		return contractAmount;
	}

	/**
	 * @param contractAmount the contractAmount to set
	 */
	public void setContractAmount(Long contractAmount) {
		this.contractAmount = contractAmount;
	}

	/**
	 * @return the tax
	 */
	public Long getTax() {
		return tax;
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
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * @return the contractExecution
	 */
	public String getContractExecution() {
		return contractExecution;
	}

	/**
	 * @param contractExecution the contractExecution to set
	 */
	public void setContractExecution(String contractExecution) {
		this.contractExecution = contractExecution;
	}

	/**
	 * @return the recurringPaymentInfoId
	 */
	public Long getRecurringPaymentInfoId() {
		return recurringPaymentInfoId;
	}

	/**
	 * @param recurringPaymentInfoId the recurringPaymentInfoId to set
	 */
	public void setRecurringPaymentInfoId(Long recurringPaymentInfoId) {
		this.recurringPaymentInfoId = recurringPaymentInfoId;
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
	 * @return the createdDate
	 */
	public Timestamp getCreatedDate() {
		return createdDate;
	}

	/**
	 * @param createdBy the createdBy to set
	 */
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	/**
	 * @param updatedDate the updatedDate to set
	 */
	public void setUpdatedDate(Timestamp updatedDate) {
		this.updatedDate = updatedDate;
	}

	/**
	 * @return the createdBy
	 */
	public String getCreatedBy() {
		return createdBy;
	}

	/**
	 * @return the updatedDate
	 */
	public Timestamp getUpdatedDate() {
		return updatedDate;
	}

	/**
	 * @return the updatedBy
	 */
	public String getUpdatedBy() {
		return updatedBy;
	}
	
	/**
	 * @param createdDate the createdDate to set
	 */
	public void setCreatedDate(Timestamp createdDate) {
		this.createdDate = createdDate;
	}

	/**
	 * @param updatedBy the updatedBy to set
	 */
	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}
	
}
