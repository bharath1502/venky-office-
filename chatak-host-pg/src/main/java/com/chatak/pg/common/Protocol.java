package com.chatak.pg.common;

/**
 * @Comments : This interface used for get all transaction protocol constants
 */
public interface Protocol {

  public final static byte ENQ = 0x05; // enquiry

  public final static byte STX = 0x02; // Start of Message

  public final static byte ETX = 0x03; // End of Message

  public final static byte ESC = 0x1B; // Escape Character

  public final static byte FS = 0x1C; // Field Separator

  public final static byte GS = 0x1D; // Group Separator

  public final static byte RS = 0x1E; // Record Separator

  public final static byte US = 0x1F; // Unit Separator

  public final static byte ACK = 0x06; // acknowledgement

  public final static byte NAK = 0x15; // negative ACK

  public final static byte EOT = 0x04; // end of text

  public final static byte LRC = 0x09; // Longitudinal Redundancy Check w/o STX
  // ETX and Msg. Length

}