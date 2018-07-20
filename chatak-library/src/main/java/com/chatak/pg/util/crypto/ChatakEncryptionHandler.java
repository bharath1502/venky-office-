package com.chatak.pg.util.crypto;

import org.apache.log4j.Logger;

/**
 * << Add Comments Here >>
 * 
 * @author Girmiti Software
 * @date 25-May-2015 10:25:28 AM
 * @version 1.0
 */
public class ChatakEncryptionHandler {
  
  private static Logger logger = Logger.getLogger(ChatakEncryptionHandler.class);

  /**
   * Method to encrypt the given value with Key
   * 
   * @param value
   * @return
   */
  public String encrypt(String saltKey, String value) {
    StringBuilder response = new StringBuilder(value);
    String encryptedValue = null;
    try {
      ChatakEncryption chatakEncryption = new AESEncryption();
      encryptedValue = chatakEncryption.encrypt(response.toString(), saltKey);
      if(encryptedValue != null) {
        encryptedValue = encryptedValue.replaceAll("\r\n", "");
        encryptedValue = encryptedValue.replaceAll("\n", "");
      }

    }
    catch(Exception e) {
      logger.error("ERROR:: ChatakEncryptionHandler:: encrypt method", e);
    }
    return encryptedValue;
  }

  /**
   * Method to decrypt the given value with Key
   * 
   * @param value
   * @return
   */
  public String decrypt(String saltKey, String value) {
    String decryptedValue = null;
    try {
      ChatakEncryption chatakEncryption = new AESEncryption();
      decryptedValue = chatakEncryption.decrypt(value, saltKey);
    }
    catch(Exception e) {
      logger.error("ERROR:: ChatakEncryptionHandler:: decrypt method", e);
    }
    return decryptedValue;
  }
  
  public static void main(String[] args) {
    ChatakEncryptionHandler handler = new ChatakEncryptionHandler();

    String enc = handler.encrypt("rS1OUmqREPbM&vrI", ";4999999999999109=19081014764094900000?");
    logger.info(enc);
    logger.info(handler.decrypt("rS1OUmqREPbM&vrI", enc));
    
  }

}
