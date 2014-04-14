package com.excilys.computerdatabase.persistence;

import java.sql.SQLException;
import java.util.List;

import com.excilys.computerdatabase.domain.Computer;
import com.excilys.computerdatabase.domain.PageWrapper;

public interface ComputerDAO {

	public void create(Computer c) throws SQLException;

	public Computer retrieveById(Long id) throws SQLException;

	public void update(Computer c) throws SQLException;

	public void delete(Computer c) throws SQLException;

	public List<Computer> retrieveAll(PageWrapper pw) throws SQLException;

	public List<Computer> retrieveAllByCompany(PageWrapper pw)
			throws SQLException;
}
