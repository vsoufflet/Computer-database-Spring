package com.excilys.computerdatabase.persistence;

import java.sql.SQLException;
import java.util.List;

import com.excilys.computerdatabase.domain.Log;

public interface LogDAO {

	public void create(Log log) throws SQLException;

	public List<Log> retrieveAll() throws SQLException;

	public void deleteAll() throws SQLException;
}
