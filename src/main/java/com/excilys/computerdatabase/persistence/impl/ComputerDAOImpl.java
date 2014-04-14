package com.excilys.computerdatabase.persistence.impl;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.stereotype.Repository;

import com.excilys.computerdatabase.domain.Company;
import com.excilys.computerdatabase.domain.Computer;
import com.excilys.computerdatabase.domain.PageWrapper;
import com.excilys.computerdatabase.persistence.ComputerDAO;
import com.excilys.computerdatabase.service.CompanyService;
import com.jolbox.bonecp.BoneCPDataSource;

@Repository
public class ComputerDAOImpl implements ComputerDAO {

	@Autowired
	CompanyService companyService;
	@Autowired
	@Qualifier(value = "DataSource")
	BoneCPDataSource ds;

	public void create(Computer c) throws SQLException {

		Connection conn = DataSourceUtils.getConnection(ds);
		PreparedStatement ps = null;
		StringBuilder query = new StringBuilder("INSERT INTO computer (name");
		int numberOfParam = 1;

		if (c.getIntroduced() != null) {
			query.append(", introduced");
		}
		if (c.getDiscontinued() != null) {
			query.append(", discontinued");
		}
		if (c.getCompany() != null) {
			query.append(", company_id");
		}
		query.append(") VALUES(?");

		if (c.getIntroduced() != null) {
			query.append(",?");
		}
		if (c.getDiscontinued() != null) {
			query.append(",?");
		}
		if (c.getCompany() != null) {
			query.append(",?");
		}
		query.append(")");
		ps = conn.prepareStatement(query.toString());

		ps.setString(numberOfParam, c.getName());

		if (c.getIntroduced() != null) {
			numberOfParam++;
			ps.setDate(numberOfParam, new Date(c.getIntroduced().toDate()
					.getTime()));
		}
		if (c.getDiscontinued() != null) {
			numberOfParam++;
			ps.setDate(numberOfParam, new Date(c.getDiscontinued().toDate()
					.getTime()));
		}
		if (c.getCompany() != null) {
			numberOfParam++;
			ps.setLong(numberOfParam, c.getCompany().getId());
		}

		ps.executeUpdate();

		ps.close();
	}

	public Computer retrieveById(Long id) throws SQLException {

		Connection conn = DataSourceUtils.getConnection(ds);
		PreparedStatement ps = null;
		String query = "SELECT * FROM computer WHERE id=?";
		ResultSet rs = null;
		Computer computer = new Computer();

		ps = conn.prepareStatement(query);
		ps.setLong(1, id);
		rs = ps.executeQuery();

		while (rs.next()) {
			computer.setId(rs.getLong(1));
			computer.setName(rs.getString(2));
			if (rs.getDate(3) != null) {
				computer.setIntroduced(new DateTime(rs.getDate(3)));
			}
			if (rs.getDate(4) != null) {
				computer.setDiscontinued(new DateTime(rs.getDate(4)));
			}
			if (rs.getLong(5) != 0) {
				Company company = companyService.retrieveById(rs.getLong(5));
				computer.setCompany(company);
			}
		}
		rs.close();
		ps.close();
		return computer;
	}

	public void update(Computer c) throws SQLException {

		Connection conn = DataSourceUtils.getConnection(ds);
		PreparedStatement ps = null;
		StringBuilder query = new StringBuilder("UPDATE computer SET (name");
		int numberOfParam = 1;

		if (c.getIntroduced() != null) {
			query.append(", introduced");
		}
		if (c.getDiscontinued() != null) {
			query.append(", discontinued");
		}
		if (c.getCompany() != null) {
			query.append(", company_id");
		}

		query.append(") VALUES(?");

		if (c.getIntroduced() != null) {
			query.append(",?");
		}
		if (c.getDiscontinued() != null) {
			query.append(",?");
		}
		if (c.getCompany() != null) {
			query.append(",?");
		}

		query.append(") WHERE id=?");

		ps = conn.prepareStatement(query.toString());

		ps.setString(numberOfParam, c.getName());

		if (c.getIntroduced() != null) {
			numberOfParam++;
			ps.setDate(numberOfParam, new Date(c.getIntroduced().toDate()
					.getTime()));
		} else {
			numberOfParam++;
			ps.setDate(numberOfParam, null);
		}
		if (c.getDiscontinued() != null) {
			numberOfParam++;
			ps.setDate(numberOfParam, new Date(c.getDiscontinued().toDate()
					.getTime()));
		} else {
			numberOfParam++;
			ps.setDate(numberOfParam, null);
		}
		if (c.getCompany() != null) {
			numberOfParam++;
			ps.setLong(numberOfParam, c.getCompany().getId());
		}
		numberOfParam++;
		ps.setLong(numberOfParam, c.getId());

		ps.executeUpdate();

		ps.close();
	}

	public void delete(Computer c) throws SQLException {

		Connection conn = DataSourceUtils.getConnection(ds);
		PreparedStatement ps = null;
		String query = "DELETE FROM computer WHERE id=?";

		ps = conn.prepareStatement(query);
		ps.setLong(1, c.getId());

		ps.executeUpdate();

		ps.close();
	}

	public List<Computer> retrieveAll(PageWrapper pw) throws SQLException {

		Connection conn = DataSourceUtils.getConnection(ds);
		List<Computer> computerList = new ArrayList<Computer>();
		PreparedStatement ps = null;
		StringBuilder query = new StringBuilder();
		// String numberOfLines = "SELECT FOUND_ROWS()";
		ResultSet rs = null;

		if (pw.getSearch().equalsIgnoreCase("default")
				|| pw.getSearch().equalsIgnoreCase("")) {
			query = query.append("SELECT SQL_CALC_FOUND_ROWS * FROM computer");
		} else {
			query = query
					.append("SELECT SQL_CALC_FOUND_ROWS * FROM computer WHERE name LIKE ?");
		}
		if (!pw.getOrderBy().equalsIgnoreCase("default")) {
			query = query.append(" ORDER BY " + pw.getOrderBy());

			if (!pw.getWay().equalsIgnoreCase("default")) {
				query = query.append(" " + pw.getWay());
			}
		}
		query = query.append(" LIMIT ?, ?");
		String sqlQuery = query.toString();
		ps = conn.prepareStatement(sqlQuery);

		if (!pw.getSearch().equalsIgnoreCase("default")
				&& !pw.getSearch().equalsIgnoreCase("")) {
			ps.setString(1, "%" + pw.getSearch() + "%");

			ps.setInt(2, pw.getOffset());
			ps.setInt(3, pw.getComputersPerPage());
		} else {
			ps.setInt(1, pw.getOffset());
			ps.setInt(2, pw.getComputersPerPage());

		}

		rs = ps.executeQuery();

		while (rs.next()) {
			Computer computer = new Computer();
			computer.setId(rs.getLong(1));
			computer.setName(rs.getString(2));
			if (rs.getDate(3) != null) {
				computer.setIntroduced(new DateTime(rs.getDate(3)));
			}
			if (rs.getDate(4) != null) {
				computer.setDiscontinued(new DateTime(rs.getDate(4)));
			}
			if (rs.getLong(5) != 0) {
				Company company = companyService.retrieveById(rs.getLong(5));
				computer.setCompany(company);
			}

			computerList.add(computer);
		}

		rs.close();
		ps.close();

		return computerList;
	}

	public List<Computer> retrieveAllByCompany(PageWrapper pw)
			throws SQLException {

		Connection conn = DataSourceUtils.getConnection(ds);
		List<Computer> computerList = new ArrayList<Computer>();
		PreparedStatement ps = null;
		StringBuilder query = new StringBuilder();
		// String numberOfLines = "SELECT FOUND_ROWS()";
		ResultSet rs = null;

		if (pw.getSearch().equalsIgnoreCase("default")
				|| pw.getSearch().equalsIgnoreCase("")) {
			query = query.append("SELECT SQL_CALC_FOUND_ROWS * FROM computer");
			if (!pw.getOrderBy().equalsIgnoreCase("default")) {
				query = query
						.append(" INNER JOIN company ON computer.company_id = company.id ORDER BY "
								+ pw.getOrderBy());
				if (!pw.getWay().equalsIgnoreCase("default")) {
					query = query.append(" " + pw.getWay());
				}
			}
		} else {
			query = query
					.append("SELECT SQL_CALC_FOUND_ROWS * FROM computer INNER JOIN company ON computer.company_id = company.id WHERE company.name LIKE ?");
			if (!pw.getOrderBy().equalsIgnoreCase("default")) {
				query = query.append(" ORDER BY " + pw.getOrderBy());

				if (!pw.getWay().equalsIgnoreCase("default")) {
					query = query.append(" " + pw.getWay());
				}
			}
		}

		query = query.append(" LIMIT ?, ?");
		String sqlQuery = query.toString();
		ps = conn.prepareStatement(sqlQuery);

		if (!pw.getSearch().equalsIgnoreCase("default")
				&& !pw.getSearch().equalsIgnoreCase("")) {
			ps.setString(1, "%" + pw.getSearch() + "%");

			ps.setInt(2, pw.getOffset());
			ps.setInt(3, pw.getComputersPerPage());
		} else {
			ps.setInt(1, pw.getOffset());
			ps.setInt(2, pw.getComputersPerPage());
		}

		rs = ps.executeQuery();

		while (rs.next()) {
			Computer computer = new Computer();
			computer.setId(rs.getLong(1));
			computer.setName(rs.getString(2));
			if (rs.getDate(3) != null) {
				computer.setIntroduced(new DateTime(rs.getDate(3)));
			}
			if (rs.getDate(4) != null) {
				computer.setDiscontinued(new DateTime(rs.getDate(4)));
			}
			if (rs.getLong(5) != 0) {
				Company company = companyService.retrieveById(rs.getLong(5));
				computer.setCompany(company);
			}

			computerList.add(computer);
		}

		rs.close();
		ps.close();

		return computerList;
	}
}
