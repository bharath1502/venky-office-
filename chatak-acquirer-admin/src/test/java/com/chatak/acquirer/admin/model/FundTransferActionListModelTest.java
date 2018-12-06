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
 * @date Dec 28, 2017 6:20:47 PM
 * @version 1.0
 */
public class FundTransferActionListModelTest {
  private FundTransferActionListModel listModel;

  @Mock
  List<FundTransferActionModel> pgTransfersIds;

  @Before
  public void init() {
    listModel = new FundTransferActionListModel();
    listModel.setAction("action");
    listModel.setPgTransfersIds(pgTransfersIds);
  }

  @Test
  public void testFundTransferActionListModel() {
    Assert.assertEquals("action", listModel.getAction());
    Assert.assertEquals(pgTransfersIds, listModel.getPgTransfersIds());
  }

}
