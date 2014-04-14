package com.excilys.computerdatabase.persistence.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.stereotype.Repository;

import com.excilys.computerdatabase.domain.Log;
import com.excilys.computerdatabase.persistence.LogDAO;
import com.jolbox.bonecp.BoneCPDataSource;

@Repository
public class LogDAOImpl implements LogDAO {

	@Autowired
	@Qualifier(value = "DataSource")
	BoneCPDataSource ds;

	public void create(Log log) throws SQLException {

		Connection conn = DataSourceUtils.getConnection(ds);
		PreparedStatement ps = null;

		String query = "INSERT into log (type, description) VALUES(?,?)";

		ps = conn.prepareStatement(query);

		ps.setString(1, log.getType());
		ps.setString(2, log.getDescription());

		ps.executeUpdate();

		ps.close();
	}

	public List<Log> retrieveAll() throws SQLException {

		Connection conn = DataSourceUtils.getConnection(ds);
		List<Log> logList = new ArrayList<Log>();
		Statement stmt = null;
		ResultSet rs = null;

		String query = "SELECT * FROM log";

		stmt = conn.createStatement();
		rs = stmt.executeQuery(query);

		while (rs.next()) {
			Log log = new Log();
			log.setId(rs.getLong(1));
			log.setType(rs.getString(3));
			log.setDescription(rs.getString(4));
			logList.add(log);
		}

		rs.close();
		stmt.close();

		return logList;
	}

	public void deleteAll() throws SQLException {

		Connection conn = DataSourceUtils.getConnection(ds);
		PreparedStatement ps = null;

		String query = "DELETE * FROM log";

		ps = conn.prepareStatement(query);
		ps.executeUpdate();

		ps.close();
	}
}
