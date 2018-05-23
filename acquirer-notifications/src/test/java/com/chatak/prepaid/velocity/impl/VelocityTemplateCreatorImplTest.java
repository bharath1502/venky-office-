package com.chatak.prepaid.velocity.impl;

import java.util.HashMap;
import java.util.Map;

import org.apache.velocity.exception.ResourceNotFoundException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class VelocityTemplateCreatorImplTest {

	@InjectMocks
	VelocityTemplateCreatorImpl velocityTemplateCreatorImpl = new VelocityTemplateCreatorImpl();

	@Test(expected=ResourceNotFoundException.class)
	public void testCreateEmailTemplate() {
		Map<String, String> requestMap = new HashMap<>();
		String string = new String();
		requestMap.put(string, "abc");
		velocityTemplateCreatorImpl.createEmailTemplate(requestMap, "templatePath");
	}
}
