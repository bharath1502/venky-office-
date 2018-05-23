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
 * @date Dec 28, 2017 5:45:37 PM
 * @version 1.0
 */
public class CurrenyValueTest {

  private CurrenyValue currenyValue;

  @Before
  public void init() {
    currenyValue = new CurrenyValue();
    currenyValue.setId(Long.parseLong("1"));
    currenyValue.setCurrencyCodeNumeric("currencyCodeNumeric");
    currenyValue.setCurrencyName("currencyName");
    currenyValue.setStatus("status");
    currenyValue.setCurrencyCodeAlpha("currencyCodeAlpha");
    currenyValue.setCurrencyExponent(Integer.parseInt("1"));
    currenyValue.setCurrencySymbol('C');
    currenyValue.setCurrencySeparatorPosition(Integer.parseInt("1"));
    currenyValue.setCurrencyThousandsUnit(',');
    currenyValue.setCurrencyMinorUnit('.');
  }

  @Test
  public void testCurrenyValue() {
    Assert.assertEquals(Long.valueOf(1), currenyValue.getId());
    Assert.assertEquals("currencyCodeNumeric", currenyValue.getCurrencyCodeNumeric());
    Assert.assertEquals("currencyName", currenyValue.getCurrencyName());
    Assert.assertEquals("status", currenyValue.getStatus());
    Assert.assertEquals("currencyCodeAlpha", currenyValue.getCurrencyCodeAlpha());
    Assert.assertEquals(Integer.valueOf(1), currenyValue.getCurrencyExponent());
    Assert.assertEquals(Character.valueOf('C'), currenyValue.getCurrencySymbol());
    Assert.assertEquals(Integer.valueOf(1), currenyValue.getCurrencySeparatorPosition());
    Assert.assertEquals(Character.valueOf(','), currenyValue.getCurrencyThousandsUnit());
    Assert.assertEquals(Character.valueOf('.'), currenyValue.getCurrencyMinorUnit());
  }
}
