/**
 * 
 */
package com.chatak.pg.user.bean;

import java.util.List;

/**
 * @Author: Girmiti Software
 * @Date: Sep 20, 2017
 * @Time: 2:14:01 PM
 * @Version: 1.0
 * @Comments: 
 *
 */
public class GetDailyFundingReportResponse extends Response {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  private List<DailyFundingReport> dailyFundingReport;

  private Integer totalResultCount;

  public List<DailyFundingReport> getDailyFundingReport() {
    return dailyFundingReport;
  }

  public void setDailyFundingReport(List<DailyFundingReport> dailyFundingReport) {
    this.dailyFundingReport = dailyFundingReport;
  }

  public Integer getTotalResultCount() {
    return totalResultCount;
  }

  public void setTotalResultCount(Integer totalResultCount) {
    this.totalResultCount = totalResultCount;
  }

}
