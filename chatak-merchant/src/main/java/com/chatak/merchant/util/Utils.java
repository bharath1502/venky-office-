package com.chatak.merchant.util;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;

import org.apache.log4j.Logger;

public class Utils {

  private static Logger logger = Logger.getLogger(Utils.class);

  private static final char CSV_DELIMITER = ',';

  private static final char CSV_QUOTE = '"';
  
  public static final char LF = '\n';

  public static final char CR = '\r';

  private static final char[] CSV_SEARCH_CHARS = new char[] {CSV_DELIMITER, CSV_QUOTE, CR, LF};

  private Utils() {
    // Do Nothing
  }
  
  public static boolean containsNone(String str, char[] invalidChars) {
    if (str == null || null == invalidChars) {
      return true;
    }
    int strSize = str.length();
    int validSize = invalidChars.length;
    for (int i = 0; i < strSize; i++) {
      char ch = str.charAt(i);
      for (int j = 0; j < validSize; j++) {
        if (invalidChars[j] == ch) {
          return false;
        }
      }
    }
    return true;
  }

  public static String formatCommaSeparatedValues(String commaSeparatedValue) {
    if (commaSeparatedValue != null) {
      StringWriter writer = new StringWriter();
      try {
        escapeCsv(writer, commaSeparatedValue);
      } catch (IOException exp) {
        logger.error("Error :: Utils :: formatCommaSeparatedValues IOException", exp);
      }
      return writer.toString();

    } else {
      return "";
    }

  }

  public static void escapeCsv(Writer out, String str) throws IOException {
    if (containsNone(str, CSV_SEARCH_CHARS)) {
      if (null != str) {
        out.write(str);
      }
      return;
    }
    out.write(CSV_QUOTE);
    for (int i = 0; i < str.length(); i++) {
      char c = str.charAt(i);
      if (CSV_QUOTE == c) {
        out.write(CSV_QUOTE); // escape double quote
      }
      out.write(c);
    }
    out.write(CSV_QUOTE);
  }

}
