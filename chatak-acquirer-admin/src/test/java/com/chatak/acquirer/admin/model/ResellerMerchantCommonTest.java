/**
 * 
 */
package com.chatak.acquirer.admin.model;

import static org.junit.Assert.*;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * << Add Comments Here >>
 *
 * @author Girmiti Software
 * @date Dec 28, 2017 7:07:17 PM
 * @version 1.0
 */
public class ResellerMerchantCommonTest {

  private ResellerMerchantCommon resellerMerchantCommon;

  @Before
  public void init() {
    resellerMerchantCommon = new ResellerMerchantCommon();
    resellerMerchantCommon.setAddress1("address1");
    resellerMerchantCommon.setAddress2("address2");
    resellerMerchantCommon.setCity("city");
    resellerMerchantCommon.setState("state");
    resellerMerchantCommon.setCountry("country");
    resellerMerchantCommon.setPageIndex(Integer.parseInt("1"));
    resellerMerchantCommon.setPageSize(Integer.parseInt("1"));
    resellerMerchantCommon.setNoOfRecords(Integer.parseInt("1"));
  }

  @Test
  public void testResellerMerchantCommon() {
    Assert.assertEquals("address1", resellerMerchantCommon.getAddress1());
    Assert.assertEquals("address2", resellerMerchantCommon.getAddress2());
    Assert.assertEquals("city", resellerMerchantCommon.getCity());
    Assert.assertEquals("state", resellerMerchantCommon.getState());
    Assert.assertEquals("country", resellerMerchantCommon.getCountry());
    Assert.assertEquals(Integer.valueOf(1), resellerMerchantCommon.getPageIndex());
    Assert.assertEquals(Integer.valueOf(1), resellerMerchantCommon.getPageSize());
    Assert.assertEquals(Integer.valueOf(1), resellerMerchantCommon.getNoOfRecords());
  }

}
