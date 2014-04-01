package com.excilys.computerdatabase.domain;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class ComputerValidator implements
		ConstraintValidator<DateFormat, String> {

	private Logger logger = LoggerFactory.getLogger(ComputerValidator.class);

	@Override
	public void initialize(DateFormat constraintAnnotation) {
	}

	@Override
	public boolean isValid(String fullDate, ConstraintValidatorContext context) {

		boolean validDate = false;
		String[] date = fullDate.split("/");

		if (fullDate.matches("[0-9]{4}-[0-9]{2}-[0-9]{2}")
				|| fullDate.matches("[0-9]{4}/[0-9]{2}/[0-9]{2}")) {
			if (Integer.valueOf(date[1]) <= 12) {
				if (Integer.valueOf(date[2]) <= 31) {
					logger.debug("Format 'introduced' correct");
					validDate = true;
				} else {
					logger.debug("31 jours par mois maximum");
				}
			} else {
				logger.debug("12 mois par an maximum");
			}
		} else {
			logger.error("Format 'introduced' incorrect");
			validDate = false;
		}
		return validDate;
	}

	public boolean validate(ComputerDTO cDTO) {

		boolean validation;
		boolean introduced = false;
		boolean discontinued = false;
		boolean dateOrder = false;

		String cdtoIntroduced = cDTO.getIntroduced();
		String cdtoDiscontinued = cDTO.getDiscontinued();

		String[] intDate = cdtoIntroduced.split("/");
		String[] disDate = cdtoDiscontinued.split("/");

		if (cdtoIntroduced.matches("[0-9]{4}-[0-9]{2}-[0-9]{2}")
				|| cdtoIntroduced.matches("[0-9]{4}/[0-9]{2}/[0-9]{2}")) {
			if (Integer.valueOf(intDate[1]) <= 12) {
				if (Integer.valueOf(intDate[2]) <= 31) {
					logger.debug("Format 'introduced' correct");
					introduced = true;
				} else {
					logger.debug("31 jours par mois maximum");
				}
			} else {
				logger.debug("12 mois par an maximum");
			}
		} else {
			logger.error("Format 'introduced' incorrect");
			introduced = false;
		}

		if (cdtoDiscontinued.matches("[0-9]{4}-[0-9]{2}-[0-9]{2}")
				|| cdtoDiscontinued.matches("[0-9]{4}/[0-9]{2}/[0-9]{2}")) {
			if (Integer.valueOf(disDate[1]) <= 12) {
				if (Integer.valueOf(disDate[2]) <= 31) {
					logger.debug("Format 'discontinued' correct");
					discontinued = true;
				} else {
					logger.debug("31 jours par mois maximum");
				}
			} else {
				logger.debug("12 mois par an maximum");
			}
		} else {
			logger.error("Format 'discontinued' incorrect");
			discontinued = false;
		}

		if (Integer.valueOf(intDate[0]) <= Integer.valueOf(disDate[0])) {
			logger.debug("Cohérence temporelle respectée pour les années.");

			if (Integer.valueOf(intDate[1]) <= Integer.valueOf(disDate[1])) {
				logger.debug("Cohérence temporelle respectée pour les mois.");

				if (Integer.valueOf(intDate[2]) <= Integer.valueOf(disDate[2])) {
					logger.debug("Cohérence temporelle respectée pour les jours.");
					dateOrder = true;
				} else {
					logger.error("discontinued antérieure à introduced. Vérifiez les jours.");
					dateOrder = false;
				}
			} else {
				logger.error("discontinued antérieure à introduced. Vérifiez les mois.");
				dateOrder = false;
			}

		} else {
			logger.error("discontinued antérieure à introduced. Vérifiez les années.");
			dateOrder = false;
		}

		if (introduced == false || discontinued == false || dateOrder == false) {
			validation = false;
		} else {
			validation = true;
		}
		return validation;
	}
}