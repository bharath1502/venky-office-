package com.chatak.acquirer.admin.service;

import com.chatak.acquirer.admin.exception.ChatakAdminException;
import com.chatak.pg.bean.Response;
import com.chatak.pg.model.DynamicMDRDTO;

public interface MDRService {

  public DynamicMDRDTO saveOrUpdateDynamicMDR(DynamicMDRDTO dynamicMDRDTO)
      throws ChatakAdminException;

  public DynamicMDRDTO searchMDRById(Long getMDRId);

  public Response searchDynamicMDR(DynamicMDRDTO dynamicMDRDTO);

}
