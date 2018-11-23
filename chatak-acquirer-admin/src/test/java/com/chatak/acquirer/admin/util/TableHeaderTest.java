package com.chatak.acquirer.admin.util;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.itextpdf.text.Document;
import com.itextpdf.text.pdf.PdfWriter;

@RunWith(MockitoJUnitRunner.class)
public class TableHeaderTest {

	@InjectMocks
	TableHeader tableHeader;

	@Mock
	PdfWriter writer;

	@Mock
	Document document;

	@Test
	public void testSetFooter() {
		tableHeader.setFooter("footer");
	}

	@Test
	public void testSetHeader() {
		tableHeader.setHeader("header");
	}

	@Test(expected = NullPointerException.class)
	public void testOnOpenDocument() {
		tableHeader.onOpenDocument(writer, document);
	}

	@Test(expected = NullPointerException.class)
	public void testOnCloseDocument() {
		tableHeader.onCloseDocument(writer, document);
	}

	@Test(expected = NullPointerException.class)
	public void testOnEndPage() {
		tableHeader.onEndPage(writer, document);
	}

}
