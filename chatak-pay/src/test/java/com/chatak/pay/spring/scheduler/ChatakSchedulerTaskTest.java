package com.chatak.pay.spring.scheduler;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;

import com.chatak.pay.exception.ChatakPayException;

@RunWith(MockitoJUnitRunner.class)
public class ChatakSchedulerTaskTest {

	@InjectMocks
	ChatakSchedulerTask chatakSchedulerTask = new ChatakSchedulerTask();

	@Test
	public void testProcessReleaseUserSessionTask() throws ChatakPayException {
		chatakSchedulerTask.processReleaseUserSessionTask();
	}
}
