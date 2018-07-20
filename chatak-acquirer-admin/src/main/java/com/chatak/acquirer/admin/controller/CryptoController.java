/**
 * 
 */
package com.chatak.acquirer.admin.controller;

import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.chatak.acquirer.admin.constants.URLMappingConstants;
import com.chatak.crypto.dukpt.DUKPTUtil;
import com.chatak.pg.util.Constants;

/**
 * << Add Comments Here >>
 *	
 * @author Girmiti Software
 * @date Jul 8, 2015 12:11:50 PM
 * @version 1.0
 */
@SuppressWarnings({"rawtypes"})
@Controller
public class CryptoController implements URLMappingConstants {

  private static Logger logger = Logger.getLogger(MerchantController.class);

  @RequestMapping(value = CHATAK_CRYPTO, method = RequestMethod.GET)
  public ModelAndView showCryptoDownload(HttpServletRequest request, Map model,
      HttpSession session) {
    logger.info("Entering:: CryptoController:: showCryptoDownload method");
    ModelAndView modelAndView = new ModelAndView(CHATAK_CRYPTO);
    logger.info("Exiting:: CryptoController:: showCryptoDownload method");
    return modelAndView;
  }

  @RequestMapping(value = CHATAK_CRYPTO_DOWNLOAD, method = RequestMethod.POST)
  public ModelAndView downloadCrypto(HttpServletRequest request, Map model, HttpSession session,
      HttpServletResponse response) {
    logger.info("Entering:: CryptoController:: downloadCrypto method");
    ModelAndView modelAndView = new ModelAndView(CHATAK_CRYPTO);
    if (request.getHeader("referer") == null) {
      session.invalidate();
      modelAndView.setViewName(INVALID_REQUEST_PAGE);
      return modelAndView;
    }
    Date date = new Date();
    String dateString = new SimpleDateFormat(Constants.EXPORT_FILE_NAME_DATE_FORMAT).format(date);
    String filename = "DUKPT-" + dateString + ".key";
    response.setContentType("text");
    response.setHeader("Content-Disposition", "attachment; filename=" + filename);
    try {
      OutputStream outputStream = response.getOutputStream();
      String outputResult = DUKPTUtil.deriveKey();
      outputStream.write(outputResult.getBytes());
      outputStream.flush();
      outputStream.close();
    } catch (Exception e) {
      logger.error("Exiting:: CryptoController:: downloadCrypto method", e);
    }
    logger.info("Exiting:: CryptoController:: downloadCrypto method");
    return modelAndView;
  }
}
