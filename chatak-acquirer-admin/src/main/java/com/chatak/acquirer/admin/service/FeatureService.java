package com.chatak.acquirer.admin.service;

import java.util.List;
import java.util.Map;

import com.chatak.acquirer.admin.exception.ChatakAdminException;
import com.chatak.pg.bean.FeatureDataResponse;


public interface FeatureService
{
  public Map<Long, List<FeatureDataResponse>> getFeature()throws ChatakAdminException;
}
