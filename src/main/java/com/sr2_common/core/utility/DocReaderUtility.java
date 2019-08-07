package com.sr2_common.core.utility;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.extractor.WordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;

public class DocReaderUtility {

	public static void main(String[] args) {
		readDocxFile("C:\\Users\\m1032091\\Work\\Mindtree\\Learning\\doc_generation_folder\\Testing Form 1.docx");
		//readDocFile("C:\\Users\\m1032091\\Work\\Mindtree\\Learning\\doc_generation_folder\\Testing Form 1.docx");
	} 
	
	public static void readDocFile(String fileName) {
		FileInputStream fis = null;
		WordExtractor we  = null;
		try {
			File file = new File(fileName);
			fis = new FileInputStream(file.getAbsolutePath());
			HWPFDocument doc = new HWPFDocument(fis);
			we = new WordExtractor(doc);
			String[] paragraphs = we.getParagraphText();

			System.out.println("Total no of paragraph " + paragraphs.length);
			for (String para : paragraphs) {
				System.out.println(para.toString());
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(we != null) {
				try {
					we.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			IOUtils.closeQuietly(fis);
		}

	}

	public static void readDocxFile(String fileName) {
		FileInputStream fis = null;
		XWPFDocument document = null;
		try {
			File file = new File(fileName);
			fis = new FileInputStream(file.getAbsolutePath());
			document = new XWPFDocument(fis);
			List<XWPFParagraph> paragraphs = document.getParagraphs();
			System.out.println("Total no of paragraph " + paragraphs.size());
			for (XWPFParagraph para : paragraphs) {
				System.out.println(para.getText());
			}
			fis.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(document != null) {
				try {
					document.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			IOUtils.closeQuietly(fis);
		}
	}
}
