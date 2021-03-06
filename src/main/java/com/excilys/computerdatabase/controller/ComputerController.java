package com.excilys.computerdatabase.controller;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.excilys.computerdatabase.domain.Company;
import com.excilys.computerdatabase.domain.Computer;
import com.excilys.computerdatabase.domain.ComputerDTO;
import com.excilys.computerdatabase.domain.ComputerValidator;
import com.excilys.computerdatabase.domain.PageWrapper;
import com.excilys.computerdatabase.mapper.ComputerMapper;
import com.excilys.computerdatabase.service.CompanyService;
import com.excilys.computerdatabase.service.ComputerService;

@Controller
public class ComputerController {
	/**
	 * 
	 */

	// TODO: sort update() method while updating existing dates to empty dates
	// TODO: sort search by company to also have the empty ones
	// TODO: complete mvc pagination
	// TODO: complete name and dates validation, for add and edit features;
	// Think about tags
	// TODO: sort transactions errors
	// TODO: complete internationalization by adapting date validator to each
	// language

	@Autowired
	private ComputerService computerService;
	@Autowired
	private CompanyService companyService;
	@Autowired
	private ComputerMapper cm;
	@Autowired
	private ComputerValidator cv;
	@Autowired
	private PageWrapper pw;
	private static Logger logger = LoggerFactory
			.getLogger(ComputerController.class);

	@RequestMapping(value = "dashboard", method = RequestMethod.GET)
	public ModelAndView displayList(
			@RequestParam(value = "searchBy", required = false, defaultValue = "default") String searchBy,
			@RequestParam(value = "search", required = false, defaultValue = "default") String search,
			@RequestParam(value = "orderBy", required = false, defaultValue = "default") String orderBy,
			@RequestParam(value = "way", required = false, defaultValue = "default") String way) {

		logger.debug("Entering displayList");

		List<ComputerDTO> computerDTOList = new ArrayList<ComputerDTO>();
		List<Computer> computerList = new ArrayList<Computer>();

		pw = PageWrapper.builder().searchBy(searchBy).search(search)
				.orderBy(orderBy).way(way).build();

		if ("computer".equalsIgnoreCase(searchBy)) {

			if (!"company.id".equalsIgnoreCase(orderBy)
					&& !"company.name".equalsIgnoreCase(orderBy)) {
				computerList = computerService.retrieveList(pw);
			} else {
				computerList = computerService.retrieveListByCompany(pw);
			}

		} else if ("company".equalsIgnoreCase(searchBy)) {
			computerList = computerService.retrieveListByCompany(pw);

		} else {

			if (!"company.id".equalsIgnoreCase(orderBy)
					&& !"company.name".equalsIgnoreCase(orderBy)) {
				computerList = computerService.retrieveList(pw);
			} else {
				computerList = computerService.retrieveListByCompany(pw);
			}
		}

		if (computerList != null) {
			for (Computer c : computerList) {
				ComputerDTO computerDTO = cm.toComputerDTO(c);
				computerDTOList.add(computerDTO);
			}
		}
		ModelAndView model = new ModelAndView();
		pw.setComputerDTOList(computerDTOList);
		model.addObject("PageWrapper", pw);

		if (computerDTOList.size() <= 1) {
			model.addObject("NombreOrdinateurs", computerDTOList.size());
		} else {
			model.addObject("NombreOrdinateurs", computerDTOList.size());
		}
		model.setViewName("dashboard");

		logger.debug("Exiting displayList");

		return model;
	}

	@RequestMapping(value = "addComputerForm", method = RequestMethod.GET)
	public ModelAndView addComputerForm(ModelAndView mAndV) {

		List<Company> companyList = companyService.retrieveList();
		ComputerDTO cDTO = new ComputerDTO();

		mAndV.addObject("companyList", companyList);
		mAndV.addObject("computerDTO", cDTO);
		mAndV.setViewName("addComputer");
		return mAndV;
	}

	@RequestMapping(value = "addComputer", method = RequestMethod.POST)
	public String add(@ModelAttribute("computerDTO") @Valid ComputerDTO cDTO,
			BindingResult result) {

		logger.debug("Entering add");

		if (!result.hasErrors()) {
			Computer computer = cm.toComputer(cDTO);
			computerService.create(computer);
			logger.debug("Exiting add");
			return "redirect:/dashboard";

		} else {
			return "redirect:/addComputerForm";
		}
	}

	@RequestMapping(value = "edit", method = RequestMethod.GET)
	public ModelAndView edit(ModelAndView mAndV,
			@RequestParam(value = "id", required = true) Long id) {

		List<Company> companyList = companyService.retrieveList();

		Computer computer = computerService.retrieveById(id);
		ComputerDTO computerDTO = cm.toComputerDTO(computer);

		mAndV.addObject("computerDTO", computerDTO);
		mAndV.addObject("companyList", companyList);
		mAndV.setViewName("editComputer");

		return mAndV;
	}

	@RequestMapping(value = "update", method = RequestMethod.POST)
	public String update(ModelAndView mAndV,
			@ModelAttribute("computerDTO") @Valid ComputerDTO computerDTO,
			BindingResult result) {

		logger.debug("Entering update");

		if (!result.hasErrors()) {
			Computer computer = cm.toComputer(computerDTO);
			computerService.update(computer);
			logger.debug("Exiting update");
			return "redirect:/dashboard";

		} else {
			mAndV.addObject("id", computerDTO.getId());
			return "redirect:/edit";
		}
	}

	@RequestMapping(value = "delete", method = RequestMethod.GET)
	public String delete(
			Model model,
			@RequestParam(value = "searchBy", required = false, defaultValue = "default") String searchBy,
			@RequestParam(value = "search", required = false, defaultValue = "default") String search,
			@RequestParam(value = "orderBy", required = false, defaultValue = "default") String orderBy,
			@RequestParam(value = "way", required = false, defaultValue = "default") String way,
			@RequestParam(value = "id", required = true) Long id) {

		logger.debug("Entering delete");

		List<ComputerDTO> computerDTOList = new ArrayList<ComputerDTO>();
		List<Computer> computerList = new ArrayList<Computer>();

		pw = PageWrapper.builder().searchBy(searchBy).search(search)
				.orderBy(orderBy).way(way).build();

		Computer computer = computerService.retrieveById(id);
		computerService.delete(computer);

		computerList = computerService.retrieveList(pw);
		for (Computer c : computerList) {
			ComputerDTO computerDTO = cm.toComputerDTO(c);
			computerDTOList.add(computerDTO);
		}
		pw = PageWrapper.builder().computerDTOList(computerDTOList).build();

		logger.debug("Exiting delete");

		return "redirect:/dashboard";
	}
}
