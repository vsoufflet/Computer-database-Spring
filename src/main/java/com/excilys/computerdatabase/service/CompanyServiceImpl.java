package com.excilys.computerdatabase.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.excilys.computerdatabase.domain.Company;
import com.excilys.computerdatabase.domain.Log;
import com.excilys.computerdatabase.persistence.CompanyDAO;
import com.excilys.computerdatabase.persistence.ConnectionJDBC;
import com.excilys.computerdatabase.persistence.LogDAO;

@Service
public class CompanyServiceImpl implements CompanyServiceInterface {

	Logger logger = LoggerFactory.getLogger(CompanyServiceImpl.class);

	@Autowired
	CompanyDAO myCompanyDAO;
	@Autowired
	LogDAO myLogDAO;
	@Autowired
	ConnectionJDBC connectionJDBC;

	@Override
	public void create(Company c) {
		Connection conn = connectionJDBC.startConnection();

		try {
			logger.debug("company creation -> started");
			myCompanyDAO.create(c);
			Log log = Log.builder().type("Info")
					.description("Creating company. Name = " + c.getName())
					.build();
			myLogDAO.create(log);
			conn.commit();
		} catch (SQLException e) {
			logger.error("Erreur lors de la création. Voir CompanDAO->create()");
			try {
				conn.rollback();
			} catch (SQLException e1) {
				logger.error("Could not rollback");
				e1.printStackTrace();
			}
			e.printStackTrace();
		} finally {
			// connectionJDBC.close(conn);
		}
		logger.debug("company creation -> ended");
	}

	@Override
	public Company retrieveById(Long id) {
		Company company = null;
		Connection conn = connectionJDBC.startConnection();

		try {
			logger.debug("company retrievement by id-> started");
			company = myCompanyDAO.retrieveById(id);
			Log log = Log.builder().type("Info")
					.description("Looking for company n° " + id).build();
			myLogDAO.create(log);
			conn.commit();
		} catch (SQLException e) {
			logger.error("Erreur de chargement depuis la base. Voir CompanyDAO->retrieveList()");
			try {
				conn.rollback();
			} catch (SQLException e1) {
				logger.error("Could not rollback");
				e1.printStackTrace();
			}
			e.printStackTrace();
		} finally {
			// connectionJDBC.close(conn);
		}
		logger.debug("company retrievement by id-> ended");
		return company;
	}

	@Override
	public List<Company> retrieveList() {
		List<Company> companyList = null;
		Connection conn = connectionJDBC.startConnection();

		try {
			logger.debug("companyList retrievement -> started");
			companyList = myCompanyDAO.retrieveList();
			Log log = Log.builder().type("Info")
					.description("Looking for the whole company list").build();
			myLogDAO.create(log);
			conn.commit();
		} catch (SQLException e) {
			logger.error("Erreur de chargement depuis la base. Voir CompanyDAO->retrieveList()");
			try {
				conn.rollback();
			} catch (SQLException e1) {
				logger.error("Could not rollback");
				e1.printStackTrace();
			}
			e.printStackTrace();
		} finally {
			// connectionJDBC.close(conn);
		}
		logger.debug("companyList retrievement -> ended");
		return companyList;
	}
}
