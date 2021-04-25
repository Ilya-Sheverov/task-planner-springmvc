<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page isELIgnored="false" %>

<link rel="stylesheet" href="<c:url value="/resources/css/task.css"/>">

<jsp:useBean id="model" beanName="model" scope="request"
             type="ilya.sheverov.projectstask.model.TaskModelFacade"/>

<html>
<head>
    <title>${model.titleOfThePageEditPerson}</title>
</head>
<body>

<c:set var="lang" value="${model.lang}" scope="page"/>
<c:set var="personsIdWithInitials" value="${model.personIdsAndInitials}"/>
<c:set var="personsId" value="${model.personIds}" scope="page"/>
<c:set var="task" value="${model.taskPresenter}"/>

<div id="create-task-form">
    <a href='<c:url value="/task/create?lang=ru?id=${task.id}&version=${task.version}"/>'>RU</a>
    <a href='<c:url value="/task/create?lang=en?id=${task.id}&version=${task.version}"/>'>EN</a>
    <form action="<c:url value="/task/edit?lang=${lang}"/>" method="post">

        <p>${model.labelOfTheId}</p>
        <c:if test="${model.taskIdIsIncorrect}">
            <p>${model.taskIdIsIncorrectMsg}</p>
        </c:if>
        <input required readonly hidden name="id" type="text" value="${task.id}"><br>

        <label for="name">${model.labelOfTheName}</label><br>
        <c:if test="${model.nameIsIncorrect}">
            <p>${model.nameIsIncorrectMsg}</p>
        </c:if>
        <textarea required type="text" id="name" name="name"
                  placeholder="${model.placeholderOfTheName}"
                  minlength="1" maxlength="120"
                  title="${model.titleOfTheName}">${task.name}</textarea>
        <br>

        <label for="volumeOfWork">${model.labelOfTheVolumeOfWorkInHours}</label><br>
        <c:if test="${model.volumeOfWorkInHoursIsIncorrect}">
            <p>${model.volumeOfWorkInHoursIsIncorrectMsg}</p>
        </c:if>
        <input type="number" id="volumeOfWork" name="volumeOfWorkInHours"
               placeholder="${model.placeholderOfTheVolumeOfWorkInHours}"
               title="${model.titleOfTheVolumeOfWorkInHours}"
               value="${task.volumeOfWorkInHours}">
        <br>

        <label for="status">${model.labelOfTheStatus}</label><br>
        <c:if test="${model.statusIsIncorrect}">
            <p>${model.statusIsIncorrectMsg}</p>
        </c:if>
        <select id="status" name="status" title="${model.titleOfTheStatus}">
            <option selected>${task.status}</option>
            <c:forEach var="status" items="${model.statusValues}">
                <c:if test="${task.status != status}">
                    <option>${status}</option>
                </c:if>
            </c:forEach>
        </select>
        <br>

        <label for="personId">${model.labelOfThePersonId}</label><br>
        <c:if test="${model.personIdIsIncorrect}">
            <p>${model.personIsIncorrectMsg}</p>
        </c:if>
        <select id="personId" name="personId" size="1" title="${model.titleOfThePersonId}">
            <c:if test="${empty task.personId}">
                <option selected value="${task.personId}">Не назначен</option>
            </c:if>
            <c:if test="${not empty task.personId}">
                <option value="${""}">Не назначен</option>
                <option selected
                        value="${task.personId}">${personsIdWithInitials.get(task.personId)}
                </option>
            </c:if>
            <c:forEach var="personId" items="${personsId}">
                <c:if test="${personId.equals(task.personId) == false}">
                    <option value="${personId}">${personsIdWithInitials.get(personId)}</option>
                </c:if>
            </c:forEach>
        </select>

        <br>

        <label for="startDate">${model.labelOfTheStartDate}</label><br>
        <c:if test="${model.startDateIsIncorrect}">
            <p>${model.startDateIsIncorrectMsg}</p>
        </c:if>
        <input type="text" id="startDate" class="data-time" name="startDate"
               placeholder="${model.placeholderOfTheStartDate}"
               title="${model.titleOfTheStartDate}"
               pattern="${model.patternDataTime}"
               value="${task.startDate}">
        <br>

        <label for="dueDate">${model.labelOfTheDueDate}</label><br>
        <c:if test="${model.dueDateIsIncorrect}">
            <p>${model.dueDateIsIncorrectMsg}</p>
        </c:if>
        <input type="text" id="dueDate" class="data-time" name="dueDate"
               placeholder="${model.placeholderOfTheDueDate}"
               title="${model.titleOfTheDueDate}"
               pattern="${model.patternDataTime}"
               value="${task.dueDate}">
        <br>

        <c:if test="${model.versionIsIncorrect}">
            <p>${model.versionIsIncorrectMsg}</p>
        </c:if>
        <input required hidden name="version" type="text"
               value="${task.version}"><br>

        <input type="submit" value="${model.submitEdit}">
        <input type="button" onclick="history.back();" value="${model.buttonBack}"/>
    </form>
</div>
</body>
</html>
