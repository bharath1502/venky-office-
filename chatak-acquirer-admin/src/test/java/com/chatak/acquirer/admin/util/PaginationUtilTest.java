package com.chatak.acquirer.admin.util;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.web.servlet.ModelAndView;

@RunWith(MockitoJUnitRunner.class)
public class PaginationUtilTest {

	@InjectMocks
	PaginationUtil paginationUtil;

	@Test
	public void testGetPaginationModel() {
		ModelAndView modelAndView = new ModelAndView();
		paginationUtil.getPagenationModel(modelAndView, Integer.parseInt("15"));
	}

	@Test
	public void testGetPagenationModelSuccessive() {
		ModelAndView modelAndView = new ModelAndView();
		paginationUtil.getPagenationModelSuccessive(modelAndView, Integer.parseInt("43"), Integer.parseInt("43"));
	}

	@Test
	public void testGetMaxDisplayCount() {
		paginationUtil.getMaxDisplayCount();
	}

	@Test
	public void testGetPagenationModelSuccessives() {
		ModelAndView modelAndView = new ModelAndView();
		paginationUtil.getPagenationModelSuccessive(modelAndView, Integer.parseInt("2"), Integer.parseInt("5"),
				Integer.parseInt("4"));
	}

	@Test
	public void testGetPagenationModel() {
		ModelAndView modelAndView = new ModelAndView();
		paginationUtil.getPagenationModel(modelAndView, 0, Integer.parseInt("43"));
	}

	@Test
	public void testGetPagenationModelElse() {
		ModelAndView modelAndView = new ModelAndView();
		paginationUtil.getPagenationModel(modelAndView, Integer.parseInt("5"), Integer.parseInt("43"));
	}

}
