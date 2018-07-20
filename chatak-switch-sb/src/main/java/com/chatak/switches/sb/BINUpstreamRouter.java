/**
 * 
 */
package com.chatak.switches.sb;

import java.util.ArrayList;
import java.util.List;

import com.chatak.pg.dao.util.StringUtil;
import com.chatak.pg.util.Constants;
import com.chatak.switches.services.PaymentService;
import com.chatak.switches.services.PaymentServiceImpl;

/**
 * << Add Comments Here >>
 * 
 * @author Girmiti Software
 * @date 09-Mar-2015 2:57:28 PM
 * @version 1.0
 */
public class BINUpstreamRouter {

  private List<Long> binList = new ArrayList<>(1);

  /**
   * 
   */
  public BINUpstreamRouter() {

  }

  public BINUpstreamRouter(List<Long> binList) {
    this.binList = binList;
  }

  /**
   * Method to check the BIN and returns the Onus or offus
   * 
   * @param cardNumber
   * @return
   */
  public boolean isOnUsBIN(String cardNumber) {
    if (StringUtil.isNullEmpty(cardNumber)) {
      return false;
    } else {

      Long cardBIN =
          Long.parseLong((cardNumber.length() > Constants.FIVE) ? cardNumber.substring(0, Constants.SIX) : cardNumber);

      return binList.contains(cardBIN);
    }
  }

  /**
   * Method to check the BIN and returns the respective processor service
   * 
   * @param cardNumber
   * @return
   */
  public PaymentService getPaymentService() {
    return new PaymentServiceImpl();
  }

  /**
   * @return the binList
   */
  public List<Long> getBinList() {
    return binList;
  }

  /**
   * @param binList
   *          the binList to set
   */
  public void setBinList(List<Long> binList) {
    this.binList = binList;
  }

}
