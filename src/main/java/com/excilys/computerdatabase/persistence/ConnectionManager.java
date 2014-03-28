package com.excilys.computerdatabase.persistence;

import java.sql.DriverManager;
import java.sql.SQLException;

import com.mysql.jdbc.Connection;

public class ConnectionManager {

	private static ConnectionManager cm = new ConnectionManager();

	private ConnectionManager() {

	}

	public static ConnectionManager getInstance() {
		return cm;
	}

	public Connection getConnection() throws SQLException {

		Connection connection = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");

			String url = "jdbc:mysql://127.0.0.1:3306/computer-database-db?zeroDateTimeBehavior=convertToNull";
			connection = (Connection) DriverManager.getConnection(url, "root",
					"root");

		} catch (ClassNotFoundException e) {
			System.err.println("Driver Mysql introuvable");
			e.printStackTrace();
		}
		return connection;
	}
}
