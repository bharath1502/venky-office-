package com.chatak.acquirer.admin.service;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import com.chatak.acquirer.admin.exception.ChatakAdminException;
import com.chatak.acquirer.admin.service.impl.BatchSchedularServiceImpl;
import com.chatak.pg.acq.dao.BatchSchedularDao;
import com.chatak.pg.bean.Response;
import com.chatak.pg.user.bean.DailyFundingReport;
import com.chatak.pg.user.bean.GetDailyFundingReportRequest;
import com.chatak.pg.user.bean.GetDailyFundingReportResponse;

@RunWith(MockitoJUnitRunner.class)
public class BatchSchedularServiceTest {

  @InjectMocks
  private BatchSchedularServiceImpl batchSchedularServiceImpl = new BatchSchedularServiceImpl();

  @Mock
  private BatchSchedularDao batchSchedularDao;

  @Mock
  private GetDailyFundingReportRequest dailyFundingReportRequest;

  @Mock
  private GetDailyFundingReportResponse dailyFundingReportResponse;

  @Mock
  private Response response;

  @Test
  public void testSearchDailyFundingReportDetails() throws ChatakAdminException {
    List<DailyFundingReport> list = new ArrayList<>();
    Mockito
        .when(batchSchedularDao
            .searchDailyFundingReportDetails(Matchers.any(GetDailyFundingReportRequest.class)))
        .thenReturn(list);
    dailyFundingReportResponse =
        batchSchedularServiceImpl.searchDailyFundingReportDetails(dailyFundingReportRequest);

  }

  @Test
  public void testSearchDailyFundingReportDetailsElse() throws ChatakAdminException {
    Mockito
        .when(batchSchedularDao
            .searchDailyFundingReportDetails(Matchers.any(GetDailyFundingReportRequest.class)))
        .thenReturn(null);
    dailyFundingReportResponse =
        batchSchedularServiceImpl.searchDailyFundingReportDetails(dailyFundingReportRequest);

  }

  @Test
  public void testSearchDailyFundingReportDetailsException() throws ChatakAdminException {
    Mockito
        .when(batchSchedularDao
            .searchDailyFundingReportDetails(Matchers.any(GetDailyFundingReportRequest.class)))
        .thenThrow(NullPointerException.class);
    dailyFundingReportResponse =
        batchSchedularServiceImpl.searchDailyFundingReportDetails(dailyFundingReportRequest);

  }

  @Test
  public void testFundingReportSchedular() {
    batchSchedularServiceImpl.fundingReportSchedular();
  }

  @Test
  public void testManualFundingReport() {
    Mockito.when(batchSchedularServiceImpl.manualFundingReport()).thenReturn(response);
  }
}
