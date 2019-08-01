package com.sr2_common.core.utility.test;


import java.sql.Connection;

import com.sr2_common.core.utility.DBUtility;

public class DBUtilityTest {

	public static void main(String[] args) {
		String hostName = "jdbc:mysql://localhost/quiz_master?useSSL=false";
		String userName = "root";
		String password = "Welcome@123!";
		Connection connection = null;
		try {
			connection = DBUtility.getConnection(hostName , userName , password );
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBUtility.closeConnection(connection);
		}
		
	}
}
