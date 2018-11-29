/**
 * 
 */
package com.chatak.acquirer.admin.model;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

/**
 *
 * << Add Comments Here >>
 *
 * @author Girmiti Software
 * @date Dec 28, 2017 7:15:24 PM
 * @version 1.0
 */
public class TerminalSearchResponseTest {

  private TerminalSearchResponse response;

  @Mock
  private List<Terminals> terminalList;

  @Before
  public void init() {
    response = new TerminalSearchResponse();
    response.setTerminalList(terminalList);
  }

  @Test
  public void testTerminalSearchResponse() {
    Assert.assertEquals(terminalList, response.getTerminalList());
  }

}
