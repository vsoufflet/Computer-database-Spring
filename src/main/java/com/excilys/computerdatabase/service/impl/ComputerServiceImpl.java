package com.excilys.computerdatabase.service.impl;

import java.sql.SQLException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.excilys.computerdatabase.domain.Computer;
import com.excilys.computerdatabase.domain.Log;
import com.excilys.computerdatabase.domain.PageWrapper;
import com.excilys.computerdatabase.persistence.ComputerDAO;
import com.excilys.computerdatabase.persistence.LogDAO;
import com.excilys.computerdatabase.service.ComputerService;

@Service
@Transactional(readOnly = true)
public class ComputerServiceImpl implements ComputerService {

	private static Logger logger = LoggerFactory
			.getLogger(ComputerServiceImpl.class);

	@Autowired
	ComputerDAO myComputerDAO;
	@Autowired
	LogDAO myLogDAO;

	@Override
	@Transactional(readOnly = false)
	public void create(Computer c) {

		try {
			logger.debug("computer creation -> started");
			Log log = Log.builder().type("Info")
					.description("Creating computer. Name = " + c.getName())
					.build();

			myLogDAO.create(log);

			myComputerDAO.create(c);

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		logger.debug("computer creation -> ended");
	}

	@Override
	@Transactional(readOnly = false)
	public Computer retrieveById(Long id) {
		Computer computer = null;

		try {
			logger.debug("computer retrievement by id-> started");
			computer = myComputerDAO.retrieveById(id);
			Log log = Log.builder().type("Info")
					.description("Looking for computer n° " + computer.getId())
					.build();
			myLogDAO.create(log);

		} catch (SQLException e) {
			logger.error("Erreur de connexion. Voir ComputerDAO->retrieveById()");
		}
		logger.debug("computerlist retrievement by id-> ended");
		return computer;
	}

	@Override
	@Transactional(readOnly = false)
	public void update(Computer c) {

		try {
			logger.debug("computer updating -> started");
			myComputerDAO.update(c);
			Log log = Log.builder().type("Info")
					.description("Updating computer n° ").build();
			myLogDAO.create(log);
		} catch (SQLException e) {
			logger.error("Erreur de connexion. Voir ComputerDAO->update()");
			e.printStackTrace();
		}
		logger.debug("computer updating -> endeded");
	}

	@Override
	@Transactional(readOnly = false)
	public List<Computer> retrieveList(PageWrapper pw) {
		List<Computer> computerList = null;

		try {
			logger.debug("computerlist retrievement -> started");
			computerList = myComputerDAO.retrieveAll(pw);
			Log log = Log.builder().type("Info")
					.description("Looking for the whole computer list").build();
			myLogDAO.create(log);
		} catch (SQLException e) {
			logger.error("Erreur de connexion. Voir ComputerDAO->retrieveAll()");
		}
		logger.debug("computerlist retrievement -> ended");
		return computerList;
	}

	@Override
	@Transactional(readOnly = false)
	public List<Computer> retrieveListByCompany(PageWrapper pw) {
		List<Computer> computerList = null;

		try {
			logger.debug("computerlist retrievement by Company-> started");
			computerList = myComputerDAO.retrieveAllByCompany(pw);
			Log log = Log
					.builder()
					.type("Info")
					.description(
							"Looking for the whole computer list via their company")
					.build();
			myLogDAO.create(log);
		} catch (SQLException e) {
			logger.error("Erreur de connexion. Voir ComputerDAO->retrieveAllByCompany");
		}
		logger.debug("computerlist retrievement -> ended");
		return computerList;
	}

	@Override
	@Transactional(readOnly = false)
	public void delete(Computer c) {

		try {
			logger.debug("computer deleting -> started");
			myComputerDAO.delete(c);
			Log log = Log.builder().type("Info")
					.description("Deleting computer n° ").build();
			myLogDAO.create(log);
		} catch (SQLException e) {
			logger.error("Erreur de connexion. Voir ComputerDAO->delete()");
		}
		logger.debug("computer deleting -> endeded");
	}
}
