package com.chatak.pay.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.codehaus.jackson.map.annotate.JsonSerialize;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class TransactionDTOResponse  extends com.chatak.pay.controller.model.Response {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6365669293751717524L;
	private List<TransactionDTO> transactionDTO;


	/**
	 * @return the transactionDTO
	 */
	public List<TransactionDTO> getTransactionDTO() {
		return transactionDTO;
	}


	/**
	 * @param transactionDTO the transactionDTO to set
	 */
	public void setTransactionDTO(List<TransactionDTO> transactionDTO) {
		this.transactionDTO = transactionDTO;
	}
	
	
}
