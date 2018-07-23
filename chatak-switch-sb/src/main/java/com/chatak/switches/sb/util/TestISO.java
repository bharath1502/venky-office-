/**
 * 
 */
package com.chatak.switches.sb.util;

import org.apache.log4j.Logger;
import org.jpos.iso.ISOMsg;

import com.chatak.switches.prepaid.ChatakPrepaidSwitchTransaction;
import com.chatak.switches.sb.SwitchTransaction;

/**
 *
 * << Add Comments Here >>
 *
 * @author Girmiti Software
 * @date 28-Jan-2015 2:17:52 AM
 * @version 1.0
 */
public class TestISO {
  
  private static Logger logger = Logger.getLogger(TestISO.class);

  public static void main(String[] args) throws Exception {
    for(int i = 0; i < 1; i++) {
      Runnable task = () -> {
        try {
          SwitchTransaction switchTransaction = new ChatakPrepaidSwitchTransaction();
          ISOMsg isoMsg = new ISOMsg();
          isoMsg.setMTI("0200");
          isoMsg.set(Integer.parseInt("3"), "201234");
          isoMsg.set(Integer.parseInt("4"), "10000");
          isoMsg.set(Integer.parseInt("7"), "110722180");
          isoMsg.set(Integer.parseInt("11"), "123456");
          isoMsg.set(Integer.parseInt("32"), "100005");
          isoMsg.set(Integer.parseInt("44"), "A5DFGR");
          switchTransaction.auth(isoMsg);
        }
        catch(Exception e) {
          logger.error("ERROR:: TestISO:: main method", e);
        }   
      };
      new Thread(task).start();
      
    }
    
  }
}
