package com.chatak.pg.acq.dao.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.chatak.pg.acq.dao.RolesFeatureMappingDao;
import com.chatak.pg.acq.dao.model.PGFeatures;
import com.chatak.pg.acq.dao.model.PGRolesFeatureMapping;
import com.chatak.pg.acq.dao.model.QPGRolesFeatureMapping;
import com.chatak.pg.acq.dao.repository.FeatureRepository;
import com.chatak.pg.acq.dao.repository.RolesFeatureMappingRepository;
import com.chatak.pg.acq.dao.repository.SubFeatureRepository;
import com.chatak.pg.model.FeatureDTO;
import com.chatak.pg.model.UserRolesDTO;
import com.chatak.pg.util.StringUtils;
import com.mysema.query.jpa.impl.JPAQuery;
import com.mysema.query.types.expr.BooleanExpression;

@SuppressWarnings("unchecked")
@Repository("rolesFeatureMappingDao")
public class RolesFeatureMappingDaoImpl implements RolesFeatureMappingDao {
  
  private static Logger logger = Logger.getLogger(VoidTransactionDaoImpl.class);
  @Autowired
  RolesFeatureMappingRepository rolesFeatureMappingRepository;

  @Autowired
  FeatureRepository featureRepository;

  @Autowired
  SubFeatureRepository subFeatureRepository;

  @PersistenceContext
  private EntityManager entityManager;

  @Override
  public void saveRoleFeatureMapping(PGRolesFeatureMapping pgRolesFeatureMapping) {
    rolesFeatureMappingRepository.save(pgRolesFeatureMapping);
  }

  @Override
  public List<PGRolesFeatureMapping> getRolesFeature(Long roleId) {

    List<PGRolesFeatureMapping> roleFeatureMapping =
        rolesFeatureMappingRepository.findByRoleId(roleId);
    return (StringUtils.isListNotNullNEmpty(roleFeatureMapping)) ? roleFeatureMapping : null;
  }

  @Override
  public void deleteRolesFeatureMApping(Long roleId) {
    List<PGRolesFeatureMapping> roleFeatureMapping =
        rolesFeatureMappingRepository.findByRoleId(roleId);
    if (StringUtils.isListNotNullNEmpty(roleFeatureMapping)) {
      for (PGRolesFeatureMapping pgRolesFeatureMapping : roleFeatureMapping) {
        rolesFeatureMappingRepository.delete(pgRolesFeatureMapping);
      }
    }

  }

  public Map<Long, List<String>> getFeatureList(List<UserRolesDTO> roleList) {
    if (StringUtils.isListNotNullNEmpty(roleList)) {
      try {
        Map<Long, List<String>> featureMap = new HashMap<>();

        for (UserRolesDTO userRole : roleList) {

          StringBuilder sb = new StringBuilder(
              "select  distinct(feature_id) FROM pg_role_feature_mapping_new where  USER_ROLE_ID=:roleId");
          Query qry = entityManager.createNativeQuery(sb.toString());
          qry.setParameter("roleId", userRole.getRoleId());
          List<BigDecimal> featureList = qry.getResultList();
          List<String> featureDataList = new ArrayList<>();
          validatefeatureList(featureList, featureDataList);
          featureMap.put(userRole.getRoleId(), featureDataList);

        }
        return featureMap;
      } catch (Exception e) {
        logger.error("ERROR:: RolesFeatureMappingDaoImpl::getFeatureList ", e);
        
      }
    }
    return null;
  }

  private void validatefeatureList(List<BigDecimal> featureList, List<String> featureDataList) {
	for (BigDecimal featureData : featureList) {

	    PGFeatures pgFeatures = featureRepository.findByFeatureId(featureData.longValue());
	    StringBuilder featureName = new StringBuilder(pgFeatures.getFeatureName() + " - ");

	    String featureValue = featureName.toString();
	    featureDataList.add(featureValue.substring(0, featureValue.length() - 1));

	  }
  }

  /**
   * @param roleLevel
   * @param status
   * @return
   */
  @Override
  public List<PGRolesFeatureMapping> getAdminRoleFeaturesList(FeatureDTO featureDTO) {

    String roleType = featureDTO.getRoleType();
    JPAQuery query = new JPAQuery(entityManager);
    QPGRolesFeatureMapping qRolesFeatures = QPGRolesFeatureMapping.pGRolesFeatureMapping;
    return query.from(qRolesFeatures).where(isRoleLevel(roleType)).list(qRolesFeatures);
  }

  private BooleanExpression isRoleLevel(String roleLevel) {
    return (roleLevel != null && !"".equals(roleLevel))
        ? QPGRolesFeatureMapping.pGRolesFeatureMapping.roleId.like(roleLevel) : null;
  }
}
