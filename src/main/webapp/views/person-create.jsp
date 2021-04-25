<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page isELIgnored="false" %>

<link rel="stylesheet" href="../resources/css/person.css" type="text/css">

<jsp:useBean id="model" beanName="model" scope="request"
             type="ilya.sheverov.projectstask.model.PersonModelFacade"/>
<c:set var="lang" value="${model.lang}" scope="page"/>

<c:set var="person" value="${model.personPresenter}" scope="page"/>
<html>
<head>
    <title>${model.titlePageCreatePerson}</title>
</head>
<body>
<div id="person-form">
    <a href='<c:url value="/person/create?lang=ru"/>'>RU</a>
    <a href='<c:url value="/person/create?lang=en"/>'>EN</a>
    <form action="<c:url value="/person/create?lang=${lang}"/>" method="post">
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

        <input type="submit" value="${model.createSubmit}">
        <input type="button" onclick="history.back();" value="${model.backButton}"/>
    </form>
</div>
</body>
</html>
