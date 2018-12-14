package com.chatak.pg.upstream.tcpIp;

import org.apache.log4j.Logger;
import org.jpos.iso.ISOException;
import org.jpos.iso.ISOField;
import org.jpos.iso.ISOMsg;
import org.jpos.iso.packager.GenericPackager;

import com.chatak.pg.util.ByteConversionUtils;
import com.chatak.pg.util.JPOSUtil;
import com.chatak.pg.util.StringUtils;


/**
 * 
 * @Comments : This method packages the Request in the ISO format
 */
public class SwitchRequestPackager {

	protected static SwitchRequestPackager _requestPackager = null;

	private int _length = 0;

	private String _entireRequest = "";

	private String configFilePath = "config";

	private Logger logger = Logger.getLogger(this.getClass());

	/**
	 * To ensure that we will have a Singleton but we have made it public for bean
	 * initialization for the web component
	 */
	private SwitchRequestPackager() {
		_requestPackager = this;
	}

	/**
	 * The getInstance method ensures a Singular Instance
	 */
	public static SwitchRequestPackager getInstance() {
		if(_requestPackager == null)
			_requestPackager = new SwitchRequestPackager();
		return _requestPackager;
	}

	/**
	 * This method is the one call to format a complete Request that adheres to
	 * both the ISO 8583 format as well as the PG format
	 * 
	 * @param reqObj
	 * @return byte[] - the request byte that need to be sent to the server
	 */
	public byte[] createFormattedRequest(SwitchRequest reqObj) throws Exception {
		//byte[] requestBuffer = null;
		byte[] isoBuffer = null;
		try {

			//Create ISO Message
			ISOMsg isoMsg = createISOMsg(reqObj);
			JPOSUtil.logISOData(isoMsg, logger);

			//First create the ISO Request
			isoBuffer = createISORequest(isoMsg);
			//isoBuffer = isoMsg.pack();
			set_length(isoBuffer.length);

			// Now pack it into PG message format
			//requestBuffer = packPGFormat(isoBuffer);
			//_entireRequest = ByteConversionUtils.byteArrayToHexString(requestBuffer, requestBuffer.length, true);
			_entireRequest = ByteConversionUtils.byteArrayToHexString(isoBuffer, isoBuffer.length, true);
			logger.info(_entireRequest);
		}
		catch(Exception e) {
			e.printStackTrace();
			logger.error("Exceptions occur while create the formatted request" + e.getMessage(), e);
			throw new Exception("Exceptions occur while create the formatted request" + e.getMessage(), e);
		}
		return isoBuffer;
	}

	/**
	 * This method creates a ISO format Request String ISO formatted string
	 * 
	 * @param reqObj
	 * @return byte[]- the request byte that need to be sent to the server
	 */
	private byte[] createISORequest(ISOMsg isoMsg) {
		byte[] isoRequest = new byte[350];

		isoRequest = packISOFormat(isoMsg);

		return isoRequest;
	}

	private ISOMsg createISOMsg(SwitchRequest reqObj) {
		// The ISO message
		ISOMsg isoMsg = new ISOMsg();
		try {
			isoMsg.setMTI(reqObj.get_mti());
			String procCode = reqObj.get_de3();
			logger.debug("PROC CODE is " + procCode);

			// Field 2 for AccountNumber/CardNumber
			//isoMsg.set(new ISOField(2, reqObj.get_cardNumber()));

			// Field 3 for process code
			isoMsg.set(new ISOField(3, reqObj.get_de3()));
			
			// Field 4 for original transaction amount
			if(StringUtils.isValidString(reqObj.get_de4())){
				isoMsg.set(new ISOField(4, reqObj.get_de4()));
			}
			// Field 7 Transmission Date & Time
			if(StringUtils.isValidString(reqObj.get_de7())){
				isoMsg.set(new ISOField(7, reqObj.get_de7()));
			}
			// Field 11 Systems Trace Audit Number
			if(StringUtils.isValidString(reqObj.get_de11())){
				isoMsg.set(new ISOField(11, reqObj.get_de11()));
			}
			// Field 12 Time, Local Transaction
			if(StringUtils.isValidString(reqObj.get_de12())){
				isoMsg.set(new ISOField(12, reqObj.get_de12()));
			}
			// Field 13 Date, Local Transaction
			if(StringUtils.isValidString(reqObj.get_de13())){
				isoMsg.set(new ISOField(13, reqObj.get_de13()));
			}
			// Field 15 Date, Settlement
			if(StringUtils.isValidString(reqObj.get_de15())){
				isoMsg.set(new ISOField(15, reqObj.get_de15()));
			}
			// Field 22 POS entry mode
			if(StringUtils.isValidString(reqObj.get_de18())){
				isoMsg.set(new ISOField(18, reqObj.get_de18()));
			}
			// Field 18 Merchant Type
      if(StringUtils.isValidString(reqObj.get_de22())){
        isoMsg.set(new ISOField(22, reqObj.get_de22()));
      }
			// Field 32 Acquiring Institution Identification Code
			if(StringUtils.isValidString(reqObj.get_de32())){
				isoMsg.set(new ISOField(32, reqObj.get_de32()));
			}
			// Field 35 Track2 data
      if(StringUtils.isValidString(reqObj.get_de35())){
        isoMsg.set(new ISOField(35, reqObj.get_de35()));
      }
			// Field 37 Retrieval Reference Number
			if(StringUtils.isValidString(reqObj.get_de37())){
				isoMsg.set(new ISOField(37, reqObj.get_de37()));
			}
			// Field 41 Card Acceptor Terminal Identification
			if(StringUtils.isValidString(reqObj.get_de41())){
				isoMsg.set(new ISOField(41, reqObj.get_de41()));
			}
			// Field 43 Card Acceptor Name & Location
			if(StringUtils.isValidString(reqObj.get_de43())){
				isoMsg.set(new ISOField(43, reqObj.get_de43()));
			}
			// Field 48 Merchant/ Bank Name
			if(StringUtils.isValidString(reqObj.get_de48())){
				isoMsg.set(new ISOField(48, reqObj.get_de48()));
			}
			// Field 49 Currency Code, Transaction
			if(StringUtils.isValidString(reqObj.get_de49())){
				isoMsg.set(new ISOField(49, reqObj.get_de49()));
			}
			// Field 58 National Pointof- Service Condition Code
			if(StringUtils.isValidString(reqObj.get_de58())){
				isoMsg.set(new ISOField(58, reqObj.get_de58()));
			}
			// Field 59 National Pointof- Service Geographic Data
			if(StringUtils.isValidString(reqObj.get_de59())){
				isoMsg.set(new ISOField(59, reqObj.get_de59()));
			}


		}
		catch(ISOException e) {
			logger.error("Exception occurs while creates a ISO format Request" + e.getMessage(), e);
		}
		return isoMsg;
	}

	/**
	 * This method used for pack the iso message.
	 * 
	 * @param isoMsg
	 * @return byte[]
	 */
	private byte[] packISOFormat(ISOMsg isoMsg) {
		byte[] isoRequest = new byte[350];
		try {
			GenericPackager p2 = JPOSUtil.getChatakGenericPackager();
			isoMsg.setPackager(p2);
			isoRequest = isoMsg.pack();
			JPOSUtil.logISOData(isoMsg, logger);
		}
		catch(ISOException e) {
			e.printStackTrace();
			logger.error("Exception occurs while pack the iso message" + e.getMessage(), e);
		}
		return isoRequest;
	}

	/**
	 * Format request - This method sends the transaction data. The final request
	 * with all the agreed upon formatting.
	 * 
	 * @param txnData
	 *          : The transaction data to be sent, in string format. This method
	 *          formats the enitre data into the acceptable format OUR PROTOCOL:
	 *          <STX(1)> <LOD(2)> <DATA(LOD)> <ETX(1)> <LRC(1)> where LOD =
	 *          LengthOfData
	 */
	public byte[] packPGFormat(String txnData) throws Exception {
		byte[] txnBuffer = null;
		try {
			if(txnData != null)
				txnBuffer = packPGFormat(txnData.getBytes());
		}
		catch(Exception e) {
			e.printStackTrace();
			logger.error("Exceptions occurs while pack transaction data into PG Format" + e.getMessage(), e);
			throw new Exception("Exceptions occurs while pack transaction data into PG Format" + e.getMessage(), e);
		}
		return txnBuffer;
	}

	/**
	 * Format request - This method sends the transaction data. The final request
	 * with all the agreed upon formatting.
	 * 
	 * @param dataBytes
	 *          : The transaction data to be sent, in string format. This method
	 *          formats the enitre data into the acceptable format OUR PROTOCOL:
	 *          <STX(1)> <LOD(2)> <DATA(LOD)> <ETX(1)> <LRC(1)> where LOD =
	 *          LengthOfData
	 */
	private byte[] packPGFormat(byte[] dataBytes) {
		int index = 0;
		int txnLength = dataBytes.length;

		// Getting the length of the complete formatted transaction
		int totalLength = txnLength + 3 + 2 + 1;
		byte[] completeTxnByte = new byte[totalLength];

		// To get the length of just the transaction data
		byte[] lengthBytes = ByteConversionUtils.intToByteArray(txnLength);
		// Should be 2 according to our format
		int lengthBytesLength = lengthBytes.length;

		// **************** BEGIN: POPULATING THE REQUEST *********************//


		for(int i = 0; i < lengthBytesLength; i++) {
			completeTxnByte[index++] = lengthBytes[i];
		}

		for(int i = 0; i < txnLength; i++) {
			completeTxnByte[index++] = dataBytes[i];
		}
		// **************** END: POPULATING THE REQUEST *********************//
		return completeTxnByte;
	}

	/**
	 * This method is used to validate the Format for the lower level data
	 * transmission protocol. The protocol would be as follows: <STX(1)> <LOD(2)>
	 * <DATA(LOD)> <ETX(1)> <LRC(1)> where LOD = LengthOfData
	 * 
	 * @param : 1) byte[] The complete data that we receive 2) int The length of
	 *        the data received. @ return: String The <Data> after stripping of
	 *        everythin else
	 */
	public byte[] unpackPGFormat(byte[] completeTxnByte, int bytesRead) {
		
		int totalLength = completeTxnByte.length;
		int index = 0;
		int txnLOD = 0;
		byte[] dataRetrieved = null;

		// Now detect the length of the transaction record
		byte[] lengthBytes = new byte[2];
		lengthBytes[0] = completeTxnByte[index++];
		lengthBytes[1] = completeTxnByte[index++];
		txnLOD = ByteConversionUtils.getIntFromByteArray(lengthBytes);
		logger.info("Length Of Data: " + txnLOD + ", BytesRead: " + bytesRead);

		// Now retrieve the real transaction data
		dataRetrieved = new byte[txnLOD];
		for(int i = 0; i < txnLOD; i++)
			dataRetrieved[i] = completeTxnByte[index++];

		return dataRetrieved;
	}

	/**
	 * Returns the configFilePath value
	 * 
	 * @return the configFilePath
	 */
	public String getConfigFilePath() {
		return configFilePath;
	}

	/**
	 * Set the configFilePath value
	 * 
	 * @param configFilePath
	 *          the configFilePath to set
	 */
	public void setConfigFilePath(String configFilePath) {
		this.configFilePath = configFilePath;
	}

	/**
	 * Returns the _length value
	 * 
	 * @return the _length
	 */
	public int get_length() {
		return _length;
	}

	/**
	 * Set the _length value
	 * 
	 * @param _length
	 *          the _length to set
	 */
	public void set_length(int _length) {
		this._length = _length;
	}

	/**
	 * Returns the _entireRequest value
	 * 
	 * @return the _entireRequest
	 */
	public String get_entireRequest() {
		return _entireRequest;
	}

	/**
	 * Set the _entireRequest value
	 * 
	 * @param _entireRequest
	 *          the _entireRequest to set
	 */
	public void set_entireRequest(String request) {
		_entireRequest = request;
	}

}