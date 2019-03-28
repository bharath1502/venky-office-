package com.chatak.pg.util.validator;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.support.RequestDataValueProcessor;

public class CSRFRequestDataValueProcessor implements RequestDataValueProcessor {

  private String httpMethod = RequestMethod.GET.toString();

  @Override
  public String processAction(HttpServletRequest request, String action, String httpMethod) {
    this.httpMethod = httpMethod;
    return action;
  }

  @Override
  public String processFormFieldValue(HttpServletRequest request, String name, String value, String type) {
    return value;
  }

  @Override
  public Map<String, String> getExtraHiddenFields(HttpServletRequest request) {
    Map<String, String> hiddenFields = new HashMap<String, String>();
    if(!request.getMethod().equals(httpMethod)) {
      hiddenFields.put(CSRFTokenManager.CSRF_PARAM_NAME, CSRFTokenManager.getTokenForSession(request.getSession()));
    }
    return hiddenFields;
  }

  @Override
  public String processUrl(HttpServletRequest request, String url) {
    return url;
  }
}
