package com.chatak.acquirer.admin.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chatak.acquirer.admin.exception.ChatakAdminException;
import com.chatak.acquirer.admin.service.FeatureService;
import com.chatak.pg.acq.dao.FeatureDao;
import com.chatak.pg.acq.dao.SubFeatureDao;
import com.chatak.pg.acq.dao.model.PGFeatures;
import com.chatak.pg.acq.dao.model.PGSubFeature;
import com.chatak.pg.bean.FeatureDataResponse;
import com.chatak.pg.dao.util.StringUtil;
import com.chatak.pg.util.Constants;
import com.chatak.pg.util.Properties;

@Service("featureService")
public class FeatureServiceImpl implements FeatureService {
	
private static Logger logger = Logger.getLogger(FeatureServiceImpl.class);
	
  @Autowired
  FeatureDao featureDao;

  @Autowired
  SubFeatureDao subFeatureDao;

  @Override
  public Map<Long, List<FeatureDataResponse>> getFeature() throws ChatakAdminException {
    Map<Long, List<FeatureDataResponse>> featureMap =
        new HashMap<Long, List<FeatureDataResponse>>();
    try {
      List<PGFeatures> featureList = featureDao.getFeatureList();
      List<PGSubFeature> subFeatureList = subFeatureDao.getSubFeatureList();
      // Set the Feature Data response
      setFeatureDataResponse(featureMap, featureList);
      // Set the sub feature to Feature Data response
      processSubFeatureList(featureMap, subFeatureList);
    } catch (Exception e) {
    	logger.error("ERROR:: FeatureServiceImpl:: getFeature method", e);
      throw new ChatakAdminException(Properties.getProperty("chatak.normal.error.message"));
    }
    return featureMap;
  }

	private void processSubFeatureList(Map<Long, List<FeatureDataResponse>> featureMap,
			List<PGSubFeature> subFeatureList) {
		if (StringUtil.isListNotNullNEmpty(subFeatureList)) {
			List<FeatureDataResponse> featureDataList = null;
			FeatureDataResponse featureDataResponse = null;
			for (PGSubFeature subFeature : subFeatureList) {
				featureDataResponse = new FeatureDataResponse();
				featureDataResponse.setFeatureId(subFeature.getFeatureId());
				featureDataResponse.setFeatureName(subFeature.getFeatureName());
				featureDataResponse.setSubFeatureId(subFeature.getSubFeatureId());
				featureDataResponse.setFeatureUrl(subFeature.getFeatureName());
				featureDataResponse.setFeatureLevel(Constants.FEATURE_LEVEL_ONE);
				featureDataList = featureMap.get(subFeature.getFeatureId());
				if (featureDataList == null)
					featureDataList = new ArrayList<FeatureDataResponse>();
				featureDataList.add(featureDataResponse);
				featureMap.put(subFeature.getFeatureId(), featureDataList);

			}
		}
	}

	private void setFeatureDataResponse(Map<Long, List<FeatureDataResponse>> featureMap, List<PGFeatures> featureList) {
		if (StringUtil.isListNotNullNEmpty(featureList)) {
			List<FeatureDataResponse> featureDataList = null;
			FeatureDataResponse featureDataResponse = null;
			for (PGFeatures features : featureList) {
				featureDataResponse = new FeatureDataResponse();
				featureDataResponse.setFeatureId(features.getFeatureId());
				featureDataResponse.setFeatureName(features.getFeatureName());
				featureDataResponse.setFeatureLevel(Constants.FEATURE_LEVEL_ZERO);
				featureDataResponse.setFeatureUrl(features.getFeatureName());
				featureDataList = featureMap.get(features.getFeatureId());
				if (featureDataList == null)
					featureDataList = new ArrayList<FeatureDataResponse>();
				featureDataList.add(featureDataResponse);
				featureMap.put(features.getFeatureId(), featureDataList);
			}
		}
	}
}
