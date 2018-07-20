/**
 * 
 */
package com.chatak.pg.acq.dao.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import com.chatak.pg.acq.dao.PaymentSchemeDao;
import com.chatak.pg.acq.dao.model.PGPaymentScheme;
import com.chatak.pg.acq.dao.model.QPGPaymentScheme;
import com.chatak.pg.acq.dao.repository.PaymentSchemeRepository;
import com.chatak.pg.bean.Response;
import com.chatak.pg.constants.ActionErrorCode;
import com.chatak.pg.constants.PGConstants;
import com.chatak.pg.user.bean.PaymentSchemeRequest;
import com.chatak.pg.user.bean.PaymentSchemeResponse;
import com.chatak.pg.util.Constants;
import com.chatak.pg.util.DateUtil;
import com.chatak.pg.util.StringUtils;
import com.mysema.query.Tuple;
import com.mysema.query.jpa.impl.JPAQuery;
import com.mysema.query.types.OrderSpecifier;
import com.mysema.query.types.expr.BooleanExpression;

/**
 * @Author: Girmiti Software
 * @Date: Aug 6, 2016
 * @Time: 10:57:08 AM
 * @Version: 1.0
 * @Comments:
 *
 */
@Repository("paymentSchemeDao")
public class PaymentSchemeDaoImpl implements PaymentSchemeDao {

	private static Logger logger = Logger.getLogger(PaymentSchemeDaoImpl.class);

	/**
	 * @param getPaymentSchemeId
	 * @return
	 */
	@Autowired
	private PaymentSchemeRepository paymentSchemeRepository;

	@PersistenceContext
	EntityManager entityManager;

	@Override
	public PGPaymentScheme getPaymentSchemeInfoId(Long id) {
		PGPaymentScheme pgPaymentScheme = paymentSchemeRepository.findById(id);
		if (null != pgPaymentScheme) {
			return pgPaymentScheme;
		}
		return null;
	}

	/**
	 * @param updatePaymentSchemeRequest
	 * @param userid
	 * @return
	 */
	@Override
	public PaymentSchemeResponse updatePaymentSchemeInformation(PaymentSchemeRequest updatePaymentSchemeRequest,
			String userid) {
		logger.info("PaymentSchemeDaoImpl | updatePaymentScheme | Entering");
		String EmailID = updatePaymentSchemeRequest.getContactEmail().toLowerCase();
		PaymentSchemeResponse updatePaymentSchemeResponse = new PaymentSchemeResponse();
		List<PGPaymentScheme> pgPaymentScheme = paymentSchemeRepository.findByContactEmailOrderByUpdatedDateDesc(EmailID);
		try {
			if (StringUtils.isListNotNullNEmpty(pgPaymentScheme) &&
			    !pgPaymentScheme.get(0).getId().equals(updatePaymentSchemeRequest.getId())) {

				  updatePaymentSchemeResponse.setErrorCode(ActionErrorCode.ERROR_CODE_Z5);
				  updatePaymentSchemeResponse.setErrorMessage(PGConstants.DUPLICATE_PAYMENT_SCHEME_EMAIL_ID);
				  return updatePaymentSchemeResponse;
			}
			PGPaymentScheme pgPaymentScheme2 = paymentSchemeRepository.findById(updatePaymentSchemeRequest.getId());

			if (pgPaymentScheme2 != null) {
				pgPaymentScheme2.setId(updatePaymentSchemeRequest.getId());
				pgPaymentScheme2.setPaymentSchemeName(updatePaymentSchemeRequest.getPaymentSchemeName());
				pgPaymentScheme2.setContactName(updatePaymentSchemeRequest.getContactName());
				pgPaymentScheme2.setContactEmail(EmailID);
				pgPaymentScheme2.setContactPhone(updatePaymentSchemeRequest.getContactPhone());
				pgPaymentScheme2.setRid(updatePaymentSchemeRequest.getRid());
				pgPaymentScheme2.setStatus(updatePaymentSchemeRequest.getStatus());
				pgPaymentScheme2.setTypeOfCard(updatePaymentSchemeRequest.getTypeOfCard());
				pgPaymentScheme2.setUpdatedBy(userid);
				pgPaymentScheme2.setUpdatedDate(DateUtil.getCurrentTimestamp());
				paymentSchemeRepository.save(pgPaymentScheme2);
				updatePaymentSchemeResponse.setErrorCode(ActionErrorCode.ERROR_CODE_00);
			} else {
				updatePaymentSchemeResponse.setErrorCode(ActionErrorCode.ERROR_CODE_01);
			}
		} catch (Exception exception) {
			logger.error("PaymentSchemeDaoImpl | updatePaymentScheme | Exception " + exception);
		}
		logger.info("PaymentSchemeDaoImpl | updatePaymentScheme | Exiting");

		return updatePaymentSchemeResponse;
	}

	/**
	 * @param addPaymentSchemeRequest
	 * @param userid
	 * @return
	 */
	@Override
	public PaymentSchemeResponse addPaymentSchemeInformation(PaymentSchemeRequest addPaymentSchemeRequest,
			String userid) {
		logger.info("PaymentSchemeDaoImpl | addPaymentSchemeInformation | Entering");
		PaymentSchemeResponse addPaymentSchemeResponse = new PaymentSchemeResponse();
		String EmailID = addPaymentSchemeRequest.getContactEmail().toLowerCase();
		try {

				List<PGPaymentScheme> pgPaymentScheme1 = paymentSchemeRepository.findByContactEmailOrderByUpdatedDateDesc(EmailID);

				if (StringUtils.isListNotNullNEmpty(pgPaymentScheme1) 
						&& pgPaymentScheme1.get(0).getId() != addPaymentSchemeRequest.getId()) {

					addPaymentSchemeResponse.setErrorCode(ActionErrorCode.ERROR_CODE_Z5);
					addPaymentSchemeResponse.setErrorMessage(PGConstants.DUPLICATE_PAYMENT_SCHEME_EMAIL_ID);
					return addPaymentSchemeResponse;
				}
				Timestamp currentDate = new Timestamp(System.currentTimeMillis());

				PGPaymentScheme pgPaymentScheme = new PGPaymentScheme();
				pgPaymentScheme.setCreatedDate(currentDate);
				pgPaymentScheme.setUpdatedDate(currentDate);
				pgPaymentScheme.setPaymentSchemeName(addPaymentSchemeRequest.getPaymentSchemeName());
				pgPaymentScheme.setContactName(addPaymentSchemeRequest.getContactName());
				pgPaymentScheme.setContactEmail(EmailID);
				pgPaymentScheme.setContactPhone(addPaymentSchemeRequest.getContactPhone());
				pgPaymentScheme.setRid(addPaymentSchemeRequest.getRid());
				pgPaymentScheme.setTypeOfCard(addPaymentSchemeRequest.getTypeOfCard());
				pgPaymentScheme.setStatus(addPaymentSchemeRequest.getStatus());
				pgPaymentScheme.setCreatedBy(userid);
				pgPaymentScheme.setUpdatedBy(userid);
			
					paymentSchemeRepository.save(pgPaymentScheme);
					addPaymentSchemeResponse.setErrorCode(ActionErrorCode.ERROR_CODE_00);
					addPaymentSchemeResponse.setErrorMessage(ActionErrorCode.getInstance().getMessage(ActionErrorCode.ERROR_CODE_00));
		} catch (Exception e) {
			logger.error("PaymentSchemeDaoImpl | addPaymentSchemeInformation | Exception" + e);
			addPaymentSchemeResponse.setErrorCode(ActionErrorCode.ERROR_CODE_Z5);
			addPaymentSchemeResponse
					.setErrorMessage(ActionErrorCode.getInstance().getMessage(ActionErrorCode.ERROR_CODE_Z5));
			addPaymentSchemeResponse.setErrorMessage(PGConstants.DUPLICATE_PAYMENT_SCHEME_EMAIL_ID);
		}
		logger.info("PaymentSchemeDaoImpl | addPaymentSchemeInformation | Exiting");
		return addPaymentSchemeResponse;
	}

	@Override
	public List<PaymentSchemeRequest> findPaymentScheme(PaymentSchemeRequest paymentScheme) throws DataAccessException {
		logger.info("PaymentSchemeDaoImpl | findPaymentScheme | Entering");

		Integer pageIndex = paymentScheme.getPageIndex();
		Integer pageSize = paymentScheme.getPageSize();
		Integer totalRecords;
	    
		int offset = 0;
		int limit = 0;
		
		if(pageIndex == null || pageIndex == 1){
			totalRecords = getTotalNumberOfRecords(paymentScheme);
			paymentScheme.setNoOfRecords(totalRecords);
		}

		if (pageIndex == null && pageSize == null) {
			offset = 0;
			limit = Constants.DEFAULT_PAGE_SIZE;
		} else {
			offset = (pageIndex - 1) * pageSize;
			limit = pageSize;
		}
		List<PaymentSchemeRequest> searchList = null;

		JPAQuery query = new JPAQuery(entityManager);

		List<Tuple> tupleList = query.from(QPGPaymentScheme.pGPaymentScheme)
				.where(isPaymentSchemeName(paymentScheme.getPaymentSchemeName()),
						isTypeOfCard(paymentScheme.getTypeOfCard()), isRID(paymentScheme.getRid()),
						isStatus(paymentScheme.getStatus()))
				.offset(offset)
				.limit(limit)
				.orderBy(orderByCreatedDateDesc()).list(QPGPaymentScheme.pGPaymentScheme.paymentSchemeName,
						QPGPaymentScheme.pGPaymentScheme.rid, QPGPaymentScheme.pGPaymentScheme.typeOfCard,
						QPGPaymentScheme.pGPaymentScheme.status, QPGPaymentScheme.pGPaymentScheme.contactEmail,
						QPGPaymentScheme.pGPaymentScheme.contactName, QPGPaymentScheme.pGPaymentScheme.contactPhone,
						QPGPaymentScheme.pGPaymentScheme.id);

		if (!CollectionUtils.isEmpty(tupleList)) {
			searchList = new ArrayList<PaymentSchemeRequest>();
			PaymentSchemeRequest paymentScheme1 = null;
			for (Tuple tuple : tupleList) {
				paymentScheme1 = new PaymentSchemeRequest();
				paymentScheme1.setPaymentSchemeName(tuple.get(QPGPaymentScheme.pGPaymentScheme.paymentSchemeName));
				paymentScheme1.setRid(tuple.get(QPGPaymentScheme.pGPaymentScheme.rid));
				paymentScheme1.setTypeOfCard(tuple.get(QPGPaymentScheme.pGPaymentScheme.typeOfCard));
				paymentScheme1.setStatus(tuple.get(QPGPaymentScheme.pGPaymentScheme.status));
				paymentScheme1.setContactEmail(tuple.get(QPGPaymentScheme.pGPaymentScheme.contactEmail));
				paymentScheme1.setContactName(tuple.get(QPGPaymentScheme.pGPaymentScheme.contactName));
				paymentScheme1.setContactPhone(tuple.get(QPGPaymentScheme.pGPaymentScheme.contactPhone));
				paymentScheme1.setId(tuple.get(QPGPaymentScheme.pGPaymentScheme.id));
				if (paymentScheme1.getStatus().equals("0")) {
					paymentScheme1.setStatus(PGConstants.STATUS_ACTIVE);
				} else if (paymentScheme1.getStatus().equals("1")) {
					paymentScheme1.setStatus(PGConstants.STATUS_PENDING);
				} else if (paymentScheme1.getStatus().equals("2")) {
					paymentScheme1.setStatus(PGConstants.STATUS_SUSPENDED);
				}
				searchList.add(paymentScheme1);
			}
		}
		return searchList;
	}
	
	@Override
	public Response getUserByEmailId(String contactEmail) {
		String emailId=contactEmail.toLowerCase();
	    PGPaymentScheme paymentScheme = paymentSchemeRepository.findByContactEmail(emailId);
	    Response response = new Response();
	    if(paymentScheme != null) {
	      response.setErrorCode(ActionErrorCode.ERROR_CODE_DUPLICATE_ENTRY);
	      response.setErrorMessage(ActionErrorCode.getInstance().getMessage(ActionErrorCode.ERROR_CODE_DUPLICATE_ENTRY));
	      return response;
	    }
	    else {
	      response.setErrorCode(ActionErrorCode.ERROR_CODE_00);
	      response.setErrorMessage(ActionErrorCode.getInstance().getMessage(ActionErrorCode.ERROR_CODE_00));
	      return response;
	    }
	  }

	public int getTotalNumberOfRecords(PaymentSchemeRequest paymentScheme) {
		JPAQuery query = new JPAQuery(entityManager);

		List<Tuple> tupleList = query.from(QPGPaymentScheme.pGPaymentScheme)
				.where(isPaymentSchemeName(paymentScheme.getPaymentSchemeName()),
						isTypeOfCard(paymentScheme.getTypeOfCard()), isRID(paymentScheme.getRid()),
						isStatus(paymentScheme.getStatus()))
				.orderBy(orderByCreatedDateDesc()).list(QPGPaymentScheme.pGPaymentScheme.paymentSchemeName,
						QPGPaymentScheme.pGPaymentScheme.rid, QPGPaymentScheme.pGPaymentScheme.typeOfCard,
						QPGPaymentScheme.pGPaymentScheme.status, QPGPaymentScheme.pGPaymentScheme.contactEmail,
						QPGPaymentScheme.pGPaymentScheme.contactName, QPGPaymentScheme.pGPaymentScheme.contactPhone,
						QPGPaymentScheme.pGPaymentScheme.id);
	    return (StringUtils.isListNotNullNEmpty(tupleList) ? tupleList.size() : 0);
	  }

	
	@Override
	public PGPaymentScheme findPaymentSchemeById(Long paymentSchemeId) {
		PGPaymentScheme pgPaymentScheme = paymentSchemeRepository.findById(paymentSchemeId);
		return pgPaymentScheme;
	}
	
	@Override
	public PGPaymentScheme createOrUpdatePaymentScheme(PGPaymentScheme pgPaymentScheme) throws DataAccessException {
	    return paymentSchemeRepository.save(pgPaymentScheme);
	}


	/**
	 * @return
	 **/
	private OrderSpecifier<Timestamp> orderByCreatedDateDesc() {
		
		return QPGPaymentScheme.pGPaymentScheme.createdDate.desc();
	}

	private BooleanExpression isPaymentSchemeName(String paymentSchemeName) {
		return (null != paymentSchemeName && !"".equals(paymentSchemeName))
				? QPGPaymentScheme.pGPaymentScheme.paymentSchemeName.toUpperCase()
						.like("%" + paymentSchemeName.toUpperCase().replace("*", "") + "%") : null;
	}

	private BooleanExpression isTypeOfCard(String typeOfCard) {
		return (null != typeOfCard && !"".equals(typeOfCard))
				? QPGPaymentScheme.pGPaymentScheme.typeOfCard.eq(typeOfCard) : null;
	}

	private BooleanExpression isRID(String rid) {
		return (null != rid && !"".equals(rid)) ? QPGPaymentScheme.pGPaymentScheme.rid.toUpperCase()
				.like("%" + rid.toUpperCase().replace("*", "") + "%") : null;
	}

	private BooleanExpression isStatus(Integer status) {

		return null != status ? QPGPaymentScheme.pGPaymentScheme.status.eq(status) : null;
	}

	/**
	 * @param paymentSchemeName
	 * @return
	 */
	@Override
	public PGPaymentScheme getPaymentSchemeName(String paymentSchemeName) {
		return paymentSchemeRepository.findByPaymentSchemeName(paymentSchemeName);
	}

}
