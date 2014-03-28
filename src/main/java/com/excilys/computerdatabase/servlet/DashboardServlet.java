package com.excilys.computerdatabase.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.computerdatabase.domain.Computer;
import com.excilys.computerdatabase.domain.ComputerDTO;
import com.excilys.computerdatabase.domain.PageWrapper;
import com.excilys.computerdatabase.mapper.ComputerMapper;
import com.excilys.computerdatabase.service.CompanyServiceImpl;
import com.excilys.computerdatabase.service.ComputerServiceImpl;

/**
 * Servlet implementation class DashboardServlet
 */
@WebServlet("/DashboardServlet")
public class DashboardServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	ComputerServiceImpl computerService = ComputerServiceImpl.getInstance();
	CompanyServiceImpl companyService = CompanyServiceImpl.getInstance();
	ComputerMapper cm = new ComputerMapper();

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public DashboardServlet() {
		super();
	}

	/**
	 * @throws IOException
	 * @throws ServletException
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {

		Logger logger = LoggerFactory.getLogger(DashboardServlet.class);
		PageWrapper pw = new PageWrapper();
		List<ComputerDTO> computerDTOList = new ArrayList<ComputerDTO>();
		List<Computer> computerList = new ArrayList<Computer>();

		logger.info("Dashboard started");
		String searchBy = request.getParameter("searchBy") == null ? "default"
				: request.getParameter("searchBy");
		String search = request.getParameter("search") == null ? "default"
				: request.getParameter("search");
		String orderBy = request.getParameter("orderBy") == null ? "default"
				: request.getParameter("orderBy");
		String way = request.getParameter("way") == null ? "default" : request
				.getParameter("way");

		pw = PageWrapper.builder().searchBy(searchBy).search(search)
				.orderBy(orderBy).way(way).build();

		/*
		 * if (request.getParameter("page") == "1") { pw.setOffset(0);
		 * 
		 * } else { pw.setOffset(Integer.parseInt(request.getParameter("page"))
		 * pw.getComputersPerPage()); }
		 */

		if (searchBy.equalsIgnoreCase("computer")) {

			if (!orderBy.equalsIgnoreCase("company.id")
					&& !orderBy.equalsIgnoreCase("company.name")) {
				computerList = computerService.retrieveList(pw);
			} else {
				computerList = computerService.retrieveListByCompany(pw);
			}

		} else if (searchBy.equalsIgnoreCase("company")) {
			computerList = computerService.retrieveListByCompany(pw);

		} else {

			if (!orderBy.equalsIgnoreCase("company.id")
					&& !orderBy.equalsIgnoreCase("company.name")) {
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
		request.setAttribute("PageWrapper", pw);

		if (computerDTOList.size() <= 1) {
			request.setAttribute("NombreOrdinateurs", computerDTOList.size()
					+ " computer found");
		} else {
			request.setAttribute("NombreOrdinateurs", computerDTOList.size()
					+ " computers found");
		}

		logger.info("Dashboard ended");
		request.getRequestDispatcher("WEB-INF/dashboard.jsp").forward(request,
				response);
	}
}
