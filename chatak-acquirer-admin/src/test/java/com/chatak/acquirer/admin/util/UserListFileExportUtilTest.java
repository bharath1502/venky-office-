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

import com.chatak.pg.model.GenericUserDTO;

@RunWith(MockitoJUnitRunner.class)
public class UserListFileExportUtilTest {

	@InjectMocks
	UserListFileExportUtil userListFileExportUtil;

	@Mock
	HttpServletResponse response;

	@Mock
	MessageSource messageSource;

	@Test
	public void testDownloadUserXl() {
		List<GenericUserDTO> userList = new ArrayList<>();
		GenericUserDTO dto = new GenericUserDTO();
		dto.setStatus(Integer.parseInt("2"));
		dto.setUserType("Merchant");
		userList.add(dto);
		Mockito.when(messageSource.getMessage(Matchers.anyString(), Matchers.any(Object[].class),
				Matchers.any(Locale.class))).thenReturn("abcde");
		userListFileExportUtil.downloadUserXl(userList, response, messageSource);
	}

	@Test
	public void testDownloadUserXlSubMerchant() {
		List<GenericUserDTO> userList = new ArrayList<>();
		GenericUserDTO dto = new GenericUserDTO();
		dto.setStatus(Integer.parseInt("2"));
		dto.setUserType("SubMerchant");
		userList.add(dto);
		Mockito.when(messageSource.getMessage(Matchers.anyString(), Matchers.any(Object[].class),
				Matchers.any(Locale.class))).thenReturn("abcde");
		userListFileExportUtil.downloadUserXl(userList, response, messageSource);
	}

	@Test
	public void testDownloadUserXlSubMerchantElse() {
		List<GenericUserDTO> userList = new ArrayList<>();
		GenericUserDTO dto = new GenericUserDTO();
		dto.setStatus(Integer.parseInt("2"));
		dto.setUserType("abcd");
		userList.add(dto);
		Mockito.when(messageSource.getMessage(Matchers.anyString(), Matchers.any(Object[].class),
				Matchers.any(Locale.class))).thenReturn("abcde");
		userListFileExportUtil.downloadUserXl(userList, response, messageSource);
	}

	@Test(expected = NullPointerException.class)
	public void testDownloadUserPdf() {
		List<GenericUserDTO> userList = new ArrayList<>();
		GenericUserDTO dto = new GenericUserDTO();
		dto.setStatus(Integer.parseInt("2"));
		dto.setUserType("Merchant");
		userList.add(dto);
		Mockito.when(messageSource.getMessage(Matchers.anyString(), Matchers.any(Object[].class),
				Matchers.any(Locale.class))).thenReturn("abcde");
		userListFileExportUtil.downloadUserPdf(userList, response, messageSource);
	}

	@Test(expected = NullPointerException.class)
	public void testDownloadUserPdfSubMerchant() {
		List<GenericUserDTO> userList = new ArrayList<>();
		GenericUserDTO dto = new GenericUserDTO();
		dto.setStatus(Integer.parseInt("2"));
		dto.setUserType("SubMerchant");
		userList.add(dto);
		Mockito.when(messageSource.getMessage(Matchers.anyString(), Matchers.any(Object[].class),
				Matchers.any(Locale.class))).thenReturn("abcde");
		userListFileExportUtil.downloadUserPdf(userList, response, messageSource);
	}

	@Test(expected = NullPointerException.class)
	public void testDownloadUserPdfSubMerchantElse() {
		List<GenericUserDTO> userList = new ArrayList<>();
		GenericUserDTO dto = new GenericUserDTO();
		dto.setStatus(Integer.parseInt("2"));
		dto.setUserType("xyz");
		userList.add(dto);
		Mockito.when(messageSource.getMessage(Matchers.anyString(), Matchers.any(Object[].class),
				Matchers.any(Locale.class))).thenReturn("abcde");
		userListFileExportUtil.downloadUserPdf(userList, response, messageSource);
	}

}
