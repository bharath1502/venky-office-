package com.chatak.acquirer.admin.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.chatak.pg.model.LitleEFTDTO;


public class FundTransferUtils {
  
	private FundTransferUtils(){}
	public static Map<String,List<LitleEFTDTO>> splitTransferListOnMerchantCode(List<LitleEFTDTO> list){
		Map<String, List<LitleEFTDTO>> splitList = new HashMap<>();
		List<LitleEFTDTO> tempList=null;
		for (LitleEFTDTO yo : list) {
			tempList = splitList.get(yo.getMerchantCode());
		  if (tempList == null) {
			  tempList= new ArrayList<>();
		    splitList.put(yo.getMerchantCode(), tempList);
		  }
		  tempList.add(yo);
		}
		return splitList;
	}
}
