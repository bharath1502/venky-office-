package com.chatak.pg.server.config;

import com.chatak.pg.exception.ConfigException;


/**
 * 
 * This class deals with the Configuration manager
 */
public class ConfigMgr {

	private final static String VER_15 = "15";
	
	private final static String VER_31 = "31";

	private static ConfigMgr mInstance = null;

	private TcpConfig mTcpConfigV15 = null;
	
	private TcpConfig mTcpConfigV31 = null;

	/**
	 * Default constructor
	 */
	private ConfigMgr() {
		super();
	}

	/**
	 * Create default ConfigMgr instance
	 * 
	 * @return ConfigMgr
	 */
	public static ConfigMgr getInstance() {
		if(mInstance == null)
			mInstance = new ConfigMgr();
		return mInstance;
	}

	/**
	 * This method load the config file
	 * 
	 * @throws ConfigException
	 */
	public void loadConfigFile() throws ConfigException {
		// Set old configuration instances to null
		mTcpConfigV15 = null;
		mTcpConfigV31 = null;

		// Load new configuration file
		ServerConfig.readConfigFile();

		getTcpConfigV15();
		getTcpConfigV31();
	}

	/**
	 * This method save the configuration file
	 * 
	 * @param cfgPath
	 * @throws ConfigException
	 */
	public void saveConfigFile(String cfgPath) throws ConfigException {
		if(mTcpConfigV15 != null)
			mTcpConfigV15.updateConfig();

		ServerConfig.writeConfigFile(cfgPath);
	}

	/**
	 * This method get TCP configuration V15 file
	 * 
	 * @return Returns the instance of TcpCfgV15.
	 * @throws ConfigException
	 */
	public TcpConfig getTcpConfigV15() throws ConfigException {
		if(mTcpConfigV15 == null)
			mTcpConfigV15 = new TcpConfig(VER_15);
		return mTcpConfigV15;
	}
	
	/**
	 * This method get TCP configuration V31 file
	 * 
	 * @return Returns the instance of TcpCfgV31.
	 * @throws ConfigException
	 */
	public TcpConfig getTcpConfigV31() throws ConfigException {
		if(mTcpConfigV31 == null)
			mTcpConfigV31 = new TcpConfig(VER_31);
		return mTcpConfigV31;
	}

}