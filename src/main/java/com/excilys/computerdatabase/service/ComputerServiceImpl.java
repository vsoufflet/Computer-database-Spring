package com.excilys.computerdatabase.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.excilys.computerdatabase.domain.Computer;
import com.excilys.computerdatabase.domain.Log;
import com.excilys.computerdatabase.domain.PageWrapper;
import com.excilys.computerdatabase.persistence.ComputerDAO;
import com.excilys.computerdatabase.persistence.ConnectionJDBC;
import com.excilys.computerdatabase.persistence.LogDAO;

@Service
public class ComputerServiceImpl implements ComputerServiceInterface {

	private static Logger logger = LoggerFactory
			.getLogger(ComputerServiceImpl.class);

	@Autowired
	ComputerDAO myComputerDAO;
	@Autowired
	LogDAO myLogDAO;
	@Autowired
	ConnectionJDBC connectionJDBC;

	@Override
	public void create(Computer c) {

		Connection conn = connectionJDBC.startConnection();
		try {
			logger.debug("computer creation -> started");
			Log log = Log.builder().type("Info")
					.description("Creating computer. Name = " + c.getName())
					.build();
			myLogDAO.create(log);
			myComputerDAO.create(c);

			conn.commit();

		} catch (SQLException e) {
			logger.error("Erreur lors de la création. Voir ComputerDAO->create()");
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
		logger.debug("computer creation -> ended");
	}

	@Override
	public Computer retrieveById(Long id) {
		Computer computer = null;
		Connection conn = connectionJDBC.startConnection();

		try {
			logger.debug("computer retrievement by id-> started");
			computer = myComputerDAO.retrieveById(id);
			Log log = Log.builder().type("Info")
					.description("Looking for computer n° " + computer.getId())
					.build();
			myLogDAO.create(log);

			conn.commit();
		} catch (SQLException e) {
			logger.error("Erreur de connexion. Voir ComputerDAO->retrieveById()");
			try {
				conn.rollback();
			} catch (SQLException e1) {
				logger.error("Could not rollback.");
				e1.printStackTrace();
			}
			e.printStackTrace();
		} finally {
			// connectionJDBC.close(conn);
		}
		logger.debug("computerlist retrievement by id-> ended");
		return computer;
	}

	@Override
	public void update(Computer c) {
		Connection conn = connectionJDBC.startConnection();

		try {
			logger.debug("computer updating -> started");
			myComputerDAO.update(c);
			Log log = Log.builder().type("Info")
					.description("Updating computer n° ").build();
			myLogDAO.create(log);
			conn.commit();
		} catch (SQLException e) {
			logger.error("Erreur de connexion. Voir ComputerDAO->update()");
			try {
				conn.rollback();
			} catch (SQLException e1) {
				logger.error("Could not rollback.");
				e1.printStackTrace();
			}
			e.printStackTrace();
		} finally {
			// connectionJDBC.close(conn);
		}
		logger.debug("computer updating -> endeded");
	}

	@Override
	public List<Computer> retrieveList(PageWrapper pw) {
		List<Computer> computerList = null;
		Connection conn = connectionJDBC.startConnection();

		try {
			logger.debug("computerlist retrievement -> started");
			computerList = myComputerDAO.retrieveAll(pw);
			Log log = Log.builder().type("Info")
					.description("Looking for the whole computer list").build();
			myLogDAO.create(log);
			conn.commit();
		} catch (SQLException e) {
			logger.error("Erreur de connexion. Voir ComputerDAO->retrieveAll()");
			try {
				conn.rollback();
			} catch (SQLException e1) {
				logger.error("Could not rollback.");
				e1.printStackTrace();
			}
			e.printStackTrace();
		} finally {
			// connectionJDBC.close(conn);
		}
		logger.debug("computerlist retrievement -> ended");
		return computerList;
	}

	@Override
	public List<Computer> retrieveListByCompany(PageWrapper pw) {
		List<Computer> computerList = null;
		Connection conn = connectionJDBC.startConnection();

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
			conn.commit();
		} catch (SQLException e) {
			logger.error("Erreur de connexion. Voir ComputerDAO->retrieveAllByCompany");
			try {
				conn.rollback();
			} catch (SQLException e1) {
				logger.error("Could not rollback.");
				e1.printStackTrace();
			}
			e.printStackTrace();
		} finally {
			// connectionJDBC.close(conn);
		}
		logger.debug("computerlist retrievement -> ended");
		return computerList;
	}

	@Override
	public void delete(Computer c) {
		Connection conn = connectionJDBC.startConnection();

		try {
			logger.debug("computer deleting -> started");
			myComputerDAO.delete(c);
			Log log = Log.builder().type("Info")
					.description("Deleting computer n° ").build();
			myLogDAO.create(log);
			conn.commit();
		} catch (SQLException e) {
			logger.error("Erreur de connexion. Voir ComputerDAO->delete()");
			try {
				conn.rollback();
			} catch (SQLException e1) {
				logger.error("Could not rollback.");
				e1.printStackTrace();
			}
			e.printStackTrace();
		} finally {
			// connectionJDBC.close(conn);
		}
		logger.debug("computer deleting -> endeded");
	}
}
