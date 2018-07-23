/**
 * 
 */
package com.chatak.switches.sb.channel;

import java.io.EOFException;
import java.io.IOException;
import java.io.InterruptedIOException;
import java.net.SocketException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jpos.iso.BaseChannel;
import org.jpos.iso.ISOException;
import org.jpos.iso.ISOFilter.VetoException;
import org.jpos.iso.ISOMsg;
import org.jpos.iso.ISOPackager;
import org.jpos.util.LogEvent;

import com.chatak.pg.util.Constants;
import com.chatak.pg.util.LogHelper;
import com.chatak.pg.util.LoggerMessage;
import com.chatak.switches.jpos.util.JPOSUtil;
import com.chatak.switches.sb.exception.ChatakSwitchException;
import com.chatak.switches.services.TransactionService;

/**
 * << Add Comments Here >>
 * 
 * @author Girmiti Software
 * @date 24-Jan-2015 4:15:51 PM
 * @version 1.0
 */
public class ChatakSwitchChannel extends BaseChannel {

  protected Logger logger = LogManager.getLogger(this.getClass());

  private static org.apache.log4j.Logger log = org.apache.log4j.Logger.getLogger(ChatakSwitchChannel.class);

  private String switchName;

  /**
   * @param socketHost
   * @param port
   * @param isoPackager
   * @throws ChatakSwitchException
   */
  public ChatakSwitchChannel(String socketHost, int port, ISOPackager isoPackager, String switchName) throws ChatakSwitchException {
    super(socketHost, port, isoPackager);
    this.switchName = switchName;
  }

  /**
   * Method to Send ISO Message to Socket
   * 
   * @param isoMsg
   * @throws IOException
   * @throws ISOException
   */
  @Override
  public void send(ISOMsg isoMsg) throws IOException, ISOException {
	LogHelper.logEntry(log, LoggerMessage.getCallerName());
    try {
      if(!isConnected()) {
    	LogHelper.logInfo(log, LoggerMessage.getCallerName(), switchName + " Connection is not available");
        throw new ISOException(switchName + " Connection is not available");
      }
      LogHelper.logInfo(log, LoggerMessage.getCallerName(), ">> Sending ISO Packet to Switch - "+switchName+"\n");
      String field22 = isoMsg.getString(Constants.TWENTYTWO);
      if(field22.length() > Constants.THREE) {
        isoMsg.set(Constants.TWENTYTWO, field22.substring(0, Constants.THREE));
      }
      JPOSUtil.logISOData(isoMsg, logger);
      isoMsg.setDirection(ISOMsg.OUTGOING);
      LogEvent evt = new LogEvent(this, switchName + ">> send");
      isoMsg.setPackager(packager);
      isoMsg = applyOutgoingFilters(isoMsg, evt);
      isoMsg.setDirection(ISOMsg.OUTGOING);
      byte[] b = isoMsg.pack();
      synchronized(serverOutLock) {
        serverOut.write(b);
        serverOut.flush();
      }
      cnt[TX]++;
      setChanged();
      notifyObservers(isoMsg);
    }
    catch(VetoException e) {
      throw e;
    }
    catch(ISOException e) {
      throw e;
    }
    catch(Exception e) {
      throw new ISOException("unexpected exception", e);
    }
    finally {
      logger.info("Completed the Sending Transaction");
    }
  }

  /**
   * Method to receive the ISO Msg from Socket
   * 
   * @return
   * @throws IOException
   * @throws ISOException
   */
  @Override
  public ISOMsg receive() throws IOException, ISOException {
	LogHelper.logEntry(log, LoggerMessage.getCallerName());
    byte[] incomingBytes = new byte[Constants.NUMBER];

    LogEvent evt = new LogEvent(this, switchName + "<< receive");
    ISOMsg isoMsg = new ISOMsg();

    isoMsg.setSource(this);

    try {
      if(!isConnected()) {
    	LogHelper.logInfo(log, LoggerMessage.getCallerName(), switchName + " Connection is not available");
        throw new ISOException(switchName + " Connection is not available");
      }

      synchronized(serverInLock) {
        serverIn.read(incomingBytes);
      }
      isoMsg.setPackager(packager);

      if(incomingBytes.length > 0) {
        isoMsg.unpack(incomingBytes);
      }
      isoMsg.setDirection(ISOMsg.INCOMING);
      evt.addMessage(isoMsg);
      isoMsg = applyIncomingFilters(isoMsg, header, incomingBytes, evt);
      isoMsg.setDirection(ISOMsg.INCOMING);
      cnt[RX]++;
      setChanged();
      notifyObservers(isoMsg);
    }
    catch(ISOException e) {
      evt.addMessage(e);
      if(incomingBytes != null) {
        JPOSUtil.logISOData(isoMsg, logger);
      }
      throw e;
    }
    catch(EOFException e) {
      closeSocket();
      LogHelper.logError(log, LoggerMessage.getCallerName(), e, "Peer Disconnected while Receiving Transaction");
      throw e;
    }
    catch(SocketException e) {
      closeSocket();
      if(usable) {
    	LogHelper.logError(log, LoggerMessage.getCallerName(), e, "Peer Disconnected while Receiving Transaction" + e.getMessage());
      }
      throw e;
    }
    catch(InterruptedIOException e) {
      closeSocket();
      LogHelper.logError(log, LoggerMessage.getCallerName(), e, "Timeout while Receiving Transaction due to " + e.getMessage());
      throw e;
    }
    catch(IOException e) {
      closeSocket();
      if(usable) {
    	LogHelper.logError(log, LoggerMessage.getCallerName(), e, "Input/Output while Receiving Transaction due to " + e.getMessage());
      }
      throw e;
    }
    catch(Exception e) {
      LogHelper.logError(log, LoggerMessage.getCallerName(), e, "System Malfunction" + e.getMessage());
      evt.addMessage(isoMsg);
      evt.addMessage(e);
      throw new ISOException("System Malfunction", e);
    }
    finally {
    	LogHelper.logInfo(log, LoggerMessage.getCallerName(), "Completed the Receive Transaction");
    }
    logger.info("<< Receiving ISO Packet from Switch - "+switchName+"\n");
    JPOSUtil.logISOData(isoMsg, logger);
    LogHelper.logExit(log, LoggerMessage.getCallerName());
    return isoMsg;
  }

  /**
   * Run server
   * 
   */
  public void run()  {
	// need to implement based on requirement
  }

}
