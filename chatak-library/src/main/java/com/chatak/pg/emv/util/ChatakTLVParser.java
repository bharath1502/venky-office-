package com.chatak.pg.emv.util;

import java.lang.reflect.Field;
import java.util.Enumeration;

import org.jpos.iso.ISOException;
import org.jpos.iso.ISOUtil;
import org.jpos.tlv.TLVList;
import org.jpos.tlv.TLVMsg;

import com.chatak.pg.exception.InvalidTLVDataFormatException;
import com.chatak.pg.util.ByteConversionUtils;
import com.chatak.pg.util.PGUtils;

/**
 * The ChatakTLVParser class is a Tag Length Value (TLV) parser. This class
 * helps to parse the TLV data, constructs JPOS {@link TLVList} and construct
 * {@link EMVData}
 * 
 * @author Girmiti Software
 * @date 05-Dec-2014 11:57:46 AM
 * @version 1.0
 */
public final class ChatakTLVParser implements ChatakEMVTags {

  private TLVList tlvList;

  private String tlvHex;

  private EMVData emvData = null;

  public ChatakTLVParser(String tlvHex) throws InvalidTLVDataFormatException {
    this.tlvHex = tlvHex;
    parse();
  }

  /**
   * Method to parse TLV Data
   * 
   * @param tlvHex
   * @return
   */
  private void parse() throws InvalidTLVDataFormatException {
    if(null == tlvHex || tlvHex.trim().length() < 3) {
      throw new InvalidTLVDataFormatException("TLV data is null or empty");
    }
    else {
      try {
        tlvList = new TLVList();
        tlvHex = tlvHex.trim();
        
        int EMV_DATA_LEN_BYTES = 0; //Skipping Field-55 EMV data length (First 2 bytes - 4 characters)
        
        byte[] byteArray = ByteConversionUtils.HexStringToByteArray(tlvHex.substring(EMV_DATA_LEN_BYTES));
        tlvList.unpack(byteArray, 0);
      }
      catch(NumberFormatException e) {
        throw new InvalidTLVDataFormatException("Invalid TLV Data format");
      }
      catch(ISOException e) {
        throw new InvalidTLVDataFormatException("Invalid TLV Data format");
      }
    }
  }

  /**
   * Method to get Value on Tag
   * 
   * @param tagName
   * @return
   */
  public String getValueOnTag(String tagName) {
    try {
      Field field = ChatakEMVTags.class.getDeclaredField(tagName);
      TLVMsg tlvMsg = tlvList.find((Integer) field.get(null));
      if(null != tlvMsg) {
        return ISOUtil.hexString(tlvMsg.getValue());
      }
    }
    catch(Exception e) {
      e.printStackTrace();
    }
    return null;
  }

  /**
   * Get EMV data object, the setting the EMV data from TLVList and returns the
   * {@link EMVData} object
   * 
   * @return
   */
  @SuppressWarnings("unchecked")
  public EMVData getEMVData() {
    if(null == emvData) {
      System.out.println("EMV Data Null...");
      emvData = new EMVData();
      try {
        Enumeration<TLVMsg> enumeration = tlvList.elements();
        while(enumeration.hasMoreElements()) {
          TLVMsg tlvMsg2 = enumeration.nextElement();
          setTagValue(tlvMsg2.getTag(), ISOUtil.hexString(tlvMsg2.getValue()));
        }
      }
      catch(Exception e) {
        e.printStackTrace();
      }
    }
    return emvData;
  }

  /**
   * unpack a message with a starting offset
   * 
   * @param buf
   *          - raw message
   * @param offset
   *          theoffset
   * @throws org.jpos.iso.ISOException
   */
  public void unpack(String hexData) throws ISOException {
    String newRaw = hexData;
    for(int k = ChatakEMVTags.EMV_TAGS.length-1; k >= 0; k--) {
      newRaw = parseTLV(newRaw, ChatakEMVTags.EMV_TAGS[k]);
    }

  }
  
  
  /**
   * Return the next TAG
   * 
   * @return tag
   */
  private String parseTLV(String raw, String tagKey) {
    String newRaw = raw;
    int index = raw.indexOf(tagKey);
    String value = "";
    String len = "";
    if(index >= 0) {
      len = raw.substring(index + tagKey.length(), index + tagKey.length() + 2);
      value = raw.substring(index + tagKey.length() + 2, index + tagKey.length() + 2 + (Integer.parseInt(len) * 2));
      String[] splitAr = raw.split((tagKey + len + value));
      newRaw = ((splitAr.length > 0) ? splitAr[0] : "") + ((splitAr.length > 1) ? splitAr[1] : "");
      setTagValue(Integer.parseInt(tagKey, 16), value);
    }
    return newRaw;
  }

  /**
   * Method to set Tag value into EMVData Object
   * 
   * @param tag
   * @param tagValue
   */
  private void setTagValue(int tag, String tagValue) {
    switch(tag) {
      case ISSUER_SCRIPT_TEMPLATE_1:
        emvData.setIst_1(tagValue);
        break;
      case ISSUER_SCRIPT_TEMPLATE:
        emvData.setIst(tagValue);
        break;
      case AIP:
        emvData.setAip(tagValue);
        break;
      case AID:
        emvData.setAid(tagValue);
        break;
      case IAD:
        emvData.setIad(tagValue);
        break;
      case TVR:
        emvData.setTvr(tagValue);
        break;
      case APPLICATION_EXPIRATION_DATE:
        emvData.setAed(tagValue);
        break;
      case TRANSACTION_DATE:
        emvData.setTxnDate(tagValue);
        break;
      case TRANSACTION_STATUS_INFORMATION:
        emvData.setTxnStatusInfo(tagValue);
        break;
      case TRANSACTION_TYPE:
        emvData.setTxnType(tagValue);
        break;
      case TXN_CURRENCY_CODE:
        emvData.setTxnCurrency(tagValue);
        break;
      case PSN:
        emvData.setPsn(tagValue);
        break;
      case AMOUNT:
        emvData.setAmount(tagValue);
        break;
      case AMOUNT_OTHER:
        emvData.setAmountOther(tagValue);
        break;
      case APPLICATION_VERSION_NUMBER:
        emvData.setAvn(tagValue);
        break;
      case ISSUER_APPLICATION_DATA:
        emvData.setIssuerApplicationData(tagValue);
        break;
      case TERMINAL_COUNTRY_CODE:
        emvData.setTcc(tagValue);
        break;
      case IFD:
        emvData.setIfd(PGUtils.convertHexToString(tagValue));
        break;
      case APPLICATION_CRYPTOGRAM:
        emvData.setAcr(tagValue);
        break;
      case CRYPTOGRAM_INFORMATION_DATA:
        emvData.setCid(tagValue);
        break;
      case TERMINAL_CAPABILITIES:
        emvData.setTc(tagValue);
        break;
      case CVMR:
        emvData.setCvmr(tagValue);
        break;
      case TERMINAL_TYPE:
        emvData.setTt(tagValue);
        break;
      case APPLICATION_TRANSACTION_COUNTER:
        emvData.setAtc(tagValue);
        break;
      case UNPREDICTABLE_NUMBER:
        emvData.setUnPredictableNum(tagValue);
        break;
      case TSN:
        emvData.setTsn(tagValue);
        break;
      case TCC:
        emvData.setTcc(tagValue);
        break;
      case ISR:
        emvData.setIsr(tagValue);
        break;
      case FCI:
        emvData.setFci(tagValue);
        break;
      case FCIP:
        emvData.setFcip(tagValue);
        break;
      case LAN:
        emvData.setLan(tagValue);
        break;
      default:
        break;
    }
  }

}
