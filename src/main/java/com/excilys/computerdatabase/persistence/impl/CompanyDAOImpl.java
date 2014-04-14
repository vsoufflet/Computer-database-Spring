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

import com.excilys.computerdatabase.domain.Company;
import com.excilys.computerdatabase.persistence.CompanyDAO;
import com.jolbox.bonecp.BoneCPDataSource;

@Repository
public class CompanyDAOImpl implements CompanyDAO {

	@Autowired
	@Qualifier(value = "DataSource")
	BoneCPDataSource ds;

	public void create(Company c) throws SQLException {

		Connection conn = DataSourceUtils.getConnection(ds);
		PreparedStatement ps = null;
		String query = "INSERT into computer (name) VALUES(?)";

		ps = conn.prepareStatement(query);

		ps.setString(1, c.getName());

		ps.executeUpdate();

		ps.close();
	}

	public Company retrieveById(Long id) throws SQLException {

		Connection conn = DataSourceUtils.getConnection(ds);
		PreparedStatement ps = null;
		String query = "SELECT * FROM company WHERE id=?";
		ResultSet rs = null;
		Company company = new Company();

		ps = conn.prepareStatement(query);
		ps.setLong(1, id);
		rs = ps.executeQuery();

		while (rs.next()) {
			company.setId(rs.getLong(1));
			company.setName(rs.getString(2));
		}
		rs.close();
		ps.close();
		return company;
	}

	public List<Company> retrieveList() throws SQLException {

		Connection conn = DataSourceUtils.getConnection(ds);
		Statement stmt = null;
		String query = "SELECT id,name FROM company";
		ResultSet rs = null;
		List<Company> companyList = new ArrayList<Company>();

		stmt = (Statement) conn.createStatement();
		rs = stmt.executeQuery(query);

		while (rs.next()) {

			Company c = new Company();
			c.setId(rs.getLong(1));
			c.setName(rs.getString(2));

			companyList.add(c);
		}
		rs.close();
		stmt.close();

		return companyList;
	}
}
