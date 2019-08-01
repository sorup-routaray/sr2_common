package com.sr2_common.core.utility;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DBUtility {

	public static Connection getConnection(String hostName, String userName, String password) {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("com.mysql.jdbc.Driver not found in class path.");
		}
		Connection connection = null;
		try {
			connection = DriverManager.getConnection(hostName, userName, password);
			//System.out.println("DB Connection established successfully.");
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		
		return connection;
	}
	
	public static void closeConnection(Connection connection) {
		if(connection != null) {
			try {
				connection.close();
				//System.out.println("DB Connection closed successfully.");
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void closeStatement(Statement loStatement) {
		if(loStatement != null) {
			try {
				loStatement.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void rollbackChanges(Connection connection) {
		if(connection != null) {
			try {
				connection.rollback();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}
