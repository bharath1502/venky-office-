/**
 * 
 */
package com.chatak.pg.upstream;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.config.AutowireCapableBeanFactory;

import com.chatak.pg.acq.service.ChatakPaymentServiceImpl;
import com.chatak.pg.acq.service.PaymentService;
import com.chatak.pg.acq.service.PaymentServiceImpl;
import com.chatak.pg.acq.spring.util.SpringDAOBeanFactory;
import com.chatak.pg.dao.util.StringUtil;

/**
 *
 * << Add Comments Here >>
 *
 * @author Girmiti Software
 * @date 09-Mar-2015 2:57:28 PM
 * @version 1.0
 */
public class BINUpstreamRouter {

  private static List<Integer> binList = new ArrayList<Integer>(1);
  
  static {
    binList.add(422224);
    binList.add(533335);
    
  }

  public BINUpstreamRouter() {
    AutowireCapableBeanFactory acbFactory = SpringDAOBeanFactory.getSpringContext().getAutowireCapableBeanFactory();
    acbFactory.autowireBean(this);
  }

  /**
   * Method to check the BIN and returns the respective processor service
   * 
   * @param cardNumber
   * @return
   */
  public static synchronized PaymentService getPaymentService(String cardNumber) {
    if(StringUtil.isNullEmpty(cardNumber)) {
      return new ChatakPaymentServiceImpl();
    }
    else {

      Integer cardBIN = Integer.parseInt((cardNumber.length() > 5) ? cardNumber.substring(0, 6) : cardNumber);

      if(binList.contains(cardBIN)) {
        return new PaymentServiceImpl();//Litle upstream processor routing
      }
      else {
        return new ChatakPaymentServiceImpl();
      }
    }
  }

}
