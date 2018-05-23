/**
 * 
 */
package com.chatak.acquirer.admin.model;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import com.chatak.pg.model.FeeProgramDTO;

/**
 *
 * << Add Comments Here >>
 *
 * @author Girmiti Software
 * @date Dec 28, 2017 6:11:56 PM
 * @version 1.0
 */
public class FeeProgramResponseDetailsTest {

  private FeeProgramResponseDetails details;

  @Mock
  private List<FeeProgramDTO> feeCodeList;

  @Before
  public void init() {
    details = new FeeProgramResponseDetails();
    details.setFeeCode(Long.parseLong("1"));
    details.setFeeCodeList(feeCodeList);
  }

  @Test
  public void testFeeProgramResponseDetails() {
    Assert.assertEquals(Long.valueOf(1), details.getFeeCode());
    Assert.assertEquals(feeCodeList, details.getFeeCodeList());
  }

}
