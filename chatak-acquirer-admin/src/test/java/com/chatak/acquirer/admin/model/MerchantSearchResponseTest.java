/**
 * 
 */
package com.chatak.acquirer.admin.model;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import com.chatak.pg.acq.dao.model.PGMerchant;

/**
 *
 * << Add Comments Here >>
 *
 * @author Girmiti Software
 * @date Dec 28, 2017 7:03:56 PM
 * @version 1.0
 */
public class MerchantSearchResponseTest {

  private MerchantSearchResponse response;

  @Mock
  private List<MerchantData> merchants;

  @Mock
  private List<MerchantData> subMerchants;

  @Mock
  private PGMerchant merchantData;

  @Before
  public void init() {
    response = new MerchantSearchResponse();
    response.setMerchants(merchants);
    response.setSubMerchants(subMerchants);
    response.setMerchantData(merchantData);
  }

  @Test
  public void testMerchantSearchResponse() {
    Assert.assertEquals(merchants, response.getMerchants());
    Assert.assertEquals(subMerchants, response.getSubMerchants());
    Assert.assertEquals(merchantData, response.getMerchantData());
  }

}
