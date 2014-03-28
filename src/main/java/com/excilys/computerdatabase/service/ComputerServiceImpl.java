package com.excilys.computerdatabase.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.computerdatabase.domain.Computer;
import com.excilys.computerdatabase.domain.Log;
import com.excilys.computerdatabase.domain.PageWrapper;
import com.excilys.computerdatabase.persistence.ComputerDAO;
import com.excilys.computerdatabase.persistence.ConnectionJDBC;
import com.excilys.computerdatabase.persistence.LogDAO;

public class ComputerServiceImpl implements ComputerServiceInterface {

	private static ComputerServiceImpl myComputerService = new ComputerServiceImpl();

	ComputerDAO myComputerDAO = ComputerDAO.getInstance();
	LogDAO myLogDAO = LogDAO.getInstance();
	Logger logger = LoggerFactory.getLogger(ComputerServiceImpl.class);

	ConnectionJDBC connectionJDBC = ConnectionJDBC.getInstance();

	private ComputerServiceImpl() {

	}

	public static ComputerServiceImpl getInstance() {
		return myComputerService;
	}

	@Override
	public void create(Computer c) {

		Connection conn = connectionJDBC.getConnection();
		try {
			logger.info("computer creation -> started");
			conn.setAutoCommit(false);
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
		logger.info("computer creation -> ended");
	}

	@Override
	public Computer retrieveByName(String name) {
		Computer computer = null;
		Connection conn = connectionJDBC.getConnection();

		try {
			logger.info("computer retrievement by name -> started");
			conn.setAutoCommit(false);
			computer = myComputerDAO.retrieveByName(name);
			Log log = Log
					.builder()
					.type("Info")
					.description(
							"Looking for computer which name is "
									+ computer.getName()).build();
			myLogDAO.create(log);

			conn.commit();

		} catch (SQLException e) {
			logger.error("Erreur de connexion. Voir ComputerDAO->retrieveByName()");
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
		logger.info("computer retrievement by name-> ended");
		return computer;
	}

	@Override
	public Computer retrieveById(Long id) {
		Computer computer = null;
		Connection conn = connectionJDBC.getConnection();

		try {
			logger.info("computer retrievement by id-> started");
			conn.setAutoCommit(false);
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
		logger.info("computerlist retrievement by id-> ended");
		return computer;
	}

	@Override
	public List<Computer> retrieveList(PageWrapper pw) {
		List<Computer> computerList = null;
		Connection conn = connectionJDBC.getConnection();

		try {
			logger.info("computerlist retrievement -> started");
			conn.setAutoCommit(false);
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
		logger.info("computerlist retrievement -> ended");
		return computerList;
	}

	@Override
	public List<Computer> retrieveListByCompany(PageWrapper pw) {
		List<Computer> computerList = null;
		Connection conn = connectionJDBC.getConnection();

		try {
			logger.info("computerlist retrievement by Company-> started");
			conn.setAutoCommit(false);
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
		logger.info("computerlist retrievement -> ended");
		return computerList;
	}

	@Override
	public void delete(Computer c) {
		Connection conn = connectionJDBC.getConnection();

		try {
			logger.info("computerlist deleting -> started");
			conn.setAutoCommit(false);
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
		logger.info("computerlist deleting -> endeded");
	}

}
