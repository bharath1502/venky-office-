/**
 * 
 */
package com.chatak.pg.user.bean;

import java.util.List;

import com.chatak.pg.bean.Response;

/**
 * @Author: Girmiti Software
 * @Date: Aug 6, 2016
 * @Time: 11:57:06 AM
 * @Version: 1.0
 * @Comments:
 *
 */
public class PaymentSchemeResponse extends Response{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7923458779984919981L;

	private List<PaymentSchemeRequest> PaymentSchemesRequest;
	
	public List<PaymentSchemeRequest> getPaymentSchemesRequest() {
		return PaymentSchemesRequest;
	}

	public void setPaymentSchemesRequest(List<PaymentSchemeRequest> paymentSchemesRequest) {
		PaymentSchemesRequest = paymentSchemesRequest;
	}

}
