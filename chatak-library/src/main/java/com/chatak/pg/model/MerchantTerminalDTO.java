package com.chatak.pg.model;

import java.io.Serializable;

public class MerchantTerminalDTO implements Serializable {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1988745697929089785L;

	private Long merchantTerminalId;

	private String merchantId;

	private String terminalId;
	
	private String productId;

	private Long price;

	private String comments;
	
	private String description;

	private String status;

	/**
	 * @return the merchantTerminalId
	 */
	public Long getMerchantTerminalId() {
		return merchantTerminalId;
	}

	/**
	 * @param merchantTerminalId the merchantTerminalId to set
	 */
	public void setMerchantTerminalId(Long merchantTerminalId) {
		this.merchantTerminalId = merchantTerminalId;
	}

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
   * @return the status
   */
  public String getStatus() {
    return status;
  }

	/**
	 * @return the terminalId
	 */
	public String getTerminalId() {
		return terminalId;
	}
	
	/**
   * @param status the status to set
   */
  public void setStatus(String status) {
    this.status = status;
  }

	/**
	 * @param terminalId the terminalId to set
	 */
	public void setTerminalId(String terminalId) {
		this.terminalId = terminalId;
	}

	/**
	 * @return the productId
	 */
	public String getProductId() {
		return productId;
	}
	
	/**
   * @return the price
   */
  public Long getPrice() {
    return price;
  }
  
  /**
   * @return the comments
   */
  public String getComments() {
    return comments;
  }

	/**
	 * @param productId the productId to set
	 */
	public void setProductId(String productId) {
		this.productId = productId;
	}

	/**
   * @return the description
   */
  public String getDescription() {
    return description;
  }

	/**
	 * @param price the price to set
	 */
	public void setPrice(Long price) {
		this.price = price;
	}

	/**
	 * @param comments the comments to set
	 */
	public void setComments(String comments) {
		this.comments = comments;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

}
