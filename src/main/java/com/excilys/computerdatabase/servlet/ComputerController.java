package com.excilys.computerdatabase.servlet;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

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
	@Autowired
	ComputerServiceImpl computerService;
	@Autowired
	CompanyServiceImpl companyService;
	@Autowired
	ComputerMapper cm;
	@Autowired
	ComputerValidator cv;
	@Autowired
	PageWrapper pw;

	private static Logger logger = LoggerFactory
			.getLogger(ComputerController.class);

	@RequestMapping(value = "dashboard", method = RequestMethod.GET)
	public String DisplayList(
			Model model,
			@RequestParam(value = "searchBy", required = false, defaultValue = "default") String searchBy,
			@RequestParam(value = "search", required = false, defaultValue = "default") String search,
			@RequestParam(value = "orderBy", required = false, defaultValue = "default") String orderBy,
			@RequestParam(value = "way", required = false, defaultValue = "default") String way) {

		logger.info("Entering DisplayList");

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
			model.addAttribute("NombreOrdinateurs", computerDTOList.size()
					+ " computer found");
		} else {
			model.addAttribute("NombreOrdinateurs", computerDTOList.size()
					+ " computers found");
		}
		logger.info("Exiting DisplayList");

		return "dashboard";
	}

	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public String Add(
			@RequestParam(value = "name", required = false) String name,
			@RequestParam(value = "introducedDate", required = false) String introduced,
			@RequestParam(value = "discontinuedDate", required = false) String discontinued,
			@RequestParam(value = "company", required = false) Long companyId) {

		logger.info("Entering Add");

		ComputerDTO cDTO = new ComputerDTO();
		cDTO.setName(name);
		cDTO.setIntroduced(introduced);
		cDTO.setDiscontinued(discontinued);
		cDTO.setCompanyId(companyId);

		Computer computer = cm.toComputer(cDTO);

		/*
		 * boolean valid = cv.validate(computer);
		 * 
		 * if (valid == true) { computerService.create(computer);
		 * request.setAttribute("success",
		 * "L'ordinateur a été ajouté avec succès."); } else {
		 * request.setAttribute( "failure",
		 * "Une erreur est survenue, l'ordinateur n'a pas pu être ajouté. Merci de vérifier les données insérées."
		 * ); }
		 */

		computerService.create(computer);

		logger.info("Exiting Add");

		return "getCompanyListForAdding";
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public String Edit(Model model,
			@RequestParam(value = "name", required = false) String name) {

		logger.info("Entering Edit");

		Computer computer = computerService.retrieveByName(name);
		ComputerDTO computerDTO = cm.toComputerDTO(computer);
		model.addAttribute("computer", computer);

		if (computer.getName() != null) {
			model.addAttribute("name", computerDTO.getName());
		}
		if (computer.getIntroduced() != null) {
			model.addAttribute("introduced", computerDTO.getIntroduced());
		}
		if (computer.getDiscontinued() != null) {
			model.addAttribute("discontinued", computerDTO.getDiscontinued());
		}
		if (computer.getCompany() != null) {
			model.addAttribute("companyId", computer.getCompany().getId());
			model.addAttribute("companyName", computer.getCompany().getName());
		}
		logger.info("Exiting Edit");

		return "getCompanyListForEditing";
	}

	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public String Delete(
			Model model,
			@RequestParam(value = "searchBy", required = false) String searchBy,
			@RequestParam(value = "search", required = false) String search,
			@RequestParam(value = "orderBy", required = false) String orderBy,
			@RequestParam(value = "way", required = false) String way,
			@RequestParam(value = "id", required = false) Long id) {

		logger.info("Entering Delete");

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

		logger.info("Exiting Delete");

		return "dashboard";
	}
}
