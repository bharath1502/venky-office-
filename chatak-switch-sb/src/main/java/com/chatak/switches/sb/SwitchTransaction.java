/**
 * 
 */
package com.chatak.switches.sb;

import org.jpos.iso.ISOMsg;

import com.chatak.switches.sb.exception.ChatakSwitchException;

/**
 * << Add Comments Here >>
 * 
 * @author Girmiti Software
 * @date 24-Jan-2015 2:33:10 PM
 * @version 1.0
 */
public interface SwitchTransaction {

  /**
   * Init method to configure hostIp and port
   * @param hostIp
   * @param port
   */
  public void initConfig(String hostIp, Integer port);
	
  /**
   * Service to process the Auth transaction (0100) and returns (0110)
   * 
   * @param isoMsg
   * @return
   * @throws ChatakSwitchException
   */
  public ISOMsg auth(ISOMsg isoMsg) throws ChatakSwitchException;
  
  /**
   * Service to process the Auth Repeat transaction (0121) and returns (0130)
   * 
   * @param isoMsg
   * @return
   * @throws ChatakSwitchException
   */
  public ISOMsg authAdviceRepeat(ISOMsg isoMsg) throws ChatakSwitchException;
  
  /**
   * Service to process the Auth Advice transaction (0120) and returns (0130)
   * 
   * @param isoMsg
   * @return
   * @throws ChatakSwitchException
   */
  public ISOMsg authAdvice(ISOMsg isoMsg) throws ChatakSwitchException;
  
  /**
   * Service to process the Financial transaction (0200) and returns (0210)
   * 
   * @param isoMsg
   * @return
   * @throws ChatakSwitchException
   */
  public ISOMsg financial(ISOMsg isoMsg) throws ChatakSwitchException;

  /**
   * Service to process the Financial Advice transaction (0220) and returns (0230)
   * 
   * @param isoMsg
   * @return
   * @throws ChatakSwitchException
   */
  public ISOMsg financialAdvice(ISOMsg isoMsg) throws ChatakSwitchException;
  
  /**
   * Service to process the Financial Advice Repeat transaction (0221) and returns (0230)
   * 
   * @param isoMsg
   * @return
   * @throws ChatakSwitchException
   */
  public ISOMsg financialAdviceRepeat(ISOMsg isoMsg) throws ChatakSwitchException;
  
  /**
   * Service to process the Reversal transaction (0400) and returns (0430)
   * 
   * @param isoMsg
   * @return
   * @throws ChatakSwitchException
   */
  public ISOMsg reversal(ISOMsg isoMsg) throws ChatakSwitchException;

  /**
   * Service to process the Reversal Advice transaction (0420) and returns (0430)
   * 
   * @param isoMsg
   * @return
   * @throws ChatakSwitchException
   */
  public ISOMsg reversalAdvice(ISOMsg isoMsg) throws ChatakSwitchException;
  
  /**
   * Service to process the Reversal Advice Repeat transaction (0421) and returns (0430)
   * 
   * @param isoMsg
   * @return
   * @throws ChatakSwitchException
   */
  public ISOMsg reversalAdviceRepeat(ISOMsg isoMsg) throws ChatakSwitchException;

  /**
   * Service to process the Settlement transaction (0500) and returns (0510)
   * 
   * @param isoMsg
   * @return
   * @throws ChatakSwitchException
   */
  public ISOMsg settlement(ISOMsg isoMsg) throws ChatakSwitchException;

  /**
   * Service to process the Network Management transaction (0800) and returns (0810)
   * 
   * @param isoMsg
   * @return
   * @throws ChatakSwitchException
   */
  public ISOMsg network(ISOMsg isoMsg) throws ChatakSwitchException;
  
  /**
   * Service to process the Network Management Advice transaction (0820) and returns (0810)
   * 
   * @param isoMsg
   * @return
   * @throws ChatakSwitchException
   */
  public ISOMsg networkAdvice(ISOMsg isoMsg) throws ChatakSwitchException;
}
