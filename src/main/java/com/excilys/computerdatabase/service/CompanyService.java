package com.excilys.computerdatabase.service;

import java.util.List;

import com.excilys.computerdatabase.domain.Company;

public interface CompanyService {

	public void create(Company c);

	public Company retrieveById(Long id);

	public List<Company> retrieveList();

}
