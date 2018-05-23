package com.chatak.merchant.util;

import javax.servlet.http.HttpServletResponse;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.context.MessageSource;
import org.springframework.web.servlet.ModelAndView;

import com.chatak.pg.util.Properties;

@RunWith(MockitoJUnitRunner.class)
public class PaginationUtilTest {

	@InjectMocks
	PaginationUtil paginationUtil;

	@Mock
	HttpServletResponse response;

	@Mock
	MessageSource messageSource;

	@Mock
	ModelAndView modelAndView;

	@Before
	public void pro() {
		java.util.Properties properties = new java.util.Properties();
		properties.setProperty("dash-board.label.completedtransactions", "0");
		properties.setProperty("chatak.header.manual.transactions.reports", "0");

		Properties.mergeProperties(properties);
	}

	@Test
	public void testGetPagenationModel() {
		paginationUtil.getPagenationModel(modelAndView, Integer.parseInt("234"));
	}

	@Test
	public void testGetPagenationModelSuccessive() {
		paginationUtil.getPagenationModelSuccessive(modelAndView, Integer.parseInt("10"), Integer.parseInt("4"));
	}

	@Test
	public void testGetMaxDisplayCount() {
		paginationUtil.getMaxDisplayCount();
	}

}
