package com.chatak.pg.server.config;

/**
 * 
 * This class deals with the Server Configuration parameters
 */
public class ServerConfig extends ApplicationConfig {

  // configuration groups
  public final static String JDBC_GROUP = "jdbc";

  public final static String SSL_GROUP = "ssl";

  public final static String TCPIP_GROUP = "tcpIp";

  // JDBC configuration keys
  public final static String JDBC_HOST = "host";

  public final static String JDBC_PORT = "port";

  public final static String JDBC_SID = "sid";

  public final static String JDBC_USER = "user";

  public final static String JDBC_PASSWORD = "password";

  public final static String JDBC_MIN_CONNECTIONS = "minConnections";

  public final static String JDBC_MAX_CONNECTIONS = "maxConnections";

  // SSL configuration keys
  public final static String SSL_PORT = "port";

  public final static String SSL_BACKLOG_SIZE = "backlogSize";

  public final static String SSL_THREADPOOL_SIZE = "threadpoolSize";

  public final static String SSL_KEYSTORE_PATH = "keystorePath";

  public final static String SSL_KEYSTORE_PASSWORD = "keystorePassword";

  public final static String SSL_KEY_PASSWORD = "keyPassword";

  // TCP/IP configuration keys
  public final static String TCPIP_PORT = "port";

  public final static String TCPIP_BACKLOG_SIZE = "backlogSize";

  public final static String TCPIP_THREADPOOL_SIZE = "threadpoolSize";

  // private constructor to disallow instantiation
  private ServerConfig() {
  }
}
