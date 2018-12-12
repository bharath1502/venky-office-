Below steps describes the JNDI configuration for properties file on Tomcat deployment 
--------------------------------------------------------------------------------------

1. Keep the properties file in a specific folder and should be used in JNDI later
2. Edit context.xml of {tomcat-home}/conf
3. Add below content inside <Context> tag

	<Environment name="java/chatakAcquirerSysConfig" override="false"
		type="java.lang.String"
		value="file://${PROPERTIES_FILE_PATH}" />
				
	Where,
		${PROPERTIES_FILE_PATH} - should be replaced with the absolute path of the properties file
		Ex:file:///home/raj.k/chatak-pg-acquirer.properties

4. Deploy the applications
5. Start the server

Note: Verify the application by accessing the URL
Sample context.xml is provided with this package for your references.