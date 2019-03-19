package com.chatak.pg.user.bean;


import java.util.List;

import com.chatak.pg.model.FaqManagementRequest;

public class FaqManagementResponse extends Response{
  
  private static final long serialVersionUID = 7930200091065374559L;

  private List<FaqManagementRequest> faqManagementList;

  public List<FaqManagementRequest> getFaqManagementList() {
    return faqManagementList;
  }

  public void setFaqManagementList(List<FaqManagementRequest> faqManagementRequestList) {
     this.faqManagementList=faqManagementRequestList;
    
  }

}
