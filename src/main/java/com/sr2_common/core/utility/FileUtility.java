package com.sr2_common.core.utility;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.nio.charset.Charset;

/**
 * 
 * @author Sorup Routaray
 *
 */
public class FileUtility {

	
	public static String getStringFromFile(String filePath) {
		File loFile = new File(filePath);
		StringBuilder contentBuilder = new StringBuilder(); 
		BufferedReader bufReader = null;
		try {
			Reader fileReader = new FileReader(loFile); 
			bufReader = new BufferedReader(fileReader); 
			String line = bufReader.readLine();
			while( line != null){ 
				contentBuilder.append(line); 
				line = bufReader.readLine(); 
			}
			return contentBuilder.toString();
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			closeReader(bufReader);
		}
	}
	
	public static void writeStringToFile(String content, String filePath) {
		OutputStream outputStream = null;
		try {
			outputStream = new FileOutputStream(filePath);
			closeSilently(outputStream);
			outputStream.write(content.getBytes(Charset.forName("UTF-8")));
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			closeSilently(outputStream);
		}
	}
	
	private static void closeReader(Reader loReader) {
		if(loReader != null) {
			try {
				loReader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void closeSilently(OutputStream outputStream) {
		if(outputStream != null) {
			try {
				outputStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void closeSilently(InputStream loInputStream) {
		if(loInputStream != null) {
			try {
				loInputStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
