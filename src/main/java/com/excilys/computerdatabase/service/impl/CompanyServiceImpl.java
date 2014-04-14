package com.excilys.computerdatabase.service.impl;

import java.sql.SQLException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.excilys.computerdatabase.domain.Company;
import com.excilys.computerdatabase.domain.Log;
import com.excilys.computerdatabase.persistence.CompanyDAO;
import com.excilys.computerdatabase.persistence.LogDAO;
import com.excilys.computerdatabase.service.CompanyService;

@Service
@Transactional(readOnly = true)
public class CompanyServiceImpl implements CompanyService {

	Logger logger = LoggerFactory.getLogger(CompanyServiceImpl.class);

	@Autowired
	CompanyDAO myCompanyDAO;
	@Autowired
	LogDAO myLogDAO;

	@Override
	@Transactional(readOnly = false)
	public void create(Company c) {

		try {
			logger.debug("company creation -> started");
			myCompanyDAO.create(c);
			Log log = Log.builder().type("Info")
					.description("Creating company. Name = " + c.getName())
					.build();
			myLogDAO.create(log);
		} catch (SQLException e) {
			logger.error("Erreur lors de la création. Voir CompanDAO->create()");
		}
		logger.debug("company creation -> ended");
	}

	@Override
	@Transactional(readOnly = false)
	public Company retrieveById(Long id) {
		Company company = null;

		try {
			logger.debug("company retrievement by id-> started");
			company = myCompanyDAO.retrieveById(id);
			Log log = Log.builder().type("Info")
					.description("Looking for company n° " + id).build();
			myLogDAO.create(log);
		} catch (SQLException e) {
			logger.error("Erreur de chargement depuis la base. Voir CompanyDAO->retrieveList()");
		}
		logger.debug("company retrievement by id-> ended");
		return company;
	}

	@Override
	@Transactional(readOnly = false)
	public List<Company> retrieveList() {
		List<Company> companyList = null;

		try {
			logger.debug("companyList retrievement -> started");
			companyList = myCompanyDAO.retrieveList();
			Log log = Log.builder().type("Info")
					.description("Looking for the whole company list").build();
			myLogDAO.create(log);
		} catch (SQLException e) {
			logger.error("Erreur de chargement depuis la base. Voir CompanyDAO->retrieveList()");
		}
		logger.debug("companyList retrievement -> ended");
		return companyList;
	}
}
