package com.sr2_common.core.utility;


import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;

import javax.xml.transform.Result;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.sax.SAXResult;
import javax.xml.transform.stream.StreamSource;

import org.apache.fop.apps.FOUserAgent;
import org.apache.fop.apps.Fop;
import org.apache.fop.apps.FopFactory;
import org.apache.fop.apps.MimeConstants;

public class PDFUtility {

	public static void generatePDFFromXML(final String inputFilePath, final String xsltFilePath,
			final String targetFilePath, final String targetFileName) {
		String xmlContent = FileUtility.getStringFromFile(inputFilePath);
		InputStream ioStream = new ByteArrayInputStream(xmlContent.getBytes(Charset.forName("UTF-8")));
		StreamSource xmlSource = new StreamSource(ioStream);
		OutputStream outputStream = null;
		try {
			String loFilePath = targetFilePath + targetFileName;
			outputStream = new FileOutputStream(loFilePath);
			File fopConf = new File(xsltFilePath);
			//FopFactory fopFactory = FopFactory.newInstance(new File("C:\\Users\\m1032091\\Work\\Mindtree\\Learning\\doc_generation_folder\\fop_conf.xml").toURI());
			FopFactory fopFactory = FopFactory.newInstance(new File("C:/Users/m1032091/Work/Mindtree/Learning/doc_generation_folder/fop_conf.xml"));
			FOUserAgent foUserAgent = fopFactory.newFOUserAgent();
			
			Fop fop = fopFactory.newFop(MimeConstants.MIME_PDF, foUserAgent, outputStream);
			//TransformerFactory factory = TransformerFactory.newInstance();
			TransformerFactory factory = TransformerFactory.newInstance("net.sf.saxon.TransformerFactoryImpl",null);
			Transformer transformer = factory.newTransformer(new StreamSource(fopConf));
			Result res = new SAXResult(fop.getDefaultHandler());
			transformer.transform(xmlSource, res);
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			FileUtility.closeSilently(outputStream);
		}
	}
	
	
}
