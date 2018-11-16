package com.chatak.pg.acq.dao.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.chatak.pg.acq.dao.TerminalDeviceManagementDao;
import com.chatak.pg.acq.dao.model.PGActionCodeParameters;
import com.chatak.pg.acq.dao.model.PGAid;
import com.chatak.pg.acq.dao.model.PGCaPublicKeys;
import com.chatak.pg.acq.dao.model.PGMagneticStripeParameters;
import com.chatak.pg.acq.dao.model.PGMerchant;
import com.chatak.pg.acq.dao.model.PGParameterProfile;
import com.chatak.pg.acq.dao.model.PGTerminalDeviceMangement;
import com.chatak.pg.acq.dao.model.QPGMerchant;
import com.chatak.pg.acq.dao.model.QPGMerchantTerminal;
import com.chatak.pg.acq.dao.model.QPGPosDevice;
import com.chatak.pg.acq.dao.model.QPGTerminalDeviceMangement;
import com.chatak.pg.acq.dao.repository.ActionCodeParametersRepository;
import com.chatak.pg.acq.dao.repository.ApplicationIdDetailsRepository;
import com.chatak.pg.acq.dao.repository.CAPublicKeysRepository;
import com.chatak.pg.acq.dao.repository.MagneticStripeCradParametersRepository;
import com.chatak.pg.acq.dao.repository.MerchantRepository;
import com.chatak.pg.acq.dao.repository.ParameterProfileRepository;
import com.chatak.pg.acq.dao.repository.TerminalDeviceManagementRepository;
import com.chatak.pg.dao.util.StringUtil;
import com.chatak.pg.model.TerminalDeviceManagementDTO;
import com.chatak.pg.util.Constants;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;


@Repository("mobileDeviceManagementDao")
public class TerminalDeviceManagementDaoImpl implements TerminalDeviceManagementDao {
  @PersistenceContext
  private EntityManager entityManager;

  @Autowired
  ApplicationIdDetailsRepository applicationIdDetailsRepository;

  @Autowired
  MagneticStripeCradParametersRepository magneticStripeCradParametersRepository;

  @Autowired
  CAPublicKeysRepository cAPublicKeysRepository;

  @Autowired
  ActionCodeParametersRepository actionCodeParametersRepository;

  @Autowired
  ParameterProfileRepository parameterProfileRepository;

  @Autowired
  TerminalDeviceManagementRepository mobileDeviceManagementRepository;

  @Autowired
  MerchantRepository merchantRepository;



  @Override
  public List<PGAid> getAllApplicationId() {
    return applicationIdDetailsRepository.findAll();

  }

  @Override
  public List<PGMagneticStripeParameters> getAllMagneticStripeCardParameters() {
    return magneticStripeCradParametersRepository.findAll();
  }

  @Override
  public List<PGCaPublicKeys> getAllCAPublicKeyResponse() {

    return cAPublicKeysRepository.findAll();
  }

  @Override
  public List<PGActionCodeParameters> getAllActionCodeParameters() {

    return actionCodeParametersRepository.findAll();
  }

  @SuppressWarnings("unchecked")
  @Override
  public List<PGParameterProfile> getAllParametersProfileName(String parameterType) {

    Query query = entityManager
        .createQuery("Select p FROM PGParameterProfile p WHERE p.parameterType =:parameterType");
    query.setParameter("parameterType", parameterType);
    List<PGParameterProfile> list = query.getResultList();
    return list;

  }

  @Override
  public PGTerminalDeviceMangement createTerminalDevice(
      PGTerminalDeviceMangement mobileDeviceMangement) {

    return mobileDeviceManagementRepository.save(mobileDeviceMangement);
  }

  @Override
  public List<TerminalDeviceManagementDTO> searchTerminalDevice(
      TerminalDeviceManagementDTO mobileDeviceManagementTO) {
    Integer offset = 0;
    Integer limit = 0;
    Integer pageIndex = mobileDeviceManagementTO.getPageIndex();
    Integer pageSize = mobileDeviceManagementTO.getPageSize();
    Integer totalRecords;
    String merchantsCode = mobileDeviceManagementTO.getMerchantCode();

    if (mobileDeviceManagementTO.getPageIndex() == null
        || mobileDeviceManagementTO.getPageIndex() == 1) {
      totalRecords = getTotalNumberOfRecords(mobileDeviceManagementTO);
      mobileDeviceManagementTO.setNoOfRecords(totalRecords);
    }

    if (pageIndex == null && pageSize == null) {
      offset = 0;
      limit = Constants.DEFAULT_PAGE_SIZE;
    } else {
      offset = (pageIndex - 1) * pageSize;
      limit = pageSize;
    }

    if (merchantsCode != null) {

      PGMerchant merchant =
          merchantRepository.findByMerchantCode(mobileDeviceManagementTO.getMerchantCode());
      if (merchant != null) {
        mobileDeviceManagementTO.setMerchantId(String.valueOf(merchant.getId()));
      } else {
        mobileDeviceManagementTO.setMerchantId("0");
      }
    }
    List<TerminalDeviceManagementDTO> mobileDeviceList =
        new ArrayList<>();
    JPAQuery<Tuple> query = new JPAQuery<Tuple>(entityManager);
    List<Tuple> tupleList = query
        .from(QPGTerminalDeviceMangement.pGTerminalDeviceMangement, QPGPosDevice.pGPosDevice,
            QPGMerchantTerminal.pGMerchantTerminal, QPGMerchant.pGMerchant)
        .select(QPGTerminalDeviceMangement.pGTerminalDeviceMangement.deviceManagementId,
            QPGTerminalDeviceMangement.pGTerminalDeviceMangement.imeiNo,
            QPGTerminalDeviceMangement.pGTerminalDeviceMangement.deviceId,
            QPGMerchant.pGMerchant.merchantCode, QPGMerchantTerminal.pGMerchantTerminal.merchantId,
            QPGMerchantTerminal.pGMerchantTerminal.terminalId,
            QPGTerminalDeviceMangement.pGTerminalDeviceMangement.status,
            QPGPosDevice.pGPosDevice.deviceSerialNo)
        .where(isDeviceSerialNo(mobileDeviceManagementTO.getDeviceSerialNo()),
            isImeiNo(mobileDeviceManagementTO.getImeiNo()),
            isMerchantId(mobileDeviceManagementTO.getMerchantId()),

            isTerminalId(mobileDeviceManagementTO.getTerminalId()),
            isMobileStatus(mobileDeviceManagementTO.getStatus()),



            QPGTerminalDeviceMangement.pGTerminalDeviceMangement.deviceId
                .eq(QPGPosDevice.pGPosDevice.id),
            QPGMerchantTerminal.pGMerchantTerminal.id
                .eq(QPGTerminalDeviceMangement.pGTerminalDeviceMangement.pgMerchantTerminal.id),
            QPGMerchantTerminal.pGMerchantTerminal.merchantId.eq(QPGMerchant.pGMerchant.id))
        .offset(offset).limit(limit).orderBy(orderByDeviceIdDesc())
        .fetch();


    for (Tuple tuple : tupleList) {
      TerminalDeviceManagementDTO mobileDeviceManagementTO2 = new TerminalDeviceManagementDTO();
      mobileDeviceManagementTO2.setDeviceManagementId(
          tuple.get(QPGTerminalDeviceMangement.pGTerminalDeviceMangement.deviceManagementId));
      mobileDeviceManagementTO2
          .setImeiNo(tuple.get(QPGTerminalDeviceMangement.pGTerminalDeviceMangement.imeiNo));
      mobileDeviceManagementTO2
          .setDeviceId(tuple.get(QPGTerminalDeviceMangement.pGTerminalDeviceMangement.deviceId));
      mobileDeviceManagementTO2
          .setDeviceSerialNo(tuple.get(QPGPosDevice.pGPosDevice.deviceSerialNo));
      mobileDeviceManagementTO2
          .setMerchantId(tuple.get(QPGMerchantTerminal.pGMerchantTerminal.merchantId).toString());
      mobileDeviceManagementTO2.setMerchantCode(tuple.get(QPGMerchant.pGMerchant.merchantCode));
      mobileDeviceManagementTO2
          .setTerminalId(tuple.get(QPGMerchantTerminal.pGMerchantTerminal.terminalId));
      mobileDeviceManagementTO2
          .setStatus(tuple.get(QPGTerminalDeviceMangement.pGTerminalDeviceMangement.status));
      mobileDeviceList.add(mobileDeviceManagementTO2);
    }
    return mobileDeviceList;
  }

  private OrderSpecifier<Long> orderByDeviceIdDesc() {
    return QPGTerminalDeviceMangement.pGTerminalDeviceMangement.deviceManagementId.desc();
  }

  private BooleanExpression isDeviceSerialNo(String deviceSerialNo) {
    return (deviceSerialNo != null && !"".equals(deviceSerialNo))
        ? QPGPosDevice.pGPosDevice.deviceSerialNo.like("%" + deviceSerialNo.replace("*", "") + "%")
        : null;
  }

  private BooleanExpression isImeiNo(String imeiNo) {

    return (imeiNo != null && !"".equals(imeiNo))
        ? QPGTerminalDeviceMangement.pGTerminalDeviceMangement.imeiNo.equalsIgnoreCase(imeiNo)
        : null;
  }

  private BooleanExpression isMerchantId(String merchantd) {

    return (merchantd != null)
        ? QPGMerchantTerminal.pGMerchantTerminal.merchantId.eq(Long.valueOf(merchantd)) : null;
  }

  private BooleanExpression isTerminalId(String terminalId) {

    return (terminalId != null && !"".equals(terminalId))
        ? QPGMerchantTerminal.pGMerchantTerminal.terminalId.eq(terminalId) : null;
  }

  private BooleanExpression isMobileStatus(String status) {

    return (status != null && !"".equals(status))
        ? QPGTerminalDeviceMangement.pGTerminalDeviceMangement.status.equalsIgnoreCase(status)
        : null;
  }


  private int getTotalNumberOfRecords(TerminalDeviceManagementDTO mobileDeviceManagementTO)
      {
    JPAQuery<Long> query = new JPAQuery<Long>(entityManager);
    List<Long> mobileDeviceInformationList = query
        .from(QPGTerminalDeviceMangement.pGTerminalDeviceMangement, QPGPosDevice.pGPosDevice,
            QPGMerchantTerminal.pGMerchantTerminal)
        .select(QPGTerminalDeviceMangement.pGTerminalDeviceMangement.deviceManagementId)
        .where(isDeviceSerialNo(mobileDeviceManagementTO.getDeviceSerialNo()),
            isImeiNo(mobileDeviceManagementTO.getImeiNo()),
            isMerchantId(mobileDeviceManagementTO.getMerchantId()),
            isTerminalId(mobileDeviceManagementTO.getTerminalId()),
            isMobileStatus(mobileDeviceManagementTO.getStatus()),
            QPGTerminalDeviceMangement.pGTerminalDeviceMangement.deviceId
                .eq(QPGPosDevice.pGPosDevice.id),
            QPGMerchantTerminal.pGMerchantTerminal.id
                .eq(QPGTerminalDeviceMangement.pGTerminalDeviceMangement.pgMerchantTerminal.id))
        .fetch();
    return (StringUtil.isListNotNullNEmpty(mobileDeviceInformationList)
        ? mobileDeviceInformationList.size() : 0);
  }

  @Override
  public PGTerminalDeviceMangement findByTerminalDeviceId(Long mobileDeviceId) {

    return mobileDeviceManagementRepository.findByDeviceManagementId(mobileDeviceId);
  }

  @Override
  public PGTerminalDeviceMangement updateTerminalDeviceDetails(
      PGTerminalDeviceMangement mobileDeviceMangement) {

    return mobileDeviceManagementRepository.save(mobileDeviceMangement);
  }

  @Override
  public PGAid findByApplicationId(Long applicationId) {
    return applicationIdDetailsRepository.findById(applicationId).orElse(null);
  }

  @Override
  public PGMagneticStripeParameters findByMagneticStripeId(Long magneticStripeId) {
    return magneticStripeCradParametersRepository.findById(magneticStripeId).orElse(null);
  }

  @Override
  public PGCaPublicKeys findByPublicKeyId(Long publicKeyId) {
    return cAPublicKeysRepository.findById(publicKeyId).orElse(null);
  }

  @Override
  public PGActionCodeParameters findByActioncodeId(Long actioncodeId) {
    return actionCodeParametersRepository.findById(actioncodeId).orElse(null);
  }

  @Override
  public Boolean updateCaKeys(TerminalDeviceManagementDTO mobileDeviceManagement) {
    Boolean isUpdated = Boolean.FALSE;
    Query query = entityManager.createQuery(
        "UPDATE pGTerminalDeviceMangement M SET M.caPublicKeys = :caPublicKeys WHERE M.deviceManagementId = :deviceManagementId");
    query.setParameter("caPublicKeys", mobileDeviceManagement.getCaPublicKeys());
    query.setParameter(Constants.DEVICE_MANAGEMENT_ID, mobileDeviceManagement.getDeviceManagementId());
    int updatedCount = query.executeUpdate();

    if (updatedCount > 0) {
      isUpdated = Boolean.TRUE;
    }
    return isUpdated;
  }

  @Override
  public Boolean updateDeviceStatus(PGTerminalDeviceMangement mobileDeviceMangement) {
    Boolean isUpdated = Boolean.FALSE;
    Query query = entityManager.createQuery(
        "UPDATE pGTerminalDeviceMangement M SET M.status = :status, M.remarks = :remarks WHERE M.deviceManagementId = :deviceManagementId");
    query.setParameter(Constants.STATUS, mobileDeviceMangement.getStatus());
    query.setParameter("remarks", mobileDeviceMangement.getRemarks());
    query.setParameter(Constants.DEVICE_MANAGEMENT_ID, mobileDeviceMangement.getDeviceManagementId());
    int updatedCount = query.executeUpdate();

    if (updatedCount > 0) {
      isUpdated = Boolean.TRUE;
    }
    return isUpdated;
  }

  @Override
  public PGTerminalDeviceMangement findByDeviceId(Long deviceId) {
    return mobileDeviceManagementRepository.findByDeviceId(deviceId);
  }

  @Override
  public void updateDeviceId(PGTerminalDeviceMangement mobileDeviceMangement) {
    Query query = entityManager.createQuery(
        "UPDATE pGTerminalDeviceMangement M SET M.deviceId = :deviceId, M.status = :status WHERE M.deviceManagementId = :deviceManagementId");
    query.setParameter(Constants.STATUS, mobileDeviceMangement.getStatus());
    query.setParameter("deviceId", mobileDeviceMangement.getDeviceId());
    query.setParameter(Constants.DEVICE_MANAGEMENT_ID, mobileDeviceMangement.getDeviceManagementId());
    query.executeUpdate();
  }

  @Override
  public PGTerminalDeviceMangement findByMerchantTerminalId(String terminalId) {
    JPAQuery<PGTerminalDeviceMangement> query = new JPAQuery<PGTerminalDeviceMangement>(entityManager);
    List<PGTerminalDeviceMangement> list = query
        .from(QPGTerminalDeviceMangement.pGTerminalDeviceMangement,
            QPGMerchantTerminal.pGMerchantTerminal)
        .select(QPGTerminalDeviceMangement.pGTerminalDeviceMangement)
        .where(isTerminalId(terminalId),
            QPGMerchantTerminal.pGMerchantTerminal.id
                .eq(QPGTerminalDeviceMangement.pGTerminalDeviceMangement.pgMerchantTerminal.id))
        .fetch();
    return StringUtil.isListNotNullNEmpty(list) ? list.get(0) : null;
  }

  @Override
  public void linkTerminalDevice(PGTerminalDeviceMangement mobileDeviceMangement) {
    Query query = entityManager.createQuery(
        "UPDATE pGTerminalDeviceMangement M SET M.imeiNo = :imeiNo, M.status = :status, M.updatedDate = :updatedDate WHERE M.deviceManagementId = :deviceManagementId");
    query.setParameter(Constants.STATUS, mobileDeviceMangement.getStatus());
    query.setParameter("imeiNo", mobileDeviceMangement.getImeiNo());
    query.setParameter("updatedDate", mobileDeviceMangement.getUpdatedDate());
    query.setParameter(Constants.DEVICE_MANAGEMENT_ID, mobileDeviceMangement.getDeviceManagementId());
    query.executeUpdate();
  }

}
