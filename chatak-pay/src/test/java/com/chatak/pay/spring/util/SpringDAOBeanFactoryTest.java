package com.chatak.pay.spring.util;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class SpringDAOBeanFactoryTest {

	@InjectMocks
	SpringDAOBeanFactory springDAOBeanFactory;

	@Test
	public void testGetSpringContext() {
		SpringDAOBeanFactory.getSpringContext();
	}

}
