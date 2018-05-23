package com.chatak.acquirer.admin.util;

import java.net.URL;

import com.chatak.pg.util.Constants;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfTemplate;
import com.itextpdf.text.pdf.PdfWriter;

public class TableHeader extends PdfPageEventHelper {

	String header;
	String footer;
	/** The template with the total number of pages. */
	PdfTemplate total;
	
	public void setFooter(String footer) {
		this.footer = footer;
	}

	/**
	 * Allows us to change the content of the header.
	 * @param header The new header String
	 */
	public void setHeader(String header) {
		this.header = header;
	}

	/**
	 * Creates the PdfTemplate that will hold the total number of pages.
	 * @see com.itextpdf.text.pdf.PdfPageEventHelper#onOpenDocument(
	 *      com.itextpdf.text.pdf.PdfWriter, com.itextpdf.text.Document)
	 */
	public void onOpenDocument(PdfWriter writer, Document document) {
		total = writer.getDirectContent().createTemplate(Constants.THIRTY, Constants.SIXTEEN);
	}
	
	/**
	 * Fills out the total number of pages before the document is closed.
	 * @see com.itextpdf.text.pdf.PdfPageEventHelper#onCloseDocument(
	 *      com.itextpdf.text.pdf.PdfWriter, com.itextpdf.text.Document)
	 */
	public void onCloseDocument(PdfWriter writer, Document document) {
		ColumnText.showTextAligned(total, Element.ALIGN_LEFT,
				new Phrase(String.valueOf(writer.getPageNumber() - 1)),
				Constants.TWO, Constants.TWO, 0);
	}

	/**
	 * Adds a footer to every page
	 * @see com.itextpdf.text.pdf.PdfPageEventHelper#onEndPage(
	 *      com.itextpdf.text.pdf.PdfWriter, com.itextpdf.text.Document)
	 */
	public void onEndPage(PdfWriter writer, Document document) {
		Rectangle page = document.getPageSize();

		Font headerStyle = new Font(); 
		headerStyle.setSize(Constants.EIGHTEEN);
		headerStyle.setStyle(Font.BOLD);  

		PdfPTable headertable = new PdfPTable(1);
		PdfPCell pageNo = new PdfPCell(new Phrase(String.format("Page %d ", writer.getPageNumber())));
		pageNo.setPaddingBottom(Constants.THIRTY);
		pageNo.setBorder(Rectangle.NO_BORDER);
		headertable.addCell(pageNo);

		Font myContentStyledata = new Font(); 
		myContentStyledata.setStyle(Font.NORMAL);
		myContentStyledata.setSize(Constants.SEVEN);

		PdfPTable foot = new PdfPTable(1);
		PdfPCell footercell = new PdfPCell(new Phrase(footer,myContentStyledata));
		footercell.setBorder(Rectangle.TOP);
		foot.addCell(footercell);
		foot.setTotalWidth(page.getWidth() - document.leftMargin() - document.rightMargin());
		foot.writeSelectedRows(0, -1, document.leftMargin(), document.bottomMargin(),
				writer.getDirectContent());
	
	try {
		URL url = getClass().getClassLoader().getResource("Chatak-lofo-f.png");

		Image image = Image.getInstance(url);
		image.setAbsolutePosition(0, Integer.parseInt("325"));
		image.scalePercent(Integer.parseInt("7"));
		image.setAlignment(Integer.parseInt("100"));
		final Font ffont = new Font(Font.FontFamily.TIMES_ROMAN, Integer.parseInt("8"), Font.ITALIC);
		final Phrase footer = new Phrase("", ffont);
		image.scaleToFit(50f, 50f);
		image.setAbsolutePosition(Integer.parseInt("750"), Integer.parseInt("40"));
		final PdfContentByte cb = writer.getDirectContent();
		cb.addImage(image);
		ColumnText.showTextAligned(cb, Element.ALIGN_RIGHT, footer, Integer.parseInt("490"), Integer.parseInt("15"), 0);

		PdfContentByte pdfContentByte = writer.getDirectContent();
		PdfTemplate pdfTemplate = pdfContentByte.createTemplate(Integer.parseInt("500"), Integer.parseInt("1000"));

		pdfTemplate.addImage(image);
		pdfContentByte.addTemplate(pdfTemplate, Integer.parseInt("50"), Integer.parseInt("740"));
	} catch (Exception e) {
		//AdminLogStackTrace.logStackTrace(e, logger);
	}

}
}

