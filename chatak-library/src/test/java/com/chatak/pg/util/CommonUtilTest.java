package com.chatak.pg.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;

import com.chatak.pg.exception.PrepaidException;

@RunWith(MockitoJUnitRunner.class)
public class CommonUtilTest {

  private static Logger logger = LogManager.getLogger(CommonUtilTest.class);

  @InjectMocks
  CommonUtil commonUtil;

  @Test
  public void formatAmountOnCurrencyInvalidAmountTest() {
    try {
      CommonUtil.formatAmountOnCurrency("", 2, 2, '.', ',');
    } catch(PrepaidException e) {
      logger.error("Error :: CommonUtilTest :: formatAmountOnCurrencyInvalidAmountTest", e);
    }
  }

  @Test
  public void formatAmountOnCurrencyTest() throws PrepaidException {
    String result = CommonUtil.formatAmountOnCurrency("123456789", 2, 2, '.', ',');
    Assert.assertEquals(result, "1,23,45,67.89");
  }

  @Test
  public void formatAmountOnCurrencyLesscurrencyExponentTest() throws PrepaidException {
    String result = CommonUtil.formatAmountOnCurrency("1.00", 4, 2, '.', ',');
    Assert.assertEquals(result, "0.0100");
  }

  @Test
  public void formatAmountOnCurrencySameSeparatorDotTest() throws PrepaidException {
    String result = CommonUtil.formatAmountOnCurrency("100", 4, 2, '.', '.');
    Assert.assertEquals(result, "0.0100");
  }

  @Test
  public void formatAmountOnCurrencySameSeparatorTest() throws PrepaidException {
    String result = CommonUtil.formatAmountOnCurrency("100", 4, 2, ',', ',');
    Assert.assertEquals(result, "0,0100");
  }
}