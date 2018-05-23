/**
 * 
 */
package com.chatak.acquirer.admin.model;

import java.sql.Timestamp;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import com.chatak.pg.model.FeatureDTO;

/**
 *
 * << Add Comments Here >>
 *
 * @author Girmiti Software
 * @date Dec 28, 2017 4:53:35 PM
 * @version 1.0
 */
public class AdminFeatureRequestTest {

  private AdminFeatureRequest request;

  @Mock
  List<FeatureDTO> featureDTOs;

  @Mock
  private Timestamp createdDate;

  @Before
  public void init() {
    request = new AdminFeatureRequest();
    request.setFeatureId(Long.parseLong("1"));
    request.setName("name");
    request.setFeatureLevel(Long.parseLong("1"));
    request.setRefFeatureId(Long.parseLong("1"));
    request.setStatus("status");
    request.setCreatedDate(createdDate);
    request.setRoleFeatureId("roleFeatureId");
    request.setRoleType("roleType");
    request.setRoleId(Long.parseLong("1"));
    request.setDisplayOrder(Long.parseLong("1"));
    request.setSubFeatureId(Long.parseLong("1"));
    request.setFeatureDTOs(featureDTOs);
  }

  @Test
  public void testAdminFeatureRequest() {
    Assert.assertEquals(Long.valueOf(1), request.getFeatureId());
    Assert.assertEquals("name", request.getName());
    Assert.assertEquals(Long.valueOf(1), request.getFeatureLevel());
    Assert.assertEquals(Long.valueOf(1), request.getRefFeatureId());
    Assert.assertEquals("status", request.getStatus());
    Assert.assertEquals(createdDate, request.getCreatedDate());
    Assert.assertEquals("roleFeatureId", request.getRoleFeatureId());
    Assert.assertEquals("roleType", request.getRoleType());
    Assert.assertEquals(Long.valueOf(1), request.getRoleId());
    Assert.assertEquals(Long.valueOf(1), request.getDisplayOrder());
    Assert.assertEquals(Long.valueOf(1), request.getSubFeatureId());
    Assert.assertEquals(featureDTOs, request.getFeatureDTOs());
  }
}
