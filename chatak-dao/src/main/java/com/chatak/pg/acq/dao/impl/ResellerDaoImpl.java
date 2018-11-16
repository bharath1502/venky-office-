package com.chatak.pg.acq.dao.impl;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import com.chatak.pg.acq.dao.ResellerDao;
import com.chatak.pg.acq.dao.model.PGReseller;
import com.chatak.pg.acq.dao.model.QPGReseller;
import com.chatak.pg.acq.dao.repository.ResellerRepository;
import com.chatak.pg.constants.ActionErrorCode;
import com.chatak.pg.constants.PGConstants;
import com.chatak.pg.user.bean.AddResellerRequest;
import com.chatak.pg.user.bean.AddResellerResponse;
import com.chatak.pg.user.bean.DeleteResellerResponse;
import com.chatak.pg.user.bean.GetResellerListRequest;
import com.chatak.pg.user.bean.GetResellerListResponse;
import com.chatak.pg.user.bean.UpdateResellerRequest;
import com.chatak.pg.user.bean.UpdateResellerResponse;
import com.chatak.pg.util.CommonUtil;
import com.chatak.pg.util.Constants;
import com.chatak.pg.util.DateUtil;
import com.chatak.pg.util.StringUtils;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;

/**
 * Class provides implementation of API's required for Merchandise application,
 * basically to insert, update, delete and fetch the list of Merchant, Store and
 * Terminal related data.
 * 
 * @author Girmiti Software
 * @date 01-Jan-2015 4:14:47 PM
 * @version 1.0
 */
@SuppressWarnings("unchecked")
@Repository("resellerDao")
public class ResellerDaoImpl implements ResellerDao, PGConstants {

  private static Logger logger = Logger.getLogger(ResellerDaoImpl.class);

  @Autowired
  private ResellerRepository resellerRepository;

  @PersistenceContext
  private EntityManager entityManager;

  /**
   * @param addResellerRequest
   * @return
   */
  @Override
  public AddResellerResponse addReseller(AddResellerRequest addResellerRequest) {

    logger.info("Entering ResellerDaoImpl : : addReseller Method");
    AddResellerResponse addResellerResponse = new AddResellerResponse();

    if (null != addResellerRequest) {

      return addSellerExistingResellerData(addResellerRequest, addResellerResponse);
    } else {
      return null;
    }
  }

  private AddResellerResponse addSellerExistingResellerData(AddResellerRequest addResellerRequest,
      AddResellerResponse addResellerResponse) {
    List<PGReseller> pgResellerList =
        resellerRepository.findByEmailIdOrResellerNameOrderByCreatedDateDesc(
            addResellerRequest.getEmailId(), addResellerRequest.getResellerName());

    if ((StringUtils.isListNotNullNEmpty(pgResellerList))
        && pgResellerList.get(0).getStatus() != PGConstants.STATUS_DELETED) {

      pgResellerList =
          resellerRepository.findByEmailIdOrderByUpdatedDateDesc(addResellerRequest.getEmailId());

      if ((StringUtils.isListNotNullNEmpty(pgResellerList))
          && pgResellerList.get(0).getStatus() != PGConstants.STATUS_DELETED) {
        addResellerResponse.setErrorCode(ActionErrorCode.ERROR_CODE_Z5);
        addResellerResponse.setErrorMessage(PGConstants.DUPLICATE_RESELLER_EMAIL_ID);
        return addResellerResponse;
      }

      pgResellerList = resellerRepository
          .findByResellerNameOrderByUpdatedDateDesc(addResellerRequest.getResellerName());
      if ((StringUtils.isListNotNullNEmpty(pgResellerList))
          && pgResellerList.get(0).getStatus() != PGConstants.STATUS_DELETED) {
        addResellerResponse.setErrorCode(ActionErrorCode.ERROR_CODE_Z10);
        addResellerResponse.setErrorMessage(
            ActionErrorCode.getInstance().getMessage(ActionErrorCode.ERROR_CODE_Z10));
        return addResellerResponse;
      }
    }

    Timestamp currentDate = new Timestamp(System.currentTimeMillis());

    PGReseller resellerData = new PGReseller();
    resellerData.setAddress1(CommonUtil.isNullAndEmpty(addResellerRequest.getAddress1()) ? null
        : addResellerRequest.getAddress1());
    resellerData.setAddress2(CommonUtil.isNullAndEmpty(addResellerRequest.getAddress2()) ? null
        : addResellerRequest.getAddress2());
    resellerData.setCity(CommonUtil.isNullAndEmpty(addResellerRequest.getCity()) ? null
        : addResellerRequest.getCity());
    resellerData.setCountry(addResellerRequest.getCountry());
    resellerData.setCreatedDate(currentDate);
    resellerData.setPhone(addResellerRequest.getPhone());
    resellerData.setResellerName(addResellerRequest.getResellerName());
    resellerData.setState(addResellerRequest.getState());
    resellerData.setEmailId(addResellerRequest.getEmailId());
    resellerData.setContactName(addResellerRequest.getContactName());
    resellerData.setUpdatedDate(currentDate);
    resellerData.setCreatedBy(addResellerRequest.getCreatedBy());
    resellerData.setUpdatedBy(addResellerRequest.getCreatedBy());
    resellerData.setStatus(addResellerRequest.getStatus());
    resellerData.setZip(addResellerRequest.getZip());
    resellerData.setAccountBalance(PGConstants.ZERO);
    resellerData.setAccountNumber(addResellerRequest.getAccountNumber());

      resellerRepository.save(resellerData);
      addResellerResponse.setErrorCode(ActionErrorCode.ERROR_CODE_00);
      addResellerResponse.setErrorMessage(
          ActionErrorCode.getInstance().getMessage(ActionErrorCode.ERROR_CODE_00));
    logger.info("Exiting ResellerDaoImpl : : addReseller Method");
    return addResellerResponse;
  }

  /**
   * @return
   * @throws
   */
  @Override
  public String getResllerAccountNumber() {
    List<BigDecimal> list =
        entityManager.createNativeQuery("select SEQ_PG_RESELLER_ACCOUNTNUMBER.nextval from dual")
            .getResultList();
    return list.get(0).toString();

  }

  /**
   * @param serachReseller
   * @return
   */
  @Override
  public GetResellerListResponse getResellerlist(GetResellerListRequest serachReseller) {

    GetResellerListResponse getResellerListResponse = new GetResellerListResponse();
    List<PGReseller> resellerList = null;
    try {
      int offset = 0;
      int limit = 0;
      Integer totalRecords = serachReseller.getNoOfRecords();

      if (serachReseller.getPageIndex() == null || serachReseller.getPageIndex() == 1) {
        totalRecords = getTotalNumberOfResellerRecords(serachReseller);
        serachReseller.setNoOfRecords(totalRecords);
      }
      getResellerListResponse.setNoOfRecords(totalRecords);
      if (serachReseller.getPageIndex() == null && serachReseller.getPageSize() == null) {
        offset = 0;
        limit = Constants.DEFAULT_PAGE_SIZE;
      } else {
        offset = (serachReseller.getPageIndex() - 1) * serachReseller.getPageSize();
        limit = serachReseller.getPageSize();
      }
      JPAQuery<Tuple> query = new JPAQuery<Tuple>(entityManager);
      List<Tuple> tupleList = query.from(QPGReseller.pGReseller)
    	  .select(QPGReseller.pGReseller.resellerName, QPGReseller.pGReseller.contactName,
              QPGReseller.pGReseller.emailId, QPGReseller.pGReseller.phone,
              QPGReseller.pGReseller.accountNumber, QPGReseller.pGReseller.accountBalance,
              QPGReseller.pGReseller.city, QPGReseller.pGReseller.country,
              QPGReseller.pGReseller.status, QPGReseller.pGReseller.resellerId)
          .where(isResellerNameLike(serachReseller.getResellerName()),
              isCityLike(serachReseller.getCity()), isCountryEq(serachReseller.getCountry()),
              isEmailLike(serachReseller.getEmailId()),
              isAccountNumberEq(serachReseller.getAccountNumber()),
              isContactNameLike(serachReseller.getContactName()),
              isStatusEq(serachReseller.getStatus()), isPhoneLike(serachReseller.getPhone()))
          .offset(offset).limit(limit).orderBy(orderByCreatedDateDesc())
          .fetch();
      if (!CollectionUtils.isEmpty(tupleList)) {
        resellerList = new ArrayList<>();
        PGReseller reseller = null;
        for (Tuple tuple : tupleList) {

          reseller = new PGReseller();

          reseller.setResellerId(tuple.get(QPGReseller.pGReseller.resellerId));
          reseller.setResellerName(tuple.get(QPGReseller.pGReseller.resellerName));
          reseller.setContactName(tuple.get(QPGReseller.pGReseller.contactName));
          reseller.setEmailId(tuple.get(QPGReseller.pGReseller.emailId));
          reseller.setPhone(tuple.get(QPGReseller.pGReseller.phone));
          reseller.setAccountNumber(tuple.get(QPGReseller.pGReseller.accountNumber));
          reseller.setCity(tuple.get(QPGReseller.pGReseller.city));
          reseller.setCountry(tuple.get(QPGReseller.pGReseller.country));
          reseller.setStatus(tuple.get(QPGReseller.pGReseller.status));
          reseller.setAccountBalance(tuple.get(QPGReseller.pGReseller.accountBalance));
          reseller.setState(tuple.get(QPGReseller.pGReseller.state));
          resellerList.add(reseller);
        }
      }

      if (resellerList != null && !resellerList.isEmpty()) {
        getResellerListResponse.setResellers(resellerList);
        getResellerListResponse.setErrorCode(ActionErrorCode.ERROR_CODE_00);
        getResellerListResponse.setErrorMessage(
            ActionErrorCode.getInstance().getMessage(ActionErrorCode.ERROR_CODE_00));
      } else {
        getResellerListResponse.setErrorCode(ActionErrorCode.ERROR_CODE_Z5);
        getResellerListResponse.setErrorMessage(PGConstants.NO_RECORDS_FOUND);
      }

    } catch (Exception e) {
      logger.error("ResellerDaoImpl | getRerchantlist | Exception " + e);
      getResellerListResponse.setErrorCode(ActionErrorCode.ERROR_CODE_Z5);
      getResellerListResponse
          .setErrorMessage(ActionErrorCode.getInstance().getMessage(ActionErrorCode.ERROR_CODE_Z5));
    }

    logger.info("ResellerDaoImpl | getRerchantlist | Exiting");
    return getResellerListResponse;
  }

  public Integer getTotalNumberOfResellerRecords(GetResellerListRequest serachReseller) {
    JPAQuery<Long> query = new JPAQuery<Long>(entityManager);
    List<Long> list = query.from(QPGReseller.pGReseller)
    	.select(QPGReseller.pGReseller.resellerId)
        .where(isResellerNameLike(serachReseller.getResellerName()),
            isCityLike(serachReseller.getCity()), isCountryEq(serachReseller.getCountry()),
            isEmailLike(serachReseller.getEmailId()),
            isAccountNumberEq(serachReseller.getAccountNumber()),
            isContactNameLike(serachReseller.getContactName()),
            isPhoneLike(serachReseller.getPhone()), isStatusEq(serachReseller.getStatus()))
        .fetch();

    return (StringUtils.isListNotNullNEmpty(list) ? list.size() : 0);
  }

  private BooleanExpression isContactNameLike(String contactName) {
    return (contactName != null && !"".equals(contactName)) ? QPGReseller.pGReseller.contactName
        .toUpperCase().like("%" + contactName.toUpperCase().replace("*", "") + "%") : null;
  }

  private BooleanExpression isResellerNameLike(String resellerName) {
    return (resellerName != null && !"".equals(resellerName)) ? QPGReseller.pGReseller.resellerName
        .toUpperCase().like("%" + resellerName.toUpperCase().replace("*", "") + "%") : null;
  }

  private BooleanExpression isCityLike(String city) {
    return (city != null && !"".equals(city)) ? QPGReseller.pGReseller.city.toUpperCase()
        .like("%" + city.toUpperCase().replace("*", "") + "%") : null;
  }

  private BooleanExpression isCountryEq(String country) {
    return (country != null && !"".equals(country)) ? QPGReseller.pGReseller.country.eq(country)
        : null;
  }

  private BooleanExpression isEmailLike(String emailId) {
    return (emailId != null && !"".equals(emailId)) ? QPGReseller.pGReseller.emailId.toUpperCase()
        .like("%" + emailId.toUpperCase().replace("*", "") + "%") : null;
  }

  private BooleanExpression isAccountNumberEq(Long accountNumber) {
    return (accountNumber != null && !"".equals(accountNumber.toString()))
        ? QPGReseller.pGReseller.accountNumber.eq(accountNumber) : null;
  }

  private BooleanExpression isPhoneLike(String phone) {
    return (phone != null && !"".equals(phone)) ? QPGReseller.pGReseller.phone.toUpperCase()
        .like("%" + phone.toUpperCase().replace("*", "") + "%") : null;
  }

  private BooleanExpression isStatusEq(Integer status) {
    return (status != null && !"".equals(status.toString()))
        ? QPGReseller.pGReseller.status.eq(status) : null;
  }

  private OrderSpecifier<Timestamp> orderByCreatedDateDesc() {
    return QPGReseller.pGReseller.createdDate.desc();
  }

  @Override
  public PGReseller getResellerByresellerId(Long resellerId) {
    return resellerRepository.findByResellerId(resellerId);
  }

  @Override
  public UpdateResellerResponse updateReseller(UpdateResellerRequest updateResellerRequest) {
    logger.info("Entering:: ResellerDaoImpl:: updateReseller method");
    UpdateResellerResponse updateResellerResponse = new UpdateResellerResponse();
    List<PGReseller> resellerDb =
        resellerRepository.findByEmailIdOrderByUpdatedDateDesc(updateResellerRequest.getEmailId());

    if (StringUtils.isListNotNullNEmpty(resellerDb)
        && resellerDb.get(0).getStatus() != PGConstants.STATUS_DELETED
        && !resellerDb.get(0).getResellerId().equals(updateResellerRequest.getResellerId())) {

      updateResellerResponse.setErrorCode(ActionErrorCode.ERROR_CODE_Z5);
      updateResellerResponse.setErrorMessage((PGConstants.DUPLICATE_RESELLER_EMAIL_ID));
      return updateResellerResponse;
    }
    PGReseller pgReseller =
        resellerRepository.findByResellerId(updateResellerRequest.getResellerId());

    if (null != pgReseller) {
      pgReseller.setResellerName(updateResellerRequest.getResellerName());
      pgReseller.setContactName(updateResellerRequest.getContactName());
      pgReseller.setEmailId(updateResellerRequest.getEmailId());
      pgReseller.setPhone(updateResellerRequest.getPhone());
      pgReseller.setAccountNumber(updateResellerRequest.getAccountNumber());
      pgReseller.setCity(updateResellerRequest.getCity());
      pgReseller.setCountry(updateResellerRequest.getCountry());
      pgReseller.setState(updateResellerRequest.getState());
      pgReseller.setStatus(updateResellerRequest.getStatus());
      pgReseller.setAddress1(updateResellerRequest.getAddress1());
      pgReseller.setAddress2(updateResellerRequest.getAddress2());
      pgReseller.setUpdatedBy(updateResellerRequest.getUpdatedBy());
      pgReseller.setUpdatedDate(updateResellerRequest.getUpdatedDate());
      pgReseller.setZip(updateResellerRequest.getZip());

      resellerRepository.save(pgReseller);

      updateResellerResponse.setUpdated(true);
      updateResellerResponse.setErrorCode(ActionErrorCode.ERROR_CODE_00);
      updateResellerResponse
          .setErrorMessage(ActionErrorCode.getInstance().getMessage(ActionErrorCode.ERROR_CODE_00));
    }

    return updateResellerResponse;
  }

  /**
   * @param resellerId
   * @return
   */
  @Override
  public DeleteResellerResponse deleteReseller(Long resellerId) {

    DeleteResellerResponse deleteResellerResponse = new DeleteResellerResponse();

    PGReseller resellerdb2 = resellerRepository.findByResellerId(resellerId);
    if (null != resellerdb2) {
      resellerdb2.setStatus(PGConstants.STATUS_DELETED);
      resellerdb2.setUpdatedDate(DateUtil.getCurrentTimestamp());

      resellerRepository.save(resellerdb2);

      deleteResellerResponse.setIsdeleated(true);
      deleteResellerResponse.setErrorCode(ActionErrorCode.ERROR_CODE_00);
      deleteResellerResponse
          .setErrorMessage(ActionErrorCode.getInstance().getMessage(ActionErrorCode.ERROR_CODE_00));

    } else {
      deleteResellerResponse.setIsdeleated(false);
      deleteResellerResponse.setErrorCode(ActionErrorCode.ERROR_CODE_Z5);
      deleteResellerResponse.setErrorMessage(PGConstants.RESELLER_DETAIL_DELETE_ERROR);
    }

    return deleteResellerResponse;
  }

  /**
   * @return
   */
  @Override
  public List<PGReseller> getResellerData() {
    List<PGReseller> pgReseller = null;
    JPAQuery<Tuple> query = new JPAQuery<Tuple>(entityManager);
    List<Tuple> tupleList = query.from(QPGReseller.pGReseller)
    	.select(QPGReseller.pGReseller.resellerId, QPGReseller.pGReseller.resellerName)
        .where(QPGReseller.pGReseller.status.eq(0)).orderBy(orderByCreatedDateDesc())
        .fetch();
    if (!CollectionUtils.isEmpty(tupleList)) {
      pgReseller = new ArrayList<>();
      PGReseller reseller = null;
      for (Tuple tuple : tupleList) {
        reseller = new PGReseller();
        reseller.setResellerId(tuple.get(QPGReseller.pGReseller.resellerId));
        reseller.setResellerName(tuple.get(QPGReseller.pGReseller.resellerName));
        pgReseller.add(reseller);
      }
    }
    return pgReseller;
  }

}
