package com.sr2_common.core.utility.test;


import com.sr2_common.core.utility.PDFUtility;

public class PDFUtilityTest {

	private static final String XML_FILE_PATH = "C:\\Users\\m1032091\\Work\\Mindtree\\Learning\\XML2PDF\\urs.xml";
	private static final String PDF_CONVERTER_XSLT = "C:\\Users\\m1032091\\Work\\Mindtree\\Learning\\XML2PDF\\urs.xslt";
	private static final String PDF_GENERATE_PATH = "C:\\Users\\m1032091\\Work\\Mindtree\\Learning\\XML2PDF\\generated_pdf\\";
	
	
	public static void main(String[] args) {
		String fileName = "URS_PDF_" + System.currentTimeMillis() + ".pdf";
		PDFUtility.generatePDFFromXML(XML_FILE_PATH, PDF_CONVERTER_XSLT, PDF_GENERATE_PATH, fileName);
	}
}
