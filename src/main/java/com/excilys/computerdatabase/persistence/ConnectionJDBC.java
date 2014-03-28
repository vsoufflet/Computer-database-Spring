package com.excilys.computerdatabase.persistence;

import java.sql.Connection;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.jolbox.bonecp.BoneCP;
import com.jolbox.bonecp.BoneCPConfig;

@Repository
public class ConnectionJDBC {

	private static String url = "jdbc:mysql://127.0.0.1:3306/computer-database-db?zeroDateTimeBehavior=convertToNull";
	private static String driver = "com.mysql.jdbc.Driver";
	private static String userName = "root";
	private static String passWord = "root";
	private static BoneCP connectionPool;

	static Logger logger = LoggerFactory.getLogger(ConnectionJDBC.class);

	private static ThreadLocal<Connection> threadLocal = new ThreadLocal<Connection>();

	public BoneCP getConnectionPool() {
		return connectionPool;
	}

	public static void initialise() {

		logger.info("intialising connection pool");
		try {
			Class.forName(driver);
			BoneCPConfig config = new BoneCPConfig();
			config.setJdbcUrl(url);
			config.setUsername(userName);
			config.setPassword(passWord);

			config.setMinConnectionsPerPartition(2);
			config.setMaxConnectionsPerPartition(8);
			config.setPartitionCount(2);

			connectionPool = new BoneCP(config);

			logger.info("intialising connection pool initialised");
		} catch (SQLException | ClassNotFoundException e) {
			logger.error("Could not find the mysql driver");
			e.printStackTrace();
		}
	}

	public Connection getConnection() {
		logger.info("retrieving connection from threadLocal");

		try {
			if (connectionPool == null) {
				initialise();
			}
			if (threadLocal.get() == null) {
				threadLocal.set(getConnectionPool().getConnection());
			}
			logger.info("return connection");
		} catch (SQLException e) {
			logger.error("Erreur lors de la connexion.");
			e.printStackTrace();
		}
		return threadLocal.get();
	}

	public void close(Connection connection) {

		logger.info("connection closing");
		try {
			if (threadLocal != null) {
				threadLocal.remove();
			}
			if (connection != null) {
				connection.close();
			}
		} catch (SQLException e) {
			logger.error("Erreur lors de la fermeture de la connexion.");
			e.printStackTrace();
		}
	}
}
