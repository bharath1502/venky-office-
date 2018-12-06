package com.chatak.pg.user.bean;

import java.util.List;

import com.chatak.pg.bean.Response;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProgramManagerResponse extends Response {

  private static final long serialVersionUID = -8939778823660689477L;

  private List<ProgramManagerRequest> programManagersList;

  public List<ProgramManagerRequest> getProgramManagersList() {
    return programManagersList;
  }

  public void setProgramManagersList(List<ProgramManagerRequest> programManagersList) {
    this.programManagersList = programManagersList;
  }
}
