/**
 * 
 */
package com.chatak.acquirer.admin.spring.util;

import org.springframework.context.ApplicationContext;


/**
 *
 * << Add Comments Here >>
 *
 * @author Girmiti Software
 * @date 08-Dec-2014 4:24:58 pm
 * @version 1.0
 */
public class SpringDAOBeanFactory {

  private SpringDAOBeanFactory() {
    //Need to Modify based on requirement
  }

  private static final ApplicationContext appContext = null;

  public static ApplicationContext getSpringContext() {
    return appContext;
  }
}
