package com.excilys.computerdatabase.mapper;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.excilys.computerdatabase.domain.Company;
import com.excilys.computerdatabase.domain.Computer;
import com.excilys.computerdatabase.domain.ComputerDTO;
import com.excilys.computerdatabase.service.CompanyServiceImpl;
import com.excilys.computerdatabase.service.ComputerServiceImpl;

@Repository
public class ComputerMapper {

	@Autowired
	ComputerServiceImpl myComputerService;
	@Autowired
	CompanyServiceImpl myCompanyService;

	private static Logger logger = LoggerFactory
			.getLogger(ComputerMapper.class);

	public Computer toComputer(ComputerDTO cDTO) {

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd");
		Computer computer = null;

		try {
			computer = new Computer();
			computer.setId(cDTO.getId());
			computer.setName(cDTO.getName());
			if (cDTO.getIntroduced() != null) {
				computer.setIntroduced(sdf.parse(cDTO.getIntroduced()));
			}
			if (cDTO.getDiscontinued() != null) {
				computer.setDiscontinued(sdf.parse(cDTO.getDiscontinued()));
			}

			if (cDTO.getCompanyId() != 0) {
				Company company = myCompanyService.retrieveById(cDTO
						.getCompanyId());
				computer.setCompany(company);
			}

		} catch (ParseException e) {
			logger.error("Erreur lors du parse des dates.");
			e.printStackTrace();
		}

		return computer;
	}

	public ComputerDTO toComputerDTO(Computer c) {

		ComputerDTO cDTO = new ComputerDTO();
		cDTO.setId(c.getId());
		cDTO.setName(c.getName());
		if (c.getIntroduced() != null) {
			cDTO.setIntroduced(c.getIntroduced().toString());
		}
		if (c.getDiscontinued() != null) {
			cDTO.setDiscontinued(c.getDiscontinued().toString());
		}

		if (c.getCompany() != null) {
			cDTO.setCompany(c.getCompany().getName());
			cDTO.setCompanyId(c.getCompany().getId());
		}

		return cDTO;
	}
}
