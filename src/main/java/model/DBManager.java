package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class DBManager {
	
	private static String URL = "";
	private static String DB_NAME = "";
	private static String USER_NAME = "";
	private static String PASSWORD = "";
	
	private static void loadPropertiesFile() {
		try {
			ResourceBundle rb = ResourceBundle.getBundle("/properties/DBAccess");
			
			URL = rb.getString("url");
			DB_NAME = rb.getString("db_name");
			USER_NAME = rb.getString("user_name");
			PASSWORD = rb.getString("password");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static Connection getConnection() throws SQLException {
		loadPropertiesFile();
		
		String accessUrl = URL + DB_NAME + "?";
		
		Connection con = DriverManager.getConnection(accessUrl, USER_NAME, PASSWORD);
		
		return con;
	}
}
