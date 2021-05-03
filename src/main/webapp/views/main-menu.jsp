<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page isELIgnored="false" %>

<link rel="stylesheet" href="<c:url value="/resources/css/mainmenu.css"/>">

<jsp:useBean id="model" beanName="model" scope="request"
             type="ilya.sheverov.projectstask.model.MainMenuModelFacade"/>

<c:set var="lang" value="${model.lang}" scope="page"/>
<c:set var="pageNumberOfThePersonList" value="${model.pageNumberOfThePersonList}" scope="page"/>
<c:set var="pageNumberOfTheTaskList" value="${model.pageNumberOfTheTaskList}" scope="page"/>
<c:set var="nameOfTheColumnToBeSortedPersonList"
       value="${model.nameOfTheColumnToBeSortedPersonList}" scope="page"/>
<c:set var="nameOfTheColumnToBeSortedTaskList" value="${model.nameOfTheColumnToBeSortedTaskList}"
       scope="page"/>
<c:set var="multiplePersonSelectionMode" value="${model.multiplePersonSelectionMode}" scope="page"/>
<c:set var="multipleTaskSelectionMode" value="${model.multipleTaskSelectionMode}" scope="page"/>
<c:set var="statusValues" value="${model.statusValues}" scope="page"/>

<html>
<head>
    <title>${model.titlePageMainMenu}</title>
</head>
<body>
<jsp:include page="header.jsp"/>
<a href='<c:url value="/mainmenu?lang=ru&pageNumberOfThePersonList=${pageNumberOfThePersonList}&nameOfTheColumnToBeSortedPersonList=${nameOfTheColumnToBeSortedPersonList}&pageNumberOfTheTaskList=${pageNumberOfTheTaskList}&nameOfTheColumnToBeSortedTaskList=${nameOfTheColumnToBeSortedTaskList}"/>'>RU</a>
<a href='<c:url value="/mainmenu?lang=en&pageNumberOfThePersonList=${pageNumberOfThePersonList}&nameOfTheColumnToBeSortedPersonList=${nameOfTheColumnToBeSortedPersonList}&pageNumberOfTheTaskList=${pageNumberOfTheTaskList}&nameOfTheColumnToBeSortedTaskList=${nameOfTheColumnToBeSortedTaskList}"/>'>EN</a>
<div class="blockDiv">
    <div id="person-list">
        <form hidden id="selected-persons" action="<c:url value="/person/delete/selected"/>"
              method="post">
        </form>
        <table id="person-table">
            <thead>
            <tr>
                <th id="id">${model.labelOfThePersonTableColumnId}
                    <c:if test="${nameOfTheColumnToBeSortedPersonList == \"id\"}">&darr;
                    </c:if>
                    <c:if test="${nameOfTheColumnToBeSortedPersonList !=  \"id\"}">
                        <a href='<c:url value="/mainmenu?lang=${lang}&pageNumberOfThePersonList=${pageNumberOfThePersonList}&nameOfTheColumnToBeSortedPersonList=id&pageNumberOfTheTaskList=${pageNumberOfTheTaskList}&nameOfTheColumnToBeSortedTaskList=${nameOfTheColumnToBeSortedTaskList}"/>'>&uarr;</a>
                    </c:if>
                </th>
                <th id="lastName">${model.labelOfThePersonTableColumnLastNames}
                    <c:if test="${nameOfTheColumnToBeSortedPersonList == \"last_name\"}">&darr;
                    </c:if>
                    <c:if test="${nameOfTheColumnToBeSortedPersonList != \"last_name\"}">
                        <a href='<c:url value="/mainmenu?lang=${lang}&pageNumberOfThePersonList=${pageNumberOfThePersonList}&nameOfTheColumnToBeSortedPersonList=last_name&pageNumberOfTheTaskList=${pageNumberOfTheTaskList}&nameOfTheColumnToBeSortedTaskList=${nameOfTheColumnToBeSortedTaskList}"/>'>&uarr;</a>
                    </c:if>

                </th>
                <th id="firstName">${model.labelOfThePersonTableColumnFirstNames}
                    <c:if test="${nameOfTheColumnToBeSortedPersonList == \"first_name\"}">&darr;
                    </c:if>
                    <c:if test="${nameOfTheColumnToBeSortedPersonList != \"first_name\"}">
                        <a href='<c:url value="/mainmenu?lang=${lang}&pageNumberOfThePersonList=${pageNumberOfThePersonList}&nameOfTheColumnToBeSortedPersonList=first_name&pageNumberOfTheTaskList=${pageNumberOfTheTaskList}&nameOfTheColumnToBeSortedTaskList=${nameOfTheColumnToBeSortedTaskList}"/>'>&uarr;</a>
                    </c:if>
                </th>
                <th id="middleName">${model.labelOfThePersonTableColumnMiddleNames}
                    <c:if test="${nameOfTheColumnToBeSortedPersonList == \"middle_name\"}">&darr;
                    </c:if>
                    <c:if test="${nameOfTheColumnToBeSortedPersonList != \"middle_name\"}">
                        <a href='<c:url value="/mainmenu?lang=${lang}&pageNumberOfThePersonList=${pageNumberOfThePersonList}&nameOfTheColumnToBeSortedPersonList=middle_name&pageNumberOfTheTaskList=${pageNumberOfTheTaskList}&nameOfTheColumnToBeSortedTaskList=${nameOfTheColumnToBeSortedTaskList}"/>'>&uarr;</a>
                    </c:if>
                </th>
                <td>
                    <a href='<c:url value="/person/create?lang=${lang}"/>'>${model.textValueCreatePerson}</a>
                </td>
                <td>
                    <c:if test="${multiplePersonSelectionMode == false}">
                        <a href='<c:url value="/mainmenu?lang=${lang}&pageNumberOfThePersonList=${pageNumberOfThePersonList}&nameOfTheColumnToBeSortedPersonList=${nameOfTheColumnToBeSortedPersonList}&multiplePersonSelectionMode=true&pageNumberOfTheTaskList=${pageNumberOfTheTaskList}&nameOfTheColumnToBeSortedTaskList=${nameOfTheColumnToBeSortedTaskList}"/>'>
                                ${model.textValueSelectPersons}</a>
                    </c:if>
                    <c:if test="${multiplePersonSelectionMode}">
                        <input type="submit" form="selected-persons" class="link"
                               value="${model.textValueDeleteSelectedPersons}">
                        <a href='<c:url value="/mainmenu?lang=${lang}&pageNumberOfThePersonList=${pageNumberOfThePersonList}&nameOfTheColumnToBeSortedPersonList=${nameOfTheColumnToBeSortedPersonList}"/>'>${model.textValueCancel}</a>
                    </c:if>
                </td>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="person" items="${model.personList}" varStatus="counter">
                <tr>
                    <td>${person.id}</td>
                    <td>${person.lastName}</td>
                    <td>${person.firstName}</td>
                    <td>${person.middleName}</td>
                    <td>
                        <a href='<c:url value="/person/edit?lang=${lang}&id=${person.id}&version=${person.version}"/>'>
                                ${model.textValueEditPerson}
                        </a>
                    </td>
                    <c:if test="${model.getPersonsTasksCount(person)  == 0}">
                        <td>
                            <a href='<c:url value="/person/delete?lang=${lang}&id=${person.id}&version=${person.version}"/>'
                               onclick="return deletePerson('${model.getDeleteAPersonQuestion(person.id)}');">
                                    ${model.textValueDeletePerson}
                            </a>
                            <c:if test="${multiplePersonSelectionMode}">
                                <input type="checkbox" name="selectedPerson" form="selected-persons"
                                       value="${person.id},${person.version}"
                            </c:if>
                        </td>
                    </c:if>
                    <c:if test="${model.getPersonsTasksCount(person) != 0}">
                        <td>
                            <p title="Невозможно удалить персону, так как у неё есть задачи(${model.getPersonsTasksCount(person)}).">
                                    ${model.textValueDeletePerson}
                            </p>
                        </td>
                    </c:if>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
    <div id="persons-list-pages">
        <table>
            <tbody>
            <tr>
                <c:if test="${pageNumberOfThePersonList > 1}">
                    <td>
                        <a href="<c:url value="/mainmenu?lang=${lang}&pageNumberOfThePersonList=${1}&nameOfTheColumnToBeSortedPersonList=${nameOfTheColumnToBeSortedPersonList}&pageNumberOfTheTaskList=${pageNumberOfTheTaskList}&nameOfTheColumnToBeSortedTaskList=${nameOfTheColumnToBeSortedTaskList}
                    "/>">
                                ${model.textValueFirstPagePersonList}
                        </a>
                    </td>
                    <td>
                        <a href="<c:url value="/mainmenu?lang=${lang}&pageNumberOfThePersonList=${pageNumberOfThePersonList-1}&nameOfTheColumnToBeSortedPersonList=${nameOfTheColumnToBeSortedPersonList}&pageNumberOfTheTaskList=${pageNumberOfTheTaskList}&nameOfTheColumnToBeSortedTaskList=${nameOfTheColumnToBeSortedTaskList}"/>">
                                ${model.textValuePreviousPagePersonList}
                        </a>
                    </td>
                </c:if>
                <c:if test="${pageNumberOfThePersonList <= 1}">
                    <td>
                            ${model.textValueFirstPagePersonList}
                    </td>
                    <td>
                            ${model.textValuePreviousPagePersonList}
                    </td>
                </c:if>
                <c:forEach begin="${model.numberOfTheFirstPageInThePersonListPageNavigation}"
                           end="${model.numberOfTheLastPageInThePersonListPageNavigation}"
                           step="1"
                           var="pageNumberOfThePersonListCounter"
                           varStatus="counter">
                    <td>
                        <c:if test="${pageNumberOfThePersonListCounter != pageNumberOfThePersonList}">
                            <a href="<c:url value="/mainmenu?lang=${lang}&pageNumberOfThePersonList=${pageNumberOfThePersonListCounter}&nameOfTheColumnToBeSortedPersonList=${nameOfTheColumnToBeSortedPersonList}&pageNumberOfTheTaskList=${pageNumberOfTheTaskList}&nameOfTheColumnToBeSortedTaskList=${nameOfTheColumnToBeSortedTaskList}"/>">
                                    ${pageNumberOfThePersonListCounter}
                            </a>
                        </c:if>
                        <c:if test="${pageNumberOfThePersonListCounter ==  pageNumberOfThePersonList}">
                            <strong> ${pageNumberOfThePersonListCounter}</strong>
                        </c:if>
                    </td>
                </c:forEach>
                <c:if test="${pageNumberOfThePersonList < model.numberOfAllLinksToPagesInPageNavigationOfTheListOfPersons}">
                    <td>
                        <a href="<c:url value="/mainmenu?lang=${lang}&pageNumberOfThePersonList=${pageNumberOfThePersonList+1}&nameOfTheColumnToBeSortedPersonList=${nameOfTheColumnToBeSortedPersonList}&pageNumberOfTheTaskList=${pageNumberOfTheTaskList}&nameOfTheColumnToBeSortedTaskList=${nameOfTheColumnToBeSortedTaskList}"/>">
                                ${model.textValueNextPageOfTheListOfPersons}
                        </a>
                    </td>
                    <td>
                        <a href="<c:url value="/mainmenu?lang=${lang}&pageNumberOfThePersonList=${model.numberOfAllLinksToPagesInPageNavigationOfTheListOfPersons}&nameOfTheColumnToBeSortedPersonList=${nameOfTheColumnToBeSortedPersonList}&pageNumberOfTheTaskList=${pageNumberOfTheTaskList}&nameOfTheColumnToBeSortedTaskList=${nameOfTheColumnToBeSortedTaskList}"/>">
                                ${model.textValueLastPageOfTheListOfPersons}
                        </a>
                    </td>
                </c:if>
                <c:if test="${pageNumberOfThePersonList >= model.numberOfAllLinksToPagesInPageNavigationOfTheListOfPersons}">
                    <td>
                            ${model.textValueNextPageOfTheListOfPersons}
                    </td>
                    <td>
                            ${model.textValueLastPageOfTheListOfPersons}
                    </td>
                </c:if>
            </tr>
            </tbody>

        </table>
    </div>

    <div id="task-list">
        <c:if test="${multipleTaskSelectionMode}">
            <form hidden id="selected-tasks"
                  action="<c:url value="/task/delete/selected?lang=${lang}
              &pageNumberOfTheTaskList=${pageNumberOfTheTaskList}
              &nameOfTheColumnToBeSortedTaskList=${nameOfTheColumnToBeSortedTaskList}"/>"
                  method="post">
            </form>
        </c:if>
        <table id="tasks-table">
            <thead>
            <tr>
                <th id="taskId">${model.labelOfTheTaskTableColumnId}
                    <c:if test="${nameOfTheColumnToBeSortedTaskList == \"id\"}">&darr;
                    </c:if>
                    <c:if test="${nameOfTheColumnToBeSortedTaskList !=  \"id\"}">
                        <a href='<c:url value="/mainmenu?lang=${lang}&pageNumberOfThePersonList=${pageNumberOfThePersonList}&nameOfTheColumnToBeSortedPersonList=${nameOfTheColumnToBeSortedPersonList}&pageNumberOfTheTaskList=${pageNumberOfTheTaskList}&nameOfTheColumnToBeSortedTaskList=id"/>'>&uarr;</a>
                    </c:if>
                </th>
                <th id="name">${model.labelOfTheTaskTableColumnName}
                    <c:if test="${nameOfTheColumnToBeSortedTaskList == \"name\"}">&darr;
                    </c:if>
                    <c:if test="${nameOfTheColumnToBeSortedTaskList !=  \"name\"}">
                        <a href='<c:url value="/mainmenu?lang=${lang}&pageNumberOfThePersonList=${pageNumberOfThePersonList}&nameOfTheColumnToBeSortedPersonList=${nameOfTheColumnToBeSortedPersonList}&pageNumberOfTheTaskList=${pageNumberOfTheTaskList}&nameOfTheColumnToBeSortedTaskList=name"/>'>&uarr;</a>
                    </c:if>
                </th>
                <th id="volumeOfTheWork">${model.labelOfTheTaskTableColumnVolumeOfTheWork}
                    <c:if test="${nameOfTheColumnToBeSortedTaskList == \"volume_of_work_in_hours\"}">&darr;
                    </c:if>
                    <c:if test="${nameOfTheColumnToBeSortedTaskList !=  \"volume_of_work_in_hours\"}">
                        <a href='<c:url value="/mainmenu?lang=${lang}&pageNumberOfThePersonList=${pageNumberOfThePersonList}&nameOfTheColumnToBeSortedPersonList=${nameOfTheColumnToBeSortedPersonList}&pageNumberOfTheTaskList=${pageNumberOfTheTaskList}&nameOfTheColumnToBeSortedTaskList=volume_of_work_in_hours"/>'>&uarr;</a>
                    </c:if>
                </th>
                <th id="startDate">${model.labelOfTheTaskTableColumnStartDate}
                    <c:if test="${nameOfTheColumnToBeSortedTaskList == \"start_date\"}">&darr;
                    </c:if>
                    <c:if test="${nameOfTheColumnToBeSortedTaskList !=  \"start_date\"}">
                        <a href='<c:url value="/mainmenu?lang=${lang}&pageNumberOfThePersonList=${pageNumberOfThePersonList}&nameOfTheColumnToBeSortedPersonList=${nameOfTheColumnToBeSortedPersonList}&pageNumberOfTheTaskList=${pageNumberOfTheTaskList}&nameOfTheColumnToBeSortedTaskList=start_date"/>'>&uarr;</a>
                    </c:if>
                </th>
                <th id="dueDate">${model.labelOfTheTaskTableColumnDueDate}
                    <c:if test="${nameOfTheColumnToBeSortedTaskList == \"due_date\"}">&darr;
                    </c:if>
                    <c:if test="${nameOfTheColumnToBeSortedTaskList !=  \"due_date\"}">
                        <a href='<c:url value="/mainmenu?lang=${lang}&pageNumberOfThePersonList=${pageNumberOfThePersonList}&nameOfTheColumnToBeSortedPersonList=${nameOfTheColumnToBeSortedPersonList}&pageNumberOfTheTaskList=${pageNumberOfTheTaskList}&nameOfTheColumnToBeSortedTaskList=due_date"/>'>&uarr;</a>
                    </c:if>
                </th>
                <th id="status">${model.labelOfTheTaskTableColumnStatus}
                    <c:if test="${nameOfTheColumnToBeSortedTaskList == \"status\"}">&darr;
                    </c:if>
                    <c:if test="${nameOfTheColumnToBeSortedTaskList !=  \"status\"}">
                        <a href='<c:url value="/mainmenu?lang=${lang}&pageNumberOfThePersonList=${pageNumberOfThePersonList}&nameOfTheColumnToBeSortedPersonList=${nameOfTheColumnToBeSortedPersonList}&pageNumberOfTheTaskList=${pageNumberOfTheTaskList}&nameOfTheColumnToBeSortedTaskList=status"/>'>&uarr;</a>
                    </c:if>
                </th>
                <th id="executor">${model.labelOfTheTaskTableColumnExecutor}
                    <c:if test="${nameOfTheColumnToBeSortedTaskList == \"person_id\"}">&darr;
                    </c:if>
                    <c:if test="${nameOfTheColumnToBeSortedTaskList !=  \"person_id\"}">
                        <a href='<c:url value="/mainmenu?lang=${lang}&pageNumberOfThePersonList=${pageNumberOfThePersonList}&nameOfTheColumnToBeSortedPersonList=${nameOfTheColumnToBeSortedPersonList}&pageNumberOfTheTaskList=${pageNumberOfTheTaskList}&nameOfTheColumnToBeSortedTaskList=person_id"/>'>&uarr;</a>
                    </c:if>
                </th>
                <td>
                    <a href='<c:url value="/task/create?lang=${lang}"/>'>${model.textValueCreateATask}</a>
                </td>
                <td>
                    <c:if test="${multipleTaskSelectionMode == false}">
                        <a href='<c:url value="/mainmenu?lang=${lang}&pageNumberOfThePersonList=${pageNumberOfThePersonList}&nameOfTheColumnToBeSortedPersonList=${nameOfTheColumnToBeSortedPersonList}&pageNumberOfTheTaskList=${pageNumberOfTheTaskList}&nameOfTheColumnToBeSortedTaskList=${nameOfTheColumnToBeSortedTaskList}&multipleTaskSelectionMode=true"/>'>
                                ${model.textValueSelectTasks}
                        </a>
                    </c:if>
                    <c:if test="${multipleTaskSelectionMode}">
                        <input type="submit" form="selected-tasks" class="link"
                               value="${model.textValueDeleteSelectedTasks}">
                        <a href='<c:url value="/mainmenu?lang=${lang}&pageNumberOfThePersonList=${pageNumberOfThePersonList}&nameOfTheColumnToBeSortedPersonList=${nameOfTheColumnToBeSortedPersonList}&pageNumberOfTheTaskList=${pageNumberOfTheTaskList}&nameOfTheColumnToBeSortedTaskList=${nameOfTheColumnToBeSortedTaskList}"/>'>${model.textValueCancel}</a>
                    </c:if>
                </td>
            </tr>
            </thead>
            <tbody>
            <c:set var="listOfTasksWithInitials" value="${model.listOfTasksWithInitials}"
                   scope="page"/>
            <c:set var="tasks" value="${model.listOfTasks}" scope="page"/>
            <c:forEach var="task" items="${tasks}">
                <tr>
                    <td>${task.id}</td>
                    <td>${task.name}</td>
                    <td>${task.volumeOfWorkInHours}</td>
                    <td>${task.startDate}</td>
                    <td>${task.dueDate}</td>
                    <td>${statusValues.get(task.status)}</td>
                    <td>${model.getInitialsOfTheExecutor(task)}</td>
                    <td>
                        <a href='<c:url value="/task/edit?lang=${lang}&id=${task.id}&version=${task.version}"/>'>${model.textValueEditATask}</a>
                    </td>
                    <td>
                        <a href="<c:url value="/task/delete?lang=${lang}&id=${task.id}&version=${task.version}"/>"
                           onclick="return deleteTask('${model.getDeleteATaskQuestion(task.id)}');">
                                ${model.textValueDeleteTheTask}
                        </a>
                        <c:if test="${multipleTaskSelectionMode}">
                            <input type="checkbox" name="selectedTask" form="selected-tasks"
                                   value="${task.id},${task.version}"/>
                        </c:if>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
    <div id="tasks-list-pages">
        <table>
            <tbody>
            <tr>
                <c:if test="${pageNumberOfTheTaskList > 1}">
                    <td>
                        <a href="<c:url value="/mainmenu?lang=${lang}&pageNumberOfThePersonList=${pageNumberOfThePersonList}&nameOfTheColumnToBeSortedPersonList=${nameOfTheColumnToBeSortedPersonList}&pageNumberOfTheTaskList=${1}&nameOfTheColumnToBeSortedTaskList=${nameOfTheColumnToBeSortedTaskList}"/>">
                                ${model.textValueFirstPageOfListOfTasks}
                        </a>
                    </td>
                    <td>
                        <a href="<c:url value="/mainmenu?lang=${lang}&pageNumberOfThePersonList=${pageNumberOfThePersonList}&nameOfTheColumnToBeSortedPersonList=${nameOfTheColumnToBeSortedPersonList}&pageNumberOfTheTaskList=${pageNumberOfTheTaskList-1}&nameOfTheColumnToBeSortedTaskList=${nameOfTheColumnToBeSortedTaskList}"/>">
                                ${model.textValuePreviousPageOfListOfTasks}
                        </a>
                    </td>
                </c:if>
                <c:if test="${pageNumberOfTheTaskList <= 1}">
                    <td>
                            ${model.textValueFirstPageOfListOfTasks}
                    </td>
                    <td>
                            ${model.textValuePreviousPageOfListOfTasks}
                    </td>
                </c:if>
                <c:forEach begin="${model.firstPageNumberInThePersonListPageNavigation}"
                           end="${model.lastPageNumberInThePersonListPageNavigation}"
                           step="1"
                           var="pageNumberCounterOfListOfTasks"
                           varStatus="counter">
                    <td>
                        <c:if test="${pageNumberCounterOfListOfTasks != pageNumberOfTheTaskList}">
                            <a href="<c:url value="/mainmenu?lang=${lang}&pageNumberOfThePersonList=${pageNumberOfThePersonList}&nameOfTheColumnToBeSortedPersonList=${nameOfTheColumnToBeSortedPersonList}&pageNumberOfTheTaskList=${pageNumberCounterOfListOfTasks}&nameOfTheColumnToBeSortedTaskList=${nameOfTheColumnToBeSortedTaskList}"/>">
                                    ${pageNumberCounterOfListOfTasks}
                            </a>
                        </c:if>
                        <c:if test="${pageNumberCounterOfListOfTasks ==  pageNumberOfTheTaskList}">
                            <strong> ${pageNumberCounterOfListOfTasks}</strong>
                        </c:if>
                    </td>
                </c:forEach>
                <c:if test="${pageNumberOfTheTaskList < model.numberOfAllLinksToPagesInPageNavigationOfTheListOfTasks}">
                    <td>
                        <a href="<c:url value="/mainmenu?lang=${lang}&pageNumberOfThePersonList=${pageNumberOfThePersonList}&nameOfTheColumnToBeSortedPersonList=${nameOfTheColumnToBeSortedPersonList}&pageNumberOfTheTaskList=${pageNumberOfTheTaskList+1}&nameOfTheColumnToBeSortedTaskList=${nameOfTheColumnToBeSortedTaskList}"/>">
                                ${model.textValueNextPageOfTheListOfTasks}
                        </a>
                    </td>
                    <td>
                        <a href="<c:url value="/mainmenu?lang=${lang}&pageNumberOfThePersonList=${pageNumberOfThePersonList}&nameOfTheColumnToBeSortedPersonList=${nameOfTheColumnToBeSortedPersonList}&pageNumberOfTheTaskList=${model.numberOfAllLinksToPagesInPageNavigationOfTheListOfTasks}&nameOfTheColumnToBeSortedTaskList=${nameOfTheColumnToBeSortedTaskList}"/>">
                                ${model.textValueLastPageOfTheListOfTasks}
                        </a>
                    </td>
                </c:if>
                <c:if test="${pageNumberOfTheTaskList >= model.numberOfAllLinksToPagesInPageNavigationOfTheListOfTasks}">
                    <td>
                            ${model.textValueNextPageOfTheListOfTasks}
                    </td>
                    <td>
                            ${model.textValueLastPageOfTheListOfTasks}
                    </td>
                </c:if>
            </tr>
            </tbody>
        </table>
    </div>
</div>
<script>
    function deleteTask(message) {
        return confirm(message);
    }
</script>
<script>
    function deletePerson(message) {
        return confirm(message);
    }
</script>
</body>
</html>
