package com.chatak.pg.server.coreLauncher;

import org.apache.log4j.Logger;

import com.chatak.pg.server.config.ConfigMgr;
import com.chatak.pg.server.tcpIp.TcpServer;
import com.chatak.pg.server.tcpIp.TcpServerException;

/**
 * @Comments : This class is the main class on the Server side. It is the driver
 *           class responsible for calling 'Servers' for SSL requests; Non-SSL
 *           simple TCP/IP requests. We would be supporting all formats. Each
 *           kind of request would be received at a predetermined port.
 */
@SuppressWarnings("static-access")
public final class PaymentGateway {

  // get class logger
  private static Logger log = Logger.getLogger(PaymentGateway.class);;

  public static boolean isAcquirerStarted = false;

  /**
   * Default Constructor.
   */
  private PaymentGateway() throws Exception {
    // initialize configuration parameters
    ConfigMgr.getInstance().loadConfigFile();
  }

  /**
   * Method to start the PG socket connections
   * 
   * @throws Exception
   */
  public static void startAcquirer() throws Exception {
    log.info("Staring Acquirer..................");
    String version = "04302013";
    // Create instance of the server and run it
    PaymentGateway server = new PaymentGateway();
    server.log.info("********* PaymentGateway 1.0 (version : " + version + ")  *************");
    server.runServer();
    isAcquirerStarted = true;
    synchronized(server) {
      server.wait();
    }
    
  }

  /**
   * This method creates server objects/threads and associate to each other and
   * start servers.
   */
  public void runServer() {

    ServerRunner server = new ServerRunner(ServerFactory.TCP_IP_SERVER, "TcpIp Version 1.0");

    // Create server threads
    Thread thread = new Thread(server);
    

    // Start server threads
    thread.start();

  }

  /**
   * ServerRunner class
   */
  private class ServerRunner implements Runnable {
    String serverType = "";

    String serverDescription = "Default";

    /**
     * Constructs a ServerRunner with the given identifier.
     * 
     * @param ident
     *          Used to identify output from this Listener.
     */
    public ServerRunner(String type, String description) {
      serverType = type;
      serverDescription = description;
    }

    /**
     * This method implements Runnable interface function. Get all the
     * configuration parameters required for the server and create server
     * instance.
     */
    public void run() {
      log.info("Run Server: " + serverDescription);
      try {
        log.info("Starting Server " + serverDescription);
        TcpServer server = ServerFactory.getInstance().createServer(serverType);
        server.runServer();
      }
      catch(TcpServerException e) {
        e.printStackTrace();
        log.error("Caught TcpServer exception: " + e.getMessage(), e);
      }
      catch(Exception e) {
        e.printStackTrace();
        log.error("Caught exception: " + e.getMessage(), e);
      }
    }
  }
}