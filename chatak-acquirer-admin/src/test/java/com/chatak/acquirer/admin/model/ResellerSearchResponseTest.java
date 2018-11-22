/**
 * 
 */
package com.chatak.acquirer.admin.model;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

/**
 *
 * << Add Comments Here >>
 *
 * @author Girmiti Software
 * @date Dec 28, 2017 7:12:36 PM
 * @version 1.0
 */
public class ResellerSearchResponseTest {

  private ResellerSearchResponse response;

  @Mock
  private List<ResellerValue> reseller;

  @Before
  public void init() {
    response = new ResellerSearchResponse();
    response.setReseller(reseller);
  }

  @Test
  public void testResellerSearchResponse() {
    Assert.assertEquals(reseller, response.getReseller());
  }

}
