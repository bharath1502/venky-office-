package com.chatak.pg.acq.dao.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "PG_MERCHANT_CONFIG")
public class PGMerchantConfig {
	
	  @Id
	  /*@SequenceGenerator(name = "SEQ_PG_MERCHANT_CONFIG_ID", sequenceName = "SEQ_PG_MERCHANT_CONFIG")
	  @GeneratedValue(generator = "SEQ_PG_MERCHANT_CONFIG_ID")*/
	  @GeneratedValue(strategy = GenerationType.AUTO)
	  @Column(name = "MER_CONFIG_ID")
	  private Long id;
	  
	  @Column(name = "FEE_PROGRAM")
	  private String feeProgram;
	  
	  @Column(name = "PROCESSOR")
	  private String processor;
	  
	  @Column(name = "REFUND")
	  private Integer refunds;
	  
	  @Column(name = "TIP_AMOUNT")
	  private Integer tipAmount;
	  
	  @Column(name = "TAX_AMOUNT")
	  private Integer taxAmount;
	  
	  @Column(name = "SHIPPING_AMT")
	  private Integer shippingAmount;
	  
	  @Column(name = "CREATED_DATE")
	  private Timestamp createdDate;

	  @Column(name = "UPDATED_DATE")
	  private Timestamp updatedDate;
	  
	  @Column(name = "AUTO_SETTLEMENT")
	  private Integer autoSettlement;
	  
	  @Column(name = "VIRTUAL_TERMINAL")
	  private Integer virtualTerminal;
	  
	  @Column(name = "POS_TERMINAL")
	  private Integer posTerminal;
	  
	  @Column(name = "ONLINE_TERMINAL")
	  private Integer online;
	  
	  @Column(name = "WEBSITE_ADDRESS")
	  private String webSiteAddress;
	  
	  @Column(name = "RETURN_URL")
	  private String returnUrl;
	  
	  @Column(name = "CANCEL_URL")	
	  private String cancelUrl;
	  
	  @Column(name = "PAY_PAGE_CONFIG")	
	  private Integer payPageConfig;
	  
	  @Column(name = "PAYOUT_AT")
	  private String payOutAt;
	  
	  //NEW ADDED COLUMNS
	  
	/**
	 * @param processor the processor to set
	 */
	public void setProcessor(String processor) {
		this.processor = processor;
	}
	
	/**
	 * @return the shippingAmount
	 */
	public Integer getShippingAmount() {
		return shippingAmount;
	}
	
	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @return the refunds
	 */
	public Integer getRefunds() {
		return refunds;
	}
	
	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}


	/**
	 * @param refunds the refunds to set
	 */
	public void setRefunds(Integer refunds) {
		this.refunds = refunds;
	}
	
	/**
	 * @return the tipAmount
	 */
	public Integer getTipAmount() {
		return tipAmount;
	}
	
	/**
	 * @return the feeProgram
	 */
	public String getFeeProgram() {
		return feeProgram;
	}

	/**
	 * @param tipAmount the tipAmount to set
	 */
	public void setTipAmount(Integer tipAmount) {
		this.tipAmount = tipAmount;
	}
	
	/**
	 * @param feeProgram the feeProgram to set
	 */
	public void setFeeProgram(String feeProgram) {
		this.feeProgram = feeProgram;
	}

	/**
	 * @return the taxAmount
	 */
	public Integer getTaxAmount() {
		return taxAmount;
	}
	
	/**
	 * @return the processor
	 */
	public String getProcessor() {
		return processor;
	}

	/**
	 * @param taxAmount the taxAmount to set
	 */
	public void setTaxAmount(Integer taxAmount) {
		this.taxAmount = taxAmount;
	}

	/**
	 * @param shippingAmount the shippingAmount to set
	 */
	public void setShippingAmount(Integer shippingAmount) {
		this.shippingAmount = shippingAmount;
	}

	/**
	 * @return the createdDate
	 */
	public Timestamp getCreatedDate() {
		return createdDate;
	}

	/**
	 * @param createdDate the createdDate to set
	 */
	public void setCreatedDate(Timestamp createdDate) {
		this.createdDate = createdDate;
	}

	/**
	 * @return the updatedDate
	 */
	public Timestamp getUpdatedDate() {
		return updatedDate;
	}

	/**
	 * @param updatedDate the updatedDate to set
	 */
	public void setUpdatedDate(Timestamp updatedDate) {
		this.updatedDate = updatedDate;
	}

	/**
	 * @return the virtualTerminal
	 */
	public Integer getVirtualTerminal() {
		return virtualTerminal;
	}
	
	/**
	 * @return the autoSettlement
	 */
	public Integer getAutoSettlement() {
		return autoSettlement;
	}

	/**
	 * @param virtualTerminal the virtualTerminal to set
	 */
	public void setVirtualTerminal(Integer virtualTerminal) {
		this.virtualTerminal = virtualTerminal;
	}

	/**
	 * @return the posTerminal
	 */
	public Integer getPosTerminal() {
		return posTerminal;
	}
	
	/**
	 * @param autoSettlement the autoSettlement to set
	 */
	public void setAutoSettlement(Integer autoSettlement) {
		this.autoSettlement = autoSettlement;
	}

	/**
	 * @param posTerminal the posTerminal to set
	 */
	public void setPosTerminal(Integer posTerminal) {
		this.posTerminal = posTerminal;
	}
	
	public void setPayOutAt(String payOutAt) {
		this.payOutAt = payOutAt;
	}

	/**
	 * @return the online
	 */
	public Integer getOnline() {
		return online;
	}

	/**
	 * @param online the online to set
	 */
	public void setOnline(Integer online) {
		this.online = online;
	}
	
	public String getPayOutAt() {
		return payOutAt;
	}

	/**
	 * @return the webSiteAddress
	 */
	public String getWebSiteAddress() {
		return webSiteAddress;
	}

	/**
	 * @param webSiteAddress the webSiteAddress to set
	 */
	public void setWebSiteAddress(String webSiteAddress) {
		this.webSiteAddress = webSiteAddress;
	}
	
	public void setPayPageConfig(Integer payPageConfig) {
		this.payPageConfig = payPageConfig;
	}

	/**
	 * @return the returnUrl
	 */
	public String getReturnUrl() {
		return returnUrl;
	}

	/**
	 * @param returnUrl the returnUrl to set
	 */
	public void setReturnUrl(String returnUrl) {
		this.returnUrl = returnUrl;
	}
	
	public Integer getPayPageConfig() {
		return payPageConfig;
	}

	/**
	 * @return the cancelUrl
	 */
	public String getCancelUrl() {
		return cancelUrl;
	}

	/**
	 * @param cancelUrl the cancelUrl to set
	 */
	public void setCancelUrl(String cancelUrl) {
		this.cancelUrl = cancelUrl;
	}
	
}
