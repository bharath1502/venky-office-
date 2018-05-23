/**
 * 
 */
package com.chatak.acquirer.admin.model;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import com.chatak.pg.user.bean.MerchantAccountSearchDto;

/**
 *
 * << Add Comments Here >>
 *
 * @author Girmiti Software
 * @date Dec 28, 2017 6:28:15 PM
 * @version 1.0
 */
@SuppressWarnings("deprecation")
public class MerchantAccountSearchResponseTest {

  private MerchantAccountSearchResponse response;

  @Mock
  private List<MerchantAccountSearchDto> merchantAccountSearchDtoList;

  @Mock
  private String[][] sortProperty;

  @Before
  public void init() {
    response = new MerchantAccountSearchResponse();
    response.setMerchantAccountSearchDtoList(merchantAccountSearchDtoList);
    response.setSortProperty(sortProperty);
  }

  @Test
  public void testMerchantAccountSearchResponse() {
    Assert.assertEquals(merchantAccountSearchDtoList, response.getMerchantAccountSearchDtoList());
    Assert.assertEquals(sortProperty, response.getSortProperty());
  }

}
