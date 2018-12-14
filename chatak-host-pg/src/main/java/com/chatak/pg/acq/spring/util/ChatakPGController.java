/**
 * 
 */
package com.chatak.pg.acq.spring.util;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.chatak.license.exception.InvalidChatakLicenseException;
import com.chatak.license.validator.ChatakLicenseValidator;
import com.chatak.pg.acq.dao.AdminUserDao;
import com.chatak.pg.acq.dao.PGParamsDao;
import com.chatak.pg.acq.dao.model.PGParams;
import com.chatak.pg.server.coreLauncher.PaymentGateway;
import com.chatak.pg.util.Constants;
import com.chatak.pg.util.EncryptionUtil;
import com.chatak.switches.sb.util.ProcessorConfig;

/**
 * << Add Comments Here >>
 * 
 * @author Girmiti Software
 * @date 11-Dec-2014 10:53:59 am
 * @version 1.0
 */
@Controller
public class ChatakPGController extends Constants {

  /*@Autowired
  private ChatakPGLicense chatakPGLicense;
*/
  @Autowired
  private AdminUserDao adminUserDao;

  @Autowired
  private PGParamsDao paramsDao;

  @Autowired
  private ApplicationContext appContext;

  @Value("${chatak.pg.downstream.socket.timeout}")
  private Integer scoketConnectionTimeout;

  @PostConstruct
  private void loadConfiguration() {
    List<PGParams> pgParams = paramsDao.getAllPGParams();
    ProcessorConfig.setProcessorConfiguration(pgParams);
  }

  /**
   * Method used to display the login page
   * 
   * @param request
   * @param response
   * @param session
   * @param model
   * @return
   */
  @RequestMapping(value = CHATAK_PG_LOGIN, method = RequestMethod.POST)
  public ModelAndView login(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
	  scoketConnectionTimeout = Constants.CHATAK_DOWNSTREAM_SOCKET_TIMEOUT ;
    ModelAndView modelAndView = new ModelAndView(Constants.CHATAK_PG_LOGIN_PAGE);

    SpringDAOBeanFactory.appContext = appContext;

    try {
     // ChatakLicenseValidator.getInstance().validateChatakLicenseKey();
     // CHATAK_LICENSE_VALID = true;
      String userName = request.getParameter("uPG");
      String pass = request.getParameter("pPG");

      if(null == userName || null == pass || "".equals(userName.trim()) || "".equals(pass.trim())) {
        modelAndView.addObject(Constants.ERROR, "Authentication failed!");
        session.setAttribute(Constants.ERROR, "Authentication failed!");
      }
      else {
        boolean isAuthenticated = adminUserDao.authenticateAcquirerAdmin(userName, EncryptionUtil.encodePassword(pass));
        if(isAuthenticated) {

          if(PaymentGateway.isAcquirerStarted) {
            modelAndView.addObject(Constants.ERROR, "PG Acquirer is started and running...");
            modelAndView.setViewName(Constants.CHATAK_PG_HOME);
            return modelAndView;
          }
          else {

            modelAndView.addObject(Constants.ERROR, "PG Acquirer is getting Started... Please wait...");
            session.setAttribute(Constants.ERROR, null);
            session.setAttribute("PG_USER_AUTH", "TRUE");
            new Thread(new Runnable() {
              @Override
              public void run() {
                try {
                  PaymentGateway.startAcquirer();
                }
                catch(Exception e) {
                  e.printStackTrace();
                }
              }
            }).start();

            modelAndView.setViewName(Constants.CHATAK_PG_HOME);
          }
        }
        else {
          modelAndView.addObject(Constants.ERROR, "Authentication failed!");
          session.setAttribute(Constants.ERROR, "Authentication failed!");
        }

      }
    }
    catch(DataAccessException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    
    catch(Exception e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    return modelAndView;
  }

  /**
   * Method used to display the login page
   * 
   * @param request
   * @param response
   * @param session
   * @param model
   * @return
   */
  @RequestMapping(value = CHATAK_PG_RELOAD, method = RequestMethod.GET)
  public ModelAndView reload(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
    ModelAndView modelAndView = new ModelAndView(Constants.CHATAK_PG_HOME);
    String userAuth = (String) session.getAttribute("PG_USER_AUTH");
    if(null == userAuth) {
      modelAndView.setViewName(Constants.CHATAK_PG_LOGIN_PAGE);
      session.setAttribute(Constants.ERROR, null);

    }
    else {
      if(PaymentGateway.isAcquirerStarted) {
        modelAndView.addObject(Constants.ERROR, "PG Acquirer is started and running...");
        modelAndView.setViewName(Constants.CHATAK_PG_HOME);
      }
      else {
        modelAndView.addObject(Constants.ERROR,
                               (Constants.CHATAK_LICENSE_VALID) ? "PG Acquirer is not running. Please contact Techincal team."
                                                               : "Chatak license is expired! Please contact Chatak Support team.");
        modelAndView.setViewName(Constants.CHATAK_PG_HOME);
      }
    }
    return modelAndView;
  }

}
