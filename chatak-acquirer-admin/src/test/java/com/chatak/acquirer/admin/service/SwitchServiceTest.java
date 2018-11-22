package com.chatak.acquirer.admin.service;

import java.util.Arrays;

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

import com.chatak.acquirer.admin.constants.TestConstants;
import com.chatak.acquirer.admin.exception.ChatakAdminException;
import com.chatak.acquirer.admin.service.impl.SwitchServiceImpl;
import com.chatak.pg.acq.dao.SwitchDao;
import com.chatak.pg.acq.dao.model.PGSwitch;
import com.chatak.pg.constants.ActionErrorCode;
import com.chatak.pg.model.Switch;
import com.chatak.pg.user.bean.SwitchRequest;
import com.chatak.pg.user.bean.SwitchResponse;

@RunWith(MockitoJUnitRunner.class)
public class SwitchServiceTest {

  @InjectMocks
  private SwitchServiceImpl switchService = new SwitchServiceImpl();

  @Mock
  private SwitchDao switchDao;

  @Mock
  private SwitchRequest switchRequest = new SwitchRequest();

  @Before
  public void setup() {
    MockitoAnnotations.initMocks(this);
  }

  @Test
  public void testaddSwitchInformation() throws ChatakAdminException {

    SwitchResponse addSwitchResponse = Mockito.mock(SwitchResponse.class);
    Switch switchInfo = Mockito.mock(Switch.class);

    Mockito
        .when(
            switchDao.addSwitchInformation(Matchers.any(SwitchRequest.class), Matchers.anyString()))
        .thenReturn(addSwitchResponse);
    Mockito.when(addSwitchResponse.getErrorCode()).thenReturn(ActionErrorCode.ERROR_CODE_00);


    addSwitchResponse = switchService.addSwitchInformation(switchInfo, "1");
    Assert.assertNotNull(addSwitchResponse);
  }

  @Test
  public void testsearchSwitchInformation() throws ChatakAdminException {
    SwitchResponse addSwitchResponse = Mockito.mock(SwitchResponse.class);

    Mockito.when(switchDao.searchSwitchInformation(Matchers.any(SwitchRequest.class)))
        .thenReturn(Arrays.asList(switchRequest));
    Mockito.when(switchRequest.getStatus()).thenReturn(0);
    Mockito.when(addSwitchResponse.getErrorCode()).thenReturn(ActionErrorCode.ERROR_CODE_00);


    addSwitchResponse = switchService.searchSwitchInformation(switchRequest);
    Assert.assertNotNull(addSwitchResponse);
  }

  @Test
  public void testupdateSwitchInformation() {
    SwitchResponse addSwitchResponse = Mockito.mock(SwitchResponse.class);
    switchRequest = Mockito.mock(SwitchRequest.class);

    Mockito.when(switchDao.updateSwitchInformation(switchRequest, "1"))
        .thenReturn(addSwitchResponse);
    addSwitchResponse = switchService.updateSwitchInformation(switchRequest, "1");
    Assert.assertNotNull(addSwitchResponse);
  }

  @Test
  public void testgetSwtichInformationById() {
    PGSwitch pgSwitch = Mockito.mock(PGSwitch.class);

    Mockito.when(switchDao.getSwtichInformationById(TestConstants.TWO_LONG)).thenReturn(pgSwitch);
    switchRequest = switchService.getSwtichInformationById(TestConstants.TWO_LONG);
    Assert.assertNotNull(switchRequest);
  }

  @Test
  public void testgetSwitchByStatus() {
    PGSwitch pgSwitch = Mockito.mock(PGSwitch.class);

    Mockito.when(switchDao.getAllSwitchNamesByStatus(0)).thenReturn(Arrays.asList(pgSwitch));
    SwitchResponse addSwitchResponse = switchService.getSwitchByStatus(0);
    Assert.assertNotNull(addSwitchResponse);
  }
}
