package com.excilys.computerdatabase.persistence;

import java.sql.SQLException;
import java.util.List;

import com.excilys.computerdatabase.domain.Company;

public interface CompanyDAO {

	public void create(Company c) throws SQLException;

	public Company retrieveById(Long id) throws SQLException;

	public List<Company> retrieveList() throws SQLException;

}
