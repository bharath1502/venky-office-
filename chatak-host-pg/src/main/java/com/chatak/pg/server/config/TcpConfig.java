package com.chatak.pg.server.config;

import org.w3c.dom.Node;

import com.chatak.pg.exception.ConfigException;

/**
 * This class deals with the SSL Configuration parameters
 */
public class TcpConfig {

  // configuration group
  private final static String GROUP = "tcpIp";

  // TCP/IP configuration keys
  protected final static String PORT = "port";

  protected final static String BACKLOG_SIZE = "backlogSize";

  protected final static String THREADPOOL_SIZE = "threadpoolSize";

  private final static String ATTR_VERSION = "Version";

  protected int mPort = 443; // Default port

  protected int mBacklogSize = 100; // Default back log size

  protected int mThreadPoolSize = 32; // Default thread pool

  private String mVersion = "20";

  /**
   * Default constructor, TspipConfig().
   */
  public TcpConfig() {
    initDefault();
  }

  /**
   * Full constructor
   */
  public TcpConfig(int port, int bcklgSize, int threadPool, String version) {
    mPort = port;
    mBacklogSize = bcklgSize;
    mThreadPoolSize = threadPool;
    mVersion = version;
  }

  /**
   * Partial constructor
   */
  public TcpConfig(String verId) throws ConfigException {
    mVersion = verId;
    System.out.println("Vesion ID: "+verId);
    Node node = ApplicationConfig.getNodeForAttribute(GROUP, ATTR_VERSION, verId);
    if(node != null) {
      init(node);
    }
    else {
      initDefault();
    }
  }

  /**
   * Returns the mBacklogSize value
   * 
   * @return the mBacklogSize
   */

  public int getBacklogSize() {
    return mBacklogSize;
  }

  /**
   * Set the mBacklogSize value
   * 
   * @param backlogSize
   *          the mBacklogSize to set
   */
  public void setBacklogSize(int backlogSize) {
    mBacklogSize = backlogSize;
  }

  /**
   * Returns the mPort value
   * 
   * @return the mPort
   */

  public int getPort() {
    return mPort;
  }

  /**
   * Set the mPort value
   * 
   * @param port
   *          the mPort to set
   */
  public void setPort(int port) {
    mPort = port;
  }

  /**
   * Returns the mThreadPoolSize value
   * 
   * @return the mThreadPoolSize
   */

  public int getThreadPoolSize() {
    return mThreadPoolSize;
  }

  /**
   * Set the mThreadPoolSize value
   * 
   * @param threadPoolSize
   *          the mThreadPoolSize to set
   */
  public void setThreadPoolSize(int threadPoolSize) {
    mThreadPoolSize = threadPoolSize;
  }

  /**
   * Returns the mVersion value
   * 
   * @return the mVersion
   */

  public String getVersion() {
    return mVersion;
  }

  /**
   * Set the mVersion value
   * 
   * @param version
   *          the mVersion to set
   */
  public void setVersion(String version) {
    mVersion = version;
  }

  /**
   * Init method
   */
  protected void initDefault() {
    mPort = 443;
    mBacklogSize = 100;
    mThreadPoolSize = 32;
  }

  /**
   * Init method
   * 
   * @param node
   */
  protected void init(Node node) {
    // read thread pool size from cfg file
    mThreadPoolSize = ApplicationConfig.getInt(node, THREADPOOL_SIZE);
    // read tcpip port number from cfg file
    mPort = ApplicationConfig.getInt(node, PORT);
    // read backlog size from cfg file
    mBacklogSize = ApplicationConfig.getInt(node, BACKLOG_SIZE);
  }

  /**
   * This method update the configuration
   */
  protected void updateConfig() {
    Node node = ApplicationConfig.getNodeForAttribute(GROUP, ATTR_VERSION, mVersion);
    if(node == null) {
      node = ApplicationConfig.getNodeForAttribute(GROUP, ATTR_VERSION, mVersion);
    }
    // set tcpip port number to cfg file
    ApplicationConfig.setInt(node, PORT, mPort);
    // set backlog size to cfg file
    ApplicationConfig.setInt(node, BACKLOG_SIZE, mBacklogSize);
    // set tcpip thread pool size to cfg file
    ApplicationConfig.setInt(node, THREADPOOL_SIZE, mThreadPoolSize);
  }
}
