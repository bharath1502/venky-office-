package com.chatak.pg.model;

import java.io.Serializable;


public class TerminalCapabilitiesDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3313374342903528161L;

	private Long profileId;
	
	private Long terminalCapabilitiesId;
	
	private Boolean manualEntryKey;
	
	private Boolean iCWithContacts;
	
	private Boolean magneticStripes;
	
	private Boolean encipheredPINForOnlineVerification;
	
	private Boolean plainTextPINForICCVerification;
	
	private Boolean encipheredPINForOfflineVerification;
	
	private Boolean noCVMRequired;
	
	private Boolean signature;
	
	private Boolean cda;
	
	private Boolean sda;
	
	private Boolean dda;
	
	private Boolean cardCapture;
	
	private Boolean cash;
	
	private Boolean transfer;
	
	private Boolean numericKeys;
	
	private Boolean functionKeys;
	
	private Boolean displayCardholder;
	
	private Boolean goods;
	
	private Boolean payment;
	
	private Boolean alphanumericSpecialCharacters;
	
	private Boolean printAttendant;
	
	private Boolean services;
	
	private Boolean administrative;
	
	private Boolean printCardholder;
	
	private Boolean inquiry;
	
	private Boolean cashDeposits;
	
	private Boolean commandKeys;
	
	private Boolean displayAttendant;
	

	/**
	 * @return the profileId
	 */
	public Long getProfileId() {
		return profileId;
	}

	/**
	 * @param profileId the profileId to set
	 */
	public void setProfileId(Long profileId) {
		this.profileId = profileId;
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
   * @param sda the sda to set
   */
  public void setSda(Boolean sda) {
    this.sda = sda;
  }

	/**
	 * @return the cda
	 */
	public Boolean getCda() {
		return cda;
	}
	
	/**
   * @return the dda
   */
  public Boolean getDda() {
    return dda;
  }

	/**
	 * @param cda the cda to set
	 */
	public void setCda(Boolean cda) {
		this.cda = cda;
	}
	
	/**
   * @param dda the dda to set
   */
  public void setDda(Boolean dda) {
    this.dda = dda;
  }

	/**
	 * @return the sda
	 */
	public Boolean getSda() {
		return sda;
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
	 * @return the cash
	 */
	public Boolean getCash() {
		return cash;
	}

	/**
	 * @return the transfer
	 */
	public Boolean getTransfer() {
		return transfer;
	}
	
	/**
   * @param cash the cash to set
   */
  public void setCash(Boolean cash) {
    this.cash = cash;
  }

	/**
	 * @param transfer the transfer to set
	 */
	public void setTransfer(Boolean transfer) {
		this.transfer = transfer;
	}
	
	/**
   * @return the functionKeys
   */
  public Boolean getFunctionKeys() {
    return functionKeys;
  }

	/**
	 * @return the numericKeys
	 */
	public Boolean getNumericKeys() {
		return numericKeys;
	}
	
	/**
   * @param functionKeys the functionKeys to set
   */
  public void setFunctionKeys(Boolean functionKeys) {
    this.functionKeys = functionKeys;
  }

	/**
	 * @param numericKeys the numericKeys to set
	 */
	public void setNumericKeys(Boolean numericKeys) {
		this.numericKeys = numericKeys;
	}

	/**
	 * @return the displayCardholder
	 */
	public Boolean getDisplayCardholder() {
		return displayCardholder;
	}
	
	/**
   * @return the goods
   */
  public Boolean getGoods() {
    return goods;
  }

	/**
	 * @param displayCardholder the displayCardholder to set
	 */
	public void setDisplayCardholder(Boolean displayCardholder) {
		this.displayCardholder = displayCardholder;
	}

	/**
	 * @return the payment
	 */
	public Boolean getPayment() {
		return payment;
	}
	
	/**
   * @param goods the goods to set
   */
  public void setGoods(Boolean goods) {
    this.goods = goods;
  }

	/**
	 * @param payment the payment to set
	 */
	public void setPayment(Boolean payment) {
		this.payment = payment;
	}
	
	/**
   * @return the printAttendant
   */
  public Boolean getPrintAttendant() {
    return printAttendant;
  }

	/**
	 * @return the alphanumericSpecialCharacters
	 */
	public Boolean getAlphanumericSpecialCharacters() {
		return alphanumericSpecialCharacters;
	}
	
	/**
   * @param printAttendant the printAttendant to set
   */
  public void setPrintAttendant(Boolean printAttendant) {
    this.printAttendant = printAttendant;
  }

	/**
	 * @param alphanumericSpecialCharacters the alphanumericSpecialCharacters to set
	 */
	public void setAlphanumericSpecialCharacters(
			Boolean alphanumericSpecialCharacters) {
		this.alphanumericSpecialCharacters = alphanumericSpecialCharacters;
	}

	/**
	 * @return the services
	 */
	public Boolean getServices() {
		return services;
	}
	
	/**
   * @return the administrative
   */
  public Boolean getAdministrative() {
    return administrative;
  }

	/**
	 * @param services the services to set
	 */
	public void setServices(Boolean services) {
		this.services = services;
	}

	/**
	 * @return the printCardholder
	 */
	public Boolean getPrintCardholder() {
		return printCardholder;
	}
	
	/**
   * @param administrative the administrative to set
   */
  public void setAdministrative(Boolean administrative) {
    this.administrative = administrative;
  }

	/**
	 * @param printCardholder the printCardholder to set
	 */
	public void setPrintCardholder(Boolean printCardholder) {
		this.printCardholder = printCardholder;
	}
	
	/**
   * @return the cashDeposits
   */
  public Boolean getCashDeposits() {
    return cashDeposits;
  }

	/**
	 * @return the inquiry
	 */
	public Boolean getInquiry() {
		return inquiry;
	}
	
	/**
   * @param cashDeposits the cashDeposits to set
   */
  public void setCashDeposits(Boolean cashDeposits) {
    this.cashDeposits = cashDeposits;
  }

	/**
	 * @param inquiry the inquiry to set
	 */
	public void setInquiry(Boolean inquiry) {
		this.inquiry = inquiry;
	}

	/**
	 * @return the commandKeys
	 */
	public Boolean getCommandKeys() {
		return commandKeys;
	}
	
	/**
   * @return the displayAttendant
   */
  public Boolean getDisplayAttendant() {
    return displayAttendant;
  }

	/**
	 * @param commandKeys the commandKeys to set
	 */
	public void setCommandKeys(Boolean commandKeys) {
		this.commandKeys = commandKeys;
	}

	/**
	 * @param displayAttendant the displayAttendant to set
	 */
	public void setDisplayAttendant(Boolean displayAttendant) {
		this.displayAttendant = displayAttendant;
	}
	
}
