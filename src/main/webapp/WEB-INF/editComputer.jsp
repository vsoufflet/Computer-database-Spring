<jsp:include page="include/header.jsp" />
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<section id="main">

	<h1>Edit Computer</h1>
	
	<form:form action="editComputer" method="GET" modelAttribute="computerDTO">
		<fieldset>
			<div class="clearfix">
				<label for="name">Computer name:</label>
				<div class="input">
					<form:input path="name" type="text" name="name"/>
					<span class="help-inline">Required</span>
				</div>
			</div>
	
			<div class="clearfix">
				<label for="introduced">Introduced date:</label>
				<div class="input">
					<form:input path="introduced" type="date" name="introducedDate"/>
					<span class="help-inline">YYYY-MM-DD</span>
				</div>
			</div>
			<div class="clearfix">
				<label for="discontinued">Discontinued date:</label>
				<div class="input" >
					<form:input path="discontinued" type="date" name="discontinuedDate"/>
					<span class="help-inline">YYYY-MM-DD</span>
				</div>
			</div>
			<div class="clearfix">
				<label for="company">Company Name:</label>
				<div class="input">
					<form:select path="companyId" items="${companyList}" itemLabel="name" itemValue="id" />
				</div>
			</div>
		</fieldset>
		<div class="actions">
			<a type="submit" href="addComputer" class="btn primary">Save</a>
			or <a href="dashboard" class="btn">Cancel</a>
		</div>
	</form:form>
</section>

<jsp:include page="include/footer.jsp" />