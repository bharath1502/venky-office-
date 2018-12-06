package com.chatak.acquirer.admin.service;

import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.dao.DataAccessException;

import com.chatak.acquirer.admin.constants.TestConstants;
import com.chatak.acquirer.admin.exception.ChatakAdminException;
import com.chatak.acquirer.admin.model.CAPublicKeysResponse;
import com.chatak.acquirer.admin.model.Response;
import com.chatak.acquirer.admin.service.impl.CAPublicKeysServiceImpl;
import com.chatak.pg.acq.dao.CAPublicKeysDao;
import com.chatak.pg.acq.dao.model.PGCaPublicKeys;
import com.chatak.pg.model.CAPublicKeysDTO;

@RunWith(MockitoJUnitRunner.class)
public class CAPublicKeysServiceTest {
  @InjectMocks
  private CAPublicKeysServiceImpl CAPublicKeysService = new CAPublicKeysServiceImpl();

  @Mock
  private CAPublicKeysDTO caPublicKeysDTO;

  @Mock
  private CAPublicKeysDao caPublicKeysDao;

  @Before
  public void setup() {
    MockitoAnnotations.initMocks(this);
  }

  @Test
  public void createCAPublicKeys() throws ChatakAdminException {
    Response response = Mockito.mock(Response.class);
    caPublicKeysDTO = Mockito.mock(CAPublicKeysDTO.class);
    PGCaPublicKeys caPublicKeysDaoDetails = Mockito.mock(PGCaPublicKeys.class);

    Mockito.when(caPublicKeysDao.createCAPublicKeys(Matchers.any(PGCaPublicKeys.class)))
        .thenReturn(caPublicKeysDaoDetails);
    Mockito.when(response.getErrorCode()).thenReturn(TestConstants.SUCCESS_CODE);

    response = CAPublicKeysService.createCAPublicKeys(caPublicKeysDTO);
    Assert.assertNotNull(response);

  }

  @Test
  public void updateCAPublicKeys() throws ChatakAdminException {

    Response response = Mockito.mock(Response.class);
    PGCaPublicKeys caPublicKeysDaoDetails = Mockito.mock(PGCaPublicKeys.class);

    Mockito.when(caPublicKeysDao.updateCAPublicKeys(Matchers.any(PGCaPublicKeys.class)))
        .thenReturn(caPublicKeysDaoDetails);
    Mockito.when(response.getErrorCode()).thenReturn(TestConstants.SUCCESS_CODE);

    response = CAPublicKeysService.updateCAPublicKeys(caPublicKeysDTO);
    Assert.assertNotNull(response);

  }

  @Test
  public void caPublicKeysById() {
    PGCaPublicKeys pgCaPublicKeys = Mockito.mock(PGCaPublicKeys.class);

    Mockito.when(caPublicKeysDao.caPublicKeysById(TestConstants.TWO_LONG)).thenReturn(pgCaPublicKeys);
    pgCaPublicKeys = CAPublicKeysService.caPublicKeysById(TestConstants.TWO_LONG);
    Assert.assertNotNull(pgCaPublicKeys);
  }

  @Test
  public void searchCAPublicKeys() throws DataAccessException, ChatakAdminException {
    CAPublicKeysResponse caPublicKeysResponse = Mockito.mock(CAPublicKeysResponse.class);
    List<CAPublicKeysDTO> caPublicKeysResponseList = Arrays.asList(caPublicKeysDTO);


    Mockito.when(caPublicKeysDao.searchCAPublicKeys(Matchers.any(CAPublicKeysDTO.class)))
        .thenReturn(caPublicKeysResponseList);
    Mockito.when(caPublicKeysDTO.getStatus()).thenReturn("0");
    Mockito.when(caPublicKeysResponse.getErrorCode()).thenReturn(TestConstants.STATUS_CODE_SUCCESS);

    caPublicKeysResponse = CAPublicKeysService.searchCAPublicKeys(caPublicKeysDTO);

    Assert.assertNotNull(caPublicKeysResponse);

  }

}
