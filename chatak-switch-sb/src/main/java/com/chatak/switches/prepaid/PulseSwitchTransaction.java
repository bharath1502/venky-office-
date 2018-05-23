/**
 * 
 */
package com.chatak.switches.prepaid;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jpos.iso.ISOException;
import org.jpos.iso.ISOMsg;

import com.chatak.pg.constants.ActionErrorCode;
import com.chatak.pg.util.Constants;
import com.chatak.switches.jpos.SwitchISOPackager;
import com.chatak.switches.sb.SwitchTransaction;
import com.chatak.switches.sb.channel.ChatakSwitchChannel;
import com.chatak.switches.sb.exception.ChatakSwitchException;
import com.chatak.switches.sb.util.ThreadPool;

/**
 *
 * << Add Comments Here >>
 *
 * @author Girmiti Software
 * @date 24-Jan-2015 2:44:05 PM
 * @version 1.0
 */
public class PulseSwitchTransaction implements SwitchTransaction {
  
  private Logger logger = LogManager.getLogger(this.getClass());
  
  private final static String SWITCH_NAME = "Pulse";
  
  private String hostIp;
  private Integer port;
  
  @Override
  public ISOMsg auth(ISOMsg isoMsg) throws ChatakSwitchException {
    ISOMsg isoMsgResponse = null;
    try {
      isoMsgResponse = sendMsgToPulseSwitchChannel(isoMsg);
    }
    catch(Exception e) {
      logger.error("ERROR:: PulseSwitchTransaction:: auth method",e);
      throw new ChatakSwitchException(ActionErrorCode.ERROR_CODE_91);
    }
    return isoMsgResponse;
  }
  
  @Override
  public void initConfig(String hostIp, Integer port) {
  	this.hostIp=hostIp;
  	this.port=port;
  }

  @Override
  public ISOMsg authAdviceRepeat(ISOMsg isoMsg) throws ChatakSwitchException {
    ISOMsg isoMsgResponse = null;
    try {
      isoMsgResponse = sendMsgToPulseSwitchChannel(isoMsg);
    }
    catch(Exception e) {
      logger.error("ERROR:: PulseSwitchTransaction:: authAdviceRepeat method",e);
      throw new ChatakSwitchException(ActionErrorCode.ERROR_CODE_91);
    }
    return isoMsgResponse;
  }

  @Override
  public ISOMsg authAdvice(ISOMsg isoMsg) throws ChatakSwitchException {
    ISOMsg isoMsgResponse = null;
    try {
      isoMsgResponse = sendMsgToPulseSwitchChannel(isoMsg);
    }
    catch(Exception e) {
      logger.error("ERROR:: PulseSwitchTransaction:: authAdvice method",e);
      throw new ChatakSwitchException(ActionErrorCode.ERROR_CODE_91);
    }
    return isoMsgResponse;
  }
  
  @Override
  public ISOMsg financialAdvice(ISOMsg isoMsg) throws ChatakSwitchException {
    ISOMsg isoMsgResponse = null;
    try {
      isoMsgResponse = sendMsgToPulseSwitchChannel(isoMsg);
    }
    catch(Exception e) {
      logger.error("ERROR:: PulseSwitchTransaction:: financialAdvice method",e);
      throw new ChatakSwitchException(ActionErrorCode.ERROR_CODE_91);
    }
    return isoMsgResponse;
  }

  @Override
  public ISOMsg financial(ISOMsg isoMsg) throws ChatakSwitchException {
    ISOMsg isoMsgResponse = null;
    try {
      isoMsgResponse = sendMsgToPulseSwitchChannel(isoMsg);
    }
    catch(java.io.IOException e) {
      logger.error("ERROR:: PulseSwitchTransaction:: financial method",e);
      throw new ChatakSwitchException("Unable to connect to HOST - Processor");
    }
    catch(Exception e) {
      logger.error("ERROR:: PulseSwitchTransaction:: financial method", e);
      throw new ChatakSwitchException(ActionErrorCode.ERROR_CODE_91);
    }
    return isoMsgResponse;
  }

  

  @Override
  public ISOMsg financialAdviceRepeat(ISOMsg isoMsg) throws ChatakSwitchException {
    ISOMsg isoMsgResponse = null;
    try {
      isoMsgResponse = sendMsgToPulseSwitchChannel(isoMsg);
    }
    catch(Exception e) {
      logger.error("ERROR:: PulseSwitchTransaction:: financialAdviceRepeat method",e);
    }
    return isoMsgResponse;
  }

  @Override
  public ISOMsg reversal(ISOMsg isoMsg) throws ChatakSwitchException {
    ISOMsg isoMsgResponse = null;
    try {
      isoMsgResponse = sendMsgToPulseSwitchChannel(isoMsg);
    }
    catch(Exception e) {
      logger.error("ERROR:: PulseSwitchTransaction:: reversal method",e);
    }
    return isoMsgResponse;
  }

  @Override
  public ISOMsg reversalAdvice(ISOMsg isoMsg) throws ChatakSwitchException {
    ISOMsg isoMsgResponse = null;
    try {
      isoMsgResponse = sendMsgToPulseSwitchChannel(isoMsg);
    }
    catch(Exception e) {
      logger.error("ERROR:: PulseSwitchTransaction:: reversalAdvice method",e);
    }
    return isoMsgResponse;
  }

  @Override
  public ISOMsg reversalAdviceRepeat(ISOMsg isoMsg) throws ChatakSwitchException {
    ISOMsg isoMsgResponse = null;
    try {
      isoMsgResponse = sendMsgToPulseSwitchChannel(isoMsg);
    }
    catch(Exception e) {
      logger.error("ERROR:: PulseSwitchTransaction:: reversalAdviceRepeat method",e);
    }
    return isoMsgResponse;
  }

  @Override
  public ISOMsg settlement(ISOMsg isoMsg) throws ChatakSwitchException {
    ISOMsg isoMsgResponse = null;
    try {
      isoMsgResponse = sendMsgToPulseSwitchChannel(isoMsg);
    }
    catch(Exception e) {
      logger.error("ERROR:: PulseSwitchTransaction:: settlement method",e);
    }
    return isoMsgResponse;
  }

  @Override
  public ISOMsg network(ISOMsg isoMsg) throws ChatakSwitchException {
    ISOMsg isoMsgResponse = null;
    try {
      isoMsgResponse = sendMsgToPulseSwitchChannel(isoMsg);
    }
    catch(Exception e) {
      logger.error("ERROR:: PulseSwitchTransaction:: network method",e);
    }
    return isoMsgResponse;
  }

  @Override
  public ISOMsg networkAdvice(ISOMsg isoMsg) throws ChatakSwitchException {
    ISOMsg isoMsgResponse = null;
    try {
      isoMsgResponse = sendMsgToPulseSwitchChannel(isoMsg);
    }
    catch(Exception e) {
      logger.error("ERROR:: PulseSwitchTransaction:: networkAdvice method",e);
    }
    return isoMsgResponse;
  }
  
  /**
   * Init method
   * 
   * @param config
   */
  protected void initConfig() {
    new ThreadPool(Constants.FIVETHOUSAND, true);
  }
  
  /**
   * Method to send ISO Msg to Chatak Socket Channel
   * 
   * @return
   * @throws ISOException
   * @throws ChatakSwitchException
   * @throws IOException 
   */
  private ISOMsg sendMsgToPulseSwitchChannel(ISOMsg isoMsg) throws ISOException, ChatakSwitchException, IOException {
    
	ChatakSwitchChannel chatakSwitchChannel = new ChatakSwitchChannel(hostIp, port, SwitchISOPackager.getChatakGenericPackager(), SWITCH_NAME);
    chatakSwitchChannel.connect();
    chatakSwitchChannel.send(isoMsg);
    return chatakSwitchChannel.receive();
  }
}
