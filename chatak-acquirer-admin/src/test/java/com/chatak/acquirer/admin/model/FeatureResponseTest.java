/**
 * 
 */
package com.chatak.acquirer.admin.model;

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
 * @date Dec 28, 2017 6:02:03 PM
 * @version 1.0
 */
public class FeatureResponseTest {

  private FeatureResponse response;

  @Mock
  private List<String> feature;

  @Mock
  private List<String> subFeature;

  @Mock
  private List<FeatureDTO> featureDTO;

  @Before
  public void init() {
    response = new FeatureResponse();
    response.setFeature(feature);
    response.setSubFeature(subFeature);
    response.setFeatureDTO(featureDTO);
    response.setRoleType("roleType");
  }

  @Test
  public void testFeatureResponse() {
    Assert.assertEquals(feature, response.getFeature());
    Assert.assertEquals(subFeature, response.getSubFeature());
    Assert.assertEquals(featureDTO, response.getFeatureDTO());
    Assert.assertEquals("roleType", response.getRoleType());
  }
}
