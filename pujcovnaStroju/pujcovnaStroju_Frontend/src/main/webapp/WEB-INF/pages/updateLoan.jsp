<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	 pageEncoding="UTF-8"%>

    <h2>
        <spring:message code="lang.updateLoanTitle" text="Update loan" />
    </h2>
    
        <c:forEach var="state" items="${loanStates}">
            ${state}
        </c:forEach>
    
	<form:form commandName="command" method="post" id="updateLoanForm" name="updateLoanForm" action="update">
	<c:if test="${not empty loan}"><form:hidden path="id" value="${loan.id}" /></c:if>
	<table>
	    <c:if test="${not empty loan}">
	    <tr>
		<td><spring:message code="lang.loanId" text="ID" /></td>
		<td>${loan.id}
		</td>
		<td>
		</td>
	    </tr>
	    </c:if>
	    <tr>
		<td><form:label path="customer">
                        <spring:message code="lang.customer" text="Customer" />
		    </form:label></td>
		<td><form:select path="customer">
			<c:forEach var="customer" items="${customers}">
				<option value="${customer.id}" <c:if test="${customer.id eq loan.customer.id}">selected="selected"</c:if>><c:out value="${customer.lastName} ${customer.firstName} (${customer.id})"/></option>
			</c:forEach>
		    </form:select>
		</td>
		<td><label id="loanFormCustomerWarning"
			   class="offscreen warningMessage">
                        <spring:message code="lang.mandatory" text="Mandatory Field" />
		    </label></td>
	    </tr>
	    <tr>
		<td><form:label path="loanTime">
			<spring:message code="lang.loanTime" text="Loan Time" />
		    </form:label></td>
		<td>
		    <form:input cssClass="inputField datePicker" path="loanTime" />
		</td>
		<td><label id="loanFormLoanTimeWarning" class="offscreen warningMessage">
                        <spring:message code="lang.mandatory" text="Mandatory Field" />
		    </label></td>
	    </tr>
	    <tr>
		<td><form:label path="returnTime">
			<spring:message code="lang.returnTime" text="Return Time" />
		    </form:label></td>
		<td><form:input cssClass="inputField datePicker" path="returnTime" /></td>
		<td>
		    <label id="loanFormReturnTimeWarning" class="offscreen warningMessage"> 
			<spring:message code="lang.mandatory" text="Mandatory Field" />
		    </label>
		</td>
	    </tr>
	    <tr>
		<td><form:label path="loanState">
                        <spring:message code="lang.loanState" text="Loan State" />
		    </form:label></td>
		<td>
                <c:if test="${userType == 'CUSTOMERINDIVIDUAL' ||
                                userType == 'CUSTOMERLEGAL'}">
                    <form:select path="loanState">
                        <c:forEach var="state" items="${bookedState}">
                            <option value="${state}" <c:if test="${state eq loan.loanState.typeLabel}">selected="selected"</c:if>>
                                <c:out value="${state}"/>
                            </option>
                        </c:forEach>
                    </form:select>
                </c:if>
                <c:if test="${userType == 'ADMINISTRATOR' || 
                                userType == 'REVISIONER' ||
                                userType == 'EMPLOYEE'}">
                    <form:select path="loanState">
                        <c:forEach var="state" items="${loanStates}">
                            <option value="${state}" <c:if test="${state eq loan.loanState.typeLabel}">selected="selected"</c:if>>
                                <c:out value="${state}"/>
                            </option>
                        </c:forEach>
                    </form:select>
                </c:if>
                </td>
		<td><label id="loanFormLoanWarning"
			   class="offscreen warningMessage">
                        <spring:message code="lang.mandatory" text="Mandatory Field" />
		    </label></td>
	    </tr>
	    <tr>
		<td colspan="3">
		    
		    <c:if test="${not empty loan.machines}">
			    <table id="standardTable">
				    <thead>
					    <tr>
						    <th>ID</th>
						    <th><spring:message code="lang.machineLabel" text="Label" /></th>
						    <th><spring:message code="lang.machineType" text="Type" /></th>
						    <th><spring:message code="lang.machineDescription" text="Description" /></th>
						    <th><spring:message code="lang.machineActions" text="Actions" /></th>
					    </tr>
				    </thead>
				    <tbody>
					    <c:forEach items="${loan.machines}" var="machine">
						    <tr>
							    <td>${machine.id}</td>
							    <td><a href="<c:url value="/machine/detail/${machine.id}"/>">${machine.label}</a></td>
							    <td>${machine.type.typeLabel}</td>
							    <td>${machine.description}</td>
							    <td><input type="checkbox" name="machineList" id="machineList" value="${machine.id}" checked="checked" /></td>
						    </tr>
					    </c:forEach>
				    </tbody>
				    <tfoot>
					    <tr>
						    <th>ID</th>
						    <th><spring:message code="lang.machineLabel" text="Label" /></th>
						    <th><spring:message code="lang.machineType" text="Type" /></th>
						    <th><spring:message code="lang.machineDescription" text="Description" /></th>
						    <th><spring:message code="lang.machineActions" text="Actions" /></th>
					    </tr>
				    </tfoot>

			    </table>
		    </c:if>
		    <c:if test="${empty loan.machines}">
			    <div class="info">
				    <p>
					    <spring:message code="lang.loanNoMachines" text="No Machines" />
				    </p>

			    </div>
		    </c:if>
		    
		</td>
	    </tr>
	    <tr>
                <td>
		    <spring:message code="lang.machines" text="Machines" />
                </td>
		<td colspan="2">
		    <div id="machines"></div>
		</td>
	    </tr>

	    <tr>
		<td colspan="2">
		    <c:if test="${not empty loan}"><input class="button" type="button" value="Update loan" onclick="javascript:validateAndSubmitUpdateLoanForm()" /></c:if>
		    <c:if test="${empty loan}">
                        <button class="button" type="button" value="Add loan" 
                            onclick="javascript:validateAndSubmitAddLoanForm()">
                                <spring:message code="lang.addLoan" text="Add Loan" />
                        </button>
                    </c:if>
		    
		</td>
	    </tr>
	</table>

    </form:form>
