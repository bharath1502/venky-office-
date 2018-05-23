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
 * @date Dec 28, 2017 7:14:42 PM
 * @version 1.0
 */
public class ResellerValueTest {

  private ResellerValue resellerValue;

  @Before
  public void init() {
    resellerValue = new ResellerValue();
    resellerValue.setResellerId(Long.parseLong("1"));
    resellerValue.setResellerName("resellerName");
    resellerValue.setContactName("contactName");
    resellerValue.setEmailId("emailId");
    resellerValue.setPhone("phone");
    resellerValue.setZip("zip");
    resellerValue.setCreatedBy("createdBy");
    resellerValue.setUpdatedBy("updatedBy");
    resellerValue.setCreatedDate("createdDate");
    resellerValue.setUpdatedDate("updatedDate");
    resellerValue.setStatus("status");
    resellerValue.setAccountNumber(Long.parseLong("1"));
    resellerValue.setPageIndex(Integer.parseInt("1"));
    resellerValue.setPageSize(Integer.parseInt("1"));
    resellerValue.setNoOfRecords(Integer.parseInt("1"));
    resellerValue.setAccountBalance("accountBalance");
  }

  @Test
  public void testResellerValue() {
    Assert.assertEquals(Long.valueOf(1), resellerValue.getResellerId());
    Assert.assertEquals("resellerName", resellerValue.getResellerName());
    Assert.assertEquals("contactName", resellerValue.getContactName());
    Assert.assertEquals("emailId", resellerValue.getEmailId());
    Assert.assertEquals("phone", resellerValue.getPhone());
    Assert.assertEquals("zip", resellerValue.getZip());
    Assert.assertEquals("createdBy", resellerValue.getCreatedBy());
    Assert.assertEquals("updatedBy", resellerValue.getUpdatedBy());
    Assert.assertEquals("createdDate", resellerValue.getCreatedDate());
    Assert.assertEquals("updatedDate", resellerValue.getUpdatedDate());
    Assert.assertEquals("status", resellerValue.getStatus());
    Assert.assertEquals(Long.valueOf(1), resellerValue.getAccountNumber());
    Assert.assertEquals(Integer.valueOf(1), resellerValue.getPageSize());
    Assert.assertEquals(Integer.valueOf(1), resellerValue.getPageIndex());
    Assert.assertEquals(Integer.valueOf(1), resellerValue.getNoOfRecords());
    Assert.assertEquals("accountBalance", resellerValue.getAccountBalance());
  }

}
