package com.excilys.computerdatabase.domain;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ComputerValidator {

	private Logger logger = LoggerFactory.getLogger(ComputerValidator.class);

	public ComputerValidator() {

	}

	public boolean validate(Computer c) {

		boolean validation;
		boolean name = true, introduced = true, discontinued = true, dateOrder = true, company = true;

		if (c.getName() != null && !c.getName().trim().isEmpty()) {
			logger.debug("Variable 'nom' détectée. Valeur: " + c.getName());

		} else {
			logger.error("Veuillez renseigner un nom dans la case prévue.");
			name = false;
		}

		if (c.getIntroduced() != null && !c.getIntroduced().equals(0)) {
			logger.debug("Variable 'introduced' détectée. Valeur: "
					+ c.getIntroduced());
		} else {
			logger.error("Veuillez renseigner une date dans la case prévue.");
			introduced = false;
		}

		if (c.getDiscontinued() != null && !c.getDiscontinued().equals(0)) {
			logger.debug("Variable 'discontinued' détectée. Valeur: "
					+ c.getDiscontinued());
		} else {
			logger.error("Veuillez renseigner une date dans la case prévue.");
			discontinued = false;
		}

		if (c.getIntroduced().before(c.getDiscontinued())) {
			logger.debug("Cohérence temporelle respectée. 'introduced' est bien antérieure à 'discontinued'.");
		} else {
			logger.error("'discontinued' ne peut être antérieure à 'introduced'. Veuillez renseigner des dates cohérentes.");
			dateOrder = false;
		}
		if (c.getCompany().getName() != null
				&& !c.getCompany().getName().trim().isEmpty()
				&& !c.getCompany().getId().equals(0)) {
			logger.debug("Variable 'company' détectée. Valeur: "
					+ c.getCompany().getName());
		} else {
			logger.error("Veuillez choisir une compagnie parmi la liste.");
			company = false;
		}

		if (name == false || introduced == false || discontinued == false
				|| dateOrder == false || company == false) {
			validation = false;
		} else {
			validation = true;
		}
		return validation;
	}
}