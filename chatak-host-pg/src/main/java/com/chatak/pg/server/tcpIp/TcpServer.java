package com.chatak.pg.server.tcpIp;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import org.apache.log4j.Logger;

import com.chatak.pg.exception.ConfigException;
import com.chatak.pg.server.config.ConfigMgr;
import com.chatak.pg.server.config.TcpConfig;
import com.chatak.pg.util.Constants;

/**
 
 * This class is the main class on the Server side for Tcp/Ip
 * connections This class will be responsible for accepting
 * connections, handling requests initially, handing it over for
 * further processing, sending the response and closing the connection
 * at the server end.
 */
public class TcpServer {

  // Class logger
  protected static Logger log = Logger.getLogger(TcpServer.class);;

  protected String mServerType = "TCP";

  ThreadPool mThreadPool = null; // thread pool

  int mPort; // SSL port

  int mBacklogSize; // server connection backlog size

  /**
   * This is the constructor that gets called from the driver
   * 
   * @param int port: SSL port where it is listening
   * @param int backlogSize: Server connection backlog size
   * @param int numThreads: This is the SSL Threadpool Size return void
   */
  public TcpServer(TcpConfig tcpConfig) {
    initConfig(tcpConfig);
    showServerConfig();
  }

  /**
   * Init method
   * 
   * @param config
   */
  protected void initConfig(TcpConfig config) {
    mPort = config.getPort();
    mBacklogSize = config.getBacklogSize();
    int numThreads = config.getThreadPoolSize();
    log.info("Creating thread pool with " + numThreads);
    mThreadPool = new ThreadPool(numThreads, true);
  }

  /**
   * Run server
   * 
   * @throws TcpServerException
   */
  public void runServer() throws TcpServerException {
    // creating a server socket to listen for connections
    ServerSocket serverSocket = createServerSocket();
    // accept client connection requests
    if(serverSocket != null)
      acceptConnections(serverSocket);
  }

  /**
   * Show configuration
   */
  protected void showServerConfig() {
    log.info("TcpIp server configuration values" + "\n" + "\tPort \t\t: " + mPort + "\n" + "\tBacklog Size \t: "
             + mBacklogSize);
  }

  /**
   * Load configuration
   * 
   * @throws ConfigException
   */
  protected void loadConfig() throws ConfigException {
    // initialize configuration
    TcpConfig tcpConfig = getTcpConfig();

    // create thread pool
    int numThreads = tcpConfig.getThreadPoolSize();
    log.info("creating thread pool with " + numThreads);
    mThreadPool = new ThreadPool(numThreads, true);

    // get configured tcp port
    mPort = tcpConfig.getPort();
    // get configured connection backlog size
    mBacklogSize = tcpConfig.getBacklogSize();
    log.info("Tcpip server configuration values" + "\n" + "\tPort \t\t: " + mPort + "\n" + "\tBacklog Size \t: "
             + mBacklogSize);
  }

  /**
   * This method creates a ServerSocket
   * 
   * @return ServerSocket
   */
  protected ServerSocket createServerSocket() throws TcpServerException {
    ServerSocket socket = null;
    log.info("==> creating server socket for port " + mPort);
    socket = createServerSocket(mPort);
    log.info("<== created server socket for port " + mPort);
    return socket;
  }

  /**
   * This method creates a serverSocket 1) It creates the socket connection at
   * the agreed port 2) Waits indefinitely for a request
   * 
   * @param port
   * @return ServerSocket
   */
  protected ServerSocket createServerSocket(int port) throws TcpServerException {
    ServerSocket socket = null;
    try {
      // 1) Creating the socket connection on the agreed port
      socket = new ServerSocket(port);
      log.info("Created " + socket.getLocalPort());
    }
    catch(IOException e) {
      String msg = "Unable to create server socket. Port: " + port;
      throw new TcpServerException(msg, e);
    }
    catch(SecurityException e) {
      String msg = "Unable to create server socket. Port: " + port;
      throw new TcpServerException(msg, e);
    }
    catch(Exception e) {
      String msg = "Unable to create server socket. Port: " + port;
      throw new TcpServerException(msg, e);
    }
    return socket;
  }

  /**
   * accept client connections - accepts connection request - spawns off
   * ClientHandler thread for each client
   * 
   * @param serverSocket
   */
  protected void acceptConnections(ServerSocket serverSocket) {
    Socket socket = null;
    while(true) {
      try {
      /*  if(!Constants.CHATAK_LICENSE_VALID) {
          log.info("Ooops..... Chatak Product License is expired! Please contact Administrator to continue with the services.");
          serverSocket.close();
          break;
        }*/
        // wait for a connection request.
        socket = serverSocket.accept();
        if(socket != null) {
          log.info("<== accepted client connection at " + mPort + " from " + socket.getRemoteSocketAddress());
          mThreadPool.run(createClientHandler(socket));
        }
        else {
          log.error(">>> Returned socket is null");
          Thread.sleep(1000); // Sleep for 1000 msec.
        }
      }
      catch(IOException e) {
        log.error(">>> Caught IOException " + e.getMessage(), e);
      }
      catch(Exception e) {
        e.printStackTrace();
        log.error(">>> Caught Exception " + e.getMessage(), e);
      }
    }
  }

  /**
   * Get TCP Configuration
   * 
   * @return TcpConfig
   * @throws ConfigException
   */
  protected TcpConfig getTcpConfig() throws ConfigException {
    return ConfigMgr.getInstance().getTcpConfigV15();
  }

  /**
   * Create Client Handler
   * 
   * @param socket
   * @return ClientHandler
   */
  protected ClientHandler createClientHandler(Socket socket) {
    return new ClientHandler(socket, this);
  }

  /**
   * Returns the mServerType value
   * 
   * @return the mServerType
   */
  protected String getType() {
    return mServerType;
  }
}