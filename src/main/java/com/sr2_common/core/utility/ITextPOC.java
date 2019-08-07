package com.sr2_common.core.utility;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.AcroFields;
import com.itextpdf.text.pdf.AcroFields.Item;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.parser.PdfTextExtractor;

public class ITextPOC {

	private static final String DEST_PATH = "C:\\Users\\m1032091\\Work\\Mindtree\\Learning\\doc_generation_folder\\test.pdf";
	private static final String SOURCE = "C:\\Users\\m1032091\\Work\\Mindtree\\Learning\\doc_generation_folder\\sample_form.pdf";

	public static void main(String[] args) throws Exception{
		//String fileName = "test.pdf";
		// writeUsingIText(DEST_PATH, fileName);
		//readUsingIText(DEST_PATH, "TEST_1697_LA01R.pdf");
		manipulatePdf(SOURCE, DEST_PATH);
	}

	private static void writeUsingIText(String dest, String fileName) {

		Document pdfDocument = new Document();
		try {

			String pdfFilePath = dest + System.getProperty("file.separator") + fileName;
			PdfWriter.getInstance(pdfDocument, new FileOutputStream(new File(pdfFilePath)));
			// open
			pdfDocument.open();
			Paragraph p = new Paragraph();
			p.add("This is my paragraph 1");
			p.setAlignment(Element.ALIGN_CENTER);
			pdfDocument.add(p);
			Paragraph p2 = new Paragraph();
			p2.add("This is my paragraph 2"); // no alignment

			pdfDocument.add(p2);

			Font f = new Font();
			f.setStyle(Font.BOLD);
			f.setSize(8);
			pdfDocument.add(new Paragraph("This is my paragraph 3", f));
			System.out.println("Done");

		} catch (FileNotFoundException | DocumentException e) {
			e.printStackTrace();
		} finally {
			if (pdfDocument != null) {
				pdfDocument.close();
			}
		}
	}

	private static void readUsingIText(String dest, String fileName) {
		PdfReader reader = null;
		try {
			String pdfFilePath = dest + System.getProperty("file.separator") + fileName;
			reader = new PdfReader(pdfFilePath);
			// pageNumber = 1
			String textFromPage = PdfTextExtractor.getTextFromPage(reader, 1);
			System.out.println(textFromPage);
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (reader != null) {
				reader.close();
			}
		}
	}

	protected static void manipulatePdf(String src, String dest) throws Exception {
		PdfReader reader = new PdfReader(src);
		PdfStamper stamper = new PdfStamper(reader, new FileOutputStream(dest));
		AcroFields form = stamper.getAcroFields();
		Map<String, Item> fields = form.getFields();
		for(Map.Entry<String, Item> entrySet : fields.entrySet()) {
			System.out.println(entrySet.getKey());
		}
		stamper.close();
	}

}
