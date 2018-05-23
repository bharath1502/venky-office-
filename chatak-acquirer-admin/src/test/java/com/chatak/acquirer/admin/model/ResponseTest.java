/**
 * 
 */
package com.chatak.acquirer.admin.model;

import static org.junit.Assert.fail;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * << Add Comments Here >>
 *
 * @author Girmiti Software
 * @date Dec 28, 2017 7:14:56 PM
 * @version 1.0
 */
public class ResponseTest {

  private Response response;

  @Before
  public void init() {
    response = new Response();
    response.setErrorCode("errorCode");
    response.setTotalNoOfRows(Integer.parseInt("1"));
    response.setErrorMessage("errorMessage");
  }

  @Test
  public void testResponse() {
    Assert.assertEquals("errorCode", response.getErrorCode());
    Assert.assertEquals(Integer.valueOf(1), response.getTotalNoOfRows());
    Assert.assertEquals("errorMessage", response.getErrorMessage());
  }

}
