/**
 * 
 */
package com.chatak.acquirer.admin.model;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import com.chatak.pg.model.CAPublicKeysDTO;

/**
 *
 * << Add Comments Here >>
 *
 * @author Girmiti Software
 * @date Dec 28, 2017 5:39:29 PM
 * @version 1.0
 */
public class CAPublicKeysResponseTest {

  private CAPublicKeysResponse response;

  @Mock
  List<CAPublicKeysDTO> caPublicKeysList;

  @Before
  public void init() {
    response = new CAPublicKeysResponse();
    response.setCaPublicKeysList(caPublicKeysList);
  }

  @Test
  public void testCAPublicKeysResponse() {
    Assert.assertEquals(caPublicKeysList, response.getCaPublicKeysList());
  }
}
