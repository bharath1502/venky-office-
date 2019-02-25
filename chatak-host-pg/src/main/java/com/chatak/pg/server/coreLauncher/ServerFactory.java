package com.chatak.pg.server.coreLauncher;

import com.chatak.pg.exception.ConfigException;
import com.chatak.pg.server.config.ConfigMgr;
import com.chatak.pg.server.config.TcpConfig;
import com.chatak.pg.server.tcpIp.TcpServer;
import com.chatak.pg.server.tcpIp.TcpServerException;

public final class ServerFactory {

	// Description
	public final static String TCP_IP_SERVER = "TCP1.0";

	private static ServerFactory mInstance = null;

	/**
	 * Default constructor for ServerFactory.
	 */
	private ServerFactory() {
		super();
	}

	/**
	 * This method create server based on server type
	 * 
	 * @param type
	 * @return TcpServer
	 * @throws TcpServerException
	 */
	public TcpServer createServer(String type) throws TcpServerException {
		TcpServer server = null;
		TcpConfig config = null;
		try {
			config = ConfigMgr.getInstance().getTcpConfigV31();
			server = new TcpServer(config);
		}
		catch(ConfigException e) {
			throw new TcpServerException("Unable to create server. ", e);
		}
		return server;
	}

	/**
	 * Singleton, get instance of the server factory.
	 * 
	 * @return ServerFactory
	 */
	public static ServerFactory getInstance() {
		if(mInstance == null)
			mInstance = new ServerFactory();
		return mInstance;
	}
}