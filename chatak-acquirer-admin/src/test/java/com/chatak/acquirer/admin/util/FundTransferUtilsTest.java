package com.chatak.acquirer.admin.util;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;

import com.chatak.pg.model.LitleEFTDTO;

@RunWith(MockitoJUnitRunner.class)
public class FundTransferUtilsTest {

	@InjectMocks
	FundTransferUtils fundTransferUtils;

	@Test
	public void testSplitTransferListOnMerchantCode() {
		List<LitleEFTDTO> list = new ArrayList<>();
		LitleEFTDTO litleEFTDTO = new LitleEFTDTO();
		list.add(litleEFTDTO);
		fundTransferUtils.splitTransferListOnMerchantCode(list);
	}

}
