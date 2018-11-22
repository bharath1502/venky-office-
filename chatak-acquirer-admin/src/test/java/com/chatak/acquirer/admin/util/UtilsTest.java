package com.chatak.acquirer.admin.util;

import java.io.IOException;
import java.io.Writer;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
@RunWith(MockitoJUnitRunner.class)
public class UtilsTest {
	
	@InjectMocks
	Utils utils;
	
	@Mock
	Writer out;


	@Test
	public void testFormatCommaSeparatedValues() {
		utils.formatCommaSeparatedValues("5345");
	}
	
	@Test
	public void testContainsNone() {
		char[] invalidChars={'c','b','a'};
		utils.containsNone("5345",invalidChars);
	}
	
	@Test
	public void testEscapeCsv() throws IOException {
		utils.escapeCsv(out,"1");
	}

}
