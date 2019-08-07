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

	private static final String SOURCE = "E:\\doc_generation_dir\\form.pdf";
	private static final String DEST = "E:\\doc_generation_dir\\f8966_d.pdf";

	public static void main(String[] args) throws Exception {
		// writeUsingIText();
		//readDoc();
		readAllFormFields(SOURCE, DEST);
	}

	private static void readAllFormFields(String src, String dest) throws Exception {
		PdfReader reader = new PdfReader(src);
		PdfStamper stamper = new PdfStamper(reader, new FileOutputStream(dest));
		AcroFields form = stamper.getAcroFields();
		Map<String, Item> fields = form.getFields();
		for(Map.Entry<String, Item> entrySet : fields.entrySet()) {
			System.out.println(entrySet.getKey());
		}

	}

	private static void writeUsingIText() {

		Document document = new Document();

		try {

			PdfWriter.getInstance(document, new FileOutputStream(new File(SOURCE)));

			// open
			document.open();

			Paragraph p = new Paragraph();
			p.add("This is my paragraph 1");
			p.setAlignment(Element.ALIGN_CENTER);

			document.add(p);

			Paragraph p2 = new Paragraph();
			p2.add("This is my paragraph 2"); // no alignment

			document.add(p2);

			Font f = new Font();
			f.setStyle(Font.BOLD);
			f.setSize(8);

			document.add(new Paragraph("This is my paragraph 3", f));

			// close
			document.close();

			System.out.println("Done");

		} catch (FileNotFoundException | DocumentException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	private static void readDoc() {
		PdfReader reader;

		try {

			reader = new PdfReader(SOURCE);

			// pageNumber = 1
			String textFromPage = PdfTextExtractor.getTextFromPage(reader, 1);

			System.out.println(textFromPage);

			reader.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
