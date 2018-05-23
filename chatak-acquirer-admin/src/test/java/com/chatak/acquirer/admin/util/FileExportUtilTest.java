package com.chatak.acquirer.admin.util;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.context.MessageSource;

import com.chatak.pg.acq.dao.model.PGTransfers;

@RunWith(MockitoJUnitRunner.class)

public class FileExportUtilTest {

	@InjectMocks
	FileExportUtil fileExportUtil;

	@Mock
	HttpServletResponse response;

	@Mock
	MessageSource messageSource;
	
	@Mock
	List<Long> transfersIds;

	@Test
	public void testDownloadEFTRequestsXlBatch() {
		List<PGTransfers> transfersList = new ArrayList<>();
		PGTransfers pgTransfers=new PGTransfers();
		Map<String, String> merchantNameMap = new HashMap<>();
		pgTransfers.setPgTransfersId(Long.parseLong("432"));
		pgTransfers.setAmount(Long.parseLong("432"));
		pgTransfers.setCreatedDate(new Timestamp(Long.parseLong("432")));
		transfersList.add(pgTransfers);
		Mockito.when(messageSource.getMessage(Matchers.anyString(),Matchers.any(Object[].class),Matchers.any(Locale.class))).thenReturn("534");
		fileExportUtil.downloadEFTRequestsXlBatch(transfersList, transfersIds, merchantNameMap, response,
				messageSource);
	}

}
