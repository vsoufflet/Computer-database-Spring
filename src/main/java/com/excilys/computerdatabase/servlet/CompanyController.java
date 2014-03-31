package com.excilys.computerdatabase.servlet;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.excilys.computerdatabase.domain.Company;
import com.excilys.computerdatabase.service.CompanyServiceImpl;

public class CompanyController {

	@Autowired
	CompanyServiceImpl companyService;

	@RequestMapping(value = "/getCompanyListforAdding", method = RequestMethod.GET)
	public String getListForAdding(Model model) {

		List<Company> companyList = companyService.retrieveList();

		model.addAttribute("companyList", companyList);
		return "redirect:/addComputer";
	}

	@RequestMapping(value = "/getCompanyListforEditing", method = RequestMethod.GET)
	public String getListForEditing(Model model) {

		List<Company> companyList = companyService.retrieveList();

		model.addAttribute("companyList", companyList);
		return "redirect:/editComputer";
	}
}
