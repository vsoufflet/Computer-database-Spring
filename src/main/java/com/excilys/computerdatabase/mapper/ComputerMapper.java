package com.excilys.computerdatabase.mapper;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.excilys.computerdatabase.domain.Company;
import com.excilys.computerdatabase.domain.Computer;
import com.excilys.computerdatabase.domain.ComputerDTO;
import com.excilys.computerdatabase.service.CompanyService;
import com.excilys.computerdatabase.service.ComputerService;

@Repository
public class ComputerMapper {

	@Autowired
	ComputerService myComputerService;
	@Autowired
	CompanyService myCompanyService;

	private static Logger logger = LoggerFactory
			.getLogger(ComputerMapper.class);

	public Computer toComputer(ComputerDTO cDTO) {

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Computer computer = null;

		try {
			computer = new Computer();
			computer.setId(cDTO.getId());
			computer.setName(cDTO.getName());
			if (cDTO.getIntroduced() != null
					&& !cDTO.getIntroduced().trim().isEmpty()) {
				computer.setIntroduced(new DateTime(sdf.parse(cDTO
						.getIntroduced())));
			}
			if (cDTO.getDiscontinued() != null
					&& !cDTO.getDiscontinued().trim().isEmpty()) {
				computer.setDiscontinued(new DateTime(sdf.parse(cDTO
						.getDiscontinued())));
			}

			if (cDTO.getCompanyId() != null && cDTO.getCompanyId() != 0) {
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
			cDTO.setIntroduced(c.getIntroduced().toString().substring(0, 10));
		}
		if (c.getDiscontinued() != null) {
			cDTO.setDiscontinued(c.getDiscontinued().toString()
					.substring(0, 10));
		}

		if (c.getCompany() != null) {
			cDTO.setCompany(c.getCompany().getName());
			cDTO.setCompanyId(c.getCompany().getId());
		}

		return cDTO;
	}
}
