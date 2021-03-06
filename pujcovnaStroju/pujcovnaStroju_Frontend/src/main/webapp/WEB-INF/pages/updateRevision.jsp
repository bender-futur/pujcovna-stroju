<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>


<jsp:include page="parts/header.jsp"></jsp:include>
<jsp:include page="parts/left_menu.jsp"></jsp:include>
<div class="content">
    <h2 class="pageLabel">
        <spring:message code="lang.updateRevisionTitle" text="Update revision" />
    </h2>
    <br>
    <c:if test="${not empty revision}">
        <form:form method="post" id="updateRevisionForm" name="updateRevisionForm"
                action="update">
            <table>
                <tr>
                    <form:hidden path="revID" value="${revision.revID}" />
                    <td>
                        <form:label path="revDate">
                        <spring:message code="lang.revDate" text="Revision Date" />
                        </form:label>
                    </td>
                    <td>
                        <form:input cssClass="inputField datePicker" path="revDate" value="${revision.revDate}" />
                    </td>
                    <td><label id="revisionFormRevDateWarning"
                               class="offscreen warningMessage">
                        <spring:message code="lang.mandatory" text="Mandatory Field" />
                        </label>
                    </td>
                </tr>
                <tr>
                    <td>
                        <form:label path="comment">
                        <spring:message code="lang.comment" text="Comment" />
                        </form:label>
                    </td>
                    <td>
                        <form:input cssClass="inputField" path="comment" value="${revision.comment}" />
                    </td>
                    <td><label id="revisionFormCommentWarning"
                               class="offscreen warningMessage">
                        <spring:message code="lang.mandatory" text="Mandatory Field" />
                        </label>
                    </td>
                </tr>
                
                <tr>
                    <td colspan="2">
                        <button class="button" type="button" value="Update revision"
                                onclick="javascript:validateAndSubmitUpdateRevisionForm()">
                            <spring:message code="lang.updateRevision" text="Update revision" />
                        </button>
                    </td>
                </tr>
            </table>
            <input type="hidden" id="machine" name="machine" value="${machine.id}"/>
            <input type="hidden" id="systemUser" name="systemUser" value="${user.id}"/>
        </form:form>
    </c:if>

    <c:if test="${empty revision}">
        <div class="error">
            <spring:message code="lang.revisionNotFound" text="Revision not found" />
            ID: '${id}'
        </div>
    </c:if>

</div>
<jsp:include page="parts/footer.jsp"></jsp:include>