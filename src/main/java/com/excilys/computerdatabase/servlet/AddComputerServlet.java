package com.excilys.computerdatabase.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.excilys.computerdatabase.domain.Computer;
import com.excilys.computerdatabase.domain.ComputerDTO;
import com.excilys.computerdatabase.domain.ComputerValidator;
import com.excilys.computerdatabase.mapper.ComputerMapper;
import com.excilys.computerdatabase.service.ComputerServiceImpl;

@WebServlet("/AddComputerServlet")
public class AddComputerServlet extends javax.servlet.http.HttpServlet
		implements javax.servlet.Servlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	ComputerServiceImpl computerService = ComputerServiceImpl.getInstance();
	ComputerMapper cm = new ComputerMapper();
	ComputerValidator cv = new ComputerValidator();

	public AddComputerServlet() {
		super();
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {

		String name = request.getParameter("name");
		String introduced = request.getParameter("introducedDate");
		String discontinued = request.getParameter("discontinuedDate");
		Long companyId = Long.parseLong(request.getParameter("company"));

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
		request.getRequestDispatcher("index.jsp").forward(request, response);
	}
}
