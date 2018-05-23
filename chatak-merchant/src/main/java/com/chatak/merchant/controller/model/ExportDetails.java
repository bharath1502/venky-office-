package com.chatak.merchant.controller.model;

import java.util.List;

import com.chatak.pg.enums.ExportType;

public class ExportDetails {



  private String reportName;
  private String dateFormatter;
  private String headerMessageProperty;
  private ExportType exportType;
  private List<String> headerList;
  private List<Object[]> fileData;
  private Integer excelStartRowNumber;

  public String getDateFormatter() {
    return dateFormatter;
  }

  public void setDateFormatter(String dateFormatter) {
    this.dateFormatter = dateFormatter;
  }

  public String getReportName() {
    return reportName;
  }

  public void setReportName(String reportName) {
    this.reportName = reportName;
  }

  public ExportType getExportType() {
    return exportType;
  }

  public void setExportType(ExportType exportType) {
    this.exportType = exportType;
  }

  public List<Object[]> getFileData() {
    return fileData;
  }

  public void setFileData(List<Object[]> fileData) {
    this.fileData = fileData;
  }

  public List<String> getHeaderList() {
    return headerList;
  }

  public void setHeaderList(List<String> headerList) {
    this.headerList = headerList;
  }

  public Integer getExcelStartRowNumber() {
    return excelStartRowNumber;
  }

  public void setExcelStartRowNumber(Integer excelStartRowNumber) {
    this.excelStartRowNumber = excelStartRowNumber;
  }

  public String getHeaderMessageProperty() {
    return headerMessageProperty;
  }

  public void setHeaderMessageProperty(String headerMessageProperty) {
    this.headerMessageProperty = headerMessageProperty;
  }

}
