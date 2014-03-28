package com.excilys.computerdatabase.persistence;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.excilys.computerdatabase.domain.Company;
import com.excilys.computerdatabase.domain.Computer;
import com.excilys.computerdatabase.domain.PageWrapper;
import com.excilys.computerdatabase.service.CompanyServiceImpl;

public class ComputerDAO {

	CompanyServiceImpl companyService = CompanyServiceImpl.getInstance();
	ConnectionJDBC connectionJDBC = ConnectionJDBC.getInstance();

	private static ComputerDAO myDAO = new ComputerDAO();

	private ComputerDAO() {

	}

	public static ComputerDAO getInstance() {
		return myDAO;
	}

	public void create(Computer c) throws SQLException {

		Connection conn = connectionJDBC.getConnection();
		PreparedStatement ps = null;

		String query = "INSERT into computer (name,introduced,discontinued,company_id) VALUES(?,?,?,?)";
		ps = conn.prepareStatement(query);

		ps.setString(1, c.getName());
		ps.setDate(2, new Date(c.getIntroduced().getTime()));
		ps.setDate(3, new Date(c.getDiscontinued().getTime()));
		ps.setObject(4, c.getCompany().getId());

		ps.executeUpdate();

		ps.close();
	}

	public Computer retrieveByName(String name) throws SQLException {

		Connection conn = connectionJDBC.getConnection();
		PreparedStatement ps = null;
		String query = "SELECT * FROM computer WHERE name=?";
		ResultSet rs = null;
		Computer computer = new Computer();

		ps = conn.prepareStatement(query);
		ps.setString(1, name);
		rs = ps.executeQuery();

		rs.next();
		computer.setId(rs.getLong(1));
		computer.setName(rs.getString(2));
		computer.setIntroduced(rs.getDate(3));
		computer.setDiscontinued(rs.getDate(4));

		Company company = companyService.retrieveById(rs.getLong(5));
		computer.setCompany(company);

		rs.close();
		ps.close();
		return computer;
	}

	public Computer retrieveById(Long id) throws SQLException {

		Connection conn = connectionJDBC.getConnection();
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
			computer.setIntroduced(rs.getDate(3));
			computer.setDiscontinued(rs.getDate(4));
			Company company = companyService.retrieveById(rs.getLong(5));
			computer.setCompany(company);
		}
		rs.close();
		ps.close();
		return computer;
	}

	public void delete(Computer c) throws SQLException {

		Connection conn = connectionJDBC.getConnection();
		PreparedStatement ps = null;
		String query = "DELETE FROM computer WHERE id=?";

		ps = conn.prepareStatement(query);
		ps.setLong(1, c.getId());

		ps.executeUpdate();

		ps.close();
	}

	public List<Computer> retrieveAll(PageWrapper pw) throws SQLException {

		Connection conn = connectionJDBC.getConnection();
		List<Computer> computerList = new ArrayList<Computer>();
		PreparedStatement ps = null;
		StringBuilder query = new StringBuilder();
		ResultSet rs = null;

		if (pw.getSearch().equalsIgnoreCase("default")
				|| pw.getSearch().equalsIgnoreCase("")) {
			query = query.append("SELECT * FROM computer");
		} else {
			query = query.append("SELECT * FROM computer WHERE name LIKE ?");
		}
		if (!pw.getOrderBy().equalsIgnoreCase("default")) {
			query = query.append(" ORDER BY " + pw.getOrderBy());

			if (!pw.getWay().equalsIgnoreCase("default")) {
				query = query.append(" " + pw.getWay());
			}
		}
		// query = query.append(" LIMIT ?, ?");
		String sqlQuery = query.toString();
		ps = conn.prepareStatement(sqlQuery);

		if (!pw.getSearch().equalsIgnoreCase("default")
				&& !pw.getSearch().equalsIgnoreCase("")) {
			ps.setString(1, "%" + pw.getSearch() + "%");
			/*
			 * ps.setInt(2, pw.getOffset()); ps.setInt(3,
			 * pw.getComputersPerPage()); } else { ps.setInt(1, pw.getOffset());
			 * ps.setInt(2, pw.getComputersPerPage());
			 */
		}

		rs = ps.executeQuery();

		while (rs.next()) {
			Computer computer = new Computer();
			computer.setId(rs.getLong(1));
			computer.setName(rs.getString(2));
			computer.setIntroduced(rs.getDate(3));
			computer.setDiscontinued(rs.getDate(4));
			Company company = companyService.retrieveById(rs.getLong(5));
			computer.setCompany(company);

			computerList.add(computer);
		}

		rs.close();
		ps.close();

		return computerList;
	}

	public List<Computer> retrieveAllByCompany(PageWrapper pw)
			throws SQLException {

		Connection conn = connectionJDBC.getConnection();
		List<Computer> computerList = new ArrayList<Computer>();
		PreparedStatement ps = null;
		StringBuilder query = new StringBuilder();
		ResultSet rs = null;

		if (pw.getSearch().equalsIgnoreCase("default")
				|| pw.getSearch().equalsIgnoreCase("")) {
			query = query.append("SELECT * FROM computer");
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
					.append("SELECT * FROM computer INNER JOIN company ON computer.company_id = company.id WHERE company.name LIKE ?");
			if (!pw.getOrderBy().equalsIgnoreCase("default")) {
				query = query.append(" ORDER BY " + pw.getOrderBy());

				if (!pw.getWay().equalsIgnoreCase("default")) {
					query = query.append(" " + pw.getWay());
				}
			}
		}

		// query = query.append(" LIMIT ?, ?");
		String sqlQuery = query.toString();
		ps = conn.prepareStatement(sqlQuery);

		if (!pw.getSearch().equalsIgnoreCase("default")
				&& !pw.getSearch().equalsIgnoreCase("")) {
			ps.setString(1, "%" + pw.getSearch() + "%");
			/*
			 * ps.setInt(2, pw.getOffset()); ps.setInt(3,
			 * pw.getComputersPerPage()); } else { ps.setInt(1, pw.getOffset());
			 * ps.setInt(2, pw.getComputersPerPage());
			 */
		}

		rs = ps.executeQuery();

		while (rs.next()) {
			Computer computer = new Computer();
			computer.setId(rs.getLong(1));
			computer.setName(rs.getString(2));
			computer.setIntroduced(rs.getDate(3));
			computer.setDiscontinued(rs.getDate(4));
			Company company = companyService.retrieveById(rs.getLong(5));
			computer.setCompany(company);

			computerList.add(computer);
		}

		rs.close();
		ps.close();

		return computerList;
	}
}
