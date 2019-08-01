package com.sr2_common.core.utility;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class QuizeMasterDataPopulateApp {

	private static String DB_HOST_NAME = "jdbc:mysql://localhost/quiz_master?useSSL=false";
	private static String DB_USER_NAME = "root";
	private static String DB_PASSWORD = "Welcome@123!";
	
	private static final String QUIZ_QUESTION_OPTION_LIST_INSERT_QUERY = "INSERT INTO QUIZ_QUESTION_LIST (QUIZ_QUESTION, PARENT_ID) VALUES(?, ?)";
	private static final String QUIZ_TYPE_LIST_INSERT_QUERY = "INSERT INTO QUIZ_TYPE_LIST (QUIZ_TYPE_TEXT, QUIZ_TYPE_DESCRIPTION, QUIZ_TYPE_CODE) VALUES(?, ?, ?)";
	private static final String QUIZ_ITEM_OPTION_LIST_INSERT_QUERY = "INSERT INTO QUIZ_ANSWER_OPTION_LIST (PARENT_ID, OPTION_DESCRIPTION, IS_CORRECT_OPTION) VALUES(?, ?, ?)";
	
	private static final String USER_INFO_INSERT_QUERY = "INSERT INTO USER_INFO (F_NAME, L_NAME, EMAIL_ID, DOB, CONTACT_NUMBER_1, CONTACT_NUMBER_2, PRIMARY_ADDRESS, OTHER_ADDRESS,  GENDER, PARENT_ID) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
	
	private static final String USER_SECURITY_INSERT_QUERY = "INSERT INTO USER_SECURITY (USER_NAME, CREATE_TS, CREATE_BY, PASSWORD, IS_ACTIVE, IS_PASSWORD_CHANGED, USER_ROLE) VALUES(?, ?, ?, ?, ?, ?, ?)";
	
	public static void main(String[] args) {
		String loExcelfilePath = "C:\\Users\\m1032091\\Work\\Mindtree\\Learning\\Java_World\\excel_home\\QuizMaster.xlsx";
		String quizTypeSheetName = "Quiz_Type";
		String cricketQuizSheetName = "Cricket_QuestionList";
		String qtype = "Cricket";
		insertQuizTypeRecords(loExcelfilePath, quizTypeSheetName);
		insertQuizItemRecords(loExcelfilePath, cricketQuizSheetName, qtype);
		insertUserInfoRecords(loExcelfilePath, "UserData");
	}

	private static void insertQuizTypeRecords(String loExcelfilePath, String sheetName) {
		
		List<Map<String, String>> excelDatainMap = ExcelUtility.getExcelDataMap(loExcelfilePath,sheetName);
		Connection loConnection = null;
		PreparedStatement pstmt = null;
		try {
			loConnection = DBUtility.getConnection(DB_HOST_NAME, DB_USER_NAME, DB_PASSWORD);
			loConnection.setAutoCommit(Boolean.FALSE);
			
			pstmt = loConnection.prepareStatement(QUIZ_TYPE_LIST_INSERT_QUERY);
			for (Map<String, String> dataMap : excelDatainMap) {
				String quizType = dataMap.get("Quiz Type");
				String quizDescription = dataMap.get("Quiz Description");
				String quizCodeCode = dataMap.get("Quiz Type Code");
				pstmt.setString(1, quizType);
				pstmt.setString(2, quizDescription);
				pstmt.setString(3, quizCodeCode);
				pstmt.addBatch();
			}
			int[] executeBatch = pstmt.executeBatch();
			if(excelDatainMap.size() == executeBatch.length) {
				loConnection.commit();
				System.out.println("Quiz type master data saved successfully.");
			} else {
				DBUtility.rollbackChanges(loConnection);
				throw new RuntimeException("Total number of input records does not match with total number of records inserted. Rollingback the transaction to early statge.");
			}
		} catch (Exception e) {
			DBUtility.rollbackChanges(loConnection);
			if(e instanceof RuntimeException) {
				throw (RuntimeException)e;
			}
			throw new RuntimeException(e);
		} finally {
			DBUtility.closeStatement(pstmt);
			DBUtility.closeConnection(loConnection);
		}
	}	
	
	private static void insertQuizItemRecords(String loExcelfilePath, String sheetName, String qtype) {
		
		List<Map<String, String>> excelDatainMap = ExcelUtility.getExcelDataMap(loExcelfilePath,sheetName);
		Integer qTypeId = getQuestionTypeId(qtype);
		if(qTypeId == 0) {
			throw new RuntimeException("Unable to retrive QTYPE ID from Db for QTYPE code " + qtype);
		}
		Connection loConnection = null;
		PreparedStatement pstmt = null;
		try {
			loConnection = DBUtility.getConnection(DB_HOST_NAME, DB_USER_NAME, DB_PASSWORD);
			loConnection.setAutoCommit(Boolean.FALSE);
			
			pstmt = loConnection.prepareStatement(QUIZ_QUESTION_OPTION_LIST_INSERT_QUERY);
			for (Map<String, String> dataMap : excelDatainMap) {
				String quizDescription = dataMap.get("Question Description");
				pstmt.setString(1, quizDescription);
				pstmt.setInt(2, qTypeId);
				pstmt.addBatch();
			}
			int[] executeBatch = pstmt.executeBatch();
			if(excelDatainMap.size() == executeBatch.length) {
				loConnection.commit();
				System.out.println("Quiz Item data saved successfully.");
				insertQuizeItemOptionRecords(loExcelfilePath, sheetName);
			} else {
				DBUtility.rollbackChanges(loConnection);
				throw new RuntimeException("Total number of input records does not match with total number of records inserted. Rolling back the transaction to early statge.");
			}
		} catch (Exception e) {
			DBUtility.rollbackChanges(loConnection);
			if(e instanceof RuntimeException) {
				throw (RuntimeException)e;
			}
			throw new RuntimeException(e);
		} finally {
			DBUtility.closeStatement(pstmt);
			DBUtility.closeConnection(loConnection);
		}
		
	}
	
	private static void insertQuizeItemOptionRecords(String loExcelfilePath, String sheetName) {
		List<Map<String, String>> excelDatainMap = ExcelUtility.getExcelDataMap(loExcelfilePath,sheetName);
		List<Map<String, Object>> optionItemMapList = new ArrayList<>();
		for (Map<String, String> dataMap : excelDatainMap) {
			String quizDescription = dataMap.get("Question Description");
			Integer qTypeId = getQuestionItemId(quizDescription);
			dataMap.put("PARENT_ID", String.valueOf(qTypeId));
			String option1 = dataMap.get("Option 1");
			String option2 = dataMap.get("Option 2");
			String option3 = dataMap.get("Option 3");
			String option4 = dataMap.get("Option 4");
			String correctOption = dataMap.get("Correct Option");
			Double loCorrectOption = Double.valueOf(correctOption);
			Integer correctoptionid = loCorrectOption.intValue();
			Map<String, Object> option1ItemMap = new HashMap<>();
			option1ItemMap.put("OPTION_DESC", option1);
			option1ItemMap.put("PARENT_ID", qTypeId);
			option1ItemMap.put("IS_CORRECT_ANSWER", correctoptionid.equals(1));
			optionItemMapList.add(option1ItemMap);
			
			Map<String, Object> option2ItemMap = new HashMap<>();
			option2ItemMap.put("OPTION_DESC", option2);
			option2ItemMap.put("PARENT_ID", qTypeId);
			option2ItemMap.put("IS_CORRECT_ANSWER", correctoptionid.equals(2));
			optionItemMapList.add(option2ItemMap);

			Map<String, Object> option3ItemMap = new HashMap<>();
			option3ItemMap.put("OPTION_DESC", option3);
			option3ItemMap.put("PARENT_ID", qTypeId);
			option3ItemMap.put("IS_CORRECT_ANSWER", correctoptionid.equals(3));
			optionItemMapList.add(option3ItemMap);
			
			Map<String, Object> option4ItemMap = new HashMap<>();
			option4ItemMap.put("OPTION_DESC", option4);
			option4ItemMap.put("PARENT_ID", qTypeId);
			option4ItemMap.put("IS_CORRECT_ANSWER", correctoptionid.equals(4));
			optionItemMapList.add(option4ItemMap);
		}
		
		Connection loConnection = null;
		PreparedStatement pstmt = null;
		try {
			loConnection = DBUtility.getConnection(DB_HOST_NAME, DB_USER_NAME, DB_PASSWORD);
			loConnection.setAutoCommit(Boolean.FALSE);
			
			pstmt = loConnection.prepareStatement(QUIZ_ITEM_OPTION_LIST_INSERT_QUERY);
			for (Map<String, Object> dataMap : optionItemMapList) {
				Integer parentId = (Integer)dataMap.get("PARENT_ID");
				String optionDesc = (String)dataMap.get("OPTION_DESC");
				Boolean isCorrect = (Boolean)dataMap.get("IS_CORRECT_ANSWER");
				String isCorrectAnswer = isCorrect ? "Y" : "N";
				pstmt.setInt(1, parentId);
				pstmt.setString(2, optionDesc);
				pstmt.setString(3, isCorrectAnswer);
				pstmt.addBatch();
			}
			int[] executeBatch = pstmt.executeBatch();
			if(optionItemMapList.size() == executeBatch.length) {
				loConnection.commit();
				System.out.println("Quiz Answer Option Item data saved successfully.");
			} else {
				DBUtility.rollbackChanges(loConnection);
				throw new RuntimeException("Total number of input records does not match with total number of records inserted. Rolling back the transaction to early statge.");
			}
		} catch (Exception e) {
			DBUtility.rollbackChanges(loConnection);
			if(e instanceof RuntimeException) {
				throw (RuntimeException)e;
			}
			throw new RuntimeException(e);
		} finally {
			DBUtility.closeStatement(pstmt);
			DBUtility.closeConnection(loConnection);
		}
	}
	
	private static void insertUserInfoRecords(String loExcelfilePath, String sheetName) {
		List<Map<String, String>> excelDatainMap = ExcelUtility.getExcelDataMap(loExcelfilePath,sheetName);
		Connection loConnection = null;
		PreparedStatement pstmt = null;
		try {
			loConnection = DBUtility.getConnection(DB_HOST_NAME, DB_USER_NAME, DB_PASSWORD);
			loConnection.setAutoCommit(Boolean.FALSE);
			
			pstmt = loConnection.prepareStatement(USER_INFO_INSERT_QUERY);
			for (Map<String, String> dataMap : excelDatainMap) {
				String userName = dataMap.get("USER_NAME");
				String userRole = dataMap.get("USER_ROLE");
				Integer userId = getenrateUserId(loConnection, userName, userRole);
				String fName = dataMap.get("FIRST NAME");
				String lName = dataMap.get("LAST NAME");
				String emailid = dataMap.get("EMAIL ID");
				Date utilDob = DateUtility.getDateFromString(dataMap.get("DOB"), "dd-MM-yyyy");
				String loContactNumber1 = dataMap.get("CONTACT_NUMBER_1");
				String loContactNumber2 = dataMap.get("CONTACT_NUMBER_2");
				String loPrimaryAddress = dataMap.get("PRIMARY_ADDRESS");
				String loOtherAddress = dataMap.get("OTHER_ADDRESS");
				String loGender = dataMap.get("GENDER");
				String genderCode = deriveGenderCode(loGender);
				pstmt.setString(1, fName);
				pstmt.setString(2, lName);
				pstmt.setString(3, emailid);
				pstmt.setDate(4, DateUtility.getSQLdateFromUtilDate(utilDob));
				pstmt.setString(5, loContactNumber1);
				pstmt.setString(6, loContactNumber2);
				pstmt.setString(7, loPrimaryAddress);
				pstmt.setString(8, loOtherAddress);
				pstmt.setString(9, genderCode);
				pstmt.setInt(10, userId);
				pstmt.addBatch();
			}
			int[] executeBatch = pstmt.executeBatch();
			if(excelDatainMap.size() == executeBatch.length) {
				loConnection.commit();
				System.out.println("User Info data saved successfully.");
			} else {
				DBUtility.rollbackChanges(loConnection);
				throw new RuntimeException("Total number of input records does not match with total number of records inserted. Rollingback the transaction to early statge.");
			}
		} catch (Exception e) {
			DBUtility.rollbackChanges(loConnection);
			if(e instanceof RuntimeException) {
				throw (RuntimeException)e;
			}
			throw new RuntimeException(e);
		} finally {
			DBUtility.closeStatement(pstmt);
			DBUtility.closeConnection(loConnection);
		}
		
	}
	private static Integer getenrateUserId(Connection loConnection, String inUserName, String userRole) {
		
		PreparedStatement pstmt = null;
		try {
			loConnection = DBUtility.getConnection(DB_HOST_NAME, DB_USER_NAME, DB_PASSWORD);
			loConnection.setAutoCommit(Boolean.FALSE);
			pstmt = loConnection.prepareStatement(USER_SECURITY_INSERT_QUERY,Statement.RETURN_GENERATED_KEYS);
			pstmt.setString(1, inUserName);
			pstmt.setTimestamp(2, DateUtility.getCurrenttimestamp());
			pstmt.setString(3, "SYSTEM");
			String encodePassword = PasswordUtility.encodetoBase64String(PasswordUtility.generateRandomPassword(20));
			pstmt.setString(4, encodePassword);
			pstmt.setString(5, "1");
			pstmt.setString(6, "N");
			pstmt.setString(7, userRole);
			pstmt.executeUpdate();
			ResultSet executeQuery = pstmt.getGeneratedKeys();
			if(executeQuery.next()) {
				loConnection.commit();
				int userId = executeQuery.getInt(1);
				return userId;
			}
			throw new RuntimeException("Not able to insert record in User secirity table.");
		} catch (Exception e) {
			DBUtility.rollbackChanges(loConnection);
			if(e instanceof RuntimeException) {
				throw (RuntimeException)e;
			}
			throw new RuntimeException(e);
		} finally {
			DBUtility.closeStatement(pstmt);
			DBUtility.closeConnection(loConnection);
		}
	}

	private static Integer getQuestionItemId(String quizItemDesc) {
		Connection loConnection = null;
		PreparedStatement pstmt = null;
		Integer quizItemId = 0;
		try {
			loConnection = DBUtility.getConnection(DB_HOST_NAME, DB_USER_NAME, DB_PASSWORD);
			pstmt = loConnection.prepareStatement("SELECT PK_ID FROM QUIZ_QUESTION_LIST WHERE UPPER(QUIZ_QUESTION) = ?");
			pstmt.setString(1, quizItemDesc.toUpperCase());
			ResultSet resultSet = pstmt.executeQuery();
			while(resultSet.next()) {
				quizItemId = resultSet.getInt("PK_ID");
			}
		} catch (Exception e) {
			if(e instanceof RuntimeException) {
				throw (RuntimeException)e;
			}
			throw new RuntimeException(e);
		} finally {
			DBUtility.closeStatement(pstmt);
			DBUtility.closeConnection(loConnection);
		}
		return quizItemId;
	}


	private static Integer getQuestionTypeId(String qtype) {
		Connection loConnection = null;
		PreparedStatement pstmt = null;
		Integer questiontypeId = 0;
		try {
			loConnection = DBUtility.getConnection(DB_HOST_NAME, DB_USER_NAME, DB_PASSWORD);
			pstmt = loConnection.prepareStatement("SELECT PK_ID FROM QUIZ_TYPE_LIST WHERE UPPER(QUIZ_TYPE_TEXT) = ?");
			pstmt.setString(1, qtype.toUpperCase());
			ResultSet resultSet = pstmt.executeQuery();
			while(resultSet.next()) {
				questiontypeId = resultSet.getInt("PK_ID");
			}
		} catch (Exception e) {
			if(e instanceof RuntimeException) {
				throw (RuntimeException)e;
			}
			throw new RuntimeException(e);
		} finally {
			DBUtility.closeStatement(pstmt);
			DBUtility.closeConnection(loConnection);
		}
		return questiontypeId;
	}
	
	private static String deriveGenderCode(String loGender) {
		if(loGender == null || loGender.trim().isEmpty()) {
			return "3";
		}
		if(loGender.equalsIgnoreCase("Male")) {
			return "1";
		} else if(loGender.equalsIgnoreCase("Female")) {
			return "2";
		}
		return "3";
	}

		
}
