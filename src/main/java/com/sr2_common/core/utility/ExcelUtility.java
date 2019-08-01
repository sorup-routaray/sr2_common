package com.sr2_common.core.utility;


import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * 
 * @author Sorup Routaray
 *
 */
public class ExcelUtility {

	private static final String INVALID_EXCEL_PATH_ERR_MESSAGE = "Invalid file path %s provided.";
	private static final String INVALID_EXCEL_SHEETNAME_ERR_MESSAGE = "Invalid sheet name %s provided.";
	private static final DateFormat EXCEL_DATE_TO_STRING_FORMAT = new SimpleDateFormat("dd-MM-yyyy");

	/**
	 * 
	 * @param loExcelfilePath
	 * @param sheetName
	 * @return
	 */
	public static List<Map<String, String>> getExcelDataMap(String loExcelfilePath, String sheetName) {
		FileInputStream fileInputStream = null;
		List<Map<String, String>> excelDataMapList = new ArrayList<Map<String, String>>();
		XSSFWorkbook workbook = null;
		try {
			File inputFile = new File(loExcelfilePath);
			if (!inputFile.exists()) {
				throw new RuntimeException(String.format(INVALID_EXCEL_PATH_ERR_MESSAGE, loExcelfilePath));
			}
			fileInputStream = new FileInputStream(inputFile);
			workbook = new XSSFWorkbook(fileInputStream);

			XSSFSheet sheet = workbook.getSheet(sheetName);
			if (sheet == null) {
				throw new RuntimeException(String.format(INVALID_EXCEL_SHEETNAME_ERR_MESSAGE, sheetName));
			}
			int lastRowNum = sheet.getLastRowNum();
			Map<Integer, String> columnIndexAndNameMap = new HashMap<>();
			for (int i = 0; i <= lastRowNum; i++) {
				XSSFRow currentProcessingRow = sheet.getRow(i);
				if (i == 0) {
					populateColumnIndexAndNameMapList(currentProcessingRow, columnIndexAndNameMap);
					continue;
				}
				Map<String, String> excelRowDataMap = getExcelRowAsMap(currentProcessingRow, columnIndexAndNameMap);
				excelDataMapList.add(excelRowDataMap);
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			FileUtility.closeSilently(fileInputStream);
			closeWorkbook(workbook);
		}
		return excelDataMapList;
	}

	private static Map<String, String> getExcelRowAsMap(XSSFRow excelRow, Map<Integer, String> columnIndexAndNameMap) {
		Map<String, String> excelRowDataMap = new HashMap<>();
		for (Map.Entry<Integer, String> entrySetForColumnIndexAndName : columnIndexAndNameMap.entrySet()) {
			Integer cellIndex = entrySetForColumnIndexAndName.getKey();
			String columnName = entrySetForColumnIndexAndName.getValue();
			XSSFCell currentProcessingCell = excelRow.getCell(cellIndex);
			String stringCellValue = getStringValueFromExcelCell(currentProcessingCell);
			excelRowDataMap.put(columnName, stringCellValue);
		}
		return excelRowDataMap;
	}

	private static void populateColumnIndexAndNameMapList(XSSFRow headerRow,
			Map<Integer, String> columnIndexAndNameMap) {
		Iterator<Cell> cellIterator = headerRow.cellIterator();
		while (cellIterator.hasNext()) {
			Cell cell = cellIterator.next();
			String columnName = cell.getStringCellValue();
			int columnIndex = cell.getColumnIndex();
			columnIndexAndNameMap.put(columnIndex, columnName);
		}
	}

	private static String getStringValueFromExcelCell(XSSFCell cell) {
		if(cell == null) {
			return "";
		}
		
		switch (cell.getCellType()) {
		case NUMERIC:
			if(DateUtil.isCellDateFormatted(cell)) {
				Date dateCellValue = cell.getDateCellValue();
				if(dateCellValue == null) {
					return "";
				} else {
					return EXCEL_DATE_TO_STRING_FORMAT.format(dateCellValue);
				}
			} else {
				cell.setCellType(CellType.STRING);
				return String.valueOf(cell.getStringCellValue());
			}
		case STRING:
			return cell.getStringCellValue();
		default:
			return "";
		}
	}

	private static void closeWorkbook(XSSFWorkbook workbook) {
		if (workbook != null) {
			try {
				workbook.close();
			} catch (IOException e) {
			}
		}
	}

}
