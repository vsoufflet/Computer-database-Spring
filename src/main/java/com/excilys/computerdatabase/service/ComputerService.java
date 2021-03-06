package com.excilys.computerdatabase.service;

import java.util.List;

import com.excilys.computerdatabase.domain.Computer;
import com.excilys.computerdatabase.domain.PageWrapper;

public interface ComputerService {

	public void create(Computer c);

	public Computer retrieveById(Long id);

	public void update(Computer c);

	public List<Computer> retrieveList(PageWrapper pw);

	public List<Computer> retrieveListByCompany(PageWrapper pw);

	public void delete(Computer c);

}
