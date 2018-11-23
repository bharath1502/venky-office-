package com.chatak.acquirer.admin.util;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.context.MessageSource;

import com.chatak.pg.user.bean.BlackListedCardRequest;

@RunWith(MockitoJUnitRunner.class)
public class BlackListedCardFileExportUtilTest {

	@InjectMocks
	BlackListedCardFileExportUtil blackListedCardFileExportUtil;

	@Mock
	HttpServletResponse response;

	@Mock
	MessageSource messageSource;

	@Test(expected = NullPointerException.class)
	public void testDownloadBlackListedCardPdf() {
		List<BlackListedCardRequest> blackListedCardData = new ArrayList<>();
		BlackListedCardRequest cardRequest = new BlackListedCardRequest();
		cardRequest.setCardNumber(Long.parseLong("5435"));
		blackListedCardData.add(cardRequest);
		blackListedCardFileExportUtil.downloadBlackListedCardPdf(blackListedCardData, response, "headerMessage",
				messageSource);
	}

	@Test
	public void testDownloadBlackListedCardXl() {
		List<BlackListedCardRequest> blackListedCardData = new ArrayList<>();
		BlackListedCardRequest cardRequest = new BlackListedCardRequest();
		blackListedCardData.add(cardRequest);
		blackListedCardFileExportUtil.downloadBlackListedCardXl(blackListedCardData, response, "headerMessage",
				messageSource);
	}

}
