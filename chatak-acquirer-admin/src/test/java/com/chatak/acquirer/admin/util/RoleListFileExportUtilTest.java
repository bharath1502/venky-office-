package com.chatak.acquirer.admin.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletResponse;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.context.MessageSource;

import com.chatak.pg.model.UserRolesDTO;

@RunWith(MockitoJUnitRunner.class)
public class RoleListFileExportUtilTest {

	@InjectMocks
	RoleListFileExportUtil roleListFileExportUtil;

	@Mock
	HttpServletResponse response;

	@Mock
	MessageSource messageSource;

	@Test
	public void testDownloadRoleXl() {
		List<UserRolesDTO> userRoleList = new ArrayList<>();
		UserRolesDTO dto = new UserRolesDTO();
		dto.setStatus(0);
		userRoleList.add(dto);
		Mockito.when(messageSource.getMessage(Matchers.anyString(), Matchers.any(Object[].class),
				Matchers.any(Locale.class))).thenReturn("abcd");
		roleListFileExportUtil.downloadRoleXl(userRoleList, response, messageSource);
	}

	@Test(expected=NullPointerException.class)
	public void testDownloadRolePdf() {
		List<UserRolesDTO> userRoleList = new ArrayList<>();
		UserRolesDTO dto = new UserRolesDTO();
		dto.setStatus(0);
		userRoleList.add(dto);
		Mockito.when(messageSource.getMessage(Matchers.anyString(), Matchers.any(Object[].class),
				Matchers.any(Locale.class))).thenReturn("abcd");
		roleListFileExportUtil.downloadRolePdf(userRoleList, response, messageSource);
	}

}
