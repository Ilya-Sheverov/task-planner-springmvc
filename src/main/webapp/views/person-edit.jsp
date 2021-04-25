<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page isELIgnored="false" %>

<link rel="stylesheet" href="<c:url value="/resources/css/person.css"/>">

<jsp:useBean id="model" beanName="model" scope="request"
             type="ilya.sheverov.projectstask.model.PersonModelFacade"/>
<c:set var="lang" value="${model.lang}" scope="page"/>

<c:set var="person" value="${model.personPresenter}" scope="page"/>
<html>
<head>
    <title>${model.titlePageEditPerson}</title>
</head>
<body>
</div>
<div id="person-form">

    <a href='<c:url value="/person/edit?lang=ru&id=${person.id}&version=${person.version}"/>'>RU</a>
    <a href='<c:url value="/person/edit?lang=en&id=${person.id}&version=${person.version}"/>'>EN</a>

    <form action="<c:url value="/person/edit?lang=${lang}"/>" method="post">

        <label>${model.getInputLabelId(person.id)}</label><br>
        <c:if test="${model.idIsIncorrect}">
            <p>${model.messageIdIsIncorrect}</p>
        </c:if>
        <input hidden required readonly type="number" name="id" value="${person.id}"><br>

        <label>${model.inputLabelLastName}</label><br>
        <c:if test="${model.lastNameIsIncorrect}">
            <p>${model.messageLastNameIsIncorrect}</p>
        </c:if>
        <input required type="text" name="lastName" placeholder="${model.lastNamePlaceholder}"
               title="${model.lastNameTitle}"
               minlength="1" maxlength="35"
               value="${person.lastName}"><br>

        <label>${model.inputLabelFirstName}</label><br>
        <c:if test="${model.firstNameIsIncorrect}">
            <p>${model.messageFirstNameIsIncorrect}</p>
        </c:if>
        <input required type="text" name="firstName" placeholder="${model.firstNamePlaceholder}"
               title="${model.firstNameTitle}"
               minlength="1" maxlength="35"
               value="${person.firstName}"><br>

        <label>${model.inputLabelMiddleName}</label><br>
        <c:if test="${model.middleNameIsIncorrect}">
            <p>${model.messageMiddleNameIsIncorrect}</p>
        </c:if>
        <input required type="text" name="middleName" placeholder="${model.middleNamePlaceholder}"
               title="${model.middleNameTitle}"
               minlength="1" maxlength="35"
               value="${person.middleName}"><br>

        <c:if test="${model.versionIsIncorrect}">
            <p>${model.messageVersionIsIncorrect}</p>
        </c:if>
        <input required hidden type="text" name="version"
               value="${person.version}"><br>

        <input type="submit" value="${model.editSubmit}">
        <input type="button" onclick="history.back();" value="${model.backButton}"/>
    </form>
</div>
</body>
</html>
