/**
 * 
 */
package com.chatak.acquirer.admin.model;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import com.chatak.pg.model.Bank;

/**
 *
 * << Add Comments Here >>
 *
 * @author Girmiti Software
 * @date Dec 28, 2017 5:35:37 PM
 * @version 1.0
 */
public class BankSearchResponseTest {

  private BankSearchResponse response;

  @Mock
  List<Bank> banks;

  @Before
  public void init() {
    response = new BankSearchResponse();
    response.setBanks(banks);
  }

  @Test
  public void testBankSearchResponse() {
    Assert.assertEquals(banks, response.getBanks());
  }
}
