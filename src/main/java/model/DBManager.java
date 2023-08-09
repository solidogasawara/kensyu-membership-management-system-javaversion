package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBManager {
	private static String URL = "jdbc:mysql://localhost:3306/";
	private static String DB_NAME = "customers";
	private static String USER_NAME = "root";
	private static String PASSWORD = "root";
	
	public static Connection getConnection() throws SQLException {
		String accessUrl = URL + DB_NAME + "?";
		
		Connection con = DriverManager.getConnection(accessUrl, USER_NAME, PASSWORD);
		
		return con;
	}
}
