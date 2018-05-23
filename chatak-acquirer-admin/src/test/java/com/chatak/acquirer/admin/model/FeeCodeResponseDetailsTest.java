/**
 * 
 */
package com.chatak.acquirer.admin.model;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import com.chatak.pg.model.FeeCodeDTO;

/**
 *
 * << Add Comments Here >>
 *
 * @author Girmiti Software
 * @date Dec 28, 2017 6:09:11 PM
 * @version 1.0
 */
public class FeeCodeResponseDetailsTest {

  private FeeCodeResponseDetails details;

  @Mock
  private List<FeeCodeDTO> feeCodeList;

  @Before
  public void init() {
    details = new FeeCodeResponseDetails();
    details.setFeeCodeList(feeCodeList);
  }

  @Test
  public void testFeeCodeResponseDetails() {
    Assert.assertEquals(feeCodeList, details.getFeeCodeList());
  }

}
