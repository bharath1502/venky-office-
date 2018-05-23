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
 * @date Dec 28, 2017 7:15:16 PM
 * @version 1.0
 */
public class TerminalsTest {

  private Terminals terminals;

  @Before
  public void init() {
    terminals = new Terminals();
    terminals.setMerchantId(Long.parseLong("1"));
    terminals.setValidFromDate("validFromDate");
    terminals.setTerminalCode(Long.parseLong("1"));
    terminals.setMerchantCode(Long.parseLong("1"));
    terminals.setValidToDate("validToDate");
    terminals.setId(Long.parseLong("1"));
    terminals.setStatus("status");
  }

  @Test
  public void testTerminals() {
    Assert.assertEquals(Long.valueOf(1), terminals.getMerchantId());
    Assert.assertEquals("validFromDate", terminals.getValidFromDate());
    Assert.assertEquals(Long.valueOf(1), terminals.getTerminalCode());
    Assert.assertEquals(Long.valueOf(1), terminals.getMerchantCode());
    Assert.assertEquals("validToDate", terminals.getValidToDate());
    Assert.assertEquals(Long.valueOf(1), terminals.getId());
    Assert.assertEquals("status", terminals.getStatus());
  }

}
