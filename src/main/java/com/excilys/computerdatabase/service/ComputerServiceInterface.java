package com.excilys.computerdatabase.service;

import java.util.List;

import com.excilys.computerdatabase.domain.Computer;
import com.excilys.computerdatabase.domain.PageWrapper;

public interface ComputerServiceInterface {

	public void create(Computer c);

	public Computer retrieveByName(String name);

	public Computer retrieveById(Long id);

	public List<Computer> retrieveList(PageWrapper pw);

	public List<Computer> retrieveListByCompany(PageWrapper pw);

	public void delete(Computer c);

}
