<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page isELIgnored="false" %>

<link rel="stylesheet" href="<c:url value="/resources/css/person.css"/>">

<jsp:useBean id="model" beanName="model" scope="request"
             type="ilya.sheverov.projectstask.model.PersonModelFacade"/>

<html>
<head>
    <title>${model.titlePageWithAListOfPersons}</title>
</head>
<body>
<c:set var="lang" value="${model.lang}" scope="page"/>
<c:set var="pageNumber" value="${model.currentPageNumber}" scope="page"/>

<c:set var="nameOfTheColumnToBeSorted" value="${model.nameOfTheColumnToBeSorted}" scope="page"/>

<c:set var="selectPersons" value="${model.multiplePersonSelectionMode}" scope="page"/>

<jsp:include page="header.jsp"/>

<a href='<c:url value="/person/all?lang=ru&page=${pageNumber}&sortUsersList=${nameOfTheColumnToBeSorted}"/>'>RU</a>
<a href='<c:url value="/person/all?lang=en&page=${pageNumber}&sortUsersList=${nameOfTheColumnToBeSorted}"/>'>EN</a>

<div id="person-list">
    <form hidden id="selected-persons" action="<c:url value="/person/delete/selected"/>"
          method="post">
    </form>
    <table id="person-table">
        <tr>
            <th id="id">${model.columnNameIdNames}
                <c:if test="${nameOfTheColumnToBeSorted == \"id\"}">
                    &darr;
                </c:if>
                <c:if test="${nameOfTheColumnToBeSorted !=  \"id\"}">
                    <a href='<c:url value="/person/all?lang=${lang}&page=${pageNumber}&sortUsersList=id"/>'>&uarr;</a>
                </c:if>
            </th>
            <th id="lastName">${model.columnNameLastNames}
                <c:if test="${nameOfTheColumnToBeSorted == \"last_name\"}">
                    &darr;
                </c:if>
                <c:if test="${nameOfTheColumnToBeSorted != \"last_name\"}">
                    <a href='<c:url value="/person/all?lang=${lang}&page=${pageNumber}&sortUsersList=last_name"/>'>&uarr;</a>
                </c:if>

            </th>
            <th id="firstName">${model.columnNameFirstNames}
                <c:if test="${nameOfTheColumnToBeSorted == \"first_name\"}">
                    &darr;
                </c:if>
                <c:if test="${nameOfTheColumnToBeSorted != \"first_name\"}">
                    <a href='<c:url value="/person/all?lang=${lang}&page=${pageNumber}&sortUsersList=first_name"/>'>&uarr;</a>
                </c:if>
            </th>
            <th id="middleName">${model.columnNameMiddleNames}
                <c:if test="${nameOfTheColumnToBeSorted == \"middle_name\"}">
                    &darr;
                </c:if>
                <c:if test="${nameOfTheColumnToBeSorted != \"middle_name\"}">
                    <a href='<c:url value="/person/all?lang=${lang}&page=${pageNumber}&sortUsersList=middle_name"/>'>&uarr;</a>
                </c:if>
            </th>
            <td><a href='<c:url value="/person/create?lang=${lang}"/>'>${model.createPerson}</a>
            </td>
            <td>
                <c:if test="${selectPersons == false}">
                    <a href='<c:url value="/person/all?lang=${lang}&page=${pageNumber}&sortUsersList=${nameOfTheColumnToBeSorted}&selectPersons=true"/>'>
                            ${model.selectPersons}</a>
                </c:if>
                <c:if test="${selectPersons}">
                    <input type="submit" form="selected-persons" class="link"
                           value="${model.deleteSelectedPersons}">
                    <a href='<c:url value="/person/all?lang=${lang}&page=${pageNumber}&sortUsersList=${nameOfTheColumnToBeSorted}"/>'>${model.cancel}</a>
                </c:if>
            </td>
        </tr>
        <c:set var="deleteAPersonQuestion"/>
        <c:forEach var="person" items="${model.personsWithTasksCount.keySet()}" varStatus="counter">
            <tr>
                <td>${person.id}</td>
                <td>${person.lastName}</td>
                <td>${person.firstName}</td>
                <td>${person.middleName}</td>
                <td>
                    <a href='<c:url value="/person/edit?lang=${lang}&id=${person.id}&version=${person.version}" />'>
                            ${model.editPerson}
                    </a>
                </td>
                <c:if test="${model.personsWithTasksCount.get(person)  == 0}">
                    <td>
                        <a href='<c:url value="/person/delete?lang=${lang}&id=${person.id}&version=${person.version}"/>'
                           onclick="return deletePerson('${model.getDeleteAPersonQuestion(person.id)}');">
                                ${model.deletePerson}
                        </a>
                        <c:if test="${selectPersons}">
                            <input type="checkbox" name="selectedPerson" form="selected-persons"
                                   value="${person.id},${person.version}"
                        </c:if>
                    </td>
                </c:if>
                <c:if test="${model.personsWithTasksCount.get(person) != 0}">
                    <td>
                        <p title="Невозможно удалить персону, так как у неё есть задачи(${model.personsWithTasksCount.get(person)}).">
                                ${model.deletePerson}
                        </p>
                    </td>
                </c:if>
            </tr>
        </c:forEach>
    </table>
</div>
<div id="persons-list-pages">
    <table>
        <tbody>
        <tr>
            <c:if test="${pageNumber > 1}">
                <td>
                    <a href="<c:url value="/person/all?lang=${lang}&page=${1}&sortUsersList=${nameOfTheColumnToBeSorted}"/>">
                            ${model.firstPage}
                    </a>
                </td>
                <td>
                    <a href="<c:url value="/person/all?lang=${lang}&page=${pageNumber-1}&sortUsersList=${nameOfTheColumnToBeSorted}"/>">
                            ${model.previousPage}
                    </a>
                </td>
            </c:if>
            <c:if test="${pageNumber <= 1}">
                <td>
                        ${model.firstPage}
                </td>
                <td>
                        ${model.previousPage}
                </td>
            </c:if>
            <c:forEach begin="${model.firstNumberOfPageInPageList}"
                       end="${model.lastNumberOfPageInPageList}"
                       step="1"
                       var="pageNumberCounter"
                       varStatus="counter">
                <td>
                    <c:if test="${pageNumberCounter != pageNumber}">
                        <a href="<c:url value="/person/all?lang=${lang}&page=${pageNumberCounter}&sortUsersList=${nameOfTheColumnToBeSorted}"/>">
                                ${pageNumberCounter}
                        </a>
                    </c:if>
                    <c:if test="${pageNumberCounter ==  pageNumber}">
                        <strong> ${pageNumberCounter}</strong>
                    </c:if>
                </td>
            </c:forEach>
            <c:if test="${pageNumber < model.numberOfAllLinksToPages}">
                <td>
                    <a href="<c:url value="/person/all?lang=${lang}&page=${pageNumber+1}&sortUsersList=${nameOfTheColumnToBeSorted}"/>">
                            ${model.nextPage}
                    </a>
                </td>
                <td>
                    <a href="<c:url value="/person/all?lang=${lang}&page=${model.numberOfAllLinksToPages}&sortUsersList=${nameOfTheColumnToBeSorted}"/>">
                            ${model.lastPage}
                    </a>
                </td>
            </c:if>
            <c:if test="${pageNumber >= model.numberOfAllLinksToPages}">
                <td>
                        ${model.nextPage}
                </td>
                <td>
                        ${model.lastPage}
                </td>
            </c:if>
        </tr>
        </tbody>

    </table>
</div>
<script>
    function errorMsg(errorMsg) {
        confirm(errorMsg);
    }
</script>
<c:if test="${model.hasMessage}">
    <script type="text/javascript">
        errorMsg('${model.message}');
    </script>
</c:if>
<script>
    function deletePerson(message) {
        return confirm(message);
    }
</script>
</body>
</html>
