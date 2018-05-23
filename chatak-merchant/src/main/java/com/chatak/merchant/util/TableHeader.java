package com.chatak.merchant.util;
import com.chatak.pg.util.Constants;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.ColumnText;
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

	/**
	 * Allows us to change the content of the header.
	 * @param header The new header String
	 */
	public void setHeader(String header) {
		this.header = header;
	}

	public void setFooter(String footer) {
		this.footer = footer;
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
	 * Adds a footer to every page
	 * @see com.itextpdf.text.pdf.PdfPageEventHelper#onEndPage(
	 *      com.itextpdf.text.pdf.PdfWriter, com.itextpdf.text.Document)
	 */
	public void onEndPage(PdfWriter writer, Document document) {
		Rectangle page = document.getPageSize();


		Font HeaderStyle = new Font(); 
		HeaderStyle.setSize(Constants.EIGHTEEN);
		HeaderStyle.setStyle(Font.BOLD);  
		PdfPTable headertable = new PdfPTable(1);
		PdfPCell pageNo = new PdfPCell(new Phrase(String.format("Page %d ", writer.getPageNumber())));
		pageNo.setPaddingBottom(Constants.THIRTY);
		pageNo.setBorder(Rectangle.NO_BORDER);
		headertable.addCell(pageNo);
		Font myContentStyledata = new Font(); 
		myContentStyledata.setSize(Constants.SEVEN);
		myContentStyledata.setStyle(Font.NORMAL);

		PdfPTable foot = new PdfPTable(1);
		PdfPCell footercell = new PdfPCell(new Phrase(footer,myContentStyledata));
		footercell.setBorder(Rectangle.TOP);
		foot.addCell(footercell);
		foot.setTotalWidth(page.getWidth() - document.leftMargin() - document.rightMargin());
		foot.writeSelectedRows(0, -1, document.leftMargin(), document.bottomMargin(),
				writer.getDirectContent());
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
}

