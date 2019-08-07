package com.sr2_common.core.utility.test;


import com.sr2_common.core.utility.PDFUtility;

public class PDFUtilityTest {

	private static final String SOURCE_XML_FILE = "C:\\Users\\m1032091\\Work\\Mindtree\\Learning\\doc_generation_folder\\S_URS.xml";
	//private static final String PDF_CONVERTER_XSLT = "C:\\Users\\m1032091\\Work\\Mindtree\\Learning\\XML2PDF\\urs.xslt";
	private static final String PDF_GENERATE_PATH = "C:\\Users\\m1032091\\Work\\Mindtree\\Learning\\doc_generation_folder\\generated_pdf\\";
	
	private static final String PDF_CONVERTER_XSLT = "C:\\Users\\m1032091\\Work\\Mindtree\\Learning\\doc_generation_folder\\form_si_qte.xsl";
	
	
	public static void main(String[] args) {
		String fileName = "URS_PDF_" + System.currentTimeMillis() + ".pdf";
		PDFUtility.generatePDFFromXML(SOURCE_XML_FILE, PDF_CONVERTER_XSLT, PDF_GENERATE_PATH, fileName);
	}
}
