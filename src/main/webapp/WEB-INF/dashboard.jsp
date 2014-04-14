<jsp:include page="include/header.jsp" />
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%-- <%@ taglib uri="/WEB-INF/PaginationTag.tld" prefix="paging" %>  --%>
<section id="main">
	Language <a href="?language=en">English</a> | <a href="?language=fr">Français</a>
	<c:choose>
		<c:when test="${NombreOrdinateurs <= 1}">
			<h1 id="hometitle">${NombreOrdinateurs}
				<spring:message code="foundComputer" />
			</h1>
		</c:when>
		<c:otherwise>
			<h1 id="homeTitle">${NombreOrdinateurs}
				<spring:message code="foundComputers" />
			</h1>
		</c:otherwise>
	</c:choose>

	<div id="actions">
		<form action="dashboard" method="GET">
			<input type="search" id="searchbox" name="search"
				placeholder=<spring:message code="search"/>> <select
				name="searchBy">
				<option SELECTED value="default">
					<spring:message code="searchBy" /></option>
				<option value="computer">
					<spring:message code="computerName" /></option>
				<option value="company">
					<spring:message code="companyName" /></option>
			</select> <select name="orderBy">
				<option SELECTED value="default">
					<spring:message code="orderBy" /></option>
				<option value="id"><spring:message code="computerId" /></option>
				<option value="name"><spring:message code="computerName" /></option>
				<option value="introduced"><spring:message
						code="introduced" /></option>
				<option value="discontinued"><spring:message
						code="discontinued" /></option>
				<option value="company.id"><spring:message code="companyId" /></option>
				<option value="company.name"><spring:message
						code="companyName" /></option>
			</select> <input type="submit" class="btn primary" id="searchsubmit"
				value=<spring:message code="filterButton"/>></input>
		</form>

		<a class="btn success" id="add" href="addComputerForm"> <spring:message
				code="addButton" />
		</a>
	</div>

	<table class="computers zebra-striped">
		<thead>
			<tr>
				<!-- Variable declarations for passing labels as parameters -->
				<!-- Table header for Computer Name -->
				<th><spring:message code="column1" /></th>
				<th><spring:message code="column2" /></th>
				<!-- Table header for Discontinued Date -->
				<th><spring:message code="column3" /></th>
				<!-- Table header for Company -->
				<th><spring:message code="column4" /></th>
				<th></th>
			</tr>
		</thead>
		<tbody>

			<c:forEach var="computerDTO" items="${PageWrapper.computerDTOList}">
				<tr>
					<td><a href="edit?id=${computerDTO.id}"><c:out
								value="${computerDTO.name}" /></a></td>
					<td>${computerDTO.introduced}</td>
					<td>${computerDTO.discontinued}</td>
					<td>${computerDTO.company}</td>
					<td><a class="btn danger" id="Delete"
						href="delete?id=${computerDTO.id}"><spring:message
								code="deleteButton" /></a></td>
				</tr>
			</c:forEach>

		</tbody>
	</table>
	<%--  <paging:display totalRecords="${NombreOrdinateurs}" recordsPerPage="15"/> --%>
</section>

<jsp:include page="include/footer.jsp" />
