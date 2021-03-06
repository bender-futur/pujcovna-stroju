<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<sec:authentication var="principal" property="principal" />

<jsp:include page="parts/header.jsp"></jsp:include>
<jsp:include page="parts/left_menu.jsp"></jsp:include>
<div class="content">

	<h2>
		<spring:message code="lang.detailMachineTitle"
			text="Details of machine" />
	</h2>

	<table id="standardTable">
		<tr>
			<td><spring:message code="lang.machineID" text="ID" /></td>
			<td>${machine.id}</td>
		</tr>
		<tr>
			<td><spring:message code="lang.machineLabel" text="Label" /></td>
			<td>${machine.label}</td>
		</tr>
		<tr>
		<tr>
			<td><spring:message code="lang.machineType" text="Type" /></td>
			<td>${machine.type.typeLabel}</td>
		</tr>
		<tr>
			<td><spring:message code="lang.machineDescription"
					text="Description" /></td>
			<td>${machine.description}</td>
		</tr>

	</table>
	<c:if test="${empty machine}">
		<div class="error">
			<spring:message code="lang.machineNotFound" text="Machine not found" />
			ID: '${id}'
		</div>
	</c:if>
	<!--<a href="<c:url value="/loan/new/${machine.id}"/>">link</a>-->
	<c:if test="${not empty loans}">
		<h3>
			<spring:message code="lang.listLoansTitle" text="List of loans" />
		</h3>
		<table id="standardTable">
			<thead>
				<tr>
					<th><spring:message code="lang.id" text="ID" /></th>
					<th><spring:message code="lang.customer" text="Customer" /></th>
					<th><spring:message code="lang.loanTime" text="Loan Time" /></th>
					<th><spring:message code="lang.returnTime" text="Return Time" /></th>
					<th><spring:message code="lang.loanState" text="Loan State" /></th>
				</tr>
			</thead>
			<tbody>

				<c:forEach items="${loans}" var="loan">
					<tr>
						<td>
							<sec:authorize access="hasRole('ADMINISTRATOR') OR hasRole('EMPLOYEE') 
										   OR ${principal.username == loan.customer.username}">
								<a href="<c:url value="/loan/detailFull/${loan.id}"/>">
							</sec:authorize>
							${loan.id}
							<sec:authorize access="hasRole('ADMINISTRATOR') OR hasRole('EMPLOYEE') 
										   OR ${principal.username == loan.customer.username}">
								</a>
							</sec:authorize>
						</td>
						<td>
							<sec:authorize access="hasRole('ADMINISTRATOR') OR hasRole('EMPLOYEE') 
										   OR ${principal.username == loan.customer.username}">
								<a href="<c:url value="/user/detail/${loan.customer.id}"/>">
							</sec:authorize>
							${loan.customer.firstName} ${loan.customer.lastName}
							<sec:authorize access="hasRole('ADMINISTRATOR') OR hasRole('EMPLOYEE') 
										   OR ${principal.username == loan.customer.username}">
								</a>
							</sec:authorize>
						</td>

						<td>${loan.loanTime}</td>
						<td>${loan.returnTime}</td>
						<td>${loan.loanState.typeLabel}</td>
					</tr>
				</c:forEach>
			</tbody>
			<tfoot>
				<tr>
					<th><spring:message code="lang.id" text="ID" /></th>
					<th><spring:message code="lang.customer" text="Customer" /></th>
					<th><spring:message code="lang.loanTime" text="Loan Time" /></th>
					<th><spring:message code="lang.returnTime" text="Return Time" /></th>
					<th><spring:message code="lang.loanState" text="Loan State" /></th>
				</tr>
			</tfoot>

		</table>
	</c:if>
	<c:if test="${empty loans}">
		<div class="info">
			<p>
				<spring:message code="lang.machineNoLoans" text="No loans" />
			</p>

		</div>
	</c:if>
	<c:if test="${not empty revisions}">
		<h3>
			<spring:message code="lang.listRevisionsTitle"
				text="List of revisions" />
		</h3>
		<table id="standardTable">
			<thead>
				<tr>
					<th><spring:message code="lang.id" text="ID" /></th>
					<th><spring:message code="lang.systemUser" text="User" /></th>

					<th><spring:message code="lang.revisionTime"
							text="Return date" /></th>
					<th><spring:message code="lang.comment" text="Revisons State" /></th>
				</tr>
			</thead>
			<tbody>

				<c:forEach items="${revisions}" var="revision">
					<tr>
						<td><sec:authorize access="hasRole('ADMINISTRATOR') OR hasRole('REVISIONER')">
								<a href="<c:url value="/revision/detail/${revision.revID}"/>">
							</sec:authorize>
								${revision.revID}
							<sec:authorize access="hasRole('ADMINISTRATOR') OR hasRole('REVISIONER')">
								</a>
							</sec:authorize>
							</td>
						<td>
							<sec:authorize access="hasRole('ADMINISTRATOR') OR hasRole('EMPLOYEE') 
										   OR ${principal.username == revision.systemUser.username}">
								<a href="<c:url value="/user/detail/${revision.systemUser.id}"/>">
							</sec:authorize>
								${revision.systemUser.firstName} ${revision.systemUser.lastName}
							<sec:authorize access="hasRole('ADMINISTRATOR') OR hasRole('EMPLOYEE') 
										   OR ${principal.username == revision.systemUser.username}">
								</a>
							</sec:authorize>
						</td>

						<td>${revision.revDate}</td>
						<td>${revision.comment}</td>
					</tr>
				</c:forEach>
			</tbody>
			<tfoot>
				<tr>

				<tr>
					<th><spring:message code="lang.id" text="ID" /></th>
                    <th><spring:message code="lang.systemUser"
							text="User" /></th>
                    
                    <th><spring:message code="lang.revisionTime"
							text="Return date" /></th>
                    <th><spring:message code="lang.comment"
							text="Revisons State" /></th>
                </tr></tr>
				
			</tfoot>

		</table>
	</c:if>
	<c:if test="${empty revisions}">
		<div class="info">
			<p>
				<spring:message code="lang.machineNoRevisions" text="No Revisions" />
			</p>

		</div>
	</c:if>
</div>
<jsp:include page="parts/footer.jsp"></jsp:include>