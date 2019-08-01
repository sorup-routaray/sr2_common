package com.sr2_common.core.utility;


import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.xml.sax.InputSource;

public class XMLUtility {

	/**
	 * This method converts XML document object to String object
	 * 
	 * @param loDocument
	 * @return String representation of the input XML Document object
	 */
	public String convertDocumentToString(Document loDocument) {
		Transformer transformer = null;
		StringWriter writer = new StringWriter();
		try {
			transformer = TransformerFactory.newInstance().newTransformer();
			transformer.transform(new DOMSource(loDocument), new StreamResult(writer));
			String output = writer.getBuffer().toString();
			writer.close();
			return output;
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			closeWriter(writer);
		}
	}
	
	public static Document convertStringToDocument(final String xmlContent) {
		InputSource inputSource = null;
		try {
			DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			inputSource = new InputSource(new StringReader(xmlContent));
			Document loDocument = builder.parse(inputSource);
			return loDocument;
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			
		}
	}

	private void closeWriter(Writer loWriter) {
		if (loWriter != null) {
			try {
				loWriter.close();
			} catch (IOException e) {// closing quietly}
			}
		}
	}
}
