/**
 * 
 */
package com.chatak.pay.util;

import org.apache.log4j.Logger;

import com.chatak.pay.controller.model.CardData;
import com.chatak.pay.service.impl.VaultServiceImpl;

/**
 * << Add Comments Here >>
 * 
 * @author Girmiti Software
 * @date 26-May-2015 3:00:56 PM
 * @version 1.0
 */
public class CardTrackParser {

  private String track;

  private String track1;

  private String track2;

  private CardData cardData;

  /**
   * @param track
   * @throws IllegalArgumentException
   */
  public CardTrackParser(String track) throws IllegalArgumentException {
    if(null == track || "".equals(track.trim())) {
      throw new IllegalArgumentException();
    }
    this.track = track;
    this.cardData = new CardData();
    init();
  }

  /**
   * @throws IllegalArgumentException
   */
  private void init() throws IllegalArgumentException {
    String sTrackData = track;
    // --- Determine the presence of special characters
    int nHasTrack1 = track.indexOf('^');
    int nHasTrack2 = track.indexOf('=');
    boolean bHasTrack1 = false;
    boolean bHasTrack2 = false;

    // --- Set boolean values based off of character presence
    if(nHasTrack1 > 0) {
      bHasTrack1 = true;
    }
    if(nHasTrack2 > 0) {
      bHasTrack2 = true;
    }

    // --- Test messages

    // --- Initialize
    boolean bTrack1_2 = false;
    boolean bTrack1 = false;
    boolean bTrack2 = false;

    // --- Determine tracks present
    bTrack1_2 = validateForTrack(bHasTrack1, bHasTrack2);
    if((bHasTrack1) && (!bHasTrack2)) {
      bTrack1 = true;
    }
    if((!bHasTrack1) && (bHasTrack2)) {
      bTrack2 = true;
    }

    if(bTrack1_2) {

      sTrackData = setCardData();

    }

    // -----------------------------------------------------------------------------
    // --- Track 1 only cards
    // --- Ex:
    // B1234123412341234^CardUser/John^030510100000019301000000877000000?
    // -----------------------------------------------------------------------------
    if(bTrack1) {

      String strCutUpSwipe = track;
      String[] arrayStrSwipe = strCutUpSwipe.split("\\^");

      if(arrayStrSwipe.length > Integer.parseInt("2")) {
        this.cardData.setCardNumber(stripAlpha(arrayStrSwipe[0].substring(1, arrayStrSwipe[0].length())));
        this.cardData.setCardHolderName(arrayStrSwipe[1]);
        this.cardData.setExpDate(arrayStrSwipe[Integer.parseInt("2")].substring(0, Integer.parseInt("2")) + arrayStrSwipe[Integer.parseInt("2")].substring(Integer.parseInt("2"), Integer.parseInt("4")));

        // --- Different card swipe readers include or exclude the % in
        // --- the front of the track data - when it's there, there are
        // --- problems with parsing on the part of credit cards processor - so
        // strip it off
        if("%".equals(sTrackData.substring(0, 1))) {
          this.track1 = sTrackData = sTrackData.substring(1, sTrackData.length());
        }

        // --- Add track 2 data to the string for processing reasons
        // if not present
        this.track2 = ";" + cardData.getCardNumber() + "=" + cardData.getExpDate() + "111111111111?";
        sTrackData = sTrackData + this.track2;

        // --- parse name field into first/last names
        int nameDelim = cardData.getCardHolderName().indexOf("/");
        validateNameDelim(nameDelim);

      }
      else 
      {
        throw new IllegalArgumentException();
      }
    }

    // -----------------------------------------------------------------------------
    // --- Track 2 only cards
    // --- Ex: 1234123412341234=0305101193010877?
    // -----------------------------------------------------------------------------
    if(bTrack2) {

      int nSeperator = track.indexOf('=');

      this.cardData.setCardNumber(stripAlpha(track.substring(1, nSeperator)));
      this.cardData.setExpDate(track.substring(nSeperator+1, nSeperator + Integer.parseInt("5")));

      this.track2 = ";" + cardData.getCardNumber() + "=" + cardData.getExpDate() + "111111111111?";
      
      // --- Different card swipe readers include or exclude the % in the front
      // of the track data - when it's there,
      // --- there are problems with parsing on the part of credit cards
      // processor - so strip it off
      lengthValidation(sTrackData);

    }

    // -----------------------------------------------------------------------------
    // --- No Track Match
    // -----------------------------------------------------------------------------
    validateForException(bTrack1_2, bTrack1, bTrack2);

  }

  private boolean validateForTrack(boolean bHasTrack1, boolean bHasTrack2) {
    boolean bTrack1_2 = false;
    if((bHasTrack1) && (bHasTrack2)) {
      bTrack1_2 = true;
    }
    return bTrack1_2;
  }

  private void validateForException(boolean bTrack1_2, boolean bTrack1, boolean bTrack2) {
    if(((!bTrack1_2) && (!bTrack1) && (!bTrack2))) {
      throw new IllegalArgumentException();
    }
  }

  private void lengthValidation(String sTrackData) {
    if("%".equals(sTrackData.substring(0, 1))) {
      String length = sTrackData.substring(1, sTrackData.length());
      Logger.getLogger(CardTrackParser.class).info("Length : " + length);
    }
  }

  private void validateNameDelim(int nameDelim) {
    if(nameDelim != -1) {
      this.cardData.setCardHolderName(cardData.getCardHolderName().substring(nameDelim + 1) + " "
                                      + cardData.getCardHolderName().substring(0, nameDelim));
    }
  }

  private String setCardData() {
    String sTrackData = track;
    String strCutUpSwipe = track;
    String[] arrayStrSwipe = strCutUpSwipe.split("\\^");

    if(arrayStrSwipe.length > Integer.parseInt("2")) {
      this.cardData.setCardNumber(stripAlpha(arrayStrSwipe[0].substring(1, arrayStrSwipe[0].length())));
      this.cardData.setCardHolderName(arrayStrSwipe[1]);
      this.cardData.setExpDate(arrayStrSwipe[Integer.parseInt("2")].substring(0,Integer.parseInt("2")) + arrayStrSwipe[Integer.parseInt("2")].substring(Integer.parseInt("2"), Integer.parseInt("4")));

      // --- Different card swipe readers include or exclude the % in the
      // front of the track data - when it's there, there are
      // --- problems with parsing on the part of credit cards processor - so
      // strip it off
      if("%".equals(sTrackData.substring(0, 1))) {
        sTrackData = sTrackData.substring(1, sTrackData.length());
      }

      int track2sentinel = sTrackData.indexOf(';');
      if(track2sentinel != -1) {
        this.track1 = sTrackData.substring(0, track2sentinel);
        this.track2 = sTrackData.substring(track2sentinel);
      }

      // --- parse name field into first/last names
      int nameDelim = cardData.getCardHolderName().indexOf("/");
      validateNameDelim(nameDelim);
    }
    return sTrackData;
  }

  private String stripAlpha(String sInput) {
    if(sInput == null)
      return "";
    return sInput.replaceAll("[^0-9]", "");
  }

  /**
   * @return the track
   */
  public String getTrack() {
    return track;
  }

  /**
   * @param track
   *          the track to set
   */
  public void setTrack(String track) {
    this.track = track;
  }

  /**
   * @return the cardData
   */
  public CardData getCardData() {
    return cardData;
  }

  /**
   * @param cardData
   *          the cardData to set
   */
  public void setCardData(CardData cardData) {
    this.cardData = cardData;
  }

  /**
   * @return the track1
   */
  public String getTrack1() {
    return track1;
  }

  /**
   * @param track1
   *          the track1 to set
   */
  public void setTrack1(String track1) {
    this.track1 = track1;
  }

  /**
   * @return the track2
   */
  public String getTrack2() {
    return track2;
  }

  /**
   * @param track2
   *          the track2 to set
   */
  public void setTrack2(String track2) {
    this.track2 = track2;
  }

}