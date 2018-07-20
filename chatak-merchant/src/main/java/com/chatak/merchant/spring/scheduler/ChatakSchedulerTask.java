/**
 * 
 */
package com.chatak.merchant.spring.scheduler;

import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.session.SessionInformation;
import org.springframework.security.core.session.SessionRegistryImpl;

import com.chatak.merchant.util.StringUtil;
import com.chatak.pg.util.Properties;

/**
 * This class to hold all schedulers in the system
 * 
 * @author Girmiti Software
 * @date 23-Jul-2014 12:59:04 PM
 * @version 1.0
 */
public class ChatakSchedulerTask {

  private static final Logger logger = Logger.getLogger(ChatakSchedulerTask.class);

  @Autowired
  private SessionRegistryImpl sessionRegistry;
  

  /**
   * Method to process the User session from the Spring Session Registry
   */
  
  public void processReleaseUserSessionTask() {
    try {
      
      if(null != sessionRegistry && StringUtil.isListNotNullNEmpty(sessionRegistry.getAllPrincipals())) {
       Integer sessionActiveMinutes = Integer.parseInt(Properties.getProperty("merchant.session.expiration.minutes"));
       // Integer sessionActiveMinutes = Integer.parseInt("10");//hc 30 min
        Calendar calendar = Calendar.getInstance();

        for(Object object : sessionRegistry.getAllPrincipals()) {
          List<SessionInformation> sessionInformations = sessionRegistry.getAllSessions(object, false);
          processExpireSessionData(sessionActiveMinutes, calendar, sessionInformations);
        }
      }
    } catch(Exception e) {
      logger.error("ERROR:: ChatakSchedulerTask:: processReleaseUserSessionTask method", e);
    }
  }

  private void processExpireSessionData(Integer sessionActiveMinutes, Calendar calendar,
      List<SessionInformation> sessionInformations) {
    if(StringUtil.isListNotNullNEmpty(sessionInformations)) {
      Iterator<SessionInformation> iterator = sessionInformations.iterator();
      while(iterator.hasNext()) {
        SessionInformation sessionInformation2 = iterator.next();
        checkAndExpireSession(sessionInformation2, sessionActiveMinutes, calendar);
      }
    }
  }

  /**
   * Method to check expiration of session and remove from registry
   * 
   * @param sessionInformation
   * @param sessionActiveMinutes
   * @param calendar
   */
  private void checkAndExpireSession(SessionInformation sessionInformation, int sessionActiveMinutes, Calendar calendar) {
    try {
      if(null != sessionInformation.getLastRequest()) {
        Calendar newCalendar = Calendar.getInstance();
        newCalendar.setTime(sessionInformation.getLastRequest());
        newCalendar.add(Calendar.MINUTE, sessionActiveMinutes);
        if(calendar.after(newCalendar)) {
          sessionInformation.expireNow();
          sessionRegistry.removeSessionInformation(sessionInformation.getSessionId());
        }
      }
    } catch(Exception e) {
       	logger.error("ERROR:: ChatakSchedulerTask:: checkAndExpireSession method", e);
    }
  }
}
