<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page isELIgnored="false" %>

<link rel="stylesheet" href="<c:url value="/resources/css/task.css"/>">

<jsp:useBean id="model" beanName="model" scope="request"
             type="ilya.sheverov.projectstask.model.TaskModelFacade"/>

<c:set var="lang" value="${model.lang}" scope="page"/>
<c:set var="pageNumber" value="${model.currentPageNumber}" scope="page"/>
<c:set var="nameOfTheColumnToBeSorted" value="${model.nameOfTheColumnToBeSorted}" scope="page"/>
<c:set var="multipleTaskSelectionMode" value="${model.multipleTaskSelectionMode}" scope="page"/>

<html>
<head>
    <title>${model.titleOfThePagePersonList}</title>
</head>
<body>
<header>
    <jsp:include page="header.jsp"/>
    <a href='<c:url value="/task/all?lang=ru&page=${pageNumber}&sortUsersList=${nameOfTheColumnToBeSorted}"/>'>RU</a>
    <a href='<c:url value="/task/all?lang=en&page=${pageNumber}&sortUsersList=${nameOfTheColumnToBeSorted}"/>'>EN</a>
</header>

<div id="task-list">
    <c:if test="${multipleTaskSelectionMode}">
        <form hidden id="selected-tasks"
              action="<c:url value="/task/delete/selected?lang=${lang}&page=${pageNumber}&sortTasksList=${nameOfTheColumnToBeSorted}"/>"
              method="post">
        </form>
    </c:if>
    <table id="tasks-table">
        <thead>
        <tr>
            <th id="id">${model.labelOfTheTableColumnId}
                <c:if test="${nameOfTheColumnToBeSorted == \"id\"}">
                    &darr;
                </c:if>
                <c:if test="${nameOfTheColumnToBeSorted !=  \"id\"}">
                    <a href='<c:url value="/task/all?lang=${lang}&page=${pageNumber}&sortTasksList=id"/>'>&uarr;</a>
                </c:if>
            </th>
            <th id="name">${model.labelOfTheTableColumnName}
                <c:if test="${nameOfTheColumnToBeSorted == \"name\"}">
                    &darr;
                </c:if>
                <c:if test="${nameOfTheColumnToBeSorted !=  \"name\"}">
                    <a href='<c:url value="/task/all?lang=${lang}&page=${pageNumber}&sortTasksList=name"/>'>&uarr;</a>
                </c:if>
            </th>
            <th id="volumeOfTheWork">${model.labelOfTheTableColumnVolumeOfTheWork}
                <c:if test="${nameOfTheColumnToBeSorted == \"volume_of_work_in_hours\"}">
                    &darr;
                </c:if>
                <c:if test="${nameOfTheColumnToBeSorted !=  \"volume_of_work_in_hours\"}">
                    <a href='<c:url value="/task/all?lang=${lang}&page=${pageNumber}&sortTasksList=volume_of_work_in_hours"/>'>&uarr;</a>
                </c:if>
            </th>
            <th id="startDate">${model.labelOfTheTableColumnStartDate}
                <c:if test="${nameOfTheColumnToBeSorted == \"start_date\"}">
                    &darr;
                </c:if>
                <c:if test="${nameOfTheColumnToBeSorted !=  \"start_date\"}">
                    <a href='<c:url value="/task/all?lang=${lang}&page=${pageNumber}&sortTasksList=start_date"/>'>&uarr;</a>
                </c:if>
            </th>
            <th id="dueDate">${model.labelOfTheTableColumnDueDate}
                <c:if test="${nameOfTheColumnToBeSorted == \"due_date\"}">
                    &darr;
                </c:if>
                <c:if test="${nameOfTheColumnToBeSorted !=  \"due_date\"}">
                    <a href='<c:url value="/task/all?lang=${lang}&page=${pageNumber}&sortTasksList=due_date"/>'>&uarr;</a>
                </c:if>
            </th>
            <th id="status">${model.labelOfTheTableColumnStatus}
                <c:if test="${nameOfTheColumnToBeSorted == \"status\"}">
                    &darr;
                </c:if>
                <c:if test="${nameOfTheColumnToBeSorted !=  \"status\"}">
                    <a href='<c:url value="/task/all?lang=${lang}&page=${pageNumber}&sortTasksList=status"/>'>&uarr;</a>
                </c:if>
            </th>
            <th id="executor">${model.labelOfTheTableColumnExecutor}
                <c:if test="${nameOfTheColumnToBeSorted == \"person_id\"}">
                    &darr;
                </c:if>
                <c:if test="${nameOfTheColumnToBeSorted !=  \"person_id\"}">
                    <a href='<c:url value="/task/all?lang=${lang}&page=${pageNumber}&sortTasksList=person_id"/>'>&uarr;</a>
                </c:if>
            </th>
            <td>
                <a href='<c:url value="/task/create?lang=${lang}"/>'>${model.textValueCreateATask}</a>
            </td>
            <td>
                <c:if test="${multipleTaskSelectionMode == false}">
                    <a href='<c:url value="/task/all?lang=${lang}&page=${pageNumber}&sortTasksList=${nameOfTheColumnToBeSorted}&multipleTaskSelectionMode=true"/>'>
                            ${model.textValueSelectTasks}
                    </a>
                </c:if>
                <c:if test="${multipleTaskSelectionMode}">
                    <input type="submit" form="selected-tasks" class="link"
                           value="${model.textValueDeleteSelectedTasks}">
                    <a href='<c:url value="/task/all?lang=${lang}&page=${pageNumber}&sortTasksList=${nameOfTheColumnToBeSorted}"/>'>${model.textValueCancel}</a>
                </c:if>
            </td>
        </tr>
        </thead>
        <tbody>
        <c:set var="listOfTasksWithInitials" value="${model.listOfTasksWithInitials}" scope="page"/>
        <c:set var="tasks" value="${model.listOfTasks}" scope="page"/>

        <c:forEach var="task" items="${tasks}">
            <tr>
                <td>${task.id}</td>
                <td>${task.name}</td>
                <td>${task.volumeOfWorkInHours}</td>
                <td>${task.startDate}</td>
                <td>${task.dueDate}</td>
                <td>${task.status}</td>
                <td>${listOfTasksWithInitials.get(task)}</td>
                <td>
                    <a href='<c:url value="/task/edit?id=${task.id}&version=${task.version}"/>'>${model.textValueEditATask}</a>
                </td>
                <td>
                    <a href="<c:url value="/task/delete?id=${task.id}&version=${task.version}"/>"
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
            <c:if test="${pageNumber > 1}">
                <td>
                    <a href="<c:url value="/task/all?lang=${lang}&page=${1}&sortTasksList=${nameOfTheColumnToBeSorted}"/>">
                            ${model.textValueFirstPage}
                    </a>
                </td>
                <td>
                    <a href="<c:url value="/task/all?lang=${lang}&page=${pageNumber-1}&sortTasksList=${nameOfTheColumnToBeSorted}"/>">
                            ${model.textValuePreviousPage}
                    </a>
                </td>
            </c:if>
            <c:if test="${pageNumber <= 1}">
                <td>
                        ${model.textValueFirstPage}
                </td>
                <td>
                        ${model.textValuePreviousPage}
                </td>
            </c:if>
            <c:forEach begin="${model.firstNumberOfPageInPageNavigation}"
                       end="${model.lastNumberOfPageInPageNavigation}"
                       step="1"
                       var="pageNumberCounter"
                       varStatus="counter">
                <td>
                    <c:if test="${pageNumberCounter != pageNumber}">
                        <a href="<c:url value="/task/all?lang=${lang}&page=${pageNumberCounter}&sortTasksList=${nameOfTheColumnToBeSorted}"/>">
                                ${pageNumberCounter}
                        </a>
                    </c:if>
                    <c:if test="${pageNumberCounter ==  pageNumber}">
                        <strong> ${pageNumberCounter}</strong>
                    </c:if>
                </td>
            </c:forEach>
            <c:if test="${pageNumber < model.numberOfAllLinksToPagesInPageNavigation}">
                <td>
                    <a href="<c:url value="/task/all?lang=${lang}&page=${pageNumber+1}&sortTasksList=${nameOfTheColumnToBeSorted}"/>">
                            ${model.textValueNextPage}
                    </a>
                </td>
                <td>
                    <a href="<c:url value="/task/all?lang=${lang}&page=${model.numberOfAllLinksToPagesInPageNavigation}&sortTasksList=${nameOfTheColumnToBeSorted}"/>">
                            ${model.textValueLastPage}
                    </a>
                </td>
            </c:if>
            <c:if test="${pageNumber >= model.numberOfAllLinksToPagesInPageNavigation}">
                <td>
                        ${model.textValueNextPage}
                </td>
                <td>
                        ${model.textValueLastPage}
                </td>
            </c:if>
        </tr>
        </tbody>
    </table>
</div>

<script>
    function deleteTask(message) {
        return confirm(message);
    }
</script>
<script>
    function message(message) {
        confirm(message);
    }
</script>

<c:if test="${model.hasMessage}">
    <script type="text/javascript">
        message("${model.message}")
    </script>
</c:if>
</body>
</html>
