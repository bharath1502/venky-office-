package com.chatak.merchant.spring.scheduler;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.security.core.session.SessionInformation;
import org.springframework.security.core.session.SessionRegistryImpl;

import com.chatak.pg.util.Properties;

@RunWith(MockitoJUnitRunner.class)
public class ChatakSchedulerTaskTest {

	@InjectMocks
	ChatakSchedulerTask chatakSchedulerTask = new ChatakSchedulerTask();

	@Mock
	private SessionRegistryImpl sessionRegistry;

	@Mock
	SessionInformation sessionInformation;

	@Before
	public void pro() {
		java.util.Properties properties = new java.util.Properties();
		properties.setProperty("merchant.session.expiration.minutes", "10");
		Properties.mergeProperties(properties);
	}

	@Test
	public void testProcessReleaseUserSessionTask() {
		List<Object> list = new ArrayList<>();
		List<SessionInformation> sessionInformations = new ArrayList<>();
		Object object = new Object();
		Date lastRequest = new Date(Integer.parseInt("2014"));
		sessionInformation = new SessionInformation(object, "123", lastRequest);
		list.add(object);
		sessionInformations.add(sessionInformation);
		Mockito.when(sessionRegistry.getAllPrincipals()).thenReturn(list);
		Mockito.when(sessionRegistry.getAllSessions(Matchers.any(Object.class), Matchers.anyBoolean()))
				.thenReturn(sessionInformations);
		chatakSchedulerTask.processReleaseUserSessionTask();
	}

	@Test
	public void testProcessReleaseUserSessionTaskException() {
		Mockito.when(sessionRegistry.getAllPrincipals()).thenThrow(new NullPointerException());
		chatakSchedulerTask.processReleaseUserSessionTask();
	}

}
