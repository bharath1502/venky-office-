/**
 * 
 */
package com.chatak.pg.util;

import java.util.Arrays;
import java.util.Random;

/**
 * << Add Comments Here >>
 *
 * @author Girmiti Software
 * @date 22-Dec-2014 12:35:25 pm
 * @version 1.0
 */
public class TransactionId {
  /**
   * RFC5289 Transaction ID length.
   */
  public static final int RFC5389_TRANSACTION_ID_LENGTH = 12;

  /**
   * RFC3489 Transaction ID length.
   */
  public static final int RFC3489_TRANSACTION_ID_LENGTH = 16;

  /**
   * The id itself
   */
  private final byte[] transactionID;

  /**
   * The object to use to generate the rightmost 8 bytes of the id.
   */
  private static final Random random = new Random(System.currentTimeMillis());

  /**
   * A hashcode for hashtable storage.
   */
  private int hashCode = 0;

  /**
   * Limits access to <tt>TransactionId</tt> instantiation.
   */
  private TransactionId() {
    this(false);
  }

  /**
   * Limits access to <tt>TransactionId</tt> instantiation.
   *
   * @param rfc3489Compatibility
   *          true to create a RFC3489 transaction ID
   */
  private TransactionId(boolean rfc3489Compatibility) {
    transactionID = new byte[rfc3489Compatibility ? RFC3489_TRANSACTION_ID_LENGTH : RFC5389_TRANSACTION_ID_LENGTH];
  }

  /**
   * Creates a transaction id object.The transaction id itself is generated
   * using the following algorithm: The first 6 bytes of the id are given the
   * value of <tt>System.currentTimeMillis()</tt>. Putting the right most bits
   * first so that we get a more optimized equals() method.
   *
   * @return A <tt>TransactionId</tt> object with a unique transaction id.
   */
  public static TransactionId createNewTransactionID() {
    TransactionId tid = new TransactionId();

    generateTransactionID(tid, RFC5389_TRANSACTION_ID_LENGTH);
    return tid;
  }

  /**
   * Creates a RFC3489 transaction id object.The transaction id itself is
   * generated using the following algorithm: The first 8 bytes of the id are
   * given the value of <tt>System.currentTimeMillis()</tt>. Putting the right
   * most bits first so that we get a more optimized equals() method.
   *
   * @return A <tt>TransactionId</tt> object with a unique transaction id.
   */
  public static TransactionId createNewRFC3489TransactionID() {
    TransactionId tid = new TransactionId(true);

    generateTransactionID(tid, RFC3489_TRANSACTION_ID_LENGTH);
    return tid;
  }

  /**
   * Generates a random transaction ID
   *
   * @param tid
   *          transaction ID
   * @param nb
   *          number of bytes to generate
   */
  private static void generateTransactionID(TransactionId tid, int nb) {
    long left = System.currentTimeMillis();// the first nb/2 bytes of the id
    long right = random.nextLong();// the last nb/2 bytes of the id
    int b = nb / 2;

    for(int i = 0; i < b; i++) {
      tid.transactionID[i] = (byte) ((left >> (i * 8)) & 0xFFl);
      tid.transactionID[i + b] = (byte) ((right >> (i * 8)) & 0xFFl);
    }

    // calculate hashcode for Hashtable storage.
    tid.hashCode = (tid.transactionID[3] << 24 & 0xFF000000) | (tid.transactionID[2] << 16 & 0x00FF0000)
                   | (tid.transactionID[1] << 8 & 0x0000FF00) | (tid.transactionID[0] & 0x000000FF);
  }

  /**
   * Returns the transaction id byte array (length 12 or 16 if RFC3489
   * compatible).
   *
   * @return the transaction ID byte array.
   */
  public byte[] getBytes() {
    return transactionID;
  }

  /**
   * If the transaction is compatible with RFC3489 (16 bytes).
   *
   * @return true if transaction ID is compatible with RFC3489
   */
  public boolean isRFC3489Compatible() {
    return (transactionID.length == 16);
  }

  /**
   * Compares two TransactionId objects.
   * 
   * @param obj
   *          the object to compare with.
   * @return true if the objects are equal and false otherwise.
   */
  public boolean equals(Object obj) {
    if(this == obj)
      return true;
    if(!(obj instanceof TransactionId))
      return false;

    byte targetBytes[] = ((TransactionId) obj).transactionID;

    return Arrays.equals(transactionID, targetBytes);
  }

  /**
   * Compares the specified byte array with this transaction id.
   * 
   * @param targetID
   *          the id to compare with ours.
   * @return true if targetID matches this transaction id.
   */
  public boolean equals(byte[] targetID) {
    return Arrays.equals(transactionID, targetID);
  }

  /**
   * Returns the first four bytes of the transactionID to ensure proper
   * retrieval from hashtables.
   * 
   * @return the hashcode of this object - as advised by the Java Platform
   *         Specification
   */
  public int hashCode() {
    return hashCode;
  }

  /**
   * Returns a string representation of the ID
   *
   * @return a hex string representing the id
   */
  public String toString() {
    return TransactionId.toString(transactionID);
  }

  /**
   * Returns a string representation of the ID
   *
   * @param transactionID
   *          the transaction ID to convert into <tt>String</tt>.
   * @return a hex string representing the id
   */
  public static String toString(byte[] transactionID) {
    StringBuilder idStr = new StringBuilder();

    idStr.append("0x");
    for(int i = 0; i < transactionID.length; i++) {

      if((transactionID[i] & 0xFF) <= 15)
        idStr.append("0");

      idStr.append(Integer.toHexString(transactionID[i] & 0xFF).toUpperCase());
    }

    return idStr.toString();
  }
  
  public static void main(String[] args) {
    TransactionId id = new TransactionId();
    System.out.println(createNewTransactionID());
  }

}
