/**
 * 
 */
package com.chatak.acquirer.admin.model;

import java.sql.Timestamp;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

/**
 *
 * << Add Comments Here >>
 *
 * @author Girmiti Software
 * @date Dec 28, 2017 6:34:33 PM
 * @version 1.0
 */
public class MerchantDataTest {

  private MerchantData merchantData;

  @Mock
  private Timestamp date;

  @Before
  public void init() {
    merchantData = new MerchantData();
    merchantData.setId(Long.parseLong("1"));
    merchantData.setMerchantCode("merchantCode");
    merchantData.setBusinessName("businessName");
    merchantData.setFirstName("firstName");
    merchantData.setLastName("lastName");
    merchantData.setPhone(Long.parseLong("1"));
    merchantData.setFax(Long.parseLong("1"));
    merchantData.setEmailId("emailId");
    merchantData.setPin("pin");
    merchantData.setTimeZone("timeZone");
    merchantData.setAppMode("appMode");
    merchantData.setUserName("userName");
    merchantData.setBusinessURL("businessURL");
    merchantData.setFederalTaxId(Long.parseLong("1"));
    merchantData.setStateTaxId(Long.parseLong("1"));
    merchantData.setSalesTaxId(Long.parseLong("1"));
    merchantData.setOwnership("ownership");
    merchantData.setEstimatedYearlySale(Long.parseLong("1"));
    merchantData.setStatus("status");
    merchantData.setTipAmount(true);
    merchantData.setTaxAmount(true);
    merchantData.setRole("role");
    merchantData.setFeeProgram("feeProgram");
    merchantData.setBusinessStartDate("businessStartDate");
    merchantData.setNoOfEmployee("noOfEmployee");
    merchantData.setProcessor("processor");
    merchantData.setRefunds(true);
    merchantData.setShowRecurringBilling(true);
    merchantData.setAutoSettlement(true);
    merchantData.setShippingAmount(true);
    merchantData.setAllowRepeatSale(true);
    merchantData.setCreatedDate(date);
    merchantData.setUpdatedDate("updatedDate");
    merchantData.setCreatedDateString("createdDateString");
    merchantData.setVirtualTerminal(true);
    merchantData.setPosTerminal(true);
    merchantData.setOnline(true);
    merchantData.setWebSiteAddress("webSiteAddress");
    merchantData.setReturnUrl("returnUrl");
    merchantData.setCancelUrl("cancelUrl");
    merchantData.setMcc("mcc");
    merchantData.setReason("reason");
    merchantData.setLocalCurrency("localCurrency");
  }

  @Test
  public void testMerchantData() {
    Assert.assertEquals(Long.valueOf(1), merchantData.getId());
    Assert.assertEquals("merchantCode", merchantData.getMerchantCode());
    Assert.assertEquals("businessName", merchantData.getBusinessName());
    Assert.assertEquals("firstName", merchantData.getFirstName());
    Assert.assertEquals("lastName", merchantData.getLastName());
    Assert.assertEquals(Long.valueOf(1), merchantData.getPhone());
    Assert.assertEquals(Long.valueOf(1), merchantData.getFax());
    Assert.assertEquals("emailId", merchantData.getEmailId());
    Assert.assertEquals("pin", merchantData.getPin());
    Assert.assertEquals("timeZone", merchantData.getTimeZone());
    Assert.assertEquals("appMode", merchantData.getAppMode());
    Assert.assertEquals("userName", merchantData.getUserName());
    Assert.assertEquals("businessURL", merchantData.getBusinessURL());
    Assert.assertEquals(Long.valueOf(1), merchantData.getFederalTaxId());
    Assert.assertEquals(Long.valueOf(1), merchantData.getStateTaxId());
    Assert.assertEquals(Long.valueOf(1), merchantData.getSalesTaxId());
    Assert.assertEquals("ownership", merchantData.getOwnership());
    Assert.assertEquals(Long.valueOf(1), merchantData.getEstimatedYearlySale());
    Assert.assertEquals("status", merchantData.getStatus());
    Assert.assertEquals(true, merchantData.getTipAmount());
    Assert.assertEquals(true, merchantData.getTaxAmount());
    Assert.assertEquals("role", merchantData.getRole());
    Assert.assertEquals("feeProgram", merchantData.getFeeProgram());
    Assert.assertEquals("businessStartDate", merchantData.getBusinessStartDate());
    Assert.assertEquals("noOfEmployee", merchantData.getNoOfEmployee());
    Assert.assertEquals("processor", merchantData.getProcessor());
    Assert.assertEquals(true, merchantData.getRefunds());
    Assert.assertEquals(true, merchantData.getShowRecurringBilling());
    Assert.assertEquals(true, merchantData.getAutoSettlement());
    Assert.assertEquals(true, merchantData.getShippingAmount());
    Assert.assertEquals(true, merchantData.getAllowRepeatSale());
    Assert.assertEquals(date, merchantData.getCreatedDate());
    Assert.assertEquals("updatedDate", merchantData.getUpdatedDate());
    Assert.assertEquals("createdDateString", merchantData.getCreatedDateString());
    Assert.assertEquals(true, merchantData.getVirtualTerminal());
    Assert.assertEquals(true, merchantData.getPosTerminal());
    Assert.assertEquals(true, merchantData.getOnline());
    Assert.assertEquals("webSiteAddress", merchantData.getWebSiteAddress());
    Assert.assertEquals("returnUrl", merchantData.getReturnUrl());
    Assert.assertEquals("cancelUrl", merchantData.getCancelUrl());
    Assert.assertEquals("mcc", merchantData.getMcc());
    Assert.assertEquals("reason", merchantData.getReason());
    Assert.assertEquals("localCurrency", merchantData.getLocalCurrency());
  }

}
