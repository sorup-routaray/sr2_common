package com.sr2_common.core.utility.test;


import java.util.List;
import java.util.Map;

import com.sr2_common.core.utility.ExcelUtility;

public class ExcelUtilityTest {

	public static void main(String[] args) {
		
		String loExcelfilePath = "C:\\Users\\m1032091\\Work\\Mindtree\\Learning\\Java_World\\excel_home\\QuizMaster.xlsx";
		String sheetName = "Cricket_QuestionList";
		List<Map<String, String>> excelDatainMap = ExcelUtility.getExcelDataMap(loExcelfilePath,sheetName);
		for (Map<String, String> map : excelDatainMap) {
			for(Map.Entry<String, String> entrySet : map.entrySet()) {
				System.out.println(entrySet.getKey() + ":" + entrySet.getValue());
			}
		}
	}
}
