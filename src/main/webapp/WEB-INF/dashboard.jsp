<jsp:include page="include/header.jsp" />
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%-- <%@ taglib uri="/WEB-INF/PaginationTag.tld" prefix="paging" %>  --%>
<section id="main">
	<h1 id="homeTitle">${NombreOrdinateurs}</h1>
	<div id="actions">
		<form action="DashboardServlet" method="GET">
			<input type="search" id="searchbox" name="search"
				placeholder="Search name"> 
				
		<select name="searchBy">
		<option SELECTED value="default">Search By</option>
		<option value="computer">Computer Name</option>
		<option value="company">Company Name</option>
		</select>
		
		<select name="orderBy">
			<option SELECTED value="default">Order By</option>
			<option value="id">Computer Id</option>
			<option value="name">Computer Name</option>
			<option value="introduced">Introduced</option>
			<option value="discontinued">Discontinued</option>
			<option value="company.id">Company Id</option>
			<option value="company.name">Company Name</option>
		</select>
				<input type="submit" class="btn primary"
				id="searchsubmit" value="Filter by name"></input>
		</form>
		
		<a class="btn success" id="add" href="ListCompanyServlet">Add
			Computer</a>
	</div>

	<table class="computers zebra-striped">
		<thead>
			<tr>
				<!-- Variable declarations for passing labels as parameters -->
				<!-- Table header for Computer Name -->
				<th>Computer Name</th>
				<th>Introduced Date</th>
				<!-- Table header for Discontinued Date -->
				<th>Discontinued Date</th>
				<!-- Table header for Company -->
				<th>Company</th>
				<th></th>
			</tr>
		</thead>
		<tbody>

			<c:forEach var="computerDTO" items="${PageWrapper.computerDTOList}">
				<tr>
					<td><a href="EditComputerServlet?name=${computerDTO.name}"><c:out
								value="${computerDTO.name}" /></a></td>
					<td>${computerDTO.introduced}</td>
					<td>${computerDTO.discontinued}</td>
					<td>${computerDTO.company}</td>
					<td><a class="btn danger" id="Delete"
						href="DeleteComputerServlet?id=${computerDTO.id}">Delete Computer</a></td>
				</tr>
			</c:forEach>

		</tbody>
	</table>
	<%--  <paging:display totalRecords="${NombreOrdinateurs}" recordsPerPage="15"/> --%>
</section>

<jsp:include page="include/footer.jsp" />
