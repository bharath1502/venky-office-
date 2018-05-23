/**
 * 
 */
package com.chatak.acquirer.admin.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.chatak.pg.acq.dao.model.PGParams;

/**
 * << Add Comments Here >>
 * 
 * @author Girmiti Software
 * @date 29-Aug-2015 12:34:30 PM
 * @version 1.0
 */
public final class ProcessorConfig {

  public static final String LITLE_PREFIX = "LITLE_";

  public static final String DEMO = "DEMO";

  public static final String PRELIVE = "PRELIVE";

  public static final String LIVE = "LIVE";

  public static final String USER = "USER_";

  public static final String PASS = "PASS_";

  public static final String URL = "URL_";

  public static final String MID = "MID_";

  public static final String MAG_TEK_KEY = "MAG_TEK_KEY";

  public static final String FEE_SERVICE = "FEE_SERVICE_";

  private static Map<String, String> processorConfiguration = new HashMap<>();

  ProcessorConfig() {}

  public static void setProcessorConfig(List<PGParams> pgParams) {
    for (PGParams pgParam : pgParams) {

      processorConfiguration.put(pgParam.getParamName(), pgParam.getParamValue());

    }
  }

  public static String get(String key) {
    return processorConfiguration.get(key);
  }

}
