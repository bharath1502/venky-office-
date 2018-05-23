/**
 * 
 */
package com.chatak.acquirer.admin.model;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * << Add Comments Here >>
 *
 * @author Girmiti Software
 * @date Dec 28, 2017 6:23:56 PM
 * @version 1.0
 */
public class FundTransferActionModelTest {

  private FundTransferActionModel model;

  @Before
  public void init() {
    model = new FundTransferActionModel();
    model.setAction("action");
    model.setPgTransfersId(Long.parseLong("1"));
  }

  @Test
  public void testFundTransferActionModel() {
    Assert.assertEquals("action", model.getAction());
    Assert.assertEquals(Long.valueOf(1), model.getPgTransfersId());
  }

}
