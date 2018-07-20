package com.chatak.acquirer.admin.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.ws.rs.FormParam;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.chatak.acquirer.admin.constants.StatusConstants;
import com.chatak.acquirer.admin.constants.URLMappingConstants;
import com.chatak.pg.util.Properties;

@SuppressWarnings({"rawtypes"})
@Controller
public class ManualOperationController implements URLMappingConstants{

  private static Logger logger = Logger.getLogger(ManualOperationController.class);

  private static final String CATALINA_HOME = "catalina.home";

  private static final String APPLICATION_OCTET_STREAM = "APPLICATION/OCTET-STREAM";

  private static final String CONTENT_DISPOSITION = "Content-Disposition";

  private static final String ATTACHMENT_FILE_NAME = "attachment; filename=\"";

  private static final String ADMIN_LOG = "adminLog";

  private static final String MERCHANT_LOG = "merchantLog";

  private static final String CATALINA_LOG = "catalinaLog";

  private static final String PG_ADMIN_LOG = "chatak-acq-admin.log";

  private static final String PG_MERCHANT_LOG = "chatak-merchant.log";

  private static final String PG_CATALINA_OUT = "catalina.out";
  
  @RequestMapping(value = CHATAK_DOWNLOAD_LOGS, method = RequestMethod.GET)
  public ModelAndView showDownloadLogs(Map model, HttpSession session) {
    logger.info("Entering:: ManualOperationController:: showDownloadLogs method");
    ModelAndView modelAndView = new ModelAndView(CHATAK_DOWNLOAD_LOGS);
    modelAndView.addObject(StatusConstants.ERROR, null);
    session.setAttribute(StatusConstants.ERROR, null);
    logger.info("Exiting:: ManualOperationController:: showDownloadLogs method");
    return modelAndView;
  }
  
  @RequestMapping(value = CHATAK_PROCESS_DOWNLOAD_LOGS, method = RequestMethod.POST)
 public ModelAndView processDownloadLogs(HttpSession httpSession, HttpServletRequest httpServletRequest,
   Map<String, Object> model,HttpServletResponse httpServletResponse,Map map,@FormParam("selectedLog") final String selectedLog ){
   logger.info("Entering:: ManualOperationController:: processDownloadLogs method");
   ModelAndView modelAndView = new ModelAndView(CHATAK_DOWNLOAD_LOGS);
   logger.info("Checking for " + selectedLog);
   try {
     if (selectedLog.equals(ADMIN_LOG)) {
       String adminLoggerPath = Properties.getProperty("log4j.appender.FILE.File");
       StringBuilder adminLoggerNewPath = new StringBuilder(adminLoggerPath);
       adminLoggerNewPath = modifyLoggersPath(adminLoggerNewPath, Properties.getProperty("gateway.admin.log.location"));
       String str1[] = adminLoggerNewPath.toString().split(PG_ADMIN_LOG);
       List<String> adminLogs = new ArrayList<String>();
       File file = new File(str1[0]);
       FilenameFilter fileNameFilter = filterFileName(PG_ADMIN_LOG);
       String s[] = file.list(fileNameFilter);
       getLogs(adminLogs, s);
       model.put(ADMIN_LOG, adminLogs);
     } else if (selectedLog.equals(MERCHANT_LOG)) {
       String merchantLoggerPath = Properties.getProperty("log4j.appender.FILE.File.merchant");
       StringBuilder merchantLoggerNewPath = new StringBuilder(merchantLoggerPath);
       merchantLoggerNewPath = modifyLoggersPath(merchantLoggerNewPath, Properties.getProperty("gateway.merchant.log.location"));
       String str[] = merchantLoggerNewPath.toString().split(PG_MERCHANT_LOG);
       List<String> merchantLogs = new ArrayList<String>();
       File file = new File(str[0]);
       FilenameFilter fileNameFilter = filterFileName(PG_MERCHANT_LOG);
       String s[] = file.list(fileNameFilter);
       getLogs(merchantLogs, s);
       model.put(MERCHANT_LOG, merchantLogs);
     } else if (selectedLog.equals(CATALINA_LOG)) {
       String catalinaLoggerPath = Properties.getProperty("log4j.appender.FILE.File.catalina");
       StringBuilder catalinaLoggerNewPath = new StringBuilder(catalinaLoggerPath);
       catalinaLoggerNewPath = modifyLoggersPath(catalinaLoggerNewPath, Properties.getProperty("gateway.catalina.log.location"));
       String str[] = catalinaLoggerNewPath.toString().split(PG_CATALINA_OUT);
       List<String> catalinaLogs = new ArrayList<String>();
       File file = new File(str[0]);
       FilenameFilter fileNameFilter = filterFileName(PG_CATALINA_OUT);
       String s[] = file.list(fileNameFilter);
       getLogs(catalinaLogs, s);
       model.put(CATALINA_LOG, catalinaLogs);
     }
   } catch (Exception e) {
     logger.error("ERROR:: ManualOperationController:: processDownloadLogs method", e);
   }
   logger.info("Exiting:: ManualOperationController:: processDownloadLogs method");
   return modelAndView;
 }

  private void getLogs(List<String> portalLogs, String[] s) {
    if(s!=null){
       for (int i = 0; i < s.length; i++) {
         portalLogs.add(s[i]);
       }
     }
  }
 
  @RequestMapping(value = CHATAK_DOWNLOAD_FORM_LOGS ,method = RequestMethod.POST)
  public void downloadLogs(HttpServletRequest httpServletRequest,HttpServletResponse response,HttpSession httpSession,
      @FormParam("downloadLog") final String downloadLog, @FormParam("logName") final String logName) throws IOException{
    logger.info("Entering:: ManualOperationsController:: downloadLogs method");
    String fileName = '/'+downloadLog;
    StringBuilder loggerPath = getLoggerPath(logName);
    if (loggerPath != null) {
    	try (FileInputStream fileInputStream = new FileInputStream(loggerPath.toString());
    			OutputStream outputStream = response.getOutputStream()) {
    		if (logName.equals(ADMIN_LOG)) {
    			String str[] = loggerPath.toString().split(PG_ADMIN_LOG);
    			processWriteByteFile(response, fileInputStream, outputStream, fileName, str);
    			outputStream.flush();
    		} else if (logName.equals(MERCHANT_LOG)) {
    			String str[] = loggerPath.toString().split(PG_MERCHANT_LOG);
    			processWriteByteFile(response, fileInputStream, outputStream, fileName, str);
    			outputStream.flush();
    		} else if (logName.equals(CATALINA_LOG)) {
    			String str[] = loggerPath.toString().split(PG_CATALINA_OUT);
    			processWriteByteFile(response, fileInputStream, outputStream, fileName, str);
    			outputStream.flush();
    		}
    	} catch (Exception e) {
    		logger.error("ERROR :: ManualOperationController :: downloadLogs methods", e);
    	}
    }
    logger.info("Exiting :: ManualOperationsController :: downloadLogs method");
  }

/**
 * @param logName
 */
private StringBuilder getLoggerPath(final String logName) {
	StringBuilder loggerPath = null;
	if (logName.equals(ADMIN_LOG)) {
	    loggerPath = new StringBuilder(Properties.getProperty("log4j.appender.FILE.File"));
        loggerPath = modifyLoggersPath(loggerPath, Properties.getProperty("gateway.admin.log.location"));
	  } else if (logName.equals(MERCHANT_LOG)) {
	    loggerPath = new StringBuilder(Properties.getProperty("log4j.appender.FILE.File.merchant"));
	    loggerPath = modifyLoggersPath(loggerPath, Properties.getProperty("gateway.merchant.log.location"));
	  } else if (logName.equals(CATALINA_LOG)) {
	    loggerPath = new StringBuilder(Properties.getProperty("log4j.appender.FILE.File.catalina"));
	    loggerPath = modifyLoggersPath(loggerPath, Properties.getProperty("gateway.catalina.log.location"));
	  }
	return loggerPath;
}

  private void processWriteByteFile(HttpServletResponse response, FileInputStream fileInputStream,
      OutputStream outputStream, String fileName, String[] str) throws IOException {
    File file = new File(str[0] + fileName);
    response.setContentType(APPLICATION_OCTET_STREAM);
    response.setHeader(CONTENT_DISPOSITION, ATTACHMENT_FILE_NAME + fileName + "\"");
    byte[] byteFile = new byte[(int) file.length()];
    int bytesReaded = fileInputStream.read(byteFile);
    logger.info("Number of bytes Read : " + bytesReaded);
    outputStream.write(byteFile);
  }

  private StringBuilder modifyLoggersPath(StringBuilder properties, String poratalLogPath) {
    if (properties.toString().contains(CATALINA_HOME)) {
      String serverDirPath = System.getProperty(CATALINA_HOME) != null ? System.getProperty(CATALINA_HOME) : "";
      properties = new StringBuilder(serverDirPath + poratalLogPath);
    }
    return properties;
  }

  private FilenameFilter filterFileName(String loggerName) {
    return (dir,name) ->  {
            return name.startsWith(loggerName);
      };
    }
  }
