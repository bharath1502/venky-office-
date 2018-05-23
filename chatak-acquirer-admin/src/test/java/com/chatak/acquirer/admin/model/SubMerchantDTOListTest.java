/**
 * 
 */
package com.chatak.acquirer.admin.model;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import com.chatak.pg.model.ReportsDTO;

/**
 *
 * << Add Comments Here >>
 *
 * @author Girmiti Software
 * @date Dec 28, 2017 7:15:06 PM
 * @version 1.0
 */
public class SubMerchantDTOListTest {

  private SubMerchantDTOList dtoList;

  @Mock
  private List<ReportsDTO> list;

  @Before
  public void init() {
    dtoList = new SubMerchantDTOList();
    dtoList.setList(list);
  }

  @Test
  public void testSubMerchantDTOList() {
    Assert.assertEquals(list, dtoList.getList());
  }

}
