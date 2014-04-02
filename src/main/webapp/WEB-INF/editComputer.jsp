<jsp:include page="include/header.jsp" />
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<section id="main">

	<h1>Edit Computer</h1>
	
	<form:form action="update?id=${computerDTO.id}" method="POST" modelAttribute="computerDTO">
		<fieldset>
			<div class="clearfix">
				<label for="name">Computer name:</label>
				<div class="input">
					<form:input path="name" type="text"/>
					<form:errors path="name">Veuillez entrer un nom</form:errors>
					<span class="help-inline">Required</span>
				</div>
			</div>
	
			<div class="clearfix">
				<label for="introduced">Introduced date:</label>
				<div class="input">
					<form:input path="introduced" type="text"/>
					<form:errors path="introduced">Date incorrecte</form:errors>
					<span class="help-inline">YYYY-MM-DD</span>
				</div>
			</div>
			<div class="clearfix">
				<label for="discontinued">Discontinued date:</label>
				<div class="input" >
					<form:input path="discontinued" type="text"/>
					<form:errors path="discontinued">Date incorrecte</form:errors>
					<span class="help-inline">YYYY-MM-DD</span>
				</div>
			</div>
			<div class="clearfix">
				<label for="company">Company Name:</label>
				<div class="input">
					<form:select path="companyId" items="${companyList}" itemLabel="name" itemValue="id"/>
				</div>
			</div>
		</fieldset>
		<div class="actions">
		<input type="submit" value="Save" class="btn primary">
			or <a href="dashboard" class="btn">Cancel</a>
		</div>
	</form:form>
</section>

<jsp:include page="include/footer.jsp" />