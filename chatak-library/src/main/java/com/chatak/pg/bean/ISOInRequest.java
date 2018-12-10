package com.chatak.pg.bean;

import org.jpos.iso.ISOException;
import org.jpos.iso.ISOMsg;


import com.chatak.pg.constants.ActionCode;
import com.chatak.pg.constants.PGConstants;
import com.chatak.pg.emv.util.ChatakTLVParser;
import com.chatak.pg.emv.util.EMVData;
import com.chatak.pg.exception.InvalidEMVDataFormatException;
import com.chatak.pg.exception.InvalidTLVDataFormatException;
import com.chatak.pg.exception.MagneticStripeParseException;
import com.chatak.pg.util.MagneticStripeCardUtil;
import com.chatak.pg.util.StringUtils;

public class ISOInRequest {
  


	// Message Type Indicator
	private String _MTI;

	private String _processingCode;

	// Primary account number (PAN) Field2;
	private String _cardNum;

	// Processing Code Field3
	// private String _processingCode;

	// Amount, transaction Field4;
	private Long _txnAmount;

	// Systems trace audit number Field11;
	private String _sysTraceNum;

	// Date, Expiration Field14;
	private String _expDate;

	// Track 2 data Field35;
	private String _track2;

	// Auth Transaction Reference Number _track37
	private String _authTxnRefNum;

	// AuthId of Auth Transaction _track38
	private String _authId;

	// Card acceptor terminal identification Field41;
	private String _terminalId;

	// Card acceptor identification code Field42;
	private String _merchantId;

	// Additional amounts Field54;
	private Long _adjustedTxnAmount;

	// Original amount/Host Authorised amount Field60;
	private Long _hostTxnAmount;

	// Invoice Number Field62;
	private String _invoiceNumber;

	// Is Chip transaction
	private Boolean _isChipTransaction = false;

	// EMV Data for Chip based transaction
	private EMVData emvData;

	// Is Txn is fall back
	private Boolean _isFallback = false;

	// Track-2 data Service Code
	private String _ServiceCode;

	// Response Code
	private String _field39;

	// Pos Entry Transaction
	private String _Field22;

	// ICC data
	private String _Field55;

	// Is Offline transaction
	private Boolean _isOfflineTransaction = false;
	
	private ISOMsg isoMsg;

	public ISOInRequest(ISOMsg isoMsg) throws ISOException,
			MagneticStripeParseException, InvalidTLVDataFormatException,
			InvalidEMVDataFormatException {
		
		this.isoMsg=isoMsg;

		if (StringUtils.isValidString((String) isoMsg.getValue(0))) {
			_MTI = (String) isoMsg.getValue(0);
		}

		if (StringUtils.isValidString((String) isoMsg.getValue(2))) {
			_cardNum = (String) isoMsg.getValue(2);
		}

		if (StringUtils.isValidString((String) isoMsg.getValue(3))) {
			_processingCode = (String) isoMsg.getValue(3);
		}

		if (StringUtils.isValidString((String) isoMsg.getValue(4))) {
			_txnAmount = Long.valueOf((String) isoMsg.getValue(4));
		}

		if (StringUtils.isValidString((String) isoMsg.getValue(11))) {
			_sysTraceNum = (String) isoMsg.getValue(11);
		}

		// Date, Expiration Field14;
		if (StringUtils.isValidString((String) isoMsg.getValue(14))) {
			_expDate = (String) isoMsg.getValue(14);
		}

		// POS Entry mode
		if (StringUtils.isValidString(isoMsg.getString(22))) {
			_Field22 = isoMsg.getString(22);

			/**
			 * Transaction message of fall back transactions are subject to
			 * below validations • If the transaction is a fall back
			 * transaction, POS entry mode shall be set to the proper value.
			 * Exact value is dependent on the Switch specifications • Track 2
			 * data shall contain the actual track 2 read from the card’s
			 * magnetic stripe • Field 55 shall not be present. • Field 52 may
			 * be present or not depending on whether the PIN is captured at the
			 * terminal or not.
			 */
			String _Field22First2 = _Field22.substring(0, 2);
			if (PGConstants.POS_ENTRY_MODE_CHIP_TXN_05.equals(_Field22First2)
					|| PGConstants.POS_ENTRY_MODE_CHIP_TXN_95
							.equals(_Field22First2)) {
				_isChipTransaction = true;
			} else if (PGConstants.POS_ENTRY_MODE_CHIP_FALLBACK_TXN_80
					.equals(_Field22First2)
					|| PGConstants.SERVICE_CODE_2.equals(_ServiceCode)
					|| PGConstants.SERVICE_CODE_6.equals(_ServiceCode)) {
				_isChipTransaction = false;
				_isFallback = true;
			}
		}

		// Track 2 data Field35;
		if (StringUtils.isValidString((String) isoMsg.getValue(35))) {
			_track2 = (String) isoMsg.getValue(35);

			MagneticStripeCardUtil magneticStripeCardUtil = new MagneticStripeCardUtil();
			magneticStripeCardUtil._parseTrack2(_track2);
			_cardNum = magneticStripeCardUtil.getPan();
			_expDate = magneticStripeCardUtil.getExpDate()
					+ magneticStripeCardUtil.getExpMonth();
			_ServiceCode = magneticStripeCardUtil.getServiceCode();

		}

		// Retrieval reference number Field37;
		if (StringUtils.isValidString((String) isoMsg.getValue(37))) {
			_authTxnRefNum = (String) isoMsg.getValue(37);
		}

		// Authorization identification Field38;
		if (StringUtils.isValidString((String) isoMsg.getValue(38))) {
			_authId = (String) isoMsg.getValue(38);
		}

		// Response code in request
		if (StringUtils.isValidString(isoMsg.getString(39))) {
			_field39 = isoMsg.getString(39);

			if (ActionCode.ERROR_CODE_Y1.equals(_field39)
					|| ActionCode.ERROR_CODE_Y3.equals(_field39)) {
				_isOfflineTransaction = true;
			} else if (ActionCode.ERROR_CODE_Z1.equals(_field39)
					|| ActionCode.ERROR_CODE_Z3.equals(_field39)) {
				_isOfflineTransaction = true;
			}
		}

		// Card acceptor terminal identification Field41;
		if (StringUtils.isValidString((String) isoMsg.getValue(41))) {
			_terminalId = (String) isoMsg.getValue(41);
		}

		// Card acceptor identification code Field42;
		if (StringUtils.isValidString((String) isoMsg.getValue(42))) {
			_merchantId = (String) isoMsg.getValue(42);
		}

		// Additional amounts Field54;
		if (StringUtils.isValidString((String) isoMsg.getValue(54))) {
			_adjustedTxnAmount = Long.valueOf((String) isoMsg.getValue(54));
		}

		// EMV Data Field55;
		if (StringUtils.isValidString(isoMsg.getString(55))) {
			// _Field55 =
			// ByteConversionUtils.hexToAsciiString(isoMsg.getString(55));
			_Field55 = isoMsg.getString(55);

			String _Field22First2 = _Field22.substring(1, 3);
			if (_Field22.contains("07") || PGConstants.POS_ENTRY_MODE_CHIP_TXN_05.equals(_Field22First2)
					|| PGConstants.POS_ENTRY_MODE_CHIP_TXN_95
							.equals(_Field22First2) || PGConstants.POS_ENTRY_MODE_CONTACT_LESS_TXN_91
              .equals(_Field22First2)) {
				_isChipTransaction = true;
			} else {
				/**
				 * Note: If field 55 is present and POS entry mode (Field-22) is
				 * not 05 or 95 then such transactions shall be declined.
				 */
				throw new InvalidEMVDataFormatException();
			}

			// Calling EMV parser
			emvData = new ChatakTLVParser(_Field55).getEMVData();
		}

		// Original amount/Host Authorised amount Field60;
		if (StringUtils.isValidString((String) isoMsg.getValue(60))) {
			_hostTxnAmount = Long.valueOf((String) isoMsg.getValue(60));
		}

		// Invoice Number Field62;
		if (StringUtils.isValidString((String) isoMsg.getValue(62))) {
			_invoiceNumber = (String) isoMsg.getValue(62);
		}

	}

	/**
	 * @return the _cardNum
	 */
	public String get_cardNum() {
		return _cardNum;
	}

	/**
	 * @return the _txnAmount
	 */
	public Long get_txnAmount() {
		return _txnAmount;
	}

	/**
	 * @return the _sysTraceNum
	 */
	public String get_sysTraceNum() {
		return _sysTraceNum;
	}

	/**
	 * @return the _expDate
	 */
	public String get_expDate() {
		return _expDate;
	}

	/**
	 * @return the _track2
	 */
	public String get_track2() {
		return _track2;
	}

	/**
	 * @return the _authTxnRefNum
	 */
	public String get_authTxnRefNum() {
		return _authTxnRefNum;
	}

	/**
	 * @return the _authId
	 */
	public String get_authId() {
		return _authId;
	}

	/**
	 * @return the _terminalId
	 */
	public String get_terminalId() {
		return _terminalId;
	}

	/**
	 * @return the _merchantId
	 */
	public String get_merchantId() {
		return _merchantId;
	}

	/**
	 * @return the _adjustedTxnAmount
	 */
	public Long get_adjustedTxnAmount() {
		return _adjustedTxnAmount;
	}

	/**
	 * @return the _hostTxnAmount
	 */
	public Long get_hostTxnAmount() {
		return _hostTxnAmount;
	}

	/**
	 * @return the _invoiceNumber
	 */
	public String get_invoiceNumber() {
		return _invoiceNumber;
	}

	/**
	 * @param _cardNum
	 *            the _cardNum to set
	 */
	public void set_cardNum(String _cardNum) {
		this._cardNum = _cardNum;
	}

	/**
	 * @param _txnAmount
	 *            the _txnAmount to set
	 */
	public void set_txnAmount(Long _txnAmount) {
		this._txnAmount = _txnAmount;
	}

	/**
	 * @param _sysTraceNum
	 *            the _sysTraceNum to set
	 */
	public void set_sysTraceNum(String _sysTraceNum) {
		this._sysTraceNum = _sysTraceNum;
	}

	/**
	 * @param _expDate
	 *            the _expDate to set
	 */
	public void set_expDate(String _expDate) {
		this._expDate = _expDate;
	}

	/**
	 * @param _track2
	 *            the _track2 to set
	 */
	public void set_track2(String _track2) {
		this._track2 = _track2;
	}

	/**
	 * @param _authTxnRefNum
	 *            the _authTxnRefNum to set
	 */
	public void set_authTxnRefNum(String _authTxnRefNum) {
		this._authTxnRefNum = _authTxnRefNum;
	}

	/**
	 * @param _authId
	 *            the _authId to set
	 */
	public void set_authId(String _authId) {
		this._authId = _authId;
	}

	/**
	 * @param _terminalId
	 *            the _terminalId to set
	 */
	public void set_terminalId(String _terminalId) {
		this._terminalId = _terminalId;
	}

	/**
	 * @param _merchantId
	 *            the _merchantId to set
	 */
	public void set_merchantId(String _merchantId) {
		this._merchantId = _merchantId;
	}

	/**
	 * @param _adjustedTxnAmount
	 *            the _adjustedTxnAmount to set
	 */
	public void set_adjustedTxnAmount(Long _adjustedTxnAmount) {
		this._adjustedTxnAmount = _adjustedTxnAmount;
	}

	/**
	 * @param _hostTxnAmount
	 *            the _hostTxnAmount to set
	 */
	public void set_hostTxnAmount(Long _hostTxnAmount) {
		this._hostTxnAmount = _hostTxnAmount;
	}

	/**
	 * @param _invoiceNumber
	 *            the _invoiceNumber to set
	 */
	public void set_invoiceNumber(String _invoiceNumber) {
		this._invoiceNumber = _invoiceNumber;
	}

	/**
	 * @return the _isChipTransaction
	 */
	public Boolean get_isChipTransaction() {
		return _isChipTransaction;
	}

	/**
	 * @param _isChipTransaction
	 *            the _isChipTransaction to set
	 */
	public void set_isChipTransaction(Boolean _isChipTransaction) {
		this._isChipTransaction = _isChipTransaction;
	}

	/**
	 * @return the emvData
	 */
	public EMVData getEmvData() {
		return emvData;
	}

	/**
	 * @param emvData
	 *            the emvData to set
	 */
	public void setEmvData(EMVData emvData) {
		this.emvData = emvData;
	}

	/**
	 * @return the _isFallback
	 */
	public Boolean get_isFallback() {
		return _isFallback;
	}

	/**
	 * @param _isFallback
	 *            the _isFallback to set
	 */
	public void set_isFallback(Boolean _isFallback) {
		this._isFallback = _isFallback;
	}

	/**
	 * @return the _field39
	 */
	public String get_field39() {
		return _field39;
	}

	/**
	 * @param _field39
	 *            the _field39 to set
	 */
	public void set_field39(String _field39) {
		this._field39 = _field39;
	}

	/**
	 * @return the _isOfflineTransaction
	 */
	public Boolean get_isOfflineTransaction() {
		return _isOfflineTransaction;
	}

	/**
	 * @param _isOfflineTransaction
	 *            the _isOfflineTransaction to set
	 */
	public void set_isOfflineTransaction(Boolean _isOfflineTransaction) {
		this._isOfflineTransaction = _isOfflineTransaction;
	}

	/**
	 * @return the _MTI
	 */
	public String get_MTI() {
		return _MTI;
	}

	/**
	 * @param _MTI
	 *            the _MTI to set
	 */
	public void set_MTI(String _MTI) {
		this._MTI = _MTI;
	}

	/**
	 * @return the _ServiceCode
	 */
	public String get_ServiceCode() {
		return _ServiceCode;
	}

	/**
	 * @param _ServiceCode
	 *            the _ServiceCode to set
	 */
	public void set_ServiceCode(String _ServiceCode) {
		this._ServiceCode = _ServiceCode;
	}

	/**
	 * @return the _Field55
	 */
	public String get_Field55() {
		return _Field55;
	}

	/**
	 * @param _Field55
	 *            the _Field55 to set
	 */
	public void set_Field55(String _Field55) {
		this._Field55 = _Field55;
	}

	/**
	 * @return the _processingCode
	 */
	public String get_processingCode() {
		return _processingCode;
	}

	/**
	 * @param _processingCode
	 *            the _processingCode to set
	 */
	public void set_processingCode(String _processingCode) {
		this._processingCode = _processingCode;
	}

	public String get_Field22() {
		return _Field22;
	}

	public void set_Field22(String _Field22) {
		this._Field22 = _Field22;
	}

	/**
	 * @return the isoMsg
	 */
	public ISOMsg getIsoMsg() {
		return isoMsg;
	}

	/**
	 * @param isoMsg the isoMsg to set
	 */
	public void setIsoMsg(ISOMsg isoMsg) {
		this.isoMsg = isoMsg;
	}

}
