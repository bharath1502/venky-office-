package com.chatak.pg.model;

import com.chatak.pg.bean.SearchRequest;
import com.chatak.pg.enums.TransactionType;

public class VirtualTerminalVoidDTO extends SearchRequest {
  
	 /**
   * 
   */
  private static final long serialVersionUID = 1L;

    private Integer cvv;
    
    private String cardNum;
		
		private String expDate;
		
		private String cardHolderName;
		
		private String merchantId;
		
		private Double subTotal;
		
		private String terminalId;
		
		private Double taxAmt;
		
		private Double shippingAmt;
		
		private Double tipAmount;
		
		private Double txnAmount;
		
		private String city;
		
		private String street;
		
		private String zip;
		
		private String authId;
		
		private String invoiceNumber;
		
		private String txnRefNumber;
		
		private String cgRefNumber;
		
		private String description;
		
		private Double feeAmount;
		
		private Boolean successDiv;
		
		private String cardNumMasked;
		
		private TransactionType transactionType;

		/**
		 * @return the txnRefNum
		 */
		public String getTxnRefNumber() {
			return txnRefNumber;
		}
		
		/**
     * @return the cardNum
     */
    public String getCardNum() {
      return cardNum;
    }

		/**
		 * @param txnRefNum the txnRefNum to set
		 */
		public void setTxnRefNumber(String txnRefNumber) {
			this.txnRefNumber = txnRefNumber;
		}

		/**
		 * @return the cvv
		 */
		public Integer getCvv() {
			return cvv;
		}
		
		/**
     * @param cardNum the cardNum to set
     */
    public void setCardNum(String cardNum) {
      this.cardNum = cardNum;
    }

		/**
		 * @param cvv the cvv to set
		 */
		public void setCvv(Integer cvv) {
			this.cvv = cvv;
		}
		
		/**
     * @param expDate the expDate to set
     */
    public void setExpDate(String expDate) {
      this.expDate = expDate;
    }

		/**
		 * @return the cardHolderName
		 */
		public String getCardHolderName() {
			return cardHolderName;
		}
		
		/**
     * @return the expDate
     */
    public String getExpDate() {
      return expDate;
    }

		/**
		 * @param cardHolderName the cardHolderName to set
		 */
		public void setCardHolderName(String cardHolderName) {
			this.cardHolderName = cardHolderName;
		}

		/**
		 * @return the merchantId
		 */
		public String getMerchantId() {
			return merchantId;
		}
		
		/**
     * @return the terminalId
     */
    public String getTerminalId() {
      return terminalId;
    }

		/**
		 * @param merchantId the merchantId to set
		 */
		public void setMerchantId(String merchantId) {
			this.merchantId = merchantId;
		}

		/**
		 * @return the subTotal
		 */
		public Double getSubTotal() {
			return subTotal;
		}
		
		/**
     * @param terminalId the terminalId to set
     */
    public void setTerminalId(String terminalId) {
      this.terminalId = terminalId;
    }

		/**
		 * @param subTotal the subTotal to set
		 */
		public void setSubTotal(Double subTotal) {
			this.subTotal = subTotal;
		}
		
		/**
     * @return the tipAmount
     */
    public Double getTipAmount() {
      return tipAmount;
    }

		/**
		 * @return the taxAmt
		 */
		public Double getTaxAmt() {
			return taxAmt;
		}
		
		/**
     * @param tipAmount the tipAmount to set
     */
    public void setTipAmount(Double tipAmount) {
      this.tipAmount = tipAmount;
    }

		/**
		 * @param taxAmt the taxAmt to set
		 */
		public void setTaxAmt(Double taxAmt) {
			this.taxAmt = taxAmt;
		}

		/**
		 * @return the shippingAmt
		 */
		public Double getShippingAmt() {
			return shippingAmt;
		}
		
		/**
     * @return the txnAmount
     */
    public Double getTxnAmount() {
      return txnAmount;
    }

		/**
		 * @param shippingAmt the shippingAmt to set
		 */
		public void setShippingAmt(Double shippingAmt) {
			this.shippingAmt = shippingAmt;
		}

		/**
		 * @return the street
		 */
		public String getStreet() {
			return street;
		}
		
		/**
     * @param txnAmount the txnAmount to set
     */
    public void setTxnAmount(Double txnAmount) {
      this.txnAmount = txnAmount;
    }

		/**
		 * @param street the street to set
		 */
		public void setStreet(String street) {
			this.street = street;
		}

		/**
		 * @return the zip
		 */
		public String getZip() {
			return zip;
		}
		
		/**
     * @return the city
     */
    public String getCity() {
      return city;
    }

		/**
		 * @param zip the zip to set
		 */
		public void setZip(String zip) {
			this.zip = zip;
		}
		
		/**
     * @param city the city to set
     */
    public void setCity(String city) {
      this.city = city;
    }

		/**
		 * @return the invoiceNumber
		 */
		public String getInvoiceNumber() {
			return invoiceNumber;
		}
		
		/**
     * @return the authId
     */
    public String getAuthId() {
      return authId;
    }

		/**
		 * @param invoiceNumber the invoiceNumber to set
		 */
		public void setInvoiceNumber(String invoiceNumber) {
			this.invoiceNumber = invoiceNumber;
		}

		/**
		 * @return the cgRefNumber
		 */
		public String getCgRefNumber() {
			return cgRefNumber;
		}
		
		/**
     * @param authId the authId to set
     */
    public void setAuthId(String authId) {
      this.authId = authId;
    }

		/**
		 * @param cgRefNumber the cgRefNumber to set
		 */
		public void setCgRefNumber(String cgRefNumber) {
			this.cgRefNumber = cgRefNumber;
		}
		
		/**
     * @return the feeAmount
     */
    public Double getFeeAmount() {
      return feeAmount;
    }

		/**
		 * @return the description
		 */
		public String getDescription() {
			return description;
		}
		
		/**
     * @param feeAmount the feeAmount to set
     */
    public void setFeeAmount(Double feeAmount) {
      this.feeAmount = feeAmount;
    }

		/**
		 * @param description the description to set
		 */
		public void setDescription(String description) {
			this.description = description;
		}

    public Boolean getSuccessDiv() {
      return successDiv;
    }

    public TransactionType getTransactionType() {
      return transactionType;
    }

    public String getCardNumMasked() {
      return cardNumMasked;
    }
    
    public void setSuccessDiv(Boolean successDiv) {
      this.successDiv = successDiv;
    }

    public void setCardNumMasked(String cardNumMasked) {
      this.cardNumMasked = cardNumMasked;
    }

    public void setTransactionType(TransactionType transactionType) {
      this.transactionType = transactionType;
    }


}
