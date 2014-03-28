package com.excilys.computerdatabase.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.excilys.computerdatabase.domain.Company;
import com.excilys.computerdatabase.service.CompanyServiceImpl;

/**
 * Servlet implementation class ListCompany
 */
@WebServlet("/ListCompanyServlet")
public class ListCompanyServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	CompanyServiceImpl companyService = CompanyServiceImpl.getInstance();

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ListCompanyServlet() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		List<Company> companyList = companyService.retrieveList();

		request.setAttribute("companyList", companyList);
		request.getRequestDispatcher("WEB-INF/addComputer.jsp").forward(
				request, response);
	}
}
