<jsp:include page="include/header.jsp" />
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<section id="main">
	Language <a href="?language=en">English</a> | <a href="?language=fr">Fran�ais</a>
	<h1>
		<spring:message code="addComputerTitle" />
	</h1>

	<form:form action="addComputer" method="POST"
		modelAttribute="computerDTO">
		<fieldset>
			<div class="clearfix">
				<label for="name"><spring:message code="column1" />:</label>
				<div class="input">
					<form:input path="name" type="text" />
					<form:errors path="name">
						<spring:message code="emptyNameError" />
					</form:errors>
					<span class="help-inline"><spring:message code="required" /></span>
				</div>
			</div>

			<div class="clearfix">
				<label for="introduced"><spring:message code="column2" />:</label>
				<div class="input">
					<form:input path="introduced" type="text" />
					<form:errors path="introduced">
						<spring:message code="dateFormatError" />
					</form:errors>
					<span class="help-inline"><spring:message code="datePattern" /></span>
				</div>
			</div>
			<div class="clearfix">
				<label for="discontinued"><spring:message code="column3" />:</label>
				<div class="input">
					<form:input path="discontinued" type="text" />
					<form:errors path="discontinued">
						<spring:message code="dateFormatError" />
					</form:errors>
					<span class="help-inline"><spring:message code="datePattern" /></span>
				</div>
			</div>
			<div class="clearfix">
				<label for="company"><spring:message code="column4" />:</label>
				<div class="input">
					<select name="companyId" class="form-control select-form">
						<option value="default">--</option>
						<c:forEach var="companyId" items="${companyList}">
							<option value="${company.id}">${company.name}</option>
						</c:forEach>
					</select>
				</div>
			</div>
		</fieldset>
		<div class="actions">
			<input type="submit" value=<spring:message code="addButton"/>
				class="btn primary">
			<spring:message code="or" />
			<a href="dashboard" class="btn"><spring:message
					code="cancelButton" /></a>
		</div>
	</form:form>
</section>

<jsp:include page="include/footer.jsp" />