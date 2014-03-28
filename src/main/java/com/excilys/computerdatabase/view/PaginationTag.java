package com.excilys.computerdatabase.view;

import java.io.IOException;

import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PaginationTag extends TagSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Logger logger = LoggerFactory.getLogger(PaginationTag.class);

	private int totalRecords; // Nombre d'enregistrements
	private int recordsPerPage; // Incrément du limit
	private int nbOfPages; // Nombre de pages total
	private int previousBound; // Ancienne valeur du firstBound pour calcul

	public int doStartTag() {
		JspWriter out = pageContext.getOut();
		try {
			setPreviousBound(recordsPerPage);
			out.println("<ul class='paginatorList'>");
			nbOfPages = (int) Math.ceil((double) totalRecords / recordsPerPage);
			for (int i = 1; i < nbOfPages + 1; i++) {
				if (i == 1) {
					out.println("<li><a href='/computer-database/Computers?firstBound=0&secondBound="
							+ recordsPerPage + "'>1</a></li>");
				} else {
					out.println("<li><a href='/computer-database/Computers?firstBound="
							+ previousBound
							+ "&secondBound="
							+ recordsPerPage
							+ "'>" + i + "</a></li>");
					previousBound += recordsPerPage;
				}
			}
			out.println("</ul>");
		} catch (IOException e) {
			logger.error("Erreur d'I/O lors de la création de la pagination", e);
		}
		return SKIP_BODY;
	}

	public int doEndTag() {
		return EVAL_PAGE;
	}

	public int getTotalRecords() {
		return totalRecords;
	}

	public void setTotalRecords(int totalRecords) {
		this.totalRecords = totalRecords;
	}

	public int getRecordsPerPage() {
		return recordsPerPage;
	}

	public void setRecordsPerPage(int recordsPerPage) {
		this.recordsPerPage = recordsPerPage;
	}

	public int getNbOfPages() {
		return nbOfPages;
	}

	public void setNbOfPages(int nbOfPages) {
		this.nbOfPages = nbOfPages;
	}

	public int getPreviousBound() {
		return previousBound;
	}

	public void setPreviousBound(int previousBound) {
		this.previousBound = previousBound;
	}
}
