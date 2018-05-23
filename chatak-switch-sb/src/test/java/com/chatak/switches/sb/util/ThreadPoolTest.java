package com.chatak.switches.sb.util;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class ThreadPoolTest {

	int numberThreads;
	boolean isDaemon;

	@InjectMocks
	ThreadPool threadPool = new ThreadPool(Integer.parseInt("123"), true);

	@Mock
	Runnable work;

	@Test
	public void testRun() {
		threadPool.run(work);
	}

	@Test
	public void testClose() {
		threadPool.close();
	}

}
