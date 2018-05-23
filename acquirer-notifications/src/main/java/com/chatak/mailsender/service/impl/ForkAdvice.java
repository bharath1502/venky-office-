
package com.chatak.mailsender.service.impl;

import org.apache.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;

public class ForkAdvice {
  
  private static Logger logger = Logger.getLogger(ForkAdvice.class);
	
  public void fork(final ProceedingJoinPoint pjp) {
    Runnable task = () -> {
      try {
        pjp.proceed();
      } catch (Throwable t) {
        logger.error("ERROR:: ForkAdvice:: fork method", t);
      }
    };
    new Thread(task).start();
  }
}
