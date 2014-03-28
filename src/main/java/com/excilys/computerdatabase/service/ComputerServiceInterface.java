package com.excilys.computerdatabase.service;

import java.util.List;

import com.excilys.computerdatabase.domain.Computer;
import com.excilys.computerdatabase.domain.PageWrapper;
import com.excilys.computerdatabase.persistence.ComputerDAO;

public interface ComputerServiceInterface {

	ComputerDAO myComputerDAO = ComputerDAO.getInstance();

	public void create(Computer c);

	public Computer retrieveByName(String name);

	public Computer retrieveById(Long id);

	public List<Computer> retrieveList(PageWrapper pw);

	public List<Computer> retrieveListByCompany(PageWrapper pw);

	public void delete(Computer c);

}
