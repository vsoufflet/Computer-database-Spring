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
import com.excilys.computerdatabase.service.CompanyServiceImpl;
import com.excilys.computerdatabase.service.ComputerServiceImpl;

@Controller
public class ComputerController {
	/**
	 * 
	 */

	// TODO: complete mvc pagination
	// TODO: complete name and dates validation, for add and edit features
	// TODO: sort transactions errors
	@Autowired
	private ComputerServiceImpl computerService;
	@Autowired
	private CompanyServiceImpl companyService;
	@Autowired
	private ComputerMapper cm;
	@Autowired
	private ComputerValidator cv;
	@Autowired
	private PageWrapper pw;
	private static Logger logger = LoggerFactory
			.getLogger(ComputerController.class);

	@RequestMapping(value = "/dashboard", method = RequestMethod.GET)
	public String displayList(
			Model model,
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

		for (Computer c : computerList) {
			ComputerDTO computerDTO = cm.toComputerDTO(c);
			computerDTOList.add(computerDTO);
		}

		pw.setComputerDTOList(computerDTOList);
		model.addAttribute("PageWrapper", pw);

		if (computerDTOList.size() <= 1) {
			model.addAttribute("NombreOrdinateurs", computerDTOList.size());
		} else {
			model.addAttribute("NombreOrdinateurs", computerDTOList.size());
		}
		logger.debug("Exiting displayList");

		return "dashboard";
	}

	@RequestMapping(value = "/addComputerForm", method = RequestMethod.GET)
	public ModelAndView addComputerForm(ModelAndView mAndV) {

		List<Company> companyList = companyService.retrieveList();
		ComputerDTO cDTO = new ComputerDTO();

		mAndV.addObject("companyList", companyList);
		mAndV.addObject("computerDTO", cDTO);
		mAndV.setViewName("addComputer");
		return mAndV;
	}

	@RequestMapping(value = "/addComputer", method = RequestMethod.POST)
	public String add(
			@ModelAttribute("computerDTO") @Valid ComputerDTO cDTO,
			BindingResult result,
			@RequestParam(value = "name", required = true) String name,
			@RequestParam(value = "introduced", required = false) String introduced,
			@RequestParam(value = "discontinued", required = false) String discontinued,
			@RequestParam(value = "company", required = false) Long companyId) {

		logger.debug("Entering add");

		cDTO.setName(name);
		if (introduced != null) {
			cDTO.setIntroduced(introduced);
		}
		if (discontinued != null) {
			cDTO.setDiscontinued(discontinued);
		}
		if (companyId != 0) {
			cDTO.setCompanyId(companyId);
		}

		if (!result.hasErrors()) {
			Computer computer = cm.toComputer(cDTO);
			computerService.create(computer);
			logger.debug("Exiting add");
			return "redirect:/dashboard";

		} else {
			return "redirect:/addComputerForm";
		}
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
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

	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public String update(
			ModelAndView mAndV,
			@ModelAttribute("computerDTO") @Valid ComputerDTO computerDTO,
			BindingResult result,
			@RequestParam(value = "name", required = true) String name,
			@RequestParam(value = "introduced", required = false) String introduced,
			@RequestParam(value = "discontinued", required = false) String discontinued,
			@RequestParam(value = "companyId", required = false) Long companyId) {

		logger.debug("Entering update");

		computerDTO.setName(name);
		if (introduced != null) {
			computerDTO.setIntroduced(introduced);
		}
		if (discontinued != null) {
			computerDTO.setDiscontinued(discontinued);
		}
		if (companyId != 0) {
			computerDTO.setCompanyId(companyId);
		}

		if (!result.hasErrors()) {
			Computer computer = cm.toComputer(computerDTO);
			computerService.update(computer);
			logger.debug("Exiting update");
			return "dashboard";

		} else {
			mAndV.addObject("id", computerDTO.getId());
			return "redirect:/edit";
		}
	}

	@RequestMapping(value = "/delete", method = RequestMethod.GET)
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
