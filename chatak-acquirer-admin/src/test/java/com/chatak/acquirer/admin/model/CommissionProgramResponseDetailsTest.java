/**
 * 
 */
package com.chatak.acquirer.admin.model;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import com.chatak.pg.model.CommissionDTO;

/**
 *
 * << Add Comments Here >>
 *
 * @author Girmiti Software
 * @date Dec 28, 2017 5:41:54 PM
 * @version 1.0
 */
public class CommissionProgramResponseDetailsTest {

  private CommissionProgramResponseDetails details;

  @Mock
  List<CommissionDTO> commProgList;

  @Before
  public void init() {
    details = new CommissionProgramResponseDetails();
    details.setCommProgList(commProgList);
  }

  @Test
  public void testCommissionProgramResponseDetails() {
    Assert.assertEquals(commProgList, details.getCommProgList());
  }

}
