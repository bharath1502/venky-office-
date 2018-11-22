/**
 * 
 */
package com.chatak.pg.acq.dao;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.chatak.pg.acq.dao.model.PGPaymentScheme;
import com.chatak.pg.bean.Response;
import com.chatak.pg.user.bean.PaymentSchemeRequest;
import com.chatak.pg.user.bean.PaymentSchemeResponse;

/**
 * @Author: Girmiti Software
 * @Date: Aug 5, 2016
 * @Time: 8:04:46 PM
 * @Version: 1.0
 * @Comments: 
 *
 **/
public interface PaymentSchemeDao {
	
	public PGPaymentScheme getPaymentSchemeInfoId(Long id);
	
	public PaymentSchemeResponse updatePaymentSchemeInformation(PaymentSchemeRequest updatePaymentSchemeRequest, String userid);
	
	public PaymentSchemeResponse addPaymentSchemeInformation(PaymentSchemeRequest addPaymentSchemeRequest, String userid);
	
	public List<PaymentSchemeRequest> findPaymentScheme(PaymentSchemeRequest paymentScheme) throws DataAccessException;
	
	public Response getUserByEmailId(String contactEmail);
	
	public PGPaymentScheme findPaymentSchemeById(Long paymentSchemeId);
	
	public PGPaymentScheme createOrUpdatePaymentScheme(PGPaymentScheme pgPaymentScheme) throws DataAccessException;
	
	public PGPaymentScheme getPaymentSchemeName(String  paymentSchemeName);

}
