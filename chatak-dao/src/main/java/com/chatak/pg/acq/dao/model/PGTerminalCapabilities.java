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
@Table(name = "pg_terminal_capabilities")
public class PGTerminalCapabilities implements Serializable {


	/**
	 * 
	 */
	private static final long serialVersionUID = -4835486396894804028L;

	
	@Id
	/*@SequenceGenerator(name = "SEQ_PG_TERMINAL_CAPABILITIES_TERMINAL_CAPABILITIES_ID", sequenceName = "SEQ_PG_TERMINAL_CAPABILITIES")
	@GeneratedValue(generator = "SEQ_PG_TERMINAL_CAPABILITIES_TERMINAL_CAPABILITIES_ID")*/
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "terminal_capabilities_id")
	private Long terminalCapabilitiesId;

	@Column(name = "manual_entry_key")
	private Boolean manualEntryKey;
	
	@Column(name = "ic_with_contacts")
	private Boolean iCWithContacts;
	
	@Column(name = "magnetic_stripes")
	private Boolean magneticStripes;
	
	@Column(name = "enciphered_pin_for_online")
	private Boolean encipheredPINForOnlineVerification;
	
	@Column(name = "plain_text_pin_for_icc")
	private Boolean plainTextPINForICCVerification;
	
	@Column(name = "enciphered_pin_for_offline")
	private Boolean encipheredPINForOfflineVerification;
	
	@Column(name = "no_cvm_required")
	private Boolean noCVMRequired;
	
	@Column(name = "signature")
	private Boolean signature;
	
	@Column(name = "cda")
	private Boolean cda;
	
	@Column(name = "sda")
	private Boolean sda;
	
	@Column(name = "dda")
	private Boolean dda;
	
	@Column(name = "card_capture")
	private Boolean cardCapture;
	
	@Column(name = "cash")
	private Boolean cash;
	
	@Column(name = "transfer")
	private Boolean transfer;
	
	@Column(name = "numeric_keys")
	private Boolean numericKeys;
	
	@Column(name = "function_Keys")
	private Boolean functionKeys;
	
	@Column(name = "display_cardholder")
	private Boolean displayCardholder;
	
	@Column(name = "goods")
	private Boolean goods;
	
	@Column(name = "payment")
	private Boolean payment;
	
	@Column(name = "alphanumeric_special")
	private Boolean alphanumericSpecialCharacters;
	
	@Column(name = "print_attendant")
	private Boolean printAttendant;
	
	@Column(name = "services")
	private Boolean services;
	
	@Column(name = "administrative")
	private Boolean administrative;
	
	@Column(name = "print_cardholder")
	private Boolean printCardholder;
	
	@Column(name = "inquiry")
	private Boolean inquiry;
	
	@Column(name = "cash_deposits")
	private Boolean cashDeposits;
	
	@Column(name = "command_keys")
	private Boolean commandKeys;
	
	@Column(name = "display_attendant")
	private Boolean displayAttendant;
	
	@Column(name = "financial_Institute")
	private Boolean financialInstitute;
		
	@Column(name = "merchant")
	private Boolean merchant;	
	
	@Column(name = "created_date")
	private Timestamp createdDate;
	
	@Column(name = "updated_date")
	private Timestamp updatedDate;
	
	@Column(name = "created_by")
	private String createdBy;

	@Column(name = "updated_by")
	private String updatedBy;
	
	/**
	 * @return the manualEntryKey
	 */
	public Boolean getManualEntryKey() {
		return manualEntryKey;
	}

	/**
	 * @param manualEntryKey the manualEntryKey to set
	 */
	public void setManualEntryKey(Boolean manualEntryKey) {
		this.manualEntryKey = manualEntryKey;
	}

	/**
	 * @return the magneticStripes
	 */
	public Boolean getMagneticStripes() {
		return magneticStripes;
	}

	/**
	 * @param magneticStripes the magneticStripes to set
	 */
	public void setMagneticStripes(Boolean magneticStripes) {
		this.magneticStripes = magneticStripes;
	}

	/**
	 * @return the plainTextPINForICCVerification
	 */
	public Boolean getPlainTextPINForICCVerification() {
		return plainTextPINForICCVerification;
	}

	/**
	 * @param plainTextPINForICCVerification the plainTextPINForICCVerification to set
	 */
	public void setPlainTextPINForICCVerification(
			Boolean plainTextPINForICCVerification) {
		this.plainTextPINForICCVerification = plainTextPINForICCVerification;
	}

	/**
	 * @return the encipheredPINForOnlineVerification
	 */
	public Boolean getEncipheredPINForOnlineVerification() {
		return encipheredPINForOnlineVerification;
	}

	/**
	 * @param encipheredPINForOnlineVerification the encipheredPINForOnlineVerification to set
	 */
	public void setEncipheredPINForOnlineVerification(
			Boolean encipheredPINForOnlineVerification) {
		this.encipheredPINForOnlineVerification = encipheredPINForOnlineVerification;
	}

	/**
	 * @return the encipheredPINForOfflineVerification
	 */
	public Boolean getEncipheredPINForOfflineVerification() {
		return encipheredPINForOfflineVerification;
	}

	/**
	 * @param encipheredPINForOfflineVerification the encipheredPINForOfflineVerification to set
	 */
	public void setEncipheredPINForOfflineVerification(
			Boolean encipheredPINForOfflineVerification) {
		this.encipheredPINForOfflineVerification = encipheredPINForOfflineVerification;
	}

	/**
	 * @return the signature
	 */
	public Boolean getSignature() {
		return signature;
	}

	/**
	 * @param signature the signature to set
	 */
	public void setSignature(Boolean signature) {
		this.signature = signature;
	}

	/**
	 * @return the noCVMRequired
	 */
	public Boolean getNoCVMRequired() {
		return noCVMRequired;
	}

	/**
	 * @param noCVMRequired the noCVMRequired to set
	 */
	public void setNoCVMRequired(Boolean noCVMRequired) {
		this.noCVMRequired = noCVMRequired;
	}

	/**
	 * @return the iCWithContacts
	 */
	public Boolean getiCWithContacts() {
		return iCWithContacts;
	}

	/**
	 * @param iCWithContacts the iCWithContacts to set
	 */
	public void setiCWithContacts(Boolean iCWithContacts) {
		this.iCWithContacts = iCWithContacts;
	}

	/**
	 * @return the cardCapture
	 */
	public Boolean getCardCapture() {
		return cardCapture;
	}

	/**
	 * @param cardCapture the cardCapture to set
	 */
	public void setCardCapture(Boolean cardCapture) {
		this.cardCapture = cardCapture;
	}

	/**
	 * @return the cda
	 */
	public Boolean getCda() {
		return cda;
	}

	/**
	 * @param cda the cda to set
	 */
	public void setCda(Boolean cda) {
		this.cda = cda;
	}

	/**
	 * @return the sda
	 */
	public Boolean getSda() {
		return sda;
	}

	/**
	 * @param sda the sda to set
	 */
	public void setSda(Boolean sda) {
		this.sda = sda;
	}

	/**
	 * @return the dda
	 */
	public Boolean getDda() {
		return dda;
	}

	/**
	 * @param dda the dda to set
	 */
	public void setDda(Boolean dda) {
		this.dda = dda;
	}

	/**
	 * @return the cash
	 */
	public Boolean getCash() {
		return cash;
	}

	/**
	 * @param cash the cash to set
	 */
	public void setCash(Boolean cash) {
		this.cash = cash;
	}

	/**
	 * @return the transfer
	 */
	public Boolean getTransfer() {
		return transfer;
	}

	/**
	 * @param transfer the transfer to set
	 */
	public void setTransfer(Boolean transfer) {
		this.transfer = transfer;
	}

	/**
	 * @return the numeric_Keys
	 */
	public Boolean getNumericKeys() {
		return numericKeys;
	}

	/**
	 * @param numeric_Keys the numeric_Keys to set
	 */
	public void setNumericKeys(Boolean numericKeys) {
		this.numericKeys = numericKeys;
	}

	/**
	 * @return the functionKeys
	 */
	public Boolean getFunctionKeys() {
		return functionKeys;
	}

	/**
	 * @param functionKeys the functionKeys to set
	 */
	public void setFunctionKeys(Boolean functionKeys) {
		this.functionKeys = functionKeys;
	}

	/**
	 * @return the displayCardholder
	 */
	public Boolean getDisplayCardholder() {
		return displayCardholder;
	}

	/**
	 * @param displayCardholder the displayCardholder to set
	 */
	public void setDisplayCardholder(Boolean displayCardholder) {
		this.displayCardholder = displayCardholder;
	}

	/**
	 * @return the goods
	 */
	public Boolean getGoods() {
		return goods;
	}

	/**
	 * @param goods the goods to set
	 */
	public void setGoods(Boolean goods) {
		this.goods = goods;
	}

	/**
	 * @return the payment
	 */
	public Boolean getPayment() {
		return payment;
	}

	/**
	 * @param payment the payment to set
	 */
	public void setPayment(Boolean payment) {
		this.payment = payment;
	}

	/**
	 * @return the alphanumericSpecialCharacters
	 */
	public Boolean getAlphanumericSpecialCharacters() {
		return alphanumericSpecialCharacters;
	}

	/**
	 * @param alphanumericSpecialCharacters the alphanumericSpecialCharacters to set
	 */
	public void setAlphanumericSpecialCharacters(
			Boolean alphanumericSpecialCharacters) {
		this.alphanumericSpecialCharacters = alphanumericSpecialCharacters;
	}

	/**
	 * @return the printAttendant
	 */
	public Boolean getPrintAttendant() {
		return printAttendant;
	}

	/**
	 * @param printAttendant the printAttendant to set
	 */
	public void setPrintAttendant(Boolean printAttendant) {
		this.printAttendant = printAttendant;
	}

	/**
	 * @return the services
	 */
	public Boolean getServices() {
		return services;
	}

	/**
	 * @param services the services to set
	 */
	public void setServices(Boolean services) {
		this.services = services;
	}

	/**
	 * @return the administrative
	 */
	public Boolean getAdministrative() {
		return administrative;
	}

	/**
	 * @param administrative the administrative to set
	 */
	public void setAdministrative(Boolean administrative) {
		this.administrative = administrative;
	}

	/**
	 * @return the printCardholder
	 */
	public Boolean getPrintCardholder() {
		return printCardholder;
	}

	/**
	 * @param printCardholder the printCardholder to set
	 */
	public void setPrintCardholder(Boolean printCardholder) {
		this.printCardholder = printCardholder;
	}

	/**
	 * @return the inquiry
	 */
	public Boolean getInquiry() {
		return inquiry;
	}

	/**
	 * @param inquiry the inquiry to set
	 */
	public void setInquiry(Boolean inquiry) {
		this.inquiry = inquiry;
	}

	/**
	 * @return the cashDeposits
	 */
	public Boolean getCashDeposits() {
		return cashDeposits;
	}

	/**
	 * @param cashDeposits the cashDeposits to set
	 */
	public void setCashDeposits(Boolean cashDeposits) {
		this.cashDeposits = cashDeposits;
	}

	/**
	 * @return the commandKeys
	 */
	public Boolean getCommandKeys() {
		return commandKeys;
	}

	/**
	 * @param commandKeys the commandKeys to set
	 */
	public void setCommandKeys(Boolean commandKeys) {
		this.commandKeys = commandKeys;
	}

	/**
	 * @return the displayAttendant
	 */
	public Boolean getDisplayAttendant() {
		return displayAttendant;
	}

	/**
	 * @param displayAttendant the displayAttendant to set
	 */
	public void setDisplayAttendant(Boolean displayAttendant) {
		this.displayAttendant = displayAttendant;
	}

	/**
	 * @return the terminalCapabilitiesId
	 */
	public Long getTerminalCapabilitiesId() {
		return terminalCapabilitiesId;
	}

	/**
	 * @param terminalCapabilitiesId the terminalCapabilitiesId to set
	 */
	public void setTerminalCapabilitiesId(Long terminalCapabilitiesId) {
		this.terminalCapabilitiesId = terminalCapabilitiesId;
	}

	/**
	 * @return the financialInstitute
	 */
	public Boolean getFinancialInstitute() {
		return financialInstitute;
	}

	/**
	 * @param financialInstitute the financialInstitute to set
	 */
	public void setFinancialInstitute(Boolean financialInstitute) {
		this.financialInstitute = financialInstitute;
	}

	/**
	 * @return the merchant
	 */
	public Boolean getMerchant() {
		return merchant;
	}

	/**
	 * @param merchant the merchant to set
	 */
	public void setMerchant(Boolean merchant) {
		this.merchant = merchant;
	}

	/**
	 * @return the createdDate
	 */
	public Timestamp getCreatedDate() {
		return createdDate;
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

	/**
	 * @param updatedDate the updatedDate to set
	 */
	public void setUpdatedDate(Timestamp updatedDate) {
		this.updatedDate = updatedDate;
	}

	/**
	 * @return the updatedBy
	 */
	public String getUpdatedBy() {
		return updatedBy;
	}

	/**
	 * @param createdBy the createdBy to set
	 */
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

}
