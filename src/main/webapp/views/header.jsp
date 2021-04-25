<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page isELIgnored="false" %>

<jsp:useBean id="model" beanName="model" scope="request"
             type="ilya.sheverov.projectstask.model.HeaderFacade"/>

<c:set var="lang" value="${model.lang}" scope="page"/>

<html>
<body>
<nav>
    <a href='<c:url value= "/mainmenu?lang=${lang}"/>'>${model.textValueMainMenu}</a> |
    <a href='<c:url value= "/person/all?lang=${lang}"/>'>${model.textValuePersonList}</a> |
    <a href='<c:url value= "/task/all?lang=${lang}"/>'>${model.textValueTaskList}</a>
</nav>
</body>
</html>
