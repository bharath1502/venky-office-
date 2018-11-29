package com.chatak.pay.controller.model.topup;

import java.util.List;

import com.chatak.pay.controller.model.Response;

public class GetOperatorsResponse extends Response {

	private static final long serialVersionUID = 1L;

	private List<OperatorDTO> operators;

	/**
	 * @return the operators
	 */
	public List<OperatorDTO> getOperators() {
		return operators;
	}

	/**
	 * @param operators
	 *            the operators to set
	 */
	public void setOperators(List<OperatorDTO> operators) {
		this.operators = operators;
	}
}
