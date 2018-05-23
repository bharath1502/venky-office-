package com.chatak.mailsender.service.impl;

import org.aspectj.lang.ProceedingJoinPoint;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class ForkAdviceTest {

	@InjectMocks
	ForkAdvice forkAdvice = new ForkAdvice();

	@Test
	public void testFork() {
		ProceedingJoinPoint pjp = null;
		forkAdvice.fork(pjp);
	}

}
