<jsp:include page="include/header.jsp" />
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<section id="main">

	<h1>Edit Computer</h1>
	
	<form action="EditComputerServlet" method="GET">
		<fieldset>
			<div class="clearfix">
				<label for="name">Computer name:</label>
				<div class="input">
					<input type="text" value="${name}" name="name" />
					<span class="help-inline">Required</span>
				</div>
			</div>
	
			<div class="clearfix">
				<label for="introduced">Introduced date:</label>
				<div class="input">
					<input type="date" value="${introduced}" name="introducedDate"/>
					<span class="help-inline">YYYY-MM-DD</span>
				</div>
			</div>
			<div class="clearfix">
				<label for="discontinued">Discontinued date:</label>
				<div class="input" >
					<input type="date" value="${discontinued}" name="discontinuedDate"/>
					<span class="help-inline">YYYY-MM-DD</span>
				</div>
			</div>
			<div class="clearfix">
				<label for="company">Company Name:</label>
				<div class="input">
					<select name="company" class="form-control select-form">
						<option value="${companyId}">${companyName}</option>
						<c:forEach var="entry" items="${companyList}">
							<option value="${entry.id}">${entry.name}</option>
						</c:forEach>
					</select>
				</div>
			</div>
		</fieldset>
		<div class="actions">
			<a type="submit" href="AddComputerServlet" class="btn primary">Save</a>
			or <a href="DashboardServlet" class="btn">Cancel</a>
		</div>
	</form>
</section>

<jsp:include page="include/footer.jsp" />