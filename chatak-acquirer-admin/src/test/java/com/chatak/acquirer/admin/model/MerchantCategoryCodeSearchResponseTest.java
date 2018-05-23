/**
 * 
 */
package com.chatak.acquirer.admin.model;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import com.chatak.pg.model.MerchantCategoryCode;

/**
 *
 * << Add Comments Here >>
 *
 * @author Girmiti Software
 * @date Dec 28, 2017 6:32:24 PM
 * @version 1.0
 */
public class MerchantCategoryCodeSearchResponseTest {

  private MerchantCategoryCodeSearchResponse response;

  @Mock
  List<MerchantCategoryCode> mccs;

  @Before
  public void init() {
    response = new MerchantCategoryCodeSearchResponse();
    response.setMccs(mccs);
  }

  @Test
  public void testMerchantCategoryCodeSearchResponse() {
    Assert.assertEquals(mccs, response.getMccs());
  }

}
