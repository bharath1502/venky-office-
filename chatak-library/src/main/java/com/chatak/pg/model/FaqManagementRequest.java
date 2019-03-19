package com.chatak.pg.model;

import com.chatak.pg.bean.SearchRequest;

public class FaqManagementRequest extends SearchRequest {
  
  private static final long serialVersionUID = 1L;

  private Long categoryId;

  private Long moduleId;

  private Long categoryMappingId;
  
  private String categoryName;

  private String questionName;

  private String moduleName;

  private String questionAnswer;

  private Long faqId;

  private String status;

  public Long getCategoryId() {
    return categoryId;
  }

  public void setCategoryId(Long categoryId) {
    this.categoryId = categoryId;
  }

  public Long getModuleId() {
    return moduleId;
  }

  public void setModuleId(Long moduleId) {
    this.moduleId = moduleId;
  }

  public Long getCategoryMappingId() {
    return categoryMappingId;
  }

  public void setCategoryMappingId(Long categoryMappingId) {
    this.categoryMappingId = categoryMappingId;
  }

  public String getCategoryName() {
    return categoryName;
  }

  public void setCategoryName(String categoryName) {
    this.categoryName = categoryName;
  }

  public String getQuestionName() {
    return questionName;
  }

  public void setQuestionName(String questionName) {
    this.questionName = questionName;
  }

  public String getModuleName() {
    return moduleName;
  }

  public void setModuleName(String moduleName) {
    this.moduleName = moduleName;
  }

  public String getQuestionAnswer() {
    return questionAnswer;
  }

  public void setQuestionAnswer(String questionAnswer) {
    this.questionAnswer = questionAnswer;
  }

  public Long getFaqId() {
    return faqId;
  }

  public void setFaqId(Long faqId) {
    this.faqId = faqId;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status =status;
    
  }

}
